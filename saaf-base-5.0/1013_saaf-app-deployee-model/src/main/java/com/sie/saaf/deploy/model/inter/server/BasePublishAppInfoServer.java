package com.sie.saaf.deploy.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.deploy.model.entities.BasePublishAppInfoEntity_HI;
import com.sie.saaf.deploy.model.entities.readonly.BasePublishAppInfoEntity_HI_RO;
import com.sie.saaf.deploy.model.inter.IBasePublishAppInfo;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("basePublishAppInfoServer")
public class BasePublishAppInfoServer  extends BaseCommonServer<BasePublishAppInfoEntity_HI> implements IBasePublishAppInfo {
	private static final Logger LOGGER = LoggerFactory.getLogger(BasePublishAppInfoServer.class);
	@Autowired
	private ViewObject<BasePublishAppInfoEntity_HI> basePublishAppInfoDAO_HI;

	@Autowired
	private BaseViewObject<BasePublishAppInfoEntity_HI_RO> basePublishAppInfoDAO_HI_RO;
	public BasePublishAppInfoServer() {
		super();
	}

	/**
	 * 查询应用发布列表
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@Override
	public Pagination findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(BasePublishAppInfoEntity_HI_RO.QUERY_LIST_SQL);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "pub.delete_flag", "deleteFlag", sql,  queryParamMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "pub.app_system", "appSystem", sql,  queryParamMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "pub.app_wap_name", "appWapName", sql,  queryParamMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "pub.app_wap_id", "appWapId", sql,  queryParamMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "pub.app_publish_version", "appPublishVersion", sql,  queryParamMap, "fulllike");
		sql.append(" order by pub.app_wap_name, pub.creation_date desc");
		return basePublishAppInfoDAO_HI_RO.findPagination(sql.toString(), queryParamMap, pageIndex, pageRows);
	}

	/**
	 * 查询应用发布信息
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public BasePublishAppInfoEntity_HI findPublishInfo(JSONObject queryParamJSON) throws Exception {
        BasePublishAppInfoEntity_HI instance = null;
        if (StringUtils.isNotBlank(queryParamJSON.getString("appWapId"))) {
            instance = basePublishAppInfoDAO_HI.getById(queryParamJSON.getInteger("appWapId"));
        } else {
            StringBuffer hql = new StringBuffer(" from BasePublishAppInfoEntity_HI pub where pub.appWapName = ? and pub.appSystem = ?");
            instance = basePublishAppInfoDAO_HI.get(hql.toString(), queryParamJSON.getString("appWapName"), queryParamJSON.getString("appSystem"));
        }

		if (null == instance) {
			throw new IllegalArgumentException("该应用发布信息不存在");
		}

		return instance;
	}

	/**
	 * 新增应用发布信息
	 * @param paramsJSON
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public BasePublishAppInfoEntity_HI savePublishInfo(JSONObject paramsJSON, Integer userId) throws Exception{
		BasePublishAppInfoEntity_HI instance = SaafToolUtils.setEntity(BasePublishAppInfoEntity_HI.class, paramsJSON, basePublishAppInfoDAO_HI, userId);
		StringBuffer hql = new StringBuffer("select count(1) from base_publish_app_info pub where pub.app_wap_name = ? and pub.app_system = ? and pub.delete_flag = 0");
		int count = basePublishAppInfoDAO_HI_RO.count(hql.toString(), paramsJSON.getString("appWapName"), paramsJSON.getString("appSystem"));
		if (count > 0) {
			throw new IllegalArgumentException("该应用发布信息已存在");
		}
		instance.setDeleteFlag(0);
		basePublishAppInfoDAO_HI.save(instance);
		LOGGER.info("saved BasePublishAppInfo:{}", JSON.toJSON(instance));
		return instance;
	}

	/**
	 * 修改应用发布信息
	 * @param queryParamJSON
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public BasePublishAppInfoEntity_HI updatePublishInfo(JSONObject queryParamJSON, Integer userId) throws Exception {
		BasePublishAppInfoEntity_HI instance = basePublishAppInfoDAO_HI.getById(queryParamJSON.getInteger("appWapId"));
		if (null == instance) {
			throw new IllegalArgumentException("该应用发布信息不存在");
		}

		instance.setAppPublishVersion(queryParamJSON.getString("appPublishVersion"));
		instance.setAppUpdateTips(queryParamJSON.getString("appUpdateTips"));
		instance.setAppDownloadUrl(queryParamJSON.getString("appDownloadUrl"));
		instance.setLastUpdatedBy(userId);
		instance.setLastUpdateDate(new Date());
		basePublishAppInfoDAO_HI.update(instance);
		LOGGER.info("updated BasePublishAppInfo:{}", JSON.toJSON(instance));
		return instance;
	}
}
