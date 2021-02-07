package com.sie.saaf.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupValues;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.common.DesUtil;
import com.sie.saaf.message.model.dao.readonly.MsgUserDAO_HI_RO;
import com.sie.saaf.message.model.entities.MsgUserEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgUserEntity_HI_RO;
import com.sie.saaf.message.model.inter.IMsgUser;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("msgUserServer")
public class MsgUserServer extends BaseCommonServer<MsgUserEntity_HI> implements IMsgUser {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgUserServer.class);
    @Autowired
    private ViewObject<MsgUserEntity_HI> msgUserDAO_HI;

    @Autowired
    private IBaseLookupValues baseLookupValuesServer;

    @Autowired
    private MsgUserDAO_HI_RO msgUserDAO_HI_RO;

    public MsgUserServer() {
        super();
    }

    public List<MsgUserEntity_HI> findMsgUserInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<MsgUserEntity_HI> findListResult = msgUserDAO_HI.findList("from MsgUserEntity_HI", queryParamMap);
        return findListResult;
    }

    public Object saveMsgUserInfo(JSONObject queryParamJSON) {
        //判断用户名是否存在,如果存在不允许新增
        StringBuffer sql = new StringBuffer();
        sql.append(MsgUserEntity_HI_RO.QUERY_USERNAME_EXIST_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "mu.msg_user_name", "msgUserName", sql, paramsMap, "=");

        String msgUserId = queryParamJSON.getString("msgUserId");
        if (!StringUtils.isEmpty(msgUserId)) {
            SaafToolUtils.parperParam(queryParamJSON, "mu.msg_user_id", "msgUserId", sql, paramsMap, "!=");
        }

        List<MsgUserEntity_HI_RO> findList = msgUserDAO_HI_RO.findList(sql, paramsMap);
        if (findList != null && findList.size() > 0) {
            throw new IllegalArgumentException("用户名重复!请修改用户名!");
        }
        //对加密密钥进行查询解密
        String desUtilKey = getDesKey();

        //新增用户,直接加密用户密码信息
        if (StringUtils.isEmpty(msgUserId)) {

            String msgUserPwd = queryParamJSON.getString("msgUserPwd");
            try {
                msgUserPwd = DesUtil.AESEncode(desUtilKey,msgUserPwd);
            } catch (Exception ex) {
                throw new IllegalArgumentException("密码加密错误");
            }

            queryParamJSON.put("msgUserPwd", msgUserPwd);
            if (queryParamJSON.getString("isDelete") == null) {
                queryParamJSON.put("isDelete", "0");
            }
            if (queryParamJSON.getString("noLog") == null) {
                queryParamJSON.put("noLog", "1");
            }
            if (queryParamJSON.getString("enabledFlag") == null) {
                queryParamJSON.put("enabledFlag", "0");
            }

            MsgUserEntity_HI msgUserEntity_HI = JSON.parseObject(queryParamJSON.toString(), MsgUserEntity_HI.class);
            return msgUserDAO_HI.save(msgUserEntity_HI);
        } else {

            MsgUserEntity_HI msgUserEntity_HI_OLD = msgUserDAO_HI.getById(Integer.parseInt(msgUserId));

            if (msgUserEntity_HI_OLD != null) {

                String msgUserPwd = queryParamJSON.getString("msgUserPwd");

                //如果加密的内容不等,则重新加密内容,否则不更新密码信息
                if (!msgUserEntity_HI_OLD.getMsgUserPwd().equals(msgUserPwd)) {
                    try {
                        msgUserPwd = DesUtil.AESEncode(desUtilKey,msgUserPwd);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("密码加密错误");
                    }
                    msgUserEntity_HI_OLD.setMsgUserPwd(msgUserPwd);

                }

                msgUserEntity_HI_OLD.setEnabledFlag(queryParamJSON.getString("enabledFlag"));
                msgUserEntity_HI_OLD.setOrgId(queryParamJSON.getString("orgId"));
                msgUserEntity_HI_OLD.setNoLog(queryParamJSON.getString("noLog"));
                msgUserEntity_HI_OLD.setMsgUserName(queryParamJSON.getString("msgUserName"));
                msgUserEntity_HI_OLD.setRemark(queryParamJSON.getString("remark"));
                msgUserEntity_HI_OLD.setOperatorUserId(queryParamJSON.getInteger("operatorUserId"));
                msgUserDAO_HI.update(msgUserEntity_HI_OLD);
                return msgUserEntity_HI_OLD;
            } else {
                throw new IllegalArgumentException("用户修改失败!当前用户ID不存在!");
            }


        }


    }

    @Override
    public String getDesKey() {
        JSONObject obj = new JSONObject();
        obj.put("lookupType","ENCRYPTION_KEY");
        obj.put("lookupCode","desUtilKey");
        List<BaseLookupValuesEntity_HI_RO> dic = baseLookupValuesServer.findCacheDic(obj);
        String desUtilKey = null;
        if((null != dic) && dic.size() == 1){
            desUtilKey = dic.get(0).getDescription();
            if(null!=desUtilKey){
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    desUtilKey = new String(decoder.decodeBuffer(desUtilKey));
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(),e);
                }
            }

        }
        return desUtilKey;
    }

    @Override
    public Pagination<MsgUserEntity_HI_RO> findMsgUserList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(MsgUserEntity_HI_RO.QUERY_SELECT_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "mu.org_id", "orgId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "mu.msg_user_name", "msgUserName", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "mu.param", "param", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "mu.remark", "remark", sql, paramsMap, "fulllike");
        sql.append("  order by mu.creation_date desc");
        Pagination<MsgUserEntity_HI_RO> findList = msgUserDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public String deleteMsgUser(JSONObject queryParamJSON, int userId) {
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
                MsgUserEntity_HI entity = msgUserDAO_HI.getById(Integer.valueOf(id));
                if (entity != null) {
                    entity.setIsDelete(CommonConstants.DELETE_TRUE);
                    entity.setOperatorUserId(userId);
                    msgUserDAO_HI.update(entity);
                }
            }
        }
        jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
        return jsonResult.toString();
    }
}
