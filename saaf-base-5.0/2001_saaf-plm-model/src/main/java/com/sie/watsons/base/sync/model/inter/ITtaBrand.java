package com.sie.watsons.base.sync.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.sync.model.entities.TtaBrandEntity_HI;

import java.sql.SQLException;

import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaBrand extends IBaseCommon<TtaBrandEntity_HI>{
   void find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws SQLException;

}
