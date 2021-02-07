package com.sie.watsons.base.api.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.api.model.entities.PlmUdaAttributeEntity_HI;
import com.sie.watsons.base.api.model.inter.IPlmSupProductImport;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmCountryOfOriginEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmCountryOfOrigin;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierInfoEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.server.PlmProductBarcodeTableServer;
import com.sie.watsons.base.product.model.inter.server.PlmProductHeadServer;
import com.sie.watsons.base.product.model.inter.server.PlmProductPriceServer;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/1/3/003.
 */
@Component("plmSupProductImportServer")
public class PlmSupProductImportServer implements IPlmSupProductImport {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PlmSupProductServer.class);


    @Autowired
    private BaseCommonDAO_HI<PlmProductHeadEntity_HI> plmProductHeadEntity_HI;
    @Autowired
    private BaseCommonDAO_HI<PlmProductSupplierInfoEntity_HI> plmProductSupplierInfoEntity_HI;
    @Autowired
    private BaseCommonDAO_HI<PlmUdaAttributeEntity_HI> plmUdaAttributeEntity_HI;

    public void insertDate() throws IOException{
        Map<String,Map<String,String>> udaMapList = readerUdaFile("D:\\RMS_Item_UDA_Change1.csv");
        Map<String,Map<String,String>> supMapList = readerSupFile("D:\\RMS_item_supp_change1.csv");
        Map<String,Map<String,String>> masMapList = readerMasFile("D:\\RMS_Item_Mas_Change1.csv");
        List<PlmProductHeadEntity_HI> headList = new ArrayList<>();
        List<PlmProductSupplierInfoEntity_HI> supList = new ArrayList<>();
        for(Map.Entry<String,Map<String,String>> map:udaMapList.entrySet()){
            PlmProductHeadEntity_HI head = new PlmProductHeadEntity_HI();
            setUda(head,map.getValue());
            String itemCode = map.getKey();
            Map<String,String> masMap = masMapList.get(itemCode);
            head.setRmsCode(masMap.get("ITEM"));
            head.setClassDesc(masMap.get("ITEM_DESC"));
            head.setGroupBrand(masMap.get("GROUP_NO"));
            head.setDepartment(masMap.get("DEPT"));
            head.setClasses(masMap.get("CLASS"));
            head.setSubClass(masMap.get("SUBCLASS"));
            head.setOrderStatus(masMap.get("STATUS"));
            headList.add(head);
        }
        plmProductHeadEntity_HI.saveAll(headList);
        for(Map.Entry<String,Map<String,String>> map:supMapList.entrySet()){
            PlmProductSupplierInfoEntity_HI supplier = new PlmProductSupplierInfoEntity_HI();
            map.getValue().get("WH_CONSIGN_IND");
            supplier.setRmsCode(map.getValue().get("ITEM"));
            supplier.setSupplierId(map.getValue().get("SUPPLIER"));
            supplier.setIsMainsupplier(map.getValue().get("PRIMARY_SUPP_IND"));
            supplier.setCurrencyCost(map.getValue().get("UNIT_COST"));
            supplier.setPackageSpe(Integer.parseInt(map.getValue().get("SUPP_PACK_SIZE")));
            supplier.setInnerpackageSpe(Integer.parseInt(map.getValue().get("INNER_PACK_SIZE")));
            //supplier.set
            supList.add(supplier);
        }
        plmProductSupplierInfoEntity_HI.saveAll(supList);
    }

    public PlmProductHeadEntity_HI setUda(PlmProductHeadEntity_HI head,Map<String,String> map1){
        //Map<String,String> map1 = udaMapList.get("100000001");
        if(map1.get("16")!=null){
            head.setCountUnit(map1.get("16"));
        }
        if(map1.get("18")!=null){
            head.setOriginCountry(map1.get("18"));
        }
        if(map1.get("27")!=null){
            head.setUnit(map1.get("27"));
        }
        if(map1.get("35")!=null){
            head.setWarehouseGetDay(map1.get("35"));
        }
        if(map1.get("36")!=null){
            head.setSxDays(Integer.parseInt(map1.get("36")));
        }
        if(map1.get("37")!=null){
            head.setWarehousePostDay(map1.get("37"));
        }
        if(map1.get("42")!=null){
            head.setProductEname(map1.get("42"));
        }
        if(map1.get("2")!=null){
            head.setValidDays(map1.get("2"));
        }
        if(map1.get("3")!=null){
            head.setSpecialLicence(map1.get("3"));
        }
        if(map1.get("4")!=null){
            head.setProductType(map1.get("4"));
        }
        if(map1.get("5")!=null){
            head.setProductResource(map1.get("5"));
        }
        if(map1.get("7")!=null){
            head.setPricewarProduct(map1.get("7"));
        }
        if(map1.get("8")!=null){
            head.setUniqueCommodities(map1.get("8"));
        }
        if(map1.get("9")!=null){
            head.setSpecialtyProduct(map1.get("9"));
        }
        if(map1.get("10")!=null){
            head.setProductProperties(map1.get("10"));
        }
        if(map1.get("11")!=null){
            head.setBuyingLevel(map1.get("11"));
        }
        if(map1.get("12")!=null){
            head.setDangerousProduct(map1.get("12"));
        }
        if(map1.get("13")!=null){
            head.setPosInfo(map1.get("13"));
        }
        if(map1.get("15")!=null){
            head.setInternationProduct(map1.get("15"));
        }
        if(map1.get("29")!=null){
            head.setSesionProduct(map1.get("29"));
        }
        if(map1.get("41")!=null){
            head.setProductReturn(map1.get("41"));
        }
        if(map1.get("45")!=null){
            head.setTopProduct(map1.get("45"));
        }
        if(map1.get("61")!=null){
            head.setBluecapProduct(map1.get("61"));
        }
        if(map1.get("62")!=null){
            head.setCrossborderProduct(map1.get("62"));
        }
        if(map1.get("63")!=null){
            head.setVcProduct(map1.get("63"));
        }
        if(map1.get("CN")!=null){
            head.setBrandnameCn(map1.get("CN"));
        }
        if(map1.get("EN")!=null){
            head.setBrandnameEn(map1.get("EN"));
        }
        System.out.println(head.toString());
        return head;
    }

    public Map<String,Map<String,String>> readerUdaFile(String fileName) throws IOException{
        String sql = "select uda_id||'-'||uda_value udaKey,uda_value_desc udaDesc from plm_uda_attribute where uda_id > 100 " ;
        List<Map<String, Object>> list = plmUdaAttributeEntity_HI.queryNamedSQLForList(sql, new HashMap<>());
        Map<String,Map<String,String>> udaMapList = new HashMap<>();
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(new File(fileName)));
            BufferedReader reader= new BufferedReader(new InputStreamReader(in,"GBK"));
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            int i=0;
            String itemCode = "";
            Map<String,String> udaMap = new HashMap<>();
            while((line=reader.readLine())!=null){
                i=i+1;
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String str2 = item[2];
                String str3 = item[3];
                String str4 = item[4];
                String str6 = item[6];
                if(str6.indexOf("BBB")>0){
                    udaMapList.put(itemCode,udaMap);
                }
                if(!itemCode.equals(str2)){
                    if(i>1){
                        udaMapList.put(itemCode,udaMap);
                        udaMap = new HashMap<>();
                    }
                    itemCode = str2;
                }
                udaMap.put(str3,str4);
                if(Integer.parseInt(str3)<151&&Integer.parseInt(str3)>100){
                    for(int k=0;k<list.size();k++){
                        String keystr = list.get(k).get("UDAKEY").toString();
                        if((str3+"-"+str4).equals(keystr)){
                            udaMap.put("CN",list.get(k).get("UDADESC").toString());
                        }
                    }
                }
                if(Integer.parseInt(str3)<251&&Integer.parseInt(str3)>150){
                    for(int k=0;k<list.size();k++){
                        String keystr = list.get(k).get("UDAKEY").toString();
                        if((str3+"-"+str4).equals(keystr)){
                            udaMap.put("EN",list.get(k).get("UDADESC").toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return udaMapList;
    }

    public Map<String,Map<String,String>> readerMasFile(String fileName) throws IOException{
        Map<String,Map<String,String>> masMapList = new HashMap<>();
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(new File(fileName)));
            BufferedReader reader= new BufferedReader(new InputStreamReader(in,"GBK"));
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            int i=0;
            String itemCode = "";
            Map<String,String> map = new HashMap<>();
            while((line=reader.readLine())!=null){
                i=i+1;
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String str0 = item[0];
                String str1 = item[1];
                String str2 = item[2];
                String str3 = item[3];
                String str4 = item[4];
                String str5 = item[5];
                String str6 = item[6];
                String str7 = item[7];
                String str9 = item[9];
                //最后一行添加BBB做尾行标记
                if(str9.indexOf("BBB")>0){
                    masMapList.put(str0,map);
                }

                map = new HashMap<>();
                map.put("ITEM",str0);
                map.put("ITEM_DESC",str1);
                map.put("GROUP_NO",str2);
                map.put("DEPT",str3);
                map.put("CLASS",str4);
                map.put("SUBCLASS",str5);
                map.put("WASTE_PCT",str6);
                map.put("STATUS",str7);
                masMapList.put(str0,map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return masMapList;
    }

    public Map<String,Map<String,String>> readerSupFile(String fileName) throws IOException{
        Map<String,Map<String,String>> supMapList = new HashMap<>();
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(new File(fileName)));
            BufferedReader reader= new BufferedReader(new InputStreamReader(in,"GBK"));
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            int i=0;
            String itemCode = "";
            Map<String,String> map = new HashMap<>();
            while((line=reader.readLine())!=null){
                i=i+1;
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String str0 = item[0];
                String str1 = item[1];
                String str2 = item[2];
                String str3 = item[3];
                String str4 = item[4];
                String str5 = item[5];
                String str6 = item[6];
                String str7 = item[7];
                String str8 = item[8];
                String str9 = item[9];
                String str10 = item[10];
                String str11 = item[11];
                String str12 = item[12];
                String str13 = item[13];
                String str14 = item[14];
                String str16 = item[16];
                //最后一行添加BBB做尾行标记
                if(str16.indexOf("BBB")>0){
                    supMapList.put(str2,map);
                }

                map = new HashMap<>();
                map.put("UPDATE_TYPE",str0);
                map.put("ITEM",str1);
                map.put("SUPPLIER",str2);
                map.put("PRIMARY_SUPP_IND",str3);
                map.put("WH_CONSIGN_IND",str4);
                map.put("ORIGIN_COUNTRY_ID",str5);
                map.put("UNIT_COST",str6);
                map.put("SUPP_PACK_SIZE",str7);
                map.put("INNER_PACK_SIZE",str8);
                map.put("ROUND_TO_INNER_PCT",str9);
                map.put("ROUND_TO_CASE_PCT",str10);
                map.put("ROUND_TO_LAYER_PCT",str11);
                map.put("ROUND_TO_PALLET_PCT",str12);
                map.put("HI",str13);
                map.put("TI",str14);
                supMapList.put(str2,map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supMapList;
    }

    public static void main(String[] args) {
        try {
            new PlmSupProductImportServer().insertDate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
