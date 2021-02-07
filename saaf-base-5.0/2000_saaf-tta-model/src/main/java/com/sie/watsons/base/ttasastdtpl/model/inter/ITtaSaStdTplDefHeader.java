package com.sie.watsons.base.ttasastdtpl.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplDefHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdTplDefHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSaStdTplDefHeader extends IBaseCommon<TtaSaStdTplDefHeaderEntity_HI>{

    List<TtaSaStdTplDefHeaderEntity_HI_RO> findTtaSaStdTplDefHeaderTree(JSONObject queryParamJSON);

    TtaSaStdTplDefHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, UserSessionBean userSessionBean, int userId) throws Exception;
    void delete(Integer pkId);
}
