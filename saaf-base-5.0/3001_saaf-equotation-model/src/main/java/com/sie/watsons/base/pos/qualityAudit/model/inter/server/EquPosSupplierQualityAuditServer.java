package com.sie.watsons.base.pos.qualityAudit.model.inter.server;

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
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosQualificationInfoEntity_HI;
import com.sie.watsons.base.pos.qualityAudit.model.entities.readonly.EquPosSupplierQualityAuditEntity_HI_RO;
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
import com.sie.watsons.base.pos.qualityAudit.model.entities.EquPosSupplierQualityAuditEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualityAudit.model.inter.IEquPosSupplierQualityAudit;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosSupplierQualityAuditServer")
public class EquPosSupplierQualityAuditServer extends BaseCommonServer<EquPosSupplierQualityAuditEntity_HI> implements IEquPosSupplierQualityAudit{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierQualityAuditServer.class);

	@Autowired
	private ViewObject<EquPosSupplierQualityAuditEntity_HI> equPosSupplierQualityAuditDAO_HI;

	@Autowired
	private ViewObject<EquPosQualificationInfoEntity_HI> equPosQualificationInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierQualityAuditEntity_HI_RO> equPosSupplierQualityAuditEntity_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	public EquPosSupplierQualityAuditServer() {
		super();
	}

	/**
	 * 供应商质量审核单据查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierQualityAudit(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosSupplierQualityAuditEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierQualityAuditEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by qa.creation_date desc");
		Pagination<EquPosSupplierQualityAuditEntity_HI_RO> pagination = equPosSupplierQualityAuditEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public void operateValidate(String operator,Integer qualityAuditId,Integer userId) throws Exception{
		if(null != qualityAuditId){
			JSONObject queryObj = new JSONObject();
			queryObj.put("qualityAuditId",qualityAuditId);
			JSONObject qualityObj = this.findSupplierQualityAudit(queryObj,1,999);
			JSONArray qualityArr = qualityObj.getJSONArray("data");
			JSONObject qualityJson = qualityArr.getJSONObject(0);
			String qualityAuditStatus = qualityJson.getString("qualityAuditStatus");
			Integer createdBy = qualityJson.getInteger("createdBy");
			//保存操作，状态校验
			if(userId.intValue() != createdBy.intValue()){
				throw new Exception("非法操作,系统拒绝响应!");
			}
			if("SUBMIT".equals(operator)){
				if("APPROVAL".equals(qualityAuditStatus)){
					throw new Exception("非法操作,系统拒绝响应!");
				}
			}
		}
	}

	/**
	 * 供应商质量审核单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosSupplierQualityAuditEntity_HI saveSupplierQualityAudit(JSONObject params) throws Exception {
		//校验用户操作权限
		Integer userId = params.getInteger("varUserId");
		Integer qualityAuditId = params.getInteger("qualityAuditId");
		operateValidate("SAVE",qualityAuditId,userId);

		EquPosSupplierQualityAuditEntity_HI entity = null;
		if(params.containsKey("qualityAuditId")){
			entity = this.saveOrUpdate(params);
//			//修改保存
//			List<EquPosSupplierQualityAuditEntity_HI> list = equPosSupplierQualityAuditDAO_HI.findByProperty("qualityAuditId",params.getInteger("qualityAuditId"));
//			entity = list.get(0);
//			String qualityAuditStatus = entity.getQualityAuditStatus();
//			if("DRAFT".equals(qualityAuditStatus)){
//				entity = this.saveOrUpdate(params);
//			}else{
//				entity.setCapRectificationSummaryId(params.getInteger("capRectificationSummaryId"));
//				entity.setCapRectificationSummaryName(params.getString("capRectificationSummaryName"));
//				entity.setCapRectificationSummaryPath(params.getString("capRectificationSummaryPath"));
//				entity.setOperatorUserId(userId);
//				entity = equPosSupplierQualityAuditDAO_HI.saveEntity(entity);
//			}
		}else{
			//新增保存
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String currentDate = format.format(new Date());
			String prefix = "ZLSH-" + currentDate;
			String sequenceId = generateCodeServer.getSequenceId(prefix,4);
			String qualityAuditNumber = prefix + "-" + sequenceId;
			params.put("qualityAuditNumber", qualityAuditNumber);
			params.put("qualityAuditStatus","DRAFT");
			entity = this.saveOrUpdate(params);
		}
		return entity;
	}

	/**
	 * 供应商质量审核单据删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteSupplierQualityAudit(JSONObject jsonObject) {
		Integer qualityAuditId =jsonObject.getInteger("id");
		EquPosSupplierQualityAuditEntity_HI entity =equPosSupplierQualityAuditDAO_HI.getById(qualityAuditId);
		if(entity!=null){
			equPosSupplierQualityAuditDAO_HI.delete(entity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}

	/**
	 * 供应商质量审核单据提交
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void submitSupplierQualityAudit(JSONObject params) throws Exception {

		//校验用户操作权限
		Integer userId = params.getInteger("varUserId");
		Integer qualityAuditId = params.getInteger("qualityAuditId");
		operateValidate("SUBMIT",qualityAuditId,userId);

		params.put("qualityAuditStatus","APPROVAL");
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
			String mailBody = CommonUtils.generateQualityCompleteMailContent(ownerName,supplierName);
			EmailUtil.sendInMail("供应商质量审核结束", mailBody,emailAddress);
		}
	}
}
