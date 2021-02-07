package com.sie.saaf.schedule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.schedule.model.entities.ScheduleJobParametersEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobParametersEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IJobParameters extends IBaseCommon<ScheduleJobParametersEntity_HI> {
    String saveParameter(JSONObject parameters);
    String saveParameterInfo(JSONObject parameters);

    String deleteJobParameter(JSONObject parameters);

    String updateJobParameter(JSONObject parameters);

    Pagination<ScheduleJobParametersEntity_HI_RO> findJobParameters(JSONObject parameters, Integer pageIndex, Integer pageRows);
}
