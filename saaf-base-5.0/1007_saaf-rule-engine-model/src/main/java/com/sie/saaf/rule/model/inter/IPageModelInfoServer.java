package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.PageModelInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.PageModelInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on Thu Jul 06 17:24:57 CST 2017
 */
public interface IPageModelInfoServer extends IBaseCommon<PageModelInfoEntity_HI> {

    PageModelInfoEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    boolean delete(Integer id);

    Pagination<PageModelInfoEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize);

    List<PageModelInfoEntity_HI> findByProperty(String name, Object value);

    List<PageModelInfoEntity_HI> findByProperty(Map<String, Object> map);

    PageModelInfoEntity_HI findById(Integer id);
}
