package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseMenuEntity_HI;
import com.sie.saaf.base.user.model.inter.server.BaseAccreditServer;
import com.sie.saaf.common.bean.*;
import com.sie.saaf.common.exception.PermissionException;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.server.BaseAccreditCacheServer;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.*;

/**
 * 权限控制
 *
 * @author huangtao
 * @createTime 2018年7月11日 10:09:09
 */
@RestController
@RequestMapping("/baseAccreditService")
public class BaseAccreditService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(BaseAccreditService.class);

    @Autowired
    private BaseAccreditServer baseAccreditServer;

    @Autowired
    private IBaseAccreditCache baseAccreditCacheServer;

    @Autowired
    private JedisCluster jedisCluster;



    @RequestMapping(method = RequestMethod.POST, value = "getRespInfo", produces = "application/json")
    public String getRespInfo(String params){

        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "respId","userId");
            ResponsibilityBean responsibilityBean= baseAccreditCacheServer.findResp(jsonObject.getInteger("userId"),jsonObject.getInteger("respId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, responsibilityBean).toJSONString();
        } catch (PermissionException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "网络异常，请稍后再试", 0, null).toString();
        }
    }



    /**
     * 查询菜单下的职责
     *
     * @param params {
     *               menuCode: 菜单编码
     *               }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findRespByMenuCode", produces = "application/json")
    public String findRespByMenuCode(String params) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "menuCode");

            UserSessionBean userSessionBean=getUserSessionBean();
            Integer userId=StringUtils.isBlank(jsonObject.getString("userId"))?userSessionBean.getUserId():jsonObject.getInteger("userId");
            String val=jedisCluster.get(BaseAccreditCacheServer.PERMISSION_CACHE_UPDATE_LOCK+userId);
            List<ResponsibilityBean> list;
            if (StringUtils.isBlank(val)){
               list= baseAccreditServer.findMenuResp(userId, jsonObject.getString("menuCode"));
            }else {
                //缓存更新时直接查询数据库
                log.warn("用户[{}]权限缓存更新中，从数据库查询用户菜单职责数据",userId);
                list=baseAccreditServer.findMenuRespFromDb(userId,jsonObject.getString("menuCode"));
            }
            //职位信息
            List<PositionBean> positionBeans=userSessionBean.getPositionList();
            Map<Integer,PositionBean> positionBeanMap=new HashMap<>();
            if (positionBeans!=null){
                for (PositionBean positionBean:positionBeans){
                    boolean contain=positionBeanMap.containsKey(positionBean.getOrgId());
                    if (!contain || (contain && Objects.equals(positionBean.getPrimaryFlag(),"Y")))
                        positionBeanMap.put(positionBean.getOrgId(),positionBean);
                }
            }
            //通过ou 筛选职责
            for (Iterator<ResponsibilityBean> iterator = list.iterator(); iterator.hasNext();){
                ResponsibilityBean responsibilityBean=iterator.next();
                ResponsibilityBean respBean= baseAccreditServer.getRespById(userId,responsibilityBean.getResponsibilityId());
                if (StringUtils.isNotBlank(jsonObject.getString("orgId")) && (respBean.getOrgBean()==null || !Objects.equals(respBean.getOrgBean().getOrgId(),jsonObject.getInteger("orgId")))){
                    iterator.remove();
                    continue;
                }
                if (respBean!=null){
                    responsibilityBean.setOrgBean(respBean.getOrgBean());
                    responsibilityBean.setProFileBeans(respBean.getProFileBeans());
                    responsibilityBean.setPositionBean(( respBean.getOrgBean()!=null && respBean.getOrgBean().getOrgId()!=null)?positionBeanMap.get(responsibilityBean.getOrgBean().getOrgId()):null);
                }
            }
            Collections.sort(list);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, list.size(), list).toJSONString();
        } catch (PermissionException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "网络异常，请稍后再试", 0, null).toString();
        }
    }


    /**
     * 查询菜单树
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findMenuInfo", produces = "application/json")
    public String findMenuInfo(String params) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "systemCode");
            UserSessionBean userSessionBean=getUserSessionBean();
            String val=jedisCluster.get(BaseAccreditCacheServer.PERMISSION_CACHE_UPDATE_LOCK+userSessionBean.getUserId());
            List<MenuInfoBean> list =new ArrayList<>();
            if (StringUtils.isBlank(val)){
                list = baseAccreditServer.findMenuInfo(userSessionBean.getUserId(), jsonObject.getString("systemCode"));
            }else {
                //缓存更新时直接查询数据库
                log.warn("用户[{}]权限缓存更新中，从数据库查询用户菜单数据",userSessionBean.getUserId());
                List<BaseMenuEntity_HI>  menuList= baseAccreditServer.findMenuInfoFromDb(userSessionBean.getUserId(),jsonObject.getString("systemCode"));
                for (Iterator<BaseMenuEntity_HI> iterator=menuList.iterator();iterator.hasNext();){
                    BaseMenuEntity_HI obj=iterator.next();
                    MenuInfoBean menuInfoBean = new MenuInfoBean();
                    BeanUtils.copyProperties(obj, menuInfoBean);
                    list.add(menuInfoBean);
                }
            }
            //list.removeIf(item -> item == null || !Objects.equals(item.getFromType(), "pc"));
            
            Collections.sort(list);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, list.size(), list).toJSONString();
        } catch (PermissionException | IllegalArgumentException e) {
            log.warn(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "网络异常，请稍后再试", 0, null).toString();
        }

    }


    /**
     * 切换职责
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "changeResp", produces = "application/json")
    public String changeResp(String params) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "menuCode", "responsibilityId");
            baseAccreditServer.setMenuOprResp(getUserSessionBean(), jsonObject.getString("menuCode"), jsonObject.getInteger("responsibilityId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toJSONString();
        } catch (PermissionException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "网络异常，请稍后再试", 0, null).toString();
        }
    }

    /**
     * 查询资源
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findResource", produces = "application/json")
    public String findResource(String params) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "menuCode");
            UserSessionBean userSessionBean=getUserSessionBean();
            Assert.notNull(userSessionBean,"登录已失效，请重新登录");
            String val=jedisCluster.get(BaseAccreditCacheServer.PERMISSION_CACHE_UPDATE_LOCK+userSessionBean.getUserId());
            List<ResourceBean> list;
            Integer respId = baseAccreditCacheServer.getOprResp(getUserSessionBean().getCertificate(), jsonObject.getString("menuCode"));
            if (StringUtils.isNotBlank(val)){
                log.warn("用户[{}]权限缓存更新中，从数据库查询资源数据",userSessionBean.getUserId());
                list = baseAccreditServer.findUserResourcesFromDb(respId,jsonObject.getString("menuCode"));
            }else {
                if (StringUtils.isNotBlank(jsonObject.getString("responsibilityId")))
                    respId = jsonObject.getInteger("responsibilityId");
                list = baseAccreditServer.findUserResources(getSessionUserId(), respId, jsonObject.getString("menuCode"), false);
            }
            Collections.sort(list);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, list.size(), list).toJSONString();
        } catch (PermissionException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "网络异常，请稍后再试", 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "getOprResp", produces = "application/json")
    public String getOprResp(String params) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject, "menuCode");
            Integer respId = baseAccreditCacheServer.getOprResp(getUserSessionBean().getCertificate(), jsonObject.getString("menuCode"));
            Assert.notNull(respId,"当前用户没有权限访问此菜单");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, respId).toJSONString();
        } catch (PermissionException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "网络异常，请稍后再试", 0, null).toString();
        }
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
}
