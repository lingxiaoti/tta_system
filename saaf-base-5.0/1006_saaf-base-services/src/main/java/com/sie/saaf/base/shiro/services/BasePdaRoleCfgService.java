package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.base.shiro.model.inter.IBasePdaRoleCfg;
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

@RestController
@RequestMapping("/basePdaRoleCfgService")
public class BasePdaRoleCfgService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePdaRoleCfgService.class);
    @Autowired
    private IBasePdaRoleCfg basePdaRoleCfgServer;

    public BasePdaRoleCfgService() {
        super();
    }

    @Override
    public IBaseCommon getBaseCommonServer() {
        return basePdaRoleCfgServer;
    }

    /**
     * 根据Id查询数据
     *
     * @param params 参数id
     *               {
     *               id:主键Id
     *               }
     * @return
     * @author ZhangJun
     * @creteTime 2018/3/27
     */
    @RequestMapping(method = RequestMethod.POST, value = "findById")
    public String findById(String params) {
        return super.findById(params);
    }

    /**
     * 查找数据
     *
     * @param params
     * @return
     * @author ZhangJun
     * @createTime 2018/3/27
     * @description 查找数据
     */
    @RequestMapping(method = RequestMethod.POST, value = "findList")
    public String findList(@RequestParam(required = false) String params) {
        return super.findList(params);
    }

    /**
     * 查找数据
     *
     * @param params
     * @param pageIndex
     * @param pageRows
     * @return
     * @author ZhangJun
     * @createTime 2018/3/27
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        return super.findPagination(params, pageIndex, pageRows);
    }


    /**
     * 查找数据(查询条件：角色名称，库存组织,菜单名称,是否生效,子库名称,子库编码)
     *
     * @param params
     * @param pageIndex
     * @param pageRows
     * @return
     * @author 黄铭林
     * @createTime 2018/4/11
     */
    @Permission(menuCode = "PDAYHCDPZB")
    @RequestMapping(method = RequestMethod.POST, value = "findPaginationByParams")
    public String findPaginationByParams(@RequestParam(required = false) String params,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON     = parseObject(params);
            queryParamJSON.put("operationOrgIds",baseAccreditCacheServer.getOrgId(getSessionUserId()));
            SaafToolUtils.cleanNull(queryParamJSON,"menuName","orgName","roleName","channelCode","enabled");
            Pagination paginationByParams = basePdaRoleCfgServer.findPaginationByParams(queryParamJSON, pageIndex, pageRows);
            JSONObject results            = JSONObject.parseObject(JSON.toJSONString(paginationByParams));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 保存或更新数据
     *
     * @param params
     * @return
     * @author huangminglin
     * @createTime 2018/4/11
     */
    @RequestMapping(method = RequestMethod.POST, value = "save")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
            return super.saveOrUpdate(params);
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
     * @createTime 2018/3/27
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        return super.delete(params);
    }


    /**
     * basePdaInvCfgSync 初始化同步update
     * @param params 无参
     * @return
     * @author ZhangJun
     * @createTime 2018/3/15
     */
    @RequestMapping(method = RequestMethod.POST, value = "basePdaRoleCfgSync")
    public String basePdaRoleCfgSync(@RequestParam(required = false) String params) {
        try{
            JSONObject queryParamJSON = parseObject(params);

            String result = this.basePdaRoleCfgServer.basePdaRoleCfgSync(queryParamJSON);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, result, 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

}
