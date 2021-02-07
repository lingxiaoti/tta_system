package com.sie.saaf.business.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.business.model.entities.TtaProposalApprovlHeaderEntity_HI;
import com.sie.saaf.business.model.entities.readonly.TtaProposalApprovlHeaderEntity_HI_RO;
import com.sie.saaf.business.model.inter.ITtaProposalApprovlHeader;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.EmailUtil;
import com.sie.saaf.schedule.utils.MyLogUtils;
import com.sie.saaf.schedule.utils.ResourceUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaProposalApprovlHeaderService")
public class TtaProposalApprovlHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalApprovlHeaderService.class);
	public static final String A = "A";
	public static final String B = "B";
	public static final String C = "C";

	@Autowired
	private ITtaProposalApprovlHeader ttaProposalApprovlHeaderServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaProposalApprovlHeaderServer;
	}

	@RequestMapping("/ttaProposalApprovalSendMail")
	public String SendMail(JobExecutionContext context) {
		LOGGER.info(".saveSendMail  TTA_PROPOSAL审批邮件发送 调度开始执行!");
		try {
			ITtaProposalApprovlHeader ttaProposalApprovlHeaderServer = (ITtaProposalApprovlHeader) SpringBeanUtil.getBean("ttaProposalApprovlHeaderServer");
			//1.审批中的邮件发送
			List<TtaProposalApprovlHeaderEntity_HI_RO> findList = ttaProposalApprovlHeaderServer.findList();
			String curUserName = "";
			StringBuffer sb = new StringBuffer();
			//添加表头
			sb = headStr(sb,"请审批PROPOSAL单据");
			List<TtaProposalApprovlHeaderEntity_HI_RO> list= new ArrayList<TtaProposalApprovlHeaderEntity_HI_RO>();
			//对不同的办理人分类,如办理人为同一个人,那么把审批任务汇总起来,统一发送邮件
			for (int i = 0; i < findList.size(); i++) {
				TtaProposalApprovlHeaderEntity_HI_RO tpah = findList.get(i);
				if( (!curUserName.equals(tpah.getAssignee())) ){
					if (!com.sie.saaf.common.util.SaafToolUtils.isNullOrEmpty(curUserName)) {
						JSONObject jsonObject= new JSONObject();
						jsonObject.put("LIST",new ArrayList<TtaProposalApprovlHeaderEntity_HI_RO>(list));
						jsonObject.put("WAY","SEND_APPROVAL");
						list.clear();
						sb = send(sb,findList.get(i-1),context,jsonObject,ttaProposalApprovlHeaderServer);
						sb.setLength(0);
						sb = headStr(sb,"请审批PROPOSAL单据");

					}
					curUserName = SaafToolUtils.isNullOrEmpty(tpah.getAssignee())?"" : tpah.getAssignee();
				}
				list.add(tpah);
				sb = lineStr(sb,tpah);

				if ( (i == findList.size() - 1) && (!com.sie.saaf.common.util.SaafToolUtils.isNullOrEmpty(curUserName)) ) {
					JSONObject jsonObject= new JSONObject();
					jsonObject.put("LIST",new ArrayList<TtaProposalApprovlHeaderEntity_HI_RO>(list));
					jsonObject.put("WAY","SEND_APPROVAL");
					list.clear();
					sb = send(sb,tpah,context,jsonObject,ttaProposalApprovlHeaderServer);
				}
			}

			//2.审批完成的时候的邮件发送(发送给制单人)
			JSONObject paramJsonObject = new JSONObject();
			paramJsonObject.put("way","SEND_SUBMIT_PERSON");
			List<TtaProposalApprovlHeaderEntity_HI_RO> findSubmitterList = ttaProposalApprovlHeaderServer.findSubmitterList(paramJsonObject);
			String curSubUserName = "";
			StringBuffer sbSub = new StringBuffer();
			//添加表头
			sbSub = headStr(sbSub,"PROPOSAL审批完成");
			list.clear();
			for (int i = 0; i < findSubmitterList.size(); i++) {
				TtaProposalApprovlHeaderEntity_HI_RO tpahSub = findSubmitterList.get(i);

				if( (null !=tpahSub.getCreatedBy()) && (!curSubUserName.equals(tpahSub.getCreatedBy().toString())) ){
					if (!com.sie.saaf.common.util.SaafToolUtils.isNullOrEmpty(curSubUserName)) {
						JSONObject jsonObject= new JSONObject();
						jsonObject.put("LIST",new ArrayList<TtaProposalApprovlHeaderEntity_HI_RO>(list));
						jsonObject.put("WAY","SEND_SUBMIT_PERSON");
						list.clear();
						sbSub = send(sbSub,findSubmitterList.get(i-1),context,jsonObject,ttaProposalApprovlHeaderServer);
						sbSub.setLength(0);
						sbSub = headStr(sbSub,"PROPOSAL审批完成");

					}
					curSubUserName = tpahSub.getCreatedBy().toString();
				}
				list.add(tpahSub);
				sbSub = lineStr(sbSub,tpahSub);

				if ( (i == findSubmitterList.size() - 1) && (!com.sie.saaf.common.util.SaafToolUtils.isNullOrEmpty(curSubUserName)) ) {
					JSONObject jsonObjectEnd= new JSONObject();
					jsonObjectEnd.put("LIST",new ArrayList<TtaProposalApprovlHeaderEntity_HI_RO>(list));
					jsonObjectEnd.put("WAY","SEND_SUBMIT_PERSON");
					list.clear();
					sbSub = send(sbSub,tpahSub,context,jsonObjectEnd,ttaProposalApprovlHeaderServer);
				}
			}
			// 3.审批完成的发送给所有BIC用户
			sbSub.setLength(0);
			sbSub = headStr(sbSub,"PROPOSAL审批完成");
			paramJsonObject.put("way","SEND_BIC");
			findSubmitterList = ttaProposalApprovlHeaderServer.findSubmitterList(paramJsonObject);
			List<TtaProposalApprovlHeaderEntity_HI_RO> findBICList = ttaProposalApprovlHeaderServer.findBICList();
			if (findBICList.size() > 0) {
				List<TtaProposalApprovlHeaderEntity_HI_RO> bicInsertList= new ArrayList<TtaProposalApprovlHeaderEntity_HI_RO>();
				for (int i = 0; i < findSubmitterList.size(); i++) {
					TtaProposalApprovlHeaderEntity_HI_RO tpahSub = findSubmitterList.get(i);
					sbSub = lineStr(sbSub,tpahSub);
					bicInsertList.add(tpahSub);
					if (i == findSubmitterList.size() - 1) {
						JSONObject jsonObject= new JSONObject();
						jsonObject.put("BIC",findBICList.get(0).getReceiver());
						jsonObject.put("LIST",bicInsertList);
						jsonObject.put("WAY","SEND_BIC");
						sbSub = send(sbSub,tpahSub,context,jsonObject,ttaProposalApprovlHeaderServer);
					}
				}
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();

		} catch (Exception e) {
			LOGGER.error("TTA_PROPOSAL审批状态发送数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(MyLogUtils.getArguments(context, "scheduleId")  + ""), MyLogUtils.getErrorMsg(e), "TTA_PROPOSAL审批状态发送失败!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}

	}


	private StringBuffer headStr (StringBuffer sb,String title) {
		sb.append("<div><p>" + title + "</p></div>");
		sb.append("<style type=\"text/css\">\n" +
				".c {\n" +
				"border: solid windowtext 1.0pt;\n" +
				"padding: 0cm 5.4pt 0cm 5.4pt;\n" +
				"height: 30.0pt;\n" +
				"text-align: center;\n" +
				"vertical-align:middle;\n" +
				"}\n" +
				"</style>") ;
		sb.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse\"><tr>   ");
		sb.append("<th class=\"c\"><p>单据号</p></th>  ");
		sb.append("<th class=\"c\"><p>供应商编码</p></th>        \n");
		sb.append("<th class=\"c\"><p>供应商名称</p></th>        \n");
		sb.append("<th class=\"c\"><p>proposal制作年度</p></th>        \n");
		sb.append("<th class=\"c\"><p>销售方式</p></th>        \n");
		sb.append("<th class=\"c\"><p>品牌</p></th>        \n");
		sb.append("<th class=\"c\"><p>制单人</p></th>        \n");
		sb.append("<th class=\"c\"><p>制单人(中文)</p></th>    \n");
		sb.append("</tr> ");

		return  sb ;
	}

	private StringBuffer lineStr (StringBuffer sb, TtaProposalApprovlHeaderEntity_HI_RO tpah) {
		String linkUrl = "http://10.82.31.206/";
		sb.append("<tr>");
		sb.append("<td class=\"c\"><a href =\"" + linkUrl + "\" target=\"_blank\">" + tpah.getOrderNo() + "</a></td>   ");
		sb.append("<td class=\"c\"><p>" + tpah.getVendorNbr() + "</p></td>  ");
		sb.append("<td class=\"c\"><p>" + tpah.getVendorName() + "</p></td>  ");
		sb.append("<td class=\"c\"><p>" + tpah.getProposalYear() + "</p></td>  ");
		sb.append("<td class=\"c\"><p>" + tpah.getSaleTypeName() + "</p></td>  ");
		sb.append("<td class=\"c\"><p>" + tpah.getBrandCn() + "</p></td>  ");
		sb.append("<td class=\"c\"><p>" + tpah.getUserName() + "</p></td>  ");
		sb.append("<td class=\"c\"><p>" + tpah.getUserFullName() + "</p></td>  ");
		sb.append("</tr>");

		return  sb ;
	}

	private StringBuffer send (StringBuffer sb,TtaProposalApprovlHeaderEntity_HI_RO tpah,JobExecutionContext context,JSONObject object,ITtaProposalApprovlHeader ttaProposalApprovlHeaderServer) {
		sb.append("</table>");
		JSONObject instance = new JSONObject();
		instance.put("msgSubject","TTA系统邮件通知");
		instance.put("msgContent",sb.toString());
		if ( (!SaafToolUtils.isNullOrEmpty(object)) && (!SaafToolUtils.isNullOrEmpty(object.getString("BIC")))) {
			instance.put("receiveCode",object.getString("BIC"));
		} else {
			instance.put("receiveCode",tpah.getReceiver());
		}


		JSONObject sourceCfg = new JSONObject();
		String paraConfig = "{\"ServerHost\":\"Wtccn-gz-mailgate.aswgcn.asiapacific.aswgroup.net\",\"sendFrom\":\"WTCCN.service@watsons-china.com.cn\",\"sendName\":\"WTCCN.service\"}";
		//String paraConfig = "{\"ServerHost\":\"smtp.163.com\",\"sendFrom\":\"XXX\",\"sendName\":\"XXX\"}";

		sourceCfg.put("paramCfg",paraConfig);
		sourceCfg.put("sourceUser","WTCCN.service@watsons-china.com.cn");
		sourceCfg.put("sourcePwd","xxx");
		//sourceCfg.put("sourceUser","XXX");
		//sourceCfg.put("sourcePwd","XXX");
		JSONObject result = EmailUtil.sendMail(instance,sourceCfg);
		List<TtaProposalApprovlHeaderEntity_HI_RO> list = (List<TtaProposalApprovlHeaderEntity_HI_RO>)object.get("LIST");
		List<TtaProposalApprovlHeaderEntity_HI> ttaProposalApprovlHeaderEntityList = new ArrayList<TtaProposalApprovlHeaderEntity_HI>();
		String uuid = UUID.randomUUID().toString();
		for (TtaProposalApprovlHeaderEntity_HI_RO s : list) {
			TtaProposalApprovlHeaderEntity_HI tpahNew = new TtaProposalApprovlHeaderEntity_HI();
			tpahNew.setOperatorUserId(1);
			tpahNew.setOnlyCode(s.getOnlyCode());
			tpahNew.setOrderNo(s.getOrderNo());
			tpahNew.setReceiver(instance.getString("receiveCode"));
			tpahNew.setIsSameBatch(uuid);
			tpahNew.setUserCode(SaafToolUtils.isNullOrEmpty(s.getAssignee())?(s.getCreatedBy() == null?"":s.getCreatedBy().toString()):s.getAssignee());
			tpahNew.setOrderKey(s.getBusinessKey());
			tpahNew.setType("TTA_PROPOSAL");
			tpahNew.setWay(object.getString("WAY"));
			tpahNew.setSendWay("EMAIL");
			if("S".equals(result.get("CODE"))) {
				tpahNew.setStatus("Y");
			}else {
				tpahNew.setStatus("F");
				tpahNew.setReason(result.getString("MSG"));
			}
			ttaProposalApprovlHeaderEntityList.add(tpahNew);
		}
		ttaProposalApprovlHeaderServer.saveOrUpdateAll(ttaProposalApprovlHeaderEntityList);
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		if("S".equals(result.get("CODE"))) {
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), listToString(list,';') + ";" + instance.getString("receiveCode"), "RPOPOSAL邮件发送成功", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
		}else {
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), result.getString("MSG"), "RPOPOSAL邮件发送失败", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
		}
		return sb;
	}

	private String listToString(List<TtaProposalApprovlHeaderEntity_HI_RO> list, char separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append("'");
				sb.append(JSON.toJSONString(list.get(i)));
				sb.append("'");
			} else {
				sb.append("'");
				sb.append(JSON.toJSONString(list.get(i)));
				sb.append("'");
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * TTA 业务模块发送邮件 每天下午四点钟发送
	 * @param context
	 * @return
	 */
	@RequestMapping("/ttaBusinessModuleSendMail")
	public String sendEmailByScheduled(JobExecutionContext context) {
		LOGGER.info(".saveSendMail  TTA系统审批邮件发送 调度开始执行!");
		try {
			ITtaProposalApprovlHeader ttaProposalApprovlHeaderServer = (ITtaProposalApprovlHeader) SpringBeanUtil.getBean("ttaProposalApprovlHeaderServer");
			//1.审批中的邮件发送
			//查询审批中的Proposal流程单据
			List<TtaProposalApprovlHeaderEntity_HI_RO> findList = ttaProposalApprovlHeaderServer.findList();
			//查询六大活动的流程单据
			List<TtaProposalApprovlHeaderEntity_HI_RO> findOsd = ttaProposalApprovlHeaderServer.findOSDList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findDM = ttaProposalApprovlHeaderServer.findDmList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findCw = ttaProposalApprovlHeaderServer.findCwList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findNpp = ttaProposalApprovlHeaderServer.findNppList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findHwb = ttaProposalApprovlHeaderServer.findHwbList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findFg = ttaProposalApprovlHeaderServer.findFgList();
			//查询独家协议(标准与非标)的流程单据
			List<TtaProposalApprovlHeaderEntity_HI_RO> findSoleStandar = ttaProposalApprovlHeaderServer.findSoleStandarList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findNonSoleStandar = ttaProposalApprovlHeaderServer.findNonSoleStandarList();
			//查询补充协议(标准与非标)的流程单据
			List<TtaProposalApprovlHeaderEntity_HI_RO> findSupplierSta = ttaProposalApprovlHeaderServer.findStandarSupplementList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findSupplierNonSta = ttaProposalApprovlHeaderServer.findNonStandarSupplementList();
			//查询电子签章的流程单据
			List<TtaProposalApprovlHeaderEntity_HI_RO> findElecContract = ttaProposalApprovlHeaderServer.findElecContractList();

			Map<String,Map<String,Object>> assigneeInfoMap = new HashMap<>();
			getAssigneeInfo("proposal","Proposal",findList,assigneeInfoMap,"A");
			getAssigneeInfo("sixReport","OSD Monthly Checking",findOsd,assigneeInfoMap,"A");
			getAssigneeInfo("sixReport","DM Monthly Checking",findDM,assigneeInfoMap,"A");
			getAssigneeInfo("sixReport","C&W Monthly Checking",findCw,assigneeInfoMap,"A");
			getAssigneeInfo("sixReport","NPP Monthly Checking",findNpp,assigneeInfoMap,"A");
			getAssigneeInfo("sixReport","HWB Awards Checking",findHwb,assigneeInfoMap,"A");
			getAssigneeInfo("sixReport","Free Goods Checking",findFg,assigneeInfoMap,"A");
			getAssigneeInfo("exclusiveStandar","独家协议（标准）",findSoleStandar,assigneeInfoMap,"A");
			getAssigneeInfo("exclusiveNonStandar","独家协议（非标准）",findNonSoleStandar,assigneeInfoMap,"A");
			getAssigneeInfo("supplementStandar","补充协议（标准）",findSupplierSta,assigneeInfoMap,"A");
			getAssigneeInfo("supplementNonStandar","补充协议（非标准）",findSupplierNonSta,assigneeInfoMap,"A");
			getAssigneeInfo("eleContract","电子签章",findElecContract,assigneeInfoMap,"A");

			//对不同办理人的相关业务模块发送邮件
			for (Map.Entry<String, Map<String, Object>> mapEntry : assigneeInfoMap.entrySet()) {
				//String assignee = mapEntry.getKey();
				Map<String, Object> value = mapEntry.getValue();
				String way = (String)value.get("WAY");
				List<TtaProposalApprovlHeaderEntity_HI_RO> list = (List<TtaProposalApprovlHeaderEntity_HI_RO>)value.get("LIST");
				String receive = value.get("receive") + "";
				StringBuffer emailContent = (StringBuffer)value.get("emailContent");//邮件内容
				emailContent.insert(0,tableHeaderStr(new StringBuffer(),"请审批TTA System单据"));//添加头信息
				emailContent.append("</table>");
				//发送邮件
				sendEmail(emailContent,receive,way,list,context,ttaProposalApprovlHeaderServer);
			}

			//2.审批完成的时候的邮件发送(发送给制单人)
			JSONObject paramJsonObject = new JSONObject();
			paramJsonObject.put("way","SEND_SUBMIT_PERSON");
			//proposal
			List<TtaProposalApprovlHeaderEntity_HI_RO> findSubmitterList = ttaProposalApprovlHeaderServer.findSubmitterList(paramJsonObject);
			//六大活动报表
			List<TtaProposalApprovlHeaderEntity_HI_RO> findOsdSubmitterList = ttaProposalApprovlHeaderServer.findOsdSubmitterList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findDMSubmitterList = ttaProposalApprovlHeaderServer.findDMSubmitterList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findCwSubmitterList = ttaProposalApprovlHeaderServer.findCwSubmitterList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findNppSubmitterList = ttaProposalApprovlHeaderServer.findNppSubmitterList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findHwbSubmitterList = ttaProposalApprovlHeaderServer.findHwbSubmitterList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> findFgSubmitterList = ttaProposalApprovlHeaderServer.findFgSubmitterList();
			//独家协议(标准与非标准)
			List<TtaProposalApprovlHeaderEntity_HI_RO> soleStandarSubmitterList = ttaProposalApprovlHeaderServer.findSoleStandarSubmitterList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> soleNonStandarSubmitterList = ttaProposalApprovlHeaderServer.findNonSoleStandarSubmitterList();
			//补充协议(标准与非标准)
			List<TtaProposalApprovlHeaderEntity_HI_RO> supplementSaStandarSubList = ttaProposalApprovlHeaderServer.findSaStandarSubmitterList();
			List<TtaProposalApprovlHeaderEntity_HI_RO> supplementSaNonStandarSubList = ttaProposalApprovlHeaderServer.findSaNonStandarSubmitterList();
			//电子签章
			List<TtaProposalApprovlHeaderEntity_HI_RO> findElecContSubmitterList = ttaProposalApprovlHeaderServer.findElecContSubmitterList();
			Map<String,Map<String,Object>> createrMap = new HashMap<>();
			getAssigneeInfo("proposal","Proposal",findSubmitterList,createrMap,"B");
			getAssigneeInfo("sixReport","OSD Monthly Checking",findOsdSubmitterList,createrMap,"B");
			getAssigneeInfo("sixReport","DM Monthly Checking",findDMSubmitterList,createrMap,"B");
			getAssigneeInfo("sixReport","C&W Monthly Checking",findCwSubmitterList,createrMap,"B");
			getAssigneeInfo("sixReport","NPP Monthly Checking",findNppSubmitterList,createrMap,"B");
			getAssigneeInfo("sixReport","HWB Awards Checking",findHwbSubmitterList,createrMap,"B");
			getAssigneeInfo("sixReport","Free Goods Checking",findFgSubmitterList,createrMap,"B");
			getAssigneeInfo("exclusiveStandar","独家协议（标准）",soleStandarSubmitterList,createrMap,"B");
			getAssigneeInfo("exclusiveNonStandar","独家协议（非标准）",soleNonStandarSubmitterList,createrMap,"B");
			getAssigneeInfo("supplementStandar","补充协议（标准）",supplementSaStandarSubList,createrMap,"B");
			getAssigneeInfo("supplementNonStandar","补充协议（非标准）",supplementSaNonStandarSubList,createrMap,"B");
			getAssigneeInfo("eleContract","电子签章",findElecContSubmitterList,createrMap,"B");
			for (Map.Entry<String, Map<String, Object>> mapEntry : createrMap.entrySet()) {
				//String assignee = mapEntry.getKey();
				Map<String, Object> value = mapEntry.getValue();
				String way = (String)value.get("WAY");
				List<TtaProposalApprovlHeaderEntity_HI_RO> list = (List<TtaProposalApprovlHeaderEntity_HI_RO>)value.get("LIST");
				String receive = value.get("receive") + "";
				StringBuffer emailContent = (StringBuffer)value.get("emailContent");//邮件内容
				emailContent.insert(0,tableHeaderStr(new StringBuffer(),"TTA System单据审批完成"));//添加头信息
				emailContent.append("</table>");
				//发送邮件
				sendEmail(emailContent,receive,way,list,context,ttaProposalApprovlHeaderServer);
				//测试使用
				//sendEmail(emailContent,"melody.li@watsons.com.cn",way,list,context,ttaProposalApprovlHeaderServer);
			}

			// 3.审批完成的发送给所有BIC用户
			//BIC用户
			List<TtaProposalApprovlHeaderEntity_HI_RO> findBICList = ttaProposalApprovlHeaderServer.findBICList();
			if (findBICList != null && findBICList.size() > 0) {
				List<TtaProposalApprovlHeaderEntity_HI_RO> allApproveList = new ArrayList<>();
				addEntity("Proposal",allApproveList,findSubmitterList);
				addEntity("独家协议（标准）",allApproveList,soleStandarSubmitterList);
				addEntity("独家协议（非标准）",allApproveList,soleNonStandarSubmitterList);
				addEntity("补充协议（标准）",allApproveList,supplementSaStandarSubList);
				addEntity("补充协议（非标准）",allApproveList,supplementSaNonStandarSubList);
				addEntity("电子签章",allApproveList,findElecContSubmitterList);
				StringBuffer contentSbf = new StringBuffer();
				for (int i = 0; i < allApproveList.size(); i++) {
					TtaProposalApprovlHeaderEntity_HI_RO entityHiRo = allApproveList.get(i);
					tableLineStr(contentSbf, "", entityHiRo.getOrderType(), entityHiRo);//添加行
					if (i == allApproveList.size() - 1) {
						contentSbf.insert(0,tableHeaderStr(new StringBuffer(),"TTA System单据审批完成"));//插入最前面
						contentSbf.append("</table>");
						//发送邮件
						//sendEmail(contentSbf,findBICList.get(0).getReceiver(),"SEND_BIC",allApproveList,context,ttaProposalApprovlHeaderServer);
						sendEmail(contentSbf,ResourceUtils.getAssignEmailUser(),"SEND_BIC",allApproveList,context,ttaProposalApprovlHeaderServer);
					}
				}
			}

			//4.审批完成的发送给发布人
			Map<String,Map<String,Object>> publishByMap = new HashMap<>();
			getAssigneeInfo("sixReport","OSD Monthly Checking",findOsdSubmitterList,publishByMap,"C");
			getAssigneeInfo("sixReport","DM Monthly Checking",findDMSubmitterList,publishByMap,"C");
			getAssigneeInfo("sixReport","C&W Monthly Checking",findCwSubmitterList,publishByMap,"C");
			getAssigneeInfo("sixReport","NPP Monthly Checking",findNppSubmitterList,publishByMap,"C");
			getAssigneeInfo("sixReport","HWB Awards Checking",findHwbSubmitterList,publishByMap,"C");
			getAssigneeInfo("sixReport","Free Goods Checking",findFgSubmitterList,publishByMap,"C");
			for (Map.Entry<String, Map<String, Object>> mapEntry : publishByMap.entrySet()) {
				Map<String, Object> value = mapEntry.getValue();
				String way = (String)value.get("WAY");
				List<TtaProposalApprovlHeaderEntity_HI_RO> list = (List<TtaProposalApprovlHeaderEntity_HI_RO>)value.get("LIST");
				String receive = value.get("receive") + "";
				StringBuffer emailContent = (StringBuffer)value.get("emailContent");//邮件内容
				emailContent.insert(0,tableHeaderStr(new StringBuffer(),"TTA System单据审批完成"));//添加头信息
				emailContent.append("</table>");
				//发送邮件
				sendEmail(emailContent,receive,way,list,context,ttaProposalApprovlHeaderServer);
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("TTA System单据审批状态发送数据异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(MyLogUtils.getArguments(context, "scheduleId")  + ""), MyLogUtils.getErrorMsg(e), "TTA_PROPOSAL审批状态发送失败!", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
	}

	private void addEntity(String orderType,List<TtaProposalApprovlHeaderEntity_HI_RO> allApproveList,List<TtaProposalApprovlHeaderEntity_HI_RO> list){
		if (list == null || list.isEmpty()){
			return;
		}
		if (SaafToolUtils.isNullOrEmpty(orderType)) return;
		for (TtaProposalApprovlHeaderEntity_HI_RO entityHiRo : list) {
			entityHiRo.setType(orderType);
			entityHiRo.setOrderType(orderType);
			allApproveList.add(entityHiRo);
		}
	}

	private void getAssigneeInfo(String bussinessType, String orderType, List<TtaProposalApprovlHeaderEntity_HI_RO> findList, Map<String, Map<String, Object>> assigneeInfoMap,String synbol) {
		if (findList == null || findList.isEmpty()) {
			return;
		}
		for (TtaProposalApprovlHeaderEntity_HI_RO tpah : findList) {
			tpah.setType(orderType);
			String assignee = "";
			if (A.equals(synbol)) {
				assignee = tpah.getAssignee();
			} else if (!"eleContract".equals(bussinessType) && B.equals(synbol)) {
				assignee = tpah.getUserName();//制单人
				//assignee = "41150794";测试使用
			} else if ("eleContract".equals(bussinessType) && B.equals(synbol)) {//当是电子签章时,取提交人
				assignee = tpah.getCreatedBy().toString();//提交人
			} else if (C.equals(synbol)){//发布人
				assignee = tpah.getIsPublishBy().toString();
			}
			if (assigneeInfoMap.containsKey(assignee)) {
				Map<String, Object> receiverMap = assigneeInfoMap.get(assignee);
				List<TtaProposalApprovlHeaderEntity_HI_RO> list = (List<TtaProposalApprovlHeaderEntity_HI_RO>) receiverMap.get("LIST");
				list.add(tpah);
				StringBuffer emailContent = (StringBuffer) receiverMap.get("emailContent");
				tableLineStr(emailContent, bussinessType, orderType, tpah);
				receiverMap.put("emailContent", emailContent);
			} else {
				List<TtaProposalApprovlHeaderEntity_HI_RO> list = new ArrayList<>();
				Map<String, Object> map = new HashMap<>();
				StringBuffer contentSb = new StringBuffer();
				tableLineStr(contentSb, bussinessType, orderType, tpah);//添加行
				list.add(tpah);
				//发送方式
				if (A.equals(synbol)){
					map.put("WAY", "SEND_APPROVAL");
				} else if (!"eleContract".equals(bussinessType) && B.equals(synbol)){
					map.put("WAY", "SEND_CREATE_PERSON");
				} else if ("eleContract".equals(bussinessType) && B.equals(synbol)) {
					map.put("WAY", "SEND_SUBMIT_PERSON");
				} else if (C.equals(synbol)) {
					map.put("WAY", "SEND_IS_PUBLISH_PERSON");
				}
				map.put("LIST", list);
				if (!C.equals(synbol)) {
					map.put("receive", tpah.getReceiver());//接收人邮箱
				} else {
					map.put("receive", tpah.getIsPublishByEmail());//接收人邮箱
				}
				map.put("emailContent", contentSb);
				assigneeInfoMap.put(assignee, map);
			}
		}
	}


	/**
	 * 表格头
	 * @param sb
	 * @param title
	 * @return
	 */
	private StringBuffer tableHeaderStr(StringBuffer sb,String title) {
		sb.append("<div><p>").append(title).append("</p></div>");
		sb.append("<style type=\"text/css\">\n" +
				".c {\n" +
				"border: solid windowtext 1.0pt;\n" +
				"padding: 0cm 5.4pt 0cm 5.4pt;\n" +
				"height: 30.0pt;\n" +
				"text-align: center;\n" +
				"vertical-align:middle;\n" +
				"}\n" +
				"</style>") ;
		sb.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse\"><tr>\n");
		sb.append("<th class=\"c\"><p>单据类型</p></th>\n");
		sb.append("<th class=\"c\"><p>单据号</p></th>\n");
		sb.append("<th class=\"c\"><p>单据状态</p></th>\n");
		sb.append("<th class=\"c\"><p>促销档期</p></th>\n");
		sb.append("<th class=\"c\"><p>批次号</p></th>\n");
		sb.append("<th class=\"c\"><p>供应商编码</p></th>\n");
		sb.append("<th class=\"c\"><p>供应商名称</p></th>\n");
		sb.append("<th class=\"c\"><p>proposal制作年度</p></th>\n");
		sb.append("<th class=\"c\"><p>销售方式</p></th>\n");
		sb.append("<th class=\"c\"><p>品牌</p></th>\n");
		sb.append("<th class=\"c\"><p>制单人</p></th>\n");
		sb.append("<th class=\"c\"><p>制单人(英文)</p></th>\n");
		sb.append("</tr> ");
		return  sb ;
	}

	private void tableLineStr(StringBuffer sb,String businessType,String orderType ,TtaProposalApprovlHeaderEntity_HI_RO tpah) {
		//String linkUrl = "http://10.82.31.206/";
		String linkUrl = "http://wtccn-watsonsportal:2020/wui/index.html#/?logintype=1&_key=7qlz7s";
		sb.append("<tr>");
		sb.append("<td class=\"c\"><p>").append(orderType).append("</p></td>");//单据类型
		sb.append("<td class=\"c\"><a href =\"").append(linkUrl).append("\" target=\"_blank\">").append(tpah.getOrderNo()).append("</a></td>");//单据编号
		sb.append("<td class=\"c\"><p>").append(tpah.getOrderStatus()).append("</p></td>  ");//单据状态
		sb.append("<td class=\"c\"><p>").append(dealNull(tpah.getPromotionSectionName())).append("</p></td>  ");//促销档期
		sb.append("<td class=\"c\"><p>").append(dealNull(tpah.getBatchCode())).append("</p></td>  ");//批次号
		sb.append("<td class=\"c\"><p>").append(dealNull(tpah.getVendorNbr())).append("</p></td>  ");//供应商编号
		sb.append("<td class=\"c\"><p>").append(dealNull(tpah.getVendorName())).append("</p></td>  ");//供应商名称
		sb.append("<td class=\"c\"><p>").append(dealNull(tpah.getProposalYear())).append("</p></td>  ");//Proposal制作年度
		sb.append("<td class=\"c\"><p>").append(dealNull(tpah.getSaleTypeName())).append("</p></td>  ");//销售方式
		sb.append("<td class=\"c\"><p>").append(dealNull(tpah.getBrandCn())).append("</p></td>  ");//品牌
		sb.append("<td class=\"c\"><p>").append(dealNull(tpah.getUserName())).append("</p></td>  ");//制单人
		sb.append("<td class=\"c\"><p>").append(dealNull(tpah.getUserFullName())).append("</p></td>  ");//制单人英文名
		sb.append("</tr>");
	}

	private String dealNull(String value){
		if (StringUtils.isBlank(value)){
			return "";
		}
		return value;
	}

	//发送邮件
	private void sendEmail(StringBuffer emailContent, String receive,String way,List<TtaProposalApprovlHeaderEntity_HI_RO> approvlList,JobExecutionContext context, ITtaProposalApprovlHeader ttaProposalApprovlHeaderServer) {
		JSONObject instance = new JSONObject();
		instance.put("msgSubject","TTA系统邮件通知");
		instance.put("msgContent",emailContent.toString());
		instance.put("receiveCode",receive);
		/*instance.put("receiveCode","echo.zeng@watsons.com.cn,\n" +
				"eunice.ma@watsons.com.cn,\n" +
				"Jophy.Zou@watsons.com.cn,\n" +
				"melody.li@watsons.com.cn,\n" +
				"tracy.huang@watsons.com.cn,\n" +
				"YanLing.Huang@watsons.com.cn,huang491591@qq.com");*/

		JSONObject sourceCfg = new JSONObject();
		//String paraConfig = "{\"ServerHost\":\"Wtccn-gz-mailgate.aswgcn.asiapacific.aswgroup.net\",\"sendFrom\":\"WTCCN.service@watsons-china.com.cn\",\"sendName\":\"WTCCN.service\"}";
		sourceCfg.put("paramCfg", ResourceUtils.getEmailParaConfig());
		//sourceCfg.put("paramCfg", paraConfig);
		//sourceCfg.put("sourceUser","WTCCN.service@watsons-china.com.cn");
		sourceCfg.put("sourceUser",ResourceUtils.getEmailUser());
		sourceCfg.put("sourcePwd","xxx");

		JSONObject result = EmailUtil.sendMail(instance,sourceCfg);
		List<TtaProposalApprovlHeaderEntity_HI> ttaProposalApprovlHeaderEntityList = new ArrayList<TtaProposalApprovlHeaderEntity_HI>();
		String uuid = UUID.randomUUID().toString();
		for (TtaProposalApprovlHeaderEntity_HI_RO entityHiRo : approvlList) {
			TtaProposalApprovlHeaderEntity_HI tpahNew = new TtaProposalApprovlHeaderEntity_HI();
			tpahNew.setOperatorUserId(1);
			tpahNew.setOnlyCode(entityHiRo.getOnlyCode());
			tpahNew.setOrderNo(entityHiRo.getOrderNo());
			tpahNew.setReceiver(instance.getString("receiveCode"));
			tpahNew.setIsSameBatch(uuid);
			tpahNew.setUserCode(SaafToolUtils.isNullOrEmpty(entityHiRo.getAssignee()) ? (entityHiRo.getCreatedBy() == null ? "" : entityHiRo.getCreatedBy().toString()) : entityHiRo.getAssignee());
			tpahNew.setOrderKey(entityHiRo.getBusinessKey());
			tpahNew.setType(entityHiRo.getType());//"TTA_PROPOSAL"
			tpahNew.setWay(way);
			tpahNew.setSendWay("EMAIL");
			if("S".equals(result.get("CODE"))) {
				tpahNew.setStatus("Y");
			}else {
				tpahNew.setStatus("F");
				tpahNew.setReason(result.getString("MSG"));
			}
			ttaProposalApprovlHeaderEntityList.add(tpahNew);
		}
		ttaProposalApprovlHeaderServer.saveOrUpdateAll(ttaProposalApprovlHeaderEntityList);
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		if("S".equals(result.get("CODE"))) {
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), listToString(approvlList,';') + ";" + instance.getString("receiveCode"), "TTA邮件发送成功", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
		}else {
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), result.getString("MSG"), "TTA邮件发送失败", null, MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
		}
	}


}