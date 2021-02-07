package com.sie.watsons.base.ttaImport.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaOiTaxEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaSroiBillLineEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.inter.ITtaSroiBillLine;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ttaSroiBillLineService")
public class TtaSroiBillLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSroiBillLineService.class);

	@Autowired
	private ITtaSroiBillLine ttaSroiBillLineServer;

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSroiBillLineServer;
	}

	/**
	 * 批量导入
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "saveImportSROIInfo")
	public String saveImportTaxInfo(@RequestParam("file") MultipartFile file){
		try{
			String params = "";
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			int size = ttaSroiBillLineServer.saveImportSROIInfo(jsonObject,file,sessionBean);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			jedisCluster.setex(getUserSessionBean().getCertificate(),3600,"{status:'E',currentStage:'失败',orderNum:"+"'"+e.getMessage()+"+'}");
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
			//
			//return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
		}
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
			Pagination<TtaSroiBillLineEntity_HI_RO> result = ttaSroiBillLineServer.findSROIInfo(jsonObject,pageIndex,pageRows);
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
	@RequestMapping(method = RequestMethod.POST,value = "deleteImportSROIInfo")
	public String deleteImportTaxInfo(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			JSONObject result = ttaSroiBillLineServer.deleteImportSROIInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
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
	@RequestMapping(method = RequestMethod.POST,value = "getStatus")
	public String getStatus(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			String str = "{}";
			if(jedisCluster.exists(jsonObject.getString("Certificate"))){
				 str= jedisCluster.get(jsonObject.getString("Certificate"));
			}
			if( "S".equals(JSON.parseObject(str).getString("status"))){
				return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, JSON.parseObject(str)).toString();
			}else if("E".equals(JSON.parseObject(str).getString("status"))){
				return SToolUtils.convertResultJSONObj("E", SUCCESS_MSG, 0, JSON.parseObject(str)).toString();
			}else{
				return SToolUtils.convertResultJSONObj("U", SUCCESS_MSG, 0, JSON.parseObject(str)).toString();
			}
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}