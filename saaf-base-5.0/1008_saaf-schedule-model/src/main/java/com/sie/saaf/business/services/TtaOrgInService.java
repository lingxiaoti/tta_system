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
import com.sie.saaf.business.model.entities.TtaOrgInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaOrgIn;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.FTPUtil;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.ResourceUtils;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import com.sie.saaf.schedule.utils.StringUtil;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/ttaOrgInService")
public class TtaOrgInService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOrgInService.class);

	@Autowired
	private ITtaOrgIn ttaOrgInServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaOrgInServer;
	}

	@RequestMapping("/saveBatchOrg")
	public String saveBatchOrg(JobExecutionContext context) {
		FTPClient ftpClient = null;
		InputStream ins = null;
		BufferedReader reader = null;
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		ITtaObject iTTtaObject = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		try {
			LOGGER.info(".saveBatchOrg  组织机构调度开始执行!");
			ITtaOrgIn iTtaOrgIn = (ITtaOrgIn) SpringBeanUtil.getBean("ttaOrgInServer");
			ftpClient = FTPUtil.getFTPClient(ResourceUtils.getFtpHost(), ResourceUtils.getFtpPassword(), ResourceUtils.getFtpUserName(), ResourceUtils.getFtpPort());
			String organizationFile = StringUtil.getBasePath("Organization_");
			LOGGER.info(".saveBatchOrg 组织机构文件路径:{}!", organizationFile);
			ins = ftpClient.retrieveFileStream(organizationFile);
			reader = new BufferedReader(new InputStreamReader(ins, "utf-8"));
			List<Map<String, Object>> dataList = SaafToolUtils.fileDataAssembleList(reader,"\\|");
			//1.把文件中数据转化封装成set集合
			LinkedHashSet<TtaOrgInEntity_HI> reportList = new LinkedHashSet(JSON.parseArray(SaafToolUtils.toJson(dataList), TtaOrgInEntity_HI.class));
			//2.查询所有数据
			List<TtaOrgInEntity_HI> findList = iTtaOrgIn.findList(new JSONObject());
			Map<String, TtaOrgInEntity_HI> recordMap = new HashMap<>();
			for (TtaOrgInEntity_HI entity_hi : findList) {
				recordMap.put(entity_hi.getOrgCode(), entity_hi);
			}
			//3.比对所有数据
			reportList.removeAll(findList);
			//4.差异数据可能是新增或者更新操作，如果更新的话，把id值赋值
			for (TtaOrgInEntity_HI entity_hi : reportList) {
				TtaOrgInEntity_HI entity = recordMap.get(entity_hi.getOrgCode());
				//做更新操作必须保证有id和版本号
				if (entity != null) {
					//entity_hi.setOrgId(entity.getOrgId());
					entity_hi.setVersionNum(entity.getVersionNum());
					entity_hi.setLastUpdateDate(new Date());
					entity_hi.setCreationDate(entity.getCreationDate());
				}
			}
			LOGGER.info("组织机构数据差异数据条目数：" +  reportList.size());
			//5.更新或保存差异数据
			iTtaOrgIn.saveOrUpdateBatchOrg(reportList);
			iTtaOrgIn.callProBaseOrganization();
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "bpm组织机构数据保存成功", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("组织机构批量保存文件数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "bpm组织机构数据保存失败!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			String msg = "您好,今日:" + SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd") + ",调度执行BPM组织机构数据接口失败,请求路径为:ttaOrgInService/saveBatchOrg" +
					"执行同步文件路径为:" + ResourceUtils.getFtpHost() + "/" + StringUtil.getBasePath("Organization_") + "失败原因为:\n" + e.getMessage() + "\n" + "请及时排查问题!";
			JSONObject jsonObject = iTTtaObject != null ? iTTtaObject.sendMailByAllScheduler(msg) : null;
			LOGGER.error("邮件发送情况:{}" , jsonObject);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} finally {
			SaafToolUtils.close(ftpClient, ins, reader);
		}
	}
}