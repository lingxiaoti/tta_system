package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePdaInvCfgEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BasePdaInvCfgUserWarehouse_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.sql.SQLException;

public interface IBasePdaInvCfg extends IBaseCommon<BasePdaInvCfgEntity_HI> {

	Pagination<BasePdaInvCfgUserWarehouse_HI_RO> findPdaInvROPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    String basePdaInvCfgSync(JSONObject queryParamJSON) throws SQLException;
}
