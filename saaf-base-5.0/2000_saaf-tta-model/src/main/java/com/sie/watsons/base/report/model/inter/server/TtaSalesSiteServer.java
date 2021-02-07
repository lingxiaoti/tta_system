package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.TtaSalesSiteDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaSalesSiteEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaSalesSiteEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.readonly.TtaSalesSiteEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaSalesSite;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/16/016.
 */
@Component("ttaSalesSiteServer")
public class TtaSalesSiteServer extends BaseCommonServer<TtaSalesSiteEntity_HI> implements ITtaSalesSite {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaSalesSiteServer.class);

    @Autowired
    private ViewObject<TtaSalesSiteEntity_HI> TtaSalesSiteDAO_HI;

    @Autowired
    private BaseViewObject<TtaSalesSiteEntity_HI_RO> TtaSalesSiteDAO_HI_RO;

    @Autowired
    private TtaSalesSiteDAO_HI ttaSalesSiteDAO ;

    @Autowired
    private redis.clients.jedis.JedisCluster jedisCluster;

    public TtaSalesSiteServer() {
        super();
    }



    /**
     * OI批量导入
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    public int saveImportOsdInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
        jedisCluster.setex(sessionBean.getCertificate(),3600,"{status,'U'}");
        JSONArray errList = new JSONArray();
        List<Map<String,Object>> list = null ;
        if(file != null ){
            Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaSalesSiteEntity_HI_MODEL.class,0,jedisCluster,sessionBean);
            Boolean flag = (Boolean) result.get("flag");
            if(flag){
                list = (List<Map<String,Object>>) result.get("datas");
            }else{
                JSONObject errJson2 = new JSONObject();
                errJson2.put("ROW_NUM",'0');
                errJson2.put("ERR_MESSAGE","表头信息错误");
                errList.add(errJson2) ;
            }
        }

        for(int i=0;i<list.size();i++){
            Map<String,Object> json = list.get(i);
            JSONObject errJson = new JSONObject();
            json.put("CREATED_BY",queryParamJSON.getInteger("varUserId")) ;
            json.put("CREATION_DATE",new Date()) ;
            json.put("LAST_UPDATED_BY",queryParamJSON.getInteger("varUserId")) ;
            json.put("LAST_UPDATE_DATE",new Date()) ;
            json.put("LAST_UPDATE_LOGIN",queryParamJSON.getInteger("varUserId")) ;
            json.put("VERSION_NUM",0) ;
            String msgStr = "";
            try {
                if(!"".equals(msgStr)){
                    errJson.put("ROW_NUM",json.get("ROW_NUM"));
                    errJson.put("ERR_MESSAGE",msgStr);
                    errList.add(errJson);
                }else{
                    //	json.put("operatorUserId",queryParamJSON.get("operatorUserId"));
                    //	super.saveOrUpdate(json);
                }
            }catch (Exception e){
                msgStr += ("有异常,数据有误.");
                errJson.put("ROW_NUM",json.get("ROW_NUM"));
                errJson.put("ERR_MESSAGE",msgStr);
                errList.add(errJson);
                e.printStackTrace();
            }
        }
        if (!errList.isEmpty()){
            throw new Exception(errList.toJSONString());
        }else{
            ttaSalesSiteDAO.saveSeqBatchJDBC("TTA_OSD_SALES_SITE",list,"SALES_SITE_ID","SEQ_TTA_OSD_SALES_SITE.NEXTVAL",jedisCluster,sessionBean);
            jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'S',currentStage:'完成',orderNum:"+"'无'}");

        }
        return list.size();
    }

    /**
     *
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @Override
    public Pagination<TtaSalesSiteEntity_HI_RO> findOsdInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
        StringBuffer sql = new StringBuffer();
        Map<String,Object> map = new HashMap<String,Object>();

        sql.append(TtaSalesSiteEntity_HI_RO.QUERY) ;
        SaafToolUtils.parperParam(queryParamJSON, "toss.sales_year", "salesYear", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "toss.sales_site", "salesSite", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "toss.DEPT", "dept", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "toss.SUPPLIER_CODE", "supplierCode", sql, map, "fulllike");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "toss.sales_site_id desc", false);
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");

        Pagination<TtaSalesSiteEntity_HI_RO> resultList =TtaSalesSiteDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
        return resultList;
    }

    /**
     *
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    public List<TtaSalesSiteEntity_HI_RO> findOsdInfoOne(JSONObject queryParamJSON) throws Exception{
        StringBuffer sql = new StringBuffer();
        Map<String,Object> map = new HashMap<String,Object>();
        sql.append(TtaSalesSiteEntity_HI_RO.QUERY) ;
        SaafToolUtils.parperParam(queryParamJSON, "tpsl.promotion_section", "promotionSection", sql, map, "=");
        sql.append(" and rownum =1") ;
        List<TtaSalesSiteEntity_HI_RO> list = TtaSalesSiteDAO_HI_RO.findList(sql, map);
        return list;
    }

    /**
     *
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    public JSONObject deleteImportOsdInfo(JSONObject queryParamJSON) throws Exception{
        JSONObject result = new JSONObject();
        if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag"))){
            if("pl".equals(queryParamJSON.getString("flag"))){
                if(queryParamJSON.getString("year")!=null || !"".equals(queryParamJSON.getString("year"))){
                    StringBuffer countSql = new StringBuffer("select count(*) from TtaSalesSiteEntity_HI ts " +
                            "where ts.salesYear = :salesYear");
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("salesYear",queryParamJSON.getInteger("year")) ;
                    Integer count = TtaSalesSiteDAO_HI.count(countSql, map);
                    if(count.intValue() == 0){
                        throw new IllegalArgumentException("当前年份不存在,删除失败");
                    }
                    TtaSalesSiteDAO_HI.executeSqlUpdate("delete from tta_osd_sales_site t where t.sales_year =" + queryParamJSON.getInteger("year")+"");
                }
            }else{
                if(queryParamJSON.getInteger("salesSiteId")!=null || !"".equals(queryParamJSON.getInteger("salesSiteId"))){
                    TtaSalesSiteDAO_HI.delete(queryParamJSON.getInteger("salesSiteId"));
                }
            }
        }
        return result;
    }

    @Override
    public TtaSalesSiteEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        TtaSalesSiteEntity_HI instance = SaafToolUtils.setEntity(TtaSalesSiteEntity_HI.class, paramsJSON, TtaSalesSiteDAO_HI, userId);
        TtaSalesSiteDAO_HI.saveOrUpdate(instance);
        return instance;
    }

}

