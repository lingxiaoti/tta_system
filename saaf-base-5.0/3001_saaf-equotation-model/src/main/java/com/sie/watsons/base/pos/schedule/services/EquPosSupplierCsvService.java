package com.sie.watsons.base.pos.schedule.services;

import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.watsons.base.pos.schedule.model.inter.IEquPosSupplierCsv;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/equPosSupplierCsvService")
public class EquPosSupplierCsvService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCsvService.class);

	@Autowired
	private IEquPosSupplierCsv equPosSupplierCsvServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierCsvServer;
	}

	/**
	 *
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findExportSupplier")
	public String findExportSupplier() {
		String accessPath = null;
		try {
			ResultFileEntity result = equPosSupplierCsvServer.findExportSupplier();
//			if(result == null){
//				return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "导出失败.没有数据").toString();
//			}
//			accessPath = result.getAccessPath();
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, "").toString();
		} catch (Exception e) {
			LOGGER.info("产品明细导出失败"+e.getMessage());
		}
		return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "导出失败.请联系管理员").toString();
	}

}