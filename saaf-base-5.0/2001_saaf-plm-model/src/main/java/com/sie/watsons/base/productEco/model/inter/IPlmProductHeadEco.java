package com.sie.watsons.base.productEco.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductHeadEcoEntity_HI_RO;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductParamEcoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.productEco.model.entities.PlmProductHeadEcoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IPlmProductHeadEco extends IBaseCommon<PlmProductHeadEcoEntity_HI>{


    /**
     *  根据货品ID新增修改单信息  20200529 改接口不使用
     * @param param
     * @return
     */
    String addModifyProductHead(JSONObject param) throws Exception;

    /**
     * 根据 货品ID 和 userId(创建人ID) 查询修改单ID  ecoId
     * @param param
     * @return
     */
    Integer getEcoIdByParams(JSONObject param) throws Exception;

    /**
     * 根据ecoId 查询出对象信息
     * @param ecoId
     * @return
     */
    PlmProductParamEcoEntity_HI_RO findEcoInfoByEcoId(Integer ecoId);

    /**
     * 更新修改单   20200529 备注 新增修改用此接口
     * @param data
     * @return
     */
    String updateModifyProductHead(JSONObject data) throws Exception;

    /**
     * 分页查询修改单数据
     * @param param
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<PlmProductHeadEcoEntity_HI_RO> findProductHeadEcoList(JSONObject param, Integer pageIndex, Integer pageRows);

    /**
     * 根据headerId 调用PKG
     * @param ecoId
     * @return
     */
    String diferenceCheckByEcoId(Integer ecoId) throws Exception;

	void updateModify(String string);
}
