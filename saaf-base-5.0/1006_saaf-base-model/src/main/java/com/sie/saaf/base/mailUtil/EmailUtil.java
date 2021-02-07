package com.sie.saaf.base.mailUtil;
//
//import com.alibaba.fastjson.JSONObject;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sie.saaf.base.mailUtil.EmailModule.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

/**
 * @author Chenzg
 * @createTime 2018-05-30 16:33
 * @description  email util
 */
public class EmailUtil {

//    public static void sentMail(String jsonString) throws Exception {
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.host","smtp.qq.com");//发送邮箱服务器
//        properties.setProperty("mail.smtp.port","465");//发送端口
//        properties.setProperty("mail.smtp.auth","true");//是否开启权限控制
//        properties.setProperty("mail.debug","true");//true 打印信息到控制台
//        properties.setProperty("mail.transport","smtp");//发送的协议是简单的邮件传输协议
//        properties.setProperty("mail.transport.protocol", "smtp");
//        properties.setProperty("mail.smtp.ssl.enable","true");
//        //使用JavaMail发送邮件的5个步骤
//        //1、创建session
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("1561763825@qq.com","saewoiciavyvijgb");
//            }
//        });
//        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//        session.setDebug(true);
//        //2、通过session得到transport对象
//        Transport ts = session.getTransport();
//        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
//        ts.connect("1561763825@qq.com", "saewoiciavyvijgb");
//        //4、创建邮件
//        Message message = createSimpleMail(session,jsonString);
//        //5、发送邮件
//        ts.sendMessage(message, message.getAllRecipients());
//        ts.close();
//    }
//
//    /**
//     * * @Method: createSimpleMail
//     * * @Description: 创建一封只包含文本的邮件
//     * * @Anthor:chenzg
//     * *
//     * * @param session
//     * * @return
//     * * @throws Exception
//     */
//    public static MimeMessage createSimpleMail(Session session,String jsonString)
//            throws Exception {
//        JSONObject jsonObject = JSONObject.parseObject(jsonString);
//        //创建邮件对象
//        MimeMessage message = new MimeMessage(session);
//        //指明邮件的发件人
//        message.setFrom(new InternetAddress(jsonObject.getString("sendFrom")));
//        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
//        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(jsonObject.getString("sendTo")));
//        //邮件的标题
//        message.setSubject(jsonObject.getString("msgSubject"));
//        //邮件的文本内容
//        message.setContent(jsonObject.getString("message"), "text/html;charset=UTF-8");
//        //返回创建好的邮件对象
//        return message;
//    }


    public static String SendEmail(EmailParameters parameters) throws IOException {
        String result = "";
        String requestid = UUID.randomUUID().toString();

        login_param param = new login_param();
        param.appcode = parameters.appcode;
        param.apptoken = parameters.apptoken;
        param.requestid = requestid;

        result = Login(param);
        if (result.isEmpty()) {
            EmailObject eo = new EmailObject();
            eo.blindcarboncopys = parameters.blindcarboncopys;
            eo.carboncopys = parameters.carboncopys;
            eo.priority = parameters.priority;
            eo.to = parameters.to;
            eo.subject = parameters.subject;
            eo.body = parameters.body;
            eo.appkey = param.appkey;
            eo.requestid = requestid;




            eo.appcode = parameters.appcode;
            eo.throughoutsidegateway = parameters.throughoutsidegateway;
            eo.from = parameters.from;
            eo.frompwd = parameters.frompwd;
            eo.fromnickname = parameters.fromnickname;

            if (!(parameters.linkedresources == null)) {
                List<EmailLinkedResource> el = new ArrayList<EmailLinkedResource>();
                for(ecLinkedResource elf : parameters.linkedresources){
                    File f = new File(elf.file);
                    if (!f.exists()){
                        result = "Missing link file: " + elf.file;
                        return result;
                    }
                    else{
                        String filename = elf.file.substring(elf.file.lastIndexOf("\\") + 1, elf.file.length());
                        InputStream is = new FileInputStream(elf.file);
                        byte[] array = new byte[is.available()];
                        is.read(array);
                        is.close();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        GZIPOutputStream gzip = new GZIPOutputStream(bos);
                        gzip.write(array);
                        gzip.finish();
                        gzip.close();
                        byte[] zipped = bos.toByteArray();
                        bos.close();
                        String attachment = Base64.getEncoder().encodeToString(zipped);

                        EmailLinkedResource r = new EmailLinkedResource();
                        r.base64str = attachment;
                        r.contentid = elf.contentid;
                        r.filename = filename;
                        r.imagetype = elf.imagetype;
                        el.add(r);
                    }
                }
                eo.linkedresources = el;
            }
            if (!(parameters.attachments == null)) {
                eo.hasattached = "Y";
                List<EmailAttachment> atts = new ArrayList<EmailAttachment>();

                for (String file : parameters.attachments) {
                    File f = new File(file);
                    if (!f.exists()) {
                        result = "Missing attachment: " + file;
                        return result;
                    } else {
                        String filename = file.substring(file.lastIndexOf("/") + 1, file.length());
                        InputStream is = new FileInputStream(file);
                        byte[] array = new byte[is.available()];
                        is.read(array);
                        is.close();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        GZIPOutputStream gzip = new GZIPOutputStream(bos);
                        gzip.write(array);
                        gzip.finish();
                        gzip.close();
                        byte[] zipped = bos.toByteArray();
                        bos.close();
                        String attachment = Base64.getEncoder().encodeToString(zipped);

                        EmailAttachment ea = new EmailAttachment();
                        ea.filename = filename;
                        ea.base64str = attachment;
                        atts.add(ea);
                    }
                }
                eo.attachments = atts;
            }

            HttpURLConnection conn = null;
            try {
                // 创建url资源
                //URL url = new URL("http://wtccn-gz-bugt:58725/api/email/sendmail");
                URL url = new URL("http://10.82.20.184:58725/api/email/sendmail");
                // 建立http连接
                conn = (HttpURLConnection) url.openConnection();
                // 设置允许输出
                conn.setDoOutput(true);
                // 设置允许输入
                conn.setDoInput(true);
                // 设置不用缓存
                conn.setUseCaches(false);
                // 设置传递方式
                conn.setRequestMethod("POST");
                // 设置维持长连接
                //conn.setRequestProperty("Connection", "Keep-Alive");
                // 设置文件字符集:
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Accept", "text/html, application/xhtml+xml, */*");
                // 转换为字节数组
                ObjectMapper mapper = new ObjectMapper();
                byte[] data = mapper.writeValueAsBytes(eo);

                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                // 设置文件类型:
                conn.setRequestProperty("Content-Type", "application/json");
                // 开始连接请求
                conn.connect();
                OutputStream out = new DataOutputStream(conn.getOutputStream());
                // 写入请求的字符串
                out.write(data);
                out.flush();
                out.close();

                //System.out.println(conn.getResponseCode());
                // 请求返回的状态
                if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                    //System.out.println("连接成功");
                    // 请求返回的数据
                    InputStream in1 = conn.getInputStream();
                    String txt = "";
                    try {
                        String readLine = new String();
                        BufferedReader responseReader = new BufferedReader(new InputStreamReader(in1, "UTF-8"));
                        while ((readLine = responseReader.readLine()) != null) {
                            txt += readLine;
                        }
                        responseReader.close();
                        //System.out.println(sb.toString());
                        EmailNonceResult enr = mapper.readValue(txt, EmailNonceResult.class);
                        if (enr.status == -1) {
                            result = enr.message;
                        } else {
                            String stoken = param.apptoken + enr.nonce.toUpperCase();
                            param.appkey = DigestUtils.shaHex(stoken);
                        }
                    } catch (Exception e1) {
                        result = e1.getMessage();
                    }
                } else {
                    //System.out.println("error++");
                    result = "error++";
                }
            } catch (Exception e) {
                result = e.getMessage();
            }
        }
        return result;
    }

    public static String Login(login_param param) {
        String rst = "";
        HttpURLConnection conn = null;
        try {
            EmailApplication ea = new EmailApplication();
            ea.applicationcode = param.appcode;
            ea.requestid = param.requestid;
            ea.expired = 120;

            // 创建url资源
            //URL url = new URL("http://wtccn-gz-bugt:58725/api/Login/RequestLogin");
            URL url = new URL("http://10.82.20.184:58725/api/Login/RequestLogin");
            // 建立http连接
            conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            // 设置允许输入
            conn.setDoInput(true);
            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            //conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Accept", "text/html, application/xhtml+xml, */*");

            // 转换为字节数组
            ObjectMapper mapper = new ObjectMapper();
            byte[] data = mapper.writeValueAsBytes(ea);

            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 设置文件类型:
            conn.setRequestProperty("Content-Type", "application/json");
            // 开始连接请求
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 写入请求的字符串
            out.write(data);
            out.flush();
            out.close();

            //System.out.println(conn.getResponseCode());
            // 请求返回的状态
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                //System.out.println("连接成功");
                // 请求返回的数据
                InputStream in1 = conn.getInputStream();
                String txt = "";
                try {
                    String readLine = new String();
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(in1, "UTF-8"));
                    while ((readLine = responseReader.readLine()) != null) {
                        txt += readLine;
                    }
                    responseReader.close();
                    //System.out.println(sb.toString());
                    EmailNonceResult enr = mapper.readValue(txt, EmailNonceResult.class);
                    if (enr.status == -1) {
                        rst = enr.message;
                    } else {
                        String stoken = param.apptoken + enr.nonce.toUpperCase();
                        param.appkey = DigestUtils.sha1Hex(stoken);
                    }
                } catch (Exception e1) {
                    rst = e1.getMessage();
                }
            } else {
                //System.out.println("error++");
                rst = "error++";
            }

        } catch (Exception err) {
            rst = err.getMessage();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rst;
    }

    //屈臣氏发送邮件API
    public static void sendMailFromWatsons(String sendTo,String subject,String body) {
        // TODO code application logic here
        EmailParameters ep = new EmailParameters();
        ep.appcode = "E9A1A314-21B2-4E5F-9B77-01FF34FFEB56";
        ep.apptoken = "A61B2F34-0193-4EBF-BCD9-A0941F5C35F9";
        ep.body = body;
        ep.subject = subject;
        ep.to = sendTo;
        ep.bodyencoding = "UTF8";
        ep.bodyformat = "html";
        ep.priority = "low";
        ep.throughoutsidegateway = 1;
        ep.from = "tender@watsonschina.com";
        ep.frompwd = "o3Sa4eY8qy";
        ep.fromnickname = "屈臣氏系统登录提示";
        try {
            String rst = SendEmail(ep);
//            System.out.println(rst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        sendMailFromWatsons("1198415652@qq.com","测试","这是一个测试");
        System.out.println("邮件发送完毕");
    }
}
