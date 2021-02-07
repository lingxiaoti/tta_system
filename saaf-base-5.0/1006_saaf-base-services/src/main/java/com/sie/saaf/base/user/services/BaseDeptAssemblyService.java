package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseDept_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BasePersonAss_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionAss_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseDeptAssembly;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
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

@RestController
@RequestMapping("/baseDeptAssemblyService")
public class BaseDeptAssemblyService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(BaseDeptAssemblyService.class);
    @Autowired
    private IBaseDeptAssembly baseDeptAssemblyServer;
    /**
     * 查询部门信息
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDeptInfo", produces = "application/json")
    public String findDeptInfo(@RequestParam(required = false) String params,
                               @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                               @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try{
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject,"orgId");
            Pagination<BaseDept_HI_RO> findList = baseDeptAssemblyServer.findDeptList(jsonObject,pageIndex,pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询人员控件信息
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPersonInfo", produces = "application/json")
    public String findPersonInfo(@RequestParam(required = false) String params,
                               @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                               @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try{
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject,"orgId");
            Pagination<BasePersonAss_HI_RO> findList = baseDeptAssemblyServer.findPersonAssList(jsonObject,pageIndex,pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 查询职位控件信息
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPositionByPerson", produces = "application/json")
    public String findPositionByPerson(@RequestParam(required = false) String params,
                                 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try{
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject,"orgId");
            SaafToolUtils.validateJsonParms(jsonObject,"personId");
            Pagination<BasePositionAss_HI_RO> findList = baseDeptAssemblyServer.findPersonAssListByPerson(jsonObject,pageIndex,pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
}
