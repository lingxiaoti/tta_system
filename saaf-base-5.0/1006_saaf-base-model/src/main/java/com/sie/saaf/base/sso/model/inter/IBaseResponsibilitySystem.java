package com.sie.saaf.base.sso.model.inter;

import com.sie.saaf.base.sso.model.entities.readonly.BaseSystemSsoRepEntity_HI_RO;

import java.util.List;
import java.util.Set;

/**
 * @author hunagtao
 */
public interface IBaseResponsibilitySystem  {

    List<BaseSystemSsoRepEntity_HI_RO> findSystemByResponsibilityId(Set<Integer> idSet);

	List<BaseSystemSsoRepEntity_HI_RO> findSystemBySuperAdmin();
}
