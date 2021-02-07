package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseWarehouseMenu_HI_RO;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */
public interface IBaseWarehouseMenu {
    List<BaseWarehouseMenu_HI_RO> query(JSONObject queryParamJSON);
}
