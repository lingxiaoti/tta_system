package com.sie.watsons.base.pon.scoring.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.scoring.model.entities.readonly.EquPonScoringInfoEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPonScoringInfoDAO_HI_RO")
public class EquPonScoringInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPonScoringInfoEntity_HI_RO>  {
	public EquPonScoringInfoDAO_HI_RO() {
		super();
	}

}
