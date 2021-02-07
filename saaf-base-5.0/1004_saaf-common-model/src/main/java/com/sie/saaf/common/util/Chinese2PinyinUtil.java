package com.sie.saaf.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Chinese2PinyinUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(Chinese2PinyinUtil.class);
	/**
	 * 转换拼音，如果有多音字，则返回第一个
	 * @param msg 汉语
	 * @return 返回拼音
	 */
	public static String convertToPinyinSpell(String msg){
		return convertToPinYinString(msg, false);
	}
	
	/**
	 * 转换拼音首字母，如果有多音字，则返回第一个
	 * @param msg 汉语
	 * @return 返回拼音首字母
	 */
	public static String convertToFirstSpell(String msg){
		return convertToPinYinString(msg, true);
	}
	
	/**
     * 转换拼音
     * @param msg 需要转换的中文字符
     * @param simplePinYin 是否返回首字母
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    private static String convertToPinYinString(String msg,boolean simplePinYin){
    	StringBuffer sb = new StringBuffer(); //转换后的拼音字符串
        try {
        	if(msg==null){
        		return null;
			}
			char[] chars = msg.toCharArray();
			for (int i = 0; i < chars.length; i++) {
			    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(chars[i], getPinyinOutputFormat());
			    if (pinyinArr == null || pinyinArr.length == 0) {
			        sb.append(String.valueOf(chars[i])); //不是汉字，原样输出
			    } else {
			    	if(simplePinYin){
			    		sb.append(pinyinArr[0].charAt(0));
			    	}else{
			    		sb.append(pinyinArr[0]); //汉字，返回第一个拼音（多音字的情况）
			    	}
			    }
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			LOGGER.error(e.getMessage(),e);
		}
        return sb.toString();
    }
    
    /**
     * 拼音格式化
     * @return
     */
    private static HanyuPinyinOutputFormat getPinyinOutputFormat(){
		//拼音格式化
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		return format;
	}

}
