package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdHeaderEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdFieldCfgLineEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplDefHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSaStdHeader extends IBaseCommon<TtaSaStdHeaderEntity_HI>{

    Pagination<TtaSaStdHeaderEntity_HI_RO> find(JSONObject parameters, Integer pageIndex, Integer pageRows);

    List<TtaSaStdTplDefHeaderEntity_HI_RO> findSupplementAgrTemlateTreeList(JSONObject queryParamJSON);

    TtaSaStdHeaderEntity_HI saveOrUpdate(JSONObject jsonObject, UserSessionBean userSessionBean) throws Exception;

    TtaSaStdHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON);

    List<TtaSaStdFieldCfgLineEntity_HI_RO> saveFindSupplementExpandInfo(JSONObject jsonObject, int userId);

    TtaSaStdHeaderEntity_HI saveByDisSicradHeader(JSONObject parameters, int userId) throws Exception;

    void updateStatus(JSONObject paramJson, int userId,UserSessionBean userSessionBean) throws Exception;

    JSONObject callSupplementAgreementStandardChangStatus(JSONObject paramsJSON, Integer userId) throws Exception;

    Long savePrint(JSONObject jsonObject, UserSessionBean userSessionBean,int userId) throws Exception;

    TtaSaStdHeaderEntity_HI updateSupplementSkipStatus(JSONObject jsonObject, UserSessionBean userSessionBean) throws Exception;
}
