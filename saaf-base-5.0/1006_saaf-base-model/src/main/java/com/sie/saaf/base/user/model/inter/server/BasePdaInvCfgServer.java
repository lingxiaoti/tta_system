package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePdaInvCfgEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BasePdaInvCfgUserWarehouse_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.base.user.model.inter.IBasePdaInvCfg;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("basePdaInvCfgServer")
public class BasePdaInvCfgServer extends BaseCommonServer<BasePdaInvCfgEntity_HI> implements IBasePdaInvCfg {
    @Autowired
    private BaseViewObject<BasePdaInvCfgUserWarehouse_HI_RO> basePdaInvCfgUserWarehouseDAO_HI_RO;
//    @Autowired
//    private OracleTemplateServer                             oracleTemplateServer;

//    @Autowired
//    private BaseViewObject<BaseUsersPerson_HI_RO> baseUsersPersonDAO_HI_RO;

    public BasePdaInvCfgServer() {
        super();
    }

    @Override
    public Pagination<BasePdaInvCfgUserWarehouse_HI_RO> findPdaInvROPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sb = new StringBuffer(BasePdaInvCfgUserWarehouse_HI_RO.QUERY_SQL);

        Map<String, Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperHbmParam(BasePdaInvCfgUserWarehouse_HI_RO.class, queryParamJSON, sb, paramsMap);

        // 权限校验
//        String requestURL = CloudInstanceNameConstants.INSTANCE_BASE+"/baseWarehouseMappingService/findWarehouseCodeByOrgId";
//        JSONArray warehouseCodes = SaafToolUtils.preaseServiceResult(requestURL,new JSONObject().fluentPut("params",queryParamJSON));
//        if(warehouseCodes != null && warehouseCodes.size() > 0){
//            String fromSubInv = SaafToolUtils.buildLogicIN(" A.SUB_INV_CODE", warehouseCodes);
//            sb.append(" AND ").append(fromSubInv);
//        }else {
//            sb.append(" and 1=2 ");
//        }
        changeQuerySort(queryParamJSON, sb, "A.last_update_date desc,A.CFG_ID", false);
        Pagination<BasePdaInvCfgUserWarehouse_HI_RO> pagination = basePdaInvCfgUserWarehouseDAO_HI_RO.findPagination(sb,SaafToolUtils.getSqlCountString(sb), paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public String basePdaInvCfgSync(JSONObject queryParamJSON) throws SQLException {

        StringBuffer result = new StringBuffer();

//        //从oracle读取数据------------------------------------------------
//
//        StringBuffer sql = new StringBuffer("select a.sub_inv_code as subInvCode,\n" +
//                "         a.organization_id as organizationId,\n" +
//                "         a.channel_code as channelCode,\n" +
//                "         a.role_id as roleId,\n" +
//                "         a.user_id as userId,\n" +
//                "         '0' as deleteFlag,\n" +
//                "         a.creation_date as creationDate,\n" +
//                "         '-1' as createdBy,\n" +
//                "         sysdate as lastUpdateDate,\n" +
//                "         '-1' as lastUpdatedBy,\n" +
//                "         '0' as versionNum,\n" +
//                "         '-1' as lastUpdateLogin\n" +
//                "   from brc.brc_pda_inv_cfg a ");
//        Map<String, Object> paramsMap  = new HashMap<>();
//        List<JSONObject>    oracleList = oracleTemplateServer.findList(sql.toString(), paramsMap);
//        //---------------------------------------------------------------
//        //遍历list结果，通过userId 在brc里通过source_id查询brc中base_users表的user_Id
//        for (JSONObject json : oracleList) {
//            BasePdaInvCfgEntity_HI basePdaInvCfgEntityHi = new BasePdaInvCfgEntity_HI();
//            basePdaInvCfgEntityHi.setSubInvCode(json.getString("SUBINVCODE"));
//            basePdaInvCfgEntityHi.setOrganizationId(json.getInteger("ORGANIZATIONID"));
//            basePdaInvCfgEntityHi.setChannelCode(json.getString("CHANNELCODE"));
//            basePdaInvCfgEntityHi.setRoleId(json.getInteger("ROLEID"));
//            basePdaInvCfgEntityHi.setUserId(json.getInteger("USERID"));
//            basePdaInvCfgEntityHi.setDeleteFlag(json.getInteger("DELETEFLAG"));
//            basePdaInvCfgEntityHi.setCreationDate(json.getDate("CREATIONDATE"));
//            basePdaInvCfgEntityHi.setCreatedBy(json.getInteger("CREATEDBY"));
//            basePdaInvCfgEntityHi.setLastUpdateDate(json.getDate("LASTUPDATEDATE"));
//            basePdaInvCfgEntityHi.setLastUpdatedBy(json.getInteger("LASTUPDATEDBY"));
//            basePdaInvCfgEntityHi.setVersionNum(json.getInteger("VERSIONNUM"));
//            basePdaInvCfgEntityHi.setLastUpdateLogin(json.getInteger("LASTUPDATELOGIN"));
//            //替换原查出的数据中的userId ,插入到brc中的base_Pda_Inv_Cfg表中
//            Integer             userId  = basePdaInvCfgEntityHi.getUserId();
//            String              userSql = BaseUsersPerson_HI_RO.SQL_USER_BY_SOURCEID;
//            Map<String, Object> map     = new HashMap<>();
//            map.put("sourceId", userId);
//            List<BaseUsersPerson_HI_RO> user = baseUsersPersonDAO_HI_RO.findList(userSql, map);
//            if (!user.isEmpty()) {
//                Integer newUserId = user.get(0).getUserId();
//                basePdaInvCfgEntityHi.setUserId(newUserId);
//                this.saveOrUpdate(basePdaInvCfgEntityHi);
//            }
//        }
        return result.append("已同步" + 0 + "条").toString();
    }

    public BasePdaInvCfgEntity_HI saveOrUpdate(JSONObject queryParamJSON) {

        BasePdaInvCfgEntity_HI result;

        if (null == queryParamJSON.getString("channelCode") || "".equals(queryParamJSON.getString("channelCode"))) {
            queryParamJSON.fluentPut("channelCode", "0");
        }

        if (null != queryParamJSON.getInteger("cfgId")) {    //更新
            //查询数据库中是否有相同数据 -----------------------------------
            Map<String,Object> judgeParamMap = new HashMap<>();
            judgeParamMap.put("cfgId", queryParamJSON.getString("cfgId"));
            judgeParamMap.put("userId", queryParamJSON.getString("userId"));
            judgeParamMap.put("subInvCode", queryParamJSON.getString("subInvCode"));

            StringBuffer coutSql = new StringBuffer("select count(cfg_Id) from base_pda_inv_cfg where cfg_id = :cfgId and user_id = :userId and sub_Inv_Code = :subInvCode");

            Integer count = basePdaInvCfgUserWarehouseDAO_HI_RO.count(coutSql, judgeParamMap);
            //----------------------------------------------------------

            if (count > 0) {
                return super.saveOrUpdate(queryParamJSON);
            }
        }
        //保存

        //查询数据库中是否有相同数据 -----------------------------------
        JSONObject judgeParamJSON = new JSONObject();
        judgeParamJSON.fluentPut("userId", queryParamJSON.getString("userId"));
        judgeParamJSON.fluentPut("subInvCode", queryParamJSON.getString("subInvCode"));
        List<BasePdaInvCfgEntity_HI> judgeResult = findList(judgeParamJSON);
        //----------------------------------------------------------

        if (judgeResult.size() == 0) {
            result = super.saveOrUpdate(queryParamJSON);
        } else {
            throw new RuntimeException("与原有数据重复,请重新输入");
        }
        return result;
    }
}
