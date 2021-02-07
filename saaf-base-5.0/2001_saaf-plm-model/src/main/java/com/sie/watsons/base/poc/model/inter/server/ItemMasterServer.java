package com.sie.watsons.base.poc.model.inter.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.poc.model.entities.ExistedProm;
import com.sie.watsons.base.poc.model.entities.ItemMasterEntity_HI;
import com.sie.watsons.base.poc.model.entities.StoreItem;
import com.sie.watsons.base.poc.model.entities.XxPromHeadEntity_HI;
import com.sie.watsons.base.poc.model.entities.readonly.XxPromGroupsEntity_HI_RO;
import com.sie.watsons.base.poc.model.entities.readonly.XxPromHeadEntity_HI_RO;
import com.sie.watsons.base.poc.model.entities.readonly.XxPromStoreEntity_HI_RO;
import com.sie.watsons.base.poc.model.entities.readonly.XxPromTypeDetailEntity_HI_RO;
import com.sie.watsons.base.poc.model.inter.IItemMaster;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("itemMasterServer")
public class ItemMasterServer extends BaseCommonServer<ItemMasterEntity_HI>
		implements IItemMaster {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ItemMasterServer.class);
	@Autowired
	private ViewObject<ItemMasterEntity_HI> itemMasterDAO_HI;

	@Autowired
	private BaseViewObject<XxPromStoreEntity_HI_RO> xxPromStoreDAO_HI_RO;

	@Autowired
	private BaseViewObject<XxPromGroupsEntity_HI_RO> xxPromGroupsDAO_HI_RO;

	@Autowired
	private BaseViewObject<XxPromTypeDetailEntity_HI_RO> xxPromTypeDetailDAO_HI_RO;

	@Autowired
	private ViewObject<XxPromHeadEntity_HI> xxPromHeadDAO_HI;

	@Autowired
	private BaseViewObject<XxPromHeadEntity_HI_RO> xxPromHeadDAO_HI_RO;

	@Autowired
	private JedisCluster jedis;

	// private Map<String, List<ExistedProm>> storeMap = new HashMap<>();
	private Map<String, String> storeMap = null;

	private final static Map<String, Map<String, String>> map = new HashMap<>();

	private final static Map<String, Map<String, String>> mapOfType = new HashMap<>();

	// @Autowired
	// private BaseViewObject<XxPromStoreEntity_HI_RO> xxPromStoreDAO_HI;
	//
	// @Autowired
	// private BaseViewObject<XxPromStoreEntity_HI_RO> xxPromStoreDAO_HI;

	public ItemMasterServer() {
		super();
	}

	public List<ItemMasterEntity_HI> findItemMasterInfo(
			JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils
				.fastJsonObj2Map(queryParamJSON);
		List<ItemMasterEntity_HI> findListResult = itemMasterDAO_HI.findList(
				"from ItemMasterEntity_HI", queryParamMap);

		return findListResult;
	}

	@Override
	public void addRedis() throws Exception {
		List<XxPromHeadEntity_HI> head = getHead();
		Map<String, String> typeMap = null;
		for (XxPromHeadEntity_HI entityHi : head) {
			typeMap = getTypeMap(entityHi.getXxPromType());
			mapOfType.put(entityHi.getXxPromType(), typeMap);
			List<XxPromGroupsEntity_HI_RO> goods = getGroups(entityHi
					.getXxPromId());
			List<XxPromStoreEntity_HI_RO> store = getStore(entityHi
					.getXxPromId());
			for (XxPromStoreEntity_HI_RO storeEn : store) {
				storeMap = map.get(storeEn.getStore().toString());
				if (storeMap == null) {
					String stores = jedis.get(storeEn.getStore().toString());
					storeMap = (Map<String, String>) JSON.parse(stores);
					if (storeMap == null) {
						storeMap = new HashMap<>();
					}
				}
				ExistedProm existedProm = null;
				for (XxPromGroupsEntity_HI_RO good : goods) {
					if (good.getItem() != null && !"".equals(good.getItem())) {
						String STORE_ITEM = storeEn.getStore() + ":"
								+ good.getItem();
						String item = storeMap.get(STORE_ITEM);
						if (item == null && !"".equals(item)) {
							List<ExistedProm> promList = new ArrayList<>();
							existedProm = new ExistedProm();
							existedProm.setPromType(entityHi.getXxPromType());
							existedProm.setStartDate(storeEn.getStartDate());
							existedProm.setEndDate(storeEn.getEndDate());
							promList.add(existedProm);
							storeMap.put(STORE_ITEM,
									JSON.toJSONString(promList));
						} else {
							JSONArray arr = JSONArray.parseArray(item);
							existedProm = new ExistedProm();
							existedProm.setPromType(entityHi.getXxPromType());
							existedProm.setStartDate(storeEn.getStartDate());
							existedProm.setEndDate(storeEn.getEndDate());
							JSONArray newArr = sortedAdd(arr, existedProm);
							storeMap.put(STORE_ITEM, newArr.toJSONString());
						}
					}
				}
				map.put(storeEn.getStore().toString(), storeMap);
				String map = JSON.toJSONString(storeMap);
				// jedis.hmset(storeEn.getStore(), storeMap);
				jedis.set(storeEn.getStore().toString(), map);
			}
		}
	}

	@Override
	public Object callChecking(JSONObject paramJSON) throws Exception {
		long curTime = System.currentTimeMillis();
		XxPromHeadEntity_HI_RO head = getHead(paramJSON);
		List<XxPromGroupsEntity_HI_RO> goods = getGroups(head.getXxPromId()
				.toString());
		List<XxPromStoreEntity_HI_RO> store = getStore(head.getXxPromId()
				.toString());
		long nexTime = System.currentTimeMillis();
		System.out.print("Time consumed(ms):");
		System.out.println(nexTime - curTime);
		Map<String, String> typeMap = null;
		List<StoreItem> list = new ArrayList<>();
		StoreItem conflictedItem = null;
		CountDownLatch latch = new CountDownLatch(store.size());
		for (XxPromStoreEntity_HI_RO s : store) {
			storeMap = map.get(s.getStore().toString());
			if (storeMap == null) {
				String m = jedis.get(s.getStore().toString());
				storeMap = (Map<String, String>) JSON.parse(m);
				map.put(s.getStore().toString(), storeMap);
			}
			if (storeMap != null) {
				JSONArray arr = null;
				for (XxPromGroupsEntity_HI_RO g : goods) {
					String STORE_ITEM = s.getStore() + ":" + g.getItem();
					String existedGoods = storeMap.get(STORE_ITEM);
					if (existedGoods != null && !"".equals(existedGoods)) {
						arr = JSONArray.parseArray(existedGoods);
						String type = appendArr(arr);
						typeMap = getTypeMap(head.getXxPromType());
						String val = typeMap.get(type);
						if (val == null && "".equals(val)) {
							conflictedItem = new StoreItem();
							conflictedItem.setItem(g.getItem());
							conflictedItem.setStore(s.getStore().toString());
							conflictedItem.setStartDate(s.getStartDate());
							conflictedItem.setEndDate(s.getEndDate());
							list.add(conflictedItem);
						}
					}
				}
			}
		}
		return list;
	}

	// @Override
	// public Object callChecking(JSONObject paramJSON, XxPromHeadEntity_HI_RO
	// head, List<XxPromGroupsEntity_HI_RO> goods, XxPromStoreEntity_HI_RO s)
	// throws Exception {
	// Map<String, String> typeMap = null;
	// List<StoreItem> list = new ArrayList<>();
	// StoreItem conflictedItem = null;
	// storeMap = map.get(s.getStore().toString());
	// if (storeMap == null) {
	// String m = jedis.get(s.getStore().toString());
	// storeMap = (Map<String, String>) JSON.parse(m);
	// map.put(s.getStore().toString(), storeMap);
	// }
	// if (storeMap != null){
	// JSONArray arr = null;
	// for (XxPromGroupsEntity_HI_RO g : goods) {
	// String STORE_ITEM = s.getStore() + ":" + g.getItem();
	// String existedGoods = storeMap.get(STORE_ITEM);
	// if (existedGoods != null && !"".equals(existedGoods)){
	// arr = JSONArray.parseArray(existedGoods);
	// String type = appendArr(arr);
	// typeMap = getTypeMap(head.getXxPromType());
	// String val = typeMap.get(type);
	// if (val == null && "".equals(val)){
	// conflictedItem = new StoreItem();
	// conflictedItem.setItem(g.getItem());
	// conflictedItem.setStore(s.getStore().toString());
	// conflictedItem.setStartDate(s.getStartDate());
	// conflictedItem.setEndDate(s.getEndDate());
	// list.add(conflictedItem);
	// }
	// }
	// }
	// }
	// return null;
	// }

	@Override
	public List<StoreItem> callChecking(JSONObject paramJSON,
			XxPromHeadEntity_HI_RO head, List<XxPromGroupsEntity_HI_RO> goods,
			XxPromStoreEntity_HI_RO s) throws Exception {
		Map<String, String> typeMap = null;
		List<StoreItem> list = new ArrayList<>();
		StoreItem conflictedItem = null;
		storeMap = map.get(s.getStore().toString());
		if (storeMap == null) {
			String m = jedis.get(s.getStore().toString());
			storeMap = (Map<String, String>) JSON.parse(m);
			map.put(s.getStore().toString(), storeMap);
		}
		if (storeMap != null) {
			JSONArray arr = null;
			for (XxPromGroupsEntity_HI_RO g : goods) {
				String STORE_ITEM = s.getStore() + ":" + g.getItem();
				String existedGoods = storeMap.get(STORE_ITEM);
				if (existedGoods != null && !"".equals(existedGoods)) {
					arr = JSONArray.parseArray(existedGoods);
					String type = appendArr(arr);
					if (head.getXxPromType() == null) {
						throw new Exception(head.getXxPromId() + "没有对应的对应的促销类型");
					}
					typeMap = getTypeMap(head.getXxPromType());
					String val = typeMap.get(type);
					if (val == null || "".equals(val)) {
						conflictedItem = new StoreItem();
						conflictedItem.setItem(g.getItem());
						conflictedItem.setStore(s.getStore().toString());
						conflictedItem.setStartDate(s.getStartDate());
						conflictedItem.setEndDate(s.getEndDate());
						list.add(conflictedItem);
					}
				}
			}
		}
		return list;
	}

	@Override
	public Object startCheckingMultiThreads(JSONObject paramJSON) {
		long curTime = System.currentTimeMillis();
		final XxPromHeadEntity_HI_RO head = getHead(paramJSON);
		final CountDownLatch latch = new CountDownLatch(2);
		Callable<List<XxPromGroupsEntity_HI_RO>> goods = new Callable<List<XxPromGroupsEntity_HI_RO>>() {
			@Override
			public List<XxPromGroupsEntity_HI_RO> call() throws Exception {
				latch.countDown();
				return getGroups(head.getXxPromId().toString());
			}
		};
		Callable<List<XxPromStoreEntity_HI_RO>> store = new Callable<List<XxPromStoreEntity_HI_RO>>() {
			@Override
			public List<XxPromStoreEntity_HI_RO> call() throws Exception {
				latch.countDown();
				return getStore(head.getXxPromId().toString());
			}
		};
		Map<String, Object> map = null;
		try {
			map = new HashMap<>();
			ExecutorService executorService = Executors.newCachedThreadPool();
			Future<List<XxPromGroupsEntity_HI_RO>> goodsFut = executorService
					.submit(goods);
			Future<List<XxPromStoreEntity_HI_RO>> storeFut = executorService
					.submit(store);
			latch.await();
			List<XxPromGroupsEntity_HI_RO> l1 = goodsFut.get();
			List<XxPromStoreEntity_HI_RO> l2 = storeFut.get();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		// List<XxPromGroupsEntity_HI_RO> goods =
		// getGroups(head.getXxPromId().toString());
		// List<XxPromStoreEntity_HI_RO> store =
		// getStore(head.getXxPromId().toString());
		long nexTime = System.currentTimeMillis();
		System.out.print("Time consumed(ms):");
		System.out.println(nexTime - curTime);
		return null;
	}

	private String appendArr(JSONArray arr) {
		String result = "";
		if (arr != null && arr.size() > 0) {
			for (int i = 0; i < arr.size(); i++) {
				result += arr.getJSONObject(i).getString("promType") + ",";
			}
		}
		int lastIndex = result.lastIndexOf(",");
		result = result.substring(0, lastIndex);
		return result;
	}

	@Override
	public Object getStoreMap(JSONObject paramJSON) {
		Map<String, String> store = map.get(paramJSON.getString("store"));
		return store;
	}

	@Override
	public void delRedis() {
		XxPromHeadEntity_HI_RO entity = new XxPromHeadEntity_HI_RO();
		// entity.setXxPromId("700148837");
		List<XxPromStoreEntity_HI_RO> store = getStore(entity.getXxPromId()
				.toString());
		for (XxPromStoreEntity_HI_RO entityHi : store) {
			jedis.del(entityHi.getStore().toString());
		}
	}

	@Override
	public void getStoreRedis() {
		String str = jedis.get("197");
		storeMap = (Map<String, String>) JSON.parse(str);
		// List<ExistedProm> en = storeMap.get("100659706");
		String en = storeMap.get("100659706");
		JSONArray arr = JSONArray.parseArray(en);
		// ExistedProm entity = en.get(0);
		// List<Object> b = ((StoreItem) en).getExistedProm();
		// StoreItem storeItem = (StoreItem) en;
		// List<ExistedProm> e = storeItem.getExistedProm();
		// System.out.println(e);
		// String ss = en.get(0).toString();
		// System.out.println(en.get(0).getPromType());
		// System.out.println(en.get(0).getStartDate());
		// System.out.println(en.get(0).getEndDate());
		JSONObject obj = arr.getJSONObject(0);
		System.out.println(obj.getString("startDate"));
		System.out.println(obj.getString("endDate"));
		System.out.println(obj.getString("promType"));
		System.out.println(storeMap);
	}

	public List<XxPromStoreEntity_HI_RO> getStore(String xxPromId) {
		JSONObject param = new JSONObject();
		param.put("xxPromId", xxPromId);
		StringBuffer sql = new StringBuffer(
				"select xps.xx_prom_id xxPromId, xps.store store, xps.start_date startDate, xps.end_date endDate from xx_prom_store xps where 1=1");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(XxPromGroupsEntity_HI_RO.class, param,
				sql, queryParamMap);
		List<XxPromStoreEntity_HI_RO> findListResult = xxPromStoreDAO_HI_RO
				.findList(sql, param);
		return findListResult;
	}

	// private List<XxPromGroupsEntity_HI> getGroups(XxPromHeadEntity_HI
	// entityHi) {
	// JSONObject param = new JSONObject();
	// param.put("xxPromId", entityHi.getXxPromId());
	// StringBuffer sql = new
	// StringBuffer("from XxPromGroupsEntity_HI where 1=1");
	// Map<String, Object> queryParamMap = new HashMap<String, Object>();
	// com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(XxPromGroupsEntity_HI.class,
	// param, sql, queryParamMap);
	// List<XxPromGroupsEntity_HI> findListResult =
	// xxPromGroupsDAO_HI.findList(sql, param);
	// return findListResult;
	// }

	public List<XxPromGroupsEntity_HI_RO> getGroups(String xxPromId) {
		JSONObject param = new JSONObject();
		param.put("xxPromId", xxPromId);
		StringBuffer sql = new StringBuffer(
				"select xpg.item item,xpg.xx_prom_id xxPromId from xx_prom_groups xpg where 1=1 ");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(XxPromGroupsEntity_HI_RO.class, param,
				sql, queryParamMap);
		List<XxPromGroupsEntity_HI_RO> findListResult = xxPromGroupsDAO_HI_RO
				.findList(sql, param);
		return findListResult;
	}

	public List<XxPromTypeDetailEntity_HI_RO> getTypeDetails(String xxPromType) {
		JSONObject param = new JSONObject();
		param.put("xxPromType", xxPromType);
		StringBuffer sql = new StringBuffer(
				"select xptd.xx_prom_type xxPromType,xptd.allow_overlap_type from xx_prom_type_detail xptd where 1=1 ");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(XxPromTypeDetailEntity_HI_RO.class, param,
				sql, queryParamMap);
		List<XxPromTypeDetailEntity_HI_RO> findListResult = xxPromTypeDetailDAO_HI_RO
				.findList(sql, param);
		return findListResult;
	}

	public List<XxPromHeadEntity_HI> getHead() {
		JSONObject queryParamJSON = new JSONObject();
		Map<String, Object> queryParamMap = SToolUtils
				.fastJsonObj2Map(queryParamJSON);
		List<XxPromHeadEntity_HI> findListResult = xxPromHeadDAO_HI.findList(
				"from XxPromHeadEntity_HI where status in ('S','A','E','P')",
				queryParamMap);
		return findListResult;
	}

	public XxPromHeadEntity_HI_RO getHead(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(
				"select xph.xx_prom_id xxPromId, xph.xx_prom_type xxPromType, xph.status status, xph.start_date startDate, xph.end_date endDate from xx_prom_head xph where 1=1");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(XxPromGroupsEntity_HI_RO.class,
				queryParamJSON, sql, queryParamMap);
		List<XxPromHeadEntity_HI_RO> findListResult = xxPromHeadDAO_HI_RO
				.findList(sql, queryParamMap);
		return findListResult.get(0);
	}

	public Object saveItemMasterInfo(JSONObject queryParamJSON) {
		ItemMasterEntity_HI itemMasterEntity_HI = JSON.parseObject(
				queryParamJSON.toString(), ItemMasterEntity_HI.class);
		Object resultData = itemMasterDAO_HI.save(itemMasterEntity_HI);
		return resultData;
	}

	private Map<String, String> getTypeMap(String xxPromType) throws Exception {
		Map<String, String> map = null;
		if (xxPromType != null && !"".equals(xxPromType)) {
			map = mapOfType.get(xxPromType);
			if (map == null) {
				List<XxPromTypeDetailEntity_HI_RO> types = getTypeDetails(xxPromType);
				map = transforToMap(types);
				mapOfType.put(xxPromType, map);
			}
			if (map == null) {
				throw new Exception(xxPromType + "促销类型未配置！");
			}
		}
		return map;
	}

	private Map<String, String> transforToMap(
			List<XxPromTypeDetailEntity_HI_RO> types) {
		Map<String, String> map = null;
		if (types != null && types.size() > 0) {
			map = new HashMap<>();
			for (XxPromTypeDetailEntity_HI_RO type : types) {
				map.put(type.getAllowOverlapType(), "0");
			}
		}
		return map;
	}

	private JSONArray sortedAdd(JSONArray arr, ExistedProm existedProm) {
		int count = 0;
		int existed = Integer.parseInt(existedProm.getPromType());
		for (int i = 0; i < arr.size(); i++) {
			JSONObject curObj = arr.getJSONObject(i);
			int cur = Integer.parseInt(curObj.getString("promType"));
			if (existed < cur) {
				count = i;
				break;
			}
		}
		JSONArray temp = arr;
		int size = arr.size();
		temp.set(count, existedProm);
		int mov = arr.size() - count;
		for (int j = count; j < size; j++) {
			temp.set(count + 1, arr.get(count));
		}
		return temp;
	}

	public static void main(String[] args) {
	}
}
