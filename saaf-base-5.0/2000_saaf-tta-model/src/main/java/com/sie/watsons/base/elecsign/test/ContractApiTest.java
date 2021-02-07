package com.sie.watsons.base.elecsign.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.sie.saaf.common.bean.*;
import com.sie.saaf.common.util.BestSignClient;
import com.sie.saaf.common.util.ResourceUtils;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.elecsign.model.entities.readonly.*;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ContractApiTest {

    /**
     * 获取access token
     * @throws IOException
     */
    @Test
    public void testQueryToken() throws IOException{
        String result = BestSignClient.getBestSignClient().queryToken();
        System.out.println(result);
    }

    /**
     * 查询绑定状态
     */
    @Test
    public void testUsersBindingExistence() {

        QueryBindingVO vo = new QueryBindingVO();
        vo.setDevAccountId("ssodemodevaccountid");
        String result = BestSignClient.getBestSignClient().executeRequest("/api/users/binding-existence", "POST", vo);
        System.out.println(result);
    }

    /**
     * 关键字定位
     */
    @Test
    public void key() throws Exception{

       /* JSONObject json = new JSONObject();
        JSONArray  documents = new JSONArray();
        JSONObject documentsObj = new JSONObject();
        File f = new File("D:\\A4.pdf");
        documentsObj.put("fileName","A4.pdf");
        documentsObj.put("content",BestSignClient.PDF2Base64(new FileInputStream(f)));
        documentsObj.put("order","0");
        documents.add(documentsObj);
        JSONArray  keywords = new JSONArray();
        String keywordsStr = "屈臣氏（盖章）";
        keywords.add(keywordsStr);
        keywordsStr = "供应商（盖章）";
        keywords.add(keywordsStr);
        json.put("documents",documents);
        json.put("keywords",keywords);

        String result = ifsr.keywordsSite(json);
        System.out.println(result);*/

        File file = new File("D:\\A4.pdf");
        List<String> list = Arrays.asList("屈臣氏（盖章）", "供应商（盖章）", "丙方（盖章）");
        JSONObject charpterKey = BestSignClient.findCharpterKey(new FileInputStream(file), "A4.pdf", list);
        System.out.println(charpterKey);

    }

    /**
     * 发送合同
     * {"code":"0","message":"请求成功","data":{
     *  "屈臣氏（盖章）":[{"documentOrder":0,"x":0.2746,"y":0.22100002,"pageNumber":2}],
     *  "供应商（盖章）":[{"documentOrder":0,"x":0.6626,"y":0.22100002,"pageNumber":2}]}}--0.11050001
     */
    @Test
    public void sendContract() throws IOException{

        ContractSender_RO sender = new ContractSender_RO();
        sender.setEnterpriseName("广州屈臣氏个人用品商店有限公司");
        sender.setAccount("13560327741");

        //documents 参数
        ContractDocuments_RO document = new ContractDocuments_RO();
        String content = BestSignClient.PDF2Base64(new FileInputStream("D:\\A2.pdf"));
        document.setContent(content);//文件内容用BASE64编码
        document.setOrder(0);//Order需为大于等于0的非负整数。屈臣氏场景中在调该接口时已经文档合并，所以传入的只有一个文件，该参数传0即可
        document.setFileName("A2.pdf");

        //将文档参数加入到list中
        List<ContractDocuments_RO> documents = new ArrayList<>();
        documents.add(document);

        List<ContractReceivers_RO> receivers = new ArrayList<>();

        ///////////////////////////////////////////////////////////////
        //供应商签署方
        ContractReceivers_RO vendroReceiver = new ContractReceivers_RO();
        vendroReceiver.setIfProxyClaimer(true);//是否是企业前台代收模式,因为屈臣氏没有供应商印章管理员账号，所以传true
        vendroReceiver.setUserAccount("");//设置供应商签署信息时：设置ifProxyClaimer参数为true，则该参数不填,设置屈臣氏签署信息时：传屈臣氏签署主体公司的主管理员账号
        vendroReceiver.setUserType("ENTERPRISE");//设置为ENTERPRISE，即企业类型
        vendroReceiver.setEnterpriseName("四川省嘉恒食品有限责任公司");
        vendroReceiver.setNotification("13923171263");//设置供应商签署信息时：传供应商业务人员手机号，设置屈臣氏签署信息时：不填
        vendroReceiver.setRouteOrder(1);//则各个参与人按次序签署合同。供应商传1，屈臣氏传2；即合同签署顺序，按照屈臣氏需求
        //添加盖章信息
        List<ContractLabels_RO> VendorLabels = new ArrayList<>();
        //供应商签署："供应商（盖章）":[{"documentOrder":0,"x":0.6626,"y":0.22100002,"pageNumber":2}]}}
        ContractLabels_RO vendorLabel = new ContractLabels_RO();
        vendorLabel.setX(0.6626);
        vendorLabel.setY(0.11050001);
        vendorLabel.setPageNumber(2);
        vendorLabel.setDocumentOrder(0);
        vendorLabel.setType("SEAL");
        VendorLabels.add(vendorLabel);
        vendroReceiver.setLabels(VendorLabels);
        receivers.add(vendroReceiver);
        ///////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////
        //屈臣氏签署方
        ContractReceivers_RO watsonReceiver = new ContractReceivers_RO();
        watsonReceiver.setIfProxyClaimer(false);//是否是企业前台代收模式,因为屈臣氏没有供应商印章管理员账号，所以传true
        watsonReceiver.setUserAccount(ResourceUtils.getAcount());//"13560327741"设置供应商签署信息时：设置ifProxyClaimer参数为true，则该参数不填,设置屈臣氏签署信息时：传屈臣氏签署主体公司的主管理员账号
        watsonReceiver.setUserType("ENTERPRISE");//设置为ENTERPRISE，即企业类型
        watsonReceiver.setEnterpriseName("广州屈臣氏个人用品商店有限公司");
        watsonReceiver.setNotification("");//设置供应商签署信息时：传供应商业务人员手机号，设置屈臣氏签署信息时：不填
        watsonReceiver.setRouteOrder(2);//则各个参与人按次序签署合同。供应商传1，屈臣氏传2；即合同签署顺序，按照屈臣氏需求
        //添加盖章信息
        List<ContractLabels_RO> watsonLabels = new ArrayList<>();
        //屈臣氏签署："屈臣氏（盖章）":[{"documentOrder":0,"x":0.2746,"y":0.22100002,"pageNumber":2}],
        ContractLabels_RO watsonsLabel = new ContractLabels_RO();
        watsonsLabel.setX(0.2746);
        watsonsLabel.setY(0.11050001);
        watsonsLabel.setPageNumber(2);
        watsonsLabel.setDocumentOrder(0);
        watsonsLabel.setType("SEAL");
        watsonLabels.add(watsonsLabel);
        watsonReceiver.setLabels(watsonLabels);

        receivers.add(watsonReceiver);
        ///////////////////////////////////////////////////////////////

        ContractCreateAndSendInfo_RO cca = new ContractCreateAndSendInfo_RO();
        cca.setContractTitle("tta系统电子签章测试");
        cca.setSignOrdered(true);//填true
        cca.setDocuments(documents);
        cca.setReceivers(receivers);
        cca.setSignDeadline(String.valueOf(SaafDateUtils.getNextDay(new Date(), 1).getTime()));
        cca.setSender(sender);

        String result = BestSignClient.getBestSignClient().executeRequest("/api/contracts", "POST", cca);
        System.out.println(result);
    };


    /**
     * 查询合同详情（新）
     */
    @Test
    public void testContractDetail() throws IOException {
        String contractId = "2592739025697636357";
        String account = "13560327741";
        //中文放入url请求需转码
        String enterpriseName = URLEncoder.encode("武汉屈臣氏个人用品商店有限公司", "UTF-8");
        String uriWithParam =  String.format("/api/contracts/detail/%s?account=%s&enterpriseName=%s", contractId, account, enterpriseName);
        String fileData = BestSignClient.getBestSignClient().executeRequest(uriWithParam, "GET", null);
        System.out.println(fileData);

    }

    /**
     * {"code":"0","message":"请求成功","data":{"contractId":"2587866260645609473","receivers":
     * [{"userAccount":"","userType":"ENTERPRISE","userName":"","enterpriseName":"四川省嘉恒食品有限责任公司",
     * "receiverType":"SIGNER","operateUrl":"https://api.bestsign.info/oauth/certification/sign?contractId=2587866260645609473&clientId=1589440821014756729&accessToken=","shortOperateUrl":"https://ent.bestsign.info/s/q3JEmU7a","userId":"0","enterpriseId":"2581852758873210886"},
     * {"userAccount":"13560327741","userType":"ENTERPRISE","userName":"","enterpriseName":"广州屈臣氏个人用品商店有限公司",
     * "receiverType":"SIGNER","operateUrl":"https://api.bestsign.info/oauth/certification/sign?contractId=2587866260645609473&clientId=1589440821014756729&accessToken=","shortOperateUrl":"https://ent.bestsign.info/s/FqVFy76a","userId":"2519536764658384901",
     * "enterpriseId":"2546934189677084673"}]}}
     * 下载合同附页
     */
    @Test
    public void testAppendixFile() throws IOException {
        String contractId = "2592739025697636357";
        String account = "13560327741";
        //中文放入url请求需转码
        String enterpriseName = URLEncoder.encode("广州屈臣氏个人用品商店有限公司", "UTF-8");
        String uriWithParam =  String.format("/api/contracts/%s/appendix-file?account=%s&enterpriseName=%s", contractId, account, enterpriseName);
        String fileData = BestSignClient.getBestSignClient().executeRequest(uriWithParam, "GET", null);
        System.out.println(fileData);
        BestSignClient.decryptByBase64(fileData,"D:/abcd.pdf");
    }

    /**
     * 合同附件下载
     */
    @Test
    public void testDown() throws  Exception{
        JSONObject params = new JSONObject();
        JSONArray contractIds = new JSONArray();
        contractIds.add("2588225992711471109");
        params.put("contractIds",contractIds);
        params.put("encodeByBase64",false);
        params.put("fileType","pdf");
        String fileData = BestSignClient.getBestSignClient().executeRequest("/api/contracts/download-file", "POST", params);
        System.out.println(fileData);
        byte[] bytes = BestSignClient.decryptByBase64ToByteArr(fileData);
        FileOutputStream out = new FileOutputStream("d:/test.pdf");
        StreamUtils.copy(bytes, out);
        out.close();
    }

    public static void main(String[] args) throws Exception {
       /// byte[] bytes = Base64.getDecoder().decode(base64);
        Files.write(Paths.get("d:/abc.txt"), "abc".getBytes());
    }
}
