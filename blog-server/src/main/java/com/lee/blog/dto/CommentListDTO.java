package com.lee.blog.dto;

import com.lee.blog.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lee
 * @create 2021-09-16 11:13
 **/
@Data
public class CommentListDTO {

    /**
     * id
     */
    private Integer commentId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 被回复用户昵称
     */
    private String replyNickname;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 是否审核
     */
    private Integer isReview;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

}
