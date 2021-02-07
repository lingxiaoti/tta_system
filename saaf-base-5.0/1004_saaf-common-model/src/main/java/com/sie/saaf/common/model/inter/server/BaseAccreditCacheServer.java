package com.sie.saaf.common.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.bean.MenuInfoBean;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.bean.ResourceBean;
import com.sie.saaf.common.bean.ResponsibilityBean;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.yhg.redis.framework.JedisClusterCore;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 统一权限API
 * api级权限验证，权限缓存等
 * author: huangtao
 * date: 2018-06-22
 */
@Component
public class BaseAccreditCacheServer extends JedisClusterCore implements IBaseAccreditCache {


    /**
     * 事业部对应的profile编码
     */
    public static final String ORG_ID = "BASE_OU";
    /**
     * 渠道对应的profile编码
     */
    public static final String CHANNEL = "CHANNEL";
    /**
     * 用户类型对应的profile编码
     */
    public static final String USER_TYPE = "USER_TYPE";

    /**
     * redis缓存更新标识，缓存更新过程中查询数据库
     */
    public static final String PERMISSION_CACHE_UPDATE_LOCK="permissionUpdate";


    @Override
    public Integer getOprRespId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request==null)
            throw new IllegalStateException("无法获取当前职责");
        Object resp=request.getAttribute("currentOprRespId");
        if (resp==null)
            throw new IllegalStateException("无法获取当前职责");
        return Integer.valueOf(resp.toString());
    }


    /**
     * 查询职责信息
     * @param userId
     * @param respId
     * @return
     */
    @Override
    public ResponsibilityBean findResp(Integer userId, Integer respId){
        Assert.notNull(respId,"respId is required");
        Assert.notNull(userId,"userId is required");
        String cacheKey = RESP_CACHE + userId;
        String val=jedisCluster.hget(cacheKey,respId+"");
        if (StringUtils.isBlank(val))
            throw  new IllegalArgumentException("职责不存在");
        return JSON.parseObject(val,ResponsibilityBean.class);
    }



    /**
     * 得到用户当前职责对应的OrgId
     *
     * @param userId           用户ID
     * @param responsibilityId 职责ID
     * @return
     */
    @Override
    public ProFileBean getOrg(Integer userId, Integer responsibilityId) {
        if (!respExist(userId, responsibilityId))
            return null;
        List<ProFileBean>  list= findProfileValByResponsibilityId(responsibilityId, ORG_ID);
        if (list==null || list.size()!=1)
            return null;
        return list.get(0);
    }

    /**
     * 得到用户当前职责对应的OrgId
     *
     * @param userId           用户ID
     * @return
     */
    @Override
    public ProFileBean getOrg(Integer userId) {
        return getOrg(userId,getOprRespId());
    }

    /**
     * 得到用户当前职责对应的OrgId
     *
     * @param userId           用户ID
     * @return
     */
    @Override
    public List<Integer> getOrgId(Integer userId) {
        List<ProFileBean> list= findProfileValByResponsibilityId(getOprRespId(), ORG_ID);
        Set<Integer> ids=new HashSet<>();
        if(list==null || list.size()==0)
            return new ArrayList<>(ids);
        for (ProFileBean proFileBean:list){
            ids.add(Integer.valueOf(proFileBean.getProfileValue()));
        }
        return new ArrayList<>(ids);
    }


    /**
     * 得到用户当前访问菜单所用职责对应的UserType
     *
     * @param userId           用户ID
     * @param responsibilityId 职责ID
     * @return
     */
    @Override
    public ProFileBean getUserType(Integer userId, Integer responsibilityId) {
        if (!respExist(userId, responsibilityId))
            return  null;
        List<ProFileBean> list= findProfileValByResponsibilityId(responsibilityId, USER_TYPE);
        if (list==null || list.size()!=1)
            return  null;
        return list.get(0);
    }



    /**
     * 得到用户当前访问菜单所用职责对应的UserType
     *
     * @param userId           用户ID
     * @return
     */
    @Override
    public ProFileBean getUserType(Integer userId) {
        return getUserType(userId,getOprRespId());
    }

    /**
     * 得到用户当前访问菜单所用职责对应的ChannelType
     *
     * @param userId           用户ID
     * @param responsibilityId 职责ID
     * @return
     */
    @Override
    public ProFileBean getChannelType(Integer userId, Integer responsibilityId) {
        if (!respExist(userId, responsibilityId))
            return  null;
       List <ProFileBean>list=findProfileValByResponsibilityId(responsibilityId, CHANNEL);
       if (list==null || list.size()!=1)
           return null;
       return list.get(0);
    }

    /**
     * 得到用户当前访问菜单所用职责对应的ChannelType
     *
     * @param userId           用户ID
     * @return
     */
    @Override
    public List<ProFileBean> getChannelType(Integer userId) {
        return findProfileValByResponsibilityId(getOprRespId(), CHANNEL);
    }


    /**
     * 得到用户当前职责对应的profile值
     *
     * @param responsibilityId 职责ID
     * @param profileCode      profile编码
     * @return
     */
    @Override
    public List<ProFileBean> findProfileValByResponsibilityId(Integer responsibilityId, String profileCode) {
        Assert.notNull(responsibilityId,"argument responsibilityId is required");
        List<ProFileBean> list = new ArrayList<>();
        String key = PROFILE_CACHE + responsibilityId;

        if (StringUtils.isNotBlank(profileCode)) {
            String val = jedisCluster.hget(key, profileCode);
            if (StringUtils.isBlank(val))
                return Collections.emptyList();
           list.add(JSON.parseObject(val,ProFileBean.class));
           return list;
        }
        Map<String, String> map = jedisCluster.hgetAll(key);
        if (map.size() == 0)
           return Collections.emptyList();
        Set<String> keys = map.keySet();
        for (String mapKey : keys) {
            list.add(JSON.parseObject(map.get(mapKey), ProFileBean.class));
        }
        return list;
    }

    /**
     * 得到当前用户访问功能所对应的职责
     *
     * @param userId   用户ID
     * @param menuCode 菜单ID
     * @return
     */
    @Override
    public List<ResponsibilityBean> findUserResponsibilities(Integer userId, String menuCode) {
        if (StringUtils.isBlank(menuCode))
            return Collections.emptyList();
        String key = MENU_RESP_CACHE + userId;
        String val = jedisCluster.hget(key, menuCode);
        if (StringUtils.isBlank(val))
            return Collections.emptyList();
        return JSONArray.parseArray(val, ResponsibilityBean.class);
    }


    /**
     * 得到当前用户导航Tree
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<MenuInfoBean> findMenuTree(Integer userId, String systemCode) {
        List<MenuInfoBean> result = new ArrayList<>();
        if (userId == null)
            return result;
        String key = MENU_CACHE + userId;
        if (StringUtils.isBlank(systemCode)) {
            String cursor = "0";
            do {
                ScanResult<Map.Entry<String, String>> scanResult = jedisCluster.hscan(key, cursor);
                for (Map.Entry<String, String> entry : scanResult.getResult()) {
                    String val = entry.getValue();
                    if (StringUtils.isBlank(val))
                        continue;
                    result.addAll(JSONArray.parseArray(val, MenuInfoBean.class));
                }
                cursor = scanResult.getStringCursor();
            } while (!"0".equals(cursor));
            return result;
        }

        String val = jedisCluster.hget(key, systemCode);
        if (StringUtils.isBlank(val))
            return Collections.emptyList();
        return JSONArray.parseArray(val, MenuInfoBean.class);
    }


    /**
     * 通过职责获取用户资源
     *
     * @param menuCode         菜单编码
     * @param responsibilityId 职责ID
     */
    @Override
    public List<ResourceBean> findUserResources(String menuCode, Integer responsibilityId) {
        String key = RESOURCE_CACHE + menuCode;
        String val = jedisCluster.hget(key, responsibilityId + "");
        if (StringUtils.isBlank(val))
            return Collections.emptyList();
        return JSONArray.parseArray(val, ResourceBean.class);
    }


    /**
     * 查询用户所有职责
     *
     * @param userId
     */
    @Override
    public List<ResponsibilityBean> findUserResponsibility(Integer userId) {
        String key = RESP_CACHE + userId;
        String cursor = "0";
        List<ResponsibilityBean> result = new ArrayList<>();
        do {
            ScanResult<Map.Entry<String, String>> scanResult = jedisCluster.hscan(key, cursor);
            for (Map.Entry<String, String> entry : scanResult.getResult()) {
                if (!entry.getKey().startsWith("sys_"))
                    result.add(JSON.parseObject(entry.getValue(),ResponsibilityBean.class));
            }
            cursor = scanResult.getStringCursor();
        } while (!"0".equals(cursor));
        return result;
    }

    /**
     * 查询用户当前系统所有职责
     *
     * @param userId
     * @param systemCode 为空时查所有系统职责
     * @return
     */
    @Override
    public List<ResponsibilityBean> findUserResponsibility(Integer userId, String systemCode) {
        if (userId == null)
            return null;

        String key = RESP_CACHE + userId;
        if (StringUtils.isNotBlank(systemCode)) {
            String val = jedisCluster.hget(key, "sys_" + systemCode);
            if (StringUtils.isBlank(val))
                return Collections.emptyList();
            return JSONArray.parseArray(val, ResponsibilityBean.class);
        }
        // hscan 方式遍历
        String cursor = "0";
        ScanParams scanParam = new ScanParams();
        scanParam.match("sys_*");
        List<ResponsibilityBean> result = new ArrayList<>();
        do {
            ScanResult<Map.Entry<String, String>> scanResult = jedisCluster.hscan(key, cursor, scanParam);
            for (Map.Entry<String, String> entry : scanResult.getResult()) {
                if (StringUtils.isBlank(systemCode) || entry.getKey().equals("sys_" + systemCode))
                    result.add(JSON.parseObject(entry.getValue(), ResponsibilityBean.class));
            }
            cursor = scanResult.getStringCursor();
        } while (!"0".equals(cursor));
        return result;
    }


    /**
     * 判断当前用户是否已缓存菜单权限信息
     *
     * @param userId
     * @return
     */
    @Override
    public boolean menuCacheExist(Integer userId) {
        if (userId == null)
            return false;
        String key = MENU_CACHE + userId;
        return jedisCluster.exists(key);
    }

    /**
     * 判断当前用户是否已缓存菜单下职责信息
     *
     * @param userId
     * @return
     */
    @Override
    public boolean menuRespCacheExist(Integer userId) {
        if (userId == null)
            return false;
        String key = MENU_RESP_CACHE + userId;
        return jedisCluster.exists(key);
    }

    /**
     *
     *  判断用菜单下是否有对应职责
     * @param userId
     * @param respId
     * @param menuCode
     * @return
     */
    @Override
    public boolean menuRespCacheExist(Integer userId, Integer respId, String menuCode) {
        if (userId == null || respId == null || StringUtils.isBlank(menuCode))
            return false;
        if (!respExist(userId, respId))
            return false;
        List<ResponsibilityBean> list = findUserResponsibilities(userId, menuCode);
        boolean denied = false;
        for (ResponsibilityBean responsibilityBean : list) {
            if (responsibilityBean.getResponsibilityId().intValue() == respId) {
                denied = true;
                break;
            }
        }
        return denied;
    }


    /**
     * 判断当前用户是否有对应职责权限
     *
     * @param userId
     * @return
     */
    @Override
    public boolean respExist(Integer userId, Integer respId) {
        if (userId == null || respId == null)
            return false;
        String key = RESP_CACHE + userId;
        return jedisCluster.hexists(key, respId + "");
    }

    /**
     * 判断资源是否可用
     * 项目中用于API级权限验证
     *
     * @param respId
     * @return
     */
    @Override
    public boolean resourceCacheExist(String menuCode, Integer respId, String resourceCode) {
        if (StringUtils.isBlank(menuCode) || respId==null)
            return false;
        String key = RESOURCE_CACHE + menuCode;
        if (respId != null && StringUtils.isBlank(resourceCode)) {
            return jedisCluster.hexists(key, respId + "");
        }

        List<ResourceBean> resourceBeanList = findUserResources(menuCode, respId);
        for (ResourceBean resourceBean : resourceBeanList) {
            if (resourceCode.equals(resourceBean.getResourceCode()))
                return true;
        }
        return false;
    }


    /**
     * 判断当前职责是否已缓存 资源信息
     *
     * @param respId
     * @param menuCode
     * @return
     */
    @Override
    public boolean resourceCacheExist(String menuCode, Integer respId) {
        return resourceCacheExist(menuCode, respId, null);
    }


    /**
     * 判断当前职责是否已缓存profile信息
     *
     * @param respId
     * @param profileCode
     * @return
     */
    @Override
    public boolean profileCacheExist(Integer respId, String profileCode) {
        if (respId == null)
            return false;
        String key = RESOURCE_CACHE + respId;
        if (StringUtils.isNotBlank(profileCode)) {
            return jedisCluster.hexists(key, profileCode);
        }
        return jedisCluster.exists(key);
    }

    /**
     * 判断当前职责是否已缓存profile信息
     *
     * @param respId
     * @return
     */
    @Override
    public boolean profileCacheExist(Integer respId) {
        return profileCacheExist(respId, null);
    }

    /**
     *  获取用户当前菜单下的操作职责
     * @param certificate 用户session key
     * @param menuCode    菜单编码
     * @return
     */
    @Override
    public Integer getOprResp(String certificate, String menuCode){
        if (StringUtils.isBlank(certificate) || StringUtils.isBlank(menuCode))
            return null;
        String val=jedisCluster.hget("cookie_"+certificate,"oprResp_"+menuCode);
        if (StringUtils.isBlank(val))
            return null;
        return Integer.valueOf(val);
    }


    /**
     * 查询用户对应ou下的所有app菜单
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public Set<String> findAppApply(Integer userId,Integer orgId){
        Assert.notNull(userId,"userId is required");
        Assert.notNull(orgId,"orgId is required");
        Set<String> result=new HashSet<>();
        //查询ou对应的职责
        String respKey = RESP_CACHE + userId;
        // hscan 方式遍历
        String cursor = "0";
        ScanParams scanParam = new ScanParams();
        Set<Integer> respIdSet=new HashSet<>();
        do {
            ScanResult<Map.Entry<String, String>> scanResult = jedisCluster.hscan(respKey, cursor, scanParam);
            for (Map.Entry<String, String> entry : scanResult.getResult()) {
                if (entry.getKey() == null || entry.getKey().startsWith("sys_"))
                    continue;
                ResponsibilityBean resp = JSON.parseObject(entry.getValue(), ResponsibilityBean.class);
                if (resp.getOrgBean() == null || resp.getOrgBean().getOrgId() == null || resp.getOrgBean().getOrgId().intValue() != orgId.intValue())
                    continue;
                respIdSet.add(resp.getResponsibilityId());
            }
            cursor = scanResult.getStringCursor();
        } while (!"0".equals(cursor));

        if (respIdSet.size()==0)
           return result;

        //查询所有菜单
        List<MenuInfoBean> menuList = new ArrayList<>();
        String key = MENU_CACHE + userId;
        cursor = "0";
        do {
            ScanResult<Map.Entry<String, String>> scanResult = jedisCluster.hscan(key, cursor);
            for (Map.Entry<String, String> entry : scanResult.getResult()) {
                String val = entry.getValue();
                if (StringUtils.isBlank(val))
                    continue;
                menuList.addAll(JSONArray.parseArray(val, MenuInfoBean.class));
            }
            cursor = scanResult.getStringCursor();
        } while (!"0".equals(cursor));

        if (menuList.size()==0)
            return result;

        //遍历菜单，根据职责筛选和菜单类型筛选菜单
        for (Iterator<MenuInfoBean> iterator=menuList.iterator();iterator.hasNext();){
            MenuInfoBean item=iterator.next();
            if ("pc".equals(item.getFromType()))
                continue;
            for (Integer respId:respIdSet){
                if (menuRespCacheExist(userId,respId,item.getMenuCode())){
                    result.add(item.getMenuCode());
                    break;
                }
            }
        }
        return result;
    }


}
