package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseProductInvEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseProductInv_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseProductInv extends IBaseCommon<BaseProductInvEntity_HI> {

	/**
	 * 关联产品及子库查询分页
	 * @author ZhangJun
	 * @createTime 2018/3/9
	 * @description 关联产品及子库查询分页
	 */
	Pagination<BaseProductInv_HI_RO> findROPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<BaseProductInv_HI_RO> findROList(JSONObject queryParamJSON);

	/**
	 * 保存或更新数据
	 * @param queryParamJSON {
	 *     itemCode:产品编码,
	 *     warehouseCodes:['xx','xx']子库编码集合,
	 *     productInvId:主键,
	 *     versionNum:版本号
	 * }
	 * @return
	 * @author ZhangJun
	 * @createTime 2018/3/9
	 */
	BaseProductInvEntity_HI saveAll(JSONObject queryParamJSON);
}
