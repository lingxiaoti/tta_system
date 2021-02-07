package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.PageModelGroupInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.PageModelGroupInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on Fri Jul 07 17:27:23 CST 2017
 */
public interface IPageModelGroupInfoServer extends IBaseCommon<PageModelGroupInfoEntity_HI> {

    PageModelGroupInfoEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                      IllegalAccessException;

    boolean delete(Integer id);

    Pagination<PageModelGroupInfoEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize);

    List<PageModelGroupInfoEntity_HI> findByProperty(String name, Object value);

    List<PageModelGroupInfoEntity_HI> findByProperty(Map<String, Object> map);

    PageModelGroupInfoEntity_HI findById(Integer id);
}
