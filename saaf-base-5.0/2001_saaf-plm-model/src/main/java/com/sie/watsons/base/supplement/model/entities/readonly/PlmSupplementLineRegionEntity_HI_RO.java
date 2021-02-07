package com.sie.watsons.base.supplement.model.entities.readonly;

/**
 * Created by Administrator on 2019/12/2/002.
 */
public class PlmSupplementLineRegionEntity_HI_RO {
    public static final String SQL = "select DISTINCT VS.area_en regionEnDesc from PLM_PRODUCT_SUPPLIERPLACEINFO pps \n" +
            "LEFT JOIN vmi_shop vs on PPS.location =VS.vs_code " +
            "where loc_type = 'S' \n" +
            "and VS.vh_pre_code is not null \n" +
            "and pps.status = '0'  \n"+
            "and rms_id = ':rmsId' \n"+
            "and LOWER(VS.area_en) like LOWER('%:regionEn%')  order by VS.area_en\n";

    private String regionEn;

    private String regionEnDesc;

    private String rms_id;

    public String getRegionEn() {
        if (regionEnDesc != null && !"".equals(regionEnDesc)) {
            if (regionEnDesc.equals("East Area")) {
                regionEn = "E";
            }
            if (regionEnDesc.equals("North Area")) {
                regionEn = "N";
            }
            if (regionEnDesc.equals("South Area")) {
                regionEn = "S";
            }
            if (regionEnDesc.equals("West Area")) {
                regionEn = "W";
            }
        }
        return regionEn;
    }

    public void setRegionEn(String regionEn) {
        this.regionEn = regionEn;
    }

    public String getRms_id() {
        return rms_id;
    }

    public void setRms_id(String rms_id) {
        this.rms_id = rms_id;
    }

    public String getRegionEnDesc() {
        return regionEnDesc;
    }

    public void setRegionEnDesc(String regionEnDesc) {
        this.regionEnDesc = regionEnDesc;
    }
}
