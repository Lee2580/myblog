package com.lee.blog.service;

import com.lee.blog.dto.MessageDTO;
import com.lee.blog.dto.MessageListDTO;
import com.lee.blog.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.MessageVo;
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
public interface MessageService extends IService<Message> {

    void updateMessagesReview(ReviewVo reviewVo);

    PageResult<MessageListDTO> getMessageListDTO(ConditionVo condition);

    void saveMessage(MessageVo messageVo);

    List<MessageDTO> listMessages();
}
