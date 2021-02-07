package com.sie.saaf.business.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.entities.TtaPersonInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaPersonIn;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.FTPUtil;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.ResourceUtils;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import com.sie.saaf.schedule.utils.StringUtil;
import com.yhg.base.utils.SToolUtils;


@RestController
@RequestMapping("/ttaPersonInService")
public class TtaPersonInService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPersonInService.class);
	
	@RequestMapping("/saveBatchPerson")
	public String saveBatchPerson(JobExecutionContext context) {
		FTPClient ftpClient = null;
		InputStream ins = null;
		BufferedReader reader = null;
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		ITtaObject iTTtaObject = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		try {
			LOGGER.info(".saveBatchPerson  人员信息调度开始执行!");
			ITtaPersonIn iTtaPersonIn = (ITtaPersonIn) SpringBeanUtil.getBean("ttaPersonInServer");
			//ins = new FileInputStream(new File("C:\\Users\\Administrator.SC-201811110953\\Desktop\\数据文件\\Employee_20190628.txt"));
			ftpClient = FTPUtil.getFTPClient(ResourceUtils.getFtpHost(), ResourceUtils.getFtpPassword(), ResourceUtils.getFtpUserName(), ResourceUtils.getFtpPort());
			String employeeFile = StringUtil.getBasePath("Employee_");
			LOGGER.info(".saveBatchPerson 人员信息文件路径:{}!", employeeFile);
			ins = ftpClient.retrieveFileStream(employeeFile);
			reader = new BufferedReader(new InputStreamReader(ins, "utf-8"));
			List<Map<String, Object>> dataList = SaafToolUtils.fileDataAssembleList(reader, "\\|");
			//1.把文件中数据转化封装成set集合
			LinkedHashSet<TtaPersonInEntity_HI> reportList = new LinkedHashSet(JSON.parseArray(SaafToolUtils.toJson(dataList), TtaPersonInEntity_HI.class));
			//2.查询所有数据
			List<TtaPersonInEntity_HI> findList = iTtaPersonIn.findList(new JSONObject());
			Map<String, TtaPersonInEntity_HI> recordMap = new HashMap<>();
			for (TtaPersonInEntity_HI entity_hi : findList) {
				recordMap.put(entity_hi.getCode(), entity_hi);
			}
			//3.比对所有数据
			reportList.removeAll(findList);
			//4.差异数据可能是新增或者更新操作，如果更新的话，把id值赋值
			for (TtaPersonInEntity_HI entity_hi : reportList) {
				TtaPersonInEntity_HI entity = recordMap.get(entity_hi.getCode());
				//做更新操作必须保证有id和版本号
				if (entity != null) {
					//entity_hi.setPersonInId(entity.getPersonInId());
					entity_hi.setVersionNum(entity.getVersionNum());
					entity_hi.setLastUpdateDate(new Date());
					entity_hi.setCreationDate(entity.getCreationDate());
				}
			}
			//5.更新或保存差异数据
			LOGGER.info("人员数据差异数据条目数：" +  reportList.size());
			iTtaPersonIn.saveOrUpdateAll(new ArrayList<>(reportList));
			iTtaPersonIn.callProBasePersonJob();
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "bpm人员数据保存成功", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("批量保存文件数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "bpm人员数据保存失败!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			String msg = "您好,今日:" + SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd") + ",调度执行BPM人员数据接口失败,请求路径为:ttaPersonInService/saveBatchPerson" +
					"执行同步文件路径为:" + ResourceUtils.getFtpHost() + "/" + StringUtil.getBasePath("Employee_") + ",失败原因为:\n" + e.getMessage() + "\n" + "请及时排查问题!";
			JSONObject jsonObject = iTTtaObject != null ? iTTtaObject.sendMailByAllScheduler(msg) : null;
			LOGGER.error("邮件发送情况:{}" , jsonObject);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			SaafToolUtils.close(ftpClient, ins, reader);
		}
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
}