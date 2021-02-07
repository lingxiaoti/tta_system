package com.sie.watsons.base.ttaImport.model.inter.server;

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
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.ttaImport.model.dao.TtaSupplierItemStoreDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaSupplierItemStoreEntity_HI_MODEL;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaSupplierItemStoreEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.inter.ITtaSupplierItemStore;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.ttaImport.model.entities.TtaSupplierItemStoreEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

@Component("ttaSupplierItemStoreServer")
public class TtaSupplierItemStoreServer extends BaseCommonServer<TtaSupplierItemStoreEntity_HI> implements ITtaSupplierItemStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemStoreServer.class);

    @Autowired
    private ViewObject<TtaSupplierItemStoreEntity_HI> TtaSupplierItemStoreDAO_HI;

    @Autowired
    private TtaSupplierItemStoreDAO_HI TtaSupplierItemStoreDAOHi ;

    @Autowired
    private BaseViewObject<TtaSupplierItemStoreEntity_HI_RO> TtaSupplierItemStoreDAO_HI_RO;

    public TtaSupplierItemStoreServer() {
        super();
    }

    /**
     * 导入
     * @param queryParamJSON
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public int saveImportInfo(JSONObject queryParamJSON, MultipartFile file) throws Exception {
        JSONArray errList = new JSONArray();
        List<Map<String,Object>> list = null ;
        if(file != null ){
            Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaSupplierItemStoreEntity_HI_MODEL.class,0);
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
                    if(SaafToolUtils.isNullOrEmpty(json.get("TRAN_DATE"))){
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
            TtaSupplierItemStoreDAOHi.saveSeqBatchJDBC("TTA_SUPPLIER_ITEM_STORE",list,"MAPPINIG_ID","SEQ_TTA_SUPPLIER_ITEM_STORE.NEXTVAL");
        }
        return list.size();
    }

    /**
     * 查询
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @Override
    public Pagination<TtaSupplierItemStoreEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
        StringBuffer sql = new StringBuffer(TtaSupplierItemStoreEntity_HI_RO.QUERY);
        Map<String,Object> map = new HashMap<String,Object>();
        if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("tranDate"))){
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM" );
            queryParamJSON.put("tranDate",sdf.parse(queryParamJSON.getString("tranDate")));
            SaafToolUtils.parperHbmParam(TtaSupplierItemStoreEntity_HI_RO.class, queryParamJSON, "tbbl.tran_date", "tranDate", sql, map, "=");
        }
        SaafToolUtils.parperHbmParam(TtaSupplierItemStoreEntity_HI_RO.class, queryParamJSON, "tbbl.STORE_CODE", "storeCode", sql, map, "fulllike");
        SaafToolUtils.parperHbmParam(TtaSupplierItemStoreEntity_HI_RO.class, queryParamJSON, "tbbl.ITEM_CODE", "itemCode", sql, map, "fulllike");
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " MAPPINIG_ID desc", false);
        Pagination<TtaSupplierItemStoreEntity_HI_RO> resultList =TtaSupplierItemStoreDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
        return resultList;
    }

    /**
     * 单条删除或者批量删除
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject deleteImportInfo(JSONObject queryParamJSON) throws Exception {
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
                    //TtaSupplierItemStoreDAO_HI.executeSqlUpdate("delete from TTA_SUPPLIER_ITEM_STORE t where to_char(nvl(t.tran_date,sysdate),'yyyy-mm') ='" + queryParamJSON.getString("month")+"'");
                }
            }else{
                if(queryParamJSON.getInteger("mappinigId")!=null || !"".equals(queryParamJSON.getInteger("mappinigId"))){
                    TtaSupplierItemStoreDAO_HI.delete(queryParamJSON.getInteger("mappinigId"));
                }
            }
        }
        return result;
    }
}
