package com.sie.saaf.schedule.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SaafToolUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaafToolUtils.class);

	/*public static final ApplicationContext context = new FileSystemXmlApplicationContext(
			"classpath:com/sie/saaf/app/config/spring.hibernate.cfg.xml");*/

	/**
	 * 功能描述： 关闭资源
	 * 
	 * @author xiaoga
	 * @date 2019/7/2
	 * @param
	 * @return
	 */
	public static void close(FTPClient ftpClient, InputStream ins, BufferedReader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
			if (ins != null) {
				ins.close();
			}
			if (ftpClient != null) {
				ftpClient.getReply();
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (Exception e) {
			LOGGER.error("close exception error:{}", e);
		}
	}

	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			LOGGER.error("toJson error:{}", e);
			return "";
		}
	}

	
	
	/**
	 *  将文件流组装成业务列表数据
	 * @param reader 
	 * @param separate 如,|等
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("all")
	public static  List<Map<String, Object>> fileDataAssembleList(BufferedReader reader, final String separate) throws Exception {
		int idx = -1;
		String line = null;
		List<Map<String, Object>> dataList = new  ArrayList<>();
		List<String> headList = null; //头部参数信息
		while ((line = reader.readLine()) != null) {
			if ("".equals(line.trim())) {
			 	continue;
			}
			idx ++;
			String[] dataArray = line.split(separate);
			if (idx == 0 || dataArray == null || dataArray.length == 0) {
				String[] keyArray = line.split(separate);
				headList = Arrays.asList(keyArray);
				continue;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			for (int index = 0; index < dataArray.length; index ++) {
				if (StringUtils.isBlank(dataArray[index])) {
					dataArray[index] = null;
				} else {
					dataArray[index] = (dataArray[index] + "").replaceAll("\\\"", "");
				}
				params.put(headList.get(index), dataArray[index]);
			}
			SaafToolUtils.setDefaultParams(params);
		    dataList.add(params);
		}
		return dataList;
	}
	

	/**
	 *  设置默认参数
	 * @param params
	 */
	public static void setDefaultParams(Map<String, Object> params) {
		java.sql.Date createDate = new java.sql.Date(System.currentTimeMillis());
		params.put("LAST_UPDATE_DATE", createDate);
		params.put("CREATION_DATE",createDate);
		params.put("VERSION_NUM",0);
		params.put("CREATED_BY",-1);
		params.put("LAST_UPDATED_BY",-1);
		params.put("LAST_UPDATE_LOGIN", -1);
	}
}
