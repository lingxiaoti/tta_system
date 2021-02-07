package com.sie.watsons.base.api.model.inter.server;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.watsons.base.api.ConfigModel;
import com.sie.watsons.base.api.model.entities.PlmApiLogEntity_HI;
import com.sie.watsons.base.api.model.entities.readonly.PlmApiLogRankEntity_HI_RO;
import com.sie.watsons.base.api.model.enums.DrugProductLookupEnum;
import com.sie.watsons.base.plmBase.model.entities.PlmSalesAreaRowEntity_HI;
import com.sie.watsons.base.plmBase.model.inter.IPlmSalesAreaRow;
import com.sie.watsons.base.product.model.entities.*;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductSalePropertiesEntity_HI_RO;
import com.sie.watsons.base.redisUtil.FtpUtil;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.api.model.contents.serviceContents;
import com.sie.watsons.base.api.model.entities.readonly.PlmRmsMapEntity_HI_RO;
import com.sie.watsons.base.api.model.inter.IPlmRmsMap;
import com.sie.watsons.base.api.model.inter.IPlmSupProduct;
import com.sie.watsons.base.api.model.utils.CsvUtils;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmCountryOfOriginEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmCountryOfOrigin;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.server.PlmProductBarcodeTableServer;
import com.sie.watsons.base.product.model.inter.server.PlmProductHeadServer;
import com.sie.watsons.base.product.model.inter.server.PlmProductPriceServer;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


/**
 * Created by Administrator on 2019/12/13/013.
 */
@Component("plmSupProductServer")
public class PlmSupProductServer implements IPlmSupProduct {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmSupProductServer.class);

	@Autowired
	private ViewObject<PlmSalesAreaRowEntity_HI> plmSalesAreaRowDAO_HI;
	@Autowired
	private IPlmSalesAreaRow plmSalesAreaRow;
	@Autowired
	private ConfigModel configModel;
	@Autowired
	private PlmProductHeadServer plmProductHeadServer;
	@Autowired
	private PlmProductBarcodeTableServer plmProductBarcodeTableServer;
	@Autowired
	private PlmProductPriceServer plmProductPriceServer;
	@Autowired
	private IPlmCountryOfOrigin plmCountryOfOriginServer;
	@Autowired
	private BaseViewObject<PlmCountryOfOriginEntity_HI_RO> plmCountryOfOriginEntity_HI_RO;
	@Autowired
	private BaseViewObject<PlmProductHeadEntity_HI_RO> plmProductHeadEntity_HI_RO;
	@Autowired
	private BaseViewObject<PlmProductSalePropertiesEntity_HI_RO> plmProductPropertiesEntityHiRo;
	@Autowired
	private BaseCommonDAO_HI<PlmProductHeadEntity_HI> plmProductHeadEntity_HI;

	@Autowired
	private BaseCommonDAO_HI<PlmProductSupplierplaceinfoEntity_HI> plmProductSupplierplaceinfoEntity_HI;
	@Autowired
	private BaseCommonDAO_HI<PlmProductDrugEntity_HI> plmProductDrugEntity_HI;
	@Autowired
	private BaseCommonDAO_HI<PlmProductConsaleinfoEntity_HI> consaleinfoEntity_Hi;
	@Autowired
	private BaseViewObject<PlmRmsMapEntity_HI_RO> plmRmsMapDAO_HI_RO;
	@Autowired
	private BaseViewObject<PlmApiLogRankEntity_HI_RO> apiLogRankEntity_HI;

	@Autowired
	private IPlmRmsMap plmRmsMapServer;
    @Autowired
    private GenerateCodeService generateCodeService;
    @Autowired
    private ViewObject<PlmApiLogEntity_HI> plmApiLogDAO_HI;
	private SimpleDateFormat consDateFormat = new SimpleDateFormat(
			"yyyy/MM/dd");

	public Map<String, String> getMap() {
		StringBuffer sql = new StringBuffer(PlmRmsMapEntity_HI_RO.SQL);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmRmsMapEntity_HI_RO.class,
				new JSONObject(), sql, queryParamMap);
		Pagination<PlmRmsMapEntity_HI_RO> findListResult = plmRmsMapDAO_HI_RO
				.findPagination(sql, queryParamMap, 0, 1000);
		List<PlmRmsMapEntity_HI_RO> list = findListResult.getData();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getDataName(), list.get(i).getDataValue());
		}
		return map;
	}

	@Override
	public void productToMainCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> plist) throws IOException {
		// 获取配置数据
		Map<String, String> map = getMap();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//		String sql = PlmProductHeadEntity_HI_RO.QUERY;
//		sql = sql.replace(":creatDate", queryParamJSON.get("creationDate")
//				.toString());
//		Integer productHeadId = queryParamJSON.getInteger("productHeadId");
//		if (productHeadId != null) {
//			sql = sql + " and p.product_head_id=" + productHeadId + "";
//		}
//		List<PlmProductHeadEntity_HI_RO> plist = plmProductHeadEntity_HI_RO
//				.findList(sql, new HashMap<String, Object>());
		String timeStr = getTimeStr();
		String file = "E:\\work\\广州屈臣氏项目\\CSV文件\\WTCCN_SP_RMS_PRODUCT_NEW_"
				+ timeStr + ".csv";
		String osName = System.getProperties().getProperty("os.name");
		LOGGER.info("当前操作系统： "+osName );
		if(osName.equals("Linux")){
			file = "WTCCN_SP_RMS_PRODUCT_NEW_"+ timeStr + ".csv";
		}
		FileOutputStream fos = new FileOutputStream(new File(file));
		byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
		fos.write(uft8bom);
		OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
		String[] HEADERS = new String[] { "Reqeust ID", "SUPPLIER", "SUP_NAME",
				"DEPT", "CLASS", "SUBCLASS", "BRAND_CHI", "BRAND_ENG",
				"ITEM_CHI_DESC", "ITEM_ENG_DESC", "BARCODE", "CASE_LENGTH",
				"CASE_WIDTH", "CASE_HEIGHT", "SUPP_PACK_SIZE",
				"INNER_PACK_SIZE", "UNIT_COST", "Zone_Group_id", "UNIT_RETAIL",
				"STANDARD_UOM", "STORE_ORD_MULT", "Waste_type", "WASTE_PCT",
				"Share_barcode_ind", "TAX_CODE", "XX_NON_RFUN_FG",
				"XX_PRC_VRFY_FG", "XX_INHBT_QTY_FG", "16", "17", "18", "27",
				"35", "36", "37", "42", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "15", "29", "40", "41", "45", "53",
				"61", "62", "63", "50", "48", "47", "666", "49", "51", "64",
				"UDA_COL_37", "UDA_COL_38", "UDA_COL_39", "UDA_COL_40",
				"UDA_COL_41", "UDA_COL_42", "UDA_COL_43", "UDA_COL_44",
				"UDA_COL_45", "UDA_COL_46", "UDA_COL_47", "UDA_COL_48",
				"UDA_COL_49", "UDA_COL_50", "" };
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
				.withHeader(HEADERS).withDelimiter(',')
				.withIgnoreEmptyLines(false)
				.withRecordSeparator("\n"))) {

			for (PlmProductHeadEntity_HI_RO header : plist) {
				JSONObject param = new JSONObject();
				param.put("productHeadId", header.getProductHeadId());
				param.put("varUserType", "50");
				param.put("isMainsupplier", "1");
				JSONObject productInfo = plmProductHeadServer
						.findProductDetail(param);
				JSONArray supplierlist = JSONArray.parseArray(JSONObject
						.toJSONString(productInfo.get("supplierlist")));
				List<PlmProductSupplierInfoEntity_HI> lis = supplierlist.toJavaList(PlmProductSupplierInfoEntity_HI.class);

				String place = "";
				place = dealPlace(lis);
				String supplierCode = "";
				String supplierName = "";
				String barCode = "";
				String productLength = "";
				String productBreadth = "";
				String productHeight = "";
				String innerpackageSpe = "";
				String packageSpe = "";
				String price = "";
				String unitPrice = "";
				String priceGroup = "";
				String dayDamage = "";

				String tier_666 = "";
				String power_48 = "";
				String range_47 = "";


				String mother_company_49 = "";
				String condition_51 = "";
				String warehouse_resource_64 = "";
                String wasteType ="";



				supplierlist = getList("1", supplierlist);
				if (supplierlist.size() > 0) {

					if (null != JSONObject.parseObject(
							supplierlist.get(0).toString()).get("supplierCode")) {
						supplierCode = JSONObject
								.parseObject(supplierlist.get(0).toString())
								.get("supplierCode").toString();
					}
					if (null != JSONObject.parseObject(
							supplierlist.get(0).toString()).get("supplierName")) {
						supplierName = JSONObject
								.parseObject(supplierlist.get(0).toString())
								.get("supplierName").toString();
					}
//					if (null != JSONObject.parseObject(
//							supplierlist.get(0).toString()).get("barcode")) {
//						barCode = JSONObject
//								.parseObject(supplierlist.get(0).toString())
//								.get("barcode").toString();
//					}
					if (null != JSONObject.parseObject(
							supplierlist.get(0).toString())
							.get("productLength")) {
						productLength = JSONObject
								.parseObject(supplierlist.get(0).toString())
								.get("productLength").toString();
					}
					if (null != JSONObject.parseObject(
							supplierlist.get(0).toString()).get(
							"productBreadth")) {
						productBreadth = JSONObject
								.parseObject(supplierlist.get(0).toString())
								.get("productBreadth").toString();
					}
					if (null != JSONObject.parseObject(
							supplierlist.get(0).toString())
							.get("productHeight")) {
						productHeight = JSONObject
								.parseObject(supplierlist.get(0).toString())
								.get("productHeight").toString();
					}
					if (null != JSONObject.parseObject(
							supplierlist.get(0).toString()).get(
							"innerpackageSpe")) {
						innerpackageSpe = JSONObject
								.parseObject(supplierlist.get(0).toString())
								.get("innerpackageSpe").toString();
					}
					if (null != JSONObject.parseObject(
							supplierlist.get(0).toString()).get("packageSpe")) {
						packageSpe = JSONObject
								.parseObject(supplierlist.get(0).toString())
								.get("packageSpe").toString();
					}
					if (null != JSONObject.parseObject(
							supplierlist.get(0).toString()).get("price")) {
						price = JSONObject
								.parseObject(supplierlist.get(0).toString())
								.get("price").toString();
					}
//					if (null != JSONObject.parseObject(
//							supplierlist.get(0).toString()).get("place")) {
//						place = JSONObject
//								.parseObject(supplierlist.get(0).toString())
//								.get("place").toString();
//					}

				}
				JSONObject queryJSON = new JSONObject();
				queryJSON.put("productHeadId", header.getProductHeadId());
				queryJSON.put("isMain", "1");
				List<PlmProductBarcodeTableEntity_HI> barList = plmProductBarcodeTableServer
						.findList(queryJSON);
				if (barList.size() > 0) {
					barCode = barList.get(0).getBarcode();
				}
				JSONObject headInfo = JSONObject.parseObject(JSONObject
						.toJSONString(productInfo.get("headInfo")));
				if (null != headInfo.get("dayDamage")) {
					dayDamage = headInfo.get("dayDamage").toString()
							.replace("%", "");
                    wasteType="SP";
					if(Integer.valueOf(dayDamage) ==0){
                        dayDamage="";
                        wasteType="";
                    }
				}else{
                    dayDamage="";
                    wasteType="";
                }
				if (null != headInfo.get("tier")) {
					tier_666 = headInfo.get("tier").toString();
				}
				if (null != headInfo.get("powerOb")) {
					power_48 = headInfo.get("powerOb").toString();
				}
				if (null != headInfo.get("rangOb")) {
					range_47 = headInfo.get("rangOb").toString();
				}

				if (null != headInfo.get("motherCompany")) {
					mother_company_49 = headInfo.get("motherCompany").toString();
				}
				if (null != headInfo.get("condition")) {
					condition_51 = headInfo.get("condition").toString();
				}
				if (null != headInfo.get("warehouseResource")) {
					warehouse_resource_64 = headInfo.get("warehouseResource").toString();
				}

				JSONObject priceJSON = new JSONObject();
				priceJSON.put("productHeadId", header.getProductHeadId());
				List<PlmProductPriceEntity_HI> priceList = plmProductPriceServer
						.findList(priceJSON);
				if (!CollectionUtils.isEmpty(priceList)) {

                    List<PlmProductPriceEntity_HI> mainPrice = priceList.stream().filter(p->"1".equals(p.getMainPrice())).collect(Collectors.toList());
					if(!CollectionUtils.isEmpty(mainPrice)){
					    //主数据只有1条
                        priceGroup = mainPrice.get(0).getPriceGroupCode();
						unitPrice = mainPrice.get(0).getUnitPrice();
//						break;
                    }else {
                        for (int k = 0; k < priceList.size(); k++) {
                            if ("5%".equals(priceList.get(k).getPriceArea())) {
                                priceGroup = priceList.get(k).getPriceGroupCode();
                                unitPrice = priceList.get(k).getUnitPrice();
                                break;
                            }
                            if ("ALL".equals(priceList.get(k).getPriceArea())) {
                                priceGroup = priceList.get(k).getPriceGroupCode();
                                unitPrice = priceList.get(k).getUnitPrice();
                                break;
                            }
                        }
                    }
				}

                BigInteger requestId = new BigInteger(generateCodeService
                        .getPartPlanCode());
				printer.printRecord(
//						headInfo.get("plmCode").toString().substring(2),// Reqeust_ID
                        requestId,
						supplierCode,// SUPPLIER
						supplierName,// SUP_NAME
						headInfo.get("department"),// DEPT
						headInfo.get("classes"),// CLASS
						headInfo.get("subClass"),// SUBCLASS
						headInfo.get("brandnameCn"),// BRAND_CHI
						headInfo.get("brandnameEn"),// BRAND_ENG
						headInfo.get("productName"),// ITEM_CHI_DESC
						headInfo.get("productEname"),// ITEM_ENG_DESC
                        header.getBarCode()== null|| "88888888".equals(header.getBarCode())  ? "":header.getBarCode(),//BARCODE
//						"",// BARCODE
						productLength,// CASE_LENGTH
						productBreadth,// CASE_WIDTH
						productHeight,// CASE_HEIGHT
						packageSpe.equals("") ? "1" : packageSpe,// SUPP_PACK_SIZE
						innerpackageSpe,// INNER_PACK_SIZE
						price.equals("") ? "0" : price,// UNIT_COST
						priceGroup,// Zone_Group_id
						unitPrice.equals("") ? "0" : unitPrice,// UNIT_RETAIL-vendor
						// headInfo.get("unit"),//STANDARD_UOM
						map.get("STANDARD_UOM"),// "EA",//STANDARD_UOM
						map.get("STORE_ORD_MULT"),// "E",//STORE_ORD_MULT
//						map.get("WASTE_TYPE"),// "SP",//Waste_type
                        wasteType,
                        dayDamage,
//						 dayDamage.equals("")?"":dayDamage,//WASTE_PCT
//						map.get("WASTE_PCT"),// "100",//WASTE_PCT(因为上面Waste_type为SP时，WASTE_PCT为100%)
						map.get("SHARE_BARCODE_IND"),// "N",//Share_barcode_ind
						headInfo.get("rateClassCode"),// TAX_CODE
//						map.get("XX_NON_RFUN_FG"),// "N",//XX_NON_RFUN_FG
//						map.get("XX_PRC_VRFY_FG"),// "N",//XX_PRC_VRFY_FG
//						map.get("XX_INHBT_QTY_FG"),// "N",//XX_INHBT_QTY_FG
						"0".equals(headInfo.get("isreturnSale"))?"N":"Y",// XX_NON_RFUN_FG
						"0".equals(headInfo.get("isupdatePrice"))?"N":"Y",// XX_PRC_VRFY_FG
						"0".equals(headInfo.get("isiterateSales"))?"N":"Y",// XX_INHBT_QTY_FG
						headInfo.get("countUnit"),// 16 计价单位
//						getPlace(place),// 17 产地 Product Origin
						place,// 17 产地 Product Origin
						headInfo.get("originCountry") == null ? "CN" : headInfo
								.get("originCountry"),// 18 原产国
						headInfo.get("standardOfUnit") == null ? "1" : headInfo
								.get("standardOfUnit"),// 27 单位规格 Standard Of Unit 20200426 调整单位规格取值
						headInfo.get("warehouseGetDay"),// 35 仓库可收货天数
						headInfo.get("sxDays") == null ? "3650" : headInfo
								.get("sxDays"),// 36 保质期天数 Expiring date
						headInfo.get("warehousePostDay"),// 37 仓库可出货天数
						headInfo.get("productEname"),// 42 商品英文名
						headInfo.get("validDays") == null ? "0" : headInfo
								.get("validDays"),// 2 有效期限（保质期）
						 headInfo.get("specialLicence")==null?"0":headInfo.get("specialLicence"),//3  特殊牌照
						headInfo.get("productType") == null ? "3" : headInfo
								.get("productType"),// 4 货品类型
						headInfo.get("productResource") == null ? "1"
								: headInfo.get("productResource"),// 5 货品（商品）来源
						headInfo.get("productCategeery") == null ? "2"
								: headInfo.get("productCategeery"),// 6 货品种类
						headInfo.get("pricewarProduct") == null ? "1"
								: headInfo.get("pricewarProduct"),// 7 价格竞争商品合
						headInfo.get("uniqueCommodities") == null ? "0"
								: headInfo.get("uniqueCommodities"),// 8 独有商品
						headInfo.get("specialtyProduct") == null ? "0"
								: headInfo.get("specialtyProduct"),// 9 专柜商品
						headInfo.get("productProperties") == null ? "1"
								: headInfo.get("productProperties"),// 10
																	// 货品（商品）性质
						headInfo.get("buyingLevel") == null ? "4" : headInfo
								.get("buyingLevel"),// 11 Buying Level
						headInfo.get("dangerousProduct") == null ? "0"
								: headInfo.get("dangerousProduct"),// 12
																	// 危险品及易燃易爆
						headInfo.get("posInfo") == null ? "0" : headInfo
								.get("posInfo"),// 13 POS弹出信息商品
						headInfo.get("internationProduct") == null ? "0"
								: headInfo.get("internationProduct"),// 15
																		// 国际采购商品
						headInfo.get("sesionProduct") == null ? "0" : headInfo
								.get("sesionProduct"),// 29 季节性商品
						"60",// 40 传空值  CM名称
						headInfo.get("productReturn") == null ? "1" : headInfo
								.get("productReturn"),// 41 商品可退货属性
						headInfo.get("topProduct") == null ? "0" : headInfo
								.get("topProduct"),// 45 TOP商品
//						"",//53去掉
							 headInfo.get("companyDeletion")==null? "" : headInfo
						.get("companyDeletion"),//53 COMPANY
							// DELETION
						headInfo.get("bluecapProduct") == null ? "0" : headInfo
								.get("bluecapProduct"),// 61 蓝帽产品 --Health Food
														// Ledger System product
						headInfo.get("crossborderProduct") == null ? "0"
								: headInfo.get("crossborderProduct"),// 62 跨境商品
						headInfo.get("vcProduct")== null ? "0" : headInfo
								.get("vcProduct"),// 63 VC商品
						header.getDrugIns()== null ? "0":header.getDrugIns(),// 50
						power_48,// 48
						range_47,// 47
						tier_666,// 666
						mother_company_49,// 49
						condition_51,// 51
						warehouse_resource_64,// 64
						"",//
						"",//
						"",//
						"",//
						"",//
						"",//
						"",//
						"",//
						"",//
						"",//
						"",//
						"",//
						"",//
						"");
				//将plm_Api_Log表

                // requestId 和 plmCode 存入 PlmApiLogEntity_HI
                PlmApiLogEntity_HI apiLog = new PlmApiLogEntity_HI();
                apiLog.setRequestId(requestId.toString());
                apiLog.setItem(headInfo.get("plmCode").toString());
				apiLog.setLogType("NEW");
                plmApiLogDAO_HI.saveOrUpdate(apiLog);
            }
			out.close();
//			uploadFile("sieuat", "lLPsYmJlu5", "10.82.24.173", file,
//					"/app/local/rms");

//			uploadFile("wtccnit", "wtccnit123", "10.32.152.148", file,
//					"/usr01/ifiles/hbcnbit/inbox/itmmst_inbox",22,"sftp");
      uploadFile(configModel.getUser(), configModel.getPossword(), configModel.getHost(), file,
        configModel.getPath(),22,configModel.getOpen());
			 deleteFile(file);

			List<Integer> headerIds = plist.stream().map(p->p.getProductHeadId()).collect(Collectors.toList());
			plmProductHeadServer.updateByHeaderIds(headerIds);

		}

	}

	private String dealPlace(List<PlmProductSupplierInfoEntity_HI> lis) {
		if(CollectionUtils.isEmpty(lis)){
			return "见外包装";
		}else {
			List<String> places = lis.stream().map(sup->sup.getPlace()).distinct().collect(Collectors.toList());
			if(places.size()>1){
				return "见外包装";
			}else {
				return getPlace(places.get(0));
			}
		}
	}

	// 请求完成后记录一个批次信息到PLM_API_LOG
    public void recordedInformation(BigInteger requestId,
                                    PlmProductUpdatepropertisEntity_HI updateEntity) {
        PlmApiLogEntity_HI entityHi = new PlmApiLogEntity_HI();
        entityHi.setRequestId(requestId.toString());
        entityHi.setItem(updateEntity.getProductNo());
        plmApiLogDAO_HI.saveOrUpdate(entityHi);
    }

	public String getPlace(String place) {

		String placeall="";
		if(place.equals(""))
		{
			return "见外包装()";
		}
		String[] placeList=place.split(",");
		for(String child:placeList)
		{
			if (child.indexOf("-") <= 0) {  //国外名称
				placeall+=child;
			}
			else
			{
				String country = child.split("-")[0];
				String provide = child.split("-")[1];

				String city ="";
				if(child.split("-").length>2){
					city=child.split("-")[2];
				}else {
					//直辖市
					city=child.split("-")[1];
				}

				if(city.equals("北京")||city.equals("天津")||city.equals("上海")||
						city.equals("重庆")||city.equals("香港")||city.equals("台湾")||city.equals("澳门"))
				{
					placeall+=city;
				}else
				{
                String placename="";
					if ("河北".equals(provide)) {
						placename="冀" + city;
					}
					if ("山西".equals(provide)) {
						placename="晋" + city;
					}
					if ("内蒙古".equals(provide)) {
						placename="蒙" + city;
					}
					if ("辽宁".equals(provide)) {
						placename="辽" + city;
					}
					if ("吉林".equals(provide)) {
						placename="吉" + city;
					}
					if ("黑龙江".equals(provide)) {
						placename="黑" + city;
					}
					if ("江苏".equals(provide)) {
						placename="苏" + city;
					}
					if ("浙江".equals(provide)) {
						placename="浙" + city;
					}
					if ("安徽".equals(provide)) {
						placename="皖" + city;
					}
					if ("福建".equals(provide)) {
						placename="闽" + city;
					}
					if ("江西".equals(provide)) {
						placename="赣" + city;
					}
					if ("山东".equals(provide)) {
						placename= "鲁" + city;
					}
					if ("河南".equals(provide)) {
						placename="豫" + city;
					}
					if ("湖北".equals(provide)) {
						placename="鄂" + city;
					}
					if ("湖南".equals(provide)) {
						placename="湘" + city;
					}
					if ("广东".equals(provide)) {
						placename= "粤" + city;
					}
					if ("广西".equals(provide)) {
						placename= "桂" + city;
					}
					if ("海南".equals(provide)) {
						placename="琼" + city;
					}
					if ("四川".equals(provide)) {
						placename= "川" + city;
					}
					if ("贵州".equals(provide)) {
						placename= "贵" + city;
					}
					if ("云南".equals(provide)) {
						placename="云" + city;
					}
					if ("重庆".equals(provide)) {
						placename="渝" + city;
					}
					if ("西藏".equals(provide)) {
						placename="藏" + city;
					}
					if ("陕西".equals(provide)) {
						placename="陕" + city;
					}
					if ("甘肃".equals(provide)) {
						placename="甘" + city;
					}
					if ("青海".equals(provide)) {
						placename="青" + city;
					}
					if ("宁夏".equals(provide)) {
						placename="宁" + city;
					}
					if ("新疆".equals(provide)) {
						placename="新" + city;
					}

					placeall+=placename;
				}


			}
		}
       if(placeList.length>1)
      {
//	  return "见外包装("+placeall+")";
		  return "见外包装";
    }
    else {
	return placeall;
     }

	}



	@Override
	public void productToSupsCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> plist) throws IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//		String sql = PlmProductHeadEntity_HI_RO.QUERY;
//		sql = sql.replace(":creatDate", queryParamJSON.get("creationDate")
//				.toString());
//		Integer productHeadId = queryParamJSON.getInteger("productHeadId");
//		if (productHeadId != null) {
//			sql = sql + " and p.product_head_id=" + productHeadId + "";
//		}
//		List<PlmProductHeadEntity_HI_RO> plist = plmProductHeadEntity_HI_RO
//				.findList(sql, new HashMap<String, Object>());
		String timeStr = getTimeStr();

		String file = "E:\\work\\广州屈臣氏项目\\CSV文件\\WTCCN_SP_RMS_PRODUCT_SUPS_"
				+ timeStr + ".csv";
		String osName = System.getProperties().getProperty("os.name");
		if(osName.equals("Linux")){
			file = "WTCCN_SP_RMS_PRODUCT_SUPS_"+ timeStr + ".csv";
		}
		FileOutputStream fos = new FileOutputStream(new File(file));
		byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
		fos.write(uft8bom);
		OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
		String[] HEADERS = new String[] { "Reqeust ID", "supplier",
				"primary_supp_ind", "WH CONSIGN_IND", "ORIGIN_COUNTRY_ID",
				"UNIT_COST", "SUPP_PACK_SIZE", "INNER_PACK_SIZE",
				"CASE_LENGTH", "CASE_WIDTH", "CASE_HEIGHT",/** "round_inner_pct",
				"round_case_pct", "round_layer_pct", "round_pallet_pct",*/
				"LOC_TYPE", "loc", "" };
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
				.withHeader(HEADERS).withDelimiter(',')
				.withIgnoreEmptyLines(false)
				.withRecordSeparator("\n"))) {

			for (PlmProductHeadEntity_HI_RO header : plist) {
				JSONObject param = new JSONObject();
				param.put("productHeadId", header.getProductHeadId());
				param.put("varUserType", "50");
				param.put("isMainsupplier", "0");
				JSONObject productInfo = plmProductHeadServer
						.findProductDetail(param);
				LOGGER.info("打印商品供应商数据 :{}",productInfo.toJSONString());
				JSONArray supplierlist = JSONArray.parseArray(JSONObject
						.toJSONString(productInfo.get("supplierlist")));

				JSONObject headInfo = JSONObject.parseObject(JSONObject
						.toJSONString(productInfo.get("headInfo")));

				// 采购类型
				String type = "";
				if (null != headInfo.get("purchaseType")) {
					type = headInfo.get("purchaseType").toString();
				}

				String consignment = "N";
				if ("3".equals(type)) {
					consignment = "Y";
				}
//				supplierlist = getList("N", supplierlist);

                //获取requestId;
                String requestId ="";
                if(headInfo.get("plmCode")!=null){
                    StringBuffer sb = new StringBuffer(PlmApiLogRankEntity_HI_RO.SQL);
                    sb.append(" and rankView.plmCode ='"+headInfo.get("plmCode")+"'");
                    Object rankList =  apiLogRankEntity_HI.findList(sb.toString());
//					JSONArray jsonArray = JSONArray.parseArray(rankList.toString());
                    List<Map<String,Object>> maps = (List<Map<String,Object>>)rankList;
                    Map<String,Object> map = maps.get(0);
//					JSONObject json = JSONObject.parseObject(rankList.toString());
//					if(!CollectionUtils.isEmpty(json)){
//						JSONObject jsonObject =jsonArray.getJSONObject(0);
                    requestId= map.get("REQUESTID").toString();
//					}

                }
				supplierlist = getList("0",supplierlist);
				for (int i = 0; i < supplierlist.size(); i++) {
					PlmProductSupplierInfoEntity_HI supper = JSONObject.toJavaObject(JSONObject.parseObject(supplierlist.get(i).toString()),PlmProductSupplierInfoEntity_HI.class);
					String supplierCode = "";
					String sxWay = "";
					String place = "";
					String productLength = "";
					String productBreadth = "";
					String productHeight = "";
					String innerpackageSpe = "";
					String packageSpe = "";
					String price = "";
					String isMain = "";
					String whs = "";
					String stores = "";
					String originCountryid = "";
					String countryPlace = "";
                    String sxWayName = "";

					List<String> placeList = new ArrayList<String>();
					List<Map<String,Object>> maps = new ArrayList<>();
					Map<String,Object> map = new HashMap<>();
					if (supplierlist.size() > 0) {
						if (supper.getIsMainsupplier() != null) {
							isMain = supper.getIsMainsupplier();
						} else {
							isMain = "0";
						}
						if (null != supper.getSupplierCode()) {
							supplierCode = supper.getSupplierCode();
//							JSONObject.parseObject(supplierlist.get(i).toString()).get("supplierCode").toString();
						}
						if (null != supper.getSxWay()) {
							sxWay = supper.getSxWay();
							if ("1".equals(sxWay)) {
								sxWayName = "ALL";
								place = "";
								placeList.add("");
								map = new HashMap<>();
								map.put("sxWay","ALL");
								map.put("place","");
								maps.add(map);
							}
							if ("2".equals(sxWay)) {
                                sxWayName = "STORE";
								String sxStore = supper.getSxStore();
								if(sxStore !=null && sxStore.length()>1){
									String[] sxStores = sxStore.split(",");
									for (String s: sxStores) {
										map = new HashMap<>();
										placeList.add(s);
										map.put("sxWay","STORE");
										map.put("place",s);
										maps.add(map);
									}
								}
							}
							if ("3".equals(sxWay)) {
                                sxWayName = "LOC_LIST";
								if (null != supper.getPlaceCode()) {
									place = supper.getPlaceCode();
									placeList.add(place);//
								}
								map = new HashMap<>();
								map.put("sxWay","LOC_LIST");
								map.put("place",place);
								maps.add(map);
							}
							if ("4".equals(sxWay)) {
								//指定仓 + 店
//								sxWay = "ALL_STORE";
//								map = new HashMap<>();
//								map.put("sxWay","ALL_STORE");
//								map.put("place","");
//								maps.add(map);
//								String sxWarehouse = supper.getSxWarehouse();
								maps = getPlaceForWh(maps,supper);
								String sxStore = supper.getSxStore();
								toList(place, placeList);
								if(sxStore.length()>1 && sxStore !=null ) {
									String[] sts = sxStore.split(",");
									for (String p : sts) {
										map = new HashMap<>();
										map.put("sxWay", "STORE");
										map.put("place", p);
										maps.add(map);
									}
								}
//								if(sxWarehouse.length()>1 && sxWarehouse !=null ){
//									String[] swhs = sxWarehouse.split(",");
//									for (String swh : swhs ) {
//										placeList.add(swh);
//										map = new HashMap<>();
//										map.put("sxWay","WH");
//										map.put("place",swh);
//										maps.add(map);
//									}
//								}
							}
							if ("5".equals(sxWay)) {
                                sxWayName = "AREA";
								String areaId = supper.getAreaId();
								if(areaId != null && ! "".equals(areaId) ){
									if(areaId.length()>=1){
										String[] areas = areaId.split(",");
										for (String area : areas ) {
											placeList.add(area);
											map = new HashMap<>();
											map.put("sxWay","AREA");
											map.put("place",area);
											maps.add(map);
										}
									}
								}
							}
							if("6".equals(sxWay)){
								//获取供应商的仓库信息
								maps = getPlaceForWh(maps,supper);
//								sxWay = "指定仓";
//							String sxWarehouse = supper.getSxWarehouse();
//							if( sxWarehouse!=null && sxWarehouse.length()>1){
//								String[] swhs = sxWarehouse.split(",");
//								for (String wh:swhs) {
//									map = new HashMap<>();
//							    	placeList.add(wh);
//									map.put("sxWay","WH");
//									map.put("place",wh);
//									maps.add(map);
//								}
//							}

							} else if("7".equals(sxWay)){
//								sxWay = "指定店铺";
                                sxWayName = "STORE";
								String sxStore = supper.getSxStore();
								if(sxStore !=null && sxStore.length()>1){
									String[] sxStores = sxStore.split(",");
									for (String s: sxStores) {
										map = new HashMap<>();
										placeList.add(s);
										map.put("sxWay","STORE");
										map.put("place",s);
										maps.add(map);
									}
								}
							}else if("8".equals(sxWay)){
//								sxWay = "指定仓+地点清单";

								maps = getPlaceForWh(maps,supper);
								String sxWarehouse = supper.getSxWarehouse();
//								if( sxWarehouse!=null && sxWarehouse.length()>1){
//									String[] swhs = sxWarehouse.split(",");
//									for (String wh:swhs) {
//										map = new HashMap<>();
//										placeList.add(wh);
//										map.put("sxWay","WH");
//										map.put("place",wh);
//										maps.add(map);
//									}
//								}
								if (null != supper.getPlaceCode()) {
									place = supper.getPlaceCode();
									map = new HashMap<>();
									map.put("sxWay","LOC_LIST");
									map.put("place",place);
									maps.add(map);
								}


							}else if("9".equals(sxWay)){
//								sxWay = "仓库及其他店铺";
                                sxWayName = "STORE";
								String sxStore =supper.getSxStore();
								if(sxStore !=null && sxStore.length()>1){
									String[] sxStores = sxStore.split(",");
									for (String s: sxStores) {
										map = new HashMap<>();
										placeList.add(s);
										map.put("sxWay","STORE");
										map.put("place",s);
										maps.add(map);
									}
								}

								maps = getPlaceForWh(maps,supper);
//								String sxWarehouse = supper.getSxWarehouse();
//								if( sxWarehouse!=null && sxWarehouse.length()>1){
//									String[] swhs = sxWarehouse.split(",");
//									for (String wh:swhs) {
//										map = new HashMap<>();
//										placeList.add(wh);
//										map.put("sxWay","WH");
//										map.put("place",wh);
//										maps.add(map);
//									}
//								}
							}
						}

						if (null != supper.getProductLength()) {
							productLength = supper.getProductLength().toString();
						}
						if (null != supper.getProductBreadth()) {
							productBreadth = supper.getProductBreadth().toString();
						}
						if (null != supper.getProductHeight()) {
							productHeight = supper.getProductHeight().toString();
						}
						if (null != supper.getInnerpackageSpe()) {
							innerpackageSpe = supper.getInnerpackageSpe().toString();
						}
						if (null != supper.getPackageSpe()) {
							packageSpe = supper.getPackageSpe().toString();
						}
						if (null !=supper.getPrice()) {
							if ("1".equals(isMain)) {
								price = "0";
							} else {
								price = supper.getPrice();
							}
					}
					}
					if (!"1".equals(headInfo.get("purchaseType").toString())) {
						price = "0";
					}
					for (int m = 0; m < maps.size(); m++) {
						printer.printRecord(
                                requestId,
								supplierCode,// SUPPLIER

								isMain.equals("1")?"Y":"N",// primary_supp_ind
								consignment,// WH consignment*
								"CN",// origin_country_id
								price.equals("") ? "0" : price,// UNIT_COST
								packageSpe.equals("") ? "1" : packageSpe,// SUPP_PACK_SIZE
								innerpackageSpe.equals("") ? "1"
										: innerpackageSpe,// INNER_PACK_SIZE
								productLength.equals("") ? "1" : productLength,// CASE_LENGTH
								productBreadth.equals("") ? "1"
										: productBreadth,// CASE_WIDTH
								productHeight.equals("") ? "1" : productHeight,// CASE_HEIGHT
//								"0",// round_inner_pct
//								"0",// round_case_pct
//								"0",// round_layer_pct
//								"0",// round_pallet_pct
								maps.get(m).get("sxWay"),// LOC_TYPE 生效方式
								maps.get(m).get("place"),// LOC
								"");
            LOGGER.info("检测写入商品供应商文件 :{}",supplierCode);
					}
				}
			}
			out.close();
//			uploadFile("sieuat", "lLPsYmJlu5", "10.82.24.173", file,
//					"/app/local/rms");

//			uploadFile("wtccnit", "wtccnit123", "10.32.152.148", file,
//					"/usr01/ifiles/hbcnbit/inbox/itmmst_inbox",22,"sftp");
      uploadFile(configModel.getUser(), configModel.getPossword(), configModel.getHost(), file,
        configModel.getPath(),22,configModel.getOpen());
			 deleteFile(file);

		}

	}

	/**
	 * 根据 供应商信息查询所属的仓库数据
	 * @param maps
	 * @param supper
	 */
	private List<Map<String, Object>> getPlaceForWh(List<Map<String, Object>> maps, PlmProductSupplierInfoEntity_HI supper) {
		Map <String ,Object> findParam = new HashMap<>();
		findParam.put("supplierCode",supper.getSupplierCode());
		findParam.put("productHeadId",supper.getProductHeadId().toString());
		findParam.put("locType","W");
		List<PlmProductSupplierplaceinfoEntity_HI> supplierPlaces = plmProductSupplierplaceinfoEntity_HI.findByProperty(findParam);
		if(!CollectionUtils.isEmpty(supplierPlaces)){
			for (PlmProductSupplierplaceinfoEntity_HI entity : supplierPlaces) {
				Map<String, Object> place = new HashMap<>();
				place.put("sxWay","WH");
				place.put("place",entity.getLocation());
				maps.add(place);
			}
		}
		return maps;
	}

	public void toList(String place, List placeList) {
		String[] list = place.split(",");
		for (int k = 0; k < list.length; k++) {
			placeList.add(list[k]);
		}
	}

	@Override
	public void productToBarCodeCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> plist)
			throws IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		// JSONObject queryParamJSON = new JSONObject();
		// queryParamJSON.put("orderStatus", 6);
		// queryParamJSON.put("creationDate", "2019-12-16");
//		String sql = PlmProductHeadEntity_HI_RO.QUERY;
//		sql = sql.replace(":creatDate", queryParamJSON.get("creationDate")
//				.toString());
//		Integer productHeadId = queryParamJSON.getInteger("productHeadId");
//		if (productHeadId != null) {
//			sql = sql + " and p.product_head_id=" + productHeadId + "";
//		}
//		List<PlmProductHeadEntity_HI_RO> plist = plmProductHeadEntity_HI_RO
//				.findList(sql, new HashMap<String, Object>());
		String timeStr = getTimeStr();
		String file = "E:\\work\\广州屈臣氏项目\\CSV文件\\WTCCN_SP_RMS_PRODUCT_BARCODES_"
				+ timeStr + ".csv";
		String osName = System.getProperties().getProperty("os.name");
		if(osName.equals("Linux")){
			file = "WTCCN_SP_RMS_PRODUCT_BARCODES_"+ timeStr + ".csv";
		}
		FileOutputStream fos = new FileOutputStream(new File(file));
		byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
		fos.write(uft8bom);
		OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
		String[] HEADERS = new String[] { "Reqeust ID", "BARCODE", "" };
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
				.withHeader(HEADERS).withDelimiter(',')
				.withIgnoreEmptyLines(false)
				.withRecordSeparator("\n"))) {

			for (PlmProductHeadEntity_HI_RO header : plist) {
				JSONObject param = new JSONObject();
				param.put("productHeadId", header.getProductHeadId());
				param.put("varUserType", "50");
				JSONObject productInfo = plmProductHeadServer
						.findProductDetail(param);

				JSONObject headInfo = JSONObject.parseObject(JSONObject
						.toJSONString(productInfo.get("headInfo")));
				JSONObject queryJSON = new JSONObject();
				queryJSON.put("productHeadId", header.getProductHeadId());
				List<PlmProductBarcodeTableEntity_HI> barList = plmProductBarcodeTableServer
						.findList(queryJSON);
				// barList = barList("N",barList);
				if(header.getBarCode()!=null){
					barList = barList.stream().filter(bar->!bar.getBarcode().equals(header.getBarCode())).collect(Collectors.toList());
				}
				barList = barList.stream().filter(bar-> !"88888888".equals(bar.getBarcode())).collect(Collectors.toList());

				//获取requestId;
                String requestId ="";
                if(headInfo.get("plmCode")!=null) {
                    StringBuffer sb = new StringBuffer(PlmApiLogRankEntity_HI_RO.SQL);
                    sb.append(" and rankView.plmCode ='"+headInfo.get("plmCode")+"'");
                    Object rankList =  apiLogRankEntity_HI.findList(sb.toString());
//					JSONArray jsonArray = JSONArray.parseArray(rankList.toString());
                    List<Map<String,Object>> maps = (List<Map<String,Object>>)rankList;
                    Map<String,Object> map = maps.get(0);
//					JSONObject json = JSONObject.parseObject(rankList.toString());
//					if(!CollectionUtils.isEmpty(json)){
//						JSONObject jsonObject =jsonArray.getJSONObject(0);
                    requestId= map.get("REQUESTID").toString();
//					}

                }
				for (PlmProductBarcodeTableEntity_HI bar : barList) {
					printer.printRecord(
//					        headInfo.get("plmCode").toString().substring(2),// Reqeust ID
                            requestId,
							bar.getBarcode(),// SUPPLIER,数据库数据要符合barcode规则，不嫩随便输入
							""

					);
				}

			}
			out.close();
//			uploadFile("sieuat", "lLPsYmJlu5", "10.82.24.173", file,
//					"/app/local/rms");

//			uploadFile("wtccnit", "wtccnit123", "10.32.152.148", file,
//					"/usr01/ifiles/hbcnbit/inbox/itmmst_inbox",22,"sftp");
      uploadFile(configModel.getUser(), configModel.getPossword(), configModel.getHost(), file,
        configModel.getPath(),22,configModel.getOpen());
			 deleteFile(file);

		}
	}

	public JSONArray getList(String statu, JSONArray supplierlist) {
		JSONArray list = new JSONArray();
		String sta = "";
		for (int i = 0; i < supplierlist.size(); i++) {
			if (JSONObject.parseObject(supplierlist.get(i).toString()).get(
					"isMainsupplier") == null) {
				sta = "0";
			} else {
				sta = JSONObject.parseObject(supplierlist.get(i).toString())
						.get("isMainsupplier").toString();
			}

			if (statu.equals(sta)) {
				list.add(supplierlist.get(i));
			}
		}

		return list;
	}

	public List<PlmProductBarcodeTableEntity_HI> barList(String statu,
			List<PlmProductBarcodeTableEntity_HI> barList) {
		List<PlmProductBarcodeTableEntity_HI> list = new ArrayList<PlmProductBarcodeTableEntity_HI>();
		String sta = "";
		for (int i = 0; i < barList.size(); i++) {
			if (barList.get(i).getIsMain() == null) {
				sta = "0";
			} else {
				sta = barList.get(i).getIsMain();
			}

			if (statu.equals(sta)) {
				list.add(barList.get(i));
			}
		}

		return list;
	}

	@Override
	public void productToPriceCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> plist) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//		String sql = PlmProductHeadEntity_HI_RO.QUERY;
//		sql = sql.replace(":creatDate", queryParamJSON.get("creationDate")
//				.toString());
//		Integer productHeadId = queryParamJSON.getInteger("productHeadId");
//		if (productHeadId != null) {
//			sql = sql + " and p.product_head_id=" + productHeadId + "";
//		}
//		List<PlmProductHeadEntity_HI_RO> plist = plmProductHeadEntity_HI_RO
//				.findList(sql, new HashMap<String, Object>());
		String timeStr = getTimeStr();
		String file = "E:\\work\\广州屈臣氏项目\\CSV文件\\WTCCN_SP_RMS_PRODUCT_ZONES_"
				+ timeStr + ".csv";
		String osName = System.getProperties().getProperty("os.name");
		if(osName.equals("Linux")){
			file = "WTCCN_SP_RMS_PRODUCT_ZONES_"+ timeStr + ".csv";
		}
		FileOutputStream fos = new FileOutputStream(new File(file));
		byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
		fos.write(uft8bom);
		OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
		String[] HEADERS = new String[] { "Reqeust ID", "ZONE_GROUP_ID",
				"ZONE_ID", "UNIT_RETAIL", "" };

		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
				.withHeader(HEADERS).withDelimiter(',')
				.withIgnoreEmptyLines(false)
				.withRecordSeparator("\n"))) {

			for (PlmProductHeadEntity_HI_RO header : plist) {
				JSONObject param = new JSONObject();
				param.put("productHeadId", header.getProductHeadId());
				param.put("varUserType", "50");
				param.put("isMainsupplier", "1");
				JSONObject productInfo = plmProductHeadServer
						.findProductDetail(param);

				JSONObject headInfo = JSONObject.parseObject(JSONObject
						.toJSONString(productInfo.get("headInfo")));
				String otherInfo = headInfo.get("otherInfo").toString();
				JSONObject priceJSON = new JSONObject();
				priceJSON.put("productHeadId", header.getProductHeadId());
				List<PlmProductPriceEntity_HI> priceList = plmProductPriceServer
						.findList(priceJSON);
//				priceList = getOrderList(priceList);
				// 20200407 查询售价区
//				List<String> priceGroups = priceList.stream().map(p->p.getPriceGroupCode()).distinct().collect(Collectors.toList());
//				List<PlmSalesAreaRowEntity_HI_RO> rows =  new ArrayList<>();


//				rows= plmSalesAreaRow.findListByGroup(priceGroups);
//				if(CollectionUtils.isEmpty(rows)){
//					LOGGER.error("物料:"+header.getPlmCode()+",查询的售价组为空！");
//					continue;
//				}


				//获取requestId;
                String requestId ="";
                if(headInfo.get("plmCode")!=null){
                    StringBuffer sb = new StringBuffer(PlmApiLogRankEntity_HI_RO.SQL);
                    sb.append(" and rankView.plmCode ='"+headInfo.get("plmCode")+"'");
                    Object rankList =  apiLogRankEntity_HI.findList(sb.toString());
//					JSONArray jsonArray = JSONArray.parseArray(rankList.toString());
                    List<Map<String,Object>> maps = (List<Map<String,Object>>)rankList;
                    Map<String,Object> map = maps.get(0);
//					JSONObject json = JSONObject.parseObject(rankList.toString());
//					if(!CollectionUtils.isEmpty(json)){
//						JSONObject jsonObject =jsonArray.getJSONObject(0);
                    requestId= map.get("REQUESTID").toString();
//					}

                }
//                String[] groups = {"1","2"};
//				for (String group:groups) {
//					if(priceGroups.contains(group)){
//						List<PlmSalesAreaRowEntity_HI_RO>  areaRowList = rows.stream().filter(r->group.equals(r.getGroupCode())).collect(Collectors.toList());
//						PlmProductPriceEntity_HI price = priceList.stream().filter(p->group.equals(p.getPriceGroupCode())).collect(Collectors.toList()).get(0);
//						for (PlmSalesAreaRowEntity_HI_RO row : areaRowList) {
//							printer.printRecord(
////							        headInfo.get("plmCode").toString().substring(2),// Reqeust ID
//									requestId,
//									price.getPriceGroupCode(),// ZONE_GROUP_ID
//									row.getAreaCode(),// ZONE_ID
//									price.getUnitPrice(),// UNIT_RETAIL
//									"");
//						}
//					};
//				}

				for (PlmProductPriceEntity_HI price : priceList) {
							printer.printRecord(
                                    requestId,
									price.getPriceGroupCode(),// ZONE_GROUP_ID
									price.getAreaId(),// ZONE_ID
									price.getUnitPrice(),// UNIT_RETAIL
									"");
				}

			}
			out.close();
//			uploadFile("sieuat", "lLPsYmJlu5", "10.82.24.173", file,
//					"/app/local/rms");

//			uploadFile("wtccnit", "wtccnit123", "10.32.152.148", file,
//					"/usr01/ifiles/hbcnbit/inbox/itmmst_inbox",22,"sftp");
      uploadFile(configModel.getUser(), configModel.getPossword(), configModel.getHost(), file,
        configModel.getPath(),22,configModel.getOpen());
			 deleteFile(file);

		}catch (Exception e){
		    throw  new Exception(e.getMessage());
        }

	}

	public List<PlmProductPriceEntity_HI> getOrderList(
			List<PlmProductPriceEntity_HI> priceList) {
		List<PlmProductPriceEntity_HI> list = new ArrayList<PlmProductPriceEntity_HI>();
		int step = 11;
		for (int i = 1; i < 12; i++) {
			for (int k = 0; k < priceList.size(); k++) {
			    if( priceList.get(k).getPriceGroupCode()!=null && !ObjectUtils.isEmpty(priceList.get(k).getPriceGroupCode())){
                    if (i == Integer.parseInt( priceList.get(k).getPriceGroupCode()) ){
                        list.add(priceList.get(k));
                    }
                }

			}
		}
		return list;
	}

	@Override
	public void productToConsCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> plist) throws IOException {
//		String sql = PlmProductHeadEntity_HI_RO.QUERY2;
//		sql = sql.replace(":creatDate", queryParamJSON.get("creationDate")
//				.toString());
//		sql = sql.replace(":purchaseType", "3");
//		Integer productHeadId = queryParamJSON.getInteger("productHeadId");
//		if (productHeadId != null) {
//			sql = sql + " and p.product_head_id=" + productHeadId + "";
//		}
////		  sql = sql + " and p.product_head_id !=624 ";
//		List<PlmProductHeadEntity_HI_RO> plist = plmProductHeadEntity_HI_RO
//				.findList(sql, new HashMap<String, Object>());
		if(!CollectionUtils.isEmpty(plist)){
			plist=plist.stream().filter(p->"3".equals(p.getPurchaseType())).collect(Collectors.toList());
			if(CollectionUtils.isEmpty(plist)){
				return;
			}
		}else {
			return;
		}
		String timeStr = getTimeStr();
		String file = "E:\\work\\广州屈臣氏项目\\CSV文件\\WTCCN_SP_RMS_PRODUCT_CONS_"
				+ timeStr + ".csv";
		String osName = System.getProperties().getProperty("os.name");
		if(osName.equals("Linux")){
			file = "WTCCN_SP_RMS_PRODUCT_CONS_"+ timeStr + ".csv";
		}
		FileOutputStream fos = new FileOutputStream(new File(file));
		byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
		fos.write(uft8bom);
		OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
		String[] HEADERS = new String[] { "Reqeust ID", "SUPPLIER", "LOC_TYPE",
				"LOC", "CONSIGNMENT_TYPE", "CONSIGNMENT_RATE", "START_DATE",
				"END_DATE", "" };
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
				.withHeader(HEADERS).withDelimiter(',')
				.withIgnoreEmptyLines(false)
				.withRecordSeparator("\n"))) {
			for (PlmProductHeadEntity_HI_RO header : plist) {
                Integer departMent = Integer.parseInt(header.getDepartment()
                        .split("")[2]);
                Boolean status = false;
                if (5 <= departMent && departMent < 8) {
                    status = true;
                }
                JSONObject param = new JSONObject();
                param.put("productHeadId", header.getProductHeadId());
                param.put("varUserType", "50");
                JSONObject productInfo = plmProductHeadServer
                        .findProductDetail(param);
                JSONArray storelist = JSONArray.parseArray(JSONObject
                        .toJSONString(productInfo.get("storelist")));
                JSONArray supplierlist = JSONArray.parseArray(JSONObject
                        .toJSONString(productInfo.get("supplierlist")));
                JSONObject headInfo = JSONObject.parseObject(JSONObject
                        .toJSONString(productInfo.get("headInfo")));
                //获取requestId;
                String requestId = "";
                if (headInfo.get("plmCode") != null) {
                    StringBuffer sb = new StringBuffer(PlmApiLogRankEntity_HI_RO.SQL);
                    sb.append(" and rankView.plmCode ='" + headInfo.get("plmCode") + "'");
                    Object rankList = apiLogRankEntity_HI.findList(sb.toString());
//					JSONArray jsonArray = JSONArray.parseArray(rankList.toString());
                    List<Map<String, Object>> maps = (List<Map<String, Object>>) rankList;
                    Map<String, Object> map = maps.get(0);
                    requestId = map.get("REQUESTID").toString();
                }
                //转换出 代销店铺类别 2020-06-16 代码修改重构
				List<PlmProductStoreEntity_HI> storeEnlist = storelist.toJavaList(PlmProductStoreEntity_HI.class);
                //1.将地点清单封装到csv
				List<PlmProductStoreEntity_HI> storelocList = storeEnlist.stream().filter(s->"1".equals(s.getSxWay())).collect(Collectors.toList());
				if(!CollectionUtils.isEmpty(storelocList)){
					for (int i = 0; i <storelocList.size() ; i++) {
						PlmProductStoreEntity_HI store = storelocList.get(i);
						printer.printRecord(
								requestId,
								store.getSupplierId(),// SUPPLIER
								"LOC_LIST",// LOC_TYPE
								store.getPlaceCode(),// loc
								 store.getSubstituteType(),// CONSIMENT_TYPE
								  store.getSubstitutePropetion(),// CONSIGNMENT_RATE
								consDateFormat.format(store.getStartDate())	 ,// START_DATE
								consDateFormat.format(store.getEndDate()),// END_DATE
								"");
					}
				}
				//2.将生成的数据同步到csv
				Map<String,Object> map = new HashMap<>();
				map.put("requestId",headInfo.get("plmCode"));
				List<PlmProductConsaleinfoEntity_HI> entitys = consaleinfoEntity_Hi.findByProperty(map);
				if(!CollectionUtils.isEmpty(entitys)){
					for (int i = 0; i <entitys.size() ; i++) {
						PlmProductConsaleinfoEntity_HI cons = entitys.get(i);
						printer.printRecord(
								requestId,
								cons.getSupplier(),// SUPPLIER
								"STORE",// LOC_TYPE
								cons.getLoc(),// loc
								cons.getConsimentType(),// CONSIMENT_TYPE
								cons.getConsignmentRate(),// CONSIGNMENT_RATE
								consDateFormat.format(cons.getStartDate()),// START_DATE
								consDateFormat.format(cons.getEndDate()),// END_DATE
								"");
					}
				}
			}
			out.close();
//			uploadFile("sieuat", "lLPsYmJlu5", "10.82.24.173", file,
//					"/app/local/rms");

//			uploadFile("wtccnit", "wtccnit123", "10.32.152.148", file,
//					"/usr01/ifiles/hbcnbit/inbox/itmmst_inbox",22,"sftp");
      uploadFile(configModel.getUser(), configModel.getPossword(), configModel.getHost(), file,
        configModel.getPath(),22,configModel.getOpen());
			 deleteFile(file);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String dateToString(Date d, String format) {
		return new SimpleDateFormat(format).format(d);
	}

	public Date stringToDate(String s, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(s);
	}

	@Override
	public void productToCvwCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> plist) throws IOException {
//		String sql = PlmProductHeadEntity_HI_RO.QUERY2;
//		sql = sql.replace(":creatDate", queryParamJSON.get("creationDate")
//				.toString());
//		sql = sql.replace(":purchaseType", "2");
//		Integer productHeadId = queryParamJSON.getInteger("productHeadId");
//		if (productHeadId != null) {
//			sql = sql + " and p.product_head_id=" + productHeadId + "";
//		}
//		List<PlmProductHeadEntity_HI_RO> plist = plmProductHeadEntity_HI_RO
//				.findList(sql, new HashMap<String, Object>());
		if(!CollectionUtils.isEmpty(plist)){
			plist = plist.stream().filter(p->"2".equals(p.getPurchaseType())).collect(Collectors.toList());
			if(CollectionUtils.isEmpty(plist)){
				return;
			}
		}else {
			return;
		}

		String timeStr = getTimeStr();
		String file = "E:\\work\\广州屈臣氏项目\\CSV文件\\WTCCN_SP_RMS_PRODUCT_CVW_"
				+ timeStr + ".csv";
		String osName = System.getProperties().getProperty("os.name");
		if(osName.equals("Linux")){
			file = "WTCCN_SP_RMS_PRODUCT_CVW_"+ timeStr + ".csv";
		}
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
				file), "UTF-8");
		String[] HEADERS = new String[] { "Reqeust ID", "WH_CONSIGN_DESC",
				"SUPPLIER", "CURRENCY_CODE", "EXCHANGE_RATE", "START_DATE",
				"END_DATE", "ADJUSTMENT_BASIS", "ADJUSTMENT_VALUE", "" };
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
				.withHeader(HEADERS).withDelimiter(',')
				.withIgnoreEmptyLines(false)
				.withRecordSeparator("\n")
		)) {

			for (PlmProductHeadEntity_HI_RO header : plist) {
				Integer departMent = Integer.parseInt(header.getDepartment()
						.split("")[2]);
				Boolean status = false;
				if (8 <= departMent) {
					status = true;
				}
				JSONObject param = new JSONObject();
				param.put("productHeadId", header.getProductHeadId());
				param.put("varUserType", "50");
				JSONObject productInfo = plmProductHeadServer
						.findProductDetail(param);
				JSONArray storelist = JSONArray.parseArray(JSONObject
						.toJSONString(productInfo.get("storelist")));
				JSONArray supplierlist = JSONArray.parseArray(JSONObject
						.toJSONString(productInfo.get("supplierlist")));

				JSONObject headInfo = JSONObject.parseObject(JSONObject
						.toJSONString(productInfo.get("headInfo")));

				//获取requestId;
				String requestId ="";
				if(headInfo.get("plmCode")!=null){
					StringBuffer sb = new StringBuffer(PlmApiLogRankEntity_HI_RO.SQL);
					sb.append(" and rankView.plmCode ='"+headInfo.get("plmCode")+"'");
					Object rankList =  apiLogRankEntity_HI.findList(sb.toString());
//					JSONArray jsonArray = JSONArray.parseArray(rankList.toString());
					List<Map<String,Object>> maps = (List<Map<String,Object>>)rankList;
					Map<String,Object> map = maps.get(0);
//					JSONObject json = JSONObject.parseObject(rankList.toString());
//					if(!CollectionUtils.isEmpty(json)){
//						JSONObject jsonObject =jsonArray.getJSONObject(0);
						requestId= map.get("REQUESTID").toString();
//					}

				}
				for (int i = 0; i < storelist.size(); i++) {
					String originCountryid = "";
					String supplierCode = "";// 代销表的
					String supplierCode2 = "";// 供应商表的
					String currencyCost = "";
					String startDate = "";
					String endDate = "";
					String substituteType = "";
					String substitutePropetion = "";
					String exRate = "";//汇率



					if (storelist.size() > 0) {
						if (null != JSONObject.parseObject(
								storelist.get(i).toString()).get("supplierId")) {
							supplierCode = JSONObject
									.parseObject(storelist.get(i).toString())
									.get("supplierId").toString();
						}
						if (null != JSONObject.parseObject(
								storelist.get(i).toString()).get("startDate")) {
							startDate = JSONObject
									.parseObject(storelist.get(i).toString())
									.get("startDate").toString();
							startDate = startDate.split(" ")[0].replace("-",
									"/");
						}
						if (null != JSONObject.parseObject(
								storelist.get(i).toString()).get("endDate")) {
							endDate = JSONObject
									.parseObject(storelist.get(i).toString())
									.get("endDate").toString();
							endDate = endDate.split(" ")[0].replace("-", "/");
						}
						if (null != JSONObject.parseObject(
								storelist.get(i).toString()).get(
								"substituteType")) {
							substituteType = JSONObject
									.parseObject(storelist.get(i).toString())
									.get("substituteType").toString();
							if ("F".equals(substituteType)) {
								substituteType = "Q";
							}
							if ("P".equals(substituteType)) {
								substituteType = "V";
							}
						}
						if (null != JSONObject.parseObject(
								storelist.get(i).toString()).get(
								"substitutePropetion")) {
							if (status) {
								substitutePropetion = JSONObject
										.parseObject(
												storelist.get(i).toString())
										.get("substitutePropetion").toString();
							} else {
								substitutePropetion = "";
							}

						}
						if (null != JSONObject.parseObject(
								storelist.get(i).toString()).get(
								"exRate")) {
							if (status) {
								substitutePropetion = JSONObject
										.parseObject(
												storelist.get(i).toString())
										.get("exRate").toString();
							} else {
								substitutePropetion = "";
							}
						}
						// 轮询匹配供应商
						for (int m = 0; m < supplierlist.size(); m++) {
							if (null != JSONObject.parseObject(
									supplierlist.get(m).toString()).get(
									"supplierCode")) {
								supplierCode2 = JSONObject
										.parseObject(
												supplierlist.get(m).toString())
										.get("supplierCode").toString();
							}// 如果供应商编号对上则取出对应的币种值
							if (supplierCode.equals(supplierCode2)) {
								if (null != JSONObject.parseObject(
										supplierlist.get(m).toString()).get(
										"currencyCost")) {
									currencyCost = JSONObject
											.parseObject(
													supplierlist.get(m)
															.toString())
											.get("currencyCost").toString();
								}
							}
						}
					}
					if("".equals(currencyCost)){
						currencyCost = "USD";
					}
					printer.printRecord(
							requestId,
							"WTCCNIT_UPLOAD",// WH_CONSIGN_DESC
							supplierCode,// SUPPLIER
							currencyCost,// CURRENCY_CODE
							exRate,//exchange_rate
							startDate,// START_DATE
							endDate,// END_DATE
							substituteType,// ADJUSTMENT_BASIS
							substitutePropetion,// ADJUSTMENT_VALUE
							"");
				}

			}
			out.close();
//			uploadFile("sieuat", "lLPsYmJlu5", "10.82.24.173", file,
//					"/app/local/rms");

//			uploadFile("wtccnit", "wtccnit123", "10.32.152.148", file,
//					"/usr01/ifiles/hbcnbit/inbox/itmmst_inbox",22,"sftp");
      uploadFile(configModel.getUser(), configModel.getPossword(), configModel.getHost(), file,
        configModel.getPath(),22,configModel.getOpen());
      deleteFile(file);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//
	public List<Map<String, Object>> changeType(String type,
			List<Map<String, Object>> list) {
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			String t = list.get(i).get("STORETYPE").toString();
			if (type.equals(t)) {
				list2.add(list.get(i));
			}
		}
		return list2;
	}

	public Map<String, String> addList(Map<String, String> storeList,
			List<Map<String, Object>> list) {
		for (int i = 0; i < list.size(); i++) {
			if (storeList.get(list.get(i).get("CODE")) == null) {
				storeList.put(list.get(i).get("CODE").toString(), list.get(i)
						.get("CODE").toString());
			}

		}
		return storeList;
	}

	@Override
	public void productToDrugCSV(JSONObject queryParamJSON, List<PlmProductHeadEntity_HI_RO> plist) throws IOException {
//		String sql = PlmProductHeadEntity_HI_RO.QUERY;
//		sql = sql.replace(":creatDate", queryParamJSON.get("creationDate")
//				.toString());
//		Integer productHeadId = queryParamJSON.getInteger("productHeadId");
//		if (productHeadId != null) {
//			sql = sql + " and p.product_head_id=" + productHeadId + "";
//		}
//		List<PlmProductHeadEntity_HI_RO> plist = plmProductHeadEntity_HI_RO
//				.findList(sql, new HashMap<String, Object>());
		Map<String, String> storeList0 = new HashMap<>();
		Map<String, String> storeList1 = new HashMap<>();
		plist= plist.stream().filter( p-> !StringUtils.isEmpty(p.getDrugIns())).collect(Collectors.toList());
		for (PlmProductHeadEntity_HI_RO header : plist) {
//			String storeSql = "select v.vs_code code" +
//					  " ,v.drugstore_attribute storeType "
//					+ " from PLM_PRODUCT_SUPPLIERPLACEINFO s  "
//					+ " LEFT JOIN vmi_shop v  "
//					+ " on v.vs_code = s.LOCATION  "
//					+ " where  1=1 "
//					+ " and s.PRODUCT_HEAD_ID = '97422' "
//					+ " and s.LOC_TYPE ='S' "
//					+ " and v.drugstore_attribute is not null ";
			String storeSql = "SELECT v.vs_code             code\n" +
					"                    ,v.drugstore_attribute storetype\n" +
					"                    , p.\"shop_id\" shopId\n" +
					"                FROM plm_product_supplierplaceinfo s\n" +
					"                LEFT JOIN vmi_shop v\n" +
					"                  ON v.vs_code = s.location\n" +
					"                LEFT JOIN plm_chain_store p\n" +
					"                  ON v.vs_code =  p.\"shop_id\" \n" +
					"               WHERE 1 = 1\n" +
					"                 AND s.loc_type = 'S'\n" +
					"                 AND v.drugstore_attribute IS NOT NULL\n";
//					 "                 AND s.product_head_id = 97422";
			if (header.getProductHeadId()  != null) {
				storeSql = storeSql + " and s.PRODUCT_HEAD_ID =" + header.getProductHeadId() + "";
			}else {
				continue;
			}

			List<Map<String, Object>> list = plmProductHeadEntity_HI
					.queryNamedSQLForList(storeSql,
							new HashMap<String, Object>());

			// 单体药店
//			List<Map<String, Object>> list0 = changeType("0", list);
			List<Map<String, Object>> list0=list.stream().filter(map->"0".equals(map.get("STORETYPE").toString()) ).collect(Collectors.toList());
			addList(storeList0, list0);
			if(!CollectionUtils.isEmpty(list0)){
				//去重code
				List<String> codes = list0.stream().map(map->map.get("CODE").toString()).distinct().collect(Collectors.toList());
				if(!CollectionUtils.isEmpty(codes)){
					codes.stream().forEach(code->{
						storeList0.put(code,code);
					});
				}
			}

			// 连体药店
//			List<Map<String, Object>> list1 = changeType("1", list);

//			addList(storeList1, list1);
			List<Map<String, Object>> list1=list.stream().filter(map->"1".equals(map.get("STORETYPE").toString())
					&& !ObjectUtils.isEmpty(map.get("SHOPID"))).collect(Collectors.toList());
			list1 = getShops(list1);
			if(!CollectionUtils.isEmpty(list1)){
				//去重code
				List<String> codes = list1.stream().map(map->map.get("CODE").toString()).distinct().collect(Collectors.toList());
				if(!CollectionUtils.isEmpty(codes)){
					codes.stream().forEach(code->{
						storeList1.put(code,code);
					});
				}
			}
		}
		String timeStr = getTimeStr();
		Iterator<Map.Entry<String, String>> it0 = storeList0.entrySet()
				.iterator();
		Map<String ,String > fileInfoMap = new HashMap<>();
		while (it0.hasNext()) {
			Map.Entry<String, String> entry = it0.next();
			drugCVS(entry.getValue().toString(), plist,"0",timeStr,fileInfoMap);
		}

		Iterator<Map.Entry<String, String>> it1 = storeList1.entrySet()
				.iterator();
		while (it1.hasNext()) {
			Map.Entry<String, String> entry = it1.next();
			drugCVS(entry.getValue().toString(), plist,"1",timeStr,fileInfoMap);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					LOGGER.info("上传药品：配置："+configModel.getHost2()+","+configModel.getUser2()+","+configModel.getPossword2()+", 时间:"+ consDateFormat.format(new Date()));
					boolean ff = FtpUtil.uploadFileforMult(configModel.getHost2(), configModel.getUser2(), configModel.getPossword2(),21,  "/FTP",fileInfoMap);
					LOGGER.info("结束时间:"+ consDateFormat.format(new Date())+", 返回结果："+ff);
					LOGGER.error("新增单药品上传FTP 成功");
				} catch (Exception e) {
					LOGGER.error("新增单药品上传FTP 报错");
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	public List<PlmProductHeadEntity_HI_RO> findByQuery() {
		String sql = PlmProductHeadEntity_HI_RO.QUERY;

		List<PlmProductHeadEntity_HI_RO> plist = plmProductHeadEntity_HI_RO
				.findList(sql, new HashMap<String, Object>());
		return plist;
	}

	@Override
	public List<PlmProductHeadEntity_HI_RO> findByQuery2() {
		String sql = PlmProductHeadEntity_HI_RO.QUERY2;
		List<PlmProductHeadEntity_HI_RO> plist = plmProductHeadEntity_HI_RO
				.findList(sql, new HashMap<String, Object>());
		return plist;
	}

	public void drugCVS(String storeCode, List<PlmProductHeadEntity_HI_RO> plist, String flag, String timeStr, Map<String, String> fileInfoMap)
			throws IOException {
//		String timeStr = getTimeStr();
		String file="";
		String fileName="";
		if("0".equals(flag)){
			file = "E:\\work\\广州屈臣氏项目\\CSV文件\\GSP" + storeCode + "-"
					+ timeStr+ "_0" + ".csv";
			fileName= "GSP" + storeCode + "-"+ timeStr+ "_0" + ".csv";
			String osName = System.getProperties().getProperty("os.name");
			if(osName.equals("Linux")){
				file = "GSP" + storeCode + "-"+ timeStr+ "_0" + ".csv";
			}
		}else if("1".equals(flag)){
			file = "E:\\work\\广州屈臣氏项目\\CSV文件\\GSP" + storeCode + "-"
					+ timeStr+ "_1" + ".csv";
			fileName = "GSP" + storeCode + "-"+ timeStr+ "_1" + ".csv";
			String osName = System.getProperties().getProperty("os.name");
			if(osName.equals("Linux")){
				file = "GSP" + storeCode + "-"+ timeStr+ "_1" + ".csv";
			}
		}
		FileOutputStream fos = new FileOutputStream(new File(file));
		byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
		fos.write(uft8bom);
		List<String[]> rowList = new ArrayList<String[]>();
		OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
//		String[] HEADERS = new String[] { "Reqeust ID", "productName", "symbol", "commonName", "backageUnit", "drugForm"
//				, "producer", "storeCondition", "symbolDate", "symbolDays", "protectPeriod", "displayCycle"
//				, "businessCategory", "typeCode", "categoryName", "displayDays", "sxDays", "supPrice"
//				, "isBatchnumber", "presType", "supplierCode", "supplierName", "gspManage", "drugStandcode"
//				, "drugStandard", "qaStandard", "fileInfo"};

		String[] HEADERS = new String[] {"药品代码", "药品名称", "通用名称", "规格", "包装规格"
				, "药监包装规格", "剂型", "单位", "条形码", "生产厂家", "产地"
				, "上市许可持有人", "批准文号", "储藏条件", "批文有效期", "批文预警天数", "保质期"
				, "陈列周期", "经营类别", "类别代码", "类别名称", "是否重点养护", "陈列预警天数"
				, "效期预警天数", "进价一", "进价二", "售价一", "售价四", "是否拆零", "是否属批号管理", "处方药类别"
				, "是否处方药品", "是否冷藏药品", "是否含麻黄碱", "供应商编号", "供商名称", "GSP管理级别", "药监统一编号", "药品本位码"
				, "所属经营范围", "质量标准", "批准证明文件及其附件", "OPK"};



		try (
				CSVPrinter printer = new CSVPrinter( out,CSVFormat.DEFAULT.withDelimiter(',')
						.withIgnoreEmptyLines(false)
						.withRecordSeparator("\n").withHeader(HEADERS))) {
		for (PlmProductHeadEntity_HI_RO header : plist) {

			//获取Drug的值
			Integer drugId = header.getDrugId();
			if(drugId==null){
				continue;
			}
			PlmProductDrugEntity_HI drugEntity = plmProductDrugEntity_HI.getById(header.getDrugId());
			JSONObject param = new JSONObject();
			param.put("productHeadId", header.getProductHeadId());
			param.put("varUserType", "50");
			JSONObject productInfo = plmProductHeadServer
					.findProductDetail(param);
			String str = productInfo.getJSONObject("drugInfo").toJSONString();

			JSONObject drugInfo = JSONObject.parseObject(str);
			JSONArray supplierlist = JSONArray.parseArray(JSONObject
					.toJSONString(productInfo.get("supplierlist")));
			JSONObject queryJSON = new JSONObject();
			queryJSON.put("productHeadId",header.getProductHeadId());
			List<PlmProductBarcodeTableEntity_HI> barList = plmProductBarcodeTableServer
					.findList(queryJSON);
			barList = barList("1", barList);
			JSONObject priceJSON = new JSONObject();
			priceJSON.put("productHeadId", header.getProductHeadId());
			List<PlmProductPriceEntity_HI> priceList = plmProductPriceServer
					.findList(priceJSON);

			JSONObject headInfo = JSONObject.parseObject(JSONObject
					.toJSONString(productInfo.get("headInfo")));


			String drugCode = "";//药品代码
			String productName = "";//药品名称
			String commonName = "";//通用名称
			String standard = "";// 基本单位(规格)
			String backageUnit = "";//包装规格
			String drugUnit = "";//药监包装规格
			String drugForm = "";// 剂型
			String unit = "";// 单位(规格)
			String barCode = "";// 条形码
			if (barList.size() > 0) {
				barCode = barList.get(0).getBarcode();
			}
			String producer = "";// 生产厂家
			String drugPlace = "";// 产地
			String marketPerson = "";// 上市可持有人
			String symbol = "";// 批准文号
			String storeCondition = "";// 存储条件
			String symbolDate = "";// 批文有效期
			String symbolDays = "";// 批文预警天数
			String protectPeriod = "";// 保质期
			String displayCycle = "";// 陈列周期
			String businessCategory = "";// 经营类别
			String typeCode = "";// 类别代码
			String categoryName = "";// 类别名称
			String isProtect = "";// 是否重点养护
			String displayDays = "";// 陈列预警天数
			String sxDays = "";// 效期预警天数
			String supPrice = "";// 进价一 --供应商成本价
			String supPrice2 = "";// 进价二 --供应商成本价

			String price = "";// 售价一 --价格表其中一条就好
			String price2 = "";// 售价四 --供应商成本价
			String isPull = "";// 是否拆零
			if (priceList.size() > 0) {
				price = priceList.get(0).getUnitPrice();
			}
			String isBatchnumber = "";// 是否属批号管理(默认1)
			String presType = "";// 处方药类别
			String isRx = "";// 是否处方药品(1是，0否)
			String isCold=""; //是否冷藏
			String isEphedrine="";//是否含麻黄碱
			String supplierCode = "";// 供应商编号
			String supplierName = "";// 供商名称
			String gspManage = "";// GSP管理级别
			String drugStandcode = "";// 药监统一编号
			String drugStandard = "";// 药品本位码
			String rang = "";// 所属经营范围
			String qaStandard = "";// 质量标准
			String fileInfo = "";// 批准证明文件及其附件
			String opk = "1";// OPK(1.新建、2.更新、3.停用)

			String supplierCode2 = "";

			if (null != drugInfo.get("drugCode")) {
				drugCode = drugInfo.get("drugCode").toString();
			}
			if (null != drugInfo.get("symbol")) {
				symbol = drugInfo.get("symbol").toString();
			}
			if (null != headInfo.get("productName")) {
				productName = headInfo.get("productName").toString();
			}
			if (null != drugInfo.get("commonName")) {
				commonName = drugInfo.get("commonName").toString();
			}
			if (null != drugInfo.get("standard")) {
				standard =  getMeaningForValue(DrugProductLookupEnum.DrugUnit.class, drugInfo.get("standard").toString()) ;
			}

			if (null != drugInfo.get("backageUnit")) {
				backageUnit = drugInfo.get("backageUnit").toString();
			}
			if (null != drugInfo.get("drugUnit")) {
				drugUnit = drugInfo.get("drugUnit").toString();
			}
			if (null != drugInfo.get("drugForm")) {
//				drugForm = drugInfo.get("drugForm").toString();
				drugForm = getMeaningForValue(DrugProductLookupEnum.DosageForm.class, drugInfo.get("drugForm").toString()) ;
			}
			if (null != drugInfo.get("unit")) {
				unit = drugInfo.get("unit").toString();
			}
			if (null != drugInfo.get("producer")) {
				producer = drugInfo.get("producer").toString();
				// StringBuffer sb = new
				// StringBuffer(drugInfo.get("producer").toString());
				//
				// producer = sb.toString();
				// producer = "\""+producer+"\"";
			}
			if (null != drugInfo.get("drugPlace")) {
				drugPlace = drugInfo.get("drugPlace").toString();
			}
			if (null != drugInfo.get("marketPerson")) {
				marketPerson = drugInfo.get("marketPerson").toString();
			}

			if (null != drugInfo.get("storeCondition")) {
//				storeCondition = drugInfo.get("storeCondition").toString();
				storeCondition = getMeaningForValue(DrugProductLookupEnum.DosageForm.class, drugInfo.get("storeCondition").toString()) ;
			}
			if (null != drugInfo.get("symbolDate")) {
				symbolDate = drugInfo.get("symbolDate").toString();
			}
			if (null != drugInfo.get("symbolDays")) {
				symbolDays = drugInfo.get("symbolDays").toString();
			}
			if (null != drugInfo.get("protectPeriod")) {
				protectPeriod = drugInfo.get("protectPeriod").toString();
			}
			if (null != drugInfo.get("displayCycle")) {
				displayCycle = drugInfo.get("displayCycle").toString();
			}
			if (null != drugInfo.get("businessCategory")) {
//				businessCategory = drugInfo.get("businessCategory").toString();
				businessCategory = getMeaningForValue(DrugProductLookupEnum.BusinessCategory.class, drugInfo.get("businessCategory").toString()) ;

			}
			if (null != drugInfo.get("typeCode")) {
				typeCode = drugInfo.get("typeCode").toString();
			}
			if (null != drugInfo.get("categoryName")) {
				categoryName = drugInfo.get("categoryName").toString();
			}
			if (null != drugInfo.get("isProtect")) {
				isProtect = drugInfo.get("isProtect").toString();
			}

			if (null != drugInfo.get("displayDays")) {
				displayDays = drugInfo.get("displayDays").toString();
			}
			if (null != drugInfo.get("sxDays")) {
				sxDays = drugInfo.get("sxDays").toString();
			}
			if (null != drugInfo.get("supPrice")) {
				supPrice = drugInfo.get("supPrice").toString();
			}
			if (null != drugInfo.get("isPull")) {
				isPull = drugInfo.get("isPull").toString();
			}
			if (null != drugInfo.get("isBatchnumber")) {
				isBatchnumber = drugInfo.get("isBatchnumber").toString();
			}
			if (null != drugInfo.get("presType")) {
//				presType = drugInfo.get("presType").toString();
				presType = getMeaningForValue(DrugProductLookupEnum.DrugTypeCode.class, drugInfo.get("presType").toString()) ;

			}
			if (null != drugInfo.get("isRx")) {
				isRx = drugInfo.get("isRx").toString();
			}
			if (null != drugInfo.get("isCold")) {
				isCold = drugInfo.get("isCold").toString();
			}
			if (null != drugInfo.get("isEphedrine")) {
				isEphedrine = drugInfo.get("isEphedrine").toString();
			}
			if (null != drugInfo.get("supplierCode")) {
				supplierCode = drugInfo.get("supplierCode").toString();
			}
			if (null != drugInfo.get("supplierName")) {
				supplierName = drugInfo.get("supplierName").toString();
			}
			if (null != drugInfo.get("gspManage")) {
//				gspManage = drugInfo.get("gspManage").toString();
				gspManage = getMeaningForValue(DrugProductLookupEnum.Gps.class, drugInfo.get("gspManage").toString()) ;

			}
			if (null != drugInfo.get("drugStandcode")) {
				drugStandcode = drugInfo.get("drugStandcode").toString();
			}
			if (null != drugInfo.get("drugStandard")) {
				drugStandard = drugInfo.get("drugStandard").toString();
			}
			if (null != drugInfo.get("rangName")) {
				rang = drugInfo.get("rangName").toString();
				if(!StringUtils.isEmpty(rang)){
					rang= rang.replace(",","/");
				}
			}
			if (null != drugInfo.get("qaStandard")) {
				qaStandard = drugInfo.get("qaStandard").toString();
			}
			if (null != drugInfo.get("fileInfo")) {
				fileInfo = drugInfo.get("fileInfo").toString();
			}

			// 轮询匹配供应商
			for (int m = 0; m < supplierlist.size(); m++) {
				if (null != JSONObject.parseObject(
						supplierlist.get(m).toString()).get("supplierCode")) {
					supplierCode2 = JSONObject
							.parseObject(supplierlist.get(m).toString())
							.get("supplierCode").toString();
				}// 如果供应商编号对上则取出对应的币种值
				if (supplierCode.equals(supplierCode2)) {
					if (null != JSONObject.parseObject(
							supplierlist.get(m).toString()).get("price")) {
						supPrice = JSONObject
								.parseObject(supplierlist.get(m).toString())
								.get("price").toString();
					}
				}
			}
				printer.printRecord(
						 drugCode
						,productName
						,commonName
						,standard
						,backageUnit
						,drugUnit
						,drugForm
						,unit
						,barCode
						,producer
						,drugPlace
						,marketPerson
						,symbol
						,storeCondition
						,symbolDate
						,symbolDays
						,protectPeriod
						,displayCycle
						,businessCategory
						,typeCode
						,categoryName
						,isProtect
						,displayDays
						,sxDays
						,supPrice
						,supPrice2
						,price
						,price2
						,isPull
						,isBatchnumber
						,presType
						,isRx
						,isCold
						,isEphedrine
						,supplierCode
						,supplierName
						,gspManage
						,drugStandcode
						,drugStandard
						,rang
						,qaStandard
						,fileInfo
						,opk
				);
			List<String> list = new ArrayList<String>();
			list.add(drugCode + ",");
			list.add("\"" + productName + "\",");
			list.add("\"" + commonName + "\",");
			list.add("\"" + backageUnit + "\",");
			list.add("\"" + drugForm + "\",");
			list.add("\"" + unit + "\",");
			list.add(barCode + ",");
			list.add("\"" + producer + "\",");
			list.add("\"" + symbol + "\",");
			list.add(storeCondition + ",");
			list.add(symbolDate.split(" ")[0] + ",");
			list.add(symbolDays + ",");
			list.add(protectPeriod + ",");
			list.add(displayCycle + ",");
			list.add(businessCategory + ",");
			list.add(typeCode + ",");
			list.add(categoryName + ",");
			list.add(displayDays + ",");
			list.add(sxDays + ",");
			list.add(supPrice + ",");
			list.add(price + ",");
			list.add(isBatchnumber + ",");
			list.add(presType + ",");
			list.add(isRx + ",");
			list.add(supplierCode + ",");
			list.add(supplierName + ",");
			list.add(gspManage + ",");
			list.add(drugStandcode + ",");
			list.add(drugStandard + ",");
			list.add(rang + ",");
			list.add(qaStandard + ",");
			list.add(fileInfo + ",");
			list.add(opk + ",");
			list.add("");
			String[] row = new String[list.size()];
			list.toArray(row);
			rowList.add(row);
		}
		String[][] rows = new String[rowList.size()][34];
		rowList.toArray(rows);
		out.close();
		fileInfoMap.put(fileName,file);
//			FileInputStream in=new FileInputStream(new File(file));
////			uploadFileFtp("gspkc", "123@abc#", "10.82.96.76", in,
////					"/FTP",21,"ftp");
//			boolean ff = FtpUtil.uploadFile(configModel.getHost2(), configModel.getUser2(), configModel.getPossword2(),21,  "/FTP",fileName,  in);
//
//		 deleteFile(file);
		} catch (Exception e) {
		 e.printStackTrace();
		 }
	}

	/**
	 * 枚举类反射取值法
	 * @param enumType 枚举类 Class
	 * @param compare 对比值
	 * @param <T>  枚举类型 Enum
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public  <T extends Enum<T>> String getMeaningForValue(Class<T> enumType, String compare ) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		EnumSet<T> enumSet = EnumSet.allOf(enumType);
		for ( Object obj : enumType.getEnumConstants()){
            Class[] cArg = new Class[1];
            cArg[0] = String.class;
			Method m = obj.getClass().getDeclaredMethod("getCode", null);
			Method m2 = obj.getClass().getDeclaredMethod("getValue", null);
			String code = (String) m.invoke(obj, null);
			if ( compare.equals(code)){
				return (String) m2.invoke(obj, null);
			}
		}
		return "";
	}
	public List<Map<String, Object>> getShops(List<Map<String, Object>> list) {
		List<Map<String, Object>> list0 = new ArrayList<>();
		String storeSql = "select s.\"shop_id\" shopId,s.\"shop_name\" shopName,s.\"parent_id\" parentId   "
				+ " from PLM_CHAIN_STORE s  ";

		List<Map<String, Object>> shopList = plmProductHeadEntity_HI
				.queryNamedSQLForList(storeSql, new HashMap<String, Object>());

		List <String > shopids = shopList.stream().map(map->map.get("SHOPID").toString()).collect(Collectors.toList());
		for (int i = 0; i < shopList.size(); i++) {
			for (int k = 0; k < list.size(); k++) {
				if (shopList.get(i).get("SHOPID")
						.equals(list.get(k).get("CODE"))) {
					list0.add(list.get(k));
				}
			}
		}
		return list0;
	}

	public static String join(String[] strArr, String delim) {
		StringBuilder sb = new StringBuilder();
		for (String s : strArr) {
			sb.append(s);
			sb.append(delim);
		}
		String ret;
		if (strArr.length > 1) {
			ret = sb.substring(0, sb.length() - 1);
		} else {
			ret = sb.toString();
		}
		return ret;
	}

	public boolean deleteFile(String sPath) {
		Boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			boolean deleteResult = file.delete();
			flag = deleteResult;
		}
		return flag;
	}


    @Override
	public Boolean uploadFile(String user, String password, String host,
			String localFile, String path,Integer port,String open) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel(open);
			sftpChannel.connect();

			sftpChannel.put(localFile, path);

			sftpChannel.disconnect();
			session.disconnect();
		} catch (JSchException | SftpException e) {
			System.out.println(e);
		}
		return true;
	}

	@Override
	public String csvGetResult(String params, String timeStr)
			throws IOException {
		String serviceFileStart = "SP_STATUS_RPT_21-FEB-";
		String downPath = "E:\\work\\QCS\\CSV\\down\\" + getTimeStr2() + "\\"
				+ timeStr + ".csv";
		Session session = null;
		ChannelSftp sftpChannel = null;
		InputStream is = null;
		FileOutputStream fileOutputStream = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(serviceContents.username,
					serviceContents.host, serviceContents.port);
			session.setPassword(serviceContents.password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			// 获取文件

			File file = new File(downPath);
			if (!file.isDirectory()) {
				file.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			is = sftpChannel.get(serviceContents.directory + "/"
					+ serviceFileStart + timeStr + ".csv");
			List<String[]> list = CsvUtils.csvToList(is);
			sftpChannel.cd("/app/local/rms");
			sftpChannel.rm(serviceFileStart + timeStr + ".csv");
			sftpChannel.disconnect();
			session.disconnect();
			is.close();
			StringBuilder resultStr = new StringBuilder("结果：");
			if (!CollectionUtils.isEmpty(list)) {
				for (int i = 1; i < list.size(); i++) {
					String[] strs = list.get(i);
					PlmProductHeadEntity_HI productHeadEntityHi = plmProductHeadServer
							.getObjByplmCode("20" + strs[0]);
					if ("success".equals(strs[2]) && strs[0] != null) {
						// 同步成功
						productHeadEntityHi.setRmsCode(strs[1]);
						productHeadEntityHi.setRmsStatus(strs[2]);
						productHeadEntityHi.setRmsRemake(strs[3]);
					} else {
						// 同步失败
						productHeadEntityHi.setRmsStatus(strs[2]);
						productHeadEntityHi.setRmsRemake(strs[3]);
					}
					plmProductHeadEntity_HI.saveOrUpdate(productHeadEntityHi);
				}
			}
			return "S";
		} catch (Exception e) {
			sftpChannel.disconnect();
			session.disconnect();
			e.printStackTrace();
		}
		return "S";
	}

	@Override
	public void productSalePropertiesToCSV(JSONObject queryParamJSON) throws Exception {
		String sql = PlmProductSalePropertiesEntity_HI_RO.QUERY_SALE_PROPERTIES;
		Object ros =plmProductPropertiesEntityHiRo.findList(sql);
		List<Map<String,Object>> maps = (List<Map<String,Object>>)ros;
		String timeStr = getTimeStr();
		if(CollectionUtils.isEmpty(maps)){LOGGER.info("时间日期："+timeStr+", 没有销售属性！"); return;}
//		JSONArray array = JSONArray.parseArray(maps.toString());
		String rmsCode="";
		String seqBatch="";
		String file ="E:\\work\\广州屈臣氏项目\\CSV文件\\IF321_LINCES_"
				+ timeStr +"_"+timeStr.substring(0,8)+"_"+timeStr.substring(8)+ ".csv";
		String osName = System.getProperties().getProperty("os.name");
		if (osName.equals("Linux")) {
			file = "IF321_LINCES_"+ timeStr +"_"+timeStr.substring(0,8)+"_"+timeStr.substring(8)+ ".csv";
		}


		String[] HEADERS = new String[] {"Batch No.|Store|Item Type(UDA/Dept/Item/class/subclass)|Value1|Value2|Value3|Status|Modify Type(I/E/U)|Effective Date (yyyy/mm/dd)"};
		FileOutputStream fos = new FileOutputStream(new File(file));
		byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
		fos.write(uft8bom);
		OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
				.withHeader(HEADERS).withDelimiter(',')
				.withIgnoreEmptyLines(false)
				.withRecordSeparator("\n"))) {
			for (int n = 0; n < maps.size(); n++) {
				JSONObject ro = JSONObject.parseObject(JSONObject.toJSONString(maps.get(n))) ;
				if(n==0 || !rmsCode.equals(ro.get("VALUE1"))){
					rmsCode= ro.getString("VALUE1");
					//获取序列号
					seqBatch = getSeqBatch();
				}
				printer.printRecord(
						        seqBatch
								+"|"+(ro.get("PLACEDETAIL")==null?"":ro.get("PLACEDETAIL")) +"|"+"ITEM"
								+"|"+ro.get("VALUE1")                                     +"|" +(ro.get("VALUE2")==null?"":ro.get("VALUE2"))
								+"|"+(ro.get("VALUE3")==null?"":ro.get("VALUE3"))         +"|" +ro.get("STATUS")
								+"|"+"I"      +"|"       +  ro.get("EFFECTIVEDATE")
						);
			}

		}catch (Exception e ){
			out.close();
			throw new Exception(e.getMessage());
		}
		out.close();
//		uploadFile("wtccnit", "wtccnit123", "10.32.152.148", file,
//				"/usr01/ifiles/hbcnbit/inbox",22,"sftp");
    uploadFile(configModel.getUser(), configModel.getPossword(), configModel.getHost(), file,
      configModel.getPath(),22,configModel.getOpen());
		deleteFile(file);
	}

	private String getSeqBatch() {
		Object seqRos = plmProductPropertiesEntityHiRo.findList(PlmProductSalePropertiesEntity_HI_RO.SALES_PROPERTIES_RMS_BATCHNO_SEQ);
		List<Map<String,Object>> seqRoMap = (List<Map<String,Object>>)seqRos;
		JSONObject seqRo = JSONObject.parseObject(JSONObject.toJSONString(seqRoMap.get(0))) ;
		String seqBatch= seqRo.getString("SEQ");
		return seqBatch;
	}

	public static void main(String[] args) {
	String result=new PlmSupProductServer().getPlace("中国-北京,中国-辽宁-沈阳,阿根廷");
	System.out.println(result);
	}

	public static String getTimeStr() {
		String timeStr = "";
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		timeStr = sf.format(date);
		return timeStr;
	}

	public String getTimeStr2() {
		String timeStr = "";
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		timeStr = sf.format(date);
		return timeStr;
	}

}
