package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.api.ConfigModel;
import com.sie.watsons.base.api.model.inter.IPlmSupProduct;
import com.sie.watsons.base.plmBase.model.dao.PlmBrandMapDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmMotherCompanyEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBrandMapEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBrandMap;
import com.sie.watsons.base.productEco.config.ProductEcoEnum;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sie.saaf.common.model.inter.server.FastdfsServer.getTimeStr;

@Component("plmBrandMapServer")
public class PlmBrandMapServer extends BaseCommonServer<PlmBrandMapEntity_HI>
		implements IPlmBrandMap {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBrandMapServer.class);

    @Autowired
    protected HibernateTemplate hibernateTemplete;

    @Autowired
    protected SessionFactory sessionFactory;

	@Autowired
	private ViewObject<PlmBrandMapEntity_HI> plmBrandMapDAO_HI;
	@Autowired
	private BaseViewObject<PlmBrandMapEntity_HI_RO> plmBrandMapEntity_HI_RO;
	@Autowired
	@Lazy
	private IPlmSupProduct plmSupProductServer;

	@Autowired
	private  PlmBrandMapDAO_HI plmBrandMap;

	public PlmBrandMapServer() {
		super();
	}

	@Override
	public Pagination<PlmBrandMapEntity_HI_RO> findBrandMapByCondition(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmBrandMapEntity_HI_RO.querySql);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		String brandCnUdaValueDesc = queryParamJSON
				.getString("brandCnUdaValueDesc_fulllike");
		String brandEnUdaValueDesc = queryParamJSON
				.getString("brandEnUdaValueDesc_fulllike");
		String billStatus = queryParamJSON.getString("billStatus");
		if (!"".equals(brandCnUdaValueDesc) && brandCnUdaValueDesc != null) {
			sql.append(" and pbm.brand_cn_uda_value_desc like '%"
					+ brandCnUdaValueDesc + "%' ");
			queryParamJSON.remove("brandCnUdaValueDesc_fulllike");
		}
		if (!"".equals(brandEnUdaValueDesc) && brandEnUdaValueDesc != null) {
			sql.append(" and upper(pbm.brand_en_uda_value_desc) like '%"
					+ brandEnUdaValueDesc.toUpperCase() + "%' ");
			queryParamJSON.remove("brandEnUdaValueDesc_fulllike");
		}
		if (!"".equals(billStatus) && billStatus != null) {
			sql.append(" and pbm.bill_status = '" + billStatus + "' ");
			queryParamJSON.remove("billStatus");
		}
		SaafToolUtils.parperHbmParam(PlmBrandMapEntity_HI.class,
				queryParamJSON, sql, queryParamMap);

		sql.append(" order by pbm.brand_map_id desc ");
		Pagination<PlmBrandMapEntity_HI_RO> findListResult = plmBrandMapEntity_HI_RO
				.findPagination(sql, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	@Override
	public String deletedByCondition(JSONObject queryParamJSON) {
		JSONArray ids = queryParamJSON.getJSONArray("seqIds");
		String sql = "delete from plm_brand_map where brand_map_id in (''";
		StringBuilder sb = new StringBuilder(sql);
		if (CollectionUtils.isNotEmpty(ids)) {
			Map<String, Object> paramsMap = new HashMap<>();
			List<Integer> seqIds = ids.toJavaList(Integer.class);
			if (CollectionUtils.isNotEmpty(seqIds)) {
				paramsMap.put("seqIds", seqIds);
				for (Integer id : seqIds) {
					sql = sql + "," + id;
					sb.append("," + id);
				}
			}
			sql = sql + ")";
			sb.append(")");
			plmBrandMapDAO_HI.executeSql(sb.toString());
		}
		return "S";
	}

	@Override
	public String saveBrandMap(JSONObject queryParamJSON) throws Exception {
		PlmBrandMapEntity_HI plmProductBpmUserEntity = JSON.parseObject(
				queryParamJSON.toString(), PlmBrandMapEntity_HI.class);
		plmProductBpmUserEntity.setBillStatus(ProductEcoEnum.UPD_MAKING
				.getValues());
		plmProductBpmUserEntity.setBillStatusName(ProductEcoEnum.UPD_MAKING
				.getMeaning());
		plmProductBpmUserEntity.setCreator(queryParamJSON
				.getString("varEmployeeNumber"));
		plmBrandMapDAO_HI.saveOrUpdate(plmProductBpmUserEntity);
		return "S";
	}

	@Override
	public PlmBrandMapEntity_HI_RO findBrandMapDetail(JSONObject queryParamJSON)
			throws Exception {
		StringBuffer sql = new StringBuffer(PlmBrandMapEntity_HI_RO.querySql);
		Integer brandMapId = queryParamJSON.getInteger("brandMapId");
		sql.append(" and pbm.brand_map_id='" + brandMapId + "'");

		List<PlmBrandMapEntity_HI_RO> ros = plmBrandMapEntity_HI_RO
				.findList(sql.toString());
		if (CollectionUtils.isNotEmpty(ros)) {
			return ros.get(0);
		} else {
			throw new Exception("上传的主键ID没查到数据");
		}

	}

  @Autowired
  private ConfigModel configModel;

	@Override
	public String brandToCsv(Integer brandMapId) throws Exception {

		PlmBrandMapEntity_HI brandMap = plmBrandMapDAO_HI.getById(brandMapId);
		String timeStr = getTimeStr();
		String file = "E:\\work\\quc_file\\WTCCN_SP_RMS_PRODUCT_NEWBRAND_"
				+ timeStr + ".csv";
		String osName = System.getProperties().getProperty("os.name");
		if (osName.equals("Linux")) {
			file = "WTCCN_SP_RMS_PRODUCT_NEWBRAND_" + timeStr + ".csv";
		}
		if (ObjectUtils.isEmpty(brandMap)) {
			throw new Exception("查询数据为空！");
		}

		String[] HEADERS = new String[] { "ACTION_TYPE", "BRAND_TYPE",
				"BRAND_DESC" };
		FileOutputStream fos = new FileOutputStream(new File(file));
		OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.POSTGRESQL_CSV
				.withHeader(HEADERS).withDelimiter(','))) {
			printer.printRecord("NEW", "E", brandMap.getBrandEnUdaValueDesc());
			printer.printRecord("NEW", "C", brandMap.getBrandCnUdaValueDesc());
		}
		out.close();
//		plmSupProductServer.uploadFile("wtccnit", "wtccnit123",
//				"10.32.152.148", file,
//				"/usr01/ifiles/hbcnbit/inbox/itmmst_inbox",22,"sftp");
    plmSupProductServer.uploadFile(configModel.getUser(), configModel.getPossword(), configModel.getHost(), file,
      configModel.getPath(),22,configModel.getOpen());

		File fileP = new File(file);
		fileP.delete();

		return "S";
	}

	@Override
	public String syncCnEnBrand() throws Exception {

		Map<String, Object> map = plmBrandMap.callPkgSyncCnEnBrand();

		return map.toString();
	}

	@Override
    public PlmBrandMapEntity_HI findBybrandOrCompany(String cname, String ename, Integer motherCompanyId,
							 				Integer groupbrandId, Integer brandMapId)
    {
    	return this.findBybrandOrCompany(cname, ename, motherCompanyId, groupbrandId, brandMapId, null);
    }

    @Override
	public PlmBrandMapEntity_HI findBybrandOrCompany(String cname, String ename, Integer motherCompanyId, Integer groupbrandId,
											  Integer brandMapId, List<String> status)
	{
		Criteria criteria = hibernateTemplete.getSessionFactory().getCurrentSession()
				.createCriteria(PlmBrandMapEntity_HI.class);

		criteria.add(Restrictions.eq("brandCnUdaValueDesc",cname))
				.add(Restrictions.eq("brandEnUdaValueDesc",ename));
		if(null != groupbrandId) {
			criteria.add(Restrictions.eq("groupbrandId",groupbrandId));
		}
		if(null != motherCompanyId) {
			criteria.add(Restrictions.eq("motherCompanyId",motherCompanyId));
		}

		if(null != brandMapId) {
			criteria.add(Restrictions.ne("brandMapId",brandMapId));
		}

		if(null != status && CollectionUtils.isNotEmpty(status)) {
			criteria.add(Restrictions.in("billStatus",status));
		}
		List<PlmBrandMapEntity_HI> list = criteria.list();
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	public void initMotherCompanyIdForMC() {
		List<PlmBrandMapEntity_HI> list = hibernateTemplete.getSessionFactory().getCurrentSession()
				.createCriteria(PlmBrandMapEntity_HI.class).add(Restrictions.isNotNull("plmMotherCompany"))
				.add(Restrictions.isNull("motherCompanyId")).list();
		if(CollectionUtils.isNotEmpty(list)) {
			for(PlmBrandMapEntity_HI map:list) {
				if (StringUtils.isNotBlank(map.getPlmMotherCompany())) {
					PlmMotherCompanyEntity_HI company = (PlmMotherCompanyEntity_HI) hibernateTemplete.getSessionFactory().getCurrentSession()
							.createCriteria(PlmMotherCompanyEntity_HI.class)
							.add(Restrictions.eq("plmMotherCompanyName",map.getPlmMotherCompany()))
							.setMaxResults(1).uniqueResult();

					if(null != company) {
						map.setMotherCompanyId(company.getPlmMotherCompanyId());
						this.saveOrUpdate(map);
					}
				}
			}
		}
	}
}
