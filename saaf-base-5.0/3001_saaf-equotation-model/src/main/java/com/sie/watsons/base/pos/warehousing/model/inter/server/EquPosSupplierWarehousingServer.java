package com.sie.watsons.base.pos.warehousing.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.creditAudit.model.dao.EquPosSupplierCreditAuditDAO_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosSupplierCreditAuditEntity_HI;
import com.sie.watsons.base.pos.csrAudit.model.entities.EquPosSupplierCsrAuditEntity_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.*;
import com.sie.watsons.base.pos.qualityAudit.model.entities.EquPosSupplierQualityAuditEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.*;
import com.sie.watsons.base.pos.warehousing.model.entities.EquPosSupplierWarehousingEntity_HI;
import com.sie.watsons.base.pos.warehousing.model.entities.readonly.EquPosSupplierWarehousingEntity_HI_RO;
import com.sie.watsons.base.pos.warehousing.model.inter.IEquPosSupplierWarehousing;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("equPosSupplierWarehousingServer")
public class EquPosSupplierWarehousingServer extends BaseCommonServer<EquPosSupplierWarehousingEntity_HI> implements IEquPosSupplierWarehousing{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierWarehousingServer.class);

	@Autowired
	private ViewObject<EquPosSupplierWarehousingEntity_HI> equPosSupplierWarehousingDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierQualityAuditEntity_HI> equPosSupplierQualityAuditDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierInfoEntity_HI> equPosSupplierInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierProductCatEntity_HI> eqquPosSupplierProductCatDAO_HI;


	@Autowired
	private ViewObject<EquPosSupplierCsrAuditEntity_HI> equPosSupplierCsrAuditDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierWarehousingEntity_HI_RO> equPosSupplierWarehousingDAO_HI_RO;

	@Autowired
	private EquPosSupplierCreditAuditDAO_HI equPosSupplierCreditAuditDAO_HI;

	@Autowired
	private ViewObject<EquPosQualificationInfoEntity_HI> equPosQualificationInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscSupplierEntity_HI> equPosZzscSupplierDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscSuppDeptInfoEntity_HI> equPosZzscSuppDeptInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscCategoryEntity_HI> equPosZzscCategoryDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscContactsEntity_HI> equPosZzscContactsDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscAssociateSuppEntity_HI> equPosZzscAssociateSuppDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscBankEntity_HI> equPosZzscBankDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscCredentialsEntity_HI> equPosZzscCredentialsDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscCredentialAttachEntity_HI> equPosZzscCredentialAttachDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscAddressesEntity_HI> equPosZzscAddressesDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscOperationStatusEntity_HI> equPosZzscOperationStatusDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscProductionInfoEntity_HI> equPosZzscProductionInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscCapacityEntity_HI> equPosZzscCapacityDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierContactsEntity_HI> equPosSupplierContactsDAO_HI;

	@Autowired
	private ViewObject<EquPosAssociatedSupplierEntity_HI> equPosAssociatedSupplierDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierBankEntity_HI> equPosSupplierBankDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierCredentialsEntity_HI> equPosSupplierCredentialsDAO_HI;

	@Autowired
	private ViewObject<EquPosCredentialsAttachmentEntity_HI> equPosCredentialsAttachmentDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierAddressesEntity_HI> equPosSupplierAddressesDAO_HI;

	@Autowired
	private ViewObject<EquPosOperationalStatusEntity_HI> equPosOperationalStatusDAO_HI;

	@Autowired
	private ViewObject<EquPosProductionInfoEntity_HI> equPosProductionInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosCapacityInfoEntity_HI> equPosCapacityInfoDAO_HI;


	@Autowired
    private GenerateCodeServer generateCodeServer;

	public EquPosSupplierWarehousingServer() {
		super();
	}

	private  static String YES = "Y";
	private  static String NO = "N";

	@Override
	public Pagination<EquPosSupplierWarehousingEntity_HI_RO> findEquPosWarehousingInfo(String params, Integer pageIndex, Integer pageRows) {
		JSONObject jsonParam = JSONObject.parseObject(params);
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		StringBuffer queryString = new StringBuffer(
				EquPosSupplierWarehousingEntity_HI_RO.QUEERY_WARE_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(jsonParam, "T.sup_Warehousing_Id", "id", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "pi.supplier_Name", "supplierName", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "T.sup_Warehousing_Code", "supWarehousingCode", queryString, map, "like");
		SaafToolUtils.parperParam(jsonParam, "to_number(T.sup_Warehousing_Status)", "supWarehousingStatus", queryString, map, "=");
		SaafToolUtils.parperParam(jsonParam, "to_number(T.scene_type)", "supWarehousingType", queryString, map, "=");
		// 只有OEM才能查询
  		if(jsonParam.get("department")!=null && !"0E".equals(jsonParam.getString("department"))){
			queryString.append(" and 1 = 2");
		}
		JSONObject dateParam = new JSONObject();
		dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
		dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));
		SaafToolUtils.parperHbmParam(EquPosSupplierWarehousingEntity_HI_RO.class, dateParam, queryString, map);
		// 排序
		queryString.append(" order by t.creation_date desc");
		Pagination<EquPosSupplierWarehousingEntity_HI_RO> sceneManageList = equPosSupplierWarehousingDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

		return sceneManageList;
	}

	@Override
	public EquPosSupplierWarehousingEntity_HI_RO findSupWarehousingDatail(String params) {
		JSONObject jsonParam = JSONObject.parseObject(params);
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		StringBuffer queryString = new StringBuffer(EquPosSupplierWarehousingEntity_HI_RO.QUEERY_WARE_SQL);
		Map<String, Object> map = new HashMap<String, Object>();
		if(jsonParam.get("supWarehousingId")!=null){
			SaafToolUtils.parperParam(jsonParam, "T.sup_Warehousing_Id", "supWarehousingId", queryString, map, "=");
		}else if(jsonParam.get("id")!=null){
			SaafToolUtils.parperParam(jsonParam, "T.sup_Warehousing_Id", "id", queryString, map, "=");
		}else{
			queryString.append(" and 1 = 2");
		}
		EquPosSupplierWarehousingEntity_HI_RO  sceneManage = (EquPosSupplierWarehousingEntity_HI_RO)equPosSupplierWarehousingDAO_HI_RO.get(queryString, map);
		if("10".equals( sceneManage.getSupWarehousingStatus())){
			map = new HashMap();
			map.put("qualificationId",sceneManage.getQualificationId());
			List<EquPosSupplierCreditAuditEntity_HI> creditAuditEntityHiList = equPosSupplierCreditAuditDAO_HI.findList
					("FROM EquPosSupplierCreditAuditEntity_HI WHERE supCreditAuditStatus = '30' AND qualificationId = :qualificationId order by approveDate desc ",map);
			if(creditAuditEntityHiList.size()>0){
				sceneManage.setSupCreditAuditId(creditAuditEntityHiList.get(0).getSupCreditAuditId());
				String creditAuditResule =creditAuditEntityHiList.get(0).getCreditAuditResule();
				String specialResults =creditAuditEntityHiList.get(0).getSpecialResults();
				String results = "N";
				if("Y".equals(creditAuditResule)){
					results = "Y";
				}else if("N".equals(creditAuditResule)&&"Y".equals(creditAuditEntityHiList.get(0).getIsSpecial())){
					if("N".equals(specialResults)){
						results="NN";
					}else if("Y".equals(specialResults)){
						results="YN";
					}
				}
				sceneManage.setSupCreditAuditStatus(creditAuditEntityHiList.get(0).getSupCreditAuditStatus());
				sceneManage.setSupCreditAuditCode(creditAuditEntityHiList.get(0).getSupCreditAuditCode());
				sceneManage.setCreditAuditScore(creditAuditEntityHiList.get(0).getCreditAuditScore());
				sceneManage.setCreditAuditResule(results);
			}
//  			如果是全新制造工厂准入OEM,则需要添加最新质量审核和csr审核
			if("20".equals(sceneManage.getSceneType())||"40".equals(sceneManage.getSceneType())||"50".equals(sceneManage.getSceneType())){
				map = new HashMap<String, Object>();
				map.put("qualificationAuditNumber",sceneManage.getQualificationCode());
				List<EquPosSupplierQualityAuditEntity_HI> qualityAuditEntityHis = equPosSupplierQualityAuditDAO_HI.findList
						("FROM EquPosSupplierQualityAuditEntity_HI  WHERE qualityAuditStatus = 'APPROVAL' AND qualificationAuditNumber = :qualificationAuditNumber    order by lastUpdateDate desc ",map);
				if(qualityAuditEntityHis.size()>0){
					sceneManage.setSupQualityAuditId(qualityAuditEntityHis.get(0).getQualityAuditId());
					sceneManage.setQualityAuditNumber(qualityAuditEntityHis.get(0).getQualityAuditNumber());
					sceneManage.setQualityAuditStatus(qualityAuditEntityHis.get(0).getQualityAuditStatus());
					sceneManage.setQualityAuditResult(qualityAuditEntityHis.get(0).getQualityAuditResult());
					if(qualityAuditEntityHis.get(0).getQualityAuditScore()!=null){
						sceneManage.setQualityAuditScore(qualityAuditEntityHis.get(0).getQualityAuditScore() );
					}
				}
				List<EquPosSupplierCsrAuditEntity_HI> csrAuditEntityHis = equPosSupplierCsrAuditDAO_HI.findList
						("FROM EquPosSupplierCsrAuditEntity_HI WHERE csrAuditStatus =  'APPROVAL' AND qualificationAuditNumber = :qualificationAuditNumber   order by lastUpdateDate desc ", map);
				if(csrAuditEntityHis.size()>0){
					sceneManage.setSupCsrAuditId(csrAuditEntityHis.get(0).getCsrAuditId());
					sceneManage.setCsrAuditNumber(csrAuditEntityHis.get(0).getCsrAuditNumber());
					sceneManage.setCsrAuditResult(csrAuditEntityHis.get(0).getCsrAuditResult());
					sceneManage.setCsrAuditStatus(csrAuditEntityHis.get(0).getCsrAuditStatus());
					sceneManage.setIsExemption(csrAuditEntityHis.get(0).getIsExemption());
					if(csrAuditEntityHis.get(0).getCsrAuditScore()!=null){
						sceneManage.setCsrAuditScore(csrAuditEntityHis.get(0).getCsrAuditScore().toString());
					}
				}
			}
		}
		return  sceneManage;
	}

	@Override
	public EquPosSupplierWarehousingEntity_HI saveSupWarehousingDatailSumbit(JSONObject jsonObject, int userId) {
		EquPosSupplierWarehousingEntity_HI creditAuditEntityHi;
		creditAuditEntityHi = equPosSupplierWarehousingDAO_HI.getById(jsonObject.getInteger("supWarehousingId"));
		creditAuditEntityHi.setOperatorUserId(userId);
		creditAuditEntityHi.setSupWarehousingStatus("20");
		equPosSupplierWarehousingDAO_HI.save(creditAuditEntityHi);
		return null;
	}

	@Override
	public JSONObject findSupWarehousingDatailLine(JSONObject jsonParam) {
		LOGGER.info("------jsonParam------" + jsonParam.toString());
		EquPosSupplierWarehousingEntity_HI_RO sceneManage = new EquPosSupplierWarehousingEntity_HI_RO();
		HashMap map = new HashMap();
		map.put("qualificationId", jsonParam.getInteger("qualificationId"));
		List<EquPosSupplierCreditAuditEntity_HI> creditAuditEntityHiList = equPosSupplierCreditAuditDAO_HI.findList
				("FROM EquPosSupplierCreditAuditEntity_HI WHERE supCreditAuditStatus = '30' AND qualificationId = :qualificationId order by approveDate desc ", map);
		if (creditAuditEntityHiList.size() > 0) {
			sceneManage.setSupCreditAuditId(creditAuditEntityHiList.get(0).getSupCreditAuditId());
			String creditAuditResule = creditAuditEntityHiList.get(0).getCreditAuditResule();
			String specialResults = creditAuditEntityHiList.get(0).getSpecialResults();
			String results = "N";
			System.out.println("1");
			if ("Y".equals(creditAuditResule)) {
				results = "Y";
			} else if ("N".equals(creditAuditResule) && "Y".equals(creditAuditEntityHiList.get(0).getIsSpecial())) {
				if ("N".equals(specialResults)) {
					results = "NN";
					System.out.println("1");
				} else if ("Y".equals(specialResults)) {
					results = "YN";
				}
			}
			sceneManage.setSupCreditAuditStatus(creditAuditEntityHiList.get(0).getSupCreditAuditStatus());
			sceneManage.setSupCreditAuditCode(creditAuditEntityHiList.get(0).getSupCreditAuditCode());
			sceneManage.setCreditAuditScore(creditAuditEntityHiList.get(0).getCreditAuditScore());
			sceneManage.setCreditAuditResule(results);
		}
//  			如果是全新制造工厂准入OEM,则需要添加最新质量审核和csr审核
		map = new HashMap<String, Object>();
		map.put("qualificationAuditNumber",jsonParam.getString("qualificationCode"));
		List<EquPosSupplierQualityAuditEntity_HI> qualityAuditEntityHis = equPosSupplierQualityAuditDAO_HI.findList
				("FROM EquPosSupplierQualityAuditEntity_HI  WHERE qualityAuditStatus = 'APPROVAL' AND qualificationAuditNumber = :qualificationAuditNumber    order by lastUpdateDate desc ", map);
		if (qualityAuditEntityHis.size() > 0) {
			sceneManage.setSupQualityAuditId(qualityAuditEntityHis.get(0).getQualityAuditId());
			sceneManage.setQualityAuditNumber(qualityAuditEntityHis.get(0).getQualityAuditNumber());
			sceneManage.setQualityAuditStatus(qualityAuditEntityHis.get(0).getQualityAuditStatus());
			System.out.println("1");
			sceneManage.setQualityAuditResult(qualityAuditEntityHis.get(0).getQualityAuditResult());
			if (qualityAuditEntityHis.get(0).getQualityAuditScore() != null) {
				sceneManage.setQualityAuditScore(qualityAuditEntityHis.get(0).getQualityAuditScore());
			}
		}
		List<EquPosSupplierCsrAuditEntity_HI> csrAuditEntityHis = equPosSupplierCsrAuditDAO_HI.findList
				("FROM EquPosSupplierCsrAuditEntity_HI WHERE csrAuditStatus =  'APPROVAL' AND qualificationAuditNumber = :qualificationAuditNumber   order by lastUpdateDate desc ", map);
		if (csrAuditEntityHis.size() > 0) {
			sceneManage.setSupCsrAuditId(csrAuditEntityHis.get(0).getCsrAuditId());
			sceneManage.setCsrAuditNumber(csrAuditEntityHis.get(0).getCsrAuditNumber());
			sceneManage.setCsrAuditResult(csrAuditEntityHis.get(0).getCsrAuditResult());
			System.out.println("1");
			sceneManage.setCsrAuditStatus(csrAuditEntityHis.get(0).getCsrAuditStatus());
			if (csrAuditEntityHis.get(0).getCsrAuditScore() != null) {
				sceneManage.setCsrAuditScore(csrAuditEntityHis.get(0).getCsrAuditScore().toString());
			}
			sceneManage.setIsExemption(csrAuditEntityHis.get(0).getIsExemption());
		}
		return JSONObject.parseObject(JSONObject.toJSONString(sceneManage));
	}

	@Override
    public EquPosSupplierWarehousingEntity_HI saveEquPosWarehousing(JSONObject jsonObject, int userId) throws Exception {
        EquPosSupplierWarehousingEntity_HI jsonEntityHi = JSON.parseObject(jsonObject.toJSONString(), EquPosSupplierWarehousingEntity_HI.class);
        EquPosSupplierWarehousingEntity_HI creditAuditEntityHi;
        String supWarehousingStatus = "10";
        if (jsonObject.get("supWarehousingId") != null) {
			creditAuditEntityHi = this.saveOrUpdate(jsonObject);
			EquPosSupplierWarehousingEntity_HI  warehousingEntityHi =  equPosSupplierWarehousingDAO_HI.getById(jsonObject.getInteger("supWarehousingId"));
			supWarehousingStatus = warehousingEntityHi.getSupWarehousingStatus();
//            creditAuditEntityHi = equPosSupplierWarehousingDAO_HI.getById(jsonObject.getInteger("supWarehousingId"));
//            PropertyUtils.copyProperties(creditAuditEntityHi, jsonEntityHi);
//            if(creditAuditEntityHi.getCreatedBy()==null){
//                creditAuditEntityHi.setCreatedBy(userId);
//                creditAuditEntityHi.setCreationDate(new Date());
//            }
        } else {
            creditAuditEntityHi = jsonEntityHi;
            String code = generateCodeServer.getSupplierSuspendCode("RKSH", 4, true, true);
            creditAuditEntityHi.setSupWarehousingCode(code);
            creditAuditEntityHi.setCreatedBy(userId);
            creditAuditEntityHi.setCreationDate(new Date());
//            新建需要各种判断
			//匹配最新的信用审核
			Map map = new HashMap();
			map.put("qualificationId",jsonObject.getInteger("qualificationId"));
			List<EquPosSupplierCreditAuditEntity_HI> creditAuditEntityHiList = equPosSupplierCreditAuditDAO_HI.findList
					("FROM EquPosSupplierCreditAuditEntity_HI WHERE supCreditAuditStatus = '30' AND qualificationId = :qualificationId order by approveDate desc ",map);
			if(creditAuditEntityHiList.size()>0){
				creditAuditEntityHi.setSupCreditAuditId(creditAuditEntityHiList.get(0).getSupCreditAuditId());
				creditAuditEntityHi.setSupCreditAuditStatus(creditAuditEntityHiList.get(0).getSupCreditAuditStatus());
				creditAuditEntityHi.setCreditAuditScore(creditAuditEntityHiList.get(0).getCreditAuditScore());
				String result = "N";
				String creditAuditResule =creditAuditEntityHiList.get(0).getCreditAuditResule();
				String specialResults =creditAuditEntityHiList.get(0).getSpecialResults();
				if(YES.equals(creditAuditResule)){
					result = YES;
				}else if(NO.equals(creditAuditResule)&&YES.equals(creditAuditEntityHiList.get(0).getIsSpecial())){
					if(NO.equals(specialResults)){
						result="NN";
					}else if(YES.equals(specialResults)){
						result="YN";
					}
				}
				creditAuditEntityHi.setCreditAuditResule(result);
			}
//  			如果是全新制造工厂准入OEM,则需要添加最新质量审核和csr审核
			if("20".equals(creditAuditEntityHi.getSceneType())){
				map = new HashMap<String, Object>();
				map.put("qualificationAuditNumber",jsonObject.getString("qualificationCode"));
				List<EquPosSupplierQualityAuditEntity_HI> qualityAuditEntityHis = equPosSupplierQualityAuditDAO_HI.findList
						("FROM EquPosSupplierQualityAuditEntity_HI WHERE qualityAuditStatus = 'APPROVAL' AND qualificationAuditNumber = :qualificationAuditNumber    order by qualityAuditDate desc ",map);
				if(qualityAuditEntityHis.size()>0){
					creditAuditEntityHi.setSupQualityAuditId(qualityAuditEntityHis.get(0).getQualityAuditId());
					creditAuditEntityHi.setQualityAuditStatus(qualityAuditEntityHis.get(0).getQualityAuditStatus());
					creditAuditEntityHi.setQualityAuditResult(qualityAuditEntityHis.get(0).getQualityAuditResult());
					creditAuditEntityHi.setQualityAuditScore(qualityAuditEntityHis.get(0).getQualityAuditScore());
				}
				List<EquPosSupplierCsrAuditEntity_HI> csrAuditEntityHis = equPosSupplierCsrAuditDAO_HI.findList
						("FROM EquPosSupplierCsrAuditEntity_HI WHERE csrAuditStatus =  'APPROVAL' AND qualificationAuditNumber = :qualificationAuditNumber   order by lastUpdateDate desc ", map);
				if(csrAuditEntityHis.size()>0){
					creditAuditEntityHi.setSupCsrAuditId(csrAuditEntityHis.get(0).getCsrAuditId());
					creditAuditEntityHi.setCsrAuditResult(csrAuditEntityHis.get(0).getCsrAuditResult());
					creditAuditEntityHi.setCsrAuditStatus(csrAuditEntityHis.get(0).getCsrAuditStatus());
					creditAuditEntityHi.setCsrAuditScore(csrAuditEntityHis.get(0).getCsrAuditScore());
				}
			}
        }
        String action = jsonObject.getString("action");
		switch (action) {

			case "approve":
				creditAuditEntityHi.setSupWarehousingStatus("30");
				//修改供应商状态和 供应商资质状态
				Integer supplierId = creditAuditEntityHi.getSupplierId();
				Integer qualificationId = creditAuditEntityHi.getQualificationId();
				EquPosSupplierInfoEntity_HI supplierInfoEntityHi = equPosSupplierInfoDAO_HI.getById(supplierId);
				Map map = new HashMap();
				map.put("supplierId",supplierId);
				List<EquPosSuppInfoWithDeptEntity_HI> withDeptEntityHi = equPosSuppInfoWithDeptDAO_HI.findList("from EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId and " +
						" deptCode = '0E'",map);
				List<EquPosSuppInfoWithDeptEntity_HI> withDeptList = new ArrayList<>();
				supplierInfoEntityHi.setSupplierStatus("QUALIFIED");
				supplierInfoEntityHi.setOperatorUserId(userId);
				for (EquPosSuppInfoWithDeptEntity_HI deptEntityHi : withDeptEntityHi) {
					deptEntityHi.setSupplierStatus("QUALIFIED");
					deptEntityHi.setOperatorUserId(userId);
					withDeptList.add(deptEntityHi);
				}
//				EquPosSuppInfoWithDeptEntity_HI
				Map newMap = new HashMap();
				newMap.put("supplierId",creditAuditEntityHi.getSupplierId());
				newMap.put("deptCode",creditAuditEntityHi.getDeptCode());
				List<EquPosSupplierProductCatEntity_HI> catEntity = eqquPosSupplierProductCatDAO_HI.findList("from EquPosSupplierProductCatEntity_HI where supplierId = :supplierId  and  deptCode= :deptCode",newMap);
				for (EquPosSupplierProductCatEntity_HI catEntity_hi : catEntity) {
					catEntity_hi.setStatus("QUALIFIED");
					catEntity_hi.setOperatorUserId(userId);
				}
				eqquPosSupplierProductCatDAO_HI.save(catEntity);
				equPosSupplierInfoDAO_HI.save(supplierInfoEntityHi);
				equPosSuppInfoWithDeptDAO_HI.saveAll(withDeptList);
				break;
			case "cancel":creditAuditEntityHi.setSupWarehousingStatus("50");break;
			case "reject":creditAuditEntityHi.setSupWarehousingStatus("40");break;
		}
		if(((!"10".equals(supWarehousingStatus)&&!"40".equals(supWarehousingStatus))&&("save".equals(action)))){
			throw new IllegalArgumentException("单据不是拟定或者驳回状态无法操作.");
		}
        creditAuditEntityHi.setOperatorUserId(userId);
        equPosSupplierWarehousingDAO_HI.save(creditAuditEntityHi);
        return creditAuditEntityHi;
    }

	@Override
	public Integer deleteSupplierWarehousing(JSONObject jsonObject, int userId) {
		if (jsonObject.get("supWarehousingId") != null) {
			EquPosSupplierWarehousingEntity_HI creditAuditEntityHi = equPosSupplierWarehousingDAO_HI.getById(jsonObject.getInteger("supWarehousingId"));
			equPosSupplierWarehousingDAO_HI.delete(creditAuditEntityHi);
		}

		//单据删除时，判断单据状态如果为驳回，则查询单据的流程id，返回前端，用于终止流程
		String supWarehousingStatus = jsonObject.getString("supWarehousingStatus");
		String supWarehousingCode = jsonObject.getString("supWarehousingCode");
		if("40".equals(supWarehousingStatus)){
			//查询流程信息，提取流程id
			JSONObject b = new JSONObject();
			b.put("code", supWarehousingCode);
			JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
			//根据流程id，终止流程
			Integer listId = resultJSON.getInteger("listId");
			return listId;
		}
		return null;
	}

	@Override
	public EquPosSupplierWarehousingEntity_HI updateWarehousingCallback(JSONObject paramsObject, int userId) {
		Integer headerId = paramsObject.getIntValue("id");//单据Id
		EquPosSupplierWarehousingEntity_HI entityHeader = this.getById(headerId);
		EquPosQualificationInfoEntity_HI qualificationEntity = equPosQualificationInfoDAO_HI.getById(entityHeader.getQualificationId());
		String orderStatu = null;//状态
		switch (paramsObject.getString("status")) {
			case "REFUSAL":
				orderStatu = "40";
				break;
			case "ALLOW":
				if(!"50".equals(qualificationEntity.getSceneType())){
					//修改供应商状态和 供应商资质状态
					Integer supplierId = entityHeader.getSupplierId();
					List<EquPosSupplierInfoEntity_HI> supplierInfoList = equPosSupplierInfoDAO_HI.findByProperty("supplierId",supplierId);
					EquPosSupplierInfoEntity_HI supplierInfoEntityHi = supplierInfoList.get(0);
					Map map = new HashMap();
					map.put("supplierId",supplierId);
					List<EquPosSuppInfoWithDeptEntity_HI> withDeptEntityHi = equPosSuppInfoWithDeptDAO_HI.findList("from EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId and " +
							" deptCode = '" + qualificationEntity.getDeptCode() + "'",map);
					List<EquPosSuppInfoWithDeptEntity_HI> withDeptList = new ArrayList<>();
					supplierInfoEntityHi.setSupplierStatus("QUALIFIED");
					supplierInfoEntityHi.setOperatorUserId(userId);
					for (EquPosSuppInfoWithDeptEntity_HI deptEntityHi : withDeptEntityHi) {
						deptEntityHi.setSupplierStatus("QUALIFIED");
						deptEntityHi.setOperatorUserId(userId);
						withDeptList.add(deptEntityHi);
					}
					Map newMap = new HashMap();
					newMap.put("supplierId",entityHeader.getSupplierId());
					newMap.put("deptCode",entityHeader.getDeptCode());
					List<EquPosSupplierProductCatEntity_HI> catEntity = eqquPosSupplierProductCatDAO_HI.findList("from EquPosSupplierProductCatEntity_HI where supplierId = :supplierId  and  deptCode= :deptCode",newMap);
					for (EquPosSupplierProductCatEntity_HI catEntity_hi : catEntity) {
						catEntity_hi.setStatus("QUALIFIED");
						catEntity_hi.setOperatorUserId(userId);
					}
					eqquPosSupplierProductCatDAO_HI.save(catEntity);
					equPosSupplierInfoDAO_HI.save(supplierInfoEntityHi);
					equPosSuppInfoWithDeptDAO_HI.saveAll(withDeptList);
				}else{
					supplierInWarehouse(entityHeader);
				}

				entityHeader.setApproveDate(new Date());
				orderStatu = "30";
				break;
			case "DRAFT":
				orderStatu = "10";
				break;
			case "APPROVAL":
				orderStatu = "20";
				break;
			case "CLOSED":
				orderStatu = "50";
				break;
		}

		//流程节点审批通过,邮件通知owner
		CommonUtils.processApprovalEmailToOwner(paramsObject,entityHeader.getCreatedBy(),entityHeader.getSupWarehousingCode());

		entityHeader.setSupWarehousingStatus(orderStatu);
		entityHeader.setOperatorUserId(userId);
		this.saveOrUpdate(entityHeader);
		return entityHeader;
	}



	//审批通过，生产供应商档案数据
	public void supplierInWarehouse(EquPosSupplierWarehousingEntity_HI entityHeader){
		Integer supplierId = entityHeader.getSupplierId();
		Integer qualificationId = entityHeader.getQualificationId();
		String deptCode = "0E";
		Map queryMap1 = new HashMap();
		queryMap1.put("supplierId",supplierId);
		Map queryMap2 = new HashMap();
		queryMap2.put("supplierId",supplierId);
		queryMap2.put("deptCode",deptCode);
		Map queryMap3 = new HashMap();
		queryMap3.put("supplierId",supplierId);
		queryMap3.put("deptmentCode",deptCode);
		//查询资质审查
//		EquPosQualificationInfoEntity_HI qualificationEntity = equPosQualificationInfoDAO_HI.getById(qualificationId);

			//清空供应商档案数据
			List<EquPosSupplierInfoEntity_HI> delSupplierList = equPosSupplierInfoDAO_HI.findByProperty(queryMap1);
			equPosSupplierInfoDAO_HI.delete(delSupplierList);

			List<EquPosSuppInfoWithDeptEntity_HI> delSuppDeptInfoList = equPosSuppInfoWithDeptDAO_HI.findByProperty(queryMap2);
			equPosSuppInfoWithDeptDAO_HI.delete(delSuppDeptInfoList);

			List<EquPosSupplierProductCatEntity_HI> delCategoryList = eqquPosSupplierProductCatDAO_HI.findByProperty(queryMap2);
			eqquPosSupplierProductCatDAO_HI.delete(delCategoryList);

			List<EquPosSupplierContactsEntity_HI> delContactsList = equPosSupplierContactsDAO_HI.findByProperty(queryMap2);
			equPosSupplierContactsDAO_HI.delete(delContactsList);

			List<EquPosAssociatedSupplierEntity_HI> delAssociateSuppList = equPosAssociatedSupplierDAO_HI.findByProperty(queryMap2);
			equPosAssociatedSupplierDAO_HI.delete(delAssociateSuppList);

			List<EquPosSupplierBankEntity_HI> delBankList = equPosSupplierBankDAO_HI.findByProperty(queryMap2);
			equPosSupplierBankDAO_HI.delete(delBankList);

			List<EquPosSupplierCredentialsEntity_HI> delCredentialsList = equPosSupplierCredentialsDAO_HI.findByProperty(queryMap2);
			equPosSupplierCredentialsDAO_HI.delete(delCredentialsList);

			List<EquPosCredentialsAttachmentEntity_HI> delCredentialAttachList = equPosCredentialsAttachmentDAO_HI.findByProperty(queryMap3);
			equPosCredentialsAttachmentDAO_HI.delete(delCredentialAttachList);

			List<EquPosSupplierAddressesEntity_HI> delAddressesList = equPosSupplierAddressesDAO_HI.findByProperty(queryMap2);
			for(int i = 0; i < delAddressesList.size(); i++){
				EquPosSupplierAddressesEntity_HI delEntity = delAddressesList.get(i);
				Map map = new HashMap();
				map.put("supplierAddressId",delEntity.getSupplierAddressId());
				map.put("supplierId",supplierId);
				List<EquPosOperationalStatusEntity_HI> delOperationStatusList = equPosOperationalStatusDAO_HI.findByProperty(map);
				equPosOperationalStatusDAO_HI.delete(delOperationStatusList);
				List<EquPosProductionInfoEntity_HI> delProductionInfoList = equPosProductionInfoDAO_HI.findByProperty(map);
				equPosProductionInfoDAO_HI.delete(delProductionInfoList);
				List<EquPosCapacityInfoEntity_HI> delCapacityList = equPosCapacityInfoDAO_HI.findByProperty(map);
				equPosCapacityInfoDAO_HI.delete(delCapacityList);
				equPosSupplierAddressesDAO_HI.delete(delEntity);
			}


			//查询EquPosZzscSupplierEntity_HI
			List<EquPosZzscSupplierEntity_HI> zzscSupplierList = equPosZzscSupplierDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscSuppDeptInfoEntity_HI
			List<EquPosZzscSuppDeptInfoEntity_HI> zzscSuppDeptInfoList = equPosZzscSuppDeptInfoDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscCategoryEntity_HI
			List<EquPosZzscCategoryEntity_HI> zzscCategoryList = equPosZzscCategoryDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscContactsEntity_HI
			List<EquPosZzscContactsEntity_HI> zzscContactsList = equPosZzscContactsDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscAssociateSuppEntity_HI
			List<EquPosZzscAssociateSuppEntity_HI> zzscAssociateSuppList = equPosZzscAssociateSuppDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscBankEntity_HI
			List<EquPosZzscBankEntity_HI> zzscBankList = equPosZzscBankDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscCredentialsEntity_HI
			List<EquPosZzscCredentialsEntity_HI> zzscCredentialsList = equPosZzscCredentialsDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscCredentialAttachEntity_HI
			List<EquPosZzscCredentialAttachEntity_HI> zzscCredentialAttachList = equPosZzscCredentialAttachDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscAddressesEntity_HI
			List<EquPosZzscAddressesEntity_HI> zzscAddressesList = equPosZzscAddressesDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscOperationStatusEntity_HI
			List<EquPosZzscOperationStatusEntity_HI> zzscOperationStatusList = equPosZzscOperationStatusDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscProductionInfoEntity_HI
			List<EquPosZzscProductionInfoEntity_HI> zzscProductionInfoList = equPosZzscProductionInfoDAO_HI.findByProperty("qualificationId",qualificationId);
			//查询EquPosZzscCapacityEntity_HI
			List<EquPosZzscCapacityEntity_HI> zzscCapacityList = equPosZzscCapacityDAO_HI.findByProperty("qualificationId",qualificationId);

			//生成EquPosSupplierInfoEntity_HI
			for(int i = 0; i < zzscSupplierList.size(); i++){
				EquPosZzscSupplierEntity_HI sourceEntity = zzscSupplierList.get(i);
				List<EquPosSupplierInfoEntity_HI> supplierList = equPosSupplierInfoDAO_HI.findByProperty("supplierId",supplierId);
				if(supplierList.size() == 0){
					EquPosSupplierInfoEntity_HI targetentity = new EquPosSupplierInfoEntity_HI();
					//拷贝属性
					try{
						copy(sourceEntity,targetentity);
						targetentity.setSupplierId(supplierId);
						targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
						equPosSupplierInfoDAO_HI.saveEntity(targetentity);
					}catch (Exception ex){
						ex.printStackTrace();
					}
				}
			}

			//生成EquPosSuppInfoWithDeptEntity_HI
			for(int i = 0; i < zzscSuppDeptInfoList.size(); i++){
				EquPosZzscSuppDeptInfoEntity_HI sourceEntity = zzscSuppDeptInfoList.get(i);
				EquPosSuppInfoWithDeptEntity_HI targetentity = new EquPosSuppInfoWithDeptEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					targetentity.setSupplierStatus("QUALIFIED");
					equPosSuppInfoWithDeptDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosSupplierProductCatEntity_HI
			for(int i = 0; i < zzscCategoryList.size(); i++){
				EquPosZzscCategoryEntity_HI sourceEntity = zzscCategoryList.get(i);
				EquPosSupplierProductCatEntity_HI targetentity = new EquPosSupplierProductCatEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					targetentity.setStatus("QUALIFIED");
					eqquPosSupplierProductCatDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosSupplierContactsEntity_HI
			for(int i = 0; i < zzscContactsList.size(); i++){
				EquPosZzscContactsEntity_HI sourceEntity = zzscContactsList.get(i);
				EquPosSupplierContactsEntity_HI targetentity = new EquPosSupplierContactsEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					equPosSupplierContactsDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosAssociatedSupplierEntity_HI
			for(int i = 0; i < zzscAssociateSuppList.size(); i++){
				EquPosZzscAssociateSuppEntity_HI sourceEntity = zzscAssociateSuppList.get(i);
				EquPosAssociatedSupplierEntity_HI targetentity = new EquPosAssociatedSupplierEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					equPosAssociatedSupplierDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosSupplierBankEntity_HI
			for(int i = 0; i < zzscBankList.size(); i++){
				EquPosZzscBankEntity_HI sourceEntity = zzscBankList.get(i);
				EquPosSupplierBankEntity_HI targetentity = new EquPosSupplierBankEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					equPosSupplierBankDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosSupplierCredentialsEntity_HI
			for(int i = 0; i < zzscCredentialsList.size(); i++){
				EquPosZzscCredentialsEntity_HI sourceEntity = zzscCredentialsList.get(i);
				EquPosSupplierCredentialsEntity_HI targetentity = new EquPosSupplierCredentialsEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					equPosSupplierCredentialsDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosCredentialsAttachmentEntity_HI
			for(int i = 0; i < zzscCredentialAttachList.size(); i++){
				EquPosZzscCredentialAttachEntity_HI sourceEntity = zzscCredentialAttachList.get(i);
				EquPosCredentialsAttachmentEntity_HI targetentity = new EquPosCredentialsAttachmentEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					equPosCredentialsAttachmentDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosSupplierAddressesEntity_HI
			for(int i = 0; i < zzscAddressesList.size(); i++){
				EquPosZzscAddressesEntity_HI sourceEntity = zzscAddressesList.get(i);
				EquPosSupplierAddressesEntity_HI targetentity = new EquPosSupplierAddressesEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					equPosSupplierAddressesDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosOperationalStatusEntity_HI
			for(int i = 0; i < zzscOperationStatusList.size(); i++){
				EquPosZzscOperationStatusEntity_HI sourceEntity = zzscOperationStatusList.get(i);
				EquPosOperationalStatusEntity_HI targetentity = new EquPosOperationalStatusEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					equPosOperationalStatusDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosProductionInfoEntity_HI
			for(int i = 0; i < zzscProductionInfoList.size(); i++){
				EquPosZzscProductionInfoEntity_HI sourceEntity = zzscProductionInfoList.get(i);
				EquPosProductionInfoEntity_HI targetentity = new EquPosProductionInfoEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					equPosProductionInfoDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

			//生成EquPosCapacityInfoEntity_HI
			for(int i = 0; i < zzscCapacityList.size(); i++){
				EquPosZzscCapacityEntity_HI sourceEntity = zzscCapacityList.get(i);
				EquPosCapacityInfoEntity_HI targetentity = new EquPosCapacityInfoEntity_HI();
				//拷贝属性
				try{
					copy(sourceEntity,targetentity);
					targetentity.setOperatorUserId(sourceEntity.getCreatedBy());
					equPosCapacityInfoDAO_HI.saveEntity(targetentity);
				}catch (Exception ex){
					ex.printStackTrace();
				}

		}
	}

	/**
	 * 复制源对象和目标对象的属性值
	 *
	 */
	public static void copy(Object source, Object target) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class sourceClass = source.getClass();//得到对象的Class
		Class targetClass = target.getClass();//得到对象的Class

		Field[] sourceFields = sourceClass.getDeclaredFields();//得到Class对象的所有属性
		Field[] targetFields = targetClass.getDeclaredFields();//得到Class对象的所有属性

		for(Field sourceField : sourceFields){
			String name = sourceField.getName();//属性名
			Class type = sourceField.getType();//属性类型

			if(!"versionNum".equals(name)){
				String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);

				Method getMethod = sourceClass.getMethod("get" + methodName);//得到属性对应get方法

				Object value = getMethod.invoke(source);//执行源对象的get方法得到属性值

				for(Field targetField : targetFields){
					String targetName = targetField.getName();//目标对象的属性名

					if(targetName.equals(name)){
						Method setMethod = targetClass.getMethod("set" + methodName, type);//属性对应的set方法

						setMethod.invoke(target, value);//执行目标对象的set方法
					}
				}
			}
		}
	}
}
