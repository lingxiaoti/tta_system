package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierContactsEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSupplierContacts;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

@Component("equPosSupplierContactsServer")
public class EquPosSupplierContactsServer extends BaseCommonServer<EquPosSupplierContactsEntity_HI> implements IEquPosSupplierContacts{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierContactsServer.class);

	@Autowired
	private ViewObject<EquPosSupplierContactsEntity_HI> equPosSupplierContactsDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierContactsEntity_HI_RO> equPosSupplierContactsEntity_HI_RO;

	public EquPosSupplierContactsServer() {
		super();
	}

    @Override
    public EquPosSupplierContactsEntity_HI findContactPeopleInfo(String params) {
        JSONObject paramsJson = parseObject(params);
        List<EquPosSupplierContactsEntity_HI> list = equPosSupplierContactsDAO_HI.findByProperty("userId", paramsJson.getInteger("userId"));
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    /**
	 * 供应商联系人目录查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierContactsInfo(JSONObject queryParamJSON, Integer pageIndex,
											   Integer pageRows) {
		if(queryParamJSON.containsKey("deptCode")){
			if(!"0E".equals(queryParamJSON.getString("deptCode"))){
				queryParamJSON.remove("deptCode");
				queryParamJSON.put("deptCode_neq","0E");
			}
		}
		StringBuffer sql = new StringBuffer(EquPosSupplierContactsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierContactsEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosSupplierContactsEntity_HI_RO> pagination = equPosSupplierContactsEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商联系人信息-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosSupplierContactsEntity_HI saveSupplierContactsInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 供应商联系人信息-删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteSupplierContactsInfo(JSONObject jsonObject) {
		Integer supplierContactId =jsonObject.getInteger("id");
		EquPosSupplierContactsEntity_HI supplierContactEntity =equPosSupplierContactsDAO_HI.getById(supplierContactId);
		if(supplierContactEntity!=null){
			equPosSupplierContactsDAO_HI.delete(supplierContactEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}

	/**
	 * 供应商联系人信息报表查询(Non-trade)
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierContactReportForm(JSONObject queryParamJSON, Integer pageIndex,
												 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosSupplierContactsEntity_HI_RO.QUERY_SQL_SUPPLIER_CONTACT_REPORT_FORM);
		Map<String, Object> map = new HashMap<>();

		if(queryParamJSON.containsKey("supplierStatus") && !"".equals(queryParamJSON.getString("supplierStatus"))){
			String supplierStatus = queryParamJSON.getString("supplierStatus");
			if("APPROVING".equals(supplierStatus)){
				sql.append(" and  t.supplier_status in ('APPROVING','TEMPLATE')");
			}else{
				sql.append(" and  t.supplier_status = '" + supplierStatus + "'");
			}
			queryParamJSON.remove("supplierStatus");
		}

		if(queryParamJSON.containsKey("supplierNumber") && !"".equals(queryParamJSON.getString("supplierNumber"))){
			String supplierNumber = queryParamJSON.getString("supplierNumber");
			sql.append(" and  t.supplier_number like '%" + supplierNumber + "%'");
			queryParamJSON.remove("supplierNumber");
		}

		if(queryParamJSON.containsKey("supplierName_like") && !"".equals(queryParamJSON.getString("supplierName_like"))){
			String supplierName = queryParamJSON.getString("supplierName_like");
			sql.append(" and  t.supplier_name like '%" + supplierName + "%'");
			queryParamJSON.remove("supplierName_like");
		}

		if(queryParamJSON.containsKey("supplierShortName_like") && !"".equals(queryParamJSON.getString("supplierShortName_like"))){
			String supplierShortName = queryParamJSON.getString("supplierShortName_like");
			sql.append(" and  t.supplier_short_name like '%" + supplierShortName + "%'");
			queryParamJSON.remove("supplierShortName_like");
		}

		if(queryParamJSON.containsKey("licenseNum_like") && !"".equals(queryParamJSON.getString("licenseNum_like"))){
			String licenseNum = queryParamJSON.getString("licenseNum_like");
			sql.append(" and  t.license_num like '%" + licenseNum + "%'");
			queryParamJSON.remove("licenseNum_like");
		}

		if(queryParamJSON.containsKey("supplierType") && !"".equals(queryParamJSON.getString("supplierType"))){
			String supplierType = queryParamJSON.getString("supplierType");
			sql.append(" and  t.supplier_type = '" + supplierType + "'");
			queryParamJSON.remove("supplierType");
		}

		String expireDays = queryParamJSON.getString("expireDays");
		String serviceScope = queryParamJSON.getString("serviceScope");

		if(null != serviceScope && !"".equals(serviceScope)){
			sql.append(" and  t.category_count > 0 ");
		}else{
			queryParamJSON.put("serviceScope","-1");
		}

		if(null != expireDays && !"".equals(expireDays)){
			sql.append(" and  t.expire_days <= " + expireDays);
		}

		map.put("serviceScope",queryParamJSON.getString("serviceScope"));

		sql.append(" order by t.supplier_id desc");
		Pagination<EquPosSupplierContactsEntity_HI_RO> pagination = equPosSupplierContactsEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}
}
