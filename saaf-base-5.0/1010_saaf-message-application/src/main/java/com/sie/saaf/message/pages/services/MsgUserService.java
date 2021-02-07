package com.sie.saaf.message.pages.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.model.entities.readonly.MsgUserEntity_HI_RO;
import com.sie.saaf.message.model.inter.server.MsgUserServer;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author Chenzg
 * @createTime 2018-06-27 18:05
 * @description
 */
@RestController
@RequestMapping("/msgUserService")
public class MsgUserService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(MsgUserService.class);

    @Autowired
    private MsgUserServer msgUserServer;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgUserService.class);

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return this.msgUserServer;
    }


    /**
     * @param params    {
     *                  msg_user_id 消息服务用户id
     *                  msg_instance_id 消息实例id
     *                  org_id 事业部
     *                  msg_user_name 用户名
     *                  msg_user_pwd 密码
     *                  remark 备注
     *                  enabled_flag 启用状态
     *                  <p>
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 用户分页查询
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "-1") String pageIndex,
                       @RequestParam(required = false, defaultValue = "-1") String pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();

            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);

            Pagination<MsgUserEntity_HI_RO> pagination = msgUserServer.findMsgUserList(jsonObject, Integer.valueOf(Objects.toString(pageIndex, "-1")), Integer.valueOf(Objects.toString(pageRows, "-1")));
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**新增和修改用户数据
     * @param params {
     *               msg_user_id 消息服务用户id
     *               msg_instance_id 消息实例id
     *               org_id 事业部
     *               msg_user_name 用户名
     *               msg_user_pwd 密码
     *               remark 备注
     *               enabled_flag 启用状态
     *               <p>
     *               }
     *  params: {"msgUserId":"","enabledFlag":"0","noLog":"1","isDelete":"0","orgId":"261","msgUserName":"111","msgUserPwd":"111","remark":"111"}
     * @return
     * @description 用户新增修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {

        try {
            Integer userId = getSessionUserId();
            JSONObject queryParamJSON = parseObject(params);
            queryParamJSON.put("operatorUserId",userId);
            SaafToolUtils.validateJsonParms(queryParamJSON,"orgId","msgUserName","msgUserPwd");
            Object entity = msgUserServer.saveMsgUserInfo(queryParamJSON);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, (new JSONArray()).fluentAdd(entity)).toString();
        }  catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }

    }

    /**
     * 删除数据
     *
     * @param params 参数id
     *               {
     *               id:需要删除的数据Id，如果需要删除多个，则用;分隔
     *               }
     * @return {
     * status:操作是否成功,E:失败，S:成功
     * msg:成功或者失败后消息
     * count:成功的记录数
     * data:成功的数据
     * }
     * @author chenzg
     * @creteTime 2018/6/27
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    @Override
    public String delete(@RequestParam(required = false) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"idDetails");
            String result = msgUserServer.deleteMsgUser(queryParamJSON, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
        }  catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }
}
