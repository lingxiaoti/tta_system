package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BasePdaRoleCfgEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseUserResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.inter.server.BasePdaRoleCfgServer;
import com.sie.saaf.base.user.model.entities.BasePdaInvCfgEntity_HI;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPDAAutoCreate_HI_RO;
import com.sie.saaf.base.user.model.inter.IPdaUserAutoCreate;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangminglin on 2018/3/28.
 */
@Component("pdaUserAutoCreateServer")
public class PdaUserAutoCreateServer extends BaseCommonServer<BaseUsersEntity_HI> implements IPdaUserAutoCreate {

//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;

    @Autowired
    private ViewObject<BaseUsersEntity_HI> baseUsersDAO_HI;

//    @Autowired
//    private BaseViewObject<BaseUsersPDAAutoCreate_HI_RO> baseUsersPDAAutoCreateDAO_HI_RO;

    @Autowired
    private ViewObject<BasePdaInvCfgEntity_HI>           basePdaInvCfgDAO_HI;

    @Autowired
    private ViewObject<BaseUserResponsibilityEntity_HI> baseUserResponsibilityDAO_HI;

    @Autowired
    private ViewObject<BasePdaRoleCfgEntity_HI> basePdaRoleCfgDAO_HI;

    @Autowired
    private BasePdaRoleCfgServer basePdaRoleCfgServer;

    /**
     * PDA用户账号自动创建
     *
     * @param queryParamJSON
     * @return
     * @throws SQLException
     */
    public JSONObject pdaUserAutoCreate(JSONObject queryParamJSON) throws SQLException {
        Integer pHours = queryParamJSON.getInteger("pHours");

        Map<String, Object> params = new HashMap<>();
        params.put("pHours", pHours.intValue());

        JSONObject result = new JSONObject();

        //直连ERP数据库，根据SQL抓取数据
//        List<JSONObject> list = oracleTemplateServer.findList(BaseUsersPDAAutoCreate_HI_RO.PDA_USER_QUERY_ORACLE, params);
//
//        if (list != null && !list.isEmpty()) {
//            int count = 0;
//            for (JSONObject jsonObj : list) {
//                BaseUsersPDAAutoCreate_HI_RO entitie = JSON.toJavaObject(jsonObj, BaseUsersPDAAutoCreate_HI_RO.class);
//
//                if (entitie.getInvCode() != null) {
//
//
//                    //直连BRC数据库，执行以下判断是否已创建
//                    Map<String, Object> paramsMap = new HashMap<>();
//                    paramsMap.put("invCode", entitie.getInvCode());
//
//                    List<BaseUsersPDAAutoCreate_HI_RO> baseUsersPDAAutoCreateHiRo   = baseUsersPDAAutoCreateDAO_HI_RO.findList(BaseUsersPDAAutoCreate_HI_RO.BASE_USERS_IS_HAS, paramsMap);
//                    BaseUsersPDAAutoCreate_HI_RO       baseUsersPDAAutoCreate_hi_ro = baseUsersPDAAutoCreateHiRo.get(0);
//
//                    if (baseUsersPDAAutoCreate_hi_ro.getCount() == 0) {
//                        this.createPdaUser(entitie);
//                        count++;
//                        result.put("status", "S");
//                        result.put("count", count);
//                        result.put("msg", "自动创建用户:[" + count + "]个");
//                    }
//                }
//            }
//
//            if (count == 0) {
//                result.put("status", "S");
//                result.put("count", 0);
//                result.put("msg", "没有需要创建的用户");
//            }
//        } else {
//            result.put("status", "S");
//            result.put("count", 0);
//            result.put("msg", "没有需要创建的用户");
//        }

        return result;
    }

    //保存事务，循环体内事务，减少事务时间
    @Transactional(propagation = Propagation.REQUIRED)
    public void createPdaUser(BaseUsersPDAAutoCreate_HI_RO entitie) {

        //创建一个新User对象
        BaseUsersEntity_HI baseUsersEntityHi = new BaseUsersEntity_HI();

        baseUsersEntityHi.setUserName(entitie.getInvCode() + "01");
        baseUsersEntityHi.setEncryptedPassword("e10adc3949ba59abbe56e057f20f883e");
        baseUsersEntityHi.setUserFullName(entitie.getInvCode() + "01");
        baseUsersEntityHi.setUserDesc(entitie.getInvCode() + "01");
        baseUsersEntityHi.setNamePingyin(entitie.getInvCode() + "01");
        baseUsersEntityHi.setUserType("60");
        baseUsersEntityHi.setOperatorUserId(1);
        baseUsersEntityHi.setStartDate(new Date());
        baseUsersEntityHi.setDeleteFlag(0);

        Integer userId = (Integer) baseUsersDAO_HI.save(baseUsersEntityHi);

        //根据组织变化插入参数
        Integer organizationId = 244;
        String  channelCode    = "0";
        Integer roleId         = 120003;

        if (entitie.getOrganizationId() == null || (!"262".equals(entitie.getOrganizationId().toString()) &&
                !"103".equals(entitie.getOrganizationId().toString()) &&
                !"162".equals(entitie.getOrganizationId().toString()))) {
            organizationId = entitie.getOrganizationId();
            channelCode = entitie.getChannelCode();
            roleId = 150003;
        }

        //插入参数
        BasePdaInvCfgEntity_HI basePdaInvCfgEntityHi = new BasePdaInvCfgEntity_HI();

        basePdaInvCfgEntityHi.setSubInvCode(entitie.getInvCode());
        basePdaInvCfgEntityHi.setOrganizationId(organizationId);
        basePdaInvCfgEntityHi.setChannelCode(channelCode);
        basePdaInvCfgEntityHi.setRoleId(roleId);
        basePdaInvCfgEntityHi.setUserId(userId);
        basePdaInvCfgEntityHi.setDeleteFlag(0);
        basePdaInvCfgEntityHi.setOperatorUserId(1);

        basePdaInvCfgDAO_HI.save(basePdaInvCfgEntityHi);

        if(roleId == 0){
            this.baseUserResponsibilityCreate(userId,150005);
        }else{
            this.baseUserResponsibilityCreate(userId,150004);
        }

        basePdaRoleCfgCreate(roleId,organizationId,channelCode);

    }

    private void baseUserResponsibilityCreate(Integer userId , Integer responsibilityId){
        BaseUserResponsibilityEntity_HI baseUserResponsibilityEntityHi = new BaseUserResponsibilityEntity_HI();
        baseUserResponsibilityEntityHi.setUserId(userId);
        baseUserResponsibilityEntityHi.setResponsibilityId(responsibilityId);
        baseUserResponsibilityEntityHi.setStartDateActive(new Date());
        baseUserResponsibilityEntityHi.setCreationDate(new Date());
        baseUserResponsibilityEntityHi.setCreatedBy(-1);
        baseUserResponsibilityEntityHi.setVersionNum(0);
        baseUserResponsibilityEntityHi.setOperatorUserId(-1);
        baseUserResponsibilityEntityHi.setLastUpdateDate(new Date());

        baseUserResponsibilityDAO_HI.saveOrUpdate(baseUserResponsibilityEntityHi);
    }

    private void basePdaRoleCfgCreate(Integer roleId,Integer organizationId,String channelCode){
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("organizationId",organizationId);
        paramsMap.put("channelCode",channelCode);
        paramsMap.put("roleId",roleId);

        StringBuffer countSql = new StringBuffer("select count(pdaRole.pdaRoleCfgId) from BasePdaRoleCfgEntity_HI pdaRole " +
                "where pdaRole.organizationId = :organizationId and pdaRole.channelCode = :channelCode and pdaRole.roleId = :roleId ");

        Integer count = basePdaRoleCfgDAO_HI.count(countSql, paramsMap);

        if(count == 0){

            String sql = " from BasePdaRoleCfgEntity_HI a where enabled = 'Y' and roleId = :roleId GROUP BY a.menuId";

            StringBuffer accountSql = new StringBuffer(sql.replace(":roleId","150003"));
            StringBuffer totalSql = new StringBuffer(sql.replace(":roleId","120003"));

            Map<String,Object> map = new HashMap<>();
            List<BasePdaRoleCfgEntity_HI> accountList = basePdaRoleCfgDAO_HI.findList(accountSql, map);
            List<BasePdaRoleCfgEntity_HI> totalList = basePdaRoleCfgDAO_HI.findList(totalSql, map);

            //转换成list

            if(roleId == 150003){
                for(int i=0 ;i < accountList.size(); i++){
                    BasePdaRoleCfgEntity_HI basePdaRoleCfgEntityHi = new BasePdaRoleCfgEntity_HI();
                    basePdaRoleCfgEntityHi.setRoleId(roleId);
                    basePdaRoleCfgEntityHi.setOrganizationId(organizationId);
                    basePdaRoleCfgEntityHi.setChannelCode(channelCode);
                    basePdaRoleCfgEntityHi.setMenuId(accountList.get(i).getMenuId());
                    basePdaRoleCfgEntityHi.setEnabled("Y");
                    basePdaRoleCfgEntityHi.setCreationDate(new Date());
                    basePdaRoleCfgEntityHi.setCreatedBy(-1);
                    basePdaRoleCfgEntityHi.setOperatorUserId(-1);
                    basePdaRoleCfgEntityHi.setVersionNum(0);
                    basePdaRoleCfgServer.saveOrUpdate(basePdaRoleCfgEntityHi);
                }
            }else{
                for(int i=0 ;i < totalList.size(); i++){
                    BasePdaRoleCfgEntity_HI basePdaRoleCfgEntityHi = new BasePdaRoleCfgEntity_HI();
                    basePdaRoleCfgEntityHi.setRoleId(roleId);
                    basePdaRoleCfgEntityHi.setOrganizationId(organizationId);
                    basePdaRoleCfgEntityHi.setChannelCode(channelCode);
                    basePdaRoleCfgEntityHi.setMenuId(totalList.get(i).getMenuId());
                    basePdaRoleCfgEntityHi.setEnabled("Y");
                    basePdaRoleCfgEntityHi.setCreationDate(new Date());
                    basePdaRoleCfgEntityHi.setCreatedBy(-1);
                    basePdaRoleCfgEntityHi.setOperatorUserId(-1);
                    basePdaRoleCfgEntityHi.setVersionNum(0);
                    basePdaRoleCfgServer.saveOrUpdate(basePdaRoleCfgEntityHi);
                }
            }
        }
    }

}
