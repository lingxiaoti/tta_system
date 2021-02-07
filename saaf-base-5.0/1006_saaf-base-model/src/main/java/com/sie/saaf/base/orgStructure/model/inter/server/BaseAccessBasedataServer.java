package com.sie.saaf.base.orgStructure.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseAccessBasedataEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.BasePersonAccessTempEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseAccessBasedataEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseAccessBasedata;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.transaction.compensation.core.TransactionConsistencyCore;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("baseAccessBasedataServer")
public class BaseAccessBasedataServer extends BaseCommonServer<BaseAccessBasedataEntity_HI> implements IBaseAccessBasedata {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAccessBasedataServer.class);
    @Autowired
    private ViewObject<BaseAccessBasedataEntity_HI> baseAccessBasedataDAO_HI;
    @Autowired
    private BaseViewObject<BaseAccessBasedataEntity_HI_RO> baseAccessBasedataDAO_HI_RO;
    @Autowired
    private ViewObject<BasePersonAccessTempEntity_HI> basePersonAccessTempDAO_HI;
    @Autowired
    private TransactionConsistencyCore transactionConsistencyCore;

    public BaseAccessBasedataServer() {
        super();
    }

    public static List<BaseAccessBasedataEntity_HI> transformEntity(List<BaseAccessBasedataEntity_HI> insertNewList, List<BaseAccessBasedataEntity_HI_RO> insertList) {
        for (int i = 0; i < insertList.size(); i++) {
            BaseAccessBasedataEntity_HI accessBasedata = JSON.parseObject(JSON.toJSONString(insertList.get(i)), BaseAccessBasedataEntity_HI.class);
            accessBasedata.setCreationDate(new Date());
            accessBasedata.setCreatedBy(1);
            accessBasedata.setLastUpdateDate(new Date());
            accessBasedata.setLastUpdatedBy(1);
            accessBasedata.setDeleteFlag(0);
            accessBasedata.setVersionNum(0);
            insertNewList.add(accessBasedata);
        }
        return insertNewList;
    }

    /**
     * 新增权限基础数据（单条）
     *
     * @param paramJSON {
     *                  accessId; //主键ID
     *                  orgId; //事业部
     *                  accessType; //访问类型
     *                  userId; //用户ID
     *                  positionId; //职位
     *                  subordinatePersonId; //下级人员ID
     *                  subordinatePositionId; //下级职位ID
     *                  custAccountId; //经销商ID
     *                  accountNumber; //经销商账号
     *                  secondaryInventoryName; //子库名称
     *                  channelType; //渠道类型
     *                  creationDate; //创建时间
     *                  createdBy; //创建人
     *                  oaUserId; //OA用户ID
     *                  personId; //人员ID
     *                  }
     * @throws Exception 异常回滚
     */
    @Override
    public void saveBaseAccessBasedata(JSONObject paramJSON) throws Exception {
        try {
            BaseAccessBasedataEntity_HI baseAccessBasedata = SaafToolUtils.setEntity(BaseAccessBasedataEntity_HI.class, paramJSON, baseAccessBasedataDAO_HI, 1);
            baseAccessBasedataDAO_HI.save(baseAccessBasedata);
        } catch (Exception e) {
            LOGGER.warn("新增权限基础数据（单条）异常:{}-{}", e.getMessage(), e);
            throw new Exception(e);
        }
    }

    /**
     * 新增权限基础数据（批量）
     *
     * @param jsonArray [{
     *                  accessId; //主键ID
     *                  orgId; //事业部
     *                  accessType; //访问类型
     *                  userId; //用户ID
     *                  positionId; //职位
     *                  subordinatePersonId; //下级人员ID
     *                  subordinatePositionId; //下级职位ID
     *                  custAccountId; //经销商ID
     *                  accountNumber; //经销商账号
     *                  secondaryInventoryName; //子库名称
     *                  channelType; //渠道类型
     *                  creationDate; //创建时间
     *                  createdBy; //创建人
     *                  oaUserId; //OA用户ID
     *                  personId; //人员ID
     *                  }]
     * @throws Exception 异常回滚
     */
    @Override
    public void saveBaseAccessBasedataBatch(JSONArray jsonArray) throws Exception {
        try {
            List<BaseAccessBasedataEntity_HI> baseAccessBasedataList = JSON.parseArray(JSON.toJSONString(jsonArray), BaseAccessBasedataEntity_HI.class);
            baseAccessBasedataDAO_HI.saveAll(baseAccessBasedataList);
        } catch (Exception e) {
            LOGGER.warn("新增权限基础数据（批量）异常:{}-{}", e.getMessage(), e);
            throw new Exception(e);
        }
    }

    /**
     * 批量删除权限基础数据
     *
     * @param baseAccessBasedataList 待删除的权限基础数据列表
     * @throws Exception 抛出异常回滚
     */
    @Override
    public void deleteByBatch(List<BaseAccessBasedataEntity_HI> baseAccessBasedataList) throws Exception {
        try {
            baseAccessBasedataDAO_HI.delete(baseAccessBasedataList);
        } catch (Exception e) {
            LOGGER.warn("批量删除权限基础数据异常:{}-{}", e.getMessage(), e);
            throw new Exception(e);
        }
    }

    /**
     * 职位信息（用于权限数据同步）
     *
     * @param queryParamJSON {
     *                       ouId：事业部ID（不传怎查询所有事业部）
     *                       positionId：职位ID（不传怎查询所有职位）
     *                       }
     * @return 职位列表
     */
    @Override
    public List<BaseAccessBasedataEntity_HI_RO> findAllPositionList(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer(BaseAccessBasedataEntity_HI_RO.QUERY_ALL_POSITION_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        SaafToolUtils.parperHbmParam(BaseAccessBasedataEntity_HI_RO.class, queryParamJSON, "position.position_id", "positionId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseAccessBasedataEntity_HI_RO.class, queryParamJSON, "position.ou_id", "orgId", querySql, queryParamMap, "=");
        querySql.append(" ORDER BY position.ou_id\n" +
                        " 		 ,position.department_id\n" +
                        "		 ,position.position_id");
        LOGGER.info(querySql.toString());
        return baseAccessBasedataDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 查询职位分配的人员信息
     *
     * @param queryParamJSON
     * @return
     */
    @Override
    public List<BaseAccessBasedataEntity_HI_RO> findPositionDistributionPersonList(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer(BaseAccessBasedataEntity_HI_RO.QUERY_POSITION_DISTRIBUTION_PERSON_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        SaafToolUtils.parperHbmParam(BaseAccessBasedataEntity_HI_RO.class, queryParamJSON, "position.position_id", "positionId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseAccessBasedataEntity_HI_RO.class, queryParamJSON, "position.ou_id", "ouId", querySql, queryParamMap, "=");
        return baseAccessBasedataDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 人员20权限数据同步
     *
     * @param person20AccessList 当前职位下的下一级人员信息
     * @param orgId              当前事业部ID
     * @param positionId         当前职位ID
     * @return 当前职位下级所有人员信息
     */
    @Override
    public List<BaseAccessBasedataEntity_HI_RO> findPerson20AccessDataSyn(List<BaseAccessBasedataEntity_HI_RO> person20AccessList, Integer orgId, Integer positionId) {
        if (person20AccessList == null) {
            person20AccessList = new LinkedList<>();
        }
        List<BaseAccessBasedataEntity_HI_RO> currentPerson20AccessList = findCurrentPersonInfo(orgId, positionId);
        if (currentPerson20AccessList.size() == 0) {
            return person20AccessList;
        }
        person20AccessList.addAll(currentPerson20AccessList);

        //去重：过滤同一职位对应对个人的数据，避免同一职位循环多次
        Map<Integer, Integer> map = new HashMap<>();
        for (BaseAccessBasedataEntity_HI_RO currentPerson20Access : currentPerson20AccessList) {
            Integer subordinatePositionIdMapKey = currentPerson20Access.getSubordinatePositionId();
            if (map.containsKey(subordinatePositionIdMapKey)) {
                continue;
            }
            map.put(subordinatePositionIdMapKey, subordinatePositionIdMapKey);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            List<BaseAccessBasedataEntity_HI_RO> nextPerson20AccessList = findPerson20AccessDataSyn(person20AccessList, orgId, entry.getValue());
            if (nextPerson20AccessList.size() > person20AccessList.size()) {
                person20AccessList.addAll(nextPerson20AccessList);
            }
        }
        return person20AccessList;
    }

    @Override
    public List<BaseAccessBasedataEntity_HI_RO> findCurrentPersonInfo(Integer orgId, Integer positionId) {
        StringBuffer querySql = new StringBuffer(BaseAccessBasedataEntity_HI_RO.QUERY_PERSON_20_ACCESS_SYN);
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("orgId", orgId);
        queryParamMap.put("positionId", positionId);
        return baseAccessBasedataDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 查询人员权限数据临时表中的所有批次对应的人员信息
     *
     * @return 人员权限数据临时表中的所有批次对应的人员信息
     */
    @Override
    public List<BaseAccessBasedataEntity_HI_RO> findAllBatchCodeList() {
        return baseAccessBasedataDAO_HI_RO.findList(BaseAccessBasedataEntity_HI_RO.QUERY_ALL_BATCH_CODE_SQL);
    }

    /**
     * 同步人员20权限数据：MQ出队执行逻辑
     * @param jsonObject      消息ID
     * @param jsonObject 当前批次对应的人员信息（人员权限中间临时表，同步完人员权限数据后，删除对应批次的临时数据）
     */
    @Override
    public void person20AccessDataSynConsumer(JSONObject jsonObject) {
        String batchCode = jsonObject.getString("batchCode"); //批次编号
        Integer orgId = jsonObject.getInteger("orgId"); //当前事业部ID
        Integer personId = jsonObject.getInteger("personId"); //当前人员ID（用于和老数据关联比较）

        //人员20权限数据同步 --> 存在权限表，不存在临时表 --> 删除老数据
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("batchCode", batchCode);
        queryMap.put("orgId", orgId);
        queryMap.put("personId", personId);
        List<BaseAccessBasedataEntity_HI_RO> deleteList = baseAccessBasedataDAO_HI_RO.findList(BaseAccessBasedataEntity_HI_RO.QUERY_EXIST_ACCESS_NOT_EXIST_TEMP_SQL, queryMap);
        for (int i = 0; i < deleteList.size(); i++) {
            baseAccessBasedataDAO_HI.delete(deleteList.get(i).getAccessId());
        }
        //人员20权限数据同步 --> 存在临时表，不存在权限表 --> 插入新数据
        List<BaseAccessBasedataEntity_HI> insertNewList = new ArrayList<>();
        List<BaseAccessBasedataEntity_HI_RO> insertList = baseAccessBasedataDAO_HI_RO.findList(BaseAccessBasedataEntity_HI_RO.QUERY_EXIST_TEMP_NOT_EXIST_ACCESS_SQL, queryMap);
        insertNewList = transformEntity(insertNewList, insertList);
        baseAccessBasedataDAO_HI.save(insertNewList);
        //批量删除当前批次对应的临时数据
        List<BasePersonAccessTempEntity_HI> personAccessTempList = basePersonAccessTempDAO_HI.findByProperty("batchCode", batchCode);
        basePersonAccessTempDAO_HI.delete(personAccessTempList);
    }

    /**
     * 同步人员10权限数据--删除失效数据(入队)
     *
     * @return 同步结果
     * @throws Exception 抛出异常回滚
     */
    @Override
    public JSONObject findDeletePerson10AccessData() throws Exception {
        //人员10权限数据同步 --> 存在权限表，不存在新数据列表 --> 删除老数据
        List<BaseAccessBasedataEntity_HI_RO> deleteList = baseAccessBasedataDAO_HI_RO.findList(BaseAccessBasedataEntity_HI_RO.QUERY_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL);
        return findDeleteParams(deleteList, "access10Person");
    }

    /**
     * 同步人员10权限数据--删除失效数据(出队)
     * @param accessIdArray （accessId集合：1,2,3,4,5）
     * @return 同步结果
     * @throws Exception 抛出异常回滚
     */
    @Override
    public void deletePerson10AccessData(JSONArray accessIdArray) throws Exception {
        for (int i = 0; i < accessIdArray.size(); i++) {
            baseAccessBasedataDAO_HI.delete(Integer.parseInt(accessIdArray.get(i).toString()));
        }
    }

    /**
     * 同步人员10权限数据--新增新数据(入队)
     *
     * @return 同步结果
     * @throws Exception 抛出异常回滚
     */
    @Override
    public JSONObject findSavePerson10AccessData() throws Exception {
        //人员10权限数据同步 --> 存在新数据列表，不存在临时表 --> 插入新数据
        List<BaseAccessBasedataEntity_HI> insertNewList = new ArrayList<>();
        List<BaseAccessBasedataEntity_HI_RO> insertList = baseAccessBasedataDAO_HI_RO.findList(BaseAccessBasedataEntity_HI_RO.QUERY_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL);
        insertNewList = transformEntity(insertNewList, insertList);
        return findSaveParams(insertNewList, "access10Person");
    }

    /**
     * 同步人员10权限数据--新增新数据(出队)
     * @param insertList （新增数据集合）
     * @return 同步结果
     * @throws Exception 抛出异常回滚
     */
    @Override
    public void savePerson10AccessData(List<BaseAccessBasedataEntity_HI> insertList) throws Exception {
        baseAccessBasedataDAO_HI.save(insertList);
    }

    /**
     * 同步经销商全局用户10权限数据--删除失效数据（入队）
     *
     * @return 同步结果
     * @throws Exception 抛出异常回滚
     */
    @Override
    public JSONObject findDeleteDistributor10AccessDataSyn() throws Exception {
        //经销商全局用户权限 --> 存在权限表，不存在新数据列表，删除老数据
        List<BaseAccessBasedataEntity_HI_RO> deleteList = baseAccessBasedataDAO_HI_RO.findList(BaseAccessBasedataEntity_HI_RO.QUERY_DISTRIBUTOR_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL);
        return findDeleteParams(deleteList, "access10Dealer");
    }

    /**
     * 同步经销商全局用户10权限数据
     *
     * @return 同步结果
     * @throws Exception 抛出异常回滚
     */
    @Override
    public JSONObject findSaveDistributor10AccessDataSyn() throws Exception {
        //经销商全局用户权限 --> 存在新数据列表，不存在权限表，插入新数据
        List<BaseAccessBasedataEntity_HI> insertNewList = new ArrayList<>();
        List<BaseAccessBasedataEntity_HI_RO> insertList = baseAccessBasedataDAO_HI_RO.findList(BaseAccessBasedataEntity_HI_RO.QUERY_DISTRIBUTOR_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL);
        insertNewList = transformEntity(insertNewList, insertList);

        return findSaveParams(insertNewList, "access10Dealer");
    }

    @Override
    public JSONObject findDeleteParams(List<BaseAccessBasedataEntity_HI_RO> deleteList, String processingType) throws Exception {
        int deleteNum = deleteList.size();
        int n = 1;
        Set<Integer> accessIdArr = new LinkedHashSet<>();
        for (BaseAccessBasedataEntity_HI_RO row : deleteList) {
            accessIdArr.add(row.getAccessId());
            if (n == 500) {
                JSONObject mqParams = new JSONObject();
                mqParams.put("processingType", processingType);
                mqParams.put("processingMethod", "delete");
                mqParams.put("accessIdArr", accessIdArr);
                transactionConsistencyCore.sendMessage("access10PersonAndDealerQueue", JSON.toJSONString(mqParams));
                LOGGER.info(JSON.toJSONString(mqParams));
                accessIdArr = new HashSet<>();
                n = 0;
            }
            n++;
        }
        JSONObject mqParams = new JSONObject();
        mqParams.put("processingType", processingType);
        mqParams.put("processingMethod", "delete");
        mqParams.put("accessIdArr", accessIdArr);
        transactionConsistencyCore.sendMessage("access10PersonAndDealerQueue", JSON.toJSONString(mqParams));
        LOGGER.info(JSON.toJSONString(mqParams));
        return SToolUtils.convertResultJSONObj("S", processingType + ":删除旧数据" + deleteNum + "条", 0, null);
    }

    @Override
    public JSONObject findSaveParams(List<BaseAccessBasedataEntity_HI> insertNewList, String processingType) throws Exception {
        int n = 1;
        List<BaseAccessBasedataEntity_HI> insertParamMQ = new ArrayList<>();
        for (BaseAccessBasedataEntity_HI row : insertNewList) {
            insertParamMQ.add(row);
            if (n == 10) {
                JSONObject mqParams = new JSONObject();
                mqParams.put("processingType", processingType);
                mqParams.put("processingMethod", "save");
                mqParams.put("insertParamMQ", insertParamMQ);
                transactionConsistencyCore.sendMessage("access10PersonAndDealerQueue", JSON.toJSONString(mqParams));
                LOGGER.info(JSON.toJSONString(mqParams));
                insertParamMQ = new ArrayList<>();
                n = 0;
            }
            n++;
        }
        JSONObject mqParams = new JSONObject();
        mqParams.put("processingType", processingType);
        mqParams.put("processingMethod", "save");
        mqParams.put("insertParamMQ", insertParamMQ);
        transactionConsistencyCore.sendMessage("access10PersonAndDealerQueue", JSON.toJSONString(mqParams));
        LOGGER.info(JSON.toJSONString(mqParams));
        return SToolUtils.convertResultJSONObj("S", processingType + ":新增数据" + insertNewList.size() + "条", 0, null);
    }

    /**
     * 经销商权限 -- 删除失效用户的权限
     */
    @Override
    public void deleteInvalidUserAccess() {
        StringBuffer querySQL = new StringBuffer("SELECT accessBasedata.*\n" +
                "  FROM base_access_basedata accessBasedata\n" +
                " WHERE 1 = 1\n" +
                "\t AND accessBasedata.access_type = '2'\n" +
                "\t AND accessBasedata.user_id <> - 10 # 非全局用户\n" +
                "\t AND EXISTS ( SELECT 1 \n" +
                "\t\t\t\t\t\t\t\t\tFROM base_users users\n" +
                "\t\t\t\t\t\t\t\t WHERE 1 = 1\n" +
                "\t\t\t\t\t\t\t\t\t AND users.user_id = accessBasedata.user_id\n" +
                "\t\t\t\t\t\t\t\t\t AND users.user_type = '20'\n" +
                "\t\t\t\t\t\t\t\t\t AND users.end_date <sysdate)");
        List<BaseAccessBasedataEntity_HI> invalidUserAccessList = baseAccessBasedataDAO_HI.findList(querySQL);
        baseAccessBasedataDAO_HI.delete(invalidUserAccessList);
    }

    /**
     * 同步经销商用户20权限数据：业务执行
     * @param jsonObject userType = 20 or 30 的人员信息
     */
    @Override
    public void saveDealer20AccessConsumer(JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId"); //用户ID
        String userType = jsonObject.getString("userType");

        StringBuffer deleteSQL = new StringBuffer();
        StringBuffer insertSQL = new StringBuffer();
        if (userType != null && "20".equals(userType)) {
            deleteSQL.append(BaseAccessBasedataEntity_HI_RO.QUERY_DISTRIBUTOR_PERSON_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL);
            insertSQL.append(BaseAccessBasedataEntity_HI_RO.QUERY_DISTRIBUTOR_PERSON_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL);
        } else if (userType != null && "30".equals(userType)) {
            deleteSQL.append(BaseAccessBasedataEntity_HI_RO.QUERY_DISTRIBUTOR_USER_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL);
            insertSQL.append(BaseAccessBasedataEntity_HI_RO.QUERY_DISTRIBUTOR_USER_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL);
        } else {
            LOGGER.info("(经销商员工用户和经销商用户)20权限数据同步：未允许的userType[{}]对应的UserId[{}]", userType, userId);
            return;
        }
        //经销商用户20权限数据同步 --> 存在权限表，不存在临时表 --> 删除老数据
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("userId", userId);
        List<BaseAccessBasedataEntity_HI_RO> deleteList = baseAccessBasedataDAO_HI_RO.findList(deleteSQL, queryMap);
        for (int i = 0; i < deleteList.size(); i++) {
            baseAccessBasedataDAO_HI.delete(deleteList.get(i).getAccessId());
        }
        //经销商用户20权限数据同步 --> 存在临时表，不存在权限表 --> 插入新数据
        List<BaseAccessBasedataEntity_HI> insertNewList = new ArrayList<>();
        List<BaseAccessBasedataEntity_HI_RO> insertList = baseAccessBasedataDAO_HI_RO.findList(insertSQL, queryMap);
        insertNewList = transformEntity(insertNewList, insertList);
        baseAccessBasedataDAO_HI.save(insertNewList);
        LOGGER.info("(经销商员工用户和经销商用户)20权限数据同步：被动方业务执行完成，当前同步userType[{}]对应的UserId[{}]", userType, userId);
    }

    /**
     * 同步经销商、门店、分销商访问子库20权限数据：被动方业务执行完成
     * @param jsonObject userType = 30 or 40 的人员信息
     */
    @Override
    public void saveDealerStoreSub20AccessConsumer(JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId"); //用户ID
        String userType = jsonObject.getString("userType");

        StringBuffer deleteSQL = new StringBuffer();
        StringBuffer insertSQL = new StringBuffer();
        if (userType != null && "30".equals(userType)) {
            deleteSQL.append(BaseAccessBasedataEntity_HI_RO.QUERY_DEALER_SUB_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL);
            insertSQL.append(BaseAccessBasedataEntity_HI_RO.QUERY_DEALER_SUB_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL);
        } else if (userType != null && "40".equals(userType)) {
            deleteSQL.append(BaseAccessBasedataEntity_HI_RO.QUERY_STORE_SUB_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL);
            insertSQL.append(BaseAccessBasedataEntity_HI_RO.QUERY_STORE_SUB_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL);
        } else {
            LOGGER.info("经销商、门店、分销商访问子库20权限数据同步：未允许的userType[{}]对应的UserId[{}]", userType, userId);
            return;
        }
        //经销商、门店、分销商访问子库20权限数据同步 --> 存在权限表，不存在临时表 --> 删除老数据
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("userId", userId);
        List<BaseAccessBasedataEntity_HI_RO> deleteList = baseAccessBasedataDAO_HI_RO.findList(deleteSQL, queryMap);
        for (int i = 0; i < deleteList.size(); i++) {
            baseAccessBasedataDAO_HI.delete(deleteList.get(i).getAccessId());
        }
        //经销商、门店、分销商访问子库20权限数据同步 --> 存在临时表，不存在权限表 --> 插入新数据
        List<BaseAccessBasedataEntity_HI> insertNewList = new ArrayList<>();
        List<BaseAccessBasedataEntity_HI_RO> insertList = baseAccessBasedataDAO_HI_RO.findList(insertSQL, queryMap);
        insertNewList = transformEntity(insertNewList, insertList);
        baseAccessBasedataDAO_HI.save(insertNewList);
        LOGGER.info("经销商、门店、分销商访问子库20权限数据同步：被动方业务执行完成，当前同步userType[{}]对应的UserId[{}]", userType, userId);
    }

    /**
     * 通过userType查询所有用户信息
     *
     * @param userType   用户类型
     * @param userTypeIn 用户类型
     * @return userType对应的所有用户信息
     */
    @Override
    public List<BaseAccessBasedataEntity_HI_RO> findUserInfo(String userType, String userTypeIn) {
        StringBuffer querySql = new StringBuffer(BaseAccessBasedataEntity_HI_RO.QUERY_USER_INFO_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        if (StringUtils.isNotBlank(userType)) {
            querySql.append("and users.user_type = :userType \n");
            queryParamMap.put("userType", userType);
        }
        if (StringUtils.isNotBlank(userTypeIn)) {
            querySql.append("and users.user_type IN (" + userTypeIn + ") \n");
        }
        return baseAccessBasedataDAO_HI_RO.findList(querySql, queryParamMap);
    }
}
