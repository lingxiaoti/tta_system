package com.sie.watsons.base.report.model.entities.readonly;


/**
 * TtaIrTermsEntity_HI_RO Entity Object
 * Wed Jul 31 16:23:52 CST 2019  Auto Generate
 */

public class TtaIrTermsEntity_HI_RO {

    private Integer proposalId;
    private String orderNbr;
    private String proposalYear;
    private String vendorNbr;
    private String vendorName;
    private String majorDeptCode;
    private String deptDesc;
    private String brandCn;

    private String firstExcludeTax;
    private String firstIncludeTax;
    private String firstRate;
    private String secondExcludeTax;
    private String secondIncludeTax;
    private String secondRate;
    private String thirdExcludeTax;
    private String thirdIncludeTax;
    private String thirdRate;
    private String fourthExcludeTax;
    private String fourthIncludeTax;
    private String fourthRate;
    private String fifthExcludeTax;
    private String fifthIncludeTax;
    private String fifthRate;
    private String sixthExcludeTax;
    private String sixthIncludeTax;
    private String sixthRate;
    private String seventhExcludeTax;
    private String seventhIncludeTax;
    private String seventhRate;
    private String eighthExcludeTax;
    private String eighthIncludeTax;
    private String eighthRate;
    private Integer operatorUserId;
    private String netpurchase;//折扣前采购额（含税）
    private String predictNetpurchase; //预估全年折扣前采购额（含税） 本年度实际采购额(netpurchase)/月份*12
    private String irPredictRate; //预计IR%;
    private String passFirst;//是否已达第一层;
    private String passList;//是否已达最后一层
    private String irPredictAmount;//预计IR$(含税）
    private String nextIrRate; //下一层目标的IR%
    private String nextPurchaseAmount; //为达到下一层目标需追加的采购额 （含税）
    private String addAffterIrIncludeTaxAmount;//追加后的IR$（含税）
    private String addIrIncludeTaxAmount;//IR 增加额（含税）
    private String returnRate; //追加IR占追加采购金额比例(投资回报率)


    public static String getQuerySql() {
        String sql = "select *  from (" +
                "  select \n" +
                "       phv.proposal_id,\n" +
                "       phv.order_nbr,\n" +
                "       phv.proposal_year,\n" +
                "       nvl(ts.formal_code, phv.vendor_nbr) as vendor_nbr,\n" +
                "       nvl(ts.formal_name, phv.vendor_name) as vendor_name,\n" +
                "       phv.major_dept_code,\n" +
                "       nvl(thv.dept_desc, phv.dept_desc1) as dept_desc,\n" +
                "       nvl(thv.dept_code, phv.dept_code1) as dept_code,\n" +
                "       thv.brand_cn,\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第一层',1,phv.major_dept_code, 0) as first_exclude_tax, --第一层 （折扣前）不含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第一层',2,phv.major_dept_code, 0) as first_include_tax, --第一层 （折扣前）含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第一层',3,phv.major_dept_code, 0) as  first_rate,--第一层 佣金比例\n" +
                "       \n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第二层',1,phv.major_dept_code, 0) as second_exclude_tax, --第二层 （折扣前）不含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第二层',2,phv.major_dept_code, 0) as second_include_tax, --第二层 （折扣前）含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第二层',3,phv.major_dept_code, 0) as  second_rate,--第二层 佣金比例\n" +
                "       \n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第三层',1,phv.major_dept_code, 0) as third_exclude_tax, --第三层 （折扣前）不含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第三层',2,phv.major_dept_code, 0) as third_include_tax, --第三层 （折扣前）含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第三层',3,phv.major_dept_code, 0) as  third_rate,--第三层 佣金比例\n" +
                "\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第四层',1,phv.major_dept_code, 0) as fourth_exclude_tax, --第四层 （折扣前）不含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第四层',2,phv.major_dept_code, 0) as fourth_include_tax, --第四层 （折扣前）含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第四层',3,phv.major_dept_code, 0) as  fourth_rate,--第四层 佣金比例\n" +
                "      \n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第五层',1,phv.major_dept_code, 0) as fifth_exclude_tax, --第五层 （折扣前）不含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第五层',2,phv.major_dept_code, 0) as fifth_include_tax, --第五层 （折扣前）含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第五层',3,phv.major_dept_code, 0) as  fifth_rate,-- 第五层 佣金比例\n" +
                "       \n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第六层',1,phv.major_dept_code, 0) as sixth_exclude_tax, --第六层 （折扣前）不含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第六层',2,phv.major_dept_code, 0) as sixth_include_tax, --第六层 （折扣前）含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第六层',3,phv.major_dept_code, 0) as  sixth_rate,--第六层 佣金比例\n" +
                "       \n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第七层',1,phv.major_dept_code, 0) as seventh_exclude_tax, --第七层 （折扣前）不含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第七层',2,phv.major_dept_code, 0) as seventh_include_tax, --第七层 （折扣前）含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第七层',3,phv.major_dept_code, 0) as  seventh_rate,--第七层 佣金比例  \n" +
                "       \n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第八层',1,phv.major_dept_code, 0) as eighth_exclude_tax, --第八层 （折扣前）不含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第八层',2,phv.major_dept_code, 0) as eighth_include_tax, --第八层 （折扣前）含税采购额\n" +
                "       tta_fun_level_tta_value(phv.proposal_id, phv.proposal_year,'第八层',3,phv.major_dept_code, 0) as  eighth_rate --第八层 佣金比例      \n" +
               // "  from tta_proposal_header_v phv\n" +
                " from tta_proposal_header_new_version_v phv \n" +
                " inner join tta_proposal_terms_headerold_v thv\n" +
                "    on phv.proposal_id = thv.proposal_id\n" +
                "   left join tta_supplier ts \n" +
                "   on ts.supplier_code = phv.vendor_nbr \n" +
                " where phv.proposal_year=to_char(sysdate,'yyyy') ) tb where nvl(tb.first_rate,'-1') != '-1'";
        return sql;
    }

    /**
     * 功能描述： 通过供应商编码及用户选择的结束日期获取-折扣前采购额（含税）
     */
    public static final String getNetpurchaseSql(String vendorNbr, String endDate) {
        String sql= " select  sum(t.netpurchase) as NETPURCHASE\n" +
                "   from tta_oi_summary_line t\n" +
                "  where to_char(t.account_month, 'yyyyMM') >= to_char(sysdate,'yyyy')||'01'\n" +
                "    and to_char(t.account_month, 'yyyyMM') <='" + endDate + "'\n" +
                "    and exists (\n" +
                "        select 1 from  (\n" +
                "          select trs.rel_supplier_code as supplier_code\n" +
                "           from tta_supplier ts\n" +
                "           left join tta_rel_supplier trs\n" +
                "             on ts.supplier_id = trs.rel_id\n" +
                "          where ts.supplier_code ='" + vendorNbr + "'" +
                "          union  \n" +
                "          select '" + vendorNbr + "' from dual\n" +
                "          ) rsp where rsp.supplier_code= t.rms_code)";
        return sql;
    }


    public static final String getMaxMonthFlagSql() {
        String sql = "select max(to_char(t.account_month, 'yyyyMM')) as \"maxMonth\" from tta_oi_summary_line t ";
        return sql;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }


    public Integer getProposalId() {
        return proposalId;
    }

    public void setOrderNbr(String orderNbr) {
        this.orderNbr = orderNbr;
    }


    public String getOrderNbr() {
        return orderNbr;
    }

    public void setProposalYear(String proposalYear) {
        this.proposalYear = proposalYear;
    }


    public String getProposalYear() {
        return proposalYear;
    }

    public void setVendorNbr(String vendorNbr) {
        this.vendorNbr = vendorNbr;
    }


    public String getVendorNbr() {
        return vendorNbr;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }


    public String getVendorName() {
        return vendorName;
    }

    public void setMajorDeptCode(String majorDeptCode) {
        this.majorDeptCode = majorDeptCode;
    }


    public String getMajorDeptCode() {
        return majorDeptCode;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }


    public String getDeptDesc() {
        return deptDesc;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }


    public String getBrandCn() {
        return brandCn;
    }

    public void setFirstExcludeTax(String firstExcludeTax) {
        this.firstExcludeTax = firstExcludeTax;
    }


    public String getFirstExcludeTax() {
        return firstExcludeTax;
    }

    public void setFirstIncludeTax(String firstIncludeTax) {
        this.firstIncludeTax = firstIncludeTax;
    }


    public String getFirstIncludeTax() {
        return firstIncludeTax;
    }

    public void setFirstRate(String firstRate) {
        this.firstRate = firstRate;
    }


    public String getFirstRate() {
        return firstRate;
    }

    public void setSecondExcludeTax(String secondExcludeTax) {
        this.secondExcludeTax = secondExcludeTax;
    }


    public String getSecondExcludeTax() {
        return secondExcludeTax;
    }

    public void setSecondIncludeTax(String secondIncludeTax) {
        this.secondIncludeTax = secondIncludeTax;
    }


    public String getSecondIncludeTax() {
        return secondIncludeTax;
    }

    public void setSecondRate(String secondRate) {
        this.secondRate = secondRate;
    }


    public String getSecondRate() {
        return secondRate;
    }

    public void setThirdExcludeTax(String thirdExcludeTax) {
        this.thirdExcludeTax = thirdExcludeTax;
    }


    public String getThirdExcludeTax() {
        return thirdExcludeTax;
    }

    public void setThirdIncludeTax(String thirdIncludeTax) {
        this.thirdIncludeTax = thirdIncludeTax;
    }


    public String getThirdIncludeTax() {
        return thirdIncludeTax;
    }

    public void setThirdRate(String thirdRate) {
        this.thirdRate = thirdRate;
    }


    public String getThirdRate() {
        return thirdRate;
    }

    public void setFourthExcludeTax(String fourthExcludeTax) {
        this.fourthExcludeTax = fourthExcludeTax;
    }


    public String getFourthExcludeTax() {
        return fourthExcludeTax;
    }

    public void setFourthIncludeTax(String fourthIncludeTax) {
        this.fourthIncludeTax = fourthIncludeTax;
    }


    public String getFourthIncludeTax() {
        return fourthIncludeTax;
    }

    public void setFourthRate(String fourthRate) {
        this.fourthRate = fourthRate;
    }


    public String getFourthRate() {
        return fourthRate;
    }

    public void setFifthExcludeTax(String fifthExcludeTax) {
        this.fifthExcludeTax = fifthExcludeTax;
    }


    public String getFifthExcludeTax() {
        return fifthExcludeTax;
    }

    public void setFifthIncludeTax(String fifthIncludeTax) {
        this.fifthIncludeTax = fifthIncludeTax;
    }


    public String getFifthIncludeTax() {
        return fifthIncludeTax;
    }

    public void setFifthRate(String fifthRate) {
        this.fifthRate = fifthRate;
    }


    public String getFifthRate() {
        return fifthRate;
    }

    public void setSixthExcludeTax(String sixthExcludeTax) {
        this.sixthExcludeTax = sixthExcludeTax;
    }


    public String getSixthExcludeTax() {
        return sixthExcludeTax;
    }

    public void setSixthIncludeTax(String sixthIncludeTax) {
        this.sixthIncludeTax = sixthIncludeTax;
    }


    public String getSixthIncludeTax() {
        return sixthIncludeTax;
    }

    public void setSixthRate(String sixthRate) {
        this.sixthRate = sixthRate;
    }


    public String getSixthRate() {
        return sixthRate;
    }

    public void setSeventhExcludeTax(String seventhExcludeTax) {
        this.seventhExcludeTax = seventhExcludeTax;
    }


    public String getSeventhExcludeTax() {
        return seventhExcludeTax;
    }

    public void setSeventhIncludeTax(String seventhIncludeTax) {
        this.seventhIncludeTax = seventhIncludeTax;
    }


    public String getSeventhIncludeTax() {
        return seventhIncludeTax;
    }

    public void setSeventhRate(String seventhRate) {
        this.seventhRate = seventhRate;
    }


    public String getSeventhRate() {
        return seventhRate;
    }

    public void setEighthExcludeTax(String eighthExcludeTax) {
        this.eighthExcludeTax = eighthExcludeTax;
    }


    public String getEighthExcludeTax() {
        return eighthExcludeTax;
    }

    public void setEighthIncludeTax(String eighthIncludeTax) {
        this.eighthIncludeTax = eighthIncludeTax;
    }


    public String getEighthIncludeTax() {
        return eighthIncludeTax;
    }

    public void setEighthRate(String eighthRate) {
        this.eighthRate = eighthRate;
    }


    public String getEighthRate() {
        return eighthRate;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setNetpurchase(String netpurchase) {
        this.netpurchase = netpurchase;
    }

    public String getNetpurchase() {
        return netpurchase;
    }

    public String getPredictNetpurchase() {
        return predictNetpurchase;
    }

    public void setPredictNetpurchase(String predictNetpurchase) {
        this.predictNetpurchase = predictNetpurchase;
    }

    public String getIrPredictRate() {
        return irPredictRate;
    }

    public void setIrPredictRate(String irPredictRate) {
        this.irPredictRate = irPredictRate;
    }

    public String getPassFirst() {
        return passFirst;
    }

    public void setPassFirst(String passFirst) {
        this.passFirst = passFirst;
    }

    public String getPassList() {
        return passList;
    }

    public void setPassList(String passList) {
        this.passList = passList;
    }

    public String getIrPredictAmount() {
        return irPredictAmount;
    }

    public void setIrPredictAmount(String irPredictAmount) {
        this.irPredictAmount = irPredictAmount;
    }

    public String getNextIrRate() {
        return nextIrRate;
    }

    public void setNextIrRate(String nextIrRate) {
        this.nextIrRate = nextIrRate;
    }

    public String getNextPurchaseAmount() {
        return nextPurchaseAmount;
    }

    public void setNextPurchaseAmount(String nextPurchaseAmount) {
        this.nextPurchaseAmount = nextPurchaseAmount;
    }

    public String getAddAffterIrIncludeTaxAmount() {
        return addAffterIrIncludeTaxAmount;
    }

    public void setAddAffterIrIncludeTaxAmount(String addAffterIrIncludeTaxAmount) {
        this.addAffterIrIncludeTaxAmount = addAffterIrIncludeTaxAmount;
    }

    public String getAddIrIncludeTaxAmount() {
        return addIrIncludeTaxAmount;
    }

    public void setAddIrIncludeTaxAmount(String addIrIncludeTaxAmount) {
        this.addIrIncludeTaxAmount = addIrIncludeTaxAmount;
    }

    public String getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(String returnRate) {
        this.returnRate = returnRate;
    }
}
