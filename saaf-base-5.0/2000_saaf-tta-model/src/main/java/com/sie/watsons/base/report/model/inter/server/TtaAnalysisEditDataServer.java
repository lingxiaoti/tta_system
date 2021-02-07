package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.report.model.dao.readonly.TtaAnalysisEditDataDAO_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaAnalysisEditDataEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaAnalysisEditDataEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaAnalysisEditData;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaAnalysisEditDataServer")
public class TtaAnalysisEditDataServer extends BaseCommonServer<TtaAnalysisEditDataEntity_HI> implements ITtaAnalysisEditData{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAnalysisEditDataServer.class);

	@Autowired
	private ViewObject<TtaAnalysisEditDataEntity_HI> ttaAnalysisEditDataDAO_HI;

    @Autowired
    private TtaAnalysisEditDataDAO_HI_RO ttaAnalysisEditDataDAO_HI_RO;

	public TtaAnalysisEditDataServer() {
		super();
	}

    @Override
    public List<TtaAnalysisEditDataEntity_HI> findByProposalID(String proposalId) {
        String sql = "from TtaAnalysisEditDataEntity_HI l where " +
                "l.proposalid = "+proposalId ;
        List<TtaAnalysisEditDataEntity_HI> findList = ttaAnalysisEditDataDAO_HI.findList(sql);
        return findList;
    }

    /**
     *
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @Override
    public Pagination<TtaAnalysisEditDataEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
        StringBuffer sql = new StringBuffer(TtaAnalysisEditDataEntity_HI_RO.QUERY);
        Map<String,Object> map = new HashMap<String,Object>();
        SaafToolUtils.parperHbmParam(TtaAnalysisEditDataEntity_HI_RO.class, queryParamJSON, "s.TIMES_VERSION", "timesVersion", sql, map, "fulllike");
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " id desc", false);
        Pagination<TtaAnalysisEditDataEntity_HI_RO> resultList =ttaAnalysisEditDataDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
        return resultList;
    }

    @Override
    public TtaAnalysisEditDataEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        TtaAnalysisEditDataEntity_HI instance = SaafToolUtils.setEntity(TtaAnalysisEditDataEntity_HI.class, paramsJSON, ttaAnalysisEditDataDAO_HI, userId);

        ttaAnalysisEditDataDAO_HI.saveOrUpdate(instance);
        return instance;
    }

}
