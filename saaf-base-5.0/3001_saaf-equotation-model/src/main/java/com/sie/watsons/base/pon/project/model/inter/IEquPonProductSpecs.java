package com.sie.watsons.base.pon.project.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.project.model.entities.EquPonProductSpecsEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonProductSpecs extends IBaseCommon<EquPonProductSpecsEntity_HI>{
    //报价管理-产品规格查询，分页查询
    JSONObject findProductSpecs(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //报价管理-产品规格保存
    EquPonProductSpecsEntity_HI saveProductSpecs(JSONObject params) throws Exception;

    //报价管理-产品规格删除
    void deleteProductSpecs(JSONObject params) throws Exception;
    //批量导入
    int importProductSpecsInfo(JSONObject params) throws Exception;
}
