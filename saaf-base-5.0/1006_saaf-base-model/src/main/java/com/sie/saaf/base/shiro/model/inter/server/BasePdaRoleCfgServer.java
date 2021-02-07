package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseMenuEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BasePdaRoleCfgEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BasePdaRoleCfg_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseMenu;
import com.sie.saaf.base.shiro.model.inter.IBasePdaRoleCfg;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("basePdaRoleCfgServer")
public class BasePdaRoleCfgServer extends BaseCommonServer<BasePdaRoleCfgEntity_HI> implements IBasePdaRoleCfg {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BasePdaRoleCfgServer.class);
//    @Autowired
//    private ViewObject<BasePdaRoleCfgEntity_HI> basePdaRoleCfgDAO_HI;
    @Autowired
    private BaseViewObject<BasePdaRoleCfg_HI_RO> basePdaRoleCfgDAO_HI_RO;
//    @Autowired
//    private OracleTemplateServer                 oracleTemplateServer;

    @Autowired
    private IBaseMenu baseMenuServer;

    public BasePdaRoleCfgServer() {
        super();
    }

    /**
     * huangminglin
     *
     * @param queryParamJSON {menuName:菜单名称 orgName:组织名称 roleName:角色名称 enabled：是否启用}
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @Override
    public Pagination findPaginationByParams(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        StringBuffer sql = new StringBuffer(BasePdaRoleCfg_HI_RO.Query_params_sql);

        Map<String, Object> paramsMap = new HashMap<>();
        SaafToolUtils.cleanNull(queryParamJSON, "channelCode");
        SaafToolUtils.parperParam(queryParamJSON, "menuName", "menuName", sql, paramsMap, "like", false);
        SaafToolUtils.parperParam(queryParamJSON, "orgName", "orgName", sql, paramsMap, "like", false);
        SaafToolUtils.parperParam(queryParamJSON, "roleName", "roleName", sql, paramsMap, "like", false);
        SaafToolUtils.parperParam(queryParamJSON, "invName", "invName", sql, paramsMap, "like", false);
        SaafToolUtils.parperParam(queryParamJSON, "invCode", "invCode", sql, paramsMap, "like", false);
        SaafToolUtils.parperParam(queryParamJSON, "channelCode", "channelCode", sql, paramsMap, "=", false);
        SaafToolUtils.parperParam(queryParamJSON, "enabled", "enabled", sql, paramsMap, "=", false);

        sql.append(" ORDER BY lastUpdateDate,pdaRoleCfgId DESC");

        Pagination<BasePdaRoleCfg_HI_RO> pagination = basePdaRoleCfgDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);

        return pagination;
    }


    public BasePdaRoleCfgEntity_HI saveOrUpdate(JSONObject queryParamJSON) {

        BasePdaRoleCfgEntity_HI result = new BasePdaRoleCfgEntity_HI();

        if (null == queryParamJSON.getString("channelCode") || "".equals(queryParamJSON.getString("channelCode"))) {
            queryParamJSON.fluentPut("channelCode", "0");
        }

        JSONArray menuIds = queryParamJSON.getJSONArray("menuId");

        List<Integer> integers = menuIds.toJavaList(Integer.class);

        for (Integer menuId : integers) {
            queryParamJSON.fluentPut("menuId", menuId);

            if (null != queryParamJSON.getInteger("pdaRoleCfgId")) {    //更新
                result = super.saveOrUpdate(queryParamJSON);
            } else {                                                          //保存

                //查询数据库中是否有相同数据 -----------------------------------
                JSONObject judgeParamJSON = new JSONObject();
                judgeParamJSON.fluentPut("menuId", menuId);
                judgeParamJSON.fluentPut("channelCode", queryParamJSON.getString("channelCode"));
                judgeParamJSON.fluentPut("organizationId", queryParamJSON.getString("organizationId"));
                judgeParamJSON.fluentPut("roleId", queryParamJSON.getString("roleId"));
                List<BasePdaRoleCfgEntity_HI> judgeResult = findList(judgeParamJSON);
                //----------------------------------------------------------

                if (judgeResult.size() == 0) {
                    result = super.saveOrUpdate(queryParamJSON);
                } else {
                    List<BaseMenuEntity_HI> childrenMenus = baseMenuServer.findChildrenMenus(menuId);
                    if (childrenMenus.isEmpty()) {
                        throw new RuntimeException("与原有数据重复,请重新输入");
                    }
                }
            }
        }
        return result;
    }

    /**
     * 初始化同步数据
     *
     * @param queryParamJSON
     * @return
     */
    @Override
    public String basePdaRoleCfgSync(JSONObject queryParamJSON) throws SQLException {

        StringBuffer result = new StringBuffer();
//
//        //从oracle读取数据------------------------------------------------
//
//        StringBuffer sql = new StringBuffer("select distinct channel_code as channelCode,ROLE_ID as roleId,ORGANIZATION_ID as organizationId from brc.brc_pda_role_cfg \n");
//        Map<String, Object> paramsMap  = new HashMap<>();
//        List<JSONObject>    oracleList = oracleTemplateServer.findList(sql.toString(), paramsMap);
//        //---------------------------------------------------------------
//        //创建menu_id数据
//        String accountMenuStr = "1190\n" +
//                "11889\n" +
//                "11893\n" +
//                "11895\n" +
//                "11897\n" +
//                "11898\n" +
//                "11899\n" +
//                "11900\n" +
//                "11901\n" +
//                "11902\n" +
//                "11903\n" +
//                "11904\n" +
//                "11905\n" +
//                "11917\n" +
//                "11921\n" +
//                "11926\n" +
//                "11927\n" +
//                "11931\n" +
//                "11932\n" +
//                "22291\n" +
//                "22292\n" +
//                "22293\n" +
//                "22296\n" +
//                "22297\n" +
//                "22298";
//
//        String totalMenuStr = "1190\n" +
//                "11889\n" +
//                "11891\n" +
//                "11892\n" +
//                "11899\n" +
//                "11900\n" +
//                "11901\n" +
//                "11902\n" +
//                "11903\n" +
//                "11904\n" +
//                "11905\n" +
//                "11914\n" +
//                "11917\n" +
//                "11921\n" +
//                "11926\n" +
//                "11927\n" +
//                "11931\n" +
//                "11932\n" +
//                "11933\n" +
//                "11934\n" +
//                "11935\n" +
//                "22291\n" +
//                "22292\n" +
//                "22293\n" +
//                "22298\n";
//
//        //转换成list
//        String[] account = accountMenuStr.split("\n");
//        String[] total = totalMenuStr.split("\n");
//
        //经销商数量
        int accountCount = 0;
        int totalCount = 0;
//
//        //遍历list结果，通过通过role_Id判断总部跟经销商
//        for(JSONObject json : oracleList){
//            if("1".equals(json.getString("ROLEID"))){
//                 for(int i=0 ;i < account.length; i++){
//                     BasePdaRoleCfgEntity_HI basePdaRoleCfgEntityHi = new BasePdaRoleCfgEntity_HI();
//                     basePdaRoleCfgEntityHi.setRoleId(150003);
//                     basePdaRoleCfgEntityHi.setOrganizationId(json.getInteger("ORGANIZATIONID"));
//                     basePdaRoleCfgEntityHi.setChannelCode(json.getString("CHANNELCODE"));
//                     basePdaRoleCfgEntityHi.setMenuId(Integer.valueOf(account[i]));
//                     basePdaRoleCfgEntityHi.setEnabled("Y");
//                     basePdaRoleCfgEntityHi.setCreationDate(new Date());
//                     basePdaRoleCfgEntityHi.setCreatedBy(-1);
//                     basePdaRoleCfgEntityHi.setOperatorUserId(-1);
//                     basePdaRoleCfgEntityHi.setVersionNum(0);
//                     this.saveOrUpdate(basePdaRoleCfgEntityHi);
//                     accountCount++;
//                 }
//            }else{
//                for(int i=0 ;i < total.length; i++){
//                    BasePdaRoleCfgEntity_HI basePdaRoleCfgEntityHi = new BasePdaRoleCfgEntity_HI();
//                    basePdaRoleCfgEntityHi.setRoleId(120003);
//                    basePdaRoleCfgEntityHi.setOrganizationId(json.getInteger("ORGANIZATIONID"));
//                    basePdaRoleCfgEntityHi.setChannelCode(json.getString("CHANNELCODE"));
//                    basePdaRoleCfgEntityHi.setMenuId(Integer.valueOf(total[i]));
//                    basePdaRoleCfgEntityHi.setEnabled("Y");
//                    basePdaRoleCfgEntityHi.setCreationDate(new Date());
//                    basePdaRoleCfgEntityHi.setCreatedBy(-1);
//                    basePdaRoleCfgEntityHi.setOperatorUserId(-1);
//                    basePdaRoleCfgEntityHi.setVersionNum(0);
//                    this.saveOrUpdate(basePdaRoleCfgEntityHi);
//                    totalCount++;
//                }
//            }
//        }

        return result.append("已同步经销商" + accountCount + "条,总部" + totalCount + "条").toString();

    }
}
