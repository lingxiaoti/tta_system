package com.sie.watsons.base.pos.archivesChange.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.*;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server.*;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.*;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server.*;
import com.sie.watsons.base.pos.archivesChange.model.entities.EquPosSupplierChangeEntity_HI;
import com.sie.watsons.base.pos.archivesChange.model.entities.readonly.EquPosSupplierChangeEntity_HI_RO;
import com.sie.watsons.base.pos.archivesChange.model.inter.IEquPosSupplierChange;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosQualificationInfoEntity_HI;
import com.sie.watsons.base.pos.qualityAudit.model.entities.EquPosSupplierQualityAuditEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.*;
import com.sie.watsons.base.pos.warehousing.model.entities.EquPosSupplierWarehousingEntity_HI;
import com.sie.watsons.base.utils.CommonUtils;
import com.yhg.base.utils.DigestUtils;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.apache.tomcat.util.codec.binary.Base64;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("equPosSupplierChangeServer")
public class EquPosSupplierChangeServer extends BaseCommonServer<EquPosSupplierChangeEntity_HI> implements IEquPosSupplierChange{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierChangeServer.class);

	@Autowired
	private ViewObject<EquPosSupplierChangeEntity_HI> equPosSupplierChangeDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierChangeEntity_HI_RO> equPosSupplierChangeEntity_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private EquPosBgSupplierServer equPosBgSupplierServer;

	@Autowired
	private EquPosBgAssociateSuppServer equPosBgAssociateSuppServer;

	@Autowired
	private EquPosBgqAssociateSuppServer equPosBgqAssociateSuppServer;

	@Autowired
	private EquPosBgqSupplierServer equPosBgqSupplierServer;

	@Autowired
	private EquPosBgCategoryServer equPosBgCategoryServer;

	@Autowired
	private EquPosBgqCategoryServer equPosBgqCategoryServer;

	@Autowired
	private EquPosBgContactsServer equPosBgContactsServer;

	@Autowired
	private EquPosBgqContactsServer equPosBgqContactsServer;

	@Autowired
	private EquPosBgqOperationStatusServer equPosBgqOperationStatusServer;

	@Autowired
	private EquPosBgOperationStatusServer equPosBgOperationStatusServer;

	@Autowired
	private EquPosBgCredentialsServer equPosBgCredentialsServer;

	@Autowired
	private EquPosBgqCredentialsServer equPosBgqCredentialsServer;

	@Autowired
	private EquPosBgBankServer equPosBgBankServer;

	@Autowired
	private EquPosBgqBankServer equPosBgqBankServer;

	@Autowired
	private EquPosBgCredentialAttachServer equPosBgCredentialAttachServer;

	@Autowired
	private EquPosBgqCredentialAttachServer equPosBgqCredentialAttachServer;

	@Autowired
	private EquPosBgAddressesServer equPosBgAddressesServer;

	@Autowired
	private EquPosBgqAddressesServer equPosBgqAddressesServer;

	@Autowired
	private EquPosBgProductionInfoServer equPosBgProductionInfoServer;

	@Autowired
	private EquPosBgqProductionInfoServer equPosBgqProductionInfoServer;

	@Autowired
	private EquPosBgCapacityServer equPosBgCapacityServer;

	@Autowired
	private EquPosBgqCapacityServer equPosBgqCapacityServer;

	@Autowired
	private ViewObject<EquPosBgSuppDeptInfoEntity_HI> equPosBgSuppDeptInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosBgqSuppDeptInfoEntity_HI> equPosBgqSuppDeptInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosBgAddressesEntity_HI> equPosBgAddressesEntity_HI;

	@Autowired
	private ViewObject<EquPosBgAssociateSuppEntity_HI> equPosBgAssociateSuppEntity_HI;

	@Autowired
	private ViewObject<EquPosBgBankEntity_HI> equPosBgBankEntity_HI;

	@Autowired
	private ViewObject<EquPosBgCapacityEntity_HI> equPosBgCapacityEntity_HI;

	@Autowired
	private ViewObject<EquPosBgCategoryEntity_HI> equPosBgCategoryEntity_HI;

	@Autowired
	private ViewObject<EquPosBgContactsEntity_HI> equPosBgContactsEntity_HI;

	@Autowired
	private ViewObject<EquPosBgCredentialAttachEntity_HI> equPosBgCredentialAttachEntity_HI;

	@Autowired
	private ViewObject<EquPosBgCredentialsEntity_HI> equPosBgCredentialsEntity_HI;

	@Autowired
	private ViewObject<EquPosBgOperationStatusEntity_HI> equPosBgOperationStatusEntity_HI;

	@Autowired
	private ViewObject<EquPosBgProductionInfoEntity_HI> equPosBgProductionInfoEntity_HI;

	@Autowired
	private ViewObject<EquPosBgSuppDeptInfoEntity_HI> equPosBgSuppDeptInfoEntity_HI;

	@Autowired
	private ViewObject<EquPosBgSupplierEntity_HI> equPosBgSupplierEntity_HI;

	@Autowired
	private ViewObject<EquPosAssociatedSupplierEntity_HI> equPosAssociatedSupplierEntity_HI;

	@Autowired
	private ViewObject<EquPosCapacityInfoEntity_HI> equPosCapacityInfoEntity_HI;

	@Autowired
	private ViewObject<EquPosCredentialsAttachmentEntity_HI> equPosCredentialsAttachmentEntity_HI;

	@Autowired
	private ViewObject<EquPosOperationalStatusEntity_HI> equPosOperationalStatusEntity_HI;

	@Autowired
	private ViewObject<EquPosProductionInfoEntity_HI> equPosProductionInfoEntity_HI;

	@Autowired
	private ViewObject<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptEntity_HI;

	@Autowired
	private ViewObject<EquPosSupplierAddressesEntity_HI> equPosSupplierAddressesEntity_HI;

	@Autowired
	private ViewObject<EquPosSupplierBankEntity_HI> equPosSupplierBankEntity_HI;

	@Autowired
	private ViewObject<EquPosSupplierContactsEntity_HI> equPosSupplierContactsEntity_HI;

	@Autowired
	private ViewObject<EquPosSupplierCredentialsEntity_HI> equPosSupplierCredentialsEntity_HI;

	@Autowired
	private ViewObject<EquPosSupplierInfoEntity_HI> equPosSupplierInfoEntity_HI;

	@Autowired
	private ViewObject<EquPosSupplierProductCatEntity_HI> equPosSupplierProductCatEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqSupplierEntity_HI> bgqSupplierDao;

	@Autowired
	private ViewObject<EquPosBgqAssociateSuppEntity_HI> equPosBgqAssociateSuppEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqCategoryEntity_HI> equPosBgqCategoryEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqAddressesEntity_HI> equPosBgqAddressesEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqBankEntity_HI> equPosBgqBankEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqCapacityEntity_HI> equPosBgqCapacityEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqContactsEntity_HI> equPosBgqContactsEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqCredentialAttachEntity_HI> equPosBgqCredentialAttachEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqCredentialsEntity_HI> equPosBgqCredentialsEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqOperationStatusEntity_HI> equPosBgqOperationStatusEntity_HI;

	@Autowired
	private ViewObject<EquPosBgqProductionInfoEntity_HI> equPosBgqProductionInfoEntity_HI;

	@Autowired
	private ViewObject<EquPosBgSupplierEntity_HI> bghSupplierDao;

	@Autowired
	private ViewObject<EquPosQualificationInfoEntity_HI> equPosQualificationInfoDao;

	@Autowired
	private ViewObject<EquPosSupplierWarehousingEntity_HI> equPosSupplierWarehousingDao;



	public EquPosSupplierChangeServer() {
		super();
	}

	/**
	 * 供应商档案变更单据，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findArchivesChangeOrder(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = null;
		if(queryParamJSON.containsKey("deptCode")){
			if(!"0E".equals(queryParamJSON.getString("deptCode"))){
				queryParamJSON.remove("deptCode");
				queryParamJSON.put("deptCode_neq","0E");
				sql = new StringBuffer(EquPosSupplierChangeEntity_HI_RO.QUERY_SQL2);
			}else{
				sql = new StringBuffer(EquPosSupplierChangeEntity_HI_RO.QUERY_SQL);
			}
		}else{
			sql = new StringBuffer(EquPosSupplierChangeEntity_HI_RO.QUERY_SQL);
		}

		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierChangeEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by sc.creation_date desc");
		Pagination<EquPosSupplierChangeEntity_HI_RO> pagination = equPosSupplierChangeEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public void operateValidate(String operator,Integer changeId,Integer userId) throws Exception{
		if(null != changeId){
			JSONObject queryObj = new JSONObject();
			queryObj.put("changeId",changeId);
			JSONObject changeObj = this.findArchivesChangeOrder(queryObj,1,999);
			JSONArray changeArr = changeObj.getJSONArray("data");
			JSONObject changeJson = changeArr.getJSONObject(0);
			String changeStatus = changeJson.getString("status");
			Integer createdBy = changeJson.getInteger("createdBy");
			//保存操作，状态校验
			if(userId.intValue() != createdBy.intValue()){
				throw new Exception("非法操作,系统拒绝响应!");
			}
			if("SAVE".equals(operator) || "SUBMIT".equals(operator)){
				if(!"10".equals(changeStatus) && !"40".equals(changeStatus)){
					throw new Exception("非法操作,系统拒绝响应!");
				}
			}
		}
	}

	/**
	 * 判断是否修改页签(可合作品类/资质信息/生产资质信息/产能信息)内容，如果有，则修改qaApprocalFlag标志为Y
	 */
	public boolean changeValidate(JSONObject params){
		//变更前参数
		JSONObject bgqParams = params.getJSONObject("bgqParams");
		//变更前-可合作品类
		JSONArray bgqCopCategoryInfoArray = bgqParams.getJSONArray("copCategoryInfo") == null ? new JSONArray():bgqParams.getJSONArray("copCategoryInfo");
		//变更前-资质信息
		JSONObject bgqSupplierCredentialsInfo = getParamsJSONObject(params,bgqParams.getJSONObject("supplierCredentialsInfo") == null ? new JSONObject():bgqParams.getJSONObject("supplierCredentialsInfo"));
		//变更前-生产资质信息
		JSONArray bgqProductQualificationFileInfoArray = bgqParams.getJSONArray("productQualificationFileInfo") == null ? new JSONArray():bgqParams.getJSONArray("productQualificationFileInfo");
		//变更前-产能信息
		JSONArray bgqOemAddressInfoArray = bgqParams.getJSONArray("oemAddressInfo") == null ? new JSONArray():bgqParams.getJSONArray("oemAddressInfo");

		//变更后参数
		JSONObject bghParams = params.getJSONObject("bghParams");
		//变更前-可合作品类
		JSONArray bghCopCategoryInfoArray = bghParams.getJSONArray("copCategoryInfo") == null ? new JSONArray():bghParams.getJSONArray("copCategoryInfo");
		//变更前-资质信息
		JSONObject bghSupplierCredentialsInfo = getParamsJSONObject(params,bghParams.getJSONObject("supplierCredentialsInfo") == null ? new JSONObject():bghParams.getJSONObject("supplierCredentialsInfo"));
		//变更前-生产资质信息
		JSONArray bghProductQualificationFileInfoArray = bghParams.getJSONArray("productQualificationFileInfo") == null ? new JSONArray():bghParams.getJSONArray("productQualificationFileInfo");
		//变更前-产能信息
		JSONArray bghOemAddressInfoArray = bghParams.getJSONArray("oemAddressInfo") == null ? new JSONArray():bghParams.getJSONArray("oemAddressInfo");

		//校验可合作品类是否变更
		//变更前后记录条数不一样，代表发生变更
		if(bgqCopCategoryInfoArray.size() != bghCopCategoryInfoArray.size()){
			return true;
		}
		for(int i = 0; i < bghCopCategoryInfoArray.size(); i++){
			JSONObject bghObj = bghCopCategoryInfoArray.getJSONObject(i);
			//判断新增行
			if(!bghObj.containsKey("supplierCategoryId")){
				return true;
			}
			for(int j = 0; j < bgqCopCategoryInfoArray.size(); j++){
				JSONObject bgqObj = bgqCopCategoryInfoArray.getJSONObject(j);
				if(bghObj.getInteger("supplierCategoryId").intValue() == bgqObj.getInteger("supplierCategoryId").intValue()){
					//判断字段修改
					if(bghObj.getInteger("categoryId").intValue() != bgqObj.getInteger("categoryId").intValue()){
						return true;
					}
				}
			}
		}

		//校验资质信息是否变更
		String[] paramsNames = {"licenseNum","licenseIndate","longBusinessIndate","licenseFileId","isThreeInOne","tissueCode","tissueIndate","tissueFileId","taxCode","taxFileId","bankPermitNumber","bankFileId","taxpayerFileId","companyCreditFileId","representativeName","businessScope","establishmentDate","enrollCapital","shareholderInfo","relatedParty"};
		for(int i = 0; i < paramsNames.length; i++){
			if(!defaultIfEmpty(bgqSupplierCredentialsInfo.getString(paramsNames[i])).equals(defaultIfEmpty(bghSupplierCredentialsInfo.getString(paramsNames[i])))){
				System.out.println(paramsNames[i]);
 				return true;
			}
		}

		//校验生产资质信息是否变更
		if(bgqProductQualificationFileInfoArray.size() != bghProductQualificationFileInfoArray.size()){
			return true;
		}
		for(int i = 0; i < bghProductQualificationFileInfoArray.size(); i++){
			JSONObject bghObj = bghProductQualificationFileInfoArray.getJSONObject(i);
			//判断新增行
			if(!bghObj.containsKey("attachmentId")){
				return true;
			}
			for(int j = 0; j < bgqProductQualificationFileInfoArray.size(); j++){
				JSONObject bgqObj = bgqProductQualificationFileInfoArray.getJSONObject(j);
				if(bghObj.getInteger("attachmentId").intValue() == bgqObj.getInteger("attachmentId").intValue()){
					//判断字段修改
					String[] productParams = {"fileId","description","invalidDate"};
					for(int k = 0; k < productParams.length; k++){
						if(!defaultIfEmpty(bgqObj.getString(productParams[k])).equals(defaultIfEmpty(bghObj.getString(productParams[k])))){
							return true;
						}
					}
				}
			}
		}

		//校验地址是否变更
		if(bgqOemAddressInfoArray.size() != bghOemAddressInfoArray.size()){
			return true;
		}
		for(int i = 0; i < bghOemAddressInfoArray.size(); i++){
			JSONObject bghObj = bghOemAddressInfoArray.getJSONObject(i);

			JSONObject bghProductionInfo = new JSONObject();
			JSONArray bghCapacityInfo = new JSONArray();
			if(bghObj.containsKey("oemProductionInfoParams")){
				bghProductionInfo = bghObj.getJSONObject("oemProductionInfoParams");
			}
			if(bghObj.containsKey("oemCapacityInfoBghDataTable")){
				bghCapacityInfo = bghObj.getJSONArray("oemCapacityInfoBghDataTable");
			}
			if(bghObj.containsKey("oemCapacityInfoParams")){
				bghCapacityInfo = bghObj.getJSONArray("oemCapacityInfoParams");
			}
			if(bghObj.containsKey("oemProductionInfoBghParams")){
				bghProductionInfo = bghObj.getJSONObject("oemProductionInfoBghParams");
			}

			//判断新增行
			if(!bghObj.containsKey("supplierAddressId")){
				return true;
			}

			for(int j = 0; j < bgqOemAddressInfoArray.size(); j++){
				JSONObject bgqObj = bgqOemAddressInfoArray.getJSONObject(j);

				if(bghObj.getInteger("supplierAddressId").intValue() == bgqObj.getInteger("supplierAddressId").intValue()){
					//判断字段修改
					if(!defaultIfEmpty(bghObj.getString("addressName")).equals(defaultIfEmpty(bgqObj.getString("addressName")))){
						return true;
					}
					if(!defaultIfEmpty(bghObj.getString("country")).equals(defaultIfEmpty(bgqObj.getString("country")))){
						return true;
					}
					if(!defaultIfEmpty(bghObj.getString("province")).equals(defaultIfEmpty(bgqObj.getString("province")))){
						return true;
					}
					if(!defaultIfEmpty(bghObj.getString("city")).equals(defaultIfEmpty(bgqObj.getString("city")))){
						return true;
					}
					if(!defaultIfEmpty(bghObj.getString("county")).equals(defaultIfEmpty(bgqObj.getString("county")))){
						return true;
					}
					if(!defaultIfEmpty(bghObj.getString("detailAddress")).equals(defaultIfEmpty(bgqObj.getString("detailAddress")))){
						return true;
					}
				}else{
					continue;
				}


				JSONObject bgqProductionInfo = bgqObj.getJSONObject("oemProductionInfoBgqParams") == null ? new JSONObject() : bgqObj.getJSONObject("oemProductionInfoBgqParams");
				JSONArray bgqCapacityInfo = bgqObj.getJSONArray("oemCapacityInfoBgqParams") == null ? new JSONArray() : bgqObj.getJSONArray("oemCapacityInfoBgqParams");

				//生产信息
				String productionParams[] = {"productionStartDate","productionArea","finishedProductArea","rawMaterialArea","packagingArea","laboratoryArea","officeArea","qualityPersonnelAmount","salesmanAmount","producersAmount","designerAmount","administrativeStaffAmount","other"};
				for(int m = 0; m < productionParams.length; m++){
					if(!defaultIfEmpty(bgqProductionInfo.getString(productionParams[m])).equals(defaultIfEmpty(bghProductionInfo.getString(productionParams[m])))){
						return true;
					}
				}
				//产能信息
				if(bghCapacityInfo.size() != bgqCapacityInfo.size()){
					return true;
				}
				for(int n = 0; n < bghCapacityInfo.size(); n++){
					JSONObject bghCapacityObj = bghCapacityInfo.getJSONObject(n);
					//判断新增行
					if(!bghCapacityObj.containsKey("capacityId")){
						return true;
					}
					for(int k = 0; k < bgqCapacityInfo.size(); k++){
						JSONObject bgqCapacityObj = bgqCapacityInfo.getJSONObject(k);
						if(bghCapacityObj.getInteger("capacityId").intValue() == bgqCapacityObj.getInteger("capacityId").intValue()){
							//判断字段修改
							if(!defaultIfEmpty(bgqCapacityObj.getString("productScope")).equals(defaultIfEmpty(bghCapacityObj.getString("productScope")))){
								return true;
							}
							if(!defaultIfEmpty(bgqCapacityObj.getString("productionEquipment")).equals(defaultIfEmpty(bghCapacityObj.getString("productionEquipment")))){
								return true;
							}
							if(!defaultIfEmpty(bgqCapacityObj.getString("productionCapacity")).equals(defaultIfEmpty(bghCapacityObj.getString("productionCapacity")))){
								return true;
							}
							if(!defaultIfEmpty(bgqCapacityObj.getString("remark")).equals(defaultIfEmpty(bghCapacityObj.getString("remark")))){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public String defaultIfEmpty(String str){
		return StringUtils.defaultIfEmpty(str,"");
	}

	/**
	 * 供应商档案变更单据-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosSupplierChangeEntity_HI saveArchivesChangeOrder(JSONObject params) throws Exception {
		EquPosSupplierChangeEntity_HI archivesChangeEntity = null;
		Map queryMap = new HashMap();

		JSONObject archivesChangeInfo = getParamsJSONObject(params,params.getJSONObject("archivesChangeInfo"));

		//校验用户操作权限
//		Integer userId = params.getInteger("varUserId");
//		Integer changeId = archivesChangeInfo.getInteger("changeId");
//		operateValidate("SAVE",changeId,userId);

		//校验是否存在还没有入库的部门内新增品类资质审查
		Map map = new HashMap();
		map.put("supplierId",archivesChangeInfo.getInteger("supplierId"));
		map.put("sceneType","50");
		List<EquPosQualificationInfoEntity_HI> qualificationList = equPosQualificationInfoDao.findByProperty(map);
		for(int i = 0; i < qualificationList.size(); i++){
			EquPosQualificationInfoEntity_HI qualificationEntity = qualificationList.get(i);
			if(!"50".equals(qualificationEntity.getQualificationStatus())){
				throw new Exception("该供应商部门内新增品类还没有入库完成,不能进行供应商档案变更操作!");
			}else{
				Map map2 = new HashMap();
				map2.put("supplierId",archivesChangeInfo.getInteger("supplierId"));
				map2.put("qualificationId",qualificationEntity.getQualificationId());
				map2.put("supWarehousingStatus","30");
				System.out.println("【supplierId】:" + archivesChangeInfo.getInteger("supplierId"));
				System.out.println("【qualificationId】:" + qualificationEntity.getQualificationId());
				List<EquPosSupplierWarehousingEntity_HI> supplierWarehousingList = equPosSupplierWarehousingDao.findByProperty(map2);
				if(supplierWarehousingList.size() == 0){
					throw new Exception("该供应商部门内新增品类还没有入库完成,不能进行供应商档案变更操作!");
				}
			}
		}


		if(changeValidate(params)){
			archivesChangeInfo.put("qaApprovalFlag","Y");
		}else{
			archivesChangeInfo.put("qaApprovalFlag","N");
		}

		String supplierChangeFlag = archivesChangeInfo.getString("supplierChangeFlag");
		if(archivesChangeInfo.containsKey("changeId") && null != supplierChangeFlag && "Y".equals(supplierChangeFlag)){
			//清空页签数据
			Integer changeId = archivesChangeInfo.getInteger("changeId");
			Map paramsMap = new HashMap();
			paramsMap.put("changeId",changeId.intValue());

			List<EquPosBgqSupplierEntity_HI> equPosBgqSupplierList = bgqSupplierDao.findByProperty(paramsMap);
			if(equPosBgqSupplierList.size() > 0){
				bgqSupplierDao.delete(equPosBgqSupplierList);
			}

			List<EquPosBgqSuppDeptInfoEntity_HI> equPosBgqSuppDeptInfoList = equPosBgqSuppDeptInfoDAO_HI.findByProperty(paramsMap);
			if(equPosBgqSuppDeptInfoList.size() > 0){
				equPosBgqSuppDeptInfoDAO_HI.delete(equPosBgqSuppDeptInfoList);
			}

			List<EquPosBgqAssociateSuppEntity_HI> equPosBgqAssociateSuppList = equPosBgqAssociateSuppEntity_HI.findByProperty(paramsMap);
			if(equPosBgqAssociateSuppList.size() > 0){
				equPosBgqAssociateSuppEntity_HI.delete(equPosBgqAssociateSuppList);
			}

			List<EquPosBgqCategoryEntity_HI> equPosBgqCategoryList = equPosBgqCategoryEntity_HI.findByProperty(paramsMap);
			if(equPosBgqCategoryList.size() > 0){
				equPosBgqCategoryEntity_HI.delete(equPosBgqCategoryList);
			}

			List<EquPosBgqContactsEntity_HI> equPosBgqContactsList = equPosBgqContactsEntity_HI.findByProperty(paramsMap);
			if(equPosBgqContactsList.size() > 0){
				equPosBgqContactsEntity_HI.delete(equPosBgqContactsList);
			}


			List<EquPosBgqBankEntity_HI> equPosBgqBankList = equPosBgqBankEntity_HI.findByProperty(paramsMap);
			if(equPosBgqBankList.size() > 0){
				equPosBgqBankEntity_HI.delete(equPosBgqBankList);
			}

			List<EquPosBgqCredentialsEntity_HI> equPosBgqCredentialsList = equPosBgqCredentialsEntity_HI.findByProperty(paramsMap);
			if(equPosBgqCredentialsList.size() > 0){
				equPosBgqCredentialsEntity_HI.delete(equPosBgqCredentialsList);
			}

			List<EquPosBgqCredentialAttachEntity_HI> equPosBgqCredentialAttachList = equPosBgqCredentialAttachEntity_HI.findByProperty(paramsMap);
			if(equPosBgqCredentialAttachList.size() > 0){
				equPosBgqCredentialAttachEntity_HI.delete(equPosBgqCredentialAttachList);
			}

			List<EquPosBgqAddressesEntity_HI> equPosBgqAddressesList = equPosBgqAddressesEntity_HI.findByProperty(paramsMap);
			if(equPosBgqAddressesList.size() > 0){
				equPosBgqAddressesEntity_HI.delete(equPosBgqAddressesList);
			}

			List<EquPosBgqCapacityEntity_HI> equPosBgqCapacityList = equPosBgqCapacityEntity_HI.findByProperty(paramsMap);
			if(equPosBgqCapacityList.size() > 0){
				equPosBgqCapacityEntity_HI.delete(equPosBgqCapacityList);
			}

			List<EquPosBgqOperationStatusEntity_HI> equPosBgqOperationStatusList = equPosBgqOperationStatusEntity_HI.findByProperty(paramsMap);
			if(equPosBgqOperationStatusList.size() > 0){
				equPosBgqOperationStatusEntity_HI.delete(equPosBgqOperationStatusList);
			}

			List<EquPosBgqProductionInfoEntity_HI> equPosBgqProductionInfoList = equPosBgqProductionInfoEntity_HI.findByProperty(paramsMap);
			if(equPosBgqProductionInfoList.size() > 0){
				equPosBgqProductionInfoEntity_HI.delete(equPosBgqProductionInfoList);
			}




			List<EquPosBgSupplierEntity_HI> equPosBgSupplierList = bghSupplierDao.findByProperty(paramsMap);
			if(equPosBgSupplierList.size() > 0){
				bghSupplierDao.delete(equPosBgSupplierList);
			}

			List<EquPosBgSuppDeptInfoEntity_HI> equPosBgSuppDeptInfoList = equPosBgSuppDeptInfoDAO_HI.findByProperty(paramsMap);
			if(equPosBgSuppDeptInfoList.size() > 0){
				equPosBgSuppDeptInfoDAO_HI.delete(equPosBgSuppDeptInfoList);
			}

			List<EquPosBgAssociateSuppEntity_HI> equPosBgAssociateSuppList = equPosBgAssociateSuppEntity_HI.findByProperty(paramsMap);
			if(equPosBgAssociateSuppList.size() > 0){
				equPosBgAssociateSuppEntity_HI.delete(equPosBgAssociateSuppList);
			}

			List<EquPosBgCategoryEntity_HI> equPosBgCategoryList = equPosBgCategoryEntity_HI.findByProperty(paramsMap);
			if(equPosBgCategoryList.size() > 0){
				equPosBgCategoryEntity_HI.delete(equPosBgCategoryList);
			}

			List<EquPosBgContactsEntity_HI> equPosBgContactsList = equPosBgContactsEntity_HI.findByProperty(paramsMap);
			if(equPosBgContactsList.size() > 0){
				equPosBgContactsEntity_HI.delete(equPosBgContactsList);
			}

			List<EquPosBgBankEntity_HI> equPosBgBankList = equPosBgBankEntity_HI.findByProperty(paramsMap);
			if(equPosBgBankList.size() > 0){
				equPosBgBankEntity_HI.delete(equPosBgBankList);
			}

			List<EquPosBgCredentialsEntity_HI> equPosBgCredentialsList = equPosBgCredentialsEntity_HI.findByProperty(paramsMap);
			if(equPosBgCredentialsList.size() > 0){
				equPosBgCredentialsEntity_HI.delete(equPosBgCredentialsList);
			}

			List<EquPosBgCredentialAttachEntity_HI> equPosBgCredentialAttachList = equPosBgCredentialAttachEntity_HI.findByProperty(paramsMap);
			if(equPosBgCredentialAttachList.size() > 0){
				equPosBgCredentialAttachEntity_HI.delete(equPosBgCredentialAttachList);
			}

			List<EquPosBgAddressesEntity_HI> equPosBgAddressesList = equPosBgAddressesEntity_HI.findByProperty(paramsMap);
			if(equPosBgAddressesList.size() > 0){
				equPosBgAddressesEntity_HI.delete(equPosBgAddressesList);
			}

			List<EquPosBgCapacityEntity_HI> equPosBgCapacityList = equPosBgCapacityEntity_HI.findByProperty(paramsMap);
			if(equPosBgCapacityList.size() > 0){
				equPosBgCapacityEntity_HI.delete(equPosBgCapacityList);
			}

			List<EquPosBgOperationStatusEntity_HI> equPosBgOperationStatusList = equPosBgOperationStatusEntity_HI.findByProperty(paramsMap);
			if(equPosBgOperationStatusList.size() > 0){
				equPosBgOperationStatusEntity_HI.delete(equPosBgOperationStatusList);
			}

			List<EquPosBgProductionInfoEntity_HI> equPosBgProductionInfoList = equPosBgProductionInfoEntity_HI.findByProperty(paramsMap);
			if(equPosBgProductionInfoList.size() > 0){
				equPosBgProductionInfoEntity_HI.delete(equPosBgProductionInfoList);
			}
		}


		//保存供应商档案变更单据
		if(archivesChangeInfo.containsKey("changeId")){
			//修改保存
			archivesChangeEntity = this.saveOrUpdate(archivesChangeInfo);
		}else{
			//新增保存
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String currentDate = format.format(new Date());
			String prefix = "DABG-" + currentDate;
			String sequenceId = generateCodeServer.getSequenceId(prefix,4);
			String changeNumber = prefix + "-" + sequenceId;
			archivesChangeInfo.put("changeNumber", changeNumber);
			archivesChangeInfo.put("status","10");
			archivesChangeEntity = this.saveOrUpdate(archivesChangeInfo);
		}
		//保存变更前后的内容，当变更单据修改时，不需要保存变更前数据，如果时新增，就需要保存变更前数据
		//if(!archivesChangeInfo.containsKey("changeId")){
		if(!archivesChangeInfo.containsKey("changeId") || archivesChangeInfo.containsKey("supplierChangeFlag")){
			//变更前-供应商基础信息
			JSONObject bgqParams = params.getJSONObject("bgqParams");
			JSONObject bgqSupplierBaseInfo = getParamsJSONObject(params,bgqParams.getJSONObject("supplierBaseInfo"));
            String companyDescriptionBgq = bgqSupplierBaseInfo.getString("companyDescription");
            Clob clob = null;
            if (StringUtils.isNotEmpty(companyDescriptionBgq)) {
                clob = new javax.sql.rowset.serial.SerialClob(companyDescriptionBgq.toCharArray());
                bgqSupplierBaseInfo.remove("companyDescription");
            }
            JSONArray relatedFactoryInfoArray = bgqParams.getJSONArray("relatedFactoryInfo") == null ? new JSONArray():bgqParams.getJSONArray("relatedFactoryInfo");
			JSONArray copCategoryInfoArray = bgqParams.getJSONArray("copCategoryInfo") == null ? new JSONArray():bgqParams.getJSONArray("copCategoryInfo");
			JSONArray serviceScopeInfoArray = bgqParams.getJSONArray("serviceScopeInfo") == null ? new JSONArray():bgqParams.getJSONArray("serviceScopeInfo");
			JSONArray supplierContactInfoArray = bgqParams.getJSONArray("supplierContactInfo") == null ? new JSONArray():bgqParams.getJSONArray("supplierContactInfo");
			JSONObject supplierCredentialsInfo = getParamsJSONObject(params,bgqParams.getJSONObject("supplierCredentialsInfo") == null ? new JSONObject() : bgqParams.getJSONObject("supplierCredentialsInfo"));
			JSONArray supplierBankInfoArray = bgqParams.getJSONArray("supplierBankInfo") == null ? new JSONArray():bgqParams.getJSONArray("supplierBankInfo");
			JSONArray productQualificationFileInfoArray = bgqParams.getJSONArray("productQualificationFileInfo") == null ? new JSONArray():bgqParams.getJSONArray("productQualificationFileInfo");
			JSONArray qualificationsFileInfoArray = bgqParams.getJSONArray("qualificationsFileInfo") == null ? new JSONArray():bgqParams.getJSONArray("qualificationsFileInfo");
			JSONArray oemAddressInfoArray = bgqParams.getJSONArray("oemAddressInfo") == null ? new JSONArray():bgqParams.getJSONArray("oemAddressInfo");
			JSONArray itAddressInfoArray = bgqParams.getJSONArray("itAddressInfo") == null ? new JSONArray():bgqParams.getJSONArray("itAddressInfo");

			bgqSupplierBaseInfo.put("changeId",archivesChangeEntity.getChangeId());
            EquPosBgqSupplierEntity_HI bgqSupplierEntity = equPosBgqSupplierServer.saveOrUpdate(bgqSupplierBaseInfo);
            // 更新公司简介
            bgqSupplierEntity.setCompanyDescription(clob);
            bgqSupplierDao.update(bgqSupplierEntity);

			//变更前-供应商部门/类型/状态信息
			queryMap.clear();
			queryMap.put("deptCode",archivesChangeEntity.getDeptCode());
			queryMap.put("changeId",archivesChangeEntity.getChangeId());
			List<EquPosBgqSuppDeptInfoEntity_HI> deptEntityList =equPosBgqSuppDeptInfoDAO_HI.findByProperty(queryMap);
			if(deptEntityList.size() == 0){
				EquPosBgqSuppDeptInfoEntity_HI entity = new EquPosBgqSuppDeptInfoEntity_HI();
				entity.setId(bgqSupplierBaseInfo.getInteger("id"));
				entity.setSupplierId(archivesChangeEntity.getSupplierId());
				entity.setSupplierType(bgqSupplierBaseInfo.getString("supplierType"));
				entity.setDeptCode(archivesChangeEntity.getDeptCode());
				entity.setSupplierStatus(bgqSupplierBaseInfo.getString("supplierStatus"));
				entity.setSupplierFileId(bgqSupplierBaseInfo.getInteger("supplierFileId"));
				entity.setFileName(bgqSupplierBaseInfo.getString("fileName"));
				entity.setFilePath(bgqSupplierBaseInfo.getString("filePath"));
				entity.setMajorCustomer(bgqSupplierBaseInfo.getString("majorCustomer"));
				entity.setAgentLevel(bgqSupplierBaseInfo.getString("agentLevel"));
				entity.setCooperativeProject(bgqSupplierBaseInfo.getString("cooperativeProject"));
				entity.setSupplierInChargeNumber(bgqSupplierBaseInfo.getString("supplierInChargeNumber"));
				entity.setSupplierInChargeName(bgqSupplierBaseInfo.getString("supplierInChargeName"));
				entity.setCompanyDescription(clob);
				entity.setChangeId(archivesChangeEntity.getChangeId());
				entity.setOperatorUserId(archivesChangeEntity.getLastUpdatedBy());
				equPosBgqSuppDeptInfoDAO_HI.saveEntity(entity);
			}

			//变更前-保存关联工厂信息
			for(int i = 0; i < relatedFactoryInfoArray.size(); i++){
				JSONObject relatedFactoryInfo = getParamsJSONObject(params,relatedFactoryInfoArray.getJSONObject(i));
				relatedFactoryInfo.put("changeId",archivesChangeEntity.getChangeId());
				relatedFactoryInfo.put("supplierId",archivesChangeEntity.getSupplierId());
				equPosBgqAssociateSuppServer.saveOrUpdate(relatedFactoryInfo);
			}

//			for(int i = 0; i < relatedFactoryInfoArray.size(); i++){
//				JSONObject relatedFactoryInfo = getParamsJSONObject(params,relatedFactoryInfoArray.getJSONObject(i));
//				if(relatedFactoryInfo.containsKey("associatedId")){
//					//修改保存
//					equPosZzscAssociateSuppServer.saveOrUpdate(relatedFactoryInfo);
//				}else{
//					//新增保存
//					relatedFactoryInfo.put("supplierId",supplierEntity.getSupplierId());
//					equPosZzscAssociateSuppServer.saveOrUpdate(relatedFactoryInfo);
//				}
//			}

			//变更前-保存可合作品类信息
			for(int i = 0; i < copCategoryInfoArray.size(); i++){
				JSONObject copCategoryInfo = getParamsJSONObject(params,copCategoryInfoArray.getJSONObject(i));
				copCategoryInfo.put("changeId",archivesChangeEntity.getChangeId());
				copCategoryInfo.put("supplierId",archivesChangeEntity.getSupplierId());
				equPosBgqCategoryServer.saveOrUpdate(copCategoryInfo);
			}

			//变更前-保存服务范围信息
			for(int i = 0; i < serviceScopeInfoArray.size(); i++){
				JSONObject serviceScopeInfo = getParamsJSONObject(params,serviceScopeInfoArray.getJSONObject(i));
				serviceScopeInfo.put("changeId",archivesChangeEntity.getChangeId());
				equPosBgqCategoryServer.saveOrUpdate(serviceScopeInfo);
			}

			//变更前-保存联系人信息
			for(int i = 0; i < supplierContactInfoArray.size(); i++){
				JSONObject supplierContactInfo = getParamsJSONObject(params,supplierContactInfoArray.getJSONObject(i));
				supplierContactInfo.put("changeId",archivesChangeEntity.getChangeId());
				supplierContactInfo.put("supplierId",archivesChangeEntity.getSupplierId());
				equPosBgqContactsServer.saveOrUpdate(supplierContactInfo);
			}

			//变更前-供应商资质信息
			supplierCredentialsInfo.put("changeId",archivesChangeEntity.getChangeId());
			supplierCredentialsInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			equPosBgqCredentialsServer.saveOrUpdate(supplierCredentialsInfo);

			//变更前-保存银行信息
			for(int i = 0; i < supplierBankInfoArray.size(); i++){
				JSONObject supplierBankInfo = getParamsJSONObject(params,supplierBankInfoArray.getJSONObject(i));
				supplierBankInfo.put("changeId",archivesChangeEntity.getChangeId());
				supplierBankInfo.put("supplierId",archivesChangeEntity.getSupplierId());
				equPosBgqBankServer.saveOrUpdate(supplierBankInfo);
			}

			//变更前-保存生产资质文件信息
			for(int i = 0; i < productQualificationFileInfoArray.size(); i++){
				JSONObject productQualificationsInfo = getParamsJSONObject(params,productQualificationFileInfoArray.getJSONObject(i));
				productQualificationsInfo.put("changeId",archivesChangeEntity.getChangeId());
				productQualificationsInfo.put("supplierId",archivesChangeEntity.getSupplierId());
				equPosBgqCredentialAttachServer.saveOrUpdate(productQualificationsInfo);
			}

			//变更前-保存资质文件信息
			for(int i = 0; i < qualificationsFileInfoArray.size(); i++){
				JSONObject qualificationsFileInfo = getParamsJSONObject(params,qualificationsFileInfoArray.getJSONObject(i));
				qualificationsFileInfo.put("changeId",archivesChangeEntity.getChangeId());
				qualificationsFileInfo.put("supplierId",archivesChangeEntity.getSupplierId());
				equPosBgqCredentialAttachServer.saveOrUpdate(qualificationsFileInfo);
			}

			//变更前-保存OEM地址信息
			for(int i = 0; i < oemAddressInfoArray.size(); i++){
				JSONObject oemAddressInfo= oemAddressInfoArray.getJSONObject(i);
				JSONObject oemProductionInfo = oemAddressInfo.getJSONObject("oemProductionInfoParams");
				JSONArray oemCapacityInfoArray = oemAddressInfo.getJSONArray("oemCapacityInfoParams");
				oemAddressInfo.remove("oemProductionInfoParams");
				oemAddressInfo.remove("oemCapacityInfoParams");

				oemAddressInfo = getParamsJSONObject(params,oemAddressInfo);
//				oemProductionInfo = getParamsJSONObject(params,oemProductionInfo);

				//1.保存地址信息
				oemAddressInfo.put("changeId",archivesChangeEntity.getChangeId());
				oemAddressInfo.put("supplierId",archivesChangeEntity.getSupplierId());
				EquPosBgqAddressesEntity_HI bgqAddressEntity = equPosBgqAddressesServer.saveOrUpdate(oemAddressInfo);

				//2.保存生产信息
				if(null != oemProductionInfo){
					oemProductionInfo = getParamsJSONObject(params,oemProductionInfo);
					oemProductionInfo.put("changeId",archivesChangeEntity.getChangeId());
					oemProductionInfo.put("supplierId",archivesChangeEntity.getSupplierId());
					equPosBgqProductionInfoServer.saveOrUpdate(oemProductionInfo);
				}

				//3.保存产能信息
				if(null != oemCapacityInfoArray){
					for(int j = 0; j < oemCapacityInfoArray.size(); j++){
						JSONObject oemCapacityInfo = getParamsJSONObject(params,oemCapacityInfoArray.getJSONObject(j));
						oemCapacityInfo.put("changeId",archivesChangeEntity.getChangeId());
						oemCapacityInfo.put("supplierId",archivesChangeEntity.getSupplierId());
						equPosBgqCapacityServer.saveOrUpdate(oemCapacityInfo);
					}
				}
			}

			//变更前-保存IT地址信息
			for(int i = 0; i < itAddressInfoArray.size(); i++){
				JSONObject itAddressInfo= itAddressInfoArray.getJSONObject(i);
//				JSONObject itOperationalInfoParams = itAddressInfo.getJSONObject("itOperationalInfoParams");
//				JSONArray itCapacityInfoArray = itAddressInfo.getJSONArray("itCapacityInfoParams");

				JSONObject itOperationalInfoParams = itAddressInfo.getJSONObject("itOperationalInfoParams") == null ? new JSONObject() : itAddressInfo.getJSONObject("itOperationalInfoParams");
				JSONArray itCapacityInfoArray = itAddressInfo.getJSONArray("itCapacityInfoParams") == null ? new JSONArray() : itAddressInfo.getJSONArray("itCapacityInfoParams");
				JSONArray surEnvironmentInfoArray = itAddressInfo.getJSONArray("surEnvironmentDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("surEnvironmentDataTable");
				JSONArray doorPlateInfoArray = itAddressInfo.getJSONArray("doorPlateDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("doorPlateDataTable");
				JSONArray receptionInfoArray = itAddressInfo.getJSONArray("receptionDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("receptionDataTable");
				JSONArray companyAreaInfoArray = itAddressInfo.getJSONArray("companyAreaDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("companyAreaDataTable");
				JSONArray officeSpaceInfoArray = itAddressInfo.getJSONArray("officeSpaceDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("officeSpaceDataTable");
				JSONArray employeeProfileInfoArray = itAddressInfo.getJSONArray("employeeProfileDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("employeeProfileDataTable");

				itAddressInfo.remove("itOperationalInfoParams");
				itAddressInfo.remove("itCapacityInfoParams");
				itAddressInfo.remove("surEnvironmentDataTable");
				itAddressInfo.remove("doorPlateDataTable");
				itAddressInfo.remove("receptionDataTable");
				itAddressInfo.remove("companyAreaDataTable");
				itAddressInfo.remove("officeSpaceDataTable");
				itAddressInfo.remove("employeeProfileDataTable");

				itAddressInfo = getParamsJSONObject(params, itAddressInfo);
				itOperationalInfoParams = getParamsJSONObject(params, itOperationalInfoParams);

				//1.保存地址信息
				itAddressInfo.put("changeId",archivesChangeEntity.getChangeId());
				itAddressInfo.put("supplierId",archivesChangeEntity.getSupplierId());
				EquPosBgqAddressesEntity_HI bgqAddressEntity = equPosBgqAddressesServer.saveOrUpdate(itAddressInfo);
				//2.保存经营状况
				if(null != itOperationalInfoParams){
					itOperationalInfoParams = getParamsJSONObject(params,itOperationalInfoParams);
					itOperationalInfoParams.put("changeId",archivesChangeEntity.getChangeId());
					itOperationalInfoParams.put("supplierId",archivesChangeEntity.getSupplierId());
					itOperationalInfoParams.put("supplierAddressId", bgqAddressEntity.getSupplierAddressId());
					equPosBgqOperationStatusServer.saveOrUpdate(itOperationalInfoParams);
				}
				//3.保存周边环境附件
				for (int j = 0; j < surEnvironmentInfoArray.size(); j++) {
					JSONObject surEnvironmentInfo = getParamsJSONObject(params, surEnvironmentInfoArray.getJSONObject(j));
					surEnvironmentInfo.put("supplierId", archivesChangeEntity.getSupplierId());
					surEnvironmentInfo.put("supplierAddressId", bgqAddressEntity.getSupplierAddressId());
					surEnvironmentInfo.put("changeId",archivesChangeEntity.getChangeId());
					equPosBgqCredentialAttachServer.saveOrUpdate(surEnvironmentInfo);
				}

				//4.保存大门门牌附件
				for (int j = 0; j < doorPlateInfoArray.size(); j++) {
					JSONObject doorPlateInfo = getParamsJSONObject(params, doorPlateInfoArray.getJSONObject(j));
					doorPlateInfo.put("supplierId", archivesChangeEntity.getSupplierId());
					doorPlateInfo.put("supplierAddressId", bgqAddressEntity.getSupplierAddressId());
					doorPlateInfo.put("changeId",archivesChangeEntity.getChangeId());
					equPosBgqCredentialAttachServer.saveOrUpdate(doorPlateInfo);
				}

				//5.保存前台附件
				for (int j = 0; j < receptionInfoArray.size(); j++) {
					JSONObject receptionInfo = getParamsJSONObject(params, receptionInfoArray.getJSONObject(j));
					receptionInfo.put("supplierId", archivesChangeEntity.getSupplierId());
					receptionInfo.put("supplierAddressId", bgqAddressEntity.getSupplierAddressId());
					receptionInfo.put("changeId",archivesChangeEntity.getChangeId());
					equPosBgqCredentialAttachServer.saveOrUpdate(receptionInfo);
				}

				//6.保存公司面积附件
				for (int j = 0; j < companyAreaInfoArray.size(); j++) {
					JSONObject companyAreaInfo = getParamsJSONObject(params, companyAreaInfoArray.getJSONObject(j));
					companyAreaInfo.put("supplierId", archivesChangeEntity.getSupplierId());
					companyAreaInfo.put("supplierAddressId", bgqAddressEntity.getSupplierAddressId());
					companyAreaInfo.put("changeId",archivesChangeEntity.getChangeId());
					equPosBgqCredentialAttachServer.saveOrUpdate(companyAreaInfo);
				}

				//7.保存办公场所附件
				for (int j = 0; j < officeSpaceInfoArray.size(); j++) {
					JSONObject officeSpaceInfo = getParamsJSONObject(params, officeSpaceInfoArray.getJSONObject(j));
					officeSpaceInfo.put("supplierId", archivesChangeEntity.getSupplierId());
					officeSpaceInfo.put("supplierAddressId", bgqAddressEntity.getSupplierAddressId());
					officeSpaceInfo.put("changeId",archivesChangeEntity.getChangeId());
					equPosBgqCredentialAttachServer.saveOrUpdate(officeSpaceInfo);
				}

				//8.保存员工概况附件
				for (int j = 0; j < employeeProfileInfoArray.size(); j++) {
					JSONObject employeeProfileInfo = getParamsJSONObject(params, employeeProfileInfoArray.getJSONObject(j));
					employeeProfileInfo.put("supplierId", archivesChangeEntity.getSupplierId());
					employeeProfileInfo.put("supplierAddressId", bgqAddressEntity.getSupplierAddressId());
					employeeProfileInfo.put("changeId",archivesChangeEntity.getChangeId());
					equPosBgqCredentialAttachServer.saveOrUpdate(employeeProfileInfo);
				}

				//9.保存产能信息
				for (int j = 0; j < itCapacityInfoArray.size(); j++) {
					JSONObject itCapacityInfo = getParamsJSONObject(params, itCapacityInfoArray.getJSONObject(j));
					itCapacityInfo.put("supplierId", archivesChangeEntity.getSupplierId());
					itCapacityInfo.put("supplierAddressId", bgqAddressEntity.getSupplierAddressId());
					itCapacityInfo.put("changeId",archivesChangeEntity.getChangeId());
					equPosBgqCapacityServer.saveOrUpdate(itCapacityInfo);
				}
			}
		}

		//变更后-信息保存============================================================================》》
		JSONObject bghParams = params.getJSONObject("bghParams");
		JSONObject bghSupplierBaseInfo = getParamsJSONObject(params,bghParams.getJSONObject("supplierBaseInfo"));
        String companyDescriptionBgh = bghSupplierBaseInfo.getString("companyDescription");
        Clob clobBgh = null;
        if (StringUtils.isNotEmpty(companyDescriptionBgh)) {
            clobBgh = new javax.sql.rowset.serial.SerialClob(companyDescriptionBgh.toCharArray());
            bghSupplierBaseInfo.remove("companyDescription");
        }
		JSONArray copCategoryInfoArray = bghParams.getJSONArray("copCategoryInfo") == null ? new JSONArray():bghParams.getJSONArray("copCategoryInfo");
		JSONArray serviceScopeInfoArray = bghParams.getJSONArray("serviceScopeInfo") == null ? new JSONArray():bghParams.getJSONArray("serviceScopeInfo");
		JSONArray relatedFactoryInfoArray = bghParams.getJSONArray("relatedFactoryInfo") == null ? new JSONArray():bghParams.getJSONArray("relatedFactoryInfo");
		JSONArray supplierContactInfoArray = bghParams.getJSONArray("supplierContactInfo") == null ? new JSONArray():bghParams.getJSONArray("supplierContactInfo");
		JSONObject supplierCredentialsInfo = getParamsJSONObject(params,bghParams.getJSONObject("supplierCredentialsInfo") == null ? new JSONObject() : bghParams.getJSONObject("supplierCredentialsInfo"));
		JSONArray supplierBankInfoArray = bghParams.getJSONArray("supplierBankInfo") == null ? new JSONArray():bghParams.getJSONArray("supplierBankInfo");
		JSONArray productQualificationFileInfoArray = bghParams.getJSONArray("productQualificationFileInfo") == null ? new JSONArray():bghParams.getJSONArray("productQualificationFileInfo");
		JSONArray qualificationsFileInfoArray = bghParams.getJSONArray("qualificationsFileInfo") == null ? new JSONArray():bghParams.getJSONArray("qualificationsFileInfo");
		JSONArray oemAddressInfoArray = bghParams.getJSONArray("oemAddressInfo") == null ? new JSONArray():bghParams.getJSONArray("oemAddressInfo");
		JSONArray itAddressInfoArray = bghParams.getJSONArray("itAddressInfo") == null ? new JSONArray():bghParams.getJSONArray("itAddressInfo");

		//校验供应商名称是否已存在
		String supplierName = bghSupplierBaseInfo.getString("supplierName");
		Integer id = bghSupplierBaseInfo.getInteger("supplierId") == null ? -999 : bghSupplierBaseInfo.getInteger("supplierId");
		Map supplierQueryMap = new HashMap();
		supplierQueryMap.put("supplierName",supplierName);
		List<EquPosSupplierInfoEntity_HI> resultList = equPosSupplierInfoEntity_HI.findByProperty(supplierQueryMap);
		for(int i = 0; i < resultList.size(); i++){
			EquPosSupplierInfoEntity_HI r = resultList.get(i);
			if(r.getSupplierId().intValue() != id.intValue()){
				throw new Exception("供应商名称已存在!");
			}
		}

		//变更后-供应商基础信息
		bghSupplierBaseInfo.put("changeId",archivesChangeEntity.getChangeId());
		bghSupplierBaseInfo.put("supplierId",archivesChangeEntity.getSupplierId());
        EquPosBgSupplierEntity_HI bghSupplierEntity = equPosBgSupplierServer.saveOrUpdate(bghSupplierBaseInfo);
        // 更新公司简介
        bghSupplierEntity.setCompanyDescription(clobBgh);
        bghSupplierDao.update(bghSupplierEntity);


        //变更后-供应商部门/类型/状态信息
		queryMap.clear();
		queryMap.put("deptCode",archivesChangeEntity.getDeptCode());
		queryMap.put("changeId",archivesChangeEntity.getChangeId());
		queryMap.put("supplierId",archivesChangeEntity.getSupplierId());
		List<EquPosBgSuppDeptInfoEntity_HI> deptEntityList =equPosBgSuppDeptInfoDAO_HI.findByProperty(queryMap);
		if(deptEntityList.size() > 0){
			EquPosBgSuppDeptInfoEntity_HI entity = deptEntityList.get(0);
			entity.setSupplierType(bghSupplierBaseInfo.getString("supplierType"));
			entity.setSupplierFileId(bghSupplierBaseInfo.getInteger("supplierFileId"));
			entity.setFileName(bghSupplierBaseInfo.getString("fileName"));
			entity.setFilePath(bghSupplierBaseInfo.getString("filePath"));
			entity.setMajorCustomer(bghSupplierBaseInfo.getString("majorCustomer"));
			entity.setCompanyDescription(clobBgh);
			entity.setAgentLevel(bghSupplierBaseInfo.getString("agentLevel"));
			entity.setCooperativeProject(bghSupplierBaseInfo.getString("cooperativeProject"));
			entity.setSupplierInChargeNumber(bghSupplierBaseInfo.getString("supplierInChargeNumber"));
			entity.setSupplierInChargeName(bghSupplierBaseInfo.getString("supplierInChargeName"));
			entity.setOperatorUserId(archivesChangeEntity.getLastUpdatedBy());
			equPosBgSuppDeptInfoDAO_HI.saveEntity(entity);
		}else{
			EquPosBgSuppDeptInfoEntity_HI entity = new EquPosBgSuppDeptInfoEntity_HI();
			entity.setId(bghSupplierBaseInfo.getInteger("id"));
			entity.setSupplierId(archivesChangeEntity.getSupplierId());
			entity.setSupplierType(bghSupplierBaseInfo.getString("supplierType"));
			entity.setDeptCode(archivesChangeEntity.getDeptCode());
			entity.setSupplierStatus(bghSupplierBaseInfo.getString("supplierStatus"));
			entity.setChangeId(archivesChangeEntity.getChangeId());
			entity.setSupplierFileId(bghSupplierBaseInfo.getInteger("supplierFileId"));
			entity.setFileName(bghSupplierBaseInfo.getString("fileName"));
			entity.setFilePath(bghSupplierBaseInfo.getString("filePath"));
			entity.setMajorCustomer(bghSupplierBaseInfo.getString("majorCustomer"));
			entity.setCompanyDescription(clobBgh);
			entity.setAgentLevel(bghSupplierBaseInfo.getString("agentLevel"));
			entity.setCooperativeProject(bghSupplierBaseInfo.getString("cooperativeProject"));
			entity.setSupplierInChargeNumber(bghSupplierBaseInfo.getString("supplierInChargeNumber"));
			entity.setSupplierInChargeName(bghSupplierBaseInfo.getString("supplierInChargeName"));
			entity.setOperatorUserId(archivesChangeEntity.getLastUpdatedBy());
			equPosBgSuppDeptInfoDAO_HI.saveEntity(entity);
		}

		//变更前-保存关联工厂信息
		for(int i = 0; i < relatedFactoryInfoArray.size(); i++){
			JSONObject relatedFactoryInfo = getParamsJSONObject(params,relatedFactoryInfoArray.getJSONObject(i));
			relatedFactoryInfo.put("changeId",archivesChangeEntity.getChangeId());
			relatedFactoryInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			equPosBgAssociateSuppServer.saveOrUpdate(relatedFactoryInfo);
		}

		//变更后-保存可合作品类信息
		for(int i = 0; i < copCategoryInfoArray.size(); i++){
			JSONObject copCategoryInfo = getParamsJSONObject(params,copCategoryInfoArray.getJSONObject(i));
			copCategoryInfo.put("changeId",archivesChangeEntity.getChangeId());
			copCategoryInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			equPosBgCategoryServer.saveOrUpdate(copCategoryInfo);
		}

		//变更后-保存服务范围信息
		for(int i = 0; i < serviceScopeInfoArray.size(); i++){
			JSONObject serviceScopeInfo = getParamsJSONObject(params,serviceScopeInfoArray.getJSONObject(i));
			serviceScopeInfo.put("changeId",archivesChangeEntity.getChangeId());
		    serviceScopeInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			equPosBgCategoryServer.saveOrUpdate(serviceScopeInfo);
		}

		//变更后-保存联系人信息
		for(int i = 0; i < supplierContactInfoArray.size(); i++){
			JSONObject supplierContactInfo = getParamsJSONObject(params,supplierContactInfoArray.getJSONObject(i));
			supplierContactInfo.put("changeId",archivesChangeEntity.getChangeId());
			supplierContactInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			equPosBgContactsServer.saveOrUpdate(supplierContactInfo);
		}

		//变更后-供应商资质信息
		supplierCredentialsInfo.put("changeId",archivesChangeEntity.getChangeId());
		supplierCredentialsInfo.put("supplierId",archivesChangeEntity.getSupplierId());
		equPosBgCredentialsServer.saveOrUpdate(supplierCredentialsInfo);

		//变更后-保存银行信息
		for(int i = 0; i < supplierBankInfoArray.size(); i++){
			JSONObject supplierBankInfo = getParamsJSONObject(params,supplierBankInfoArray.getJSONObject(i));
			supplierBankInfo.put("changeId",archivesChangeEntity.getChangeId());
			supplierBankInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			equPosBgBankServer.saveOrUpdate(supplierBankInfo);
		}

		//变更后-保存生产资质文件信息
		for(int i = 0; i < productQualificationFileInfoArray.size(); i++){
			JSONObject productQualificationsInfo = getParamsJSONObject(params,productQualificationFileInfoArray.getJSONObject(i));
			productQualificationsInfo.put("changeId",archivesChangeEntity.getChangeId());
			productQualificationsInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			equPosBgCredentialAttachServer.saveOrUpdate(productQualificationsInfo);
		}

		//变更后-保存资质文件信息
		for(int i = 0; i < qualificationsFileInfoArray.size(); i++){
			JSONObject qualificationsFileInfo = getParamsJSONObject(params,qualificationsFileInfoArray.getJSONObject(i));
			qualificationsFileInfo.put("changeId",archivesChangeEntity.getChangeId());
			qualificationsFileInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			equPosBgCredentialAttachServer.saveOrUpdate(qualificationsFileInfo);
		}

		//变更后-保存OEM地址信息
		for(int i = 0; i < oemAddressInfoArray.size(); i++){
			JSONObject oemAddressInfo= oemAddressInfoArray.getJSONObject(i);
//			JSONObject oemProductionInfo = oemAddressInfo.getJSONObject("oemProductionInfoParams");
//			JSONArray oemCapacityInfoArray = oemAddressInfo.getJSONArray("oemCapacityInfoBghDataTable");
//			oemAddressInfo.remove("oemProductionInfoParams");
//			oemAddressInfo.remove("oemCapacityInfoParams");
//			JSONArray oemCapacityInfoArray = oemAddressInfo.getJSONArray("oemCapacityInfoParams");
//			JSONObject oemProductionInfo = oemAddressInfo.getJSONObject("oemProductionInfoBghParams");
//			oemAddressInfo.remove("oemProductionInfoBghParams");
//			oemAddressInfo.remove("oemCapacityInfoParams");

			JSONObject oemProductionInfo = null;
			JSONArray oemCapacityInfoArray = null;
			if(oemAddressInfo.containsKey("oemProductionInfoParams")){
				oemProductionInfo = oemAddressInfo.getJSONObject("oemProductionInfoParams");
				oemAddressInfo.remove("oemProductionInfoParams");
			}
			if(oemAddressInfo.containsKey("oemCapacityInfoBghDataTable")){
				oemCapacityInfoArray = oemAddressInfo.getJSONArray("oemCapacityInfoBghDataTable");
				oemAddressInfo.remove("oemCapacityInfoBghDataTable");
			}
			if(oemAddressInfo.containsKey("oemCapacityInfoParams")){
				oemCapacityInfoArray = oemAddressInfo.getJSONArray("oemCapacityInfoParams");
				oemAddressInfo.remove("oemCapacityInfoParams");
			}
			if(oemAddressInfo.containsKey("oemProductionInfoBghParams")){
				oemProductionInfo = oemAddressInfo.getJSONObject("oemProductionInfoBghParams");
				oemAddressInfo.remove("oemProductionInfoBghParams");
			}

			oemAddressInfo = getParamsJSONObject(params,oemAddressInfo);
//			oemProductionInfo = getParamsJSONObject(params,oemProductionInfo);

			//1.保存地址信息
			oemAddressInfo.put("changeId",archivesChangeEntity.getChangeId());
			oemAddressInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			EquPosBgAddressesEntity_HI bgAddressEntity = equPosBgAddressesServer.saveOrUpdate(oemAddressInfo);


			//2.保存生产信息
			if(null != oemProductionInfo){
				oemProductionInfo = getParamsJSONObject(params,oemProductionInfo);
				oemProductionInfo.put("changeId",archivesChangeEntity.getChangeId());
				oemProductionInfo.put("supplierId",archivesChangeEntity.getSupplierId());
				if(!oemProductionInfo.containsKey("supplierAddressId")){
					oemProductionInfo.put("supplierAddressId",bgAddressEntity.getSourceId());
				}
				equPosBgProductionInfoServer.saveOrUpdate(oemProductionInfo);
			}

			//3.保存产能信息
			if(null != oemCapacityInfoArray){
				for(int j = 0; j < oemCapacityInfoArray.size(); j++){
					JSONObject oemCapacityInfo = getParamsJSONObject(params,oemCapacityInfoArray.getJSONObject(j));
					oemCapacityInfo.put("changeId",archivesChangeEntity.getChangeId());
					oemCapacityInfo.put("supplierId",archivesChangeEntity.getSupplierId());
					if(!oemCapacityInfo.containsKey("supplierAddressId")){
						oemCapacityInfo.put("supplierAddressId",bgAddressEntity.getSupplierAddressId() == null ? bgAddressEntity.getSourceId() : bgAddressEntity.getSupplierAddressId());
					}
					equPosBgCapacityServer.saveOrUpdate(oemCapacityInfo);
				}
			}
		}

		//变更后-保存IT地址信息
		for(int i = 0; i < itAddressInfoArray.size(); i++){
			JSONObject itAddressInfo= itAddressInfoArray.getJSONObject(i);
//				JSONObject itOperationalInfoParams = itAddressInfo.getJSONObject("itOperationalInfoParams");
//				JSONArray itCapacityInfoArray = itAddressInfo.getJSONArray("itCapacityInfoParams");

			JSONObject itOperationalInfoParams = itAddressInfo.getJSONObject("itOperationalInfoParams");
			JSONArray itCapacityInfoArray = itAddressInfo.getJSONArray("itCapacityInfoParams") == null ? new JSONArray() : itAddressInfo.getJSONArray("itCapacityInfoParams");
			JSONArray surEnvironmentInfoArray = itAddressInfo.getJSONArray("surEnvironmentDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("surEnvironmentDataTable");
			JSONArray doorPlateInfoArray = itAddressInfo.getJSONArray("doorPlateDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("doorPlateDataTable");
			JSONArray receptionInfoArray = itAddressInfo.getJSONArray("receptionDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("receptionDataTable");
			JSONArray companyAreaInfoArray = itAddressInfo.getJSONArray("companyAreaDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("companyAreaDataTable");
			JSONArray officeSpaceInfoArray = itAddressInfo.getJSONArray("officeSpaceDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("officeSpaceDataTable");
			JSONArray employeeProfileInfoArray = itAddressInfo.getJSONArray("employeeProfileDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("employeeProfileDataTable");

			itAddressInfo.remove("itOperationalInfoParams");
			itAddressInfo.remove("itCapacityInfoParams");
			itAddressInfo.remove("surEnvironmentDataTable");
			itAddressInfo.remove("doorPlateDataTable");
			itAddressInfo.remove("receptionDataTable");
			itAddressInfo.remove("companyAreaDataTable");
			itAddressInfo.remove("officeSpaceDataTable");
			itAddressInfo.remove("employeeProfileDataTable");

			itAddressInfo = getParamsJSONObject(params, itAddressInfo);
			itOperationalInfoParams = getParamsJSONObject(params, itOperationalInfoParams);

			//1.保存地址信息
			itAddressInfo.put("changeId",archivesChangeEntity.getChangeId());
			itAddressInfo.put("supplierId",archivesChangeEntity.getSupplierId());
			EquPosBgAddressesEntity_HI bgAddressEntity = equPosBgAddressesServer.saveOrUpdate(itAddressInfo);
			//2.保存经营状况
			if(null != itOperationalInfoParams){
				itOperationalInfoParams = getParamsJSONObject(params,itOperationalInfoParams);
				itOperationalInfoParams.put("changeId",archivesChangeEntity.getChangeId());
				itOperationalInfoParams.put("supplierId",archivesChangeEntity.getSupplierId());
				itOperationalInfoParams.put("supplierAddressId", bgAddressEntity.getSupplierAddressId());
				equPosBgOperationStatusServer.saveOrUpdate(itOperationalInfoParams);
			}
			//3.保存周边环境附件
			for (int j = 0; j < surEnvironmentInfoArray.size(); j++) {
				JSONObject surEnvironmentInfo = getParamsJSONObject(params, surEnvironmentInfoArray.getJSONObject(j));
				surEnvironmentInfo.put("changeId",archivesChangeEntity.getChangeId());
				surEnvironmentInfo.put("supplierId", archivesChangeEntity.getSupplierId());
				surEnvironmentInfo.put("supplierAddressId", bgAddressEntity.getSupplierAddressId());
				equPosBgCredentialAttachServer.saveOrUpdate(surEnvironmentInfo);
			}

			//4.保存大门门牌附件
			for (int j = 0; j < doorPlateInfoArray.size(); j++) {
				JSONObject doorPlateInfo = getParamsJSONObject(params, doorPlateInfoArray.getJSONObject(j));
				doorPlateInfo.put("changeId",archivesChangeEntity.getChangeId());
				doorPlateInfo.put("supplierId", archivesChangeEntity.getSupplierId());
				doorPlateInfo.put("supplierAddressId", bgAddressEntity.getSupplierAddressId());
				equPosBgCredentialAttachServer.saveOrUpdate(doorPlateInfo);
			}

			//5.保存前台附件
			for (int j = 0; j < receptionInfoArray.size(); j++) {
				JSONObject receptionInfo = getParamsJSONObject(params, receptionInfoArray.getJSONObject(j));
				receptionInfo.put("changeId",archivesChangeEntity.getChangeId());
				receptionInfo.put("supplierId", archivesChangeEntity.getSupplierId());
				receptionInfo.put("supplierAddressId", bgAddressEntity.getSupplierAddressId());
				equPosBgCredentialAttachServer.saveOrUpdate(receptionInfo);
			}

			//6.保存公司面积附件
			for (int j = 0; j < companyAreaInfoArray.size(); j++) {
				JSONObject companyAreaInfo = getParamsJSONObject(params, companyAreaInfoArray.getJSONObject(j));
				companyAreaInfo.put("changeId",archivesChangeEntity.getChangeId());
				companyAreaInfo.put("supplierId", archivesChangeEntity.getSupplierId());
				companyAreaInfo.put("supplierAddressId", bgAddressEntity.getSupplierAddressId());
				equPosBgCredentialAttachServer.saveOrUpdate(companyAreaInfo);
			}

			//7.保存办公场所附件
			for (int j = 0; j < officeSpaceInfoArray.size(); j++) {
				JSONObject officeSpaceInfo = getParamsJSONObject(params, officeSpaceInfoArray.getJSONObject(j));
				officeSpaceInfo.put("changeId",archivesChangeEntity.getChangeId());
				officeSpaceInfo.put("supplierId", archivesChangeEntity.getSupplierId());
				officeSpaceInfo.put("supplierAddressId", bgAddressEntity.getSupplierAddressId());
				equPosBgCredentialAttachServer.saveOrUpdate(officeSpaceInfo);
			}

			//8.保存员工概况附件
			for (int j = 0; j < employeeProfileInfoArray.size(); j++) {
				JSONObject employeeProfileInfo = getParamsJSONObject(params, employeeProfileInfoArray.getJSONObject(j));
				employeeProfileInfo.put("changeId",archivesChangeEntity.getChangeId());
				employeeProfileInfo.put("supplierId", archivesChangeEntity.getSupplierId());
				employeeProfileInfo.put("supplierAddressId", bgAddressEntity.getSupplierAddressId());
				equPosBgCredentialAttachServer.saveOrUpdate(employeeProfileInfo);
			}

			//9.保存产能信息
			for (int j = 0; j < itCapacityInfoArray.size(); j++) {
				JSONObject itCapacityInfo = getParamsJSONObject(params, itCapacityInfoArray.getJSONObject(j));
				itCapacityInfo.put("supplierId", archivesChangeEntity.getSupplierId());
				itCapacityInfo.put("supplierAddressId", bgAddressEntity.getSupplierAddressId());
				itCapacityInfo.put("changeId",archivesChangeEntity.getChangeId());
				equPosBgCapacityServer.saveOrUpdate(itCapacityInfo);
			}
		}

		return archivesChangeEntity;
	}

	/**
	 * 供应商档案变更单据-删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public Integer deleteArchivesChangeOrder(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("changeId"));

		//单据删除时，判断单据状态如果为驳回，则查询单据的流程id，返回前端，用于终止流程
		String status = params.getString("status");
		String changeNumber = params.getString("changeNumber");
		if("40".equals(status)){
			//查询流程信息，提取流程id
			JSONObject b = new JSONObject();
			b.put("code", changeNumber);
			JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
			//根据流程id，终止流程
			Integer listId = resultJSON.getInteger("listId");
			return listId;
		}
		return null;
	}

	/**
	 * 解析json参数
	 */
	public JSONObject getParamsJSONObject(JSONObject params,JSONObject targetJsonObject){
		if(params.containsKey("varUserId")){
			targetJsonObject.put("varUserId",params.get("varUserId"));
		}
		if(params.containsKey("username")){
			targetJsonObject.put("username",params.get("username"));
		}
		if(params.containsKey("varSystemCode")){
			targetJsonObject.put("varSystemCode",params.get("varSystemCode"));
		}
		if(params.containsKey("varUserName")){
			targetJsonObject.put("varUserName",params.get("varUserName"));
		}
		if(params.containsKey("varEmployeeNumber")){
			targetJsonObject.put("varEmployeeNumber",params.get("varEmployeeNumber"));
		}
		if(params.containsKey("varUserFullName")){
			targetJsonObject.put("varUserFullName",params.get("varUserFullName"));
		}
		if(params.containsKey("varOrgId")){
			targetJsonObject.put("varOrgId",params.get("varOrgId"));
		}
		if(params.containsKey("varOrgCode")){
			targetJsonObject.put("varOrgCode",params.get("varOrgCode"));
		}
		if(params.containsKey("varLeaderId")){
			targetJsonObject.put("varLeaderId",params.get("varLeaderId"));
		}
		if(params.containsKey("varIsadmin")){
			targetJsonObject.put("varIsadmin",params.get("varIsadmin"));
		}
		if(params.containsKey("varUserType")){
			targetJsonObject.put("varUserType",params.get("varUserType"));
		}
		if(params.containsKey("operationOrgIds")){
			targetJsonObject.put("operationOrgIds",params.get("operationOrgIds"));
		}
		if(params.containsKey("operationOrgId")){
			targetJsonObject.put("operationOrgId",params.get("operationOrgId"));
		}
		if(params.containsKey("operationRespId")){
			targetJsonObject.put("operationRespId",params.get("operationRespId"));
		}
		if(params.containsKey("operatorUserId")){
			targetJsonObject.put("operatorUserId",params.get("operatorUserId"));
		}
		return targetJsonObject;
	}

	/**
	 * 供应商档案变更单据-删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void generateSupplierAccountBench(JSONObject params) throws Exception {
		List<EquPosSupplierContactsEntity_HI> contactList = equPosSupplierContactsEntity_HI.findByProperty("sendEmailFlag","Y");
		String supplierNumber = null;
		String supplierName = null;
		for(int i = 0; i < contactList.size(); i++){
			EquPosSupplierContactsEntity_HI contactEntity = contactList.get(i);
			//查找供应商编码
			List<EquPosSupplierInfoEntity_HI> supplierList = equPosSupplierInfoEntity_HI.findByProperty("supplierId",contactEntity.getSupplierId());
			EquPosSupplierInfoEntity_HI supplierEntity = supplierList.get(0);
			supplierNumber = supplierEntity.getSupplierNumber();
			supplierName = supplierEntity.getSupplierName();

			//创建账号
			JSONObject paramsObj = null;
			paramsObj = new JSONObject();
			if ("Y".equals(contactEntity.getSendEmailFlag()) && (null == contactEntity.getUserId() || "".equals(contactEntity.getUserId()))) {
				String userName = generateCodeServer.generateCode(supplierNumber, 2);
				String pwdStr = CommonUtils.generateRandomPassword(10);
				paramsObj.put("inPassword", pwdStr);
				paramsObj.put("startDate", new Date());
				paramsObj.put("userName", userName);
				paramsObj.put("userType", "60");
				paramsObj.put("password", new String(Base64.encodeBase64(pwdStr.getBytes())));
				paramsObj.put("userFullName", paramsObj.getString("contactName"));
				paramsObj.put("phoneNumber", paramsObj.getString("mobilePhone"));
				paramsObj.put("iterFlag","Y");
				paramsObj.put("emailAddress",contactEntity.getEmailAddress());
				JSONObject reqParams = new JSONObject();
				reqParams.put("params", paramsObj);
				JSONObject resultObj = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUsersService/save", reqParams);
				paramsObj.put("actionType", "1");
				JSONArray arr = resultObj.getJSONArray("data");
				JSONObject obj = (JSONObject) arr.get(0);
				paramsObj.put("userIds", JSONArray.parseArray("[" + obj.getInteger("userId") + "]"));
				paramsObj.put("responsibilityIds", JSONArray.parseArray("[11,39]"));
                System.out.println("1.账号已创建==》 供应商编码：" + supplierNumber + " ,联系人：" + contactEntity.getContactName() + " ,userId:" + obj.getInteger("userId"));
				//联系人记录用户id
				contactEntity.setUserId(obj.getInteger("userId"));
				contactEntity.setSystemAccount(userName);
				System.out.println("2.联系人已关联用户！");
				JSONObject reqParams2 = new JSONObject();
				reqParams2.put("params", paramsObj);
				ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUserResponsibilityService/saveUserResp", reqParams2);
				String mailBody = CommonUtils.generateMailContent2(supplierName,contactEntity.getContactName(), userName, pwdStr);
				EmailUtil.sendMailFromWatsons(contactEntity.getEmailAddress(), "供应商登录账号", mailBody,contactEntity.getDeptCode());
				System.out.println("3.供应商通知邮件已发送！");
			}
		}
	}

	public EquPosSupplierContactsEntity_HI generateSupplierAccount(EquPosSupplierContactsEntity_HI contactEntity,String supplierNumber,String supplierName,String deleteFlag) throws Exception {
		//创建账号
		JSONObject paramsObj = null;
		String deptCode = contactEntity.getDeptCode();
		paramsObj = new JSONObject();
		if ("Y".equals(contactEntity.getSendEmailFlag()) && (null == contactEntity.getUserId() || "".equals(contactEntity.getUserId()))) {
			String userName = generateCodeServer.generateCode(supplierNumber, 2);
			String pwdStr = CommonUtils.generateRandomPassword(10);
			paramsObj.put("inPassword", pwdStr);
			paramsObj.put("startDate", new Date());
			paramsObj.put("userName", userName);
			paramsObj.put("userType", "60");
			paramsObj.put("password", new String(Base64.encodeBase64(pwdStr.getBytes())));
			paramsObj.put("userFullName", paramsObj.getString("contactName"));
			paramsObj.put("phoneNumber", paramsObj.getString("mobilePhone"));
			paramsObj.put("iterFlag","Y");
			paramsObj.put("emailAddress",contactEntity.getEmailAddress());
			JSONObject reqParams = new JSONObject();
			reqParams.put("params", paramsObj);
			JSONObject resultObj = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUsersService/save", reqParams);
			paramsObj.put("actionType", "1");
			JSONArray arr = resultObj.getJSONArray("data");
			JSONObject obj = (JSONObject) arr.get(0);
			paramsObj.put("userIds", JSONArray.parseArray("[" + obj.getInteger("userId") + "]"));
//			paramsObj.put("responsibilityIds", JSONArray.parseArray("[11,39]"));
			if("0E".equals(deptCode)){
				paramsObj.put("responsibilityIds", JSONArray.parseArray("[11,39]"));
			}else{
				paramsObj.put("responsibilityIds", JSONArray.parseArray("[30,39]"));
			}

			//联系人记录用户id
			contactEntity.setUserId(obj.getInteger("userId"));
			contactEntity.setSystemAccount(userName);
			JSONObject reqParams2 = new JSONObject();
			reqParams2.put("params", paramsObj);
			ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUserResponsibilityService/saveUserResp", reqParams2);
			String mailBody = CommonUtils.generateMailContent2(supplierName,contactEntity.getContactName(), userName, pwdStr);
			EmailUtil.sendMailFromWatsons(contactEntity.getEmailAddress(), "供应商登录账号", mailBody,contactEntity.getDeptCode());
		}else if(!"Y".equals(contactEntity.getSendEmailFlag()) && null != contactEntity.getUserId() && !"".equals(contactEntity.getUserId())){
			//查询用户信息，修改密码
			String pwdStr = CommonUtils.generateRandomPassword(10);
			JSONObject reqParams = new JSONObject();
			paramsObj.put("id",contactEntity.getUserId());
			reqParams.put("params", paramsObj);
			JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUsersService/findById", reqParams);
			String status = resultJson.getString("status");
			if("S".equals(status)){
				JSONArray data = resultJson.getJSONArray("data");
				if(data.size() > 0){
					JSONObject userJson = data.getJSONObject(0);
					String userName2 = userJson.getString("userName");
					if("Y".equals(deleteFlag)){
						userJson.put("deleteFlag", "1");
						SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
						userJson.put("endDate", format.format(new Date()));
					}else{
						userJson.put("encryptedPassword", DigestUtils.md5(pwdStr));
					}
					userJson.put("iterFlag","Y");
					JSONObject reqParams2 = new JSONObject();
					reqParams2.put("params", userJson);
					ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUsersService/save", reqParams2);
					if(!"Y".equals(deleteFlag) && "Y".equals(contactEntity.getSendEmailFlag())){
						String mailBody = CommonUtils.generateMailContent2(supplierName,contactEntity.getContactName(), userName2, pwdStr);
						EmailUtil.sendMailFromWatsons(contactEntity.getEmailAddress(), "供应商登录账号", mailBody,contactEntity.getDeptCode());
					}
				}
			}
		}
		return contactEntity;
	}

	public void updateSupplierArchives(Integer changeId,Integer supplierId,int userId){
		//*****************************************************
		//供应商基础信息更新                                      *
		//*****************************************************
		List<EquPosBgSupplierEntity_HI> equPosBgSupplierEntityList = equPosBgSupplierEntity_HI.findByProperty("changeId",changeId);
		List<EquPosSupplierInfoEntity_HI> equPosSupplierInfoEntityList = equPosSupplierInfoEntity_HI.findByProperty("supplierId",supplierId);
		EquPosBgSupplierEntity_HI equPosBgSupplierEntity = equPosBgSupplierEntityList.get(0);
		EquPosSupplierInfoEntity_HI equPosSupplierInfoEntity = equPosSupplierInfoEntityList.get(0);
		equPosSupplierInfoEntity.setSupplierName(equPosBgSupplierEntity.getSupplierName());
		equPosSupplierInfoEntity.setSupplierShortName(equPosBgSupplierEntity.getSupplierShortName());
		equPosSupplierInfoEntity.setSupplierType(equPosBgSupplierEntity.getSupplierType());
		equPosSupplierInfoEntity.setHomeUrl(equPosBgSupplierEntity.getHomeUrl());
		equPosSupplierInfoEntity.setCompanyPhone(equPosBgSupplierEntity.getCompanyPhone());
		equPosSupplierInfoEntity.setCompanyFax(equPosBgSupplierEntity.getCompanyFax());
		equPosSupplierInfoEntity.setSupplierFileId(equPosBgSupplierEntity.getSupplierFileId());
		equPosSupplierInfoEntity.setCountry(equPosBgSupplierEntity.getCountry());
		equPosSupplierInfoEntity.setMajorCustomer(equPosBgSupplierEntity.getMajorCustomer());
		equPosSupplierInfoEntity.setCompanyDescription(equPosBgSupplierEntity.getCompanyDescription());
		equPosSupplierInfoEntity.setCooperativeProject(equPosBgSupplierEntity.getCooperativeProject());
		equPosSupplierInfoEntity.setAgentLevel(equPosBgSupplierEntity.getAgentLevel());
		equPosSupplierInfoEntity.setSupplierFileName(equPosBgSupplierEntity.getSupplierFileName());
		equPosSupplierInfoEntity.setSupplierFilePath(equPosBgSupplierEntity.getSupplierFilePath());
		equPosSupplierInfoEntity.setOperatorUserId(userId);
		equPosSupplierInfoEntity_HI.update(equPosSupplierInfoEntity);

		//*****************************************************
		//供应商部门信息更新                                      *
		//*****************************************************
		List<EquPosBgSuppDeptInfoEntity_HI> equPosBgSuppDeptInfoEntityList = equPosBgSuppDeptInfoEntity_HI.findByProperty("changeId",changeId);
		List<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptEntityList = equPosSuppInfoWithDeptEntity_HI.findByProperty("supplierId",supplierId);
		for(int i = 0; i < equPosBgSuppDeptInfoEntityList.size(); i++){
			EquPosBgSuppDeptInfoEntity_HI equPosBgSuppDeptInfoEntity = equPosBgSuppDeptInfoEntityList.get(i);
			for(int j = 0; j < equPosSuppInfoWithDeptEntityList.size(); j++){
				EquPosSuppInfoWithDeptEntity_HI equPosSuppInfoWithDeptEntity = equPosSuppInfoWithDeptEntityList.get(j);
				if(equPosBgSuppDeptInfoEntity.getId().intValue() == equPosSuppInfoWithDeptEntity.getId().intValue()){
					equPosSuppInfoWithDeptEntity.setSupplierType(equPosBgSuppDeptInfoEntity.getSupplierType());
					equPosSuppInfoWithDeptEntity.setSupplierFileId(equPosBgSuppDeptInfoEntity.getSupplierFileId());
					equPosSuppInfoWithDeptEntity.setMajorCustomer(equPosBgSuppDeptInfoEntity.getMajorCustomer());
					equPosSuppInfoWithDeptEntity.setCooperativeProject(equPosBgSuppDeptInfoEntity.getCooperativeProject());
					equPosSuppInfoWithDeptEntity.setAgentLevel(equPosBgSuppDeptInfoEntity.getAgentLevel());
					equPosSuppInfoWithDeptEntity.setFileName(equPosBgSuppDeptInfoEntity.getFileName());
					equPosSuppInfoWithDeptEntity.setFilePath(equPosBgSuppDeptInfoEntity.getFilePath());
					equPosSuppInfoWithDeptEntity.setCompanyDescription(equPosBgSuppDeptInfoEntity.getCompanyDescription());
					equPosSuppInfoWithDeptEntity.setCompanyDescription(equPosBgSuppDeptInfoEntity.getCompanyDescription());
					equPosSuppInfoWithDeptEntity.setSupplierInChargeNumber(equPosBgSuppDeptInfoEntity.getSupplierInChargeNumber());
					equPosSuppInfoWithDeptEntity.setSupplierInChargeName(equPosBgSuppDeptInfoEntity.getSupplierInChargeName());
					equPosSuppInfoWithDeptEntity.setOperatorUserId(userId);
					equPosSuppInfoWithDeptEntity_HI.update(equPosSuppInfoWithDeptEntity);
				}
			}
		}

		//*****************************************************
		//关联供应商信息更新                                      *
		//*****************************************************
		List<EquPosBgAssociateSuppEntity_HI> equPosBgAssociateSuppEntityList = equPosBgAssociateSuppEntity_HI.findByProperty("changeId",changeId);
		List<EquPosAssociatedSupplierEntity_HI> equPosAssociatedSupplierEntityList = equPosAssociatedSupplierEntity_HI.findByProperty("supplierId",supplierId);

		for(int i = 0; i < equPosBgAssociateSuppEntityList.size(); i++){
			EquPosBgAssociateSuppEntity_HI equPosBgAssociateSuppEntity = equPosBgAssociateSuppEntityList.get(i);
			int count = 0;
			for(int j = 0; j < equPosAssociatedSupplierEntityList.size(); j++){
				EquPosAssociatedSupplierEntity_HI equPosAssociatedSupplierEntity = equPosAssociatedSupplierEntityList.get(j);
				if(equPosAssociatedSupplierEntity.getAssociatedId().intValue() == (equPosBgAssociateSuppEntity.getAssociatedId() == null ? -1 : equPosBgAssociateSuppEntity.getAssociatedId().intValue())){
					count++;
				}
			}
			if(count == 0){
				//新增关联供应商
				EquPosAssociatedSupplierEntity_HI entity = new EquPosAssociatedSupplierEntity_HI();
				entity.setSupplierId(equPosBgAssociateSuppEntity.getSupplierId());
				entity.setDeptCode(equPosBgAssociateSuppEntity.getDeptCode());
				entity.setAssociatedSupplierId(equPosBgAssociateSuppEntity.getAssociatedSupplierId());
				entity.setOperatorUserId(userId);
				equPosAssociatedSupplierEntity_HI.saveEntity(entity);

				//反向新增关联
				EquPosAssociatedSupplierEntity_HI entity2 = new EquPosAssociatedSupplierEntity_HI();
				entity2.setSupplierId(equPosBgAssociateSuppEntity.getAssociatedSupplierId());
				entity2.setDeptCode(equPosBgAssociateSuppEntity.getDeptCode());
				entity2.setAssociatedSupplierId(equPosBgAssociateSuppEntity.getSupplierId());
				entity2.setOperatorUserId(userId);
				equPosAssociatedSupplierEntity_HI.saveEntity(entity2);
			}
		}

		for(int j = 0; j < equPosAssociatedSupplierEntityList.size(); j++){
			EquPosAssociatedSupplierEntity_HI equPosAssociatedSupplierEntity = equPosAssociatedSupplierEntityList.get(j);
			int count = 0;
			for(int i = 0; i < equPosBgAssociateSuppEntityList.size(); i++){
				EquPosBgAssociateSuppEntity_HI equPosBgAssociateSuppEntity = equPosBgAssociateSuppEntityList.get(i);
				if(equPosAssociatedSupplierEntity.getAssociatedId().intValue() == (equPosBgAssociateSuppEntity.getAssociatedId() == null ? -1 : equPosBgAssociateSuppEntity.getAssociatedId().intValue())){
					count++;
				}
			}
			if(count == 0){
				//删除关联供应商
				Integer supplierIdParams = equPosAssociatedSupplierEntity.getSupplierId();
				Integer associatedSupplierIdParams = equPosAssociatedSupplierEntity.getAssociatedSupplierId();
				equPosAssociatedSupplierEntity_HI.delete(equPosAssociatedSupplierEntity);

				//删除反向关联供应商
				Map queryMap = new HashMap();
				queryMap.put("supplierId",associatedSupplierIdParams);
				queryMap.put("associatedSupplierId",supplierIdParams);
				List<EquPosAssociatedSupplierEntity_HI> associatedSupplierList = equPosAssociatedSupplierEntity_HI.findByProperty(queryMap);
				if(null != associatedSupplierList && associatedSupplierList.size() > 0){
					EquPosAssociatedSupplierEntity_HI entity = associatedSupplierList.get(0);
					equPosAssociatedSupplierEntity_HI.delete(entity);
				}
			}
		}

		//*****************************************************
		//可合作品类/服务范围更新                                  *
		//*****************************************************
		List<EquPosBgCategoryEntity_HI> equPosBgCategoryEntityList = equPosBgCategoryEntity_HI.findByProperty("changeId",changeId);
		List<EquPosSupplierProductCatEntity_HI> equPosSupplierProductCatEntityList = equPosSupplierProductCatEntity_HI.findByProperty("supplierId",supplierId);

		for(int i = 0; i < equPosBgCategoryEntityList.size(); i++){
			EquPosBgCategoryEntity_HI equPosBgCategoryEntity = equPosBgCategoryEntityList.get(i);
			int count = 0;
			for(int j = 0; j < equPosSupplierProductCatEntityList.size(); j++){
				EquPosSupplierProductCatEntity_HI equPosSupplierProductCatEntity = equPosSupplierProductCatEntityList.get(j);
				try{
					if(equPosSupplierProductCatEntity.getSupplierCategoryId().intValue() == (equPosBgCategoryEntity.getSupplierCategoryId() == null ? -1 : equPosBgCategoryEntity.getSupplierCategoryId().intValue())){
						//修改可合作品类/服务范围
						equPosSupplierProductCatEntity.setCategoryId(equPosBgCategoryEntity.getCategoryId());
						equPosSupplierProductCatEntity.setOperatorUserId(userId);
						equPosSupplierProductCatEntity_HI.saveEntity(equPosSupplierProductCatEntity);
						count++;
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			if(count == 0){
				//新增可合作品类/服务范围
				EquPosSupplierProductCatEntity_HI entity = new EquPosSupplierProductCatEntity_HI();
				entity.setSupplierId(equPosBgCategoryEntity.getSupplierId());
				entity.setDeptCode(equPosBgCategoryEntity.getDeptCode());
				entity.setCategoryId(equPosBgCategoryEntity.getCategoryId());
				entity.setStatus("QUALIFIED");
				entity.setOperatorUserId(userId);
				equPosSupplierProductCatEntity_HI.saveEntity(entity);
			}
		}

		for(int j = 0; j < equPosSupplierProductCatEntityList.size(); j++){
			EquPosSupplierProductCatEntity_HI equPosSupplierProductCatEntity = equPosSupplierProductCatEntityList.get(j);
			int count = 0;
			for(int i = 0; i < equPosBgCategoryEntityList.size(); i++){
				EquPosBgCategoryEntity_HI equPosBgCategoryEntity = equPosBgCategoryEntityList.get(i);
				if(equPosSupplierProductCatEntity.getSupplierCategoryId().intValue() == (equPosBgCategoryEntity.getSupplierCategoryId() == null ? -1 : equPosBgCategoryEntity.getSupplierCategoryId().intValue())){
					count++;
				}
			}
			if(count == 0){
				//删除可合作品类/服务范围
				equPosSupplierProductCatEntity_HI.delete(equPosSupplierProductCatEntity);
			}
		}

		//*****************************************************
		//供应商银行信息更新                                     *
		//*****************************************************
		List<EquPosBgBankEntity_HI> equPosBgBankEntityList = equPosBgBankEntity_HI.findByProperty("changeId",changeId);
		List<EquPosSupplierBankEntity_HI> equPosSupplierBankEntityList = equPosSupplierBankEntity_HI.findByProperty("supplierId",supplierId);
		for(int i = 0; i < equPosBgBankEntityList.size(); i++){
			EquPosBgBankEntity_HI equPosBgBankEntity = equPosBgBankEntityList.get(i);
			int count = 0;
			for(int j = 0; j < equPosSupplierBankEntityList.size(); j++){
				EquPosSupplierBankEntity_HI equPosSupplierBankEntity = equPosSupplierBankEntityList.get(j);
				if(equPosSupplierBankEntity.getBankAccountId().intValue() == (equPosBgBankEntity.getBankAccountId() == null ? -1 : equPosBgBankEntity.getBankAccountId().intValue())){
					//修改供应商银行信息
					equPosSupplierBankEntity.setBankAccountNumber(equPosBgBankEntity.getBankAccountNumber());
					equPosSupplierBankEntity.setBankUserName(equPosBgBankEntity.getBankUserName());
					equPosSupplierBankEntity.setBankName(equPosBgBankEntity.getBankName());
					equPosSupplierBankEntity.setBankCurrency(equPosBgBankEntity.getBankCurrency());
					equPosSupplierBankEntity.setOperatorUserId(userId);
					equPosSupplierBankEntity_HI.saveEntity(equPosSupplierBankEntity);
					count++;
				}
			}
			if(count == 0){
				//新增供应商银行信息
				EquPosSupplierBankEntity_HI entity = new EquPosSupplierBankEntity_HI();
				entity.setSupplierId(equPosBgBankEntity.getSupplierId());
				entity.setBankAccountNumber(equPosBgBankEntity.getBankAccountNumber());
				entity.setBankUserName(equPosBgBankEntity.getBankUserName());
				entity.setBankName(equPosBgBankEntity.getBankName());
				entity.setBankCurrency(equPosBgBankEntity.getBankCurrency());
				entity.setOperatorUserId(userId);
				equPosSupplierBankEntity_HI.saveEntity(entity);
			}
		}

		for(int j = 0; j < equPosSupplierBankEntityList.size(); j++){
			EquPosSupplierBankEntity_HI equPosSupplierBankEntity = equPosSupplierBankEntityList.get(j);
			int count = 0;
			for(int i = 0; i < equPosBgBankEntityList.size(); i++){
				EquPosBgBankEntity_HI equPosBgBankEntity = equPosBgBankEntityList.get(i);
				if(equPosSupplierBankEntity.getBankAccountId().intValue() == (equPosBgBankEntity.getBankAccountId() == null ? -1 : equPosBgBankEntity.getBankAccountId().intValue())){
					count++;
				}
			}
			if(count == 0){
				//删除供应商银行信息
				equPosSupplierBankEntity_HI.delete(equPosSupplierBankEntity);
			}
		}

		//*****************************************************
		//供应商资质信息更新                                     *
		//*****************************************************
		List<EquPosBgCredentialsEntity_HI> equPosBgCredentialsEntityList = equPosBgCredentialsEntity_HI.findByProperty("changeId",changeId);
		List<EquPosSupplierCredentialsEntity_HI> equPosSupplierCredentialsEntityList = equPosSupplierCredentialsEntity_HI.findByProperty("supplierId",supplierId);

		EquPosBgCredentialsEntity_HI equPosBgCredentialsEntity = equPosBgCredentialsEntityList.get(0);
		EquPosSupplierCredentialsEntity_HI equPosSupplierCredentialsEntity = null;

		if(equPosSupplierCredentialsEntityList != null && equPosSupplierCredentialsEntityList.size() > 0){
			equPosSupplierCredentialsEntity = equPosSupplierCredentialsEntityList.get(0);
		}else{
			equPosSupplierCredentialsEntity = new EquPosSupplierCredentialsEntity_HI();
		}

		equPosSupplierCredentialsEntity.setSupplierId(equPosBgCredentialsEntity.getSupplierId());
		equPosSupplierCredentialsEntity.setLongBusinessIndate(equPosBgCredentialsEntity.getLongBusinessIndate());
		equPosSupplierCredentialsEntity.setIsThreeInOne(equPosBgCredentialsEntity.getIsThreeInOne());
		equPosSupplierCredentialsEntity.setLicenseNum(equPosBgCredentialsEntity.getLicenseNum());
		equPosSupplierCredentialsEntity.setLicenseIndate(equPosBgCredentialsEntity.getLicenseIndate());
		equPosSupplierCredentialsEntity.setLongLicenseIndate(equPosBgCredentialsEntity.getLongLicenseIndate());
		equPosSupplierCredentialsEntity.setTaxCode(equPosBgCredentialsEntity.getTaxCode());
		equPosSupplierCredentialsEntity.setTissueCode(equPosBgCredentialsEntity.getTissueCode());
		equPosSupplierCredentialsEntity.setTissueIndate(equPosBgCredentialsEntity.getTissueIndate());
		equPosSupplierCredentialsEntity.setLongTissueIndate(equPosBgCredentialsEntity.getLongTissueIndate());
		equPosSupplierCredentialsEntity.setBusinessScope(equPosBgCredentialsEntity.getBusinessScope());
		equPosSupplierCredentialsEntity.setRepresentativeName(equPosBgCredentialsEntity.getRepresentativeName());
		equPosSupplierCredentialsEntity.setBankPermitNumber(equPosBgCredentialsEntity.getBankPermitNumber());
		equPosSupplierCredentialsEntity.setEnrollCapital(equPosBgCredentialsEntity.getEnrollCapital());
		equPosSupplierCredentialsEntity.setRegisteredAddress(equPosBgCredentialsEntity.getRegisteredAddress());
		equPosSupplierCredentialsEntity.setEstablishmentDate(equPosBgCredentialsEntity.getEstablishmentDate());
		equPosSupplierCredentialsEntity.setBusinessYears(equPosBgCredentialsEntity.getBusinessYears());
		equPosSupplierCredentialsEntity.setShareholderInfo(equPosBgCredentialsEntity.getShareholderInfo());
		equPosSupplierCredentialsEntity.setRelatedParty(equPosBgCredentialsEntity.getRelatedParty());
		equPosSupplierCredentialsEntity.setProjectExperience(equPosBgCredentialsEntity.getProjectExperience());
		equPosSupplierCredentialsEntity.setIntentionWatsonsProject(equPosBgCredentialsEntity.getIntentionWatsonsProject());
		equPosSupplierCredentialsEntity.setLicenseFileId(equPosBgCredentialsEntity.getLicenseFileId());
		equPosSupplierCredentialsEntity.setTissueFileId(equPosBgCredentialsEntity.getTissueFileId());
		equPosSupplierCredentialsEntity.setTaxFileId(equPosBgCredentialsEntity.getTaxFileId());
		equPosSupplierCredentialsEntity.setBankFileId(equPosBgCredentialsEntity.getBankFileId());
		equPosSupplierCredentialsEntity.setTaxpayerFileId(equPosBgCredentialsEntity.getTaxpayerFileId());
		equPosSupplierCredentialsEntity.setProjectExperienceFileId(equPosBgCredentialsEntity.getProjectExperienceFileId());
		equPosSupplierCredentialsEntity.setRelatedFileId(equPosBgCredentialsEntity.getRelatedFileId());
		equPosSupplierCredentialsEntity.setLicenseFileName(equPosBgCredentialsEntity.getLicenseFileName());
		equPosSupplierCredentialsEntity.setLicenseFilePath(equPosBgCredentialsEntity.getLicenseFilePath());
		equPosSupplierCredentialsEntity.setTissueFileName(equPosBgCredentialsEntity.getTissueFileName());
		equPosSupplierCredentialsEntity.setTissueFilePath(equPosBgCredentialsEntity.getTissueFilePath());
		equPosSupplierCredentialsEntity.setTaxFileName(equPosBgCredentialsEntity.getTaxFileName());
		equPosSupplierCredentialsEntity.setTaxFilePath(equPosBgCredentialsEntity.getTaxFilePath());
		equPosSupplierCredentialsEntity.setBankFileName(equPosBgCredentialsEntity.getBankFileName());
		equPosSupplierCredentialsEntity.setBankFilePath(equPosBgCredentialsEntity.getBankFilePath());
		equPosSupplierCredentialsEntity.setTaxpayerFileName(equPosBgCredentialsEntity.getTaxpayerFileName());
		equPosSupplierCredentialsEntity.setTaxpayerFilePath(equPosBgCredentialsEntity.getTaxpayerFilePath());
		equPosSupplierCredentialsEntity.setProjectExperienceFileName(equPosBgCredentialsEntity.getProjectExperienceFileName());
		equPosSupplierCredentialsEntity.setProjectExperienceFilePath(equPosBgCredentialsEntity.getProjectExperienceFilePath());
		equPosSupplierCredentialsEntity.setRelatedFileName(equPosBgCredentialsEntity.getRelatedFileName());
		equPosSupplierCredentialsEntity.setRelatedFilePath(equPosBgCredentialsEntity.getRelatedFilePath());
		equPosSupplierCredentialsEntity.setCompanyCreditFileId(equPosBgCredentialsEntity.getCompanyCreditFileId());
		equPosSupplierCredentialsEntity.setCompanyCreditFileName(equPosBgCredentialsEntity.getCompanyCreditFileName());
		equPosSupplierCredentialsEntity.setCompanyCreditFilePath(equPosBgCredentialsEntity.getCompanyCreditFilePath());
		equPosSupplierCredentialsEntity.setDeptCode(equPosBgCredentialsEntity.getDeptCode());
		equPosSupplierCredentialsEntity.setOperatorUserId(userId);
		equPosSupplierCredentialsEntity_HI.saveEntity(equPosSupplierCredentialsEntity);

		//*****************************************************
		//供应商资质文件更新                                     *
		//*****************************************************
		List<EquPosBgCredentialAttachEntity_HI> equPosBgCredentialAttachEntityList = equPosBgCredentialAttachEntity_HI.findByProperty("changeId",changeId);
		List<EquPosCredentialsAttachmentEntity_HI> equPosCredentialsAttachmentEntityList = equPosCredentialsAttachmentEntity_HI.findByProperty("supplierId",supplierId);
		for(int i = 0; i < equPosBgCredentialAttachEntityList.size(); i++){
			EquPosBgCredentialAttachEntity_HI equPosBgCredentialAttachEntity = equPosBgCredentialAttachEntityList.get(i);
			int count = 0;
			for(int j = 0; j < equPosCredentialsAttachmentEntityList.size(); j++){
				EquPosCredentialsAttachmentEntity_HI equPosCredentialsAttachmentEntity = equPosCredentialsAttachmentEntityList.get(j);
				if(equPosCredentialsAttachmentEntity.getAttachmentId().intValue() == (equPosBgCredentialAttachEntity.getAttachmentId() == null ? -1 : equPosBgCredentialAttachEntity.getAttachmentId().intValue())){
					//修改供应商资质文件
					equPosCredentialsAttachmentEntity.setFileId(equPosBgCredentialAttachEntity.getFileId());
					equPosCredentialsAttachmentEntity.setDescription(equPosBgCredentialAttachEntity.getDescription());
					equPosCredentialsAttachmentEntity.setInvalidDate(equPosBgCredentialAttachEntity.getInvalidDate());
					equPosCredentialsAttachmentEntity.setFileName(equPosBgCredentialAttachEntity.getFileName());
					equPosCredentialsAttachmentEntity.setFilePath(equPosBgCredentialAttachEntity.getFilePath());
					equPosCredentialsAttachmentEntity.setAttachmentName(equPosBgCredentialAttachEntity.getAttachmentName());
					equPosCredentialsAttachmentEntity.setIsProductFactory(equPosBgCredentialAttachEntity.getIsProductFactory());
					equPosCredentialsAttachmentEntity.setFixFlag(equPosBgCredentialAttachEntity.getFixFlag());
					equPosCredentialsAttachmentEntity.setSupplierAddressId(equPosBgCredentialAttachEntity.getSupplierAddressId());
					equPosCredentialsAttachmentEntity.setFileType(equPosBgCredentialAttachEntity.getFileType());
					equPosCredentialsAttachmentEntity.setOperatorUserId(userId);
					equPosCredentialsAttachmentEntity_HI.saveEntity(equPosCredentialsAttachmentEntity);
					count++;
				}
			}
			if(count == 0){
				//新增供应商资质文件
				EquPosCredentialsAttachmentEntity_HI entity = new EquPosCredentialsAttachmentEntity_HI();
				entity.setSupplierId(equPosBgCredentialAttachEntity.getSupplierId());
				entity.setDeptmentCode(equPosBgCredentialAttachEntity.getDeptmentCode());
				entity.setFileId(equPosBgCredentialAttachEntity.getFileId());
				entity.setDescription(equPosBgCredentialAttachEntity.getDescription());
				entity.setInvalidDate(equPosBgCredentialAttachEntity.getInvalidDate());
				entity.setFileName(equPosBgCredentialAttachEntity.getFileName());
				entity.setFilePath(equPosBgCredentialAttachEntity.getFilePath());
				entity.setAttachmentName(equPosBgCredentialAttachEntity.getAttachmentName());
				entity.setIsProductFactory(equPosBgCredentialAttachEntity.getIsProductFactory());
				entity.setFixFlag(equPosBgCredentialAttachEntity.getFixFlag());
				entity.setSupplierAddressId(equPosBgCredentialAttachEntity.getSupplierAddressId());
				entity.setFileType(equPosBgCredentialAttachEntity.getFileType());
				entity.setOperatorUserId(userId);
				equPosCredentialsAttachmentEntity_HI.saveEntity(entity);
			}
		}

		for(int j = 0; j < equPosCredentialsAttachmentEntityList.size(); j++){
			EquPosCredentialsAttachmentEntity_HI equPosCredentialsAttachmentEntity = equPosCredentialsAttachmentEntityList.get(j);
			int count = 0;
			for(int i = 0; i < equPosBgCredentialAttachEntityList.size(); i++){
				EquPosBgCredentialAttachEntity_HI equPosBgCredentialAttachEntity = equPosBgCredentialAttachEntityList.get(i);
				if(equPosCredentialsAttachmentEntity.getAttachmentId().intValue() == (equPosBgCredentialAttachEntity.getAttachmentId() == null ? -1 : equPosBgCredentialAttachEntity.getAttachmentId().intValue())){
					count++;
				}
			}
			if(count == 0){
				//删除供应商资质文件
				equPosCredentialsAttachmentEntity_HI.delete(equPosCredentialsAttachmentEntity);
			}
		}

		//*****************************************************
		//供应商地址信息更新                                     *
		//*****************************************************
		List<EquPosBgAddressesEntity_HI> equPosBgAddressesEntityList = equPosBgAddressesEntity_HI.findByProperty("changeId",changeId);
		List<EquPosSupplierAddressesEntity_HI> equPosSupplierAddressesEntityList = equPosSupplierAddressesEntity_HI.findByProperty("supplierId",supplierId);

		for(int i = 0; i < equPosBgAddressesEntityList.size(); i++){
			EquPosBgAddressesEntity_HI equPosBgAddressesEntity = equPosBgAddressesEntityList.get(i);

			Map queryMap2 = new HashMap();
			queryMap2.put("supplierId",supplierId);
			queryMap2.put("supplierAddressId",equPosBgAddressesEntity.getSupplierAddressId());
			List<EquPosProductionInfoEntity_HI> productionList = equPosProductionInfoEntity_HI.findByProperty(queryMap2);
			List<EquPosCapacityInfoEntity_HI> capacityList = equPosCapacityInfoEntity_HI.findByProperty(queryMap2);
			equPosProductionInfoEntity_HI.delete(productionList);
			equPosCapacityInfoEntity_HI.delete(capacityList);

			int count = 0;
			for(int j = 0; j < equPosSupplierAddressesEntityList.size(); j++){
				EquPosSupplierAddressesEntity_HI equPosSupplierAddressesEntity = equPosSupplierAddressesEntityList.get(j);
				if(equPosSupplierAddressesEntity.getSupplierAddressId().intValue() == (equPosBgAddressesEntity.getSupplierAddressId() == null ? -1 : equPosBgAddressesEntity.getSupplierAddressId().intValue())){
					//修改供应商地址
					equPosSupplierAddressesEntity.setAddressName(equPosBgAddressesEntity.getAddressName());
					equPosSupplierAddressesEntity.setCountry(equPosBgAddressesEntity.getCountry());
					equPosSupplierAddressesEntity.setProvince(equPosBgAddressesEntity.getProvince());
					equPosSupplierAddressesEntity.setCity(equPosBgAddressesEntity.getCity());
					equPosSupplierAddressesEntity.setCounty(equPosBgAddressesEntity.getCounty());
					equPosSupplierAddressesEntity.setDetailAddress(equPosBgAddressesEntity.getDetailAddress());
					equPosSupplierAddressesEntity.setOperatorUserId(userId);
					equPosSupplierAddressesEntity_HI.saveEntity(equPosSupplierAddressesEntity);
					count++;

					//todo_修改。。。

					Map queryMap = new HashMap();
					queryMap.put("changeId",changeId);
					queryMap.put("supplierAddressId",equPosSupplierAddressesEntity.getSupplierAddressId());

					List<EquPosBgProductionInfoEntity_HI> equPosBgProductionInfoEntityList = equPosBgProductionInfoEntity_HI.findByProperty(queryMap);
					for(int m = 0; m < equPosBgProductionInfoEntityList.size(); m++){
						EquPosBgProductionInfoEntity_HI equPosBgProductionInfoEntity = equPosBgProductionInfoEntityList.get(m);
						//新增供应商生产信息
						EquPosProductionInfoEntity_HI productionEntity = new EquPosProductionInfoEntity_HI();
						productionEntity.setProductionStartDate(equPosBgProductionInfoEntity.getProductionStartDate());
						productionEntity.setFactoryArea(equPosBgProductionInfoEntity.getFactoryArea());
						productionEntity.setProductionArea(equPosBgProductionInfoEntity.getProductionArea());
						productionEntity.setFinishedProductArea(equPosBgProductionInfoEntity.getFinishedProductArea());
						productionEntity.setRawMaterialArea(equPosBgProductionInfoEntity.getRawMaterialArea());
						productionEntity.setPackagingArea(equPosBgProductionInfoEntity.getPackagingArea());
						productionEntity.setLaboratoryArea(equPosBgProductionInfoEntity.getLaboratoryArea());
						productionEntity.setEmployeesAmount(equPosBgProductionInfoEntity.getEmployeesAmount());
						productionEntity.setQualityPersonnelAmount(equPosBgProductionInfoEntity.getQualityPersonnelAmount());
						productionEntity.setSalesmanAmount(equPosBgProductionInfoEntity.getSalesmanAmount());
						productionEntity.setProducersAmount(equPosBgProductionInfoEntity.getProducersAmount());
						productionEntity.setDesignerAmount(equPosBgProductionInfoEntity.getDesignerAmount());
						productionEntity.setAdministrativeStaffAmount(equPosBgProductionInfoEntity.getAdministrativeStaffAmount());
						productionEntity.setOther(equPosBgProductionInfoEntity.getOther());
						productionEntity.setOfficeArea(equPosBgProductionInfoEntity.getOfficeArea());
						productionEntity.setSupplierAddressId(equPosSupplierAddressesEntity.getSupplierAddressId());
						productionEntity.setSupplierId(equPosBgProductionInfoEntity.getSupplierId());
						productionEntity.setOperatorUserId(userId);
						equPosProductionInfoEntity_HI.saveEntity(productionEntity);
					}

					List<EquPosBgCapacityEntity_HI> equPosBgCapacityEntityList = equPosBgCapacityEntity_HI.findByProperty(queryMap);
					for(int n = 0; n < equPosBgCapacityEntityList.size(); n++){
						EquPosBgCapacityEntity_HI equPosBgCapacityEntity = equPosBgCapacityEntityList.get(n);
						//新增供应商产能信息
						EquPosCapacityInfoEntity_HI capacityEntity = new EquPosCapacityInfoEntity_HI();
						capacityEntity.setProductScope(equPosBgCapacityEntity.getProductScope());
						capacityEntity.setMainRawMaterials(equPosBgCapacityEntity.getMainRawMaterials());
						capacityEntity.setProductionEquipment(equPosBgCapacityEntity.getProductionEquipment());
						capacityEntity.setProductionCapacity(equPosBgCapacityEntity.getProductionCapacity());
						capacityEntity.setRemark(equPosBgCapacityEntity.getRemark());
						capacityEntity.setProductionCapacityFileId(equPosBgCapacityEntity.getProductionCapacityFileId());
						capacityEntity.setProductionCapacityFileName(equPosBgCapacityEntity.getProductionCapacityFileName());
						capacityEntity.setProductionCapacityFilePath(equPosBgCapacityEntity.getProductionCapacityFilePath());
						capacityEntity.setOperatorUserId(userId);
						capacityEntity.setSupplierAddressId(equPosSupplierAddressesEntity.getSupplierAddressId());
						capacityEntity.setSupplierId(equPosBgCapacityEntity.getSupplierId());
						capacityEntity.setOperatorUserId(userId);
						equPosCapacityInfoEntity_HI.saveEntity(capacityEntity);
					}

					//todo 修改。。。

				}
			}
			if(count == 0){
				//新增供应商地址
				EquPosSupplierAddressesEntity_HI entity = new EquPosSupplierAddressesEntity_HI();
				entity.setSupplierId(equPosBgAddressesEntity.getSupplierId());
				entity.setDeptCode(equPosBgAddressesEntity.getDeptCode());
				entity.setAddressName(equPosBgAddressesEntity.getAddressName());
				entity.setCountry(equPosBgAddressesEntity.getCountry());
				entity.setProvince(equPosBgAddressesEntity.getProvince());
				entity.setCity(equPosBgAddressesEntity.getCity());
				entity.setCounty(equPosBgAddressesEntity.getCounty());
				entity.setDetailAddress(equPosBgAddressesEntity.getDetailAddress());
				entity.setOperatorUserId(userId);
				EquPosSupplierAddressesEntity_HI ety2 = equPosSupplierAddressesEntity_HI.saveEntity(entity);
				ety2.setSupplierAddressId(ety2.getId());
				EquPosSupplierAddressesEntity_HI ety = equPosSupplierAddressesEntity_HI.saveEntity(ety2);

				//插入供应商生产信息
				Map queryMap = new HashMap();
				queryMap.put("changeId",changeId);
				queryMap.put("supplierAddressId",equPosBgAddressesEntity.getSourceId());

				List<EquPosBgProductionInfoEntity_HI> equPosBgProductionInfoEntityList = equPosBgProductionInfoEntity_HI.findByProperty(queryMap);
				for(int j = 0; j < equPosBgProductionInfoEntityList.size(); j++){
					EquPosBgProductionInfoEntity_HI equPosBgProductionInfoEntity = equPosBgProductionInfoEntityList.get(j);
					//新增供应商生产信息
					EquPosProductionInfoEntity_HI productionEntity = new EquPosProductionInfoEntity_HI();
					productionEntity.setProductionStartDate(equPosBgProductionInfoEntity.getProductionStartDate());
					productionEntity.setFactoryArea(equPosBgProductionInfoEntity.getFactoryArea());
					productionEntity.setProductionArea(equPosBgProductionInfoEntity.getProductionArea());
					productionEntity.setFinishedProductArea(equPosBgProductionInfoEntity.getFinishedProductArea());
					productionEntity.setRawMaterialArea(equPosBgProductionInfoEntity.getRawMaterialArea());
					productionEntity.setPackagingArea(equPosBgProductionInfoEntity.getPackagingArea());
					productionEntity.setLaboratoryArea(equPosBgProductionInfoEntity.getLaboratoryArea());
					productionEntity.setEmployeesAmount(equPosBgProductionInfoEntity.getEmployeesAmount());
					productionEntity.setQualityPersonnelAmount(equPosBgProductionInfoEntity.getQualityPersonnelAmount());
					productionEntity.setSalesmanAmount(equPosBgProductionInfoEntity.getSalesmanAmount());
					productionEntity.setProducersAmount(equPosBgProductionInfoEntity.getProducersAmount());
					productionEntity.setDesignerAmount(equPosBgProductionInfoEntity.getDesignerAmount());
					productionEntity.setAdministrativeStaffAmount(equPosBgProductionInfoEntity.getAdministrativeStaffAmount());
					productionEntity.setOther(equPosBgProductionInfoEntity.getOther());
					productionEntity.setOfficeArea(equPosBgProductionInfoEntity.getOfficeArea());
					productionEntity.setSupplierAddressId(ety.getSupplierAddressId());
					productionEntity.setSupplierId(equPosBgProductionInfoEntity.getSupplierId());
					productionEntity.setOperatorUserId(userId);
					equPosProductionInfoEntity_HI.saveEntity(productionEntity);
				}

				List<EquPosBgCapacityEntity_HI> equPosBgCapacityEntityList = equPosBgCapacityEntity_HI.findByProperty(queryMap);
				for(int j = 0; j < equPosBgCapacityEntityList.size(); j++){
					EquPosBgCapacityEntity_HI equPosBgCapacityEntity = equPosBgCapacityEntityList.get(j);
					//新增供应商产能信息
					EquPosCapacityInfoEntity_HI capacityEntity = new EquPosCapacityInfoEntity_HI();
					capacityEntity.setProductScope(equPosBgCapacityEntity.getProductScope());
					capacityEntity.setMainRawMaterials(equPosBgCapacityEntity.getMainRawMaterials());
					capacityEntity.setProductionEquipment(equPosBgCapacityEntity.getProductionEquipment());
					capacityEntity.setProductionCapacity(equPosBgCapacityEntity.getProductionCapacity());
					capacityEntity.setRemark(equPosBgCapacityEntity.getRemark());
					capacityEntity.setProductionCapacityFileId(equPosBgCapacityEntity.getProductionCapacityFileId());
					capacityEntity.setProductionCapacityFileName(equPosBgCapacityEntity.getProductionCapacityFileName());
					capacityEntity.setProductionCapacityFilePath(equPosBgCapacityEntity.getProductionCapacityFilePath());
					capacityEntity.setOperatorUserId(userId);
					capacityEntity.setSupplierAddressId(ety.getSupplierAddressId());
					capacityEntity.setSupplierId(equPosBgCapacityEntity.getSupplierId());
					capacityEntity.setOperatorUserId(userId);
					equPosCapacityInfoEntity_HI.saveEntity(capacityEntity);
				}
			}
		}

		for(int j = 0; j < equPosSupplierAddressesEntityList.size(); j++){
			EquPosSupplierAddressesEntity_HI equPosSupplierAddressesEntity = equPosSupplierAddressesEntityList.get(j);
			int count = 0;
			for(int i = 0; i < equPosBgAddressesEntityList.size(); i++){
				EquPosBgAddressesEntity_HI equPosBgAddressesEntity = equPosBgAddressesEntityList.get(i);
				if(equPosSupplierAddressesEntity.getSupplierAddressId().intValue() == (equPosBgAddressesEntity.getSupplierAddressId() == null ? -1 : equPosBgAddressesEntity.getSupplierAddressId().intValue())){
					count++;
				}
			}
			if(count == 0){
				//删除供应商地址
				equPosSupplierAddressesEntity_HI.delete(equPosSupplierAddressesEntity);
			}
		}


		//*****************************************************
		//供应商生产信息更新                                     *
		//*****************************************************
//		List<EquPosBgProductionInfoEntity_HI> equPosBgProductionInfoEntityList = equPosBgProductionInfoEntity_HI.findByProperty("changeId",changeId);
//		List<EquPosProductionInfoEntity_HI> equPosProductionInfoEntityList = equPosProductionInfoEntity_HI.findByProperty("supplierId",supplierId);
//		for(int i = 0; i < equPosBgProductionInfoEntityList.size(); i++){
//			EquPosBgProductionInfoEntity_HI equPosBgProductionInfoEntity = equPosBgProductionInfoEntityList.get(i);
//			int count = 0;
//			for(int j = 0; j < equPosProductionInfoEntityList.size(); j++){
//				EquPosProductionInfoEntity_HI equPosProductionInfoEntity = equPosProductionInfoEntityList.get(j);
//				if(equPosProductionInfoEntity.getProductionId().intValue() == (equPosProductionInfoEntity.getProductionId() == null ? -1 : equPosProductionInfoEntity.getProductionId().intValue())){
//					//修改供应商生产信息
//					equPosProductionInfoEntity.setProductionStartDate(equPosBgProductionInfoEntity.getProductionStartDate());
//					equPosProductionInfoEntity.setFactoryArea(equPosBgProductionInfoEntity.getFactoryArea());
//					equPosProductionInfoEntity.setProductionArea(equPosBgProductionInfoEntity.getProductionArea());
//					equPosProductionInfoEntity.setFinishedProductArea(equPosBgProductionInfoEntity.getFinishedProductArea());
//					equPosProductionInfoEntity.setRawMaterialArea(equPosBgProductionInfoEntity.getRawMaterialArea());
//					equPosProductionInfoEntity.setPackagingArea(equPosBgProductionInfoEntity.getPackagingArea());
//					equPosProductionInfoEntity.setLaboratoryArea(equPosBgProductionInfoEntity.getLaboratoryArea());
//					equPosProductionInfoEntity.setEmployeesAmount(equPosBgProductionInfoEntity.getEmployeesAmount());
//					equPosProductionInfoEntity.setQualityPersonnelAmount(equPosBgProductionInfoEntity.getQualityPersonnelAmount());
//					equPosProductionInfoEntity.setSalesmanAmount(equPosBgProductionInfoEntity.getSalesmanAmount());
//					equPosProductionInfoEntity.setProducersAmount(equPosBgProductionInfoEntity.getProducersAmount());
//					equPosProductionInfoEntity.setDesignerAmount(equPosBgProductionInfoEntity.getDesignerAmount());
//					equPosProductionInfoEntity.setAdministrativeStaffAmount(equPosBgProductionInfoEntity.getAdministrativeStaffAmount());
//					equPosProductionInfoEntity.setOther(equPosBgProductionInfoEntity.getOther());
//					equPosProductionInfoEntity.setOfficeArea(equPosBgProductionInfoEntity.getOfficeArea());
//					equPosProductionInfoEntity.setOperatorUserId(userId);
//					equPosProductionInfoEntity_HI.saveEntity(equPosProductionInfoEntity);
//					count++;
//				}
//			}
//			if(count == 0){
//				//新增供应商生产信息
//				EquPosProductionInfoEntity_HI entity = new EquPosProductionInfoEntity_HI();
//				entity.setProductionStartDate(equPosBgProductionInfoEntity.getProductionStartDate());
//				entity.setFactoryArea(equPosBgProductionInfoEntity.getFactoryArea());
//				entity.setProductionArea(equPosBgProductionInfoEntity.getProductionArea());
//				entity.setFinishedProductArea(equPosBgProductionInfoEntity.getFinishedProductArea());
//				entity.setRawMaterialArea(equPosBgProductionInfoEntity.getRawMaterialArea());
//				entity.setPackagingArea(equPosBgProductionInfoEntity.getPackagingArea());
//				entity.setLaboratoryArea(equPosBgProductionInfoEntity.getLaboratoryArea());
//				entity.setEmployeesAmount(equPosBgProductionInfoEntity.getEmployeesAmount());
//				entity.setQualityPersonnelAmount(equPosBgProductionInfoEntity.getQualityPersonnelAmount());
//				entity.setSalesmanAmount(equPosBgProductionInfoEntity.getSalesmanAmount());
//				entity.setProducersAmount(equPosBgProductionInfoEntity.getProducersAmount());
//				entity.setDesignerAmount(equPosBgProductionInfoEntity.getDesignerAmount());
//				entity.setAdministrativeStaffAmount(equPosBgProductionInfoEntity.getAdministrativeStaffAmount());
//				entity.setOther(equPosBgProductionInfoEntity.getOther());
//				entity.setOfficeArea(equPosBgProductionInfoEntity.getOfficeArea());
//				entity.setSupplierAddressId(equPosBgProductionInfoEntity.getSupplierAddressId());
//				entity.setSupplierId(equPosBgProductionInfoEntity.getSupplierId());
//				entity.setOperatorUserId(userId);
//				equPosProductionInfoEntity_HI.saveEntity(entity);
//			}
//		}

//		for(int j = 0; j < equPosProductionInfoEntityList.size(); j++){
//			EquPosProductionInfoEntity_HI equPosProductionInfoEntity = equPosProductionInfoEntityList.get(j);
//			int count = 0;
//			for(int i = 0; i < equPosBgProductionInfoEntityList.size(); i++){
//				EquPosBgProductionInfoEntity_HI equPosBgProductionInfoEntity = equPosBgProductionInfoEntityList.get(i);
//				if(equPosProductionInfoEntity.getProductionId().intValue() == (equPosProductionInfoEntity.getProductionId() == null ? -1 : equPosProductionInfoEntity.getProductionId().intValue())){
//					count++;
//				}
//			}
//			if(count == 0){
//				//删除供应商地址
//				equPosProductionInfoEntity_HI.delete(equPosProductionInfoEntity);
//			}
//		}

		//*****************************************************
		//供应商产能信息更新                                     *
		//*****************************************************
//		List<EquPosBgCapacityEntity_HI> equPosBgCapacityEntityList = equPosBgCapacityEntity_HI.findByProperty("changeId",changeId);
//		List<EquPosCapacityInfoEntity_HI> equPosCapacityInfoEntityList = equPosCapacityInfoEntity_HI.findByProperty("supplierId",supplierId);
//		for(int i = 0; i < equPosBgCapacityEntityList.size(); i++){
//			EquPosBgCapacityEntity_HI equPosBgCapacityEntity = equPosBgCapacityEntityList.get(i);
//			int count = 0;
//			for(int j = 0; j < equPosCapacityInfoEntityList.size(); j++){
//				EquPosCapacityInfoEntity_HI equPosCapacityInfoEntity = equPosCapacityInfoEntityList.get(j);
//				if(equPosCapacityInfoEntity.getCapacityId().intValue() == (equPosBgCapacityEntity.getCapacityId() == null ? -1 : equPosBgCapacityEntity.getCapacityId().intValue())){
//					//修改供应商产能信息
//					equPosCapacityInfoEntity.setProductScope(equPosBgCapacityEntity.getProductScope());
//					equPosCapacityInfoEntity.setMainRawMaterials(equPosBgCapacityEntity.getMainRawMaterials());
//					equPosCapacityInfoEntity.setProductionEquipment(equPosBgCapacityEntity.getProductionEquipment());
//					equPosCapacityInfoEntity.setProductionCapacity(equPosBgCapacityEntity.getProductionCapacity());
//					equPosCapacityInfoEntity.setRemark(equPosBgCapacityEntity.getRemark());
//					equPosCapacityInfoEntity.setProductionCapacityFileId(equPosBgCapacityEntity.getProductionCapacityFileId());
//					equPosCapacityInfoEntity.setProductionCapacityFileName(equPosBgCapacityEntity.getProductionCapacityFileName());
//					equPosCapacityInfoEntity.setProductionCapacityFilePath(equPosBgCapacityEntity.getProductionCapacityFilePath());
//					equPosCapacityInfoEntity.setOperatorUserId(userId);
//					equPosCapacityInfoEntity_HI.saveEntity(equPosCapacityInfoEntity);
//					count++;
//				}
//			}
//			if(count == 0){
//				//新增供应商产能信息
//				EquPosCapacityInfoEntity_HI entity = new EquPosCapacityInfoEntity_HI();
//				entity.setProductScope(equPosBgCapacityEntity.getProductScope());
//				entity.setMainRawMaterials(equPosBgCapacityEntity.getMainRawMaterials());
//				entity.setProductionEquipment(equPosBgCapacityEntity.getProductionEquipment());
//				entity.setProductionCapacity(equPosBgCapacityEntity.getProductionCapacity());
//				entity.setRemark(equPosBgCapacityEntity.getRemark());
//				entity.setProductionCapacityFileId(equPosBgCapacityEntity.getProductionCapacityFileId());
//				entity.setProductionCapacityFileName(equPosBgCapacityEntity.getProductionCapacityFileName());
//				entity.setProductionCapacityFilePath(equPosBgCapacityEntity.getProductionCapacityFilePath());
//				entity.setOperatorUserId(userId);
//				entity.setSupplierAddressId(equPosBgCapacityEntity.getSupplierAddressId());
//				entity.setSupplierId(equPosBgCapacityEntity.getSupplierId());
//				entity.setOperatorUserId(userId);
//				equPosCapacityInfoEntity_HI.saveEntity(entity);
//			}
//		}
//
//		for(int j = 0; j < equPosCapacityInfoEntityList.size(); j++){
//			EquPosCapacityInfoEntity_HI equPosCapacityInfoEntity = equPosCapacityInfoEntityList.get(j);
//			int count = 0;
//			for(int i = 0; i < equPosBgCapacityEntityList.size(); i++){
//				EquPosBgCapacityEntity_HI equPosBgCapacityEntity = equPosBgCapacityEntityList.get(i);
//				if(equPosCapacityInfoEntity.getCapacityId().intValue() == (equPosBgCapacityEntity.getCapacityId() == null ? -1 : equPosBgCapacityEntity.getCapacityId().intValue())){
//					count++;
//				}
//			}
//			if(count == 0){
//				//删除供应商产能信息
//				equPosCapacityInfoEntity_HI.delete(equPosCapacityInfoEntity);
//			}
//		}

		//*****************************************************
		//供应商经营状况更新                                     *
		//*****************************************************
		List<EquPosBgOperationStatusEntity_HI> equPosBgOperationStatusEntityList = equPosBgOperationStatusEntity_HI.findByProperty("changeId",changeId);
		List<EquPosOperationalStatusEntity_HI> equPosOperationalStatusEntityList = equPosOperationalStatusEntity_HI.findByProperty("supplierId",supplierId);
		for(int i = 0; i < equPosBgOperationStatusEntityList.size(); i++){
			EquPosBgOperationStatusEntity_HI equPosBgOperationStatusEntity = equPosBgOperationStatusEntityList.get(i);
			int count = 0;
			for(int j = 0; j < equPosOperationalStatusEntityList.size(); j++){
				EquPosOperationalStatusEntity_HI equPosOperationalStatusEntity = equPosOperationalStatusEntityList.get(j);
				if(equPosOperationalStatusEntity.getOperationalId().intValue() == (equPosBgOperationStatusEntity.getOperationalId() == null ? -1 : equPosBgOperationStatusEntity.getOperationalId().intValue())){
					//修改供应商经营状况
					equPosOperationalStatusEntity.setSurroundingEnvironment(equPosBgOperationStatusEntity.getSurroundingEnvironment());
					equPosOperationalStatusEntity.setDoorPlate(equPosBgOperationStatusEntity.getDoorPlate());
					equPosOperationalStatusEntity.setReception(equPosBgOperationStatusEntity.getReception());
					equPosOperationalStatusEntity.setCompanyArea(equPosBgOperationStatusEntity.getCompanyArea());
					equPosOperationalStatusEntity.setOfficeSpace(equPosBgOperationStatusEntity.getOfficeSpace());
					equPosOperationalStatusEntity.setEmployeeProfile(equPosBgOperationStatusEntity.getEmployeeProfile());
					equPosOperationalStatusEntity.setMajorCustomers(equPosBgOperationStatusEntity.getMajorCustomers());
					equPosOperationalStatusEntity.setSaleChannel(equPosBgOperationStatusEntity.getSaleChannel());
					equPosOperationalStatusEntity.setSurEnvironmentFileId(equPosBgOperationStatusEntity.getSurEnvironmentFileId());
					equPosOperationalStatusEntity.setSurEnvironmentFileName(equPosBgOperationStatusEntity.getSurEnvironmentFileName());
					equPosOperationalStatusEntity.setSurEnvironmentFilePath(equPosBgOperationStatusEntity.getSurEnvironmentFilePath());
					equPosOperationalStatusEntity.setDoorPlateFileId(equPosBgOperationStatusEntity.getDoorPlateFileId());
					equPosOperationalStatusEntity.setDoorPlateFileName(equPosBgOperationStatusEntity.getDoorPlateFileName());
					equPosOperationalStatusEntity.setDoorPlateFilePath(equPosBgOperationStatusEntity.getDoorPlateFilePath());
					equPosOperationalStatusEntity.setReceptionFileId(equPosBgOperationStatusEntity.getReceptionFileId());
					equPosOperationalStatusEntity.setReceptionFileName(equPosBgOperationStatusEntity.getReceptionFileName());
					equPosOperationalStatusEntity.setReceptionFilePath(equPosBgOperationStatusEntity.getReceptionFilePath());
					equPosOperationalStatusEntity.setCompanyAreaFileId(equPosBgOperationStatusEntity.getCompanyAreaFileId());
					equPosOperationalStatusEntity.setCompanyAreaFileName(equPosBgOperationStatusEntity.getCompanyAreaFileName());
					equPosOperationalStatusEntity.setCompanyAreaFilePath(equPosBgOperationStatusEntity.getCompanyAreaFilePath());
					equPosOperationalStatusEntity.setOfficeSpaceFileId(equPosBgOperationStatusEntity.getOfficeSpaceFileId());
					equPosOperationalStatusEntity.setOfficeSpaceFileName(equPosBgOperationStatusEntity.getOfficeSpaceFileName());
					equPosOperationalStatusEntity.setOfficeSpaceFilePath(equPosBgOperationStatusEntity.getOfficeSpaceFilePath());
					equPosOperationalStatusEntity.setEmployeeProfileFileId(equPosBgOperationStatusEntity.getEmployeeProfileFileId());
					equPosOperationalStatusEntity.setEmployeeProfileFileName(equPosBgOperationStatusEntity.getEmployeeProfileFileName());
					equPosOperationalStatusEntity.setEmployeeProfileFilePath(equPosBgOperationStatusEntity.getEmployeeProfileFilePath());
					equPosOperationalStatusEntity.setOperatorUserId(userId);
					equPosOperationalStatusEntity_HI.saveEntity(equPosOperationalStatusEntity);
					count++;
				}
			}
			if(count == 0){
				//新增供应商经营状况
				EquPosOperationalStatusEntity_HI entity = new EquPosOperationalStatusEntity_HI();
				entity.setSurroundingEnvironment(equPosBgOperationStatusEntity.getSurroundingEnvironment());
				entity.setDoorPlate(equPosBgOperationStatusEntity.getDoorPlate());
				entity.setReception(equPosBgOperationStatusEntity.getReception());
				entity.setCompanyArea(equPosBgOperationStatusEntity.getCompanyArea());
				entity.setOfficeSpace(equPosBgOperationStatusEntity.getOfficeSpace());
				entity.setEmployeeProfile(equPosBgOperationStatusEntity.getEmployeeProfile());
				entity.setMajorCustomers(equPosBgOperationStatusEntity.getMajorCustomers());
				entity.setSaleChannel(equPosBgOperationStatusEntity.getSaleChannel());
				entity.setSurEnvironmentFileId(equPosBgOperationStatusEntity.getSurEnvironmentFileId());
				entity.setSurEnvironmentFileName(equPosBgOperationStatusEntity.getSurEnvironmentFileName());
				entity.setSurEnvironmentFilePath(equPosBgOperationStatusEntity.getSurEnvironmentFilePath());
				entity.setDoorPlateFileId(equPosBgOperationStatusEntity.getDoorPlateFileId());
				entity.setDoorPlateFileName(equPosBgOperationStatusEntity.getDoorPlateFileName());
				entity.setDoorPlateFilePath(equPosBgOperationStatusEntity.getDoorPlateFilePath());
				entity.setReceptionFileId(equPosBgOperationStatusEntity.getReceptionFileId());
				entity.setReceptionFileName(equPosBgOperationStatusEntity.getReceptionFileName());
				entity.setReceptionFilePath(equPosBgOperationStatusEntity.getReceptionFilePath());
				entity.setCompanyAreaFileId(equPosBgOperationStatusEntity.getCompanyAreaFileId());
				entity.setCompanyAreaFileName(equPosBgOperationStatusEntity.getCompanyAreaFileName());
				entity.setCompanyAreaFilePath(equPosBgOperationStatusEntity.getCompanyAreaFilePath());
				entity.setOfficeSpaceFileId(equPosBgOperationStatusEntity.getOfficeSpaceFileId());
				entity.setOfficeSpaceFileName(equPosBgOperationStatusEntity.getOfficeSpaceFileName());
				entity.setOfficeSpaceFilePath(equPosBgOperationStatusEntity.getOfficeSpaceFilePath());
				entity.setEmployeeProfileFileId(equPosBgOperationStatusEntity.getEmployeeProfileFileId());
				entity.setEmployeeProfileFileName(equPosBgOperationStatusEntity.getEmployeeProfileFileName());
				entity.setEmployeeProfileFilePath(equPosBgOperationStatusEntity.getEmployeeProfileFilePath());
				entity.setSupplierId(equPosBgOperationStatusEntity.getSupplierId());
				entity.setSupplierAddressId(equPosBgOperationStatusEntity.getSupplierAddressId());
				entity.setDeptCode(equPosBgOperationStatusEntity.getDeptCode());
				entity.setOperatorUserId(userId);
				equPosOperationalStatusEntity_HI.saveEntity(entity);
			}
		}

		for(int j = 0; j < equPosOperationalStatusEntityList.size(); j++){
			EquPosOperationalStatusEntity_HI equPosOperationalStatusEntity = equPosOperationalStatusEntityList.get(j);
			int count = 0;
			for(int i = 0; i < equPosBgOperationStatusEntityList.size(); i++){
				EquPosBgOperationStatusEntity_HI equPosBgOperationStatusEntity = equPosBgOperationStatusEntityList.get(i);
				if(equPosOperationalStatusEntity.getOperationalId().intValue() == (equPosBgOperationStatusEntity.getOperationalId() == null ? -1 : equPosBgOperationStatusEntity.getOperationalId().intValue())){
					count++;
				}
			}
			if(count == 0){
				//删除供应商产能信息
				equPosOperationalStatusEntity_HI.delete(equPosOperationalStatusEntity);
			}
		}

		//*****************************************************
		//供应商联系人更新                                       *
		//*****************************************************
		List<EquPosBgContactsEntity_HI> equPosBgContactsEntityList = equPosBgContactsEntity_HI.findByProperty("changeId",changeId);
		List<EquPosSupplierContactsEntity_HI> equPosSupplierContactsEntityList = equPosSupplierContactsEntity_HI.findByProperty("supplierId",supplierId);
		for(int i = 0; i < equPosBgContactsEntityList.size(); i++){
			EquPosBgContactsEntity_HI equPosBgContactsEntity = equPosBgContactsEntityList.get(i);
			int count = 0;
			for(int j = 0; j < equPosSupplierContactsEntityList.size(); j++){
				EquPosSupplierContactsEntity_HI equPosSupplierContactsEntity = equPosSupplierContactsEntityList.get(j);
				if(equPosSupplierContactsEntity.getSupplierContactId().intValue() == (equPosBgContactsEntity.getSupplierContactId() == null ? -1 : equPosBgContactsEntity.getSupplierContactId().intValue())){
					String sendEmailFlag = equPosSupplierContactsEntity.getSendEmailFlag() == null ? "" : equPosSupplierContactsEntity.getSendEmailFlag();
					String bgSendEmailFlag = equPosBgContactsEntity.getSendEmailFlag() == null ? "" : equPosBgContactsEntity.getSendEmailFlag();
					//修改供应商联系人
					EquPosSupplierContactsEntity_HI contactEntity = null;
					equPosSupplierContactsEntity.setContactName(equPosBgContactsEntity.getContactName());
					equPosSupplierContactsEntity.setMobilePhone(equPosBgContactsEntity.getMobilePhone());
					equPosSupplierContactsEntity.setFixedPhone(equPosBgContactsEntity.getFixedPhone());
					equPosSupplierContactsEntity.setEmailAddress(equPosBgContactsEntity.getEmailAddress());
					equPosSupplierContactsEntity.setPositionName(equPosBgContactsEntity.getPositionName());
					equPosSupplierContactsEntity.setRespCategory(equPosBgContactsEntity.getRespCategory());
					equPosSupplierContactsEntity.setRemark(equPosBgContactsEntity.getRemark());
					equPosSupplierContactsEntity.setSendEmailFlag(equPosBgContactsEntity.getSendEmailFlag());
					equPosSupplierContactsEntity.setOperatorUserId(userId);
					if(!sendEmailFlag.equals(bgSendEmailFlag)){
						try{
							contactEntity = generateSupplierAccount(equPosSupplierContactsEntity,equPosSupplierInfoEntityList.get(0).getSupplierNumber(),equPosSupplierInfoEntityList.get(0).getSupplierName(),"N");
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
					equPosSupplierContactsEntity_HI.saveEntity(contactEntity == null ? equPosSupplierContactsEntity : contactEntity);
					count++;
				}
			}
			if(count == 0){
				//新增供应商联系人
				EquPosSupplierContactsEntity_HI contactEntity = null;
				EquPosSupplierContactsEntity_HI entity = new EquPosSupplierContactsEntity_HI();
				entity.setSupplierId(equPosBgContactsEntity.getSupplierId());
				entity.setContactName(equPosBgContactsEntity.getContactName());
				entity.setMobilePhone(equPosBgContactsEntity.getMobilePhone());
				entity.setFixedPhone(equPosBgContactsEntity.getFixedPhone());
				entity.setEmailAddress(equPosBgContactsEntity.getEmailAddress());
				entity.setPositionName(equPosBgContactsEntity.getPositionName());
				entity.setRespCategory(equPosBgContactsEntity.getRespCategory());
				entity.setRemark(equPosBgContactsEntity.getRemark());
				entity.setSendEmailFlag(equPosBgContactsEntity.getSendEmailFlag());
				entity.setDeptCode(equPosBgContactsEntity.getDeptCode());
				entity.setOperatorUserId(userId);
				try{
					contactEntity = generateSupplierAccount(entity,equPosSupplierInfoEntityList.get(0).getSupplierNumber(),equPosSupplierInfoEntityList.get(0).getSupplierName(),"N");
				}catch(Exception ex){
					ex.printStackTrace();
				}
				equPosSupplierContactsEntity_HI.saveEntity(contactEntity);
			}
		}

		for(int j = 0; j < equPosSupplierContactsEntityList.size(); j++){
			EquPosSupplierContactsEntity_HI equPosSupplierContactsEntity = equPosSupplierContactsEntityList.get(j);
			int count = 0;
			for(int i = 0; i < equPosBgContactsEntityList.size(); i++){
				EquPosBgContactsEntity_HI equPosBgContactsEntity = equPosBgContactsEntityList.get(i);
				if(equPosSupplierContactsEntity.getSupplierContactId().intValue() == (equPosBgContactsEntity.getSupplierContactId() == null ? -1 : equPosBgContactsEntity.getSupplierContactId().intValue())){
					count++;
				}
			}
			if(count == 0){
				//删除供应商联系人
				try{
					generateSupplierAccount(equPosSupplierContactsEntity,equPosSupplierInfoEntityList.get(0).getSupplierNumber(),equPosSupplierInfoEntityList.get(0).getSupplierName(),"Y");
				}catch(Exception ex){
					ex.printStackTrace();
				}
				equPosSupplierContactsEntity_HI.delete(equPosSupplierContactsEntity);
			}
		}
	}

	/**
	 * 资质审查单据审批回调接口
	 * @param parseObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public EquPosSupplierChangeEntity_HI supplierArchivesChangeApprovalCallback(JSONObject parseObject, int userId) throws Exception {
		Integer headerId = parseObject.getIntValue("id");//单据Id
		EquPosSupplierChangeEntity_HI entityHeader = this.getById(headerId);
		Integer supplierId = entityHeader.getSupplierId();
		String orderStatus = null;//状态
		switch (parseObject.getString("status")) {
			case "REFUSAL":
				orderStatus = "40";
				break;
			case "ALLOW":
				orderStatus = "30";
				//变更后内容同步更新供应商档案
				updateSupplierArchives(headerId,supplierId,userId);
 				break;
			case "DRAFT":
				orderStatus = "10";
				break;
			case "APPROVAL":
				orderStatus = "20";
				break;
			case "CLOSED":
				orderStatus = "50";
				break;

		}

		//流程节点审批通过,邮件通知owner
		CommonUtils.processApprovalEmailToOwner(parseObject,entityHeader.getCreatedBy(),entityHeader.getChangeNumber());

		entityHeader.setStatus(orderStatus);
		entityHeader.setOperatorUserId(userId);
		this.saveOrUpdate(entityHeader);
		return entityHeader;
	}
}
