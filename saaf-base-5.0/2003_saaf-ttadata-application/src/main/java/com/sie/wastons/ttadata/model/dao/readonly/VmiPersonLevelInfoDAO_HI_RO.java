package com.sie.wastons.ttadata.model.dao.readonly;


import com.sie.wastons.ttadata.model.entities.readyonly.VmiPersonLevelInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("vmiPersonLevelInfoDAO_HI_RO")
public class VmiPersonLevelInfoDAO_HI_RO extends DynamicViewObjectImpl<VmiPersonLevelInfoEntity_HI_RO> {
    public VmiPersonLevelInfoDAO_HI_RO() {
        super();
    }
}
