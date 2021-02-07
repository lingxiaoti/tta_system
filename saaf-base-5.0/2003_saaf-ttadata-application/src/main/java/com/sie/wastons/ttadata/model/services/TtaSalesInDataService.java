package com.sie.wastons.ttadata.model.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.wastons.ttadata.model.entities.readyonly.TtaSalesInEntity_HI_RO;
import com.sie.wastons.ttadata.model.inter.server.TtaSalesInDataServer;
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
 * Created by user on 2019/10/28.
 */
@Api("销售数据查询")
@Slf4j
@RestController
@RequestMapping("/ttaSalesInDataService")
public class TtaSalesInDataService {

  @Autowired
  TtaSalesInDataServer ttaSalesInDataServer;

  @ApiOperation(value = "获取销售数据",notes = "销售数据")
  @RequestMapping(method = RequestMethod.POST, value = "findTtaSalesInInfo")
  public ApiResponse<List<TtaSalesInEntity_HI_RO>> findTtaSalesInInfo(@RequestBody ApiRequest<JSONObject> params) throws Exception{
    Pagination<TtaSalesInEntity_HI_RO> result = ttaSalesInDataServer.findTtaSalesInInfo(params);
    return ApiResponse.SUCCESS().fluentSetPageData(result);
  }

  @ApiOperation(value = "获取财务日历数据",notes = "销售数据")
  @RequestMapping(method = RequestMethod.POST, value = "findTradeCalendar")
  public ApiResponse<List<TtaSalesInEntity_HI_RO>> findTradeCalendar(@RequestParam(required = false) String params) throws IllegalAccessException{
    JSONObject json = JSONObject.parseObject(params);
    return ApiResponse.SUCCESS().fluentSetData(ttaSalesInDataServer.findTradeCalendar(json.getString("reserveDate")));
  }

}
