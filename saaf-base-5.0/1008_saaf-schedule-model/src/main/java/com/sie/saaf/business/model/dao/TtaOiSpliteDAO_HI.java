package com.sie.saaf.business.model.dao;

import com.sie.saaf.business.model.entities.readonly.TtaOiSalesSceneEntity_HI_RO;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ttaOiSpliteDAO_HI")
public class TtaOiSpliteDAO_HI extends BaseCommonDAO_HI<HashMap> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiSpliteDAO_HI.class);

	public TtaOiSpliteDAO_HI() {
		super();
	}


	public Map<String, List<String>> findOiSpliteTableFieldMapping(String tradeYear, String businessType) {
		Map<String, List<String>> resultMap = new HashMap<>();
		List<String> sourceFieldNameList = new ArrayList<String>();
		List<String> targetFieldNameList = new ArrayList<String>();
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("tradeYear", tradeYear);
		queryMap.put("businessType",businessType);
		List<Map<String, Object>> list = this.queryNamedSQLForList(TtaOiSalesSceneEntity_HI_RO.FIELD_MAP_SQL, queryMap);
		if (list != null) {
			list.forEach(item->{
				sourceFieldNameList.add(item.get("sourceFieldName") + "");
				targetFieldNameList.add(item.get("targetFieldName") + "");
			});
		}
		resultMap.put("sourceList", sourceFieldNameList);
		resultMap.put("targetList", targetFieldNameList);
		return resultMap;
	}

}
