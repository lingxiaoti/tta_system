package com.sie.saaf.base.message.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageContentEntity_HI;
import com.sie.saaf.base.message.model.entities.BaseMessageDepartmentEntity_HI;
import com.sie.saaf.base.message.model.entities.BaseMessagePersonEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessageContentEntity_HI_RO;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessagePersonEntity_HI_RO;
import com.sie.saaf.base.message.model.inter.IBaseMessageBu;
import com.sie.saaf.base.message.model.inter.IBaseMessageDepartment;
import com.sie.saaf.base.message.model.inter.server.BaseMessageContentServer;
import com.sie.saaf.base.message.model.inter.server.BaseMessageInstationServer;
import com.sie.saaf.base.message.model.inter.server.BaseMessagePersonServer;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseOrgStructureEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
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
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.List;

/**
 * @auther: huqitao 2018/7/7
 */
@RestController
@RequestMapping("/baseMessageService")
public class BaseMessageService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(BaseMessageService.class);
    @Autowired
    private BaseMessageContentServer baseMessageContentServer;
    @Autowired
    private IBaseMessageBu baseMessageBuServer;
    @Autowired
    private IBaseMessageDepartment baseMessageDepartmentServer;
    @Autowired
    private BaseMessagePersonServer baseMessagePersonServer;
    @Autowired
    private BaseMessageInstationServer baseMessageInstationServer;
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return null;
    }

    /**
     * 1、查询站内消息列表
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findBaseMessageContent")
    public String findBaseMessageContent(@RequestParam(required = false) String params,
                                         @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Pagination<BaseMessageContentEntity_HI_RO> pagination = baseMessageContentServer.findBaseConMessPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toJSONString();
        } catch (Exception e) {
            logger.error("查询站内消息列表:{}", e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 根据ID查询站内消息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findBaseMessageContentById")
    public String findBaseMessageContentById(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"conMessId");
            Pagination<BaseMessageContentEntity_HI_RO> pagination = baseMessageContentServer.findBaseConMessPagination(queryParamJSON, 1, 10);
            if(pagination != null && pagination.getData() != null && pagination.getData().size() > 0){
                BaseMessageContentEntity_HI_RO messageContentInfo = pagination.getData().get(0);
                List<BaseMessageContentEntity_HI_RO> list = baseMessageContentServer.findIsOrNotConsulted(queryParamJSON.getInteger("conMessId"));
                if (list.size() == 1) {
                    if(list.get(0).getMessStatus() == 0){
                        messageContentInfo.setNotConsulted(list.get(0).getMessStatusNum());
                        messageContentInfo.setBeenConsulted(0);
                    }
                    if(list.get(0).getMessStatus() == 1){
                        messageContentInfo.setNotConsulted(0);
                        messageContentInfo.setBeenConsulted(list.get(0).getMessStatusNum());
                    }
                } else {
                    for(int i = 0; i < list.size(); i++){
                        if(list.get(i).getMessStatus() == 0){
                            messageContentInfo.setNotConsulted(list.get(i).getMessStatusNum());
                        }
                        if(list.get(i).getMessStatus() == 1){
                            messageContentInfo.setBeenConsulted(list.get(i).getMessStatusNum());
                        }
                    }
                }
                messageContentInfo.setMessageBuData(baseMessageBuServer.findBuInfoList(queryParamJSON.getInteger("conMessId")));
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "查询成功", 1, messageContentInfo).toString();
            }
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未查询到数据", 0, null).toString();
        } catch (Exception e) {
            logger.error("根据ID查询站内消息异常:{}-{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 查看发送对象：查询包含部门信息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findContainDeptInfo")
    public String findContainDeptInfo(@RequestParam(required = false) String params,
                                      @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                      @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramJSON,"conMessId");

            JSONObject queryParamJSON = new JSONObject();
            queryParamJSON.put("conMessId", paramJSON.getInteger("conMessId"));
            queryParamJSON.put("deleteFlag", 0);
            Pagination<BaseMessageDepartmentEntity_HI> pagination = baseMessageDepartmentServer.findPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toJSONString();
        } catch (Exception e) {
            logger.error("查询包含部门信息异常:{}-{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 查看发送对象：查询包含接收人员信息
     * @param params
     * {
     *     conMessId：消息ID，
     *     depMessId：包含部门记录ID，
     *     deptId：部门ID
     * }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findContainUserInfo")
    public String findContainUserInfo(@RequestParam(required = false) String params,
                                      @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                      @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"conMessId", "depMessId", "deptId");
            Pagination<BaseMessagePersonEntity_HI_RO> pagination = baseMessagePersonServer.findReceiverPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toJSONString();
        } catch (Exception e) {
            logger.error("查询包含接收人员信息异常:{}-{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 查询站内消息待发送对象
     * @param params
     * {
     *     buMessId：接收对象组合ID
     *     userType : 用户类型
     * }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findMessagePerson")
    public String findMessagePerson(@RequestParam(required = false) String params,
                                      @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                      @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"buMessId", "userType");

            String userType = queryParamJSON.getString("userType");
            if (!"20".equals(userType) && !"30".equals(userType) && !"40".equals(userType)) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "无效的userType：" + userType, 0, null).toString();
            }

            Pagination<BaseMessagePersonEntity_HI_RO> pagination = baseMessagePersonServer.findMessagePerson(queryParamJSON, pageIndex, pageRows);
            JSONObject results;
            if (pagination != null) {
                results = JSONObject.parseObject(JSON.toJSONString(pagination));
            } else {
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, null).toString();
            }

            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toJSONString();
        } catch (Exception e) {
            logger.error("查询站内消息待发送对象异常:{}-{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 直接添加人员、经销商、门店
     * @param params
     * {
     *      ouId：事业部ID，
     *      keyWordName：人员名称、经销商名称
     *      userType：用户类型
     * }
     * @return 人员、经销商、门店列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDirectAddPerson")
    public String findDirectAddPerson(@RequestParam(required = false) String params,
                                      @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                      @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "ouId", "userType", "keyWordName");

            String userType = queryParamJSON.getString("userType");

            if (!"20".equals(userType) && !"30".equals(userType) && !"40".equals(userType)) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "无效的userType：" + userType, 0, null).toString();
            }

            List<BaseOrgStructureEntity_HI_RO> userInfo = baseMessagePersonServer.findDirectAddPerson(queryParamJSON);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("data", userInfo);
            jsonResult.put("status", SUCCESS_STATUS);

            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 2、新增&修改站内消息和接收对象BU组合信息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateMessContentAndBu")
    public String saveOrUpdateMessContentAndBu(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            JSONObject resultJSON = baseMessageBuServer.saveOrUpdateMessContentAndBu(paramsJSON, paramsJSON.getIntValue("varUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, resultJSON).toString();
        } catch (IllegalArgumentException e) {
            logger.error("新增&修改站内消息和接收对象BU组合信息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.error("新增&修改站内消息和接收对象BU组合信息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 3、新增&修改预发送人信息
     * @param params
     * {
     *     ouId：事业部ID，
     *     userType：用户类型
     *     conMessId：消息Id，
     *     buMessId：接收对象组合,
     *     deptData：[{
     *         deptId：部门ID，
     *         deptCode：部门编码，
     *         deptName：部门名称，
     *         personData：[1,2,3,4,5] 用户ID（员工用户、经销商用户、门店用户）
     *     }]
     * }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateMessDeptAndPerson")
    public String saveOrUpdateMessDeptAndPerson(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON,"ouId", "userType", "conMessId", "buMessId", "deptData");
            baseMessageDepartmentServer.saveOrUpdateMessDeptAndPerson(paramsJSON, paramsJSON.getIntValue("varUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            logger.error("新增&修改预发送人信息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.error("新增&修改预发送人信息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 4、删除预发送人信息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteMessDeptAndPerson")
    public String deleteMessDeptAndPerson(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON,"perMessId");
            BaseMessagePersonEntity_HI baseMessagePersonEntity = baseMessagePersonServer.getById(paramsJSON.getInteger("perMessId"));
            if(baseMessagePersonEntity == null){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "不存在的数据", 0, null).toString();
            }
            Integer conMessId = baseMessagePersonEntity.getConMessId();
            if(conMessId == null || "".equals(conMessId.toString())){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "异常数据-001", 0, null).toString();
            }
            BaseMessageContentEntity_HI baseMessageContentEntity = baseMessageContentServer.getById(conMessId);
            if(baseMessageContentEntity == null){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "异常数据-002", 0, null).toString();
            }
            if(baseMessageContentEntity.getConMessState() == 1){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "消息<" + baseMessageContentEntity.getConMessTitle()+">已经发出，不允许删除相关人员", 0, null).toString();
            }
            baseMessagePersonServer.deleteById(paramsJSON.getInteger("perMessId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            logger.error("删除预发送人信息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.error("删除预发送人信息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 5、撤回站内消息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "revokeMessage")
    public String revokeMessage(@RequestParam(required = false) String params) {
        String key = "REVOKE_MESSAGE";
        boolean releaseLock = true;
        try {
            String str= jedisCluster.get(key);
            if (StringUtils.isNotBlank(str)){
                releaseLock = false;
                logger.warn("正在执行消息撤回，执行开始时间:{}", str);
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "正在执行消息撤回", 0, null).toString();
            }
            jedisCluster.setex(key,3600, SaafDateUtils.convertDateToString(new Date()));
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON,"conMessId");
            BaseMessageContentEntity_HI baseMessageContentEntity = baseMessageContentServer.getById(paramsJSON.getInteger("conMessId"));
            if(baseMessageContentEntity == null){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "不存在的数据，参数"+params, 0, null).toString();
            }
            if(baseMessageContentEntity.getConMessState() != 1){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "已发送的消息才允许撤回", 0, null).toString();
            }
            baseMessageInstationServer.saveTranRevokeMessProduct(paramsJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "撤回成功", 0, null).toString();
        } catch (IllegalArgumentException e) {
            logger.error("撤回站内消息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.error("撤回站内消息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        } finally {
            if (releaseLock) {
                jedisCluster.del(key);
            }
        }
    }

    /**
     * 7、发送内消息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "sendMessage")
    public String sendMessage(@RequestParam(required = false) String params) {
        String lockKey = "SEND_MESSAGE";
        boolean success = true;
        try {
            Long val = jedisCluster.setnx(lockKey, SaafDateUtils.convertDateToString(new Date()));
            jedisCluster.expire(lockKey,1800);
            if (val == 0){
                success = false;
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON,"conMessId");
            BaseMessageContentEntity_HI baseMessageContentEntity = baseMessageContentServer.getById(paramsJSON.getInteger("conMessId"));
            if(baseMessageContentEntity == null){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "不存在的数据，参数"+params, 0, null).toString();
            }
            if(baseMessageContentEntity.getConMessState() == 1){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "已发送的消息不允许重复发送", 0, null).toString();
            }

            baseMessageContentServer.saveTransactionProduct(paramsJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "发送成功", 0, null).toString();
        } catch (IllegalArgumentException e) {
            logger.error("撤回站内消息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.error("撤回站内消息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        } finally {
            if (success) {
                jedisCluster.del(lockKey);
            }
        }
    }
}
