package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.report.model.entities.TtaSalesSiteEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaSalesSiteEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2019/7/16/016.
 */
public interface ITtaSalesSite extends IBaseCommon<TtaSalesSiteEntity_HI> {

    int saveImportOsdInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaSalesSiteEntity_HI_RO> findOsdInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportOsdInfo(JSONObject queryParamJSON) throws Exception;

    List<TtaSalesSiteEntity_HI_RO> findOsdInfoOne(JSONObject queryParamJSON) throws Exception;

    TtaSalesSiteEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;
}