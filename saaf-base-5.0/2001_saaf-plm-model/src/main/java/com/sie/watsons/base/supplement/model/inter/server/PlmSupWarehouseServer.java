package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.api.model.inter.server.PlmApiServer;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierInfoEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierplaceinfoEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductHead;
import com.sie.watsons.base.product.model.inter.IPlmProductSupplierInfo;
import com.sie.watsons.base.product.model.inter.IPlmProductSupplierplaceinfo;
import com.sie.watsons.base.product.model.inter.server.X509TrustUtiil;
import com.sie.watsons.base.redisUtil.ResultConstant;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.sie.watsons.base.supplement.model.entities.PlmSupLogEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupWarehouseEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupShopEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupWarehouseEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineWareEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.IPlmSupWarehouse;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementHead;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementLine;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("plmSupWarehouseServer")
public class PlmSupWarehouseServer extends BaseCommonServer<PlmSupWarehouseEntity_HI> implements IPlmSupWarehouse {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmApiServer.class);
    @Autowired
    private ViewObject<PlmSupWarehouseEntity_HI> plmSupWarehouseDAO_HI;
    @Autowired
    private BaseViewObject<PlmSupWarehouseEntity_HI_RO> plmSupWarehouseDAO_HI_RO;
    @Autowired
    IPlmSupplementHead plmSupplementHead;
    @Autowired
    private ViewObject<PlmSupLogEntity_HI> plmSupLogDAO_HI;
    @Autowired
    private IPlmProductHead plmProductHeadServer;
    @Autowired
    private IPlmSupplementLine plmSupplementLineServer;
    @Autowired
    private IPlmProductSupplierInfo plmProductSupplierInfo;
    @Autowired
    private IPlmProductSupplierplaceinfo plmProductSupplierplaceinfo;
    @Autowired
    private BaseViewObject<PlmSupShopEntity_HI_RO> plmSupShopDAO_HI_RO;
    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Autowired
    private redis.clients.jedis.JedisCluster jedis;

    @Autowired
    private IFastdfs fastdfsServer;

    public static Set<String> WC_SET;
    static {
        WC_SET = new HashSet<>();
        WC_SET.add("9901");
        WC_SET.add("9902");
        WC_SET.add("9903");
        WC_SET.add("9906");
        WC_SET.add("9907");
        WC_SET.add("9908");
        WC_SET.add("991");
        WC_SET.add("992");
        WC_SET.add("994");
        WC_SET.add("995");
        WC_SET.add("996");
        WC_SET.add("997");
        WC_SET.add("998");
        WC_SET.add("999");
    }


    public PlmSupWarehouseServer() {
        super();
    }

    @Override
    public List<PlmSupWarehouseEntity_HI> findPlmSupWarehouseInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<PlmSupWarehouseEntity_HI> findListResult = plmSupWarehouseDAO_HI.findList("from PlmSupWarehouseEntity_HI order by plmSupWarehouseId ", queryParamMap);
        return findListResult;
    }

    @Override
    public Object savePlmSupWarehouseInfo(JSONObject queryParamJSON) {
        PlmSupWarehouseEntity_HI plmSupWarehouseEntity_HI = JSON.parseObject(queryParamJSON.toString(), PlmSupWarehouseEntity_HI.class);
        Object resultData = plmSupWarehouseDAO_HI.save(plmSupWarehouseEntity_HI);
        return resultData;
    }

    @Override
    public String saveOrUpdatePlmSupWarehouse(JSONObject queryParamJSON) {
        PlmSupWarehouseEntity_HI plmSupWarehouseEntity_HI = JSON.parseObject(queryParamJSON.toString(), PlmSupWarehouseEntity_HI.class);
        plmSupWarehouseDAO_HI.saveOrUpdate(plmSupWarehouseEntity_HI);
//        JSONObject result = new JSONObject();
//        result.put("data",plmSupWarehouseEntity_HI);
        return JSONObject.toJSONString(plmSupWarehouseEntity_HI);
    }


    @Override
    public Pagination<PlmSupWarehouseEntity_HI_RO> findPlmSupWarehouseInfo(JSONObject queryParamJSON, Integer pageIndex,
                                                                           Integer pageRows) {
        StringBuffer sql = new StringBuffer(PlmSupWarehouseEntity_HI_RO.SQL_BASE);
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        String isOb = queryParamJSON.getString("isOb");
        String sesionProduct = queryParamJSON.getString("sesionProduct");
        String state = queryParamJSON.getString("state");
        setDefaultDate(queryParamJSON, "orderUpdateDateStart", "orderUpdateDateEnd");
        String orderUpdateDateStart = queryParamJSON.getString("orderUpdateDateStart");
        String orderUpdateDateEnd = queryParamJSON.getString("orderUpdateDateEnd");
        if (orderUpdateDateStart != null && orderUpdateDateEnd != null) {
            sql.append(" and to_char(psw.order_update_date,'yyyy-mm-dd') between '" + orderUpdateDateStart + "' and '" + orderUpdateDateEnd + "' ");
            queryParamJSON.remove("orderUpdateDate");
        }
        setDefaultDate(queryParamJSON, "lastUpdateDateStart", "lastUpdateDateEnd");
        String lastUpdateDateStart = queryParamJSON.getString("lastUpdateDateStart");
        String lastUpdateDateEnd = queryParamJSON.getString("lastUpdateDateEnd");
        if (lastUpdateDateStart != null && lastUpdateDateEnd != null) {
            sql.append(" and to_char(psw.last_update_date,'yyyy-mm-dd') between '" + lastUpdateDateStart + "' and '" + lastUpdateDateEnd + "' ");
            queryParamJSON.remove("lastUpdateDate");
        }
        SaafToolUtils.parperHbmParam(PlmSupWarehouseEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
        if (isOb != null) {
            if ("Y".equals(isOb)) {
                sql.append(" and (PRH.ob_id is not null or PRH.product_type=4 or PRH.product_type=1) ");
            }
            if ("N".equals(isOb)) {
//                sql.append(" and (PRH.ob_id is null or PRH.product_type!=4 or PRH.product_type!=1) ");
                sql.append(" and (PRH.ob_id is null and (PRH.product_type!=4 and PRH.product_type!=1)) ");
            }
        }
        if (!"".equals(sesionProduct) && sesionProduct != null) {
            sql.append(" and PRH.sesion_Product = '" + sesionProduct + "' ");
        }
        if (!"".equals(state) && state != null) {
            sql.append(" and PSW.state = '" + state + "' ");
        }

        //sql.append(" order by PSW.plm_sup_warehouse_id ");
        LOGGER.info("Ready to select now.................");
        Pagination<PlmSupWarehouseEntity_HI_RO> findListResult = plmSupWarehouseDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
                queryParamMap, pageIndex, pageRows);
        LOGGER.info("Select done.................");
        return findListResult;
    }

    private void setDefaultDate(JSONObject queryParamJSON, String startDate, String endDate) {
        String s = queryParamJSON.getString(startDate);
        String e = queryParamJSON.getString(endDate);

        if (s != null || e != null) {
            if (s == null) {
                s = "2000-01-01";
                queryParamJSON.put(startDate, s);
            }
            if (e == null) {
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String n = sdf.format(now);
                e = n;
                queryParamJSON.put(endDate, e);
            }
        }
    }

    @Override
    public PlmSupWarehouseEntity_HI_RO findPlmSupWarehouseInfoById(JSONObject queryParamJSON) throws Exception {
        StringBuffer sql = new StringBuffer(PlmSupWarehouseEntity_HI_RO.SQL_BASE);
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        Integer plmSupWarehouseId = queryParamJSON.getInteger("plmSupWarehouseId");
        SaafToolUtils.parperHbmParam(PlmSupWarehouseEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
        queryParamMap.put("plmSupWarehouseId", plmSupWarehouseId);
//        if(null!=plmSupWarehouseId){
//            sql.append(" PSW.PLM_SUP_WAREHOUSE_ID = '"+plmSupWarehouseId+"' ");
//        }else {
//            throw new Exception("请传入头id");
//        }
        sql.append(" order by PSW.last_update_date desc");
        List<PlmSupWarehouseEntity_HI_RO> findListResult = plmSupWarehouseDAO_HI_RO.findList(sql, queryParamMap);
        if (CollectionUtils.isEmpty(findListResult)) {
            return new PlmSupWarehouseEntity_HI_RO();
        }
        return findListResult.get(0);
    }

    @Override
    public JSONObject saveWarehouseByExcel(JSONObject param) {
        String filepath = param.getString("filepath");
        File file = this.getNetUrl(filepath);
        Map<String, PlmProductHeadEntity_HI> headlist = new HashMap<String, PlmProductHeadEntity_HI>();
        Map<String, String> supplierli = new HashMap<String, String>();
        try {
            InputStream is = new FileInputStream(file);
//			Workbook wb = new HSSFWorkbook(is);
            Workbook wb = new XSSFWorkbook(is);
            Sheet head = wb.getSheetAt(0); // 头信息

            int rownum = head.getLastRowNum(); // 总行数
            for (int i = 1; i <= rownum; i++) {
                Row curent = head.getRow(i);

                XSSFCell DC = (XSSFCell) curent.getCell(0); // 渠道
                XSSFCell supplierCode = (XSSFCell) curent.getCell(1); // 渠道
                XSSFCell productCode = (XSSFCell) curent.getCell(2); // 渠道

                XSSFCell storeWay = (XSSFCell) curent.getCell(3); // 渠道
                XSSFCell exploRevDate = (XSSFCell) curent.getCell(4); // 渠道
                XSSFCell supDelDate = (XSSFCell) curent.getCell(5); // 渠道
                XSSFCell orderFreq = (XSSFCell) curent.getCell(6); // 渠道
                XSSFCell orderDate = (XSSFCell) curent.getCell(7); // 渠道
                XSSFCell warRevDate = (XSSFCell) curent.getCell(8); // 渠道
                Pagination<PlmProductHeadEntity_HI_RO> pagination = plmProductHeadServer.findProductList2(param, 1, 1);
                if (pagination.getData().size() == 0) {
                }
                PlmSupWarehouseEntity_HI wc = new PlmSupWarehouseEntity_HI();
                wc.setWarehouseCode(this.getStringCellValue(DC));
                wc.setSupplierCode(this.getStringCellValue(supplierCode));
                wc.setProductCode(this.getStringCellValue(productCode));
                wc.setStoreWay(this.getStringCellValue(storeWay));
                wc.setExploRevDate(this.getStringCellValue(exploRevDate));
                wc.setSupDelDate(this.getStringCellValue(supDelDate));
                wc.setOrderFreq(this.getStringCellValue(orderFreq));
                wc.setOrderDate(this.getStringCellValue(orderDate));
                wc.setWarRevDate(this.getStringCellValue(warRevDate));
                wc.setState("0");

                JSONObject paramname = new JSONObject();
                paramname.put("warehouseCode", wc.getWarehouseCode());
                paramname.put("productCode", wc.getProductCode());
                List<PlmSupWarehouseEntity_HI> exl = getExWarehouses(paramname);
                paramname.put("wc", wc.getWarehouseCode());
                paramname.put("item", wc.getProductCode());
                List<PlmSupShopEntity_HI_RO> shops = plmSupplementHead.getShops(paramname);
                int total = calculate(shops);
                List<PlmSupShopEntity_HI_RO> exShops = plmSupplementHead.getExShops(paramname);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        JSONArray errArray = new JSONArray();
        List<String> list = new ArrayList<>();
        list.add(1 + "-" + "991" + "=" + "100193603");
        list.add(2 + "-" + "991" + "=" + "100193603");
        list.add(3 + "-" + "991" + "=" + "100193603");
        list.add(4 + "-" + "996" + "=" + "100193603");
        list.add(5 + "-" + "999" + "=" + "100193603");
        list.add(6 + "-" + "999" + "=" + "100193603");
        String sta = "false";
        for (int i = 0; i < list.size(); i++) {
            for (int k = 0; k < list.size(); k++) {
                if (list.get(i).split("-")[1].equals(list.get(k).split("-")[1]) && i != k) {
                    sta = "true";
                }
            }
            if (sta.equals("true")) {
                String errMsg = "仓库:" + list.get(i).split("-")[1].split("=")[0] + ",货号:" + list.get(i).split("-")[1].split("=")[1] + ",数据存在重复！";
                JSONObject errRow = new JSONObject();
                errRow.put("ROW_NUM", Integer.parseInt(list.get(i).split("-")[0]));
                errRow.put("ERR_MESSAGE", errMsg);
                errArray.add(errRow);
                sta = "false";
                continue;
            }
        }
        if (errArray.size() != 0) {
//            throw new Exception(errArray.toJSONString());
        }
    }

    @Override
    public Object saveAndImportWarehouse2(JSONObject queryParamJSON) {
        queryParamJSON.put("uploadStatus", "N");
        queryParamJSON.put("key", generateCodeServer.generateCode("UPLDB", new SimpleDateFormat(
                "yyyyMMdd"), 4));
        jedis.set(queryParamJSON.getString("key"), queryParamJSON.toJSONString());

        return queryParamJSON;
    }

    @Override
    public JSONObject saveAndImportWarehouse(JSONObject queryParamJSON) throws Exception {
//        JSONArray dataArray = queryParamJSON.getJSONArray("data");
//        JSONObject infoObject = queryParamJSON.getJSONObject("info");

        JSONArray returnArray = new JSONArray();
        JSONArray errArray = new JSONArray();
        PlmSupWarehouseEntity_HI wc = null;

        //第一轮导入数据重复性校验
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < dataArray.size(); i++) {
//            JSONObject data = dataArray.getJSONObject(i);
//
//            String warehouseCode = data.getString("warehouseCode").trim();
//            String productCode = data.getString("productCode").trim();
//            list.add((i+1)+"-"+warehouseCode+"="+productCode);
//        }
//        String sta = "false";
//        for(int i = 0; i <list.size();i++){
//            for(int k = 0; k <list.size();k++){
//                if(list.get(i).split("-")[1].equals(list.get(k).split("-")[1])&&i!=k){
//                    sta = "true";
//                }
//            }
//            if(sta.equals("true")){
//                String   errMsg = "仓库:"+list.get(i).split("-")[1].split("=")[0]+",货号:"+list.get(i).split("-")[1].split("=")[1]+",数据存在重复！";
//                JSONObject errRow = new JSONObject();
//                errRow.put("ERR_MESSAGE", errMsg);
//                errRow.put("ROW_NUM",list.get(i).split("-")[0]);
//                errArray.add(errRow);
//                sta = "false";
//                continue;
//            }
//        }
//        if(errArray.size()!=0){
//            throw new Exception(errArray.toJSONString());
//        }

//        //第二轮数据库重复性校验
//        for (int i = 0; i < dataArray.size(); i++) {
//            String errMsg = "";
//            String msg = "";
//            JSONObject data = dataArray.getJSONObject(i);
//
//            String warehouseCode = data.getString("warehouseCode").trim();
//            String productCode = data.getString("productCode").trim();
//            String sql = "select * from PLM_SUP_WAREHOUSE w where w.WAREHOUSE_CODE ='"+warehouseCode+"' " +
//                    "and w.PRODUCT_CODE ='"+productCode+"'";
//            List<PlmSupWarehouseEntity_HI_RO> list= plmSupWarehouseDAO_HI_RO.findList(sql);
//            if(list.size()>0){
//                msg = "仓库:"+warehouseCode+",货号:"+productCode+",";
//            }
//            if(msg.indexOf("货号")>0){
//                errMsg = msg+"与数据库数据重复！";
//                JSONObject errRow = new JSONObject();
//                errRow.put("ROW_NUM",data.get("ROW_NUM"));
//                errRow.put("ERR_MESSAGE", errMsg);
//                errArray.add(errRow);
//                continue;
//            }
//        }
//        if(errArray.size()!=0){
//            throw new Exception(errArray.toJSONString());
//        }
        Map<String, String> map = new HashMap<>();
        String filepath = queryParamJSON.getString("filepath");
        File file = this.getNetUrl(filepath);
//        File file = new File(filepath);
        try {
            InputStream is = new FileInputStream(file);
            Workbook wb = new XSSFWorkbook(is);
            Sheet head = wb.getSheetAt(0); // 头信息
            int rownum = head.getLastRowNum(); // 总行数
            if (queryParamJSON.getString("filepath") == null) {
//                for (int i = 1; i <= rownum; i++) {
//                    Row curent = head.getRow(i);
//
//                    XSSFCell DC = (XSSFCell) curent.getCell(0); // 渠道
//                    DC.setCellType(CellType.STRING);
//                    XSSFCell supplierCo = (XSSFCell) curent.getCell(1); // 渠道
//                    XSSFCell productCo = (XSSFCell) curent.getCell(2); // 渠道
//                    productCo.setCellType(CellType.STRING);
//
//                    XSSFCell exploRevDa = (XSSFCell) curent.getCell(3); // 渠道
//                    XSSFCell supDelDa = (XSSFCell) curent.getCell(4); // 渠道
//                    XSSFCell orderFr = (XSSFCell) curent.getCell(5); // 渠道
//                    XSSFCell orderDa = (XSSFCell) curent.getCell(6); // 渠道
//                    XSSFCell warRevDa = (XSSFCell) curent.getCell(7); // 渠道
//                    JSONObject paramname = new JSONObject();
//                    paramname.put("warehouseCode", DC);
//                    paramname.put("productCode", productCo);
//                    List<PlmSupWarehouseEntity_HI> exl = getExWarehouses(paramname);
//                    if (exl.size() > 0) {
//                        PlmSupWarehouseEntity_HI en = exl.get(0);
//                        plmSupWarehouseDAO_HI.delete(en);
//                    }
//                }
            } else {
                for (int i = 1; i <= rownum; i++) {
                    Row curent = head.getRow(i);


                    XSSFCell DC = (XSSFCell) curent.getCell(0); // 渠道
                    if (DC != null) {
                        DC.setCellType(CellType.STRING);
                    }
                    XSSFCell supplierCo = (XSSFCell) curent.getCell(1); // 渠道
                    if (supplierCo != null) {
                        supplierCo.setCellType(CellType.STRING);
                    }
                    XSSFCell productCo = (XSSFCell) curent.getCell(2); // 渠道
                    if (productCo != null) {
                        productCo.setCellType(CellType.STRING);
                    }
                    XSSFCell exploRevDa = (XSSFCell) curent.getCell(3); // 渠道
                    if (exploRevDa != null) {
                        exploRevDa.setCellType(CellType.STRING);
                    }
                    XSSFCell supDelDa = (XSSFCell) curent.getCell(4); // 渠道
                    if (supDelDa != null) {
                        supDelDa.setCellType(CellType.STRING);
                    }
                    XSSFCell orderFr = (XSSFCell) curent.getCell(5); // 渠道
                    if (orderFr != null) {
                        orderFr.setCellType(CellType.STRING);
                    }
                    XSSFCell orderDa = (XSSFCell) curent.getCell(6); // 渠道
                    if (orderDa != null) {
                        orderDa.setCellType(CellType.STRING);
                    }
                    XSSFCell warRevDa = (XSSFCell) curent.getCell(7); // 渠道
                    if (warRevDa != null) {
                        warRevDa.setCellType(CellType.STRING);
                    }
                    JSONObject data = new JSONObject();
                    data.put("warehouseCode", DC);
                    data.put("supplierCode", supplierCo);
                    data.put("productCode", productCo);
                    data.put("exploRevDate", exploRevDa);
                    data.put("supDelDate", supDelDa);
                    data.put("orderFreq", orderFr);
                    data.put("orderDate", orderDa);
                    data.put("warRevDate", warRevDa);

                    String packSize = null;
                    String errMsg = "";
                    String msg = "";
                    String supplierName = null;
//                JSONObject data = dataArray.getJSONObject(i);


                    if (SaafToolUtils.isNullOrEmpty(DC) || SaafToolUtils.isNullOrEmpty(supplierCo) || SaafToolUtils.isNullOrEmpty(productCo)
                    || SaafToolUtils.isNullOrEmpty(exploRevDa) || SaafToolUtils.isNullOrEmpty(supDelDa) ||SaafToolUtils.isNullOrEmpty(orderFr)
                    || SaafToolUtils.isNullOrEmpty(orderDa) || SaafToolUtils.isNullOrEmpty(warRevDa)) {
                        JSONObject errRow = new JSONObject();
                        errMsg = "请把必填数据填写完整！";
                        errRow.put("ERR_MESSAGE", errMsg);
                        errRow.put("ROW_NUM", data.get("ROW_NUM"));
                        errRow.put("warehouseCode", DC);
                        errRow.put("supplierCode", supplierCo);
                        errRow.put("productCode", productCo);
                        errRow.put("exploRevDate", exploRevDa);
                        errRow.put("supDelDate", supDelDa);
                        errRow.put("orderFreq", orderFr);
                        errRow.put("orderDate", orderDa);
                        errRow.put("warRevDate", warRevDa);
                        errArray.add(errRow);
                        continue;
                    }
                    String warehouseCode = data.getString("warehouseCode").trim();
                    String productCode = data.getString("productCode").trim();
                    if (map.get(warehouseCode + productCode) != null) {
                        errMsg += "仓库:" + warehouseCode + ",货号:" + productCode + ",数据存在重复；";
//                JSONObject errRow = new JSONObject();
//                errRow.put("ERR_MESSAGE", errMsg);
//                errRow.put("ROW_NUM", data.get("ROW_NUM"));
//                errArray.add(errRow);
//                continue;
                    }

                    map.put(warehouseCode + productCode, warehouseCode + productCode);

                    wc = new PlmSupWarehouseEntity_HI();
                    if (SaafToolUtils.isNullOrEmpty(data.getString("productCode"))) {
                        throw new IllegalArgumentException("货号未输入！");
                    }
//			else if (SaafToolUtils.isNullOrEmpty(data.getString("exeDate"))) {
//				throw new IllegalArgumentException("失效/生效时间未输入！");
//			}
                    else {
                        JSONObject param = new JSONObject();
                        param.put("rmsCode", data.getString("productCode").trim());
                        StringBuffer sql = null;
                        String areaList = "";
                        JSONObject queryJSON = new JSONObject();
                        queryJSON.put("rmsId", data.getString("productCode").trim());
                        Pagination<PlmSupplementLineWareEntity_HI_RO> ware = plmSupplementLineServer.findPlmSupplementLineWare(queryJSON, 1, 10000);
                        Boolean statu = false;
                        for (int j = 0; j < ware.getData().size(); j++) {
                            areaList = areaList + "," + ware.getData().get(j).getWareCode();
                            if (warehouseCode != null && warehouseCode.equals(ware.getData().get(j).getWareCode())) {
                                statu = true;
                            }
                        }

                        if (!statu) {
                            errMsg += "仓库：" + warehouseCode + "未找到；";
                        }

//                        Integer deptId = queryParamJSON.getInteger("userDept");
//                param.put("userDept", deptId);
//                param.put("orderStatus", "6");
//                Pagination<PlmProductHeadEntity_HI> pagination = plmProductHeadServer.findProductList2(param, 1, 1);
                        Pagination<PlmProductHeadEntity_HI> pagination = findProductList2(param);
                        LOGGER.info("======================product list size:{}=================", pagination.getData().size());
                        if (pagination != null) {
                            if (pagination.getData().size() == 0) {
                                errMsg += "货号：" + data.getString("productCode").trim() + "未找到；";
                                JSONObject errRow = new JSONObject();
                                errRow.put("ERR_MESSAGE", errMsg);
                                errRow.put("ROW_NUM", data.get("ROW_NUM"));
                                errRow.put("warehouseCode", DC);
                                errRow.put("supplierCode", supplierCo);
                                errRow.put("productCode", productCo);
                                errRow.put("exploRevDate", exploRevDa);
                                errRow.put("supDelDate", supDelDa);
                                errRow.put("orderFreq", orderFr);
                                errRow.put("orderDate", orderDa);
                                errRow.put("warRevDate", warRevDa);
                                errArray.add(errRow);
                                continue;
                            }
                        } else {
                            continue;
                        }
                        param.remove("rmsCode");
                        param.remove("orderStatus");
                        Integer productHeadId = pagination.getData().get(0).getProductHeadId();
                        LOGGER.info("======================product code:{}=================", pagination.getData().get(0).getRmsCode());
                        param.put("productHeadId", productHeadId);
                        param.put("supplierCode", data.getString("supplierCode").trim());
                        String status = "0";
                        List<PlmProductSupplierInfoEntity_HI> entityList = plmProductSupplierInfo.findList(param);
//                JSONObject wcparams = new JSONObject();
//                wcparams.put("locType", "W");
//                wcparams.put("location", warehouseCode);
//                wcparams.put("rmsId", productCode);
//                wcparams.put("supplierCode", data.getString("supplierCode"));
//                List<PlmProductSupplierplaceinfoEntity_HI> wcList = plmProductSupplierplaceinfo.findList(wcparams);
//                if (wcList == null || wcList.size() == 0) {
//                    errMsg += "供应商：" + data.getString("supplierCode").trim() + "没有对应仓库：" + warehouseCode + "，对应货品：" + productCode + "的权限";
//                    JSONObject errRow = new JSONObject();
//                    errRow.put("ERR_MESSAGE", errMsg);
//                    errRow.put("ROW_NUM", data.get("ROW_NUM"));
//                    errArray.add(errRow);
//                    continue;
//                }
                        LOGGER.info("begin======================supplier list size:{}=================", entityList.size());
                        if (entityList != null && entityList.size() > 0) {
                            for (int k = 0; k < entityList.size(); k++) {
                                if (entityList.get(k).getSupplierCode().equals(data.getString("supplierCode").trim())) {
                                    supplierName = entityList.get(k).getSupplierName();
                                    status = "1";
                                }
                            }
                        }
                        LOGGER.info("end======================status:{}=================", status);
                        if ("0".equals(status)) {
                            errMsg += "供应商：" + data.getString("supplierCode").trim() + "未找到；";
//                    JSONObject errRow = new JSONObject();
//                    errRow.put("ERR_MESSAGE", errMsg);
//                    errRow.put("ROW_NUM", data.get("ROW_NUM"));
//                    errArray.add(errRow);
//                    continue;
                        } else {
                            Integer tmp = entityList.get(0).getPackageSpe();
//                            try {
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                            if (tmp != null) {
                                packSize = tmp.toString();
                                LOGGER.info("end======================pack size:{}=================", packSize);
                            } else {
                                LOGGER.info("end======================pack size:{}=================", tmp);
                            }
                        }
                        LOGGER.info("end======================supplier list size:{}=================", entityList.size());
//				product = pagination.getData().get(0);
                    }
//			data.put("plmDevelopmentInfoId", plmDevelopmentInfoId);
//			data.put("plmProjectId", plmProjectId);
                    wc.setWarehouseCode(data.getString("warehouseCode"));
                    wc.setSupplierCode(data.getString("supplierCode"));
                    wc.setProductCode(data.getString("productCode"));
                    ResultUtils.getLookUpValue("PLM_SUP_STORE_TYPE");
//            JSONObject storeType = ResultConstant.PLM_SUP_STORE_TYPE;
                    //文本格式不做校验
//            String storeTypeKey = getKey(storeType, data.getString("storeWay").trim());
//            wc.setStoreWay(storeTypeKey);//易燃易爆收货
                    //纯数字，多个数字用|分割
                    String numStr = "";
                    if (data.getString("supDelDate") == null || data.getString("supDelDate").equals("")) {
                        errMsg += "请填供应链送货LT；";
                    } else {
                        numStr = data.getString("supDelDate").trim();
                        String numFormatStr = "";
                        if (isNum(numStr)) {
                            numFormatStr = numStr;
                        } else {
                            errMsg += "供应链送货LT只能是整数；";
//                JSONObject errRow = new JSONObject();
//                errRow.put("ERR_MESSAGE", errMsg);
//                errRow.put("ROW_NUM", data.get("ROW_NUM"));
//                errArray.add(errRow);
//                continue;
                        }
                    }
                    wc.setSupDelDate(numStr);
//                    wc.setExploRevDate(numFormatStr);//供应链送货
                    //只能纯数字且不能大于七位数
                    String supStr = data.getString("orderDate").trim();

                    if (checkDate(supStr).equals("false")) {
                        errMsg += "订货日应为1|2|3|4|5|6|7格式的子集；";
//                JSONObject errRow = new JSONObject();
//                errRow.put("ERR_MESSAGE", errMsg);
//                errRow.put("ROW_NUM", data.get("ROW_NUM"));
//                errArray.add(errRow);
//                continue;
                    }
//			wc.setSupDelDate(supStr);//订货日
                    wc.setOrderDate(checkDate(supStr));

                    //文本格式不做校验
                    wc.setOrderFreq(data.getString("orderFreq"));//订货频率
                    String orderFreq = data.getString("orderFreq").trim();
                    if (!isOnlyNum(orderFreq)) {
                        errMsg += "订货频率只能1-7的数字；";
//                JSONObject errRow = new JSONObject();
//                errRow.put("ERR_MESSAGE", errMsg);
//                errRow.put("ROW_NUM", data.get("ROW_NUM"));
//                errArray.add(errRow);
//                continue;
                    }
                    String orderStr = data.getString("warRevDate").trim();

                    if (checkDate(orderStr).equals("false")) {
                        errMsg += "供应商送货到仓日应为1|2|3|4|5|6|7格式的子集；";
//                JSONObject errRow = new JSONObject();
//                errRow.put("ERR_MESSAGE", errMsg);
//                errRow.put("ROW_NUM", data.get("ROW_NUM"));
//                errArray.add(errRow);
//                continue;
                    }
//			wc.setOrderDate(orderStr);
                    wc.setWarRevDate(checkDate(orderStr));
//			wc.setWarRevDate(data.getString("warRevDate"));
                    wc.setSupplierName(supplierName);
                    wc.setPackSize(packSize);
                    if (!data.getString("exploRevDate").equals("") && data.getString("exploRevDate") != null) {
                        String exploRevDate = data.getString("exploRevDate").trim();
                        if (checkDate2(exploRevDate).equals("false")) {
                            errMsg += "易燃易爆日应为0|1|2|3|4|5|6|7格式的子集；";
                        }
                    }
                    if (!"".equals(errMsg)) {
                        JSONObject errRow = new JSONObject();
                        errRow.put("ERR_MESSAGE", errMsg);
                        errRow.put("ROW_NUM", data.get("ROW_NUM"));
                        errRow.put("warehouseCode", DC);
                        errRow.put("supplierCode", supplierCo);
                        errRow.put("productCode", productCo);
                        errRow.put("exploRevDate", exploRevDa);
                        errRow.put("supDelDate", supDelDa);
                        errRow.put("orderFreq", orderFr);
                        errRow.put("orderDate", orderDa);
                        errRow.put("warRevDate", warRevDa);
                        errArray.add(errRow);
                        continue;
                    }
                    if (!data.getString("exploRevDate").equals("") && data.getString("exploRevDate") != null) {
                        wc.setExploRevDate(checkDate(data.getString("exploRevDate")));
                    }
                    wc.setState("0");
                    returnArray.add(data);
//			if(!errMsg.equals("")){
//				JSONObject errRow = new JSONObject();
//				errRow.put("ERR_MESSAGE", errMsg);
//				errRow.put("ROW_NUM",data.get("ROW_NUM"));
//				errArray.add(errRow);
//			}

                    JSONObject paramname = new JSONObject();
                    paramname.put("warehouseCode", wc.getWarehouseCode());
                    paramname.put("productCode", wc.getProductCode());
                    List<PlmSupWarehouseEntity_HI> exl = getExWarehouses(paramname);
                    paramname.put("wc", wc.getWarehouseCode());
                    paramname.put("item", wc.getProductCode());
                    List<PlmSupShopEntity_HI_RO> shops = plmSupplementHead.getShops(paramname);
                    LOGGER.info("======================calculate total shops=================");
                    int total = calculate(shops);
                    List<PlmSupShopEntity_HI_RO> exShops = plmSupplementHead.getExShops(paramname);
                    LOGGER.info("======================calculate existed shops=================");
                    if (exl != null && exl.size() > 0) {
                        PlmSupWarehouseEntity_HI en = exl.get(0);
                        en.setTotalShops(total);
                        en.setStopShops(exShops.size());
                        updateEntityForExWarehouse(en, wc);
//                if (total == exShops.size()) {
//                    en.setState("1");
//                } else {
//                }
                        en.setState("4");
                        setOrderStatus(en);
                        plmSupWarehouseDAO_HI.saveOrUpdate(en);
                        saveUpdateLog(en);
                        LOGGER.info("======================update warehouse and log=================");
                    } else {
                        wc.setTotalShops(total);
                        wc.setStopShops(exShops.size());
//                if (total == exShops.size()) {
//                    wc.setState("1");
//                } else {
//                }
                        wc.setState("4");
                        setOrderStatus(wc);
                        plmSupWarehouseDAO_HI.saveOrUpdate(wc);
                        saveOpenLog(wc);
                        LOGGER.info("======================update warehouse and log=================");
                    }
                }
            }
            LOGGER.info("======================== import finished and error array size: {} ===============================", errArray.size());
            if (errArray.size() != 0) {
                String filep = export(errArray);
//            String filep = "lalalalalalalalala.xls";
                queryParamJSON.put("uploadfilepath", filep);
                queryParamJSON.put("uploadStatus", "E");
                jedis.set(queryParamJSON.getString("key"), queryParamJSON.toJSONString());
//            throw new Exception(errArray.toJSONString());
                return queryParamJSON;
            }
//		List<PlmDevelopmentIngredientsEntity_HI> deleteArray = plmDevelopmentIngredientsDAO_HI
//				.findByProperty("plmDevelopmentInfoId", plmDevelopmentInfoId);
//		plmDevelopmentIngredientsDAO_HI.deleteAll(deleteArray);
            queryParamJSON.put("uploadStatus", "S");
            jedis.set(queryParamJSON.getString("key"), queryParamJSON.toJSONString());
            queryParamJSON.put("lines", returnArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryParamJSON;
    }

    private String export(JSONArray errArray) {
        InputStream inputStream = plmSupErrorResultToExcel(errArray);
        try {
            ResultFileEntity fileEntity = fastdfsServer.fileUpload(inputStream, "export.xls");
            return fileEntity.getAccessPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream plmSupErrorResultToExcel(JSONArray array) {
        String[] heads={"错误信息","DC","供应商编码","货号","易燃易爆收货日","供应商送货LT","订货频率","订货日","供应商送货到仓日"};
//		String[] heads={"ERR_MESSAGE","rmsCode","productName","store","meter","supplementStatus","stopReason"};
        Workbook workbook = new HSSFWorkbook();// 创建一个Excel文件
        Sheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
        Row titleRow = sheet.createRow(0);//创建表头
        for(int i=0;i<heads.length;i++){//表头赋值
            titleRow.createCell(i).setCellValue(heads[i]);
        }
        if (array.size() > 0) {//单元格赋值
            for (int i = 0; i < array.size(); i++) {
                Row row = sheet.createRow(i + 1);
                JSONObject json = array.getJSONObject(i); // 遍历 jsonarray
                for(int j=0;j<heads.length;j++){
                    if("错误信息".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("ERR_MESSAGE"))?"": json.get("ERR_MESSAGE").toString());//赋值
                    }else if("DC".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("warehouseCode"))?"": json.get("warehouseCode").toString());//赋值
                    }else if("供应商编码".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("supplierCode"))?"": json.get("supplierCode").toString());//赋值
                    }else if("货号".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("productCode"))?"": json.get("productCode").toString());//赋值
                    }else if("易燃易爆收货日".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("exploRevDate"))?"": json.get("exploRevDate").toString());//赋值
                    }else if("供应商送货LT".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("supDelDate"))?"": json.get("supDelDate").toString());//赋值
                    }else if("订货频率".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("orderFreq"))?"": json.get("orderFreq").toString());//赋值
                    }else if("订货日".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("orderDate"))?"": json.get("orderDate").toString());//赋值
                    }else if("供应商送货到仓日".equals(heads[j])){
                        row.createCell(j).setCellValue(ObjectUtils.isEmpty(json.get("warRevDate"))?"": json.get("warRevDate").toString());//赋值
                    }
                }
            }
        }

        ByteArrayOutputStream bos = null ;
        try {
            bos= new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] bytes = bos.toByteArray();
            bos.close();
            return new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Pagination<PlmProductHeadEntity_HI> findProductList2(JSONObject param) {
        Pagination<PlmProductHeadEntity_HI> list = plmProductHeadServer.findPagination(param, 1, 1);
        return list;
    }

    private void setOrderStatus(PlmSupWarehouseEntity_HI en) {
        if ((en.getState().equals("2") || en.getState().equals("4")) && en.getStopShops().intValue() == en.getTotalShops().intValue()) {
            en.setOrderStatus("0");
            en.setOrderReason("1");
        } else if ((en.getState().equals("2") || en.getState().equals("4")) && en.getStopShops().intValue() != en.getTotalShops().intValue()) {
            en.setOrderStatus("1");
            en.setOrderReason("2");
        } else if ((en.getState().equals("3") || en.getState().equals("5"))) {
            en.setOrderStatus("0");
            en.setOrderReason("0");
        }
        en.setOrderUpdateDate(new Date());
    }

    @Override
    public String deletePlmSupForList(JSONObject parameters) {
        JSONArray ids = parameters.getJSONArray("ids");
        String sql = "delete from PLM_SUP_WAREHOUSE where plm_sup_warehouse_id in (''";
        StringBuilder sb = new StringBuilder(sql);
        if (CollectionUtils.isNotEmpty(ids)) {
            Map<String, Object> paramsMap = new HashMap<>();
            List<Integer> warehouseIds = ids.toJavaList(Integer.class);
            if (CollectionUtils.isNotEmpty(warehouseIds)) {
                paramsMap.put("warehouseIds", warehouseIds);
                for (Integer id : warehouseIds) {
                    sql = sql + "," + id;
                    sb.append("," + id);
                }
            }
            sql = sql + ")";
            sb.append(")");
            plmSupWarehouseDAO_HI.executeSql(sb.toString());
        }
        return "S";
    }

    //查询重复数据
    public String checkData(JSONArray dataArray) {
        String msg = "";
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject data = dataArray.getJSONObject(i);
            String warehouseCode = data.getString("warehouseCode").trim();
            String productCode = data.getString("productCode").trim();
            String sql = "select * from PLM_SUP_WAREHOUSE w where w.WAREHOUSE_CODE ='" + warehouseCode + "' " +
                    "and w.PRODUCT_CODE ='" + productCode + "'";
            List<PlmSupWarehouseEntity_HI_RO> list = plmSupWarehouseDAO_HI_RO.findList(sql);
            if (list.size() > 0) {
                msg = "仓库:" + warehouseCode + ",货号:" + productCode + ",";
            }
        }
        return msg;
    }

    static boolean isOnlyNum(String s) {
        char[] ch = s.toCharArray();
        if (ch.length > 1) {
            return false;
        }
        for (char c : ch) {
            if (!(c >= '1' && c <= '7')) {
                return false;
            }
        }
        return true;
    }

    static boolean isOnlyNum2(String s) {
        char[] ch = s.toCharArray();
        if (ch.length > 1) {
            return false;
        }
        for (char c : ch) {
            if (!(c >= '0' && c <= '7')) {
                return false;
            }
        }
        return true;
    }

    //反向通过value取到快码表
    private String getKey(JSONObject codes, String supplementStatus) {
        if (codes != null) {
            for (int i = 0; i < 8; i++) {
                String num = String.valueOf(i);
                String value = codes.getString(num);
                if (value != null) {
                    if (value.trim().equals(supplementStatus)) {
                        return num;
                    }
                }
            }
        }
        return null;
    }

    static boolean isNum(String s) {
        char[] ch = s.toCharArray();
        for (char c : ch) {
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    public static String checkDate(String dateStr) {
        String numFormatStr = "true";
        if (isNum(dateStr)) {
            String[] list = dateStr.split("");
            numFormatStr = "";
            for (int i = 0; i < list.length; i++) {
                if (Integer.parseInt(list[i]) >= 0 && Integer.parseInt(list[i]) < 8) {
                    numFormatStr = numFormatStr + "|" + list[i];
                } else {
                    numFormatStr = "false";
                }

            }
            if (numFormatStr.indexOf("|") == 0) {
                numFormatStr = numFormatStr.substring(1, numFormatStr.length());
            }
        } else {
            if (dateStr.indexOf("|") >= 0) {
                String[] list = dateStr.split("\\|");
                for (int i = 0; i < list.length; i++) {
                    if (!isNum(list[i]) || Integer.parseInt(list[i]) <= 0 || Integer.parseInt(list[i]) > 7) {
                        numFormatStr = "false";
                    }
                }
                if (list.length > 7) {
                    numFormatStr = "false";
                }
                if (numFormatStr.equals("true")) {
                    numFormatStr = dateStr;
                }
            } else {
                numFormatStr = "false";
            }
        }
        //判断字符是否重复
        if (numFormatStr.indexOf("|") >= 0) {
            for (int i = 0; i < 7; i++) {
                if (indexStrCounts(numFormatStr, i + "") > 1) {
                    numFormatStr = "false";
                }
            }
        }
        return numFormatStr;
    }

    public static String checkDate2(String dateStr) {
        String numFormatStr = "true";
        if (isNum(dateStr)) {
            String[] list = dateStr.split("");
            numFormatStr = "";
            for (int i = 0; i < list.length; i++) {
                if (Integer.parseInt(list[i]) >= 0 && Integer.parseInt(list[i]) < 8) {
                    numFormatStr = numFormatStr + "|" + list[i];
                } else {
                    numFormatStr = "false";
                }

            }
            if (numFormatStr.indexOf("|") == 0) {
                numFormatStr = numFormatStr.substring(1, numFormatStr.length());
            }
        } else {
            if (dateStr.indexOf("|") >= 0) {
                String[] list = dateStr.split("\\|");
                for (int i = 0; i < list.length; i++) {
                    if (!isNum(list[i]) || Integer.parseInt(list[i]) < 0 || Integer.parseInt(list[i]) > 7) {
                        numFormatStr = "false";
                    }
                }
                if (list.length > 7) {
                    numFormatStr = "false";
                }
                if (numFormatStr.equals("true")) {
                    numFormatStr = dateStr;
                }
            } else {
                numFormatStr = "false";
            }
        }
        //判断字符是否重复
        if (numFormatStr.indexOf("|") >= 0) {
            for (int i = 0; i < 7; i++) {
                if (indexStrCounts(numFormatStr, i + "") > 1) {
                    numFormatStr = "false";
                }
            }
        }
        return numFormatStr;
    }


    public static int indexStrCounts(String sourceStr, String indexStr) {

        int varlen1 = sourceStr.length();
        int varlen2 = indexStr.length();

        int i = 0;//字符串下标
        int n = 0;//某字符串在指定字符串中的个数

        while (i < varlen1) {
            String varstr1 = sourceStr.substring(i, i);//substr(sourceStr,i,1);
            String varstr2 = indexStr.substring(1, 1);//获取指定用于截串的字符串的第一个字符

            if (varstr1.equals(varstr2)) {
                if (i + varlen2 <= varlen1) {//判断字符下标
                    if (indexStr.equals(sourceStr.substring(i, i + varlen2))) {
                        n++;
                        i = i + varlen2;
                    }
                }
            }
            i++;
        }
        return n;
    }

    private void updateEntityForExWarehouse(PlmSupWarehouseEntity_HI en, PlmSupWarehouseEntity_HI wc) {
        if (!SaafToolUtils.isNullOrEmpty(wc.getSupplierCode())) {
            en.setSupplierCode(wc.getSupplierCode());
        }
        if (!SaafToolUtils.isNullOrEmpty(wc.getStoreWay())) {
            en.setStoreWay(wc.getStoreWay());
        }
        if (!SaafToolUtils.isNullOrEmpty(wc.getExploRevDate())) {
            en.setExploRevDate(wc.getExploRevDate());
        }
        if (!SaafToolUtils.isNullOrEmpty(wc.getSupDelDate())) {
            en.setSupDelDate(wc.getSupDelDate());
        }
        if (!SaafToolUtils.isNullOrEmpty(wc.getOrderFreq())) {
            en.setOrderFreq(wc.getOrderFreq());
        }
        if (!SaafToolUtils.isNullOrEmpty(wc.getOrderDate())) {
            en.setOrderDate(wc.getOrderDate());
        }
        if (!SaafToolUtils.isNullOrEmpty(wc.getWarRevDate())) {
            en.setWarRevDate(wc.getWarRevDate());
        }
//        wc.setState("0");

    }

    private void saveOpenLog(PlmSupWarehouseEntity_HI shop) {
//        List<PlmSupLogEntity_HI> l = getLog(shop.getProductCode(), shop.getWarehouseCode());
        PlmSupLogEntity_HI log;
//        if (l.size() > 0) {
//            log = l.get(0);
//        } else {
//        }
        log = new PlmSupLogEntity_HI();
        log.setLogType("基础表");
        log.setProductCode(shop.getProductCode());
        log.setShopCode(shop.getWarehouseCode());
        log.setUpdateType("active");
        log.setCreationDate(new Date());
        plmSupLogDAO_HI.saveOrUpdate(log);
    }

    private void saveUpdateLog(PlmSupWarehouseEntity_HI shop) {
//        List<PlmSupLogEntity_HI> l = getLog(shop.getProductCode(), shop.getWarehouseCode());
        PlmSupLogEntity_HI log;
//        if (l.size() > 0) {
//            log = l.get(0);
//        } else {
//        }
        log = new PlmSupLogEntity_HI();
        log.setLogType("基础表");
        log.setProductCode(shop.getProductCode());
        log.setShopCode(shop.getWarehouseCode());
        log.setUpdateType("update");
        log.setCreationDate(new Date());
        plmSupLogDAO_HI.saveOrUpdate(log);
    }

    private List<PlmSupLogEntity_HI> getLog(String item, String wc) {
        JSONObject queryParamJSON = new JSONObject();
        queryParamJSON.put("productCode", item);
        queryParamJSON.put("shopCode", wc);
        StringBuffer sql = new StringBuffer(
                " from PlmSupLogEntity_HI where 1=1 ");
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(PlmSupLogEntity_HI.class,
                queryParamJSON, sql, queryParamMap);
        return plmSupLogDAO_HI.findList(sql, queryParamMap);
    }

    private int calculate(List<PlmSupShopEntity_HI_RO> shops) {
        Set<String> set = new HashSet<>();
        int total = 0;
        for (PlmSupShopEntity_HI_RO shop : shops) {
            if (!set.contains(shop.getItem() + ":" + shop.getShopCode())) {
                total++;
                set.add(shop.getItem() + ":" + shop.getShopCode());
            }
        }
        return total;
    }

    private List<PlmSupWarehouseEntity_HI_RO> getExWarehouse(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer(PlmSupWarehouseEntity_HI_RO.SQL);
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(PlmSupWarehouseEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
        List<PlmSupWarehouseEntity_HI_RO> findListResult = plmSupWarehouseDAO_HI_RO.findList(sql, queryParamMap);
        return findListResult;
    }

    @Override
    public List<PlmSupWarehouseEntity_HI> getExWarehouses(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer(" from PlmSupWarehouseEntity_HI where 1=1 ");
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(PlmSupWarehouseEntity_HI.class, queryParamJSON, sql, queryParamMap);
        List<PlmSupWarehouseEntity_HI> findListResult = plmSupWarehouseDAO_HI.findList(sql, queryParamMap);
        return findListResult;
    }

    private String getStringCellValue(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        String strCell = "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    return sdf.format(date);
                }

                strCell = String.valueOf(cell.getNumericCellValue());
                if (strCell.contains(".")) {
                    strCell = strCell.substring(0, strCell.indexOf("."));
                }

                break;
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }

        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    public static File getNetUrl(String netUrl) {
        // 判断http和https
        File file = null;
        if (netUrl.startsWith("https://")) {
            file = getNetUrlHttps(netUrl);
        } else {
            file = getNetUrlHttp(netUrl);
        }
        return file;
    }

    public static File getNetUrlHttp(String netUrl) {
        // 对本地文件命名
        String fileName = netUrl;
        File file = null;

        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("net_url", URLEncoder.encode(fileName));
//            file = new File(fileName);
            // 下载
            urlfile = new URL(netUrl);
            inStream = urlfile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            // log.error("远程图片获取错误：" + netUrl);
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public static File getNetUrlHttps(String fileUrl) {
        // 对本地文件进行命名
        String file_name = fileUrl;
        File file = null;

        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            file = File.createTempFile("net_url", file_name);

            SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
            sslcontext.init(null, new TrustManager[]{new X509TrustUtiil()},
                    new java.security.SecureRandom());
            URL url = new URL(fileUrl);

            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslsession) {
                    // logger.warn("WARNING: Hostname is not matched for cert.");
                    return true;
                }
            };
            HttpsURLConnection
                    .setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext
                    .getSocketFactory());
            HttpsURLConnection urlCon = (HttpsURLConnection) url
                    .openConnection();
            urlCon.setConnectTimeout(6000);
            urlCon.setReadTimeout(6000);
            int code = urlCon.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new Exception("文件读取失败");
            }
            // 读文件流
            in = new DataInputStream(urlCon.getInputStream());
            out = new DataOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[2048];
            int count = 0;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            // log.error("远程图片获取错误：" + fileUrl);
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return file;
    }
}
