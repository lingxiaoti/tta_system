package com.sie.saaf.base.sso.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.sso.model.entities.readonly.BaseSystemSsoRepEntity_HI_RO;
import com.sie.saaf.base.sso.model.inter.IBaseResponsibilitySystem;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author huangtao
 * @createTime 2017年12月13日 10:55:53
 * @description 职责系统映射关系表 server
 */
@Component("baseResponsibilitySystemServer")
public class BaseResponsibilitySystemServer implements IBaseResponsibilitySystem {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseResponsibilitySystemServer.class);

    @Autowired
    private BaseViewObject<BaseSystemSsoRepEntity_HI_RO> baseSystemSsoRepDAO_HI_RO;




    @Override
    public List<BaseSystemSsoRepEntity_HI_RO> findSystemByResponsibilityId(Set<Integer> idSet) {
        if (idSet == null || idSet.size() == 0)
            return new ArrayList<>();
        StringBuilder ids = new StringBuilder();
        for (Integer id : idSet) {
            ids.append(id).append(" ,");
        }

        StringBuffer sql = new StringBuffer(BaseSystemSsoRepEntity_HI_RO.SQL_ROLE_SYSTEM);
        JSONObject jsonObject = new JSONObject().fluentPut("responsibility_id_in", ids.substring(0, ids.length() - 1));
        Map<String, Object> map = new HashMap<>();
        SaafToolUtils.parperHbmParam(BaseSystemSsoRepEntity_HI_RO.class, jsonObject, sql, map);
       // sql.append(" GROUP BY bss.system_code ORDER BY bss.order_no asc");
        sql.append(" ORDER BY bss.order_no asc");
        List<BaseSystemSsoRepEntity_HI_RO> list = baseSystemSsoRepDAO_HI_RO.findList(sql, map);
        return list;
    }

    /**
     * 查询超级管理员能访问的系统，查询所有的有效系统
     * @return {@link BaseSystemSsoRepEntity_HI_RO}
     * @author ZhangJun
     * @createTime 2018/2/6
     * @description 查询超级管理员能访问的系统，查询所有的有效系统
     */
    @Override
    public List<BaseSystemSsoRepEntity_HI_RO> findSystemBySuperAdmin() {
        StringBuffer sql = new StringBuffer(BaseSystemSsoRepEntity_HI_RO.SQL_ROLE_SYSTEM);
       // sql.append(" GROUP BY bss.system_code ORDER BY bss.order_no asc");
        sql.append(" ORDER BY bss.order_no asc");
        List<BaseSystemSsoRepEntity_HI_RO> list = baseSystemSsoRepDAO_HI_RO.findList(sql);
        return list;
    }
}
