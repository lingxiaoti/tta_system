package com.sie.watsons.base.ttasastdtpl.model.inter.server;

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

import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.Word2PdfUtil;
import com.sie.watsons.base.rule.model.inter.ITempRuleDef;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaStdApplyHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaStdApplyHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.ttasastdtpl.model.inter.ITtaStdApplyHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("ttaStdApplyHeaderServer")
public class TtaStdApplyHeaderServer extends BaseCommonServer<TtaStdApplyHeaderEntity_HI> implements ITtaStdApplyHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaStdApplyHeaderServer.class);

	@Autowired
	private ViewObject<TtaStdApplyHeaderEntity_HI> ttaStdApplyHeaderDAO_HI;

	@Autowired
	private BaseViewObject<TtaStdApplyHeaderEntity_HI_RO> ttaStdApplyHeaderDAO_HI_RO;

	@Autowired
	private GenerateCodeService codeService;

	@Autowired
	private IBaseAttachment baseAttachmentServer;

	@Autowired
	private ITempRuleDef tempRuleDefServer;

	@Autowired
	private IFastdfs fastdfsServer;

	public TtaStdApplyHeaderServer() {
		super();
	}

	@Override
	public Pagination<TtaStdApplyHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaStdApplyHeaderEntity_HI_RO.TTA_ITEM_V);
		sql.append(" and tsah.status != 'E' ");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tsah.order_no", "orderNo", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "tsah.tpl_type", "tplType", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "tsah.quote_tpl_name", "quoteTplName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "tsah.tpl_name", "tplName", sql, paramsMap, "like");

		if  (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDate1"))) {
			sql.append(" and to_char(tsah.creation_Date,'YYYY-MM-DD') >= :creationDate1");
			paramsMap.put("creationDate1",queryParamJSON.getString("creationDate1"));
		}
		if  (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDate2"))) {
			sql.append(" and to_char(tsah.creation_Date,'YYYY-MM-DD') <= :creationDate2");
			paramsMap.put("creationDate2",queryParamJSON.getString("creationDate2"));
		}
	//	SaafToolUtils.parperParam(queryParamJSON, "to_char(tsah.creation_Date,'YYYY-MM-DD')", "creationDate1", sql, paramsMap, ">=");
		//SaafToolUtils.parperParam(queryParamJSON, "to_char(tsah.creation_Date,'YYYY-MM-DD')", "creationDate2", sql, paramsMap, "<=");

		SaafToolUtils.parperParam(queryParamJSON, "tsah.status", "status", sql, paramsMap, "=");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tsah.std_apply_header_id desc", false);
		Pagination<TtaStdApplyHeaderEntity_HI_RO> findList = ttaStdApplyHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public void updateStatus(Integer pkId,Integer userId) throws Exception{
		if (pkId == null) {
			return;
		}
		TtaStdApplyHeaderEntity_HI instance = ttaStdApplyHeaderDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		if ( !("A".equals(instance.getStatus())) ) {
			throw new IllegalArgumentException("单据状态不是 制单中 不可作废");
		}
		instance.setStatus("E");
		instance.setOperatorUserId(userId);
		ttaStdApplyHeaderDAO_HI.update(instance);
	}

	@Override
	public TtaStdApplyHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		SaafToolUtils.validateJsonParms(queryParamJSON,"stdApplyHeaderId");
		StringBuffer sql = new StringBuffer();
		sql.append(TtaStdApplyHeaderEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tsah.std_apply_header_id", "stdApplyHeaderId", sql, paramsMap, "=");
		TtaStdApplyHeaderEntity_HI_RO proposalHeadEntity = (TtaStdApplyHeaderEntity_HI_RO)ttaStdApplyHeaderDAO_HI_RO.get(sql,paramsMap);
		return proposalHeadEntity;
	}

	@Override
	public TtaStdApplyHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, UserSessionBean userSessionBean, int userId) throws Exception {
		if (null != paramsJSON.getInteger("stdApplyHeaderId")) {
			TtaStdApplyHeaderEntity_HI_RO byRoId = findByRoId(paramsJSON);

			if (!"A".equals(byRoId.getStatus())) {
				throw new IllegalArgumentException("单据状态不是 制单中 不可保存");
			}
		}
		boolean flag = false ;
		TtaStdApplyHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaStdApplyHeaderEntity_HI.class, paramsJSON, ttaStdApplyHeaderDAO_HI, userId);

		if(SaafToolUtils.isNullOrEmpty(instance.getStdApplyHeaderId())) {
			instance.setStatus("A");
			instance.setIsAlert("N");
			instance.setOrgCode(userSessionBean.getDeptCode());
			instance.setOrgCodeName(userSessionBean.getDeptName());
			instance.setDeptCode(userSessionBean.getGroupCode());
			instance.setDeptCodeName(userSessionBean.getGroupName());
			instance.setVersionCode(1);
			instance.setOrderNo(codeService.getstdApplyHeaderCode());
			flag = true ;

		}
		instance.setOperatorUserId(userId);
		ttaStdApplyHeaderDAO_HI.saveOrUpdate(instance);
		if (flag) {
			//复制文件到文件系统
			//补充协议
			if ("1".equals(instance.getTplType())) {
				BaseAttachmentEntity_HI_RO tta_std_apply_header = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(instance.getTplId()), "TTA_SA_STD_TPL_DEF_HEADER");
				if (!SaafToolUtils.isNullOrEmpty(tta_std_apply_header)) {
					// 读到流中
					InputStream inStream = fastdfsServer.getInputStream(tta_std_apply_header.getBucketName(), tta_std_apply_header.getPhyFileName());
					//保存附件到文件系统和base_attachment中
					ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inStream, tta_std_apply_header.getSourceFileName(), "TTA_STD_APPLY_HEADER", Long.valueOf(instance.getStdApplyHeaderId()), userId);
				} else {  //独家协议

				}
			} else if ("2".equals(instance.getTplType())) {
				JSONObject docInputStream = tempRuleDefServer.getDocInputStream(instance.getTplId());
				ResultFileEntity resultFileEntity = fastdfsServer.fileUpload((InputStream)docInputStream.get("in"),docInputStream.getString("fileName"), "TTA_STD_APPLY_HEADER", Long.valueOf(instance.getStdApplyHeaderId()), userId);
			}
		}
		return instance;
	}

	@Override
	public JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception {

		Integer stdApplyHeaderId = paramsJSON.getIntValue("id");//单据Id
		JSONObject headerObject = new JSONObject();
		headerObject.put("stdApplyHeaderId", stdApplyHeaderId);
		JSONArray result=new JSONArray();
		//查询表单工具
		List<TtaStdApplyHeaderEntity_HI> ttaStdApplyHeaderEntity = ttaStdApplyHeaderDAO_HI.findByProperty("stdApplyHeaderId",headerObject.get("stdApplyHeaderId"));
		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL":
				orderStatus = "A";
				ttaStdApplyHeaderEntity.get(0).setStatus(orderStatus);
				ttaStdApplyHeaderEntity.get(0).setOperatorUserId(userId);
				ttaStdApplyHeaderDAO_HI.saveOrUpdate(ttaStdApplyHeaderEntity.get(0));
				break;
			case "ALLOW":
				orderStatus = "C";
				ttaStdApplyHeaderEntity.get(0).setStatus(orderStatus);
				ttaStdApplyHeaderEntity.get(0).setApproveDate(new Date());
				ttaStdApplyHeaderEntity.get(0).setOperatorUserId(userId);
				ttaStdApplyHeaderDAO_HI.saveOrUpdate(ttaStdApplyHeaderEntity.get(0));
				break;
			case "DRAFT":
				orderStatus = "A";
				ttaStdApplyHeaderEntity.get(0).setStatus(orderStatus);
				ttaStdApplyHeaderEntity.get(0).setOperatorUserId(userId);
				ttaStdApplyHeaderDAO_HI.saveOrUpdate(ttaStdApplyHeaderEntity.get(0));
				break;
			case "APPROVAL":
				orderStatus = "B";
				ttaStdApplyHeaderEntity.get(0).setStatus(orderStatus);
				ttaStdApplyHeaderEntity.get(0).setOperatorUserId(userId);
				ttaStdApplyHeaderDAO_HI.saveOrUpdate(ttaStdApplyHeaderEntity.get(0));
				break;
			case "CLOSED":
				orderStatus = "G";
				ttaStdApplyHeaderEntity.get(0).setStatus(orderStatus);
				ttaStdApplyHeaderEntity.get(0).setOperatorUserId(userId);
				ttaStdApplyHeaderDAO_HI.saveOrUpdate(ttaStdApplyHeaderEntity.get(0));
				break;

		}
		return result;
	}

	@Override
	public TtaStdApplyHeaderEntity_HI saveChangeApplyAll(JSONObject jsonObject,UserSessionBean userSessionBean, int userId) throws Exception {
		Integer stdApplyHeaderId = jsonObject.getInteger("stdApplyHeaderId");
		//复制头信息
		TtaStdApplyHeaderEntity_HI sourceEntity  = ttaStdApplyHeaderDAO_HI.getById(stdApplyHeaderId);

		if (! ("C".equals(sourceEntity.getStatus())) ) {
			throw new IllegalArgumentException("单据状态不是 审批通过 不可变更");
		}
		sourceEntity.setStatus("D");
		sourceEntity.setOperatorUserId(userId);
		ttaStdApplyHeaderDAO_HI.save(sourceEntity);
		TtaStdApplyHeaderEntity_HI entity = new TtaStdApplyHeaderEntity_HI();
		Integer resourceId = sourceEntity.getStdApplyHeaderId();
		BeanUtils.copyProperties(sourceEntity, entity);
		entity.setStdApplyHeaderId(null);
		entity.setStatus("A");
		entity.setOperatorUserId(userId);
		entity.setOrgCode(userSessionBean.getDeptCode());
		entity.setOrgCodeName(userSessionBean.getDeptName());
		entity.setDeptCode(userSessionBean.getGroupCode());
		entity.setDeptCodeName(userSessionBean.getGroupName());
		entity.setVersionCode(entity.getVersionCode() + 1);
		entity.setResourceId(resourceId);
		ttaStdApplyHeaderDAO_HI.save(entity);
		BaseAttachmentEntity_HI_RO tta_std_apply_header = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(resourceId), "TTA_STD_APPLY_HEADER");
		if (!SaafToolUtils.isNullOrEmpty(tta_std_apply_header)) {
			// 读到流中
			InputStream inStream = fastdfsServer.getInputStream(tta_std_apply_header.getBucketName(),tta_std_apply_header.getPhyFileName());
			//保存附件到文件系统和base_attachment中
			ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inStream, tta_std_apply_header.getSourceFileName(), "TTA_STD_APPLY_HEADER", Long.valueOf(entity.getStdApplyHeaderId()), userId);
		}
		return entity;
	}

	@Override
	public void showPdf(String businessId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream inputStream = null ;
		ByteArrayOutputStream byteOutputStream = null ;
			BaseAttachmentEntity_HI_RO tta_std_apply_header = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(businessId), "TTA_STD_APPLY_HEADER");
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

}
