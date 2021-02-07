package com.sie.watsons.base.newbreedextend.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.newbreedextend.model.entities.TtaNewbreedExtendLineEntity_HI;
import com.sie.watsons.base.newbreedextend.model.entities.readonly.TtaNewbreedExtendHeaderEntity_HI_RO;

import java.util.List;
import com.sie.watsons.base.newbreedextend.model.entities.TtaNewbreedExtendHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaNewbreedExtendHeader extends IBaseCommon<TtaNewbreedExtendHeaderEntity_HI>{

    TtaNewbreedExtendHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON,Integer userId) throws Exception;

    void saveToNBExtend(JSONObject queryParamJSON,Integer userId) throws Exception;

    void saveToNBExtendL(JSONObject queryParamJSON,Integer userId,Integer hId) throws Exception;

    List<TtaNewbreedExtendLineEntity_HI> saveOrUpdateALL(JSONArray paramsJSON, int userId,int hId) throws Exception;

    JSONObject saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void saveTtaNBEComfirm(JSONObject queryParamJSON,Integer userId) throws Exception;

    void saveTtaNBECancelComfirm(JSONObject queryParamJSON,Integer userId) throws Exception ;

    void delete(Integer articleId);
}
