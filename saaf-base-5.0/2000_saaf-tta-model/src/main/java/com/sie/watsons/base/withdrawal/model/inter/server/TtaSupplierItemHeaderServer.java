package com.sie.watsons.base.withdrawal.model.inter.server;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnLEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandCountCRecord;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.watsons.base.withdrawal.model.dao.TtaSupplierItemHeaderDAO_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemHeaderEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemRelSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemMid;

import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemRelSupplier;
import com.sie.watsons.base.withdrawal.utils.*;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemHeaderEntity_HI;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemHeader;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import javax.transaction.SystemException;

@Component("ttaSupplierItemHeaderServer")
public class TtaSupplierItemHeaderServer extends BaseCommonServer<TtaSupplierItemHeaderEntity_HI> implements ITtaSupplierItemHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemHeaderServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaSupplierItemHeaderEntity_HI> ttaSupplierItemHeaderDAO_HI;
	
	@Autowired
	private BaseViewObject<TtaSupplierItemHeaderEntity_HI_RO> ttaSupplierItemHeaderDAO_HI_RO;
	
	@Autowired
	private BaseViewObject<TtaSupplierEntity_HI_RO> ttaSupplierEntity_HI_RO;

	@Autowired
	private ITtaSupplierItemMid ttaSupplierItemMidServer;

	@Autowired
	private ITtaSupplierItemRelSupplier ttaSupplierItemRelSupplierServer;

	@Autowired
	private BaseCommonDAO_HI<TtaSupplierItemMidEntity_HI> ttaSupplierItemMidDAO_HI;

	@Autowired
	private ITtaProposalHeader ttaProposalHeaderServer;

	@Autowired
	private BaseViewObject<TtaProposalHeaderEntity_HI_RO> ttaProposalHeaderDAO_HI_RO;

	@Autowired
	private BaseViewObject<TtaBrandplnLEntity_HI_RO> ttaBrandplnLDAO_HI_RO;

	@Autowired
	private IBaseUsers baseUsersServer;

	@Autowired
	private GenerateCodeService codeService;

	@Autowired
	private GenerateCodeServer generateCodeServer;

	@Autowired
	private JedisCluster jedisCluster;

    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Autowired
	BaseCommonDAO_HI<TtaSupplierItemHeaderEntity_HI> baseCommonDAO;

	@Autowired
	private ITtaBrandCountCRecord ttaBrandCountCRecordServer;

	//有界队列
	private static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10);
	//调用者运行策略
	private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(20, 100, 1000, TimeUnit.MILLISECONDS, queue,new ThreadPoolExecutor.CallerRunsPolicy());

	//使用排队策略 LinkedBlockingQueue
	private static ExecutorService threadPoolExecutorService = new ThreadPoolExecutor(20, 100, 0,TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	public static final Map<String,String> SPLITMAP = new HashMap<>();
	public static final Map<String,String> UP_CONDITION = new HashMap<>();
	public static final Map<Integer,String> OI_MAP = new ConcurrentHashMap<>();
	static {
		SPLITMAP.put("Group","GROUP_CODE");
		SPLITMAP.put("Dept","DEPT_CODE");
		SPLITMAP.put("Brand","BRAND_CN");
		SPLITMAP.put("Item","ITEM_NBR");

		UP_CONDITION.put("Group","tsim.group_code = tai.group_code");
		UP_CONDITION.put("Group+Dept","tsim.group_code = tai.group_code \n" +
				"         and tsim.dept_code = tai.dept_code");
		UP_CONDITION.put("Group+Dept+Brand","tsim.group_code = tai.group_code \n" +
				"         and tsim.dept_code = tai.dept_code and tsim.brand_name = tai.brand_cn");
		UP_CONDITION.put("Group+Dept+Brand+Item","tsim.group_code = tai.group_code \n" +
				"         and tsim.dept_code = tai.dept_code and tsim.brand_name = tai.brand_cn and tsim.item_code = tai.item_nbr");

		OI_MAP.put(1,"tta_oi_sales_scene_sum");
		OI_MAP.put(2,"tta_oi_po_scene_sum");
		OI_MAP.put(3,"tta_oi_po_rv_scene_sum");
		OI_MAP.put(4,"tta_oi_aboi_ng_suit_scene_sum");
		OI_MAP.put(5,"tta_oi_aboi_suit_scene_sum");
		OI_MAP.put(6,"tta_oi_ln_scene_sum");
	}

	public TtaSupplierItemHeaderServer() {
		super();
	}

	/**
	 * 保存头或者更新头信息
	 * @param queryParamJSON
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveTtaSupplierItemHeaderInfo(JSONObject queryParamJSON,int userId)  throws Exception{
		JSONObject headerInfo = queryParamJSON.getJSONObject("headerInfo");
		TtaSupplierItemHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaSupplierItemHeaderEntity_HI.class,headerInfo, ttaSupplierItemHeaderDAO_HI, userId);
		String startDate = headerInfo.getString("startDate");
		String endDate = headerInfo.getString("endDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		instance.setStartDate(sdf.parse(startDate));
		instance.setEndDate(sdf.parse(endDate));
		if (null == instance.getSupplierItemId()) {
			JSONObject isIN = new JSONObject();
			isIN.put("userId", userId);
			List<BaseUsersEntity_HI> list = this.baseUsersServer.findList(isIN);
			Assert.notNull(list,"当前登录信息已失效,请重新刷新重试");
			BaseUsersEntity_HI hi = list.get(0);
			instance.setBillCode(codeService.getSplitMergePrefix());//单据编号:自动生成
			instance.setUserGroupCode(hi.getGroupCode());
			instance.setUserGroupName(hi.getGroupName());
		}
		ttaSupplierItemHeaderDAO_HI.saveOrUpdate(instance);
		queryParamJSON.put("headerInfo",instance);//supplierItemId
		ttaSupplierItemMidServer.checkSplitConditionDetail(queryParamJSON,userId);//检查拆分明细数据
		ttaSupplierItemMidServer.saveSplitConditionDetail(queryParamJSON,userId);//保存拆分条件明细数据
		JSONObject result = new JSONObject();
		result.put("instance",instance);
		return result;
	}

	/**
	 * 查询proposal拆分与合并的数据
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination<TtaSupplierItemHeaderEntity_HI_RO> findTtaSupplierItemHeaderEntityHIPage(
			JSONObject queryParamJSON,Integer pageIndex, Integer pageRows)
			throws Exception {

		StringBuffer sb = new StringBuffer(TtaSupplierItemHeaderEntity_HI_RO.QUERY_SELECT_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		String vSql = TtaDeptFeeHeaderEntity_HI_RO.getTableDeptSql(queryParamJSON.getInteger("varUserId"),queryParamJSON.getString("varUserType")) ;
		if(!SaafToolUtils.isNullOrEmpty(vSql)){
			sb.append(" and exists (select 1 from").append(vSql).append(" where tsih.MAJOR_DEPT_CODE = dept.department_code and tsih.user_group_name = dept.group_name) ");

		}

		SaafToolUtils.parperParam(queryParamJSON,"tsih.supplier_item_id","supplierItemId",sb,paramsMap,"=",false);//单据id
		SaafToolUtils.parperParam(queryParamJSON,"tsih.bill_code","billCode",sb,paramsMap,"like",false);//单据编号
		SaafToolUtils.parperParam(queryParamJSON, "tsih.supplier_code", "supplierCode", sb, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tsih.bill_status", "billStatus", sb, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "to_char(tsih.start_date,'yyyy-mm-dd')", "startDate", sb, paramsMap, ">=");
		SaafToolUtils.parperParam(queryParamJSON, "to_char(tsih.end_date,'yyyy-mm-dd')", "endDate", sb, paramsMap, "<=");
		SaafToolUtils.changeQuerySort(queryParamJSON,sb,"tsih.creation_date desc",false);
        Pagination<TtaSupplierItemHeaderEntity_HI_RO> ttaSupplierItemHeaderDAOHiRoPagination = ttaSupplierItemHeaderDAO_HI_RO.findPagination(sb,paramsMap,pageIndex,pageRows);
        return ttaSupplierItemHeaderDAOHiRoPagination;
	}

	@Override
	public Pagination<TtaSupplierEntity_HI_RO> findTtaSupplierEntity_HI_RO(
			JSONObject jsonObject, Integer pageIndex, Integer pageRows)
			throws Exception {
		StringBuffer sql = new StringBuffer("select supplier_id supplierId,supplier_code supplierCode," +
				"supplier_name supplierName,status status,is_latent isLatent," +
				"creation_date creationDate,version_num versionNum," +
				"owner_dept ownerDept,owner_group ownerGroup," +
				"contract_output contractOutput,purchase_mode purchaseMode," +
				"proposal_brand_group proposalBrandGroup," +
				"latent_code latentCode,latent_name latentName from TTA_SUPPLIER where 1 = 1 ");
		Map<String, Object> paramsMap = new HashMap<>(); 	
		String supplierCode = jsonObject.getString("supplierCode");
		if (!"".equals(supplierCode) && supplierCode!=null){
			sql.append(" and SUPPLIER_CODE IN ('"+supplierCode+"')");
        }		
		Pagination<TtaSupplierEntity_HI_RO> supplierItemHeader = ttaSupplierEntity_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
		return supplierItemHeader;
	}

	/**
	 * 查询符合拆分条件明细数据
	 */
	@Override
	public List<Map<String, Object>> saveSplitDetailListBySplitAndDate(JSONObject queryParamJSON) throws Exception{
		String startDateStr = queryParamJSON.getString("startDate");//年月从
		String endDateStr =   queryParamJSON.getString("endDate");//年月至
		String splitCondition = queryParamJSON.getString("splitCondition");//拆分条件
		String supplierCode = queryParamJSON.getString("supplierCode");//头信息供应商编号
		Integer supplierItemId = queryParamJSON.getInteger("supplierItemId");//单据id
		Assert.notNull(splitCondition,"拆分条件未选择,请先选择拆分条件,再生产符合条件明细数据");
		String format = WDatesUtils.tranferDateFormat(startDateStr);
		String format1 = WDatesUtils.tranferDateFormat(endDateStr);
		//年份
		String purchaseYear = format.substring(0,4);
		List<Map<String, Object>> mapList = new ArrayList<>();
		//根据单据头信息的供应商查询关联供应商的数据
		Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> findList = ttaSupplierItemRelSupplierServer.findSupplierItemRelSupplierListBySupplierItemId(supplierItemId);
		List<TtaSupplierItemRelSupplierEntity_HI_RO> relSupplierData = findList.getData();

		//查询关联供应商的数据
		String relSupplierCodeSql = "select trs.rel_supplier_code from tta_rel_supplier trs \n" +
				"join tta_supplier ts on trs.rel_id = ts.supplier_id \n" +
				"where ts.supplier_code ='"+supplierCode+"'";
		List<Map<String, Object>> sqlForList = ttaSupplierItemRelSupplierServer.queryNamedSQLForList(relSupplierCodeSql, new HashMap<>());

		if (StringUtils.isNotBlank(splitCondition)) {
			String[] splitArray = splitCondition.split("\\+");
			StringBuffer sbf = new StringBuffer();
			for (String split : splitArray) {
                if(SPLITMAP.containsKey(split)){
                    String key = SPLITMAP.get(split);
                    sbf.append("tsmdt." + key + ",");
                    if ("BRAND_CN".equals(key)) {
						sbf.append("tsmdt.BRAND_EN,");
					}
                }
			}

			if (sbf.lastIndexOf(",") > -1) {
				sbf = new StringBuffer(sbf.substring(0, sbf.length() - 1));
			}
			LOGGER.info("拆分条件: {}", sbf.toString());
			//唯一标识(请求id)
			String requestId = generateCodeServer.getRequestId();
			//存放供应商集合(可能包含关联供应商)
			List<String> supplierList = new ArrayList<>();
			supplierList.add(supplierCode);
			for (TtaSupplierItemRelSupplierEntity_HI_RO relSupplierDatum : relSupplierData) {
				supplierList.add(relSupplierDatum.getRelSupplierCode());
			}
			for (Map<String, Object> stringObjectMap : sqlForList) {
				String relSupplierCode = (String)stringObjectMap.get("REL_SUPPLIER_CODE");
				supplierList.add(relSupplierCode);
			}

			//获取年月从和年月至之间的月份
			List<String> from2MonthList = WDatesUtils.getFrom2Month(format, format1);
			List<Future<List<Map<String,Object>>>> futures = new ArrayList<>();
			StringBuffer finalSbf = sbf;
			Long startTime = System.currentTimeMillis();
			LOGGER.info("=============多线程查询开始=====================");
			//开启多线程查询
			for (int i = 0;i<from2MonthList.size();i++){
				String betweenMonth = from2MonthList.get(i);
				futures.add(poolExecutor.submit(new TaskSplitConditionAndSupplierCallable(betweenMonth,supplierList,finalSbf.toString(),supplierCode)));
			}
			LOGGER.info("=============多线程查询结束=====================");
			//所有子线程查询查出来的集合
			List<Map<String,Object>> resultList = new ArrayList<>();

			for (Future<List<Map<String, Object>>> future : futures) {
				List<Map<String, Object>> list = future.get();
				if (null == list || list.isEmpty()) {
					continue;
				}
				list.forEach(item ->{
					item.put("delete_sign",requestId);
					item.put("create_date",new Date());
				});
				resultList.addAll(list);
			}
			Long endTime = System.currentTimeMillis();
			LOGGER.info("查询符合拆分条件Sale和Purchase数据,插入临时表的时间: " + (endTime - startTime)+ "ms" );
			//插入数据
			baseCommonDAO.saveBatchJDBC("tta_split_merge_data_temp",resultList);
			String querySql = getSplitConditionDataSql(requestId, "'"+StringUtils.join(supplierList, "','")+"'",sbf.toString(),supplierCode,splitCondition,purchaseYear,format,format1);
			mapList= ttaSupplierItemRelSupplierServer.queryNamedSQLForList(querySql, new HashMap<>());
			String deleteSql = "delete from tta_split_merge_data_temp tsmdt where tsmdt.delete_sign ='"+requestId+"'";
            baseCommonDAO.executeSqlUpdate(deleteSql);
		}
		return mapList;
	}

	/**
	 *
	 * @param deleteSign 删除标识
	 * @param vendorNbr 供应商编号集合
	 * @param splitConditionStr 拆分条件
	 * @param supplierCode 供应商编号
	 * @return
	 */
	public String getSplitConditionDataSql(String deleteSign,String vendorNbr,String splitConditionStr,String supplierCode,String splitCondition,String purchaseYear,String startDate,String endDate) throws Exception{
		String purchaseStr = "";
		String purchaseTitStr = "";
		String oiPoStr = "";
		String oiPoRmsStr = "";
		switch (splitCondition) {
			case ITtaSupplierItemMid.GROUP:
				purchaseStr = ITtaSupplierItemMid.PURCHASE_GROUP_STRING;
				purchaseTitStr = ITtaSupplierItemMid.PURCHASETIT_GROUP_STRING;
				oiPoStr = ITtaSupplierItemMid.OI_PO_GROUP_STR;
				oiPoRmsStr = ITtaSupplierItemMid.OI_POR_REMS_GROUP;
				break;
			case ITtaSupplierItemMid.GROUP_DEPT:
				purchaseStr = ITtaSupplierItemMid.PURCHASE_GROUP_DEPT_STRING;
				purchaseTitStr = ITtaSupplierItemMid.PURCHASETIT_GROUP_DEPT_STRING;
				oiPoStr = ITtaSupplierItemMid.OI_PO_GROUP_DEPT_STR;
				oiPoRmsStr = ITtaSupplierItemMid.OI_POR_REMS_GROUP_DEPT;
				break;
			case ITtaSupplierItemMid.GROUP_DEPT_BRAND:
				purchaseStr = ITtaSupplierItemMid.PURCHASE_GROUP__DEPT_BRAND_STRING;
				purchaseTitStr = ITtaSupplierItemMid.PURCHASETIT_GROUP_DEPT_BRAND_STRING;
				oiPoStr = ITtaSupplierItemMid.OI_PO_GROUP_DEPT_BRAND_STR;
				oiPoRmsStr = ITtaSupplierItemMid.OI_POR_REMS_GROUP_DEPT_BRAND;
				break;
			case ITtaSupplierItemMid.GROUP_DEPT_BRAND_ITEM:
				purchaseStr = ITtaSupplierItemMid.PURCHASE_GROUP__DEPT_BRAND_ITEM_STRING;
				purchaseTitStr = ITtaSupplierItemMid.PURCHASETIT_GROUP_DEPT_BRAND_ITEM_STRING;
				oiPoStr = ITtaSupplierItemMid.OI_PO_GROUP_DEPT_BRAND_ITEM_STR;
				oiPoRmsStr = ITtaSupplierItemMid.OI_POR_REMS_GROUP_DEPT_BRAND_ITEM;
				break;
		}

		String readSql = "select \n" +
				"         mid.tran_date,\n" +
				"         mid.vendor_nbr as \"supplierCode\",\n" +
				"         mid.vendor_name as \"supplierName\",\n" +
				"         mid.group_code as \"groupCode\",\n" +
				"         mid.group_desc as \"groupName\",\n" +
				"         mid.dept_code as \"deptCode\",\n" +
				"         mid.dept_desc as \"deptName\",\n" +
				"         mid.brand_code as \"brandCode\",\n" +
				"         mid.brand_cn as \"brandName\",\n" +
				"         mid.brand_en as \"brandNameEn\",\n" +
				"         mid.item_nbr as \"itemCode\",\n" +
				"         mid.item_desc_cn,\n" +
				"         mid.item_desc_en,\n" +
				"         mid.split_supplier_code as \"splitSupplierCode\",\n" +
				"         mid.split_supplier_name as \"splitSupplierName\",\n" +
				"         mid.delete_sign,\n" +
				"         mid.purch_type as \"purchType\",\n" +
				"         mid.cancel_receiving_amt,\n" +
				"         mid.total_netpurchase,\n" +
				"         mid.cost as \"cost\", \n" +
				"         decode(mid.totol_poRecord,0,0,round((mid.po_amt/mid.totol_poRecord) * mid.total_netpurchase,0)) as \"purchase\", -- 采购额(含税),\n" +
				"         mid.sales_amt as \"sales\", --含税销售总额\n" +
				"         mid.sales_exclude_amt                                                                     \n" +
				"       from \n" +
				"  (\n" +
				"  select \n" +
				"         tlt.tran_date,\n" +
				"         tlt.vendor_nbr,\n" +
				"         tlt.vendor_name,\n" +
				"         tlt.group_code,\n" +
				"         tlt.group_desc,\n" +
				"         tlt.dept_code,\n" +
				"         tlt.dept_desc,\n" +
				"         tlt.brand_code,\n" +
				"         tlt.brand_cn,\n" +
				"         tlt.brand_en,\n" +
				"         tlt.item_nbr,\n" +
				"         tlt.item_desc_cn,\n" +
				"         tlt.item_desc_en,\n" +
				"         tlt.split_supplier_code,\n" +
				"         tlt.split_supplier_name,\n" +
				"         tlt.delete_sign,\n" +
				"         tlt.purch_type,\n" +
				"         tlt.cancel_receiving_amt,\n" +
				"         tlt.total_netpurchase,\n" +
				"         tlt.cost,\n" +
				"         tlt.po_amt,\n" +
				"         sum(tlt.po_amt) over() as totol_poRecord,\n" +
				"         tlt.sales_amt,\n" +
				"         tlt.sales_exclude_amt\n" +
				"        from\n" +
				"  		( " +
				"   select         \n" +
				"         ti.tran_date,\n" +
				"         ti.vendor_nbr,\n" +
				"         ti.vendor_name,\n" +
				"         ti.group_code,\n" +
				"         ti.group_desc,\n" +
				"         ti.dept_code,\n" +
				"         ti.dept_desc,\n" +
				"         ti.brand_code,\n" +
				"         ti.brand_cn,\n" +
				"         ti.brand_en,\n" +
				"         ti.item_nbr,\n" +
				"         ti.item_desc_cn,\n" +
				"         ti.item_desc_en,\n" +
				"         ti.split_supplier_code,\n" +
				"         ti.split_supplier_name,\n" +
				"         ti.delete_sign,\n" +
				"         ti.purch_type,\n" +
				"         ti.cancel_receiving_amt,\n" +
				"         ti.total_netpurchase,\n" +
				"         round(ti.cost,0) cost, -- 采购成本(cost)\n" +
				"         round(ti.po_amt - nvl(ti.cancel_receiving_amt,0),0) as po_amt,--采购成本 \n" +
				"         --sum(round(ti.po_amt - nvl(ti.cancel_receiving_amt,0),0)) over() as totol_poRecord,--供应商所有的品牌采购成本之和\n" +
				"         round(ti.sales_amt,0) as sales_amt,--含税销售总额\n" +
				"         round(ti.sales_exclude_amt,0) as sales_exclude_amt --不含税销售总额     \n" +
				"    from (\n" +
				"         select  tsmdt.tran_date,\n" +
				"                 tsmdt.vendor_nbr,\n" +
				"                 ts.supplier_name as vendor_name,\n" +
				"                 tsmdt.group_code,\n" +
				"                 tsmdt.group_desc,\n" +
				"                 tsmdt.dept_code,\n" +
				"                 tsmdt.dept_desc,\n" +
				"                 tsmdt.brand_code,\n" +
				"                 tsmdt.brand_cn,\n" +
				"                 tsmdt.brand_en,\n" +
				"                 tsmdt.item_nbr,\n" +
				"                 tsmdt.item_desc_cn,\n" +
				"                 tsmdt.item_desc_en,\n" +
				"                 tsmdt.split_supplier_code,\n" +
				"                 tsmdt.split_supplier_name,\n" +
				"                 tsmdt.delete_sign,\n" +
				"                 tsmdt.purch_type, --采购类型\n" +
				"                 nvl(tpur.cancel_receiving_amt,0) cancel_receiving_amt, --退货总金额\n" +
				"                 nvl(rms.total_netpurchase,0) total_netpurchase,\n" +
				"                 sum(nvl(tsmdt.cost,0)) over(partition by " + splitConditionStr + " ) as cost,--采购成本\n" +
				"                 sum(case when tsmdt.purch_type = 'PURCHASE' then tsmdt.po_amt else tsmdt.cost end) over(partition by " + splitConditionStr + " ) as po_amt,--采购成本\n" +
				"                 sum(nvl(tsmdt.sales_amt,0)) over(partition by " + splitConditionStr + " ) as sales_amt,--含税销售总额\n" +
				"                 sum(nvl(tsmdt.sales_exclude_amt,0)) over(partition by " + splitConditionStr + " ) as sales_exclude_amt,--不含税销售总额\n" +
				"                 row_number() over(partition by " + splitConditionStr + " order by tsmdt.tran_date) as flag\n" +
				"            from tta_split_merge_data_temp tsmdt\n" +
				"             left join tta_supplier ts on tsmdt.vendor_nbr = ts.supplier_code\n" +
				"            left join ( --查询退货金额\n" +
				"               select * from (\n" +
				"                select \n" +
				"                     '" + supplierCode + "' as vendor_nbr,            \n" +
				"                     tit.group_code,\n" +
				"                     tit.group_desc,\n" +
				"                     tit.dept_code,\n" +
				"                     tit.dept_desc,\n" +
				"                     tit.brand_code,\n" +
				"                     tit.brand_cn,\n" +
				"                     tit.brand_en,\n" +
				"                     tsmdt.item_nbr,\n" +
				"                     tsmdt.receiving_amount,\n" +
				"                     tsmdt.purch_type,\n" +
				"                     tsmdt.po_type,\n" +
				"                     sum(tsmdt.receiving_amount) over(partition by  " + purchaseTitStr + "  ) AS cancel_receiving_amt, --退货总金额\n" +
				"                     row_number() over(partition by  " + purchaseTitStr + "  order by tsmdt.vendor_nbr) purchase_flag\n" +
				"                     from  tta_purchase_in_" + purchaseYear + " tsmdt \n" +
				"                     left join (\n" +
				"                                 select *\n" +
				"                                   from (SELECT t1.item_nbr,\n" +
				"                                                t1.group_code,\n" +
				"                                                t1.group_desc,\n" +
				"                                                t1.dept_code,\n" +
				"                                                t1.dept_desc, \n" +
				"                                                t1.brand_code,                               \n" +
				"                                                t1.brand_cn,\n" +
				"                                                t1.brand_en,\n" +
				"                                                ROW_NUMBER() OVER(PARTITION BY t1.item_nbr ORDER BY t1.last_update_date desc) row_id\n" +
				"                                           FROM tta_item t1\n" +
				"                                          order by t1.last_update_date desc) t2\n" +
				"                                  where t2.row_id = 1\n" +
				"                            ) tit\n" +
				"                      on tsmdt.item_nbr = tit.item_nbr \n" +
				"                      where tsmdt.po_type = 'RETRUN' --and tsmdt.purch_type = 'PURCHASE' \n" +
				"                      and tsmdt.vendor_nbr in (" +
				TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(supplierCode) +
				//vendorNbr + ") \n" +
				  ") \n" +
				"                      ) tpt  where tpt.purchase_flag = 1\n" +
				"                                 \n" +
				"          ) tpur \n" +
				"          on tsmdt.vendor_nbr = tpur.vendor_nbr "  +
				purchaseStr +
				"          left join (--根据供应商查询净采购额\n" +
				"             select '" + supplierCode + "' as supplierCode,\n" +
				"                    sum(nvl(tosl.netpurchase, 0)) as total_netpurchase\n" +
				"               from tta_oi_summary_line tosl\n" +
				"              where tosl.rms_code in (" +
				//vendorNbr + ")\n" +
				TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(supplierCode) +
				  ")\n" +
				"                and to_char(tosl.account_month, 'yyyyMM') >= '" + startDate + "'\n" +
				"                and to_char(tosl.account_month, 'yyyyMM') <= '" + endDate + "'\n" +
				"          ) rms on tsmdt.vendor_nbr = rms.supplierCode\n" +
				"           where 1=1 and tsmdt.delete_sign = '" + deleteSign + "' \n" +
				"              ) ti where ti.flag = 1 and nvl(ti.group_code,'-1') != '-1'\n" +
				"     ) tlt" +
				"       ) mid ";
		return readSql;
	}

	/**
	 * 生成符合条件明细
	 * @param jsonObject
	 * @param userId
	 * @return
	 */
	@Override
	public List<TtaSupplierItemMidEntity_HI> saveTtaSupplierItemSplitConditionDetail(JSONObject jsonObject, int userId) throws Exception{
		JSONObject queryParamJSON = jsonObject.getJSONObject("project");
		Integer supplierItemId = queryParamJSON.getInteger("supplierItemId");
		LOGGER.info("参数 supplierItemId :{}",new Object[]{supplierItemId});
		Assert.notNull(supplierItemId,"头信息未保存,请先保存,再生成符合拆分条件明细数据");
		String proposalCode = queryParamJSON.getString("proposalCode");
		Assert.notNull(proposalCode,"Proposal编号缺失,不能生成明细数据");
		//生成明细数据之前先删除中间表的数据
		String deleteSql = "delete from tta_supplier_item_mid tsim where tsim.supplier_item_h_id =" + supplierItemId;
		ttaSupplierItemMidDAO_HI.executeSql(deleteSql);
		TtaSupplierItemHeaderEntity_HI instance = ttaSupplierItemHeaderDAO_HI.getById(supplierItemId);
		JSONObject  queryParamObject= (JSONObject)JSONObject.toJSON(instance);
		List<Map<String, Object>> splitDetailListBySplitAndDate = this.saveSplitDetailListBySplitAndDate(queryParamObject);
		String toJson = SaafToolUtils.toJson(splitDetailListBySplitAndDate);
		LOGGER.info("拆分明细数据格式化:\n{}",toJson);
		//保存purchase数据到中间表
		List<TtaSupplierItemMidEntity_HI> list= new ArrayList<>();
		if (splitDetailListBySplitAndDate != null && splitDetailListBySplitAndDate.size() > 0) {
			list = ttaSupplierItemMidServer.savePurchaseInfoList(splitDetailListBySplitAndDate,instance,userId);
		}
		//执行成功
		jedisCluster.setex(jsonObject.getString("createKey"),3600, JSON.toJSONString(jsonObject));
		ttaBrandCountCRecordServer.updateProposalSplitRecordStatus(jsonObject,null);
		return list;
	}

	/**
	 * 提交单据
	 * @param parameters
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaSupplierItemHeaderEntity_HI saveSubmitBill(JSONObject parameters, int userId) throws Exception {
		Assert.notNull(parameters.getInteger("supplierItemId"),"单据未保存,请先保存再提交");
		LOGGER.info("参数supplierItemId : {}",parameters.getInteger("supplierItemId"));
		TtaSupplierItemHeaderEntity_HI entity_hi = ttaSupplierItemHeaderDAO_HI.getById(parameters.getInteger("supplierItemId"));
		if(entity_hi == null) {
			throw new IllegalArgumentException("单据未保存,请先保存再提交");
		} else if ("affirm".equals(entity_hi.getBillStatus())) {
			throw new IllegalArgumentException("您已确认过此单据,不需要再次提交");
		}
		Date startDate = entity_hi.getStartDate();//年月从
		Date endDate = entity_hi.getEndDate();//年月至
		Integer supplierItemId = entity_hi.getSupplierItemId();//头信息id
		boolean sameDate = WDatesUtils.isSameDate(startDate, endDate, "yyyy-MM-dd");
		//查询拆分前的供应商和拆分后的供应商集合
		Set<String> midList = ttaSupplierItemMidServer.findSupplierItemMidList(supplierItemId);
		int year1 = WDatesUtils.getYear(endDate, "yyyy-MM-dd");
		//检查拆分前的供应商和拆分后的供应商是否存在proposal单据中(状态:制作中,待审批,审批通过)
		List<TtaProposalHeaderEntity_HI_RO> proposalList = new ArrayList<>();
		if(sameDate){
			proposalList = ttaProposalHeaderServer.findProposalInfoBySupplierAndYear(year1,-1,midList);
		}else {
			int year2 = WDatesUtils.getYear(startDate, "yyyy-MM-dd");
			proposalList = ttaProposalHeaderServer.findProposalInfoBySupplierAndYear(year1,year2,midList);
		}
		//拆分前的供应商和拆分后的供应商是否已存在
		if (proposalList != null && proposalList.size()>0) {
			StringBuilder proposalStr = new StringBuilder();
			for (int i = 0; i < proposalList.size(); i++) {
					if (proposalList.size()-1 != i) {
						proposalStr.append(proposalList.get(i).getOrderNbr()).append(",");//单据号
					}else {
						proposalStr.append(proposalList.get(i).getOrderNbr());
					}
			}
			throw new IllegalArgumentException("Proposal单据号: "+ proposalStr + ";已存在Proposal拆分与合并相关的供应商,不允许提交!");
		}
		entity_hi.setBillStatus("affirm");//已确认
		entity_hi.setOperatorUserId(userId);
		entity_hi.setLastUpdateDate(new Date());
		entity_hi.setLastUpdatedBy(userId);
		entity_hi.setLastUpdateLogin(userId);
		entity_hi.setConfirmDate(new Date());
		ttaSupplierItemHeaderDAO_HI.saveOrUpdate(entity_hi);
		return entity_hi;
	}

	/**
	 * 单据作废
	 * @param parameters
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaSupplierItemHeaderEntity_HI saveSupplierItemDiscard(JSONObject parameters, int userId) throws Exception {
		Long startTime = System.currentTimeMillis();
		Assert.notNull(parameters.getInteger("supplierItemId"),"单据暂未保存");
		LOGGER.info("参数 supplierItemId :{},单据状态: {}",parameters.getInteger("supplierItemId"),parameters.getString("status"));
		TtaSupplierItemHeaderEntity_HI entity_hi = ttaSupplierItemHeaderDAO_HI.getById(parameters.getInteger("supplierItemId"));
		if ("obsolete".equals(entity_hi.getBillStatus())) {
			throw new IllegalArgumentException("当前单据已作废,不需要再次处理!");
		}
		if (!"affirm".equals(entity_hi.getBillStatus())) {
			throw new IllegalArgumentException("当前单据状态未确认,不能作废处理!");
		}

		String deleteSql = "delete from tta_split_brand_detail tsbd where tsbd.supplier_item_id =" + parameters.getInteger("supplierItemId");
		//删除拆分明细表中的数据
		ttaSupplierItemHeaderDAO_HI.executeSqlUpdate(deleteSql);

		//1.查找中间表tta_supplier_item_mid的数据
		Map<String,Object> map=new HashMap<>();
		map.put("supplierItemHId",parameters.getInteger("supplierItemId"));
		StringBuffer hql = new StringBuffer("from TtaSupplierItemMidEntity_HI h where h.supplierItemHId =:supplierItemHId");

		List<TtaSupplierItemMidEntity_HI> supplierItemMidEntity_his = ttaSupplierItemMidDAO_HI.findList(hql, map);
		//2.如果中间表没有数据,直接作废即可,不需要回滚数据
		if (supplierItemMidEntity_his == null || supplierItemMidEntity_his.size() == 0) {
			entity_hi.setBillStatus("obsolete");
			entity_hi.setLastUpdateDate(new Date());
			entity_hi.setLastUpdatedBy(userId);
			entity_hi.setLastUpdateLogin(userId);
			entity_hi.setOperatorUserId(userId);
			entity_hi.setObsoleteDate(new Date());
			ttaSupplierItemHeaderDAO_HI.saveOrUpdate(entity_hi);
		} else {//中间表有数据
			Set<String> repeatSet = new HashSet<>();
			//3.中间表有数据,并判断是否有指定的供应商,如没有,直接作废,不需要回滚数据
			boolean hasSplitSupplierFlag = false;
			for (TtaSupplierItemMidEntity_HI midEntity_hi : supplierItemMidEntity_his) {
				if (StringUtils.isNotBlank(midEntity_hi.getSplitSupplierCode())){
					hasSplitSupplierFlag = true;
					repeatSet.add(midEntity_hi.getSplitSupplierCode());
				}
			}

			//3.1 没有指定供应商,直接作废单据,不需要回滚数据
			if (!hasSplitSupplierFlag) {
				entity_hi.setBillStatus("obsolete");//已做废
				entity_hi.setLastUpdateDate(new Date());
				entity_hi.setLastUpdatedBy(userId);
				entity_hi.setLastUpdateLogin(userId);
				entity_hi.setOperatorUserId(userId);
				entity_hi.setObsoleteDate(new Date());
				ttaSupplierItemHeaderDAO_HI.saveOrUpdate(entity_hi);
				return entity_hi;
			}
				//有指定供应商  回滚中间表的数据
/*				for (int a = 0;a < supplierItemMidEntity_his.size();a++) {
					TtaSupplierItemMidEntity_HI midEntity_hi = supplierItemMidEntity_his.get(a);
					String splitSupplierCode = StringUtils.isBlank(midEntity_hi.getLastSplitSupplierCode()) ? "" : midEntity_hi.getLastSplitSupplierCode();
					String splitSupplierName = StringUtils.isBlank(midEntity_hi.getLastSplitSupplierName()) ? "" : midEntity_hi.getLastSplitSupplierName();
					midEntity_hi.setSplitSupplierCode(splitSupplierCode);//指定供应商
					midEntity_hi.setSplitSupplierName(splitSupplierName);//指定供应商名称
					midEntity_hi.setLastUpdateDate(new Date());
					midEntity_hi.setLastUpdatedBy(userId);
					midEntity_hi.setLastUpdateLogin(userId);
					midEntity_hi.setOperatorUserId(userId);
					ttaSupplierItemMidDAO_HI.saveOrUpdate(midEntity_hi);
				}*/

			//==========================Proposal拆分与合并模块:如下程序为有指定的供应商==================

			//3.2 取当前系统时间的年度年份
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			//String format = sdf.format(new Date());
			String format = sdf.format(entity_hi.getCreationDate());
			Integer proposalYear = Integer.parseInt(format) - 1;//proposal年度
			JSONObject queryParamJSON = new JSONObject();
			Map<String,Object> queryParamMap = new HashMap<>();
			StringBuffer sql = new StringBuffer(TtaProposalHeaderEntity_HI_RO.SELECT_PROPOSAL_BY_PROPOSALYEAR_VENDORNBR);
			String joinSupplier = "'"+StringUtils.join(repeatSet, "','") + "'";
			queryParamJSON.put("vendorNbr",joinSupplier);
			queryParamJSON.put("proposalYear",proposalYear);
			queryParamMap.put("vendorNbr",joinSupplier);
			//SaafToolUtils.parperParam(queryParamJSON, "tph.vendor_nbr", "vendorNbr", sql, queryParamMap, "in", false);
			SaafToolUtils.parperParam(queryParamJSON, "tph.proposal_year", "proposalYear", sql, queryParamMap, ">=", false);
			//3.3 proposalYear年度从往年年度开始查找
			List<TtaProposalHeaderEntity_HI_RO> findList = ttaProposalHeaderDAO_HI_RO.findList(sql, queryParamMap);

			//4.Proposal单据查找不到,直接作废 需要回滚数据;查找得到,再看品牌计划明细数据是否存在供应商的数据,如存在,则提示不能作废,否则需要回滚数据
			if (CollectionUtils.isNotEmpty(findList)){
				//5.查询品牌计划明细数据
				List<Integer> proposalIdList = new ArrayList<>();
				for (TtaProposalHeaderEntity_HI_RO entityHiRo : findList) {
					Integer proposalId = entityHiRo.getProposalId();
					String orderNbr = entityHiRo.getOrderNbr();

					JSONObject queryParamJSONB = new JSONObject();
					Map<String,Object> paramsMapB = new HashMap<>();
					queryParamJSONB.put("proposalId",proposalId);
					StringBuffer sb = new StringBuffer();
					sb.append(TtaBrandplnLEntity_HI_RO.SELECT_BRANDPLNL_DETAIL_DATA_BYPROPOSALID);
					SaafToolUtils.parperParam(queryParamJSONB, "tbl.proposal_id", "proposalId", sb, paramsMapB, "=");
					List<TtaBrandplnLEntity_HI_RO> list = ttaBrandplnLDAO_HI_RO.findList(sb, paramsMapB);
					if (list != null && list.size()>0) {
						proposalIdList.add(Integer.valueOf(orderNbr));
					}
				}

				//6.集合有proposalId,提示不能作废
				if (proposalIdList.size()>0) {
					String joinProposal = StringUtils.join(proposalIdList, ",");
					throw new IllegalArgumentException("当前单据中的供应商已制作Proposal单据,不能作废当前供应商拆分与合并;Proposal单据号为【"+joinProposal+"】," +
							"如需作废当前供应商拆分与合并,需要作废单据【"+joinProposal+"】即可");
				}
			}
			entity_hi.setBillStatus("obsolete");//已做废
			entity_hi.setLastUpdateDate(new Date());
			entity_hi.setLastUpdatedBy(userId);
			entity_hi.setLastUpdateLogin(userId);
			entity_hi.setOperatorUserId(userId);
			entity_hi.setObsoleteDate(new Date());
			ttaSupplierItemHeaderDAO_HI.saveOrUpdate(entity_hi);

			//////////////////////////品牌计划明细数据不存在相对应的数据,进行回滚数据操作///////////////////////////////////////////////////////////////
			//7.获取头信息id,供应商,拆分条件,日期
			Integer supplierItemId = parameters.getInteger("supplierItemId");
			String supplierCode = entity_hi.getSupplierCode();
			String splitCondition = entity_hi.getSplitCondition();//拆分条件
			Date startDate = entity_hi.getStartDate();//年月从
			Date endDate = entity_hi.getEndDate();//年月至

			//7.1 根据单据头信息的供应商查询关联供应商的数据
			Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> relSupplierList = ttaSupplierItemRelSupplierServer.findSupplierItemRelSupplierListBySupplierItemId(supplierItemId);
			List<TtaSupplierItemRelSupplierEntity_HI_RO> relSupplierData = relSupplierList.getData();
			//查询关联供应商的数据
			String relSupplierCodeSql = "select trs.rel_supplier_code from tta_rel_supplier trs \n" +
					"join tta_supplier ts on trs.rel_id = ts.supplier_id \n" +
					"where ts.supplier_code ='"+supplierCode+"'";
			List<Map<String, Object>> sqlForList = ttaSupplierItemRelSupplierServer.queryNamedSQLForList(relSupplierCodeSql, new HashMap<>());

			//7.2 存放供应商集合
			List<String> supplierList = new ArrayList<>();
			supplierList.add(supplierCode);
			if (relSupplierData != null && relSupplierData.size() > 0) {
				for (TtaSupplierItemRelSupplierEntity_HI_RO datum : relSupplierData) {
					supplierList.add(datum.getRelSupplierCode());
				}
			}
			for (Map<String, Object> stringObjectMap : sqlForList) {
				String relSupplierCode = (String)stringObjectMap.get("REL_SUPPLIER_CODE");
				supplierList.add(relSupplierCode);
			}
			String joinSupplierStr = "'" + StringUtils.join(supplierList,"','")+"'";

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String startDateFormat = simpleDateFormat.format(startDate);//开始日期
			String endDateFormat = simpleDateFormat.format(endDate);//结束日期
			//8.获取年月从和年月至之间的月份
			List<String> from2MonthList = WDatesUtils.getFrom2Month(startDateFormat, endDateFormat);
			//== 9.获取选择时间范围的年,用来更新tta_purchase_in_2019等表的数据
			List<Integer> monthBetween = WDatesUtils.getMonthBetween(startDate, endDate);
			//==

			String splitString = "";
			if (UP_CONDITION.containsKey(splitCondition.trim())) {
				splitString += UP_CONDITION.get(splitCondition.trim());
			}
			List<Future<String>> futureList = new ArrayList<>();

			//监控主线程
			CountDownLatch mainLatch = new CountDownLatch(1);
			//监控子线程
			CountDownLatch threadLatch = new CountDownLatch(from2MonthList.size() + monthBetween.size() + 6);
			//根据子线程执行结果判断是否需要回滚
			BlockingDeque<Boolean> resultList = new LinkedBlockingDeque<>(from2MonthList.size() + monthBetween.size() + 6);
			//必须要使用对象，如果使用变量会造成线程之间不可共享变量值
			RollBack rollBack = new RollBack(false);

			String finalSplitString = splitString;//拆分条件

            //回滚sale数据
			from2MonthList.forEach(monthDay ->{
				RollbackUpdateSupplierCallable rollbackUpdateSupplierCallable = new RollbackUpdateSupplierCallable(mainLatch,threadLatch,rollBack,
						resultList,supplierItemId,joinSupplierStr,monthDay,finalSplitString,supplierList,ttaSupplierItemRelSupplierServer);
				Future<String> submit = threadPoolExecutorService.submit(rollbackUpdateSupplierCallable);
				futureList.add(submit);
			});

            //回滚purchase数据
			monthBetween.forEach(yearItem ->{
				RollbackUpdatePurchaseSupplierCallable purchaseSupplierCallable = new RollbackUpdatePurchaseSupplierCallable(mainLatch,threadLatch,rollBack,
						resultList,supplierItemId,joinSupplierStr,yearItem,finalSplitString,supplierList,ttaSupplierItemRelSupplierServer);
				Future<String> stringFuture = threadPoolExecutorService.submit(purchaseSupplierCallable);
				futureList.add(stringFuture);
			});

			//回滚所有的OI场景(六种场景)
			for (int oi = 1; oi <= 6; oi++) {
				RollBackUpdateTotolOiSalesSceneCallable rollBackUpdateTotolOiSalesSceneCallable = new RollBackUpdateTotolOiSalesSceneCallable(mainLatch,threadLatch,rollBack,resultList,supplierItemId,joinSupplierStr,finalSplitString,supplierList,
						ttaSupplierItemRelSupplierServer,startDateFormat,endDateFormat,oi);
				Future<String> stringFuture = threadPoolExecutorService.submit(rollBackUpdateTotolOiSalesSceneCallable);
				futureList.add(stringFuture);
			}

			/** 存放子线程返回结果. */
			List<Boolean> backUpResult = Lists.newArrayList();
			try {
				//等待所有子线程执行完毕()
				boolean await = threadLatch.await(1800, TimeUnit.SECONDS);

				//如果超时，直接回滚
				if (!await) {
					rollBack.setRollBack(true);
				} else {
					//查看执行情况，如果有存在需要回滚的线程，则全部回滚
					for (int i = 0; i < from2MonthList.size() + monthBetween.size() + 6; i++) {
						Boolean result = resultList.take();
						backUpResult.add(result);
						LOGGER.debug("子线程返回结果result: {}", result);
						if (result) {
							/** 有线程执行异常，需要回滚子线程. */
							rollBack.setRollBack(true);
						}
					}
				}
			} catch (InterruptedException e) {
				LOGGER.error("等待所有子线程执行完毕时，出现异常");
				throw new SystemException("等待所有子线程执行完毕时，出现异常，整体回滚");
			} finally {
				mainLatch.countDown();
			}

			/** 检查子线程是否有异常，有异常整体回滚. */
			for (int i = 0; i < from2MonthList.size() + monthBetween.size() + 6; i++) {
				if (CollectionUtils.isNotEmpty(backUpResult)) {
					Boolean result = backUpResult.get(i);
					if (result) {
						LOGGER.info("回滚指定供应商失败，整体回滚1");
						throw new SystemException("回滚指定供应商失败");
					}
				} else {
					LOGGER.info("回滚指定供应商失败，整体回滚2");
					throw new SystemException("回滚指定供应商失败");
				}
			}

			//拼接结果
			try {
				for (Future<String> future : futureList) {
					String returnS = future.get();
					System.out.println("子线程返回结果:" + returnS);
					if ("fail".equals(returnS)) {
						throw new RuntimeException("回滚指定供应商失败");
					}
				}
			} catch (Exception e) {
				LOGGER.info("获取子线程操作结果出现异常，异常信息： {}",e);
				throw new SystemException("指定供应商子线程正常更新成功，主线程出现异常，回滚失败");
			}

			Long endTime = System.currentTimeMillis();
			LOGGER.info("线程执行总时间:{}",(endTime - startTime)/1000+ "s");

		}
		return entity_hi;
	}


	class TaskSplitConditionAndSupplierCallable implements Callable<List<Map<String,Object>>> {

		private String betweenMonth;//月份

		private String supplierCode;//供应商编号集合

		private String splitCondition;//拆分条件

		private String vendorNbr;//供应商编号

		public TaskSplitConditionAndSupplierCallable() {
		}

		public TaskSplitConditionAndSupplierCallable(String betweenMonth, List<String> vendorNbrList, String splitCondition,String vendorNbr) {
			this.betweenMonth = betweenMonth;
			this.supplierCode = "'"+StringUtils.join(vendorNbrList, "','")+"'";
			this.splitCondition = splitCondition;
			this.vendorNbr = vendorNbr;
		}

		@Override
		public List<Map<String, Object>> call() throws Exception {
			//汇总不同分组维度的po_amt,cost,sales_amt,sales_exclude_amt金额
			String sql = "select \n" +
					"       ti.tran_date,\n" +
					"       ti.vendor_nbr,--原供应商编码\n" +
					"       ti.group_code,\n" +
					"       ti.group_desc,\n" +
					"       ti.dept_code,\n" +
					"       ti.dept_desc,\n" +
					"       ti.brand_code,\n" +
					"       ti.brand_cn,\n" +
					"       ti.brand_en,\n" +
					"       ti.item_nbr,\n" +
					"       ti.item_desc_cn,\n" +
					"       ti.item_desc_en,\n" +
					"       ti.split_supplier_code,--拆分后的供应商编码\n" +
					"       ti.purch_type,\n" +
					"       ti.cost,\n" +
					"       ti.po_amt,\n" +
					"       ti.sales_amt,\n" +
					"       ti.sales_exclude_amt\n" +
					" from (\n" +
					"   select \n" +
					"       tssl.tran_date,\n" +
					"       " + vendorNbr + " as vendor_nbr,--原供应商编码\n" +
					"       tsmdt.group_code,\n" +
					"       tsmdt.group_desc,\n" +
					"       tsmdt.dept_code,\n" +
					"       tsmdt.dept_desc,\n" +
					"       tsmdt.brand_code,\n" +
					"       tsmdt.brand_cn,\n" +
					"       tsmdt.brand_en,\n" +
					"       tssl.item_nbr,\n" +
					"       tsmdt.item_desc_cn,\n" +
					"       tsmdt.item_desc_en,\n" +
					"       tssl.split_supplier_code,--拆分后的供应商编码\n" +
					"       tssl.purch_type,\n" +
					"       sum(tssl.cost) over(partition by " + splitCondition + " ) as cost,--每个月不含税cost\n" +
					"       sum(tssl.po_amt) over(partition by " + splitCondition + " ) as po_amt,--每个月不含税purchase(po_record)\n" +
					"       sum(tssl.gp_supplier_popt_amt) over(partition by " + splitCondition + " ) as gp_supplier_popt_amt, --每个月销售毛利额\\n\" +\n" +
					"       sum(tssl.sales_amt) over(partition by " + splitCondition + " ) as sales_amt,--每个月含税销售金额\n" +
					"       sum(tssl.sales_exclude_amt) over(partition by " + splitCondition + " ) as sales_exclude_amt,--每个月不含税销售金额\n" +
					"       row_number() OVER(partition by " + splitCondition + "  ORDER BY tssl.tran_date) flag\n" +
					"  from tta_sale_sum_" + betweenMonth + " tssl\n" +
					"  left join (\n" +
					"                 select *\n" +
					"                   from (SELECT t1.item_nbr,\n" +
					"                                t1.item_desc_cn,\n" +
					"                                t1.item_desc_en,    \n" +
					"                                t1.VENDOR_NBR,\n" +
					"                                t1.VENDOR_NAME,\n" +
					"                                t1.group_code,\n" +
					"                                t1.group_desc,\n" +
					"                                t1.dept_code,\n" +
					"                                t1.dept_desc, \n" +
					"                                 t1.brand_code,                               \n" +
					"                                t1.brand_cn,\n" +
					"                                t1.brand_en,                               \n" +
					"                                ROW_NUMBER() OVER(PARTITION BY t1.item_nbr ORDER BY t1.last_update_date desc) row_id\n" +
					"                           FROM tta_item t1\n" +
					"                          order by t1.last_update_date desc) t2\n" +
					"                  where t2.row_id = 1\n" +
					"            ) tsmdt\n" +
					"      on tssl.item_nbr = tsmdt.item_nbr \n" +
					"      where tssl.vendor_nbr in(" +
					TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorNbr) +
					//supplierCode + ")\n" +
					 ")\n" +
					"      \n" +
					"      )  ti where flag=1 ";

			List<Map<String, Object>> findList = ttaSupplierItemRelSupplierServer.queryNamedSQLForList(sql, new HashMap<>());
			return findList;
		}
	}

	/**
	 *
	 * @param mainLatch
	 * @param threadLatch
	 * @param rollBack
	 * @param resultList
	 * @param supplierItemId 单据id
	 * @param joinSupplierStr 拼接供应商
	 * @param monthDay 月份
	 * @param splitString 拆分条件
	 */
    @Override
    public String updateSplitSupplitInfo(CountDownLatch mainLatch, CountDownLatch threadLatch,
													  TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList,
													  Integer supplierItemId, String joinSupplierStr, String monthDay,
													  String splitString, String splitSupplierCode, String splitSupplierName) throws Exception{
		String msg = "success";
		Boolean result = false;

		String updateSql = "update tta_sale_sum_"+monthDay+" ttas\n" +
				"   set ttas.split_supplier_code = '"+splitSupplierCode+"',\n" +
				"       --ttas.split_supplier_name = '"+splitSupplierName+"',\n" +
				"       ttas.split_count         = ttas.split_count + 1\n" +
				" where ttas.vendor_nbr in ("+joinSupplierStr+")\n" +
				"   and ttas.item_nbr in\n" +
				"       (select distinct tai.item_nbr\n" +
				"          from tta_item tai\n" +
				"         where exists (\n" +
				"              select 1\n" +
				"                  from tta_supplier_item_mid tsim\n" +
				"                 where tsim.supplier_item_h_id = "+supplierItemId+"\n" +
				"                   "+splitString+"\n" +
				"           )) ";

		try {
			//添加数据成功,那么返回result = true;还可以返回结果
			int count = baseCommonDAO.executeSqlUpdate(updateSql);
			LOGGER.info("线程: {},表: {},更新指定供应商条数 : {}", Thread.currentThread().getName(),"tta_sale_sum_"+monthDay,count);
			if (count < 0) {
				result = true;
			}
			System.out.println(Thread.currentThread().getName() +"代码执行到这里了.....");
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
			LOGGER.info("更新的表:{}","tta_sale_sum_"+monthDay);
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

    @Override
    public String updatePurchaseSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList,
                                             Integer supplierItemId,
                                             String joinSupplierStr, Integer yearItem, String finalSplitString,
                                             String splitSupplierCode, String splitSupplierName) throws Exception{
        String msg = "success";
        Boolean result = false;

        String updateSql = "update tta_purchase_in_"+yearItem+" tpi\n" +
                "   set tpi.split_supplier_code = '"+splitSupplierCode+"',\n" +
                "       tpi.split_count         = tpi.split_count + 1\n" +
                " where tpi.vendor_nbr in ("+joinSupplierStr+")\n" +
                "   and tpi.item_nbr in\n" +
                "       (select distinct tai.item_nbr\n" +
                "          from tta_item tai\n" +
                "         where exists (\n" +
                "              select 1\n" +
                "                  from tta_supplier_item_mid tsim\n" +
                "                 where tsim.supplier_item_h_id = "+supplierItemId+"\n" +
                "                   "+finalSplitString+"\n" +
                "           )) ";


        try {
            //添加数据成功,那么返回result = true;还可以返回结果
            int count = baseCommonDAO.executeSqlUpdate(updateSql);
            LOGGER.info("线程: {},表: {},更新指定供应商条数 : {}", Thread.currentThread().getName(),"tta_purchase_in_"+yearItem,count);
            if (count < 0) {
                result = true;
            }
            System.out.println(Thread.currentThread().getName() +"线程***********执行指定供应商到采购表操作***********");
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
	 * 指定供应商功能,更新选中的供应商
	 * @param mainLatch
	 * @param threadLatch
	 * @param rollBack
	 * @param resultList
	 * @param supplierItemId 单据id
	 * @param joinSupplierStr 拼接供应商
	 * @param toMonth 月份
	 * @param splitCondition 拆分条件
	 * @param itemList item集合
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateSplitCodeBySelectData(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack,
											  BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, String toMonth,
											  String splitCondition, List<String> itemList,String finalSplitString,String joinMidId) throws Exception {

		String msg = "success";
        Boolean result = false;

        try {
     	 	//2019.12.27添加
            String updateSql = "update tta_sale_sum_"+toMonth+" ttas\n" +
					"   set ttas.split_supplier_code =\n" +
					"       (select nvl(t.split_supplier_code,t.supplier_code)\n" +
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
					"                              tm.brand_name_en,\n" +
					"                              tm.item_code\n" +
					"                         from tta_supplier_item_mid tm\n" +
					"                        where tm.mid in (" + joinMidId + ")) tsim\n" +
					"                    on (\n" +
					finalSplitString +
					"                       )) t\n" +
					"         where t.flag = 1\n" +
					"           and t.item_nbr = ttas.item_nbr\n" +
					"        \n" +
					"        ),\n" +
					"       ttas.split_count         = ttas.split_count + 1\n" +
					" where ttas.vendor_nbr in (" + joinSupplierStr + ")\n" +
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
					"                              tm.brand_name_en,\n" +
					"                              tm.item_code\n" +
					"                         from tta_supplier_item_mid tm\n" +
					"                        where tm.mid in (" + joinMidId + ")) tsim\n" +
					"                    on (\n" +
					finalSplitString +
					"                       )) t\n" +
					"         where t.flag = 1\n" +
					"           and t.item_nbr = ttas.item_nbr)";

            int count = baseCommonDAO.executeSqlUpdate(updateSql);
            LOGGER.info("线程: {},表: {},更新指定供应商条数 : {}", Thread.currentThread().getName(),"tta_sale_sum_" +toMonth ,count);
            if (count < 0) {
                result = true;
            }
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
            mainLatch.await();
            LOGGER.info("子线程 {} 再次启动", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            LOGGER.error("批量更新指定供应商子线程InterruptedException异常");
            msg = "fail";
            throw new SystemException("批量更新指定供应商线程InterruptedException异常");
        }

        if (rollBack.getRollBack()) {
            LOGGER.error("批量更新指定供应商线程回滚, 线程: {}",
                    Thread.currentThread().getName());
            LOGGER.info("子线程 {} 执行完毕，线程退出", Thread.currentThread().getName());
            msg = "fail";
            throw new SystemException("批量更新指定供应商线程回滚");
        }

        LOGGER.info("子线程 {} 执行完毕，线程退出", Thread.currentThread().getName());
        return msg;
	}

	/**
	 *  2019.10.10创建
	 * */
	@Override
	public String updateSelectPurchaseSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack,
												   BlockingDeque<Boolean> resultList, Integer supplierItemId,
												   String joinSupplierStr, Integer yearItem, String splitCondition, List<String> itemList,String finalSplitString,String joinMidId) throws Exception {
		String msg = "success";
		Boolean result = false;

		try {
            String splitSql = "update tta_purchase_in_"+yearItem+" ttas\n" +
					"   set ttas.split_supplier_code =\n" +
					"       (select nvl(t.split_supplier_code,t.supplier_code)\n" +
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
					"                              tm.brand_name_en,\n" +
					"                              tm.item_code\n" +
					"                         from tta_supplier_item_mid tm\n" +
					"                        where tm.mid in ("+joinMidId+")) tsim\n" +
					"                    on (\n" +
					finalSplitString +
					"                       )) t\n" +
					"         where t.flag = 1\n" +
					"           and t.item_nbr = ttas.item_nbr\n" +
					"        \n" +
					"        ),\n" +
					"       ttas.split_count         = ttas.split_count + 1\n" +
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
					"                              tm.brand_name_en,\n" +
					"                              tm.item_code\n" +
					"                         from tta_supplier_item_mid tm\n" +
					"                        where tm.mid in ("+joinMidId+")) tsim\n" +
					"                    on (\n" +
					finalSplitString +
					"                       )) t\n" +
					"         where t.flag = 1\n" +
					"           and t.item_nbr = ttas.item_nbr)";


            int count = baseCommonDAO.executeSqlUpdate(splitSql);
            LOGGER.info("线程: {},表: {},更新指定供应商条数 : {}", Thread.currentThread().getName(),"tta_purchase_in_"+yearItem,count);
            if (count < 0) {
                result = true;
            }
			LOGGER.info(Thread.currentThread().getName() + "线程*******************执行tta_purchase_in表操作******************");
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
			mainLatch.await();
			LOGGER.info("子线程 {} 再次启动", Thread.currentThread().getName());
		} catch (InterruptedException e) {
			LOGGER.error("批量更新指定供应商子线程InterruptedException异常");
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程InterruptedException异常");
		}

		if (rollBack.getRollBack()) {
			LOGGER.error("批量更新指定供应商线程回滚, 线程: {}",
					Thread.currentThread().getName());
			LOGGER.info("子线程 {} 执行完毕，线程退出", Thread.currentThread().getName());
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程回滚");
		}

		LOGGER.info("子线程 {} 执行完毕，线程退出", Thread.currentThread().getName());
		return msg;
	}

	/////////////////////OI拆分开始///////////////////////////////////////////////////////
	//2019.12.19
	private String getOiOneSql(String joinSupplierStr,String startDate, String endDate,Integer supplierItemId,String finalSplitString,String oiSceneTableName,String type,String joinMidId){
		String sql = "";
		String finalSplitString1 = " and " + finalSplitString;
		if ("all".equals(type)) {
			sql = "update "+oiSceneTableName+" tai\n" +
					"   set tai.split_supplier_code =(\n" +
					"        select \n" +
					"         nvl(tsim.split_supplier_code,tsim.supplier_code)\n" +
					"           from (select \n" +
					"            tm.split_supplier_code,\n" +
					"            tm.supplier_code,\n" +
					"  			 tm.group_code,\n" +
					"            tm.group_name,\n" +
					"            tm.dept_code,\n" +
					"            tm.dept_name,\n" +
					"            tm.brand_code,\n" +
					"            tm.brand_name,\n" +
					"            tm.brand_name_en,\n" +
					"            tm.item_code,\n" +
					"            tm.item_name\n" +
					"            from tta_supplier_item_mid tm where tm.mid in ("+joinMidId+")\n" +
					"            ) tsim\n" +
					"          where 1 = 1 " + finalSplitString1 +
					"     ),\n" +
					"       tai.split_count         = tai.split_count + 1\n" +
					" where tai.vendor_nbr in ("+joinSupplierStr+") and tai.account_month >= to_number('"+startDate+"') " +
					" and tai.account_month <= to_number('"+endDate+"') \n" +
					"   and exists (select 1\n" +
					"          from (select *\n" +
					"                  from tta_supplier_item_mid tm\n" +
					"                 where tm.supplier_item_h_id ="+supplierItemId+") tsim\n" +
					"         where 1 = 1 " + finalSplitString1 +
					"        )";

		} else if ("select".equals(type)) {
			sql = "update "+oiSceneTableName+" tai\n" +
					"   set tai.split_supplier_code = (\n" +
					"        select \n" +
					"         nvl(tsim.split_supplier_code,tsim.supplier_code)\n" +
					"           from (select \n" +
					"            tm.split_supplier_code,\n" +
					"            tm.supplier_code,\n" +
					"  			 tm.group_code,\n" +
					"            tm.group_name,\n" +
					"            tm.dept_code,\n" +
					"            tm.dept_name,\n" +
					"            tm.brand_code,\n" +
					"            tm.brand_name,\n" +
					"            tm.brand_name_en,\n" +
					"            tm.item_code,\n" +
					"            tm.item_name" +
					"            from tta_supplier_item_mid tm where tm.mid in ("+joinMidId+")\n" +
					"            ) tsim\n" +
					"          where 1 = 1 " + finalSplitString1 +
					"     ),\n" +
					"       tai.split_count         = tai.split_count + 1\n" +
					" where tai.vendor_nbr in("+joinSupplierStr+") and tai.account_month >= to_number('"+startDate+"') " +
					" and tai.account_month <= to_number('"+endDate+"') \n" +
					"   and exists (select 1\n" +
					"          from (select *\n" +
					"                  from tta_supplier_item_mid tm\n" +
					"                 where tm.mid in("+joinMidId+")  ) tsim\n" +
					"         where 1 = 1 " + finalSplitString1 +
					"        )";
		}
		return sql;
	}

	private String getUpdateSql(Integer oiType,String joinSupplierStr,String startDate, String endDate,Integer supplierItemId,String finalSplitString,String type,String joinMidId) {
		return getOiOneSql(joinSupplierStr,startDate,endDate,supplierItemId,finalSplitString,OI_MAP.get(oiType),type,joinMidId);
		//1.tta_oi_sales_scene_sum   2.tta_oi_po_scene_sum   3.tta_oi_po_rv_scene_sum  4.tta_oi_aboi_ng_suit_scene_sum   5.tta_oi_aboi_suit_scene_sum  6.tta_oi_ln_scene_sum
	}

	/**
	 * 所有OI拆分场景,指定供应商
	 * @date 2019.12.19
	 */
	@Override
	public String updateOiSceneSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, String finalSplitString, String splitSupplierCode, String startDate, String endDate, Integer oiType) throws Exception {
		LOGGER.info("=====================================进行拆分场景" + oiType + "=============================================");
		String msg = "success";
		Boolean result = false;
		String talbeName = OI_MAP.get(oiType);
		String  updateSql = getUpdateSql(oiType,joinSupplierStr,startDate,endDate,supplierItemId,finalSplitString,"all","");

		try {
			//添加数据成功,那么返回result = true;还可以返回结果
			int count = baseCommonDAO.executeSqlUpdate(updateSql);
			LOGGER.info("线程: {},表:{},更新指定供应商条数 : {}", Thread.currentThread().getName(),talbeName,count);
			if (count < 0) {
				result = true;
			}
			//Exception 和 Error 都需要抓
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			LOGGER.error("线程: {},更新指定供应商(操作的表:{})出现异常： {} ", Thread.currentThread().getName(),talbeName, throwable);
			result = true;
			msg = "fail";
		}

		resultList.add(result);
		threadLatch.countDown();
		LOGGER.info("子线程 {} 执行过程已经结束，等待主线程通知是否需要回滚,(操作的表:{})", Thread.currentThread().getName(),talbeName);

		try {

			//等待主线程的判断逻辑执行完，执行下面的是否回滚逻辑
			mainLatch.await();
			LOGGER.info("等待主线程执行,然后子线程 {} 再次启动,(操作的表:{})", Thread.currentThread().getName(),talbeName);
		} catch (InterruptedException e) {
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程InterruptedException异常");
		}

		//所有子线程超时,需要回滚
		if (rollBack.getRollBack()) {
			LOGGER.error("批量更新指定供应商线程回滚,(操作的表:{}), 线程: {}",talbeName,
					Thread.currentThread().getName());
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程回滚");
		}
		return msg;
	}

	@Override
	public String updateSelectTotalOiSaleSceneSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, String joinSupplierStr, String splitString, String joinMidId, String startDate, String endDate, Integer oiType) throws Exception{
		String msg = "success";
		Boolean result = false;
		String talbeName = OI_MAP.get(oiType);
		String  updateSql = getUpdateSql(oiType,joinSupplierStr,startDate,endDate,-1,splitString,"select",joinMidId);

		try {
			//添加数据成功,那么返回result = true;还可以返回结果
			int count = baseCommonDAO.executeSqlUpdate(updateSql);
			LOGGER.info("线程: {},表:{},更新指定供应商条数 : {}", Thread.currentThread().getName(),talbeName,count);
			if (count < 0) {
				result = true;
			}
			//System.out.println(Thread.currentThread().getName() +"线程***********执行指定供应商到tta_oi_sales_scene_sum(OI账单场景一)操作***********");
			//Exception 和 Error 都需要抓
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			LOGGER.error("线程: {},更新指定供应商(操作的表:{})出现异常： {} ", Thread.currentThread().getName(),talbeName, throwable);
			result = true;
			msg = "fail";
		}

		resultList.add(result);
		threadLatch.countDown();
		LOGGER.info("子线程 {} 执行过程已经结束，等待主线程通知是否需要回滚,(操作的表:{})", Thread.currentThread().getName(),talbeName);

		try {

			//等待主线程的判断逻辑执行完，执行下面的是否回滚逻辑
			mainLatch.await();
			LOGGER.info("等待主线程执行,然后子线程 {} 再次启动,(操作的表:{})", Thread.currentThread().getName(),talbeName);
		} catch (InterruptedException e) {
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程InterruptedException异常");
		}

		//所有子线程超时,需要回滚
		if (rollBack.getRollBack()) {
			LOGGER.error("批量更新指定供应商线程回滚,(操作的表:{}), 线程: {}",
					talbeName,Thread.currentThread().getName());
			msg = "fail";
			throw new SystemException("批量更新指定供应商线程回滚");
		}

		return msg;
	}

	//////////////////////OI拆分结束/////////////////////////////////////////////////////

	@Override
	public void callCommon(JSONObject jsonObject,Integer userId) throws Exception {
		JSONObject supplierItemHeaderInfo = jsonObject.getJSONObject("supplierItemHeaderInfo");
		//指定供应商行动生效操作
        ttaSupplierItemMidServer.saveOrUpdate(jsonObject,userId);
        //保存Proposal拆分与合并头信息
        saveSubmitBill(supplierItemHeaderInfo,userId);
	}

    @Override
    public JSONObject appointVendorNbrStatus(String createKey) {
        String str= jedisCluster.get(createKey);
        if (StringUtils.isBlank(str))
            return null;
        return JSONObject.parseObject(str);
    }

    @Override
	public List<Object> insertNewCompanyUsers(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, List<Object> taskList) throws Exception {
		return null;
	}
}
