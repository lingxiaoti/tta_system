package com.sie.watsons.base.product.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.entities.PlmProductBpmPersonEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductBpmPersonEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductBpmPerson;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmProductBpmPersonService")
public class PlmProductBpmPersonService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductBpmPersonService.class);

	@Autowired
	private IPlmProductBpmPerson plmProductBpmPersonServer;

	@Autowired
	private ViewObject<PlmProductBpmPersonEntity_HI> plmProductBpmPersonDAO_HI;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductBpmPersonServer;
	}

	/**
	 * 2018/10/24
	 * 
	 * @Title:
	 * @Description: TODO(获取流程参数节点)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "FindProductProcessList")
	public String findProductPage(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Integer userId = this.getSessionUserId();
			JSONObject jo = new JSONObject();
			jo.put("createdBy", userId);
			List<PlmProductBpmPersonEntity_HI> li = plmProductBpmPersonServer
					.findList(jo);

			if (li.size() == 0) { // 初始化一些数据
				List<PlmProductBpmPersonEntity_HI> data = new ArrayList<PlmProductBpmPersonEntity_HI>();
				PlmProductBpmPersonEntity_HI p2 = new PlmProductBpmPersonEntity_HI();
				p2.setOperatorUserId(userId);
				p2.setCreatedBy(userId);
				p2.setName("OB QA Officer");
				p2.setUserType("78");
				p2.setDeptType("1");

				PlmProductBpmPersonEntity_HI p3 = new PlmProductBpmPersonEntity_HI();
				p3.setOperatorUserId(userId);
				p3.setCreatedBy(userId);
				p3.setName("Supply Manager");
				p3.setUserType("79");
				p3.setDeptType("5");
				PlmProductBpmPersonEntity_HI p4 = new PlmProductBpmPersonEntity_HI();
				p4.setOperatorUserId(userId);
				p4.setCreatedBy(userId);
				p4.setName("OEM Manager");
				p4.setUserType("80");
				p4.setDeptType("3");
				PlmProductBpmPersonEntity_HI p5 = new PlmProductBpmPersonEntity_HI();
				p5.setOperatorUserId(userId);
				p5.setCreatedBy(userId);
				p5.setName("NON OB QA Officer");
				p5.setUserType("75");
				p5.setDeptType("2");
				PlmProductBpmPersonEntity_HI p6 = new PlmProductBpmPersonEntity_HI();
				p6.setOperatorUserId(userId);
				p6.setCreatedBy(userId);
				p6.setName("Promotion");
				p6.setUserType("81");
				p6.setDeptType("4");
				PlmProductBpmPersonEntity_HI p7 = new PlmProductBpmPersonEntity_HI();
				p7.setOperatorUserId(userId);
				p7.setCreatedBy(userId);
				p7.setName("ONLINE OFFICE");
				p7.setUserType("83");
				p7.setDeptType("6");
				// PlmProductBpmPersonEntity_HI p8 = new
				// PlmProductBpmPersonEntity_HI();
				// p8.setOperatorUserId(userId);
				// p8.setCreatedBy(userId);
				// p8.setName("ONLINE QA Officer");
				// p8.setUserType("84");

				// ONLINE TRADING OFFICE
				// PlmProductBpmPersonEntity_HI P10 = new
				// PlmProductBpmPersonEntity_HI();
				// P10.setOperatorUserId(userId);
				// P10.setCreatedBy(userId);
				// P10.setName("ONLINE TRADING OFFICE"); // 线上运营
				// P10.setUserType("94");
				// data.add(p);
				// data.add(p1);
				data.add(p2);
				data.add(p3);
				data.add(p4);
				data.add(p5);
				data.add(p6);
				data.add(p7);
				// data.add(p8);
				// data.add(p9);
				// data.add(P10);
				plmProductBpmPersonDAO_HI.save(data);
			}

			Pagination<PlmProductBpmPersonEntity_HI_RO> results = plmProductBpmPersonServer
					.FindProductProcessList(param, pageIndex, pageRows);
			String resultString = JSON.toJSONString(results);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 更新节点
	/**
	 * 2018/10/24
	 * 
	 * @Title:
	 * @Description: TODO(获取流程参数节点)
	 * @param @param params
	 * @param @param pageIndex
	 * @param @param pageRows
	 * @param @return 参数
	 * @return json字符串
	 * @throws
	 * @author:caojin
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateProcessList")
	public String updateProcessList(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			Integer id = param.getInteger("id");
			String userlist = param.getString("userlist");
			// String name = param.getString("name");

			PlmProductBpmPersonEntity_HI ph = plmProductBpmPersonServer
					.getById(id);
			ph.setUseridList(userlist);
			plmProductBpmPersonServer.update(ph);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
					null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping("/findProccessUsers")
	public String findProccessUsers(
			@RequestParam(required = false) String params,
			@RequestParam(required = false) String messageIndex) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<BaseUsersPerson_HI_RO> proccessUsers = plmProductBpmPersonServer
					.findProccessUsers(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					proccessUsers.size(), proccessUsers).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping("/findProccessUsersByuserId")
	public String findProccessUsersByuserId(
			@RequestParam(required = false) String params,
			@RequestParam(required = false) String messageIndex) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<BaseUsersPerson_HI_RO> proccessUsers = plmProductBpmPersonServer
					.findProccessUsersByuserId(queryParamJSON);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					proccessUsers.size(), proccessUsers).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// findCurenPerson
	@RequestMapping("/findCurenPerson")
	public String findCurenPerson(
			@RequestParam(required = false) String params,
			@RequestParam(required = false) String messageIndex) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<BaseUsersPerson_HI_RO> proccessUsers = plmProductBpmPersonServer
					.findCurenPerson(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					proccessUsers.size(), proccessUsers).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 查找人员
	@RequestMapping("/findUserProcess")
	public String findUserProcess(
			@RequestParam(required = false) String params,
			@RequestParam(required = false) String messageIndex) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<BaseUsersPerson_HI_RO> proccessUsers = plmProductBpmPersonServer
					.findUserProcess(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					proccessUsers.size(), proccessUsers).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

}