package com.sie.saaf.common.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseCommonMessageConfirmEntity_HI;

import java.util.List;
import java.util.Map;

/**
 * @auther: huqitao 2018/7/25
 */
public interface IBaseCommonMessageConfirm {
    List<BaseCommonMessageConfirmEntity_HI> findByProperty(Map<String, Object> queryMap);

    List<BaseCommonMessageConfirmEntity_HI> findByProperty(String var1, Object var2);

    List<BaseCommonMessageConfirmEntity_HI> findList(JSONObject queryParamsJSON);

    void delete(BaseCommonMessageConfirmEntity_HI messageConfirmEntity);

    void deleteAll(List<BaseCommonMessageConfirmEntity_HI> messageConfirmList);
}
