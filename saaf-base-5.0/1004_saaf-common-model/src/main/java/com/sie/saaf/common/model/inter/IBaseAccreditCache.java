package com.sie.saaf.common.model.inter;

import com.sie.saaf.common.bean.MenuInfoBean;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.bean.ResourceBean;
import com.sie.saaf.common.bean.ResponsibilityBean;

import java.util.List;
import java.util.Set;

public interface IBaseAccreditCache {

    /**
     *   用户菜单权限缓存 reids key 前缀
     */
    public static final String MENU_CACHE="menuCache_";

    /**
     *   用户菜单权限缓存 reids key 前缀,用于获取用户对应菜单下的所有职责
     */
    public static final String MENU_RESP_CACHE="menuRespCache_";

    /**
     * 用户职责缓存 reids key 前缀,用于获取相应用户系统下所有职责
     */
    public static final String RESP_CACHE="respCache_";

    /**
     *  用户资源权限缓存  redis key 前缀
     */
    public static final String RESOURCE_CACHE="resouceCache_";

    /**
     *  用户profile缓存 redis key 前缀
     */
    public static final String PROFILE_CACHE="profileCache_";

    /**
     * 已缓存的用户id集合 redis key
     */
    public static final String USER_CACHE_LIST="userAccreditList";


    Integer getOprRespId();

    ResponsibilityBean findResp(Integer userId, Integer respId);

    ProFileBean getOrg(Integer userId, Integer responsibilityId);

    ProFileBean getOrg(Integer userId);

    List<Integer> getOrgId(Integer userId);

	ProFileBean getUserType(Integer userId, Integer responsibilityId);

    ProFileBean getUserType(Integer userId);

    ProFileBean getChannelType(Integer userId, Integer responsibilityId);

    List<ProFileBean> getChannelType(Integer userId);

    List<ProFileBean> findProfileValByResponsibilityId(Integer responsibilityId, String profileCode);

    List<ResponsibilityBean> findUserResponsibilities(Integer userId, String menuCode);

    List<MenuInfoBean> findMenuTree(Integer userId, String systemCode);

    List<ResourceBean> findUserResources(String menuCode, Integer responsibilityId);

    List<ResponsibilityBean> findUserResponsibility(Integer userId);

    List<ResponsibilityBean> findUserResponsibility(Integer userId, String systemCode);

    boolean menuCacheExist(Integer userId);

    boolean menuRespCacheExist(Integer userId);

    boolean menuRespCacheExist(Integer userId, Integer respId, String menuCode);

    boolean respExist(Integer userId, Integer respId);

    boolean resourceCacheExist(String menuCode, Integer respId, String resourceCode);

    boolean resourceCacheExist(String menuCode, Integer respId);

    boolean profileCacheExist(Integer respId, String profileCode);

    boolean profileCacheExist(Integer respId);

    Integer getOprResp(String certificate, String menuCode);

    Set<String> findAppApply(Integer userId, Integer orgId);
}
