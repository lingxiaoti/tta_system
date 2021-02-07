package com.sie.watsons.base.ttaImport.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.ttaImport.model.dao.TtaAboiBillLineDAO_HI;
import com.sie.watsons.base.ttaImport.model.dao.TtaSroiBillLineDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaAboiBillLineEntity_HI_MODEL;
import com.sie.watsons.base.ttaImport.model.entities.TtaSroiBillLineEntity_HI_MODEL;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaAboiBillLineEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaSroiBillLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.ttaImport.model.entities.TtaAboiBillLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.ttaImport.model.inter.ITtaAboiBillLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

@Component("ttaAboiBillLineServer")
public class TtaAboiBillLineServer extends BaseCommonServer<TtaAboiBillLineEntity_HI> implements ITtaAboiBillLine{
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaAboiBillLineServer.class);

    @Autowired
    private ViewObject<TtaAboiBillLineEntity_HI> TtaAboiBillLineDAO_HI;

    @Autowired
    private TtaAboiBillLineDAO_HI TtaAboiBillLineDAOHi ;

    @Autowired
    private BaseViewObject<TtaAboiBillLineEntity_HI_RO> TtaAboiBillLineDAO_HI_RO;

    public TtaAboiBillLineServer() {
        super();
    }

    /**
     * Aboi导入
     * @param queryParamJSON
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public int saveImportABOIInfo(JSONObject queryParamJSON, MultipartFile file) throws Exception {
        JSONArray errList = new JSONArray();
        List<Map<String,Object>> list = null ;
        if(file != null ){
            Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaAboiBillLineEntity_HI_MODEL.class,0);
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
            TtaAboiBillLineDAOHi.saveSeqBatchJDBC("TTA_ABOI_BILL_LINE",list,"TTA_ABOI_BILL_IMPORT_ID","SEQ_TTA_ABOI_BILL_LINE.NEXTVAL");
        }
        return list.size();
    }

    /**
     * Aboi查询
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @Override
    public Pagination<TtaAboiBillLineEntity_HI_RO> findABOIInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
        StringBuffer sql = new StringBuffer(TtaAboiBillLineEntity_HI_RO.ABOI_QUERY);
        Map<String,Object> map = new HashMap<String,Object>();
        if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("accountMonth"))){
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM" );
            queryParamJSON.put("accountMonth",sdf.parse(queryParamJSON.getString("accountMonth")));
            SaafToolUtils.parperHbmParam(TtaAboiBillLineEntity_HI_RO.class, queryParamJSON, "tbbl.account_Month", "accountMonth", sql, map, "=");
        }
        SaafToolUtils.parperHbmParam(TtaAboiBillLineEntity_HI_RO.class, queryParamJSON, "tbbl.rms_Code", "rmsCode", sql, map, "fulllike");
        SaafToolUtils.parperHbmParam(TtaAboiBillLineEntity_HI_RO.class, queryParamJSON, "tbbl.vender_Name", "venderName", sql, map, "fulllike");
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " TTA_ABOI_BILL_IMPORT_ID desc", false);
        Pagination<TtaAboiBillLineEntity_HI_RO> resultList =TtaAboiBillLineDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
        return resultList;
    }

    /**
     * 单条删除或者批量删除
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject deleteImportABOIInfo(JSONObject queryParamJSON) throws Exception {
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
                    TtaAboiBillLineDAO_HI.executeSqlUpdate("delete from TTA_ABOI_bill_line t where to_char(nvl(t.account_Month,sysdate),'yyyy-mm') ='" + queryParamJSON.getString("month")+"'");
                }
            }else{
                if(queryParamJSON.getInteger("ttaAboiBillImportId")!=null || !"".equals(queryParamJSON.getInteger("ttaAboiBillImportId"))){
                    TtaAboiBillLineDAO_HI.delete(queryParamJSON.getInteger("ttaAboiBillImportId"));
                }
            }
        }
        return result;
    }
}
