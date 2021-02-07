package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.inter.IBaseProfile;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangJun
 * @creteTime 2017-12-18
 */
@RestController
@RequestMapping("/baseProfileService")
public class BaseProfileService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(BaseProfileService.class);

    @Autowired
    private IBaseProfile baseProfileServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return baseProfileServer;
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
        return super.findPagination(params, pageIndex, pageRows);
    }

    /**
     * 保存或更新数据
     *
     * @param params
     * @return
     * @author ZhangJun
     * @creteTime 2017/12/18
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
     * @creteTime 2017/12/18
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        return super.delete(params);
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
     * @creteTime 2017/12/18
     */
    @RequestMapping(method = RequestMethod.POST, value = "findById")
    public String findById(String params) {
        return super.findById(params);
    }

    /**
     * 通过profileId查询ProfileS中对应SQL的语句结果
     *
     * @param params 查询参数
     *               {
     *               profileId:profile主键
     *               }
     * @return Profile对应SQL的查询结果集
     * @author ZhangJun
     * @createTime 2018/1/11 19:17
     * @description 通过profileId查询ProfileS中对应SQL的语句结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findProfileSqlDatas")
    public String findProfileSqlDatas(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            List<JSONObject> findList       = this.baseProfileServer.findProfileSqlDatas(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 通过职责表主键值查询ProfileS中对应SQL的语句结果
     *
     * @param params 查询参数
     *               {
     *               profileCode:profile编码
     *               userRespId：[]  职责id
     *               }
     * @return Profile对应SQL的查询结果集
     * @author huangminglin
     * @createTime 2018/1/11 19:17
     * @description 通过职责表主键值查询ProfileS中对应SQL的语句结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findProfileSqlDatasByResponsibilityId")
    public String findProfileSqlDatasByResponsibilityId(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);

            //测试用
            //UserSessionBean userSessionBean = getUserSessionBean();

         /*   JSONArray userRespList2 = new JSONArray();

            userRespList2.add(parseObject("{userRespId:'9692'}"));

            userSessionBean.setUserRespList(userRespList2);*/

            JSONArray userRespList = queryParamJSON.getJSONArray("userRespId");
            if (userRespList != null) {

                List<Integer> respIdList = new ArrayList<>();

                for (int i = 0; i < userRespList.size(); i++) {
                    respIdList.add(userRespList.getInteger(i));
                }

                List<String> findList = this.baseProfileServer.findProfileSqlDatasByResponsibilityId(queryParamJSON,respIdList);

                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
            } else {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "用户未登陆", 0, null).toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}
