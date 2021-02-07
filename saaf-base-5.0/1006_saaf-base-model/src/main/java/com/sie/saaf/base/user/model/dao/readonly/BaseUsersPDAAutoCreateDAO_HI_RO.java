package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPDAAutoCreate_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * Created by huangminglin on 2018/3/26.
 */
@Component("baseUsersPDAAutoCreateDAO_HI_RO")
public class BaseUsersPDAAutoCreateDAO_HI_RO extends DynamicViewObjectImpl<BaseUsersPDAAutoCreate_HI_RO> {
    public BaseUsersPDAAutoCreateDAO_HI_RO() {
        super();
    }
}
