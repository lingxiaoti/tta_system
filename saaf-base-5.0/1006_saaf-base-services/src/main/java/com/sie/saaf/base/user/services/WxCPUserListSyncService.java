package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.AccessToken;
import com.sie.saaf.base.user.model.entities.WxUserEntity_HI;
import com.sie.saaf.base.user.model.inter.IWxUser;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzg on 2018/3/6.
 */
@RestController
@RequestMapping("/wxCPUserListSyncService")
public class WxCPUserListSyncService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(WxCPUserListSyncService.class);

    public static final String CorpID = "wxbaa80479dd64524f";
    public static final String Secret = "FbyhVFaYoWdtXAMItzRPuexSANAaGD8SH0CuljHwjJWjGZbwmQCxcx2NoZJ74vRO";

    public static final String CorpID_HP = "wx98aaf1e2d2a6dd20";
    public static final String Secret_HP = "o4kn90z_lAYL_uwtbdOe7QDIPkll0Ei3oSvJlS7sfjfF_HBRF_hCqM6xT6CPvY3Y";

    public static final String CorpID_1897 = "wx066844a6a8ebb6bd";
    public static final String Secret_1897 = "FKyiVTUBVwS9A2F1JIg7fvIb2vIjyb09rSI4uE0htlR-48EJATc16J3PiMGEUpS7";

    public static final String CorpID_GN = "wxadebb5bedca9afb3";
    public static final String Secret_GN = "Hr97TxCUDXM22mk41nMjbtz2jXOaw32pZBpwGlFf0KnC_tlreddk9w0Ku5mD9Q3m";

    public static final String CorpID_PRD = "wxd677da1f8ff44979";
    public static final String Secret_PRD = "GMx8hpExLVnA2w9U35pKrJLqAwOwoHc4FImWrXeMFtcH8UmD6Pzw85okxH7UklcG";

    // 获取token
    private static String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CorpID&corpsecret=SECRET";

    // 获取部门列表地址
    public static String DEPARTMENTLIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";

    // 获取成员列表地址
    public static String USERLIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";


    @Autowired
    private IWxUser wxUserServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.wxUserServer;
    }


    @RequestMapping(method = RequestMethod.POST, value = "syncAllUsers")
    public String syncAllUsers(String params) {
        long startTime = System.currentTimeMillis();

        try {
            // prepare data
            List<WxUserEntity_HI> prepareData = new ArrayList<WxUserEntity_HI>();

            // users in database
            List<WxUserEntity_HI> usersInDb = wxUserServer.findList(new JSONObject());

           /*
           // get token
            String token = getAccessToken(CorpID, Secret).getToken();
            // get user
            List<WxUserEntity_HI> userList = getUserList(token);
            */

            // default
            prepareData.addAll(getUserList(getAccessToken(CorpID, Secret).getToken(), usersInDb));

            // HP
            prepareData.addAll(getUserList(getAccessToken(CorpID_HP, Secret_HP).getToken(), usersInDb));

            // 1897
            prepareData.addAll(getUserList(getAccessToken(CorpID_1897, Secret_1897).getToken(), usersInDb));

            // GN
            prepareData.addAll(getUserList(getAccessToken(CorpID_GN, Secret_GN).getToken(), usersInDb));

            // PRD
            prepareData.addAll(getUserList(getAccessToken(CorpID_PRD, Secret_PRD).getToken(), usersInDb));

            wxUserServer.save(prepareData);

            long endTime = System.currentTimeMillis();

            logger.info(prepareData.size() + "条数据被创建,耗时：" + (endTime - startTime) / 1000 + "秒");

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, prepareData.size(), new JSONArray().fluentAdd(prepareData)).toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findByName")
    public String findByName(@RequestParam(required = true) String params ) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            if(!queryParamJSON.containsKey("name") || StringUtils.isBlank(queryParamJSON.getString("name"))) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "名字不能为空", 0, null).toString();
            }
            List<String> entityList = this.wxUserServer.findByName(queryParamJSON);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, new JSONArray().fluentAdd(entityList)).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findByCondition")
    public String findByCondition(@RequestParam(required = true)String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);

            List<WxUserEntity_HI> entityList = this.wxUserServer.findByCondition(queryParamJSON);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, new JSONArray().fluentAdd(entityList)).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    private List<WxUserEntity_HI> getUserList(String token, List<WxUserEntity_HI> userListInDabase) {
        // prepareUserList
        List<WxUserEntity_HI> prepareUserList = new ArrayList<WxUserEntity_HI>();

        // get all department
        String departmenet = postMessage(token, "POST", DEPARTMENTLIST_URL, null).get("department").toString();
        JSONArray departMentArray = JSON.parseArray(departmenet);

        for (int i = 0; i < departMentArray.size(); i++) {
            JSONObject json = JSON.parseObject(departMentArray.get(i).toString());
            String departId = json.get("id").toString();
            // get user list
            String accessURL = dealURL4Depart(USERLIST_URL, departId, "0");
            String users = postMessage(token, "POST", accessURL, null).get("userlist").toString();
            List<WxUserEntity_HI> userList = JSON.parseArray(users, WxUserEntity_HI.class);

            for (int j = 0; j < userList.size(); j++) {
                WxUserEntity_HI user = userList.get(j);
                if (!checkIsExits(userListInDabase, user)) {
                    prepareUserList.add(user);
                }
            }
        }
        return prepareUserList;
    }

    // if userid and depart are the same,it means the two data is the same (ps:maybe need modify)
    private boolean checkIsExits(List<WxUserEntity_HI> userListInDabase, WxUserEntity_HI user) {
        boolean ret = false;
        for (WxUserEntity_HI userInDb : userListInDabase) {
            if (userInDb.getUserId().equals(user.getUserId()) && userInDb.getDepartment().equals(user.getDepartment())) {
                ret = true;
            }
        }
        return ret;
    }

    private String dealURL4Depart(String accessUrl, String departId, String fetchChild) {
        String requestUrl = accessUrl.replace("DEPARTMENT_ID", departId).replace("FETCH_CHILD", fetchChild);
        return requestUrl;
    }


    // get token
    private static AccessToken getAccessToken(String corpID, String secret) {
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("CorpID", corpID).replace("SECRET", secret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // if success
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
                // System.out.println("获取token成功:" + jsonObject.getString("access_token") + "————" + jsonObject.getInteger("expires_in"));
            } catch (Exception e) {
                accessToken = null;
                // 获取token失败
                String error = String.format("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
                logger.error(error);
            }
        }
        return accessToken;
    }

    /**
     * 数据提交与请求通用方法
     *
     * @param access_token 凭证
     * @param RequestMt    请求方式
     * @param RequestURL   请求地址
     * @param outstr       提交json数据
     */
    public static JSONObject postMessage(String access_token, String RequestMt, String RequestURL, String outstr) {
        JSONObject result = new JSONObject();
        RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
        JSONObject jsonobject = httpRequest(RequestURL, RequestMt, outstr);
        if (null != jsonobject) {
            if (0 != jsonobject.getInteger("errcode")) {
                // result = jsonobject.getInteger("errcode");
                String error = String.format("操作失败 errcode:{} errmsg:{}", jsonobject.getInteger("errcode"), jsonobject.getString("errmsg"));
                logger.error(error);
            }
            result = jsonobject;
        }
        return result;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        @SuppressWarnings("unused")
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);
            if (outputStr != null) {
                OutputStream out = connection.getOutputStream();
                out.write(outputStr.getBytes("UTF-8"));
                out.close();
            }
            //流处理
            InputStream input = connection.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
            BufferedReader reader = new BufferedReader(inputReader);
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null || index != 3000000) {
                buffer.append(line);
                index++;
            }
            //关闭连接、释放资源
            reader.close();
            inputReader.close();
            input.close();
            input = null;
            connection.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jsonObject;
    }
}
