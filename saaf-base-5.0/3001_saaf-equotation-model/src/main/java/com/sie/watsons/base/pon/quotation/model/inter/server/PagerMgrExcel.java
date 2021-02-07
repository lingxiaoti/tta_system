//package com.sie.watsons.base.pon.quotation.model.inter.server;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.sie.saaf.common.bean.ResultFileEntity;
//import com.sie.saaf.common.model.inter.IFastdfs;
//import com.sie.watsons.base.pon.project.model.entities.EquPonProductSpecsEntity_HI;
//import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
//import com.yhg.hibernate.core.dao.ViewObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//@Component("pagerMgrExcel")
//public class PagerMgrExcel extends ExportExcelBase {
//
//    @Autowired
//    private ViewObject<EquPonProjectInfoEntity_HI> projectInfoDao;
//    @Autowired
//    private ViewObject<EquPonProductSpecsEntity_HI> productSpecsDao;
//    @Autowired
//    private IFastdfs fastDfsServer;
//
//    /**
//     *
//     * @Title: exportExcel @Description: 导出Excel @param param @return 参数 @return
//     * void @throws
//     */
//    public ResultFileEntity exportExcel(HttpServletRequest request, HttpServletResponse response, JSONArray jarr,JSONObject jsonObject) throws Exception {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//
//            // 构建sheet名
//            String sheetName = "试卷结构导出";
//
//            // 构建标题
//            String sheetTitle = "试卷结构导出";
//
//            // 构建列名
//            ArrayList<String> sheetFieldsName = new ArrayList<String>();
//            sheetFieldsName.add("姓名");
//            sheetFieldsName.add("学号");
//            sheetFieldsName.add("英文");
//
//            // 构建数据
//            JSONArray jaDatas = new JSONArray();
//
////            String projectNumber = jsonObject.getString("projectNumber");
////            List<EquPonProjectInfoEntity_HI> projectEntityList = projectInfoDao.findByProperty("projectNumber", projectNumber);
////            Integer projectId = projectEntityList.get(0).getProjectId();
////            List<EquPonProductSpecsEntity_HI> productSpecsList = productSpecsDao.findByProperty("projectId", projectId);
////            if(CollectionUtils.isEmpty(productSpecsList)){
////                throw new Exception("立项单据产品规格列表为空");
////            }
////            List<String> productNameList = productSpecsList.stream().map(e -> e.getProductName()).collect(Collectors.toList());
////            for (String productName : productNameList) {
////                List<Object> arr = new ArrayList<Object>();
////                JSONObject jo = new JSONObject();
////                arr.add(productName);
////                arr.add(null);
////                arr.add(null);
////                jo.put("data", arr);
////                jaDatas.add(jo);
////            }
////
//            for (int i = 0; i < 3; i++) {
//                ArrayList<Object> arr = new ArrayList<Object>();
//                JSONObject jo = new JSONObject();
//                arr.add("张三");
//                arr.add(123);
//                arr.add("Test");
//                jo.put("data", arr);
//                jaDatas.add(jo);
//            }
//
//            // 设置列宽
//            ArrayList<Integer> sheetColWidth = new ArrayList<Integer>();
//            sheetColWidth.add(0, 2000);
//            sheetColWidth.add(1, 3000);
//            sheetColWidth.add(2, 4000);
//
//            // title的高度
//            int sheetTitleHeight = 500;
//
//            // 构建表单内容实体
//            ExportExcelEntity expoEntity = new ExportExcelEntity(sheetName, sheetTitle, sheetFieldsName, jaDatas,
//                    sheetColWidth, sheetTitleHeight);
//
//            List<ExportExcelEntity> sheets = new ArrayList<ExportExcelEntity>();
//            sheets.add(expoEntity);
//            setSheets(sheets);
////            os = response.getOutputStream();
//
//            // 如果要自定义写入表单数据调用这个方法并复写父类 writeExcelSheetSelf( ExportExcelEntity
//            // expoEntity)方法
//            // writeExcel(os,true);
//
//            // 直接调用父类模板方法
//            writeExcel(os);
//            byte[] bytes = os.toByteArray();
//            InputStream is = new ByteArrayInputStream(bytes);
////            fastDfsServer.fileUpload(, )
//            ResultFileEntity result = fastDfsServer.fileUpload(is, "产品表导入明细.xlsx");
//            System.out.println(1);
//            return result;
//        } catch (Exception e) {
//            throw new Exception("Export Excel failed, beacause" + e.getMessage());
//        }
//    }
//}
