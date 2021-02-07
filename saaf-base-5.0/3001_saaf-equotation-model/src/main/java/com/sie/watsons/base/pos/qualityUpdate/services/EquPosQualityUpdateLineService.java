package com.sie.watsons.base.pos.qualityUpdate.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.qualityUpdate.model.inter.IEquPosQualityUpdateLine;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/equPosQualityUpdateLineService")
public class EquPosQualityUpdateLineService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosQualityUpdateLineService.class);
    @Autowired
    private IEquPosQualityUpdateLine equPosQualityUpdateLineServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.equPosQualityUpdateLineServer;
    }

    /**
     * 批量导入
     *
     * @param params
     * @return
     */
    @PostMapping("/saveImportForQualityUpdate")
    public String saveImportForQualityUpdate(@RequestParam(required = true) String params) {
        try {
            Integer userId = getSessionUserId();
            int size = equPosQualityUpdateLineServer.saveImportForQualityUpdate(params, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共" + size + "条数据导入成功", size, null).toString();
        } catch (IllegalArgumentException e) {
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
        }
    }

    @PostMapping("/findQualityUpdateLinePagination")
    public String findQualityUpdateLinePagination(@RequestParam("params") String editParams,
                                                  @RequestParam(defaultValue = "1", required = false) Integer pageIndex,
                                                  @RequestParam(defaultValue = "10", required = false) Integer pageRows) {
        try {
            JSONObject jsonObject = parseObject(editParams);
            if (jsonObject.getInteger("updateHeadId") == null) {
                throw new Exception("查询头id不能为空");
            }
            jsonObject = equPosQualityUpdateLineServer.findQualityUpdateLinePagination(jsonObject, pageIndex, pageRows);
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
            jsonObject.put("data", data);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/deleteQualityUpdateLine")
    public String deleteQualityUpdateLine(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            if (jsonObject.getString("docStatus") != null && !"DRAFT".equals(jsonObject.getString("docStatus"))) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "只有单据状态为拟定时才可以删除", 0, null).toString();
            }
            return equPosQualityUpdateLineServer.deleteQualityUpdateLine(jsonObject, userId);
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}