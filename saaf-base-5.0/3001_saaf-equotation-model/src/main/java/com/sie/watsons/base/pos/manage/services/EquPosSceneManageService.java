package com.sie.watsons.base.pos.manage.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.manage.model.entities.readonly.EquPosSceneManageEntity_HI_RO;
import com.sie.watsons.base.pos.manage.model.inter.IEquPosSceneManage;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/equPosSceneManageService")
public class EquPosSceneManageService extends CommonAbstractService {
private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSceneManageService.class);
	@Autowired
	private IEquPosSceneManage equPosSceneManageServer;
	public EquPosSceneManageService() {
		super();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
//		return this.equPosSceneManageServer;
		return null;
	}

	/**
	 * 查询供应商引入场景（分页）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findEquPosSceneManageInfo")
	public String findEquPosSceneManageInfo(@RequestParam(required = false) String params,
											@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSZRCJGL");
			//权限校验end
			Pagination<EquPosSceneManageEntity_HI_RO> infoList = equPosSceneManageServer.findEquPosSceneManageInfo(params, pageIndex, pageRows);
			JSONObject returnJson = JSONObject.parseObject(JSON.toJSONString(infoList));
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("sceneType");
			incomingParam.add("sceneStatus");
			efferentParam.add("sceneTypeName");
			efferentParam.add("sceneStatusName");
			typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
			typeParam.add("EQU_SUPPLIER_SCENE_STATUS");
			JSONArray data = returnJson.getJSONArray("data");
			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
			returnJson.put("data",data);
			returnJson.put("status","S");
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSceneManage")
	public String saveSceneManage(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			equPosSceneManageServer.saveSceneManage(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 删除
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSceneManage")
	public String deleteSceneManage(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			return equPosSceneManageServer.deleteSceneManage(jsonObject, userId);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

//
    /**
     * @param params
     * @description 删除
     */
    @RequestMapping(method = RequestMethod.POST, value = "sumbitSceneManage")
    public String sumbitSceneManage(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            return equPosSceneManageServer.sumbitSceneManage(jsonObject, userId);
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params
     * @description 验证是否有重复类型提交
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSceneManageLine")
    public String findSceneManageLine(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            return equPosSceneManageServer.findSceneManageLine(jsonObject, userId);
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


}
