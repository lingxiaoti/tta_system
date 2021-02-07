package com.sie.watsons.base.shopmarket.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.shopmarket.model.entities.readonly.TtaShopMarketEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.shopmarket.model.entities.TtaShopMarketEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaShopMarket extends IBaseCommon<TtaShopMarketEntity_HI>{

    Pagination<TtaShopMarketEntity_HI_RO> findShopPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception;
}
