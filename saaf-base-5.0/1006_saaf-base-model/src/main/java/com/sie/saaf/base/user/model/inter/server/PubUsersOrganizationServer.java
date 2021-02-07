package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.PubUsersOrganizationEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.PubUsersOrganizationEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.IPubUsersOrganization;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * 用户业务分配表
 * 
 *
 * @creteTime 2018-10-26
 */
@Component("pubUsersOrganizationServer")
public class PubUsersOrganizationServer extends BaseCommonServer<PubUsersOrganizationEntity_HI>
		implements IPubUsersOrganization {
	private static final Logger LOGGER = LoggerFactory.getLogger(PubUsersOrganizationServer.class);
	@Autowired
	private ViewObject<PubUsersOrganizationEntity_HI> pubUsersOrganizationDAO_HI;
	@Autowired
	private BaseViewObject<PubUsersOrganizationEntity_HI_RO> pubUsersOrganizationDAO_HI_RO;

	public PubUsersOrganizationServer() {
		super();
	}




	@Override
	public List<PubUsersOrganizationEntity_HI_RO> findListAssign(JSONObject queryParamJSON) {
		StringBuffer sb = new StringBuffer(PubUsersOrganizationEntity_HI_RO.QUERY_LIST);
		Map<String,Object> map = new HashMap<>();
		SaafToolUtils.parperParam(queryParamJSON,"borg.tree_type","treeType",sb,map,"=",false);
		SaafToolUtils.parperParam(queryParamJSON,"bporg.user_id","userId",sb,map,"=",false);
		List<PubUsersOrganizationEntity_HI_RO> list = pubUsersOrganizationDAO_HI_RO.findList(sb, map);
		return list;
	}

	@Override
	public void saveListAssign(JSONObject queryParamJSON) {
		Integer userId = queryParamJSON.getInteger("userId");
		JSONArray array = queryParamJSON.getJSONArray("menuData");
		Integer operatorUserId = queryParamJSON.getInteger("operatorUserId");
		//查询出旧数据，并删除
		StringBuffer sb = new StringBuffer(PubUsersOrganizationEntity_HI_RO.QUERY_DELETE_LIST);
		Map<String,Object> map = new HashMap<>();
		SaafToolUtils.parperParam(queryParamJSON,"borg.tree_type","treeType",sb,map,"=",false);
		SaafToolUtils.parperParam(queryParamJSON,"bporg.user_id","userId",sb,map,"=",false);
		List<PubUsersOrganizationEntity_HI_RO> listAssign = pubUsersOrganizationDAO_HI_RO.findList(sb, map);
		for(PubUsersOrganizationEntity_HI_RO entity : listAssign){
			pubUsersOrganizationDAO_HI.delete(entity.getUserOrgId());
		}
		//保存新数据
		ListIterator it = array.listIterator();
		while(it.hasNext()){
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(it.next()));
			Integer orgId =jsonObject.getInteger("menuId");
			PubUsersOrganizationEntity_HI saveEntity = new PubUsersOrganizationEntity_HI();
			saveEntity.setUserId(userId);	//
			saveEntity.setOrgId(orgId);
			saveEntity.setOperatorUserId(operatorUserId);
			pubUsersOrganizationDAO_HI.saveOrUpdate(saveEntity);
		}
	}

}
