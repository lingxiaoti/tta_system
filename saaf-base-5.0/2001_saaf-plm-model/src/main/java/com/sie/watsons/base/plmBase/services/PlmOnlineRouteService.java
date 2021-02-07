package com.sie.watsons.base.plmBase.services;

import java.rmi.ServerException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.PlmOnlineRouteEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmOnlineSubrouteEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmOnlineRouteEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmOnlineRoute;
import com.sie.watsons.base.plmBase.model.inter.IPlmOnlineSubroute;
import com.sie.watsons.base.product.model.inter.IPlmProductHead;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmOnlineRouteService")
public class PlmOnlineRouteService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmOnlineRouteService.class);
	@Autowired
	private IPlmOnlineRoute plmOnlineRouteServer;

	@Autowired
	private IPlmOnlineSubroute plmOnlineSubrouteServer;

	@Autowired
	private IPlmProductHead plmProductHeadServer;

	public PlmOnlineRouteService() {
		super();
	}

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmOnlineRouteServer;
	}

	/**
	 * 查询
	 * 
	 * @param params
	 *            { teamFrameworkId:框架ID }
	 * @param pageIndex
	 *            当前页码
	 * @param pageRows
	 *            页面行数
	 * @return Pagination
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmOnlineRouteInfo")
	public String findPlmOnlineRouteInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows)
			throws ClassNotFoundException, NoSuchFieldException,
			IllegalAccessException {
		JSONObject queryParamJSON = parseObject(params);

		Pagination<PlmOnlineRouteEntity_HI_RO> dataList = plmOnlineRouteServer
				.findPlmOnlineRouteInfo(queryParamJSON, pageIndex, pageRows);
		ResultUtils.getLookUpValue("PLM_PRODUCT_ONLINETYPE");
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 保存
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "savePlmOnlineRouteInfo")
	public String savePlmOnlineRouteInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);

			if (parseObject.containsKey("plmOnlineSubrouteList")) {
				JSONArray jt = parseObject
						.getJSONArray("plmOnlineSubrouteList");
				for (int i = 0; i < jt.size(); i++) {
					JSONObject jo = jt.getJSONObject(i);
					String subroutename = jo.getString("subrouteName");
					JSONObject jr = new JSONObject();
					jr.put("subrouteName", subroutename);
					jr.put("billStatus", "Y");
					List<PlmOnlineSubrouteEntity_HI> subli = plmOnlineSubrouteServer
							.findList(jr);
					if (jo.containsKey("plmOnlineSubrouteId"))// 修改
					{
						if (subli.size() > 1) {
							throw new ServerException("第" + (i + 1)
									+ "行:子渠道名称'" + subroutename + "'已经存在!");
						}
					} else // 新增
					{
						if (subli.size() > 0) {
							throw new ServerException("第" + (i + 1)
									+ "行:子渠道名称'" + subroutename + "'已经存在!");
						}
					}
				}

			}
			JSONObject jsonObject = (JSONObject) JSONObject
					.toJSON(plmOnlineRouteServer
							.savePlmOnlineRouteInfo(parseObject));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,
					jsonObject).toString();

		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			return getException(e, LOGGER);
		}
	}

	// 删除线上渠道
	/**
	 * @param params
	 * @return java.lang.String
	 * @description 保存
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "deletelmOnlineRouteInfo")
	public String deletePlmOnlineRouteInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			Integer onlineroutid = parseObject.getInteger("plmOnlineRouteId");
			String type = parseObject.getString("type");
			if (type.equals("del")) {
				String sqls = "delete from PlmOnlineSubrouteEntity_HI where plm_online_route_id="
						+ onlineroutid;

				plmProductHeadServer.execute(sqls);

				plmOnlineRouteServer.deleteById(onlineroutid);

			} else if (type.equals("sx")) {
				PlmOnlineRouteEntity_HI r = plmOnlineRouteServer
						.getById(onlineroutid);
				r.setBillStatusName("已失效");
				r.setBillStatus("N");
				plmOnlineRouteServer.update(r);
			} else if (type.equals("vli")) {
				PlmOnlineRouteEntity_HI r = plmOnlineRouteServer
						.getById(onlineroutid);
				r.setBillStatusName("已生效");
				r.setBillStatus("Y");
				plmOnlineRouteServer.update(r);
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null)
					.toString();

		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}
