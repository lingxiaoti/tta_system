package com.sie.saaf.base.orgStructure.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePersonAccessTempEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseAccessBasedataEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface IBasePersonAccessTemp extends IBaseCommon<BasePersonAccessTempEntity_HI> {
    /**
     * 批量插入临时表
     *
     * @param positionDistributionPersonList 职位对应的人员信息
     * @param person20AccessList             人员权限临时数据
     * @throws Exception 抛出异常回滚
     */
    void saveAllPersonAccessTempByBatchCode(List<BaseAccessBasedataEntity_HI_RO> positionDistributionPersonList, List<BaseAccessBasedataEntity_HI_RO> person20AccessList) throws Exception;

    Boolean savePersonAccessTemp(Integer indexValue, JSONObject paramsJSON) throws Exception;

    JSONObject callSaveTransTest(String testFlag, Integer orgId, Integer positionId, List<BaseAccessBasedataEntity_HI_RO> positionDistributionPersonList);
}
