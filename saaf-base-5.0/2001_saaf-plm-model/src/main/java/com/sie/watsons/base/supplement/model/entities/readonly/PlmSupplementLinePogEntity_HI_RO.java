package com.sie.watsons.base.supplement.model.entities.readonly;

/**
 * Created by Administrator on 2019/12/6/006.
 */
public class PlmSupplementLinePogEntity_HI_RO {
    public static final String SQL = "SELECT \n" +
            "\tvp.POG_CODE AS pogCode\n" +
            "FROM\n" +
            "\tVMI_POG vp\n" +
            "WHERE 1=1 " +
            "and rms_id = ':rmsId' \n" +
            " order by vp.POG_CODE ";

    private String rms_id;
    private String pogCode;

    public String getRms_id() {
        return rms_id;
    }

    public void setRms_id(String rms_id) {
        this.rms_id = rms_id;
    }

    public String getPogCode() {
        return pogCode;
    }

    public void setPogCode(String pogCode) {
        this.pogCode = pogCode;
    }
}
