package com.sie.saaf.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.sie.saaf.common.bean.*;
import com.sie.watsons.base.elecsign.utils.RSAUtils;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import redis.clients.jedis.JedisCluster;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author whthomas
 * @date 2018/6/17
 *
 * <h3>BestSignClient should be shared</h3>
 *
 * <p>BestSignClient performs best when you create a single {@code BestSignClient} instance and reuse it for
 * all of your API calls.
 *
 * <p>Use {@code new BestSignClient()} to create a shared instance with custom settings:
 * <pre>   {@code
 *
 *   // The singleton BestSignClient client.
 *   public final BestSignClient client = new BestSignClient(
 *                                          "https://api.bestsign.info",
 *                                          "l2ZxwSA6OBs6IgsQ6og9f7ixuPMR7hMQ",
 *                                          "3seTeUqUyhGlYZfD3CbGVXPDrcu1ieG4",
 *                                          privateKey
 * );
 *
 * }</pre>
 *
 * <p>Or use {@code spring} to create a singleton instance with custom settings:
 */
public class BestSignClient {

    private JedisCluster  jedisCluster = (JedisCluster) SpringBeanUtil.getBean("jedisCluster");
    private static final Logger LOGGER = LoggerFactory.getLogger(BestSignClient.class);

    public static final String COMPANY_A = "甲方戳章";
    public static final String COMPANY_B = "乙方戳章";
    public static final String COMPANY_C = "丙方戳章";
    static ArrayList<String> chapterKeyArr = new ArrayList<>();
    public static final double X_OFFSET = 0.05;
    public static final double Y_OFFSET = 0.08;

    static {
        chapterKeyArr.add(COMPANY_A);
        chapterKeyArr.add(COMPANY_B);
        chapterKeyArr.add(COMPANY_C);
    }

    static Map<Integer, String> chapterKeyMap = new HashMap<>();

    static {
        chapterKeyMap.put(1, COMPANY_A);
        chapterKeyMap.put(2, COMPANY_B);
        chapterKeyMap.put(3, COMPANY_C);
    }


    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    /**
     * 系统的地址
     */
    private String host;
    /**
     * BestSign分配的客户端编号
     */
    private String clientId;
    /**
     * 客户端凭证
     */
    private String clientSecret;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * Token的缓存
     */
    private String tokenCache;

    /**
     * 屈臣氏主体账号
     */
    private String acount;

    public BestSignClient(String host, String clientId, String clientSecret, String privateKey) {
        this.host = host;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.privateKey = privateKey;
    }

    public BestSignClient() {
        this.host = ResourceUtils.getHost(); //"http://10.82.27.165:8092";//"https://UAT_e-signature.watsonsvip.com.cn"; //"https://api.bestsign.info"; //ResourceUtils.getHost();
        this.clientId = ResourceUtils.getClientId();//"1589440821014756729";//ResourceUtils.getClientId();
        this.clientSecret = ResourceUtils.getClientSecret(); //"7LNdaZE6jzVPw1rAVYT9hLEDmRQW7oq0";//ResourceUtils.getClientSecret();
        this.privateKey = ResourceUtils.getPrivateKey();//"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALKKlyuhYoV0b/qfAt1gWU37lHM1bXzsDaRsQjQbByOpr7bYia7SysV6Et6A6Kw4zXPh9VzUh9ocfIJKU3B8fKLh8vwbjYoC1TNQ8gBvHMSxVlFKZbVuqaiF8I3RXwVwZ0OzN/TvCHGu33M+B+Oz59m1keyEumM/0uivt+JSXVlhAgMBAAECgYB96/0+im5ADaVEr42HsQKLW5qRvubfqmTcyHXQ5hcc8fq6DPLeWFEnAjf4shZoEQ/mYUF6HXNLQ7gU9HLw7PvBf9gr4/IOzMSO2MR10DwoYJdbEr8VXqJI5jwdvhBh4uH9Ru+pHtkfxgQrmilqZHq+hU+r4tUWwnJkKO6RHt0CgQJBANmvT0lFZYUsYUNv9Z+cC4tuVCPapSAa/H6a0DwvtqnW98+bQjGxfKAvjKHDLaZihZKw0h2bSTTLiI3WpivluwkCQQDR94LiIhx0mh+eE9K2gJNqbgtW8ApwCKpR5kXn9lx7X7NrpKxeFmACpZFwDfc1SryrW5BFJ3Hoga8jGCHVo0mZAkEAub3MeUithySHGXO+saIEd8R8Jtu5aduc9qpCZBiSU/kJGivwH1bCcCwiYySXHxGRx8j4gCqwHExPCDfPMkWjgQJBAI8m2ztl2lS9R9U02rLHErq7cEvDuLoNjmHsR7kQxJjw9JjtWv+C7c6gJ3poayUQYXXLJZ+gdw74a/nXWCoZ+/ECQD+0VyUqTLCzsq3xqEx1nDm1jezfdU4+FxuQp4WERI5eDQuIDquidDt7sMlcRwC2/qkPEShlbHw5bu5qBwjDP/M=";//ResourceUtils.getPrivateKey();
        this.acount = ResourceUtils.getAcount(); //"13560327741";//屈臣氏主体登录账号
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    //shared perform best
    private final OkHttpClient okHttpClient = new OkHttpClient
            .Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .build();

    /**
     * @param uriWithParam 请求的URL和参数
     * @param method       HTTP请求动词
     * @param requestData  请求的内容
     * @return JSON字符串
     */
    public String executeRequest(String uriWithParam, String method, Object requestData) {
        return executeRequest(uriWithParam, method, requestData, 3);
    }

    private String executeRequest(String uriWithParam, String method, Object requestData, int retryTime) {
        JSONObject json = new JSONObject();
        while (retryTime > 0) {
            try {
                String token = queryToken();
                Long timestamp = System.currentTimeMillis();
                String urlWithQueryParam = String.format("%s%s", host, uriWithParam);
                final String signRSA = signRequest(uriWithParam, timestamp, requestData);
                Headers headers = new Headers
                        .Builder()
                        .add("Authorization", "bearer " + token)
                        .add("bestsign-sign-timestamp", timestamp.toString())
                        .add("bestsign-client-id", clientId)
                        .add("bestsign-signature-type", "RSA256")
                        .add("bestsign-signature", signRSA)
                        .build();

                final Request.Builder requestBuilder = new Request
                        .Builder()
                        .url(urlWithQueryParam)
                        .headers(headers);

                if (Objects.equals(method.toUpperCase(), "GET")) {
                    requestBuilder.get();
                } else {

                    final RequestBody requestBody = RequestBody.create(JSON_TYPE, requestData == null ? "" : objectMapper.writeValueAsString(requestData));
                    requestBuilder.method(method.toUpperCase(), requestBody);
                }

                final Response response = okHttpClient.newCall(requestBuilder.build()).execute();

                if (response.code() == 401) {
                    // token失效, 重新获取token
                    invalidToken(token);
                } else if (response.code() == 200) {
                    if (response.headers().get("Content-Type").equals("application/zip") || response.headers().get("Content-Type").equals("application/pdf")) {
                        return Base64.getEncoder().encodeToString(response.body().bytes());
                    }
                    return response.body() != null ? response.body().string() : null;
                }
                retryTime--;
            } catch (Exception ex) {
                retryTime--;
                json.put("data", ex.getStackTrace());
                json.put("code", "error");
                LOGGER.error("调用电子签章出错：", ex);
            }
        }
        return json.toJSONString();
    }

    private String signRequest(String uriWithParam, Long timestamp, Object requestData) throws Exception {
        String content = String.format(
                "bestsign-client-id=%sbestsign-sign-timestamp=%sbestsign-signature-type=%srequest-body=%suri=%s",
                clientId,
                timestamp.toString(),
                "RSA256",
                MD5Utils.stringMD5(requestData == null ? "" : objectMapper.writeValueAsString(requestData)),
                uriWithParam);

        return RSAUtils.signRSA(content, privateKey);
    }

    public synchronized String queryToken() throws IOException {
        if (null == tokenCache) {
            Map<String, String> requestData = new HashMap<>(2);
            requestData.put("clientId", clientId);
            requestData.put("clientSecret", clientSecret);
            final RequestBody requestBody = RequestBody.create(JSON_TYPE, objectMapper.writeValueAsString(requestData));
            Request request = new Request
                    .Builder()
                    .url(String.format("%s/api/oa2/client-credentials/token", host))
                    .post(requestBody)
                    .build();

            final Response response = okHttpClient.newCall(request).execute();
            if (response.body() != null) {
                tokenCache = objectMapper.readTree(response.body().string()).get("data").get("accessToken").asText();
                return tokenCache;
            }
        } else {
            return tokenCache;
        }
        return null;
    }

    private synchronized void invalidToken(String oldToken) {
        if (oldToken.equals(tokenCache)) {
            tokenCache = null;
        }
    }

    public static BestSignClient getBestSignClient() {
        return new BestSignClient();
    }

    public static BestSignClient getBestSignClient(String serverHost, String clientId, String clientSecret, String privateKey) {
        return new BestSignClient(serverHost, clientId, clientSecret, privateKey);
    }


    /**
     * 文件转base64的字符串
     */
    public static String PDF2Base64(InputStream is) {
        String encoded = null;
        try {
            byte[] bytes = IOUtils.toByteArray(is);
            encoded = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return encoded;
    }


    /**
     * 将base64的字符串写入到文件中
     */
    public static boolean decryptByBase64(String base64, String filePath) {
        if (Strings.isNullOrEmpty(base64) && Strings.isNullOrEmpty(filePath)) {
            return Boolean.FALSE;
        }
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            Files.write(Paths.get(filePath), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }


    /**
     * 把base64的字符串转换为字节数组
     */
    public static byte[] decryptByBase64ToByteArr(String base64Str) {
        byte[] bytes = null;
        try {
            bytes = Base64.getDecoder().decode(base64Str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }


    /**
     * 关键字查找
     *
     * @param inputStream   pdf流
     * @param fileName      文件名称
     * @param chapterKeyArr 关键字列表
     * @return
     * @throws Exception
     */
    public static JSONObject findCharpterKey(InputStream inputStream, String fileName, List<String> chapterKeyArr) throws Exception {
        return findCharpterKey(BestSignClient.PDF2Base64(inputStream), fileName, chapterKeyArr);
    }

    public static JSONObject findCharpterKey(String pdfBase64Str, String fileName, List<String> chapterKeyArr) throws Exception {
        JSONObject result = new JSONObject();
        JSONObject keyFindParams = new JSONObject();
        JSONArray documents = new JSONArray();
        JSONObject document = new JSONObject();
        document.put("fileName", fileName);
        document.put("content", pdfBase64Str);
        document.put("order", "0");
        documents.add(document);
        JSONArray keywords = new JSONArray();
        for (String chapterKey : chapterKeyArr) {
            keywords.add(chapterKey);
        }
        keyFindParams.put("documents", documents);
        keyFindParams.put("keywords", keywords);
        String resultStr = new InterfaceSendAndReceive().keywordsSite(keyFindParams);
        return JSON.parseObject(resultStr);
    }

    /**
     * @param inputStream   pdf 流
     * @param fileName      文件流
     * @param signatoryList 签署方信息
     * @return
     * @throws IOException
     */
    @SuppressWarnings("all")
    public String createStartStamp(InputStream inputStream, String isConversion, String fileName, String enterpriseName, List<BaseContractReceivers_RO> signatoryList) throws Exception {
        JSONObject resultJson = new JSONObject();
        try {
            ContractSender_RO sender = new ContractSender_RO();
            sender.setEnterpriseName(enterpriseName);//"传屈臣氏签署主体公司名称： 广州屈臣氏个人用品商店有限公司"
            sender.setAccount(acount);//传屈臣氏签署主体公司的主管理员账号
            //documents 参数
            ContractDocuments_RO document = new ContractDocuments_RO();
            String content = BestSignClient.PDF2Base64(inputStream);
            document.setContent(content);//文件内容用BASE64编码
            document.setOrder(0);//Order需为大于等于0的非负整数。屈臣氏场景中在调该接口时已经文档合并，所以传入的只有一个文件，该参数传0即可
            document.setFileName(fileName);//文件名称
            //将文档参数加入到list中
            List<ContractDocuments_RO> documents = new ArrayList<>();
            documents.add(document);
            //step1：关键字查找
            JSONObject charpterKeyJson = BestSignClient.findCharpterKey(content, fileName, chapterKeyArr);
            String code = charpterKeyJson.getString("code");
            //所有关键字的坐标信息
            JSONObject keyJson = charpterKeyJson.getJSONObject("data");

            if (!"0".equalsIgnoreCase(code) || keyJson == null) {
                throw new Exception("关键字定位异常，关键字返回信息如下：" + keyJson);
            }
            //甲方戳章信息
            JSONArray companyAKeyArr = keyJson.getJSONArray(COMPANY_A);
            //乙方戳章信息
            JSONArray companyBKeyArr = keyJson.getJSONArray(COMPANY_B);
            //丙方戳章信息
            JSONArray companyCKeyArr = keyJson.getJSONArray(COMPANY_C);
            List<ContractLabels_RO> labelsA = getContractLabel(companyAKeyArr);
            List<ContractLabels_RO> labelsB = getContractLabel(companyBKeyArr);
            List<ContractLabels_RO> labelsC = getContractLabel(companyCKeyArr);
            Map<String,List<ContractLabels_RO>> allLabels = new HashMap<>();
            if (labelsA.size() >0) {
                //对比参照值
                double referenceX = BigDecimalUtils.format(BigDecimalUtils.divide(4, 20.9), 4);//x坐标 0.1914
                double referenceY = BigDecimalUtils.format(BigDecimalUtils.divide(4, 29.7), 4);//y坐标 0.1347
                double referenceXAddValue = BigDecimalUtils.add(referenceX, 0.05);
                double referenceYAddValue = BigDecimalUtils.add(referenceY, 0.01);
                double rightSeal = BigDecimalUtils.subtract(1.00,referenceX);//x坐标离右侧的距离 0.8086
                double topSeal = BigDecimalUtils.subtract(1.00,referenceY);//Y坐标离顶侧的距离 0.8653

                for (ContractLabels_RO labelsRoA : labelsA) {//甲方戳章
                    int pageNumberA = labelsRoA.getPageNumber().intValue();
                    double xA = BigDecimalUtils.format(labelsRoA.getX().doubleValue(),4);
                    double yA = BigDecimalUtils.format(labelsRoA.getY().doubleValue(),4);
                    labelsRoA.setType("SEAL");
                    xA = getXPoint(xA,rightSeal);
                    yA = getYPoint(yA,topSeal);
                    for (ContractLabels_RO contractLabelsRoB : labelsB) {//乙方戳章
                        int pageNumberB = contractLabelsRoB.getPageNumber().intValue();
                        double xB = BigDecimalUtils.format(contractLabelsRoB.getX().doubleValue(),4);
                        double yB = BigDecimalUtils.format(contractLabelsRoB.getY().doubleValue(),4);
                        contractLabelsRoB.setType("SEAL");
                        xB = getXPoint(xB,rightSeal);
                        yB = getYPoint(yB,topSeal);
                        /*double leftMoveBX = BigDecimalUtils.subtract(xB, X_OFFSET);
                        if (BigDecimalUtils.compare(leftMoveBX,0) >= 0 && BigDecimalUtils.compare(leftMoveBX,rightSeal) <=0){
                            //乙方戳章x坐标整体往左移0.05
                            //xB = BigDecimalUtils.subtract(xB, X_OFFSET);
                            xB = leftMoveBX;
                        } else if (BigDecimalUtils.compare(leftMoveBX,rightSeal) > 0){
                            xB = rightSeal;
                        }
                        double  topMoveBY = BigDecimalUtils.subtract(yB,Y_OFFSET);
                        //乙方戳章Y坐标整体往下移0.08
                        if (BigDecimalUtils.compare(topMoveBY,0) >= 0 && BigDecimalUtils.compare(topMoveBY,topSeal) <= 0) {
                            yB = topMoveBY;
                        } else if (BigDecimalUtils.compare(topMoveBY,topSeal) > 0) {
                            yB = topSeal;
                        }*/

                        //甲方,乙方,丙方在同一页就进行处理
                        // 判断是否在同一页,如果在同一页,判断甲方戳章和乙方戳章的问题是否在同一个x坐标上
                        if (pageNumberA == pageNumberB) {
                            if (BigDecimalUtils.compare(BigDecimalUtils.abs(BigDecimalUtils.subtract(xA, xB)),referenceX) < 0 ){
                                //判断哪个盖章在前哪个在后
                                if (BigDecimalUtils.compare(xA,xB) > 0) {
                                    contractLabelsRoB.setX(BigDecimalUtils.subtract(xA,referenceXAddValue));//referenceX
                                } else if (BigDecimalUtils.compare(xA,xB) < 0){
                                    contractLabelsRoB.setX(BigDecimalUtils.add(xA,referenceXAddValue));
                                } else { //甲方和乙方的x坐标一样,判断y坐标是否一样
                                    //contractLabelsRoB.setY(BigDecimalUtils.add(xA,referenceX));
                                    if (BigDecimalUtils.compare(BigDecimalUtils.abs(BigDecimalUtils.subtract(yA,yB)),referenceY) < 0){
                                        if (BigDecimalUtils.compare(yA,yB) > 0) {
                                            contractLabelsRoB.setY(BigDecimalUtils.subtract(yA,referenceYAddValue));//referenceY
                                        } else if (BigDecimalUtils.compare(yA,yB) < 0) {
                                            contractLabelsRoB.setY(BigDecimalUtils.add(yA,referenceYAddValue));
                                        } else {
                                            contractLabelsRoB.setY(BigDecimalUtils.add(yA,referenceYAddValue));
                                        }
                                    }
                                }
                            }
                        }
                        contractLabelsRoB.setX(xB);
                        contractLabelsRoB.setY(yB);
                    }

                    for (ContractLabels_RO contractLabelsRoC : labelsC) {
                        int pageNumberC = contractLabelsRoC.getPageNumber().intValue();
                        double xC = BigDecimalUtils.format(contractLabelsRoC.getX().doubleValue(),4);
                        double yC = BigDecimalUtils.format(contractLabelsRoC.getY().doubleValue(),4);
                        contractLabelsRoC.setType("SEAL");
                        xC = getXPoint(xC,rightSeal);
                        yC = getYPoint(yC,topSeal);
                        /*double leftMoveCX = BigDecimalUtils.subtract(xC, X_OFFSET);
                        if (BigDecimalUtils.compare(leftMoveCX,0) >= 0 && BigDecimalUtils.compare(leftMoveCX,rightSeal) <= 0 ) {
                            //xC = BigDecimalUtils.subtract(xC, X_OFFSET);
                            xC = leftMoveCX;
                        } else if (BigDecimalUtils.compare(leftMoveCX,rightSeal) > 0){
                            xC = rightSeal;
                        }
                        double topMoveCY = BigDecimalUtils.subtract(yC, Y_OFFSET);
                        if (BigDecimalUtils.compare(topMoveCY,0) >= 0 && BigDecimalUtils.compare(topMoveCY,topSeal) <= 0){
                            //yC = BigDecimalUtils.subtract(yC, Y_OFFSET);
                            yC = topMoveCY;
                        } else if (BigDecimalUtils.compare(topMoveCY,topSeal) > 0) {
                            yC = topSeal;
                        }*/
                        if (pageNumberA == pageNumberC) {
                            if (BigDecimalUtils.compare(BigDecimalUtils.abs(BigDecimalUtils.subtract(xA, xC)),referenceX) < 0 ){
                                if (BigDecimalUtils.compare(xA,xC) > 0) {
                                    contractLabelsRoC.setX(BigDecimalUtils.subtract(xA,referenceXAddValue));
                                } else if (BigDecimalUtils.compare(xA,xC) < 0){
                                    contractLabelsRoC.setX(BigDecimalUtils.add(xA,referenceXAddValue));
                                } else { //甲方和丙方的x坐标一样,判断y坐标是否一样
                                    if (BigDecimalUtils.compare(BigDecimalUtils.abs(BigDecimalUtils.subtract(yA,yC)),referenceY) < 0){
                                        if (BigDecimalUtils.compare(yA,yC) > 0) {
                                            //contractLabelsRoC.setY(BigDecimalUtils.subtract(yA,referenceY));
                                            contractLabelsRoC.setY(BigDecimalUtils.subtract(yA,referenceYAddValue));//referenceY
                                        } else if (BigDecimalUtils.compare(yA,yC) < 0) {
                                            //contractLabelsRoC.setY(BigDecimalUtils.doubleToBigDecimal(BigDecimalUtils.add(yA,referenceY)));
                                            contractLabelsRoC.setY(BigDecimalUtils.doubleToBigDecimal(BigDecimalUtils.add(yA,referenceYAddValue)));
                                        } else {
                                            contractLabelsRoC.setY(BigDecimalUtils.doubleToBigDecimal(BigDecimalUtils.add(yA,referenceYAddValue)));
                                        }
                                    }
                                }
                            }
                        }
                        contractLabelsRoC.setX(xC);
                        contractLabelsRoC.setY(yC);
                    }
                    labelsRoA.setX(xA);
                    labelsRoA.setY(yA);
                }
                allLabels.put(COMPANY_A,labelsA);
                allLabels.put(COMPANY_B,labelsB);
                allLabels.put(COMPANY_C,labelsC);
            }

            List<ContractReceivers_RO> receivers = new ArrayList<>();
            for (int idx = 0; idx < signatoryList.size(); idx++) {
                BaseContractReceivers_RO receiver = signatoryList.get(idx);
                ContractReceivers_RO watsonReceiver = new ContractReceivers_RO();
                String chapterNameKey = chapterKeyMap.get(idx + 1);//甲方戳章等关键字

                //新添加
                List<ContractLabels_RO> labels = allLabels.get(chapterNameKey);
                if ("Y".equalsIgnoreCase(isConversion)) {
                    //甲乙双方交换签署位置
                    if ("1".equalsIgnoreCase(receiver.getCompanyFlag())) {//甲方换乙方
                        labels = allLabels.get(COMPANY_B);
                    } else if ("2".equalsIgnoreCase(receiver.getCompanyFlag())){
                        labels = allLabels.get(COMPANY_A);
                    }
                }
                /*
                //2020.10.28注释
                //关键字的坐标信息
                JSONArray keyArr = keyJson.getJSONArray(chapterNameKey);
                if ("Y".equalsIgnoreCase(isConversion)) {
                    //甲乙双方交换签署位置
                    if ("1".equalsIgnoreCase(receiver.getCompanyFlag())) {//甲方换乙方
                        keyArr = keyJson.getJSONArray(COMPANY_B);
                    } else if ("2".equalsIgnoreCase(receiver.getCompanyFlag())){
                        keyArr = keyJson.getJSONArray(COMPANY_A);
                    }
                }


                List<ContractLabels_RO> labels = JSON.parseArray(SaafToolUtils.toJson(keyArr), ContractLabels_RO.class);
                for (ContractLabels_RO labelsRo : labels) {
                    Number y = labelsRo.getY().doubleValue() * 0.75D;
                    Number x = labelsRo.getX().doubleValue() * 0.8D;
                    Number pageNumber = labelsRo.getPageNumber().doubleValue();
                    labelsRo.setY(y);
                    labelsRo.setX(x);
                    labelsRo.setType("SEAL");
                }*/

                if ("1".equalsIgnoreCase(receiver.getCompanyFlag())) {
                    //屈臣氏签署方
                    watsonReceiver.setIfProxyClaimer(false);//是否是企业前台代收模式,因为屈臣氏没有供应商印章管理员账号，所以传true
                    watsonReceiver.setUserAccount(ResourceUtils.getAcount()); //"13560327741" 设置供应商签署信息时：设置ifProxyClaimer参数为true，则该参数不填,设置屈臣氏签署信息时：传屈臣氏签署主体公司的主管理员账号
                    watsonReceiver.setEnterpriseName(enterpriseName);//"广州屈臣氏个人用品商店有限公司" 传屈臣氏签署主体公司名称
                    watsonReceiver.setNotification("");//设置供应商签署信息时：传供应商业务人员手机号，设置屈臣氏签署信息时：不填
                    watsonReceiver.setRouteOrder(2);//则各个参与人按次序签署合同。供应商传1，屈臣氏传2；即合同签署顺序，按照屈臣氏需求
                } else {
                    //供应商签署方
                    watsonReceiver.setIfProxyClaimer(true);//是否是企业前台代收模式,因为屈臣氏没有供应商印章管理员账号，所以传true
                    watsonReceiver.setUserAccount("");//设置供应商签署信息时：设置ifProxyClaimer参数为true，则该参数不填,设置屈臣氏签署信息时：传屈臣氏签署主体公司的主管理员账号
                    watsonReceiver.setEnterpriseName(receiver.getEnterpriseName());//receiver 四川省嘉恒食品有限责任公司
                    watsonReceiver.setNotification(receiver.getNotification());//13923171263， 设置供应商签署信息时：传供应商业务人员手机号，设置屈臣氏签署信息时：不填
                    watsonReceiver.setRouteOrder(1);//则各个参与人按次序签署合同。供应商传1，屈臣氏传2；即合同签署顺序，按照屈臣氏需求
                }
                watsonReceiver.setUserType("ENTERPRISE");//设置为ENTERPRISE，即企业类型
                //添加盖章信息
                watsonReceiver.setLabels(labels);
                receivers.add(watsonReceiver);
            }
            ContractCreateAndSendInfo_RO cca = new ContractCreateAndSendInfo_RO();
            cca.setContractTitle("tta系统电子签章");
            cca.setSignOrdered(true);//填true
            cca.setDocuments(documents);
            cca.setReceivers(receivers);
            String signDeadLine = jedisCluster.hget("GLOBAL_REDIS_CACHE","SIGN_DEAD_LINE");
            //签约截止日期
            int dateNum = StringUtils.isNotBlank(signDeadLine) ? Integer.parseInt(signDeadLine): 30;
            cca.setSignDeadline(String.valueOf(SaafDateUtils.getNextDay(new Date(), dateNum).getTime()));
            cca.setSender(sender);
            return  BestSignClient.getBestSignClient().executeRequest("/api/contracts", "POST", cca);
        } catch (Exception e) {
            LOGGER.error("发起电子签章异常：", e);
            resultJson.put("data", e.getStackTrace());
            resultJson.put("code", "error");
        }
        return resultJson.toJSONString();
    }

    private double getYPoint(double y, double topSeal) {
        //判断y坐标
        double topMoveY = BigDecimalUtils.subtract(y,Y_OFFSET);
        if (BigDecimalUtils.compare(topMoveY,0) >= 0 && BigDecimalUtils.compare(topMoveY,topSeal) <= 0) {
            //Y坐标整体往下移0.08
            //y = BigDecimalUtils.subtract(yA,Y_OFFSET);
            y = topMoveY;
        } else if (BigDecimalUtils.compare(topMoveY,topSeal) > 0) {
            y = topSeal;
        }
        return y;
    }

    private double getXPoint(double x, double rightSeal) {
        //如果x坐标的偏移0.05小于零,就不偏移了,否则偏移
        double leftMoveX = BigDecimalUtils.subtract(x, X_OFFSET);
        if (BigDecimalUtils.compare(leftMoveX,0) >= 0 && BigDecimalUtils.compare(leftMoveX,rightSeal) <= 0) {
            //x坐标整体往左移0.05
            //xA = BigDecimalUtils.subtract(xA, X_OFFSET);
            x = leftMoveX;
        } else if (BigDecimalUtils.compare(leftMoveX,rightSeal) > 0) {
            x = rightSeal;
        }
        return x;
    }

    public List<ContractLabels_RO> getContractLabel(JSONArray keyArray){
        List<ContractLabels_RO> label = new ArrayList<>();
        if (keyArray != null && keyArray.size() > 0) {
            label = JSON.parseArray(SaafToolUtils.toJson(keyArray), ContractLabels_RO.class);
        }
        return label;
    }
    /**
     * 下载合同附页
     */
    public byte[] querySignFileByte(String contractId, String enterpriseName) throws IOException {
        //中文放入url请求需转码
        enterpriseName = URLEncoder.encode(enterpriseName, "UTF-8");
        String uriWithParam =  String.format("/api/contracts/%s/appendix-file?account=%s&enterpriseName=%s", contractId, acount, enterpriseName);
        String fileData = BestSignClient.getBestSignClient().executeRequest(uriWithParam, "GET", null);
        return BestSignClient.decryptByBase64ToByteArr(fileData);

    }


    /**
     * 查询合同详情
     */
    public String queryContractDetail(String contractId, String enterpriseName) throws Exception {
        //中文放入url请求需转码
        enterpriseName = URLEncoder.encode(enterpriseName, "UTF-8");
        String uriWithParam =  String.format("/api/contracts/detail/%s?account=%s&enterpriseName=%s", contractId, acount, enterpriseName);
        String detailContent = BestSignClient.getBestSignClient().executeRequest(uriWithParam, "GET", null);
        LOGGER.info("查询合同详情接口 queryContractDetail :{}", detailContent);
        return detailContent;
    }

    /**
     *
     * @param contractId
     * @param enterpriseName
     * @return
     */
    public Map<String, String> getDetailByCompanyName(String contractId, String enterpriseName) {
        Map<String, String> resultMap = new HashMap<>();
        String detialJson = null;
        try {
            detialJson = BestSignClient.getBestSignClient().queryContractDetail(contractId, enterpriseName);
            if (detialJson != null) {
                JSONObject jsonResult = JSON.parseObject(detialJson);
                if ("0".equalsIgnoreCase(jsonResult.getString("code"))){
                    JSONObject data = jsonResult.getJSONObject("data");
                    resultMap.put("signDeadline", data.getString("signDeadline"));
                    resultMap.put("contractStatus", data.getString("contractStatus"));
                    JSONArray signers = data.getJSONArray("signers");
                    for (int idx = 0; idx < signers.size(); idx ++) {
                        JSONObject jsonObject = signers.getJSONObject(idx);
                        String operateStatus = jsonObject.getString("operateStatus");
                        String signerName = jsonObject.getString("signerName");
                        String finishTime = jsonObject.getString("finishTime");
                        resultMap.put(signerName, operateStatus);
                        if (StringUtils.isNotBlank(finishTime)) {
                            resultMap.put(signerName+ "_finishTime", finishTime);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(" 查询签署信息异常：{},contractId:{},enterpriseName:{}", e, contractId, enterpriseName);
        }
        return  resultMap;
    }


    /**
     * 下载单个合同附件
     */
    public byte[] queryContractAttr(String contractId){
        try {
            JSONObject params = new JSONObject();
            JSONArray contractIds = new JSONArray();
            contractIds.add(contractId);
            params.put("contractIds",contractIds);
            params.put("encodeByBase64",false);
            params.put("fileType","pdf");
            String fileData = BestSignClient.getBestSignClient().executeRequest("/api/contracts/download-file", "POST", params);
            if (!fileData.contains("code")) {
                byte[] bytes = BestSignClient.decryptByBase64ToByteArr(fileData);
                return bytes;
            }
            LOGGER.error(" 下载合同附件信息异常:" + fileData);
        } catch (Exception e) {
            LOGGER.error(" 下载合同附件信息异常,Exception:{},contractId:{}", e, contractId);
        }
        return null;
    }
}
