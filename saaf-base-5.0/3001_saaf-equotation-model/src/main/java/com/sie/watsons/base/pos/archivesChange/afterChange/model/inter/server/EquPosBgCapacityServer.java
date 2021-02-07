package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgCapacityEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCapacityInfoEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCapacityEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgCapacity;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgCapacityServer")
public class EquPosBgCapacityServer extends BaseCommonServer<EquPosBgCapacityEntity_HI> implements IEquPosBgCapacity{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgCapacityServer.class);

	@Autowired
	private ViewObject<EquPosBgCapacityEntity_HI> equPosBgCapacityDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgCapacityEntity_HI_RO> equPosBgCapacityEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosCapacityInfoEntity_HI> equPosCapacityInfoDAO_HI;

	public EquPosBgCapacityServer() {
		super();
	}

	/**
	 * 档案变更-查询供应商产能信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgCapacityInfo(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgCapacityEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgCapacityEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by pci.capacity_id ");
		Pagination<EquPosBgCapacityEntity_HI_RO> pagination = equPosBgCapacityEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-查询供应商产能信息
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public List<EquPosBgCapacityEntity_HI_RO> findCapacityInfoById(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosBgCapacityEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgCapacityEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosBgCapacityEntity_HI_RO> list = equPosBgCapacityEntity_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 * 档案变更-供应商产能信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgCapacityEntity_HI saveBgCapacityInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更-供应商产能信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgCapacityInfo(JSONObject jsonObject) {
		Integer capacityId =jsonObject.getInteger("id");
		EquPosBgCapacityEntity_HI BgCapacityEntity = equPosBgCapacityDAO_HI.getById(capacityId);
		if(BgCapacityEntity!=null){
			equPosBgCapacityDAO_HI.delete(BgCapacityEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}

}
