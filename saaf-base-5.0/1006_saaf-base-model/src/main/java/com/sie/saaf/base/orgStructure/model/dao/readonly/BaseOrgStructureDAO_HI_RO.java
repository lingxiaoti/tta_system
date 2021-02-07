package com.sie.saaf.base.orgStructure.model.dao.readonly;

import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseOrgStructureEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * @auther: huqitao 2018/6/29
 */
@Component("baseOrgStructureDAO_HI_RO")
public class BaseOrgStructureDAO_HI_RO extends DynamicViewObjectImpl<BaseOrgStructureEntity_HI_RO> {
    public BaseOrgStructureDAO_HI_RO() {
        super();
    }
}
