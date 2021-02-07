package com.sie.saaf.common.util;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ContractCreateAndSendInfo_RO;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class InterfaceSendAndReceive {

    private static  String serverHost; //= "http://10.82.27.165:8092";//"https://UAT_e-signature.watsonsvip.com.cn";//https://api.bestsign.info
    private static  String clientId; // = "1589440821014756729";
    private static  String clientSecret;// = "7LNdaZE6jzVPw1rAVYT9hLEDmRQW7oq0";
    private static  String privateKey;// = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALKKlyuhYoV0b/qfAt1gWU37lHM1bXzsDaRsQjQbByOpr7bYia7SysV6Et6A6Kw4zXPh9VzUh9ocfIJKU3B8fKLh8vwbjYoC1TNQ8gBvHMSxVlFKZbVuqaiF8I3RXwVwZ0OzN/TvCHGu33M+B+Oz59m1keyEumM/0uivt+JSXVlhAgMBAAECgYB96/0+im5ADaVEr42HsQKLW5qRvubfqmTcyHXQ5hcc8fq6DPLeWFEnAjf4shZoEQ/mYUF6HXNLQ7gU9HLw7PvBf9gr4/IOzMSO2MR10DwoYJdbEr8VXqJI5jwdvhBh4uH9Ru+pHtkfxgQrmilqZHq+hU+r4tUWwnJkKO6RHt0CgQJBANmvT0lFZYUsYUNv9Z+cC4tuVCPapSAa/H6a0DwvtqnW98+bQjGxfKAvjKHDLaZihZKw0h2bSTTLiI3WpivluwkCQQDR94LiIhx0mh+eE9K2gJNqbgtW8ApwCKpR5kXn9lx7X7NrpKxeFmACpZFwDfc1SryrW5BFJ3Hoga8jGCHVo0mZAkEAub3MeUithySHGXO+saIEd8R8Jtu5aduc9qpCZBiSU/kJGivwH1bCcCwiYySXHxGRx8j4gCqwHExPCDfPMkWjgQJBAI8m2ztl2lS9R9U02rLHErq7cEvDuLoNjmHsR7kQxJjw9JjtWv+C7c6gJ3poayUQYXXLJZ+gdw74a/nXWCoZ+/ECQD+0VyUqTLCzsq3xqEx1nDm1jezfdU4+FxuQp4WERI5eDQuIDquidDt7sMlcRwC2/qkPEShlbHw5bu5qBwjDP/M=";

    static {
        serverHost = ResourceUtils.getHost();
        clientId = ResourceUtils.getClientId();
        clientSecret = ResourceUtils.getClientSecret();
        privateKey = ResourceUtils.getPrivateKey();
    }

    /**
     * 获取access token
     * @throws IOException
     */
    public String queryToken() throws IOException{
        String result = BestSignClient.getBestSignClient(serverHost, clientId, clientSecret, privateKey).queryToken();
        return result;
    }

    /**
     * 关键字定位
     */
    public String keywordsSite(JSONObject json) throws Exception {
        String result = BestSignClient.getBestSignClient(serverHost, clientId, clientSecret, privateKey).executeRequest("/api/contracts/multi-calculate-positions", "POST", json);
        return result;
    }

    /**
     * 发送合同
     */
    public String sendContract(ContractCreateAndSendInfo_RO cca) throws Exception {
        String result = BestSignClient.getBestSignClient(serverHost, clientId, clientSecret, privateKey).executeRequest("/api/contracts", "POST", cca);
        return result;
    }

    /**
     * 查询合同详情（新）
     */
    public String contractDetail(String contractId,String account,String enterpriseName1) throws Exception {
        //中文放入url请求需转码
        String enterpriseName = URLEncoder.encode(enterpriseName1, "UTF-8");
        String uriWithParam =  String.format("/api/contracts/detail/%s?account=%s&enterpriseName=%s", contractId, account, enterpriseName);
        String fileData = BestSignClient.getBestSignClient(serverHost, clientId, clientSecret, privateKey).executeRequest(uriWithParam, "GET", null);
        return fileData;
    }


    /**
     * 下载合同附页
     */
    public String appendixFile(String contractId,String account,String enterpriseName1) throws Exception {
        //中文放入url请求需转码
        String enterpriseName = URLEncoder.encode(enterpriseName1, "UTF-8");
        String uriWithParam =  String.format("/api/contracts/%s/appendix-file?account=%s&enterpriseName=%s", contractId, account, enterpriseName);
        String fileData = BestSignClient.getBestSignClient(serverHost, clientId, clientSecret, privateKey).executeRequest(uriWithParam, "GET", null);
        return fileData;
    }
}
