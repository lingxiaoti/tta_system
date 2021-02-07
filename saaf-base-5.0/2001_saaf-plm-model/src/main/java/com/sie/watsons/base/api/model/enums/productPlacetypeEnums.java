package com.sie.watsons.base.api.model.enums;

public enum productPlacetypeEnums {

    PLM_PRODUCT_PLACETYPE_1("1","全部仓+店"),

    PLM_PRODUCT_PLACETYPE_2("2","全部店铺"),

    PLM_PRODUCT_PLACETYPE_3("3","地点清单"),

    PLM_PRODUCT_PLACETYPE_4("4","指定仓+店"),

    PLM_PRODUCT_PLACETYPE_5("5","区域"),

    PLM_PRODUCT_PLACETYPE_6("6","指定仓"),

    PLM_PRODUCT_PLACETYPE_7("7","指定店铺"),

    PLM_PRODUCT_PLACETYPE_8("8","指定仓+地点清单"),

    PLM_PRODUCT_PLACETYPE_9("9","仓库及其店铺");

    private String values;
    private String meaning;

    productPlacetypeEnums(String values, String meaning) {
        this.values = values;
        this.meaning = meaning;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
