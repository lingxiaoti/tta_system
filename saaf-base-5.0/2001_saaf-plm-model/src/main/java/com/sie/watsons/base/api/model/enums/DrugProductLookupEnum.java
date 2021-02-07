package com.sie.watsons.base.api.model.enums;

import com.sie.saaf.common.model.enums.lookupCodeValusEnum;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;

public class DrugProductLookupEnum {

    /**
     * 定义 药品-剂型-数据字典 的枚举
     */
    public  enum DosageForm {
        DF_01("01", "鼻用制剂"),
        DF_02("02", "茶剂"),
        DF_03("03", "搽剂"),
        DF_04("04", "滴剂"),
        DF_05("05", "滴丸剂"),
        DF_06("06", "滴眼剂"),
        DF_07("07", "酊剂"),
        DF_08("08", "耳用制剂"),
        DF_09("09", "干混悬剂"),
        DF_10("10", "膏剂"),
        DF_100("100", "吸入用溶液剂"),
        DF_101("101", "合剂(无蔗糖)"),
        DF_102("102", "丸剂（浓缩水丸）"),
        DF_103("103", "片剂（控释）"),
        DF_104("104", "片剂(分散片)"),
        DF_105("105", "片剂（肠溶）"),
        DF_11("106", "缓释片剂"),
        DF_12("11", "灌肠剂"),
        DF_13("12", "合剂"),
        DF_14("13", "缓释制剂"),
        DF_15("14", "煎膏剂"),
        DF_16("15", "胶囊剂"),
        DF_17("16", "酒剂"),
        DF_18("17", "颗粒剂"),
        DF_19("18", "露剂"),
        DF_20("19", "膜剂"),
        DF_21("20", "凝胶剂"),
        DF_22("21", "喷雾剂"),
        DF_23("22", "片剂"),
        DF_24("23", "气雾剂"),
        DF_25("24", "溶液剂"),
        DF_26("25", "乳膏剂"),
        DF_27("26", "软膏剂"),
        DF_28("27", "散剂"),
        DF_29("28", "栓剂"),
        DF_30("29", "糖浆剂"),
        DF_31("30", "贴膏剂"),
        DF_32("31", "贴剂"),
        DF_33("32", "外用搽剂"),
        DF_34("33", "丸剂"),
        DF_35("34", "吸入剂"),
        DF_36("35", "洗剂"),
        DF_37("36", "橡胶膏剂"),
        DF_38("37", "熏剂"),
        DF_39("38", "眼用制剂"),
        DF_40("39", "饮片"),
        DF_41("40", "油剂"),
        DF_42("41", "颗粒"),
        DF_43("42", "胶囊剂(软胶囊)"),
        DF_44("43", "丸剂（大蜜丸）"),
        DF_45("44", "丸剂(水蜜丸)"),
        DF_46("45", "片剂(糖衣)"),
        DF_47("46", "橡膏剂"),
        DF_48("47", "丸剂(水丸)"),
        DF_49("48", "缓释胶囊"),
        DF_50("49", "煎膏剂(膏滋)"),
        DF_51("50", "片剂(薄膜衣)"),
        DF_52("51", "每袋装5g"),
        DF_53("52", "丸剂(大蜜丸)"),
        DF_54("53", "剂（咀嚼）"),
        DF_55("54", "贴膏剂(橡胶膏剂)"),
        DF_56("55", "贴脐片剂"),
        DF_57("56", "丸剂(浓缩丸)"),
        DF_58("57", "片剂(素片、糖衣片)"),
        DF_59("58", "锭剂"),
        DF_60("59", "口服溶液剂"),
        DF_61("60", "气雾剂、喷雾剂"),
        DF_62("61", "外用药油"),
        DF_63("62", "片剂(咀嚼)"),
        DF_64("63", "口服混悬剂"),
        DF_65("64", "服溶液剂"),
        DF_66("65", "胶剂"),
        DF_67("66", "软胶囊"),
        DF_68("67", "口服液剂"),
        DF_69("69", "滴眼液"),
        DF_70("70", "口服混悬液"),
        DF_71("71", "混悬滴剂"),
        DF_72("72", "乳胶剂"),
        DF_73("73", "贴敷剂"),
        DF_74("74", "片剂(肠溶)"),
        DF_75("75", "丸剂（小蜜丸）"),
        DF_76("76", "胶囊剂(硬胶囊)"),
        DF_77("77", "胶囊剂（肠溶）"),
        DF_78("78", "片剂(薄膜衣片)"),
        DF_79("79", "合剂（无糖型）"),
        DF_80("80", "吸入粉雾剂"),
        DF_81("81", "片剂(缓释)"),
        DF_82("82", "粉剂"),
        DF_83("83", "软胶囊剂"),
        DF_84("84", "片剂(糖衣,薄膜衣)"),
        DF_85("85", "片剂(糖衣片)"),
        DF_86("86", "胶囊剂(肠溶胶囊)"),
        DF_87("87", "胶囊型"),
        DF_88("88", "口服液"),
        DF_89("89", "眼膏剂"),
        DF_90("90", "溶液剂(含甘油)"),
        DF_91("91", "片剂(肠溶片)"),
        DF_92("92", "片剂(肠溶薄膜衣)"),
        DF_93("93", "眼用制剂(滴眼剂)"),
        DF_94("94", "片剂(薄膜衣或糖衣)"),
        DF_95("95", "片剂(糖衣;薄膜衣)"),
        DF_96("96", "丸剂(浓缩水丸)"),
        DF_97("97", "干粉吸入剂"),
        DF_98("98", "片剂(素片,薄膜衣)"),
        DF_99("99", "混悬剂");
        String code;
        String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        DosageForm(String code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    ;

    //药品单位
    public enum DrugUnit {
        DF_01("1", "支"),
        DF_02("2", "瓶"),
        DF_03("3", "盒"),
        DF_04("4", "袋"),
        DF_05("5", "罐"),
        DF_06("6", "包");
        String code;
        String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        DrugUnit(String code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    ;

    //储藏条件
    public enum DrugStore {
        DF_01("1", "常温"),
        DF_02("2", "阴凉");
        String code;
        String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        DrugStore(String code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    ;

    //经营类别
    public enum BusinessCategory {
        DF_01("1", "零售"),
        DF_02("2", "批发");
        String code;
        String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        BusinessCategory(String code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    ;

    //处方药类别
    public enum DrugTypeCode {
        DF_01("1", "单轨"),
        DF_02("2", "双轨");
        String code;
        String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        DrugTypeCode(String code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    ;

    //药品属性 gps管理级别
    public enum Gps {
        DF_01("1", "内服"),
        DF_02("2", "外用"),
        DF_03("3", "特殊管理");
        String code;
        String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        Gps(String code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    ;




}
