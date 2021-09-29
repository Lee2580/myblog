package com.lee.blog.service;

import com.lee.blog.dto.CommentDTO;
import com.lee.blog.dto.CommentListDTO;
import com.lee.blog.dto.ReplyDTO;
import com.lee.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.CommentVo;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.blog.vo.ReviewVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
public interface CommentService extends IService<Comment> {

    void updateCommentsReview(ReviewVo reviewVo);

    PageResult<CommentListDTO> getCommentListDTO(ConditionVo condition);

    void saveComment(CommentVo commentVo);

    List<ReplyDTO> listRepliesByCommentId(Long commentId);

    void saveCommentLike(Long commentId);

    PageResult<CommentDTO> listComments(Long blogId);
}
