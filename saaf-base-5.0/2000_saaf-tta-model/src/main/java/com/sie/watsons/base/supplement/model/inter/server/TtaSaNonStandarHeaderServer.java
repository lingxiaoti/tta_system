package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;

import com.deepoove.poi.XWPFTemplate;
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
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.TtaSaNonStandarLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdProposalLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaNonStandarHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaNonStandarLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdProposalLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.util.DocxUtil;
import com.sie.watsons.base.withdrawal.utils.QrcodeUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.supplement.model.entities.TtaSaNonStandarHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.supplement.model.inter.ITtaSaNonStandarHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("ttaSaNonStandarHeaderServer")
public class TtaSaNonStandarHeaderServer extends BaseCommonServer<TtaSaNonStandarHeaderEntity_HI> implements ITtaSaNonStandarHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaNonStandarHeaderServer.class);

	@Autowired
	private ViewObject<TtaSaNonStandarHeaderEntity_HI> ttaSaNonStandarHeaderDAO_HI;

	@Autowired
	private ViewObject<TtaConAttrLineEntity_HI> ttaProposalConAttrLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaSaNonStandarHeaderEntity_HI_RO> ttaSaNonStandarHeaderDAO_HI_RO;

	@Autowired
	private GenerateCodeService codeService;

	@Autowired
	private IBaseAttachment baseAttachmentServer;

	@Autowired
	private IFastdfs fastdfsServer;

	@Autowired
	private BaseViewObject<TtaSaStdProposalLineEntity_HI_RO> ttaSaStdProposalLineDAO_HI_RO;

	@Autowired
	private ViewObject<TtaSaStdProposalLineEntity_HI> ttaSaStdProposalLineDAO_HI;
	@Autowired
	private BaseViewObject<TtaSaNonStandarLineEntity_HI_RO> ttaSaNonStandarLineDAO_HI_RO;
	@Autowired
	private ViewObject<TtaSaNonStandarLineEntity_HI> ttaSaNonStandarLineDAO_HI;


	public TtaSaNonStandarHeaderServer() {
		super();
	}

	@Override
	public Pagination<TtaSaNonStandarHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaSaNonStandarHeaderEntity_HI_RO.TTA_ITEM_V);
		sql.append(" and tsnsh.status != 'E' ");
		Map<String, Object> paramsMap = new HashMap<String, Object>();

		SaafToolUtils.parperParam(queryParamJSON, "tsnsh.order_no", "orderNo", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "tsnsh.vendor_code", "vendorCode", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "tsnsh.vendor_name", "vendorName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "bu.user_full_name", "createdByName", sql, paramsMap, "like");

		if  (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDate1"))) {
			sql.append(" and to_char(tsnsh.creation_Date,'YYYY-MM-DD') >= :creationDate1");
			paramsMap.put("creationDate1",queryParamJSON.getString("creationDate1"));
		}
		if  (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDate2"))) {
			sql.append(" and to_char(tsnsh.creation_Date,'YYYY-MM-DD') <= :creationDate2");
			paramsMap.put("creationDate2",queryParamJSON.getString("creationDate2"));
		}

		//SaafToolUtils.parperParam(queryParamJSON, "to_char(tsnsh.creation_Date,'YYYY-MM-DD')", "creationDate1", sql, paramsMap, ">=");
		//SaafToolUtils.parperParam(queryParamJSON, "to_char(tsnsh.creation_Date,'YYYY-MM-DD')", "creationDate2", sql, paramsMap, "<=");
		SaafToolUtils.parperParam(queryParamJSON, "tsnsh.status", "status", sql, paramsMap, "=");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tsnsh.sa_non_standar_header_id desc", false);
		Pagination<TtaSaNonStandarHeaderEntity_HI_RO> findList = ttaSaNonStandarHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public void updateStatus(Integer pkId,Integer userId) throws Exception{
		if (pkId == null) {
			return;
		}
		TtaSaNonStandarHeaderEntity_HI instance = ttaSaNonStandarHeaderDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		if ( !("A".equals(instance.getStatus())) ) {
			throw new IllegalArgumentException("单据状态不是 制单中 不可作废");
		}
		instance.setStatus("E");
		instance.setOperatorUserId(userId);
		ttaSaNonStandarHeaderDAO_HI.update(instance);
	}

	@Override
	public TtaSaNonStandarHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		SaafToolUtils.validateJsonParms(queryParamJSON,"saNonStandarHeaderId");
		StringBuffer sql = new StringBuffer();
		sql.append(TtaSaNonStandarHeaderEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tsnsh.sa_non_standar_header_id", "saNonStandarHeaderId", sql, paramsMap, "=");
		TtaSaNonStandarHeaderEntity_HI_RO proposalHeadEntity = (TtaSaNonStandarHeaderEntity_HI_RO)ttaSaNonStandarHeaderDAO_HI_RO.get(sql,paramsMap);
		return proposalHeadEntity;
	}

	@Override
	public TtaSaNonStandarHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, UserSessionBean userSessionBean, int userId) throws Exception {
		ByteArrayOutputStream byteOut = null;
		XWPFTemplate template = null;
		ByteArrayInputStream wordStream = null;
		JSONObject  json = new JSONObject();
		if (null != paramsJSON.getInteger("saNonStandarHeaderId")) {
			TtaSaNonStandarHeaderEntity_HI_RO byRoId = findByRoId(paramsJSON);
			if (!"A".equals(byRoId.getStatus())) {
				throw new IllegalArgumentException("单据状态不是 制单中 不可保存");
			}
		}
		boolean flag = false ;
		TtaSaNonStandarHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaSaNonStandarHeaderEntity_HI.class, paramsJSON, ttaSaNonStandarHeaderDAO_HI, userId);
		if(SaafToolUtils.isNullOrEmpty(instance.getSaNonStandarHeaderId())) {
			instance.setStatus("A");
			instance.setOrderVersion(1);
			instance.setOrgCode(userSessionBean.getDeptCode());
			instance.setOrgName(userSessionBean.getDeptName());
			instance.setDeptCode(userSessionBean.getGroupCode());
			instance.setDeptName(userSessionBean.getGroupName());
			instance.setOrderNo(codeService.getSaNonStandarCode());
			flag = true ;
		}
		instance.setOperatorUserId(userId);
		ttaSaNonStandarHeaderDAO_HI.saveOrUpdate(instance);
		ttaSaNonStandarHeaderDAO_HI.fluch();

		//查询匹配Proposal供应商信息
		List<TtaSaStdProposalLineEntity_HI_RO> proposalVendor = findProposalVendor(instance);
		//查询Proposal相关信息
		if (flag) {//补充协议头非标信息未存在
			if (CollectionUtils.isNotEmpty(proposalVendor)) {
				List<TtaSaNonStandarLineEntity_HI> proposalLineEntityHiList = new ArrayList<>();
				for (TtaSaStdProposalLineEntity_HI_RO entityHiRo : proposalVendor) {
					TtaSaNonStandarLineEntity_HI lineEntity = getSadNonStandarLineEntity(entityHiRo,instance,userId);
					proposalLineEntityHiList.add(lineEntity);
				}
				ttaSaNonStandarLineDAO_HI.saveOrUpdateAll(proposalLineEntityHiList);
			}
		} else {
			JSONObject queryJSON = new JSONObject();
			queryJSON.put("saNonStandarHeaderId",instance.getSaNonStandarHeaderId());
			Map<String,Object> paramsMap = new HashMap<>();
			StringBuffer sbf = new StringBuffer(TtaSaNonStandarLineEntity_HI_RO.TTA_LIST);
			SaafToolUtils.parperParam(queryJSON,"tssl.sa_non_standar_header_id","saNonStandarHeaderId",sbf,paramsMap,"=");
			List<TtaSaNonStandarLineEntity_HI_RO> list = ttaSaNonStandarLineDAO_HI_RO.findList(sbf, paramsMap);
			if (CollectionUtils.isNotEmpty(list)) {
				String vendorCode = list.get(0).getVendorCode();
				//已存在的Proposal信息的供应商是否等于补充协议头信息的供应商,不等于删除,然后新增Proposal信息
				ArrayList<TtaSaNonStandarLineEntity_HI> proposalLineEntityHis = new ArrayList<>();
				if (!vendorCode.equals(instance.getVendorCode())) {
					ttaSaNonStandarLineDAO_HI.executeSqlUpdate("delete from tta_sa_non_standar_line t where t.sa_non_standar_header_id =" + instance.getSaNonStandarHeaderId());
					if (CollectionUtils.isNotEmpty(proposalVendor)) {
						for (TtaSaStdProposalLineEntity_HI_RO entityHiRo : proposalVendor) {
							TtaSaNonStandarLineEntity_HI lineEntity = getSadNonStandarLineEntity(entityHiRo,instance,userId);
							proposalLineEntityHis.add(lineEntity);
						}
						if (proposalLineEntityHis.size() > 0) {
							ttaSaNonStandarLineDAO_HI.saveOrUpdateAll(proposalLineEntityHis);
						}
					}
				}
			}
		}

		json.put("saNonStandarHeaderId",instance.getSaNonStandarHeaderId());
		TtaSaNonStandarHeaderEntity_HI_RO byRoId = findByRoId(json);
		if (flag) {
			//先删除文件服务器上已存在的合同文件
			List<BaseAttachmentEntity_HI_RO> baseAttachmentInfos = baseAttachmentServer.findBaseAttachmentAllFileId(Long.valueOf(instance.getSaNonStandarHeaderId()), "TTA_SA_NON_STANDAR_HEADER");
			for (BaseAttachmentEntity_HI_RO baseAttachmentInfo : baseAttachmentInfos) {
				baseAttachmentServer.deleteById(baseAttachmentInfo.getFileId());
			}
			//复制文件到文件系统
			BaseAttachmentEntity_HI_RO tta_std_apply_header = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(instance.getTplId()), "TTA_SA_STD_TPL_DEF_HEADER");
			if (!SaafToolUtils.isNullOrEmpty(tta_std_apply_header)) {
				// 读到流中
				InputStream inStream = fastdfsServer.getInputStream(tta_std_apply_header.getBucketName(), tta_std_apply_header.getPhyFileName());
				//保存附件到文件系统和base_attachment中
				//文件名命名规则
				StringBuffer fileName = new StringBuffer();
				//修改:供应商编号-单据号-2位版本号
				fileName.append(instance.getVendorCode())
						.append(instance.getOrderNo())
						.append(String.format("%0" + 2 + "d", instance.getOrderVersion()))
				        .append(".").append(tta_std_apply_header.getFileType());
				//替换,设置参数
				Map<String, Object> underLineMap = setParam(byRoId);
				template = XWPFTemplate.compile(inStream).render(underLineMap);
				byteOut = new ByteArrayOutputStream();
				template.write(byteOut);
				wordStream = new ByteArrayInputStream(byteOut.toByteArray());
				ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(wordStream, fileName.toString(), "TTA_SA_NON_STANDAR_HEADER", Long.valueOf(instance.getSaNonStandarHeaderId()), userId);
			}
		}
		return instance;
	}

	private List<TtaSaStdProposalLineEntity_HI_RO> findProposalVendor(TtaSaNonStandarHeaderEntity_HI entityHi) {
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

	private TtaSaNonStandarLineEntity_HI getSadNonStandarLineEntity(TtaSaStdProposalLineEntity_HI_RO entityHiRo, TtaSaNonStandarHeaderEntity_HI entityHi, Integer userId) throws Exception {
		TtaSaNonStandarLineEntity_HI lineEntityHi =  new TtaSaNonStandarLineEntity_HI();
		SaafBeanUtils.copyUnionProperties(entityHiRo,lineEntityHi);
		lineEntityHi.setSaNonStandarHeaderId(entityHi.getSaNonStandarHeaderId());
		lineEntityHi.setCreatedBy(userId);
		lineEntityHi.setCreationDate(new Date());
		lineEntityHi.setLastUpdatedBy(userId);
		lineEntityHi.setLastUpdateDate(new Date());
		lineEntityHi.setLastUpdateLogin(userId);
		lineEntityHi.setOperatorUserId(userId);
		return lineEntityHi;
	}

	public Map<String,Object> setParam(TtaSaNonStandarHeaderEntity_HI_RO instance) {
		Map<String, Object> underLineMap = new HashMap<>();
		if (!SaafToolUtils.isNullOrEmpty(instance.getPartyAVendorNameName())){
			underLineMap.put("partyAVendorName",instance.getPartyAVendorNameName());
		}
		if (!SaafToolUtils.isNullOrEmpty(instance.getPartyBVendorName())){
			underLineMap.put("partyBVendorName",instance.getPartyBVendorName());
		}
		if (!SaafToolUtils.isNullOrEmpty(instance.getPartyCVendorName())){
			underLineMap.put("partyCVendorName",instance.getPartyCVendorName());
		}
		if (!SaafToolUtils.isNullOrEmpty(instance.getContractYear())){
			underLineMap.put("contractYear",instance.getContractYear());
		}
		return underLineMap;
	};


	@Override
	public JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception {
		Integer saNonStandarHeaderId = paramsJSON.getIntValue("id");//单据Id
		JSONObject headerObject = new JSONObject();
		headerObject.put("saNonStandarHeaderId", saNonStandarHeaderId);
		JSONArray result=new JSONArray();
		//查询表单工具
		TtaSaNonStandarHeaderEntity_HI entity = ttaSaNonStandarHeaderDAO_HI.getById(saNonStandarHeaderId);
		//List<TtaSaNonStandarHeaderEntity_HI> entity = ttaSaNonStandarHeaderDAO_HI.findByProperty("saNonStandarHeaderId",headerObject.get("saNonStandarHeaderId"));
		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL":
				orderStatus = "A";
				entity.setStatus(orderStatus);
				entity.setOperatorUserId(userId);
				ttaSaNonStandarHeaderDAO_HI.saveOrUpdate(entity);
				break;
			case "ALLOW":
				orderStatus = "C";
				entity.setStatus(orderStatus);
				entity.setApproveDate(new Date());
				entity.setOperatorUserId(userId);
				ttaSaNonStandarHeaderDAO_HI.saveOrUpdate(entity);
				BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(headerObject.getLong("saNonStandarHeaderId"), "TTA_SA_NON_STANDAR_HEADER");
				InputStream inStream = fastdfsServer.getInputStream(info.getBucketName(),info.getPhyFileName());
				String qcCode = entity.getOrderNo() + "_" + entity.getOrderVersion();
				//word转换成pdf
				ByteArrayOutputStream os = Word2PdfUtil.getDocToPdfOutputStream(inStream);
				inStream = new ByteArrayInputStream(os.toByteArray());
				//插入水印和二维码
				inStream = QrcodeUtils.insertWatermarkAndLogo(qcCode, inStream);
				ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inStream, info.getSourceFileName(),"TTA_SA_NON_STANDAR_HEADER_CONTRACT",headerObject.getLong("saNonStandarHeaderId"),userId);
				TtaConAttrLineEntity_HI ttaConAttrLineEntity_hi = new TtaConAttrLineEntity_HI();
				ttaConAttrLineEntity_hi.setOperatorUserId(userId);
				ttaConAttrLineEntity_hi.setFileId(resultFileEntity.getFileId().intValue());
				ttaConAttrLineEntity_hi.setFileName(resultFileEntity.getSourceFileName());
				ttaConAttrLineEntity_hi.setFileType("5");//补充协议-非标
				ttaConAttrLineEntity_hi.setFileUrl(resultFileEntity.getFilePath());
				ttaConAttrLineEntity_hi.setOrderVersion(entity.getOrderVersion());
				ttaConAttrLineEntity_hi.setVendorCode(entity.getVendorCode());
				ttaConAttrLineEntity_hi.setOrderNo(entity.getOrderNo());
				ttaConAttrLineEntity_hi.setOrgCode(entity.getOrgCode());
				ttaConAttrLineEntity_hi.setOrgName(entity.getOrgName());
				ttaConAttrLineEntity_hi.setDeptCode(entity.getDeptCode());
				ttaConAttrLineEntity_hi.setDeptName(entity.getDeptName());
				ttaProposalConAttrLineDAO_HI.saveOrUpdate(ttaConAttrLineEntity_hi);
				DocxUtil.close(inStream);
				DocxUtil.close(os);
				break;
			case "DRAFT":
				orderStatus = "A";
				entity.setStatus(orderStatus);
				entity.setOperatorUserId(userId);
				ttaSaNonStandarHeaderDAO_HI.saveOrUpdate(entity);
				break;
			case "APPROVAL":
				orderStatus = "B";
				entity.setStatus(orderStatus);
				entity.setOperatorUserId(userId);
				ttaSaNonStandarHeaderDAO_HI.saveOrUpdate(entity);
				break;
			case "CLOSED":
				orderStatus = "G";
				entity.setStatus(orderStatus);
				entity.setOperatorUserId(userId);
				ttaSaNonStandarHeaderDAO_HI.saveOrUpdate(entity);
				break;
		}
		return result;
	}

	@Override
	public TtaSaNonStandarHeaderEntity_HI saveChangeApplyAll(JSONObject jsonObject,UserSessionBean userSessionBean, int userId) throws Exception {
		Integer saNonStandarHeaderId = jsonObject.getInteger("saNonStandarHeaderId");
		//复制头信息
		TtaSaNonStandarHeaderEntity_HI sourceEntity  = ttaSaNonStandarHeaderDAO_HI.getById(saNonStandarHeaderId);

		if (! ("C".equals(sourceEntity.getStatus())) ) {
			throw new IllegalArgumentException("单据状态不是 审批通过 不可变更");
		}
		sourceEntity.setStatus("D");
		sourceEntity.setOperatorUserId(userId);
		ttaSaNonStandarHeaderDAO_HI.save(sourceEntity);
		TtaSaNonStandarHeaderEntity_HI entity = new TtaSaNonStandarHeaderEntity_HI();
		Integer resourceId = sourceEntity.getSaNonStandarHeaderId();
		BeanUtils.copyProperties(sourceEntity, entity);
		entity.setSaNonStandarHeaderId(null);
		entity.setStatus("A");
		entity.setOperatorUserId(userId);
		entity.setOrderVersion(entity.getOrderVersion() + 1);
		entity.setOrgCode(userSessionBean.getDeptCode());
		entity.setOrgName(userSessionBean.getDeptName());
		entity.setDeptCode(userSessionBean.getGroupCode());
		entity.setDeptName(userSessionBean.getGroupName());
		entity.setResourceId(resourceId);
		ttaSaNonStandarHeaderDAO_HI.save(entity);
		BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(resourceId), "TTA_SA_NON_STANDAR_HEADER");
		if (!SaafToolUtils.isNullOrEmpty(info)) {
			// 读到流中
			InputStream inStream = fastdfsServer.getInputStream(info.getBucketName(),info.getPhyFileName());
			StringBuffer fileName = new StringBuffer();
			//修改:供应商编号-单据号-2位版本号
			fileName.append(entity.getVendorCode())
					.append(entity.getOrderNo())
					.append(String.format("%0" + 2 + "d", entity.getOrderVersion()))
					.append(".").append(info.getFileType());
			//保存附件到文件系统和base_attachment中
			ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inStream, fileName.toString(), "TTA_SA_NON_STANDAR_HEADER", Long.valueOf(entity.getSaNonStandarHeaderId()), userId);

		}
		return entity;
	}

	@Override
	public void showPdf(String businessId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream inputStream = null ;
		ByteArrayOutputStream byteOutputStream = null ;
		//补充协议
		BaseAttachmentEntity_HI_RO tta_std_apply_header = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(businessId), "TTA_SA_NON_STANDAR_HEADER");
		if (!SaafToolUtils.isNullOrEmpty(tta_std_apply_header)) {
			inputStream = fastdfsServer.getInputStream(tta_std_apply_header.getBucketName(), tta_std_apply_header.getPhyFileName());
			byteOutputStream = Word2PdfUtil.getDocToPdfOutputStream((ByteArrayInputStream)inputStream);
		}

		if (null != inputStream) {

			response.setContentType("application/pdf");
			//response.addHeader("x-frame-options", "AllowAll");
			OutputStream out;
			out = response.getOutputStream();
			out.write(byteOutputStream.toByteArray());
			out.flush();
			inputStream.close();
			out.close();
		}

	}

	@Override
	public TtaSaNonStandarHeaderEntity_HI updateSkipStatus(JSONObject paramsJSON, Integer userId) throws Exception {
		Integer saNonStandarHeaderId = paramsJSON.getIntValue("saNonStandarHeaderId");//单据Id
		String isSkipApprove = paramsJSON.getString("isSkipApprove");
		TtaSaNonStandarHeaderEntity_HI instance = ttaSaNonStandarHeaderDAO_HI.getById(saNonStandarHeaderId);
		if (StringUtils.isNotBlank(isSkipApprove)) {
			instance.setLastUpdateDate(new Date());
			instance.setIsSkipApprove(isSkipApprove);
			ttaSaNonStandarHeaderDAO_HI.update(instance);
		}
		return instance;
	}

	@Override
	public Long print(JSONObject jsonObject, UserSessionBean userSessionBean, Integer userId) throws Exception{
		Integer saNonStandarHeaderId = jsonObject.getInteger("saNonStandarHeaderId");
		TtaSaNonStandarHeaderEntity_HI entity  = ttaSaNonStandarHeaderDAO_HI.getById(saNonStandarHeaderId);
		BaseAttachmentEntity_HI_RO baseAttachmentInfo = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(String.valueOf(saNonStandarHeaderId)), "TTA_SA_NON_STANDAR_HEADER_CONTRACT");
		Long fileId = null;
		if (null == baseAttachmentInfo) {
			//找出最新的文件
			BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(String.valueOf(saNonStandarHeaderId)), "TTA_SA_NON_STANDAR_HEADER");
			if (null == info) {
				throw new IllegalArgumentException("合同文件未生成,合同查看失败!");
			}
			LOGGER.info("补充协议非标准,加入二维码和水印操作start");
			InputStream inStream = null;
			ByteArrayOutputStream os = null;
			try {
				inStream = fastdfsServer.getInputStream(info.getBucketName(),info.getPhyFileName());
				//word转换成pdf
				os = Word2PdfUtil.getDocToPdfOutputStream(inStream);
				inStream = new ByteArrayInputStream(os.toByteArray());
				if ("C".equals(entity.getStatus())) {//审批通过,插入水印和二维码
					String qcCode = entity.getOrderNo() + "_" + entity.getOrderVersion();
					//插入水印和二维码
					inStream = QrcodeUtils.insertWatermarkAndLogo(qcCode, inStream);
				}
				ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inStream, info.getSourceFileName(),"TTA_SA_NON_STANDAR_HEADER_CONTRACT",Long.valueOf(String.valueOf(saNonStandarHeaderId)),userId);
				fileId = resultFileEntity.getFileId();
			} catch (Exception e) {
				LOGGER.warn(e.getMessage());
				e.printStackTrace();
			} finally {
				DocxUtil.close(inStream);
				DocxUtil.close(os);
			}
			LOGGER.info("补充协议非标准,加入二维码和水印操作end");
		} else {
			fileId = baseAttachmentInfo.getFileId();
		}
		return fileId;
	}
}
