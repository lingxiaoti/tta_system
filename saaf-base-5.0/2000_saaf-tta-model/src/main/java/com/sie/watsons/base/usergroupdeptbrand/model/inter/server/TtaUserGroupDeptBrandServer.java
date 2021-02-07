package com.sie.watsons.base.usergroupdeptbrand.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly.TtaUserGroupDeptBrandEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.TtaUserGroupDeptBrandEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.usergroupdeptbrand.model.inter.ITtaUserGroupDeptBrand;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaUserGroupDeptBrandServer")
public class TtaUserGroupDeptBrandServer extends BaseCommonServer<TtaUserGroupDeptBrandEntity_HI> implements ITtaUserGroupDeptBrand{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaUserGroupDeptBrandServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaUserGroupDeptBrandEntity_HI> ttaUserGroupDeptBrandDAO_HI;

	@Autowired
	private BaseViewObject<TtaUserGroupDeptBrandEntity_HI_RO> ttaUserGroupDeptBrandDAO_HI_RO;

	@Autowired
	private ViewObject<BaseUsersEntity_HI> baseUsersDAO_HI;

	public TtaUserGroupDeptBrandServer() {
		super();
	}

	@Override
	public Pagination<TtaUserGroupDeptBrandEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		SaafToolUtils.validateJsonParms(queryParamJSON,"userId");
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(TtaUserGroupDeptBrandEntity_HI_RO.TTA_GROUP_LIST);
		SaafToolUtils.parperParam(queryParamJSON, "tug.\"GROUP\"", "group", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.group_Desc", "groupDesc", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.dept_desc", "deptDesc", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.brand_cn", "brandCn", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.brand_en", "brandEn", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.dept", "dept", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.brand_code", "brandCode", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.user_id", "userId", sql, paramsMap, "=");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tug.user_group_dept_brand_id desc", false);

		Pagination<TtaUserGroupDeptBrandEntity_HI_RO> findList = ttaUserGroupDeptBrandDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public Pagination<TtaUserGroupDeptBrandEntity_HI_RO> findUser(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(TtaUserGroupDeptBrandEntity_HI_RO.TTA_GROUP_USER_LIST);
		SaafToolUtils.parperParam(queryParamJSON, "tug.\"GROUP\"", "group", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.group_Desc", "groupDesc", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.dept_desc", "deptDesc", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.brand_cn", "brandCn", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tug.dept", "dept", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "bu.user_name", "userName", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "bu.user_full_name", "userFullName", sql, paramsMap, "fulllike");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tug.user_group_dept_brand_id desc", false);

		Pagination<TtaUserGroupDeptBrandEntity_HI_RO> findList = ttaUserGroupDeptBrandDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public JSONObject saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		JSONArray jsonA = paramsJSON.getJSONArray("userGroupDeptBrand") ;
		String  deptCode = "";
		String  groupCode ="";
		String  brandCn ="";
		if("new".equals(paramsJSON.getString("flag"))){
			SaafToolUtils.validateJsonParms(paramsJSON,"userId","dataType");
			//校验数据类型的准确性
			StringBuffer sql = new StringBuffer();
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			sql.append(TtaUserGroupDeptBrandEntity_HI_RO.TTA_DATA_CHECK);
			SaafToolUtils.parperParam(paramsJSON, "bu.user_id", "userId", sql, paramsMap, "=");
			SaafToolUtils.parperParam(paramsJSON, "bu.data_type", "dataType", sql, paramsMap, "=");
			TtaUserGroupDeptBrandEntity_HI_RO ttaUserGroupDeptBrandEntityHiRo = ttaUserGroupDeptBrandDAO_HI_RO.get(sql, paramsMap);
			int counts = ttaUserGroupDeptBrandEntityHiRo.getCounts().intValue();
			if(0 == counts){
				throw new IllegalArgumentException("无法保存，权限类型 不一致,请刷新，或者保存左边的数据");
			}
		}
		JSONObject jsonObject = new JSONObject();
		HashSet hashSet = new HashSet<String>();
		List<TtaUserGroupDeptBrandEntity_HI> objects = new ArrayList<>();
		Date endTime = null ;

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE));
		Date endDate = dft.parse(dft.format(date.getTime()));

		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();


		for(int i = 0;i<jsonA.size();i++) {
			TtaUserGroupDeptBrandEntity_HI ttaUserGroupDeptBrandEntity_HI = SaafToolUtils.setEntity(TtaUserGroupDeptBrandEntity_HI.class, (JSONObject) jsonA.get(i), ttaUserGroupDeptBrandDAO_HI, userId);
			if ("new".equals(paramsJSON.getString("flag"))) {
				ttaUserGroupDeptBrandEntity_HI.setUserId(paramsJSON.getInteger("userId"));
				ttaUserGroupDeptBrandEntity_HI.setDept(((JSONObject) jsonA.get(i)).getString("deptCode"));
				ttaUserGroupDeptBrandEntity_HI.setGroup(((JSONObject) jsonA.get(i)).getString("groupCode"));
				ttaUserGroupDeptBrandEntity_HI.setDataType(paramsJSON.getString("dataType"));
				ttaUserGroupDeptBrandEntity_HI.setStartTime(new Date());
			} else if ("current".equals(paramsJSON.getString("flag"))) {
				if (StringUtils.isBlank(paramsJSON.getString("date"))) {
					throw new IllegalArgumentException("失效时间不能为空");
				}
				ttaUserGroupDeptBrandEntity_HI.setEndTime(paramsJSON.getDate("date"));
			}
			//校验权限类型
			endTime = SaafToolUtils.isNullOrEmpty(ttaUserGroupDeptBrandEntity_HI.getEndTime()) ? new Date() : ttaUserGroupDeptBrandEntity_HI.getEndTime();
			if (endTime.compareTo(endDate) >= 0) {
				hashSet.add(ttaUserGroupDeptBrandEntity_HI.getDataType());
			}

			sql.setLength(0);
			sql.append(TtaUserGroupDeptBrandEntity_HI_RO.TTA_GROUP_LIST);
			if (endTime.compareTo(endDate) >= 0) {
					//校验是否重复
					SaafToolUtils.parperParam((JSONObject) jsonA.get(i), "tug.\"GROUP\"", "groupCode", sql, paramsMap, "=");
					SaafToolUtils.parperParam((JSONObject) jsonA.get(i), "tug.dept", "deptCode", sql, paramsMap, "=");
					SaafToolUtils.parperParam((JSONObject) jsonA.get(i), "tug.\"GROUP\"", "group", sql, paramsMap, "=");
					SaafToolUtils.parperParam((JSONObject) jsonA.get(i), "tug.dept", "dept", sql, paramsMap, "=");
					SaafToolUtils.parperParam((JSONObject) jsonA.get(i), "tug.brand_cn", "brandCn", sql, paramsMap, "=");
					sql.append(" and tug.user_id = :userId ");
					sql.append(" and nvl(tug.start_time,sysdate) < sysdate");
					sql.append(" and nvl(tug.end_time,sysdate) >= sysdate");
					if (!SaafToolUtils.isNullOrEmpty(ttaUserGroupDeptBrandEntity_HI.getUserGroupDeptBrandId())) {
						sql.append(" and tug.user_group_dept_brand_id != " + ttaUserGroupDeptBrandEntity_HI.getUserGroupDeptBrandId());
					}
					paramsMap.put("userId", paramsJSON.getInteger("userId"));
					TtaUserGroupDeptBrandEntity_HI_RO ttaUserGroupDeptBrandEntityHiRo = ttaUserGroupDeptBrandDAO_HI_RO.get(sql, paramsMap);
					if (!SaafToolUtils.isNullOrEmpty(ttaUserGroupDeptBrandEntityHiRo)) {

						deptCode = ((JSONObject) jsonA.get(i)).getString("dept");
						if (SaafToolUtils.isNullOrEmpty(deptCode)) {
							deptCode = ((JSONObject) jsonA.get(i)).getString("deptCode");
						}
						groupCode = ((JSONObject) jsonA.get(i)).getString("group");
						if (SaafToolUtils.isNullOrEmpty(groupCode)) {
							groupCode = ((JSONObject) jsonA.get(i)).getString("groupCode");
						}
						brandCn = ((JSONObject) jsonA.get(i)).getString("brandCn");
						throw new IllegalArgumentException("不能重复添加,重复的是" + deptCode + "," + groupCode + "," + brandCn);
					}

			}

			objects.add(ttaUserGroupDeptBrandEntity_HI);
		}
		if(hashSet.size()>1){
			throw new IllegalArgumentException("当前页面的权限类型不一致");
		}
		ttaUserGroupDeptBrandDAO_HI.saveOrUpdateAll(objects);
		jsonObject.put("list",objects);
		return jsonObject;
	}

	@Override
	public JSONObject saveOrUpdateRxpire(JSONObject paramsJSON, int userId) throws Exception {
		SaafToolUtils.validateJsonParms(paramsJSON,"flag","userId");
		JSONObject jsonObject = new JSONObject();
		List<TtaUserGroupDeptBrandEntity_HI> objects = new ArrayList<>();
		String flag = paramsJSON.getString("flag");
		Integer userId1 = paramsJSON.getInteger("userId");
		if(StringUtils.isBlank(paramsJSON.getString("date"))){
			throw new IllegalArgumentException("失效时间不能为空");
		}
		if ("all".equals(flag)) {
			ttaUserGroupDeptBrandDAO_HI.executeSqlUpdate("update  tta_user_group_dept_brand t set \n" +
					"t.last_Updated_By=" +userId+","+"t.last_Update_Date = sysdate,\n" +
					"t.end_Time = to_date('"+paramsJSON.getString("date")+"','yyyy-mm-dd') where  t.user_Id =" + userId1);
		} else {
			JSONArray jsonA = paramsJSON.getJSONArray("userGroupDeptBrand") ;
			for(int i = 0;i<jsonA.size();i++){
				TtaUserGroupDeptBrandEntity_HI ttaUserGroupDeptBrandEntity_HI = SaafToolUtils.setEntity(TtaUserGroupDeptBrandEntity_HI.class,(JSONObject)jsonA.get(i),ttaUserGroupDeptBrandDAO_HI,userId);
				ttaUserGroupDeptBrandEntity_HI.setEndTime(paramsJSON.getDate("date"));
				objects.add(ttaUserGroupDeptBrandEntity_HI);
			}
			ttaUserGroupDeptBrandDAO_HI.saveOrUpdateAll(objects);
		}

		return jsonObject;
	}


	@Override
	public JSONObject saveOrUpdatePower(JSONObject paramsJSON, int userId) throws Exception {
		SaafToolUtils.validateJsonParms(paramsJSON,"userId","toUserId");
		Integer userId1 = paramsJSON.getInteger("userId");//转移方
		Integer toUserId = paramsJSON.getInteger("toUserId");//接收方
		//转移方查询
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap2 = new HashMap<String, Object>();
		sql.append(TtaUserGroupDeptBrandEntity_HI_RO.TTA_GROUP_LIST);
		SaafToolUtils.parperParam(paramsJSON, "tug.user_id", "userId", sql, paramsMap2, "=");
		sql.append(" and nvl(tug.end_time,sysdate) >= sysdate  ");
		sql.append(" and rownum =1 ");
		TtaUserGroupDeptBrandEntity_HI_RO ttaUserGroupDeptBrandEntityH = ttaUserGroupDeptBrandDAO_HI_RO.get(sql, paramsMap2);
		if (SaafToolUtils.isNullOrEmpty(ttaUserGroupDeptBrandEntityH) ||
				SaafToolUtils.isNullOrEmpty(ttaUserGroupDeptBrandEntityH.getDataType())) {
			throw new IllegalArgumentException("转移方权限类型为空,不能一键转移");
		}
		//接收方查询
		JSONObject jsonObject = new JSONObject();
		StringBuffer sqlToUser = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sqlToUser.append(TtaUserGroupDeptBrandEntity_HI_RO.TTA_USER);
		SaafToolUtils.parperParam(paramsJSON, "tug.user_id", "toUserId", sqlToUser, paramsMap, "=");
		TtaUserGroupDeptBrandEntity_HI_RO ttaUserGroupDeptBrandEntityHiRo = ttaUserGroupDeptBrandDAO_HI_RO.get(sqlToUser, paramsMap);
		int counts = ttaUserGroupDeptBrandEntityHiRo.getCounts().intValue();
		//接收方权限不存在,直接转移,接收方权限存在,判断转移方和接收方的权限是否重复
		if(0 != counts){
			BaseUsersEntity_HI toUserEntity = baseUsersDAO_HI.getById(paramsJSON.getInteger("toUserId"));
			String dataType = toUserEntity.getDataType();
			if (!ttaUserGroupDeptBrandEntityH.getDataType().equals(dataType)){
				throw new IllegalArgumentException("转移方的权限类型和接收方的权限类型不一致,权限无法转移");
			}
			//查转移方的权限
			/*StringBuffer tranferSql = new StringBuffer(TtaUserGroupDeptBrandEntity_HI_RO.TTA_GROUP_LIST);
			Map<String,Object> tranferMap = new HashMap<>();
			SaafToolUtils.parperParam(paramsJSON, "tug.user_id", "userId", tranferSql, tranferMap, "=");
			sql.append(" and nvl(tug.end_time,sysdate) >= sysdate  ");
			List<TtaUserGroupDeptBrandEntity_HI_RO> tranferList = ttaUserGroupDeptBrandDAO_HI_RO.findList(tranferSql, tranferMap);
			//查询接收方的权限
			StringBuffer recieveSql = new StringBuffer(TtaUserGroupDeptBrandEntity_HI_RO.TTA_GROUP_LIST);
			Map<String,Object> recieveMap = new HashMap<>();
			SaafToolUtils.parperParam(paramsJSON, "tug.user_id", "toUserId", tranferSql, recieveMap, "=");
			sql.append(" and nvl(tug.end_time,sysdate) >= sysdate  ");
			List<TtaUserGroupDeptBrandEntity_HI_RO> recieveList = ttaUserGroupDeptBrandDAO_HI_RO.findList(recieveSql, recieveMap);*/
			//throw new IllegalArgumentException("权限无法一键转移。原因：接收方的权限本身就存在");
		}
		//更新转移方
		ttaUserGroupDeptBrandDAO_HI.executeSqlUpdate("update  tta_user_group_dept_brand t set \n" +
				"t.last_Updated_By=" +userId+","+"t.last_Update_Date = sysdate,\n" +
				"t.user_Id ="+ toUserId + "  where nvl(t.end_time,sysdate) >= sysdate  and t.user_Id =" + userId1);
		//更新接收方
		ttaUserGroupDeptBrandDAO_HI.executeSqlUpdate("update  base_users t set \n" +
				"t.data_type='" +ttaUserGroupDeptBrandEntityH.getDataType()+"' where  t.user_Id =" + toUserId);
		return jsonObject;
	}

}
