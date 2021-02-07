package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.report.model.entities.TtaMonthlyCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaCWCheckingReport_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaMonthlyCheckingEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2019/7/17/017.
 */
public interface ITtaMonthlyChecking extends IBaseCommon<TtaMonthlyCheckingEntity_HI> {

    int saveImportOsdMajorInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaMonthlyCheckingEntity_HI_RO> findOsdMajorInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows,UserSessionBean sessionBean) throws Exception ;

    JSONObject deleteImportOsdMajorInfo(JSONObject queryParamJSON) throws Exception;

    JSONObject saveOrUpdateFind(JSONObject paramsJSON, int userId) throws Exception ;

    List<TtaMonthlyCheckingEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception ;

    List<TtaMonthlyCheckingEntity_HI> saveOrUpdateSplitALL(JSONArray paramsJSON, int userId, JSONObject currentRow) throws Exception;

    List<TtaMonthlyCheckingEntity_HI> saveOrUpdateTransferALL(JSONObject paramsJSON, int userId) throws Exception ;

    /**
     * 查询报表的SUNMARY
     * @param queryParamJSON
     * @param userId
     * @return
     * @throws Exception
     */
    List<TtaMonthlyCheckingEntity_HI_RO> findOsdMajorSummaryList(JSONObject queryParamJSON) throws Exception ;

    /**
     * 报表导出
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    Pagination<TtaMonthlyCheckingEntity_HI_RO> findExportPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception;

    void updateImportOSDInfo(JSONObject jsonObject, MultipartFile file)  throws  Exception;

    Pagination<TtaMonthlyCheckingEntity_HI_RO>  findProcessSummaryOsdInfo(JSONObject queryParamJSON, UserSessionBean sessionBean) throws Exception;

    Pagination<TtaMonthlyCheckingEntity_HI_RO>  findProcessOsdInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception;

    void findCheckGroupCount(JSONObject paramsJson, UserSessionBean sessionBean);

    List<TtaMonthlyCheckingEntity_HI_RO> findAmountList(JSONObject queryParamJSON) throws Exception;

    Pagination<TtaMonthlyCheckingEntity_HI_RO> findNotExist(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception;
}