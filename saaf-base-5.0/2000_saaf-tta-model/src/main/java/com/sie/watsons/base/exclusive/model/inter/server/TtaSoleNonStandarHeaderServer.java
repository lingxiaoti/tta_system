package com.sie.watsons.base.exclusive.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.Word2PdfUtil;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleNonStandarHeaderEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.TempRuleDefEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.ITempRuleDef;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleNonStandarHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleNonStandarHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("ttaSoleNonStandarHeaderServer")
public class TtaSoleNonStandarHeaderServer extends BaseCommonServer<TtaSoleNonStandarHeaderEntity_HI> implements ITtaSoleNonStandarHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleNonStandarHeaderServer.class);

	@Autowired
	private ViewObject<TtaSoleNonStandarHeaderEntity_HI> ttaSoleNonStandarHeaderDAO_HI;

	@Autowired
	private ViewObject<TtaConAttrLineEntity_HI> ttaProposalConAttrLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaSoleNonStandarHeaderEntity_HI_RO> ttaSoleNonStandarHeaderDAO_HI_RO;

	@Autowired
	private GenerateCodeService codeService;

	@Autowired
	private ITempRuleDef tempRuleDefServer;

	@Autowired
	private IBaseAttachment baseAttachmentServer;

	@Autowired
	private IFastdfs fastdfsServer;

	public TtaSoleNonStandarHeaderServer() {
		super();
	}

	@Override
	public Pagination<TtaSoleNonStandarHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaSoleNonStandarHeaderEntity_HI_RO.TTA_ITEM_V);
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

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tsnsh.sole_non_standar_header_id desc", false);
		Pagination<TtaSoleNonStandarHeaderEntity_HI_RO> findList = ttaSoleNonStandarHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public void updateStatus(Integer pkId,Integer userId) throws Exception{
		if (pkId == null) {
			return;
		}
		TtaSoleNonStandarHeaderEntity_HI instance = ttaSoleNonStandarHeaderDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		if ( !("A".equals(instance.getStatus())) ) {
			throw new IllegalArgumentException("单据状态不是 制单中 不可作废");
		}
		instance.setStatus("E");
		instance.setOperatorUserId(userId);
		ttaSoleNonStandarHeaderDAO_HI.update(instance);
	}

	@Override
	public TtaSoleNonStandarHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		SaafToolUtils.validateJsonParms(queryParamJSON,"soleNonStandarHeaderId");
		StringBuffer sql = new StringBuffer();
		sql.append(TtaSoleNonStandarHeaderEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tsnsh.sole_non_standar_header_id", "soleNonStandarHeaderId", sql, paramsMap, "=");
		TtaSoleNonStandarHeaderEntity_HI_RO proposalHeadEntity = (TtaSoleNonStandarHeaderEntity_HI_RO)ttaSoleNonStandarHeaderDAO_HI_RO.get(sql,paramsMap);
		return proposalHeadEntity;
	}

	@Override
	public TtaSoleNonStandarHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, UserSessionBean userSessionBean, int userId) throws Exception {
		if (null != paramsJSON.getInteger("soleNonStandarHeaderId")) {
			TtaSoleNonStandarHeaderEntity_HI_RO byRoId = findByRoId(paramsJSON);

			if (!"A".equals(byRoId.getStatus())) {
				throw new IllegalArgumentException("单据状态不是 制单中 不可保存");
			}
		}
		boolean flag = false ;
		TtaSoleNonStandarHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaSoleNonStandarHeaderEntity_HI.class, paramsJSON, ttaSoleNonStandarHeaderDAO_HI, userId);

		if(SaafToolUtils.isNullOrEmpty(instance.getSoleNonStandarHeaderId())) {
			instance.setStatus("A");
			instance.setOrderVersion(1);
			instance.setOrgCode(userSessionBean.getDeptCode());
			instance.setOrgName(userSessionBean.getDeptName());
			instance.setDeptCode(userSessionBean.getGroupCode());
			instance.setDeptName(userSessionBean.getGroupName());
			instance.setOrderNo(codeService.getSoleNonStandarCode());
			flag = true ;

		}
		instance.setOperatorUserId(userId);
		ttaSoleNonStandarHeaderDAO_HI.saveOrUpdate(instance);
		if (flag) {
			//带出proposal 供应商

			//复制文件到文件系统
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("soleResouceType",instance.getSoleResouceType());
			jsonObject.put("soleProductType",instance.getSoleProductType());
			jsonObject.put("isIncludeEc",instance.getIsIncludeEc());
			jsonObject.put("isIncludeSpecial",instance.getIsIncludeSpecial());
			jsonObject.put("isEnable","Y");
			TempRuleDefEntity_HI_RO ruleOne = tempRuleDefServer.findRuleOne(jsonObject);
			JSONObject docInputStream = tempRuleDefServer.getDocInputStream(ruleOne.getRulId());
			StringBuffer fileName = new StringBuffer();
			//修改:供应商编号-单据号-2位版本号
			fileName.append(instance.getVendorCode())
					.append(instance.getOrderNo())
					.append(String.format("%0" + 2 + "d", instance.getOrderVersion()))
					.append(".").append("doc");
				ResultFileEntity resultFileEntity = fastdfsServer.fileUpload((InputStream)docInputStream.get("in"),fileName.toString(), "TTA_SOLE_NON_STANDAR_HEADER", Long.valueOf(instance.getSoleNonStandarHeaderId()), userId);
		}
		return instance;
	}

	@Override
	public JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception {

		Integer soleNonStandarHeaderId = paramsJSON.getIntValue("id");//单据Id
		JSONObject headerObject = new JSONObject();
		headerObject.put("soleNonStandarHeaderId", soleNonStandarHeaderId);
		JSONArray result=new JSONArray();
		//查询表单工具
		List<TtaSoleNonStandarHeaderEntity_HI> Entity = ttaSoleNonStandarHeaderDAO_HI.findByProperty("soleNonStandarHeaderId",headerObject.get("soleNonStandarHeaderId"));
		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL":
				orderStatus = "A";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setOperatorUserId(userId);
				ttaSoleNonStandarHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				break;
			case "ALLOW":
				orderStatus = "C";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setApproveDate(new Date());
				Entity.get(0).setOperatorUserId(userId);
				ttaSoleNonStandarHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(headerObject.getLong("soleNonStandarHeaderId"), "TTA_SOLE_NON_STANDAR_HEADER");
				TtaConAttrLineEntity_HI ttaConAttrLineEntity_hi = new TtaConAttrLineEntity_HI();
				ttaConAttrLineEntity_hi.setOperatorUserId(userId);
				ttaConAttrLineEntity_hi.setFileId(info.getFileId().intValue());
				ttaConAttrLineEntity_hi.setFileName(info.getSourceFileName());
				ttaConAttrLineEntity_hi.setFileType("3");
				ttaConAttrLineEntity_hi.setFileUrl(info.getFilePath());
				ttaConAttrLineEntity_hi.setOrderVersion(Entity.get(0).getOrderVersion());
				ttaConAttrLineEntity_hi.setVendorCode(Entity.get(0).getVendorCode());
				ttaConAttrLineEntity_hi.setOrderNo(Entity.get(0).getOrderNo());
				ttaProposalConAttrLineDAO_HI.saveOrUpdate(ttaConAttrLineEntity_hi);
				break;
			case "DRAFT":
				orderStatus = "A";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setOperatorUserId(userId);
				ttaSoleNonStandarHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				break;
			case "APPROVAL":
				orderStatus = "B";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setOperatorUserId(userId);
				ttaSoleNonStandarHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				break;
			case "CLOSED":
				orderStatus = "G";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setOperatorUserId(userId);
				ttaSoleNonStandarHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				break;

		}
		return result;
	}

	@Override
	public TtaSoleNonStandarHeaderEntity_HI saveChangeApplyAll(JSONObject jsonObject,UserSessionBean userSessionBean, int userId) throws Exception {
		Integer soleNonStandarHeaderId = jsonObject.getInteger("soleNonStandarHeaderId");
		//复制头信息
		TtaSoleNonStandarHeaderEntity_HI sourceEntity  = ttaSoleNonStandarHeaderDAO_HI.getById(soleNonStandarHeaderId);

		if (! ("C".equals(sourceEntity.getStatus())) ) {
			throw new IllegalArgumentException("单据状态不是 审批通过 不可变更");
		}
		sourceEntity.setStatus("D");
		sourceEntity.setOperatorUserId(userId);
		ttaSoleNonStandarHeaderDAO_HI.save(sourceEntity);
		TtaSoleNonStandarHeaderEntity_HI entity = new TtaSoleNonStandarHeaderEntity_HI();
		Integer resourceId = sourceEntity.getSoleNonStandarHeaderId();
		BeanUtils.copyProperties(sourceEntity, entity);
		entity.setSoleNonStandarHeaderId(null);
		entity.setStatus("A");
		entity.setOperatorUserId(userId);
		entity.setOrderVersion(entity.getOrderVersion() + 1);
		entity.setResourceId(resourceId);
		entity.setOrgCode(userSessionBean.getDeptCode());
		entity.setOrgName(userSessionBean.getDeptName());
		entity.setDeptCode(userSessionBean.getGroupCode());
		entity.setDeptName(userSessionBean.getGroupName());
		ttaSoleNonStandarHeaderDAO_HI.save(entity);
		BaseAttachmentEntity_HI_RO info = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(resourceId), "TTA_SOLE_NON_STANDAR_HEADER");
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
			ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inStream, fileName.toString(), "TTA_SOLE_NON_STANDAR_HEADER", Long.valueOf(entity.getSoleNonStandarHeaderId()), userId);
		}
		return entity;
	}

	@Override
	public void showPdf(String businessId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream inputStream = null ;
		ByteArrayOutputStream byteOutputStream = null ;
		//补充协议
		BaseAttachmentEntity_HI_RO tta_std_apply_header = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(businessId), "TTA_SOLE_NON_STANDAR_HEADER");
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
	public TtaSoleNonStandarHeaderEntity_HI updateSkipStatus(JSONObject paramsJSON, Integer userId) throws Exception {
		Integer soleNonStandarHeaderId = paramsJSON.getIntValue("soleNonStandarHeaderId");//单据Id
		String isSkipApprove = paramsJSON.getString("isSkipApprove");
		TtaSoleNonStandarHeaderEntity_HI instance = ttaSoleNonStandarHeaderDAO_HI.getById(soleNonStandarHeaderId);
		if (StringUtils.isNotBlank(isSkipApprove)) {
			instance.setLastUpdateDate(new Date());
			instance.setIsSkipApprove(isSkipApprove);
			ttaSoleNonStandarHeaderDAO_HI.update(instance);
		}
		return instance;
	}



}
