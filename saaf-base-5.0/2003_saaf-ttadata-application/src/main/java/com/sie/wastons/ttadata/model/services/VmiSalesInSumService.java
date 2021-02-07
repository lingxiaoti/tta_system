package com.sie.wastons.ttadata.model.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.wastons.ttadata.model.entities.VmiSalesInSumEntity_HI;
import com.sie.wastons.ttadata.model.inter.IVmiSalesInSum;
import com.sie.wastons.view.ApiRequest;
import com.sie.wastons.view.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api("获取销售数据")
@Slf4j
@RestController
@RequestMapping("/vmiSalesInSumService/v2")
public class VmiSalesInSumService {

    @Autowired
    IVmiSalesInSum vmiSalesInSumServer;


    @ApiOperation(value = "获取销售数据", notes = "获取销售数据")
    @RequestMapping(method = RequestMethod.POST, value = "findTtaSalesInSumInfo")
    public ApiResponse<List<VmiSalesInSumEntity_HI>> findTtaSalesInSumInfo(@RequestBody ApiRequest<JSONObject> params) throws Exception {
        List<VmiSalesInSumEntity_HI> result = vmiSalesInSumServer.findSalesInSum(params.getParams().getString("store"),params.getParams().getString("itemCode"));
        return ApiResponse.SUCCESS().fluentSetData(result);
    }

    @ApiOperation(value = "获取上级邮箱地址", notes = "获取上级邮箱地址")
    @RequestMapping(method = RequestMethod.POST, value = "findMgrEmail")
    public ApiResponse<String[]> findMgrEmail(@RequestBody ApiRequest<JSONObject> params) throws Exception{
        String[] mgr = vmiSalesInSumServer.findMgrEmail(params.getParams().getBigDecimal("userId"),params.getParams().getBigDecimal("createdBy"));
        return ApiResponse.SUCCESS().fluentSetData(mgr);
    }

    @ApiOperation(value = "获取汇报线邮箱地址", notes = "获取汇报线邮箱地址")
    @RequestMapping(method = RequestMethod.POST, value = "findMgrLineEmail")
    public ApiResponse<String[]> findMgrLineEmail(@RequestBody ApiRequest<JSONObject> params) throws Exception{
        String[] mgr = vmiSalesInSumServer.findMgrLineEmail(params.getParams().getBigDecimal("userId"));
        return ApiResponse.SUCCESS().fluentSetData(mgr);
    }

}
