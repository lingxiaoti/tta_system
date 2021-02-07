package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseProductInfoEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseProductInfoChannelOrg_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseProductInfoEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseProductInfo extends IBaseCommon<BaseProductInfoEntity_HI> {

	/**
	 * 分页查询产品信息
	 * @author ZhangJun
	 * @createTime 2018/3/13
	 * @description 分页查询产品信息
	 */
    Pagination<BaseProductInfoChannelOrg_HI_RO> findProductInfoROPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 取得产品相关信息
     * @author ZhangJun
     * @createTime 2018/3/13
     * @description 取得产品相关信息
     */
    List<BaseProductInfoEntity_HI_RO> findBaseProductInfoEntities(JSONObject queryParamJSON);

    /**
     * 取得产品名称，编码
     * @author ZhangJun
     * @createTime 2018/3/13
     * @description 取得产品名称，编码
     */
    List<BaseProductInfoEntity_HI_RO> findBaseProductInfoItemName(JSONObject queryParamJSON);

    /**
     * 取得产品编码，单位，说明
     * @author ZhangJun
     * @createTime 2018/3/13
     * @description 取得产品编码，单位，说明
     */
    List<BaseProductInfoEntity_HI_RO> findBaseProductInfoItemDesc(JSONObject queryParamJSON);

	/**
	 * 同步物料信息
	 * @author ZhangJun
	 * @createTime 2018/3/14
	 * @description 同步物料信息
	 */
	JSONObject saveSyncProductInfo(JSONObject queryParamJSON);

	/**
	 * 交易汇总相关查询（仓库发货确认）
	 * @author yuzhenli
	 * @description 通过unitTraQuantity+itemCode获取boxUnit
	 */
	BaseProductInfoEntity_HI_RO getBoxUnit(JSONObject queryParamJSON);

	/**
	 * 查询数据
	 * @author ZhangJun
	 * @createTime 2018/3/20
	 * @description 查询数据
	 */
	List findCacheList(JSONObject queryParamJSON);

    Pagination<BaseProductInfoEntity_HI_RO> findItemInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageSize);
}
