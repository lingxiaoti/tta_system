package com.sie.watsons.base.ob.model.inter.server;

import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaDetailEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaSummaryEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmProjectProductDetailEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentInfoEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentInfo;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentQaSummary;
import com.sie.watsons.base.ob.model.inter.IPlmProjectProductDetail;
import com.sie.watsons.base.product.model.entities.PlmProductBarcodeTableEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductBarcodeTable;
import com.sie.watsons.base.product.services.PlmProductHeadService;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmDevelopmentInfoServer")
public class PlmDevelopmentInfoServer extends
		BaseCommonServer<PlmDevelopmentInfoEntity_HI> implements
		IPlmDevelopmentInfo {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDevelopmentInfoServer.class);
	@Autowired
	private ViewObject<PlmDevelopmentInfoEntity_HI> plmDevelopmentInfoDAO_HI;
	@Autowired
	private BaseViewObject<PlmDevelopmentInfoEntity_HI_RO> plmDevelopmentInfoDAO_HI_RO;
	@Autowired
	private ViewObject<PlmDevelopmentQaSummaryEntity_HI> plmDevelopmentQaSummaryDAO_HI;
	@Autowired
	private IPlmProjectProductDetail plmProjectProductDetailServer;
	@Autowired
	private GenerateCodeServer generateCodeServer;
	@Autowired
	private IPlmDevelopmentQaSummary plmDevelopmentQaSummaryServer;
	@Autowired
	private ViewObject<PlmProjectProductDetailEntity_HI> plmProjectProductDetailDAO_HI;
	@Autowired
	private ViewObject<PlmDevelopmentQaDetailEntity_HI> plmDevelopmentQaDetailDAO_HI;

	@Autowired
	private IPlmProductBarcodeTable plmProductBarcodeTableDao;

	private static final String DEVELOPING_STATUS = "DEVELOPING";

	public PlmDevelopmentInfoServer() {
		super();
	}

	@Override
	public Pagination<PlmDevelopmentInfoEntity_HI_RO> findPlmDevelopmentInfoInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmDevelopmentInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		String creator = queryParamJSON.getString("productCreator_like");
		if(creator!=null&&!creator.equals("")){
			sql.append(" AND lower(pdi.PRODUCT_CREATOR) LIKE '%"+creator.toLowerCase()+"%' ");
			queryParamJSON.remove("productCreator_like");
		}
		//productName_like
		String productName = queryParamJSON.getString("productName_like");
		if(productName!=null&&!productName.equals("")){
			sql.append(" AND lower(pdi.PRODUCT_NAME) LIKE '%"+productName.toLowerCase()+"%' ");
			queryParamJSON.remove("productName_like");
		}
		SaafToolUtils.parperHbmParam(PlmDevelopmentInfoEntity_HI_RO.class,
				queryParamJSON, sql, paramsMap);
		sql.append(" order by pdi.last_update_date desc");
		Pagination<PlmDevelopmentInfoEntity_HI_RO> pagination = plmDevelopmentInfoDAO_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public Pagination<PlmDevelopmentInfoEntity_HI_RO> findPlmDevelopmentInfoInfoNew(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmDevelopmentInfoEntity_HI_RO.QUERY_SQL_NEW);
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(PlmDevelopmentInfoEntity_HI_RO.class,
				queryParamJSON, sql, paramsMap);
//		sql.append(" order by pdi.last_update_date desc");
		Pagination<PlmDevelopmentInfoEntity_HI_RO> pagination = plmDevelopmentInfoDAO_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmDevelopmentInfoEntity_HI savePlmDevelopmentInfoInfo(
			JSONObject queryParamJSON) throws ServerException {
		PlmDevelopmentInfoEntity_HI entity = JSON.parseObject(
				queryParamJSON.toString(), PlmDevelopmentInfoEntity_HI.class);
		String barcodeType = entity.getBarcodeType();
		String barcode = entity.getBarcode();
		if (entity.getBarcodeType() != null && !barcodeType.equals("")
				&& entity.getBarcode() != null
				&& !entity.getBarcode().equals("")) {
			JSONObject ma = new JSONObject();
			ma.put("barcode", barcode);
			List<PlmProductBarcodeTableEntity_HI> barli = new ArrayList<PlmProductBarcodeTableEntity_HI>();

			try {
				if (!barcode.equals("88888888")) {
					barli = plmProductBarcodeTableDao.findList(ma);
				}
			} catch (Exception e) {
				throw new ServerException("barcode,格式错误!");
			}

			if (barli.size() > 0) {
				throw new ServerException("barcode已经存在!");
			} else {
				if (!barcodeType.equals("6") && !barcode.equals("88888888")) {
					boolean f = new PlmProductHeadService().isBarcode(
							barcodeType, barcode);
					if (f == false) {
						throw new ServerException("barcode,格式错误!");
					}
				}
			}
		}

		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		if (SaafToolUtils.isNullOrEmpty(entity.getProductCreator())) {
			entity.setProductCreator(queryParamJSON
					.getString("varUserFullName"));
		}
		if (SaafToolUtils.isNullOrEmpty(entity.getObId())) {
			entity.setObId(generateCodeServer.generateCode("OB",
					new SimpleDateFormat("yy"), 5));
		}
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getString("commandStatus"))) {
			entity.setDevelopmentBillStatus(queryParamJSON
					.getString("commandStatus"));
			if (queryParamJSON.getString("commandStatus").equals("COMPLETED")
					&& DEVELOPING_STATUS.equals(entity.getProductStatus())
					|| DEVELOPING_STATUS.equals(entity.getProductStatus())
					&& queryParamJSON.getString("commandStatus").equals(
							"TOBEADDED")) {
				entity.setProductStatus("TO_BE_INTRODUCED");
			} else if (queryParamJSON.getString("commandStatus").equals(
					"ABANDONED")) {
				entity.setProductStatus("ABANDONED");
			}
		}
		plmDevelopmentInfoDAO_HI.saveOrUpdate(entity);

		// 手工新增需在项目产品明细生成父单
		if (SaafToolUtils.isNullOrEmpty(entity.getPlmProjectProductDetailId())) {
			queryParamJSON.put("plmDevelopmentInfoId",
					entity.getPlmDevelopmentInfoId());
			queryParamJSON.put("obId", entity.getObId());
			JSONObject returnObject = createProjectProductDetail(queryParamJSON);
			entity.setPlmProjectProductDetailId(returnObject
					.getInteger("plmProjectProductDetailId"));
			plmDevelopmentInfoDAO_HI.saveOrUpdate(entity);
		} else {
			PlmProjectProductDetailEntity_HI productDetailEntity = plmProjectProductDetailDAO_HI
					.findByProperty("plmProjectProductDetailId",
							entity.getPlmProjectProductDetailId()).get(0);
			productDetailEntity.setOperatorUserId(queryParamJSON
					.getInteger("varUserId"));
			productDetailEntity.setProductBillStatus(entity
					.getDevelopmentBillStatus());
			productDetailEntity.setProductStatus(entity.getProductStatus());
			productDetailEntity.setProducerName(entity.getProducer());
			productDetailEntity.setProductName(entity.getProductName());
			plmProjectProductDetailDAO_HI.saveOrUpdate(productDetailEntity);
		}

		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getString("commandStatus"))) {
			queryParamJSON.put("plmDevelopmentInfoId",
					entity.getPlmDevelopmentInfoId());
			this.generateQaSummaryByCommand(queryParamJSON,
					queryParamJSON.getString("command"));
		}

		return entity;
	}

	// 生成项目产品明细单
	public JSONObject createProjectProductDetail(JSONObject queryParamJSON) {
		SaafToolUtils.validateJsonParms(queryParamJSON, "plmProjectId",
				"plmDevelopmentInfoId", "obId", "productName",
				"productCategory", "developmentBillStatus", "productStatus");
		Map<String, Object> validMap = new HashMap<>();
		validMap.put("plmProjectId", queryParamJSON.getInteger("plmProjectId"));
		validMap.put("productName", queryParamJSON.getString("productName"));
		List<PlmProjectProductDetailEntity_HI> dataList = plmProjectProductDetailDAO_HI
				.findByProperty(validMap);
		if (dataList.size() > 0) {
			throw new IllegalArgumentException("所属项目存在重复产品名称！");
		}
		PlmProjectProductDetailEntity_HI entity = JSON.parseObject(
				queryParamJSON.toString(),
				PlmProjectProductDetailEntity_HI.class);
		entity.setProductBillStatus(queryParamJSON
				.getString("developmentBillStatus"));
		entity.setProducerName(queryParamJSON.getString("producer"));
		entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		plmProjectProductDetailDAO_HI.saveOrUpdate(entity);
		queryParamJSON.put("plmProjectProductDetailId",
				entity.getPlmProjectProductDetailId());
		return queryParamJSON;
	}

	// 手工新增OB产品单回写到项目产品明细
	public void generateProjectProductDetail(
			PlmDevelopmentInfoEntity_HI entity, Integer userId) {
		JSONObject projectProductJson = new JSONObject();
		projectProductJson.put("varUserId", userId);
		JSONArray projectProductArray = new JSONArray();
		JSONObject projectProductDetail = new JSONObject();
		projectProductDetail.put("plmProjectId", entity.getPlmProjectId());
		projectProductDetail.put("plmDevelopmentInfoId",
				entity.getPlmDevelopmentInfoId());
		projectProductDetail.put("productName", entity.getProductName());
		projectProductDetail
				.put("productCategory", entity.getProductCategory());
		projectProductDetail.put("producerName", entity.getProducer());
		projectProductDetail.put("productBillStatus",
				entity.getDevelopmentBillStatus());
		projectProductDetail.put("productStatus", entity.getProductStatus());
		projectProductDetail.put("obId", entity.getObId());
		projectProductArray.add(projectProductDetail);
		projectProductJson.put("productDetailList", projectProductArray);
		List<PlmProjectProductDetailEntity_HI> dataArray = plmProjectProductDetailServer
				.savePlmProjectProductDetailInfo(projectProductJson);
		entity.setPlmProjectProductDetailId(dataArray.get(0)
				.getPlmProjectProductDetailId());
		plmDevelopmentInfoDAO_HI.saveOrUpdate(entity);
	}

	// 根据产品品类生成资质文件
	public void generateQaSummaryByCommand(JSONObject queryParamJSON,
			String command) {
		List<PlmDevelopmentQaSummaryEntity_HI> originalList = plmDevelopmentQaSummaryDAO_HI
				.findByProperty("plmDevelopmentInfoId",
						queryParamJSON.getInteger("plmDevelopmentInfoId"));

		// 清空旧有资质文件汇总与明细
		if (originalList.size() != 0
				&& !originalList.get(0).getProductCategory()
						.equals(queryParamJSON.getString("productCategory"))) {
			plmDevelopmentQaSummaryDAO_HI.fluch();
			plmDevelopmentQaSummaryDAO_HI.deleteAll(originalList);
			List<PlmDevelopmentQaDetailEntity_HI> detailArray = plmDevelopmentQaDetailDAO_HI
					.findByProperty("plmDevelopmentInfoId",
							queryParamJSON.getInteger("plmDevelopmentInfoId"));
			plmDevelopmentQaDetailDAO_HI.deleteAll(detailArray);
			plmDevelopmentQaSummaryServer
					.savePlmDevelopmentQaSummaryInfoByCategory(queryParamJSON);
		}

		if (originalList.size() == 0) {
			plmDevelopmentQaSummaryServer
					.savePlmDevelopmentQaSummaryInfoByCategory(queryParamJSON);
		}
	}

}
