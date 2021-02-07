package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.watsons.base.report.model.entities.TtaIrTermsEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbAttendanceFeeEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaHwbAttendanceFeeEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaHwbAttendanceFee extends IBaseCommon<TtaHwbAttendanceFeeEntity_HI>{

    Pagination<TtaHwbAttendanceFeeEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Pagination<BaseUsersPerson_HI_RO> findUser(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaHwbAttendanceFeeEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

}
