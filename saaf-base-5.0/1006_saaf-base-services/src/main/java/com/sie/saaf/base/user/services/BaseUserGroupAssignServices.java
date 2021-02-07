package com.sie.saaf.base.user.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserGroupAssignEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseUserGroupAssign;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@RestController
@RequestMapping("/baseUserGroupAssignServices")
public class BaseUserGroupAssignServices extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(BaseUserGroupAssignServices.class);

    @Autowired
    private IBaseUsers baseUsersServer;

    @Autowired
    private IBaseUserGroupAssign baseUserGroupAssignServer;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.baseUserGroupAssignServer;
    }



    /**
     * 指定用户查询关联群组
     *
     * @param params    {<br>
     * @author wujigao
     * @createTime 2020/03/20
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUserGroupAssignsForUser")
    public String findUserGroupAssignsForUser(
            @RequestParam(required = false) String params,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"userId");
            // queryParamJSON.put("userType", "20");//只查询内部员工
            Pagination<BaseUserGroupAssignEntity_HI_RO> findList = this.baseUserGroupAssignServer
                    .findUserGroupAssignsForUser(queryParamJSON,pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON
                    .toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询未分配给指定用户的用户群组
     *
     * @param params    {<br>
     * @author wujigao
     * @createTime 2020/03/20
     */
    @RequestMapping(method = RequestMethod.POST, value = "findNoAssignsForUser")
    public String findNoAssignsForUser(
            @RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"userId");
            List<BaseLookupValuesEntity_HI_RO> findList = this.baseUserGroupAssignServer
                    .findNoAssignsForUser(queryParamJSON);
            JSONObject results = new JSONObject();
            results.put("data",findList);
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }
    /**
     * 指定用户查询关联群组
     *
     * @param params    {<br>
     * @author wujigao
     * @createTime 2020/03/20
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUserGroupAssignsForGroup")
    public String findUserGroupAssignsForGroup(
            @RequestParam(required = false) String params,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"userGroupCode");
            // queryParamJSON.put("userType", "20");//只查询内部员工
            Pagination<BaseUserGroupAssignEntity_HI_RO> findList = this.baseUserGroupAssignServer
                    .findUserGroupAssignsForGroup(queryParamJSON,pageIndex, pageRows);
            String findListStr = findList.toString();

            JSONObject results = JSONObject.parseObject(JSON
                    .toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findUserGroupAssignsForGroup2")
    public String findUserGroupAssignsForGroup2(
            @RequestParam(required = false) String params,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"userGroupCode");
            // queryParamJSON.put("userType", "20");//只查询内部员工
            Pagination<BaseUserGroupAssignEntity_HI_RO> findList = this.baseUserGroupAssignServer
                    .findUserGroupAssignsForGroup2(queryParamJSON,pageIndex, pageRows);
            String findListStr = findList.toString();

            JSONObject results = JSONObject.parseObject(JSON
                    .toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 指定用户新增一个或多个群组
     *
     * @param params    {<br>
     * @author wujigao
     * @createTime 2020/03/20
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveUserGroupAssignsForUser")
    public String saveUserGroupAssignsForUser(
            @RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"userId");
            String result = this.baseUserGroupAssignServer
                    .saveUserGroupAssignsForUser(queryParamJSON);
            JSONObject results = new JSONObject();
            results.put("data",result);
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 指定用户删除一个或多个群组
     *
     * @param params    {<br>
     * @author wujigao
     * @createTime 2020/03/20
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteUserGroupAssignsForUser")
    public String deleteUserGroupAssignsForUser(
            @RequestParam(required = false) String params) {
        try {
//            JSONObject queryParamJSON = parseObject(params);
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"ids");
            String result = this.baseUserGroupAssignServer
                    .deleteUserGroupAssignsForUser(queryParamJSON);
            JSONObject results = new JSONObject();
            results.put("data",result);
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 指定群组关联一个或多个用户
     * @param params    {<br>
     * @author wujigao
     * @createTime 2020/03/20
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveUserGroupAssignsForGroup")
    public String saveUserGroupAssignsForGroup(
            @RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"userGroupCode","users");
            String result = this.baseUserGroupAssignServer
                    .saveUserGroupAssignsForGroup(queryParamJSON);
            JSONObject results = new JSONObject();
            results.put("data",result);
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }
    /**
     * 指定群组删除一个或多个用户
     *
     * @param params    {<br>
     * @author wujigao
     * @createTime 2020/03/20
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteUserGroupAssignsForGroup")
    public String deleteUserGroupAssignsForGroup(
            @RequestParam(required = false) String params) {
        try {
//            JSONObject queryParamJSON = JSONObject.parseObject(params);
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON,"ids");
            String result = this.baseUserGroupAssignServer
                    .deleteUserGroupAssignsForGroup(queryParamJSON);
            JSONObject results = new JSONObject();
            results.put("data",result);
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }

}
