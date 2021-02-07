package com.sie.watsons.base.contract.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractHeaderEntity_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractHeaderEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineHEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineProposalEntity_HI_RO;
import com.sie.watsons.base.contract.model.inter.ITtaContractLine;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnHEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Component("ttaContractLineServer")
public class TtaContractLineServer extends BaseCommonServer<TtaContractLineEntity_HI> implements ITtaContractLine{

    static final List FIELD_LIST = Arrays.asList(new String[]{"delivery_granary","sales_area", "purchase", "sales", "tta_value"});

	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractLineServer.class);

	@Autowired
	private ViewObject<TtaContractLineEntity_HI> ttaContractLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaContractLineProposalEntity_HI_RO> ttaContractLineProposalDAO_HI_RO;

    @Autowired
    private BaseViewObject<BaseLookupValuesEntity_HI_RO> baseLookupValuesDao_HI_RO;

    @Autowired
    private BaseViewObject<TtaOiBillLineEntity_HI_RO> ttaOiBillLineDao_HI_RO;

	@Autowired
	private BaseViewObject<TtaContractLineEntity_HI_RO> ttaContractLineDAO_HI_RO;

    @Autowired
    private BaseViewObject<TtaContractHeaderEntity_HI_RO> ttaContractHeaderEntity_HI_RO;

    @Autowired
    private BaseCommonDAO_HI<TtaContractHeaderEntity_HI> ttaContractHeaderLineDAO_HI;

    @Autowired
    private ViewObject<TtaProposalHeaderEntity_HI> ttaProposalHeaderEntity_HI;

    @Autowired
    private BaseCommonDAO_HI<TtaContractLineEntity_HI> ttaContractLineEntity_HI;

	public TtaContractLineServer() {
		super();
	}


    /*************************************************************start**********************************************************************/
    /**
     * 功能描述： 通过proposal编码查询proposal 头部信息及proposal 条款信息
     */
    @Override
    public Map<String, Object> findByPro(JSONObject queryParamJSON) {
        String varUserType = queryParamJSON.getString("varUserType");
        Integer contractHId = queryParamJSON.getInteger("contractHId"); //合同头id
        Integer proposalId = queryParamJSON.getInteger("proposalId"); //proposalid
        if (contractHId == null && proposalId == null) {
            Assert.notNull(null, "必传参数不能为空!");
        }
        List<String> proposalTitleList = null;
        List<String> proposalTitleBottomList = null;
        List<String> proposalContractList = null;
        List<List<Map<String, String>>> vendorContractList = null;

        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap();;
        StringBuffer sql = new StringBuffer();
        if (contractHId != null) {
            params.put("contractHId", contractHId);
            sql.append(TtaContractHeaderEntity_HI_RO.queryProposalHeaderAndContractSql());//查询合同表及proposal信息
        } else {
            params.clear();
            params.put("proposalId", proposalId);
            sql.append(TtaContractHeaderEntity_HI_RO.queryProposalHeaderSql());//没有保存时，查询proposal信息
        }
        //合同头部信息
        TtaContractHeaderEntity_HI_RO contractHeader = ttaContractHeaderEntity_HI_RO.get(sql.toString(), params);
        proposalId = proposalId != null ? proposalId : contractHeader.getProposalId();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("proposalId", proposalId);
        //最顶部title 如： 数据分享费|新品额外折扣 (首6个月)等数据
        List<Map<String, Object>> titleList = ttaContractHeaderLineDAO_HI.queryNamedSQLForList(TtaContractHeaderEntity_HI_RO.getContractTitleSlq(), queryMap);

        if (titleList == null  || titleList.isEmpty()) {
            Assert.notNull(null, "该proposal信息无条款数据！");
        }
        //title第二部分的值如： 条款值|条款内容|条款值|条款内容
        List<Map<String, Object>> titleBottomList = ttaContractHeaderLineDAO_HI.queryNamedSQLForList(TtaContractHeaderEntity_HI_RO.getContractBottomTitle(), queryMap);
        proposalTitleList = Arrays.asList((titleList.get(0).get("VALUE") + "").split("\\|"));
        proposalTitleBottomList = Arrays.asList((titleBottomList.get(0).get("VALUE") + "").split("\\|"));//顶部top标题信息展示
        //父级proposal合同条款信息展示，如1029448105 成都艾米莲商贸有限责任公司
        List<Map<String, Object>>  contractList = ttaContractHeaderLineDAO_HI.queryNamedSQLForList(TtaContractHeaderEntity_HI_RO.getContractTermsAndValueSlq(), queryMap);
        proposalContractList = Arrays.asList((contractList.get(0).get("VALUE") + "").split("\\|"));

        //各供应商的合同条款信息
        if (contractHId != null) {
            //取出供应商信息
            Map<String, Object> queryVendorParams = new HashMap<>();
            queryVendorParams.put("contractHId", contractHId);
            List<TtaContractLineEntity_HI_RO> list = ttaContractLineDAO_HI_RO.findList(TtaContractHeaderEntity_HI_RO.getContractVendorList(), queryVendorParams);
            if (list != null && !list.isEmpty()) {
                Map<String, Object> queryVendorMap = null;
                vendorContractList = new ArrayList<>();
                for (TtaContractLineEntity_HI_RO venderEntity : list) {
                    queryVendorMap = new HashMap<>();
                    queryVendorMap.put("proposalId", proposalId);
                    queryVendorMap.put("vendorCode", venderEntity.getVendorCode());
                    queryVendorMap.put("contractHId", contractHId);
                    //子级合同拆分具体数据值：如1029448105 成都艾米莲商贸有限责任公司
                    List<Map<String, Object>>  splitVendorContractList = ttaContractHeaderLineDAO_HI.queryNamedSQLForList(TtaContractHeaderEntity_HI_RO.getVendorContractTermsAndValueSlq(varUserType), queryVendorMap);
                    List<Map<String, String>> objects = new ArrayList<>();
                    for (Map<String, Object> valueMap : splitVendorContractList) {
                        List<String> idFieldNameValueList = Arrays.asList( (valueMap.get("VALUE") + "").split("\\|")); //[1@fieldName1@value1,2@fieldName2@value3]
                        Map<String, String> unitMap = null;
                        for (String value : idFieldNameValueList) {
                            String[] splitArr = value.split("@");
                            unitMap = new HashMap<>();
                            unitMap.put("id", splitArr[0]);
                            unitMap.put("fieldName", splitArr[1]);
                            unitMap.put("fieldValue", splitArr[2].replace("-9999",""));
                            objects.add(unitMap);
                        }
                        if (!objects.isEmpty()) {
                            vendorContractList.add(objects);
                        }
                    }
                }
            }
        }
        //P字头供应商，付款天数<60天，品牌（指定品牌排除），此标识系统自动带出Y.
        if (contractHId == null) {
           String paySql = " select  (case when NVL(t.pay_days,0)  < 60 then 'N' else 'Y' end) as IS_SPECIAL from  tta_proposal_terms_header t where t.proposal_id =" + proposalId;
            List<Map<String, Object>> listMap = ttaContractHeaderLineDAO_HI.queryNamedSQLForList(paySql, new HashMap<String, Object>());
            if (listMap != null && !listMap.isEmpty()) {
                String isSpecial = listMap.get(0).get("IS_SPECIAL") + "";
                boolean flag = contractHeader.getVendorNbr().contains("P") && ("Y".equalsIgnoreCase(isSpecial)) ;
                contractHeader.setIsSpecial(flag ? "Y" : "N");
            }
        }
        resultMap.put("contractHeader", contractHeader);
        resultMap.put("proposalTitleList", proposalTitleList);
        resultMap.put("proposalTitleBottomList", proposalTitleBottomList);
        resultMap.put("proposalContractList", proposalContractList);
        resultMap.put("vendorContractList", vendorContractList);//合同具体值
        return resultMap;
    }

    /**
     * 功能描述： 合同拆分供应商信息
     */
    @Override
    public void saveSplitVenders(JSONObject queryParamJSON) {
        JSONArray venderList = queryParamJSON.getJSONArray("venderList");
        Integer contractHId = queryParamJSON.getInteger("contractHId");
        Integer proposalId = queryParamJSON.getInteger("proposalId");
        Integer userId = queryParamJSON.getInteger("varUserId");
        String isSpecial = StringUtils.isEmpty(queryParamJSON.getString("isSpecial")) ? "N": queryParamJSON.getString("isSpecial");//是否特批
        Assert.notNull(venderList, "参数错误，拆分的供应商不能为空!");
        Assert.notNull(contractHId, "参数错误，合同头ID不能为空!");
        Assert.notNull(proposalId, "参数错误，proposal参数不能为空!");
        
        //执行合同拆分操作
        for (int i = 0; i < venderList.size(); i ++) {
            JSONObject venderObject = venderList.getJSONObject(i);
            String supplierCode = venderObject.getString("supplierCode");
            String supplierName = venderObject.getString("supplierName");
            LOGGER.info("正则执行拆供应商，参数信息contractHId:{},proposalId:{},userId:{},supplierCode:{},supplierName:{}", contractHId, proposalId, userId, supplierCode, supplierName);
            ttaContractLineEntity_HI.executeSqlUpdate(TtaContractLineEntity_HI_RO.getSplitVenderToContractSql(proposalId, contractHId, supplierCode, supplierName, userId, isSpecial));

        }
    }

    /**
     * 功能描述： 保存拆分结果
     */
    @Override
    public void saveBatchSplitResult(JSONObject queryParamJSON) {
        //DELIVERY_GRANARY,SALES_AREA,PURCHASE,SALES,TTA_VALUE
        JSONArray vendorContractList = queryParamJSON.getJSONArray("vendorContractList");
        List<Map<String,Map<String, Object>>> entitylist = new ArrayList<>();

        //初始化采购额
        Map<String, BigDecimal> salePuracheTotalMap = new HashMap<String, BigDecimal>();
        salePuracheTotalMap.put("-5",new BigDecimal("0"));
        salePuracheTotalMap.put("-6",new BigDecimal("0"));

        for (int idx = 0; idx < vendorContractList.size(); idx ++) {
            Map<String,Map<String, Object>> rowMap = new HashMap<>();
            Map<String, Object> propertiesMap = null;
            JSONArray jsonArray = vendorContractList.getJSONArray(idx);
            HashMap<String, Object> attMap = new HashMap<>();//存储供应商编码、名称，配送仓库，销售区域，销售金额，采购金额附加信息
            for (int rowIdx = 0; rowIdx < jsonArray.size(); rowIdx ++ ) {
                JSONObject jsonMap = jsonArray.getJSONObject(rowIdx);
                String id = jsonMap.getString("id");
                String fieldName = jsonMap.getString("fieldName").toLowerCase();
                String fieldValue = jsonMap.getString("fieldValue");
                if ("-1,-2,-3,-4,-5,-6,-7".contains(id)) { //分别表示供应商编码、名称，配送仓库，销售区域，销售金额，采购金额附加信息
                    attMap.put(fieldName, fieldValue);
                    if ("-5,-6".contains(id)) {
                        //-5：销售，-6采购数据
                        Object value = salePuracheTotalMap.get(id) == null ? new BigDecimal(0) : salePuracheTotalMap.get(id);
                        BigDecimal totalValues = new BigDecimal(value.toString()).add(new BigDecimal(fieldValue));
                        salePuracheTotalMap.put(id, totalValues);
                    }
                }
                propertiesMap = rowMap.get(id);
                if (propertiesMap == null || propertiesMap.isEmpty()) {
                    propertiesMap = new HashMap<>();
                    rowMap.put(id, propertiesMap);
                }
                propertiesMap.putAll(attMap);
                propertiesMap.put("contractLId", id);
                propertiesMap.put(SaafToolUtils.remove_AndNextChar2UpperCase(fieldName), fieldValue);
            }
            entitylist.add(rowMap);
        }

        LinkedHashSet<String> parentIds = new LinkedHashSet<>();//父节点ids
        Map<String, BigDecimal> totalValueMap = new HashMap<>(); //求和
        //存储校验相关的求和信息
        List<Map<String,Object>> updateList = new ArrayList<>();
        for (Map<String,Map<String, Object>> rowMap : entitylist) {
            Collection<Map<String, Object>> mapValues = rowMap.values();
            for (Map<String, Object> map : mapValues) {
                updateList.add(map);
                checkTotal(parentIds, totalValueMap, map);
            }
        }

        //校验开始
        String sql = "select contract_l_id as \"contractLId\",nvl(tta_value, 0) as \"ttaValue\",terms_cn as \"termsCn\", t.sales as \"sales\", t.purchase as  \"purchase\"  from tta_contract_line t where t.contract_l_id in ('" + String.join("','", parentIds) + "') order by t.order_no ";
        List<Map<String, Object>> ttaValueList = ttaContractLineEntity_HI.queryNamedSQLForList(sql, new HashMap<>());
        Assert.notEmpty(ttaValueList, "没有对应的父级条款信息");
        BigDecimal saleTotal = salePuracheTotalMap.get("-5");//拆分后销售总额
        BigDecimal purchaseTotal = salePuracheTotalMap.get("-6");//拆分后采购总额
        BigDecimal sale = new BigDecimal(ttaValueList.get(0).get("sales").toString());
        BigDecimal purchase =   new BigDecimal(ttaValueList.get(0).get("purchase").toString());
        if (saleTotal.compareTo(sale) != 0) {
            Assert.isTrue(false, "请检查销售金额列求和不相等,原始值是:" + sale + ",拆分后的总值是:" + saleTotal);
        }
        if (purchaseTotal.compareTo(purchase) != 0) {
            Assert.isTrue(false, "请检查采购金额列求和不相等,原始值是:" + purchase + ",拆分后的总值是:" + purchaseTotal);
        }
        for (Map<String, Object> ttaValueMap : ttaValueList) {
            String originContractLId = ttaValueMap.get("contractLId") + "";
            String  originTtaValue = ttaValueMap.get("ttaValue") == null ? "0" : ttaValueMap.get("ttaValue") + "";
            Object termsCn = ttaValueMap.get("termsCn");
            BigDecimal splitTotalValue = totalValueMap.get(originContractLId);
            BigDecimal bigDecimal = new BigDecimal(originTtaValue);
            System.out.println(originTtaValue + "-----" + splitTotalValue);
            if (bigDecimal.compareTo(splitTotalValue) != 0) {
                Assert.isTrue(false, "请检查条款【" + termsCn + "】列求和不相等,原始值是:" + originTtaValue + ",拆分后的总值是:" + splitTotalValue);
            }
        }

        ttaContractLineEntity_HI.updateBatchListMap(updateList);
    }

    private void checkTotal(LinkedHashSet<String> parentIds, Map<String, BigDecimal> totalValueMap, Map<String, Object> map) {
        if (!map.containsKey("splitParentId")) {
            return;
        }
        //添加求和数据
        String splitParentId = map.get("splitParentId") + "";//拆分供应商的父级id
        parentIds.add(splitParentId);
        Object ttaValue = map.get("ttaValue");//拆分供应商的ttaValue值
        if (splitParentId != null &&  SaafToolUtils.isNumber(ttaValue)) {
            Object  ttaValueTemp = totalValueMap.get(splitParentId);
            BigDecimal valueTemp = ttaValueTemp == null ? new BigDecimal(0) : new BigDecimal(ttaValueTemp.toString());
            BigDecimal totalVlue = new BigDecimal(ttaValue.toString()).add(valueTemp);
            totalValueMap.put(splitParentId, totalVlue);
        }
    }

    public static void main(String[] args) {
        LinkedHashSet<String> parentIds = new LinkedHashSet<>();//父节点ids
        parentIds.add("1");
        parentIds.add("2");
        parentIds.add("3");
        String sql = "select tta_value  from tta_contract_line t where t.contract_l_id in ('" + String.join("','", parentIds) + "')";
        System.out.println(sql);
    }


    /*************************************************************end**********************************************************************/

    @Override
    public List<TtaContractLineEntity_HI_RO> findAnalysisLine(String proposalId,String year) {
        Pagination<TtaContractLineEntity_HI_RO> findList = null;
        String sql = TtaContractLineEntity_HI_RO.findList;
        sql = sql.replace("859",proposalId);
        sql = sql.replace("2018",year);
        findList = ttaContractLineDAO_HI_RO.findPagination(sql, null, 0, 99999);
        return findList.getData();
    }

    @Override
    public List<TtaContractLineEntity_HI_RO> findAnalysisFreeGood(String proposalId) {
        Pagination<TtaContractLineEntity_HI_RO> findList = null;
        StringBuffer sql = new StringBuffer(TtaContractLineEntity_HI_RO.freeGoodfindList);
        JSONObject queryParamJSON = new JSONObject();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        queryParamJSON.put("proposalId", proposalId);
        queryParamJSON.put("termsCn", "免费货");
        SaafToolUtils.parperParam(queryParamJSON, "l.PROPOSAL_ID", "proposalId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "l.terms_cn", "termsCn", sql, paramsMap, "like");
        findList = ttaContractLineDAO_HI_RO.findPagination(sql, paramsMap, 0, 99999);
        return findList.getData();
    }

    @Override
    public List<Map<String, Object>> findContractLine(String contractLine){
        //原sql:select DISTINCT c.Y_TERMS_SOURCE,c.Y_TERMS_TARGET,c.TABLE_NAME,c.year from TTA_TERMS_AC_COUNT c
        //                where c.CONTRACT_TERM_CN is not null  ORDER BY c.Y_TERMS_SOURCE ;

        String sql = "select DISTINCT decode(c.Y_TERMS_SOURCE,\n" +
                "                       '退货处理服务费',\n" +
                "                       '(集中收货) 购货折扣',\n" +
                "                       c.Y_TERMS_SOURCE) as Y_TERMS_SOURCE,\n" +
                "                c.Y_TERMS_TARGET,\n" +
                "                c.TABLE_NAME,\n" +
                "                c.year,\n" +
                "                c.contract_term_cn\n" +
                "  from tta_terms_ac_count_original c\n" + //当往年的Proposal为2018年的单据时,使用tta_terms_ac_count_original表
                " where c.CONTRACT_TERM_CN is not null\n" +
                " ORDER BY Y_TERMS_SOURCE";
        List<Map<String, Object>> list =ttaContractLineEntity_HI.queryNamedSQLForList(sql,new HashMap<>());
        return list;
    }

    /**
     * 查询OI条款对应的表和表字段
     * @param oldProposalYear Proposal制作年度
     * @date 2020.1.3
     * @return
     */
    @Override
    public List<Map<String, Object>> findTermsAcCount(String oldProposalYear) {
        String sql = "select DISTINCT c.Y_TERMS_SOURCE,c.Y_TERMS_TARGET,c.TABLE_NAME from TTA_TERMS_AC_COUNT c \n" +
                "where TO_CHAR(c.YEAR, 'YYYY') = '"+oldProposalYear+"'  ORDER BY c.Y_TERMS_SOURCE ";
        List<Map<String, Object>> list =ttaContractLineEntity_HI.queryNamedSQLForList(sql,new HashMap<>());
        return list;
    }

    @Override
    public List<Map<String, Object>> findBrand(String proposalId,String vendorCode){
        LOGGER.info("********Analysis查询品牌信息需要的参数ProposalId:{}--->>>供应商编号:{},方法名:findBrand***********",proposalId,vendorCode);
        String sql = "select " +
                "       brandpln_h_id,\n" +
                "       new_or_existing,\n" +
                "       vendor_nbr,\n" +
                "       vendor_name,\n" +
                "       po_record_sum,\n" +
                "       sales_sum,\n" +
                "       actual_gp,\n" +
                "       fcs_purchase,\n" +
                "       purchase_growth,\n" +
                "       fcs_sales,\n" +
                "       sales_growth,\n" +
                "       nvl(adjust_gp,gp) gp,\n" +
                "       remark,\n" +
                "       creation_date,\n" +
                "       created_by,\n" +
                "       last_updated_by,\n" +
                "       last_update_date,\n" +
                "       last_update_login,\n" +
                "       version_num,\n" +
                "       proposal_id,\n" +
                "       po_record_sum2,\n" +
                "       sales_sum2,\n" +
                "       actual_gp2,\n" +
                "       year_code,\n" +
                "       data_source,\n" +
                "       adjust_gp\n" +
                " from TTA_BRANDPLN_HEADER h  \n" +
                "where h.VENDOR_NBR = '"+vendorCode+"' \n" +
                "and h.PROPOSAL_ID = '"+proposalId+"'  ";
        List<Map<String, Object>> list =ttaContractLineEntity_HI.queryNamedSQLForList(sql,new HashMap<>());
        return list;
    }

    @Override
    public List<TtaContractLineEntity_HI> findByTerm(String proposalId) {
        String sql = "from TtaContractLineEntity_HI l where " +
                "l.proposalId = "+proposalId +" order by l.oiType";
        List<TtaContractLineEntity_HI> findList = ttaContractLineDAO_HI.findList(sql);
        return findList;
    }

    //按照年份来查,analysis没有加月份
    @Override
    public List<Map<String, Object>> findOIList(String oiType,String actuallyCountDate,String vendorCode,String year) {
        //TTA_ABOI_BILL_LINE,TTA_BEOI_BILL_LINE,TTA_SROI_BILL_LINE
//        String sql = "select * from "+oiType+" b where b.rms_code = '"+vendorCode+"' \n" +
//                "and SUBSTR(TO_CHAR(b.ACCOUNT_MONTH,'yyyy-mm-dd hh24:mi:ss'), 1,4) = '"+year+"'";
        String sql = "";
        Date d = new Date();
        String y = (d.getYear()+1900)+"";//当前系统年度
        if(y.equals(year)){
           /* sql = "select b.* from "+oiType+" b where b.rms_code =  '"+vendorCode+"' " +
                    "and to_char(b.ACCOUNT_MONTH,'yyyy') = "+year+"  \n" +
                   "and b.CREATION_DATE <= TO_DATE('"+actuallyCountDate+"','yyyy-mm-dd hh24:mi:ss') \n" +
                    "union all \n" +
                    "select * from "+oiType+" o where 1=1  \n" +
                    "and to_char(o.ACCOUNT_MONTH,'yyyy') = "+year+" \n" +
                    "and o.RMS_CODE  in (  \n" +
                    "select s.REL_SUPPLIER_CODE from TTA_REL_SUPPLIER s  \n" +
                    "left join TTA_SUPPLIER p on p.SUPPLIER_ID = s.REL_ID  \n" +
                    " where p.SUPPLIER_CODE = '"+vendorCode+"') \n" +
                    "and to_char(o.ACCOUNT_MONTH,'yyyy') = "+year+"  \n" +
                    "and o.CREATION_DATE <= TO_DATE('"+actuallyCountDate+"','yyyy-mm-dd hh24:mi:ss') \n" +
                    " ";*/
            sql = "   select b.*\n" +
                    "  from "+oiType+" b\n" +
                    " where to_char(b.ACCOUNT_MONTH, 'yyyy') = "+year+"\n" +
                    "   and b.CREATION_DATE <= TO_DATE('"+actuallyCountDate+"', 'yyyy-mm-dd hh24:mi:ss')\n" +
                    "   and b.rms_code in(\n"+
                    TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorCode)
                    +")";
        }else{
         /*   sql = "select b.* from "+oiType+" b where b.rms_code =  '"+vendorCode+"' " +
                    "and to_char(b.ACCOUNT_MONTH,'yyyy') = "+year+"  \n" +
                    "union all \n" +
                    "select * from "+oiType+" o where 1=1  \n" +
                    "and to_char(o.ACCOUNT_MONTH,'yyyy') = "+year+" \n" +
                    "and o.RMS_CODE  in (  \n" +
                    "select s.REL_SUPPLIER_CODE from TTA_REL_SUPPLIER s  \n" +
                    "left join TTA_SUPPLIER p on p.SUPPLIER_ID = s.REL_ID  \n" +
                    " where p.SUPPLIER_CODE = '"+vendorCode+"') \n" +
                    "and to_char(o.ACCOUNT_MONTH,'yyyy') = "+year+"  \n" +
                    "";*/
            sql = "   select b.*\n" +
                    "  from "+oiType+" b\n" +
                    " where to_char(b.ACCOUNT_MONTH, 'yyyy') = "+year+"\n" +
                    "   and b.rms_code in(\n"+
                    TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorCode)
                    +")";
        }

        List<Map<String, Object>> list =ttaContractLineEntity_HI.queryNamedSQLForList(sql,new HashMap<>());
        return list;
    }

    /**
     * 查询主供应商和关联供应商的OI数据
     * @date 2020.1.3
     * @param tableName
     * @param actuallyCountDate 实际计算时间
     * @param vendorCode 供应商编号
     * @param oldProposalYear 往年Proposal制作年度
     * @return
     */
    @Override
    public List<Map<String, Object>> findOiResultList(String tableName, String actuallyCountDate, String vendorCode, String oldProposalYear) {
        String sql = "";
        Date d = new Date();
        String currentSysYear = (d.getYear()+1900)+"";
        if(currentSysYear.equals(oldProposalYear)){
            //2020.6.3 不使用这种方式
            /*sql = "select b.*\n" +
                    "  from "+tableName+" b\n" +
                    " where b.split_supplier_code = '"+vendorCode+"'\n" +
                    "   and substr(b.ACCOUNT_MONTH,1,4) = "+oldProposalYear+"\n" +
                    "   and b.creation_date <= TO_DATE('"+actuallyCountDate+"', 'yyyy-mm-dd hh24:mi:ss')\n" +
                    "union all\n" +
                    "select o.*\n" +
                    "  from "+tableName+" o\n" +
                    " where 1 = 1\n" +
                    "   and substr(o.ACCOUNT_MONTH,1,4) = "+oldProposalYear+"\n" +
                    "   and o.split_supplier_code in (select s.REL_SUPPLIER_CODE\n" +
                    "                        from TTA_REL_SUPPLIER s\n" +
                    "                        left join TTA_SUPPLIER p\n" +
                    "                          on p.SUPPLIER_ID = s.REL_ID\n" +
                    "                       where p.SUPPLIER_CODE = '"+vendorCode+"')\n" +
                    "   and o.CREATION_DATE <= TO_DATE('"+actuallyCountDate+"', 'yyyy-mm-dd hh24:mi:ss')";*/
            sql = " select b.*\n" +
                    "  from " + tableName + " b\n" +
                    " where 1=1 \n" +
                    "   and substr(b.ACCOUNT_MONTH,1,4) = " + oldProposalYear + "\n" +
                    "   and b.creation_date <= TO_DATE('"+actuallyCountDate+"', 'yyyy-mm-dd hh24:mi:ss') \n" +
                    "   and b.split_supplier_code in(\n"+
                    TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorCode)
                    +")";

        } else {
/*            sql = "select b.*\n" +
                    "  from "+tableName+" b\n" +
                    " where b.split_supplier_code = '"+vendorCode+"'\n" +
                    "   and substr(b.ACCOUNT_MONTH, 1,4) = "+oldProposalYear+"\n" +
                    "union all\n" +
                    "select *\n" +
                    "  from "+tableName+" o\n" +
                    " where 1 = 1\n" +
                    "   and substr(o.ACCOUNT_MONTH,1,4) = "+oldProposalYear+"\n" +
                    "   and o.split_supplier_code in (select s.REL_SUPPLIER_CODE\n" +
                    "                        from TTA_REL_SUPPLIER s\n" +
                    "                        left join TTA_SUPPLIER p\n" +
                    "                          on p.SUPPLIER_ID = s.REL_ID\n" +
                    "                       where p.SUPPLIER_CODE = '"+vendorCode+"') ";*/
            sql = " select b.*\n" +
                    "  from " + tableName + " b\n" +
                    " where 1=1 \n" +
                    "   and substr(b.ACCOUNT_MONTH,1,4) = " + oldProposalYear + "\n" +
                    "   and b.split_supplier_code in(\n"+
                    TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorCode)
                    +")";
        }

        List<Map<String, Object>> list =ttaContractLineEntity_HI.queryNamedSQLForList(sql,new HashMap<>());
        return list;
    }

    /**
     * 根据Proposal制作年度查找Oi类型表名
     * @date 2020.1.3
     * @param proposalYear
     * @return
     */
    @Override
    public List<Map<String,Object>> findOiTableName(String proposalYear) {
        String sql ="SELECT TABLE_NAME\n" +
                "  FROM TTA_TERMS_AC_COUNT TTACA\n" +
                " WHERE TO_CHAR(TTACA.YEAR, 'YYYY') = '"+proposalYear+"'\n" +
                "  -- AND TTACA.Y_TERMS_SOURCE = V.Y_TERMS\n" +
                " GROUP BY TTACA.TABLE_NAME";
        return ttaContractLineEntity_HI.queryNamedSQLForList(sql,new HashMap<>());
    }

    //同一proposal生效的变成失效
    @Override
    public void updateStatus(String proposalId,int userId) throws Exception{
        String sql1 = "from TtaContractLineEntity_HI l where l.status = '0' and \n" +
                "l.proposalId = "+proposalId;
        List<TtaContractLineEntity_HI> findList1 = ttaContractLineDAO_HI.findList(sql1);
        //如果存在失效旧数据就不再继续处理
        if(findList1.size()>0){
            return;
        }
        String sql = "from TtaContractLineEntity_HI l where l.status = '1' and \n" +
                "l.proposalId = "+proposalId;
        List<TtaContractLineEntity_HI> findList = ttaContractLineDAO_HI.findList(sql);
        for(TtaContractLineEntity_HI entity:findList) {
            entity.setStatus("0");
            JSONObject jsonObject = new JSONObject();
            JSONObject params = JSONObject.parseObject(jsonObject.toJSONString(entity));
            TtaContractLineEntity_HI instance = SaafToolUtils.setEntity(TtaContractLineEntity_HI.class,params, ttaContractLineDAO_HI, userId);
            ttaContractLineDAO_HI.saveOrUpdate(instance);
        }
    }

    @Override
    public Pagination<BaseLookupValuesEntity_HI_RO> findLookUpCode() {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaContractLineProposalEntity_HI_RO.TTA_LOOKUP_CODE);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        Pagination<BaseLookupValuesEntity_HI_RO> findList = baseLookupValuesDao_HI_RO.findPagination(sql, paramsMap, 0, 1000);
        return findList;
    }

    @Override
    public Pagination<TtaOiBillLineEntity_HI_RO> findBillOi(String vendorCode,String year) {
        String sql = TtaOiBillLineEntity_HI_RO.SUM_MONEY;
        sql.replace("1067634101",vendorCode);
        sql.replace("2019",year);

        Pagination<TtaOiBillLineEntity_HI_RO> findList = ttaOiBillLineDao_HI_RO.findPagination(sql, null, 0, 1000);
        return findList;
    }

    @Override
    public Pagination<TtaContractLineProposalEntity_HI_RO> findByCode(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaContractLineProposalEntity_HI_RO.TTA_CONTRACT_LINE);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "cl.order_no", "orderNo", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "cl.id desc", false);
        Pagination<TtaContractLineProposalEntity_HI_RO> findList = ttaContractLineProposalDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }

	@Override
	public Pagination<TtaContractLineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaContractLineEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.contract_L_Id", "contractLId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.contract_H_Id", "contractHId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Nbr", "vendorNbr", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Name", "vendorName", sql, paramsMap, "like");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.contract_L_Id desc", false);
		Pagination<TtaContractLineEntity_HI_RO> findList = ttaContractLineDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}


    @Override
	public TtaContractLineEntity_HI save(String paramsJSON, String contractIdString,int userId) throws Exception {
		JSONArray jsonList = JSONArray.parseArray(paramsJSON);
        List<TtaContractLineEntity_HI> contractLineList = new ArrayList<TtaContractLineEntity_HI>();
        List<TtaContractLineEntity_HI> findList =null;
        String str =  contractIdString.substring(1);
        String[] contractIds = str.split(",");
        int step = 0;
        int status = 0;
		for(int i = 0;i<jsonList.size();i++){
			JSONObject jsonObj = jsonList.getJSONObject(i);
			String vendorNbr = (String)jsonObj.get("term1");
			String vendorName = (String)jsonObj.get("term2");
			String deptDesc = (String)jsonObj.get("term3");
			String brandCn = (String)jsonObj.get("term4");
			String purchase = (String)jsonObj.get("term5");
			String sales = (String)jsonObj.get("term6");
			String salesArea = (String)jsonObj.get("term7");
			String deliveryGranary = (String)jsonObj.get("term8");

            //前端标记是否要新增header的变量
            String contractHId = jsonObj.get("contractHId")+"";
            Integer proposalId = (Integer)jsonObj.get("proposalId");
            Integer contractLId = null;

//            if(null!=jsonObj.get("contractLId")&&!"".equals(jsonObj.get("contractLId"))){
//                contractLId = Integer.parseInt(jsonObj.get("contractLId").toString());
//            }

            if(i == 0){
                //查出旧数据做模板
                String sql = "from TtaContractLineEntity_HI l where l.status = '0' and \n" +
                        "l.proposalId = "+proposalId;
                findList = ttaContractLineDAO_HI.findList(sql);
                if(contractIds.length>0) {
                    for (int m = 0; m < findList.size(); m++) {
                        //传过来行id与旧数据id存在相同，则认为是同一批旧数据，拆分未保存，接下来做新增保存
                        if (Integer.parseInt(contractIds[0])==findList.get(m).getContractLId()) {
                            status = 1;
                        }
                    }
                }
            }

            //插入数据后返回的HID
            Integer hId = 0;

			String collectionMethod = "";
			String ttaValue = "";
			String unit = "";
			String terms = "";
			String feeIntas = "";
			String feeNotax = "";
            String oiType = "";
            String termCn = "";
            //计算一行的列数，其中3为AEOI，TermCn，真实值三类
			for(int k = 1;k<=(jsonObj.size()-3)/3;k++){
				String name = (String)jsonObj.get("term"+k);
				if((k-8)%6==1){
					collectionMethod = (String)jsonObj.get("term"+k);
                    //0时是编辑保存状态
                    if(status==0){
                        //每6格为一条contractLine数据，获取对应ID
                        contractLId = Integer.parseInt(contractIds[step]);
                        step = step +1;
                    }

				}
				if((k-8)%6==2){
					ttaValue = (String)jsonObj.get("term"+k);
                    oiType = (String)jsonObj.get("oiType"+k);
                    termCn = (String)jsonObj.get("termCn"+k);
				}
				if((k-8)%6==3){
					unit = (String)jsonObj.get("term"+k);
				}
				if((k-8)%6==4){
					terms = (String)jsonObj.get("term"+k);
				}
				if((k-8)%6==5){
					feeIntas = (String)jsonObj.get("term"+k);
				}
				if((k-8)%6==0&&k>8){
					feeNotax = (String)jsonObj.get("term"+k);

                //一个term加载完则添加一个contractLine实体
                    TtaContractLineEntity_HI line = new TtaContractLineEntity_HI();
                    TtaContractLineEntity_HI lineOld = new TtaContractLineEntity_HI();

                    if(status==1){
                        //查出旧数据做模板，以（OI类型+条款名称）确定那一条合同行
                        for(int m=0;m<findList.size();m++){
                            if(oiType.equals(findList.get(m).getOiType())&&termCn.equals(findList.get(m).getTermsCn())){
                                lineOld = findList.get(m);
                            }
                        }
                    }else{
                        line.setContractLId(contractLId);
                    }

                    //每一条Iterm插入一条Line数据
                    // TtaContractLineEntity_HI line = new TtaContractLineEntity_HI();
                    line.setVendorCode(vendorNbr);
                    line.setVendorName(vendorName);
                    line.setOrgCode(deptDesc);
                    line.setTradeMark(brandCn);
                    line.setPurchase(new BigDecimal("".equals(purchase)?"0":purchase));
                    line.setSales(new BigDecimal(sales));
                    line.setSalesArea(salesArea);
                    line.setDeliveryGranary(deliveryGranary);
                    line.setCollectType(collectionMethod);
                    line.setTtaValue(ttaValue);
                    line.setUnit(unit);
                    line.setTermsCn(terms);
                    line.setFeeIntas(new BigDecimal("".equals(feeIntas)?"0":feeIntas));
                    line.setFeeNotax(new BigDecimal("".equals(feeNotax)?"0":feeNotax));
                    line.setCreatedBy(userId);
                    line.setProposalId(proposalId);
                    line.setOiType(oiType);
                    line.setTermsCn(termCn);
                    line.setStatus("1");
                    line.setContractHId(Integer.parseInt(contractHId));

                    line.setReferenceStandard(lineOld.getReferenceStandard());
                    line.setRemark(lineOld.getRemark());
                    line.setClauseId(lineOld.getClauseId());
                    line.setCreatedBy(lineOld.getCreatedBy());
                    line.setCreationDate(lineOld.getCreationDate());
                    line.setFeeIntasOld(line.getFeeNotaxOld());
                    line.setLastUpdateDate(new Date());
                    line.setFeeNotaxOld(lineOld.getFeeNotaxOld());
                    line.setOrderNo(lineOld.getOrderNo());
                    line.setYTerms(lineOld.getYTerms());
                    line.setTermsSystemOld(lineOld.getTermsSystemOld());
                    line.setTermCategory(lineOld.getTermCategory());
                    line.setOperatorUserId(lineOld.getOperatorUserId());
                    line.setTermsSystemUp(lineOld.getTermsSystemUp());

                    line.setClauseId(0);

                    contractLineList.add(line);
                    }
            }


		}

        for(TtaContractLineEntity_HI entity:contractLineList) {
            JSONObject jsonObject = new JSONObject();
            JSONObject params = JSONObject.parseObject(jsonObject.toJSONString(entity));
            TtaContractLineEntity_HI instance = SaafToolUtils.setEntity(TtaContractLineEntity_HI.class,params, ttaContractLineDAO_HI, userId);
            ttaContractLineDAO_HI.saveOrUpdate(instance);
        }

		return contractLineList.get(0);
	}

    @Override
    public TtaContractLineEntity_HI submit(String contractId,String status, int userId) throws Exception {

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id",contractId);
        List<TtaContractHeaderEntity_HI> list = ttaContractHeaderLineDAO_HI.findByProperty(param);

        if(list.size()>0){
            TtaContractHeaderEntity_HI  header = list.get(0);
            header.setBillStatus(status);
            JSONObject jsonObject = new JSONObject();
            JSONObject params = JSONObject.parseObject(jsonObject.toJSONString(header));
            TtaContractHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaContractHeaderEntity_HI.class,params, ttaContractHeaderLineDAO_HI, userId);
            ttaContractHeaderLineDAO_HI.saveOrUpdate(instance);
        }
        return null;
    }

    //插入HeaderLine并返回HID
    public Integer AddHeaderLine(TtaContractHeaderEntity_HI ttaContractHeaderEntity_HI,int userId)throws Exception{
        JSONObject jsonObject = new JSONObject();
        JSONObject params = JSONObject.parseObject(jsonObject.toJSONString(ttaContractHeaderEntity_HI));
        TtaContractHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaContractHeaderEntity_HI.class,params, ttaContractHeaderLineDAO_HI, userId);
        ttaContractHeaderLineDAO_HI.saveOrUpdate(instance);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("contractCode",ttaContractHeaderEntity_HI.getContractCode());
       // param.put("proposalCode",ttaContractHeaderEntity_HI.getProposalCode());
        List<TtaContractHeaderEntity_HI> list = ttaContractHeaderLineDAO_HI.findByProperty(param);
        Integer contractHId = 0;
        if(list.size()>0){
            contractHId = list.get(0).getContractHId();
        }
        return contractHId;
    }

	@Override
	public JSONObject saveOrUpdate2(JSONObject paramsJSON, int userId) throws Exception {
		JSONArray ttaContractLineTable = paramsJSON.getJSONArray("ttaContractLineTable");
		List<TtaContractLineEntity_HI> arrayList = new  ArrayList<TtaContractLineEntity_HI>();
		JSONObject jsonObject = new JSONObject();
		for(int i = 0 ;i<ttaContractLineTable.size();i++){
			TtaContractLineEntity_HI instance = SaafToolUtils.setEntity(TtaContractLineEntity_HI.class, (JSONObject)ttaContractLineTable.get(i), ttaContractLineDAO_HI, userId);
			if(SaafToolUtils.isNullOrEmpty(instance.getRemark())){
                instance.setTermsSystem(instance.getTermsSystemUp()== null ?"":instance.getTermsSystemUp());
            }else{
                instance.setTermsSystem( (instance.getTermsSystemUp()== null ?"":instance.getTermsSystemUp()).concat("(").concat( (instance.getRemark() == null ?"":instance.getRemark())).concat(")"));
            }
			arrayList.add(instance);
		}
		ttaContractLineDAO_HI.saveOrUpdateAll(arrayList);
		jsonObject.put("ttaContractLineTable",arrayList);
		return jsonObject;
	}

    @Override
    public TtaContractLineEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        TtaContractLineEntity_HI instance = SaafToolUtils.setEntity(TtaContractLineEntity_HI.class, paramsJSON, ttaContractLineDAO_HI, userId);


        ttaContractLineDAO_HI.saveOrUpdate(instance);
        return instance;
    }

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaContractLineEntity_HI instance = ttaContractLineDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaContractLineDAO_HI.delete(instance);
	}

    @Override
    public void deleteRow(List<String> vendorCodeList, String contractHId) {
        String sql = "delete from tta_contract_line tcl where tcl.vendor_code in('" + String.join("','", vendorCodeList) + "') and tcl.contract_h_id=" + contractHId;
       ttaContractLineDAO_HI.executeSqlUpdate(sql);
    }



	@Override
	public TtaContractLineEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaContractLineEntity_HI_RO.TTA_ITEM_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.contract_L_Id", "contractLId", sql, paramsMap, "=");
		return (TtaContractLineEntity_HI_RO)ttaContractLineDAO_HI_RO.get(sql,paramsMap);
	}

    @Override
    public List<TtaContractLineEntity_HI_RO> findById(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaContractLineEntity_HI_RO.TTA_ITEM_LINE);

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "l.contract_H_Id", "contractHId", sql, paramsMap, "=");
        List<TtaContractLineEntity_HI_RO> conlist = (List<TtaContractLineEntity_HI_RO>)ttaContractLineDAO_HI_RO.findList(sql,paramsMap);
        return conlist;
    }

	@Override
	public void saveTtaTermsLToContract(JSONObject queryParamJSON,Integer userId) throws Exception{
		SaafToolUtils.validateJsonParms(queryParamJSON, "termsHId");
		StringBuffer sql = new StringBuffer();
		Map<String,Object> hashMap = new HashMap<String,Object>();
		sql.append(TtaContractLineEntity_HI_RO.TTA_ITERM_TO_CONTRACT);
		sql.append(" and tptl.terms_h_id =:termsHId");
		hashMap.put("termsHId", queryParamJSON.getInteger("termsHId"));
		List<TtaContractLineEntity_HI_RO> list = ttaContractLineDAO_HI_RO.findList(sql, hashMap);
		ArrayList<Object> objects = new ArrayList<>();
		for(TtaContractLineEntity_HI_RO v: list){
			TtaContractLineEntity_HI ttaContractLineEntity_hi = new TtaContractLineEntity_HI();
			SaafBeanUtils.copyUnionProperties(v, ttaContractLineEntity_hi);
			ttaContractLineEntity_hi.setOperatorUserId(userId);
			objects.add(ttaContractLineEntity_hi) ;
		}
		ttaContractLineDAO_HI.saveOrUpdateAll(objects);

		//hmb 2020.7.31注释
		//根据往年供应商 更新 PURCHASE，SALES，预估含税，预估不含税
/*        if ("1".equals(queryParamJSON.getString("isSplit"))) {
            ttaContractLineDAO_HI.executeSqlUpdate(TtaContractLineHEntity_HI_RO.UpdateSplitMoney(queryParamJSON.getInteger("oldProposalId"),queryParamJSON.getString("oldProposalYear"),queryParamJSON.getString("oldSplitStatus"),queryParamJSON.getString("oldSupplierCode"))) ;
            ttaContractLineDAO_HI.executeSqlUpdate(TtaContractLineHEntity_HI_RO.UpdateFcs(userId,queryParamJSON.getInteger("oldProposalId"),queryParamJSON.getBigDecimal("oldFcsSplitPurchse"),queryParamJSON.getBigDecimal("oldFcsSplitSales"))) ;
        }*/
	}

	@Override
	public List<TtaContractLineEntity_HI_RO> findTtaSummaryById(JSONObject queryParamJSON,Integer userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(TtaContractLineEntity_HI_RO.TTA_SUMMARY_LIST);
		sql.append(" and  tcl.contract_h_id is null");
		sql.append(" and  tcl.proposal_Id =:proposalId");
		paramsMap.put("proposalId", queryParamJSON.getString("proposalId")) ;
		sql.append("  order   by  FOR_STR_RPAD(order_No)") ;
		return  ttaContractLineDAO_HI_RO.findList(sql, paramsMap);
	}

}
