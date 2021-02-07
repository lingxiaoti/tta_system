package com.sie.saaf.base.sso.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.sso.model.entities.BaseFunctionCollectionUserEntity_HI;

import java.util.List;

public interface IBaseFunctionCollectionUser {

	List<BaseFunctionCollectionUserEntity_HI> findBaseFunctionCollectionUserInfo(JSONObject queryParamJSON);

	List<BaseFunctionCollectionUserEntity_HI> saveBaseFunctionCollectionUserInfo(String functionCollectionIds[], Integer userId);

	void delete(String[] ids);
}
