package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscSupplierEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscSupplierEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscSupplier;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscSupplierServer")
public class EquPosZzscSupplierServer extends BaseCommonServer<EquPosZzscSupplierEntity_HI> implements IEquPosZzscSupplier{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscSupplierServer.class);

	@Autowired
	private ViewObject<EquPosZzscSupplierEntity_HI> equPosZzscSupplierDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscSupplierEntity_HI_RO> equPosZzscSupplierEntity_HI_RO;

	public EquPosZzscSupplierServer() {
		super();
	}

	/**
	 * 资质审查-供应商基础信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscSupplierInfo(JSONObject queryParamJSON, Integer pageIndex,
									   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscSupplierEntity_HI_RO.QUERY_SQL);
		if(queryParamJSON.get("searchType")!=null&&"WAREHOUSING".equals(queryParamJSON.getString("searchType"))){
			sql = new StringBuffer(EquPosZzscSupplierEntity_HI_RO.QUERY_WAREHOUSING_SQL);
		}
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscSupplierEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosZzscSupplierEntity_HI_RO> pagination = equPosZzscSupplierEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		System.out.println("111111111111111");
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 资质审查-供应商基础信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscSupplierEntity_HI saveZzscSupplierInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	@Override
	public Pagination<EquPosZzscSupplierEntity_HI_RO> findZzscSupplierLov(JSONObject jsonParam, Integer pageIndex, Integer pageRows) {
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		StringBuffer queryString = new StringBuffer(EquPosZzscSupplierEntity_HI_RO.QUERY_SUPPLIER_SQL);
		Map<String, Object> map = new HashMap<String, Object>();

//        queryString.append("   AND exists (\n" +
//                "       select 1 from equ_pos_qualification_info q where q.qualification_status = '50' and q.supplier_id = t.supplier_id\n" +
//                "   )");
		SaafToolUtils.parperParam(jsonParam, "T.sup_Suspend_Id", "id", queryString, map, "=");
		if ("warehousing".equals(jsonParam.getString("type"))){
			queryString.append(" and t.supplier_status <> 'BLACKLIST' and  d.supplier_status <> 'OUT' and d.supplier_status <> 'SUSPEND' ");
		}

		if(jsonParam.get("deptCode")!=null){
//            SaafToolUtils.parperParam(jsonParam, "d.dept_Code", "deptCode", queryString, map, "=");
			queryString.append(" and d.dept_Code = '" + jsonParam.getString("deptCode") + "'");
		}else{
			queryString.append( " and 1 = 2 ");
		}

		SaafToolUtils.parperParam(jsonParam, "T.supplier_Name", "supplierName", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "T.supplier_Number", "supplierNumber", queryString, map, "like");
		// 排序
		queryString.append(" order by t.creation_date desc");
		Pagination<EquPosZzscSupplierEntity_HI_RO> sceneManageList = equPosZzscSupplierEntity_HI_RO.findPagination(queryString, map, pageIndex, pageRows);
		return sceneManageList;
	}
}
