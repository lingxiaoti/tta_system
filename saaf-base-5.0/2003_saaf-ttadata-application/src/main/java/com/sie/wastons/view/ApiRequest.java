package com.sie.wastons.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @ClassName: RequestParam
 * @Description: 通用入参
 * @author: wangxl
 * @date: 2019/4/23 16:56
 */
@ApiModel("入参")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest<T> extends BasePagination {

    @ApiModelProperty(value = "params", position = 1)
    private T params;

}
