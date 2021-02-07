package com.sie.saaf.dataexport.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sie.saaf.app.DataExportApplication;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.dataexport.model.entities.BaseExportRecordEntity_HI;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangJun
 * @createTime 2018-06-22 11:33
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataExportApplication.class)
public class TestExport {

	MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationConnect;

	@Autowired
	private ViewObject<BaseExportRecordEntity_HI> baseExportRecordDAO_HI;

	@Autowired
	private DynamicBaseViewObject<JSONObject> commonDAO_HI_DY;

	@Before
	public void setUp() throws JsonProcessingException {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();

	}

	@Test
	public void testExport() throws Exception {
//		String expectedResult = "hello world!";
		String uri = "/export";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
	}


	@Test
	@Transactional()
	public void testQuery(){
		StringBuffer sql=new StringBuffer("SELECT id AS id,export_id as id,user_id AS userId, request_URL AS requestUrl FROM base_export_record where 1=1");
		Map<String,Object> map=new HashMap<>();
		com.alibaba.fastjson.JSONObject  jsonObject=new com.alibaba.fastjson.JSONObject().fluentPut("requestUrl_iclike","http");
		SaafToolUtils.parperHbmParam(null,jsonObject,sql,map);
		System.out.println(sql);
		System.out.println();
		System.out.println(JSON.toJSONString(commonDAO_HI_DY.findList(sql,map)));
	}
}
