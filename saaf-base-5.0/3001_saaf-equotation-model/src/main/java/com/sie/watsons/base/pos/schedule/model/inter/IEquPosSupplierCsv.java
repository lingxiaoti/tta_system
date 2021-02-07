package com.sie.watsons.base.pos.schedule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.schedule.model.entities.EquPosSupplierCsvEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosSupplierCsv extends IBaseCommon<EquPosSupplierCsvEntity_HI>{

    ResultFileEntity findExportSupplier() throws Exception;
}
