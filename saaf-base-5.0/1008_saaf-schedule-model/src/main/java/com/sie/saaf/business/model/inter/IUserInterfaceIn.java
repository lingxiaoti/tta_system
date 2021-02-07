package com.sie.saaf.business.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.LinkedHashSet;
import java.util.List;
import com.sie.saaf.business.model.entities.UserInterfaceInEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IUserInterfaceIn extends IBaseCommon<UserInterfaceInEntity_HI>{

    void saveOrUpdateBatchUserInterfaceIn(LinkedHashSet<UserInterfaceInEntity_HI> reportList);


    void callProUpdateTtaUserInterfaceIn();

    void updateUserInterfaceIn() throws Exception;
}
