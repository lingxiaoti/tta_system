package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscCapacityEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCapacityInfoEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCapacityEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscCapacity;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscCapacityServer")
public class EquPosZzscCapacityServer extends BaseCommonServer<EquPosZzscCapacityEntity_HI> implements IEquPosZzscCapacity{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscCapacityServer.class);

	@Autowired
	private ViewObject<EquPosZzscCapacityEntity_HI> equPosZzscCapacityDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscCapacityEntity_HI_RO> equPosZzscCapacityEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosCapacityInfoEntity_HI> equPosCapacityInfoDAO_HI;

	public EquPosZzscCapacityServer() {
		super();
	}

	/**
	 * 资质审查-查询供应商产能信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findZzscCapacityInfo(JSONObject queryParamJSON, Integer pageIndex,
									   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscCapacityEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscCapacityEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by pci.capacity_id");
		Pagination<EquPosZzscCapacityEntity_HI_RO> pagination = equPosZzscCapacityEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 资质审查-查询供应商产能信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public List<EquPosZzscCapacityEntity_HI_RO> findCapacityInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosZzscCapacityEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscCapacityEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by pci.capacity_id");
		List<EquPosZzscCapacityEntity_HI_RO> list = equPosZzscCapacityEntity_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 * 资质审查-供应商产能信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscCapacityEntity_HI saveZzscCapacityInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 资质审查-供应商产能信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteZzscCapacityInfo(JSONObject jsonObject) {
		Integer capacityId =jsonObject.getInteger("id");
		EquPosZzscCapacityEntity_HI zzscCapacityEntity = equPosZzscCapacityDAO_HI.getById(capacityId);
		if(zzscCapacityEntity!=null){
			equPosZzscCapacityDAO_HI.delete(zzscCapacityEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
