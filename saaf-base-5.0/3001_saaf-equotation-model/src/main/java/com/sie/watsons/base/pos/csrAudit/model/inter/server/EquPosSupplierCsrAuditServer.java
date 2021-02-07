package com.sie.watsons.base.pos.csrAudit.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.csrAudit.model.entities.readonly.EquPosSupplierCsrAuditEntity_HI_RO;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosQualificationInfoEntity_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosQualificationInfoEntity_HI_RO;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.csrAudit.model.entities.EquPosSupplierCsrAuditEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.csrAudit.model.inter.IEquPosSupplierCsrAudit;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosSupplierCsrAuditServer")
public class EquPosSupplierCsrAuditServer extends BaseCommonServer<EquPosSupplierCsrAuditEntity_HI> implements IEquPosSupplierCsrAudit{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCsrAuditServer.class);

	@Autowired
	private ViewObject<EquPosSupplierCsrAuditEntity_HI> equPosSupplierCsrAuditDAO_HI;

	@Autowired
	private ViewObject<EquPosQualificationInfoEntity_HI> equPosQualificationInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierCsrAuditEntity_HI_RO> equPosSupplierCsrAuditEntity_HI_RO;

	@Autowired
	private BaseViewObject<EquPosQualificationInfoEntity_HI_RO> equPosQualificationInfoEntity_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	public EquPosSupplierCsrAuditServer() {
		super();
	}

	/**
	 * 供应商CSR审核单据查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierCsrAudit(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosSupplierCsrAuditEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierCsrAuditEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by csr.creation_date desc");
		Pagination<EquPosSupplierCsrAuditEntity_HI_RO> pagination = equPosSupplierCsrAuditEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public void operateValidate(String operator,Integer csrAuditId,Integer userId) throws Exception{
		if(null != csrAuditId){
			JSONObject queryObj = new JSONObject();
			queryObj.put("csrAuditId",csrAuditId);
			JSONObject csrObj = this.findSupplierCsrAudit(queryObj,1,999);
			JSONArray csrArr = csrObj.getJSONArray("data");
			JSONObject csrJson = csrArr.getJSONObject(0);
			String csrAuditStatus = csrJson.getString("csrAuditStatus");
			Integer createdBy = csrJson.getInteger("createdBy");
			//保存操作，状态校验
			if(userId.intValue() != createdBy.intValue()){
				throw new Exception("非法操作,系统拒绝响应!");
			}
			if("SAVE".equals(operator) || "SUBMIT".equals(operator)){
				if("APPROVAL".equals(csrAuditStatus)){
					throw new Exception("非法操作,系统拒绝响应!");
				}
			}
		}
	}

	/**
	 * 供应商CSR审核单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosSupplierCsrAuditEntity_HI saveSupplierCsrAudit(JSONObject params) throws Exception {
		//校验用户操作权限
//		Integer userId = params.getInteger("varUserId");
//		Integer csrAuditId = params.getInteger("csrAuditId");
//		operateValidate("SAVE",csrAuditId,userId);

		EquPosSupplierCsrAuditEntity_HI entity = new EquPosSupplierCsrAuditEntity_HI();
		if(params.containsKey("csrAuditId")){
			//修改保存
			entity = this.saveOrUpdate(params);
		}else{
			//新增保存
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String currentDate = format.format(new Date());
			String prefix = "CSRSH-" + currentDate;
			String sequenceId = generateCodeServer.getSequenceId(prefix,4);
			String qualityAuditNumber = prefix + "-" + sequenceId;
			params.put("csrAuditNumber", qualityAuditNumber);
			params.put("csrAuditStatus","DRAFT");
			entity = this.saveOrUpdate(params);
		}
		return entity;
	}

	/**
	 * 供应商CSR审核单据删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteSupplierCsrAudit(JSONObject jsonObject) {
		Integer csrAuditId =jsonObject.getInteger("id");
		EquPosSupplierCsrAuditEntity_HI entity =equPosSupplierCsrAuditDAO_HI.getById(csrAuditId);
		if(entity!=null){
			equPosSupplierCsrAuditDAO_HI.delete(entity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}

	/**
	 * 供应商CSR审核单据提交
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void submitSupplierCsrAudit(JSONObject params) throws Exception {
		//校验用户操作权限
		Integer userId = params.getInteger("varUserId");
		Integer csrAuditId = params.getInteger("csrAuditId");
		operateValidate("SUBMIT",csrAuditId,userId);

		params.put("csrAuditStatus","APPROVAL");
		this.saveOrUpdate(params);

		String qualificationAuditNumber = params.getString("qualificationAuditNumber");
		String supplierName = params.getString("supplierName");
		List<EquPosQualificationInfoEntity_HI> qualificationList = equPosQualificationInfoDAO_HI.findByProperty("qualificationNumber",qualificationAuditNumber);
		EquPosQualificationInfoEntity_HI qualificationEntity = null;
		if(qualificationList.size() > 0){
			qualificationEntity = qualificationList.get(0);
			Integer createdBy = qualificationEntity.getCreatedBy();
			JSONObject queryJson = new JSONObject();
			queryJson.put("createdBy",createdBy);
			JSONObject resultJson = ResultUtils.getUserInfoForCreatedBy(queryJson);
			System.out.println(resultJson);
			String ownerName = resultJson.getString("createdByName");
			String emailAddress = resultJson.getString("email");
			//QA填写完成csr审核单据后通知到owner
			String mailBody = CommonUtils.generateCSRCompleteMailContent(ownerName,supplierName);
		    EmailUtil.sendInMail("供应商CSR审核结束", mailBody,emailAddress);
		}
	}
}
