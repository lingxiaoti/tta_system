package com.sie.saaf.base.user.model.inter.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.dao.readonly.BaseProfileValueDAO_HI_RO;
import com.sie.saaf.base.shiro.model.entities.BaseMenuEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseResourceEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseRoleMenuEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseProfileValue_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserResponsibility_HI_RO;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseAccredit;
import com.sie.saaf.common.bean.*;
import com.sie.saaf.common.cache.server.IRedisCacheData;
import com.sie.saaf.common.exception.PermissionException;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.server.BaseAccreditCacheServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.redis.framework.JedisClusterCore;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 查询权限缓存
 *
 * @author huangtao
 * @creationTime 2018年6月29日 10:28:53
 */
@Component
public class BaseAccreditServer extends JedisClusterCore implements IBaseAccredit {

    private static final Logger log = LoggerFactory.getLogger(BaseAccreditServer.class);

    @Autowired
    private IBaseAccreditCache baseAccreditCacheServer;

    @Autowired
    private BaseViewObject<BaseUserResponsibility_HI_RO> baseUserResponsibilityDAO_HI_RO;

    @Autowired
    private BaseProfileValueDAO_HI_RO baseProfileValueDAO_HI_RO;

    @Autowired
    private IRedisCacheData redisCacheDataServer;

    @Autowired
    private ViewObject<BaseResponsibilityEntity_HI> baseResponsibilityDAO_HI;

    @Autowired
    private ViewObject<BaseRoleMenuEntity_HI> baseRoleMenuDAO_HI;

    @Autowired
    private ViewObject<BaseMenuEntity_HI> baseMenuDAO_HI;

    @Autowired
    private ViewObject<BaseResourceEntity_HI> baseResourceDAO_HI;

    @Autowired
    private ViewObject<BaseUsersEntity_HI> baseUsersDAO_HI;

    @Autowired
    private ProducerService producerService;

//    private ExecutorService batchExportPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2, r -> {
//        Thread s = Executors.defaultThreadFactory().newThread(r);
//        s.setDaemon(true);
//        return s;
//    });

    /**
     *  延时执行
     */
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(4);


    public UserSessionBean findUserAccreditInfo(Integer userId, UserSessionBean userSessionBean) {
        Assert.notNull(userId, "userId argument is required; it must not be null");

        //职责信息
        List<ResponsibilityBean> respList = findRespByUserId(userId, false,null);
        //菜单信息
        if (!baseAccreditCacheServer.menuCacheExist(userId))
            findMenuInfo(userId, null);
        if (!baseAccreditCacheServer.menuRespCacheExist(userId))
            findMenuResp(userId, null);
        userSessionBean.setUserRespList(respList);
        //记录已缓存权限数据的用户id，更新权限缓存时会用到
        jedisCluster.sadd(baseAccreditCacheServer.USER_CACHE_LIST, userId + "");
        return userSessionBean;

    }

    /**
     * 查询用户菜单信息
     * redis hash缓存结构   "menuCache_"+用户id ：<系统编码：菜单信息集合>
     *
     * @param userId     用户id
     * @param systemCode 系统编码
     * @return
     */
    public List<MenuInfoBean> findMenuInfo(Integer userId, String systemCode) {
        return findMenuInfo(userId, systemCode, false);
    }


    /**
     * 查询用户菜单信息
     * redis hash缓存结构   "menuCache_"+用户id ：<系统编码：菜单信息集合>
     *
     * @param userId     用户id
     * @param systemCode 系统编码
     * @return
     */
    public List<MenuInfoBean> findMenuInfo(Integer userId, String systemCode, boolean flusthCache) {

        if (userId == null)
            return Collections.emptyList();
        if (!flusthCache && baseAccreditCacheServer.menuCacheExist(userId))
            return baseAccreditCacheServer.findMenuTree(userId, systemCode);
        List<BaseMenuEntity_HI> list =findMenuInfoFromDb(userId,systemCode);
        List<MenuInfoBean> result = new ArrayList<>();
        //写缓存
        String key = baseAccreditCacheServer.MENU_CACHE + userId;
        Set<String> sysSet=new HashSet<>();
        Map<String, List<MenuInfoBean>> map = new HashMap<>();
        for (BaseMenuEntity_HI obj : list) {
            List<MenuInfoBean> menuList = map.get(obj.getSystemCode());
            menuList = (List<MenuInfoBean>) (menuList == null ? new ArrayList<>() : menuList);
            MenuInfoBean menuInfoBean = new MenuInfoBean();
            BeanUtils.copyProperties(obj, menuInfoBean);
            menuList.add(menuInfoBean);
            map.put(obj.getSystemCode(), menuList);
            sysSet.add(obj.getSystemCode());
            if (StringUtils.isBlank(systemCode)){
                result.add(menuInfoBean);
            }
        }
        Set<String> keys = map.keySet();
        for (String mapKey : keys) {
            jedisCluster.hset(key, mapKey, JSON.toJSONString(map.get(mapKey)));
        }
        Set<String> sysCacheSet=jedisCluster.hkeys(key);
        for (String str:sysCacheSet){
            if (!sysSet.contains(str))
                jedisCluster.hdel(key,str);
        }
        if (StringUtils.isNotBlank(systemCode))
            return map.get(systemCode);
        return result;
    }


    /**
     * 从数据库查询菜单信息
     * @param userId        用户id
     * @param systemCode    系统编码
     * @return
     */
    @Override
    public List<BaseMenuEntity_HI> findMenuInfoFromDb(Integer userId, String systemCode) {
        //查询菜单信息
        StringBuilder menuSql = new StringBuilder("from BaseMenuEntity_HI where enabled='Y' ")
                .append(" and (startDateActive is null or startDateActive<=CURRENT_DATE)")
                .append(" and (endDateActive is null or endDateActive>=CURRENT_DATE) ");
        //  1、通过职责查角色  2、通过角色查菜单
        List<ResponsibilityBean> responsibilityBeanList =findRespByUserIdFromDb(userId);
        Set<String> respId = new HashSet<>();
        for (ResponsibilityBean obj : responsibilityBeanList)
            respId.add(obj.getResponsibilityId() + "");
        if (respId.size() == 0)
            return Collections.emptyList();
        StringBuilder respRoleSql = new StringBuilder("select distinct roleMenu from  BaseResponsibilityRoleEntity_HI respRole,BaseRoleMenuEntity_HI roleMenu where 1=1 ")
                .append(" and respRole.roleId=roleMenu.roleId")
                .append(" and respRole.responsibilityId in ('")
                .append(String.join("','", respId)).append("') ");
        List<BaseRoleMenuEntity_HI> roleMenuList = baseRoleMenuDAO_HI.findList(respRoleSql);
        Set<String> menuId = new HashSet<>();
        for (BaseRoleMenuEntity_HI roleMune : roleMenuList)
            menuId.add(roleMune.getMenuId() + "");
        if (menuId.size() == 0)
            return Collections.emptyList();
        menuSql.append(" and menuId in ('").append(String.join("','", menuId)).append("') ");
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(systemCode)) {
            menuSql.append(" and systemCode=:systemCode");
            paramMap.put("systemCode", systemCode);
        }
        return baseMenuDAO_HI.findList(menuSql, paramMap);

    }



    /**
     * 查询菜单对应的职责信息
     * reids hash缓存结构 “menuRespCache_”+用户id ：<菜单编码：菜单下的职责集合>
     *
     * @param userId   用户id
     * @param menuCode 菜单编码
     */
    public List<ResponsibilityBean> findMenuResp(Integer userId, String menuCode) {
        return findMenuResp(userId, menuCode, false,null);
    }


    /**
     * 查询菜单对应的职责信息
     * reids hash缓存结构 “menuRespCache_”+用户id ：<菜单编码：菜单下的职责集合>
     *
     * @param userId    用户id
     * @param menuCode  菜单编码
     * @param flusCache 刷新缓存
     */
    public List<ResponsibilityBean> findMenuResp(Integer userId, String menuCode, boolean flusCache,String timeStamp) {

        List<ResponsibilityBean> responsibilityBeanList = new ArrayList<>();
        if (userId == null)
            return responsibilityBeanList;

        if (baseAccreditCacheServer.menuRespCacheExist(userId) && !flusCache)
            return baseAccreditCacheServer.findUserResponsibilities(userId, menuCode);

        List<MenuInfoBean> menuList = findMenuInfo(userId, null);
        //查询菜单对应的所有职责
        String cacheKey = baseAccreditCacheServer.MENU_RESP_CACHE + userId;
        Set<String> menucodeSet=new HashSet<>();
        for (MenuInfoBean menuItem : menuList) {

            //只缓存功能节点职责资源数据
            if (StringUtils.isBlank(menuItem.getHtmlUrl()) && "pc".equals(menuItem.getFromType()))
                continue;
            StringBuilder respSql = new StringBuilder("select distinct resp  from  BaseResponsibilityRoleEntity_HI respRole,BaseRoleMenuEntity_HI roleMenu,BaseMenuEntity_HI menu,BaseResponsibilityEntity_HI resp,BaseUserResponsibilityEntity_HI userResp where 1=1 ")
                    .append(" and respRole.roleId=roleMenu.roleId")
                    .append(" and menu.menuId=roleMenu.menuId")
                    .append(" and respRole.responsibilityId=resp.responsibilityId")
                    .append(" and respRole.responsibilityId=userResp.responsibilityId")
                    .append(" and userResp.userId=?")
                    .append(" and (userResp.startDateActive is null or userResp.startDateActive<=?)")
                    .append(" and (userResp.endDateActive is null or userResp.endDateActive>=CURRENT_DATE) ")
                    .append(" and roleMenu.menuId =? ");
            List<BaseResponsibilityEntity_HI> respIdList = baseResponsibilityDAO_HI.findList(respSql, userId,SaafDateUtils.getDate(1),menuItem.getMenuId());
            List<ResponsibilityBean> menuRespList = new ArrayList<>();
            for (BaseResponsibilityEntity_HI item : respIdList) {
                ResponsibilityBean responsibilityBean = getRespById(userId, item.getResponsibilityId());
                if (responsibilityBean == null)
                    continue;
                responsibilityBean.setOrgBean(null);
                responsibilityBean.setProFileBeans(null);
                menuRespList.add(responsibilityBean);
            }
            if (menuRespList.size() == 0)
                continue;
            //设置菜单下职责缓存
            jedisCluster.hset(cacheKey, menuItem.getMenuCode(), JSON.toJSONString(menuRespList));
            menucodeSet.add(menuItem.getMenuCode());

            // 缓存资源信息
            String resouceCachekey = baseAccreditCacheServer.RESOURCE_CACHE + menuItem.getMenuCode();
            for (ResponsibilityBean resp : menuRespList) {
                String key="resourceUpdate"+menuItem.getMenuCode()+timeStamp+resp.getResponsibilityId();
                if ((StringUtils.isNotBlank(timeStamp) && StringUtils.isNotBlank(jedisCluster.get(key))) || !flusCache){
                    continue;
                }
                List<ResourceBean> resourceBeanList = findUserResourcesFromDb(resp.getResponsibilityId(),menuItem.getMenuCode());
                jedisCluster.hset(resouceCachekey, resp.getResponsibilityId() + "", JSON.toJSONString(resourceBeanList));
                jedisCluster.set(key,timeStamp,"NX","EX",1800);
            }
            if (StringUtils.isBlank(menuCode) || menuCode.equals(menuItem.getMenuCode()))
                responsibilityBeanList.addAll(menuRespList);
        }

        Set<String> menucodeCache= jedisCluster.hkeys(cacheKey);
        for (String str:menucodeCache){
            if (!menucodeSet.contains(str))
                jedisCluster.hdel(cacheKey,str);
        }
        return responsibilityBeanList;
    }

    /**
     * 从数据库查询菜单对应的职责信息
     * @param menuCode
     * @return
     */
    @Override
    public List<ResponsibilityBean> findMenuRespFromDb(Integer userId,String menuCode) {
        StringBuilder respSql = new StringBuilder("select distinct resp  from  BaseResponsibilityRoleEntity_HI respRole,BaseRoleMenuEntity_HI roleMenu,BaseMenuEntity_HI menu,BaseResponsibilityEntity_HI resp,BaseUserResponsibilityEntity_HI userResp where 1=1 ")
                .append(" and respRole.roleId=roleMenu.roleId")
                .append(" and menu.menuId=roleMenu.menuId")
                .append(" and respRole.responsibilityId=resp.responsibilityId")
                .append(" and respRole.responsibilityId=userResp.responsibilityId")
                .append(" and userResp.userId=?")
                .append(" and (userResp.startDateActive is null or userResp.startDateActive<=?)")
                .append(" and (userResp.endDateActive is null or userResp.endDateActive>=CURRENT_DATE) ")
                .append(" and menu.menuCode =? ");
        List<BaseResponsibilityEntity_HI> respIdList = baseResponsibilityDAO_HI.findList(respSql, userId, SaafDateUtils.getDate(1),menuCode);
        List<ResponsibilityBean> menuRespList=new ArrayList<>();
        for (BaseResponsibilityEntity_HI item : respIdList) {
            ResponsibilityBean responsibilityBean=new ResponsibilityBean();
            responsibilityBean.setResponsibilityCode(item.getResponsibilityCode());
            responsibilityBean.setResponsibilityId(item.getResponsibilityId());
            responsibilityBean.setSystemCode(item.getSystemCode());
            responsibilityBean.setResponsibilityName(item.getResponsibilityName());
            menuRespList.add(responsibilityBean);
        }
        return menuRespList;
    }



    /**
     * 通过职责id 查询职责缓存信息
     *
     * @param userId
     * @param respId
     * @return
     */
    public ResponsibilityBean getRespById(Integer userId, Integer respId) {
        Assert.notNull(userId, "userId is null");
        Assert.notNull(respId, "respId is null");
        String key = baseAccreditCacheServer.RESP_CACHE + userId;
        String val = jedisCluster.hget(key, respId + "");
        if (StringUtils.isBlank(val))
            return null;
        return JSON.parseObject(val, ResponsibilityBean.class);
    }


    /**
     * 设置用户session中菜单下的默认职责
     *
     * @param userSessionBean
     */
    @Override
    public void setDefaulOprMenuResp(UserSessionBean userSessionBean) {
        if (userSessionBean == null || userSessionBean.getUserId() == null)
            return;
        List<MenuInfoBean> menuInfoBeans = findMenuInfo(userSessionBean.getUserId(), null);
        String key = "cookie_" + userSessionBean.getCertificate();
        for (MenuInfoBean menuInfoBean : menuInfoBeans) {
            List<ResponsibilityBean> responsibilityBeanList = findMenuResp(userSessionBean.getUserId(), menuInfoBean.getMenuCode());
            if (responsibilityBeanList == null || responsibilityBeanList.size() == 0)
                continue;
            Collections.sort(responsibilityBeanList);
            jedisCluster.hset(key, "oprResp_" + menuInfoBean.getMenuCode(), responsibilityBeanList.get(0).getResponsibilityId() + "");
        }
    }


    /**
     * 查询用户所有职责信息
     *
     * @param userId
     * @param flushCache 是否刷新缓存
     * @return
     */
    @Override
    public List<ResponsibilityBean> findRespByUserId(Integer userId, boolean flushCache,String timestamp) {

        if (!flushCache) {
            List<ResponsibilityBean> list = baseAccreditCacheServer.findUserResponsibility(userId);
            if (list != null && list.size() > 0)
                return list;
        }

        //写缓存
        List<ResponsibilityBean> result = findRespByUserIdFromDb(userId);
        Map<String, List<ResponsibilityBean>> map = new HashMap<>();
        String cacheKey = baseAccreditCacheServer.RESP_CACHE + userId;

        Set<String> respSet=new HashSet<>();
        Set<String> sysSet=new HashSet<>();

        for (ResponsibilityBean obj : result) {
            if (StringUtils.isNotBlank(obj.getSystemCode())) {
                ResponsibilityBean sysCodeCache = new ResponsibilityBean();
                BeanUtils.copyProperties(obj, sysCodeCache);
                List<ResponsibilityBean> sysRespList = map.get("sys_" + obj.getSystemCode());
                if (sysRespList == null)
                    sysRespList = new ArrayList<>();
                sysRespList.add(sysCodeCache);
                map.put("sys_" + obj.getSystemCode(), sysRespList);
                sysSet.add("sys_" + obj.getSystemCode());
            }
            //查询职责下所有的profile
            List<ProFileBean> proFileBeans=findProfileValByResponsibilityId(obj.getResponsibilityId(), null, flushCache, timestamp);
            //查询职责下的ou
            for (ProFileBean proFileBean:proFileBeans){
                if ("BASE_OU".equals(proFileBean.getProfileCode())){
                    JSONObject ouLookupMean = redisCacheDataServer.findLookupValueMeaning("BASE_OU", "BASE");
                    JSONObject ouLookupDesc = redisCacheDataServer.findLookupValueDescription("BASE_OU", "BASE");
                    OrgBean orgBean = new OrgBean();
                    orgBean.setOrgId(Integer.parseInt(proFileBean.getProfileValue()));
                    orgBean.setOrgName(ouLookupMean.getString(proFileBean.getProfileValue()));
                    orgBean.setOrgDescription(ouLookupDesc.getString(proFileBean.getProfileValue()));
                    obj.setOrgBean(orgBean);
                }
            }
            //responsibilityId 作为field，用于快速判断用户是否有对应职责权限
            obj.setProFileBeans(proFileBeans);
            jedisCluster.hset(cacheKey, obj.getResponsibilityId() + "", JSON.toJSONString(obj));
            respSet.add(obj.getResponsibilityId()+"");
        }
        Set<String> keys = map.keySet();
        for (String key : keys) {
            //系统编码 作为field，用于查询用户当前系统下的所有职责
            jedisCluster.hset(cacheKey, key, JSON.toJSONString(map.get(key)));
        }
        Set<String> respCacheSet=jedisCluster.hkeys(cacheKey);
        for (String str:respCacheSet){
            if ( !respSet.contains(str) && !sysSet.contains(str))
                jedisCluster.hdel(cacheKey,str);
        }
        return result;
    }

    /**
     * 从数据库查询用户职责数据
     * @param userId
     * @return
     */
    public List<ResponsibilityBean> findRespByUserIdFromDb(Integer userId) {
        //查职责数据
        StringBuilder sql = new StringBuilder(BaseUserResponsibility_HI_RO.QUERY_RESPONSIBILITY_BY_USERID_SQL);
        sql.append(" AND (baseResponsibility.start_date_active is NULL OR baseResponsibility.start_date_active<  SYSDATE + 1 ) ")
                .append(" AND (baseResponsibility.end_date_active IS NULL OR baseResponsibility.end_date_active>=SYSDATE )")
                .append(" AND (baseUserResponsibility.start_date_active is NULL OR baseUserResponsibility.start_date_active< SYSDATE + 1) ")
                .append(" AND (baseUserResponsibility.end_date_active IS NULL OR baseUserResponsibility.end_date_active>=SYSDATE ) and baseUsers.user_id=?");
        List<BaseUserResponsibility_HI_RO> list = baseUserResponsibilityDAO_HI_RO.findList(sql.toString(), userId + "");
        List<ResponsibilityBean> result = new ArrayList<>();
        for (BaseUserResponsibility_HI_RO obj : list) {
            ResponsibilityBean responsibilityBean = new ResponsibilityBean();
            responsibilityBean.setResponsibilityCode(obj.getResponsibilityCode());
            responsibilityBean.setResponsibilityName(obj.getResponsibilityName());
            responsibilityBean.setSystemCode(obj.getSystemCode());
            responsibilityBean.setResponsibilityId(obj.getResponsibilityId());
            result.add(responsibilityBean);
        }
        return result;
    }



    /**
     * 查询职责对应的ou信息
     *
     * @param respId 职责id
     * @return
     */
    @Override
    public OrgBean findOrgInfo(Integer respId) {
        JSONObject ouLookupMean = redisCacheDataServer.findLookupValueMeaning("BASE_OU", "BASE");
        JSONObject ouLookupDesc = redisCacheDataServer.findLookupValueDescription("BASE_OU", "BASE");
        List<ProFileBean> ouProfileList = findProfileValByResponsibilityId(respId, "BASE_OU", false,null);
        if (ouProfileList == null || ouProfileList.size() != 1)
            return null;
        OrgBean orgBean = new OrgBean();
        orgBean.setOrgId(Integer.parseInt(ouProfileList.get(0).getProfileValue()));
        orgBean.setOrgName(ouLookupMean.getString(ouProfileList.get(0).getProfileValue()));
        orgBean.setOrgDescription(ouLookupDesc.getString(ouProfileList.get(0).getProfileValue()));
        return orgBean;
    }


    /**
     * 判断资源是否可用
     * 项目中用于API级权限验证
     *
     * @param userId           用户ID
     * @param responsibilityId 职责ID
     * @param menuCode         菜单ID
     * @param resourceCode     资源ID[按钮操作]
     * @return
     */
    public static boolean isExistOperateResource(String userId, String responsibilityId, String menuCode, String resourceCode) {
        return true;
    }

    /**
     * 得到用户当前职责对应的OrgId
     *
     * @param userId           用户ID
     * @param responsibilityId 职责ID
     * @return
     */
    public List<ProFileBean> getOrg(String userId, Integer responsibilityId) {
        return findProfileValByResponsibilityId(responsibilityId, BaseAccreditCacheServer.ORG_ID, false,null);
    }


    /**
     * 得到用户当前访问菜单所用职责对应的UserType
     *
     * @param userId           用户ID
     * @param responsibilityId 职责ID
     * @return
     */
    public List<ProFileBean> getUserType(String userId, Integer responsibilityId) {
        return findProfileValByResponsibilityId(responsibilityId, BaseAccreditCacheServer.USER_TYPE, false,null);
    }


    /**
     * 得到用户当前访问菜单所用职责对应的ChannelType
     *
     * @param userId           用户ID
     * @param responsibilityId 职责ID
     * @return
     */
    public List<ProFileBean> getChannelType(String userId, Integer responsibilityId) {
        return findProfileValByResponsibilityId(responsibilityId, BaseAccreditCacheServer.CHANNEL, false,null);
    }


    /**
     * 得到当前职责对应的profile值
     *
     * @param responsibilityId 职责ID
     * @param profileCode      profileCode
     * @param flushCache       是否刷新缓存
     * @return profileCode为空时，返回职责对应的所有 profile值
     */
    public List<ProFileBean> findProfileValByResponsibilityId(Integer responsibilityId, String profileCode, boolean flushCache,String timestamp) {
        Assert.notNull(responsibilityId, "argument responsibilityId required");
        String cacheKey="profileUpdate"+timestamp+responsibilityId;
        if ((StringUtils.isNotBlank(timestamp) && StringUtils.isNotBlank(jedisCluster.get(cacheKey))) || !flushCache ) {
            List<ProFileBean> list = baseAccreditCacheServer.findProfileValByResponsibilityId(responsibilityId, profileCode);
            if (list != null && list.size() > 0)
                return list;
            return Collections.emptyList();
        }

        //查询profile数据
        StringBuilder sql = new StringBuilder(BaseProfileValue_HI_RO.QUERY_PROFILE_VALUE_QUERY)
                .append(" and baseProfileValue.business_key=?");
        List<BaseProfileValue_HI_RO> list = baseProfileValueDAO_HI_RO.findList(sql, responsibilityId);
        List<ProFileBean> result = new ArrayList<>();
        Set<String> existProfile = new HashSet<>();
        String key = baseAccreditCacheServer.PROFILE_CACHE + responsibilityId;
        Set<String> profileCodes=new HashSet<>();
        for (BaseProfileValue_HI_RO obj : list) {
            //忽略职责下重复profile
            if (!existProfile.add(obj.getProfileCode())) {
                log.warn("repeated profileCode:{} with responsibilityId:{}", obj.getProfileCode(), responsibilityId);
                jedisCluster.hdel(key, obj.getProfileCode());
                continue;
            }
            ProFileBean proFileBean = new ProFileBean();
            proFileBean.setProfileCode(obj.getProfileCode());
            proFileBean.setProfileId(obj.getProfileId());
            proFileBean.setProfileValue(obj.getProfileValue());
            jedisCluster.hset(key, obj.getProfileCode(), JSON.toJSONString(proFileBean));
            if (StringUtils.isBlank(profileCode) || obj.equals(profileCode))
                result.add(proFileBean);
            profileCodes.add(obj.getProfileCode());
        }
        jedisCluster.set(cacheKey,timestamp,"NX","EX",1800);
        //删除缓存
        Set<String>  cacheCode= jedisCluster.hkeys(key);
        if (cacheCode==null)
            return result;
        for (String code:cacheCode){
            if (!profileCodes.contains(code))
                jedisCluster.hdel(key,code);
        }
        return result;
    }


    /**
     * 更新菜单下的操作职责
     */
    @Override
    public void setMenuOprResp(UserSessionBean userSessionBean, String menuCode, Integer respId) {
        Assert.notNull(userSessionBean, "certificate argument is required; it must not be null");
        Assert.notNull(menuCode, "menuCode argument is required; it must not be null");
        Assert.notNull(respId, "respId argument is required; it must not be null");
        if (!baseAccreditCacheServer.menuRespCacheExist(userSessionBean.getUserId(), respId, menuCode))
            throw new PermissionException("permission denied");
        jedisCluster.hset("cookie_" + userSessionBean.getCertificate(), "oprResp_" + menuCode, respId + "");
    }


    /**
     * 通过职责获取用户资源
     *
     * @param userId           用户ID
     * @param responsibilityId 职责ID
     * @param flushCache       是否刷新缓存
     */
    @Override
    public List<ResourceBean> findUserResources(Integer userId, Integer responsibilityId, String menuCode, boolean flushCache) {

        if (userId == null || responsibilityId == null || StringUtils.isBlank(menuCode))
            return Collections.emptyList();

        if (!baseAccreditCacheServer.menuRespCacheExist(userId, responsibilityId, menuCode))
            throw new PermissionException("permission denied");

        if (!flushCache && jedisCluster.exists(baseAccreditCacheServer.RESOURCE_CACHE + menuCode)) {
            return baseAccreditCacheServer.findUserResources(menuCode, responsibilityId);
        }


        String key = baseAccreditCacheServer.RESOURCE_CACHE + menuCode;
        List<ResourceBean> resourceBeanList = findUserResourcesFromDb(responsibilityId,menuCode);
        jedisCluster.hset(key, responsibilityId + "", JSON.toJSONString(resourceBeanList));
        return resourceBeanList;
    }


    /**
     * 查询数据库，通过职责获取用户资源
     * @param responsibilityId
     * @param menuCode
     * @return
     */
    @Override
    public List<ResourceBean> findUserResourcesFromDb( Integer responsibilityId, String menuCode) {
        //查询职责下所有的资源
        StringBuilder sql = new StringBuilder(" select distinct resource from BaseResourceEntity_HI resource,BaseMenuEntity_HI menu,BaseRoleResourceEntity_HI role,BaseResponsibilityRoleEntity_HI resp where ")
                .append(" resource.resourceId=role.resourceId ")
                .append(" and resp.roleId=role.roleId ")
                .append(" and menu.menuId=resource.menuId ")
                .append(" and  resp.responsibilityId =:responsibilityId ");
        Map<String,Object> map=new HashMap<>();
        map.put("responsibilityId",responsibilityId);
        if (StringUtils.isNotBlank(menuCode)){
            map.put("menuCode",menuCode);
            sql.append(" and  menu.menuCode =:menuCode ");
        }
        sql.append(" order by resource.orderNo ");
        List<BaseResourceEntity_HI> resourceList = baseResourceDAO_HI.findList(sql,map);
        List<ResourceBean> resourceBeanList = new ArrayList<>();
        for (BaseResourceEntity_HI item : resourceList) {
            ResourceBean resourceBean = new ResourceBean();
            BeanUtils.copyProperties(item, resourceBean);
            resourceBeanList.add(resourceBean);
        }
        return resourceBeanList;
    }


    /**
     * 更新缓存
     *
     */
    @Override
    public void flushAccreditCache(final Integer userId, final String timestamp) {
        if (userId == null)
            return;

        Long time = System.currentTimeMillis();
        String key = "permissionUpdate" + userId;
        String uuid = UUID.randomUUID().toString();
        String val = jedisCluster.set(key, uuid, "NX", "EX", 300);
        try {
            if (StringUtils.isBlank(val)) {
                // 相同的用户数据并发执行时， 延时触发
                service.schedule(new Runnable() {
                    @Override
                    public void run() {
                        ActiveMQQueue queueDestination_ = new ActiveMQQueue("permissionUpdaetQueue");
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("userId",userId);
                        jsonObject.put("timestamp",timestamp);
                        producerService.sendMessage(queueDestination_, jsonObject.toJSONString());
                    }
                }, 10, TimeUnit.SECONDS);
                log.info("用户{}权限缓存更新中", userId);
                return;
            }
            log.info("{}开始更新权限缓存", userId);
            jedisCluster.hset("userPermissionUpdate", userId + "", SaafDateUtils.convertDateToString(new Date()));
            //菜单
            findMenuInfo(userId, null, true);
            //职责、profile
            findRespByUserId(userId, true, timestamp);
            //菜单下职责、资源
            findMenuResp(userId, null, true, timestamp);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            String lockVal = jedisCluster.get(key);
            if (uuid.equals(lockVal)) {
                jedisCluster.hdel("userPermissionUpdate", userId + "");
                jedisCluster.del(key);
                log.info("{}缓存更新完成,用时:{}s",userId, Double.valueOf(System.currentTimeMillis() - time) / 1000);
            }
        }

    }

    /**
     * @param menuIdArray
     * @param roleIdArray
     * @param respIdArray
     * @param resourceIdArray
     * @return
     */
    @Override
    public Set<Integer> findRelatedUserId(Set<String> menuIdArray, Set<String> roleIdArray, Set<String> respIdArray, Set<String> resourceIdArray) {
        StringBuilder sql = new StringBuilder("select distinct user from BaseUsersEntity_HI user,BaseUserResponsibilityEntity_HI userResp,BaseResponsibilityRoleEntity_HI resp,BaseRoleMenuEntity_HI role where ")
                .append(" user.userId=userResp.userId")
                .append(" and userResp.responsibilityId=resp.responsibilityId")
                .append(" and resp.roleId=role.roleId ");
        StringBuilder condition = new StringBuilder();
        if (menuIdArray != null && menuIdArray.size() > 0) {
            condition.append(" and role.menuId in ('")
                    .append(String.join("','", menuIdArray))
                    .append("')");
        }
        if (roleIdArray != null && roleIdArray.size() > 0) {
            condition.append(" and role.roleId in ('")
                    .append(String.join("','", roleIdArray))
                    .append("')");
        }
        if (respIdArray != null && respIdArray.size() > 0) {
            condition.append(" and resp.responsibilityId in ('")
                    .append(String.join("','", respIdArray))
                    .append("')");
        }
        if (resourceIdArray != null && resourceIdArray.size() > 0) {
            int whereIndex = sql.indexOf("where");
            //拼接资源表
            sql.replace(whereIndex, whereIndex + 5, ",BaseRoleResourceEntity_HI resource where resource.roleId=role.roleId and ");
            condition.append("and role.resourceId in ('")
                    .append(String.join("','", resourceIdArray))
                    .append("')");
        }
        if (condition.length() == 0)
            return Collections.emptySet();
        sql.append(condition);
        List<BaseUsersEntity_HI> userList = baseUsersDAO_HI.findList(sql);
        Set<Integer> result = new HashSet<>();
        for (BaseUsersEntity_HI user : userList) {
            result.add(user.getUserId());
        }
        return result;
    }

}
