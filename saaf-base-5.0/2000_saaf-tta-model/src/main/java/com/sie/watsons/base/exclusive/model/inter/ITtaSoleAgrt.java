package com.sie.watsons.base.exclusive.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtInfoEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtInfoEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleItemEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public interface ITtaSoleAgrt extends IBaseCommon<TtaSoleAgrtEntity_HI>{

	TtaSoleAgrtEntity_HI saveTtaSoleAgrt(JSONObject params, UserSessionBean userSessionBean) throws Exception;

	Pagination<TtaSoleAgrtEntity_HI_RO> findTtaSoleAgrtEntity_HI_RO(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;
	
	Pagination<TtaSoleSupplierEntity_HI_RO> findTtaSoleSupplierEntity_HI_RO(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

	Pagination<TtaSoleAgrtInfoEntity_HI_RO> findTtaSoleAgrtInfoEntity_HI_RO(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

	Pagination<TtaSoleItemEntity_HI_RO> findTtaSoleItemEntity_HI_RO(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

    TtaSoleAgrtEntity_HI sumbitSoleAgrtInfo(JSONObject parameters, int userId) throws Exception;

    TtaSoleAgrtEntity_HI ttaSoleAgrtDiscard(JSONObject parameters, int userId) throws Exception;

    TempRuleDefEntity_HI_RO findRuleInfoByCondition(JSONObject paramJson) throws Exception;

    void updateStatus(JSONObject parameters, int userId) throws Exception;

	TtaSoleAgrtEntity_HI_RO findByRoId(JSONObject jsonObject);

    JSONObject callSoleAgrtChangStatus(JSONObject jsonObject, Integer userId) throws Exception;

    TtaSoleAgrtEntity_HI updateExclusiveSkipStatus(JSONObject jsonObject, UserSessionBean userSessionBean) throws Exception;

    Map<String,Object> setParam(Integer soleAgrtInfoId, Integer soleAgrtHId);

    TtaSoleAgrtEntity_HI ttaSoleAgrtCancal(JSONObject parameters, int userId) throws Exception;

    byte[] findItemList(Integer soleAgrtInfoId) throws Exception;

    TtaSoleAgrtEntity_HI checkTtaSoleAgrtBillCodeStatus(JSONObject parameters, int userId);

    ByteArrayInputStream insertWatermarkAndLogo(Integer soleAgrtHId, ByteArrayInputStream byteInput) throws Exception;

    List<TtaSoleAgrtEntity_HI> findSoleAgrt(String soleAgrtCode);

    List<TtaSoleAgrtInfoEntity_HI> findSoleAgrtInfo(Integer soleAgrtHId);

    ByteArrayInputStream findTtaSoleAgrtHeader(Integer soleAgrtHId, ByteArrayInputStream byteInput) throws Exception;
}
