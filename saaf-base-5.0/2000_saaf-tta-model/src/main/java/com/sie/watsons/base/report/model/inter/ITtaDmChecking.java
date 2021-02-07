package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.report.model.entities.TtaDmCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmCheckingEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ITtaDmChecking extends IBaseCommon<TtaDmCheckingEntity_HI>{

    int saveImportCwInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaDmCheckingEntity_HI_RO> findDmInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaDmCheckingEntity_HI_RO> findProcessDmInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaDmCheckingEntity_HI_RO> findProcessSummaryDmInfo(JSONObject queryParamJSON, UserSessionBean sessionBean) throws Exception ;

    JSONObject deleteImportDmInfo(JSONObject queryParamJSON) throws Exception;

    JSONObject saveOrUpdateFind(JSONObject paramsJSON, int userId) throws Exception ;

    List<TtaDmCheckingEntity_HI> saveOrUpdateALL(JSONArray paramsJSON, int userId) throws Exception;

    List<TtaDmCheckingEntity_HI> saveOrUpdateSplitALL(JSONArray paramsJSON, int userId,JSONObject currentRow) throws Exception;

    List<TtaDmCheckingEntity_HI> saveOrUpdateTransferALL(JSONObject paramsJSON, int userId) throws Exception ;

    public void updateImportDMInfo(JSONObject jsonObject, MultipartFile file)  throws  Exception;

    /**
     * 再提交流程之前，检查所有的部门是否全部提交了流程
     */
    public void findCheckGroupCount(JSONObject paramsJson, UserSessionBean sessionBean);

    Pagination<TtaDmCheckingEntity_HI_RO> findNotExist(JSONObject jsonObject, Integer pageIndex, Integer pageRows);


    TtaDmCheckingEntity_HI_RO findChangeVender(JSONObject queryParamJSON);
}
