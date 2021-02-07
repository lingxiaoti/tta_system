package com.sie.watsons.base.supplier.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sie.saaf.base.mailUtil.EmailModule.EmailNonceResult;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierBrandEntity_HI;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaAbnormalSupplierBrandEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierBrandEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.ITtaRelSupplier;
import com.sie.watsons.base.supplier.model.inter.ITtaRelSupplierBrand;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemRelSupplier;
import com.sie.watsons.base.withdrawal.utils.TransferObjectUtil;
import com.sie.watsons.base.withdrawal.utils.WDatesUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.supplier.model.entities.TtaAbnormalSupplierBrandEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.supplier.model.inter.ITtaAbnormalSupplierBrand;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;


@Component("ttaAbnormalSupplierBrandServer")
public class TtaAbnormalSupplierBrandServer extends BaseCommonServer<TtaAbnormalSupplierBrandEntity_HI> implements ITtaAbnormalSupplierBrand{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAbnormalSupplierBrandServer.class);

	@Autowired
	private ViewObject<TtaAbnormalSupplierBrandEntity_HI> ttaAbnormalSupplierBrandDAO_HI;
	@Autowired
	private BaseViewObject<TtaSupplierEntity_HI_RO> ttaSupplierDAO_HI_RO;
	@Autowired
	private BaseViewObject<TtaAbnormalSupplierBrandEntity_HI_RO> ttaAbnormalSupplierBrandDAO_HI_RO;
	@Autowired
	private BaseViewObject<TtaRelSupplierBrandEntity_HI_RO> ttaRelSupplierBrandDAO_HI_RO;
	@Autowired
	private DynamicBaseViewObject commonDAO_HI_DY;
	@Autowired
	private BaseCommonDAO_HI<TtaAbnormalSupplierBrandEntity_HI> baseCommonDAO_hi;
	@Autowired
	private ITtaSupplierItemRelSupplier ttaSupplierItemRelSupplierServer;
	@Autowired
	private GenerateCodeService codeService;
	@Autowired
	private ITtaRelSupplierBrand ttaRelSupplierBrandServer;

	private ExecutorService customerExecutorService = new ThreadPoolExecutor(15, 20, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	public TtaAbnormalSupplierBrandServer() {
		super();
	}

	@Override
	public int callSupplierBrandByNoExixstTTA(JSONObject queryParamJson, int userId) throws Exception {
		Integer year = queryParamJson.getInteger("year");
		Assert.notNull(year,"缺失年份,刷新品牌信息需要年份条件");
		int currentYear = WDatesUtils.getYear(new Date(), "yyyy");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		List<String> from2MonthList = null;
		List<Integer> from2YearList = null;
		String startMonth = "";
		String endMonth = "";
		Date startDate = null;
		Date endDate = null;
		if (year == null || year == currentYear) {//如果年份为空,默认为系统的时间
			String callBrandSql = "select * from ( select * from  tta_call_data_time_increase tcd order by tcd.create_date desc) tl where rownum = 1";
			List<Map<String, Object>> list = (List<Map<String, Object>>) commonDAO_HI_DY.findList(callBrandSql);
			if (CollectionUtils.isNotEmpty(list)) {
				Map<String, Object> objectMap = list.get(0);
				String startCallMonth = (String)objectMap.get("CREATE_DATE");
				startMonth = WDatesUtils.getAddMonth(startCallMonth,"yyyyMM");//起始月
				endMonth = SaafDateUtils.convertDateToString(new Date(),"yyyyMM");//结束月
				//如果在同一个月再次刷数据,提示
				if (startCallMonth.equals(endMonth)) {
					throw new IllegalArgumentException("系统中已存在当前月份" + endMonth + "的异常供应商品牌数据,无需再次重刷");
				}
				from2MonthList = WDatesUtils.getFrom2Month(startMonth,endMonth);//获取起始月和结束月之间的月份
				startDate =  df.parse(startMonth);
				endDate = df.parse(endMonth);
				from2YearList = WDatesUtils.getMonthBetween(startDate,endDate);//获取起始月或结束月之间的年份
				Set<String> monthList = new LinkedHashSet();
				for (String montS : from2MonthList) {
					String countSql = "select count(1) nums from user_tables ut where ut.TABLE_NAME = 'TTA_SALE_SUM_" + montS + "'";
					List<Map<String, Object>> countSqlList = (List<Map<String, Object>>)getTableIsExixts(countSql);
					BigDecimal count  = (BigDecimal)countSqlList.get(0).get("NUMS");
					if (count.intValue() == 0) {//表不存在
						monthList.add(montS);
					} else {
						String rornumSql = "select * from tta_sale_sum_" + montS + " ts where rownum = 1";
						List daoHiDyList = getTableCount(rornumSql);
						if (null == daoHiDyList || daoHiDyList.isEmpty()) {
							monthList.add(montS);
						}
					}
				}
				List<String> purchaseYearList = new ArrayList<>();
				for (Integer yearIndx : from2YearList) {
					String purchaseSql = "select count(1) nums from user_tables ut where ut.TABLE_NAME = 'TTA_PURCHASE_IN_" + yearIndx + "'";
					List<Map<String, Object>> purchaseList = (List<Map<String, Object>>)getTableIsExixts(purchaseSql);
					BigDecimal purchaseNum  = (BigDecimal)purchaseList.get(0).get("NUMS");
					if (purchaseNum.intValue() == 0) {
						purchaseYearList.add(yearIndx + "");
					} else {
						String rornumSql = "select * from TTA_PURCHASE_IN_" + yearIndx + " ts where rownum = 1";
						List tableCount = getTableCount(rornumSql);
						if (null == tableCount || tableCount.isEmpty()) {
							purchaseYearList.add(yearIndx + "");
						}
					}
				}
				if (monthList.size() > 0 || purchaseYearList.size() > 0) {
					String str = "";
					if (purchaseYearList.size() > 0) {//SALES数据
						String joinStr = StringUtils.join(purchaseYearList,",");
						str += "当前系统不存在【"+joinStr+"】年度的PO数据,";
					}

					if (monthList.size() > 0) {
						String join = StringUtils.join(monthList, ",");
						str += "当前系统不存在【"+join+"】月份的SALES数据,";
					}
					str += "刷新数据失败!";
					throw new IllegalArgumentException(str);
				}
			} else {
				from2MonthList = WDatesUtils.getAllMonth(new Date());
				startMonth = from2MonthList.get(0);
				endMonth = from2MonthList.get(from2MonthList.size() - 1);
				startDate = df.parse(startMonth);
				endDate = df.parse(endMonth);
				from2YearList = WDatesUtils.getMonthBetween(startDate,endDate);
			}
		} else if (year == 2019) {
			String lastYearSql = "select count(1) exixs_nums from tta_abnormal_supplier_brand tasb where substr(tasb.account_month,0,4) = '"+year+"'";
			List<Map<String,Object>> lastYearList = (List<Map<String,Object>>)getTableCount(lastYearSql);
			BigDecimal exixs_nums = (BigDecimal)lastYearList.get(0).get("EXIXS_NUMS");
			if (exixs_nums.intValue() != 0) {
				throw new IllegalArgumentException("你已刷了当前年度"+year+"的异常供应商品牌数据,不能再次重刷");
			}
			from2MonthList = WDatesUtils.getFrom2Month("201901","201912");
			startMonth = "201901";
			endMonth = "201912";
			startDate = df.parse(startMonth);
			endDate = df.parse(endMonth);
			from2YearList = WDatesUtils.getMonthBetween(startDate,endDate);
		}

		String queryVendor = "select ts.supplier_code,ts.supplier_name from tta_supplier ts ";
		List<TtaSupplierEntity_HI_RO> vendorList = ttaSupplierDAO_HI_RO.findList(queryVendor, new HashMap<>());
		String brandBatchCode = codeService.getRefreshBrandBatchCode();
		List<Map<String,Object>> resultList = new ArrayList<>();
		List<Future<List<Map<String,Object>>>> futures = new ArrayList<>();
		//从tta_sale_sum_xx表刷取数
		from2MonthList.forEach(month ->{
			futures.add(customerExecutorService.submit(new TackRefreshSupplierBrandCable(month)));
		});
		//从tta_purchase_in_xx表刷取数
		String finalStartMonth = startMonth;
		String finalEndMonth = endMonth;
		from2YearList.forEach(m ->{
			futures.add(customerExecutorService.submit(new RefreshBrandPurchaseCallable(finalStartMonth, finalEndMonth,String.valueOf(m))));
		});

		int supplierBrandNum = 0;
		for (Future<List<Map<String, Object>>> future : futures) {
			List<Map<String, Object>> mapList = future.get();
			supplierBrandNum += mapList.size();
			if (CollectionUtils.isEmpty(mapList)) continue;
			mapList = mapList.stream().filter(objectMap -> (null != objectMap.get("SUPPLIER_CODE") && !"".equals((String) objectMap.get("SUPPLIER_CODE")) && !"null".equalsIgnoreCase((String) objectMap.get("SUPPLIER_CODE")))).collect(Collectors.toList());
			for (Map<String, Object> map : mapList) {
				map.put("batch_number",brandBatchCode);
				map.put("active_status","1");
				map.put("creation_date",new Date());
				map.put("created_by",userId);
				map.put("last_update_date",new Date());
				map.put("last_updated_by",userId);
				map.put("last_update_login",userId);
				map.put("version_num",1);
			}
			resultList.addAll(mapList);
		}
		LOGGER.info("刷新不存在TTA系统的供应商品牌数为:{}",supplierBrandNum);
		List<TtaAbnormalSupplierBrandEntity_HI_RO> entityHiRos = JSON.parseArray(JSON.toJSONString(resultList), TtaAbnormalSupplierBrandEntity_HI_RO.class);
		//去重并排序
		entityHiRos = entityHiRos.stream().distinct().sorted((x,y)->{
			if (x.getGroupCode().equalsIgnoreCase(y.getGroupCode())){
				return x.getGroupName().compareTo(y.getGroupName());
			}
			return x.getGroupCode().compareTo(y.getGroupCode());
		}).collect(Collectors.toList());
		LOGGER.info("去重之后的供应商品牌数为:{}",entityHiRos.size());

		//查询不存在供应商品牌表的数据
		List<TtaAbnormalSupplierBrandEntity_HI_RO> daoHiRoList = ttaAbnormalSupplierBrandDAO_HI_RO.findList(TtaAbnormalSupplierBrandEntity_HI_RO.QUERY, new HashMap<>());
		if (CollectionUtils.isNotEmpty(daoHiRoList)) {
			entityHiRos.removeAll(daoHiRoList);//对比,取出差异数据
		}

		if (entityHiRos.size() > 0) {
			Set<String> monthSet = new HashSet<>();
			List<Map<String,Object>> saveList = new ArrayList<>();
			for (TtaAbnormalSupplierBrandEntity_HI_RO entityHi : entityHiRos) {
				String accountMonth = entityHi.getAccountMonth();
				monthSet.add(accountMonth);
				entityHi.setYear(entityHi.getAccountMonth().substring(0,4));
				entityHi.setIsCreate("0");
				for (TtaSupplierEntity_HI_RO vendorEntity : vendorList) {
					if(entityHi.getSupplierCode().equals(vendorEntity.getSupplierCode())){
						entityHi.setSupplierName(vendorEntity.getSupplierName());
						break;
					}
				}
				saveList.add(TransferObjectUtil.objToMap(entityHi));
			}
			Set<String> sortSet = new TreeSet<>(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			sortSet.addAll(monthSet);
			baseCommonDAO_hi.saveSeqBatchJDBC("tta_abnormal_supplier_brand",saveList,"SUPPLIER_BRAND_ID","seq_tta_abnormal_supplier_brand.nextval");
			for (String monthStr : sortSet) {
				commonDAO_HI_DY.executeUpdate("insert into tta_call_data_time_increase values(seq_tta_call_data_time_increase.nextval,"+monthStr+",'"+brandBatchCode+"')");
			}
		}
		return entityHiRos.size();
	}

	private List getTableIsExixts(String tableSql){
		if (StringUtils.isBlank(tableSql)) return null;
		return commonDAO_HI_DY.findList(tableSql);
	}

	private List getTableCount(String countSql){
		if (StringUtils.isBlank(countSql)) return null;
		return ttaSupplierItemRelSupplierServer.queryNamedSQLForList(countSql, new HashMap<>());
	}

	class TackRefreshSupplierBrandCable implements Callable<List<Map<String,Object>>> {
		private String startMonth;

		public TackRefreshSupplierBrandCable(){

		}

		public TackRefreshSupplierBrandCable(String startMonth){
			this.startMonth = startMonth;
		}

		@Override
		public List<Map<String, Object>> call() throws Exception {
			LOGGER.info("当前刷新供应商品牌数的线程:{},刷新月份为:{},执行表名:{}",Thread.currentThread().getName(),startMonth,"tta_sale_sum_" + startMonth);
			String countSql = "select count(1) nums from user_tables ut where ut.TABLE_NAME = 'TTA_SALE_SUM_"+startMonth+"'";
			List<Map<String, Object>> countSqlList = ttaSupplierItemRelSupplierServer.queryNamedSQLForList(countSql, new HashMap<>());
			BigDecimal count  = (BigDecimal)countSqlList.get(0).get("NUMS");
			if (count.intValue() == 0) return Collections.EMPTY_LIST;
			String querySql = "select max(st.tran_date) account_month,\n" +
					"       st.vendor_nbr supplier_code,\n" +
					"       item.group_code group_code,\n" +
					"       item.group_desc group_name,\n" +
					"       item.dept_code dept_code,\n" +
					"       item.dept_desc dept_name,\n" +
					"       item.brand_code brand_code,\n" +
					"       item.brand_cn brand_cn,\n" +
					"       item.brand_en brand_en \n" +
					"       from (select max(ts.tran_date) tran_date,ts.vendor_nbr,ts.item_nbr from tta_sale_sum_"+startMonth+" ts \n" +
					"group by ts.vendor_nbr,ts.item_nbr) st  \n" +
					" inner join (select *\n" +
					"               from (SELECT t1.item_nbr,\n" +
					"                            t1.group_code,\n" +
					"                            t1.group_desc,\n" +
					"                            t1.dept_code,\n" +
					"                            t1.dept_desc,\n" +
					"                            t1.brand_code,\n" +
					"                            t1.brand_cn,\n" +
					"                            t1.brand_en,\n" +
					"                            ROW_NUMBER() OVER(PARTITION BY t1.item_nbr ORDER BY t1.item_nbr) row_id\n" +
					"                       FROM tta_item t1\n" +
					"                      order by t1.last_update_date desc) t2\n" +
					"              where t2.row_id = 1) item\n" +
					"    on st.item_nbr = item.item_nbr\n" +
					"    where not exists(\n" +
					"          select 1 from tta_supplier t inner join  tta_rel_supplier_brand trsb on t.supplier_id = trsb.rel_id  where trsb.group_code = item.group_code and trsb.group_name = item.group_desc\n" +
					"          and trsb.dept_code = item.dept_code and trsb.dept_name = item.dept_desc\n" +
					"           --and trsb.rel_brand_code = item.brand_code\n" +
					"          --and trsb.rel_brand_name_en = item.brand_en\n" +
					"          and trsb.rel_brand_name = item.brand_cn  and st.vendor_nbr = t.supplier_code\n" +
					"    ) \n" +
					"       group by \n" +
					"       st.vendor_nbr,\n" +
					"       item.group_code,\n" +
					"       item.group_desc,\n" +
					"       item.dept_code,\n" +
					"       item.dept_desc,\n" +
					"       item.brand_code,\n" +
					"       item.brand_cn,\n" +
					"       item.brand_en";
			return ttaSupplierItemRelSupplierServer.queryNamedSQLForList(querySql,new HashMap<>());
		}

	}

	class RefreshBrandPurchaseCallable implements Callable<List<Map<String,Object>>> {
		private String startMonth;
		private String endMonth;
		private String year;

		public RefreshBrandPurchaseCallable(){

		}
		public RefreshBrandPurchaseCallable(String startMonth,String endMonth,String year){
			this.startMonth = startMonth;
			this.endMonth = endMonth;
			this.year = year;
		}

		@Override
		public List<Map<String, Object>> call() throws Exception {
			LOGGER.info("当前刷新供应商品牌数的线程:{},刷新年份为:{},执行表名:{}",Thread.currentThread().getName(),year,"tta_purchase_in" + year);
			String countSql = "select count(1) nums from user_tables ut where ut.TABLE_NAME = 'TTA_PURCHASE_IN_"+year+"'";
			List<Map<String, Object>> countSqlList = ttaSupplierItemRelSupplierServer.queryNamedSQLForList(countSql, new HashMap<>());
			BigDecimal count  = (BigDecimal)countSqlList.get(0).get("NUMS");
			if (count.intValue() == 0) return Collections.EMPTY_LIST;
			String querySql = "select    " +
					"          max(ttc.trade_year_month) account_month,\n" +
					"          tpi.vendor_nbr supplier_code,\n" +
					"          item.group_code group_code,\n" +
					"          item.group_desc group_name,\n" +
					"          item.dept_code dept_code,\n" +
					"          item.dept_desc dept_name,\n" +
					"          item.brand_code brand_code,\n" +
					"          item.brand_cn brand_cn,\n" +
					"          item.brand_en brand_en \n" +
					"  from (select max(tpp.receive_date) receive_date,\n" +
					"               tpp.vendor_nbr,\n" +
					"               tpp.item_nbr\n" +
					"          from tta_purchase_in_"+year+" tpp\n" +
					"         group by tpp.vendor_nbr, tpp.item_nbr) tpi\n" +
					" inner join tta_trade_calendar ttc\n" +
					"    on tpi.receive_date between ttc.week_start and ttc.week_end\n" +
					" inner join (select *\n" +
					"               from (SELECT t1.item_nbr,\n" +
					"                            t1.group_code,\n" +
					"                            t1.group_desc,\n" +
					"                            t1.dept_code,\n" +
					"                            t1.dept_desc,\n" +
					"                            t1.brand_code,\n" +
					"                            t1.brand_cn,\n" +
					"                            t1.brand_en,\n" +
					"                            ROW_NUMBER() OVER(PARTITION BY t1.item_nbr ORDER BY t1.item_nbr) row_id\n" +
					"                       FROM tta_item t1\n" +
					"                      order by t1.last_update_date desc) t2\n" +
					"              where t2.row_id = 1) item\n" +
					"    on tpi.item_nbr = item.item_nbr\n" +
					" where ttc.trade_year_month <= "+endMonth+"\n" +
					"   and ttc.trade_year_month >= "+startMonth+"\n" +
					"   and not exists(\n" +
					"          select 1 from tta_supplier t \n" +
					"          inner join  tta_rel_supplier_brand trsb on t.supplier_id = trsb.rel_id \n" +
					"          where trsb.group_code = item.group_code and trsb.group_name = item.group_desc\n" +
					"          and trsb.dept_code = item.dept_code and trsb.dept_name = item.dept_desc\n" +
					"           --and trsb.rel_brand_code = item.brand_code\n" +
					"          --and trsb.rel_brand_name_en = item.brand_en\n" +
					"          and trsb.rel_brand_name = item.brand_cn  and tpi.vendor_nbr = t.supplier_code\n" +
					"    )\n" +
					" group by tpi.vendor_nbr,\n" +
					"          item.group_code,\n" +
					"          item.group_desc,\n" +
					"          item.dept_code,\n" +
					"          item.dept_desc,\n" +
					"          item.brand_code,\n" +
					"          item.brand_cn,\n" +
					"          item.brand_en";
			return ttaSupplierItemRelSupplierServer.queryNamedSQLForList(querySql,new HashMap<>());
		}
	}

	@Override
	public Pagination<TtaAbnormalSupplierBrandEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaAbnormalSupplierBrandEntity_HI_RO.QUERY);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tsb.supplier_code", "supplierCode", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "tsb.supplier_name", "supplierName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON,"tsb.account_month","accountMonth",sql,paramsMap,"=");
		SaafToolUtils.parperParam(queryParamJSON, "tsb.active_status", "activeStatus", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "tsb.is_create", "isCreate", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tsb.supplier_brand_id desc", false);
		Pagination<TtaAbnormalSupplierBrandEntity_HI_RO> findList = ttaAbnormalSupplierBrandDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public void saveOrUpdate(JSONObject queryJsonParam, int userId) throws Exception {
		Assert.notNull(queryJsonParam.getJSONArray("data"),"请选择需要保存的行");
		JSONArray paramJSONArray = queryJsonParam.getJSONArray("data");
		for (Object object : paramJSONArray) {
			TtaAbnormalSupplierBrandEntity_HI entity = SaafToolUtils.setEntity(TtaAbnormalSupplierBrandEntity_HI.class, JSONObject.parseObject(JSON.toJSONString(object)), ttaAbnormalSupplierBrandDAO_HI, userId);
			entity.setLastUpdateDate(new Date());
			entity.setLastUpdatedBy(userId);
			entity.setLastUpdateLogin(userId);
			ttaAbnormalSupplierBrandDAO_HI.saveOrUpdate(entity);
		}
	}

	@Override
	public void insertSupplierBrand(JSONObject queryJsonParam, int userId) throws Exception {
		Assert.notNull(queryJsonParam.getJSONArray("data"),"请选择需要生成供应商品牌信息的行");
		JSONArray jsonArray = queryJsonParam.getJSONArray("data");
		jsonArray = jsonArray.stream().filter(object -> {
			JSONObject jsonObject = (JSONObject)object;
			return "1".equals(jsonObject.getString("activeStatus"));
		}).collect(Collectors.toCollection(JSONArray::new));
		List<TtaAbnormalSupplierBrandEntity_HI> supplierBrandEntity_his = new ArrayList<>();
		Set<String> repeatSet = new LinkedHashSet<>();
		for (Object o : jsonArray) {
			JSONObject jsonObject = (JSONObject) o;
			String activeStatus = jsonObject.getString("activeStatus");
			String isCreate = jsonObject.getString("isCreate");
			if ("1".equals(isCreate)){//筛选已经生成供应商品牌信息的数据
				repeatSet.add(jsonObject.getString("supplierCode"));
			}
			if ("1".equals(activeStatus)) {//筛选生效的数据
				TtaAbnormalSupplierBrandEntity_HI entityHi = SaafToolUtils.setEntity(TtaAbnormalSupplierBrandEntity_HI.class, jsonObject, ttaAbnormalSupplierBrandDAO_HI, userId);
				this.setParams(entityHi,userId);
				supplierBrandEntity_his.add(entityHi);
			}
		}
		if (repeatSet.size() > 0) {
			String s = StringUtils.join(repeatSet, ",");
			String msg = "生成供应商品牌信息错误,供应商编号" + s + "有已经生成供应商品牌信息的数据了,请检查!";
			throw new IllegalArgumentException(msg);
		}
		if (supplierBrandEntity_his.size() == 0) {
			return ;
		}

		String queryVendor = "select ts.supplier_id, ts.supplier_code,ts.supplier_name from tta_supplier ts ";
		List<TtaSupplierEntity_HI_RO> vendorList = ttaSupplierDAO_HI_RO.findList(queryVendor, new HashMap<>());
		List<TtaRelSupplierBrandEntity_HI_RO> relSupplierBrandList = ttaRelSupplierBrandDAO_HI_RO.findList(TtaRelSupplierBrandEntity_HI_RO.TTA_REL_SUPPLIER_BRAND_V, new HashMap<>());

		//List<TtaRelSupplierBrandEntity_HI> ttaRelSupplierBrandEntity_his = new ArrayList<>();
		List<Map<String,Object>> saveRelSupplierBrandList = new ArrayList<>();
		if (supplierBrandEntity_his.size() > 0) {
			for (int i = 0; i < supplierBrandEntity_his.size(); i++) {
				TtaAbnormalSupplierBrandEntity_HI brandEntityHi = supplierBrandEntity_his.get(i);
				Integer supplierId = -1;
				for (TtaSupplierEntity_HI_RO supplierEntityHiRo : vendorList) {
					if (supplierEntityHiRo.getSupplierCode().equals(brandEntityHi.getSupplierCode())){
						supplierId = supplierEntityHiRo.getSupplierId();//供应商id
						TtaRelSupplierBrandEntity_HI entityHi = setObjectAttribute(supplierEntityHiRo, brandEntityHi, userId);
						saveRelSupplierBrandList.add(TransferObjectUtil.objToMap(entityHi));
						//ttaRelSupplierBrandEntity_his.add(entityHi);
						break;
					}
				}
				if (supplierId == -1){
					throw new IllegalArgumentException("供应商"+ brandEntityHi.getSupplierCode() + "在系统中不存在,请检查!");
				}

				for (TtaRelSupplierBrandEntity_HI_RO hiRo : relSupplierBrandList) {
					Integer relId = hiRo.getRelId();
					String groupCode = hiRo.getGroupCode();
					String groupName = hiRo.getGroupName();
					String deptCode = hiRo.getDeptCode();
					String deptName = hiRo.getDeptName();
					String relBrandCode = StringUtils.isBlank(hiRo.getRelBrandCode()) ? "" : hiRo.getRelBrandCode();
					String relBrandName = hiRo.getRelBrandName();
					String relBrandNameEn = hiRo.getRelBrandNameEn();
					if (relId.equals(supplierId) && groupCode.equals(brandEntityHi.getGroupCode()) && groupName.equals(brandEntityHi.getGroupName())
							&& deptCode.equals(brandEntityHi.getDeptCode()) && deptName.equals(brandEntityHi.getDeptName())
							&& relBrandCode.equals(StringUtils.isBlank(brandEntityHi.getBrandCode()) ? "" : brandEntityHi.getBrandCode()) && relBrandName.equals(brandEntityHi.getBrandCn())){//暂时不比较英文名,因为tta_rel_supplier_brand确实品牌英文名
						String tipMsg = "供应商" + brandEntityHi.getSupplierCode() + "的品牌【"+brandEntityHi.getGroupName()+"-"+brandEntityHi.getDeptName()
								+ "-" + brandEntityHi.getBrandCn() +"】,已经在系统中存在,生成供应商品牌信息失败";
						throw new IllegalArgumentException(tipMsg);
					}
				}
			}

			List<Map<String,Object>> mapList = new ArrayList<>();
			for (TtaAbnormalSupplierBrandEntity_HI abnormalSupplierBrandEntityHi: supplierBrandEntity_his) {
				Map<String, Object> objectMap = SaafBeanUtils.object2Map(abnormalSupplierBrandEntityHi);
				mapList.add(objectMap);
			}

			baseCommonDAO_hi.saveSeqBatchJDBC("tta_rel_supplier_brand",saveRelSupplierBrandList,"REL_SUPPLIER_ID","seq_tta_rel_supplier_brand.nextval");
			baseCommonDAO_hi.updateBatchJDBC("tta_abnormal_supplier_brand",TtaAbnormalSupplierBrandEntity_HI.class,mapList);
			LOGGER.info("生成供应商品牌信息成功");

		}
	}

	private TtaRelSupplierBrandEntity_HI setObjectAttribute(TtaSupplierEntity_HI_RO supplierEntityHiRo,TtaAbnormalSupplierBrandEntity_HI brandEntityHi,int userId){
		TtaRelSupplierBrandEntity_HI entityHi = new TtaRelSupplierBrandEntity_HI();
		entityHi.setRelId(supplierEntityHiRo.getSupplierId());
		entityHi.setGroupCode(brandEntityHi.getGroupCode());
		entityHi.setGroupName(brandEntityHi.getGroupName());
		entityHi.setDeptCode(brandEntityHi.getDeptCode());
		entityHi.setDeptName(brandEntityHi.getDeptName());
		entityHi.setRelBrandCode(brandEntityHi.getBrandCode());
		entityHi.setRelBrandName(brandEntityHi.getBrandCn());
		entityHi.setRelBrandNameEn(brandEntityHi.getBrandEn());
		entityHi.setBuScorecard(brandEntityHi.getBuScorecard());
		entityHi.setKbScorecard(brandEntityHi.getKbScorecard());
		entityHi.setWinTopSupplier(brandEntityHi.getWinTopSupplier());
		entityHi.setCreationDate(new Date());
		entityHi.setCreatedBy(userId);
		entityHi.setLastUpdateDate(new Date());
		entityHi.setLastUpdatedBy(userId);
		entityHi.setLastUpdateLogin(userId);
		return entityHi;
	}

	public void setParams(TtaAbnormalSupplierBrandEntity_HI entity_hi,int userId){
		entity_hi.setIsCreate("1");
		entity_hi.setLastUpdateDate(new Date());
		entity_hi.setLastUpdatedBy(userId);
		entity_hi.setLastUpdateLogin(userId);
	}
}
