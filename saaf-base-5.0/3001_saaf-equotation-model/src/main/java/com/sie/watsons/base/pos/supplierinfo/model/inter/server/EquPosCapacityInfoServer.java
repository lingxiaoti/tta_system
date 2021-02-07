package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosCapacityInfoEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCapacityInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosCapacityInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosCapacityInfoServer")
public class EquPosCapacityInfoServer extends BaseCommonServer<EquPosCapacityInfoEntity_HI> implements IEquPosCapacityInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCapacityInfoServer.class);

	@Autowired
	private ViewObject<EquPosCapacityInfoEntity_HI> equPosCapacityInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosCapacityInfoEntity_HI_RO> equPosCapacityInfoEntity_HI_RO;

	public EquPosCapacityInfoServer() {
		super();
	}

	/**
	 * 查询供应商产能信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findCapacityInfo(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosCapacityInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosCapacityInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by pci.capacity_id");
		Pagination<EquPosCapacityInfoEntity_HI_RO> pagination = equPosCapacityInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 查询供应商产能信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public List<EquPosCapacityInfoEntity_HI_RO> findCapacityInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosCapacityInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosCapacityInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by pci.capacity_id");
		List<EquPosCapacityInfoEntity_HI_RO> list = equPosCapacityInfoEntity_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 * 供应商产能信息-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosCapacityInfoEntity_HI saveCapacityInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 供应商产能信息-删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteCapacityInfo(JSONObject jsonObject) {
		Integer capacityId =jsonObject.getInteger("id");
		EquPosCapacityInfoEntity_HI capacityEntity = equPosCapacityInfoDAO_HI.getById(capacityId);
		if(capacityEntity!=null){
			equPosCapacityInfoDAO_HI.delete(capacityEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
