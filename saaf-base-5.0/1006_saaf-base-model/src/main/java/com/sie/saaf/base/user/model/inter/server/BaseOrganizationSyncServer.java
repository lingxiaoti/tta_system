package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.dao.BaseOrganizationDAO_HI;
import com.sie.saaf.base.user.model.entities.BaseOrganizationEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseOrganizationPerson_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseOrganization_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseOrganizationSync;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Chenzg
 * @creteTime 2018-04-13
 */
@Component("baseOrganizationSyncServer")
public class BaseOrganizationSyncServer extends BaseCommonServer<BaseOrganizationEntity_HI> implements IBaseOrganizationSync {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseOrganizationSyncServer.class);
//    @Autowired
//    private BaseOrganizationDAO_HI baseOrganizationDAO_HI;
//    @Autowired
//    private BaseViewObject<BaseOrganizationPerson_HI_RO> baseOrganizationPersonDAO_HI_RO;
//    @Autowired
//    private BaseViewObject<BaseOrganization_HI_RO> baseOrganizationDAO_HI_RO;
//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;
    @Autowired
    private JedisCluster jedisCluster;

    public BaseOrganizationSyncServer() {
        super();
    }

    Integer count = 0;

    @Override
    public List<BaseOrganizationEntity_HI> syncOrganizations() throws SQLException {

        // get root organizationList,and save
        List<JSONObject> rootOrgList = getRootOrgList();
        savOrgList(rootOrgList);
        LOGGER.info(rootOrgList.size()+"");
        count = count + rootOrgList.size();
        for (JSONObject org : rootOrgList) {
            String orgId = org.get("SOURCE_SYSTEM_ID").toString();
            // get second
            List<JSONObject> secondLevelOrgList = getSecondOrgList(orgId);
            savOrgList(secondLevelOrgList);
            getThirdOrMoreOrgList(secondLevelOrgList);
        }

        //保存至redis中
        JSONObject redisValue = new JSONObject();
        redisValue.put("orgLastSynTime",new Date());
        jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_ORGANIZATION_SYNC_KEY,String.valueOf("orgLastSynTime"),redisValue.toJSONString());

        return null;
    }

    private void getThirdOrMoreOrgList(List<JSONObject> secondLevelOrgList) throws SQLException {
        for (JSONObject secondLevelOrg : secondLevelOrgList) {
            // get third
            String secondOrgId = secondLevelOrg.get("SOURCE_SYSTEM_ID").toString();
            String parentOrgId = secondLevelOrg.get("PARENT_ORG_ID").toString();
            if (secondOrgId.equals(parentOrgId)) continue;
            List<JSONObject> thirdLevelOrgList = getThirdOrgList(secondOrgId);
            savOrgList(thirdLevelOrgList);

            getThirdOrMoreOrgList(thirdLevelOrgList);
        }
    }

    private void savOrgList(List<JSONObject> rootOrgList) {
        List<BaseOrganizationEntity_HI> list = JSONArray.parseArray(rootOrgList.toString(), BaseOrganizationEntity_HI.class);
        this.save(list);
    }

    private List<JSONObject> getThirdOrgList(String secondOrgId) throws SQLException {
        List<JSONObject> orgs = new ArrayList<>();
//        StringBuffer sb = new StringBuffer();
//        sb.append("select '' as org_id,--主键自增长\n" +
//                "       '"+secondOrgId+"' as parent_org_id,\n" +
//                "       dept.org_id as organization_id,\n" +
//                "       '' as org_code,\n" +
//                "       dept.department_name as org_name,\n" +
//                "       '行政' as tree_type,\n" +
//                "       dept.channel_type as channel_type,\n" +
//                "       '' as business_type,\n" +
//                "       'Y' as is_dep,\n" +
//                "       'DEPT' as org_type,\n" +
//                "       '' as org_level,\n" +
//                "       '' as is_leaf,\n" +
//                "       dept.EFFECTIVE_BEG_DATE as start_date,\n" +
//                "       dept.EFFECTIVE_END_DATE as end_date,\n" +
//                "       '' as remark,\n" +
//                "       '' as org_pinyin_name,\n" +
//                "       '' as org_simple_pinyin_name,\n" +
//                "       '' as org_hierarchy_id,\n" +
//                "       '' as org_email,\n" +
//                "       dept.department_id as source_system_id,--源系统ID\n" +
//                "       '' as leader_id,\n" +
//                "       'Y' as enabled\n" +
//                "from auportal.base_department_v dept where parent_department_id='" + secondOrgId + "'");
//        List<JSONObject> orgs = oracleTemplateServer.findList(sb.toString());
        return orgs;
    }

    private List<JSONObject> getSecondOrgList(String orgId) throws SQLException {
        List<JSONObject> orgs = new ArrayList<>();
//        StringBuffer sb = new StringBuffer();
//        sb.append("select '' as org_id,--主键自增长\n" +
//                "      '"+orgId+"' as PARENT_ORG_ID,--第一步查出的部门结构树ID\n" +
//                "       DEPT.ORG_ID AS ORGANIZATION_ID,--OU组织ID\n" +
//                "       dept.department_name as org_name,\n" +
//                "       '行政' as tree_type,\n" +
//                "       dept.channel_type as channel_type,\n" +
//                "       '' AS BUSINESS_TYPE,\n" +
//                "       'Y' AS IS_DEPT,\n" +
//                "       'DEPT' AS ORG_TYPE,\n" +
//                "       '' as org_level,\n" +
//                "       '' as is_leaf,\n" +
//                "       dept.EFFECTIVE_BEG_DATE as start_date,\n" +
//                "       dept.EFFECTIVE_END_DATE as end_date,\n" +
//                "       '' as remark,\n" +
//                "       '' as org_pinyin_name,\n" +
//                "       '' as org_simple_pinyin_name,\n" +
//                "       '' as org_hierarchy_id,\n" +
//                "       '' as org_email,\n" +
//                "       DEPT.department_id as source_system_id,--源系统ID\n" +
//                "       '' as leader_id,\n" +
//                "       'Y' as enabled\n" +
//                " from auportal.base_department_v DEPT where department_level_num=0 AND DEPARTMENT_STRUCTURE_TYPE=10 AND DEPARTMENT_STRUCTURE_ID=" + orgId);
//        List<JSONObject> orgs = oracleTemplateServer.findList(sb.toString());
        return orgs;
    }

    private List<JSONObject> getRootOrgList() throws SQLException {
        List<JSONObject> orgs = new ArrayList<>();
//        StringBuffer sb = new StringBuffer();
//        sb.append("select '' as org_id,--主键自增长\n" +
//                "       0 as PARENT_ORG_ID,--部门结构树当第一层使用\n" +
//                "       DEPT.ORG_ID AS ORGANIZATION_ID,--OU组织ID\n" +
//                "       DEPT.DEPARTMENT_STRUCTURE_NAME AS ORG_NAME,--部门结构名称为组织名称\n" +
//                "       '行政' AS TREE_TYPE,\n" +
//                "       '' AS CHANNEL_TYPE,\n" +
//                "       '' AS BUSINESS_TYPE,\n" +
//                "       'Y' AS IS_DEPT,\n" +
//                "       'DEPT' AS ORG_TYPE,\n" +
//                "       '' as org_level,\n" +
//                "       '' as is_leaf,\n" +
//                "       '' as start_Date,\n" +
//                "       '' as end_Date,\n" +
//                "       '' as remark,\n" +
//                "       '' as org_pinyin_name,\n" +
//                "       '' as org_simple_pinyin_name,\n" +
//                "       '' as org_hierarchy_id,\n" +
//                "       '' as org_email,\n" +
//                "       DEPT.DEPARTMENT_STRUCTURE_ID as source_system_id,--源系统ID\n" +
//                "       '' as leader_id,\n" +
//                "       'Y' as enabled\n" +
//                "from BASE.BASE_DEPARTMENT_STRUCTURE DEPT \n" +
//                "WHERE DEPARTMENT_STRUCTURE_TYPE=10");
//
//        orgs = oracleTemplateServer.findList(sb.toString());
        return orgs;
    }

}
