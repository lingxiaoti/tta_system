package com.sie.saaf.base.sso.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.sso.model.entities.BaseFunctionCollectionEntity_HI;
import com.sie.saaf.base.sso.model.entities.BaseFunctionCollectionUserEntity_HI;
import com.sie.saaf.base.sso.model.entities.readonly.BaseFunctionCollectionEntity_HI_RO;
import com.sie.saaf.base.sso.model.entities.readonly.BaseSystemSsoRepEntity_HI_RO;
import com.sie.saaf.base.sso.model.inter.IBaseFunctionCollection;
import com.sie.saaf.base.sso.model.inter.IBaseFunctionCollectionUser;
import com.sie.saaf.base.sso.model.inter.IBaseResponsibilitySystem;
import com.sie.saaf.base.user.model.entities.readonly.BaseMenuRoleEntity_HI_RO;
import com.sie.saaf.common.bean.ResponsibilityBean;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author huangtao
 */

@RestController
@RequestMapping("/baseFunctionCollectionService")
public class BaseFunctionCollectionService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(BaseFunctionCollectionService.class);

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    @Autowired
    private IBaseFunctionCollection baseFunctionCollectionServer;

    @Autowired
    private IBaseResponsibilitySystem baseResponsibilitySystemServer;

    @Autowired
    private IBaseFunctionCollectionUser baseFunctionCollectionUserServer;


    /**
     * @param params    {
     *                  functionCollectionId;//主键
     *                  collectionType; //收藏类型 (外部菜单/内部菜单)
     *                  functionName; //菜单名称
     *                  functionUrl; //菜单url
     *                  requestMethod; //请求方式 get/post
     *                  menuId; //菜单id
     *                  respId; //职责id
     *                  systemCode; //系统编码
     *                  systemName; //系统名称
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "-1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "-1") Integer pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            jsonObject= SaafToolUtils.cleanNull(jsonObject,"requestMethod","systemName");
            Pagination<BaseFunctionCollectionEntity_HI_RO> result = baseFunctionCollectionServer.findFunctionCollections(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询用户可访问的快捷菜单
     *
     * @param params {
     *               addFlag:值为true时  会过滤调已经绑定过的快捷菜单
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUserCollection")
    public String findUserCollection(@RequestParam(required = false) String params) {
        try {

            UserSessionBean userSessionBean = getUserSessionBean();
            Assert.notNull(userSessionBean, "请重新登录");

            JSONObject paramJson = new JSONObject();
            if (StringUtils.isNotBlank(params))
                paramJson = JSON.parseObject(params);

            //通过职责查处允许访问的快捷菜单
            List<BaseSystemSsoRepEntity_HI_RO> systemSsoRoles;
            if ("Y".equals(userSessionBean.getIsadmin())) {
                systemSsoRoles = baseResponsibilitySystemServer.findSystemBySuperAdmin();
            } else {
                List<ResponsibilityBean> responsibilityBeans= userSessionBean.getUserRespList();
                Set<Integer> resps = new HashSet<>();
                for (int i = 0; i < responsibilityBeans.size(); i++) {
                    ResponsibilityBean responsibilityBean = responsibilityBeans.get(i);
                    resps.add(responsibilityBean.getResponsibilityId());
                }
                systemSsoRoles = baseResponsibilitySystemServer.findSystemByResponsibilityId(resps);
            }

            StringBuilder systems = new StringBuilder();
            for (BaseSystemSsoRepEntity_HI_RO obj : systemSsoRoles) {
                systems.append(obj.getSystemCode());
                if (systemSsoRoles.indexOf(obj) != systemSsoRoles.size() - 1)
                    systems.append(",");
            }

            if (systems.length() != 0 && !"Y".equals(getUserSessionBean().getIsadmin()))
                paramJson.put("systemCode_in", systems.toString());

            //过滤已添加过的快捷菜单(功能弃用)
//            paramJson.fluentPut("userId", getSessionUserId());
//            if ("true".equals(paramJson.getString("addFlag"))) {
//                List<BaseFunctionCollectionEntity_HI_RO> collection = baseFunctionCollectionServer.findUserCollection(paramJson);
//                StringBuilder bindedSystems = new StringBuilder();
//                for (BaseFunctionCollectionEntity_HI_RO obj : collection) {
//                    bindedSystems.append(obj.getFunctionCollectionId());
//                    if (collection.indexOf(obj) != collection.size() - 1)
//                        bindedSystems.append(",");
//                }
//                paramJson.remove("userId");
//                if (bindedSystems.length() != 0)
//                    paramJson.put("functionCollectionId_notin", bindedSystems.toString());
//            }

            List<BaseFunctionCollectionEntity_HI_RO> result = baseFunctionCollectionServer.findUserCollection(paramJson);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, result.size(), result).toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    /**
     * @return
     */
    @RequestMapping(value = "findMenuList", method = RequestMethod.POST)
    public String findMenuList() {
        try {
            UserSessionBean userSessionBean = getUserSessionBean();
            JSONArray array = userSessionBean.getRoles();
            if (array.size() == 0 && !"Y".equals(userSessionBean.getIsadmin()))
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
            StringBuilder roles = new StringBuilder();
            for (int i = 0; i < array.size(); i++) {
                JSONObject tmpJson = array.getJSONObject(i);
                roles.append(tmpJson.getString("roleId"));
                if (i == array.size() - 1)
                    break;
                roles.append(",");
            }
            List<BaseMenuRoleEntity_HI_RO> result = baseFunctionCollectionServer.findPopularMenu(userSessionBean, roles.toString());
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, result.size(), result).toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    /**
     * @param params {
     *               functionCollectionId: 快捷菜单id  ，多个id用逗号间隔
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "addCollection")
    public String addCollection(@RequestParam(required = true) String params) {
        try {
            UserSessionBean userSessionBean = getUserSessionBean();
            Assert.notNull(userSessionBean, "请重新登录");
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("functionCollectionId").split(",");
            List<BaseFunctionCollectionUserEntity_HI> list = baseFunctionCollectionUserServer.saveBaseFunctionCollectionUserInfo(ids, getSessionUserId());
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, list).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    /**
     * @param params JSON参数，对象属性的JSON格式
     *               {
     *               functionCollectionId;//主键
     *               collectionType; //收藏类型 (外部菜单/内部菜单)
     *               functionName; //菜单名称
     *               functionUrl; //菜单url
     *               requestMethod; //请求方式 get/post
     *               menuId; //菜单id
     *               respId; //职责id
     *               systemCode; //系统编码
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            BaseFunctionCollectionEntity_HI instance = baseFunctionCollectionServer.saveOrUpdate(jsonObject, getSessionUserId());
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params-id 菜单用户关系表主键 functionCollectionUserId
     * @description 删除用户绑定的快捷菜单
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteUserCollection")
    public String deleteUserCollection(@RequestParam(required = true) String params) {
        try {

            JSONObject jsonObject = JSON.parseObject(params);
            if (StringUtils.isBlank(jsonObject.getString("id"))) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            String[] ids = jsonObject.getString("id").split(",");
            baseFunctionCollectionUserServer.delete(ids);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }


    }


    /**
     * @param params-id 主键
     * @description 删除配置的快捷菜单
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        try {

            JSONObject jsonObject = JSON.parseObject(params);
            if (StringUtils.isBlank(jsonObject.getString("id"))) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            String[] ids = jsonObject.getString("id").split(",");
            baseFunctionCollectionServer.delete(ids);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params JSON参数，对象属性的JSON格式
     *               {
     *               respId; //职责id
     *               }
     * @return
     * @author yuzhenli
     * @description 查询内部收藏菜单
     */
    @RequestMapping(method = RequestMethod.POST, value = "findInCollection")
    public String findInCollection(@RequestParam(required = false) String params) {
        try {
            UserSessionBean userSessionBean = getUserSessionBean();
            Assert.notNull(userSessionBean, "请重新登录");
            JSONObject paramJson = JSON.parseObject(params);
            List<BaseFunctionCollectionEntity_HI_RO> result = baseFunctionCollectionServer.findInCollection(paramJson.fluentPut("userId", getSessionUserId()));
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, result.size(), result).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params JSON参数，对象属性的JSON格式
     *               {
     *               menuIds; //收藏菜单id  ，多个id用逗号间隔
     *               systemCode; //系统编码
     *               respId;//职责Id
     *               }
     * @return
     * @author yuzhenli
     */
    @RequestMapping(method = RequestMethod.POST, value = "addInCollection")
    public String addInCollection(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            if (StringUtils.isBlank(jsonObject.getString("menuIds"))) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:menuId", 0, null).toString();
            }
            String[] ids = jsonObject.getString("menuIds").split(",");
            Integer respId = jsonObject.getInteger("respId");
            String systemCode = jsonObject.getString("systemCode");
            baseFunctionCollectionServer.saveInCollection(ids, getSessionUserId(), respId, systemCode);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params-functionCollectionId 主键
     * @description 删除收藏夹菜单
     * @author yuzhenli
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteInCollection")
    public String deleteInCollection(@RequestParam(required = false) String params) {
        try {

            JSONObject jsonObject = JSON.parseObject(params);
            Integer functionCollectionId = jsonObject.getInteger("functionCollectionId");
            if (StringUtils.isBlank(functionCollectionId + "")) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            baseFunctionCollectionServer.deleteInCollection(functionCollectionId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}
