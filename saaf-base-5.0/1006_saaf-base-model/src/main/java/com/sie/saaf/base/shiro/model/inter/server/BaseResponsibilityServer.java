package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.api.model.entities.BaseApiCenterHEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseProfileValueEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.*;
import com.sie.saaf.base.shiro.model.inter.IBaseProfileValue;
import com.sie.saaf.base.shiro.model.inter.IBaseResponsibility;
import com.sie.saaf.base.shiro.model.inter.IBaseResponsibilityRole;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 对职责表base_responsibility进行CRUD操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/13
 */
@Component("baseResponsibilityServer")
public class BaseResponsibilityServer extends BaseCommonServer<BaseResponsibilityEntity_HI> implements IBaseResponsibility {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseResponsibilityServer.class);
    @Autowired
    private ViewObject<BaseResponsibilityEntity_HI>      baseResponsibilityDAO_HI;
    @Autowired
    private BaseViewObject<BaseResponsibilityRole_HI_RO> baseResponsibilityRoleDAO_HI_RO;
    @Autowired
    private BaseViewObject<BaseUserResponsibility_HI_RO> baseUserResponsibilityDAO_HI_RO;
    @Autowired
    private BaseViewObject<BaseProfileValue_HI_RO>       baseProfileValueDAO_HI_RO;
    @Autowired
    private BaseViewObject<BaseResponsibilityUserEntity_HI_RO> baseResponsibilityUserDAO_HI_RO;
    @Autowired
    private IBaseProfileValue baseProfileValueServer;
    @Autowired
    private IBaseResponsibilityRole baseResponsibilityRoleServer;

    public static final String PROFILE_KEY_TABLE_NAME = "base_responsibility";

    /**
     * 分页查询职责列表
     *
     * @param queryParamJSON JSON查询参数<br>
     *                       {<br>
     *                       responsibilityName:职责名称<br>
     *                       responsibilityCode:职责编码<br>
     *                       startDateActive:生效时间<br>
     *                       endDateActive:生效时间<br>
     *                       }
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 分页对象列表<br>
     * { <br>
     * count: 总记录数,<br>
     * curIndex: 当前页索引,<br>
     * data: [{<br>
     * responsibilityId:职责标识<br>
     * responsibilityCode:职责编号<br>
     * responsibilityName:职责名称<br>
     * responsibilityDesc:职责描述<br>
     * startDateActive:生效时间<br>
     * endDateActive:失效时间<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdatedBy:更新人<br>
     * lastUpdateDate:更新日期<br>
     * versionNum:版本号<br>
     * }],<br>
     * firstIndex: 首页索引,<br>
     * lastIndex: 尾页索引,<br>
     * nextIndex: 下一页索引,<br>
     * pageSize: 每页记录数,<br>
     * pagesCount: 总页数,<br>
     * preIndex: 上一页索引<br>
     * }
     * @author ZhangJun
     * @creteTime 2017/12/13
     */
    @Override
    public Pagination<BaseResponsibilityEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String,Object> queryParamMap = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        if(queryParamJSON.containsKey("isEfficacious")){
            if(queryParamJSON.getBooleanValue("isEfficacious")) {// 生效
                sb.append(" FROM BaseResponsibilityEntity_HI baseResponsibility where baseResponsibility.startDateActive<=:startDateActive and (baseResponsibility.endDateActive is null or baseResponsibility.endDateActive>=:endDateActive) ");
                queryParamMap.put("startDateActive", new Date());
                queryParamMap.put("endDateActive", new Date());
            } else { // 失效
                sb.append(" FROM BaseResponsibilityEntity_HI baseResponsibility where (baseResponsibility.startDateActive>=:startDateActive or baseResponsibility.endDateActive<=:endDateActive) ");
                queryParamMap.put("startDateActive", new Date());
                queryParamMap.put("endDateActive", new Date());
            }
        }else {
            queryParamMap.put("startDateActive", new Date());
            queryParamMap.put("endDateActive", new Date());
            sb.append(" FROM BaseResponsibilityEntity_HI baseResponsibility where 1=1 and baseResponsibility.startDateActive<=:startDateActive and (baseResponsibility.endDateActive is null or baseResponsibility.endDateActive>=:endDateActive)");
        }

        if(!"Y".equals(queryParamJSON.getString("varIsadmin"))){
            queryParamMap.put("varUserId",queryParamJSON.getInteger("varUserId"));
            queryParamMap.put("varStartDateActive", new Date());
            queryParamMap.put("varEndDateActive", new Date());
            sb.append(" and exists ( from BaseUserResponsibilityEntity_HI userResp where userResp.responsibilityId = baseResponsibility.responsibilityId and userResp.userId = :varUserId and userResp.startDateActive<=:varStartDateActive and (userResp.endDateActive is null or userResp.endDateActive>=:varEndDateActive)  )");
        }

        if(null !=queryParamJSON.getDate("startDateActive_lt")){
            queryParamJSON.put("startDateActive_lt", SToolUtils.addDate(1, Calendar.DATE, queryParamJSON.getDate("startDateActive_lt")));
        }
        StringBuffer countSQL = new StringBuffer("select count(*) ").append(sb);

        //SaafToolUtils.parperHbmParam(BaseResponsibilityEntity_HI.class, queryParamJSON, sb, queryParamMap);
        //SaafToolUtils.parperHbmParam(BaseResponsibilityEntity_HI.class, queryParamJSON, countSQL, queryParamMap);
        SaafToolUtils.parperHbmParam(BaseResponsibilityEntity_HI.class,queryParamJSON, "responsibilityName", "responsibilityName", sb, queryParamMap, "like");
        SaafToolUtils.parperHbmParam(BaseResponsibilityEntity_HI.class,queryParamJSON, "responsibilityName", "responsibilityName", countSQL, queryParamMap, "like");
        return baseResponsibilityDAO_HI.findPagination(sb,countSQL,queryParamMap,pageIndex,pageRows);
    }

    public Pagination<BaseResponsibilityUserEntity_HI_RO> findCurrentResponsibilityPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String,Object> queryParamMap = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(BaseResponsibilityUserEntity_HI_RO.QUERY_RESP_USER_SQL);
        queryParamMap.put("varUserId",queryParamJSON.getInteger("varUserId"));
        StringBuffer countSQL = new StringBuffer("select count(*) from (").append(sb);
        SaafToolUtils.parperHbmParam(BaseResponsibilityEntity_HI.class, queryParamJSON, sb, queryParamMap);
        SaafToolUtils.parperHbmParam(BaseResponsibilityEntity_HI.class, queryParamJSON, countSQL, queryParamMap);
        countSQL.append(" ) ");
        return baseResponsibilityUserDAO_HI_RO.findPagination(sb,countSQL,queryParamMap,pageIndex,pageRows);
    }

    public List<BaseResponsibilityUserEntity_HI_RO> findBaseResponsibilityUser(Integer userId){
      Map<String,Object> queryParamMap = new HashMap<>();
      StringBuffer sb = new StringBuffer();
      sb.append(BaseResponsibilityUserEntity_HI_RO.QUERY_RESP_USER_SQL);
      queryParamMap.put("varUserId",userId);
      return baseResponsibilityUserDAO_HI_RO.findList(sb,queryParamMap);
    }
    /**
     * 保存一条记录
     *
     * @param queryParamJSON 保存参数<br>
     *                       {
     *                       responsibilityId:职责Id,<br>
     *                       responsibilityCode:职责编号,<br>
     *                       responsibilityName:职责名称,<br>
     *                       responsibilityDesc:职责描述,<br>
     *                       startDateActive:生效时间,<br>
     *                       endDateActive:失效时间,<br>
     *                       versionNum:版本号,<br>
     *                       operatorUserId:操作者Id,<br>
     *                       }
     * @return BaseResponsibilityEntity_HI对象
     * @author ZhangJun
     * @createTime 2018/1/12 11:44
     * @description 保存一条记录
     */
    @Override
    public BaseResponsibilityEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
        return super.saveOrUpdate(queryParamJSON);
    }

    /**
     * 保存一条记录
     *
     * @param queryParamJSON 保存的数据参数<br>
     *                       {<br>
     *                       responsibilityId:职责Id,<br>
     *                       responsibilityCode:职责编号,<br>
     *                       responsibilityName:职责名称,<br>
     *                       responsibilityDesc:职责描述,<br>
     *                       startDateActive:生效时间,<br>
     *                       endDateActive:失效时间,<br>
     *                       versionNum:版本号,<br>
     *                       operatorUserId:操作者Id,<br>
     *                       roleIds:[1,2,3,4],//角色Id<br>
     *                       profiles:[{<br>
     *                       profileValueId:profile行表Id,<br>
     *                       profileId:profile主键,<br>
     *                       profileValue:profile值<br>
     *                       versionNum:版本号<br>
     *                       }]<br>
     *                       }
     * @return {@link BaseRespRoleProfile_HI_RO}
     * @author ZhangJun
     * @creteTime 2017/12/13
     */
    @Override
    public BaseRespRoleProfile_HI_RO saveRespRoleProfile(JSONObject queryParamJSON) throws Exception {

        Integer                     operatorUserId     = queryParamJSON.getInteger("operatorUserId");
        BaseResponsibilityEntity_HI baseResponsibility = super.saveOrUpdate(queryParamJSON);

        BaseRespRoleProfile_HI_RO returnEntity = new BaseRespRoleProfile_HI_RO();
        BeanUtils.copyProperties(baseResponsibility, returnEntity);

		/*=======保存职责角色========*/
        JSONObject roleJSON = new JSONObject();
        roleJSON.put("responsibilityId", baseResponsibility.getResponsibilityId());
        roleJSON.put("operatorUserId", operatorUserId);
        roleJSON.put("roleIds", new JSONArray());
        if (queryParamJSON.containsKey("respRoles")) {
            //传了roles
            JSONArray roleIds    = new JSONArray();
            JSONArray rolesArray = queryParamJSON.getJSONArray("respRoles");
            if (rolesArray != null && !rolesArray.isEmpty()) {
                for (int i = 0; i < rolesArray.size(); i++) {
                    JSONObject roleobjJSON = rolesArray.getJSONObject(i);
                    roleIds.add(roleobjJSON.getInteger("roleId"));
                }
            }
            roleJSON.put("roleIds", roleIds);
        }
        JSONArray respRolesJSON = this.baseResponsibilityRoleServer.saveRespRole(roleJSON);
        returnEntity.setRespRoles(respRolesJSON);
        /*=======保存职责角色========*/


        //保存ProfileValue
        List<BaseProfileValueEntity_HI> profileValues = this.saveBaseProfileValue(queryParamJSON, baseResponsibility);
        returnEntity.setProfileValues(JSONArray.parseArray(JSON.toJSONString(profileValues)));

        return returnEntity;
    }

    /**
     * 保存profileValue
     *
     * @author ZhangJun
     * @createTime 2018/1/23 10:13
     * @description 保存profileValue
     */
    private List<BaseProfileValueEntity_HI> saveBaseProfileValue(JSONObject queryParamJSON, BaseResponsibilityEntity_HI baseResponsibility) throws Exception {
        Integer operatorUserId = queryParamJSON.getInteger("operatorUserId");
        String  systemCode     = queryParamJSON.getString("systemCode");

        List<BaseProfileValueEntity_HI> newProfiles = new ArrayList<>();
        if (queryParamJSON.containsKey("profileValues")) {
            //传了profiles
            JSONArray profilesArray = queryParamJSON.getJSONArray("profileValues");
            Set<String> exist=new HashSet<>();
            if (profilesArray != null && !profilesArray.isEmpty()) {
                //查询profileValue，在后面循环时，移除掉添加或更新的数据，剩下的则是需要删除的数据
                List<BaseProfileValueEntity_HI>         profileValueList = baseProfileValueServer.findList(PROFILE_KEY_TABLE_NAME, baseResponsibility.getResponsibilityId());
                Map<Integer, BaseProfileValueEntity_HI> profileValueMap  = new HashMap<>();

                if (profileValueList != null && !profileValueList.isEmpty()) {
                    for (BaseProfileValueEntity_HI entity : profileValueList) {
                        if (entity.getDeleteFlag().intValue() == CommonConstants.DELETE_FALSE) {
                            profileValueMap.put(entity.getProfileValueId(), entity);
                        }
                    }
                }
                //查询end


                for (int i = 0; i < profilesArray.size(); i++) {
                    JSONObject profileJSON = profilesArray.getJSONObject(i);
                    if (!exist.add(profileJSON.getString("profileId")+"-"+profileJSON.getString("1015615101"))){
                        throw new IllegalArgumentException("profile 配置重复");
                    }
                    if (profileJSON.containsKey("profileValueId")) {
                        //存在主键，则这条数据需要更新，从profileValueMap中移除
                        BaseProfileValueEntity_HI profileValue = profileValueMap.get(profileJSON.getInteger("profileValueId"));
                        if (null != profileValue) {
                            BaseProfileValueEntity_HI baseProfileValueEntity_hi = JSON.parseObject(profileJSON.toJSONString(), BaseProfileValueEntity_HI.class);
                            SaafBeanUtils.copyUnionProperties(baseProfileValueEntity_hi, profileValue);
                            this.baseProfileValueServer.saveOrUpdate(profileValue);
                            profileValueMap.remove(profileJSON.getInteger("profileValueId"));
                            newProfiles.add(profileValue);
                        }
                        continue;
                    }

                    profileJSON.put("businessKey", baseResponsibility.getResponsibilityId());
                    profileJSON.put("keyTableName", PROFILE_KEY_TABLE_NAME);
                    profileJSON.put("deleteFlag", CommonConstants.DELETE_FALSE);
                    profileJSON.put("operatorUserId", operatorUserId);
                    profileJSON.put("systemCode", systemCode);
                    BaseProfileValueEntity_HI profileValue = this.baseProfileValueServer.saveOrUpdate(profileJSON);
                    newProfiles.add(profileValue);

                }

                if (!profileValueMap.isEmpty()) {
                    //到此处，profileValueMap中已移除更新的数据，剩下的则是需要删除的，直接设置状态标记deleteFlag = 1
                    Collection<BaseProfileValueEntity_HI> deleteValues = profileValueMap.values();
                    Iterator<BaseProfileValueEntity_HI>   it           = deleteValues.iterator();
                    while (it.hasNext()) {
                        BaseProfileValueEntity_HI deleteEntity = it.next();
                        deleteEntity.setDeleteFlag(CommonConstants.DELETE_TRUE);
                        this.baseProfileValueServer.saveOrUpdate(deleteEntity);
                    }
                }
            } else {
                saveAllProfiles(baseResponsibility.getResponsibilityId());
            }
        } else {
            saveAllProfiles(baseResponsibility.getResponsibilityId());
        }

        return newProfiles;
    }

    /**
     * 删除职责对应的所有的profileValue
     *
     * @author ZhangJun
     * @createTime 2018/1/23 10:32
     * @description 删除职责对应的所有的profileValue
     */
    private void saveAllProfiles(Integer responsibilityId) {
        List<BaseProfileValueEntity_HI> list = baseProfileValueServer.findList(PROFILE_KEY_TABLE_NAME, responsibilityId);
        if (list != null && !list.isEmpty()) {
            for (BaseProfileValueEntity_HI entity : list) {
                entity.setDeleteFlag(CommonConstants.DELETE_TRUE);
                this.baseProfileValueServer.saveOrUpdate(entity);
            }
        }
    }

    @Override
    protected void setEntityDefaultValue(BaseResponsibilityEntity_HI entity) {
        if (entity.getStartDateActive() == null) {
            entity.setStartDateActive(new Date());
        }
    }

    /**
     * 设置查询条件
     *
     * @param queryParamJSON 入参
     * @param paramsMap      sql或hql参数
     * @param sql            sql或hql
     * @param isHql          是否HQL查询，如果是HQL查询，自动将查询字段转换为对象属性
     */
    @Override
    protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
        SaafToolUtils.parperParam(queryParamJSON, "baseResponsibility.responsibility_name", "responsibilityName", sql, paramsMap, "like", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseResponsibility.responsibility_code", "responsibilityCode", sql, paramsMap, "=", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseResponsibility.responsibility_desc", "responsibilityDesc", sql, paramsMap, "=", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseRole.role_id", "roleId", sql, paramsMap, "=", isHql);
        SaafToolUtils.parperParam(queryParamJSON, "baseResponsibility.system_code", "systemCode", sql, paramsMap, "=", isHql);
        boolean isValid = false;
        if (queryParamJSON.containsKey("isValid")) {
            isValid = queryParamJSON.getBooleanValue("isValid");
        }
        if (isValid) {
            // 查询有效的
            if (isHql) {
                sql.append(
                        " and baseResponsibility.startDateActive<=:startDateActive and (baseResponsibility.endDateActive is null or baseResponsibility.endDateActive>=:endDateActive) ");
            } else {
                sql.append(
                        " and baseResponsibility.start_date_active<=:startDateActive and (baseResponsibility.end_date_active is null or baseResponsibility.end_date_active>=:endDateActive) ");
            }
            paramsMap.put("startDateActive", new Date());
            paramsMap.put("endDateActive", new Date());
        } else {
            if (isHql) {
                //
                if (queryParamJSON.containsKey("startDateActive") && StringUtils.isNotBlank(queryParamJSON.getString("startDateActive"))) {
                    sql.append(" and baseResponsibility.startDateActive>=:startDateActive");
                    paramsMap.put("startDateActive", queryParamJSON.getDate("startDateActive"));
                }
                if (queryParamJSON.containsKey("endDateActive") && StringUtils.isNotBlank(queryParamJSON.getString("endDateActive"))) {
                    sql.append(" and baseResponsibility.endDateActive<=:endDateActive");
                    paramsMap.put("endDateActive", queryParamJSON.getDate("endDateActive"));
                }
            } else {
                SaafToolUtils.parperParam(queryParamJSON, "baseResponsibility.start_date_active", "startDateActive", sql, paramsMap, ">=", isHql);
                SaafToolUtils.parperParam(queryParamJSON, "baseResponsibility.end_date_active", "endDateActive", sql, paramsMap, "<=", isHql);
            }
        }
    }

    /**
     * 根据角色Id查询分配的职责
     *
     * @param roleId 角色Id
     * @return 职责与角色关系列表<br>
     * [{<br>
     * responsibilityId:职责标识,<br>
     * responsibilityCode:职责编号,<br>
     * responsibilityName:职责名称,<br>
     * responsibilityDesc:职责描述,<br>
     * startDateActive:生效时间,<br>
     * endDateActive:失效时间,<br>
     * creationDate:创建日期,<br>
     * createdBy:创建人,<br>
     * lastUpdatedBy:更新人,<br>
     * lastUpdateDate:更新日期,<br>
     * versionNum:版本号,<br>
     * roleId:角色Id,<br>
     * roleName:角色名称,<br>
     * roleCode:角色编号,<br>
     * roleDesc:角色描述,<br>
     * systemCode:系统编码,<br>
     * roleStartDateActive:生效时间,<br>
     * roleEndDateActive:失效时间,<br>
     * roleVersionNum:版本号,<br>
     * }]
     * @author ZhangJun
     * @creteTime 2017/12/13
     */
    @Override
    public List<BaseResponsibilityRole_HI_RO> findResponsibilityByRoleId(Integer roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("缺少参数 roleId");
        }
        JSONObject queryParamJSON = new JSONObject();
        queryParamJSON.put("roleId", roleId);

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        StringBuffer        sb        = new StringBuffer();
        sb.append(BaseResponsibilityRole_HI_RO.QUERY_RESP_ROLE_SQL);
        changeQuerySql(queryParamJSON, paramsMap, sb, false);
        List<BaseResponsibilityRole_HI_RO> findList = baseResponsibilityRoleDAO_HI_RO.findList(sb, paramsMap);
        return findList;
    }

    /**
     * 根据用户Id查询职责
     *
     * @param userId 用户Id
     * @return 用户与职责关系列表<br>
     * [{<br>
     * responsibilityId:职责标识<br>
     * responsibilityCode:职责编号<br>
     * responsibilityName:职责名称<br>
     * responsibilityDesc:职责描述<br>
     * startDateActive:生效时间<br>
     * endDateActive:失效时间<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdatedBy:更新人<br>
     * lastUpdateDate:更新日期<br>
     * versionNum:版本号<br>
     * }]
     * @author ZhangJun
     * @creteTime 2017/12/13
     */
    @Override
    public List<BaseUserResponsibility_HI_RO> findResponsibilityByUserId(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("缺少参数 userId");
        }
        JSONObject          queryParamJSON = new JSONObject();
        Map<String, Object> paramsMap      = new HashMap<String, Object>();
        StringBuffer        sb             = new StringBuffer();
        sb.append(BaseUserResponsibility_HI_RO.QUERY_RESPONSIBILITY_BY_USERID_SQL);
        sb.append(" AND baseUsers.user_id=:userId AND (baseUsers.start_date-1)<=CURRENT_DATE  AND( baseUsers.end_date IS NULL OR baseUsers.end_date >=CURRENT_DATE ) " +
                "AND (baseUserResponsibility.end_date_active > sysdate or baseUserResponsibility.end_date_active is null) AND baseUsers.delete_flag =:deleteFalse");
        paramsMap.put("deleteFalse", CommonConstants.DELETE_FALSE);
        paramsMap.put("userId", userId);
        changeQuerySql(queryParamJSON, paramsMap, sb, false);

        List<BaseUserResponsibility_HI_RO> findList = baseUserResponsibilityDAO_HI_RO.findList(sb, paramsMap);
        return findList;
    }

    /**
     * 根据Id查询职责，职责角色，职责profile
     *
     * @param id 职责Id
     * @author ZhangJun
     * @createTime 2018/1/12 15:43
     * @description 根据Id查询职责
     */
    @Override
    public JSONObject findRespRoleProfileById(Integer id) {
        BaseResponsibilityEntity_HI baseResponsibility = this.getById(id);
        Integer                     responsibilityId   = baseResponsibility.getResponsibilityId();

        JSONObject entityJSON = JSONObject.parseObject(JSON.toJSONString(baseResponsibility));

        StringBuffer sb = new StringBuffer(BaseResponsibilityRole_HI_RO.QUERY_ROLE_RESPONSIBILITY_SQL);
        sb.append(" and baseResponsibilityRole.responsibility_Id=:responsibilityId");
        JSONObject queryJSON = new JSONObject();
        queryJSON.put("responsibilityId", responsibilityId);
        List<BaseResponsibilityRole_HI_RO> respRoles = baseResponsibilityRoleDAO_HI_RO.findList(sb, queryJSON);
        entityJSON.put("respRoles", JSONArray.parseArray(JSON.toJSONString(respRoles)));

        StringBuffer sb2 = new StringBuffer(BaseProfileValue_HI_RO.QUERY_PROFILE_VALUE_QUERY);
        sb2.append(" and baseProfileValue.business_key = :businessKey and baseProfileValue.key_table_name = :keyTableName and baseProfileValue.delete_flag=:deleteFlag");
        JSONObject queryJSON2 = new JSONObject();
        queryJSON2.put("keyTableName", PROFILE_KEY_TABLE_NAME);
        queryJSON2.put("businessKey", String.valueOf(responsibilityId));
        queryJSON2.put("deleteFlag", CommonConstants.DELETE_FALSE);

        List<BaseProfileValue_HI_RO> profileValues = this.baseProfileValueDAO_HI_RO.findList(sb2, queryJSON2);
        entityJSON.put("profileValues", JSONArray.parseArray(JSON.toJSONString(profileValues)));

        return entityJSON;
    }

    /**
     * @author YangXiaowei
     * @creteTime 2018/4/14
     * @description 职责新增判断是否重复
     */
    @Override
    public void checkOut(JSONObject queryParamJSON) {

        SaafToolUtils.validateJsonParms(queryParamJSON, "responsibilityName", "responsibilityCode", "systemCode");
        String  name       = queryParamJSON.getString("responsibilityName");
        String  code       = queryParamJSON.getString("responsibilityCode");
        String  systemCode = queryParamJSON.getString("systemCode");
        Integer id         = queryParamJSON.getInteger("responsibilityId");

        StringBuffer findsql = new StringBuffer(" from BaseResponsibilityEntity_HI where systemCode = :systemCode " +
                " and (responsibilityName = :responsibilityName or responsibilityCode =:responsibilityCode )");
        HashMap<String, Object> map = new HashMap<>();
        map.put("systemCode", systemCode);
        map.put("responsibilityName", name);
        map.put("responsibilityCode", code);
        List<BaseResponsibilityEntity_HI> list = baseResponsibilityDAO_HI.findList(findsql, map);

        if (list.size() > 0) {
            if (id == null) {
                throw new IllegalStateException(systemCode + "系统中已存在相同的职责编码或名称");
            } else {
                if (list.size() > 1) {
                    throw new IllegalStateException(systemCode + "系统中已存在相同的职责编码或名称");
                }
                if (!id.equals(list.get(0).getResponsibilityId())) {
                    throw new IllegalStateException(systemCode + "系统中已存在相同的职责编码或名称");
                }
            }
        }
    }
}
