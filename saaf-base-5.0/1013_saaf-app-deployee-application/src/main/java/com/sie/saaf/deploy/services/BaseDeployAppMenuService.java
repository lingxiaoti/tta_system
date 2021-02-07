package com.sie.saaf.deploy.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppMenuEntity_HI;
import com.sie.saaf.deploy.model.inter.IBaseDeployeeAppInfo;
import com.sie.saaf.deploy.model.inter.IBaseDeployeeAppMenu;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/deployAppMenuService")
public class BaseDeployAppMenuService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDeployAppMenuService.class);

	@Autowired
	private IBaseDeployeeAppMenu baseAppDeployeeMenuServer;

	@Autowired
	private IBaseDeployeeAppInfo baseAppDeployeeInfoServer;


	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return baseAppDeployeeMenuServer;
	}
	
	
	/**
	 * 查询应用菜单（分页）
	 * @param params
	 * {
	 * appCode 应用编码
	 * appWapId 应用Id
	 * appMenuCode 菜单编码
	 * appMenuName 菜单名称
	 * }
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findAppMenus")
	public String findAppDeployMenu(@RequestParam(required = false) String params,
								    @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			if(StringUtils.isBlank(queryParamJSON.getString("deleteFlag"))) {
				queryParamJSON.put("deleteFlag", 0);
			}
			Pagination<BaseDeployeeAppMenuEntity_HI> pagination = baseAppDeployeeMenuServer.findAppMenus(queryParamJSON, pageIndex, pageRows);
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
	 * 保存应用菜单
	 * @param params
	 * {
	 * appWapId 应用ID
	 * dataTable
	 * [{
	 * appMenuId 菜单ID
	 * appMenuName app菜单名称
	 * appMenuCode app菜单编码
	 * appMenuImagePath app菜单图标
	 * appMenuUrl app菜单访问地址
	 * appMenuSort app菜单排序
	 * appDefaultDisplay 默认显示在用户常用收藏栏,1表示显示 0表示不显示 默认值为0
	 * }]
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String save(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapId", "dataTable");
			List<String> codes = new ArrayList<String>();
			List<String> names = new ArrayList<String>();
			JSONArray menusJSON = paramsJSON.getJSONArray("dataTable");
			for(int i=0; i<menusJSON.size(); i++){
				JSONObject menuJSON = menusJSON.getJSONObject(i);
				SaafToolUtils.validateJsonParms(menuJSON, "appMenuName", "appMenuCode", "appMenuImagePath", "appMenuUrl", "appMenuSort");
				if(codes.contains(menuJSON.getString("appMenuCode")) || names.contains(menuJSON.getString("appMenuName"))){
					return SToolUtils.convertResultJSONObj(ERROR_STATUS, "菜单不可重复！", 0, null).toString();
				}
				codes.add(menuJSON.getString("appMenuCode"));
				names.add(menuJSON.getString("appMenuName"));
			}
			baseAppDeployeeMenuServer.saveOrUpdate(paramsJSON, this.getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 删除应用菜单信息
	 * @param params
	 * {
	 * appMenuIds:[] 主键数组
	 * }
	 */ 
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			baseAppDeployeeMenuServer.delete(paramsJSON, this.getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查看我最近使用的菜单
	 * @author laoqunzhao 2018-08-24
	 * @param params
	 * {
	 * ouId  OUID
	 * appCode APP编码
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getMyLatestMenus")
	public String getMyLatestMenus(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "ouId", "appCode");
			Integer ouId = paramsJSON.getIntValue("ouId");
			String appCode = paramsJSON.getString("appCode");
			UserSessionBean sessionBean = super.getUserSessionBean();
			baseAppDeployeeInfoServer.checkUserCertificate(ouId, sessionBean.getUserId(), appCode, sessionBean.getCertificate());
			List<BaseDeployeeAppMenuEntity_HI> myLatestMenus = baseAppDeployeeMenuServer
					.getMyLatestMenus(ouId, sessionBean.getUserId(), appCode);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", myLatestMenus == null?0 : myLatestMenus.size(), myLatestMenus).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	
	/**
	 * 添加最近使用的菜单
	 * @param params
	 * {
	 * ouId  OUID
	 * appCode APP编码
	 * appMenuCode: 菜单编码
	 * }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "addMyLatestMenu")
	public String addMyLatestMenu(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "ouId");
			SaafToolUtils.validateJsonParms(paramsJSON, "appCode");
			SaafToolUtils.validateJsonParms(paramsJSON, "appMenuCode");
			int ouId = paramsJSON.getIntValue("ouId");
			String appCode = paramsJSON.getString("appCode");
			String appMenuCode = paramsJSON.getString("appMenuCode");
			baseAppDeployeeMenuServer.addMyLatestMenu(ouId, getSessionUserId(), appCode, appMenuCode);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	
	/**
	 * 查询我的收藏列表
	 * @author laoqunzhao 2018-08-24
	 * @param params
	 * {
	 * ouId  OUID
	 * appCode APP编码
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getMyFavoriteMenus")
	public String getMyFavoriteMenus(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "ouId");
			SaafToolUtils.validateJsonParms(paramsJSON, "appCode");
			Integer ouId = paramsJSON.getIntValue("ouId");
			String appCode = paramsJSON.getString("appCode");
			UserSessionBean sessionBean = super.getUserSessionBean();
			baseAppDeployeeInfoServer.checkUserCertificate(ouId, sessionBean.getUserId(), appCode, sessionBean.getCertificate());
			List<BaseDeployeeAppMenuEntity_HI> myLatestMenus = baseAppDeployeeMenuServer.getMyFavoriteMenus(
					ouId, sessionBean.getUserId(), appCode);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", myLatestMenus == null?0 : myLatestMenus.size(), myLatestMenus).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 编辑我的收藏
	 * @param params
	 * {
	 * ouId  OUID
	 * appCode APP编码
	 * appMenuCodes 收藏的菜单CODE数组[]
	 * }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "editMyFavoriteMenus")
	public String editMyFavoriteMenus(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "ouId");
			SaafToolUtils.validateJsonParms(paramsJSON, "appCode");
			SaafToolUtils.validateJsonParms(paramsJSON, "appMenuCodes");
			paramsJSON.put("userId", getSessionUserId());
			baseAppDeployeeMenuServer.resetMyFavoriteMenus(paramsJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	
}
