package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementHeadEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupShopEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementHeadEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IPlmSupplementHead extends IBaseCommon<PlmSupplementHeadEntity_HI>{

	List<PlmSupplementHeadEntity_HI> findPlmSupplementHeadInfo(JSONObject queryParamJSON);

	Object savePlmSupplementHeadInfo(JSONObject ParamJSON) throws Exception;

	Pagination<PlmSupplementHeadEntity_HI_RO> findPlmSupplementInfo(JSONObject queryParamJSON, Integer pageIndex,
			Integer pageRows);

	Object findPlmSupplementDetail(JSONObject queryParamJSON, Integer pageIndex,
																	Integer pageRows);

	List<PlmSupShopEntity_HI_RO> getShops(JSONObject queryParamJSON);

	List<PlmSupShopEntity_HI_RO> getExShops(JSONObject queryParamJSON);

    void updateShopsSupExe(JSONObject queryParamJSON);

    void updateShopsSupExpire(JSONObject queryParamJSON);

    JSONObject updateShopsSupBefore(JSONObject queryParamJSON) throws Exception;

	JSONObject findPlmSupItem(JSONObject queryParamJSON);

	void saveForWorkflow(JSONObject queryParamJSON);

    Object getItems(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Object findLines(JSONObject queryParamJSON);

	void deleteCahe();

	Object findPlmSupplementLines(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
