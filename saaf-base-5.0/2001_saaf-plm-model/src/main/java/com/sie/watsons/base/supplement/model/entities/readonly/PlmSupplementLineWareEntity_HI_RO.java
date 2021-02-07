package com.sie.watsons.base.supplement.model.entities.readonly;

/**
 * Created by Administrator on 2019/12/2/002.
 */
public class PlmSupplementLineWareEntity_HI_RO {
    public static final String SQL = "select DISTINCT VS.vh_pre_code wareCode from PLM_PRODUCT_SUPPLIERPLACEINFO pps  \n" +
            "LEFT JOIN vmi_shop vs on PPS.location =VS.vs_code  \n " +
            "where pps.loc_type = 'S' \n" +
            "and VS.vh_pre_code is not null \n" +
            "and pps.status = '0'  \n"+
            "and rms_id = ':rmsId' \n"+
            "and LOWER(vh_pre_code) like LOWER('%:wareCode%')  " +
            "order by  VS.vh_pre_code \n";

    private String wareCode;
    private String rms_id;

    public String getRms_id() {
        return rms_id;
    }

    public void setRms_id(String rms_id) {
        this.rms_id = rms_id;
    }

    public String getWareCode() {
        return wareCode;
    }

    public void setWareCode(String wareCode) {
        this.wareCode = wareCode;
    }
}
