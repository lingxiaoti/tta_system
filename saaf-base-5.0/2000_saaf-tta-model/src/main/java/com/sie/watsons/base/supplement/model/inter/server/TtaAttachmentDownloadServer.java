package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplement.model.entities.TtaAttachmentDownloadEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaAttachmentDownloadEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSideAgrtHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaAttachmentDownload;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component("ttaAttachmentDownloadServer")
public class TtaAttachmentDownloadServer extends BaseCommonServer<TtaAttachmentDownloadEntity_HI> implements ITtaAttachmentDownload {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAttachmentDownloadServer.class);

	@Autowired
	private ViewObject<TtaAttachmentDownloadEntity_HI> ttaAttachmentDownloadDAO_HI;

	@Autowired
	private BaseViewObject<TtaAttachmentDownloadEntity_HI_RO> ttaAttachmentDownloadDAO_HI_RO;

	public TtaAttachmentDownloadServer() {
		super();
	}


	/**
	 * 保存
	 * @param baseAttachmentEntity_hi
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaAttachmentDownloadEntity_HI ttaAchmentDownload(BaseAttachmentEntity_HI baseAttachmentEntity_hi,int userId) throws Exception {
		TtaAttachmentDownloadEntity_HI entity_hi = new TtaAttachmentDownloadEntity_HI();
		entity_hi.setFilePath(baseAttachmentEntity_hi.getFilePath());
		entity_hi.setBusinessId(baseAttachmentEntity_hi.getBusinessId());
		entity_hi.setFunctionId(baseAttachmentEntity_hi.getFunctionId());
		entity_hi.setFileStoreSystem(baseAttachmentEntity_hi.getFileStoreSystem());
		entity_hi.setFileSize(baseAttachmentEntity_hi.getFileSize());
		entity_hi.setFileType(baseAttachmentEntity_hi.getFileType());
		entity_hi.setSourceFileName(baseAttachmentEntity_hi.getSourceFileName());
		entity_hi.setPhyFileName(baseAttachmentEntity_hi.getPhyFileName());
		entity_hi.setBucketName(baseAttachmentEntity_hi.getBucketName());
		entity_hi.setCreationDate(new Date());
		entity_hi.setLastUpdateDate(new Date());
		entity_hi.setCreatedBy(userId);
		entity_hi.setLastUpdatedBy(userId);
		entity_hi.setLastUpdateLogin(userId);
		ttaAttachmentDownloadDAO_HI.saveOrUpdate(entity_hi);
		return entity_hi;
	}

	@Override
	public Pagination<TtaAttachmentDownloadEntity_HI_RO> findAttachmentDownloadPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer("select ba.file_id fileId,ba.source_file_name sourceFileName," +
				"ba.function_id functionId,ba.business_id businessId," +
				"ba.file_store_system fileStoreSystem," +
				"ba.file_path filePath,ba.bucket_name bucketName," +
				"ba.phy_file_name phyFileName,ba.status status,ba.file_size fileSize," +
				"ba.file_type fileType,ba.delete_flag deleteFlag," +
				"ba.created_by createdBy,ba.creation_date creationDate," +
				"ba.last_updated_by lastUpdatedBy,ba.last_update_date lastUpdateDate," +
				"bu.user_full_name createdByUser," +
				"ba.last_update_login lastUpdateLogin,ba.version_num versionNum from tta_attachment_download ba,base_users bu  where ba.created_by = bu.user_id and" +
				" 1 = 1 ");
		Map<String, Object> paramsMap = new HashMap<>();
		//SaafToolUtils.parperParam(queryParamJSON, "ba.function_id", "functionId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "ba.business_id", "businessId", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON,sql,"ba.file_id asc",false);
		Pagination<TtaAttachmentDownloadEntity_HI_RO> pagination = ttaAttachmentDownloadDAO_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
		return pagination;
	}
}
