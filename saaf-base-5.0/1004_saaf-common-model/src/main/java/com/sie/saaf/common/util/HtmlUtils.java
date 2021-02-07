package com.sie.saaf.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.perl.Perl5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class HtmlUtils {

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("js");

    private static final Logger log = LoggerFactory.getLogger(HtmlUtils.class);

    /**
     * 功能描述： 获取需要替换的内容
     * @date 2019/9/4
     * @param contentTex 文本内容
     * @return paramKey 如参数：startEB004End
     */
    public static String getContentReplace(String contentTex, String paramKey) {
        Pattern pattern= Pattern.compile("<p[\\w\\W]*?start\\w{1,}End[\\w\\W]*?</p>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(contentTex);
        while (matcher.find()) {
            String group = matcher.group();
            if (StringUtils.isNotBlank(group) && group.contains(paramKey)) {
                return group;
            }
        }
        return null;
    }
    
    
    public static String replaceContentByParamKey(String content, String paramKey) {
    	return content.replaceAll("<p[\\w\\W]*?[" + paramKey + "][\\w\\W]*?</p>", "");
    }

    public static String delHTMLTag(String htmlStr){

        if(StringUtils.isNotEmpty(htmlStr)){
            String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
            String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
            String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

            Pattern p_script=Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script=p_script.matcher(htmlStr);
            htmlStr=m_script.replaceAll(""); //过滤script标签

            Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
            Matcher m_style=p_style.matcher(htmlStr);
            htmlStr=m_style.replaceAll(""); //过滤style标签

            Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            Matcher m_html=p_html.matcher(htmlStr);
            htmlStr=m_html.replaceAll(""); //过滤html标签

            return htmlStr.trim(); //返回文本字符串
        }else{
            return "";
        }

    }

    public static String getContentKey(String contentTex, String paramKey) {
        try {
            Pattern pattern= Pattern.compile("<p[\\w\\W]*?["  + paramKey + "][\\w\\W]*?</p>", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(contentTex);
            while (matcher.find()) {
                String group = matcher.group();
                if (group.contains(paramKey)) {
                    return group;
                }
            }
        } catch (Exception e) {
            log.error(".getContentKey error:{}", e);
        }

        return null;
    }



    /**
     * 功能描述： 移除一些标签
     * @date 2019/8/15
     * @param
     * @return
     */
    public static String clearHTMLTag(String htmlStr) {
        StringBuffer buffer = new StringBuffer();
        Perl5Util preg = new Perl5Util();
        preg.substitute(buffer,"s/<script[^>]*?>.*?<\\/script>//gmi",htmlStr);
        htmlStr =buffer.toString();
        buffer.setLength(0);
        //"s#<[/]*?(?!p|img|span)[^<>]*?>##gmi"
        preg.substitute(buffer,"s#<[/]*?(?!p|/p)[^<>]*?>##gmi",htmlStr);
        //(?# a,/a,p/br ...标签之外,都删除)
        htmlStr =buffer.toString();
        buffer.setLength(0);
        return htmlStr;
    }

    public static String removeTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style
        String regEx_html = "<[^>]+>"; // HTML tag
        String regEx_space = "\\s+|\t|\r|\n";// other characters

        Pattern p_script = Pattern.compile(regEx_script,
                Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");

        Pattern p_style = Pattern
                .compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");

        Pattern p_space = Pattern
                .compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(" ");

        return htmlStr;

    }

    /**
     * 功能描述： 移除一些标签
     * @date 2019/8/15
     * @param
     * @return
     */
    public static String clearHTMLTagExculdeSpan(String htmlStr) {
        StringBuffer buffer = new StringBuffer();
        Perl5Util preg = new Perl5Util();
        preg.substitute(buffer,"s/<script[^>]*?>.*?<\\/script>//gmi",htmlStr);
        htmlStr =buffer.toString();
        buffer.setLength(0);
        //"s#<[/]*?(?!p|img|span)[^<>]*?>##gmi"
        preg.substitute(buffer,"s#<[/]*?(?!p|/p|span|/span)[^<>]*?>##gmi",htmlStr);
        //(?# a,/a,p/br ...标签之外,都删除)
        htmlStr =buffer.toString();
        buffer.setLength(0);
        return htmlStr;
    }

    /** img|p|span 标识过滤HTML里去除img、p、span外的所有标签
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        String regEx = "(?!<(img|span|/span).*?>)<.*?>";
        Pattern p_html = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(str);
        str = m_html.replaceAll("");
        return str.trim(); // 返回文本字符串
    }


    public static String clearStyle(String htmlStr) {
        // 正则表达式
        String regEx = " style=\"(.*?)\"";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(htmlStr);
        if (m.find()) {
            htmlStr = m.replaceAll("");
        }
        return htmlStr;
    }

    public static String clearStyleExculdeSpan(String htmlStr) {
        // 正则表达式
        String regEx = "<span style=\"(.*?)\">(.*?)</span>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(htmlStr);

        Map<String,String> htmlMap = new HashMap<>();
        int i = 64;
        while (m.find()) {
            i++;
            String group = m.group();
            String indexStr = "#" + (char)i + "#";
            htmlMap.put(indexStr,group);
            htmlStr = htmlStr.replace(group, indexStr);
        }

        String regExStyle = " style=\"(.*?)\"";
        Pattern regExStylePattern = Pattern.compile(regExStyle);
        Matcher regExStyleMatcher = regExStylePattern.matcher(htmlStr);
        if (regExStyleMatcher.find()) {
            htmlStr = m.replaceAll("");
        }

        for (Map.Entry<String, String> next : htmlMap.entrySet()) {
            String key = next.getKey();
            String value = next.getValue();
            if (htmlStr.contains(key)) {
                htmlStr = htmlStr.replace(key, value);
            }
        }
/*        while (m.find()) {
            //htmlStr = m.replaceAll("");
            String group = m.group();
            int startIndex = m.start();//获取匹配的开始索引
            String substring = htmlStr.substring(0, startIndex);//截取字符串
            Matcher regExStyleMatcher = regExStylePattern.matcher(substring);
            if (regExStyleMatcher.find()) {
                substring = regExStyleMatcher.replaceAll("");//替换style样式
            }
        }*/
        return htmlStr;
    }

    public static String readTxtContent(String filePath) {
        StringBuffer content = new StringBuffer();
        BufferedReader reader = null;
        FileInputStream ins = null;
        try {
            ins = new FileInputStream(filePath);
            reader = new BufferedReader(new InputStreamReader(ins, "utf-8"));
            String line;
            //网友推荐更加简洁的写法
            while ((line = reader.readLine()) != null) {
                // 一次读入一行数据
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

    /**
     * String str ="This Proposal is for {{Y==Existing supplier||N==Old}} supplier with contract renewal for 2019, in which:";
     * @param result
     * @param contentStr
     * @return
     */
    public static String getResult(String result, String contentStr) {
        try {
            String resulStr = "";
            if (contentStr.contains("{{") && contentStr.contains("}}") && contentStr.contains("==")) {
                int start = contentStr.indexOf("{{");
                int end =contentStr.indexOf("}}");
                String subStr = contentStr.substring(start+2, end);
                List<String> asList = Arrays.asList(subStr.split("\\|\\|"));
                for (String s : asList) {
                    if(s.contains(result + "==")){
                        resulStr = s.split("==")[1];
                        break;
                    }
                }
                contentStr = contentStr.replace("{{"+ subStr+ "}}", resulStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentStr;
    }

    public static void setParams(JSONObject jsonParams, HashMap<String, Object> queryMap, String sqlValues) {
        Pattern compile = Pattern.compile(":\\w{1,}");
        Matcher matcher = compile.matcher(sqlValues);
        while(matcher.find()) {
            String mapKey = (matcher.group() + "").replace(":", "");
            Object mapValue = jsonParams.get(mapKey + "");
            log.info("参数值：mapKey:{}, mapValue:{}", mapKey, mapValue);
            queryMap.put(mapKey, mapValue);
        }
    }

    /**
     * 功能描述：动态取值，写法：str=#{year-1}, 如传入变量Map key:year, value:2019, 运算结果为2018
     * @param
     * @return
     */
    public static String convertResult(String str, Map<String, Object> params)  {
        try {
            if (StringUtils.isBlank(str)) {
                return str;
            }
            Pattern pt = Pattern.compile("#[{].*?[}]");
            Matcher mc = pt.matcher(str);
            while (mc.find()) {
                String groupValue = mc.group();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue() + "";
                    if (groupValue.contains(key)) {
                        String detailValue = groupValue.replace(key, value);
                        detailValue = detailValue.substring(2, detailValue.length() - 1);
                        str = str.replace(groupValue, engine.eval(detailValue) + "");
                    }
                }
            }
        } catch (Exception e) {
            log.info(".convertResult str入参:{}, params:{}, e:{}",str, params, e);
        }
        return str;
    }


 /*   public static void main(String[] args) {
        String str = "<p style=\"margin-left:21px;line-height:28px\">\n" +
                "    3、独家销售期限：自20&nbsp;&nbsp; 年&nbsp;&nbsp; 月&nbsp;&nbsp; 日起至20&nbsp;&nbsp; 年&nbsp;&nbsp; 月&nbsp;&nbsp; 日。\n" +
                "</p>\n" +
                "<p style=\"margin-left:21px;line-height:28px\">\n" +
                "    &nbsp;&nbsp; 如独家销售期限超出合同书的合同期限的，则合同书的合同期限顺延至独家销售期限截止日。前述顺延期间内各年度的销售折扣及业务推广费用标准等贸易条款，双方需按年度协商确定并重新签署合同书附件二，签署前，屈臣氏先按上一年度的标准收取，并在签署后按签署的当年度的贸易条款进行调整，多退少补。如双方未能对顺延期间内各年度的贸易条款达成一致，则屈臣氏可选择终止合同书及本协议。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>";
        System.out.println(clearStyle(str));
    }*/


    public static List<String> getTextParamsList(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        Pattern pt = Pattern.compile("[{ ][{]\\w{1,}[}][}]");
        Matcher mc = pt.matcher(text);
        while (mc.find()){
            String str = mc.group().replaceAll("[{|}]","");
            list.add(str);
        }
        return list;
    }
}
