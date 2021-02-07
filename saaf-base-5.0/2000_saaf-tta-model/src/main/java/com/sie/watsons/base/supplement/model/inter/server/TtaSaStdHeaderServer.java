package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.Word2PdfUtil;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.dao.TtaSaStdHeaderDAO_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdFieldLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdProposalLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdFieldLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdProposalLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaTabLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaSaStdFieldLine;
import com.sie.watsons.base.supplement.model.util.DocxUtil;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdFieldCfgLineEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplDefHeaderEntity_HI_RO;
import com.sie.watsons.base.withdrawal.utils.QrcodeUtils;
import com.sie.watsons.base.withdrawal.utils.WDatesUtils;
import com.yhg.base.utils.DateUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.supplement.model.inter.ITtaSaStdHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

@Component("ttaSaStdHeaderServer")
public class TtaSaStdHeaderServer extends BaseCommonServer<TtaSaStdHeaderEntity_HI> implements ITtaSaStdHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdHeaderServer.class);

	@Autowired
	private ViewObject<TtaSaStdHeaderEntity_HI> ttaSaStdHeaderDAO_HI;
	@Autowired
	private BaseViewObject<TtaSaStdHeaderEntity_HI_RO> ttaSaStdHeaderDAO_HI_RO;
	@Autowired
	private BaseViewObject<TtaSaStdTplDefHeaderEntity_HI_RO> ttaSaStdTplDefHeaderDAO_HI_RO;
	@Autowired
	private BaseViewObject<TtaSaStdFieldCfgLineEntity_HI_RO> ttaSaStdFieldCfgLineDAO_HI_RO;
	@Autowired
	private GenerateCodeService codeService;
	@Autowired
	private ITtaSaStdFieldLine ttaSaStdFieldLineServer;
	@Autowired
	private TtaSaStdHeaderDAO_HI ttaSaStdHeaderDAO;
	@Autowired
	private IFastdfs fastDfsServer;
	@Autowired
	private IBaseAttachment baseAttachmentServer;
	@Autowired
	private BaseViewObject<BaseLookupValuesEntity_HI_RO> baseLookupValuesDAO_HI_RO;
	@Autowired
	private ViewObject<TtaSaStdFieldLineEntity_HI> ttaSaStdFieldLineDAO_HI;
	@Autowired
	private BaseViewObject<TtaSaTabLineEntity_HI_RO> ttaSaTabLineDAO_HI_RO;
	@Autowired
	private BaseViewObject<TtaSaStdProposalLineEntity_HI_RO> ttaSaStdProposalLineDAO_HI_RO;
	@Autowired
	private ViewObject<TtaSaStdProposalLineEntity_HI> ttaSaStdProposalLineDAO_HI;
	@Autowired
	private ViewObject<TtaConAttrLineEntity_HI> ttaProposalConAttrLineDAO_HI;


	public static final String FUNCTION_ID = "TTA_SA_STD_TPL_DEF_HEADER_PARAM";

	public TtaSaStdHeaderServer() {
		super();
	}

	@Override
	public Pagination<TtaSaStdHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<>();
		StringBuffer sql = new StringBuffer(TtaSaStdHeaderEntity_HI_RO.SELECT_SA_STD_HEADER_LIST);
		String vSql = TtaDeptFeeHeaderEntity_HI_RO.getTableDeptSql(queryParamJSON.getInteger("varUserId"),queryParamJSON.getString("varUserType")) ;
		if(!SaafToolUtils.isNullOrEmpty(vSql)){
			sql.append(" and exists (select 1 from").append(vSql).append(" where v.org_code = dept.department_code and v.dept_code = dept.group_code) ");

		}
		SaafToolUtils.parperParam(queryParamJSON, "v.sa_std_code", "saStdCode", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.vendor_code", "vendorCode", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.vendor_name", "vendorName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.status", "status", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "to_char(v.creation_date,'yyyy-mm-dd hh24:mi:ss')", "creationDate", sql, paramsMap, ">=");
		SaafToolUtils.parperParam(queryParamJSON, "to_char(v.creation_date,'yyyy-mm-dd hh24:mi:ss')", "creationDateEnd", sql, paramsMap, "<=");
		SaafToolUtils.parperParam(queryParamJSON, "v.effective_start_time", "effectiveStartTime", sql, paramsMap, ">=");
		SaafToolUtils.parperParam(queryParamJSON, "v.effective_end_time", "effectiveEndTime", sql, paramsMap, "<=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.sa_std_header_id desc", false);
		return ttaSaStdHeaderDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
	}

	@Override
	public List<TtaSaStdTplDefHeaderEntity_HI_RO> findSupplementAgrTemlateTreeList(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer sql = new StringBuffer(TtaSaStdTplDefHeaderEntity_HI_RO.SUPPLEMENT_AGRMENT_STANDARD_TREE);
		//SaafToolUtils.parperHbmParam(TtaSaStdTplDefHeaderEntity_HI_RO.class,queryParamJSON,"","",sql,queryParamMap,"=");
		changeQuerySort(queryParamJSON,sql,"tsst.tpl_code asc",false);
		return ttaSaStdTplDefHeaderDAO_HI_RO.findList(sql,queryParamMap);
	}

	@Override
	public TtaSaStdHeaderEntity_HI saveOrUpdate(JSONObject jsonObject, UserSessionBean userSessionBean) throws Exception {
		JSONObject supplementAgreementHead = jsonObject.getJSONObject("supplementAgreementHead");
		JSONArray dynamicFieldlDataList = jsonObject.getJSONArray("dynamicFieldlDataList");
		Integer userId = userSessionBean.getUserId();
		TtaSaStdHeaderEntity_HI entityHi = SaafToolUtils.setEntity(TtaSaStdHeaderEntity_HI.class, supplementAgreementHead, ttaSaStdHeaderDAO_HI, userId);
		boolean flag = false;
		if (SaafToolUtils.isNullOrEmpty(entityHi.getSaStdHeaderId())) {
			String saStdCode = "SSA";
			String ttaGeneralCode = codeService.getTTAGeneralCode(saStdCode);
			entityHi.setSaStdCode(ttaGeneralCode);
			entityHi.setSaStdVersion(1);//版本从1开始,变更的时候才会使用到这个版本号
			if (SaafToolUtils.isNullOrEmpty(entityHi.getStatus())) {
				entityHi.setStatus("A");
			}
			//设置用户的大部门,小部门信息
			entityHi.setOrgCode(userSessionBean.getDeptCode());//大部门
			entityHi.setOrgName(userSessionBean.getDeptName());
			entityHi.setDeptCode(userSessionBean.getGroupCode());//小部门
			entityHi.setDeptName(userSessionBean.getGroupName());
			flag = true;
		} else {
			entityHi.setLastUpdateDate(new Date());
			entityHi.setLastUpdatedBy(userId);
			entityHi.setLastUpdateLogin(userId);
		}
		ttaSaStdHeaderDAO_HI.saveOrUpdate(entityHi);
		//保存补充协议拓展信息
		if (CollectionUtils.isNotEmpty(dynamicFieldlDataList)) {
			ttaSaStdFieldLineServer.saveSaStaDynamicFieldlDataList(dynamicFieldlDataList,entityHi,userId);
		}
		//查询匹配Proposal供应商信息
		List<TtaSaStdProposalLineEntity_HI_RO> proposalVendor = findProposalVendor(entityHi);
		if (flag) {//补充协议头信息未存在
			if (CollectionUtils.isNotEmpty(proposalVendor)) {
				List<TtaSaStdProposalLineEntity_HI> proposalLineEntityHiList = new ArrayList<>();
				for (TtaSaStdProposalLineEntity_HI_RO entityHiRo : proposalVendor) {
					TtaSaStdProposalLineEntity_HI lineEntity = getSaStdProposalLineEntity(entityHiRo,entityHi,userId);
					proposalLineEntityHiList.add(lineEntity);
				}
				ttaSaStdProposalLineDAO_HI.saveOrUpdateAll(proposalLineEntityHiList);
			}
		} else {
			JSONObject queryJSON = new JSONObject();
			queryJSON.put("saStdHeaderId",entityHi.getSaStdHeaderId());
			Map<String,Object> paramsMap = new HashMap<>();
			StringBuffer sbf = new StringBuffer(TtaSaStdProposalLineEntity_HI_RO.QUERY);
			SaafToolUtils.parperParam(queryJSON,"t.sa_std_header_id","saStdHeaderId",sbf,paramsMap,"=");
			List<TtaSaStdProposalLineEntity_HI_RO> list = ttaSaStdProposalLineDAO_HI_RO.findList(sbf, paramsMap);
			if (CollectionUtils.isNotEmpty(list)) {
				String vendorCode = list.get(0).getVendorCode();
				//已存在的Proposal信息的供应商是否等于补充协议头信息的供应商,不等于删除,然后新增Proposal信息
				ArrayList<TtaSaStdProposalLineEntity_HI> proposalLineEntityHis = new ArrayList<>();
				if (!vendorCode.equals(entityHi.getVendorCode())) {
					ttaSaStdProposalLineDAO_HI.executeSqlUpdate("delete from tta_sa_std_proposal_line t where t.sa_std_header_id =" + entityHi.getSaStdHeaderId());
					if (CollectionUtils.isNotEmpty(proposalVendor)) {
						for (TtaSaStdProposalLineEntity_HI_RO entityHiRo : proposalVendor) {
							TtaSaStdProposalLineEntity_HI lineEntity = getSaStdProposalLineEntity(entityHiRo,entityHi,userId);
							proposalLineEntityHis.add(lineEntity);
						}
						if (proposalLineEntityHis.size() > 0) {
							ttaSaStdProposalLineDAO_HI.saveOrUpdateAll(proposalLineEntityHis);
						}
					}
				}
			}
		}
		return entityHi;
	}

	private TtaSaStdProposalLineEntity_HI getSaStdProposalLineEntity(TtaSaStdProposalLineEntity_HI_RO entityHiRo, TtaSaStdHeaderEntity_HI entityHi, Integer userId) throws Exception {
		TtaSaStdProposalLineEntity_HI lineEntityHi =  new TtaSaStdProposalLineEntity_HI();
		SaafBeanUtils.copyUnionProperties(entityHiRo,lineEntityHi);
		lineEntityHi.setSaStdHeaderId(entityHi.getSaStdHeaderId());
		lineEntityHi.setCreatedBy(userId);
		lineEntityHi.setCreationDate(new Date());
		lineEntityHi.setLastUpdatedBy(userId);
		lineEntityHi.setLastUpdateDate(new Date());
		lineEntityHi.setLastUpdateLogin(userId);
		lineEntityHi.setOperatorUserId(userId);
		return lineEntityHi;
	}

	private List<TtaSaStdProposalLineEntity_HI_RO> findProposalVendor(TtaSaStdHeaderEntity_HI entityHi) throws Exception{
		JSONObject queryParamJSON = new JSONObject();
		Integer year = LocalDate.now().getYear();
		queryParamJSON.put("vendorCode",entityHi.getVendorCode());
		queryParamJSON.put("year",year);//本年度
		StringBuffer sql = new StringBuffer(TtaProposalHeaderEntity_HI_RO.CONTRACT_SPLIT_PROPOSAL_VENDOR);
		Map<String,Object> paramsMap = new HashMap<>();
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("vendorCode"))) {
			sql.append(" and t.CONDITION_VENDOR = '" + queryParamJSON.getString("vendorCode") + "'");
		}
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("year"))) {
			sql.append(" and t.PROPOSAL_YEAR = '" + queryParamJSON.getInteger("year") + "'");
		}

		//SaafToolUtils.parperParam(queryParamJSON, "t.PROPOSAL_ORDER", "proposalOrder", sql, paramsMap, "like", false);
		//SaafToolUtils.parperParam(queryParamJSON, "t.VENDOR_CODE", "vendorCode", sql, paramsMap, "=", false);
		//SaafToolUtils.parperParam(queryParamJSON, "t.VENDOR_NAME", "vendorName", sql, paramsMap, "like", false);
		//SaafToolUtils.parperParam(queryParamJSON, "t.PROPOSAL_YEAR", "year", sql, paramsMap, "=", false);
		//SaafToolUtils.parperParam(queryParamJSON, "t.PROPOSAL_YEAR", "effectiveEndTime", sql, paramsMap, "=", false);
		List<TtaSaStdProposalLineEntity_HI_RO> findList = ttaSaStdProposalLineDAO_HI_RO.findList(sql, paramsMap);
		return findList;
	}

	@Override
	public TtaSaStdHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		Map<String,Object> paramsMap = new HashMap<>();
		StringBuffer sql = new StringBuffer(TtaSaStdHeaderEntity_HI_RO.SELECT_SA_STD_HEADER_LIST);
		SaafToolUtils.parperParam(queryParamJSON, "v.sa_std_header_id", "saStdHeaderId", sql, paramsMap, "=");
		return ttaSaStdHeaderDAO_HI_RO.get(sql,paramsMap);
	}

	@Override
	public List<TtaSaStdFieldCfgLineEntity_HI_RO> saveFindSupplementExpandInfo(JSONObject queryParamJSON, int userId) {
		Integer saStdHeaderId = queryParamJSON.getInteger("saStdHeaderId");
		if (saStdHeaderId != null) {
			String deleteSql = "delete from tta_sa_std_field_line ts where ts.sa_std_header_id =" + saStdHeaderId;
			ttaSaStdFieldLineDAO_HI.executeSqlUpdate(deleteSql);
		}
		Map<String,Object> paramsMap = new HashMap<>();
		StringBuffer sql = new StringBuffer(TtaSaStdFieldCfgLineEntity_HI_RO.QUERE_STD_FIELD);
		queryParamJSON.put("isEnable","Y");
		SaafToolUtils.parperParam(queryParamJSON, "tssf.sa_std_tpl_def_header_id", "saStdTplDefHeaderId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "tssf.is_enable", "isEnable", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON,sql,"tssf.sa_std_tpl_field_cfg_id asc",false);
		return ttaSaStdFieldCfgLineDAO_HI_RO.findList(sql,paramsMap);
	}

	@Override
	public TtaSaStdHeaderEntity_HI saveByDisSicradHeader(JSONObject paramJSON, int userId) throws Exception {
		LOGGER.info("参数:{}",paramJSON.toString());
		Integer saStdHeaderId = paramJSON.getInteger("saStdHeaderId");
		Assert.notNull(saStdHeaderId,"您还未保存此单据,暂不能进行作废操作");
		TtaSaStdHeaderEntity_HI stdHeaderEntityHi = ttaSaStdHeaderDAO_HI.getById(saStdHeaderId);
		Assert.notNull(stdHeaderEntityHi,"您还未保存此单据,暂不能进行作废操作");
		if ("A".equals(stdHeaderEntityHi.getStatus())) {
			stdHeaderEntityHi.setStatus("D");//已作废
			stdHeaderEntityHi.setLastUpdateDate(new Date());
			stdHeaderEntityHi.setLastUpdatedBy(userId);
			stdHeaderEntityHi.setLastUpdateLogin(userId);
			stdHeaderEntityHi.setOperatorUserId(userId);
			ttaSaStdHeaderDAO_HI.saveOrUpdate(stdHeaderEntityHi);
		} else {
			throw new IllegalArgumentException("单据状态需要为制作中才能进行作废操作");
		}
		return stdHeaderEntityHi;
	}

	@Override
	public void updateStatus(JSONObject paramsJSON, int userId,UserSessionBean userSessionBean) throws Exception {
		Integer id = paramsJSON.getIntValue("id");//单据Id
		TtaSaStdHeaderEntity_HI instance = ttaSaStdHeaderDAO_HI.getById(id);
		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL"://审批驳回
			case "REVOKE"://撤回
				orderStatus = "A";
				instance.setStatus(orderStatus);
				ttaSaStdHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "ALLOW"://审批通过
				orderStatus = "C";
				instance.setStatus(orderStatus);
				instance.setApproveDate(new Date());//审批通过时间
				instance.setOperatorUserId(userId);
				ttaSaStdHeaderDAO_HI.saveOrUpdate(instance);
				LOGGER.info("补充协议(标准)通过,执行上传补充协议合同文件到文件服务器上 start");
				//查询最大的文件
				//BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(headerObject.getLong("saNonStandarHeaderId"), "TTA_SA_NON_STANDAR_HEADER");
				BaseAttachmentEntity_HI info = uploadSaStdHeaderFile(instance,userId,userSessionBean);
				LOGGER.info("补充协议(标准)通过,执行上传补充协议合同文件到文件服务器上 end");
				TtaConAttrLineEntity_HI ttaConAttrLineEntity_hi = new TtaConAttrLineEntity_HI();
				ttaConAttrLineEntity_hi.setOperatorUserId(userId);
				ttaConAttrLineEntity_hi.setFileId(info.getFileId().intValue());
				ttaConAttrLineEntity_hi.setFileName(info.getSourceFileName());
				ttaConAttrLineEntity_hi.setFileType("4");//补充协议-标准
				ttaConAttrLineEntity_hi.setFileUrl(info.getFilePath());
				ttaConAttrLineEntity_hi.setOrderVersion(instance.getSaStdVersion());
				ttaConAttrLineEntity_hi.setVendorCode(instance.getVendorCode());
				ttaConAttrLineEntity_hi.setOrderNo(instance.getSaStdCode());
				ttaConAttrLineEntity_hi.setOrgCode(instance.getOrgCode());
				ttaConAttrLineEntity_hi.setOrgName(instance.getOrgName());
				ttaConAttrLineEntity_hi.setDeptCode(instance.getDeptCode());
				ttaConAttrLineEntity_hi.setDeptName(instance.getDeptName());
				ttaProposalConAttrLineDAO_HI.saveOrUpdate(ttaConAttrLineEntity_hi);
				LOGGER.info("***保存补充协议(标准)相关信息到TTA_CON_ATTR_LINE表成功***");
				break;
			case "DRAFT"://草稿
				orderStatus = "A";
				instance.setStatus(orderStatus);
				ttaSaStdHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "APPROVAL"://审批中
				orderStatus = "B";
				instance.setStatus(orderStatus);
				ttaSaStdHeaderDAO_HI.saveOrUpdate(instance);
				break;
			case "CLOSED":
				orderStatus = "E";
				instance.setStatus(orderStatus);
				//ttaSaStdHeaderDAO_HI.saveOrUpdate(instance);
				break;

		}

		instance.setOperatorUserId(userId);
		ttaSaStdHeaderDAO_HI.saveOrUpdate(instance);
		ttaSaStdHeaderDAO_HI.fluch();
	}

	private BaseAttachmentEntity_HI uploadSaStdHeaderFile(TtaSaStdHeaderEntity_HI instance, int userId,UserSessionBean userSessionBean) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("saStdHeaderId",instance.getSaStdHeaderId());
		Long fileId = this.savePrint(jsonObject, userSessionBean,userId);
		BaseAttachmentEntity_HI baseAttachmentInfo = baseAttachmentServer.findBaseAttachmentInfo(fileId);
		return baseAttachmentInfo;
	}

	@Override
	public JSONObject callSupplementAgreementStandardChangStatus(JSONObject paramsJSON, Integer userId) throws Exception {
		LOGGER.info("用戶id：{}",userId);
		LOGGER.info("**************补充协议标准进行变更操作开始*****************");
		Integer saStdHeaderId = paramsJSON.getInteger("saStdHeaderId");//原始saStdHeaderId
		Assert.notNull(saStdHeaderId,"您还未保存此单据,不能进行变更操作");
		JSONObject returnObject = new JSONObject();
		if (!"C".equals(paramsJSON.getString("status"))){
			throw new IllegalArgumentException("您当前单据状态不在审批通过中,不能进行变更操作");
		}
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("单据状态为{}",paramsJSON.getString("status"));
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paramsMap.put("pkId",saStdHeaderId);
			paramsMap.put("userId",userId);
			LOGGER.info("执行变更前的saStdHeaderId:{}", saStdHeaderId);
			resultMap = ttaSaStdHeaderDAO.callSupplementAgreementStandardChangStatus(paramsMap);
			Integer xFlag = (Integer) resultMap.get("xFlag");
			String xMsg = (String) resultMap.get("xMsg");
			Integer changeSaStdHeaderId = (Integer) resultMap.get("saStdHeaderId");
			LOGGER.info("执行变更后的saStdHeaderId:{},变更日志:{}", changeSaStdHeaderId,xMsg);
			if (xFlag!=1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //回滚事务
			};
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("执行变更失败,失败原因:" + e.getMessage());
		}
		Integer returnHearder = (Integer)resultMap.get("saStdHeaderId");//返回的saStdHeaderId
		TtaSaStdHeaderEntity_HI headerDAOHiRoById = ttaSaStdHeaderDAO_HI.getById(returnHearder);
		returnObject.put("result",(JSONObject)JSON.toJSON(resultMap));
		returnObject.put("data",(JSONObject)JSON.toJSON(headerDAOHiRoById));
		LOGGER.info("**************补充协议标准进行变更操作结束*****************");
		return returnObject;
	}

	private List<List<TtaSaTabLineEntity_HI_RO>> findStaSaTabline(JSONObject queryParamJSON){
		Map<String,Object> paramsMap = new HashMap<>();
		StringBuffer sql = new StringBuffer(TtaSaTabLineEntity_HI_RO.QUERY_SA_TAB_LINE);
		SaafToolUtils.parperParam(queryParamJSON,"tstl.sa_std_header_id","saStdHeaderId",sql,paramsMap,"=");
		sql.append("  order by tstl.y_point asc, tstl.x_poiMultipartFilent asc");
		List<TtaSaTabLineEntity_HI_RO> returnList = ttaSaTabLineDAO_HI_RO.findList(sql, paramsMap);
		List<List<TtaSaTabLineEntity_HI_RO>>  planarList = new ArrayList<>();
		int flagNum = 0;
		List<TtaSaTabLineEntity_HI_RO> linearArray = new ArrayList<>();
		for (TtaSaTabLineEntity_HI_RO lineEntityHiRo : returnList) {
			if (lineEntityHiRo.getYPoint().intValue() != flagNum) {
				flagNum = lineEntityHiRo.getYPoint().intValue();
				linearArray = new ArrayList<>();
				planarList.add(linearArray);
			}
			linearArray.add(lineEntityHiRo);
		}
		return planarList;
	}


	@Override
	public Long savePrint(JSONObject queryJSONParams, UserSessionBean userSessionBean,int userId) throws Exception{
		LOGGER.info("*************打印开始************");
		Integer saStdHeaderId = queryJSONParams.getInteger("saStdHeaderId");
		LOGGER.info("补充协议(标准)头信息id:{}",saStdHeaderId);
		Assert.notNull(saStdHeaderId,"您还未保存单据信息,请完善数据并保存再打印");
		TtaSaStdHeaderEntity_HI saStdHeaderDAO_hiById = ttaSaStdHeaderDAO_HI.getById(saStdHeaderId);
		//1.获取动态拓展信息
		List<TtaSaStdFieldLineEntity_HI_RO> expandInfo = ttaSaStdFieldLineServer.findSupplementExpandInfo(queryJSONParams, userId);
		if (CollectionUtils.isEmpty(expandInfo)) {
			throw new IllegalArgumentException("合同打印,补充协议拓展信息字段值不能为空");
		}

		//查询补充协议表格信息
		List<List<TtaSaTabLineEntity_HI_RO>> staSaTabline = findStaSaTabline(queryJSONParams);

		//2.获取补充协议标准模板的word路径
		BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(saStdHeaderDAO_hiById.getTplId().longValue(), FUNCTION_ID);
		if (info == null){
			throw new IllegalArgumentException("当前单据没有补充协议标准word模板,请检查");
		}

		//3.替换word模板数据开始
		Map<String, String> params = copyParamsFromList(expandInfo);
		InputStream inStream = fastDfsServer.getInputStream(info.getBucketName(),info.getPhyFileName());
		Assert.notNull(inStream,"获取补充协议标准word模板失败");
		LOGGER.info(".inStream----{}",inStream);
		XWPFDocument document = new XWPFDocument(inStream);
		LOGGER.info("文档对象--{}",document);

		String tableKey = "#{GRIDE}";// 在文档中需要替换插入表格的位置
		//替换段落里的变量
		DocxUtil.replaceInParagraphByVariable(document,params);
		//指定位置插入表格
		inserTable(document,tableKey,staSaTabline);

		//下载文件
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		document.write(os);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(os.toByteArray());

		//word转换成pdf
		os = Word2PdfUtil.getDocToPdfOutputStream(byteArrayInputStream);

		InputStream is = new ByteArrayInputStream(os.toByteArray());
		//插入水印和二维码
		if ("C".equals(saStdHeaderDAO_hiById.getStatus())) {//审批通过状态才插入水印
			is = this.insertWatermarkAndLogo(saStdHeaderDAO_hiById,is);
		}

		String fileName = saStdHeaderDAO_hiById.getVendorName() +"_" + DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm") +"补充协议标准.pdf";

		ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, fileName,"TTA_SA_STD_HEADER_CONTRACT",Long.valueOf(saStdHeaderId.toString()),userId);
		Long fileId = resultFileEntity.getFileId();
		DocxUtil.close(inStream);
		DocxUtil.close(is);
		DocxUtil.close(os);
		DocxUtil.close(byteArrayInputStream);
		return fileId;
	}

	/**
	 * 插入水印和二维码
	 * @param saStdHeaderDAO_hiById
	 * @param is
	 * @return
	 */
	private InputStream insertWatermarkAndLogo(TtaSaStdHeaderEntity_HI saStdHeaderDAO_hiById, InputStream is) throws Exception{
		String saStdCode = saStdHeaderDAO_hiById.getSaStdCode();//单据号
		Integer saStdVersion = saStdHeaderDAO_hiById.getSaStdVersion();
		String codeVersion = saStdCode + "_" + saStdVersion;//单据号_版本
		File outFile = Files.createTempFile("SaStdHeader_" + UUID.randomUUID().toString().replace("-",""), ".pdf").toFile();
		//File outFile = new File(ResourceUtils.getUploadTempPath());
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));

		PdfReader reader = new PdfReader(is);
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
				content.showTextAligned(Element.ALIGN_CENTER,codeVersion,470,770,0);
			}

			//************添加文本水印start***************
			//content.setTextRenderingMode(0);
			//content.setColorFill(BaseColor.LIGHT_GRAY);//Color.LIGHT_GRAY  new BaseColor(192, 192, 192)
			//content.setFontAndSize(base, 50);
			//content.showTextAligned(Element.ALIGN_CENTER, "WATSONS",300 , 400, 45);
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
			//gs.setFillOpacity(0.1f);
			//content.setGState(gs);
			// 添加水印图片
			content.addImage(imageLogo);
			//************************添加水印图片end*********************************

			//********************添加二维码水印start******************
			Image image = Image.getInstance(QrcodeUtils.createQrcode(codeVersion, 200,null));
			image.setAbsolutePosition(510, 20); // image of the absolute 720,5
			image.scaleToFit(80, 80); //设置图片大小
			gs1.setFillOpacity(0.8f);
			content.setGState(gs1);
			content.addImage(image);
			content.setColorFill(BaseColor.BLACK);//Color.BLACK
			content.setFontAndSize(base, 8);
			//content.showTextAligned(Element.ALIGN_CENTER, "下载时间：" + waterMarkName + "", 300, 10, 0);
			//*********************添加二维码水印end***********************
			content.endText();
		}
		stamper.close();
		bos.close();
		reader.close();
		byte[] bytes = Files.readAllBytes(outFile.toPath());
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		byteArrayInputStream.close();
		return byteArrayInputStream;
	}


/*
	@Override
	public Long print(JSONObject queryJSONParams, UserSessionBean userSessionBean) throws Exception{
		LOGGER.info("*************打印开始************");
		Integer saStdHeaderId = queryJSONParams.getInteger("saStdHeaderId");
		Integer userId = userSessionBean.getUserId();
		Assert.notNull(saStdHeaderId,"您还未保存单据信息,请完善数据并保存再打印");
		TtaSaStdHeaderEntity_HI saStdHeaderDAO_hiById = ttaSaStdHeaderDAO_HI.getById(saStdHeaderId);
		//1.获取动态拓展信息
		List<TtaSaStdFieldLineEntity_HI_RO> expandInfo = ttaSaStdFieldLineServer.findSupplementExpandInfo(queryJSONParams, userId);
		if (CollectionUtils.isEmpty(expandInfo)) {
			throw new IllegalArgumentException("合同打印,补充协议拓展信息字段值不能为空");
		}

		//查询补充协议表格信息
		List<List<TtaSaTabLineEntity_HI_RO>> staSaTabline = findStaSaTabline(queryJSONParams);

		//2.获取补充协议标准模板的word路径
		BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(saStdHeaderDAO_hiById.getTplId().longValue(), FUNCTION_ID);
		if (info == null){
			throw new IllegalArgumentException("当前单据没有补充协议标准word模板,请检查");
		}

		//3.替换word模板数据开始
		Map<String, String> params = copyParamsFromList(expandInfo);
		InputStream inStream = fastDfsServer.getInputStream(info.getBucketName(),info.getPhyFileName());
		LOGGER.info(".inStream{}",inStream);
		//XWPFDocument document = new XWPFDocument(inStream);
		//LOGGER.info("文档对象--{}",document);

		//InputStream resourceAsStream = this.getClass().getResourceAsStream("/template/test.docx");
		//XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(filePath));


		Map<String,Object> underLineMap = new HashMap<>();
		XWPFTemplate template  = XWPFTemplate.compile(inStream).render(underLineMap);
		FileOutputStream out = new FileOutputStream("F:\\补充协议.docx");
		template.write(out);


		String tableKey = "#{GRIDE}";// 在文档中需要替换插入表格的位置
		//替换段落里的变量
		//DocxUtil.replaceInParagraphByVariable(document,params);
		//指定位置插入表格
		//inserTable(document,tableKey,staSaTabline);



		//下载文件
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		//document.write(os);
		byte[] bytes = os.toByteArray();
		InputStream is = new ByteArrayInputStream(bytes);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

		//做测试
		//out.write(stream.toByteArray());
		//out.flush();
		//stream.close();
		//out.close();
		//writeToLocal("F:\\model.docx",is);


		//word转换成pdf
		os = Word2PdfUtil.getDocToPdfOutputStream(byteArrayInputStream);
		is = new ByteArrayInputStream(os.toByteArray());
		String fileName = saStdHeaderDAO_hiById.getVendorName() +"_" + DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm") +"补充协议标准.pdf";
		ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, fileName);
		Long fileId = resultFileEntity.getFileId();
		//DocxUtil.close(inStream);
		DocxUtil.close(is);
		DocxUtil.close(os);
		DocxUtil.close(byteArrayInputStream);
		return fileId;
	}
*/

	//在word指定位置插入表格
	private void inserTable(XWPFDocument document,String tableKey,List<List<TtaSaTabLineEntity_HI_RO>> staSaTabline){
		LOGGER.info("--------插入表格开始--------");
		List<XWPFParagraph> paragraphs = document.getParagraphs();//获取所有的段落
		if (paragraphs != null && paragraphs.size() > 0) {
			for (XWPFParagraph paragraph : paragraphs) {
				List<XWPFRun> runs = paragraph.getRuns();//获取每个段落的runs
				System.out.println("paragraph:" + paragraph.getText());
				for (XWPFRun run : runs) {
					String runText = run.getText(0);
					if (runText != null && runText.trim().contains(tableKey)) {
						run.setText(runText.replace(tableKey,""),0);
						//创建一个游标
						XmlCursor cursor = paragraph.getCTP().newCursor();
						// 在指定游标位置插入表格
						XWPFTable table = document.insertNewTbl(cursor);
						CTTblPr tablePr = table.getCTTbl().getTblPr();//表格属性
						CTTblWidth width = tablePr.addNewTblW();//创建一个表格宽度对象
						CTJc ctJc = tablePr.addNewJc();
						ctJc.setVal(STJc.CENTER);
						width.setType(STTblWidth.DXA);
						width.setW(BigInteger.valueOf(9072));//设置宽度
						this.inserTableInfo(table,staSaTabline);
						break;
					}
				}
			}
		}
	}

	private void inserTableInfo(XWPFTable table,List<List<TtaSaTabLineEntity_HI_RO>> staSaTabline) {
		//输出表格数据
		if (CollectionUtils.isNotEmpty(staSaTabline)) {
			for (int i = 0; i < staSaTabline.size(); i++) {
				List<TtaSaTabLineEntity_HI_RO> entityHiRos = staSaTabline.get(i);
				XWPFTableRow row1 = null;
				if (i == 0) {
					row1 = table.getRow(0);//获取第一行
				} else {
					row1 = table.createRow();
				}
				for (int j = 0; j < entityHiRos.size(); j++) {
					TtaSaTabLineEntity_HI_RO entity = entityHiRos.get(j);
					if (i == 0) {// 第一行创建了多少列，后续增加的行自动增加列
						if (j != 0) {
							CTTcPr cPr = row1.createCell().getCTTc().addNewTcPr();
							cPr.addNewVAlign().setVal(STVerticalJc.CENTER);
							CTTblWidth ctTblWidth = cPr.addNewTcW();
							ctTblWidth.setType(STTblWidth.DXA);
							ctTblWidth.setW(BigInteger.valueOf(360 * 5));
						} else {
							CTTcPr ctTcPr = row1.getCell(j).getCTTc().addNewTcPr();
							ctTcPr.addNewVAlign().setVal(STVerticalJc.CENTER);
							CTTblWidth ctTblWidth = ctTcPr.addNewTcW();
							ctTblWidth.setType(STTblWidth.DXA);
							ctTblWidth.setW(BigInteger.valueOf(360 * 5));
						}
						row1.getCell(j).setText(entity.getGridPointValue());
					} else {
						row1.getCell(j).setText(entity.getGridPointValue());
					}

				}
			}
		}
	}

	private String findBaseLookupValues(String lookupType, String lookupCode){
		String meaning = "";
		Map<String,Object> paramsMap = new HashMap<>();
		paramsMap.put("lookupType", lookupType);
		paramsMap.put("lookupCode", lookupCode);
		StringBuffer sql = new StringBuffer(TtaSaStdHeaderEntity_HI_RO.QUERY_DIC_RESULT_SQL);
		sql.append(" and blv.lookup_type=:lookupType and  blv.lookup_code=:lookupCode ");
		List<BaseLookupValuesEntity_HI_RO> list = baseLookupValuesDAO_HI_RO.findList(sql, paramsMap);
		if (CollectionUtils.isNotEmpty(list)) {
			meaning = list.get(0).getMeaning();
		}
		return meaning;
	}

	private Map<String,String> copyParamsFromList(List<TtaSaStdFieldLineEntity_HI_RO> list){
		String key;
		String value;
		Map<String,String> params = new HashMap<>();
		for (TtaSaStdFieldLineEntity_HI_RO hiRo : list) {
			String dicCode = hiRo.getDicCode();
			if (StringUtils.isNotBlank(dicCode)) {
				value = findBaseLookupValues(dicCode, hiRo.getFiledValue());
			} else {
				value = hiRo.getFiledValue();
			}
			//key = "${" + hiRo.getTplCode() + "}";
			if (StringUtils.isBlank(value)) {
				value = "";
			}
			key = hiRo.getTplCode();
			params.put(key,value);
		}
		return params;
	}

	private void writeToLocal(String destination, InputStream input) throws IOException {
		int index;
		byte[] bytes = new byte[1024];
		FileOutputStream out = new FileOutputStream(destination);
		while ((index = input.read(bytes)) != -1) {
			out.write(bytes,0,index);
			out.flush();
		}
		out.close();
		input.close();

	}

	@Override
	public TtaSaStdHeaderEntity_HI updateSupplementSkipStatus(JSONObject jsonObject, UserSessionBean userSessionBean) throws Exception {
		Integer supplementAgreementHead = jsonObject.getInteger("supplementAgreementHead");
		String isSkipApprove = jsonObject.getString("isSkipApprove");
		TtaSaStdHeaderEntity_HI instance = ttaSaStdHeaderDAO_HI.getById(supplementAgreementHead);
		if (org.apache.commons.lang.StringUtils.isNotBlank(isSkipApprove)) {
			instance.setLastUpdateDate(new Date());
			instance.setIsSkipApprove(isSkipApprove);
			ttaSaStdHeaderDAO_HI.update(instance);
		}
		return instance;
	}


}
