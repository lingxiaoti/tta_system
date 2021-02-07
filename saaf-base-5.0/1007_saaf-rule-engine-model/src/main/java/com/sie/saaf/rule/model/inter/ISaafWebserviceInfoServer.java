package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.SaafWebserviceInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.SaafWebserviceInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2017/7/5.
 */
public interface ISaafWebserviceInfoServer extends IBaseCommon<SaafWebserviceInfoEntity_HI> {
    SaafWebserviceInfoEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                      IllegalAccessException;

    boolean delete(Integer id);

    Pagination<SaafWebserviceInfoEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize);

    List<SaafWebserviceInfoEntity_HI> findByProperty(String name, Object value);

    List<SaafWebserviceInfoEntity_HI> findByProperty(Map<String, Object> map);

    SaafWebserviceInfoEntity_HI findById(Integer id);
}
