package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.UserPwdTempEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseUserPassWordInit;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.base.user.model.inter.IUserPwdTemp;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.DigestUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.*;

@Component("baseUserPassWordInitServer")
public class BaseUserPassWordInitServer implements IBaseUserPassWordInit {

//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseUserPassWordInitServer.class);

//    @Autowired
//    private JavaMailSender mailSender;

//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;

    @Autowired
    private ViewObject<BaseUsersEntity_HI> baseUsersDAO_HI;

    @Autowired
    private ViewObject<BasePersonEntity_HI> basepersonDAO_HI;

    @Autowired
    private IBaseUsers baseUsersServer;

    @Autowired
    private IUserPwdTemp userPwdTempServer;

//    private String from; //读取配置文件中的参数

    private static Map<String, String> loginUrlMap = new HashMap<String, String>();

    static {
        loginUrlMap.put("81_LOGINURL", "http://e.allnutria.com");
        loginUrlMap.put("82_LOGINURL", "http://e.kabrita.com.cn");
        loginUrlMap.put("181_LOGINURL", "http://e.1897.com");
        loginUrlMap.put("182_LOGINURL", "http://e.puredo.com");
        loginUrlMap.put("261_LOGINURL", "http://e.ausnutria.com");
    }

    @Override
    public void initPassWord(String parameter) throws SQLException {
        List<UserPwdTempEntity_HI> listCount = userPwdTempServer.findList(new JSONObject());
        if (listCount.size() > 0) return;

        // 从ess获得用户bu以及邮件
        List<JSONObject> userBuList = getUserBuList();

        JSONObject queryParamJSON = new JSONObject();
        if (null != parameter && StringUtils.isNotEmpty(parameter)) {
            queryParamJSON.put("userName", parameter);
        }
        List<BaseUsersEntity_HI> userList = findUserList(queryParamJSON);

        List<BaseUsersEntity_HI> needModifyList = getNeedModifyList(userList, userBuList);

        for (BaseUsersEntity_HI user : needModifyList) {
            String userName = user.getUserName();
            Map<String, String> map = getBUAndEmailByUserName(userName, userBuList);
            String bu = map.get("ouOrganizationId");
            String email = map.get("email");

            JSONObject jsonObject = new JSONObject();
            //生成随机密码
            String randomPwd = getPwdRandom(6);

            String password = DigestUtils.md5(randomPwd);
            user.setEncryptedPassword(password);
            baseUsersServer.updateProperty(user, "encryptedPassword", password);

            JSONObject jo = new JSONObject();
            jo.put("userName", userName);
            List<UserPwdTempEntity_HI> count = userPwdTempServer.findList(jo);
            if (count.size() > 0) continue;

            UserPwdTempEntity_HI userPwdTemp = new UserPwdTempEntity_HI();
            userPwdTemp.setBu(bu);
            userPwdTemp.setEmail(email);
            userPwdTemp.setPwd(randomPwd);
            userPwdTemp.setUserName(userName);
            userPwdTemp.setSendFlag("0");
            userPwdTemp.setOperatorUserId(1);
            userPwdTempServer.save(userPwdTemp);
        }
    }

    @Override
    public void sendEmailBatch(String userName)  {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("sendFlag", "0");
//        List<UserPwdTempEntity_HI> userPwdTempList = userPwdTempServer.findList(jsonObject);
//        for (UserPwdTempEntity_HI entity : userPwdTempList) {
//            try {
//                if ("empty".equals(entity.getBu()) || StringUtils.isEmpty(entity.getEmail())) {
//                    continue;
//                }
//                String loginUrl = getUrlByBU(entity.getBu());
//                sendEmail(entity, loginUrl);
//                entity.setSendFlag("1");
//            }catch (SendFailedException e){
//                LOGGER.error(e.getMessage(),e);
//                entity.setSendFlag("2");
//            } catch (MessagingException e) {
//                entity.setSendFlag("3");
//                LOGGER.error(e.getMessage(),e);
//            }catch (Exception e){
//                entity.setSendFlag("4");
//                LOGGER.error(e.getMessage(),e);
//            }finally {
//                userPwdTempServer.saveOrUpdate(entity);
//            }
//        }
    }

    @Override
    public void initPassWord82(String parameter) throws SQLException {
        // 82总部角色用户
        List<JSONObject> userBuList = getUserBuList82();

        // 新平台用户列表
        JSONObject queryParamJSON = new JSONObject();
        if (null != parameter && StringUtils.isNotEmpty(parameter)) {
            queryParamJSON.put("userName", parameter);
        }
        List<BaseUsersEntity_HI> userList = findUserList(queryParamJSON);

        List<BaseUsersEntity_HI> needModifyList = getNeedModifyList82(userList, userBuList);

        for (BaseUsersEntity_HI user : needModifyList) {
            String userName = user.getUserName();
            String randomPwd = getPwdRandom(6);
            String password = DigestUtils.md5(randomPwd);
            user.setEncryptedPassword(password);
            baseUsersServer.updateProperty(user, "encryptedPassword", password);

            JSONObject jo = new JSONObject();
            jo.put("userName", userName);
            List<UserPwdTempEntity_HI> count = userPwdTempServer.findList(jo);
            if (count.size() > 0) continue;

            Map<String, String> map = getBUAndEmailByUserName(userName, userBuList);
            String bu = map.get("ouOrganizationId");
            String email = map.get("email");

            UserPwdTempEntity_HI userPwdTemp = new UserPwdTempEntity_HI();
            userPwdTemp.setBu(bu);
            userPwdTemp.setEmail(email);
            userPwdTemp.setPwd(randomPwd);
            userPwdTemp.setUserName(userName);
            userPwdTemp.setSendFlag("0");
            userPwdTemp.setOperatorUserId(1);
            userPwdTempServer.save(userPwdTemp);
        }
    }


    @Override
    public List<BaseUsersEntity_HI> findUserList(JSONObject queryParamJSON) {
        StringBuffer querySQL = new StringBuffer(" FROM BaseUsersEntity_HI WHERE user_type = '20'");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "USER_NAME", "userName", querySQL, paramMap, "=", false);
        List<BaseUsersEntity_HI> userEntitys = baseUsersDAO_HI.findList(querySQL.toString(), paramMap);
        return userEntitys;
    }

    //获取随机密码
    private String getPwdRandom(int length) {
        String val = "";
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    //邮件通知
//    private void sendEmail(UserPwdTempEntity_HI entity, String loginUrl)  {
//        MimeMessage message = null;
//        message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setFrom("notice@ausnutria.com");
//        helper.setTo(entity.getEmail());
//        helper.setSubject("企业管理平台启用通知");
//
//        String text = "您好！欢迎您使用_BU_企业管理平台！\n<br>" +
//                "\n<br>" +
//                "系统网址：_URL_\n<br>" +
//                "用户名：_USERNAME_\n<br>" +
//                "初始密码：_PWD_\n<br>" +
//                "\n<br>" +
//                "登陆后请修改密码，密码长度需大于6位。\n<br>" +
//                "温馨提示：请使用Google Chrome、火狐（Mozilla Firefox）、<font color='red'>IE11</font>及以上版本、Safari等浏览器操作。如果电脑中IE浏览器的版本低于<font color='red'>IE11</font>，使用360、QQ、搜狗等浏览器可能也无法登录。";
//
//        helper.setText(changeTitle(text, entity, loginUrl), true);
//        mailSender.send(message);
//    }

    // 佳贝艾特 能力多 美纳多 海普诺凯生物 澳优 82,81,182,181,261
    private List<BaseUsersEntity_HI> getNeedModifyList(List<BaseUsersEntity_HI> userList, List<JSONObject> userBulist) {
        List<BaseUsersEntity_HI> retList = new ArrayList<BaseUsersEntity_HI>();
        for (BaseUsersEntity_HI entity : userList) {
            String userName = entity.getUserName();
            Map<String, String> map = getBUAndEmailByUserName(userName, userBulist);
            String bu = map.get("ouOrganizationId");
            if (bu.equals("81") || bu.equals("181") || bu.equals("182") || bu.equals("261")) {
                // 其他只处理员工
                retList.add(entity);
            }
        }
        return retList;
    }

    private List<BaseUsersEntity_HI> getNeedModifyList82(List<BaseUsersEntity_HI> userList, List<JSONObject> userBuList) {
        List<BaseUsersEntity_HI> retList = new ArrayList<BaseUsersEntity_HI>();
        for(JSONObject jo:userBuList){
            String userName = jo.get("USER_NAME").toString();
            BaseUsersEntity_HI user = getMysqlUserByUserName(userName,userList);
            retList.add(user);
        }
        return retList;
    }

    private BaseUsersEntity_HI getMysqlUserByUserName(String userName,List<BaseUsersEntity_HI> userList) {
        for (BaseUsersEntity_HI entity : userList) {
            String uName = entity.getUserName();
            if(userName.equals(uName)) return entity;
        }
        return null;
    }

    private Map<String, String> getBUAndEmailByUserName(String userName, List<JSONObject> userBulist) {
        Map<String, String> map = new HashMap<String, String>();
        String orgId = "empty";
        String email = "";
        for (JSONObject jsonObject : userBulist) {
            if (jsonObject.get("USER_NAME").equals(userName)) {
                orgId = jsonObject.getString("OU_ORGANIZATION_ID");
                email = jsonObject.getString("EMAIL");
            }
        }
        map.put("ouOrganizationId", orgId);
        map.put("email", email);
        return map;
    }


    @Override
    public List<BasePersonEntity_HI> findPersonList(JSONObject paramJSON) {
        StringBuffer querySQL = new StringBuffer(" FROM BasePersonEntity_HI WHERE 1=1");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(paramJSON, "personId", "personId", querySQL, paramMap, "=");
        List<BasePersonEntity_HI> personEntitys = basepersonDAO_HI.findList(querySQL.toString(), paramMap);

        return personEntitys;
    }

//    private String changeTitle(String text, UserPwdTempEntity_HI entity, String url) {
//        if (entity.getBu().equals("82")) {
//            text = text.replace("_BU_", "佳贝艾特");
//        } else if (entity.getBu().equals("81")) {
//            text = text.replace("_BU_", "澳优");
//        } else if (entity.getBu().equals("182")) {
//            text = text.replace("_BU_", "美纳多");
//        } else if (entity.getBu().equals("181")) {
//            text = text.replace("_BU_", "海普诺凯生物");
//        } else if (entity.getBu().equals("261")) {
//            text = text.replace("_BU_", "澳优");
//        }
//        text = text.replace("_URL_", url);
//        text = text.replace("_USERNAME_", entity.getUserName());
//        text = text.replace("_PWD_", entity.getPwd());
//        return text;
//    }

    private String getUrlByBU(String bu) {
        String ret = "";
        ret = loginUrlMap.get(bu + "_LOGINURL");
        return ret;
    }

    private List<JSONObject> getUserBuList() throws SQLException {
        List<JSONObject> orgs = new ArrayList<>();
//        StringBuffer sb = new StringBuffer();
//        sb.append("SELECT c.OU_ORGANIZATION_ID,A.USER_NAME,A.USER_ID ,\n" +
//                "(\n" +
//                "SELECT PF.EMAIL_ADDRESS FROM APPS.PER_ALL_PEOPLE_F PF WHERE SYSDATE BETWEEN PF.EFFECTIVE_START_DATE AND PF.EFFECTIVE_END_DATE AND   PF.PERSON_ID=A.PERSON_ID AND ROWNUM=1\n" +
//                ") AS EMAIL\n" +
//                "\n" +
//                "FROM BASE.BASE_USER A , AUPORTAL.BASE_HR_POSITION_ASSIGN_V B,AUPORTAL.BASE_HR_POSITION_V C  WHERE A.PERSON_ID = B.PERSON_ID\n" +
//                "AND SYSDATE BETWEEN B.PEOPLE_START_DATE AND B.PEOPLE_END_DATE AND SYSDATE BETWEEN B.EFFECTIVE_START_DATE AND B.EFFECTIVE_END_DATE AND SYSDATE BETWEEN C.EFFECTIVE_START_DATE AND C.EFFECTIVE_END_DATE\n" +
//                "AND B.PRIMARY_FLAG = 'Y'\n" +
//                "AND C.POSITION_ID = B.POSITION_ID \n" +
//                "AND SYSDATE BETWEEN A.BEGIN_DATE AND A.END_DATE");
//        List<JSONObject> orgs = oracleTemplateServer.findList(sb.toString());
        return orgs;
    }

    private List<JSONObject> getUserBuList82() throws SQLException{
        List<JSONObject> orgs = new ArrayList<>();
//        StringBuffer sb = new StringBuffer();
//        sb.append("SELECT * FROM (SELECT c.OU_ORGANIZATION_ID,A.USER_NAME,A.USER_ID ,C.POSITION_ID,C.POSITION_NAME,\n" +
//                "(\n" +
//                "select pf.email_address from APPS.PER_ALL_PEOPLE_F pf where sysdate Between pf.effective_start_date and pf.effective_end_date and   pf.person_id=a.person_id and rownum=1\n" +
//                ") as EMAIL,\n" +
//                "(\n" +
//                "SELECT A2.IS_PROVINCET\n" +
//                "  FROM AUPORTAL.BASE_HR_POSITION_V A1,\n" +
//                "       (SELECT FV.FLEX_VALUE,\n" +
//                "               FV.ATTRIBUTE2 IS_PROVINCET\n" +
//                "          FROM APPLSYS.FND_FLEX_VALUE_SETS FVS,\n" +
//                "               APPS.FND_FLEX_VALUES_VL     FV\n" +
//                "         WHERE FVS.FLEX_VALUE_SET_ID = FV.FLEX_VALUE_SET_ID\n" +
//                "           AND FVS.FLEX_VALUE_SET_NAME LIKE 'CUX_PROVINCE') A2\n" +
//                " WHERE A1.PROVINCE = A2.FLEX_VALUE\n" +
//                "       AND A1.POSITION_ID = C.POSITION_ID\n" +
//                ") IS_PROVINCE\n" +
//                "FROM BASE.BASE_USER A , AUPORTAL.BASE_HR_POSITION_ASSIGN_V B,AUPORTAL.BASE_HR_POSITION_V C  WHERE A.PERSON_ID = B.PERSON_ID\n" +
//                "AND SYSDATE BETWEEN B.PEOPLE_START_DATE AND B.PEOPLE_END_DATE AND SYSDATE BETWEEN B.EFFECTIVE_START_DATE AND B.EFFECTIVE_END_DATE AND SYSDATE BETWEEN C.EFFECTIVE_START_DATE AND C.EFFECTIVE_END_DATE\n" +
//                "AND B.PRIMARY_FLAG = 'Y'\n" +
//                "AND C.POSITION_ID = B.POSITION_ID \n" +
//                "AND SYSDATE BETWEEN A.BEGIN_DATE AND A.END_DATE\n" +
//                "AND c.OU_ORGANIZATION_ID = '82') AA WHERE AA.IS_PROVINCE = 'N'");
//        orgs = oracleTemplateServer.findList(sb.toString());
        return orgs;
    }
}
