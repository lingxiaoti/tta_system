package com.sie.watsons.base.api.services;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.sie.saaf.common.services.CommonAbstractService.ERROR_STATUS;
import static com.sie.saaf.common.services.CommonAbstractService.SUCCESS_MSG;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yhg.base.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.sie.watsons.base.api.model.inter.IPlmApi;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * Created by Administrator on 2019/12/16/016.
 */
@RestController
@RequestMapping("/plmApiService")
public class PlmApiService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmApiService.class);
	@Autowired
	private IPlmApi iPlmApiServer;

	public PlmApiService() {
		super();
	}

	/**
	 * 新增ITEM返回接口
	 * 
	 * @param dateParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "addItemReturnMethod")
	public String testAPI(@RequestParam(required = false) String dateParam) {
		try {
			JSONObject jsonStr = new JSONObject();
			SimpleDateFormat sfd = new SimpleDateFormat("yyyyMMdd");
			jsonStr.put("date", sfd.format(new Date()));
			 Calendar calendar = Calendar.getInstance();
			 // 商品接收新增RMS返回接口参数取当天
//			 calendar.add(Calendar.DATE,-1);
			 String date= sfd.format(calendar.getTime());
			 jsonStr.put("date",date);
			 //接收字段日期
			 if(StringUtils.isNotEmpty(dateParam)){
				 jsonStr.put("date",dateParam);
			 }
//			jsonStr.put("date","20200622");
			jsonStr.put("request_id", "all");
			String paramJson = JSONObject.toJSONString(jsonStr);
			iPlmApiServer.addItemReturnMethod(paramJson);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, dateParam)
					.toString();
		} catch (Exception e) {
			LOGGER.error("新增ITEM返回接口:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	/**
	 * 查询近2个月同步的新增单据发邮件
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "toSendEmailforNew")
	public String testAPI() {
		try {
			iPlmApiServer.toSendEmailforNew();
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, "S")
					.toString();
		} catch (Exception e) {
			LOGGER.error("新增ITEM返回接口:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}


	/**
	 * （UDA，售价，成本）修改接口
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateUdaMethod")
	public String updateUdaMethod(@RequestParam(required = true) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
//			iPlmApiServer.updateUdaMethod(queryParamJSON);
			iPlmApiServer.updateUdaMethodByEcoId(queryParamJSON);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, queryParamJSON)
					.toString();
		} catch (Exception e) {
			LOGGER.error("（UDA，售价，成本）:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	/**
	 * 修改ITEM属性返回接口
	 * 

	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateItemPropertyReturns")
	public String updateItemPropertyReturns(
			@RequestParam(required = false) String dateParam
	) {
		try {
			JSONObject queryParamJSON = new JSONObject();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//			String date = dateFormat.format(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE,-1);
			String date= dateFormat.format(calendar.getTime());

			if(!StringUtils.isEmpty(dateParam)){
				queryParamJSON.put("date", dateParam);
			}else {
				queryParamJSON.put("date", date);
			}
//			queryParamJSON.put("date", "20200624");
			iPlmApiServer.updateItemPropertyReturns(queryParamJSON);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, queryParamJSON)
					.toString();
		} catch (Exception e) {
			LOGGER.error(" 修改ITEM属性返回接口:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	/**
	 * UDA属性同步
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "udaAttributeSyn")
	public String udaAttributeSyn(@RequestParam(required = false) String params) {
		try {
			params = "{}";
			JSONObject queryParamJSON = parseObject(params);
			iPlmApiServer.udaAttributeSyn(queryParamJSON);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, params)
					.toString();
		} catch (Exception e) {
			LOGGER.error(" UDA属性同步:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	/**
	 * 部门及分类编码传输
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "depClasCode")
	public String depClasCode(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			iPlmApiServer.depClasCode(queryParamJSON);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, params)
					.toString();
		} catch (Exception e) {
			LOGGER.error(" 部门及分类编码传输:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	/**
	 * 地点清单查询接口
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "siteListingMethod")
	public String siteListingMethod(@RequestParam(required = true) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			JSONObject j = iPlmApiServer.siteListingMethod(queryParamJSON
					.getInteger("locId"));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, j)
					.toString();
		} catch (Exception e) {
			LOGGER.error(" 地点清单查询接口:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	/**
	 * 商品售价区域接口
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "commodityPriceArea")
	public String commodityPriceArea(
			@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			iPlmApiServer.commodityPriceArea(queryParamJSON);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, params)
					.toString();
		} catch (Exception e) {
			LOGGER.error(" 地点清单查询接口:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	/**
	 * 获取货品数据
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "obtainGoodsData")
	public String obtainGoodsData(@RequestParam(required = true) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			JSONObject json = iPlmApiServer.obtainGoodsData(queryParamJSON);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, json)
					.toString();
		} catch (Exception e) {
			LOGGER.error(" 获取货品数据:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	/**
	 * 获取促销数据
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "gainSalesOutlets")
	public String gainSalesOutlets(@RequestParam(required = true) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			JSONObject json = iPlmApiServer.gainSalesOutlets(queryParamJSON);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, json)
					.toString();
		} catch (Exception e) {
			LOGGER.error(" 获取促销数据:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	/**
	 * 获取促销门店
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "gainSalesOutletsShop")
	public String gainSalesOutletsShop(
			@RequestParam(required = true) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			JSONObject json = iPlmApiServer
					.gainSalesOutletsShop(queryParamJSON);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, json)
					.toString();
		} catch (Exception e) {
			LOGGER.error(" 获取促销门店:{}-{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "getUdaByudaId")
	public String getUdaByudaId(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<Object> json = iPlmApiServer
					.getUdaAttributeByUdaId(queryParamJSON);

			List<Object> pageresults = new ArrayList<Object>();

			Pagination<Object> results = new Pagination<Object>();

			results.setPageSize(pageRows);
			results.setCount(json.size());

			results.setCurIndex(pageIndex);
			Integer pagecount = 0;
			if (json.size() / pageRows == 0) {
				pagecount = json.size() / pageRows;
			} else {
				pagecount = json.size() / pageRows + 1;
			}

			if (pagecount == 0) {
				pagecount = 1;
			}
			results.setPagesCount(pagecount);
			results.setNextIndex(pageIndex + 1);

			Integer first = (pageIndex - 1) * pageRows;
			Integer end = first + pageRows - 1;
			if (end > json.size() - 1) {
				end = json.size() - 1;
			}
			for (int i = first; i <= end; i++) {
				pageresults.add(json.get(i));
			}
			results.setData(pageresults);

			String resultString = JSON.toJSONString(results);

			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			jsonObject.put("count", json.size());
			return jsonObject.toString();

		} catch (Exception e) {
			LOGGER.error(" 获取motherCompany", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}

	// 根据名称查询地点清单
	@RequestMapping(method = RequestMethod.POST, value = "getLocByDescName")
	public String getLocByDescName(

			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<Object> json = iPlmApiServer.getLocByDescName(queryParamJSON);

			List<Object> pageresults = new ArrayList<Object>();

			Pagination<Object> results = new Pagination<Object>();

			results.setPageSize(pageRows);
			results.setCount(json.size());

			results.setCurIndex(pageIndex);
			Integer pagecount = 0;
			if (json.size() / pageRows == 0) {
				pagecount = json.size() / pageRows;
			} else {
				pagecount = json.size() / pageRows + 1;
			}

			if (pagecount == 0) {
				pagecount = 1;
			}
			results.setPagesCount(pagecount);
			results.setNextIndex(pageIndex + 1);

			Integer first = (pageIndex - 1) * pageRows;
			Integer end = first + pageRows - 1;
			if (end > json.size() - 1) {
				end = json.size() - 1;
			}
			for (int i = first; i <= end; i++) {
				pageresults.add(json.get(i));
			}
			results.setData(pageresults);

			String resultString = JSON.toJSONString(results);

			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			jsonObject.put("count", json.size());
			return jsonObject.toString();

		} catch (Exception e) {
			LOGGER.error(" 获取地点清单", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					Throwables.getRootCause(e).getMessage(), 0, null)
					.toString();
		}
	}
	@RequestMapping(method = RequestMethod.POST, value = "getBpmUrl")
	public String getBpmUrl()
	{
		return iPlmApiServer.getBpmUrl();
	}

}
