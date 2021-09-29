package com.lee.blog.service;

import com.lee.blog.dto.FriendLinkDTO;
import com.lee.blog.dto.FriendLinkListDTO;
import com.lee.blog.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.FriendLinkStatusVo;
import com.lee.blog.vo.FriendLinkVo;
import com.lee.blog.vo.PageResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
public interface FriendLinkService extends IService<FriendLink> {

    void saveOrUpdateFriendLink(FriendLinkVo friendLinkVo);

    void updateLinkStatus(FriendLinkStatusVo friendLinkStatusVo);

    PageResult<FriendLinkListDTO> listFriendLinkDTO(ConditionVo conditionVo);

    List<FriendLinkDTO> listFriendLinks();
}
