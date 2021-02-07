package com.sie.watsons.base.product.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentInfoEntity_HI;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentInfo;
import com.sie.watsons.base.plmBase.model.entities.*;
import com.sie.watsons.base.plmBase.model.inter.*;
import com.sie.watsons.base.product.model.entities.*;
import com.sie.watsons.base.product.model.entities.readonly.*;
import com.sie.watsons.base.product.model.inter.*;
import com.sie.watsons.base.product.services.PlmProductHeadService;
import com.sie.watsons.base.productEco.model.entities.PlmProductSupplierInfoEcoEntity_HI;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.ServerException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sie.saaf.common.services.CommonAbstractService.SUCCESS_MSG;
import static com.sie.saaf.common.services.CommonAbstractService.SUCCESS_STATUS;

@Component("plmProductHeadServer")
public class PlmProductHeadServer extends
		BaseCommonServer<PlmProductHeadEntity_HI> implements IPlmProductHead {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductHeadServer.class);

	// @Autowired
	// private IPlmApi iPlmApiServer;

	@Autowired
	private IFastdfs fastdfsServer;

	@Autowired
	private BaseViewObject<BaseUsersPerson_HI_RO> baseUsersPersonDAO_HI_RO;

	@Autowired
	private ViewObject<PlmProductHeadEntity_HI> plmProductHeadDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductHeadEntity_HI_RO> plmProductHeadEntity_HI_RO;
	@Autowired
	private BaseViewObject<PlmProductAddReport> PlmProductAddReportRO;
	@Autowired
	private BaseViewObject<PlmProductAddReportT> PlmProductAddReportTRO;
	@Autowired
	private BaseViewObject<PlmSupplierQaReport> PlmSupplierQaReportRO;

	@Autowired
	private BaseViewObject<PlmProductConditionReport> PlmProductConditionReportRO;
	@Autowired
	private IPlmProductBarcodeTable plmProductBarcodeTableDao;
	@Autowired
	private IPlmDevelopmentInfo IPlmDevelopmentInfoDao;
	// 2019/11/1
	@Autowired
	private BaseViewObject<PlmProductBarcodeTableEntity_HI> PlmProductBarcodeTableDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductFileEntity_HI> PlmProductFileDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductOnlineChannalEntity_HI> PlmProductOnlineChannalDAO_HI;
	@Autowired
	private ViewObject<PlmProductPicfileTableEntity_HI> PlmProductPicfileTableDAO_HI;
	@Autowired
	private ViewObject<PlmProductSupplierInfoEntity_HI> plmProductSupplierInfoDAO_HI;

	@Autowired
	private BaseViewObject<PlmProductSupplierInfoEntity_HI_RO> plmProductSupplierInfoDAO_HI_RO;

	@Autowired
	private ViewObject<PlmProductSalesPropertiesEntity_HI> PlmProductSalesPropertiesDAO_HI;
	@Autowired
	private ViewObject<PlmProductQaFileEntity_HI> PlmProductQaFileDAO_HI;
	@Autowired
	private BaseViewObject<PlmProductQaFileEntity_HI_RO> plmProductQaFileDAO_HI_RO;
	@Autowired
	private ViewObject<PlmProductPriceEntity_HI> PlmProductPriceDAO_HI;
	@Autowired
	private ViewObject<PlmProductDrugEntity_HI> PlmProductDrugDAO_HI;
	@Autowired
	private ViewObject<PlmProductMedicalinfoEntity_HI> PlmProductMedicalinfoDAO_HI;
	//
	@Autowired
	private IPlmProductFile plmProductFileDao;

	@Autowired
	private IPlmProductOnlineChannal plmProductOnlineChannalDao;
	@Autowired
	private IPlmProductLog plmProductLog;
	@Autowired
	private IPlmProductPicfileTable plmProductPicfileTableDao;

	@Autowired
	private IPlmProductPrice plmProductPriceDao;
	@Autowired
	private IPlmProductQaFile plmProductQaFileDao;
	@Autowired
	private IPlmProductSalesProperties plmProductSalesPropertiesDao;
	@Autowired
	private IPlmProductSupplierInfo plmProductSupplierInfoDao;

	@Autowired
	private ViewObject<BaseUsersEntity_HI> BaseUserDAO_HI;

	@Autowired
	private IPlmProductDrug plmProductDrug;

	@Autowired
	private IPlmProductMedicalinfo plmMedicainfo;

	@Autowired
	private IPlmDevelopmentInfo developinfo;

	@Autowired
	private GenerateCodeServer generateCodeServer;
	@Autowired
	private PlmProductUpdatepropertisServer plmProductUpdatepropertis;

	@Autowired
	private ViewObject<PlmProductModifyCheckEntity_HI> plmProductModifyCheckServer;

	@Autowired
	private PlmProductModifyCheckServer plmProductModify;

	@Autowired
	private IPlmProductStore plmProductStoreServer; // 门店表
	@Autowired
	private IPlmDeptList plmDeptlistServer; // 部门
	@Autowired
	private IPlmDeptClass plmDeptclassServer; // 分类
	@Autowired
	private IPlmDeptSubclass plmDeptSubclass;// 子分类
	@Autowired
	private IPlmTaxTypeList plmTaxType; // 税收分类编码
	@Autowired
	private IPlmLocationList ploList; // 地点清单
	@Autowired
	private IPlmBrandInfo brandinfo; // 品牌

	@Autowired
	private IPlmSupplierQaNonObInfo plmSupplierQaNonObInfoServer;

	@Autowired
	private ViewObject<PlmProductSupplierplaceinfoEntity_HI> placeinfoServer;

	@Autowired
	private IPlmDataAclHeader plmDataAclHeaderServer;// 权限表
	@Autowired
	private IPlmDataAclLine IPlmDataAclLineServer; // 权限行表

	@Autowired
	private BaseViewObject<PlmProductQaReport> plmProductQaReportRO;

	@Autowired
	private BaseViewObject<PlmProductSupplierplaceinfoEntity_HI> suplace;

//	@Autowired
//	private IPlmProductExtendAttribute productExtendAttributeServer;

	private static deptNames dept = new deptNames();

	public PlmProductHeadServer() {
		super();
	}

	public static void main(String[] args) throws IllegalAccessException {
		PlmProductSupplierInfoEntity_HI old=new PlmProductSupplierInfoEntity_HI();
            old.setPrice("1.5");
				PlmProductSupplierInfoEcoEntity_HI obj=	new PlmProductSupplierInfoEcoEntity_HI();
				obj.setPrice("1.6");


	}

	@Override
	public JSONObject saveProductInfo(JSONObject queryParamJSON)
			throws ServerException {
		// placeinfoServer.
		String UserType = queryParamJSON.getString("varUserType"); // 用户类型
		String userName = queryParamJSON.getString("varUserName");
		String vendcode = queryParamJSON.getString("vendcode");
		JSONObject param = queryParamJSON.getJSONObject("headInfo"); // 商品头表信息
		JSONArray supplierlist = queryParamJSON.getJSONArray("supplierlist");// 供应商行信息
		JSONArray barcodelist = queryParamJSON.getJSONArray("barcodelist");// 条码行信息
		JSONArray picfilelist = null;
		JSONArray qalist = null;
		JSONArray pricelist = null;
		JSONArray saleslist = null;
		JSONArray channallist = null;
		JSONArray filelist = null;
		JSONObject drugobj = null;
		JSONObject medicalobj = null;
		JSONArray store = null;
		if (queryParamJSON.containsKey("storelist")) {
			store = queryParamJSON.getJSONArray("storelist");
		}

		if (queryParamJSON.containsKey("picfilelist")) {
			picfilelist = queryParamJSON.getJSONArray("picfilelist");// 图片文件行信息
		}
		if (queryParamJSON.containsKey("qalist")) {
			qalist = queryParamJSON.getJSONArray("qalist");// 资质文件行信息
		}

		if (queryParamJSON.containsKey("pricelist")) {
			pricelist = queryParamJSON.getJSONArray("pricelist");// 商品包装行信息
		}
		if (queryParamJSON.containsKey("saleslist")) {
			saleslist = queryParamJSON.getJSONArray("saleslist");// 可销售属性行信息
		}

		if (queryParamJSON.containsKey("channallist")) {
			channallist = queryParamJSON.getJSONArray("channallist");// 线上渠道行信息
		}
		if (queryParamJSON.containsKey("filelist")) {
			filelist = queryParamJSON.getJSONArray("filelist");// 附件行信息
		}

		if (queryParamJSON.containsKey("drugInfo")) {

			drugobj = queryParamJSON.getJSONObject("drugInfo");// 药品信息
		}

		// medicalInfo
		if (queryParamJSON.containsKey("medicalInfo")) {
			medicalobj = queryParamJSON.getJSONObject("medicalInfo");
		}

		try {

			String buttonStatus = "";
			String orderStatus = "";
			String supplierstatus = "";
			if (param.containsKey("buttonStatus")) {
				buttonStatus = param.getString("buttonStatus");
			}
			if (param.containsKey("orderStatus")) {
				orderStatus = param.getString("orderStatus");
			}

			if (orderStatus.equals("1") && buttonStatus.equals("save")) {
				supplierstatus = "1";
			}
			if (orderStatus.equals("2") && buttonStatus.equals("save")) {
				supplierstatus = "2";
			}
			// 保存头表信息
			if (orderStatus.equals("1") && buttonStatus.equals("submit")) // 采购商并且订单状态为(采购发起)状态在提交的时候
			{
				param.put("supplierCount", supplierlist.size()); // 设置供应商未提交个数
				param.remove("orderStatus");
				param.put("orderStatus", "2"); // 变为供应商完善中 状态
				supplierstatus = "1";
			}

			// 供应商类型(提交状态)
			if (UserType.equals("60") && buttonStatus.equals("submit")
					&& orderStatus.equals("2")) {
				supplierstatus = "2";
				if (supplierlist.size() > 0) {
					JSONObject supplierobj = supplierlist.getJSONObject(0); // 只能看到自己供应商信息,取第一行
					String isSubmit = "0";
					if (supplierobj.containsKey("isSubmit")) {
						isSubmit = supplierobj.getString("isSubmit"); // 行表供应商提交状态
					}
					if (isSubmit.equals("0")) { // 供应商未提交
						Integer count = param.getInteger("supplierCount"); // 供应商个数
						if (count <= 1) {
							param.remove("orderStatus");
							param.put("orderStatus", "3");
							param.remove("supplierCount");
							param.put("supplierCount", "0");
						} else {
							Integer newcount = count - 1;
							param.remove("supplierCount");
							param.put("supplierCount", newcount);

						}
						supplierobj.remove("isSubmit");
						supplierobj.remove("returnStatus");
						supplierobj.put("isSubmit", "1");
						supplierobj.put("returnStatus", "1");
					}
				}
			} else if (!UserType.equals("60") && buttonStatus.equals("submit")
					&& orderStatus.equals("2")) {
				supplierstatus = "3";
				param.remove("orderStatus");
				param.put("orderStatus", "3");
				param.remove("supplierCount");
				param.put("supplierCount", "0");
				for (int i = 0; i < supplierlist.size(); i++) {
					JSONObject supplierobj = supplierlist.getJSONObject(i);
					supplierobj.remove("isSubmit");
					supplierobj.remove("returnStatus");
					supplierobj.put("isSubmit", "1");
					supplierobj.put("returnStatus", "1");
				}

			}// if

			if (orderStatus.equals("3")) { // 供应商状态
				supplierstatus = "3";
				// if (buttonStatus.equals("submit")) {
				// param.remove("orderStatus");
				// param.put("orderStatus", "4"); // 新增审批中
				// }
			}
			// ---------
			param.put("operatorUserId",
					queryParamJSON.getInteger("operatorUserId"));
			if (!param.containsKey("productHeadId")) { // 新增
				param.put("plmCode", this.getPlmProduct());
				param.put("createdstr",
						queryParamJSON.getString("varUserFullName"));
				param.put("createdEmp", queryParamJSON.getString("createdEmp"));
				param.put("createdEname",
						queryParamJSON.getString("createdEname"));
				param.put("userDept", queryParamJSON.getString("userDept"));
				param.put("userGroupcode",
						queryParamJSON.getString("userGroupcode"));
			}

			PlmProductHeadEntity_HI saveproduct = this.saveOrUpdate(param);

			// 保存行表信息
			if (store != null) {
				for (int i = 0; i < store.size(); i++) {
					JSONObject storeobj = store.getJSONObject(i);
					storeobj.put("productHeadId",
							saveproduct.getProductHeadId());
					storeobj.put("operatorUserId",
							queryParamJSON.getInteger("operatorUserId"));
					plmProductStoreServer.saveOrUpdate(storeobj);
				}
			}

			for (int i = 0; i < supplierlist.size(); i++) { // 新增或修改供应商行信息
				JSONObject supplierobj = supplierlist.getJSONObject(i);
				if (supplierobj.containsKey("sxWay")) {// 生效方式
					String sxway = supplierobj.getString("sxWay");
					if (sxway.equals("1") || sxway.equals("2")) { // 全部仓+店
						supplierobj.put("sxWarehouse", "");
						supplierobj.put("sxStore", "");
						supplierobj.put("placeNote", "");
						supplierobj.put("area", "");
						supplierobj.put("placeCode", "");
						supplierobj.put("areaId", "");
						supplierobj.put("sxWarehouseId", "");
					} else if (sxway.equals("3")) {// 地点清单
						supplierobj.put("sxWarehouse", "");
						supplierobj.put("sxStore", "");
						supplierobj.put("area", "");
						supplierobj.put("areaId", "");
						supplierobj.put("sxWarehouseId", "");
					} else if (sxway.equals("4")) {// 指定仓+店
						supplierobj.put("placeNote", "");
						supplierobj.put("area", "");
						supplierobj.put("placeCode", "");
						supplierobj.put("areaId", "");
					} else if (sxway.equals("5")) {// 区域
						supplierobj.put("sxWarehouse", "");
						supplierobj.put("sxWarehouseId", "");
						supplierobj.put("sxStore", "");
						supplierobj.put("placeNote", "");
						supplierobj.put("placeCode", "");
					} else if (sxway.equals("6") || sxway.equals("9")) {// 指定仓
						supplierobj.put("sxStore", "");
						supplierobj.put("placeNote", "");
						supplierobj.put("area", "");
						supplierobj.put("placeCode", "");
						supplierobj.put("areaId", "");
					} else if (sxway.equals("7")) { // 指定店铺
						supplierobj.put("sxWarehouse", "");
						supplierobj.put("sxWarehouseId", "");
						supplierobj.put("placeNote", "");
						supplierobj.put("area", "");
						supplierobj.put("placeCode", "");
						supplierobj.put("areaId", "");
					} else if (sxway.equals("8")) { // 指定仓+地点清单
						supplierobj.put("sxStore", "");
						supplierobj.put("area", "");
						supplierobj.put("areaId", "");
					}
				}
				// 采购操作时
				if (supplierstatus.equals("1")) {
					supplierobj.remove("isSubmit");
					supplierobj.remove("returnStatus");
					supplierobj.put("isSubmit", "0");
					supplierobj.put("returnStatus", "0");
					String supplierId = supplierobj.getString("supplierCode");
					// 默认新增图片文件
					if (!supplierobj.containsKey("id")) { // 新增操作
						PlmProductPicfileTableEntity_HI pic1 = new PlmProductPicfileTableEntity_HI();
						pic1.setPicType("1");
						pic1.setSupplierId(supplierId);
						pic1.setProductHeadId(saveproduct.getProductHeadId());
						pic1.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic2 = new PlmProductPicfileTableEntity_HI();
						pic2.setPicType("2");
						pic2.setSupplierId(supplierId);
						pic2.setProductHeadId(saveproduct.getProductHeadId());
						pic2.setIsUpdate("0");
						pic2.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic3 = new PlmProductPicfileTableEntity_HI();
						pic3.setPicType("3");
						pic3.setSupplierId(supplierId);
						pic3.setProductHeadId(saveproduct.getProductHeadId());
						pic3.setIsUpdate("0");
						pic3.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic4 = new PlmProductPicfileTableEntity_HI();
						pic4.setPicType("4");
						pic4.setSupplierId(supplierId);
						pic4.setProductHeadId(saveproduct.getProductHeadId());
						pic4.setIsUpdate("0");
						pic4.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic5 = new PlmProductPicfileTableEntity_HI();
						pic5.setPicType("5");
						pic5.setSupplierId(supplierId);
						pic5.setProductHeadId(saveproduct.getProductHeadId());
						pic5.setIsUpdate("0");
						pic5.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic6 = new PlmProductPicfileTableEntity_HI();
						pic6.setPicType("6");
						pic6.setSupplierId(supplierId);
						pic6.setProductHeadId(saveproduct.getProductHeadId());
						pic6.setIsUpdate("0");
						pic6.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic7 = new PlmProductPicfileTableEntity_HI();
						pic7.setPicType("7");
						pic7.setSupplierId(supplierId);
						pic7.setProductHeadId(saveproduct.getProductHeadId());
						pic7.setIsUpdate("0");
						pic7.setIsRequire("Y");
						List<PlmProductPicfileTableEntity_HI> lidata = new ArrayList<PlmProductPicfileTableEntity_HI>();
						lidata.add(pic1);
						lidata.add(pic2);
						lidata.add(pic3);
						lidata.add(pic4);
						lidata.add(pic5);
						lidata.add(pic6);
						lidata.add(pic7);
						plmProductPicfileTableDao.save(lidata);

						// 药品或医疗器械时 添加默认资质文件
						String otherinfo = saveproduct.getOtherInfo();
						if (otherinfo.equals("1")) // 药品
						{
							List<PlmProductQaFileEntity_HI> qadata = new ArrayList<PlmProductQaFileEntity_HI>();
							PlmProductQaFileEntity_HI q1 = new PlmProductQaFileEntity_HI();
							q1.setQaFiletype("药品注册证");
							q1.setSupplierId(supplierId);
							q1.setProductHeadId(saveproduct.getProductHeadId());
							q1.setQaCodetype("3");
							q1.setDateType("3");
							q1.setIsUpdate("0");
							PlmProductQaFileEntity_HI q2 = new PlmProductQaFileEntity_HI();
							q2.setQaFiletype("质量标准");
							q2.setSupplierId(supplierId);
							q2.setProductHeadId(saveproduct.getProductHeadId());
							q2.setQaCodetype("5");
							q2.setDateType("3");
							q2.setIsUpdate("0");
							PlmProductQaFileEntity_HI q3 = new PlmProductQaFileEntity_HI();
							q3.setQaFiletype("标签备案样式");
							q3.setSupplierId(supplierId);
							q3.setProductHeadId(saveproduct.getProductHeadId());
							q3.setQaCodetype("5");
							q3.setDateType("3");
							q3.setIsUpdate("0");
							PlmProductQaFileEntity_HI q4 = new PlmProductQaFileEntity_HI();
							q4.setQaFiletype("说明书备案样式");
							q4.setSupplierId(supplierId);
							q4.setProductHeadId(saveproduct.getProductHeadId());
							q4.setQaCodetype("5");
							q4.setDateType("3");
							q4.setIsUpdate("0");
							qadata.add(q1);
							qadata.add(q2);
							qadata.add(q3);
							qadata.add(q4);
							plmProductQaFileDao.save(qadata);

						} else if (otherinfo.equals("2")) // 医疗器械
						{
							List<PlmProductQaFileEntity_HI> qadata = new ArrayList<PlmProductQaFileEntity_HI>();
							PlmProductQaFileEntity_HI q1 = new PlmProductQaFileEntity_HI();
							q1.setQaFiletype("产品技术要求");
							q1.setSupplierId(supplierId);
							q1.setProductHeadId(saveproduct.getProductHeadId());
							q1.setQaCodetype("2");
							q1.setDateType("3");
							q1.setIsUpdate("0");
							PlmProductQaFileEntity_HI q2 = new PlmProductQaFileEntity_HI();
							q2.setQaFiletype("合格证明文件");
							q2.setSupplierId(supplierId);
							q2.setProductHeadId(saveproduct.getProductHeadId());
							q2.setQaCodetype("5");
							q2.setDateType("3");
							q2.setIsUpdate("0");
							PlmProductQaFileEntity_HI q3 = new PlmProductQaFileEntity_HI();
							q3.setQaFiletype("标签备案样式");
							q3.setSupplierId(supplierId);
							q3.setProductHeadId(saveproduct.getProductHeadId());
							q3.setQaCodetype("5");
							q3.setDateType("3");
							q3.setIsUpdate("0");
							PlmProductQaFileEntity_HI q4 = new PlmProductQaFileEntity_HI();
							q4.setQaFiletype("说明书备案样式");
							q4.setSupplierId(supplierId);
							q4.setProductHeadId(saveproduct.getProductHeadId());
							q4.setQaCodetype("5");
							q4.setDateType("3");
							q4.setIsUpdate("0");
							qadata.add(q1);
							qadata.add(q2);
							qadata.add(q3);
							qadata.add(q4);
							plmProductQaFileDao.save(qadata);
						}
					}
				} else if (supplierstatus.equals("3")) {
					supplierobj.remove("isSubmit");
					supplierobj.remove("returnStatus");
					supplierobj.put("isSubmit", "1");
					supplierobj.put("returnStatus", "1");
				} else if (supplierstatus.equals("2")) {
					if (orderStatus.equals("2") && buttonStatus.equals("save")) {
						supplierobj.remove("isSubmit");
						supplierobj.remove("returnStatus");
						supplierobj.put("isSubmit", "0");
						supplierobj.put("returnStatus", "0");
					}
				}

				supplierobj
						.put("productHeadId", saveproduct.getProductHeadId());
				supplierobj.put("operatorUserId",
						queryParamJSON.getInteger("operatorUserId"));
				plmProductSupplierInfoDao.saveOrUpdate(supplierobj);

			}

			String mainBarcode = "";
			for (int i = 0; i < barcodelist.size(); i++) { // 新增或修改条码行信息
				JSONObject barcodeobj = barcodelist.getJSONObject(i);
				if (barcodeobj.containsKey("isMain")) {
					if (barcodeobj.getString("isMain").equals("Y")) {
						mainBarcode = barcodeobj.getString("barcode");
					}
				}

				barcodeobj.put("productHeadId", saveproduct.getProductHeadId());
				barcodeobj.put("operatorUserId",
						queryParamJSON.getInteger("operatorUserId"));
				barcodeobj.put("lastUpdateLogin", 1);
				plmProductBarcodeTableDao.saveOrUpdate(barcodeobj);
			}

			if (picfilelist != null) // 新增或修改图片行信息
			{
				for (int i = 0; i < picfilelist.size(); i++) {
					JSONObject picfileobj = picfilelist.getJSONObject(i);
					picfileobj.put("productHeadId",
							saveproduct.getProductHeadId());
					picfileobj.put("operatorUserId",
							queryParamJSON.getInteger("operatorUserId"));
					picfileobj.remove("isUpdate");
					picfileobj.put("isUpdate", "0");
					if (UserType.equals("60")
							&& !picfileobj.containsKey("picId")) {
						picfileobj.put("supplierId", vendcode);
					}
					plmProductPicfileTableDao.saveOrUpdate(picfileobj);
				}
			}

			if (qalist != null) { // 新增或修改资质文件行信息
				for (int i = 0; i < qalist.size(); i++) {
					JSONObject qaobj = qalist.getJSONObject(i);
					qaobj.remove("isUpdate");
					qaobj.put("isUpdate", "0");
					qaobj.put("productHeadId", saveproduct.getProductHeadId());
					qaobj.put("operatorUserId",
							queryParamJSON.getInteger("operatorUserId"));
//					if (UserType.equals("60") && !qaobj.containsKey("qaId")) {
//						qaobj.put("supplierId", vendcode);
//					}
					plmProductQaFileDao.saveOrUpdate(qaobj);
				}
			}

			if (pricelist != null) {// 新增或修改商品售价信息
				for (int i = 0; i < pricelist.size(); i++) {

					JSONObject priceobj = pricelist.getJSONObject(i);
					priceobj.remove("isUpdate");
					priceobj.put("isUpdate", "0");
					priceobj.put("productHeadId",
							saveproduct.getProductHeadId());
					priceobj.put("operatorUserId",
							queryParamJSON.getInteger("operatorUserId"));
					plmProductPriceDao.saveOrUpdate(priceobj);
				}
			}

			if (saleslist != null) { // 新增或修改商品可销售属性信息
				for (int i = 0; i < saleslist.size(); i++) {
					JSONObject salesobj = saleslist.getJSONObject(i);
					salesobj.put("productHeadId",
							saveproduct.getProductHeadId());
					salesobj.put("operatorUserId",
							queryParamJSON.getInteger("operatorUserId"));
					plmProductSalesPropertiesDao.saveOrUpdate(salesobj);
				}
			}

			if (channallist != null) {// 新增或修改商品线上渠道信息
				for (int i = 0; i < channallist.size(); i++) {

					JSONObject channalobj = channallist.getJSONObject(i);
					if (!channalobj.containsKey("versionNum")) {
						channalobj.put("versionNum", 0);
					}
					channalobj.put("productHeadId",
							saveproduct.getProductHeadId());
					channalobj.put("operatorUserId",
							queryParamJSON.getInteger("operatorUserId"));
					plmProductOnlineChannalDao.saveOrUpdate(channalobj);
				}
			}

			if (filelist != null) {// 新增或修改商品附件信息
				for (int i = 0; i < filelist.size(); i++) {
					JSONObject fileobj = filelist.getJSONObject(i);
					fileobj.remove("isUpdate");
					fileobj.put("isUpdate", "0");
					fileobj.put("productHeadId", saveproduct.getProductHeadId());
					fileobj.put("operatorUserId",
							queryParamJSON.getInteger("operatorUserId"));

					plmProductFileDao.saveOrUpdate(fileobj);
				}
			}

			if (drugobj != null && drugobj.size() > 0) {// 新增或修改商品附件信息
				drugobj.put("productHeadId", saveproduct.getProductHeadId());
				drugobj.put("operatorUserId",
						queryParamJSON.getInteger("operatorUserId"));
				plmProductDrug.saveOrUpdate(drugobj);
			}

			if (medicalobj != null && medicalobj.size() > 0) {// 新增或修改商品附件信息
				medicalobj.put("productHeadId", saveproduct.getProductHeadId());
				medicalobj.put("operatorUserId",
						queryParamJSON.getInteger("operatorUserId"));
				plmMedicainfo.saveOrUpdate(medicalobj);
			}

			// int len = 32 - (mainBarcode.length() + 7);
			String title = "商品新增" + '-' + saveproduct.getProductName() + "-"
					+ saveproduct.getBrandnameCn() + "-" + mainBarcode;

			JSONObject jo = new JSONObject();
			jo.put("productHeadId", saveproduct.getProductHeadId());
			jo.put("id", saveproduct.getProductHeadId());
			jo.put("title", title);
			jo.put("billNo", saveproduct.getPlmCode());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, jo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServerException(e.getMessage());
		}

	}

	@Override
	public String updateByHeaderIds(List<Integer> headerIds)
			throws ServerException {
		String SQL = "update  plm_product_head p set p.rms_update=1 where     p.product_head_id in (0";

		StringBuffer sb = new StringBuffer(SQL);
		if (CollectionUtils.isNotEmpty(headerIds)) {
			for (int i = 0; i < headerIds.size(); i++) {
				sb.append("," + headerIds.get(i));
			}
			sb.append(")");
			plmProductHeadDAO_HI.executeSqlUpdate(sb.toString());
		}

		return "S";
	}

	// 根据Id获取商品头与行信息
	@Override
	public JSONObject findProductDetail(JSONObject param) {
		// 获取详情并转为JSON
		String vendcode = param.getString("vendcode");
		Integer productHeadId = null;
		if (param.containsKey("productHeadId")) {
			productHeadId = param.getInteger("productHeadId");
		}
		if (param.containsKey("id")) {
			String prohead = param.getString("id");
			if (prohead.contains("_")) {
				String re = prohead.split("_")[0];
				productHeadId = new Integer(re);
			} else {
				productHeadId = new Integer(prohead);
			}
		}
		if (param.containsKey("plmCode")) {
			String plmcode = param.getString("plmCode");
			JSONObject params = new JSONObject();
			params.put("plmCode", plmcode);
			List<PlmProductHeadEntity_HI> headli = this.findList(params);
			if (headli.size() > 0) {
				productHeadId = headli.get(0).getProductHeadId();
			}
		}

		String UserType = param.getString("varUserType"); // 用户类型
		String userName = param.getString("varUserName");
		StringBuffer query = new StringBuffer(
				PlmProductHeadEntity_HI_RO.QUERY_HEAD);
		query.append(" and product.product_head_id = :productHeadId");
		Map<String, Object> paramProduct = new HashMap<>();
		paramProduct.put("productHeadId", productHeadId);
		PlmProductHeadEntity_HI_RO productEntity = plmProductHeadEntity_HI_RO
				.get(query, paramProduct);
		if (productEntity.getLastUpdatedBy() != null) {
			JSONObject jo = new JSONObject();
			jo.put("createdBy", productEntity.getLastUpdatedBy());
			try {
				JSONObject jt = ResultUtils.getUserInfoForCreatedBy(jo);
				if (jt != null) {
					productEntity.setLastUpdatePerson(jt
							.getString("userFullName"));
				}

			} catch (Exception e) {
				System.out.println("获取最后更新人用户失败!");
			}

		}

		JSONObject productJson = JSONObject.parseObject(JSONObject
				.toJSONString(productEntity));
		JSONObject result = new JSONObject();
		result.put("headInfo", productJson);

		JSONObject productparam = new JSONObject();
		productparam.put("productHeadId", String.valueOf(productHeadId));

		JSONObject productparamBysupplierId = new JSONObject();
		JSONObject productparamBysupplierCode = new JSONObject();
		productparamBysupplierId.put("productHeadId",
				String.valueOf(productHeadId));

		productparamBysupplierCode.put("productHeadId",
				String.valueOf(productHeadId));
		Map<String, Object> suppliermap = new HashMap<String, Object>();
		suppliermap.put("productHeadId", String.valueOf(productHeadId));
		String susql = PlmProductSupplierInfoEntity_HI_RO.QUERY_STORE;
		if (UserType.equals("60")) // 供应商类型
		{
			productparamBysupplierId.put("supplierId", vendcode);

//			susql += " and info.supplier_id=:supplierId";
//			suppliermap.put("supplierId", String.valueOf(vendcode));
            String vendorCodeStr = "";
            JSONArray vendorCodeList = param.getJSONArray("vendorCodeList");
            if (vendorCodeList != null && vendorCodeList.size() >0){
                for (Object object : vendorCodeList) {
                    vendorCodeStr += object.toString()+",";
                }
                vendorCodeStr = vendorCodeStr.substring(0, vendorCodeStr.length()-1);
                productparamBysupplierCode.put("supplierCode_in", vendorCodeStr);
            }
		}
		// 获取供应商

		// productparamBysupplierCode.put("isUpdate", "0");
		List<PlmProductSupplierInfoEntity_HI> lis = plmProductSupplierInfoDao
				.findList(productparamBysupplierCode);

		result.put("supplierlist", lis);

		// 获取stroelist
		List<PlmProductStoreEntity_HI> storelist = plmProductStoreServer
				.findList(productparamBysupplierId);
		result.put("storelist", storelist);
		// 获取条码行信息
		List<PlmProductBarcodeTableEntity_HI> barcodelist = plmProductBarcodeTableDao
				.findList(productparam);
		result.put("barcodelist", barcodelist);

		JSONObject priceparam = new JSONObject();
		priceparam.put("productHeadId", String.valueOf(productHeadId));
		priceparam.put("isUpdate", "0");
		if (UserType.equals("60")) {
      String vendorCodeStr = "";
      JSONArray vendorCodeList = param.getJSONArray("vendorCodeList");
      if (vendorCodeList != null && vendorCodeList.size() >0){
        for (Object object : vendorCodeList) {
          vendorCodeStr += object.toString()+",";
        }
        vendorCodeStr = vendorCodeStr.substring(0, vendorCodeStr.length()-1);
       // productparamBysupplierCode.put("supplierCode_in", vendorCodeStr);
      }
      priceparam.put("supplierId_in", vendorCodeStr);
		}

		// 获取图片文件信息
		List<PlmProductPicfileTableEntity_HI> picfilelist = plmProductPicfileTableDao
				.findList(priceparam);
		for (PlmProductPicfileTableEntity_HI pic : picfilelist) {
			// String supplierId = pic.getSupplierId();
			if (pic.getSupplierId() != null && !pic.getSupplierId().equals("")) {
				pic.setIsSupplierid("1");
			}

		}
		Collections.sort(picfilelist);
		result.put("picfilelist", picfilelist);

		// 获取资质文件信息
		List<PlmProductQaFileEntity_HI> qalist = plmProductQaFileDao
				.findList(priceparam);
		result.put("qalist", qalist);
		for (PlmProductQaFileEntity_HI q : qalist) {
			if (q.getSupplierId() != null && !q.getSupplierId().equals("")) {
				q.setIsSupplierid("1");
			}
		}

		// 获取商品售价信息
    JSONObject priceParam = priceparam;
    priceParam.remove("supplierId_in");
		List<PlmProductPriceEntity_HI> pricelist = plmProductPriceDao
				.findList(priceParam);
		result.put("pricelist", pricelist);

		// 获取商品销售属性
		List<PlmProductSalesPropertiesEntity_HI> saleslist = plmProductSalesPropertiesDao
				.findList(productparam);
		result.put("saleslist", saleslist);

		// 获取商品线上渠道
		List<PlmProductOnlineChannalEntity_HI> channallist = plmProductOnlineChannalDao
				.findList(productparam);
		result.put("channallist", channallist);

		// 获取商品附件信息
		List<PlmProductFileEntity_HI> filelist = plmProductFileDao
				.findList(priceparam);
		result.put("filelist", filelist);
		// 获取药品信息
		List<PlmProductDrugEntity_HI> druglist = plmProductDrug
				.findList(productparam);
		PlmProductDrugEntity_HI drug = new PlmProductDrugEntity_HI();
		if (druglist.size() >= 1) {
			drug = druglist.get(0);
		}
		result.put("drugInfo", drug);

		// 获取器械信息
		List<PlmProductMedicalinfoEntity_HI> medicallist = plmMedicainfo
				.findList(productparam);
		PlmProductMedicalinfoEntity_HI medical = new PlmProductMedicalinfoEntity_HI();
		if (medicallist.size() >= 1) {
			medical = medicallist.get(0);
		}
		result.put("medicalInfo", medical);
		// 获取request_Id
		// String requestId =
		// getRequestIdFromApiLog(productEntity.getPlmCode());

		// 得到修改属性列表

		JSONObject changepa = new JSONObject();
		changepa.put("productHeadId", String.valueOf(productHeadId));
		changepa.put("status", "1");
		List<PlmProductModifyCheckEntity_HI> li = plmProductModify
				.findList(productparam);
		result.put("changeli", li);
		// 计算出修改列表审批人


		//获取产品详细属性
//		if(null != productHeadId) {
//			List<PlmProductExtendAttributeEntity_HI> productExtendAttributes = productExtendAttributeServer
//					.findByProductId(productHeadId);
//			result.put("productExtendAttributes", productExtendAttributes);
//		}else {
//			result.put("productExtendAttributes", new ArrayList<>());
//		}

		return result;
	}

	@Override
	public Pagination<PlmProductHeadEntity_HI_RO> findProductList(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		String UserType = param.getString("varUserType"); // 用户类型
		String userName = param.getString("varUserName"); // 用户名
		String userType = param.getString("userType"); // 用户名
		boolean iscopy = false;
		StringBuffer query = new StringBuffer();
		query.append(PlmProductHeadEntity_HI_RO.QUERY_HEAD);
		Map<String, Object> params = new HashMap<String, Object>();

		if(StringUtils.isNotBlank(userType) && StringUtils.equals(userType,"60")) {
			query.append(" and ((product.MOTHER_COMPANY_ID = userBrandMap.MOTHER_COMPANY_ID AND product.GROUP_BRAND IS NULL) ")
					.append(" OR (product.GROUP_BRAND = userBrandMap.GROUP_BRAND_ID AND product.MOTHER_COMPANY IS NULL))");
			if (param.containsKey("userId")) {
				query.append(" and userBrandMap.SUP_USER_ID ="
						+ param.getInteger("userId") + " ");
			}
		}

		if (param.containsKey("productName")) {
			query.append(" and upper(product.product_name) like '%"
					+ param.getString("productName").toUpperCase() + "%' ");
		}

		SaafToolUtils.parperParam(param, "product.plm_code", "plmCode", query,
				params, "fulllike");
		SaafToolUtils.parperParam(param, "product.brandname_cn", "brandnameCn",
				query, params, "fulllike");
		SaafToolUtils.parperParam(param, "product.department", "department",
				query, params, "fulllike");
		//
		param.remove("productName");
		param.remove("plmCode");
		param.remove("brandnameCn");
		param.remove("department");
		if (param.containsKey("createdstr")) {
			String createdstr = param.getString("createdstr");
			param.remove("createdstr");
			query.append(" and ( upper(PRODUCT.createdstr) like '%"
					+ createdstr.toUpperCase()
					+ "%' or upper(PRODUCT.created_emp) like '"
					+ createdstr.toUpperCase()
					+ "%' or upper(PRODUCT.created_ename) like '"
					+ createdstr.toUpperCase() + "%') ");
		}

		if (param.containsKey("orderStatus")) {
			String orderStatus = param.getString("orderStatus");
			if (orderStatus.equals("0")) {
				param.remove("orderStatus");
				query.append(" and PRODUCT.order_status!='8'");
			}

		}
		if (param.containsKey("creationDate")
				&& param.getDate("creationDate") != null) {
			Date creationDate = param.getDate("creationDate");
			SimpleDateFormat sim = new SimpleDateFormat("YYYY-MM-dd");
			param.remove("creationDate");
			query.append(" and to_char(PRODUCT.creation_date,'yyyy-mm-dd')='"
					+ sim.format(creationDate) + "'");
		}

		if (param.containsKey("iscopy")) {
			iscopy = true;
			param.remove("iscopy");
		}

		JSONObject deptparam = new JSONObject();
		deptparam.put("deptId", param.getInteger("userDept"));
    List<PlmDataAclHeaderEntity_HI> headli = plmDataAclHeaderServer
      .findList(deptparam);
		if (!UserType.equals("60")) {
      if(headli.size()==0)
      {
        query.append(" and 1=2 ");
      }
    }

		if (headli.size() > 0 && !userName.equals("ADMIN")
				&& !UserType.equals("60") && iscopy == false) {
			String location = "";
			PlmDataAclHeaderEntity_HI headinfo = headli.get(0);
			Integer headid = headinfo.getHeadId();
			JSONObject lineparam = new JSONObject();
			lineparam.put("headId", headid);
			lineparam.put("enableFlag","Y");
			List<PlmDataAclLineEntity_HI> linedata = IPlmDataAclLineServer
					.findList(lineparam);
			String sqlappend = " and (";
			for (PlmDataAclLineEntity_HI line : linedata) {
				// QUERY
				String acltype = line.getAclType();
				if (acltype.equals("QUERY")) { // 新建权限
					String groupCode = line.getGroupCode();
					if (groupCode.length() == 1 || groupCode.length() == 2) {
						sqlappend += " PRODUCT.DEPARTMENT like '" + groupCode
								+ "%'  or ";
					} else {
						location += "'" + groupCode + "',";
					}

				}
			}
			sqlappend += " 1=1";
			if (!location.equals("")) {
				sqlappend += " or PRODUCT.DEPARTMENT in("
						+ location.substring(0, location.length() - 1) + ")";

			}
			query.append(sqlappend.replace("or  1=1", "").replace("1=1 or", ""));
			query.append(")");
		}

		if (iscopy) {
			query.append(" and PRODUCT.USER_DEPT='"
					+ param.getString("userDept") + "' ");
		}

		if(param.containsKey("barCode")){
		 String barcode=param.getString("barCode");
		    if(!barcode.equals("")) {
				query.append(" and exists(select null from plm_product_barcode_table t2 where t2.barcode like  " + barcode +
						"||'%' and t2.product_head_id=PRODUCT.product_head_id) ");
			}
             param.remove("barCode");
		}

		if (param.containsKey("nolist")) {
			query.append(" and PRODUCT.RMS_CODE is not null ");
			param.remove("nolist");
		}

		param.remove("userDept");


		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmProductHeadEntity_HI_RO.class, param, query, params);
		if (UserType.equals("60")) { // 供应商类型 只能看到自己供应商的 所有状态数据
			String othercondition = "";
			if (param.containsKey("isSubmit")) {
				if (!param.getString("isSubmit").equals("")) {
					if (param.getString("isSubmit").equals("1")) {
						othercondition = " and (info.return_status='"
								+ param.getString("isSubmit")
								+ "' or info.return_status is null)";
					} else {
						othercondition = " and info.return_status='"
								+ param.getString("isSubmit") + "'";
					}
				}
			}
//			if (param.containsKey("vendcode")) { // 应用profile供应商vendcode编号
//				String vendcode = param.getString("vendcode");
//				String sqls = "  and product.PRODUCT_HEAD_ID in(select INFO.PRODUCT_HEAD_ID from (select RETURN_STATUS,PRODUCT_HEAD_ID,SUPPLIER_CODE,\r\n"
//						+ "count(*) from PLM_PRODUCT_SUPPLIER_INFO group by PRODUCT_HEAD_ID,SUPPLIER_CODE,RETURN_STATUS) info where info.supplier_code='"
//						+ vendcode + "'" + othercondition + ")";
//				query.append(sqls);
//			}
			JSONArray vendorCodeList = param.getJSONArray("vendorCodeList");
			if(vendorCodeList != null && !vendorCodeList.isEmpty()){//从session中获取供应商编码
				String vendorCode = "";
				for (Object object : vendorCodeList) {
					vendorCode += "'"+object.toString()+"',";
				}
				vendorCode = vendorCode.substring(0, vendorCode.length()-1);
				String sqls = " and product.PRODUCT_HEAD_ID IN (SELECT product_head_id FROM PLM_PRODUCT_SUPPLIER_INFO sup WHERE sup.supplier_code IN ("+vendorCode+")) ";
				query.append(sqls);
			}

		}

		//query.append(" order by PRODUCT.LAST_UPDATE_DATE desc");
		System.out.println(query);
		Pagination<PlmProductHeadEntity_HI_RO> pagination = plmProductHeadEntity_HI_RO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);
		if (UserType.equals("60")) {
			if (param.containsKey("isSubmit")) {
				String result = "";
				String issubmit = param.getString("isSubmit");
				switch (issubmit) {
				case "0":
					result = "未提交";
					break;
				case "1":
					result = "已提交";
					break;
				case "2":
					result = "已驳回";
					break;
				default:
					System.out.println("default");

				}
				// String result = issubmit.equals("0") ? "未提交" : "已提交";
				List<PlmProductHeadEntity_HI_RO> r = pagination.getData();
				for (PlmProductHeadEntity_HI_RO t : r) {
					t.setSubmitStatus(result);
				}

			} else {
				String vendcode = param.getString("vendcode");
				JSONObject jt = new JSONObject();
				jt.put("supplierCode", vendcode);
				// 找到该供应商下面的所有商品
				Map<Integer, String> ma = new HashMap<Integer, String>();
				List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
						.findList(jt);
				for (PlmProductSupplierInfoEntity_HI info : suli) {
					if (info.getReturnStatus() == null) {
						ma.put(info.getProductHeadId(), "");
					} else {
						ma.put(info.getProductHeadId(), info.getReturnStatus());
					}
				}
				List<PlmProductHeadEntity_HI_RO> r = pagination.getData();
				String result = "";
				for (PlmProductHeadEntity_HI_RO t : r) {
					if (ma.containsKey(t.getProductHeadId())) {
						String data = ma.get(t.getProductHeadId());
						// .equals("0") ? "未提交" : "已提交";
						switch (data) {
						case "0":
							result = "未提交";
							break;
						case "1":
							result = "已提交";
							break;
						case "2":
							result = "已驳回";
							break;
						default:
							System.out.println("default");

						}
						t.setSubmitStatus(result);
					}
				}

			}
		}

		return pagination;

	}

	@Override
	public void deleteByProductId(JSONObject param) throws ServerException {
		try {
			Integer id = param.getInteger("productHeadId");
			this.deleteById(id);

			JSONObject head = new JSONObject();
			head.put("productHeadId", id);
			// 删除所有行信息表数据
			List<PlmProductBarcodeTableEntity_HI> barcodelist = plmProductBarcodeTableDao
					.findList(head);
			for (PlmProductBarcodeTableEntity_HI code : barcodelist) {
				plmProductBarcodeTableDao.deleteById(code.getBarcodeId());
			}

			List<PlmProductFileEntity_HI> filelist = plmProductFileDao
					.findList(head);
			for (PlmProductFileEntity_HI file : filelist) {
				plmProductFileDao.deleteById(file.getFileId());
			}

			List<PlmProductOnlineChannalEntity_HI> channallist = plmProductOnlineChannalDao
					.findList(head);
			for (PlmProductOnlineChannalEntity_HI channal : channallist) {
				plmProductOnlineChannalDao.deleteById(channal.getChannalId());
			}

			List<PlmProductPicfileTableEntity_HI> picfilelist = plmProductPicfileTableDao
					.findList(head);
			for (PlmProductPicfileTableEntity_HI picfile : picfilelist) {
				plmProductPicfileTableDao.deleteById(picfile.getPicId());
			}

			List<PlmProductPriceEntity_HI> pricelist = plmProductPriceDao
					.findList(head);
			for (PlmProductPriceEntity_HI price : pricelist) {
				plmProductPriceDao.deleteById(price.getPriceId());
			}
			List<PlmProductQaFileEntity_HI> qalist = plmProductQaFileDao
					.findList(head);
			for (PlmProductQaFileEntity_HI qa : qalist) {
				plmProductQaFileDao.deleteById(qa.getQaId());
			}

			List<PlmProductSalesPropertiesEntity_HI> salelist = plmProductSalesPropertiesDao
					.findList(head);
			for (PlmProductSalesPropertiesEntity_HI sale : salelist) {
				plmProductSalesPropertiesDao.deleteById(sale.getSalesId());
			}

			List<PlmProductSupplierInfoEntity_HI> supplierlist = plmProductSupplierInfoDao
					.findList(head);
			for (PlmProductSupplierInfoEntity_HI spinfo : supplierlist) {
				plmProductSupplierInfoDao.deleteById(spinfo.getId());
			}

			List<PlmProductDrugEntity_HI> druglist = plmProductDrug
					.findList(head);
			for (PlmProductDrugEntity_HI tag : druglist) {
				plmProductDrug.deleteById(tag.getDrugId());
			}

			List<PlmProductMedicalinfoEntity_HI> medicalist = plmMedicainfo
					.findList(head);
			for (PlmProductMedicalinfoEntity_HI tag : medicalist) {
				plmMedicainfo.deleteById(tag.getMedicalId());
			}

		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new ServerException("服务异常");
		}
	}

	@Override
	public JSONObject SaveProductByExcel(JSONObject param) throws IOException,
			ParseException {
		String UserType = param.getString("varUserType"); // 用户类型
		String userName = param.getString("varUserName"); // 用户名

		String filepath = param.getString("filepath");
		File file = this.getNetUrl(filepath);
		Map<String, PlmProductHeadEntity_HI> headlist = new HashMap<String, PlmProductHeadEntity_HI>();
		List<PlmProductSupplierInfoEntity_HI> supplierlistdata = new ArrayList<PlmProductSupplierInfoEntity_HI>();
		Map<String, Integer> supplierCount = new HashMap<String, Integer>(); // 供应商个数
		Map<String, Integer> barcodeCount = new HashMap<String, Integer>(); // 条码个数
		Map<String, String> supplierli = new HashMap<String, String>();
		Map<String, String> codemap = new HashMap<String, String>();// 序号
		Map<String, String> productMap = new HashMap<String, String>();// 商品中文名
		List<PlmProductSupplierInfoEntity_HI> supplierAlldata = new ArrayList<PlmProductSupplierInfoEntity_HI>();// 供应商集合
		try {
			InputStream is = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet head = wb.getSheetAt(0); // 头信息
			int rownum = head.getLastRowNum(); // 总行数
			int headcolumnCount = head.getRow(0).getPhysicalNumberOfCells();
			if (headcolumnCount != 13) {
				throw new ServerException("[商品主数据]数据格式错误:请按照模板格式填写!");
			}
			for (int i = 1; i <= rownum; i++) {

				PlmProductHeadEntity_HI headentity = new PlmProductHeadEntity_HI();

				XSSFRow curent = head.getRow(i);
				if (curent == null) {
					continue;
				}
				XSSFCell code = curent.getCell(0); // 序号
				XSSFCell productname = curent.getCell(1); // 商品名中文
				XSSFCell productename = curent.getCell(2); // 商品名英文
				XSSFCell ppname = curent.getCell(3); // 品牌名
				XSSFCell ppename = curent.getCell(4); // 品牌英文名
				XSSFCell dpcode = curent.getCell(5); // 部门编码
				XSSFCell classcode = curent.getCell(6); // 分类编码
				XSSFCell childclasscode = curent.getCell(7); // 子分类编码
				XSSFCell producttype = curent.getCell(8); // 商品类型
				XSSFCell qd = curent.getCell(9); // 销售渠道
				XSSFCell validate = curent.getCell(10); // 有效保质期
				XSSFCell validateday = curent.getCell(11); // 有效保质期天数
				XSSFCell speciproduct = curent.getCell(12); // 特殊商品类型

				String codeValue = this.getStringCellValueXss(code);
				String productnamevalue = this
						.getStringCellValueXss(productname);

				// 判断格式 数据为空的现象
				if (codeValue.equals("") && productnamevalue.equals("")) {
					continue;
				}

				if (codeValue.equals("")) {
					throw new ServerException("[商品主数据]数据格式错误:第" + (i + 1)
							+ "行,序号不能为空!");
				} else // 验证重复
				{
					if (codemap.containsKey(codeValue)) {
						throw new ServerException("[商品主数据]数据格式错误:第" + (i + 1)
								+ "行,序号存在相同值!");
					} else {
						codemap.put(codeValue, codeValue);
					}
					// 校验必填字段
					if (productnamevalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',产品名称不能为空!");
					}

					boolean flag = this.isSpecialChar(productnamevalue);
					if (flag == false) {
						throw new ServerException("[商品主数据]第" + (i + 1)
								+ "行,产品名称存在特殊符号!");
					}

					productMap.put(productnamevalue, productnamevalue);

					// 校验必填项目
					String productenamevalue = this
							.getStringCellValueXss(productename);
					if (productenamevalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',产品英文名称不能为空!");
					} else {
						boolean isenstr = this.isEnStr(productenamevalue);
						if (isenstr == false) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',产品英文名称数据格式错误!");
						}
					}

					String ppnamevalue = this.getStringCellValueXss(ppname);
					String ppenamevalue = this.getStringCellValueXss(ppename);
					if (ppnamevalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',(品牌名)中文,不能为空!");
					} else {
						JSONObject brand = new JSONObject();
						brand.put("plmBrandCn", ppnamevalue);
						List<PlmBrandInfoEntity_HI> branli = brandinfo
								.findList(brand);
						if (branli.size() == 0) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',(品牌名)中文,不存在!");
						} else {
							ppenamevalue = branli.get(0).getPlmBrandEn();
						}
					}
					headentity.setBrandnameEn(ppenamevalue);

					String depcodevalue = this.getStringCellValueXss(dpcode);
					Integer depId = null;
					String mainClass = "";
					if (depcodevalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',部门编码,不能为空!");
					} else {

						// 判断是否有新增权限
						JSONObject dp = new JSONObject();
						dp.put("plmDeptCode", depcodevalue);
						List<PlmDeptListEntity_HI> dplist = plmDeptlistServer
								.findList(dp);
						if (dplist.size() == 0) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',部门编码不存在,请填写正确部门编码!");
						} else {

							JSONObject deptparam = new JSONObject();
							deptparam.put("deptId",
									param.getInteger("userDept"));
							List<PlmDataAclHeaderEntity_HI> headli = plmDataAclHeaderServer
									.findList(deptparam);
							if (headli.size() > 0 && !userName.equals("ADMIN")
									&& !UserType.equals("60")) {
								String newResult = "";
								boolean isnew = false;
								String location = "";
								PlmDataAclHeaderEntity_HI headinfo = headli
										.get(0);
								Integer headid = headinfo.getHeadId();
								JSONObject lineparam = new JSONObject();
								lineparam.put("headId", headid);
								List<PlmDataAclLineEntity_HI> linedata = IPlmDataAclLineServer
										.findList(lineparam);

								for (PlmDataAclLineEntity_HI line : linedata) {
									// QUERY
									String acltype = line.getAclType();
									if (acltype.equals("NEW")) { // 新建权限
										String groupCode = line.getGroupCode();
										if (groupCode.length() == 4) {
											newResult += groupCode + ",";
											if (depcodevalue.equals(groupCode)) {
												isnew = true;

												if (line.getMainClass() != null) {
													mainClass += line
															.getMainClass()
															+ ",";
												}
											}
										} else { // groupcode为1
											newResult += groupCode + "开头,";
											String deptprefix = depcodevalue
													.substring(0, 1);
											if (deptprefix.equals(groupCode)) {
												isnew = true;

											}

										}

									}
								}
								System.out.println(newResult);
								if (isnew == false) {
									throw new ServerException("[商品主数据]序号'"
											+ codeValue
											+ "',当前用户没有该部门编码新增权限,请填写范围["
											+ newResult.substring(0,
													newResult.length() - 1)
											+ "]以内的部门编码!");
								}
							}

							depId = dplist.get(0).getPlmDeptListId();
							headentity.setDepartmentDescript(dplist.get(0)
									.getPlmDeptName());

							// 采购类型
							if (depcodevalue.length() == 4) {
								String depcode = depcodevalue.substring(2, 3);
								if (new Integer(depcode) < 5) {
									headentity.setPurchaseType("1");
								} else if (new Integer(depcode) >= 5
										&& new Integer(depcode) < 8) {
									headentity.setPurchaseType("2");
								} else {
									headentity.setPurchaseType("3");
								}
							}
						}

					}

					String classcodevalue = this
							.getStringCellValueXss(classcode);
					Integer classId = null;
					if (classcodevalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',分类编码,不能为空!");
					} else {
						JSONObject classobj = new JSONObject();
						classobj.put("plmDeptListId", depId);
						classobj.put("plmClassCode", classcodevalue);
						List<PlmDeptClassEntity_HI> classli = plmDeptclassServer
								.findList(classobj);
						if (classli.size() == 0) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',该部门下的分类编码不存在,请填写正确分类编码!");
						} else {
							boolean mainCode = false;
							if (!mainClass.equals("")) {
								String maindata = mainClass.substring(0,
										mainClass.length() - 1);
								for (String mc : maindata.split(",")) {
									if (mc.equals(classcodevalue)) {
										mainCode = true;
									}
								}
								if (mainCode == false) {
									throw new ServerException("[商品主数据]序号'"
											+ codeValue
											+ "',当前用户没有选择该部门下的分类编码权限,请填写范围["
											+ maindata + "]的分类编码!");
								}
							}

							classId = classli.get(0).getPlmDeptClassId();
							headentity.setClassDesc(classli.get(0)
									.getPlmClassName());

						}
					}

					String subclasscodevalue = this
							.getStringCellValueXss(childclasscode);
					if (subclasscodevalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',子分类编码,不能为空!");
					} else {
						JSONObject subclassobj = new JSONObject();
						subclassobj.put("plmDeptListId", depId);
						subclassobj.put("plmDeptClassId", classId);
						subclassobj.put("plmSubclassCode", subclasscodevalue);
						List<PlmDeptSubclassEntity_HI> subli = plmDeptSubclass
								.findList(subclassobj);
						if (subli.size() == 0) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',子分类编码在该分类下不存在,请填写正确的子分类编码!");
						} else {
							headentity.setSubclassDesc(subli.get(0)
									.getPlmSubclassName());
						}
					}

					String producttypevalue = this
							.getStringCellValueXss(producttype);
					if (producttypevalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',商品类型,不能为空!");
					} else {
						if (!producttypevalue.equals("2")
								&& !producttypevalue.equals("3")
								&& !producttypevalue.equals("5")
								&& !producttypevalue.equals("6")) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',商品类型,不存在或数据错误!");
						}
					}

					String qdvalue = this.getStringCellValueXss(qd);
					if (qdvalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',销售渠道,不能为空!");
					} else {
						if (!qdvalue.equals("1") && !qdvalue.equals("2")
								&& !qdvalue.equals("3")) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',销售渠道,不存在或数据错误!");
						}
					}

					String validatevalue = this.getStringCellValueXss(validate);
					if (validatevalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',有效保质期,不能为空!");
					} else {
						if (!validatevalue.equals("1")
								&& !validatevalue.equals("0")) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',有效保质期,数据错误!");
						}
					}
					// validDays
					headentity.setValidDays(validatevalue);
					String validatedayvalue = this
							.getStringCellValueXss(validateday);
					if (validatedayvalue.equals("")
							&& validatevalue.equals("1")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',有效保质期天数,不能为空!");
					} else if (validatevalue.equals("0")
							&& validatedayvalue.equals("")) {
						validatedayvalue = "3650";
					} else if (validatevalue.equals("0")
							&& !validatedayvalue.equals("3650")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',有效保质期为0时,默认为3650不能填写其它数据!");
					} else if (validatevalue.equals("1")
							&& !validatedayvalue.equals("")) {
						boolean isnum = this.isNumeric(validatedayvalue);
						if (isnum == false) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',有效保质期天数,只能填写数字!");
						}
					}
					headentity.setSxDays(new Integer(validatedayvalue));
					String speciproductvalue = this
							.getStringCellValueXss(speciproduct);
					if (speciproductvalue.equals("")) {
						throw new ServerException("[商品主数据]序号'" + codeValue
								+ "',特殊商品类型,不能为空!");
					} else {
						if (!speciproductvalue.equals("0")
								&& !speciproductvalue.equals("1")
								&& !speciproductvalue.equals("2")) {
							throw new ServerException("[商品主数据]序号'" + codeValue
									+ "',特殊商品类型,不存在或数据错误!");
						}
						if (speciproductvalue.equals("2")
								|| speciproductvalue.equals("1")) // 只有1部门才能新增特殊商品类型为'药品'的商品。
						{
							String deno = depcodevalue.substring(0, 1);
							if (!deno.equals("1")) {
								throw new ServerException(
										"[商品主数据]序号'"
												+ codeValue
												+ "',特殊商品类型,只有为1开头的部门编码才能新增特殊商品类型为1[药品]或2[医疗器械] 的商品!");
							}
						}
					}

				}

				headentity.setPlmCode(this.getPlmProduct());
				headentity.setMarkerChannel(this.getStringCellValueXss(qd));
				headentity.setDepartment(this.getStringCellValueXss(dpcode));
				headentity.setClasses(this.getStringCellValueXss(classcode));
				headentity.setSubClass(this
						.getStringCellValueXss(childclasscode));
				headentity.setProductName(this
						.getStringCellValueXss(productname));
				headentity.setProductEname(this
						.getStringCellValueXss(productename));
				headentity.setBrandnameCn(this.getStringCellValueXss(ppname));
				// headentity.setBrandnameEn(ppenamevalue);
				headentity.setProductType(this
						.getStringCellValueXss(producttype));
				headentity.setOtherInfo(this
						.getStringCellValueXss(speciproduct));
				headentity.setCreatedBy(new Integer(param
						.getString("varUserId")));
				headentity.setCreatedstr(param.getString("varUserFullName"));
				headentity.setIsUpdatecheck("0");
				headentity.setOrderStatus("1");
				headlist.put(this.getStringCellValueXss(code), headentity);
				supplierCount.put(this.getStringCellValueXss(code), 0); // 初始化供应商个数
				barcodeCount.put(this.getStringCellValueXss(code), 0);
			}

			XSSFSheet supplierlist = wb.getSheetAt(1); // 供应商信息
			int suppliercolumncount = supplierlist.getRow(0)
					.getPhysicalNumberOfCells(); // 总列数
			if (suppliercolumncount != 3) {
				throw new ServerException("[供应商],数据格式错误!");
			}
			int supplierRownum = supplierlist.getLastRowNum();
			for (int pi = 1; pi <= supplierRownum; pi++) {
				PlmProductSupplierInfoEntity_HI su = new PlmProductSupplierInfoEntity_HI();
				XSSFRow curent = supplierlist.getRow(pi);
				if (curent == null) {
					continue;
				}
				XSSFCell code = curent.getCell(0); // 序号
				XSSFCell supplierid = curent.getCell(1); // supplierId供应商id
				XSSFCell suppliermain = curent.getCell(2); // 优先供应商
				if (this.getStringCellValueXss(code).equals("")
						&& this.getStringCellValueXss(supplierid).equals("")) {
					continue;
				}

				if (this.getStringCellValueXss(code).equals("")) {
					throw new ServerException("[供应商]第" + (pi + 1) + "行,序号不能为空!");
				} else {
					String codes = this.getStringCellValueXss(code);
					if (!headlist.containsKey(codes)) {
						throw new ServerException("[供应商]第" + (pi + 1)
								+ "行,序号不存在!");
					}
					String supplieridvalue = "";
					switch (supplierid.getCellTypeEnum()) {
					case STRING:
						supplieridvalue = supplierid.getStringCellValue()
								.trim();
						break;
					case NUMERIC:
						DecimalFormat df = new DecimalFormat("0");
						supplieridvalue = df.format(supplierid
								.getNumericCellValue());
						break;
					}
					;
					if (supplieridvalue.equals("")) {
						throw new ServerException("[供应商]第" + (pi + 1)
								+ "行,供应商id不能为空!");
					} else {
						JSONObject s = new JSONObject();
						s.put("supplierCode", supplieridvalue);

						JSONObject jt = new ResultUtils().getTtaSupplierInfo(s);
						JSONArray ja = jt.getJSONArray("data");
						if (ja.size() == 0) {
							throw new ServerException("[供应商]第" + (pi + 1)
									+ "行,供应商id不存在,或数据错误!");
						} else {
							JSONObject bu = ja.getJSONObject(0);
							su.setSupplierId(supplieridvalue);
							su.setSupplierCode(supplieridvalue);
							su.setSupplierName(bu.getString("SUPPLIERNAME"));
							su.setCurrencyCost(bu.getString("CURRENCYCODE"));
						}

					}

					if (headlist.containsKey(this.getStringCellValueXss(code))) {
						String key = supplieridvalue + "_"
								+ this.getStringCellValueXss(code);
						if (supplierli.containsKey(key)) { // 存在重复供应商id

							throw new ServerException("[供应商]第" + (pi + 1)
									+ "行,同一商品下存在相同供应商id!");
						} else {
							supplierli.put(key, key);
						}
					} else {
						throw new ServerException("[供应商]第" + (pi + 1)
								+ "行,无法找到商品主数据对应序号!");
					}

				}
				// 优先供应商
				String suppliermainvalue = this
						.getStringCellValueXss(suppliermain);
				if (suppliermainvalue.equals("")) {
					throw new ServerException("[供应商]第" + (pi + 1)
							+ "行,优先供应商不能为空!");
				} else {
					if (!suppliermainvalue.equals("Y")
							&& !suppliermainvalue.equals("N")) {
						throw new ServerException("[供应商]第" + (pi + 1)
								+ "行,优先供应商数据格式错误!");
					}
				}

				PlmProductSupplierInfoEntity_HI sdata = new PlmProductSupplierInfoEntity_HI();
				sdata.setVersionNum(pi + 1); // 行数
				sdata.setId(new Integer(this.getStringCellValueXss(code)));
				sdata.setSupplierId(this.getStringCellValueXss(supplierid));
				sdata.setIsMainsupplier(suppliermainvalue);
				supplierAlldata.add(sdata);

				su.setIsMainsupplier(this.getStringCellValueXss(suppliermain));
				// su.setSupplierCode(this.getStringCellValueXss(code));
				su.setReturnStatus("0");
				su.setIsSubmit("0");
				// su.setIsUpdate("0");
				su.setFlags(this.getStringCellValueXss(code));
				supplierlistdata.add(su);

				if (supplierCount.containsKey(this.getStringCellValueXss(code))) { // 修改供应商个数
					String codes = this.getStringCellValueXss(code);
					Integer oldvalue = supplierCount.get(codes);
					Integer newvalue = oldvalue + 1;
					supplierCount.remove(this.getStringCellValueXss(code));
					supplierCount.put(codes, newvalue);
				}

			}

			Map<String, List<PlmProductSupplierInfoEntity_HI>> suallmap = new HashMap<String, List<PlmProductSupplierInfoEntity_HI>>();
			// supplierAlldata
			for (int i = 0; i < supplierAlldata.size(); i++) { // 判断同一商品下
																// 一定要有一个主供应商
				PlmProductSupplierInfoEntity_HI sinfo = supplierAlldata.get(i);
				Integer code = sinfo.getId(); // 序号
				String key = String.valueOf(code);
				if (suallmap.containsKey(key)) {
					List<PlmProductSupplierInfoEntity_HI> lidata = suallmap
							.get(key);
					lidata.add(sinfo);
					suallmap.remove(key);
					suallmap.put(key, lidata);

				} else {
					List<PlmProductSupplierInfoEntity_HI> lidata = new ArrayList<PlmProductSupplierInfoEntity_HI>();
					lidata.add(sinfo);
					suallmap.put(key, lidata);
				}

			}

			// 判断 suallmap中所有的分组供应商
			for (Map.Entry<String, List<PlmProductSupplierInfoEntity_HI>> ma : suallmap
					.entrySet()) {
				int count = 0;
				String key = ma.getKey();
				List<PlmProductSupplierInfoEntity_HI> suli = ma.getValue();
				for (PlmProductSupplierInfoEntity_HI suinfo : suli) {
					if (suinfo.getIsMainsupplier().equals("Y")) {
						count++;
					}
				}
				if (count == 0 || count > 1) {
					throw new ServerException("[供应商信息]商品序号'"
							+ key.split("_")[0] + "',必须有且只有一个优先供应商!");
				}
			}

			// 条码信息
			List<PlmProductBarcodeTableEntity_HI> barcodedata = new ArrayList<PlmProductBarcodeTableEntity_HI>();

			Map<String, String> barcodeMap = new HashMap<String, String>();
			XSSFSheet barcodelist = wb.getSheetAt(2);
			int barcodecolumncount = barcodelist.getRow(0)
					.getPhysicalNumberOfCells();
			if (barcodecolumncount != 4) {
				throw new ServerException("[条码]数据格式错误!");
			}
			for (int bi = 1; bi <= barcodelist.getPhysicalNumberOfRows(); bi++) {
				XSSFRow curent = barcodelist.getRow(bi);
				if (curent == null) {
					continue;
				}
				XSSFCell code = curent.getCell(0); // 序号
				XSSFCell barcodetype = curent.getCell(1); // 条码类型
				XSSFCell barcode = curent.getCell(2); // 条码
				XSSFCell isMain = curent.getCell(3); // 是否主条码
				if (this.getStringCellValueXss(code).equals("")
						&& this.getStringCellValueXss(barcode).equals("")) {
					continue;
				}

				if (this.getStringCellValueXss(code).equals("")) {
					throw new ServerException("[条码]第" + (bi + 1) + "行,序号不能为空!");
				} else {
					String codes = this.getStringCellValueXss(code);
					if (!headlist.containsKey(codes)) {
						throw new ServerException("[条码]第" + (bi + 1)
								+ "行,序号不存在!");
					}
				}

				if (headlist.containsKey(this.getStringCellValueXss(code))) {

					String barcodetypevalue = this
							.getStringCellValueXss(barcodetype);
					if (barcodetypevalue.equals("")) {
						throw new ServerException("[条码]第" + (bi + 1)
								+ "行,条码类型不能为空!");
					} else {
						boolean barcodetypeflag = this
								.isNumeric(barcodetypevalue);
						if (barcodetypeflag == false) {
							throw new ServerException("[条码]第" + (bi + 1)
									+ "行,条码类型请填写数字!");
						} else {
							Integer typeinteger = new Integer(barcodetypevalue);
							if (typeinteger < 1 || typeinteger > 6) {
								throw new ServerException("[条码]第" + (bi + 1)
										+ "行,条码类型数据错误!");
							}
						}

					}

					String barcodevalue = this.getStringCellValueXss(barcode);

					if (barcodevalue.equals("")) {
						throw new ServerException("[条码]第" + (bi + 1)
								+ "行,Barcode不能为空!");
					} else {
						boolean barcodeflag = this.isNumeric(barcodevalue);
						Integer len = barcodevalue.length();

						if (barcodeflag == false || len < 8 || len > 14) {
							throw new ServerException("[条码]第" + (bi + 1)
									+ "行,Barcode请填写8-14位数字!");
						} else // 验证条码的唯一性
						{
							if (!barcodetypevalue.equals("6")) {
								boolean baflag = new PlmProductHeadService()
										.isBarcode(barcodetypevalue,
												barcodevalue);
								if (baflag == false) {
									throw new ServerException("[条码]第"
											+ (bi + 1) + "行,Barcode格式错误!");
								}

								JSONObject baobj = new JSONObject();
								baobj.put("barcode", barcodevalue);

								List<PlmProductBarcodeTableEntity_HI> batable = new ArrayList<PlmProductBarcodeTableEntity_HI>();
								try {
									batable = plmProductBarcodeTableDao
											.findList(baobj);

								} catch (Exception e) {
									throw new ServerException("[条码]第"
											+ (bi + 1) + "行,Barcode格式错误!");
								}

								if (batable.size() > 0) {
									boolean isexis = false;
									for (PlmProductBarcodeTableEntity_HI ba : batable) {
										Integer headid = ba.getProductHeadId();
										PlmProductHeadEntity_HI proold = this
												.getById(headid);
										if (!proold.getOrderStatus()
												.equals("5")) {
											isexis = true;
											break;
										}
									}
									if (isexis) {
										throw new ServerException("条形码序号'"
												+ (bi + 1) + "',条码已经存在!");
									}

								}
							}
						} // 验证条码

					}

					String isMainvalue = this.getStringCellValueXss(isMain);
					if (isMainvalue.equals("")) {
						throw new ServerException("[条码]第" + (bi + 1)
								+ "行,主Barcode不能为空!");
					} else {
						if (!isMainvalue.equals("Y")
								&& !isMainvalue.equals("N")) {
							throw new ServerException("[条码]第" + (bi + 1)
									+ "行,主Barcode数据格式错误!");
						}
					}

					String codevalue = this.getStringCellValueXss(code);
					Integer codelen = barcodevalue.length();
					if (barcodetypevalue.equals("1")
							|| barcodetypevalue.equals("2")) {

						if (codelen != 8) {
							throw new ServerException(
									"[条码]第"
											+ (bi + 1)
											+ "行,条码类型为1或2(UPC-E或EAN8)时,barcode长度只能填写8位!");
						}
					} else if (barcodetypevalue.equals("3")) {
						if (codelen != 12) {
							throw new ServerException("[条码]第" + (bi + 1)
									+ "行,条码类型为3(UPC-A)时,barcode长度只能填写12位!");
						}
					} else if (barcodetypevalue.equals("4")) {
						if (codelen != 13) {
							throw new ServerException("[条码]第" + (bi + 1)
									+ "行,条码类型为4(EAN-13)时,barcode长度只能填写13位!");
						}
					} else if (barcodetypevalue.equals("5")) {
						if (codelen != 14) {
							throw new ServerException("[条码]第" + (bi + 1)
									+ "行,条码类型为5(UCC-14)时,barcode长度只能填写14位!");
						}
					}

					if (!barcodetypevalue.equals("6")) {
						String key = barcodevalue;
						if (barcodeMap.containsKey(key)) {
							throw new ServerException("[条码]第" + (bi + 1)
									+ "行,条码不能重复!");
						} else {
							barcodeMap.put(key, key);
						}
					}// 不是虚拟条码的时候

					PlmProductBarcodeTableEntity_HI barcodeentity = new PlmProductBarcodeTableEntity_HI();
					barcodeentity.setBarcode(barcodevalue);
					barcodeentity.setIsMain(isMainvalue);
					barcodeentity.setFlags(codevalue);
					barcodeentity.setBarcodeType(barcodetypevalue);
					barcodeentity.setOperatorUserId(new Integer(param
							.getString("varUserId")));
					barcodeentity.setCreatedBy(new Integer(param
							.getString("varUserId")));
					barcodedata.add(barcodeentity);

					// barcodeCount
					if (barcodeCount.containsKey(this
							.getStringCellValueXss(code))) {
						String codes = this.getStringCellValueXss(code);
						Integer oldvalue = barcodeCount.get(codes);
						Integer newvalue = oldvalue + 1;
						barcodeCount.remove(this.getStringCellValueXss(code));
						barcodeCount.put(codes, newvalue);
					}

				} else {
					throw new ServerException("[条码]第" + (bi + 1)
							+ "行,无法找到商品主数据对应序号!");
				}
			}

			// barcodedata

			Map<String, List<PlmProductBarcodeTableEntity_HI>> barallmap = new HashMap<String, List<PlmProductBarcodeTableEntity_HI>>();
			for (PlmProductBarcodeTableEntity_HI barobj : barcodedata) {
				String code = barobj.getFlags();
				if (code.equals("")) {
					continue;
				}
				if (barallmap.containsKey(code)) {

					List<PlmProductBarcodeTableEntity_HI> barli = barallmap
							.get(code);
					barli.add(barobj);
					barallmap.remove(code);
					barallmap.put(code, barli);
				} else {
					List<PlmProductBarcodeTableEntity_HI> ad = new ArrayList<PlmProductBarcodeTableEntity_HI>();
					ad.add(barobj);
					barallmap.put(code, ad);
				}
			}

			for (Map.Entry<String, List<PlmProductBarcodeTableEntity_HI>> bao : barallmap
					.entrySet()) {
				int count = 0;
				String key = bao.getKey();
				List<PlmProductBarcodeTableEntity_HI> value = bao.getValue();
				for (PlmProductBarcodeTableEntity_HI pt : value) {
					if (pt.getIsMain().equals("Y")) {
						count++;
					}
				}
				if (count == 0 || count > 1) {
					throw new ServerException("[条码]序号'" + key
							+ "',必须有且只有一个主Barcode!");
				}
			}

			for (Map.Entry<String, PlmProductHeadEntity_HI> headdata : headlist
					.entrySet()) {
				String code = headdata.getKey();
				Integer suppliercount = supplierCount.get(code); // 供应商个数
				if (suppliercount == 0) {
					throw new ServerException("商品序号'" + code + "',至少需要一个供应商信息!");
				}

				Integer barcodecount = barcodeCount.get(code);
				if (barcodecount == 0) {
					throw new ServerException("商品序号'" + code + "',至少需要一个条码信息!");
				}
			}

			// 保存数据
			for (Map.Entry<String, PlmProductHeadEntity_HI> headdata : headlist
					.entrySet()) {
				String code = headdata.getKey();
				Integer suppliercount = supplierCount.get(code); // 供应商个数

				PlmProductHeadEntity_HI headobj = headdata.getValue();
				headobj.setSupplierCount(suppliercount);
				headobj.setCreatedEmp(param.getString("userEmp"));
				headobj.setCreatedEname(param.getString("userEname"));
				headobj.setCreatedstr(param.getString("personName"));
				headobj.setUserDept(param.getString("userDept"));
				headobj.setUserGroupcode(param.getString("userGroupcode"));
				JSONObject jo = (JSONObject) JSONObject.toJSON(headobj);

				PlmProductHeadEntity_HI saveobj = this.saveOrUpdate(jo);
				Integer productHeadId = saveobj.getProductHeadId();
				for (int i = 0; i < supplierlistdata.size(); i++) {
					PlmProductSupplierInfoEntity_HI info = supplierlistdata
							.get(i);
					if (info.getFlags().equals(code)) {
						info.setProductHeadId(productHeadId);
						info.setOperatorUserId(new Integer(param
								.getString("varUserId")));
						info.setCreatedBy(new Integer(param
								.getString("varUserId")));
						plmProductSupplierInfoDao.save(info);

						String supplierId = info.getSupplierCode();
						PlmProductPicfileTableEntity_HI pic1 = new PlmProductPicfileTableEntity_HI();
						pic1.setPicType("1");
						pic1.setSupplierId(supplierId);
						pic1.setProductHeadId(productHeadId);
						pic1.setIsUpdate("0");
						pic1.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic2 = new PlmProductPicfileTableEntity_HI();
						pic2.setPicType("2");
						pic2.setIsUpdate("0");
						pic2.setIsRequire("Y");
						pic2.setSupplierId(supplierId);
						pic2.setProductHeadId(productHeadId);
						PlmProductPicfileTableEntity_HI pic3 = new PlmProductPicfileTableEntity_HI();
						pic3.setPicType("3");
						pic3.setSupplierId(supplierId);
						pic3.setProductHeadId(productHeadId);
						pic3.setIsUpdate("0");
						pic3.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic4 = new PlmProductPicfileTableEntity_HI();
						pic4.setPicType("4");
						pic4.setSupplierId(supplierId);
						pic4.setProductHeadId(productHeadId);
						pic4.setIsUpdate("0");
						pic4.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic5 = new PlmProductPicfileTableEntity_HI();
						pic5.setPicType("5");
						pic5.setSupplierId(supplierId);
						pic5.setProductHeadId(productHeadId);
						pic5.setIsUpdate("0");
						pic5.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic6 = new PlmProductPicfileTableEntity_HI();
						pic6.setPicType("6");
						pic6.setSupplierId(supplierId);
						pic6.setProductHeadId(productHeadId);
						pic6.setIsUpdate("0");
						pic6.setIsRequire("Y");
						PlmProductPicfileTableEntity_HI pic7 = new PlmProductPicfileTableEntity_HI();
						pic7.setPicType("7");
						pic7.setSupplierId(supplierId);
						pic7.setProductHeadId(productHeadId);
						pic7.setIsUpdate("0");
						pic7.setIsRequire("Y");
						List<PlmProductPicfileTableEntity_HI> lidata = new ArrayList<PlmProductPicfileTableEntity_HI>();
						lidata.add(pic1);
						lidata.add(pic2);
						lidata.add(pic3);
						lidata.add(pic4);
						lidata.add(pic5);
						lidata.add(pic6);
						lidata.add(pic7);
						plmProductPicfileTableDao.save(lidata);
						// 资质文件
						// 药品或医疗器械时 添加默认资质文件
						String otherinfo = saveobj.getOtherInfo();
						if (otherinfo.equals("1")) // 药品
						{
							List<PlmProductQaFileEntity_HI> qadata = new ArrayList<PlmProductQaFileEntity_HI>();
							PlmProductQaFileEntity_HI q1 = new PlmProductQaFileEntity_HI();
							q1.setQaFiletype("药品注册证");
							q1.setSupplierId(supplierId);
							q1.setProductHeadId(productHeadId);
							q1.setQaCodetype("3");
							q1.setDateType("3");
							q1.setIsUpdate("0");

							PlmProductQaFileEntity_HI q2 = new PlmProductQaFileEntity_HI();
							q2.setQaFiletype("质量标准");
							q2.setSupplierId(supplierId);
							q2.setProductHeadId(productHeadId);
							q2.setQaCodetype("5");
							q2.setDateType("3");
							q2.setIsUpdate("0");
							PlmProductQaFileEntity_HI q3 = new PlmProductQaFileEntity_HI();
							q3.setQaFiletype("标签备案样式");
							q3.setSupplierId(supplierId);
							q3.setProductHeadId(productHeadId);
							q3.setQaCodetype("5");
							q3.setDateType("3");
							q3.setIsUpdate("0");
							PlmProductQaFileEntity_HI q4 = new PlmProductQaFileEntity_HI();
							q4.setQaFiletype("说明书备案样式");
							q4.setSupplierId(supplierId);
							q4.setProductHeadId(productHeadId);
							q4.setQaCodetype("5");
							q4.setDateType("3");
							q4.setIsUpdate("0");
							qadata.add(q1);
							qadata.add(q2);
							qadata.add(q3);
							qadata.add(q4);
							plmProductQaFileDao.save(qadata);

						} else if (otherinfo.equals("2")) // 医疗器械
						{
							List<PlmProductQaFileEntity_HI> qadata = new ArrayList<PlmProductQaFileEntity_HI>();
							PlmProductQaFileEntity_HI q1 = new PlmProductQaFileEntity_HI();
							q1.setQaFiletype("产品技术要求");
							q1.setSupplierId(supplierId);
							q1.setProductHeadId(productHeadId);
							q1.setQaCodetype("2");
							q1.setDateType("3");
							q1.setIsUpdate("0");
							PlmProductQaFileEntity_HI q2 = new PlmProductQaFileEntity_HI();
							q2.setQaFiletype("合格证明文件");
							q2.setSupplierId(supplierId);
							q2.setProductHeadId(productHeadId);
							q2.setQaCodetype("5");
							q2.setDateType("3");
							q2.setIsUpdate("0");
							PlmProductQaFileEntity_HI q3 = new PlmProductQaFileEntity_HI();
							q3.setQaFiletype("标签备案样式");
							q3.setSupplierId(supplierId);
							q3.setProductHeadId(productHeadId);
							q3.setQaCodetype("5");
							q3.setDateType("3");
							q3.setIsUpdate("0");
							PlmProductQaFileEntity_HI q4 = new PlmProductQaFileEntity_HI();
							q4.setQaFiletype("说明书备案样式");
							q4.setSupplierId(supplierId);
							q4.setProductHeadId(productHeadId);
							q4.setQaCodetype("5");
							q4.setDateType("3");
							q4.setIsUpdate("0");
							qadata.add(q1);
							qadata.add(q2);
							qadata.add(q3);
							qadata.add(q4);
							plmProductQaFileDao.save(qadata);
						}
					}
				}

				for (int i = 0; i < barcodedata.size(); i++) {
					PlmProductBarcodeTableEntity_HI barcode = barcodedata
							.get(i);
					if (barcode.getFlags().equals(code)) {
						barcode.setProductHeadId(productHeadId);
						plmProductBarcodeTableDao.save(barcode);
					}
				}

			}
			wb.close();
			is.close();
		} catch (Exception e) {

			LOGGER.error(e.getMessage(), e);
			throw new ServerException(e.getMessage());
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
				new JSONArray().fluentAdd(param));
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 *
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		String strCell = "";
		switch (cell.getCellTypeEnum()) {
		case STRING:
			strCell = cell.getStringCellValue();
			break;
		case NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				return sdf.format(date);
			}

			strCell = String.valueOf(cell.getNumericCellValue());
			if (strCell.contains(".")) {
				strCell = strCell.substring(0, strCell.indexOf("."));
			}

			break;
		case BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}

		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 *
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValueXss(XSSFCell cell) {
		if (cell == null) {
			return "";
		}
		String strCell = "";
		switch (cell.getCellTypeEnum()) {
		case STRING:
			strCell = cell.getStringCellValue().trim();
			break;
		case NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				return sdf.format(date);
			}

			strCell = String.valueOf(cell.getNumericCellValue());
			if (strCell.endsWith(".0")) {
				strCell = strCell.substring(0, strCell.indexOf("."));
			}

			break;
		case BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}

		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	// 得到结果集
	public List<PlmProductUpdatepropertisEntity_HI> getDataByJSON(
			JSONObject data, String tableName, JSONObject params,
			PlmProductHeadEntity_HI oldobj, Integer businessId) {
		Integer productHeadId = oldobj.getProductHeadId();
		List<PlmProductUpdatepropertisEntity_HI> listdata = new ArrayList<PlmProductUpdatepropertisEntity_HI>();
		Integer deptId = params.getInteger("deptId");
		String deptname = params.getString("deptName");
		Iterator itr = data.entrySet().iterator();
		while (itr.hasNext()) {
			Object j = itr.next();
			String values = j.toString();
			String key = values.split("=")[0];
			String value = values.split("=")[1];

			String columnname = key.split("_")[0]; // 字段中文名
			String column = key.split("_")[1]; // 字段名
			String oldvalue = value.split("&&&")[0];
			String newvalue = value.split("&&&")[1];
			PlmProductUpdatepropertisEntity_HI hi = new PlmProductUpdatepropertisEntity_HI();
			hi.setTablesName(tableName);

			hi.setOldValue(oldvalue);
			hi.setNewValue(newvalue);
			hi.setProductHeadId(productHeadId.toString());
			hi.setBusinessId(businessId);
			hi.setUpdateName(columnname);
			hi.setColumnNames(column);
			hi.setDeptId(deptId);
			hi.setProductNo(oldobj.getPlmCode());
			hi.setProductName(oldobj.getProductName());
			hi.setProductNo(oldobj.getPlmCode());
			hi.setProductName(oldobj.getProductName());
			hi.setDeptName(deptname);
			listdata.add(hi);
		}
		return listdata;

	}

	@Override
	public JSONObject updateProductInfo(JSONObject queryParamJSON)
			throws ServerException {
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null);

	}

	// 得到这个实体类的所有修改字段
	public List<PlmProductUpdatepropertisEntity_HI> getDataByObject(Object obj,
			JSONObject param, Integer businessId, PlmProductHeadEntity_HI head,
			String tableName) throws IllegalArgumentException,
			IllegalAccessException {
		Integer productHeadId = head.getProductHeadId();
		List<PlmProductUpdatepropertisEntity_HI> listdata = new ArrayList<PlmProductUpdatepropertisEntity_HI>();
		Integer deptId = param.getInteger("deptId");
		String deptname = param.getString("deptName");
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field fl : fields) {
			PlmProductUpdatepropertisEntity_HI hi = new PlmProductUpdatepropertisEntity_HI();
			fl.setAccessible(true);
			String coname = "";
			columnNames co = fl.getAnnotation(columnNames.class);
			if (co != null) {
				coname = co.name();
				Object value = fl.get(obj);
				if (value == null || value.equals("")) {
					continue;
				}
				if (value instanceof Date) {
					Date d = (Date) value;
					SimpleDateFormat simple = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					hi.setNewValue(simple.format(d));
				} else {
					hi.setNewValue(value.toString());
				}

				hi.setTablesName(tableName);
				hi.setProductHeadId(productHeadId.toString());
				hi.setBusinessId(businessId);
				hi.setUpdateName(coname);
				hi.setColumnNames(fl.getName());
				hi.setDeptId(deptId);
				hi.setProductNo(head.getPlmCode());
				hi.setProductName(head.getProductName());
				hi.setProductNo(head.getPlmCode());
				hi.setProductName(head.getProductName());
				hi.setDeptName(deptname);
				listdata.add(hi);

			}
		}
		return listdata;
	}

	// 得到前后改变的字段值
	public List<String> getDeffcuteCloumValue(Object oldobj, Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> ma = new HashMap<String, Object>();
		Map<String, Object> ma2 = new HashMap<String, Object>();
		Field[] fields = oldobj.getClass().getDeclaredFields();
		for (Field fl : fields) {
			fl.setAccessible(true);
				Object value = fl.get(oldobj);
				if (value == null) {
					ma.put( fl.getName(), "");
				} else {
					ma.put(fl.getName(), value);
				}


		}
		Field[] fields2 = obj.getClass().getDeclaredFields();
		for (Field f2 : fields2) {
			f2.setAccessible(true);
				Object value = f2.get(obj);
				if (value == null) {
					ma2.put(f2.getName(), "");
				} else {
					ma2.put(f2.getName(), value);
				}


		}
		List<String> resultcolumn=new ArrayList<String>();
		for (String key : ma.keySet()) {
			if(!ma2.containsKey(key))
			{
				continue;
			}
				if (!ma.get(key).equals(ma2.get(key))) {
					Object value = ma2.get(key);
					if (value instanceof Date) {
//						Date d = (Date) value;
//						String v2 = "";
//						Object v1 = ma.get(key);
//						Date d2 = null;
//						if (!v1.equals("")) {
//							d2 = (Date) v1;
//						}
//
//						SimpleDateFormat simple = new SimpleDateFormat(
//								"yyyy-MM-dd HH:mm:ss");
//						String v = simple.format(d);
//						if (d2 != null) {
//							v2 = simple.format(d2); // 原值
//						}
//
//						if (!v.equals(v2)) {
//							result.put(key, v2 + "&&&" + v);
//						}
						continue;
					} else {

						resultcolumn.add(key);
					}

			}
		}
		return resultcolumn;
	}

	@Override
	public JSONObject saveObProduct(JSONObject param) throws ServerException {
		try {

			JSONObject headparam = param.getJSONObject("headInfo"); // 商品头表信息
			JSONArray supplierlist = param.getJSONArray("supplierlist");// 供应商行信息
			JSONArray barcodelist = param.getJSONArray("barcodelist");// 条码行信息
			Integer productHeadId = null;
			param.put("operatorUserId", param.getInteger("operatorUserId"));
			if (!headparam.containsKey("productHeadId")) { // 新增
				headparam.put("plmCode", this.getPlmProduct());
				headparam.put("createdstr", param.getString("varUserFullName"));
				headparam.put("createdEmp", param.getString("createdEmp"));
				headparam.put("createdEname", param.getString("createdEname"));
				headparam.put("userDept", param.getString("userDept"));
				headparam
						.put("userGroupcode", param.getString("userGroupcode"));

				if (headparam.containsKey("obId")) {
					String obId = headparam.getString("obId");
					if (!obId.equals("")) {
						JSONObject obparam = new JSONObject();
						obparam.put("obId", obId);
						List<PlmDevelopmentInfoEntity_HI> oblist = developinfo
								.findList(obparam);

						if (oblist.size() > 0) {
							PlmDevelopmentInfoEntity_HI infoobj = oblist.get(0);
							infoobj.setProductStatus("INTRODUCING");
							developinfo.update(infoobj);
						}
					}
				}
			}
			headparam.put("supplierCount", supplierlist.size());
			PlmProductHeadEntity_HI saveproduct = this.saveOrUpdate(headparam);
			productHeadId = saveproduct.getProductHeadId();
			for (int i = 0; i < supplierlist.size(); i++) { // 新增或修改供应商行信息
				JSONObject supplierobj = supplierlist.getJSONObject(i);
				// 采购操作时
				supplierobj.remove("isSubmit");
				supplierobj.remove("returnStatus");
				supplierobj.put("isSubmit", "0");
				supplierobj.put("returnStatus", "0");
				String supplierId = supplierobj.getString("supplierCode");
				// 默认新增图片文件
				if (!supplierobj.containsKey("id")) { // 新增操作
					PlmProductPicfileTableEntity_HI pic1 = new PlmProductPicfileTableEntity_HI();
					pic1.setPicType("1");
					pic1.setSupplierId(supplierId);
					pic1.setProductHeadId(saveproduct.getProductHeadId());
					pic1.setIsUpdate("0");
					pic1.setIsRequire("Y");
					pic1.setCreatedBy(param.getInteger("userId"));
					PlmProductPicfileTableEntity_HI pic2 = new PlmProductPicfileTableEntity_HI();
					pic2.setPicType("2");
					pic2.setSupplierId(supplierId);
					pic2.setProductHeadId(saveproduct.getProductHeadId());
					pic2.setIsUpdate("0");
					pic2.setIsRequire("Y");
					pic2.setCreatedBy(param.getInteger("userId"));
					PlmProductPicfileTableEntity_HI pic3 = new PlmProductPicfileTableEntity_HI();
					pic3.setPicType("3");
					pic3.setSupplierId(supplierId);
					pic3.setIsUpdate("0");
					pic3.setIsRequire("Y");
					pic3.setProductHeadId(saveproduct.getProductHeadId());
					pic3.setCreatedBy(param.getInteger("userId"));
					PlmProductPicfileTableEntity_HI pic4 = new PlmProductPicfileTableEntity_HI();
					pic4.setPicType("4");
					pic4.setIsRequire("Y");
					pic4.setSupplierId(supplierId);
					pic4.setProductHeadId(saveproduct.getProductHeadId());
					pic4.setIsUpdate("0");
					pic4.setCreatedBy(param.getInteger("userId"));
					PlmProductPicfileTableEntity_HI pic5 = new PlmProductPicfileTableEntity_HI();
					pic5.setPicType("5");
					pic5.setIsRequire("Y");
					pic5.setSupplierId(supplierId);
					pic5.setProductHeadId(saveproduct.getProductHeadId());
					pic5.setIsUpdate("0");
					pic5.setCreatedBy(param.getInteger("userId"));
					PlmProductPicfileTableEntity_HI pic6 = new PlmProductPicfileTableEntity_HI();
					pic6.setPicType("6");
					pic6.setSupplierId(supplierId);
					pic6.setProductHeadId(saveproduct.getProductHeadId());
					pic6.setIsUpdate("0");
					pic6.setIsRequire("Y");
					pic6.setCreatedBy(param.getInteger("userId"));
					PlmProductPicfileTableEntity_HI pic7 = new PlmProductPicfileTableEntity_HI();
					pic7.setPicType("7");
					pic7.setSupplierId(supplierId);
					pic7.setProductHeadId(saveproduct.getProductHeadId());
					pic7.setIsUpdate("0");
					pic7.setIsRequire("Y");
					pic7.setCreatedBy(param.getInteger("userId"));
					List<PlmProductPicfileTableEntity_HI> lidata = new ArrayList<PlmProductPicfileTableEntity_HI>();
					lidata.add(pic1);
					lidata.add(pic2);
					lidata.add(pic3);
					lidata.add(pic4);
					lidata.add(pic5);
					lidata.add(pic6);
					lidata.add(pic7);
					plmProductPicfileTableDao.save(lidata);

					// 药品或医疗器械时 添加默认资质文件
					String otherinfo = saveproduct.getOtherInfo();
					if (otherinfo.equals("1")) // 药品
					{
						List<PlmProductQaFileEntity_HI> qadata = new ArrayList<PlmProductQaFileEntity_HI>();
						PlmProductQaFileEntity_HI q1 = new PlmProductQaFileEntity_HI();
						q1.setQaFiletype("药品注册证");
						q1.setSupplierId(supplierId);
						q1.setProductHeadId(saveproduct.getProductHeadId());
						q1.setQaCodetype("3");
						q1.setDateType("3");
						q1.setIsUpdate("0");
						q1.setCreatedBy(param.getInteger("userId"));
						PlmProductQaFileEntity_HI q2 = new PlmProductQaFileEntity_HI();
						q2.setQaFiletype("质量标准");
						q2.setSupplierId(supplierId);
						q2.setProductHeadId(saveproduct.getProductHeadId());
						q2.setQaCodetype("5");
						q2.setDateType("3");
						q2.setIsUpdate("0");
						q2.setCreatedBy(param.getInteger("userId"));
						PlmProductQaFileEntity_HI q3 = new PlmProductQaFileEntity_HI();
						q3.setQaFiletype("标签备案样式");
						q3.setSupplierId(supplierId);
						q3.setProductHeadId(saveproduct.getProductHeadId());
						q3.setQaCodetype("5");
						q3.setDateType("3");
						q3.setIsUpdate("0");
						q3.setCreatedBy(param.getInteger("userId"));
						PlmProductQaFileEntity_HI q4 = new PlmProductQaFileEntity_HI();
						q4.setQaFiletype("说明书备案样式");
						q4.setSupplierId(supplierId);
						q4.setProductHeadId(saveproduct.getProductHeadId());
						q4.setQaCodetype("5");
						q4.setDateType("3");
						q4.setIsUpdate("0");
						q4.setCreatedBy(param.getInteger("userId"));
						qadata.add(q1);
						qadata.add(q2);
						qadata.add(q3);
						qadata.add(q4);
						plmProductQaFileDao.save(qadata);

					} else if (otherinfo.equals("2")) // 医疗器械
					{
						List<PlmProductQaFileEntity_HI> qadata = new ArrayList<PlmProductQaFileEntity_HI>();
						PlmProductQaFileEntity_HI q1 = new PlmProductQaFileEntity_HI();
						q1.setQaFiletype("产品技术要求");
						q1.setSupplierId(supplierId);
						q1.setProductHeadId(saveproduct.getProductHeadId());
						q1.setQaCodetype("2");
						q1.setDateType("3");
						q1.setIsUpdate("0");
						q1.setCreatedBy(param.getInteger("userId"));
						PlmProductQaFileEntity_HI q2 = new PlmProductQaFileEntity_HI();
						q2.setQaFiletype("合格证明文件");
						q2.setSupplierId(supplierId);
						q2.setProductHeadId(saveproduct.getProductHeadId());
						q2.setQaCodetype("5");
						q2.setDateType("3");
						q2.setIsUpdate("0");
						q2.setCreatedBy(param.getInteger("userId"));
						PlmProductQaFileEntity_HI q3 = new PlmProductQaFileEntity_HI();
						q3.setQaFiletype("标签备案样式");
						q3.setSupplierId(supplierId);
						q3.setProductHeadId(saveproduct.getProductHeadId());
						q3.setQaCodetype("5");
						q3.setDateType("3");
						q3.setIsUpdate("0");
						q3.setCreatedBy(param.getInteger("userId"));
						PlmProductQaFileEntity_HI q4 = new PlmProductQaFileEntity_HI();
						q4.setQaFiletype("说明书备案样式");
						q4.setSupplierId(supplierId);
						q4.setProductHeadId(saveproduct.getProductHeadId());
						q4.setQaCodetype("5");
						q4.setDateType("3");
						q4.setIsUpdate("0");
						q4.setCreatedBy(param.getInteger("userId"));
						qadata.add(q1);
						qadata.add(q2);
						qadata.add(q3);
						qadata.add(q4);
						plmProductQaFileDao.save(qadata);
					}

				}

				supplierobj
						.put("productHeadId", saveproduct.getProductHeadId());
				supplierobj.put("operatorUserId",
						param.getInteger("operatorUserId"));
				plmProductSupplierInfoDao.saveOrUpdate(supplierobj);
			}

			for (int i = 0; i < barcodelist.size(); i++) { // 新增或修改条码行信息
				JSONObject barcodeobj = barcodelist.getJSONObject(i);

				barcodeobj.put("productHeadId", saveproduct.getProductHeadId());
				barcodeobj.put("operatorUserId",
						param.getInteger("operatorUserId"));
				barcodeobj.put("lastUpdateLogin", 1);
				plmProductBarcodeTableDao.saveOrUpdate(barcodeobj);
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, productHeadId);
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new ServerException("服务异常");
		}
	}

	public static File getNetUrl(String netUrl) {
		// 判断http和https
		File file = null;
		if (netUrl.startsWith("https://")) {
			file = getNetUrlHttps(netUrl);
		} else {
			file = getNetUrlHttp(netUrl);
		}
		return file;
	}

	public static File getNetUrlHttp(String netUrl) {
		// 对本地文件命名
		String fileName = netUrl;
		File file = null;

		URL urlfile;
		InputStream inStream = null;
		OutputStream os = null;
		try {
			file = File.createTempFile("net_url", URLEncoder.encode(fileName));
			// 下载
			urlfile = new URL(netUrl);
			inStream = urlfile.openStream();
			os = new FileOutputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			// log.error("远程图片获取错误：" + netUrl);
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
					os.close();
				}
				if (null != inStream) {
					inStream.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return file;
	}

	public static File getNetUrlHttps(String fileUrl) {
		// 对本地文件进行命名
		String file_name = fileUrl;
		File file = null;

		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			file = File.createTempFile("net_url", file_name);

			SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
			sslcontext.init(null, new TrustManager[] { new X509TrustUtiil() },
					new java.security.SecureRandom());
			URL url = new URL(fileUrl);

			HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
				@Override
				public boolean verify(String s, SSLSession sslsession) {
					// logger.warn("WARNING: Hostname is not matched for cert.");
					return true;
				}
			};
			HttpsURLConnection
					.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext
					.getSocketFactory());
			HttpsURLConnection urlCon = (HttpsURLConnection) url
					.openConnection();
			urlCon.setConnectTimeout(6000);
			urlCon.setReadTimeout(6000);
			int code = urlCon.getResponseCode();
			if (code != HttpURLConnection.HTTP_OK) {
				throw new Exception("文件读取失败");
			}
			// 读文件流
			in = new DataInputStream(urlCon.getInputStream());
			out = new DataOutputStream(new FileOutputStream(file));
			byte[] buffer = new byte[2048];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			// log.error("远程图片获取错误：" + fileUrl);
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != in) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return file;
	}

	public String getPlmProduct() {
		return generateCodeServer.generateCode("", new SimpleDateFormat(
				"yyyyMMdd"), 4);

	}

	// 修改属性字段
	@Override
	public JSONObject updateConfirmProduct(JSONObject param)
			throws ServerException {
		try {
			// {"id":"5602","userId":1766,"varUserId":"1766","status":"APPROVAL","processDefinitionKey":"TE"}
			// JSONObject jo = param.getJSONObject("Info");

			// iPlmApiServer
			String id = param.getString("id");
			JSONObject ja = new JSONObject();

			String headid = id.split("_")[0];
			PlmProductHeadEntity_HI headobj = this.getById(new Integer(headid));

			// headobj.setOrderStatus("6");
			JSONObject pa = new JSONObject();
			pa.put("productHeadId", id);
			List<PlmProductUpdatepropertisEntity_HI> dali = plmProductUpdatepropertis
					.findList(pa);

			UpdatePropertiesMap r = new UpdatePropertiesMap();
			Map<String, String> data = r.getMap();
			boolean issend = false;
			for (PlmProductUpdatepropertisEntity_HI prop : dali) {
				if (data.containsKey(prop.getColumnNames())) {
					issend = true;
					break;
				}
			}

			if (issend) {
				// ja.put("productHeadId", id);
				// iPlmApiServer.updateUdaMethod(ja);
			}

			// 更新字段
			this.getUpdateObject(dali, data, "other");
			// this.update(headEntity); // 更新操作

			List<PlmProductModifyCheckEntity_HI> moli = new ArrayList<PlmProductModifyCheckEntity_HI>();

			for (PlmProductUpdatepropertisEntity_HI po : dali) {
				po.setFlag("true");
				po.setOrderStatus("2");
				po.setOrderStatusname("已审批");
				plmProductUpdatepropertis.update(po);

				PlmProductModifyCheckEntity_HI moobj = new PlmProductModifyCheckEntity_HI();
				moobj.setRmsCode(po.getProductNo());
				moobj.setColumnName(po.getColumnNames());
				moobj.setLineId(po.getBusinessId());
				moobj.setNewValue(po.getNewValue());
				moobj.setOldValue(po.getOldValue());
				moobj.setProductHeadId(headobj.getProductHeadId());
				moobj.setTableName(po.getTablesName());
				moobj.setOperatorUserId(1);
				moli.add(moobj);
			}

			for (PlmProductModifyCheckEntity_HI c : moli) {
				JSONObject js = new JSONObject();
				js.put("tableName", c.getTableName());
				js.put("productHeadId", c.getProductHeadId());
				js.put("lineId", c.getLineId());
				js.put("columnName", c.getColumnName());
				List<PlmProductModifyCheckEntity_HI> result = plmProductModify
						.findList(js);
				if (result.size() == 0) {
					c.setStatus("2"); // 审批完成
					plmProductModifyCheckServer.save(c);
				} else {
					PlmProductModifyCheckEntity_HI cobj = result.get(0);
					cobj.setStatus("2"); // 审批完成
					cobj.setOldValue(c.getOldValue());
					cobj.setNewValue(c.getNewValue());
					plmProductModifyCheckServer.update(cobj);
				}

			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					null);
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new ServerException("服务异常");
		}

	}

	private void getUpdateObject(List<PlmProductUpdatepropertisEntity_HI> dali,
			Map<String, String> data, String type)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ParseException {

		PlmProductHeadEntity_HI prohead = null;
		Map<Integer, PlmProductSupplierInfoEntity_HI> supplier = new HashMap<Integer, PlmProductSupplierInfoEntity_HI>();
		// Map<Integer, PlmProductStoreEntity_HI> store = new HashMap<Integer,
		// PlmProductStoreEntity_HI>();
		// Map<Integer, PlmProductBarcodeTableEntity_HI> barcode = new
		// HashMap<Integer, PlmProductBarcodeTableEntity_HI>();
		Map<Integer, PlmProductPicfileTableEntity_HI> picfile = new HashMap<Integer, PlmProductPicfileTableEntity_HI>();
		Map<Integer, PlmProductQaFileEntity_HI> qafile = new HashMap<Integer, PlmProductQaFileEntity_HI>();
		Map<Integer, PlmProductPriceEntity_HI> price = new HashMap<Integer, PlmProductPriceEntity_HI>();
		// Map<Integer, PlmProductSalesPropertiesEntity_HI> sale = new
		// HashMap<Integer, PlmProductSalesPropertiesEntity_HI>();
		// Map<Integer, PlmProductOnlineChannalEntity_HI> channel = new
		// HashMap<Integer, PlmProductOnlineChannalEntity_HI>();
		Map<Integer, PlmProductFileEntity_HI> file = new HashMap<Integer, PlmProductFileEntity_HI>();
		Map<Integer, PlmProductDrugEntity_HI> drug = new HashMap<Integer, PlmProductDrugEntity_HI>();
		Map<Integer, PlmProductMedicalinfoEntity_HI> medi = new HashMap<Integer, PlmProductMedicalinfoEntity_HI>();
		for (PlmProductUpdatepropertisEntity_HI pro : dali) {
			String propry = pro.getColumnNames(); // 得到属性字段名
			if (data.containsKey(propry) && type.equals("other")) { // 需要rms修改返回
				continue;
			}
			Integer businessid = pro.getBusinessId();
			String tablename = pro.getTablesName();
			if (tablename.equals("PLM_PRODUCT_HEAD")) {
				if (prohead == null) {
					Integer headid = pro.getBusinessId();
					PlmProductHeadEntity_HI oldobj = this.getById(headid);
					prohead = (PlmProductHeadEntity_HI) this.getNewObject(
							oldobj, propry, pro);
				} else {
					prohead = (PlmProductHeadEntity_HI) this.getNewObject(
							prohead, propry, pro);
				}

			} else if (tablename.equals("PLM_PRODUCT_SUPPLIER_INFO")) {

				if (!supplier.containsKey(businessid)) {
					PlmProductSupplierInfoEntity_HI oldobj = plmProductSupplierInfoDao
							.getById(businessid);
					PlmProductSupplierInfoEntity_HI newobj = (PlmProductSupplierInfoEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					supplier.put(businessid, newobj);

				} else {
					PlmProductSupplierInfoEntity_HI oldobj = supplier
							.get(businessid);
					PlmProductSupplierInfoEntity_HI newobj = (PlmProductSupplierInfoEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					supplier.remove(businessid);
					supplier.put(businessid, newobj);
				}

			} else if (tablename.equals("PLM_PRODUCT_PICFILE_TABLE")) {
				if (!picfile.containsKey(businessid)) {
					PlmProductPicfileTableEntity_HI oldobj = plmProductPicfileTableDao
							.getById(businessid);
					PlmProductPicfileTableEntity_HI newobj = (PlmProductPicfileTableEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					picfile.put(businessid, newobj);
				} else {
					PlmProductPicfileTableEntity_HI oldobj = picfile
							.get(businessid);
					PlmProductPicfileTableEntity_HI newobj = (PlmProductPicfileTableEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					picfile.remove(businessid);
					picfile.put(businessid, newobj);
				}
			} else if (tablename.equals("PLM_PRODUCT_QA_FILE")) {
				if (!qafile.containsKey(businessid)) {
					PlmProductQaFileEntity_HI oldobj = plmProductQaFileDao
							.getById(businessid);
					PlmProductQaFileEntity_HI newobj = (PlmProductQaFileEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					qafile.put(businessid, newobj);
				} else {
					PlmProductQaFileEntity_HI oldobj = qafile.get(businessid);
					PlmProductQaFileEntity_HI newobj = (PlmProductQaFileEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					qafile.remove(businessid);
					qafile.put(businessid, newobj);
				}
			} else if (tablename.equals("PLM_PRODUCT_PRICE")) {
				if (!price.containsKey(businessid)) {
					PlmProductPriceEntity_HI oldobj = plmProductPriceDao
							.getById(businessid);
					PlmProductPriceEntity_HI newobj = (PlmProductPriceEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					price.put(businessid, newobj);
				} else {
					PlmProductPriceEntity_HI oldobj = price.get(businessid);
					PlmProductPriceEntity_HI newobj = (PlmProductPriceEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					price.remove(businessid);
					price.put(businessid, newobj);
				}
			}

			else if (tablename.equals("PLM_PRODUCT_FILE")) {
				if (!file.containsKey(businessid)) {
					PlmProductFileEntity_HI oldobj = plmProductFileDao
							.getById(businessid);
					PlmProductFileEntity_HI newobj = (PlmProductFileEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					file.put(businessid, newobj);
				} else {
					PlmProductFileEntity_HI oldobj = file.get(businessid);
					PlmProductFileEntity_HI newobj = (PlmProductFileEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					file.remove(businessid);
					file.put(businessid, newobj);
				}
			} else if (tablename.equals("PLM_PRODUCT_DRUG")) {
				if (!drug.containsKey(businessid)) {
					PlmProductDrugEntity_HI oldobj = plmProductDrug
							.getById(businessid);
					PlmProductDrugEntity_HI newobj = (PlmProductDrugEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					drug.put(businessid, newobj);
				} else {
					PlmProductDrugEntity_HI oldobj = drug.get(businessid);
					PlmProductDrugEntity_HI newobj = (PlmProductDrugEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					drug.remove(businessid);
					drug.put(businessid, newobj);
				}
			} else if (tablename.equals("PLM_PRODUCT_MEDICALINFO")) {
				if (!medi.containsKey(businessid)) {
					PlmProductMedicalinfoEntity_HI oldobj = plmMedicainfo
							.getById(businessid);
					PlmProductMedicalinfoEntity_HI newobj = (PlmProductMedicalinfoEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					medi.put(businessid, newobj);
				} else {
					PlmProductMedicalinfoEntity_HI oldobj = medi
							.get(businessid);
					PlmProductMedicalinfoEntity_HI newobj = (PlmProductMedicalinfoEntity_HI) this
							.getNewObject(oldobj, propry, pro);
					medi.remove(businessid);
					medi.put(businessid, newobj);
				}
			}

		}// for

		if (prohead != null) {
			this.update(prohead);
		}
		if (supplier.size() > 0) {
			for (Map.Entry<Integer, PlmProductSupplierInfoEntity_HI> su : supplier
					.entrySet()) {
				PlmProductSupplierInfoEntity_HI info = su.getValue();
				// info.setIsUpdate("0"); // 设置为可显示状态
				plmProductSupplierInfoDao.update(info);
			}
		}

		if (picfile.size() > 0) {
			for (Map.Entry<Integer, PlmProductPicfileTableEntity_HI> ba : picfile
					.entrySet()) {
				PlmProductPicfileTableEntity_HI pic = ba.getValue();
				pic.setIsUpdate("0");
				plmProductPicfileTableDao.update(pic);
			}
		}
		if (qafile.size() > 0) {
			for (Map.Entry<Integer, PlmProductQaFileEntity_HI> ba : qafile
					.entrySet()) {
				PlmProductQaFileEntity_HI pic = ba.getValue();
				pic.setIsUpdate("0");
				plmProductQaFileDao.update(pic);
			}
		}

		if (price.size() > 0) {
			for (Map.Entry<Integer, PlmProductPriceEntity_HI> ba : price
					.entrySet()) {
				PlmProductPriceEntity_HI pic = ba.getValue();
				pic.setIsUpdate("0");
				plmProductPriceDao.update(pic);
			}
		}

		if (file.size() > 0) {
			for (Map.Entry<Integer, PlmProductFileEntity_HI> ba : file
					.entrySet()) {
				PlmProductFileEntity_HI pic = ba.getValue();
				pic.setIsUpdate("0");
				plmProductFileDao.update(pic);
			}
		}
		if (drug.size() > 0) {
			for (Map.Entry<Integer, PlmProductDrugEntity_HI> ba : drug
					.entrySet()) {
				PlmProductDrugEntity_HI pic = ba.getValue();
				plmProductDrug.update(pic);
			}
		}
		if (medi.size() > 0) {
			for (Map.Entry<Integer, PlmProductMedicalinfoEntity_HI> ba : medi
					.entrySet()) {
				PlmProductMedicalinfoEntity_HI pic = ba.getValue();
				plmMedicainfo.update(pic);
			}
		}

	}

	public Object getNewObject(Object headobj, String propry,
			PlmProductUpdatepropertisEntity_HI pro)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ParseException {
		String provalue = propry.substring(0, 1).toUpperCase()
				.concat(propry.substring(1));
		String value = pro.getNewValue();
		Class c1 = headobj.getClass();
		Method getmethod = c1.getMethod("get" + provalue);
		String typestr = getmethod.getReturnType().toString();
		Method method = c1.getDeclaredMethod("set" + provalue,
				new Class[] { getmethod.getReturnType() });
		if (typestr.equals("class java.lang.String")) {
			method.invoke(headobj, new Object[] { value });
		} else if (typestr.equals("class java.lang.Integer")) {
			method.invoke(headobj, new Object[] { new Integer(value) });
		} else if (typestr.equals("class java.util.Date")) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(value);
			method.invoke(headobj, new Object[] { d });
		}
		return headobj;
	}

	@Override
	public Pagination<PlmProductAddReport> FindProductReportList(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmProductAddReport.querySql);
		Map<String, Object> params = new HashMap<String, Object>();
		SaafToolUtils.parperParam(param, "product.product_name", "productName",
				query, params, "fulllike");
		SaafToolUtils.parperParam(param, "product.brandname_cn", "brandnameCn",
				query, params, "fulllike");

		param.remove("productName");
		param.remove("brandnameCn");
		SaafToolUtils.parperHbmParam(PlmProductAddReport.class, param, query,
				params);
		query.append(PlmProductAddReport.getAppend(param));
		query.append(" order by PRODUCT.CREATION_DATE desc ");
		Pagination<PlmProductAddReport> pagination = PlmProductAddReportRO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);
		return pagination;
	}

	// 得到审批部门
	public List<JSONObject> getAlldept(
			List<PlmProductUpdatepropertisEntity_HI> li) {
		for (PlmProductUpdatepropertisEntity_HI fo : li) {
			String columnname = fo.getColumnNames();

		}
		return null;
	}

	@Override
	public JSONObject updateProductByExcel(JSONObject param)
			throws ServerException {
		String filepath = param.getString("filepath");
		File file = this.getNetUrl(filepath);
		List<PlmProductUpdatepropertisEntity_HI> listdata = new ArrayList<PlmProductUpdatepropertisEntity_HI>();
		try {
			InputStream is = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet headinfo = wb.getSheet("商品信息"); // 头信息
			XSSFSheet productprice = wb.getSheet("商品售价"); // 商品售价
			XSSFSheet supplierinfo = wb.getSheet("商品成本,包装及地点信息"); // 供应商信息
			Integer headrownum = headinfo.getLastRowNum(); // 头信息总行数
			Integer pricenum = productprice.getLastRowNum(); // 售价总行数
			Integer suppliernum = supplierinfo.getLastRowNum(); // 供应商信息总行数
			if (headrownum > 0 && pricenum > 0 && suppliernum > 1) {
				throw new ServerException("只能提交修改一个sheet页!");
			} else if (headrownum > 0 && pricenum > 0) {
				throw new ServerException("只能提交修改一个sheet页!");
			} else if (headrownum > 0 && suppliernum > 1) {
				throw new ServerException("只能提交修改一个sheet页!");
			} else if (pricenum > 0 && suppliernum > 1) {
				throw new ServerException("只能提交修改一个sheet页!");
			}

			if (headrownum > 0) // 头信息
			{
				List<PlmProductHeadEntity_HI> reli = new ArrayList<PlmProductHeadEntity_HI>();
				Map<String, PlmProductHeadEntity_HI> headlist = new HashMap<String, PlmProductHeadEntity_HI>();
				for (int i = 1; i <= headrownum; i++) {

					XSSFRow curent = headinfo.getRow(i);
					XSSFCell code = curent.getCell(0); // plmcode编码

					String result = this
							.valid(this.getStringCellValueXss(code));
					if (!result.equals("true")) {
						throw new ServerException("第" + (i + 1) + "行," + result);
					}
					XSSFCell productname = curent.getCell(1); // 商品中文名
					XSSFCell productename = curent.getCell(2); // 商品英文名
					XSSFCell dxtype = curent.getCell(3); // 代销类型
					XSSFCell dxbl = curent.getCell(4); // 代销比率
					PlmProductHeadEntity_HI hi = new PlmProductHeadEntity_HI();
					hi.setProductName(this.getStringCellValueXss(productname));
					hi.setProductEname(this.getStringCellValueXss(productename));
					hi.setConsignmentType(this.getStringCellValueXss(dxtype));
					hi.setConsignmentRate(this.getStringCellValueXss(dxbl));

					if (headlist.containsKey(this.getStringCellValueXss(code))) {
						throw new ServerException("第" + (i + 1)
								+ "行,存在相同plmCode!");
					} else {
						headlist.put(this.getStringCellValueXss(code), hi);
					}

					PlmProductHeadEntity_HI oldobj = this.getObjByplmCode(this
							.getStringCellValueXss(code));
					JSONObject jo = this.getDeffcuteCloum(oldobj, hi);
					Iterator m = jo.entrySet().iterator();
					while (m.hasNext()) {
						Object j = m.next();
						String values = j.toString();
						String key = values.split("=")[0];
						String value = values.split("=")[1];
						String columnname = key.split("_")[0]; // 字段中文名
						String column = key.split("_")[1]; // 字段名
						String oldvalue = value.split("&&&")[0];
						String newvalue = value.split("&&&")[1];
						PlmProductUpdatepropertisEntity_HI savehi = new PlmProductUpdatepropertisEntity_HI();
						savehi.setTablesName("PLM_PRODUCT_HEAD");
						savehi.setOldValue(oldvalue);
						savehi.setNewValue(newvalue);
						savehi.setProductHeadId(oldobj.getProductHeadId()
								.toString());
						savehi.setBusinessId(oldobj.getProductHeadId());
						savehi.setUpdateName(columnname);
						savehi.setColumnNames(column);
						savehi.setProductNo(oldobj.getPlmCode());
						savehi.setProductName(oldobj.getProductName());
						savehi.setProductNo(oldobj.getPlmCode());
						savehi.setProductName(oldobj.getProductName());
						listdata.add(savehi);
					}
					if (jo.size() > 0) {
						reli.add(oldobj);
					}
				}

				for (PlmProductHeadEntity_HI pl : reli) {
					pl.setOrderStatus("4");
					this.update(pl);
				}
				plmProductUpdatepropertis.save(listdata);
				wb.close();
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
						1, new JSONArray().fluentAdd(reli));

			}
			if (pricenum > 0) {
				List<PlmProductHeadEntity_HI> reli = new ArrayList<PlmProductHeadEntity_HI>();
				Map<String, String> pricelist = new HashMap<String, String>();
				Map<String, PlmProductHeadEntity_HI> lcli = new HashMap<String, PlmProductHeadEntity_HI>();
				for (int i = 1; i <= pricenum; i++) {
					XSSFRow curent = productprice.getRow(i);
					XSSFCell code = curent.getCell(0); // plmcode编码
					String result = this
							.valid(this.getStringCellValueXss(code));
					if (!result.equals("true")) {
						throw new ServerException("第" + (i + 1) + "行," + result);
					}
					XSSFCell sjfz = curent.getCell(1); // 售价分组
					if (pricelist.containsKey(this.getStringCellValueXss(code)
							+ "_" + this.getStringCellValueXss(sjfz))) {
						throw new ServerException("第" + (i + 1)
								+ "行,plmCode与售价分组存在相同数据!");
					} else {
						pricelist.put(
								this.getStringCellValueXss(code) + "_"
										+ this.getStringCellValueXss(sjfz),
								this.getStringCellValueXss(code) + "_"
										+ this.getStringCellValueXss(sjfz));
					}

					XSSFCell nq = curent.getCell(2); //
					XSSFCell bq = curent.getCell(3);
					XSSFCell dq = curent.getCell(4);
					XSSFCell xq = curent.getCell(5);
					XSSFCell jc = curent.getCell(6);
					XSSFCell five = curent.getCell(7);
					XSSFCell ten = curent.getCell(8);
					XSSFCell ft = curent.getCell(9); // 15
					XSSFCell tt = curent.getCell(10); // 20
					XSSFCell net = curent.getCell(11);
					XSSFCell xndp = curent.getCell(12);
					Map<String, String> li = new HashMap<String, String>(); // 映射数据字典
					li.put("6", this.getStringCellValueXss(jc)); // 机场
					li.put("7", this.getStringCellValueXss(five)); // 5%
					li.put("8", this.getStringCellValueXss(ten)); // 10%
					li.put("9", this.getStringCellValueXss(ft)); // 15%
					li.put("10", this.getStringCellValueXss(tt)); // 20%
					li.put("11", this.getStringCellValueXss(net)); // 网络
					li.put("12", this.getStringCellValueXss(xndp)); // 虚拟店铺
					li.put("13", this.getStringCellValueXss(dq)); // 东区
					li.put("14", this.getStringCellValueXss(xq)); // 西区
					li.put("15", this.getStringCellValueXss(bq)); // 北区
					li.put("16", this.getStringCellValueXss(nq)); // 北区
					XSSFCell gds = curent.getCell(12); // 广东省
					XSSFCell hns = curent.getCell(12);// 河南省
					if (this.getStringCellValueXss(sjfz).equals("普通商品")) {

						// 获取原来的售价分组区域
						PlmProductHeadEntity_HI oldobj = this
								.getObjByplmCode(this
										.getStringCellValueXss(code));
						if (!lcli.containsKey(this.getStringCellValueXss(code))) {
							lcli.put(this.getStringCellValueXss(code), oldobj);
							reli.add(oldobj);
						}
						Integer headid = oldobj.getProductHeadId();
						JSONObject price = new JSONObject();
						price.put("productHeadId", headid);
						List<PlmProductPriceEntity_HI> priceli = plmProductPriceDao
								.findList(price);
						for (PlmProductPriceEntity_HI hi : priceli) {
							String area = hi.getPriceArea();
							String oldprice = hi.getUnitPrice(); // 原来的售价
							String newprice = li.get(area);
							if (!oldprice.equals(newprice)) {// 修改字段
								PlmProductUpdatepropertisEntity_HI savehi = new PlmProductUpdatepropertisEntity_HI();
								savehi.setTablesName("PLM_PRODUCT_HEAD");
								savehi.setOldValue(oldprice);
								savehi.setNewValue(newprice);
								savehi.setProductHeadId(headid.toString());
								savehi.setBusinessId(hi.getPriceId());
								savehi.setUpdateName("零售价");
								savehi.setColumnNames("unitPrice");
								savehi.setProductNo(oldobj.getPlmCode());
								savehi.setProductName(oldobj.getProductName());
								savehi.setProductNo(oldobj.getPlmCode());
								savehi.setProductName(oldobj.getProductName());
								listdata.add(savehi);
							}
						}

					} else if (this.getStringCellValueXss(sjfz).equals("药品")) {

					} else {
						throw new ServerException("第" + (i + 1) + "行,"
								+ "售价分组不存在!");
					}

				}
				plmProductUpdatepropertis.save(listdata);
				wb.close();
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
						1, new JSONArray().fluentAdd(reli));
			}
			if (suppliernum > 1) {
				List<PlmProductHeadEntity_HI> reli = new ArrayList<PlmProductHeadEntity_HI>();
				Map<String, String> supplierlist = new HashMap<String, String>();
				Map<String, PlmProductHeadEntity_HI> lcli = new HashMap<String, PlmProductHeadEntity_HI>();
				for (int i = 2; i <= suppliernum; i++) {
					XSSFRow curent = supplierinfo.getRow(i);
					XSSFCell code = curent.getCell(0); // plmcode编码
					String result = this
							.valid(this.getStringCellValueXss(code));
					if (!result.equals("true")) {
						throw new ServerException("第" + (i + 1) + "行," + result);
					}

					XSSFCell supplierid = curent.getCell(1);
					if (supplierlist.containsKey(this
							.getStringCellValueXss(code)
							+ "_"
							+ this.getStringCellValueXss(supplierid))) {
						throw new ServerException("第" + (i + 1)
								+ "行,plmCode与供应商id存在相同数据!");
					} else {
						supplierlist
								.put(this.getStringCellValueXss(code)
										+ "_"
										+ this.getStringCellValueXss(supplierid),
										this.getStringCellValueXss(code)
												+ "_"
												+ this.getStringCellValueXss(supplierid));
					}
					XSSFCell weight = curent.getCell(2);// 中
					XSSFCell len = curent.getCell(3); // 外箱长
					XSSFCell k = curent.getCell(4); // 外箱宽
					XSSFCell height = curent.getCell(5); // 外箱高
					XSSFCell boxweight = curent.getCell(6); // 外箱重
					XSSFCell packunit = curent.getCell(7); // 包装规格
					XSSFCell innerpack = curent.getCell(8); // 内包装规格
					XSSFCell sxfs = curent.getCell(9); // 生效方式
					XSSFCell area = curent.getCell(10); // 区域
					XSSFCell placelist = curent.getCell(11); // 地点清单
					XSSFCell warehouse = curent.getCell(12); // 生效仓库
					XSSFCell md = curent.getCell(13); // 生效门店
					XSSFCell bz = curent.getCell(14); // 成本币种
					XSSFCell price = curent.getCell(15); // 成本价
					PlmProductSupplierInfoEntity_HI ho = new PlmProductSupplierInfoEntity_HI();
					ho.setWeight(new Float(this.getStringCellValueXss(weight)));
					ho.setBoxLength(new Float(this.getStringCellValueXss(len)));
					ho.setBoxBreadth(new Float(this.getStringCellValueXss(k)));
					ho.setBoxHeight(new Float(this
							.getStringCellValueXss(height)));
					ho.setBoxWeight(new Float(this
							.getStringCellValueXss(boxweight)));
					ho.setPackageSpe(new Integer(this
							.getStringCellValueXss(packunit)));
					ho.setInnerpackageSpe(new Integer(this
							.getStringCellValueXss(innerpack)));

					ho.setCurrencyCost(this.getStringCellValueXss(bz));
					ho.setPrice(this.getStringCellValueXss(price));

					PlmProductHeadEntity_HI oldobj = this.getObjByplmCode(this
							.getStringCellValueXss(code));
					if (!lcli.containsKey(this.getStringCellValueXss(code))) {
						lcli.put(this.getStringCellValueXss(code), oldobj);
						reli.add(oldobj);
					}
					JSONObject jo = this.getDeffcuteCloum(oldobj, ho);
					Iterator m = jo.entrySet().iterator();
					while (m.hasNext()) {
						Object j = m.next();
						String values = j.toString();
						String key = values.split("=")[0];
						String value = values.split("=")[1];
						String columnname = key.split("_")[0]; // 字段中文名
						String column = key.split("_")[1]; // 字段名
						String oldvalue = value.split("&&&")[0];
						String newvalue = value.split("&&&")[1];
						PlmProductUpdatepropertisEntity_HI savehi = new PlmProductUpdatepropertisEntity_HI();
						savehi.setTablesName("PLM_PRODUCT_HEAD");
						savehi.setOldValue(oldvalue);
						savehi.setNewValue(newvalue);
						savehi.setProductHeadId(oldobj.getProductHeadId()
								.toString());
						savehi.setBusinessId(oldobj.getProductHeadId());
						savehi.setUpdateName(columnname);
						savehi.setColumnNames(column);
						savehi.setProductNo(oldobj.getPlmCode());
						savehi.setProductName(oldobj.getProductName());
						savehi.setProductNo(oldobj.getPlmCode());
						savehi.setProductName(oldobj.getProductName());
						listdata.add(savehi);
					}

				}
				plmProductUpdatepropertis.save(listdata);
				wb.close();
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
						1, new JSONArray().fluentAdd(reli));
			}
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new ServerException(e.getLocalizedMessage());
		}

	}

	// 验证plmcode是否存在，以及是否正在审批中
	public String valid(String plmCode) {
		JSONObject jo = new JSONObject();
		jo.put("plmCode", plmCode);
		List<PlmProductHeadEntity_HI> da = this.findList(jo);
		if (da.size() == 0) {
			return "该plmCode不存在!";
		} else {
			PlmProductHeadEntity_HI headinfo = da.get(0);
			if (headinfo != null) {
				if (!headinfo.getOrderStatus().equals("6")) {
					return "只有已审批状态的单据才能提交修改";
				}
			}
		}
		return "true";
	}

	public PlmProductHeadEntity_HI getObjByplmCode(String plmCode) {
		JSONObject oldjson = new JSONObject();
		oldjson.put("plmCode", plmCode);
		List<PlmProductHeadEntity_HI> li = this.findList(oldjson);
		if (li.size() >= 1) {
			return li.get(0);
		} else {
			return null;
		}
	}

	// 得到改变的json
	public static JSONObject getDeffcuteCloum(Object oldobj, Object obj)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
		JSONObject change = new JSONObject();
		Map<String, Object> data = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		Class oldclass = oldobj.getClass();
		for (Field fl : fields) {
			fl.setAccessible(true);
			String filedname = fl.getName();

			columnNames co = fl.getAnnotation(columnNames.class);
			if (co != null) {
				Object value = fl.get(obj);
				if (value != null && !value.equals("")) {
					data.put(co.name() + "_" + filedname, value);
				}
			}

		}
		for (Map.Entry<String, Object> p : data.entrySet()) {
			String columName = p.getKey().split("_")[1];
			Object v1 = p.getValue(); // 新值
			Field curent = oldclass.getDeclaredField(columName);
			curent.setAccessible(true);
			Object value = curent.get(oldobj);
			if (value == null) {
				value = "";
			}
			if (!value.equals(v1)) {
				change.put(p.getKey(), value.toString() + "&&&" + v1.toString());
			}
		}
		return change;
	}

	@Override
	public Pagination<PlmProductAddReportT> FindProductReportTList(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmProductAddReportT.querySql);
		Map<String, Object> params = new HashMap<String, Object>();
		SaafToolUtils.parperParam(param, "HEAD.product_name", "productName",
				query, params, "fulllike");
		SaafToolUtils.parperParam(param, "HEAD.brandname_cn", "brandnameCn",
				query, params, "fulllike");

		param.remove("productName");
		param.remove("brandnameCn");
		SaafToolUtils.parperHbmParam(PlmProductAddReportT.class, param, query,
				params);

		query.append(PlmProductAddReportT.getAppend(param));
		query.append(" order by INFO.CREATION_DATE desc ");
		Pagination<PlmProductAddReportT> pagination = PlmProductAddReportTRO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public Pagination<PlmProductConditionReport> FindProductconditionReportLis(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmProductConditionReport.querySql);
		Map<String, Object> params = new HashMap<String, Object>();
		SaafToolUtils.parperParam(param, "product.product_name", "productName",
				query, params, "fulllike");
		SaafToolUtils.parperParam(param, "product.brandname_cn", "brandnameCn",
				query, params, "fulllike");

		param.remove("productName");
		param.remove("brandnameCn");
		SaafToolUtils.parperHbmParam(PlmProductConditionReport.class, param,
				query, params);
		query.append(PlmProductConditionReport.getAppend(param));
		query.append(" order by PRODUCT.CREATION_DATE desc ");
		Pagination<PlmProductConditionReport> pagination = PlmProductConditionReportRO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public Pagination<PlmProductPackageReport> FindProductUpdatePackage(
			JSONObject param, Integer pageIndex, Integer pageRows)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ParseException {
		Map<String, PlmProductPackageReport> da = new HashMap<String, PlmProductPackageReport>();

		param.put("tablesName", "PLM_PRODUCT_SUPPLIER_INFO");
		List<PlmProductUpdatepropertisEntity_HI_RO> uli = plmProductUpdatepropertis
				.findProductUpdateList(param, 1, 10000).getData();

		if (uli.size() > 0) {
			for (PlmProductUpdatepropertisEntity_HI_RO u : uli) {
				String columnNames = u.getColumnNames();
				if (columnNames.equals("weight")
						|| columnNames.equals("packageSpe")
						|| columnNames.equals("boxLength")
						|| columnNames.equals("boxBreadth")
						|| columnNames.equals("boxHeight")
						|| columnNames.equals("innerpackageSpe")) {// 判断条件
					String headid = u.getProductHeadId();
					if (!da.containsKey(headid)) {
						PlmProductPackageReport pl = new PlmProductPackageReport();
						PlmProductHeadEntity_HI head = this
								.getById(new Integer(headid));
						Integer id = u.getBusinessId();
						PlmProductSupplierInfoEntity_HI olddata = plmProductSupplierInfoDao
								.getById(id);
						// 初始化
						if (olddata.getWeight() == null
								|| olddata.getWeight().equals("null")) {
							pl.setWeight("旧值:无");
						} else {
							pl.setWeight("旧值：" + olddata.getWeight() == null ? ""
									: olddata.getWeight().toString());
						}

						if (olddata.getPackageSpe() == null
								|| olddata.getPackageSpe().equals("null")) {
							pl.setPackageSpe("旧值:无");
						} else {
							pl.setPackageSpe("旧值:"
									+ olddata.getPackageSpe().toString());
						}
						if (olddata.getBoxLength() == null
								|| olddata.getBoxLength().equals("null")) {
							pl.setBoxLength("旧值:无");
						} else {
							pl.setBoxLength("旧值:"
									+ olddata.getBoxLength().toString());
						}

						if (olddata.getBoxBreadth() == null
								|| olddata.getBoxBreadth().equals("null")) {
							pl.setBoxBreadth("旧值:无");
						} else {
							pl.setBoxBreadth("旧值:"
									+ olddata.getBoxBreadth().toString());
						}
						if (pl.getBoxHeight() == null
								|| pl.getBoxHeight().equals("null")) {
							pl.setBoxHeight("旧值:无");
						} else {
							pl.setBoxHeight("旧值:"
									+ olddata.getBoxHeight().toString());
						}

						if (pl.getInnerpackageSpe() == null
								|| pl.getInnerpackageSpe().equals("null")) {
							pl.setInnerpackageSpe("旧值:无");
						} else {
							pl.setInnerpackageSpe("旧值:"
									+ olddata.getInnerpackageSpe().toString());
						}
						pl.setProductName(head.getProductName());
						pl.setPlmCode(head.getPlmCode());
						pl.setSupplierId(olddata.getSupplierId());
						pl.setSupplierName(olddata.getSupplierName());
						pl.setUnit(head.getUnit());
						String productd = head.getDangerousProduct();
						if (productd.equals("1")) {
							pl.setDangerousProduct("是");
						} else if (productd.equals("2")) {
							pl.setDangerousProduct("否");
						}
						pl.setWarehouseGetDay(String.valueOf(head
								.getWarehouseGetDay()));
						pl.setWarehousePostDay(String.valueOf(head
								.getWarehousePostDay()));
						this.setObject(columnNames, "旧值:" + u.getOldValue()
								+ ",新值:" + u.getNewValue(), pl);
						da.put(headid, pl);
					} else {
						PlmProductPackageReport report = da.get(headid);
						this.setObject(columnNames, "旧值:" + u.getOldValue()
								+ ",新值:" + u.getNewValue(), report);
					}
				}

			}
		}
		List<PlmProductPackageReport> dp = new ArrayList<PlmProductPackageReport>();
		List<PlmProductPackageReport> data = new ArrayList<PlmProductPackageReport>();
		for (Map.Entry<String, PlmProductPackageReport> ma : da.entrySet()) {
			dp.add(ma.getValue());
		}

		Integer first = pageIndex * pageRows - pageRows;
		Integer end = first + pageRows;
		if (dp.size() < end) {
			end = dp.size();
		}
		for (int i = first; i < end; i++) {
			data.add(dp.get(i));
		}

		Pagination<PlmProductPackageReport> pagination = new Pagination<PlmProductPackageReport>();
		pagination.setCurIndex(Long.parseLong(pageIndex.toString()));
		pagination.setCount(dp.size());
		pagination.setPagesCount(dp.size() / pageRows);
		pagination.setPageSize(Long.parseLong(pageRows.toString()));
		pagination.setData(data);
		return pagination;
	}

	public void setObject(String columnNames, String value,
			PlmProductPackageReport pl) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ParseException {
		String propry = columnNames; // 得到属性字段名
		String provalue = propry.substring(0, 1).toUpperCase()
				.concat(propry.substring(1));
		Class c1 = pl.getClass();
		Method getmethod = c1.getMethod("get" + provalue);
		String typestr = getmethod.getReturnType().toString();
		Method method = c1.getDeclaredMethod("set" + provalue,
				new Class[] { getmethod.getReturnType() });
		if (typestr.equals("class java.lang.String")) {
			method.invoke(pl, new Object[] { value });
		} else if (typestr.equals("class java.lang.Integer")) {
			method.invoke(pl, new Object[] { new Integer(value) });
		} else if (typestr.equals("class java.util.Date")) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(value);
			method.invoke(pl, new Object[] { d });
		}
	}

	@Override
	public Pagination<PlmProductSupplierReport> FindProductUpdateSupplier(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		Map<String, List<PlmProductSupplierInfoEntity_HI>> group = new HashMap<String, List<PlmProductSupplierInfoEntity_HI>>();
		param.put("tablesName", "PLM_PRODUCT_SUPPLIER_INFO");
		List<PlmProductUpdatepropertisEntity_HI_RO> uli = plmProductUpdatepropertis
				.findProductUpdateList(param, 1, 10000).getData();

		for (PlmProductUpdatepropertisEntity_HI_RO cu : uli) {
			String columnNames = cu.getColumnNames();
			if (columnNames.equals("isMain")) { // 优先供应商
				String headid = cu.getProductHeadId();
				Integer id = cu.getBusinessId();
				PlmProductSupplierInfoEntity_HI olddata = plmProductSupplierInfoDao
						.getById(id);
				olddata.setIsMainsupplier(cu.getNewValue());
				if (group.containsKey(headid)) {
					List<PlmProductSupplierInfoEntity_HI> sp = group
							.get(headid);
					sp.add(olddata);
					System.out.println(sp.size());
					group.remove(headid);
					group.put(headid, sp);
				} else {

					List<PlmProductSupplierInfoEntity_HI> suli = new ArrayList<PlmProductSupplierInfoEntity_HI>();
					suli.add(olddata);
					group.put(headid, suli);

				}
			}

		} // for
		List<PlmProductSupplierReport> data = new ArrayList<PlmProductSupplierReport>();
		List<PlmProductSupplierReport> d2 = new ArrayList<PlmProductSupplierReport>();
		for (Map.Entry<String, List<PlmProductSupplierInfoEntity_HI>> l : group
				.entrySet()) {
			String headid = l.getKey();

			List<PlmProductSupplierInfoEntity_HI> suli = l.getValue();
			PlmProductSupplierReport pl = new PlmProductSupplierReport();
			PlmProductHeadEntity_HI head = this.getById(new Integer(headid));
			pl.setPlmCode(head.getPlmCode());
			pl.setProductName(head.getProductName());
			pl.setDepartment(head.getDepartment());
			for (PlmProductSupplierInfoEntity_HI h : suli) {
				if (h.getIsMainsupplier() == null
						|| h.getIsMainsupplier().equals("1")) {
					pl.setSupplierId(h.getSupplierId());
					pl.setSupplierName(h.getSupplierName());

				} else if (h.getIsMainsupplier().equals("0")) {

					pl.setNewsupplierId(h.getSupplierId());
					pl.setNewsupplierName(h.getSupplierName());

				}
			}
			data.add(pl);

		}

		Integer first = pageIndex * pageRows - pageRows;
		Integer end = first + pageRows;
		if (data.size() < end) {
			end = data.size();
		}
		for (int i = first; i < end; i++) {
			d2.add(data.get(i));
		}

		Pagination<PlmProductSupplierReport> pagination = new Pagination<PlmProductSupplierReport>();
		pagination.setCurIndex(Long.parseLong(pageIndex.toString()));
		pagination.setCount(uli.size());
		pagination.setPagesCount(uli.size() / pageRows);
		pagination.setPageSize(Long.parseLong(pageRows.toString()));
		pagination.setData(d2);
		return pagination;
	}

	@Override
	public JSONObject productSupplierReturn(JSONObject param)
			throws ServerException {
		Integer headid = null;
		Integer suppliercount = 0;
		JSONArray data = param.getJSONArray("supplierlist");
		for (int i = 0; i < data.size(); i++) {
			JSONObject json = data.getJSONObject(i);
			if (i == 0) {
				headid = json.getInteger("productHeadId");
			}
			if (json.containsKey("returnReson")) {
				suppliercount++;
				if (!json.getString("returnReson").equals("")) {
					json.remove("isSubmit");
					json.remove("returnStatus");
					json.put("isSubmit", "0");
					json.put("returnStatus", "2");// 驳回
					plmProductSupplierInfoDao.saveOrUpdate(json);
				}
			}
		}
		try {
			if (suppliercount > 0) {
				PlmProductHeadEntity_HI head = this.getById(headid);
				head.setOrderStatus("2"); // 改为供应商完善状态
				head.setSupplierCount(suppliercount);
				this.update(head);

				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, null);
			} else {
				throw new ServerException("驳回供应商信息需要填写,需驳回供应商 的退回原因!");
			}
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new ServerException("服务异常");
		}
		// return null;
	}

	@Override
	public JSONObject saveNextPerson(JSONObject param) throws ServerException {
		try {
			Integer headid = 0;
			String headstr = param.getString("id");
			if (headstr.contains("_")) {
				headid = new Integer(headstr.split("_")[0]);
			} else {
				headid = new Integer(headstr);
			}
			PlmProductHeadEntity_HI headobj = this.getById(headid);
			String orderstatus = headobj.getOrderStatus();
			String status = param.getString("status"); // 状态
			if (status.equals("REFUSAL")) { // 驳回
				if (headobj.getIsRmsRever() != null) {
					if (headobj.getIsRmsRever().equals("true")) {
						headobj.setRmsReverBusinesskey(headstr);
					}
				}
				headobj.setOrderStatus("10");
			} else if (status.equals("REVOKE")) {
				if (headobj.getIsRmsRever() != null) {
					if (headobj.getIsRmsRever().equals("true")) {
						headobj.setRmsReverBusinesskey(headstr);
					}
				}
				headobj.setOrderStatus("11"); // 撤回
			} else {
				String processDefinitionKey = param
						.getString("processDefinitionKey");

				if (headobj != null && orderstatus.equals("3")) {
					headobj.setOrderStatus("4");
					headobj.setAddProcessname(processDefinitionKey);
				} else if (headobj != null && orderstatus.equals("10")) {
					headobj.setOrderStatus("4");
				} else if (headobj != null && orderstatus.equals("11")) {
					headobj.setOrderStatus("4");
				} else if (headobj != null && orderstatus.equals("7")) { // rms驳回重新提交
					headobj.setOrderStatus("4");// 新增审批中
					headobj.setIsRmsRever("true");
					headobj.setRmsReverBusinesskey(null);
					headobj.setProcessInstanceid(headstr); // 保存最新的流程实例
				}

			}
			this.update(headobj);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null);
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new ServerException("服务异常");
		}

		// return null;
	}

	@Override
	public Pagination<PlmProductHeadEntity_HI_RO> findProductList2(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		String UserType = param.getString("varUserType"); // 用户类型
		StringBuffer query = new StringBuffer();
		query.append(PlmProductHeadEntity_HI_RO.QUERY_HEAD);
		query.append(" and PRODUCT.RMS_CODE IS NOT NULL ");
		String userName = param.getString("varUserName"); // 用户名
		Map<String, Object> params = new HashMap<String, Object>();
		if (param.containsKey("productName")) {
			query.append(" and upper(product.product_name) like '%"
					+ param.getString("productName") + "%' ");
		}

		SaafToolUtils.parperParam(param, "product.plm_code", "plmCode", query,
				params, "fulllike");
		SaafToolUtils.parperParam(param, "product.brandname_cn", "brandnameCn",
				query, params, "fulllike");
		SaafToolUtils.parperParam(param, "product.department", "department",
				query, params, "fulllike");
		//
		param.remove("productName");
		param.remove("plmCode");
		param.remove("brandnameCn");
		param.remove("department");
		if (param.containsKey("createdstr")) {
			String createdstr = param.getString("createdstr");
			param.remove("createdstr");
			query.append(" and ( upper(PRODUCT.createdstr) like '%"
					+ createdstr.toUpperCase()
					+ "%' or upper(PRODUCT.created_emp) like '"
					+ createdstr.toUpperCase()
					+ "%' or upper(PRODUCT.created_ename) like '"
					+ createdstr.toUpperCase() + "%')");
		}

		if (param.containsKey("orderStatus")) {
			String orderStatus = param.getString("orderStatus");
			if (orderStatus.equals("0")) {
				param.remove("orderStatus");
				query.append(" and PRODUCT.order_status!='8'");
			}

		}
		if (param.containsKey("creationDate")
				&& param.getDate("creationDate") != null) {
			Date creationDate = param.getDate("creationDate");
			SimpleDateFormat sim = new SimpleDateFormat("YYYY-MM-dd");
			param.remove("creationDate");
			query.append(" and to_char(PRODUCT.creation_date,'yyyy-mm-dd')='"
					+ sim.format(creationDate) + "'");
		}
		String deptName = param.getString("deptName");
		if (!userName.equals("ADMIN")
				&& !UserType.equals("60")
				&& !deptName.contains("WTCCN HO Supply Chain")
				&& !deptName
						.equals("WTCCN HO Trading-Promotion Planning and Trade Marketing")
				&& !deptName.equals("WTCCN HO Trading-Merchandising")) {
			query.append(" and PRODUCT.USER_DEPT='"
					+ param.getString("userDept") + "' ");
		}

		param.remove("userDept");

		SaafToolUtils.parperHbmParam(PlmProductHeadEntity_HI_RO.class, param,
				query, params);

		query.append(" order by PRODUCT.CREATION_DATE desc ");
		Pagination<PlmProductHeadEntity_HI_RO> pagination = plmProductHeadEntity_HI_RO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);
		return pagination;

	}

	@Override
	public JSONObject productPicfileReturn(JSONObject param)
			throws ServerException {
		// picfilelist
		Map<Integer, Integer> supplierli = new HashMap<Integer, Integer>();
		Integer headid = null;
		JSONArray data = param.getJSONArray("picfilelist");
		for (int i = 0; i < data.size(); i++) {
			JSONObject json = data.getJSONObject(i);
			if (i == 0) {
				headid = json.getInteger("productHeadId");
			}
			if (json.containsKey("returnReson")) {
				if (!json.getString("returnReson").equals("")) {
					Integer supplierId = json.getInteger("supplierId");
					if (!supplierli.containsKey(supplierId)) {
						supplierli.put(supplierId, supplierId);
					}
					plmProductPicfileTableDao.saveOrUpdate(json);
				}
			}
		}

		try {
			if (supplierli.size() > 0) {
				PlmProductHeadEntity_HI head = this.getById(headid);
				head.setOrderStatus("2"); // 改为供应商完善状态
				if (head.getSupplierCount() != 1) {
					head.setSupplierCount(1);
				}
				this.update(head);
				for (Map.Entry<Integer, Integer> ma : supplierli.entrySet()) { // supplier
					Integer supplierId = ma.getKey();

					JSONObject jo = new JSONObject();
					jo.put("productHeadId", headid);
					jo.put("supplierCode", supplierId);
					List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
							.findList(jo);
					if (suli.size() > 0) {
						PlmProductSupplierInfoEntity_HI supplier = suli.get(0);
						supplier.setIsSubmit("0");
						supplier.setReturnStatus("2"); // 驳回
						plmProductSupplierInfoDAO_HI.saveOrUpdate(supplier);
					}

				}

				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, null);
			} else {
				throw new ServerException("驳回图片文件,需填写驳回资质文件的退回原因!");
			}
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new ServerException("服务异常");
		}
	}

	@Override
	public JSONObject productQafileReturn(JSONObject param)
			throws ServerException {
		Map<Integer, Integer> supplierli = new HashMap<Integer, Integer>();

		Map<Integer, String> supplierli2 = new HashMap<Integer, String>();
		Integer headid = null;
		JSONArray data = param.getJSONArray("qalist");
		for (int i = 0; i < data.size(); i++) {
			JSONObject json = data.getJSONObject(i);
			if (i == 0) {
				headid = json.getInteger("productHeadId");
			}
			if (json.containsKey("returnReson")) {
				if (!json.getString("returnReson").equals("")) {
					Integer supplierId = json.getInteger("supplierId");
					if (!supplierli.containsKey(supplierId)) {
						supplierli.put(supplierId, supplierId);
					}
					plmProductQaFileDao.saveOrUpdate(json);
				}
			}
		}

		// picfile
		if (param.containsKey("picfilelist")) {
			JSONArray picfiledata = param.getJSONArray("picfilelist");
			for (int i = 0; i < picfiledata.size(); i++) {
				JSONObject json = picfiledata.getJSONObject(i);
				if (i == 0) {
					headid = json.getInteger("productHeadId");
				}
				if (json.containsKey("returnReson")) {
					if (!json.getString("returnReson").equals("")) {
						Integer supplierId = json.getInteger("supplierId");
						if (!supplierli.containsKey(supplierId)) {
							supplierli.put(supplierId, supplierId);
						}
						plmProductPicfileTableDao.saveOrUpdate(json);
					}
				}
			}
		}
		// supplist
		if (param.containsKey("supplierlist")) {
			JSONArray sudata = param.getJSONArray("supplierlist");
			for (int i = 0; i < sudata.size(); i++) {
				JSONObject json = sudata.getJSONObject(i);
				if (i == 0) {
					headid = json.getInteger("productHeadId");
				}
				if (json.containsKey("returnReson")) {
					if (!json.getString("returnReson").equals("")) {
						if (!supplierli.containsKey(json
								.getInteger("supplierCode"))) {
							supplierli.put(json.getInteger("supplierCode"),
									json.getInteger("supplierCode"));
						}
						supplierli2.put(json.getInteger("supplierCode"),
								json.getString("returnReson"));
					}
				}
			}
		}
		try {
			if (supplierli.size() > 0) {
				PlmProductHeadEntity_HI head = this.getById(headid);
				head.setOrderStatus("2"); // 改为供应商完善状态
				// if (head.getSupplierCount() != 1) {
				head.setSupplierCount(supplierli.size());
				// }
				this.update(head);
				for (Map.Entry<Integer, Integer> ma : supplierli.entrySet()) { // supplier
					Integer supplierId = ma.getKey();

					JSONObject jo = new JSONObject();
					jo.put("productHeadId", headid);
					jo.put("supplierCode", supplierId);
					List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
							.findList(jo);
					if (suli.size() > 0) {
						PlmProductSupplierInfoEntity_HI supplier = suli.get(0);
						Integer suppliercode = new Integer(
								supplier.getSupplierCode());
						if (supplierli2.containsKey(suppliercode)) {
							supplier.setReturnReson(supplierli2
									.get(new Integer(supplier.getSupplierCode())));
						}
						supplier.setIsSubmit("0");
						supplier.setReturnStatus("2");
						plmProductSupplierInfoDAO_HI.saveOrUpdate(supplier);
					}
				}

				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, null);
			} else {

				throw new ServerException("驳回资质文件,需填写驳回原因!");
			}
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new ServerException("服务异常");
		}
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (i != 0 && str.charAt(i) == '.') {
				continue;
			}
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}

		}
		return true;
	}

	@Override
	public JSONObject findProductDetail2(JSONObject param) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDate(String date) {
		/**
		 * 判断日期格式和范围
		 */
		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\\\\\/\\s]?((((0?[13578])|(1[02]))[\\\\\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\\\\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\\\\\/\\s]?((((0?[13578])|(1[02]))[\\\\\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\\\\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(date);

		boolean dateType = mat.matches();

		return dateType;
	}

	// 一阶段批量提交
	@Override
	public String SaveProductPattch(JSONObject param) {
		String result = "";
		// List<PlmProductHeadEntity_HI> lidata = new
		// ArrayList<PlmProductHeadEntity_HI>();
		JSONArray headlist = param.getJSONArray("headlist");
		for (int i = 0; i < headlist.size(); i++) {
			JSONObject headjson = headlist.getJSONObject(i);
			Integer productHeadId = headjson.getInteger("productHeadId");
			String error = "";
			PlmProductHeadEntity_HI he = this
					.getById(new Integer(productHeadId));
			if (he != null) {
				if (he.getProductName() == null
						|| he.getProductName().equals("")) {
					error += "货品名(中文),不能为空!";
				}
				if (he.getProductEname() == null
						|| he.getProductEname().equals("")) {
					error += "货品名(英文),不能为空!";
				}
				if (he.getDepartment() == null || he.getDepartment().equals("")) {
					error += "部门编码,不能为空!";
				}
				if (he.getClasses() == null || he.getClasses().equals("")) {
					error += "分类,不能为空!";
				}
				if (he.getSubClass() == null || he.getSubClass().equals("")) {
					error += "子分类,不能为空!";
				}
				if (he.getBrandnameCn() == null
						|| he.getBrandnameCn().equals("")) {
					error += "品牌名(中文),不能为空!";
				}
				if (he.getBrandnameEn() == null
						|| he.getBrandnameEn().equals("")) {
					error += "品牌名(英文),不能为空!";
				}
				if (he.getProductType() == null
						|| he.getProductType().equals("")) {
					error += "商品类型,不能为空!";
				}
				if (he.getPurchaseType() == null
						|| he.getPurchaseType().equals("")) {
					error += "采购类型,不能为空!";
				}

				if (he.getMarkerChannel() == null
						|| he.getMarkerChannel().equals("")) {
					error += "销售渠道,不能为空!";
				}
				if (he.getOtherInfo() == null || he.getOtherInfo().equals("")) {
					error += "特殊商品类型,不能为空!";
				}
				if (he.getValidDays() == null || he.getValidDays().equals("")) {
					error += "有效保质期,不能为空!";
				}
				if (he.getSxDays() == null || he.getSxDays().equals("")) {
					error += "保质期天数,不能为空!";
				}

				// 获取供应商行表
				JSONObject su = new JSONObject();
				su.put("productHeadId", he.getProductHeadId());
				List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
						.findList(su);
				if (suli.size() == 0) {
					error += "请添加供应商信息!";
				}
				int suppliercount = 0;
				for (int j = 0; j < suli.size(); j++) {
					PlmProductSupplierInfoEntity_HI info = suli.get(j);
					if (info != null) {
						if (info.getSupplierName() == null
								|| info.getSupplierName().equals("")) {
							error += "供应商信息第[" + j + "]行:供应商名称不能为空!";
						}
						if (info.getIsMainsupplier() == null
								|| info.getIsMainsupplier().equals("")) {
							error += "供应商信息第[" + j + "]行:优先供应商不能为空!";
						}
						if (info.getIsMainsupplier().equals("Y")) {
							suppliercount++;
						}
					}
				}

				if (suppliercount == 0) {
					error += "必须要有且只有一个优先供应商!";
				}

				// 验证供应商

				// 获取条码
				JSONObject barcode = new JSONObject();
				barcode.put("productHeadId", he.getProductHeadId());
				List<PlmProductBarcodeTableEntity_HI> barcodelist = plmProductBarcodeTableDao
						.findList(barcode);
				if (barcodelist.size() == 0) {
					error += "请添加条码信息!";
				}
				boolean bflag = false;
				for (int z = 0; z < barcodelist.size(); z++) {
					PlmProductBarcodeTableEntity_HI barobj = barcodelist.get(z);
					if (barobj != null) {
						if (barobj.getBarcode() == null
								|| barobj.getBarcode().equals("")) {
							error += "条形码第[" + z + "]行:条码不能为空!";
						}
						if (barobj.getIsMain().equals("Y")) {
							bflag = true;
						}
					}

				}
				if (bflag == false) {
					error += "必须要有且只有一个主条码!";
				}

				if (!error.equals("")) {
					String results = "商品[" + he.getPlmCode() + "],提交失败,失败原因:"
							+ error + "\r\n";
					result += results;
				} else // 修改提交状态
				{
					result += "商品[" + he.getPlmCode() + "],提交成功!" + "\r\n";
					he.setOrderStatus("2");
					he.setSupplierCount(suli.size());
					for (PlmProductSupplierInfoEntity_HI h : suli) {
						h.setReturnStatus("0");
						h.setIsSubmit("0");

						plmProductSupplierInfoDAO_HI.update(h);
					}

					this.update(he);
				}
			}
		}
		return result;
	}

	// 批量下载
	@Override
	public @ResponseBody
	String uploadProductSupplierPattch(JSONObject param,
			HttpServletResponse response) throws ServerException {

		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			Sheet headsheet = wb.createSheet("供应商信息");
			for (int i = 0; i <= 12; i++) {
				headsheet.setColumnWidth(i, 256 * 30);
			}
			Row row = headsheet.createRow(0);
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont xfont = wb.createFont();
			xfont.setFontName("仿宋_GB2312");
			xfont.setBold(true);
			xfont.setFontHeightInPoints((short) 12);
			row.setRowStyle(style);
			XSSFCell cell = (XSSFCell) row.createCell(0);
			cell.setCellValue("PLM ID(系统填入不可修改)");
			XSSFCell cell1 = (XSSFCell) row.createCell(1);
			cell1.setCellValue("商品名中文(系统填入不可修改)");
			XSSFCell cell2 = (XSSFCell) row.createCell(2);
			cell2.setCellValue("供应商ID(系统填入不可修改)");
			XSSFCell cell3 = (XSSFCell) row.createCell(3);
			cell3.setCellValue("成本币种");
			XSSFCell cell4 = (XSSFCell) row.createCell(4);
			cell4.setCellValue("成本价");
			XSSFCell cell5 = (XSSFCell) row.createCell(5);
			cell5.setCellValue("外箱长");
			XSSFCell cell6 = (XSSFCell) row.createCell(6);
			cell6.setCellValue("外箱宽");
			XSSFCell cell7 = (XSSFCell) row.createCell(7);
			cell7.setCellValue("外箱高");
			XSSFCell cell8 = (XSSFCell) row.createCell(8);
			cell8.setCellValue("外箱重");
			XSSFCell cell9 = (XSSFCell) row.createCell(9);
			// cell9.setCellValue("内包装规格");
			// XSSFCell cell10 = (XSSFCell) row.createCell(10);
			cell9.setCellValue("包装规格(个-内箱)");
			XSSFCell cell11 = (XSSFCell) row.createCell(10);
			cell11.setCellValue("商品深(必填)");
			XSSFCell cell12 = (XSSFCell) row.createCell(11);
			cell12.setCellValue("商品宽(必填)");
			XSSFCell cell13 = (XSSFCell) row.createCell(12);
			cell13.setCellValue("商品高(必填)");

			XSSFCell cell14 = (XSSFCell) row.createCell(13);
			cell14.setCellValue("产地(必填)");

			Sheet sheet2 = wb.createSheet("药品信息");
			for (int i = 0; i <= 35; i++) {
				sheet2.setColumnWidth(i, 256 * 30);
			}

			Row row1 = sheet2.createRow(0);
			row1.setRowStyle(style);
			XSSFCell row1cell = (XSSFCell) row1.createCell(0);
			row1cell.setCellValue("PLM ID(系统填入不可修改)");
			XSSFCell row2cell = (XSSFCell) row1.createCell(1);
			row2cell.setCellValue("商品名中文(系统填入不可修改)");
			XSSFCell row4cell = (XSSFCell) row1.createCell(2);
			row4cell.setCellValue("医保药品(必填)");
			XSSFCell row5cell = (XSSFCell) row1.createCell(3);
			row5cell.setCellValue("通用名称(必填)");
			XSSFCell row6cell = (XSSFCell) row1.createCell(4);
			row6cell.setCellValue("药品代码(必填)");
			XSSFCell row7cell = (XSSFCell) row1.createCell(5);
			row7cell.setCellValue("规格(必填)");
			XSSFCell row8cell = (XSSFCell) row1.createCell(6);
			row8cell.setCellValue("剂型(必填)");
			XSSFCell row9cell = (XSSFCell) row1.createCell(7);
			row9cell.setCellValue("单位(必填)");
			XSSFCell row10cell = (XSSFCell) row1.createCell(8);
			row10cell.setCellValue("生产厂家(必填)");
			XSSFCell row11cell = (XSSFCell) row1.createCell(9);
			row11cell.setCellValue("批准文号(必填)");
			XSSFCell row12cell = (XSSFCell) row1.createCell(10);
			row12cell.setCellValue("储藏条件(必填)");
			XSSFCell row13cell = (XSSFCell) row1.createCell(11);
			row13cell.setCellValue("批文有效期(必填)");
			XSSFCell row15cell = (XSSFCell) row1.createCell(12);
			row15cell.setCellValue("批文预警天数(必填)");
			XSSFCell row16cell = (XSSFCell) row1.createCell(13);
			row16cell.setCellValue("保质期(必填)");
			XSSFCell row17cell = (XSSFCell) row1.createCell(14);
			row17cell.setCellValue("陈列周期(必填)");
			XSSFCell row18cell = (XSSFCell) row1.createCell(15);
			row18cell.setCellValue("经营类别(必填)");
			XSSFCell row19cell = (XSSFCell) row1.createCell(16);
			row19cell.setCellValue("药品类别(必填)");

			// 陈列预警天数
			XSSFCell row21cell = (XSSFCell) row1.createCell(17);
			row21cell.setCellValue("陈列预警天数(必填)");
			XSSFCell row22cell = (XSSFCell) row1.createCell(18);
			row22cell.setCellValue("有效期预警天数(必填)");
			XSSFCell row23cell = (XSSFCell) row1.createCell(19);
			row23cell.setCellValue("是否属批号管理(必填)");
			XSSFCell row24cell = (XSSFCell) row1.createCell(20);
			row24cell.setCellValue("处方药类别(必填)");
			XSSFCell row25cell = (XSSFCell) row1.createCell(21);
			row25cell.setCellValue("是否处方药品(必填)");
			XSSFCell row26cell = (XSSFCell) row1.createCell(22);
			row26cell.setCellValue("GSP管理级别(必填)");
			XSSFCell row27cell = (XSSFCell) row1.createCell(23);
			row27cell.setCellValue("药监统一编号(必填)");
			XSSFCell row28cell = (XSSFCell) row1.createCell(24);
			row28cell.setCellValue("药品本位码(必填)");
			XSSFCell row29cell = (XSSFCell) row1.createCell(25);
			row29cell.setCellValue("所属经营范围(必填)");

			XSSFCell row30cell = (XSSFCell) row1.createCell(26);
			row30cell.setCellValue("质量标准(必填)");
			XSSFCell row39cell = (XSSFCell) row1.createCell(27);
			row39cell.setCellValue("批准证明文件及其附件");

			XSSFCell row31cell = (XSSFCell) row1.createCell(28);
			row31cell.setCellValue("包装规格(必填)");
			XSSFCell row32cell = (XSSFCell) row1.createCell(29);
			row32cell.setCellValue("药监包装规格(必填)");
			XSSFCell row33cell = (XSSFCell) row1.createCell(30);
			row33cell.setCellValue("是否重点养护(必填)");
			XSSFCell row34cell = (XSSFCell) row1.createCell(31);
			row34cell.setCellValue("是否拆零(必填)");
			XSSFCell row35cell = (XSSFCell) row1.createCell(32);
			row35cell.setCellValue("是否冷藏药品(必填)");
			XSSFCell row36cell = (XSSFCell) row1.createCell(33);
			row36cell.setCellValue("是否含麻黄碱(必填)");
			XSSFCell row37cell = (XSSFCell) row1.createCell(34);
			row37cell.setCellValue("产地（中药饮片）");
			XSSFCell row38cell = (XSSFCell) row1.createCell(35);
			row38cell.setCellValue("二级分类（必填）");

			Sheet sheet3 = wb.createSheet("医疗器械");
			for (int i = 0; i <= 10; i++) {
				sheet3.setColumnWidth(i, 256 * 30);
			}
			Row row2 = sheet3.createRow(0);
			row2.setRowStyle(style);
			XSSFCell shrowcell = (XSSFCell) row2.createCell(0);
			shrowcell.setCellValue("PLM ID(系统填入不可修改)");
			XSSFCell shrowcell1 = (XSSFCell) row2.createCell(1);
			shrowcell1.setCellValue("商品名（中文）(系统填入不可修改)");
			XSSFCell shrowcell3 = (XSSFCell) row2.createCell(2);
			shrowcell3.setCellValue("名称(必填)");
			XSSFCell shrowcell4 = (XSSFCell) row2.createCell(3);
			shrowcell4.setCellValue("医疗器械类别(必填)");
			XSSFCell shrowcell5 = (XSSFCell) row2.createCell(4);
			shrowcell5.setCellValue("医疗器械目录类别(必填)");
			XSSFCell shrowcell6 = (XSSFCell) row2.createCell(5);
			shrowcell6.setCellValue("注册证号或者备案凭证编号(必填)");
			XSSFCell shrowcell7 = (XSSFCell) row2.createCell(6);
			shrowcell7.setCellValue("规格型号(必填)");
			XSSFCell shrowcell8 = (XSSFCell) row2.createCell(7);
			shrowcell8.setCellValue("储运条件(必填)");
			XSSFCell shrowcell9 = (XSSFCell) row2.createCell(8);
			shrowcell9.setCellValue("是否灭菌批号管理(必填)");
			XSSFCell shrowcell10 = (XSSFCell) row2.createCell(9);
			shrowcell10.setCellValue("产品有效期(必填)");

			Sheet sheet4 = wb.createSheet("填写说明"); // 填写说明
			Row row4 = sheet4.createRow(0);
			row4.setHeight((short) 400);
			XSSFCell c4 = (XSSFCell) row4.createCell(0);
			c4.setCellValue("详情说明  所有填入按照excel文本格式填写,所有填写范围为单项方式。"
					+ " 药品商品需要填写药品信息,医疗器械商品需要填写医疗器械信息，为系统自动带出。[供应商信息]:1.成本币种,填写1或2 (1.CNY,2.USD)。");
			Row row41 = sheet4.createRow(1);
			row41.setHeight((short) 400);
			XSSFCell c5 = (XSSFCell) row41.createCell(0);
			c5.setCellValue("2.(成本币种-商品高) 只能填写数字。");

			Row row56 = sheet4.createRow(2);
			row56.setHeight((short) 400);
			XSSFCell c20 = (XSSFCell) row56.createCell(0);
			c20.setCellValue("[药品信息]。1.医保药品,是否批号管理,是否重点养护,是否拆零,是否冷藏药品,是否含麻黄碱. 填写1或2 (1.是,2.否)");

			Row row57 = sheet4.createRow(3);
			row57.setHeight((short) 400);
			XSSFCell c21 = (XSSFCell) row57.createCell(0);
			c21.setCellValue("2.单位,填写范围1-6(1.支,2.瓶,3.盒,4.袋,5.罐,6.包)");

			Row row58 = sheet4.createRow(4);
			row58.setHeight((short) 400);
			XSSFCell c22 = (XSSFCell) row58.createCell(0);
			c22.setCellValue("3.存储条件,经营类别. 填写范围1-2(1.常温,2.阴凉)");

			Row row59 = sheet4.createRow(5);
			row59.setHeight((short) 400);
			XSSFCell c23 = (XSSFCell) row59.createCell(0);
			c23.setCellValue("4.处方药类别,填写范围1-2(1.单轨,2.双规)");

			Row row60 = sheet4.createRow(6);
			row60.setHeight((short) 400);
			XSSFCell c24 = (XSSFCell) row60.createCell(0);
			c24.setCellValue("5.批准证明文件,填写范围1-2(1.0,2.1)");

			Row row61 = sheet4.createRow(7);
			row61.setHeight((short) 400);
			XSSFCell c25 = (XSSFCell) row61.createCell(0);
			c25.setCellValue("6.所属经营范围,填写范围1-9(1.处方药,2.甲类非处方药,3.乙类非处方药,4.中药饮品,5.中成药,6.化学药制剂,7.抗生素药剂,8.生化药品,9.生物制品)");

			Row row62 = sheet4.createRow(8);
			row62.setHeight((short) 400);
			XSSFCell c26 = (XSSFCell) row62.createCell(0);
			c26.setCellValue("7.二级分类,填写范围101-129(101.感冒类,102.清热类,103.呼吸系统类,104.眼科类,105.耳科类,106.鼻腔类,107.口腔咽喉类,108.胃肠道类,109.肝胆类,110.妇科类,111.泌尿生殖系统类,"
					+ "112.肛肠类,113.皮肤类,114.抗过敏类,115.解热镇痛风湿类,116.跌打损伤骨症类,117.滋补养生类,118.维生素矿物质类,119.儿科类,120.安神助眠类,121.心脑血管类,122.糖尿病类,123.内分泌类,"
					+ "124.抗菌消炎类,125.中枢神经系统类,126.激素类,127.注射剂类,128.抗肿瘤类,129.其他类)");

			Row row63 = sheet4.createRow(9);
			row63.setHeight((short) 400);
			XSSFCell c27 = (XSSFCell) row63.createCell(0);
			c27.setCellValue("8.批文有效期,填写为时间格式(YYYY/MM/dd)");

			Row row67 = sheet4.createRow(10);
			row67.setHeight((short) 400);
			XSSFCell c31 = (XSSFCell) row67.createCell(0);
			c31.setCellValue("9.剂型,填写范围 01-106  具体参考 数据字典模块,快码值 [DOSAGE_FORM]");

			Row row68 = sheet4.createRow(11);
			row68.setHeight((short) 400);
			XSSFCell c32 = (XSSFCell) row68.createCell(0);
			c32.setCellValue("10.Gps管理级别,填写范围 1-6  具体参考 数据字典模块,快码值 [GSP_MANAGEMENT_LEVEL]");

			Row row69 = sheet4.createRow(12);
			row69.setHeight((short) 400);
			XSSFCell c33 = (XSSFCell) row69.createCell(0);
			c33.setCellValue("11.保质期，批文预警天数，效期预警天数. 只能填写数字");

			Row row70 = sheet4.createRow(13);
			row70.setHeight((short) 400);
			XSSFCell c34 = (XSSFCell) row70.createCell(0);
			c34.setCellValue("12.药品类别,填写范围 01-0106 (01.药品,0101.中成药,0102.化学药制剂,0103.抗生素制剂,0104.中药饮片,0105.生化制剂,0106.生物制品（除疫苗）) ");

			Row row64 = sheet4.createRow(14);
			row64.setHeight((short) 400);
			XSSFCell c28 = (XSSFCell) row64.createCell(0);
			c28.setCellValue("[医疗器械] 1.医疗器械类别,填写范围1-3 (1.一类,2.二类,3.三类)");

			Row row65 = sheet4.createRow(15);
			row65.setHeight((short) 400);
			XSSFCell c29 = (XSSFCell) row65.createCell(0);
			c29.setCellValue("2.储运条件,填写范围1-3 (1.常温,2.阴凉,3.冷藏),3.是否灭菌批号管理 填写范围1-2(1.是,2.否)");

			Row row66 = sheet4.createRow(16);
			row66.setHeight((short) 400);
			XSSFCell c30 = (XSSFCell) row66.createCell(0);
			c30.setCellValue("3.产品有效期,填写日期格式 (YYYY/MM/dd)");

			// cell.set
			JSONArray headlist = param.getJSONArray("headlist");
			int supplierow = 1;
			int drugrow = 1;
			int mediarow = 1;
			for (int i = 0; i < headlist.size(); i++) {
				JSONObject head = headlist.getJSONObject(i);
				Integer productHeadId = head.getInteger("productHeadId");
				PlmProductHeadEntity_HI headobj = this.getById(productHeadId);
				JSONObject supplierlist = new JSONObject();
				supplierlist.put("productHeadId", productHeadId);
				List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
						.findList(supplierlist);
				for (int j = 0; j < suli.size(); j++) {
					PlmProductSupplierInfoEntity_HI suinfo = suli.get(j);
					Row headrow1 = headsheet.createRow(supplierow);
					String supplierid = suinfo.getSupplierCode();
					String cost = suinfo.getCurrencyCost(); // 成本币种
					String price = suinfo.getPrice(); // 成本价
					Float boxlength = suinfo.getBoxLength(); // 外箱长
					Float boxBreadth = suinfo.getBoxBreadth(); // 外箱宽
					Float boxLength = suinfo.getBoxHeight();// 外箱高
					Float boxWeight = suinfo.getBoxWeight();// 外箱重
					Integer packageSpe = suinfo.getPackageSpe();
					Float productLength = suinfo.getProductLength();
					Float productBreadth = suinfo.getProductBreadth();
					Float productHeight = suinfo.getProductHeight();
					String place = suinfo.getPlace(); // 产地

					XSSFCell suppliercell = (XSSFCell) headrow1.createCell(0);
					suppliercell.setCellValue(headobj.getPlmCode());

					XSSFCell suppliercell1 = (XSSFCell) headrow1.createCell(1);
					suppliercell1.setCellValue(headobj.getProductName());

					XSSFCell suppliercell2 = (XSSFCell) headrow1.createCell(2);
					suppliercell2.setCellValue(supplierid);

					XSSFCell suppliercell3 = (XSSFCell) headrow1.createCell(3);
					suppliercell3.setCellValue(cost);

					XSSFCell suppliercell4 = (XSSFCell) headrow1.createCell(4);
					suppliercell4.setCellValue(price);

					XSSFCell suppliercell5 = (XSSFCell) headrow1.createCell(5);
					if (boxlength == null) {
						suppliercell5.setCellValue("");
					} else {
						suppliercell5.setCellValue(boxlength);
					}

					XSSFCell suppliercell6 = (XSSFCell) headrow1.createCell(6);
					if (boxBreadth == null) {
						suppliercell6.setCellValue("");
					} else {
						suppliercell6.setCellValue(boxBreadth);
					}

					XSSFCell suppliercell7 = (XSSFCell) headrow1.createCell(7);
					if (boxLength == null) {
						suppliercell7.setCellValue("");
					} else {
						suppliercell7.setCellValue(boxLength);
					}

					XSSFCell suppliercell8 = (XSSFCell) headrow1.createCell(8);
					if (boxWeight == null) {
						suppliercell8.setCellValue("");
					} else {
						suppliercell8.setCellValue(boxWeight);
					}

					XSSFCell suppliercell10 = (XSSFCell) headrow1.createCell(9);
					if (packageSpe == null) {
						suppliercell10.setCellValue("");
					} else {
						suppliercell10.setCellValue(packageSpe);
					}

					XSSFCell suppliercell11 = (XSSFCell) headrow1
							.createCell(10);
					if (productLength == null) {
						suppliercell11.setCellValue("");
					} else {
						suppliercell11.setCellValue(productLength);
					}

					XSSFCell suppliercell12 = (XSSFCell) headrow1
							.createCell(11);
					if (productBreadth == null) {
						suppliercell12.setCellValue("");
					} else {
						suppliercell12.setCellValue(productBreadth);
					}

					XSSFCell suppliercell13 = (XSSFCell) headrow1
							.createCell(12);
					if (productHeight == null) {
						suppliercell13.setCellValue("");
					} else {
						suppliercell13.setCellValue(productHeight);
					}
					XSSFCell suppliercell14 = (XSSFCell) headrow1
							.createCell(13);
					if (place == null) {
						suppliercell14.setCellValue("");
					} else {
						suppliercell14.setCellValue(place);
					}

					supplierow++; // 自增
				}// supplierlist

				// 判断是药品还是医疗器械
				String otherInfo = headobj.getOtherInfo();
				if (otherInfo.equals("1")) // 药品
				{
					JSONObject drugparam = new JSONObject();
					drugparam.put("productHeadId", headobj.getProductHeadId());
					List<PlmProductDrugEntity_HI> li = plmProductDrug
							.findList(drugparam);
					if (li.size() == 0) {
						Row sheet2row2 = sheet2.createRow(drugrow);
						XSSFCell sheet2cell1 = (XSSFCell) sheet2row2
								.createCell(0);
						sheet2cell1.setCellValue(headobj.getPlmCode());
						XSSFCell sheet2cell2 = (XSSFCell) sheet2row2
								.createCell(1);
						sheet2cell2.setCellValue(headobj.getProductName());
						XSSFCell sheet2cell3 = (XSSFCell) sheet2row2
								.createCell(2);
						XSSFCell sheet2cell4 = (XSSFCell) sheet2row2
								.createCell(3);
						XSSFCell sheet2cell5 = (XSSFCell) sheet2row2
								.createCell(4);
						sheet2cell2.setCellValue(headobj.getProductName());
						XSSFCell sheet2cell6 = (XSSFCell) sheet2row2
								.createCell(5);
						XSSFCell sheet2cell7 = (XSSFCell) sheet2row2
								.createCell(6);
						XSSFCell sheet2cell8 = (XSSFCell) sheet2row2
								.createCell(7);
						sheet2cell2.setCellValue(headobj.getProductName());
						XSSFCell sheet2cell9 = (XSSFCell) sheet2row2
								.createCell(8);
						XSSFCell sheet2cell10 = (XSSFCell) sheet2row2
								.createCell(9);
						XSSFCell sheet2cell11 = (XSSFCell) sheet2row2
								.createCell(10);
						XSSFCell sheet2cell12 = (XSSFCell) sheet2row2
								.createCell(11);
						XSSFCell sheet2cell13 = (XSSFCell) sheet2row2
								.createCell(12);
						XSSFCell sheet2cell14 = (XSSFCell) sheet2row2
								.createCell(13);
						XSSFCell sheet2cell15 = (XSSFCell) sheet2row2
								.createCell(14);
						XSSFCell sheet2cell16 = (XSSFCell) sheet2row2
								.createCell(15);
						XSSFCell sheet2cell17 = (XSSFCell) sheet2row2
								.createCell(16);
						XSSFCell sheet2cell18 = (XSSFCell) sheet2row2
								.createCell(17);
						XSSFCell sheet2cell19 = (XSSFCell) sheet2row2
								.createCell(18);
						XSSFCell sheet2cell20 = (XSSFCell) sheet2row2
								.createCell(19);
						XSSFCell sheet2cell21 = (XSSFCell) sheet2row2
								.createCell(20);
						XSSFCell sheet2cell22 = (XSSFCell) sheet2row2
								.createCell(21);
						XSSFCell sheet2cell23 = (XSSFCell) sheet2row2
								.createCell(22);
						XSSFCell sheet2cell24 = (XSSFCell) sheet2row2
								.createCell(23);
						XSSFCell sheet2cell25 = (XSSFCell) sheet2row2
								.createCell(24);
						XSSFCell sheet2cell26 = (XSSFCell) sheet2row2
								.createCell(25);
						XSSFCell sheet2cell27 = (XSSFCell) sheet2row2
								.createCell(26);
						XSSFCell sheet2cell28 = (XSSFCell) sheet2row2
								.createCell(27);
						XSSFCell sheet2cell29 = (XSSFCell) sheet2row2
								.createCell(28);
						XSSFCell sheet2cell30 = (XSSFCell) sheet2row2
								.createCell(29);
						XSSFCell sheet2cell31 = (XSSFCell) sheet2row2
								.createCell(30);
						XSSFCell sheet2cell32 = (XSSFCell) sheet2row2
								.createCell(31);
						XSSFCell sheet2cell33 = (XSSFCell) sheet2row2
								.createCell(32);
						XSSFCell sheet2cell34 = (XSSFCell) sheet2row2
								.createCell(33);
						XSSFCell sheet2cell35 = (XSSFCell) sheet2row2
								.createCell(34);
						XSSFCell sheet2cell36 = (XSSFCell) sheet2row2
								.createCell(35);
						drugrow++;

					} else {
						PlmProductDrugEntity_HI hi = li.get(0);
						Row sheet2row2 = sheet2.createRow(drugrow);
						XSSFCell sheet2cell1 = (XSSFCell) sheet2row2
								.createCell(0);
						sheet2cell1.setCellValue(headobj.getPlmCode());

						XSSFCell sheet2cell2 = (XSSFCell) sheet2row2
								.createCell(1);
						sheet2cell2.setCellValue(headobj.getProductName());

						XSSFCell sheet2cell3 = (XSSFCell) sheet2row2
								.createCell(2);
						if (hi.getDrugIns() == null) {
							sheet2cell3.setCellValue("");
						} else {
							sheet2cell3.setCellValue(hi.getDrugIns()); // 医保药品
						}

						XSSFCell sheet2cell4 = (XSSFCell) sheet2row2
								.createCell(3);
						if (hi.getCommonName() == null) {
							sheet2cell4.setCellValue("");
						} else {
							sheet2cell4.setCellValue(hi.getCommonName()); // 通用名称
						}
						XSSFCell sheet2cell5 = (XSSFCell) sheet2row2
								.createCell(4);
						if (hi.getDrugCode() == null) {
							sheet2cell5.setCellValue("");
						} else {
							sheet2cell5.setCellValue(hi.getDrugCode()); // 药品代码
						}

						XSSFCell sheet2cell7 = (XSSFCell) sheet2row2
								.createCell(5);
						if (hi.getDrugForm() == null) {
							sheet2cell7.setCellValue("");
						} else {
							sheet2cell7.setCellValue(hi.getDrugForm()); // 剂型
						}

						XSSFCell sheet2cell38 = (XSSFCell) sheet2row2
								.createCell(6);
						if (hi.getStandard() == null) {
							sheet2cell38.setCellValue("");
						} else {
							sheet2cell38.setCellValue(hi.getStandard()); // 规格
						}

						XSSFCell sheet2cell8 = (XSSFCell) sheet2row2
								.createCell(7);
						if (hi.getUnit() == null) {
							sheet2cell8.setCellValue("");
						} else {
							sheet2cell8.setCellValue(hi.getUnit()); // 单位
						}
						XSSFCell sheet2cell9 = (XSSFCell) sheet2row2
								.createCell(8);
						if (hi.getProducer() == null) {
							sheet2cell9.setCellValue("");
						} else {
							sheet2cell9.setCellValue(hi.getProducer()); // 生产商
						}
						XSSFCell sheet2cell10 = (XSSFCell) sheet2row2
								.createCell(9);
						if (hi.getSymbol() == null) {
							sheet2cell10.setCellValue("");
						} else {
							sheet2cell10.setCellValue(hi.getSymbol()); // 批准文号
						}
						XSSFCell sheet2cell11 = (XSSFCell) sheet2row2
								.createCell(10);
						if (hi.getStoreCondition() == null) {
							sheet2cell11.setCellValue("");
						} else {
							sheet2cell11.setCellValue(hi.getStoreCondition()); // 存储条件
						}
						XSSFCell sheet2cell12 = (XSSFCell) sheet2row2
								.createCell(11);
						if (hi.getSymbolDate() == null) {
							sheet2cell12.setCellValue("");
						} else {
							sheet2cell12.setCellValue(hi.getSymbolDate()); // 批文有效期
						}
						XSSFCell sheet2cell13 = (XSSFCell) sheet2row2
								.createCell(12);
						if (hi.getSymbolDays() == null) {
							sheet2cell13.setCellValue("");
						} else {
							sheet2cell13.setCellValue(hi.getSymbolDays()); // 批文有效天数
						}

						XSSFCell sheet2cell14 = (XSSFCell) sheet2row2
								.createCell(13);
						if (hi.getProtectPeriod() == null) {
							sheet2cell14.setCellValue(""); // 保质期
						} else {
							sheet2cell14.setCellValue(hi.getProtectPeriod()); // 保质期
						}

						XSSFCell sheet2cell15 = (XSSFCell) sheet2row2
								.createCell(14);
						if (hi.getDisplayCycle() == null) {
							sheet2cell15.setCellValue("");
						} else {
							sheet2cell15.setCellValue(hi.getDisplayCycle()); // 陈列周期
						}

						XSSFCell sheet2cell16 = (XSSFCell) sheet2row2
								.createCell(15);
						if (hi.getBusinessCategory() == null) {
							sheet2cell15.setCellValue("");
						} else {
							sheet2cell16.setCellValue(hi.getBusinessCategory()); // 经营类别
						}

						XSSFCell sheet2cell18 = (XSSFCell) sheet2row2
								.createCell(16);
						if (hi.getCategoryName() == null) {
							sheet2cell18.setCellValue("");
						} else {
							sheet2cell18.setCellValue(hi.getCategoryName()); // 药品类别
						}

						XSSFCell sheet2cell19 = (XSSFCell) sheet2row2
								.createCell(17);
						if (hi.getDisplayDays() == null) {
							sheet2cell19.setCellValue("");
						} else {
							sheet2cell19.setCellValue(hi.getDisplayDays()); // 陈列预警天数
						}
						XSSFCell sheet2cell20 = (XSSFCell) sheet2row2
								.createCell(18);
						if (hi.getSxDays() == null) {
							sheet2cell20.setCellValue("");
						} else {
							sheet2cell20.setCellValue(hi.getSxDays()); // 效期预警天数
						}
						XSSFCell sheet2cell21 = (XSSFCell) sheet2row2
								.createCell(19);
						if (hi.getIsBatchnumber() == null) {
							sheet2cell21.setCellValue("");
						} else {
							sheet2cell21.setCellValue(hi.getIsBatchnumber()); // 是否属批号管理
						}
						XSSFCell sheet2cell22 = (XSSFCell) sheet2row2
								.createCell(20);
						if (hi.getPresType() == null) {
							sheet2cell22.setCellValue("");
						} else {
							sheet2cell22.setCellValue(hi.getPresType()); // 处方药类别
						}

						XSSFCell sheet2cell23 = (XSSFCell) sheet2row2
								.createCell(21);
						if (hi.getIsRx() == null) {
							sheet2cell23.setCellValue("");
						} else {
							sheet2cell23.setCellValue(hi.getIsRx()); // 是否处方药品
						}

						XSSFCell sheet2cell24 = (XSSFCell) sheet2row2
								.createCell(22);
						if (hi.getGspManage() == null) {
							sheet2cell24.setCellValue("");
						} else {
							sheet2cell24.setCellValue(hi.getGspManage()); // GPS管理级别
						}
						XSSFCell sheet2cell25 = (XSSFCell) sheet2row2
								.createCell(23);
						if (hi.getDrugStandcode() == null) {
							sheet2cell25.setCellValue("");
						} else {
							sheet2cell25.setCellValue(hi.getDrugStandcode()); // 药监统一编号
						}
						XSSFCell sheet2cell26 = (XSSFCell) sheet2row2
								.createCell(24);
						if (hi.getDrugStandard() == null) {
							sheet2cell26.setCellValue("");
						} else {
							sheet2cell26.setCellValue(hi.getDrugStandard()); // 药品本位码
						}

						XSSFCell sheet2cell27 = (XSSFCell) sheet2row2
								.createCell(25);
						if (hi.getRang() == null) {
							sheet2cell27.setCellValue("");
						} else {
							sheet2cell27.setCellValue(hi.getRang()); // 所属经营范围
						}
						XSSFCell sheet2cell28 = (XSSFCell) sheet2row2
								.createCell(26);
						if (hi.getQaStandard() == null) {
							sheet2cell28.setCellValue("");
						} else {
							sheet2cell28.setCellValue(hi.getQaStandard()); // 质量标准
						}
						XSSFCell sheet2cell29 = (XSSFCell) sheet2row2
								.createCell(27);
						if (hi.getFileInfo() == null) {
							sheet2cell29.setCellValue("");
						} else {
							sheet2cell29.setCellValue(hi.getFileInfo()); // 批准证明文件及其附件
						}
						XSSFCell sheet2cell30 = (XSSFCell) sheet2row2
								.createCell(28);
						if (hi.getBackageUnit() == null) {
							sheet2cell30.setCellValue("");
						} else {
							sheet2cell30.setCellValue(hi.getBackageUnit()); // 包装规格
						}
						XSSFCell sheet2cell31 = (XSSFCell) sheet2row2
								.createCell(29);
						if (hi.getDrugUnit() == null) {
							sheet2cell31.setCellValue("");
						} else {
							sheet2cell31.setCellValue(hi.getDrugUnit()); // 药监包装规格
						}
						XSSFCell sheet2cell32 = (XSSFCell) sheet2row2
								.createCell(30);
						if (hi.getIsProtect() == null) {
							sheet2cell32.setCellValue("");
						} else {
							sheet2cell32.setCellValue(hi.getIsProtect()); // 是否重点养护
						}
						XSSFCell sheet2cell33 = (XSSFCell) sheet2row2
								.createCell(31);
						if (hi.getIsPull() == null) {
							sheet2cell33.setCellValue("");
						} else {
							sheet2cell33.setCellValue(hi.getIsPull()); // 是否拆零
						}
						XSSFCell sheet2cell34 = (XSSFCell) sheet2row2
								.createCell(32);
						if (hi.getIsCold() == null) {
							sheet2cell34.setCellValue("");
						} else {
							sheet2cell34.setCellValue(hi.getIsCold()); // 是否冷藏药品
						}
						XSSFCell sheet2cell35 = (XSSFCell) sheet2row2
								.createCell(33);
						if (hi.getIsEphedrine() == null) {
							sheet2cell35.setCellValue("");
						} else {
							sheet2cell35.setCellValue(hi.getIsEphedrine()); // 是否含麻黄碱
						}
						XSSFCell sheet2cell36 = (XSSFCell) sheet2row2
								.createCell(34);
						if (hi.getDrugPlace() == null) {
							sheet2cell36.setCellValue("");
						} else {
							sheet2cell36.setCellValue(hi.getDrugPlace()); // 产地
						}

						XSSFCell sheet2cell37 = (XSSFCell) sheet2row2
								.createCell(35);
						if (hi.getDrugPlace() == null) {
							sheet2cell37.setCellValue("");
						} else {
							sheet2cell37.setCellValue(hi.getSecondClass()); // 二级分类
						}

						drugrow++;
					}

				} else if (otherInfo.equals("2")) // 医疗器械
				{
					JSONObject medicalparam = new JSONObject();
					medicalparam.put("productHeadId", productHeadId);

					List<PlmProductMedicalinfoEntity_HI> medicallist = plmMedicainfo
							.findList(medicalparam);
					if (medicallist.size() == 0) {
						Row sheet3row2 = sheet3.createRow(mediarow);
						XSSFCell sheet3cell1 = (XSSFCell) sheet3row2
								.createCell(0);
						sheet3cell1.setCellValue(headobj.getPlmCode());
						XSSFCell sheet3cell2 = (XSSFCell) sheet3row2
								.createCell(1);
						sheet3cell2.setCellValue(headobj.getProductName());
						XSSFCell sheet3cell3 = (XSSFCell) sheet3row2
								.createCell(2);
						XSSFCell sheet3cell4 = (XSSFCell) sheet3row2
								.createCell(3);
						XSSFCell sheet3cell5 = (XSSFCell) sheet3row2
								.createCell(4);
						XSSFCell sheet3cell6 = (XSSFCell) sheet3row2
								.createCell(5);
						XSSFCell sheet3cell7 = (XSSFCell) sheet3row2
								.createCell(6);
						XSSFCell sheet3cell8 = (XSSFCell) sheet3row2
								.createCell(7);
						XSSFCell sheet3cell9 = (XSSFCell) sheet3row2
								.createCell(8);
						XSSFCell sheet3cell10 = (XSSFCell) sheet3row2
								.createCell(9);

						mediarow++;
					} else {
						PlmProductMedicalinfoEntity_HI medical = medicallist
								.get(0);
						Row sheet3row2 = sheet3.createRow(mediarow);
						sheet3row2.setRowStyle(style);
						XSSFCell sheet3cell1 = (XSSFCell) sheet3row2
								.createCell(0);
						sheet3cell1.setCellValue(headobj.getPlmCode());
						XSSFCell sheet3cell2 = (XSSFCell) sheet3row2
								.createCell(1);
						sheet3cell2.setCellValue(headobj.getProductName());
						XSSFCell sheet3cell3 = (XSSFCell) sheet3row2
								.createCell(2);
						if (medical.getName() == null) {
							sheet3cell3.setCellValue("");
						} else {
							sheet3cell3.setCellValue(medical.getName());
						}

						XSSFCell sheet3cell4 = (XSSFCell) sheet3row2
								.createCell(3);
						if (medical.getMedicalType() == null) {
							sheet3cell4.setCellValue("");
						} else {
							sheet3cell4.setCellValue(medical.getMedicalType());
						}
						XSSFCell sheet3cell5 = (XSSFCell) sheet3row2
								.createCell(4);
						if (medical.getCatalogCategory() == null) {
							sheet3cell5.setCellValue("");
						} else {
							sheet3cell5.setCellValue(medical
									.getCatalogCategory());
						}
						XSSFCell sheet3cell6 = (XSSFCell) sheet3row2
								.createCell(5);
						if (medical.getRegisterNo() == null) {
							sheet3cell6.setCellValue("");
						} else {
							sheet3cell6.setCellValue(medical.getRegisterNo());
						}

						XSSFCell sheet3cell7 = (XSSFCell) sheet3row2
								.createCell(6);
						if (medical.getModels() == null) {
							sheet3cell7.setCellValue("");
						} else {
							sheet3cell7.setCellValue(medical.getModels());
						}
						XSSFCell sheet3cell8 = (XSSFCell) sheet3row2
								.createCell(7);
						if (medical.getTransCondition() == null) {
							sheet3cell8.setCellValue("");
						} else {
							sheet3cell8.setCellValue(medical
									.getTransCondition());
						}
						XSSFCell sheet3cell9 = (XSSFCell) sheet3row2
								.createCell(8);
						if (medical.getIfBatchnumber() == null) {
							sheet3cell9.setCellValue("");
						} else {
							sheet3cell9
									.setCellValue(medical.getIfBatchnumber());
						}
						XSSFCell sheet3cell10 = (XSSFCell) sheet3row2
								.createCell(9);
						if (medical.getProductSxdate() == null) {
							sheet3cell10.setCellValue("");
						} else {
							sheet3cell10.setCellValue(medical
									.getProductSxdate());
						}
						mediarow++;
					}
				}

			}// headlist

			String filename = "product.xlsx";
			String realPath = this.getClass().getClassLoader().getResource("")
					.getFile();

			String[] result = realPath.split("/");
			String append = "";
			for (int i = 0; i < result.length; i++) {
				if (i <= 3) {
					String s = result[i];
					append += s + "/";
				}
			}

			String resultrealPath = append += filename;

			java.io.File file = new java.io.File(resultrealPath);
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			file.createNewFile();

			FileOutputStream output = new FileOutputStream(file);
			wb.write(output);

			FileInputStream input = new FileInputStream(file);
			ResultFileEntity fileentity = fastdfsServer.fileUpload(input,
					filename);// 上传至文件服务器
			System.out.println(fileentity.getFilePath() + ".................");
			output.close();
			input.close();
			wb.close();
			return fileentity.getAccessPath();

		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public JSONObject SaveSupplierProductByExcel(JSONObject param)
			throws ServerException {
		String filepath = param.getString("filepath");
		File file = this.getNetUrl(filepath);
		Map<String, String> suppliermap = new HashMap<String, String>();
		Map<String, String> drugmap = new HashMap<String, String>(); // 药品验重
		Map<String, String> mediamap = new HashMap<String, String>(); // 医疗器械验重
		try {
			InputStream is = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet head = wb.getSheetAt(0); // 头信息
			int rownum = head.getLastRowNum(); // 总行数
			int headcolumnCount = head.getRow(0).getPhysicalNumberOfCells();
			if (headcolumnCount != 14) {
				throw new ServerException("[供应商信息]数据格式错误:请按照模板格式填写!");
			}
			for (int i = 1; i <= rownum; i++) {
				XSSFRow curent = head.getRow(i);
				if (curent == null) {
					continue;
				}
				XSSFCell code = curent.getCell(0); // PLM_code
				XSSFCell productname = curent.getCell(1); // 商品名中文
				XSSFCell supplierId = curent.getCell(2); // 供应商id
				XSSFCell cost = curent.getCell(3); // 成本币种
				XSSFCell price = curent.getCell(4); // 成本价
				XSSFCell boxlength = curent.getCell(5); // 外箱长
				XSSFCell boxwidth = curent.getCell(6); // 外箱宽
				XSSFCell boxheight = curent.getCell(7); // 外箱高
				XSSFCell boxweight = curent.getCell(8); // 外箱重
				// XSSFCell Innerpack = curent.getCell(9); // 内包装规格
				XSSFCell pack = curent.getCell(9); // 外包装规格
				XSSFCell productlength = curent.getCell(10); // 商品长
				XSSFCell productwidth = curent.getCell(11); // 商品宽
				XSSFCell productheight = curent.getCell(12); // 商品高
				XSSFCell place = curent.getCell(13); // 产地

				Integer headid = null; // 当前行的头id
				String plmcode = this.getStringCellValueXss(code);
				if (plmcode.equals("")) {
					throw new ServerException("[供应商信息]第" + (i + 1)
							+ "行,PLM_ID,不能为空!");
				} else {
					// 验证供应商id的正确性
					JSONObject plmparam = new JSONObject();
					plmparam.put("plmCode", plmcode);
					List<PlmProductHeadEntity_HI> headli = this
							.findList(plmparam);
					if (headli.size() == 0) {
						throw new ServerException("[供应商信息]第" + (i + 1)
								+ "行,PLM_ID不存在!");
					} else {
						headid = headli.get(0).getProductHeadId();
					}
				}

				// 供应商id
				String supplierIdvalue = this.getStringCellValueXss(supplierId);
				if (supplierIdvalue.equals("")) {
					throw new ServerException("[供应商信息]第" + (i + 1)
							+ "行,供应商ID不能为空!");
				} else {
					JSONObject suinfo = new JSONObject();
					suinfo.put("supplierCode", supplierIdvalue);
					List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
							.findList(suinfo);
					if (suli.size() == 0) {
						throw new ServerException("[供应商信息]第" + (i + 1)
								+ "行,供应商ID错误!");
					}
				}

				PlmProductSupplierInfoEntity_HI supplierobj = new PlmProductSupplierInfoEntity_HI();
				// purchase
				if (headid != null) {
					JSONObject suobj = new JSONObject();
					suobj.put("productHeadId", headid);
					suobj.put("supplierId", supplierIdvalue);
					List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
							.findList(suobj);
					if (suli.size() > 0) {
						supplierobj = suli.get(0);
					}
					// 校验供应商id
					String key = plmcode + "_" + supplierIdvalue;
					if (suppliermap.containsKey(key)) {
						throw new ServerException("[供应商信息]第" + (i + 1)
								+ "行,同一商品存在相同供应商id!");
					} else {
						suppliermap.put(key, key);
					}

					PlmProductHeadEntity_HI obj = this.getById(headid);
					String purchasetype = obj.getPurchaseType();
					if (purchasetype.equals("1")) { // 需要校验成本价这些字段
						String costvalue = this.getStringCellValueXss(cost);
						if (!costvalue.equals("")) {
							if (!costvalue.equals("1")
									&& !costvalue.equals("2")) {
								throw new ServerException("[供应商信息]第" + (i + 1)
										+ "行,成本币种数据错误,请填写1或2!");
							}
						}
						// supplierobj.setp
						supplierobj.setCurrencyCost(costvalue);
						// 成本价
						String pricevalue = this.getStringCellValueXss(price);
						if (!pricevalue.equals("")) {
							boolean flag = this.isNumeric(pricevalue);
							if (flag == false) {
								throw new ServerException("[供应商信息]第" + (i + 1)
										+ "行,成本价请填写数字!");
							}
						}
						supplierobj.setPrice(pricevalue);
						// 外箱长
						String boxlengthvalue = this
								.getStringCellValueXss(boxlength);
						if (!boxlengthvalue.equals("")) {
							boolean lengthflag = this.isNumeric(boxlengthvalue);
							if (lengthflag == false) {
								throw new ServerException("[供应商信息]第" + (i + 1)
										+ "行,外箱长请填写数字!");
							}
						}
						if (!boxlengthvalue.equals("")) {
							supplierobj.setBoxLength(new Float(boxlengthvalue));
						}
						// 外箱宽
						String boxwidthvalue = this
								.getStringCellValueXss(boxwidth);
						if (!boxwidthvalue.equals("")) {
							boolean widthflag = this.isNumeric(boxwidthvalue);
							if (widthflag == false) {
								throw new ServerException("[供应商信息]第" + (i + 1)
										+ "行,外箱宽请填写数字!");
							}
						}
						if (!boxwidthvalue.equals("")) {
							supplierobj.setBoxBreadth(new Float(boxwidthvalue));
						}
						// 外箱高
						String boxheightvalue = this
								.getStringCellValueXss(boxheight);
						if (!boxheightvalue.equals("")) {
							boolean boxheightflag = this
									.isNumeric(boxheightvalue);
							if (boxheightflag == false) {
								throw new ServerException("[供应商信息]第" + (i + 1)
										+ "行,外箱高请填写数字!");
							}
						}
						if (!boxheightvalue.equals("")) {
							supplierobj.setBoxHeight(new Float(boxheightvalue));
						}
						// 外箱重
						String boxweightvalue = this
								.getStringCellValueXss(boxweight);
						if (!boxweightvalue.equals("")) {
							boolean boxweightflag = this
									.isNumeric(boxweightvalue);
							if (boxweightflag == false) {
								throw new ServerException("[供应商信息]第" + (i + 1)
										+ "行,外箱重请填写数字!");
							}
						}
						if (!boxweightvalue.equals("")) {
							supplierobj.setBoxWeight(new Float(boxweightvalue));
						}

						supplierobj.setInnerpackageSpe(1);

						// 外包装规格
						String packvalue = this.getStringCellValueXss(pack);
						if (!packvalue.equals("")) {
							boolean packflag = this.isNumeric(packvalue);
							if (packflag == false) {
								throw new ServerException("[供应商信息]第" + (i + 1)
										+ "行,包装规格请填写数字!");
							}
						}
						if (!packvalue.equals("")) {
							supplierobj.setPackageSpe(new Integer(packvalue));
						}

					}// purchase
					String productlengthvalue = this
							.getStringCellValueXss(productlength); // 深
					if (productlengthvalue.equals("")) {
						throw new ServerException("[供应商信息]第" + (i + 1)
								+ "行,商品深不能为空!");
					} else {
						if (this.isNumeric(productlengthvalue) == false) {
							throw new ServerException("[供应商信息]第" + (i + 1)
									+ "行,商品深请填写数字!");
						}
					}
					if (!productlengthvalue.equals("")) {
						supplierobj.setProductLength(new Float(
								productlengthvalue));
					}

					// 商品宽
					String productwidthvalue = this
							.getStringCellValueXss(productwidth);
					if (productwidthvalue.equals("")) {
						throw new ServerException("[供应商信息]第" + (i + 1)
								+ "行,商品宽不能为空!");
					} else {
						if (this.isNumeric(productwidthvalue) == false) {
							throw new ServerException("[供应商信息]第" + (i + 1)
									+ "行,商品宽请填写数字!");
						}
					}
					if (!productwidthvalue.equals("")) {
						supplierobj.setProductBreadth(new Float(
								productwidthvalue));
					}
					// 商品高
					String productheightvalue = this
							.getStringCellValueXss(productheight);
					if (productheightvalue.equals("")) {
						throw new ServerException("[供应商信息]第" + (i + 1)
								+ "行,商品高不能为空!");
					} else {
						if (this.isNumeric(productwidthvalue) == false) {
							throw new ServerException("[供应商信息]第" + (i + 1)
									+ "行,商品高请填写数字!");
						}

					}
					if (!productheightvalue.equals("")) {
						supplierobj.setProductHeight(new Float(
								productheightvalue));
					}
					// 产地
					String placevalue = this.getStringCellValueXss(place);
					if (placevalue.equals("")) {
						throw new ServerException("[供应商信息]第" + (i + 1)
								+ "行,产地不能为空!");
					}
					supplierobj.setPlace(placevalue);
				}
				supplierobj.setOperatorUserId(param.getInteger("varUserId"));
				plmProductSupplierInfoDAO_HI.saveOrUpdate(supplierobj);
			} // headsheet

			XSSFSheet sh1 = wb.getSheetAt(1);
			Integer sh1rownum = sh1.getLastRowNum();
			int sh1columnCount = sh1.getRow(0).getPhysicalNumberOfCells();
			if (sh1columnCount != 36) {
				throw new ServerException("[药品信息],数据格式错误!");
			}
			for (int i = 1; i <= sh1rownum; i++) {
				XSSFRow curent = sh1.getRow(i);
				if (curent == null) {
					continue;
				}
				XSSFCell code = curent.getCell(0); // PLM_code
				XSSFCell productname = curent.getCell(1); // 商品名称
				XSSFCell yb = curent.getCell(2); // 医保药品
				XSSFCell commonname = curent.getCell(3); // 通用名称
				XSSFCell drugcode = curent.getCell(4); // 药品代码
				XSSFCell gge = curent.getCell(5); // 规格

				XSSFCell jx = curent.getCell(6); // 剂型
				XSSFCell unit = curent.getCell(7); // 单位
				XSSFCell sccj = curent.getCell(8); // 生产厂家
				XSSFCell pzwh = curent.getCell(9); // 批准文号
				XSSFCell cctj = curent.getCell(10); // 储藏条件
				XSSFCell pwyxq = curent.getCell(11); // 批文有效期
				XSSFCell pwyjts = curent.getCell(12); // 批文预警天数
				XSSFCell bzq = curent.getCell(13); // 保质期
				XSSFCell clzq = curent.getCell(14); // 成列周期
				XSSFCell jylb = curent.getCell(15); // 经营类别
				// XSSFCell lbdm = curent.getCell(15); // 类别代码
				XSSFCell lbmc = curent.getCell(16); // 类别名称
				XSSFCell clyjts = curent.getCell(17); // 成列预警天数
				XSSFCell xqyjts = curent.getCell(18); // 效期预警天数
				XSSFCell sfphgl = curent.getCell(19); // 是否批号管理
				XSSFCell cfylb = curent.getCell(20); // 处方药类别
				XSSFCell sfcfylb = curent.getCell(21); // 是否处方药品
				XSSFCell gsplevel = curent.getCell(22); // GSP管理级别
				XSSFCell standcode = curent.getCell(23);// 药监统一编号
				XSSFCell bwm = curent.getCell(24); // 药品本位码
				XSSFCell ssjyfw = curent.getCell(25); // 所属经营范围
				XSSFCell zlbz = curent.getCell(26); // 质量标准
				XSSFCell pzzmwj = curent.getCell(27);// 批准证明文件及其附件
				XSSFCell bzgg = curent.getCell(28);// 包装规格
				XSSFCell yjbzgg = curent.getCell(29); // 药监包装规格
				XSSFCell sfzdyh = curent.getCell(30); // 是否重点养护
				XSSFCell sflc = curent.getCell(31); // 是否零拆
				XSSFCell sflcyp = curent.getCell(32); // 是否冷藏药品
				XSSFCell sfhmhj = curent.getCell(33); // 是否含麻黄碱
				XSSFCell cd = curent.getCell(34); // 产地
				XSSFCell second = curent.getCell(35); // 二级分类
				Integer headid = null;

				String plmcode = this.getStringCellValueXss(code); // 商品编号
				if (plmcode.equals("")) {
					throw new ServerException("[药品信息]第" + (i + 1)
							+ "行,PLM_ID,不能为空!");
				} else {
					// 验证供应商id的正确性
					JSONObject plmparam = new JSONObject();
					plmparam.put("plmCode", plmcode);
					List<PlmProductHeadEntity_HI> headli = this
							.findList(plmparam);
					if (headli.size() == 0) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,PLM_ID不存在!");
					} else {
						headid = headli.get(0).getProductHeadId();
					}
				}

				PlmProductHeadEntity_HI headobj = this.getById(headid);
				if (headobj.getOtherInfo().equals("0")
						|| headobj.getOtherInfo().equals("2")) // 普通商品或医疗器械的跳过
				{
					continue;
				}
				PlmProductDrugEntity_HI druginfo = new PlmProductDrugEntity_HI();

				if (headid != null) {
					druginfo.setProductHeadId(headid);
					JSONObject drugjson = new JSONObject();
					drugjson.put("productHeadId", headid);
					List<PlmProductDrugEntity_HI> lidrug = plmProductDrug
							.findList(drugjson);
					if (lidrug.size() > 0) {
						druginfo = lidrug.get(0);
					}

					if (drugmap.containsKey(plmcode)) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,出现重复的PLM_ID!");
					} else {
						drugmap.put(plmcode, plmcode);
					}

					String ybvalue = this.getStringCellValueXss(yb); // 医保药品
					if (ybvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,医保药品不能为空!");
					} else {
						if (!ybvalue.equals("1") && !ybvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,医保药品数据错误,请填写1或2!");
						}
					}
					druginfo.setDrugIns(ybvalue);
					String commonnamevalue = this
							.getStringCellValueXss(commonname); // 通用名称
					if (commonnamevalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,通用名称不能为空!");
					}
					druginfo.setCommonName(commonnamevalue);
					// PlmProductHeadEntity_HI headobj = this.getById(headid);
					String drugcodevalue = this.getStringCellValueXss(drugcode); // 药品代码
					if (drugcodevalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,药品代码不能为空!");
					}
					druginfo.setDrugCode(drugcodevalue);
					String jxvalue = this.getStringCellValueXss(jx); // 剂型
					if (jxvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,剂型不能为空!");
					} else {
						boolean jxflag = this.isNumeric(jxvalue);
						if (jxflag == false) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,剂型数据错误!");
						} else {
							Integer jxin = new Integer(jxvalue);
							if (jxin < 1 || jxin > 106) {
								throw new ServerException("[药品信息]第" + (i + 1)
										+ "行,剂型数据错误!");
							}
						}
					}

					druginfo.setDrugForm(jxvalue);

					String ggvalue = this.getStringCellValueXss(gge);
					if (ggvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,规格不能为空!");
					}
					druginfo.setStandard(ggvalue);
					String dwvalue = this.getStringCellValueXss(unit); // 单位
					if (dwvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,单位不能为空!");
					}

					druginfo.setUnit(dwvalue);
					String sccjvalue = this.getStringCellValueXss(sccj);// 生产厂家
					if (sccjvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,生产厂家不能为空!");
					}
					druginfo.setProducer(sccjvalue);
					String pzwhvalue = this.getStringCellValueXss(pzwh);// 批准文号
					if (pzwhvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,批准文号不能为空!");
					}

					druginfo.setSymbol(pzwhvalue);
					String cctjvalue = this.getStringCellValueXss(cctj);// 存储条件
					if (cctjvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,存储条件不能为空!");
					} else {
						if (!cctjvalue.equals("1") && cctjvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,存储条件数据错误,请填写1或2!");
						}
					}

					druginfo.setStoreCondition(cctjvalue);
					String pwyxqvalue = this.getStringCellValueXss(pwyxq);
					if (pwyxqvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,批文有效期不能为空!");
					} else {
						boolean isdate = this.isDate(pwyxqvalue);
						if (isdate == false) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,批文有效期格式错误,请使用 yyyy/MM/dd格式!");
						}
					}
					SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd");
					druginfo.setSymbolDate(simple.parse(pwyxqvalue));

					String pwyjtsvalue = this.getStringCellValueXss(pwyjts); // 批文预警天数
					if (pwyjtsvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,批文预警天数不能为空!");
					} else {
						if (this.isNumeric(pwyjtsvalue) == false) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,批文预警天数只能填写数字!");
						}
					}
					if (!pwyjtsvalue.equals("")) {
						druginfo.setSymbolDays(new Integer(pwyjtsvalue));
					}
					String bzqvalue = this.getStringCellValueXss(bzq);
					if (bzqvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,保质期不能为空!");
					}
					druginfo.setProtectPeriod(bzqvalue);
					String clzqvalue = this.getStringCellValueXss(clzq);
					if (clzqvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,陈列周期不能为空!");
					}
					druginfo.setDisplayCycle(clzqvalue);
					String jylbvalue = this.getStringCellValueXss(jylb);
					if (jylbvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,经营类别不能为空!");
					} else {
						if (!jylbvalue.equals("1") && !jylbvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,经营类别数据错误,请填写1或2!");
						}
					}

					druginfo.setBusinessCategory(jylbvalue);

					String lbmcvalue = this.getStringCellValueXss(lbmc);
					if (lbmcvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,药品类别不能为空!");
					} else {
						if (!lbmcvalue.equals("01")
								&& !lbmcvalue.equals("0101")
								&& !lbmcvalue.equals("0102")
								&& !lbmcvalue.equals("0103")
								&& !lbmcvalue.equals("0104")
								&& !lbmcvalue.equals("0105")
								&& !lbmcvalue.equals("0106")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,药品类别数据错误!");
						}
					}
					druginfo.setCategoryName(lbmcvalue);
					String clyjtsvalue = this.getStringCellValueXss(clyjts);
					if (clyjtsvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,陈列预警天数不能为空!");
					} else {
						boolean flag = this.isNumeric(clyjtsvalue);
						if (flag == false) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,陈列预警天数只能填写数字!");
						}
					}
					if (!clyjtsvalue.equals("")) {
						druginfo.setDisplayDays(new Integer(clyjtsvalue));
					}
					String xqyjtsvalue = this.getStringCellValueXss(xqyjts);
					if (xqyjtsvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,效期预警天数不能为空!");
					} else {
						boolean flag = this.isNumeric(xqyjtsvalue);
						if (flag == false) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,效期预警天数只能填写数字!");
						}
					}
					druginfo.setSxDays(new Integer(xqyjtsvalue));
					String sfphglvalue = this.getStringCellValueXss(sfphgl);
					if (sfphglvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,是否批号管理不能为空!");
					} else {
						if (!sfphglvalue.equals("1")
								&& !sfphglvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,是否批号管理数据错误,请填写1或2!");
						}
					}
					druginfo.setIsBatchnumber(sfphglvalue);
					String cfylbvalue = this.getStringCellValueXss(cfylb);
					if (cfylbvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,处方药类别不能为空!");
					} else {
						if (!cfylbvalue.equals("1") && !cfylbvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,处方药类别数据错误,请填写1或2!");
						}
					}
					druginfo.setPresType(cfylbvalue);
					String sfcfylbvalue = this.getStringCellValueXss(sfcfylb);
					if (sfcfylbvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,是否处方药品不能为空!");
					} else {
						if (!sfcfylbvalue.equals("1")
								&& !sfcfylbvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,是否处方药品数据错误,请填写1或2!");
						}
					}
					druginfo.setIsRx(sfcfylbvalue);
					// gps

					String gsplevelvalue = this.getStringCellValueXss(gsplevel);
					if (gsplevelvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,GSP管理级别不能为空!");
					} else {
						boolean gspflag = this.isNumeric(gsplevelvalue);
						if (gspflag == false) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,GSP管理级别数据错误,请填写1-6!");
						}
						Integer gspin = new Integer(gsplevelvalue);
						if (gspin < 1 || gspin > 6) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,GSP管理级别数据错误,请填写1-6!");
						}
					}

					druginfo.setGspManage(gsplevelvalue);
					String standcodevalue = this
							.getStringCellValueXss(standcode);
					if (standcodevalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,药监统一编号不能为空!");
					}
					druginfo.setDrugStandcode(standcodevalue);

					String bwmvalue = this.getStringCellValueXss(bwm);
					if (bwmvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,药品本位码不能为空!");
					}
					druginfo.setDrugStandard(bwmvalue);
					// 所属经营范围
					String ssjyfwvalue = this.getStringCellValueXss(ssjyfw);
					if (ssjyfwvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,所属经营范围不能为空!");
					} else {
						if (ssjyfwvalue.length() > 1) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,所属经营范围数据格式错误,请填写1-9!");
						} else {
							if (this.isNumeric(ssjyfwvalue) == false) {
								throw new ServerException("[药品信息]第" + (i + 1)
										+ "行,所属经营范围数据格式错误,请填写1-9!");
							}
						}
					}
					druginfo.setRang(ssjyfwvalue);
					//

					String zlbzvalue = this.getStringCellValueXss(zlbz);
					if (zlbzvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,质量标准不能为空!");
					}
					druginfo.setQaStandard(zlbzvalue);
					String pzzmwjvalue = this.getStringCellValueXss(pzzmwj);
					if (pzzmwjvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,批准证明文件及其附件不能为空!");
					} else {
						if (!pzzmwjvalue.equals("1")
								&& !pzzmwjvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,批准证明文件及其附件数据错误,请填写1或2!");
						}
					}
					druginfo.setFileInfo(pzzmwjvalue);
					String bzggvalue = this.getStringCellValueXss(bzgg);
					if (bzggvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,包装规格不能为空!");
					}
					druginfo.setBackageUnit(bzggvalue);

					String yjbzggvalue = this.getStringCellValueXss(yjbzgg);
					if (yjbzggvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,药监包装规格不能为空!");
					}
					druginfo.setDrugUnit(yjbzggvalue);
					String sfzdyhvalue = this.getStringCellValueXss(sfzdyh);
					if (sfzdyhvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,是否重点养护不能为空!");
					} else {
						if (!sfzdyhvalue.equals("1")
								&& !sfzdyhvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,是否重点养护数据错误,请填写1或2!");
						}
					}
					druginfo.setIsProtect(sfzdyhvalue);

					String sflcvalue = this.getStringCellValueXss(sflc);
					if (sflcvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,是否零拆不能为空!");
					} else {
						if (!sflcvalue.equals("1") && !sflcvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,是否零拆数据错误,请填写1或2!");
						}
					}

					druginfo.setIsPull(sflcvalue);
					String sflcypvalue = this.getStringCellValueXss(sflcyp);
					if (sflcypvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,是否冷藏药品不能为空!");
					} else {
						if (!sflcypvalue.equals("1")
								&& !sflcypvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,是否冷藏药品数据错误,请填写1或2!");
						}
					}
					druginfo.setIsCold(sflcypvalue);
					String sfhmhjvalue = this.getStringCellValueXss(sfhmhj);
					if (sfhmhjvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,是否含麻黄碱不能为空!");
					} else {
						if (!sfhmhjvalue.equals("1")
								&& !sfhmhjvalue.equals("2")) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,是否含麻黄碱数据错误,请填写1或2!");
						}
					}
					druginfo.setIsEphedrine(sfhmhjvalue);
					String cdvalue = this.getStringCellValueXss(cd);
					if (cdvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,产地不能为空!");
					}
					druginfo.setDrugPlace(cdvalue);

					String secondvalue = this.getStringCellValueXss(second);
					if (secondvalue.equals("")) {
						throw new ServerException("[药品信息]第" + (i + 1)
								+ "行,二级分类不能为空!");
					} else {
						boolean f = this.isNumeric(secondvalue);
						if (f == false) {
							throw new ServerException("[药品信息]第" + (i + 1)
									+ "行,二级分类数据错误!");
						} else {
							Integer s = new Integer(secondvalue);
							if (s < 101 || s > 129) {
								throw new ServerException("[药品信息]第" + (i + 1)
										+ "行,二级分类数据错误!");
							}
						}
					}

					druginfo.setOperatorUserId(param.getInteger("varUserId"));
				}
				PlmProductDrugDAO_HI.saveOrUpdate(druginfo); // 保存该条药品信息
			}// sh1eet
				// 医疗器械
			XSSFSheet sh2 = wb.getSheetAt(2);
			Integer sh2rownum = sh2.getLastRowNum();
			int sh2columnCount = sh2.getRow(0).getPhysicalNumberOfCells();
			if (sh2columnCount != 10) {
				throw new ServerException("[医疗器械],数据格式错误!");
			}

			for (int i = 1; i <= sh2rownum; i++) {
				XSSFRow curent = sh2.getRow(i);
				if (curent == null) {
					continue;
				}
				Integer headid = null;
				XSSFCell code = curent.getCell(0); // PLM_code
				XSSFCell productname = curent.getCell(1); // 商品名
				XSSFCell name = curent.getCell(2); // 名称
				XSSFCell ylqxlb = curent.getCell(3); // 医疗器械类别
				XSSFCell ylqxmllb = curent.getCell(4); // 医疗器械目录类别
				XSSFCell registerno = curent.getCell(5); // 注册证号或者备案凭证编号
				XSSFCell model = curent.getCell(6); // 规格型号
				XSSFCell transcondition = curent.getCell(7); // 储运条件
				XSSFCell mjgl = curent.getCell(8); // 是否灭菌批号管理
				XSSFCell cpyxq = curent.getCell(9); // 产品有效期

				String plmcode = this.getStringCellValueXss(code);
				if (plmcode.equals("")) {
					continue;
				} else {
					// 验证供应商id的正确性
					JSONObject plmparam = new JSONObject();
					plmparam.put("plmCode", plmcode);
					List<PlmProductHeadEntity_HI> headli = this
							.findList(plmparam);
					if (headli.size() == 0) {
						throw new ServerException("[医疗器械]第" + (i + 1)
								+ "行,PLM_ID不存在!");
					} else {
						headid = headli.get(0).getProductHeadId();
					}

				}
				if (mediamap.containsKey(plmcode)) {
					throw new ServerException("[医疗器械]第" + (i + 1)
							+ "行,出现重复的PLM_ID!");
				} else {
					mediamap.put(plmcode, plmcode);
				}

				PlmProductMedicalinfoEntity_HI medical = new PlmProductMedicalinfoEntity_HI();
				if (headid != null) {
					PlmProductHeadEntity_HI headentity = this.getById(headid);
					if (headentity.getOtherInfo().equals("0")
							|| headentity.getOtherInfo().equals("1")) { // 普通商品
						continue;
					}
					medical.setProductHeadId(headid);
					JSONObject medi = new JSONObject();
					medi.put("productHeadId", headid);
					List<PlmProductMedicalinfoEntity_HI> melist = plmMedicainfo
							.findList(medi);
					if (melist.size() > 0) {
						medical = melist.get(0);
					}

					String namevalue = this.getStringCellValueXss(name);
					if (namevalue.equals("")) {
						throw new ServerException("[医疗器械]第" + (i + 1)
								+ "行,名称不能为空!");
					}
					medical.setName(namevalue);
					String ylqxlbvalue = this.getStringCellValueXss(ylqxlb);
					if (ylqxlbvalue.equals("")) {
						throw new ServerException("[医疗器械]第" + (i + 1)
								+ "行,医疗器械类别不能为空!");
					} else {
						if (!ylqxlbvalue.equals("1")
								&& !ylqxlbvalue.equals("2")
								&& !ylqxlbvalue.equals("3")) {
							throw new ServerException("[医疗器械]第" + (i + 1)
									+ "行,医疗器械类别数据错误,请填写 1,2或3!");
						}
					}
					medical.setMedicalType(ylqxlbvalue);
					String ylqxmllbvalue = this.getStringCellValueXss(ylqxmllb);
					if (ylqxmllbvalue.equals("")) {
						throw new ServerException("[医疗器械]第" + (i + 1)
								+ "行,医疗器械目录类别不能为空!");
					}
					medical.setCatalogCategory(ylqxmllbvalue);
					String registernovalue = this
							.getStringCellValueXss(registerno);
					if (registernovalue.equals("")) {
						throw new ServerException("[医疗器械]第" + (i + 1)
								+ "行,注册证号不能为空!");
					}
					medical.setRegisterNo(registernovalue);
					String transconditionvalue = this
							.getStringCellValueXss(transcondition);
					if (transconditionvalue.equals("")) {
						throw new ServerException("[医疗器械]第" + (i + 1)
								+ "行,储运条件不能为空!");
					} else {
						if (!transconditionvalue.equals("1")
								&& !transconditionvalue.equals("2")
								&& !transconditionvalue.equals("3")) {
							throw new ServerException("[医疗器械]第" + (i + 1)
									+ "行,储运条件数据错误,请填写1-3!");
						}
					}

					medical.setTransCondition(transconditionvalue);
					String modelvalue = this.getStringCellValueXss(model);
					if (modelvalue.equals("")) {
						throw new ServerException("[医疗器械]第" + (i + 1)
								+ "行,规格型号不能为空!");
					}
					medical.setModels(modelvalue);
					String mjglvalue = this.getStringCellValueXss(mjgl);
					if (mjglvalue.equals("")) {
						throw new ServerException("[医疗器械]第" + (i + 1)
								+ "行,是否灭菌批号管理不能为空!");
					} else {
						if (!mjglvalue.equals("1") && !mjglvalue.equals("2")) {
							throw new ServerException("[医疗器械]第" + (i + 1)
									+ "行,是否灭菌批号管理数据错误,请填写1或2!");
						}
					}
					medical.setIfBatchnumber(mjglvalue);
					String cpyxqvalue = this.getStringCellValueXss(cpyxq);
					if (cpyxqvalue.equals("")) {
						throw new ServerException("[医疗器械]第" + (i + 1)
								+ "行,产品有效期不能为空!");
					} else {
						boolean isdate = this.isDate(cpyxqvalue);
						if (isdate == false) {
							throw new ServerException("[医疗器械]第" + (i + 1)
									+ "行,产品有效期格式错误,请使用 yyyy/MM/dd格式!");
						}
					}
					SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd");
					medical.setProductSxdate(simple.parse(cpyxqvalue));

				}
				medical.setOperatorUserId(param.getInteger("varUserId"));
				PlmProductMedicalinfoDAO_HI.saveOrUpdate(medical);
			}// for

		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
		return null;
	}

	@Override
	public JSONObject ConnectSupplierProductByFile(JSONObject param)
			throws ServerException {
		JSONArray headlist = param.getJSONArray("headlist");
		JSONArray piclist = param.getJSONArray("picfilelist");
		JSONArray qalist = param.getJSONArray("qalist");

		try {

			// 关联图片文件
			for (int i = 0; i < headlist.size(); i++) {
				JSONObject jo = headlist.getJSONObject(i);
				Integer headid = jo.getInteger("productHeadId");
				JSONObject jw = new JSONObject();
				jw.put("productHeadId", headid);
				PlmProductHeadEntity_HI heado = this.getById(headid);
				List<PlmProductQaFileEntity_HI> qa = plmProductQaFileDao
						.findList(jw);
				List<PlmProductPicfileTableEntity_HI> pic = plmProductPicfileTableDao
						.findList(jw);
				for (PlmProductQaFileEntity_HI qafile : qa) { // 先清除原来的数据
					Integer qaid = qafile.getQaId();
					plmProductQaFileDao.deleteById(qaid);// 循环删除
				}
				for (PlmProductPicfileTableEntity_HI picfile : pic) {
					Integer id = picfile.getPicId();
					plmProductPicfileTableDao.deleteById(id);
				}
				// 循环供应商

				// plmProductPicfileTableDao
				List<PlmProductSupplierInfoEntity_HI> pinfo = plmProductSupplierInfoDao
						.findList(jw);
				for (PlmProductSupplierInfoEntity_HI p : pinfo) {
					String supplierid = p.getSupplierId();

					for (int j = 0; j < piclist.size(); j++) {
						JSONObject picobj = piclist.getJSONObject(j);
						picobj.put("productHeadId", headid);
						picobj.put("supplierId", supplierid);
						picobj.put("operatorUserId",
								param.getInteger("operatorUserId"));
						plmProductPicfileTableDao.saveOrUpdate(picobj);
					}

				}
			}
			// 关联资质文件
			// JSONArray qalist = param.getJSONArray("qalist");
			Map<String, List<JSONObject>> sumap = new HashMap<String, List<JSONObject>>();
			// 先将资质文件根据名称分组
			for (int i = 0; i < qalist.size(); i++) {
				JSONObject qj = qalist.getJSONObject(i);
				// String supplierName = qj.getString("supplierName");
				qj.remove("supplierName");
				qj.put("operatorUserId", param.getInteger("operatorUserId"));
				plmProductQaFileDao.saveOrUpdate(qj);
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, param.toString());
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}

	}

	// 二阶段批量提交
	@Override
	public String SaveProductSupplierSubmit(JSONObject param) {
		String result = "";
		JSONArray headlist = param.getJSONArray("headlist");
		for (int i = 0; i < headlist.size(); i++) {
			JSONObject headjson = headlist.getJSONObject(i);
			Integer productHeadId = headjson.getInteger("productHeadId");
			String error = "";
			PlmProductHeadEntity_HI he = this
					.getById(new Integer(productHeadId));

			// 获取供应商行表
			JSONObject su = new JSONObject();
			su.put("productHeadId", he.getProductHeadId());
			List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
					.findList(su);
			if (suli.size() == 0) {
				error += "请添加供应商信息!";
			}
			for (int j = 0; j < su.size(); j++) {
				PlmProductSupplierInfoEntity_HI info = suli.get(j);
				if (info != null) {

					if (info.getProductLength() == null
							|| info.getProductLength().equals("")) {
						error += "供应商信息第[" + (j + 1) + "]行:商品深不能为空!";
					}

					if (info.getProductBreadth() == null
							|| info.getProductBreadth().equals("")) {
						error += "供应商信息第[" + (j + 1) + "]行:商品宽不能为空!";
					}

					if (info.getProductHeight() == null
							|| info.getProductHeight().equals("")) {
						error += "供应商信息第[" + (j + 1) + "]行:商品高不能为空!";
					}

				}
			}

			// 资质文件
			List<PlmProductQaFileEntity_HI> qalist = plmProductQaFileDao
					.findList(su);
			if (qalist.size() == 0) {
				error += "请添加资质文件!";
			} else {
				for (int z = 0; z < qalist.size(); z++) {
					PlmProductQaFileEntity_HI qa = qalist.get(z);
					if (qa.getQaFiletype() == null
							|| qa.getQaFiletype().equals("")) {
						error += "资质文件第[" + (z + 1) + "]行:资质文件类型不能为空!";
					}
					if (qa.getQaUrl() == null || qa.getQaUrl().equals("")) {
						error += "资质文件第[" + (z + 1) + "]行:资质文件不能为空!";
					}
					if (qa.getQaCode() == null || qa.getQaCode().equals("")) {
						error += "资质文件第[" + (z + 1) + "]行:证书编号不能为空!";
					}
					if (qa.getDatecurent() == null) {
						error += "资质文件第[" + (z + 1) + "]行:日期不能为空!";
					}
				}
			}

			// 图片文件
			List<PlmProductPicfileTableEntity_HI> picli = plmProductPicfileTableDao
					.findList(su);

			if (picli.size() == 0) {
				error += "请添加图片数据!";
			} else {
				for (int p = 0; p < picli.size(); p++) {
					PlmProductPicfileTableEntity_HI picf = picli.get(p);
					if (picf.getPicUrl() == null || picf.getPicUrl().equals("")) {
						error += "图片文件第[" + (p + 1) + "]行:请选择图片!";
					}
					if (picf.getSupplierId() == null
							|| picf.getSupplierId().equals("")) {
						error += "图片文件第[" + (p + 1) + "]行:供应商id不能为空!";
					}
				}
			}

			if (!error.equals("")) {
				String results = "商品[" + he.getPlmCode() + "],提交失败,失败原因:"
						+ error + "\r\n";
				result += results;
			} else // 修改提交状态
			{
				result += "商品[" + he.getPlmCode() + "],提交成功!" + "\r\n";
				he.setOrderStatus("3");
				he.setSupplierCount(0);
				for (PlmProductSupplierInfoEntity_HI h : suli) {
					h.setReturnStatus("1");
					h.setIsSubmit("1");
					plmProductSupplierInfoDAO_HI.update(h);
				}

				this.update(he);
			}

		}

		return result;
	}

	// 阶段三批量下载
	@Override
	public String uploadProductBuyerPattch(JSONObject param,
			HttpServletResponse response) throws ServerException {

		try {
			// response.
			XSSFWorkbook wb = new XSSFWorkbook();
			Sheet headsheet = wb.createSheet("商品主信息");
			for (int c = 0; c < 45; c++) {
				headsheet.setColumnWidth(c, 200 * 30);
			}
			Row row = headsheet.createRow(0);

			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont xfont = wb.createFont();
			xfont.setFontName("仿宋_GB2312");
			xfont.setBold(true);
			xfont.setFontHeightInPoints((short) 12);
			row.setRowStyle(style);
			XSSFCell cell = (XSSFCell) row.createCell(0);
			cell.setCellValue("PLM ID(系统填入不可修改)");
			XSSFCell cell1 = (XSSFCell) row.createCell(1);
			cell1.setCellValue("商品名中文");
			XSSFCell cell2 = (XSSFCell) row.createCell(2);
			cell2.setCellValue("商品名英文");
			XSSFCell cell3 = (XSSFCell) row.createCell(3);
			cell3.setCellValue("品牌名(中文)(系统填入不可修改)");
			XSSFCell cell4 = (XSSFCell) row.createCell(4);
			cell4.setCellValue("保质期天数(必填 数字)");
			XSSFCell cell5 = (XSSFCell) row.createCell(5);
			cell5.setCellValue("特别牌照(必填)");
			XSSFCell cell6 = (XSSFCell) row.createCell(6);
			cell6.setCellValue("商品来源(必填)");
			XSSFCell cell7 = (XSSFCell) row.createCell(7);
			cell7.setCellValue("独有商品(必填 )");
			XSSFCell cell8 = (XSSFCell) row.createCell(8);
			cell8.setCellValue("专柜商品(必填)");
			XSSFCell cell9 = (XSSFCell) row.createCell(9);
			cell9.setCellValue("商品性质(必填)");
			XSSFCell cell10 = (XSSFCell) row.createCell(10);
			cell10.setCellValue("Buying Level(必填)");
			XSSFCell cell11 = (XSSFCell) row.createCell(11);
			cell11.setCellValue("危险品及易燃易爆(必填)");
			XSSFCell cell12 = (XSSFCell) row.createCell(12);
			cell12.setCellValue("POS弹出信息商品(Unit)");
			XSSFCell cell13 = (XSSFCell) row.createCell(13);
			cell13.setCellValue("国际采购商品(必填)");
			XSSFCell cell14 = (XSSFCell) row.createCell(14);
			cell14.setCellValue("季节性商品(必填)");
			XSSFCell cell15 = (XSSFCell) row.createCell(15);
			cell15.setCellValue("TOP商品(必填)");
			XSSFCell cell16 = (XSSFCell) row.createCell(16);
			cell16.setCellValue("计价单位(必填)");
			XSSFCell cell17 = (XSSFCell) row.createCell(17);
			cell17.setCellValue("蓝帽商品(必填)");
			XSSFCell cell18 = (XSSFCell) row.createCell(18);
			cell18.setCellValue("单位规格(必填)");
			XSSFCell cell19 = (XSSFCell) row.createCell(19);
			cell19.setCellValue("税收分类编码(必填)");
			XSSFCell cell20 = (XSSFCell) row.createCell(20);
			cell20.setCellValue("商品可退货属性(必填)");
			XSSFCell cell21 = (XSSFCell) row.createCell(21);
			cell21.setCellValue("仓库来源(必填)");
			XSSFCell cell22 = (XSSFCell) row.createCell(22);
			cell22.setCellValue("日损耗率(%)");
			XSSFCell cell23 = (XSSFCell) row.createCell(23);
			cell23.setCellValue("商品种类");
			XSSFCell cell24 = (XSSFCell) row.createCell(24);
			cell24.setCellValue("跨境商品");
			XSSFCell cell25 = (XSSFCell) row.createCell(25);
			cell25.setCellValue("VC商品");
			XSSFCell cell26 = (XSSFCell) row.createCell(26);
			cell26.setCellValue("原产国");
			XSSFCell cell27 = (XSSFCell) row.createCell(27);
			cell27.setCellValue("价格竞争商品");
			XSSFCell cell28 = (XSSFCell) row.createCell(28);
			cell28.setCellValue("仓库可收货天数");
			XSSFCell cell29 = (XSSFCell) row.createCell(29);
			cell29.setCellValue("仓库可出货天数");
			XSSFCell cell30 = (XSSFCell) row.createCell(30);
			cell30.setCellValue("特殊要求");
			XSSFCell cell31 = (XSSFCell) row.createCell(31);
			cell31.setCellValue("运输存储");
			XSSFCell cell32 = (XSSFCell) row.createCell(32);
			cell32.setCellValue("商品证照(必填)");
			XSSFCell cell33 = (XSSFCell) row.createCell(33);
			cell33.setCellValue("建议零售价");
			XSSFCell cell34 = (XSSFCell) row.createCell(34);
			cell34.setCellValue("参考货号(必填)");
			XSSFCell cell35 = (XSSFCell) row.createCell(35);
			cell35.setCellValue("/周/店预测(必填)");
			XSSFCell cell36 = (XSSFCell) row.createCell(36);
			cell36.setCellValue("是否乳制品(必填)");
			XSSFCell cell37 = (XSSFCell) row.createCell(37);
			cell37.setCellValue("货品形态");

			// pog生效方式
			XSSFCell cell38 = (XSSFCell) row.createCell(38);
			cell38.setCellValue("POG生效方式");

			XSSFCell cell39 = (XSSFCell) row.createCell(39);
			cell39.setCellValue("区域");

			XSSFCell cell40 = (XSSFCell) row.createCell(40);
			cell40.setCellValue("Tier");

			XSSFCell cell41 = (XSSFCell) row.createCell(41);
			cell41.setCellValue("POG部门编码");

			XSSFCell cell42 = (XSSFCell) row.createCell(42);
			cell42.setCellValue("Store Type");

			XSSFCell cell43 = (XSSFCell) row.createCell(43);
			cell43.setCellValue("Trade Zone");

			Sheet sheet2 = wb.createSheet("供应商信息");
			for (int rw2 = 0; rw2 < 19; rw2++) {
				sheet2.setColumnWidth(rw2, 200 * 30);
			}
			Row row1 = sheet2.createRow(0);
			row1.setRowStyle(style);
			XSSFCell row1cell = (XSSFCell) row1.createCell(0);
			row1cell.setCellValue("PLM ID(系统填入不可修改)");
			XSSFCell row2cell = (XSSFCell) row1.createCell(1);
			row2cell.setCellValue("商品名中文(系统填入不可修改)");
			XSSFCell row3cell = (XSSFCell) row1.createCell(2);
			row3cell.setCellValue("供应商Id(系统填入不可修改)");

			XSSFCell row4cell = (XSSFCell) row1.createCell(3);
			row4cell.setCellValue("供应商名称(系统填入不可修改)");
			XSSFCell row5cell = (XSSFCell) row1.createCell(4);
			row5cell.setCellValue("生效方式(必填)");
			XSSFCell row6cell = (XSSFCell) row1.createCell(5);
			row6cell.setCellValue("生效仓库");
			XSSFCell row7cell = (XSSFCell) row1.createCell(6);
			row7cell.setCellValue("生效门店");
			XSSFCell row8cell = (XSSFCell) row1.createCell(7);
			row8cell.setCellValue("区域");
			XSSFCell row9cell = (XSSFCell) row1.createCell(8);
			row9cell.setCellValue("地点清单");

			XSSFCell row10cell = (XSSFCell) row1.createCell(9);
			row10cell.setCellValue("产地(必填)");
			XSSFCell row11cell = (XSSFCell) row1.createCell(10);
			row11cell.setCellValue("成本币种");
			XSSFCell row12cell = (XSSFCell) row1.createCell(11);
			row12cell.setCellValue("成本价");

			Sheet sheet3 = wb.createSheet("商品售价");
			for (int z = 0; z < 5; z++) {
				sheet3.setColumnWidth(z, 200 * 30);
			}
			Row row2 = sheet3.createRow(0);
			row2.setRowStyle(style);
			XSSFCell shrowcell = (XSSFCell) row2.createCell(0);
			shrowcell.setCellValue("PLM ID(系统填入不可修改)");
			XSSFCell shrowcell1 = (XSSFCell) row2.createCell(1);
			shrowcell1.setCellValue("商品名（中文）(系统填入不可修改)");
			XSSFCell shrowcell6 = (XSSFCell) row2.createCell(2);
			shrowcell6.setCellValue("售价Id(系统填入不可修改)");
			XSSFCell shrowcell3 = (XSSFCell) row2.createCell(3);
			shrowcell3.setCellValue("售价分组(系统填入不可修改)");
			XSSFCell shrowcell4 = (XSSFCell) row2.createCell(4);
			shrowcell4.setCellValue("售价区域(必填)");
			XSSFCell shrowcell5 = (XSSFCell) row2.createCell(5);
			shrowcell5.setCellValue("零售价(必填)");
			XSSFCell shrowcell7 = (XSSFCell) row2.createCell(6);
			shrowcell7.setCellValue("是否主零售价(必填)");

			Sheet sheet4 = wb.createSheet("填入说明");
			// sheet4.setColumnWidth(0, 300 * 300);
			Row row4 = sheet4.createRow(0);
			row4.setHeight((short) 400);
			XSSFCell c4 = (XSSFCell) row4.createCell(0);
			c4.setCellValue("详情说明  所有填入按照excel文本格式填写。[商品主信息]:1.特别牌照,专柜商品,危险品及易燃易爆,国际采购商品,蓝帽商品,跨境商品,VC商品,"
					+ "价格竞争商品,是否乳制品.填写1或2,1为是 2为否.");
			Row row41 = sheet4.createRow(1);
			row41.setHeight((short) 400);
			XSSFCell c5 = (XSSFCell) row41.createCell(0);
			c5.setCellValue("2.商品来源。填写 1或2 (1.Local manufacture,2.Import)");

			Row row42 = sheet4.createRow(2);
			row42.setHeight((short) 400);
			XSSFCell c6 = (XSSFCell) row42.createCell(0);
			c6.setCellValue("3.独有商品。填写 1-3 (1.NON EB,2.EB,3.PEB)");

			Row row43 = sheet4.createRow(3);
			row43.setHeight((short) 400);
			XSSFCell c7 = (XSSFCell) row43.createCell(0);
			c7.setCellValue("4.商品性质。填写 1或2 (1.National,2.Regional)");

			Row row44 = sheet4.createRow(4);
			row44.setHeight((short) 400);
			XSSFCell c8 = (XSSFCell) row44.createCell(0);
			c8.setCellValue("5.Buying Level。填写 1-4 (1.Group Ordering,2.Group Negotiation,3.BU Joint Buying,4.Local Buying)");

			Row row45 = sheet4.createRow(5);
			row45.setHeight((short) 400);
			XSSFCell c9 = (XSSFCell) row45.createCell(0);
			c9.setCellValue("6.POS弹出信息商品。填写 1-4 (1.NO,2.生产日期,3.预付卡,4.会员卡)");

			Row row46 = sheet4.createRow(6);
			row46.setHeight((short) 400);
			XSSFCell c10 = (XSSFCell) row46.createCell(0);
			c10.setCellValue("7.季节性商品。填写 0-7 (0.No,1.Sucmer Only,2.Sucmer & All Year,3.CNY Only,4.CNY & All Year,5.Winter Only,6.Winter & All Year,7.Christmas)");

			Row row47 = sheet4.createRow(7);
			row47.setHeight((short) 400);
			XSSFCell c11 = (XSSFCell) row47.createCell(0);
			c11.setCellValue("8.计价单位。填写1-54 (1.把,2.板,3.包,4.杯,5.本,6.册...)");

			Row row48 = sheet4.createRow(8);
			row48.setHeight((short) 400);
			XSSFCell c12 = (XSSFCell) row48.createCell(0);
			c12.setCellValue("9.商品可退货属性。填写1或3 (1.Returnable,3.Non-Returnable)");

			Row row49 = sheet4.createRow(9);
			row49.setHeight((short) 400);
			XSSFCell c13 = (XSSFCell) row49.createCell(0);
			c13.setCellValue("10.仓库来源。填写1-3 (1.OW,2.BW,3.JW)");

			Row row50 = sheet4.createRow(10);
			row50.setHeight((short) 400);
			XSSFCell c14 = (XSSFCell) row50.createCell(0);
			c14.setCellValue("11.日损耗率。填写1或2 (1.0%,2.100%)");

			Row row51 = sheet4.createRow(11);
			row51.setHeight((short) 400);
			XSSFCell c15 = (XSSFCell) row51.createCell(0);
			c15.setCellValue("12.商品种类。填写1-5 (1.POG,2.Promotion,3.Trial,5.POG&Promotion)");

			Row row52 = sheet4.createRow(12);
			row52.setHeight((short) 400);
			XSSFCell c16 = (XSSFCell) row52.createCell(0);
			c16.setCellValue("13.特殊要求。填写1-12 (1.清真,2.26国,3.日本食品,4.高原类,5.防嗮类,6.保暖类,7.易冻品,8.口香糖,9.赠品,10.物料,11.危险品,12.无)");

			Row row53 = sheet4.createRow(13);
			row53.setHeight((short) 400);
			XSSFCell c17 = (XSSFCell) row53.createCell(0);
			c17.setCellValue("14.运输存储。填写1-5 (1.冷链,2.易冻品,3.易燃易爆,4.串味,5.无)");

			Row row54 = sheet4.createRow(14);
			row54.setHeight((short) 400);
			XSSFCell c18 = (XSSFCell) row54.createCell(0);
			c18.setCellValue("15.商品证照。填写FD-NONE (FD.食品,IFY.乳制品(含婴幼儿配方乳粉),DAIRY.乳制品(不含婴幼儿配方乳粉),HF.保健食品,MDAN.医疗器械一类(无需许可),MDBN.医疗器械二类(无需许可),FPP.计生用品,MDBL.医疗器械二类(需许可),MDCL.医疗器械三类(需许可),OTCA.甲类非处方药,OTCB.乙类非处方药,PD.处方药,ALCOHOL.酒类,NONE.无)");

			Row row55 = sheet4.createRow(15);
			row55.setHeight((short) 400);
			XSSFCell c19 = (XSSFCell) row55.createCell(0);
			c19.setCellValue("16.货品形态。填写1-4 (1.液体,2.固体,3.固液,4.气溶胶)");

			Row row60 = sheet4.createRow(16);
			row60.setHeight((short) 400);
			XSSFCell c23 = (XSSFCell) row60.createCell(0);
			c23.setCellValue("17.参考货号。对应商品列表 rms_id字段,如无需参考货号 填写 001");

			Row row61 = sheet4.createRow(17);
			row61.setHeight((short) 400);
			XSSFCell c24 = (XSSFCell) row61.createCell(0);
			c24.setCellValue("18.当商品种类为1(POG)时,需要填写以下字段。POG生效方式(填写1或2。1.区域,2.Tier),区域(填写范围 [\"N\",\"S\",\"E\",\"W\"] 可多填,用逗号隔开 例如:[\"N\",\"S\"]),Tier(填写范围  1,2,3/4/5 可多填用逗号隔开),Store Type(填写范围 Super Sta,star,Anchor,Basic,Baby/Embryo 可多填用逗号隔开),Trade Zone(填写范围 DC,CC,RC,SC,OC,RC+OC 可多填用逗号隔开 例如:[\"DC\",\"CC\"]),POG部门编码。"
					+ "当POG生效方式为1时  Tier字段默认置空,当POG生效方式为 2 时 区域 字段默认置空。");

			Row row56 = sheet4.createRow(18);
			row56.setHeight((short) 400);
			XSSFCell c20 = (XSSFCell) row56.createCell(0);
			c20.setCellValue("[供应商信息]。1.生效方式，填写1-5(1.全部仓+店,2.全部店铺,3.地点清单,4.指定仓+店,5.区域) 2.[地点清单]对应PLM基础模块 地点清单'编号'字段,[区域] 填写[\"N\",\"S\",\"E\",\"W\"]其中一个或多个用',' 分隔 例如:[\"N\",\"S\",].生效仓库,门店,根据生效方式字段  对应 仓,店,生效仓库门店填写多个门店id或仓库id 用','隔开 例如:99011,99012");

			Row row57 = sheet4.createRow(19);
			row57.setHeight((short) 400);
			XSSFCell c21 = (XSSFCell) row57.createCell(0);
			c21.setCellValue("[供应商信息]。2.成本币种，填写1或2(1.CNY,2.USD)");

			Row row58 = sheet4.createRow(20);
			row58.setHeight((short) 400);
			XSSFCell c22 = (XSSFCell) row58.createCell(0);
			c22.setCellValue("[商品售价]。当所选商品没有商品售价时,模板会默认带出一条该商品的主售价信息为必填项,导入时务必将 [售价Id] 字段置空。1.售价区域,填写对应 PLM基础模块[售价区域] 区域名称,2.是否主零售价 填写 Y或N, Y(是)N(否)");

			JSONArray headlist = param.getJSONArray("headlist");
			int supplierow = 1;
			int pricecount = 1;
			for (int i = 0; i < headlist.size(); i++) {
				JSONObject head = headlist.getJSONObject(i);
				Integer productHeadId = head.getInteger("productHeadId");
				PlmProductHeadEntity_HI headobj = this.getById(productHeadId);

				Row headrows = headsheet.createRow(i + 1);
				XSSFCell headcell = (XSSFCell) headrows.createCell(0);
				headcell.setCellValue(headobj.getPlmCode());

				XSSFCell headcell1 = (XSSFCell) headrows.createCell(1);
				headcell1.setCellValue(headobj.getProductName());

				XSSFCell headcell2 = (XSSFCell) headrows.createCell(2);
				headcell2.setCellValue(headobj.getProductEname());

				XSSFCell headcell3 = (XSSFCell) headrows.createCell(3);
				headcell3.setCellValue(headobj.getBrandnameCn());

				XSSFCell headcell4 = (XSSFCell) headrows.createCell(4);
				headcell4.setCellValue(headobj.getSxDays());

				XSSFCell headcell5 = (XSSFCell) headrows.createCell(5);
				headcell5.setCellValue(headobj.getSpecialLicence());

				XSSFCell headcell6 = (XSSFCell) headrows.createCell(6);
				headcell6.setCellValue(headobj.getProductResource());

				XSSFCell headcell7 = (XSSFCell) headrows.createCell(7);
				headcell7.setCellValue(headobj.getUniqueCommodities());

				XSSFCell headcell8 = (XSSFCell) headrows.createCell(8);
				headcell8.setCellValue(headobj.getSpecialtyProduct());

				XSSFCell headcell9 = (XSSFCell) headrows.createCell(9);
				headcell9.setCellValue(headobj.getProductProperties());

				XSSFCell headcell10 = (XSSFCell) headrows.createCell(10);
				headcell10.setCellValue(headobj.getBuyingLevel());

				XSSFCell headcell11 = (XSSFCell) headrows.createCell(11);
				headcell11.setCellValue(headobj.getDangerousProduct());

				XSSFCell headcell12 = (XSSFCell) headrows.createCell(12);
				headcell12.setCellValue(headobj.getPosInfo());

				XSSFCell headcell13 = (XSSFCell) headrows.createCell(13);
				headcell13.setCellValue(headobj.getInternationProduct());

				XSSFCell headcell14 = (XSSFCell) headrows.createCell(14);
				headcell14.setCellValue(headobj.getSesionProduct());

				XSSFCell headcell15 = (XSSFCell) headrows.createCell(15);
				headcell15.setCellValue(headobj.getTopProduct());

				XSSFCell headcell16 = (XSSFCell) headrows.createCell(16);
				headcell16.setCellValue(headobj.getCountUnit());

				XSSFCell headcell17 = (XSSFCell) headrows.createCell(17);
				headcell17.setCellValue(headobj.getBluecapProduct());

				XSSFCell headcell18 = (XSSFCell) headrows.createCell(18);
				headcell18.setCellValue(headobj.getUnit());

				XSSFCell headcell19 = (XSSFCell) headrows.createCell(19);
				headcell19.setCellValue(headobj.getRateClassCode());

				XSSFCell headcell20 = (XSSFCell) headrows.createCell(20);
				headcell20.setCellValue(headobj.getProductReturn());

				XSSFCell headcell21 = (XSSFCell) headrows.createCell(21);
				headcell21.setCellValue(headobj.getWarehouseResource());

				XSSFCell headcell22 = (XSSFCell) headrows.createCell(22);
				headcell22.setCellValue(headobj.getDayDamage());

				XSSFCell headcell23 = (XSSFCell) headrows.createCell(23);
				headcell23.setCellValue(headobj.getProductCategeery());

				XSSFCell headcell24 = (XSSFCell) headrows.createCell(24);
				headcell24.setCellValue(headobj.getCrossborderProduct());

				XSSFCell headcell25 = (XSSFCell) headrows.createCell(25);
				headcell25.setCellValue(headobj.getVcProduct());

				XSSFCell headcell26 = (XSSFCell) headrows.createCell(26);
				headcell26.setCellValue(headobj.getOriginCountry());

				XSSFCell headcell27 = (XSSFCell) headrows.createCell(27);
				headcell27.setCellValue(headobj.getPricewarProduct());

				XSSFCell headcell28 = (XSSFCell) headrows.createCell(28);
				if (headobj.getWarehouseGetDay() == null) {
					headcell28.setCellValue("");
				} else {
					headcell28.setCellValue(headobj.getWarehouseGetDay());
				}

				XSSFCell headcell29 = (XSSFCell) headrows.createCell(29);
				if (headobj.getWarehousePostDay() == null) {
					headcell29.setCellValue("");
				} else {
					headcell29.setCellValue(headobj.getWarehousePostDay());
				}

				XSSFCell headcell30 = (XSSFCell) headrows.createCell(30);
				headcell30.setCellValue(headobj.getSpecialRequier());

				XSSFCell headcell31 = (XSSFCell) headrows.createCell(31);
				headcell31.setCellValue(headobj.getTransportStorage());

				XSSFCell headcell32 = (XSSFCell) headrows.createCell(32);
				headcell32.setCellValue(headobj.getProductLicense());

				XSSFCell headcell33 = (XSSFCell) headrows.createCell(33);
				headcell33.setCellValue(headobj.getSugRetailprice());

				XSSFCell headcell34 = (XSSFCell) headrows.createCell(34);
				headcell34.setCellValue(headobj.getConsultProductno());

				XSSFCell headcell35 = (XSSFCell) headrows.createCell(35);
				headcell35.setCellValue(headobj.getSalesQty());

				XSSFCell headcell36 = (XSSFCell) headrows.createCell(36);
				headcell36.setCellValue(headobj.getIsDiaryproduct());

				XSSFCell headcell37 = (XSSFCell) headrows.createCell(37);
				headcell37.setCellValue(headobj.getProductShape());

				// pog生效方式
				XSSFCell headcell38 = (XSSFCell) headrows.createCell(38);
				headcell38.setCellValue(headobj.getPogWays());

				XSSFCell headcell39 = (XSSFCell) headrows.createCell(39);
				headcell39.setCellValue(headobj.getTier1());

				XSSFCell headcell40 = (XSSFCell) headrows.createCell(40);
				headcell40.setCellValue(headobj.getTier2());

				XSSFCell headcell41 = (XSSFCell) headrows.createCell(41);
				headcell41.setCellValue(headobj.getPogDeparment());

				XSSFCell headcell42 = (XSSFCell) headrows.createCell(42);
				headcell42.setCellValue(headobj.getStoreType());

				XSSFCell headcell43 = (XSSFCell) headrows.createCell(43);
				headcell43.setCellValue(headobj.getTradeZone());

				JSONObject supplierlist = new JSONObject();
				supplierlist.put("productHeadId", productHeadId);
				List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
						.findList(supplierlist);

				for (int j = 0; j < suli.size(); j++) {
					PlmProductSupplierInfoEntity_HI suinfo = suli.get(j);
					Row headrow1 = sheet2.createRow(supplierow);
					String supplierId = suinfo.getSupplierId();
					String suppliername = suinfo.getSupplierName();

					String cost = suinfo.getCurrencyCost(); // 成本币种
					String price = suinfo.getPrice(); // 成本价

					String place = suinfo.getPlace(); // 产地
					String sxway = suinfo.getSxWay(); // 生效方式
					String sxwarehouse = suinfo.getSxWarehouse(); // 生效仓库
					String shop = suinfo.getSxStore();
					String area = suinfo.getArea(); // 区域

					String placenote = suinfo.getPlaceNote(); // 地点清单

					XSSFCell suppliercell = (XSSFCell) headrow1.createCell(0);
					suppliercell.setCellValue(headobj.getPlmCode());

					XSSFCell suppliercell1 = (XSSFCell) headrow1.createCell(1);
					suppliercell1.setCellValue(headobj.getProductName());

					XSSFCell suppliercell20 = (XSSFCell) headrow1.createCell(2);
					suppliercell20.setCellValue(supplierId);

					XSSFCell suppliercell2 = (XSSFCell) headrow1.createCell(3);
					suppliercell2.setCellValue(suppliername);

					XSSFCell suppliercell3 = (XSSFCell) headrow1.createCell(4);
					suppliercell3.setCellValue(sxway);

					XSSFCell suppliercell4 = (XSSFCell) headrow1.createCell(5);
					suppliercell4.setCellValue(sxwarehouse);

					XSSFCell suppliercell5 = (XSSFCell) headrow1.createCell(6);
					suppliercell5.setCellValue(shop);

					XSSFCell suppliercell6 = (XSSFCell) headrow1.createCell(7);
					suppliercell6.setCellValue(area);

					XSSFCell suppliercell21 = (XSSFCell) headrow1.createCell(8);

					suppliercell21.setCellValue(placenote);

					XSSFCell suppliercell17 = (XSSFCell) headrow1.createCell(9);

					suppliercell17.setCellValue(place);

					XSSFCell suppliercell18 = (XSSFCell) headrow1
							.createCell(10);
					suppliercell18.setCellValue(cost);

					XSSFCell suppliercell19 = (XSSFCell) headrow1
							.createCell(11);
					suppliercell19.setCellValue(price);

					supplierow++; // 自增
				}

				// 商品售价
				List<PlmProductPriceEntity_HI> pricelist = plmProductPriceDao
						.findList(supplierlist);

				if (pricelist.size() == 0) {
					Row pricerow = sheet3.createRow(pricecount);
					XSSFCell pricecell = (XSSFCell) pricerow.createCell(0);
					pricecell.setCellValue(headobj.getPlmCode());

					XSSFCell pricecell1 = (XSSFCell) pricerow.createCell(1);
					pricecell1.setCellValue(headobj.getProductName());

					XSSFCell priceId = (XSSFCell) pricerow.createCell(2);
					priceId.setCellValue("");

					XSSFCell pricecell2 = (XSSFCell) pricerow.createCell(3);
					pricecell2.setCellValue(headobj.getOtherInfo());

					XSSFCell pricecell3 = (XSSFCell) pricerow.createCell(4);
					pricecell3.setCellValue("");

					XSSFCell pricecell4 = (XSSFCell) pricerow.createCell(5);
					pricecell4.setCellValue("");

					XSSFCell pricecell5 = (XSSFCell) pricerow.createCell(6);
					pricecell5.setCellValue("Y");

					pricecount++;
				} else {
					for (int prices = 0; prices < pricelist.size(); prices++) {
						PlmProductPriceEntity_HI priceobj = pricelist
								.get(prices);
						Row pricerow = sheet3.createRow(pricecount);
						XSSFCell pricecell = (XSSFCell) pricerow.createCell(0);
						pricecell.setCellValue(headobj.getPlmCode());

						XSSFCell pricecell1 = (XSSFCell) pricerow.createCell(1);
						pricecell1.setCellValue(headobj.getProductName());

						XSSFCell priceId = (XSSFCell) pricerow.createCell(2);
						priceId.setCellValue(priceobj.getPriceId());

						XSSFCell pricecell2 = (XSSFCell) pricerow.createCell(3);
						pricecell2.setCellValue(priceobj.getPriceGroup());

						XSSFCell pricecell3 = (XSSFCell) pricerow.createCell(4);
						pricecell3.setCellValue(priceobj.getPriceArea());

						XSSFCell pricecell4 = (XSSFCell) pricerow.createCell(5);
						pricecell4.setCellValue(priceobj.getUnitPrice());

						XSSFCell pricecell5 = (XSSFCell) pricerow.createCell(6);
						pricecell5.setCellValue(priceobj.getMainPrice());

						pricecount++;
					}
				}
			}// headlist

			String filename = "productComplete.xlsx";
			String realPath = this.getClass().getClassLoader().getResource("")
					.getFile();

			String[] result = realPath.split("/");
			String append = "";
			for (int i = 0; i < result.length; i++) {
				if (i <= 3) {
					String s = result[i];
					append += s + "/";
				}
			}

			String resultrealPath = append += filename;

			java.io.File file = new java.io.File(resultrealPath);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream output = new FileOutputStream(file);
			wb.write(output);

			FileInputStream input = new FileInputStream(file);
			ResultFileEntity fileentity = fastdfsServer.fileUpload(input,
					filename);// 上传至文件服务器
			System.out.println(fileentity.getFilePath() + ".................");
			output.close();
			input.close();

			return fileentity.getAccessPath();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ServerException(e.getMessage());
		}
		// return null;
	}

	public static HSSFSheet setHSSFValidation(HSSFSheet sheet,
			String[] textlist, int firstRow, int endRow, int firstCol,
			int endCol) {
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,
				endRow, firstCol, endCol);
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint
				.createExplicitListConstraint(textlist);
		// 数据有效性对象
		HSSFDataValidation data_validation_list = new HSSFDataValidation(
				regions, constraint);
		sheet.addValidationData(data_validation_list);
		return sheet;
	}

	public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
			String promptContent, int firstRow, int endRow, int firstCol,
			int endCol) {
		// 构造constraint对象
		DVConstraint constraint = DVConstraint
				.createCustomFormulaConstraint("BB1");
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,
				endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_view = new HSSFDataValidation(
				regions, constraint);
		data_validation_view.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(data_validation_view);
		return sheet;
	}

	// 三阶段批量
	@Override
	public JSONObject SaveThreeProductByExcel(JSONObject param)
			throws ServerException {
		String filepath = param.getString("filepath");
		File file = this.getNetUrl(filepath);
		Map<String, String> suppliermap = new HashMap<String, String>();
		Map<String, String> drugmap = new HashMap<String, String>();
		Map<String, String> mediamap = new HashMap<String, String>();

		List<PlmProductHeadEntity_HI> headlist = new ArrayList<PlmProductHeadEntity_HI>();
		List<PlmProductSupplierInfoEntity_HI> supplierlist = new ArrayList<PlmProductSupplierInfoEntity_HI>();

		Map<String, List<PlmProductPriceEntity_HI>> priceMap = new HashMap<String, List<PlmProductPriceEntity_HI>>();
		try {
			InputStream is = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet head = wb.getSheetAt(0); // 头信息
			int rownum = head.getLastRowNum(); // 总行数
			int headcolumnCount = head.getRow(0).getPhysicalNumberOfCells();
			if (headcolumnCount != 44) {
				throw new ServerException("[商品主信息]数据格式错误:请按照模板格式填写!");
			}
			for (int i = 1; i <= rownum; i++) {
				XSSFRow curent = head.getRow(i);
				if (curent == null) {
					continue;
				}
				XSSFCell code = curent.getCell(0); // PLM_code
				XSSFCell productname = curent.getCell(1); // 商品名中文
				XSSFCell productename = curent.getCell(2); // 商品英文名
				XSSFCell brand = curent.getCell(3); // 品牌名称
				XSSFCell sxdays = curent.getCell(4); // 生效时间
				XSSFCell lisen = curent.getCell(5); // 特别牌照
				XSSFCell productresource = curent.getCell(6); //
				XSSFCell uniqueCommodities = curent.getCell(7);
				XSSFCell specialtyProduct = curent.getCell(8);
				XSSFCell productProperties = curent.getCell(9);
				XSSFCell buyinglevel = curent.getCell(10);
				XSSFCell dangerousProduct = curent.getCell(11);
				XSSFCell posinfo = curent.getCell(12);
				XSSFCell internationProduct = curent.getCell(13);
				XSSFCell sesionProduct = curent.getCell(14);
				XSSFCell topproduct = curent.getCell(15);
				XSSFCell countunit = curent.getCell(16);
				XSSFCell BluecapProduct = curent.getCell(17);
				XSSFCell Unit = curent.getCell(18);
				XSSFCell RateClassCode = curent.getCell(19);
				XSSFCell ProductReturn = curent.getCell(20);
				XSSFCell WarehouseResource = curent.getCell(21); //
				XSSFCell DayDamage = curent.getCell(22);
				XSSFCell ProductCategeery = curent.getCell(23);
				XSSFCell CrossborderProduct = curent.getCell(24);
				XSSFCell getVcProduct = curent.getCell(25);
				XSSFCell OriginCountry = curent.getCell(26);
				XSSFCell PricewarProduct = curent.getCell(27);
				XSSFCell WarehouseGetDay = curent.getCell(28);
				XSSFCell WarehousePostDay = curent.getCell(29);
				XSSFCell SpecialRequier = curent.getCell(30); // PLM_code
				XSSFCell TransportStorage = curent.getCell(31);
				XSSFCell ProductLicense = curent.getCell(32);
				XSSFCell SugRetailprice = curent.getCell(33);
				XSSFCell ConsultProductno = curent.getCell(34);
				XSSFCell SalesQty = curent.getCell(35);
				XSSFCell IsDiaryproduct = curent.getCell(36); //
				XSSFCell ProductShape = curent.getCell(37);

				XSSFCell pogWay = curent.createCell(38); // pog生效方式
				XSSFCell t1 = curent.createCell(39); // 区域
				XSSFCell t2 = curent.createCell(40); // Tier
				XSSFCell pogdeptno = curent.createCell(41); // pog部门编码
				XSSFCell storetype = curent.createCell(42); // Store Type
				XSSFCell tradezone = curent.createCell(43); // Trade zone
				// XSSFCell gross = curent.createCell(44); // 毛利

				Integer headid = null; // 当前行的头id
				String plmcode = this.getStringCellValueXss(code);
				if (plmcode.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,PLM_ID,不能为空!");
				} else {
					// 验证供应商id的正确性
					JSONObject plmparam = new JSONObject();
					plmparam.put("plmCode", plmcode);
					List<PlmProductHeadEntity_HI> headli = this
							.findList(plmparam);
					if (headli.size() == 0) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,PLM_ID不存在!");
					} else {
						headid = headli.get(0).getProductHeadId();
					}

					priceMap.put(plmcode,
							new ArrayList<PlmProductPriceEntity_HI>());

				}

				PlmProductHeadEntity_HI prohead = this.getById(headid);
				// XSSFCell productname = curent.getCell(1); // 商品名中文
				// XSSFCell productename = curent.getCell(2); // 商品英文名
				prohead.setProductName(this.getStringCellValueXss(productname));
				prohead.setProductEname(this
						.getStringCellValueXss(productename));
				String sxdaysvalue = this.getStringCellValueXss(sxdays);
				if (sxdaysvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,保质期天数不能为空!");
				} else {
					boolean f = this.isNumeric(sxdaysvalue);
					if (f == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,保质期天数只能为数字!");
					} else {
						prohead.setSxDays(new Integer(sxdaysvalue));
					}
				}

				String lisenvalue = this.getStringCellValueXss(lisen);// 特别牌照
				if (lisenvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,特别牌照不能为空!");
				} else {
					if (!lisenvalue.equals("1") && !lisenvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,特别牌照数据错误!");
					}
				}
				prohead.setSpecialLicence(lisenvalue);

				String productresourcevalue = this
						.getStringCellValueXss(productresource);

				if (productresourcevalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,商品来源不能为空!");
				} else {
					if (!productresourcevalue.equals("1")
							&& !productresourcevalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,商品来源数据错误!");
					}
				}
				prohead.setProductResource(productresourcevalue);

				String uniqueCommoditiesvalue = this
						.getStringCellValueXss(uniqueCommodities);

				if (uniqueCommoditiesvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,独有商品不能为空!");
				} else {
					if (!uniqueCommoditiesvalue.equals("1")
							&& !uniqueCommoditiesvalue.equals("2")
							&& !uniqueCommoditiesvalue.equals("3")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,独有商品数据错误!");
					}
				}

				prohead.setUniqueCommodities(uniqueCommoditiesvalue);

				String specialtyProductvalue = this
						.getStringCellValueXss(specialtyProduct);

				if (specialtyProductvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,专柜商品不能为空!");
				} else {
					if (!specialtyProductvalue.equals("1")
							&& !specialtyProductvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,专柜商品商品数据错误!");
					}
				}
				prohead.setSpecialtyProduct(specialtyProductvalue);

				String productPropertiesvalue = this
						.getStringCellValueXss(productProperties);
				if (productPropertiesvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,商品性质不能为空!");
				} else {
					if (!productPropertiesvalue.equals("1")
							&& !productPropertiesvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,商品性质数据错误!");
					}
				}

				prohead.setProductProperties(productPropertiesvalue);

				String buyinglevelvalue = this
						.getStringCellValueXss(buyinglevel);

				if (buyinglevelvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,Buying Level不能为空!");
				} else {
					if (this.isNumeric(buyinglevelvalue) == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,Buying Level数据错误!");
					} else {
						Integer s = new Integer(buyinglevelvalue);
						if (s < 1 || s > 4) {
							throw new ServerException("[商品主信息]第" + (i + 1)
									+ "行,Buying Level数据错误!");
						}
					}

				}

				prohead.setBuyingLevel(buyinglevelvalue);

				String dangerousProductvalue = this
						.getStringCellValueXss(dangerousProduct);

				if (dangerousProductvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,危险品及易燃易爆不能为空!");
				} else {
					if (!dangerousProductvalue.equals("1")
							&& !dangerousProductvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,危险品及易燃易爆数据错误!");
					}
				}

				prohead.setDangerousProduct(dangerousProductvalue);

				String posinfovalue = this.getStringCellValueXss(posinfo);
				if (posinfovalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,POS弹出信息商品不能为空!");
				} else {
					if (!posinfovalue.equals("1") && !posinfovalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,POS弹出信息商品数据错误!");
					}
				}

				prohead.setPosInfo(posinfovalue);

				String internationProductvalue = this
						.getStringCellValueXss(internationProduct);
				if (internationProductvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,国际采购商品不能为空!");
				} else {
					if (!internationProductvalue.equals("1")
							&& !internationProductvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,国际采购商品数据错误!");
					}
				}

				prohead.setInternationProduct(internationProductvalue);

				String sesionProductvalue = this
						.getStringCellValueXss(sesionProduct);

				if (sesionProductvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,季节性商品不能为空!");
				} else {
					if (this.isNumeric(sesionProductvalue) == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,季节性商品数据错误!");
					} else {
						Integer s = new Integer(sesionProductvalue);
						if (s < 0 || s > 7) {
							throw new ServerException("[商品主信息]第" + (i + 1)
									+ "行,季节性商品数据错误!");
						}
					}

				}

				prohead.setSesionProduct(sesionProductvalue);

				String topproductvalue = this.getStringCellValueXss(topproduct);
				if (topproductvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,TOP商品不能为空!");
				}

				prohead.setTopProduct(topproductvalue);

				String countunitvalue = this.getStringCellValueXss(countunit);
				if (countunitvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,计价单位不能为空!");
				} else {
					if (this.isNumeric(countunitvalue) == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,计价单位数据错误!");
					} else {
						Integer s = new Integer(countunitvalue);
						if (s < 1 || s > 54) {
							throw new ServerException("[商品主信息]第" + (i + 1)
									+ "行,计价单位数据错误!");
						}
					}

				}

				prohead.setCountUnit(countunitvalue);

				String BluecapProductvalue = this
						.getStringCellValueXss(BluecapProduct);
				if (BluecapProductvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,蓝帽商品不能为空!");
				} else {
					if (!BluecapProductvalue.equals("1")
							&& !BluecapProductvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,蓝帽商品数据错误!");
					}
				}
				prohead.setBluecapProduct(BluecapProductvalue);

				String Unitvalue = this.getStringCellValueXss(Unit);
				if (Unitvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,单位规格不能为空!");
				}

				prohead.setUnit(Unitvalue);
				String RateClassCodevalue = this
						.getStringCellValueXss(RateClassCode);
				if (RateClassCodevalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,税收分类编码不能为空!");
				} else {
					JSONObject jq = new JSONObject();
					jq.put("plmTaxTypeCode", RateClassCodevalue);
					List<PlmTaxTypeListEntity_HI> ptaxli = plmTaxType
							.findList(jq);
					if (ptaxli.size() == 0) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,税收分类编码不存在!");
					} else {
						prohead.setRateClassCode(RateClassCodevalue);
						prohead.setRateClass(ptaxli.get(0).getPlmTaxTypeName());
					}
				}

				// prohead.setRateClassCode(RateClassCodevalue);

				String ProductReturnvalue = this
						.getStringCellValueXss(ProductReturn);
				if (ProductReturnvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,商品可退货属性不能为空!");
				} else {
					if (!ProductReturnvalue.equals("1")
							&& !ProductReturnvalue.equals("3")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,商品可退货属性数据错误!");
					}
				}

				prohead.setProductReturn(ProductReturnvalue);

				String WarehouseResourcevalue = this
						.getStringCellValueXss(WarehouseResource);

				if (WarehouseResourcevalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,仓库来源不能为空!");
				} else {
					if (!WarehouseResourcevalue.equals("1")
							&& !WarehouseResourcevalue.equals("2")
							&& !WarehouseResourcevalue.equals("3")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,仓库来源数据错误!");
					}
				}

				prohead.setWarehouseResource(WarehouseResourcevalue);

				String DayDamagevalue = this.getStringCellValueXss(DayDamage);
				if (!DayDamagevalue.equals("")) {
					if (!DayDamagevalue.equals("1")
							&& !DayDamagevalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,日损耗率数据错误!");
					}
				}

				String department = prohead.getDepartment();
				if (department.substring(0, 2).equals("99")) {
					if (!department.equals("9901")
							&& !department.equals("9907")) {
						prohead.setDayDamage("100%");
					}
				} else {
					prohead.setDayDamage(null);
				}

				String ProductCategeeryvalue = this
						.getStringCellValueXss(ProductCategeery);
				if (!ProductCategeeryvalue.equals("")) {

					if (!ProductCategeeryvalue.equals("1")
							&& !ProductCategeeryvalue.equals("2")
							&& !ProductCategeeryvalue.equals("3")
							&& !ProductCategeeryvalue.equals("5")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,商品种类数据错误!");
					}
				}

				prohead.setProductCategeery(ProductCategeeryvalue);

				String CrossborderProductvalue = this
						.getStringCellValueXss(CrossborderProduct);

				if (CrossborderProductvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,跨境商品不能为空!");
				} else {
					if (!CrossborderProductvalue.equals("1")
							&& !CrossborderProductvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,跨境商品数据错误!");
					}
				}

				prohead.setCrossborderProduct(CrossborderProductvalue);

				String vcproductvalue = this
						.getStringCellValueXss(getVcProduct);
				if (!vcproductvalue.equals("")) {
					if (!vcproductvalue.equals("1")
							&& !vcproductvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,vc商品数据错误!");
					}
				}
				prohead.setVcProduct(vcproductvalue);
				String OriginCountryvalue = this
						.getStringCellValueXss(OriginCountry);
				prohead.setOriginCountry(OriginCountryvalue);

				String PricewarProductvalue = this
						.getStringCellValueXss(PricewarProduct);
				if (!PricewarProductvalue.equals("")) {
					if (!PricewarProductvalue.equals("1")
							&& !PricewarProductvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,价格竞争商品数据错误!");
					}
				}
				prohead.setPricewarProduct(PricewarProductvalue);
				String WarehouseGetDayvalue = this
						.getStringCellValueXss(WarehouseGetDay);
				if (!WarehouseGetDayvalue.equals("")) {
					if (this.isNumeric(WarehouseGetDayvalue) == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,仓库可收货天数请填写数字!");
					}
				}

				prohead.setWarehouseGetDay(new Integer(WarehouseGetDayvalue));

				String WarehousePostDayvalue = this
						.getStringCellValueXss(WarehousePostDay);
				if (!WarehousePostDayvalue.equals("")) {
					if (this.isNumeric(WarehousePostDayvalue) == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,仓库可出货天数请填写数字!");
					}
				}
				prohead.setWarehousePostDay(new Integer(WarehousePostDayvalue));

				String SpecialRequiervalue = this
						.getStringCellValueXss(SpecialRequier);

				if (SpecialRequiervalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,特殊要求不能为空!");
				} else {
					if (this.isNumeric(SpecialRequiervalue) == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,特殊要求数据错误!");
					} else {
						Integer s = new Integer(SpecialRequiervalue);
						if (s < 1 || s > 12) {
							throw new ServerException("[商品主信息]第" + (i + 1)
									+ "行,特殊要求数据错误!");
						}
					}

				}

				prohead.setSpecialRequier(SpecialRequiervalue);

				String TransportStoragevalue = this
						.getStringCellValueXss(TransportStorage);
				if (TransportStoragevalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,运输存储不能为空!");
				} else {
					if (this.isNumeric(TransportStoragevalue) == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,运输存储数据错误!");
					} else {
						Integer s = new Integer(TransportStoragevalue);
						if (s < 1 || s > 5) {
							throw new ServerException("[商品主信息]第" + (i + 1)
									+ "行,运输存储数据错误!");
						}
					}

				}

				prohead.setTransportStorage(TransportStoragevalue);

				String ProductLicensevalue = this
						.getStringCellValueXss(ProductLicense);
				if (ProductLicensevalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,商品证照不能为空!");
				} else {
					Map<String, String> plicen = new HashMap<String, String>();
					plicen.put("FD", "FD");
					plicen.put("IFY", "IFY");
					plicen.put("DAIRY", "DAIRY");
					plicen.put("HF", "HF");
					plicen.put("MDBN", "MDBN");
					plicen.put("FPP", "FPP");
					plicen.put("MDBL", "MDBL");
					plicen.put("MDCL", "MDCL");
					plicen.put("OTCA", "OTCA");
					plicen.put("OTCB", "OTCB");
					plicen.put("PD", "PD");
					plicen.put("ALCOHOL", "ALCOHOL");
					plicen.put("NONE", "NONE");
					if (!plicen.containsKey(ProductLicensevalue)) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,商品证照数据错误!");
					}

				}

				prohead.setProductLicense(ProductLicensevalue);

				String SugRetailpricevalue = this
						.getStringCellValueXss(SugRetailprice);

				if (!SugRetailpricevalue.equals("")) {
					if (this.isNumeric(SugRetailpricevalue) == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,建议零售价请填写数字!");
					}
				}

				prohead.setSugRetailprice(SugRetailpricevalue);

				String ConsultProductnovalue = this
						.getStringCellValueXss(ConsultProductno);
				if (ConsultProductnovalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,参考货号不能为空!");
				} else {
					if (!ConsultProductnovalue.equals("001")) {
						Map<String, Object> pa = new HashMap<String, Object>();
						pa.put("rmsCode", ConsultProductnovalue);

						List<PlmProductHeadEntity_HI_RO> aa = plmProductHeadEntity_HI_RO
								.findList(PlmProductHeadEntity_HI_RO.QUERY_HEAD
										+ " and PRODUCT.rms_code=:rmsCode", pa);

						if (aa.size() == 0) {
							throw new ServerException("[商品主信息]第" + (i + 1)
									+ "行,参考货号不存在!");
						} else {
							prohead.setConsultProductno(ConsultProductnovalue);
							prohead.setConsultProductname(aa.get(0)
									.getConsultProductname());
						}

					} else {
						prohead.setConsultProductno(ConsultProductnovalue);
						prohead.setConsultProductname("NEW");
					}
				}

				String SalesQtyvalue = this.getStringCellValueXss(SalesQty);
				if (SalesQtyvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,/周/店预测不能为空!");
				} else {
					boolean flag = this.isNumeric(SalesQtyvalue);
					if (flag == false) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,/周/店预测只能填写数字!");
					}
				}

				String IsDiaryproductvalue = this
						.getStringCellValueXss(IsDiaryproduct);

				if (IsDiaryproductvalue.equals("")) {
					throw new ServerException("[商品主信息]第" + (i + 1)
							+ "行,是否乳制品不能为空!");
				} else {
					if (!IsDiaryproductvalue.equals("1")
							&& !IsDiaryproductvalue.equals("2")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,是否乳制品数据错误!");
					}
				}

				String ProductShapevalue = this
						.getStringCellValueXss(ProductShape);
				if (!ProductShapevalue.equals("")) {
					if (!ProductShapevalue.equals("1")
							&& !ProductShapevalue.equals("2")
							&& !ProductShapevalue.equals("3")
							&& !ProductShapevalue.equals("4")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,货品形态数据错误!");
					}
				}

				prohead.setSalesQty(SalesQtyvalue);
				prohead.setIsDiaryproduct(IsDiaryproductvalue);
				prohead.setProductShape(ProductShapevalue);

				if (ProductCategeeryvalue.equals("1")) // 校验pog信息
				{
					String pogway = this.getStringCellValueXss(pogWay);
					if (pogway.equals("")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,pog生效方式不能为空!");
					} else {
						if (!pogway.equals("1") && !pogway.equals("2")) {
							throw new ServerException("[商品主信息]第" + (i + 1)
									+ "行,pog生效方式数据错误!");
						}
					}
					String t1value = this.getStringCellValueXss(t1);
					String t2value = this.getStringCellValueXss(t2);
					String tradevalue = this.getStringCellValueXss(tradezone);
					prohead.setTier1(t1value);
					prohead.setTier2(t2value);
					prohead.setPogWays(pogway);
					prohead.setTradeZone(tradevalue);
					if (pogway.equals("1")) {
						prohead.setTier2("");
					} else {
						prohead.setTier1("");
					}

					String pogdeptvalue = this.getStringCellValueXss(pogdeptno);
					if (pogdeptvalue.equals("")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行,pog部门编码不能为空!");
					}
					prohead.setPogDeparment(pogdeptvalue);
					String storevalue = this.getStringCellValueXss(storetype);
					if (storevalue.equals("")) {
						throw new ServerException("[商品主信息]第" + (i + 1)
								+ "行, Store Type不能为空!");
					}

				}
				// this.update(prohead);

				headlist.add(prohead);

			} // head

			XSSFSheet sh1 = wb.getSheetAt(1);
			Integer sh1rownum = sh1.getLastRowNum();
			int sh1columnCount = sh1.getRow(0).getPhysicalNumberOfCells();
			if (sh1columnCount != 12) {
				throw new ServerException("[供应商信息],数据格式错误!");
			}
			for (int i = 1; i <= sh1rownum; i++) {
				XSSFRow curent = sh1.getRow(i);
				if (curent == null) {
					continue;
				}
				XSSFCell code = curent.getCell(0); //
				XSSFCell productname = curent.getCell(1);
				XSSFCell supplierId = curent.getCell(2);
				XSSFCell yb = curent.getCell(3);
				XSSFCell sxway = curent.getCell(4);
				XSSFCell sxwarehouse = curent.getCell(5);
				XSSFCell shop = curent.getCell(6);
				XSSFCell area = curent.getCell(7);
				XSSFCell placenote = curent.getCell(8);
				XSSFCell place = curent.getCell(9);
				XSSFCell cost = curent.getCell(10);
				XSSFCell price = curent.getCell(11);

				Integer headid = null;

				String plmcode = this.getStringCellValueXss(code); // 商品编号
				if (plmcode.equals("")) {
					throw new ServerException("[供应商信息]第" + (i + 1)
							+ "行,PLM_ID,不能为空!");
				} else {
					// 验证供应商id的正确性

					Map<String, Object> pa = new HashMap<String, Object>();
					pa.put("plmCode", plmcode);
					List<PlmProductHeadEntity_HI_RO> headli = plmProductHeadEntity_HI_RO
							.findList(PlmProductHeadEntity_HI_RO.QUERY_HEAD
									+ " and PRODUCT.plm_code= :plmCode", pa);

					if (headli.size() == 0) {
						throw new ServerException("[供应商信息]第" + (i + 1)
								+ "行,PLM_ID不存在!");
					} else {
						headid = headli.get(0).getProductHeadId();
					}
				}

				String supplierIdvalue = this.getStringCellValueXss(supplierId);
				JSONObject suobj = new JSONObject();
				suobj.put("productHeadId", headid);
				suobj.put("supplierCode", supplierIdvalue);

				List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
						.findList(suobj);

				if (suli.size() == 0) {
					continue;
				} else {
					PlmProductSupplierInfoEntity_HI pobj = suli.get(0);

					JSONObject jo = new JSONObject();
					jo.put("supplierCode", supplierIdvalue);
					List<PlmSupplierQaNonObInfoEntity_HI> liinfo = plmSupplierQaNonObInfoServer
							.findList(jo);
					if (liinfo.size() > 0) {
						pobj.setGroupId(liinfo.get(0).getQaGroupCode());
						pobj.setGroupName(liinfo.get(0).getQaGroupName());
					}

					String sxwayvalue = this.getStringCellValueXss(sxway);
					if (sxwayvalue.equals("")) {
						throw new ServerException("[供应商信息]第" + (i + 1)
								+ "行,生效方式不能为空!");
					} else {
						if (this.isNumeric(sxwayvalue) == false) {
							throw new ServerException("[供应商信息]第" + (i + 1)
									+ "行,生效方式数据错误!");
						} else {
							Integer sxint = new Integer(sxwayvalue);
							if (sxint < 1 || sxint > 5) {
								throw new ServerException("[供应商信息]第" + (i + 1)
										+ "行,生效方式数据错误!");
							}
						}
					}

					String sxwarehousevalue = this
							.getStringCellValueXss(sxwarehouse);
					String shopvalue = this.getStringCellValueXss(shop);
					String areavalue = this.getStringCellValueXss(area);
					String placevalue = this.getStringCellValueXss(place);
					String placenotevalue = this
							.getStringCellValueXss(placenote);
					String costvalue = this.getStringCellValueXss(cost);
					String pricevalue = this.getStringCellValueXss(price);

					if (sxwayvalue.equals("1") || sxwayvalue.equals("2")) {
						pobj.setSxWay(sxwayvalue);
					} else if (sxwayvalue.equals("3")) { // 地点清单
						if (placevalue.equals("")) {
							throw new ServerException("[供应商信息]第" + (i + 1)
									+ "行,地点清单不能为空!");
						} else {
							JSONObject plo = new JSONObject();
							plo.put("code", placenotevalue);
							List<PlmLocationListEntity_HI> pq = ploList
									.findList(plo);
							if (pq.size() == 0) {
								throw new ServerException("[供应商信息]第" + (i + 1)
										+ "行,地点清单不存在!");
							} else {
								pobj.setPlaceNote(pq.get(0).getDescName());
							}
						}

						pobj.setSxWay(sxwayvalue);
						// pobj.setPlaceNote(placenotevalue);
					} else if (sxwayvalue.equals("4")) {
						pobj.setSxWay(sxwayvalue);
						if (sxwarehousevalue.equals("")) {
							throw new ServerException("[供应商信息]第" + (i + 1)
									+ "行,生效仓库不能为空!");
						}
						if (shopvalue.equals("")) {
							throw new ServerException("[供应商信息]第" + (i + 1)
									+ "行,生效门店不能为空!");
						}
						pobj.setSxWarehouse(sxwarehousevalue);
						pobj.setSxStore(shopvalue);
					} else if (sxwayvalue.equals("5")) // 区域
					{
						pobj.setSxWay(sxwayvalue);
						if (areavalue.equals("")) {
							throw new ServerException("[供应商信息]第" + (i + 1)
									+ "行,区域不能为空!");
						}
						pobj.setArea(areavalue);
					}

					// plmProductSupplierInfoDAO_HI.update(pobj);
					supplierlist.add(pobj);
				}

			}// sheet1
				// 商品售价

			XSSFSheet sh2 = wb.getSheetAt(2);
			Integer sh2rownum = sh2.getLastRowNum();
			int sh2columnCount = sh2.getRow(0).getPhysicalNumberOfCells();
			if (sh2columnCount != 7) {
				throw new ServerException("[商品售价],数据格式错误!");
			}

			for (int i = 1; i <= sh2rownum; i++) {
				XSSFRow curent = sh2.getRow(i);
				if (curent == null) {
					continue;
				}
				Integer headid = null;
				XSSFCell code = curent.getCell(0); //
				XSSFCell productname = curent.getCell(1);
				XSSFCell priceId = curent.getCell(2);

				XSSFCell y = curent.getCell(3);
				XSSFCell PriceArea = curent.getCell(4);
				XSSFCell UnitPrice = curent.getCell(5);
				XSSFCell mainPrice = curent.getCell(6);

				String plmcode = this.getStringCellValueXss(code);
				if (plmcode.equals("")) {
					throw new ServerException("[商品售价]第" + (i + 1)
							+ "行,PLM_ID不能为空!");
				} else {
					// 验证供应商id的正确性
					JSONObject plmparam = new JSONObject();
					plmparam.put("plmCode", plmcode);
					List<PlmProductHeadEntity_HI> headli = this
							.findList(plmparam);

					if (headli.size() == 0) {
						throw new ServerException("[商品售价]第" + (i + 1)
								+ "行,PLM_ID不存在!");
					} else {
						headid = headli.get(0).getProductHeadId();
					}

				}

				String priceIdvalue = this.getStringCellValueXss(priceId);
				String PriceAreavalue = this.getStringCellValueXss(PriceArea);
				if (PriceAreavalue.equals("")) {
					throw new ServerException("[商品售价]第" + (i + 1)
							+ "行,售价区域不能为空!");
				} else {
					JSONObject price = new JSONObject();
					price.put("priceArea", PriceAreavalue);
					List<PlmProductPriceEntity_HI> li = plmProductPriceDao
							.findList(price);
					if (li.size() == 0) {
						throw new ServerException("[商品售价]第" + (i + 1)
								+ "行,售价区域数据错误,请填写plm基础模块 [售价区域]已存在的区域名称!");
					}
				}

				String mainPriceValue = this.getStringCellValueXss(mainPrice);
				if (!mainPriceValue.equals("Y") && !mainPriceValue.equals("N")) {
					throw new ServerException("[商品售价]第" + (i + 1)
							+ "行,是否主零售价只能填写 Y或N!");
				}

				String UnitPricevalue = this.getStringCellValueXss(UnitPrice);
				if (UnitPricevalue.equals("")) {
					throw new ServerException("[商品售价]第" + (i + 1)
							+ "行,零售价不能为空!");
				} else {
					boolean f = this.isNumeric(UnitPricevalue);
					if (f == false) {
						throw new ServerException("[商品售价]第" + (i + 1)
								+ "行,零售价只能填写数字!");
					}
				}

				PlmProductPriceEntity_HI pri = new PlmProductPriceEntity_HI();
				if (priceIdvalue.equals("")) { // 新增

					pri.setProductHeadId(headid);
					PlmProductHeadEntity_HI he = this.getById(headid);
					if (he != null) {
						pri.setPriceGroup(he.getOtherInfo());
					}
					pri.setPriceArea(PriceAreavalue);
					pri.setUnitPrice(UnitPricevalue);
					pri.setMainPrice(mainPriceValue);

					List<PlmProductPriceEntity_HI> liprice = priceMap
							.get(plmcode);
					liprice.add(pri);
					priceMap.remove(plmcode);
					priceMap.put(plmcode, liprice);

					// PlmProductPriceDAO_HI.save(pri);
				} else {

					JSONObject j = new JSONObject();
					j.put("priceId", priceIdvalue);
					List<PlmProductPriceEntity_HI> priceli = plmProductPriceDao
							.findList(j);
					if (priceli.size() == 0) {
						throw new ServerException("[商品售价]第" + (i + 1)
								+ "行,售价Id不存在!");
					} else {
						pri = priceli.get(0);
						pri.setPriceArea(PriceAreavalue);
						pri.setUnitPrice(UnitPricevalue);
						pri.setMainPrice(mainPriceValue);

						// PlmProductPriceDAO_HI.update(pri);
					}
					List<PlmProductPriceEntity_HI> liprice = priceMap
							.get(plmcode);
					liprice.add(pri);
					priceMap.remove(plmcode);
					priceMap.put(plmcode, liprice);
				}// else

			}

			Map<String, PlmProductPriceEntity_HI> mianprice = new HashMap<String, PlmProductPriceEntity_HI>();
			Map<Integer, PlmProductSupplierInfoEntity_HI> miansupplier = new HashMap<Integer, PlmProductSupplierInfoEntity_HI>();
			// for
			for (Map.Entry<String, List<PlmProductPriceEntity_HI>> data : priceMap
					.entrySet()) {
				String plmCode = data.getKey();
				Integer maincount = 0;
				List<PlmProductPriceEntity_HI> liprice = data.getValue();
				if (liprice.size() == 0) {
					throw new ServerException("商品" + plmCode + ",至少要有一个售价信息");
				}
				// 校验是否有多个主零售价 或者没有主零售价，以及校验一个商品至少有一个售价信息
				for (PlmProductPriceEntity_HI p : liprice) {
					if (p.getMainPrice().equals("Y")) {
						maincount++;
					}
				}
				if (maincount == 0) {
					throw new ServerException("商品" + plmCode + ",缺少'主零售价'售价信息!");
				} else if (maincount > 1) {
					throw new ServerException("商品" + plmCode
							+ ",有且只能有一个'主零售价'售价信息!");
				} else if (maincount == 1) {
					for (PlmProductPriceEntity_HI p : liprice) {
						if (p.getMainPrice().equals("Y")) {
							mianprice.put(plmCode, p);
							break;
						}
					}

					PlmProductPriceDAO_HI.saveOrUpdateAll(liprice);
				}
			}

			for (int i = 0; i < supplierlist.size(); i++) {
				PlmProductSupplierInfoEntity_HI info = supplierlist.get(i);
				Integer headid = info.getProductHeadId();
				String ismain = info.getIsMainsupplier();
				if (!miansupplier.containsKey(headid) && ismain.equals("Y")) {
					miansupplier.put(headid, info);
				}

			}

			for (PlmProductHeadEntity_HI heado : headlist) {
				if (heado.getPurchaseType().equals("1")) { // purchase 计算毛利
					String plmcode = heado.getPlmCode();
					Integer headid = heado.getProductHeadId();

					PlmProductPriceEntity_HI p = mianprice.get(plmcode); // 零售价
					PlmProductSupplierInfoEntity_HI suinfo = miansupplier
							.get(headid);
					Float un = Float.parseFloat(p.getUnitPrice());// 零售价
					Float pn = Float.parseFloat(suinfo.getPrice());// 成本价
					Float gross = un - pn; // 毛利
					heado.setGrossEarnings(String.valueOf(gross));
				}

			}
			plmProductSupplierInfoDAO_HI.updateAll(supplierlist);
			this.save(headlist);

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			throw new ServerException(e.getMessage());
		}
		return null;
	}

	// 阶段三批量提交
	@Override
	public JSONObject SaveProductBuyer3Submit(JSONObject param) {
		String result = "";
		JSONArray headlist = param.getJSONArray("headlist");
		JSONArray paarray = new JSONArray(); // 参数json数组对象
		for (int i = 0; i < headlist.size(); i++) {
			JSONObject headjson = headlist.getJSONObject(i);
			Integer productHeadId = headjson.getInteger("productHeadId");
			String error = "";
			PlmProductHeadEntity_HI he = this
					.getById(new Integer(productHeadId));

			if (he == null) {
				continue;
			}
			if (he.getSxDays() == null) {
				error += "生效时间不能为空!";
			}
			if (he.getSpecialLicence() == null
					|| he.getSpecialLicence().equals("")) {
				error += "特别牌照不能为空!";
			}
			// if (he.getGrossEarnings() == null
			// || he.getGrossEarnings().equals("")) {
			// error += "毛利不能为空!";
			// }
			if (he.getProductResource() == null
					|| he.getProductResource().equals("")) {
				error += "商品种类不能为空!";
			}
			if (he.getUniqueCommodities() == null
					|| he.getUniqueCommodities().equals("")) {
				error += "独有商品不能为空!";
			}

			if (he.getSpecialtyProduct() == null
					|| he.getSpecialtyProduct().equals("")) {
				error += "专柜商品不能为空!";
			}

			if (he.getProductProperties() == null
					|| he.getProductProperties().equals("")) {
				error += "商品性质不能为空!";
			}

			if (he.getBuyingLevel() == null || he.getBuyingLevel().equals("")) {
				error += "buyingLevel不能为空!";
			}
			if (he.getDangerousProduct() == null
					|| he.getDangerousProduct().equals("")) {
				error += "危险品及易燃易爆不能为空!";
			}
			if (he.getPosInfo() == null || he.getPosInfo().equals("")) {
				error += "POS弹出信息不能为空!";
			}
			if (he.getInternationProduct() == null
					|| he.getInternationProduct().equals("")) {
				error += "国际采购商品不能为空!";
			}
			if (he.getSesionProduct() == null
					|| he.getSesionProduct().equals("")) {
				error += "季节性商品不能为空!";
			}
			if (he.getTopProduct() == null || he.getTopProduct().equals("")) {
				error += "TOP商品不能为空!";
			}
			if (he.getCountUnit() == null || he.getCountUnit().equals("")) {
				error += "计价单位不能为空!";
			}
			if (he.getBluecapProduct() == null
					|| he.getBluecapProduct().equals("")) {
				error += "蓝帽商品不能为空!";
			}
			if (he.getUnit() == null || he.getUnit().equals("")) {
				error += "单位规格不能为空!";
			}
			if (he.getRateClassCode() == null
					|| he.getRateClassCode().equals("")) {
				error += "税收分类编码不能为空!";
			}
			if (he.getProductReturn() == null
					|| he.getProductReturn().equals("")) {
				error += "退货条件不能为空!";
			}
			if (he.getSpecialRequier() == null
					|| he.getSpecialRequier().equals("")) {
				error += "特殊要求不能为空!";
			}
			if (he.getTransportStorage() == null
					|| he.getTransportStorage().equals("")) {
				error += "运输存储不能为空!";
			}

			if (he.getProductLicense() == null
					|| he.getProductLicense().equals("")) {
				error += "商品证照不能为空!";
			}
			if (he.getSugRetailprice() == null
					|| he.getSugRetailprice().equals("")) {
				error += "建议零售价不能为空!";
			}
			if (he.getConsultProductno() == null
					|| he.getConsultProductno().equals("")) {
				error += "参考货号不能为空!";
			}
			if (he.getSalesQty() == null || he.getSalesQty().equals("")) {
				error += "/周/店预测不能为空!";
			}
			if (he.getIsDiaryproduct() == null
					|| he.getIsDiaryproduct().equals("")) {
				error += "是否乳制品不能为空!";
			}

			// 获取供应商行表
			JSONObject su = new JSONObject();
			su.put("productHeadId", he.getProductHeadId());
			List<PlmProductSupplierInfoEntity_HI> suli = plmProductSupplierInfoDao
					.findList(su);
			if (suli.size() == 0) {
				error += "请添加供应商信息!";
			}
			boolean isinner = false;
			for (int j = 0; j < su.size(); j++) {
				PlmProductSupplierInfoEntity_HI info = suli.get(j);

				if (info != null) {
					if (info.getInnerpackageSpe() > 1) {
						isinner = true;
					}

					if (info.getProductLength() == null
							|| info.getProductLength().equals("")) {
						error += "供应商信息第[" + j + "]行:商品深不能为空!";
					}

					if (info.getProductBreadth() == null
							|| info.getProductBreadth().equals("")) {
						error += "供应商信息第[" + j + "]行:商品宽不能为空!";
					}

					if (info.getProductHeight() == null
							|| info.getProductHeight().equals("")) {
						error += "供应商信息第[" + j + "]行:商品高不能为空!";
					}

				}
			}

			// 资质文件
			List<PlmProductQaFileEntity_HI> qalist = plmProductQaFileDao
					.findList(su);
			if (qalist.size() == 0) {
				error += "请添加资质文件!";
			} else {
				for (int z = 0; z < qalist.size(); z++) {
					PlmProductQaFileEntity_HI qa = qalist.get(z);
					if (qa.getQaFiletype() == null
							|| qa.getQaFiletype().equals("")) {
						error += "资质文件第[" + z + "]行:资质文件类型不能为空!";
					}
					if (qa.getQaUrl() == null || qa.getQaUrl().equals("")) {
						error += "资质文件第[" + z + "]行:资质文件不能为空!";
					}
					if (qa.getQaCode() == null || qa.getQaCode().equals("")) {
						error += "资质文件第[" + z + "]行:证书编号不能为空!";
					}
					if (qa.getDatecurent() == null) {
						error += "资质文件第[" + z + "]行:日期不能为空!";
					}
				}
			}

			// 图片文件
			List<PlmProductPicfileTableEntity_HI> picli = plmProductPicfileTableDao
					.findList(su);

			if (picli.size() == 0) {
				error += "请添加图片数据!";
			} else {
				for (int p = 0; p < picli.size(); p++) {
					PlmProductPicfileTableEntity_HI picf = picli.get(p);
					if (picf.getPicUrl() == null || picf.getPicUrl().equals("")) {
						error += "图片文件第[" + p + "]行:请选择图片!";
					}
					if (picf.getSupplierId() == null
							|| picf.getSupplierId().equals("")) {
						error += "图片文件第[" + p + "]行:供应商id不能为空!";
					}
				}
			}

			List<PlmProductPriceEntity_HI> priceli = plmProductPriceDao
					.findList(su);

			if (priceli.size() == 0) {
				error += "请添加商品售价!";
			} else {
				for (int pr = 0; pr < priceli.size(); pr++) {
					PlmProductPriceEntity_HI priceobj = priceli.get(pr);
					if (priceobj.getPriceArea() == null
							|| priceobj.getPriceArea().equals("")) {
						error += "商品售价第[" + pr + "]行:请填写售价区域!";
					}
					if (priceobj.getUnitPrice() == null
							|| priceobj.getUnitPrice().equals("")) {
						error += "商品售价第[" + pr + "]行:请填写零售价!";
					}
				}
			}

			if (!error.equals("")) {
				String results = "商品[" + he.getPlmCode() + "],提交失败,失败原因:"
						+ error + "\r\n";
				result += results;
			} else // 修改提交状态
			{
				result += "商品[" + he.getPlmCode() + "],提交成功!" + "\r\n";
				// he.setOrderStatus("3");
				he.setSupplierCount(0);
				this.update(he);

				// 判断流程并且设置参数

				JSONObject pa = new JSONObject(); // 默认流程变量
				pa.put("OBQA", "0");
				pa.put("SUPPLY", "0");
				pa.put("OEM", "0");
				pa.put("isob", "0");
				pa.put("isnonob", "0");
				pa.put("NONOBQA", "0");
				pa.put("LOCAL", "0");
				pa.put("liuchenparam", "PRODUCTADD");

				String markerchannel = he.getMarkerChannel(); // 判断是否线上
				String deptsort = he.getDepartment().substring(0, 1);
				String deptsortnum = he.getDepartment().substring(0, 4);
				String producttype = he.getProductType(); // 商品类型
				if ((producttype.equals("1") || producttype.equals("4"))
						&& (deptsort.equals("1")
								|| deptsort.equals("2")
								|| deptsort.equals("3") // ob路线
								|| deptsort.equals("4") || deptsort.equals("5")
								|| deptsort.equals("6")
								|| deptsortnum.equals("9901") || deptsortnum
									.equals("9907"))) // ob商品
				{
					pa.put("OBQA", "1");
					pa.put("SUPPLY", "0");
					pa.put("isob", "1");
					pa.put("isnonob", "0");
					pa.put("NONOBQA", "0");
					pa.put("LOCAL", "1");
					pa.put("liuchenparam", "PRODUCTADD");
					pa.put("OEM", "1");

				} // ob商品

				// 非ob
				else if ((producttype.equals("2") || producttype.equals("3")
						|| producttype.equals("5") || producttype.equals("6"))
						&& (deptsort.equals("1")
								|| deptsort.equals("2")
								|| deptsort.equals("3") // ob路线
								|| deptsort.equals("4") || deptsort.equals("5")
								|| deptsort.equals("6")
								|| deptsortnum.equals("9901") || deptsortnum
									.equals("9907"))) {

					pa.put("LOCAL", "1");
					pa.put("isob", "0");
					pa.put("isnonob", "1");
					pa.put("OBQA", "0");
					pa.put("SUPPLY", "0");
					pa.put("NONOBQA", "1");
					pa.put("OEM", "0");

					if (new Integer(he.getWarehousePostDay()) > 300
							|| isinner == true) {
						pa.put("SUPPLY", "1");
					} else {
						pa.put("SUPPLY", "0");
					}
					pa.put("liuchenparam", "PRODUCTADD");

				}// 2356
				else {
					pa.put("OBQA", "0");
					pa.put("SUPPLY", "0");
					pa.put("isob", "1");
					pa.put("isnonob", "0");
					pa.put("NONOBQA", "0");
					pa.put("OEM", "0");
					pa.put("LOCAL", "1");
					pa.put("liuchenparam", "PRODUCTADD");
				}

				pa.put("productHeadId", he.getProductHeadId());

				String mainbarcode = "";
				JSONObject jt = new JSONObject(); // 找到主barcode
				jt.put("productHeadId", he.getProductHeadId());
				jt.put("isMain", "Y");
				List<PlmProductBarcodeTableEntity_HI> libs = plmProductBarcodeTableDao
						.findList(jt);
				if (libs.size() > 0) {
					mainbarcode = libs.get(0).getBarcode();
				}
				// int len = 32 - (mainbarcode.length() + 7);
				String title = "商品新增" + '-' + he.getProductName() + "-"
						+ he.getBrandnameCn() + "-" + mainbarcode;
				pa.put("plmCode", he.getPlmCode());
				pa.put("title", title);
				paarray.add(pa);
			}

		} // headlist
		JSONObject results = new JSONObject();
		results.put("msg", result);
		results.put("palist", paarray);

		return results;
	}

	// **
	// * 判断是否含有特殊字符
	// *
	// * @param str
	// * @return true为包含，false为不包含
	// */
	public static boolean isSpecialChar(String str) {
		// (  -  _  +  )  空格键  の .  &
		// boolean flag = true;
		// String regEx = "(-_+).の&\t~";
		// for (int i = 0; i < regEx.length(); i++) {
		// char s = regEx.charAt(i);
		// Character c = new Character(s);
		// String value = c.toString();
		// if (str.contains(value)) {
		// flag = false;
		// }
		// }
		String patters = "[ _`!$%^&*αβⅠの\\(\\)+=|{}\\Ⅱ'\\Ⅲ\\Ⅳ\\Ⅴ\\Ⅵ\\ Ф\\°\\#:\\;',\\[\\].\\<\\>/?（）——+|‘”“’。，、？^0-9 a-z A-Z \\《\\》\\＜\\ ＞  \\u4e00-\\u9fa5]|\n|\r|\t";
		Pattern p = Pattern.compile(patters);
		Matcher m = p.matcher(str);
		return m.find();
	}

	// public static void main(String[] args) {
	// // int[] arr = { 9, 7, 13, 20, 15, 100, 8 };
	// // Arrays.sort(arr);
	// // for (int i : arr) {
	// // System.out.print(i + ",");
	// // }
	//
	// Long rowCount = HibernateViewObjectUtil.object2Long(5L);
	// System.out.println(rowCount);
	//
	// }

	@Override
	public JSONObject getSupplierByProduct(JSONObject param)
			throws ServerException {
		JSONArray headlist = param.getJSONArray("headlist");
		List<PlmProductSupplierInfoEntity_HI> s = new ArrayList<PlmProductSupplierInfoEntity_HI>();
		try {
			PlmProductHeadEntity_HI one = new PlmProductHeadEntity_HI();
			List<PlmProductQaFileEntity_HI> qadata = new ArrayList<PlmProductQaFileEntity_HI>();
			for (int i = 0; i < headlist.size(); i++) {

				JSONObject ja = headlist.getJSONObject(i);
				Integer productHeadid = ja.getInteger("productHeadId");
				// 循环获取供应商
				if (i == 0) {
					one = this.getById(productHeadid);
				}

				String otherinfo = one.getOtherInfo();
				//
				if (otherinfo.equals("1")) // 药品
				{

					PlmProductQaFileEntity_HI q1 = new PlmProductQaFileEntity_HI();
					q1.setQaFiletype("药品注册证");
					q1.setProductHeadId(productHeadid);
					q1.setQaCodetype("3");
					q1.setDateType("3");
					q1.setSupplierName(ja.getString("productName"));

					PlmProductQaFileEntity_HI q2 = new PlmProductQaFileEntity_HI();
					q2.setQaFiletype("质量标准");
					q2.setProductHeadId(productHeadid);
					q2.setQaCodetype("5");
					q2.setDateType("3");
					q2.setSupplierName(ja.getString("productName"));

					PlmProductQaFileEntity_HI q3 = new PlmProductQaFileEntity_HI();
					q3.setQaFiletype("标签备案样式");
					q3.setProductHeadId(productHeadid);
					q3.setQaCodetype("5");
					q3.setDateType("3");
					q3.setSupplierName(ja.getString("productName"));

					PlmProductQaFileEntity_HI q4 = new PlmProductQaFileEntity_HI();
					q4.setQaFiletype("说明书备案样式");
					q4.setProductHeadId(productHeadid);
					q4.setQaCodetype("5");
					q4.setDateType("3");
					q4.setSupplierName(ja.getString("productName"));

					qadata.add(q1);
					qadata.add(q2);
					qadata.add(q3);
					qadata.add(q4);

				} else if (otherinfo.equals("2")) // 医疗器械
				{
					PlmProductQaFileEntity_HI q1 = new PlmProductQaFileEntity_HI();
					q1.setQaFiletype("产品技术要求");
					q1.setProductHeadId(productHeadid);
					q1.setQaCodetype("2");
					q1.setDateType("3");
					q1.setSupplierName(ja.getString("productName"));

					PlmProductQaFileEntity_HI q2 = new PlmProductQaFileEntity_HI();
					q2.setQaFiletype("合格证明文件");
					q2.setSupplierName(ja.getString("productName"));
					q2.setProductHeadId(productHeadid);
					q2.setQaCodetype("5");
					q2.setDateType("3");

					PlmProductQaFileEntity_HI q3 = new PlmProductQaFileEntity_HI();
					q3.setQaFiletype("标签备案样式");
					q3.setProductHeadId(productHeadid);
					q3.setQaCodetype("5");
					q3.setDateType("3");
					q3.setSupplierName(ja.getString("productName"));

					PlmProductQaFileEntity_HI q4 = new PlmProductQaFileEntity_HI();
					q4.setQaFiletype("说明书备案样式");
					q4.setProductHeadId(productHeadid);
					q4.setQaCodetype("5");
					q4.setDateType("3");
					q4.setSupplierName(ja.getString("productName"));
					qadata.add(q1);
					qadata.add(q2);
					qadata.add(q3);
					qadata.add(q4);

				}
			}

			if (one.getOtherInfo().equals("1")) {

				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, qadata);
			} else if (one.getOtherInfo().equals("2")) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, qadata);
			} else {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, s);
			}

		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}

	private static boolean isEnStr(String text) {
		String regex = ".*[a-zA-z].*";
		boolean falg = text.matches(regex);
		return falg;
	}

	@Override
	public Pagination<PlmProductQaReport> FindProductQaReportList(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmProductQaReport.querySql);
		if(param.containsKey("QaFiletype")){
			if(!param.getString("QaFiletype").equals("")) {
				query.append(" and f.Qa_filetype='" + param.getString("QaFiletype") + "' ");
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmProductQaReport.class, param, query,
				params);
		query.append(PlmProductQaReport.getAppend(param));
		query.append(" order by f.CREATION_DATE desc ");
		Pagination<PlmProductQaReport> pagination = plmProductQaReportRO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);

		return pagination;
	}

	@Override
	public Pagination<PlmSupplierQaReport> FindSupplierQaReportList(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmSupplierQaReport.querySql);
		Map<String, Object> params = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplierQaReport.class, param, query,
				params);
		query.append(PlmSupplierQaReport.getAppend(param));
		query.append(" order by filetable.creationDate desc ");
		Pagination<PlmSupplierQaReport> pagination = PlmSupplierQaReportRO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);

		return pagination;
	}

	@Override
	public void execute(String sql) {
		placeinfoServer.execute(sql);
	}

	@Override
	public void UpdateRmsColumns(JSONObject dataJSON)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ParseException {
		List<PlmProductUpdatepropertisEntity_HI> info = new ArrayList<PlmProductUpdatepropertisEntity_HI>();
		if (dataJSON.containsKey("uda")) {
			JSONObject uda = dataJSON.getJSONObject("uda");
			if (uda.containsKey("array")) {
				JSONArray udaarray = uda.getJSONArray("array");
				for (int i = 0; i < udaarray.size(); i++) {
					JSONObject row = udaarray.getJSONObject(i);
					PlmProductUpdatepropertisEntity_HI obj = (PlmProductUpdatepropertisEntity_HI) JSONObject
							.toJavaObject(row,
									PlmProductUpdatepropertisEntity_HI.class);
					obj.setRemake("get success");
					info.add(obj);

				}
			}
		}
		if (dataJSON.containsKey("price")) {
			JSONObject price = dataJSON.getJSONObject("price");
			if (price.containsKey("array")) {
				JSONArray pricearray = price.getJSONArray("array");
				for (int i = 0; i < pricearray.size(); i++) {
					JSONObject row = pricearray.getJSONObject(i);
					PlmProductUpdatepropertisEntity_HI obj = (PlmProductUpdatepropertisEntity_HI) JSONObject
							.toJavaObject(row,
									PlmProductUpdatepropertisEntity_HI.class);
					obj.setRemake("get success");
					info.add(obj);

				}
			}
		}
		if (dataJSON.containsKey("cost")) {
			JSONObject cost = dataJSON.getJSONObject("cost");
			if (cost.containsKey("array")) {
				JSONArray costarray = cost.getJSONArray("array");
				for (int i = 0; i < costarray.size(); i++) {
					JSONObject row = costarray.getJSONObject(i);
					PlmProductUpdatepropertisEntity_HI obj = (PlmProductUpdatepropertisEntity_HI) JSONObject
							.toJavaObject(row,
									PlmProductUpdatepropertisEntity_HI.class);
					obj.setRemake("get success");
					info.add(obj);

				}
			}
		}

		UpdatePropertiesMap r = new UpdatePropertiesMap();
		Map<String, String> data = r.getMap();
		this.getUpdateObject(info, data, "rms");
	}

	@Override
	public List<PlmProductSupplierplaceinfoEntity_HI> getList(String string,
			Map<String, Object> map) {
		return suplace.findList(string, map);
	}

	@Override
	public void saveAndSync(JSONObject queryParamJSON) throws IOException {
		List<PlmProductQaFileEntity_HI_RO> hisList = getHistoryList();
		//FTP client connection
		FTPClient ftp = new FTPClient();
		ftp.connect("10.82.31.84", 21);
		ftp.login("VD_SIE_PLM", "h1FyOwwaTX");
		//Mongo client connection
		MongoCredential.createCredential("spusers2", "database", "7343@sp#".toCharArray());
		ServerAddress serverAddress = new ServerAddress("10.82.28.209");
		MongoClient mongoClient = new MongoClient(serverAddress, MongoCredential.createCredential("spusers2", "database", "7343@sp#".toCharArray()), MongoClientOptions.builder().build());
		mongoClient.getCredentialsList();
		MongoDatabase myDatabase = mongoClient.getDatabase("database");
		GridFSBucket gridFSBucket = GridFSBuckets.create(myDatabase,"ProductFile");
		if (hisList.size() > 0) {
//			PlmProductQaFileEntity_HI_RO en = null;
//			int count = 0;
//			while (count < hisList.size()) {
//				if (isFTP(hisList.get(count).getQaUrl())) {
//					en = hisList.get(count);
//					break;
//				}
//				count++;
//			}
			for (PlmProductQaFileEntity_HI_RO en: hisList) {
				if (en != null && en.getProductHeadId() != null) {
					List<PlmProductQaFileEntity_HI> list = getQAList(en);
					if (list != null && list.size() > 0) {
						LOGGER.info("url==============" + en.getQaUrl());
						LOGGER.info("url==============" + en.getProductHeadId());
						InputStream in;
						String fileName;
						if (isFTP(en.getQaUrl())) {
							LOGGER.info("FTP file detected============================");
//					SFTPUtils sftp = SFTPUtils.getInstance("10.82.31.84", 22, "VD_SIE_PLM", "h1FyOwwaTX");
//					in = sftp.download(en.getQaUrl());
							String path = getPath(en.getQaUrl());
							LOGGER.info("FTP download path============================{}", path);
//					in = FtpUtil.downloadFile("10.82.31.84", 21, "VD_SIE_PLM", "h1FyOwwaTX", "FileMan/Liscense/Vendor License Type/6903148258347_watsons_7_90_0.pdf");
							in = ftp.retrieveFileStream(path);
							if (in != null) {
								LOGGER.info("InputStream downloaded============================");
							} else {
								LOGGER.info("InputStream downloaded failed============================");
							}
							fileName = getFTPFilename(en.getQaUrl());
							LOGGER.info("filename: {}============================", fileName);
						} else {
							LOGGER.info("MONGO file detected============================");
							GridFSFile gridFSFile = gridFSBucket.find(new Document().append("_id", en.getQaUrl())).first();
							in = gridFSBucket.openDownloadStream(gridFSFile.getId());
							if (in != null) {
								LOGGER.info("InputStream downloaded============================");
							} else {
								LOGGER.info("InputStream downloaded failed============================");
							}
							fileName = gridFSFile.getExtraElements().get("file_name").toString();
							LOGGER.info("filename: {}============================", fileName);
						}
						try {
							ResultFileEntity fileEntity = fastdfsServer.fileUpload(in, fileName);

							if (list.size() > 0) {
								LOGGER.info("PlmProductQaFileEntity_HI url: {} listId: {} found, size: {}", en.getQaUrl(), en.getProductHeadId(), list.size());
								PlmProductQaFileEntity_HI entity = list.get(0);
								entity.setQaUrl(fileEntity.getAccessPath());
								PlmProductQaFileDAO_HI.saveOrUpdate(entity);
							} else {
								LOGGER.info("no PlmProductQaFileEntity_HI url: {} listId: {} found", en.getQaUrl(), en.getProductHeadId());
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						LOGGER.info("no list_id found, list_id: {}============================", en.getProductHeadId());
					}
				}
			}
//			PlmProductQaFileEntity_HI_RO en = hisList.get(0);
		} else {
			LOGGER.info("no history data found ========================");
		}
	}

	private String getPath(String filename) {
//		String filename = "ftp://10.83.21.11/file/a/b/cyber.html";
		String temp = filename.substring(filename.lastIndexOf("."));
		String tem = filename.substring(0, filename.lastIndexOf("."));
		String suffix = tem.substring(tem.lastIndexOf(".") + 1);
		String preffix = suffix.substring(suffix.indexOf("/") + 1);
		preffix += temp;
		return preffix;
	}

	private String getFTPFilename(String qaUrl) {
		return qaUrl.substring(qaUrl.lastIndexOf("/") + 1);
	}

	private boolean isFTP(String qaUrl) {
		if (qaUrl.indexOf("ftp") != -1) {
			return true;
		}
		return false;
	}

	private List<PlmProductQaFileEntity_HI_RO> getHistoryList() {
		String SQL2 = PlmProductQaFileEntity_HI_RO.HIS_SQL;
		StringBuffer sql = new StringBuffer(SQL2);
		return plmProductQaFileDAO_HI_RO.findList(sql);
	}

	private List<PlmProductQaFileEntity_HI> getQAList(PlmProductQaFileEntity_HI_RO en) {
		String SQL2 = " from PlmProductQaFileEntity_HI where 1=1 ";
		StringBuffer sql = new StringBuffer(SQL2);
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("listId", en.getProductHeadId());
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmProductQaFileEntity_HI.class,
				queryParamJSON, sql, queryParamMap);
		return PlmProductQaFileDAO_HI.findList(sql, queryParamMap);
	}

	@Override
	public PlmProductHeadEntity_HI findProductHead(JSONObject queryParamJSON) throws IOException
	{
		List<PlmProductHeadEntity_HI> list = findList(queryParamJSON);
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}
