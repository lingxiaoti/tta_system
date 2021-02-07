package com.sie.watsons.base.proposal.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.model.dao.TtaBrandplnLDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnHEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnLEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaTermsHEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnLEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnLEntity_HI_RO_Model;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandplnH;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandplnL;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.util.*;

@Component("ttaBrandplnLServer")
public class TtaBrandplnLServer extends BaseCommonServer<TtaBrandplnLEntity_HI> implements ITtaBrandplnL {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandplnLServer.class);
    @Autowired
    private ViewObject<TtaBrandplnLEntity_HI> ttaBrandplnLDAO_HI;
    @Autowired
    private BaseViewObject<TtaBrandplnLEntity_HI_RO> ttaBrandplnLDAO_HI_RO;
    @Autowired
    private DynamicBaseViewObject commonDAO_HI_DY;
    @Autowired
    private TtaBrandplnLDAO_HI ttaBrandplnLDAO;
    @Autowired
    private ViewObject<TtaBrandplnHEntity_HI> ttaBrandplnHDAO_HI;
    @Autowired
    private BaseViewObject<TtaBrandplnHEntity_HI_RO> ttaBrandplnHDAO_HI_RO;
    @Autowired
    private BaseCommonDAO_HI<TtaBrandplnLEntity_HI> ttaBrandplnLEntityHiBaseCommonDAOHi;
    @Autowired
    private ITtaBrandplnH ttaBrandplnHServer;
    @Autowired
    private ViewObject<TtaTermsHEntity_HI> ttaTermsHDAO_HI;

    public TtaBrandplnLServer() {
        super();
    }


    @Override
    public Pagination<TtaBrandplnLEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaBrandplnLEntity_HI_RO.TTA_ITEM_V);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "v.brandpln_H_Id", "brandplnHId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.brandpln_L_Id", "brandplnLId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.PROPOSAL_ID", "proposalId", sql, paramsMap, "=");

        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.brandpln_L_Id asc", false);
        Pagination<TtaBrandplnLEntity_HI_RO> findList = ttaBrandplnLDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public List<TtaBrandplnLEntity_HI> callsaveOrUpdate(JSONObject queryJSONParam, int userId) throws Exception {

        //获取品牌明细列表的数组
        JSONArray paramsArray = queryJSONParam.getJSONArray("brandPlnLDataTable");
        JSONObject project = queryJSONParam.getJSONObject("project");//proposal单据的参数
        JSONObject brandparams = queryJSONParam.getJSONObject("brandparams");//头信息

        //比较品牌是否重复
        for (int m = 0; m < paramsArray.size(); m++) {
            JSONObject jsonObjectm = paramsArray.getJSONObject(m);
            //String brandDetails = jsonObjectm.getString("brandDetails");
            String groupDesc = jsonObjectm.getString("groupDesc");
            String deptDesc = jsonObjectm.getString("deptDesc");
            String deptCodeCnm = jsonObjectm.getString("deptCode");
            String brandCnm = jsonObjectm.getString("brandCn");
            String brandEnm = jsonObjectm.getString("brandEn");

            for (int n = m + 1; n < paramsArray.size(); n++) {
                JSONObject jsonObjectn = paramsArray.getJSONObject(n);
                String brandCnn = jsonObjectn.getString("brandCn");
                String groupDescn = jsonObjectn.getString("groupDesc");
                String deptDescn = jsonObjectn.getString("deptDesc");
                String deptCoden = jsonObjectn.getString("deptCode");
                String brandEnn = jsonObjectn.getString("brandEn");

                if ((groupDesc != null && groupDesc.equals(groupDescn)) && (brandCnm != null && brandCnm.equals(brandCnn))&&
                        (deptDesc != null && deptDesc.equals(deptDescn)) && (brandEnm !=null && brandEnm.equals(brandEnn)) &&
                        (deptCodeCnm != null && deptCodeCnm.equals(deptCoden))) {
                    throw new IllegalArgumentException("品牌[" + brandCnm + "]重复!请修改!");
                }
            }

        }

        List<TtaBrandplnLEntity_HI> entity_hiList = new ArrayList<>();
		for(int i=0;i<paramsArray.size();i++){
			JSONObject jsonObject = paramsArray.getJSONObject(i);

            String newOrExisting = brandparams.getString("newOrExisting");
            String brandDetails = jsonObject.getString("brandDetails");
            if ("New_Vendor".equals(newOrExisting)) {//新的供应商
                SaafToolUtils.validateJsonParms(jsonObject, "groupCode", "deptCode", "brandCn","gp","brandEn","fcsPurchase","fcsSales");
            }else {
                //验证是否为空,如为空,给前台提示
                if("Existing_Brand".equals(brandDetails)) {
                    SaafToolUtils.validateJsonParms(jsonObject, "groupCode", "deptCode", "brandCn", "purchaseGrowth","salesGrowth","gp","brandEn");
                }else {
                    SaafToolUtils.validateJsonParms(jsonObject, "groupCode", "deptCode", "brandCn","gp","brandEn");
                }

            }
			//保存品牌计划明细数据
			TtaBrandplnLEntity_HI instance = SaafToolUtils.setEntity(TtaBrandplnLEntity_HI.class, jsonObject, ttaBrandplnLDAO_HI, userId);
			//如果存在供应商并且是拉取的数据
			if (null != instance.getBrandplnHId() && "Existing_Brand".equals(instance.getBrandDetails())) {
                //instance.setFcsSales();
            }

            instance.setProposalId(project.getInteger("proposalId"));
			//ttaBrandplnLDAO_HI.saveOrUpdate(instance);
            entity_hiList.add(instance);
		}

        //保存品牌计划头信息
        TtaBrandplnHEntity_HI hEntity_hi = SaafToolUtils.setEntity(TtaBrandplnHEntity_HI.class, brandparams, ttaBrandplnHDAO_HI, userId);
        ttaBrandplnHDAO_HI.saveOrUpdate(hEntity_hi);
        ttaBrandplnLDAO_HI.saveOrUpdateAll(entity_hiList);
        //品牌计划填写了调整gp,并且TTA TERMS上的数据存在时,才去更新GP%
        if(null != hEntity_hi.getAdjustGp()) {
            List<TtaTermsHEntity_HI> termsHDAOHiByProperty = ttaTermsHDAO_HI.findByProperty(new JSONObject().fluentPut("proposalId", project.getInteger("proposalId")));
            if (null != termsHDAOHiByProperty && !termsHDAOHiByProperty.isEmpty()) {
                TtaTermsHEntity_HI termsHEntityHi = termsHDAOHiByProperty.get(0);
                termsHEntityHi.setGp(hEntity_hi.getAdjustGp());
                ttaTermsHDAO_HI.saveOrUpdate(termsHEntityHi);
            }
        }
        return entity_hiList;
    }

    @Override
    public void delete(Integer pkId) {
        if (pkId == null) {
            return;
        }
        TtaBrandplnLEntity_HI instance = ttaBrandplnLDAO_HI.getById(pkId);
        if (instance == null) {
            return;
        }
        ttaBrandplnLDAO_HI.delete(instance);
    }

    @Override
    public void deleteClear(Integer pkId) {
        if (pkId == null) {
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("brandplnHId", pkId);
        List<TtaBrandplnLEntity_HI> list = ttaBrandplnLDAO_HI.findByProperty(map);
        if (list == null) {
            return;
        }
        ttaBrandplnLDAO_HI.delete(list);
    }


    @Override
    public TtaBrandplnLEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaBrandplnLEntity_HI_RO.TTA_ITEM_V);

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "v.brandpln_H_Id", "brandplnHId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.brandpln_L_Id", "brandplnLId", sql, paramsMap, "=");
        return (TtaBrandplnLEntity_HI_RO) ttaBrandplnLDAO_HI_RO.get(sql, paramsMap);
    }

    /**
     *
     * @param vendorNbr 供应商编号
     * @param purchType 采购模式
     * @param integer 月份
     * @param deleteSymbol 删除标识
     * @throws Exception
     */
    @Override
    public void insertBrandPlanData(String vendorNbr, String purchType, Integer integer,String deleteSymbol) throws Exception{
        LOGGER.info("品牌计划表插入数据到临时表所需的参数,供应商: {},采购模式:{},月份:{}",vendorNbr,purchType,integer);
        String tableName = "tta_sale_sum_" + integer;
        List list = commonDAO_HI_DY.findList("select * from user_tables where table_name =upper('" + tableName + "')");
        if (list == null || list.isEmpty()) {
            return;
        }
        String sb = "insert into tta_collect_sales_temp\n" +
                "   (tran_date, item_nbr, vendor_nbr, purch_type, sales_amt, po_amt, gp_amt, gp_supplier_popt_amt,cost,group_code, group_desc, dept_code, dept_desc, brand_code, brand_cn, brand_en,delete_symbol,split_supplier_code," +
                "sales_exclude_amt)\n" +
                "   select\n" +
                "        t.tran_date,\n" +
                "        t.item_nbr,\n" +
                "        t.vendor_nbr,\n" +
                "        t.purch_type, \n" +
                "        t.total_sales_amt as sales_amt, --每个月的含税销售总额\n" +
                "        t.total_po_amt as po_amt,--每个月的采购成本\n" +
                "        t.gp_amt,  \n" +
                "        t.total_gp_supplier_popt_amt as gp_supplier_popt_amt,--每个月的不含税销售毛利额   \n" +
                "        t.cost, \n" +
                "        t.group_code,\n" +
                "        t.group_desc,\n" +
                "        t.dept_code,\n" +
                "        t.dept_desc,\n" +
                "        t.brand_code,\n" +
                "        t.brand_cn,\n" +
                "        t.brand_en,\n" +
                "        t.delete_symbol as delete_symbol,\n" +
                "        '" + vendorNbr + "' as split_supplier_code,\n" +
                "        t.total_sales_exclude_amt as sales_exclude_amt --每个月的不含税销售额\n" +
                "         from (select tssl.tran_date,\n" +
                "                      tssl.item_nbr,\n" +
                "                      tssl.vendor_nbr,\n" +
                "                      tssl.split_supplier_code,\n" +
                "                      tssl.purch_type, -- 采购模式,\\n\" +\n" +
                "                      tssl.sales_amt,\n" +
                "                      tssl.po_amt,\n" +
                "                      tssl.gp_supplier_popt_amt,\n" +
                "                      tssl.gp_amt, --每个月份最小交易的金额,这里涉及的金额都是每个月最小交易的         \n" +
                "                      --tssl.cost, --CVM,CONSIGN模式采购成本\\n\" +\n" +
                "                      ti.group_code,\n" +
                "                      ti.group_desc,\n" +
                "                      ti.dept_code,\n" +
                "                      ti.dept_desc,\n" +
                "                      ti.brand_code,\n" +
                "                      ti.brand_cn,\n" +
                "                      ti.brand_en,\n" +
                "                      '" + deleteSymbol + "' as delete_symbol,\n" +
                "                      sum(tssl.po_amt) over(partition by ti.group_code, ti.dept_code, ti.brand_cn, ti.brand_en) as total_po_amt, --每个月采购成本 不含税\n" +
                "                      sum(tssl.cost) over(partition by ti.group_code, ti.dept_code, ti.brand_cn, ti.brand_en) as cost, --每个月采购成本 不含税\n" +
                "                      sum(tssl.gp_supplier_popt_amt) over(partition by ti.group_code, ti.dept_code, ti.brand_cn, ti.brand_en) as total_gp_supplier_popt_amt, --每个月销售毛利额\\n\" +\n" +
                "                      sum(tssl.sales_amt) over(partition by ti.group_code, ti.dept_code, ti.brand_cn, ti.brand_en) AS total_sales_amt, --每个月含税销售总额\\n\" +\n" +
                "                      sum(tssl.sales_exclude_amt) over(partition by ti.group_code, ti.dept_code, ti.brand_cn, ti.brand_en) AS total_sales_exclude_amt, --每个月不含税销售总额\\n\" +\n" +
                "                      row_number() over(partition by ti.group_code, ti.dept_code, ti.brand_cn, ti.brand_en order by tssl.tran_date) flag\n" +
                "                 from tta_sale_sum_" + integer + " tssl\n" +
                "                 left join (select *\n" +
                "                             from (SELECT t1.item_nbr,\n" +
                "                                          t1.group_code,\n" +
                "                                          t1.group_desc,\n" +
                "                                          t1.dept_code,\n" +
                "                                          t1.dept_desc,\n" +
                "                                          t1.brand_code,\n" +
                "                                          t1.brand_cn,\n" +
                "                                          t1.brand_en,\n" +
                "                                          ROW_NUMBER() OVER(PARTITION BY t1.item_nbr ORDER BY t1.last_update_date desc) row_id\n" +
                "                                     FROM tta_item t1\n" +
                "                                    order by t1.last_update_date desc) t2\n" +
                "                            where t2.row_id = 1) ti\n" +
                "                   on tssl.item_nbr = ti.item_nbr\n" +
                "                where  tssl.split_supplier_code in(\n" +
                TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorNbr) +
                " )) t\n" +
                " where t.flag = 1 ";
        LOGGER.info("Sales销售数据汇总表:{},插入到tta_collect_sales_temp的sql:{}","tta_sale_sum_"+integer,sb);
        int count = commonDAO_HI_DY.executeUpdate(sb);
        LOGGER.info("当前线程["+ Thread.currentThread().getName() +"]插入数据到表:tta_collect_sales_temp;插入条数:{}",count);
    }

    /**
     * 获取品牌明细数据
     * @param proposalId
     * @return
     */
    public List<TtaBrandplnLEntity_HI_RO> findBrandplnDataByProposalSplit(Integer proposalId) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaBrandplnLEntity_HI_RO.TTA_ITEM_V);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        JSONObject queryParamJSON = new JSONObject();
        queryParamJSON.put("proposalId",proposalId);
        SaafToolUtils.parperParam(queryParamJSON, "v.proposal_id", "proposalId", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.brandpln_L_Id asc", false);
        List<TtaBrandplnLEntity_HI_RO>   findList = ttaBrandplnLDAO_HI_RO.findList(sql,paramsMap);
        return findList;
    }

    /**
     * 查询品牌汇总数据和proposal单号,proposalId
     * @param ttaProposalHeaderEntity_hi_ro
     * @return
     */
    public JSONObject findBrandTotalAndProposalCode(TtaProposalHeaderEntity_HI_RO ttaProposalHeaderEntity_hi_ro){
        JSONObject result = new JSONObject();
        Integer proposalId = ttaProposalHeaderEntity_hi_ro.getProposalId();

        String sql = "select sum(nvl(tbl.fcs_purchase,0)) fcs_purchase,sum(nvl(tbl.fcs_sales,0)) fcs_sales from tta_brandpln_line tbl  where tbl.proposal_id = '"+proposalId+"'";
        List<Map<String, Object>> mapList = ttaBrandplnLDAO.queryNamedSQLForList(sql, new HashMap<>());
        List<TtaBrandplnLEntity_HI_RO> dataByProposalSplit = findBrandplnDataByProposalSplit(proposalId);
        Map<String, Object> map = null;
        if (mapList != null && mapList.size() > 0) {
             map = mapList.get(0);
             map.put("proposalId",proposalId);
             map.put("proposalCode",ttaProposalHeaderEntity_hi_ro.getOrderNbr());
             map.put("vendorNbr",ttaProposalHeaderEntity_hi_ro.getVendorNbr());
        }

        result.put("brandHeader",map);
        result.put("brandLine",dataByProposalSplit);
        return result;
    }

        @Override
    public int saveImportTtaBrandlpn(Integer proposalId,Integer userId,MultipartFile file) throws Exception {
        TtaBrandplnHEntity_HI_RO entityHiRo = ttaBrandplnHDAO_HI_RO.get(TtaBrandplnHEntity_HI_RO.getBrandplnHSQL(proposalId), new HashMap<>());
        Map<String,Object> brandParamMap = new HashMap<>();
        brandParamMap.put("proposalId",proposalId);
        List<TtaBrandplnLEntity_HI_RO> hiRoList = ttaBrandplnLDAO_HI_RO.findList(TtaBrandplnLEntity_HI_RO.getBrandplnDetailSql(), brandParamMap);
        Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaBrandplnLEntity_HI_RO_Model.class,0);
        List<Map<String, Object>> datas = (List<Map<String, Object>>)result.get("datas");
        //List<TtaBrandplnLEntity_HI_RO_Model> models = JSONArray.parseArray(JSONArray.toJSONString(datas), TtaBrandplnLEntity_HI_RO_Model.class);
        List<Map<String,Object>> updateList = new ArrayList<>();
        List<TtaBrandplnLEntity_HI> insertList = new ArrayList<>();
        StringBuffer sbf = new StringBuffer();
        if (null != datas &&  datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                TtaBrandplnLEntity_HI_RO_Model object = JSON.parseObject(JSONObject.toJSONString(datas.get(i)), TtaBrandplnLEntity_HI_RO_Model.class);
                StringBuffer sb = new StringBuffer();
                isNullByImport(object,sb);
                boolean isUpdate = false;
                for (int j = 0; j < hiRoList.size(); j++) {
                    TtaBrandplnLEntity_HI_RO hiRo = hiRoList.get(j);
                    if (!SaafToolUtils.isNullOrEmpty(object.getGroupCode()) && !SaafToolUtils.isNullOrEmpty(object.getGroupDesc())
                        && !SaafToolUtils.isNullOrEmpty(object.getDeptCode()) && !SaafToolUtils.isNullOrEmpty(object.getDeptDesc())
                        && !SaafToolUtils.isNullOrEmpty(object.getBrandCn()) && !SaafToolUtils.isNullOrEmpty(object.getBrandEn())) {
                        if (object.getGroupCode().trim().equals(hiRo.getGroupCode()) && object.getGroupDesc().trim().equals(hiRo.getGroupDesc()) &&
                                object.getDeptCode().trim().equals(hiRo.getDeptCode()) && object.getDeptDesc().trim().equals(hiRo.getDeptDesc()) &&
                                object.getBrandCn().trim().equals(hiRo.getBrandCn()) && object.getBrandEn().trim().equals(hiRo.getBrandEn())){
                            isUpdate = true;
                            datas.get(i).put("LAST_UPDATED_BY",userId);
                            datas.get(i).put("LAST_UPDATE_LOGIN",userId);
                            datas.get(i).put("LAST_UPDATE_DATE", new Date());
                            datas.get(i).put("proposal_id",proposalId);
                            datas.get(i).put("brandpln_l_id",hiRo.getBrandplnLId());
                            updateList.add(datas.get(i));
                            break;
                        }
                    }
                }
                if (!isUpdate) {
                    //新增品牌不能有po_record,sales等金额
                    this.hasDataByPurcahseAndSale(object,sb);
                    TtaBrandplnLEntity_HI target = new TtaBrandplnLEntity_HI();
                    SaafBeanUtils.copyUnionProperties(object,target);
                    target.setPoRecord(BigDecimal.ZERO);
                    target.setSales(BigDecimal.ZERO);
                    target.setActualGp(BigDecimal.ZERO);
                    target.setAnnualPurchase(BigDecimal.ZERO);
                    target.setAnnualSales(BigDecimal.ZERO);
                    target.setCreatedBy(userId);
                    target.setCreationDate(new Date());
                    target.setLastUpdatedBy(userId);
                    target.setLastUpdateDate(new Date());
                    target.setLastUpdateLogin(userId);
                    target.setProposalId(proposalId);
                    target.setBrandplnHId(entityHiRo.getBrandplnHId());
                    target.setBrandDetails("New_Brand");
                    insertList.add(target);
                }

                if (sb.length() != 0) {
                    sb.insert(0,"当前第" + (i + 2) + "行,");
/*                    if (sb.lastIndexOf(",") > 0) {
                        String substring = sb.substring(0,sb.lastIndexOf(","));
                        System.out.println(substring);
                        sb = new StringBuffer(substring);
                    }*/
                    sb.append("</br>");
                    sbf.append(sb);
                }

            }
        }

        if (sbf.length() > 0){
            throw new Exception(sbf.toString());
        }
        if (updateList.size() < hiRoList.size() || updateList.size() > hiRoList.size()) {
            throw new IllegalArgumentException("当前需要更新的品牌与原导出的品牌的数量不相等,不包含手动新增的品牌,请检查!");
        }
        ttaBrandplnLEntityHiBaseCommonDAOHi.updateBatchJDBC("tta_brandpln_line",TtaBrandplnLEntity_HI_RO_Model.class,updateList);
        if (!insertList.isEmpty()){
            ttaBrandplnLDAO_HI.saveOrUpdateAll(insertList);
        }
        ttaBrandplnHServer.brandplnHUpdate(new JSONObject().fluentPut("brandplnHId",entityHiRo.getBrandplnHId()),userId);
        return datas.size();
    }

    private void isNullByImport(TtaBrandplnLEntity_HI_RO_Model model,StringBuffer sb){
        List<String> groupList = new ArrayList<>();
        if (SaafToolUtils.isNullOrEmpty(model.getGroupCode()))
            groupList.add("Group Code");
        if (SaafToolUtils.isNullOrEmpty(model.getGroupDesc()))
            groupList.add("Group Desc");
        if (SaafToolUtils.isNullOrEmpty(model.getDeptCode()))
            groupList.add("Dept Code");
        if (SaafToolUtils.isNullOrEmpty(model.getDeptDesc()))
            groupList.add("DEPT_DESC");
        if (SaafToolUtils.isNullOrEmpty(model.getBrandCn()))
            groupList.add("BRAND_CN");
        if(SaafToolUtils.isNullOrEmpty(model.getBrandEn())){
            groupList.add("BRAND_EN");
        }
        if (groupList.size() > 0) {
            sb.append("导入的列").append("【").append(StringUtils.join(groupList,",")).append("】").append("不能为空;");
        }
    }

    private void hasDataByPurcahseAndSale(TtaBrandplnLEntity_HI_RO_Model model,StringBuffer stringBuffer){
        List<String> strList = new ArrayList<>();
        if (!SaafToolUtils.isNullOrEmpty(model.getPoRecord())){
            strList.add("Purchase （PO Record）");
        }
        if (!SaafToolUtils.isNullOrEmpty(model.getSales())){
            strList.add("Sales");
        }
        if (!SaafToolUtils.isNullOrEmpty(model.getActualGp())){
            strList.add("Actual GP%");
        }
        if (!SaafToolUtils.isNullOrEmpty(model.getAnnualPurchase())) {
            strList.add("Annual Purchase");
        }
        if (!SaafToolUtils.isNullOrEmpty(model.getAnnualSales())) {
            strList.add("Annual Sales");
        }
        if (strList.size() > 0){//
            stringBuffer.append("新增品牌时,").append("列【").append(StringUtils.join(strList,","))
                    .append("】").append("不需要填写数值");
        }
    }
}
