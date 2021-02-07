package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.*;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqCapacityEntity_HI_RO;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqOperationStatusEntity_HI_RO;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqProductionInfoEntity_HI_RO;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server.EquPosBgqCapacityServer;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server.EquPosBgqOperationStatusServer;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server.EquPosBgqProductionInfoServer;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgAddressesEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgAddresses;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgAddressesServer")
public class EquPosBgAddressesServer extends BaseCommonServer<EquPosBgAddressesEntity_HI> implements IEquPosBgAddresses{
	@Autowired
	private ViewObject<EquPosBgAddressesEntity_HI> equPosBgAddressesDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgAddressesEntity_HI_RO> equPosBgAddressesEntity_HI_RO;

	@Autowired
	private EquPosBgProductionInfoServer equPosBgProductionInfoServer;

	@Autowired
	private EquPosBgqProductionInfoServer equPosBgqProductionInfoServer;

	@Autowired
	private EquPosBgqCapacityServer equPosBgqCapacityServer;

	@Autowired
	private EquPosBgCapacityServer equPosBgCapacityServer;

	@Autowired
	private EquPosBgOperationStatusServer equPosBgOperationStatusServer;

	@Autowired
	private EquPosBgqOperationStatusServer equPosBgqOperationStatusServer;

	@Autowired
	private EquPosBgCredentialAttachServer equPosBgCredentialAttachServer;

	public EquPosBgAddressesServer() {
		super();
	}

	/**
	 * 档案变更-供应商地址信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgSupplierAddressInfo(JSONObject queryParamJSON, Integer pageIndex,
												  Integer pageRows) {
		String deptCode = queryParamJSON.getString("deptCode");
		StringBuffer sql = new StringBuffer(EquPosBgAddressesEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgAddressesEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by sa.source_id ");
		Pagination<EquPosBgAddressesEntity_HI_RO> pagination = equPosBgAddressesEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);

		List<EquPosBgAddressesEntity_HI_RO> addressList = pagination.getData();

		JSONObject queryJson = new JSONObject();

		for(int i = 0; i < addressList.size(); i++){
			EquPosBgAddressesEntity_HI_RO address = addressList.get(i);
			queryJson.clear();
//			queryJson.put("supplierAddressId",address.getSourceId());
			queryJson.put("supplierAddressId",address.getSupplierAddressId() == null ? address.getSourceId() : address.getSupplierAddressId());
			queryJson.put("changeId",address.getChangeId());

			//查询生产信息
			EquPosBgProductionInfoEntity_HI_RO productionObj = equPosBgProductionInfoServer.findProductionInfoById(queryJson);
			EquPosBgqProductionInfoEntity_HI_RO productionObj2 = equPosBgqProductionInfoServer.findProductionInfoById(queryJson);
			//查询经营状况
			EquPosBgOperationStatusEntity_HI_RO operationalObj = equPosBgOperationStatusServer.findOperationalInfoById(queryJson);
			operationalObj = operationalObj == null ? new EquPosBgOperationStatusEntity_HI_RO() : operationalObj;

			EquPosBgqOperationStatusEntity_HI_RO operationalObj2 = equPosBgqOperationStatusServer.findOperationalInfoById(queryJson);
			operationalObj2 = operationalObj2 == null ? new EquPosBgqOperationStatusEntity_HI_RO() : operationalObj2;

			//查询产能信息
			List<EquPosBgCapacityEntity_HI_RO> capacityArray = equPosBgCapacityServer.findCapacityInfoById(queryJson);
			List<EquPosBgqCapacityEntity_HI_RO> capacityArray2 = equPosBgqCapacityServer.findCapacityInfoById(queryJson);

			if(null == productionObj && null != address.getSupplierAddressId()){
				queryJson.put("supplierAddressId",address.getSupplierAddressId());
				queryJson.put("changeId",address.getChangeId());
				productionObj = equPosBgProductionInfoServer.findProductionInfoById(queryJson);
			}

			if(null == productionObj2 && null != address.getSupplierAddressId()){
				queryJson.put("supplierAddressId",address.getSupplierAddressId());
				queryJson.put("changeId",address.getChangeId());
				productionObj2 = equPosBgqProductionInfoServer.findProductionInfoById(queryJson);
			}

			if(null == capacityArray || capacityArray.size() == 0 && null != address.getSupplierAddressId()){
				queryJson.put("supplierAddressId",address.getSupplierAddressId());
				queryJson.put("changeId",address.getChangeId());
				capacityArray = equPosBgCapacityServer.findCapacityInfoById(queryJson);
			}

			if(null == capacityArray2 || capacityArray2.size() == 0 && null != address.getSupplierAddressId()){
				queryJson.put("supplierAddressId",address.getSupplierAddressId());
				queryJson.put("changeId",address.getChangeId());
				capacityArray2 = equPosBgqCapacityServer.findCapacityInfoById(queryJson);
			}

			if(!"0E".equals(deptCode)){
				queryJson.put("fileType","SurEnvironment");
				List<EquPosBgCredentialAttachEntity_HI_RO> surEnvironmentArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","DoorPlate");
				List<EquPosBgCredentialAttachEntity_HI_RO> doorPlateArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","Reception");
				List<EquPosBgCredentialAttachEntity_HI_RO> receptionArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","CompanyArea");
				List<EquPosBgCredentialAttachEntity_HI_RO> companyAreaArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","OfficeSpace");
				List<EquPosBgCredentialAttachEntity_HI_RO> officeSpaceArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","EmployeeProfile");
				List<EquPosBgCredentialAttachEntity_HI_RO> employeeProfileArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);

				address.setOemProductionInfoParams(productionObj);
				address.setOemCapacityInfoParams(capacityArray);
				address.setOemProductionInfoBgqParams(productionObj2);
				address.setOemCapacityInfoBgqParams(capacityArray2);

				address.setItOperationalInfoParams(operationalObj);
			    address.setItCapacityInfoParams(capacityArray);
			    address.setItCapacityInfoBgqParams(capacityArray2);
			    address.setItOperationalInfoBgqParams(operationalObj2);

				address.setSurEnvironmentDataTable(surEnvironmentArray);
				address.setDoorPlateDataTable(doorPlateArray);
				address.setReceptionDataTable(receptionArray);
				address.setCompanyAreaDataTable(companyAreaArray);
				address.setOfficeSpaceDataTable(officeSpaceArray);
				address.setEmployeeProfileDataTable(employeeProfileArray);

			}else if("0E".equals(deptCode)){
				address.setOemProductionInfoParams(productionObj);
				address.setOemCapacityInfoParams(capacityArray);
				address.setOemProductionInfoBgqParams(productionObj2);
				address.setOemCapacityInfoBgqParams(capacityArray2);
			}


//			address.setOemProductionInfoParams(productionObj);
//			address.setOemCapacityInfoParams(capacityArray);
//			address.setOemProductionInfoBgqParams(productionObj2);
//			address.setOemCapacityInfoBgqParams(capacityArray2);
//
//			queryJson.put("fileType","SurEnvironment");
//			List<EquPosBgCredentialAttachEntity_HI_RO> surEnvironmentArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
//			queryJson.put("fileType","DoorPlate");
//			List<EquPosBgCredentialAttachEntity_HI_RO> doorPlateArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
//			queryJson.put("fileType","Reception");
//			List<EquPosBgCredentialAttachEntity_HI_RO> receptionArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
//			queryJson.put("fileType","CompanyArea");
//			List<EquPosBgCredentialAttachEntity_HI_RO> companyAreaArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
//			queryJson.put("fileType","OfficeSpace");
//			List<EquPosBgCredentialAttachEntity_HI_RO> officeSpaceArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
//			queryJson.put("fileType","EmployeeProfile");
//			List<EquPosBgCredentialAttachEntity_HI_RO> employeeProfileArray = equPosBgCredentialAttachServer.findBgOperationalAttachmentInfo(queryJson);
//
//
////			address.setItOperationalInfoParams(operationalObj);
////			address.setItCapacityInfoParams(capacityArray);
//			address.setSurEnvironmentDataTable(surEnvironmentArray);
//			address.setDoorPlateDataTable(doorPlateArray);
//			address.setReceptionDataTable(receptionArray);
//			address.setCompanyAreaDataTable(companyAreaArray);
//			address.setOfficeSpaceDataTable(officeSpaceArray);
//			address.setEmployeeProfileDataTable(employeeProfileArray);

		}
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-供应商地址信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgAddressesEntity_HI saveBgSupplierAddressInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更-供应商地址信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgSupplierAddressInfo(JSONObject jsonObject) {
		Integer addressId =jsonObject.getInteger("id");
		EquPosBgAddressesEntity_HI addressEntity =equPosBgAddressesDAO_HI.getById(addressId);
		if(addressEntity!=null){
			equPosBgAddressesDAO_HI.delete(addressEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
