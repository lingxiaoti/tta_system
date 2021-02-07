package com.sie.saaf.base.orgStructure.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseAccessBasedataEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseAccessBasedataEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface IBaseAccessBasedata extends IBaseCommon<BaseAccessBasedataEntity_HI> {
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
    void saveBaseAccessBasedata(JSONObject paramJSON) throws Exception;

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
    void saveBaseAccessBasedataBatch(JSONArray jsonArray) throws Exception;

    /**
     * 批量删除权限基础数据
     *
     * @param baseAccessBasedataList 待删除的权限基础数据列表
     * @throws Exception 抛出异常回滚
     */
    void deleteByBatch(List<BaseAccessBasedataEntity_HI> baseAccessBasedataList) throws Exception;

    /**
     * 职位信息（用于权限数据同步）
     *
     * @param queryParamJSON {
     *                       positionId：职位ID（不传怎查询所有职位）
     *                       }
     * @return 职位列表
     */
    List<BaseAccessBasedataEntity_HI_RO> findAllPositionList(JSONObject queryParamJSON);

    /**
     * 查询职位分配的人员信息
     *
     * @param queryParamJSON {
     *                       orgId：事业部ID
     *                       positionId：职位ID（不传怎查询所有职位）
     *                       }
     * @return 职位分配的人员信息
     */
    List<BaseAccessBasedataEntity_HI_RO> findPositionDistributionPersonList(JSONObject queryParamJSON);

    /**
     * 人员20权限数据同步
     *
     * @param person20AccessList 当前职位下的下一级人员信息
     * @param orgId              当前事业部ID
     * @param positionId         当前职位ID
     * @return 当前职位下级所有人员信息
     */
    List<BaseAccessBasedataEntity_HI_RO> findPerson20AccessDataSyn(List<BaseAccessBasedataEntity_HI_RO> person20AccessList, Integer orgId, Integer positionId);

//    /**
//     * 同步人员10权限数据
//     *
//     * @return 同步结果
//     * @throws Exception 抛出异常回滚
//     */
//    JSONObject savePerson10AccessData() throws Exception;

    void saveDealer20AccessConsumer(JSONObject jsonObject);

    void saveDealerStoreSub20AccessConsumer(JSONObject jsonObject);

    /**
     * 通过userType查询所有用户信息
     *
     * @param userType   用户类型
     * @param userTypeIn 用户类型
     * @return userType对应的所有用户信息
     */
    List<BaseAccessBasedataEntity_HI_RO> findUserInfo(String userType, String userTypeIn);

    List<BaseAccessBasedataEntity_HI_RO> findCurrentPersonInfo(Integer orgId, Integer positionId);

    List<BaseAccessBasedataEntity_HI_RO> findAllBatchCodeList();

    void person20AccessDataSynConsumer(JSONObject jsonObject);

    JSONObject findDeletePerson10AccessData() throws Exception;

    void deletePerson10AccessData(JSONArray accessIdArray) throws Exception;

    JSONObject findSavePerson10AccessData() throws Exception;

    void savePerson10AccessData(List<BaseAccessBasedataEntity_HI> insertList) throws Exception;

    JSONObject findDeleteDistributor10AccessDataSyn() throws Exception;

    JSONObject findSaveDistributor10AccessDataSyn() throws Exception;

    JSONObject findDeleteParams(List<BaseAccessBasedataEntity_HI_RO> deleteList, String processingType) throws Exception;

    JSONObject findSaveParams(List<BaseAccessBasedataEntity_HI> insertNewList, String processingType) throws Exception;

    /**
     * 经销商权限 -- 删除失效用户的权限
     */
    void deleteInvalidUserAccess();
}
