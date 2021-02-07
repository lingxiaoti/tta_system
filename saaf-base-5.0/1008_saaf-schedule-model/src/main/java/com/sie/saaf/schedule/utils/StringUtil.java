package com.sie.saaf.schedule.utils;

import com.sie.saaf.common.util.SaafDateUtils;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * @roseuid 3E4490BD00B3
	 */
	public StringUtil() {

	}

	/**
	 * encode the string to html string
	 *
	 * @param s
	 * @return java.lang.String
	 * @roseuid 3E446B6E025E
	 */
	public static String toHtml(String s) {
		if (isEmpty(s)) {
			return "";
		}
		char ac[] = new char[s.length()];
		s.getChars(0, s.length(), ac, 0);
		StringBuffer stringbuffer = new StringBuffer(ac.length + 50);
		for (int i = 0; i < ac.length; i++) {
			switch (ac[i]) {
			case 60: // '<'
				stringbuffer.append("&lt;");
				break;
			case 62: // '>'
				stringbuffer.append("&gt;");
				break;
			case 38: // '&'
				stringbuffer.append("&amp;");
				break;
			case 34: // '"'
				stringbuffer.append("&quot;");
				break;
			case 10: // '\n'
				stringbuffer.append("<br>");
				break;
			default:
				stringbuffer.append(ac[i]);
				break;
			}
		}
		return stringbuffer.toString();
	}

	/**
	 * Encode the string to html string, if the string is empty, return "&nbsp"
	 *
	 * @param
	 * @return java.lang.String
	 * @roseuid 3E446B8101E3
	 */
	public static String toHtmlWithNbsp(Object obj) {
		if (obj == null || isEmpty(obj.toString())) {
			return "&nbsp";
		}
		return toHtml(obj.toString());
	}

	/**
	 * Validate whether the string is empty
	 *
	 * @param s
	 * @return boolean
	 * @roseuid 3E446B850243
	 */
	public static boolean isEmpty(String s) {
		if (s == null || "".equals(s) || s.length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validate whether the string is empty
	 *
	 * @param s
	 * @return boolean
	 * @roseuid 3E446B850243
	 */
	public static boolean isEmpty(Object s) {
		if (s == null) {
			return true;
		} else {
			return isEmpty(s.toString());
		}
	}

	/**
	 * Encode the object to html string
	 *
	 * @param obj
	 * @return java.lang.String
	 * @roseuid 3E446C9A01D3
	 */
	public static String toHtml(Object obj) {
		if (obj == null) {
			return "";
		}
		return toHtml(obj.toString());
	}

	/**
	 * Encode the chinese String.
	 */
	public static String toChinese(String str) {
		try {
			if (isEmpty(str)) {
				return "";
			} else {
				return new String(str.getBytes("ISO8859_1"), "GBK");
				// return str;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String getRefresh() {
		java.util.Random rd = new java.util.Random();
		return (int) (rd.nextDouble() * 100000) + "";
	}

	public static String formatDouble(double d) {
		java.text.NumberFormat f = NumberFormat.getNumberInstance();
		return f.format(d);
	}

	public static String displayDouble(double d) {
		java.text.DecimalFormat f = new DecimalFormat("#.##");
		return f.format(d);
	}

	// ����ĸ��д
	public static String capitalize(String str) throws Exception {
		if (str == null || str.equals("")) {
			return "";
		}
		str = str.toLowerCase();
		str = str.substring(0, 1).toUpperCase() + str.substring(1);
		return str;
	}

	public static String obj2String(String str) {
		return (str == null) ? "" : str.trim();
	}

	public static int obj2Int(String str) {
		str = (str == null) ? "0" : str;
		str = (str.trim().equals("")) ? "0" : str;
		return Integer.parseInt(str);
	}

	public static double obj2Double(String str) {
		str = (str == null) ? "0" : str;
		str = (str.trim().equals("")) ? "0" : str;
		return Double.parseDouble(str);
	}

	public static String BlobToString(Blob blob) {

		String result = "";
		try {
			ByteArrayInputStream msgContent = (ByteArrayInputStream) blob.getBinaryStream();
			byte[] byte_data = new byte[msgContent.available()];
			msgContent.read(byte_data, 0, byte_data.length);
			result = new String(byte_data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * �Զ���0���� zsd
	 * 
	 * @param len
	 * @param object
	 * @param type
	 *            left ��0 right �Ҳ��ո�
	 * @return
	 */
	public static String getFixLenStr(int len, Object object, String type) {

		StringBuffer strbuffer = new StringBuffer();

		String objectstr = String.valueOf(object);
		String returnstr = "";
		if (("LEFT").equals(type)) {
			for (int i = 0; i < len; i++) {
				strbuffer.append("0");
			}
			returnstr = strbuffer.substring(0, len - objectstr.length()) + objectstr;
		} else if (("RIGHT").equals(type)) {
			for (int i = 0; i < len; i++) {
				strbuffer.append(" ");
			}
			returnstr = objectstr + strbuffer.substring(0, len - objectstr.length());
		} else if (("RIGHT0").equals(type)) {
			for (int i = 0; i < len; i++) {
				strbuffer.append("0");
			}
			returnstr = objectstr + strbuffer.substring(0, len - objectstr.length());
		}
		return returnstr;
	}

	/*
	 * 
	 * 0ת���ַ���ʾ��ҳ��
	 */

	public static String zeroTohtml(Object str) {
		if (str.equals("0") || str.equals("0.0") || str.equals("0.00")) {
			return "";
		} else {
			return str.toString();
		}
	}

	public static Double objNullToDouble(Double str) {
		str = (str == null || str.equals("null")) ? 0.00 : str;
		return str;
	}

	public static String replaceBlank(String str) {
		String returnVal = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			returnVal = m.replaceAll("");
		}
		return returnVal;
	}

/*	public static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}*/

	 public static boolean isNotBlank(Object str) {
    	 if (str == null) {
    		 return false;
    	 }
    	 return StringUtils.isNotBlank(str.toString()) && !"null".equals(str.toString().toLowerCase().trim());
    }
	 
	 public static boolean isBlank(Object str) {
    	 if (str == null || "null".equals((str + "").trim().toLowerCase()) || "".equals((str + "").trim())) {
    		 return true;
    	 }
    	 return false;
    }
	
	 /**
	  * 获取格式转化之后的内容
	  * @param formateContent 
	  * @param params 
	  * @return
	  */
	public static String getSendContent(String formateContent, Map<String, Object> params) {
		if (isBlank(formateContent)) {
			return formateContent;
		}
		Set<Entry<String, Object>> entrySet = params.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			formateContent = formateContent.replace(key, String.valueOf(value));
		}
		return formateContent;
	}
	
	/**
	 * 数组转str
	 * */
	public static String getParamValues (String args[]) {
		StringBuilder str = new StringBuilder();
		if(null == args || args.length <=0) {
			return str.toString();
		}
		for (int i = 0; i < args.length; i ++) {
			if (StringUtil.isBlank(args[i])) {
				continue ;
			}
			str.append(args[i]).append(",");
		}
		return str.deleteCharAt(str.length()-1).toString();
	}
	
	public static String listToString(List<String> list, String separator) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < list.size(); i++) {
	            sb.append("'"+list.get(i)+"'").append(separator);
	        }
	        return sb.toString().substring(0, sb.toString().length() - 1);
	    }


	/**
	 * 
	 * 功能说明：获取32位的uuid
	 * @createTime 2018年7月27日 上午10:26:23
	 * @author liuli
	 * @return
	 */
	public static String getUUID() {
		String uuidString = UUID.randomUUID().toString();
		uuidString = uuidString.replaceAll("-","");
		return uuidString;
	}
	
	/**
	 * 判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches() ){
            return false;
        }
        return true;
    }
	
	//获取8位递增数
	public static String getAutoNumber(int number,String type){
		String code = "0000000"+number;
		if(number>=10 && number <100){
			code = "000000"+number;
		}else if(number >=100 && number <1000){
			code = "00000"+number;
		}else if(number>=1000 && number<10000){
			code = "0000"+number;
		}else if(number>=10000 && number<100000){
			code = "000"+number;
		}else if(number>=100000 && number < 1000000) {
			code = "00"+number;
		}
		return type+code;
   }

	public static String getBasePath(String fixFileName) {
		String basePath = ResourceUtils.getBasePath() + "/" + fixFileName + SaafDateUtils.convertDateToString(new Date(), "yyyyMMdd") + ".txt";
		return basePath;
	}

	//动态字段拼接
	public static String getFlieds(List<String> fliedList) {
		if(fliedList == null || fliedList.isEmpty()) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		fliedList.forEach(item->{
			buffer.append("\t").append(item.trim()).append(",\n");
		});
		return buffer.substring(0, buffer.lastIndexOf(",\n")) + "\n";
	}

	public static String getFlieds(List<String> fliedList, String prefixStr, String suffixStr) {
		if(fliedList == null || fliedList.isEmpty()) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		fliedList.forEach(item->{
			buffer.append("\t").append(prefixStr).append(item.trim()).append(suffixStr).append(" as ").append(item.trim()).append(",\n");
		});
		return buffer.substring(0, buffer.lastIndexOf(",\n")) + "\n";
	}


	//String flieds = getFlieds(fliedList, "nvl(t1.", ",0)-nvl(t2.", ",0)");
	public static String getFlieds(List<String> fliedList, String prefixStr, String midStr, String suffixStr) {
		if(fliedList == null || fliedList.isEmpty()) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		fliedList.forEach(item->{
			buffer.append("\t").append(prefixStr).append(item.trim()).append(midStr).append(item.trim()).append(suffixStr).append(" as ").append(item.trim()).append(",\n");
		});
		return buffer.substring(0, buffer.lastIndexOf(",\n")) + "\n";
	}
}