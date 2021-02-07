package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BasePdaRoleCfgEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.sql.SQLException;

public interface IBasePdaRoleCfg extends IBaseCommon<BasePdaRoleCfgEntity_HI> {


    Pagination findPaginationByParams(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    String basePdaRoleCfgSync(JSONObject queryParamJSON) throws SQLException;
}
