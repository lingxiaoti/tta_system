package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmUserBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmUserBrandMapEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.Date;
import java.util.List;

public interface IPlmUserBrandMap extends IBaseCommon<PlmUserBrandMapEntity_HI> {

    List<PlmUserBrandMapEntity_HI> getUserBrandMaps(Integer brandMapId, Integer brandInfoId);

    List<PlmUserBrandMapEntity_HI> getUserBrandMaps(Integer brandMapId, Integer brandInfoId, Integer supUserId,
                                                    Integer motherCompanyId, Integer groupBrandId,
                                                    List<Integer> status, Integer deleteFlag);

    /**
     * UserBrandMaps假删除（deleteFlag标记为1）
     * @param brandMapId
     */
    void deleteByBrandMapId(Integer brandMapId);

    void deleteByBrandInfoId(Integer brandInfoId);

    void updateStatusByBrandInfoId(Integer brandInfoId, Integer status);

    void updateStatusByBpm(Integer brandInfoId, Integer status);

    Pagination<PlmUserBrandMapEntity_HI_RO> findBaseUsersPage(
            JSONObject param, Integer pageIndex, Integer pageRows, String certificate);

    void syncToUserBrandMaps(Date startDate, Date endDate);

    int insertUserBrandMap(Integer motherCompanyId, Integer groupBrandId, String brandCn, String brandEn,
                           Integer accountId);
}
