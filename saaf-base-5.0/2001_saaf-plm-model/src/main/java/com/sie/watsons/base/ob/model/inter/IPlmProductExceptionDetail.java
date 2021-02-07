package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProductExceptionDetailEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmProductExceptionDetailEntity_HI;

public interface IPlmProductExceptionDetail extends IBaseCommon<PlmProductExceptionDetailEntity_HI> {

	Pagination<PlmProductExceptionDetailEntity_HI_RO> findPlmProductExceptionDetailInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmProductExceptionDetailEntity_HI> savePlmProductExceptionDetailInfo(JSONObject queryParamJSON);

	Integer deletePlmProductExceptionDetailInfo(JSONObject queryParamJSON);

}
