package com.sie.saaf.base.sso.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.sso.model.entities.BaseFunctionCollectionEntity_HI;
import com.sie.saaf.base.sso.model.entities.BaseFunctionCollectionUserEntity_HI;
import com.sie.saaf.base.sso.model.inter.IBaseFunctionCollectionUser;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component("baseFunctionCollectionUserServer")
public class BaseFunctionCollectionUserServer implements IBaseFunctionCollectionUser {
	@Autowired
	private ViewObject<BaseFunctionCollectionUserEntity_HI> baseFunctionCollectionUserDAO_HI;

	@Autowired
	private ViewObject<BaseFunctionCollectionEntity_HI> baseFunctionCollectionDAO_HI;


	public BaseFunctionCollectionUserServer() {
		super();
	}

	public List<BaseFunctionCollectionUserEntity_HI> findBaseFunctionCollectionUserInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<BaseFunctionCollectionUserEntity_HI> findListResult = baseFunctionCollectionUserDAO_HI.findList("from BaseFunctionCollectionUserEntity_HI", queryParamMap);
		return findListResult;
	}


	@Override
	public List<BaseFunctionCollectionUserEntity_HI> saveBaseFunctionCollectionUserInfo(String functionCollectionIds[], Integer userId) {
		if (functionCollectionIds==null || functionCollectionIds.length==0)
			return Collections.emptyList();
		List<BaseFunctionCollectionUserEntity_HI> list=new ArrayList<>();
		for (String functionCollectionId:functionCollectionIds){
			BaseFunctionCollectionEntity_HI functionCollection= baseFunctionCollectionDAO_HI.getById(Integer.valueOf(functionCollectionId));
			Assert.notNull(functionCollection,"快捷菜单不存在id[#]".replace("#",functionCollectionId));
			Map<String,Object> map=new HashMap<>();
			map.put("userId",userId);
			map.put("functionCollectionId",Integer.valueOf(functionCollectionId));
			List<BaseFunctionCollectionUserEntity_HI> collectionList= baseFunctionCollectionUserDAO_HI.findByProperty(map);
			if (collectionList.size()>0)
				continue;
			BaseFunctionCollectionUserEntity_HI instance=new BaseFunctionCollectionUserEntity_HI();
			instance.setOperatorUserId(userId);
			instance.setUserId(userId);
			instance.setFunctionCollectionId(Integer.valueOf(functionCollectionId));
			baseFunctionCollectionUserDAO_HI.save(instance);
			list.add(instance);
		}
		return list;
	}


	@Override
	public void delete(String[] ids) {
		if (ids == null || ids.length == 0)
			return;
		for (String id : ids) {
			if (StringUtils.isBlank(id))
				continue;
			BaseFunctionCollectionUserEntity_HI instance=baseFunctionCollectionUserDAO_HI.getById(Integer.valueOf(id));
			Assert.notNull(instance,"数据id[#]不存在".replace("#",id));
			baseFunctionCollectionUserDAO_HI.delete(instance);
		}
	}

}
