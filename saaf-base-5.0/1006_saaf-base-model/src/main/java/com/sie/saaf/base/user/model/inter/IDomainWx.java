package com.sie.saaf.base.user.model.inter;

import com.sie.saaf.base.user.model.entities.DomainWxEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IDomainWx extends IBaseCommon<DomainWxEntity_HI> {

    DomainWxEntity_HI findByDomain(String domain);
}
