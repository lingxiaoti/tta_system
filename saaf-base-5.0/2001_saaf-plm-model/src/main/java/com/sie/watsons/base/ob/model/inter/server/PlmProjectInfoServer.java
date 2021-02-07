package com.sie.watsons.base.ob.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmProjectExceptionEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmProjectInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmProjectProductDetailEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectInfoEntity_HI_RO;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectProductDetailEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.*;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component("plmProjectInfoServer")
public class PlmProjectInfoServer extends BaseCommonServer<PlmProjectInfoEntity_HI> implements IPlmProjectInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectInfoServer.class);
	@Autowired
	private ViewObject<PlmProjectInfoEntity_HI> plmProjectInfoDAO_HI;
	@Autowired
	private BaseViewObject<PlmProjectInfoEntity_HI_RO> plmProjectInfoDAO_HI_RO;
	@Autowired
	private GenerateCodeServer generateCodeServer;
	@Autowired
	private IPlmProjectProductDetail plmProjectProductDetailServer;
	@Autowired
	private IPlmProjectException plmProjectExceptionServer;
	@Autowired
	private IPlmDevelopmentInfo plmDevelopmentInfoServer;
	@Autowired
	private ViewObject<PlmDevelopmentInfoEntity_HI> plmDevelopmentInfoDAO_HI;
	@Autowired
	private ViewObject<PlmProjectProductDetailEntity_HI> plmProjectProductDetailDAO_HI;
	@Autowired
	private IPlmDevelopmentQaSummary plmDevelopmentQaSummaryServer;



	public PlmProjectInfoServer() {
		super();
	}


	@Override
	public Pagination<PlmProjectInfoEntity_HI_RO> findPlmProjectInfoInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmProjectInfoEntity_HI_RO.QUERY_SQL);
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("import"))){
			sql = new StringBuffer(PlmProjectInfoEntity_HI_RO.IMPORT_PROJECT_SQL);
		}
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))){
			sql = new StringBuffer(PlmProjectInfoEntity_HI_RO.REPORT_SQL);
		}
		Map<String, Object> paramsMap = new HashMap<>();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("projectRange_like"))){
			sql.append(" and (upper(ppi.PROJECT_RANGE) like upper('%"+queryParamJSON.getString("projectRange_like")+"%') OR lower(ppi.PROJECT_RANGE) like lower('%"+queryParamJSON.getString("projectRange_like")+"%'))");
			queryParamJSON.remove("projectRange_like");
		}
		String creator = queryParamJSON.getString("creatorName_like");
		if(creator!=null&&!creator.equals("")){
			sql.append(" AND lower(ppi.CREATOR_NAME) LIKE '%"+creator.toLowerCase()+"%' ");
			queryParamJSON.remove("creatorName_like");
		}
		SaafToolUtils.parperHbmParam(PlmProjectInfoEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" order by ppi.last_update_date desc");
		Pagination<PlmProjectInfoEntity_HI_RO> pagination = plmProjectInfoDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmProjectInfoEntity_HI savePlmProjectInfoInfo(JSONObject queryParamJSON) throws Exception {
		JSONObject currentObject = queryParamJSON;
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("data"))&&!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONObject("info"))){
			currentObject = queryParamJSON.getJSONObject("info").getJSONObject("headerData");
			queryParamJSON.put("productDetailList",queryParamJSON.getJSONArray("data"));
			queryParamJSON.put("certificate", queryParamJSON.getJSONObject("info").getString("certificate"));
		}
		PlmProjectInfoEntity_HI plmProjectInfoEntity_HI = JSON.parseObject(currentObject.toString(), PlmProjectInfoEntity_HI.class);
		plmProjectInfoEntity_HI.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		if(SaafToolUtils.isNullOrEmpty(plmProjectInfoEntity_HI.getCreatorName())) {
			plmProjectInfoEntity_HI.setCreatorName(queryParamJSON.getString("varUserFullName"));
		}
		if(SaafToolUtils.isNullOrEmpty(plmProjectInfoEntity_HI.getProjectNumber())){
			plmProjectInfoEntity_HI.setProjectNumber(generateCodeServer.generateCode("XM",new SimpleDateFormat("yy"),5));
		}
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("productDetailList"))){
			plmProjectInfoEntity_HI.setMultiProducer(getMultiProducer(queryParamJSON));
		}

		PlmProjectInfoEntity_HI resultData = plmProjectInfoDAO_HI.saveEntity(plmProjectInfoEntity_HI);
		queryParamJSON.put("plmProjectId",resultData.getPlmProjectId());

		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("data"))&&!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONObject("info"))) {
			queryParamJSON.put("supplierType",resultData.getSupplierType());
			queryParamJSON.put("supplierName",resultData.getSupplierName());
			queryParamJSON = checkProductDetailImportData(queryParamJSON);
			queryParamJSON.put("projectRange", resultData.getProjectRange());
		}

		this.saveRows(queryParamJSON, resultData.getBillStatus());
		return resultData;
	}

	/**
	 * 校验导入数据是否合规
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public JSONObject checkProductDetailImportData(JSONObject queryParamJSON) throws Exception{
		List<String> lookupParams = Arrays.asList("PLM_PROJECT_PRODUCT_CATEGORY");
		JSONObject lookupJson = ResultUtils.getReturnJson(lookupParams).get(0);
		//产品品类映射集
		Map<String, String> productCategoryMap = new HashMap<>(16);
		Map<String, String> productCategoryReturnMap = (Map)lookupJson;
		for(String str: productCategoryReturnMap.keySet()){
			productCategoryMap.put(productCategoryReturnMap.get(str), str);
		}

		StringBuilder stringBuilder = new StringBuilder("");
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("productDetailList"))) {
			for (int i = 0; i < queryParamJSON.getJSONArray("productDetailList").size(); i++) {
				JSONObject data = queryParamJSON.getJSONArray("productDetailList").getJSONObject(i);
				if(!SaafToolUtils.isNullOrEmpty(data.getString("producerName"))){
					stringBuilder.append(data.getString("producerName")).append(",");
				}
			}
		}
		int size = stringBuilder.length();
		String supplierNameList = "";
		if (size != 0) {
			supplierNameList = stringBuilder.substring(0,size-1);
		}
		JSONObject querySupplierParams = new JSONObject();
		querySupplierParams.put("certificate", queryParamJSON.getString("certificate"));
		querySupplierParams.put("supplierName_in", supplierNameList);
		querySupplierParams.put("supplierStatus", "QUALIFIED");
		querySupplierParams.put("supplierType", "20");
		querySupplierParams.put("deptCode", "0E");
		Map<Object, JSONArray> dataList = ResultUtils.queryEquotationSupplierInfo(querySupplierParams);
		Set<String> productNameSet = new HashSet<>();

		JSONArray errArray = new JSONArray();
		JSONArray returnArray = new JSONArray();

		for(int i = 0; i < queryParamJSON.getJSONArray("productDetailList").size(); i++){
			String errMsg = "";
			JSONObject data = queryParamJSON.getJSONArray("productDetailList").getJSONObject(i);
			if(!productCategoryMap.containsKey(data.getString("productCategoryName"))) {
				errMsg = "系统无此品类！";
			}
			else {
				data.put("productCategory", productCategoryMap.get(data.getString("productCategoryName")));
			}
			//供应商类型为贸易商时需填生产商
			if(SaafToolUtils.isNullOrEmpty(data.getString("producerName"))&&queryParamJSON.getString("supplierType").equals("TRADER")){
				errMsg += "生产商必填！";
			}
			if(productNameSet.contains(data.getString("productName"))){
				errMsg += "产品名重复！";
			}else {
				productNameSet.add(data.getString("productName"));
			}
			//生产商不一定有填写
			if(!SaafToolUtils.isNullOrEmpty(data.getString("producerName"))) {
				String[] producerList = data.getString("producerName").trim().replace("，", ",").split(",");
				String wrongProducer = "";
				boolean nonHeaderSupplier = true;
				for (String str : producerList) {
					if(str.equals(queryParamJSON.getString("supplierName"))){
						nonHeaderSupplier = false;
					}
					if (!dataList.containsKey(str)) {
						wrongProducer += str + ",";
					}
				}
				if(nonHeaderSupplier&&queryParamJSON.getString("supplierType").equals("PRODUCER")){
					data.put("producerName", data.getString("producerName").trim()+","+queryParamJSON.getString("supplierName"));
				}
				if (!wrongProducer.equals("")) {
					errMsg += "生产商：" + wrongProducer.substring(0, wrongProducer.length() - 1) + "不符规范！";
				}
			}
			else if(queryParamJSON.getString("supplierType").equals("PRODUCER")){
				data.put("producerName", queryParamJSON.getString("supplierName"));
			}
			data.put("productBillStatus","TODO");
			data.put("productStatus","TODO");
			if(!errMsg.equals("")){
				JSONObject errRow = new JSONObject();
				errRow.put("ERR_MESSAGE", errMsg);
				errRow.put("ROW_NUM",data.get("ROW_NUM"));
				errArray.add(errRow);
			}
			returnArray.add(data);
		}

		if(errArray.size()!=0){
			throw new IllegalStateException(errArray.toJSONString());
		}

		plmProjectProductDetailDAO_HI.deleteAll(plmProjectProductDetailDAO_HI.findByProperty("plmProjectId", queryParamJSON.getInteger("plmProjectId")));
		queryParamJSON.put("productDetailList",returnArray);

		return queryParamJSON;
	}

	//获取行表上所有生产商名
	public String getMultiProducer(JSONObject queryParamJSON){
		JSONArray dataList = queryParamJSON.getJSONArray("productDetailList");
		Set<String> producerNameSet = new HashSet<>();
//		String producerNameStr = "";
		for(int i = 0; i < dataList.size(); i++){
			JSONObject data = dataList.getJSONObject(i);
			if(SaafToolUtils.isNullOrEmpty(data.getString("producerName")))
				continue;
			String[] strList = data.getString("producerName").trim().split(",");
			for(String str: strList){
//				if(!producerNameSet.contains(str)){
//					producerNameStr += str + ",";
//				}
				producerNameSet.add(str);
			}
		}
		return producerNameSet.toString().replace(" ","").replace("[","]").replace("]","");
	}

	public void saveRows(JSONObject queryParamJSON, String commandStatus){
		queryParamJSON.put("commandStatus",commandStatus);
		//项目管理查询页作废，查出产品明细行
		if(commandStatus.equals("ABANDONED")&&SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("productDetailList"))){
			JSONObject findProductDetailParams = new JSONObject();
			findProductDetailParams.put("plmProjectId",queryParamJSON.getInteger("plmProjectId"));
			Pagination<PlmProjectProductDetailEntity_HI_RO> pagination = plmProjectProductDetailServer.findPlmProjectProductDetailInfo(findProductDetailParams,1,1000);
			List<PlmProjectProductDetailEntity_HI_RO> returnDataArray  = pagination.getData();
			queryParamJSON.put("productDetailList",JSONArray.parseArray(JSON.toJSONString(returnDataArray)));
		}
		//项目作废产品明细对应OB单作废
		if(commandStatus.equals("ABANDONED")){
			this.discardAllDevelopmentInfo(queryParamJSON);
		}
		//若后期更新了Range需更新到所有已生成的OB产品单头表数据
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("projectRange"))) {
			if (SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("originalRange")) && !SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("projectRange")) || !queryParamJSON.getString("originalRange").equals(queryParamJSON.getString("projectRange"))) {
				List<PlmDevelopmentInfoEntity_HI> dataList = plmDevelopmentInfoDAO_HI.findByProperty("plmProjectId", queryParamJSON.getInteger("plmProjectId"));
				for (PlmDevelopmentInfoEntity_HI data : dataList) {
					data.setBiddingCode(queryParamJSON.getString("biddingCode"));
					data.setProjectRange(queryParamJSON.getString("projectRange"));
				}
				plmDevelopmentInfoDAO_HI.saveOrUpdateAll(dataList);
			}
		}

		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("productDetailList"))) {
			Set<String> productNameSet = new HashSet<>();
			for (int i = 0; i < queryParamJSON.getJSONArray("productDetailList").size(); i++) {
				if(productNameSet.contains(queryParamJSON.getJSONArray("productDetailList").getJSONObject(i).getString("productName"))){
					throw new IllegalArgumentException("存在重复产品："+queryParamJSON.getJSONArray("productDetailList").getJSONObject(i).getString("productName")+"！");
				}else {
					productNameSet.add(queryParamJSON.getJSONArray("productDetailList").getJSONObject(i).getString("productName"));
				}
			}
		}

		List<PlmProjectProductDetailEntity_HI> productDetailList = plmProjectProductDetailServer.savePlmProjectProductDetailInfo(queryParamJSON);
		List<PlmProjectExceptionEntity_HI> productExceptionList = plmProjectExceptionServer.savePlmProjectExceptionInfo(queryParamJSON);
		//提交状态拆分产品明细生成OB产品开发单
		if(commandStatus.equals("SUBMITTED")&&SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("productDetailList").getJSONObject(0).getInteger("plmDevelopmentInfoId"))){
			this.generateObDevelopmentInfoWhenSubmitted(queryParamJSON);
			return;
		}
		//已提交状态下修改项目头 range、项目名称、招标编码，回写到已生成的OB产品开发单
		else if(commandStatus.equals("SUBMITTED")&&!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("productDetailList").getJSONObject(0).getInteger("plmDevelopmentInfoId"))){
			this.writeBackPlmDevelopmentInfo(queryParamJSON);
		}
	}

	//项目作废产品明细对应OB单作废
	public void discardAllDevelopmentInfo(JSONObject queryParamJSON){
		List<PlmDevelopmentInfoEntity_HI> dataList = plmDevelopmentInfoDAO_HI.findByProperty("plmProjectId", queryParamJSON.getInteger("plmProjectId"));
		for(PlmDevelopmentInfoEntity_HI data : dataList){
			data.setDevelopmentBillStatus("ABANDONED");
			data.setProductStatus("ABANDONED");
			plmDevelopmentInfoDAO_HI.saveOrUpdate(data);
		}
	}

	//已提交项目信息修改后回写到OB单
	public void writeBackPlmDevelopmentInfo(JSONObject queryParamJSON){
		JSONArray dataArray = queryParamJSON.getJSONArray("productDetailList");
		List<PlmProjectProductDetailEntity_HI> productDetailArray = JSON.parseArray(dataArray.toString(),PlmProjectProductDetailEntity_HI.class);
		for(PlmProjectProductDetailEntity_HI data: productDetailArray){
			PlmDevelopmentInfoEntity_HI needEntity = plmDevelopmentInfoDAO_HI.findByProperty("plmDevelopmentInfoId",data.getPlmDevelopmentInfoId()).get(0);
			needEntity.setProjectRange(queryParamJSON.getString("projectRange"));
			needEntity.setProjectName(queryParamJSON.getString("projectName"));
			needEntity.setBiddingCode(queryParamJSON.getString("biddingCode"));
			plmDevelopmentInfoDAO_HI.saveOrUpdate(needEntity);
		}
	}

	//提交状态拆分产品明细生成OB产品开发单
	public void generateObDevelopmentInfoWhenSubmitted(JSONObject queryParamJSON){

		plmProjectProductDetailDAO_HI.fluch();
		plmProjectProductDetailDAO_HI.fluch();
		List<PlmProjectProductDetailEntity_HI> dataList = plmProjectProductDetailDAO_HI.findByProperty("plmProjectId", queryParamJSON.getInteger("plmProjectId"));
		for(PlmProjectProductDetailEntity_HI data: dataList){

			PlmDevelopmentInfoEntity_HI saveData = new PlmDevelopmentInfoEntity_HI();
			saveData.setPlmProjectId(queryParamJSON.getInteger("plmProjectId"));
			saveData.setPlmProjectProductDetailId(data.getPlmProjectProductDetailId());
			saveData.setProjectRange(queryParamJSON.getString("projectRange"));
			saveData.setBiddingCode(queryParamJSON.getString("biddingCode"));
			saveData.setProjectName(queryParamJSON.getString("projectName"));
			saveData.setProductName(data.getProductName());
			saveData.setSupplierId(queryParamJSON.getInteger("supplierId"));
			saveData.setSupplierCode(queryParamJSON.getString("supplierCode"));
			saveData.setSupplierType(queryParamJSON.getString("supplierType"));
			saveData.setSupplierName(queryParamJSON.getString("supplierName"));
			saveData.setProducer(data.getProducerName());
			saveData.setDevelopmentBillStatus("DEVELOPING");
			saveData.setProductStatus("DEVELOPING");
			saveData.setObId(generateCodeServer.generateCode("OB",new SimpleDateFormat("yy"),5));
			saveData.setProductCategory(data.getProductCategory());
			saveData.setProjectCreator(queryParamJSON.getString("varUserFullName"));
			if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creatorName"))&&queryParamJSON.getString("creatorName").equals("E-quotation")){
				saveData.setProductCreator("E-quotation");
			}
			else {
				saveData.setProductCreator(queryParamJSON.getString("varUserFullName"));
			}
			saveData.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
			saveData.setSpecialApprovalFlag("N");
			plmDevelopmentInfoDAO_HI.saveOrUpdate(saveData);
			//生成产品资质文件明细
			JSONObject generateQaSummaryObject = new JSONObject();
			generateQaSummaryObject.put("plmProjectId", queryParamJSON.getInteger("plmProjectId"));
			generateQaSummaryObject.put("productCategory", saveData.getProductCategory());
			generateQaSummaryObject.put("plmDevelopmentInfoId",saveData.getPlmDevelopmentInfoId());
			plmDevelopmentQaSummaryServer.savePlmDevelopmentQaSummaryInfoByCategory(generateQaSummaryObject);
			data.setPlmDevelopmentInfoId(saveData.getPlmDevelopmentInfoId());
			data.setObId(saveData.getObId());
			data.setProductBillStatus("DEVELOPING");
			data.setProductStatus("DEVELOPING");
		}
		plmProjectProductDetailDAO_HI.saveOrUpdateAll(dataList);

	}

	@Override
	public void deletePlmProjectInfoInfo(JSONObject params){
		if(SaafToolUtils.isNullOrEmpty(params.getInteger("plmProjectId")))
			throw new IllegalArgumentException("未获取到项目信息！");
		Integer plmProjectId = params.getInteger("plmProjectId");
		plmProjectInfoDAO_HI.delete(JSON.toJavaObject(params,PlmProjectInfoEntity_HI.class));
		List<PlmProjectProductDetailEntity_HI> dataList = plmProjectProductDetailDAO_HI.findByProperty("plmProjectId", plmProjectId);
		plmProjectProductDetailDAO_HI.deleteAll(dataList);
	}



}
