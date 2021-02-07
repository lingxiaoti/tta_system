package com.sie.wastons.ttadata.model.dao.readonly;


import com.sie.wastons.ttadata.model.entities.readyonly.VmiPersonInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("vmiPersonInfoDAO_HI_RO")
public class VmiPersonInfoDAO_HI_RO extends DynamicViewObjectImpl<VmiPersonInfoEntity_HI_RO> {
    public VmiPersonInfoDAO_HI_RO() {
        super();
    }
}
