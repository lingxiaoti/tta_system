package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqCredentialAttachEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCredentialAttachEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqCredentialAttach;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqCredentialAttachServer")
public class EquPosBgqCredentialAttachServer extends BaseCommonServer<EquPosBgqCredentialAttachEntity_HI> implements IEquPosBgqCredentialAttach{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqCredentialAttachServer.class);

	@Autowired
	private ViewObject<EquPosBgqCredentialAttachEntity_HI> equPosBgqCredentialAttachDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqCredentialAttachEntity_HI_RO> equPosBgqCredentialAttachEntity_HI_RO;

	public EquPosBgqCredentialAttachServer() {
		super();
	}

	/**
	 * 档案变更前-供应商资质文件查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqCredentialsAttachmentInfo(JSONObject queryParamJSON, Integer pageIndex,
													  Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqCredentialAttachEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqCredentialAttachEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.attachment_id");
		Pagination<EquPosBgqCredentialAttachEntity_HI_RO> pagination = equPosBgqCredentialAttachEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商经营资质文件查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public List<EquPosBgqCredentialAttachEntity_HI_RO> findBgqOperationalAttachmentInfo(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosBgqCredentialAttachEntity_HI_RO.QUERY_SQL_OPERATIONAL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqCredentialAttachEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosBgqCredentialAttachEntity_HI_RO> list = equPosBgqCredentialAttachEntity_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 * 档案变更前-供应商资质文件保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqCredentialAttachEntity_HI saveBgqCredentialsAttachmentInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更前-供应商资质文件删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgqCredentialsAttachmentInfo(JSONObject jsonObject) {
		Integer attachmentId =jsonObject.getInteger("id");
		EquPosBgqCredentialAttachEntity_HI BgqAttachmentEntity =equPosBgqCredentialAttachDAO_HI.getById(attachmentId);
		if(BgqAttachmentEntity!=null){
			equPosBgqCredentialAttachDAO_HI.delete(BgqAttachmentEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
