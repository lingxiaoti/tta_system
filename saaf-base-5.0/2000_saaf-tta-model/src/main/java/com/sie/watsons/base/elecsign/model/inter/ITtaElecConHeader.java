package com.sie.watsons.base.elecsign.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractRecordHeaderEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractSpecialHeaderEntity_HI_RO;
import com.sie.watsons.base.elecsign.model.entities.TtaAttrCheckItemEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaElecConAttrLineEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaElecConHeaderEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.readonly.*;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;

public interface ITtaElecConHeader extends IBaseCommon<TtaElecConHeaderEntity_HI> {

    public Pagination<TtaElecConHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 保存电子盖章头信息
     */
    public JSONObject saveElecConHeader(JSONObject jsonObject, Integer userId) throws Exception;

    /**
     * 查询合同附件信息列表
     */
    public Pagination<TtaConAttrLineEntity_HI_RO> findContractList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

        /**
         * 查询合同附件信息列表删除
         */
    public void deleteContractDetail(JSONObject jsonObject);

    /**
     * 查询合同附件信息列表信息保存
     */
    public void saveBacthContractDetail(JSONObject jsonList,  Integer userId) throws Exception;


    /**
     * 电子签章返回信息列表
     */
    public Pagination<TtaElecSignResultLineEntity_HI_RO> findEeleContractHandleList(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;


    /**
     * 通过id查询头部信息及选项信息
     */
    public JSONObject findById(Integer id) throws Exception;

    /**
     * 分页查询不存在合同附件中的附件
     */
    Pagination<TtaConAttrLineEntity_HI_RO> findAddNotExistsAttList(JSONObject paramJson, Integer pageIndex, Integer pageRows);

    /**
     * 保存附件信息
     */
    void saveContractAttrList(List<TtaElecConAttrLineEntity_HI> paramsList);

    void updateApprove(JSONObject jsonObject, int userId);

    TtaElecConHeaderEntity_HI saveChangeElecAll(JSONObject jsonObject, int userId);

    List<Map<String, Object>> findDicValues(JSONObject jsonObject);

    TtaElecConHeaderEntity_HI updateSkipStatus(JSONObject jsonObject, Integer sessionUserId);

    List<TtaAttrCheckItemEntity_HI> findAttCheckList(Integer userId, Integer elecConHeaderId);

    Pagination<TtaContractSpecialHeaderEntity_HI_RO> findSepcial(JSONObject jsonObject, Integer pageIndex, Integer pageRows);


    JSONObject saveCreateContractAttr(JSONObject jsonObject) throws Exception;

    Pagination<TtaContractRecordHeaderEntity_HI_RO> findReceive(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaElecConHeaderEntity_HI saveAndfindContractNo(JSONObject queryParamJSON,int userId) throws Exception;
}
