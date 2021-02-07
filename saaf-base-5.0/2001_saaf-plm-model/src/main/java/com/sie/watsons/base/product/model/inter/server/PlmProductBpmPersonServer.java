package com.sie.watsons.base.product.model.inter.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaNonObInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierQaNonObInfo;
import com.sie.watsons.base.product.model.entities.PlmProductBpmPersonEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductBpmPersonEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductBpmPerson;
import com.sie.watsons.base.product.model.inter.IPlmProductDrugfile;
import com.sie.watsons.base.product.model.inter.IPlmProductHead;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmProductBpmPersonServer")
public class PlmProductBpmPersonServer extends
		BaseCommonServer<PlmProductBpmPersonEntity_HI> implements
		IPlmProductBpmPerson {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductBpmPersonServer.class);
	@Autowired
	private ViewObject<BasePersonEntity_HI> basePersonDAO_HI;

	@Autowired
	private ViewObject<PlmProductHeadEntity_HI> productHeadDAO_HI;

	@Autowired
	private IPlmProductHead headServer;
	@Autowired
	private ViewObject<BaseUsersEntity_HI> baseUser_HI;

	@Autowired
	private BaseViewObject<BaseUsersPerson_HI_RO> baseUsersPersonDAO_HI_RO;

	@Autowired
	private ViewObject<PlmProductBpmPersonEntity_HI> plmProductBpmPersonDAO_HI;

	@Autowired
	private BaseViewObject<PlmProductBpmPersonEntity_HI_RO> plmProductBpmPersonEntity_HI_RO;
	@Autowired
	private IPlmProductDrugfile drugfileServer;

	@Autowired
	private IPlmSupplierQaNonObInfo plmSupplierQaNonObInfo;

	public PlmProductBpmPersonServer() {
		super();
	}

	@Override
	public Pagination<PlmProductBpmPersonEntity_HI_RO> FindProductProcessList(
			JSONObject param, Integer pageIndex, Integer pageRows) {

		StringBuffer query = new StringBuffer();
		query.append(PlmProductBpmPersonEntity_HI_RO.QUERY_PERSON);
		Map<String, Object> params = new HashMap<String, Object>();
		param.put("createdBy", param.getInteger("varUserId"));
		SaafToolUtils.parperHbmParam(PlmProductHeadEntity_HI_RO.class, param,
				query, params);
		query.append(" order by person.CREATION_DATE desc ");
		Pagination<PlmProductBpmPersonEntity_HI_RO> pagination = plmProductBpmPersonEntity_HI_RO
				.findPagination(query, params, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<BaseUsersPerson_HI_RO> findProccessUsers(
			JSONObject queryParamJSON) {
		List<BaseUsersPerson_HI_RO> resultList = new ArrayList<BaseUsersPerson_HI_RO>();
		String assignFlag = queryParamJSON.getString("userdesc");
		Integer userId = queryParamJSON.getInteger("varUserId");
		JSONObject jo = new JSONObject();
		jo.put("name", assignFlag);
		jo.put("createdBy", userId);
		List<PlmProductBpmPersonEntity_HI> li = this.findList(jo);
		String userlist = "";
		if (li.size() > 0) {
			PlmProductBpmPersonEntity_HI pobj = li.get(0);
			userlist = pobj.getUseridList();

		}
		String uli[] = userlist.split(",");
		for (String ul : uli) {
			JSONObject s = new JSONObject();
			s.put("createdBy", new Integer(ul));
			JSONObject bu = ResultUtils.getUserInfoForCreatedBy(s);
			// BaseUsersEntity_HI bu = baseUser_HI.getById(new Integer(ul));
			BaseUsersPerson_HI_RO r = new BaseUsersPerson_HI_RO();
			r.setUserId(bu.getInteger("createdBy"));
			r.setUserName(bu.getString("userName"));
			r.setUserFullName(bu.getString("userFullName"));
			r.setPersonName(bu.getString("userFullName"));
			// r.setPersonId(bu.getPersonId().toString());
			r.setUserType(bu.getString("userType"));
			resultList.add(r);
		}

		return resultList;
	}

	@Override
	public List<BaseUsersPerson_HI_RO> findProccessUsersByuserId(
			JSONObject queryParamJSON) {
		List<BaseUsersPerson_HI_RO> personList = new ArrayList<BaseUsersPerson_HI_RO>();
		Integer userId = 1;
		if (queryParamJSON.containsKey("userId")) {
			userId = queryParamJSON.getInteger("userId");
		}
		if (queryParamJSON.containsKey("isdept")) {
			JSONObject jo = new JSONObject();
			List<PlmProductHeadEntity_HI> li = headServer.findList(jo);
			PlmProductHeadEntity_HI r = li.get(0);
			r.setNextCheckperson(userId.toString());
			headServer.update(r);
		}
		JSONObject b = new JSONObject();
		b.put("userId", userId);
		JSONObject jto = ResultUtils.getUserInfoByUserId(b);
		String jo2 = jto.toJSONString().replace("\\", "").replace("\"[", "[")
				.replace("]\"", "]");
		JSONObject js = JSONObject.parseObject(jo2);
		JSONArray data = js.getJSONArray("data");
		if (data.size() == 0) {
			return personList;
		}

		for (int i = 0; i < data.size(); i++) {
			JSONObject cujo = data.getJSONObject(i);
			String uId = cujo.getString("userId");
			String userType = cujo.getString("userType");
			String userName = cujo.getString("userName");
			BaseUsersPerson_HI_RO rw = new BaseUsersPerson_HI_RO();
			rw.setUserId(new Integer(uId));
			rw.setUserName(userName);
			rw.setUserType(userType);
			personList.add(rw);

		}
		return personList;
	}

	@Override
	public List<BaseUsersPerson_HI_RO> findCurenPerson(JSONObject queryParamJSON) {
		List<BaseUsersPerson_HI_RO> personList = new ArrayList<BaseUsersPerson_HI_RO>();
		JSONObject jt = new JSONObject();
		PlmProductHeadEntity_HI one = new PlmProductHeadEntity_HI(); // 得到当前登录人
		Integer userId = 1; // 存放在第一条数据
		List<PlmProductHeadEntity_HI> li = headServer.findList(jt);
		if (li.size() > 0) {
			one = li.get(0);

		}
		if (one.getNextCheckperson() != null) {
			userId = new Integer(one.getNextCheckperson());
		}
		JSONObject b = new JSONObject();
		b.put("userId", userId);
		JSONObject jto = ResultUtils.getUserInfoByUserId(b);
		String jo2 = jto.toJSONString().replace("\\", "").replace("\"[", "[")
				.replace("]\"", "]");
		JSONObject js = JSONObject.parseObject(jo2);
		JSONArray data = js.getJSONArray("data");
		if (data.size() == 0) {
			return personList;
		}

		for (int i = 0; i < data.size(); i++) {
			JSONObject cujo = data.getJSONObject(i);
			String uId = cujo.getString("userId");
			String userType = cujo.getString("userType");
			String userName = cujo.getString("userName");
			BaseUsersPerson_HI_RO rw = new BaseUsersPerson_HI_RO();
			rw.setUserId(new Integer(uId));
			rw.setUserName(userName);
			rw.setUserType(userType);
			personList.add(rw);

		}
		return personList;
	}

	//
	@Override
	public List<BaseUsersPerson_HI_RO> findUserProcess(JSONObject queryParamJSON) {
		List<BaseUsersPerson_HI_RO> personList = new ArrayList<BaseUsersPerson_HI_RO>();
		Integer id = queryParamJSON.getInteger("id");
		String types = queryParamJSON.getString("types");
		if (id != null) {
			if (types.equals("purchase")) {
				PlmSupplierQaNonObInfoEntity_HI qaob = plmSupplierQaNonObInfo
						.getById(id);
				String userName = qaob.getPurchaseApprovalRole();
				Integer userId = qaob.getPurchaseApprovalUser();
				String userType = "13";

				BaseUsersPerson_HI_RO rw = new BaseUsersPerson_HI_RO();
				rw.setUserId(userId);
				rw.setUserType(userType);
				rw.setUserName(userName);
				personList.add(rw);
			} else {
				PlmSupplierQaNonObInfoEntity_HI qaob = plmSupplierQaNonObInfo
						.getById(id);
				String userName = qaob.getQaApprovalRole();
				Integer userId = qaob.getQaApprovalUser();
				String userType = "75";

				BaseUsersPerson_HI_RO rw = new BaseUsersPerson_HI_RO();
				rw.setUserId(userId);
				rw.setUserType(userType);
				rw.setUserName(userName);
				personList.add(rw);
			}
		}
		return personList;
	}
}
