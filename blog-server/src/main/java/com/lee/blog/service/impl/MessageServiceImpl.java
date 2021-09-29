package com.lee.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.blog.dto.MessageDTO;
import com.lee.blog.dto.MessageListDTO;
import com.lee.blog.entity.Message;
import com.lee.blog.mapper.MessageMapper;
import com.lee.blog.service.BlogInfoService;
import com.lee.blog.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.util.IpUtil;
import com.lee.blog.util.PageUtil;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.MessageVo;
import com.lee.blog.vo.PageResult;
import com.lee.blog.vo.ReviewVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lee.common.comstant.Const.FALSE;
import static com.lee.common.comstant.Const.TRUE;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    BlogInfoService blogInfoService;

    @Resource
    HttpServletRequest request;

    /**
     * 审核留言
     * @param reviewVo
     */
    @Override
    public void updateMessagesReview(ReviewVo reviewVo) {
        // 修改留言审核状态
        List<Message> messageList = reviewVo.getIdList().stream().map(item -> Message.builder()
                .mid(item)
                .isReview(reviewVo.getIsReview())
                .build())
                .collect(Collectors.toList());
        this.updateBatchById(messageList);
    }

    /**
     * admin获取留言列表
     * @param condition
     * @return
     */
    @Override
    public PageResult<MessageListDTO> getMessageListDTO(ConditionVo condition) {
        // 分页查询留言列表
        Page<Message> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());

        LambdaQueryWrapper<Message> messageLambdaQueryWrapper = new LambdaQueryWrapper<Message>()
                //keywords模糊匹配
                .like(StringUtils.isNotBlank(condition.getKeywords()), Message::getNickname, condition.getKeywords())
                //对比审核状态
                .eq(Objects.nonNull(condition.getIsReview()), Message::getIsReview, condition.getIsReview())
                //mid降序
                .orderByDesc(Message::getMid);

        Page<Message> messagePage = messageMapper.selectPage(page, messageLambdaQueryWrapper);
        // 转换DTO
        List<MessageListDTO> messageBackDTOList = BeanCopyUtil.copyList(
                messagePage.getRecords(), MessageListDTO.class);
        return new PageResult<>(messageBackDTOList, (int) messagePage.getTotal());
    }

    /**
     * 添加保存留言
     * @param messageVo
     */
    @Override
    public void saveMessage(MessageVo messageVo) {

        // 判断是否需要审核
        Integer isReview = blogInfoService.getWebsiteConfig().getIsMessageReview();
        // 获取用户ip
        String ipAddress = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        Message message = BeanUtil.copyProperties(messageVo, Message.class);
        message.setIpAddress(ipAddress);
        message.setIsReview(isReview == TRUE ? FALSE : TRUE);
        message.setIpSource(ipSource);
        messageMapper.insert(message);
    }

    /**
     * 查询展示留言列表
     * @return
     */
    @Override
    public List<MessageDTO> listMessages() {

        // 查询留言列表
        List<Message> messageList = messageMapper.selectList(new LambdaQueryWrapper<Message>()
                .select(Message::getMid, Message::getNickname, Message::getAvatar,
                        Message::getMessageContent, Message::getTime)
                .eq(Message::getIsReview, TRUE));
        return BeanCopyUtil.copyList(messageList, MessageDTO.class);
    }
}
