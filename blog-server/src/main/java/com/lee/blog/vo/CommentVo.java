package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 评论vo
 * @author lee
 * @create 2021-09-18 13:17
 **/
@Data
public class CommentVo {
    /**
     * 回复用户id
     */
    @ApiModelProperty(name = "replyUserId", value = "回复用户id", dataType = "Long")
    private Long replyUserId;

    /**
     * 评论博客id
     */
    @ApiModelProperty(name = "blogId", value = "文章id", dataType = "Long")
    private Long blogId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @ApiModelProperty(name = "commentContent", value = "评论内容", required = true, dataType = "String")
    private String commentContent;

    /**
     * 父评论id
     */
    @ApiModelProperty(name = "parentCommentId", value = "评论父id", dataType = "Long")
    private Long parentCommentId;
}
