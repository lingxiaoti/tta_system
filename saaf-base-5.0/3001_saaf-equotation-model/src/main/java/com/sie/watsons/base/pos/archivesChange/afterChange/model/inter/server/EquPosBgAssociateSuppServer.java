package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgAssociateSuppEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgAssociateSuppEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgAssociateSupp;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgAssociateSuppServer")
public class EquPosBgAssociateSuppServer extends BaseCommonServer<EquPosBgAssociateSuppEntity_HI> implements IEquPosBgAssociateSupp{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgAssociateSuppServer.class);

	@Autowired
	private ViewObject<EquPosBgAssociateSuppEntity_HI> equPosBgAssociateSuppDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgAssociateSuppEntity_HI_RO> equPosAssociatedSupplierEntity_HI_RO;

	public EquPosBgAssociateSuppServer() {
		super();
	}

	/**
	 * 档案变更-供应商关联工厂查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgAssociatedSupplier(JSONObject queryParamJSON, Integer pageIndex,
												 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgAssociateSuppEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgAssociateSuppEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.associated_id");
		Pagination<EquPosBgAssociateSuppEntity_HI_RO> pagination = equPosAssociatedSupplierEntity_HI_RO.findPagination(sql, map,1,999);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-供应商关联工厂保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgAssociateSuppEntity_HI saveBgAssociatedSupplier(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更-供应商关联工厂删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgAssociatedSupplier(JSONObject jsonObject) {
		Integer sourceId =jsonObject.getInteger("id");
		EquPosBgAssociateSuppEntity_HI associatedSupplierEntity =equPosBgAssociateSuppDAO_HI.getById(sourceId);
		if(associatedSupplierEntity!=null){
			equPosBgAssociateSuppDAO_HI.delete(associatedSupplierEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
