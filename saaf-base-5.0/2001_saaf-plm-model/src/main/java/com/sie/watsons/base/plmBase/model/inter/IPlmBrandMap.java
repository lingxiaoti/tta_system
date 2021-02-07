package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBrandMapEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandMapEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface IPlmBrandMap extends IBaseCommon<PlmBrandMapEntity_HI>{

    /**
     * 分页查询 品牌
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<PlmBrandMapEntity_HI_RO> findBrandMapByCondition(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 删除数据
     * @param parameters
     */
    String deletedByCondition(JSONObject parameters);

    /**
     * 修改保存数据
     * @param jsonObject
     */
    String saveBrandMap(JSONObject jsonObject) throws Exception;

    /**
     *  根据ID 查询详情
     * @param queryParamJSON
     * @return
     */
    PlmBrandMapEntity_HI_RO findBrandMapDetail(JSONObject queryParamJSON) throws Exception;

    /**
     * 根据brandMapId 品牌同步中英文品牌 Csv
     * @return
     * @param brandMapId
     */
    String brandToCsv(Integer brandMapId) throws Exception;

    /**
     * 中英文品牌调用存储过程
     * @return
     */
    String syncCnEnBrand() throws Exception;

    /**
     * 查询brandMap
     * @param cname
     * @param ename
     * @param motherCompanyId
     * @param groupbrandId
     * @return
     */
    PlmBrandMapEntity_HI findBybrandOrCompany(String cname, String ename, Integer motherCompanyId, Integer groupbrandId,
                                              Integer brandMapId);

    PlmBrandMapEntity_HI findBybrandOrCompany(String cname, String ename, Integer motherCompanyId, Integer groupbrandId,
                                              Integer brandMapId, List<String> status);

    void initMotherCompanyIdForMC();
}
