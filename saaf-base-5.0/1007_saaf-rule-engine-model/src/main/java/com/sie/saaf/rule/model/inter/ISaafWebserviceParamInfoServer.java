package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.SaafWebserviceParamInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.SaafWebserviceParamInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2017/7/5.
 */
public interface ISaafWebserviceParamInfoServer extends IBaseCommon<SaafWebserviceParamInfoEntity_HI> {
    SaafWebserviceParamInfoEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                           IllegalAccessException;

    boolean delete(Integer id);

    Pagination<SaafWebserviceParamInfoEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize);

    List<SaafWebserviceParamInfoEntity_HI> findByProperty(String name, Object value);

    List<SaafWebserviceParamInfoEntity_HI> findByProperty(Map<String, Object> map);

    SaafWebserviceParamInfoEntity_HI findById(Integer id);
}
