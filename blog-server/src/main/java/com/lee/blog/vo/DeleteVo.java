package com.lee.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 逻辑删除vo
 * @author lee
 * @create 2021-09-14 23:02
 **/
@Data
public class DeleteVo {

    /**
     * id列表
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(name = "idList", value = "id列表", required = true, dataType = "List<Integer>")
    private List<Long> idList;

    /**
     * 状态值
     */
    @NotNull(message = "状态值不能为空")
    @ApiModelProperty(name = "isDelete", value = "删除状态", required = true, dataType = "Integer")
    private Integer isDelete;
}
