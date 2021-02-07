package com.sie.saaf.base.user.model.inter;

import com.sie.saaf.base.user.model.entities.BaseOrganizationEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Chenzg
 * @creteTime 2018-04-13
 */
public interface IBaseOrganizationSync extends IBaseCommon<BaseOrganizationEntity_HI> {

    List<BaseOrganizationEntity_HI> syncOrganizations() throws SQLException;
}
