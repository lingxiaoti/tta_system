package com.sie.saaf.base.commmon.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.commmon.model.inter.IGenerateSigature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisCluster;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

/**
 * Created by huangminglin on 2018/5/24.
 */
@Component("generateSigatureServer")
public class GenerateSigatureServer implements IGenerateSigature {

    private static final String ENCODING_UTF_8="UTF-8";

    private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    @Autowired
    private JedisCluster jedisCluster;

    /**
     *huangminglin
     * 网易邮箱获取签名
     * @param src
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    @Override
    public String generateSigature(String src) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, UnsupportedEncodingException {

        String priKey = jedisCluster.hget("GLOBAL_REDIS_CACHE","GENERATE_SIGATURE_PRIKEY");

        Assert.notNull(priKey, "没有配置私钥");

        Signature           sigEng  = Signature.getInstance("SHA1withRSA");
        byte[]              pribyte = hexStrToBytes(priKey.trim());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);

        KeyFactory fac = KeyFactory.getInstance("RSA");

        RSAPrivateKey privateKey = (RSAPrivateKey) fac
                .generatePrivate(keySpec);
        sigEng.initSign(privateKey);
        sigEng.update(src.getBytes(ENCODING_UTF_8));

        byte[] signature = sigEng.sign();
        return bytesToHexStr(signature);
    }

    /**
     * huangminglin
     * 网易邮箱获取未读邮件
     * @param account_name
     * @param type
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    @Override
    public String unreadMail(String account_name,String type) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, SignatureException {
        String domain = "hyproca.com";
        String format = "json";

        long time = new Date().getTime();

        String paramsStr = "account_name=" + account_name + "&domain=" + domain + "&format=" + format + "&time=" + time + "&type="+ type;

        String sign = this.generateSigature(paramsStr);

        RestTemplate restTemplate =new RestTemplate();
        String       url          ="http://cm.qiye.163.com/oaserver/user/getUnreadMsg?" + paramsStr + "&sign=" + sign;
        HttpHeaders  headers      = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String                 body     = exchange.getBody();

        return body;
    }

    /**
     * huangminglin
     * QQ邮箱获取Token
     * @param corpid
     * @param corpsecret
     * @return
     */
    @Override
    public String getToken(String corpid, String corpsecret) {
        RestTemplate restTemplate =new RestTemplate();
        String       url          ="https://api.exmail.qq.com/cgi-bin/gettoken?corpid="+ corpid +"&corpsecret=" + corpsecret;
        HttpHeaders  headers      = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String                 body     = exchange.getBody();
        return body;
    }

    /**
     * huangminglin
     * QQ邮箱获取未读邮件数
     * @param corpid
     * @param corpsecret
     * @param userId
     * @return
     */
    @Override
    public String unreadQQMail(String corpid, String corpsecret, String userId) {

        String token = this.getToken(corpid, corpsecret);

        JSONObject tokenJson = JSONObject.parseObject(token);

        String accessToken = tokenJson.getString("access_token");

        RestTemplate restTemplate =new RestTemplate();
        String       url          ="https://api.exmail.qq.com/cgi-bin/mail/newcount?access_token="+ accessToken +"&userid=" + userId;
        HttpHeaders  headers      = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String                 body     = exchange.getBody();

        return body;
    }

    /**
     * huangminglin
     * QQ邮箱获取登录企业邮的url
     *
     * @param corpid
     * @param corpsecret
     * @param userId
     * @return
     */
    @Override
    public String getQQMailURL(String corpid, String corpsecret, String userId,String accessToken) {
        if(accessToken == null){
            String token = this.getToken(corpid, corpsecret);

            JSONObject tokenJson = JSONObject.parseObject(token);

            accessToken = tokenJson.getString("access_token");
        }

        RestTemplate restTemplate =new RestTemplate();
        String       url          ="https://api.exmail.qq.com/cgi-bin/service/get_login_url?access_token="+ accessToken +"&userid=" + userId;
        HttpHeaders  headers      = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String                 body     = exchange.getBody();

        return body;
    }


    private static byte[] hexStrToBytes(String s) {
        byte[] bytes;

        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
                    16);
        }

        return bytes;
    }

    private static String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);

        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }

        return s.toString();
    }
}
