package com.sie.watsons.base.product.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.watsons.base.api.ConfigModel;
import com.sie.watsons.base.api.model.inter.IPlmApi;
import com.sie.watsons.base.plmBase.model.inter.server.PlmBrandInfoServer;
import com.sie.watsons.base.plmBase.model.inter.server.PlmBrandMapServer;
import com.sie.watsons.base.product.model.inter.IPlmUdaAttribute;
import com.sie.watsons.base.productEco.model.entities.PlmProductHeadEcoEntity_HI;
import com.sie.watsons.base.productEco.model.inter.IPlmProductHeadEco;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisCluster;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.rmi.ServerException;
import java.util.*;



@RestController
@RequestMapping("/WatonsBpmService")
public class WatonsBpmService extends CommonAbstractService {
	// @Autowired
	// private IWatsonsFlowList watsonsFlowListServer;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private IPlmProductHeadEco plmProductHeadEcoServer;
	@Autowired
	private ConfigModel configModel;
	@Lazy
	@Autowired
	private IPlmApi iPlmApiServer;
	@Autowired
	private PlmBrandMapServer plmBrandMapServer;
	@Autowired
	private PlmBrandInfoServer plmBrandInfoServer;
	@Autowired
	private IPlmUdaAttribute udaAttributeServer;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WatonsBpmService.class);


	// public static String Url =
	// "http://10.82.25.54/WTC_BPM/API/PostMsg.ashx";
	//public static String Url = "http://10.82.97.161/WTC_BPM/API/PostMsg.ashx";

	public   String ExecuteBpm(JSONObject params) throws ServerException {
	  LOGGER.info("WatonsBpmService/ExecuteBpm params:{}",params);
		try {
			// HttpClient httpClient = new DefaultHttpClient();
			// HttpPost post = new HttpPost(Url);
			// StringEntity postingString = new
			// StringEntity(params.toString());// 参数传递
			// post.setEntity(postingString);
			// post.setHeader("Content-type", "application/json");
			// HttpResponse response = httpClient.execute(post);
			// String content = EntityUtils.toString(response.getEntity());
			String url=configModel.getBpmUrl();
			Object j = ResultUtils.doPost1(url, params);
			return j.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ServerException(e.getMessage());
		}
	}

	public WatonsBpmService() {
		super();
	}

	// 供应商资质组修改流程
	@RequestMapping(method = RequestMethod.POST, value = "SupplierQaUpdateStartBpm")
	public String SupplierQaUpdateStartBpm(@RequestParam String params)
			throws Exception {
		// jedisCluster.hmset(key, hash)
		JSONObject jsonParam = JSON.parseObject(params);
		String userName = this.getUserSessionBean().getUserName();
		if (this.getUserSessionBean().getUserType().equals("60")) {
			userName = this.getUserSessionBean().getNamePingyin();
		}
		String action = jsonParam.getString("action");
		String billNo = jsonParam.getString("billNo");
		String no = billNo.replace("ZZ", "");
//		try {
//
//
//			JSONObject jq = new JSONObject();
//			jq.put("supplierCode", no);
//			JSONObject re = this.getProductSupplier(jq.toJSONString());
//			Integer cc = re.getInteger("data");
//			if (cc > 0) {
//				throw new Exception("该供应商的资质组有在新增商品过程中使用到,不允许发起修改流程!");
//			}
//		}catch (Exception e)
//		{
//			return getException(e, LOGGER);
//		}

		JSONObject param = new JSONObject(true);
		param.put("Method", "SubmitIncident");
		param.put("TASKUSER", userName);
		// param.put("TASKUSER", taskUser);
		param.put("PROCESSNAME", "供应商资质管理修改流程");
		param.put("SUMMARY", billNo);
		param.put("ACTION", action);
		if (!jsonParam.containsKey("COMMENTS")) {
			param.put("COMMENTS", "");
		} else {
			param.put("COMMENTS", jsonParam.getString("COMMENTS"));
		}

		if (action.equals("Submit")) {
			param.put("VERSION", "0");
			param.put("INCIDENT", "0");
			param.put("TASKID", "");
			JSONArray userlist = new JSONArray();
			List<String> user7 = new ArrayList<String>();

			String u1 = jsonParam.getString("taskUser");// 采购
			user7.add(u1);
			JSONObject data7 = new JSONObject(true);
			data7.put("STEPNAME", "采购");
			data7.put("APPROVER", user7);
			userlist.add(data7);
			param.put("APPROVERLIST", userlist);

		} else if (action.equals("Approval")) {

			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}

			String incident = jsonParam.getString("INCIDENT");

			if (!this.getUserSessionBean().getUserType().equals("60")) {
				String userInfo = findWastonHistoricBycurentUser("供应商资质管理修改流程",
						incident, "grantTo");
				String ue = userInfo.split("&&&")[1];
				if (!ue.equals(this.getUserSessionBean().getUserName())) {
					param.remove("TASKUSER");
					param.put("TASKUSER", ue);
				}
			}

			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);
			if (STEPLABEL.equals("采购")) {
				// 获取qa
				JSONObject ja = new JSONObject();
				ja.put("qaGroupCode", billNo);
				String result = this.getSupplierQa(ja.toJSONString());
				JSONObject jsonresult = parseObject(result);
				JSONObject data = jsonresult.getJSONObject("data");
				JSONObject headinfo = data.getJSONObject("headInfo");
				String qauser = headinfo.getString("qaApprovalRole");
				List<String> ulist = new ArrayList<String>();
				ulist.add(qauser);
				JSONArray userlist = new JSONArray();
				JSONObject data2 = new JSONObject(true);
				data2.put("STEPNAME", "QA");
				data2.put("APPROVER", ulist);
				userlist.add(data2);
				param.put("APPROVERLIST", userlist);

				// 将状态变成qa待审
				JSONObject qaobj = new JSONObject(true);
				qaobj.put("status", "UPDATE_QAAPROV");
				qaobj.put("billNo", billNo);
				qaobj.put("INCIDENT", incident);
				qaobj.put("VERSION", version);
				qaobj.put("TASKID", taskid);
				qaobj.put("TASKUSER", userName);
				this.updaterSupplierQaStatus(qaobj.toJSONString());

			} else if (STEPLABEL.equals("提交")) // 重新提交
			{
				// 根据单号 获取待办 第一条信息
				Map<String, String> taskinfo = this.getTaskInfoByUser(billNo,
						"1", "供应商资质管理修改流程", this.getUserSessionBean()
								.getNamePingyin());
				String Tid = taskinfo.get("TASKID");
				param.remove("TASKID");
				param.put("TASKID", Tid);

				JSONObject qaobj = new JSONObject(true);
				qaobj.put("status", "UPDATE_APROV");
				qaobj.put("billNo", billNo);
				qaobj.put("TASKID", param.getString("TASKID"));
				qaobj.put("INCIDENT", param.getString("INCIDENT"));
				qaobj.put("VERSION", param.getString("VERSION"));
				qaobj.put("TASKUSER", userName);
				this.updaterSupplierQaStatus(qaobj.toJSONString());

				JSONArray userlist = new JSONArray();
				List<String> user7 = new ArrayList<String>();
				String u1 = jsonParam.getString("taskUser");// 采购
				user7.add(u1);
				JSONObject data7 = new JSONObject(true);
				data7.put("STEPNAME", "采购");
				data7.put("APPROVER", user7);
				userlist.add(data7);
				param.put("APPROVERLIST", userlist);
			}

		} else if (action.equals("Return") || action.equals("Reject")) {
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);
		}
		String result = this.ExecuteBpm(param);
		JSONObject data = JSON.parseObject(result);
		String messageresult = data.getString("MESSAGE");
		if (data.containsKey("SUCCESSCODE")) {
			if (action.equals("Submit")) {
				// 将状态变成采购待审

				Map<String, String> taskInfo = getTaskInfoByUser(billNo, "3",
						"供应商资质管理修改流程", this.getUserSessionBean()
								.getNamePingyin());

				JSONObject qaobj = new JSONObject(true);
				qaobj.put("status", "UPDATE_APROV");
				qaobj.put("billNo", billNo);
				if (taskInfo.containsKey("TASKID")) {
					qaobj.put("TASKID", taskInfo.get("TASKID"));
					qaobj.put("INCIDENT", taskInfo.get("INCIDENT"));
				} else {
					qaobj.put("INCIDENT", "");
					qaobj.put("TASKID", "");
				}
				qaobj.put("VERSION", "");

				qaobj.put("TASKUSER", userName);
				this.updaterSupplierQaStatus(qaobj.toJSONString());
			} else if (action.equals("Reject")) {
				JSONObject gb = new JSONObject(true);
				gb.put("status", "UPDATE_REFU"); // 拒绝
				gb.put("billNo", billNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));

				this.updaterSupplierQaStatus(gb.toJSONString());
			}
			String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
			if (PROCESS_COMPLETED.equals("1")) // 审批完成
			{
				JSONObject gb = new JSONObject(true);
				gb.put("status", "APPROVAL"); // 已生效
				gb.put("billNo", billNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));

				this.updaterSupplierQaStatus(gb.toJSONString());
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}
	}

	// 供应商资质新增流程
	@RequestMapping(method = RequestMethod.POST, value = "SupplierQaStartBpm")
	public String SupplierQaStartBpm(@RequestParam String params) throws ServerException {
		JSONObject jsonParam = JSONObject.parseObject(params);
		String userName = this.getUserSessionBean().getUserName();
		if (this.getUserSessionBean().getUserType().equals("60")) {
			userName = this.getUserSessionBean().getNamePingyin();
		}

		String action = jsonParam.getString("action");
		String billNo = jsonParam.getString("billNo");
		JSONObject param = new JSONObject(true);
		param.put("Method", "SubmitIncident");
		param.put("TASKUSER", userName);
		// param.put("TASKUSER", taskUser);
		param.put("PROCESSNAME", "供应商资质管理流程");
		param.put("SUMMARY", billNo);
		param.put("ACTION", action);
		if (!jsonParam.containsKey("COMMENTS")) {
			param.put("COMMENTS", "");
		} else {
			param.put("COMMENTS", jsonParam.getString("COMMENTS"));
		}

		if (action.equals("Submit")) {
			param.put("VERSION", "0");
			param.put("INCIDENT", "0");
			param.put("TASKID", "");
			JSONArray userlist = new JSONArray();
			List<String> user7 = new ArrayList<String>();

			String u1 = jsonParam.getString("taskUser");// 采购
			user7.add(u1);
			JSONObject data7 = new JSONObject(true);
			data7.put("STEPNAME", "采购");
			data7.put("APPROVER", user7);
			userlist.add(data7);
			param.put("APPROVERLIST", userlist);

		} else if (action.equals("Approval")) {

			if (!jsonParam.containsKey("STEPLABEL")) {
				return "缺少流程参数 STEPLABEL!";
			}
			if (!jsonParam.containsKey("INCIDENT")) {
        return "缺少流程参数 INCIDENT!";
			}
			if (!jsonParam.containsKey("VERSION")) {
        return "缺少流程参数 VERSION!";
			}
			if (!jsonParam.containsKey("TASKID")) {
        return "缺少流程参数 TASKID!";
			}

			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);

			if (!this.getUserSessionBean().getUserType().equals("60")) {
				String userInfo = findWastonHistoricBycurentUser("供应商资质管理流程",
						incident, "grantTo");
				String ue = userInfo.split("&&&")[1];
				if (!ue.equals(this.getUserSessionBean().getUserName())) {
					param.remove("TASKUSER");
					param.put("TASKUSER", ue);
				}
			}

			if (STEPLABEL.equals("采购")) {
				// 获取qa
				JSONObject ja = new JSONObject();
				ja.put("qaGroupCode", billNo);
				String result = this.getSupplierQa(ja.toJSONString());
				JSONObject jsonresult = parseObject(result);
				JSONObject data = jsonresult.getJSONObject("data");
				JSONObject headinfo = data.getJSONObject("headInfo");
				String qauser = headinfo.getString("qaApprovalRole");
				List<String> ulist = new ArrayList<String>();
				ulist.add(qauser);
				JSONArray userlist = new JSONArray();
				JSONObject data2 = new JSONObject(true);
				data2.put("STEPNAME", "QA");
				data2.put("APPROVER", ulist);
				userlist.add(data2);
				param.put("APPROVERLIST", userlist);

				// 将状态变成qa待审
				JSONObject qaobj = new JSONObject(true);
				qaobj.put("status", "QA_TODO");
				qaobj.put("billNo", billNo);
				qaobj.put("INCIDENT", incident);
				qaobj.put("VERSION", version);
				qaobj.put("TASKID", taskid);
				qaobj.put("TASKUSER", userName);
				this.updaterSupplierQaStatus(qaobj.toJSONString());

			} else if (STEPLABEL.equals("提交")) // 重新提交
			{
				// 根据单号 获取待办 第一条信息
				Map<String, String> taskinfo = this.getTaskInfoByUser(billNo,
						"1", "供应商资质管理流程", this.getUserSessionBean()
								.getNamePingyin());
				String Tid = taskinfo.get("TASKID");
				param.remove("TASKID");
				param.put("TASKID", Tid);

				JSONObject qaobj = new JSONObject(true);
				qaobj.put("status", "PURCHASE_TODO");
				qaobj.put("billNo", billNo);
				qaobj.put("TASKID", param.getString("TASKID"));
				qaobj.put("INCIDENT", param.getString("INCIDENT"));
				qaobj.put("VERSION", param.getString("VERSION"));
				qaobj.put("TASKUSER", userName);
				LOGGER.warn("++++++++++++++供应商资质管理流程重新提交"+qaobj.toJSONString());
				this.updaterSupplierQaStatus(qaobj.toJSONString());

				JSONArray userlist = new JSONArray();
				List<String> user7 = new ArrayList<String>();
				String u1 = jsonParam.getString("taskUser");// 采购
				user7.add(u1);
				JSONObject data7 = new JSONObject(true);
				data7.put("STEPNAME", "采购");
				data7.put("APPROVER", user7);
				userlist.add(data7);
				param.put("APPROVERLIST", userlist);

			}

		} else if (action.equals("Return") || action.equals("Reject")) {
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);
		}
		LOGGER.info("SupplierQaStartBpm/param :{}",param);
		String result = this.ExecuteBpm(param);
    LOGGER.info("SupplierQaStartBpm/return :{}",result);
		JSONObject data = JSON.parseObject(result);
		// String messagecode = data.getString("SUCCESSCODE");
		String messageresult = data.getString("MESSAGE");
		if (data.containsKey("SUCCESSCODE")) {
			if (action.equals("Submit")) {
				// 将状态变成采购待审

				Map<String, String> taskInfo = getTaskInfoByUser(billNo, "3",
						"供应商资质管理流程", this.getUserSessionBean().getNamePingyin());

				JSONObject qaobj = new JSONObject(true);
				qaobj.put("status", "PURCHASE_TODO");
				qaobj.put("billNo", billNo);
				if (taskInfo.containsKey("TASKID")) {
					qaobj.put("TASKID", taskInfo.get("TASKID"));
					qaobj.put("INCIDENT", taskInfo.get("INCIDENT"));
				} else {
					qaobj.put("INCIDENT", "");
					qaobj.put("TASKID", "");
				}
				qaobj.put("VERSION", "");

				qaobj.put("TASKUSER", userName);
				this.updaterSupplierQaStatus(qaobj.toJSONString());
			} else if (action.equals("Reject")) {
				JSONObject gb = new JSONObject(true);
				gb.put("status", "REFU"); // 拒绝
				gb.put("billNo", billNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));

				this.updaterSupplierQaStatus(gb.toJSONString());
			}
			String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
			if (PROCESS_COMPLETED.equals("1")) // 审批完成
			{
				JSONObject gb = new JSONObject(true);
				gb.put("status", "APPROVAL"); // 已生效
				gb.put("billNo", billNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));

				this.updaterSupplierQaStatus(gb.toJSONString());
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}
	}

	// 品牌流程
	@RequestMapping(method = RequestMethod.POST, value = "brandStartBpm")
	public String brandStartBpm(@RequestParam String params)
			throws ServerException {
		JSONObject jsonParam = JSON.parseObject(params);
		String userName = this.getUserSessionBean().getUserName();
		String action = jsonParam.getString("action");
		String billNo = jsonParam.getString("billNo");
		String orgiNo = billNo.split("-")[0];

		JSONObject param = new JSONObject(true);
		param.put("Method", "SubmitIncident");
		param.put("TASKUSER", userName);
		param.put("PROCESSNAME", "品牌流程");
		param.put("SUMMARY", billNo);
		param.put("ACTION", action);
		if (!jsonParam.containsKey("COMMENTS")) {
			param.put("COMMENTS", "");
		} else {
			param.put("COMMENTS", jsonParam.getString("COMMENTS"));
		}

		if (action.equals("Submit")) {
			param.put("VERSION", "0");
			param.put("INCIDENT", "0");
			param.put("TASKID", "");
			JSONArray userlist = new JSONArray();
//			List<String> user7 =this.findBpmNodeByname("BIC");
//			JSONObject data7 = new JSONObject(true);
//			data7.put("STEPNAME", "BIC");
//			data7.put("APPROVER", user7);

			List<String> user8 = this.findBpmNodeByname("GROUPTA");

			JSONObject data8 = new JSONObject(true);
			data8.put("STEPNAME", "TA");
			data8.put("APPROVER", user8);

			if (!jsonParam.containsKey("variables")) {
				throw new ServerException("缺少流程参数!");
			}
			JSONArray varry = jsonParam.getJSONArray("variables");
			for (int i = 0; i < varry.size(); i++) {
				JSONObject jt = varry.getJSONObject(i);
				String type = jt.getString("type");
				if (type.equals("string")) {
					String name = jt.getString("name");
					if (name.equals("containMotherCompany")) {
						String value = jt.getString("value");
						if (value.equals("Y")) {
							JSONArray paramlist = new JSONArray();
							JSONObject data = new JSONObject(true);
							data.put("PARMNAME", "containMotherCompany");
							data.put("VALUE", "Y");

							paramlist.add(data);
							param.put("PARMLIST", paramlist);

							List<String> user9 = this.findBpmNodeByname("BA");
							JSONObject data9 = new JSONObject(true);
							data9.put("STEPNAME", "BA");
							data9.put("APPROVER", user9);
							userlist.add(data9);
						}
					}
				}
			}
			//userlist.add(data7);
			userlist.add(data8);
			param.put("APPROVERLIST", userlist);

		} else if (action.equals("Approval") || action.equals("Return")
				|| action.equals("Reject")) {
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);

			String ue = this.getUserSessionBean().getUserName();
			if (action.equals("Approval")) {
				String unameinfo = this.findWastonHistoricBycurentUser("品牌流程",
						incident, "grantTo"); //
				String uname = unameinfo.split("&&&")[1];// 被委托人

				if (!uname.equals(ue)) {
					ue = uname;
					param.remove("TASKUSER");
					param.put("TASKUSER", uname);
				}
			}

			if (STEPLABEL.equals("提交")) // 重新提交
			{

				// 根据单号 获取待办 第一条信息
				Map<String, String> taskinfo = this.getTaskInfoByUser(billNo,
						"1", "品牌流程", ue);
				String Tid = taskinfo.get("TASKID");
				param.remove("TASKID");
				param.put("TASKID", Tid);

				JSONArray userlist = new JSONArray();
				JSONArray varry = jsonParam.getJSONArray("variables");
				for (int i = 0; i < varry.size(); i++) {
					JSONObject jt = varry.getJSONObject(i);
					String type = jt.getString("type");
					if (type.equals("string")) {
						String name = jt.getString("name");
						if (name.equals("containMotherCompany")) {
							String value = jt.getString("value");
							if (value.equals("Y")) {
								JSONArray paramlist = new JSONArray();
								JSONObject data = new JSONObject(true);
								data.put("PARMNAME", "containMotherCompany");
								data.put("VALUE", "Y");

								paramlist.add(data);
								param.put("PARMLIST", paramlist);

								List<String> user9 = this.findBpmNodeByname("BA");
								JSONObject data9 = new JSONObject(true);
								data9.put("STEPNAME", "BA");
								data9.put("APPROVER", user9);
								userlist.add(data9);
								param.put("APPROVERLIST", userlist);
							}else{
								JSONArray paramlist = new JSONArray();
								JSONObject data = new JSONObject(true);
								data.put("PARMNAME", "containMotherCompany");
								data.put("VALUE", "N");

								paramlist.add(data);
								param.put("PARMLIST", paramlist);
							}
						}
					}
				}




			}

		}
		String result = this.ExecuteBpm(param);
		JSONObject data = JSON.parseObject(result);

		String messageresult = data.getString("MESSAGE");
		if (data.containsKey("SUCCESSCODE")) {
			if (action.equals("Submit")||jsonParam.getString("STEPLABEL").equals("提交")) { // 变成审批中
				Map<String, String> taskInfo = getTaskInfoByUser(billNo, "3",
						"品牌流程", this.getUserSessionBean().getUserName());

				JSONObject gb = new JSONObject(true);
				gb.put("status", "APPROVING");
				gb.put("processname", "brandinfo");
				gb.put("billNo", orgiNo);
				if (taskInfo.containsKey("TASKID")) {
					gb.put("INCIDENT", taskInfo.get("INCIDENT"));
					gb.put("TASKID", taskInfo.get("TASKID"));
				} else {
					gb.put("TASKID", "");
					gb.put("INCIDENT", "");
				}
				gb.put("VERSION", "");

				gb.put("TASKUSER", param.getString("TASKUSER"));
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			} else if (action.equals("Reject")) { // 拒绝
				JSONObject gb = new JSONObject(true);
				gb.put("status", "REJECT");
				gb.put("processname", "brandinfo");
				gb.put("billNo", orgiNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			}

			if (data.containsKey("PROCESS_COMPLETED")) {

				String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
				if (PROCESS_COMPLETED.equals("1")) // 审批完成
				{
					JSONObject gb = new JSONObject(true);
					gb.put("status", "Y"); // 已生效
					gb.put("processname", "brandinfo");
					gb.put("billNo", orgiNo);
					gb.put("INCIDENT", param.getString("INCIDENT"));
					gb.put("TASKID", param.getString("TASKID"));
					gb.put("VERSION", param.getString("VERSION"));
					gb.put("TASKUSER", param.getString("TASKUSER"));
					gb.put("allNo", billNo);
					this.updaterGroupStatus(gb.toJSONString());
				}
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			if (action.equals("Submit")) {
				JSONObject gb = new JSONObject(true);
				gb.put("status", "TODO"); // 制单中
				gb.put("processname", "brandinfo");
				gb.put("billNo", orgiNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			}
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}
	}

	// MotherComany审批流程
	@RequestMapping(method = RequestMethod.POST, value = "MotherComanyStartBpm")
	public String MotherComanyStartBpm(@RequestParam String params)
			throws ServerException {
		JSONObject jsonParam = JSON.parseObject(params);
		String userName = this.getUserSessionBean().getUserName();
		String action = jsonParam.getString("action");
		String billNo = jsonParam.getString("billNo");
		String orgiNo = billNo.split("-")[0];
		JSONObject param = new JSONObject(true);
		param.put("Method", "SubmitIncident");
		param.put("TASKUSER", userName);
		param.put("PROCESSNAME", "Mother Comany流程");
		param.put("SUMMARY", billNo);
		param.put("ACTION", action);
		if (!jsonParam.containsKey("COMMENTS")) {
			param.put("COMMENTS", "");
		} else {
			param.put("COMMENTS", jsonParam.getString("COMMENTS"));
		}

		if (action.equals("Submit")) {
			param.put("VERSION", "0");
			param.put("INCIDENT", "0");
			param.put("TASKID", "");
			JSONArray userlist = new JSONArray();
			List<String> user7 = this.findBpmNodeByname("BA");
			JSONObject data7 = new JSONObject(true);
			data7.put("STEPNAME", "BA");
			data7.put("APPROVER", user7);

			List<String> user8 = this.findBpmNodeByname("GROUPTA");

			JSONObject data8 = new JSONObject(true);
			data8.put("STEPNAME", "TA");
			data8.put("APPROVER", user8);

			userlist.add(data7);
			userlist.add(data8);
			param.put("APPROVERLIST", userlist);

		} else if (action.equals("Approval") || action.equals("Return")
				|| action.equals("Reject")) {
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);

			String ue = this.getUserSessionBean().getUserName();
			if (action.equals("Approval")) {
				String unameinfo = this.findWastonHistoricBycurentUser(
						"Mother Comany流程", incident, "grantTo"); //
				String uname = unameinfo.split("&&&")[1];// 被委托人

				if (!uname.equals(ue)) {
					ue = uname;
					param.remove("TASKUSER");
					param.put("TASKUSER", uname);
				}
			}

			if (STEPLABEL.equals("提交")) // 重新提交
			{
				// 根据单号 获取待办 第一条信息
				Map<String, String> taskinfo = this.getTaskInfoByUser(billNo,
						"1", "Mother Comany流程", ue);
				String Tid = taskinfo.get("TASKID");
				param.remove("TASKID");
				param.put("TASKID", Tid);
			}

		}
		String result = this.ExecuteBpm(param);
		JSONObject data = JSON.parseObject(result);
		// String messagecode = data.getString("SUCCESSCODE");
		String messageresult = data.getString("MESSAGE");
		if (data.containsKey("SUCCESSCODE")) {
			if (action.equals("Submit")||jsonParam.getString("STEPLABEL").equals("提交")) { // 变成已审批

				Map<String, String> taskInfo = getTaskInfoByUser(billNo, "3",
						"Mother Comany流程", this.getUserSessionBean()
								.getUserName());

				JSONObject gb = new JSONObject(true);
				gb.put("status", "APPROVING");
				gb.put("processname", "mother");
				gb.put("billNo", orgiNo);
				if (taskInfo.containsKey("TASKID")) {
					gb.put("INCIDENT", taskInfo.get("INCIDENT"));
					gb.put("TASKID", taskInfo.get("TASKID"));
				} else {
					gb.put("TASKID", "");
					gb.put("INCIDENT", "");
				}
				gb.put("VERSION", "");

				gb.put("TASKUSER", param.getString("TASKUSER"));
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			}  else if (action.equals("Reject")) { // 拒绝
				JSONObject gb = new JSONObject(true);
				gb.put("status", "REJECT");
				gb.put("processname", "mother");
				gb.put("billNo", orgiNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));
				gb.put("allNo", billNo);

				this.updaterGroupStatus(gb.toJSONString());
			}

			if (data.containsKey("PROCESS_COMPLETED")) {

				String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
				if (PROCESS_COMPLETED.equals("1")) // 审批完成
				{
					JSONObject gb = new JSONObject(true);
					gb.put("status", "RMSCONFIRM"); // rms确认
					gb.put("processname", "mother");
					gb.put("billNo", orgiNo);
					gb.put("INCIDENT", param.getString("INCIDENT"));
					gb.put("TASKID", param.getString("TASKID"));
					gb.put("VERSION", param.getString("VERSION"));
					gb.put("TASKUSER", param.getString("TASKUSER"));
					gb.put("allNo", billNo);

					this.updaterGroupStatus(gb.toJSONString());
				}
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			if (action.equals("Submit")||jsonParam.getString("STEPLABEL").equals("提交")) {
				JSONObject gb = new JSONObject(true);
				gb.put("status", "TODO"); // rms确认
				gb.put("processname", "mother");
				gb.put("billNo", orgiNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			}
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}
	}

	// Group Brand流程
	@RequestMapping(method = RequestMethod.POST, value = "GroupBrandStartBpm")
	public String GroupBrandStartBpm(@RequestParam String params)
			throws ServerException {
		JSONObject jsonParam = JSON.parseObject(params);
		String userName = this.getUserSessionBean().getUserName();
		String action = jsonParam.getString("action");
		String billNo = jsonParam.getString("billNo");
		String orgiNo = billNo.split("-")[0];
		JSONObject param = new JSONObject(true);
		param.put("Method", "SubmitIncident");
		param.put("TASKUSER", userName);
		param.put("PROCESSNAME", "Group Brand流程");
		param.put("SUMMARY", billNo);
		param.put("ACTION", action);
		if (!jsonParam.containsKey("COMMENTS")) {
			param.put("COMMENTS", "");
		} else {
			param.put("COMMENTS", jsonParam.getString("COMMENTS"));
		}
		if (action.equals("Submit")) {
			param.put("VERSION", "0");
			param.put("INCIDENT", "0");
			param.put("TASKID", "");
			JSONArray userlist = new JSONArray();
			List<String> user7 = this.findBpmNodeByname("GROUPTA");
			JSONObject data7 = new JSONObject(true);
			data7.put("STEPNAME", "TA");
			data7.put("APPROVER", user7);
			userlist.add(data7);
			param.put("APPROVERLIST", userlist);

		} else if (action.equals("Approval") || action.equals("Return")
				|| action.equals("Reject")) {
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);

			String ue = this.getUserSessionBean().getUserName();
			if (action.equals("Approval")) {
				String unameinfo = this.findWastonHistoricBycurentUser(
						"Group Brand流程", incident, "grantTo"); //
				String uname = unameinfo.split("&&&")[1];// 被委托人

				if (!uname.equals(ue)) {
					ue = uname;
					param.remove("TASKUSER");
					param.put("TASKUSER", uname);
				}
			}

			if (STEPLABEL.equals("提交")) // 重新提交
			{

				// 根据单号 获取待办 第一条信息
				Map<String, String> taskinfo = this.getTaskInfoByUser(billNo,
						"1", "Group Brand流程", ue);
				String Tid = taskinfo.get("TASKID");
				param.remove("TASKID");
				param.put("TASKID", Tid);
			}

		}
		String result = this.ExecuteBpm(param);
		JSONObject data = JSON.parseObject(result);

		String messageresult = data.getString("MESSAGE");
		if (data.containsKey("SUCCESSCODE")) {

			if (action.equals("Submit")||jsonParam.getString("STEPLABEL").equals("提交")) { // 变成已审批
				Map<String, String> taskInfo = getTaskInfoByUser(billNo, "3",
						"Group Brand流程", this.getUserSessionBean()
								.getUserName());

				JSONObject gb = new JSONObject(true);
				gb.put("status", "APPROVING");
				gb.put("processname", "groupbrand");
				gb.put("billNo", orgiNo);
				if (taskInfo.containsKey("TASKID")) {
					gb.put("INCIDENT", taskInfo.get("INCIDENT"));
					gb.put("TASKID", taskInfo.get("TASKID"));
				} else {
					gb.put("TASKID", "");
					gb.put("INCIDENT", "");
				}
				gb.put("VERSION", "");

				gb.put("TASKUSER", param.getString("TASKUSER"));
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			} else if (action.equals("Reject")) { // 拒绝
				JSONObject gb = new JSONObject(true);
				gb.put("status", "REJECT");
				gb.put("processname", "groupbrand");
				gb.put("billNo", orgiNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			}

			if (data.containsKey("PROCESS_COMPLETED")) {

				String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
				if (PROCESS_COMPLETED.equals("1")) // 审批完成
				{
					JSONObject gb = new JSONObject(true);
					gb.put("status", "Y"); // 已生效
					gb.put("processname", "groupbrand");
					gb.put("billNo", orgiNo);
					gb.put("INCIDENT", param.getString("INCIDENT"));
					gb.put("TASKID", param.getString("TASKID"));
					gb.put("VERSION", param.getString("VERSION"));
					gb.put("TASKUSER", param.getString("TASKUSER"));
					gb.put("allNo", billNo);
					this.updaterGroupStatus(gb.toJSONString());
				}
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			if (action.equals("Submit")) {
				JSONObject gb = new JSONObject(true);
				gb.put("status", "TODO"); // 已生效
				gb.put("processname", "groupbrand");
				gb.put("billNo", orgiNo);
				gb.put("INCIDENT", param.getString("INCIDENT"));
				gb.put("TASKID", param.getString("TASKID"));
				gb.put("VERSION", param.getString("VERSION"));
				gb.put("TASKUSER", param.getString("TASKUSER"));
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			}
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}
	}

	public JSONObject InitSupSet(JSONObject param, JSONObject jsonParam)
			throws ServerException {
		String dept = "";
		JSONArray paramlist = new JSONArray();
		if (!jsonParam.containsKey("variables")) {
			throw new ServerException("缺少流程参数!");
		}
		JSONArray varry = jsonParam.getJSONArray("variables");
		String billno=jsonParam.getString("billNo");
		String orgiNo=billno.split("-")[0];
		for (int i = 0; i < varry.size(); i++) {
			JSONObject jt = varry.getJSONObject(i);
			String type = jt.getString("type");
			if (type.equals("string")) {

				String name = jt.getString("name");
				if (name.equals("dept")) {
					JSONObject initparam = new JSONObject(true);
					dept = jt.getString("value");
					initparam.put("PARMNAME", "Dept");
					initparam.put("VALUE", dept);
					paramlist.add(initparam);
				}
			}
		}
		if (paramlist.size() > 0) {
			param.put("PARMLIST", paramlist);
		}

		JSONArray userlist = new JSONArray();
		// 需要supply审批
		if (dept.equals("MySelf")) { // 走汇报线
			JSONObject userstr = new JSONObject(true);
			userstr.put("userId", this.getUserSessionBean().getUserId());
			List<String> userhuibao = this.findUserByNextPerson(
					userstr.toJSONString(), ""); // 查找汇报线人员
			if(userhuibao.size()==0)
			{
				// 获取该业务单据的 订单状态 去设置promotion节点人员
				JSONObject suobj = new JSONObject();
				suobj.put("supCode", orgiNo);
				String result = this.getSuplmentBySupCode(suobj
						.toJSONString());
				JSONObject jsonresult = parseObject(result);
				JSONObject data = jsonresult.getJSONObject("data");
				String orderType = data.getString("orderType");
				if (orderType.equals("0")) // 促销订单
				{
					JSONObject p1 = new JSONObject();
					p1.put("PARMNAME", "IsPromotion");
					p1.put("VALUE", "1");
					paramlist.add(p1);

					List<String> prolist = this.findBpmNodeByname("PROMOTION");

					JSONObject data1 = new JSONObject(true);
					data1.put("STEPNAME", "PROMOTION");
					data1.put("APPROVER", prolist);
					userlist.add(data1);

				} else if (orderType.equals("1")) { // pog
					JSONObject p1 = new JSONObject();
					p1.put("PARMNAME", "POG");
					p1.put("VALUE", "1");
					paramlist.add(p1);

					List<String> user5 =this.findBpmNodeByname("POG1");

					JSONObject data1 = new JSONObject(true);
					data1.put("STEPNAME", "POG1");
					data1.put("APPROVER", user5);

					List<String> user6 = this.findBpmNodeByname("POG2");
					JSONObject data6 = new JSONObject(true);
					data6.put("STEPNAME", "POG2");
					data6.put("APPROVER", user6);
					userlist.add(data1);
					userlist.add(data6);

				} else if (orderType.equals("2")) { // promotion 和Pog
					JSONObject p1 = new JSONObject(true);
					p1.put("PARMNAME", "POG");
					p1.put("VALUE", "1");
					JSONObject p2 = new JSONObject(true);
					p2.put("PARMNAME", "IsPromotion");
					p2.put("VALUE", "1");
					paramlist.add(p1);
					paramlist.add(p2);

					List<String> prolist = this.findBpmNodeByname("PROMOTION");
					JSONObject pp1 = new JSONObject(true);
					pp1.put("STEPNAME", "PROMOTION");
					pp1.put("APPROVER", prolist);
					userlist.add(pp1);

					// pog
					List<String> user5 =this.findBpmNodeByname("POG1");

					JSONObject data1 = new JSONObject(true);
					data1.put("STEPNAME", "POG1");
					data1.put("APPROVER", user5);

					List<String> user6 =this.findBpmNodeByname("POG2");

					JSONObject data6 = new JSONObject(true);
					data6.put("STEPNAME", "POG2");
					data6.put("APPROVER", user6);
					userlist.add(data1);
					userlist.add(data6);

				}

				param.put("PARMLIST", paramlist);
				param.put("APPROVERLIST", userlist);
			}else {
				JSONObject data3 = new JSONObject(true);
				data3.put("STEPNAME", "预审人1");
				data3.put("APPROVER", userhuibao);
				userlist.add(data3);
			}
		} else { // supply manage
			JSONObject data3 = new JSONObject(true);
			JSONObject userstr = new JSONObject(true);
			userstr.put("userId", this.getUserSessionBean().getUserId());
			List<String> userhuibao = this.findUserByNextPerson(
					userstr.toJSONString(), ""); // 查找汇报线人员
			data3.put("STEPNAME", "Supply Chain Manager");
			data3.put("APPROVER", userhuibao);
			userlist.add(data3);
		}

		param.put("APPROVERLIST", userlist);
		return param;
	}

	// 商品补货发起 流程
	@RequestMapping(method = RequestMethod.POST, value = "SuplementStartBpm")
	public String SuplementStartBpm(@RequestParam String params)
			throws ServerException {
		Map<String, String> paramap = new HashMap<String, String>();
		JSONObject jsonParam = JSON.parseObject(params);
		String userName = this.getUserSessionBean().getUserName();
		String action = jsonParam.getString("action");
		String billNo = jsonParam.getString("billNo");
		// String origno = jsonParam.getString("billNo");
		String orgiNo = jsonParam.getString("billNo");
		if (billNo.length() > 12) {
			orgiNo = billNo.split("-")[0];
		}

		JSONObject param = new JSONObject(true);
		param.put("Method", "SubmitIncident");
		param.put("TASKUSER", userName);
		param.put("PROCESSNAME", "补货流程");
		param.put("SUMMARY", billNo);
		param.put("ACTION", action);
		if (!jsonParam.containsKey("COMMENTS")) {
			param.put("COMMENTS", "");
		} else {
			param.put("COMMENTS", jsonParam.getString("COMMENTS"));
		}
		if (action.equals("Submit")) // 指定汇报线节点
		{
			param.put("VERSION", "0");
			param.put("INCIDENT", "0");
			param.put("TASKID", "");
			param = this.InitSupSet(param, jsonParam);

		} else if (action.equals("Approval")) { // 审批 查找汇报线人员
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}

			String STEPLABEL = jsonParam.getString("STEPLABEL");
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");

			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);

			if (STEPLABEL.equals("提交")) // 重新提交
			{

				String unameinfo = this.findWastonHistoricBycurentUser("补货流程",
						incident, "grantTo"); //
				String uname = unameinfo.split("&&&")[1];// 被委托人

				// 获取当前用户待办的taskId
				JSONObject pa = new JSONObject(true);
				pa.put("Method", "GetTaskList");
				pa.put("LISTTYPE", "1");
				if (!uname.equals(this.getUserSessionBean().getUserName())) {
					pa.put("TASKUSER", uname);
				} else {
					pa.put("TASKUSER", this.getUserSessionBean().getUserName());
				}
				pa.put("PROCESSLIST", "补货流程");
				pa.put("SUMMARY", billNo);

				pa.put("FROM", 1);
				pa.put("TO", 10);
				String result = this.ExecuteBpm(pa);

				JSONObject jsonresult = parseObject(result);
				JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");
				for (int i = 0; i < tasklist.size(); i++) {
					JSONObject taskchild = tasklist.getJSONObject(i);
					String step = taskchild.getString("STEPLABEL");
					if (step.equals("提交人")) {
						String TASKIDS = taskchild.getString("TASKID");
						param.remove("TASKID");
						param.put("TASKID", TASKIDS);
						break;
					}
				}

			}

			JSONObject userstr = new JSONObject(true);
			userstr.put("userId", this.getUserSessionBean().getUserId());
			String nextstepname = "";
			if (STEPLABEL.equals("SUPPLY CHAIN MANAGER")) {
				nextstepname = "SUPPLY CHAIN SENIOR MANGER";
			} else if (STEPLABEL.equals("SUPPLY CHAIN SENIOR MANGER")
					|| STEPLABEL.contains("POG")
					|| STEPLABEL.equals("PROMOTION")) {
				nextstepname = ""; // 结束掉
			} else {
				String data = STEPLABEL.substring(STEPLABEL.length() - 1,
						STEPLABEL.length());
				String data1 = STEPLABEL.substring(0, STEPLABEL.length() - 1);

				if (data.equals("1")) {
					nextstepname = data1 + "2";
				} else if (data.equals("2")) {
					nextstepname = data1 + "3";
				} else if (data.equals("3")) {
					nextstepname = data1 + "4";
				} else if (data.equals("4")) {
					nextstepname = data1 + "5";
				} else if (data.equals("5")) {
					nextstepname = data1 + "6";
				} else if (data.equals("6")) {
					nextstepname = data1 + "7";
				}
			}

			String unameinfo = this.findWastonHistoricBycurentUser("补货流程",
					incident, "grant");
			String uname = unameinfo.split("&&&")[1];
			if (!uname.equals(userName)) {
				String userid = unameinfo.split("&&&")[0];
				userstr.put("userId", new Integer(userid));
			} else {
				userstr.put("userId", this.getUserSessionBean().getUserId());
			}

			List<String> ulist = new ArrayList<String>();
			if(nextstepname.contains("预审人"))
			{
				ulist=this.findUserByNextPerson(
						userstr.toJSONString(), ""); // 查找汇报线人员
			}
			else {
				ulist=this.findUserByNextPerson(
						userstr.toJSONString(), "SUPPLY"); // 查找汇报线人员
			}


			JSONArray userlist = new JSONArray();
			if (ulist.size() == 0) {
				if (nextstepname.contains("预审人")) { // 本部门节点

					// 获取该业务单据的 订单状态 去设置promotion节点人员
					JSONObject suobj = new JSONObject();
					suobj.put("supCode", orgiNo);
					String result = this.getSuplmentBySupCode(suobj
							.toJSONString());
					JSONObject jsonresult = parseObject(result);
					JSONObject data = jsonresult.getJSONObject("data");
					String orderType = data.getString("orderType");
					JSONArray paramlist = new JSONArray();

					if (orderType.equals("0")) // 促销订单
					{
						JSONObject p1 = new JSONObject();
						p1.put("PARMNAME", "IsPromotion");
						p1.put("VALUE", "1");
						paramlist.add(p1);

						List<String> prolist = this.findBpmNodeByname("PROMOTION");

						JSONObject data1 = new JSONObject(true);
						data1.put("STEPNAME", "PROMOTION");
						data1.put("APPROVER", prolist);
						userlist.add(data1);

					} else if (orderType.equals("1")) { // pog
						JSONObject p1 = new JSONObject();
						p1.put("PARMNAME", "POG");
						p1.put("VALUE", "1");
						paramlist.add(p1);

						List<String> user5 =this.findBpmNodeByname("POG1");

						JSONObject data1 = new JSONObject(true);
						data1.put("STEPNAME", "POG1");
						data1.put("APPROVER", user5);

						List<String> user6 = this.findBpmNodeByname("POG2");
						JSONObject data6 = new JSONObject(true);
						data6.put("STEPNAME", "POG2");
						data6.put("APPROVER", user6);
						userlist.add(data1);
						userlist.add(data6);

					} else if (orderType.equals("2")) { // promotion 和Pog
						JSONObject p1 = new JSONObject(true);
						p1.put("PARMNAME", "POG");
						p1.put("VALUE", "1");
						JSONObject p2 = new JSONObject(true);
						p2.put("PARMNAME", "IsPromotion");
						p2.put("VALUE", "1");
						paramlist.add(p1);
						paramlist.add(p2);

						List<String> prolist = this.findBpmNodeByname("PROMOTION");


						JSONObject pp1 = new JSONObject(true);
						pp1.put("STEPNAME", "PROMOTION");
						pp1.put("APPROVER", prolist);
						userlist.add(pp1);

						// pog
						List<String> user5 =this.findBpmNodeByname("POG1");


						JSONObject data1 = new JSONObject(true);
						data1.put("STEPNAME", "POG1");
						data1.put("APPROVER", user5);

						List<String> user6 =this.findBpmNodeByname("POG2");


						JSONObject data6 = new JSONObject(true);
						data6.put("STEPNAME", "POG2");
						data6.put("APPROVER", user6);
						userlist.add(data1);
						userlist.add(data6);

					}

					param.put("PARMLIST", paramlist);
					param.put("APPROVERLIST", userlist);
				}
			}
			if (ulist.size() > 0) {
				if (!nextstepname.equals("")) {
					JSONObject data2 = new JSONObject(true);
					data2.put("STEPNAME", nextstepname);
					data2.put("APPROVER", ulist);
					userlist.add(data2);
					param.put("APPROVERLIST", userlist);
				}
			}

		} else if (action.equals("Return") || action.equals("Reject")) // 退回或拒绝
		{
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);

		}
		System.out.println(param.toJSONString());
		String result = this.ExecuteBpm(param);
		JSONObject data = JSON.parseObject(result);

		String messageresult = data.getString("MESSAGE");
		if (data.containsKey("SUCCESSCODE")) {

			if (action.equals("Submit")) {
				// 改变业务单据状态 保存流程提交人
				Map<String, String> taskInfo = getTaskInfoByUser(billNo, "3",
						"补货流程", this.getUserSessionBean().getUserName());

				JSONObject status = new JSONObject(true);
				status.put("billNo", orgiNo);
				status.put("state", "1"); // 审批中
				if (taskInfo.containsKey("TASKID")) {
					status.put("INCIDENT", taskInfo.get("INCIDENT"));
					status.put("TASKID", taskInfo.get("TASKID"));
				} else {
					status.put("TASKID", "");
					status.put("INCIDENT", "");
				}
				status.put("TASKID", "");
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", "Submit");

				this.updateSuppleStatus(status.toJSONString());
			} else if (action.equals("Reject")) {
				// 改变业务单据状态
				JSONObject status = new JSONObject(true);
				status.put("billNo", orgiNo);
				status.put("state", "3"); // 驳回
				status.put("INCIDENT", param.getString("INCIDENT"));
				status.put("VERSION", param.getString("VERSION"));
				status.put("TASKID", param.getString("TASKID"));
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", billNo);

				this.updateSuppleStatus(status.toJSONString());
			} else if (action.equals("Approval")) { // 重新提交
				JSONObject status = new JSONObject(true);
				status.put("billNo", orgiNo);
				status.put("state", "1"); // 审批中
				if (data.containsKey("PROCESS_COMPLETED")) {
                    String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
                    if ("1".equals(PROCESS_COMPLETED)){// 审批完成
                        status.put("state", "2"); // 审批完成
                    }
				}
				status.put("INCIDENT", param.getString("INCIDENT"));
				status.put("VERSION", param.getString("VERSION"));
				status.put("TASKID", param.getString("TASKID"));
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", billNo);
				this.updateSuppleStatus(status.toJSONString());
			}

//			if (data.containsKey("PROCESS_COMPLETED")) {
//
//				String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
//				if (PROCESS_COMPLETED.equals("1")) // 审批完成
//				{
//					// 改变业务单据状态
//					JSONObject status = new JSONObject(true);
//					status.put("billNo", orgiNo);
//					status.put("state", "2"); // 审批完成
//					status.put("INCIDENT", param.getString("INCIDENT"));
//					status.put("VERSION", param.getString("VERSION"));
//					status.put("TASKID", param.getString("TASKID"));
//					status.put("TASKUSER", param.getString("TASKUSER"));
//					status.put("ALLNO", billNo);
//
//					this.updateSuppleStatus(status.toJSONString());
//
//				}
//			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}
	}

	// 商品新增初始化流程节点
	public JSONObject InitSet(JSONObject param, JSONObject jsonParam) {
		String dept = "";
		Map<String, String> paramap = new HashMap<String, String>();
		JSONArray paramlist = new JSONArray();

		JSONArray varry = jsonParam.getJSONArray("variables");
		for (int i = 0; i < varry.size(); i++) {
			JSONObject jt = varry.getJSONObject(i);
			String type = jt.getString("type");
			if (type.equals("string")) {
				String name = jt.getString("name");
				if (name.equals("dept")) {
					dept = jt.getString("value");
				}
			} else {
				Integer value = jt.getInteger("value");
				// if (value == 1) {
				String name = jt.getString("name");
				JSONObject initparam = new JSONObject(true);
				initparam.put("PARMNAME", name);
				initparam.put("VALUE", value);
				paramlist.add(initparam);
				paramap.put(name, String.valueOf(value));
				// }
			}
		}
		if (paramlist.size() > 0) {
			param.put("PARMLIST", paramlist);
		}

		JSONArray userlist = new JSONArray();
		if (paramap.containsKey("SUPPLY")) {
//			JSONObject u3 = new JSONObject(true);
//			u3.put("processName", "商品新增流程");
//			u3.put("nodeName", "Supply Chain Officer");
			// 获取指定supply节点
			List<String> user3 = this.findBpmNodeByname("Supply Chain Officer");

			JSONObject data3 = new JSONObject();
			data3.put("STEPNAME", "Supply Chain Officer");
			data3.put("APPROVER", user3);
			userlist.add(data3);
		}
		// 需要nonobqa审批
		if (paramap.containsKey("NONOBQA")&&paramap.get("NONOBQA").equals("1")) {
			JSONObject u1 = new JSONObject(true);
			u1.put("processNodeCode", "201");
			u1.put("ownerUserId", this.getUserSessionBean().getUserId());
			List<String> user = this.getUserBynodeInfo(u1.toJSONString());

			JSONObject data1 = new JSONObject(true);
			data1.put("STEPNAME", "NON OB QA Officer");
			data1.put("APPROVER", user);
			userlist.add(data1);
		}
		// 需要obqa审批
		if (paramap.containsKey("OBQA")&&paramap.get("OBQA").equals("1")) {
			JSONObject u2 = new JSONObject(true);
			u2.put("processNodeCode", "200");
			u2.put("ownerUserId", this.getUserSessionBean().getUserId());
			List<String> user2 = this.getUserBynodeInfo(u2.toJSONString());
			JSONObject data2 = new JSONObject();
			data2.put("STEPNAME", "OB QA Officer");
			data2.put("APPROVER", user2);
			userlist.add(data2);
		}
		// 需要oem审批
		if (paramap.containsKey("OEM")&&paramap.get("OEM").equals("1")) {
			JSONObject u4 = new JSONObject(true);
			u4.put("processNodeCode", "206");
			u4.put("ownerUserId", this.getUserSessionBean().getUserId());
			List<String> user4 = this.getUserBynodeInfo(u4.toJSONString());
			JSONObject data4 = new JSONObject(true);
			data4.put("STEPNAME", "OEM Officer");
			data4.put("APPROVER", user4);
			userlist.add(data4);
		}

		if(paramap.containsKey("BIC")&&paramap.get("BIC").equals("1"))
		{
			List<String> user6 = this.findBpmNodeByname("ADDBIC");

			JSONObject data3 = new JSONObject();
			data3.put("STEPNAME", "BIC MANAGER");
			data3.put("APPROVER", user6);
			userlist.add(data3);
		}

		// JSONObject u5 = new JSONObject(true);
		// u5.put("processName", "商品新增流程");
		// u5.put("nodeName", "TA");
		// 获取指定supply节点
		List<String> user5 = this.findBpmNodeByname("TA");
		JSONObject data5 = new JSONObject(true);
		data5.put("STEPNAME", "TA");
		data5.put("APPROVER", user5);
		userlist.add(data5);
		List<String> user6 = new ArrayList<String>();

		String orgId = String.valueOf(this.getUserSessionBean().getOrgId());
		String Pu = "";
		String other = "";
		Map<String, String> gmu = this.getAllGm();
		for (Map.Entry<String, String> da : gmu.entrySet()) {
			if (orgId.equals(da.getValue())) {
				Pu = da.getKey();
			}
			if (da.getValue().equals("Others")) {
				other = da.getKey();
			}
		}

		if (Pu.equals("")) {
			Pu = other;
		}
		user6.add(Pu);

		JSONObject data6 = new JSONObject(true);
		data6.put("STEPNAME", "GM");
		data6.put("APPROVER", user6);
		userlist.add(data6);

		JSONObject userstr = new JSONObject(true);
		userstr.put("userId", this.getUserSessionBean().getUserId());
		List<String> user7 = this.findUserByNextPerson(userstr.toJSONString(),
				""); // 查找汇报线人员

		JSONObject data7 = new JSONObject(true);
		data7.put("STEPNAME", "采购预审人1");
		data7.put("APPROVER", user7);
		userlist.add(data7);

		param.put("APPROVERLIST", userlist);
		String pa=param.toJSONString();
		return param;
	}

	// 商品修改初始化流程节点
	public JSONObject InitUpdateSet(JSONObject param, JSONObject jsonParam) {
		String dept = "";
		Map<String, String> paramap = new HashMap<String, String>();
		JSONArray paramlist = new JSONArray();

		JSONArray varry = jsonParam.getJSONArray("variables");
		for (int i = 0; i < varry.size(); i++) {
			JSONObject jt = varry.getJSONObject(i);
			String type = jt.getString("type");
			if (type.equals("string")) {
				String name = jt.getString("name");
				if (name.equals("dept")) {
					dept = jt.getString("value");
				}
			} else {
				Integer value = jt.getInteger("value");

				String name = jt.getString("name");
				if(!name.equals("BIC1")) {
					JSONObject initparam = new JSONObject(true);
					initparam.put("PARMNAME", name);
					initparam.put("VALUE", value);
					paramlist.add(initparam);
				}
				paramap.put(name, String.valueOf(value));
				// }
			}
		}
		if (paramlist.size() > 0) {
			param.put("PARMLIST", paramlist);
		}

		JSONArray userlist = new JSONArray();
		if (paramap.containsKey("SUPPLY")&&paramap.get("SUPPLY").equals("1")) {

			// 获取指定supply节点
			List<String> user3 = this.findBpmNodeByname("Supply Chain Officer");

			JSONObject data3 = new JSONObject();
			data3.put("STEPNAME", "Supply Chain Officer");
			data3.put("APPROVER", user3);
			userlist.add(data3);
		}
		// 需要nonobqa审批
		if (paramap.containsKey("NONOBQA")&&paramap.get("NONOBQA").equals("1")) {
			JSONObject u1 = new JSONObject(true);
			u1.put("processNodeCode", "201");
			u1.put("ownerUserId", this.getUserSessionBean().getUserId());
			List<String> user = this.getUserBynodeInfo(u1.toJSONString());

			JSONObject data1 = new JSONObject(true);
			data1.put("STEPNAME", "NON OB QA Officer");
			data1.put("APPROVER", user);
			userlist.add(data1);
		}
		// 需要obqa审批
		if (paramap.containsKey("OBQA")&&paramap.get("OBQA").equals("1")) {
			JSONObject u2 = new JSONObject(true);
			u2.put("processNodeCode", "200");
			u2.put("ownerUserId", this.getUserSessionBean().getUserId());
			List<String> user2 = this.getUserBynodeInfo(u2.toJSONString());
			JSONObject data2 = new JSONObject();
			data2.put("STEPNAME", "OB QA Officer");
			data2.put("APPROVER", user2);
			userlist.add(data2);
		}
		// 需要oem审批
		if (paramap.containsKey("OEM")&&paramap.get("OEM").equals("1")) {
			JSONObject u4 = new JSONObject(true);
			u4.put("processNodeCode", "206");
			u4.put("ownerUserId", this.getUserSessionBean().getUserId());
			List<String> user4 = this.getUserBynodeInfo(u4.toJSONString());
			JSONObject data4 = new JSONObject(true);
			data4.put("STEPNAME", "OEM Officer");
			data4.put("APPROVER", user4);
			userlist.add(data4);
		}

		// JSONObject u5 = new JSONObject(true);
		// u5.put("processName", "商品新增流程");
		// u5.put("nodeName", "TA");
		// 获取指定supply节点
		List<String> user5 = this.findBpmNodeByname("TA");
		JSONObject data5 = new JSONObject(true);
		data5.put("STEPNAME", "TA");
		data5.put("APPROVER", user5);
		userlist.add(data5);

		if(paramap.containsKey("NEEDGM")&&paramap.get("NEEDGM").equals("1")) {
			List<String> user6 = new ArrayList<String>();
			String orgId = String.valueOf(this.getUserSessionBean().getOrgId());
			String Pu = "";
			String other = "";
			Map<String, String> gmu = this.getAllGm();
			for (Map.Entry<String, String> da : gmu.entrySet()) {
				if (orgId.equals(da.getValue())) {
					Pu = da.getKey();
				}
				if (da.getValue().equals("Others")) {
					other = da.getKey();
				}
			}

			if (Pu.equals("")) {
				Pu = other;
			}
			user6.add(Pu);

			JSONObject data6 = new JSONObject(true);
			data6.put("STEPNAME", "GM");
			data6.put("APPROVER", user6);
			userlist.add(data6);
		}

		if(paramap.containsKey("BIC")&&paramap.get("BIC").equals("1"))
		{
			List<String> user11=new ArrayList<String>();
			if(paramap.containsKey("BIC1"))
			{
				user11= this.findBpmNodeByname("ADDBIC");
			}
			else {
				user11= this.findBpmNodeByname("UPDATEBIC");
			}

			JSONObject data11 = new JSONObject(true);
			data11.put("STEPNAME", "BIC MANAGER");
			data11.put("APPROVER", user11);
			userlist.add(data11);
		}


		List<String> u12= new ArrayList<String>();
		u12.add("10000000");
		JSONObject data12 = new JSONObject(true);
		data12.put("STEPNAME","RMS确认");
		data12.put("APPROVER", u12);
		userlist.add(data12);




		JSONObject userstr = new JSONObject(true);
		userstr.put("userId", this.getUserSessionBean().getUserId());
		List<String> user7 = this.findUserByNextPerson(userstr.toJSONString(),
				""); // 查找汇报线人员

		JSONObject data7 = new JSONObject(true);
		data7.put("STEPNAME", "采购预审人1");
		data7.put("APPROVER", user7);
		userlist.add(data7);

		param.put("APPROVERLIST", userlist);
		String par=param.toJSONString();
		System.out.println(par);
		return param;
	}


	// 商品新增 发起流程
	@RequestMapping(method = RequestMethod.POST, value = "StartBpm")
	public String startBpm(@RequestParam String params) throws ServerException {
		String userName = "";
		JSONObject jsonParam = JSON.parseObject(params);
		if (jsonParam.containsKey("TASKUSER")) {
			userName = jsonParam.getString("TASKUSER");
		} else {
			userName = this.getUserSessionBean().getUserName();
		}
		String action = jsonParam.getString("action");
		String billNo = jsonParam.getString("billNo");
		String orgiNo = jsonParam.getString("billNo");
		if (billNo.length() > 12) {
			orgiNo = billNo.split("-")[0];
		}

		JSONObject param = new JSONObject(true);
		param.put("Method", "SubmitIncident");
		if (jsonParam.containsKey("TASKUSER")) {
			param.put("TASKUSER", jsonParam.getString("TASKUSER"));
		} else {

			param.put("TASKUSER", userName);
		}
		param.put("PROCESSNAME", "商品新增流程");
		param.put("SUMMARY", billNo);
		param.put("ACTION", action);
		if (!jsonParam.containsKey("COMMENTS")) {
			param.put("COMMENTS", "");
		} else {
			param.put("COMMENTS", jsonParam.getString("COMMENTS"));
		}

		if (action.equals("Submit")) // 指定汇报线节点
		{
			param.put("VERSION", "0");
			param.put("INCIDENT", "0");
			param.put("TASKID", "");
			if (!jsonParam.containsKey("variables")) {
				throw new ServerException("缺少流程参数!");
			} else {
				param = InitSet(param, jsonParam);
			}

		} else if (action.equals("Approval")) { // 审批 查找汇报线人员
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}

			String STEPLABEL = jsonParam.getString("STEPLABEL");
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");

			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);

			if (STEPLABEL.equals("提交")) // 重新提交
			{
				String unameinfo = this.findWastonHistoricBycurentUser(
						"商品新增流程", incident, "grantTo"); //
				String uname = unameinfo.split("&&&")[1];// 被委托人

				// 获取当前用户待办的taskId
				JSONObject pa = new JSONObject(true);
				pa.put("Method", "GetTaskList");
				pa.put("LISTTYPE", "1");
				if (!userName.equals(uname)) {

					pa.put("TASKUSER", uname);
				} else {
					pa.put("TASKUSER", this.getUserSessionBean().getUserName());
				}
				pa.put("PROCESSLIST", "商品新增流程");
				pa.put("SUMMARY", billNo);

				pa.put("FROM", 1);
				pa.put("TO", 10);
				String result = this.ExecuteBpm(pa);

				JSONObject jsonresult = parseObject(result);
				JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");
				for (int i = 0; i < tasklist.size(); i++) {
					JSONObject taskchild = tasklist.getJSONObject(i);
					String step = taskchild.getString("STEPLABEL");
					if (step.equals("提交")) {
						String TASKIDS = taskchild.getString("TASKID");
						param.remove("TASKID");
						param.put("TASKID", TASKIDS);
						break;
					}
				}

				// 重新设置 人员节点
				param = InitSet(param, jsonParam);

				JSONObject status = new JSONObject(true);
				status.put("billNo", orgiNo);
				status.put("status", "4"); // 变成新增审批中
				status.put("INCIDENT", param.getString("INCIDENT"));
				status.put("VERSION", param.getString("VERSION"));
				status.put("TASKID", param.getString("TASKID"));
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", billNo);
				this.updateStatus(status.toJSONString());

				String grantinfo = this.findWastonHistoricBycurentUser(
						"商品新增流程", incident, "grantTo"); //
				String grantname = grantinfo.split("&&&")[1];// 被委托人
				if (!userName.equals(grantname)) {
					param.remove("TASKUSER");
					param.put("TASKUSER", grantname);
				}

			}

			String nextstepname = "";
			if (STEPLABEL.equals("NON OB QA OFFICER")) {
				nextstepname = "NON OB QA - 预审人1";
			} else if (STEPLABEL.equals("OB QA OFFICER")) {
				nextstepname = "OB QA - 预审人1";
			} else if (STEPLABEL.equals("SUPPLY CHAIN OFFICER")) {
				// nextstepname = "Supply Chain - 预审人1";
			} else if (STEPLABEL.equals("OEM OFFICER")) {
				nextstepname = "OEM - 预审人1";
			} else if (STEPLABEL.equals("GM")) {
				nextstepname = "RMS确认";
			} else {
				String data = STEPLABEL.substring(STEPLABEL.length() - 1,
						STEPLABEL.length());
				String data1 = STEPLABEL.substring(0, STEPLABEL.length() - 1);

				if (data.equals("1")) {
					nextstepname = data1 + "2";
				} else if (data.equals("2")) {
					nextstepname = data1 + "3";
				} else if (data.equals("3")) {
					nextstepname = data1 + "4";
				} else if (data.equals("4")) {
					nextstepname = data1 + "5";
				} else if (data.equals("5")) {
					nextstepname = data1 + "6";
				} else if (data.equals("6")) {
					nextstepname = data1 + "7";
				}
			}

			if (!nextstepname.equals("")) {
				JSONObject userstr = new JSONObject(true);
				if (!userName.equals("10000000")) {
					String unameinfo = this.findWastonHistoricBycurentUser(
							"商品新增流程", incident, "grant");
					String uname = unameinfo.split("&&&")[1];
					if (!uname.equals(userName)) {
						String userid = unameinfo.split("&&&")[0];
						userstr.put("userId", new Integer(userid));
					} else {
						userstr.put("userId", this.getUserSessionBean()
								.getUserId());
					}
				}
				if (nextstepname.equals("RMS确认")) {
					JSONArray userlist = new JSONArray();
					List<String> ulist = new ArrayList<String>();
					ulist.add("10000000");
					JSONObject data2 = new JSONObject(true);
					data2.put("STEPNAME", nextstepname);
					data2.put("APPROVER", ulist);
					userlist.add(data2);
					param.put("APPROVERLIST", userlist);

					// 改变业务单据状态
					JSONObject status = new JSONObject(true);
					status.put("billNo", orgiNo);
					status.put("status", "6"); // RMS待确认
					status.put("INCIDENT", param.getString("INCIDENT"));
					status.put("VERSION", param.getString("VERSION"));
					status.put("TASKID", param.getString("TASKID"));
					status.put("TASKUSER", param.getString("TASKUSER"));
					status.put("ALLNO", billNo);
					this.updateStatus(status.toJSONString());

				} else {
					List<String> ulist = new ArrayList<String>();
					if (nextstepname.contains("NON OB QA")) {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), "NONOBQA");
					} else if (nextstepname.contains("OB QA")) {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), "OBQA");
					} else if (nextstepname.contains("预审人")) {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), "预审人"); // 本部门
					} else if (nextstepname.contains("OEM")) {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), "OEM"); // 本部门
					} else {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), ""); // 查找汇报线人员
					}
					if (ulist.size() > 0) {
						JSONArray userlist = new JSONArray();
						JSONObject data2 = new JSONObject(true);
						data2.put("STEPNAME", nextstepname);
						data2.put("APPROVER", ulist);
						userlist.add(data2);
						param.put("APPROVERLIST", userlist);
					}
				} // else

			}

		} else if (action.equals("Return") || action.equals("Reject")) // 退回或拒绝
		{

			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);

		}
		System.out.println(param.toJSONString());
		String result = this.ExecuteBpm(param);
		JSONObject data = JSON.parseObject(result);
		// String messagecode = data.getString("SUCCESSCODE");
		String messageresult = data.getString("MESSAGE");
		if (data.containsKey("SUCCESSCODE")) {
			// 提交
			if (action.equals("Submit")) {
				// 根据单据号，当前人 获取我的流程第一条数据 INCIDENT 保存至业务单据
				JSONObject pa = new JSONObject(true);
				pa.put("Method", "GetTaskList");
				pa.put("LISTTYPE", "3");
				pa.put("TASKUSER", this.getUserSessionBean().getUserName());
				pa.put("PROCESSLIST", "商品新增流程");

				pa.put("SUMMARY", billNo);

				pa.put("FROM", 1);
				pa.put("TO", 10);
				String myresult = this.ExecuteBpm(pa);
				JSONObject jsonresult = parseObject(myresult);
				JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");
				String incident = "";
				if (tasklist.size() > 0) {
					JSONObject ja = tasklist.getJSONObject(0);
					incident = ja.getString("INCIDENT");
				}

				JSONObject status = new JSONObject(true);
				status.put("billNo", orgiNo);
				status.put("status", "4"); // 新增审批中
				status.put("INCIDENT", incident);
				status.put("VERSION", "");
				status.put("TASKID", "");
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", "submit");
				this.updateStatus(status.toJSONString());
			} else if (action.equals("Reject")
					&& !param.getString("STEPLABEL").equals("RMS确认")) { // 驳回
				// 改变业务单据状态 已撤回
				JSONObject status = new JSONObject(true);
				status.put("billNo", orgiNo);
				status.put("status", "10"); // 已驳回
				status.put("INCIDENT", param.getString("INCIDENT"));
				status.put("VERSION", param.getString("VERSION"));
				status.put("TASKID", param.getString("TASKID"));
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", billNo);
				this.updateStatus(status.toJSONString());
			}

			if (data.containsKey("PROCESS_COMPLETED")) {
				String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
				if (PROCESS_COMPLETED.equals("1")) // 审批完成
				{
					// 改变业务单据状态
					JSONObject status = new JSONObject(true);
					status.put("billNo", orgiNo);
					status.put("status", "8");
					status.put("INCIDENT", param.getString("INCIDENT"));
					status.put("VERSION", param.getString("VERSION"));
					status.put("TASKID", param.getString("TASKID"));
					status.put("TASKUSER", param.getString("TASKUSER"));
					status.put("ALLNO", billNo);
					this.updateStatus(status.toJSONString());
				}
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			if (messageresult.equals("未将对象引用设置到对象的实例。")) {
				messageresult = "审批已被处理，请刷新单据状态!";
			}
			if (action.equals("Submit")) // 提交失败 将状态还原为采购完善
			{
				JSONObject status = new JSONObject(true);
				status.put("billNo", orgiNo);
				status.put("status", "3"); // 采购完善
				status.put("INCIDENT", "");
				status.put("VERSION", "");
				status.put("TASKID", "");
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", "error");
				this.updateStatus(status.toJSONString());
			}
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}

		// return result;
	}

	// 取消流程
	@RequestMapping(method = RequestMethod.POST, value = "stopBpm")
	public String stopBpm(@RequestParam String params) throws ServerException {
		JSONObject data = JSON.parseObject(params);
		if (!data.containsKey("billNo")) {
			throw new ServerException("缺少流程参数 billNo,单据编号!");
		}
		String billNo = data.getString("billNo");

		JSONObject pa = new JSONObject(true);
		pa.put("Method", "CancelIncident");
		if ((data.getString("PROCESSNAME").equals("供应商资质管理流程") || data
				.getString("PROCESSNAME").equals("供应商资质管理修改流程"))
				&& this.getUserSessionBean().getUserType().equals("60")) {
			pa.put("TASKUSER", this.getUserSessionBean().getNamePingyin());
		} else {
			pa.put("TASKUSER", this.getUserSessionBean().getUserName());
		}
		pa.put("PROCESSNAME", data.getString("PROCESSNAME"));
		pa.put("INCIDENT", data.getString("INCIDENT"));

		// 是否被委托

		String curentUser = findWastonHistoricBycurentUser(
				data.getString("PROCESSNAME"), data.getString("INCIDENT"),
				"grant");
		String userName = curentUser.split("&&&")[1];
		if (!userName.equals(this.getUserSessionBean().getUserName())) {
			pa.remove("TASKUSER");
			pa.put("TASKUSER", userName);
		}

		String result = this.ExecuteBpm(pa);
		JSONObject datas = JSON.parseObject(result);
		String messageresult = datas.getString("MESSAGE");
		if (datas.containsKey("SUCCESSCODE")) {

			if (data.getString("PROCESSNAME").equals("商品新增流程")) {

				String origNo = data.getString("billNo");
				if (billNo.length() > 12) {
					origNo = billNo.split("-")[0];
				}

				JSONObject status = new JSONObject(true);
				status.put("billNo", origNo);
				status.put("status", "5"); // 作废掉
				status.put("INCIDENT", pa.getString("INCIDENT"));
				status.put("VERSION", "");
				status.put("TASKID", "");
				status.put("TASKUSER", this.getUserSessionBean().getUserName());
				status.put("ALLNO", "");

				this.updateStatus(status.toJSONString());
			} else if (data.getString("PROCESSNAME").equals("补货流程")) {
				String origNo = data.getString("billNo");
				if (billNo.length() > 12) {
					origNo = billNo.split("-")[0];
				}

				JSONObject status = new JSONObject(true);
				status.put("billNo", origNo);
				status.put("state", "4"); // 作废掉
				status.put("INCIDENT", pa.getString("INCIDENT"));
				status.put("VERSION", "");
				status.put("TASKID", "");
				status.put("TASKUSER", this.getUserSessionBean().getUserName());
				status.put("ALLNO", "");

				this.updateSuppleStatus(status.toJSONString());
			} else if (data.getString("PROCESSNAME").equals("Group Brand流程")) {
				JSONObject gb = new JSONObject(true);
				gb.put("status", "N");
				gb.put("processname", "groupbrand");
				gb.put("billNo", billNo.split("-")[0]);
				gb.put("INCIDENT", "");
				gb.put("TASKID", "");
				gb.put("VERSION", "");
				gb.put("TASKUSER", "");
				gb.put("allNo", billNo);

				this.updaterGroupStatus(gb.toJSONString());
			} else if (data.getString("PROCESSNAME").equals("中英文品牌流程")) {
				JSONObject brandpa = new JSONObject(true);
				brandpa.put("brandMapId", billNo.split("-")[0]);
				brandpa.put("status", "CANCEL");
				brandpa.put("INCIDENT", "");
				brandpa.put("VERSION", "");
				brandpa.put("TASKID", "");
				brandpa.put("TASKUSER", "");
				this.updateBrandStatus(brandpa.toJSONString());
			} else if (data.getString("PROCESSNAME").equals("Mother Comany流程")) {
				JSONObject gb = new JSONObject(true);
				gb.put("status", "N");
				gb.put("processname", "mother");
				gb.put("billNo", billNo.split("-")[0]);
				gb.put("INCIDENT", "");
				gb.put("TASKID", "");
				gb.put("VERSION", "");
				gb.put("TASKUSER", "");
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			} else if (data.getString("PROCESSNAME").equals("品牌流程")) {
				JSONObject gb = new JSONObject(true);
				gb.put("status", "N");
				gb.put("processname", "brandinfo");
				gb.put("billNo", billNo.split("-")[0]);
				gb.put("INCIDENT", "");
				gb.put("TASKID", "");
				gb.put("VERSION", "");
				gb.put("TASKUSER", "");
				gb.put("allNo", billNo);
				this.updaterGroupStatus(gb.toJSONString());
			} else if (data.getString("PROCESSNAME").equals("供应商资质管理流程")
					|| data.getString("PROCESSNAME").equals("供应商资质管理修改流程")) {
				JSONObject gb = new JSONObject(true);
				gb.put("status", "CANCEL"); // 拒绝
				gb.put("billNo", billNo);
				gb.put("INCIDENT", "");
				gb.put("TASKID", "");
				gb.put("VERSION", "");
				gb.put("TASKUSER", "");

				this.updaterSupplierQaStatus(gb.toJSONString());
			} else if (data.getString("PROCESSNAME").equals("商品修改流程")) {
				String origNo = data.getString("billNo");
				if (billNo.length() > 12) {
					origNo = billNo.split("-")[0];
				}

				JSONObject status = new JSONObject(true);
				status.put("ecoNo", origNo);
				status.put("status", "CANCEL"); // 已取消
				status.put("INCIDENT", "");
				status.put("VERSION", "");
				status.put("TASKID", "");
				status.put("TASKUSER", "");
				status.put("ALLNO", billNo);
				this.productUpdateStatus(status.toJSONString());
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {

			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), "操作失败,流程不存在或输入字符串的格式不正确!").toString();

		}

	}

	// 获取待办,已审批,我的申请
	@RequestMapping(method = RequestMethod.POST, value = "getTaskList")
	public String getTaskList(
			@RequestParam String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows)
			throws ServerException {

		Integer begin = 0;
		Integer end = 0;
		if (pageIndex == 1) {
			begin = 1;
			end = pageRows;
		} else {
			begin = (pageIndex - 1) * pageRows + 1;
			end = (pageIndex - 1) * pageRows + pageRows;
		}
		JSONObject data = JSON.parseObject(params);

		JSONObject pa = new JSONObject(true);
		pa.put("Method", "GetTaskList");
		pa.put("LISTTYPE", data.getString("LISTTYPE"));
		if (//this.getUserSessionBean().getUserName().length() > 8&&
		this.getUserSessionBean().getUserType().equals("60")) {
			pa.put("TASKUSER", this.getUserSessionBean().getNamePingyin());
		} else {
			pa.put("TASKUSER", this.getUserSessionBean().getUserName());
		}
		if (data.containsKey("PROCESSNAME")) {
			if (!data.getString("PROCESSNAME").equals("")) {
				pa.put("PROCESSLIST", data.getString("PROCESSNAME"));
			} else {
				pa.put("PROCESSLIST", data.getString("PROCESSLIST"));
			}

		} else {
			pa.put("PROCESSLIST", data.getString("PROCESSLIST"));
		}
		if (data.containsKey("SUMMARY")) {
			pa.put("SUMMARY", data.getString("SUMMARY"));
		} else {
			pa.put("SUMMARY", "");
		}
		if ((data.containsKey("CREATEDTIME") && !data.getString("CREATEDTIME")
				.equals(""))
				|| (data.containsKey("ENDTIME") && !data.getString("ENDTIME")
				.equals(""))
				|| (data.containsKey("CREATE_BY_NAME") && !data.getString(
				"CREATE_BY_NAME").equals(""))
				|| (data.containsKey("") && !data.getString("CREATE_DATE")
				.equals(""))) {
			pa.put("FROM", 1);
			pa.put("TO", 100);
		} else {
			pa.put("FROM", begin);
			pa.put("TO", end);
		}
		String result = this.ExecuteBpm(pa);
		JSONObject jsonresult = parseObject(result);
		Integer total = jsonresult.getInteger("TOTAL");
		List<Object> pageresults = new ArrayList<Object>();
		Pagination<Object> results = new Pagination<Object>();
		results.setPageSize(pageRows);
		results.setCount(total);

		results.setCurIndex(pageIndex);
		Integer pagecount = 0;
		if (total / pageRows == 0) {
			pagecount = total / pageRows;
		} else {
			pagecount = total / pageRows + 1;
		}

		if (pagecount == 0) {
			pagecount = 1;
		}
		results.setPagesCount(pagecount);
		results.setNextIndex(pageIndex + 1);
		JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");

		for (int i = 0; i < tasklist.size(); i++) {
			JSONObject taskchild = tasklist.getJSONObject(i);
			String STEPLABEL = taskchild.getString("STEPLABEL");
			String PROCESSNAME = taskchild.getString("PROCESSNAME");
			if (PROCESSNAME.equals("商品新增流程")) {
				if (STEPLABEL.contains("NON OB QA")) {
					taskchild.put("MODIFY", "1"); // 可编辑
				} else {
					taskchild.put("MODIFY", "0");
				}

			} else if (PROCESSNAME.equals("供应商资质管理流程")
					|| PROCESSNAME.equals("供应商资质管理修改流程")) {
				if (STEPLABEL.equals("提交")) {
					JSONObject up = new JSONObject();
					up.put("namePingyin", this.getUserSessionBean().getNamePingyin());
					String userresult = getUserDetail(up.toJSONString());
					JSONObject uresult = parseObject(userresult);
					JSONArray datas = uresult.getJSONArray("data");
					if (datas.size() > 0) {
						JSONObject reobj = datas.getJSONObject(0);
						String userName = reobj.getString("userName");
						taskchild.remove("TASKUSER");
						taskchild.remove("ASSIGNEDTOUSER");
						taskchild.put("TASKUSER", userName);
						taskchild.put("ASSIGNEDTOUSER", userName);
					}

				}

				taskchild.put("MODIFY", "1"); // 可编辑
			} else if (PROCESSNAME.equals("商品修改流程")) {
				String SUMMARY = taskchild.getString("SUMMARY");
				String billno = SUMMARY.split("-")[0];
				JSONObject ja = new JSONObject();
				ja.put("ecoNo", billno);
				List<PlmProductHeadEcoEntity_HI> ecl = plmProductHeadEcoServer
						.findList(ja);
				if (ecl.size() > 0) {
					PlmProductHeadEcoEntity_HI ec = ecl.get(0);
					Integer ecoId = ec.getEcoId();
					Integer prohead = ec.getProductHeadId();
					taskchild.put("ecoId", ecoId);
					taskchild.put("productHeadId", prohead);
				}

			} else {
				taskchild.put("MODIFY", "0");
			}

			if ((data.containsKey("CREATEDTIME") && !data.getString(
					"CREATEDTIME").equals(""))
					|| (data.containsKey("ENDTIME") && !data.getString(
					"ENDTIME").equals(""))) {
				// STARTTIME ENDTIME
				if (i == pageRows) {
					break;
				}

				if (data.containsKey("CREATEDTIME")
						&& !data.containsKey("ENDTIME")) {
					String createdtime = data.getString("CREATEDTIME"); // 查询传入时间
					// yyyy-mm-dd
					// HH:mm:ss
					String starttime = taskchild.getString("STARTTIME");
					String createtolong = createdtime.replace("-", "");

					String starttimetolong = starttime.substring(0, 10)
							.replace("-", "");
					long nesslong = Long.valueOf(createtolong);
					long initlong = Long.valueOf(starttimetolong);
					if (initlong >= nesslong) {
						pageresults.add(taskchild);
					}

				} else if (data.containsKey("CREATEDTIME")
						&& data.containsKey("ENDTIME")) {
					String createdtime = data.getString("CREATEDTIME");
					String starttime = taskchild.getString("STARTTIME");
					String endtime = taskchild.getString("ENDTIME");
					String createtolong = createdtime.replace("-", "");

					String starttimetolong = starttime.substring(0, 10)
							.replace("-", "");
					String endtimetolong = endtime.replace("-", "");

					long nesslong = Long.valueOf(createtolong);
					long initlong = Long.valueOf(starttimetolong);
					long nessendlong = Long.valueOf(endtimetolong);
					if (initlong >= nesslong && initlong <= nessendlong) {
						pageresults.add(taskchild);
					}

				} else if (!data.containsKey("CREATEDTIME")
						&& data.containsKey("ENDTIME")) {

					String starttime = taskchild.getString("STARTTIME");
					String endtime = taskchild.getString("ENDTIME");

					String starttimetolong = starttime.substring(0, 10)
							.replace("-", "");
					String endtimetolong = endtime.replace("-", "");

					long initlong = Long.valueOf(starttimetolong);
					long nessendlong = Long.valueOf(endtimetolong);
					if (initlong <= nessendlong) {
						pageresults.add(taskchild);
					}

				}

			} else if (data.containsKey("CREATE_BY_NAME")
					&& !data.getString("CREATE_BY_NAME").equals("")) { // 包含发起人
				String CREATE_BY_NAME = data.getString("CREATE_BY_NAME"); // 得到参数
				String created_name = taskchild.getString("CREATE_BY_NAME"); // 创建名称
				String created_no = taskchild.getString("CREATE_BY_NO"); // 创建工号
				if (created_name.equals("") || created_no.equals("")) {
					continue;
				}
				if (created_name.contains(CREATE_BY_NAME)
						|| created_name.contains(created_no)) {
					pageresults.add(taskchild);
				}
			} else if (data.containsKey("CREATE_DATE")
					&& !data.getString("CREATE_DATE").equals("")) // 发起时间
			{
				String CREATE_DATE = data.getString("CREATE_DATE");

				String create_date = taskchild.getString("CREATE_DATE")
						.substring(0, 10).trim();
				if (create_date.equals(CREATE_DATE)) {
					pageresults.add(taskchild);
				}

			} else {
				pageresults.add(taskchild);
			}

		}
		results.setData(pageresults);
		String resultString = JSON.toJSONString(results);
		JSONObject jsonObject = JSONObject.parseObject(resultString);
		jsonObject.put(SToolUtils.STATUS, "S");
		jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
		jsonObject.put("count", total);
		jsonObject.put("url", "");
		return jsonObject.toString();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {

		return null;
	}

	public static JSONObject getServiceResult(String requestURL,
											  JSONObject queryParamJSON) {
		Assert.isTrue(StringUtils.isNotBlank(requestURL),
				"requesturl is required");
		RestTemplate restTemplate = (RestTemplate) SpringBeanUtil
				.getBean("restTemplate");
		if (restTemplate == null) {
			throw new ExceptionInInitializerError("初始化异常");
		}

		MultiValueMap header = new LinkedMultiValueMap();
		Long timestamp = System.currentTimeMillis();
		header.add("timestamp", timestamp + "");
		header.add("pdasign", SaafToolUtils.buildSign(timestamp));
		JSONObject resultJSON = restSpringCloudPost(requestURL, queryParamJSON,
				header, restTemplate);
		if (resultJSON.getIntValue("statusCode") == 200) {
			if (JSON.parse(resultJSON.getString("data")) instanceof JSONArray) {
				return resultJSON;
			}
			JSONObject data = resultJSON.getJSONObject("data");
			return data;
		}
		return null;
	}

	public static JSONObject restSpringCloudPost(String requestURL,
												 JSONObject postParam, MultiValueMap<String, String> headerParams,
												 RestTemplate restTemplate) {
		JSONObject resultJSONObject = new JSONObject();

		if (!headerParams.containsKey("Accept")) {
			headerParams.add("Accept", "application/json");
		}

		if (!headerParams.containsKey("Accept-Charset")) {
			headerParams.add("Accept-Charset", "utf-8");
		}

		if (!headerParams.containsKey("Content-Type")) {
			headerParams.add("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
		}

		if (!headerParams.containsKey("Content-Encoding")) {
			headerParams.add("Content-Encoding", "UTF-8");
		}

		StringBuilder requestBodey = new StringBuilder();
		if (postParam != null && postParam.size() > 0) {
			Set<Map.Entry<String, Object>> entrySet = postParam.entrySet();
			Iterator iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry) iterator.next();
				requestBodey.append(entry.getKey()).append("=");
				if (entry.getValue() != null) {
					try {
						requestBodey.append(URLEncoder.encode(entry.getValue()
								.toString(), "utf-8"));
					} catch (UnsupportedEncodingException e) {
					}
				}
				if (iterator.hasNext())
					requestBodey.append("&");
			}
		}

		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		HttpClient httpClient = httpClientBuilder.build();
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);

		if (postParam != null
				&& StringUtils.isNotBlank(postParam.toJSONString())) {
			try {
				restTemplate.setRequestFactory(clientHttpRequestFactory);
				HttpEntity request = new HttpEntity(requestBodey.toString(),
						headerParams);
				ResponseEntity responseEntity = restTemplate.postForEntity(
						requestURL, request, String.class, new Object[0]);
				HttpStatus strVlaue = responseEntity.getStatusCode();
				if (strVlaue.value() == 200) {
					resultJSONObject.put("statusCode", strVlaue.value());
				}

				String body = (String) responseEntity.getBody();
				resultJSONObject.put("data", body);
			} catch (Exception var13) {
				resultJSONObject.put("msg", var13.getMessage());
			}
		}
		return resultJSONObject;
	}

	@RequestMapping(method = RequestMethod.POST, value = "getUserBynodeInfo")
	public List<String> getUserBynodeInfo(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmProductBpmUserService/findByConditionList",
				jsonObject);
		if (resultJson.containsKey("data")) {
			List<String> lidata = new ArrayList<String>();
			JSONArray data = resultJson.getJSONArray("data");
			for (int i = 0; i < data.size(); i++) {
				JSONObject jdata = data.getJSONObject(i);
				String uname = jdata.getString("userName");
				// 41152251
				if (uname.length() == 8) {
					lidata.add(uname);
				}
			}
			return lidata;
		} else {
			return new ArrayList<String>();
		}
	}

	// 汇报线接口
	@RequestMapping(method = RequestMethod.POST, value = "findUserByNextPerson")
	public List<String> findUserByNextPerson(String params, String stepName) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/baseServer/basePersonService/findAssignStartUser2",
				jsonObject);
		if (resultJson.containsKey("data")) {
			JSONArray data = resultJson.getJSONArray("data");
			if (data.size() == 0) {
				return new ArrayList<String>();
			}
			List<String> lidata = new ArrayList<String>();
			for (int i = 0; i < data.size(); i++) {
				JSONObject jdata = data.getJSONObject(i);
				String uname = jdata.getString("userName");
				String postName = jdata.getString("postName");
				if (stepName.equals("NONOBQA") || stepName.equals("SUPPLY")||stepName.equals("OEM")) {
					if (postName.contains("Director")) {
						return new ArrayList<String>();
					} else {
						lidata.add(uname);
					}

				} else if (stepName.equals("OBQA")) { // obqa
					if (postName.contains("Director")
							&& !postName.contains("Assistant")) {
						return new ArrayList<String>();
					} else {
						Map<String, String> node = this.getAllGm();
						if (node.containsKey(uname)) {
							return new ArrayList<String>();
						}
						lidata.add(uname);
					}
				} else { // oem和本部门或其它 判断下一个节点是否和gm节点相同
					Map<String, String> node = this.getAllGm();
					if (node.containsKey(uname)) {
						return new ArrayList<String>();
					}
					lidata.add(uname);
				}
			}
			return lidata;
		} else {
			return new ArrayList<String>();
		}
	}

	// 获取所有gm人员
	private Map<String, String> getAllGm() {
		Map<String, String> data = new HashMap<String, String>();
		boolean exis = jedisCluster.exists("GmAll");
		if (!exis) {
			JSONObject gm = new JSONObject();
			gm.put("lookupType", "PLM_WTC_BPM_NODE_NEWITEM_GM");
			JSONObject gmjson = this.getDic(gm.toJSONString());
			JSONArray dataarray = gmjson.getJSONArray("data");
			if (dataarray.size() > 0) {
				for (int i = 0; i < dataarray.size(); i++) {
					JSONObject jt = dataarray.getJSONObject(i);
					String lookupCode = jt.getString("lookupCode");
					String meaning = jt.getString("meaning");
					if (!data.containsKey(meaning)) {
						data.put(meaning, lookupCode);
					}
				}
			}
			jedisCluster.hmset("GmAll", data);
			jedisCluster.expire("GmAll", 3600 * 12);
			return data;
		} else {
			return jedisCluster.hgetAll("GmAll");
		}

	}

	// 获取指定节点人员信息
	@RequestMapping(method = RequestMethod.POST, value = "findBpmNodeByname")
	public List<String> findBpmNodeByname(String desc) {
		// "http://1002-saaf-api-gateway/api/baseServer/baseWatsonsBpmNodeUserService/findNodeUser";
		JSONObject af = new JSONObject();
		af.put("lookupType", "PLM_PRODUCT_USERSET");
		JSONObject resultJson = this.getDic(af.toJSONString());
		if (resultJson.containsKey("data")) {
			JSONArray data = resultJson.getJSONArray("data");
			if (data.size() == 0) {
				return new ArrayList<String>();
			}
			List<String> lidata = new ArrayList<String>();
			for (int i = 0; i < data.size(); i++) {
				JSONObject jdata = data.getJSONObject(i);
				String uname = jdata.getString("meaning");
				String dc = jdata.getString("description");
				if (dc.equals(desc)) {
					lidata.add(uname);
				}
			}
			return lidata;
		} else {
			return new ArrayList<String>();
		}
	}

	// 待办
	// @RequestMapping(method = RequestMethod.POST, value =
	// "findWastonTodoTasks")
	// public String findWastonTodoTasks(
	// @RequestParam(required = false) String params,
	// @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
	// @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
	// try {
	// JSONObject paramJSON = this.parseObject(params);
	// String result = watsonsFlowListServer
	// .findWastonTodoTasks(paramJSON);
	// return result;
	//
	// } catch (Exception e) {
	// LOGGER.error(e.getMessage(), e);
	// return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败\n" + e,
	// 0, null).toString();
	// }
	// }

	/**
	 * 获取流程历史
	 *
	 * @param params
	 *            processInstanceId:流程实例ID processDefinitionKey 流程KEY
	 *            businessKey 业务主键 ouId 事业部ID responsibilityId 职责ID
	 *            includeActive: 是否包含活动任务
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findWastonHistoricActivities")
	public String findWastonHistoricActivities(@RequestParam String params) {

		try {
			JSONObject paramJSON = this.parseObject(params);
			JSONObject urlParam = new JSONObject(true);
			urlParam.put("Method", "GetTaskFlow");
			urlParam.put("PROCESSNAME", paramJSON.getString("PROCESSNAME"));
			urlParam.put("INCIDENT", paramJSON.getString("INCIDENT"));
			JSONArray array = new JSONArray();
			array = JSONArray.parseArray(this.ExecuteBpm(urlParam));

			for (int i = 0; i < array.size(); i++) {
				JSONObject da = array.getJSONObject(i);
				String status = da.getString("Status"); // 状态
				if (da.containsKey("Step Name")) {
					String stepName = da.getString("Step Name");
					da.remove("Step Name");
					da.put("StepName", stepName);
				}
				if (da.containsKey("Approver")) // 审批人
				{
					String approver = da.getString("Approver");
					if (approver.contains("<br>")) {
						String newapprover = approver.replace(
								"<br><font color='red'>", "").replace(
								"</font>", "");
						da.remove("Approver");
						da.put("Approver", newapprover);
					}
				}
				if (da.containsKey("Start Time")) {
					String StartTime = da.getString("Start Time");
					da.remove("Start Time");
					da.put("StartTime", StartTime);
				}
				if (da.containsKey("End Time")) {
					String EndTime = da.getString("End Time");
					da.remove("End Time");
					da.put("EndTime", EndTime);
				}
				if (da.containsKey("Time Spent")) {
					String TimeSpent = da.getString("Time Spent");
					da.remove("Time Spent");
					da.put("TimeSpent", TimeSpent);
				}

			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					array == null ? 0 : array.size(), array).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败" + e, 0,
					null).toString();
		}
	}

	// /**
	// * 配置邮件发送
	// *
	// * @return
	// */
	// @RequestMapping(method = RequestMethod.POST, value =
	// "sendEmailConfigForNow")
	// public JSONObject sendEmailConfigForNow(@RequestParam String params) {
	//
	// try {
	// JSONObject paramJSON = this.parseObject(params);
	// // 员工号 语言版本 发送标识
	// SaafToolUtils.validateJsonParms(paramJSON, "EMPLOYEE_NO", "LANG",
	// "TYPE");
	// JSONArray array = paramJSON.getJSONArray("TASKS");
	// JSONObject result = new JSONObject();
	// if (CollectionUtils.isEmpty(array)) {
	// // 每日汇总审批
	// result = watsonsFlowListServer.sendEmailConfigForNow(paramJSON);
	// } else {
	// // 接口审批发送
	// result = watsonsFlowListServer
	// .sendEmailConfigForSummary(paramJSON);
	//
	// }
	// // String result =
	// // watsonsFlowListServer.sendEmailConfigForNow(paramJSON);
	// return result;
	// } catch (Exception e) {
	// LOGGER.error(e.getMessage(), e);
	// return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败" + e, 0,
	// null);
	// }
	// }
	//
	// /**
	// * 配置邮件审批
	// *
	// * @return
	// */
	// @RequestMapping(method = RequestMethod.POST, value =
	// "sendEmailConfigForApprove")
	// public String sendEmailConfigForApprove(@RequestParam String params) {
	//
	// try {
	// JSONObject paramJSON = this.parseObject(params);
	//
	// String result = watsonsFlowListServer
	// .sendEmailConfigForApprove(paramJSON);
	// return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", 0,
	// result).toString();
	// } catch (Exception e) {
	// LOGGER.error(e.getMessage(), e);
	// return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败" + e, 0,
	// null).toString();
	// }
	// }

	@RequestMapping(method = RequestMethod.POST, value = "updateStatus")
	public void updateStatus(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmProductHeadService/updateProval",
				jsonObject);
	}

	// 改变补货状态
	@RequestMapping(method = RequestMethod.POST, value = "updateSuppleStatus")
	public void updateSuppleStatus(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmSupplementHeadService/submitWorkflow",
				jsonObject);
	}

	// 获取业务单据详情
	@RequestMapping(method = RequestMethod.POST, value = "getSupplementBysupCode")
	public String getSuplmentBySupCode(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmSupplementHeadService/findPlmsInfoDetail",
				jsonObject);
		return resultJson.toJSONString();
	}

	// 中英文品牌流程
	@RequestMapping(method = RequestMethod.POST, value = "StartBrandEcn")
	public String StartBrandEcn(@RequestParam String params)
			throws ServerException {
		JSONObject jsonParam = JSON.parseObject(params);
		String userName = this.getUserSessionBean().getUserName();
		String action = jsonParam.getString("action");
		String billNo = jsonParam.getString("billNo");
		String orgiNo = billNo.split("-")[0];

		JSONObject param = new JSONObject(true);
		param.put("Method", "SubmitIncident");
		param.put("TASKUSER", userName);
		param.put("PROCESSNAME", "中英文品牌流程");
		param.put("SUMMARY", billNo);
		param.put("ACTION", action);
		if (!jsonParam.containsKey("COMMENTS")) {
			param.put("COMMENTS", "");
		} else {
			param.put("COMMENTS", jsonParam.getString("COMMENTS"));
		}

		if (action.equals("Submit")) {
			param.put("VERSION", "0");
			param.put("INCIDENT", "0");
			param.put("TASKID", "");
			JSONArray userlist = new JSONArray();

//			List<String> user7 = new ArrayList<String>();
//					//this.findBpmNodeByname("BIC");
//			JSONObject data7 = new JSONObject(true);
//			data7.put("STEPNAME", "BIC");
//			data7.put("APPROVER", user7);

			List<String> user8 = this.findBpmNodeByname("GROUPTA");
			JSONObject data8 = new JSONObject(true);
			data8.put("STEPNAME", "TA");
			data8.put("APPROVER", user8);

			//userlist.add(data7);
			userlist.add(data8);

			if (!jsonParam.containsKey("variables")) {
				throw new ServerException("缺少流程参数!");
			}
			JSONArray varry = jsonParam.getJSONArray("variables");
			for (int i = 0; i < varry.size(); i++) {
				JSONObject jt = varry.getJSONObject(i);
				String type = jt.getString("type");
				if (type.equals("string")) {
					String name = jt.getString("name");
					if (name.equals("containMotherCompany")) {
						String value = jt.getString("value");
						if (value.equals("Y")) {
							JSONArray paramlist = new JSONArray();
							JSONObject data = new JSONObject(true);
							data.put("PARMNAME", "containMotherCompany");
							data.put("VALUE", "Y");

							paramlist.add(data);
							param.put("PARMLIST", paramlist);

							List<String> user9 = this.findBpmNodeByname("BA");
							JSONObject data9 = new JSONObject(true);
							data9.put("STEPNAME", "BA");
							data9.put("APPROVER", user9);
							userlist.add(data9);
						}
					}
				}
			}

			param.put("APPROVERLIST", userlist);

		} else if (action.equals("Approval")) {

			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}

			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);

			if (STEPLABEL.equals("提交")) // 重新提交
			{
				// 获取当前用户待办的taskId
				JSONObject pa = new JSONObject(true);
				pa.put("Method", "GetTaskList");
				pa.put("LISTTYPE", "1");
				pa.put("TASKUSER", this.getUserSessionBean().getUserName());
				pa.put("PROCESSLIST", "中英文品牌流程");
				pa.put("SUMMARY", billNo);

				pa.put("FROM", 1);
				pa.put("TO", 10);
				String result = this.ExecuteBpm(pa);

				JSONObject jsonresult = parseObject(result);
				JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");
				for (int i = 0; i < tasklist.size(); i++) {
					JSONObject taskchild = tasklist.getJSONObject(i);
					String step = taskchild.getString("STEPLABEL");
					if (step.equals("提交")) {
						String TASKIDS = taskchild.getString("TASKID");
						param.remove("TASKID");
						param.put("TASKID", TASKIDS);
						break;
					}
				}
				JSONArray userlist = new JSONArray();

				if (!jsonParam.containsKey("variables")) {
					throw new ServerException("缺少流程参数!");
				}
				JSONArray varry = jsonParam.getJSONArray("variables");
				for (int i = 0; i < varry.size(); i++) {
					JSONObject jt = varry.getJSONObject(i);
					String type = jt.getString("type");
					if (type.equals("string")) {
						String name = jt.getString("name");
						if (name.equals("containMotherCompany")) {
							String value = jt.getString("value");
							if (value.equals("Y")) {
								JSONArray paramlist = new JSONArray();
								JSONObject data = new JSONObject(true);
								data.put("PARMNAME", "containMotherCompany");
								data.put("VALUE", "Y");

								paramlist.add(data);
								param.put("PARMLIST", paramlist);

								List<String> user9 = this
										.findBpmNodeByname("BA");
								JSONObject data9 = new JSONObject(true);
								data9.put("STEPNAME", "BA");
								data9.put("APPROVER", user9);
								userlist.add(data9);
								param.put("APPROVERLIST", userlist);
							}else{
								JSONArray paramlist = new JSONArray();
								JSONObject data = new JSONObject(true);
								data.put("PARMNAME", "containMotherCompany");
								data.put("VALUE", "N");

								paramlist.add(data);
								param.put("PARMLIST", paramlist);
							}
						}
					}
				}

			}



		} else if (action.equals("Return") || action.equals("Reject")) {
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);
		}
		String result = this.ExecuteBpm(param);
		JSONObject data = JSON.parseObject(result);
		String messageresult = data.getString("MESSAGE");
		if (data.containsKey("SUCCESSCODE")) {

			if (action.equals("Submit")||jsonParam.getString("STEPLABEL").equals("提交")) {
				Map<String, String> taskInfo = getTaskInfoByUser(billNo, "3",
						"中英文品牌流程", this.getUserSessionBean().getUserName());
				JSONObject brandpa = new JSONObject(true);
				brandpa.put("brandMapId", orgiNo);
				brandpa.put("status", "APPROVING");
				brandpa.put("VERSION", "");
				if (taskInfo.containsKey("TASKID")) {
					brandpa.put("INCIDENT", taskInfo.get("INCIDENT"));
					brandpa.put("TASKID", taskInfo.get("TASKID"));
				} else {
					brandpa.put("INCIDENT", "");
					brandpa.put("TASKID", "");
				}

				brandpa.put("TASKUSER", param.getString("TASKUSER"));
				this.updateBrandStatus(brandpa.toJSONString());
			}  else if (action.equals("Reject")) // 驳回
			{
				JSONObject brandpa = new JSONObject(true);
				brandpa.put("brandMapId", orgiNo);
				brandpa.put("status", "REJECT");
				brandpa.put("INCIDENT", param.getString("INCIDENT"));
				brandpa.put("VERSION", param.getString("VERSION"));
				brandpa.put("TASKID", param.getString("TASKID"));
				brandpa.put("TASKUSER", param.getString("TASKUSER"));

				this.updateBrandStatus(brandpa.toJSONString());
			}

			if (data.containsKey("PROCESS_COMPLETED")) {
				String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
				if (PROCESS_COMPLETED.equals("1")) // 审批完成
				{

					/*PlmBrandMapEntity_HI brandMap = plmBrandMapServer.getById(Integer.valueOf(orgiNo));
					if(null == brandMap) {
						return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未找到mapId为"+orgiNo+"的记录",
								0, null).toString();
					}
					String cname= brandMap.getBrandCnUdaValueDesc();
					String ename= brandMap.getBrandEnUdaValueDesc();
					//审批完后，判断品牌名称是否已存在，如果已存在则不需要再走RMS流程，同时将信息同步到BRAND_INFO表
					PlmBrandInfoEntity_HI bi = plmBrandInfoServer.findBybrandOrCompany(cname,ename,brandMap.getMotherCompanyId(),
							brandMap.getGroupbrandId(), null,Arrays.asList(new String[]{"EFFECTIVE"}));
					if(null != bi) {
						return SToolUtils.convertResultJSONObj(ERROR_STATUS,
								"(品牌管理中)该组合已经存在,不能重复添加!",
								0, null).toString();
					}

					//是否走rms流程
					boolean toRMS = false;
					PlmBrandMapEntity_HI bm = plmBrandMapServer.findBybrandOrCompany(cname,ename,null,
							null, brandMap.getBrandMapId(), Arrays.asList(new String[]{"EFFECTIVE"}));*/


//					if(toRMS) { //走RMS流程
						// 改变业务单据状态
						JSONObject brandpa = new JSONObject(true);
						brandpa.put("brandMapId", orgiNo);
						brandpa.put("status", "RMS_CONFIG");
						brandpa.put("INCIDENT", param.getString("INCIDENT"));
						brandpa.put("VERSION", param.getString("VERSION"));
						brandpa.put("TASKID", param.getString("TASKID"));
						brandpa.put("TASKUSER", param.getString("TASKUSER"));
						this.updateBrandStatus(brandpa.toJSONString());

						JSONObject ja = new JSONObject();
						ja.put("brandMapId", orgiNo);
						// 调用接口
						this.postCsv(ja.toJSONString());
					/*}else {//不走RMS流程，直接生效且同步到brandInfo及修改userBrandMap
						//获取UDA相关信息
						PlmUdaAttributeEntity_HI cnUda = udaAttributeServer.findByUdaDesc(brandMap.getBrandCnUdaValueDesc());
						PlmUdaAttributeEntity_HI enUda = udaAttributeServer.findByUdaDesc(brandMap.getBrandEnUdaValueDesc());
						Integer cnUdaId = null;
						Integer enUdaId = null;
						if(null != cnUda) {
							cnUdaId = cnUda.getUdaId();
						}
						if(null != enUda) {
							enUdaId = enUda.getUdaId();
						}

						brandMap.setBrandCnUdaId(cnUdaId);
						brandMap.setBillStatus("EFFECTIVE");
						brandMap.setBillStatusName("已生效");
						brandMap.setProcessReject(null);
						plmBrandMapServer.update(brandMap);

						PlmBrandInfoEntity_HI brandInfo = new PlmBrandInfoEntity_HI();
					}*/
				}
			}

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			if (action.equals("Submit")) {
				JSONObject brandpa = new JSONObject(true);
				brandpa.put("brandMapId", orgiNo);
				brandpa.put("status", "MAKING");
				brandpa.put("INCIDENT", param.getString("INCIDENT"));
				brandpa.put("VERSION", param.getString("VERSION"));
				brandpa.put("TASKID", param.getString("TASKID"));
				brandpa.put("TASKUSER", param.getString("TASKUSER"));
				this.updateBrandStatus(brandpa.toJSONString());
			}
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}
	}

	// 改变中英文品牌
	@RequestMapping(method = RequestMethod.POST, value = "updaterBrandStatus")
	public void updateBrandStatus(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmBrandMapService/UpdateStatusById",
				jsonObject);
	}

	// 获取任务taskId
	public Map<String, String> getTaskInfoByUser(String billNo,
												 String listtype, String processname, String taskuser)
			throws ServerException {
		JSONObject pa = new JSONObject(true);
		pa.put("Method", "GetTaskList");
		pa.put("LISTTYPE", listtype);
		// if ((processname.equals("供应商资质管理流程") || processname
		// .equals("供应商资质管理修改流程"))
		// && this.getUserSessionBean().getUserType().equals("60")) {
		pa.put("TASKUSER", taskuser);
		// } else {
		// pa.put("TASKUSER", this.getUserSessionBean().getUserName());
		// }

		// String grantinfo = this.findWastonHistoricBycurentUser(
		// "商品新增流程", incident, "grantTo"); //
		// String grantname = grantinfo.split("&&&")[1];// 被委托人
		// if (!userName.equals(grantname)) {
		// param.remove("TASKUSER");
		// param.put("TASKUSER", grantname);
		// }

		pa.put("PROCESSLIST", processname);
		pa.put("SUMMARY", billNo);

		pa.put("FROM", 1);
		pa.put("TO", 10);
		String result = this.ExecuteBpm(pa);
		JSONObject jsonresult = JSON.parseObject(result);

		JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");
		if (tasklist.size() > 0) {
			JSONObject ja = tasklist.getJSONObject(0);
			Map<String, String> data = new HashMap<String, String>();
			data.put("TASKID", ja.getString("TASKID"));
			data.put("INCIDENT", ja.getString("INCIDENT"));
			return data;
		} else {
			return new HashMap<String, String>();
		}

	}

	// 改变group brand,brand 状态
	@RequestMapping(method = RequestMethod.POST, value = "saveConfirmedPlmGroupBrandStatus")
	public void updaterGroupStatus(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmGroupBrandService/saveConfirmedPlmGroupBrandStatus",
				jsonObject);
	}

	// 获取供应商资质组 业务单据信息
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierQaById")
	public String getSupplierQa(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmSupplierQaNonObInfoService/findSupplierQaById",
				jsonObject);
		return resultJson.toJSONString();
	}

	// 改变供应商资质管理状态
	// 改变group brand,brand 状态
	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierQaStatus")
	public void updaterSupplierQaStatus(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmSupplierQaNonObInfoService/savesubmit",
				jsonObject);
	}

	// 委派配置
	@RequestMapping(method = RequestMethod.POST, value = "Assign")
	public String Assign(@RequestParam String params) throws ServerException {
		JSONObject data = JSON.parseObject(params);
		if (!data.containsKey("TASKUSER")) {
			throw new ServerException("缺少流程参数TASKUSER,原任务处理人工号!");
		}

		if (!data.containsKey("ASSIGNTO")) {
			throw new ServerException("缺少流程参数CREATEDBY,操作人工号!");
		}

		if (!data.containsKey("PROCESSLIST")) {
			throw new ServerException("缺少流程参数PROCESSLIST,流程名称!");
		}
		JSONObject param = new JSONObject(true);
		param.put("Method", "Assign");
		String processName = data.getString("PROCESSLIST");
		List<String> process = new ArrayList<String>();
		for (String processobj : processName.split(",")) {
			if (processobj.equals("资质管理修改流程")) {
				processobj = "供应商资质管理修改流程";
			}
			process.add(processobj);
		}
		param.put("PROCESS_NAME", process);
		param.put("TASK_USER", data.getString("TASKUSER")); // 原工号处理人工号
		param.put("ASSIGN_TO", data.getString("ASSIGNTO")); // 委派员工工号
		param.put("START_TIME", data.getString("STARTTIME"));// 时间格式：yyyy-MM-dd
		// HH:mm:ss
		// 委派开始时间
		param.put("END_TIME", data.getString("ENDTIME")); // 时间格式
		// 时间格式：yyyy-MM-dd
		// HH:mm:ss 委派结束时间
		param.put("CREATE_BY", data.getString("CREATEBY")); // 操作人工号
		String result = this.ExecuteBpm(param);
		JSONObject jsonresult = parseObject(result);
		String code = jsonresult.getString("code");
		String messageresult = jsonresult.getString("message");
		if (code.equals("900001")) {
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}

	}

	// 撤销委派
	@RequestMapping(method = RequestMethod.POST, value = "aPointBpm")
	public String aPointBpm(@RequestParam String params) throws ServerException {
		JSONObject data = JSON.parseObject(params);
		if (!data.containsKey("TASKUSER")) {
			throw new ServerException("缺少流程参数TASKUSER,原任务处理人工号!");
		}

		if (!data.containsKey("CREATEBY")) {
			throw new ServerException("缺少流程参数CREATEBY,操作人工号!");
		}
		if (!data.containsKey("PROCESSLIST")) {
			throw new ServerException("缺少流程参数PROCESSLIST,流程名称!");
		}
		JSONObject param = new JSONObject(true);
		param.put("Method", "AssignRecovery");
		String processName = data.getString("PROCESSLIST");
		List<String> process = new ArrayList<String>();
		for (String processobj : processName.split(",")) {
			if (processobj.equals("资质管理修改流程")) {
				processobj = "供应商资质管理修改流程";
			}
			process.add(processobj);
		}
		param.put("PROCESS_NAME", process);
		param.put("TASK_USER", data.getString("TASKUSER"));
		param.put("CREATE_BY", data.getString("CREATEBY"));
		String result = this.ExecuteBpm(param);
		JSONObject jsonresult = parseObject(result);
		String code = jsonresult.getString("code");
		String messageresult = jsonresult.getString("message");
		if (code.equals("900001")) {
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}

	}

	// 查询委派记录
	@RequestMapping(method = RequestMethod.POST, value = "AssignRecord")
	public String AssignRecord(
			@RequestParam String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows)
			throws ServerException {
		JSONObject data = JSON.parseObject(params);
		if (!data.containsKey("CREATEBY")) {
			throw new ServerException("缺少流程参数CREATEBY,操作人工号!");
		}
		if (!data.containsKey("PROCESSLIST")) {
			throw new ServerException("缺少流程参数PROCESSLIST,流程名称!");
		}
		Integer begin = 0;
		Integer end = 0;
		if (pageIndex == 1) {
			begin = 1;
			end = pageRows;
		} else {
			begin = (pageIndex - 1) * pageRows + 1;
			end = (pageIndex - 1) * pageRows + pageRows;
		}

		JSONObject param = new JSONObject(true);

		param.put("Method", "AssignRecord");
		String processName = data.getString("PROCESSLIST");
		List<String> process = new ArrayList<String>();
		for (String processobj : processName.split(",")) {
			process.add(processobj);
		}
		param.put("PROCESS_NAME", process);
		param.put("CREATE_BY", data.getString("CREATEBY"));
		param.put("FROM", begin);
		param.put("TO", end);

		String result = this.ExecuteBpm(param);
		JSONObject jsonresult = parseObject(result);
		Integer total = jsonresult.getInteger("TOTAL");
		List<Object> pageresults = new ArrayList<Object>();
		Pagination<Object> results = new Pagination<Object>();
		results.setPageSize(pageRows);
		results.setCount(total);

		results.setCurIndex(pageIndex);
		Integer pagecount = 0;
		if (total / pageRows == 0) {
			pagecount = total / pageRows;
		} else {
			pagecount = total / pageRows + 1;
		}

		if (pagecount == 0) {
			pagecount = 1;
		}
		results.setPagesCount(pagecount);
		results.setNextIndex(pageIndex + 1);

		JSONArray tasklist = jsonresult.getJSONArray("RECORD_LIST");

		for (int i = 0; i < tasklist.size(); i++) {
			JSONObject taskchild = tasklist.getJSONObject(i);
			pageresults.add(taskchild);
		}

		results.setData(pageresults);
		String resultString = JSON.toJSONString(results);
		JSONObject jsonObject = JSONObject.parseObject(resultString);
		jsonObject.put(SToolUtils.STATUS, "S");
		jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
		jsonObject.put("count", total);
		jsonObject.put("url", "");
		return jsonObject.toString();
	}

	// 查询委派记录 当前任务
	@RequestMapping(method = RequestMethod.POST, value = "AssignRecordCurrent")
	public String AssignRecordCurrent(
			@RequestParam String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows)
			throws ServerException {
		JSONObject data = JSON.parseObject(params);
		if (!data.containsKey("CREATEBY")) {
			throw new ServerException("缺少流程参数CREATEBY,操作人工号!");
		}
		if (!data.containsKey("PROCESSLIST")) {
			throw new ServerException("缺少流程参数PROCESSLIST,流程名称!");
		}
		Integer begin = 0;
		Integer end = 0;
		if (pageIndex == 1) {
			begin = 1;
			end = pageRows;
		} else {
			begin = (pageIndex - 1) * pageRows + 1;
			end = (pageIndex - 1) * pageRows + pageRows;
		}

		JSONObject param = new JSONObject(true);

		param.put("Method", "AssignRecordCurrent");
		String processName = data.getString("PROCESSLIST");
		List<String> process = new ArrayList<String>();
		for (String processobj : processName.split(",")) {
			process.add(processobj);
		}
		param.put("PROCESS_NAME", process);
		param.put("CREATE_BY", data.getString("CREATEBY"));
		param.put("FROM", begin);
		param.put("TO", end);

		String result = this.ExecuteBpm(param);
		JSONObject jsonresult = parseObject(result);
		Integer total = jsonresult.getInteger("TOTAL");
		List<Object> pageresults = new ArrayList<Object>();
		Pagination<Object> results = new Pagination<Object>();
		results.setPageSize(pageRows);
		results.setCount(total);

		results.setCurIndex(pageIndex);
		Integer pagecount = 0;
		if (total / pageRows == 0) {
			pagecount = total / pageRows;
		} else {
			pagecount = total / pageRows + 1;
		}

		if (pagecount == 0) {
			pagecount = 1;
		}
		results.setPagesCount(pagecount);
		results.setNextIndex(pageIndex + 1);

		JSONArray tasklist = jsonresult.getJSONArray("RECORD_LIST");

		for (int i = 0; i < tasklist.size(); i++) {
			JSONObject taskchild = tasklist.getJSONObject(i);
			pageresults.add(taskchild);
		}

		results.setData(pageresults);
		String resultString = JSON.toJSONString(results);
		JSONObject jsonObject = JSONObject.parseObject(resultString);
		jsonObject.put(SToolUtils.STATUS, "S");
		jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
		jsonObject.put("count", total);
		jsonObject.put("url", "");
		return jsonObject.toString();

	}

	// 转派当前任务
	@RequestMapping(method = RequestMethod.POST, value = "AssignCurrent")
	public String AssignCurrent(@RequestParam String params)
			throws ServerException {
		JSONObject data = JSON.parseObject(params);
		if (!data.containsKey("TASKUSER")) {
			throw new ServerException("缺少流程参数TASKUSER,原任务处理人工号!");
		}

		if (!data.containsKey("ASSIGNTO")) {
			throw new ServerException("缺少流程参数CREATEDBY,操作人工号!");
		}

		if (!data.containsKey("PROCESSLIST")) {
			throw new ServerException("缺少流程参数PROCESSLIST,流程名称!");
		}
		JSONObject param = new JSONObject(true);
		param.put("Method", "AssignCurrent");
		String processName = data.getString("PROCESSLIST");
		List<String> process = new ArrayList<String>();
		for (String processobj : processName.split(",")) {
			if (processobj.equals("资质管理修改流程")) {
				processobj = "供应商资质管理修改流程";
			}
			process.add(processobj);
		}
		param.put("PROCESS_NAME", process);
		param.put("TASK_USER", data.getString("TASKUSER")); // 原工号处理人工号
		param.put("ASSIGN_TO", data.getString("ASSIGNTO")); // 委派员工工号
		param.put("CREATE_BY", data.getString("CREATEBY")); // 操作人工号
		String result = this.ExecuteBpm(param);
		JSONObject jsonresult = parseObject(result);
		String code = jsonresult.getString("code");
		String messageresult = jsonresult.getString("message");
		if (code.equals("900001")) {
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}
	}

	// 获取用户详情
	@RequestMapping(method = RequestMethod.POST, value = "getUserDetail")
	public String getUserDetail(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/baseServer/baseUsersService/findPagination",
				jsonObject);
		return resultJson.toJSONString();
	}

	// 中英文品牌传参
	@RequestMapping(method = RequestMethod.POST, value = "postCsv")
	public String postCsv(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmBrandMapService/brandToCsv",
				jsonObject);
		return resultJson.toJSONString();
	}

	// 返回邮件信息内容
	@RequestMapping(method = RequestMethod.POST, value = "getMailInfo")
	@ResponseBody
	public String getMailInfo(@RequestBody String params) {
		JSONObject data = parseObject(params);
		if(data.containsKey("TASKS"))
		{
			return  this.getDailyMailInfo(params);
		}
		String employee = data.getString("EMPLOYEE_NO"); // 审批工号
		String processname = data.getString("PROCESS_NAME"); // 流程名称
		String summary = data.getString("SUMMARY"); // 单据号
		String email = "";
		String ufname = "";
		JSONObject up = new JSONObject();
		up.put("userName", employee);
		String userresult = getUserDetail(up.toJSONString());
		JSONObject uresult = parseObject(userresult);
		JSONArray datas = uresult.getJSONArray("data");
		if (datas.size() > 0) {
			JSONObject reobj = datas.getJSONObject(0);
			email = reobj.getString("email");
			ufname = reobj.getString("userFullName"); // 用户名
		}
		String temp = "{审批节点姓名} 您好:您有一个待办审批:{流程名字},{单据号}。请注意审批。"
				+ "(本邮件由系统自动发送,请不要回复!)";
		String body = temp.replace("{审批节点姓名}", ufname)
				.replace("{流程名字}", processname).replace("{单据号}", summary);
		// TODO 测试用的邮箱
		LOGGER.info(email + "发送人邮箱..");
		JSONArray attchment=new JSONArray();
		if (!StringUtils.isBlank(email)) {
			JSONObject maindata = new JSONObject();
			maindata.put("subject", processname + "待办提醒");
			maindata.put("body", body);
//			maindata.put("mail_to",
//					"Jayden.Li@watsons.com.cn;" +
//							"Judy.Zhou@watsons.com.cn;hans.liu@watsons.com.cn;mary_l.ma@watsons.com.cn;" +
//							"shirley_p.wang@watsons.com.cn;eleen.tan@watsons.com.cn");
			maindata.put("mail_to",email);
			maindata.put("mail_cc", null);
			maindata.put("attachment_path", attchment);
			maindata.put("attachment_name", attchment);
			return maindata.toJSONString();
		} else {
			JSONObject maindata = new JSONObject();
			maindata.put("subject", "");
			maindata.put("body", "");
			maindata.put("mail_to", "");
			maindata.put("mail_cc", "");
			maindata.put("attachment_path", attchment);
			maindata.put("attachment_name", attchment);
			return maindata.toJSONString();
		}

	}

	// 是否授权人用户
	public String findWastonHistoricBycurentUser(String processname,
												 String incident, String type) throws ServerException {
		String username = this.getUserSessionBean().getUserName();
		JSONObject urlParam = new JSONObject(true);
		urlParam.put("Method", "GetTaskFlow");
		urlParam.put("PROCESSNAME", processname);
		urlParam.put("INCIDENT", incident);
		JSONArray array = new JSONArray();
		array = JSONArray.parseArray(this.ExecuteBpm(urlParam));
		Map<String, String> madata = new HashMap<String, String>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject da = array.getJSONObject(i);
			String status = da.getString("Status");

			if (da.containsKey("Approver") && status.equals("处理中")) // 审批人
			{
				String approver = da.getString("Approver");
				if (approver.contains("授权")) {
					String a1 = approver.substring(0, 8);
					Integer index1 = approver.indexOf("由");
					Integer index2 = approver.indexOf("授权");
					String a2 = approver.substring(index1 + 2, index2).split(
							" ")[0];
					if (type.equals("grant")) {
						madata.put(a1, a2);
					} else {
						madata.put(a2, a1);
					}
				}
			}
		}
		if (madata.size() > 0) {
			if (madata.containsKey(username)) {
				JSONObject ja = new JSONObject();
				ja.put("userName", madata.get(username));
				String results = this.getUserDetail(ja.toJSONString());
				JSONObject uresult = parseObject(results);
				JSONArray datas = uresult.getJSONArray("data");
				if (datas.size() > 0) {
					JSONObject reobj = datas.getJSONObject(0);
					String userId = reobj.getString("userId");
					return userId + "&&&" + madata.get(username);
				}
			}
		}
		return String.valueOf(this.getUserSessionBean().getUserId()) + "&&&"
				+ this.getUserSessionBean().getUserName();
	}

	// 商品修改

	@RequestMapping(method = RequestMethod.POST, value = "startUpdateBpm")
	public String startUpdateBpm(@RequestParam String params)
			throws ServerException {
		String userName = "";
		JSONObject jsonParam = JSON.parseObject(params);
		if (jsonParam.containsKey("TASKUSER")) {
			userName = jsonParam.getString("TASKUSER");
		} else {
			userName = this.getUserSessionBean().getUserName();
		}
		String action = jsonParam.getString("action");
		String billNo = jsonParam.getString("billNo");
		String orgiNo = jsonParam.getString("billNo");

		// variables

		if (billNo.length() > 12) {
			orgiNo = billNo.split("-")[0];
		}

		JSONObject param = new JSONObject(true);
		param.put("Method", "SubmitIncident");
		if (jsonParam.containsKey("TASKUSER")) {
			param.put("TASKUSER", jsonParam.getString("TASKUSER"));
		} else {

			param.put("TASKUSER", userName);
		}
		param.put("PROCESSNAME", "商品修改流程");
		param.put("SUMMARY", billNo);
		param.put("ACTION", action);
		if (!jsonParam.containsKey("COMMENTS")) {
			param.put("COMMENTS", "");
		} else {
			param.put("COMMENTS", jsonParam.getString("COMMENTS"));
		}

		if (action.equals("Submit")) // 指定汇报线节点
		{
			if (!jsonParam.containsKey("ecoId")) {
				throw new ServerException("缺少参数ecoId!");
			}

			Integer ecoId = jsonParam.getInteger("ecoId");
			JSONObject mo = new JSONObject();
			mo.put("ecoId", ecoId);
			JSONObject ecodata = this.getModify(mo.toJSONString());
			JSONObject vari = this.getUpdateProductCondition(ecodata
					.toJSONString());
			JSONArray variables = vari.getJSONArray("variables");
			jsonParam.put("variables", variables);

			param.put("VERSION", "0");
			param.put("INCIDENT", "0");
			param.put("TASKID", "");

			param = InitUpdateSet(param, jsonParam);

		} else if (action.equals("Approval")) { // 审批 查找汇报线人员
			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}

			String STEPLABEL = jsonParam.getString("STEPLABEL");
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");

			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);

			if (STEPLABEL.equals("提交")) // 重新提交
			{
				String unameinfo = this.findWastonHistoricBycurentUser(
						"商品修改流程", incident, "grantTo"); //
				String uname = unameinfo.split("&&&")[1];// 被委托人

				// 获取当前用户待办的taskId
				JSONObject pa = new JSONObject(true);
				pa.put("Method", "GetTaskList");
				pa.put("LISTTYPE", "1");
				if (!userName.equals(uname)) {

					pa.put("TASKUSER", uname);
				} else {
					pa.put("TASKUSER", this.getUserSessionBean().getUserName());
				}
				pa.put("PROCESSLIST", "商品修改流程");
				pa.put("SUMMARY", billNo);

				pa.put("FROM", 1);
				pa.put("TO", 10);
				String result = this.ExecuteBpm(pa);

				JSONObject jsonresult = parseObject(result);
				JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");
				for (int i = 0; i < tasklist.size(); i++) {
					JSONObject taskchild = tasklist.getJSONObject(i);
					String step = taskchild.getString("STEPLABEL");
					if (step.equals("提交")) {
						String TASKIDS = taskchild.getString("TASKID");
						param.remove("TASKID");
						param.put("TASKID", TASKIDS);
						break;
					}
				}
				if (!jsonParam.containsKey("ecoId")) {
					throw new ServerException("缺少参数ecoId!");
				}

				Integer ecoId = jsonParam.getInteger("ecoId");
				JSONObject mo = new JSONObject();
				mo.put("ecoId", ecoId);
				JSONObject ecodata = this.getModify(mo.toJSONString());
				JSONObject vari = this.getUpdateProductCondition(ecodata
						.toJSONString());
				JSONArray variables = vari.getJSONArray("variables");
				jsonParam.put("variables", variables);

				// 重新设置 人员节点
				param = InitUpdateSet(param, jsonParam);

				String grantinfo = this.findWastonHistoricBycurentUser(
						"商品修改流程", incident, "grantTo"); //
				String grantname = grantinfo.split("&&&")[1];// 被委托人
				if (!userName.equals(grantname)) {
					param.remove("TASKUSER");
					param.put("TASKUSER", grantname);
				}

				JSONObject status = new JSONObject(true);
				status.put("ecoNo", orgiNo);
				status.put("status", "APPROVING"); // 审批中
				status.put("INCIDENT", incident);
				status.put("VERSION", param.getString("VERSION"));
				status.put("TASKID", param.getString("TASKID"));
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", billNo);
				this.productUpdateStatus(status.toJSONString());

			}

			String nextstepname = "";
			if (STEPLABEL.equals("NON OB QA OFFICER")) {
				nextstepname = "NON OB QA - 预审人1";
			} else if (STEPLABEL.equals("OB QA OFFICER")) {
				nextstepname = "OB QA - 预审人1";
			} else if (STEPLABEL.equals("SUPPLY CHAIN OFFICER")) {
				// nextstepname = "Supply Chain - 预审人1";
			} else if (STEPLABEL.equals("OEM OFFICER")) {
				nextstepname = "OEM - 预审人1";
			} else if (STEPLABEL.equals("GM")) {
				nextstepname = "RMS确认";
			}
			else if(STEPLABEL.equals("TA"))
			{

				String no = billNo.split("-")[0];
				JSONObject ja = new JSONObject();
				ja.put("ecoNo", no);
				List<PlmProductHeadEcoEntity_HI> ecl = plmProductHeadEcoServer
						.findList(ja);
				if (ecl.size() > 0) {
					PlmProductHeadEcoEntity_HI ec = ecl.get(0);
					Integer ecoId = ec.getEcoId();
					JSONObject mo = new JSONObject();
					mo.put("ecoId", ecoId);
					JSONObject ecodata = this.getModify(mo.toJSONString());
					JSONObject vari = this.getUpdateProductCondition(ecodata
							.toJSONString());
					JSONArray variables = vari.getJSONArray("variables");
					for(int i=0;i<variables.size();i++)
					{
						JSONObject qq=variables.getJSONObject(i);
						String name =qq.getString("name");
						if(name.equals("NEEDGM"))
						{
							String value = qq.getString("value");
							if(value.equals("0"))
							{
								nextstepname = "RMS确认";
								break;
							}
						}
					}
				}
			}
			else {
				String data = STEPLABEL.substring(STEPLABEL.length() - 1,
						STEPLABEL.length());
				String data1 = STEPLABEL.substring(0, STEPLABEL.length() - 1);

				if (data.equals("1")) {
					nextstepname = data1 + "2";
				} else if (data.equals("2")) {
					nextstepname = data1 + "3";
				} else if (data.equals("3")) {
					nextstepname = data1 + "4";
				} else if (data.equals("4")) {
					nextstepname = data1 + "5";
				} else if (data.equals("5")) {
					nextstepname = data1 + "6";
				} else if (data.equals("6")) {
					nextstepname = data1 + "7";
				}
			}

			if (!nextstepname.equals("")) {
				JSONObject userstr = new JSONObject(true);
				if (!userName.equals("10000000")) {
					String unameinfo = this.findWastonHistoricBycurentUser(
							"商品修改流程", incident, "grant");
					String uname = unameinfo.split("&&&")[1];
					if (!uname.equals(userName)) {
						String userid = unameinfo.split("&&&")[0];
						userstr.put("userId", new Integer(userid));
					} else {
						userstr.put("userId", this.getUserSessionBean()
								.getUserId());
					}
				}
				if (nextstepname.equals("RMS确认")) {
					JSONArray userlist = new JSONArray();
					List<String> u12= new ArrayList<String>();
					u12.add("10000000");
					JSONObject data12 = new JSONObject(true);
					data12.put("STEPNAME","RMS确认");
					data12.put("APPROVER", u12);
					userlist.add(data12);
					param.put("APPROVERLIST", userlist);

					// 改变业务单据状态
					JSONObject status = new JSONObject(true);
					status.put("ecoNo", orgiNo);
					status.put("status", "RMS_CONFIG"); // RMS待确认
					status.put("INCIDENT", param.getString("INCIDENT"));
					status.put("VERSION", param.getString("VERSION"));
					status.put("TASKID", param.getString("TASKID"));
					status.put("TASKUSER", param.getString("TASKUSER"));
					status.put("ALLNO", billNo);
					this.productUpdateStatus(status.toJSONString());

					JSONObject ja = new JSONObject();
					ja.put("ecoNo", orgiNo);
					List<PlmProductHeadEcoEntity_HI> ecl = plmProductHeadEcoServer
							.findList(ja);

					if(ecl.size()>0)
					{
						PlmProductHeadEcoEntity_HI h=ecl.get(0);
						JSONObject jt=new JSONObject(true);
						jt.put("ecoId",h.getEcoId());

						try {
							iPlmApiServer.updateUdaMethodByEcoId(jt);
						} catch (Exception e) {

							e.printStackTrace();
						}
					}



				} else {
					List<String> ulist = new ArrayList<String>();
					if (nextstepname.contains("NON OB QA")) {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), "NONOBQA");
					} else if (nextstepname.contains("OB QA")) {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), "OBQA");
					} else if (nextstepname.contains("预审人")) {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), "预审人"); // 本部门
					} else if (nextstepname.contains("OEM")) {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), "OEM"); // 本部门
					} else {
						ulist = this.findUserByNextPerson(
								userstr.toJSONString(), ""); // 查找汇报线人员
					}
					if (ulist.size() > 0) {
						JSONArray userlist = new JSONArray();
						JSONObject data2 = new JSONObject(true);
						data2.put("STEPNAME", nextstepname);
						data2.put("APPROVER", ulist);
						userlist.add(data2);
						param.put("APPROVERLIST", userlist);
					}
				} // else

			}

		} else if (action.equals("Return") || action.equals("Reject")) // 退回或拒绝
		{

			if (!jsonParam.containsKey("STEPLABEL")) {
				throw new ServerException("缺少流程参数 STEPLABEL!");
			}
			if (!jsonParam.containsKey("INCIDENT")) {
				throw new ServerException("缺少流程参数 INCIDENT!");
			}
			if (!jsonParam.containsKey("VERSION")) {
				throw new ServerException("缺少流程参数 VERSION!");
			}
			if (!jsonParam.containsKey("TASKID")) {
				throw new ServerException("缺少流程参数 TASKID!");
			}
			String incident = jsonParam.getString("INCIDENT");
			String version = jsonParam.getString("VERSION");
			String taskid = jsonParam.getString("TASKID");
			String STEPLABEL = jsonParam.getString("STEPLABEL");
			param.put("INCIDENT", incident);
			param.put("VERSION", version);
			param.put("TASKID", taskid);
			param.put("STEPLABEL", STEPLABEL);

		}
		System.out.println(param.toJSONString());
		String result = this.ExecuteBpm(param);
		JSONObject data = JSON.parseObject(result);
		// String messagecode = data.getString("SUCCESSCODE");
		String messageresult = data.getString("MESSAGE");
		if (data.containsKey("SUCCESSCODE")) {
			// 提交
			if (action.equals("Submit")) {
				// 根据单据号，当前人 获取我的流程第一条数据 INCIDENT 保存至业务单据
				JSONObject pa = new JSONObject(true);
				pa.put("Method", "GetTaskList");
				pa.put("LISTTYPE", "3");
				pa.put("TASKUSER", this.getUserSessionBean().getUserName());
				pa.put("PROCESSLIST", "商品修改流程");

				pa.put("SUMMARY", billNo);

				pa.put("FROM", 1);
				pa.put("TO", 10);
				String myresult = this.ExecuteBpm(pa);
				JSONObject jsonresult = parseObject(myresult);
				JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");
				String incident = "";
				if (tasklist.size() > 0) {
					JSONObject ja = tasklist.getJSONObject(0);
					incident = ja.getString("INCIDENT");
				}

				JSONObject status = new JSONObject(true);
				status.put("ecoNo", orgiNo);
				status.put("status", "APPROVING"); // 新增审批中
				status.put("INCIDENT", incident);
				status.put("VERSION", "");
				status.put("TASKID", "");
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", "submit");
				this.productUpdateStatus(status.toJSONString());
			} else if (action.equals("Reject")
					&& !param.getString("STEPLABEL").equals("RMS确认")) { // 驳回
				// 改变业务单据状态 已撤回
				JSONObject status = new JSONObject(true);
				status.put("ecoNo", orgiNo);
				status.put("status", "REJECT"); // 已驳回
				status.put("INCIDENT", param.getString("INCIDENT"));
				status.put("VERSION", param.getString("VERSION"));
				status.put("TASKID", param.getString("TASKID"));
				status.put("TASKUSER", param.getString("TASKUSER"));
				status.put("ALLNO", billNo);
				this.productUpdateStatus(status.toJSONString());
			}

			if (data.containsKey("PROCESS_COMPLETED")) {
				String PROCESS_COMPLETED = data.getString("PROCESS_COMPLETED");
				if (PROCESS_COMPLETED.equals("1")) // 审批完成
				{
					// 改变业务单据状态
//					JSONObject status = new JSONObject(true);
//					status.put("ecoNo", orgiNo);
//					status.put("status", "SUCCESS");
//					status.put("INCIDENT", param.getString("INCIDENT"));
//					status.put("VERSION", param.getString("VERSION"));
//					status.put("TASKID", param.getString("TASKID"));
//					status.put("TASKUSER", param.getString("TASKUSER"));
//					status.put("ALLNO", billNo);
//					this.productUpdateStatus(status.toJSONString());
				}
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功",
					result.length(), messageresult).toString();
		} else {
			if (messageresult.equals("未将对象引用设置到对象的实例。")) {
				messageresult = "审批已被处理，请刷新单据状态!";
			}
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "失败",
					result.length(), messageresult).toString();
		}

		// return result;
	}

	// 根据供应商编号查看是否有正在商品使用 该资质组
	@RequestMapping(method = RequestMethod.POST, value = "getProductSupplier")
	public JSONObject getProductSupplier(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmSupplierQaNonObInfoService/getProductSupplier",
				jsonObject);
		return resultJson;
	}

	public static void main(String[] args) throws ServerException {
	}

	// 获取快码信息
	@RequestMapping(method = RequestMethod.POST, value = "getDic")
	public JSONObject getDic(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/baseServer/baseLookupValuesService/findDic",
				jsonObject);
		return resultJson;
	}

	// 查找修改字段
	@RequestMapping(method = RequestMethod.POST, value = "getModify")
	public JSONObject getModify(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmProductModifyCheckService/findProductModifyCheckByEcoId",
				jsonObject);
		return resultJson;
	}

	// 获取修改流程参数
	@RequestMapping(method = RequestMethod.POST, value = "getUpdateProductCondition")
	public JSONObject getUpdateProductCondition(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmProductHeadService/getUpdateProductCondition",
				jsonObject);
		return resultJson;
	}

	// 商品修改状态改变
	@RequestMapping(method = RequestMethod.POST, value = "productUpdateStatus")
	public void productUpdateStatus(String params) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("params", params);
		JSONObject resultJson = getServiceResult(
				configModel.getPlmServer()+"/plmProductHeadEcoService/updateHeadByEcoCode",
				jsonObject);
	}



	// 返回邮件信息内容
	@RequestMapping(method = RequestMethod.POST, value = "getDailyMailInfo")
	@ResponseBody
	public String getDailyMailInfo(@RequestBody String params) {
		JSONObject data = parseObject(params);
		String employee = data.getString("EMPLOYEE_NO"); // 审批工号
		String email = "";
		String ufname = "";
		JSONObject up = new JSONObject();
		up.put("userName", employee);
		String userresult = getUserDetail(up.toJSONString());
		JSONObject uresult = parseObject(userresult);
		JSONArray datas = uresult.getJSONArray("data");
		if (datas.size() > 0) {
			JSONObject reobj = datas.getJSONObject(0);
			email = reobj.getString("email");
			ufname = reobj.getString("userFullName"); // 用户名
		}
		JSONArray TASKS=data.getJSONArray("TASKS");
		int len=TASKS.size();
		String bodys = ufname+ " 您好:您有"+len+ "个待办审批：<br/>";

		for(int i=0;i<TASKS.size();i++)
		{
			JSONObject cu=TASKS.getJSONObject(i);
			String processname=cu.getString("PROCESS_NAME"); //流程名称
			String summary = cu.getString("SUMMARY"); // 单据号
			bodys+=(i+1)+"."+processname+","+summary+"。"+"<a href='[[APPROVAL]]'>审批通过</a>  "+"<a href='[[REJECT]]'>驳回</a>"+"<br/>";

		}
		bodys+="<a href='[[BATCH_APPROVAL]]'>批量审批通过</a> <br/>";
		bodys+= "(本邮件由系统自动发送,请不要回复!)";
		JSONArray attchment=new JSONArray();
		// TODO 测试用的邮箱
		LOGGER.info(email + "发送人邮箱..");
		if (!StringUtils.isBlank(email)) {
			JSONObject maindata = new JSONObject();
			maindata.put("subject",  "审批待办提醒");
			maindata.put("body", bodys);
//			maindata.put("mail_to",
//					"Jayden.Li@watsons.com.cn;Judy.Zhou@watsons.com.cn;" +
//							"hans.liu@watsons.com.cn;mary_l.ma@watsons.com.cn;" +
//							"shirley_p.wang@watsons.com.cn;eleen.tan@watsons.com.cn");
			maindata.put("mail_to",email);
			maindata.put("mail_cc", null);
			maindata.put("attachment_path", attchment);
			maindata.put("attachment_name", attchment);
			return maindata.toJSONString();
		} else {
			JSONObject maindata = new JSONObject();
			maindata.put("subject", "");
			maindata.put("body", "");
			maindata.put("mail_to", "");
			maindata.put("mail_cc", "");
			maindata.put("attachment_path", attchment);
			maindata.put("attachment_name", attchment);
			return maindata.toJSONString();
		}

	}


}
