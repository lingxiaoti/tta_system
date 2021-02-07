package com.sie.watsons.base.pos.archivesChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.model.entities.EquPosSupplierChangeEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosSupplierChange extends IBaseCommon<EquPosSupplierChangeEntity_HI>{
    //供应商档案变更单据，分页查询
    JSONObject findArchivesChangeOrder(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //供应商档案变更单据-保存
    EquPosSupplierChangeEntity_HI saveArchivesChangeOrder(JSONObject params) throws Exception;

    //供应商档案变更单据-删除
    Integer deleteArchivesChangeOrder(JSONObject params) throws Exception;

    //审批流程回调方法
    EquPosSupplierChangeEntity_HI supplierArchivesChangeApprovalCallback(JSONObject parseObject,int userId) throws Exception;

    //初始化-供应商联系人账号批量生成
    void generateSupplierAccountBench(JSONObject parseObject) throws Exception;
}
