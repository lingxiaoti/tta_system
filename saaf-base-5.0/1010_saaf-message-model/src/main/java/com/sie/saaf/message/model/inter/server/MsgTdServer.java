package com.sie.saaf.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SimpleXml;
import com.sie.saaf.message.common.Postman;
import com.sie.saaf.message.model.dao.readonly.MsgTdDAO_HI_RO;
import com.sie.saaf.message.model.entities.MsgSourceCfgEntity_HI;
import com.sie.saaf.message.model.entities.MsgTdEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgTdEntity_HI_RO;
import com.sie.saaf.message.model.inter.IMsgTd;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component("msgTdServer")
public class MsgTdServer extends BaseCommonServer<MsgTdEntity_HI> implements IMsgTd {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgTdServer.class);
    @Autowired
    private ViewObject<MsgTdEntity_HI> msgTdDAO_HI;

    @Autowired
    private MsgTdDAO_HI_RO msgTdDAO_HI_RO;

    public MsgTdServer() {
        super();
    }

    @Autowired
    private MsgSourceCfgServer msgSourceCfgServer;

    public List<MsgTdEntity_HI> findMsgTdInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<MsgTdEntity_HI> findListResult = msgTdDAO_HI.findList("from MsgTdEntity_HI", queryParamMap);
        return findListResult;
    }

    public Object saveMsgTdInfo(JSONObject queryParamJSON) {
        MsgTdEntity_HI msgTdEntity_HI = JSON.parseObject(queryParamJSON.toString(), MsgTdEntity_HI.class);
        Object resultData = msgTdDAO_HI.save(msgTdEntity_HI);
        return resultData;
    }

    //  回复过td或者TD内容的，认为已经退订
    public boolean isBlack(String phone) {
        boolean ret = false;
        JSONObject para = new JSONObject();
        para.put("phone", phone);
        List<MsgTdEntity_HI> entityList = this.findList(para);
        if (entityList.isEmpty()) return ret;
        for (MsgTdEntity_HI td : entityList) {
            if (td.getContent().contains("td") || td.getContent().contains("TD")) {
                ret = true;
                return ret;
            }
        }
        return ret;
    }

    @Override
    public String getSmsExEx() {
        JSONObject para = new JSONObject();
        para.put("isDelete", "0");
        para.put("msgTypeCode", "SMS");//短信
        List<MsgSourceCfgEntity_HI> sourceList = msgSourceCfgServer.findList(para);
        for (MsgSourceCfgEntity_HI source : sourceList) {
            JSONObject cfg = JSONObject.parseObject(source.getParamCfg());
            String retXml = callMService(cfg.getString("ServerHost"), cfg.getString("ServerPort"), source.getSourceUser(), source.getSourcePwd());
            LOGGER.debug("短信退订返回xml内容:" + retXml);
            if (!"E".equals(retXml)) {
                dealXml(retXml, source);
            }
        }
        return null;
    }

    @Override
    public Pagination<MsgTdEntity_HI_RO> findMsgTdList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(MsgTdEntity_HI_RO.QUERY_SELECT_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "mt.org_id", "orgId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "mt.phone", "phone", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "mt.pass", "pass", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "mt.remark", "remark", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "mt.send_time", "sendTimeStart", sql, paramsMap, ">=");
        SaafToolUtils.parperParam(queryParamJSON, "mt.send_time", "sendTimeEnd", sql, paramsMap, "<=");
        sql.append(" order by mt.send_time desc");
        Pagination<MsgTdEntity_HI_RO> findList = msgTdDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;
    }


    @Override
    public String deleteMsgTd(JSONObject queryParamJSON, int userId) {
        JSONObject jsonResult = new JSONObject();
        if (queryParamJSON.get("idDetails") == null ) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定需删除的调度请求Id数组（idDetails）", 0, null);
            return jsonResult.toString();
        }
        JSONArray idDetails = queryParamJSON.getJSONArray("idDetails");
        if(idDetails!=null && !idDetails.isEmpty()){
            for(int i=0;i<idDetails.size();i++){
                JSONObject object = idDetails.getJSONObject(i);
                String id=object.getString("id");
                MsgTdEntity_HI entity = msgTdDAO_HI.getById(Integer.valueOf(id));
                if (entity != null) {
                    entity.setIsDelete(CommonConstants.DELETE_TRUE);
                    entity.setOperatorUserId(userId);
                    msgTdDAO_HI.update(entity);
                }
            }
        }
        jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
        return jsonResult.toString();
    }

    private void dealXml(String xml, MsgSourceCfgEntity_HI source) {
        SimpleXml root = SimpleXml.read(xml);
        SimpleXml arrayOfString = root.getSubElements().get(0);
        List<SimpleXml> list = arrayOfString.getSubElements();
        if (list != null && list.size() > 0) {
            for (SimpleXml x : list) {
                String text = x.getText();
                String[] textArray = text.split(",");
                String sendTime = textArray[0] + " " + textArray[1];
                String phone = textArray[2];
                String pass = textArray[3];
                String remark = textArray[4];
                String content = textArray[5];
            /*System.out.println(date);
            System.out.println(phone);
            System.out.println(pass);
            System.out.println(desc);
            System.out.println(content);*/
                MsgTdEntity_HI tdEntity = new MsgTdEntity_HI();
                tdEntity.setPhone(phone);
                tdEntity.setSendTime(SaafDateUtils.string2UtilDate(sendTime));
                tdEntity.setPass(pass);
                tdEntity.setContent(content);
                tdEntity.setRemark(remark);
                tdEntity.setIsDelete(0);
                tdEntity.setOperatorUserId(-1);
                tdEntity.setOrgId(source.getOrgId());
                tdEntity.setMsgSourceId(source.getMsgSourceId());
                this.saveOrUpdate(tdEntity);
            }
        }
    }

    private String callMService(String host, String port, String uName, String pwd) {
        try {
            String url = "http://%serverhost%:%port%/MWGate/wmgw.asmx/MongateCsGetSmsExEx";
            Map params = new HashMap<>();
            params.put("userId", uName);
            params.put("password", pwd);
            url = url.replace("%serverhost%", host).replace("%port%", port);
            return Postman.jsonPost(url, params);
        } catch (Exception e) {
            return "E";
        }
    }
}
