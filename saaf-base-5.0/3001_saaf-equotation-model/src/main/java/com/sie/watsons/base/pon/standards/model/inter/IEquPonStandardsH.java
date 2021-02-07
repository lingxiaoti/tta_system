package com.sie.watsons.base.pon.standards.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pon.standards.model.entities.readonly.EquPonStandardsHEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import com.sie.watsons.base.pon.standards.model.entities.EquPonStandardsHEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonStandardsH extends IBaseCommon<EquPonStandardsHEntity_HI>{

    Pagination<EquPonStandardsHEntity_HI_RO> findPonStandardsInfo(String params, Integer pageIndex, Integer pageRows);

    JSONObject findPonStandardsDatail(String params);

    JSONObject findPonStandardsDatailByCode(JSONObject params);

    JSONObject savePonStandards(JSONObject jsonObject, int userId) throws Exception;

    void deletePonStandardsLine(JSONObject jsonObject, int userId);

    void deleteCopyStandardsDatail(JSONObject params);

    JSONObject returnCopyDatail(JSONObject returnJson);

    Integer deletePonStandards(JSONObject jsonObject, int userId);

    //评分标准审批回调接口
    EquPonStandardsHEntity_HI standardsApprovalCallback(JSONObject parseObject,int userId) throws Exception;

    JSONObject savePonStandardsSubmit(JSONObject jsonObject, int userId);
}
