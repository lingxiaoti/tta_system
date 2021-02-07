package com.sie.watsons.base.api.model.contents;

public class fileTypeContents {

    //普通商品供应商文件类型
    public static final String[]  fileTypes_0_1 = {"营业执照","食品经营许可证","其他"};
    //普通商品生产厂家文件类型
    public static final String[]  fileTypes_0_2 = {"营业执照","化妆品生产许可证","食品生产许可证","消毒产品卫生许可证","农药生产许可证","其他"};
    //普通商品品牌文件类型
    public static final String[]  fileTypes_0_3 = {"商标注册证","授权文件","其他"};
    //普通商品QA产品文件类型
    public static final String[]  fileTypes_0_4 = {"国产产品检测报告","进口产品报关单","进口产品入境货物检验检疫证明"
            ,"国产非特殊用途化妆品备案凭证","国产特殊用途化妆品行政许可批件","进口非特殊用途化妆品备案凭证"
            ,"进口特殊用途化妆品行政许可批件","保健食品批准证书","保健食品备案凭证"
            ,"强制性产品认证证书","农药登记证","其他"};

    //OB所有商品供应商文件类型
    public static final String[]  obFileTypes_common_pass = {"第三方检验报告","PSI"};
    public static final String[]  obFileTypes_common_no_pass = {"卖点支持证据","AW签署","FM签署","PSI样品签署","TPC","其他"};

    //OB国产化妆品 通过与不通过
    public static final String[]  obFileTypes_domestic_cosmetics_pass = {"国产非特备案凭证或国产特妆批件","风险物质检测"};
    public static final String[]  obFileTypes_domestic_cosmetics_no_pass = {"卖点支持证据","TRA","稳定性测试","包材兼容性测试"
            ,"化妆品安全风险评估或毒理测试","防腐剂功效测试","敏感肌测试","皮肤斑贴测试","产品功效测试"};

    //OB进口化妆品 通过与不通过
    public static final String[]  obFileTypes_imported_cosmetics_pass = {"进口非特备案凭证或进口特妆批件","化妆品安全风险评估或毒理测试"
            ,"风险物质检测","首批报关单","首批CIQ报告"};
    public static final String[]  obFileTypes_imported_cosmetics_no_pass = {"成分表确认","TRA","稳定性测试","包材兼容性测试"
            ,"防腐剂功效测试","敏感肌测试","皮肤斑贴测试","产品功效测试"};

    //OB国产食品 通过与不通过
    public static final String[]  obFileTypes_domestic_food_pass = {"营养成分检测"};
    public static final String[]  obFileTypes_domestic_food_no_pass = {"成分表确认","稳定性测试"};

    //OB进口食品 通过与不通过
    public static final String[]  obFileTypes_imported_food_pass = {"营养成分检测","首批报关单","首批CIQ报告"};
    public static final String[]  obFileTypes_imported_food_no_pass = {"成分表确认","稳定性测试"};

    //OB牙膏 通过与不通过
    public static final String[]  obFileTypes_toothpaste_pass = {"风险物质检测","产品功效测试"};
    public static final String[]  obFileTypes_toothpaste_no_pass = {"成分表确认","TRA","稳定性测试","包材兼容性测试","防腐剂功效测试"};

    //OB医疗器械 通过与不通过
    public static final String[]  obFileTypes_medical_equipment_pass = {"第一类医疗器械备案凭证或二类三类医疗器械注册证"};
    public static final String[]  obFileTypes_medical_equipment_no_pass = {};

    //OB其他类型产品 通过与不通过
    public static final String[]  obFileTypes_others_pass = {"风险物质检测"};
    public static final String[]  obFileTypes_others_no_pass = {"敏感肌测试"};


}
