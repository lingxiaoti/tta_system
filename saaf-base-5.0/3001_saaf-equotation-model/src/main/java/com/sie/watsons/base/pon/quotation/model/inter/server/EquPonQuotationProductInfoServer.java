package com.sie.watsons.base.pon.quotation.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.project.model.entities.EquPonProductSpecsEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationProductInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationProductInfoEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.inter.IEquPonQuotationProductInfo;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component("equPonQuotationProductInfoServer")
public class EquPonQuotationProductInfoServer extends BaseCommonServer<EquPonQuotationProductInfoEntity_HI> implements IEquPonQuotationProductInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationProductInfoServer.class);

    @Autowired
    private BaseViewObject<EquPonQuotationProductInfoEntity_HI_RO> equPonQuotationProductInfoDAO_HI_RO;
    @Autowired
    private ViewObject<EquPonProductSpecsEntity_HI> ponProductSpecsDao;
    @Autowired
    private ViewObject<EquPonQuotationProductInfoEntity_HI> productInfoDao;
    @Autowired
    private IFastdfs fastDfsServer;
    @Autowired
    private ViewObject<EquPonProductSpecsEntity_HI> productSpecsDao;
    @Autowired
    private GenerateCodeServer generateCodeServer;

    public EquPonQuotationProductInfoServer() {
        super();
    }

    @Override
    public int saveImportForQuotationProduct(String params, Integer userId) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(params);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        Integer projectId = jsonObject.getJSONObject("info").getInteger("projectId");
        String projectNumber = jsonObject.getJSONObject("info").getString("projectNumber");
        Integer quotationId = jsonObject.getJSONObject("info").getInteger("quotationId");
        Integer supplierId = jsonObject.getJSONObject("info").getInteger("supplierId");
        Integer sourceId = jsonObject.getJSONObject("info").getInteger("sourceId");
        // 先删除之前导入数据
        Map<String, Object> map = new HashMap<>(4);
        map.put("quotationId", quotationId);
        map.put("projectNumber", projectNumber);
        map.put("supplierId", supplierId);
        List<EquPonQuotationProductInfoEntity_HI> productList = productInfoDao.findByProperty(map);
        productInfoDao.delete(productList);

        JSONArray errList = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            JSONObject errJson = new JSONObject();
            String msgStr = "";
            try {
                // 校验导入的产品不能重复
                for (int j = i + 1; j < jsonArray.size(); j++) {
                    if (jsonArray.getJSONObject(i).getString("productName").equals(jsonArray.getJSONObject(j).getString("productName"))) {
                        msgStr += "导入的" + (i + 1) + "行与" + (j + 1) + "行重复";
                        break;
                    }
                }
                if (SaafToolUtils.isNullOrEmpty(json.getString("productName"))) {
                    msgStr += "产品不能为空";
                }
                // 一次报价时产品价格不能为空
                if (sourceId == null) {
                    if (SaafToolUtils.isNullOrEmpty(json.getBigDecimal("firstProductPrice"))) {
                        msgStr += "承载产品的包装价格(如软管、玻璃瓶等),但不含合同费用及不含税:Net Net Cost(元)不能为空";
                    }
                    if (SaafToolUtils.isNullOrEmpty(json.getBigDecimal("twoProductPrice"))) {
                        msgStr += "承载产品的包装价格(如软管、玻璃瓶等),含OI%免费货,但不含税:Net Net Cost(1-OI%)(元)不能为空";
                    }
                }
                // 如果是二次报价导入,则判断二次报价价格是否为空,获取一次报价的价格数据,赋值给二次报价中一次报价的栏位
                if (sourceId != null) {
                    if (SaafToolUtils.isNullOrEmpty(json.getBigDecimal("bargainFirstPrice"))) {
                        msgStr += "承载产品的包装价格(如软管、玻璃瓶等),但不含合同费用及不含税:Net Net Cost(元)--二次报价不能为空";
                    }
                    if (SaafToolUtils.isNullOrEmpty(json.getBigDecimal("bargainTwoPrice"))) {
                        msgStr += "承载产品的包装价格(如软管、玻璃瓶等),含OI%免费货,但不含税:Net Net Cost(1-OI%)(元)--二次报价不能为空";
                    }

                    List<EquPonQuotationProductInfoEntity_HI> productInfoList = productInfoDao.findByProperty("quotationId", sourceId);
                    for (EquPonQuotationProductInfoEntity_HI productInfo : productInfoList) {
                        if (StringUtils.equals(productInfo.getProductName(), json.getString("productName"))) {
                            json.put("firstProductPrice", productInfo.getFirstProductPrice());
                            json.put("twoProductPrice", productInfo.getTwoProductPrice());
                            break;
                        }
                    }
                }
                // 校验是否和立项中产品名称相同
                List<EquPonProductSpecsEntity_HI> list = ponProductSpecsDao.findByProperty("projectId", projectId);
                if (CollectionUtils.isEmpty(list)) {
                    msgStr += "该立项单据" + projectId + "对应的产品列表不能为空";
                }
                // 如果列表中不包含该产品则错误提示
                boolean b = true;
                for (EquPonProductSpecsEntity_HI productSpecsEntity : list) {
                    if (productSpecsEntity.getProductName().equals(json.getString("productName"))) {
                        // 根据产品名和立项id获取立项产品中对应的id
                        Map<String, Object> specMap = new HashMap<>(4);
                        specMap.put("productName", productSpecsEntity.getProductName());
                        specMap.put("projectId", projectId);
                        List<EquPonProductSpecsEntity_HI> specsEntityHiList = productSpecsDao.findByProperty(specMap);
                        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(specsEntityHiList)){
                            json.put("specsId", specsEntityHiList.get(0).getSpecsId());
                        }
                        b = true;
                        break;
                    }
                    b = false;
                }
                if (!b) {
                    msgStr += "导入的该产品" + json.getString("productName") + "在该立项单据对应的产品列表中不存在";
                }
                if (!"".equals(msgStr)) {
                    errJson.put("ROW_NUM", json.get("ROW_NUM"));
                    errJson.put("ERR_MESSAGE", msgStr);
                    errList.add(errJson);
                } else {
                    json.put("operatorUserId", userId);
                    json.put("projectNumber", projectNumber);
                    json.put("quotationId", quotationId);
                    json.put("supplierId", supplierId);
                    super.saveOrUpdate(json);
                }
            } catch (Exception e) {
                msgStr = "有异常,数据有误.";
                errJson.put("ROW_NUM", json.get("ROW_NUM"));
                errJson.put("ERR_MESSAGE", msgStr);
                errList.add(errJson);
                e.printStackTrace();
            }
        }

        // 获取立项中产品名称
        List<EquPonProductSpecsEntity_HI> specsEntityList = ponProductSpecsDao.findByProperty("projectId", projectId);
        //  获取报价中的产品名称
        List<String> specsNameCollect = specsEntityList.stream().map(e -> e.getProductName()).collect(Collectors.toList());
        List<EquPonQuotationProductInfoEntity_HI> productInfoEntityList = productInfoDao.findByProperty("quotationId", quotationId);
        List<String> productNameCollect = productInfoEntityList.stream().map(e -> e.getProductName()).collect(Collectors.toList());
        // 判断导入的产品和立项中产品规格的产品是否都相同，如果完全相同则通过，否则抛出异常提醒
        boolean b = specsNameCollect.containsAll(productNameCollect) && productNameCollect.containsAll(specsNameCollect);
        if(!b){
            throw new IllegalArgumentException("导入的产品和立项中的产品不完全相同或报价价格为空，请重新导入");
        }

        if (!errList.isEmpty()) {
            throw new Exception(errList.toJSONString());
        }
        return jsonArray.size();
    }

    @Override
    public List<EquPonQuotationProductInfoEntity_HI_RO> findQuoProductInfo(JSONObject jsonObject) {
        StringBuffer sql = new StringBuffer(EquPonQuotationProductInfoEntity_HI_RO.SELECT_SEQ);
        sql.append(" and t.quotation_id =" + jsonObject.getInteger("quotationId"));
//            .append(" and t.project_number = " +"'"+ jsonObject.getString("projectNumber")+"'");
        sql.append(" order by  t.specs_id");
        List<EquPonQuotationProductInfoEntity_HI_RO> list = equPonQuotationProductInfoDAO_HI_RO.findList(sql, new HashMap<>(4));
        return list;
    }

    @Override
    public String deleteQuotationProductInfo(JSONObject jsonObject, int userId) {

        Integer quotationProductId = jsonObject.getInteger("quotationProductId");
        EquPonQuotationProductInfoEntity_HI entity = productInfoDao.getById(quotationProductId);
        if (!ObjectUtils.isEmpty(entity)) {
            productInfoDao.delete(entity);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
        }
    }

    @Override
    public ResultFileEntity productTemplateExport(JSONObject jsonObject) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 设置sheet标题
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 生成报价编号格式yyyyMMdd00001
        String generateCode = generateCodeServer.generateCode(sdf.format(new Date()), 3);
        ExportExcelEntity exportExcelEntity = null;
        // 区分第一次报价和二次报价生成数据
        if (StringUtils.equals("20", jsonObject.getString("orderType"))) {
            exportExcelEntity = generateSecondProductExport(jsonObject, generateCode);
        } else {
            exportExcelEntity = generateFirstProductExport(jsonObject, generateCode);
        }
        // 创建Excel的工作sheet HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls；
        //XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx
        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet(exportExcelEntity.getSheetTitle());
        // 向工作表中填充内容
        writeExcelSheet(exportExcelEntity, sheet, jsonObject);
        wb.write(os);
        byte[] bytes = os.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
        ResultFileEntity resultFileEntity = null;
        if (StringUtils.equals("20", jsonObject.getString("orderType"))) {
            resultFileEntity = fastDfsServer.fileUpload(is, "二次报价表.xlsx");
        }else {
            resultFileEntity = fastDfsServer.fileUpload(is, "产品报价表.xlsx");
        }

        return resultFileEntity;
    }

    private ExportExcelEntity generateSecondProductExport(JSONObject jsonObject, String generateCode) throws Exception {
        String sheetTitle = "二次报价产品报价表-" + generateCode;
        // 构建列名
        List<String> sheetFieldsName = new ArrayList<>();
        sheetFieldsName.add("*产品");
//        sheetFieldsName.add("*承载产品的包装价格(如软管、玻璃瓶等),但不含合同费用及不含税:Net Net Cost(元)");
//        sheetFieldsName.add("*承载产品的包装价格(如软管、玻璃瓶等),含OI%免费货,但不含税:Net Net Cost(1-OI%)(元)");
        sheetFieldsName.add("*承载产品的包装价格(如软管、玻璃瓶等),但不含合同费用及不含税:Net Net Cost(元)---二次议价报价");
        sheetFieldsName.add("*承载产品的包装价格(如软管、玻璃瓶等),含OI%免费货,但不含税:Net Net Cost(1-OI%)(元)---二次议价报价");
        // 构建数据
        List<EquPonQuotationProductInfoEntity_HI> productInfoList = productInfoDao.findByProperty("quotationId", jsonObject.getInteger("sourceId"));
        if (CollectionUtils.isEmpty(productInfoList)) {
            throw new Exception("报价单据首次报价产品表不能为空");
        }
        // 设置列宽
        List<Integer> sheetColWidth = new ArrayList<Integer>();
        sheetColWidth.add(0, 2000);
        sheetColWidth.add(1, 25000);
        sheetColWidth.add(2, 25000);
//        sheetColWidth.add(3, 18000);
//        sheetColWidth.add(4, 18000);
        // 构建表单内容实体
        return new ExportExcelEntity(sheetTitle, sheetFieldsName, productInfoList, sheetColWidth);
    }

    private ExportExcelEntity generateFirstProductExport(JSONObject jsonObject, String generateCode) throws Exception {
        String sheetTitle = "产品报价表-" + generateCode;
        // 构建列名
        List<String> sheetFieldsName = new ArrayList<>();
        sheetFieldsName.add("*产品");
        sheetFieldsName.add("*承载产品的包装价格(如软管、玻璃瓶等),但不含合同费用及不含税:Net Net Cost(元)");
        sheetFieldsName.add("*承载产品的包装价格(如软管、玻璃瓶等),含OI%免费货,但不含税:Net Net Cost(1-OI%)(元)");
        // 构建数据
        List<EquPonProductSpecsEntity_HI> productSpecsList = productSpecsDao.findByProperty("projectId", jsonObject.getInteger("projectId"));
        if (CollectionUtils.isEmpty(productSpecsList)) {
            throw new Exception("立项单据产品规格列表为空");
        }
        List<EquPonQuotationProductInfoEntity_HI> productInfoList = Lists.newArrayList();
        for (EquPonProductSpecsEntity_HI productSpecsEntity : productSpecsList) {
            EquPonQuotationProductInfoEntity_HI productInfo = new EquPonQuotationProductInfoEntity_HI();
            productInfo.setProductName(productSpecsEntity.getProductName());
            productInfo.setSpecsId(productSpecsEntity.getSpecsId());
            productInfoList.add(productInfo);
        }
        // 设置列宽
        List<Integer> sheetColWidth = new ArrayList<>();
        sheetColWidth.add(0, 2000);
        sheetColWidth.add(1, 20000);
        sheetColWidth.add(2, 20000);
        // 构建表单内容实体
//        List<EquPonQuotationProductInfoEntity_HI> sortedList = productInfoList.stream().sorted((e1, e2) -> e1.getProductName().compareTo(e2.getProductName())).collect(Collectors.toList());
        return new ExportExcelEntity(sheetTitle, sheetFieldsName, productInfoList, sheetColWidth);
    }

    private void writeExcelSheet(ExportExcelEntity expoEntity, SXSSFSheet sheet, JSONObject jsonObject) {
        // 总列数
        int colsCount = expoEntity.getSheetFieldsName().size();
        // 创建Excel的sheet的一行
        SXSSFRow row = sheet.createRow(0);
        // 创建sheet的列名
        SXSSFCell rowCell = null;
        for (int i = 0; i < colsCount; i++) {
            rowCell = row.createCell(i);
            String fieldName = expoEntity.getSheetFieldsName().get(i);
            rowCell.setCellValue(fieldName);
            // 设置自定义列宽
            if (expoEntity.getSheetColWidth() != null) {
                sheet.setColumnWidth(i, expoEntity.getSheetColWidth().get(i));
            }
        }
        //向表格内写入获取的动态数据
        List<EquPonQuotationProductInfoEntity_HI> sheetData = expoEntity.getSheetData();

        // 对获取的动态数据按照产品名称进行排序
        sheetData = sheetData.stream().sorted((e1, e2) -> e1.getSpecsId().compareTo(e2.getSpecsId())).collect(Collectors.toList());
        for (int i = 0; i < sheetData.size(); i++) {
            row = sheet.createRow(1 + i);
            SXSSFCell productName = row.createCell(0);
            productName.setCellType(CellType.STRING);
            productName.setCellValue(sheetData.get(i).getProductName());
            // 二次报价生成数据
            if (StringUtils.equals("20", jsonObject.getString("orderType"))) {
                SXSSFCell bargainFirstPrice = row.createCell(1);
                bargainFirstPrice.setCellType(CellType.NUMERIC);
                if (!ObjectUtils.isEmpty(sheetData.get(i).getBargainFirstPrice())) {
                    bargainFirstPrice.setCellValue(Double.parseDouble(sheetData.get(i).getBargainFirstPrice().toString()));
                }
                SXSSFCell bargainTwoPrice = row.createCell(2);
                bargainTwoPrice.setCellType(CellType.NUMERIC);
                if (!ObjectUtils.isEmpty(sheetData.get(i).getBargainTwoPrice())) {
                    bargainTwoPrice.setCellValue(Double.parseDouble(sheetData.get(i).getBargainTwoPrice().toString()));
                }
                //  如果是一次报价
            }else {
                SXSSFCell firstPrice = row.createCell(1);
                firstPrice.setCellType(CellType.NUMERIC);
                if (!ObjectUtils.isEmpty(sheetData.get(i).getFirstProductPrice())) {
                    firstPrice.setCellValue(Double.parseDouble(sheetData.get(i).getFirstProductPrice().toString()));
                }
                SXSSFCell secondPrice = row.createCell(2);
                secondPrice.setCellType(CellType.NUMERIC);
                if (!ObjectUtils.isEmpty(sheetData.get(i).getTwoProductPrice())) {
                    secondPrice.setCellValue(Double.parseDouble(sheetData.get(i).getTwoProductPrice().toString()));
                }
            }
        }
    }
}
