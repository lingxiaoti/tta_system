package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.BigDecimalUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.utils.Util;
import com.sie.watsons.base.report.model.entities.TtaIrTermsEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaIrTermsEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaIrTerms;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly.TtaUserGroupDeptBrandEntity_HI_RO;
import com.yhg.base.utils.DateUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ttaIrTermsServer")
public class TtaIrTermsServer extends BaseCommonServer<TtaIrTermsEntity_HI> implements ITtaIrTerms {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaIrTermsServer.class);

    @Autowired
    private BaseCommonDAO_HI<TtaIrTermsEntity_HI> ttaIrTermsDAO_HI;
    @Autowired
    private ViewObject<TtaReportHeaderEntity_HI> ttaReportHeaderDAO_HI;
    @Autowired
    private JedisCluster jedisCluster;


    @Autowired
    private BaseViewObject<TtaIrTermsEntity_HI_RO> ttaIrTermsDAO_HI_RO;

    @Autowired
    private BaseViewObject<TtaUserGroupDeptBrandEntity_HI_RO> ttaUserGroupDeptBrandDAO_HI_RO;

    public TtaIrTermsServer() {
        super();
    }

    @Autowired
    public HttpServletRequest request;

    @SuppressWarnings("all")
    @Override
    public Pagination<TtaIrTermsEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        String deptCode = queryParamJSON.getString("deptCode");
        String endDate = queryParamJSON.getString("endDate");
        if (StringUtils.isBlank(endDate)) {
            endDate = DateUtil.date2Str(new Date(), "yyyyMM");
        } else {
            endDate = endDate.replace("-", "");
        }

        String maxMonthFlagSql = TtaIrTermsEntity_HI_RO.getMaxMonthFlagSql();
        List<Map<String, Object>> maxMonthList = ttaIrTermsDAO_HI.queryNamedSQLForList(maxMonthFlagSql, new HashMap<String, Object>());
        if (maxMonthList == null || maxMonthList.isEmpty()) {
            return  new Pagination<TtaIrTermsEntity_HI_RO>();
        }
        String maxMonth = maxMonthList.get(0).get("maxMonth") +"";
        if (endDate.compareTo(maxMonth) > 0) {
            endDate = maxMonth;//如果用户选择的交易时间大于系统导入数据的日期，则采用最大导入数据的最大日期。
        }
        double mount = Double.parseDouble(endDate.substring(4,6)); //截取月份
        StringBuffer sql = new StringBuffer(TtaIrTermsEntity_HI_RO.getQuerySql());
        Map<String, Object> queryMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(TtaIrTermsEntity_HI_RO.class, queryParamJSON, "tb.order_nbr", "orderNbr", sql, queryMap, "=");
        SaafToolUtils.parperHbmParam(TtaIrTermsEntity_HI_RO.class, queryParamJSON, "tb.vendor_nbr", "vendorNbr", sql, queryMap, "fulllike");
        SaafToolUtils.parperHbmParam(TtaIrTermsEntity_HI_RO.class, queryParamJSON, "tb.vendor_name", "vendorName", sql, queryMap, "fulllike");
        SaafToolUtils.parperHbmParam(TtaIrTermsEntity_HI_RO.class, queryParamJSON, "tb.brand_cn", "brandCn", sql, queryMap, "fulllike");
        if (StringUtils.isNotBlank(deptCode)) {
            sql.append( " and  tb.dept_code  in ('" + String.join("','",  deptCode.split(",") ) + "')");
        }
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql, "count(*)");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tb.order_nbr asc", false);
        Pagination<TtaIrTermsEntity_HI_RO> pagination = ttaIrTermsDAO_HI_RO.findPagination(sql, countSql, queryMap, pageIndex, pageRows);
        if (pagination != null && pagination.getData() != null) {
            List<TtaIrTermsEntity_HI_RO> irList = pagination.getData();
            for (TtaIrTermsEntity_HI_RO entityHiRo : irList) {
                List<Map<String, Object>> list = ttaIrTermsDAO_HI.queryNamedSQLForList(TtaIrTermsEntity_HI_RO.getNetpurchaseSql(entityHiRo.getVendorNbr(), endDate), new HashMap<String, Object>());
                if (list != null && !list.isEmpty()) {
                    Object netpurchase = list.get(0).get("NETPURCHASE");
                    String netpurchaseValue = (netpurchase == null) ? "0" : netpurchase.toString();//折扣前采购额（含税）
                    entityHiRo.setNetpurchase(netpurchaseValue);//折扣前采购额（含税）
                    //预估全年折扣前采购额（含税）,本年度实际采购额“/月份*12
                    Double divide = BigDecimalUtils.divide(Double.parseDouble(entityHiRo.getNetpurchase()) , mount) * 12d;
                    entityHiRo.setPredictNetpurchase(divide.toString());
                    BigDecimal predictNetpurchase = new BigDecimal(entityHiRo.getPredictNetpurchase());
                    int level = 0;
                    w:
                    while (level <= 8) {
                        ++level;
                        if (level == 1) {
                            if (entityHiRo.getFirstRate() == null || entityHiRo.getFirstIncludeTax() == null) {
                                break w;
                            }
                            String firstIncludeTax = entityHiRo.getFirstIncludeTax();
                            String secondIncludeTax = entityHiRo.getSecondIncludeTax();
                            //1.当前第一层值比对：小于第一层，未达到第一层，需要按照未达到目标层计算
                            if (predictNetpurchase.compareTo(new BigDecimal(firstIncludeTax)) < 0) {
                                entityHiRo.setIrPredictRate(null); //预计IR%;
                                entityHiRo.setPassFirst("N");//是否已达第一层;
                                entityHiRo.setPassList("N");//是否已达最后一层
                                entityHiRo.setIrPredictAmount(null);//预计IR$(含税）
                                entityHiRo.setNextIrRate(entityHiRo.getFirstRate()); //下一层目标的IR%
                                entityHiRo.setNextPurchaseAmount(new BigDecimal(firstIncludeTax).subtract(predictNetpurchase) + ""); //为达到下一层目标需追加的采购额 （含税）

                                // (预估全年折扣前采购额（含税）/元 + 为达到下一层目标需追加的采购额 （含税）) * 下一层目标的IR%
                                 //(predictNetpurchase + nextPurchaseAmount) * nextIrRate
                                Double value = BigDecimalUtils.add(Double.parseDouble(entityHiRo.getPredictNetpurchase()), Double.parseDouble(entityHiRo.getNextPurchaseAmount()));
                                value = BigDecimalUtils.divide(value, 100);
                                Double multiply = BigDecimalUtils.multiply(value, Double.parseDouble(entityHiRo.getNextIrRate()));
                                entityHiRo.setAddAffterIrIncludeTaxAmount(multiply + "");//追加后的IR$（含税）
                                entityHiRo.setAddIrIncludeTaxAmount(multiply + "");//IR 增加额（含税）=追加后的IR$（含税） - 预计IR$(含税）

                                //投资回报率=IR增加额/为达到下一层目标需追加的采购额
                                BigDecimal returnRate = new BigDecimal(multiply * 100).divide(BigDecimal.valueOf(Double.parseDouble(entityHiRo.getNextPurchaseAmount())),2, BigDecimal.ROUND_HALF_UP);
                                entityHiRo.setReturnRate(returnRate + ""); //投资回报率=IR增加额/为达到下一层目标需追加的采购额
                                break w;
                            }
                            //2.1当前第一层值比对：第二层有值，且当前值大于等于第一层且小于第二层，需要按照未达到目标层计算。
                            if (secondIncludeTax != null) {
                                //第一层
                                if (predictNetpurchase.compareTo(new BigDecimal(firstIncludeTax)) >= 0 && predictNetpurchase.compareTo(new BigDecimal(secondIncludeTax)) < 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getFirstRate()); //预计IR%;
                                    entityHiRo.setPassFirst("Y");//是否已达第一层;
                                    entityHiRo.setPassList("N");//是否已达最后一层
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    //String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(entityHiRo.getSecondRate()); //下一层目标的IR%
                                    entityHiRo.setNextPurchaseAmount(new BigDecimal(secondIncludeTax).subtract(predictNetpurchase) + ""); //为达到下一层目标需追加的采购额 （含税）
                                    BigDecimal addAffterIrIncludeTaxAmount = new BigDecimal(entityHiRo.getNextIrRate()).multiply(new BigDecimal(entityHiRo.getNextPurchaseAmount()).add(predictNetpurchase));
                                    addAffterIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.divide(new BigDecimal(100));
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(addAffterIrIncludeTaxAmount + "");//追加后的IR$（含税）
                                    BigDecimal addIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.subtract(new BigDecimal(entityHiRo.getIrPredictAmount()));
                                    entityHiRo.setAddIrIncludeTaxAmount(addIrIncludeTaxAmount + "");//IR 增加额（含税）
                                    BigDecimal returnRate = addIrIncludeTaxAmount.multiply(new BigDecimal(100)).divide(new BigDecimal(entityHiRo.getNextPurchaseAmount()), 2, BigDecimal.ROUND_HALF_UP);
                                    entityHiRo.setReturnRate(returnRate + ""); //追加IR占追加采购金额比例(投资回报率)
                                    break w;
                                }
                                //2.2 当前第一层值比对：第二层有值，且当前值大于第二层，继续往上走continue。
                                if (predictNetpurchase.compareTo(new BigDecimal(secondIncludeTax)) > 0) {
                                    continue;
                                }
                            } else {
                                //3.当前第一层值比对：第二层没有值，当前值与第一层比对，如果大于等于第一层，确定属于第一层，并且是最高层。
                                if (predictNetpurchase.compareTo(new BigDecimal(firstIncludeTax)) >= 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getFirstRate()); //预计IR%;
                                    entityHiRo.setPassFirst("Y");//是否已达第一层;
                                    entityHiRo.setPassList("Y");//是否已达最后一层
                                    //String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(null); //下一层目标的IR%
                                    entityHiRo.setNextPurchaseAmount(null); //为达到下一层目标需追加的采购额 （含税）
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(null);//追加后的IR$（含税）
                                    entityHiRo.setAddIrIncludeTaxAmount(null);//IR 增加额（含税）
                                    entityHiRo.setReturnRate(null); //追加IR占追加采购金额比例(投资回报率)
                                    break;
                                }
                            }
                        } else if (level == 2) {
                            String secondIncludeTax = entityHiRo.getSecondIncludeTax(); //getFirstIncludeTax();
                            String thirdIncludeTax = entityHiRo.getThirdIncludeTax(); //getSecondIncludeTax();
                            //2.1当前第二层值比对：第三层有值，且当前值大于等于第二层且小于第三层，需要按照未达到目标层计算。
                            if (thirdIncludeTax != null) {
                                //第二层
                                if (predictNetpurchase.compareTo(new BigDecimal(secondIncludeTax)) >= 0 && predictNetpurchase.compareTo(new BigDecimal(thirdIncludeTax)) < 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getSecondRate()); //预计IR% --需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第二层;
                                    entityHiRo.setPassList("N");//是否已达最后三层
                                    //String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(entityHiRo.getThirdRate()); //下一层目标的IR% --需修改
                                    entityHiRo.setNextPurchaseAmount(new BigDecimal(thirdIncludeTax).subtract(predictNetpurchase) + ""); //为达到下一层目标需追加的采购额 （含税）
                                    BigDecimal addAffterIrIncludeTaxAmount = new BigDecimal(entityHiRo.getNextIrRate()).multiply(new BigDecimal(entityHiRo.getNextPurchaseAmount()).add(predictNetpurchase));
                                    addAffterIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.divide(new BigDecimal(100));
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(addAffterIrIncludeTaxAmount + "");//追加后的IR$（含税）
                                    BigDecimal addIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.subtract(new BigDecimal(entityHiRo.getIrPredictAmount()));
                                    entityHiRo.setAddIrIncludeTaxAmount(addIrIncludeTaxAmount + "");//IR 增加额（含税）
                                    BigDecimal b = new BigDecimal(entityHiRo.getNextPurchaseAmount());
                                    BigDecimal returnRate = addIrIncludeTaxAmount.multiply(new BigDecimal(100)).divide(b, 2, BigDecimal.ROUND_HALF_UP);
                                    entityHiRo.setReturnRate(returnRate + ""); //追加IR占追加采购金额比例(投资回报率)
                                    break w;
                                }
                                //2.2 当前第二层值比对：第三层有值，且当前值大于第三层，继续往上走continue。
                                if (predictNetpurchase.compareTo(new BigDecimal(thirdIncludeTax)) > 0) {
                                    continue;
                                }
                            } else {
                                //3.当前第二层值比对：第三层没有值，当前值与第二层比对，如果大于等于第二层，确定属于第二层，并且是最高层。
                                if (predictNetpurchase.compareTo(new BigDecimal(secondIncludeTax)) >= 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getSecondRate()); //预计IR%; -- 需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第一层;
                                    entityHiRo.setPassList("Y");//是否已达最后一层
                                    //String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(null); //下一层目标的IR%
                                    entityHiRo.setNextPurchaseAmount(null); //为达到下一层目标需追加的采购额 （含税）
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(null);//追加后的IR$（含税）
                                    entityHiRo.setAddIrIncludeTaxAmount(null);//IR 增加额（含税）
                                    entityHiRo.setReturnRate(null); //追加IR占追加采购金额比例(投资回报率)
                                    break;
                                }
                            }
                        } else if (level == 3) {
                            String fourthIncludeTax = entityHiRo.getFourthIncludeTax(); //-- 需更改
                            String thirdIncludeTax = entityHiRo.getThirdIncludeTax(); //-- 需更改
                            //2.1当前第三层值比对：第四层有值，且当前值大于等于第三层且小于第四层，需要按照未达到目标层计算。
                            if (fourthIncludeTax != null) {
                                //第三层
                                if (predictNetpurchase.compareTo(new BigDecimal(thirdIncludeTax)) >= 0 && predictNetpurchase.compareTo(new BigDecimal(fourthIncludeTax)) < 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getThirdRate()); //预计IR% --需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第三层;
                                    entityHiRo.setPassList("N");//是否已达最后四层
                                    //String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(entityHiRo.getFourthRate()); //下一层目标的IR% --需修改
                                    entityHiRo.setNextPurchaseAmount(new BigDecimal(fourthIncludeTax).subtract(predictNetpurchase) + ""); //为达到下一层目标需追加的采购额 （含税）
                                    BigDecimal addAffterIrIncludeTaxAmount = new BigDecimal(entityHiRo.getNextIrRate()).multiply(new BigDecimal(entityHiRo.getNextPurchaseAmount()).add(predictNetpurchase));
                                    addAffterIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.divide(new BigDecimal(100));
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(addAffterIrIncludeTaxAmount + "");//追加后的IR$（含税）
                                    BigDecimal addIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.subtract(new BigDecimal(entityHiRo.getIrPredictAmount()));
                                    entityHiRo.setAddIrIncludeTaxAmount(addIrIncludeTaxAmount + "");//IR 增加额（含税）
                                    BigDecimal returnRate = addIrIncludeTaxAmount.multiply(new BigDecimal(100)).divide(new BigDecimal(entityHiRo.getNextPurchaseAmount()), 2, BigDecimal.ROUND_HALF_UP);
                                    entityHiRo.setReturnRate(returnRate + ""); //追加IR占追加采购金额比例(投资回报率)
                                    break w;
                                }
                                //2.2 当前第三层值比对：第四层有值，且当前值大于第四层，继续往上走continue。
                                if (predictNetpurchase.compareTo(new BigDecimal(fourthIncludeTax)) > 0) {
                                    continue;
                                }
                            } else {
                                //3.当前第三层值比对：第四层没有值，当前值与第三层比对，如果大于等于第三层，确定属于第三层，并且是最高层。
                                if (predictNetpurchase.compareTo(new BigDecimal(thirdIncludeTax)) >= 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getThirdRate()); //预计IR%; -- 需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第一层;
                                    entityHiRo.setPassList("Y");//是否已达最后一层
                                    //String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(null); //下一层目标的IR%
                                    entityHiRo.setNextPurchaseAmount(null); //为达到下一层目标需追加的采购额 （含税）
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(null);//追加后的IR$（含税）
                                    entityHiRo.setAddIrIncludeTaxAmount(null);//IR 增加额（含税）
                                    entityHiRo.setReturnRate(null); //追加IR占追加采购金额比例(投资回报率)
                                    break;
                                }
                            }
                        } else if (level == 4) {
                            String fifthIncludeTax = entityHiRo.getFifthIncludeTax(); //-------- 需更改
                            String fourthIncludeTax = entityHiRo.getFourthIncludeTax(); //------- 需更改
                            //2.1当前第四层值比对：第五层有值，且当前值大于等于第四层且小于第五层，需要按照未达到目标层计算。
                            if (fifthIncludeTax != null) {
                                //第四层
                                if (predictNetpurchase.compareTo(new BigDecimal(fourthIncludeTax)) >= 0 && predictNetpurchase.compareTo(new BigDecimal(fifthIncludeTax)) < 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getFourthRate()); //预计IR% -----需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第四层;
                                    entityHiRo.setPassList("N");//是否已达最后五层
                                    //String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(entityHiRo.getFifthRate()); //下一层目标的IR% ----需修改
                                    entityHiRo.setNextPurchaseAmount(new BigDecimal(fifthIncludeTax).subtract(predictNetpurchase) + ""); //为达到下一层目标需追加的采购额 （含税）
                                    BigDecimal addAffterIrIncludeTaxAmount = new BigDecimal(entityHiRo.getNextIrRate()).multiply(new BigDecimal(entityHiRo.getNextPurchaseAmount()).add(predictNetpurchase));
                                    addAffterIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.divide(new BigDecimal(100));
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(addAffterIrIncludeTaxAmount + "");//追加后的IR$（含税）
                                    BigDecimal addIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.subtract(new BigDecimal(entityHiRo.getIrPredictAmount()));
                                    entityHiRo.setAddIrIncludeTaxAmount(addIrIncludeTaxAmount + "");//IR 增加额（含税）
                                    BigDecimal returnRate = addIrIncludeTaxAmount.multiply(new BigDecimal(100)).divide(new BigDecimal(entityHiRo.getNextPurchaseAmount()), 2, BigDecimal.ROUND_HALF_UP);
                                    entityHiRo.setReturnRate(returnRate + ""); //追加IR占追加采购金额比例(投资回报率)
                                    break w;
                                }
                                //2.2 当前第三层值比对：第四层有值，且当前值大于第五层，继续往上走continue。
                                if (predictNetpurchase.compareTo(new BigDecimal(fifthIncludeTax)) > 0) {
                                    continue;
                                }
                            } else {
                                //3.当前第四层值比对：第五层没有值，当前值与第四层比对，如果大于等于第四层，确定属于第四层，并且是最高层。
                                if (predictNetpurchase.compareTo(new BigDecimal(fourthIncludeTax)) >= 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getFourthRate()); //预计IR%; -- 需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第一层;
                                    entityHiRo.setPassList("Y");//是否已达最后一层
                                    //String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(null); //下一层目标的IR%
                                    entityHiRo.setNextPurchaseAmount(null); //为达到下一层目标需追加的采购额 （含税）
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(null);//追加后的IR$（含税）
                                    entityHiRo.setAddIrIncludeTaxAmount(null);//IR 增加额（含税）
                                    entityHiRo.setReturnRate(null); //追加IR占追加采购金额比例(投资回报率)
                                    break;
                                }
                            }
                        } else if (level == 5) {
                            String sixthIncludeTax = entityHiRo.getSixthIncludeTax(); //-------- 需更改
                            String fifthIncludeTax = entityHiRo.getFifthIncludeTax(); //------- 需更改
                            //2.1当前第五层值比对：第六层有值，且当前值大于等于第五层且小于第六层，需要按照未达到目标层计算。
                            if (sixthIncludeTax != null) {
                                //第五层
                                if (predictNetpurchase.compareTo(new BigDecimal(fifthIncludeTax)) >= 0 && predictNetpurchase.compareTo(new BigDecimal(sixthIncludeTax)) < 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getFifthRate()); //预计IR% -----需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第五层;
                                    entityHiRo.setPassList("N");//是否已达最后六层
                                   // String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(entityHiRo.getSixthRate()); //下一层目标的IR% ----需修改
                                    entityHiRo.setNextPurchaseAmount(new BigDecimal(sixthIncludeTax).subtract(predictNetpurchase) + ""); //为达到下一层目标需追加的采购额 （含税）
                                    BigDecimal addAffterIrIncludeTaxAmount = new BigDecimal(entityHiRo.getNextIrRate()).multiply(new BigDecimal(entityHiRo.getNextPurchaseAmount()).add(predictNetpurchase));
                                    addAffterIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.divide(new BigDecimal(100));
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(addAffterIrIncludeTaxAmount + "");//追加后的IR$（含税）
                                    BigDecimal addIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.subtract(new BigDecimal(entityHiRo.getIrPredictAmount()));
                                    entityHiRo.setAddIrIncludeTaxAmount(addIrIncludeTaxAmount + "");//IR 增加额（含税）
                                    BigDecimal returnRate = addIrIncludeTaxAmount.multiply(new BigDecimal(100)).divide(new BigDecimal(entityHiRo.getNextPurchaseAmount()), 2, BigDecimal.ROUND_HALF_UP);
                                    entityHiRo.setReturnRate(returnRate + ""); //追加IR占追加采购金额比例(投资回报率)
                                    break w;
                                }
                                //2.2 当前第五层值比对：第六层有值，且当前值大于第六层，继续往上走continue。
                                if (predictNetpurchase.compareTo(new BigDecimal(sixthIncludeTax)) > 0) {
                                    continue;
                                }
                            } else {
                                //3.当前第五层值比对：第六层没有值，当前值与第五层比对，如果大于等于第五层，确定属于第五层，并且是最高层。
                                if (predictNetpurchase.compareTo(new BigDecimal(fifthIncludeTax)) >= 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getFifthRate()); //预计IR%; -- 需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第一层;
                                    entityHiRo.setPassList("Y");//是否已达最后一层
                                   // String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(null); //下一层目标的IR%
                                    entityHiRo.setNextPurchaseAmount(null); //为达到下一层目标需追加的采购额 （含税）
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(null);//追加后的IR$（含税）
                                    entityHiRo.setAddIrIncludeTaxAmount(null);//IR 增加额（含税）
                                    entityHiRo.setReturnRate(null); //追加IR占追加采购金额比例(投资回报率)
                                    break;
                                }
                            }
                        } else if (level == 6) {
                            String seventhIncludeTax = entityHiRo.getSeventhIncludeTax(); //-------- 需更改
                            String sixthIncludeTax = entityHiRo.getSixthIncludeTax(); //------- 需更改
                            //2.1当前第六层值比对：第七层有值，且当前值大于等于第六层且小于第七层，需要按照未达到目标层计算。
                            if (seventhIncludeTax != null) {
                                //第六层
                                if (predictNetpurchase.compareTo(new BigDecimal(sixthIncludeTax)) >= 0 && predictNetpurchase.compareTo(new BigDecimal(seventhIncludeTax)) < 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getSixthRate()); //预计IR% -----需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第六层;
                                    entityHiRo.setPassList("N");//是否已达最后七层
                                   // String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(entityHiRo.getSeventhRate()); //下一层目标的IR% ----需修改
                                    entityHiRo.setNextPurchaseAmount(new BigDecimal(seventhIncludeTax).subtract(predictNetpurchase) + ""); //为达到下一层目标需追加的采购额 （含税）
                                    BigDecimal addAffterIrIncludeTaxAmount = new BigDecimal(entityHiRo.getNextIrRate()).multiply(new BigDecimal(entityHiRo.getNextPurchaseAmount()).add(predictNetpurchase));
                                    addAffterIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.divide(new BigDecimal(100));
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(addAffterIrIncludeTaxAmount + "");//追加后的IR$（含税）
                                    BigDecimal addIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.subtract(new BigDecimal(entityHiRo.getIrPredictAmount()));
                                    entityHiRo.setAddIrIncludeTaxAmount(addIrIncludeTaxAmount + "");//IR 增加额（含税）
                                    BigDecimal returnRate = addIrIncludeTaxAmount.multiply(new BigDecimal(100)).divide(new BigDecimal(entityHiRo.getNextPurchaseAmount()), 2, BigDecimal.ROUND_HALF_UP);
                                    entityHiRo.setReturnRate(returnRate + ""); //追加IR占追加采购金额比例(投资回报率)
                                    break w;
                                }
                                //2.2 当前第六层值比对：第七层有值，且当前值大于第七层，继续往上走continue。
                                if (predictNetpurchase.compareTo(new BigDecimal(seventhIncludeTax)) > 0) {
                                    continue;
                                }
                            } else {
                                //3.当前第六层值比对：第七层没有值，当前值与第六层比对，如果大于等于第六层，确定属于第六层，并且是最高层。
                                if (predictNetpurchase.compareTo(new BigDecimal(sixthIncludeTax)) >= 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getSixthRate()); //预计IR%; -- 需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第一层;
                                    entityHiRo.setPassList("Y");//是否已达最后一层
                                   // String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(null); //下一层目标的IR%
                                    entityHiRo.setNextPurchaseAmount(null); //为达到下一层目标需追加的采购额 （含税）
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(null);//追加后的IR$（含税）
                                    entityHiRo.setAddIrIncludeTaxAmount(null);//IR 增加额（含税）
                                    entityHiRo.setReturnRate(null); //追加IR占追加采购金额比例(投资回报率)
                                    break;
                                }
                            }
                        } else if (level == 7) {
                            String eighthIncludeTax = entityHiRo.getEighthIncludeTax(); //-------- 需更改
                            String seventhIncludeTax = entityHiRo.getSeventhIncludeTax(); //------- 需更改
                            //2.1当前第七层值比对：第八层有值，且当前值大于等于第七层且小于第八层，需要按照未达到目标层计算。
                            if (eighthIncludeTax != null) {
                                //第七层
                                if (predictNetpurchase.compareTo(new BigDecimal(seventhIncludeTax)) >= 0 && predictNetpurchase.compareTo(new BigDecimal(eighthIncludeTax)) < 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getSeventhRate()); //预计IR% -----需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第七层;
                                    entityHiRo.setPassList("N");//是否已达最后八层
                                   // String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100))+ "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(entityHiRo.getEighthRate()); //下一层目标的IR% ----需修改
                                    entityHiRo.setNextPurchaseAmount(new BigDecimal(eighthIncludeTax).subtract(predictNetpurchase) + ""); //为达到下一层目标需追加的采购额 （含税）
                                    BigDecimal addAffterIrIncludeTaxAmount = new BigDecimal(entityHiRo.getNextIrRate()).multiply(new BigDecimal(entityHiRo.getNextPurchaseAmount()).add(predictNetpurchase));
                                    addAffterIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.divide(new BigDecimal(100));
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(addAffterIrIncludeTaxAmount + "");//追加后的IR$（含税）
                                    BigDecimal addIrIncludeTaxAmount = addAffterIrIncludeTaxAmount.subtract(new BigDecimal(entityHiRo.getIrPredictAmount()));
                                    entityHiRo.setAddIrIncludeTaxAmount(addIrIncludeTaxAmount + "");//IR 增加额（含税）
                                    BigDecimal returnRate = addIrIncludeTaxAmount.multiply(new BigDecimal(100)).divide(new BigDecimal(entityHiRo.getNextPurchaseAmount()), 2, BigDecimal.ROUND_HALF_UP);
                                    entityHiRo.setReturnRate(returnRate + ""); //追加IR占追加采购金额比例(投资回报率)
                                    break w;
                                }
                                //2.2 当前第七层值比对：第八层有值，且当前值大于第八层，继续往上走continue。
                                if (predictNetpurchase.compareTo(new BigDecimal(eighthIncludeTax)) > 0) {
                                    continue;
                                }
                            } else {
                                //3.当前第七层值比对：第八层没有值，当前值与第七层比对，如果大于等于第七层，确定属于第七层，并且是最高层。
                                if (predictNetpurchase.compareTo(new BigDecimal(seventhIncludeTax)) >= 0) {
                                    entityHiRo.setIrPredictRate(entityHiRo.getSeventhRate()); //预计IR%; -- 需修改
                                    entityHiRo.setPassFirst("Y");//是否已达第一层;
                                    entityHiRo.setPassList("Y");//是否已达最后一层
                                    //String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate())).divide(new BigDecimal(100)) + "";
                                    String irPredictAmount = predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(100)) + "";
                                    entityHiRo.setIrPredictAmount(irPredictAmount);//预计IR$(含税）
                                    entityHiRo.setNextIrRate(null); //下一层目标的IR%
                                    entityHiRo.setNextPurchaseAmount(null); //为达到下一层目标需追加的采购额 （含税）
                                    entityHiRo.setAddAffterIrIncludeTaxAmount(null);//追加后的IR$（含税）
                                    entityHiRo.setAddIrIncludeTaxAmount(null);//IR 增加额（含税）
                                    entityHiRo.setReturnRate(null); //追加IR占追加采购金额比例(投资回报率)
                                    break;
                                }
                            }
                        } else if (level == 8) {
                            if (entityHiRo.getEighthRate() == null || entityHiRo.getEighthIncludeTax() == null) {
                                break w;
                            }
                            //是否达到当前目标退佣
                            if (predictNetpurchase.compareTo(new BigDecimal(entityHiRo.getEighthIncludeTax())) >= 0) {
                                entityHiRo.setIrPredictRate(entityHiRo.getEighthIncludeTax());//预计IR%
                                entityHiRo.setPassFirst("Y"); //已经达到第一层
                                entityHiRo.setPassList("Y"); //已经达到最后一层
                                //预计IR$(含税）=全年预估采购额*预计IR%
                                entityHiRo.setIrPredictAmount(predictNetpurchase.multiply(new BigDecimal(entityHiRo.getIrPredictRate()).divide(new BigDecimal(100))).toString());
                                entityHiRo.setNextIrRate(null);//下一层目标的IR%
                                entityHiRo.setNextPurchaseAmount(null);//为达到下一层目标需追加的采购额 （含税）
                                entityHiRo.setAddAffterIrIncludeTaxAmount(null); //追加后的IR$（含税）
                                entityHiRo.setAddIrIncludeTaxAmount(null);//IR 增加额（含税）
                                entityHiRo.setReturnRate(null);//追加IR占追加采购金额比例(投资回报率)
                                break w;
                            } else {
                                break w;
                            }
                        }
                    }
                    setFormatEntity(entityHiRo);
                }
            }
        }
        return pagination;
    }


    @Override
    public TtaIrTermsEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        return null;
    }


    @SuppressWarnings("all")
    public void setFormatEntity(TtaIrTermsEntity_HI_RO entity) {
         //entity.setFirstExcludeTax(Util.fmtMicrometer(entity.getFirstExcludeTax(), 0, "元"));

        entity.setFirstExcludeTax(Util.fmtMicrometer(entity.getFirstExcludeTax(), 0, ""));
        entity.setFirstIncludeTax(Util.fmtMicrometer(entity.getFirstIncludeTax(), 0, ""));
        entity.setFirstRate(Util.fmtMicrometer(entity.getFirstRate(), 2, "%"));

        entity.setSecondExcludeTax(Util.fmtMicrometer(entity.getSecondExcludeTax(), 0, ""));
        entity.setSecondIncludeTax(Util.fmtMicrometer(entity.getSecondIncludeTax(), 0, ""));
        entity.setSecondRate(Util.fmtMicrometer(entity.getSecondRate(), 2, "%"));

        entity.setThirdExcludeTax(Util.fmtMicrometer(entity.getThirdExcludeTax(), 0, ""));
        entity.setThirdIncludeTax(Util.fmtMicrometer(entity.getThirdIncludeTax(), 0, ""));
        entity.setThirdRate(Util.fmtMicrometer(entity.getThirdRate(), 2, "%"));

        entity.setFourthExcludeTax(Util.fmtMicrometer(entity.getFourthExcludeTax(), 0, ""));
        entity.setFourthIncludeTax(Util.fmtMicrometer(entity.getFourthIncludeTax(), 0, ""));
        entity.setFourthRate(Util.fmtMicrometer(entity.getFourthRate(), 2, "%"));

        entity.setFifthExcludeTax(Util.fmtMicrometer(entity.getFifthExcludeTax(), 0, ""));
        entity.setFifthIncludeTax(Util.fmtMicrometer(entity.getFifthIncludeTax(), 0, ""));
        entity.setFifthRate(Util.fmtMicrometer(entity.getFifthRate(), 2, "%"));

        entity.setSixthExcludeTax(Util.fmtMicrometer(entity.getSixthExcludeTax(), 0, ""));
        entity.setSixthIncludeTax(Util.fmtMicrometer(entity.getSixthIncludeTax(), 0, ""));
        entity.setSixthRate(Util.fmtMicrometer(entity.getSixthRate(), 2, "%"));

        entity.setSeventhExcludeTax(Util.fmtMicrometer(entity.getSeventhExcludeTax(), 0, ""));
        entity.setSeventhIncludeTax(Util.fmtMicrometer(entity.getSeventhIncludeTax(), 0, ""));
        entity.setSeventhRate(Util.fmtMicrometer(entity.getSeventhRate(), 2, "%"));

        entity.setEighthExcludeTax(Util.fmtMicrometer(entity.getEighthExcludeTax(), 0, ""));
        entity.setEighthIncludeTax(Util.fmtMicrometer(entity.getEighthIncludeTax(), 0, ""));
        entity.setEighthRate(Util.fmtMicrometer(entity.getEighthRate(), 2, "%"));

        entity.setNetpurchase(Util.fmtMicrometer(entity.getNetpurchase(), 0, "")); //折扣前采购额（含税）
        entity.setPredictNetpurchase(Util.fmtMicrometer(entity.getPredictNetpurchase(), 0, ""));//预计IR%;
        entity.setIrPredictRate(Util.fmtMicrometer(entity.getIrPredictRate(), 2, "%"));
        entity.setIrPredictAmount(Util.fmtMicrometer(entity.getIrPredictAmount(), 0, "")); //预计IR$(含税）
        entity.setNextIrRate(Util.fmtMicrometer(entity.getNextIrRate(), 2, "%")); //下一层目标的IR%
        entity.setNextPurchaseAmount(Util.fmtMicrometer(entity.getNextPurchaseAmount(), 0, ""));//为达到下一层目标需追加的采购额 （含税）
        entity.setAddAffterIrIncludeTaxAmount(Util.fmtMicrometer(entity.getAddAffterIrIncludeTaxAmount(), 0,""));//追加后的IR$（含税）
        entity.setAddIrIncludeTaxAmount(Util.fmtMicrometer(entity.getAddIrIncludeTaxAmount(), 0, ""));//IR 增加额（含税）
        entity.setReturnRate(Util.fmtMicrometer(entity.getReturnRate(), 2,"%"));//追加IR占追加采购金额比例(投资回报率)*/
    }

}


