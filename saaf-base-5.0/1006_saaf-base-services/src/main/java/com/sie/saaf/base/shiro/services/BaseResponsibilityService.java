package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRespRoleProfile_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseResponsibilityUserEntity_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseResponsibility;
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

import java.util.List;

/**
 * @author ZhangJun
 * @creteTime 2017-12-18
 */
@RestController
@RequestMapping("/baseResponsibilityService")
public class BaseResponsibilityService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(BaseResponsibilityService.class);
    @Autowired
    private IBaseResponsibility baseResponsibilityServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return baseResponsibilityServer;
    }

    /**
     * 查找数据
     *
     * @param params
     * @param pageIndex
     * @param pageRows
     * @return
     * @author ZhangJun
     * @creteTime 2017/12/18
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject parseJson = parseObject(params);
            parseJson = SaafToolUtils.cleanNull(parseJson, "systemCode", "responsibilityName", "isEfficacious");
            Pagination<BaseResponsibilityEntity_HI> findList = this.baseResponsibilityServer.findPagination(parseJson, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查找数据
     *
     * @param params
     * @param pageIndex
     * @param pageRows
     * @return
     * @author ZhangJun
     * @creteTime 2017/12/18
     */
    @RequestMapping(method = RequestMethod.POST, value = "findCurrentResponsibilityPagination")
    public String findCurrentResponsibilityPagination(@RequestParam(required = false) String params,
                                                      @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                                      @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject parseJson = parseObject(params);
            Pagination<BaseResponsibilityUserEntity_HI_RO> findList = this.baseResponsibilityServer.findCurrentResponsibilityPagination(parseJson, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 保存或更新数据
     *
     * @param params 保存的数据参数<br>
     *               {<br>
     *               responsibilityId:职责Id,<br>
     *               responsibilityCode:职责编号,<br>
     *               responsibilityName:职责名称,<br>
     *               responsibilityDesc:职责描述,<br>
     *               startDateActive:生效时间,<br>
     *               endDateActive:失效时间,<br>
     *               versionNum:版本号,<br>
     *               roleIds:[1,2,3,4]角色Id,<br>
     *               profiles:[{<br>
     *               profileValueId:profile行表Id,<br>
     *               profileId:profile主键,<br>
     *               profileValue:profile值<br>
     *               versionNum:版本号<br>
     *               }]<br>
     *               }
     * @return
     * @author ZhangJun
     * @creteTime 2017/12/18
     */
    @RequestMapping(method = RequestMethod.POST, value = "save")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            baseResponsibilityServer.checkOut(queryParamJSON);
            BaseRespRoleProfile_HI_RO entity = this.baseResponsibilityServer.saveRespRoleProfile(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, new JSONArray().fluentAdd(entity)).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 删除数据
     *
     * @param params 参数id
     *               {
     *               id:需要删除的数据Id，如果需要删除多个，则用;分隔
     *               }
     * @return
     * @author ZhangJun
     * @creteTime 2017/12/18
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        return super.delete(params);
    }

    /**
     * 根据角色Id查询分配的职责
     *
     * @param params JSON查询参数 {roleId:角色Id}
     * @return 职责与角色关系列表<br>
     * [{<br>
     * responsibilityId:职责标识,<br>
     * responsibilityCode:职责编号,<br>
     * responsibilityName:职责名称,<br>
     * responsibilityDesc:职责描述,<br>
     * startDateActive:生效时间,<br>
     * endDateActive:失效时间,<br>
     * creationDate:创建日期,<br>
     * createdBy:创建人,<br>
     * lastUpdatedBy:更新人,<br>
     * lastUpdateDate:更新日期,<br>
     * versionNum:版本号,<br>
     * roleId:角色Id,<br>
     * roleName:角色名称,<br>
     * roleCode:角色编号,<br>
     * roleDesc:角色描述,<br>
     * systemCode:系统编码,<br>
     * roleStartDateActive:生效时间,<br>
     * roleEndDateActive:失效时间,<br>
     * roleVersionNum:版本号,<br>
     * }]
     * @author ZhangJun
     * @creteTime 2017/12/13
     */
    @RequestMapping(method = RequestMethod.POST, value = "findResponsibilityByRoleId")
    public String findResponsibilityByRoleId(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Integer roleId = queryParamJSON.getInteger("roleId");
            List findList = this.baseResponsibilityServer.findResponsibilityByRoleId(roleId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据用户Id查询职责
     *
     * @param params JSON查询参数{userId:用户Id}
     * @return 用户与职责关系列表<br>
     * [{<br>
     * responsibilityId:职责标识<br>
     * responsibilityCode:职责编号<br>
     * responsibilityName:职责名称<br>
     * responsibilityDesc:职责描述<br>
     * startDateActive:生效时间<br>
     * endDateActive:失效时间<br>
     * creationDate:创建日期<br>
     * createdBy:创建人<br>
     * lastUpdatedBy:更新人<br>
     * lastUpdateDate:更新日期<br>
     * versionNum:版本号<br>
     * }]
     * @author ZhangJun
     * @creteTime 2017/12/13
     */
    @RequestMapping(method = RequestMethod.POST, value = "findResponsibilityByUserId")
    public String findResponsibilityByUserId(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Integer userId = queryParamJSON.getInteger("userId");
            List findList = this.baseResponsibilityServer.findResponsibilityByUserId(userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据Id查询职责
     *
     * @author ZhangJun
     * @createTime 2018/1/12 15:40
     * @description 根据Id查询职责
     */
    @RequestMapping(method = RequestMethod.POST, value = "findById")
    @Override
    public String findById(String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Integer id = queryParamJSON.getInteger("id");
            JSONObject findEntity = this.baseResponsibilityServer.findRespRoleProfileById(id);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, new JSONArray().fluentAdd(findEntity)).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}
