package com.sie.watsons.base.questionnaire.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.util.Hash;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.questionnaire.model.dao.readonly.TtaQuestionChoiceLineDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.dao.readonly.TtaQuestionHeaderDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionChoiceLineEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionHeaderEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionChoiceLineEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionHeaderEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ITtaQuestionHeader;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("ttaQuestionHeaderServer")
public class TtaQuestionHeaderServer extends BaseCommonServer<TtaQuestionHeaderEntity_HI> implements ITtaQuestionHeader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionHeaderServer.class);

    @Autowired
    private ViewObject<TtaQuestionHeaderEntity_HI> ttaQuestionHeaderDAO_HI;

    @Autowired
    private ViewObject<TtaQuestionChoiceLineEntity_HI> ttaQuestionChoiceLineDao_HI;

    @Autowired
    private TtaQuestionHeaderDAO_HI_RO ttaQuestionHeaderDAOHiRo;

    @Autowired
    private TtaQuestionChoiceLineDAO_HI_RO ttaQuestionChoiceLineDAO_HI_RO;

    public TtaQuestionHeaderServer() {
        super();
    }

    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Override
    public TtaQuestionHeaderEntity_HI saveSaafQuestionHeader(JSONObject jsonParams) {
        TtaQuestionHeaderEntity_HI entity = JSON.parseObject(jsonParams.toJSONString(), TtaQuestionHeaderEntity_HI.class);
        ttaQuestionHeaderDAO_HI.saveEntity(entity);
        return entity;
    }

    @Override
    public Pagination<TtaQuestionHeaderEntity_HI_RO> findPaginationQuestionHeader(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(TtaQuestionHeaderEntity_HI_RO.QUERY_HEADER_SQL);
        SaafToolUtils.parperParam(queryParamJSON, "t.project_type", "projectType", sqlBuffer, paramsMap, "=");
        if (StringUtils.isNotEmpty(queryParamJSON.getString("projectCnTitle"))) {
            sqlBuffer.append(" and t.project_cn_title like '%" + queryParamJSON.getString("projectCnTitle") + "%' ");
        }
        if (StringUtils.isNotEmpty(queryParamJSON.getString("projectEnTitle"))) {
            sqlBuffer.append(" and t.project_en_title like '%%" + queryParamJSON.getString("projectEnTitle") + "' ");
        }
        SaafToolUtils.changeQuerySort(queryParamJSON, sqlBuffer, " t.serial_number ", false);
        return ttaQuestionHeaderDAOHiRo.findPagination(sqlBuffer, paramsMap, pageIndex, pageRows);
    }

    @Override
    public JSONObject findQuestionByHeaderId(Integer qHeaderId) {
        JSONObject jsonResult = new JSONObject();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(TtaQuestionHeaderEntity_HI_RO.QUERY_HEADER_SQL);
        SaafToolUtils.parperParam(new JSONObject().fluentPut("qHeaderId", qHeaderId), "t.q_header_id", "qHeaderId", sqlBuffer, paramsMap, "=");
        List<TtaQuestionHeaderEntity_HI_RO> list = ttaQuestionHeaderDAOHiRo.findList(sqlBuffer.toString(), paramsMap);
        if (list == null || list.isEmpty()) {
            return jsonResult;
        }
        TtaQuestionHeaderEntity_HI_RO headerData = list.get(0);
        sqlBuffer = new StringBuffer();
        paramsMap.clear();
        sqlBuffer.append(TtaQuestionChoiceLineEntity_HI_RO.QUERY_BY_HEADER_ID).append(" and nvl(t.parent_id,0) = 0 \n");
        SaafToolUtils.parperParam(new JSONObject().fluentPut("qHeaderId", qHeaderId), "t.q_header_id", "qHeaderId", sqlBuffer, paramsMap, "=");
        SaafToolUtils.changeQuerySort(new JSONObject(), sqlBuffer, " t.serial_number asc ", false);
        List<TtaQuestionChoiceLineEntity_HI_RO> lineList = ttaQuestionChoiceLineDAO_HI_RO.findList(sqlBuffer.toString(), paramsMap);
        jsonResult.fluentPut("headerData", headerData).fluentPut("lineList", lineList);
        return jsonResult;
    }


    @Override
    public TtaQuestionHeaderEntity_HI saveOrUpdateALL(JSONObject paramsJson) throws Exception {
        JSONObject headData = paramsJson.getJSONObject("headData");
        JSONArray lineArr = paramsJson.getJSONArray("lineArr");
        TtaQuestionHeaderEntity_HI headerEntity = SaafToolUtils.setEntity(TtaQuestionHeaderEntity_HI.class, headData, ttaQuestionHeaderDAO_HI, paramsJson.getInteger("varUserId"));
        ttaQuestionHeaderDAO_HI.saveOrUpdate(headerEntity);
        if (lineArr != null && !lineArr.isEmpty()) {
            for (int i = 0; i < lineArr.size(); i++) {
                JSONObject object = lineArr.getJSONObject(i);
               //TtaQuestionChoiceLineEntity_HI choiceEntity = SaafToolUtils.setEntity(TtaQuestionChoiceLineEntity_HI.class, object, ttaQuestionChoiceLineDao_HI, paramsJson.getInteger("varUserId"));
                TtaQuestionChoiceLineEntity_HI  choiceEntity = JSON.parseObject(object.toJSONString(), TtaQuestionChoiceLineEntity_HI.class);
                choiceEntity.setQHeaderId(headerEntity.getQHeaderId());
                choiceEntity.setOperatorUserId(paramsJson.getInteger("varUserId"));
                if (StringUtils.isBlank(choiceEntity.getChoiceEnContent())) {
                    String code = generateCodeServer.getSequenceId("Q",10);
                    choiceEntity.setChoiceEnContent(code);
                }
                ttaQuestionChoiceLineDao_HI.saveOrUpdate(choiceEntity);
            }
        }
        return headerEntity;
    }

    @Override
    public void deleteQuestionHeaderOrLine(JSONObject jsonParam) {
        Integer qHeaderId = jsonParam.getInteger("qHeaderId");
        Integer choiceLineId = jsonParam.getInteger("choiceLineId");
        if (choiceLineId != null) {
            ttaQuestionChoiceLineDao_HI.delete(choiceLineId);
        } else {
            List<TtaQuestionChoiceLineEntity_HI> lineList = ttaQuestionChoiceLineDao_HI.findByProperty("q_header_id", qHeaderId);
            if (lineList != null && !lineList.isEmpty()) {
                ttaQuestionChoiceLineDao_HI.deleteAll(lineList);
            }
            ttaQuestionHeaderDAO_HI.delete(qHeaderId);
        }
    }

}
