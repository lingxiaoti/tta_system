package com.sie.watsons.base.usergroupdeptbrand.services;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.usergroupdeptbrand.model.inter.ITtaUserDataType;

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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ttaUserDataTypeService")
public class TtaUserDataTypeService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaUserDataTypeService.class);

	@Autowired
	private ITtaUserDataType ttaUserDataTypeServer;

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaUserDataTypeServer;
	}

	/**
	 * 批量导入
	 * @param file
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "saveImportInfo")
	public String saveImportInfo(@RequestParam("file") MultipartFile file){
		try{
			String params = "";
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			int size = ttaUserDataTypeServer.saveImportInfo(jsonObject,file,sessionBean);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			jedisCluster.setex("TTA_USER_DATA_TYPE" + getUserSessionBean().getCertificate(),3600,"{status:'E',currentStage:'失败',orderNum:"+"'"+e.getMessage()+"+'}");

			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
			//
			//return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
		}
	}

}