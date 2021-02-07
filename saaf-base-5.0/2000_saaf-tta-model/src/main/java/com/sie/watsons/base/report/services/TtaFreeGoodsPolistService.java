package com.sie.watsons.base.report.services;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsPolistEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsPolistEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsPolistEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaFreeGoodsPolist;
import com.sie.watsons.base.report.utils.BasicExcelListener;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.report.utils.OrderStoreItemModal;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/ttaFreeGoodsPolistService")
public class TtaFreeGoodsPolistService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsPolistService.class);

    @Autowired
    private ITtaFreeGoodsPolist ttaFreeGoodsPolistServer;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.ttaFreeGoodsPolistServer;
    }

    /**
     * 批量导入po_list数据
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "saveImportInfo")
    public String saveImportTaxInfo(@RequestParam("file") MultipartFile file){
        try{
            Assert.notNull(file,"No choose file,upload failed");
            String params = "";
            JSONObject jsonObject = parseObject(params);
            UserSessionBean sessionBean = getUserSessionBean();
            int size = ttaFreeGoodsPolistServer.saveImportInfo(jsonObject,file,sessionBean);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
        }catch (Exception e){
            jedisCluster.setex(getUserSessionBean().getCertificate(),3600,"{status:'E',currentStage:'失败',orderNum:"+"'"+e.getMessage()+"+'}");
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     *
     * @param params JSON参数，查询条件的JSON格式
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
            JSONObject jsonObject = parseObject(params);
            Pagination<TtaFreeGoodsPolistEntity_HI_RO> result = ttaFreeGoodsPolistServer.findInfo(jsonObject,pageIndex,pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS,"S");
            jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
            return jsonObject.toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "deleteImportInfo")
    public String deleteImportInfo(@RequestParam(required = false) String params){
        try{
            JSONObject jsonObject = parseObject(params);
            JSONObject result = ttaFreeGoodsPolistServer.deleteImportInfo(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = this.parseObject(params);
            TtaFreeGoodsPolistEntity_HI instance = ttaFreeGoodsPolistServer.saveOrUpdate(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 接口测试调用
     * 支持导入百万级excel数据
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "importExcelData")
    public String importExcelData(@RequestParam("file") MultipartFile file){
        try{
            System.out.println("读取文件开始");
            long start = System.currentTimeMillis();
            Assert.notNull(file,"No choose file,upload failed");
            BasicExcelListener listener = new BasicExcelListener(jdbcTemplate);
            //InputStream inputStream = new FileInputStream(new File("D:\\aaa\\abc_2007.xlsx"));
            EasyExcelFactory.readBySax(file.getInputStream(), new Sheet(1, 1, OrderStoreItemModal.class), listener);
            long stop = System.currentTimeMillis();
            System.out.println("读取文件并执行sql结束，使用时间："+(stop-start)+"毫秒");
            return "导入成功";
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}