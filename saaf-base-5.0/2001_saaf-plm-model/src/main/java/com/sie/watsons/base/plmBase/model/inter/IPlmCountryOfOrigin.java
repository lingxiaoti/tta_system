package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmCountryOfOriginEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmCountryOfOriginEntity_HI;

/**
 * @author panshenghao
 */
public interface IPlmCountryOfOrigin extends IBaseCommon<PlmCountryOfOriginEntity_HI> {

	/**
	 * 查询
	 * @param queryParams 查询传参
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	Pagination<PlmCountryOfOriginEntity_HI_RO> findPlmCountryOfOriginInfo(JSONObject queryParams, Integer pageIndex, Integer pageRows);

	/**
	 * 保存
	 * @param queryParams
	 * @return
	 */
	PlmCountryOfOriginEntity_HI savePlmCountryOfOriginInfo(JSONObject queryParams);

}
