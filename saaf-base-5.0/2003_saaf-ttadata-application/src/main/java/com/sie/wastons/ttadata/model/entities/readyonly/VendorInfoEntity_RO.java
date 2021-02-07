package com.sie.wastons.ttadata.model.entities.readyonly;

import com.sie.wastons.sql.annotation.SqlBinder;
import lombok.Data;

@Data
public class VendorInfoEntity_RO {

    @SqlBinder(sqlColumn = "SUPPLIER_CODE",opreation = SqlBinder.OPR.START_WITH,orderBy = SqlBinder.SORT.ASC)
    private String vendorNbr;

    @SqlBinder(sqlColumn = "SUPPLIER_NAME",opreation = SqlBinder.OPR.CONTAIN)
    private String vendorName;

    @SqlBinder(sqlColumn = "STATUS")
    private String status;

}
