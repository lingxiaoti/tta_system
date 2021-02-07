package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseUserSyncServer;
import com.sie.saaf.common.util.Chinese2PinyinUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by husaiqiang on 2018/4/11.
 */
@Component("baseUserSyncServer")
public class BaseUserSyncServer implements IBaseUserSyncServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseUserSyncServer.class);

    @Autowired
    private ViewObject<BaseUsersEntity_HI> baseUsersDAO_HI;

    @Autowired
    private ViewObject<BasePersonEntity_HI> basepersonDAO_HI;



//    private BasePersonEntity_HI packingPersonEntity(JSONObject person) {
//        BasePersonEntity_HI entity = new BasePersonEntity_HI();
//        //修改了BasePersonEntity_HI的属性，sourceSystemId -> sourceId modified on 2018-06-22
//        entity.setSourceId(person.getInteger("PERSON_ID"));
//        entity.setEmployeeNumber(person.getString("EMPLOYEE_NUMBER"));
//        entity.setPersonName(person.getString("PERSON_NAME"));
//        entity.setPersonType(person.getString("PERSON_TYPE"));
//        entity.setSex(person.getString("SEX"));
//        entity.setBirthDay(person.getDate("DATE_OF_BIRTH"));
//        entity.setCardNo(person.getString("CARD_NO"));
//        entity.setTelPhone(person.getString("TEL_PHONE"));
//        entity.setMobilePhone(person.getString("MOBILE_PHONE"));
//        entity.setEmail(person.getString("EMAIL_ADDRESS"));
//        entity.setPostalAddress(person.getString("POSTAL_ADDRESS"));
//        entity.setPostcode(person.getString("POSTCODE"));
//        return entity;
//    }

    @Override
    public BaseUsersEntity_HI packingUserEntity(JSONObject user) {
        BaseUsersEntity_HI entity = new BaseUsersEntity_HI();
        entity.setPhoneNumber(user.getString("PHONE_NUMBER"));
        entity.setNamePingyin(user.getString("NAME_PINYIN"));
        entity.setNameSimplePinyin(user.getString("NAME_SIMPLE_PINYIN"));
        entity.setIsadmin(user.getString("ISADMIN"));
        entity.setUserName(user.getString("USER_NAME"));
        entity.setUserDesc(user.getString("USER_DESC"));
        entity.setUserType(user.getString("USER_TYPE"));
        entity.setUserFullName(user.getString("USER_FULL_NAME"));
        entity.setEncryptedPassword("e10adc3949ba59abbe56e057f20f883e");
        entity.setInternalUser(user.getString("INTERNAL_USER"));
        entity.setUserHeadImgUrl(user.getString("USER_HEAD_IMG_URL"));
        entity.setStartDate(user.getDate("START_DATE"));
        entity.setEndDate(user.getDate("END_DATE"));
        entity.setSourceId(user.getString("USER_ID"));
        if (!"0".equals(user.getString("PERSON_ID"))){
            entity.setPersonId(user.getInteger("PERSON_ID"));
        } else{
            entity.setPersonId(-1);
        }
        entity.setDeleteFlag(0);
        if (StringUtils.isNotBlank(entity.getUserFullName())){
            entity.setNamePingyin(Chinese2PinyinUtil.convertToPinyinSpell(entity.getUserFullName()));
            entity.setNameSimplePinyin(Chinese2PinyinUtil.convertToFirstSpell(entity.getUserFullName()));
        }
        return entity;
    }

    @Override
    public List<BasePersonEntity_HI> findPersonList(JSONObject queryParamJSON) {
        StringBuffer querySQLSB = new StringBuffer(" FROM BasePersonEntity_HI WHERE 1 = 1 ");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "SOURCE_SYSTEM_ID", "sourceSystemId_in", querySQLSB, paramMap, "in");
        List<BasePersonEntity_HI> personEntitys = basepersonDAO_HI.findList(querySQLSB.toString(), paramMap);
        return personEntitys;
    }

    @Override
    public List<BaseUsersEntity_HI> findUserList(JSONObject queryParamJSON) {
        StringBuffer querySQLSB = new StringBuffer(" FROM BaseUsersEntity_HI WHERE userType!='60' ");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "sourceId", "sourceId_in", querySQLSB, paramMap, "in");
        SaafToolUtils.parperParam(queryParamJSON, "userName", "userName_in", querySQLSB, paramMap, "in");
        SaafToolUtils.parperHbmParam(BaseUsersEntity_HI.class,queryParamJSON, "userType", "userType", querySQLSB, paramMap, "<>");
        List<BaseUsersEntity_HI> userEntitys = baseUsersDAO_HI.findList(querySQLSB.toString(), paramMap);
        return userEntitys;
    }



    @Override
    public  Integer findPersonIdBySourceId(Integer sourceId){
        if (sourceId==null)
            return null;
        Map<String ,Object> map=new HashMap<>();
        map.put("sourceCode","ESS");
        map.put("sourceId",sourceId);
        List<BasePersonEntity_HI> list= basepersonDAO_HI.findByProperty(map);
        if (list.size()==1){
            return list.get(0).getPersonId();
        }else {
            LOGGER.warn("base_person 数据异常,{}",map.toString());
        }
        return null;
    }


    @Override
    public JSONObject saveSyncCrmUser(JSONObject queryJSONObject){
        Date date=new Date();
        JSONObject result = new JSONObject();
//        try {
//            String CRM_USER_LAST_SYNC_TIME = "CRM_USER_LAST_SYNC_TIME";
//            String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME,CRM_USER_LAST_SYNC_TIME);
//            if(StringUtils.isBlank(lastSyncTime)){
//                lastSyncTime = "2000-01-01 00:00:00";
//            }
//            StringBuffer sb = new StringBuffer("SELECT T.USER_ID,T.USER_NAME,T.REALNAME AS USER_FULL_NAME,T.SEX,T.MOBILETEL,T.EMAIL,T.BLOCKED,T.NAME_PINYIN,T.REGDATE,T.STATUS \n");
//            sb.append("FROM BA_USER_INFO_T T \n");
//            sb.append("WHERE  T.USER_TYPE = 'manager_user' AND T.LAST_UPDATE_DATE >= TO_DATE('"+lastSyncTime+"','yyyy-mm-dd HH24:mi:ss')");
//
//            int count = 0;
//            int hasCount = 0;
//            Set<String> hasUserFullName = new HashSet<>();
//            Set<String> newUserFullName = new HashSet<>();
//            List<JSONObject> crmUsers = crmOracleTemplateServer.findList(sb.toString());
//            if(crmUsers != null && !crmUsers.isEmpty()){
//                List<BaseUsersEntity_HI> baseUsers = baseUsersServer.findList(new JSONObject().fluentPut("userType","20"));
//                Map<String,BaseUsersEntity_HI> users = new HashMap<>();
//                for(BaseUsersEntity_HI entity : baseUsers){
//                    users.put(entity.getUserName(),entity);
//                }
//                for (int i = 0; i < crmUsers.size(); i++) {
//                    JSONObject crmUser = crmUsers.get(i);
//                    String userName = crmUser.getString("USER_NAME").toUpperCase();
//                    String status=crmUser.getString("STATUS");
//
//                    if(!users.containsKey(userName)){
//                        BaseUsersEntity_HI baseUser = new BaseUsersEntity_HI();
//                        baseUser.setSourceId(crmUser.getString("USER_ID"));
//                        baseUser.setUserName(crmUser.getString("USER_NAME"));
//                        baseUser.setUserFullName(crmUser.getString("USER_FULL_NAME"));
//                        baseUser.setPhoneNumber(crmUser.getString("MOBILETEL"));
//                        baseUser.setIsadmin("N");
//                        baseUser.setInternalUser("N");
//                        baseUser.setPassword(Base64.encodeBase64String("123456".getBytes()));
//
//                        baseUser.setUserDesc("CRM");
//                        baseUser.setUserType("20");
//                        baseUser.setStartDate(crmUser.getDate("REGDATE"));
//                        baseUser.setOperatorUserId(-1);
//                        baseUser.setDeleteFlag(CommonConstants.DELETE_FALSE);
//                        if ("A".equals(status)==false){
//                            baseUser.setEndDate(SaafDateUtils.getDate(0));
//                        }
//                        BaseUsersEntity_HI              baseUsersEntityHi      = baseUsersServer.saveOrUpdate(JSONObject.parseObject(JSON.toJSONString(baseUser)));
//                        if(StringUtils.equals(baseUsersEntityHi.getUserType(),"20")) {
//                            //添加crm职责
//                            BaseUserResponsibilityEntity_HI baseUserResponsibility = new BaseUserResponsibilityEntity_HI();
//                            baseUserResponsibility.setResponsibilityId(120019);
//                            baseUserResponsibility.setUserId(baseUsersEntityHi.getUserId());
//                            baseUserResponsibility.setStartDateActive(new Date());
//                            baseUserResponsibility.setVersionNum(0);
//                            baseUserResponsibility.setOperatorUserId(-1);
//                            baseUserResponsibilityServer.saveOrUpdate(JSONObject.parseObject(JSON.toJSONString(baseUserResponsibility)));
//                        }
//                        newUserFullName.add(userName);
//                        count++;
//                    }else{
//                        hasCount++;
//                        hasUserFullName.add(userName);
//                        BaseUsersEntity_HI baseUsersEntityHi = users.get(userName);
//                        baseUsersEntityHi.setUserDesc("CRM");
//                        baseUsersEntityHi.setLastUpdateDate(new Date());
//                        baseUsersEntityHi.setOperatorUserId(-1);
//                        baseUsersEntityHi.setEndDate("A".equals(status)?null:SaafDateUtils.getDate(0));
//                        baseUsersServer.update(baseUsersEntityHi);
//                        if(StringUtils.equals(baseUsersEntityHi.getUserType(),"20")) {
//                            List<BaseUserResponsibilityEntity_HI> responsibilityEntity = baseUserResponsibilityServer.findList(new JSONObject().fluentPut("userId", baseUsersEntityHi.getUserId()).fluentPut("responsibilityId", 120019));
//                            if (responsibilityEntity != null && responsibilityEntity.size() == 0) {
//                                BaseUserResponsibilityEntity_HI baseUserResponsibility = new BaseUserResponsibilityEntity_HI();
//                                baseUserResponsibility.setResponsibilityId(120019);
//                                baseUserResponsibility.setUserId(baseUsersEntityHi.getUserId());
//                                baseUserResponsibility.setStartDateActive(new Date());
//                                baseUserResponsibility.setVersionNum(0);
//                                baseUserResponsibility.setOperatorUserId(-1);
//                                baseUserResponsibilityServer.saveOrUpdate(JSONObject.parseObject(JSON.toJSONString(baseUserResponsibility)));
//                            }
//                        }
//                    }
//                }
//            }
//
//            //同步完成，更新redis最后更新时间
//            jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME,CRM_USER_LAST_SYNC_TIME, SaafDateUtils.convertDateToString(date));
//
//            result.put("syncStatus","success");
//            result.put("syncMsg","CRM用户同步完成,增加用户["+StringUtils.join(newUserFullName,",")+"],已存在用户["+StringUtils.join(hasUserFullName,",")+"]");
//            result.put("savecount",count);
//            result.put("hasCount",hasCount);
//        } catch (SQLException e) {
//            result.put("syncStatus","fail");
//            result.put("syncMsg","CRM用户同步失败，"+e.getMessage());
//            LOGGER.error(e.getMessage(),e);
//        }

        return result;
    }
}
