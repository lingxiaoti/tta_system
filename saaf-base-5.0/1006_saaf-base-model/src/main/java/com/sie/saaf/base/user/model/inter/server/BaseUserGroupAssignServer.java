package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupValues;
import com.sie.saaf.base.user.model.entities.BaseUserGroupAssignEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseProductInv_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserGroupAssignEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseUserGroupAssign;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseUserGroupAssignServer")
public class BaseUserGroupAssignServer extends BaseCommonServer<BaseUserGroupAssignEntity_HI> implements IBaseUserGroupAssign {
    	private static final Logger LOGGER = LoggerFactory.getLogger(BaseUserGroupAssignServer.class);


    @Autowired
    private BaseViewObject<BaseUserGroupAssignEntity_HI_RO> baseUserGroupAssignDAO_HI_RO;
    @Autowired
    private ViewObject<BaseUserGroupAssignEntity_HI> baseUserGroupAssignDAO_HI;
    @Autowired
    private ViewObject<BaseLookupValuesEntity_HI> baseLookupValuesEntityHi;
    @Autowired
    private BaseViewObject<BaseLookupValuesEntity_HI_RO> baseLookupValuesEntity_HI_RO;

    @Override
    public Pagination<BaseUserGroupAssignEntity_HI_RO> findUserGroupAssignsForUser(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Integer userId = queryParamJSON.getInteger("userId");
        StringBuffer sb = new StringBuffer();
        sb.append(BaseUserGroupAssignEntity_HI_RO.QUERY);
        JSONObject paramJson = new JSONObject();
        paramJson.put("userId",userId);
        Map<String,Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperParam(queryParamJSON, "t.user_id", "userId", sb, paramsMap, "=");
//        SaafToolUtils.parperHbmParam(BaseProductInv_HI_RO.class,queryParamJSON,sb,paramsMap);
        Pagination<BaseUserGroupAssignEntity_HI_RO> findList = baseUserGroupAssignDAO_HI_RO.findPagination(sb,SaafToolUtils.getSqlCountString(sb),paramsMap,pageIndex,pageRows);

        return findList;
    }

    @Override
    public Pagination<BaseUserGroupAssignEntity_HI_RO> findUserGroupAssignsForGroup(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        String userGroupCode = queryParamJSON.getString("userGroupCode");
        StringBuffer sb = new StringBuffer();
        sb.append(BaseUserGroupAssignEntity_HI_RO.QUERY);
        sb.append(" and t.user_group_code ='"+userGroupCode+"'");
        if(queryParamJSON.getString("userFullName")!=null && !"".equals(queryParamJSON.getString("userFullName"))){
            String userFullName = queryParamJSON.getString("userFullName");
            sb.append(" and bu.user_full_name ='"+userFullName+"'");
        }

        JSONObject paramJson = new JSONObject();
//        paramJson.put("userGroupCode",userGroupCode);
        Map<String,Object> paramsMap = new HashMap<>();
//        SaafToolUtils.parperHbmParam(BaseUserGroupAssignEntity_HI_RO.class,queryParamJSON,sb,paramsMap);
//        SaafToolUtils.parperParam(queryParamJSON, "t.user_group_code", "userGroupCode", sb, paramsMap, "=");

        Pagination<BaseUserGroupAssignEntity_HI_RO> findList = baseUserGroupAssignDAO_HI_RO.findPagination(sb,SaafToolUtils.getSqlCountString(sb),paramsMap,pageIndex,pageRows);
        return findList;
    }

    @Override
    public Pagination<BaseUserGroupAssignEntity_HI_RO> findUserGroupAssignsForGroup2(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        String userGroupCode = queryParamJSON.getString("userGroupCode");
        StringBuffer sb = new StringBuffer();
        sb.append(BaseUserGroupAssignEntity_HI_RO.QUERY);
        sb.append(" and t.user_group_code ='"+userGroupCode+"'");
        if(queryParamJSON.getString("userFullName")!=null && !"".equals(queryParamJSON.getString("userFullName"))){
            String userFullName = queryParamJSON.getString("userFullName");
            sb.append(" and bu.user_full_name ='"+userFullName+"'");
        }else {
            queryParamJSON.remove("userFullName");
        }
        if(queryParamJSON.getString("userName")!=null && !"".equals(queryParamJSON.getString("userName"))){
            String userName = queryParamJSON.getString("userName");
            sb.append(" and bu.user_name ='"+userName+"'");
        }else {
            queryParamJSON.remove("userName");
        }
        Map<String,Object> paramsMap = new HashMap<>();
        Pagination<BaseUserGroupAssignEntity_HI_RO> findList = baseUserGroupAssignDAO_HI_RO.findPagination(sb,SaafToolUtils.getSqlCountString(sb),paramsMap,pageIndex,pageRows);
        if(!queryParamJSON.containsKey("userName") && !queryParamJSON.containsKey("userFullName")){
            List<BaseUserGroupAssignEntity_HI_RO> ro = new ArrayList<>();
            findList.setData(ro);
            findList.setPagesCount(0);
            findList.setCount(0);
        }
        return findList;
    }

    @Override
    public List <BaseLookupValuesEntity_HI_RO> findNoAssignsForUser(JSONObject queryParamJSON) {
        String userId = queryParamJSON.getString("userId");
        StringBuffer sb = new StringBuffer();
        sb.append("select t1.* \n" +
                " from base_lookup_values t1\n" +
                " where t1.lookup_type = 'USER_GROUP'\n" +
                "   and t1.system_code = 'PUBLIC'\n" +
                "   and t1.ENABLED_FLAG = 'Y'\n" +
                "   and t1.delete_flag = 0\n" +
                "   and not exists(select null\n" +
                "                  from base_user_group_assign t2 \n" +
                "                  where t2.user_group_code = t1.lookup_code" +
                "   and t2.user_id= "+ userId +" )");
        Map<String,Object> paramsMap = new HashMap<>();
        List <BaseLookupValuesEntity_HI_RO> list = baseLookupValuesEntity_HI_RO.findList(sb);
        if(CollectionUtils.isEmpty(list)){
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public String saveUserGroupAssignsForUser(JSONObject queryParamJSON) {
        Integer userId = queryParamJSON.getInteger("userId");
        JSONArray jsonArray = queryParamJSON.getJSONArray("userGroups");
                if(!jsonArray.isEmpty()){
                    for (int i =0;i<jsonArray.size();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        BaseUserGroupAssignEntity_HI groupAssignEntity = new BaseUserGroupAssignEntity_HI();
                        groupAssignEntity.setCreatedBy(queryParamJSON.getInteger("varUserId"));
                        groupAssignEntity.setUserGroupCode(jsonObject.getString("userGroupCode"));
                        groupAssignEntity.setUserGroupName(jsonObject.getString("userGroupName"));
                        groupAssignEntity.setUserId(userId);
                        groupAssignEntity.setEnableFlag("Y");
                        baseUserGroupAssignDAO_HI.save(groupAssignEntity);
                    }
                }
        return "S";
    }


    @Override
    public String saveUserGroupAssignsForGroup(JSONObject queryParamJSON) {
        String userGroupCode = queryParamJSON.getString("userGroupCode");
        JSONArray jsonArray = queryParamJSON.getJSONArray("users");
        if(!jsonArray.isEmpty()){
            for (int i =0;i<jsonArray.size();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                BaseUserGroupAssignEntity_HI groupAssignEntity = new BaseUserGroupAssignEntity_HI();
                groupAssignEntity.setCreatedBy(queryParamJSON.getInteger("varUserId"));
                groupAssignEntity.setUserId(jsonObject.getInteger("userId"));
                groupAssignEntity.setUserGroupName(queryParamJSON.getString("userGroupName"));
                groupAssignEntity.setUserGroupCode(userGroupCode);
                groupAssignEntity.setEnableFlag("Y");
                baseUserGroupAssignDAO_HI.save(groupAssignEntity);
            }
        }
        return "S";
    }

    @Override
    public String deleteUserGroupAssignsForUser(JSONObject queryParamJSON) {
        JSONArray ids = queryParamJSON.getJSONArray("ids");
        Integer score=0;
        if (CollectionUtils.isNotEmpty(ids)) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("assignId", ids.toJavaList(Integer.class));
//            List<BaseUserGroupAssignEntity_HI> userGroupAssignList = this.baseUserGroupAssignDAO_HI.findList("from BaseUserGroupAssignEntity_HI where assignId in (:assignId)", paramsMap);
            if (CollectionUtils.isNotEmpty(ids)) {
                for (Integer id :ids.toJavaList(Integer.class)){
                    BaseUserGroupAssignEntity_HI entity_hi=  this.baseUserGroupAssignDAO_HI.getById(id);
                    if(!ObjectUtils.isEmpty(entity_hi)){
//                        baseUserGroupAssignDAO_HI.execute("delete from base_user_group_assign  where assign_id="+id);
                        baseUserGroupAssignDAO_HI.delete(id);
                        score++;
                    }
                }
            }
        }

        return score.toString();
    }
    @Override
    public String deleteUserGroupAssignsForGroup(JSONObject queryParamJSON) {
        JSONArray ids = queryParamJSON.getJSONArray("ids");
        Integer score=0;

        if (CollectionUtils.isNotEmpty(ids)) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("assignId", ids.toJavaList(Integer.class));
//            List<BaseUserGroupAssignEntity_HI> userGroupAssignList = this.baseUserGroupAssignDAO_HI.findList("from BaseUserGroupAssignEntity_HI where assignId in (:assignId)", paramsMap);
            if (CollectionUtils.isNotEmpty(ids)) {
                for (Integer id :ids.toJavaList(Integer.class)){
                    BaseUserGroupAssignEntity_HI entity_hi=  this.baseUserGroupAssignDAO_HI.getById(id);
                    if(!ObjectUtils.isEmpty(entity_hi)){
//                        baseUserGroupAssignDAO_HI.execute("delete from base_user_group_assign  where assign_id="+id);
                        baseUserGroupAssignDAO_HI.delete(id);

                        score++;
                    }
                }
            }
        }
        return score.toString();
    }
}
