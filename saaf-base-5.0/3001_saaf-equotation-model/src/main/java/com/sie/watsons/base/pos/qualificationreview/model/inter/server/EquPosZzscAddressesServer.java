package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.*;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscAddressesEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscAddresses;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscAddressesServer")
public class EquPosZzscAddressesServer extends BaseCommonServer<EquPosZzscAddressesEntity_HI> implements IEquPosZzscAddresses{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscAddressesServer.class);

	@Autowired
	private ViewObject<EquPosZzscAddressesEntity_HI> equPosZzscAddressesDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscAddressesEntity_HI_RO> equPosZzscAddressesEntity_HI_RO;

	@Autowired
	private EquPosZzscProductionInfoServer equPosZzscProductionInfoServer;

	@Autowired
	private EquPosZzscCapacityServer equPosZzscCapacityServer;

	@Autowired
	private EquPosZzscOperationStatusServer equPosZzscOperationStatusServer;

	@Autowired
	private EquPosZzscCredentialAttachServer equPosZzscCredentialAttachServer;

	public EquPosZzscAddressesServer() {
		super();
	}

	/**
	 * 供应商地址信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findZzscSupplierAddressInfo(JSONObject queryParamJSON, Integer pageIndex,
											  Integer pageRows) {
		String deptCode = queryParamJSON.getString("deptCode");
		StringBuffer sql = new StringBuffer(EquPosZzscAddressesEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscAddressesEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by sa.supplier_address_id");
		Pagination<EquPosZzscAddressesEntity_HI_RO> pagination = equPosZzscAddressesEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);

		List<EquPosZzscAddressesEntity_HI_RO> addressList = pagination.getData();

		JSONObject queryJson = new JSONObject();

		for(int i = 0; i < addressList.size(); i++){
			EquPosZzscAddressesEntity_HI_RO address = addressList.get(i);
			queryJson.clear();
			queryJson.put("supplierAddressId",address.getSupplierAddressId());
			queryJson.put("supplierId",address.getSupplierId());



			//查询生产信息
			EquPosZzscProductionInfoEntity_HI_RO productionObj = equPosZzscProductionInfoServer.findProductionInfoById(queryJson);
			//查询经营状况
			EquPosZzscOperationStatusEntity_HI_RO operationalObj = equPosZzscOperationStatusServer.findOperationalInfoById(queryJson);
			//查询产能信息
			List<EquPosZzscCapacityEntity_HI_RO> capacityArray = equPosZzscCapacityServer.findCapacityInfoById(queryJson);

			if(!"0E".equals(deptCode)){
				queryJson.put("fileType","SurEnvironment");
				List<EquPosZzscCredentialAttachEntity_HI_RO> surEnvironmentArray = equPosZzscCredentialAttachServer.findZzscOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","DoorPlate");
				List<EquPosZzscCredentialAttachEntity_HI_RO> doorPlateArray = equPosZzscCredentialAttachServer.findZzscOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","Reception");
				List<EquPosZzscCredentialAttachEntity_HI_RO> receptionArray = equPosZzscCredentialAttachServer.findZzscOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","CompanyArea");
				List<EquPosZzscCredentialAttachEntity_HI_RO> companyAreaArray = equPosZzscCredentialAttachServer.findZzscOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","OfficeSpace");
				List<EquPosZzscCredentialAttachEntity_HI_RO> officeSpaceArray = equPosZzscCredentialAttachServer.findZzscOperationalAttachmentInfo(queryJson);
				queryJson.put("fileType","EmployeeProfile");
				List<EquPosZzscCredentialAttachEntity_HI_RO> employeeProfileArray = equPosZzscCredentialAttachServer.findZzscOperationalAttachmentInfo(queryJson);

				address.setItOperationalInfoParams(operationalObj);
				address.setItCapacityInfoParams(capacityArray);
				address.setSurEnvironmentDataTable(surEnvironmentArray);
				address.setDoorPlateDataTable(doorPlateArray);
				address.setReceptionDataTable(receptionArray);
				address.setCompanyAreaDataTable(companyAreaArray);
				address.setOfficeSpaceDataTable(officeSpaceArray);
				address.setEmployeeProfileDataTable(employeeProfileArray);
			}else if("0E".equals(deptCode)){
				address.setOemProductionInfoParams(productionObj);
				address.setOemCapacityInfoParams(capacityArray);
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
	public EquPosZzscAddressesEntity_HI saveZzscSupplierAddressInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 供应商地址信息-删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteZzscSupplierAddressInfo(JSONObject jsonObject) {
		Integer addressId =jsonObject.getInteger("id");
		EquPosZzscAddressesEntity_HI addressEntity =equPosZzscAddressesDAO_HI.getById(addressId);
		if(addressEntity!=null){
			equPosZzscAddressesDAO_HI.delete(addressEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
