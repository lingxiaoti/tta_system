package com.sie.watsons.base.params.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.params.model.entities.TempParamDefEntity_HI;
import com.sie.watsons.base.params.model.entities.readonly.TempParamDefEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface ITempParamDef extends IBaseCommon<TempParamDefEntity_HI>{

	/**
	 * 分页查询
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
    Pagination<TempParamDefEntity_HI_RO> findPage(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

     void saveOrUpdate(TempParamDefEntity_HI entity_hi);

    void delete(Integer tempId);

    TempParamDefEntity_HI_RO findById(Integer tempId);

    /**
     * 功能描述： 查询规则已排除的列表项
     * @author xiaoga
     * @date 2019/5/31
     * @param
     * @return
     */
	Pagination<TempParamDefEntity_HI_RO> findNotExistsList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);


	/**
	 * 功能描述： 通过规则查询到参数信息
	 * @author xiaoga
	 * @date 2019/6/3
	 * @param
	 * @return
	 */
	public List<TempParamDefEntity_HI_RO> findParamsByRuleId(Integer ruleId);


}
