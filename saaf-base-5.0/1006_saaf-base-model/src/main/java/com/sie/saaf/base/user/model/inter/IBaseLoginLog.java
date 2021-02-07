package com.sie.saaf.base.user.model.inter;

import com.sie.saaf.base.user.model.entities.BaseLoginLogEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.Date;

public interface IBaseLoginLog extends IBaseCommon<BaseLoginLogEntity_HI> {


    Date getLastLoginDate(Integer userId);
}
