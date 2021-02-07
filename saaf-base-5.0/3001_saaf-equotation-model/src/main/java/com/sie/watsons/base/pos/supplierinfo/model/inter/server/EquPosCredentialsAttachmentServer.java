package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosCredentialsAttachmentEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCredentialsAttachmentEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosCredentialsAttachment;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosCredentialsAttachmentServer")
public class EquPosCredentialsAttachmentServer extends BaseCommonServer<EquPosCredentialsAttachmentEntity_HI> implements IEquPosCredentialsAttachment{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCredentialsAttachmentServer.class);

	@Autowired
	private ViewObject<EquPosCredentialsAttachmentEntity_HI> equPosCredentialsAttachmentDAO_HI;

	@Autowired
	private BaseViewObject<EquPosCredentialsAttachmentEntity_HI_RO> equPosCredentialsAttachmentEntity_HI_RO;

	public EquPosCredentialsAttachmentServer() {
		super();
	}

	/**
	 * 供应商资质文件查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findCredentialsAttachmentInfo(JSONObject queryParamJSON, Integer pageIndex,
									   Integer pageRows) {
		if(queryParamJSON.containsKey("deptmentCode")){
			if(!"0E".equals(queryParamJSON.getString("deptmentCode"))){
				queryParamJSON.remove("deptmentCode");
				queryParamJSON.put("deptmentCode_neq","0E");
			}
		}
		StringBuffer sql = new StringBuffer(EquPosCredentialsAttachmentEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosCredentialsAttachmentEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.attachment_id");
		Pagination<EquPosCredentialsAttachmentEntity_HI_RO> pagination = equPosCredentialsAttachmentEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商经营资质文件查询
	 * @param queryParamJSON 参数：密级Entity中的字findCredentialsAttachmentInfo段
	 * @return
	 */
	public List<EquPosCredentialsAttachmentEntity_HI_RO> findOperationalAttachmentInfo(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosCredentialsAttachmentEntity_HI_RO.QUERY_SQL_OPERATIONAL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosCredentialsAttachmentEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosCredentialsAttachmentEntity_HI_RO> list = equPosCredentialsAttachmentEntity_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 * 应商资质文件-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosCredentialsAttachmentEntity_HI saveCredentialsAttachmentInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 应商资质文件-删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteCredentialsAttachmentInfo(JSONObject jsonObject) {
		Integer attachmentId =jsonObject.getInteger("id");
		EquPosCredentialsAttachmentEntity_HI attachmentEntity =equPosCredentialsAttachmentDAO_HI.getById(attachmentId);
		if(attachmentEntity!=null){
			equPosCredentialsAttachmentDAO_HI.delete(attachmentEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}

	/**
	 * 供应商联系人信息报表查询(Non-trade)
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findCredentialsAttachmentReportForm(JSONObject queryParamJSON, Integer pageIndex,
													Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosCredentialsAttachmentEntity_HI_RO.QUERY_SQL_CREDENTIAL_ATTACHMENT_REPORT_FORM);
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
		String serviceScope = queryParamJSON.getString("serviceScope");

		if(null != serviceScope && !"".equals(serviceScope)){
			sql.append(" and  t.category_count > 0 ");
		}else{
			queryParamJSON.put("serviceScope","-1");
		}

		if(null != expireDays && !"".equals(expireDays)){
			sql.append(" and  t.expire_days <= " + expireDays);
		}

		map.put("serviceScope",queryParamJSON.getString("serviceScope"));

		sql.append(" order by t.supplier_id desc");
		Pagination<EquPosCredentialsAttachmentEntity_HI_RO> pagination = equPosCredentialsAttachmentEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}
}
