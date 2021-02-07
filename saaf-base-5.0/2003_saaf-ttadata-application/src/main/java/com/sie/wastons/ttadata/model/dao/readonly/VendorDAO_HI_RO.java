package com.sie.wastons.ttadata.model.dao.readonly;

import com.sie.wastons.ttadata.model.entities.readyonly.VendorInfoEntity_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("vendorDAO_HI_RO")
public class VendorDAO_HI_RO extends DynamicViewObjectImpl<VendorInfoEntity_RO> {

}
