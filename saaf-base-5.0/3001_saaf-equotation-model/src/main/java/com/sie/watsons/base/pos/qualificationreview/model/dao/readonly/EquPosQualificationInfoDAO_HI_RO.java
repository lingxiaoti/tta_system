package com.sie.watsons.base.pos.qualificationreview.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosQualificationInfoEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosQualificationInfoDAO_HI_RO")
public class EquPosQualificationInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPosQualificationInfoEntity_HI_RO>  {
	public EquPosQualificationInfoDAO_HI_RO() {
		super();
	}

}
