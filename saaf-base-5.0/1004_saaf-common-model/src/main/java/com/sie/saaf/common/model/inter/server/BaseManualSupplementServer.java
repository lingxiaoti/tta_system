package com.sie.saaf.common.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseManualSupplementEntity_HI;
import com.sie.saaf.common.model.inter.IBaseManualSupplement;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangtao
 */
@Component("baseManualSupplementServer")
public class BaseManualSupplementServer implements IBaseManualSupplement {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseManualSupplementServer.class);
	@Autowired
	private ViewObject<BaseManualSupplementEntity_HI> baseManualSupplementDAO_HI;

	public BaseManualSupplementServer() {
		super();
	}

	public List<BaseManualSupplementEntity_HI> findBaseManualSupplementInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<BaseManualSupplementEntity_HI> findListResult = baseManualSupplementDAO_HI.findList("from BaseManualSupplementEntity_HI", queryParamMap);
		return findListResult;
	}

	public Object saveBaseManualSupplementInfo(JSONObject queryParamJSON) {
		BaseManualSupplementEntity_HI baseManualSupplementEntity_HI = JSON.parseObject(queryParamJSON.toString(), BaseManualSupplementEntity_HI.class);
		Object resultData = baseManualSupplementDAO_HI.save(baseManualSupplementEntity_HI);
		return resultData;
	}

	@Override
	public void deleteByMessageIndex(Integer messageIndex){
		List<BaseManualSupplementEntity_HI> list= baseManualSupplementDAO_HI.findByProperty("messageIndex",messageIndex);
		if (list.size()==0)
			return;
		baseManualSupplementDAO_HI.delete(list);
	}

	@Override
	public BaseManualSupplementEntity_HI save(BaseManualSupplementEntity_HI instance){
	    if (instance.getOperatorUserId()==null)
	        instance.setOperatorUserId(0);
	    baseManualSupplementDAO_HI.saveOrUpdate(instance);
	    return instance;
    }

    @Override
	public List<BaseManualSupplementEntity_HI> findAllManualSupplementList(){
		String querySQL = " from BaseManualSupplementEntity_HI where 1 = 1";
		return baseManualSupplementDAO_HI.findList(querySQL, new HashMap<>());
	}
}
