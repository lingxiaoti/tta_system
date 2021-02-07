package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgCredentialAttachEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCredentialsAttachmentEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCredentialAttachEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgCredentialAttach;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgCredentialAttachServer")
public class EquPosBgCredentialAttachServer extends BaseCommonServer<EquPosBgCredentialAttachEntity_HI> implements IEquPosBgCredentialAttach{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgCredentialAttachServer.class);

	@Autowired
	private ViewObject<EquPosBgCredentialAttachEntity_HI> equPosBgCredentialAttachDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgCredentialAttachEntity_HI_RO> equPosBgCredentialAttachEntity_HI_RO;


	public EquPosBgCredentialAttachServer() {
		super();
	}

	/**
	 * 档案变更-供应商资质文件查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgCredentialsAttachmentInfo(JSONObject queryParamJSON, Integer pageIndex,
														Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgCredentialAttachEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgCredentialAttachEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.attachment_id");
		Pagination<EquPosBgCredentialAttachEntity_HI_RO> pagination = equPosBgCredentialAttachEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商经营资质文件查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public List<EquPosBgCredentialAttachEntity_HI_RO> findBgOperationalAttachmentInfo(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosBgCredentialAttachEntity_HI_RO.QUERY_SQL_OPERATIONAL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgCredentialAttachEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosBgCredentialAttachEntity_HI_RO> list = equPosBgCredentialAttachEntity_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 * 档案变更-供应商资质文件保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgCredentialAttachEntity_HI saveBgCredentialsAttachmentInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更-供应商资质文件删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgCredentialsAttachmentInfo(JSONObject jsonObject) {
		Integer attachmentId =jsonObject.getInteger("id");
		EquPosBgCredentialAttachEntity_HI BgAttachmentEntity =equPosBgCredentialAttachDAO_HI.getById(attachmentId);
		if(BgAttachmentEntity!=null){
			equPosBgCredentialAttachDAO_HI.delete(BgAttachmentEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
