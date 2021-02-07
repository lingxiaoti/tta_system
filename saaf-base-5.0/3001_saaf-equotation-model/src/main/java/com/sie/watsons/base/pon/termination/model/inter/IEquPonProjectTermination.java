package com.sie.watsons.base.pon.termination.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.termination.model.entities.EquPonProjectTerminationEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonProjectTermination extends IBaseCommon<EquPonProjectTerminationEntity_HI>{
    //报价管理-立项终止单据查询，分页查询
    JSONObject findProjectTermination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
    JSONObject findItProjectTermination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
    //报价管理-立项终止单据保存
    EquPonProjectTerminationEntity_HI saveProjectTermination(JSONObject params) throws Exception;
    //报价管理-立项终止单据删除
    Integer deleteProjectTermination(JSONObject params) throws Exception;
    //审批流程回调方法
    EquPonProjectTerminationEntity_HI projectTerminationCallback(JSONObject parseObject,int userId) throws Exception;
    EquPonProjectTerminationEntity_HI itProjectTerminationCallback(JSONObject parseObject,int userId) throws Exception;
}
