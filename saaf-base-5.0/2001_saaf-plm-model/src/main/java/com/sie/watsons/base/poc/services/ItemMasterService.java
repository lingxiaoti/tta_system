package com.sie.watsons.base.poc.services;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.poc.model.inter.IItemMaster;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/itemMasterService")
public class ItemMasterService extends CommonAbstractService {
	private static final Logger log = LoggerFactory
			.getLogger(ItemMasterService.class);
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ItemMasterService.class);
	@Autowired
	private IItemMaster itemMasterServer;

	public ItemMasterService() {
		super();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param params
	 *            JSON参数 <br>
	 *            {<br>
	 *            apihId:主键，（更新时必填）<br>
	 *            centerName:项目/中心名称<br>
	 *            centerCode:项目/中心编码<br>
	 *            versionNum:版本号（更新时必填）<br>
	 *            }
	 * @return
	 * @author your name
	 * @creteTime Tue Aug 13 12:21:37 CST 2019
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItemMasterInfo")
	// /restServer/itemMasterService/findItemMasterInfo
	public String findItemMasterInfo(
			@RequestParam(required = true) String params) {
		LOGGER.info(params);
		JSONObject paramJSON = JSON.parseObject(params);
		String resultStr = JSONObject.toJSONString(itemMasterServer
				.findItemMasterInfo(paramJSON));
		LOGGER.info(resultStr);
		return resultStr;
	}

	@RequestMapping(method = RequestMethod.POST, value = "insertRedis")
	public String insertRedis(@RequestParam(required = true) String params)
			throws Exception {
		try {
			JSONObject paramJSON = JSON.parseObject(params);
			itemMasterServer.addRedis();
			String resultStr = "successful";
			LOGGER.info(resultStr);
			return resultStr;
		} catch (IllegalArgumentException e) {
			log.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("E", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return SToolUtils
					.convertResultJSONObj("E", e.getMessage(), 0, null)
					.toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "overlapCheck")
	public String overlapCheck(@RequestParam(required = true) String params)
			throws Exception {
		try {
			JSONObject paramJSON = JSON.parseObject(params);
			String resultStr = JSONObject.toJSONString(itemMasterServer
					.callChecking(paramJSON));
			LOGGER.info(resultStr);
			return resultStr;
		} catch (IllegalArgumentException e) {
			log.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("E", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return SToolUtils
					.convertResultJSONObj("E", e.getMessage(), 0, null)
					.toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "overlapCheckMultiThreads")
	public String overlapCheckMultiThreads(
			@RequestParam(required = true) String params) throws Exception {
		try {
			JSONObject paramJSON = JSON.parseObject(params);
			long curTime = System.currentTimeMillis();
			CountDownLatch latch = new CountDownLatch(2);
			// Callable<XxPromHeadEntity_HI_RO> head = () ->
			// itemMasterServer.getHead(paramJSON);
			// Callable<List<XxPromGroupsEntity_HI_RO>> goods = () -> {
			// latch.countDown();
			// return
			// itemMasterServer.getGroups(paramJSON.getString("xxPromId"));
			// };
			// Callable<List<XxPromStoreEntity_HI_RO>> store = () -> {
			// latch.countDown();
			// return
			// itemMasterServer.getStore(paramJSON.getString("xxPromId"));
			// };
			// ConcurrentHashMap<String, List<StoreItem>> resultsMap = null;
			// ExecutorService executorService =
			// Executors.newCachedThreadPool();
			// Future<XxPromHeadEntity_HI_RO> headFut = executorService
			// .submit(head);
			// Future<List<XxPromGroupsEntity_HI_RO>> goodsFut = executorService
			// .submit(goods);
			// Future<List<XxPromStoreEntity_HI_RO>> storeFut = executorService
			// .submit(store);
			// XxPromHeadEntity_HI_RO h = headFut.get();
			// List<XxPromGroupsEntity_HI_RO> goodslist = goodsFut.get();
			// List<XxPromStoreEntity_HI_RO> storelist = storeFut.get();
			// latch.await();
			// CountDownLatch latch2 = new CountDownLatch(storelist.size());
			// resultsMap = new ConcurrentHashMap<>();
			// for (XxPromStoreEntity_HI_RO s : storelist) {
			// Callable<List<StoreItem>> task = () -> {
			// List<StoreItem> results =
			// itemMasterServer.callChecking(paramJSON, h, goodslist, s);
			// return results;
			// };
			// Future<List<StoreItem>> r = executorService.submit(task);
			// List<StoreItem> storeItemList = r.get();
			// latch2.countDown();
			// if (storeItemList != null && storeItemList.size() > 0) {
			// resultsMap.put(s.getStore().toString(), storeItemList);
			// }
			// }
			// latch2.await();
			// Integer size = 0;
			// for (XxPromStoreEntity_HI_RO s : storelist) {
			// List<StoreItem> results =
			// itemMasterServer.callChecking(paramJSON, h, goodslist, s);
			// if (results != null && results.size() > 0){
			// size += results.size();
			// }
			// }
			// XxPromHeadEntity_HI_RO head =
			// itemMasterServer.getHead(paramJSON);
			// List<XxPromGroupsEntity_HI_RO> goods =
			// itemMasterServer.getGroups(paramJSON.getString("xxProm"));
			// List<XxPromStoreEntity_HI_RO> store =
			// itemMasterServer.getStore(paramJSON.getString("xxPromId"));
			// size = goods.size() + store.size();
			long nexTime = System.currentTimeMillis();
			System.out.print("Time consumed(ms):");
			System.out.println(nexTime - curTime);
			// return size.toString();
			// return resultsMap.toString();
			return "successful";
		} catch (IllegalArgumentException e) {
			log.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("E", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return SToolUtils
					.convertResultJSONObj("E", e.getMessage(), 0, null)
					.toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "delRedis")
	public String delRedis(@RequestParam(required = true) String params) {
		JSONObject paramJSON = JSON.parseObject(params);
		itemMasterServer.delRedis();
		String resultStr = "successful";
		LOGGER.info(resultStr);
		return resultStr;
	}

	@RequestMapping(method = RequestMethod.POST, value = "getStoreRedis")
	public String getStoreRedis(@RequestParam(required = true) String params) {
		JSONObject paramJSON = JSON.parseObject(params);
		itemMasterServer.getStoreRedis();
		// String resultStr =
		// JSONObject.toJSONString(itemMasterServer.findItemMasterInfo(paramJSON));
		String resultStr = "successful";
		LOGGER.info(resultStr);
		return resultStr;
	}

	@RequestMapping(method = RequestMethod.POST, value = "getStoreMap")
	public String getStoreMap(@RequestParam(required = true) String params) {
		JSONObject paramJSON = JSON.parseObject(params);
		String resultStr = JSONObject.toJSONString(itemMasterServer
				.getStoreMap(paramJSON));
		LOGGER.info(resultStr);
		return resultStr;
	}
}
