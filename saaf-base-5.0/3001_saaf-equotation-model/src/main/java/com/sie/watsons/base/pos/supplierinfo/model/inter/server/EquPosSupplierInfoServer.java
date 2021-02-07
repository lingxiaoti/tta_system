package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosCredentialsAttachmentEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierCredentialsEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierInfoEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSupplierInfo;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.sun.javafx.collections.MappingChange;
import com.yhg.base.utils.DateUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("equPosSupplierInfoServer")
public class EquPosSupplierInfoServer extends BaseCommonServer<EquPosSupplierInfoEntity_HI> implements IEquPosSupplierInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierInfoServer.class);

	@Autowired
	private ViewObject<EquPosSupplierInfoEntity_HI> equPosSupplierInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierContactsEntity_HI> equPosSupplierContactsDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierInfoEntity_HI_RO> equPosSupplierInfoEntity_HI_RO;

	@Autowired
	private BaseViewObject<EquPosSupplierCredentialsEntity_HI_RO> equPosSupplierCredentialsEntity_HI_RO;

	@Autowired
	private BaseViewObject<EquPosCredentialsAttachmentEntity_HI_RO> equPosCredentialsAttachmentEntity_HI_RO;

	public EquPosSupplierInfoServer() {
		super();
	}


	/**
	 * 供应商基础信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierInfo(JSONObject queryParamJSON, Integer pageIndex,
									   Integer pageRows) {
		if(queryParamJSON.containsKey("deptCode")){
			if(!"0E".equals(queryParamJSON.getString("deptCode"))){
				queryParamJSON.remove("deptCode");
				queryParamJSON.put("deptCode_neq","0E");
			}
		}
		StringBuffer sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosSupplierInfoEntity_HI_RO> pagination = equPosSupplierInfoEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	@Override
	public JSONArray findSupplierScoreInfo(JSONObject paramsJONS ) {
		JSONArray returnArr = new JSONArray();

		List<EquPosSupplierInfoEntity_HI_RO> returnList = new ArrayList<>();
		Map<String, EquPosSupplierInfoEntity_HI_RO> conMap = new HashMap();
//查询除了质量审核以外的所有正常审核信息
		StringBuffer sql = new StringBuffer (EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_SCORE);
		Map<String, Object> map = new HashMap<>();
		map.put("supplierId",paramsJONS.getInteger("supplierId"));
		List<EquPosSupplierInfoEntity_HI_RO> pagination = equPosSupplierInfoEntity_HI_RO.findList(sql, map );
		for (EquPosSupplierInfoEntity_HI_RO entityHiRo : pagination) {
			conMap.put(entityHiRo.getAtType(), entityHiRo);
		}

//查询除了质量审核以外的所有导入审核信息( 只需要查询信用审核与信用审核因为其他的都只有导入功能)
		StringBuffer changeSql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_CHANCE_SCORE);
		Map<String, Object> changeMap = new HashMap<>();

		List<EquPosSupplierInfoEntity_HI> supplierInfoEntityHi = equPosSupplierInfoDAO_HI.findByProperty("supplierId",paramsJONS.getInteger("supplierId"));
		changeMap.put("supplierNumber", supplierInfoEntityHi.get(0).getSupplierNumber());
		List<EquPosSupplierInfoEntity_HI_RO> changePagination = equPosSupplierInfoEntity_HI_RO.findList(changeSql, changeMap);
		for (EquPosSupplierInfoEntity_HI_RO infoEntityHiRo : changePagination) {
			if (conMap.get(infoEntityHiRo.getAtType()) == null) {
				conMap.put(infoEntityHiRo.getAtType(), infoEntityHiRo);
			} else {
				EquPosSupplierInfoEntity_HI_RO supplierInfoEntityHiRo = conMap.get(infoEntityHiRo.getAtType());
//supplierInfoEntityHiRo 在 infoEntityHiRo 前就变更
				if (supplierInfoEntityHiRo.getLastUpdateDate().getTime() < infoEntityHiRo.getLastUpdateDate().getTime()) {
					conMap.put(infoEntityHiRo.getAtType(), infoEntityHiRo);
				}
			}
		}

		for (String key : conMap.keySet()) {//keySet获取map集合key的集合 然后在遍历key即可
			returnList.add(conMap.get(key));
		}

		for (EquPosSupplierInfoEntity_HI_RO infoEntityHiRo : returnList) {
			returnArr.add(JSONObject.parseObject(JSONObject.toJSONString(infoEntityHiRo)));
		}
//获取质量审核 的数据先判断 是取审核数据还是导入的数据
//		sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_QUALITY);
//		List<EquPosSupplierInfoEntity_HI_RO> pagination1 = equPosSupplierInfoEntity_HI_RO.findList(sql, map);
//		sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_QUALITY_UPDATE);
//		List<EquPosSupplierInfoEntity_HI_RO> pagination2 = equPosSupplierInfoEntity_HI_RO.findList(sql, changeMap);
//		List<EquPosSupplierInfoEntity_HI_RO> pagination3 = new ArrayList<>();
//		if (pagination1.size() > 0 && pagination2.size() > 0) {
////pagination1 在 pagination2 前就获取pagination2
//			if (pagination1.get(0).getLastUpdateDate().getTime() >= pagination2.get(0).getLastUpdateDate().getTime()) {
//				pagination3 = getQualityLine(pagination1.get(0),paramsJONS.getInteger("supplierId"));
//			} else {
//				pagination3 = getQualityChangeLine(pagination2.get(0), supplierInfoEntityHi.getSupplierNumber());
//			}
//		}else if(pagination1.size() > 0){
//			pagination3 = getQualityLine(pagination1.get(0),paramsJONS.getInteger("supplierId"));
//		}else if(pagination2.size() > 0){
//			pagination3 = getQualityChangeLine(pagination2.get(0), supplierInfoEntityHi.getSupplierNumber());
//		}
		sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_QUALITY_ALL);
		List<EquPosSupplierInfoEntity_HI_RO> pagination3 = equPosSupplierInfoEntity_HI_RO.findList(sql, map);
		for (EquPosSupplierInfoEntity_HI_RO entityHiRo : pagination3) {
			returnArr.add(JSONObject.parseObject(JSONObject.toJSONString(entityHiRo)));
		}
		return returnArr;
	}

    //获取导入变更详情
	public List<EquPosSupplierInfoEntity_HI_RO>  getQualityChangeLine(EquPosSupplierInfoEntity_HI_RO entityHiRo, String supplierNumber) {
		StringBuffer sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_QUALITY_UPDATE_LINE);
		Map map = new HashMap();
		map.put("updateHeadId",entityHiRo.getId());
		map.put("supplierNumber",supplierNumber);
		List<EquPosSupplierInfoEntity_HI_RO> pagination1 = equPosSupplierInfoEntity_HI_RO.findList(sql, map);
		List<EquPosSupplierInfoEntity_HI_RO> returnList = new ArrayList<>();
		for (EquPosSupplierInfoEntity_HI_RO supplierInfoEntityHiRo : pagination1) {
			supplierInfoEntityHiRo.setAtType("质量审核");
			returnList.add(supplierInfoEntityHiRo);
		}
		return returnList;
	}
	//获取审批详情
	public List<EquPosSupplierInfoEntity_HI_RO>  getQualityLine(EquPosSupplierInfoEntity_HI_RO entityHiRo, Integer supplierId) {
		StringBuffer sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_QUALITY_LINE);
		Map map = new HashMap();
		map.put("supplierId",supplierId);
		List<EquPosSupplierInfoEntity_HI_RO> pagination1 = equPosSupplierInfoEntity_HI_RO.findList(sql, map);
		List<EquPosSupplierInfoEntity_HI_RO> returnList = new ArrayList<>();
		for (EquPosSupplierInfoEntity_HI_RO supplierInfoEntityHiRo : pagination1) {
			supplierInfoEntityHiRo.setAtType("质量审核");
			supplierInfoEntityHiRo.setFilePath(entityHiRo.getFilePath());
			supplierInfoEntityHiRo.setFileName(entityHiRo.getFileName());
			supplierInfoEntityHiRo.setFileId(entityHiRo.getFileId());
			supplierInfoEntityHiRo.setSupplierResule(entityHiRo.getSupplierResule());
			supplierInfoEntityHiRo.setSupplierScore(entityHiRo.getSupplierScore());
			supplierInfoEntityHiRo.setValidityPeriodDate(entityHiRo.getValidityPeriodDate());
			returnList.add(supplierInfoEntityHiRo);
		}
		return returnList;
	}

	/**
	 * 资质审查单据-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosSupplierInfoEntity_HI saveSupplierInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 供应商档案查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierFiles(JSONObject queryParamJSON, Integer pageIndex,
									   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_SUPPLIER_FILES);
//		if(queryParamJSON.containsKey("categoryId")){
//			sql.append(" and  pc.category_id = " + queryParamJSON.getInteger("categoryId"));
//	}
		String categoryId = null;
		if(queryParamJSON.containsKey("supplierStatus") && !"".equals(queryParamJSON.getString("supplierStatus"))){
			String supplierStatus = queryParamJSON.getString("supplierStatus");
			if("APPROVING".equals(supplierStatus)){
				sql.append(" and  t.supplier_status in ('APPROVING','TEMPLATE')");
			}else{
				sql.append(" and  t.supplier_status = '" + supplierStatus + "'");
			}
			queryParamJSON.remove("supplierStatus");
		}

		if(queryParamJSON.containsKey("supplierNumber") && !"".equals(queryParamJSON.getString("supplierNumber"))){
			String supplierNumber = queryParamJSON.getString("supplierNumber");
			sql.append(" and  t.supplier_number like '%" + supplierNumber + "%'");
			queryParamJSON.remove("supplierNumber");
		}

		if(queryParamJSON.containsKey("categoryId")){
			categoryId = queryParamJSON.getString("categoryId");
			sql.append(" and  t.category_validate_falg = 'Y'");
			queryParamJSON.remove("categoryId");
			queryParamJSON.remove("categoryName");
		}

		if(queryParamJSON.containsKey("deptCode")){
			String deptCode = queryParamJSON.getString("deptCode");
			if("0E".equals(deptCode) || "12".equals(deptCode) || "13".equals(deptCode)){
				queryParamJSON.put("deptCode","0E");
			}
//			if(!"IT".equals(deptCode)){
//				queryParamJSON.put("deptCode","0E");
//			}
		}


		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		if(null == categoryId){
			map.put("categoryId","-1");
		}else{
			map.put("categoryId",categoryId);
		}
		sql.append(" order by t.supplier_id desc");
		Pagination<EquPosSupplierInfoEntity_HI_RO> pagination = equPosSupplierInfoEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商档案查询it
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierFilesIt(JSONObject queryParamJSON, Integer pageIndex,
										Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_SUPPLIER_FILES_IT);
		Map<String, Object> map = new HashMap<>();

		String categoryId = null;
		if(queryParamJSON.containsKey("supplierStatus") && !"".equals(queryParamJSON.getString("supplierStatus"))){
			String supplierStatus = queryParamJSON.getString("supplierStatus");
			if("APPROVING".equals(supplierStatus)){
				sql.append(" and  t.supplier_status in ('APPROVING','TEMPLATE')");
			}else{
				sql.append(" and  t.supplier_status = '" + supplierStatus + "'");
			}
			queryParamJSON.remove("supplierStatus");
		}

		if(queryParamJSON.containsKey("supplierNumber") && !"".equals(queryParamJSON.getString("supplierNumber"))){
			String supplierNumber = queryParamJSON.getString("supplierNumber");
			sql.append(" and  t.supplier_number like '%" + supplierNumber + "%'");
			queryParamJSON.remove("supplierNumber");
		}

		if(queryParamJSON.containsKey("supplierName_like") && !"".equals(queryParamJSON.getString("supplierName_like"))){
			String supplierName = queryParamJSON.getString("supplierName_like");
			sql.append(" and  t.supplier_name like '%" + supplierName + "%'");
			queryParamJSON.remove("supplierName_like");
		}

		if(queryParamJSON.containsKey("supplierShortName_like") && !"".equals(queryParamJSON.getString("supplierShortName_like"))){
			String supplierShortName = queryParamJSON.getString("supplierShortName_like");
			sql.append(" and  t.supplier_short_name like '%" + supplierShortName + "%'");
			queryParamJSON.remove("supplierShortName_like");
		}

		if(queryParamJSON.containsKey("licenseNum_like") && !"".equals(queryParamJSON.getString("licenseNum_like"))){
			String licenseNum = queryParamJSON.getString("licenseNum_like");
			sql.append(" and  t.license_num like '%" + licenseNum + "%'");
			queryParamJSON.remove("licenseNum_like");
		}

		if(queryParamJSON.containsKey("deptCode") && !"".equals(queryParamJSON.getString("deptCode"))){
//			String deptCode = queryParamJSON.getString("deptCode");
			sql.append(" and  t.dept_code <> '0E'");
			queryParamJSON.remove("deptCode");
		}

		if(queryParamJSON.containsKey("supplierType") && !"".equals(queryParamJSON.getString("supplierType"))){
			String supplierType = queryParamJSON.getString("supplierType");
			sql.append(" and  t.supplier_type = '" + supplierType + "'");
			queryParamJSON.remove("supplierType");
		}

		if(queryParamJSON.containsKey("country") && !"".equals(queryParamJSON.getString("country"))){
			String country = queryParamJSON.getString("country");
			sql.append(" and  t.country = '" + country + "'");
			queryParamJSON.remove("country");
		}

		String qualificationsFileName = queryParamJSON.getString("qualificationsFileName");
		String serviceScope = queryParamJSON.getString("serviceScope");
		String expireDays = queryParamJSON.getString("expireDays");

		if(null != qualificationsFileName && !"".equals(qualificationsFileName)){
			queryParamJSON.put("qualificationsFileName","%" + qualificationsFileName + "%");
			sql.append(" and  t.attachment_count > 0 ");
		}else{
			queryParamJSON.put("qualificationsFileName","");
		}

		if(null != serviceScope && !"".equals(serviceScope)){
			sql.append(" and  t.category_count > 0 ");
		}else{
			queryParamJSON.put("serviceScope","-1");
		}

		if(null != expireDays && !"".equals(expireDays)){
			sql.append(" and  t.expire_days <= " + expireDays);
		}

		map.put("serviceScope",queryParamJSON.getString("serviceScope"));
		map.put("qualificationsFileName",queryParamJSON.getString("qualificationsFileName"));

		sql.append(" order by t.supplier_id desc");
		Pagination<EquPosSupplierInfoEntity_HI_RO> pagination = equPosSupplierInfoEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商信息报表查询(Non-trade) 是否
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierInfoReportForm(JSONObject queryParamJSON, Integer pageIndex,
										  Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_SUPPLIER_INFO_REPORT_FORM);
		Map<String, Object> map = new HashMap<>();

		if(queryParamJSON.containsKey("supplierStatus") && !"".equals(queryParamJSON.getString("supplierStatus"))){
			String supplierStatus = queryParamJSON.getString("supplierStatus");
			if("APPROVING".equals(supplierStatus)){
				sql.append(" and  t.supplier_status in ('APPROVING','TEMPLATE')");
			}else{
				sql.append(" and  t.supplier_status = '" + supplierStatus + "'");
			}
			queryParamJSON.remove("supplierStatus");
		}

		if(queryParamJSON.containsKey("supplierNumber") && !"".equals(queryParamJSON.getString("supplierNumber"))){
			String supplierNumber = queryParamJSON.getString("supplierNumber");
			sql.append(" and  t.supplier_number like '%" + supplierNumber + "%'");
			queryParamJSON.remove("supplierNumber");
		}

		if(queryParamJSON.containsKey("supplierName_like") && !"".equals(queryParamJSON.getString("supplierName_like"))){
			String supplierName = queryParamJSON.getString("supplierName_like");
			sql.append(" and  t.supplier_name like '%" + supplierName + "%'");
			queryParamJSON.remove("supplierName_like");
		}

		if(queryParamJSON.containsKey("supplierShortName_like") && !"".equals(queryParamJSON.getString("supplierShortName_like"))){
			String supplierShortName = queryParamJSON.getString("supplierShortName_like");
			sql.append(" and  t.supplier_short_name like '%" + supplierShortName + "%'");
			queryParamJSON.remove("supplierShortName_like");
		}

		if(queryParamJSON.containsKey("licenseNum_like") && !"".equals(queryParamJSON.getString("licenseNum_like"))){
			String licenseNum = queryParamJSON.getString("licenseNum_like");
			sql.append(" and  t.license_num like '%" + licenseNum + "%'");
			queryParamJSON.remove("licenseNum_like");
		}

		if(queryParamJSON.containsKey("supplierType") && !"".equals(queryParamJSON.getString("supplierType"))){
			String supplierType = queryParamJSON.getString("supplierType");
			sql.append(" and  t.supplier_type = '" + supplierType + "'");
			queryParamJSON.remove("supplierType");
		}

		String expireDays = queryParamJSON.getString("expireDays");

		if(queryParamJSON.containsKey("serviceScope") && !"".equals(queryParamJSON.getString("serviceScope"))){
			String serviceScope = queryParamJSON.getString("serviceScope");
			sql.append(" and  t.category_id = '" + serviceScope + "'");
			queryParamJSON.remove("serviceScope");
		}

		if(null != expireDays && !"".equals(expireDays)){
			sql.append(" and  t.expire_days <= " + expireDays);
		}

		map.put("serviceScope",queryParamJSON.getString("serviceScope"));

		sql.append(" order by t.supplier_id desc");
		Pagination<EquPosSupplierInfoEntity_HI_RO> pagination = equPosSupplierInfoEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商查询LOV
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierLov(JSONObject queryParamJSON, Integer pageIndex,
									   Integer pageRows) {
		StringBuffer sql = null;
		String excludeDeptCode = queryParamJSON.getString("excludeDeptCode");
		String excludesupplierName = queryParamJSON.getString("excludesupplierName");
		if(queryParamJSON.containsKey("isCsr")){
			sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.SUPPLIER_QUERY_SQL_WITH_CSR);
		}else{
			sql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_LOV);
		}
		Map<String, Object> map = new HashMap<>();

		SaafToolUtils.parperParam(queryParamJSON,"psi.supplier_number", "supplierNumber", sql, map, "like");
		SaafToolUtils.parperParam(queryParamJSON,"psi.supplier_name", "supplierName", sql, map, "like");
//		SaafToolUtils.parperParam(queryParamJSON,"d.dept_code", "deptCode", sql, map, "=");
		SaafToolUtils.parperParam(queryParamJSON,"d.supplier_type", "supplierType", sql, map, "=");
		SaafToolUtils.parperParam(queryParamJSON,"d.supplier_status", "supplierStatus", sql, map, "in");

		if(queryParamJSON.containsKey("deptCode")){
			if("0E".equals(queryParamJSON.getString("deptCode"))){
				sql.append(" and d.dept_code = '" + queryParamJSON.getString("deptCode") + "'");
			}else{
				sql.append(" and d.dept_code <> '0E'");
			}
		}
		if(excludeDeptCode != null && !"".equals(excludeDeptCode)){
			if("0E".equals(excludeDeptCode)){
				sql.append(" and psi.supplier_id not in (select t.supplier_id from equ_pos_supp_info_with_dept t where t.dept_code = '" + excludeDeptCode + "')");
			}else{
				sql.append(" and psi.supplier_id not in (select t.supplier_id from equ_pos_supp_info_with_dept t where t.dept_code <> '0E')");
			}
		}
		if(excludesupplierName != null && !"".equals(excludesupplierName)){
			sql.append(" and psi.supplier_name not in ('" + excludesupplierName + "')");
		}
        sql.append(" order by psi.supplier_number asc");
		Pagination<EquPosSupplierInfoEntity_HI_RO> pagination = equPosSupplierInfoEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	@Override
	public void checkSupplierCredentials() {
		try {
			StringBuffer ownerBuffer = new StringBuffer();
			StringBuffer supplierBuffer = new StringBuffer();
			StringBuffer buffer;
			long betweenDays = 0;

			//1.查找所有供应商(排除状态：准入中/黑名单)
			StringBuffer supplierSql = new StringBuffer();
			supplierSql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_LOV);
			supplierSql.append(" and d.dept_code <> '0E'");
			supplierSql.append(" and d.supplier_status not in ('APPROVING','BLACKLIST')");
			List<EquPosSupplierInfoEntity_HI_RO> supplierList = equPosSupplierInfoEntity_HI_RO.findList(supplierSql);
			//2.循环供应商，查找每个供应商对应的资质信息和资质文件信息
			for(int i = 0; i < supplierList.size(); i++){
				EquPosSupplierInfoEntity_HI_RO supplierRow = supplierList.get(i);
				Integer supplierId = supplierRow.getSupplierId();
				String supplierName = supplierRow.getSupplierName();
				String supplierInChargeName = supplierRow.getSupplierInChargeName();
				String supplierInChargeNumber = supplierRow.getSupplierInChargeNumber();
				ownerBuffer = new StringBuffer();
				ownerBuffer.append("<html><body>");
				ownerBuffer.append("尊敬的").append(supplierInChargeName).append(":<br/>");

				supplierBuffer = new StringBuffer();
				supplierBuffer.append("<html><body>");
				supplierBuffer.append("尊敬的").append("%supplierContactName%").append(":<br/>");

				buffer = new StringBuffer();

				buffer.append("供应商").append(supplierName).append("相关资质将要过期或已过期,详情如下:").append("<br/>");
				int contentCount = 0;
				//查询资质信息
				StringBuffer credentialSql = new StringBuffer(EquPosSupplierCredentialsEntity_HI_RO.QUERY_SQL);
				credentialSql.append(" and sc.supplier_id = " + supplierId);
				credentialSql.append(" and sc.dept_code <> '0E'");
				List<EquPosSupplierCredentialsEntity_HI_RO> credentialList = equPosSupplierCredentialsEntity_HI_RO.findList(credentialSql);
				if(credentialList.size() > 0){
					EquPosSupplierCredentialsEntity_HI_RO credentialRow = credentialList.get(0);
					if(!"Y".equals(credentialRow.getLongBusinessIndate())){
						//如果营业期限没有勾选长期，则需要校验营业期限是否过期
						Date licenseIndate = credentialRow.getLicenseIndate();
						if(null != licenseIndate){
							betweenDays = getBetweenDays(licenseIndate);
							//30天后过期
							if(betweenDays <= 30 && betweenDays > 0){
								contentCount++;
								//todo 拼接消息
								buffer.append(contentCount).append(".资质信息-营业期限,有效期至").append(DateUtil.date2Str(licenseIndate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
							}else if(betweenDays <= 0){
								contentCount++;
								buffer.append(contentCount).append(".资质信息-营业期限,有效期至").append(DateUtil.date2Str(licenseIndate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
							}
						}
					}
					//组织机构代码到期日
					Date tissueIndate = credentialRow.getTissueIndate();
					if(null != tissueIndate){
						betweenDays = getBetweenDays(tissueIndate);
						if(betweenDays <= 30 && betweenDays > 0){
							contentCount++;
							//todo 拼接消息
							buffer.append(contentCount).append(".资质信息-组织机构代码号,有效期至").append(DateUtil.date2Str(tissueIndate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
						}else if(betweenDays <= 0){
							contentCount++;
							buffer.append(contentCount).append(".资质信息-组织机构代码号,有效期至").append(DateUtil.date2Str(tissueIndate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
						}
					}
				}

				//资质文件信息
				StringBuffer credentialAttachSql = new StringBuffer(EquPosCredentialsAttachmentEntity_HI_RO.QUERY_SQL);
				credentialAttachSql.append(" and t.supplier_id = " + supplierId);
				credentialAttachSql.append(" and t.deptment_code <> '0E'");
				credentialAttachSql.append(" and nvl(t.is_product_factory,'N') = 'N'");
				List<EquPosCredentialsAttachmentEntity_HI_RO> credentialAttachList = equPosCredentialsAttachmentEntity_HI_RO.findList(credentialAttachSql);
				for(int j = 0; j < credentialAttachList.size(); j++){
					EquPosCredentialsAttachmentEntity_HI_RO credentialAttachRow = credentialAttachList.get(j);
					Date invalidate = credentialAttachRow.getInvalidDate();
					if(null != invalidate){
						String attachmentName = credentialAttachRow.getAttachmentName();
						betweenDays = getBetweenDays(invalidate);
						if(betweenDays <= 30 && betweenDays > 0){
							contentCount++;
							//todo 拼接消息
							buffer.append(contentCount).append(".资质文件-").append(attachmentName).append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
						}else if(betweenDays <= 0){
							contentCount++;
							buffer.append(contentCount).append(".资质文件-").append(attachmentName).append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
						}
					}
				}

				supplierBuffer.append(buffer.toString());
				supplierBuffer.append("</body></html>");
				ownerBuffer.append(buffer.toString());
				ownerBuffer.append("</body></html>");

				if(contentCount > 0){
					//todo 发送邮件给供应商联系人
					//查找供应商联系人
//					Map queryMap = new HashMap();
//					queryMap.put("supplierId",supplierId);
//					queryMap.put("deptCode","03");
//					List<EquPosSupplierContactsEntity_HI> supplierContactsList = equPosSupplierContactsDAO_HI.findByProperty(queryMap);

					List<EquPosSupplierContactsEntity_HI> supplierContactsList = equPosSupplierContactsDAO_HI.findList("from EquPosSupplierContactsEntity_HI where supplierId = " + supplierId + " and deptCode <> '0E'");

					for(int m = 0; m < supplierContactsList.size(); m++){
						EquPosSupplierContactsEntity_HI contactsEntity = supplierContactsList.get(m);
						String contactName = contactsEntity.getContactName();
						String mailBody = supplierBuffer.toString().replace("%supplierContactName%",contactName);
						String email = contactsEntity.getEmailAddress();
						EmailUtil.sendInMail("供应商资质文件过期提醒", mailBody,email);
					}

					//todo 发送邮件给供应商负责人
					//查找供应商负责人
					JSONObject paramsJson = new JSONObject();
					JSONObject resultJson = new JSONObject();
					paramsJson.put("userName", supplierInChargeNumber);
					resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
					if (resultJson.get("email") != null) {
						EmailUtil.sendInMail("供应商资质文件过期提醒", ownerBuffer.toString(),resultJson.getString("email"));
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("监控待报价调度程序运行失败，请联系管理员");
		}
	}

	@Override
	public void checkOEMSupplierCredentials(){
		try {
			StringBuffer emailBuffer = new StringBuffer();
			long betweenDays = 0;

			//1.查找所有供应商(排除状态：准入中/黑名单)
			StringBuffer supplierSql = new StringBuffer();
			supplierSql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_LOV);
			supplierSql.append(" and d.dept_code = '0E'");
			supplierSql.append(" and d.supplier_status not in ('APPROVING','BLACKLIST')");
			List<EquPosSupplierInfoEntity_HI_RO> supplierList = equPosSupplierInfoEntity_HI_RO.findList(supplierSql);
			//2.循环供应商，查找每个供应商对应的资质信息和生产资质文件信息
			for(int i = 0; i < supplierList.size(); i++){
				EquPosSupplierInfoEntity_HI_RO supplierRow = supplierList.get(i);
				Integer supplierId = supplierRow.getSupplierId();
				String supplierNumber = supplierRow.getSupplierNumber();
				String supplierName = supplierRow.getSupplierName();
				String supplierInChargeName = supplierRow.getSupplierInChargeName();
				String supplierInChargeNumber = supplierRow.getSupplierInChargeNumber();
				emailBuffer = new StringBuffer();
				emailBuffer.append("<html><body>");
				emailBuffer.append("尊敬的").append("%supplierContactName%").append(":<br/>");

				emailBuffer.append("供应商").append(supplierName).append("相关资质将要过期或已过期,详情如下:").append("<br/>");
				int contentCount = 0;
				//查询资质信息
				StringBuffer credentialSql = new StringBuffer(EquPosSupplierCredentialsEntity_HI_RO.QUERY_SQL);
				credentialSql.append(" and sc.supplier_id = " + supplierId);
				credentialSql.append(" and sc.dept_code = '0E'");
				List<EquPosSupplierCredentialsEntity_HI_RO> credentialList = equPosSupplierCredentialsEntity_HI_RO.findList(credentialSql);
				if(credentialList.size() > 0){
					EquPosSupplierCredentialsEntity_HI_RO credentialRow = credentialList.get(0);
					if(!"Y".equals(credentialRow.getLongBusinessIndate())){
						//如果营业期限没有勾选长期，则需要校验营业期限是否过期
						Date licenseIndate = credentialRow.getLicenseIndate();
						if(null != licenseIndate){
							betweenDays = getBetweenDays(licenseIndate);
							//30天后过期
							if(betweenDays <= 30 && betweenDays > 0){
								contentCount++;
								//todo 拼接消息
								emailBuffer.append(contentCount).append(".资质信息-营业期限,有效期至").append(DateUtil.date2Str(licenseIndate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
							}else if(betweenDays <= 0){
								contentCount++;
								emailBuffer.append(contentCount).append(".资质信息-营业期限,有效期至").append(DateUtil.date2Str(licenseIndate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
							}
						}
					}
					//组织机构代码到期日
					Date tissueIndate = credentialRow.getTissueIndate();
					if(null != tissueIndate){
						betweenDays = getBetweenDays(tissueIndate);
						if(betweenDays <= 30 && betweenDays > 0){
							contentCount++;
							//todo 拼接消息
							emailBuffer.append(contentCount).append(".资质信息-组织机构代码号,有效期至").append(DateUtil.date2Str(tissueIndate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
						}else if(betweenDays <= 0){
							contentCount++;
							emailBuffer.append(contentCount).append(".资质信息-组织机构代码号,有效期至").append(DateUtil.date2Str(tissueIndate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
						}
					}
				}

				//生产资质文件信息
				StringBuffer credentialAttachSql = new StringBuffer(EquPosCredentialsAttachmentEntity_HI_RO.QUERY_SQL);
				credentialAttachSql.append(" and t.supplier_id = " + supplierId);
				credentialAttachSql.append(" and t.deptment_code = '0E'");
				credentialAttachSql.append(" and nvl(t.is_product_factory,'N') = 'Y'");
				List<EquPosCredentialsAttachmentEntity_HI_RO> credentialAttachList = equPosCredentialsAttachmentEntity_HI_RO.findList(credentialAttachSql);
				for(int j = 0; j < credentialAttachList.size(); j++){
					EquPosCredentialsAttachmentEntity_HI_RO credentialAttachRow = credentialAttachList.get(j);
					Date invalidate = credentialAttachRow.getInvalidDate();
					if(null != invalidate){
						String attachmentName = credentialAttachRow.getAttachmentName();
						betweenDays = getBetweenDays(invalidate);
						if(betweenDays <= 30 && betweenDays > 0){
							contentCount++;
							//todo 拼接消息
							emailBuffer.append(contentCount).append(".资质文件-").append(attachmentName).append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
						}else if(betweenDays <= 0){
							contentCount++;
							emailBuffer.append(contentCount).append(".资质文件-").append(attachmentName).append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
						}
					}
				}

				//信用审核/信用审核导入更新
				StringBuffer creditSql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SUPPLIER_CREDIT_SQL);
				Map map = new HashMap();
				map.put("supplierId",supplierId);
				List<EquPosSupplierInfoEntity_HI_RO> creditResultList = equPosSupplierInfoEntity_HI_RO.findList(creditSql, map);
				if(creditResultList.size() > 0){
					EquPosSupplierInfoEntity_HI_RO creditResultRow = creditResultList.get(0);
					Date invalidate = creditResultRow.getValidityPeriodDate();

					betweenDays = getBetweenDays(invalidate);
					if(betweenDays <= 30 && betweenDays > 0){
						contentCount++;
						//todo 拼接消息
						emailBuffer.append(contentCount).append(".").append("信用审核").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
					}else if(betweenDays <= 0){
						contentCount++;
						emailBuffer.append(contentCount).append(".").append("信用审核").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
					}
				}
				//csr审核/csr审核导入更新
				StringBuffer csrSql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SUPPLIER_CSR_SQL);
				Map map2 = new HashMap();
				map2.put("supplierId",supplierId);
//				map2.put("supplierNumber",supplierNumber);
				List<EquPosSupplierInfoEntity_HI_RO> csrResultList = equPosSupplierInfoEntity_HI_RO.findList(csrSql, map2);
				if(csrResultList.size() > 0){
					EquPosSupplierInfoEntity_HI_RO csrResultRow = csrResultList.get(0);
					Date invalidate = csrResultRow.getValidityPeriodDate();

					betweenDays = getBetweenDays(invalidate);
					if(betweenDays <= 30 && betweenDays > 0){
						contentCount++;
						//todo 拼接消息
						emailBuffer.append(contentCount).append(".").append("CSR审核").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
					}else if(betweenDays <= 0){
						contentCount++;
						emailBuffer.append(contentCount).append(".").append("CSR审核").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
					}
				}
				//质量审核/质量审核导入更新
				StringBuffer qualitySql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SUPPLIER_QUALITY_SQL);
				Map map3 = new HashMap();
				map3.put("supplierId",supplierId);
//				map3.put("supplierNumber",supplierNumber);
				List<EquPosSupplierInfoEntity_HI_RO> qualityResultList = equPosSupplierInfoEntity_HI_RO.findList(qualitySql, map3);
				if(qualityResultList.size() > 0){
					EquPosSupplierInfoEntity_HI_RO qualityResultRow = qualityResultList.get(0);
					Date invalidate = qualityResultRow.getValidityPeriodDate();

					betweenDays = getBetweenDays(invalidate);
					if(betweenDays <= 30 && betweenDays > 0){
						contentCount++;
						//todo 拼接消息
						emailBuffer.append(contentCount).append(".").append("质量审核").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
					}else if(betweenDays <= 0){
						contentCount++;
						emailBuffer.append(contentCount).append(".").append("质量审核").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
					}
				}
				//Commercial Tearms Evalvation Score
				StringBuffer commercialSql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SUPPLIER_COMMERCIAL_SQL);
				Map map4 = new HashMap();
				map4.put("supplierId",supplierId);
				List<EquPosSupplierInfoEntity_HI_RO> commercialResultList = equPosSupplierInfoEntity_HI_RO.findList(commercialSql, map4);
				if(commercialResultList.size() > 0){
					EquPosSupplierInfoEntity_HI_RO commercialResultRow = commercialResultList.get(0);
					Date invalidate = commercialResultRow.getValidityPeriodDate();

					betweenDays = getBetweenDays(invalidate);
					if(betweenDays <= 30 && betweenDays > 0){
						contentCount++;
						//todo 拼接消息
						emailBuffer.append(contentCount).append(".").append("Commercial Tearms Evalvation Score").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
					}else if(betweenDays <= 0){
						contentCount++;
						emailBuffer.append(contentCount).append(".").append("Commercial Tearms Evalvation Score").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
					}
				}
				//Supplier Spending Percentage Evalvation
				StringBuffer spendingSql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SUPPLIER_SPENDING_SQL);
				Map map5 = new HashMap();
				map5.put("supplierId",supplierId);
				List<EquPosSupplierInfoEntity_HI_RO> spendingResultList = equPosSupplierInfoEntity_HI_RO.findList(spendingSql, map5);
				if(spendingResultList.size() > 0){
					EquPosSupplierInfoEntity_HI_RO spendingResultRow = spendingResultList.get(0);
					Date invalidate = spendingResultRow.getValidityPeriodDate();

					betweenDays = getBetweenDays(invalidate);
					if(betweenDays <= 30 && betweenDays > 0){
						contentCount++;
						//todo 拼接消息
						emailBuffer.append(contentCount).append(".").append("Supplier Spending Percentage Evalvation").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
					}else if(betweenDays <= 0){
						contentCount++;
						emailBuffer.append(contentCount).append(".").append("Supplier Spending Percentage Evalvation").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
					}
				}
				//年度评分导入更新
				StringBuffer scoringSql = new StringBuffer(EquPosSupplierInfoEntity_HI_RO.QUERY_SUPPLIER_SCORE_SQL);
				Map map6 = new HashMap();
				map6.put("supplierId",supplierId);
				List<EquPosSupplierInfoEntity_HI_RO> scoringResultList = equPosSupplierInfoEntity_HI_RO.findList(scoringSql, map6);
				if(scoringResultList.size() > 0){
					EquPosSupplierInfoEntity_HI_RO scoringResultRow = scoringResultList.get(0);
					Date invalidate = scoringResultRow.getValidityPeriodDate();

					betweenDays = getBetweenDays(invalidate);
					if(betweenDays <= 30 && betweenDays > 0){
						contentCount++;
						//todo 拼接消息
						emailBuffer.append(contentCount).append(".").append("年度评分").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",将于").append(betweenDays).append("天后过期").append("<br/>");
					}else if(betweenDays <= 0){
						contentCount++;
						emailBuffer.append(contentCount).append(".").append("年度评分").append(",有效期至").append(DateUtil.date2Str(invalidate,"yyyy/MM/dd")).append(",已过期").append("<br/>");
					}
				}

				emailBuffer.append("</body></html>");

				if(contentCount > 0){
					//todo 发送邮件给供应商联系人
					//查找供应商联系人
//					Map queryMap = new HashMap();
//					queryMap.put("supplierId",supplierId);
//					queryMap.put("deptCode","03");
//					List<EquPosSupplierContactsEntity_HI> supplierContactsList = equPosSupplierContactsDAO_HI.findByProperty(queryMap);

					List<EquPosSupplierContactsEntity_HI> supplierContactsList = equPosSupplierContactsDAO_HI.findList("from EquPosSupplierContactsEntity_HI where supplierId = " + supplierId + " and deptCode <> '0E'");

					for(int m = 0; m < supplierContactsList.size(); m++){
						EquPosSupplierContactsEntity_HI contactsEntity = supplierContactsList.get(m);
						String contactName = contactsEntity.getContactName();
						String mailBody = emailBuffer.toString().replace("%supplierContactName%",contactName);
						String email = contactsEntity.getEmailAddress();
						System.out.println(mailBody);
						EmailUtil.sendMailFromWatsons("供应商资质文件过期提醒", mailBody,email,contactsEntity.getDeptCode());
					}

					//todo 发送邮件给供应商负责人
					//查找供应商负责人
					JSONObject paramsJson = new JSONObject();
					JSONObject resultJson = new JSONObject();
					paramsJson.put("userName", supplierInChargeNumber);
					resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
					String mailBody = emailBuffer.toString().replace("%supplierContactName%",supplierInChargeName);
					System.out.println(mailBody);
					if (resultJson.get("email") != null) {
						EmailUtil.sendInMail("供应商资质文件过期提醒", mailBody,resultJson.getString("email"));
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			LOGGER.error("监控待报价调度程序运行失败，请联系管理员");
		}
	}

	public long getBetweenDays(Date destDate) throws Exception {
		Date current = new Date();
		long betweenDays = (destDate.getTime() - current.getTime()) / (1000L*3600L*24L);
		return betweenDays + 1;
	}
}
