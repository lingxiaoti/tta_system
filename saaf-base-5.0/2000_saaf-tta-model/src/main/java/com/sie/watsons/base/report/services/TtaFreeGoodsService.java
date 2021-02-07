package com.sie.watsons.base.report.services;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsEntityModel;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.TtaFreeGoods;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/30/030.
 */
@RestController
@RequestMapping("/ttaFreeGoodsService")
public class TtaFreeGoodsService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsService.class);

    @Autowired
    private TtaFreeGoods ttaFreeGoodsServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.ttaFreeGoodsServer;
    }


    /**
     * 按照部门查询审批通过的FG流程信息
     */
    @RequestMapping(method = RequestMethod.POST,value = "findProcessSummaryFgInfo")
    public String findProcessSummaryFgInfo(@RequestParam(required = false) String params){
        try{
            JSONObject jsonObject = parseObject(params);
            UserSessionBean sessionBean = getUserSessionBean();
            Pagination<TtaFreeGoodsEntity_HI_RO> processSummaryInfo = ttaFreeGoodsServer.findProcessSummaryInfo(jsonObject, sessionBean);
            jsonObject = (JSONObject) JSON.toJSON(processSummaryInfo);
            jsonObject.put(SToolUtils.STATUS,"S");
            jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
            return jsonObject.toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params    {
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 查询列表（带分页）
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params)) {
                jsonObject = JSON.parseObject(params);
            }
            UserSessionBean userSessionBean = this.getUserSessionBean();
            Pagination<TtaFreeGoodsEntity_HI_RO> result = ttaFreeGoodsServer.saveFind(jsonObject,userSessionBean, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
    /**
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "dataImport")
    public String dataImport(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            TtaFreeGoodsEntity_HI instance = ttaFreeGoodsServer.saveOrUpdate(jsonObject, userId);
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
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            ttaFreeGoodsServer.saveOrUpdateAll(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params- 主键
     * @description 删除
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        try {
            if (StringUtils.isBlank(params)) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("ids").split(",");
            for (String pkId : ids) {
                ttaFreeGoodsServer.delete(Integer.parseInt(pkId));
            }
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * FG 免费货excel导出
     * @param batchId
     * @param request
     * @param response
     */
    @RequestMapping(value = "freeGoodsExcelEmport", method = {RequestMethod.GET})
    public void FileDownload(@RequestParam String batchId, HttpServletRequest request, HttpServletResponse response) {
        ExcelWriter writer = null;
        OutputStream out = null;
        try {
            Assert.notNull(batchId,"缺少批次号");
            Integer userId = this.getSessionUserId();
            //查询
            List<TtaFreeGoodsEntity_HI_RO> freeGoodList = ttaFreeGoodsServer.findfFreeGoodList(batchId,userId);
            //List<TtaFreeGoodsEntityModel> ttaFreeGoodsEntityModels = JSONObject.parseArray(JSONObject.toJSONString(freeGoodList), TtaFreeGoodsEntityModel.class);
            List<TtaFreeGoodsEntityModel> targeList = new ArrayList<>();
            for (TtaFreeGoodsEntity_HI_RO hi_ro : freeGoodList) {
                TtaFreeGoodsEntityModel entityModel = new TtaFreeGoodsEntityModel();
                BeanUtils.copyProperties(hi_ro,entityModel);
                targeList.add(entityModel);
            }

            out = response.getOutputStream();
            writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            String fileName = "FreeGoods报表";
            Sheet sheet = new Sheet(1, 0, TtaFreeGoodsEntityModel.class);
            sheet.setSheetName("freeGoods");
            sheet.setAutoWidth(true);
            writer.write(targeList, sheet);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "ISO8859-1"));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.finish();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存reportHeader头信息,并且生成FG数据
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveFreeGoodsByPoList")
    public String saveFreeGoodsByPoList(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = this.parseObject(params);
            UserSessionBean userSessionBean = this.getUserSessionBean();
            JSONObject instance = ttaFreeGoodsServer.saveFreeGoodsByPoList(jsonObject,userSessionBean, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("--FG异常报错--:{}"+ e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error("--FG错误--:{}" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}

