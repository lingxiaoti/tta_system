package com.sie.watsons.base.rule.model.inter.server;

import com.sie.watsons.base.rule.model.entities.TempParamRuleMidleEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.rule.model.inter.ITempParamRuleMidle;
import com.yhg.hibernate.core.dao.ViewObject;

import java.util.List;

@Component("tempParamRuleMidleServer")
public class TempParamRuleMidleServer extends BaseCommonServer<TempParamRuleMidleEntity_HI> implements ITempParamRuleMidle{
	private static final Logger LOGGER = LoggerFactory.getLogger(TempParamRuleMidleServer.class);

	@Autowired
	private ViewObject<TempParamRuleMidleEntity_HI> tempParamRuleMidleDAO_HI;

	public TempParamRuleMidleServer() {
		super();
	}

	@Override
	public void saveParams(List<TempParamRuleMidleEntity_HI> entityHiList) {
		tempParamRuleMidleDAO_HI.saveAll(entityHiList);
	}

	@Override
	public void deleteParamById(List<TempParamRuleMidleEntity_HI> entityList) {
		tempParamRuleMidleDAO_HI.deleteAll(entityList);
	}
}
