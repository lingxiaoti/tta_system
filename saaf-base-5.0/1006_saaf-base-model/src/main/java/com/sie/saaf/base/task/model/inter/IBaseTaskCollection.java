package com.sie.saaf.base.task.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.saaf.base.task.model.entities.BaseTaskCollectionEntity_HI;

public interface IBaseTaskCollection {

	List<BaseTaskCollectionEntity_HI> findBaseTaskCollectionInfo(JSONObject queryParamJSON);

	Object saveBaseTaskCollectionInfo(JSONObject queryParamJSON);

}
