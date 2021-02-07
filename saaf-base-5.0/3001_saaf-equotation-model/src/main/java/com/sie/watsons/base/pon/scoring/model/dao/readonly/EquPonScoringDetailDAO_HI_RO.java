package com.sie.watsons.base.pon.scoring.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.scoring.model.entities.readonly.EquPonScoringDetailEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPonScoringDetailDAO_HI_RO")
public class EquPonScoringDetailDAO_HI_RO extends DynamicViewObjectImpl<EquPonScoringDetailEntity_HI_RO>  {
	public EquPonScoringDetailDAO_HI_RO() {
		super();
	}

}
