package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdProposalLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdProposalLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSaStdProposalLine extends IBaseCommon<TtaSaStdProposalLineEntity_HI>{

    Pagination<TtaSaStdProposalLineEntity_HI_RO> findProposalVendor(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    TtaSaStdProposalLineEntity_HI saveContractProposalVendor(JSONObject queryParamJSON, UserSessionBean sessionBean) throws Exception;

    Pagination<TtaSaStdProposalLineEntity_HI_RO> find(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    TtaSaStdProposalLineEntity_HI delete(JSONObject jsonObject) throws Exception;
}
