package com.sie.wastons.ttadata.model.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.wastons.ttadata.model.entities.readyonly.TtaVendorInEntity_HI_RO;
import com.sie.wastons.ttadata.model.inter.server.TtaVendorInServer;
import com.sie.wastons.view.ApiRequest;
import com.sie.wastons.view.ApiResponse;
import com.yhg.hibernate.core.paging.Pagination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by user on 2019/10/31.
 */
@Api("供应商数据查询")
@Slf4j
@RestController
@RequestMapping("/ttaVendorInService")
public class TtaVendorInService {

  @Autowired
  TtaVendorInServer ttaVendorInServer;

  @ApiOperation(value = "供应商数据", notes = "供应商数据")
  @RequestMapping(method = RequestMethod.POST, value = "findTtaVendorInInfo")
  public ApiResponse<List<TtaVendorInEntity_HI_RO>> findTtaVendorInInfo(@RequestBody ApiRequest<JSONObject> params) throws IllegalAccessException {
    List<TtaVendorInEntity_HI_RO> result = ttaVendorInServer.findTtaVendorInInfo(params);
    return ApiResponse.SUCCESS().fluentSetData(result);
  }

  @ApiOperation(value = "供应商列表挑选", notes = "供应商列表挑选")
  @RequestMapping(method = RequestMethod.POST, value = "findTtaVendorInList")
  public ApiResponse<List<TtaVendorInEntity_HI_RO>> findTtaVendorInList(@RequestBody ApiRequest<JSONObject> params) throws IllegalAccessException {
    Pagination<TtaVendorInEntity_HI_RO> result = ttaVendorInServer.findTtaVendorInList(params);
    return ApiResponse.SUCCESS().fluentSetPageData(result);
  }

  @ApiOperation(value = "供应商分组数据", notes = "供应商分组数据")
  @RequestMapping(method = RequestMethod.POST, value = "findAutomaticTtaVendorIn")
  public List<TtaVendorInEntity_HI_RO> findAutomaticTtaVendorIn() throws IllegalAccessException {
    List<TtaVendorInEntity_HI_RO> result = ttaVendorInServer.findAutomaticTtaVendorIn();
    return result;
  }

  @ApiOperation(value = "供应商分组数据明细", notes = "供应商分组数据明细")
  @RequestMapping(method = RequestMethod.POST, value = "findAutomaticTtaVendorInInfo")
  public List<TtaVendorInEntity_HI_RO> findAutomaticTtaVendorInInfo(@RequestParam("params") String params) throws IllegalAccessException {
    List<TtaVendorInEntity_HI_RO> result = ttaVendorInServer.findAutomaticTtaVendorInInfo(params);
    return result;
  }

}
