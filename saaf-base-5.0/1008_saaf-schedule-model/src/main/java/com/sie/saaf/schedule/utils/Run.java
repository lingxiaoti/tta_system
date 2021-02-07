package com.sie.saaf.schedule.utils;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.base.utils.restful.RestfulClientUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class Run implements InterruptableJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(Run.class);

    public Run() {
        super();
    }

    private MyLogUtils myLogUtils=(MyLogUtils)SpringBeanUtil.getBean("myLogUtils");;



    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {

            // 这里定义我们执行的定时任务的具体内容，比如订单处理完成以后，我们可以修改订单状态字段。
            LOGGER.info("定时任务开始执行...");
            // 获取当前执行的定时任务的细节
            LOGGER.info("context.getJobDetail()=" + context.getJobDetail());
            // 获取当前执行的定时任务的名称
           LOGGER.info("context.getJobDetail().getKey()=" + context.getJobDetail().getKey());
            // 获取当前执行的触发器的细节
           LOGGER.info("context.getTrigger() = " + context.getTrigger());
            // 获取当前执行的触发器的名称
           LOGGER.info("context.getTrigger().getKey() = " + context.getTrigger().getKey());
            // 获取当前执行定时任务的时间
           LOGGER.info("context.getScheduledFireTime() = " + context.getScheduledFireTime());


            // 0：忽略， -1：一直尝试，直到成功 ， 9：尝试9次
            int failAttemptFrequency =
                    Integer.parseInt("" + getArguments(context, "failAttemptFrequency") != null && getArguments(context, "failAttemptFrequency").toString().length() > 0 ?
                            getArguments(context, "failAttemptFrequency").toString() : "0");
            // -1：不作限制， 5：超时时间5秒
            int timeout =
                    Integer.parseInt("" + getArguments(context, "timeout") != null && getArguments(context, "timeout").toString().length() > 0 ? getArguments(context, "timeout").toString() :
                            "-1");
            if (failAttemptFrequency < 0) {
                //如果失败尝试为 一直尝试，直到成功  则默认最多尝试 99999 次
                failAttemptFrequency = 99999;
            }
            if (timeout <= 0) {
                //如果没有设置超时参数， 则强制默认 超时时间为 60 秒
                timeout = 60 * 1000;
            } else {
                timeout = timeout * 1000;
            }
            String jobType = "" + getArguments(context, "jobType");
            if (jobType.equalsIgnoreCase("webservice")) {
                run_webservice(context, timeout, failAttemptFrequency);
            } else if (jobType.equalsIgnoreCase("java")) {
                run_javaClass(context);
            } else if (jobType.equalsIgnoreCase("package")) {
                run_package(context, timeout, failAttemptFrequency);
            } else {

                this.loggerError(context, "******** 调度执行，webservice 任务执行失败" + "未知任务类型（jobType）=" + jobType + "，jobName=" + context.getJobDetail().getKey().getName());

            }
        } catch (Exception e) {

            this.loggerError(context, "******** 调度执行  任务执行失败" + "，jobName=" + context.getJobDetail().getKey().getName() + ", exception=" + getDetailErrorMessage(e));

            //插入错误日志表,进行记录操作信息





            throw new JobExecutionException(e);
        }
    }


    private void run_package(JobExecutionContext context, int socketTimeout, int retryCount) throws Exception {
        String executableName = ("" + getArguments(context, "executableName")).trim();
        //  String method = "" + getArguments(context, "method");

        this.loggerInfo(context, "******** 调度执行，package 任务，名称=" + executableName);

        //获取输入参数列表
        Map<String, String> inParamsMap = new HashMap<String, String>();
        //获取输出参数列表
        Map<String, String> outParamsMap = new HashMap<String, String>();

        //获取数据库参数列表
        Map<String, String> dbParamsMap = new HashMap<String, String>();


        //获取运行参数
        for (Object item : context.getJobDetail().getJobDataMap().keySet().toArray()) {
            String paramRegion = "" + ((HashMap<String, Object>) context.getJobDetail().getJobDataMap().get(item)).keySet().toArray()[0];
            String defaultValue = "" + getArguments(context, "" + item);
            String paramName = "" + item;
            if (paramRegion.equalsIgnoreCase("IN")) {
                inParamsMap.put(paramName, defaultValue);
            } else if (paramRegion.equalsIgnoreCase("OUT")) {
                outParamsMap.put(paramName, defaultValue);
            } else if (paramRegion.equalsIgnoreCase("DB")) {
                dbParamsMap.put(paramName, defaultValue);
            }

        }

        String url = dbParamsMap.get("DB_URL");
        String user = dbParamsMap.get("DB_USER");
        String password = dbParamsMap.get("DB_PWD");

//        String url = "jdbc:oracle:thin:@192.168.1.185:1521:dev_crm";
//        String user = "BIZCRMDB";
//        String password = "CrmTest8899";


        if (!StringUtils.isEmpty(url) && !StringUtils.isEmpty(user) && !StringUtils.isEmpty(password)) {
            String result = "";
            String msg = "";


            String parms = "";

            //处理参数
            if (inParamsMap.size() > 0 || outParamsMap.size() > 0) {

                parms += "(";
                for (String o : inParamsMap.keySet()) {
//                    System.out.println("key=" + o + " value=" + inParamsMap.get(o));
                    parms += "?,";
                }
                for (String o : outParamsMap.keySet()) {
//                    System.out.println("key=" + o + " value=" + inParamsMap.get(o));
                    parms += "?,";
                }
                parms = parms.substring(0, parms.length() - 1);
                parms += ")";
            }

            Connection con = null;
            try {

                con = this.getConnection(url, user, password);
                CallableStatement proc = null;
                proc = con.prepareCall("{ call " + executableName + parms + "}"); // 设置存储过程

                for (String o : inParamsMap.keySet()) {
                    proc.setString(o, inParamsMap.get(o));
                }

                for (String o : outParamsMap.keySet()) {
                    proc.registerOutParameter(o, Types.VARCHAR);// 设置参数
                }

                proc.execute();// 执行


//            retMap.put("result", result);
//            retMap.put("msg", msg);


                for (String o : outParamsMap.keySet()) {
                    msg = proc.getString(o);

                    this.loggerInfo(context, "调度执行，package 任务，executableName=" + executableName + ", 参数:" + o + "--返回:" + msg);

                    if ("S".equals(msg)) {
                        //如果返回参数中带有返回状态S,表示成功
                        result = "S";
                    }

                }


                //根据过程返回内容判断是否成功
                if ("S".equals(result)) {
                    return;
                } else {
                    throw new JobExecutionException("调度执行出错, 返回结果：<result>=" + result);
                }


            } catch (SQLException ex2) {
                LOGGER.error(ex2.getMessage(),ex2);
            } catch (Exception ex2) {
                LOGGER.error(ex2.getMessage(),ex2);
            } finally {
                if (null != con) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(),e);
                    }
                }
            }
        } else {
            this.loggerInfo(context, "******** 调度执行，package 任务，数据库连接参数未设置!" + executableName);
            throw new JobExecutionException("******** 调度执行，package 任务，数据库连接参数未设置!");
        }


        //     Map<String, String> retMap = new HashMap<String, String>();


    }

    public Connection getConnection(String url, String user, String password) {
//        String url="jdbc:oracle:thin:@localhost:1521:orcl";
//        //用户名
//        String user = "scott";
//        //密码
//        String password = "tiger";
        Connection connection = null;
        try {
            //1.加载驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //2.得到连接
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            //记录日志
            LOGGER.error("******** 获取jdbc连接错误误" + e.getMessage());
            throw new RuntimeException("找不到驱动类", e);
        }
    }

    private String run_javaClass(JobExecutionContext context) throws Exception {
        String executableName = "" + this.getArguments(context, "executableName");
        String method = "" + this.getArguments(context, "method");

        try {
            this.getClass();
            Class mJob = Class.forName(executableName);
            Method execute = mJob.getDeclaredMethod(method, JobExecutionContext.class);
            execute.invoke(mJob.newInstance(), context);
            return "200 执行成功 javaClass";
        } catch (ClassNotFoundException var6) {
            myLogUtils.error(context.getJobDetail().getKey().getName(), "ClassNotFoundException，" + var6);
            return "555 执行失败 JavaClass ClassNotFoundException";
        } catch (NoSuchMethodException var7) {
            myLogUtils.error(context.getJobDetail().getKey().getName(), "NoSuchMethodException，" + var7);
            return "555 执行失败 JavaClass NoSuchMethodException";
        } catch (IllegalAccessException var8) {
            myLogUtils.error(context.getJobDetail().getKey().getName(), "IllegalAccessException，" + var8);
            return "555 执行失败 JavaClass IllegalAccessException";
        } catch (InstantiationException var9) {
            myLogUtils.error(context.getJobDetail().getKey().getName(), "InstantiationException，" + var9);
            return "555 执行失败 JavaClass InstantiationException";
        } catch (InvocationTargetException var10) {
            myLogUtils.error(context.getJobDetail().getKey().getName(), "InvocationTargetException，异常" + var10.getTargetException());
            return "555 执行失败 JavaClass InvocationTargetException" + var10.getTargetException();
        }
    }

    private void run_webservice(JobExecutionContext context, int socketTimeout, int retryCount) throws Exception {
        String executableName = ("" + getArguments(context, "executableName")).trim();
        String method = "" + getArguments(context, "method");

        this.loggerInfo(context, "调度执行，webservice 任务，executableName=" + executableName + ", method=" + method);

        //url
        Map<String, String> atUrlParamsMap = new HashMap<String, String>();
        //header
        Map<String, String> atHeaderParamsMap = new HashMap<String, String>();
        //body
        Map<String, Object> atBodyParamsMap = new HashMap<String, Object>();
        //获取运行参数
        for (Object item : context.getJobDetail().getJobDataMap().keySet().toArray()) {
            if(!"parameters".equals(String.valueOf(item))){
                String paramRegion = "" + ((HashMap<String, Object>) context.getJobDetail().getJobDataMap().get(item)).keySet().toArray()[0];
                String defaultValue = "" + getArguments(context, "" + item);
                String paramName = "" + item;
                if (paramRegion.equalsIgnoreCase("HEAD")) {
                    atHeaderParamsMap.put(paramName, defaultValue);
                } else if (paramRegion.equalsIgnoreCase("URL")) {
                    atUrlParamsMap.put(paramName, defaultValue);
                } else if (paramRegion.equalsIgnoreCase("BODY")) {
//                JSONObject paramObj = JSONObject.parseObject(defaultValue);
//                for (Object key : paramObj.keySet().toArray()) {
//                    atBodyParamsMap.put(key.toString(), paramObj.get(key.toString()).toString());
//                }
                    atBodyParamsMap.put(paramName, defaultValue);

                }
            }
        }
        if (method != null && method.equalsIgnoreCase("get")) {
            doWebserviceGet(context, executableName, atUrlParamsMap, atHeaderParamsMap, socketTimeout, retryCount);
        } else if (method != null && method.equalsIgnoreCase("post")) {
            doWebservicePost(context, executableName, atUrlParamsMap, atHeaderParamsMap, atBodyParamsMap, socketTimeout, retryCount);
        } else {
            this.loggerError(context, "******** 调度执行，未知的 webservice method，" + " method=" + method);

            throw new Exception("******** 调度执行，未知的 webservice method，" + " method=" + method);

        }
    }

    private void doWebserviceGet(JobExecutionContext context, String url, Map<String, String> atUrlParamsMap, Map<String, String> atHeaderParamsMap, int socketTimeout, int retryCount) throws Exception {

        this.loggerInfo(context, "调度执行，<doWebserviceGet> 方法，参数：<url>=" + url + ", <atUrlParamsMap>=" + JSONObject.toJSONString(atUrlParamsMap) + ", <atHeaderParamsMap>=" +
                JSONObject.toJSONString(atHeaderParamsMap) + ", <socketTimeout>=" + socketTimeout + ", <retryCount>=" + retryCount);

        do {
            String tokenValue = "0";
          /*  WSSecurityPolicy wsSecurityPolicy = (WSSecurityPolicy)SaafToolUtils.context.getBean("wsSecurityPolicy");
            String tokenValue = wsSecurityPolicy.getTokenByKey("schedule_Token");
            if (null == tokenValue || tokenValue.equals("")) {
                tokenValue = wsSecurityPolicy.generateStaticToken("schedule_Token", 0);
            }*/
            atHeaderParamsMap.put("TokenCode", tokenValue);
            atHeaderParamsMap.put("TokenKey", "schedule_Token");
            String result = RestfulClientUtils.restfulGet(url, atHeaderParamsMap, socketTimeout);

            this.loggerInfo(context, "调度执行，<doWebserviceGet> 方法，结果：<result>=" + result);

            //尝试解析 执行返回的结果，并尝试判断 其‘status’是否为‘S’（即 执行成功）
            try {
                JSONObject resultJsonObj = JSONObject.parseObject(result);
                if (resultJsonObj.get("status").toString().equalsIgnoreCase("S")) {
                    return;
                } else {
                    if (retryCount == 0) {
                        throw new JobExecutionException("调度执行出错, 返回结果：<result>=" + result);
                    }
                }
            } catch (JobExecutionException e) {
                throw e;
            } catch (Exception ignore) {
                //被默认成功
                LOGGER.error("******** 调度执行异常，<doWebserviceGet> 方法", ignore);
                myLogUtils.error(context.getJobDetail().getKey().getName(), "调度执行异常，<doWebserviceGet> 方法" + ignore.getMessage());
                //其他异常,同样返回任务执行出错
                throw new JobExecutionException("调度执行出错, 返回结果：<result>=" + result);
            }
        } while (retryCount-- > 0);
    }

    private void doWebservicePost(JobExecutionContext context, String url, Map<String, String> atUrlParamsMap, Map<String, String> atHeaderParamsMap, Map<String, Object> atBodyParamsMap, int socketTimeout,
                                  int retryCount) throws Exception {

        this.loggerInfo(context, "调度执行，<doWebservicePost> 方法，参数：<url>=" + url + ", <atUrlParamsMap>=" + JSONObject.toJSONString(atUrlParamsMap) + ", <atHeaderParamsMap>=" +
                JSONObject.toJSONString(atHeaderParamsMap) + ", <atBodyParamsMap>=" + JSONObject.toJSONString(atBodyParamsMap) + ", <socketTimeout>=" + socketTimeout +
                ", <retryCount>=" + retryCount);

        do {
            String tokenValue = "0";
            /*  WSSecurityPolicy wsSecurityPolicy = (WSSecurityPolicy)SaafToolUtils.context.getBean("wsSecurityPolicy");
              String tokenValue = wsSecurityPolicy.getTokenByKey("schedule_Token");
              if (null == tokenValue || tokenValue.equals("")) {
                  tokenValue = wsSecurityPolicy.generateStaticToken("schedule_Token", 0);
              }*/
            //加入sso登录签名--start
            atHeaderParamsMap.put("TokenCode", tokenValue);
            atHeaderParamsMap.put("TokenKey", "schedule_Token");

            Long timestamp = System.currentTimeMillis();
            atHeaderParamsMap.put("timestamp", timestamp + "");
            atHeaderParamsMap.put("pdasign", SaafToolUtils.buildSign(timestamp));
            //end

            String result = RestfulClientUtils.restfulPostFormParam(url, atBodyParamsMap, atHeaderParamsMap, socketTimeout);

            this.loggerInfo(context, "调度执行，<doWebservicePost> 方法，结果：<result>=" + result);

            //尝试解析 执行返回的结果，并尝试判断 其‘status’是否为‘S’（即 执行成功）
            try {
                JSONObject resultJsonObj = JSONObject.parseObject(result);
                if (resultJsonObj.get("status").toString().equalsIgnoreCase("S")) {
                    return;
                } else {
                    if (retryCount == 0) {
                        throw new JobExecutionException("调度执行出错, 返回结果：<result>=" + result);
                    }
                }
            } catch (Exception ignore) {
                LOGGER.error("******** 调度执行异常，<doWebservicePost> 方法", ignore);
                myLogUtils.error(context.getJobDetail().getKey().getName(), "调度执行异常，<doWebservicePost> 方法" + ignore.getMessage());
                //其他异常,同样返回任务执行出错
                throw new JobExecutionException("调度执行出错, 返回结果：<result>=" + result);
            }
        } while (retryCount-- > 0);
    }

    private Object getArguments(JobExecutionContext context, String argName) throws Exception {
        Object value = null;
        try {
            value = ((HashMap<String, Object>) context.getJobDetail().getJobDataMap().get(argName)).values().toArray()[0];
        } catch (Exception e) {
            this.loggerError(context, "调度执行，获取参数失败：指定名称 <" + argName + "> 的参数不存在");
            throw e;
        }
        return value;
    }

    private String getDetailErrorMessage(Exception e) {
        StringBuilder detailMessageOfError = new StringBuilder();
        if (e != null && e.getStackTrace().length > 0) {
            detailMessageOfError = new StringBuilder().append("\r\n").append("Error: ").append(e.getMessage() == null ? "" : e.getMessage()).append("\r\n");
            for (StackTraceElement item : e.getStackTrace()) {
                detailMessageOfError.append("\t").append(item.toString()).append("\r\n");
            }
            if (e.getCause() != null) {
                for (StackTraceElement item : e.getCause().getStackTrace()) {
                    detailMessageOfError.append("Cause By: \r\n").append(item.toString()).append("\r\n");
                }
            }
        }
        return detailMessageOfError.toString();
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        LOGGER.error("------------Job-interrupted（!）-------被打断!!!!!!!!---------");
    }

    private void loggerInfo(JobExecutionContext context, String msg) {
        LOGGER.info(msg);
        myLogUtils.info(context.getJobDetail().getKey().getName(), msg);
    }

    private void loggerError(JobExecutionContext context, String msg) {
        LOGGER.error(msg);
        myLogUtils.info(context.getJobDetail().getKey().getName(), msg);
    }

}
