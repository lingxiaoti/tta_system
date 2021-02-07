package com.sie.saaf.base.user.model.inter;

import com.sie.saaf.base.shiro.model.entities.BaseMenuEntity_HI;
import com.sie.saaf.common.bean.OrgBean;
import com.sie.saaf.common.bean.ResourceBean;
import com.sie.saaf.common.bean.ResponsibilityBean;
import com.sie.saaf.common.bean.UserSessionBean;

import java.util.List;
import java.util.Set;

public interface IBaseAccredit {
    List<BaseMenuEntity_HI> findMenuInfoFromDb(Integer userId, String systemCode);

    List<ResponsibilityBean> findMenuRespFromDb(Integer userId, String menuCode);

    void setDefaulOprMenuResp(UserSessionBean userSessionBean);

    List<ResponsibilityBean> findRespByUserId(Integer userId, boolean flushCache, String uuid);

    OrgBean findOrgInfo(Integer respId);

    void setMenuOprResp(UserSessionBean userSessionBean, String menuCode, Integer respId);

    List<ResourceBean> findUserResources(Integer userId, Integer responsibilityId, String menuCode, boolean flushCache);

    List<ResourceBean> findUserResourcesFromDb(Integer responsibilityId, String menuCode);

    void flushAccreditCache(Integer userId, String timestamp);

    Set<Integer> findRelatedUserId(Set<String> menuIdArray, Set<String> roleIdArray, Set<String> respIdArray, Set<String> resourceIdArray);
}
