package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.TtaMonthlyCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbCheckingEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaHwbCheckingEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaHwbChecking extends IBaseCommon<TtaHwbCheckingEntity_HI>{

    int saveImportHwbMajorInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaHwbCheckingEntity_HI_RO> findHwbMajorInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception ;

    JSONObject deleteImportHwbMajorInfo(JSONObject queryParamJSON) throws Exception;

    JSONObject saveOrUpdateFind(JSONObject paramsJSON, int userId) throws Exception ;

    List<TtaHwbCheckingEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception ;

    List<TtaHwbCheckingEntity_HI> saveOrUpdateSplitALL(JSONArray paramsJSON, int userId, JSONObject currentRow) throws Exception;

    List<TtaHwbCheckingEntity_HI> saveOrUpdateTransferALL(JSONObject paramsJSON, int userId) throws Exception ;

    /**
     * 查询报表的SUNMARY
     * @param queryParamJSON
     * @param userId
     * @return
     * @throws Exception
     */
    List<TtaHwbCheckingEntity_HI_RO> findHwbMajorSummaryList(JSONObject queryParamJSON) throws Exception ;

    /**
     * 报表导出
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    Pagination<TtaHwbCheckingEntity_HI_RO> findExportPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception;

    void updateImportHWBInfo(JSONObject jsonObject, MultipartFile file)  throws  Exception;

    Pagination<TtaHwbCheckingEntity_HI_RO>  findProcessSummaryHwbInfo(JSONObject queryParamJSON, UserSessionBean sessionBean) throws Exception;

    Pagination<TtaHwbCheckingEntity_HI_RO>  findProcessHwbInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception;

    void findCheckGroupCount(JSONObject paramsJson, UserSessionBean sessionBean);

    Pagination<TtaHwbCheckingEntity_HI_RO> findNotExist(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception;


}
