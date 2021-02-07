package com.sie.wastons.ttadata.model.dao.readonly;


import com.sie.wastons.ttadata.model.entities.readyonly.VmiSalesInSearchEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("vmiSalesInSearchDAO_HI_RO")
public class VmiSalesInSearchDAO_HI_RO extends DynamicViewObjectImpl<VmiSalesInSearchEntity_HI_RO> {
    public VmiSalesInSearchDAO_HI_RO() {
        super();
    }
}
