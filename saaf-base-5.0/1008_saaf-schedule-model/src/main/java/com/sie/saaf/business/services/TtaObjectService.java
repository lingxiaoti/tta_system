package com.sie.saaf.business.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.entities.readonly.TtaObjectSalePurchaseEntity_HI_RO;
import com.sie.saaf.business.model.inter.ITtaObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.*;
import com.yhg.base.utils.DateUtil;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
@RestController
@RequestMapping("/ttaObjectService")
public class TtaObjectService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaObjectService.class);

	//@Autowired
	//private ITtaObject iTTtaObject;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return  (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
	}
	
	/**
	 * 店铺对应市场：（每天跑一次零晨1:30点，跑当天全量），暂时uat环境ftp文件导入数据，生产环境dblink方式 。
	 */
	@RequestMapping("/saveBatchTtaShopMarket")
	public String saveBatchTtaShopMarket(JobExecutionContext context) {
		ITtaObject  ttaObjectServer = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		FTPClient ftpClient = null;
		InputStream ins = null;
		BufferedReader reader = null;
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		try {
			//ftpClient = FTPUtil.getFTPClient(ResourceUtils.getFtpHost(), ResourceUtils.getFtpPassword(), ResourceUtils.getFtpUserName(), ResourceUtils.getFtpPort());
			//ins = ftpClient.retrieveFileStream("/TTA/ReportTo_20190628.txt");
			//ins = new FileInputStream(new File("C:\\Users\\Administrator.SC-201811110953\\Desktop\\销售数据\\Format Demo\\ITF_RMS2TTA_STORE.txt"));

			String fileSuffix = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd"), -1);
			String fileName = ResourceUtils.getSftpBasePath() + "ITF_RMS2TTA_STORE_" + fileSuffix + ".txt";
			LOGGER.info(".saveBatchTtaShopMarket  fileSuffix文件路径:{}", fileName);
			File filePath = new File(fileName);
			ins = new FileInputStream(filePath);
			reader = new BufferedReader(new InputStreamReader(ins, "utf-8"));
			List<Map<String, Object>> dataList = SaafToolUtils.fileDataAssembleList(reader, "\\|");
			ttaObjectServer.saveJdbcBatchObject("tta_shop_market_in", dataList);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "rms店铺与市场对应关系数据保存成功", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveBatchTtaShopMarket保存文件数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "rms店铺与市场对应关系数据保存失败", null,MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			String fileSuffix = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd"), -1);
			String fileName = ResourceUtils.getSftpBasePath() + "ITF_RMS2TTA_STORE_" + fileSuffix + ".txt";
			String msg = "您好,今日:" + SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd") + ",调度执行RMS店铺与市场对应关系数据接口失败,请求路径为:ttaObjectService/saveBatchTtaShopMarket," +
					"执行的同步文件路径为:" + fileName + ";失败原因为:\n" + e.getMessage() + "\n" + "请及时排查问题!";
			JSONObject jsonObject = ttaObjectServer != null ? ttaObjectServer.sendMailByAllScheduler(msg) : null;
			LOGGER.error("邮件发送情况:{}" , jsonObject);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			SaafToolUtils.close(ftpClient, ins, reader);
		}
	}
	
	

	/**
	 * 采购数据：PURCHASE数据（当天跑前一天数据，当天零晨2点跑增量数据，TTA系统按年度分表)，生产环境dblink方式 。
	 */
	@RequestMapping("/saveBatchTtaPurchase")
	public String saveBatchTtaPurchase(JobExecutionContext context) {
		FTPClient ftpClient = null;
		BufferedInputStream ins = null;
		BufferedReader reader = null;
        String scheduleId = null;
        long startTime = System.currentTimeMillis();
		ITtaObject  iTTtaObject = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		try {
			LOGGER.info("每天同步定时同步采购数据开始。");
            scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
			 //rms 每天中午提供数据，tta延后一天跑数据。
			//File filePath = new File(ResourceUtils.getSftpBasePath() + "ITF_RMS2TTA_PURCHASE.txt");
			String year = DateUtil.date2Str(DateUtil.addDay(new Date(), -1), "yyyy");
			String fileSuffix = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd"), -1);
			String fileName = ResourceUtils.getSftpBasePath() + "ITF_RMS2TTA_PURCHASE_" + fileSuffix + ".txt";
			LOGGER.info("采购数据开始调用,year:{}, fileSuffix:{},fileName:{}", year, fileSuffix, fileName);
			ins = new BufferedInputStream(new FileInputStream(fileName));
			iTTtaObject.saveBatchTtaPurchaseByDay(ins);
			//iTTtaObject.updateTtaPurchase(year);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "每天调度RMS采购数据成功！", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "每天调度RMS采购数据失败!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			LOGGER.error(this.getClass() + ".saveBatchTtaPurchase保存文件数据异常：{}", e);
			String fileSuffix = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd"), -1);
			String fileName = ResourceUtils.getSftpBasePath() + "ITF_RMS2TTA_PURCHASE_" + fileSuffix + ".txt";
			String msg = "您好,今日:" + SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd") + ",调度执行RMS采购数据接口失败,请求路径为:ttaObjectService/saveBatchTtaPurchase" +
					"执行的同步文件路径为:" + fileName + "失败原因为:\n" + e.getMessage() + "\n" + "请及时排查问题!";
			JSONObject jsonObject = iTTtaObject != null ? iTTtaObject.sendMailByAllScheduler(msg) : null;
			LOGGER.error("邮件发送情况:{}" , jsonObject);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			LOGGER.info("每天同步采购数据耗时：{}秒。" , (System.currentTimeMillis() - startTime) / 1000);
			SaafToolUtils.close(ftpClient, ins, reader);
		}


	}

	/**
	 * 功能描述：ales文件格式没有任何变更，只是将输出调整为By Day，由于存在Missing Sales的情况，因此每天传送的文件会存在过往几天的数据，
	 * 如果要导入系统需要你那边By Day By Store清理历史数据后再导入。
	 */
	@RequestMapping("/saveBatchTtaSalesByDay")
	public String saveBatchTtaSalesByDay(JobExecutionContext context) {
		String scheduleId = null;
		FTPClient ftpClient = null;
		BufferedInputStream ins = null;
		long startTime = System.currentTimeMillis();
		try {
			LOGGER.info("每天同步定时同步销售数据开始。");
			ITtaObject  iTTtaObject = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
			scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
			String fileSuffix = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd"), -1);
			String fileName = ResourceUtils.getSftpBasePath() + "ITF_BO2TTA_SALES_" + fileSuffix + ".txt";
			LOGGER.info("每天同步定时同步销售数据开始,当前操作的文件是" + fileName);
			//fileName = "D:/abc/" + "ITF_BO2TTA_SALES_" + fileSuffix + ".txt";
			File file = new File(fileName);
			ins = new BufferedInputStream(new FileInputStream(file));
			iTTtaObject.saveBatchTtaSalesByDay(ins, fileSuffix);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "调度sales数据保存成功!", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveBatchTtaSalesByDay保存文件数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "调度sales数据保存异常!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			LOGGER.info("每天销售采购数据耗时：{}秒。" , (System.currentTimeMillis() - startTime) / 1000);
			SaafToolUtils.close(ftpClient, ins, null);
		}
	}



	/**
	 * 销售数据：（当天跑前一天数据，当天零晨1点跑增量数据，TTA系统按年度分表)，生产环境dblink方式 。
	 */
	@RequestMapping("/saveBatchTtaSales")
	public String saveBatchTtaSales(JobExecutionContext context) {
		FTPClient ftpClient = null;
		BufferedInputStream ins = null;
		try {
			//String filePath = "C:\\Users\\Administrator.SC-201811110953\\Desktop\\销售数据\\Format Demo\\ITF_BO2TTA_SALES.txt";
		/*	SFTPUtil sftpUtil = new SFTPUtil(ResourceUtils.getSftpUserName(), ResourceUtils.getSftpPassword(), ResourceUtils.getSftpHost(), ResourceUtils.getSftpPort());
			File downloadFile = sftpUtil.downloadFile(ResourceUtils.getSftpRemoteBasePath(), ResourceUtils.getSftpBasePath());
			File file = new File(ResourceUtils.getBasePath());*/
			File file = new File(ResourceUtils.getSftpBasePath());
			LOGGER.info("saveBatchTtaSales 获取文件路径信息：" +  file.getPath());
			String[] filePathArr = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toUpperCase().contains("ITF_BO2TTA_SALES");
				}
			});
			Arrays.sort(filePathArr);
			for (String fileName : filePathArr) {
				LOGGER.info("saveBatchTtaSales 获取文件名信息：" +  fileName);
				try {
					String tableSuffix = fileName.substring("ITF_BO2TTA_SALES".length() + 1,"ITF_BO2TTA_SALES_".length() + 6);
					ins = new BufferedInputStream(new FileInputStream(new File(file.getPath() + File.separatorChar + fileName)));
					LOGGER.info("保存的表名:" + "TTA_SALES_IN_" + tableSuffix);
					this.batchSaveDataList("TTA_SALES_IN_" + tableSuffix, ins, "\\|", 10000);
				} catch (Exception e) {
					LOGGER.error("saveBatchTtaSales error:{}", e);
				} finally {
					if (ins != null) {
						ins.close();
					}
				}
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveBatchTtaSales保存文件数据异常：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			SaafToolUtils.close(ftpClient, ins, null);
		}
	}

	
	/**
	 * 销售数据：PURCHASE数据（当天跑前一天数据，当天零晨1点跑增量数据，TTA系统按年度分表)，生产环境dblink方式 。
	 */

	@RequestMapping("/saveBatchTtaItems")
	public String saveBatchTtaItems(JobExecutionContext context) {
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		LOGGER.info(".saveBatchTtaItems  item数据开始调度执行!");
		ITtaObject  ttaObjectServer = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		FTPClient ftpClient = null;
		BufferedInputStream ins = null;
		BufferedReader reader = null;
		try {
			//当前日期减一天
			String fileSuffix = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd"), -1);
			String fileName = ResourceUtils.getSftpBasePath() + "ITF_RMS2TTA_ITEM_" + fileSuffix + ".txt";
			LOGGER.info(".saveBatchTtaItems  fileSuffix文件路径:{}", fileName);
			File file = new File(fileName);
			ins = new BufferedInputStream(new FileInputStream(file));
			LOGGER.info("保存的表名:" + "TTA_ITEM_IN");
			this.batchSaveDataList("TTA_ITEM_IN", ins , "\\|", 10000);
			ttaObjectServer.callProUpdateTtaItem();
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "rms item数据保存成功", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveBatchTtaItems保存文件数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "rms item数据保存失败!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			String fileSuffix = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd"), -1);
			String fileName = ResourceUtils.getSftpBasePath() + "ITF_RMS2TTA_ITEM_" + fileSuffix + ".txt";
			String msg = "您好,今日:" + SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd") + ",调度执行RMS ITEM数据接口失败,请求路径为:ttaObjectService/saveBatchTtaItems" +
					"执行的同步文件路径为:" + fileName + ",失败原因为:\n" + e.getMessage() + "\n" + "请及时排查问题!";
			JSONObject jsonObject = ttaObjectServer != null ? ttaObjectServer.sendMailByAllScheduler(msg) : null;
			LOGGER.error("邮件发送情况:{}" , jsonObject);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			SaafToolUtils.close(ftpClient, ins, reader);
		}
	}



	@RequestMapping("/saveBatchTtaVendor")
	public String saveBatchTtaVendor(JobExecutionContext context) {
		ITtaObject  ttaObjectServer = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		FTPClient ftpClient = null;
		InputStream ins = null;
		BufferedReader reader = null;
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		try {
			String fileSuffix = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd"), -1);
			String filePath = ResourceUtils.getSftpBasePath() + "ITF_RMS2TTA_SUPPLIER_" + fileSuffix + ".txt";
			LOGGER.info(".saveBatchTtaVendor  fileSuffix文件路径:{}", filePath);
			ins = new FileInputStream(new File(filePath));
			reader = new BufferedReader(new InputStreamReader(ins, "utf-8"));
			List<Map<String, Object>> dataList = SaafToolUtils.fileDataAssembleList(reader, "\\|");
			ttaObjectServer.saveJdbcBatchObject("tta_vendor_in", dataList);
			ttaObjectServer.callProUpdateTtaSupplier();
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "rms供应商数据保存成功", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".saveBatchTtaVendor 保存文件数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "rms供应商数据保存失败!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			String fileSuffix = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd"), -1);
			String filePath = ResourceUtils.getSftpBasePath() + "ITF_RMS2TTA_SUPPLIER_" + fileSuffix + ".txt";
			String msg = "您好,今日:" + SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd") + ",调度执行RMS供应商数据接口失败,请求路径为:ttaObjectService/saveBatchTtaVendor" +
					"执行的同步文件路径为:" + filePath + ",失败原因为:\n" + e.getMessage() + "\n" + "请及时排查问题!";
			JSONObject jsonObject = ttaObjectServer != null ? ttaObjectServer.sendMailByAllScheduler(msg) : null;
			LOGGER.error("邮件发送情况:{}" , jsonObject);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			SaafToolUtils.close(ftpClient, ins, reader);
		}
	}



	@RequestMapping("/savettaStoreitemSupplier")
	public String savettaStoreitemSupplier(JobExecutionContext context) {
		FTPClient ftpClient = null;
		BufferedInputStream ins = null;
		try {
			/*	SFTPUtil sftpUtil = new SFTPUtil(ResourceUtils.getSftpUserName(), ResourceUtils.getSftpPassword(), ResourceUtils.getSftpHost(), ResourceUtils.getSftpPort());
			File downloadFile = sftpUtil.downloadFile(ResourceUtils.getSftpRemoteBasePath(), ResourceUtils.getSftpBasePath());
			File file = new File(ResourceUtils.getBasePath());*/
			
			File file = new File(ResourceUtils.getSftpBasePath());
			LOGGER.info("savettaStoreitemSupplier 获取文件路径信息：" +  file.getPath());
			String[] filePathArr = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toUpperCase().contains("ITF_BO2TTA_SUPPLIER");
				}
			});
			for (String fileName : filePathArr) {
				LOGGER.info("savettaStoreitemSupplier 获取文件名信息：" +  fileName);
				try {
					ins = new BufferedInputStream(new FileInputStream(new File(file.getPath() + File.separatorChar + fileName)));
					LOGGER.info("保存的表名:" + "tta_store_item_supplier");
					this.batchSaveDataList("tta_store_item_supplier", ins, "\\|", 10000);
				} catch (Exception e) {
					LOGGER.error("savettaStoreitemSupplier error:{}", e);
				} finally {
					if (ins != null) {
						ins.close();
					}
				}
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".savettaStoreitemSupplier保存文件数据异常：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			SaafToolUtils.close(ftpClient, ins, null);
		}
	}
	

	@RequestMapping("/saveSalePurchaseJob")
	public String saveSalePurchaseJob(JobExecutionContext context) {
		try {
			ITtaObject  iTTtaObject = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
			Integer diffDay = StringUtils.isBlank(ResourceUtils.getTradeDiffDate()) ? 3 : Integer.parseInt(ResourceUtils.getTradeDiffDate());
			Date date = new Date();
			String delayDate = SaafDateUtils.dateDiffDay(SaafDateUtils.convertDateToString(date, "yyyyMMdd"), diffDay);//交易日历延迟x天，可配置
			date = SaafDateUtils.string2UtilDate(delayDate, "yyyyMMdd");

			String currentYear =  "2019"; //SaafDateUtils.convertDateToString(date, "yyyy"); //"2019";
			String lastYear = "2018"; //SaafDateUtils.dateDiffYear(currentYear, -1); //"2018";
			String currentYearMonth = "201912"; //SaafDateUtils.convertDateToString(date, "yyyyMM"); //"201911";
			String lastYearMonth = "201911"; //SaafDateUtils.dateDiffMonth(currentYearMonth, -1); //"201910";

			LOGGER.info(".saveSalePurchaseJob 调度入参信息:currentYear:{}, lastYear:{}, currentYearMonth:{}, lastYearMonth:{}",currentYear, lastYear, currentYearMonth, lastYearMonth);
			//当前日期延迟X天，如果与交易日期相等则执行，否则不在往下执行。
			List<Map<String, Object>> tradeYearMonthList = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.getTradeDate(delayDate), new HashMap<>());
			String tradeYearMonth = tradeYearMonthList.get(0).get("TRADE_YEAR_MONTH") + "";//交易年月
			String weekStart = tradeYearMonthList.get(0).get("WEEK_START") + "";//交易开始日期
			LOGGER.info("当前设定的交易日历延期:{}天,当前延迟的日期是:{}，当前查询的交易年月是:{}, 交易开始日期是:{}", diffDay, delayDate, tradeYearMonth, weekStart);
			if (delayDate.compareTo(weekStart) != 0) {//当延期的
				LOGGER.info("当前设定的交易日历延期:{}天,当前延迟的日期是:{}，当前查询的交易年月是:{}, 交易开始日期是:{}, 不往下执行！", diffDay, delayDate, tradeYearMonth, weekStart);
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
			}

			//查看tta_sale_sum_xx表是否有数据，没有数据表示没有执行，否则有执行过。
			iTTtaObject.saveCreateTable("tta_sale_sum_" + tradeYearMonth, "tta_sale_sum");
			List<Map<String, Object>> ttaSaleSumList = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.getTtaSaleSum(tradeYearMonth), new HashMap<String, Object>());
			if (!"0".equalsIgnoreCase(ttaSaleSumList.get(0).get("CNT") + "")) {
				LOGGER.info("tta_sale_sum{}表有数据, 不往下执行！", tradeYearMonth);
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
			}
			//2.查询店铺市场数据是否每天都有, tta_shop_market_in。
			HashMap<String, Object> queryMap = new HashMap<>();
			queryMap.put("month", currentYearMonth);
			List<Map<String, Object>> tradeMonthList = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.QUERY_TRADE_MONTH_START_END_SQL, queryMap);
			Assert.notEmpty(tradeMonthList, "交易日历表数据为空调度失败！");
			Map<String, Object> tradeMap = tradeMonthList.get(0);
			String startDate = tradeMap.get("startDate") + "";
			String endDate = tradeMap.get("endDate") + "";
			ArrayList monthArray = new ArrayList<String>();
			while (endDate.compareTo(startDate) > 0) {
				monthArray.add(startDate);
				startDate = SaafDateUtils.dateDiffDay(startDate, 1);
			}
			List<Map<String, Object>> list = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.QUERY_SHOP_MARKET_DATE_LIST_SQL, queryMap);
			ArrayList<Object> shopMarketList = new ArrayList<>();
			if (list != null) {
				list.forEach(item->{
					shopMarketList.add(item.get("TRADEDATE") + "");
				});
			}
			//2.1 差值，如为空，店铺市场每天都有数据，则继续执行，否则不在执行。
			monthArray.removeAll(shopMarketList);
			if (!monthArray.isEmpty()) {
				LOGGER.info("saveSalePurchaseJob 在交易月没店铺市场有缺省数据,缺省的日期是:{}", monthArray);
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "在交易月没店铺市场有缺省数据，缺省的日期是:" + monthArray, 0, null).toString();
			}
			//3.判断purchase，销售数据、交易月份是否有数据。
			List<Map<String, Object>> saleList = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.getSalesCount(currentYearMonth), new HashMap<>());
			if (saleList == null || saleList.size() == 0 || "0".equalsIgnoreCase(saleList.get(0).get("CNT") + "")) {
				LOGGER.info("saveSalePurchaseJob 在交易月缺省销售数据。");
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "在交易月缺省销售数据", 0, null).toString();
			}
			List<Map<String, Object>> purchaseList = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.getPurchaseCount(currentYearMonth), new HashMap<>());
			if (purchaseList == null || purchaseList.size() == 0 || "0".equalsIgnoreCase(purchaseList.get(0).get("CNT") + "")) {
				LOGGER.info("saveSalePurchaseJob 在交易月缺省采购数据。");
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "在交易月缺省采购数据", 0, null).toString();
			}
			//开始跑数据操作
			iTTtaObject.saveSalePurchaseJob(currentYear,  currentYearMonth,  lastYear,  lastYearMonth);
			LOGGER.info("saveSalePurchaseJob 执行成功");
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("saveSalePurchaseJob 出错：{}",  e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
	}

	/**
	 * 功能描述：手动触发执行跑销售和采购的占比
	 */
	@RequestMapping("/manualSaveSalePurchase")
	public String manualSaveSalePurchase(@RequestParam("params") String params) {
		try {
			LOGGER.info("手动跑数据开始。");
			ITtaObject  iTTtaObject = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
			JSONObject jsonObject = this.parseObject(params);
			String currentYearMonth = jsonObject.getString("currentYearMonth");// "201912"; //SaafDateUtils.convertDateToString(date, "yyyyMM"); //"201911";
			String currentYear =  currentYearMonth.substring(0, 4); //"2019" //SaafDateUtils.convertDateToString(date, "yyyy"); //"2019";
			String lastYear = SaafDateUtils.dateDiffYear(currentYear, -1); //"2018"; //SaafDateUtils.dateDiffYear(currentYear, -1); //"2018";
			String lastYearMonth = SaafDateUtils.dateDiffMonth(currentYearMonth, -1); //"201911"; //SaafDateUtils.dateDiffMonth(currentYearMonth, -1); //"201910";

			LOGGER.info(".manualSaveSalePurchase 调度入参信息:currentYear:{}, lastYear:{}, currentYearMonth:{}, lastYearMonth:{}",currentYear, lastYear, currentYearMonth, lastYearMonth);
			if (StringUtils.isBlank(currentYearMonth) || currentYearMonth.length() != 6) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "参数错误！", 0, null).toString();
			}
			//查看tta_sale_sum_xx表是否有数据，没有数据表示没有执行，否则有执行过。
			iTTtaObject.saveCreateTable("tta_sale_sum_"  + currentYearMonth, "tta_sale_sum");
			List<Map<String, Object>> ttaSaleSumList = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.getTtaSaleSum(currentYearMonth), new HashMap<String, Object>());
			if (!"0".equalsIgnoreCase(ttaSaleSumList.get(0).get("CNT") + "")) {
				LOGGER.info("tta_sale_sum{}表有数据, 不往下执行！", currentYearMonth);
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
			}
			//2.查询店铺市场数据是否每天都有, tta_shop_market_in。
			HashMap<String, Object> queryMap = new HashMap<>();
			queryMap.put("month", currentYearMonth);
			List<Map<String, Object>> tradeMonthList = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.QUERY_TRADE_MONTH_START_END_SQL, queryMap);
			Assert.notEmpty(tradeMonthList, "交易日历表数据为空调度失败！");
			Map<String, Object> tradeMap = tradeMonthList.get(0);
			String startDate = tradeMap.get("startDate") + "";
			String endDate = tradeMap.get("endDate") + "";
			ArrayList monthArray = new ArrayList<String>();
			while (endDate.compareTo(startDate) > 0) {
				monthArray.add(startDate);
				startDate = SaafDateUtils.dateDiffDay(startDate, 1);
			}
			List<Map<String, Object>> list = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.QUERY_SHOP_MARKET_DATE_LIST_SQL, queryMap);
			ArrayList<Object> shopMarketList = new ArrayList<>();
			if (list != null) {
				list.forEach(item->{
					shopMarketList.add(item.get("TRADEDATE") + "");
				});
			}
			//2.1 差值，如为空，店铺市场每天都有数据，则继续执行，否则不在执行。
			monthArray.removeAll(shopMarketList);
			if (!monthArray.isEmpty()) {
				LOGGER.info("saveSalePurchaseJob 在交易月没店铺市场有缺省数据,缺省的日期是:{}", monthArray);
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "在交易月没店铺市场有缺省数据，缺省的日期是:" + monthArray, 0, null).toString();
			}
			//3.判断purchase，销售数据、交易月份是否有数据。
			List<Map<String, Object>> saleList = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.getSalesCount(currentYearMonth), new HashMap<>());
			if (saleList == null || saleList.size() == 0 || "0".equalsIgnoreCase(saleList.get(0).get("CNT") + "")) {
				LOGGER.info("saveSalePurchaseJob 在交易月缺省销售数据。");
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "在交易月缺省销售数据", 0, null).toString();
			}
			List<Map<String, Object>> purchaseList = iTTtaObject.queryNamedSQLForList(TtaObjectSalePurchaseEntity_HI_RO.getPurchaseCount(currentYearMonth), new HashMap<>());
			if (purchaseList == null || purchaseList.size() == 0 || "0".equalsIgnoreCase(purchaseList.get(0).get("CNT") + "")) {
				LOGGER.info("saveSalePurchaseJob 在交易月缺省采购数据。");
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "在交易月缺省采购数据", 0, null).toString();
			}
			//开始跑数据操作
			iTTtaObject.saveSalePurchaseJob(currentYear,  currentYearMonth,  lastYear,  lastYearMonth);
			LOGGER.info("saveSalePurchaseJob 执行成功");
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("saveSalePurchaseJob 出错：{}",  e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
	}


	@RequestMapping("/testJob")
	public void testJob(JobExecutionContext context) {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String s = SaafToolUtils.toJson(jobDataMap);
		System.out.println("======================" + s);
		Object scheduleId = MyLogUtils.getArguments(context, "scheduleId");
		LOGGER.info(".testJbo scheduleId:" + scheduleId);
		//MyLogUtils.saveErrorData(Integer.parseInt(scheduleId+""), null, "bpm市场数据保存成功", null, SUCCESS_STATUS);
	}
	/**
	 * 批量保存数据
	 * @param table
	 * @param bis
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void batchSaveDataList(String table, BufferedInputStream bis, String separator, int rownumber) {
		int count = 0;
		BufferedReader reader = null;
		try {
			ITtaObject  ttaObjectServer = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
			reader = new BufferedReader(new InputStreamReader(bis, "UTF-8"), 50 * 1024 * 1024);
			int idx = -1;
			String line = null;
			List<Map<String, Object>> dataList = new  ArrayList<>();
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
			ttaObjectServer.saveJdbcBatchObject(table, dataList);
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

	//每天定时删除指定文件(一个月之后的文件)
	@RequestMapping("/deleteFile")
	public static String deleteFile(JobExecutionContext context) {
		FTPClient ftpClient = null;
		BufferedInputStream ins = null;
		try {
			File file = new File(ResourceUtils.getSftpBasePath());//获取删除的目录信息
			LOGGER.info("deleteFile 获取需要删除的文件路径信息：" +  file.getPath());
			String[] filePathArr = file.list(new FilenameFilter() {
				String regex = "\\d{6}";
				Pattern compile = Pattern.compile(regex);
				@Override
				public boolean accept(File dir, String name) {
					Matcher matcher = compile.matcher(name);
					while (matcher.find()) {
						//删除前两个月的文件
						String month = matcher.group();
						String diffMonth = SaafDateUtils.dateDiffMonth(SaafDateUtils.convertDateToString(new Date(), "yyyyMM"), -2);
						if (diffMonth.compareTo(month) >= 0) {
							return true;
						}
					}
					return false;
				}
			});
			for (String fileName : filePathArr) {
				try {
					File deletefile = new File(file.getPath() + File.separator + fileName);
					if (deletefile.exists()) {
						LOGGER.info("deleteFile 获取文件名信息：" +  fileName);
						deletefile.delete();
					}
				} catch (Exception e) {
					LOGGER.error(".deleteFile 循环删除异常：{}", e);
				}
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(".deleteFile异常：{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
	}


	// 11/1 号
	public static void main(String[] args) {
	    /*
	     * 功能描述：
	     * @author xiaoga
	     sftpHost=10.82.24.106
        sftpPassword=sw7smROQAe
        sftpUserName=ttauat
        sftpPort=22
        #\u672C\u5730\u8DEF\u5F84
        sftpBasePath=/home/ttauat/business_data/
        #\u8FDC\u7A0B\u8DEF\u5F84
        sftpRemoteBasePath=/home/ttauat/business_data/
	     * @param
	     * @return
	     */

		//SFTPUtil sftpUtil = new SFTPUtil("ttauat", "sw7smROQAe", "10.82.24.106", 22);
		//File downloadFile = sftpUtil.downloadFile("/home/ttauat/business_data/ITF_BO2TTA_SALES_201809.txt", "D:\\sftp\\");
	}
	
}