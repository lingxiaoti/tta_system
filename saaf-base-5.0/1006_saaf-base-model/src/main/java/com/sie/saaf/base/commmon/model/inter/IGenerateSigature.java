package com.sie.saaf.base.commmon.model.inter;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by huangminglin on 2018/5/24.
 */
public interface IGenerateSigature {

    String generateSigature(String src) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, UnsupportedEncodingException;

    String unreadMail(String account_name, String type) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, SignatureException;

    String getToken(String corpid, String corpsecret);

    String unreadQQMail(String corpid, String corpsecret, String userId);

    String getQQMailURL(String corpid, String corpsecret, String userId, String accessToken);
}
