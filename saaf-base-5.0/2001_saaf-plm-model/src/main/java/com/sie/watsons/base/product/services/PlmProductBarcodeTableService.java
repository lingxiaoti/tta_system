package com.sie.watsons.base.product.services;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.inter.IPlmProductBarcodeTable;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/plmProductBarcodeTableService")
public class PlmProductBarcodeTableService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductBarcodeTableService.class);

	@Autowired
	private IPlmProductBarcodeTable plmProductBarcodeTableServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductBarcodeTableServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "DeleteBarcodeById")
	public String deleteByid(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			Integer id = param.getInteger("id");
			plmProductBarcodeTableServer.deleteById(id);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	public static void main(String[] args) {
		Scanner s1 = new Scanner(System.in);
		System.out.println("输入第一个数");
		int a = s1.nextInt();

		System.out.println("输入第二个数");
		int b = s1.nextInt();

		System.out.println("输入第三个数");
		int c = s1.nextInt();
		if (a < b) {
			int t = a;
			a = b;
			b = t;
		}
		if (b < c) {
			int t = b;
			b = c;
			c = t;
		}
		if (a < b) {
			int t = a;
			a = b;
			b = t;
		}
		System.out.println(a + "==" + b + "==" + c);
	}

}