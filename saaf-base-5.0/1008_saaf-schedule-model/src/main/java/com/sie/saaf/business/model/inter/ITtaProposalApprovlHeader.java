package com.sie.saaf.business.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.entities.readonly.TtaProposalApprovlHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.saaf.business.model.entities.TtaProposalApprovlHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaProposalApprovlHeader extends IBaseCommon<TtaProposalApprovlHeaderEntity_HI>{

    List<TtaProposalApprovlHeaderEntity_HI_RO> findList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findSubmitterList(JSONObject jsonObject);

    List<TtaProposalApprovlHeaderEntity_HI_RO> findBICList();

    void saveOrUpdateAll(List<TtaProposalApprovlHeaderEntity_HI> personList);

    List<TtaProposalApprovlHeaderEntity_HI_RO> findOSDList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findDmList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findCwList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findNppList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findHwbList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findFgList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findSoleStandarList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findNonSoleStandarList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findStandarSupplementList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findNonStandarSupplementList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findElecContractList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findOsdSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findDMSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findCwSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findNppSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findHwbSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findFgSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findSoleStandarSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findNonSoleStandarSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findSaStandarSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findSaNonStandarSubmitterList();

    List<TtaProposalApprovlHeaderEntity_HI_RO> findElecContSubmitterList();
}
