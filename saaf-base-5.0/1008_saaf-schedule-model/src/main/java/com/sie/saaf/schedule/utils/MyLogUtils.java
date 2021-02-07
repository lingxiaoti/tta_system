package com.sie.saaf.schedule.utils;

import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesErrorEntity_HI;
import com.sie.saaf.schedule.model.inter.IScheduleSchedulesError;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 读、写请求执行日志。
 */
@Component
public class MyLogUtils {

    //1汇报关系,2人员,3组织,4部门,5市场,6店铺对应市场,7供应商信息,8ITEM信息,9purchase数据,10销售数据
/*
    public static final Integer SCHEDULE_RELATIONSHIP_TYPE = 1;
    public static final Integer SCHEDULE_PERSON_TYPE = 2;
    public static final Integer SCHEDULE_ORG_TYPE = 3;
    public static final Integer SCHEDULE_DEPT_TYPE = 4;
    public static final Integer SCHEDULE_MARKET_TYPE = 5;
    public static final Integer SCHEDULE_SHOP_TYPE = 6;
    public static final Integer SCHEDULE_SUPPLIER_TYPE = 7;
    public static final Integer SCHEDULE_ITEM_TYPE = 8;
    public static final Integer SCHEDULE_PURCHASE_TYPE = 9;
    public static final Integer SCHEDULE_SALES_TYPE = 10;
*/

    public static final String SCHEDULE_ERROR_STATUSCODE = "ERROR_STATUSCODE";	//失败
    public static final String SCHEDULE_OK_STATUSCODE = "OK_STATUSCODE"; //	正常
    public static final String SCHEDULE_UNDEFINED_STATUSCODE	= "UNDEFINED_STATUSCODE"; //待定


    private static final Logger LOGGER = LoggerFactory.getLogger(MyLogUtils.class);

    @Autowired
    private MongoTemplate mongoTemplate;


    //用于缓存日志内容。格式为（HashMap<日志文件名称，日志内容>）
//    private static HashMap<String, StringBuilder> openedFileConstants = new HashMap<String, StringBuilder>();

    private MyLogUtils() {
        super();
    }
//
//    private static synchronized StringBuilder getFromOpenedFileConstants(String key) {
//
//        return openedFileConstants.get(key);
//    }
//
//    private static synchronized Object putIntoOpenedFileConstants(String key, StringBuilder value) {
//
//        return openedFileConstants.put(key, value);
//    }
//
//    private static synchronized StringBuilder removeFromOpenedFileConstants(String key) {
//
//        return openedFileConstants.remove(key);
//    }


    /**
     * 用于日志记录。
     *
     * @param fileName
     * @param info
     */
    public void info(String fileName, String info) {
//
//        if (getFromOpenedFileConstants(fileName) != null) {
//            getFromOpenedFileConstants(fileName).append();
//        } else {
//
//
//            //      String fileConstants = "";
//
//            //     fileConstants = readLogFile(fileName);
//            //      newFileLogConstants.append(fileConstants + "\r\n");
//
//
//            putIntoOpenedFileConstants(fileName, newFileLogConstants);
//        }

        StringBuilder newFileLogConstants = new StringBuilder();
        newFileLogConstants.append("< " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " > INFO > " + info + "\r\n");
        writeLogFile(fileName, newFileLogConstants.toString());//改为直接写入
    }

    /**
     * 用于记录错误日志。
     *
     * @param fileName
     * @param info
     */
    public void error(String fileName, String info) {

//        if (getFromOpenedFileConstants(fileName) != null) {
//            getFromOpenedFileConstants(fileName).append("< " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " > *** ERROR *** > " + info + "\r\n");
//        } else {
//            StringBuilder newFileLogConstants = new StringBuilder();
//
////            String fileConstants = "";
////            fileConstants = readLogFile(fileName);
////            newFileLogConstants.append(fileConstants + "\r\n");
//
//
//            newFileLogConstants.append("< " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " > *** ERROR *** > " + info + "\r\n");
//            putIntoOpenedFileConstants(fileName, newFileLogConstants);
//        }

        StringBuilder newFileLogConstants = new StringBuilder();
        newFileLogConstants.append("< " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " > *** ERROR ***  > " + info + "\r\n");
        writeLogFile(fileName, newFileLogConstants.toString());//改为直接写入
    }

    /**
     * 实际写日志文件。（指定文件不存在则创建。）
     */
    private void writeLogFile(String fileName, String info) {

        //更换为写mongdb
        ScheduleLogDocument scheduleLogDocument = new ScheduleLogDocument();
        scheduleLogDocument.setScheduleId(fileName);
        scheduleLogDocument.setMessage(info);
        scheduleLogDocument.setTime(new Date());
        //mongoTemplate.insert(scheduleLogDocument, fileName);


    }

    /**
     * 实际读日志文件。（指定文件不存在则返回提示信息。）
     */
    private String readLogFile(String fileName) {

//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder fileConstants = new StringBuilder();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR_OF_DAY, -48);


//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(new Date());

        Query query = new Query();

        //查询时间最近时间段内的数据,2天内的数据显示

//        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query.addCriteria(Criteria.where("time").gte(c.getTime()));//.lt(fromISODate(c2.getTime().toString()))
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "time")));

        List<ScheduleLogDocument> logList = mongoTemplate.find(query, ScheduleLogDocument.class, fileName);
        if (logList != null && logList.size() > 0) {
            for (int i = 0; i < logList.size(); i++) {
                fileConstants.append(logList.get(i).getMessage().toString() + "\r\n");
            }
        }

        return fileConstants.toString();
    }


    /**
     * 用于获取指定名称的日志文件的内容。
     *
     * @param fileName
     * @return
     */
    public String getLogContents(String fileName) {
//        if (getFromOpenedFileConstants(fileName) == null) { //如果指定的日志文件未缓存，则尝试读取。
//            return readLogFile(fileName);
//        } else {
//            //有缓存的内容,把缓存写入mongdb, 重新读取文件信息
//            writeLogFile(fileName, getFromOpenedFileConstants(fileName).toString());
//            removeFromOpenedFileConstants(fileName);
//            return readLogFile(fileName);
//        }
        return readLogFile(fileName);//改为直接读取

    }

//    /**
//     * 实际写入单个日志文件。（请求执行完毕时调用）
//     */
//    public void flushLogFileConstants(String fileName) {
//
//        writeLogFile(fileName, getFromOpenedFileConstants(fileName).toString());
//        removeFromOpenedFileConstants(fileName);
//    }


//    /**
//     * 实际写入所有日志文件。（web应用关闭时调用）
//     */
//    public void flushAllLogFileConstants() {
//
//        for (String fileName : openedFileConstants.keySet()) {
//            writeLogFile(fileName, getFromOpenedFileConstants(fileName).toString());
//        }
//    }


    @SuppressWarnings("all")
    public static void saveErrorData(Integer scheduleId, String errorStr, String description, String scheduleData, String status){
        try {
            IScheduleSchedulesError schedulesError = (IScheduleSchedulesError) SpringBeanUtil.getBean("scheduleSchedulesErrorServer");
            ScheduleSchedulesErrorEntity_HI errorjob = new ScheduleSchedulesErrorEntity_HI();
            errorjob.setErrorStr(errorStr);
            errorjob.setDescription(description);//描述
            errorjob.setScheduleId(scheduleId);
            errorjob.setScheduleData(scheduleData);//调度参数
            errorjob.setStatus(status);
            errorjob.setOperatorUserId(0);
            schedulesError.save(errorjob);
        }catch(Exception e){
            LOGGER.error(".saveErrorData", e);
        }
    }

    public static Object getArguments(JobExecutionContext context, String argName) {
        Object value = -1;
        try {
            if (context == null || context.getJobDetail() == null || context.getJobDetail().getJobDataMap() == null) {
                return  value;
            }
            value = ((HashMap<String, Object>)context.getJobDetail().getJobDataMap().get(argName)).values().toArray()[0];
            if (value == null || StringUtil.isEmpty(value + "") ||  "null".equalsIgnoreCase(value + "")) {
                value = -1;
            }
        } catch (Exception e) {
            LOGGER.error("******** 调度执行，获取参数失败：指定名称 <" + argName + "> 的参数不存在");
        }
        return value;
    }



    public static String getErrorMsg (Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.fillInStackTrace().printStackTrace(printWriter);
        return result.toString();
    }
}
