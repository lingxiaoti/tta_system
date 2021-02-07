package com.sie.watsons.base.pon.itproject.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonItProjectInfo extends IBaseCommon<EquPonItProjectInfoEntity_HI>{
    //IT报价管理-立项单据查询，分页查询
    JSONObject findItProjectInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
    //IT报价管理-立项单据保存
    EquPonItProjectInfoEntity_HI saveItProjectInfo(JSONObject params) throws Exception;
    //IT报价管理-立项单据删除
    Integer deleteItProjectInfo(JSONObject params) throws Exception;
    //查找导航菜单节点
    JSONObject findPonItNavigationMenuNodeList(JSONObject queryParamJSON);
    //查询关联部门业务人员(审批流)
    JSONObject findRelateDeptBussinessApproval(JSONObject queryParamJSON);

    JSONObject findQuoInformationDefaultWitnessApproval(JSONObject queryParamJSON);
}
