package com.sie.saaf.base.sso.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.inter.IBaseMenu;
import com.sie.saaf.base.sso.model.entities.BaseFunctionCollectionEntity_HI;
import com.sie.saaf.base.sso.model.entities.readonly.BaseFunctionCollectionEntity_HI_RO;
import com.sie.saaf.base.sso.model.inter.IBaseFunctionCollection;
import com.sie.saaf.base.user.model.entities.readonly.BaseMenuRoleEntity_HI_RO;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import java.util.*;

@Component("baseFunctionCollectionServer")
public class BaseFunctionCollectionServer implements IBaseFunctionCollection {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFunctionCollectionServer.class);
    @Autowired
    private ViewObject<BaseFunctionCollectionEntity_HI> baseFunctionCollectionDAO_HI;

    @Autowired
    private BaseViewObject<BaseFunctionCollectionEntity_HI_RO> baseFunctionCollectionDAO_HI_RO;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private IBaseMenu baseMenuServer;

    public BaseFunctionCollectionServer() {
        super();
    }

    public List<BaseFunctionCollectionEntity_HI> findBaseFunctionCollectionInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<BaseFunctionCollectionEntity_HI> findListResult = baseFunctionCollectionDAO_HI.findList("from BaseFunctionCollectionEntity_HI", queryParamMap);
        return findListResult;
    }

    public Object saveBaseFunctionCollectionInfo(JSONObject queryParamJSON) {
        BaseFunctionCollectionEntity_HI baseFunctionCollectionEntity_HI = JSON.parseObject(queryParamJSON.toString(), BaseFunctionCollectionEntity_HI.class);
        Object resultData = baseFunctionCollectionDAO_HI.save(baseFunctionCollectionEntity_HI);
        return resultData;
    }

    @Override
    public Pagination<BaseFunctionCollectionEntity_HI_RO> findFunctionCollections(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(BaseFunctionCollectionEntity_HI_RO.SQL_QUERY_FUNCTION);
        Map<String, Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperParam(queryParamJSON, "bfc.request_method", "requestMethod", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "bfc.function_name", "functionName", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "blt.meaning", "systemName", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON,"blt.system_code", "bltSystemCode", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "bfc.creation_date", false);
        Pagination<BaseFunctionCollectionEntity_HI_RO> findList = baseFunctionCollectionDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }


    @Override
    public List<BaseFunctionCollectionEntity_HI_RO> findUserCollection(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        sql.append(BaseFunctionCollectionEntity_HI_RO.SQL_QUERY_USER_COLLECTION);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(BaseFunctionCollectionEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
//        if ("true".equals(queryParamJSON.getString("addFlag")) == false)
//            sql.append(" and bfcu.function_collection_user_id is not null ");
        sql.append(" and bfc.collection_type = 'outside' ");
        sql.append(" GROUP BY bfc.function_collection_id ");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "bfc.creation_date", false);
        List<BaseFunctionCollectionEntity_HI_RO> findList = baseFunctionCollectionDAO_HI_RO.findList(sql, paramsMap);
        return findList;
    }


    /**
     *  查询使用率最高的菜单信息
     * @param roles
     * @return
     */
    @Override
    public List<BaseMenuRoleEntity_HI_RO> findPopularMenu(UserSessionBean userSessionBean, String roles){
        String str=jedisCluster.hget("MENU_CLICK_COUNT",userSessionBean.getUserId()+"");
        JSONObject jsonObject=new JSONObject();
        if (StringUtils.isNotBlank(str))
            jsonObject=JSON.parseObject(str);
        Set<Integer> menuIds=menuInfoSort(jsonObject,7);
        List<BaseMenuRoleEntity_HI_RO> result=new ArrayList<>();

        jsonObject.fluentClear();
        if ("Y".equals(userSessionBean.getIsadmin())==false)
                jsonObject.put("roleId_in",roles);
        for (Integer menuId:menuIds){
            jsonObject.put("menuId",menuId);
            List<BaseMenuRoleEntity_HI_RO> list=baseMenuServer.findMenuList(jsonObject,-1,-1);
            result.addAll(list);
        }
        int fillSize=5-result.size();
        if (fillSize<=0)
            return result;
        StringBuilder ids=new StringBuilder();
        List<Integer> idArray= new ArrayList<Integer>(menuIds);
        for (int i = 0; i < idArray.size(); i++) {
            ids.append(idArray.get(i));
            if (i==idArray.size()-1)
                break;
            ids.append(",");
        }
        jsonObject.fluentRemove("menuId").put("menuId_notin",ids.toString());
        List<BaseMenuRoleEntity_HI_RO> list=baseMenuServer.findMenuList(jsonObject,1,fillSize);
        result.addAll(list);
        return result;
    }


    /**
     * 从redis中取出使用率最高的menuId
     * @param jsonData
     * @param size
     * @return
     */
    private Set<Integer> menuInfoSort(JSONObject jsonData,int size){
        Set<Integer> menuIds=new HashSet<>();
        Set<String> keys=jsonData.keySet();
        Map<Integer,Integer> map=new LinkedHashMap<>();
        for (int i=0;i<size;i++){
            if (keys.size()==0)
                break;
            Integer tmp=0;
            Integer menuId=0;
            for (String key :keys){
                if (tmp<=jsonData.getInteger(key)){
                    tmp=jsonData.getInteger(key);
                    menuId=Integer.valueOf(key);
                }
            }
            map.put(menuId,tmp);
            keys.remove(menuId+"");
        }
        for (Integer menuId:map.keySet()){
            menuIds.add(menuId);
        }
        return menuIds;
    }

    @Override
    public BaseFunctionCollectionEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        SaafToolUtils.validateJsonParms(paramsJSON,"functionUrl","requestMethod","systemCode","functionName");
        BaseFunctionCollectionEntity_HI instance = SaafToolUtils.setEntity(BaseFunctionCollectionEntity_HI.class, paramsJSON, baseFunctionCollectionDAO_HI, userId);
        if (instance.getFunctionCollectionId() == null) {
            instance.setCollectionType("outside");//inside
        } else {
            SaafToolUtils.validateJsonParms(paramsJSON, "versionNum");
        }
        baseFunctionCollectionDAO_HI.saveOrUpdate(instance);
        return instance;
    }


    @Override
    public void delete(String[] ids) {
        if (ids == null || ids.length == 0)
            return;
        for (String id : ids) {
            if (StringUtils.isBlank(id))
                continue;
            BaseFunctionCollectionEntity_HI instance = baseFunctionCollectionDAO_HI.getById(Integer.valueOf(id));
            Assert.notNull(instance, "数据id[#]不存在".replace("#", id));
            baseFunctionCollectionDAO_HI.delete(instance);
        }
    }

    @Override
    public List<BaseFunctionCollectionEntity_HI_RO> findInCollection(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        sql.append(BaseFunctionCollectionEntity_HI_RO.SQL_QUERY_IN_COLLECTION);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(BaseFunctionCollectionEntity_HI_RO.class, queryParamJSON, sql, paramsMap);

        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "collection.creation_date", false);
        List<BaseFunctionCollectionEntity_HI_RO> findList = baseFunctionCollectionDAO_HI_RO.findList(sql, paramsMap);
        return findList;
    }

    @Override
    public List<BaseFunctionCollectionEntity_HI> saveInCollection(String menuIds[], Integer userId, Integer respId, String systemCode) {
        if (menuIds == null || menuIds.length == 0)
            return Collections.emptyList();
        List<BaseFunctionCollectionEntity_HI> list = new ArrayList<>();
        for (String menuId : menuIds) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("menuId", Integer.valueOf(menuId));
            List<BaseFunctionCollectionEntity_HI> collectionList = baseFunctionCollectionDAO_HI.findByProperty(map);
            if (collectionList.size() > 0)
                continue;
            BaseFunctionCollectionEntity_HI instance = new BaseFunctionCollectionEntity_HI();
            instance.setOperatorUserId(userId);
            instance.setUserId(userId);
            instance.setCollectionType("inside");
            instance.setRequestMethod("post");
            instance.setMenuId(Integer.valueOf(menuId));
            instance.setRespId(respId);
            instance.setSystemCode(systemCode);
            baseFunctionCollectionDAO_HI.save(instance);
            list.add(instance);
        }
        return list;
    }

    @Override
    public void deleteInCollection(Integer functionCollectionId) {

        BaseFunctionCollectionEntity_HI instance = baseFunctionCollectionDAO_HI.getById(Integer.valueOf(functionCollectionId));
        Assert.notNull(instance, "数据id[#]不存在".replace("#", functionCollectionId + ""));
        baseFunctionCollectionDAO_HI.delete(instance);

    }
}
