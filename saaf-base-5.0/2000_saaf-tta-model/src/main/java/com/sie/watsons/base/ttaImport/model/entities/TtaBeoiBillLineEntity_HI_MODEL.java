package com.sie.watsons.base.ttaImport.model.entities;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaBeoiBillLineEntity_HI Entity Object
 * Fri Oct 18 18:05:16 CST 2019  Auto Generate
 */
public class TtaBeoiBillLineEntity_HI_MODEL {

    private Integer ttaBeoiBillImportId;
    @ExcelProperty(value ="年月")
    private Date accountMonth;
    @ExcelProperty(value ="供应商编号")
    private String rmsCode;
    @ExcelProperty(value ="供应商名称")
    private String venderName;
    @ExcelProperty(value ="JV")
    private String bookInJv;
    @ExcelProperty(value ="供应商编号+JV")
    private String bookInJv1;
    @ExcelProperty(value ="采购")
    private String buyer;
    @ExcelProperty(value ="TC采购总监")
    private String tc;
    @ExcelProperty(value ="部门")
    private String department;
    @ExcelProperty(value ="品牌")
    private String brand;
    @ExcelProperty(value ="供应商属性")
    private String venderab;
    @ExcelProperty(value ="合同编号")
    private String userContractId;
    @ExcelProperty(value ="供应商类型")
    private String cooperateStatus;
    @ExcelProperty(value ="合作状态")
    private String venderType;
    @ExcelProperty(value ="计生供应商")
    private String familyPlaningFlag;
    @ExcelProperty(value ="合同有效期开始日期")
    private Date validBeginDate;
    @ExcelProperty(value ="合同有效期结束日期")
    private Date validEndDate;
    @ExcelProperty(value ="发票折扣比例")
    private String pickUpRate;
    @ExcelProperty(value ="发票折扣比例是否含税")
    private String venderContainTax;
    @ExcelProperty(value ="采购额（折扣前）")
    private String purchase;
    @ExcelProperty(value ="退货额（折扣前）")
    private String goodsReturn;
    @ExcelProperty(value ="净采购额（折扣前）")
    private String netPur;
    @ExcelProperty(value ="免送额（折扣前）")
    private String dsd;
    @ExcelProperty(value ="折扣后进货额")
    private String purchaseOrigin;
    @ExcelProperty(value ="折扣后退货额")
    private String goodsReturnOrigin;
    @ExcelProperty(value ="折扣后净采购额")
    private String netPurOrigin;
    @ExcelProperty(value ="以前年度的进货额")
    private String pyPurchase;
    @ExcelProperty(value ="以前年度的退货额")
    private String pyGoodsReturn;
    @ExcelProperty(value ="以前年度的净采购额(S+T)")
    private String pyNetPur;
    @ExcelProperty(value ="以前年度的DSD(免送额)")
    private String pydsd;
    @ExcelProperty(value ="本月采购额计算")
    private String monthPur;
    @ExcelProperty(value ="本月计算")
    private String monthCount;
    @ExcelProperty(value ="本月计算-ABI(TTA)")
    private String monthAbi;
    @ExcelProperty(value ="本月计算-ABI(On top)")
    private String monthAbiOntop;
    @ExcelProperty(value ="以前年度计算-ABI(TTA)")
    private String yearAbi;
    @ExcelProperty(value ="以前年度计算-ABI(On top)")
    private String yearAbiOntop;
    @ExcelProperty(value ="合同调整")
    private String contractAdj;
    @ExcelProperty(value ="对帐调整")
    private String billAdj;
    @ExcelProperty(value ="以前年度合同调整")
    private String yearContractAdj;
    @ExcelProperty(value ="以前年度对帐调整")
    private String yearBillAdj;
    @ExcelProperty(value ="以前年度采购额计算/PRG补收/自查补收")
    private String yearPurchaseCount;
    @ExcelProperty(value ="AP OI Total(含税)")
    private String apOiTotalTax;
    @ExcelProperty(value ="AP OI Total(不含税-16%VAT)")
    private String apOiTotalNotax;
    @ExcelProperty(value ="税金(16% VAT)")
    private String tax16;
    @ExcelProperty(value ="AP OI Total(不含税-13%VAT)")
    private String noTax13;
    @ExcelProperty(value ="税金(13% VAT)")
    private String tax13;
    @ExcelProperty(value ="AP OI Total(不含税-10%VAT)")
    private String noTax10;
    @ExcelProperty(value ="税金(10% VAT)")
    private String tax10;
    @ExcelProperty(value ="条款-一般购货折扣")
    private String purDisctContractText;
    @ExcelProperty(value ="条款（%）- 一般购货折扣")
    private String purDisctContractValue;
    @ExcelProperty(value ="本月采购额计算- 一般购货折扣")
    private String purDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- 一般购货折扣")
    private String purDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- 一般购货折扣")
    private String purDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- 一般购货折扣")
    private String purDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- 一般购货折扣")
    private String purDisctLastyearRecAdj;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整- 一般购货折扣")
    private String purDisctLastyearAudAdj;
    @ExcelProperty(value ="备注 一般购货折扣")
    private String purDisctMemo;
    @ExcelProperty(value ="一般购货折扣Total")
    private String purDisctIncludeTax;
    @ExcelProperty(value ="一般购货折扣Total(不含税)")
    private String purDisctIncludeNotax;
    @ExcelProperty(value ="条款- （提前付款）购货折扣")
    private String earlypayDisctContractText;
    @ExcelProperty(value ="条款（%）- （提前付款）购货折扣")
    private String earlypayDisctContractValue;
    @ExcelProperty(value ="本月采购额计算- （提前付款）购货折扣")
    private String earlypayDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- （提前付款）购货折扣")
    private String earlypayDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- （提前付款）购货折扣")
    private String earlypayDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- （提前付款）购货折扣")
    private String earlypayDisLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- （提前付款）购货折扣")
    private String earlypayDisLastyearRecAdj;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整- （提前付款）购货折扣")
    private String earlypayDisLastyearAudAdj;
    @ExcelProperty(value ="备注- （提前付款）购货折扣")
    private String earlypayDisctMemo;
    @ExcelProperty(value ="（提前付款）购货折扣Total")
    private String earlypayDisctIncludeTax;
    @ExcelProperty(value ="（提前付款）购货折扣Total(不含税)")
    private String earlypayDisctIncludeNotax;
    @ExcelProperty(value ="条款- 促销折扣")
    private String promotDisctContractText;
    @ExcelProperty(value ="条款（%）- 促销折扣")
    private String promotDisctContractValue;
    @ExcelProperty(value ="本月采购额计算- 促销折扣")
    private String promotDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- 促销折扣")
    private String promotDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- 促销折扣")
    private String promotDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- 促销折扣")
    private String promotDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- 促销折扣")
    private String promotDisctLastyearRecAdj;
    @ExcelProperty(value ="以前年度采购额计算/PRG&调整/自查调整- 促销折扣")
    private String promotDisctLastyearAudAdj;
    @ExcelProperty(value ="备注- 促销折扣")
    private String promotDisctMemo;
    @ExcelProperty(value ="促销折扣Total")
    private String promotDisctIncludeTax;
    @ExcelProperty(value ="促销折扣Total(不含税)")
    private String promotDisctIncludeNotax;
    @ExcelProperty(value ="条款- 集中收货折扣")
    private String distriDisctContractText;
    @ExcelProperty(value ="条款（%）- 集中收货折扣")
    private String distriDisctContractValue;
    @ExcelProperty(value ="本月采购额计算- 集中收货折扣")
    private String distriDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- 集中收货折扣")
    private String distriDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- 集中收货折扣")
    private String distriDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- 集中收货折扣")
    private String distriDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- 集中收货折扣")
    private String distriDisctLastyearRecAdj;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整- 集中收货折扣")
    private String distriDisctLastyearAudAdj;
    @ExcelProperty(value ="备注- 集中收货折扣")
    private String distriDisctMemo;
    @ExcelProperty(value ="集中收货折扣Total")
    private String distriDisctIncludeTax;
    @ExcelProperty(value ="集中收货折扣Total(不含税)")
    private String distriDisctIncludeNotax;
    @ExcelProperty(value ="条款- （残损）购货折扣")
    private String dgdsDisctContractText;
    @ExcelProperty(value ="条款（%）- （残损）购货折扣")
    private String dgdsDisctContractValue;
    @ExcelProperty(value ="本月采购额计算- （残损）购货折扣")
    private String dgdsDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- （残损）购货折扣")
    private String dgdsDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- （残损）购货折扣")
    private String dgdsDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- （残损）购货折扣")
    private String dgdsDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- （残损）购货折扣")
    private String dgdsDisctLastyearRecAdj;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整- （残损）购货折扣")
    private String dgdsDisctLastyearAudAdj;
    @ExcelProperty(value ="备注- （残损）购货折扣")
    private String dgdsDisctMemo;
    @ExcelProperty(value ="（残损）购货折扣Total")
    private String dgdsDisctIncludeTax;
    @ExcelProperty(value ="（残损）购货折扣Total(不含税)")
    private String dgdsDisctIncludeNotax;
    @ExcelProperty(value ="条款- 目标退佣")
    private String itrDisctContractText;
    @ExcelProperty(value ="本年度目标计算- 目标退佣")
    private String itrDisctContractValue;
    @ExcelProperty(value ="本月合同调整- 目标退佣")
    private String itrDisctSumMoney;
    @ExcelProperty(value ="本月对帐调整- 目标退佣")
    private String itrDisctContractAdj;
    @ExcelProperty(value ="以前年度合同调整- 目标退佣")
    private String itrDisctReconAdj;
    @ExcelProperty(value ="以前年度对帐调整- 目标退佣")
    private String itrDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整- 目标退佣")
    private String itrDisctLastyearRecAdj;
    @ExcelProperty(value ="备注- 目标退佣")
    private String itrDisctLastyearAudAdj;
    @ExcelProperty(value ="目标退佣Total")
    private String itrDisctMemo;
    @ExcelProperty(value ="目标退佣(不含税)Total")
    private String itrDisctIncludeTax;
    @ExcelProperty(value ="达到目标条款相应的点数 目标退佣")
    private String itrDisctIncludeNotax;
    @ExcelProperty(value ="条款- 商业发展支持")
    private String bsdtDisctContractText;
    @ExcelProperty(value ="条款（%）- 商业发展支持")
    private String bsdtDisctContractValue;
    @ExcelProperty(value ="本月采购额计算- 商业发展支持")
    private String bsdtDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- 商业发展支持")
    private String bsdtDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- 商业发展支持")
    private String bsdtDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- 商业发展支持")
    private String bsdtDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- 商业发展支持")
    private String bsdtDisctLastyearRecAdj;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整- 商业发展支持")
    private String bsdtDisctLastyearAudAdj;
    @ExcelProperty(value ="备注- 商业发展支持")
    private String bsdtDisctMemo;
    @ExcelProperty(value ="商业发展支持Total")
    private String bsdtDisctIncludeTax;
    @ExcelProperty(value ="商业发展支持Total(不含税)")
    private String bsdtDisctIncludeNotax;
    @ExcelProperty(value ="条款-延误送货罚款")
    private String ldiDisctContractText;
    @ExcelProperty(value ="条款（%）- 延误送货罚款")
    private String ldiDisctContractValue;
    @ExcelProperty(value ="本月计算- 延误送货罚款")
    private String ldiDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- 延误送货罚款")
    private String ldiDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- 延误送货罚款")
    private String ldiDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- 延误送货罚款")
    private String ldiDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- 延误送货罚款")
    private String ldiDisctLastyearRecAdj;
    @ExcelProperty(value ="以前年度PRG调整/自查调整- 延误送货罚款")
    private String ldiDisctLastyearAudAdj;
    @ExcelProperty(value ="备注- 延误送货罚款")
    private String ldiDisctMemo;
    @ExcelProperty(value ="延误送货罚款Total")
    private String ldiDisctIncludeTax;
    @ExcelProperty(value ="延误送货罚款Total(不含税)")
    private String ldiDisctIncludeNotax;
    @ExcelProperty(value ="条款-满足率低于95%罚款")
    private String nonfpDisctContractText;
    @ExcelProperty(value ="条款（%）-满足率低于95%罚款")
    private String nonfpDisctContractValue;
    @ExcelProperty(value ="本月计算-满足率低于95%罚款")
    private String nonfpDisctSumMoney;
    @ExcelProperty(value ="本月合同调整-满足率低于95%罚款")
    private String nonfpDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整-满足率低于95%罚款")
    private String nonfpDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整-满足率低于95%罚款")
    private String nonfpDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整-满足率低于95%罚款")
    private String nonfpDisctLastyearRecAdj;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-满足率低于95%罚款")
    private String nonfpDisctLastyearAudAdj;
    @ExcelProperty(value ="备注-满足率低于95%罚款")
    private String nonfpDisctMemo;
    @ExcelProperty(value ="满足率低于95%罚款Total")
    private String nonfpDisctIncludeTax;
    @ExcelProperty(value ="满足率低于95%罚款Total(不含税)")
    private String nonfpDisctIncludeNotax;
    @ExcelProperty(value ="条款-优惠券")
    private String couDisctContractText;
    @ExcelProperty(value ="条款（%）- 优惠券")
    private String couDisctContractValue;
    @ExcelProperty(value ="本月计算- 优惠券")
    private String couDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- 优惠券")
    private String couDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- 优惠券")
    private String couDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- 优惠券")
    private String couDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- 优惠券")
    private String couDisctLastyearRecAdj;
    @ExcelProperty(value ="以前年度PRG调整/自查调整- 优惠券")
    private String couDisctLastyearAudAdj;
    @ExcelProperty(value ="备注- 优惠券")
    private String couDisctMemo;
    @ExcelProperty(value ="优惠券Total")
    private String couDisctIncludeTax;
    @ExcelProperty(value ="优惠券Total(不含税)")
    private String couDisctIncludeNotax;
    @ExcelProperty(value ="条款- 会员优惠")
    private String vipDisctContractText;
    @ExcelProperty(value ="条款（%）- 会员优惠")
    private String vipDisctContractValue;
    @ExcelProperty(value ="本月计算- 会员优惠")
    private String vipDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- 会员优惠")
    private String vipDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- 会员优惠")
    private String vipDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- 会员优惠")
    private String vipDisctLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- 会员优惠")
    private String vipDisctLastyearRecAdj;
    @ExcelProperty(value ="以前年度PRG调整/自查调整- 会员优惠")
    private String vipDisctLastyearAudAdj;
    @ExcelProperty(value ="备注- 会员优惠")
    private String vipDisctMemo;
    @ExcelProperty(value ="会员优惠Total")
    private String vipDisctIncludeTax;
    @ExcelProperty(value ="会员优惠Total(不含税)")
    private String vipDisctIncludeNotax;
    @ExcelProperty(value ="条款- 未在合同上的提早付款折扣")
    private String uncontrDisctContractText;
    @ExcelProperty(value ="条款（%）- 未在合同上的提早付款折扣")
    private String uncontrDisctContractValue;
    @ExcelProperty(value ="本月计算- 未在合同上的提早付款折扣")
    private String uncontrDisctSumMoney;
    @ExcelProperty(value ="本月合同调整- 未在合同上的提早付款折扣")
    private String uncontrDisctContractAdj;
    @ExcelProperty(value ="本月对帐调整- 未在合同上的提早付款折扣")
    private String uncontrDisctReconAdj;
    @ExcelProperty(value ="以前年度合同调整- 未在合同上的提早付款折扣")
    private String uncontrDisLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- 未在合同上的提早付款折扣")
    private String uncontrDisLastyearRecAdj;
    @ExcelProperty(value ="以前年度PRG调整/自查调整- 未在合同上的提早付款折扣")
    private String uncontrDisLastyearAudAdj;
    @ExcelProperty(value ="备注- 未在合同上的提早付款折扣")
    private String uncontrDisctMemo;
    @ExcelProperty(value ="未在合同上的提早付款折扣Total")
    private String uncontrDisctIncludeTax;
    @ExcelProperty(value ="未在合同上的提早付款折扣Total(不含税)")
    private String uncontrDisctIncludeNotax;
    @ExcelProperty(value ="条款- 成本补差")
    private String costRedIncome;
    @ExcelProperty(value ="条款（%）- 成本补差")
    private String costRedIncomeValue;
    @ExcelProperty(value ="本月计算(TTA)- 成本补差")
    private String costRedIncomeCurmonTta;
    @ExcelProperty(value ="本月计算(ON TOP)- 成本补差")
    private String costRedIncomeCurmonOntop;
    @ExcelProperty(value ="以前年度计算(TTA)- 成本补差")
    private String costRedIncomePyyearTta;
    @ExcelProperty(value ="以前年度计算(ON TOP)- 成本补差")
    private String costRedIncomePyyearOntop;
    @ExcelProperty(value ="本月合同调整- 成本补差")
    private String costRedContractAdj;
    @ExcelProperty(value ="本月对帐调整- 成本补差")
    private String costRedReconAdj;
    @ExcelProperty(value ="以前年度合同调整- 成本补差")
    private String costRedLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整- 成本补差")
    private String costRedLastyearRecAdj;
    @ExcelProperty(value ="以前年度PRG调整/自查调整- 成本补差")
    private String costRedLastyearAudAdj;
    @ExcelProperty(value ="备注- 成本补差")
    private String costRedMemo;
    @ExcelProperty(value ="成本补差Total")
    private String costRedIncludeTax;
    @ExcelProperty(value ="成本补差Total(不含税)")
    private String costDisctExcludeNotax;//uncontr_disct_exclude_notax  成本补差Total(不含税)
    @ExcelProperty(value ="条款-OEM试用装")
    private String otherOem;
    @ExcelProperty(value ="条款（%）-OEM试用装")
    private String otherOemValue;
    @ExcelProperty(value ="本月计算(TTA)-OEM试用装")
    private String otherOemMonthTta;
    @ExcelProperty(value ="本月计算(ON TOP)-OEM试用装")
    private String otherOemMonthOntop;
    @ExcelProperty(value ="以前年度计算(TTA)-OEM试用装")
    private String otherOemPyyearTta;
    @ExcelProperty(value ="以前年度计算(ON TOP)-OEM试用装")
    private String otherOemPyyearOntop;
    @ExcelProperty(value ="本月合同调整-OEM试用装")
    private String otherOemContractAdj;
    @ExcelProperty(value ="本月对帐调整-OEM试用装")
    private String otherOemReconAdj;
    @ExcelProperty(value ="以前年度合同调整-OEM试用装")
    private String otherOemLastyearConAdj;
    @ExcelProperty(value ="以前年度对帐调整-OEM试用装")
    private String otherOemLastyearRecAdj;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-OEM试用装")
    private String otherOemLastyearAudAdj;
    @ExcelProperty(value ="备注-OEM试用装")
    private String otherOemMemo;
    @ExcelProperty(value ="OEM试用装Total")
    private String otherOemIncludeTax;
    @ExcelProperty(value ="OEM试用装otal(不含税)")
    private String otherOemExcludeNotax;

    private Date creationDate;

    private Integer createdBy;

    private Integer lastUpdatedBy;

    private Date lastUpdateDate;

    private Integer lastUpdateLogin;

    private Integer versionNum;

    private String apOiTotalNotax1;

    private String tax1;

    private String pfc01DisctContractText;

    private String pfc01DisctContractValue;

    private String pfc01DisctSumMoney;

    private String pfc01DisctContractAdj;

    private String pfc01DisctReconAdj;

    private String pfc01DisLastyearConAdj;

    private String pfc01DisLastyearRecAdj;

    private String pfc01DisLastyearAudAdj;

    private String pfc01DisctMemo;

    private String pfc01DisctIncludeTax;

    private String pfc01DisctIncludeNotax;

    private String ddb01DisctContractText;

    private String ddb01DisctContractValue;

    private String ddb01DisctSumMoney;

    private String ddb01DisctContractAdj;

    private String ddb01DisctReconAdj;

    private String ddb01DisLastyearConAdj;

    private String ddb01DisLastyearRecAdj;

    private String ddb01DisLastyearAudAdj;

    private String ddb01DisctMemo;

    private String ddb01DisctIncludeTax;

    private String ddb01DisctIncludeNotax;

    private String rgt01DisctContractText;

    private String rgt01DisctContractValue;

    private String rgt01DisctSumMoney;

    private String rgt01DisctContractAdj;

    private String rgt01DisctReconAdj;

    private String rgt01DisLastyearConAdj;

    private String rgt01DisLastyearRecAdj;

    private String rgt01DisLastyearAudAdj;

    private String rgt01DisctMemo;

    private String rgt01DisctIncludeTax;

    private String rgt01DisctIncludeNotax;

    private String bdf01DisctContractText;

    private String bdf01DisctContractValue;

    private String bdf01DisctSumMoney;

    private String bdf01DisctContractAdj;

    private String bdf01DisctReconAdj;

    private String bdf01DisLastyearConAdj;

    private String bdf01DisLastyearRecAdj;

    private String bdf01DisLastyearAudAdj;

    private String bdf01DisctMemo;

    private String bdf01DisctIncludeTax;

    private String bdf01DisctIncludeNotax;

    private String oic01RedIncome;

    private String oic01RedIncomeValue;

    private String oic01RedIncomeCurmonTta;

    private String oic01RedIncomeCurmonOntop;

    private String oic01RedIncomePyyearTta;

    private String oic01RedIncomePyyearOntop;

    private String oic01RedContractAdj;

    private String oic01RedReconAdj;

    private String oic01RedLastyearConAdj;

    private String oic01RedLastyearRecAdj;

    private String oic01RedLastyearAudAdj;

    private String oic01RedMemo;

    private String oic01RedIncludeTax;

    private String oic01RedIncludeNotax;

    private String psf01DisctContractText;

    private String psf01DisctContractValue;

    private String psf01DisctSumMoney;

    private String psf01DisctContractAdj;

    private String psf01DisctReconAdj;

    private String psf01DisLastyearConAdj;

    private String psf01DisLastyearRecAdj;

    private String psf01DisLastyearAudAdj;

    private String psf01DisctMemo;

    private String psf01DisctIncludeTax;

    private String psf01DisctIncludeNotax;

    public Integer getTtaBeoiBillImportId() {
        return ttaBeoiBillImportId;
    }

    public void setTtaBeoiBillImportId(Integer ttaBeoiBillImportId) {
        this.ttaBeoiBillImportId = ttaBeoiBillImportId;
    }

    public Date getAccountMonth() {
        return accountMonth;
    }

    public void setAccountMonth(Date accountMonth) {
        this.accountMonth = accountMonth;
    }

    public String getRmsCode() {
        return rmsCode;
    }

    public void setRmsCode(String rmsCode) {
        this.rmsCode = rmsCode;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getBookInJv() {
        return bookInJv;
    }

    public void setBookInJv(String bookInJv) {
        this.bookInJv = bookInJv;
    }

    public String getBookInJv1() {
        return bookInJv1;
    }

    public void setBookInJv1(String bookInJv1) {
        this.bookInJv1 = bookInJv1;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVenderab() {
        return venderab;
    }

    public void setVenderab(String venderab) {
        this.venderab = venderab;
    }

    public String getUserContractId() {
        return userContractId;
    }

    public void setUserContractId(String userContractId) {
        this.userContractId = userContractId;
    }

    public String getCooperateStatus() {
        return cooperateStatus;
    }

    public void setCooperateStatus(String cooperateStatus) {
        this.cooperateStatus = cooperateStatus;
    }

    public String getVenderType() {
        return venderType;
    }

    public void setVenderType(String venderType) {
        this.venderType = venderType;
    }

    public String getFamilyPlaningFlag() {
        return familyPlaningFlag;
    }

    public void setFamilyPlaningFlag(String familyPlaningFlag) {
        this.familyPlaningFlag = familyPlaningFlag;
    }

    public Date getValidBeginDate() {
        return validBeginDate;
    }

    public void setValidBeginDate(Date validBeginDate) {
        this.validBeginDate = validBeginDate;
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
    }

    public String getPickUpRate() {
        return pickUpRate;
    }

    public void setPickUpRate(String pickUpRate) {
        this.pickUpRate = pickUpRate;
    }

    public String getVenderContainTax() {
        return venderContainTax;
    }

    public void setVenderContainTax(String venderContainTax) {
        this.venderContainTax = venderContainTax;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getGoodsReturn() {
        return goodsReturn;
    }

    public void setGoodsReturn(String goodsReturn) {
        this.goodsReturn = goodsReturn;
    }

    public String getNetPur() {
        return netPur;
    }

    public void setNetPur(String netPur) {
        this.netPur = netPur;
    }

    public String getDsd() {
        return dsd;
    }

    public void setDsd(String dsd) {
        this.dsd = dsd;
    }

    public String getPurchaseOrigin() {
        return purchaseOrigin;
    }

    public void setPurchaseOrigin(String purchaseOrigin) {
        this.purchaseOrigin = purchaseOrigin;
    }

    public String getGoodsReturnOrigin() {
        return goodsReturnOrigin;
    }

    public void setGoodsReturnOrigin(String goodsReturnOrigin) {
        this.goodsReturnOrigin = goodsReturnOrigin;
    }

    public String getNetPurOrigin() {
        return netPurOrigin;
    }

    public void setNetPurOrigin(String netPurOrigin) {
        this.netPurOrigin = netPurOrigin;
    }

    public String getPyPurchase() {
        return pyPurchase;
    }

    public void setPyPurchase(String pyPurchase) {
        this.pyPurchase = pyPurchase;
    }

    public String getPyGoodsReturn() {
        return pyGoodsReturn;
    }

    public void setPyGoodsReturn(String pyGoodsReturn) {
        this.pyGoodsReturn = pyGoodsReturn;
    }

    public String getPyNetPur() {
        return pyNetPur;
    }

    public void setPyNetPur(String pyNetPur) {
        this.pyNetPur = pyNetPur;
    }

    public String getPydsd() {
        return pydsd;
    }

    public void setPydsd(String pydsd) {
        this.pydsd = pydsd;
    }

    public String getMonthPur() {
        return monthPur;
    }

    public void setMonthPur(String monthPur) {
        this.monthPur = monthPur;
    }

    public String getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(String monthCount) {
        this.monthCount = monthCount;
    }

    public String getMonthAbi() {
        return monthAbi;
    }

    public void setMonthAbi(String monthAbi) {
        this.monthAbi = monthAbi;
    }

    public String getMonthAbiOntop() {
        return monthAbiOntop;
    }

    public void setMonthAbiOntop(String monthAbiOntop) {
        this.monthAbiOntop = monthAbiOntop;
    }

    public String getYearAbi() {
        return yearAbi;
    }

    public void setYearAbi(String yearAbi) {
        this.yearAbi = yearAbi;
    }

    public String getYearAbiOntop() {
        return yearAbiOntop;
    }

    public void setYearAbiOntop(String yearAbiOntop) {
        this.yearAbiOntop = yearAbiOntop;
    }

    public String getContractAdj() {
        return contractAdj;
    }

    public void setContractAdj(String contractAdj) {
        this.contractAdj = contractAdj;
    }

    public String getBillAdj() {
        return billAdj;
    }

    public void setBillAdj(String billAdj) {
        this.billAdj = billAdj;
    }

    public String getYearContractAdj() {
        return yearContractAdj;
    }

    public void setYearContractAdj(String yearContractAdj) {
        this.yearContractAdj = yearContractAdj;
    }

    public String getYearBillAdj() {
        return yearBillAdj;
    }

    public void setYearBillAdj(String yearBillAdj) {
        this.yearBillAdj = yearBillAdj;
    }

    public String getYearPurchaseCount() {
        return yearPurchaseCount;
    }

    public void setYearPurchaseCount(String yearPurchaseCount) {
        this.yearPurchaseCount = yearPurchaseCount;
    }

    public String getApOiTotalTax() {
        return apOiTotalTax;
    }

    public void setApOiTotalTax(String apOiTotalTax) {
        this.apOiTotalTax = apOiTotalTax;
    }

    public String getApOiTotalNotax() {
        return apOiTotalNotax;
    }

    public void setApOiTotalNotax(String apOiTotalNotax) {
        this.apOiTotalNotax = apOiTotalNotax;
    }

    public String getTax16() {
        return tax16;
    }

    public void setTax16(String tax16) {
        this.tax16 = tax16;
    }

    public String getNoTax13() {
        return noTax13;
    }

    public void setNoTax13(String noTax13) {
        this.noTax13 = noTax13;
    }

    public String getTax13() {
        return tax13;
    }

    public void setTax13(String tax13) {
        this.tax13 = tax13;
    }

    public String getNoTax10() {
        return noTax10;
    }

    public void setNoTax10(String noTax10) {
        this.noTax10 = noTax10;
    }

    public String getTax10() {
        return tax10;
    }

    public void setTax10(String tax10) {
        this.tax10 = tax10;
    }

    public String getPurDisctContractText() {
        return purDisctContractText;
    }

    public void setPurDisctContractText(String purDisctContractText) {
        this.purDisctContractText = purDisctContractText;
    }

    public String getPurDisctContractValue() {
        return purDisctContractValue;
    }

    public void setPurDisctContractValue(String purDisctContractValue) {
        this.purDisctContractValue = purDisctContractValue;
    }

    public String getPurDisctSumMoney() {
        return purDisctSumMoney;
    }

    public void setPurDisctSumMoney(String purDisctSumMoney) {
        this.purDisctSumMoney = purDisctSumMoney;
    }

    public String getPurDisctContractAdj() {
        return purDisctContractAdj;
    }

    public void setPurDisctContractAdj(String purDisctContractAdj) {
        this.purDisctContractAdj = purDisctContractAdj;
    }

    public String getPurDisctReconAdj() {
        return purDisctReconAdj;
    }

    public void setPurDisctReconAdj(String purDisctReconAdj) {
        this.purDisctReconAdj = purDisctReconAdj;
    }

    public String getPurDisctLastyearConAdj() {
        return purDisctLastyearConAdj;
    }

    public void setPurDisctLastyearConAdj(String purDisctLastyearConAdj) {
        this.purDisctLastyearConAdj = purDisctLastyearConAdj;
    }

    public String getPurDisctLastyearRecAdj() {
        return purDisctLastyearRecAdj;
    }

    public void setPurDisctLastyearRecAdj(String purDisctLastyearRecAdj) {
        this.purDisctLastyearRecAdj = purDisctLastyearRecAdj;
    }

    public String getPurDisctLastyearAudAdj() {
        return purDisctLastyearAudAdj;
    }

    public void setPurDisctLastyearAudAdj(String purDisctLastyearAudAdj) {
        this.purDisctLastyearAudAdj = purDisctLastyearAudAdj;
    }

    public String getPurDisctMemo() {
        return purDisctMemo;
    }

    public void setPurDisctMemo(String purDisctMemo) {
        this.purDisctMemo = purDisctMemo;
    }

    public String getPurDisctIncludeTax() {
        return purDisctIncludeTax;
    }

    public void setPurDisctIncludeTax(String purDisctIncludeTax) {
        this.purDisctIncludeTax = purDisctIncludeTax;
    }

    public String getPurDisctIncludeNotax() {
        return purDisctIncludeNotax;
    }

    public void setPurDisctIncludeNotax(String purDisctIncludeNotax) {
        this.purDisctIncludeNotax = purDisctIncludeNotax;
    }

    public String getEarlypayDisctContractText() {
        return earlypayDisctContractText;
    }

    public void setEarlypayDisctContractText(String earlypayDisctContractText) {
        this.earlypayDisctContractText = earlypayDisctContractText;
    }

    public String getEarlypayDisctContractValue() {
        return earlypayDisctContractValue;
    }

    public void setEarlypayDisctContractValue(String earlypayDisctContractValue) {
        this.earlypayDisctContractValue = earlypayDisctContractValue;
    }

    public String getEarlypayDisctSumMoney() {
        return earlypayDisctSumMoney;
    }

    public void setEarlypayDisctSumMoney(String earlypayDisctSumMoney) {
        this.earlypayDisctSumMoney = earlypayDisctSumMoney;
    }

    public String getEarlypayDisctContractAdj() {
        return earlypayDisctContractAdj;
    }

    public void setEarlypayDisctContractAdj(String earlypayDisctContractAdj) {
        this.earlypayDisctContractAdj = earlypayDisctContractAdj;
    }

    public String getEarlypayDisctReconAdj() {
        return earlypayDisctReconAdj;
    }

    public void setEarlypayDisctReconAdj(String earlypayDisctReconAdj) {
        this.earlypayDisctReconAdj = earlypayDisctReconAdj;
    }

    public String getEarlypayDisLastyearConAdj() {
        return earlypayDisLastyearConAdj;
    }

    public void setEarlypayDisLastyearConAdj(String earlypayDisLastyearConAdj) {
        this.earlypayDisLastyearConAdj = earlypayDisLastyearConAdj;
    }

    public String getEarlypayDisLastyearRecAdj() {
        return earlypayDisLastyearRecAdj;
    }

    public void setEarlypayDisLastyearRecAdj(String earlypayDisLastyearRecAdj) {
        this.earlypayDisLastyearRecAdj = earlypayDisLastyearRecAdj;
    }

    public String getEarlypayDisLastyearAudAdj() {
        return earlypayDisLastyearAudAdj;
    }

    public void setEarlypayDisLastyearAudAdj(String earlypayDisLastyearAudAdj) {
        this.earlypayDisLastyearAudAdj = earlypayDisLastyearAudAdj;
    }

    public String getEarlypayDisctMemo() {
        return earlypayDisctMemo;
    }

    public void setEarlypayDisctMemo(String earlypayDisctMemo) {
        this.earlypayDisctMemo = earlypayDisctMemo;
    }

    public String getEarlypayDisctIncludeTax() {
        return earlypayDisctIncludeTax;
    }

    public void setEarlypayDisctIncludeTax(String earlypayDisctIncludeTax) {
        this.earlypayDisctIncludeTax = earlypayDisctIncludeTax;
    }

    public String getEarlypayDisctIncludeNotax() {
        return earlypayDisctIncludeNotax;
    }

    public void setEarlypayDisctIncludeNotax(String earlypayDisctIncludeNotax) {
        this.earlypayDisctIncludeNotax = earlypayDisctIncludeNotax;
    }

    public String getPromotDisctContractText() {
        return promotDisctContractText;
    }

    public void setPromotDisctContractText(String promotDisctContractText) {
        this.promotDisctContractText = promotDisctContractText;
    }

    public String getPromotDisctContractValue() {
        return promotDisctContractValue;
    }

    public void setPromotDisctContractValue(String promotDisctContractValue) {
        this.promotDisctContractValue = promotDisctContractValue;
    }

    public String getPromotDisctSumMoney() {
        return promotDisctSumMoney;
    }

    public void setPromotDisctSumMoney(String promotDisctSumMoney) {
        this.promotDisctSumMoney = promotDisctSumMoney;
    }

    public String getPromotDisctContractAdj() {
        return promotDisctContractAdj;
    }

    public void setPromotDisctContractAdj(String promotDisctContractAdj) {
        this.promotDisctContractAdj = promotDisctContractAdj;
    }

    public String getPromotDisctReconAdj() {
        return promotDisctReconAdj;
    }

    public void setPromotDisctReconAdj(String promotDisctReconAdj) {
        this.promotDisctReconAdj = promotDisctReconAdj;
    }

    public String getPromotDisctLastyearConAdj() {
        return promotDisctLastyearConAdj;
    }

    public void setPromotDisctLastyearConAdj(String promotDisctLastyearConAdj) {
        this.promotDisctLastyearConAdj = promotDisctLastyearConAdj;
    }

    public String getPromotDisctLastyearRecAdj() {
        return promotDisctLastyearRecAdj;
    }

    public void setPromotDisctLastyearRecAdj(String promotDisctLastyearRecAdj) {
        this.promotDisctLastyearRecAdj = promotDisctLastyearRecAdj;
    }

    public String getPromotDisctLastyearAudAdj() {
        return promotDisctLastyearAudAdj;
    }

    public void setPromotDisctLastyearAudAdj(String promotDisctLastyearAudAdj) {
        this.promotDisctLastyearAudAdj = promotDisctLastyearAudAdj;
    }

    public String getPromotDisctMemo() {
        return promotDisctMemo;
    }

    public void setPromotDisctMemo(String promotDisctMemo) {
        this.promotDisctMemo = promotDisctMemo;
    }

    public String getPromotDisctIncludeTax() {
        return promotDisctIncludeTax;
    }

    public void setPromotDisctIncludeTax(String promotDisctIncludeTax) {
        this.promotDisctIncludeTax = promotDisctIncludeTax;
    }

    public String getPromotDisctIncludeNotax() {
        return promotDisctIncludeNotax;
    }

    public void setPromotDisctIncludeNotax(String promotDisctIncludeNotax) {
        this.promotDisctIncludeNotax = promotDisctIncludeNotax;
    }

    public String getDistriDisctContractText() {
        return distriDisctContractText;
    }

    public void setDistriDisctContractText(String distriDisctContractText) {
        this.distriDisctContractText = distriDisctContractText;
    }

    public String getDistriDisctContractValue() {
        return distriDisctContractValue;
    }

    public void setDistriDisctContractValue(String distriDisctContractValue) {
        this.distriDisctContractValue = distriDisctContractValue;
    }

    public String getDistriDisctSumMoney() {
        return distriDisctSumMoney;
    }

    public void setDistriDisctSumMoney(String distriDisctSumMoney) {
        this.distriDisctSumMoney = distriDisctSumMoney;
    }

    public String getDistriDisctContractAdj() {
        return distriDisctContractAdj;
    }

    public void setDistriDisctContractAdj(String distriDisctContractAdj) {
        this.distriDisctContractAdj = distriDisctContractAdj;
    }

    public String getDistriDisctReconAdj() {
        return distriDisctReconAdj;
    }

    public void setDistriDisctReconAdj(String distriDisctReconAdj) {
        this.distriDisctReconAdj = distriDisctReconAdj;
    }

    public String getDistriDisctLastyearConAdj() {
        return distriDisctLastyearConAdj;
    }

    public void setDistriDisctLastyearConAdj(String distriDisctLastyearConAdj) {
        this.distriDisctLastyearConAdj = distriDisctLastyearConAdj;
    }

    public String getDistriDisctLastyearRecAdj() {
        return distriDisctLastyearRecAdj;
    }

    public void setDistriDisctLastyearRecAdj(String distriDisctLastyearRecAdj) {
        this.distriDisctLastyearRecAdj = distriDisctLastyearRecAdj;
    }

    public String getDistriDisctLastyearAudAdj() {
        return distriDisctLastyearAudAdj;
    }

    public void setDistriDisctLastyearAudAdj(String distriDisctLastyearAudAdj) {
        this.distriDisctLastyearAudAdj = distriDisctLastyearAudAdj;
    }

    public String getDistriDisctMemo() {
        return distriDisctMemo;
    }

    public void setDistriDisctMemo(String distriDisctMemo) {
        this.distriDisctMemo = distriDisctMemo;
    }

    public String getDistriDisctIncludeTax() {
        return distriDisctIncludeTax;
    }

    public void setDistriDisctIncludeTax(String distriDisctIncludeTax) {
        this.distriDisctIncludeTax = distriDisctIncludeTax;
    }

    public String getDistriDisctIncludeNotax() {
        return distriDisctIncludeNotax;
    }

    public void setDistriDisctIncludeNotax(String distriDisctIncludeNotax) {
        this.distriDisctIncludeNotax = distriDisctIncludeNotax;
    }

    public String getDgdsDisctContractText() {
        return dgdsDisctContractText;
    }

    public void setDgdsDisctContractText(String dgdsDisctContractText) {
        this.dgdsDisctContractText = dgdsDisctContractText;
    }

    public String getDgdsDisctContractValue() {
        return dgdsDisctContractValue;
    }

    public void setDgdsDisctContractValue(String dgdsDisctContractValue) {
        this.dgdsDisctContractValue = dgdsDisctContractValue;
    }

    public String getDgdsDisctSumMoney() {
        return dgdsDisctSumMoney;
    }

    public void setDgdsDisctSumMoney(String dgdsDisctSumMoney) {
        this.dgdsDisctSumMoney = dgdsDisctSumMoney;
    }

    public String getDgdsDisctContractAdj() {
        return dgdsDisctContractAdj;
    }

    public void setDgdsDisctContractAdj(String dgdsDisctContractAdj) {
        this.dgdsDisctContractAdj = dgdsDisctContractAdj;
    }

    public String getDgdsDisctReconAdj() {
        return dgdsDisctReconAdj;
    }

    public void setDgdsDisctReconAdj(String dgdsDisctReconAdj) {
        this.dgdsDisctReconAdj = dgdsDisctReconAdj;
    }

    public String getDgdsDisctLastyearConAdj() {
        return dgdsDisctLastyearConAdj;
    }

    public void setDgdsDisctLastyearConAdj(String dgdsDisctLastyearConAdj) {
        this.dgdsDisctLastyearConAdj = dgdsDisctLastyearConAdj;
    }

    public String getDgdsDisctLastyearRecAdj() {
        return dgdsDisctLastyearRecAdj;
    }

    public void setDgdsDisctLastyearRecAdj(String dgdsDisctLastyearRecAdj) {
        this.dgdsDisctLastyearRecAdj = dgdsDisctLastyearRecAdj;
    }

    public String getDgdsDisctLastyearAudAdj() {
        return dgdsDisctLastyearAudAdj;
    }

    public void setDgdsDisctLastyearAudAdj(String dgdsDisctLastyearAudAdj) {
        this.dgdsDisctLastyearAudAdj = dgdsDisctLastyearAudAdj;
    }

    public String getDgdsDisctMemo() {
        return dgdsDisctMemo;
    }

    public void setDgdsDisctMemo(String dgdsDisctMemo) {
        this.dgdsDisctMemo = dgdsDisctMemo;
    }

    public String getDgdsDisctIncludeTax() {
        return dgdsDisctIncludeTax;
    }

    public void setDgdsDisctIncludeTax(String dgdsDisctIncludeTax) {
        this.dgdsDisctIncludeTax = dgdsDisctIncludeTax;
    }

    public String getDgdsDisctIncludeNotax() {
        return dgdsDisctIncludeNotax;
    }

    public void setDgdsDisctIncludeNotax(String dgdsDisctIncludeNotax) {
        this.dgdsDisctIncludeNotax = dgdsDisctIncludeNotax;
    }

    public String getItrDisctContractText() {
        return itrDisctContractText;
    }

    public void setItrDisctContractText(String itrDisctContractText) {
        this.itrDisctContractText = itrDisctContractText;
    }

    public String getItrDisctContractValue() {
        return itrDisctContractValue;
    }

    public void setItrDisctContractValue(String itrDisctContractValue) {
        this.itrDisctContractValue = itrDisctContractValue;
    }

    public String getItrDisctSumMoney() {
        return itrDisctSumMoney;
    }

    public void setItrDisctSumMoney(String itrDisctSumMoney) {
        this.itrDisctSumMoney = itrDisctSumMoney;
    }

    public String getItrDisctContractAdj() {
        return itrDisctContractAdj;
    }

    public void setItrDisctContractAdj(String itrDisctContractAdj) {
        this.itrDisctContractAdj = itrDisctContractAdj;
    }

    public String getItrDisctReconAdj() {
        return itrDisctReconAdj;
    }

    public void setItrDisctReconAdj(String itrDisctReconAdj) {
        this.itrDisctReconAdj = itrDisctReconAdj;
    }

    public String getItrDisctLastyearConAdj() {
        return itrDisctLastyearConAdj;
    }

    public void setItrDisctLastyearConAdj(String itrDisctLastyearConAdj) {
        this.itrDisctLastyearConAdj = itrDisctLastyearConAdj;
    }

    public String getItrDisctLastyearRecAdj() {
        return itrDisctLastyearRecAdj;
    }

    public void setItrDisctLastyearRecAdj(String itrDisctLastyearRecAdj) {
        this.itrDisctLastyearRecAdj = itrDisctLastyearRecAdj;
    }

    public String getItrDisctLastyearAudAdj() {
        return itrDisctLastyearAudAdj;
    }

    public void setItrDisctLastyearAudAdj(String itrDisctLastyearAudAdj) {
        this.itrDisctLastyearAudAdj = itrDisctLastyearAudAdj;
    }

    public String getItrDisctMemo() {
        return itrDisctMemo;
    }

    public void setItrDisctMemo(String itrDisctMemo) {
        this.itrDisctMemo = itrDisctMemo;
    }

    public String getItrDisctIncludeTax() {
        return itrDisctIncludeTax;
    }

    public void setItrDisctIncludeTax(String itrDisctIncludeTax) {
        this.itrDisctIncludeTax = itrDisctIncludeTax;
    }

    public String getItrDisctIncludeNotax() {
        return itrDisctIncludeNotax;
    }

    public void setItrDisctIncludeNotax(String itrDisctIncludeNotax) {
        this.itrDisctIncludeNotax = itrDisctIncludeNotax;
    }

    public String getBsdtDisctContractText() {
        return bsdtDisctContractText;
    }

    public void setBsdtDisctContractText(String bsdtDisctContractText) {
        this.bsdtDisctContractText = bsdtDisctContractText;
    }

    public String getBsdtDisctContractValue() {
        return bsdtDisctContractValue;
    }

    public void setBsdtDisctContractValue(String bsdtDisctContractValue) {
        this.bsdtDisctContractValue = bsdtDisctContractValue;
    }

    public String getBsdtDisctSumMoney() {
        return bsdtDisctSumMoney;
    }

    public void setBsdtDisctSumMoney(String bsdtDisctSumMoney) {
        this.bsdtDisctSumMoney = bsdtDisctSumMoney;
    }

    public String getBsdtDisctContractAdj() {
        return bsdtDisctContractAdj;
    }

    public void setBsdtDisctContractAdj(String bsdtDisctContractAdj) {
        this.bsdtDisctContractAdj = bsdtDisctContractAdj;
    }

    public String getBsdtDisctReconAdj() {
        return bsdtDisctReconAdj;
    }

    public void setBsdtDisctReconAdj(String bsdtDisctReconAdj) {
        this.bsdtDisctReconAdj = bsdtDisctReconAdj;
    }

    public String getBsdtDisctLastyearConAdj() {
        return bsdtDisctLastyearConAdj;
    }

    public void setBsdtDisctLastyearConAdj(String bsdtDisctLastyearConAdj) {
        this.bsdtDisctLastyearConAdj = bsdtDisctLastyearConAdj;
    }

    public String getBsdtDisctLastyearRecAdj() {
        return bsdtDisctLastyearRecAdj;
    }

    public void setBsdtDisctLastyearRecAdj(String bsdtDisctLastyearRecAdj) {
        this.bsdtDisctLastyearRecAdj = bsdtDisctLastyearRecAdj;
    }

    public String getBsdtDisctLastyearAudAdj() {
        return bsdtDisctLastyearAudAdj;
    }

    public void setBsdtDisctLastyearAudAdj(String bsdtDisctLastyearAudAdj) {
        this.bsdtDisctLastyearAudAdj = bsdtDisctLastyearAudAdj;
    }

    public String getBsdtDisctMemo() {
        return bsdtDisctMemo;
    }

    public void setBsdtDisctMemo(String bsdtDisctMemo) {
        this.bsdtDisctMemo = bsdtDisctMemo;
    }

    public String getBsdtDisctIncludeTax() {
        return bsdtDisctIncludeTax;
    }

    public void setBsdtDisctIncludeTax(String bsdtDisctIncludeTax) {
        this.bsdtDisctIncludeTax = bsdtDisctIncludeTax;
    }

    public String getBsdtDisctIncludeNotax() {
        return bsdtDisctIncludeNotax;
    }

    public void setBsdtDisctIncludeNotax(String bsdtDisctIncludeNotax) {
        this.bsdtDisctIncludeNotax = bsdtDisctIncludeNotax;
    }

    public String getLdiDisctContractText() {
        return ldiDisctContractText;
    }

    public void setLdiDisctContractText(String ldiDisctContractText) {
        this.ldiDisctContractText = ldiDisctContractText;
    }

    public String getLdiDisctContractValue() {
        return ldiDisctContractValue;
    }

    public void setLdiDisctContractValue(String ldiDisctContractValue) {
        this.ldiDisctContractValue = ldiDisctContractValue;
    }

    public String getLdiDisctSumMoney() {
        return ldiDisctSumMoney;
    }

    public void setLdiDisctSumMoney(String ldiDisctSumMoney) {
        this.ldiDisctSumMoney = ldiDisctSumMoney;
    }

    public String getLdiDisctContractAdj() {
        return ldiDisctContractAdj;
    }

    public void setLdiDisctContractAdj(String ldiDisctContractAdj) {
        this.ldiDisctContractAdj = ldiDisctContractAdj;
    }

    public String getLdiDisctReconAdj() {
        return ldiDisctReconAdj;
    }

    public void setLdiDisctReconAdj(String ldiDisctReconAdj) {
        this.ldiDisctReconAdj = ldiDisctReconAdj;
    }

    public String getLdiDisctLastyearConAdj() {
        return ldiDisctLastyearConAdj;
    }

    public void setLdiDisctLastyearConAdj(String ldiDisctLastyearConAdj) {
        this.ldiDisctLastyearConAdj = ldiDisctLastyearConAdj;
    }

    public String getLdiDisctLastyearRecAdj() {
        return ldiDisctLastyearRecAdj;
    }

    public void setLdiDisctLastyearRecAdj(String ldiDisctLastyearRecAdj) {
        this.ldiDisctLastyearRecAdj = ldiDisctLastyearRecAdj;
    }

    public String getLdiDisctLastyearAudAdj() {
        return ldiDisctLastyearAudAdj;
    }

    public void setLdiDisctLastyearAudAdj(String ldiDisctLastyearAudAdj) {
        this.ldiDisctLastyearAudAdj = ldiDisctLastyearAudAdj;
    }

    public String getLdiDisctMemo() {
        return ldiDisctMemo;
    }

    public void setLdiDisctMemo(String ldiDisctMemo) {
        this.ldiDisctMemo = ldiDisctMemo;
    }

    public String getLdiDisctIncludeTax() {
        return ldiDisctIncludeTax;
    }

    public void setLdiDisctIncludeTax(String ldiDisctIncludeTax) {
        this.ldiDisctIncludeTax = ldiDisctIncludeTax;
    }

    public String getLdiDisctIncludeNotax() {
        return ldiDisctIncludeNotax;
    }

    public void setLdiDisctIncludeNotax(String ldiDisctIncludeNotax) {
        this.ldiDisctIncludeNotax = ldiDisctIncludeNotax;
    }

    public String getNonfpDisctContractText() {
        return nonfpDisctContractText;
    }

    public void setNonfpDisctContractText(String nonfpDisctContractText) {
        this.nonfpDisctContractText = nonfpDisctContractText;
    }

    public String getNonfpDisctContractValue() {
        return nonfpDisctContractValue;
    }

    public void setNonfpDisctContractValue(String nonfpDisctContractValue) {
        this.nonfpDisctContractValue = nonfpDisctContractValue;
    }

    public String getNonfpDisctSumMoney() {
        return nonfpDisctSumMoney;
    }

    public void setNonfpDisctSumMoney(String nonfpDisctSumMoney) {
        this.nonfpDisctSumMoney = nonfpDisctSumMoney;
    }

    public String getNonfpDisctContractAdj() {
        return nonfpDisctContractAdj;
    }

    public void setNonfpDisctContractAdj(String nonfpDisctContractAdj) {
        this.nonfpDisctContractAdj = nonfpDisctContractAdj;
    }

    public String getNonfpDisctReconAdj() {
        return nonfpDisctReconAdj;
    }

    public void setNonfpDisctReconAdj(String nonfpDisctReconAdj) {
        this.nonfpDisctReconAdj = nonfpDisctReconAdj;
    }

    public String getNonfpDisctLastyearConAdj() {
        return nonfpDisctLastyearConAdj;
    }

    public void setNonfpDisctLastyearConAdj(String nonfpDisctLastyearConAdj) {
        this.nonfpDisctLastyearConAdj = nonfpDisctLastyearConAdj;
    }

    public String getNonfpDisctLastyearRecAdj() {
        return nonfpDisctLastyearRecAdj;
    }

    public void setNonfpDisctLastyearRecAdj(String nonfpDisctLastyearRecAdj) {
        this.nonfpDisctLastyearRecAdj = nonfpDisctLastyearRecAdj;
    }

    public String getNonfpDisctLastyearAudAdj() {
        return nonfpDisctLastyearAudAdj;
    }

    public void setNonfpDisctLastyearAudAdj(String nonfpDisctLastyearAudAdj) {
        this.nonfpDisctLastyearAudAdj = nonfpDisctLastyearAudAdj;
    }

    public String getNonfpDisctMemo() {
        return nonfpDisctMemo;
    }

    public void setNonfpDisctMemo(String nonfpDisctMemo) {
        this.nonfpDisctMemo = nonfpDisctMemo;
    }

    public String getNonfpDisctIncludeTax() {
        return nonfpDisctIncludeTax;
    }

    public void setNonfpDisctIncludeTax(String nonfpDisctIncludeTax) {
        this.nonfpDisctIncludeTax = nonfpDisctIncludeTax;
    }

    public String getNonfpDisctIncludeNotax() {
        return nonfpDisctIncludeNotax;
    }

    public void setNonfpDisctIncludeNotax(String nonfpDisctIncludeNotax) {
        this.nonfpDisctIncludeNotax = nonfpDisctIncludeNotax;
    }

    public String getCouDisctContractText() {
        return couDisctContractText;
    }

    public void setCouDisctContractText(String couDisctContractText) {
        this.couDisctContractText = couDisctContractText;
    }

    public String getCouDisctContractValue() {
        return couDisctContractValue;
    }

    public void setCouDisctContractValue(String couDisctContractValue) {
        this.couDisctContractValue = couDisctContractValue;
    }

    public String getCouDisctSumMoney() {
        return couDisctSumMoney;
    }

    public void setCouDisctSumMoney(String couDisctSumMoney) {
        this.couDisctSumMoney = couDisctSumMoney;
    }

    public String getCouDisctContractAdj() {
        return couDisctContractAdj;
    }

    public void setCouDisctContractAdj(String couDisctContractAdj) {
        this.couDisctContractAdj = couDisctContractAdj;
    }

    public String getCouDisctReconAdj() {
        return couDisctReconAdj;
    }

    public void setCouDisctReconAdj(String couDisctReconAdj) {
        this.couDisctReconAdj = couDisctReconAdj;
    }

    public String getCouDisctLastyearConAdj() {
        return couDisctLastyearConAdj;
    }

    public void setCouDisctLastyearConAdj(String couDisctLastyearConAdj) {
        this.couDisctLastyearConAdj = couDisctLastyearConAdj;
    }

    public String getCouDisctLastyearRecAdj() {
        return couDisctLastyearRecAdj;
    }

    public void setCouDisctLastyearRecAdj(String couDisctLastyearRecAdj) {
        this.couDisctLastyearRecAdj = couDisctLastyearRecAdj;
    }

    public String getCouDisctLastyearAudAdj() {
        return couDisctLastyearAudAdj;
    }

    public void setCouDisctLastyearAudAdj(String couDisctLastyearAudAdj) {
        this.couDisctLastyearAudAdj = couDisctLastyearAudAdj;
    }

    public String getCouDisctMemo() {
        return couDisctMemo;
    }

    public void setCouDisctMemo(String couDisctMemo) {
        this.couDisctMemo = couDisctMemo;
    }

    public String getCouDisctIncludeTax() {
        return couDisctIncludeTax;
    }

    public void setCouDisctIncludeTax(String couDisctIncludeTax) {
        this.couDisctIncludeTax = couDisctIncludeTax;
    }

    public String getCouDisctIncludeNotax() {
        return couDisctIncludeNotax;
    }

    public void setCouDisctIncludeNotax(String couDisctIncludeNotax) {
        this.couDisctIncludeNotax = couDisctIncludeNotax;
    }

    public String getVipDisctContractText() {
        return vipDisctContractText;
    }

    public void setVipDisctContractText(String vipDisctContractText) {
        this.vipDisctContractText = vipDisctContractText;
    }

    public String getVipDisctContractValue() {
        return vipDisctContractValue;
    }

    public void setVipDisctContractValue(String vipDisctContractValue) {
        this.vipDisctContractValue = vipDisctContractValue;
    }

    public String getVipDisctSumMoney() {
        return vipDisctSumMoney;
    }

    public void setVipDisctSumMoney(String vipDisctSumMoney) {
        this.vipDisctSumMoney = vipDisctSumMoney;
    }

    public String getVipDisctContractAdj() {
        return vipDisctContractAdj;
    }

    public void setVipDisctContractAdj(String vipDisctContractAdj) {
        this.vipDisctContractAdj = vipDisctContractAdj;
    }

    public String getVipDisctReconAdj() {
        return vipDisctReconAdj;
    }

    public void setVipDisctReconAdj(String vipDisctReconAdj) {
        this.vipDisctReconAdj = vipDisctReconAdj;
    }

    public String getVipDisctLastyearConAdj() {
        return vipDisctLastyearConAdj;
    }

    public void setVipDisctLastyearConAdj(String vipDisctLastyearConAdj) {
        this.vipDisctLastyearConAdj = vipDisctLastyearConAdj;
    }

    public String getVipDisctLastyearRecAdj() {
        return vipDisctLastyearRecAdj;
    }

    public void setVipDisctLastyearRecAdj(String vipDisctLastyearRecAdj) {
        this.vipDisctLastyearRecAdj = vipDisctLastyearRecAdj;
    }

    public String getVipDisctLastyearAudAdj() {
        return vipDisctLastyearAudAdj;
    }

    public void setVipDisctLastyearAudAdj(String vipDisctLastyearAudAdj) {
        this.vipDisctLastyearAudAdj = vipDisctLastyearAudAdj;
    }

    public String getVipDisctMemo() {
        return vipDisctMemo;
    }

    public void setVipDisctMemo(String vipDisctMemo) {
        this.vipDisctMemo = vipDisctMemo;
    }

    public String getVipDisctIncludeTax() {
        return vipDisctIncludeTax;
    }

    public void setVipDisctIncludeTax(String vipDisctIncludeTax) {
        this.vipDisctIncludeTax = vipDisctIncludeTax;
    }

    public String getVipDisctIncludeNotax() {
        return vipDisctIncludeNotax;
    }

    public void setVipDisctIncludeNotax(String vipDisctIncludeNotax) {
        this.vipDisctIncludeNotax = vipDisctIncludeNotax;
    }

    public String getUncontrDisctContractText() {
        return uncontrDisctContractText;
    }

    public void setUncontrDisctContractText(String uncontrDisctContractText) {
        this.uncontrDisctContractText = uncontrDisctContractText;
    }

    public String getUncontrDisctContractValue() {
        return uncontrDisctContractValue;
    }

    public void setUncontrDisctContractValue(String uncontrDisctContractValue) {
        this.uncontrDisctContractValue = uncontrDisctContractValue;
    }

    public String getUncontrDisctSumMoney() {
        return uncontrDisctSumMoney;
    }

    public void setUncontrDisctSumMoney(String uncontrDisctSumMoney) {
        this.uncontrDisctSumMoney = uncontrDisctSumMoney;
    }

    public String getUncontrDisctContractAdj() {
        return uncontrDisctContractAdj;
    }

    public void setUncontrDisctContractAdj(String uncontrDisctContractAdj) {
        this.uncontrDisctContractAdj = uncontrDisctContractAdj;
    }

    public String getUncontrDisctReconAdj() {
        return uncontrDisctReconAdj;
    }

    public void setUncontrDisctReconAdj(String uncontrDisctReconAdj) {
        this.uncontrDisctReconAdj = uncontrDisctReconAdj;
    }

    public String getUncontrDisLastyearConAdj() {
        return uncontrDisLastyearConAdj;
    }

    public void setUncontrDisLastyearConAdj(String uncontrDisLastyearConAdj) {
        this.uncontrDisLastyearConAdj = uncontrDisLastyearConAdj;
    }

    public String getUncontrDisLastyearRecAdj() {
        return uncontrDisLastyearRecAdj;
    }

    public void setUncontrDisLastyearRecAdj(String uncontrDisLastyearRecAdj) {
        this.uncontrDisLastyearRecAdj = uncontrDisLastyearRecAdj;
    }

    public String getUncontrDisLastyearAudAdj() {
        return uncontrDisLastyearAudAdj;
    }

    public void setUncontrDisLastyearAudAdj(String uncontrDisLastyearAudAdj) {
        this.uncontrDisLastyearAudAdj = uncontrDisLastyearAudAdj;
    }

    public String getUncontrDisctMemo() {
        return uncontrDisctMemo;
    }

    public void setUncontrDisctMemo(String uncontrDisctMemo) {
        this.uncontrDisctMemo = uncontrDisctMemo;
    }

    public String getUncontrDisctIncludeTax() {
        return uncontrDisctIncludeTax;
    }

    public void setUncontrDisctIncludeTax(String uncontrDisctIncludeTax) {
        this.uncontrDisctIncludeTax = uncontrDisctIncludeTax;
    }

    public String getUncontrDisctIncludeNotax() {
        return uncontrDisctIncludeNotax;
    }

    public void setUncontrDisctIncludeNotax(String uncontrDisctIncludeNotax) {
        this.uncontrDisctIncludeNotax = uncontrDisctIncludeNotax;
    }

    public String getCostRedIncome() {
        return costRedIncome;
    }

    public void setCostRedIncome(String costRedIncome) {
        this.costRedIncome = costRedIncome;
    }

    public String getCostRedIncomeValue() {
        return costRedIncomeValue;
    }

    public void setCostRedIncomeValue(String costRedIncomeValue) {
        this.costRedIncomeValue = costRedIncomeValue;
    }

    public String getCostRedIncomeCurmonTta() {
        return costRedIncomeCurmonTta;
    }

    public void setCostRedIncomeCurmonTta(String costRedIncomeCurmonTta) {
        this.costRedIncomeCurmonTta = costRedIncomeCurmonTta;
    }

    public String getCostRedIncomeCurmonOntop() {
        return costRedIncomeCurmonOntop;
    }

    public void setCostRedIncomeCurmonOntop(String costRedIncomeCurmonOntop) {
        this.costRedIncomeCurmonOntop = costRedIncomeCurmonOntop;
    }

    public String getCostRedIncomePyyearTta() {
        return costRedIncomePyyearTta;
    }

    public void setCostRedIncomePyyearTta(String costRedIncomePyyearTta) {
        this.costRedIncomePyyearTta = costRedIncomePyyearTta;
    }

    public String getCostRedIncomePyyearOntop() {
        return costRedIncomePyyearOntop;
    }

    public void setCostRedIncomePyyearOntop(String costRedIncomePyyearOntop) {
        this.costRedIncomePyyearOntop = costRedIncomePyyearOntop;
    }

    public String getCostRedContractAdj() {
        return costRedContractAdj;
    }

    public void setCostRedContractAdj(String costRedContractAdj) {
        this.costRedContractAdj = costRedContractAdj;
    }

    public String getCostRedReconAdj() {
        return costRedReconAdj;
    }

    public void setCostRedReconAdj(String costRedReconAdj) {
        this.costRedReconAdj = costRedReconAdj;
    }

    public String getCostRedLastyearConAdj() {
        return costRedLastyearConAdj;
    }

    public void setCostRedLastyearConAdj(String costRedLastyearConAdj) {
        this.costRedLastyearConAdj = costRedLastyearConAdj;
    }

    public String getCostRedLastyearRecAdj() {
        return costRedLastyearRecAdj;
    }

    public void setCostRedLastyearRecAdj(String costRedLastyearRecAdj) {
        this.costRedLastyearRecAdj = costRedLastyearRecAdj;
    }

    public String getCostRedLastyearAudAdj() {
        return costRedLastyearAudAdj;
    }

    public void setCostRedLastyearAudAdj(String costRedLastyearAudAdj) {
        this.costRedLastyearAudAdj = costRedLastyearAudAdj;
    }

    public String getCostRedMemo() {
        return costRedMemo;
    }

    public void setCostRedMemo(String costRedMemo) {
        this.costRedMemo = costRedMemo;
    }

    public String getCostRedIncludeTax() {
        return costRedIncludeTax;
    }

    public void setCostRedIncludeTax(String costRedIncludeTax) {
        this.costRedIncludeTax = costRedIncludeTax;
    }

    public String getCostDisctExcludeNotax() {
        return costDisctExcludeNotax;
    }

    public void setCostDisctExcludeNotax(String costDisctExcludeNotax) {
        this.costDisctExcludeNotax = costDisctExcludeNotax;
    }

    public String getOtherOem() {
        return otherOem;
    }

    public void setOtherOem(String otherOem) {
        this.otherOem = otherOem;
    }

    public String getOtherOemValue() {
        return otherOemValue;
    }

    public void setOtherOemValue(String otherOemValue) {
        this.otherOemValue = otherOemValue;
    }

    public String getOtherOemMonthTta() {
        return otherOemMonthTta;
    }

    public void setOtherOemMonthTta(String otherOemMonthTta) {
        this.otherOemMonthTta = otherOemMonthTta;
    }

    public String getOtherOemMonthOntop() {
        return otherOemMonthOntop;
    }

    public void setOtherOemMonthOntop(String otherOemMonthOntop) {
        this.otherOemMonthOntop = otherOemMonthOntop;
    }

    public String getOtherOemPyyearTta() {
        return otherOemPyyearTta;
    }

    public void setOtherOemPyyearTta(String otherOemPyyearTta) {
        this.otherOemPyyearTta = otherOemPyyearTta;
    }

    public String getOtherOemPyyearOntop() {
        return otherOemPyyearOntop;
    }

    public void setOtherOemPyyearOntop(String otherOemPyyearOntop) {
        this.otherOemPyyearOntop = otherOemPyyearOntop;
    }

    public String getOtherOemContractAdj() {
        return otherOemContractAdj;
    }

    public void setOtherOemContractAdj(String otherOemContractAdj) {
        this.otherOemContractAdj = otherOemContractAdj;
    }

    public String getOtherOemReconAdj() {
        return otherOemReconAdj;
    }

    public void setOtherOemReconAdj(String otherOemReconAdj) {
        this.otherOemReconAdj = otherOemReconAdj;
    }

    public String getOtherOemLastyearConAdj() {
        return otherOemLastyearConAdj;
    }

    public void setOtherOemLastyearConAdj(String otherOemLastyearConAdj) {
        this.otherOemLastyearConAdj = otherOemLastyearConAdj;
    }

    public String getOtherOemLastyearRecAdj() {
        return otherOemLastyearRecAdj;
    }

    public void setOtherOemLastyearRecAdj(String otherOemLastyearRecAdj) {
        this.otherOemLastyearRecAdj = otherOemLastyearRecAdj;
    }

    public String getOtherOemLastyearAudAdj() {
        return otherOemLastyearAudAdj;
    }

    public void setOtherOemLastyearAudAdj(String otherOemLastyearAudAdj) {
        this.otherOemLastyearAudAdj = otherOemLastyearAudAdj;
    }

    public String getOtherOemMemo() {
        return otherOemMemo;
    }

    public void setOtherOemMemo(String otherOemMemo) {
        this.otherOemMemo = otherOemMemo;
    }

    public String getOtherOemIncludeTax() {
        return otherOemIncludeTax;
    }

    public void setOtherOemIncludeTax(String otherOemIncludeTax) {
        this.otherOemIncludeTax = otherOemIncludeTax;
    }

    public String getOtherOemExcludeNotax() {
        return otherOemExcludeNotax;
    }

    public void setOtherOemExcludeNotax(String otherOemExcludeNotax) {
        this.otherOemExcludeNotax = otherOemExcludeNotax;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getApOiTotalNotax1() {
        return apOiTotalNotax1;
    }

    public void setApOiTotalNotax1(String apOiTotalNotax1) {
        this.apOiTotalNotax1 = apOiTotalNotax1;
    }

    public String getTax1() {
        return tax1;
    }

    public void setTax1(String tax1) {
        this.tax1 = tax1;
    }

    public String getPfc01DisctContractText() {
        return pfc01DisctContractText;
    }

    public void setPfc01DisctContractText(String pfc01DisctContractText) {
        this.pfc01DisctContractText = pfc01DisctContractText;
    }

    public String getPfc01DisctContractValue() {
        return pfc01DisctContractValue;
    }

    public void setPfc01DisctContractValue(String pfc01DisctContractValue) {
        this.pfc01DisctContractValue = pfc01DisctContractValue;
    }

    public String getPfc01DisctSumMoney() {
        return pfc01DisctSumMoney;
    }

    public void setPfc01DisctSumMoney(String pfc01DisctSumMoney) {
        this.pfc01DisctSumMoney = pfc01DisctSumMoney;
    }

    public String getPfc01DisctContractAdj() {
        return pfc01DisctContractAdj;
    }

    public void setPfc01DisctContractAdj(String pfc01DisctContractAdj) {
        this.pfc01DisctContractAdj = pfc01DisctContractAdj;
    }

    public String getPfc01DisctReconAdj() {
        return pfc01DisctReconAdj;
    }

    public void setPfc01DisctReconAdj(String pfc01DisctReconAdj) {
        this.pfc01DisctReconAdj = pfc01DisctReconAdj;
    }

    public String getPfc01DisLastyearConAdj() {
        return pfc01DisLastyearConAdj;
    }

    public void setPfc01DisLastyearConAdj(String pfc01DisLastyearConAdj) {
        this.pfc01DisLastyearConAdj = pfc01DisLastyearConAdj;
    }

    public String getPfc01DisLastyearRecAdj() {
        return pfc01DisLastyearRecAdj;
    }

    public void setPfc01DisLastyearRecAdj(String pfc01DisLastyearRecAdj) {
        this.pfc01DisLastyearRecAdj = pfc01DisLastyearRecAdj;
    }

    public String getPfc01DisLastyearAudAdj() {
        return pfc01DisLastyearAudAdj;
    }

    public void setPfc01DisLastyearAudAdj(String pfc01DisLastyearAudAdj) {
        this.pfc01DisLastyearAudAdj = pfc01DisLastyearAudAdj;
    }

    public String getPfc01DisctMemo() {
        return pfc01DisctMemo;
    }

    public void setPfc01DisctMemo(String pfc01DisctMemo) {
        this.pfc01DisctMemo = pfc01DisctMemo;
    }

    public String getPfc01DisctIncludeTax() {
        return pfc01DisctIncludeTax;
    }

    public void setPfc01DisctIncludeTax(String pfc01DisctIncludeTax) {
        this.pfc01DisctIncludeTax = pfc01DisctIncludeTax;
    }

    public String getPfc01DisctIncludeNotax() {
        return pfc01DisctIncludeNotax;
    }

    public void setPfc01DisctIncludeNotax(String pfc01DisctIncludeNotax) {
        this.pfc01DisctIncludeNotax = pfc01DisctIncludeNotax;
    }

    public String getDdb01DisctContractText() {
        return ddb01DisctContractText;
    }

    public void setDdb01DisctContractText(String ddb01DisctContractText) {
        this.ddb01DisctContractText = ddb01DisctContractText;
    }

    public String getDdb01DisctContractValue() {
        return ddb01DisctContractValue;
    }

    public void setDdb01DisctContractValue(String ddb01DisctContractValue) {
        this.ddb01DisctContractValue = ddb01DisctContractValue;
    }

    public String getDdb01DisctSumMoney() {
        return ddb01DisctSumMoney;
    }

    public void setDdb01DisctSumMoney(String ddb01DisctSumMoney) {
        this.ddb01DisctSumMoney = ddb01DisctSumMoney;
    }

    public String getDdb01DisctContractAdj() {
        return ddb01DisctContractAdj;
    }

    public void setDdb01DisctContractAdj(String ddb01DisctContractAdj) {
        this.ddb01DisctContractAdj = ddb01DisctContractAdj;
    }

    public String getDdb01DisctReconAdj() {
        return ddb01DisctReconAdj;
    }

    public void setDdb01DisctReconAdj(String ddb01DisctReconAdj) {
        this.ddb01DisctReconAdj = ddb01DisctReconAdj;
    }

    public String getDdb01DisLastyearConAdj() {
        return ddb01DisLastyearConAdj;
    }

    public void setDdb01DisLastyearConAdj(String ddb01DisLastyearConAdj) {
        this.ddb01DisLastyearConAdj = ddb01DisLastyearConAdj;
    }

    public String getDdb01DisLastyearRecAdj() {
        return ddb01DisLastyearRecAdj;
    }

    public void setDdb01DisLastyearRecAdj(String ddb01DisLastyearRecAdj) {
        this.ddb01DisLastyearRecAdj = ddb01DisLastyearRecAdj;
    }

    public String getDdb01DisLastyearAudAdj() {
        return ddb01DisLastyearAudAdj;
    }

    public void setDdb01DisLastyearAudAdj(String ddb01DisLastyearAudAdj) {
        this.ddb01DisLastyearAudAdj = ddb01DisLastyearAudAdj;
    }

    public String getDdb01DisctMemo() {
        return ddb01DisctMemo;
    }

    public void setDdb01DisctMemo(String ddb01DisctMemo) {
        this.ddb01DisctMemo = ddb01DisctMemo;
    }

    public String getDdb01DisctIncludeTax() {
        return ddb01DisctIncludeTax;
    }

    public void setDdb01DisctIncludeTax(String ddb01DisctIncludeTax) {
        this.ddb01DisctIncludeTax = ddb01DisctIncludeTax;
    }

    public String getDdb01DisctIncludeNotax() {
        return ddb01DisctIncludeNotax;
    }

    public void setDdb01DisctIncludeNotax(String ddb01DisctIncludeNotax) {
        this.ddb01DisctIncludeNotax = ddb01DisctIncludeNotax;
    }

    public String getRgt01DisctContractText() {
        return rgt01DisctContractText;
    }

    public void setRgt01DisctContractText(String rgt01DisctContractText) {
        this.rgt01DisctContractText = rgt01DisctContractText;
    }

    public String getRgt01DisctContractValue() {
        return rgt01DisctContractValue;
    }

    public void setRgt01DisctContractValue(String rgt01DisctContractValue) {
        this.rgt01DisctContractValue = rgt01DisctContractValue;
    }

    public String getRgt01DisctSumMoney() {
        return rgt01DisctSumMoney;
    }

    public void setRgt01DisctSumMoney(String rgt01DisctSumMoney) {
        this.rgt01DisctSumMoney = rgt01DisctSumMoney;
    }

    public String getRgt01DisctContractAdj() {
        return rgt01DisctContractAdj;
    }

    public void setRgt01DisctContractAdj(String rgt01DisctContractAdj) {
        this.rgt01DisctContractAdj = rgt01DisctContractAdj;
    }

    public String getRgt01DisctReconAdj() {
        return rgt01DisctReconAdj;
    }

    public void setRgt01DisctReconAdj(String rgt01DisctReconAdj) {
        this.rgt01DisctReconAdj = rgt01DisctReconAdj;
    }

    public String getRgt01DisLastyearConAdj() {
        return rgt01DisLastyearConAdj;
    }

    public void setRgt01DisLastyearConAdj(String rgt01DisLastyearConAdj) {
        this.rgt01DisLastyearConAdj = rgt01DisLastyearConAdj;
    }

    public String getRgt01DisLastyearRecAdj() {
        return rgt01DisLastyearRecAdj;
    }

    public void setRgt01DisLastyearRecAdj(String rgt01DisLastyearRecAdj) {
        this.rgt01DisLastyearRecAdj = rgt01DisLastyearRecAdj;
    }

    public String getRgt01DisLastyearAudAdj() {
        return rgt01DisLastyearAudAdj;
    }

    public void setRgt01DisLastyearAudAdj(String rgt01DisLastyearAudAdj) {
        this.rgt01DisLastyearAudAdj = rgt01DisLastyearAudAdj;
    }

    public String getRgt01DisctMemo() {
        return rgt01DisctMemo;
    }

    public void setRgt01DisctMemo(String rgt01DisctMemo) {
        this.rgt01DisctMemo = rgt01DisctMemo;
    }

    public String getRgt01DisctIncludeTax() {
        return rgt01DisctIncludeTax;
    }

    public void setRgt01DisctIncludeTax(String rgt01DisctIncludeTax) {
        this.rgt01DisctIncludeTax = rgt01DisctIncludeTax;
    }

    public String getRgt01DisctIncludeNotax() {
        return rgt01DisctIncludeNotax;
    }

    public void setRgt01DisctIncludeNotax(String rgt01DisctIncludeNotax) {
        this.rgt01DisctIncludeNotax = rgt01DisctIncludeNotax;
    }

    public String getBdf01DisctContractText() {
        return bdf01DisctContractText;
    }

    public void setBdf01DisctContractText(String bdf01DisctContractText) {
        this.bdf01DisctContractText = bdf01DisctContractText;
    }

    public String getBdf01DisctContractValue() {
        return bdf01DisctContractValue;
    }

    public void setBdf01DisctContractValue(String bdf01DisctContractValue) {
        this.bdf01DisctContractValue = bdf01DisctContractValue;
    }

    public String getBdf01DisctSumMoney() {
        return bdf01DisctSumMoney;
    }

    public void setBdf01DisctSumMoney(String bdf01DisctSumMoney) {
        this.bdf01DisctSumMoney = bdf01DisctSumMoney;
    }

    public String getBdf01DisctContractAdj() {
        return bdf01DisctContractAdj;
    }

    public void setBdf01DisctContractAdj(String bdf01DisctContractAdj) {
        this.bdf01DisctContractAdj = bdf01DisctContractAdj;
    }

    public String getBdf01DisctReconAdj() {
        return bdf01DisctReconAdj;
    }

    public void setBdf01DisctReconAdj(String bdf01DisctReconAdj) {
        this.bdf01DisctReconAdj = bdf01DisctReconAdj;
    }

    public String getBdf01DisLastyearConAdj() {
        return bdf01DisLastyearConAdj;
    }

    public void setBdf01DisLastyearConAdj(String bdf01DisLastyearConAdj) {
        this.bdf01DisLastyearConAdj = bdf01DisLastyearConAdj;
    }

    public String getBdf01DisLastyearRecAdj() {
        return bdf01DisLastyearRecAdj;
    }

    public void setBdf01DisLastyearRecAdj(String bdf01DisLastyearRecAdj) {
        this.bdf01DisLastyearRecAdj = bdf01DisLastyearRecAdj;
    }

    public String getBdf01DisLastyearAudAdj() {
        return bdf01DisLastyearAudAdj;
    }

    public void setBdf01DisLastyearAudAdj(String bdf01DisLastyearAudAdj) {
        this.bdf01DisLastyearAudAdj = bdf01DisLastyearAudAdj;
    }

    public String getBdf01DisctMemo() {
        return bdf01DisctMemo;
    }

    public void setBdf01DisctMemo(String bdf01DisctMemo) {
        this.bdf01DisctMemo = bdf01DisctMemo;
    }

    public String getBdf01DisctIncludeTax() {
        return bdf01DisctIncludeTax;
    }

    public void setBdf01DisctIncludeTax(String bdf01DisctIncludeTax) {
        this.bdf01DisctIncludeTax = bdf01DisctIncludeTax;
    }

    public String getBdf01DisctIncludeNotax() {
        return bdf01DisctIncludeNotax;
    }

    public void setBdf01DisctIncludeNotax(String bdf01DisctIncludeNotax) {
        this.bdf01DisctIncludeNotax = bdf01DisctIncludeNotax;
    }

    public String getOic01RedIncome() {
        return oic01RedIncome;
    }

    public void setOic01RedIncome(String oic01RedIncome) {
        this.oic01RedIncome = oic01RedIncome;
    }

    public String getOic01RedIncomeValue() {
        return oic01RedIncomeValue;
    }

    public void setOic01RedIncomeValue(String oic01RedIncomeValue) {
        this.oic01RedIncomeValue = oic01RedIncomeValue;
    }

    public String getOic01RedIncomeCurmonTta() {
        return oic01RedIncomeCurmonTta;
    }

    public void setOic01RedIncomeCurmonTta(String oic01RedIncomeCurmonTta) {
        this.oic01RedIncomeCurmonTta = oic01RedIncomeCurmonTta;
    }

    public String getOic01RedIncomeCurmonOntop() {
        return oic01RedIncomeCurmonOntop;
    }

    public void setOic01RedIncomeCurmonOntop(String oic01RedIncomeCurmonOntop) {
        this.oic01RedIncomeCurmonOntop = oic01RedIncomeCurmonOntop;
    }

    public String getOic01RedIncomePyyearTta() {
        return oic01RedIncomePyyearTta;
    }

    public void setOic01RedIncomePyyearTta(String oic01RedIncomePyyearTta) {
        this.oic01RedIncomePyyearTta = oic01RedIncomePyyearTta;
    }

    public String getOic01RedIncomePyyearOntop() {
        return oic01RedIncomePyyearOntop;
    }

    public void setOic01RedIncomePyyearOntop(String oic01RedIncomePyyearOntop) {
        this.oic01RedIncomePyyearOntop = oic01RedIncomePyyearOntop;
    }

    public String getOic01RedContractAdj() {
        return oic01RedContractAdj;
    }

    public void setOic01RedContractAdj(String oic01RedContractAdj) {
        this.oic01RedContractAdj = oic01RedContractAdj;
    }

    public String getOic01RedReconAdj() {
        return oic01RedReconAdj;
    }

    public void setOic01RedReconAdj(String oic01RedReconAdj) {
        this.oic01RedReconAdj = oic01RedReconAdj;
    }

    public String getOic01RedLastyearConAdj() {
        return oic01RedLastyearConAdj;
    }

    public void setOic01RedLastyearConAdj(String oic01RedLastyearConAdj) {
        this.oic01RedLastyearConAdj = oic01RedLastyearConAdj;
    }

    public String getOic01RedLastyearRecAdj() {
        return oic01RedLastyearRecAdj;
    }

    public void setOic01RedLastyearRecAdj(String oic01RedLastyearRecAdj) {
        this.oic01RedLastyearRecAdj = oic01RedLastyearRecAdj;
    }

    public String getOic01RedLastyearAudAdj() {
        return oic01RedLastyearAudAdj;
    }

    public void setOic01RedLastyearAudAdj(String oic01RedLastyearAudAdj) {
        this.oic01RedLastyearAudAdj = oic01RedLastyearAudAdj;
    }

    public String getOic01RedMemo() {
        return oic01RedMemo;
    }

    public void setOic01RedMemo(String oic01RedMemo) {
        this.oic01RedMemo = oic01RedMemo;
    }

    public String getOic01RedIncludeTax() {
        return oic01RedIncludeTax;
    }

    public void setOic01RedIncludeTax(String oic01RedIncludeTax) {
        this.oic01RedIncludeTax = oic01RedIncludeTax;
    }

    public String getOic01RedIncludeNotax() {
        return oic01RedIncludeNotax;
    }

    public void setOic01RedIncludeNotax(String oic01RedIncludeNotax) {
        this.oic01RedIncludeNotax = oic01RedIncludeNotax;
    }

    public String getPsf01DisctContractText() {
        return psf01DisctContractText;
    }

    public void setPsf01DisctContractText(String psf01DisctContractText) {
        this.psf01DisctContractText = psf01DisctContractText;
    }

    public String getPsf01DisctContractValue() {
        return psf01DisctContractValue;
    }

    public void setPsf01DisctContractValue(String psf01DisctContractValue) {
        this.psf01DisctContractValue = psf01DisctContractValue;
    }

    public String getPsf01DisctSumMoney() {
        return psf01DisctSumMoney;
    }

    public void setPsf01DisctSumMoney(String psf01DisctSumMoney) {
        this.psf01DisctSumMoney = psf01DisctSumMoney;
    }

    public String getPsf01DisctContractAdj() {
        return psf01DisctContractAdj;
    }

    public void setPsf01DisctContractAdj(String psf01DisctContractAdj) {
        this.psf01DisctContractAdj = psf01DisctContractAdj;
    }

    public String getPsf01DisctReconAdj() {
        return psf01DisctReconAdj;
    }

    public void setPsf01DisctReconAdj(String psf01DisctReconAdj) {
        this.psf01DisctReconAdj = psf01DisctReconAdj;
    }

    public String getPsf01DisLastyearConAdj() {
        return psf01DisLastyearConAdj;
    }

    public void setPsf01DisLastyearConAdj(String psf01DisLastyearConAdj) {
        this.psf01DisLastyearConAdj = psf01DisLastyearConAdj;
    }

    public String getPsf01DisLastyearRecAdj() {
        return psf01DisLastyearRecAdj;
    }

    public void setPsf01DisLastyearRecAdj(String psf01DisLastyearRecAdj) {
        this.psf01DisLastyearRecAdj = psf01DisLastyearRecAdj;
    }

    public String getPsf01DisLastyearAudAdj() {
        return psf01DisLastyearAudAdj;
    }

    public void setPsf01DisLastyearAudAdj(String psf01DisLastyearAudAdj) {
        this.psf01DisLastyearAudAdj = psf01DisLastyearAudAdj;
    }

    public String getPsf01DisctMemo() {
        return psf01DisctMemo;
    }

    public void setPsf01DisctMemo(String psf01DisctMemo) {
        this.psf01DisctMemo = psf01DisctMemo;
    }

    public String getPsf01DisctIncludeTax() {
        return psf01DisctIncludeTax;
    }

    public void setPsf01DisctIncludeTax(String psf01DisctIncludeTax) {
        this.psf01DisctIncludeTax = psf01DisctIncludeTax;
    }

    public String getPsf01DisctIncludeNotax() {
        return psf01DisctIncludeNotax;
    }

    public void setPsf01DisctIncludeNotax(String psf01DisctIncludeNotax) {
        this.psf01DisctIncludeNotax = psf01DisctIncludeNotax;
    }
}
