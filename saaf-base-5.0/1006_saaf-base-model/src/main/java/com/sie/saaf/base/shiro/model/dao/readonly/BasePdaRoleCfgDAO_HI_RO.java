package com.sie.saaf.base.shiro.model.dao.readonly;

import com.sie.saaf.base.shiro.model.entities.readonly.BasePdaRoleCfg_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * Created by huangminglin on 2018/3/28.
 */
@Component("basePdaRoleCfgDAO_HI_RO")
public class BasePdaRoleCfgDAO_HI_RO extends DynamicViewObjectImpl<BasePdaRoleCfg_HI_RO> {
    public BasePdaRoleCfgDAO_HI_RO() {
        super();
    }
}
