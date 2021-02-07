package com.sie.watsons.base.report.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelImportUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelImportUtils.class);
    private static Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE);

    /**
     *  指定阈值
     * @param consumer Consumer函数接口 ,其作用是接受一个参数,不返回值,调用accept方法就会调用相应的业务逻辑方法
     * @param batchCount 批量数
     * @param <T>
     * @return
     */
    public static <T> AnalysisEventListener getListener(Consumer<List<T>> consumer,int batchCount,JedisCluster jedisCluster, UserSessionBean sessionBean){
        return new AnalysisEventListener<T>() {
            private LinkedList<T> linkedList = new LinkedList<>();
            //读取每一行数据的回调
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                LOGGER.info("解析行数{},解析到一条数据:{}",analysisContext.getCurrentRowNum(), JSON.toJSONString(t));
                Integer currentRowNum = analysisContext.getCurrentRowNum();
                //获取行数据
                if (currentRowNum != 0) {
                    try {
                        linkedList.add((T) objToMap(t));
                        if(!SaafToolUtils.isNullOrEmpty(jedisCluster)){
                            jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'U',currentStage:'读取数据阶段',orderNum:"+"'" + currentRowNum+"'}");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (linkedList.size() == batchCount) {
                        consumer.accept(linkedList);//调用此方法,同时会调用相应的业务方法
                        linkedList.clear();
                    }
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (linkedList.size() > 0) {
                    consumer.accept(linkedList);
                }
                LOGGER.info("所有数据解析完成！");
            }

            @Override
            public void onException(Exception exception, AnalysisContext context) throws Exception {
                LOGGER.error("解析失败，失败原因:{}", exception.getMessage());
                super.onException(exception, context);
                if (exception instanceof ExcelDataConvertException) {
                    ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
                    LOGGER.error("第{}行,解析异常:{}", context.getCurrentRowNum(),
                            excelDataConvertException.getMessage());
                }
            }
        };
    }

    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer,JedisCluster jedisCluster, UserSessionBean sessionBean){
        return getListener(consumer,10000,jedisCluster,sessionBean);
    }

    //Object转换为Map(实体对象转换成带下划线的map)
    public static Map<String,Object> objToMap(Object obj) throws Exception{
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            map.put(converter.convert(field.getName()) , field.get(obj));
        }
        return map;
    }

    /**
     *
     * @param function Function函数接口 Function<T,R>   接受一个参数对象,返回一个参数对象,调用apply方法传参调用其业务方法
     * @param batchCount
     * @param jedisCluster
     * @param sessionBean
     * @param <T>
     * @return
     */
    public static <T> AnalysisEventListener getListener(Function<List<T>,Integer> function, int batchCount, JedisCluster jedisCluster, UserSessionBean sessionBean,Integer[] insetNum){
        return new AnalysisEventListener<T>() {
            private LinkedList<T> linkedList = new LinkedList<>();
            private int returnNum = 0;
            //读取每一行数据的回调
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                LOGGER.info("解析行数{},解析到一条数据:{}",analysisContext.getCurrentRowNum(), JSON.toJSONString(t));
                Integer currentRowNum = analysisContext.getCurrentRowNum();
                //获取行数据
                if (currentRowNum != 0) {
                    try {

                        linkedList.add((T) objToMap(t));
                        if(!SaafToolUtils.isNullOrEmpty(jedisCluster)){
                            jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'U',currentStage:'读取数据阶段',orderNum:"+"'" + currentRowNum+"'}");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (linkedList.size() == batchCount) {
                        //consumer.accept(linkedList);//调用此方法,同时会调用相应的业务方法
                        Integer apply = function.apply(linkedList);
                        returnNum  += apply;
                        linkedList.clear();
                    }
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (linkedList.size() > 0) {
                    //consumer.accept(linkedList);
                    Integer apply = function.apply(linkedList);
                    returnNum  += apply;
                }
                insetNum[0] = returnNum;
                //insetNum[0] = analysisContext.getCurrentRowNum();
                LOGGER.info("所有数据解析完成！");
            }

            @Override
            public void onException(Exception exception, AnalysisContext context) throws Exception {
                LOGGER.error("解析失败，失败原因:{}", exception.getMessage());
                super.onException(exception, context);
                if (exception instanceof ExcelDataConvertException) {
                    ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
                    LOGGER.error("第{}行,解析异常:{}", context.getCurrentRowNum(),
                            excelDataConvertException.getMessage());
                }
            }
        };
    }



    /**将Sheet列号变为列名
     * @param index 列号, 从0开始
     * @return 0->A; 1->B...26->AA
     */
    public static String index2ColName(int index) {
        if (index < 0) {
            return null;
        }
        int num = 65;// A的Unicode码
        String colName = "";
        do {
            if (colName.length() > 0) {
                index--;
            }
            int remainder = index % 26;
            colName = ((char) (remainder + num)) + colName;
            index = (int) ((index - remainder) / 26);
        } while (index > 0);
        return colName;
    }

    /**根据表元的列名转换为列号
     * @param colName 列名, 从A开始
     * @return A1->0; B1->1...AA1->26
     */
    public static int colName2Index(String colName) {
        int index = -1;
        int num = 65;// A的Unicode码
        int length = colName.length();
        for (int i = 0; i < length; i++) {
            char c = colName.charAt(i);
            if (Character.isDigit(c)) break;// 确定指定的char值是否为数字
            index = (index + 1) * 26 + (int)c - num;
        }
        return index;
    }


    public static final List<String> getFormula(String formulaStr) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(.*?)]", Pattern.CASE_INSENSITIVE);//忽略大小写
        Matcher matcher = pattern.matcher(formulaStr);
        while(matcher.find()){
            list.add(matcher.group());
        }
        return list;
    }

    public static final String getFormula(String formulaStr, int offset) {
        if (StringUtils.isBlank(formulaStr)) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\[(.*?)]", Pattern.CASE_INSENSITIVE);//忽略大小写
        Matcher matcher = pattern.matcher(formulaStr);
        while(matcher.find()){
            String orginStr = matcher.group();
            String formula = orginStr.replaceAll("\\[|\\]", "");
            String changeFormula = "~"+ ExcelImportUtils.index2ColName(ExcelImportUtils.colName2Index(formula) + offset) + "^";
            formulaStr = formulaStr.replace(orginStr, changeFormula);
        }
        return formulaStr.replace("~","[").replace("^","]");
    }
}
