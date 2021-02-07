package com.sie.watsons.base.usergroupdeptbrand.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly.TtaUserGroupDeptBrandEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.TtaUserGroupDeptBrandEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaUserGroupDeptBrand extends IBaseCommon<TtaUserGroupDeptBrandEntity_HI>{

    Pagination<TtaUserGroupDeptBrandEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    JSONObject saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    JSONObject saveOrUpdateRxpire(JSONObject paramsJSON, int userId) throws Exception;

    JSONObject saveOrUpdatePower(JSONObject paramsJSON, int userId) throws Exception;

    Pagination<TtaUserGroupDeptBrandEntity_HI_RO> findUser(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
