package com.sie.saaf.business.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.ArrayList;
import java.util.List;
import com.sie.saaf.business.model.entities.TtaPurchaseEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaPurchase extends IBaseCommon<TtaPurchaseEntity_HI>{

    List<TtaPurchaseEntity_HI> saveBatchPurchase(List<TtaPurchaseEntity_HI> ttaPurchaseEntity_his);
}
