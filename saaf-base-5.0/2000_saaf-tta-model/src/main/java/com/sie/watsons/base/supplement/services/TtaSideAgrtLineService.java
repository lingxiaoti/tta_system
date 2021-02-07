package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtLineEntity_HI;
import com.sie.watsons.base.supplement.model.inter.ITtaSideAgrtLine;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ttaSideAgrtLineService")
public class TtaSideAgrtLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSideAgrtLineService.class);

	@Autowired
	private ITtaSideAgrtLine ttaSideAgrtLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSideAgrtLineServer;
	}

	/**
	 * 保存proposal供应商的数据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "saveTtaPoposalToSideAgrtLine")
	public String saveProposalSupplier(@RequestParam(required = true) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			//proposal供应商
			List<TtaSideAgrtLineEntity_HI> list= ttaSideAgrtLineServer.saveProposalSupplier(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,list).toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  查找数据
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaSideAgrtLineEntity_HI> findList = ttaSideAgrtLineServer.findPagination(jsonObject, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *删除proposal供应商数据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteById")
	public String deleteById(@RequestParam(required = false) String params) {
		String result = null;
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer id = jsonObject.getInteger("id");
			Assert.notNull(id, "参数id错误");
			TtaSideAgrtLineEntity_HI ttaSideAgrtLineEntity_hi = ttaSideAgrtLineServer.deleteSupplierBySideAgrtLineId(id);
			result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, ttaSideAgrtLineEntity_hi);
		} catch (Exception e) {
			LOGGER.error(".deleteById:{}" , e);
			result = this.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, null);
		}
		LOGGER.info(".deleteById 入参信息:{},出参信息:{}", new Object[]{params, result});
		return result;
	}

}