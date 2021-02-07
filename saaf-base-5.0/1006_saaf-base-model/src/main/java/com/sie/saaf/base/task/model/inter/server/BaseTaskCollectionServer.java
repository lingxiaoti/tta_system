package com.sie.saaf.base.task.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.task.model.entities.BaseTaskCollectionEntity_HI;
import com.sie.saaf.base.task.model.inter.IBaseTaskCollection;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.base.task.model.inter.IBaseTaskCollection;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseTaskCollectionServer")
public class BaseTaskCollectionServer implements IBaseTaskCollection {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseTaskCollectionServer.class);
	@Autowired
	private ViewObject<BaseTaskCollectionEntity_HI> baseTaskCollectionDAO_HI;
	public BaseTaskCollectionServer() {
		super();
	}

	public List<BaseTaskCollectionEntity_HI> findBaseTaskCollectionInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<BaseTaskCollectionEntity_HI> findListResult = baseTaskCollectionDAO_HI.findList("from BaseTaskCollectionEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveBaseTaskCollectionInfo(JSONObject queryParamJSON) {
		BaseTaskCollectionEntity_HI baseTaskCollectionEntity_HI = JSON.parseObject(queryParamJSON.toString(), BaseTaskCollectionEntity_HI.class);
		Object resultData = baseTaskCollectionDAO_HI.save(baseTaskCollectionEntity_HI);
		return resultData;
	}

	public Pagination<BaseTaskCollectionEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append("from BaseTaskCollectionEntity_HI where 1=1 ");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(BaseTaskCollectionEntity_HI.class,queryParamJSON,sql,paramsMap);

		Pagination<BaseTaskCollectionEntity_HI> findList = baseTaskCollectionDAO_HI.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}
}
