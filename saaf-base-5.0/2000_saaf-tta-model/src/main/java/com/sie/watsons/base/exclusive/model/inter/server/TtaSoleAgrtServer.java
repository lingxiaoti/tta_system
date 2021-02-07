package com.sie.watsons.base.exclusive.model.inter.server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.Word2PdfUtil;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.watsons.base.exclusive.model.dao.TtaSoleAgrtDAO_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;
import com.sie.watsons.base.exclusive.utils.DynamicTableDTO;
import com.sie.watsons.base.exclusive.utils.OperateDTO;
import com.sie.watsons.base.exclusive.utils.WordUtils;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.rule.model.dao.readonly.TempRuleDefDAO_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.RuleFileTemplateEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.IRuleFileTemplate;
import com.sie.watsons.base.rule.services.TempRuleDefService;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdProposalLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaSaStdProposalLine;
import com.sie.watsons.base.withdrawal.utils.MergePdf;
import com.sie.watsons.base.withdrawal.utils.QrcodeUtils;
import com.sie.watsons.base.withdrawal.utils.ResourceUtils;
import com.sie.watsons.base.withdrawal.utils.WDatesUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtInfoEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtInfoEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleItemEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleAgrt;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.transaction.HeuristicCompletionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import sun.security.x509.EDIPartyName;

@Component("ttaSoleAgrtServer")
public class TtaSoleAgrtServer extends BaseCommonServer<TtaSoleAgrtEntity_HI> implements ITtaSoleAgrt{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleAgrtServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaSoleAgrtEntity_HI> ttaSoleAgrtDAO_HI;
	@Autowired
	private BaseCommonDAO_HI<TtaSoleAgrtInfoEntity_HI> ttaSoleAgrtInfoEntity_HI;
	@Autowired
	private DynamicViewObjectImpl<TtaSoleAgrtEntity_HI_RO> ttaSoleAgrtEntity_HI_RO;
	@Autowired
	private DynamicViewObjectImpl<TtaSoleSupplierEntity_HI_RO> ttaSoleSupplierEntity_HI_RO;
	@Autowired
	private DynamicViewObjectImpl<TtaSoleAgrtInfoEntity_HI_RO> ttaSoleAgrtInfoEntity_HI_RO;
	@Autowired
	private DynamicViewObjectImpl<TtaSoleItemEntity_HI_RO> ttaSoleItemEntity_HI_RO;
	@Autowired
	private TempRuleDefDAO_HI_RO tempRuleDefDAO_HI_RO;
	@Autowired
	private GenerateCodeService codeService;
	@Autowired
	private TtaSoleAgrtDAO_HI ttaSoleAgrtDAOHi;
	@Autowired
	private BaseViewObject<TtaSaStdProposalLineEntity_HI_RO> ttaSaStdProposalLineDAO_HI_RO;
	@Autowired
	private BaseCommonDAO_HI<TtaSoleSupplierEntity_HI> ttaSoleSupplierDAO_HI;
	@Autowired
	private IBaseAttachment baseAttachmentServer;
	@Autowired
	private ViewObject<TtaConAttrLineEntity_HI> ttaProposalConAttrLineDAO_HI;
	@Autowired
	private IRuleFileTemplate ruleFileTemplate;
	@Autowired
	private TempRuleDefService tempRuleDefService;
	@Autowired
	private IFastdfs fastdfsServer;
	@Autowired
	private ITtaSoleAgrt ttaSoleAgrtServer;

	private static final Map<String,String> STATUS_MAP = new HashMap<>();

	static {
		STATUS_MAP.put("A","A");//制作中
		STATUS_MAP.put("B","B");//待审批
		STATUS_MAP.put("C","C");//审批通过
		STATUS_MAP.put("D","D");//变更中
		STATUS_MAP.put("E","E");//已作废
		STATUS_MAP.put("F","F");//已取消
	}

	public TtaSoleAgrtServer() {
		super();
	}

	@Override
	public TtaSoleAgrtEntity_HI saveTtaSoleAgrt(JSONObject queryParamJSON, UserSessionBean userSessionBean) throws Exception {
		LOGGER.info("参数params: {}",queryParamJSON.toString());
		Integer userId = userSessionBean.getUserId();
		if (StringUtils.isBlank(queryParamJSON.getString("startDate")) || StringUtils.isBlank(queryParamJSON.getString("endDate"))) {
			throw new IllegalArgumentException("独家销售时间为空,请先填写再保存");
		}
		TtaSoleAgrtEntity_HI ttaSoleAgrtEntity = SaafToolUtils.setEntity(TtaSoleAgrtEntity_HI.class, queryParamJSON, ttaSoleAgrtDAO_HI, userId);
		if (SaafToolUtils.isNullOrEmpty(ttaSoleAgrtEntity.getSoleAgrtHId())) {
			ttaSoleAgrtEntity.setStatus("A");
			ttaSoleAgrtEntity.setSoleAgrtVersion("1");//版本号
			if ("standard".equals(ttaSoleAgrtEntity.getAgrtType())) {//标准
				ttaSoleAgrtEntity.setSoleAgrtCode(codeService.getExclusiveCode());
			} else {//非标准
				ttaSoleAgrtEntity.setSoleAgrtCode(codeService.getSoleNonStandarCode());
			}
			ttaSoleAgrtEntity.setApplyDate(new Date());
			ttaSoleAgrtEntity.setOperatorUserId(userId);
			ttaSoleAgrtEntity.setCreationDate(new Date());
			ttaSoleAgrtEntity.setCreatedBy(userId);
			ttaSoleAgrtEntity.setLastUpdateDate(new Date());
			ttaSoleAgrtEntity.setLastUpdatedBy(userId);
			ttaSoleAgrtEntity.setLastUpdateLogin(userId);
			ttaSoleAgrtEntity.setOrgCode(userSessionBean.getDeptCode());
			ttaSoleAgrtEntity.setOrgName(userSessionBean.getDeptName());
			ttaSoleAgrtEntity.setDeptCode(userSessionBean.getGroupCode());
			ttaSoleAgrtEntity.setDeptName(userSessionBean.getGroupName());
		}
		ttaSoleAgrtDAO_HI.saveOrUpdate(ttaSoleAgrtEntity);
		//查询Proposal信息,时间范围为(当前独家销售起始时间年度减去一年)与独家销售结束时间之间的年度
		List<TtaSaStdProposalLineEntity_HI_RO> proposalVendorList = findProposalVendor(ttaSoleAgrtEntity);
		JSONObject queryJSON = new JSONObject();
		queryJSON.put("soleAgrtHId",ttaSoleAgrtEntity.getSoleAgrtHId());
		Map<String,Object> paramsMap = new HashMap<>();
		StringBuffer sbf = new StringBuffer(TtaSoleSupplierEntity_HI_RO.QUEREY_PROPOSAL_CONTRACT_VENDOR);
		SaafToolUtils.parperParam(queryJSON,"tss.sole_agrt_h_id","soleAgrtHId",sbf,paramsMap,"=");
		List<TtaSoleSupplierEntity_HI_RO> list = ttaSoleSupplierEntity_HI_RO.findList(sbf, paramsMap);
		if (CollectionUtils.isNotEmpty(list)) {
			String supplierCode = list.get(0).getSupplierCode();
			String conditionVendor = list.get(0).getConditionVendor();//条件供应商
			//如果头信息上的供应商编号和已经存在Proposal信息不一样,那么删除已经存在的Proposal信息
			List<TtaSoleSupplierEntity_HI> soleSupplierList = new ArrayList<>();
			if (!conditionVendor.equals(ttaSoleAgrtEntity.getVendorCode())){
				ttaSoleSupplierDAO_HI.executeSqlUpdate("delete from tta_sole_supplier tss where tss.sole_agrt_h_id =" + ttaSoleAgrtEntity.getSoleAgrtHId());
				for (TtaSaStdProposalLineEntity_HI_RO entityHiRo : proposalVendorList) {
					TtaSoleSupplierEntity_HI entityHi = getSoleSupplierEntity(entityHiRo,ttaSoleAgrtEntity,userId);
					soleSupplierList.add(entityHi);
				}
			} else {//供应商都一样
				if (CollectionUtils.isNotEmpty(proposalVendorList)) {
					for (TtaSaStdProposalLineEntity_HI_RO entity_hi_ro : proposalVendorList) {
						boolean flag = false;
						for (TtaSoleSupplierEntity_HI_RO hiRo : list) {
							if (entity_hi_ro.getProposalId().equals(hiRo.getPoposalId())){
								flag = true;
							}
						}
						if (!flag){
							/*
							 同一个供应商,重新保存,不再保存数据到tta_sole_supplier
							soleSupplierList.add(getSoleSupplierEntity(entity_hi_ro,ttaSoleAgrtEntity,userId));
							*/
						}
					}
				}
			}
			if(soleSupplierList.size() > 0){
				ttaSoleSupplierDAO_HI.saveOrUpdateAll(soleSupplierList);
			}
		} else {//新增Proposal供应商信息
			if (CollectionUtils.isNotEmpty(proposalVendorList)) {
				List<TtaSoleSupplierEntity_HI> soleSupplierList = new ArrayList<>();
				for (TtaSaStdProposalLineEntity_HI_RO entityHiRo : proposalVendorList) {
					TtaSoleSupplierEntity_HI entityHi = getSoleSupplierEntity(entityHiRo,ttaSoleAgrtEntity,userId);
					soleSupplierList.add(entityHi);
				}
				ttaSoleSupplierDAO_HI.saveOrUpdateAll(soleSupplierList);
			}
		}
		return ttaSoleAgrtEntity;
	}

	private TtaSoleSupplierEntity_HI getSoleSupplierEntity(TtaSaStdProposalLineEntity_HI_RO proposalLine,TtaSoleAgrtEntity_HI soleAgrtEntityHi,int userId) throws Exception {
		TtaSoleSupplierEntity_HI entityHi = new TtaSoleSupplierEntity_HI();
		SaafBeanUtils.copyUnionProperties(proposalLine,entityHi);
		entityHi.setSoleAgrtHId(soleAgrtEntityHi.getSoleAgrtHId());
		entityHi.setPoposalCode(proposalLine.getProposalOrder());
		entityHi.setSupplierCode(proposalLine.getVendorCode());
		entityHi.setSupplierName(proposalLine.getVendorName());
		entityHi.setPoposalId(proposalLine.getProposalId());
		entityHi.setCreatedBy(userId);
		entityHi.setCreationDate(new Date());
		entityHi.setLastUpdatedBy(userId);
		entityHi.setLastUpdateDate(new Date());
		entityHi.setLastUpdateLogin(userId);
		entityHi.setOperatorUserId(userId);
		return entityHi;
	}

	private  List<TtaSaStdProposalLineEntity_HI_RO> findProposalVendor(TtaSoleAgrtEntity_HI entityHi){
		int startYear = WDatesUtils.getYear(entityHi.getStartDate(),"yyyy-MM-dd");//独家销售起始时间
		int endYear = WDatesUtils.getYear(entityHi.getEndDate(),"yyyy-MM-dd");//独家销售结束时间
		int lastYear = startYear - 1;//独家销售起始时间上一年度
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("vendorCode",entityHi.getVendorCode());
		queryParamJSON.put("effectiveStartTime",lastYear);
		queryParamJSON.put("effectiveEndTime",endYear);
		//StringBuffer sql = new StringBuffer(TtaProposalHeaderEntity_HI_RO.CONTRACT_SPLIT_PROPOSAL_VENDOR);
		StringBuffer sql = new StringBuffer(TtaProposalHeaderEntity_HI_RO.CONTRACT_EDIT_PROPOSAL);

		Map<String,Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperParam(queryParamJSON, "t.PROPOSAL_ORDER", "proposalOrder", sql, paramsMap, "like", false);
		SaafToolUtils.parperParam(queryParamJSON, "t.CONDITION_VENDOR", "vendorCode", sql, paramsMap, "like", false);
		SaafToolUtils.parperParam(queryParamJSON, "t.VENDOR_NAME", "vendorName", sql, paramsMap, "like", false);
		SaafToolUtils.parperParam(queryParamJSON, "t.PROPOSAL_YEAR", "effectiveStartTime", sql, paramsMap, ">=", false);
		SaafToolUtils.parperParam(queryParamJSON, "t.PROPOSAL_YEAR", "effectiveEndTime", sql, paramsMap, "<=", false);
		SaafToolUtils.changeQuerySort(queryParamJSON,sql,"t.PROPOSAL_YEAR asc",false);
		List<TtaSaStdProposalLineEntity_HI_RO> proposalVendorList = ttaSaStdProposalLineDAO_HI_RO.findList(sql, paramsMap);
		return proposalVendorList;
	}

	@Override
	public TtaSoleAgrtEntity_HI_RO findByRoId(JSONObject jsonObject) {
		Map<String,Object> paramsMap = new HashMap<>();
		StringBuffer sql = new StringBuffer(TtaSoleAgrtEntity_HI_RO.QUERY_SINGLE_ARGRT);
		SaafToolUtils.parperParam(jsonObject, "tsa.sole_agrt_h_id", "soleAgrtHId", sql, paramsMap, "=");
		return ttaSoleAgrtEntity_HI_RO.get(sql,paramsMap);
	}

	@Override
	public Pagination<TtaSoleAgrtEntity_HI_RO> findTtaSoleAgrtEntity_HI_RO(
			JSONObject jsonObject, Integer pageIndex, Integer pageRows){
		StringBuffer sql = new StringBuffer(TtaSoleAgrtEntity_HI_RO.QUEEY_SOLE_ARGRT);
		Map<String, Object> paramsMap = new HashMap<>();
		String vSql = TtaDeptFeeHeaderEntity_HI_RO.getTableDeptSql(jsonObject.getInteger("varUserId"),jsonObject.getString("varUserType")) ;
		if(!SaafToolUtils.isNullOrEmpty(vSql)){
			sql.append(" and exists (select 1 from").append(vSql).append(" where tsa.org_code = dept.department_code and tsa.dept_code = dept.group_code) ");
		}
		SaafToolUtils.parperParam(jsonObject, "tsa.sole_agrt_code", "soleAgrtCode", sql, paramsMap, "=");
		SaafToolUtils.parperParam(jsonObject, "tsa.dept_code", "deptCode", sql, paramsMap, "=");
		SaafToolUtils.parperParam(jsonObject, "tsa.dept_name", "deptName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "tsa.vendor_code", "vendorCode", sql, paramsMap, "like");
		SaafToolUtils.parperParam(jsonObject, "tsa.vendor_name", "vendorName", sql, paramsMap, "=");
		SaafToolUtils.parperParam(jsonObject, "tsa.agrt_type", "agrtType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(jsonObject, "tsa.status", "status", sql, paramsMap, "=");
		SaafToolUtils.parperParam(jsonObject, "tsa.creation_date", "creationDate", sql, paramsMap, ">=");
		SaafToolUtils.parperParam(jsonObject, "tsa.creation_date", "creationDateEnd", sql, paramsMap, "<=");
		SaafToolUtils.changeQuerySort(jsonObject,sql,"tsa.creation_date desc",false);
		Pagination<TtaSoleAgrtEntity_HI_RO> ttaSoleAgrtEntityHiRoPagination = ttaSoleAgrtEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
		return ttaSoleAgrtEntityHiRoPagination;
	}	
	
	@Override
	public Pagination<TtaSoleSupplierEntity_HI_RO> findTtaSoleSupplierEntity_HI_RO(
			JSONObject jsonObject, Integer pageIndex, Integer pageRows)
			throws Exception {
		StringBuffer sql = new StringBuffer("select sole_supplier_id soleSupplierId,sole_agrt_h_id soleAgrtHId,poposal_code poposalCode,supplier_code supplierCode,supplier_name supplierName," +
				"version_num versionNum,creation_date creationDate," +
				"created_by createdBy,last_updated_by lastUpdatedBy,last_update_date lastUpdateDate from TTA_SOLE_SUPPLIER where 1 = 1 ");
		Map<String, Object> paramsMap = new HashMap<>(); 	
		Pagination<TtaSoleSupplierEntity_HI_RO> supplierItemHeader = ttaSoleSupplierEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
		return supplierItemHeader;
	}

	@Override
	public Pagination<TtaSoleAgrtInfoEntity_HI_RO> findTtaSoleAgrtInfoEntity_HI_RO(
			JSONObject parameters, Integer pageIndex, Integer pageRows)
			throws Exception {
		StringBuffer sql = new StringBuffer("select sole_agrt_info_id soleAgrtInfoId,sole_supplier_id soleSupplierId,is_peb isPeb," +
				"supplier_code supplierCode,supplier_name supplierName,org_code orgCode,sole_brand_cn soleBrandCn," +
				"sale_start_date saleStartDate,sale_end_date saleEndDate,sale_region saleRegion,is_new_sole isNewSole," +
				"is_special isSpecial,is_auto_deferral isAutoDeferral,channel_type channelType," +
				"product_type productType,is_ec_channel isEcChannel,is_exception isException,exception_remark exceptionRemark," +
				"is_covered isCovered,is_end_argt isEndArgt,is_change_clause isChangeClause,is_sign_account isSignAccount," +
				"contract_situation contractSituation,remark remark,contract_accept_date contractAcceptDate," +
				"sale_effect_date saleEffectDate,is_special_approval isSpecialApproval,is_renewal isRenewal," +
				"sys_start_date sysStartDate,version_num versionNum from TTA_SOLE_AGRT_INFO where 1 = 1 ");
		Map<String, Object> paramsMap = new HashMap<>(); 	
		Pagination<TtaSoleAgrtInfoEntity_HI_RO> supplierItemHeader = ttaSoleAgrtInfoEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
		return supplierItemHeader;
	}

	@Override
	public Pagination<TtaSoleItemEntity_HI_RO> findTtaSoleItemEntity_HI_RO(
			JSONObject parameters, Integer pageIndex, Integer pageRows)
			throws Exception {
		StringBuffer sql = new StringBuffer("select sole_agrt_info_id soleAgrtInfoId,group_code groupCode,brand brand,bar_code barCode," +
				"item_code itemCode,item_name itemName,effective_date effectiveDate," +
				"expiration_date expirationDate,version_num versionNum,creation_date creationDate," +
				"created_by createdBy,last_updated_by lastUpdatedBy from TTA_SOLE_ITEM where 1 = 1 ");
		Map<String, Object> paramsMap = new HashMap<>(); 	
		Pagination<TtaSoleItemEntity_HI_RO> supplierItemHeader = ttaSoleItemEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
		return supplierItemHeader;
	}

	@Override
	public TtaSoleAgrtEntity_HI sumbitSoleAgrtInfo(JSONObject parameters, int userId) throws Exception{
		Assert.notNull(parameters.getInteger("soleAgrtHId"),"独家协议信息单据没有保存,请重新保存!");
		TtaSoleAgrtEntity_HI ttaSoleAgrtEntity_hi = ttaSoleAgrtDAO_HI.getById(parameters.getInteger("soleAgrtHId"));
		LOGGER.info("参数soleAgrtHId :{}",parameters.getInteger("soleAgrtHId"));
		if (ttaSoleAgrtEntity_hi == null) {
			throw new IllegalArgumentException("独家协议信息单据没有保存,请重新保存");
		}
		ttaSoleAgrtEntity_hi.setStatus(STATUS_MAP.get("B"));//待审批
		ttaSoleAgrtDAO_HI.saveOrUpdate(ttaSoleAgrtEntity_hi);
		return ttaSoleAgrtEntity_hi;
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
		TtaSoleAgrtEntity_HI instance = ttaSoleAgrtDAO_HI.getById(id);

		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL"://草稿
				orderStatus = "A";//制作中
				instance.setStatus(orderStatus);
				ttaSoleAgrtDAO_HI.saveOrUpdate(instance);
				break;
			case "ALLOW"://审批通过
				orderStatus = "C";
				instance.setStatus(orderStatus);
				//instance.setApplyDate(new Date());//审批通过时间
				instance.setApproveDate(new Date());//审批通过时间
				ttaSoleAgrtDAO_HI.saveOrUpdate(instance);
				//查询最大的附件
				//BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(headerObject.getLong("soleNonStandarHeaderId"), "TTA_SOLE_NON_STANDAR_HEADER");
				//存入文件相关信息到tta_con_attr_line表中
				ResultFileEntity resultFileEntity = uploadSoleAndNonAgrtFile(instance, userId);
				TtaConAttrLineEntity_HI ttaConAttrLineEntity_hi = new TtaConAttrLineEntity_HI();
				ttaConAttrLineEntity_hi.setOperatorUserId(userId);
				ttaConAttrLineEntity_hi.setFileId(resultFileEntity.getFileId().intValue());
				ttaConAttrLineEntity_hi.setFileName(resultFileEntity.getSourceFileName());
				if ("standard".equals(instance.getAgrtType())) {
					ttaConAttrLineEntity_hi.setFileType("2");//独家协议-标准
				} else {
					ttaConAttrLineEntity_hi.setFileType("3");//独家协议-非标
				}
				ttaConAttrLineEntity_hi.setFileUrl(resultFileEntity.getFilePath());
				ttaConAttrLineEntity_hi.setOrderVersion(Integer.valueOf(instance.getSoleAgrtVersion()));
				ttaConAttrLineEntity_hi.setVendorCode(instance.getVendorCode());
				ttaConAttrLineEntity_hi.setOrderNo(instance.getSoleAgrtCode());
				ttaConAttrLineEntity_hi.setOrgCode(instance.getOrgCode());
				ttaConAttrLineEntity_hi.setOrgName(instance.getOrgName());
				ttaConAttrLineEntity_hi.setDeptCode(instance.getDeptCode());
				ttaConAttrLineEntity_hi.setDeptName(instance.getDeptName());
				ttaProposalConAttrLineDAO_HI.saveOrUpdate(ttaConAttrLineEntity_hi);
				break;
			case "DRAFT"://审批驳回
				orderStatus = "A";//制作中
				instance.setStatus(orderStatus);
				ttaSoleAgrtDAO_HI.saveOrUpdate(instance);
				break;
			case "APPROVAL"://审批中
				orderStatus = "B";
				instance.setStatus(orderStatus);
				ttaSoleAgrtDAO_HI.saveOrUpdate(instance);
				break;
			case "CLOSED":
				orderStatus = "E";//已作废
				instance.setStatus(orderStatus);
				ttaSoleAgrtDAO_HI.saveOrUpdate(instance);
				break;

		}

		instance.setOperatorUserId(userId);
		ttaSoleAgrtDAO_HI.saveOrUpdate(instance);
		ttaSoleAgrtDAO_HI.fluch();
	}

	private ResultFileEntity uploadSoleAndNonAgrtFile(TtaSoleAgrtEntity_HI instance,int userId) throws Exception{
		String fileName = "";
		InputStream in = null;
		ByteArrayInputStream inputStream = null ;
		ByteArrayOutputStream byteOutputStream = null ;
		ByteArrayOutputStream byteOut = null;
		XWPFTemplate template = null;
		ByteArrayInputStream byteInput = null;
		ResultFileEntity resultFileEntity = null;
		Integer soleAgrtHId = instance.getSoleAgrtHId();
		String type = instance.getAgrtType();
		List<TtaSoleAgrtInfoEntity_HI> byProperty = ttaSoleAgrtInfoEntity_HI.findByProperty(new JSONObject().fluentPut("soleAgrtHId",soleAgrtHId));
		Integer soleAgrtInfoId = null;
		TempRuleDefEntity_HI_RO tempRuleDef = null;
		Integer ruleId = null;
		if (CollectionUtils.isNotEmpty(byProperty)) {
			soleAgrtInfoId = byProperty.get(0).getSoleAgrtInfoId();
			//查询独家协议模板信息
			tempRuleDef = findRuleFile(byProperty.get(0));
		}
		if (tempRuleDef != null) {
			ruleId = tempRuleDef.getRulId();
		}
		try {
			Assert.notNull(tempRuleDef,"缺失独家协议模板信息");
			Assert.notNull(soleAgrtHId,"独家协议标准头信息未保存,请先保存");
			Assert.notNull(soleAgrtInfoId,"当前合同下载缺失独家协议信息主键");
			Assert.notNull(type,"类型不能为空");
			//合成pdf所需要的字节数组
			List<byte[]> pdfList = new ArrayList<>();
			if ("standard".equals(type)) {
				RuleFileTemplateEntity_HI_RO entity = ruleFileTemplate.findByBusinessType("1");
				if (entity == null || entity.getFileContent() == null) {
					LOGGER.info(this.getClass() + "独家协议模板word文件未上传!");
					throw new IllegalArgumentException("独家协议模板word文件未上传!");
				}
				if (ruleId != null) {
					Map<String, Object> resultMap = tempRuleDefService.generateDocx(ruleId, false);
					in = (FileInputStream)resultMap.get("fis");
					//fileName = new String((resultMap.get("fileName") + "").getBytes(), "ISO8859-1") + ".pdf";
					fileName = resultMap.get("fileName") + ".pdf";
				} else {
					throw new IllegalArgumentException("当前选择的独家协议信息未找到合同模板");
				}

				//替换,设置参数
				Map<String, Object> underLineMap = setParam(soleAgrtInfoId, soleAgrtHId);
				template = XWPFTemplate.compile(in).render(underLineMap);
				byteOut = new ByteArrayOutputStream();
				template.write(byteOut);
				ByteArrayInputStream wordStream = new ByteArrayInputStream(byteOut.toByteArray());
				ByteArrayOutputStream pdfOutputStream = Word2PdfUtil.getDocToPdfOutputStream(wordStream);
				pdfOutputStream.close();
				wordStream.close();
				pdfList.add(pdfOutputStream.toByteArray());
			} else {
				//非标
				BaseAttachmentEntity_HI_RO fileInfo = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(soleAgrtInfoId), "TTA_SOLE_AGRT_INFO");
				inputStream = (ByteArrayInputStream)fastdfsServer.getInputStream(fileInfo.getBucketName(), fileInfo.getPhyFileName());
				byteOutputStream = Word2PdfUtil.getDocToPdfOutputStream((ByteArrayInputStream)inputStream);
				//fileName = new String(fileInfo.getSourceFileName().getBytes(), "ISO8859-1") + ".pdf";
				String sourceFileName = fileInfo.getSourceFileName();
				fileName = sourceFileName.substring(0,sourceFileName.lastIndexOf(".")) + ".pdf";
				byteOutputStream.close();
				pdfList.add(byteOutputStream.toByteArray());
			}

			byte[] pdfItemByte = null;
			if (CollectionUtils.isNotEmpty(byProperty)) {
				TtaSoleAgrtInfoEntity_HI soleAgrtInfoEntity_hi = byProperty.get(0);
				//如果产品类型为全品牌独家,不输出指定产品清单模板
				if (!"1".equals(soleAgrtInfoEntity_hi.getProductType())){
					//获取pdf流
					pdfItemByte = ttaSoleAgrtServer.findItemList(soleAgrtInfoId);
				}
			}

			if (pdfItemByte != null) {
				pdfList.add(pdfItemByte);
			}
			byteInput = new ByteArrayInputStream(MergePdf.mergePdfFiles(pdfList));
			//插入水印和logo
			byteInput = ttaSoleAgrtServer.insertWatermarkAndLogo(soleAgrtHId,byteInput);

			//插入文件到文件服务器上
			resultFileEntity = fastdfsServer.fileUpload(byteInput,fileName, "TTA_SOLE_CONTRACT_FILE", Long.valueOf(soleAgrtHId), userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件上传异常:" + e.getMessage());
		} finally {
			if (in != null) {
				in.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (byteOutputStream != null) {
				byteOutputStream.close();
			}
			if (byteOut != null) {
				byteOut.close();
			}
			if (template != null) {
				template.close();
			}
			if (byteInput != null) {
				byteInput.close();
			}
		}
		return resultFileEntity;
	}

	private TempRuleDefEntity_HI_RO findRuleFile(TtaSoleAgrtInfoEntity_HI instance) {
			StringBuffer sql = new StringBuffer();
			sql.append(TempRuleDefEntity_HI_RO.SELECT_RULEINFO_LIST);
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			JSONObject queryParamJSON = new JSONObject();
			queryParamJSON.put("soleResouceType",instance.getChannelType());//独家渠道类别
			queryParamJSON.put("soleProductType",instance.getProductType());//独家产品类型
			queryParamJSON.put("isIncludeEc",instance.getIsEcChannel());//是否包含电商渠道
			queryParamJSON.put("isIncludeSpecial",instance.getIsException());//是否包含独家渠道例外情形
			SaafToolUtils.parperParam(queryParamJSON, "t.is_enable", "isEnable", sql, paramsMap, "=");
			SaafToolUtils.parperParam(queryParamJSON, "t.rule_name", "ruleName", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "t.sole_resouce_type", "soleResouceType", sql, paramsMap, "=");
			SaafToolUtils.parperParam(queryParamJSON, "t.sole_product_type", "soleProductType", sql, paramsMap, "=");
			SaafToolUtils.parperParam(queryParamJSON, "t.is_include_ec", "isIncludeEc", sql, paramsMap, "=");
			SaafToolUtils.parperParam(queryParamJSON, "t.is_include_special", "isIncludeSpecial", sql, paramsMap, "=");
			List<TempRuleDefEntity_HI_RO> list = tempRuleDefDAO_HI_RO.findList(sql, paramsMap);
			TempRuleDefEntity_HI_RO ruleDefEntity = null;
			if (list != null && !list.isEmpty()) {
				ruleDefEntity = list.get(0);
			}
			return ruleDefEntity;
		}

	@Override
	public TtaSoleAgrtEntity_HI ttaSoleAgrtDiscard(JSONObject parameters, int userId) throws Exception {
		Integer soleAgrtHId = parameters.getInteger("soleAgrtHId");
		Assert.notNull(soleAgrtHId,"独家协议信息单据没有保存,不能作废!");
		LOGGER.info("单据状态:{}","A".equals(parameters.getString("status") )? "制作中":"其他状态");
		if (!"A".equals(parameters.getString("status"))){
			throw new IllegalArgumentException("当前单据状态不在制作中,不能作废");
		}
		TtaSoleAgrtEntity_HI ttaSoleAgrtEntity_hi = ttaSoleAgrtDAO_HI.getById(soleAgrtHId);
		if (!"A".equals(ttaSoleAgrtEntity_hi.getStatus())) {
			throw new IllegalArgumentException("当前单据状态不在制作中,不能作废");
		} else {
			ttaSoleAgrtEntity_hi.setStatus(STATUS_MAP.get("E"));//作废
			ttaSoleAgrtEntity_hi.setLastUpdateDate(new Date());
			ttaSoleAgrtEntity_hi.setLastUpdatedBy(userId);
			ttaSoleAgrtEntity_hi.setLastUpdateLogin(userId);
			ttaSoleAgrtDAO_HI.saveOrUpdate(ttaSoleAgrtEntity_hi);
		}
		return ttaSoleAgrtEntity_hi;
	}

	@Override
	public TtaSoleAgrtEntity_HI ttaSoleAgrtCancal(JSONObject parameters, int userId) throws Exception {
		Integer soleAgrtHId = parameters.getInteger("soleAgrtHId");
		Assert.notNull(soleAgrtHId,"独家协议信息单据没有保存,不能点击取消操作!");
		if (!"C".equals(parameters.getString("status"))){
			throw new IllegalArgumentException("当前单据状态不在审批通过,不能取消");
		}
		TtaSoleAgrtEntity_HI ttaSoleAgrtEntity_hi = ttaSoleAgrtDAO_HI.getById(soleAgrtHId);
		if (!"C".equals(ttaSoleAgrtEntity_hi.getStatus())) {
			throw new IllegalArgumentException("当前单据状态不在审批通过,不能取消");
		} else {
			ttaSoleAgrtEntity_hi.setStatus(STATUS_MAP.get("F"));//已取消状态
			ttaSoleAgrtEntity_hi.setLastUpdateDate(new Date());
			ttaSoleAgrtEntity_hi.setLastUpdatedBy(userId);
			ttaSoleAgrtEntity_hi.setLastUpdateLogin(userId);
			ttaSoleAgrtDAO_HI.saveOrUpdate(ttaSoleAgrtEntity_hi);
		}
		return ttaSoleAgrtEntity_hi;
	}

	@Override
	public  byte[] findItemList(Integer soleAgrtInfoId) throws Exception{
		byte[] bytes = null;
	    List<TtaSoleItemEntity_HI_RO> resultList = new ArrayList<>();
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("productType", "2");
		//queryMap.put("checked", "1");
		queryMap.put("soleAgrtInfoId", soleAgrtInfoId);
        List<Map<String, Object>> listMap = ttaSoleAgrtInfoEntity_HI.queryNamedSQLForList(TtaSoleItemEntity_HI_RO.QUERY_PDF_ITEM_SQL, queryMap);
        if (listMap != null) {
            resultList = JSON.parseArray(SaafToolUtils.toJson(listMap), TtaSoleItemEntity_HI_RO.class);
        }
		if (resultList != null) {
			List<List<String>> lists = new ArrayList<>();
			for (TtaSoleItemEntity_HI_RO entityHiRo: resultList) {
				List<String> list = new ArrayList<>();
				list.add(entityHiRo.getItemCode());//ITEM编码
				list.add(entityHiRo.getItemName());//条码
				list.add(entityHiRo.getBarCode());//item名称
				lists.add(list);
			}
			//未满指定行数（16行），需要添加至16行
			int diffRow = lists.size() - 11; //15
			if (diffRow < 0) {
				for (int idx = 0; idx < Math.abs(diffRow); idx ++) {
					List<String> list = new ArrayList<>();
					//三列
					for (int i = 0; i < 3; i ++) {
						list.add("");
					}
					lists.add(list);
				}
			}
			OperateDTO dto = new  OperateDTO();
			DynamicTableDTO dtd = new DynamicTableDTO();
			dtd.setFontSize(11);
			dtd.setTableIndex(0);
			dtd.setRows(lists);
			List<DynamicTableDTO> dynamicTableDTOS = new ArrayList<>();
			dynamicTableDTOS.add(dtd);
			dto.setDynamicRows(dynamicTableDTOS);
			//排序 防止替换前面的列后 后面的列索引变换
			Collections.sort(dto.getDynamicRows());
			InputStream fileInputStream = this.getClass().getResourceAsStream("/template/soleWordTemplate.docx");
			XWPFDocument document = new XWPFDocument(fileInputStream);
			WordUtils.dynamicTableReplace(document,dto.getDynamicRows());
			ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
			document.write(fileOutputStream);
			ByteArrayInputStream pdfInputStream = new ByteArrayInputStream(fileOutputStream.toByteArray());
			ByteArrayOutputStream pdfOutputStream = Word2PdfUtil.getDocToPdfOutputStream(pdfInputStream);
			bytes = pdfOutputStream.toByteArray();
			//关闭流
			pdfInputStream.close();
			fileOutputStream.close();
			fileInputStream.close();
		}
        return bytes;
	}

	@Override
	public JSONObject callSoleAgrtChangStatus(JSONObject paramsJSON, Integer userId) throws Exception {
		LOGGER.info("用戶id：{}",userId);
		LOGGER.info("**************独家协议标准进行变更操作开始*****************");
		Integer soleAgrtHId = paramsJSON.getInteger("soleAgrtHId");//原始soleAgrtHId
		Assert.notNull(soleAgrtHId,"您还未保存此单据,不能进行变更操作");
		JSONObject returnObject = new JSONObject();
		TtaSoleAgrtEntity_HI byId = ttaSoleAgrtDAO_HI.getById(soleAgrtHId);
		if (!"C".equals(byId.getStatus())){
			throw new IllegalArgumentException("您当前单据状态不在审批通过中,不能进行变更操作");
		}
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("单据状态为{}",byId.getStatus());
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramsMap.put("pkId",soleAgrtHId);
			paramsMap.put("userId",userId);
			LOGGER.info("执行变更前的soleAgrtHId:{}", soleAgrtHId);
			resultMap = ttaSoleAgrtDAOHi.callSoleAgrtChangStatus(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");
			Integer changeSoleAgrtHId = (Integer) resultMap.get("soleAgrtHId");
			LOGGER.info("执行变更后的soleAgrtHId:{},变更日志:{}", changeSoleAgrtHId,xMsg);
			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //回滚事务
			};
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("执行变更失败,失败原因:" + e.getMessage());
		}
		Integer returnHearder = (Integer)resultMap.get("soleAgrtHId");//返回的soleAgrtHId
		TtaSoleAgrtEntity_HI agrtEntity_hi = ttaSoleAgrtDAO_HI.getById(returnHearder);
		returnObject.put("result",(JSONObject)JSON.toJSON(resultMap));
		returnObject.put("data",(JSONObject)JSON.toJSON(agrtEntity_hi));
		LOGGER.info("**************独家协议标准进行变更操作结束*****************");
		return returnObject;
	}

	/**
	 * 根据独家渠道类别,独家产品类型等条件查询规则设定信息
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public TempRuleDefEntity_HI_RO findRuleInfoByCondition(JSONObject queryParamJSON) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(TempRuleDefEntity_HI_RO.SELECT_RULEINFO_LIST);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		queryParamJSON.put("soleResouceType",queryParamJSON.getString("channelType"));//独家渠道类别
		queryParamJSON.put("soleProductType",queryParamJSON.getString("productType"));//独家产品类型
		queryParamJSON.put("isIncludeEc",queryParamJSON.getString("isEcChannel"));//是否包含电商渠道
		queryParamJSON.put("isIncludeSpecial",queryParamJSON.getString("isException"));//是否包含独家渠道例外情形
		SaafToolUtils.parperParam(queryParamJSON, "t.is_enable", "isEnable", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "t.rule_name", "ruleName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "t.sole_resouce_type", "soleResouceType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "t.sole_product_type", "soleProductType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "t.is_include_ec", "isIncludeEc", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "t.is_include_special", "isIncludeSpecial", sql, paramsMap, "=");
		//SaafToolUtils.parperParam(new JSONObject().fluentPut("ruleId", ruleId), "t.rul_id", "ruleId", sql, paramsMap, "=");
		List<TempRuleDefEntity_HI_RO> list = tempRuleDefDAO_HI_RO.findList(sql, paramsMap);
		TempRuleDefEntity_HI_RO ruleDefEntity = null;
		if (list != null && !list.isEmpty()) {
			ruleDefEntity = list.get(0);
		}
		return ruleDefEntity;
	}

	@Override
	public TtaSoleAgrtEntity_HI updateExclusiveSkipStatus(JSONObject jsonObject, UserSessionBean userSessionBean) throws Exception {
		Integer supplementAgreementHead = jsonObject.getInteger("soleAgrtHId");
		String isSkipApprove = jsonObject.getString("isSkipApprove");
		TtaSoleAgrtEntity_HI instance = ttaSoleAgrtDAO_HI.getById(supplementAgreementHead);
		if (org.apache.commons.lang.StringUtils.isNotBlank(isSkipApprove)) {
			instance.setLastUpdateDate(new Date());
			instance.setIsSkipApprove(isSkipApprove);
			ttaSoleAgrtDAO_HI.update(instance);
		}
		return instance;
	}

	@Override
	public TtaSoleAgrtEntity_HI checkTtaSoleAgrtBillCodeStatus(JSONObject parameters, int userId) {
		Integer supplementAgreementHead = parameters.getInteger("soleAgrtHId");
		TtaSoleAgrtEntity_HI instance = ttaSoleAgrtDAO_HI.getById(supplementAgreementHead);
		return instance;
	}

	@Override
	public Map<String,Object> setParam(Integer soleAgrtInfoId, Integer soleAgrtHId) {
		LOGGER.info("************************合同下载替换参数开始**********************");
		TtaSoleAgrtInfoEntity_HI infoEntity = ttaSoleAgrtInfoEntity_HI.getById(soleAgrtInfoId);
		StringBuffer sql = new StringBuffer(TtaSoleAgrtEntity_HI_RO.QUEEY_SOLE_ARGRT);
		sql.append(" and tsa.sole_agrt_h_id =:soleAgrtHId");
		TtaSoleAgrtEntity_HI_RO entityHiRo = ttaSoleAgrtEntity_HI_RO.get(sql, new JSONObject().fluentPut("soleAgrtHId", soleAgrtHId));

		//获取系统的时间
		LocalDateTime time = LocalDateTime.now();
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");//年份
		DateTimeFormatter monthFormat= DateTimeFormatter.ofPattern("MM");//月份
		DateTimeFormatter dayFormat= DateTimeFormatter.ofPattern("dd");//日
		String year = time.format(yearFormat);
		String substringYear = year.substring(2);//截取年份
		String month = time.format(monthFormat);
		//String day = time.format(dayFormat);
		Map<String, Object> underLineMap = new HashMap<>();
		underLineMap.put("company", getTextRenderDataByParamValue(entityHiRo.getOwnerCompanyName()));//屈臣氏甲方公司
		underLineMap.put("vendorNbr", getTextRenderDataByParamValue(infoEntity.getSupplierCode()));
		underLineMap.put("vendorName", getTextRenderDataByParamValue(infoEntity.getSupplierName()));
		underLineMap.put("year",getTextRenderDataByParamValue(substringYear));
		underLineMap.put("month",getTextRenderDataByParamValue(month));
		underLineMap.put("soleBrandCn",getTextRenderDataByParamValue(infoEntity.getSoleBrandCn()));//独家品牌
		Date saleStartDate = infoEntity.getSaleStartDate();
		Date saleEndDate = infoEntity.getSaleEndDate();
		LocalDate saleStartLocalDate = date2LocalDate(saleStartDate);//独家销售起始时间
		LocalDate saleEndLocalDate = date2LocalDate(saleEndDate);//独家销售结束时间
		String saleStartYear = saleStartLocalDate.format(yearFormat);//年
		String saleStartMonth = saleStartLocalDate.format(monthFormat);//月
		String saleStartDay = saleStartLocalDate.format(dayFormat);//日
		String saleEndYear = saleEndLocalDate.format(yearFormat);//年
		String saleEndMonth = saleEndLocalDate.format(monthFormat);//月
		String saleEndDay = saleEndLocalDate.format(dayFormat);//日
		saleStartYear = saleStartYear.substring(2);
		saleEndYear = saleEndYear.substring(2);
		underLineMap.put("saleStartDateYear",getTextRenderDataByParamValue(saleStartYear));
		underLineMap.put("saleStartDateMonth",getTextRenderDataByParamValue(saleStartMonth));
		underLineMap.put("saleStartDateDay",getTextRenderDataByParamValue(saleStartDay));
		underLineMap.put("saleEndDateYear",getTextRenderDataByParamValue(saleEndYear));
		underLineMap.put("saleEndDateMonth",getTextRenderDataByParamValue(saleEndMonth));
		underLineMap.put("saleEndDateDay",getTextRenderDataByParamValue(saleEndDay));
		underLineMap.put("seviceName",getTextRenderDataByParamValue(StringUtils.isBlank(infoEntity.getExceptionRemark()) ? "" : infoEntity.getExceptionRemark()));
		return underLineMap;
	}

	private TextRenderData getTextRenderDataByParamValue(String value){
		//添加下划线
		TextRenderData data = new TextRenderData();
		Style style = new Style();
		style.setUnderLine(true);
		data.setStyle(style);
		data.setText(value);
		return data;
	}

	private static LocalDate date2LocalDate(Date date) {
		if(null == date) {
			return null;
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	@Override
	public ByteArrayInputStream insertWatermarkAndLogo(Integer soleAgrtHId, ByteArrayInputStream byteInput) throws Exception {
		TtaSoleAgrtEntity_HI soleAgrtEntity = ttaSoleAgrtDAO_HI.getById(soleAgrtHId);
		String soleAgrtCode = soleAgrtEntity.getSoleAgrtCode();
		String soleAgrtVersion = soleAgrtEntity.getSoleAgrtVersion();
		String codeVersion = soleAgrtCode + "_" + soleAgrtVersion;//单据号_版本
		File outFile = Files.createTempFile("soleAgrt_" + UUID.randomUUID().toString().replace("-", ""), ".pdf").toFile();
		//File outFile = new File(ResourceUtils.getUploadTempPath());
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));

		PdfReader reader = new PdfReader(byteInput);
		PdfStamper stamper = new PdfStamper(reader, bos);

		int total = reader.getNumberOfPages() + 1;
		//BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
		ClassPathResource resource = new ClassPathResource("/fonts/STCAIYUN.TTF");
		//使用华文彩云字体,通过加载resource资源目录下的字体文件
		BaseFont base = BaseFont.createFont(resource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		PdfGState gs = new PdfGState();
		PdfGState gs1 = new PdfGState();
		PdfContentByte content;
		for (int i = 1; i < total; i++) {
			content = stamper.getOverContent(i);// 在内容上方加水印
			gs.setFillOpacity(0.8f);
			content.setGState(gs);
			content.beginText();
			if(i == 1){
				//文字加粗
				//设置文本描边宽度
				content.setLineWidth(0.5f);
				//设置文本为描边模式
				content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
				content.setFontAndSize(baseFont, 13);
				content.showTextAligned(Element.ALIGN_CENTER,codeVersion,470,753,0);
			}

			//************添加文本水印start***************
/*			content.setTextRenderingMode(0);
			content.setColorFill(BaseColor.LIGHT_GRAY);//Color.LIGHT_GRAY  new BaseColor(192, 192, 192)
			content.setFontAndSize(base, 50);
			content.showTextAligned(Element.ALIGN_CENTER, "WATSONS",300 , 400, 45);*/
			//************添加文本水印end**************

			//************************添加水印图片start*********************************
			Image imageLogo = Image.getInstance(QrcodeUtils.getImagePath());
			// 设置坐标 绝对位置 X Y
			imageLogo.setAbsolutePosition(425,795);
			//imageLogo.setRotation(45);// 旋转 弧度
			// 设置旋转角度
			imageLogo.setRotationDegrees(0);// 旋转 角度
			// 设置等比缩放
			imageLogo.scalePercent(15);// 依照比例缩放
			//imageLogo.scaleAbsolute(50,100);//自定义大小
			// 设置透明度
			//gs.setFillOpacity(0.3f);
			//content.setGState(gs);
			// 添加水印图片
			content.addImage(imageLogo);
			//************************添加水印图片end*********************************

			//**********************添加二维码start********************************
			Image image = Image.getInstance(QrcodeUtils.createQrcode(codeVersion, 200,null));
			image.setAbsolutePosition(510, 20); // image of the absolute 720,5
			image.scaleToFit(80, 80); //设置图片大小
			gs1.setFillOpacity(0.8f);
			content.setGState(gs1);
			content.addImage(image);
			content.setColorFill(BaseColor.BLACK);//Color.BLACK
			content.setFontAndSize(base, 8);
			//content.showTextAligned(Element.ALIGN_CENTER, "下载时间：" + waterMarkName + "", 300, 10, 0);
			//**********************添加二维码end********************************
			content.endText();
		}
		stamper.close();
		bos.close();
		reader.close();
		//System.out.println(outFile);
		byte[] bytes = Files.readAllBytes(outFile.toPath());
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		byteArrayInputStream.close();
		return byteArrayInputStream;
	}

	@Override
	public ByteArrayInputStream findTtaSoleAgrtHeader(Integer soleAgrtHId, ByteArrayInputStream byteInput) throws Exception{
		TtaSoleAgrtEntity_HI entityHi = ttaSoleAgrtDAO_HI.getById(soleAgrtHId);
		if ("C".equals(entityHi.getStatus())) {
			//插入水印和logo
			byteInput = ttaSoleAgrtServer.insertWatermarkAndLogo(soleAgrtHId,byteInput);
		}
		return byteInput;
	}

	@Override
	public List<TtaSoleAgrtEntity_HI> findSoleAgrt(String soleAgrtCode) {
		List<TtaSoleAgrtEntity_HI> soleList = ttaSoleAgrtDAO_HI.findByProperty(new JSONObject().fluentPut("soleAgrtCode", soleAgrtCode));
		return soleList;
	}

	@Override
	public List<TtaSoleAgrtInfoEntity_HI> findSoleAgrtInfo(Integer soleAgrtHId) {
		List<TtaSoleAgrtInfoEntity_HI> byProperty = ttaSoleAgrtInfoEntity_HI.findByProperty(new JSONObject().fluentPut("soleAgrtHId",soleAgrtHId));
		return byProperty;
	}

}
