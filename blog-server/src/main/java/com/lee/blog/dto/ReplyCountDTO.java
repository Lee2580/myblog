package com.lee.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回复量dto
 * @author lee
 * @create 2021-09-18 16:32
 **/
@Data
public class ReplyCountDTO {

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 回复数量
     */
    private Integer replyCount;
}
