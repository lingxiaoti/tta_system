package com.sie.saaf.base.dict.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupTypesEntity_HI;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupTypeEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupTypes;
import com.sie.saaf.base.dict.model.inter.IBaseLookupValues;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangtao
 * @description 数据字典头表server
 * @createTime 2017年12月11日 20:16:15
 */
@Component("baseLookupTypesServer")
public class BaseLookupTypesServer extends BaseCommonServer<BaseLookupTypesEntity_HI> implements IBaseLookupTypes {
    @Autowired
    private ViewObject<BaseLookupTypesEntity_HI> baseLookupTypesDAO_HI;

    @Autowired
    private ViewObject<BaseLookupValuesEntity_HI> baseLookupValuesDAO_HI;

    @Autowired
    private IBaseLookupValues baseLookupValuesServer;

    @Autowired
    private BaseViewObject<BaseLookupTypeEntity_HI_RO> baseLookupTypeDAO_HI_RO;


    public BaseLookupTypesServer() {
        super();
    }


    /**
     * @param parameter {
     *                  parentTypeId       父节点id
     *                  lastUpdateDate     更新日期
     *                  versionNum         版本号
     *                  description        描述
     *                  lookupTypeId       主键ID
     *                  lookupType         数据字典类型
     *                  systemCode         系统编码
     *                  createdBy          创建人
     *                  meaning            说明
     *                  customizationLevel 系统级别或是用户级别
     *                  }
     * @param userId
     * @return BaseLookupTypesEntity_HI
     * @throws Exception
     * @description 数据字典头表保存/修改
     */
    @Override
    public BaseLookupTypesEntity_HI saveOrUpdateBaseLookupType(JSONObject parameter, int userId) throws Exception {
        BaseLookupTypesEntity_HI instance = SaafToolUtils.setEntity(BaseLookupTypesEntity_HI.class, parameter, baseLookupTypesDAO_HI, userId);

        if (instance.getLookupTypeId() == null) {
            if (StringUtils.isBlank(instance.getLookupType())) {
                throw new IllegalArgumentException("缺少 参数 lookupType ");
            }
            if (StringUtils.isBlank(instance.getSystemCode()))
                throw new IllegalArgumentException("缺少参数 systemCode");
            Map<String, Object> map = new HashMap<>();
            map.put("lookupType", instance.getLookupType());
            map.put("systemCode", instance.getSystemCode());
            map.put("deleteFlag", CommonConstants.DELETE_FALSE);
            if (baseLookupTypesDAO_HI.findByProperty(map).size() > 0) {
                throw new IllegalArgumentException("lookupType和systemCode的组合已存在");
            }
            instance.setDeleteFlag(0);
        } else {
            if (StringUtils.isBlank(parameter.getString("versionNum")))
                throw new IllegalArgumentException("缺少参数 versionNum");
        }
        baseLookupTypesDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    /**
     * @param queryParamJSON
     * @return
     * @description 数据字典头表查询
     */
    public List<BaseLookupTypesEntity_HI> findBaseLookupTypesInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<BaseLookupTypesEntity_HI> findListResult = baseLookupTypesDAO_HI.findList("from BaseLookupTypesEntity_HI",
                queryParamMap);
        return findListResult;
    }


    /**
     * @param queryParamJSON {
     *                       parentTypeId       父节点id
     *                       lookupTypeId       主键ID
     *                       lookupType         数据字典类型
     *                       systemCode         系统编码
     *                       meaning            说明
     *                       customizationLevel 系统级别或是用户级别
     *                       }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 数据字典头表分页查询
     */
    @Override
    public Pagination<BaseLookupTypeEntity_HI_RO> findBaseLookupTypesPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        String lookupType = queryParamJSON.getString("lookupType_like");
        if(StringUtils.isNotEmpty(lookupType)){
            queryParamJSON.put("lookupType_like",lookupType.toUpperCase());
        }
        sql.append(BaseLookupTypeEntity_HI_RO.query);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        changeQuerySql(queryParamJSON,paramsMap,sql,false);
        sql.append(" ORDER BY blt.last_update_date DESC");

        Pagination<BaseLookupTypeEntity_HI_RO> findList = baseLookupTypeDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }

    /**
     * @param id
     * @description 删除数据字典头表
     */
    @Override
    public void delete(Integer id) {
        if (id == null)
            return;
        BaseLookupTypesEntity_HI type = baseLookupTypesDAO_HI.getById(id);
        if (id == null)
            return;
        type.setDeleteFlag(CommonConstants.DELETE_TRUE);
        List<BaseLookupValuesEntity_HI> list = baseLookupValuesDAO_HI.findByProperty("lookupType", type.getLookupType());
        baseLookupTypesDAO_HI.update(type);
        for (BaseLookupValuesEntity_HI instance : list){
            baseLookupValuesServer.deleteCache(instance.getLookupValuesId());
        }

    }
    protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql,boolean isHql){
       SaafToolUtils.parperParam(queryParamJSON, "blt.system_code", "systemCode", sql, paramsMap, "=");
       SaafToolUtils.parperParam(queryParamJSON, "blt.customization_level","customizationLevel", sql, paramsMap, "=");
       SaafToolUtils.parperParam(queryParamJSON, "blt.meaning","meaning_like", sql, paramsMap ,"like");
       SaafToolUtils.parperParam(queryParamJSON, "blt.lookup_type" ,"lookupType_like", sql, paramsMap ,"like");
       SaafToolUtils.parperParam(queryParamJSON, "blt.lookup_type_id", "lookupTypeId", sql, paramsMap, "=");
    }

}
