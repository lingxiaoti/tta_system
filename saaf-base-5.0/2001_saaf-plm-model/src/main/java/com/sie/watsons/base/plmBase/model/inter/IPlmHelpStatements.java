package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmHelpStatementsEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmHelpStatementsEntity_HI;

/**
 * @author panshenghao
 */
public interface IPlmHelpStatements extends IBaseCommon<PlmHelpStatementsEntity_HI> {

	/**
	 * 查询
	 * @param queryParams 查询传参
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	Pagination<PlmHelpStatementsEntity_HI_RO> findPlmHelpStatementsInfo(JSONObject queryParams, Integer pageIndex, Integer pageRows);

	/**
	 * 保存
	 * @param queryParams
	 * @return
	 */
	PlmHelpStatementsEntity_HI savePlmHelpStatementsInfo(JSONObject queryParams);

}
