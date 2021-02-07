package com.sie.saaf.base.user.model.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用户同步
 *
 * @author huangtao
 * @creteTime 2018年6月22日 14:40:07
 */
@Component
public class BaseUserPersonSynClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseUserPersonSynClient.class);

//    @Autowired
//    private JedisCluster jedisCluster;
//
//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;
//
//    @Autowired
//    private IBaseUserSyncServer baseUserSyncServer;
//
//    @Autowired
//    private IBaseUserResponsibility baseUserResponsibilityServer;
//
//    @Autowired
//    private IBaseUsers baseUsersServer;
//
//    @Autowired
//    private IBasePerson basePersonServer;
//
//    @Autowired
//    private ViewObject<BasePersonEntity_HI> basePersonDAO_HI;
//
//
//    /**
//     * ess 用户同步
//     *
//     * @param queryParamJSON {
//     *                       startPage:开始页
//     *                       endPage: 结束页
//     *                       }
//     * @return
//     */
//    public JSONObject saveBaseUserSync(JSONObject queryParamJSON) {
//        Date date = new Date();
//        final String BASE_USER_AND_PERSON_LAST_SYNC_TIME = "BASE_USER_AND_PERSON_LAST_SYNC_TIME";
//        JSONObject result = new JSONObject();
//        result.put("syncStatus", "success");
//        result.put("syncMsg", "员工、用户同步完成");
//        result.put("updateCount", 0);
//        result.put("status", "S");
//        try {
//            String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_USER_AND_PERSON_LAST_SYNC_TIME);
//            if (StringUtils.isBlank(lastSyncTime)) {
//                lastSyncTime = "2000-01-01 00:00:00";
//            }
//            String userSql = "select distinct user_id,\n" +
//                    "       personInfo.mobile_phone as phone_number,\n" +
//                    "       '' as name_pinyin,\n" +
//                    "       '' as name_simple_pinyin,\n" +
//                    "       baseUser.person_id as person_id,\n" +
//                    "       user_id as source_id,\n" +
//                    "       'N' as isadmin,\n" +
//                    "       user_name as user_name,\n" +
//                    "       '' as user_desc,\n" +
//                    "       user_type as user_type,\n" +
//                    "       personInfo.person_name as user_full_name,\n" +
//                    "       'e10adc3949ba59abbe56e057f20f883e' as encrypted_password,\n" +
//                    "       cust_account_id as cust_account_id,\n" +
//                    "       store_code as store_code,\n" +
//                    "       internal_user as internal_user,\n" +
//                    "       '' as user_head_img_url,\n" +
//                    "       begin_date as start_date,\n" +
//                    "       end_date as end_date\n" +
//                    "  from BASE_USER baseUser left join (\n" +
//                    "       select * from auportal.base_hr_person_v PV where PV.EFFECTIVE_START_DATE<=sysdate \n" +
//                    "  and (PV.EFFECTIVE_END_DATE is null or PV.EFFECTIVE_END_DATE>sysdate) )personInfo\n" +
//                    "  on baseUser.person_id=personInfo.person_id WHERE BASEUSER.LAST_UPDATE_DATE >=TO_DATE('" + lastSyncTime + "','yyyy-mm-dd HH24:mi:ss') ";
//            //查询用户员工总数
//            String userCountSql = new StringBuilder("select count(1) from (").append(userSql).append(" ) USERTEMP").toString();
//            int userCount = oracleTemplateServer.findCount(userCountSql);
//            LOGGER.info("同步用户查询总数{},personCountSql: {},",userCount, userCountSql);
//            int userPagesize = 1000;
//            int userPage = userCount / userPagesize + 1;
//            int count = 0;
//
//            int startPage = 1;
//            if (queryParamJSON.containsKey("startPage")) {
//                startPage = queryParamJSON.getIntValue("startPage");
//            }
//            if (queryParamJSON.containsKey("endPage")) {
//                userPage = queryParamJSON.getIntValue("endPage");
//            }
//
//            if (userCount < 1) {
//                return result;
//            }
//            //用户信息太多，使用分页查询导入数据，每次导入1000条（第一次导入时信息比较多）
//            for (int pageIndex = startPage; pageIndex <= userPage; pageIndex++) {
//                LOGGER.info("分页查询用户，第{}页，共{}页", pageIndex, userPage);
//                Pagination<JSONObject> users = oracleTemplateServer.findByProperty(userSql, pageIndex, userPagesize);
//                List<JSONObject> userList = users.getData();
//                List<String> userNames = new ArrayList<>();
//                for (int i = 0; i < userList.size(); i++) {
//                    String userName = userList.get(i).getString("USER_NAME").toUpperCase();
//                    userNames.add(userName);
//                }
//                Map<String, BaseUsersEntity_HI> userMap = new HashMap<>();
//                JSONObject params = new JSONObject();
//                params.put("userName_in", StringUtils.join(userNames, ","));
//                List<BaseUsersEntity_HI> userMappings = baseUserSyncServer.findUserList(params);
//                for (BaseUsersEntity_HI entity : userMappings) {
//                    userMap.put(entity.getUserName(), entity);
//                }
//
//                List<BaseUsersEntity_HI> saveUserList = new ArrayList<>();
//                for (int i = 0; i < userList.size(); i++) {
//                    JSONObject user = userList.get(i);
//                    String userName = user.getString("USER_NAME");
//                    if (StringUtils.isBlank(userName))
//                        continue;
//                    BaseUsersEntity_HI entity = null;
//                    if (userMap.containsKey(userName)) {
//                        entity = userMap.get(userName);
//                        String password=entity.getEncryptedPassword();
//                        BaseUsersEntity_HI newEntity = baseUserSyncServer.packingUserEntity(user);
//                        SaafBeantUtils.copyUnionProperties(newEntity, entity);
//                        entity.setEncryptedPassword(password);
//
//                    } else {
//                        entity = baseUserSyncServer.packingUserEntity(user);
//                    }
//                    entity.setOperatorUserId(1);
//                    entity.setSourceCode("ESS");
//                    saveUserList.add(entity);
//                }
//
//                for (BaseUsersEntity_HI entity : saveUserList) {
//                    boolean isNewUser =entity.getUserId()==null;
//                    baseUsersServer.saveOrUpdate(entity);
//                    //新增用户新增职责
//                    if (isNewUser &&  StringUtils.equals(entity.getUserType(), "20")) {
//                        BaseUserResponsibilityEntity_HI baseUserResponsibility = new BaseUserResponsibilityEntity_HI();
//                        baseUserResponsibility.setResponsibilityId(120018);
//                        baseUserResponsibility.setUserId(entity.getUserId());
//                        baseUserResponsibility.setStartDateActive(new Date());
//                        baseUserResponsibility.setVersionNum(0);
//                        baseUserResponsibility.setOperatorUserId(-1);
//                        baseUserResponsibilityServer.saveOrUpdate(JSONObject.parseObject(JSON.toJSONString(baseUserResponsibility)));
//                    }
//
//                    count++;
//                }
//            }
//            //同步完成， 更新redis最后更新时间
//            jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_USER_AND_PERSON_LAST_SYNC_TIME, SaafDateUtils.convertDateToString(date));
//            result.put("syncStatus", "success");
//            result.put("syncMsg", "员工、用户同步完成");
//            result.put("updateCount", count);
//
//        } catch (Exception e) {
//            LOGGER.error("", e);
//            result.put("syncStatus", "fail");
//            result.put("status", "E");
//            result.put("syncMsg", "员工、用户同步出现异常:" + e.getMessage());
//        }
//        return result;
//    }
//
//
//    /**
//     * 员工同步
//     *
//     * @return
//     */
//    public Map<Integer, Integer> syncBasePerson() {
//        JSONObject result = new JSONObject();
//        int count = 0;
//        int totalNum = 0;
//        Map<Integer, Integer> personMap = new HashMap<>();
//        try {
//            final String BASE_PERSON_LAST_SYNC_TIME = "BASE_PERSON_LAST_SYNC_TIME";
//            String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_PERSON_LAST_SYNC_TIME);
//            if (com.yhg.base.utils.StringUtils.isBlank(lastSyncTime)) {
//                lastSyncTime = "2000-01-01 00:00:00";
//            }
//            //当前同步时间
//            String lastSyncTimeNew = SaafDateUtils.convertDateToString(new Date());
//            List<JSONObject> perAllPeopleFList = basePersonServer.findPerAllPeopleFList(lastSyncTime);
//
//            if (perAllPeopleFList == null || perAllPeopleFList.isEmpty()) {
//                result.put("syncStatus", "success");
//                result.put("syncMsg", "当前未取得需要同步的人员数据");
//                LOGGER.info("人员同步结果：{}", result.toJSONString());
//                return personMap;
//            }
//            totalNum = perAllPeopleFList.size();
//
//            for (int i = 0; i < perAllPeopleFList.size(); i++) {
//                JSONObject perAllPeopleFJson = perAllPeopleFList.get(i);
//                Integer sourceId = perAllPeopleFJson.getInteger("SOURCE_ID");//源系统ID
//                String sourceCode = "ESS"; // 系统来源,
//                //通过源系统ID和系统来源查询人员信息,用于判断该人员是否存在系统，存在则更新，否则插入
//                BasePersonEntity_HI personInstance= basePersonServer.getById(sourceId);
//                if (personInstance!=null) {
//                    personInstance.setLastUpdateDate(new Date());
//                    if (StringUtils.isNotBlank("EMPLOYEE_NUMBER"))
//                        personInstance.setEmployeeNumber(perAllPeopleFJson.getString("EMPLOYEE_NUMBER"));
//                    if (StringUtils.isNotBlank("PERSON_NAME"))
//                        personInstance.setPersonName(perAllPeopleFJson.getString("PERSON_NAME"));
//                    if (StringUtils.isNotBlank("SEX"))
//                        personInstance.setSex(perAllPeopleFJson.getString("SEX"));
//                    if (StringUtils.isNotBlank("BIRTH_DAY"))
//                        personInstance.setBirthDay(perAllPeopleFJson.getDate("BIRTH_DAY"));
//                    if (StringUtils.isNotBlank("CARD_NO"))
//                        personInstance.setCardNo(perAllPeopleFJson.getString("CARD_NO"));
//                    if (StringUtils.isNotBlank("MOBILE_PHONE"))
//                        personInstance.setMobilePhone(perAllPeopleFJson.getString("MOBILE_PHONE"));
//                    if (StringUtils.isNotBlank("EMAIL"))
//                        personInstance.setEmail(perAllPeopleFJson.getString("EMAIL"));
//                    personInstance.setOperatorUserId(1);
//                    personInstance.setSourceCode("ESS");
//                    personInstance.setEnabled("Y");
//                    personInstance.setSourceId(sourceId);
//                    LOGGER.info("已存在同步过的人员数据，本次为更新，第:{}次，参数:{}", i, JSON.toJSONString(personInstance));
//                    basePersonServer.saveOrUpdateBasePerson(personInstance);
////                    personMap.put(personInstance.getSourceId(), personInstance.getPersonId());
//                } else {
//                    BasePersonEntity_HI basePersonEntity = JSON.toJavaObject(perAllPeopleFJson, BasePersonEntity_HI.class);
//                    basePersonEntity.setSourceCode(sourceCode);
//                    basePersonEntity.setCreatedBy(1);
//                    basePersonEntity.setCreationDate(new Date());
//                    basePersonEntity.setLastUpdateDate(new Date());
//                    basePersonEntity.setLastUpdatedBy(1);
//                    basePersonEntity.setLastUpdateLogin(1);
//                    basePersonEntity.setOperatorUserId(1);
//                    basePersonEntity.setEnabled("Y");
//                    basePersonEntity.setSourceCode("ESS");
//                    basePersonEntity.setSourceId(sourceId);
//                    basePersonEntity.setPersonId(sourceId);
//                    String sql= SaafToolUtils.getInsertSql(basePersonEntity);
//                    if (StringUtils.isBlank(sql)) {
//                        LOGGER.warn("用户同步失败:[{}]",perAllPeopleFJson);
//                        continue;
//                    }
//                    basePersonServer.saveByExcuteSql(sql);
////                    personMap.put(basePersonEntity.getSourceId(), basePersonEntity.getPersonId());
//                }
//                count++;
//            }
//            LOGGER.info("人员同步总数:{},成功:{}条", totalNum, count);
//            //同步完成，更新redis最后更新时间
//            jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_PERSON_LAST_SYNC_TIME, lastSyncTimeNew);
//            return personMap;
//        } catch (Exception e) {
//            LOGGER.error("人员同步异常:{}", e);
//            result.put("syncStatus", "fail");
//            result.put("syncMsg", "人员同步异常,应同步" + totalNum + "条，实际同步了" + count + "条，异常原因:" + e.getMessage());
//        }
//        return personMap;
//    }
//
//    public static void main(String[] args) {
//        int userPagesize = 1000;
//        int userCount=1441;
//        int userPage = userCount / userPagesize + 1;
//        System.out.println(userPage);
//    }


}
