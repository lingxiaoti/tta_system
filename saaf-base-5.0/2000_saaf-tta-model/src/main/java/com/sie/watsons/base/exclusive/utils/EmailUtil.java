package com.sie.watsons.base.exclusive.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.ResourceUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Chenzg
 * @createTime 2018-05-30 16:33
 * @description  email util
 */
public class EmailUtil {

//    public static void main(String[] args) throws Exception {
//       /* Properties prop = new Properties();
//        prop.setProperty("mail.host", "smtp.exmail.qq.com");
//        prop.setProperty("mail.transport.protocol", "smtp");
//        prop.setProperty("mail.smtp.auth", "true");
//        //使用JavaMail发送邮件的5个步骤
//        //1、创建session
//        Session session = Session.getInstance(prop);
//        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//        session.stDebug(true);
//        //2、通过session得到transport对象
//        Transport ts = session.getTransport();
//        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
//        ts.connect("smtp.exmail.qq.com", "notice@ausnutria.com", "Au1234567");
//        //4、创建邮件
//        Message message = createSimpleMail(session);
//        //5、发送邮件
//        ts.sendMessage(message, message.getAllRecipients());
//        ts.close();*/
//    }

    /**
     * * @Method: createSimpleMail
     * * @Description: 创建一封只包含文本的邮件
     * * @Anthor:chenzg
     * *
     * * @param session
     * * @return
     * * @throws Exception
     */
    public static MimeMessage createSimpleMail(Session session, JSONObject instance, JSONObject sourceCfg,JSONObject configJson)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(configJson.getString("sendFrom"),configJson.getString("sendName")));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
       // message.setRecipient(Message.RecipientType.TO, new InternetAddress(instance.getReceiveCode()));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(instance.getString("receiveCode")));
        //邮件的标题
        message.setSubject(instance.getString("msgSubject"));
        //邮件的文本内容
        message.setContent(instance.getString("msgContent"), "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }

    public static JSONObject sendMail(JSONObject instance, JSONObject sourceCfg) {
        JSONObject returnMsg = new JSONObject();
        try {
            String paraConfig = sourceCfg.getString("paramCfg");
            JSONObject configJson = JSONObject.parseObject(paraConfig);

            Properties prop = new Properties();
            prop.setProperty("mail.host", configJson.getString("ServerHost"));
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
            //使用JavaMail发送邮件的5个步骤
            //1、创建session
            Session session = Session.getInstance(prop);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);
            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(configJson.getString("ServerHost"), sourceCfg.getString("sourceUser"), sourceCfg.getString("sourcePwd"));
            //4、创建邮件
            Message message = createSimpleMail(session, instance, sourceCfg,configJson);
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();

            returnMsg.put("CODE","S");
            returnMsg.put("MSG","邮件发送成功");
            return returnMsg;
        }catch(Exception e){
            returnMsg.put("CODE","E");
            returnMsg.put("MSG","邮件发送失败"+e.getMessage());
            return returnMsg;
        }
    }


    public static JSONObject sendMail(String receiveCode, String msgSubject, String msgContent) {
        JSONObject instance = new JSONObject();
        instance.put("msgSubject", msgSubject);
        instance.put("msgContent", msgContent);
        instance.put("receiveCode", receiveCode);

        JSONObject paraConfig = JSON.parseObject(ResourceUtils.getEmailParaConfig());

        JSONObject sourceCfg = new JSONObject();
        sourceCfg.put("paramCfg",paraConfig.toJSONString());
        sourceCfg.put("sourceUser",paraConfig.getString("sendFrom"));//WTCCN.service@watsons-china.com.cn
        sourceCfg.put("sourcePwd","");
        return  sendMail(instance, sourceCfg);
    }
}
