package com.sie.saaf.base.region.model.inter.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.region.model.entities.BaseAdminstrativeRegionEntity_HI;
import com.sie.saaf.base.region.model.entities.readonly.BaseAdminstrativeRegion_HI_RO;
import com.sie.saaf.base.region.model.inter.IBaseAdminstrativeRegion;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("baseAdminstrativeRegionServer")
public class BaseAdminstrativeRegionServer extends
		BaseCommonServer<BaseAdminstrativeRegionEntity_HI> implements
		IBaseAdminstrativeRegion {
	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(BaseAdminstrativeRegionServer.class);
	//
	// @Autowired
	// private ViewObject<BaseAdminstrativeRegionEntity_HI>
	// baseAdminstrativeRegionDAO_HI;
	@Autowired
	private BaseViewObject<BaseAdminstrativeRegion_HI_RO> baseAdminstrativeRegionDAO_HI_RO;

	public BaseAdminstrativeRegionServer() {
		super();
	}

	@Override
	protected void setEntityDefaultValue(BaseAdminstrativeRegionEntity_HI entity) {
		if (entity.getDeleteFlag() == null) {
			entity.setDeleteFlag(CommonConstants.DELETE_FALSE);
		}
	}

	/**
	 * 分页查询行政区域列表
	 * 
	 * @author ZhangJun
	 * @createTime 2018/11/20
	 * @description 分页查询行政区域列表
	 */
	@Override
	public Pagination<BaseAdminstrativeRegion_HI_RO> findRegionPagination(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sb = new StringBuffer(BaseAdminstrativeRegion_HI_RO.QUERY);
		sb.append(" and region.delete_flag<>1 ");
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(BaseAdminstrativeRegion_HI_RO.class,
				queryParamJSON, sb, paramsMap);

		StringBuffer countString = SaafToolUtils.getSqlCountString(sb);

		Pagination<BaseAdminstrativeRegion_HI_RO> findPagination = baseAdminstrativeRegionDAO_HI_RO
				.findPagination(sb, countString, paramsMap, pageIndex, pageRows);
		return findPagination;
	}

	/**
	 * 根据条件查询行政区域列表
	 * 
	 * @author ZhangJun
	 * @createTime 2018/11/20
	 * @description 根据条件查询行政区域列表
	 */
	@Override
	public List<BaseAdminstrativeRegion_HI_RO> findRegionList(
			JSONObject queryParamJSON) {
		StringBuffer sb = new StringBuffer(BaseAdminstrativeRegion_HI_RO.QUERY);
		sb.append(" and region.delete_flag<>1 ");
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(BaseAdminstrativeRegion_HI_RO.class,
				queryParamJSON, sb, paramsMap);
		List<BaseAdminstrativeRegion_HI_RO> findList = baseAdminstrativeRegionDAO_HI_RO
				.findList(sb, paramsMap);
		return findList;
	}

	/**
	 * 查询国家
	 * 
	 * @author ZhangJun
	 * @createTime 2018/11/20
	 * @description 查询国家
	 */
	@Override
	public List<BaseAdminstrativeRegion_HI_RO> findCountry(
			JSONObject queryParamJSON) {
		queryParamJSON.put("regionLevel", "country");
		return this.findRegionList(queryParamJSON);
	}

	/**
	 * 查询省份
	 * 
	 * @author ZhangJun
	 * @createTime 2018/11/20
	 * @description 查询省份
	 */
	@Override
	public List<BaseAdminstrativeRegion_HI_RO> findProvinces(
			JSONObject queryParamJSON) {
		queryParamJSON.put("regionLevel", "1");
		return this.findRegionList(queryParamJSON);
	}
}
