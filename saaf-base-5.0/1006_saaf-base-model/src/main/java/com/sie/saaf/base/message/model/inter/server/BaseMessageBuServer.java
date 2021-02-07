package com.sie.saaf.base.message.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageBuEntity_HI;
import com.sie.saaf.base.message.model.entities.BaseMessageContentEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessageBuEntity_HI_RO;
import com.sie.saaf.base.message.model.inter.IBaseMessageBu;
import com.sie.saaf.base.message.model.inter.IBaseMessageContent;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseMessageBuServer")
public class BaseMessageBuServer extends BaseCommonServer<BaseMessageBuEntity_HI> implements IBaseMessageBu {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMessageBuServer.class);
    @Autowired
    private ViewObject<BaseMessageBuEntity_HI> baseMessageBuDAO_HI;
    @Autowired
    private BaseViewObject<BaseMessageBuEntity_HI_RO> baseMessageBuDAO_HI_RO;
    @Autowired
    private IBaseMessageContent baseMessageContentServer;

    public BaseMessageBuServer() {
        super();
    }

    /**
     * 新增&修改站内消息和接收对象BU组合信息
     *
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject saveOrUpdateMessContentAndBu(JSONObject paramsJSON, int userId) throws Exception {
        JSONObject resultJSON = new JSONObject();
        BaseMessageContentEntity_HI messageContentEntity = baseMessageContentServer.saveOrUpdateMessContent(paramsJSON, userId);
        resultJSON.put("headInfo", messageContentEntity);

        JSONArray messageBuData = paramsJSON.getJSONArray("messageBuData");
        List<BaseMessageBuEntity_HI> messageBuList = new ArrayList<>();
        for (int i = 0; i < messageBuData.size(); i++) {
            JSONObject messageBuParamsJSON = messageBuData.getJSONObject(i);
            messageBuParamsJSON.put("conMessId", messageContentEntity.getConMessId());
            BaseMessageBuEntity_HI messageBuEntity = saveOrUpdateMessageBu(messageBuParamsJSON, userId);
            messageBuList.add(messageBuEntity);
        }
        resultJSON.put("lineInfo", messageBuList);
        return resultJSON;
    }

    /**
     * 新增&修改接收对象BU组合信息
     *
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public BaseMessageBuEntity_HI saveOrUpdateMessageBu(JSONObject paramsJSON, int userId) throws Exception {
        SaafToolUtils.validateJsonParms(paramsJSON, "conMessId", "buId", "userType");
        BaseMessageBuEntity_HI messageBuEntity = SaafToolUtils.setEntity(BaseMessageBuEntity_HI.class, paramsJSON, baseMessageBuDAO_HI, userId);
        if (StringUtils.isBlank(paramsJSON.getString("buMessId"))) {
            messageBuEntity.setDeleteFlag(0);
        }
        baseMessageBuDAO_HI.saveOrUpdate(messageBuEntity);
        return messageBuEntity;
    }

    /**
     * 查询接收对象信息
     * @param conMessId 消息ID
     * @return 接收对象信息列表
     */
    @Override
    public List<BaseMessageBuEntity_HI_RO> findBuInfoList(Integer conMessId) {
        StringBuffer querySQL = new StringBuffer(BaseMessageBuEntity_HI_RO.QUERY_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("conMessId", conMessId);
        return baseMessageBuDAO_HI_RO.findList(querySQL, paramsMap);
    }
}
