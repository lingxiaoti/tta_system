package com.sie.watsons.base.api.model.inter.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.HttpClientUtil;
import com.sie.watsons.base.api.model.entities.PlmUdaAttributeEntity_HI;
import com.sie.watsons.base.api.model.inter.IPlmUda;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import org.springframework.util.ObjectUtils;

/**
 * Created by Administrator on 2020/1/10/010.
 */
@Component("plmUdaServer")
public class PlmUdaServer implements IPlmUda {
	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(Plm UdaServer.class);

	@Autowired
	private ViewObject<PlmProductHeadEntity_HI> plmProductHeadDAO_HI;
	@Autowired
	private ViewObject<PlmUdaAttributeEntity_HI> plmUdaAttributeDAO_HI;
	@Autowired
	private ViewObject<PlmProductSupplierInfoEntity_HI> PlmProductSupplierInfoDAO_HI;

	@Override
	public void updateItemSupp(JSONObject queryJSON) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String currentDate = format.format(new Date());
		String date = queryJSON.get("date").toString();
		String getUserIdUrl = "http://10.82.24.180/rms/master/item_supp?update_date="
				+ currentDate;
		// String getUserIdUrl =
		// "http://10.82.24.180/rms/master/item_supp?update_date="+"20191030";
		String jsonStr = HttpClientUtil.send(getUserIdUrl);
		System.out.println(jsonStr);
		JSONObject json = JSON.parseObject(jsonStr);

		JSONArray arr = json.getJSONArray("data_set");
		for (int i = 0; i < arr.size(); i++) {
			JSONObject queryParamJSON = arr.getJSONObject(i);

			String sql = new String();
			sql = " "
					+ "from PlmProductSupplierInfoEntity_HI where  productHeadId = "
					+ "(select productHeadId from PlmProductHeadEntity_HI where rmsCode ='"
					+ queryParamJSON.get("item").toString() + "') "
					+ "and supplierCode='"
					+ queryParamJSON.get("supplier").toString() + "'";
			Map<String, Object> map = new HashMap<String, Object>();
			List<PlmProductSupplierInfoEntity_HI> lineList = PlmProductSupplierInfoDAO_HI
					.findList(sql);
			PlmProductSupplierInfoEntity_HI entity = null;

			for (int k = 0; k < lineList.size(); k++) {
				entity = lineList.get(k);
				if (entity != null) {
					if (queryParamJSON.get("supplier") != null) {
						entity.setSupplierId(queryParamJSON.get("supplier")
								.toString());
					}
					if (queryParamJSON.get("primary_supp_ind") != null) {
						entity.setIsMainsupplier(queryParamJSON.get(
								"primary_supp_ind").toString());
					}
					if (queryParamJSON.get("origin_country_id") != null) {
						// wh_consign_ind
						entity.setCountryCode(queryParamJSON.get(
								"origin_country_id").toString());
					}
					if (queryParamJSON.get("unit_cost") != null) {
						entity.setCurrencyCost(queryParamJSON.get("unit_cost")
								.toString());
					}
					if (queryParamJSON.get("supp_pack_size") != null) {
						entity.setPackageSpe(Integer.parseInt(queryParamJSON
								.get("supp_pack_size").toString()));
					}
					if (queryParamJSON.get("inner_pack_size") != null) {
						entity.setInnerpackageSpe(Integer
								.parseInt(queryParamJSON.get("inner_pack_size")
										.toString()));
					}
					if (queryParamJSON.get("round_to_case_pct") != null) {
						entity.setRoundToCasePct(queryParamJSON.get(
								"round_to_case_pct").toString());
					}
					if (queryParamJSON.get("round_to_inner_pct") != null) {
						entity.setRoundToInnerPct(queryParamJSON.get(
								"round_to_inner_pct").toString());
					}
					if (queryParamJSON.get("round_to_layer_pct") != null) {
						entity.setRoundToLayerPct(queryParamJSON.get(
								"round_to_layer_pct").toString());
					}
					if (queryParamJSON.get("round_to_pallet_pct") != null) {
						entity.setRoundToPalletPct(queryParamJSON.get(
								"round_to_pallet_pct").toString());
					}
					if (queryParamJSON.get("hi") != null) {
						entity.setHi(queryParamJSON.get("hi").toString());
					}
					if (queryParamJSON.get("ti") != null) {
						entity.setTi(queryParamJSON.get("ti").toString());
					}

					PlmProductSupplierInfoDAO_HI.saveOrUpdate(entity);
				}
			}

		}
	}

	@Override
	public void updateItemMaster(JSONObject queryJSON) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String currentDate = format.format(new Date());
		String getUserIdUrl = "http://10.82.24.180/rms/master/item?update_date="
				+ currentDate;
		// String getUserIdUrl =
		// "http://10.82.24.180/rms/master/item?update_date="+"20191030";
		String jsonStr = HttpClientUtil.send(getUserIdUrl);
		System.out.println(jsonStr);
		JSONObject json = JSON.parseObject(jsonStr);

		JSONArray arr = json.getJSONArray("data_set");
		for (int i = 0; i < arr.size(); i++) {
			JSONObject queryParamJSON = arr.getJSONObject(i);
			String sql = new String();
			sql = "from PlmProductHeadEntity_HI s where rmsCode ='"
					+ queryParamJSON.get("item").toString() + "' ";
			Map<String, Object> map = new HashMap<String, Object>();
			List<PlmProductHeadEntity_HI> lineList = plmProductHeadDAO_HI
					.findList(sql, map);
			PlmProductHeadEntity_HI entity = null;
			if (lineList.size() > 0) {
				entity = lineList.get(i);
			}
			if (entity != null) {
				if (queryParamJSON.get("subclass") != null) {
					entity.setSubClass(queryParamJSON.get("subclass")
							.toString());
				}
				if (queryParamJSON.get("item_desc") != null) {
					entity.setClassDesc(queryParamJSON.get("item_desc")
							.toString());
				}
				if (queryParamJSON.get("group_no") != null) {
					entity.setGroupBrand(queryParamJSON.get("group_no")
							.toString());
				}
				if (queryParamJSON.get("dept") != null) {
					entity.setDepartment(queryParamJSON.get("dept").toString());
				}
				if (queryParamJSON.get("class") != null) {
					entity.setClasses(queryParamJSON.get("class").toString());
				}
				if (queryParamJSON.get("status") != null) {
					// waste_pct
					entity.setOrderStatus(queryParamJSON.get("status")
							.toString());
				}

				plmProductHeadDAO_HI.saveOrUpdate(entity);
			}
		}
	}

	@Override
	public void updateUdaInformation(JSONObject queryJSON) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String currentDate = format.format(new Date());
		String getUserIdUrl = "http://10.82.24.180/rms/master/uda?update_date="
				+ currentDate;
		// String getUserIdUrl =
		// "http://10.82.24.180/rms/master/uda?update_date="+"20191030";
		String jsonStr = HttpClientUtil.send(getUserIdUrl);
		System.out.println(jsonStr);
		JSONObject json = JSON.parseObject(jsonStr);

		JSONArray arr = json.getJSONArray("data_set");
		String item = "";
		PlmProductHeadEntity_HI head = null;
		for (int i = 0; i < arr.size(); i++) {
			Map<String,List<JSONObject>> udaUpdateList = new HashMap<>();
			List<String> items = arr.stream().map(js -> JSONObject.parseObject(js.toString()).getString("item")).distinct().collect(Collectors.toList());
			for (String itemParam:items) {
				List<Object> list= arr.stream().filter(a->itemParam.equals(JSON.parseObject(a.toString()).getString("item"))).collect(Collectors.toList());
				//查询货品
				String sql = "from PlmProductHeadEntity_HI  where rmsCode ='"
						+ itemParam + "' ";
				Map<String, Object> map = new HashMap<String, Object>();
				List<PlmProductHeadEntity_HI> lineList = plmProductHeadDAO_HI
						.findList(sql, map);
				if (ObjectUtils.isEmpty(lineList)) {
					continue;
				}
				head = lineList.get(0);
				for (Object j : list){
					JSONObject queryJ = JSONObject.parseObject(j.toString());
					updateByUdaId(head,queryJ.get("uda_id").toString(),queryJ.get("uda_value").toString());
				}
				plmProductHeadDAO_HI.saveOrUpdate(head);
			}


//			JSONObject queryParamJSON = arr.getJSONObject(i);
//			String sql = new String();
//			if (item.equals("")
//					|| !item.equals(queryParamJSON.get("item").toString())) {
//				item = queryParamJSON.get("item").toString();
//				sql = "from PlmProductHeadEntity_HI  where rmsCode ='"
//						+ queryParamJSON.get("item").toString() + "' ";
//				Map<String, Object> map = new HashMap<String, Object>();
//				List<PlmProductHeadEntity_HI> lineList = plmProductHeadDAO_HI
//						.findList(sql, map);
//				if (lineList.size() > 0) {
//					head = lineList.get(0);
//				}
//			}
//
//			if (head != null && queryParamJSON.get("uda_id") != null) {
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("16")) {
//					head.setCountUnit(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("18")) {
//					head.setOriginCountry(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("27")) {
//					head.setUnit(queryParamJSON.get("uda_value").toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("35")) {
//					head.setWarehouseGetDay(new Integer(queryParamJSON.get(
//							"uda_value").toString()));
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("36")) {
//					head.setSxDays(Integer.parseInt(queryParamJSON.get(
//							"uda_value").toString()));
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("37")) {
//					head.setWarehousePostDay(new Integer(queryParamJSON.get(
//							"uda_value").toString()));
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("42")) {
//					head.setProductEname(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("2")) {
//					head.setValidDays(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("3")) {
//					head.setSpecialLicence(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("4")) {
//					head.setProductType(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("5")) {
//					head.setProductResource(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("7")) {
//					head.setPricewarProduct(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("8")) {
//					head.setUniqueCommodities(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("9")) {
//					head.setSpecialtyProduct(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("10")) {
//					head.setProductProperties(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("11")) {
//					head.setBuyingLevel(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("12")) {
//					head.setDangerousProduct(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("13")) {
//					head.setPosInfo(queryParamJSON.get("uda_value").toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("15")) {
//					head.setInternationProduct(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("29")) {
//					head.setSesionProduct(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("41")) {
//					head.setProductReturn(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("45")) {
//					head.setTopProduct(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("61")) {
//					head.setBluecapProduct(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("62")) {
//					head.setCrossborderProduct(queryParamJSON.get("uda_value")
//							.toString());
//				}
//				if (queryParamJSON.get("uda_id") != null
//						&& queryParamJSON.get("uda_id").toString().equals("63")) {
//					head.setVcProduct(queryParamJSON.get("uda_value")
//							.toString());
//				}
//
//				plmProductHeadDAO_HI.saveOrUpdate(head);
//			}
		}
	}

	/**
	 * 根据uda_id 修改货品属性
	 * @param head
	 * @param uda_id
	 * @param uda_value
	 */
	private void updateByUdaId(PlmProductHeadEntity_HI head, String uda_id, String uda_value) {
		if (uda_id != null
				&& uda_id.equals("16")) {
			head.setCountUnit(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("18")) {
			head.setOriginCountry(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("27")) {
			head.setUnit(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("35")) {
			head.setWarehouseGetDay(new Integer(uda_value));
		}
		if (uda_id != null
				&& uda_id.equals("36")) {
			head.setSxDays(Integer.parseInt(uda_value));
		}
		if (uda_id != null
				&& uda_id.equals("37")) {
			head.setWarehousePostDay(new Integer(uda_value));
		}
		if (uda_id != null
				&& uda_id.equals("42")) {
			head.setProductEname(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("2")) {
			head.setValidDays(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("3")) {
			head.setSpecialLicence(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("4")) {
			head.setProductType(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("5")) {
			head.setProductResource(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("7")) {
			head.setPricewarProduct(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("8")) {
			head.setUniqueCommodities(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("9")) {
			head.setSpecialtyProduct(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("10")) {
			head.setProductProperties(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("11")) {
			head.setBuyingLevel(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("12")) {
			head.setDangerousProduct(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("13")) {
			head.setPosInfo(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("15")) {
			head.setInternationProduct(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("29")) {
			head.setSesionProduct(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("41")) {
			head.setProductReturn(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("45")) {
			head.setTopProduct(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("61")) {
			head.setBluecapProduct(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("62")) {
			head.setCrossborderProduct(uda_value);
		}
		if (uda_id != null
				&& uda_id.equals("63")) {
			head.setVcProduct(uda_value);
		}

	}

	public static void main(String[] args) throws IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String currentDate = format.format(new Date());
		String getUserIdUrl = "http://10.82.24.180/rms/master/item?update_date="
				+ "20191030";
		String jsonStr = HttpClientUtil.send(getUserIdUrl);
		System.out.println(jsonStr);
		JSONObject paramJSON = JSON.parseObject(jsonStr);
		JSONArray arr = paramJSON.getJSONArray("data_set");
		for (int i = 0; i < arr.size(); i++) {
			JSONObject queryParamJSON = arr.getJSONObject(i);
			System.out.println(queryParamJSON.get("item").toString());
		}

	}
}
