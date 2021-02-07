package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.PageModelGroupDetailEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.PageModelGroupDetailEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on Fri Jul 07 17:28:51 CST 2017
 */
public interface IPageModelGroupDetailServer extends IBaseCommon<PageModelGroupDetailEntity_HI> {

    PageModelGroupDetailEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                        IllegalAccessException;

    boolean delete(Integer id);

    Pagination<PageModelGroupDetailEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize);

    List<PageModelGroupDetailEntity_HI> findByProperty(String name, Object value);

    List<PageModelGroupDetailEntity_HI> findByProperty(Map<String, Object> map);

    PageModelGroupDetailEntity_HI findById(Integer id);
}
