package com.sie.watsons.base.ttaImport.model.entities;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class TtaOiSummaryLineEntity_HI_MODEL {
    @ExcelProperty(value ="ID")
    private Integer ttaOiSummaryLineId;
    @ExcelProperty(value ="年月")
    private Date accountMonth;
    @ExcelProperty(value ="供应商编号")
    private String rmsCode;
    @ExcelProperty(value ="供应商名称")
    private String venderName;
    @ExcelProperty(value ="部门")
    private String department;
    @ExcelProperty(value ="品牌")
    private String brand;
    @ExcelProperty(value ="供应商属性")
    private String venderab;
    @ExcelProperty(value ="计生供应商")
    private String familyPlaningFlag;
    @ExcelProperty(value ="供应商类型")
    private String venderType;
    @ExcelProperty(value ="采购额（折扣前）")
    private BigDecimal purchase;
    @ExcelProperty(value ="退货额（折扣前）")
    private BigDecimal goodsreturn;
    @ExcelProperty(value ="净采购额（折扣前）")
    private BigDecimal netpurchase;
    @ExcelProperty(value ="免送额（折扣前）")
    private BigDecimal dsd;
    @ExcelProperty(value ="折扣后进货额")
    private BigDecimal purchaseorigin;
    @ExcelProperty(value ="折扣后退货额")
    private BigDecimal goodsreturnorigin;
    @ExcelProperty(value ="折扣后净采购额")
    private BigDecimal netpurchaseorigin;
    @ExcelProperty(value ="以前年度的进货额")
    private BigDecimal pypurchase;
    @ExcelProperty(value ="以前年度的退货额")
    private BigDecimal pygoodsreturn;
    @ExcelProperty(value ="以前年度的净采购额(S+T)")
    private BigDecimal pynetpurchase;
    @ExcelProperty(value ="以前年度的DSD(免送额)")
    private BigDecimal pydsd;
    @ExcelProperty(value ="本月采购额计算-一般购货折扣")
    private BigDecimal tyWivatBtFrP592950;
    @ExcelProperty(value ="本月合同调整-一般购货折扣")
    private BigDecimal tyadjCtaWivatBtFrP592950;
    @ExcelProperty(value ="本月对帐调整-一般购货折扣")
    private BigDecimal tyadjRcaWivatBtFrP592950;
    @ExcelProperty(value ="以前年度合同调整-一般购货折扣")
    private BigDecimal lyadjCtaWivatBtFrP592950;
    @ExcelProperty(value ="以前年度对帐调整-一般购货折扣")
    private BigDecimal lyadjRcaWivatBtFrP592950;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-一般购货折扣")
    private BigDecimal lyadjPraWivatBtFrP592950;
    @ExcelProperty(value ="一般购货折扣Total")
    private BigDecimal totalWivatBtFrP592950;
    @ExcelProperty(value ="一般购货折扣Total(不含税)")
    private BigDecimal totalWovatBtFrP592950;
    @ExcelProperty(value ="本月采购额计算-（提前付款）购货折扣")
    private BigDecimal tyWivatBtEpdP592712;
    @ExcelProperty(value ="本月合同调整-（提前付款）购货折扣")
    private BigDecimal tyadjCtaWivatBtEpdP592712;
    @ExcelProperty(value ="本月对帐调整-（提前付款）购货折扣")
    private BigDecimal tyadjRcaWivatBtEpdP592712;
    @ExcelProperty(value ="以前年度合同调整-（提前付款）购货折扣")
    private BigDecimal lyadjCtaWivatBtEpdP592712;
    @ExcelProperty(value ="以前年度对帐调整-（提前付款）购货折扣")
    private BigDecimal lyadjRcaWivatBtEpdP592712;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-（提前付款）购货折扣")
    private BigDecimal lyadjPraWivatBtEpdP592712;
    @ExcelProperty(value ="（提前付款）购货折扣Total")
    private BigDecimal totalWivatBtEpdP592712;
    @ExcelProperty(value ="（提前付款）购货折扣Total(不含税)")
    private BigDecimal totalWovatBtEpdP592712;
    @ExcelProperty(value ="本月采购额计算-促销折扣")
    private BigDecimal tyWivatBtPdP592803;
    @ExcelProperty(value ="本月合同调整-促销折扣")
    private BigDecimal tyadjCtaWivatBtPdP592803;
    @ExcelProperty(value ="本月对帐调整-促销折扣")
    private BigDecimal tyadjRcaWivatBtPdP592803;
    @ExcelProperty(value ="以前年度合同调整-促销折扣")
    private BigDecimal lyadjCtaWivatBtPdP592803;
    @ExcelProperty(value ="以前年度对帐调整-促销折扣")
    private BigDecimal lyadjRcaWivatBtPdP592803;
    @ExcelProperty(value ="以前年度采购额计算/PRG&调整/自查调整-促销折扣")
    private BigDecimal lyadjPraWivatBtPdP592803;
    @ExcelProperty(value ="促销折扣Total")
    private BigDecimal totalWivatBtPdP592803;
    @ExcelProperty(value ="促销折扣Total(不含税)")
    private BigDecimal totalWovatBtPdP592803;
    @ExcelProperty(value ="本月采购额计算-集中收货折扣")
    private BigDecimal tyWivatBtDaP592901;
    @ExcelProperty(value ="本月合同调整-集中收货折扣")
    private BigDecimal tyadjCtaWivatBtDaP592901;
    @ExcelProperty(value ="本月对帐调整-集中收货折扣")
    private BigDecimal tyadjRcaWivatBtDaP592901;
    @ExcelProperty(value ="以前年度合同调整-集中收货折扣")
    private BigDecimal lyadjCtaWivatBtDaP592901;
    @ExcelProperty(value ="以前年度对帐调整-集中收货折扣")
    private BigDecimal lyadjRcaWivatBtDaP592901;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-集中收货折扣")
    private BigDecimal lyadjPraWivatBtDaP592901;
    @ExcelProperty(value ="集中收货折扣Total")
    private BigDecimal totalWivatBtDaP592901;
    @ExcelProperty(value ="集中收货折扣Total(不含税)")
    private BigDecimal totalWovatBtDaP592901;
    @ExcelProperty(value ="本月采购额计算-（残损）购货折扣")
    private BigDecimal tyWivatBtDgaP592708;
    @ExcelProperty(value ="本月合同调整-（残损）购货折扣")
    private BigDecimal tyadjCtaWivatBtDgaP592708;
    @ExcelProperty(value ="本月对帐调整-（残损）购货折扣")
    private BigDecimal tyadjRcaWivatBtDgaP592708;
    @ExcelProperty(value ="以前年度合同调整-（残损）购货折扣")
    private BigDecimal lyadjCtaWivatBtDgaP592708;
    @ExcelProperty(value ="以前年度对帐调整-（残损）购货折扣")
    private BigDecimal lyadjRcaWivatBtDgaP592708;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-（残损）购货折扣")
    private BigDecimal lyadjPraWivatBtDgaP592708;
    @ExcelProperty(value ="（残损）购货折扣Total")
    private BigDecimal totalWivatBtDgaP592708;
    @ExcelProperty(value ="（残损）购货折扣Total(不含税)")
    private BigDecimal totalWovatBtDgaP592708;
    @ExcelProperty(value ="本月合同调整-目标退佣")
    private BigDecimal tyadjCtaWivatBtIrP592951;
    @ExcelProperty(value ="本月对帐调整-目标退佣")
    private BigDecimal tyadjRcaWivatBtIrP592951;
    @ExcelProperty(value ="以前年度合同调整-目标退佣")
    private BigDecimal lyadjCtaWivatBtIrP592951;
    @ExcelProperty(value ="以前年度对帐调整-目标退佣")
    private BigDecimal lyadjRcaWivatBtIrP592951;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-目标退佣")
    private BigDecimal lyadjPraWivatBtIrP592951;
    @ExcelProperty(value ="目标退佣Total")
    private BigDecimal totalWivatBtIrP592951;
    @ExcelProperty(value ="目标退佣(不含税)Total")
    private BigDecimal totalWovatBtIrP592951;
    @ExcelProperty(value ="本月采购额计算-商业发展支持")
    private BigDecimal tyWivatBtBdsP592792;
    @ExcelProperty(value ="本月合同调整-商业发展支持")
    private BigDecimal tyadjCtaWivatBtBdsP592792;
    @ExcelProperty(value ="本月对帐调整-商业发展支持")
    private BigDecimal tyadjRcaWivatBtBdsP592792;
    @ExcelProperty(value ="以前年度合同调整-商业发展支持")
    private BigDecimal lyadjCtaWivatBtBdsP592792;
    @ExcelProperty(value ="以前年度对帐调整-商业发展支持")
    private BigDecimal lyadjRcaWivatBtBdsP592792;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-商业发展支持")
    private BigDecimal lyadjPraWivatBtBdsP592792;
    @ExcelProperty(value ="商业发展支持Total")
    private BigDecimal totalWivatBtBdsP592792;
    @ExcelProperty(value ="商业发展支持Total(不含税)")
    private BigDecimal totalWovatBtBdsP592792;
    @ExcelProperty(value ="本月计算-延误送货罚款")
    private BigDecimal tyWivatOtLdP592722;
    @ExcelProperty(value ="本月合同调整-延误送货罚款")
    private BigDecimal tyadjCtaWivatOtLdP592722;
    @ExcelProperty(value ="本月对帐调整-延误送货罚款")
    private BigDecimal tyadjRcaWivatOtLdP592722;
    @ExcelProperty(value ="以前年度合同调整-延误送货罚款")
    private BigDecimal lyadjCtaWivatOtLdP592722;
    @ExcelProperty(value ="以前年度对帐调整-延误送货罚款")
    private BigDecimal lyadjRcaWivatOtLdP592722;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-延误送货罚款")
    private BigDecimal lyadjPraWivatOtLdP592722;
    @ExcelProperty(value ="延误送货罚款Total")
    private BigDecimal totalWivatOtLdP592722;
    @ExcelProperty(value ="延误送货罚款Total(不含税)")
    private BigDecimal totalWovatOtLdP592722;
    @ExcelProperty(value ="本月计算-满足率低于95%罚款")
    private BigDecimal tyWivatOtNfP592780;
    @ExcelProperty(value ="本月合同调整-满足率低于95%罚款")
    private BigDecimal tyadjCtaWivatOtNfP592780;
    @ExcelProperty(value ="本月对帐调整-满足率低于95%罚款")
    private BigDecimal tyadjRcaWivatOtNfP592780;
    @ExcelProperty(value ="以前年度合同调整-满足率低于95%罚款")
    private BigDecimal lyadjCtaWivatOtNfP592780;
    @ExcelProperty(value ="以前年度对帐调整-满足率低于95%罚款")
    private BigDecimal lyadjRcaWivatOtNfP592780;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-满足率低于95%罚款")
    private BigDecimal lyadjPraWivatOtNfP592780;
    @ExcelProperty(value ="满足率低于95%罚款Total")
    private BigDecimal totalWivatOtNfP592780;
    @ExcelProperty(value ="满足率低于95%罚款Total(不含税)")
    private BigDecimal totalWovatOtNfP592780;
    @ExcelProperty(value ="本月计算-优惠券")
    private BigDecimal tyWivatOtCpnP592743;
    @ExcelProperty(value ="本月合同调整-优惠券")
    private BigDecimal tyadjCtaWivatOtCpnP592743;
    @ExcelProperty(value ="本月对帐调整-优惠券")
    private BigDecimal tyadjRcaWivatOtCpnP592743;
    @ExcelProperty(value ="以前年度合同调整-优惠券")
    private BigDecimal lyadjCtaWivatOtCpnP592743;
    @ExcelProperty(value ="以前年度对帐调整-优惠券")
    private BigDecimal lyadjRcaWivatOtCpnP592743;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-优惠券")
    private BigDecimal lyadjPraWivatOtCpnP592743;
    @ExcelProperty(value ="优惠券Total")
    private BigDecimal totalWivatOtCpnP592743;
    @ExcelProperty(value ="优惠券Total(不含税)")
    private BigDecimal totalWovatOtLpP592743;
    @ExcelProperty(value ="本月计算-会员优惠")
    private BigDecimal tyWivatOtLpP500320;
    @ExcelProperty(value ="本月合同调整-会员优惠")
    private BigDecimal tyadjCtaWivatOtLpP500320;
    @ExcelProperty(value ="本月对帐调整-会员优惠")
    private BigDecimal tyadjRcaWivatOtLpP500320;
    @ExcelProperty(value ="以前年度合同调整-会员优惠")
    private BigDecimal lyadjCtaWivatOtLpP500320;
    @ExcelProperty(value ="以前年度对帐调整-会员优惠")
    private BigDecimal lyadjRcaWivatOtLpP500320;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-会员优惠")
    private BigDecimal lyadjPraWivatOtLpP500320;
    @ExcelProperty(value ="会员优惠Total")
    private BigDecimal totalWivatOtLpP500320;
    @ExcelProperty(value ="会员优惠Total(不含税)")
    private BigDecimal totalWovatOtLpP500320;
    @ExcelProperty(value ="本月计算-未在合同上的提早付款折扣")
    private BigDecimal tyWivatBtUepdP592796;
    @ExcelProperty(value ="本月合同调整-未在合同上的提早付款折扣")
    private BigDecimal tyadjCtaWivatBtUepdP592796;
    @ExcelProperty(value ="本月对帐调整-未在合同上的提早付款折扣")
    private BigDecimal tyadjRcaWivatBtUepdP592796;
    @ExcelProperty(value ="以前年度合同调整-未在合同上的提早付款折扣")
    private BigDecimal lyadjCtaWivatBtUepdP592796;
    @ExcelProperty(value ="以前年度对帐调整-未在合同上的提早付款折扣")
    private BigDecimal lyadjRcaWivatBtUepdP592796;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-未在合同上的提早付款折扣")
    private BigDecimal lyadjPraWivatBtUepdP592796;
    @ExcelProperty(value ="未在合同上的提早付款折扣Total")
    private BigDecimal totalWivatBtUepdP592796;
    @ExcelProperty(value ="未在合同上的提早付款折扣Total(不含税)")
    private BigDecimal totalWovatBtUepdP592796;
    @ExcelProperty(value ="本月计算(TTA)-成本补差")
    private BigDecimal tyWivatAbtCriP592738;
    @ExcelProperty(value ="本月计算(ONTOP)-成本补差")
    private BigDecimal tyWivatAbotCriP592738;
    @ExcelProperty(value ="以前年度计算(TTA)-成本补差")
    private BigDecimal lyadjWivatAbtCriP592738;
    @ExcelProperty(value ="以前年度计算(ONTOP)-成本补差")
    private BigDecimal lyadjWivatAbotCriP592738;
    @ExcelProperty(value ="本月合同调整-成本补差")
    private BigDecimal tyadjCtaWivatAbtCriP592738;
    @ExcelProperty(value ="本月对帐调整-成本补差")
    private BigDecimal tyadjRcaWivatAbtCriP592738;
    @ExcelProperty(value ="以前年度合同调整-成本补差")
    private BigDecimal lyadjCtaWivatAbtCriP592738;
    @ExcelProperty(value ="以前年度对帐调整-成本补差")
    private BigDecimal lyadjRcaWivatAbtCriP592738;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-成本补差")
    private BigDecimal lyadjPraWivatAbtCriP592738;
    @ExcelProperty(value ="成本补差Total")
    private BigDecimal totalWivatAbCriP592738;
    @ExcelProperty(value ="成本补差Total(不含税)")
    private BigDecimal totalWovatAbCriP592738;
    @ExcelProperty(value ="本月计算(TTA)-OEM试用装")
    private BigDecimal tyWivatAbtOemtP592873;
    @ExcelProperty(value ="本月计算(ONTOP)-OEM试用装")
    private BigDecimal tyWivatAbotOemtP592873;
    @ExcelProperty(value ="以前年度计算(TTA)-OEM试用装")
    private BigDecimal lyadjWivatAbtOemtP592873;
    @ExcelProperty(value ="以前年度计算(ONTOP)-OEM试用装")
    private BigDecimal lyadjWivatAbotOemtP592873;
    @ExcelProperty(value ="本月合同调整-OEM试用装")
    private BigDecimal tyadjCtaWivatAbtOemP592873;
    @ExcelProperty(value ="本月对帐调整-OEM试用装")
    private BigDecimal tyadjRcaWivatAbtOemP592873;
    @ExcelProperty(value ="以前年度合同调整-OEM试用装")
    private BigDecimal lyadjCtaWivatAbtOemP592873;
    @ExcelProperty(value ="以前年度对帐调整-OEM试用装")
    private BigDecimal lyadjRcaWivatAbtOemP592873;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-OEM试用装")
    private BigDecimal lyadjPraWivatAbtOemP592873;
    @ExcelProperty(value ="OEM试用装Total")
    private BigDecimal totalWivatAbOemtP592873;
    @ExcelProperty(value ="OEM试用装otal(不含税)")
    private BigDecimal totalWovatAbtOemtP592873;
    @ExcelProperty(value ="本月计算(TTA)-数据分享费")
    private BigDecimal tyWivatAbtDsP592814;
    @ExcelProperty(value ="本月计算(ONTOP)-数据分享费")
    private BigDecimal tyWivatAbotDsP592814;
    @ExcelProperty(value ="以前年度计算(TTA)-数据分享费")
    private BigDecimal lyadjWivatAbtDsP592814;
    @ExcelProperty(value ="以前年度计算(ONTOP)-数据分享费")
    private BigDecimal lyadjWivatAbotDsP592814;
    @ExcelProperty(value ="本月合同调整-数据分享费")
    private BigDecimal tyadjCtaWivatAbtDsP592814;
    @ExcelProperty(value ="本月对帐调整-数据分享费")
    private BigDecimal tyadjRcaWivatAbtDsP592814;
    @ExcelProperty(value ="以前年度合同调整-数据分享费")
    private BigDecimal lyadjCtaWivatAbtDsP592814;
    @ExcelProperty(value ="以前年度对帐调整-数据分享费")
    private BigDecimal lyadjRcaWivatAbtDsP592814;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-数据分享费")
    private BigDecimal lyadjPraWivatAbtDsP592814;
    @ExcelProperty(value ="数据分享费Total")
    private BigDecimal totalWivatAbDsP592814;
    @ExcelProperty(value ="本月计算(TTA)-促销陈列服务费")
    private BigDecimal tyWivatAbtDroP592810;
    @ExcelProperty(value ="本月计算(ONTOP)-促销陈列服务费")
    private BigDecimal tyWivatAbotDroP592810;
    @ExcelProperty(value ="以前年度计算(TTA)-促销陈列服务费")
    private BigDecimal lyadjWivatAbtDroP592810;
    @ExcelProperty(value ="以前年度计算(ONTOP)-促销陈列服务费")
    private BigDecimal lyadjWivatAbotDroP592810;
    @ExcelProperty(value ="本月合同调整-促销陈列服务费")
    private BigDecimal tyadjCtaWivatAbtDroP592810;
    @ExcelProperty(value ="本月对帐调整-促销陈列服务费")
    private BigDecimal tyadjRcaWivatAbtDroP592810;
    @ExcelProperty(value ="以前年度合同调整-促销陈列服务费")
    private BigDecimal lyadjCtaWivatAbtDroP592810;
    @ExcelProperty(value ="以前年度对帐调整-促销陈列服务费")
    private BigDecimal lyadjRcaWivatAbtDroP592810;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-促销陈列服务费")
    private BigDecimal lyadjPraWivatAbtDroP592810;
    @ExcelProperty(value ="促销陈列服务费Total")
    private BigDecimal totalWivatAbDroP592810;
    @ExcelProperty(value ="本月计算(TTA)-促销陈列服务费-NewTrialProjects")
    private BigDecimal tyWivatAbtDrntP592871;
    @ExcelProperty(value ="本月计算(ONTOP)-促销陈列服务费-NewTrialProjects")
    private BigDecimal tyWivatAbotDrntP592871;
    @ExcelProperty(value ="以前年度计算(TTA)-促销陈列服务费-NewTrialProjects")
    private BigDecimal lyadjWivatAbtDrntP592871;
    @ExcelProperty(value ="以前年度计算(ONTOP)-促销陈列服务费-NewTrialProjects")
    private BigDecimal lyadjWivatAbotDrntP592871;
    @ExcelProperty(value ="本月合同调整-促销陈列服务费-NewTrialProjects")
    private BigDecimal tyadjCtaWivatAbtDrnP592871;
    @ExcelProperty(value ="本月对帐调整-促销陈列服务费-NewTrialProjects")
    private BigDecimal tyadjRcaWivatAbtDrnP592871;
    @ExcelProperty(value ="以前年度合同调整-促销陈列服务费-NewTrialProjects")
    private BigDecimal lyadjCtaWivatAbtDrnP592871;
    @ExcelProperty(value ="以前年度对帐调整-促销陈列服务费-NewTrialProjects")
    private BigDecimal lyadjRcaWivatAbtDrnP592871;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-促销陈列服务费-NewTrialProjects")
    private BigDecimal lyadjPraWivatAbtDrnP592871;
    @ExcelProperty(value ="促销陈列服务费-NewTrialProjectsTotal")
    private BigDecimal totalWivatAbDrntP592871;
    @ExcelProperty(value ="本月计算(TTA)-专柜促销陈列服务费")
    private BigDecimal tyWivatAbtCrP592715;
    @ExcelProperty(value ="本月计算(ONTOP)-专柜促销陈列服务费")
    private BigDecimal tyWivatAbotCrP592715;
    @ExcelProperty(value ="以前年度计算(TTA)-专柜促销陈列服务费")
    private BigDecimal lyadjWivatAbtCrP592715;
    @ExcelProperty(value ="以前年度计算(ONTOP)-专柜促销陈列服务费")
    private BigDecimal lyadjWivatAbotCrP592715;
    @ExcelProperty(value ="本月合同调整-专柜促销陈列服务费")
    private BigDecimal tyadjCtaWivatAbtCrP592715;
    @ExcelProperty(value ="本月对帐调整-专柜促销陈列服务费")
    private BigDecimal tyadjRcaWivatAbtCrP592715;
    @ExcelProperty(value ="以前年度合同调整-专柜促销陈列服务费")
    private BigDecimal lyadjCtaWivatAbtCrP592715;
    @ExcelProperty(value ="以前年度对帐调整-专柜促销陈列服务费")
    private BigDecimal lyadjRcaWivatAbtCrP592715;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-专柜促销陈列服务费")
    private BigDecimal lyadjPraWivatAbtCrP592715;
    @ExcelProperty(value ="专柜促销陈列服务费Total")
    private BigDecimal totalWivatAbCrP592715;
    @ExcelProperty(value ="本月计算(TTA)-宣传单张、宣传牌及促销用品推广服务费")
    private BigDecimal tyWivatAbtDmP592751;
    @ExcelProperty(value ="本月计算(ONTOP)-宣传单张、宣传牌及促销用品推广服务费")
    private BigDecimal tyWivatAbotDmP592751;
    @ExcelProperty(value ="以前年度计算(TTA)-宣传单张、宣传牌及促销用品推广服务费")
    private BigDecimal lyadjWivatAbtDmP592751;
    @ExcelProperty(value ="以前年度计算(ONTOP)-宣传单张、宣传牌及促销用品推广服务费")
    private BigDecimal lyadjWivatAbotDmP592751;
    @ExcelProperty(value ="本月合同调整-宣传单张、宣传牌及促销用品推广服务费")
    private BigDecimal tyadjCtaWivatAbtDmP592751;
    @ExcelProperty(value ="本月对帐调整-宣传单张、宣传牌及促销用品推广服务费")
    private BigDecimal tyadjRcaWivatAbtDmP592751;
    @ExcelProperty(value ="以前年度合同调整-宣传单张、宣传牌及促销用品推广服务费")
    private BigDecimal lyadjCtaWivatAbtDmP592751;
    @ExcelProperty(value ="以前年度对帐调整-宣传单张、宣传牌及促销用品推广服务费")
    private BigDecimal lyadjRcaWivatAbtDmP592751;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-宣传单张、宣传牌及促销用品推广服务费")
    private BigDecimal lyadjPraWivatAbtDmP592751;
    @ExcelProperty(value ="宣传单张、宣传牌及促销用品推广服务费Total")
    private BigDecimal totalWivatAbDmP592751;
    @ExcelProperty(value ="本月计算(TTA)-新品种宣传推广服务费")
    private BigDecimal tyWivatAbtNppP592804;
    @ExcelProperty(value ="本月计算(ONTOP)-新品种宣传推广服务费")
    private BigDecimal tyWivatAbotNppP592804;
    @ExcelProperty(value ="以前年度计算(TTA)-新品种宣传推广服务费")
    private BigDecimal lyadjWivatAbtNppP592804;
    @ExcelProperty(value ="以前年度计算(ONTOP)-新品种宣传推广服务费")
    private BigDecimal lyadjWivatAbotNppP592804;
    @ExcelProperty(value ="本月合同调整-新品种宣传推广服务费")
    private BigDecimal tyadjCtaWivatAbtNppP592804;
    @ExcelProperty(value ="本月对帐调整--新品种宣传推广服务费")
    private BigDecimal tyadjRcaWivatAbtNppP592804;
    @ExcelProperty(value ="以前年度合同调整--新品种宣传推广服务费")
    private BigDecimal lyadjCtaWivatAbtNppP592804;
    @ExcelProperty(value ="以前年度对帐调整--新品种宣传推广服务费")
    private BigDecimal lyadjRcaWivatAbtNppP592804;
    @ExcelProperty(value ="以前年度PRG调整/自查调整--新品种宣传推广服务费")
    private BigDecimal lyadjPraWivatAbtNppP592804;
    @ExcelProperty(value ="新品种宣传推广服务费Total")
    private BigDecimal totalWivatAbNppP592804;
    @ExcelProperty(value ="本月计算(TTA)BDF商业发展服务费")
    private BigDecimal tyWivatAbtBdfP592726;
    @ExcelProperty(value ="本月计算(ONTOP)BDF商业发展服务费")
    private BigDecimal tyWivatAbotBdfP592726;
    @ExcelProperty(value ="以前年度计算(TTA)BDF商业发展服务费")
    private BigDecimal lyadjWivatAbtBdfP592726;
    @ExcelProperty(value ="以前年度计算(ONTOP)BDF商业发展服务费")
    private BigDecimal lyadjWivatAbotBdfP592726;
    @ExcelProperty(value ="本月合同调整BDF商业发展服务费")
    private BigDecimal tyadjCtaWivatAbtBdfP592726;
    @ExcelProperty(value ="本月对帐调整BDF商业发展服务费")
    private BigDecimal tyadjRcaWivatAbtBdfP592726;
    @ExcelProperty(value ="以前年度合同调整BDF商业发展服务费")
    private BigDecimal lyadjCtaWivatAbtBdfP592726;
    @ExcelProperty(value ="以前年度对帐调整BDF商业发展服务费")
    private BigDecimal lyadjRcaWivatAbtBdfP592726;
    @ExcelProperty(value ="以前年度PRG调整/自查调整BDF商业发展服务费")
    private BigDecimal lyadjPraWivatAbtBdfP592726;
    @ExcelProperty(value ="商业发展服务费Total")
    private BigDecimal totalWivatAbBdfP592726;
    @ExcelProperty(value ="本月计算(TTA)-H&BAward健与美")
    private BigDecimal tyWivatAbtHwbP592812;
    @ExcelProperty(value ="本月计算(ONTOP)-H&BAward健与美")
    private BigDecimal tyWivatAbotHwbP592812;
    @ExcelProperty(value ="以前年度计算(TTA)-H&BAward健与美")
    private BigDecimal lyadjWivatAbtHwbP592812;
    @ExcelProperty(value ="以前年度计算(ONTOP)-H&BAward健与美")
    private BigDecimal lyadjWivatAbotHwbP592812;
    @ExcelProperty(value ="本月合同调整-H&BAward健与美")
    private BigDecimal tyadjCtaWivatAbtHwbP592812;
    @ExcelProperty(value ="本月对帐调整-H&BAward健与美")
    private BigDecimal tyadjRcaWivatAbtHwbP592812;
    @ExcelProperty(value ="以前年度合同调整-H&BAward健与美")
    private BigDecimal lyadjCtaWivatAbtHwbP592812;
    @ExcelProperty(value ="以前年度对帐调整-H&BAward健与美")
    private BigDecimal lyadjRcaWivatAbtHwbP592812;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-H&BAward健与美")
    private BigDecimal lyadjPraWivatAbtHwbP592812;
    @ExcelProperty(value ="H&BAward健与美Total")
    private BigDecimal totalWivatAbHwbP592812;
    @ExcelProperty(value ="本月计算(TTA)-其他")
    private BigDecimal tyWivatAbtOpbP592700;
    @ExcelProperty(value ="本月计算(ONTOP)-其他")
    private BigDecimal tyWivatAbotOpbP592700;
    @ExcelProperty(value ="以前年度计算(TTA)-其他")
    private BigDecimal lyadjWivatAbtOpbP592700;
    @ExcelProperty(value ="以前年度计算(ONTOP)-其他")
    private BigDecimal lyadjWivatAbotOpbP592700;
    @ExcelProperty(value ="本月计算(GL)-其他")
    private BigDecimal tyWivatAbGlOpbP592700;
    @ExcelProperty(value ="本月采购额计算-其他P*EM")
    private BigDecimal tyWivatAbPemOpbP592700;
    @ExcelProperty(value ="本月合同调整-其他")
    private BigDecimal tyadjCtaWivatAbtOpbP592700;
    @ExcelProperty(value ="本月对帐调整--其他")
    private BigDecimal tyadjRcaWivatAbtOpbP592700;
    @ExcelProperty(value ="以前年度合同调整--其他")
    private BigDecimal lyadjCtaWivatAbtOpbP592700;
    @ExcelProperty(value ="以前年度对帐调整--其他")
    private BigDecimal lyadjRcaWivatAbtOpbP592700;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-其他")
    private BigDecimal lyadjPraWivatAbtOpbP592700;
    @ExcelProperty(value ="其他Total")
    private BigDecimal totalWivatAbOpbP592700;
    @ExcelProperty(value ="本月计算(TTA)-市场推广服务费")
    private BigDecimal tyWivatAbtMktgP592811;
    @ExcelProperty(value ="本月计算(ONTOP)-市场推广服务费")
    private BigDecimal tyWivatAbotMktgP592811;
    @ExcelProperty(value ="以前年度计算(TTA)-市场推广服务费")
    private BigDecimal lyadjWivatAbtMktgP592811;
    @ExcelProperty(value ="以前年度计算(ONTOP)-市场推广服务费")
    private BigDecimal lyadjWivatAbotMktgP592811;
    @ExcelProperty(value ="本月合同调整-市场推广服务费")
    private BigDecimal tyadjCtaWivatAbtMktP592811;
    @ExcelProperty(value ="本月对帐调整-市场推广服务费")
    private BigDecimal tyadjRcaWivatAbtMktP592811;
    @ExcelProperty(value ="以前年度合同调整-市场推广服务费")
    private BigDecimal lyadjCtaWivatAbtMktP592811;
    @ExcelProperty(value ="以前年度对帐调整-市场推广服务费")
    private BigDecimal lyadjRcaWivatAbtMktP592811;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-市场推广服务费")
    private BigDecimal lyadjPraWivatAbtMktP592811;
    @ExcelProperty(value ="市场推广服务费Total")
    private BigDecimal totalWivatAbMktgP592811;
    @ExcelProperty(value ="本月计算(TTA)-非贸易收入")
    private BigDecimal tyWivatAbtNtP592760;
    @ExcelProperty(value ="本月计算(ONTOP)-非贸易收入")
    private BigDecimal tyWivatAbotNtP592760;
    @ExcelProperty(value ="以前年度计算(TTA)-非贸易收入")
    private BigDecimal lyadjWivatAbtNtP592760;
    @ExcelProperty(value ="以前年度计算(ONTOP)-非贸易收入")
    private BigDecimal lyadjWivatAbotNtP592760;
    @ExcelProperty(value ="本月合同调整-非贸易收入")
    private BigDecimal tyadjCtaWivatAbtNtP592760;
    @ExcelProperty(value ="本月对帐调整-非贸易收入")
    private BigDecimal tyadjRcaWivatAbtNtP592760;
    @ExcelProperty(value ="以前年度合同调整-非贸易收入")
    private BigDecimal lyadjCtaWivatAbtNtP592760;
    @ExcelProperty(value ="以前年度对帐调整-非贸易收入")
    private BigDecimal lyadjRcaWivatAbtNtP592760;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-非贸易收入")
    private BigDecimal lyadjPraWivatAbtNtP592760;
    @ExcelProperty(value ="非贸易收入Total")
    private BigDecimal totalWivatAbNtP592760;
    @ExcelProperty(value ="本月计算(TTA)-促销服务费")
    private BigDecimal tyWivatAbtSbaP592795;
    @ExcelProperty(value ="本月计算(ONTOP)-促销服务费")
    private BigDecimal tyWivatAbotSbaP592795;
    @ExcelProperty(value ="以前年度计算(TTA)-促销服务费")
    private BigDecimal lyadjWivatAbtSbaP592795;
    @ExcelProperty(value ="以前年度计算(ONTOP)-促销服务费")
    private BigDecimal lyadjWivatAbotSbaP592795;
    @ExcelProperty(value ="本月合同调整-促销服务费")
    private BigDecimal tyadjCtaWivatAbtSbaP592795;
    @ExcelProperty(value ="本月对帐调整-促销服务费")
    private BigDecimal tyadjRcaWivatAbtSbaP592795;
    @ExcelProperty(value ="以前年度合同调整-促销服务费")
    private BigDecimal lyadjCtaWivatAbtSbaP592795;
    @ExcelProperty(value ="以前年度对帐调整-促销服务费")
    private BigDecimal lyadjRcaWivatAbtSbaP592795;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-促销服务费")
    private BigDecimal lyadjPraWivatAbtSbaP592795;
    @ExcelProperty(value ="促销服务费Total")
    private BigDecimal totalWivatAbSbaP592795;
    @ExcelProperty(value ="本月计算(TTA)-其他促销服务费")
    private BigDecimal tyWivatAbtOpsP592874;
    @ExcelProperty(value ="本月计算(ONTOP)-其他促销服务费")
    private BigDecimal tyWivatAbotOpsP592874;
    @ExcelProperty(value ="以前年度计算(TTA)-其他促销服务费")
    private BigDecimal lyadjWivatAbtOpsP592874;
    @ExcelProperty(value ="以前年度计算(ONTOP)-其他促销服务费")
    private BigDecimal lyadjWivatAbotOpsP592874;
    @ExcelProperty(value ="本月合同调整-其他促销服务费")
    private BigDecimal tyadjCtaWivatAbtOpsP592874;
    @ExcelProperty(value ="本月对帐调整-其他促销服务费")
    private BigDecimal tyadjRcaWivatAbtOpsP592874;
    @ExcelProperty(value ="以前年度合同调整-其他促销服务费")
    private BigDecimal lyadjCtaWivatAbtOpsP592874;
    @ExcelProperty(value ="以前年度对帐调整-其他促销服务费")
    private BigDecimal lyadjRcaWivatAbtOpsP592874;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-其他促销服务费")
    private BigDecimal lyadjPraWivatAbtOpsP592874;
    @ExcelProperty(value ="其他促销服务费Total")
    private BigDecimal totalWivatAbOpsP592874;
    @ExcelProperty(value ="本月计算-新店宣传推广服务费")
    private BigDecimal tyWivatSrtNssP592802;
    @ExcelProperty(value ="本月合同调整-新店宣传推广服务费")
    private BigDecimal tyadjCtaWivatSrtNssP592802;
    @ExcelProperty(value ="本月对帐调整-新店宣传推广服务费")
    private BigDecimal tyadjRcaWivatSrtNssP592802;
    @ExcelProperty(value ="以前年度合同调整-新店宣传推广服务费")
    private BigDecimal lyadjCtaWivatSrtNssP592802;
    @ExcelProperty(value ="以前年度对帐调整-新店宣传推广服务费")
    private BigDecimal lyadjRcaWivatSrtNssP592802;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-新店宣传推广服务费")
    private BigDecimal lyadjPraWivatSrtNssP592802;
    @ExcelProperty(value ="新店宣传推广服务费total")
    private BigDecimal totalWivatSrtNssP592802;
    @ExcelProperty(value ="本月计算-店铺升级推广服务费")
    private BigDecimal tyWivatSrtRssP592756;
    @ExcelProperty(value ="本月合同调整-店铺升级推广服务费")
    private BigDecimal tyadjCtaWivatSrtRssP592756;
    @ExcelProperty(value ="本月对帐调整-店铺升级推广服务费")
    private BigDecimal tyadjRcaWivatSrtRssP592756;
    @ExcelProperty(value ="以前年度合同调整-店铺升级推广服务费")
    private BigDecimal lyadjCtaWivatSrtRssP592756;
    @ExcelProperty(value ="以前年度对帐调整-店铺升级推广服务费")
    private BigDecimal lyadjRcaWivatSrtRssP592756;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-店铺升级推广服务费")
    private BigDecimal lyadjPraWivatSrtRssP592756;
    @ExcelProperty(value ="店铺升级推广服务费Total")
    private BigDecimal totalWivatSrtRssP592756;
    @ExcelProperty(value ="本月计算-节庆促销服务费")
    private BigDecimal tyWivatSrtAssP592768;
    @ExcelProperty(value ="本月合同调整-节庆促销服务费")
    private BigDecimal tyadjCtaWivatSrtAssP592768;
    @ExcelProperty(value ="本月对帐调整-节庆促销服务费")
    private BigDecimal tyadjRcaWivatSrtAssP592768;
    @ExcelProperty(value ="以前年度合同调整-节庆促销服务费")
    private BigDecimal lyadjCtaWivatSrtAssP592768;
    @ExcelProperty(value ="以前年度对帐调整-节庆促销服务费")
    private BigDecimal lyadjRcaWivatSrtAssP592768;
    @ExcelProperty(value ="以前年度采购额计算/PRG调整/自查调整-节庆促销服务费")
    private BigDecimal lyadjPraWivatSrtAssP592768;
    @ExcelProperty(value ="节庆促销服务费Total")
    private BigDecimal totalWivatSrtAssP592768;
    @ExcelProperty(value ="本月计算-节庆促销服务费（38妇女节）")
    private BigDecimal tyWivatSrtWdsP592793;
    @ExcelProperty(value ="本月合同调整-节庆促销服务费（38妇女节）")
    private BigDecimal tyadjCtaWivatSrtWdsP592793;
    @ExcelProperty(value ="本月对帐调整-节庆促销服务费（38妇女节）")
    private BigDecimal tyadjRcaWivatSrtWdsP592793;
    @ExcelProperty(value ="以前年度合同调整-节庆促销服务费（38妇女节）")
    private BigDecimal lyadjCtaWivatSrtWdsP592793;
    @ExcelProperty(value ="以前年度对帐调整-节庆促销服务费（38妇女节）")
    private BigDecimal lyadjRcaWivatSrtWdsP592793;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-节庆促销服务费（38妇女节）")
    private BigDecimal lyadjPraWivatSrtWdsP592793;
    @ExcelProperty(value ="节庆促销服务费（38妇女节）Total")
    private BigDecimal totalWivatSrtWdsP592793;
    @ExcelProperty(value ="本月计算-新城市宣传推广服务费")
    private BigDecimal tyWivatSrtNcsP592813;
    @ExcelProperty(value ="本月合同调整-新城市宣传推广服务费")
    private BigDecimal tyadjCtaWivatSrtNcsP592813;
    @ExcelProperty(value ="本月对帐调整-新城市宣传推广服务费")
    private BigDecimal tyadjRcaWivatSrtNcsP592813;
    @ExcelProperty(value ="以前年度合同调整-新城市宣传推广服务费")
    private BigDecimal lyadjCtaWivatSrtNcsP592813;
    @ExcelProperty(value ="以前年度对帐调整-新城市宣传推广服务费")
    private BigDecimal lyadjRcaWivatSrtNcsP592813;
    @ExcelProperty(value ="以前年度PRG调整/自查调整-新城市宣传推广服务费")
    private BigDecimal lyadjPraWivatSrtNcsP592813;
    @ExcelProperty(value ="新城市宣传推广服务费Total")
    private BigDecimal totalWivatSrtNcsP592813;
    @ExcelProperty(value ="本月采购额计算-退货处理服务费")
    private BigDecimal tyWivatBeTtaRgsP592872;
    @ExcelProperty(value ="本月合同调整-退货处理服务费")
    private BigDecimal tyadjCtaWivatBetRgsP592872;
    @ExcelProperty(value ="本月对帐调整-退货处理服务费")
    private BigDecimal tyadjRcaWivatBetRgsP592872;
    @ExcelProperty(value ="以前年度合同调整-退货处理服务费")
    private BigDecimal lyadjCtaWivatBetRgsP592872;
    @ExcelProperty(value ="以前年度对帐调整-退货处理服务费")
    private BigDecimal lyadjRcaWivatBetRgsP592872;
    @ExcelProperty(value ="以前年度调整-PRG&自查-退货处理服务费")
    private BigDecimal lyadjPraWivatBetRgsP592872;
    @ExcelProperty(value ="退货处理服务费Total")
    private BigDecimal totalWivatBeTtaRgsP592872;
    @ExcelProperty(value ="本月计算-延误退货罚款")
    private BigDecimal tyWivatOttLpuP592794;
    @ExcelProperty(value ="本月合同调整-延误退货罚款")
    private BigDecimal tyadjCtaWivatOttLpuP592794;
    @ExcelProperty(value ="本月对帐调整-延误退货罚款")
    private BigDecimal tyadjRcaWivatOttLpuP592794;
    @ExcelProperty(value ="以前年度合同调整-延误退货罚款")
    private BigDecimal lyadjCtaWivatOttLpuP592794;
    @ExcelProperty(value ="以前年度对帐调整-延误退货罚款")
    private BigDecimal lyadjRcaWivatOttLpuP592794;
    @ExcelProperty(value ="以前年度调整-PRG&自查-延误退货罚款")
    private BigDecimal lyadjPraWivatOttLpuP592794;
    @ExcelProperty(value ="延误退货罚款Total")
    private BigDecimal totalWivatOttLpuP592794;
    @ExcelProperty(value ="本月计算-QA审查服务费")
    private BigDecimal tyWivatOttTfP592842;
    @ExcelProperty(value ="本月合同调整-QA审查服务费")
    private BigDecimal tyadjCtaWivatOttTfP592842;
    @ExcelProperty(value ="本月对帐调整-QA审查服务费")
    private BigDecimal tyadjRcaWivatOttTfP592842;
    @ExcelProperty(value ="以前年度合同调整-QA审查服务费")
    private BigDecimal lyadjCtaWivatOttTfP592842;
    @ExcelProperty(value ="以前年度对帐调整-QA审查服务费")
    private BigDecimal lyadjRcaWivatOttTfP592842;
    @ExcelProperty(value ="以前年度调整-PRG&自查-QA审查服务费")
    private BigDecimal lyadjPraWivatOttTfP592842;
    @ExcelProperty(value ="QA审查服务费Total")
    private BigDecimal totalWivatOttTfP592842;
    @ExcelProperty(value ="本月计算-赔偿(补偿)收入")
    private BigDecimal tyWivatOttCiP592836;
    @ExcelProperty(value ="本月采购额计算-赔偿(补偿)收入")
    private BigDecimal tyWivatOthSmCiP592836;
    @ExcelProperty(value ="本月合同调整-赔偿(补偿)收入")
    private BigDecimal tyadjCtaWivatOttCiP592836;
    @ExcelProperty(value ="本月对帐调整-赔偿(补偿)收入")
    private BigDecimal tyadjRcaWivatOttCiP592836;
    @ExcelProperty(value ="以前年度合同调整-赔偿(补偿)收入")
    private BigDecimal lyadjCtaWivatOttCiP592836;
    @ExcelProperty(value ="以前年度对帐调整-赔偿(补偿)收入")
    private BigDecimal lyadjRcaWivatOttCiP592836;
    @ExcelProperty(value ="以前年度调整-PRG&自查-赔偿(补偿)收入")
    private BigDecimal lyadjPraWivatOttCiP592836;
    @ExcelProperty(value ="赔偿(补偿)收入Total")
    private BigDecimal totalWivatOttCiP592836;
    @ExcelProperty(value ="本月计算-促销服务费(第三方)")
    private BigDecimal tyWivatOttBiP592868;
    @ExcelProperty(value ="本月合同调整-促销服务费(第三方)")
    private BigDecimal tyadjCtaWivatOttBiP592868;
    @ExcelProperty(value ="本月对帐调整-促销服务费(第三方)")
    private BigDecimal tyadjRcaWivatOttBiP592868;
    @ExcelProperty(value ="以前年度合同调整-促销服务费(第三方)")
    private BigDecimal lyadjCtaWivatOttBiP592868;
    @ExcelProperty(value ="以前年度对帐调整-促销服务费(第三方)")
    private BigDecimal lyadjRcaWivatOttBiP592868;
    @ExcelProperty(value ="以前年度调整-PRG&自查-促销服务费(第三方)")
    private BigDecimal lyadjPraWivatOttBiP592868;
    @ExcelProperty(value ="促销服务费(第三方)Total")
    private BigDecimal totalWivatOttBiP592868;

    @ExcelIgnore
    private Integer createdBy;

    @ExcelIgnore
    private Integer lastUpdatedBy;

    @ExcelIgnore
    private Date lastUpdateDate;

    @ExcelIgnore
    private Date creationDate;

    @ExcelIgnore
    private Integer lastUpdateLogin;

    @ExcelIgnore
    private Integer versionNum;

    @ExcelProperty(value ="本年度目标计算-目标退佣")
    private BigDecimal tyWivatBtIrP592951;

    public Integer getTtaOiSummaryLineId() {
        return ttaOiSummaryLineId;
    }

    public void setTtaOiSummaryLineId(Integer ttaOiSummaryLineId) {
        this.ttaOiSummaryLineId = ttaOiSummaryLineId;
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

    public String getFamilyPlaningFlag() {
        return familyPlaningFlag;
    }

    public void setFamilyPlaningFlag(String familyPlaningFlag) {
        this.familyPlaningFlag = familyPlaningFlag;
    }

    public String getVenderType() {
        return venderType;
    }

    public void setVenderType(String venderType) {
        this.venderType = venderType;
    }

    public BigDecimal getPurchase() {
        return purchase;
    }

    public void setPurchase(BigDecimal purchase) {
        this.purchase = purchase;
    }

    public BigDecimal getGoodsreturn() {
        return goodsreturn;
    }

    public void setGoodsreturn(BigDecimal goodsreturn) {
        this.goodsreturn = goodsreturn;
    }

    public BigDecimal getNetpurchase() {
        return netpurchase;
    }

    public void setNetpurchase(BigDecimal netpurchase) {
        this.netpurchase = netpurchase;
    }

    public BigDecimal getDsd() {
        return dsd;
    }

    public void setDsd(BigDecimal dsd) {
        this.dsd = dsd;
    }

    public BigDecimal getPurchaseorigin() {
        return purchaseorigin;
    }

    public void setPurchaseorigin(BigDecimal purchaseorigin) {
        this.purchaseorigin = purchaseorigin;
    }

    public BigDecimal getGoodsreturnorigin() {
        return goodsreturnorigin;
    }

    public void setGoodsreturnorigin(BigDecimal goodsreturnorigin) {
        this.goodsreturnorigin = goodsreturnorigin;
    }

    public BigDecimal getNetpurchaseorigin() {
        return netpurchaseorigin;
    }

    public void setNetpurchaseorigin(BigDecimal netpurchaseorigin) {
        this.netpurchaseorigin = netpurchaseorigin;
    }

    public BigDecimal getPypurchase() {
        return pypurchase;
    }

    public void setPypurchase(BigDecimal pypurchase) {
        this.pypurchase = pypurchase;
    }

    public BigDecimal getPygoodsreturn() {
        return pygoodsreturn;
    }

    public void setPygoodsreturn(BigDecimal pygoodsreturn) {
        this.pygoodsreturn = pygoodsreturn;
    }

    public BigDecimal getPynetpurchase() {
        return pynetpurchase;
    }

    public void setPynetpurchase(BigDecimal pynetpurchase) {
        this.pynetpurchase = pynetpurchase;
    }

    public BigDecimal getPydsd() {
        return pydsd;
    }

    public void setPydsd(BigDecimal pydsd) {
        this.pydsd = pydsd;
    }

    public BigDecimal getTyWivatBtFrP592950() {
        return tyWivatBtFrP592950;
    }

    public void setTyWivatBtFrP592950(BigDecimal tyWivatBtFrP592950) {
        this.tyWivatBtFrP592950 = tyWivatBtFrP592950;
    }

    public BigDecimal getTyadjCtaWivatBtFrP592950() {
        return tyadjCtaWivatBtFrP592950;
    }

    public void setTyadjCtaWivatBtFrP592950(BigDecimal tyadjCtaWivatBtFrP592950) {
        this.tyadjCtaWivatBtFrP592950 = tyadjCtaWivatBtFrP592950;
    }

    public BigDecimal getTyadjRcaWivatBtFrP592950() {
        return tyadjRcaWivatBtFrP592950;
    }

    public void setTyadjRcaWivatBtFrP592950(BigDecimal tyadjRcaWivatBtFrP592950) {
        this.tyadjRcaWivatBtFrP592950 = tyadjRcaWivatBtFrP592950;
    }

    public BigDecimal getLyadjCtaWivatBtFrP592950() {
        return lyadjCtaWivatBtFrP592950;
    }

    public void setLyadjCtaWivatBtFrP592950(BigDecimal lyadjCtaWivatBtFrP592950) {
        this.lyadjCtaWivatBtFrP592950 = lyadjCtaWivatBtFrP592950;
    }

    public BigDecimal getLyadjRcaWivatBtFrP592950() {
        return lyadjRcaWivatBtFrP592950;
    }

    public void setLyadjRcaWivatBtFrP592950(BigDecimal lyadjRcaWivatBtFrP592950) {
        this.lyadjRcaWivatBtFrP592950 = lyadjRcaWivatBtFrP592950;
    }

    public BigDecimal getLyadjPraWivatBtFrP592950() {
        return lyadjPraWivatBtFrP592950;
    }

    public void setLyadjPraWivatBtFrP592950(BigDecimal lyadjPraWivatBtFrP592950) {
        this.lyadjPraWivatBtFrP592950 = lyadjPraWivatBtFrP592950;
    }

    public BigDecimal getTotalWivatBtFrP592950() {
        return totalWivatBtFrP592950;
    }

    public void setTotalWivatBtFrP592950(BigDecimal totalWivatBtFrP592950) {
        this.totalWivatBtFrP592950 = totalWivatBtFrP592950;
    }

    public BigDecimal getTotalWovatBtFrP592950() {
        return totalWovatBtFrP592950;
    }

    public void setTotalWovatBtFrP592950(BigDecimal totalWovatBtFrP592950) {
        this.totalWovatBtFrP592950 = totalWovatBtFrP592950;
    }

    public BigDecimal getTyWivatBtEpdP592712() {
        return tyWivatBtEpdP592712;
    }

    public void setTyWivatBtEpdP592712(BigDecimal tyWivatBtEpdP592712) {
        this.tyWivatBtEpdP592712 = tyWivatBtEpdP592712;
    }

    public BigDecimal getTyadjCtaWivatBtEpdP592712() {
        return tyadjCtaWivatBtEpdP592712;
    }

    public void setTyadjCtaWivatBtEpdP592712(BigDecimal tyadjCtaWivatBtEpdP592712) {
        this.tyadjCtaWivatBtEpdP592712 = tyadjCtaWivatBtEpdP592712;
    }

    public BigDecimal getTyadjRcaWivatBtEpdP592712() {
        return tyadjRcaWivatBtEpdP592712;
    }

    public void setTyadjRcaWivatBtEpdP592712(BigDecimal tyadjRcaWivatBtEpdP592712) {
        this.tyadjRcaWivatBtEpdP592712 = tyadjRcaWivatBtEpdP592712;
    }

    public BigDecimal getLyadjCtaWivatBtEpdP592712() {
        return lyadjCtaWivatBtEpdP592712;
    }

    public void setLyadjCtaWivatBtEpdP592712(BigDecimal lyadjCtaWivatBtEpdP592712) {
        this.lyadjCtaWivatBtEpdP592712 = lyadjCtaWivatBtEpdP592712;
    }

    public BigDecimal getLyadjRcaWivatBtEpdP592712() {
        return lyadjRcaWivatBtEpdP592712;
    }

    public void setLyadjRcaWivatBtEpdP592712(BigDecimal lyadjRcaWivatBtEpdP592712) {
        this.lyadjRcaWivatBtEpdP592712 = lyadjRcaWivatBtEpdP592712;
    }

    public BigDecimal getLyadjPraWivatBtEpdP592712() {
        return lyadjPraWivatBtEpdP592712;
    }

    public void setLyadjPraWivatBtEpdP592712(BigDecimal lyadjPraWivatBtEpdP592712) {
        this.lyadjPraWivatBtEpdP592712 = lyadjPraWivatBtEpdP592712;
    }

    public BigDecimal getTotalWivatBtEpdP592712() {
        return totalWivatBtEpdP592712;
    }

    public void setTotalWivatBtEpdP592712(BigDecimal totalWivatBtEpdP592712) {
        this.totalWivatBtEpdP592712 = totalWivatBtEpdP592712;
    }

    public BigDecimal getTotalWovatBtEpdP592712() {
        return totalWovatBtEpdP592712;
    }

    public void setTotalWovatBtEpdP592712(BigDecimal totalWovatBtEpdP592712) {
        this.totalWovatBtEpdP592712 = totalWovatBtEpdP592712;
    }

    public BigDecimal getTyWivatBtPdP592803() {
        return tyWivatBtPdP592803;
    }

    public void setTyWivatBtPdP592803(BigDecimal tyWivatBtPdP592803) {
        this.tyWivatBtPdP592803 = tyWivatBtPdP592803;
    }

    public BigDecimal getTyadjCtaWivatBtPdP592803() {
        return tyadjCtaWivatBtPdP592803;
    }

    public void setTyadjCtaWivatBtPdP592803(BigDecimal tyadjCtaWivatBtPdP592803) {
        this.tyadjCtaWivatBtPdP592803 = tyadjCtaWivatBtPdP592803;
    }

    public BigDecimal getTyadjRcaWivatBtPdP592803() {
        return tyadjRcaWivatBtPdP592803;
    }

    public void setTyadjRcaWivatBtPdP592803(BigDecimal tyadjRcaWivatBtPdP592803) {
        this.tyadjRcaWivatBtPdP592803 = tyadjRcaWivatBtPdP592803;
    }

    public BigDecimal getLyadjCtaWivatBtPdP592803() {
        return lyadjCtaWivatBtPdP592803;
    }

    public void setLyadjCtaWivatBtPdP592803(BigDecimal lyadjCtaWivatBtPdP592803) {
        this.lyadjCtaWivatBtPdP592803 = lyadjCtaWivatBtPdP592803;
    }

    public BigDecimal getLyadjRcaWivatBtPdP592803() {
        return lyadjRcaWivatBtPdP592803;
    }

    public void setLyadjRcaWivatBtPdP592803(BigDecimal lyadjRcaWivatBtPdP592803) {
        this.lyadjRcaWivatBtPdP592803 = lyadjRcaWivatBtPdP592803;
    }

    public BigDecimal getLyadjPraWivatBtPdP592803() {
        return lyadjPraWivatBtPdP592803;
    }

    public void setLyadjPraWivatBtPdP592803(BigDecimal lyadjPraWivatBtPdP592803) {
        this.lyadjPraWivatBtPdP592803 = lyadjPraWivatBtPdP592803;
    }

    public BigDecimal getTotalWivatBtPdP592803() {
        return totalWivatBtPdP592803;
    }

    public void setTotalWivatBtPdP592803(BigDecimal totalWivatBtPdP592803) {
        this.totalWivatBtPdP592803 = totalWivatBtPdP592803;
    }

    public BigDecimal getTotalWovatBtPdP592803() {
        return totalWovatBtPdP592803;
    }

    public void setTotalWovatBtPdP592803(BigDecimal totalWovatBtPdP592803) {
        this.totalWovatBtPdP592803 = totalWovatBtPdP592803;
    }

    public BigDecimal getTyWivatBtDaP592901() {
        return tyWivatBtDaP592901;
    }

    public void setTyWivatBtDaP592901(BigDecimal tyWivatBtDaP592901) {
        this.tyWivatBtDaP592901 = tyWivatBtDaP592901;
    }

    public BigDecimal getTyadjCtaWivatBtDaP592901() {
        return tyadjCtaWivatBtDaP592901;
    }

    public void setTyadjCtaWivatBtDaP592901(BigDecimal tyadjCtaWivatBtDaP592901) {
        this.tyadjCtaWivatBtDaP592901 = tyadjCtaWivatBtDaP592901;
    }

    public BigDecimal getTyadjRcaWivatBtDaP592901() {
        return tyadjRcaWivatBtDaP592901;
    }

    public void setTyadjRcaWivatBtDaP592901(BigDecimal tyadjRcaWivatBtDaP592901) {
        this.tyadjRcaWivatBtDaP592901 = tyadjRcaWivatBtDaP592901;
    }

    public BigDecimal getLyadjCtaWivatBtDaP592901() {
        return lyadjCtaWivatBtDaP592901;
    }

    public void setLyadjCtaWivatBtDaP592901(BigDecimal lyadjCtaWivatBtDaP592901) {
        this.lyadjCtaWivatBtDaP592901 = lyadjCtaWivatBtDaP592901;
    }

    public BigDecimal getLyadjRcaWivatBtDaP592901() {
        return lyadjRcaWivatBtDaP592901;
    }

    public void setLyadjRcaWivatBtDaP592901(BigDecimal lyadjRcaWivatBtDaP592901) {
        this.lyadjRcaWivatBtDaP592901 = lyadjRcaWivatBtDaP592901;
    }

    public BigDecimal getLyadjPraWivatBtDaP592901() {
        return lyadjPraWivatBtDaP592901;
    }

    public void setLyadjPraWivatBtDaP592901(BigDecimal lyadjPraWivatBtDaP592901) {
        this.lyadjPraWivatBtDaP592901 = lyadjPraWivatBtDaP592901;
    }

    public BigDecimal getTotalWivatBtDaP592901() {
        return totalWivatBtDaP592901;
    }

    public void setTotalWivatBtDaP592901(BigDecimal totalWivatBtDaP592901) {
        this.totalWivatBtDaP592901 = totalWivatBtDaP592901;
    }

    public BigDecimal getTotalWovatBtDaP592901() {
        return totalWovatBtDaP592901;
    }

    public void setTotalWovatBtDaP592901(BigDecimal totalWovatBtDaP592901) {
        this.totalWovatBtDaP592901 = totalWovatBtDaP592901;
    }

    public BigDecimal getTyWivatBtDgaP592708() {
        return tyWivatBtDgaP592708;
    }

    public void setTyWivatBtDgaP592708(BigDecimal tyWivatBtDgaP592708) {
        this.tyWivatBtDgaP592708 = tyWivatBtDgaP592708;
    }

    public BigDecimal getTyadjCtaWivatBtDgaP592708() {
        return tyadjCtaWivatBtDgaP592708;
    }

    public void setTyadjCtaWivatBtDgaP592708(BigDecimal tyadjCtaWivatBtDgaP592708) {
        this.tyadjCtaWivatBtDgaP592708 = tyadjCtaWivatBtDgaP592708;
    }

    public BigDecimal getTyadjRcaWivatBtDgaP592708() {
        return tyadjRcaWivatBtDgaP592708;
    }

    public void setTyadjRcaWivatBtDgaP592708(BigDecimal tyadjRcaWivatBtDgaP592708) {
        this.tyadjRcaWivatBtDgaP592708 = tyadjRcaWivatBtDgaP592708;
    }

    public BigDecimal getLyadjCtaWivatBtDgaP592708() {
        return lyadjCtaWivatBtDgaP592708;
    }

    public void setLyadjCtaWivatBtDgaP592708(BigDecimal lyadjCtaWivatBtDgaP592708) {
        this.lyadjCtaWivatBtDgaP592708 = lyadjCtaWivatBtDgaP592708;
    }

    public BigDecimal getLyadjRcaWivatBtDgaP592708() {
        return lyadjRcaWivatBtDgaP592708;
    }

    public void setLyadjRcaWivatBtDgaP592708(BigDecimal lyadjRcaWivatBtDgaP592708) {
        this.lyadjRcaWivatBtDgaP592708 = lyadjRcaWivatBtDgaP592708;
    }

    public BigDecimal getLyadjPraWivatBtDgaP592708() {
        return lyadjPraWivatBtDgaP592708;
    }

    public void setLyadjPraWivatBtDgaP592708(BigDecimal lyadjPraWivatBtDgaP592708) {
        this.lyadjPraWivatBtDgaP592708 = lyadjPraWivatBtDgaP592708;
    }

    public BigDecimal getTotalWivatBtDgaP592708() {
        return totalWivatBtDgaP592708;
    }

    public void setTotalWivatBtDgaP592708(BigDecimal totalWivatBtDgaP592708) {
        this.totalWivatBtDgaP592708 = totalWivatBtDgaP592708;
    }

    public BigDecimal getTotalWovatBtDgaP592708() {
        return totalWovatBtDgaP592708;
    }

    public void setTotalWovatBtDgaP592708(BigDecimal totalWovatBtDgaP592708) {
        this.totalWovatBtDgaP592708 = totalWovatBtDgaP592708;
    }

    public BigDecimal getTyadjCtaWivatBtIrP592951() {
        return tyadjCtaWivatBtIrP592951;
    }

    public void setTyadjCtaWivatBtIrP592951(BigDecimal tyadjCtaWivatBtIrP592951) {
        this.tyadjCtaWivatBtIrP592951 = tyadjCtaWivatBtIrP592951;
    }

    public BigDecimal getTyadjRcaWivatBtIrP592951() {
        return tyadjRcaWivatBtIrP592951;
    }

    public void setTyadjRcaWivatBtIrP592951(BigDecimal tyadjRcaWivatBtIrP592951) {
        this.tyadjRcaWivatBtIrP592951 = tyadjRcaWivatBtIrP592951;
    }

    public BigDecimal getLyadjCtaWivatBtIrP592951() {
        return lyadjCtaWivatBtIrP592951;
    }

    public void setLyadjCtaWivatBtIrP592951(BigDecimal lyadjCtaWivatBtIrP592951) {
        this.lyadjCtaWivatBtIrP592951 = lyadjCtaWivatBtIrP592951;
    }

    public BigDecimal getLyadjRcaWivatBtIrP592951() {
        return lyadjRcaWivatBtIrP592951;
    }

    public void setLyadjRcaWivatBtIrP592951(BigDecimal lyadjRcaWivatBtIrP592951) {
        this.lyadjRcaWivatBtIrP592951 = lyadjRcaWivatBtIrP592951;
    }

    public BigDecimal getLyadjPraWivatBtIrP592951() {
        return lyadjPraWivatBtIrP592951;
    }

    public void setLyadjPraWivatBtIrP592951(BigDecimal lyadjPraWivatBtIrP592951) {
        this.lyadjPraWivatBtIrP592951 = lyadjPraWivatBtIrP592951;
    }

    public BigDecimal getTotalWivatBtIrP592951() {
        return totalWivatBtIrP592951;
    }

    public void setTotalWivatBtIrP592951(BigDecimal totalWivatBtIrP592951) {
        this.totalWivatBtIrP592951 = totalWivatBtIrP592951;
    }

    public BigDecimal getTotalWovatBtIrP592951() {
        return totalWovatBtIrP592951;
    }

    public void setTotalWovatBtIrP592951(BigDecimal totalWovatBtIrP592951) {
        this.totalWovatBtIrP592951 = totalWovatBtIrP592951;
    }

    public BigDecimal getTyWivatBtBdsP592792() {
        return tyWivatBtBdsP592792;
    }

    public void setTyWivatBtBdsP592792(BigDecimal tyWivatBtBdsP592792) {
        this.tyWivatBtBdsP592792 = tyWivatBtBdsP592792;
    }

    public BigDecimal getTyadjCtaWivatBtBdsP592792() {
        return tyadjCtaWivatBtBdsP592792;
    }

    public void setTyadjCtaWivatBtBdsP592792(BigDecimal tyadjCtaWivatBtBdsP592792) {
        this.tyadjCtaWivatBtBdsP592792 = tyadjCtaWivatBtBdsP592792;
    }

    public BigDecimal getTyadjRcaWivatBtBdsP592792() {
        return tyadjRcaWivatBtBdsP592792;
    }

    public void setTyadjRcaWivatBtBdsP592792(BigDecimal tyadjRcaWivatBtBdsP592792) {
        this.tyadjRcaWivatBtBdsP592792 = tyadjRcaWivatBtBdsP592792;
    }

    public BigDecimal getLyadjCtaWivatBtBdsP592792() {
        return lyadjCtaWivatBtBdsP592792;
    }

    public void setLyadjCtaWivatBtBdsP592792(BigDecimal lyadjCtaWivatBtBdsP592792) {
        this.lyadjCtaWivatBtBdsP592792 = lyadjCtaWivatBtBdsP592792;
    }

    public BigDecimal getLyadjRcaWivatBtBdsP592792() {
        return lyadjRcaWivatBtBdsP592792;
    }

    public void setLyadjRcaWivatBtBdsP592792(BigDecimal lyadjRcaWivatBtBdsP592792) {
        this.lyadjRcaWivatBtBdsP592792 = lyadjRcaWivatBtBdsP592792;
    }

    public BigDecimal getLyadjPraWivatBtBdsP592792() {
        return lyadjPraWivatBtBdsP592792;
    }

    public void setLyadjPraWivatBtBdsP592792(BigDecimal lyadjPraWivatBtBdsP592792) {
        this.lyadjPraWivatBtBdsP592792 = lyadjPraWivatBtBdsP592792;
    }

    public BigDecimal getTotalWivatBtBdsP592792() {
        return totalWivatBtBdsP592792;
    }

    public void setTotalWivatBtBdsP592792(BigDecimal totalWivatBtBdsP592792) {
        this.totalWivatBtBdsP592792 = totalWivatBtBdsP592792;
    }

    public BigDecimal getTotalWovatBtBdsP592792() {
        return totalWovatBtBdsP592792;
    }

    public void setTotalWovatBtBdsP592792(BigDecimal totalWovatBtBdsP592792) {
        this.totalWovatBtBdsP592792 = totalWovatBtBdsP592792;
    }

    public BigDecimal getTyWivatOtLdP592722() {
        return tyWivatOtLdP592722;
    }

    public void setTyWivatOtLdP592722(BigDecimal tyWivatOtLdP592722) {
        this.tyWivatOtLdP592722 = tyWivatOtLdP592722;
    }

    public BigDecimal getTyadjCtaWivatOtLdP592722() {
        return tyadjCtaWivatOtLdP592722;
    }

    public void setTyadjCtaWivatOtLdP592722(BigDecimal tyadjCtaWivatOtLdP592722) {
        this.tyadjCtaWivatOtLdP592722 = tyadjCtaWivatOtLdP592722;
    }

    public BigDecimal getTyadjRcaWivatOtLdP592722() {
        return tyadjRcaWivatOtLdP592722;
    }

    public void setTyadjRcaWivatOtLdP592722(BigDecimal tyadjRcaWivatOtLdP592722) {
        this.tyadjRcaWivatOtLdP592722 = tyadjRcaWivatOtLdP592722;
    }

    public BigDecimal getLyadjCtaWivatOtLdP592722() {
        return lyadjCtaWivatOtLdP592722;
    }

    public void setLyadjCtaWivatOtLdP592722(BigDecimal lyadjCtaWivatOtLdP592722) {
        this.lyadjCtaWivatOtLdP592722 = lyadjCtaWivatOtLdP592722;
    }

    public BigDecimal getLyadjRcaWivatOtLdP592722() {
        return lyadjRcaWivatOtLdP592722;
    }

    public void setLyadjRcaWivatOtLdP592722(BigDecimal lyadjRcaWivatOtLdP592722) {
        this.lyadjRcaWivatOtLdP592722 = lyadjRcaWivatOtLdP592722;
    }

    public BigDecimal getLyadjPraWivatOtLdP592722() {
        return lyadjPraWivatOtLdP592722;
    }

    public void setLyadjPraWivatOtLdP592722(BigDecimal lyadjPraWivatOtLdP592722) {
        this.lyadjPraWivatOtLdP592722 = lyadjPraWivatOtLdP592722;
    }

    public BigDecimal getTotalWivatOtLdP592722() {
        return totalWivatOtLdP592722;
    }

    public void setTotalWivatOtLdP592722(BigDecimal totalWivatOtLdP592722) {
        this.totalWivatOtLdP592722 = totalWivatOtLdP592722;
    }

    public BigDecimal getTotalWovatOtLdP592722() {
        return totalWovatOtLdP592722;
    }

    public void setTotalWovatOtLdP592722(BigDecimal totalWovatOtLdP592722) {
        this.totalWovatOtLdP592722 = totalWovatOtLdP592722;
    }

    public BigDecimal getTyWivatOtNfP592780() {
        return tyWivatOtNfP592780;
    }

    public void setTyWivatOtNfP592780(BigDecimal tyWivatOtNfP592780) {
        this.tyWivatOtNfP592780 = tyWivatOtNfP592780;
    }

    public BigDecimal getTyadjCtaWivatOtNfP592780() {
        return tyadjCtaWivatOtNfP592780;
    }

    public void setTyadjCtaWivatOtNfP592780(BigDecimal tyadjCtaWivatOtNfP592780) {
        this.tyadjCtaWivatOtNfP592780 = tyadjCtaWivatOtNfP592780;
    }

    public BigDecimal getTyadjRcaWivatOtNfP592780() {
        return tyadjRcaWivatOtNfP592780;
    }

    public void setTyadjRcaWivatOtNfP592780(BigDecimal tyadjRcaWivatOtNfP592780) {
        this.tyadjRcaWivatOtNfP592780 = tyadjRcaWivatOtNfP592780;
    }

    public BigDecimal getLyadjCtaWivatOtNfP592780() {
        return lyadjCtaWivatOtNfP592780;
    }

    public void setLyadjCtaWivatOtNfP592780(BigDecimal lyadjCtaWivatOtNfP592780) {
        this.lyadjCtaWivatOtNfP592780 = lyadjCtaWivatOtNfP592780;
    }

    public BigDecimal getLyadjRcaWivatOtNfP592780() {
        return lyadjRcaWivatOtNfP592780;
    }

    public void setLyadjRcaWivatOtNfP592780(BigDecimal lyadjRcaWivatOtNfP592780) {
        this.lyadjRcaWivatOtNfP592780 = lyadjRcaWivatOtNfP592780;
    }

    public BigDecimal getLyadjPraWivatOtNfP592780() {
        return lyadjPraWivatOtNfP592780;
    }

    public void setLyadjPraWivatOtNfP592780(BigDecimal lyadjPraWivatOtNfP592780) {
        this.lyadjPraWivatOtNfP592780 = lyadjPraWivatOtNfP592780;
    }

    public BigDecimal getTotalWivatOtNfP592780() {
        return totalWivatOtNfP592780;
    }

    public void setTotalWivatOtNfP592780(BigDecimal totalWivatOtNfP592780) {
        this.totalWivatOtNfP592780 = totalWivatOtNfP592780;
    }

    public BigDecimal getTotalWovatOtNfP592780() {
        return totalWovatOtNfP592780;
    }

    public void setTotalWovatOtNfP592780(BigDecimal totalWovatOtNfP592780) {
        this.totalWovatOtNfP592780 = totalWovatOtNfP592780;
    }

    public BigDecimal getTyWivatOtCpnP592743() {
        return tyWivatOtCpnP592743;
    }

    public void setTyWivatOtCpnP592743(BigDecimal tyWivatOtCpnP592743) {
        this.tyWivatOtCpnP592743 = tyWivatOtCpnP592743;
    }

    public BigDecimal getTyadjCtaWivatOtCpnP592743() {
        return tyadjCtaWivatOtCpnP592743;
    }

    public void setTyadjCtaWivatOtCpnP592743(BigDecimal tyadjCtaWivatOtCpnP592743) {
        this.tyadjCtaWivatOtCpnP592743 = tyadjCtaWivatOtCpnP592743;
    }

    public BigDecimal getTyadjRcaWivatOtCpnP592743() {
        return tyadjRcaWivatOtCpnP592743;
    }

    public void setTyadjRcaWivatOtCpnP592743(BigDecimal tyadjRcaWivatOtCpnP592743) {
        this.tyadjRcaWivatOtCpnP592743 = tyadjRcaWivatOtCpnP592743;
    }

    public BigDecimal getLyadjCtaWivatOtCpnP592743() {
        return lyadjCtaWivatOtCpnP592743;
    }

    public void setLyadjCtaWivatOtCpnP592743(BigDecimal lyadjCtaWivatOtCpnP592743) {
        this.lyadjCtaWivatOtCpnP592743 = lyadjCtaWivatOtCpnP592743;
    }

    public BigDecimal getLyadjRcaWivatOtCpnP592743() {
        return lyadjRcaWivatOtCpnP592743;
    }

    public void setLyadjRcaWivatOtCpnP592743(BigDecimal lyadjRcaWivatOtCpnP592743) {
        this.lyadjRcaWivatOtCpnP592743 = lyadjRcaWivatOtCpnP592743;
    }

    public BigDecimal getLyadjPraWivatOtCpnP592743() {
        return lyadjPraWivatOtCpnP592743;
    }

    public void setLyadjPraWivatOtCpnP592743(BigDecimal lyadjPraWivatOtCpnP592743) {
        this.lyadjPraWivatOtCpnP592743 = lyadjPraWivatOtCpnP592743;
    }

    public BigDecimal getTotalWivatOtCpnP592743() {
        return totalWivatOtCpnP592743;
    }

    public void setTotalWivatOtCpnP592743(BigDecimal totalWivatOtCpnP592743) {
        this.totalWivatOtCpnP592743 = totalWivatOtCpnP592743;
    }

    public BigDecimal getTotalWovatOtLpP592743() {
        return totalWovatOtLpP592743;
    }

    public void setTotalWovatOtLpP592743(BigDecimal totalWovatOtLpP592743) {
        this.totalWovatOtLpP592743 = totalWovatOtLpP592743;
    }

    public BigDecimal getTyWivatOtLpP500320() {
        return tyWivatOtLpP500320;
    }

    public void setTyWivatOtLpP500320(BigDecimal tyWivatOtLpP500320) {
        this.tyWivatOtLpP500320 = tyWivatOtLpP500320;
    }

    public BigDecimal getTyadjCtaWivatOtLpP500320() {
        return tyadjCtaWivatOtLpP500320;
    }

    public void setTyadjCtaWivatOtLpP500320(BigDecimal tyadjCtaWivatOtLpP500320) {
        this.tyadjCtaWivatOtLpP500320 = tyadjCtaWivatOtLpP500320;
    }

    public BigDecimal getTyadjRcaWivatOtLpP500320() {
        return tyadjRcaWivatOtLpP500320;
    }

    public void setTyadjRcaWivatOtLpP500320(BigDecimal tyadjRcaWivatOtLpP500320) {
        this.tyadjRcaWivatOtLpP500320 = tyadjRcaWivatOtLpP500320;
    }

    public BigDecimal getLyadjCtaWivatOtLpP500320() {
        return lyadjCtaWivatOtLpP500320;
    }

    public void setLyadjCtaWivatOtLpP500320(BigDecimal lyadjCtaWivatOtLpP500320) {
        this.lyadjCtaWivatOtLpP500320 = lyadjCtaWivatOtLpP500320;
    }

    public BigDecimal getLyadjRcaWivatOtLpP500320() {
        return lyadjRcaWivatOtLpP500320;
    }

    public void setLyadjRcaWivatOtLpP500320(BigDecimal lyadjRcaWivatOtLpP500320) {
        this.lyadjRcaWivatOtLpP500320 = lyadjRcaWivatOtLpP500320;
    }

    public BigDecimal getLyadjPraWivatOtLpP500320() {
        return lyadjPraWivatOtLpP500320;
    }

    public void setLyadjPraWivatOtLpP500320(BigDecimal lyadjPraWivatOtLpP500320) {
        this.lyadjPraWivatOtLpP500320 = lyadjPraWivatOtLpP500320;
    }

    public BigDecimal getTotalWivatOtLpP500320() {
        return totalWivatOtLpP500320;
    }

    public void setTotalWivatOtLpP500320(BigDecimal totalWivatOtLpP500320) {
        this.totalWivatOtLpP500320 = totalWivatOtLpP500320;
    }

    public BigDecimal getTotalWovatOtLpP500320() {
        return totalWovatOtLpP500320;
    }

    public void setTotalWovatOtLpP500320(BigDecimal totalWovatOtLpP500320) {
        this.totalWovatOtLpP500320 = totalWovatOtLpP500320;
    }

    public BigDecimal getTyWivatBtUepdP592796() {
        return tyWivatBtUepdP592796;
    }

    public void setTyWivatBtUepdP592796(BigDecimal tyWivatBtUepdP592796) {
        this.tyWivatBtUepdP592796 = tyWivatBtUepdP592796;
    }

    public BigDecimal getTyadjCtaWivatBtUepdP592796() {
        return tyadjCtaWivatBtUepdP592796;
    }

    public void setTyadjCtaWivatBtUepdP592796(BigDecimal tyadjCtaWivatBtUepdP592796) {
        this.tyadjCtaWivatBtUepdP592796 = tyadjCtaWivatBtUepdP592796;
    }

    public BigDecimal getTyadjRcaWivatBtUepdP592796() {
        return tyadjRcaWivatBtUepdP592796;
    }

    public void setTyadjRcaWivatBtUepdP592796(BigDecimal tyadjRcaWivatBtUepdP592796) {
        this.tyadjRcaWivatBtUepdP592796 = tyadjRcaWivatBtUepdP592796;
    }

    public BigDecimal getLyadjCtaWivatBtUepdP592796() {
        return lyadjCtaWivatBtUepdP592796;
    }

    public void setLyadjCtaWivatBtUepdP592796(BigDecimal lyadjCtaWivatBtUepdP592796) {
        this.lyadjCtaWivatBtUepdP592796 = lyadjCtaWivatBtUepdP592796;
    }

    public BigDecimal getLyadjRcaWivatBtUepdP592796() {
        return lyadjRcaWivatBtUepdP592796;
    }

    public void setLyadjRcaWivatBtUepdP592796(BigDecimal lyadjRcaWivatBtUepdP592796) {
        this.lyadjRcaWivatBtUepdP592796 = lyadjRcaWivatBtUepdP592796;
    }

    public BigDecimal getLyadjPraWivatBtUepdP592796() {
        return lyadjPraWivatBtUepdP592796;
    }

    public void setLyadjPraWivatBtUepdP592796(BigDecimal lyadjPraWivatBtUepdP592796) {
        this.lyadjPraWivatBtUepdP592796 = lyadjPraWivatBtUepdP592796;
    }

    public BigDecimal getTotalWivatBtUepdP592796() {
        return totalWivatBtUepdP592796;
    }

    public void setTotalWivatBtUepdP592796(BigDecimal totalWivatBtUepdP592796) {
        this.totalWivatBtUepdP592796 = totalWivatBtUepdP592796;
    }

    public BigDecimal getTotalWovatBtUepdP592796() {
        return totalWovatBtUepdP592796;
    }

    public void setTotalWovatBtUepdP592796(BigDecimal totalWovatBtUepdP592796) {
        this.totalWovatBtUepdP592796 = totalWovatBtUepdP592796;
    }

    public BigDecimal getTyWivatAbtCriP592738() {
        return tyWivatAbtCriP592738;
    }

    public void setTyWivatAbtCriP592738(BigDecimal tyWivatAbtCriP592738) {
        this.tyWivatAbtCriP592738 = tyWivatAbtCriP592738;
    }

    public BigDecimal getTyWivatAbotCriP592738() {
        return tyWivatAbotCriP592738;
    }

    public void setTyWivatAbotCriP592738(BigDecimal tyWivatAbotCriP592738) {
        this.tyWivatAbotCriP592738 = tyWivatAbotCriP592738;
    }

    public BigDecimal getLyadjWivatAbtCriP592738() {
        return lyadjWivatAbtCriP592738;
    }

    public void setLyadjWivatAbtCriP592738(BigDecimal lyadjWivatAbtCriP592738) {
        this.lyadjWivatAbtCriP592738 = lyadjWivatAbtCriP592738;
    }

    public BigDecimal getLyadjWivatAbotCriP592738() {
        return lyadjWivatAbotCriP592738;
    }

    public void setLyadjWivatAbotCriP592738(BigDecimal lyadjWivatAbotCriP592738) {
        this.lyadjWivatAbotCriP592738 = lyadjWivatAbotCriP592738;
    }

    public BigDecimal getTyadjCtaWivatAbtCriP592738() {
        return tyadjCtaWivatAbtCriP592738;
    }

    public void setTyadjCtaWivatAbtCriP592738(BigDecimal tyadjCtaWivatAbtCriP592738) {
        this.tyadjCtaWivatAbtCriP592738 = tyadjCtaWivatAbtCriP592738;
    }

    public BigDecimal getTyadjRcaWivatAbtCriP592738() {
        return tyadjRcaWivatAbtCriP592738;
    }

    public void setTyadjRcaWivatAbtCriP592738(BigDecimal tyadjRcaWivatAbtCriP592738) {
        this.tyadjRcaWivatAbtCriP592738 = tyadjRcaWivatAbtCriP592738;
    }

    public BigDecimal getLyadjCtaWivatAbtCriP592738() {
        return lyadjCtaWivatAbtCriP592738;
    }

    public void setLyadjCtaWivatAbtCriP592738(BigDecimal lyadjCtaWivatAbtCriP592738) {
        this.lyadjCtaWivatAbtCriP592738 = lyadjCtaWivatAbtCriP592738;
    }

    public BigDecimal getLyadjRcaWivatAbtCriP592738() {
        return lyadjRcaWivatAbtCriP592738;
    }

    public void setLyadjRcaWivatAbtCriP592738(BigDecimal lyadjRcaWivatAbtCriP592738) {
        this.lyadjRcaWivatAbtCriP592738 = lyadjRcaWivatAbtCriP592738;
    }

    public BigDecimal getLyadjPraWivatAbtCriP592738() {
        return lyadjPraWivatAbtCriP592738;
    }

    public void setLyadjPraWivatAbtCriP592738(BigDecimal lyadjPraWivatAbtCriP592738) {
        this.lyadjPraWivatAbtCriP592738 = lyadjPraWivatAbtCriP592738;
    }

    public BigDecimal getTotalWivatAbCriP592738() {
        return totalWivatAbCriP592738;
    }

    public void setTotalWivatAbCriP592738(BigDecimal totalWivatAbCriP592738) {
        this.totalWivatAbCriP592738 = totalWivatAbCriP592738;
    }

    public BigDecimal getTotalWovatAbCriP592738() {
        return totalWovatAbCriP592738;
    }

    public void setTotalWovatAbCriP592738(BigDecimal totalWovatAbCriP592738) {
        this.totalWovatAbCriP592738 = totalWovatAbCriP592738;
    }

    public BigDecimal getTyWivatAbtOemtP592873() {
        return tyWivatAbtOemtP592873;
    }

    public void setTyWivatAbtOemtP592873(BigDecimal tyWivatAbtOemtP592873) {
        this.tyWivatAbtOemtP592873 = tyWivatAbtOemtP592873;
    }

    public BigDecimal getTyWivatAbotOemtP592873() {
        return tyWivatAbotOemtP592873;
    }

    public void setTyWivatAbotOemtP592873(BigDecimal tyWivatAbotOemtP592873) {
        this.tyWivatAbotOemtP592873 = tyWivatAbotOemtP592873;
    }

    public BigDecimal getLyadjWivatAbtOemtP592873() {
        return lyadjWivatAbtOemtP592873;
    }

    public void setLyadjWivatAbtOemtP592873(BigDecimal lyadjWivatAbtOemtP592873) {
        this.lyadjWivatAbtOemtP592873 = lyadjWivatAbtOemtP592873;
    }

    public BigDecimal getLyadjWivatAbotOemtP592873() {
        return lyadjWivatAbotOemtP592873;
    }

    public void setLyadjWivatAbotOemtP592873(BigDecimal lyadjWivatAbotOemtP592873) {
        this.lyadjWivatAbotOemtP592873 = lyadjWivatAbotOemtP592873;
    }

    public BigDecimal getTyadjCtaWivatAbtOemP592873() {
        return tyadjCtaWivatAbtOemP592873;
    }

    public void setTyadjCtaWivatAbtOemP592873(BigDecimal tyadjCtaWivatAbtOemP592873) {
        this.tyadjCtaWivatAbtOemP592873 = tyadjCtaWivatAbtOemP592873;
    }

    public BigDecimal getTyadjRcaWivatAbtOemP592873() {
        return tyadjRcaWivatAbtOemP592873;
    }

    public void setTyadjRcaWivatAbtOemP592873(BigDecimal tyadjRcaWivatAbtOemP592873) {
        this.tyadjRcaWivatAbtOemP592873 = tyadjRcaWivatAbtOemP592873;
    }

    public BigDecimal getLyadjCtaWivatAbtOemP592873() {
        return lyadjCtaWivatAbtOemP592873;
    }

    public void setLyadjCtaWivatAbtOemP592873(BigDecimal lyadjCtaWivatAbtOemP592873) {
        this.lyadjCtaWivatAbtOemP592873 = lyadjCtaWivatAbtOemP592873;
    }

    public BigDecimal getLyadjRcaWivatAbtOemP592873() {
        return lyadjRcaWivatAbtOemP592873;
    }

    public void setLyadjRcaWivatAbtOemP592873(BigDecimal lyadjRcaWivatAbtOemP592873) {
        this.lyadjRcaWivatAbtOemP592873 = lyadjRcaWivatAbtOemP592873;
    }

    public BigDecimal getLyadjPraWivatAbtOemP592873() {
        return lyadjPraWivatAbtOemP592873;
    }

    public void setLyadjPraWivatAbtOemP592873(BigDecimal lyadjPraWivatAbtOemP592873) {
        this.lyadjPraWivatAbtOemP592873 = lyadjPraWivatAbtOemP592873;
    }

    public BigDecimal getTotalWivatAbOemtP592873() {
        return totalWivatAbOemtP592873;
    }

    public void setTotalWivatAbOemtP592873(BigDecimal totalWivatAbOemtP592873) {
        this.totalWivatAbOemtP592873 = totalWivatAbOemtP592873;
    }

    public BigDecimal getTotalWovatAbtOemtP592873() {
        return totalWovatAbtOemtP592873;
    }

    public void setTotalWovatAbtOemtP592873(BigDecimal totalWovatAbtOemtP592873) {
        this.totalWovatAbtOemtP592873 = totalWovatAbtOemtP592873;
    }

    public BigDecimal getTyWivatAbtDsP592814() {
        return tyWivatAbtDsP592814;
    }

    public void setTyWivatAbtDsP592814(BigDecimal tyWivatAbtDsP592814) {
        this.tyWivatAbtDsP592814 = tyWivatAbtDsP592814;
    }

    public BigDecimal getTyWivatAbotDsP592814() {
        return tyWivatAbotDsP592814;
    }

    public void setTyWivatAbotDsP592814(BigDecimal tyWivatAbotDsP592814) {
        this.tyWivatAbotDsP592814 = tyWivatAbotDsP592814;
    }

    public BigDecimal getLyadjWivatAbtDsP592814() {
        return lyadjWivatAbtDsP592814;
    }

    public void setLyadjWivatAbtDsP592814(BigDecimal lyadjWivatAbtDsP592814) {
        this.lyadjWivatAbtDsP592814 = lyadjWivatAbtDsP592814;
    }

    public BigDecimal getLyadjWivatAbotDsP592814() {
        return lyadjWivatAbotDsP592814;
    }

    public void setLyadjWivatAbotDsP592814(BigDecimal lyadjWivatAbotDsP592814) {
        this.lyadjWivatAbotDsP592814 = lyadjWivatAbotDsP592814;
    }

    public BigDecimal getTyadjCtaWivatAbtDsP592814() {
        return tyadjCtaWivatAbtDsP592814;
    }

    public void setTyadjCtaWivatAbtDsP592814(BigDecimal tyadjCtaWivatAbtDsP592814) {
        this.tyadjCtaWivatAbtDsP592814 = tyadjCtaWivatAbtDsP592814;
    }

    public BigDecimal getTyadjRcaWivatAbtDsP592814() {
        return tyadjRcaWivatAbtDsP592814;
    }

    public void setTyadjRcaWivatAbtDsP592814(BigDecimal tyadjRcaWivatAbtDsP592814) {
        this.tyadjRcaWivatAbtDsP592814 = tyadjRcaWivatAbtDsP592814;
    }

    public BigDecimal getLyadjCtaWivatAbtDsP592814() {
        return lyadjCtaWivatAbtDsP592814;
    }

    public void setLyadjCtaWivatAbtDsP592814(BigDecimal lyadjCtaWivatAbtDsP592814) {
        this.lyadjCtaWivatAbtDsP592814 = lyadjCtaWivatAbtDsP592814;
    }

    public BigDecimal getLyadjRcaWivatAbtDsP592814() {
        return lyadjRcaWivatAbtDsP592814;
    }

    public void setLyadjRcaWivatAbtDsP592814(BigDecimal lyadjRcaWivatAbtDsP592814) {
        this.lyadjRcaWivatAbtDsP592814 = lyadjRcaWivatAbtDsP592814;
    }

    public BigDecimal getLyadjPraWivatAbtDsP592814() {
        return lyadjPraWivatAbtDsP592814;
    }

    public void setLyadjPraWivatAbtDsP592814(BigDecimal lyadjPraWivatAbtDsP592814) {
        this.lyadjPraWivatAbtDsP592814 = lyadjPraWivatAbtDsP592814;
    }

    public BigDecimal getTotalWivatAbDsP592814() {
        return totalWivatAbDsP592814;
    }

    public void setTotalWivatAbDsP592814(BigDecimal totalWivatAbDsP592814) {
        this.totalWivatAbDsP592814 = totalWivatAbDsP592814;
    }

    public BigDecimal getTyWivatAbtDroP592810() {
        return tyWivatAbtDroP592810;
    }

    public void setTyWivatAbtDroP592810(BigDecimal tyWivatAbtDroP592810) {
        this.tyWivatAbtDroP592810 = tyWivatAbtDroP592810;
    }

    public BigDecimal getTyWivatAbotDroP592810() {
        return tyWivatAbotDroP592810;
    }

    public void setTyWivatAbotDroP592810(BigDecimal tyWivatAbotDroP592810) {
        this.tyWivatAbotDroP592810 = tyWivatAbotDroP592810;
    }

    public BigDecimal getLyadjWivatAbtDroP592810() {
        return lyadjWivatAbtDroP592810;
    }

    public void setLyadjWivatAbtDroP592810(BigDecimal lyadjWivatAbtDroP592810) {
        this.lyadjWivatAbtDroP592810 = lyadjWivatAbtDroP592810;
    }

    public BigDecimal getLyadjWivatAbotDroP592810() {
        return lyadjWivatAbotDroP592810;
    }

    public void setLyadjWivatAbotDroP592810(BigDecimal lyadjWivatAbotDroP592810) {
        this.lyadjWivatAbotDroP592810 = lyadjWivatAbotDroP592810;
    }

    public BigDecimal getTyadjCtaWivatAbtDroP592810() {
        return tyadjCtaWivatAbtDroP592810;
    }

    public void setTyadjCtaWivatAbtDroP592810(BigDecimal tyadjCtaWivatAbtDroP592810) {
        this.tyadjCtaWivatAbtDroP592810 = tyadjCtaWivatAbtDroP592810;
    }

    public BigDecimal getTyadjRcaWivatAbtDroP592810() {
        return tyadjRcaWivatAbtDroP592810;
    }

    public void setTyadjRcaWivatAbtDroP592810(BigDecimal tyadjRcaWivatAbtDroP592810) {
        this.tyadjRcaWivatAbtDroP592810 = tyadjRcaWivatAbtDroP592810;
    }

    public BigDecimal getLyadjCtaWivatAbtDroP592810() {
        return lyadjCtaWivatAbtDroP592810;
    }

    public void setLyadjCtaWivatAbtDroP592810(BigDecimal lyadjCtaWivatAbtDroP592810) {
        this.lyadjCtaWivatAbtDroP592810 = lyadjCtaWivatAbtDroP592810;
    }

    public BigDecimal getLyadjRcaWivatAbtDroP592810() {
        return lyadjRcaWivatAbtDroP592810;
    }

    public void setLyadjRcaWivatAbtDroP592810(BigDecimal lyadjRcaWivatAbtDroP592810) {
        this.lyadjRcaWivatAbtDroP592810 = lyadjRcaWivatAbtDroP592810;
    }

    public BigDecimal getLyadjPraWivatAbtDroP592810() {
        return lyadjPraWivatAbtDroP592810;
    }

    public void setLyadjPraWivatAbtDroP592810(BigDecimal lyadjPraWivatAbtDroP592810) {
        this.lyadjPraWivatAbtDroP592810 = lyadjPraWivatAbtDroP592810;
    }

    public BigDecimal getTotalWivatAbDroP592810() {
        return totalWivatAbDroP592810;
    }

    public void setTotalWivatAbDroP592810(BigDecimal totalWivatAbDroP592810) {
        this.totalWivatAbDroP592810 = totalWivatAbDroP592810;
    }

    public BigDecimal getTyWivatAbtDrntP592871() {
        return tyWivatAbtDrntP592871;
    }

    public void setTyWivatAbtDrntP592871(BigDecimal tyWivatAbtDrntP592871) {
        this.tyWivatAbtDrntP592871 = tyWivatAbtDrntP592871;
    }

    public BigDecimal getTyWivatAbotDrntP592871() {
        return tyWivatAbotDrntP592871;
    }

    public void setTyWivatAbotDrntP592871(BigDecimal tyWivatAbotDrntP592871) {
        this.tyWivatAbotDrntP592871 = tyWivatAbotDrntP592871;
    }

    public BigDecimal getLyadjWivatAbtDrntP592871() {
        return lyadjWivatAbtDrntP592871;
    }

    public void setLyadjWivatAbtDrntP592871(BigDecimal lyadjWivatAbtDrntP592871) {
        this.lyadjWivatAbtDrntP592871 = lyadjWivatAbtDrntP592871;
    }

    public BigDecimal getLyadjWivatAbotDrntP592871() {
        return lyadjWivatAbotDrntP592871;
    }

    public void setLyadjWivatAbotDrntP592871(BigDecimal lyadjWivatAbotDrntP592871) {
        this.lyadjWivatAbotDrntP592871 = lyadjWivatAbotDrntP592871;
    }

    public BigDecimal getTyadjCtaWivatAbtDrnP592871() {
        return tyadjCtaWivatAbtDrnP592871;
    }

    public void setTyadjCtaWivatAbtDrnP592871(BigDecimal tyadjCtaWivatAbtDrnP592871) {
        this.tyadjCtaWivatAbtDrnP592871 = tyadjCtaWivatAbtDrnP592871;
    }

    public BigDecimal getTyadjRcaWivatAbtDrnP592871() {
        return tyadjRcaWivatAbtDrnP592871;
    }

    public void setTyadjRcaWivatAbtDrnP592871(BigDecimal tyadjRcaWivatAbtDrnP592871) {
        this.tyadjRcaWivatAbtDrnP592871 = tyadjRcaWivatAbtDrnP592871;
    }

    public BigDecimal getLyadjCtaWivatAbtDrnP592871() {
        return lyadjCtaWivatAbtDrnP592871;
    }

    public void setLyadjCtaWivatAbtDrnP592871(BigDecimal lyadjCtaWivatAbtDrnP592871) {
        this.lyadjCtaWivatAbtDrnP592871 = lyadjCtaWivatAbtDrnP592871;
    }

    public BigDecimal getLyadjRcaWivatAbtDrnP592871() {
        return lyadjRcaWivatAbtDrnP592871;
    }

    public void setLyadjRcaWivatAbtDrnP592871(BigDecimal lyadjRcaWivatAbtDrnP592871) {
        this.lyadjRcaWivatAbtDrnP592871 = lyadjRcaWivatAbtDrnP592871;
    }

    public BigDecimal getLyadjPraWivatAbtDrnP592871() {
        return lyadjPraWivatAbtDrnP592871;
    }

    public void setLyadjPraWivatAbtDrnP592871(BigDecimal lyadjPraWivatAbtDrnP592871) {
        this.lyadjPraWivatAbtDrnP592871 = lyadjPraWivatAbtDrnP592871;
    }

    public BigDecimal getTotalWivatAbDrntP592871() {
        return totalWivatAbDrntP592871;
    }

    public void setTotalWivatAbDrntP592871(BigDecimal totalWivatAbDrntP592871) {
        this.totalWivatAbDrntP592871 = totalWivatAbDrntP592871;
    }

    public BigDecimal getTyWivatAbtCrP592715() {
        return tyWivatAbtCrP592715;
    }

    public void setTyWivatAbtCrP592715(BigDecimal tyWivatAbtCrP592715) {
        this.tyWivatAbtCrP592715 = tyWivatAbtCrP592715;
    }

    public BigDecimal getTyWivatAbotCrP592715() {
        return tyWivatAbotCrP592715;
    }

    public void setTyWivatAbotCrP592715(BigDecimal tyWivatAbotCrP592715) {
        this.tyWivatAbotCrP592715 = tyWivatAbotCrP592715;
    }

    public BigDecimal getLyadjWivatAbtCrP592715() {
        return lyadjWivatAbtCrP592715;
    }

    public void setLyadjWivatAbtCrP592715(BigDecimal lyadjWivatAbtCrP592715) {
        this.lyadjWivatAbtCrP592715 = lyadjWivatAbtCrP592715;
    }

    public BigDecimal getLyadjWivatAbotCrP592715() {
        return lyadjWivatAbotCrP592715;
    }

    public void setLyadjWivatAbotCrP592715(BigDecimal lyadjWivatAbotCrP592715) {
        this.lyadjWivatAbotCrP592715 = lyadjWivatAbotCrP592715;
    }

    public BigDecimal getTyadjCtaWivatAbtCrP592715() {
        return tyadjCtaWivatAbtCrP592715;
    }

    public void setTyadjCtaWivatAbtCrP592715(BigDecimal tyadjCtaWivatAbtCrP592715) {
        this.tyadjCtaWivatAbtCrP592715 = tyadjCtaWivatAbtCrP592715;
    }

    public BigDecimal getTyadjRcaWivatAbtCrP592715() {
        return tyadjRcaWivatAbtCrP592715;
    }

    public void setTyadjRcaWivatAbtCrP592715(BigDecimal tyadjRcaWivatAbtCrP592715) {
        this.tyadjRcaWivatAbtCrP592715 = tyadjRcaWivatAbtCrP592715;
    }

    public BigDecimal getLyadjCtaWivatAbtCrP592715() {
        return lyadjCtaWivatAbtCrP592715;
    }

    public void setLyadjCtaWivatAbtCrP592715(BigDecimal lyadjCtaWivatAbtCrP592715) {
        this.lyadjCtaWivatAbtCrP592715 = lyadjCtaWivatAbtCrP592715;
    }

    public BigDecimal getLyadjRcaWivatAbtCrP592715() {
        return lyadjRcaWivatAbtCrP592715;
    }

    public void setLyadjRcaWivatAbtCrP592715(BigDecimal lyadjRcaWivatAbtCrP592715) {
        this.lyadjRcaWivatAbtCrP592715 = lyadjRcaWivatAbtCrP592715;
    }

    public BigDecimal getLyadjPraWivatAbtCrP592715() {
        return lyadjPraWivatAbtCrP592715;
    }

    public void setLyadjPraWivatAbtCrP592715(BigDecimal lyadjPraWivatAbtCrP592715) {
        this.lyadjPraWivatAbtCrP592715 = lyadjPraWivatAbtCrP592715;
    }

    public BigDecimal getTotalWivatAbCrP592715() {
        return totalWivatAbCrP592715;
    }

    public void setTotalWivatAbCrP592715(BigDecimal totalWivatAbCrP592715) {
        this.totalWivatAbCrP592715 = totalWivatAbCrP592715;
    }

    public BigDecimal getTyWivatAbtDmP592751() {
        return tyWivatAbtDmP592751;
    }

    public void setTyWivatAbtDmP592751(BigDecimal tyWivatAbtDmP592751) {
        this.tyWivatAbtDmP592751 = tyWivatAbtDmP592751;
    }

    public BigDecimal getTyWivatAbotDmP592751() {
        return tyWivatAbotDmP592751;
    }

    public void setTyWivatAbotDmP592751(BigDecimal tyWivatAbotDmP592751) {
        this.tyWivatAbotDmP592751 = tyWivatAbotDmP592751;
    }

    public BigDecimal getLyadjWivatAbtDmP592751() {
        return lyadjWivatAbtDmP592751;
    }

    public void setLyadjWivatAbtDmP592751(BigDecimal lyadjWivatAbtDmP592751) {
        this.lyadjWivatAbtDmP592751 = lyadjWivatAbtDmP592751;
    }

    public BigDecimal getLyadjWivatAbotDmP592751() {
        return lyadjWivatAbotDmP592751;
    }

    public void setLyadjWivatAbotDmP592751(BigDecimal lyadjWivatAbotDmP592751) {
        this.lyadjWivatAbotDmP592751 = lyadjWivatAbotDmP592751;
    }

    public BigDecimal getTyadjCtaWivatAbtDmP592751() {
        return tyadjCtaWivatAbtDmP592751;
    }

    public void setTyadjCtaWivatAbtDmP592751(BigDecimal tyadjCtaWivatAbtDmP592751) {
        this.tyadjCtaWivatAbtDmP592751 = tyadjCtaWivatAbtDmP592751;
    }

    public BigDecimal getTyadjRcaWivatAbtDmP592751() {
        return tyadjRcaWivatAbtDmP592751;
    }

    public void setTyadjRcaWivatAbtDmP592751(BigDecimal tyadjRcaWivatAbtDmP592751) {
        this.tyadjRcaWivatAbtDmP592751 = tyadjRcaWivatAbtDmP592751;
    }

    public BigDecimal getLyadjCtaWivatAbtDmP592751() {
        return lyadjCtaWivatAbtDmP592751;
    }

    public void setLyadjCtaWivatAbtDmP592751(BigDecimal lyadjCtaWivatAbtDmP592751) {
        this.lyadjCtaWivatAbtDmP592751 = lyadjCtaWivatAbtDmP592751;
    }

    public BigDecimal getLyadjRcaWivatAbtDmP592751() {
        return lyadjRcaWivatAbtDmP592751;
    }

    public void setLyadjRcaWivatAbtDmP592751(BigDecimal lyadjRcaWivatAbtDmP592751) {
        this.lyadjRcaWivatAbtDmP592751 = lyadjRcaWivatAbtDmP592751;
    }

    public BigDecimal getLyadjPraWivatAbtDmP592751() {
        return lyadjPraWivatAbtDmP592751;
    }

    public void setLyadjPraWivatAbtDmP592751(BigDecimal lyadjPraWivatAbtDmP592751) {
        this.lyadjPraWivatAbtDmP592751 = lyadjPraWivatAbtDmP592751;
    }

    public BigDecimal getTotalWivatAbDmP592751() {
        return totalWivatAbDmP592751;
    }

    public void setTotalWivatAbDmP592751(BigDecimal totalWivatAbDmP592751) {
        this.totalWivatAbDmP592751 = totalWivatAbDmP592751;
    }

    public BigDecimal getTyWivatAbtNppP592804() {
        return tyWivatAbtNppP592804;
    }

    public void setTyWivatAbtNppP592804(BigDecimal tyWivatAbtNppP592804) {
        this.tyWivatAbtNppP592804 = tyWivatAbtNppP592804;
    }

    public BigDecimal getTyWivatAbotNppP592804() {
        return tyWivatAbotNppP592804;
    }

    public void setTyWivatAbotNppP592804(BigDecimal tyWivatAbotNppP592804) {
        this.tyWivatAbotNppP592804 = tyWivatAbotNppP592804;
    }

    public BigDecimal getLyadjWivatAbtNppP592804() {
        return lyadjWivatAbtNppP592804;
    }

    public void setLyadjWivatAbtNppP592804(BigDecimal lyadjWivatAbtNppP592804) {
        this.lyadjWivatAbtNppP592804 = lyadjWivatAbtNppP592804;
    }

    public BigDecimal getLyadjWivatAbotNppP592804() {
        return lyadjWivatAbotNppP592804;
    }

    public void setLyadjWivatAbotNppP592804(BigDecimal lyadjWivatAbotNppP592804) {
        this.lyadjWivatAbotNppP592804 = lyadjWivatAbotNppP592804;
    }

    public BigDecimal getTyadjCtaWivatAbtNppP592804() {
        return tyadjCtaWivatAbtNppP592804;
    }

    public void setTyadjCtaWivatAbtNppP592804(BigDecimal tyadjCtaWivatAbtNppP592804) {
        this.tyadjCtaWivatAbtNppP592804 = tyadjCtaWivatAbtNppP592804;
    }

    public BigDecimal getTyadjRcaWivatAbtNppP592804() {
        return tyadjRcaWivatAbtNppP592804;
    }

    public void setTyadjRcaWivatAbtNppP592804(BigDecimal tyadjRcaWivatAbtNppP592804) {
        this.tyadjRcaWivatAbtNppP592804 = tyadjRcaWivatAbtNppP592804;
    }

    public BigDecimal getLyadjCtaWivatAbtNppP592804() {
        return lyadjCtaWivatAbtNppP592804;
    }

    public void setLyadjCtaWivatAbtNppP592804(BigDecimal lyadjCtaWivatAbtNppP592804) {
        this.lyadjCtaWivatAbtNppP592804 = lyadjCtaWivatAbtNppP592804;
    }

    public BigDecimal getLyadjRcaWivatAbtNppP592804() {
        return lyadjRcaWivatAbtNppP592804;
    }

    public void setLyadjRcaWivatAbtNppP592804(BigDecimal lyadjRcaWivatAbtNppP592804) {
        this.lyadjRcaWivatAbtNppP592804 = lyadjRcaWivatAbtNppP592804;
    }

    public BigDecimal getLyadjPraWivatAbtNppP592804() {
        return lyadjPraWivatAbtNppP592804;
    }

    public void setLyadjPraWivatAbtNppP592804(BigDecimal lyadjPraWivatAbtNppP592804) {
        this.lyadjPraWivatAbtNppP592804 = lyadjPraWivatAbtNppP592804;
    }

    public BigDecimal getTotalWivatAbNppP592804() {
        return totalWivatAbNppP592804;
    }

    public void setTotalWivatAbNppP592804(BigDecimal totalWivatAbNppP592804) {
        this.totalWivatAbNppP592804 = totalWivatAbNppP592804;
    }

    public BigDecimal getTyWivatAbtBdfP592726() {
        return tyWivatAbtBdfP592726;
    }

    public void setTyWivatAbtBdfP592726(BigDecimal tyWivatAbtBdfP592726) {
        this.tyWivatAbtBdfP592726 = tyWivatAbtBdfP592726;
    }

    public BigDecimal getTyWivatAbotBdfP592726() {
        return tyWivatAbotBdfP592726;
    }

    public void setTyWivatAbotBdfP592726(BigDecimal tyWivatAbotBdfP592726) {
        this.tyWivatAbotBdfP592726 = tyWivatAbotBdfP592726;
    }

    public BigDecimal getLyadjWivatAbtBdfP592726() {
        return lyadjWivatAbtBdfP592726;
    }

    public void setLyadjWivatAbtBdfP592726(BigDecimal lyadjWivatAbtBdfP592726) {
        this.lyadjWivatAbtBdfP592726 = lyadjWivatAbtBdfP592726;
    }

    public BigDecimal getLyadjWivatAbotBdfP592726() {
        return lyadjWivatAbotBdfP592726;
    }

    public void setLyadjWivatAbotBdfP592726(BigDecimal lyadjWivatAbotBdfP592726) {
        this.lyadjWivatAbotBdfP592726 = lyadjWivatAbotBdfP592726;
    }

    public BigDecimal getTyadjCtaWivatAbtBdfP592726() {
        return tyadjCtaWivatAbtBdfP592726;
    }

    public void setTyadjCtaWivatAbtBdfP592726(BigDecimal tyadjCtaWivatAbtBdfP592726) {
        this.tyadjCtaWivatAbtBdfP592726 = tyadjCtaWivatAbtBdfP592726;
    }

    public BigDecimal getTyadjRcaWivatAbtBdfP592726() {
        return tyadjRcaWivatAbtBdfP592726;
    }

    public void setTyadjRcaWivatAbtBdfP592726(BigDecimal tyadjRcaWivatAbtBdfP592726) {
        this.tyadjRcaWivatAbtBdfP592726 = tyadjRcaWivatAbtBdfP592726;
    }

    public BigDecimal getLyadjCtaWivatAbtBdfP592726() {
        return lyadjCtaWivatAbtBdfP592726;
    }

    public void setLyadjCtaWivatAbtBdfP592726(BigDecimal lyadjCtaWivatAbtBdfP592726) {
        this.lyadjCtaWivatAbtBdfP592726 = lyadjCtaWivatAbtBdfP592726;
    }

    public BigDecimal getLyadjRcaWivatAbtBdfP592726() {
        return lyadjRcaWivatAbtBdfP592726;
    }

    public void setLyadjRcaWivatAbtBdfP592726(BigDecimal lyadjRcaWivatAbtBdfP592726) {
        this.lyadjRcaWivatAbtBdfP592726 = lyadjRcaWivatAbtBdfP592726;
    }

    public BigDecimal getLyadjPraWivatAbtBdfP592726() {
        return lyadjPraWivatAbtBdfP592726;
    }

    public void setLyadjPraWivatAbtBdfP592726(BigDecimal lyadjPraWivatAbtBdfP592726) {
        this.lyadjPraWivatAbtBdfP592726 = lyadjPraWivatAbtBdfP592726;
    }

    public BigDecimal getTotalWivatAbBdfP592726() {
        return totalWivatAbBdfP592726;
    }

    public void setTotalWivatAbBdfP592726(BigDecimal totalWivatAbBdfP592726) {
        this.totalWivatAbBdfP592726 = totalWivatAbBdfP592726;
    }

    public BigDecimal getTyWivatAbtHwbP592812() {
        return tyWivatAbtHwbP592812;
    }

    public void setTyWivatAbtHwbP592812(BigDecimal tyWivatAbtHwbP592812) {
        this.tyWivatAbtHwbP592812 = tyWivatAbtHwbP592812;
    }

    public BigDecimal getTyWivatAbotHwbP592812() {
        return tyWivatAbotHwbP592812;
    }

    public void setTyWivatAbotHwbP592812(BigDecimal tyWivatAbotHwbP592812) {
        this.tyWivatAbotHwbP592812 = tyWivatAbotHwbP592812;
    }

    public BigDecimal getLyadjWivatAbtHwbP592812() {
        return lyadjWivatAbtHwbP592812;
    }

    public void setLyadjWivatAbtHwbP592812(BigDecimal lyadjWivatAbtHwbP592812) {
        this.lyadjWivatAbtHwbP592812 = lyadjWivatAbtHwbP592812;
    }

    public BigDecimal getLyadjWivatAbotHwbP592812() {
        return lyadjWivatAbotHwbP592812;
    }

    public void setLyadjWivatAbotHwbP592812(BigDecimal lyadjWivatAbotHwbP592812) {
        this.lyadjWivatAbotHwbP592812 = lyadjWivatAbotHwbP592812;
    }

    public BigDecimal getTyadjCtaWivatAbtHwbP592812() {
        return tyadjCtaWivatAbtHwbP592812;
    }

    public void setTyadjCtaWivatAbtHwbP592812(BigDecimal tyadjCtaWivatAbtHwbP592812) {
        this.tyadjCtaWivatAbtHwbP592812 = tyadjCtaWivatAbtHwbP592812;
    }

    public BigDecimal getTyadjRcaWivatAbtHwbP592812() {
        return tyadjRcaWivatAbtHwbP592812;
    }

    public void setTyadjRcaWivatAbtHwbP592812(BigDecimal tyadjRcaWivatAbtHwbP592812) {
        this.tyadjRcaWivatAbtHwbP592812 = tyadjRcaWivatAbtHwbP592812;
    }

    public BigDecimal getLyadjCtaWivatAbtHwbP592812() {
        return lyadjCtaWivatAbtHwbP592812;
    }

    public void setLyadjCtaWivatAbtHwbP592812(BigDecimal lyadjCtaWivatAbtHwbP592812) {
        this.lyadjCtaWivatAbtHwbP592812 = lyadjCtaWivatAbtHwbP592812;
    }

    public BigDecimal getLyadjRcaWivatAbtHwbP592812() {
        return lyadjRcaWivatAbtHwbP592812;
    }

    public void setLyadjRcaWivatAbtHwbP592812(BigDecimal lyadjRcaWivatAbtHwbP592812) {
        this.lyadjRcaWivatAbtHwbP592812 = lyadjRcaWivatAbtHwbP592812;
    }

    public BigDecimal getLyadjPraWivatAbtHwbP592812() {
        return lyadjPraWivatAbtHwbP592812;
    }

    public void setLyadjPraWivatAbtHwbP592812(BigDecimal lyadjPraWivatAbtHwbP592812) {
        this.lyadjPraWivatAbtHwbP592812 = lyadjPraWivatAbtHwbP592812;
    }

    public BigDecimal getTotalWivatAbHwbP592812() {
        return totalWivatAbHwbP592812;
    }

    public void setTotalWivatAbHwbP592812(BigDecimal totalWivatAbHwbP592812) {
        this.totalWivatAbHwbP592812 = totalWivatAbHwbP592812;
    }

    public BigDecimal getTyWivatAbtOpbP592700() {
        return tyWivatAbtOpbP592700;
    }

    public void setTyWivatAbtOpbP592700(BigDecimal tyWivatAbtOpbP592700) {
        this.tyWivatAbtOpbP592700 = tyWivatAbtOpbP592700;
    }

    public BigDecimal getTyWivatAbotOpbP592700() {
        return tyWivatAbotOpbP592700;
    }

    public void setTyWivatAbotOpbP592700(BigDecimal tyWivatAbotOpbP592700) {
        this.tyWivatAbotOpbP592700 = tyWivatAbotOpbP592700;
    }

    public BigDecimal getLyadjWivatAbtOpbP592700() {
        return lyadjWivatAbtOpbP592700;
    }

    public void setLyadjWivatAbtOpbP592700(BigDecimal lyadjWivatAbtOpbP592700) {
        this.lyadjWivatAbtOpbP592700 = lyadjWivatAbtOpbP592700;
    }

    public BigDecimal getLyadjWivatAbotOpbP592700() {
        return lyadjWivatAbotOpbP592700;
    }

    public void setLyadjWivatAbotOpbP592700(BigDecimal lyadjWivatAbotOpbP592700) {
        this.lyadjWivatAbotOpbP592700 = lyadjWivatAbotOpbP592700;
    }

    public BigDecimal getTyWivatAbGlOpbP592700() {
        return tyWivatAbGlOpbP592700;
    }

    public void setTyWivatAbGlOpbP592700(BigDecimal tyWivatAbGlOpbP592700) {
        this.tyWivatAbGlOpbP592700 = tyWivatAbGlOpbP592700;
    }

    public BigDecimal getTyWivatAbPemOpbP592700() {
        return tyWivatAbPemOpbP592700;
    }

    public void setTyWivatAbPemOpbP592700(BigDecimal tyWivatAbPemOpbP592700) {
        this.tyWivatAbPemOpbP592700 = tyWivatAbPemOpbP592700;
    }

    public BigDecimal getTyadjCtaWivatAbtOpbP592700() {
        return tyadjCtaWivatAbtOpbP592700;
    }

    public void setTyadjCtaWivatAbtOpbP592700(BigDecimal tyadjCtaWivatAbtOpbP592700) {
        this.tyadjCtaWivatAbtOpbP592700 = tyadjCtaWivatAbtOpbP592700;
    }

    public BigDecimal getTyadjRcaWivatAbtOpbP592700() {
        return tyadjRcaWivatAbtOpbP592700;
    }

    public void setTyadjRcaWivatAbtOpbP592700(BigDecimal tyadjRcaWivatAbtOpbP592700) {
        this.tyadjRcaWivatAbtOpbP592700 = tyadjRcaWivatAbtOpbP592700;
    }

    public BigDecimal getLyadjCtaWivatAbtOpbP592700() {
        return lyadjCtaWivatAbtOpbP592700;
    }

    public void setLyadjCtaWivatAbtOpbP592700(BigDecimal lyadjCtaWivatAbtOpbP592700) {
        this.lyadjCtaWivatAbtOpbP592700 = lyadjCtaWivatAbtOpbP592700;
    }

    public BigDecimal getLyadjRcaWivatAbtOpbP592700() {
        return lyadjRcaWivatAbtOpbP592700;
    }

    public void setLyadjRcaWivatAbtOpbP592700(BigDecimal lyadjRcaWivatAbtOpbP592700) {
        this.lyadjRcaWivatAbtOpbP592700 = lyadjRcaWivatAbtOpbP592700;
    }

    public BigDecimal getLyadjPraWivatAbtOpbP592700() {
        return lyadjPraWivatAbtOpbP592700;
    }

    public void setLyadjPraWivatAbtOpbP592700(BigDecimal lyadjPraWivatAbtOpbP592700) {
        this.lyadjPraWivatAbtOpbP592700 = lyadjPraWivatAbtOpbP592700;
    }

    public BigDecimal getTotalWivatAbOpbP592700() {
        return totalWivatAbOpbP592700;
    }

    public void setTotalWivatAbOpbP592700(BigDecimal totalWivatAbOpbP592700) {
        this.totalWivatAbOpbP592700 = totalWivatAbOpbP592700;
    }

    public BigDecimal getTyWivatAbtMktgP592811() {
        return tyWivatAbtMktgP592811;
    }

    public void setTyWivatAbtMktgP592811(BigDecimal tyWivatAbtMktgP592811) {
        this.tyWivatAbtMktgP592811 = tyWivatAbtMktgP592811;
    }

    public BigDecimal getTyWivatAbotMktgP592811() {
        return tyWivatAbotMktgP592811;
    }

    public void setTyWivatAbotMktgP592811(BigDecimal tyWivatAbotMktgP592811) {
        this.tyWivatAbotMktgP592811 = tyWivatAbotMktgP592811;
    }

    public BigDecimal getLyadjWivatAbtMktgP592811() {
        return lyadjWivatAbtMktgP592811;
    }

    public void setLyadjWivatAbtMktgP592811(BigDecimal lyadjWivatAbtMktgP592811) {
        this.lyadjWivatAbtMktgP592811 = lyadjWivatAbtMktgP592811;
    }

    public BigDecimal getLyadjWivatAbotMktgP592811() {
        return lyadjWivatAbotMktgP592811;
    }

    public void setLyadjWivatAbotMktgP592811(BigDecimal lyadjWivatAbotMktgP592811) {
        this.lyadjWivatAbotMktgP592811 = lyadjWivatAbotMktgP592811;
    }

    public BigDecimal getTyadjCtaWivatAbtMktP592811() {
        return tyadjCtaWivatAbtMktP592811;
    }

    public void setTyadjCtaWivatAbtMktP592811(BigDecimal tyadjCtaWivatAbtMktP592811) {
        this.tyadjCtaWivatAbtMktP592811 = tyadjCtaWivatAbtMktP592811;
    }

    public BigDecimal getTyadjRcaWivatAbtMktP592811() {
        return tyadjRcaWivatAbtMktP592811;
    }

    public void setTyadjRcaWivatAbtMktP592811(BigDecimal tyadjRcaWivatAbtMktP592811) {
        this.tyadjRcaWivatAbtMktP592811 = tyadjRcaWivatAbtMktP592811;
    }

    public BigDecimal getLyadjCtaWivatAbtMktP592811() {
        return lyadjCtaWivatAbtMktP592811;
    }

    public void setLyadjCtaWivatAbtMktP592811(BigDecimal lyadjCtaWivatAbtMktP592811) {
        this.lyadjCtaWivatAbtMktP592811 = lyadjCtaWivatAbtMktP592811;
    }

    public BigDecimal getLyadjRcaWivatAbtMktP592811() {
        return lyadjRcaWivatAbtMktP592811;
    }

    public void setLyadjRcaWivatAbtMktP592811(BigDecimal lyadjRcaWivatAbtMktP592811) {
        this.lyadjRcaWivatAbtMktP592811 = lyadjRcaWivatAbtMktP592811;
    }

    public BigDecimal getLyadjPraWivatAbtMktP592811() {
        return lyadjPraWivatAbtMktP592811;
    }

    public void setLyadjPraWivatAbtMktP592811(BigDecimal lyadjPraWivatAbtMktP592811) {
        this.lyadjPraWivatAbtMktP592811 = lyadjPraWivatAbtMktP592811;
    }

    public BigDecimal getTotalWivatAbMktgP592811() {
        return totalWivatAbMktgP592811;
    }

    public void setTotalWivatAbMktgP592811(BigDecimal totalWivatAbMktgP592811) {
        this.totalWivatAbMktgP592811 = totalWivatAbMktgP592811;
    }

    public BigDecimal getTyWivatAbtNtP592760() {
        return tyWivatAbtNtP592760;
    }

    public void setTyWivatAbtNtP592760(BigDecimal tyWivatAbtNtP592760) {
        this.tyWivatAbtNtP592760 = tyWivatAbtNtP592760;
    }

    public BigDecimal getTyWivatAbotNtP592760() {
        return tyWivatAbotNtP592760;
    }

    public void setTyWivatAbotNtP592760(BigDecimal tyWivatAbotNtP592760) {
        this.tyWivatAbotNtP592760 = tyWivatAbotNtP592760;
    }

    public BigDecimal getLyadjWivatAbtNtP592760() {
        return lyadjWivatAbtNtP592760;
    }

    public void setLyadjWivatAbtNtP592760(BigDecimal lyadjWivatAbtNtP592760) {
        this.lyadjWivatAbtNtP592760 = lyadjWivatAbtNtP592760;
    }

    public BigDecimal getLyadjWivatAbotNtP592760() {
        return lyadjWivatAbotNtP592760;
    }

    public void setLyadjWivatAbotNtP592760(BigDecimal lyadjWivatAbotNtP592760) {
        this.lyadjWivatAbotNtP592760 = lyadjWivatAbotNtP592760;
    }

    public BigDecimal getTyadjCtaWivatAbtNtP592760() {
        return tyadjCtaWivatAbtNtP592760;
    }

    public void setTyadjCtaWivatAbtNtP592760(BigDecimal tyadjCtaWivatAbtNtP592760) {
        this.tyadjCtaWivatAbtNtP592760 = tyadjCtaWivatAbtNtP592760;
    }

    public BigDecimal getTyadjRcaWivatAbtNtP592760() {
        return tyadjRcaWivatAbtNtP592760;
    }

    public void setTyadjRcaWivatAbtNtP592760(BigDecimal tyadjRcaWivatAbtNtP592760) {
        this.tyadjRcaWivatAbtNtP592760 = tyadjRcaWivatAbtNtP592760;
    }

    public BigDecimal getLyadjCtaWivatAbtNtP592760() {
        return lyadjCtaWivatAbtNtP592760;
    }

    public void setLyadjCtaWivatAbtNtP592760(BigDecimal lyadjCtaWivatAbtNtP592760) {
        this.lyadjCtaWivatAbtNtP592760 = lyadjCtaWivatAbtNtP592760;
    }

    public BigDecimal getLyadjRcaWivatAbtNtP592760() {
        return lyadjRcaWivatAbtNtP592760;
    }

    public void setLyadjRcaWivatAbtNtP592760(BigDecimal lyadjRcaWivatAbtNtP592760) {
        this.lyadjRcaWivatAbtNtP592760 = lyadjRcaWivatAbtNtP592760;
    }

    public BigDecimal getLyadjPraWivatAbtNtP592760() {
        return lyadjPraWivatAbtNtP592760;
    }

    public void setLyadjPraWivatAbtNtP592760(BigDecimal lyadjPraWivatAbtNtP592760) {
        this.lyadjPraWivatAbtNtP592760 = lyadjPraWivatAbtNtP592760;
    }

    public BigDecimal getTotalWivatAbNtP592760() {
        return totalWivatAbNtP592760;
    }

    public void setTotalWivatAbNtP592760(BigDecimal totalWivatAbNtP592760) {
        this.totalWivatAbNtP592760 = totalWivatAbNtP592760;
    }

    public BigDecimal getTyWivatAbtSbaP592795() {
        return tyWivatAbtSbaP592795;
    }

    public void setTyWivatAbtSbaP592795(BigDecimal tyWivatAbtSbaP592795) {
        this.tyWivatAbtSbaP592795 = tyWivatAbtSbaP592795;
    }

    public BigDecimal getTyWivatAbotSbaP592795() {
        return tyWivatAbotSbaP592795;
    }

    public void setTyWivatAbotSbaP592795(BigDecimal tyWivatAbotSbaP592795) {
        this.tyWivatAbotSbaP592795 = tyWivatAbotSbaP592795;
    }

    public BigDecimal getLyadjWivatAbtSbaP592795() {
        return lyadjWivatAbtSbaP592795;
    }

    public void setLyadjWivatAbtSbaP592795(BigDecimal lyadjWivatAbtSbaP592795) {
        this.lyadjWivatAbtSbaP592795 = lyadjWivatAbtSbaP592795;
    }

    public BigDecimal getLyadjWivatAbotSbaP592795() {
        return lyadjWivatAbotSbaP592795;
    }

    public void setLyadjWivatAbotSbaP592795(BigDecimal lyadjWivatAbotSbaP592795) {
        this.lyadjWivatAbotSbaP592795 = lyadjWivatAbotSbaP592795;
    }

    public BigDecimal getTyadjCtaWivatAbtSbaP592795() {
        return tyadjCtaWivatAbtSbaP592795;
    }

    public void setTyadjCtaWivatAbtSbaP592795(BigDecimal tyadjCtaWivatAbtSbaP592795) {
        this.tyadjCtaWivatAbtSbaP592795 = tyadjCtaWivatAbtSbaP592795;
    }

    public BigDecimal getTyadjRcaWivatAbtSbaP592795() {
        return tyadjRcaWivatAbtSbaP592795;
    }

    public void setTyadjRcaWivatAbtSbaP592795(BigDecimal tyadjRcaWivatAbtSbaP592795) {
        this.tyadjRcaWivatAbtSbaP592795 = tyadjRcaWivatAbtSbaP592795;
    }

    public BigDecimal getLyadjCtaWivatAbtSbaP592795() {
        return lyadjCtaWivatAbtSbaP592795;
    }

    public void setLyadjCtaWivatAbtSbaP592795(BigDecimal lyadjCtaWivatAbtSbaP592795) {
        this.lyadjCtaWivatAbtSbaP592795 = lyadjCtaWivatAbtSbaP592795;
    }

    public BigDecimal getLyadjRcaWivatAbtSbaP592795() {
        return lyadjRcaWivatAbtSbaP592795;
    }

    public void setLyadjRcaWivatAbtSbaP592795(BigDecimal lyadjRcaWivatAbtSbaP592795) {
        this.lyadjRcaWivatAbtSbaP592795 = lyadjRcaWivatAbtSbaP592795;
    }

    public BigDecimal getLyadjPraWivatAbtSbaP592795() {
        return lyadjPraWivatAbtSbaP592795;
    }

    public void setLyadjPraWivatAbtSbaP592795(BigDecimal lyadjPraWivatAbtSbaP592795) {
        this.lyadjPraWivatAbtSbaP592795 = lyadjPraWivatAbtSbaP592795;
    }

    public BigDecimal getTotalWivatAbSbaP592795() {
        return totalWivatAbSbaP592795;
    }

    public void setTotalWivatAbSbaP592795(BigDecimal totalWivatAbSbaP592795) {
        this.totalWivatAbSbaP592795 = totalWivatAbSbaP592795;
    }

    public BigDecimal getTyWivatAbtOpsP592874() {
        return tyWivatAbtOpsP592874;
    }

    public void setTyWivatAbtOpsP592874(BigDecimal tyWivatAbtOpsP592874) {
        this.tyWivatAbtOpsP592874 = tyWivatAbtOpsP592874;
    }

    public BigDecimal getTyWivatAbotOpsP592874() {
        return tyWivatAbotOpsP592874;
    }

    public void setTyWivatAbotOpsP592874(BigDecimal tyWivatAbotOpsP592874) {
        this.tyWivatAbotOpsP592874 = tyWivatAbotOpsP592874;
    }

    public BigDecimal getLyadjWivatAbtOpsP592874() {
        return lyadjWivatAbtOpsP592874;
    }

    public void setLyadjWivatAbtOpsP592874(BigDecimal lyadjWivatAbtOpsP592874) {
        this.lyadjWivatAbtOpsP592874 = lyadjWivatAbtOpsP592874;
    }

    public BigDecimal getLyadjWivatAbotOpsP592874() {
        return lyadjWivatAbotOpsP592874;
    }

    public void setLyadjWivatAbotOpsP592874(BigDecimal lyadjWivatAbotOpsP592874) {
        this.lyadjWivatAbotOpsP592874 = lyadjWivatAbotOpsP592874;
    }

    public BigDecimal getTyadjCtaWivatAbtOpsP592874() {
        return tyadjCtaWivatAbtOpsP592874;
    }

    public void setTyadjCtaWivatAbtOpsP592874(BigDecimal tyadjCtaWivatAbtOpsP592874) {
        this.tyadjCtaWivatAbtOpsP592874 = tyadjCtaWivatAbtOpsP592874;
    }

    public BigDecimal getTyadjRcaWivatAbtOpsP592874() {
        return tyadjRcaWivatAbtOpsP592874;
    }

    public void setTyadjRcaWivatAbtOpsP592874(BigDecimal tyadjRcaWivatAbtOpsP592874) {
        this.tyadjRcaWivatAbtOpsP592874 = tyadjRcaWivatAbtOpsP592874;
    }

    public BigDecimal getLyadjCtaWivatAbtOpsP592874() {
        return lyadjCtaWivatAbtOpsP592874;
    }

    public void setLyadjCtaWivatAbtOpsP592874(BigDecimal lyadjCtaWivatAbtOpsP592874) {
        this.lyadjCtaWivatAbtOpsP592874 = lyadjCtaWivatAbtOpsP592874;
    }

    public BigDecimal getLyadjRcaWivatAbtOpsP592874() {
        return lyadjRcaWivatAbtOpsP592874;
    }

    public void setLyadjRcaWivatAbtOpsP592874(BigDecimal lyadjRcaWivatAbtOpsP592874) {
        this.lyadjRcaWivatAbtOpsP592874 = lyadjRcaWivatAbtOpsP592874;
    }

    public BigDecimal getLyadjPraWivatAbtOpsP592874() {
        return lyadjPraWivatAbtOpsP592874;
    }

    public void setLyadjPraWivatAbtOpsP592874(BigDecimal lyadjPraWivatAbtOpsP592874) {
        this.lyadjPraWivatAbtOpsP592874 = lyadjPraWivatAbtOpsP592874;
    }

    public BigDecimal getTotalWivatAbOpsP592874() {
        return totalWivatAbOpsP592874;
    }

    public void setTotalWivatAbOpsP592874(BigDecimal totalWivatAbOpsP592874) {
        this.totalWivatAbOpsP592874 = totalWivatAbOpsP592874;
    }

    public BigDecimal getTyWivatSrtNssP592802() {
        return tyWivatSrtNssP592802;
    }

    public void setTyWivatSrtNssP592802(BigDecimal tyWivatSrtNssP592802) {
        this.tyWivatSrtNssP592802 = tyWivatSrtNssP592802;
    }

    public BigDecimal getTyadjCtaWivatSrtNssP592802() {
        return tyadjCtaWivatSrtNssP592802;
    }

    public void setTyadjCtaWivatSrtNssP592802(BigDecimal tyadjCtaWivatSrtNssP592802) {
        this.tyadjCtaWivatSrtNssP592802 = tyadjCtaWivatSrtNssP592802;
    }

    public BigDecimal getTyadjRcaWivatSrtNssP592802() {
        return tyadjRcaWivatSrtNssP592802;
    }

    public void setTyadjRcaWivatSrtNssP592802(BigDecimal tyadjRcaWivatSrtNssP592802) {
        this.tyadjRcaWivatSrtNssP592802 = tyadjRcaWivatSrtNssP592802;
    }

    public BigDecimal getLyadjCtaWivatSrtNssP592802() {
        return lyadjCtaWivatSrtNssP592802;
    }

    public void setLyadjCtaWivatSrtNssP592802(BigDecimal lyadjCtaWivatSrtNssP592802) {
        this.lyadjCtaWivatSrtNssP592802 = lyadjCtaWivatSrtNssP592802;
    }

    public BigDecimal getLyadjRcaWivatSrtNssP592802() {
        return lyadjRcaWivatSrtNssP592802;
    }

    public void setLyadjRcaWivatSrtNssP592802(BigDecimal lyadjRcaWivatSrtNssP592802) {
        this.lyadjRcaWivatSrtNssP592802 = lyadjRcaWivatSrtNssP592802;
    }

    public BigDecimal getLyadjPraWivatSrtNssP592802() {
        return lyadjPraWivatSrtNssP592802;
    }

    public void setLyadjPraWivatSrtNssP592802(BigDecimal lyadjPraWivatSrtNssP592802) {
        this.lyadjPraWivatSrtNssP592802 = lyadjPraWivatSrtNssP592802;
    }

    public BigDecimal getTotalWivatSrtNssP592802() {
        return totalWivatSrtNssP592802;
    }

    public void setTotalWivatSrtNssP592802(BigDecimal totalWivatSrtNssP592802) {
        this.totalWivatSrtNssP592802 = totalWivatSrtNssP592802;
    }

    public BigDecimal getTyWivatSrtRssP592756() {
        return tyWivatSrtRssP592756;
    }

    public void setTyWivatSrtRssP592756(BigDecimal tyWivatSrtRssP592756) {
        this.tyWivatSrtRssP592756 = tyWivatSrtRssP592756;
    }

    public BigDecimal getTyadjCtaWivatSrtRssP592756() {
        return tyadjCtaWivatSrtRssP592756;
    }

    public void setTyadjCtaWivatSrtRssP592756(BigDecimal tyadjCtaWivatSrtRssP592756) {
        this.tyadjCtaWivatSrtRssP592756 = tyadjCtaWivatSrtRssP592756;
    }

    public BigDecimal getTyadjRcaWivatSrtRssP592756() {
        return tyadjRcaWivatSrtRssP592756;
    }

    public void setTyadjRcaWivatSrtRssP592756(BigDecimal tyadjRcaWivatSrtRssP592756) {
        this.tyadjRcaWivatSrtRssP592756 = tyadjRcaWivatSrtRssP592756;
    }

    public BigDecimal getLyadjCtaWivatSrtRssP592756() {
        return lyadjCtaWivatSrtRssP592756;
    }

    public void setLyadjCtaWivatSrtRssP592756(BigDecimal lyadjCtaWivatSrtRssP592756) {
        this.lyadjCtaWivatSrtRssP592756 = lyadjCtaWivatSrtRssP592756;
    }

    public BigDecimal getLyadjRcaWivatSrtRssP592756() {
        return lyadjRcaWivatSrtRssP592756;
    }

    public void setLyadjRcaWivatSrtRssP592756(BigDecimal lyadjRcaWivatSrtRssP592756) {
        this.lyadjRcaWivatSrtRssP592756 = lyadjRcaWivatSrtRssP592756;
    }

    public BigDecimal getLyadjPraWivatSrtRssP592756() {
        return lyadjPraWivatSrtRssP592756;
    }

    public void setLyadjPraWivatSrtRssP592756(BigDecimal lyadjPraWivatSrtRssP592756) {
        this.lyadjPraWivatSrtRssP592756 = lyadjPraWivatSrtRssP592756;
    }

    public BigDecimal getTotalWivatSrtRssP592756() {
        return totalWivatSrtRssP592756;
    }

    public void setTotalWivatSrtRssP592756(BigDecimal totalWivatSrtRssP592756) {
        this.totalWivatSrtRssP592756 = totalWivatSrtRssP592756;
    }

    public BigDecimal getTyWivatSrtAssP592768() {
        return tyWivatSrtAssP592768;
    }

    public void setTyWivatSrtAssP592768(BigDecimal tyWivatSrtAssP592768) {
        this.tyWivatSrtAssP592768 = tyWivatSrtAssP592768;
    }

    public BigDecimal getTyadjCtaWivatSrtAssP592768() {
        return tyadjCtaWivatSrtAssP592768;
    }

    public void setTyadjCtaWivatSrtAssP592768(BigDecimal tyadjCtaWivatSrtAssP592768) {
        this.tyadjCtaWivatSrtAssP592768 = tyadjCtaWivatSrtAssP592768;
    }

    public BigDecimal getTyadjRcaWivatSrtAssP592768() {
        return tyadjRcaWivatSrtAssP592768;
    }

    public void setTyadjRcaWivatSrtAssP592768(BigDecimal tyadjRcaWivatSrtAssP592768) {
        this.tyadjRcaWivatSrtAssP592768 = tyadjRcaWivatSrtAssP592768;
    }

    public BigDecimal getLyadjCtaWivatSrtAssP592768() {
        return lyadjCtaWivatSrtAssP592768;
    }

    public void setLyadjCtaWivatSrtAssP592768(BigDecimal lyadjCtaWivatSrtAssP592768) {
        this.lyadjCtaWivatSrtAssP592768 = lyadjCtaWivatSrtAssP592768;
    }

    public BigDecimal getLyadjRcaWivatSrtAssP592768() {
        return lyadjRcaWivatSrtAssP592768;
    }

    public void setLyadjRcaWivatSrtAssP592768(BigDecimal lyadjRcaWivatSrtAssP592768) {
        this.lyadjRcaWivatSrtAssP592768 = lyadjRcaWivatSrtAssP592768;
    }

    public BigDecimal getLyadjPraWivatSrtAssP592768() {
        return lyadjPraWivatSrtAssP592768;
    }

    public void setLyadjPraWivatSrtAssP592768(BigDecimal lyadjPraWivatSrtAssP592768) {
        this.lyadjPraWivatSrtAssP592768 = lyadjPraWivatSrtAssP592768;
    }

    public BigDecimal getTotalWivatSrtAssP592768() {
        return totalWivatSrtAssP592768;
    }

    public void setTotalWivatSrtAssP592768(BigDecimal totalWivatSrtAssP592768) {
        this.totalWivatSrtAssP592768 = totalWivatSrtAssP592768;
    }

    public BigDecimal getTyWivatSrtWdsP592793() {
        return tyWivatSrtWdsP592793;
    }

    public void setTyWivatSrtWdsP592793(BigDecimal tyWivatSrtWdsP592793) {
        this.tyWivatSrtWdsP592793 = tyWivatSrtWdsP592793;
    }

    public BigDecimal getTyadjCtaWivatSrtWdsP592793() {
        return tyadjCtaWivatSrtWdsP592793;
    }

    public void setTyadjCtaWivatSrtWdsP592793(BigDecimal tyadjCtaWivatSrtWdsP592793) {
        this.tyadjCtaWivatSrtWdsP592793 = tyadjCtaWivatSrtWdsP592793;
    }

    public BigDecimal getTyadjRcaWivatSrtWdsP592793() {
        return tyadjRcaWivatSrtWdsP592793;
    }

    public void setTyadjRcaWivatSrtWdsP592793(BigDecimal tyadjRcaWivatSrtWdsP592793) {
        this.tyadjRcaWivatSrtWdsP592793 = tyadjRcaWivatSrtWdsP592793;
    }

    public BigDecimal getLyadjCtaWivatSrtWdsP592793() {
        return lyadjCtaWivatSrtWdsP592793;
    }

    public void setLyadjCtaWivatSrtWdsP592793(BigDecimal lyadjCtaWivatSrtWdsP592793) {
        this.lyadjCtaWivatSrtWdsP592793 = lyadjCtaWivatSrtWdsP592793;
    }

    public BigDecimal getLyadjRcaWivatSrtWdsP592793() {
        return lyadjRcaWivatSrtWdsP592793;
    }

    public void setLyadjRcaWivatSrtWdsP592793(BigDecimal lyadjRcaWivatSrtWdsP592793) {
        this.lyadjRcaWivatSrtWdsP592793 = lyadjRcaWivatSrtWdsP592793;
    }

    public BigDecimal getLyadjPraWivatSrtWdsP592793() {
        return lyadjPraWivatSrtWdsP592793;
    }

    public void setLyadjPraWivatSrtWdsP592793(BigDecimal lyadjPraWivatSrtWdsP592793) {
        this.lyadjPraWivatSrtWdsP592793 = lyadjPraWivatSrtWdsP592793;
    }

    public BigDecimal getTotalWivatSrtWdsP592793() {
        return totalWivatSrtWdsP592793;
    }

    public void setTotalWivatSrtWdsP592793(BigDecimal totalWivatSrtWdsP592793) {
        this.totalWivatSrtWdsP592793 = totalWivatSrtWdsP592793;
    }

    public BigDecimal getTyWivatSrtNcsP592813() {
        return tyWivatSrtNcsP592813;
    }

    public void setTyWivatSrtNcsP592813(BigDecimal tyWivatSrtNcsP592813) {
        this.tyWivatSrtNcsP592813 = tyWivatSrtNcsP592813;
    }

    public BigDecimal getTyadjCtaWivatSrtNcsP592813() {
        return tyadjCtaWivatSrtNcsP592813;
    }

    public void setTyadjCtaWivatSrtNcsP592813(BigDecimal tyadjCtaWivatSrtNcsP592813) {
        this.tyadjCtaWivatSrtNcsP592813 = tyadjCtaWivatSrtNcsP592813;
    }

    public BigDecimal getTyadjRcaWivatSrtNcsP592813() {
        return tyadjRcaWivatSrtNcsP592813;
    }

    public void setTyadjRcaWivatSrtNcsP592813(BigDecimal tyadjRcaWivatSrtNcsP592813) {
        this.tyadjRcaWivatSrtNcsP592813 = tyadjRcaWivatSrtNcsP592813;
    }

    public BigDecimal getLyadjCtaWivatSrtNcsP592813() {
        return lyadjCtaWivatSrtNcsP592813;
    }

    public void setLyadjCtaWivatSrtNcsP592813(BigDecimal lyadjCtaWivatSrtNcsP592813) {
        this.lyadjCtaWivatSrtNcsP592813 = lyadjCtaWivatSrtNcsP592813;
    }

    public BigDecimal getLyadjRcaWivatSrtNcsP592813() {
        return lyadjRcaWivatSrtNcsP592813;
    }

    public void setLyadjRcaWivatSrtNcsP592813(BigDecimal lyadjRcaWivatSrtNcsP592813) {
        this.lyadjRcaWivatSrtNcsP592813 = lyadjRcaWivatSrtNcsP592813;
    }

    public BigDecimal getLyadjPraWivatSrtNcsP592813() {
        return lyadjPraWivatSrtNcsP592813;
    }

    public void setLyadjPraWivatSrtNcsP592813(BigDecimal lyadjPraWivatSrtNcsP592813) {
        this.lyadjPraWivatSrtNcsP592813 = lyadjPraWivatSrtNcsP592813;
    }

    public BigDecimal getTotalWivatSrtNcsP592813() {
        return totalWivatSrtNcsP592813;
    }

    public void setTotalWivatSrtNcsP592813(BigDecimal totalWivatSrtNcsP592813) {
        this.totalWivatSrtNcsP592813 = totalWivatSrtNcsP592813;
    }

    public BigDecimal getTyWivatBeTtaRgsP592872() {
        return tyWivatBeTtaRgsP592872;
    }

    public void setTyWivatBeTtaRgsP592872(BigDecimal tyWivatBeTtaRgsP592872) {
        this.tyWivatBeTtaRgsP592872 = tyWivatBeTtaRgsP592872;
    }

    public BigDecimal getTyadjCtaWivatBetRgsP592872() {
        return tyadjCtaWivatBetRgsP592872;
    }

    public void setTyadjCtaWivatBetRgsP592872(BigDecimal tyadjCtaWivatBetRgsP592872) {
        this.tyadjCtaWivatBetRgsP592872 = tyadjCtaWivatBetRgsP592872;
    }

    public BigDecimal getTyadjRcaWivatBetRgsP592872() {
        return tyadjRcaWivatBetRgsP592872;
    }

    public void setTyadjRcaWivatBetRgsP592872(BigDecimal tyadjRcaWivatBetRgsP592872) {
        this.tyadjRcaWivatBetRgsP592872 = tyadjRcaWivatBetRgsP592872;
    }

    public BigDecimal getLyadjCtaWivatBetRgsP592872() {
        return lyadjCtaWivatBetRgsP592872;
    }

    public void setLyadjCtaWivatBetRgsP592872(BigDecimal lyadjCtaWivatBetRgsP592872) {
        this.lyadjCtaWivatBetRgsP592872 = lyadjCtaWivatBetRgsP592872;
    }

    public BigDecimal getLyadjRcaWivatBetRgsP592872() {
        return lyadjRcaWivatBetRgsP592872;
    }

    public void setLyadjRcaWivatBetRgsP592872(BigDecimal lyadjRcaWivatBetRgsP592872) {
        this.lyadjRcaWivatBetRgsP592872 = lyadjRcaWivatBetRgsP592872;
    }

    public BigDecimal getLyadjPraWivatBetRgsP592872() {
        return lyadjPraWivatBetRgsP592872;
    }

    public void setLyadjPraWivatBetRgsP592872(BigDecimal lyadjPraWivatBetRgsP592872) {
        this.lyadjPraWivatBetRgsP592872 = lyadjPraWivatBetRgsP592872;
    }

    public BigDecimal getTotalWivatBeTtaRgsP592872() {
        return totalWivatBeTtaRgsP592872;
    }

    public void setTotalWivatBeTtaRgsP592872(BigDecimal totalWivatBeTtaRgsP592872) {
        this.totalWivatBeTtaRgsP592872 = totalWivatBeTtaRgsP592872;
    }

    public BigDecimal getTyWivatOttLpuP592794() {
        return tyWivatOttLpuP592794;
    }

    public void setTyWivatOttLpuP592794(BigDecimal tyWivatOttLpuP592794) {
        this.tyWivatOttLpuP592794 = tyWivatOttLpuP592794;
    }

    public BigDecimal getTyadjCtaWivatOttLpuP592794() {
        return tyadjCtaWivatOttLpuP592794;
    }

    public void setTyadjCtaWivatOttLpuP592794(BigDecimal tyadjCtaWivatOttLpuP592794) {
        this.tyadjCtaWivatOttLpuP592794 = tyadjCtaWivatOttLpuP592794;
    }

    public BigDecimal getTyadjRcaWivatOttLpuP592794() {
        return tyadjRcaWivatOttLpuP592794;
    }

    public void setTyadjRcaWivatOttLpuP592794(BigDecimal tyadjRcaWivatOttLpuP592794) {
        this.tyadjRcaWivatOttLpuP592794 = tyadjRcaWivatOttLpuP592794;
    }

    public BigDecimal getLyadjCtaWivatOttLpuP592794() {
        return lyadjCtaWivatOttLpuP592794;
    }

    public void setLyadjCtaWivatOttLpuP592794(BigDecimal lyadjCtaWivatOttLpuP592794) {
        this.lyadjCtaWivatOttLpuP592794 = lyadjCtaWivatOttLpuP592794;
    }

    public BigDecimal getLyadjRcaWivatOttLpuP592794() {
        return lyadjRcaWivatOttLpuP592794;
    }

    public void setLyadjRcaWivatOttLpuP592794(BigDecimal lyadjRcaWivatOttLpuP592794) {
        this.lyadjRcaWivatOttLpuP592794 = lyadjRcaWivatOttLpuP592794;
    }

    public BigDecimal getLyadjPraWivatOttLpuP592794() {
        return lyadjPraWivatOttLpuP592794;
    }

    public void setLyadjPraWivatOttLpuP592794(BigDecimal lyadjPraWivatOttLpuP592794) {
        this.lyadjPraWivatOttLpuP592794 = lyadjPraWivatOttLpuP592794;
    }

    public BigDecimal getTotalWivatOttLpuP592794() {
        return totalWivatOttLpuP592794;
    }

    public void setTotalWivatOttLpuP592794(BigDecimal totalWivatOttLpuP592794) {
        this.totalWivatOttLpuP592794 = totalWivatOttLpuP592794;
    }

    public BigDecimal getTyWivatOttTfP592842() {
        return tyWivatOttTfP592842;
    }

    public void setTyWivatOttTfP592842(BigDecimal tyWivatOttTfP592842) {
        this.tyWivatOttTfP592842 = tyWivatOttTfP592842;
    }

    public BigDecimal getTyadjCtaWivatOttTfP592842() {
        return tyadjCtaWivatOttTfP592842;
    }

    public void setTyadjCtaWivatOttTfP592842(BigDecimal tyadjCtaWivatOttTfP592842) {
        this.tyadjCtaWivatOttTfP592842 = tyadjCtaWivatOttTfP592842;
    }

    public BigDecimal getTyadjRcaWivatOttTfP592842() {
        return tyadjRcaWivatOttTfP592842;
    }

    public void setTyadjRcaWivatOttTfP592842(BigDecimal tyadjRcaWivatOttTfP592842) {
        this.tyadjRcaWivatOttTfP592842 = tyadjRcaWivatOttTfP592842;
    }

    public BigDecimal getLyadjCtaWivatOttTfP592842() {
        return lyadjCtaWivatOttTfP592842;
    }

    public void setLyadjCtaWivatOttTfP592842(BigDecimal lyadjCtaWivatOttTfP592842) {
        this.lyadjCtaWivatOttTfP592842 = lyadjCtaWivatOttTfP592842;
    }

    public BigDecimal getLyadjRcaWivatOttTfP592842() {
        return lyadjRcaWivatOttTfP592842;
    }

    public void setLyadjRcaWivatOttTfP592842(BigDecimal lyadjRcaWivatOttTfP592842) {
        this.lyadjRcaWivatOttTfP592842 = lyadjRcaWivatOttTfP592842;
    }

    public BigDecimal getLyadjPraWivatOttTfP592842() {
        return lyadjPraWivatOttTfP592842;
    }

    public void setLyadjPraWivatOttTfP592842(BigDecimal lyadjPraWivatOttTfP592842) {
        this.lyadjPraWivatOttTfP592842 = lyadjPraWivatOttTfP592842;
    }

    public BigDecimal getTotalWivatOttTfP592842() {
        return totalWivatOttTfP592842;
    }

    public void setTotalWivatOttTfP592842(BigDecimal totalWivatOttTfP592842) {
        this.totalWivatOttTfP592842 = totalWivatOttTfP592842;
    }

    public BigDecimal getTyWivatOttCiP592836() {
        return tyWivatOttCiP592836;
    }

    public void setTyWivatOttCiP592836(BigDecimal tyWivatOttCiP592836) {
        this.tyWivatOttCiP592836 = tyWivatOttCiP592836;
    }

    public BigDecimal getTyWivatOthSmCiP592836() {
        return tyWivatOthSmCiP592836;
    }

    public void setTyWivatOthSmCiP592836(BigDecimal tyWivatOthSmCiP592836) {
        this.tyWivatOthSmCiP592836 = tyWivatOthSmCiP592836;
    }

    public BigDecimal getTyadjCtaWivatOttCiP592836() {
        return tyadjCtaWivatOttCiP592836;
    }

    public void setTyadjCtaWivatOttCiP592836(BigDecimal tyadjCtaWivatOttCiP592836) {
        this.tyadjCtaWivatOttCiP592836 = tyadjCtaWivatOttCiP592836;
    }

    public BigDecimal getTyadjRcaWivatOttCiP592836() {
        return tyadjRcaWivatOttCiP592836;
    }

    public void setTyadjRcaWivatOttCiP592836(BigDecimal tyadjRcaWivatOttCiP592836) {
        this.tyadjRcaWivatOttCiP592836 = tyadjRcaWivatOttCiP592836;
    }

    public BigDecimal getLyadjCtaWivatOttCiP592836() {
        return lyadjCtaWivatOttCiP592836;
    }

    public void setLyadjCtaWivatOttCiP592836(BigDecimal lyadjCtaWivatOttCiP592836) {
        this.lyadjCtaWivatOttCiP592836 = lyadjCtaWivatOttCiP592836;
    }

    public BigDecimal getLyadjRcaWivatOttCiP592836() {
        return lyadjRcaWivatOttCiP592836;
    }

    public void setLyadjRcaWivatOttCiP592836(BigDecimal lyadjRcaWivatOttCiP592836) {
        this.lyadjRcaWivatOttCiP592836 = lyadjRcaWivatOttCiP592836;
    }

    public BigDecimal getLyadjPraWivatOttCiP592836() {
        return lyadjPraWivatOttCiP592836;
    }

    public void setLyadjPraWivatOttCiP592836(BigDecimal lyadjPraWivatOttCiP592836) {
        this.lyadjPraWivatOttCiP592836 = lyadjPraWivatOttCiP592836;
    }

    public BigDecimal getTotalWivatOttCiP592836() {
        return totalWivatOttCiP592836;
    }

    public void setTotalWivatOttCiP592836(BigDecimal totalWivatOttCiP592836) {
        this.totalWivatOttCiP592836 = totalWivatOttCiP592836;
    }

    public BigDecimal getTyWivatOttBiP592868() {
        return tyWivatOttBiP592868;
    }

    public void setTyWivatOttBiP592868(BigDecimal tyWivatOttBiP592868) {
        this.tyWivatOttBiP592868 = tyWivatOttBiP592868;
    }

    public BigDecimal getTyadjCtaWivatOttBiP592868() {
        return tyadjCtaWivatOttBiP592868;
    }

    public void setTyadjCtaWivatOttBiP592868(BigDecimal tyadjCtaWivatOttBiP592868) {
        this.tyadjCtaWivatOttBiP592868 = tyadjCtaWivatOttBiP592868;
    }

    public BigDecimal getTyadjRcaWivatOttBiP592868() {
        return tyadjRcaWivatOttBiP592868;
    }

    public void setTyadjRcaWivatOttBiP592868(BigDecimal tyadjRcaWivatOttBiP592868) {
        this.tyadjRcaWivatOttBiP592868 = tyadjRcaWivatOttBiP592868;
    }

    public BigDecimal getLyadjCtaWivatOttBiP592868() {
        return lyadjCtaWivatOttBiP592868;
    }

    public void setLyadjCtaWivatOttBiP592868(BigDecimal lyadjCtaWivatOttBiP592868) {
        this.lyadjCtaWivatOttBiP592868 = lyadjCtaWivatOttBiP592868;
    }

    public BigDecimal getLyadjRcaWivatOttBiP592868() {
        return lyadjRcaWivatOttBiP592868;
    }

    public void setLyadjRcaWivatOttBiP592868(BigDecimal lyadjRcaWivatOttBiP592868) {
        this.lyadjRcaWivatOttBiP592868 = lyadjRcaWivatOttBiP592868;
    }

    public BigDecimal getLyadjPraWivatOttBiP592868() {
        return lyadjPraWivatOttBiP592868;
    }

    public void setLyadjPraWivatOttBiP592868(BigDecimal lyadjPraWivatOttBiP592868) {
        this.lyadjPraWivatOttBiP592868 = lyadjPraWivatOttBiP592868;
    }

    public BigDecimal getTotalWivatOttBiP592868() {
        return totalWivatOttBiP592868;
    }

    public void setTotalWivatOttBiP592868(BigDecimal totalWivatOttBiP592868) {
        this.totalWivatOttBiP592868 = totalWivatOttBiP592868;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public BigDecimal getTyWivatBtIrP592951() {
        return tyWivatBtIrP592951;
    }

    public void setTyWivatBtIrP592951(BigDecimal tyWivatBtIrP592951) {
        this.tyWivatBtIrP592951 = tyWivatBtIrP592951;
    }
}
