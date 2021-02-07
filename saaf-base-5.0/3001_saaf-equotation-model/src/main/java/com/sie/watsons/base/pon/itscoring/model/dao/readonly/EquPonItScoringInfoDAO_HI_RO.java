package com.sie.watsons.base.pon.itscoring.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.itscoring.model.entities.readonly.EquPonItScoringInfoEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPonItScoringInfoDAO_HI_RO")
public class EquPonItScoringInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPonItScoringInfoEntity_HI_RO>  {
	public EquPonItScoringInfoDAO_HI_RO() {
		super();
	}

}
