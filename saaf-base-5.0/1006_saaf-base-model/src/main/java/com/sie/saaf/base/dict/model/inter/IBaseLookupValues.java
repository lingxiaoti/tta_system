package com.sie.saaf.base.dict.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

/**
 * @author huangtao
 * @createTime 2017年12月11日 20:35:25
 */
public interface IBaseLookupValues extends IBaseCommon<BaseLookupValuesEntity_HI> {


	BaseLookupValuesEntity_HI saveCacheOrUpdateBaseLookupValue(JSONObject parameter, int userId) throws Exception;

	JSONObject saveCacheOrUpdateALL(JSONObject lookupTypeParameter, JSONArray lookupValueParameter, int userId) throws Exception;

	List<BaseLookupValuesEntity_HI_RO> findCacheDic(JSONObject queryParamJSON);

	String findBaseLookupValuesPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	void deleteCache(Integer id);

	Pagination<BaseLookupValuesEntity_HI_RO> findCurrentBaseLookupValuesPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
