package com.sie.watsons.base.supplement.model.inter.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSideAgrtHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaSideAgrtHeader;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.util.Assert;

@Component("ttaSideAgrtHeaderServer")
public class TtaSideAgrtHeaderServer extends BaseCommonServer<TtaSideAgrtHeaderEntity_HI> implements ITtaSideAgrtHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSideAgrtHeaderServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaSideAgrtHeaderEntity_HI> ttaSideAgrtHeaderDAO_HI;
	
	@Autowired
    private BaseViewObject<TtaSideAgrtHeaderEntity_HI_RO> ttaSideAgrtHeaderEntity_HI_RO;
    
    @Autowired
	private BaseViewObject<BaseAttachmentEntity_HI_RO> baseAttachmentEntity_HI_RO;
		 
    @Autowired
	private BaseViewObject<TtaSupplierEntity_HI_RO> ttaSupplierEntity_HI_RO;

	@Autowired
	private GenerateCodeService codeService;

	public TtaSideAgrtHeaderServer() {
		super();
	}

	/**
	 * 保存
	 * @param params
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaSideAgrtHeaderEntity_HI editTtaSideAgrtHeader(JSONObject params,int userId) throws Exception {
		LOGGER.info("参数:{}",params.toString());
		TtaSideAgrtHeaderEntity_HI ttaSideAgrtHeaderEntity_hi = SaafToolUtils.setEntity(TtaSideAgrtHeaderEntity_HI.class, params, ttaSideAgrtHeaderDAO_HI, userId);
/*
		if (!"A".equals(params.getString("billStatus"))) {
			throw new IllegalArgumentException("单据状态不是制作中,请重新选择再保存");
		}
		ttaSideAgrtHeaderEntity_hi.setBillStatus("A");//单据状态为制作中
        */

		ttaSideAgrtHeaderEntity_hi.setSideAgrtCode(codeService.getProposalSupplementCode());
		ttaSideAgrtHeaderEntity_hi.setCreatedBy(userId);
		ttaSideAgrtHeaderEntity_hi.setCreationDate(new Date());
		ttaSideAgrtHeaderEntity_hi.setOperatorUserId(userId);

		ttaSideAgrtHeaderDAO_HI.saveOrUpdate(ttaSideAgrtHeaderEntity_hi);
		return ttaSideAgrtHeaderEntity_hi;
	}

	/**
	 * 提交
	 * @param parameters
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaSideAgrtHeaderEntity_HI submitTtaSideAgrtHeader(JSONObject parameters, int userId) throws Exception {
		LOGGER.info("参数:{}",parameters.toString());
		TtaSideAgrtHeaderEntity_HI ttaSideAgrtHeaderEntity_hi = SaafToolUtils.setEntity(TtaSideAgrtHeaderEntity_HI.class, parameters, ttaSideAgrtHeaderDAO_HI, userId);

		if (!"A".equals(parameters.getString("billStatus"))) {
			throw new IllegalArgumentException("单据状态需要为制作中才能提交");
		}
		ttaSideAgrtHeaderEntity_hi.setBillStatus("B");//单据状态为待审批
		ttaSideAgrtHeaderEntity_hi.setLastUpdateDate(new Date());
		ttaSideAgrtHeaderEntity_hi.setLastUpdatedBy(userId);
		ttaSideAgrtHeaderEntity_hi.setLastUpdateLogin(userId);

		ttaSideAgrtHeaderDAO_HI.saveOrUpdate(ttaSideAgrtHeaderEntity_hi);
		return ttaSideAgrtHeaderEntity_hi;
	}

	/**
	 * 更新状态
	 * @param paramsJSON
	 * @param userId
	 * @throws Exception
	 */
	@Override
	public void updateStatus(JSONObject paramsJSON, int userId) throws Exception {
		Integer id = paramsJSON.getIntValue("id");//单据Id
		TtaSideAgrtHeaderEntity_HI instance = ttaSideAgrtHeaderDAO_HI.getById(id);

		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL"://草稿
				orderStatus = "A";
				instance.setBillStatus(orderStatus);
				ttaSideAgrtHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "ALLOW"://审批通过
				orderStatus = "C";
				instance.setBillStatus(orderStatus);
				instance.setApproveDate(new Date());//审批通过时间
				ttaSideAgrtHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "DRAFT"://审批驳回
				orderStatus = "A";
				instance.setBillStatus(orderStatus);
				ttaSideAgrtHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "APPROVAL"://审批中
				orderStatus = "B";
				instance.setBillStatus(orderStatus);
				ttaSideAgrtHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "CLOSED":
				orderStatus = "E";
				instance.setBillStatus(orderStatus);
				ttaSideAgrtHeaderDAO_HI.saveOrUpdate(instance);
				break;

		}

		instance.setOperatorUserId(userId);
		ttaSideAgrtHeaderDAO_HI.saveOrUpdate(instance);
		ttaSideAgrtHeaderDAO_HI.fluch();
	}

	/**
	 * 作废
	 * @param parameters
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaSideAgrtHeaderEntity_HI disSicradTtaSideAgrtHeader(JSONObject parameters, int userId) throws Exception {
		LOGGER.info("参数:{}",parameters.toString());
		TtaSideAgrtHeaderEntity_HI ttaSideAgrtHeaderEntity_hi = ttaSideAgrtHeaderDAO_HI.getById(parameters.getInteger("sideAgrtHId"));
		Assert.notNull(ttaSideAgrtHeaderEntity_hi,"您还未保存此单据,不能进行作废操作");
		if ("C".equals(ttaSideAgrtHeaderEntity_hi.getBillStatus())) {//审批通过状态
			ttaSideAgrtHeaderEntity_hi.setBillStatus("E");//单据状态为已作废
			ttaSideAgrtHeaderEntity_hi.setLastUpdateDate(new Date());
			ttaSideAgrtHeaderEntity_hi.setLastUpdatedBy(userId);
			ttaSideAgrtHeaderEntity_hi.setLastUpdateLogin(userId);
			ttaSideAgrtHeaderEntity_hi.setOperatorUserId(userId);
			ttaSideAgrtHeaderDAO_HI.saveOrUpdate(ttaSideAgrtHeaderEntity_hi);
		}else {
			throw new IllegalArgumentException("单据状态需要为审批通过才能进行作废操作");
		}
		return ttaSideAgrtHeaderEntity_hi;
	}


	/**
	 * 查询proposal补充协议头信息
	 * @param jsonObject
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@Override
	public Pagination<TtaSideAgrtHeaderEntity_HI_RO> findTtaSideAgrtHeaderEntity(JSONObject jsonObject,
			Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<>();
		String sideAgrtCode = jsonObject.getString("sideAgrtCode");
		String billStatus = jsonObject.getString("billStatus");
		Integer sideAgrtHId = jsonObject.getInteger("sideAgrtHId");
		String sideAgrtHIdStr = null;
		if (null != sideAgrtHId) {
			sideAgrtHIdStr = String.valueOf(sideAgrtHId);
		}

		StringBuffer sql = new StringBuffer(TtaSideAgrtHeaderEntity_HI_RO.SIDEAGRT_QUERY_H);
        if (sideAgrtCode!=null && !"".equals(sideAgrtCode)){//单据编码
        	sql.append(" and tsah.SIDE_AGRT_CODE ='"+sideAgrtCode+"' ");

        }
        if (billStatus!=null && !"".equals(billStatus) ){//单据状态
        	sql.append(" and tsah.BILL_STATUS ='"+billStatus+"' ");
        }

        if (sideAgrtHIdStr != null && !"".equals(sideAgrtHIdStr)){
			sql.append(" and tsah.SIDE_AGRT_H_ID = '"+sideAgrtHIdStr+"'");//主键
		}

        SaafToolUtils.changeQuerySort(jsonObject,sql,"tsah.creation_date desc",false);
        Pagination<TtaSideAgrtHeaderEntity_HI_RO> ruleLList = ttaSideAgrtHeaderEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);

		return ruleLList;
	}
	
	@Override
	public Pagination<TtaSupplierEntity_HI_RO> findTtaSupplierEntity(
			JSONObject jsonObject, Integer pageIndex, Integer pageRows)
			throws Exception {
		StringBuffer sql = new StringBuffer("select supplier_id supplierId,supplier_code supplierCode,supplier_name supplierName,status status,is_latent isLatent,creation_date creationDate,version_num versionNum,owner_dept ownerDept,owner_group ownerGroup,contract_output contractOutput,purchase_mode purchaseMode,proposal_brand_group proposalBrandGroup,latent_code latentCode,latent_name latentName from TTA_SUPPLIER where 1 = 1 ");
		Map<String, Object> paramsMap = new HashMap<>(); 	
		String supplierCode = jsonObject.getString("supplierCode");
		if (!"".equals(supplierCode) && supplierCode!=null){
			sql.append(" and SUPPLIER_CODE IN ('"+supplierCode+"')");
        }		
		Pagination<TtaSupplierEntity_HI_RO> supplierItemHeader = ttaSupplierEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
		return supplierItemHeader;
	}

	@Override
	public Pagination<BaseAttachmentEntity_HI_RO> findBaseAttachmentEntity(
			JSONObject jsonObject, Integer pageIndex, Integer pageRows)
			throws Exception {
	StringBuffer sql = new StringBuffer("select ba.file_id           fileId,\n" +
			"       ba.source_file_name  sourceFileName,\n" +
			"       ba.function_id       functionId,\n" +
			"       ba.business_id       businessId,\n" +
			"       ba.file_store_system fileStoreSystem,\n" +
			"       ba.file_path         filePath,\n" +
			"       ba.bucket_name       bucketName,\n" +
			"       ba.phy_file_name     phyFileName,\n" +
			"       ba.status            status,\n" +
			"       ba.file_size         fileSize,\n" +
			"       ba.file_type         fileType,\n" +
			"       ba.delete_flag       deleteFlag,\n" +
			"       ba.created_by        createdBy,\n" +
			"       ba.creation_date     creationDate,\n" +
			"       ba.last_updated_by   lastUpdatedBy,\n" +
			"       ba.last_update_date  lastUpdateDate,\n" +
			"       bu.user_full_name    createdByUser,\n" +
			"       ba.last_update_login lastUpdateLogin,\n" +
			"       ba.version_num       versionNum\n" +
			"  from base_attachment ba, base_users bu\n" +
			" where ba.created_by = bu.user_id\n" +
			"   and 1 = 1\n" +
			"   and ba.delete_flag = '0' ");
		Map<String, Object> paramsMap = new HashMap<>();
		//jsonObject.put("delete_flag",0);
		SaafToolUtils.parperParam(jsonObject, "ba.function_id", "functionId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(jsonObject, "ba.business_id", "businessId", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(jsonObject,sql,"ba.creation_date desc",false);
		Pagination<BaseAttachmentEntity_HI_RO> ttaSupplier = baseAttachmentEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
		return ttaSupplier;
	}


}
