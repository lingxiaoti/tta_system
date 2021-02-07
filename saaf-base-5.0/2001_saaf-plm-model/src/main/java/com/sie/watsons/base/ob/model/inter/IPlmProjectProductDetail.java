package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectProductDetailEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmProjectProductDetailEntity_HI;

public interface IPlmProjectProductDetail extends IBaseCommon<PlmProjectProductDetailEntity_HI> {

	Pagination<PlmProjectProductDetailEntity_HI_RO> findPlmProjectProductDetailInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmProjectProductDetailEntity_HI> savePlmProjectProductDetailInfo(JSONObject queryParamJSON);

	Integer deletePlmProjectProductDetailInfo(JSONObject queryParamJSON);

}
