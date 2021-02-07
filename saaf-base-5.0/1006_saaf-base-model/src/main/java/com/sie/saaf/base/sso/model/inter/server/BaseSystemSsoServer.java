package com.sie.saaf.base.sso.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.base.dict.model.inter.IBaseLookupValues;
import com.sie.saaf.base.sso.model.entities.BaseSystemSsoEntity_HI;
import com.sie.saaf.base.sso.model.inter.IBaseSystemSso;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import java.util.*;

/**
 * @author huangtao
 * @createTime 2017年12月12日 11:03:35
 * @description 单点登录server
 */
@Component("baseSystemSsoServer")
public class BaseSystemSsoServer extends BaseCommonServer<BaseSystemSsoEntity_HI> implements IBaseSystemSso {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSystemSsoServer.class);
    @Autowired
    private ViewObject<BaseSystemSsoEntity_HI> baseSystemSsoDAO_HI;

    @Autowired
    private DynamicBaseViewObject commonDAO_HI_DY;

    @Autowired
    private IBaseLookupValues baseLookupValuesServer;

//    @Autowired
//    private JedisCluster jedisCluster;




    /**
     * @param queryParamJSON {
     *                       lastUpdatedBy  更新人
     *                       sysSsoId       主键Id
     *                       clientId
     *                       orderNo        排序
     *                       lastUpdateDate 更新日期
     *                       versionNum     版本号
     *                       requestMethod  请求方法 get/post
     *                       description    描述
     *                       params         参数
     *                       creationDate   创建日期
     *                       systemName     系统名称
     *                       systemCode     系统编码
     *                       createdBy      创建人
     *                       requestUrl     服务地址
     *                       imageUrl       图标地址
     *                       enableFlag     是否启用
     *                       }
     * @return
     * @description 单点登录查询
     */
    public List<BaseSystemSsoEntity_HI> findBaseSystemSsoInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<BaseSystemSsoEntity_HI> findListResult = baseSystemSsoDAO_HI.findByProperty(queryParamMap);
        return findListResult;
    }

    /**
     * @param params ｛
     *               sysSsoId       主键Id
     *               orderNo        排序
     *               lastUpdateDate 更新日期
     *               versionNum     版本号
     *               requestMethod  请求方法 get/post
     *               description    描述
     *               params         参数
     *               creationDate   创建日期
     *               systemName     系统名称
     *               systemCode     系统编码
     *               createdBy      创建人
     *               requestUrl     服务地址
     *               imageUrl       图标地址
     *               enableFlag     是否启用
     *               ｝
     * @param userId
     * @return
     * @throws Exception
     * @description 单点登陆表 新增/修改
     */
    @Override
    public BaseSystemSsoEntity_HI saveOrUpdateSystemSsoInfo(JSONObject params, int userId) throws Exception {
        boolean updateSystemRole = false;
        String systemCode = params.getString("systemCode");

        if (StringUtils.isBlank(params.getString("sysSsoId"))) {
            SaafToolUtils.validateJsonParms(params, "systemCode");
            if (baseSystemSsoDAO_HI.findByProperty("systemCode", params.getString("systemCode")).size() > 0)
                throw new IllegalArgumentException("systemCode 值已存在");
            List<BaseLookupValuesEntity_HI> dics = baseLookupValuesServer.findList(new JSONObject().fluentPut("lookupType", CommonConstants.DIC_TYPE_SYSTEM_CODE).fluentPut("lookupCode", systemCode));
            Assert.notEmpty(dics, "数据字典[#]不存在".replace("#", systemCode));
            params.put("systemName", dics.get(0).getMeaning());
            if (CommonConstants.ENABLED_TRUE.equals(params.getString("enableFlag")) == false)
                params.put("enableFlag", CommonConstants.ENABLED_FALSE);
            if (StringUtils.isBlank(params.getString("orderNo")))
                params.put("orderNo", getOrderNo());
            params.put("clientId", UUID.randomUUID().toString());
        } else {
            SaafToolUtils.validateJsonParms(params, "versionNum");
            //系统编码与系统名如有修改,同步更新映射表中的值
            if (params.containsKey("systemCode")) {
                SaafToolUtils.validateJsonParms(params, "systemCode");

                List<BaseLookupValuesEntity_HI> dics = baseLookupValuesServer.findList(new JSONObject().fluentPut("lookupType", CommonConstants.DIC_TYPE_SYSTEM_CODE).fluentPut("lookupCode", systemCode));
                Assert.notEmpty(dics, "数据字典[#]不存在".replace("#", systemCode));
                params.put("systemName", dics.get(0).getMeaning());

                BaseSystemSsoEntity_HI systemSso = baseSystemSsoDAO_HI.getById(params.getInteger("sysSsoId"));
                if (systemSso == null)
                    throw new IllegalArgumentException("数据异常,主键id[#]不存在".replace("#", params.getString("sysSsoId")));
                List<BaseSystemSsoEntity_HI> systemSsoList = baseSystemSsoDAO_HI.findByProperty("systemCode", systemCode);
                if (systemSsoList.size() > 0 && systemSsoList.get(0).getSysSsoId().equals(systemSso.getSysSsoId())==false) {
                    throw new IllegalArgumentException("系统编码#配置已存在".replace("#",systemCode));
                }
                if (systemSso.getSystemCode().equals(systemCode) == false) {
                    systemCode = systemSso.getSystemCode();
                    updateSystemRole = true;
                } else if (Objects.equals(systemSso.getSystemName(), params.getString("systemName")) == false) {
                    updateSystemRole = true;
                }
            }
        }

        BaseSystemSsoEntity_HI instance = SaafToolUtils.setEntity(BaseSystemSsoEntity_HI.class, params, baseSystemSsoDAO_HI, userId);
        baseSystemSsoDAO_HI.saveOrUpdate(instance);

        return instance;
    }


    /**
     * @param queryParamJSON ｛
     *                       systemName 系统名
     *                       sysSsoId   主键id
     *                       systemCode 系统编码
     *                       clientId   clientid
     *                       enableFlag 生效标识
     *                       ｝
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 单点登录系统分页查询
     */
    @Override
    public Pagination<BaseSystemSsoEntity_HI> findSystemSsoInfoWithPage(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append("from BaseSystemSsoEntity_HI where 1=1 ");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "systemName", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "requestUrl", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "sysSsoId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "systemCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "clientId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "enableFlag", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "orderNo,lastUpdateDate desc", false);
        Pagination<BaseSystemSsoEntity_HI> findList = baseSystemSsoDAO_HI.findPagination(sql,paramsMap, pageIndex, pageRows);
        return findList;
    }


    /**
     * @return
     * @description 返回最大序号+1
     */
    @Override
    public Integer getOrderNo() {
        List<HashMap<String, Integer>> list = commonDAO_HI_DY.findList("SELECT max(order_no) as orderNo FROM base_system_sso");
        if (list == null || list.size() == 0)
            return 1;
        return list.get(0).get("orderNo") + 1;
    }


    @Override
    public void delete(Integer id) {
        if (id == null)
            return;
        BaseSystemSsoEntity_HI instance = baseSystemSsoDAO_HI.getById(id);
        if (instance == null)
            return;
        baseSystemSsoDAO_HI.delete(id);
    }

    @Override
    public void delete(String[] ids) {
        if (ids == null)
            return;
        for (String id : ids) {
            delete(Integer.parseInt(id));
        }
    }


}
