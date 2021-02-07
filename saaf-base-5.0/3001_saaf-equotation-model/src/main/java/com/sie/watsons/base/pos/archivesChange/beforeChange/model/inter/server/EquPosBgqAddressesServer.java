package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgCapacityEntity_HI_RO;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgProductionInfoEntity_HI_RO;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server.EquPosBgCapacityServer;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server.EquPosBgProductionInfoServer;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.*;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqAddressesEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqAddresses;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqAddressesServer")
public class EquPosBgqAddressesServer extends BaseCommonServer<EquPosBgqAddressesEntity_HI> implements IEquPosBgqAddresses{
	@Autowired
	private ViewObject<EquPosBgqAddressesEntity_HI> equPosBgqAddressesDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqAddressesEntity_HI_RO> equPosBgqAddressesEntity_HI_RO;

	@Autowired
	private EquPosBgqProductionInfoServer equPosBgqProductionInfoServer;

	@Autowired
	private EquPosBgCapacityServer equPosBgCapacityServer;

	@Autowired
	private EquPosBgProductionInfoServer equPosBgProductionInfoServer;

	@Autowired
	private EquPosBgqCapacityServer equPosBgqCapacityServer;

	@Autowired
	private EquPosBgqOperationStatusServer equPosBgqOperationStatusServer;

	@Autowired
	private EquPosBgqCredentialAttachServer equPosBgqCredentialAttachServer;

	public EquPosBgqAddressesServer() {
		super();
	}

	/**
	 * 档案变更前-供应商地址信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqSupplierAddressInfo(JSONObject queryParamJSON, Integer pageIndex,
												Integer pageRows) {
		String deptCode = queryParamJSON.getString("deptCode");
		StringBuffer sql = new StringBuffer(EquPosBgqAddressesEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqAddressesEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by sa.source_id ");
		Pagination<EquPosBgqAddressesEntity_HI_RO> pagination = equPosBgqAddressesEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);

		List<EquPosBgqAddressesEntity_HI_RO> addressList = pagination.getData();

		JSONObject queryJson = new JSONObject();

		for(int i = 0; i < addressList.size(); i++){
			EquPosBgqAddressesEntity_HI_RO address = addressList.get(i);
			queryJson.clear();
			queryJson.put("supplierAddressId",address.getSupplierAddressId() == null ? address.getSourceId() : address.getSupplierAddressId());
			queryJson.put("changeId",address.getChangeId());

			//查询生产信息
			//查询生产信息
			EquPosBgProductionInfoEntity_HI_RO productionObj = equPosBgProductionInfoServer.findProductionInfoById(queryJson);
			EquPosBgqProductionInfoEntity_HI_RO productionObj2 = equPosBgqProductionInfoServer.findProductionInfoById(queryJson);
			//查询经营状况
			EquPosBgqOperationStatusEntity_HI_RO operationalObj = equPosBgqOperationStatusServer.findOperationalInfoById(queryJson);
			//查询产能信息
			List<EquPosBgCapacityEntity_HI_RO> capacityArray = equPosBgCapacityServer.findCapacityInfoById(queryJson);
			List<EquPosBgqCapacityEntity_HI_RO> capacityArray2 = equPosBgqCapacityServer.findCapacityInfoById(queryJson);

//			address.setOemProductionInfoParams(productionObj);
//			address.setOemCapacityInfoParams(capacityArray);
//			address.setOemProductionInfoBgqParams(productionObj2);
//			address.setOemCapacityInfoBgqParams(capacityArray2);

			if(!"0E".equals(deptCode)){
				queryJson.put("fileType","SurEnvironment");
				List<EquPosBgqCredentialAttachEntity_HI_RO> surEnvironmentArray = equPosBgqCredentialAttachServer.findBgqOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","DoorPlate");
				List<EquPosBgqCredentialAttachEntity_HI_RO> doorPlateArray = equPosBgqCredentialAttachServer.findBgqOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","Reception");
				List<EquPosBgqCredentialAttachEntity_HI_RO> receptionArray = equPosBgqCredentialAttachServer.findBgqOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","CompanyArea");
				List<EquPosBgqCredentialAttachEntity_HI_RO> companyAreaArray = equPosBgqCredentialAttachServer.findBgqOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","OfficeSpace");
				List<EquPosBgqCredentialAttachEntity_HI_RO> officeSpaceArray = equPosBgqCredentialAttachServer.findBgqOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","EmployeeProfile");
				List<EquPosBgqCredentialAttachEntity_HI_RO> employeeProfileArray = equPosBgqCredentialAttachServer.findBgqOperationalAttachmentInfo(queryJson);

//				address.setOemProductionInfoParams(productionObj);
//				address.setOemCapacityInfoParams(capacityArray);
//				address.setOemProductionInfoBgqParams(productionObj2);
//				address.setOemCapacityInfoBgqParams(capacityArray2);

				address.setItOperationalInfoParams(operationalObj);
				address.setItCapacityInfoParams(capacityArray2);

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
		}
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-供应商地址信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqAddressesEntity_HI saveBgqSupplierAddressInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更前-供应商地址信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgqSupplierAddressInfo(JSONObject jsonObject) {
		Integer addressId =jsonObject.getInteger("id");
		EquPosBgqAddressesEntity_HI addressEntity =equPosBgqAddressesDAO_HI.getById(addressId);
		if(addressEntity!=null){
			equPosBgqAddressesDAO_HI.delete(addressEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
