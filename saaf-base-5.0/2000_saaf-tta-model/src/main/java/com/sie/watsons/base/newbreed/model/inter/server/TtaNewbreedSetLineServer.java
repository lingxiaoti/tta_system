package com.sie.watsons.base.newbreed.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.newbreed.model.entities.TtaNewbreedSetLineEntity_HI;
import com.sie.watsons.base.newbreed.model.inter.ITtaNewbreedSetLine;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ttaNewbreedSetLineServer")
public class TtaNewbreedSetLineServer extends BaseCommonServer<TtaNewbreedSetLineEntity_HI> implements ITtaNewbreedSetLine {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedSetLineServer.class);

	@Autowired
	private ViewObject<TtaNewbreedSetLineEntity_HI> ttaNewbreedSetLineDAO_HI;

	public TtaNewbreedSetLineServer() {
		super();
	}

	/**
	 * 新增&修改新品种行信息
	 *
	 * @param paramsJSON 对象属性的JSON格式
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 * @throws Exception 抛出异常
	 */
	@Override
	public List<TtaNewbreedSetLineEntity_HI> saveOrUpdateTtaNewbreedSetLineInfo(JSONArray paramsJSON, Integer userId,Integer newbreedSetId,Integer deleteFlag) throws Exception {
		List<TtaNewbreedSetLineEntity_HI>  list= new ArrayList<TtaNewbreedSetLineEntity_HI>();
		for(int i=0;i<paramsJSON.size();i++){
			TtaNewbreedSetLineEntity_HI ttaNewbreedSetLineEntity = SaafToolUtils.setEntity(TtaNewbreedSetLineEntity_HI.class, (JSONObject)paramsJSON.get(i), ttaNewbreedSetLineDAO_HI, userId);
			ttaNewbreedSetLineEntity.setDeleteFlag(deleteFlag);
			if(null != newbreedSetId){
				ttaNewbreedSetLineEntity.setNewbreedSetId(newbreedSetId);
			}
			list.add(ttaNewbreedSetLineEntity);

		}
		ttaNewbreedSetLineDAO_HI.saveOrUpdateAll(list);
		return  list;
	}

}
