package com.sie.watsons.base.pon.project.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectChangeCauseEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonProjectChangeCause extends IBaseCommon<EquPonProjectChangeCauseEntity_HI>{

    JSONObject findProjectChangeCause(JSONObject paramsJONS, Integer pageIndex, Integer pageRows);

    void deleteChangeCause(JSONObject params) throws Exception;
}
