package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPersonAuthority_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * Created by WORK on 2019/7/11.
 */
@Component("baseUsersPersonAuthorityDAO_HI_RO")
public class BaseUsersPersonAuthorityDAO_HI_RO extends DynamicViewObjectImpl<BaseUsersPersonAuthority_HI_RO> {
    public BaseUsersPersonAuthorityDAO_HI_RO() {
        super();
    }
}
