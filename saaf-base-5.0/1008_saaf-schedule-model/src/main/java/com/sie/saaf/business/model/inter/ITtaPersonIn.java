package com.sie.saaf.business.model.inter;

import java.util.List;

import com.sie.saaf.business.model.entities.TtaPersonInEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;


public interface ITtaPersonIn extends IBaseCommon<TtaPersonInEntity_HI>{

	/**
	 * 批量保存人员信息
	 * @param personList
	 */
	public void saveOrUpdateAll(List<TtaPersonInEntity_HI> personList);

	public void callProBasePersonJob();

}
