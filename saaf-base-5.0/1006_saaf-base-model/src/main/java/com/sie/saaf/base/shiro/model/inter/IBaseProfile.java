package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseProfileEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.sql.SQLException;
import java.util.List;

/**
 * 接口：对base_profile表进行CRUD操作
 * @author ZhangJun
 * @creteTime 2017/12/14
 */
public interface IBaseProfile extends IBaseCommon<BaseProfileEntity_HI> {


	List<JSONObject> findProfileSqlDatas(JSONObject queryParamJSON) throws SQLException;

	List<String> findProfileSqlDatasByResponsibilityId(JSONObject queryParamJSON, List<Integer> respIdList) throws SQLException;
}
