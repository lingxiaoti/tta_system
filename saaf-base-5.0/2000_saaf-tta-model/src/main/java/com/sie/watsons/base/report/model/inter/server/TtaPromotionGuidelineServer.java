package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaPromotionGuidelineEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaPromotionGuidelineEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.TtaPromotionGuideline;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/11/011.
 */
@Component("ttaPromotionGuidelineServer")
public class TtaPromotionGuidelineServer extends BaseCommonServer<TtaPromotionGuidelineEntity_HI> implements TtaPromotionGuideline {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaPromotionGuidelineServer.class);

    @Autowired
    private ViewObject<TtaPromotionGuidelineEntity_HI> TtaPromotionGuidelineDAO_HI;
    @Autowired
    private BaseViewObject<TtaPromotionGuidelineEntity_HI_RO> TtaPromotionGuidelineDAO_HI_RO;

    public TtaPromotionGuidelineServer() {
        super();
    }



    @Override
    public Pagination<TtaPromotionGuidelineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaPromotionGuidelineEntity_HI_RO.TTA_ITEM);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
      //  SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " osd_id desc", false);
        Pagination<TtaPromotionGuidelineEntity_HI_RO> findList = TtaPromotionGuidelineDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public TtaPromotionGuidelineEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        JSONArray objList = paramsJSON.getJSONArray("data");
        JSONObject obj = objList.getJSONObject(0);
        TtaPromotionGuidelineEntity_HI instance = SaafToolUtils.setEntity(TtaPromotionGuidelineEntity_HI.class, obj, TtaPromotionGuidelineDAO_HI, userId);
        JSONObject json = paramsJSON.getJSONObject("info");
        String promPeriodFrom=json.getString("promPeriodFrom");
        String promPeriodTo=json.getString("promPeriodTo");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        instance.setPromNumber(json.getString("promNumber"));
        instance.setPromPeriodFrom(sdf.parse(promPeriodFrom));
        instance.setPromPeriodTo(sdf.parse(promPeriodTo));
        TtaPromotionGuidelineDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public void delete(Integer pkId) {
        if (pkId == null) {
            return;
        }
        TtaPromotionGuidelineEntity_HI instance = TtaPromotionGuidelineDAO_HI.getById(pkId);
        if (instance == null) {
            return;
        }
        TtaPromotionGuidelineDAO_HI.delete(instance);
    }


}
