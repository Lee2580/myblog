package com.lee.blog.util;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 拷贝工具类
 * @author lee
 * @create 2021-09-21 16:58
 **/
public class BeanCopyUtil {

    /**
     * 拷贝集合
     *
     * @param source 源
     * @param target 目标
     * @return {@link List<T>} 集合
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(BeanUtil.copyProperties(obj, target));
            }
        }
        return list;
    }
}
