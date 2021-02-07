package com.sie.saaf.base.dict.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupTypesEntity_HI;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupTypeEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * @author huangtao
 * @createTime 2017年12月11日 20:35:25
 */
public interface IBaseLookupTypes extends IBaseCommon<BaseLookupTypesEntity_HI> {


	BaseLookupTypesEntity_HI saveOrUpdateBaseLookupType(JSONObject parameter, int userId) throws Exception;

	Pagination<BaseLookupTypeEntity_HI_RO> findBaseLookupTypesPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	void delete(Integer id);
}
