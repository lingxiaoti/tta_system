package com.sie.saaf.message.pages.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.message.common.DateFomatUtil;
import com.sie.saaf.message.model.entities.readonly.MsgLogEntity_HI_RO;
import com.sie.saaf.message.model.inter.server.MsgLogServer;
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
 * @createTime 2018-06-27 19:06
 * @description
 */
@RestController
@RequestMapping("/msgLogService")
public class MsgLogService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(MsgLogService.class);

    @Autowired
    private MsgLogServer msgLogServer;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgLogService.class);

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return this.msgLogServer;
    }


    /**
     * @param params    {
     *                  log_id 主键
     *                  org_id 机构id
     *                  request_param 请求参数
     *                  return_data 返回值参数
     *                  job_id 任务no
     *                  user_name 请求用户
     *                  request_id 请求id
     *                  <p>
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 消息日志分页查询
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "-1") String pageIndex,
                       @RequestParam(required = false, defaultValue = "-1") String pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();

            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            DateFomatUtil.dateFormat(jsonObject,"creationDateEnd");
            Pagination<MsgLogEntity_HI_RO> pagination = msgLogServer.findMsgLogList(jsonObject, Integer.valueOf(Objects.toString(pageIndex, "-1")), Integer.valueOf(Objects.toString(pageRows, "-1")));
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params {
     *               log_id 主键
     *               org_id 机构id
     *               request_param 请求参数
     *               return_data 返回值参数
     *               job_id 任务no
     *               user_name 请求用户
     *               request_id 请求id
     *               <p>
     *               }
     * @return
     * @description 消息日志新增修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        JSONObject queryParamJSON = JSONObject.parseObject(params);
        String msgSourceId = queryParamJSON.getString("msg_cfg_id");
        Integer userId = getSessionUserId();
        queryParamJSON.put("operatorUserId",userId.toString());
        params=queryParamJSON.toJSONString();
        return super.saveOrUpdate(params);
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
            String result = msgLogServer.deleteMsgLog(queryParamJSON, userId);
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
