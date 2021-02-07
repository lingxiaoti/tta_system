package com.sie.watsons.base.pon.project.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProductSpecsEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPonProductSpecsDAO_HI_RO")
public class EquPonProductSpecsDAO_HI_RO extends DynamicViewObjectImpl<EquPonProductSpecsEntity_HI_RO>  {
	public EquPonProductSpecsDAO_HI_RO() {
		super();
	}

}
