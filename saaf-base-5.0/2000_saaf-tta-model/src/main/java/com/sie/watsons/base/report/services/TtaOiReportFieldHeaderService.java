package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaReportAttGenRecordEntity_HI;
import com.sie.watsons.base.report.model.inter.ITtaOiReportFieldHeader;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.inter.ITtaReportAttGenRecord;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaOiReportFieldHeaderService")
public class TtaOiReportFieldHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportFieldHeaderService.class);

	@Autowired
	private ITtaOiReportFieldHeader ttaOiReportFieldHeaderServer;

	@Autowired
	private ITtaReportAttGenRecord ttaReportAttGenRecordServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaOiReportFieldHeaderServer;
	}

	//用于控制并发导出的任务数
	private static ExecutorService concurrentExportPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	//阻塞队列
	private static  LinkedBlockingQueue<JSONObject> queue = new LinkedBlockingQueue<JSONObject>(100);

	public TtaOiReportFieldHeaderService() {
		LOGGER.info("导出类初始化");
		for (int i=0;i<8;i++){
			Runnable runnable=new Runnable() {
				@Override
				public void run() {
					while (true){
						JSONObject jsonObject = null;
						String type = "";
						try {
							LOGGER.info("{}开始处理，当前任务数:{}",Thread.currentThread().getName(),queue.size());
							jsonObject = queue.take();
							if(!SaafToolUtils.isNullOrEmpty(jsonObject.getString("type"))) {
								type = jsonObject.getString("type");
								TtaReportAttGenRecordEntity_HI recordEntity = ttaOiReportFieldHeaderServer.saveOrUpdateAttRecord(jsonObject);
								jsonObject.put("recordEntity", recordEntity);
								switch (type) {
									case "1":
										//报表1
										ttaOiReportFieldHeaderServer.findSupplierPerformanceReport(jsonObject, recordEntity);
										break;
									case "2":
										//报表2
										ttaOiReportFieldHeaderServer.findTopSupplierReport(jsonObject, recordEntity);
										break;
									case "4":
										Long fileId = ttaOiReportFieldHeaderServer.findTtaVSActualAchieveRateReportAll(jsonObject);
										recordEntity.setMsgCode("OK_STATUSCODE");
										recordEntity.setFileId(fileId.intValue());
										ttaOiReportFieldHeaderServer.saveOrUpdateAttRecord(recordEntity);
										break;
									case "6":
									case "7":
									case "8":
									case "9":
									case "10":
										//报表6-10
										ttaOiReportFieldHeaderServer.findOiFeeTypeCombinationReport(jsonObject,recordEntity);
										break;
									case "12":
										//报表12
										ttaOiReportFieldHeaderServer.findTtaABOIReportAll(jsonObject);
										break;
									case "20"://OI分摊导出
										ttaOiReportFieldHeaderServer.findOisceneReport(jsonObject,recordEntity);
										break;
								}
							}
						}catch (Exception e){
							if(!SaafToolUtils.isNullOrEmpty(jsonObject.getString("type"))) {
								type = jsonObject.getString("type");
								HashSet hashSet = new HashSet();
								hashSet.add("4");hashSet.add("12");
								String [] types = {"4","12"};
								if (hashSet.contains(type)) {
									TtaReportAttGenRecordEntity_HI recordEntityError = (TtaReportAttGenRecordEntity_HI)jsonObject.get("recordEntity");
									recordEntityError.setMsgRemark(e.toString());
									recordEntityError.setMsgCode("ERROR_STATUSCODE");
									LOGGER.warn("Exception:{}", e);
									try {
										ttaOiReportFieldHeaderServer.saveOrUpdateAttRecord(recordEntityError);
									}catch (Exception e2){
										LOGGER.error(e.getMessage(), e);
									}
								}
							}


						}
					}
				}
			};
			concurrentExportPool.submit(runnable);
		}
	}

	
	@RequestMapping(method = RequestMethod.POST, value = "saveQueque")
	public String saveQueque(@RequestParam(required = false) String params) throws Exception {
		UserSessionBean session = this.getUserSessionBean();
		JSONObject jsonObject = this.parseObject(params);
		String type = jsonObject.getString("type");
		jsonObject.put("type", type);
		jsonObject.put("userId",session.getUserId());
		boolean result=queue.offer(jsonObject);
		if (!result) {
			jsonObject.put("status","E");
			return SToolUtils.convertResultJSONObj("E", "当前下过人数过多(共计"+queue.size()+")，请稍后再试", 0, null).toString();
		}else {
			return SToolUtils.convertResultJSONObj("S", "数据生成中,请稍后查看！", 0, null).toString();
		}
	};



	/**
	 *  报表4实现
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTtaVSActualAchieveRateReport")
	public String findTtaVSActualAchieveRateReport(@RequestParam(required = false) String params) {
		UserSessionBean session = this.getUserSessionBean();
		JSONObject jsonObject = parseObject(params);
		String type = jsonObject.getString("type");
		jsonObject.put("type", type);
		jsonObject.put("userId",session.getUserId());
		boolean result=queue.offer(jsonObject);
		if (!result) {
			jsonObject.put("status","E");
			return SToolUtils.convertResultJSONObj("E", "当前下过人数过多(共计"+queue.size()+")，请稍后再试", 0, null).toString();
		}else {
			return SToolUtils.convertResultJSONObj("S", "数据生成中,请稍后查看！", 0, null).toString();
		}
	}

	/**
	 *  报表12实现
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTtaABOIReportAll")
	public String findTtaABOIReportAll(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			ttaOiReportFieldHeaderServer.findTtaABOIReportAll(jsonObject);
			jsonObject = (JSONObject) JSON.toJSON(new HashMap<String, Object>());
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("IllegalArgumentException:{}", e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("Exception:{}", e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 检查六大场景月份
	 */
	@RequestMapping(method = RequestMethod.POST, value = "checkOiReportYearMonth")
	public String checkOiReportYearMonth(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = this.parseObject(params);
			JSONObject instance = ttaOiReportFieldHeaderServer.checkOiReportYearMonth(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}