package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqCapacityEntity_HI_RO;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqProductionInfoEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.*;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierAddressesEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSupplierAddresses;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

import static com.sie.saaf.common.services.CommonAbstractService.SUCCESS_MSG;
import static com.sie.saaf.common.services.CommonAbstractService.SUCCESS_STATUS;

@Component("equPosSupplierAddressesServer")
public class EquPosSupplierAddressesServer extends BaseCommonServer<EquPosSupplierAddressesEntity_HI> implements IEquPosSupplierAddresses{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierAddressesServer.class);

	@Autowired
	private ViewObject<EquPosSupplierAddressesEntity_HI> equPosSupplierAddressesDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierAddressesEntity_HI_RO> equPosSupplierAddressesEntity_HI_RO;

	@Autowired
	private EquPosProductionInfoServer equPosProductionInfoServer;

	@Autowired
	private EquPosCapacityInfoServer equPosCapacityInfoServer;

	@Autowired
	private EquPosOperationalStatusServer equPosOperationalStatusServer;

	@Autowired
	private EquPosCredentialsAttachmentServer equPosCredentialsAttachmentServer;


	public EquPosSupplierAddressesServer() {
		super();
	}

	/**
	 * 供应商地址信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierAddressInfo(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		String deptCode = queryParamJSON.getString("deptCode");

		if(queryParamJSON.containsKey("deptCode")){
			if(!"0E".equals(queryParamJSON.getString("deptCode"))){
				queryParamJSON.remove("deptCode");
				queryParamJSON.put("deptCode_neq","0E");
			}
		}

		StringBuffer sql = new StringBuffer(EquPosSupplierAddressesEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierAddressesEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by sa.supplier_address_id");
		Pagination<EquPosSupplierAddressesEntity_HI_RO> pagination = equPosSupplierAddressesEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);

		List<EquPosSupplierAddressesEntity_HI_RO> addressList = pagination.getData();

		JSONObject queryJson = new JSONObject();

		for(int i = 0; i < addressList.size(); i++){
			EquPosSupplierAddressesEntity_HI_RO address = addressList.get(i);
			queryJson.clear();
			queryJson.put("supplierAddressId",address.getSupplierAddressId());
			queryJson.put("supplierId",address.getSupplierId());

			//查询生产信息
			EquPosProductionInfoEntity_HI_RO productionObj = equPosProductionInfoServer.findProductionInfoById(queryJson);
			EquPosProductionInfoEntity_HI_RO productionObj2 = equPosProductionInfoServer.findProductionInfoById(queryJson);

			//查询经营状况
			EquPosOperationalStatusEntity_HI_RO operationalObj = equPosOperationalStatusServer.findOperationalInfoById(queryJson);
			//查询产能信息
			List<EquPosCapacityInfoEntity_HI_RO> capacityArray = equPosCapacityInfoServer.findCapacityInfoById(queryJson);
			List<EquPosCapacityInfoEntity_HI_RO> capacityArray2 = equPosCapacityInfoServer.findCapacityInfoById(queryJson);
//			Collections.copy(capacityArray2,capacityArray);
//			List<EquPosCapacityInfoEntity_HI_RO> capacityArray2 = new ArrayList<>();
//			CollectionUtils.addAll(capacityArray2, new Object[capacityArray.size()]);
//			Collections.copy(capacityArray2, capacityArray);
//			List<EquPosCapacityInfoEntity_HI_RO> capacityArray2 = equPosCapacityInfoServer.findCapacityInfoById(queryJson);


			if("0E".equals(deptCode)){
//				address.setOemProductionInfoParams(productionObj);
//				address.setOemCapacityInfoParams(capacityArray);
				address.setOemProductionInfoParams(productionObj);
				address.setOemCapacityInfoParams(capacityArray);
				address.setOemProductionInfoBgqParams(productionObj2);
				address.setOemCapacityInfoBgqParams(capacityArray2);
			}else{
				queryJson.put("fileType","SurEnvironment");
				List<EquPosCredentialsAttachmentEntity_HI_RO> surEnvironmentArray = equPosCredentialsAttachmentServer.findOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","DoorPlate");
				List<EquPosCredentialsAttachmentEntity_HI_RO> doorPlateArray = equPosCredentialsAttachmentServer.findOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","Reception");
				List<EquPosCredentialsAttachmentEntity_HI_RO> receptionArray = equPosCredentialsAttachmentServer.findOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","CompanyArea");
				List<EquPosCredentialsAttachmentEntity_HI_RO> companyAreaArray = equPosCredentialsAttachmentServer.findOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","OfficeSpace");
				List<EquPosCredentialsAttachmentEntity_HI_RO> officeSpaceArray = equPosCredentialsAttachmentServer.findOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","EmployeeProfile");
				List<EquPosCredentialsAttachmentEntity_HI_RO> employeeProfileArray = equPosCredentialsAttachmentServer.findOperationalAttachmentInfo(queryJson);


				address.setItOperationalInfoParams(operationalObj);
				address.setItCapacityInfoParams(capacityArray);
				address.setSurEnvironmentDataTable(surEnvironmentArray);
				address.setDoorPlateDataTable(doorPlateArray);
				address.setReceptionDataTable(receptionArray);
				address.setCompanyAreaDataTable(companyAreaArray);
				address.setOfficeSpaceDataTable(officeSpaceArray);
				address.setEmployeeProfileDataTable(employeeProfileArray);
			}
		}
		return JSONObject.parseObject(JSONObject.toJSONString(pagination, SerializerFeature.DisableCircularReferenceDetect));
	}

	/**
	 * 供应商地址信息-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosSupplierAddressesEntity_HI saveSupplierAddressInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 供应商地址信息-删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteSupplierAddressInfo(JSONObject jsonObject) {
		Integer addressId =jsonObject.getInteger("id");
		EquPosSupplierAddressesEntity_HI addressEntity =equPosSupplierAddressesDAO_HI.getById(addressId);
		if(addressEntity!=null){
			equPosSupplierAddressesDAO_HI.delete(addressEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}

	/**
	 * 供应商具体经营状况及现场信息报表查询(Non-trade)
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierAddressReportForm(JSONObject queryParamJSON, Integer pageIndex,
													Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosSupplierAddressesEntity_HI_RO.QUERY_SQL_SUPPLIER_ADDRESS_REPORT_FORM);
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
		Pagination<EquPosSupplierAddressesEntity_HI_RO> pagination = equPosSupplierAddressesEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}
}
