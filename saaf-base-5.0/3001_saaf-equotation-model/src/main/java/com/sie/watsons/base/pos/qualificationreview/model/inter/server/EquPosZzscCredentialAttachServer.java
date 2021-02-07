package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscCredentialAttachEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCredentialsAttachmentEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCredentialAttachEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscCredentialAttach;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscCredentialAttachServer")
public class EquPosZzscCredentialAttachServer extends BaseCommonServer<EquPosZzscCredentialAttachEntity_HI> implements IEquPosZzscCredentialAttach{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscCredentialAttachServer.class);

	@Autowired
	private ViewObject<EquPosZzscCredentialAttachEntity_HI> equPosZzscCredentialAttachDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscCredentialAttachEntity_HI_RO> equPosZzscCredentialAttachEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosCredentialsAttachmentEntity_HI> equPosCredentialsAttachmentDAO_HI;

	public EquPosZzscCredentialAttachServer() {
		super();
	}

	/**
	 * 资质审查-供应商资质文件查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscCredentialsAttachmentInfo(JSONObject queryParamJSON, Integer pageIndex,
													Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscCredentialAttachEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscCredentialAttachEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.attachment_id");
		Pagination<EquPosZzscCredentialAttachEntity_HI_RO> pagination = equPosZzscCredentialAttachEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商经营资质文件查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public List<EquPosZzscCredentialAttachEntity_HI_RO> findZzscOperationalAttachmentInfo(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosZzscCredentialAttachEntity_HI_RO.QUERY_SQL_OPERATIONAL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscCredentialAttachEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosZzscCredentialAttachEntity_HI_RO> list = equPosZzscCredentialAttachEntity_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 * 资质审查-供应商资质文件保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscCredentialAttachEntity_HI saveZzscCredentialsAttachmentInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 资质审查-供应商资质文件删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteZzscCredentialsAttachmentInfo(JSONObject jsonObject) {
		Integer attachmentId =jsonObject.getInteger("id");
		EquPosZzscCredentialAttachEntity_HI zzscAttachmentEntity =equPosZzscCredentialAttachDAO_HI.getById(attachmentId);
		if(zzscAttachmentEntity!=null){
			equPosZzscCredentialAttachDAO_HI.delete(zzscAttachmentEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
