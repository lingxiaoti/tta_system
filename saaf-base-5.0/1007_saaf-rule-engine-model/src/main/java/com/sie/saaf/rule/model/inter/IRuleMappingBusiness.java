package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.RuleMappingBusinessEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleMappingBusinessEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IRuleMappingBusiness extends IBaseCommon<RuleMappingBusinessEntity_HI> {

    RuleMappingBusinessEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                                   IllegalAccessException;

    boolean delete(Integer id);

    Pagination<RuleMappingBusinessEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize);

    List<RuleMappingBusinessEntity_HI> findByProperty(String name, Object value);

    List<RuleMappingBusinessEntity_HI> findByProperty(Map<String, Object> map);

    RuleMappingBusinessEntity_HI findById(Integer id);
}
