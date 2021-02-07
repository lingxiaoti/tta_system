package com.sie.watsons.base.api.model.inter.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.api.model.entities.readonly.*;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.*;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBrandInfoEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmLocationListEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmMotherCompanyEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.*;
import com.sie.watsons.base.product.model.entities.readonly.*;
import com.sie.watsons.base.product.model.inter.IPlmProductObLicense;
import com.sie.watsons.base.product.services.WatonsBpmService;
import com.sie.watsons.base.productEco.model.entities.PlmProductDrugEcoEntity_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductHeadEcoEntity_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductPriceEcoEntity_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductSupplierInfoEcoEntity_HI;
import com.sie.watsons.base.productEco.model.inter.ICommProcessDeal;
import com.sie.watsons.base.productEco.utils.EcoUtils;
import com.sie.watsons.base.redisUtil.*;

import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.api.ConfigModel;
import com.sie.watsons.base.api.model.entities.PlmApiLogEntity_HI;
import com.sie.watsons.base.api.model.entities.PlmUdaAttributeEntity_HI;
import com.sie.watsons.base.api.model.inter.IPlmApi;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2019/12/13/013.
 */
@Component("plmApiServer")
public class PlmApiServer implements IPlmApi {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmApiServer.class);

	@Autowired
	private ViewObject<PlmProductHeadEntity_HI> plmProductHeadDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductHeadEntity_HI_RO> plmProductHeadDAO_HI_RO;
	@Autowired
	private ViewObject<PlmApiLogEntity_HI> plmApiLogDAO_HI;
	@Autowired
	private BaseViewObject<VmiShopEntity_HI_RO2> vmiShopEntity_HI_RO;
	@Autowired
	private ViewObject<PlmUdaAttributeEntity_HI> plmUdaAttributeDAO_HI;
	@Autowired
	private ViewObject<PlmProductUpdatepropertisEntity_HI> plmProductUpdatepropertisDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductUpdatepropertisEntity_HI_RO> plmProductUpdatepropertisDAO_HI_RO;
	@Autowired
	private ConfigModel configModel;
	@Autowired
	private ViewObject<PlmProductSupplierInfoEntity_HI> plmProductSupplierInfoDAO_HI;
	@Autowired
	private ViewObject<PlmSalesAreaRowEntity_HI> plmSalesAreaRowDAO_HI;
	@Autowired
	private ViewObject<PlmDeptListEntity_HI> plmDeptListDAO_HI;
	@Autowired
	private ViewObject<PlmDeptClassEntity_HI> plmDeptClassDAO_HI;
	@Autowired
	private ViewObject<PlmDeptSubclassEntity_HI> plmDeptSubclassDAO_HI;
	@Autowired
	private ViewObject<PlmLocationListEntity_HI> plmLocationListDAO_HI;
	@Autowired
	private ViewObject<PlmProductSupplierInfoEntity_HI> PlmProductSupplierInfoDAO_HI;
	@Autowired
	private BaseCommonDAO_HI<PlmProductDrugEntity_HI> plmProductDrugEntity_HI;
	// @Autowired
	// private PlmProductPriceServer plmProductPriceServer;
	@Autowired
	private ViewObject<PlmProductPriceEntity_HI> plmProductPriceServer;

	@Autowired
	private ViewObject<PlmProductPriceEntity_HI> plmProductPriceEntity_HI;
	@Autowired
	private BaseCommonDAO_HI<PlmProductBarcodeTableEntity_HI> plmProductBarcodeTableEntity_HI;
	@Autowired
	private BaseViewObject<VmiPromotionStoreEntity_HI_RO> vmiPromotionStorDAO_HI_RO;
	@Autowired
	private BaseViewObject<VmiPromotionItemEntity_HI_RO> vmiPromotionItemDAO_HI_RO;
	@Autowired
	private BaseViewObject<PlmProductObLicenseEntity_HI_RO> plmProductObLicenseEntity_HI_RO;
	@Autowired
	private BaseViewObject<PlmProductNewEmailEntity_HI_RO> productNewEmailEntityRO;
	@Autowired
	private ViewObject<PlmProductObLicenseEntity_HI> plmProductObLicenseEntity_HI;
	@Autowired
	private ViewObject<PlmProductSupplierplaceinfoEntity_HI> plmProductSupplierplaceinfoEntity_HI;
	@Autowired
	private ViewObject<PlmProductBarcodeTableEntity_HI> plmProductBarcodeTableDao;
	@Autowired
	@Lazy
	private IPlmProductObLicense plmProductObLicenseServer;
	@Autowired
	private ViewObject<PlmDevelopmentInfoEntity_HI> deveinfo;
	@Autowired
	private ViewObject<PlmProductQaFileEntity_HI> qaFileEntity;
	@Autowired
	private ViewObject<PlmSupplierQaDealerEntity_HI> dealerEntity;
	@Autowired
	private ViewObject<PlmSupplierQaBrandEntity_HI> brandEntity;
	@Value("${api.attachment.orderListURL:}")
	private String orderListURL;
	// @Lazy
	// private IPlmProductHead plmProductHeadServer;
	@Autowired
	private GenerateCodeService generateCodeService;
	private SimpleDateFormat requestDateFormat = new SimpleDateFormat(
			"yyyyMMddhhmm");
	private SimpleDateFormat requestKeyDateFormat = new SimpleDateFormat(
			"yyyyMMdd");
	@Autowired
	@Lazy
	private WatonsBpmService watonsBpmService;
	@Autowired
	private ICommProcessDeal commProcessDealServer;

	//修改单调用注入
	@Autowired
	private BaseCommonDAO_HI<PlmProductHeadEcoEntity_HI> plmProductHeadEcoEntity_HI;
	@Autowired
	private BaseCommonDAO_HI<PlmProductModifyCheckEntity_HI> plmProductModifyCheckEntity_HI;
	@Autowired
	private BaseCommonDAO_HI<PlmProductPriceEcoEntity_HI> plmProductPriceEcoEntity_HI;
	@Autowired
	private BaseCommonDAO_HI<PlmProductSupplierInfoEcoEntity_HI> plmProductSupplierInfoEcoEntity_HI;
	@Autowired
	private BaseCommonDAO_HI<PlmProductDrugEcoEntity_HI> plmProductDrugEcoEntity_HI;


	/**
	 * 新增ITEM返回接口
	 * 
	 * @param params
	 * @return
	 */
	@Override
	// public void addItemReturnMethod(String params) throws Exception {
	public void addItemReturnMethod(String params) throws Exception {
		// http://10.82.24.180/rms/newitem?date=20191030&request_id=all
		JSONObject jsonObject = JSONObject.parseObject(params);
		String param_url = configModel.getAddItemUrl();
		// String url = "http://10.82.24.180/rms/newitem?date="+
		// jsonObject.get("date") + "&request_id=all";
		String url = param_url + "?date=" + jsonObject.get("date")
				+ "&request_id=all";
		// JSONObject response = ResultUtils.doGet(configModel.getAddItemUrl(),
		JSONObject response = ResultUtils.doGet(url,
				JSONObject.parseObject(params));
		LOGGER.info(url + " 请求接口返回参数：" + response);

		JSONArray array = response.getJSONArray("data_set");
		if (array.size() > 0) {

			ResultUtils.getLookUpValue("PLM_PRODUCT_HEADQAFILETYE");
			ResultUtils.getLookUpValue("PLM_BRAND_QA_TYPE");
			List<PlmProductHeadEntity_HI> emailProductList = new ArrayList<>();
			for (int i = 0; i < array.size(); i++) {

				JSONObject jsonLine = (JSONObject) array.get(i);
//				if(!jsonLine.getString("request_id").startsWith("200610005")){
//					continue;
//				}
				SaafToolUtils.validateJsonParms(jsonLine, "status",
						"request_id"); // 效验
				PlmApiLogEntity_HI apiLog = new PlmApiLogEntity_HI();
				Map<String, Object> apiMap = new HashMap<>();
				apiMap.put("requestId", jsonLine.getString("request_id"));
				String itemType= jsonLine.getString("item");
				String status= jsonLine.getString("status");
				if("NewBrand".equals(itemType) || "N".equals(jsonLine.getString("status") )){
					continue;
				}
				List<PlmApiLogEntity_HI> plmApiLogs = plmApiLogDAO_HI
						.findByProperty(apiMap);
				if(CollectionUtils.isEmpty(plmApiLogs)){
					//排除中英文品牌信息，如果没有找到requestId
					continue;
				}
				apiLog = plmApiLogs.get(0);
				if("1".equals(apiLog.getReturnFlag())){
					//20200527 已经处理过的数据直接跳过
					LOGGER.info( " 请求requestId：" + apiLog.getRequestId()+",plmCode:"+apiLog.getItem()+",已处理,跳过");
					continue;
				}
				Map<String, Object> proMap = new HashMap<>();
				proMap.put("plmCode", apiLog.getItem());
				List<PlmProductHeadEntity_HI> productHeads = plmProductHeadDAO_HI
						.findByProperty(proMap);
				PlmProductHeadEntity_HI product = productHeads.get(0);

				if (!"Y".equals(jsonLine.getString("status"))) {
					if("N".equals(jsonLine.getString("status"))){
						//20200527 RMS 暂时未处理直接跳过改条数据
						continue;
					}
					// 测试
					plmProductHeadDAO_HI
							.executeSqlUpdate("update plm_product_head t set "
									+ " t.rms_remake ="
									+ "'"
									+ jsonLine.getString("remarks").replace(
									"'", "")
									+ "', "
									+ " t.order_status = '7' "
									+ ", "
									+ " t.rms_status ="
									+ "'"
									+ jsonLine.getString("status")
									+ "' "
									+ "where t.plm_code in  (select pal.item from plm_api_log  pal where 1=1 and pal.request_id ='"
									+ jsonLine.getString("request_id") + "'  and nvl(pal.RETURN_FLAG , 0) = '0' )"
									+ " and nvl(t.rms_status,'F') !='Y'");
					// 驳回
					if (product != null) {
//						this.ProcessDeal(product, "Reject",
//								jsonLine.getString("remarks").replace("'", ""));
						commProcessDealServer.ProcessDeal(product.getAddProcessname(), "Reject",
								jsonLine.getString("remarks").replace("'", ""),"商品新增流程");

					}
					apiLog.setRemark("RMS同步失败");
					apiLog.setStatus(jsonLine.getString("status"));
					apiLog.setReturnFlag("1");
					plmApiLogDAO_HI.saveOrUpdate(apiLog);
					continue;
				}
				plmProductHeadDAO_HI
						.executeSqlUpdate(" update plm_product_head t set "
								+ " t.rms_code ="
								+ "'"
								+ jsonLine.getString("item")
								+ "', "
								+ " t.order_status = '8' "
								+ " , "
								+ " t.RMS_SYNCHR_DATE = SYSDATE"
								+ " , "
								+ " t.rms_remake ="
								+ "'"
								+ jsonLine.getString("remarks")
								+ "', "
								+ " t.rms_status ="
								+ "'"
								+ jsonLine.getString("status")
								+ "' "
								+ " where t.plm_code in  (select pal.item from plm_api_log pal where 1=1 and pal.request_id ='"
								+ jsonLine.getString("request_id")
								+ "' and nvl(pal.RETURN_FLAG , 0) = '0' )"
								+ "  and nvl(t.rms_status,'F') != 'Y'");

				// 同步到下游表plm_product_supplier_info 和
				// plm_product_supplierplaceinfo 20200511
				// 审批成功
				if (product != null) {
//					this.ProcessDeal(product, "Approval", "RMS同步成功");
					commProcessDealServer.ProcessDeal(product.getAddProcessname(), "Approval", "RMS同步成功","商品新增流程");
				}

				updateProductHeadLineByRmsCode(jsonLine.getString("item"),
						product);



				// 商品ID OB产品证照 3,正书下载
				if ("Y".equals(jsonLine.getString("status"))
						&& !"1".equals(apiLog.getReturnFlag())) {
					// todo: 区分OB 与 非OB

					emailProductList.add(product);
					if (ObjectUtils.isEmpty(product.getObId())) {
						// todo:成功的商品需要存 非OB
						generateObLicenseByRequestId(
								jsonLine.getString("request_id"),
								PlmProductObLicenseEntity_HI_RO.SqlByRequest,
								"2");
						// todo:成功的商品需要存 品牌证书下载
						generateObLicenseByRequestId(
								jsonLine.getString("request_id"),
								PlmProductObLicenseEntity_HI_RO.SqlByBrand, "3");
						// todo:成功的商品需要存 生产和经销商 证书下载
						generateObLicenseByRequestId(
								jsonLine.getString("request_id"),
								PlmProductObLicenseEntity_HI_RO.SqlByDealer,
								"3");
					} else {
						// todo:含有ObId的Ob商品
						dealObProduct(product);
					}
					// 更新返回使用标识
					apiLog.setRemark("RMS同步成功");
					apiLog.setStatus(jsonLine.getString("status"));
					apiLog.setReturnFlag("1");
					plmApiLogDAO_HI.saveOrUpdate(apiLog);
				}
			}
			// 发送邮件
			if (CollectionUtils.isNotEmpty(emailProductList)) {
				toSendEmailforNew();
			}
		}
	}

	@Async
	@Override
	public void toSendEmailforNew() throws Exception {
//		List<PlmProductHeadEntity_HI> emailProductList = plmProductHeadDAO_HI.findPagination(PlmProductHeadEntity_HI_RO.QUERY4,new HashMap<>());
		StringBuffer sb = new StringBuffer(PlmProductHeadEntity_HI_RO.QUERY4);
		Pagination<PlmProductHeadEntity_HI_RO> findList = plmProductHeadDAO_HI_RO.findPagination(sb.toString(),SaafToolUtils.getSqlCountString(sb), null, 1, 5000);
		List<PlmProductHeadEntity_HI_RO> emailProductROList= findList.getData();
//		List<PlmProductHeadEntity_HI> emailProductList= new ArrayList<>();
//		BeanUtils.copyListProperties(emailProductROList,emailProductList);

		//根据货品ID查询 需要发送邮件的数据
        ResultUtils.getLookUpValue("PLM_NOTICE_EMAIL");
        List<Integer> phIds = emailProductROList.stream().map(p->p.getProductHeadId()).collect(Collectors.toList());
        //查询区域数据
		StringBuffer areaSql = new StringBuffer(PlmProductNewEmailEntity_HI_RO.QUERY_PLACE);
//		List<VmiShopEntity_HI_RO2> shops = vmiShopEntity_HI_RO.findList(areaSql.toString());

        StringBuffer buffer = new StringBuffer(PlmProductNewEmailEntity_HI_RO.QUERY);
        buffer.append(" and  pph.product_head_id in (''");
        if(CollectionUtils.isNotEmpty(phIds)){
            for (int i = 0; i <phIds.size(); i++) {
                buffer.append(","+phIds.get(i));
            }
        }
        buffer.append(")");
        Set<String> emails =   ResultConstant.PLM_NOTICE_EMAIL.keySet();
        List<String> ests = new ArrayList<>();
			for(String o :emails){
				ests.add(o);
			}
			Object list = productNewEmailEntityRO.findList(buffer.toString());
//			Map<String,String> areaListagg = CommonUtils.listToListAgg(shops);
//			String body = CommonUtils.newProductMailContent("PLM&RMS 货品新增完成邮件通知",(List<Map<String,Object>>) list,areaListagg);
		    String body = CommonUtils.newProductMailContentNew("PLM&RMS 货品新增完成邮件通知",(List<Map<String,Object>>) list);
		    //附件URL
//			String url = CommonUtils.createAttachment("PLM&RMS货品新增完成邮件通知",(List<Map<String,Object>>) list,areaListagg,orderListURL);
			String url = CommonUtils.createAttachmentNew("PLM&RMS货品新增完成邮件通知",(List<Map<String,Object>>) list,orderListURL);
			LOGGER.info(" 邮件内容：：：" + body);
//			EmailUtil.sendMailFromWatsons(ests.toArray(new String[ests.size()]),"PLM&RMS 货品新增完成邮件通知",body);
		    EmailUtil.sendMailFromWatsons(ests.toArray(new String[ests.size()]),"PLM&RMS 货品新增完成邮件通知",body,url);

	}

	/**
	 * 根据RMS编码 查询货品 并同步到下游表单中
	 * 
	 * @Date:2020-05-11
	 * @param rmsCode
	 * @param product
	 */
	private void updateProductHeadLineByRmsCode(String rmsCode, PlmProductHeadEntity_HI product) {
		//根据RmsCode 查询商品
//		Map<String,Object> map = new HashMap<>();
//		map.put("rmsCode",rmsCode);
//		List<PlmProductHeadEntity_HI> products = plmProductHeadDAO_HI.findByProperty(map);
//		if(CollectionUtils.isNotEmpty(products)){
//			for (PlmProductHeadEntity_HI product : products) {
				Map<String,Object> lineMap = new HashMap<>();
				lineMap.put("productHeadId",product.getProductHeadId());
				//查询供应商行表
				List<PlmProductSupplierInfoEntity_HI> suppers = plmProductSupplierInfoDAO_HI.findByProperty(lineMap);
				suppers= suppers.stream().filter(s->s.getRmsCode()==null||"".equals(s.getRmsCode())).collect(Collectors.toList());
				if(CollectionUtils.isNotEmpty(suppers)){
					for (PlmProductSupplierInfoEntity_HI supper : suppers) {
						supper.setRmsCode(rmsCode);
						plmProductSupplierInfoDAO_HI.saveOrUpdate(supper);
					}
				}
//                lineMap.put("productHeadId",product.getProductHeadId().toString());
//				  List<PlmProductSupplierplaceinfoEntity_HI> supperPlaces = plmProductSupplierplaceinfoEntity_HI.findByProperty(lineMap);
//				  supperPlaces= supperPlaces.stream().filter(sp->sp.getRmsId()==null || "".equals(sp.getRmsId())).collect(Collectors.toList());
//                supperPlaces= supperPlaces.stream().filter(sp->! rmsCode.equals(sp.getRmsId()==null?"":sp.getRmsId())).collect(Collectors.toList());

                plmProductSupplierplaceinfoEntity_HI
                        .executeSqlUpdate(" UPDATE plm_product_supplierplaceinfo psi\n" +
                                "    SET psi.rms_id           = '" + rmsCode + "'\n" +
								"       ,psi.last_update_date = SYSDATE \n" +
                                "       ,psi.status = '0' \n" +
                                "  WHERE psi.product_head_id = '" + product.getProductHeadId() + "'\n" +
                                "  and psi.rms_id is null ");

//			}
//		}



	}

	private void dealObProduct(PlmProductHeadEntity_HI productHead)
			throws Exception {
		JSONObject productparam = new JSONObject();
		productparam.put("productHeadId", productHead.getProductHeadId());
		List<PlmProductBarcodeTableEntity_HI> barcodelist = plmProductBarcodeTableDao
				.findByProperty(productparam);
		JSONObject objson = new JSONObject();
		objson.put("obId", productHead.getObId());
		// todo:生成 OB证照文件记录
		plmProductObLicenseServer.saveProductObLicenseByObId(objson);

		List<PlmDevelopmentInfoEntity_HI> li = deveinfo.findByProperty(objson);
		if (CollectionUtils.isNotEmpty(li)) {
			PlmDevelopmentInfoEntity_HI r = li.get(0);
			r.setProductStatus("COMPLETED");
			r.setDevelopmentBillStatus("COMPLETED");
			r.setProductNameCn(productHead.getProductName());
			r.setProductNameEn(productHead.getProductEname());
			if (barcodelist.size() > 0) {
				r.setBarcode(barcodelist.get(0).getBarcode());
			}
			r.setBrandCn(productHead.getBrandnameCn());
			r.setProductNo(productHead.getPlmCode());
			r.setStartDate(new Date());
			deveinfo.update(r);
		}

	}

	/**
	 * 生成记录文件
	 * 
	 * @param requestId
	 * @param sql
	 *            使用的SQL
	 * @param flag
	 *            生成的记录标识 1,OB 2,非OB 3,正书下载
	 **/
	private void generateObLicenseByRequestId(String requestId, String sql,
			String flag) {
		// 查询实体类
		StringBuffer sb = new StringBuffer(sql);
		sb.append("      AND pal.request_id = '" + requestId + "' ");
		List<PlmProductObLicenseEntity_HI_RO> licenseRos = plmProductObLicenseEntity_HI_RO
				.findList(sb.toString());
		// 打印 块码日志：
		LOGGER.info("QA资质文件块码：" + ResultConstant.PLM_PRODUCT_HEADQAFILETYE == null ? "PLM_PRODUCT_HEADQAFILETYE为空"
				: ResultConstant.PLM_PRODUCT_HEADQAFILETYE.toJSONString());
		LOGGER.info("QA品牌文件块码：" + ResultConstant.PLM_BRAND_QA_TYPE == null ? "PLM_BRAND_QA_TYPE为空"
				: ResultConstant.PLM_BRAND_QA_TYPE.toJSONString());

		if (CollectionUtils.isNotEmpty(licenseRos)) {
			for (PlmProductObLicenseEntity_HI_RO ro : licenseRos) {
				// todo:校验是否需要同步SPA
				if (!validProductData(ro)) {
					if ("PLM_PRODUCT_QA_FILE".equals(ro.getFromTable())) {
						PlmProductQaFileEntity_HI qa = qaFileEntity.getById(ro
								.getKeyId());
						qa.setIsSpa("2");
						qaFileEntity.saveOrUpdate(qa);
					} else if ("PLM_SUPPLIER_QA_BRAND"
							.equals(ro.getFromTable())) {
						PlmSupplierQaBrandEntity_HI brand = brandEntity
								.getById(ro.getKeyId());
						brand.setIsSpa("2");
						brandEntity.saveOrUpdate(brand);
					} else if ("PLM_SUPPLIER_QA_DEALER".equals(ro
							.getFromTable())) {
						PlmSupplierQaDealerEntity_HI dealer = dealerEntity
								.getById(ro.getKeyId());
						dealer.setIsSpa("2");
						dealerEntity.saveOrUpdate(dealer);
					}
					continue;
				}
				PlmProductObLicenseEntity_HI entity = new PlmProductObLicenseEntity_HI();
				BeanUtils.copyProperties(ro, entity);
				entity.setLastUpdateDate(new Date());
				entity.setCreationDate(new Date());
				entity.setCreationDate(new Date());
				entity.setLastUpdatedBy(-999);
				entity.setCreatedBy(-999);
				entity.setCreatedBy(-999);
				entity.setVersionNum(0);
				entity.setFileFlag(flag);
				plmProductObLicenseEntity_HI.saveOrUpdate(entity);
				if ("PLM_PRODUCT_QA_FILE".equals(ro.getFromTable())) {
					PlmProductQaFileEntity_HI qa = qaFileEntity.getById(ro
							.getKeyId());
					qa.setIsSpa("1");
					qaFileEntity.saveOrUpdate(qa);
				} else if ("PLM_SUPPLIER_QA_BRAND".equals(ro.getFromTable())) {
					PlmSupplierQaBrandEntity_HI brand = brandEntity.getById(ro
							.getKeyId());
					brand.setIsSpa("1");
					brandEntity.saveOrUpdate(brand);
				} else if ("PLM_SUPPLIER_QA_DEALER".equals(ro.getFromTable())) {
					PlmSupplierQaDealerEntity_HI dealer = dealerEntity
							.getById(ro.getKeyId());
					dealer.setIsSpa("1");
					dealerEntity.saveOrUpdate(dealer);
				}

			}
		}
	}

	@Override
	public boolean validProductData(PlmProductObLicenseEntity_HI_RO ro) {
		// 校验数据是否需要上传; 是true 否false
		if ("0".equals(ro.getOtherInfo())) {
			// 普通商品
			// todo:判断商品来源类型
			if ("PLM_PRODUCT_QA_FILE".equals(ro.getFromTable())) {

				List<Object> keys = ResultConstant.PLM_PRODUCT_HEADQAFILETYE
						.keySet().stream().collect(Collectors.toList());

				if (keys.contains(ro.getFileType())
						&& !"其他".equals(ResultConstant.PLM_PRODUCT_HEADQAFILETYE
								.getString(ro.getFileType()))) {
					return true;
				} else {
					return false;
				}
			} else if ("PLM_SUPPLIER_QA_BRAND".equals(ro.getFromTable())) {
				List<Object> values = ResultConstant.PLM_BRAND_QA_TYPE.values()
						.stream().collect(Collectors.toList());
				List<Object> keys = ResultConstant.PLM_BRAND_QA_TYPE.keySet()
						.stream().collect(Collectors.toList());
				if (keys.contains(ro.getFileType())
						&& !"其他".equals(ResultConstant.PLM_BRAND_QA_TYPE
								.getString(ro.getFileType()))
						&& !"授权文件".equals(ResultConstant.PLM_BRAND_QA_TYPE
								.getString(ro.getFileType()))) {
					return true;
				} else {
					return false;
				}
			} else if ("PLM_SUPPLIER_QA_DEALER".equals(ro.getFromTable())) {
				// 判断是经销商还是 生成商
				if ("PRODUCER".equals(ro.getPersonType())) {
					if (!"其他".equals(ro.getFileType())) {
						return true;
					} else {
						return false;
					}
				} else if ("DEALER".equals(ro.getPersonType())) {
					if (!"其他".equals(ro.getFileType())) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * （UDA，售价，成本）修改接口
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	public void updateUdaMethod(JSONObject jsonObject) throws Exception {

		//1.查询最新的带有版本号的 PLM_PRODUCT_UPDATEPROPERTIS 的表的productHeadId
		String sql_header= PlmProductUpdatepropertisEntity_HI_RO.query_updateHeaderId;
		sql_header = sql_header.replace(":headerId", jsonObject.get("productHeadId").toString());
		List<PlmProductUpdatepropertisEntity_HI_RO> listHs = plmProductUpdatepropertisDAO_HI_RO.findList(sql_header);
		String productHeadId = "";
		if(CollectionUtils.isNotEmpty(listHs)){
			productHeadId= listHs.get(0).getProductHeadId();
		}else {
			return;
		}

		PlmProductHeadEntity_HI product = plmProductHeadDAO_HI.getById(jsonObject.getInteger("productHeadId"));
		Map pararmMap = new HashMap();
		pararmMap.put("productHeadId", productHeadId);
		// pararmMap.put("tablesName", jsonObject.getString("tablesName"));
		List<PlmProductUpdatepropertisEntity_HI> list = plmProductUpdatepropertisDAO_HI
				.findByProperty(pararmMap);
		list= list.stream().filter(up->!up.getNewValue().equals(up.getOldValue())).collect(Collectors.toList());
		JSONArray jsonArrayUda = new JSONArray();// UDA修改数组
		JSONArray jsonArrayPriceChange = new JSONArray();// 售价修改数组
		JSONArray jsonCostChangeChange = new JSONArray();// 成本修改数组

		// 获取中间表对应的map
		Map map = ResultConstant.map;
		Map<Integer, List<PlmProductUpdatepropertisEntity_HI>> restMap = new HashMap(); // 销售修改map
		Map<Integer, List<PlmProductUpdatepropertisEntity_HI>> costMap = new HashMap(); // 成本修改map
		Map<Integer, List<PlmProductUpdatepropertisEntity_HI>> drugMap = new HashMap(); // 药品修改map

		BigInteger requestId = new BigInteger(
				generateCodeService.getPartPlanCode()); // 同一批执行操作的修改数据用同一个请求id

		for (int i = 0; i < list.size(); i++) {
			PlmProductUpdatepropertisEntity_HI entity = list.get(i);
			if ("PLM_PRODUCT_HEAD".equals(entity.getTablesName())) {// TABLES_NAME为PLM_PRODUCT_HEAD调用UDA修改
				JSONObject restUdaJson = getUpdateUdaMethod(map, entity,
						new BigInteger(requestId.toString() + "1"),product.getRmsCode());
				if(!ObjectUtils.isEmpty(restUdaJson)){
					jsonArrayUda.add(restUdaJson);
				}
			} else if ("PLM_PRODUCT_PRICE".equals(entity.getTablesName())) {// TABLES_NAME为PLM_PRODUCT_PRICE调用售价修改
				// 同一个businessId合并到一个数据
				restMap = updatePriceChange(entity, restMap);
			} else if ("PLM_PRODUCT_SUPPLIER_INFO".equals(entity
					.getTablesName())) {
				// 同一个businessId合并到一个数据
				costMap = updatePriceChange(entity, costMap);
			} else if ("PLM_PRODUCT_DRUG".equals(entity.getTablesName())) {
				drugMap = updatePriceChange(entity, drugMap);
			}
		}
//		ResultUtils.getLookUpValue("PLM_PRODUCT_UPDATETYPE"); // 修改类型
		ResultUtils.getLookUpValue("PLM_PRODUCT_UPDATESESON");// 修改原因
		ResultUtils.getLookUpValue("PLM_PRODUCT_UPDATEPRICE");// 修改成本价原因
		if (jsonArrayUda.size() > 0) {
			getPackagingUdpJson(jsonArrayUda);
		}
		if (restMap.size() > 0) {
			getPackagingPriceChangeJson(restMap, jsonArrayPriceChange,new BigInteger(requestId.toString() + "2"),product);
		}
		if (costMap.size() > 0) {
			getPackagingCostChangeJson(costMap, jsonCostChangeChange,new BigInteger(requestId.toString() + "3"),product);
		}
		if (drugMap.size() > 0) {
//			generateDrugCsv(drugMap, jsonObject.getInteger("productHeadId"));
		}

		// 请求完成后记录一个批次信息到PLM_API_LOG
		if (CollectionUtils.isNotEmpty(list)){
//			recordedInformation(requestId, list.get(0));
			for (PlmProductUpdatepropertisEntity_HI entity : list) {
				entity.setIsRmsFlag("1");
				plmProductUpdatepropertisDAO_HI.saveOrUpdate(entity);
			}


		}

	}

	/**
	 * todo:货品修改单 同步RMS
	 * @param queryParamJSON
	 */
	@Override
	public void updateUdaMethodByEcoId(JSONObject queryParamJSON) throws Exception {
		//1根据 ecoId查询 修改单
		Integer ecoId = queryParamJSON.getInteger("ecoId");
		PlmProductHeadEcoEntity_HI headEco =  plmProductHeadEcoEntity_HI.getById(ecoId);
		Map<String ,Object> findParam = new HashMap<>();
		findParam.put("ecoId",ecoId);
		List<PlmProductModifyCheckEntity_HI> list = plmProductModifyCheckEntity_HI.findByProperty(findParam);
		String[] status= {"RMS_FAILURE","RMS_SUCCESS","EFFECTIVE","REJECT"};
		list = list.stream().filter(l->!Arrays.asList(status).contains(l.getStatus())).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(list)){
			LOGGER.info("修改单没有修改的信息需要同步打RMS!");
		}
//		ResultUtils.getLookUpValue("PLM_PRODUCT_UPDATETYPE"); // 修改类型
		ResultUtils.getLookUpValue("PLM_PRODUCT_UPDATESESON");// 修改原因
		ResultUtils.getLookUpValue("PLM_PRODUCT_UPDATEPRICE");// 修改成本价原因
		// 获取中间表对应的map
		Map map = ResultConstant.map;
		JSONArray jsonArrayUda = new JSONArray();// UDA修改数组
		JSONArray jsonArrayPriceChange = new JSONArray();// 售价修改数组
		JSONArray jsonCostChangeChange = new JSONArray();// 成本修改数组
		BigInteger requestId = new BigInteger(
				generateCodeService.getPartPlanCode()); // 同一批执行操作的修改数据用同一个请求id
		List<Integer> checkIds = new ArrayList<>();
		String rmsCode = headEco.getRmsCode();
		String employeeNo = headEco.getCreatedEmp();
		//1.处理头部数据
		List<PlmProductModifyCheckEntity_HI> headList = list.stream().filter(l -> "PLM_PRODUCT_HEAD_ECO".equals(l.getTableName()) ).collect(Collectors.toList());
		List<PlmProductModifyCheckEntity_HI> supplierPlaceList = list.stream().filter(l -> "PLM_PRODUCT_SUPPLIER_INFO_ECO".equals(l.getTableName())&& "place".equals(l.getColumnName()) ).collect(Collectors.toList());
		//是否医保药品数字典
		List<PlmProductModifyCheckEntity_HI> drugInsList = list.stream().filter(l -> "PLM_PRODUCT_DRUG_ECO".equals(l.getTableName())&& "drugIns".equals(l.getColumnName()) ).collect(Collectors.toList());
		headList.addAll(supplierPlaceList);
		headList.addAll(drugInsList);
		//定义需要修改日期的数据
		List<Map<String,String>> updatePriceDateMap= new ArrayList<>();
		//定义当期时间
		Date now  = new Date();
		if (CollectionUtils.isNotEmpty(headList) ) {
			//todo:处理产地 的RMS传值
			String toPlace = dealPlace(ecoId);
			//处理头部UDA
			for (PlmProductModifyCheckEntity_HI head :headList ) {
				String oldvalue=head.getOldValue()==null?"":head.getOldValue();
				JSONObject restUdaJson = (JSONObject) EcoUtils.getUpdateUdaMethod(map, head.getColumnName(),head.getNewValue(),employeeNo,
						new BigInteger(requestId.toString() + "1"),rmsCode,oldvalue,toPlace);
				if(!ObjectUtils.isEmpty(restUdaJson)){
					//封装参数JSONArray
					jsonArrayUda.add(restUdaJson);
					checkIds.add(head.getCheckId());
				}
			}
			//执行 接口
			if(CollectionUtils.isNotEmpty(jsonArrayUda)){
				EcoUtils.commonForUrl( jsonArrayUda ,configModel.getUpdateUdaMethodUrl());
			}
		}
		List<PlmProductModifyCheckEntity_HI> priceList = list.stream().filter(l -> "PLM_PRODUCT_PRICE_ECO".equals(l.getTableName())).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(priceList) ) {
			//处理售价
			List<PlmProductPriceEcoEntity_HI> priceEcos = new ArrayList<>();
			for (PlmProductModifyCheckEntity_HI check : priceList) {
				//查询出根据
				Map<String,Object> lineMap = new HashMap<>();
				lineMap.put("priceId",check.getLineId());
				lineMap.put("ecoId",ecoId);
				List<PlmProductPriceEcoEntity_HI> priceEcoss = plmProductPriceEcoEntity_HI.findByProperty(lineMap);
				if(!CollectionUtils.isEmpty(priceEcoss)){
					priceEcos.add(priceEcoss.get(0));
					EcoUtils.AddMapForUpdatePriceDate(priceEcoss.get(0),updatePriceDateMap,now);
					checkIds.add(check.getCheckId());
				}
			}
			//封装参数JSONArray
			if(CollectionUtils.isNotEmpty(priceEcos)){
				EcoUtils.getPackagingPriceChangeJson(priceEcos,jsonArrayPriceChange, new BigInteger(requestId.toString() + "2"),rmsCode,employeeNo);
				//执行 接口
				EcoUtils.commonForUrl( jsonArrayPriceChange ,configModel.getUpdatePriceChangeMethodUrl());
			}
		}
		List<PlmProductModifyCheckEntity_HI> supplierList = list.stream().filter(l -> "PLM_PRODUCT_SUPPLIER_INFO_ECO".equals(l.getTableName())&& "price".equals(l.getColumnName()) ).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(supplierList) ) {
			//处理成本价
			List<PlmProductSupplierInfoEcoEntity_HI> supplierEcos = new ArrayList<>();
			for (PlmProductModifyCheckEntity_HI check : supplierList) {
					Map<String, Object> lineMap = new HashMap<>();
					lineMap.put("id", check.getLineId());
					lineMap.put("ecoId", ecoId);
					List<PlmProductSupplierInfoEcoEntity_HI> supplierEcoss = plmProductSupplierInfoEcoEntity_HI.findByProperty(lineMap);
					lineMap.remove("ecoId");
					List<PlmProductSupplierInfoEntity_HI> suppliers = PlmProductSupplierInfoDAO_HI.findByProperty(lineMap);
					if (!CollectionUtils.isEmpty(supplierEcoss) && !CollectionUtils.isEmpty(suppliers)) {
						supplierEcos.add(supplierEcoss.get(0));
						now=new Date();
						EcoUtils.AddMapForUpdatePriceDate(supplierEcos.get(0),updatePriceDateMap,now);
						checkIds.add(check.getCheckId());
					}
			}
			if(CollectionUtils.isNotEmpty(supplierEcos)){
				EcoUtils.getPackagingCostChangeJson(supplierEcos, jsonCostChangeChange,new BigInteger(requestId.toString() + "3"),rmsCode,employeeNo);
				EcoUtils.commonForUrl( jsonCostChangeChange ,configModel.getUpdateCostChangeMethodUrl());
			}
		}
		List<PlmProductModifyCheckEntity_HI> drugList = list.stream().filter(l -> "PLM_PRODUCT_DRUG_ECO".equals(l.getTableName())).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(drugList)) {
			Integer lineId = drugList.get(0).getLineId();
			Map<String,Object> mapDrug = new HashMap<>();
			mapDrug.put("drugId",lineId);
			mapDrug.put("ecoId",ecoId);
			List<PlmProductDrugEcoEntity_HI> drugs =  plmProductDrugEcoEntity_HI.findByProperty(mapDrug);
			if(!CollectionUtils.isEmpty(drugs)){
				//处理药品
				PlmProductDrugEcoEntity_HI drugEco = drugs.get(0);
				generateDrugCsv(drugEco,headEco);
				List<Integer> ids = drugList.stream().map(d->d.getCheckId()).collect(Collectors.toList());
				checkIds.addAll(ids);
			}

		}
		//将修改的记录更新为 RMS_APPROVING  RMS处理中
		updateCheckModifyByIds(checkIds);
//		updateHeadEco(headEco);

		plmProductHeadEcoEntity_HI.executeSql("update plm_product_head_eco set order_status='RMS_APPROVING'  where  eco_id="+ecoId);
		// 请求完成后记录一个批次信息到PLM_API_LOG
		if (CollectionUtils.isNotEmpty(list)){
			recordedInformation(requestId, headEco.getEcoNo(),"ECO");
		}

		if (CollectionUtils.isNotEmpty(updatePriceDateMap)){
			updateCommenDate(updatePriceDateMap);
		}

	}

	private String dealPlace(Integer ecoId) {
		//todo: 根据ecoId查询supper
		Map<String,Object> map = new HashMap<>();
		map.put("ecoId",ecoId);
		List<PlmProductSupplierInfoEcoEntity_HI> suppers = plmProductSupplierInfoEcoEntity_HI.findByProperty(map);
		List<String> places = suppers.stream().map(sup->sup.getPlace()).distinct().collect(Collectors.toList());
		if(CollectionUtils.isEmpty(places)){
			return "见外包装";
		}else {
			if(places.size()>1){
				return "见外包装";
			}else {
				return EcoUtils.getPlace(places.get(0));
			}
		}
	}

	@Async
	@Override
	public void updateCommenDate(List<Map<String, String>> updatePriceDateMap) {
		for (Map<String, String> map :updatePriceDateMap) {
			String sql = " update "+ map.get("tableName") +" SET " + map.get("columnName")
					+" = to_date('"+map.get("updatePriceDate")+"','yyyy/MM/dd') where 1=1 " +
					" and  " + map.get("keyName") +" = " + map.get("ID")
					;
			plmProductDrugEcoEntity_HI.executeSql(sql);

		}
	}

	@Override
	public String getBpmUrl() {
		return configModel.getBpmUrl()==null?"":configModel.getBpmUrl();
	}

	private void updateHeadEco(PlmProductHeadEcoEntity_HI headEco) {
		//修改为 RMS 处理中
		headEco.setOrderStatus("RMS_APPROVING");
		plmProductHeadEcoEntity_HI.saveOrUpdate(headEco);
	}

	@Async
	void updateCheckModifyByIds(List<Integer> checkIds) {
		if(CollectionUtils.isEmpty(checkIds)){return;}else {
			StringBuffer sb = new StringBuffer(PlmProductModifyCheckEntity_HI_RO.QUERY_UPDATE);
			for (Integer id:checkIds) {
				sb.append(","+id);
			}
			sb.append(")");
			plmProductModifyCheckEntity_HI
					.executeSqlUpdate(sb.toString());
		}

	}
	@Async
	@Override
	public void generateDrugCsv(PlmProductDrugEcoEntity_HI entity, PlmProductHeadEcoEntity_HI headEco) throws Exception {

		Integer productHeadId = headEco.getProductHeadId();
        String timeStr = getTimeStr();
        Map<String,Object> pararmMap = new HashMap<>();
        pararmMap.put("productHeadId",headEco.getProductHeadId());

        List<Map<String, Object>> storeList = plmProductDrugEntity_HI
                .queryNamedSQLForList(PlmProductDrugEntity_HI_RO.storeSql,
                        new HashMap<String, Object>());

        //获取单体店
        List<Map<String, Object>> storeList0 = storeList.stream().filter(map-> "0".equals(map.get("STORETYPE").toString())).collect(Collectors.toList());
        //获取连体药店
        List<Map<String, Object>> storeList1 = storeList.stream().filter(map-> "1".equals(map.get("STORETYPE").toString())).collect(Collectors.toList());
        //连体药店清单
        List<Map<String, Object>> storeChainList = plmProductDrugEntity_HI
                .queryNamedSQLForList(PlmProductDrugEntity_HI_RO.storeChainSql,
                        new HashMap<String, Object>());
        List<String> chainShopIds = storeChainList.stream().map(map->map.get("SHOPID").toString()).collect(Collectors.toList());

        //获取单体药店编码
        List<String> signCodes = storeList0.stream().map(map->map.get("CODE").toString()).distinct().collect(Collectors.toList());

        //获取连体药店编码
        List<String> chainCodes = new ArrayList<>();
        List<String> codes =  storeList1.stream().map(map->map.get("CODE").toString()).distinct().collect(Collectors.toList());
        for (String code:codes) {
            if(chainShopIds.contains(code)){
                chainCodes.add(code);
            }
        }
        //处理 非药品属性的字段 的通用字段
        Map<String,Object> map = new HashMap<>();
        //条码数据
        List<PlmProductBarcodeTableEntity_HI> barCodeEntity  = plmProductBarcodeTableEntity_HI.findByProperty(pararmMap);
        if(CollectionUtils.isNotEmpty(barCodeEntity)){
            map.put("barCode",barCodeEntity.get(0).getBarcode());
        }
        PlmProductHeadEntity_HI product = plmProductHeadDAO_HI.getById(headEco.getProductHeadId());
        map.put("productName",product.getProductName());
        //处理药品单价
        JSONObject priceJSON = new JSONObject();
        priceJSON.put("productHeadId", productHeadId);
        List<PlmProductPriceEntity_HI> priceList = plmProductPriceServer.findByProperty(priceJSON);
        if(CollectionUtils.isNotEmpty(priceList)){
            map.put("price",priceList.get(0).getUnitPrice());
        }
        //判断是否有供应商
        if(!StringUtils.isEmpty(entity.getSupplierCode())){
            //存在供应商给供应商价 supPrice
            pararmMap.put("supplierCode",entity.getSupplierCode());
            List<PlmProductSupplierInfoEntity_HI> suppers =  PlmProductSupplierInfoDAO_HI.findByProperty(pararmMap);
            map.put("supPrice",suppers.get(0).getPrice());
        }
		Map<String ,String > fileInfoMap = new HashMap<>();
        //单体药店生成修改csv文件
        if(CollectionUtils.isNotEmpty(signCodes)){
            for (String code:signCodes) {
                drugUpdateCVS(code,"0" ,timeStr ,entity ,map,fileInfoMap);
            }
        }

        //连体药店生成修改csv文件
        if(CollectionUtils.isNotEmpty(chainCodes)){
            for (String code:chainCodes) {
                drugUpdateCVS(code, "1",timeStr,entity,map,fileInfoMap);
            }
        }
        new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
						LOGGER.info("上传药品：配置："+configModel.getHost2()+","+configModel.getUser2()+","+configModel.getPossword2()+", 时间:"+ requestDateFormat.format(new Date()));
						boolean ff = FtpUtil.uploadFileforMult(configModel.getHost2(), configModel.getUser2(), configModel.getPossword2(),21,  "/FTP",fileInfoMap);
						LOGGER.info("结束时间:"+ requestDateFormat.format(new Date())+", 返回结果："+ff);
						LOGGER.error("修改单药品上传FTP 成功");
					} catch (Exception e) {
						LOGGER.error("修改单药品上传FTP 报错");
						e.printStackTrace();
					}
				}
			}).start();
    }
    @Async
	void drugUpdateCVS(String code,
					   String flag, String timeStr, PlmProductDrugEcoEntity_HI entity,
					   Map<String, Object> map, Map<String, String> fileInfoMap) throws Exception {

		String file = "E:\\work\\广州屈臣氏项目\\CSV文件\\update\\GSP" + code + "-"
				+ timeStr + "_" + flag + "_u" + ".csv";
		String fileName ="GSP" + code + "-"+ timeStr + "_" + flag + "_u" + ".csv";
		String osName = System.getProperties().getProperty("os.name");
		LOGGER.info("当前操作系统： "+osName );
		if(osName.equals("Linux")){
			file = "GSP" + code + "-"+ timeStr + "_" + flag + "_u" + ".csv";
		}
		FileOutputStream fos = new FileOutputStream(new File(file));
		byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
		fos.write(uft8bom);
		OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

		String[] HEADERS = new String[] { "药品代码", "药品名称", "通用名称", "规格", "包装规格",
				"药监包装规格", "剂型", "单位", "条形码", "生产厂家", "产地", "上市许可持有人", "批准文号",
				"储藏条件", "批文有效期", "批文预警天数", "保质期", "陈列周期", "经营类别", "类别代码",
				"类别名称", "是否重点养护", "陈列预警天数", "效期预警天数", "进价一", "进价二", "售价一",
				"售价四", "是否拆零", "是否属批号管理", "处方药类别", "是否处方药品", "是否冷藏药品",
				"是否含麻黄碱", "供应商编号", "供商名称", "GSP管理级别", "药监统一编号", "药品本位码",
				"所属经营范围", "质量标准", "批准证明文件及其附件", "OPK" };

		try (CSVPrinter printer = new CSVPrinter(out,
				CSVFormat.DEFAULT.withDelimiter(','))) {
//			for (Map.Entry<Integer, List<PlmProductUpdatepropertisEntity_HI>> entry : drugMap
//					.entrySet()) {
//				List<PlmProductUpdatepropertisEntity_HI> list = entry
//						.getValue();
//				DrugColumn dc = new DrugColumn();
//				Class<?> c = entity.getClass();
//				dc.setDrugCode(entity.getDrugCode());
//				for (PlmProductUpdatepropertisEntity_HI updatepropertisEntity : list) {
//					// 将修改的属性替换到DrugColumn
//					try {
//						Field field = c.getDeclaredField(updatepropertisEntity
//								.getColumnNames());
//						field.setAccessible(true);
//						field.set(entity, updatepropertisEntity.getNewValue());
//					} catch (NoSuchFieldException e) {
//						LOGGER.error("药品属性字段："
//								+ updatepropertisEntity.getColumnNames()
//								+ " ,药品值："
//								+ updatepropertisEntity.getNewValue()
//								+ ",不存在DrugColumn对象");
//						continue;
//					}
//				}
				printer.printRecord(entity.getDrugCode(),
						map.get("productName"), entity.getCommonName(),
						entity.getStandard(), entity.getBackageUnit(),
						entity.getDrugUnit(), entity.getDrugForm(),
						entity.getUnit(), map.get("barCode"),
						entity.getProducer(), entity.getDrugPlace(),
						entity.getMarketPerson(), entity.getSymbol(),
						entity.getStoreCondition(), entity.getSymbolDate(),
						entity.getSymbolDays(), entity.getProtectPeriod(),
						entity.getDisplayCycle(), entity.getBusinessCategory(),
						entity.getTypeCode(), entity.getCategoryName(),
						entity.getIsProtect(), entity.getDisplayDays(),
						entity.getSxDays(), map.get("supPrice"), "",
						map.get("price"), "", entity.getIsPull(),
						entity.getIsBatchnumber(), entity.getPresType(),
						entity.getIsRx(), entity.getIsCold(),
						entity.getIsEphedrine(), entity.getSupplierCode(),
						entity.getSupplierName(), entity.getGspManage(),
						entity.getDrugStandcode(), entity.getDrugStandard(),
						entity.getRang(), entity.getQaStandard(),
						entity.getFileInfo(), "2");
//			}
			out.close();
//			uploadFileFtp("gspkc", "123@abc#", "10.82.96.76", file,
//					"/FTP",21,"ftp");
			fileInfoMap.put(fileName,file);
//			FileInputStream in=new FileInputStream(new File(file));
//			String finalFile = file;
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						Thread.sleep(2000);
//						LOGGER.info("上传药品：配置："+configModel.getHost2()+","+configModel.getUser2()+","+configModel.getPossword2()+", 时间:"+ requestDateFormat.format(new Date()));
//						boolean ff = FtpUtil.uploadFile(configModel.getHost2(), configModel.getUser2(), configModel.getPossword2(),21,  "/FTP",fileName,  in);
//						new File(finalFile).delete();
//						LOGGER.info("结束时间:"+ requestDateFormat.format(new Date())+", 返回结果："+ff);
//						LOGGER.error("修改单药品上传FTP 成功");
//					} catch (Exception e) {
//						LOGGER.error("修改单药品上传FTP 报错");
//						e.printStackTrace();
//					}
//				}
//			}).start();


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getTimeStr() {
		String timeStr = "";
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		timeStr = sf.format(date);
		return timeStr;
	}

	// 请求完成后记录一个批次信息到PLM_API_LOG
	public void recordedInformation(BigInteger requestId,
			String code,String logType) {
		PlmApiLogEntity_HI entityHi = new PlmApiLogEntity_HI();
		entityHi.setRequestId(requestId.toString());
		entityHi.setItem(code);
		entityHi.setLogType(logType);
		plmApiLogDAO_HI.saveOrUpdate(entityHi);
	}

	// TABLES_NAME为PLM_PRODUCT_HEAD调用UDA修改
	public JSONObject getUpdateUdaMethod( Map map,
			PlmProductUpdatepropertisEntity_HI entity, BigInteger requestId,
			String rmsCode ) throws Exception {
		String src = (String) map.get(entity.getColumnNames());
		JSONObject json = new JSONObject(true);
		if (!SaafToolUtils.isNullOrEmpty(src)) {
			// JSONObject json = new JSONObject();
			String[] strings = src.split("\\|");
			json.put("request_id", requestId);
			json.put("update_type", "U");
			json.put("uda_type", strings[1]);
			json.put("uda_id", Integer.parseInt(strings[0]));
			// json.put("uda_value", Integer.parseInt(entity.getNewValue()));
			json.put("uda_value", entity.getNewValue());
			json.put("item", rmsCode);
			json.put("item_type", "I");
			json.put("update_datetime", requestDateFormat.format(new Date()));
			json.put("last_update_id", entity.getLastUpdateId());
			return json;
		} else {
			// throw new IllegalAccessException(entity.getColumnNames()+
			// "：未在map定义转换码");
			return json;
		}

	}

	// 封装UDPjson数据
	public void getPackagingUdpJson(JSONArray jsonArrayUda) {
		JSONObject sumJson = new JSONObject();
		// sumJson.put("data_row", 2);
		sumJson.put("data_set", jsonArrayUda);
		LOGGER.info(configModel.getUpdateUdaMethodUrl() + " 请求接口参数："
				+ sumJson.toJSONString());
		// 请求接口
		JSONObject response = ResultUtils.doPost(
				configModel.getUpdateUdaMethodUrl(), sumJson);
		LOGGER.info(configModel.getUpdateUdaMethodUrl() + " 请求接口返回参数："
				+ response);
	}




	// 同一个businessId合并到一个数据
	public Map updatePriceChange(PlmProductUpdatepropertisEntity_HI entity,
			Map<Integer, List<PlmProductUpdatepropertisEntity_HI>> classifyMap) {

		// 把同一个数据的合并到一个list
		List<PlmProductUpdatepropertisEntity_HI> tempList = classifyMap
				.get(entity.getBusinessId());
		if (tempList == null) {
			tempList = new ArrayList<PlmProductUpdatepropertisEntity_HI>();
			tempList.add(entity);
			classifyMap.put(entity.getBusinessId(), tempList);
		} else {
			tempList.add(entity);
		}
		return classifyMap;
	}

	// 封装售价修改的json数据
	public void getPackagingPriceChangeJson(
			Map<Integer, List<PlmProductUpdatepropertisEntity_HI>> restMap,
			JSONArray jsonArrayPriceChange, BigInteger requestId,
			PlmProductHeadEntity_HI product) throws IllegalAccessException,
			NoSuchFieldException, ClassNotFoundException {
//		JSONObject updateTypeJson = ResultConstant.PLM_PRODUCT_UPDATETYPE;
		JSONObject reasonJson = ResultConstant.PLM_PRODUCT_UPDATESESON;
		for (Integer businessId : restMap.keySet()) {
			PlmProductPriceEntity_HI price = plmProductPriceServer
					.getById(businessId);
			List<PlmProductUpdatepropertisEntity_HI> dd = restMap
					.get(businessId);
			JSONObject json = new JSONObject(true);
			json.put("request_id", requestId);
			// json.put("update_type", "R");
			json.put("supplier", 0);
			json.put("item", product.getRmsCode());
			json.put("effective_date",
					requestKeyDateFormat.format(tomorrow(new Date())));
			json.put("change_desc", "Price-change");

			for (PlmProductUpdatepropertisEntity_HI entity : dd) {
				if ("priceGroupCode".equals(entity.getColumnNames())) {
					json.put("zone_group_id",
							Integer.valueOf(entity.getNewValue()));
				} else if ("areaId".equals(entity.getColumnNames())) {
					json.put("zone_id", entity.getNewValue());
				} else if ("unitPrice".equals(entity.getColumnNames())) {
					json.put("value", entity.getNewValue().toString());
//				} else if ("updateType".equals(entity.getColumnNames())) {
//					json.put("change_type",
//							updateTypeJson.get(entity.getNewValue()));
				} else if ("updateType".equals(entity.getColumnNames())) {
					json.put("change_type",entity.getNewValue());
				} else if ("updateSeson".equals(entity.getColumnNames())) {
					json.put("reason", Integer.valueOf(entity.getNewValue()));
				} else if ("Seson".equals(entity.getColumnNames())) {
					json.put("change_desc", entity.getNewValue());
				}
			}
			if (json.get("zone_group_id") == null) {
				json.put("zone_group_id", price.getPriceGroupCode());
			}
			if (json.get("zone_id") == null) {
				json.put(
						"zone_id",
						price.getAreaId() == null ? null : Integer
								.valueOf(price.getAreaId()));
			}
			if (json.get("change_type") == null) {
//				json.put("change_type",
//						updateTypeJson.get(price.getUpdateType()));
				json.put("change_type",
						price.getUpdateType());
			}
			if (json.get("reason") == null) {
				json.put("reason", Integer.valueOf(price.getUpdateSeson()));
			}
			json.put("item_type", "I");
			json.put("update_datetime", requestDateFormat.format(new Date()));
			json.put("last_update_id", restMap.get(businessId).get(0)
					.getLastUpdateId());
			SaafToolUtils.validateJsonParms(json, "zone_group_id", "value",
					"change_type", "reason", "change_desc"); // 效验

			jsonArrayPriceChange.add(json);
		}

		JSONObject sumJson = new JSONObject();
		// sumJson.put("data_row", 2);
		sumJson.put("data_set", jsonArrayPriceChange);
		LOGGER.info("请求上传接口: " + configModel.getUpdatePriceChangeMethodUrl()
				+ "请求接口参数：" + sumJson.toJSONString());
		// 请求接口
		JSONObject response = ResultUtils.doPost(
				configModel.getUpdatePriceChangeMethodUrl(), sumJson);
		LOGGER.info(configModel.getUpdatePriceChangeMethodUrl() + "请求接口返回参数："
				+ response);
	}

	// 封装成本修改的json数据
	public void getPackagingCostChangeJson(
			Map<Integer, List<PlmProductUpdatepropertisEntity_HI>> restMap,
			JSONArray jsonCostChangeChange, BigInteger requestId,
			PlmProductHeadEntity_HI product) {

//		JSONObject updateTypeJson = ResultConstant.PLM_PRODUCT_UPDATETYPE;
		JSONObject reasonJson = ResultConstant.PLM_PRODUCT_UPDATESESON;
		JSONObject priceReasonJson = ResultConstant.PLM_PRODUCT_UPDATEPRICE;

		for (Integer businessId : restMap.keySet()) {
			PlmProductSupplierInfoEntity_HI supplier = plmProductSupplierInfoDAO_HI
					.getById(businessId);
			List<PlmProductUpdatepropertisEntity_HI> dd = restMap
					.get(businessId);
			JSONObject json = new JSONObject(true);
			json.put("request_id", requestId);
			// json.put("update_type", "C");
			json.put("supplier", supplier.getSupplierCode());
			json.put("effective_date",
					requestKeyDateFormat.format(tomorrow(new Date())));
			json.put("update_datetime", requestDateFormat.format(new Date()));
			json.put("last_update_id", restMap.get(businessId).get(0)
					.getLastUpdateId());
			json.put("item", product.getRmsCode());
			json.put("change_type", "NP"); // 暂时默认NP(待确定)
			for (PlmProductUpdatepropertisEntity_HI entity : dd) {
				if ("price".equals(entity.getColumnNames())) {
					json.put("value", entity.getNewValue());
				} else if ("updateSeson".equals(entity.getColumnNames())) {
					json.put("reason", Integer.valueOf(entity.getNewValue()));
				} else if ("Seson".equals(entity.getColumnNames())) {
					json.put("change_desc", entity.getNewValue());
				}
			}
			if (json.get("reason") == null) {
				json.put("reason", supplier.getUpdateSeson() == null ? "1"
						: supplier.getUpdateSeson());
			}
			if (json.get("change_desc") == null) {
				json.put("change_desc", "Price-change");
			}
			json.put("item_type", "I");
			SaafToolUtils.validateJsonParms(json, "value", "reason",
					"change_desc"); // 效验
			jsonCostChangeChange.add(json);
		}

		JSONObject sumJson = new JSONObject();
		// sumJson.put("data_row", 2);
		sumJson.put("data_set", jsonCostChangeChange);
		LOGGER.info(configModel.getUpdateCostChangeMethodUrl() + " 请求接口参数："
				+ sumJson.toJSONString());
		// 请求接口
		JSONObject response = ResultUtils.doPost(
				configModel.getUpdateCostChangeMethodUrl(), sumJson);
		LOGGER.info(configModel.getUpdateCostChangeMethodUrl() + " 请求接口返回参数："
				+ response);
	}

	Date tomorrow(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		return calendar.getTime();
	}

	/**
	 * 修改ITEM属性返回接口
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	public void updateItemPropertyReturns(JSONObject jsonObject)
			throws Exception {
		// 请求返回接口： uda 成本 售价
		String url_uda = configModel.getUpdateUdaMethodUrl();
		String url_cost = configModel.getUpdateCostChangeMethodUrl();
		String url_price = configModel.getUpdatePriceChangeMethodUrl();
		List<Integer> ecoIds = new ArrayList<>();
		// 1 uda 2 成本 3 售价
		Map<String, String> map = ResultConstant.map;
		for (int i = 1; i < 4; i++) {
			String url_for = "";
			if (i == 1) {
				url_for = url_uda + "?post_date=" + jsonObject.get("date") + "";
			} else if (i == 3) {
				url_for = url_cost + "?post_date=" + jsonObject.get("date")
						+ "";
			} else if (i == 2) {
				url_for = url_price + "?post_date=" + jsonObject.get("date")
						+ "";
			}
			JSONObject response = ResultUtils.doGet(url_for, new JSONObject());
			JSONArray array = response.getJSONArray("data_set");
			LOGGER.info("查询路径："+url_for +"  结果："+array.toJSONString());
			if (CollectionUtils.isNotEmpty(array)) {
				if (i == 1) {
					 updateForUda(array,map,ecoIds);
				}else if (i == 2) {
					updateForPrice(array, map,ecoIds);
				} else if (i == 3) {
					 updateForCost(array,map,ecoIds);
				}
			}
			else {
				LOGGER.info("路径：" + url_for + " 没有数据 ");
			}
		}
		//回写审批通过状态
		if(CollectionUtils.isNotEmpty(ecoIds)){
			for(Integer ecoId:ecoIds){
				approvedEcoBpm(ecoId);
			}
		}
	}

	private void approvedEcoBpm(Integer ecoId) {
		PlmProductHeadEcoEntity_HI productEco = plmProductHeadEcoEntity_HI.getById(ecoId);
		String [] status= {"SUCCESS","EFFECTIVE"};
//		String [] status= {};
		if(!Arrays.asList(status).contains(productEco.getEcoStatus())){
			try {
				LOGGER.info("修改单号ID:"+productEco.getEcoId());
				commProcessDealServer.ProcessDeal(productEco.getAddProcessname(),"Approval","RMS修改单同步成功","商品修改流程");
			}catch (Exception e ){
				e.printStackTrace();
			}
		}


	}

	/**
	 * 3售价修改
	 *  @param array
	 * @param map
	 * @param ecoIds
	 */
	private void updateForPrice(JSONArray array, Map<String, String> map, List<Integer> ecoIds) {
        // 3 售价
        List<UpdateItemPricePropertyEntity_HI_RO> priceDates = (List<UpdateItemPricePropertyEntity_HI_RO>) JSON.parseArray(array.toJSONString(), UpdateItemPricePropertyEntity_HI_RO.class);
        //找到requestId 组
        List<String> requestIds = priceDates.stream().map(uda -> uda.getRequest_id()).distinct().collect(Collectors.toList());
        for (String requestId : requestIds) {
            List<UpdateItemPricePropertyEntity_HI_RO> priceGroupe = priceDates.stream().filter(u -> requestId.equals(u.getRequest_id())).collect(Collectors.toList());
			//根据requestId 查询ecoId
			PlmProductHeadEcoEntity_HI ecoEntity = new PlmProductHeadEcoEntity_HI();
			Integer ecoId = getEcoIdByRequestId(requestId,ecoEntity);
			if(ecoId == null){
				continue;
			}
//			ecoIds.add(ecoId);

            String rmsCode = priceGroupe.get(0).getItem();
            //根据ITEM查询货品
            Map<String, Object> mapP = new HashMap<>();
            mapP.put("rmsCode", rmsCode);
            PlmProductHeadEntity_HI product = plmProductHeadDAO_HI.findByProperty(mapP).get(0);
            //再次查询售价信息
            Map<String, Object> updateMap = new HashMap<>();
			//定义处理中的状态
			String[] ProcessInds= {"N","P","S"};
            //根据UDA_ID查询出修改的字段
            for (UpdateItemPricePropertyEntity_HI_RO priceReturn : priceGroupe) {
				if (Arrays.asList(ProcessInds).contains(priceReturn.getProcess_ind())) {
					//如果为N表示没有处理
					LOGGER.info("RMS还没有处理UDA数据:" + requestId + " ; priceGroupCode:"+priceReturn.getZone_group_id()
							+" ; areaId:"+priceReturn.getZone_id());
					continue;
				}else {
					if(!ecoIds.contains(ecoId)){
						ecoIds.add(ecoId);
					}
				}
                Map<String, Object> mmU = new HashMap<>();
                mmU.put("productHeadId", product.getProductHeadId());
				mmU.put("priceGroupCode", priceReturn.getZone_group_id());
				mmU.put("ecoId", ecoId);
				if(!Objects.isNull(priceReturn.getZone_id())){
					mmU.put("areaId", priceReturn.getZone_id());
				}
				List<PlmProductPriceEcoEntity_HI> priceEcos = plmProductPriceEcoEntity_HI.findByProperty(mmU);
				if(CollectionUtils.isEmpty(priceEcos)){
					continue;
				}
				Map<String, Object> mmC = new HashMap<>();
				Integer lineId = priceEcos.get(0).getPriceId();
				mmC.put("tableName","PLM_PRODUCT_PRICE_ECO");
				mmC.put("lineId",lineId);
				mmC.put("ecoId",ecoId);
				List<PlmProductModifyCheckEntity_HI> list = plmProductModifyCheckEntity_HI.findByProperty(mmC);
				//排除已经处理过的数据
				list=list.stream().filter(check-> "RMS_APPROVING".equals(check.getStatus())).collect(Collectors.toList());
				if(CollectionUtils.isEmpty(list)){
					continue;
				}
				for (PlmProductModifyCheckEntity_HI check : list) {
					if (!"C".equals(priceReturn.getProcess_ind())) {
						//如果为N表示没有处理  F 失败  C成功
						check.setStatus("RMS_FAILURE");
						check.setMessage(priceReturn.getMessage()==null?"RMS没有错误信息返回":priceReturn.getMessage());
					}else {
						check.setStatus("RMS_SUCCESS");
					}
//					plmProductModifyCheckEntity_HI.saveOrUpdate(check);
					plmProductModifyCheckEntity_HI.executeSql(" update PLM_PRODUCT_MODIFY_CHECK  set status='"+check.getStatus()+"',LAST_UPDATE_DATE=sysdate where check_Id="+check.getCheckId());

				}
            }
        }
    }

	private void updateForCost(JSONArray array, Map<String, String> map, List<Integer> ecoIds) {
        // 3 成本
        List<UpdateItemCostPropertyEntity_HI_RO> costDates = (List<UpdateItemCostPropertyEntity_HI_RO>) JSON.parseArray(array.toJSONString(), UpdateItemCostPropertyEntity_HI_RO.class);
        //找到requestId 组
        List<String> requestIds =  costDates.stream().map(cost -> cost.getRequest_id()).distinct().collect(Collectors.toList());
        for (String requestId : requestIds) {
            List<UpdateItemCostPropertyEntity_HI_RO>  costGroupe =  costDates.stream().filter(u -> requestId.equals(u.getRequest_id())).collect(Collectors.toList());
            //根据requestId 查询ecoId
			PlmProductHeadEcoEntity_HI ecoEntity = new PlmProductHeadEcoEntity_HI();
			Integer ecoId = getEcoIdByRequestId(requestId,ecoEntity);
			if(ecoId == null){
				continue;
			}
			//定义处理中的状态
			String[] ProcessInds= {"N","P","S"};
//			ecoIds.add(ecoId);
            //根据UDA_ID查询出修改的字段
            for (UpdateItemCostPropertyEntity_HI_RO  costReturn :  costGroupe) {
				if (Arrays.asList(ProcessInds).contains(costReturn.getProcess_ind())) {
					//如果为N表示没有处理
					LOGGER.info("RMS还没有处理UDA数据:" + requestId + " ; supplierCode:"+costReturn.getSupplier());
					continue;
				}else {
					if(!ecoIds.contains(ecoId)){
						ecoIds.add(ecoId);
					}
				}
				Map<String, Object> mapP = new HashMap<>();
				mapP.put("supplierCode", costReturn.getSupplier());
				mapP.put("ecoId",ecoId);
				PlmProductSupplierInfoEcoEntity_HI supplier = plmProductSupplierInfoEcoEntity_HI.findByProperty(mapP).get(0);
				mapP.remove("supplierCode");
				//根据供应商ID 和 ecoId 查询到check表
				mapP.put("tableName","PLM_PRODUCT_SUPPLIER_INFO_ECO");
				mapP.put("lineId",supplier.getId());
				mapP.put("ecoId",ecoId);
				List<PlmProductModifyCheckEntity_HI> list = plmProductModifyCheckEntity_HI.findByProperty(mapP);
				//排除 base 和place
                String[] filterStr = {"base","place"};
                list=list.stream().filter(check-> !Arrays.asList(filterStr).contains(check.getColumnName()) ).collect(Collectors.toList());
				//排除已经处理过的数据
				list=list.stream().filter(check-> "RMS_APPROVING".equals(check.getStatus())).collect(Collectors.toList());
				if(CollectionUtils.isEmpty(list)){
					continue;
				}
                for (PlmProductModifyCheckEntity_HI check : list) {
					if (!"C".equals(costReturn.getProcess_ind())) {
						//如果为N表示没有处理  F 失败  C成功
						check.setStatus("RMS_FAILURE");
						check.setMessage(costReturn.getMessage()==null?"RMS没有错误信息返回":costReturn.getMessage());
					}else {
						check.setStatus("RMS_SUCCESS");
					}
//					plmProductModifyCheckEntity_HI.saveOrUpdate(check);
					plmProductModifyCheckEntity_HI.executeSql(" update PLM_PRODUCT_MODIFY_CHECK  set status='"+check.getStatus()+"',LAST_UPDATE_DATE=sysdate where check_Id="+check.getCheckId());

				}
            }
        }
    }

	/**
	 * 1 uda 2 成本 3 售价
	 *  @param array
	 * @param map
	 * @param ecoIds
	 */
	private void updateForUda(JSONArray array, Map<String, String> map, List<Integer> ecoIds) throws  Exception{
		// 1 uda
			List<UpdateItemUdaPropertyEntity_HI_RO> udas = (List<UpdateItemUdaPropertyEntity_HI_RO>) JSON.parseArray(array.toJSONString(), UpdateItemUdaPropertyEntity_HI_RO.class);
			//定义处理中的状态
		    String[] ProcessInds= {"N","P","S"};
		    udas=udas.stream().filter(u->!Arrays.asList(ProcessInds).contains(u.getProcess_ind())).collect(Collectors.toList());
			//找到requestId 组
			List<String> requestIds = udas.stream().map(uda -> uda.getRequest_id()).distinct().collect(Collectors.toList());
			for (String requestId : requestIds) {
				List<UpdateItemUdaPropertyEntity_HI_RO> udaGroupe = udas.stream().filter(u->requestId.equals(u.getRequest_id())).collect(Collectors.toList());
//				String rmsCode = udaGroupe.get(0).getItem();
				PlmProductHeadEcoEntity_HI ecoEntity = new PlmProductHeadEcoEntity_HI();
				Integer ecoId = getEcoIdByRequestId(requestId,ecoEntity);
				if(ecoId == null){
					continue;
				}
//				ecoIds.add(ecoId);
				String[] status= {"RMS_FAILURE","RMS_SUCCESS"};

				//根据UDA_ID查询出修改的字段
				for (UpdateItemUdaPropertyEntity_HI_RO uda: udaGroupe) {
					if (Arrays.asList(ProcessInds).contains(uda.getProcess_ind())) {
						//如果为N表示没有处理  F 失败  C成功
						LOGGER.info("RMS还没有处理UDA数据:" + requestId + " ; UDA_ID:"+uda.getUda_id());
						continue;
					}else {
						if(!ecoIds.contains(ecoId)){
							ecoIds.add(ecoId);
						}
					}
					String column = getColumnByMap(map,uda.getUda_id().toString());
					if(column==null){
						continue;
					} else {
						//查询到check表，并修改check的状态
						Map<String ,Object> mmU= new HashMap<>();
//                        mmU.put("tableName","PLM_PRODUCT_HEAD_ECO");
						mmU.put("columnName",column);
						mmU.put("ecoId",ecoId);
						List<PlmProductModifyCheckEntity_HI> list = plmProductModifyCheckEntity_HI.findByProperty(mmU);

						//查询待处理的数据
						list=list.stream().filter(check-> "RMS_APPROVING".equals(check.getStatus())).collect(Collectors.toList());
						if(CollectionUtils.isNotEmpty(list)){
							for (PlmProductModifyCheckEntity_HI check:list){
								if (!"C".equals(uda.getProcess_ind())) {
									//如果为N表示没有处理  F 失败  C成功
									check.setStatus("RMS_FAILURE");
									check.setMessage(uda.getMessage()==null?"RMS没有错误信息返回":uda.getMessage());
								}else {
									check.setStatus("RMS_SUCCESS");
								}
								updateCheckForEco(check);
							}
						}
					}
				}
			}
	}

	private void updateCheckForEco(PlmProductModifyCheckEntity_HI check) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					plmProductModifyCheckEntity_HI.executeSql(" update PLM_PRODUCT_MODIFY_CHECK  set status='"+check.getStatus()+"',LAST_UPDATE_DATE=sysdate where check_Id="+check.getCheckId());
				}catch (Exception e) {
					LOGGER.error("修改报错!");
					e.printStackTrace();
				}
			}
		}).start();
	}

	private Integer getEcoIdByRequestId(String requestId,PlmProductHeadEcoEntity_HI ecoEntity) {
		String ecoNo="";
		Map<String,Object> maps = new HashMap<>();
		if(requestId.length()>10){maps.put("requestId",requestId.substring(0,10));}
		else {return null;}
		List<PlmApiLogEntity_HI> apis = plmApiLogDAO_HI.findByProperty(maps);
		if(CollectionUtils.isNotEmpty(apis)){
			ecoNo = apis.get(0).getItem();
		}else {
			return null;
		}
		//更加EcoNo 查询EcoId
		maps.remove("requestId");
		maps.put("ecoNo",ecoNo);
		List<PlmProductHeadEcoEntity_HI> ecos = plmProductHeadEcoEntity_HI.findByProperty(maps);
		if(CollectionUtils.isNotEmpty(ecos)){
			ecoEntity = ecos.get(0);
			return ecos.get(0).getEcoId();
		}else {
			return null;
		}

	}

	private String getColumnByMap(Map<String, String> map, String uda_id) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String evvalue=entry.getValue().replace("|LV","").replace("|FF","");
			if (evvalue.equals(uda_id)) {
				return entry.getKey();
			}
			;
		}
		return null;
	}

	/**
	 * 回调修改商品信息的接口
	 * 
	 * @param request
	 * @param last
	 * @author wjg
	 * @date 2020-03-25
	 */
	private void updateItemProperty(String request, String last, String item)
			throws Exception {
		// 根据货号Item 查询数据
		Map pararmMap = new HashMap();
		pararmMap.put("productNo", item);
		List<PlmProductUpdatepropertisEntity_HI> list = plmProductUpdatepropertisDAO_HI
				.findByProperty(pararmMap);
		JSONArray jsonArrayUda = new JSONArray();// UDA修改数组
		JSONArray jsonArrayPriceChange = new JSONArray();// 售价修改数组
		JSONArray jsonCostChangeChange = new JSONArray();// 成本修改数组

		for (int i = 0; i < list.size(); i++) {
			PlmProductUpdatepropertisEntity_HI entity = list.get(i);
			if ("PLM_PRODUCT_HEAD".equals(entity.getTablesName())) {
				JSONObject restUdaJson = JSONObject.parseObject(JSONObject
						.toJSONString(entity));
				jsonArrayUda.add(restUdaJson);
			} else if ("PLM_PRODUCT_PRICE".equals(entity.getTablesName())) {
				JSONObject restPriceJson = JSONObject.parseObject(JSONObject
						.toJSONString(entity));
				jsonArrayPriceChange.add(restPriceJson);
				// restMap = updatePriceChange(entity, restMap);
			} else if ("PLM_PRODUCT_SUPPLIER_INFO".equals(entity
					.getTablesName())) {
				JSONObject restCostJson = JSONObject.parseObject(JSONObject
						.toJSONString(entity));
				jsonCostChangeChange.add(restCostJson);
				// costMap = updatePriceChange(entity, costMap);
			}
		}
		JSONObject resultParams = new JSONObject();

		if (jsonArrayUda.size() > 0) {
			// getPackagingUdpJson(jsonArrayUda);
			JSONObject uda = new JSONObject();
			uda.put("array", jsonArrayUda);
			uda.put("status", "200");
			uda.put("remark", "成功");
			resultParams.put("uda", uda);
		} else {
			resultParams.put("uda", new JSONObject());
		}
		if (jsonArrayPriceChange.size() > 0) {
			// getPackagingPriceChangeJson(restMap, jsonArrayPriceChange,new
			// BigInteger(requestId.toString()+"2"));
			JSONObject price = new JSONObject();
			price.put("array", jsonArrayPriceChange);
			price.put("status", "200");
			price.put("remark", "成功");
			resultParams.put("price", price);
		} else {
			resultParams.put("price", new JSONObject());
		}
		if (jsonCostChangeChange.size() > 0) {
			// getPackagingCostChangeJson(costMap, jsonCostChangeChange, new
			// BigInteger(requestId.toString()+"3"));
			JSONObject supplier = new JSONObject();
			supplier.put("array", jsonCostChangeChange);
			supplier.put("status", "200");
			supplier.put("remark", "成功");
			resultParams.put("cost", supplier);
		} else {
			resultParams.put("cost", new JSONObject());
		}
		LOGGER.info("  返回传参：；resultParams::" + resultParams.toString());
		// plmProductHeadServer.UpdateRmsColumns(resultParams);

	}

	/**
	 * UDA属性同步
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	public void udaAttributeSyn(JSONObject jsonObject) throws Exception {
		jsonObject.put("action", "update");
		// 请求接口
		JSONObject response = ResultUtils.doGet(
				configModel.getUdaAttributeSynUrl(), jsonObject);
		LOGGER.info(configModel.getUdaAttributeSynUrl() + " 请求接口返回参数："
				+ response);
		JSONArray array = response.getJSONArray("data_set");
		for (int i = 0; i < array.size(); i++) {
//		for (int i = 200; i < 201; i++) {
			// for (int i = 0; i < 100; i++) {
			JSONObject json = (JSONObject) array.get(i);

			if (verification(json) == 0) {
				PlmUdaAttributeEntity_HI entity_hi = new PlmUdaAttributeEntity_HI();
				entity_hi.setUdaId(json.getInteger("uda_id"));
				entity_hi.setUdaDesc(json.getString("uda_desc"));
				entity_hi.setModule(json.getString("module"));
				entity_hi.setDisplayType(json.getString("display_type"));
				entity_hi.setDataType(json.getString("data_type"));
				entity_hi.setDataLength(json.getInteger("data_length"));
				entity_hi.setSingleValueInd(json.getString("single_value_ind"));
				entity_hi.setUdaValue(json.getInteger("uda_value"));
				entity_hi.setUdaValueDesc(json.getString("uda_value_desc"));
				plmUdaAttributeDAO_HI.saveOrUpdate(entity_hi);
			}
		}
	}

	// 效验
	private int verification(JSONObject json) {
		Map map = new HashMap();
		if (!SaafToolUtils.isNullOrEmpty(json.getInteger("uda_id"))) {
			map.put("udaId", json.getInteger("uda_id"));
		}
		if (!SaafToolUtils.isNullOrEmpty(json.getString("uda_desc"))) {
			map.put("udaDesc", json.getString("uda_desc"));
		}
		if (!SaafToolUtils.isNullOrEmpty(json.getString("module"))) {
			map.put("module", json.getString("module"));
		}
		if (!SaafToolUtils.isNullOrEmpty(json.getString("display_type"))) {
			map.put("displayType", json.getString("display_type"));
		}
		if (!SaafToolUtils.isNullOrEmpty(json.getString("data_type"))) {
			map.put("dataType", json.getString("data_type"));
		}
		if (!SaafToolUtils.isNullOrEmpty(json.getInteger("data_length"))) {
			map.put("dataLength", json.getInteger("data_length"));
		}
		if (!SaafToolUtils.isNullOrEmpty(json.getString("single_value_ind"))) {
			map.put("singleValueInd", json.getString("single_value_ind"));
		}
		if (!SaafToolUtils.isNullOrEmpty(json.getInteger("uda_value"))) {
			map.put("udaValue", json.getInteger("uda_value"));
		}
		if (!SaafToolUtils.isNullOrEmpty(json.getString("uda_value_desc"))) {
			map.put("udaValueDesc", json.getString("uda_value_desc"));
		}
		List<PlmUdaAttributeEntity_HI> list = plmUdaAttributeDAO_HI
				.findByProperty(map);
		return list.size();
	}

	/**
	 * 部门及分类编码传输
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public void depClasCode(JSONObject params) throws Exception {
		// 请求接口
		LOGGER.info(configModel.getDepClasCodeUrl() + " 请求参数：" + params);
		JSONObject response = ResultUtils.doGet(
				configModel.getDepClasCodeUrl(), params);
		LOGGER.info(configModel.getDepClasCodeUrl() + " 请求接口返回参数：" + response);
		Set<String> deptSet = new HashSet<>();
		Set<String> classSet = new HashSet<>();
		JSONArray jsonArray = response.getJSONArray("data_set");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = (JSONObject) jsonArray.get(i);
			if (!deptSet.contains(json.getString("dept"))) {
				if (verifyPlmDeptList(json) == 0) {
					PlmDeptListEntity_HI entityHi = new PlmDeptListEntity_HI();
					String code = json.getString("dept");
					String groupCode = code.trim().substring(0, 1);
					String groupDesc = getGroupDesc(groupCode);
					entityHi.setPlmDeptCode(code);
					entityHi.setPlmDeptName(json.getString("dept_name"));
					entityHi.setPlmGroupCode(groupCode);
					entityHi.setPlmGroupDesc(groupDesc);
					plmDeptListDAO_HI.saveOrUpdate(entityHi);
				}
				deptSet.add(json.getString("dept"));
			}
			if (!classSet.contains(json.getString("dept") + ":"
					+ json.getString("class"))) {
				if (verifyPlmDeptClass(json) == 0) {
					PlmDeptClassEntity_HI entity = new PlmDeptClassEntity_HI();
					entity.setPlmDeptCode(json.getString("dept"));
					entity.setPlmDeptName(json.getString("dept_name"));
					entity.setPlmClassCode(json.getString("class"));
					entity.setPlmClassName(json.getString("class_name"));
					plmDeptClassDAO_HI.saveOrUpdate(entity);
				}
				classSet.add(json.getString("dept") + ":"
						+ json.getString("class"));
			}
			if (verifyPlmDeptSubclass(json) == 0) {
				PlmDeptSubclassEntity_HI entity = new PlmDeptSubclassEntity_HI();
				entity.setPlmDeptCode(json.getString("dept"));
				entity.setPlmDeptName(json.getString("dept_name"));
				entity.setPlmClassCode(json.getString("class"));
				entity.setPlmClassName(json.getString("class_name"));
				entity.setPlmSubclassCode(json.getString("subclass"));
				entity.setPlmSubclassName(json.getString("sub_name"));
				plmDeptSubclassDAO_HI.saveOrUpdate(entity);
			}
		}
	}

	private String getGroupDesc(String groupCode) {
		String desc = null;
		if ("1".equals(groupCode)) {
			desc = "医疗保健品";
		}
		if ("2".equals(groupCode)) {
			desc = "个人护理";
		}
		if ("3".equals(groupCode)) {
			desc = "皮肤护理";
		}
		if ("4".equals(groupCode)) {
			desc = "化妆品";
		}
		if ("5".equals(groupCode)) {
			desc = "食杂部";
		}
		if ("6".equals(groupCode)) {
			desc = "百货商品";
		}
		return desc;
	}

	// 效验部门及分类清单
	public int verifyPlmDeptList(JSONObject json) {
		Map map = new HashMap();
		map.put("plmDeptCode", json.getString("dept"));
		// map.put("plmClassCode", json.getString("class"));
		// map.put("plmGroupDesc", json.getString("zone_group_desc"));
		// map.put("plmDeptCode", json.getString("zone_id"));
		// map.put("plmDeptName", json.getString("zone_desc"));
		List<PlmDeptListEntity_HI> list = plmDeptListDAO_HI.findByProperty(map);
		return list.size();
	}

	public int verifyPlmDeptClass(JSONObject json) {
		Map map = new HashMap();
		map.put("plmDeptCode", json.getString("dept"));
		map.put("plmClassCode", json.getString("class"));
		// map.put("plmSubclassCode", json.getString("subclass"));
		// map.put("plmGroupDesc", json.getString("zone_group_desc"));
		// map.put("plmDeptCode", json.getString("zone_id"));
		// map.put("plmDeptName", json.getString("zone_desc"));
		List<PlmDeptClassEntity_HI> list = plmDeptClassDAO_HI
				.findByProperty(map);
		return list.size();
	}

	public int verifyPlmDeptSubclass(JSONObject json) {
		Map map = new HashMap();
		map.put("plmDeptCode", json.getString("dept"));
		map.put("plmClassCode", json.getString("class"));
		map.put("plmSubclassCode", json.getString("subclass"));
		// map.put("plmGroupDesc", json.getString("zone_group_desc"));
		// map.put("plmDeptCode", json.getString("zone_id"));
		// map.put("plmDeptName", json.getString("zone_desc"));
		List<PlmDeptSubclassEntity_HI> list = plmDeptSubclassDAO_HI
				.findByProperty(map);
		return list.size();
	}

	/**
	 * 地点清单查询接口
	 * 
	 * @param
	 * @return
	 */
	@Override
	public JSONObject siteListingMethod(int id) throws Exception {
		// 请求接口
		JSONObject jsonObject = new JSONObject();
		LOGGER.info(configModel.getSiteListingUrl() + " 请求参数：" + id);
		JSONObject response = ResultUtils.doGet(configModel.getSiteListingUrl()
				+ id, jsonObject);
		LOGGER.info(configModel.getSiteListingUrl() + " 请求接口返回参数：" + response);

		return response;
		// JSONArray jsonArray = response.getJSONArray("data_set");
		// for (int i = 0; i < jsonArray.size(); i++) {
		// JSONObject json = (JSONObject) jsonArray.get(i);
		// if (verifyPlmLocationList(json) == 0) {
		// PlmLocationListEntity_HI entityHi = new PlmLocationListEntity_HI();
		// entityHi.setCode(json.getString("code"));
		// entityHi.setDescName(json.getString("descName"));
		// entityHi.setCreatorName(json.getString("creatorName"));
		// plmLocationListDAO_HI.saveOrUpdate(entityHi);
		// }
		// }
	}

	// 效验部门及分类清单
	public int verifyPlmLocationList(JSONObject json) {
		Map map = new HashMap();
		map.put("code", json.getString("code"));
		map.put("descName", json.getString("descName"));
		map.put("creatorName", json.getString("creatorName"));
		List<PlmLocationListEntity_HI> list = plmLocationListDAO_HI
				.findByProperty(map);
		return list.size();
	}

	/**
	 * 商品售价区域接口
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	public void commodityPriceArea(JSONObject jsonObject) throws Exception {
		// 请求接口
		LOGGER.info(configModel.getCommodityPriceAreaUrl() + " 请求参数："
				+ jsonObject);
		JSONObject response = ResultUtils.doGet(
				configModel.getCommodityPriceAreaUrl(), jsonObject);
		LOGGER.info(configModel.getCommodityPriceAreaUrl() + " 请求接口返回参数："
				+ response);

		JSONArray jsonArray = response.getJSONArray("data_set");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = (JSONObject) jsonArray.get(i);
			if (verifySellingPriceArea(json) == 0) {
				PlmSalesAreaRowEntity_HI entityHi = new PlmSalesAreaRowEntity_HI();
				entityHi.setGroupCode(json.getString("zone_group"));
				entityHi.setGroupName(json.getString("zone_group_desc"));
				entityHi.setAreaCode(json.getString("zone_id"));
				entityHi.setAreaName(json.getString("zone_desc"));
				plmSalesAreaRowDAO_HI.saveOrUpdate(entityHi);
			}
		}
	}

	// 效验售价区域
	public int verifySellingPriceArea(JSONObject json) {
		Map map = new HashMap();
		map.put("groupCode", json.getString("zone_group"));
		map.put("groupName", json.getString("zone_group_desc"));
		map.put("areaCode", json.getString("zone_id"));
		map.put("areaName", json.getString("zone_desc"));
		List<PlmSalesAreaRowEntity_HI> list = plmSalesAreaRowDAO_HI
				.findByProperty(map);
		return list.size();
	}

	/**
	 * 获取货品数据
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	public JSONObject obtainGoodsData(JSONObject jsonObject) throws Exception {
		JSONArray arrays = new JSONArray();
		JSONObject jsonRes = new JSONObject();

		StringBuffer sql = new StringBuffer(PlmProductHeadEntity_HI_RO.QUERY3);
		sql.append(" and PRH.RMS_CODE IN (");
		sql.append(splitRmsCode(jsonObject.getString("itemCode")));
		sql.append(")");

		// List<PlmProductHeadEntity_HI> headList = plmProductHeadDAO_HI
		// .findByProperty(map);
		List<PlmProductHeadEntity_HI_RO> headList = plmProductHeadDAO_HI_RO
				.findList(sql);
		for (PlmProductHeadEntity_HI_RO entity : headList) {
			Map lineMap = new HashMap();
			lineMap.put("productHeadId", entity.getProductHeadId());
			List<PlmProductSupplierInfoEntity_HI> lineList = PlmProductSupplierInfoDAO_HI
					.findByProperty(lineMap);

			JSONArray array = new JSONArray();
			for (int i = 0; i < lineList.size(); i++) {
				JSONObject lineJson = new JSONObject();
				lineJson.put("supplierId", lineList.get(i).getSupplierCode());
				lineJson.put("supplierName", lineList.get(i).getSupplierName());
				lineJson.put("supplierPrimary_Flag", lineList.get(i)
						.getIsMainsupplier());
				array.add(lineJson);
			}
			JSONObject json = new JSONObject();
			json.put("itemCode", entity.getRmsCode());
			json.put("itemDesc", entity.getProductName());
			json.put("deptNo", entity.getDepartment());
			json.put("content", array);
			arrays.add(json);
		}
		jsonRes.put("data", arrays);
		return jsonRes;
	}

	private String splitRmsCode(String itemCode) {
		String[] s = itemCode.split(",");
		StringBuffer res = new StringBuffer();
		for (String str : s) {
			// res += "'" + str + "',";
			res.append("'");
			res.append(str);
			res.append("',");
		}
		res.deleteCharAt(res.length() - 1);
		return res.toString();
	}

	/**
	 * 获取促销数据
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	public JSONObject gainSalesOutlets(JSONObject jsonObject) throws Exception {
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer(
				VmiPromotionItemEntity_HI_RO.QUERY_SQL);
		sql.append(" and t.PROMOTION_NO=:promotionNo");
		if (!SaafToolUtils.isNullOrEmpty(jsonObject.getString("offerCode"))) {
			sql.append(" and t.OFFER_CODE=:offerCode");
			map.put("offerCode", jsonObject.getString("offerCode"));
		}
		map.put("promotionNo", jsonObject.getString("packageId"));

		List<VmiPromotionItemEntity_HI_RO> lineList = vmiPromotionItemDAO_HI_RO
				.findList(sql, map);
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		for (int i = 0; i < lineList.size(); i++) {
			JSONObject lineJson = new JSONObject();
			lineJson.put("packageId", lineList.get(i).getPromotionNo());
			lineJson.put("offerCode", lineList.get(i).getOfferCode());
			lineJson.put("promotionType", lineList.get(i).getPromotionType());
			lineJson.put("effectArea", lineList.get(i).getEffectArea());
			lineJson.put("itemCode", lineList.get(i).getItemCode());
			lineJson.put("buyernote", lineList.get(i).getBuyernote());
			lineJson.put("promotionPack", lineList.get(i).getPromotionPack());
			lineJson.put("retailPrice", lineList.get(i).getRetailPrice());
			lineJson.put("promotionPrice", lineList.get(i).getPromotionPrice());
			lineJson.put("promotionMech", lineList.get(i).getPromotionMech());
			lineJson.put("osdType", lineList.get(i).getOsdType());
			lineJson.put("offerDesc", lineList.get(i).getOfferDesc());
			Date offerStartDate = lineList.get(i).getOfferStartDate();
			Date offerEndDate = lineList.get(i).getOfferEndDate();
			if (offerStartDate != null) {
				lineJson.put("offerStartDate", sdf.format(offerStartDate));
			}
			if (offerEndDate != null) {
				lineJson.put("offerEndDate", sdf.format(offerEndDate));
			}
			lineJson.put("memberOffer_Flag", lineList.get(i)
					.getMemberOfferFlag());
			lineJson.put("memberOffer", lineList.get(i).getMemberOffer());
			lineJson.put("display", lineList.get(i).getDisplay());
			lineJson.put("stepID", lineList.get(i).getStepId());
			lineJson.put("osdMin", lineList.get(i).getOsdMin());
			lineJson.put("sdMaxQty", lineList.get(i).getOsdMaxQty());
			lineJson.put("freeGood_Flag", lineList.get(i).getFreeGoodFlag());
			lineJson.put("limitedItem_Flag", lineList.get(i)
					.getLimitedItemFlag());
			lineJson.put("dmDisplay_Flag", lineList.get(i).getDmDisplayFlag());
			array.add(lineJson);
		}
		json.put("content", array);
		return json;
	}

	/**
	 * 获取促销门店
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	public JSONObject gainSalesOutletsShop(JSONObject jsonObject)
			throws Exception {
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer(
				VmiPromotionStoreEntity_HI_RO.QUERY_SQL);
		// sql.append(" and t.PROMOTION_NO=:packageId");
		if (!SaafToolUtils.isNullOrEmpty(jsonObject.getString("offerCode"))) {
			sql.append(" and t.OFFER_CODE=:offerCode");
			map.put("offerCode", jsonObject.getString("offerCode"));
		}
		map.put("packageId", jsonObject.getString("promNumber"));

		sql.append(") t");
		List<VmiPromotionStoreEntity_HI_RO> lineList = vmiPromotionStorDAO_HI_RO
				.findList(sql, map);
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < lineList.size(); i++) {
			JSONObject lineJson = new JSONObject();
			lineJson.put("packageId", lineList.get(i).getPackageId());
			lineJson.put("packageDesc", lineList.get(i).getPackageDesc());
			lineJson.put("offerCode", lineList.get(i).getOfferCode());
			array.add(lineJson);
		}
		json.put("content", array);
		return json;
	}

	/**
	 * UDA属性获取
	 * 
	 * @param jsonObject
	 * @return
	 */
	@Override
	public List<Object> getUdaAttributeByUdaId(JSONObject jsonObject)
			throws Exception {
		Integer udaId = jsonObject.getInteger("udaId");
		// 请求接口
		JSONObject response = ResultUtils.doGet(
				configModel.getUdaAttributeSynUrl() + "?uda_id=" + udaId,
				jsonObject);
		LOGGER.info(configModel.getUdaAttributeSynUrl() + " 请求接口返回参数："
				+ response);
		List<Object> data = new ArrayList<Object>();
		JSONArray jsonArray = response.getJSONArray("data_set");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = (JSONObject) jsonArray.get(i);
			Integer uda_value = json.getInteger("uda_value");
			String uda_name = json.getString("uda_value_desc");
			if (udaId == 49) {
				PlmMotherCompanyEntity_HI_RO motherinfo = new PlmMotherCompanyEntity_HI_RO();

				motherinfo.setPlmMotherCompanyId(uda_value);
				motherinfo.setPlmMotherCompanyName(uda_name);
				if (jsonObject.containsKey("PlmMotherCompanyName")) {
					String companyName = jsonObject
							.getString("PlmMotherCompanyName");
					if (motherinfo.getPlmMotherCompanyName().contains(
							companyName)) {
						data.add(motherinfo);
					}
				} else {
					data.add(motherinfo);
				}

			} else if (udaId == 48 || udaId == 47) // brand
			{
				PlmBrandInfoEntity_HI_RO brand = new PlmBrandInfoEntity_HI_RO();
				brand.setPlmBrandInfoId(uda_value);
				brand.setPlmBrandCn(uda_name);
				if (jsonObject.containsKey("Name")) {
					String companyName = jsonObject.getString("Name");
					if (brand.getPlmBrandCn().contains(companyName)) {
						data.add(brand);
					}
				} else {
					data.add(brand);
				}
			}
		}

		return data;
	}

	@Override
	public List<Object> getLocByDescName(JSONObject jsonObject)
			throws Exception {
		if (!jsonObject.containsKey("desc")) {
			throw new ServerException("请输入关键字进行查询!");
		} else {
			if (jsonObject.getString("desc").equals("")) {
				throw new ServerException("请输入关键字进行查询!");
			}
		}
		String desc = jsonObject.getString("desc");
		// 请求接口
		JSONObject response = ResultUtils.doGet(
				configModel.getLocDesc() + desc, jsonObject);
		LOGGER.info(configModel.getUdaAttributeSynUrl() + " 请求接口返回参数："
				+ response);
		List<Object> data = new ArrayList<Object>();
		JSONArray jsonArray = response.getJSONArray("data_set");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = (JSONObject) jsonArray.get(i);
			Integer value = json.getInteger("loc_list");
			String name = json.getString("loc_list_desc");
			PlmLocationListEntity_HI_RO r = new PlmLocationListEntity_HI_RO();
			r.setPlmLocationListId(value);
			r.setDescName(name);
			data.add(r);
		}

		return data;
	}


//	public void ProcessDeal(PlmProductHeadEntity_HI product, String status,
//			String comments) throws ServerException {
//		if (product.getAddProcessname() != null
//				&& !product.getAddProcessname().equals("")) {
//			String processname = product.getAddProcessname();
//			String incident = processname.split("&&&")[1];
//			String version = processname.split("&&&")[2];
//			String taskuser = "10000000";
//			String orino = processname.split("&&&")[5];
//			String taskId = this.getTaskIdByUser(orino);
//			if (!taskId.equals("")) { // 存在taskId则进行审批
//				JSONObject pa = new JSONObject(true);
//				pa.put("Method", "SubmitIncident");
//				pa.put("TASKUSER", taskuser);
//				pa.put("PROCESSNAME", "商品新增流程");
//				pa.put("billNo", orino);
//				if (status.equals("Approval")) {
//					pa.put("action", "Approval");
//				} else { // 拒绝
//					pa.put("action", "Reject");
//				}
//				pa.put("COMMENTS", comments);
//				pa.put("INCIDENT", incident);
//				pa.put("VERSION", version);
//				pa.put("TASKID", taskId);
//				pa.put("STEPLABEL", "RMS确认");
//
//				watonsBpmService.startBpm(pa.toJSONString());
//			}
//		}
//	}
//
//	// 获取虚拟用户 该表单号 待办taskId
//	public String getTaskIdByUser(String billNo) throws ServerException {
//		JSONObject pa = new JSONObject(true);
//		pa.put("Method", "GetTaskList");
//		pa.put("LISTTYPE", "1");
//		pa.put("TASKUSER", "10000000");
//		pa.put("PROCESSLIST", "商品新增流程");
//		pa.put("SUMMARY", billNo);
//		pa.put("FROM", 1);
//		pa.put("TO", 10);
//		String result = watonsBpmService.ExecuteBpm(pa);
//		JSONObject jsonresult = JSON.parseObject(result);
//
//		JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");
//		if (tasklist.size() > 0) {
//			JSONObject ja = tasklist.getJSONObject(0);
//			return ja.getString("TASKID");
//		} else {
//			return "";
//		}
//	}

}
