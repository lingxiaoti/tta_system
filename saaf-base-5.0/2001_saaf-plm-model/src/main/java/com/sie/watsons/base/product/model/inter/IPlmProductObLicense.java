package com.sie.watsons.base.product.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.PlmProductObLicenseEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductObLicenseEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmProductObLicense extends IBaseCommon<PlmProductObLicenseEntity_HI>{
    /**
     * 根据节点编码process_node_code和提单人owner_user_id查询一条或多条数据
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<PlmProductObLicenseEntity_HI_RO> findObLicenseByCondition(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     *：指定process_node_code和提单人owner_user_id，新增一条或多条数据
     */
    String saveProductObLicense(JSONObject queryParamJSON);

    String saveProductObLicenseByObId(JSONObject queryParamJSON) throws Exception;

    /**
     * 证书下载接口(新增证书)
     * @param jsonObject
     * @return
     */
    String saveProductObLicenseByNewFile(JSONObject jsonObject) throws  Exception;

    /**
     * 定时调度获取需要同步的到 SPA 的数据
     * @return
     */
    String saveProductObLicenseByTime() throws Exception;
}
