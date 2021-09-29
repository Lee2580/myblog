package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 通用审核vo
 * @author lee
 * @create 2021-09-15 22:55
 **/
@Data
public class ReviewVo {

    /**
     * id列表
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(name = "idList", value = "id列表", required = true, dataType = "List<Long>")
    private List<Long> idList;

    /**
     * 状态值
     */
    @NotNull(message = "状态值不能为空")
    @ApiModelProperty(name = "isDelete", value = "审核状态", required = true, dataType = "Integer")
    private Integer isReview;
}
