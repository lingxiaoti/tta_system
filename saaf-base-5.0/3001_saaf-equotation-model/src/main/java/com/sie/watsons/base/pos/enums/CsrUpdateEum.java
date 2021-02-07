package com.sie.watsons.base.pos.enums;

public enum CsrUpdateEum {
    /**
     * csr评分CR1-CR7
     */
    CR1("CR1"),
    CR2("CR2"),
    CR3("CR3"),
    CR4("CR4"),
    CR5("CR5"),
    CR6("CR6"),
    CR7("CR7");
    private final String value;

    public String getValue() {
        return value;
    }

    CsrUpdateEum( String value) {
        this.value = value;
    }
}
