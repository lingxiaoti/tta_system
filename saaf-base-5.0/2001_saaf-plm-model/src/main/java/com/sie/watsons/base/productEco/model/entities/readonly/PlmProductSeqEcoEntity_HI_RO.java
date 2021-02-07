package com.sie.watsons.base.productEco.model.entities.readonly;

public class PlmProductSeqEcoEntity_HI_RO {

    public static final String QUERY_QAFILE = "select seq_plm_product_qa_file.nextval as SEQ  from dual";

    public static final String QUERY_PRICE= " select seq_plm_product_price.nextval as SEQ  from dual";

    public static final String QUERY_SUPPLIER = "select seq_plm_product_supplier_info.nextval as SEQ from dual";

    public static final String QUERY_PICFILE = " select seq_plm_product_picfile_table.nextval as SEQ from dual";

    private Integer seq;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
