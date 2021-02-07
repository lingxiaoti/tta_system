package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentIngredientsEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentIngredientsEntity_HI;

public interface IPlmDevelopmentIngredients extends IBaseCommon<PlmDevelopmentIngredientsEntity_HI> {

	Pagination<PlmDevelopmentIngredientsEntity_HI_RO> findPlmDevelopmentIngredientsInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmDevelopmentIngredientsEntity_HI> savePlmDevelopmentIngredientsInfo(JSONObject queryParamJSON) throws Exception;
	
	Integer deletePlmDevelopmentIngredientsInfo(JSONObject queryParamJSON);

}
