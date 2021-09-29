package com.lee.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.blog.dto.FriendLinkDTO;
import com.lee.blog.dto.FriendLinkListDTO;
import com.lee.blog.entity.Blog;
import com.lee.blog.entity.FriendLink;
import com.lee.blog.mapper.FriendLinkMapper;
import com.lee.blog.service.FriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.util.PageUtil;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.FriendLinkStatusVo;
import com.lee.blog.vo.FriendLinkVo;
import com.lee.blog.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Autowired
    FriendLinkMapper friendLinkMapper;

    /**
     * 更新保存友链
     * @param friendLinkVo
     */
    @Override
    public void saveOrUpdateFriendLink(FriendLinkVo friendLinkVo) {
        FriendLink friendLink = BeanUtil.copyProperties(friendLinkVo, FriendLink.class);
        this.saveOrUpdate(friendLink);
    }

    /**
     * 修改友链展示状态
     * @param friendLinkStatusVo
     */
    @Override
    public void updateLinkStatus(FriendLinkStatusVo friendLinkStatusVo) {
        //log.info("friendLinkStatusVo ===>"+friendLinkStatusVo);
        FriendLink friendLink = FriendLink.builder().id(friendLinkStatusVo.getId())
                .linkStatus(friendLinkStatusVo.getLinkStatus()).build();
        friendLinkMapper.updateById(friendLink);
    }

    /**
     * admin查询友链列表
     * @param conditionVo
     * @return
     */
    @Override
    public PageResult<FriendLinkListDTO> listFriendLinkDTO(ConditionVo conditionVo) {

        // 分页查询友链列表
        Page<FriendLink> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<FriendLink> friendLinkPage = friendLinkMapper.selectPage(page, new LambdaQueryWrapper<FriendLink>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), FriendLink::getLinkName, conditionVo.getKeywords()));
        // 转换DTO
        List<FriendLinkListDTO> friendLinkBackDTOList = BeanCopyUtil.copyList(
                friendLinkPage.getRecords(), FriendLinkListDTO.class);
        return new PageResult<>(friendLinkBackDTOList, (int) friendLinkPage.getTotal());
    }

    /**
     * 查询展示友链列表
     * @return
     */
    @Override
    public List<FriendLinkDTO> listFriendLinks() {

        // 查询友链列表
        List<FriendLink> friendLinkList = friendLinkMapper.selectList(
                new LambdaQueryWrapper<FriendLink>().eq(FriendLink::getLinkStatus,1));
        //log.info("friendLinkList ===>"+friendLinkList);
        return BeanCopyUtil.copyList(friendLinkList, FriendLinkDTO.class);
    }
}
