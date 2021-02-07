package com.sie.watsons.base.product.model.inter.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.product.model.entities.readonly.PlmDataAclLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmDataAclHeaderEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmDataAclLineEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmDataAclHeaderEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmDataAclHeader;
import com.sie.watsons.base.product.model.inter.IPlmDataAclLine;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmDataAclHeaderServer")
public class PlmDataAclHeaderServer extends
		BaseCommonServer<PlmDataAclHeaderEntity_HI> implements
		IPlmDataAclHeader {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDataAclHeaderServer.class);

	@Autowired
	private ViewObject<PlmDataAclHeaderEntity_HI> plmDataAclHeaderDAO_HI;

	@Autowired
	private IPlmDataAclLine plmDataAclLineServer;

	@Autowired
	private BaseViewObject<PlmDataAclHeaderEntity_HI_RO> plmDataAclHeaderEntity_HI_RO;

	@Autowired
	private BaseViewObject<PlmDataAclLineEntity_HI_RO> plmDataAclLineEntity_HI_RO;

	public PlmDataAclHeaderServer() {
		super();
	}

	@Override
	public Pagination<PlmDataAclHeaderEntity_HI_RO> FindDataAclInfo(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmDataAclHeaderEntity_HI_RO.QUERY);
		Map<String, Object> params = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmDataAclHeaderEntity_HI_RO.class, param,
				query, params);
		query.append(" order by CREATION_DATE desc ");
		Pagination<PlmDataAclHeaderEntity_HI_RO> pagination = plmDataAclHeaderEntity_HI_RO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public JSONObject DataAclInfo(JSONObject param) {

		StringBuffer query = new StringBuffer(
				PlmDataAclHeaderEntity_HI_RO.QUERY);
		Integer queryId = null;
		PlmDataAclHeaderEntity_HI_RO productEntity = new PlmDataAclHeaderEntity_HI_RO();
		List<PlmDataAclLineEntity_HI_RO> headlist = new ArrayList<PlmDataAclLineEntity_HI_RO>();
		if (param.containsKey("deptId")) {
			queryId = param.getInteger("deptId");
			query.append(" and dept_id = :deptId order by dept_id");
			Map<String, Object> paramProduct = new HashMap<>();
			paramProduct.put("deptId", queryId);
			productEntity = plmDataAclHeaderEntity_HI_RO.get(query,
					paramProduct);
		}
		if (param.containsKey("headId")) {
			queryId = param.getInteger("headId");
			query.append(" and head_id = :headId order by dept_id");
			Map<String, Object> paramProduct = new HashMap<>();
			paramProduct.put("headId", queryId);
			productEntity = plmDataAclHeaderEntity_HI_RO.get(query,
					paramProduct);
		}

		JSONObject productJson = JSONObject.parseObject(JSONObject
				.toJSONString(productEntity));
		JSONObject result = new JSONObject();
		result.put("headInfo", productJson);

		JSONObject headparam = new JSONObject();
		if (productEntity != null) {
			if (productEntity.getHeadId() != null) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("headId",productEntity.getHeadId());
				StringBuffer querysql = new StringBuffer(
						PlmDataAclLineEntity_HI_RO.query);
				querysql.append(" and head_id= :headId order by head_id,acl_type,group_code");
				headlist=plmDataAclLineEntity_HI_RO.findList(querysql,map);

			}
		}
   if(headlist.size()>0)
   {
     for(int i=0;i<headlist.size();i++)
	 {
		 PlmDataAclLineEntity_HI_RO l=headlist.get(i);
		 String type=l.getAclType();
		 if(type.equals("QUERY"))
		 {
             l.setAclTypeName("查询权限");
		 }else if(type.equals("NEW")){
			 l.setAclTypeName("新增权限");
		 }else if(type.equals("UPDATE"))
		 {
		 	l.setAclTypeName("修改权限");
		 }
	 }
   }
		result.put("linelist", headlist);

		return result;
	}

	@Override
	public JSONObject saveHeadInfo(JSONObject param) {
		return null;
	}
}
