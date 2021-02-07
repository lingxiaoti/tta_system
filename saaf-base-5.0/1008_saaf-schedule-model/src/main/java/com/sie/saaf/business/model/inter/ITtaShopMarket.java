package com.sie.saaf.business.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.sie.saaf.business.model.entities.TtaShopMarketEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.apache.poi.ss.formula.functions.T;

public interface ITtaShopMarket extends IBaseCommon<TtaShopMarketEntity_HI>{

    List<TtaShopMarketEntity_HI> saveBatchShopMarket(Collection<? extends TtaShopMarketEntity_HI> collection) throws Exception;
}
