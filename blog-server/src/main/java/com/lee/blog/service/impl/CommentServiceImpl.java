package com.lee.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.blog.dto.*;
import com.lee.blog.entity.Comment;
import com.lee.blog.mapper.BlogMapper;
import com.lee.blog.mapper.CommentMapper;
import com.lee.blog.mapper.UserMapper;
import com.lee.blog.service.BlogInfoService;
import com.lee.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.service.RedisService;
import com.lee.blog.util.HTMLUtil;
import com.lee.blog.util.PageUtil;
import com.lee.blog.util.UserUtil;
import com.lee.blog.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lee.common.comstant.MQConst.EMAIL_EXCHANGE;
import static com.lee.common.comstant.RedisConst.COMMENT_LIKE_COUNT;
import static com.lee.common.comstant.RedisConst.COMMENT_USER_LIKE;
import static com.lee.common.comstant.Const.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    BlogInfoService blogInfoService;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisService redisService;

    /**
     * 网站网址
     */
    @Value("${website.url}")
    private String websiteUrl;

    /**
     * 审核评论
     * @param reviewVo
     */
    @Override
    public void updateCommentsReview(ReviewVo reviewVo) {
        // 修改留言审核状态
        List<Comment> commentList = reviewVo.getIdList().stream().map(item -> Comment.builder()
                .commentId(item)
                .isReview(reviewVo.getIsReview())
                .build())
                .collect(Collectors.toList());
        this.updateBatchById(commentList);
    }

    /**
     * 查询评论列表
     * @param condition
     * @return
     */
    @Override
    public PageResult<CommentListDTO> getCommentListDTO(ConditionVo condition) {

        // 统计后台评论量
        Integer count = commentMapper.countComment(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台评论集合
        List<CommentListDTO> commentList = commentMapper.getCommentListDTO(
                PageUtil.getLimitCurrent(), PageUtil.getSize(), condition);
        return new PageResult<>(commentList, count);
    }

    /**
     * 添加评论
     * @param commentVo
     */
    @Override
    public void saveComment(CommentVo commentVo) {
        //log.info("传入数据===》"+commentVo);
        // 判断是否需要审核
        WebsiteConfigVo websiteConfig = blogInfoService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        // 过滤标签
        commentVo.setCommentContent(HTMLUtil.deleteTag(commentVo.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(UserUtil.getLoginUser().getUserId())
                .replyUserId(commentVo.getReplyUserId())
                .blogId(commentVo.getBlogId())
                .commentContent(commentVo.getCommentContent())
                .parentCommentId(commentVo.getParentCommentId())
                .isReview(isReview == TRUE ? FALSE : TRUE)
                .build();

        //log.info("存入数据===》"+comment);
        commentMapper.insert(comment);

        // 判断是否开启邮箱通知,通知用户
        if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
            //notice(comment);
        }
    }

    /**
     * 查询某评论下的回复及相关数据
     * @param commentId
     * @return
     */
    @Override
    public List<ReplyDTO> listRepliesByCommentId(Long commentId) {

        // 转换页码查询评论下的回复
        List<ReplyDTO> replyDTOList = commentMapper.listRepliesByCommentId(
                PageUtil.getLimitCurrent(), PageUtil.getSize(), commentId);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        // 封装点赞数据
        replyDTOList.forEach(item ->
                item.setLikeCount((Integer) likeCountMap.get(item.getUserId().toString())));
        //log.info("评论下的回复 ===》"+replyDTOList);
        return replyDTOList;
    }

    /**
     * 点赞评论
     * @param commentId
     */
    @Override
    public void saveCommentLike(Long commentId) {

        // 判断是否点赞
        String commentLikeKey = COMMENT_USER_LIKE + UserUtil.getLoginUser().getUserId();
        if (redisService.sIsMember(commentLikeKey, commentId)) {
            // 点过赞则删除评论id
            redisService.sRemove(commentLikeKey, commentId);
            // 评论点赞量-1
            redisService.hDecr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            redisService.sAdd(commentLikeKey, commentId);
            // 评论点赞量+1
            redisService.hIncr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

    /**
     * 查询某博客评论列表及信息
     * @param blogId
     * @return
     */
    @Override
    public PageResult<CommentDTO> listComments(Long blogId) {

        // 查询文章评论量
        Integer commentCount = commentMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(blogId), Comment::getBlogId, blogId)
                .isNull(Objects.isNull(blogId), Comment::getBlogId)
                .isNull(Comment::getParentCommentId)
                .eq(Comment::getIsReview, TRUE));
        if (commentCount == 0) {
            return new PageResult<>();
        }
        //log.info("文章评论量 ===>"+commentCount);
        // 分页查询评论集合
        List<CommentDTO> commentDTOList = commentMapper.listComments(
                PageUtil.getLimitCurrent(), PageUtil.getSize(), blogId);
        //log.info("评论集合===>"+commentDTOList);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        //log.info("评论点赞数据 ===>"+likeCountMap);
        // 提取评论id集合
        List<Long> commentIdList = commentDTOList.stream()
                .map(CommentDTO::getCommentId)
                .collect(Collectors.toList());
        //log.info("commentIdList ==>"+commentIdList);
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = commentMapper.listReplies(commentIdList);
        //log.info("根据评论id集合查询回复数据 ==>"+replyDTOList);
        // 封装回复点赞量
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getCommentId().toString())));

        // 根据评论id分组回复数据
        Map<Long, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentCommentId));
        // 根据评论id查询回复量
        Map<Long, Integer> replyCountMap = commentMapper.listReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));

        // 封装评论数据
        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getCommentId().toString()));
            item.setReplyDTOList(replyMap.get(item.getCommentId()));
            item.setReplyCount(replyCountMap.get(item.getCommentId()));
        });
        //log.info("封装评论数据 ===>"+commentDTOList);
        return new PageResult<>(commentDTOList, commentCount);
    }

    /**
     * 邮箱通知评论用户
     * @param comment
     */
    private void notice(Comment comment) {

        // 查询回复用户邮箱号
        Long blogId = blogMapper.selectById(comment.getBlogId()).getUserId();
        Long userId = Optional.ofNullable(comment.getReplyUserId())
                .orElse(blogId);
        String email = userMapper.selectById(userId).getEmail();

        if (StringUtils.isNotBlank(email)) {
            // 发送消息
            EmailDTO emailDTO = new EmailDTO();
            if (comment.getIsReview().equals(TRUE)) {
                // 评论提醒
                emailDTO.setEmail(email);
                emailDTO.setSubject("评论提醒");
                // 判断页面路径
                String url = Objects.nonNull(comment.getBlogId()) ? websiteUrl + ARTICLE_PATH + comment.getBlogId() : websiteUrl + LINK_PATH;
                emailDTO.setContent("您收到了一条新的回复，请前往" + url + "\n页面查看");
            } else {
                // 管理员审核提醒
                String adminEmail = userMapper.selectById(blogId).getEmail();
                emailDTO.setEmail(adminEmail);
                emailDTO.setSubject("审核提醒");
                emailDTO.setContent("您收到了一条新的回复，请前往后台管理页面审核");
            }
            //给email交换机发送消息
            rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        }
    }
}
