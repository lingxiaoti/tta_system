package com.sie.saaf.business.model.inter;

import java.util.LinkedHashSet;

import com.sie.saaf.business.model.entities.TtaMarketInEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaMarketIn extends IBaseCommon<TtaMarketInEntity_HI>{

	public void saveOrUpdateBatchMarket(LinkedHashSet<TtaMarketInEntity_HI> list);
	
}
