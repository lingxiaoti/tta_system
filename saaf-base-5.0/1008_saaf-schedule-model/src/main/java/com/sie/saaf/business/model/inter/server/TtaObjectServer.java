package com.sie.saaf.business.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.dao.readonly.TtaElectEntity_RO;
import com.sie.saaf.business.model.dao.readonly.TtaExclusiveHeaderEntity_RO;
import com.sie.saaf.business.model.entities.readonly.TtaObjectEntity_HI_RO;
import com.sie.saaf.business.model.entities.readonly.TtaObjectSalePurchaseEntity_HI_RO;
import com.sie.saaf.business.model.inter.ITtaObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.BestSignClient;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.EmailUtil;
import com.sie.saaf.schedule.utils.ResourceUtils;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import com.sie.saaf.schedule.utils.StringUtil;
import com.yhg.base.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sun.rmi.runtime.Log;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Component("ttaObjectServer")
public class TtaObjectServer extends BaseCommonServer<Object> implements ITtaObject{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaObjectServer.class);

	@Autowired
	private IFastdfs fastdfsServer;

	@Autowired
	private BaseCommonDAO_HI<Object> ttaObjectDao;

	public TtaObjectServer() {
		super();
	}
	

	@Override
	public void saveJdbcBatchObject(final String tableName, List<Map<String, Object>> fieldParamsList) {
		ttaObjectDao.saveBatchJDBC(tableName, fieldParamsList);
	}


	/**
	 * 功能描述： 功能描述： 每月调度执行一次,按月计算并动态创建销售结果数据
	 * @date 2019/8/2
	 * @param
	 * @return
	 * String year = DateUtil.getCurDateStr("yyyy");
	 */
	@Override
	public void updateTtaPurchase(String year) {
		//更新供供应商字段信息
		ttaObjectDao.executeSqlUpdate(TtaObjectEntity_HI_RO.getUpdateTtaPurchaseVendor(year));
		/*//更新采购group、dept、brand信息
		ttaObjectDao.executeSqlUpdate(TtaObjectEntity_HI_RO.getUpdateTtaPurchaseIn(year));
		*/
	}


	@Override
	public void callProUpdateTtaItem() {
		ttaObjectDao.callProName("pro_update_tta_item");
	}


	@Override
	public void callProUpdateTtaSupplier() {
		ttaObjectDao.callProName("pro_update_tta_supplier");
	}


	public void saveSalePurchaseJob(String currentYear, String currentYearMonth, String lastYear, String lastYearMonth) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("#################汇总计算调度开始执行参数信息currentYear:{}, currentYearMonth:{}, lastYear:{}, lastYearMonth:{},开始执行时间:{}", currentYear, currentYearMonth, lastYear, lastYearMonth, new Date().toLocaleString());

        String truncateSql = TtaObjectSalePurchaseEntity_HI_RO.getTruncateSql();
        LOGGER.info("正在执行step 1:" + truncateSql);
		ttaObjectDao.executeSqlUpdate(truncateSql);

		String ttaPurchaseDetailTempTable = TtaObjectSalePurchaseEntity_HI_RO.getTtaPurchaseDetailTempTable(lastYear, currentYear, currentYearMonth);
        LOGGER.info("正在执行step 2:" + ttaPurchaseDetailTempTable);
		ttaObjectDao.executeSqlUpdate(ttaPurchaseDetailTempTable);

		String truncateSumTemp = TtaObjectSalePurchaseEntity_HI_RO.getTruncateSumTemp();
        LOGGER.info("正在执行step 3:" + truncateSumTemp);
		ttaObjectDao.executeSqlUpdate(truncateSumTemp);

		String ttaPurchaseDcItemYearSumTempSql = TtaObjectSalePurchaseEntity_HI_RO.getTtaPurchaseDcItemYearSumTempSql(currentYearMonth);
		LOGGER.info("正在执行step 4:" + ttaPurchaseDcItemYearSumTempSql);
		ttaObjectDao.executeSqlUpdate(ttaPurchaseDcItemYearSumTempSql);

		String ttaPurchaseDcItemYearSumSql = TtaObjectSalePurchaseEntity_HI_RO.getTtaPurchaseDcItemYearSumSql(currentYearMonth);
		LOGGER.info("正在执行step 5:" + ttaPurchaseDcItemYearSumSql);
		ttaObjectDao.executeSqlUpdate(ttaPurchaseDcItemYearSumSql);

		String ttaSalesDcSumSql = TtaObjectSalePurchaseEntity_HI_RO.getTtaSalesDcSumSql(currentYearMonth, currentYear);
		LOGGER.info("正在执行step 6:" + ttaSalesDcSumSql);
		ttaObjectDao.executeSqlUpdate(ttaSalesDcSumSql);

		String truncateTtaSalesDcYtdSumTempSql = TtaObjectSalePurchaseEntity_HI_RO.getTruncateTtaSalesDcYtdSumTempSql();
		LOGGER.info("正在执行step 7:" + truncateTtaSalesDcYtdSumTempSql);
		ttaObjectDao.executeSqlUpdate(truncateTtaSalesDcYtdSumTempSql);

		String ttaSalesDcYtdSumTempSql = TtaObjectSalePurchaseEntity_HI_RO.getTtaSalesDcYtdSumTempSql(currentYearMonth, currentYear);
		LOGGER.info("正在执行step 8:" + ttaSalesDcYtdSumTempSql);
		ttaObjectDao.executeSqlUpdate(ttaSalesDcYtdSumTempSql);

		//创建表同时创建索引
		final String tableName = "tta_sale_sum_" + currentYearMonth;
		this.saveCreateTable(tableName, "tta_sale_sum");//不存在则创建表
		this.saveCreateSaleIdx(tableName, "split_supplier_code");//创建索引

		String ttaSaleYtdSumSqlStep1Sql = TtaObjectSalePurchaseEntity_HI_RO.getTtaSaleYtdSumSqlStep1Sql(currentYearMonth, currentYear, lastYear);
		LOGGER.info("正在执行step 9:" + ttaSaleYtdSumSqlStep1Sql);
		ttaObjectDao.executeSqlUpdate(ttaSaleYtdSumSqlStep1Sql);

		// start
		LOGGER.info("正在执行step 10: drop table tta_item_vendor_temp \n");
		this.saveDropOrTruncateTable("tta_item_vendor_temp", 0);

		String createVenderTemp = TtaObjectSalePurchaseEntity_HI_RO.getCreateVenderTemp(currentYearMonth);
		LOGGER.info("正在执行step 11:" + createVenderTemp);
		ttaObjectDao.executeSqlUpdate(createVenderTemp);

		String updateUniqueVender = TtaObjectSalePurchaseEntity_HI_RO.getUpdateUniqueVender(currentYearMonth);
		LOGGER.info("正在执行step 12:" + updateUniqueVender);
		ttaObjectDao.executeSqlUpdate(updateUniqueVender);
		// end

		String yearMonthDiff = TtaObjectSalePurchaseEntity_HI_RO.getYearMonthDiff(currentYearMonth, lastYearMonth);
		LOGGER.info("正在执行step 13:" + yearMonthDiff);
		ttaObjectDao.executeSqlUpdate(yearMonthDiff);

		String cvwConsignmentTtaSaleSum = TtaObjectSalePurchaseEntity_HI_RO.getCvwConsignmentTtaSaleSum(currentYearMonth);
		LOGGER.info("正在执行step 14" + cvwConsignmentTtaSaleSum);
		ttaObjectDao.executeSqlUpdate(cvwConsignmentTtaSaleSum);

		String insertTtaSalesInSum = TtaObjectSalePurchaseEntity_HI_RO.getInsertTtaSalesInSum(currentYearMonth);
		LOGGER.info("OI费用管理模块sql:正在执行step 15" + insertTtaSalesInSum);
		ttaObjectDao.executeSqlUpdate(insertTtaSalesInSum);

		String ttaSaleVenderItemSum = TtaObjectSalePurchaseEntity_HI_RO.getTtaSaleVenderItemSum(currentYearMonth);
		LOGGER.info("OI费用管理模块sql:正在执行step 16" + ttaSaleVenderItemSum);
		ttaObjectDao.executeSqlUpdate(ttaSaleVenderItemSum);

		long endTime = System.currentTimeMillis();
        float minutes = (endTime - startTime) / (1000 * 60F);
        LOGGER.info("#################汇总计算调度结束执行参数信息currentYear:{}, currentYearMonth:{}, lastYear:{}, lastYearMonth:{}， 执行耗时:{}分钟！",currentYear, currentYearMonth, lastYear, lastYearMonth, minutes);
	}

/*	public static void main(String[] args) {
		String currentYearMonth = "201912", currentYear = "2019", lastYear="2018", lastYearMonth="201911";
		String ttaSaleYtdSumSqlStep1Sql = TtaObjectSalePurchaseEntity_HI_RO.getTtaSaleYtdSumSqlStep1Sql(currentYearMonth, currentYear, lastYear);
		LOGGER.info("正在执行step 9:\n" + ttaSaleYtdSumSqlStep1Sql);

		String cvwConsignmentTtaSaleSum = TtaObjectSalePurchaseEntity_HI_RO.getCvwConsignmentTtaSaleSum(currentYearMonth);
		LOGGER.info("正在执行step 11:\n" + cvwConsignmentTtaSaleSum);
	}*/

	@Override
	public List<Map<String, Object>> queryNamedSQLForList(String sql, Map<String, Object> queryMap) {
		return ttaObjectDao.queryNamedSQLForList(sql, queryMap);
	}

	@Override
	public void saveBatchTtaSalesByDay(BufferedInputStream in, String yearMonth) {
		//清除临时表数据
		this.saveDropOrTruncateTable("tta_sales_in_temp", 1);
		//执行插入操作
		this.saveBatchDataList( "tta_sales_in_temp",  in,  "\\|",  10000);

		//发送邮件start
		long cnt = 0;
		JSONObject instance = new JSONObject();
		JSONObject sourceCfg = new JSONObject();
		JSONObject result = null;
		try {
			List<Map<String, Object>> listMap = ttaObjectDao.queryNamedSQLForList(TtaObjectEntity_HI_RO.getSaleNoticeUser("EMAIL_SALES"), new HashMap<String, Object>());
			String receiveCode = "";
			if (listMap != null && !listMap.isEmpty()) {
				receiveCode = listMap.get(0).get("MEANING") + "";
			}
			if (StringUtil.isEmpty(receiveCode) || "null".equalsIgnoreCase(receiveCode)) {
				receiveCode = ResourceUtils.getEmialReceiveCode();
			}
			instance.put("msgSubject", "TTA调度系统邮件通知");
			instance.put("receiveCode",  receiveCode);//收件人
			String paraConfig = ResourceUtils.getEmailParaConfig(); //"{\"ServerHost\":\"Wtccn-gz-mailgate.aswgcn.asiapacific.aswgroup.net\",\"sendFrom\":\"WTCCN.service@watsons-china.com.cn\",\"sendName\":\"WTCCN.service\"}";
			sourceCfg.put("paramCfg",paraConfig);
			sourceCfg.put("sourceUser",ResourceUtils.getEmailUser()); //WTCCN.service@watsons-china.com.cn
			sourceCfg.put("sourcePwd","");
			cnt = findSaleByDayCheck();
		} catch (Exception e) {
			LOGGER.error("邮件组装参数异常：{}", e);
		}
		if (0 != cnt && ResourceUtils.getIsSendMsg()) {
			instance.put("msgContent", "RMS 销售数据今日执行" + ("ITF_BO2TTA_SALES_" + yearMonth + ".txt") + "文件同步有差异，有" + cnt + "个店铺差异数，请在当天及时排查问题！");
			result = EmailUtil.sendMail(instance, sourceCfg);
			LOGGER.info("RMS 销售数据邮件发送情况:{}, 邮件发送内容:{}" , result, instance);
		}
		LOGGER.info("#################################RMS销售数据继续调度执行！################################");
		//发送邮件end

		//关联交易表，查询到需要操作的交易日历表
		List<Map<String, Object>> yearMonthList = ttaObjectDao.queryNamedSQLForList(TtaObjectEntity_HI_RO.getOperatorYearMonth(), new HashMap<String, Object>());
		Assert.notEmpty(yearMonthList, "saveBatchTtaSalesByDay yearMonth：" + yearMonth + " 为空！");
		for (Map<String, Object> objectMap : yearMonthList) {
			String tradeYearMonth = objectMap.get("TRADE_YEAR_MONTH") + "";
			//1.判断表是否存在，不存在则创建表
			this.saveCreateTable("TTA_SALES_IN_" + tradeYearMonth, "TTA_SALES_IN");
			//1.1 创建索引
			this.saveCreateSaleIdx("TTA_SALES_IN_" + tradeYearMonth, "store", "item");
			this.saveCreateSaleIdx("TTA_SALES_IN_" + tradeYearMonth, "tran_date");
			//2.将需要操作的表存放于临时表， 并将原表数据删除。
			this.saveDropOrTruncateTable("TTA_SALES_IN_MID", 0);//drop 表
			//3.执行临时存放操作
			ttaObjectDao.executeSqlUpdate("create table TTA_SALES_IN_MID as select * from TTA_SALES_IN_" + tradeYearMonth);
			//4.turncate TTA_SALES_IN_yyyymm数据
			this.saveDropOrTruncateTable("TTA_SALES_IN_" + tradeYearMonth, 1);
			ttaObjectDao.executeSqlUpdate(TtaObjectEntity_HI_RO.getTtaSaleInYearMonth(tradeYearMonth));
		}
		LOGGER.info("#########################################.saveBatchTtaSalesByDay 调度执行完成！ ######################################################");
	}

	/**
	 * 功能描述：每天同步purchase数据
	 */
	@Override
	public void saveBatchTtaPurchaseByDay(BufferedInputStream in) {
		//truncate临时表
		this.saveDropOrTruncateTable("tta_purchase_in_temp", 1);
		//执行插入操作
		this.saveBatchDataList( "tta_purchase_in_temp",  in,  "\\|",  10000);
		this.updateTtaPurchase("temp");
		//关联交易表，查询到需要操作的交易日历表
		List<Map<String, Object>> yearMonthList = ttaObjectDao.queryNamedSQLForList(TtaObjectEntity_HI_RO.getOperatorPuracheYearMonth(), new HashMap<String, Object>());
		Assert.notEmpty(yearMonthList, "saveBatchTtaPurchaseByDay yearMonthList 为空！");
		List<Map<String, Object>> filedList = ttaObjectDao.queryNamedSQLForList("select t.COLUMN_NAME from user_tab_columns t where table_name=upper('tta_purchase_in')", new HashMap<>());
		List<String> fileds = new ArrayList<>();
		for (Map<String, Object> filedMap : filedList) {
			fileds.add(filedMap.get("COLUMN_NAME") + "");
		}
		String fliedsSql = StringUtil.getFlieds(fileds);
		for (Map<String, Object> yearMonth : yearMonthList) {
			String receiveDate = yearMonth.get("RECEIVE_DATE") + "";
			String tradeYear = yearMonth.get("TRADE_YEAR") + "";
			String inserSql = "insert into tta_purchase_in_" + tradeYear + " (" + fliedsSql + " ) select " + fliedsSql + " from tta_purchase_in_temp t where t.receive_date=" + "'" + receiveDate + "'";
			LOGGER.info("需要执行的sql信息:{}" + inserSql);
			final String purchaseTableName = "tta_purchase_in_" + tradeYear;
			this.saveCreateTable(purchaseTableName, "tta_purchase_in");
			this.saveCreateSaleIdx(purchaseTableName, "SPLIT_SUPPLIER_CODE");
			this.saveCreateSaleIdx(purchaseTableName, "VENDOR_NBR");
			this.saveCreateSaleIdx(purchaseTableName, "RECEIVE_DATE");
			this.saveCreateSaleIdx(purchaseTableName, "ITEM_NBR");
			ttaObjectDao.executeSqlUpdate(inserSql);
		}
	}
	@SuppressWarnings("all")
	@Override
	public void saveBatchDataList(String table, BufferedInputStream bis, String separator, int rownumber) {
		int count = 0;
		BufferedReader reader = null;
		try {
			ITtaObject  ttaObjectServer = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
			reader = new BufferedReader(new InputStreamReader(bis, "UTF-8"), 50 * 1024 * 1024);
			int idx = -1;
			String line = null;
			List<Map<String, Object>> dataList = new ArrayList<>();
			List<String> headList = null; //头部参数信息
			while (reader.ready()) {
				line = reader.readLine();
				if (StringUtils.isBlank(line)) {
					continue;
				}
				idx ++;
				if (idx == 0) {
					String[] keyArray = line.split(separator);
					headList = Arrays.asList(keyArray);
					continue;
				}
				if ("|".equalsIgnoreCase(String.valueOf(line.charAt(line.length() - 1)))) {
					line += null;
				}
				String[] dataArray = line.split(separator);
				Map<String, Object> params = new HashMap<String, Object>();
				for (int index = 0; index < headList.size(); index ++) {
					if (StringUtils.isBlank(dataArray[index]) || "null".equalsIgnoreCase(dataArray[index])) {
						dataArray[index] = null;
					} else {
						String value = (dataArray[index] + "");
						if (value.indexOf(".") == 0) {
							value = "0" + value; //如果是格式,需变换( .12 --> 0.12)
						}
						dataArray[index] = value;
					}
					params.put(headList.get(index), dataArray[index]);
				}
				SaafToolUtils.setDefaultParams(params);
				dataList.add(params);
				if (dataList.size() % rownumber == 0) {
					count ++;
					ttaObjectServer.saveJdbcBatchObject(table, dataList);
					LOGGER.info("当前操作的表:{}，batchSaveDataList 批量保存数:{}", table, rownumber * count);
					dataList = new  ArrayList<>();
				}
			}
			LOGGER.info("当前操作的表:{}，batchSaveDataList 批量保存数:{}", table, rownumber);
			ttaObjectDao.saveBatchJDBC(table, dataList);
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".文件操作数据异常：{}", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					LOGGER.error(this.getClass() + ".文件流关闭数据异常：{}", e);
				}
			}
		}

	}

	@Override
	public void saveCreateTable(String tableName, String sourceTableName) {
		List<Map<String, Object>> list = ttaObjectDao.queryNamedSQLForList("select * from user_tables where table_name =upper('" + tableName + "')", new HashMap<String, Object>());
		if (list == null || list.isEmpty()) {
			ttaObjectDao.executeSqlUpdate("create table " + tableName + " as select *  from " + sourceTableName);
		}
	}

	/**
	 * 功能描述： flag 1:truncate 0:drop
	 */
	public void saveDropOrTruncateTable(String tableName, int flag) {
		List<Map<String, Object>> list = ttaObjectDao.queryNamedSQLForList("select * from user_tables where table_name =upper('" + tableName + "')", new HashMap<String, Object>());
		if (list == null || list.isEmpty()) {
			return;
		}
		if (0 == flag) {
			ttaObjectDao.executeSqlUpdate("drop table " + tableName);
		} else {
			ttaObjectDao.executeSqlUpdate("truncate table " + tableName);
		}
	}

	@Override
	public  void saveCreateSaleIdx(String tableName, String... cols) {
		StringBuffer buffer = new StringBuffer();
		if (cols != null && cols.length > 0) {
			String idxName = "idx_" + tableName + "_" + "".join("_", cols);
			//查询是否有该索引
			List<Map<String, Object>> list = ttaObjectDao.queryNamedSQLForList("select count(1) as CNT from user_ind_columns where index_name=upper('" + idxName + "')", new HashMap<String, Object>());
			if (Integer.parseInt(list.get(0).get("CNT") + "") == 0){
				buffer.append("create index ").append(idxName).append(" on ").append(tableName)
						.append(" (").append("".join(",", cols)).append(")");
				LOGGER.info("创建索引sql:{}", buffer.toString());
				ttaObjectDao.executeSqlUpdate(buffer.toString());
			}
		}
	}

	@Override
	public long findSaleByDayCheck() {
		String maxTradeYearMonth = TtaObjectEntity_HI_RO.getMaxTradeYearMonth();
		List<Map<String, Object>> listMap = ttaObjectDao.queryNamedSQLForList(maxTradeYearMonth, new HashMap<String, Object>());
		String tradeYearMonth = listMap.get(0).get("TRADE_YEAR_MONTH") + "";
		if (StringUtil.isEmpty(tradeYearMonth) || "null".equalsIgnoreCase(tradeYearMonth)) {
			return -1;
		}
		String salesDiff = TtaObjectEntity_HI_RO.getSalesDiff();
		List<Map<String, Object>> diffMapList = ttaObjectDao.queryNamedSQLForList(salesDiff, new HashMap<String, Object>());
		if (diffMapList == null || diffMapList.isEmpty()) {
			return 0;
		}
		LOGGER.info(".findSaleByDayCheck 差异信息Json数据如下\n：" + SaafToolUtils.toJson(diffMapList) + "\n\n");
		return diffMapList.size();
	}

	@Override
	public void saveElectStauts(TtaElectEntity_RO entity) {
		//2.查询合同头表状态
		Map<String, String> detailMap = BestSignClient.getBestSignClient().getDetailByCompanyName(entity.getOrderNo(), entity.getEnterpriseName());
		if (!detailMap.isEmpty()) {
			//1.更新头表状态信息
			String sql = "update tta_elec_con_header t set t.stamp_status = '" + detailMap.get("contractStatus") + "', t.last_update_date=sysdate where t.elec_con_header_id =" + entity.getElecConHeaderId();
			ttaObjectDao.executeSqlUpdate(sql);
			StringBuffer partSql = new StringBuffer();
			if (detailMap.containsKey(entity.getPartyA() + "_finishTime") && detailMap.get(entity.getPartyA() + "_finishTime") != null) {
				String finishTime = detailMap.get(entity.getPartyA() + "_finishTime");
				Date date = new Date(Long.parseLong(finishTime));
				partSql.append("t.party_a_sign_time=to_date('" + SaafDateUtils.convertDateToString(date) + "','yyyy-MM-dd HH24:mi:ss'),");
			}
			if (detailMap.containsKey(entity.getPartyB() + "_finishTime") && detailMap.get(entity.getPartyB() + "_finishTime") != null) {
				String finishTime = detailMap.get(entity.getPartyB() + "_finishTime");
				Date date = new Date(Long.parseLong(finishTime));
				partSql.append(" t.party_b_sign_time = to_date('" + SaafDateUtils.convertDateToString(date) + "','yyyy-MM-dd HH24:mi:ss'),");
			}
			if (detailMap.containsKey(entity.getPartyC() + "_finishTime") && detailMap.get(entity.getPartyC() + "_finishTime") != null) {
				String finishTime = detailMap.get(entity.getPartyC() + "_finishTime");
				Date date = new Date(Long.parseLong(finishTime));
				partSql.append(" t.party_c_sign_time=to_date('" + SaafDateUtils.convertDateToString(date) + "','yyyy-MM-dd HH24:mi:ss'),");
			}
			//2.更新行表信息
			StringBuffer sb = new StringBuffer("update tta_elec_sign_result_line t \n" +
					"set\n" + partSql.toString() +
					(detailMap.get(entity.getPartyA()) != null ? " t.party_a_stauts = '" + detailMap.get(entity.getPartyA()) + "',\n" : "") +
					(detailMap.get(entity.getPartyB()) != null ? " t.party_b_stauts = '" + detailMap.get(entity.getPartyB()) + "',\n" : "") +
					(detailMap.get(entity.getPartyC()) != null ? " t.party_c_stauts = '" + detailMap.get(entity.getPartyC()) + "',\n" : "") +
					" t.last_update_date=sysdate where t.elec_con_attr_line_id =" + entity.getElecConAttrLineId());
			ttaObjectDao.executeSqlUpdate(sb.toString());
			//3.上传电子凭证
			String contractStatus = detailMap.get("contractStatus");
			if ("COMPLETE".equalsIgnoreCase(contractStatus)) {
				ByteArrayInputStream inputStream = null;
				try {
					byte[] bytes = BestSignClient.getBestSignClient().queryContractAttr(entity.getOrderNo());
					inputStream = new ByteArrayInputStream(bytes);
					ResultFileEntity fileEntity = fastdfsServer.fileUpload(inputStream, entity.getHeaderOrderNo() + ".pdf");
					if (fileEntity != null && fileEntity.getFileId() != null) {
						ttaObjectDao.executeSqlUpdate("update tta_elec_sign_result_line t set file_id = " + fileEntity.getFileId() + ",last_update_date = sysdate where t.elec_con_attr_line_id =" + entity.getElecConAttrLineId());
					}
				} catch (IOException e) {
					LOGGER.error("上传凭证失败：{}", e);
				} finally {
					try{
						if (inputStream != null) {
							inputStream.close();
						}
					} catch (Exception e) {
						LOGGER.error("关闭流异常：{}", e);
					}
				}
			}
			//4.合同签署完成,更新独家协议(标准,非标),补充协议(标准,非标准)BIC登记,财务登记,法务登记时间
			if ("COMPLETE".equalsIgnoreCase(contractStatus)){
				String bussinessSql = TtaElectEntity_RO.getBussinessIdByElectId(entity.getElecConHeaderId());
				List<Map<String, Object>> mapList = ttaObjectDao.queryNamedSQLForList(bussinessSql, new HashMap<>());
				String dateToString = SaafDateUtils.convertDateToString(new Date(), "yyyy-MM-dd");
				ttaObjectDao.executeSqlUpdate("update tta_elec_con_header tech\n" +
						"       set\n" +
						"       tech.bic_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
						"       tech.finance_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
						"       tech.lega_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
						"       tech.last_update_date = sysdate\n" +
						"       where tech.elec_con_header_id =" + entity.getElecConHeaderId());

				if (mapList != null && !mapList.isEmpty()) {
					for (Map<String, Object> map : mapList) {
						String fileType = map.get("FILE_TYPE") + "";
						String orderNo = map.get("ORDER_NO") + "";
						String orderVersion = map.get("ORDER_VERSION") + "";
						//2.独家协议-标准，3.独家协议-非标，4.补充协议-标准，5.补充协议-非标
						if ("2".equals(fileType) || "3".equals(fileType)) {
							ttaObjectDao.executeSqlUpdate("update tta_sole_agrt tsa \n" +
									"       set \n" +
									"       tsa.bic_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
									"       tsa.finance_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
									"       tsa.lega_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
									"       tsa.last_update_date = sysdate\n" +
									"     where tsa.sole_agrt_code = '" + orderNo + "' and tsa.sole_agrt_version =' " + orderVersion + "'");
						} else if ("4".equals(fileType)) {
							ttaObjectDao.executeSqlUpdate("update tta_sa_std_header tssh \n" +
									"       set \n" +
									"       tssh.bic_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
									"       tssh.finance_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
									"       tssh.lega_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
									"       tssh.last_update_date = sysdate\n" +
									"     where tssh.sa_std_code = '" + orderNo + "' and tssh.sa_std_version =" + orderVersion);
						} else {//5
							ttaObjectDao.executeSqlUpdate("update tta_sa_non_standar_header tssh \n" +
									"       set \n" +
									"       tssh.bic_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
									"       tssh.finance_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
									"       tssh.lega_register = to_date('" + dateToString + "','yyyy-MM-dd'),\n" +
									"       tssh.last_update_date = sysdate\n" +
									"     where tssh.order_no = '" + orderNo + "' and tssh.order_version =" + orderVersion);
						}
					}
				}
			}


		}
	}

	@Override
	public void saveExclusiveProposalData(TtaExclusiveHeaderEntity_RO entity) {
		int currentYear = LocalDate.now().getYear();
		String vendorCode = entity.getVendorCode();
		Integer soleAgrtHId = entity.getSoleAgrtHId();
		Date startDate = entity.getStartDate();
		Date endDate = entity.getEndDate();
		String startYear = SaafDateUtils.convertDateToString(startDate, "yyyy");
		String lastYear = SaafDateUtils.convertDateToString(endDate, "yyyy");
		List<Map<String, Object>> soleSupplierList = ttaObjectDao.queryNamedSQLForList(TtaExclusiveHeaderEntity_RO.getTtaSoleSupplier(soleAgrtHId), new HashMap<>());
		List<Map<String, Object>> contractProposalList = ttaObjectDao.queryNamedSQLForList(TtaExclusiveHeaderEntity_RO.getContractProposal(vendorCode, startYear, lastYear, currentYear), new HashMap<>());
		for (Map<String, Object> map : contractProposalList) {
			String conProposalOrder = map.get("PROPOSAL_ORDER") + "";
			String conVendorCode = map.get("VENDOR_CODE") + "";
			String convendorName = map.get("VENDOR_NAME") + "";
			BigDecimal conProposalVersion = (BigDecimal) map.get("PROPOSAL_VERSION");
			BigDecimal conproPosalYear = (BigDecimal)map.get("PROPOSAL_YEAR");
			BigDecimal conProposalId = (BigDecimal) map.get("PROPOSAL_ID");
			BigDecimal conContractHId = (BigDecimal)map.get("CONTRACT_H_ID");
			String conConditionVendor = map.get("CONDITION_VENDOR") + "";
			boolean flag = false;
			for (Map<String, Object> objectMap : soleSupplierList) {
				String supplierCode = objectMap.get("SUPPLIER_CODE") + "";
				BigDecimal soleproposalYear = (BigDecimal)objectMap.get("PROPOSAL_YEAR");
				BigDecimal solepoposalId = (BigDecimal)objectMap.get("POPOSAL_ID");
				BigDecimal soleContractHId = (BigDecimal)objectMap.get("CONTRACT_H_ID");
				if (conVendorCode.equals(supplierCode) && conproPosalYear.equals(soleproposalYear)
						&& conProposalId.equals(solepoposalId) && conContractHId.equals(soleContractHId)) {
					flag = true;
				}
			}
			if (!flag) {
				String sql = "insert into tta_sole_supplier\n" +
						"   (sole_supplier_id,\n" +
						"    sole_agrt_h_id,\n" +
						"    poposal_id,\n" +
						"    poposal_code,\n" +
						"    supplier_code,\n" +
						"    supplier_name,\n" +
						"    version_num,\n" +
						"    creation_date,\n" +
						"    created_by,\n" +
						"    last_updated_by,\n" +
						"    last_update_date,\n" +
						"    last_update_login,\n" +
						"    proposal_version,\n" +
						"    proposal_year,\n" +
						"    contract_h_id,\n" +
						"    condition_vendor)\n" +
						" values\n" +
						"   (SEQ_TTA_SOLE_SUPPLIER.NEXTVAL,\n" +
						"    " + soleAgrtHId + ",\n" +
						"    " + conProposalId + ",\n" +
						"    '" + conProposalOrder + "',\n" +
						"    '" + conVendorCode + "',\n" +
						"    '" + convendorName + "',\n" +
						"    ',\n" +
						"    sysdate,\n" +
						"    -1,\n" +
						"    -1,\n" +
						"    sysdate,\n" +
						"    -1,\n" +
						"    " + conProposalVersion + ",\n" +
						"    " + conproPosalYear + ",\n" +
						"    " + conContractHId +",\n" +
						"    '" + conConditionVendor + "')";
				ttaObjectDao.executeSqlUpdate(sql);
				LOGGER.info("独家协议更新Proposal信息成功,执行的SQL:\n{}",sql);
			}
		}
	}

	@Override
	public JSONObject sendMailByAllScheduler(String msgContent){
		JSONObject instance = new JSONObject();
		JSONObject sourceCfg = new JSONObject();
		JSONObject result = null;
		try {
			//获取收件人邮箱
			List<Map<String, Object>> listMap = ttaObjectDao.queryNamedSQLForList(TtaObjectEntity_HI_RO.getSaleNoticeUser("EMAIL_SALES"), new HashMap<String, Object>());
			String receiveCode = "";
			if (listMap != null && !listMap.isEmpty()) {
				receiveCode = listMap.get(0).get("MEANING") + "";
			}
			if (StringUtil.isEmpty(receiveCode) || "null".equalsIgnoreCase(receiveCode)) {
				receiveCode = ResourceUtils.getEmialReceiveCode();
			}

			instance.put("msgSubject", "TTA调度系统邮件通知");
			instance.put("receiveCode",  receiveCode);//收件人邮箱
			String paraConfig = ResourceUtils.getEmailParaConfig(); //"{\"ServerHost\":\"Wtccn-gz-mailgate.aswgcn.asiapacific.aswgroup.net\",\"sendFrom\":\"WTCCN.service@watsons-china.com.cn\",\"sendName\":\"WTCCN.service\"}";
			sourceCfg.put("paramCfg",paraConfig);
			sourceCfg.put("sourceUser",ResourceUtils.getEmailUser()); //WTCCN.service@watsons-china.com.cn
			sourceCfg.put("sourcePwd","");
		} catch (Exception e) {
			LOGGER.error("邮件组装参数异常：{}", e);
		}
		//是否需要发送
		if (ResourceUtils.getIsSendMsg()) {
			instance.put("msgContent", msgContent);
			result = EmailUtil.sendMail(instance, sourceCfg);
			LOGGER.info("邮件发送情况:{}, 邮件发送内容:{}" , result, instance);
		}
		return result;
	}
}
