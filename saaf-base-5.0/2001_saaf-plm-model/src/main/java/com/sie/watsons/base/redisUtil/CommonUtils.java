package com.sie.watsons.base.redisUtil;

import com.sie.watsons.base.api.model.enums.productPlacetypeEnums;
import com.sie.watsons.base.api.model.inter.server.PlmSendEmailServer;
import com.sie.watsons.base.product.model.entities.VmiShopEntity_HI2;
import com.sie.watsons.base.product.model.entities.readonly.VmiShopEntity_HI_RO2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CommonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    @Value("${api.attachment.orderListURL:}")
    private String orderListURL;
    /**
     * 发送邮件, 新增成功时发送邮件
     */
    public static String newProductMailContent(String contactName, List<Map<String,Object>> emailEntList,Map<String,String> areaListagg) throws Exception {
        StringBuilder content = new StringBuilder("<html><head></head><body>");

        content.append("Dears<br />");
        content.append(" 如下是新增货品<br/>");
        content.append("<table border=\"10\" style=\"border:solid 1px #E8F2F9; font-size:14px;\">");
        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\">" +
                "<th>货号</th>" +
                "<th>货名</th>" +
                "<th>部门</th>" +
                "<th>品牌</th>" +
                "<th>供应商编码</th>" +
                "<th>生效方式</th>" +
                "<th>区域</th>" +
                "<th>生效仓库</th>" +
//                "<th>生效店铺</th>" +
                "<th>生效日期</th>" +
                "<th>创建日期</th>" +
                "<th>创建人</th>" +
                "</tr>");
        for (Map<String,Object> data : emailEntList) {
            content.append("<tr>");
            content.append("<td>" + data.get("RMSCODE")                 + "</td>"); //第一列
            content.append("<td>" + data.get("PRODUCTNAME")             + "</td>"); //第二列
            content.append("<td>" + data.get("DEPARTMENT")      + "</td>"); //第三列
            content.append("<td>" + data.get("BRANDNAMECN")             + "</td>"); //第4列
            content.append("<td>" + data.get("SUPPLIERCODE")            + "</td>"); //第5列
            content.append("<td>" + (data.get("SXWAY")== null?"/":getMeaningForValue(data.get("SXWAY").toString()))+ "</td>"); //第6列
            content.append("<td>" + (data.get("AREA")==null?"/":data.get("AREA")) + "</td>"); //第7列
            StringBuffer wareHouseBuffer = new StringBuffer();
            String wareHouse = "/";
            if(data.get("AREA")!=null || data.get("AREAID")!=null){
//                wareHouseBuffer.append(data.get("SXWAREHOUSE")==null?"":data.get("SXWAREHOUSE"));
                String area = data.get("AREAID").toString();
                if(area.contains("1")){
                    wareHouseBuffer.append(areaListagg.get("1")).append(",");
                }
                if(area.contains("2")){
                    wareHouseBuffer.append(areaListagg.get("2")).append(",");
                }
                if(area.contains("3")){
                    wareHouseBuffer.append(areaListagg.get("3")).append(",");
                }
                if(area.contains("4")){
                    wareHouseBuffer.append(areaListagg.get("4")).append(",");
                }
            }
            if(wareHouseBuffer.length()>1 && !"".equals(wareHouseBuffer)){
                wareHouseBuffer.deleteCharAt(wareHouseBuffer.length()-1);
                wareHouse= wareHouseBuffer.toString();
            }else if (data.get("SXWAREHOUSE")!=null){
                wareHouse= data.get("SXWAREHOUSE").toString();
            }
//            content.append("<td>" + (data.get("SXWAREHOUSE")==null?"/":data.get("SXWAREHOUSE")) + "</td>"); //第8列
            content.append("<td>" + (wareHouse) + "</td>"); //第8列
//            content.append("<td>" + (data.get("SXSTORE")==null?"/":data.get("SXSTORE")) + "</td>"); //第9列
            content.append("<td>" + data.get("RMSSYNCHRDATE") + "</td>"); //生效日期
            content.append("<td>" + data.get("CREATIONDATE") + "</td>"); //第10列
            content.append("<td>" + (data.get("CREATEDEMP")==null?"/":data.get("CREATEDEMP")) + "</td>"); //第11列
            content.append("</tr>");
        }
        content.append("</table>");
        content.append("</body></html>");
        return content.toString();
    }
    private static String getMeaningForValue(String values) {
        EnumSet<productPlacetypeEnums> enumSet = EnumSet.allOf(productPlacetypeEnums.class);
        for ( productPlacetypeEnums e:enumSet){
            if (e.getValues().equals(values)){
                return e.getMeaning();
            }
        }
        return "";
    }

    /**
     * 发送邮件.见证人员驳回通知owner
     */
    public static String submitWitnessMailContent(String contactName, String projectName) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("尊敬的");
        buff.append(contactName);
        buff.append("，<br />\n" +
                "<br />\n" +
                "竞价项目"+projectName+"的所有供应商非报价资料已自动开启，请及时登录屈臣氏电子采购系统审阅并填写审阅意见。"+"<br />\n" +
                "<br />\n" +
                "感谢您的合作。<br />\n" +
                "<br />\n" +
                "屈臣氏电子采购系统<br />\n" +
                "<br />\n" +
                "（本邮件由系统自动发送，请不要回复）<br />");
        buff.append("</body></html>");
        return buff.toString();
    }





    public static void main(String[] args) {
        try {
            String str = "[1111112222222222223333333333333]";
            System.out.println(str.substring(1,str.length()-1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String createAttachment(String s, List<Map<String, Object>> list, Map<String, String> areaListagg, String orderListURL) {

        Map<String,String> times = getMonthTimes();
        // 生成附件新增的单据
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workBook.createCellStyle();
        XSSFDataFormat format = workBook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        XSSFSheet sheet = workBook.createSheet(s);
        sheet.setDefaultColumnWidth(20);
        String[] headers = new String[]{ "货号","货名","部门","品牌","供应商编码","生效方式","区域","生效仓库","生效日期","创建日期","创建人" };
        XSSFRow row = sheet.createRow(0);
        for (int k = 0; k < headers.length; k++) {
            XSSFCell cell = row.createCell(k);
            cell.setCellValue(headers[k]);
        }
        //        for (Map<String,Object> data : list) {
        for (int i = 0;i<list.size();i++) {
            // 生成订货清单明细
            Map<String, Object> data = list.get(i);
            row = sheet.createRow(sheet.getLastRowNum() + 1);

            StringBuffer wareHouseBuffer = new StringBuffer();
            String wareHouse = "/";
            if((data.get("AREA")!=null || data.get("AREAID")!=null) && productPlacetypeEnums.PLM_PRODUCT_PLACETYPE_5.getValues().equals(data.get("SXWAY"))){
//                wareHouseBuffer.append(data.get("SXWAREHOUSE")==null?"":data.get("SXWAREHOUSE"));
                String area = data.get("AREAID").toString();
                if(area.contains("1")){
                    wareHouseBuffer.append(areaListagg.get("1")).append(",");
                }
                if(area.contains("2")){
                    wareHouseBuffer.append(areaListagg.get("2")).append(",");
                }
                if(area.contains("3")){
                    wareHouseBuffer.append(areaListagg.get("3")).append(",");
                }
                if(area.contains("4")){
                    wareHouseBuffer.append(areaListagg.get("4")).append(",");
                }
            }
            if(wareHouseBuffer.length()>1 && !"".equals(wareHouseBuffer)){
                wareHouseBuffer.deleteCharAt(wareHouseBuffer.length()-1);
                wareHouse= wareHouseBuffer.toString();
            }else if (data.get("SXWAREHOUSE")!=null){
                wareHouse= data.get("SXWAREHOUSE").toString();
            }


            // 设置单元格为文本格式
            for (int j = 0; j < headers.length; j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                cell.setCellType(CellType.STRING);
                // 设置单元格为文本格式
                String value = "";
                switch(j){
                    case 0:
                        value=data.get("RMSCODE").toString();
                        break;
                    case 1:
                        value=data.get("PRODUCTNAME").toString();
                        break;
                    case 2:
                        value=data.get("DEPARTMENT").toString();
                        break;
                    case 3:
                        value=data.get("BRANDNAMECN").toString();
                        break;
                    case 4:
                        value=data.get("SUPPLIERCODE").toString();
                        break;
                    case 5:
                        value=(data.get("SXWAY")== null?"/":getMeaningForValue(data.get("SXWAY").toString()));
                        break;
                    case 6:
                        value=(data.get("AREA")==null?"/":data.get("AREA")).toString();
                        break;
                    case 7:
                        value= wareHouse;
                        break;
//                    case 8:
////                        value=(data.get("SXWAREHOUSE")==null?"/":data.get("SXWAREHOUSE")).toString();
//                        value=(data.get("SXSTORE")==null?"/":data.get("SXSTORE")).toString();
//                        break;
                    case 8:
                        value=data.get("RMSSYNCHRDATE").toString();
                        break;
                    case 9:
                        value=data.get("CREATIONDATE").toString();
                        break;
                    case 10:
                        value=(data.get("CREATEDEMP")==null?"/":data.get("CREATEDEMP")).toString();
                        break;
                    default:

                        break;
                }
                cell.setCellValue(value);
            }
        }
        String startTimeStr = times.get("startTimeStr");
        String endTimeStr = times.get("endTimeStr");
        String second = times.get("second");
        String osName= System.getProperties().getProperty("os.name");
        String url ="";
        if("Linux".equals(osName)){
            url = (StringUtils.isEmpty(orderListURL)?"/home/sieuat/file/attachment/":orderListURL )+ "新增货品" + startTimeStr + "-" + endTimeStr +"-" + second+ ".xlsx";
        }else {
            url ="E:\\work\\广州屈臣氏项目\\CSV文件\\新增货品" + startTimeStr + "-" + endTimeStr +"-" + second+ ".xlsx";
        }
        File file = new File(url);
        if (file.exists()) {
            file.delete();
        }
        LOGGER.info("附件参数："
                +" subjuct" + ":新增货品" + startTimeStr + "至" + endTimeStr
                +" url:: " + url);
        OutputStream output = null;
        try {
            output = new FileOutputStream(url);
            workBook.write(output);//写入磁盘
            LOGGER.info("新增货品excel成功:{}", url);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * 获取2个月的时间戳
     * @return
     */
    private static Map<String, String> getMonthTimes() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
        Date now = new Date();
        String time = sdf.format(now);
        String second = sdf2.format(now);
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(calendar.MONTH, -2); //设置为前2月，可根据需求进行修改
        Date date = calendar.getTime();//获取2个月前的时间
        String towMonth = sdf.format(date);
        Map<String, String> map = new HashMap<>();
        map.put("startTimeStr",towMonth);
        map.put("endTimeStr",time);
        map.put("second",second);
        return map;
    }

    public static Map<String, String> listToListAgg(List<VmiShopEntity_HI_RO2> shops) {
        Map<String, String> map = new HashMap<>();
        //获取为1的数据
        List<String> shop1 = shops.stream().filter(s->"1".equals(s.getAreaEn())).map(m->m.getVhPreCode()).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(shop1)){
            String wareHouse1 = shop1.toString().trim().replace("[","").replace("]","");
            map.put("1",wareHouse1);
        }
        List<String> shop2 = shops.stream().filter(s->"2".equals(s.getAreaEn())).map(m->m.getVhPreCode()).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(shop2)){
            String wareHouse2 = shop2.toString().trim().replace("[","").replace("]","");
            map.put("2",wareHouse2);
        }
        List<String> shop3 = shops.stream().filter(s->"3".equals(s.getAreaEn())).map(m->m.getVhPreCode()).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(shop3)){
            String wareHouse3 = shop3.toString().trim().replace("[","").replace("]","");
            map.put("3",wareHouse3);
        }
        List<String> shop4 = shops.stream().filter(s->"4".equals(s.getAreaEn())).map(m->m.getVhPreCode()).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(shop4)){
            String wareHouse4 = shop4.toString().trim().replace("[","").replace("]","");
            map.put("4",wareHouse4);
        }
        return map;
    }

    public static String newProductMailContentNew(String s, List<Map<String, Object>> emailEntList) {
        StringBuilder content = new StringBuilder("<html><head></head><body>");

        content.append("Dears<br />");
        content.append(" 如下是新增货品<br/>");
        content.append("<table border=\"10\" style=\"border:solid 1px #E8F2F9; font-size:14px;\">");
        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\">" +
                "<th>货号</th>" +
                "<th>货名</th>" +
                "<th>部门</th>" +
                "<th>品牌</th>" +
                "<th>供应商编码</th>" +
                "<th>供应商名称</th>" +
                "<th>生效方式</th>" +
                "<th>区域</th>" +
                "<th>生效仓库</th>" +
//                "<th>生效店铺</th>" +
                "<th>生效日期</th>" +
                "<th>创建日期</th>" +
                "<th>创建人</th>" +
                "</tr>");
        for (Map<String,Object> data : emailEntList) {
            content.append("<tr>");
            content.append("<td>" + data.get("RMSCODE")                 + "</td>"); //第一列
            content.append("<td>" + data.get("PRODUCTNAME")             + "</td>"); //第二列
            content.append("<td>" + data.get("DEPARTMENT")      + "</td>"); //第三列
            content.append("<td>" + data.get("BRANDNAMECN")             + "</td>"); //第4列
            content.append("<td>" + data.get("SUPPLIERCODE")            + "</td>"); //第5列
            content.append("<td>" + data.get("SUPPLIERNAME")            + "</td>"); //第5列
            content.append("<td>" + (data.get("SXWAY")== null?"/":getMeaningForValue(data.get("SXWAY").toString()))+ "</td>"); //第6列
            content.append("<td>" + (data.get("AREA")==null?"/":data.get("AREA")) + "</td>"); //第7列
            content.append( "<td>" + (("1".equals(data.get("SXWAY").toString()) || "2".equals(data.get("SXWAY").toString()) )?"All":(data.get("SXWAREHOUSE")==null?"/":data.get("SXWAREHOUSE").toString())) + "</td>"); //第8列
            content.append("<td>" + data.get("RMSSYNCHRDATE") + "</td>"); //生效日期
            content.append("<td>" + data.get("CREATIONDATE") + "</td>"); //第10列
            content.append("<td>" + (data.get("CREATEDEMP")==null?"/":data.get("CREATEDEMP")) + "</td>"); //第11列
            content.append("</tr>");
        }
        content.append("</table>");
        content.append("</body></html>");
        return content.toString();
    }

    public static String createAttachmentNew(String s, List<Map<String, Object>> list, String orderListURL) {

        Map<String,String> times = getMonthTimes();
        // 生成附件新增的单据
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workBook.createCellStyle();
        XSSFDataFormat format = workBook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        XSSFSheet sheet = workBook.createSheet(s);
        sheet.setDefaultColumnWidth(20);
        String[] headers = new String[]{ "货号","货名","部门","品牌","供应商编码","供应商名称","生效方式","区域","生效仓库","生效日期","创建日期","创建人" };
        XSSFRow row = sheet.createRow(0);
        for (int k = 0; k < headers.length; k++) {
            XSSFCell cell = row.createCell(k);
            cell.setCellValue(headers[k]);
        }
        //        for (Map<String,Object> data : list) {
        for (int i = 0;i<list.size();i++) {
            // 生成订货清单明细
            Map<String, Object> data = list.get(i);
            row = sheet.createRow(sheet.getLastRowNum() + 1);
            // 设置单元格为文本格式
            for (int j = 0; j < headers.length; j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                cell.setCellType(CellType.STRING);
                // 设置单元格为文本格式
                String value = "";
                switch(j){
                    case 0:
                        value=data.get("RMSCODE").toString();
                        break;
                    case 1:
                        value=data.get("PRODUCTNAME").toString();
                        break;
                    case 2:
                        value=data.get("DEPARTMENT").toString();
                        break;
                    case 3:
                        value=data.get("BRANDNAMECN").toString();
                        break;
                    case 4:
                        value=data.get("SUPPLIERCODE").toString();
                        break;
                    case 5:
                        value=data.get("SUPPLIERNAME").toString();
                        break;
                    case 6:
                        value=(data.get("SXWAY")== null?"/":getMeaningForValue(data.get("SXWAY").toString()));
                        break;
                    case 7:
                        value=(data.get("AREA")==null?"/":data.get("AREA")).toString();
                        break;
                    case 8:
//                        value=(data.get("SXWAREHOUSE")==null?"/":("1".equals(data.get("SXWAY").toString())?"All":data.get("SXWAREHOUSE")).toString());
                        value=(("1".equals(data.get("SXWAY").toString()) || "2".equals(data.get("SXWAY").toString()) )?"All":(data.get("SXWAREHOUSE")==null?"/":data.get("SXWAREHOUSE").toString()));
                        break;
//                  case 8:
////                      value=(data.get("SXWAREHOUSE")==null?"/":data.get("SXWAREHOUSE")).toString();
//                        value=(data.get("SXSTORE")==null?"/":data.get("SXSTORE")).toString();
//                        break;
                    case 9:
                        value=data.get("RMSSYNCHRDATE").toString();
                        break;
                    case 10:
                        value=data.get("CREATIONDATE").toString();
                        break;
                    case 11:
                        value=(data.get("CREATEDEMP")==null?"/":data.get("CREATEDEMP")).toString();
                        break;
                    default:

                        break;
                }
                cell.setCellValue(value);
            }
        }
        String startTimeStr = times.get("startTimeStr");
        String endTimeStr = times.get("endTimeStr");
        String second = times.get("second");
        String osName= System.getProperties().getProperty("os.name");
        String url ="";
        if("Linux".equals(osName)){
            url = (StringUtils.isEmpty(orderListURL)?"/home/sieuat/file/attachment/":orderListURL )+ "新增货品" + startTimeStr + "-" + endTimeStr +"-" + second+ ".xlsx";
        }else {
            url ="E:\\work\\广州屈臣氏项目\\CSV文件\\新增货品" + startTimeStr + "-" + endTimeStr +"-" + second+ ".xlsx";
        }
        File file = new File(url);
        if (file.exists()) {
            file.delete();
        }
        LOGGER.info("附件参数："
                +" subjuct" + ":新增货品" + startTimeStr + "至" + endTimeStr
                +" url:: " + url);
        OutputStream output = null;
        try {
            output = new FileOutputStream(url);
            workBook.write(output);//写入磁盘
            LOGGER.info("新增货品excel成功:{}", url);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }
}
