package com.sie.saaf.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Optional;

public class HttpUtils {

  public static String doGet(String url, JSONObject json) {
    DefaultHttpClient client = new DefaultHttpClient();
    HttpGet get = new HttpGet(url);
    try {
      StringEntity s = new StringEntity(json.toJSONString(), "utf-8");
      s.setContentEncoding("UTF-8");
      HttpResponse res = client.execute(get);
      if (res.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
        return EntityUtils.toString(res.getEntity(), "utf-8");// 返回json格式：
      } else {
        return new JSONObject().fluentPut("code", res.getStatusLine().getStatusCode()).toJSONString();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  public static String doPost(String url, JSONObject json) {
    DefaultHttpClient client = new DefaultHttpClient();
    HttpPost post = new HttpPost(url);
    try {
      StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");
      stringEntity.setContentType("application/json");
      stringEntity.setContentEncoding("UTF-8");
      post.setEntity(stringEntity);
      HttpResponse res = client.execute(post);
      //log.info(EntityUtils.toString(res.getEntity(), "utf-8"));
      if (res.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
        return EntityUtils.toString(res.getEntity(), "utf-8");// 返回json格式：
      } else {
        return new JSONObject().fluentPut("code", res.getStatusLine().getStatusCode()).toJSONString();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
