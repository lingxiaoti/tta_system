package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsPolistEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsPolistEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaFreeGoodsPolist extends IBaseCommon<TtaFreeGoodsPolistEntity_HI>{

    int saveImportInfo(JSONObject jsonObject, MultipartFile file,UserSessionBean sessionBean) throws Exception;

    Pagination<TtaFreeGoodsPolistEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportInfo(JSONObject queryParamJSON) throws Exception;

    TtaFreeGoodsPolistEntity_HI saveOrUpdate(JSONObject jsonObject, int userId) throws Exception;

}
