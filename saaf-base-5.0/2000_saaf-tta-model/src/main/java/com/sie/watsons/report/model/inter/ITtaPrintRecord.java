package com.sie.watsons.report.model.inter;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.report.model.entities.TtaPrintRecordEntity_HI;

public interface ITtaPrintRecord extends IBaseCommon<TtaPrintRecordEntity_HI>{

	public int saveOrUpdatePrintCount(TtaPrintRecordEntity_HI entity);
}
