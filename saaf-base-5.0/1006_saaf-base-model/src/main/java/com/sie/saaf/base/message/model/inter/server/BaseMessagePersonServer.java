package com.sie.saaf.base.message.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessagePersonEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessagePersonEntity_HI_RO;
import com.sie.saaf.base.message.model.inter.IBaseMessagePerson;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseOrgStructureEntity_HI_RO;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseMessagePersonServer")
public class BaseMessagePersonServer extends BaseCommonServer<BaseMessagePersonEntity_HI> implements IBaseMessagePerson {

    @Autowired
    private BaseViewObject<BaseMessagePersonEntity_HI_RO> baseMessagePersonDAO_HI_RO;

    @Autowired
    private BaseViewObject<BaseOrgStructureEntity_HI_RO> baseOrgStructureDAO_HI_RO;

    public BaseMessagePersonServer() {
        super();
    }

    /**
     * 查看发送对象：查询包含接收人员信息
     * @param queryParamJSON
     * {
     *     conMessId：消息ID，
     *     depMessId：包含部门记录ID，
     *     deptId：部门ID
     * }
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @Override
    public Pagination<BaseMessagePersonEntity_HI_RO> findReceiverPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows){
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("conMessId", queryParamJSON.getInteger("conMessId"));
        queryParamMap.put("depMessId", queryParamJSON.getInteger("depMessId"));
        queryParamMap.put("deptId", queryParamJSON.getInteger("deptId"));
        StringBuffer querySQL = new StringBuffer(BaseMessagePersonEntity_HI_RO.QUERY_MESSAGE_PERSON_SQL);
        StringBuffer queryCountSQL = new StringBuffer(BaseMessagePersonEntity_HI_RO.QUERY_MESSAGE_PERSON_COUNT_SQL);
        return baseMessagePersonDAO_HI_RO.findPagination(querySQL, queryCountSQL, queryParamMap, pageIndex, pageRows);
    }

    /**
     * 查询站内消息待发送对象
     * @param queryParamJSON
     * {
     *     buMessId：接收对象组合ID
     *     userType : 用户类型
     * }
     * @return
     */
    public Pagination<BaseMessagePersonEntity_HI_RO> findMessagePerson(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("buMessId", queryParamJSON.getInteger("buMessId"));
        StringBuffer querySQL = new StringBuffer(BaseMessagePersonEntity_HI_RO.QUERY_MESSAGE_TARGET_PERSON_SQL);

        switch (queryParamJSON.getString("userType")) {
            case "20":
                querySQL = querySQL.replace(querySQL.indexOf(":userTypeTable"), querySQL.indexOf(":userTypeTable") + 14, BaseOrgStructureEntity_HI_RO.QUERY_PERSON_IN_DEPT_SQL);
                querySQL.append("\n GROUP BY departmentId,userId");
                break;
            case "30":
                querySQL = querySQL.replace(querySQL.indexOf(":userTypeTable"), querySQL.indexOf(":userTypeTable") + 14, BaseOrgStructureEntity_HI_RO.QUERY_CUSTOMER_IN_DEPT_SQL);
                querySQL.append("\n GROUP BY departmentId,userId");
                break;
            case "40":
                querySQL = querySQL.replace(querySQL.indexOf(":userTypeTable"), querySQL.indexOf(":userTypeTable") + 14, BaseOrgStructureEntity_HI_RO.QUERY_STORE_IN_CUSTOMER_SQL);
                querySQL.append("\n GROUP BY departmentId,userId");
                break;
        }

        return baseMessagePersonDAO_HI_RO.findPagination(querySQL, SaafToolUtils.getSqlCountString(querySQL), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 直接添加人员、经销商、门店
     * @param queryParamJSON
     * {
     *      ouId：事业部ID，
     *      keyWordName：人员名称、经销商名称
     *      userType：用户类型
     * }
     * @return 人员、经销商、门店列表
     */
    public List<BaseOrgStructureEntity_HI_RO> findDirectAddPerson(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer();
        String keyWordName = queryParamJSON.getString("keyWordName");
        String keyWordNameList[];
        int i, length;

        if (keyWordName.contains(",")) {
            keyWordNameList = keyWordName.replace(" ", "").split(",");
        } else {
            keyWordNameList = keyWordName.replace(" ", "").split("，");
        }

        length = keyWordNameList.length;

        switch (queryParamJSON.getString("userType")) {
            case "20" :
                querySql.append(BaseOrgStructureEntity_HI_RO.QUERY_PERSON_IN_DEPT_SQL);
                querySql.append(" AND department.ou_id = :ouId     ");
                queryParamMap.put("ouId", queryParamJSON.getInteger("ouId"));
                querySql.append(" and (1 != 1     ");
                for (i = 0; i < length; i++) {
                    querySql.append(" or (users.user_name = '" + keyWordNameList[i] + "' or person.person_name = '" + keyWordNameList[i] + "')    ");
                }
                querySql.append(")");
                break;
            case "30" :
                querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_CUSTOMER_IN_DEPT_SQL);
                querySql.append(" AND department.ou_id = :ouId     ");
                queryParamMap.put("ouId", queryParamJSON.getInteger("ouId"));
                querySql.append(" and (1 != 1     ");
                for (i = 0; i < length; i++) {
                    querySql.append(" or (customer.customer_number = '" + keyWordNameList[i] + "' or customer.customer_name = '" + keyWordNameList[i] + "')    ");
                }
                querySql.append(")");
                break;
            case "40" :
                querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_STORE_IN_CUSTOMER_SQL);
                querySql.append(" AND invStoreT.ou_id = :ouId     ");
                queryParamMap.put("ouId", queryParamJSON.getInteger("ouId"));
                querySql.append(" and (1 != 1     ");
                for (i = 0; i < length; i++) {
                    querySql.append(" or (invStoreT.store_code = '" + keyWordNameList[i] + "' or invStoreT.store_name = '" + keyWordNameList[i] + "')    ");
                }
                querySql.append(")");
                break;
        }
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }
}
