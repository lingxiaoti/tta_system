package com.sie.saaf.business.services;

import java.io.*;
import java.util.*;

import com.sie.saaf.business.model.inter.ITtaObject;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesErrorEntity_HI;
import org.apache.commons.net.ftp.FTPClient;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.entities.TtaDeptInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaDeptIn;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.FTPUtil;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.MyLogUtils;
import com.sie.saaf.schedule.utils.ResourceUtils;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import com.sie.saaf.schedule.utils.StringUtil;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/ttaDeptInService")
public class TtaDeptInService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDeptInService.class);

	@Autowired
	private ITtaDeptIn ttaDeptInServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaDeptInServer;
	}

	
	@RequestMapping("/saveBatchDept")
	public String saveBatchDept(JobExecutionContext context) {
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		LOGGER.info(".saveBatchDept  部门信息调度开始执行!");
		ITtaDeptIn iTtaDeptIn = (ITtaDeptIn) SpringBeanUtil.getBean("ttaDeptInServer");
		ITtaObject iTTtaObject = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		FTPClient ftpClient = null;
		InputStream ins = null;
		BufferedReader reader = null;
		try {
			//ins = ftpClient.retrieveFileStream("/TTA/Dept_20190628.txt");
			ftpClient = FTPUtil.getFTPClient(ResourceUtils.getFtpHost(), ResourceUtils.getFtpPassword(), ResourceUtils.getFtpUserName(), ResourceUtils.getFtpPort());
			String deptFile = StringUtil.getBasePath("Dept_");
			LOGGER.info(".saveBatchDept 部门信息文件路径:{}!", deptFile);
			ins = ftpClient.retrieveFileStream(deptFile);
			reader = new BufferedReader(new InputStreamReader(ins, "utf-8"));
			List<Map<String, Object>> dataList = SaafToolUtils.fileDataAssembleList(reader,"\\|");
			//1.把文件中数据转化封装成set集合
			LinkedHashSet<TtaDeptInEntity_HI> reportList = new LinkedHashSet(JSON.parseArray(SaafToolUtils.toJson(dataList), TtaDeptInEntity_HI.class));
			//2.查询所有数据
			List<TtaDeptInEntity_HI> findList = iTtaDeptIn.findList(new JSONObject());
			HashMap<String, TtaDeptInEntity_HI> recordMap = new HashMap<>();
			for (TtaDeptInEntity_HI dept : findList) {
				recordMap.put(dept.getCode(), dept);
			}
			//3.差异数据可能是新增或者更新操作
			reportList.removeAll(findList);
			for (TtaDeptInEntity_HI entity : reportList ) {
				TtaDeptInEntity_HI dept = recordMap.get(entity.getCode());
				if (dept != null) {
					entity.setCreationDate(dept.getCreationDate());
					entity.setLastUpdateDate(new Date());
					entity.setVersionNum(dept.getVersionNum());
				}
				
			}
			//4.更新或保存差异数据
			iTtaDeptIn.saveOrUpdateBatchDept(reportList);
			LOGGER.info("部门数据差异数据条目数：" +  reportList.size());
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "bpm部门数据保存成功", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("部门批量保存文件数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "bpm部门数据保存失败!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			String msg = "您好,今日:" + SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd") + ",调度执行BPM部门数据接口失败,请求路径为:ttaDeptInService/saveBatchDept" +
					"执行的同步文件路径为:" + ResourceUtils.getFtpHost() + "/" + StringUtil.getBasePath("Dept_") + ",失败原因为:\n" + e.getMessage();
			JSONObject jsonObject = iTTtaObject != null ? iTTtaObject.sendMailByAllScheduler(msg) : null;
			LOGGER.error("邮件发送情况:{}" , jsonObject);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			SaafToolUtils.close(ftpClient, ins, reader);
		}
	}
}