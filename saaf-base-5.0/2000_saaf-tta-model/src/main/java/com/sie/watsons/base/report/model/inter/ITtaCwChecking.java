package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.readonly.TtaCwCheckingEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaCwCheckingEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaCwChecking extends IBaseCommon<TtaCwCheckingEntity_HI>{

    int saveImportCwInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaCwCheckingEntity_HI_RO> findCwInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows,UserSessionBean sessionBean) throws Exception ;

    JSONObject deleteImportCwInfo(JSONObject queryParamJSON) throws Exception;

    JSONObject saveOrUpdateFind(JSONObject paramsJSON, int userId) throws Exception ;

    List<TtaCwCheckingEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception ;

    List<TtaCwCheckingEntity_HI> saveOrUpdateSplitALL(JSONArray paramsJSON, int userId,JSONObject currentRow) throws Exception;

    List<TtaCwCheckingEntity_HI> saveOrUpdateTransferALL(JSONObject paramsJSON, int userId) throws Exception ;

    /**
     * 查询CW 报表的SUNMARY
     * @param queryParamJSON
     * @param userId
     * @return
     * @throws Exception
     */
    List<TtaCwCheckingEntity_HI_RO> findCwSummaryList(JSONObject queryParamJSON) throws Exception ;

    /**
     * 报表导出  ABOI模板
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    Pagination<TtaCwCheckingEntity_HI_RO> findExportPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception;

     void updateImportCWInfo(JSONObject jsonObject, MultipartFile file)  throws  Exception;

    Pagination<TtaCwCheckingEntity_HI_RO>  findProcessSummaryCwInfo(JSONObject queryParamJSON, UserSessionBean sessionBean) throws Exception;

    Pagination<TtaCwCheckingEntity_HI_RO>  findProcessCwInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception;

    void findCheckGroupCount(JSONObject paramsJson, UserSessionBean sessionBean);

    Pagination<TtaCwCheckingEntity_HI_RO> findNotExist(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception;

    List<TtaCwCheckingEntity_HI_RO> findAmountList(JSONObject queryParamJSON) throws Exception;

}
