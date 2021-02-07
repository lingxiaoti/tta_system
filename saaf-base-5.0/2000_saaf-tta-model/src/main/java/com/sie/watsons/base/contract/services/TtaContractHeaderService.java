package com.sie.watsons.base.contract.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.watsons.base.contract.model.entities.TtaContractHeaderEntity_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractHeaderEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineEntity_HI_RO;
import com.sie.watsons.base.contract.model.inter.ITtaContractHeader;
import com.sie.watsons.base.contract.model.inter.ITtaContractLine;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.sie.watsons.base.item.model.inter.ITtaItem;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalTermsHeader;
import com.sie.watsons.base.questionnaire.model.inter.server.TtaQuestionChoiceLineServer;
import com.sie.watsons.base.report.model.entities.TtaAnalysisEditDataEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaAnalysisEditData;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/ttaContractHeaderService")
public class TtaContractHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractHeaderService.class);

	@Autowired
	private ITtaContractHeader ttaContractHeaderServer;

    @Autowired
    private TtaQuestionChoiceLineServer ttaQuestionChoiceLineServer;

    @Autowired
    private ITtaAnalysisEditData ttaAnalysisEditDataServer;

    @Autowired
    private ITtaItem ttaItemServer;

    @Autowired
    private ITtaProposalHeader ttaProposalHeaderServer;

    @Autowired
    private ITtaProposalTermsHeader ttaProposalTermsHeaderServer;

    @Autowired
    private ITtaContractLine ttaContractLineServer;
    @Autowired
    private ViewObject<BaseUsersEntity_HI> baseUsersDAO_HI;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaContractHeaderServer;
	}

    @Autowired
    private GenerateCodeService codeService;


    /**
     * 拉取analysis数据
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "callAnalysis")
    public String callAnalysis(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = this.parseObject(params);
            UserSessionBean userSessionBean=getUserSessionBean();
            JSONObject object = ttaContractHeaderServer.callAnalysis(jsonObject, userId,userSessionBean);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, object).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询term
     *
     * @param params
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findTerm")
    public String findTerm(String params) {
        try{
            JSONObject jsonObject = JSON.parseObject(params);
            UserSessionBean userSessionBean=getUserSessionBean();
            String userName = userSessionBean.getUserFullName();
            Boolean isnull2018 = false;

            String buser = "";
            String versionCode = "";

            String poprosalId = jsonObject.getInteger("proposalId").toString();

            TtaProposalHeaderEntity_HI_RO ph = ttaProposalHeaderServer.findByRoId(jsonObject);

            if (null == ph) {
                throw new IllegalArgumentException("当前Proposal信息不存在,请刷新重试,如多次重试失败,请联系管理员");
            }

            Calendar calendar = Calendar.getInstance();//日历对象
            int yearStr = calendar.get(Calendar.YEAR);//获取年份
            int monthStr = calendar.get(Calendar.MONTH);//获取月份
            int dateStr = calendar.get(Calendar.DATE);//获取日期

            yearStr = Integer.parseInt(ph.getProposalYear());

            //获取当前analysis数据
            List<TtaAnalysisEditDataEntity_HI> analysisList = ttaAnalysisEditDataServer.findByProposalID(poprosalId);

            List<TtaOiBillLineEntity_HI_RO> billList = null;
            //获取数据字典，合同行code，和TermCn，暂时没用
            List<BaseLookupValuesEntity_HI_RO> codeList = ttaContractLineServer.findLookUpCode().getData();

            //根据条件:proposalId,年度,TTA TERMS模块,品牌计划模块,部门协定标准模块,NPP服务费模块 为确认状态下查找
            List<TtaProposalHeaderEntity_HI> headerList2019 = ttaProposalHeaderServer.findByTerm(poprosalId,yearStr+"");

            if(null == headerList2019 || headerList2019.size()==0 ){

                String isTermsConfirm = ph.getIsTermsConfirm();
                String isPlnConfirm = ph.getIsPlnConfirm();
                String isDepartConfirm = ph.getIsDepartConfirm();
                String isNewConfirm = ph.getIsNewConfirm();

                List<String> unConfimStr = new ArrayList<>();
                String tipMsg = "";
                if ("N".equals(isTermsConfirm)){
                    tipMsg = "TTA TERM";
                    unConfimStr.add(tipMsg);
                }

                if ("N".equals(isPlnConfirm)){
                    tipMsg = "品牌计划";
                    unConfimStr.add(tipMsg);
                }

                if ("N".equals(isDepartConfirm)){
                    tipMsg = "部门协定标准";
                    unConfimStr.add(tipMsg);
                }

                if ("N".equals(isNewConfirm)){
                    tipMsg = "NPP服务费";
                    unConfimStr.add(tipMsg);
                }

                if(!unConfimStr.isEmpty()) {
                    tipMsg = StringUtils.join(unConfimStr,",");
                }

                //js端会跑数据为空提示
                JSONObject obj1 = new JSONObject();
                obj1.put(SToolUtils.STATUS, "E");
                obj1.put(SToolUtils.MSG, "【" + tipMsg + "】模块为未确认状态,加载数据失败!");
                return obj1.toString();
            } else {
                if(null!=headerList2019.get(0).getCreatedBy()){
                    //获取proposal单据的创建用户信息
                    BaseUsersEntity_HI baseUsersEntity_hi = baseUsersDAO_HI.getById(headerList2019.get(0).getCreatedBy());
                    buser = baseUsersEntity_hi.getUserFullName();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH.mm.ss");
                    String dateNowStr = sdf.format(baseUsersEntity_hi.getCreationDate());
                    versionCode =buser +"_"+dateNowStr;
                }
            }
            //获取TTA Terms头信息
            List<TtaProposalTermsHeaderEntity_HI> termList2019 = ttaProposalTermsHeaderServer.findByTerm(headerList2019.get(0).getProposalId()+"");

            //根据proposalId 和 proposal制作年度查询条款信息
            List<TtaContractLineEntity_HI_RO> contractLineList2019 = ttaContractLineServer.findAnalysisLine(termList2019.get(0).getProposalId()+"",yearStr+"");
            //报表头需要单独展示免费货
            List<TtaContractLineEntity_HI_RO> freegoodsList2019 = ttaContractLineServer.findAnalysisFreeGood(termList2019.get(0).getProposalId()+"");

            TtaContractLineEntity_HI_RO freeGood2019 = null;
            //如果免费货有值,那么计算如下Sales等值
            if(freegoodsList2019.size()>0){
                freeGood2019 = freegoodsList2019.get(0);
                freeGood2019.setSales(freeGood2019.getSales().divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
                freeGood2019.setPurchase(freeGood2019.getPurchase().divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
                freeGood2019.setFeeNotax((freeGood2019.getFeeNotax()==null?new BigDecimal(0):freeGood2019.getFeeNotax()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
                freeGood2019.setFeeIntas((freeGood2019.getFeeIntas()==null?new BigDecimal(0):freeGood2019.getFeeIntas()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
                freeGood2019.setTtaValue(new BigDecimal(freeGood2019.getTtaValue()==null?"0":freeGood2019.getTtaValue()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP)+"");
            }else{
                freeGood2019 = initContractLine();
            }
            List<TtaProposalHeaderEntity_HI> headerList2018 = null;
            //统计合算sumMoney
            //sumMoney(contractLineList2019);
            if(headerList2019!=null&&headerList2019.size()!=0){
                //2019.12.10号,hmb注释
                //headerList2018 = ttaProposalHeaderServer.findByTermOld((yearStr-1)+"",headerList2019.get(0).getVendorNbr());
                //hmb 添加 ,改动原因:改变取往年的proposal单据方案,改为通过lastYearProposalId字段去找往年的proposal单据
                headerList2018 = ttaProposalHeaderServer.findLastProposal(headerList2019.get(0).getLastYearProposalId());

            }

            TtaItemEntity_HI_RO item = null;
            List<TtaProposalTermsHeaderEntity_HI> termList2018 = null;
            if(headerList2019.size()!=0){
                jsonObject.put("vendorNbr",headerList2019.get(0).getVendorNbr());
                item = ttaItemServer.findByVendor(jsonObject);
            }else{
                item = new TtaItemEntity_HI_RO();
                headerList2019.add(initProposalHeader());
            }
            if(item ==null){ //如果item为null ,初始化 item对象
                item = new TtaItemEntity_HI_RO();
                item.setVendorName("");
                item.setDeptDesc("");
                item.setVendorNbr(0);
                item.setBrandEn("");
            }

            //往年有Proposal,查找往年的TTA_TERMS
            if(headerList2018 !=null && headerList2018.size()!=0){
                //billList = ttaContractLineServer.findBillOi(headerList2018.get(0).getVendorNbr(),"2019").getData();
                termList2018  = ttaProposalTermsHeaderServer.findByTerm(headerList2018.get(0).getProposalId()+"");
                if(null == termList2018 || termList2018.size()==0){
                    termList2018.add(initProposalTermsHeader());
                }
            }else{
                //往年没有数据
                isnull2018 = true;//设置往年的为空的标识
                //没有往年的proposal单据,就初始化往年的数据
                termList2018 = new ArrayList<TtaProposalTermsHeaderEntity_HI>();
                termList2018.add(initProposalTermsHeader());
                headerList2018.add(initProposalHeader());
            }

            List<TtaContractLineEntity_HI_RO> contractLineList2018 = new ArrayList<TtaContractLineEntity_HI_RO>();
            TtaContractLineEntity_HI_RO freeGood2018 = null;

            //2020.1.10 hmb修改
            //判断为空值则初始化一个值为0的实体
            //if(null == termList2018 || termList2018.size()==0){
            //    termList2018.add(initProposalTermsHeader());
            //}else {
                //如果也找不出来也要初始化空对象
                if(termList2018.get(0).getProposalId() !=0){
                    contractLineList2018 = ttaContractLineServer.findAnalysisLine(headerList2018.get(0).getProposalId()+"",headerList2018.get(0).getProposalYear());
                    //前端需要获取单条免费货数据，先除以一千
                    List<TtaContractLineEntity_HI_RO> freegoodsList2018 = ttaContractLineServer.findAnalysisFreeGood(termList2018.get(0).getProposalId()+"");
                    if(freegoodsList2018.size()>0){
                        freeGood2018 = freegoodsList2018.get(0);
                        freeGood2018.setSales(freeGood2018.getSales().divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
                        freeGood2018.setPurchase(freeGood2018.getPurchase().divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));

                        freeGood2018.setFeeNotax((freeGood2018.getFeeNotax()==null?new BigDecimal(0):freeGood2018.getFeeNotax()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
                        freeGood2018.setFeeIntas((freeGood2018.getFeeIntas()==null?new BigDecimal(0):freeGood2018.getFeeIntas()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP));
                        freeGood2018.setTtaValue(new BigDecimal(freeGood2018.getTtaValue()==null?"0":freeGood2018.getTtaValue()).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP)+"");
                    }else{
                        freeGood2018 = initContractLine();
                    }
                    if(contractLineList2018.size()==0){
                        //如果往年没有条款,就初始化和当年条款数目一样的空对象
                        for(int i=0;i<contractLineList2019.size();i++){
                            contractLineList2018.add(initContractLine());
                        }
                    }

                    //hmb查   如果当年和往年的条款集合都不为空,那么有可能两年的条款名目对不上
                    contractLineList2018 = contractLineNull(contractLineList2018,contractLineList2019);
                    contractLineList2019 = contractLineNull(contractLineList2019,contractLineList2018);
                    if(contractLineList2018.size()>0){
                        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String ad= "";
                        if(termList2019.get(0).getActuallyCountDate() == null){
                            ad=contractLineList2019.get(0).getProposalYear()+"-12-31 23:59:59";
                        }else{
                            ad=f.format(termList2019.get(0).getActuallyCountDate());
                        }

                        //统计合算sumMoney
                        //2020.1.3号注释,放到正式环境和测试环境需要打开
                        sumMoney(contractLineList2018,ad,headerList2019);
                        //countSumMoney(contractLineList2018,ad,headerList2018,headerList2019);
                    }

                }
            //}

            //有可能19年的数据会多出几条，List全部为空时可以所有行都为零，部分条款缺失时则分情况处理，有排序问题
            int num = contractLineList2019.size()-contractLineList2018.size();
            num = 0;
            //大于零则数据不对称，则以最新一年为模板，置零，旧一年填充进去
            if(num>0){
                List<TtaContractLineEntity_HI_RO>  contractLineListNew = new ArrayList<TtaContractLineEntity_HI_RO>();
                for(int i=0;i<contractLineList2019.size();i++){
                    TtaContractLineEntity_HI_RO cl = new TtaContractLineEntity_HI_RO();
                    cl.setTtaValue("0");
                    cl.setSumMoney("0");
                    cl.setFeeIntas(new BigDecimal(0));
                    cl.setSales(contractLineList2019.get(0).getSales());
                    cl.setPurchase(contractLineList2019.get(0).getPurchase());
                    cl.setFeeNotax(new BigDecimal(0));
                    cl.setOiType(contractLineList2019.get(i).getOiType());
                    cl.setVendorCode(contractLineList2019.get(i).getVendorCode());
                    cl.setTermsCn(contractLineList2019.get(i).getTermsCn());

                    //判断条款相同则代入
                    for(int k=0;k<contractLineList2018.size();k++){
                        if(contractLineList2019.get(i).getTermsCn().equals(contractLineList2018.get(k).getTermsCn())){
                            cl.setTtaValue(contractLineList2018.get(k).getTtaValue()==null?"0":contractLineList2018.get(k).getTtaValue());
                            cl.setSumMoney(contractLineList2018.get(k).getSumMoney()==null?"0":contractLineList2018.get(k).getSumMoney());
                            cl.setFeeIntas(contractLineList2018.get(k).getFeeIntas()==null?new BigDecimal(0):contractLineList2018.get(k).getFeeIntas());
                            cl.setPurchase(contractLineList2018.get(k).getPurchase()==null?new BigDecimal(0):contractLineList2018.get(k).getPurchase());
                            cl.setFeeNotax(contractLineList2018.get(k).getFeeNotax()==null?new BigDecimal(0):contractLineList2018.get(k).getFeeNotax());
                            cl.setSales(contractLineList2018.get(k).getSales()==null?new BigDecimal(0):contractLineList2018.get(k).getSales());
                        }
                    }
                    contractLineListNew.add(cl);
                }
                contractLineList2018 = contractLineListNew;
            }

//            //实际费用字段数据处理
//            if(null!=billList&&billList.size()!=0){
////                Field[] fields = billList.get(0).getClass().getDeclaredFields();
////                Field field = billList.get(0).getClass().getField("accountMonth");
////                String val = (String)field.get(billList.get(0));
//                TtaOiBillLineEntity_HI_RO bill = sumData(billList);
//                insertSunMoney(bill,contractLineList2018);
//            }


                TtaAnalysisEditDataEntity_HI analysis =null;
            //如果analysis不存在数据则新建，并插入初始化
            if(analysisList == null || analysisList.size()==0){
                //BIC备注
                String remark = ttaQuestionChoiceLineServer.findQuestionEnglishRemark(Integer.parseInt(poprosalId));

                analysis = new TtaAnalysisEditDataEntity_HI();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String format1 = sdf.format(new Date());

                sdf = new SimpleDateFormat("HH:mm:ss");
                String format2 = sdf.format(new Date());

                //analysis.setVersionCode(buser+"_"+yearStr+monthStr+dateStr);
                analysis.setVersionCode(buser+"_"+format1 + "_" + format2);

                if(headerList2019.size()>0){
                    analysis.setBatch(headerList2019.get(0).getOrderNbr()+"_"+headerList2019.get(0).getVersionCode());
                }else{
                    analysis.setBatch("");
                }
                analysis.setProposalid(poprosalId);
                analysis.setBicRemark(remark);
                analysis.setPurchaseRemark("");
                analysis.setMarkup(0);
                analysis.setFreegoods(0);
                ttaAnalysisEditDataServer.saveOrUpdate(analysis);
            }else{
                if(StringUtils.isNotBlank(analysisList.get(0).getBicRemark())){
                    analysisList.get(0).setBicRemark(analysisList.get(0).getBicRemark().replace("<br>","&#10;"));
                }else{
                    String remark = ttaQuestionChoiceLineServer.findQuestionEnglishRemark(Integer.parseInt(poprosalId));
                    if (StringUtils.isNotBlank(remark)) {
                        analysisList.get(0).setBicRemark(remark);
                    } else {
                        analysisList.get(0).setBicRemark("");
                    }
                }
            }
            //等到测试环境测试通过，再放开正式环境使用

            //获取往年的analysis数据
         /*   TtaAnalysisEditDataEntity_HI oldAnalysis = new TtaAnalysisEditDataEntity_HI();
            if (headerList2018.get(0).getProposalId() != 0) {
                List<TtaAnalysisEditDataEntity_HI> oldAnalysisList = ttaAnalysisEditDataServer.findByProposalID(String.valueOf(headerList2018.get(0).getProposalId()));
                if (CollectionUtils.isNotEmpty(oldAnalysisList)) {
                    oldAnalysis = oldAnalysisList.get(0);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String format1 = sdf.format(new Date());
                    sdf = new SimpleDateFormat("HH:mm:ss");
                    String format2 = sdf.format(new Date());
                    oldAnalysis.setVersionCode(buser+"_"+format1 + "_" + format2);
                    oldAnalysis.setBatch(headerList2018.get(0).getOrderNbr()+"_"+headerList2018.get(0).getVersionCode());
                    oldAnalysis.setProposalid(String.valueOf(headerList2018.get(0).getProposalId()));
                    oldAnalysis.setBicRemark("");
                    oldAnalysis.setPurchaseRemark("");
                    oldAnalysis.setMarkup(0);
                    oldAnalysis.setFreegoods(0);
                    ttaAnalysisEditDataServer.saveOrUpdate(oldAnalysis);
                }
            }*/

            //往年没有数据,初始化往年的条款
            if(isnull2018){
                contractLineList2018 = oldYearNull(contractLineList2018,contractLineList2019);
            }
            //处理中文字符
            contractLineList2018 = changeChar(contractLineList2018);
            contractLineList2019 = changeChar(contractLineList2019);

            //算往年tta值,往年的预估值,例如2019TTA  计算的范围为往年的预估值:(Fcs Purchase,Fcs Sales OI费用(FEE_INTAS 费用预估))
            //ttaContractHeaderServer.countPurchaseSalesAndFeeIntas(headerList2019,headerList2018,contractLineList2018);

            //实际费用
            String purchase = "0";
            String sales = "0";
            String gp = "0";
            //预估费用
            String fpurchase = "0";
            String fsales = "0";
            String fgp = "0";
            JSONObject obj = new JSONObject();
            List<Map<String, Object>> list = ttaContractLineServer.findBrand(headerList2019.get(0).getProposalId()+"",headerList2019.get(0).getVendorNbr());
            if(list.size()>0){
                obj.put("brandVendor", list.size());
                purchase = list.get(0).get("PO_RECORD_SUM").toString();
                sales = list.get(0).get("SALES_SUM").toString();
                gp = list.get(0).get("ACTUAL_GP").toString();

                fpurchase = list.get(0).get("FCS_PURCHASE").toString();
                fsales = list.get(0).get("FCS_SALES").toString();
                fgp = list.get(0).get("GP").toString();
            }else{
                //品牌计划为空则
                obj.put("brandVendor", "null");
            }

            obj.put(SToolUtils.STATUS, "S");
            obj.put(SToolUtils.MSG, SUCCESS_MSG);
            obj.put("analysisData", analysisList.size()==0?analysis:analysisList.get(0));
            //obj.put("oldAnalysisData",oldAnalysis);
            obj.put("versionCode", versionCode);
            obj.put("buser", buser);
            obj.put("purchase", purchase);
            obj.put("sales", sales);
            obj.put("gp", gp);

            obj.put("fpurchase", fpurchase);
            obj.put("fsales", fsales);
            obj.put("fgp", fgp);

            obj.put("datasize", 1);
            if(freeGood2018==null){
                freeGood2018 = new TtaContractLineEntity_HI_RO();
                freeGood2018.setPurchase(new BigDecimal(0));
                freeGood2018.setSales(new BigDecimal(0));
                freeGood2018.setSumMoney("0");
                freeGood2018.setTtaValue("0");
                freeGood2018.setFeeNotax(new BigDecimal(0));
                freeGood2018.setFeeIntas(new BigDecimal(0));
            }
            obj.put("freeGood2018",(JSONObject) JSON.toJSON(freeGood2018));

            obj.put("freeGood2019",(JSONObject) JSON.toJSON(freeGood2019));

            obj.put("item",(JSONObject) JSON.toJSON(item));
            obj.put("header2018",(JSONObject) JSON.toJSON(headerList2018.get(0)));
            obj.put("hterm2018",(JSONObject) JSON.toJSON(termList2018.get(0)));
            JSONArray array= JSONArray.parseArray(JSON.toJSONString(contractLineList2018));
            obj.put("contractLineList2018",array);

            obj.put("header2019",(JSONObject) JSON.toJSON(headerList2019.get(0)));
            obj.put("hterm2019",(JSONObject) JSON.toJSON(termList2019.get(0)));
            JSONArray array2= JSONArray.parseArray(JSON.toJSONString(contractLineList2019));
            obj.put("contractLineList2019",array2);
            return obj.toString();

    }catch (Exception e){
        LOGGER.error(e.getMessage(),e);
        return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
    }
    }

    //两年条款为空时，取有值的那一年，不一样时中文名取最新一年的
    //名字不一样的以当年为准
    /**
     * @param list1  条款集合
     * @param list2  条款集合
     * @return
     */
    public List<TtaContractLineEntity_HI_RO> contractLineNull(List<TtaContractLineEntity_HI_RO> list1,List<TtaContractLineEntity_HI_RO> list2){
        for(int j=0;j<list2.size();j++){
            if(list2.get(j).getContractLId()==null&&list1.get(j).getContractLId()==null){
                list2.remove(j);
                list1.remove(j);
                j--;//重点:要加上,因为删除一个元素,元素向前移动一位,索引继续往后加1
            }
        }
        for(int i=0;i<list2.size();i++){
            //另一年有数据时可以覆盖
            if(list1.get(i).getContractLId()==null){
                list1.get(i).setContractLId(0);
                list1.get(i).setProposalId(0);
            }

            //hmb 重新添加注释 如果当年的条款不为空,往年的条款为空,把当年的条款数据设置到往年的条款数据中
            if(list2.get(i).getTermsCn() !=null&&list1.get(i).getTermsCn() ==null){
                list1.get(i).setTermsCn(list2.get(i).getTermsCn());
                list1.get(i).setContractLId(list2.get(i).getContractLId());
                list1.get(i).setContractHId(list2.get(i).getContractHId());
                list1.get(i).setPurchase(new BigDecimal(0));
                list1.get(i).setFeeIntas(new BigDecimal(0));
                list1.get(i).setPurchase(new BigDecimal(0));
                list1.get(i).setSumMoney("0");
                list1.get(i).setTtaValue("0");
                list1.get(i).setFeeNotax(new BigDecimal(0));
                list1.get(i).setOiType(list2.get(i).getOiType());
                list1.get(i).setVendorCode(list2.get(i).getVendorCode());
                list1.get(i).setProposalYear((Integer.parseInt(list2.get(i).getProposalYear())-1)+"");
                list1.get(i).setUnit(list2.get(i).getUnit());
                list1.get(i).setSales(new BigDecimal(0));
                list1.get(i).setTermsEn(list2.get(i).getTermsEn());
                list1.get(i).setDeliveryGranary("0");
                list1.get(i).setProposalId(0);
            }
            //名字不一样的情况
            //2019.12.30号 hmb注释
     /*       if(list2.get(i).getTermsCn()!=null&&list1.get(i).getTermsCn()!=null&&!list2.get(i).getTermsCn().equals(list1.get(i).getTermsCn())){
                list1.get(i).setTermsCn(list2.get(i).getTermsCn());
                list1.get(i).setTermsEn(list2.get(i).getTermsEn());
            }*/

        }
        return list1;
    }
    //新增供应商时往年数据为空，赋空值
    public List<TtaContractLineEntity_HI_RO> oldYearNull(List<TtaContractLineEntity_HI_RO> list1,List<TtaContractLineEntity_HI_RO> list2){

        for(int j=0;j<list2.size();j++){
            if(list2.get(j).getContractLId()==null){
                list2.remove(j);
                j--;//重点:要加上,因为删除一个元素,元素向前移动一位,索引继续往后加1
            }
        }
        for(int i=0;i<list2.size();i++){
            TtaContractLineEntity_HI_RO ro = new TtaContractLineEntity_HI_RO();
            ro.setTermsCn(list2.get(i).getTermsCn());
            ro.setContractLId(list2.get(i).getContractLId());
            ro.setContractHId(list2.get(i).getContractHId());
            ro.setPurchase(new BigDecimal(0));
            ro.setFeeIntas(new BigDecimal(0));
            ro.setPurchase(new BigDecimal(0));
            ro.setSumMoney("0");
            ro.setTtaValue("0");
            ro.setFeeNotax(new BigDecimal(0));
            ro.setOiType(list2.get(i).getOiType());
            ro.setVendorCode(list2.get(i).getVendorCode());
            ro.setProposalYear((Integer.parseInt(list2.get(i).getProposalYear())-1)+"");
            ro.setUnit(list2.get(i).getUnit());
            ro.setSales(new BigDecimal(0));
            ro.setTermsEn(list2.get(i).getTermsEn());
            ro.setDeliveryGranary("0");
            ro.setProposalId(0);
            list1.add(ro);
        }
        return list1;
    }
    //找出所有条款中的免费货条款
    public TtaContractLineEntity_HI_RO getFreeGood(List<TtaContractLineEntity_HI_RO> contractLineList){
        TtaContractLineEntity_HI_RO freeGood = null;
        for(int i=0;i<contractLineList.size();i++){
            String name = contractLineList.get(i).getTermsCn();
            if(name.indexOf("免费货")!=-1){
                freeGood = contractLineList.get(i);
            }
        }
        return freeGood;
    }

    //计算出每条contractLine的summoney值
    public void sumMoney(List<TtaContractLineEntity_HI_RO> contractLineList,String actuallyCountDate,List<TtaProposalHeaderEntity_HI> headerEntityHis){
        List<Map<String, Object>> aboiList = null;
        List<Map<String, Object>> beoiList = null;
        List<Map<String, Object>> sroiList = null;
        if(contractLineList.size()>0&&contractLineList.get(0).getVendorCode()!=null){
            //2020.2.20 因改为取2020年的上的主供应商编号，不取2019上的供应商编号相关的费用（取当年proposal单据上的供应商，不取往年proposal单据上的供应商）
            String currentYearVendorCode = headerEntityHis.get(0).getVendorNbr();
            String oldYearProposalYear = contractLineList.get(0).getProposalYear();
            //里面关联供应商，会查出多个供应商多条OI数据
            //2020.2.20 注释
            //aboiList = ttaContractLineServer.findOIList("TTA_ABOI_BILL_LINE",actuallyCountDate,contractLineList.get(0).getVendorCode(),contractLineList.get(0).getProposalYear());
            //beoiList = ttaContractLineServer.findOIList("TTA_BEOI_BILL_LINE",actuallyCountDate,contractLineList.get(0).getVendorCode(),contractLineList.get(0).getProposalYear());
            //sroiList = ttaContractLineServer.findOIList("TTA_SROI_BILL_LINE",actuallyCountDate,contractLineList.get(0).getVendorCode(),contractLineList.get(0).getProposalYear());
            aboiList = ttaContractLineServer.findOIList("TTA_ABOI_BILL_LINE",actuallyCountDate,currentYearVendorCode,oldYearProposalYear);
            beoiList = ttaContractLineServer.findOIList("TTA_BEOI_BILL_LINE",actuallyCountDate,currentYearVendorCode,oldYearProposalYear);
            sroiList = ttaContractLineServer.findOIList("TTA_SROI_BILL_LINE",actuallyCountDate,currentYearVendorCode,oldYearProposalYear);
        }
        //OIlist同一供应商存在多行，数字字段统计累加为一行
        Map<String, Object> aboiMap = sumOiDataLine(aboiList);
        Map<String, Object> beoiMap = sumOiDataLine(beoiList);
        Map<String, Object> sroiMap = sumOiDataLine(sroiList);
        System.out.println("aboiMap:" + JSONObject.toJSONString(aboiMap));
        System.out.println("beoiMap:" + JSONObject.toJSONString(beoiMap));
        System.out.println("sroiMap:" + JSONObject.toJSONString(sroiMap));
        List<Map<String, Object>> list = ttaContractLineServer.findContractLine(null);
        for(int i=0;i<contractLineList.size();i++){
           String proposalId =  contractLineList.get(i).getProposalId()+"";
            //如果为0则此条款为空
            if(proposalId.equals("0")||contractLineList.get(i).getProposalId()==null){
               continue;
            }
            Double sumMoney = new Double(0);
            String tableName = null;
            for(int m=0;m<list.size();m++){
                tableName = list.get(m).get("TABLE_NAME").toString();
                Object cell = null;
                     String str =list.get(m).get("Y_TERMS_SOURCE").toString();
                     String str2= contractLineList.get(i).getTermsCn();
                    //判断不同的OI表来源，再获取数据
                    if ("TTA_ABOI_BILL_LINE".equals(tableName)) {
                        //oi映射表与合同行表条款名对应上时，才做相加统计操作
                        //算实际费用的时候,例如查找2019年的条款,对应TTA_TERMS_AC_COUNT(条款对应实际费用表),用得是条款名对应的关系,故因注释 hmb
                        //如下的三个条款判断都改为get(i).getOiTermCn() 之前为get(i).getTermsCn()
                       /* if(contractLineList.get(i).getTermsCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
                            cell = aboiMap.get((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET"));
                        } */

                        if(contractLineList.get(i).getOiTermCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
                         /*   if("商业发展服务费".equals(list.get(m).get("Y_TERMS_SOURCE").toString())){
                                System.out.println("条款名称:" + list.get(m).get("Y_TERMS_SOURCE").toString());
                                System.out.println("字段:" + list.get(m).get("Y_TERMS_TARGET").toString());
                            }*/
                            cell = aboiMap.get((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET"));
                        }
                    }
                    if ("TTA_BEOI_BILL_LINE".equals(tableName)) {

                        /*if (contractLineList.get(i).getOiTermCn().indexOf("集中收货")>0 && list.get(m).get("Y_TERMS_SOURCE").toString().indexOf("集中收货") > 0) {
                            System.out.println("============" + contractLineList.get(i).getOiTermCn() + "========================");
                            System.out.println("TTA_TERMS_AC_COUNT表:" + list.get(m).get("Y_TERMS_SOURCE").toString());
                        }*/
                        //oi映射表与合同行表条款名对应上时，才做相加统计操作
                        if(contractLineList.get(i).getOiTermCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
                            String keystr = ((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET")).toString();
                            cell = beoiMap.get(keystr);
                            /*if (list.get(m).get("Y_TERMS_SOURCE").toString().indexOf("集中收货") > 0) {
                                System.out.println(list.get(m).get("Y_TERMS_TARGET").toString());
                                System.out.println(Double.parseDouble(cell.toString()));
                            }*/
                        }
                    }
                    if ("TTA_SROI_BILL_LINE".equals(tableName)) {
                        //oi映射表与合同行表条款名对应上时，才做相加统计操作
                        //System.out.println("contractLineList条款:" + contractLineList.get(i).getOiTermCn());
                        //System.out.println("TTA_TERMS_AC_COUNT:" + list.get(m).get("Y_TERMS_SOURCE").toString());
                        if(contractLineList.get(i).getOiTermCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
                            //System.out.println("TTA_OI_CONTRACT_MAPPING条款名:" + contractLineList.get(i).getOiTermCn());
                            cell = sroiMap.get((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET"));
                        }
                    }
                    if (null != cell) {
                        sumMoney = sumMoney + Double.parseDouble(cell.toString())/1000;
                    }

            }
            contractLineList.get(i).setSumMoney(sumMoney.toString());
        }
    }

    //计算出单独每个OITYPE的累计值，判断是数字的字段则相加，最终返回一行数据
    public Map<String, Object>  sumOiDataLine(List<Map<String, Object>> list){
        Map<String, Object> sumMap = new HashMap<String, Object>();
        for(int i=0;i<list.size();i++){
            Map<String, Object> map = list.get(i);
            Iterator<Map.Entry<String,Object>> it = map.entrySet().iterator();
            while(it.hasNext()){
              Map.Entry<String,Object> entry = it.next();
              //  System.out.println(entry.getKey()+"+"+entry.getValue());
//                if(entry.getValue()!=null&&entry.getValue().toString().indexOf("-")==0&&isNumber(entry.getValue().toString().replace("-",""))){
//                    System.out.println(entry.getValue().toString()+"++++++++++++++++++++++++++++");
//                }

                if(entry.getValue()!=null&&entry.getValue().toString().indexOf("-")<1&&isNumber(entry.getValue().toString().replace("-",""))){
                    if(entry.getKey().equals("BDF001_MONTH_RECON_ADJ")){
                        //Double va = Double.parseDouble(entry.getValue().toString());
                        System.out.println(entry.getValue().toString()+"++++++++++++++++++++++++++++");
                    }

                    String bb = (sumMap.get(entry.getKey())==null?0:sumMap.get(entry.getKey())).toString();
                    Double val = Double.parseDouble(entry.getValue().toString());
                    Double dd = Double.parseDouble(bb)+ val;
                    sumMap.put(entry.getKey(),dd);
                }else{
                    String bb = (sumMap.get(entry.getKey())==null?0:sumMap.get(entry.getKey())).toString();
                    sumMap.put(entry.getKey(),Double.parseDouble(bb)+0);
                }

            }
        }
        return sumMap;
    }

    //判断是否数字
    public boolean isNumber(Object object){
        String str = object.toString();
               String reg = "\\d+(\\.\\d+)?";
         return str.matches(reg);
    }

    /**
     * 计算每一条条款的实际费用
     * @Author hmb 添加
     * @Date 2020.1.3
     * @param contractLineList 往年的条款
     * @param actuallyCountDate 实际计算时间
     *
     */
    public void countSumMoney(List<TtaContractLineEntity_HI_RO> contractLineList,String actuallyCountDate,List<TtaProposalHeaderEntity_HI> oldProposalList,List<TtaProposalHeaderEntity_HI> headerEntityHis){
        LOGGER.info("############进入计算实际费用操作开始###################");
        if (oldProposalList == null) {
            return;
        }

        //改为取当年Proposal单据上的供应商编号
        //String vendorCode = contractLineList.get(0).getVendorCode();
        String vendorCode = headerEntityHis.get(0).getVendorNbr();
        String oldProposalYear = contractLineList.get(0).getProposalYear();

        //1.查询oi类型的表
        List<Map<String,Object>> oiTableName = ttaContractLineServer.findOiTableName(oldProposalList.get(0).getProposalYear());

        //2.存放各种Oi类型的数据
        Map<String,List<Map<String,Object>>> oiMap = new HashMap<>();
        //List<Map<String, Object>> aboiList = null;
        //List<Map<String, Object>> beoiList = null;
        //List<Map<String, Object>> sroiList = null;

        if(contractLineList.size()>0&&contractLineList.get(0).getVendorCode()!=null){
            //里面关联供应商，会查出多个供应商多条OI数据
            //aboiList = ttaContractLineServer.findOIList("TTA_ABOI_BILL_LINE",actuallyCountDate,contractLineList.get(0).getVendorCode(),contractLineList.get(0).getProposalYear());
            //beoiList = ttaContractLineServer.findOIList("TTA_BEOI_BILL_LINE",actuallyCountDate,contractLineList.get(0).getVendorCode(),contractLineList.get(0).getProposalYear());
            //sroiList = ttaContractLineServer.findOIList("TTA_SROI_BILL_LINE",actuallyCountDate,contractLineList.get(0).getVendorCode(),contractLineList.get(0).getProposalYear());
            for (Map<String, Object> map : oiTableName) {
                String tableName = (String)map.get("TABLE_NAME");
                List<Map<String,Object>> resultList = ttaContractLineServer.findOiResultList(tableName,actuallyCountDate,vendorCode,oldProposalYear);
                oiMap.put(tableName,resultList);
            }
        }

        //3.OIlist同一供应商存在多行，数字字段统计累加为一行
        Map<String,Map<String,Object>> accumuOneLineMap = new HashMap<>();
        //Map<String, Object> aboiMap = sumOiDataLine(aboiList);
        //Map<String, Object> beoiMap = sumOiDataLine(beoiList);
        //Map<String, Object> sroiMap = sumOiDataLine(sroiList);

        Iterator<Map.Entry<String, List<Map<String, Object>>>> entryIterator = oiMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, List<Map<String, Object>>> listEntry = entryIterator.next();
            String key = listEntry.getKey();
            List<Map<String, Object>> value = listEntry.getValue();
            Map<String, Object> objectMap = sumOiDataLine(value);
            accumuOneLineMap.put(key,objectMap);
        }


        //List<Map<String, Object>> list = ttaContractLineServer.findContractLine(null);
        //4.查询OI条款名对应的表和表字段
        List<Map<String, Object>> termsAcList =  ttaContractLineServer.findTermsAcCount(oldProposalYear);

        for(int i=0;i<contractLineList.size();i++){
            String proposalId =  contractLineList.get(i).getProposalId()+"";
            //如果为0则此条款为空
            if(proposalId.equals("0")||contractLineList.get(i).getProposalId()==null){
                continue;
            }
            String oiTermCn = contractLineList.get(i).getOiTermCn();//tta_contract_line 合同表的条款名
            Double sumMoney = new Double(0);

            for(int m=0;m<termsAcList.size();m++){
                String tableName = termsAcList.get(m).get("TABLE_NAME").toString();
                String yTermsSource = termsAcList.get(m).get("Y_TERMS_SOURCE").toString();
                String yTermsTarget = termsAcList.get(m).get("Y_TERMS_TARGET").toString();
                Object cell = null;

                if (accumuOneLineMap.containsKey(tableName) && oiTermCn.equals(yTermsSource)){
                    //if (oiTermCn.equals(yTermsSource)) {
                        Map<String, Object> stringObjectMap = accumuOneLineMap.get(tableName);
                        cell = stringObjectMap.get(yTermsTarget == null ? 0 : yTermsTarget);
                    //}
                }

              /*  //判断不同的OI表来源，再获取数据
                if ("TTA_ABOI_BILL_LINE".equals(tableName)) {
                    //oi映射表与合同行表条款名对应上时，才做相加统计操作
                    //算实际费用的时候,例如查找2019年的条款,对应TTA_TERMS_AC_COUNT(条款对应实际费用表),用得是条款名对应的关系,故因注释 hmb
                    if(contractLineList.get(i).getOiTermCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
                        cell = aboiMap.get((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET"));
                    }
                }
                if ("TTA_BEOI_BILL_LINE".equals(tableName)) {
                    //oi映射表与合同行表条款名对应上时，才做相加统计操作
                    if(contractLineList.get(i).getOiTermCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
                        String keystr = ((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET")).toString();
                        cell = beoiMap.get(keystr);
                    }
                }
                if ("TTA_SROI_BILL_LINE".equals(tableName)) {
                    //oi映射表与合同行表条款名对应上时，才做相加统计操作
                    if(contractLineList.get(i).getOiTermCn().equals(list.get(m).get("Y_TERMS_SOURCE").toString())) {
                        cell = sroiMap.get((list.get(m).get("Y_TERMS_TARGET")) == null ? 0 : list.get(m).get("Y_TERMS_TARGET"));
                    }
                }*/

                if (null != cell) {
                    sumMoney = sumMoney + Double.parseDouble(cell.toString())/1000;
                }

            }
            contractLineList.get(i).setSumMoney(sumMoney.toString());
        }
        LOGGER.info("############进入计算实际费用操作结束###################");
    }




    //空值处理
    public List<TtaOiBillLineEntity_HI_RO> updateNull(List<TtaOiBillLineEntity_HI_RO> billList){
        for(int i=0;i<billList.size();i++){
            billList.get(i).setAdrbSumMoney((billList.get(i).getAdrbSumMoney()==null)?"0":billList.get(0).getAdrbSumMoney());
            billList.get(i).setEpSumMoney((billList.get(i).getEpSumMoney()==null)?"0":billList.get(0).getEpSumMoney());
            billList.get(i).setAddaSumMoney((billList.get(i).getAddaSumMoney()==null)?"0":billList.get(0).getAddaSumMoney());
            billList.get(i).setAddgSumMoney((billList.get(i).getAddgSumMoney()==null)?"0":billList.get(0).getAddgSumMoney());
            billList.get(i).setAdpfSumMoney((billList.get(i).getAdpfSumMoney()==null)?"0":billList.get(0).getAdpfSumMoney());
            billList.get(i).setAdtrSumMoney((billList.get(i).getAdtrSumMoney()==null)?"0":billList.get(0).getAdtrSumMoney());
            billList.get(i).setAss001SumMoney((billList.get(i).getAss001SumMoney()==null)?"0":billList.get(0).getAss001SumMoney());
            billList.get(i).setBac001SumMoney((billList.get(i).getBac001SumMoney()==null)?"0":billList.get(0).getBac001SumMoney());
            billList.get(i).setBdfSumMoney((billList.get(i).getBdfSumMoney()==null)?"0":billList.get(0).getBdfSumMoney());
            billList.get(i).setBdf001SumMoney((billList.get(i).getBdf001SumMoney()==null)?"0":billList.get(0).getBdf001SumMoney());
            billList.get(i).setCos001SumMoney((billList.get(i).getCos001SumMoney()==null)?"0":billList.get(0).getCos001SumMoney());
            billList.get(i).setCou001SumMoney((billList.get(i).getCou001SumMoney()==null)?"0":billList.get(0).getCou001SumMoney());
            billList.get(i).setCps001SumMoney((billList.get(i).getCps001SumMoney()==null)?"0":billList.get(0).getCps001SumMoney());
            billList.get(i).setCri001SumMoney((billList.get(i).getCri001SumMoney()==null)?"0":billList.get(0).getCri001SumMoney());
            billList.get(i).setDmi001SumMoney((billList.get(i).getDmi001SumMoney()==null)?"0":billList.get(0).getDmi001SumMoney());
            billList.get(i).setDrb001SumMoney((billList.get(i).getDrb001SumMoney()==null)?"0":billList.get(0).getDrb001SumMoney());
            billList.get(i).setDrg001SumMoney((billList.get(i).getDrg001SumMoney()==null)?"0":billList.get(0).getDrg001SumMoney());
            billList.get(i).setDrh001summoney((billList.get(i).getDrh001summoney()==null)?"0":billList.get(0).getDrh001summoney());
            billList.get(i).setDro001SumMoney((billList.get(i).getDro001SumMoney()==null)?"0":billList.get(0).getDro001SumMoney());
            billList.get(i).setHbi001SumMoney((billList.get(i).getHbi001SumMoney()==null)?"0":billList.get(0).getHbi001SumMoney());
            billList.get(i).setLdp001SumMoney((billList.get(i).getLdp001SumMoney()==null)?"0":billList.get(0).getLdp001SumMoney());
            billList.get(i).setLpu001SumMoney((billList.get(i).getLpu001SumMoney()==null)?"0":billList.get(0).getLpu001SumMoney());
            billList.get(i).setNfp001SumMoney((billList.get(i).getNfp001SumMoney()==null)?"0":billList.get(0).getNfp001SumMoney());
            billList.get(i).setNpd001SumMoney((billList.get(i).getNpd001SumMoney()==null)?"0":billList.get(0).getNpd001SumMoney());
            billList.get(i).setNpm001SumMoney((billList.get(i).getNpm001SumMoney()==null)?"0":billList.get(0).getNpm001SumMoney());
            billList.get(i).setNss001SumMoney((billList.get(i).getNss001SumMoney()==null)?"0":billList.get(0).getNss001SumMoney());
            billList.get(i).setNti001SumMoney((billList.get(i).getNti001SumMoney()==null)?"0":billList.get(0).getNti001SumMoney());
            billList.get(i).setOtf001SumMoney((billList.get(i).getOtf001SumMoney()==null)?"0":billList.get(0).getOtf001SumMoney());
            billList.get(i).setOth001SumMoney((billList.get(i).getOth001SumMoney()==null)?"0":billList.get(0).getOth001SumMoney());
            billList.get(i).setPsf001SumMoney((billList.get(i).getPsf001SumMoney()==null)?"0":billList.get(0).getPsf001SumMoney());
            billList.get(i).setRss001SumMoney((billList.get(i).getRss001SumMoney()==null)?"0":billList.get(0).getRss001SumMoney());
            billList.get(i).setUep001SumMoney((billList.get(i).getUep001SumMoney()==null)?"0":billList.get(0).getUep001SumMoney());
            billList.get(i).setVip001SumMoney((billList.get(i).getVip001SumMoney()==null)?"0":billList.get(0).getVip001SumMoney());
            billList.get(i).setVir001SumMoney((billList.get(i).getVir001SumMoney()==null)?"0":billList.get(0).getVir001SumMoney());
            billList.get(i).setVir002SumMoney((billList.get(i).getVir002SumMoney()==null)?"0":billList.get(0).getVir002SumMoney());
            billList.get(i).setWdp001SumMoney((billList.get(i).getWdp001SumMoney()==null)?"0":billList.get(0).getWdp001SumMoney());
        }
        return billList;
    }

    //存在相同供应商多条数据，把所有行的金额都相加到第一行
    public TtaOiBillLineEntity_HI_RO sumData(List<TtaOiBillLineEntity_HI_RO> billList){
        billList = updateNull(billList);
        for(int i=1;i<billList.size();i++){
            billList.get(i).setAdrbSumMoney(Math.ceil((Double.parseDouble(billList.get(0).getAdrbSumMoney())+Double.parseDouble(billList.get(i).getAdrbSumMoney()))/1000)+"");
            billList.get(i).setAddgSumMoney(Math.ceil((Double.parseDouble(billList.get(0).getAddgSumMoney())+Double.parseDouble(billList.get(i).getAddgSumMoney()))/1000)+"");
            billList.get(i).setAdpfSumMoney(Math.ceil((Double.parseDouble(billList.get(0).getAdpfSumMoney())+Double.parseDouble(billList.get(i).getAdpfSumMoney()))/1000)+"");
            billList.get(i).setAdtrSumMoney(Math.ceil((Double.parseDouble(billList.get(0).getAdtrSumMoney())+Double.parseDouble(billList.get(i).getAdtrSumMoney()))/1000)+"");
            billList.get(i).setAss001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getAss001SumMoney())+Double.parseDouble(billList.get(i).getAss001SumMoney()))/1000)+"");
            billList.get(i).setBac001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getBac001SumMoney())+Double.parseDouble(billList.get(i).getBac001SumMoney()))/1000)+"");
            billList.get(i).setBdfSumMoney(Math.ceil((Double.parseDouble(billList.get(0).getBdfSumMoney())+Double.parseDouble(billList.get(i).getBdfSumMoney()))/1000)+"");
            billList.get(i).setBdf001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getBdf001SumMoney())+Double.parseDouble(billList.get(i).getBdf001SumMoney()))/1000)+"");
            billList.get(i).setCos001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getCos001SumMoney())+Double.parseDouble(billList.get(i).getCos001SumMoney()))/1000)+"");
            billList.get(i).setCou001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getCou001SumMoney())+Double.parseDouble(billList.get(i).getCou001SumMoney()))/1000)+"");
            billList.get(i).setCps001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getCps001SumMoney())+Double.parseDouble(billList.get(i).getCps001SumMoney()))/1000)+"");
            billList.get(i).setCri001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getCri001SumMoney())+Double.parseDouble(billList.get(i).getCri001SumMoney()))/1000)+"");
            billList.get(i).setDmi001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getDmi001SumMoney())+Double.parseDouble(billList.get(i).getDmi001SumMoney()))/1000)+"");
            billList.get(i).setDrb001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getDrb001SumMoney())+Double.parseDouble(billList.get(i).getDrb001SumMoney()))/1000)+"");
            billList.get(i).setDrg001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getDrg001SumMoney())+Double.parseDouble(billList.get(i).getDrg001SumMoney()))/1000)+"");
            billList.get(i).setDrh001summoney(Math.ceil((Double.parseDouble(billList.get(0).getDrh001summoney())+Double.parseDouble(billList.get(i).getDrh001summoney()))/1000)+"");
            billList.get(i).setDro001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getDro001SumMoney())+Double.parseDouble(billList.get(i).getDro001SumMoney()))/1000)+"");
            billList.get(i).setHbi001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getHbi001SumMoney())+Double.parseDouble(billList.get(i).getHbi001SumMoney()))/1000)+"");
            billList.get(i).setLdp001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getLdp001SumMoney())+Double.parseDouble(billList.get(i).getLdp001SumMoney()))/1000)+"");
            billList.get(i).setLpu001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getLpu001SumMoney())+Double.parseDouble(billList.get(i).getLpu001SumMoney()))/1000)+"");
            billList.get(i).setNfp001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getNfp001SumMoney())+Double.parseDouble(billList.get(i).getNfp001SumMoney()))/1000)+"");
            billList.get(i).setNpd001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getNpd001SumMoney())+Double.parseDouble(billList.get(i).getNpd001SumMoney()))/1000)+"");
            billList.get(i).setNpm001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getNpm001SumMoney())+Double.parseDouble(billList.get(i).getNpm001SumMoney()))/1000)+"");
            billList.get(i).setNss001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getNss001SumMoney())+Double.parseDouble(billList.get(i).getNss001SumMoney()))/1000)+"");
            billList.get(i).setNti001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getNti001SumMoney())+Double.parseDouble(billList.get(i).getNti001SumMoney()))/1000)+"");
            billList.get(i).setOtf001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getOtf001SumMoney())+Double.parseDouble(billList.get(i).getOtf001SumMoney()))/1000)+"");
            billList.get(i).setOth001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getOth001SumMoney())+Double.parseDouble(billList.get(i).getOth001SumMoney()))/1000)+"");
            billList.get(i).setPsf001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getPsf001SumMoney())+Double.parseDouble(billList.get(i).getPsf001SumMoney()))/1000)+"");
            billList.get(i).setRss001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getRss001SumMoney())+Double.parseDouble(billList.get(i).getRss001SumMoney()))/1000)+"");
            billList.get(i).setUep001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getUep001SumMoney())+Double.parseDouble(billList.get(i).getUep001SumMoney()))/1000)+"");
            billList.get(i).setVip001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getVip001SumMoney())+Double.parseDouble(billList.get(i).getVip001SumMoney()))/1000)+"");
            billList.get(i).setVir001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getVir001SumMoney())+Double.parseDouble(billList.get(i).getVir001SumMoney()))/1000)+"");
            billList.get(i).setVir002SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getVir002SumMoney())+Double.parseDouble(billList.get(i).getVir002SumMoney()))/1000)+"");
            billList.get(i).setWdp001SumMoney(Math.ceil((Double.parseDouble(billList.get(0).getWdp001SumMoney())+Double.parseDouble(billList.get(i).getWdp001SumMoney()))/1000)+"");

        }
        return billList.get(0);
    }

    //将OI账单里面的金额放入到对应的contractLine的sumMoney中---实际费用(含税)
    public List<TtaContractLineEntity_HI_RO> insertSunMoney(TtaOiBillLineEntity_HI_RO bill,List<TtaContractLineEntity_HI_RO> contractLineList){

        for(int i=0;i<contractLineList.size();i++){
            contractLineList.get(i).setSumMoney("0");
            if(contractLineList.get(i).getTermsCn().indexOf("(集中收退货)购货折扣")!=-1&&contractLineList.get(i).getTermsCn().indexOf("购货折扣")!=-1){
                contractLineList.get(i).setSumMoney(bill.getAddaSumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("残损")!=-1&&contractLineList.get(i).getTermsCn().indexOf("购货折扣")!=-1){
                contractLineList.get(i).setSumMoney(bill.getAddgSumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("宣传牌及促销用品制作费")!=-1){
                contractLineList.get(i).setSumMoney(bill.getAdpfSumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("一般购货折扣")!=-1){
                contractLineList.get(i).setSumMoney(bill.getAdrbSumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("目标退佣")!=-1){
                contractLineList.get(i).setSumMoney(bill.getAdtrSumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("节庆促销服务费")!=-1&&contractLineList.get(i).getTermsCn().indexOf("周年庆、春节、劳动节、国庆节、圣诞节")!=-1){
                contractLineList.get(i).setSumMoney(bill.getAss001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("促销服务费")!=-1){
                contractLineList.get(i).setSumMoney(bill.getBac001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("商业发展基金")!=-1){
                contractLineList.get(i).setSumMoney(bill.getBdfSumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("成本补差")!=-1){
                contractLineList.get(i).setSumMoney(bill.getCos001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("优惠券")!=-1){
                contractLineList.get(i).setSumMoney(bill.getCou001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("其他业务收入-赔偿(补偿)收入")!=-1){
                contractLineList.get(i).setSumMoney(bill.getCps001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("专柜促销陈列服务费")!=-1){
                contractLineList.get(i).setSumMoney(bill.getCri001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("宣传单张制作费(快讯及传单)")!=-1){
                contractLineList.get(i).setSumMoney(bill.getDmi001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("促销陈列服务费(促销助理管理费)")!=-1){
                contractLineList.get(i).setSumMoney(bill.getDrb001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("促销陈列服务费(端架)")!=-1){
                contractLineList.get(i).setSumMoney(bill.getDrg001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("促销陈列服务费(焦点货架)")!=-1){
                contractLineList.get(i).setSumMoney(bill.getDrh001summoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("促销陈列服务费(其他)")!=-1){
                contractLineList.get(i).setSumMoney(bill.getDro001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("(提前付款)购货折扣")!=-1){
                contractLineList.get(i).setSumMoney(bill.getEpSumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("H与B健与美")!=-1){
                contractLineList.get(i).setSumMoney(bill.getHbi001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("延误送货罚款")!=-1){
                contractLineList.get(i).setSumMoney(bill.getLdp001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("延误退货罚款")!=-1){
                contractLineList.get(i).setSumMoney(bill.getLpu001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("送货满足率低于95%罚款")!=-1){
                contractLineList.get(i).setSumMoney(bill.getNfp001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("新品种宣传推广服务费")!=-1){
                contractLineList.get(i).setSumMoney(bill.getNpd001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("新城市宣传推广服务费")!=-1){
                contractLineList.get(i).setSumMoney(bill.getNpm001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("新店宣传推广服务费")!=-1){
                contractLineList.get(i).setSumMoney(bill.getNss001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("其他业务费用")!=-1){
                contractLineList.get(i).setSumMoney(bill.getNti001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("差旅费(供应商承担部份)")!=-1){
                contractLineList.get(i).setSumMoney(bill.getOtf001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("其他")!=-1){
                contractLineList.get(i).setSumMoney(bill.getOth001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("促销服务费(VAT项目)")!=-1){
                contractLineList.get(i).setSumMoney(bill.getPsf001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("陈列区域装修费")!=-1){
                contractLineList.get(i).setSumMoney(bill.getRss001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("未在合同上的提早付款折扣")!=-1){
                contractLineList.get(i).setSumMoney(bill.getUep001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("会员优惠")!=-1){
                contractLineList.get(i).setSumMoney(bill.getVip001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("退货运输费")!=-1){
                contractLineList.get(i).setSumMoney(bill.getVir001SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("集中收货")!=-1&&contractLineList.get(i).getTermsCn().indexOf("购货折扣")!=-1){
                contractLineList.get(i).setSumMoney(bill.getVir002SumMoney());
            }
            if(contractLineList.get(i).getTermsCn().indexOf("节庆促销服务费")!=-1&&contractLineList.get(i).getTermsCn().indexOf("38妇女节")!=-1){
                contractLineList.get(i).setSumMoney(bill.getWdp001SumMoney());
            }
            if("".equals(contractLineList.get(i).getTermsCn())){
                contractLineList.get(i).setSumMoney("0");
            }

        }
        return contractLineList;
    }

    //去除空格，中文符号转英文符号
    public List<TtaContractLineEntity_HI_RO> changeChar(List<TtaContractLineEntity_HI_RO> contractLineList){
        for(int i=0;i<contractLineList.size();i++){
            if(contractLineList.get(i).getTermsCn()!=null){
                contractLineList.get(i).setTermsCn(contractLineList.get(i).getTermsCn().trim().replace("（","(").replace("）",")"));
            }

            contractLineList.get(i).setPurchase((contractLineList.get(i).getPurchase()==null?new BigDecimal(0):contractLineList.get(i).getPurchase()));
            contractLineList.get(i).setSales((contractLineList.get(i).getSales()==null?new BigDecimal(0):contractLineList.get(i).getSales()));
            contractLineList.get(i).setFeeIntas((contractLineList.get(i).getFeeIntas()==null?new BigDecimal(0):contractLineList.get(i).getFeeIntas()));
            contractLineList.get(i).setFeeNotax((contractLineList.get(i).getFeeNotax()==null?new BigDecimal(0):contractLineList.get(i).getFeeNotax()));
            contractLineList.get(i).setTtaValue(Math.ceil(Double.parseDouble(contractLineList.get(i).getTtaValue()==null?"0":contractLineList.get(i).getTtaValue()))+"");
            //contractLineList.get(i).setSumMoney("0");
        }
        return contractLineList;
    }

    public List<TtaContractLineEntity_HI> insertSumMenoy(List<TtaContractLineEntity_HI> contractLineList){
            return null;
    }

    public TtaProposalHeaderEntity_HI initProposalHeader(){
        TtaProposalHeaderEntity_HI entity = new TtaProposalHeaderEntity_HI();
        entity.setRemark("");
        entity.setProposalId(0);
        return entity;
    }

    public TtaProposalTermsHeaderEntity_HI initProposalTermsHeader(){

        TtaProposalTermsHeaderEntity_HI entity = new TtaProposalTermsHeaderEntity_HI();
        entity.setRemark("");
        entity.setBeoiTax("0");
        entity.setConsignmentDiscount(new Float(0));
        entity.setFcsPurchse(new Float(0));
        entity.setFcsSales(new Float(0));
        entity.setGp(new Float(0));
        entity.setSalesUpScale(new Float(0));
        entity.setSalesType("");
        entity.setTermsVersion("");
        entity.setProposalId(0);
        return entity;
    }

    public TtaContractLineEntity_HI_RO initContractLine(){
        TtaContractLineEntity_HI_RO entity = new TtaContractLineEntity_HI_RO();
        entity.setRemark("");
        entity.setFeeNotax(new BigDecimal(0));
        entity.setFeeIntas(new BigDecimal(0));
        entity.setPurchase(new BigDecimal(0));
        entity.setSales(new BigDecimal(0));
        entity.setTtaValue("0");
        entity.setUnit("0");
        entity.setTermsCn("");
        entity.setReferenceStandard(0);
        return entity;
    }

    /**
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveAnalysis")
    public String saveAnalysis(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();

            JSONObject jsonObject = JSON.parseObject(params);
            TtaAnalysisEditDataEntity_HI instance = ttaContractHeaderServer.saveAnalysis(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

            JSONObject jsonObject = this.parseObject(params);
            Pagination<TtaContractHeaderEntity_HI_RO> result = ttaContractHeaderServer.find(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = this.parseObject(params);
			TtaContractHeaderEntity_HI instance = ttaContractHeaderServer.saveOrUpdate(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
            LOGGER.error("IllegalArgumentException 批量保存条款数失败：" + e);
			return SToolUtils.convertResultJSONObj("E", "参数异常：" + e.getMessage(), 0, null).toString();
		} catch (Exception e) {
            LOGGER.error("批量保存条款数失败：" + e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
		}
	}

	/**
	 * @param params- 主键
	 * @description 删除数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		try {
			if (StringUtils.isBlank(params)) {
				return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
			}
			JSONObject jsonObject = JSON.parseObject(params);
			String[] ids = jsonObject.getString("ids").split(",");
			for (String pkId : ids) {
				ttaContractHeaderServer.delete(Integer.parseInt(pkId));
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
	/**
	 * 查询详情
	 *
	 * @param params
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	@Override
	public String findById(String params) {
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			TtaContractHeaderEntity_HI_RO instance = ttaContractHeaderServer.findByRoId(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();

		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页 字典）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findLov")
	public String findLov(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = this.parseObject(params);
			Pagination<TtaContractHeaderEntity_HI_RO> result = ttaContractHeaderServer.find(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * @param params
	 * @description 确认提交
	 */
	@RequestMapping(method = RequestMethod.POST, value = "confirm")
	public String confirm(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaContractHeaderEntity_HI instance = ttaContractHeaderServer.updateSubmit(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 作废
	 */
	@RequestMapping(method = RequestMethod.POST, value = "cancelConfirm")
	public String cancelConfirm(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaContractHeaderEntity_HI instance = ttaContractHeaderServer.updatecancel(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 计算
	 */
	@RequestMapping(method = RequestMethod.POST, value = "count")
	public String count(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			Map<String, Object> instance = ttaContractHeaderServer.callCount(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}