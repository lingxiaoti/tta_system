package com.sie.saaf.message.pages.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.model.entities.readonly.MsgTempleCfgEntity_HI_RO;
import com.sie.saaf.message.model.inter.server.MsgTempleCfgServer;
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

import java.time.Year;
import java.util.Objects;

/**
 * @author Chenzg
 * @createTime 2018-06-27 14:05
 * @description
 */
@RestController
@RequestMapping("/msgTempleCfgService")
public class MsgTempleCfgService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(MsgTempleCfgService.class);

    @Autowired
    private MsgTempleCfgServer msgTempleCfgServer;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgTempleCfgService.class);

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return this.msgTempleCfgServer;
    }


    /**
     * @param params    {
     *                  temple_id 主键
     *                  temple_name 模版名称
     *                  temple_content 模版内容
     *                  is_html 是否是html
     *                  msg_type 消息类型
     *                  org_id 所属机构
     *                  channel 渠道
     *                  business 所属业务
     *                  <p>
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 消息模版分页查询
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "-1") String pageIndex,
                       @RequestParam(required = false, defaultValue = "-1") String pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();

            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            Pagination<MsgTempleCfgEntity_HI_RO> pagination = msgTempleCfgServer.findTempleConfigInfo(jsonObject, Integer.valueOf(Objects.toString(pageIndex, "-1")), Integer.valueOf(Objects.toString(pageRows, "-1")));
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params {
     *               temple_id 主键
     *               temple_name 模版名称
     *               temple_content 模版内容
     *               is_html 是否是html
     *               msg_type 消息类型
     *               org_id 所属机构
     *               channel 渠道
     *               business 所属业务
     *               <p>
     *               }
     * @return
     * @description 消息模版新增修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "orgId", "templeName", "msgType", "channelName", "templeContent");
            String msgSourceId = queryParamJSON.getString("temple_id");
            Integer userId = getSessionUserId();
            queryParamJSON.put("operatorUserId", userId.toString());
            params = queryParamJSON.toJSONString();
            Object entity = msgTempleCfgServer.saveOrUpdateMsgTemple(queryParamJSON);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, (new JSONArray()).fluentAdd(entity)).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
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
            String result = msgTempleCfgServer.deleteMsgTempleCfg(queryParamJSON, userId);
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
