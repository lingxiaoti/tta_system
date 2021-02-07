package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.WxUserEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

/**
 * Created by chenzg on 2018/3/6.
 */
public interface IWxUser extends IBaseCommon<WxUserEntity_HI> {

    public List<String> findByName(JSONObject queryParamJSON);

    public List<WxUserEntity_HI> findByCondition(JSONObject queryParamJSON);
}
