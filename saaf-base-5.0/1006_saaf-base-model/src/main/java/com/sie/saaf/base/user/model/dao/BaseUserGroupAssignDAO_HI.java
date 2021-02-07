package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BaseUserGroupAssignEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseUserGroupAssignDAO_HI")
public class BaseUserGroupAssignDAO_HI extends BaseCommonDAO_HI<BaseUserGroupAssignEntity_HI> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseUserGroupAssignEntity_HI.class);
    public BaseUserGroupAssignDAO_HI() {
        super();
    }

    @Override
    public Object save(BaseUserGroupAssignEntity_HI entity) {
        return super.save(entity);
    }
}
