package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnLEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnLEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ITtaBrandplnL extends IBaseCommon<TtaBrandplnLEntity_HI>{
    Pagination<TtaBrandplnLEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    List<TtaBrandplnLEntity_HI> callsaveOrUpdate(JSONObject jsonObject, int userId) throws Exception ;



    void delete(Integer articleId);

    void deleteClear(Integer articleId);

    TtaBrandplnLEntity_HI_RO findByRoId(JSONObject queryParamJSON);


    void insertBrandPlanData(String vendorNbr, String purchType, Integer integer,String deleteSymbol) throws Exception;

    List<TtaBrandplnLEntity_HI_RO> findBrandplnDataByProposalSplit(Integer proposalId);

    JSONObject findBrandTotalAndProposalCode(TtaProposalHeaderEntity_HI_RO proposalHeaderEntity_hi_ro);

    int saveImportTtaBrandlpn(Integer proposalId,Integer userId,MultipartFile file) throws Exception;
}
