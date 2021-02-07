package com.sie.watsons.base.questionnaire.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaSubjectChoiceSecMidEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSubjectChoiceSecMidDAO_HI")
public class TtaSubjectChoiceSecMidDAO_HI extends BaseCommonDAO_HI<TtaSubjectChoiceSecMidEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSubjectChoiceSecMidDAO_HI.class);

	public TtaSubjectChoiceSecMidDAO_HI() {
		super();
	}

	public void deleteByChoiceId(Integer qnChoiceId) {
		this.executeSqlUpdate("delete from tta_subject_choice_sec_mid where qn_choice_id =" + qnChoiceId);
	}

}
