package com.sie.saaf.message.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author Chenzg
 * @createTime 2018-06-12 17:36
 * @description util
 */
public class Postman {
    private static final Logger LOGGER = LoggerFactory.getLogger(Postman.class);
    /**
     * 向指定URL发送GET方法的请求
     */
    public static String get(String url) throws Exception {
        BufferedReader in = null;
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        int index = 0;
        while ((line = in.readLine()) != null || index != 3000000) {
            sb.append(line);
            index++;
        }

        // 使用finally块来关闭输入流
        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception e2) {
            LOGGER.error(e2.getMessage(),e2);
        }
        return sb.toString();
    }

    /**
     * 发送HttpPost请求
     *
     * @param strURL 服务地址
     * @param params
     * @return 成功:返回json字符串<br/>
     */
    public static String jsonPost(String strURL, Map<String, String> params) throws Exception {

        URL url = new URL(strURL);// 创建连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("POST"); // 设置请求方式
        connection.setRequestProperty("Accept", "*/*"); // 设置接收数据的格式
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置发送数据的格式
        connection.connect();
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        // out.append(JSONObject.toJSONString(params));
        out.append(stringBuffer.toString());
        out.flush();
        out.close();

        int code = connection.getResponseCode();
        InputStream is = null;
        if (code == 200) {
            is = connection.getInputStream();
        } else {
            is = connection.getErrorStream();
        }

        // 读取响应
        int length = (int) connection.getContentLength();// 获取长度
        if (length != -1) {
            byte[] data = new byte[length];
            byte[] temp = new byte[512];
            int readLen = 0;
            int destPos = 0;
            while ((readLen = is.read(temp)) > 0) {
                System.arraycopy(temp, 0, data, destPos, readLen);
                destPos += readLen;
            }
            String result = new String(data, "UTF-8"); // utf-8编码
            is.close();
            return result;
        }
        is.close();
        return "E"; // 自定义错误信息
    }

    //         * post请求表单提交
//          *    url:请求地址
//          *    headerParams:请求头对象
//          *    params:参数对象
//         * @author      huangminglin
//         * @time        2018/9/18 17:12
//         * @return      com.alibaba.fastjson.JSONObject
    public static JSONObject request(String url, HttpHeaders headerParams, JSONObject params){

        RestTemplate restTemplate =new RestTemplate();
        //header
        headerParams.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //body
        String paramsStr = params.toJSONString();
        //获取请求体对象
        HttpEntity<String> entity = new HttpEntity<>(paramsStr,headerParams);
        //得到返回信息
        ResponseEntity<JSONObject> exchange = restTemplate.postForEntity(url,entity,JSONObject.class);
        //获取JSON格式信息体
        return exchange.getBody();
    }

    public static String httpPostByRaw(String url, String jonsStr) throws Exception {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity postingString = new StringEntity(jonsStr, "UTF-8");// json传递
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(post);
        String content = EntityUtils.toString(response.getEntity());
        return content;

    }
}
