package com.sie.saaf.business.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.ArrayList;
import java.util.List;
import com.sie.saaf.business.model.entities.TtaVendorInterEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaVendorInter extends IBaseCommon<TtaVendorInterEntity_HI>{

    List<TtaVendorInterEntity_HI> saveBatchVendor(List<TtaVendorInterEntity_HI> ttaVendorInterEntity_his);
}
