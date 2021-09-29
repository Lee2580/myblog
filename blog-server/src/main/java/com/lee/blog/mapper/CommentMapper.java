package com.lee.blog.mapper;

import com.lee.blog.dto.CommentDTO;
import com.lee.blog.dto.CommentListDTO;
import com.lee.blog.dto.ReplyCountDTO;
import com.lee.blog.dto.ReplyDTO;
import com.lee.blog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.blog.vo.ConditionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    Integer countComment(@Param("condition") ConditionVo condition);

    List<CommentListDTO> getCommentListDTO(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size, @Param("condition") ConditionVo condition);

    List<ReplyDTO> listRepliesByCommentId(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size, @Param("commentId") Long commentId);

    List<CommentDTO> listComments(@Param("limitCurrent") Long limitCurrent, @Param("size") Long size, @Param("blogId") Long blogId);

    List<ReplyDTO> listReplies(@Param("commentIdList") List<Long> commentIdList);

    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Long> commentIdList);
}
