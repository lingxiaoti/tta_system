package com.sie.watsons.base.pon.project.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProjectInfoEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPonProjectInfoDAO_HI_RO")
public class EquPonProjectInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPonProjectInfoEntity_HI_RO>  {
	public EquPonProjectInfoDAO_HI_RO() {
		super();
	}

}
