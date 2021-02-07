package com.sie.saaf.base.api.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.api.model.entities.BaseApiManagementEntity_HI;
import com.sie.saaf.base.api.model.entities.readonly.BaseApiManagementLookupValues_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.base.api.model.entities.BaseApiManagementEntity_HI;
import com.sie.saaf.base.api.model.entities.readonly.BaseApiManagementLookupValues_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * 接口：模块API管理
 * 
 * @author ZhangJun
 * @creteTime 2017-12-12
 */
public interface IBaseApiManagement extends IBaseCommon<BaseApiManagementEntity_HI> {

	Pagination<BaseApiManagementLookupValues_HI_RO> findBaseApiManagementPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
