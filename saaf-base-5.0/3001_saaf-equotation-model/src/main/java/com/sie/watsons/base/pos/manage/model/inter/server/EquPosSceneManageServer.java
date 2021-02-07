package com.sie.watsons.base.pos.manage.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.manage.model.entities.EquPosSceneManageEntity_HI;
import com.sie.watsons.base.pos.manage.model.entities.readonly.EquPosSceneManageEntity_HI_RO;
import com.sie.watsons.base.pos.manage.model.inter.IEquPosSceneManage;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;

import com.yhg.hibernate.core.paging.Pagination;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("equPosSceneManageServer")
public class EquPosSceneManageServer implements IEquPosSceneManage {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSceneManageServer.class);
	@Autowired
	private ViewObject<EquPosSceneManageEntity_HI> equPosSceneManageDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSceneManageEntity_HI_RO> equPosSceneManageDAO_HI_RO;
	public EquPosSceneManageServer() {
		super();
	}


	@Override
	public JSONObject saveDemo(String params) {

		EquPosSceneManageEntity_HI a = equPosSceneManageDAO_HI.getById(-1);
		EquPosSceneManageEntity_HI b = new EquPosSceneManageEntity_HI();
		b.setOperatorUserId(1);
		b.setSceneType("a");
		equPosSceneManageDAO_HI.save(b);
		return null;
	}

	@Override
	public Pagination<EquPosSceneManageEntity_HI_RO> findEquPosSceneManageInfo(String params,Integer pageIndex,Integer pageRows) {
		try {
			JSONObject jsonParam = JSONObject.parseObject(params);
			LOGGER.info("------jsonParam------" + jsonParam.toString());
			StringBuffer queryString = new StringBuffer(
					EquPosSceneManageEntity_HI_RO.QUERY_SCENE_SQL);
			Map<String, Object> map = new HashMap<String, Object>();
			SaafToolUtils.parperParam(jsonParam, "T.SCENE_TYPE", "sceneType", queryString, map, "=");
			// 排序
			queryString.append(" order by t.creation_date desc");

			Pagination<EquPosSceneManageEntity_HI_RO> sceneManageList = equPosSceneManageDAO_HI_RO
					.findPagination(queryString, map, pageIndex, pageRows);

			return sceneManageList;
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询异常：" + e);
			return null;
		}
	}

	@Override
	public void saveSceneManage(JSONObject jsonObject, int userId) {
		EquPosSceneManageEntity_HI manageEntity = JSON.parseObject(jsonObject.toJSONString(), EquPosSceneManageEntity_HI.class);
		manageEntity.setOperatorUserId(userId);
		manageEntity.setSceneStatus("20");
		List<EquPosSceneManageEntity_HI> manageList = getSceneManageLine(jsonObject);
		List<EquPosSceneManageEntity_HI> saveList = new ArrayList<>();
		for (EquPosSceneManageEntity_HI entityHi : manageList) {
			entityHi.setSceneStatus("30");
			entityHi.setOperatorUserId(userId);
			saveList.add(entityHi);
		}
		saveList.add(manageEntity);
		equPosSceneManageDAO_HI.save(saveList);
	}

	@Override
	public String deleteSceneManage(JSONObject jsonObject, int userId) {
		Integer sceneManageId =jsonObject.getInteger("sceneManageId");
		EquPosSceneManageEntity_HI manageEntity =equPosSceneManageDAO_HI.getById(sceneManageId);
		if(manageEntity!=null){
			equPosSceneManageDAO_HI.delete(manageEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}

	public List<EquPosSceneManageEntity_HI> getSceneManageLine(JSONObject jsonObject) {
		String sceneType =jsonObject.getString("sceneType");
		Map map = new HashMap();
		map.put("sceneType",sceneType);
		List<EquPosSceneManageEntity_HI> manageList = equPosSceneManageDAO_HI.findList("from  EquPosSceneManageEntity_HI where sceneType = :sceneType and sceneStatus = '20'",map);
		return manageList;
	}

	@Override
	public String sumbitSceneManage(JSONObject jsonObject, int userId) {
		Integer sceneManageId =jsonObject.getInteger("sceneManageId");
		String sceneType =jsonObject.getString("sceneType");
		Map map = new HashMap();
		map.put("sceneType",sceneType);
		List<EquPosSceneManageEntity_HI> manageList = getSceneManageLine(jsonObject);
		List<EquPosSceneManageEntity_HI> saveList = new ArrayList<>();
		for (EquPosSceneManageEntity_HI entityHi : manageList) {
			entityHi.setSceneStatus("30");
			entityHi.setOperatorUserId(userId);
			saveList.add(entityHi);
		}
		EquPosSceneManageEntity_HI manageEntity =equPosSceneManageDAO_HI.getById(sceneManageId);
		manageEntity.setOperatorUserId(userId);
		manageEntity.setSceneStatus("20");
		saveList.add(manageEntity);
		equPosSceneManageDAO_HI.save(saveList);
		return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, manageEntity).toString();
	}

	@Override
	public String findSceneManageLine(JSONObject jsonObject, int userId) {
		List<EquPosSceneManageEntity_HI> manageList = getSceneManageLine(jsonObject);
		return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, manageList.size()).toString();
	}
}
