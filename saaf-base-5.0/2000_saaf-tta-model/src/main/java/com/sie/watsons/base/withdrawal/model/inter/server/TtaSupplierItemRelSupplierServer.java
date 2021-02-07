package com.sie.watsons.base.withdrawal.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemRelSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.utils.RollBack;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemRelSupplierEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemRelSupplier;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;

import javax.transaction.SystemException;

@Component("ttaSupplierItemRelSupplierServer")
public class TtaSupplierItemRelSupplierServer extends BaseCommonServer<TtaSupplierItemRelSupplierEntity_HI> implements ITtaSupplierItemRelSupplier{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemRelSupplierServer.class);

	@Autowired
	private ViewObject<TtaSupplierItemRelSupplierEntity_HI> ttaSupplierItemRelSupplierDAO_HI;

	@Autowired
	private BaseViewObject<TtaSupplierItemRelSupplierEntity_HI_RO> ttaSupplierItemRelSupplierDAO_HI_RO;

	@Autowired
	private BaseViewObject<TtaRelSupplierEntity_HI_RO> ttaRelSupplierDAO_HI_RO;

	@Autowired
	private BaseCommonDAO_HI<TtaSupplierItemRelSupplierEntity_HI> baseCommonDAO_hi;

	public TtaSupplierItemRelSupplierServer() {
		super();
	}

	/**
	 * 查询关联供应商列表
	 * @param queryParamJSON 查询参数
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> findSupplierItemRelSupplierList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception {
		StringBuffer sb = new StringBuffer(TtaSupplierItemRelSupplierEntity_HI_RO.SELECT_REL_SUPPLIER);
		Map<String,Object> queryParamMap = new HashMap<>();
		Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> pagination = null;
		if (queryParamJSON.containsKey("supplierItemId")) {
			Object object = queryParamJSON.get("supplierItemId");
			if (null == object) {
				queryParamMap.put("supplierItemHId",-1000);
			} else {
				queryParamMap.put("supplierItemHId",(Integer)object);
			}
		}else {
				pagination = new Pagination<TtaSupplierItemRelSupplierEntity_HI_RO>();
				return pagination;
			//queryParamMap.put("supplierItemHId",-1000);
		}

		//SaafToolUtils.parperParam(queryParamJSON,"sirs.supplier_item_h_id","supplierItemHId",sb,queryParamMap,"=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sb, "sirs.supplier_item_h_id desc", false);
		 pagination = ttaSupplierItemRelSupplierDAO_HI_RO.findPagination(sb, queryParamMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> findSupplierItemRelSupplierListBySupplierItemId(Integer supplierItemId) throws Exception{
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("supplierItemId",supplierItemId);
		Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> supplierItemRelSupplierList = this.findSupplierItemRelSupplierList(queryParamJSON, 1, 8);
		return supplierItemRelSupplierList;
	}

	@Override
	public List<Map<String, Object>> queryNamedSQLForList(String sql, Map<String, Object> queryMap) {
		return baseCommonDAO_hi.queryNamedSQLForList(sql, queryMap);
	}

	/**
	 * 根据头表的供应商查询关联供应商
	 * @param queryParamJSON 参数
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination<TtaRelSupplierEntity_HI_RO> selectSupplierItemRelSupplierList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaRelSupplierEntity_HI_RO.SELECT_ITEM_SUPPLIER);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if (SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("supplierId"))) {
			queryParamJSON.put("supplierId",-1);
		}

		SaafToolUtils.parperParam(queryParamJSON, "t.rel_Id", "supplierId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "t.rel_supplier_code", "relSupplierCode", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "t.rel_supplier_name", "relSupplierName", sql, paramsMap, "like");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "t.rel_Supplier_Id desc", false);
		Pagination<TtaRelSupplierEntity_HI_RO> findList = ttaRelSupplierDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	/**
	 * proposal 拆分与合并,保存关联供应商
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TtaSupplierItemRelSupplierEntity_HI> ttaSupplierItemRelSupplierSave(JSONObject queryParamJSON) throws Exception {
		Assert.notNull(queryParamJSON.getJSONArray("proposalSupplierList"),"未选择供应商数据,保存失败");
		LOGGER.info("参数集合: {}",queryParamJSON.getJSONArray("proposalSupplierList").toString());
		Integer supplierItemId = queryParamJSON.getInteger("supplierItemId");
		if (null == supplierItemId) {
			throw new IllegalArgumentException("单据未保存,请选保存单据,再新增供应商");
		}
		JSONObject data = queryParamJSON.getJSONObject("data");//获取供应商等数据
		String supplierCode = data.getString("supplierCode");
		String supplierName = data.getString("supplierName");
		LOGGER.info("供应商编号: {},供应商名称:{}",supplierCode,supplierName);

		JSONArray proposalSupplierList = queryParamJSON.getJSONArray("proposalSupplierList");
		if (proposalSupplierList == null && proposalSupplierList.size() == 0) {
			throw new IllegalArgumentException("未选择供应商数据,保存失败!");
		}
		List<TtaRelSupplierEntity_HI> ttaRelSupplierEntity_hiList = proposalSupplierList.toJavaList(TtaRelSupplierEntity_HI.class);

		//去重集合
		Set<String> checkSet = new HashSet<>();

		List<TtaSupplierItemRelSupplierEntity_HI> list = new ArrayList<>();
		for (TtaRelSupplierEntity_HI entity_hi : ttaRelSupplierEntity_hiList) {
			TtaSupplierItemRelSupplierEntity_HI  relSupplierEntityHi= new TtaSupplierItemRelSupplierEntity_HI();

			Map<String,Object> properties = new HashMap<>();
			properties.put("relSupplierCode",entity_hi.getRelSupplierCode());
			//检查重复
			List<TtaSupplierItemRelSupplierEntity_HI> byProperty = ttaSupplierItemRelSupplierDAO_HI.findByProperty(properties);
			if (null != byProperty && byProperty.size() > 0) {
				checkSet.add(entity_hi.getRelSupplierCode());
			}
			Integer userId = queryParamJSON.getInteger("varUserId");
			relSupplierEntityHi.setSupplierItemHId(supplierItemId);
			relSupplierEntityHi.setRelSupplierCode(entity_hi.getRelSupplierCode());
			relSupplierEntityHi.setRelSupplierName(entity_hi.getRelSupplierName());
			relSupplierEntityHi.setMarjorSupplierCode(supplierCode);
			relSupplierEntityHi.setMarjorSupplierName(supplierName);
			relSupplierEntityHi.setCreationDate(new Date());
			relSupplierEntityHi.setCreatedBy(userId);
			relSupplierEntityHi.setLastUpdateDate(new Date());
			relSupplierEntityHi.setLastUpdatedBy(userId);
			relSupplierEntityHi.setLastUpdateLogin(userId);
			relSupplierEntityHi.setOperatorUserId(userId);
			list.add(relSupplierEntityHi);
		}

		if (checkSet.size() > 0) {
			throw new IllegalArgumentException("供应商编号重复:" + StringUtils.join(checkSet.toArray()) + ";保存失败!");
		}
		ttaSupplierItemRelSupplierDAO_HI.saveOrUpdateAll(list);
		return list;
	}

	/**
	 * 删除关联供应商
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaSupplierItemRelSupplierEntity_HI ttaSupplierItemRelSupplierDelete(Integer id) throws Exception {
		LOGGER.info("参数id :{}",id);
		TtaSupplierItemRelSupplierEntity_HI dao_hiById = ttaSupplierItemRelSupplierDAO_HI.getById(id);
		if (null == dao_hiById) {
			throw new IllegalArgumentException("你选择的数据不存在,删除失败");
		}
		ttaSupplierItemRelSupplierDAO_HI.delete(dao_hiById);
		return dao_hiById;
	}

	/**
	 * 回滚已经拆分的sale数据
	 * @param mainLatch
	 * @param threadLatch
	 * @param rollBack
	 * @param resultList
	 * @param supplierItemId
	 * @param joinSupplierStr
	 * @param monthDay
	 * @param finalSplitString
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateRollbackSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList,
											 Integer supplierItemId, String joinSupplierStr, String monthDay, String finalSplitString,List<String> supplierList) throws Exception {
		String msg = "success";
		Boolean result = false;

		/*List<Map<String,String>> mapList= new ArrayList<>();
		String supplierCodeStr = "";
		for (TtaSupplierItemMidEntity_HI midEntity_hi : supplierItemMidEntity_hi) {
			String supplierCode = midEntity_hi.getSupplierCode();
            String lastSplitSupplierCode = midEntity_hi.getLastSplitSupplierCode();
            //1) 一种情况:供应商不同;
			// 2)另一种情况,供应商相同,上一次版本的拆分供应商不同 (supplierCodeStr.equals(supplierCode) && !lastSplitSupplierCodeStr.equals(lastSplitSupplierCode) && supplierList.contains(supplierCode))
			if ((!supplierCodeStr.equals(supplierCode) && supplierList.contains(supplierCode))) {//不同供应商
				Map<String,String> map = new HashMap<>();
				map.put(supplierCode,lastSplitSupplierCode);
				mapList.add(map);
				supplierCodeStr = supplierCode;
                //lastSplitSupplierCodeStr = lastSplitSupplierCode;

			}
		}*/
		try {
		/*	for (Map<String, String> stringMap : mapList) {
				for (Map.Entry<String, String> entry : stringMap.entrySet()) {
					String updateSql = "update tta_sale_sum_"+monthDay+" ttas\n" +
							"   set ttas.split_supplier_code = '"+entry.getValue()+"',\n" +
							"       ttas.split_count         = ttas.split_count - 1\n" +
							" where ttas.vendor_nbr='"+entry.getKey()+"' \n" +
							"   and ttas.item_nbr in\n" +
							"       (select distinct tai.item_nbr\n" +
							"          from tta_item tai\n" +
							"         where exists (\n" +
							"              select 1\n" +
							"                  from tta_supplier_item_mid tsim\n" +
							"                 where tsim.supplier_item_h_id = "+supplierItemId+"\n" +
							"                   "+finalSplitString+"\n" +
							"           )) ";

					int count = baseCommonDAO_hi.executeSqlUpdate(updateSql);
					LOGGER.info("线程: {},回滚指定供应商条数 : {}", Thread.currentThread().getName(),count);
					if (count < 0) {
						result = true;
					}
				}
			}*/

			String updateSql = "update tta_sale_sum_"+monthDay+" ttas\n" +
					"   set ttas.split_supplier_code =\n" +
					"       (select t.supplier_code \n" +
					"          from (select tai.item_nbr,\n" +
					"                       tsim.supplier_code,\n" +
					"                       tsim.split_supplier_code,\n" +
					"                       row_number() over(partition by tai.item_nbr order by tai.item_nbr desc) flag\n" +
					"                  from tta_item tai\n" +
					"                  join (select \n" +
					"                              tm.mid,\n" +
					"                              tm.supplier_code,\n" +
					"                              tm.split_supplier_code,\n" +
					"                              tm.group_code,\n" +
					"                              tm.dept_code,\n" +
					"                              tm.brand_name,\n" +
					"                              tm.item_code\n" +
					"                         from tta_supplier_item_mid tm\n" +
					"                        where tm.supplier_item_h_id ="+supplierItemId+") tsim\n" +
					"                    on (\n" +
					finalSplitString +
					"                       )) t\n" +
					"         where t.flag = 1\n" +
					"           and t.item_nbr = ttas.item_nbr\n" +
					"        \n" +
					"        ),\n" +
					"       ttas.split_count  = ttas.split_count - 1\n" +
					" where ttas.vendor_nbr in ("+joinSupplierStr+")\n" +
					"   and exists (select 1\n" +
					"          from (select tai.item_nbr,\n" +
					"                       tsim.split_supplier_code,\n" +
					"                       row_number() over(partition by tai.item_nbr order by tai.item_nbr desc) flag\n" +
					"                  from tta_item tai\n" +
					"                  join (select tm.mid,\n" +
					"                              tm.split_supplier_code,\n" +
					"                              tm.group_code,\n" +
					"                              tm.dept_code,\n" +
					"                              tm.brand_name,\n" +
					"                              tm.item_code\n" +
					"                         from tta_supplier_item_mid tm\n" +
					"                        where tm.supplier_item_h_id ="+supplierItemId+") tsim\n" +
					"                    on (\n" +
					finalSplitString +
					"                       )) t\n" +
					"         where t.flag = 1\n" +
					"           and t.item_nbr = ttas.item_nbr)";

			int count = baseCommonDAO_hi.executeSqlUpdate(updateSql);
			LOGGER.info("线程: {},表: {},回滚更新条数为: {}", Thread.currentThread().getName(),"tta_sale_sum_" + monthDay ,count);
			if (count < 0) {
				result = true;
			}

			System.out.println(Thread.currentThread().getName() +"线程进入");
			//Exception 和 Error 都需要抓
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			LOGGER.info("线程: {},回滚指定供应商出现异常： {} ", Thread.currentThread().getName(), throwable);
			result = true;
			msg = "fail";
		}

		resultList.add(result);
		threadLatch.countDown();
		LOGGER.info("子线程 {} 执行过程已经结束，等待主线程通知是否需要回滚", Thread.currentThread().getName());

		try {
			//等待主线程的判断逻辑执行完，执行下面的是否回滚逻辑
			mainLatch.await();
			LOGGER.info("更新的表:{}","tta_sale_sum_" + monthDay);
			LOGGER.info("等待主线程执行,然后子线程 {} 再次启动", Thread.currentThread().getName());
		} catch (InterruptedException e) {
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程InterruptedException异常");
		}

		//所有子线程超时,需要回滚
		if (rollBack.getRollBack()) {
			LOGGER.error("批量更新指定供应商线程回滚, 线程: {}",
					Thread.currentThread().getName());
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程回滚");
		}
		return msg;
	}

	/**
	 * 回滚已经拆分的purchase数据
	 * @param mainLatch
	 * @param threadLatch
	 * @param rollBack
	 * @param resultList
	 * @param supplierItemId
	 * @param joinSupplierStr
	 * @param yearItem
	 * @param finalSplitString
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateRollbackPurchaseSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack,
													 BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr,
													 Integer yearItem, String finalSplitString,List<String> supplierList) throws Exception {
		String msg = "success";
		Boolean result = false;


		/*List<Map<String,String>> mapList = new ArrayList<>();
		String supplierCodeStr = "";
		for (TtaSupplierItemMidEntity_HI midEntity_hi : supplierItemMidEntity_hi) {
			String supplierCode = midEntity_hi.getSupplierCode();
			String lastSplitSupplierCode = midEntity_hi.getLastSplitSupplierCode();
			//1) 一种情况:供应商不同;
			// 2)另一种情况,供应商相同,上一次版本的拆分供应商不同 (supplierCodeStr.equals(supplierCode) && !lastSplitSupplierCodeStr.equals(lastSplitSupplierCode) && supplierList.contains(supplierCode))
			if ((!supplierCodeStr.equals(supplierCode) && supplierList.contains(supplierCode))) {//不同供应商
				Map<String,String> map = new HashMap<>();
				map.put(supplierCode,lastSplitSupplierCode);
				mapList.add(map);
				supplierCodeStr = supplierCode;
			}
		}*/

		try {
			/*for (Map<String, String> stringMap : mapList) {
				for (Map.Entry<String, String> entry : stringMap.entrySet()) {
					String updateSql = "update tta_purchase_in_"+yearItem+" tpi\n" +
							"   set tpi.split_supplier_code = '"+entry.getValue()+"',\n" +
							"       tpi.split_count         = tpi.split_count - 1\n" +
							" where tpi.vendor_nbr ='"+entry.getKey()+"'\n" +
							"   and tpi.item_nbr in\n" +
							"       (select distinct tai.item_nbr\n" +
							"          from tta_item tai\n" +
							"         where exists (\n" +
							"              select 1\n" +
							"                  from tta_supplier_item_mid tsim\n" +
							"                 where tsim.supplier_item_h_id = "+supplierItemId+"\n" +
							"                   "+finalSplitString+"\n" +
							"           )) ";

					//添加数据成功,那么返回result = true;还可以返回结果
					int count = baseCommonDAO_hi.executeSqlUpdate(updateSql);
					LOGGER.info("线程: {},更新指定供应商条数 : {}", Thread.currentThread().getName(),count);
					if (count < 0) {
						result = true;
					}
				}
			}*/
			String splitSql = "update tta_purchase_in_"+yearItem+" ttas\n" +
					"   set ttas.split_supplier_code =\n" +
					"       (select t.supplier_code \n" +
					"          from (select tai.item_nbr,\n" +
					"                       tsim.supplier_code,\n" +
					"                       tsim.split_supplier_code,\n" +
					"                       row_number() over(partition by tai.item_nbr order by tai.item_nbr desc) flag\n" +
					"                  from tta_item tai\n" +
					"                  join (select \n" +
					"                              tm.mid,\n" +
					"                              tm.supplier_code,\n" +
					"                              tm.split_supplier_code,\n" +
					"                              tm.group_code,\n" +
					"                              tm.dept_code,\n" +
					"                              tm.brand_name,\n" +
					"                              tm.item_code\n" +
					"                         from tta_supplier_item_mid tm\n" +
					"                        where tm.supplier_item_h_id ="+supplierItemId+") tsim\n" +
					"                    on (\n" +
					finalSplitString +
					"                       )) t\n" +
					"         where t.flag = 1\n" +
					"           and t.item_nbr = ttas.item_nbr\n" +
					"        \n" +
					"        ),\n" +
					"       ttas.split_count         = ttas.split_count - 1\n" +
					" where ttas.vendor_nbr in ("+joinSupplierStr+")\n" +
					"   and exists (select 1\n" +
					"          from (select tai.item_nbr,\n" +
					"                       tsim.split_supplier_code,\n" +
					"                       row_number() over(partition by tai.item_nbr order by tai.item_nbr desc) flag\n" +
					"                  from tta_item tai\n" +
					"                  join (select tm.mid,\n" +
					"                              tm.split_supplier_code,\n" +
					"                              tm.group_code,\n" +
					"                              tm.dept_code,\n" +
					"                              tm.brand_name,\n" +
					"                              tm.item_code\n" +
					"                         from tta_supplier_item_mid tm\n" +
					"                        where tm.supplier_item_h_id =" + supplierItemId + ") tsim\n" +
					"                    on (\n" +
					finalSplitString +
					"                       )) t\n" +
					"         where t.flag = 1\n" +
					"           and t.item_nbr = ttas.item_nbr)";


			int count = baseCommonDAO_hi.executeSqlUpdate(splitSql);
			LOGGER.info("线程: {},回滚操作表: {},回滚指定供应商条数 : {}", Thread.currentThread().getName(),"tta_purchase_in_" + yearItem,count);
			if (count < 0) {
				result = true;
			}

			System.out.println(Thread.currentThread().getName() +"线程***********执行回滚指定供应商到采购表操作***********");
			//Exception 和 Error 都需要抓
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			LOGGER.info("线程: {},更新指定供应商出现异常： {} ", Thread.currentThread().getName(), throwable);
			result = true;
			msg = "fail";
		}

		resultList.add(result);
		threadLatch.countDown();
		LOGGER.info("子线程 {} 执行过程已经结束，等待主线程通知是否需要回滚", Thread.currentThread().getName());

		try {

			//等待主线程的判断逻辑执行完，执行下面的是否回滚逻辑
			mainLatch.await();
			LOGGER.info("更新的表:{}","tta_purchase"+yearItem);
			LOGGER.info("等待主线程执行,然后子线程 {} 再次启动", Thread.currentThread().getName());
		} catch (InterruptedException e) {
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程InterruptedException异常");
		}

		//所有子线程超时,需要回滚
		if (rollBack.getRollBack()) {
			LOGGER.error("批量更新指定供应商线程回滚, 线程: {}",
					Thread.currentThread().getName());
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程回滚");
		}

		return msg;
	}

	/**
	 * OI账单场景一
	 * @param mainLatch
	 * @param threadLatch
	 * @param rollBack
	 * @param resultList
	 * @param supplierItemId
	 * @param joinSupplierStr
	 * @param startDate
	 * @param endDate
	 * @param finalSplitString
	 * @param supplierList
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateRollbackUpdateTotalOiSceneSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, String startDate, String endDate, String finalSplitString, List<String> supplierList,Integer oiType) throws Exception {
		LOGGER.info("=====================================回滚拆分场景" + oiType + "开始=============================================");
		String msg = "success";
		Boolean result = false;

		String talbeName = TtaSupplierItemHeaderServer.OI_MAP.get(oiType);
		String  updateSql = getUpdateSql(oiType,joinSupplierStr,startDate,endDate,supplierItemId,finalSplitString);


		/*String updateSql = "update tta_oi_sales_scene_sum tai\n" +
				"                       set (tai.split_supplier_code,tai.split_count) =\n" +
				"                           (select tsim.last_split_supplier_code,                             \n" +
				"                                   tai.split_count - 1\n" +
				"                              from (select *       \n" +
				"                                      from tta_supplier_item_mid tm\n" +
				"                                     where tm.supplier_item_h_id = "+supplierItemId+") tsim\n" +
				"                             where tsim.supplier_code = tai.vendor_nbr " + finalSplitString +
 				"                                )\n" +
				"                     where tai.account_month >= to_number('"+startDate+"') and tai.account_month <= to_number('"+endDate+"') \n" +
				"                       and exists (select 1\n" +
				"                              from (select *\n" +
				"                                      from tta_supplier_item_mid tm\n" +
				"                                     where tm.supplier_item_h_id ="+supplierItemId+") tsim\n" +
				"                             where tsim.supplier_code = tai.vendor_nbr " + finalSplitString +
				"                            )";*/

		try {
				//添加数据成功,那么返回result = true;还可以返回结果
				int count = baseCommonDAO_hi.executeSqlUpdate(updateSql);
				LOGGER.info("线程: {},回滚指定供应商条数 : {},操作表为:{}", Thread.currentThread().getName(),count,talbeName);
				if (count < 0) {
					result = true;
				}
			LOGGER.info(Thread.currentThread().getName() +"线程***********执行回滚指定供应商到表:("+talbeName+"),操作***********");
			//Exception 和 Error 都需要抓
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			LOGGER.info("线程: {},更新指定供应商出现异常： {} ", Thread.currentThread().getName(), throwable);
			result = true;
			msg = "fail";
		}

		resultList.add(result);
		threadLatch.countDown();
		LOGGER.info("子线程 {} 执行过程已经结束，等待主线程通知是否需要回滚,回滚场景表("+talbeName+"),", Thread.currentThread().getName());

		try {

			//等待主线程的判断逻辑执行完，执行下面的是否回滚逻辑
			mainLatch.await();
			LOGGER.info("更新的表:{}","tta_oi_sales_scene_sum");
			LOGGER.info("等待主线程执行,然后子线程 {} 再次启动", Thread.currentThread().getName());
		} catch (InterruptedException e) {
			msg = "fail";
			throw new SystemException("批量回滚操作,回滚指定供应商线程InterruptedException异常");
		}

		//所有子线程超时,需要回滚
		if (rollBack.getRollBack()) {
			LOGGER.error("批量更新指定供应商线程回滚, 线程: {}",
					Thread.currentThread().getName());
			msg = "fail";
			throw new SystemException("批量回滚操作,回滚指定供应商线程回滚");
		}
		LOGGER.info("=====================================回滚拆分场景" + oiType + "结束=============================================");
		return msg;
	}

	private String getOiOneSql(String joinSupplierStr,String startDate, String endDate,Integer supplierItemId,String finalSplitString,String oiSceneTableName){
		String finalSplitString1 = " and " + finalSplitString;

		String sql = "update "+oiSceneTableName+" tai\n" +
					"   set tai.split_supplier_code =(\n" +
					"        select \n" +
					"         tsim.supplier_code\n" +
					"           from (select \n" +
					"            tm.split_supplier_code,\n" +
					"            tm.supplier_code,\n" +
					"  			 tm.group_code,\n" +
					"            tm.group_name,\n" +
					"            tm.dept_code,\n" +
					"            tm.dept_name,\n" +
					"            tm.brand_code,\n" +
					"            tm.brand_name,\n" +
					"            tm.item_code,\n" +
					"            tm.item_name\n" +
					"            from tta_supplier_item_mid tm where tm.supplier_item_h_id ="+supplierItemId+"\n" +
					"            ) tsim\n" +
					"          where 1 = 1 " + finalSplitString1 +
					"     ),\n" +
					"       tai.split_count         = tai.split_count + 1\n" +
					" where tai.vendor_nbr in ("+joinSupplierStr+") \n" +
					" and tai.account_month >= to_number('"+startDate+"') \n" +
					" and tai.account_month <= to_number('"+endDate+"') \n" +
					"   and exists (select 1\n" +
					"          from (select *\n" +
					"                  from tta_supplier_item_mid tm\n" +
					"                 where tm.supplier_item_h_id ="+supplierItemId+") tsim\n" +
					"         where 1 = 1 " + finalSplitString1 +
					"        )";
		return sql;
	}

	private String getUpdateSql(Integer oiType,String joinSupplierStr,String startDate, String endDate,Integer supplierItemId,String finalSplitString) {
		//1.tta_oi_sales_scene_sum   2.tta_oi_po_scene_sum   3.tta_oi_po_rv_scene_sum  4.tta_oi_aboi_ng_suit_scene_sum   5.tta_oi_aboi_suit_scene_sum  6.tta_oi_ln_scene_sum
		return getOiOneSql(joinSupplierStr,startDate,endDate,supplierItemId,finalSplitString,TtaSupplierItemHeaderServer.OI_MAP.get(oiType));
	}

}
