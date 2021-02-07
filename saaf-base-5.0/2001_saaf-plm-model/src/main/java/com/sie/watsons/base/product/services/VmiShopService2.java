package com.sie.watsons.base.product.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.entities.readonly.VmiShopEntity_HI_RO2;
import com.sie.watsons.base.product.model.inter.IVmiShop2;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/vmiShopService2")
public class VmiShopService2 extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VmiShopService2.class);

	@Autowired
	private IVmiShop2 vmiShopServer2;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.vmiShopServer2;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findShopinfo")
	public String findShopinfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<VmiShopEntity_HI_RO2> dataList = vmiShopServer2
				.findShopinfo(queryParamJSON, pageIndex, pageRows);
		// ResultUtils.getCreator(queryParamJSON.getInteger("varUserId"));
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

	public static String httpGet(String urlStr, List<String> urlParam)
			throws IOException {
		if (!urlParam.isEmpty()) {
			urlStr += "?";
			// 定义一个迭代器，并将MAP值的集合赋值
			for (String string : urlParam) {
				urlStr += string + "&";
			}
			urlStr = urlStr.substring(0, urlStr.length() - 1);
		}
		// 实例一个URL资源
		URL url = new URL(urlStr);
		// 实例一个HTTP CONNECT
		HttpURLConnection connet = (HttpURLConnection) url.openConnection();
		connet.setRequestMethod("GET");
		connet.setRequestProperty("Charset", "UTF-8");
		connet.setRequestProperty("Content-Type", "application/json");
		connet.setConnectTimeout(15000);// 连接超时 单位毫秒
		connet.setReadTimeout(15000);// 读取超时 单位毫秒
		if (connet.getResponseCode() != 200) {
			System.out.println("请求异常" + urlStr);
			return "";
		}
		// 将返回的值存入到String中
		BufferedReader brd = new BufferedReader(new InputStreamReader(
				connet.getInputStream(), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = brd.readLine()) != null) {
			sb.append(line);
		}
		brd.close();
		connet.disconnect();
		return sb.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "findArea")
	public String findArea(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<VmiShopEntity_HI_RO2> dataList = vmiShopServer2.findArea(
				queryParamJSON, pageIndex, pageRows);
		// ResultUtils.getCreator(queryParamJSON.getInteger("varUserId"));
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

}