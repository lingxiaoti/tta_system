package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.RuleBusinessLineEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleBusinessLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IRuleBusinessLine extends IBaseCommon<RuleBusinessLineEntity_HI> {

    String findRuleBusinessLineInfo(JSONObject queryParamJSON);

    String saveRuleBusinessLineInfo(JSONObject queryParamJSON);

    RuleBusinessLineEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                                IllegalAccessException;

    Pagination<RuleBusinessLineEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize);

    List<RuleBusinessLineEntity_HI_RO> findRuleBusinessLineList(JSONObject parameters) throws Exception;

    boolean delete(Integer id);

    //规则引擎业务线分页
    Pagination<RuleBusinessLineEntity_HI_RO> pageRuleBusinessLine(JSONObject parameters,Integer pageIndex,Integer pageRows) throws Exception;
}
