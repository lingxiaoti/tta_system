package com.sie.watsons.base.product.services;

import java.rmi.ServerException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.sie.watsons.base.product.model.entities.*;
import com.sie.watsons.base.product.model.inter.*;
import com.sie.watsons.base.product.model.inter.server.PlmProductHeadServer;
import com.sie.watsons.base.productEco.model.entities.PlmProductHeadEcoEntity_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductPriceEcoEntity_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductSupplierInfoEcoEntity_HI;
import com.sie.watsons.base.productEco.model.inter.IPlmProductHeadEco;
import com.sie.watsons.base.productEco.model.inter.IPlmProductPriceEco;
import com.sie.watsons.base.productEco.model.inter.IPlmProductSupplierInfoEco;
import com.sie.watsons.base.productEco.model.inter.server.PlmProductHeadEcoServer;
import com.sie.watsons.base.productEco.model.inter.server.PlmProductSupplierInfoEcoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.avalon.framework.logger.Log4JLogger;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.api.model.inter.IPlmApi;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentInfoEntity_HI;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentInfo;
import com.sie.watsons.base.plmBase.model.entities.PlmLocationListEntity_HI;
import com.sie.watsons.base.plmBase.model.inter.IPlmLocationList;
import com.sie.watsons.base.product.model.dao.readonly.locationMap;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductAddReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductAddReportT;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductBarcodeTableEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductConditionReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductPackageReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductQaReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductSupplierReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmSupplierQaReport;
import com.sie.watsons.base.product.model.entities.readonly.VmiShopEntity_HI_RO2;
import com.sie.watsons.base.product.model.inter.server.PlmProductModifyCheckServer;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmProductHeadService")
public class PlmProductHeadService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductHeadService.class);

	@Autowired
	private IPlmProductObLicense plmProductObLicenseServer;

	@Autowired
	private IPlmProductHead plmProductHeadServer;

	@Autowired
	private PlmProductModifyCheckServer plmProductModify;

	@Autowired
	private IPlmProductBarcodeTable plmProductBarcodeTableDao;

	@Autowired
	private BaseViewObject<PlmProductBarcodeTableEntity_HI_RO> PlmProductBarcodeTableDAO_HI;

	@Autowired
	private ViewObject<PlmProductBarcodeTableEntity_HI> plmProductBarcodeServer;

	@Autowired
	private IPlmProductSupplierInfo plmProductSupplierInfo;

	@Autowired
	private IPlmProductStore plmProductStore;

	@Autowired
	private IPlmDevelopmentInfo deveinfo;

	@Autowired
	private IPlmProductUpdatepropertis plmProductUpdatepropertis;

	@Autowired
	private IPlmProductLog plmProductLog;

	@Autowired
	private IPlmProductDrugfile drugfileServer;
	@Autowired
	private IPlmProductStore plmProductStoreServer; // 门店表

	@Autowired
	private IVmiShop2 vimshopServer;

	@Autowired
	private IVmiWarehouse2 vmiWarehouseServer;

	@Autowired
	private IPlmProductSupplierplaceinfo supplierplaceServer;
	@Autowired
	private IPlmProductConsaleinfo plmProductConsaleinfoServer;

	@Autowired
	private ViewObject<PlmProductSupplierplaceinfoEntity_HI> placeinfoServer;

	@Autowired
	private ViewObject<PlmProductConsaleinfoEntity_HI> consaleServer;

	// 地点清单
	@Autowired
	private IPlmLocationList PlmLocationListServer;

	@Autowired
	private IPlmApi iPlmApiServer; // 查询地点清单接口
	@Autowired
	private IPlmProductSaleshop saleshop;

	@Autowired
	private ViewObject<PlmProductModifyCheckEntity_HI> plmProductModifyCheckServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductHeadServer;
	}

	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	private IPlmProductSupplierInfoEco plmProductSupplierInfoEcoServer;

	@Autowired
	private IPlmProductHeadEco plmProductHeadEco;

	@Autowired
	private IPlmProductPriceEco  plmProductPriceEco ;

	@Autowired
	private IPlmDataAclHeader plmDataAclHeaderServer;// 权限表
	@Autowired
	private IPlmDataAclLine IPlmDataAclLineServer; // 权限行表

	@Override
	public String findPagination(String params, Integer pageIndex, Integer pageRows) {
		return super.findPagination(params, pageIndex, pageRows);
	}

	/**
	 * 2018/08/30
	 * 
	 * @Title:
	 * @Description: TODO(商品保存方法)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveProductInfo")
	public String saveProductInfo(@RequestParam(required = false) String params) {

		JSONObject queryParamJSON = parseObject(params);
		queryParamJSON.put("operatorUserId", this.getSessionUserId());
		List<String> vendcodes = this.getUserSessionBean().getVendorCodes();
		if (vendcodes.size() >= 1) {
			String vendcode = vendcodes.get(0);
			queryParamJSON.put("vendcode", vendcode);
		}
		// plmProductBarcodeServer.saveAll(arg0);
		try {
			JSONObject jo = queryParamJSON.getJSONObject("headInfo");

			if (jo.containsKey("productHeadId")) { // 核对versionNum避免操作失误
				PlmProductHeadEntity_HI oldhi = plmProductHeadServer.getById(jo
						.getInteger("productHeadId"));
				if(jo.containsKey("buttonStatus")) {
					//判断权限
					String userType = this.getUserSessionBean().getUserType();
                    if(!"60".equals(userType) && !"66".equals(userType)) {
					    Integer curentorgId = this.getUserSessionBean().getOrgId();
//
							String deptment=oldhi.getDepartment();
							JSONObject deptparam = new JSONObject();
							deptparam.put("deptId", curentorgId);
							List<PlmDataAclHeaderEntity_HI> headli = plmDataAclHeaderServer
									.findList(deptparam);
							boolean newflag=false;
							boolean modifyflag=false;
							if (headli.size() > 0) {
								PlmDataAclHeaderEntity_HI headinfo = headli.get(0);
								Integer headid = headinfo.getHeadId();

								JSONObject lineparam = new JSONObject();
								lineparam.put("headId", headid);
								lineparam.put("enableFlag", "Y");
								List<PlmDataAclLineEntity_HI> linedata = IPlmDataAclLineServer
										.findList(lineparam);
								for (PlmDataAclLineEntity_HI line : linedata) {
									String groupCode = line.getGroupCode();
									String acltype = line.getAclType();
									if(acltype.equals("NEW")) {

										if (groupCode.length() == 1 || groupCode.length() == 2) {
											if (deptment.substring(0, 1).equals(groupCode)) {
												newflag = true;
											}

										}else
										{
											if(deptment.equals(groupCode))
											{
												newflag=true;
											}
										}
									}
									else if(acltype.equals("UPDATE")){
                                      if(groupCode.equals("PUBLIC"))
									  {
										  modifyflag=true;
									  }else
									  {
										  if(oldhi.getUserDept()!=null) {
											  Integer departmentNo = new Integer(oldhi.getUserDept());
											  if (departmentNo.equals(curentorgId)) {
												  modifyflag=true;
						                    }
										  }

									  }
									}

						           }
					}
                      if(newflag==false){
						  throw new ServerException("当前用户所新增的部门不在权限范围内!");
					  }
                      if(modifyflag==false){
						  throw new ServerException("当前用户没有该单据修改权限,无法保存或提交!");
					  }
                    }

					String oldorders = oldhi.getOrderStatus();
					String savetype = jo.getString("buttonStatus");
					if (!savetype.equals("submit")) //保存
					{
                     if(oldorders.equals("4")|| oldorders.equals("5")||
							 oldorders.equals("6")||oldorders.equals("8")||oldorders.equals("11")){
						 throw new ServerException("当前商品状态不允许保存!");
					 }
					} else { //提交
						if(!oldorders.equals("3")&&!oldorders.equals("7")&&!oldorders.equals("10")&&!oldorders.equals("1")&&
								!oldorders.equals("2")){
							throw new ServerException("当前商品状态不允许提交!");
						}
					}
				}

				jo.remove("versionNum");
				jo.put("versionNum", oldhi.getVersionNum());
			} else // 新增操作
			{
				queryParamJSON.put("createdEname", this.getUserSessionBean()
						.getNameEnglish());
				queryParamJSON.put("createdEmp", this.getUserSessionBean()
						.getEmployeeNumber());

				Integer deptId = this.getUserSessionBean().getOrgId();
				String deptName = this.getUserSessionBean().getOrgName();

				queryParamJSON.put("userDept", deptId);
				queryParamJSON.put("userGroupcode", deptName);

			}
			String orderstatus = jo.getString("orderStatus");
			String buttonStatus = jo.getString("buttonStatus");

			// 验证barcodetable
			// if (orderstatus.equals("1") || orderstatus.equals("2")) {
			JSONArray barcodelist = null;
			if (queryParamJSON.containsKey("barcodelist")) {
				barcodelist = queryParamJSON.getJSONArray("barcodelist");// 条码行信息
			}
			for (int i = 0; i < barcodelist.size(); i++) {
				JSONObject barobj = barcodelist.getJSONObject(i);

				if (barobj.containsKey("barcodeId")) { // 修改
					if (!barobj.containsKey("barcode")) {
						continue;
					} else {
						String barcode = barobj.getString("barcode");
						String barcodeType = barobj.getString("barcodeType");
						if (barcodeType.equals("6")
								&& barcode.equals("88888888")) {
							continue;
						}
					}

					Integer barcodeId = barobj.getInteger("barcodeId");
					PlmProductBarcodeTableEntity_HI barobject = plmProductBarcodeTableDao
							.getById(barcodeId);
					if (!barobject.getBarcode().equals(
							barobj.getString("barcode"))) {

						JSONObject ma = new JSONObject();
						ma.put("barcode", barobj.getString("barcode"));
						List<PlmProductBarcodeTableEntity_HI> barli = new ArrayList<PlmProductBarcodeTableEntity_HI>();
						try {
							if (!barobj.getString("barcode").equals("88888888")) {
								barli = plmProductBarcodeTableDao.findList(ma);
							}
						} catch (Exception e) {
							throw new ServerException("条形码序号'" + (i + 1)
									+ "',格式错误!");
						}

						if (barli.size() > 0) {
							boolean isexis = false;
							for (PlmProductBarcodeTableEntity_HI ba : barli) {
								Integer headid = ba.getProductHeadId();
								PlmProductHeadEntity_HI proold = plmProductHeadServer
										.getById(headid);
								if (!proold.getOrderStatus().equals("5")) {
									isexis = true;
									break;
								}
							}
							if (isexis) {
								throw new ServerException("条形码序号'" + (i + 1)
										+ "',条码已经存在!");
							}
						} else {
							boolean f = this.isBarcode(
									barobj.getString("barcodeType"),
									barobj.getString("barcode"));
							if (f == false) {
								throw new ServerException("条形码序号'" + (i + 1)
										+ "',格式错误!");
							}
						}

					}
				} else // 新增
				{
					if (!barobj.containsKey("barcode")) {
						continue;
					} else {
						String barcode = barobj.getString("barcode");
						String barcodeType = barobj.getString("barcodeType");
						if (barcodeType.equals("6")
								&& barcode.equals("88888888")) {
							continue;
						}
					}

					JSONObject ma = new JSONObject();
					ma.put("barcode", barobj.getString("barcode"));
					List<PlmProductBarcodeTableEntity_HI> barli = new ArrayList<PlmProductBarcodeTableEntity_HI>();
					try {
						if (!barobj.getString("barcode").equals("88888888")) {
							barli = plmProductBarcodeTableDao.findList(ma);
						}
					} catch (Exception e) {

						throw new ServerException("条形码序号'" + (i + 1)
								+ "',格式错误!");
					}

					if (barli.size() > 0) {

						boolean isexis = false;
						for (PlmProductBarcodeTableEntity_HI ba : barli) {
							Integer headid = ba.getProductHeadId();
							PlmProductHeadEntity_HI proold = plmProductHeadServer
									.getById(headid);
							if (!proold.getOrderStatus().equals("5")) {
								isexis = true;
								break;
							}
						}
						if (isexis) {
							throw new ServerException("条形码序号'" + (i + 1)
									+ "',条码已经存在!");
						}

					} else {
						boolean f = this.isBarcode(
								barobj.getString("barcodeType"),
								barobj.getString("barcode"));
						if (f == false) {
							throw new ServerException("条形码序号'" + (i + 1)
									+ "',格式错误!");
						}
					}

				}
			}
			// } // if

			JSONArray store = null;
			if (queryParamJSON.containsKey("storelist")) {
				store = queryParamJSON.getJSONArray("storelist");
			}

			if (store != null) {
				for (int i = 0; i < store.size(); i++) {
					JSONObject storeobj = store.getJSONObject(i);
					if (storeobj.containsKey("storeId")) {
						Integer storeId = storeobj.getInteger("storeId");
						PlmProductStoreEntity_HI oldobj = plmProductStoreServer
								.getById(storeId);
						Integer versionNum = oldobj.getVersionNum();
						storeobj.put("versionNum", versionNum);
					}

				}
			}

			// 校验生效方式

			if (!orderstatus.equals("1") && !orderstatus.equals("2")) {
				JSONArray supplierlist = queryParamJSON
						.getJSONArray("supplierlist");// 供应商行信息
				if (supplierlist != null) {
					String msg = this.getCheckWareShop(supplierlist);
					if (!msg.equals("1")) {
						// throw new ServerException(msg);
						return SToolUtils
								.convertResultJSONObj("F", msg, 1, msg)
								.toString();
					}
				}
				// getCheckStoreShop
				if (store != null) {
					String msg = this.getCheckStoreShop(store);
					if (!msg.equals("1")) {
						return SToolUtils
								.convertResultJSONObj("F", msg, 1, msg)
								.toString();
					}
				}
			}
			
			JSONObject result = plmProductHeadServer
					.saveProductInfo(queryParamJSON);

			return result.toString();
		} catch (Exception e) {
			// return SToolUtils.convertResultJSONObj(ERROR_STATUS,
			// e.getMessage(), 1, e.getMessage()).toString();
			return getException(e, LOGGER);
		}
	}


	// 校验供应商生效方式
	public String getCheckWareShop(JSONArray supplierlist)
			throws NumberFormatException, Exception {
		boolean all = false;
		boolean allshop = false;
		Integer allshopline = null;
		String msg = "";

		Map<String, Integer> storemap = new HashMap<String, Integer>();
		Map<String, Integer> Warehousemap = new HashMap<String, Integer>();
		Map<String, Integer> placemap = new HashMap<String, Integer>();
		Map<String, Integer> areamap = new HashMap<String, Integer>();
		Map<String, Integer> AllData = new HashMap<String, Integer>(); // 判断所有行的店铺

		List<String> redata = new ArrayList<String>(); // 判断所有行的店铺冲突
		Map<String, List<String>> remap = new HashMap<String, List<String>>();
		for (int i = 0; i < supplierlist.size(); i++) {
			JSONObject supplierobj = supplierlist.getJSONObject(i);
			if (!supplierobj.containsKey("sxWay")) {
				continue;
			}
			String sxWay = supplierobj.getString("sxWay");
			if (sxWay.equals("1")) {
				all = true;
			}
			if (sxWay.equals("2")) {
				allshop = true;
				allshopline = i + 1;
			}
		}
		if (all && supplierlist.size() > 1) {
			return "供应商信息,只有一个供应商的情况下，才能选择 '全部仓和店'的生效方式!";
		} else if (all && supplierlist.size() == 1) {
			return "1";
		}

		// 校验不同行的 区域，地点清单是否重复
		for (int i = 0; i < supplierlist.size(); i++) { //

			JSONObject supplierobj = supplierlist.getJSONObject(i);
			if (!supplierobj.containsKey("sxWay")) {
				continue;
			}
			String sxWay = supplierobj.getString("sxWay");
			if (sxWay.equals("2")) { // 全部店铺
				if (allshop && allshopline != null && allshopline != (i + 1)) {
					return "供应商信息第'" + (i + 1)
							+ "'行,已经有生效方式为 '全部店铺'的供应商,请选择其它生效方式。";
				} else {
					allshop = true;
				}
			} else if (sxWay.equals("4")) { // 指定仓+店
				if (allshop) {
					return "供应商信息第'" + (i + 1)
							+ "'行,已经有生效方式为 '全部店铺'的供应商,请选择其它生效方式。";
				}
				if (!supplierobj.containsKey("sxWarehouseId")
						|| !supplierobj.containsKey("sxStore")) {
					continue;
				}
				String shop = supplierobj.getString("sxStore"); // 生效门店
				String sxWarehouse = supplierobj.getString("sxWarehouseId"); // 生效仓库
				if (shop != null && !shop.equals("")) {
					for (String s : shop.split(",")) {
						if (storemap.containsKey(s)) {
							return "供应商信息第'" + (i + 1) + "'行生效方式,与第'"
									+ storemap.get(s) + "'行生效方式,存在相同'店铺'" + s
									+ ",请选择其它店铺或生效方式!";
						} else {
							storemap.put(s, i + 1);
						}
					}
				}
				// 仓库
				if (sxWarehouse != null && !sxWarehouse.equals("")) {
					for (String s : sxWarehouse.split(",")) {
						if (Warehousemap.containsKey(s)) {
							return "供应商信息第'" + (i + 1) + "'行生效方式,与第'"
									+ Warehousemap.get(s) + "'行生效方式,存在相同'仓库' "
									+ s + ",请选择其它仓库或生效方式!";
						} else {
							Warehousemap.put(s, i + 1);
						}
					}
				}

			} else if (sxWay.equals("3")) { // 地点清单
				if (allshop) {
					return "供应商信息第'" + (i + 1)
							+ "'行,已经有生效方式为 '全部店铺'的供应商,请选择其它生效方式。";
				}

				String placecode = supplierobj.getString("placeCode");
				if (placemap.containsKey(placecode)) {
					return "供应商信息第'" + (i + 1) + "'行生效方式,与第'"
							+ placemap.get(placecode) + "'行生效方式,存在相同'地点清单'"
							+ placecode + ",请选择其它地点清单或生效方式!";
				} else {
					placemap.put(placecode, i + 1);
				}
			} else if (sxWay.equals("7")) { // 指定店铺

				if (allshop) {
					return "供应商信息第'" + (i + 1)
							+ "'行,已经有生效方式为 '全部店铺'的供应商,请选择其它生效方式。";
				}
			} else if (sxWay.equals("5")) // 区域
			{
				if (allshop) {
					return "供应商信息第'" + (i + 1) + "'行,已经有'全部店铺'的供应商,请选择其它生效方式!";
				}
				if (!supplierobj.containsKey("areaId")) {
					continue;
				}
				String area = supplierobj.getString("areaId");
				if (area != null && !area.equals("")) {
					for (String s : area.split(",")) {
						if (areamap.containsKey(s)) {
							return "供应商信息第'" + (i + 1) + "'行生效方式,与第'"
									+ areamap.get(s) + "'行生效方式,存在相同'区域'" + s
									+ ",请选择其它区域或生效方式!";
						} else {
							areamap.put(s, i + 1);
						}
					}
				}
			} else if (sxWay.equals("6")) // 指定仓
			{
				if (!supplierobj.containsKey("sxWarehouseId")) {
					continue;
				}
				String sxWarehouse = supplierobj.getString("sxWarehouseId"); // 生效仓库
				// 仓库
				if (sxWarehouse != null && !sxWarehouse.equals("")) {
					for (String s : sxWarehouse.split(",")) {
						if (Warehousemap.containsKey(s)) {
							return "供应商信息第'" + (i + 1) + "'行生效方式,与第'"
									+ Warehousemap.get(s) + "'行生效方式,存在相同仓库'"
									+ s + "',请选择其它仓库或生效方式!";
						} else {
							Warehousemap.put(s, i + 1);
						}
					}
				}

			} else if (sxWay.equals("8")) { // 指定仓+地点清单
				if (allshop) {
					return "供应商信息第'" + (i + 1) + "'行,已经有'全部店铺'的供应商,请选择其它生效方式!";
				}
				if (!supplierobj.containsKey("sxWarehouseId")) {
					continue;
				}
				String sxWarehouse = supplierobj.getString("sxWarehouseId"); // 生效仓库
				if (sxWarehouse != null && !sxWarehouse.equals("")) {
					for (String s : sxWarehouse.split(",")) {
						if (Warehousemap.containsKey(s)) {

							return "供应商信息第'" + (i + 1) + "'行生效方式,与第'"
									+ Warehousemap.get(s) + "'行生效方式,存在相同仓库'"
									+ s + "',请选择其它仓库或生效方式!";
						} else {
							Warehousemap.put(s, i + 1);
						}
					}
				}
				// 地点清单
				if (!supplierobj.containsKey("placeCode")) {
					continue;
				}
				String placecode = supplierobj.getString("placeCode");
				if (placemap.containsKey(placecode)) {
					return "供应商信息第'" + (i + 1) + "'行生效方式,与第'"
							+ placemap.get(placecode) + "'行生效方式,存在相同地点清单'"
							+ placecode + "',请选择其它地点清单或生效方式!";
				} else {
					placemap.put(placecode, i + 1);
				}

			} else if (sxWay.equals("9")) // 仓库及其店铺 带出地点清单
			{
				if (allshop) {
					return "供应商信息第'" + (i + 1)
							+ "'行,已经有生效方式为 '全部店铺'的供应商,请选择其它生效方式。";
				}
				if (!supplierobj.containsKey("sxWarehouseId")) {
					continue;
				}
				String sxWarehouse = supplierobj.getString("sxWarehouseId"); // 生效仓库
				for (String s : sxWarehouse.split(",")) {
					locationMap locmap = new locationMap();
					Map<String, String> mapdata = locmap.getMapData();

					if (mapdata.containsKey(s)) {
						String placecode = mapdata.get(s);
						if (placemap.containsKey(placecode)) {
							return "供应商信息第'" + (i + 1) + "'行生效方式'仓库及其店铺', 仓库号'"
									+ s + "'对应的地点清单,与第'"
									+ placemap.get(placecode)
									+ "'行生效方式,存在相同'地点清单' " + placecode
									+ "',请选择其它仓库或生效方式!";
						} else {
							placemap.put(placecode, i + 1);
						}
					}

				}

			}

			// 如果不重复 校验所有行 爆开的店铺是否有相同店铺
			if (sxWay.equals("3") || sxWay.equals("8")) { // 地点清单
				if (!supplierobj.containsKey("placeCode")) {
					continue;
				}
				String placecode = supplierobj.getString("placeCode");
				JSONObject shop = iPlmApiServer.siteListingMethod(new Integer(
						placecode));
				JSONArray jsonArray = shop.getJSONArray("data_set");
				for (int len = 0; len < jsonArray.size(); len++) {
					JSONObject json = (JSONObject) jsonArray.get(len);
					String loc = json.getString("loc");// 店铺编号
					if (AllData.containsKey(loc)) {
						redata.add(String.valueOf(i + 1) + "&&"
								+ String.valueOf(AllData.get(loc)) + "_" + loc);
					} else {
						AllData.put(loc, i + 1);
					}
				}
				// 5789
			} else if (sxWay.equals("5")) {
				if (!supplierobj.containsKey("areaId")) {
					continue;
				}
				String area2 = supplierobj.getString("areaId");
				String liarea = "";
				for (String achild : area2.split(",")) {
					liarea += "'" + achild + "',";
				}

				JSONObject dataJSON = new JSONObject();
				dataJSON.put("areas", liarea.substring(0, liarea.length() - 1));
				Pagination<VmiShopEntity_HI_RO2> liareas = vimshopServer
						.findShopinfo(dataJSON, 1, 10000);
				List<VmiShopEntity_HI_RO2> shopli = liareas.getData();
				for (VmiShopEntity_HI_RO2 shop : shopli) {
					String shopcode = shop.getVsCode();
					if (AllData.containsKey(shopcode)) {
						redata.add(String.valueOf(i + 1) + "&&"
								+ String.valueOf(AllData.get(shopcode)) + "_"
								+ shopcode);
					} else {
						AllData.put(shopcode, i + 1);
					}
				}

			} else if (sxWay.equals("4") || sxWay.equals("7")) {
				if (!supplierobj.containsKey("sxStore")) {
					continue;
				}
				String shops = supplierobj.getString("sxStore");

				String loclist = "";
				for (String shop : shops.split(",")) {
					loclist += "'" + shop + "',";
				}
				JSONObject shopj = new JSONObject();
				shopj.put("vscodes", loclist.substring(0, loclist.length() - 1));
				Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
						.findShopinfo(shopj, 1, 10000);
				List<VmiShopEntity_HI_RO2> shopli = liarea.getData();
				for (VmiShopEntity_HI_RO2 shop : shopli) {
					String shopcode = shop.getVsCode();
					if (AllData.containsKey(shopcode)) {
						redata.add(String.valueOf(i + 1) + "&&"
								+ String.valueOf(AllData.get(shopcode)) + "_"
								+ shopcode);
					} else {
						AllData.put(shopcode, i + 1);
					}
				}

			} else if (sxWay.equals("9")) {

				if (!supplierobj.containsKey("sxWarehouseId")) {
					continue;
				}
				String sxWarehouse = supplierobj.getString("sxWarehouseId"); // 生效仓库
				for (String s : sxWarehouse.split(",")) {

					locationMap locmap = new locationMap();
					Map<String, String> mapdata = locmap.getMapData();
					if (mapdata.containsKey(s)) {
						String loccode = mapdata.get(s);
						JSONObject shop = iPlmApiServer
								.siteListingMethod(new Integer(loccode));
						JSONArray jsonArray = shop.getJSONArray("data_set");
						for (int len = 0; len < jsonArray.size(); len++) {
							JSONObject json = (JSONObject) jsonArray.get(len);
							String loc = json.getString("loc");// 店铺编号
							if (AllData.containsKey(loc)) {
								redata.add(String.valueOf(i + 1) + "&&"
										+ String.valueOf(AllData.get(loc))
										+ "_" + loc);
							} else {
								AllData.put(loc, i + 1);
							}
						}
					}
				}
			}

		} // for

		if (redata.size() > 0) {
			for (int i = 0; i < redata.size(); i++) {
				String pref = redata.get(i);
				String index = pref.split("_")[0];
				String value = pref.split("_")[1];
				if (remap.containsKey(index)) {
					List<String> result = remap.get(index);
					result.add(value);
					remap.remove(index);
					remap.put(index, result);
				} else {
					List<String> result = new ArrayList<String>();
					result.add(value);
					remap.put(index, result);
				}

			}

			for (Map.Entry<String, List<String>> rec : remap.entrySet()) {
				String line = rec.getKey();
				if (line.split("&&")[0] == line.split("&&")[1]) {
					msg += "供应商信息 第'" + line.split("&&")[0] + "'行,生效方式,存在冲突店铺:";
				} else {
					msg += "供应商信息 第'" + line.split("&&")[0] + "'行,与" + "第'"
							+ line.split("&&")[1] + "'行,生效方式,存在冲突店铺:";
				}
				List<String> values = rec.getValue();
				String loclist = "";
				for (int i = 0; i < values.size(); i++) {

					loclist += "'" + values.get(i) + "',";
				}
				msg += loclist.substring(0, loclist.length() - 1);
				msg += "。\r\n";
			}

		}

		if (msg.equals("")) {
			msg = "1";
		}
		return msg;
	}

	// 获得地点清单下的店铺

	// 获得

	// 检验代销生效地点
	public String getCheckStoreShop(JSONArray storelist)
			throws NumberFormatException, Exception {
		// 1.地点清单 。2.区域。3.指定店铺
		Map<String, Integer> Allmap = new HashMap<String, Integer>(); // 总店铺
		List<String> redata = new ArrayList<String>();
		Map<String, List<String>> remap = new HashMap<String, List<String>>();

		Map<String, Integer> placeMap = new HashMap<String, Integer>();
		Map<String, Integer> areaMap = new HashMap<String, Integer>();
		Map<String, Integer> supplierMap = new HashMap<String, Integer>(); // 全部店铺的供应商
																			// map集
		for (int i = 0; i < storelist.size(); i++) {
			JSONObject storeobj = storelist.getJSONObject(i);
			if (!storeobj.containsKey("sxWay")
					|| !storeobj.containsKey("supplierId")) {
				continue;
			} else {
				String sxWay = storeobj.getString("sxWay");
				if (sxWay.equals("4")) {
					String supplierId = storeobj.getString("supplierId");
					String key = supplierId + "_4";
					if (supplierMap.containsKey(key)) {
						return "代销:第" + (i + 1) + "行,供应商Id'" + supplierId
								+ "',已经有生效方式为[全部店铺]的选项!";
					} else {
						supplierMap.put(key, i);
					}
				}
			}
		}

		for (int i = 0; i < storelist.size(); i++) { //
			JSONObject storeobj = storelist.getJSONObject(i);
			if (!storeobj.containsKey("sxWay")
					|| !storeobj.containsKey("supplierId")) {
				continue;
			}
			String supplierId = storeobj.getString("supplierId");
			String sxWay = storeobj.getString("sxWay");
			if (sxWay.equals("1")) // 地点清单
			{

				if (supplierMap.containsKey(supplierId + "_4")) {
					return "代销:第" + (i + 1) + "行,供应商Id'" + supplierId
							+ "',存在生效方式为[全部店铺]的选项,不能再有其他生效方式!";
				}
				if (!storeobj.containsKey("supplierPlaceCode")) {
					continue;
				}
				String placeCode = storeobj.getString("supplierPlaceCode");
				if (placeMap.containsKey(placeCode)) {
					return "代销:第" + (i + 1) + "行,与第" + placeMap.get(placeCode)
							+ "行,存在相同地点清单'" + placeCode + "',请选择其它生效方式!";
				} else {
					placeMap.put(placeCode, i + 1);
				}
			} else if (sxWay.equals("2")) // 区域
			{
				if (supplierMap.containsKey(supplierId + "_4")) {
					return "代销:第" + (i + 1) + "行,供应商Id'" + supplierId
							+ "',存在生效方式为[全部店铺]的选项,不能再有其他生效方式!";
				}

				if (!storeobj.containsKey("areaId")) {
					continue;
				}

				String area = storeobj.getString("areaId");
				for (String a : area.split(",")) {
					if (areaMap.containsKey(a)) {
						return "代销:第" + (i + 1) + "行,与第" + areaMap.get(a)
								+ "行,存在相同区域'" + area + "',请选择其它生效方式!";
					} else {
						areaMap.put(a, i + 1);
					}
				}
			} else if (sxWay.equals("4")) { // 全部店铺
				if (!storeobj.containsKey("supplierId")) {
					continue;
				}
				if (supplierMap.containsKey(supplierId + "_4")) {
					Integer line = supplierMap.get(supplierId + "_4");
					if (line != i) {
						return "代销:第" + (i + 1) + "行,供应商Id'" + supplierId
								+ "',已经存在生效方式为[全部店铺]的选项!";
					}
				}
			}

			// 不存在相同 区域,地点清单,店铺的情况。需要将各种生效方式对应的店铺查出，然后校验是否冲突
			if (sxWay.equals("1")) // 获取地点清单下的所有店铺
			{
				if (!storeobj.containsKey("supplierPlaceCode")) {
					continue;
				}

				String placeCode = storeobj.getString("supplierPlaceCode");
				JSONObject shop = iPlmApiServer.siteListingMethod(new Integer(
						placeCode));

				JSONArray jsonArray = shop.getJSONArray("data_set");
				System.out.println(jsonArray.size());
				for (int len = 0; len < jsonArray.size(); len++) {
					JSONObject json = (JSONObject) jsonArray.get(len);
					String loc = json.getString("loc");// 店铺编号
					if (Allmap.containsKey(loc)) {
						redata.add(String.valueOf(i + 1) + "&&"
								+ String.valueOf(Allmap.get(loc)) + "_" + loc);
					} else {
						Allmap.put(loc, i + 1);
					}
				}
			} else if (sxWay.equals("2")) // 区域
			{
				if (!storeobj.containsKey("areaId")) {
					continue;
				}
				String area = storeobj.getString("areaId");
				String liarea = "";
				for (String achild : area.split(",")) {
					liarea += "'" + achild + "',";
				}

				JSONObject dataJSON = new JSONObject();
				dataJSON.put("areas", liarea.substring(0, liarea.length() - 1));
				Pagination<VmiShopEntity_HI_RO2> liareas = vimshopServer
						.findShopinfo(dataJSON, 1, 10000);
				List<VmiShopEntity_HI_RO2> shopli = liareas.getData();
				for (VmiShopEntity_HI_RO2 shop : shopli) {
					String shopcode = shop.getVsCode();
					if (Allmap.containsKey(shopcode)) {
						redata.add(String.valueOf(i + 1) + "&&"
								+ String.valueOf(Allmap.get(shopcode)) + "_"
								+ shopcode);
					} else {
						Allmap.put(shopcode, i + 1);
					}
				}

			} else if (sxWay.equals("3")) // 店铺
			{
				if (!storeobj.containsKey("sxStoreId")) {
					continue;
				}
				String shops = storeobj.getString("sxStoreId");

				for (String shop : shops.split(",")) {

					if (Allmap.containsKey(shop)) {
						redata.add(String.valueOf(i + 1) + "&&"
								+ String.valueOf(Allmap.get(shop)) + "_" + shop);
					} else {
						Allmap.put(shop, i + 1);
					}
				}

			}

		}
		String msg = "";
		if (redata.size() > 0) {
			for (int i = 0; i < redata.size(); i++) {
				String pref = redata.get(i);
				String index = pref.split("_")[0];
				String value = pref.split("_")[1];
				if (remap.containsKey(index)) {
					List<String> result = remap.get(index);
					result.add(value);
					remap.remove(index);
					remap.put(index, result);
				} else {
					List<String> result = new ArrayList<String>();
					result.add(value);
					remap.put(index, result);
				}

			}

			for (Map.Entry<String, List<String>> rec : remap.entrySet()) {
				String line = rec.getKey();
				if (line.split("&&")[0] == line.split("&&")[1]) {
					msg += "代销 第'" + line.split("&&")[0] + "'行,生效方式,存在冲突店铺:";
				} else {
					msg += "代销 第'" + line.split("&&")[0] + "'行,与" + "第'"
							+ line.split("&&")[1] + "'行,生效方式,存在冲突店铺:";
				}
				List<String> values = rec.getValue();
				String loclist = "";
				for (int i = 0; i < values.size(); i++) {

					loclist += "'" + values.get(i) + "',";
				}
				msg += loclist.substring(0, loclist.length() - 1);
				msg += "。\r\n";
			}

		}

		if (msg.equals("")) {
			msg = "1";
		}
		return msg;
	}

	// 保存供应商生效门店

	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierShop")
	public String saveSupplierShop(@RequestParam(required = false) String params) {

		try {
			JSONObject dataJSON = parseObject(params);
			JSONArray supplierlist = dataJSON.getJSONArray("supplierlist");
			Integer headid = dataJSON.getInteger("headid");
			String deleteSql = " delete from PlmProductSupplierplaceinfoEntity_HI where product_head_id='"
					+ String.valueOf(headid) + "' ";

			// 删除旧的供应商店铺信息
			plmProductHeadServer.execute(deleteSql);
			for (int i = 0; i < supplierlist.size(); i++) { //
				JSONObject supplierobj = supplierlist.getJSONObject(i);
				if (!supplierobj.containsKey("sxWay")) {
					continue;
				}
				insertData(supplierobj, headid);
			}
		} catch (Exception e) {
			return getException(e, LOGGER);
		}
		return "success";
	}

	// 保存代销
	@RequestMapping(method = RequestMethod.POST, value = "saveStoreShop")
	public String saveStoreShop(@RequestParam(required = false) String params)
			throws NumberFormatException, Exception {
		Map<String, JSONObject> sumap = new HashMap<String, JSONObject>();

		JSONObject dataJSON = parseObject(params);
		JSONArray supplierlist = dataJSON.getJSONArray("supplierlist");
		for (int i = 0; i < supplierlist.size(); i++) {
			JSONObject dat = supplierlist.getJSONObject(i);
			if (!dat.containsKey("supplierCode")) {
				continue;
			}
			sumap.put(dat.getString("supplierCode"), dat);
		}

		JSONArray storelist = dataJSON.getJSONArray("storelist");
		Integer headid = dataJSON.getInteger("headid");
		PlmProductHeadEntity_HI headobj = plmProductHeadServer.getById(headid);
		String deleteSql = " delete from PlmProductConsaleinfoEntity_HI where request_id='"
				+ headobj.getPlmCode() + "'";

		plmProductHeadServer.execute(deleteSql);

		for (int i = 0; i < storelist.size(); i++) { //
			JSONObject storeobj = storelist.getJSONObject(i);
			if (!storeobj.containsKey("sxWay")) {
				continue;
			}

			if (storeobj.containsKey("storeId")) { // 修改
				Integer id = storeobj.getInteger("storeId");
				PlmProductStoreEntity_HI oldsuobj = plmProductStore.getById(id);

				insertStoreData(storeobj, headobj, oldsuobj, sumap);

			} else // 新增
			{
				PlmProductStoreEntity_HI entity = JSON.parseObject(
						storeobj.toString(), PlmProductStoreEntity_HI.class);
				insertStoreData(storeobj, headobj, entity, sumap);
			}

		}

		return "success";
	}

	public void insertStoreData(JSONObject data,
			PlmProductHeadEntity_HI headobj, PlmProductStoreEntity_HI oldsuobj,
			Map<String, JSONObject> sudata) throws NumberFormatException,
			Exception {
		JSONObject pa = new JSONObject();
		List<PlmProductConsaleinfoEntity_HI> ldata = new ArrayList<PlmProductConsaleinfoEntity_HI>();
		String sxWay = data.getString("sxWay");
		String suppliercode = data.getString("supplierId");
		JSONObject suobj = sudata.get(suppliercode);
		pa.put("plmCode", headobj.getPlmCode());
		pa.put("supplierCode", suppliercode);
		pa.put("uid", this.getSessionUserId());
		pa.put("purchaseType", headobj.getPurchaseType());
		if (sxWay.equals("1")) { // 地点清单
			// 地点清单
			if (!data.containsKey("placeCode")) {
				return;
			}
			String placenote = data.getString("placeCode");
			try {
				StoreplaceDeal(pa, placenote, oldsuobj);
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else if (sxWay.equals("2"))// 区域
		{
			if (!data.containsKey("areaId")) {
				return;
			}

			String area = data.getString("areaId");
			if (!suobj.containsKey("sxWay")) {
				return;
			}
			String arelist = "";
			for (String achild : area.split(",")) {
				arelist += "'" + achild + "',";
			}
			String sxway = suobj.getString("sxWay"); // 供应商生效方式
			if (sxway.equals("1") || sxway.equals("2") || sxway.equals("5")) { // 全部店铺

				JSONObject jo = new JSONObject();
				jo.put("areas", arelist.substring(0, arelist.length() - 1));
				Pagination<VmiShopEntity_HI_RO2> vp = vimshopServer
						.findShopinfo(jo, 1, 10000);
				StoreShopDeal(vp.getData(), pa, oldsuobj);

			}

			else if (sxway.equals("3") || sxway.equals("8")) {
				// 获取地点清单
				String placeCode = suobj.getString("placeCode"); // 供应商地点清单
				JSONObject shop = iPlmApiServer.siteListingMethod(new Integer(
						placeCode));
				JSONArray jsonArray = shop.getJSONArray("data_set");
				String loclist = "";

				for (int len = 0; len < jsonArray.size(); len++) {
					JSONObject j = jsonArray.getJSONObject(len);
					String shopno = j.getString("loc");
					loclist += "'" + shopno + "',";
				}

				JSONObject ja = new JSONObject();
				String areas = arelist.substring(0, arelist.length() - 1);
				String shops = loclist.substring(0, loclist.length() - 1);
				ja.put("areas", areas);
				ja.put("vscodes", shops);
				Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
						.findShopinfo(ja, 1, 10000);

				StoreShopDeal(liarea.getData(), pa, oldsuobj); // 店铺处理
			} else if (sxway.equals("4") || sxway.equals("7")) { //
				if (!suobj.containsKey("sxStore")) {
					return;
				}
				String store = suobj.getString("sxStore");
				String loclist = "";
				for (String shop : store.split(",")) {
					loclist += "'" + shop + "',";
				}

				JSONObject ja = new JSONObject();
				String areas = arelist.substring(0, arelist.length() - 1);
				String shops = loclist.substring(0, loclist.length() - 1);
				ja.put("areas", areas);
				ja.put("vscodes", shops);

				Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
						.findShopinfo(ja, 1, 10000);

				List<VmiShopEntity_HI_RO2> li = liarea.getData();
				StoreShopDeal(li, pa, oldsuobj); // 店铺处理
			} else if (sxWay.equals("9")) {
				if (!suobj.containsKey("sxWarehouseId")) {
					return;
				}

				String sxWarehouse = suobj.getString("sxWarehouseId");
				for (String ware : sxWarehouse.split(",")) {
					locationMap loc = new locationMap();
					Map<String, String> locmap = loc.getMapData();
					if (locmap.containsKey(ware)) {
						String placeCode = locmap.get(ware);
						JSONObject shop = iPlmApiServer
								.siteListingMethod(new Integer(placeCode));
						JSONArray jsonArray = shop.getJSONArray("data_set");
						String loclist = "";

						for (int len = 0; len < jsonArray.size(); len++) {
							JSONObject j = jsonArray.getJSONObject(len);
							String shopno = j.getString("loc");
							loclist += "'" + shopno + "',";
						}

						List<VmiShopEntity_HI2> vhi = new ArrayList<VmiShopEntity_HI2>();

						JSONObject ja = new JSONObject();
						String areas = arelist.substring(0,
								arelist.length() - 1);
						String shops = loclist.substring(0,
								loclist.length() - 1);
						ja.put("areas", areas);
						ja.put("vscodes", shops);
						Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
								.findShopinfo(ja, 1, 10000);
						List<VmiShopEntity_HI_RO2> li = liarea.getData();
						for (VmiShopEntity_HI_RO2 s : li) {
							VmiShopEntity_HI2 vh = new VmiShopEntity_HI2();
							vh.setVsCode(s.getVsCode());
							vh.setVsName(s.getVsName());
							vhi.add(vh);
						}

					}
				}// ware
			}

		} else if (sxWay.equals("3")) { // 店铺
			if (!data.containsKey("sxStoreId")) {
				return;
			}
			String shops = data.getString("sxStoreId");
			for (String shop : shops.split(",")) {
				String loc = shop;
				PlmProductConsaleinfoEntity_HI console = new PlmProductConsaleinfoEntity_HI();
				console.setLoc(loc);
				console.setRequestId(headobj.getPlmCode());
				console.setStartDate(oldsuobj.getStartDate());
				console.setEndDate(oldsuobj.getEndDate());
				console.setSupplier(oldsuobj.getSupplierId());
				// oldsuobj
				console.setStoreId(oldsuobj.getStoreId());
				if (headobj.getPurchaseType().equals("2")) // cvw
				{
					console.setCurrencyCode("CNY");
					console.setExchangeRate("1");
					console.setAdjBasis(oldsuobj.getSubstituteType());
					console.setAdjValue(oldsuobj.getSubstitutePropetion());
				} else {
					console.setLocType(oldsuobj.getSxWay());
					console.setConsimentType(oldsuobj.getSubstituteType());
					console.setConsignmentRate(oldsuobj
							.getSubstitutePropetion());
				}
				console.setCreatedBy(this.getSessionUserId());
				console.setOperatorUserId(this.getSessionUserId());
				console.setCreationDate(new Date());
				console.setLastUpdatedBy(this.getSessionUserId());
				console.setLastUpdateLogin(this.getSessionUserId());
				console.setVersionNum(0);
				console.setLastUpdateDate(new Date());
				ldata.add(console);
			}
		} else if (sxWay.equals("4")) // 全部店铺选项
		{
			if (!suppliercode.equals("") && suppliercode != null) {
				JSONObject sj = sudata.get(suppliercode);
				String sxway = sj.getString("sxWay");
				if (sxway.equals("1") || sxway.equals("2")) { // 全部仓+店铺
					JSONObject jo = new JSONObject();
					Pagination<VmiShopEntity_HI_RO2> vshop = vimshopServer
							.findShopinfo(jo, 1, 10000);
					StoreShopDeal(vshop.getData(), pa, oldsuobj);
				} else if (sxway.equals("3") || sxway.equals("8")) { // 地点清单
					String placecode = sj.getString("placeCode");
					StoreplaceDeal(pa, placecode, oldsuobj);
				} else if (sxway.equals("4") || sxway.equals("7")) {
					if (!sj.containsKey("sxStore")) {
						return;
					}
					String shops = sj.getString("sxStore");
					for (String shop : shops.split(",")) {
						String loc = shop;
						PlmProductConsaleinfoEntity_HI console = new PlmProductConsaleinfoEntity_HI();
						console.setLoc(loc);
						console.setRequestId(headobj.getPlmCode());
						console.setStartDate(oldsuobj.getStartDate());
						console.setEndDate(oldsuobj.getEndDate());
						console.setSupplier(oldsuobj.getSupplierId());

						if (headobj.getPurchaseType().equals("2")) // cvw
						{
							console.setCurrencyCode("CNY");
							console.setExchangeRate("1");
							console.setAdjBasis(oldsuobj.getSubstituteType());
							console.setAdjValue(oldsuobj
									.getSubstitutePropetion());
						} else {
							console.setLocType(oldsuobj.getSxWay());
							console.setConsimentType(oldsuobj
									.getSubstituteType());
							console.setConsignmentRate(oldsuobj
									.getSubstitutePropetion());
						}
						console.setCreatedBy(this.getSessionUserId());
						console.setOperatorUserId(this.getSessionUserId());
						console.setCreationDate(new Date());
						console.setStoreId(oldsuobj.getStoreId());
						console.setLastUpdatedBy(this.getSessionUserId());
						console.setLastUpdateLogin(this.getSessionUserId());
						console.setVersionNum(0);
						console.setLastUpdateDate(new Date());
						ldata.add(console);
					}

				} // 4-7
				else if (sxway.equals("5")) // 区域
				{
					if (!sj.containsKey("areaId")) {
						return;
					}
					String area = sj.getString("areaId");

					String liarea = "";
					for (String achild : area.split(",")) {
						liarea += "'" + achild + "',";
					}

					JSONObject jo = new JSONObject();
					jo.put("areas", liarea.substring(0, liarea.length() - 1));
					Pagination<VmiShopEntity_HI_RO2> vshop = vimshopServer
							.findShopinfo(jo, 1, 10000);

					StoreShopDeal(vshop.getData(), pa, oldsuobj);

				} else if (sxway.equals("9")) { // 仓库及其店铺
					if (!sj.containsKey("sxWarehouseId")) {
						return;
					}
					String sxWarehouse = sj.getString("sxWarehouseId"); // 生效仓库
					for (String s : sxWarehouse.split(",")) {
						locationMap loc = new locationMap();
						Map<String, String> locmap = loc.getMapData();
						if (locmap.containsKey(s)) {
							String placecode = locmap.get(s);
							StoreplaceDeal(pa, placecode, oldsuobj);
						}

					}
				}

			}
		}
		consaleServer.save(ldata);
	}

	public void insertData(JSONObject data, Integer headid) {
		String sxWay = data.getString("sxWay");
		String suppliercode = data.getString("supplierCode");
		String suppliername = data.getString("supplierName");
		PlmProductHeadEntity_HI headobj = plmProductHeadServer.getById(headid);
		List<PlmProductSupplierplaceinfoEntity_HI> resultdata = new ArrayList<PlmProductSupplierplaceinfoEntity_HI>();

		JSONObject params = new JSONObject();
		params.put("productHeadId", headobj.getProductHeadId());
		params.put("rmsId", headobj.getRmsCode());
		params.put("supplierCode", suppliercode);
		params.put("supplierName", suppliername);
		params.put("uid", this.getSessionUserId());
		params.put("productName", headobj.getProductName());
		if (sxWay.equals("1")) { // 全部仓+店
			JSONObject jo = new JSONObject();

			List<VmiWarehouseEntity_HI2> warehouse = vmiWarehouseServer
					.findList(jo);

			Pagination<VmiShopEntity_HI_RO2> vshop = vimshopServer
					.findShopinfo(jo, 1, 10000);

			ShopDeal(vshop.getData(), params);
			WarehouseDeal(warehouse, params);

		} // 1
		else if (sxWay.equals("2")) // 全部店铺
		{
			JSONObject jo = new JSONObject();
			Pagination<VmiShopEntity_HI_RO2> vshop = vimshopServer
					.findShopinfo(jo, 1, 10000);

			ShopDeal(vshop.getData(), params);
		} // 2
		else if (sxWay.equals("3")) { // 地点清单
			String placenote = data.getString("placeCode");
			try {
				placeDeal(params, placenote);
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}// 3
		else if (sxWay.equals("4")) { // 指定仓+店
			if (!data.containsKey("sxStore")
					|| !data.containsKey("sxWarehouseId")) {
				return;
			}

			String shops = data.getString("sxStore");
			String warehouses = data.getString("sxWarehouseId");
			for (String shop : shops.split(",")) {
				PlmProductSupplierplaceinfoEntity_HI placeinfo = new PlmProductSupplierplaceinfoEntity_HI();
				placeinfo.setSupplierCode(suppliercode);
				placeinfo.setRmsId(headobj.getRmsCode());
				placeinfo.setStatus("1");
				placeinfo.setSupplierName(suppliername);
				placeinfo.setProductHeadId(String.valueOf(headobj
						.getProductHeadId()));
				placeinfo.setProductName(headobj.getProductName());
				placeinfo.setLocType("S");
				placeinfo.setLocation(shop);
				placeinfo.setOperatorUserId(this.getSessionUserId());
				placeinfo.setCreatedBy(this.getSessionUserId());
				placeinfo.setLastUpdatedBy(this.getSessionUserId());
				placeinfo.setLastUpdateLogin(this.getSessionUserId());
				placeinfo.setVersionNum(0);
				placeinfo.setLastUpdateDate(new Date());
				resultdata.add(placeinfo);
			}

			for (String warehouse : warehouses.split(",")) {
				JSONObject jt = new JSONObject();
				jt.put("vhPreCode", warehouse);
				List<VmiWarehouseEntity_HI2> warelist = vmiWarehouseServer
						.findList(jt);
				WarehouseDeal(warelist, params);

			}
		}// 4
		else if (sxWay.equals("5")) { // 区域
			if (!data.containsKey("areaId")) {
				return;
			}
			String area = data.getString("areaId");
			String liarea = "";
			for (String achild : area.split(",")) {
				liarea += "'" + achild + "',";
			}

			JSONObject jo = new JSONObject();
			jo.put("areas", liarea.substring(0, liarea.length() - 1));
			Pagination<VmiShopEntity_HI_RO2> vshop = vimshopServer
					.findShopinfo(jo, 1, 10000);

			ShopDeal(vshop.getData(), params);

		}// 5
		else if (sxWay.equals("6")) { // 指定仓
			if (!data.containsKey("sxWarehouseId")) {
				return;
			}

			String warehouses = data.getString("sxWarehouseId");
			for (String warehouse : warehouses.split(",")) {
				JSONObject jt = new JSONObject();
				jt.put("vhPreCode", warehouse);
				List<VmiWarehouseEntity_HI2> warelist = vmiWarehouseServer
						.findList(jt);
				WarehouseDeal(warelist, params);

			}
		}// 6
		else if (sxWay.equals("7")) // 指定店铺
		{
			if (!data.containsKey("sxStore")) {
				return;
			}

			String shops = data.getString("sxStore");
			for (String shop : shops.split(",")) {
				PlmProductSupplierplaceinfoEntity_HI placeinfo = new PlmProductSupplierplaceinfoEntity_HI();
				placeinfo.setSupplierCode(suppliercode);
				placeinfo.setRmsId(headobj.getRmsCode());
				placeinfo.setStatus("1");
				placeinfo.setSupplierName(suppliername);
				placeinfo.setProductHeadId(String.valueOf(headobj
						.getProductHeadId()));
				placeinfo.setProductName(headobj.getProductName());
				placeinfo.setLocType("S");
				placeinfo.setLocation(shop);
				placeinfo.setOperatorUserId(this.getSessionUserId());
				placeinfo.setCreatedBy(this.getSessionUserId());
				placeinfo.setLastUpdatedBy(this.getSessionUserId());
				placeinfo.setLastUpdateLogin(this.getSessionUserId());
				placeinfo.setVersionNum(0);
				placeinfo.setLastUpdateDate(new Date());
				resultdata.add(placeinfo);
			}
		}// 7
		else if (sxWay.equals("8")) { // 指定仓+地点清单
			if (!data.containsKey("sxWarehouseId")) {
				return;
			}
			String warehouses = data.getString("sxWarehouseId");
			for (String warehouse : warehouses.split(",")) {
				JSONObject jt = new JSONObject();
				jt.put("vhPreCode", warehouse);
				List<VmiWarehouseEntity_HI2> warelist = vmiWarehouseServer
						.findList(jt);
				WarehouseDeal(warelist, params);

			}
			// 地点清单
			String placenote = data.getString("placeCode");
			try {
				placeDeal(params, placenote);
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}// 8
		else if (sxWay.equals("9")) { // 仓库及其店铺
			if (!data.containsKey("sxWarehouseId")) {
				return;
			}
			String warehouses = data.getString("sxWarehouseId");

			for (String ware : warehouses.split(",")) {
				try {

					locationMap lomap = new locationMap();
					Map<String, String> locmap = lomap.getMapData();

					if (locmap.containsKey(ware)) {
						String wcode = locmap.get(ware);
						placeDeal(params, wcode);
					}

				} catch (NumberFormatException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}
		supplierplaceServer.save(resultdata);

	}

	// 处理地点清单
	public void placeDeal(JSONObject params, String wcode)
			throws NumberFormatException, Exception {
		List<JSONObject> datajson = new ArrayList<JSONObject>();
		JSONObject shop = iPlmApiServer.siteListingMethod(new Integer(wcode));
		JSONArray jsonArray = shop.getJSONArray("data_set");
		for (int len = 0; len < jsonArray.size(); len++) {
			JSONObject json = jsonArray.getJSONObject(len);
			if (datajson.size() == 100) {
				InsertTask task = new InsertTask(datajson, params,
						sessionFactory);
				task.run();

				datajson.clear();
				datajson.add(json);

			} else {
				datajson.add(json);
			}
		}
		if (datajson.size() > 0)// 剩余对象 重新发起进程
		{
			InsertTask task = new InsertTask(datajson, params, sessionFactory);
			task.run();
			datajson.clear();
		}
	}

	// 代销处理地点清单
	public void StoreplaceDeal(JSONObject params, String wcode,
			PlmProductStoreEntity_HI obj) throws NumberFormatException,
			Exception {
		List<JSONObject> datajson = new ArrayList<JSONObject>();
		JSONObject shop = iPlmApiServer.siteListingMethod(new Integer(wcode));
		JSONArray jsonArray = shop.getJSONArray("data_set");
		for (int len = 0; len < jsonArray.size(); len++) {
			JSONObject json = jsonArray.getJSONObject(len);
			if (datajson.size() == 100) {
				InsertStore task = new InsertStore(datajson, params,
						sessionFactory, obj);
				task.run();

				datajson.clear();
				datajson.add(json);

			} else {
				datajson.add(json);
			}
		}
		if (datajson.size() > 0)// 剩余对象 重新发起进程
		{
			InsertStore task = new InsertStore(datajson, params,
					sessionFactory, obj);
			task.run();
			datajson.clear();
		}
	}

	// 处理店铺
	public void ShopDeal(List<VmiShopEntity_HI_RO2> vshop, JSONObject params) {
		List<VmiShopEntity_HI_RO2> datajson = new ArrayList<VmiShopEntity_HI_RO2>();
		for (VmiShopEntity_HI_RO2 shop : vshop) {
			if (datajson.size() == 500) { // 分线程处理
				InsertTaskShopInstance task = new InsertTaskShopInstance(
						datajson, params, sessionFactory);
				task.run();

				datajson.clear();
				datajson.add(shop);

			} else {
				datajson.add(shop);
			}
		}
		if (datajson.size() > 0) {
			InsertTaskShopInstance task = new InsertTaskShopInstance(datajson,
					params, sessionFactory);
			task.run();
			datajson.clear();
		}
	}

	// 代销处理店铺
	public void StoreShopDeal(List<VmiShopEntity_HI_RO2> vshop,
			JSONObject params, PlmProductStoreEntity_HI obj) {
		List<VmiShopEntity_HI_RO2> datajson = new ArrayList<VmiShopEntity_HI_RO2>();
		for (VmiShopEntity_HI_RO2 shop : vshop) {
			if (datajson.size() == 500) { // 分线程处理
				InsertStoreShopInstance task = new InsertStoreShopInstance(
						datajson, params, sessionFactory, obj);
				task.run();

				datajson.clear();
				datajson.add(shop);

			} else {
				datajson.add(shop);
			}
		}
		if (datajson.size() > 0) {
			InsertStoreShopInstance task = new InsertStoreShopInstance(
					datajson, params, sessionFactory, obj);
			task.run();
			datajson.clear();
		}
	}

	// 处理仓库方法
	public void WarehouseDeal(List<VmiWarehouseEntity_HI2> warehouse,
			JSONObject params) {
		List<VmiWarehouseEntity_HI2> wdatajson = new ArrayList<VmiWarehouseEntity_HI2>();

		for (VmiWarehouseEntity_HI2 ware : warehouse) {
			String vhcode = ware.getVhCode();
			if (!vhcode.endsWith("1") && !vhcode.endsWith("2")) {
				continue;
			}
			if (wdatajson.size() == 100) { // 分线程处理
				InsertTaskWarehouseInstance task = new InsertTaskWarehouseInstance(
						wdatajson, params, sessionFactory);
				task.run();

				wdatajson.clear();
				wdatajson.add(ware);

			} else {
				wdatajson.add(ware);
			}
		}
		if (wdatajson.size() > 0) {
			InsertTaskWarehouseInstance task = new InsertTaskWarehouseInstance(
					wdatajson, params, sessionFactory);
			task.run();
			wdatajson.clear();
		}

	}

	/**
	 * 2019/09/17
	 * 
	 * @Title:
	 * @Description: TODO(商品修改方法)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateProductInfo")
	public String updateProductInfo(
			@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		queryParamJSON.put("operatorUserId", this.getSessionUserId());
		queryParamJSON.put("deptId", this.getUserSessionBean().getOrgId());
		queryParamJSON.put("deptName", this.getUserSessionBean().getOrgName());
		try {
			JSONObject result = plmProductHeadServer
					.updateProductInfo(queryParamJSON);
			return result.toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}
	}

	/**
	 * 
	 * @Title:
	 * @Description: TODO(根据id查询头信息与行信息)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findProductById")
	public String findProductById(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			List<String> vendcodes = this.getUserSessionBean().getVendorCodes();
			if (vendcodes.size() >= 1) {
				String vendcode = vendcodes.get(0);
				param.put("vendcode", vendcode);
                param.put("vendorCodeList", vendcodes);
			}

			JSONObject Detail = plmProductHeadServer.findProductDetail(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, Detail).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2018/08/30
	 * 
	 * @Title:
	 * @Description: TODO(获取商品列表)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductList")
	public String findProductPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);

			List<String> vendcodes = this.getUserSessionBean().getVendorCodes();
			if (vendcodes.size() >= 1) {
				String vendcode = vendcodes.get(0);
				param.put("vendcode", vendcode);
			}
			Integer deptId = this.getUserSessionBean().getOrgId();

			param.put("userDept", deptId);

			// param.put("userGroupcode", deptname);

			//取出供应商编码
			UserSessionBean userSessionBean = getUserSessionBean();

			param.put("vendorCodeList", userSessionBean.getVendorCodes());
			param.put("userId", userSessionBean.getUserId());
			param.put("userType", userSessionBean.getUserType());

			Pagination<PlmProductHeadEntity_HI_RO> results = plmProductHeadServer
					.findProductList(param, pageIndex, pageRows);

			ResultUtils.getLookUpValue("PLM_PRODUCT_PURCHASE");
			ResultUtils.getLookUpValue("PLM_PRODUCT_STATUS");
			ResultUtils.getLookUpValue("PLM_PRODUCT_ORTHERINFO");
			ResultUtils.getLookUpValue("PLM_PRODUT_ORDERSTATUS");
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2018/08/30
	 * 
	 * @Title:
	 * @Description: TODO(根据id删除商品)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "DeleteProductById")
	public String DeleteProductById(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			plmProductHeadServer.deleteByProductId(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 2018/09/03
	 * 
	 * @Title:
	 * @Description: TODO(批量导入)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "SaveProductByExcel")
	public String SaveProductByExcel(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			param.put("userDept", this.getUserSessionBean().getOrgId());
			param.put("userGroupcode", this.getUserSessionBean().getOrgName());
			param.put("userEmp", this.getUserSessionBean().getEmployeeNumber());
			param.put("userEname", this.getUserSessionBean().getNameEnglish());
			param.put("personName", this.getUserSessionBean().getUserFullName());
			JSONObject result = plmProductHeadServer.SaveProductByExcel(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 2018/10/11
	 * 
	 * @Title:
	 * @Description: TODO(批量修改)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateProductByExcel")
	public String updateProductByExcel(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);

			JSONObject result = plmProductHeadServer
					.updateProductByExcel(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	// 查询是否正在审批中
	/**
	 * 2018/09/19
	 * 
	 * @Title:
	 * @Description: TODO(查询是否正在审批中)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "isupdateproduct")
	public String IsupdateProduct(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			Integer id = param.getInteger("productHeadId");
			PlmProductHeadEntity_HI hi = plmProductHeadServer.getById(id);
			if (hi.getIsUpdatecheck().equals("0")) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
						SUCCESS_MSG, 1, null).toString();
			} else {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,
						"该条单据正在修改审批中!", 1, null).toString();
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	// 调用审批流回调函数
	/**
	 * 2018/09/19
	 * 
	 * @Title:
	 * @Description: TODO()
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateProval")
	public String updateProval(@RequestParam(required = false) String params) {
		try {
			System.out.println(params);
			JSONObject param = parseObject(params);
			String billNo = param.getString("billNo");
			String INCIDENT = param.getString("INCIDENT");
			String VERSION = param.getString("VERSION");
			String taskId = param.getString("TASKID");
			String TASKUSER = param.getString("TASKUSER");
			String allno = param.getString("ALLNO");
			JSONObject no = new JSONObject(true);
			no.put("plmCode", billNo);
			List<PlmProductHeadEntity_HI> pli = plmProductHeadServer
					.findList(no);
			if (pli.size() > 0) {
				PlmProductHeadEntity_HI headobj = pli.get(0);
				String orderStatus = param.getString("status");
				headobj.setOrderStatus(orderStatus);
				headobj.setStartDate(new Date());

				if (allno.equals("submit")) { // 保存当前流程提交人
					headobj.setUpdateInstanceid(TASKUSER);
					headobj.setRmsReverBusinesskey(INCIDENT);
				}
				if (orderStatus.equals("10")) // 已驳回
				{
					String refus = billNo + "&&&" + INCIDENT + "&&&" + VERSION
							+ "&&&" + taskId + "&&&" + TASKUSER + "&&&" + allno;
					headobj.setAddProcessname(refus);
					headobj.setRmsReverBusinesskey(INCIDENT);
				} else if (orderStatus.equals("4") && !allno.equals("submit")) // 取消
				{
					headobj.setAddProcessname(null);
				} else if (orderStatus.equals("6")) {
					String refus = billNo + "&&&" + INCIDENT + "&&&" + VERSION
							+ "&&&" + taskId + "&&&" + TASKUSER + "&&&" + allno;
					headobj.setAddProcessname(refus);
					headobj.setRmsReverBusinesskey(INCIDENT);
				}

				else {
					headobj.setRmsReverBusinesskey(INCIDENT);

				}
				plmProductHeadServer.update(headobj);
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
					param.toString()).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 2018/09/19
	 * 
	 * @Title:
	 * @Description: TODO(添加Ob商品)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "addobProduct")
	public String addobProduct(@RequestParam(required = false) String params) {
		try {

			JSONObject param = parseObject(params);
			JSONObject jo = param.getJSONObject("headInfo");
			if (jo.containsKey("productHeadId")) {
				PlmProductHeadEntity_HI oldhi = plmProductHeadServer.getById(jo
						.getInteger("productHeadId"));
				if(jo.containsKey("buttonStatus")) {
					//判断权限
					Integer curentorgId = this.getUserSessionBean().getOrgId();
//
					String deptment=oldhi.getDepartment();
					JSONObject deptparam = new JSONObject();
					deptparam.put("deptId", curentorgId);
					List<PlmDataAclHeaderEntity_HI> headli = plmDataAclHeaderServer
							.findList(deptparam);
					boolean newflag=false;
					boolean modifyflag=false;
					if (headli.size() > 0) {
						PlmDataAclHeaderEntity_HI headinfo = headli.get(0);
						Integer headid = headinfo.getHeadId();

						JSONObject lineparam = new JSONObject();
						lineparam.put("headId", headid);
						lineparam.put("enableFlag", "Y");
						List<PlmDataAclLineEntity_HI> linedata = IPlmDataAclLineServer
								.findList(lineparam);
						for (PlmDataAclLineEntity_HI line : linedata) {
							String groupCode = line.getGroupCode();
							String acltype = line.getAclType();
							if(acltype.equals("NEW")) {

								if (groupCode.length() == 1 || groupCode.length() == 2) {
									if (deptment.substring(0, 1).equals(groupCode)) {
										newflag = true;
									}

								}else{
									if(deptment.equals(groupCode))
									{
										newflag=true;
									}

							}
								}
							else if(acltype.equals("UPDATE")){
								if(groupCode.equals("PUBLIC"))
								{
									modifyflag=true;
								}else
								{
									if(oldhi.getUserDept()!=null) {
										Integer departmentNo = new Integer(oldhi.getUserDept());
										if (departmentNo.equals(curentorgId)) {
											modifyflag=true;
										}
									}

								}
							}

						}
					}
					if(newflag==false){
						throw new ServerException("当前用户所新增的部门不在权限范围内!");
					}
					if(modifyflag==false){
						throw new ServerException("当前用户没有该单据修改权限,无法保存或提交!");
					}


					String oldorders = oldhi.getOrderStatus();
					String savetype = jo.getString("buttonStatus");
					if (!savetype.equals("submit")) //保存
					{
						if(oldorders.equals("4")|| oldorders.equals("5")||
								oldorders.equals("6")||oldorders.equals("8")||oldorders.equals("11")){
							throw new ServerException("当前商品状态不允许保存!");
						}
					} else { //提交
						if(!oldorders.equals("3")&&!oldorders.equals("7")&&!oldorders.equals("10")&&!oldorders.equals("1")&&
								!oldorders.equals("2")){
							throw new ServerException("当前商品状态不允许提交!");
						}
					}
				}
			}

			param.put("createdEname", this.getUserSessionBean()
					.getNameEnglish());
			param.put("createdEmp", this.getUserSessionBean()
					.getEmployeeNumber());

			Integer deptId = this.getUserSessionBean().getOrgId();
			String deptName = this.getUserSessionBean().getOrgName();
			param.put("userDept", deptId);
			param.put("userGroupcode", deptName);

			JSONArray barcodelist = null;
			if (param.containsKey("barcodelist")) {
				barcodelist = param.getJSONArray("barcodelist");// 条码行信息
			}
			for (int i = 0; i < barcodelist.size(); i++) {
				JSONObject barobj = barcodelist.getJSONObject(i);

				if (barobj.containsKey("barcodeId")) { // 修改
					if (!barobj.containsKey("barcode")) {
						continue;
					} else {
						String barcode = barobj.getString("barcode");
						String barcodeType = barobj.getString("barcodeType");
						if (barcodeType.equals("6")
								&& barcode.equals("88888888")) {
							continue;
						}
					}

					Integer barcodeId = barobj.getInteger("barcodeId");
					PlmProductBarcodeTableEntity_HI barobject = plmProductBarcodeTableDao
							.getById(barcodeId);
					if (!barobject.getBarcode().equals(
							barobj.getString("barcode"))) {

						JSONObject ma = new JSONObject();
						ma.put("barcode", barobj.getString("barcode"));
						List<PlmProductBarcodeTableEntity_HI> barli = new ArrayList<PlmProductBarcodeTableEntity_HI>();
						try {
							if (!barobj.getString("barcode").equals("88888888")) {
								barli = plmProductBarcodeTableDao.findList(ma);
							}
						} catch (Exception e) {
							throw new ServerException("条形码序号'" + (i + 1)
									+ "',格式错误!");
						}

						if (barli.size() > 0) {
							boolean isexis = false;
							for (PlmProductBarcodeTableEntity_HI ba : barli) {
								Integer headid = ba.getProductHeadId();
								PlmProductHeadEntity_HI proold = plmProductHeadServer
										.getById(headid);
								if (!proold.getOrderStatus().equals("5")) {
									isexis = true;
									break;
								}
							}
							if (isexis) {
								throw new ServerException("条形码序号'" + (i + 1)
										+ "',条码已经存在!");
							}

						} else {
							boolean f = this.isBarcode(
									barobj.getString("barcodeType"),
									barobj.getString("barcode"));
							if (f == false) {
								throw new ServerException("条形码序号'" + (i + 1)
										+ "',格式错误!");
							}
						}

					}
				} else // 新增
				{
					if (!barobj.containsKey("barcode")) {
						continue;
					} else {
						String barcode = barobj.getString("barcode");
						String barcodeType = barobj.getString("barcodeType");
						if (barcodeType.equals("6")
								&& barcode.equals("88888888")) {
							continue;
						}
					}

					JSONObject ma = new JSONObject();
					ma.put("barcode", barobj.getString("barcode"));
					List<PlmProductBarcodeTableEntity_HI> barli = new ArrayList<PlmProductBarcodeTableEntity_HI>();
					try {
						if (!barobj.getString("barcode").equals("88888888")) {
							barli = plmProductBarcodeTableDao.findList(ma);
						}
					} catch (Exception e) {

						throw new ServerException("条形码序号'" + (i + 1)
								+ "',格式错误!");
					}

					if (barli.size() > 0) {
						boolean isexis = false;
						for (PlmProductBarcodeTableEntity_HI ba : barli) {
							Integer headid = ba.getProductHeadId();
							PlmProductHeadEntity_HI proold = plmProductHeadServer
									.getById(headid);
							if (!proold.getOrderStatus().equals("5")) {
								isexis = true;
								break;
							}
						}
						if (isexis) {
							throw new ServerException("条形码序号'" + (i + 1)
									+ "',条码已经存在!");
						}

					} else {
						boolean f = this.isBarcode(
								barobj.getString("barcodeType"),
								barobj.getString("barcode"));
						if (f == false) {
							throw new ServerException("条形码序号'" + (i + 1)
									+ "',格式错误!");
						}
					}

				}
			}

			JSONObject result = plmProductHeadServer.saveObProduct(param);
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return getException(e, LOGGER);
		}

	}

	/*
	 * 作废
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateobProduct")
	public String updateobProduct(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			JSONObject jt = new JSONObject();
			Integer productHeadId = param.getInteger("productHeadId");
			jt.put("productHeadId", productHeadId);
			PlmProductHeadEntity_HI entity = plmProductHeadServer
					.getById(productHeadId);
			if (entity != null) {
				// plmProductHeadServer.deleteByProductId(jt);
				entity.setOrderStatus("5");
				plmProductHeadServer.update(entity);
				if (entity.getObId() != null && !entity.getObId().equals("")) {
					JSONObject objson = new JSONObject();
					objson.put("obId", entity.getObId());
					List<PlmDevelopmentInfoEntity_HI> li = deveinfo
							.findList(objson);
					if (li.size() > 0) {
						PlmDevelopmentInfoEntity_HI r = li.get(0);
						r.setProductStatus("TO_BE_INTRODUCED");
						deveinfo.update(r);
					}
				}
				// 取消流程

				if (entity.getRmsReverBusinesskey() != null) {
					if (!entity.getRmsReverBusinesskey().equals("")) {
						String bk = entity.getRmsReverBusinesskey();
						JSONObject pa = new JSONObject(true);
						pa.put("Method", "CancelIncident");
						pa.put("TASKUSER", this.getUserSessionBean()
								.getUserName());
						pa.put("PROCESSNAME", "商品新增流程");
						pa.put("INCIDENT", bk);
						new WatonsBpmService().ExecuteBpm(pa);
					}
				}
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
					null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	// 更新回调方法
	@RequestMapping(method = RequestMethod.POST, value = "updateConfirm")
	public String updateConfirm(@RequestParam(required = false) String params) {
		try {
			System.out.println(params);
			JSONObject param = parseObject(params);
			JSONObject json = plmProductHeadServer.updateConfirmProduct(param);

			return json.toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 
	 * 
	 * @Title:
	 * @Description: TODO(获取商品列表)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductReportList")
	public String findProductReportPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmProductAddReport> results = plmProductHeadServer
					.FindProductReportList(param, pageIndex, pageRows);
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 每日新增商品（已完成）
	/**
	 * 2019/10/16
	 * 
	 * @Title:
	 * @Description: TODO(获取每日新增列表)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductReportTList")
	public String findProductReportTPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmProductAddReportT> results = plmProductHeadServer
					.FindProductReportTList(param, pageIndex, pageRows);
			ResultUtils.getLookUpValue("PLM_PRODUCT_PLACETYPE");
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 有条件审批
	// 每日新增商品（已完成）
	/**
	 * 2019/10/16
	 * 
	 * @Title:
	 * @Description: TODO(获取每日新增列表)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductconditionReportList")
	public String findProductconditionReportPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmProductConditionReport> results = plmProductHeadServer
					.FindProductconditionReportLis(param, pageIndex, pageRows);
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2019/10/18
	 * 
	 * @Title:
	 * @Description: TODO(保存流程实例id)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveProductInstanceById")
	public String saveProductInstanceById(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			// else
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2019/10/21
	 * 
	 * @Title:
	 * @Description: TODO(获取修改包装数据)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductUpdatePackage")
	public String findProductUpdatePackagePage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmProductPackageReport> results = plmProductHeadServer
					.FindProductUpdatePackage(param, pageIndex, pageRows);
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2019/10/21
	 * 
	 * @Title:
	 * @Description: TODO(获取修改优选供应商信息)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductUpdateSupplier")
	public String findProductUpdateSupplierPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmProductSupplierReport> results = plmProductHeadServer
					.FindProductUpdateSupplier(param, pageIndex, pageRows);
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 驳回供应商方法
	/**
	 * 
	 * 
	 * @Title:
	 * @Description: TODO()
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "productSupplierReturn")
	public String productSupplierReturn(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			JSONObject result = plmProductHeadServer
					.productSupplierReturn(param);
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	// 提交后的回调方法
	/**
	 * 保存当前登录人的下一个审批节点 2018/10/30
	 * 
	 * @Title:
	 * @Description: TODO()
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveNextPerson")
	public String saveNextPerson(@RequestParam(required = false) String params) {
		try {

			JSONObject param = parseObject(params);

			JSONObject result = plmProductHeadServer.saveNextPerson(param);
			// 找到上级

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
					result).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	// 流程提交方法
	@RequestMapping(method = RequestMethod.POST, value = "commitproduct")
	public String commitproduct(@RequestParam(required = false) String params) {
		try {

			JSONObject param = parseObject(params);
			String id = param.getString("id");
			String headid = id.split("_")[0];
			PlmProductHeadEntity_HI headobj = plmProductHeadServer
					.getById(new Integer(headid));
			JSONObject pa = new JSONObject();
			pa.put("productHeadId", id);

			List<PlmProductUpdatepropertisEntity_HI> dali = plmProductUpdatepropertis
					.findList(pa);
			List<PlmProductModifyCheckEntity_HI> moli = new ArrayList<PlmProductModifyCheckEntity_HI>();
			for (PlmProductUpdatepropertisEntity_HI po : dali) {
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

			String status = param.getString("status");

			if (headobj != null) {
				if (status.equals("REFUSAL")) {
					String sql = " update PlmProductUpdatepropertisEntity_HI set order_status='3',order_statusname='修改驳回' where product_head_id='"
							+ id + "'";
					plmProductHeadServer.execute(sql);

					for (PlmProductModifyCheckEntity_HI c : moli) {
						JSONObject js = new JSONObject();
						js.put("tableName", c.getTableName());
						js.put("productHeadId", c.getProductHeadId());
						js.put("lineId", c.getLineId());
						js.put("columnName", c.getColumnName());
						List<PlmProductModifyCheckEntity_HI> result = plmProductModify
								.findList(js);
						if (result.size() == 0) {
							c.setStatus("3"); // 驳回
							plmProductModifyCheckServer.save(c);
						} else {
							PlmProductModifyCheckEntity_HI cobj = result.get(0);
							cobj.setStatus("3"); // 驳回
							cobj.setOldValue(c.getOldValue());
							cobj.setNewValue(c.getNewValue());
							plmProductModifyCheckServer.update(cobj);
						}
					}

				} else if (status.equals("REVOKE")) {
					String sql = " update PlmProductUpdatepropertisEntity_HI set order_status='4',order_statusname='修改撤回' where product_head_id='"
							+ id + "'";
					plmProductHeadServer.execute(sql);

					for (PlmProductModifyCheckEntity_HI c : moli) {
						JSONObject js = new JSONObject();
						js.put("tableName", c.getTableName());
						js.put("productHeadId", c.getProductHeadId());
						js.put("lineId", c.getLineId());
						js.put("columnName", c.getColumnName());
						List<PlmProductModifyCheckEntity_HI> result = plmProductModify
								.findList(js);
						if (result.size() == 0) {
							c.setStatus("4"); // 撤回
							plmProductModifyCheckServer.save(c);
						} else {
							PlmProductModifyCheckEntity_HI cobj = result.get(0);
							cobj.setStatus("4"); // 撤回
							cobj.setOldValue(c.getOldValue());
							cobj.setNewValue(c.getNewValue());
							plmProductModifyCheckServer.update(cobj);
						}
					}

				} else {
					// if (!headobj.getOrderStatus().equals("7")) {
					String sql = " update PlmProductUpdatepropertisEntity_HI set order_status='1',order_statusname='修改审批中' where product_head_id='"
							+ id + "'";
					plmProductHeadServer.execute(sql);

					for (PlmProductModifyCheckEntity_HI c : moli) {
						JSONObject js = new JSONObject();
						js.put("tableName", c.getTableName());
						js.put("productHeadId", c.getProductHeadId());
						js.put("lineId", c.getLineId());
						js.put("columnName", c.getColumnName());
						List<PlmProductModifyCheckEntity_HI> result = plmProductModify
								.findList(js);
						if (result.size() == 0) {
							c.setStatus("1"); // 修改审批中
							plmProductModifyCheckServer.save(c);
						} else {
							PlmProductModifyCheckEntity_HI cobj = result.get(0);
							cobj.setStatus("1"); // 修改审批中
							cobj.setOldValue(c.getOldValue());
							cobj.setNewValue(c.getNewValue());
							plmProductModifyCheckServer.update(cobj);
						}
					}
				}
			}
			// }

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
					null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 2018/08/30
	 * 
	 * @Title:
	 * @Description: TODO(获取商品列表)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductList2")
	public String findProductPage2(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Integer deptId = this.getUserSessionBean().getOrgId();
			String deptName = this.getUserSessionBean().getOrgName();

			param.put("userDept", deptId);
			param.put("deptName", deptName);

			//取出供应商编码
			/*UserSessionBean userSessionBean = getUserSessionBean();

			param.put("vendorCodeList", userSessionBean.getVendorCodes());
			param.put("userId", userSessionBean.getUserId());
			param.put("userType", userSessionBean.getUserType());*/

			Pagination<PlmProductHeadEntity_HI_RO> results = plmProductHeadServer
					.findProductList2(param, pageIndex, pageRows);
			ResultUtils.getLookUpValue("PLM_PRODUCT_PURCHASE");
			ResultUtils.getLookUpValue("PLM_PRODUCT_STATUS");
			ResultUtils.getLookUpValue("PLM_PRODUCT_ORTHERINFO");
			ResultUtils.getLookUpValue("PLM_PRODUCT_SESION");
			ResultUtils.getLookUpValue("PLM_PRODUCT_RETURNPRO");
			ResultUtils.getLookUpValue("PLM_PRODUT_ORDERSTATUS");
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 图片驳回

	// 驳回供应商方法
	/**
	 * 
	 * 
	 * @Title:
	 * @Description: TODO()
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "productPicfileReturn")
	public String productPicfileReturn(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			JSONObject result = plmProductHeadServer
					.productPicfileReturn(param);
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	// 资质文件退回
	@RequestMapping(method = RequestMethod.POST, value = "productQafileReturn")
	public String productQafileReturn(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			JSONObject result = plmProductHeadServer.productQafileReturn(param);
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	// 批量下载
	// @RequestMapping(method = RequestMethod.POST, value =
	// "productQafileReturn")
	// public String productDown(@RequestParam(required = false) String params)
	// {
	// try {
	// JSONObject param = parseObject(params);
	// JSONObject result = plmProductHeadServer.productQafileReturn(param);
	// return result.toString();
	// } catch (Exception e) {
	// LOGGER.error(e.getMessage(), e);
	// return SToolUtils.convertResultJSONObj(ERROR_STATUS,
	// e.getMessage(), 0, null).toString();
	// }
	//
	// }

	// 阶段一批量提交
	@RequestMapping(method = RequestMethod.POST, value = "SaveProductBuyPattch")
	public String SaveProductBuyPattch(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			String result = plmProductHeadServer.SaveProductPattch(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, result, 1,
					result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 批量下载
	@RequestMapping(method = RequestMethod.POST, value = "uploadProductSupplierPattch")
	public @ResponseBody
	String uploadProductSupplierPattch(
			@RequestParam(required = false) String params,
			HttpServletResponse response) {
		try {
			JSONObject param = parseObject(params);

			String result = plmProductHeadServer.uploadProductSupplierPattch(
					param, response);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 1,
					result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	public static String getRandom() {
		StringBuffer flag = new StringBuffer();

		String sources = "0123456789";
		Random rand = new Random();

		for (int j = 0; j < 6; j++) {
			flag.append(sources.charAt(rand.nextInt(9)) + "");
		}
		return flag.toString();
	}

	// 供应商（二阶段） 批量上传

	/**
	 * 2018/12/10
	 * 
	 * @Title:
	 * @Description: TODO(批量导入 第二阶段)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "SaveSupplierProductByExcel")
	public String SaveSupplierProductByExcel(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);

			JSONObject result = plmProductHeadServer
					.SaveSupplierProductByExcel(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 2018/12/11
	 * 
	 * @Title:
	 * @Description: TODO(批量关联图片文件和资质文件)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ConnectSupplierProductByFile")
	public String ConnectSupplierProductByFile(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);

			JSONObject result = plmProductHeadServer
					.ConnectSupplierProductByFile(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	// 阶段二提交
	@RequestMapping(method = RequestMethod.POST, value = "SaveProductSupplierSubmit")
	public String SaveProductSupplierSubmit(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);

			String result = plmProductHeadServer
					.SaveProductSupplierSubmit(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, result, 1,
					result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 阶段三批量下载
	@RequestMapping(method = RequestMethod.POST, value = "uploadProductBuyerPattch")
	public @ResponseBody
	String uploadProductBuyerPattch(
			@RequestParam(required = false) String params,
			HttpServletResponse response) {
		try {
			JSONObject param = parseObject(params);

			String result = plmProductHeadServer.uploadProductBuyerPattch(
					param, response);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 1,
					result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	//
	// @RequestMapping(method = RequestMethod.POST, value = "getinfoByuname")
	// public JSONObject getinfo(@RequestParam(required = false) String params)
	// {
	// JSONObject param = parseObject(params);
	// // String userName = param.getString("userName");
	// JSONObject jo = ResultUtils.getUserInfoForByUserName(param);
	//
	// // String userName = jo.getString("userName");
	// // String userFullName = jo.getString("userFullName");
	// // System.out.println(userName + "==" + userFullName);
	// // System.out.println(jo.getString("userName"));
	// // Sysout
	// return jo;
	// }
	// // getUserInfoForByUserName

	/**
	 * 
	 * 
	 * @Title:
	 * @Description: TODO(批量导入 第三阶段)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "SaveThreeProductByExcel")
	public String SaveThreeProductByExcel(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);

			JSONObject result = plmProductHeadServer
					.SaveThreeProductByExcel(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	// 阶段三提交
	@RequestMapping(method = RequestMethod.POST, value = "SaveProductBuyer3Submit")
	public String SaveProductBuyer3Submit(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			JSONObject result = plmProductHeadServer
					.SaveProductBuyer3Submit(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
					result.getString("msg"), 1, result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 筛选供应商
	@RequestMapping(method = RequestMethod.POST, value = "getSupplierByProduct")
	public String getSupplierByProduct(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			JSONObject result = plmProductHeadServer
					.getSupplierByProduct(param);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 1,
					result).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2018/08/30
	 * 
	 * @Title:
	 * @Description: TODO(获取商品列表)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductNolist")
	public String FindProductNolist(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);

			List<String> vendcodes = this.getUserSessionBean().getVendorCodes();
			if (vendcodes.size() >= 1) {
				String vendcode = vendcodes.get(0);
				param.put("vendcode", vendcode);
			}
			Integer deptId = this.getUserSessionBean().getOrgId();

			param.put("userDept", deptId);
			// param.put("userGroupcode", userGroupcode);

			param.put("nolist", "true");
			Pagination<PlmProductHeadEntity_HI_RO> results = plmProductHeadServer
					.findProductList(param, pageIndex, pageRows);

			List<PlmProductHeadEntity_HI_RO> rl = results.getData();
			PlmProductHeadEntity_HI_RO r = new PlmProductHeadEntity_HI_RO();
			r.setProductName("NULL");
			r.setRmsCode("001");
			rl.add(0, r);
			ResultUtils.getLookUpValue("PLM_PRODUCT_PURCHASE");
			ResultUtils.getLookUpValue("PLM_PRODUCT_STATUS");
			ResultUtils.getLookUpValue("PLM_PRODUCT_ORTHERINFO");
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 资质文件报表

	@RequestMapping(method = RequestMethod.POST, value = "FindProductQaReportList")
	public String FindProductQaReportList(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmProductQaReport> results = plmProductHeadServer
					.FindProductQaReportList(param, pageIndex, pageRows);

			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 供应商资质文件
	@RequestMapping(method = RequestMethod.POST, value = "FindSupplierQaReportList")
	public String FindSupplierQaReportList(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Pagination<PlmSupplierQaReport> results = plmProductHeadServer
					.FindSupplierQaReportList(param, pageIndex, pageRows);

			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	public static boolean isBarcode(String type, String barcode) {
		if (!type.equals("1")) {
			boolean flag = true;
			int len = barcode.length();
			String barcodesub = barcode.substring(0, barcode.length() - 1);
			int barlen = barcodesub.length() - 1;
			String result = barcode.substring(len - 1);
			int[] a = new int[len];
			for (int i = 0; i < barcodesub.length(); i++) {
				try {
					a[i] = Integer.valueOf(barcodesub.substring(barlen - i,
							barlen - i + 1));
				} catch (Exception e) {
					return false;
				}
			}
			int sum1 = 0;
			int sum2 = 0;
			for (int i = 0; i < barcodesub.length(); i++) {
				if (i % 2 == 0) {
					sum1 = sum1 += a[i];
				} else {
					sum2 = sum2 += a[i];
				}
			}
			int temp = sum1 * 3 + sum2;
			int g = temp % 10;
			int y = 0;
			if (g != 0) {
				y = ((temp / 10) + 1) * 10 - temp;
			}
			if (y == new Integer(result)) {
				flag = true;
			} else {
				flag = false;
			}
			// 调用upc-e方法

			return flag;
		} else {

			return upce2UPCA(barcode); // 转化为lan12在校验

		}
	}

	// upc校验方法
	public static boolean upce2UPCA(String barCodeString) {
		String resultString = "";
		Integer len = barCodeString.length();
		String laststr = barCodeString.substring(barCodeString.length() - 1);
		String nextstr = barCodeString.substring(len - 2, len - 1);
		switch (nextstr) {
		case "0":
		case "1":
		case "2": {
			resultString = barCodeString.substring(0, 3) + nextstr + "0000"
					+ barCodeString.substring(3, 6) + laststr;
			break;
		}
		case "3": {
			resultString = barCodeString.substring(0, 4) + "00000"
					+ barCodeString.substring(4, 6) + laststr;
			break;
		}
		case "4": {
			resultString = barCodeString.substring(0, 5) + "00000"
					+ barCodeString.substring(5, 6) + laststr;
			break;
		}
		case "5":
		case "6":
		case "7":
		case "8":
		case "9": {
			resultString = barCodeString.substring(0, 6) + "0000"
					+ barCodeString.substring(6);
			break;
		}
		}

		System.out.println(resultString);

		int relen = resultString.length();
		String barcodesub = resultString.substring(0, relen - 1);
		int barlen = barcodesub.length() - 1;
		String result = resultString.substring(relen - 1);
		int[] a = new int[relen];
		for (int i = 0; i < barcodesub.length(); i++) {
			try {
				a[i] = Integer.valueOf(barcodesub.substring(barlen - i, barlen
						- i + 1));
			} catch (Exception e) {
				return false;
			}
		}
		int sum1 = 0;
		int sum2 = 0;
		for (int i = 0; i < barcodesub.length(); i++) {
			if (i % 2 == 0) {
				sum1 = sum1 += a[i];
			} else {
				sum2 = sum2 += a[i];
			}
		}

		int temp = sum1 * 3 + sum2;

		int g = temp % 10;
		int y = 0;
		if (g != 0) {
			y = ((temp / 10) + 1) * 10 - temp;
		}
		if (y == new Integer(result)) {
			return true;
		} else {
			return false;
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "getShopBySxWay")
	public String getShopBySxWay(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {

		try {
			JSONObject dataJSON = parseObject(params);
			if (!dataJSON.containsKey("sxWay")
					|| !dataJSON.containsKey("dxSxway")) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
						1, null).toString();
			}

			String sxWay = dataJSON.getString("sxWay"); // 供应商生效方式
			String dxSxway = dataJSON.getString("dxSxway"); // 代销生效方式
			if (sxWay.equals("1") || sxWay.equals("2")) { // 全部仓+店
				if (dxSxway.equals("1")) { // 地点清单 //返回空
					Pagination<VmiShopEntity_HI_RO2> pa = new Pagination<VmiShopEntity_HI_RO2>();
					pa.setData(new ArrayList<VmiShopEntity_HI_RO2>());
					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				} else if (dxSxway.equals("2")) // 区域 //返回全部区域
				{

					Pagination<VmiShopEntity_HI_RO2> pa = vimshopServer
							.findArea(dataJSON, pageIndex, pageRows);

					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					return jsonObject.toString();

				} else if (dxSxway.equals("3")) { // 指定店铺

					Pagination<VmiShopEntity_HI2> li = vimshopServer
							.findPagination(dataJSON, pageIndex, pageRows);

					String resultString = JSON.toJSONString(li);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					return jsonObject.toString();

				}

			}

			if (sxWay.equals("3") || sxWay.equals("8")) // 地点清单
			{
				if (!dataJSON.containsKey("placeCode")) {
					Pagination<VmiShopEntity_HI_RO2> pa = new Pagination<VmiShopEntity_HI_RO2>();
					pa.setData(new ArrayList<VmiShopEntity_HI_RO2>());
					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				}
				String placenote = dataJSON.getString("placeCode");
				if (dxSxway.equals("1")) // 地点清单
				{
					Pagination<VmiShopEntity_HI_RO2> pa = new Pagination<VmiShopEntity_HI_RO2>();
					pa.setData(new ArrayList<VmiShopEntity_HI_RO2>());
					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				} else if (dxSxway.equals("2")) // 区域
				{
					JSONObject shop = iPlmApiServer
							.siteListingMethod(new Integer(placenote));
					JSONArray jsonArray = shop.getJSONArray("data_set");
					String loclist = "";
					for (int len = 0; len < jsonArray.size(); len++) {
						JSONObject j = jsonArray.getJSONObject(len);
						String shopno = j.getString("loc");
						loclist += "'" + shopno + "',";
					}

					dataJSON.put("vscodes",
							loclist.substring(0, loclist.length() - 1));
					Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
							.findArea(dataJSON, pageIndex, pageRows);

					String resultString = JSON.toJSONString(liarea);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					return jsonObject.toString();

				} else if (dxSxway.equals("3")) // 店铺
				{
					JSONObject shop = iPlmApiServer
							.siteListingMethod(new Integer(placenote));
					JSONArray jsonArray = shop.getJSONArray("data_set");
					String loclist = "";
					for (int len = 0; len < jsonArray.size(); len++) {
						JSONObject j = jsonArray.getJSONObject(len);
						String shopno = j.getString("loc");
						loclist += "'" + shopno + "',";
					}

					dataJSON.put("vscodes",
							loclist.substring(0, loclist.length() - 1));
					Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
							.findShopinfo(dataJSON, pageIndex, pageRows);

					String resultString = JSON.toJSONString(liarea);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					return jsonObject.toString();
				}

			} else if (sxWay.equals("4") || sxWay.equals("7")) // 指定仓加店
			{
				if (!dataJSON.containsKey("sxStore")) {
					Pagination<VmiShopEntity_HI_RO2> pa = new Pagination<VmiShopEntity_HI_RO2>();
					pa.setData(new ArrayList<VmiShopEntity_HI_RO2>());
					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				}
				String shops = dataJSON.getString("sxStore");
				if (dxSxway.equals("1")) // 地点清单
				{
					Pagination<VmiShopEntity_HI_RO2> pa = new Pagination<VmiShopEntity_HI_RO2>();
					pa.setData(new ArrayList<VmiShopEntity_HI_RO2>());
					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				} else if (dxSxway.equals("3")) // 指定店铺
				{
					String loclist = "";
					for (String shop : shops.split(",")) {
						loclist += "'" + shop + "',";
					}

					dataJSON.put("vscodes",
							loclist.substring(0, loclist.length() - 1));
					Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
							.findShopinfo(dataJSON, pageIndex, pageRows);

					String resultString = JSON.toJSONString(liarea);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					return jsonObject.toString();
				} else if (dxSxway.equals("2")) // 区域
				{
					String loclist = "";
					for (String shop : shops.split(",")) {
						loclist += "'" + shop + "',";
					}

					dataJSON.put("vscodes",
							loclist.substring(0, loclist.length() - 1));
					Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
							.findArea(dataJSON, pageIndex, pageRows);

					String resultString = JSON.toJSONString(liarea);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					return jsonObject.toString();
				}

			} else if (sxWay.equals("5")) // 区域
			{
				if (!dataJSON.containsKey("area")) {
					Pagination<VmiShopEntity_HI_RO2> pa = new Pagination<VmiShopEntity_HI_RO2>();
					pa.setData(new ArrayList<VmiShopEntity_HI_RO2>());
					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				}
				if (dxSxway.equals("1")) // 地点清单
				{
					Pagination<VmiShopEntity_HI_RO2> pa = new Pagination<VmiShopEntity_HI_RO2>();
					pa.setData(new ArrayList<VmiShopEntity_HI_RO2>());
					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				} else if (dxSxway.equals("2")) // 区域
				{
					String area = dataJSON.getString("area");
					String liarea = "";
					for (String achild : area.split(",")) {
						liarea += "'" + achild + "',";
					}

					dataJSON.put("areas",
							liarea.substring(0, liarea.length() - 1));
					Pagination<VmiShopEntity_HI_RO2> liareas = vimshopServer
							.findArea(dataJSON, pageIndex, pageRows);

					String resultString = JSON.toJSONString(liareas);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					return jsonObject.toString();

				} else if (dxSxway.equals("3")) // 指定店铺
				{
					String area = dataJSON.getString("area");
					String liarea = "";
					for (String achild : area.split(",")) {
						liarea += "'" + achild + "',";
					}

					dataJSON.put("areas",
							liarea.substring(0, liarea.length() - 1));
					Pagination<VmiShopEntity_HI_RO2> liareas = vimshopServer
							.findShopinfo(dataJSON, pageIndex, pageRows);

					String resultString = JSON.toJSONString(liareas);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					return jsonObject.toString();
				}
			} else if (sxWay.equals("9")) // 仓库及其店铺
			{
				if (!dataJSON.containsKey("sxWarehouseId")) {
					Pagination<VmiShopEntity_HI_RO2> pa = new Pagination<VmiShopEntity_HI_RO2>();
					pa.setData(new ArrayList<VmiShopEntity_HI_RO2>());
					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				}
				String warehouses = dataJSON.getString("sxWarehouseId");
				List<VmiShopEntity_HI_RO2> results = new ArrayList<VmiShopEntity_HI_RO2>();
				Map<String, VmiShopEntity_HI_RO2> areadata = new HashMap<String, VmiShopEntity_HI_RO2>();
				List<VmiShopEntity_HI_RO2> arearesults = new ArrayList<VmiShopEntity_HI_RO2>();
				String loc = "";
				for (String ware : warehouses.split(",")) {

					String wareresult = "";

					try {
						locationMap lomap = new locationMap();
						Map<String, String> locmap = lomap.getMapData();
						if (locmap.containsKey(ware)) {
							String wcode = locmap.get(ware);
							JSONObject shop = iPlmApiServer
									.siteListingMethod(new Integer(wcode));
							JSONArray jsonArray = shop.getJSONArray("data_set");
							for (int len = 0; len < jsonArray.size(); len++) {
								JSONObject json = jsonArray.getJSONObject(len);
								String shopno = json.getString("loc");
								wareresult += "'" + shopno + "',";
								loc += shopno + ",";
							}

						}

						if (dxSxway.equals("2")) {
							dataJSON.put("vscodes", wareresult.substring(0,
									wareresult.length() - 1));
							Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
									.findArea(dataJSON, 1, 10000);
							arearesults.addAll(liarea.getData());

						} else if (dxSxway.equals("3")) // 店铺
						{
							dataJSON.put("vscodes", wareresult.substring(0,
									wareresult.length() - 1));
							Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
									.findShopinfo(dataJSON, 1, 10000);
							results.addAll(liarea.getData());
						}
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					} catch (Exception e) {

						e.printStackTrace();
					}

				}

				if (dxSxway.equals("1")) {
					Pagination<VmiShopEntity_HI_RO2> pa = new Pagination<VmiShopEntity_HI_RO2>();
					pa.setData(new ArrayList<VmiShopEntity_HI_RO2>());
					String resultString = JSON.toJSONString(pa);
					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				} else if (dxSxway.equals("2")) // 区域
				{
					List<VmiShopEntity_HI_RO2> rdata = new ArrayList<VmiShopEntity_HI_RO2>();
					for (VmiShopEntity_HI_RO2 se : arearesults) {
						if (!areadata.containsKey(se.getAreaId())) {
							rdata.add(se);
						}
					}

					Pagination<VmiShopEntity_HI_RO2> painfo = new Pagination<VmiShopEntity_HI_RO2>();
					painfo.setCount(rdata.size());
					painfo.setCurIndex(1);
					painfo.setPagesCount(1);
					painfo.setData(rdata);
					painfo.setPageSize(10);
					painfo.setPreIndex(1);

					String resultString = JSON.toJSONString(painfo);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", rdata.size());
					return jsonObject.toString();

				} else if (dxSxway.equals("3")) {

					List<VmiShopEntity_HI_RO2> pagedata = new ArrayList<VmiShopEntity_HI_RO2>();
					Pagination<VmiShopEntity_HI_RO2> painfo = new Pagination<VmiShopEntity_HI_RO2>();
					painfo.setCount(results.size());
					painfo.setCurIndex(pageIndex);

					Integer pagecount = 0;
					if (results.size() / pageRows == 0) {
						pagecount = results.size() / pageRows;
					} else {
						pagecount = results.size() / pageRows + 1;
					}

					if (pagecount == 0) {
						pagecount = 1;
					}

					painfo.setPagesCount(pagecount);

					painfo.setPageSize(pageRows);
					painfo.setPreIndex(pageIndex);

					int begin = (pageIndex - 1) * pageRows;
					int end = begin + pageRows - 1;
					if (end > results.size() - 1) {
						end = results.size() - 1;
					}

					for (int i = begin; i < end; i++) {
						pagedata.add(results.get(i));
					}
					painfo.setData(pagedata);

					String resultString = JSON.toJSONString(painfo);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", results.size());
					return jsonObject.toString();

				}

				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
						1, null).toString();
			}

		} catch (Exception e) {
			return e.getMessage();
		}
		return "success";
	}

	@RequestMapping(method = RequestMethod.POST, value = "UpdateRmsColumns")
	public String UpdateRmsColumns(String params) {
		JSONObject dataJSON = parseObject(params);
		try {
			plmProductHeadServer.UpdateRmsColumns(dataJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					null).toString();
		} catch (Exception e) {
			return getException(e, LOGGER);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "getSupplier")
	public String getSupplier(String params) {
		JSONObject dataJSON = parseObject(params);
		try {
			JSONObject js = new ResultUtils().getTtaSupplierInfo(dataJSON);
			return js.toJSONString();
		} catch (Exception e) {
			return getException(e, LOGGER);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "getOnlineShopBySxWay")
	public String getOnlineShopBySxWay(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {

		try {
			JSONObject dataJSON = parseObject(params);
			if (!dataJSON.containsKey("placeType")) {
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
						1, null).toString();
			}
			String placeType = dataJSON.getString("placeType"); // 地点清单生效方式
			JSONArray supplierlist = dataJSON.getJSONArray("supplierlist");
			List<PlmLocationListEntity_HI> location = new ArrayList<PlmLocationListEntity_HI>();
			Map<String, String> locationmap = new HashMap<String, String>();
			List<VmiShopEntity_HI_RO2> shoparea = new ArrayList<VmiShopEntity_HI_RO2>();
			Map<String, String> areamap = new HashMap<String, String>();

			List<VmiShopEntity_HI_RO2> shoplist = new ArrayList<VmiShopEntity_HI_RO2>();
			Map<String, String> shopmap = new HashMap<String, String>();

			if (placeType.equals("1")) // 地点清单 //将供应商选项转换为地点清单信息
			{
				for (int i = 0; i < supplierlist.size(); i++) {
					JSONObject suobj = supplierlist.getJSONObject(i);

					if (!suobj.containsKey("sxWay")) {
						continue;
					}
					String sxway = suobj.getString("sxWay");
					if (sxway.equals("3") || sxway.equals("8")) // 地点清单
					{
						if (!suobj.containsKey("placeCode")) {
							continue;
						}
						String placecode = suobj.getString("placeCode");
						PlmLocationListEntity_HI loclist = new PlmLocationListEntity_HI();
						loclist.setCode(placecode);
						loclist.setDescName(suobj.getString("placeNote"));
						if (!locationmap.containsKey(placecode)) {
							locationmap.put(placecode, placecode);
							location.add(loclist);
						}
					}
					if (sxway.equals("2") || sxway.equals("1")) {// 全部店铺，全部仓+店
						Pagination<PlmLocationListEntity_HI> page = PlmLocationListServer
								.findPagination(dataJSON, pageIndex, pageRows);
						String resultString = JSON.toJSONString(page);
						JSONObject jsonObject = JSONObject
								.parseObject(resultString);
						jsonObject.put(SToolUtils.STATUS, "S");
						jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
						// jsonObject.put("count", 0);
						return jsonObject.toString();
					} else if (sxway.equals("9")) // 仓库及其店铺
					{
						if (!suobj.containsKey("sxWarehouseId")) {
							continue;
						}
						String warehouses =suobj.getString("sxWarehouseId");

						for (String ware : warehouses.split(",")) {
							locationMap locmap = new locationMap();
							Map<String, String> mapdata = locmap.getMapData();
							if (mapdata.containsKey(ware)) {
								PlmLocationListEntity_HI wcode = new PlmLocationListEntity_HI();
								String locware = mapdata.get(ware);
								if (!locationmap.containsKey(locware)) {
									locationmap.put(locware, locware);
									wcode.setCode(mapdata.get(ware));
									location.add(wcode);
								}

							}

						}
					} else {
						Pagination<PlmLocationListEntity_HI> results = new Pagination<PlmLocationListEntity_HI>();
						results.setData(new ArrayList<PlmLocationListEntity_HI>());
						String resultString = JSON.toJSONString(results);

						JSONObject jsonObject = JSONObject
								.parseObject(resultString);
						jsonObject.put(SToolUtils.STATUS, "S");
						jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
						jsonObject.put("count", 0);
						return jsonObject.toString();
					}

				} // for

				if (location.size() > 0) {
					Pagination<PlmLocationListEntity_HI> results = new Pagination<PlmLocationListEntity_HI>();
					List<PlmLocationListEntity_HI> pageresults = new ArrayList<PlmLocationListEntity_HI>();
					results.setPageSize(pageRows);
					results.setCount(location.size());

					results.setCurIndex(pageIndex);
					Integer pagecount = 0;
					if (location.size() / pageRows == 0) {
						pagecount = location.size() / pageRows;
					} else {
						pagecount = location.size() / pageRows + 1;
					}

					if (pagecount == 0) {
						pagecount = 1;
					}
					results.setPagesCount(pagecount);
					results.setNextIndex(pageIndex + 1);

					Integer first = (pageIndex - 1) * pageRows;
					Integer end = first + pageRows - 1;
					if (end > location.size() - 1) {
						end = location.size() - 1;
					}
					for (int i = first; i <= end; i++) {
						pageresults.add(location.get(i));
					}
					results.setData(pageresults);

					String resultString = JSON.toJSONString(results);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, "S");
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", location.size());
					return jsonObject.toString();
				} else {
					Pagination<PlmLocationListEntity_HI> results = new Pagination<PlmLocationListEntity_HI>();
					results.setData(new ArrayList<PlmLocationListEntity_HI>());
					String resultString = JSON.toJSONString(results);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, "S");
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				}
			} // 地点清单
			else if (placeType.equals("2")) // 区域 将供应商选项转换为区域信息
			{

				for (int i = 0; i < supplierlist.size(); i++) {
					JSONObject suobj = supplierlist.getJSONObject(i);

					if (!suobj.containsKey("sxWay")) {
						continue;
					}
					String sxway = suobj.getString("sxWay");
					if (sxway.equals("1") || sxway.equals("2")) // 全部店铺选项
					{

						Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
								.findArea(dataJSON, pageIndex, pageRows);
						String resultString = JSON.toJSONString(liarea);

						JSONObject jsonObject = JSONObject
								.parseObject(resultString);
						jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
						jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
						return jsonObject.toString();

					} else if (sxway.equals("3") || sxway.equals("8")) // 地点清单
					{
						if (!suobj.containsKey("placeCode")) {
							continue;
						}
						String placenote = suobj.getString("placeCode");
						JSONObject shops = iPlmApiServer
								.siteListingMethod(new Integer(placenote));
						JSONArray jsonArray = shops.getJSONArray("data_set");
						String loclist = "";
						for (int len = 0; len < jsonArray.size(); len++) {
							JSONObject j = jsonArray.getJSONObject(len);
							String shopno = j.getString("loc");
							loclist += "'" + shopno + "',";
						}

						dataJSON.put("vscodes",
								loclist.substring(0, loclist.length() - 1));
						Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
								.findArea(dataJSON, 1, 10000);
						List<VmiShopEntity_HI_RO2> datas = liarea.getData();
						for (VmiShopEntity_HI_RO2 v : datas) {
							String areaId = v.getAreaId();
							if (!areamap.containsKey(areaId)) {
								shoparea.add(v);
								areamap.put(areaId, areaId);
							}
						}
						// shoparea.addAll(datas);

					} // 地点清单
					else if (sxway.equals("4") || sxway.equals("7")) { // 指定店铺
						if (!suobj.containsKey("sxStore")) {
							continue;
						}
						String shops = suobj.getString("sxStore");
						String loclist = "";
						for (String shoploc : shops.split(",")) {
							loclist += "'" + shoploc + "',";
						}

						dataJSON.put("vscodes",
								loclist.substring(0, loclist.length() - 1));
						Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
								.findArea(dataJSON, 1, 10000);
						List<VmiShopEntity_HI_RO2> datas = liarea.getData();
						for (VmiShopEntity_HI_RO2 v : datas) {
							String areaId = v.getAreaId();
							if (!areamap.containsKey(areaId)) {
								shoparea.add(v);
								areamap.put(areaId, areaId);
							}
						}

					} else if (sxway.equals("5"))// 区域
					{
						if (!suobj.containsKey("areaId")) {
							continue;
						}
						String areas = suobj.getString("areaId");
						String loclist = "";
						for (String shoploc : areas.split(",")) {
							loclist += "'" + shoploc + "',";
						}

						dataJSON.put("areas",
								loclist.substring(0, loclist.length() - 1));
						Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
								.findArea(dataJSON, 1, 10000);
						List<VmiShopEntity_HI_RO2> datas = liarea.getData();
						for (VmiShopEntity_HI_RO2 v : datas) {
							String areaId = v.getAreaId();
							if (!areamap.containsKey(areaId)) {
								shoparea.add(v);
								areamap.put(areaId, areaId);
							}
						}
					} else if (sxway.equals("9")) // 仓库及其店铺
					{
						String warehouses = suobj.getString("sxWarehouseId");
						List<VmiShopEntity_HI_RO2> datas = new ArrayList<VmiShopEntity_HI_RO2>();
						for (String ware : warehouses.split(",")) {
							String loc = "";
							locationMap locmap = new locationMap();
							Map<String, String> mapdata = locmap.getMapData();
							if (mapdata.containsKey(ware)) {
								String wcode = mapdata.get(ware);
								JSONObject placeshop = iPlmApiServer
										.siteListingMethod(new Integer(wcode));
								JSONArray jsonArray = placeshop
										.getJSONArray("data_set");
								 if (jsonArray.size() > 0) {
									for (int len = 0; len < jsonArray.size(); len++) {

										JSONObject json = jsonArray
												.getJSONObject(len);
										String shopno = json.getString("loc");
										// wareresult += "'" + shopno + "',";
										loc += "'" + shopno + "',";

									}
									dataJSON.put("vscodes",
											loc.substring(0, loc.length() - 1));
									Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
											.findArea(dataJSON, 1, 10000);
									datas.addAll(liarea.getData());

								}
							}
						}

						for (VmiShopEntity_HI_RO2 v : datas) {
							String areaId = v.getAreaId();
							if (!areamap.containsKey(areaId)) {
								shoparea.add(v);
								areamap.put(areaId, areaId);
							}
						}

					}

				} // for

				if (shoparea.size() > 0) {
					Pagination<VmiShopEntity_HI_RO2> results = new Pagination<VmiShopEntity_HI_RO2>();
					List<VmiShopEntity_HI_RO2> pageresults = new ArrayList<VmiShopEntity_HI_RO2>();
					results.setPageSize(pageRows);
					results.setCount(shoparea.size());

					results.setCurIndex(pageIndex);
					Integer pagecount = 0;
					if (shoparea.size() / pageRows == 0) {
						pagecount = shoparea.size() / pageRows;
					} else {
						pagecount = shoparea.size() / pageRows + 1;
					}

					if (pagecount == 0) {
						pagecount = 1;
					}
					results.setPagesCount(pagecount);
					results.setNextIndex(pageIndex + 1);

					Integer first = (pageIndex - 1) * pageRows;
					Integer end = first + pageRows - 1;
					if (end > shoparea.size() - 1) {
						end = shoparea.size() - 1;
					}
					for (int i = first; i <= end; i++) {
						pageresults.add(shoparea.get(i));
					}
					results.setData(pageresults);

					String resultString = JSON.toJSONString(results);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, "S");
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", shoparea.size());
					return jsonObject.toString();
				} else {
					Pagination<PlmLocationListEntity_HI> results = new Pagination<PlmLocationListEntity_HI>();
					String resultString = JSON.toJSONString(results);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, "S");
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				}
			} // placeType2
			else if (placeType.equals("3")) // 店铺
			{
				for (int i = 0; i < supplierlist.size(); i++) {
					JSONObject suobj = supplierlist.getJSONObject(i);

					if (!suobj.containsKey("sxWay")) {
						continue;
					}
					String sxway = suobj.getString("sxWay");
					if (sxway.equals("1") || sxway.equals("2")) // 全部店铺选项
					{

						Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
								.findShopinfo(dataJSON, pageIndex, pageRows);
						String resultString = JSON.toJSONString(liarea);

						JSONObject jsonObject = JSONObject
								.parseObject(resultString);
						jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
						jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
						return jsonObject.toString();

					} else if (sxway.equals("3") || sxway.equals("8")) // 地点清单
					{
						if (!suobj.containsKey("placeCode")) {
							continue;
						}
						String placenote = suobj.getString("placeCode");
						JSONObject shops = iPlmApiServer
								.siteListingMethod(new Integer(placenote));
						JSONArray jsonArray = shops.getJSONArray("data_set");
						String loclist = "";
						for (int len = 0; len < jsonArray.size(); len++) {
							JSONObject j = jsonArray.getJSONObject(len);
							String shopno = j.getString("loc");
							loclist += "'" + shopno + "',";
						}

						dataJSON.put("vscodes",
								loclist.substring(0, loclist.length() - 1));
						Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
								.findShopinfo(dataJSON, 1, 10000);
						List<VmiShopEntity_HI_RO2> datas = liarea.getData();
						for (VmiShopEntity_HI_RO2 vmi : datas) {
							if (!shopmap.containsKey(vmi.getVsCode())) {
								shoplist.add(vmi);
								shopmap.put(vmi.getVsCode(), vmi.getVsCode());
							}

						}
						// shoplist.addAll(datas);

					} // 地点清单
					else if (sxway.equals("4") || sxway.equals("7")) { // 指定店铺
						if (!suobj.containsKey("sxStore")) {
							continue;
						}
						String shops = suobj.getString("sxStore");
						String loclist = "";
						for (String shoploc : shops.split(",")) {
							loclist += "'" + shoploc + "',";
						}

						dataJSON.put("vscodes",
								loclist.substring(0, loclist.length() - 1));
						Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
								.findShopinfo(dataJSON, 1, 10000);
						List<VmiShopEntity_HI_RO2> datas = liarea.getData();
						for (VmiShopEntity_HI_RO2 vmi : datas) {
							if (!shopmap.containsKey(vmi.getVsCode())) {
								shoplist.add(vmi);
								shopmap.put(vmi.getVsCode(), vmi.getVsCode());
							}

						}

					} else if (sxway.equals("5"))// 区域
					{
						if (!suobj.containsKey("areaId")) {
							continue;
						}
						String areas = suobj.getString("areaId");
						String loclist = "";
						for (String shoploc : areas.split(",")) {
							loclist += "'" + shoploc + "',";
						}

						dataJSON.put("areas",
								loclist.substring(0, loclist.length() - 1));
						Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
								.findShopinfo(dataJSON, 1, 10000);
						List<VmiShopEntity_HI_RO2> datas = liarea.getData();
						for (VmiShopEntity_HI_RO2 vmi : datas) {
							if (!shopmap.containsKey(vmi.getVsCode())) {
								shoplist.add(vmi);
								shopmap.put(vmi.getVsCode(), vmi.getVsCode());
							}

						}
					} else if (sxway.equals("9")) // 仓库及其店铺
					{
						String warehouses = suobj.getString("sxWarehouseId");
						List<VmiShopEntity_HI_RO2> datas=new ArrayList<VmiShopEntity_HI_RO2>();
						for (String ware : warehouses.split(",")) {
							String loc = "";
							locationMap locmap = new locationMap();
							Map<String, String> mapdata = locmap.getMapData();

							if (mapdata.containsKey(ware)) {
								String wcode = mapdata.get(ware);
								JSONObject placeshop = iPlmApiServer
										.siteListingMethod(new Integer(wcode));
								JSONArray jsonArray = placeshop
										.getJSONArray("data_set");
                                               if(jsonArray.size()>0) {
												   for (int len = 0; len < jsonArray.size(); len++) {

													   JSONObject json = jsonArray
															   .getJSONObject(len);
													   String shopno = json.getString("loc");
													   // wareresult += "'" + shopno + "',";
													   loc += "'" + shopno + "',";

												   }
												   dataJSON.put("vscodes",
														   loc.substring(0, loc.length() - 1));
												   Pagination<VmiShopEntity_HI_RO2> liarea = vimshopServer
														   .findShopinfo(dataJSON, 1, 10000);
												   datas.addAll(liarea.getData());
											   }

							}
						}
						for (VmiShopEntity_HI_RO2 vmi : datas) {
							if (!shopmap.containsKey(vmi.getVsCode())) {
								shoplist.add(vmi);
								shopmap.put(vmi.getVsCode(),
										vmi.getVsCode());
							}

						}
					}

				} // for

				if (shoplist.size() > 0) {
					Pagination<VmiShopEntity_HI_RO2> results = new Pagination<VmiShopEntity_HI_RO2>();
					List<VmiShopEntity_HI_RO2> pageresults = new ArrayList<VmiShopEntity_HI_RO2>();
					results.setPageSize(pageRows);
					results.setCount(shoplist.size());

					results.setCurIndex(pageIndex);
					Integer pagecount = 0;
					if (shoplist.size() / pageRows == 0) {
						pagecount = shoplist.size() / pageRows;
					} else {
						pagecount = shoplist.size() / pageRows + 1;
					}

					if (pagecount == 0) {
						pagecount = 1;
					}
					results.setPagesCount(pagecount);
					results.setNextIndex(pageIndex + 1);

					Integer first = (pageIndex - 1) * pageRows;
					Integer end = first + pageRows - 1;
					if (end > shoplist.size() - 1) {
						end = shoplist.size() - 1;
					}
					for (int i = first; i <= end; i++) {
						pageresults.add(shoplist.get(i));
					}
					results.setData(pageresults);

					String resultString = JSON.toJSONString(results);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, "S");
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", shoplist.size());
					return jsonObject.toString();
				} else {
					Pagination<PlmLocationListEntity_HI> results = new Pagination<PlmLocationListEntity_HI>();
					String resultString = JSON.toJSONString(results);

					JSONObject jsonObject = JSONObject
							.parseObject(resultString);
					jsonObject.put(SToolUtils.STATUS, "S");
					jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
					jsonObject.put("count", 0);
					return jsonObject.toString();
				}
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					null).toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	// 保存可销售属性
	@RequestMapping(method = RequestMethod.POST, value = "saveSaleShop")
	public String saveSaleShop(@RequestParam(required = false) String params)
			throws NumberFormatException, Exception {
		JSONObject dataJSON = parseObject(params);
		JSONArray saleslist = dataJSON.getJSONArray("saleslist");
		JSONArray supplierlist = dataJSON.getJSONArray("supplierlist");
		Integer headid = dataJSON.getInteger("headid");
		PlmProductHeadEntity_HI headobj = plmProductHeadServer.getById(headid);
		String deleteSql = " delete from PlmProductSaleshopEntity_HI where product_head_id="
				+ headid;

		plmProductHeadServer.execute(deleteSql);

		Map<String, String> placemap = new HashMap<String, String>();
		Map<String, String> shopmap = new HashMap<String, String>();

		for (int i = 0; i < saleslist.size(); i++) {
			JSONObject dat = saleslist.getJSONObject(i);
			if (!dat.containsKey("placeType")) {
				continue;
			}

			JSONObject pa = new JSONObject();
			pa.put("productHeadId", headid);
			pa.put("plmCode", headobj.getPlmCode());
			pa.put("productName", headobj.getProductName());
			pa.put("uid", this.getSessionUserId());
			PlmProductSalesPropertiesEntity_HI entity = JSON.parseObject(
					dat.toString(), PlmProductSalesPropertiesEntity_HI.class);
			String placeType = dat.getString("placeType");
			if (placeType.equals("1")) // 地点清单
			{
				String placeCode = dat.getString("placeCode");
				for (String pcode : placeCode.split(",")) {
					if (!placemap.containsKey(pcode)) {
						placemap.put(pcode, pcode);
						SaleplaceDeal(pa, pcode, entity);
					}
				}
			} else if (placeType.equals("2")) // 区域
			{
				if (!dat.containsKey("areaId")) {
					continue;
				}
				String sudatas = "";
				String area = dat.getString("areaId");

				String liarea = "";
				for (String achild : area.split(",")) {
					liarea += "'" + achild + "',";
				}

				// 获取商品下的所有店铺
				for (int len = 0; len < supplierlist.size(); len++) {
					JSONObject suobj = supplierlist.getJSONObject(len);
					if (!suobj.containsKey("sxWay")) {
						continue;
					}
					String sxway = suobj.getString("sxWay");
					if (sxway.equals("1") || sxway.equals("2")
							|| sxway.equals("5")) // 全部店铺选项
					{
						List<VmiShopEntity_HI_RO2> datashop = new ArrayList<VmiShopEntity_HI_RO2>();

						JSONObject jo = new JSONObject();
						jo.put("areas",
								liarea.substring(0, liarea.length() - 1));
						Pagination<VmiShopEntity_HI_RO2> vshop = vimshopServer
								.findShopinfo(jo, 1, 10000);
						for (VmiShopEntity_HI_RO2 s : vshop.getData()) {
							if (!shopmap.containsKey(s.getVsCode())) {
								shopmap.put(s.getVsCode(), s.getVsCode());
								datashop.add(s);
							}
						}

						SaleShopDeal(datashop, pa, entity);

						return "success";
					} else if (sxway.equals("4") || sxway.equals("7")) {

						String sxStore = suobj.getString("sxStore");
						for (String store : sxStore.split(",")) {
							sudatas += "'" + store + "',";
						}
					} else if (sxway.equals("3") || sxway.equals("8")) {// 地点清单

					}

				}// for supplier
				if (!sudatas.equals("")) {
					List<VmiShopEntity_HI_RO2> dd = new ArrayList<VmiShopEntity_HI_RO2>();
					String ares = "";
					for (String are : area.split(",")) {
						ares += "'" + are + "',";
					}
					String aresall = ares.substring(0, ares.length() - 1);
					String shops = sudatas.substring(0, sudatas.length() - 1);
					JSONObject jq = new JSONObject();
					jq.put("vscodes", shops);
					jq.put("areas", aresall);
					Pagination<VmiShopEntity_HI_RO2> shpa = vimshopServer
							.findShopinfo(jq, 1, 10000);
					List<VmiShopEntity_HI_RO2> shro = shpa.getData();
					for (VmiShopEntity_HI_RO2 r : shro) {
						if (!shopmap.containsKey(r.getVsCode())) {
							dd.add(r);
							shopmap.put(r.getVsCode(), r.getVsCode());
						}
					}

					SaleShopDeal(dd, pa, entity);
				}

			} else if (placeType.equals("3")) // 店铺
			{
				String shops = dat.getString("sxStoreId");
				for (String loc : shops.split(",")) {
					PlmProductSaleshopEntity_HI console = new PlmProductSaleshopEntity_HI();

					if (!shopmap.containsKey(loc)) {
						shopmap.put(loc, loc);
						console.setShopLoc(loc);
						console.setPlmCode(headobj.getPlmCode());
						console.setSalesId(entity.getSalesId());
						console.setProductName(headobj.getProductName());
						console.setProductHeadId(headid);
						console.setCreatedBy(this.getSessionUserId());
						console.setOperatorUserId(this.getSessionUserId());
						console.setCreationDate(new Date());
						console.setIsSales(entity.getSalesProperties());
						console.setLastUpdateDate(new Date());
						console.setLastUpdatedBy(this.getSessionUserId());
						console.setVersionNum(0);
						console.setLastUpdateLogin(this.getSessionUserId());
						saleshop.save(console);
					}
					// ldata.add(console);
				}

			} else if (placeType.equals("4")) {
				// 获取供应商下的所有店铺
				for (int len = 0; len < supplierlist.size(); len++) {
					JSONObject suobj = supplierlist.getJSONObject(len);

					if (!suobj.containsKey("sxWay")) {
						continue;
					}
					String sxway = suobj.getString("sxWay");
					if (sxway.equals("1") || sxway.equals("2")) // 全部店铺选项
					{
						List<VmiShopEntity_HI_RO2> vdata = new ArrayList<VmiShopEntity_HI_RO2>();
						JSONObject shop = new JSONObject();
						Pagination<VmiShopEntity_HI_RO2> vli = vimshopServer
								.findShopinfo(shop, 1, 10000);
						List<VmiShopEntity_HI_RO2> shopro = vli.getData();
						for (VmiShopEntity_HI_RO2 ve : shopro) {
							if (!shopmap.containsKey(ve.getVsCode())) {
								vdata.add(ve);
								shopmap.put(ve.getVsCode(), ve.getVsCode());
							}
						}
						SaleShopDeal(vdata, pa, entity);
						return "success";

					} else if (sxway.equals("3") || sxway.equals("8")) // 地点清单
					{
						if (!suobj.containsKey("placeCode")) {
							continue;
						}
						String placenote = suobj.getString("placeCode");
						SaleplaceDeal(pa, placenote, entity);

					} // 地点清单
					else if (sxway.equals("4") || sxway.equals("7")) { // 指定店铺
						if (!suobj.containsKey("sxStore")) {
							continue;
						}
						String shops = suobj.getString("sxStore");
						for (String loc : shops.split(",")) {
							if (!shopmap.containsKey(loc)) {
								shopmap.put(loc, loc);

								PlmProductSaleshopEntity_HI console = new PlmProductSaleshopEntity_HI();
								console.setShopLoc(loc);
								console.setPlmCode(headobj.getPlmCode());
								console.setSalesId(entity.getSalesId());
								console.setProductName(headobj.getProductName());
								console.setProductHeadId(headid);
								console.setCreatedBy(this.getSessionUserId());
								console.setOperatorUserId(this
										.getSessionUserId());
								console.setCreationDate(new Date());
								console.setIsSales(entity.getSalesProperties());
								console.setLastUpdateDate(new Date());
								console.setLastUpdatedBy(this
										.getSessionUserId());
								console.setVersionNum(0);
								console.setLastUpdateLogin(this
										.getSessionUserId());
								saleshop.save(console);
							}
						}

					} else if (sxway.equals("5"))// 区域
					{
						List<VmiShopEntity_HI_RO2> vdata = new ArrayList<VmiShopEntity_HI_RO2>();
						if (!suobj.containsKey("areaId")) {
							continue;
						}
						String area = suobj.getString("areaId");
						String results = "";
						for (String achild : area.split(",")) {
							results += "'" + achild + "',";
						}
						JSONObject jo = new JSONObject();
						String ares = results
								.substring(0, results.length() - 1);
						jo.put("areas", ares);
						Pagination<VmiShopEntity_HI_RO2> vshop = vimshopServer
								.findShopinfo(jo, 1, 10000);
						List<VmiShopEntity_HI_RO2> sro = vshop.getData();

						for (VmiShopEntity_HI_RO2 ve : sro) {
							if (!shopmap.containsKey(ve.getVsCode())) {
								shopmap.put(ve.getVsCode(), ve.getVsCode());
								vdata.add(ve);
							}
						}
						SaleShopDeal(vdata, pa, entity);

					} else if (sxway.equals("9")) // 仓库及其店铺
					{
						if (suobj.containsKey("sxWarehouseId")) {
							continue;
						}
						String warehouses = suobj.getString("sxWarehouseId");
						for (String ware : warehouses.split(",")) {
							locationMap locmap = new locationMap();
							Map<String, String> mapdata = locmap.getMapData();
							if (mapdata.containsKey(ware)) {
								String wcode = mapdata.get(ware);
								if (!placemap.containsKey(wcode)) {
									placemap.put(wcode, wcode);
									SaleplaceDeal(pa, wcode, entity);
								}
							}

						}
					}

				} // for

			}

		}

		return "success";
	}

	// 可销售属性处理地点清单
	public void SaleplaceDeal(JSONObject params, String wcode,
			PlmProductSalesPropertiesEntity_HI obj)
			throws NumberFormatException, Exception {
		List<JSONObject> datajson = new ArrayList<JSONObject>();
		JSONObject shop = iPlmApiServer.siteListingMethod(new Integer(wcode));
		JSONArray jsonArray = shop.getJSONArray("data_set");
		for (int len = 0; len < jsonArray.size(); len++) {
			JSONObject json = jsonArray.getJSONObject(len);
			if (datajson.size() == 100) {
				InsertSalesShop task = new InsertSalesShop(datajson, params,
						sessionFactory, obj);
				task.run();

				datajson.clear();
				datajson.add(json);

			} else {
				datajson.add(json);
			}
		}
		if (datajson.size() > 0)// 剩余对象 重新发起进程
		{
			InsertSalesShop task = new InsertSalesShop(datajson, params,
					sessionFactory, obj);
			task.run();
			datajson.clear();
		}
	}

	// 可销售属性处理店铺
	public void SaleShopDeal(List<VmiShopEntity_HI_RO2> vshop,
			JSONObject params, PlmProductSalesPropertiesEntity_HI obj) {
		List<VmiShopEntity_HI_RO2> datajson = new ArrayList<VmiShopEntity_HI_RO2>();
		for (VmiShopEntity_HI_RO2 shop : vshop) {
			if (datajson.size() == 500) { // 分线程处理
				InsertSalesShopInstance task = new InsertSalesShopInstance(
						datajson, params, sessionFactory, obj);
				task.run();

				datajson.clear();
				datajson.add(shop);

			} else {
				datajson.add(shop);
			}
		}
		if (datajson.size() > 0) {
			InsertSalesShopInstance task = new InsertSalesShopInstance(
					datajson, params, sessionFactory, obj);
			task.run();
			datajson.clear();
		}
	}

	// 得到商品修改流程参数
	@RequestMapping(method = RequestMethod.POST, value = "getUpdateProductCondition")
	public String getUpdateProductCondition(
			@RequestParam(required = false) String params) throws IllegalAccessException {
		deptNames dept = new deptNames();
		Map<String, String> init = dept.getParams();
		boolean isgm=false;
		boolean bic=false;
		Integer producthead = null;
		Integer ecoId=null;
		JSONObject param = parseObject(params);
		JSONArray elist = param.getJSONArray("data");
		JSONArray elist2 = new JSONArray();
		if (elist.size() == 0) {
			JSONArray data = new JSONArray();
			for (Map.Entry<String, String> mp : init.entrySet()) {
				JSONObject jq = new JSONObject();
				jq.put("name", mp.getKey());
				jq.put("value", mp.getValue());
				jq.put("type", "int");
				data.add(jq);
			}

			JSONObject result = new JSONObject();
			JSONObject deptj = new JSONObject();
			deptj.put("name", "dept");
			deptj.put("type", "string");
			deptj.put("value", "");
			result.put("variables", data);

			return result.toJSONString();
		}

		for (int i = 0; i < elist.size(); i++) {

			JSONObject es = elist.getJSONObject(i);
			if(!es.containsKey("columnName"))
			{
				es.put("columnName","");
			}
			String columnname = es.getString("columnName");
			if(columnname.equals("productReturn"))
			{
				bic=true;
			}
			if (i == 0) {
				producthead = es.getInteger("productHeadId");
				ecoId=es.getInteger("ecoId");
				//break;
			}
			String gmcolumn=dept.getGmColumn(columnname);
			if(!gmcolumn.equals("")){
				if(!gmcolumn.equals("price")&&!gmcolumn.equals("unitPrice")) {
					isgm = true;
				}else {
					//判断gp是否小于0
					PlmProductHeadEcoEntity_HI  headco=plmProductHeadEco.getById(ecoId);
					String gross=headco.getGrossEarnings()==null?"":headco.getGrossEarnings();
					if(!gross.equals("")){
						Float result=Float.valueOf(gross);
						if(result<0){
							isgm = true;
						}


					}
				}
			}

			String tablename=es.getString("tableName");


			if(tablename.equals("PLM_PRODUCT_PRICE_ECO"))
			{
				JSONObject ecoobj = new JSONObject();
				ecoobj.put("ecoId", ecoId);
				ecoobj.put("priceId",es.getInteger("lineId"));
				List<PlmProductPriceEcoEntity_HI> ecoli=plmProductPriceEco.findList(ecoobj);
				if(ecoli.size()>0)
				{

						PlmProductPriceEcoEntity_HI e=ecoli.get(0);
						Date d=e.getUpdatePriceDate();
						Calendar cal = Calendar.getInstance();
						if(d!=null)
						{
							cal.setTime(d);
						}
						int week  = cal.get(Calendar.DAY_OF_WEEK);
						if(week!=5)
						{
							bic=true;
							break;
						}

				}

			}

		}




		if(isgm)
		{
			init.remove("NEEDGM");
			init.put("NEEDGM","1");
		}
		if(bic)
		{
			init.remove("BIC");
			init.put("BIC","1");
		}

		String type = "";
		// 判断商品为ob 或者非Ob
		PlmProductHeadEcoEntity_HI  prohi=plmProductHeadEco.getById(ecoId);
//		PlmProductHeadEntity_HI prohi = plmProductHeadServer
//				.getById(producthead);

		Map<String, String> datali = new HashMap<String, String>();

		String deptlist = "";
		String department = prohi.getDepartment().substring(0, 1);
		String departsort = prohi.getDepartment().substring(0, 4);
		if ((prohi.getProductType().equals("1") || prohi.getProductType()
				.equals("4"))
				&& (department.equals("1") || department.equals("2")
						|| department.equals("3") || department.equals("4")
						|| department.equals("5") || department.equals("6")
						|| departsort.equals("9901") || departsort
							.equals("9907"))) {
			type = "ob";

		} else if ((prohi.getProductType().equals("2")
				|| prohi.getProductType().equals("3")
				|| prohi.getProductType().equals("5") || prohi.getProductType()
				.equals("6"))
				&& (department.equals("1") || department.equals("2")
						|| department.equals("3") || department.equals("4")
						|| department.equals("5") || department.equals("6")
						|| departsort.equals("9901") || departsort
							.equals("9907"))) // 非Ob
		{
			if(prohi.getProductType().equals("2")){
				init.put("BIC1","1");
				init.remove("BIC");
				init.put("BIC","1");
			}
			type = "nonob";
		}

		for (int i = 0; i < elist.size(); i++) {
			List<String> nodelist = new ArrayList<String>();
			JSONObject es = elist.getJSONObject(i);
			String columnname = es.getString("columnName");
			if (type.equals("ob")) {
				deptlist = dept.getObDeptByname(columnname);
			} else if (type.equals("nonob")) {
				deptlist = dept.getNoneobDeptByname(columnname);
			}

			if (!deptlist.equals("")) {
				String[] dp = deptlist.split(",");
				for (String node : dp) {
					if (!datali.containsKey(node)) {
						datali.put(node, node);
						nodelist.add(node);
					}

				}
			}

			for (String node : nodelist) {
				if (init.containsKey(node)) {
					init.remove(node);
					init.put(node, "1"); // 设置为审批状态
				}
			}
		} //for

		JSONArray data = new JSONArray();

		for (Map.Entry<String, String> mp : init.entrySet()) {
			JSONObject jq = new JSONObject();
			jq.put("name", mp.getKey());
			jq.put("value", mp.getValue());
			jq.put("type", "int");
			data.add(jq);
		}

		JSONObject result = new JSONObject();
		JSONObject deptj = new JSONObject();
		deptj.put("name", "dept");
		deptj.put("type", "string");
		deptj.put("value", "");
		result.put("variables", data);

		return result.toJSONString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "synQaFileHis")
	public String synQaFileHis(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			plmProductHeadServer.saveAndSync(queryParamJSON);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null)
					.toString();
		} catch (IllegalArgumentException e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toJSONString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
		}
	}

	/*@PostMapping("findProductInfoById")
	public String findProductInfoById(@RequestParam Integer productHeadId,
									  @RequestParam(required = false) String plmCode)
	{
		PlmProductHeadEntity_HI product = plmProductHeadServer.getById(productHeadId);
		if(product == null) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未查询到productHeadId为"
					+productHeadId+"的记录",0, null).toJSONString();
		}
		try {
			JSONObject Detail = plmProductHeadServer.findProductDetail(param);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, Detail).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}*/
}