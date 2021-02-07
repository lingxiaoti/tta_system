package com.sie.saaf.business.model.inter;

import java.util.LinkedHashSet;
import java.util.List;

import com.sie.saaf.business.model.entities.TtaOrgInEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaOrgIn extends IBaseCommon<TtaOrgInEntity_HI>{

  public void saveOrUpdateBatchOrg(LinkedHashSet<TtaOrgInEntity_HI> list);
  
  public void callProBaseOrganization();
  
}
