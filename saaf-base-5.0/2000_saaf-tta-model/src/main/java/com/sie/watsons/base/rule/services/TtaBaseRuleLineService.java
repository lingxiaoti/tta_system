package com.sie.watsons.base.rule.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.rule.model.inter.ITtaBaseRuleLine;

@RestController
@RequestMapping("/ttaBaseRuleLineService")
public class TtaBaseRuleLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBaseRuleLineService.class);

	@Autowired
	private ITtaBaseRuleLine ttaBaseRuleLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaBaseRuleLineServer;
	}

}