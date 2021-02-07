package com.sie.saaf.business.model.inter;

import java.util.LinkedHashSet;

import com.sie.saaf.business.model.entities.TtaDeptInEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaDeptIn extends IBaseCommon<TtaDeptInEntity_HI>{
	public void saveOrUpdateBatchDept(LinkedHashSet<TtaDeptInEntity_HI> list);
}
