package com.sie.watsons.base.pos.obfile.services;

import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pos.obfile.model.inter.IEquPosZzscObFile;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
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

@RestController
@RequestMapping("/equPosZzscObFileService")
public class EquPosZzscObFileService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscObFileService.class);

	@Autowired
	private IEquPosZzscObFile equPosZzscObFileServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosZzscObFileServer;
	}

	/**
	 * @return java.lang.String
	 * @description   保存
	 **/
	@RequestMapping(method = RequestMethod.POST,value = "saveObFactoryFile")
	public String saveObFactoryFile(){
		try{
			JSONObject jsonObject = equPosZzscObFileServer.saveObFactoryFile();
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonObject).toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		}catch (IllegalStateException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
		}
		catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @return java.lang.String
	 * @description   保存
	 **/
	@RequestMapping(method = RequestMethod.POST,value = "saveFastDFSFile")
	public String saveFastDFSFile(){
		try{
			JSONObject jsonObject = equPosZzscObFileServer.saveFastDFSFile();
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonObject).toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		}catch (IllegalStateException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
		}
		catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}