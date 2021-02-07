package com.sie.saaf.deploy.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppInfoEntity_HI;
import com.sie.saaf.deploy.model.entities.readonly.BaseDeployeeAppInfoEntity_HI_RO;
import com.sie.saaf.deploy.model.inter.IBaseDeployeeAppInfo;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deployAppInfoService")
public class BaseDeployAppInfoService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDeployAppInfoService.class);
	
	@Autowired
	private IBaseDeployeeAppInfo baseAppDeployeeInfoServer;

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return baseAppDeployeeInfoServer;
	}

	/**
	 * 查询应用列表（分页）
	 * @author laoqunzhao 2018-08-22
	 * @param params
	 * {
	 * appCode APP编码
	 * appName APP名称
	 * appWapCode 应用编码
	 * appWapName 应用名称
	 * appWapDesc 应用描述
	 * appWapVersion 应用版本
	 * appWapStatus 1.上架  0.下架
	 * appWapDeployeeTimeStart 发布起始时间 yyyy-MM-dd
	 * appWapDeployeeTimeEnd 发布截止时间 yyyy-MM-dd
	 * }
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findDeployApps")
	public String findDeployApps(@RequestParam(required = false) String params,
								@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			if(StringUtils.isBlank(queryParamJSON.getString("deleteFlag"))) {
				queryParamJSON.put("deleteFlag", 0);
			}
			Pagination<BaseDeployeeAppInfoEntity_HI> pagination = baseAppDeployeeInfoServer.findDeployApps(queryParamJSON, pageIndex, pageRows);
			JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
			result.put(STATUS, SUCCESS_STATUS);
			result.put(MSG, "成功");
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 查询用户应用列表，同时查询应用的菜单列表,优先从Redis中查询
	 * @author laoqunzhao 2018-08-24
	 * @param params
	 * {
	 * ouId  OUID
	 * appCode APP编码
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findDeployAppsByUserId")
	public String findDeployAppsByUserId(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "ouId","appCode");
			Integer ouId = paramsJSON.getIntValue("ouId");
			String appCode = paramsJSON.getString("appCode");
			UserSessionBean sessionBean = super.getUserSessionBean();
			baseAppDeployeeInfoServer.checkUserCertificate(ouId, sessionBean.getUserId(), appCode, sessionBean.getCertificate());
			List<BaseDeployeeAppInfoEntity_HI_RO> appInfos = baseAppDeployeeInfoServer
					.findDeployAppsByUserIdInRedis(ouId, sessionBean.getUserId(), appCode);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", appInfos == null?0 : appInfos.size(), appInfos).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 新增&修改应用信息
	 * @author laoqunzhao 2018-08-22
	 * @param params
	 * {
	 * appWapId 主键Id
     * appCode APP编码
	 * appName APP名称
     * appWapCode 应用编码
     * appWapName 应用名称
     * appWapDesc 描述
     * appWapSortId 应用排序
     * appWapVersion 应用版本
     * appPackingVersion 打包版本
     * appWapAccessHomePath 应用首页访问地址
     * appWapFullScreen 是否全屏 Y.是  N.否
     * appWapBackupPath 应用备份地址
     * appWapImagePath 应用图标地址
     * appWapUploadPath 应用发布地址
     * appWapStatus 1.上架  0.下架
     * appWapDeployeeTime 发布时间
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String save(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appCode", "appName", "appWapCode", "appWapName", "appWapSortId", "appWapVersion");
			baseAppDeployeeInfoServer.saveOrUpdate(paramsJSON, this.getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 更新应用状态：上架/下架
	 * @author laoqunzhao 2018-08-22
	 * @param params
	 * {
	 * appWapIds:[] 主键数组
	 * appWapStatus   1.上架  0.下架
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateStatus")
	public String updateStatus(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapIds");
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapStatus");
			baseAppDeployeeInfoServer.updateStatus(paramsJSON, getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 删除应用信息
	 * @author laoqunzhao 2018-08-22
	 * @param params
	 * {
	 * appWapIds:[] 主键数组
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapIds");
			//兼容删除单个应用
			if(!(paramsJSON.get("appWapIds") instanceof JSONArray)){
				paramsJSON.put("appWapIds" , new JSONArray().fluentAdd(paramsJSON.get("appWapIds")));
			}
			baseAppDeployeeInfoServer.delete(paramsJSON, this.getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	

}
