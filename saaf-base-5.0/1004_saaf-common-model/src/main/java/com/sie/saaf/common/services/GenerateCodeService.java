package com.sie.saaf.common.services;

import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2018/6/11.
 */
@Component
public class GenerateCodeService {
  @Autowired
  private GenerateCodeServer generateCodeServer;
  private static final Integer TWO = 2;  // 序列长度3
  private static final Integer THREE = 3;  // 序列长度3
  private static final Integer FOUR = 4;  // 序列长度4
  private static final Integer FIVE = 5;  // 序列长度5
  private static final Integer SIX = 6;  // 序列长度6
  private static final Integer SEVEN = 7;  // 序列长度7
  private static final Integer NINE = 9;  // 序列长度9


  /******************************惠科开始************************************/
  //收款编号
  private static final String RECEIPTORDER_CODE_PREFIX = "AR"; // 编号前缀

  //信用申请前缀
  private static final String CRDAPPLY_CODE_PREFIX = "OA"; // 编号前缀

  //返厂维修工单前缀
  private static final String BILL_CODE_PREFIX = "WG"; // 编号前缀
  //返厂维修申请前缀
  private static final String FCSQ_CODE_PREFIX = "FC"; // 编号前缀
  //    客户信息前缀
  private static final String CUSTOMER_CODE_PREFIX = "CUS"; // 编号前缀
  //    收货地址前缀
  private static final String ADDRESS_CODE_PREFIX = "SHDZ"; // 编号前缀
  //发货通知单编号
  private static final String SHIPORDER_CODE_PREFIX = "DE";//编号前缀
  // 发货通知单编号(内销渠道)
  private static final String SHIP_DO_CODE_PREFIX = "FM";//编号前缀
  //出货通知书编号
  private static final String SHIP_CODE_PREFIX = "SO";//编号前缀
  //退货通知书编号
  private static final String RETURN_ADVICE_CODE_PREFIX = "ST";//编号前缀
  //退货通知单编号
  private static final String RETURN_NOTE_CODE_PREFIX = "DT";//编号前缀

  //标准模板申请前缀
  private static final String STD_APPLY_HEADER_PREFIX = "EA";
  private static final String STD_APPLY_HEADER_ONLY = "EA_STD_APPLY";
  //独家协议前缀
  private static final String SOLE_NON_STANDAR_PREFIX = "NEA";
  //独家协议前缀
  private static final String SSAA_CONTRACT_SPECIAL_PREFIX = "SAA";
  private static final String SAA_CONTRACT_SPECIAL = "SAA_CONTRACT_SPECIAL";

  //合同领用
  private static final String TCRH_PREFIX = "TTA";
  private static final String TCRH_FIX_STRING = "V1";
  private static final String TCRH_IDENTIFICATION = "TTA_CONTRACT_RECORD_HEADER";
  //TTA 补充协议
  private static final String SA_NON_STANDAR_PREFIX = "NSA";
  private static final String SOLE_NON_STANDAR_ONLY = "EA_NON_STANDAR";
  private static final String SA_NON_STANDAR_ONLY = "EA_SA_NON_STANDAR";
  //（UDA，售价，成本）修改接口：0018110801
  private static final String PART_PLAN_CODE_PREFIX = "00";
  // 返还发货计划
  private static final String RETURN_PLAN_CODE_PREFIX = "PJ";
  // 维修费用结算编号
  private static final String MAINTENANCE_CHARGE_BILL_CODE_PREFIX = "WXJS";
  // 物流费用结算编号
  private static final String LOGISTICS_CHARGE_BILL_CODE_PREFIX = "WLJS";
  // 售后服务工单
  private static final String SERVICE_BILL_CODE_PREFIX = "FW";
  // 运输线路维护
  private static final String LG_ROUTE_INFO_ROUTE_CODE = "LX";
  // 生产单号M
  private static final String ORDER_CODEM = "M";
  // 生产单号P
  private static final String ORDER_CODEP = "P";
  //回访批次编号
  private static final String RETURN_VISIT_BATCH_CODE = "HF";
  //出库单号
  private static final String OUT_WAREHOUSE_CODE = "CK";
  //入库单号
  private static final String IN_WAREHOUSE_CODE = "RK";
  //移库单
  private static final String SHIFT_WAREHOUSE_CODE = "YK";
  //计划生产单
  private static final String PLN_PRODUCE_CODE = "M";

  //订舱反馈单
  private static final String FEEDBACK_ORDER_CODE = "FK";

  //经销商费用申请
  private static final String FEE_APPLY_CODE_DAA = "DAA";
  //费用申请(售后)
  private static final String FEE_APPLY_CODE_FS = "FS";
  //海关发票
  private static final String FCI_CUSTOMS_CODE = "BI";
  //设备管理(售后)
  private static final String BILL_NUM = "SB";
  //政策申请号
  private static final String POLICY_APPLY_CODE_PREFIX = "Z";
  //物流费用结算批
  private static final String FEE_BATCH_CODE_PREFIX = "FB";

  private static final String NOTICE_RELEASE_NUMBER = "NOTICE";


  // 返回实例：CUS0001
  public String getCusCode(String type) throws Exception {
    return generateCodeServer.getCustomerCode(type, SEVEN);
  }

  public String getAddressCode() {
    return generateCodeServer.getGenerateCode(ADDRESS_CODE_PREFIX, FOUR, false, true);
  }

  //返回实例：DE1801000001
  public String getShipOrderCode() {
    return generateCodeServer.generateCode(SHIPORDER_CODE_PREFIX, new SimpleDateFormat("yyMM"), SIX);
//        return generateCodeServer.getGenerateCode(SHIPORDER_CODE_PREFIX, SIX, true, true);
  }

  //返回实例：FN1801000001
  public String getShipDomesticCode() {
    return generateCodeServer.generateCode(SHIP_DO_CODE_PREFIX, new SimpleDateFormat("yyMM"), FIVE);
  }

  //返回实例：SO180600001
  public String getShipCode() {
    return generateCodeServer.generateCode(SHIP_CODE_PREFIX, new SimpleDateFormat("YYYY"), FOUR);
//                getGenerateCode(SHIP_CODE_PREFIX, FIVE, true, true);
  }


  //返回实例：ST1801000001
  public String getReturnAdviceCode() {
    return generateCodeServer.generateCode(RETURN_ADVICE_CODE_PREFIX, new SimpleDateFormat("yyMM"), FIVE);
  }

  //返回实例：DT1801000001
  public String getReturnNoteCode() {
    return generateCodeServer.generateCode(RETURN_NOTE_CODE_PREFIX, new SimpleDateFormat("yyMM"), FIVE);
  }

  //（UDA，售价，成本）修改接口：0018110801
  public String getPartPlanCode() {
    return generateCodeServer.geRequestIdCode(FOUR, true, true);
  }

  // 修改单号
  public String getEcoPlanCode() {
    return generateCodeServer.geEcoNoCode(FIVE, true, true);
  }

  //返回实例：PJ18110801
  public String getReturnPlanCode() {
    return generateCodeServer.getGenerateCode(RETURN_PLAN_CODE_PREFIX, TWO, true, true);
  }

  //返回实例 WXJS201811120001
  public String getMaintenanceChargeBillCode() {
    return generateCodeServer.getGenerateCode(MAINTENANCE_CHARGE_BILL_CODE_PREFIX,
      FOUR, true, true);
  }

  //返回实例 WLJS201811120001
  public String getLogisticsChargeBillCode() {
    return generateCodeServer.getGenerateCode(LOGISTICS_CHARGE_BILL_CODE_PREFIX,
      FOUR, true, true);
  }

  public String getServiceBillCode() {
    return generateCodeServer.getGenerateCode(SERVICE_BILL_CODE_PREFIX,
      FOUR, true, true);
  }

  public String getReturnVisitBatchCode() {
    return generateCodeServer.getGenerateCode(RETURN_VISIT_BATCH_CODE,
      FOUR, true, true);
  }

  //生成出库单 CKD2018120100001
  public String getCKCode() {
    return generateCodeServer.getGenerateCode(OUT_WAREHOUSE_CODE, FIVE, true, true);
  }

  //生成入库单 RKD2018120100001
  public String getRKCode() {
    return generateCodeServer.getGenerateCode(IN_WAREHOUSE_CODE, FIVE, true, true);
  }

  //生成移库单 YKD2018120100001
  public String getYKCode() {
    return generateCodeServer.getGenerateCode(SHIFT_WAREHOUSE_CODE, FIVE, true, true);
  }

  // 计划生成单：P000000001
  public String getPlnProduceCode() {
    return generateCodeServer.getGenerateCode(PLN_PRODUCE_CODE, NINE, false, true);
  }

  // 经销商费用申请：DAA20181119001
  public String getFeeApplyCodeDaa() {
    return generateCodeServer.getGenerateCode(FEE_APPLY_CODE_DAA, THREE, true, true);
  }

  // 费用申请(售后)：FS201811200001
  public String getFeeApplyCodeFs() {
    return generateCodeServer.getGenerateCode(FEE_APPLY_CODE_FS, FOUR, true, true);
  }

  // 设备管理：SB 201811200001
  public String getBillNum() {
    return generateCodeServer.getGenerateCode(BILL_NUM, FOUR, true, true);
  }

  // 海关发票单号:BI110100001
  public String getFciCustomsCode() {
    return generateCodeServer.generateCode(FCI_CUSTOMS_CODE, new SimpleDateFormat("yyMM"), FIVE);
  }


  /*******************************惠科结束**********************************/

  private static final String PROJECT_CODE_PREFIX = "SJ"; // 商机报备编号前缀

  private static final String GCBJ_PREFIX = "P"; // 工程报价编号前缀===返回实例：P201806110004

  public String getProjectPriceCode() {
    return generateCodeServer.getGenerateCode(GCBJ_PREFIX, FOUR, true, true);
  }

  private static final String GCHX_PREFIX = "HX"; // 工程核销编号前缀===返回实例：HX201806110004

  public String getProjectHxCode() {
    return generateCodeServer.getGenerateCode(GCHX_PREFIX, FOUR, true, true);
  }

  private static final String IOK_PREFIX = "IOK"; //出入库单号前缀===返回实例：IOK201806110004

  public String getIokCode() {
    return generateCodeServer.getGenerateCode(IOK_PREFIX, FOUR, true, true);
  }

  private static final String ZZTC_PREFIX = "ZZTC";//专职导购提成编码前缀===返回实例：ZZTC20180611004

  public String getZZTCCode() {
    return generateCodeServer.getGenerateCode(ZZTC_PREFIX, THREE, true, true);
  }

  private static final String DMTC_PREFIX = "DMTC";//代卖导购提成编码前缀===返回实例：DMTC201806110004

  public String getDMTCCode() {
    return generateCodeServer.getGenerateCode(DMTC_PREFIX, FOUR, true, true);
  }

  //根据信用证编号
  private static final String DELI_BIL_CODE = "LC";


  //OA限额
  private static final String QUOTA_CODE_PREFIX = "OA"; // 编号前缀


  //通知代办
  private static final String SAAF_MESSAGE_PUSH = "SMP";

  public String getSaafMessagePush() {
    return generateCodeServer.getGenerateCode(SAAF_MESSAGE_PUSH, FIVE, true, true);
  }

  //根据信用证编号
  private static final String LC_MODIFY_CODE = "-M";
  //根据信用证编号
  private static final String LC_SPECIAL_CODE = "-T";

  //门店编号
  private static final String STORE_CODE_PREFIX = "KT"; // 编号前缀

  //PROPOSAL编号
  private static final String PROPOSAL_CODE_PREFIX = ""; // 编号前缀

  // 销售预测单号
  private static final String SALS_CODE_PREFIX = "YC"; // 编号前缀
  //客户申请编码
  private static final String CUSTAPPLY_CODE_PREFIX = "SQC"; // 编号前缀
  //客户编码
  private static final String CUST_CODE_PREFIX = "C"; // 编号前缀
  //特价申请编码
  private static final String PRICE_CODE_PREFIX = "TJ";// 编号前缀
  //工程报价编码
  private static final String PRICEGC_CODE_PREFIX = "GJ";// 编号前缀
  //仓库编码
  private static final String WAREHOUSE_CODE_PREFIX = "CK";// 编号前缀
  // 授信额度单号
  private static final String CREDIT_CODE_PREFIX = "SCED"; // 编号前缀
  private static final String CREDIT_CODE_PREFIX1 = "CED"; // 编号前缀
  // OEM订单号
  private static final String OEM_CODE_PREFIX = "OEM"; // 编号前缀
  // 调账申请号
  private static final String TZ_CODE_PREFIX = "TZ"; // 编号前缀


  //发票申请编码
  private static final String APPLY_CODE_PREFIX = "CI";// 编号前缀

  //收款单号
  private static final String RECEIPT_CODE_PREFIX = "SK";//编号前缀

  // 杂项订单号
  private static final String SUNDRY_CODE_PREFIX = "ZX"; // 编号前缀
  // 经销商库存单据单号
  private static final String DEALER_SUNDRY_CODE_PREFIX = "KC"; // 编号前缀

  //提货申请编码
  private static final String PICKING_CODE_PREFIX = "XS";
  //退货申请编码
  private static final String RETURN_CODE_PREFIX = "TH";
  //调拨申请单号
  private static final String ALLOT_CODE_PREFIX = "DB";
  //客户间转款申请单据号
  private static final String TRANSFER_CODE_PREFIX = "ZK";// 编号前缀

  //返利上账单
  private static final String REBATEORDER_CODE_PREFIX = "FL";//编号前缀

  //价格列表编号自动生成
  private static final String LIST_CODE_PREFIX = "JL"; // 编号前缀

  //价格调整编号自动生成
  private static final String ADJUST_CODE_PREFIX = "JT"; // 编号前缀

  //价格调整编号自动生成
  private static final String FILL_WAREHOUSE_PREFIX = "TC"; // 编号前缀

  //专项销售目标编号自动生成
  private static final String SPECIAL_TARGET_PREFIX = "ZXXS"; // 编号前缀

  //报价单号编号自动生成
  private static final String SOPIHEADERS_TARGET_PREFIX = "PI"; // 编号前缀

  //售后编码自动生成
  private static final String BILLNUM_TARGET_PREFIX = "PS";

  //物流配送单单据编码
  private static final String CS_LG_BILL_BILLNUM_TARGET_PREFIX = "W";

  //订舱申请单编码
  private static final String BOOKING_ORDER_APPLY = "HS";

  //预测汇总预测单号编码
  private static final String PLN_FORECAST_SUM_HEADERS = "PFSH";
  //客户事件单号
  private static final String CS_CUSTOMER_EVENT = "KS";

  //运力任务编号前缀
  private static final String LG_TRANSPORT_TASK_PREFIX = "LT";

  //运输实体编号前缀
  private static final String LG_TRANSPORT_ENTITY_PREFIX = "LE";

  //产品卖点尺寸编号前缀
  private static final String PUB_PRODUCT_ITEMSIZE_PREFIX = "MD";


  /****************TTA SYSTEM 前缀常量 start*******************************/
  //proposal补充协议单据号前缀
  private static final String PROPOSAL_SUPPLEMENT_PREFIX = "SA";
  //独家协议单据号前缀
  private static final String EXCLUSIVE_PREFIX = "EB";

  private static final String SPLIT_MERGE_PREFIX = "SM";


  /****************TTA SYSTEM 前缀常量 end *******************************/

  /****************PLM SYSTEM 前缀常量 start*******************************/
  private static final String PLM_PROJECT_NUMBER = "XM";

  private static final String PLM_OB_DEVELOPMENT = "OB";

  private static final String PLM_PRODUCT_EXCEPTION = "YC";


  /****************PLM SYSTEM 前缀常量 end*******************************/


  //“MD”+YYMMDD+4位流水码（按年月重置流水码）
  public String getPubsellingItemSizeCode() {
    return generateCodeServer.getGenerateCodeCustCode(PUB_PRODUCT_ITEMSIZE_PREFIX, FOUR, true, true);
  }


  //    返回实例:ZXXS201806110004
  public String getSpecialTargetCode() {
    return generateCodeServer.getGenerateCode(SPECIAL_TARGET_PREFIX, FOUR, true, true);
  }

  // 返回实例：PJ201806110004
  public String getFillWarehouseCode() {
    return generateCodeServer.getGenerateCode(FILL_WAREHOUSE_PREFIX, FOUR, true, true);
  }

  // 返回实例：PJ201806110004
  public String getProjectCode() {
    return generateCodeServer.getGenerateCode(PROJECT_CODE_PREFIX, FOUR, true, true);
  }

  // 返回实例：KT20180611001
  public String getStoreCode() {
    return generateCodeServer.getGenerateCode(STORE_CODE_PREFIX, THREE, true, true);
  }

  // 返回实例：YC201806110004
  public String getSalsCode() {
    return generateCodeServer.getGenerateCode(SALS_CODE_PREFIX, FOUR, true, true);
  }

  // 返回实例：SQC20180611004
  public String getCustomerApplyCode() {
    return generateCodeServer.getGenerateCode(CUSTAPPLY_CODE_PREFIX, THREE, true, true);
  }

  // 返回实例：C20180701004
  public String getCustomerCode() {
    return generateCodeServer.getGenerateCode(CUST_CODE_PREFIX, THREE, true, true);
  }

  // 返回实例：TJ18061500001
  public String getPriceStrucetureCode() {
    return generateCodeServer.getGenerateCode(PRICE_CODE_PREFIX, FIVE, true, true);
  }

  // 返回实例：GC18061500001
  public String getPriceStrucetureGcCode() {
    return generateCodeServer.getGenerateCode(PRICEGC_CODE_PREFIX, FIVE, true, true);
  }

  // 返回实例：CK201806150001
  public String getWarehouseCode() {
    return generateCodeServer.getGenerateCode(WAREHOUSE_CODE_PREFIX, FOUR, true, true);
  }

  // 返回实例：SCED20180519001
  public String getCreditCode() {
    return generateCodeServer.getGenerateCode(CREDIT_CODE_PREFIX, THREE, true, true);
  }

  // 返回实例：CED20180519001
  public String getCredit1Code() {
    return generateCodeServer.getGenerateCode(CREDIT_CODE_PREFIX1, THREE, true, true);
  }

  // 返回实例：OEM20180519001
  public String getOEMCode() {
    return generateCodeServer.getGenerateCode(OEM_CODE_PREFIX, THREE, true, true);
  }

  // 返回实例：TZ2018051900001
  public String getTJCode() {
    return generateCodeServer.getGenerateCode(TZ_CODE_PREFIX, FIVE, true, true);
  }

  //返回开票申请编码实例:CI+“YYMM”+5位流水（按月重置流水）
  public String getApplyCode() {
    return generateCodeServer.getApplyCodeGenerateCode(APPLY_CODE_PREFIX, FIVE, true, true);
  }

  //政策申请号:Z+“YYMM”+3位流水
  public String getPolicyCode() {
    return generateCodeServer.getApplyCodeGenerateCode(POLICY_APPLY_CODE_PREFIX, THREE, true, true);
  }

  //返回收款单编码实例:SK20180519001
  public String getReceiptCode() {
    return generateCodeServer.getGenerateCode(RECEIPT_CODE_PREFIX, THREE, true, true);
  }

  // 返回实例：ZX20180519001
  public String getSundryCode() {
    return generateCodeServer.getGenerateCode(SUNDRY_CODE_PREFIX, THREE, true, true);
  }

  // 返回实例：KC1810210001
  public String getDealerSundryCode() {
    return generateCodeServer.getGenerateCode(DEALER_SUNDRY_CODE_PREFIX, FOUR, true, true);
  }

  // 返回实例：XS20180519001
  public String getPickingCode() {
    return generateCodeServer.getGenerateCode(PICKING_CODE_PREFIX, FIVE, true, true);
  }

  // 返回实例：TH20180519001
  public String getReturnCode() {
    return generateCodeServer.getGenerateCode(RETURN_CODE_PREFIX, FIVE, true, true);
  }

  //返回实例：ZK20180519001
  public String getTransferCode() {
    return generateCodeServer.getGenerateCode(TRANSFER_CODE_PREFIX, THREE, true, true);
  }

  //返回实例：ZK201805190001
  public String getTransferApplyCode() {
    return generateCodeServer.getGenerateCode(TRANSFER_CODE_PREFIX, FOUR, true, true);
  }

  // 调拨单
  public String getAllotCode() {
    return generateCodeServer.getGenerateCode("TB", FIVE, true, true);
  }

  //返回实例：FL201806250001
  public String getRebateOrderCode() {
    return generateCodeServer.getGenerateCode(REBATEORDER_CODE_PREFIX, FOUR, true, true);
  }

  //返回实例：JL18062500001
  public String getPriceListCode() {
    return generateCodeServer.getGenerateCode(LIST_CODE_PREFIX, FIVE, true, true);
  }

  //返回实例：JT18062500001
  public String getPriceAdjustCode() {
    return generateCodeServer.getGenerateCode(ADJUST_CODE_PREFIX, FIVE, true, true);
  }

  //“PI”+YYMM+5位流水码（按月重置流水码）
  public String getSoPiHeadersCode() {
    return generateCodeServer.getGenerateCodeCustCode(SOPIHEADERS_TARGET_PREFIX, FIVE, true, true);
  }


  //信用证修改件编码  原件编号 + '-M' + 2位流水(如: 01、02)
  public String getCreditCode(String creditCode) {
    return generateCodeServer.getCreditCodeCustCode(creditCode, LC_MODIFY_CODE, TWO, true, true);
  }

  //信用证特批件 原件编号 + '-T' + 2位流水(如: 01、02)
  public String getSpecialCode(String creditCode) {
    return generateCodeServer.getSpecialCodeCustCode(creditCode, LC_SPECIAL_CODE, TWO, true, true);
  }


  //“PS”+年月日+4位流水码
  public String getBillNumCode() {
    return generateCodeServer.getGenerateCode(BILLNUM_TARGET_PREFIX, FOUR, true, true);
  }

  //“KS”+年月日+4位流水码
  public String getCsCustomerCode() {
    return generateCodeServer.getGenerateCode(CS_CUSTOMER_EVENT, FOUR, true, true);
  }

  //“W”+年月日+4位流水码
  public String getCsLgBillBillNumCode() {
    return generateCodeServer.getGenerateCode(CS_LG_BILL_BILLNUM_TARGET_PREFIX, FOUR, true, true);
  }

  //1.OA限额申请单号有系统自动生成: （编码规则“OA”+”年月日”+“4位流水码”） 如:OA201809120001；
  public String getOaQuotaHeadersCode() {
    return generateCodeServer.getGenerateCode(QUOTA_CODE_PREFIX, FOUR, true, true);
  }

  //EA +年月(yyyyMM) + 4位流水号    ======>模块 独家协议
  //EA2019070001
  public String getstdApplyHeaderCode() {
    return generateCodeServer.getComApplyCodeGenerateCode(STD_APPLY_HEADER_PREFIX,STD_APPLY_HEADER_ONLY, FOUR, true, true);
  }
  //EA +年月(yyyyMM) + 4位流水号    ======>模块 独家协议
  //EA2019070001
  public String getSoleNonStandarCode() {
    return generateCodeServer.getComApplyCodeGenerateCode(SOLE_NON_STANDAR_PREFIX,SOLE_NON_STANDAR_ONLY, FOUR, true, true);
  }
  //SAA +年月(yyyyMM) + 4位流水号    ======>模块 合同审核特批申请
  //SAA2019070001
  public String getTtaContractSpecialHeaderCode() {
    return generateCodeServer.getComApplyCodeGenerateCode(SSAA_CONTRACT_SPECIAL_PREFIX,SAA_CONTRACT_SPECIAL, FOUR, true, true);
  }

  //TTA+年份+ V1+ 6位流水号    ======>模块 合同领用
  //SAA2019070001
  public String getTtaContractRecordHeaderCode() {
    return generateCodeServer.getTtaContractRecordGenerateCode(TCRH_PREFIX,TCRH_IDENTIFICATION,TCRH_FIX_STRING, SIX, true, true);
  }
  //EA +年月(yyyyMM) + 4位流水号    ======>模块 补充协议
  //EA2019070001
  public String getSaNonStandarCode() {
    return generateCodeServer.getComApplyCodeGenerateCode(SA_NON_STANDAR_PREFIX,SA_NON_STANDAR_ONLY, FOUR, true, true);
  }

  // HS +年月日”YYMM”+5位流水码
  public String getLgBoHeadersCode() {
    return generateCodeServer.generateCode(BOOKING_ORDER_APPLY, new SimpleDateFormat("yyMM"), FIVE);
  }

  //商业发票信用证交单号 LC+YYYYMM+4位流水
  public String getDeliBillHeadersCode() {
    return generateCodeServer.getGenerateCode(DELI_BIL_CODE, FOUR, true, true);
  }

  // 预测汇总单号  PFSH +年月日”YYYYMMDD”+4位流水码
  public String getPlnForecastSumHeadersCode() {
    return generateCodeServer.getGenerateCode(PLN_FORECAST_SUM_HEADERS, FOUR, true, true);
  }

  // 运输线路维护  LX +年月日”YYYYMMDD”+4位流水码
  public String getLgRouteInfoRouteCode() {
    return generateCodeServer.getGenerateCode(LG_ROUTE_INFO_ROUTE_CODE, FOUR, true, true);
  }

  // 生产单号  M+9位流水
  public String getPickingApplyCode() {
    return generateCodeServer.getGenerateCodePickingApply(ORDER_CODEM, FIVE, true, true);
  }

  // 生产单号  M+9位流水
  public String getPickingApplyCodeM() {
    return generateCodeServer.getGenerateCodePickingApply(ORDER_CODEM, NINE, false, true);
  }

  // 生产单号  P+9位流水
  public String getPickingApplyCodeP() {
    return generateCodeServer.getGenerateCodePickingApply(ORDER_CODEP, NINE, false, true);
  }
  //客户单号 大货订单: 年月(4位)+事业部识别码(2位)+业务代码(2位)+流水码(5位)

  public String getBNrBigGoodsOrderFlagY(String buCode, String salesCode) {
    return generateCodeServer.getGenerateBNCodeBigGoodsOrderFlagY(buCode, salesCode, FIVE, true, true);
  }

  // 客户单号 非大货订单: 事业部识别码(2位)+业务代码(2位)+ ‘-’ + 流水码(6位)
  public String getBNrBigGoodsOrderFlagN(String buCode, String salesCode) {

    return generateCodeServer.getGenerateBNCodeBigGoodsOrderFlagN(buCode, salesCode, SIX, false, true);
  }

  //“FCSQ”+年月日+4位流水码
  public String getcsReturnApplyCode() {
    return generateCodeServer.getGenerateCode(FCSQ_CODE_PREFIX, FOUR, true, true);
  }
  //“code”+年月日+4位流水码
  public String getReportProcessCode(String code ) {
    return generateCodeServer.getGenerateCode(code, FOUR, true, true);
  }

  //“LT”+年月日+6位流水码 运力任务编号
  public String getLgTransportTaskCode() {
    return generateCodeServer.getGenerateCode(LG_TRANSPORT_TASK_PREFIX, SIX, true, true);
  }

  //“L”+年月日+4位流水码 运输实体编号
  public String getLgTransportEntityCode() {
    return generateCodeServer.getGenerateCode(LG_TRANSPORT_ENTITY_PREFIX, FOUR, true, true);
  }

  // WG+年月日+3位流水号
  public String getCsReturnRepareBillCode() {
    return generateCodeServer.getGenerateCode(BILL_CODE_PREFIX, THREE, true, true);
  }

  // 商业发票放单流水号：商业发票号”+F+2位流水 14
  public String getInvReleaseApplyCode(String invoiceNumber) {

    return generateCodeServer.getInvReleaseApplyCode(invoiceNumber, TWO);
  }

  // 销售单号 年月+2位字母（TV/MNT）+2位业务员编码+5位流水
  public String getGenerateSalesOrderCode(String buCode, String salesCode) {

    return generateCodeServer.getGenerateSalesOrderCode(buCode, salesCode, FIVE, true, true);
  }

  // 采购单号 年月+4位流水
  public String getPurchaseOrderNumber() {
    return generateCodeServer.getPurchaseOrderNumber();
  }

  //船务费用编号返回实例："Y'+年月+4位流水号
  public String getFeeShipApplyCode() {
    return generateCodeServer.getFeeShipApplyCode();
  }

  //返回实例：FK1801000001
  public String getFeedbackOrderCode() {
    return generateCodeServer.generateCode(FEEDBACK_ORDER_CODE, new SimpleDateFormat("yyMM"), FIVE);
  }

  //信用申请，申请单号“OA”+“YYYYMM”+4位流水
  public String getCrdApplyCode() {
    return generateCodeServer.getGenerateCode(CRDAPPLY_CODE_PREFIX, FOUR, true, true);
  }

  //收款单号，申请单号“AR”+“YYMM”+5位流水
  public String getReceiptOrderCode() {
    return generateCodeServer.getGenerateCode(RECEIPTORDER_CODE_PREFIX, FIVE, true, true);
  }

  //物流结算批编号“FB”+“YYYYMM”+4位流水
  public String getFeeBatchCode() {
    return generateCodeServer.generateCode(FEE_BATCH_CODE_PREFIX, new SimpleDateFormat("yyyyMM"), FOUR);
  }


  //  返回实例：P201900010001
  public String getSupplierCode(String deptCode) {
    return generateCodeServer.generateIncodeCode("P", new SimpleDateFormat("yyyy"), deptCode, FOUR);
//                getGenerateCode(SHIP_CODE_PREFIX, FIVE, true, true);
  }

  //******************************TTA System start************************************************//
  //“”+YYMM+3位流水码（按年月重置流水码）
  public String getTtaNppReport(String ttaNppReport) {
    return generateCodeServer.getApplyCodeGenerateCode(ttaNppReport, THREE, true, true);
  }

  //“”+YYMM+3位流水码（按年月重置流水码）System_Current导入批次使用
  public String getTtaNppImportBatch(String ttaNppImport) {
    return generateCodeServer.getApplyCodeGenerateCode(ttaNppImport, THREE, true, true);
  }

  //“”+YYMM+3位流水码（按年月重置流水码）
  public String getTtaFGReport(String ttaFg) {
    return generateCodeServer.getApplyCodeGenerateCode(ttaFg, THREE, true, true);
  }

  //“”+YYMMDD+4位流水码（按年月重置流水码）
  public String getProposalSizeCode() {
    return generateCodeServer.getGenerateCode(PROPOSAL_CODE_PREFIX, FOUR, true, true);
  }

  //“”+YYMM+3位流水码（按年月重置流水码）
  public String getTtaOiCw(String ttaOiCw) {
    return generateCodeServer.getApplyCodeGenerateCode(ttaOiCw, THREE, true, true);
  }

  public String getProposalCodeTwo(String proposalCode) {
    return generateCodeServer.getGenerateCode(proposalCode, TWO, false, true);
  }

  // SA + 年月(YYYYMM) + 4位流水码   ====>模块:proposal补充协议
  //返回实例:SA2019070001
  public String getProposalSupplementCode() {
    return generateCodeServer.getApplyCodeGenerateCode(PROPOSAL_SUPPLEMENT_PREFIX, FOUR, true, true);
  }

  //EB +年月(yyyyMM) + 4位流水号    ======>模块 独家协议
  //EB2019070001
  public String getExclusiveCode() {
    return generateCodeServer.getApplyCodeGenerateCode(EXCLUSIVE_PREFIX, FOUR, true, true);
  }

  //SM +年月(yyyyMM) + 4位流水号    =======>模块:proposal拆分与合并
  public String getSplitMergePrefix() {
    return generateCodeServer.getApplyCodeGenerateCode(SPLIT_MERGE_PREFIX, FOUR, true, true);
  }

  //freeGoods导入批次号:FB +年月(yyyyMM) + 4位流水号
  public String getFeeGoodBatchCode() {
    return generateCodeServer.generateCode(FEE_BATCH_CODE_PREFIX, new SimpleDateFormat("yyyyMMdd"), FOUR);
  }

  public String getRefreshBrandBatchCode(){
    return generateCodeServer.generateCode("RE",new SimpleDateFormat("yyyyMMdd"),FOUR);
  }

  //通用的单据编号,规则:
  public String getTTAGeneralCode(String key){
    return generateCodeServer.getGenerateCode(key, FOUR, true, true);
  }
  //**********************************TTA System end**********************************************************//

  //******************************PLM System start************************************************//
  //返回实例：XM201900001
  public String getPlmProjectNumber() {
    return generateCodeServer.generateCode(PLM_PROJECT_NUMBER, new SimpleDateFormat("yy"), FIVE);
  }

  //返回实例：OB201900001
  public String getPlmObDevelopment() {
    return generateCodeServer.generateCode(PLM_OB_DEVELOPMENT, new SimpleDateFormat("yy"), FIVE);
  }

  public String getPlmProductException() {
    return generateCodeServer.generateCode(PLM_PRODUCT_EXCEPTION, new SimpleDateFormat("yy"), FIVE);
  }

  // 生成公告流水号+
  public String getNoticeNumber() {
    return generateCodeServer.generateCode(NOTICE_RELEASE_NUMBER, new SimpleDateFormat("yyyyMMdd"), FOUR);
  }

  //******************************PLM System end************************************************//

}
