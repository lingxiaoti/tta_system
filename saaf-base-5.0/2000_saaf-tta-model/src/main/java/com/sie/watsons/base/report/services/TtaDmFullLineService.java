package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmFullLineEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaDmFullLine;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ttaDmFullLineService")
public class TtaDmFullLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDmFullLineService.class);

	@Autowired
	private ITtaDmFullLine ttaDmFullLineServer;

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;


	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaDmFullLineServer;
	}

	/**
	 *
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaDmFullLineEntity_HI_RO> result = ttaDmFullLineServer.findInfo(jsonObject,pageIndex,pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS,"S");
			jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
			return jsonObject.toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "deleteImportInfo")
	public String deleteImportInfo(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			ttaDmFullLineServer.deleteImportInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "findDmCreateInfo")
	public String findDmCreateInfo(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaDmFullLineEntity_HI_RO> result = ttaDmFullLineServer.findDmCreateInfo(jsonObject,pageIndex,pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS,"S");
			jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	@SuppressWarnings("all")
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateDmOrder")
	public String saveOrUpdateDmOrder(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			int userId = getSessionUserId();
			JSONObject jsonObject = ttaDmFullLineServer.saveOrUpdateDmOrder(paramsJSON, userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (Exception e) {
			LOGGER.warn("新增&修改DM信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}

	/**
	 * 功能描述： DM数据导入
	 */
	@RequestMapping(method = RequestMethod.POST,value = "saveImportDmInfo")
	public String saveImportDmInfo(@RequestParam("file") MultipartFile file){
		try{
			UserSessionBean userSessionBean = this.getUserSessionBean();
			JSONObject sessionBean = new JSONObject();
			sessionBean.fluentPut("userId",userSessionBean.getUserId());
			ttaDmFullLineServer.saveImportDMInfo(sessionBean, file);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "文件件上传", 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}