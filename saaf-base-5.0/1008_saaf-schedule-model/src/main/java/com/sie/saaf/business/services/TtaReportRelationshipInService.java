package com.sie.saaf.business.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.sie.saaf.business.model.inter.ITtaObject;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.schedule.utils.MyLogUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.entities.TtaReportRelationshipInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaReportRelationshipIn;
import com.sie.saaf.business.model.inter.server.TtaReportRelationshipInServer;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.FTPUtil;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.ResourceUtils;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import com.sie.saaf.schedule.utils.StringUtil;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/ttaReportRelationshipInService")
public class TtaReportRelationshipInService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportRelationshipInService.class);
	/**
	 * <code>
	 * @Autowired
	 * private ITtaReportRelationshipIn ttaReportRelationshipInServer;
	 * @Override
	 * public IBaseCommon getBaseCommonServer(){
	 * return this.ttaReportRelationshipInServer;
	 * }
	 * </code>
	 */
	  @Autowired
	  private TtaReportRelationshipInServer ttaReportRelationshipInServer;

	@RequestMapping("/saveBatchReport")
	public String saveBatchReport(JobExecutionContext context) {
		LOGGER.info(".saveBatchReport  汇报关系 调度开始执行!");
		ITtaObject iTTtaObject = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		ITtaReportRelationshipIn ttaReportRelationshipInServer = (ITtaReportRelationshipIn) SpringBeanUtil.getBean("ttaReportRelationshipInServer");
		FTPClient ftpClient = null;
		InputStream ins = null;
		BufferedReader reader = null;
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		try {
			//ins = ftpClient.retrieveFileStream("/TTA/ReportTo_20190628.txt");
			ftpClient = FTPUtil.getFTPClient(ResourceUtils.getFtpHost(), ResourceUtils.getFtpPassword(), ResourceUtils.getFtpUserName(), ResourceUtils.getFtpPort());
			String reportToFile = StringUtil.getBasePath("ReportTo_");
			LOGGER.info(".saveBatchReport 汇报关系文件路径:{}。", reportToFile);
			ins = ftpClient.retrieveFileStream(reportToFile);
			reader = new BufferedReader(new InputStreamReader(ins, "utf-8"));
			List<Map<String, Object>> dataList = SaafToolUtils.fileDataAssembleList(reader, "\\|");
			//1.把文件中数据转化封装成set集合
			LinkedHashSet<TtaReportRelationshipInEntity_HI> reportList = new LinkedHashSet(JSON.parseArray(SaafToolUtils.toJson(dataList), TtaReportRelationshipInEntity_HI.class));
			//2.查询所有数据
			List<TtaReportRelationshipInEntity_HI> findList = ttaReportRelationshipInServer.findList(new JSONObject());
			Map<String, TtaReportRelationshipInEntity_HI> recordMap = new HashMap<>();
			for (TtaReportRelationshipInEntity_HI entity_hi : findList) {
				recordMap.put(entity_hi.getEmployeeNo(), entity_hi);
			}
			//3.比对所有数据
			reportList.removeAll(findList);
			//4.差异数据可能是新增或者更新操作，如果更新的话，把id值赋值
			for (TtaReportRelationshipInEntity_HI entity_hi : reportList) {
				TtaReportRelationshipInEntity_HI entity = recordMap.get(entity_hi.getEmployeeNo());
				//做更新操作必须保证有id和版本号
				if (entity != null) {
					//entity_hi.setReportId(entity.getReportId());
					entity_hi.setVersionNum(entity.getVersionNum());
					entity_hi.setLastUpdateDate(new Date());
					entity_hi.setCreationDate(entity.getCreationDate());
				}
			}
			//5.更新或保存差异数据
			LOGGER.info("汇报关系数据差异数据条目数：" +  reportList.size());
			ttaReportRelationshipInServer.saveBatchReport(reportList);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "bpm汇报关系数据保存成功", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("汇报关系保存文件数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "bpm汇报关系数据保存失败!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			String msg = "您好,今日:" + SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd") + ",调度执行BPM汇报关系数据接口失败,请求路径为:ttaReportRelationshipInService/saveBatchReport," +
					"执行的同步文件路径为:" + ResourceUtils.getFtpHost() + "/" + StringUtil.getBasePath("ReportTo_") + "失败原因为:\n" + e.getMessage() + "\n" + "请及时排查问题!";
			JSONObject jsonObject = iTTtaObject != null ? iTTtaObject.sendMailByAllScheduler(msg) : null;
			LOGGER.error("邮件发送情况:{}" , jsonObject);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			SaafToolUtils.close(ftpClient, ins, reader);
		}
	}

	@RequestMapping("/saveBatchReport2")
	public void saveBatchReport2(/*JobExecutionContext context*/) {
		try {
			ttaReportRelationshipInServer.saveBatchJDBCSelect();
		} catch (Exception e) {
			LOGGER.error("保存数据异常：{}", e);
		} finally {
		}
	}


	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
}