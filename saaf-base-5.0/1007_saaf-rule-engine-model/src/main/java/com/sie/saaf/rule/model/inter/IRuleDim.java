package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.RuleDimEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleDimEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IRuleDim extends IBaseCommon<RuleDimEntity_HI> {

    RuleDimEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                       IllegalAccessException;

    boolean delete(Integer id);

    Pagination<RuleDimEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize);

    List<RuleDimEntity_HI> findByProperty(String name, Object value);

    List<RuleDimEntity_HI> findByProperty(Map<String, Object> map);

    RuleDimEntity_HI findById(Integer id);

    String findRuleDimInfo(JSONObject queryParamJSON);

    String saveRuleDimInfo(JSONObject queryParamJSON);

}
