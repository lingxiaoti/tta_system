package com.sie.watsons.base.ttaImport.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.ttaImport.model.dao.TtaOiSummaryLineDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaOiSummaryLineEntity_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaOiSummaryLineEntity_HI_MODEL;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaOiSummaryLineEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.inter.ITtaOiSummaryLine;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component("ttaOiSummaryLineServer")
public class TtaOiSummaryLineServer extends BaseCommonServer<TtaOiSummaryLineEntity_HI> implements ITtaOiSummaryLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaBeoiBillLineServer.class);

    @Autowired
    private ViewObject<TtaOiSummaryLineEntity_HI> ttaOiSummaryLineDAO_HI;

    @Autowired
    private TtaOiSummaryLineDAO_HI ttaOiSummaryLineDAOHi ;

    @Autowired
    private redis.clients.jedis.JedisCluster jedisCluster;

    @Autowired
    private BaseViewObject<TtaOiSummaryLineEntity_HI_RO> ttaOiSummaryLineDAO_HI_RO;

    public TtaOiSummaryLineServer() {
        super();
    }

    /**
     * BEOI导入
     * @param queryParamJSON
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public int saveImportOISInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception {
        jedisCluster.setex(sessionBean.getCertificate(),3600,"{status,'U'}");
        JSONArray errList = new JSONArray();
        List<Map<String,Object>> list = null ;
        if(file != null ){
            Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaOiSummaryLineEntity_HI_MODEL.class,0,jedisCluster,sessionBean);
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
        if (list != null) {
            for(int i=0;i<list.size();i++){
                Map<String,Object> json = list.get(i);
                json.put("CREATED_BY",queryParamJSON.getInteger("varUserId")) ;
                json.put("CREATION_DATE",new Date()) ;
                json.put("LAST_UPDATED_BY",queryParamJSON.getInteger("varUserId")) ;
                json.put("LAST_UPDATE_DATE",new Date()) ;
                json.put("LAST_UPDATE_LOGIN",queryParamJSON.getInteger("varUserId")) ;
                json.put("VERSION_NUM",0) ;
                JSONObject errJson = new JSONObject();
                String msgStr = "";
                try {
                    if(SaafToolUtils.isNullOrEmpty(json.get("ACCOUNT_MONTH"))){
                        msgStr += "导入月份的值不能为空";
                    }
                    if(!"".equals(msgStr)){
                        errJson.put("ROW_NUM",json.get("ROW_NUM"));
                        errJson.put("ERR_MESSAGE",msgStr);
                        errList.add(errJson);
                    }
                }catch (Exception e){
                    msgStr += ("有异常,数据有误.");
                    errJson.put("ROW_NUM",json.get("ROW_NUM"));
                    errJson.put("ERR_MESSAGE",msgStr);
                    errList.add(errJson);
                    e.printStackTrace();
                }
            }
        }else {
            JSONObject errJson = new JSONObject();
            errJson.put("ROW_NUM","0");
            errJson.put("ERR_MESSAGE","表头信息错误");
            errList.add(errJson);
        }

        if (!errList.isEmpty()){
            throw new Exception(errList.toJSONString());
        }else{
            ttaOiSummaryLineDAOHi.saveSeqBatchJDBC("TTA_OI_SUMMARY_LINE",list,"TTA_OI_SUMMARY_LINE_ID","SEQ_TTA_OI_SUMMARY_LINE.NEXTVAL",jedisCluster,sessionBean);
            jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'S',currentStage:'完成',orderNum:"+"'无'}");

        }
        return list.size();
    }

    /**
     * BEOI查询
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @Override
    public Pagination<TtaOiSummaryLineEntity_HI_RO> findOISInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
        StringBuffer sql = new StringBuffer(TtaOiSummaryLineEntity_HI_RO.QUERY);
        Map<String,Object> map = new HashMap<String,Object>();
        if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("accountMonth"))){
            if(queryParamJSON.getString("accountMonth").length() == 4){
                SaafToolUtils.parperParam(queryParamJSON, "to_char(tsbl.account_Month,'yyyy')", "accountMonth", sql, map, "=");
            }else{
                SaafToolUtils.parperParam(queryParamJSON, "to_char(tsbl.account_Month,'yyyy-mm')", "accountMonth", sql, map, "=");
            }

        }
        SaafToolUtils.parperHbmParam(TtaOiSummaryLineEntity_HI_RO.class, queryParamJSON, "tsbl.rms_Code", "rmsCode", sql, map, "fulllike");
        SaafToolUtils.parperHbmParam(TtaOiSummaryLineEntity_HI_RO.class, queryParamJSON, "tsbl.vender_Name", "venderName", sql, map, "fulllike");
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " TTA_OI_SUMMARY_LINE_ID desc", false);
        Pagination<TtaOiSummaryLineEntity_HI_RO> resultList =ttaOiSummaryLineDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
        return resultList;
    }

    /**
     * 单条删除或者批量删除
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject deleteImportOISInfo(JSONObject queryParamJSON) throws Exception {
        JSONObject result = new JSONObject();
        if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag"))){
            if("pl".equals(queryParamJSON.getString("flag"))){
                if(queryParamJSON.getString("month")!=null || !"".equals(queryParamJSON.getString("month"))){
                    if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("month"))){
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
                        String path="\\d{4}-\\d{1,2}"; //定义匹配规则
                        Pattern p=Pattern.compile(path);//实例化Pattern
                        Matcher m=p.matcher(queryParamJSON.getString("month"));//验证字符串内容是否合法
                        if(!m.matches()){//使用正则验证
                            throw new IllegalArgumentException("日期不能为空,请重新删除");
                        }
                    }
                    ttaOiSummaryLineDAO_HI.executeSqlUpdate("delete from tta_oi_summary_line t where to_char(nvl(t.account_Month,sysdate),'yyyy-mm') ='" + queryParamJSON.getString("month")+"'");
                }
            }else{
                if(queryParamJSON.getInteger("ttaOiSummaryLineId")!=null || !"".equals(queryParamJSON.getInteger("ttaOiSummaryLineId"))){
                    ttaOiSummaryLineDAO_HI.delete(queryParamJSON.getInteger("ttaOiSummaryLineId"));
                }
            }
        }
        return result;
    }
}
