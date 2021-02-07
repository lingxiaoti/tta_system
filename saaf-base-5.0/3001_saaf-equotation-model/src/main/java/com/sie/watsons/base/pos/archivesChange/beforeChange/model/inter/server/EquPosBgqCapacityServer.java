package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqCapacityEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCapacityInfoEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCapacityEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqCapacity;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqCapacityServer")
public class EquPosBgqCapacityServer extends BaseCommonServer<EquPosBgqCapacityEntity_HI> implements IEquPosBgqCapacity{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqCapacityServer.class);

	@Autowired
	private ViewObject<EquPosBgqCapacityEntity_HI> equPosBgqCapacityDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqCapacityEntity_HI_RO> equPosBgqCapacityEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosCapacityInfoEntity_HI> equPosCapacityInfoDAO_HI;

	public EquPosBgqCapacityServer() {
		super();
	}

	/**
	 * 档案变更前-查询供应商产能信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqCapacityInfo(JSONObject queryParamJSON, Integer pageIndex,
										 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqCapacityEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqCapacityEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by pci.capacity_id ");
		Pagination<EquPosBgqCapacityEntity_HI_RO> pagination = equPosBgqCapacityEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-查询供应商产能信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public List<EquPosBgqCapacityEntity_HI_RO> findCapacityInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosBgqCapacityEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqCapacityEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosBgqCapacityEntity_HI_RO> list = equPosBgqCapacityEntity_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 * 档案变更前-供应商产能信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqCapacityEntity_HI saveBgqCapacityInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更前-供应商产能信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgqCapacityInfo(JSONObject jsonObject) {
		Integer capacityId =jsonObject.getInteger("id");
		EquPosBgqCapacityEntity_HI BgqCapacityEntity = equPosBgqCapacityDAO_HI.getById(capacityId);
		if(BgqCapacityEntity!=null){
			equPosBgqCapacityDAO_HI.delete(BgqCapacityEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
