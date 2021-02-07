package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupWarehouseEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("plmSupWarehouseDAO_HI_RO")
public class PlmSupWarehouseDAO_HI_RO extends DynamicViewObjectImpl<PlmSupWarehouseEntity_HI_RO>  {
	public PlmSupWarehouseDAO_HI_RO() {
		super();
	}

}
