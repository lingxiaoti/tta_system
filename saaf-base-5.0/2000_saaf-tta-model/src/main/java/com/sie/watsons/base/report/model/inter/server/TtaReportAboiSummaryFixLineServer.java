package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAboiSummaryFixLineEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaReportAboiSummary;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaReportAboiSummaryFixLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaReportAboiSummaryFixLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaReportAboiSummaryFixLineServer")
public class TtaReportAboiSummaryFixLineServer extends BaseCommonServer<TtaReportAboiSummaryFixLineEntity_HI> implements ITtaReportAboiSummaryFixLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportAboiSummaryFixLineServer.class);

	@Autowired
	private ViewObject<TtaReportAboiSummaryFixLineEntity_HI> ttaReportAboiSummaryFixLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaReportAboiSummaryFixLineEntity_HI_RO> ttaReportAboiSummaryFixLineDAO_HI_RO;

	@Autowired
	private ITtaReportAboiSummary ttaReportAboiSummaryServer;

	public TtaReportAboiSummaryFixLineServer() {
		super();
	}

	@Override
	public void saveOrUpdateInfo(JSONObject json, Integer userId) throws Exception {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String time = dateFormat.format(date);
		String year  = json.getString("reportYear");
		//更新 tta_proposal_header 已经插入
		ttaReportAboiSummaryFixLineDAO_HI.executeSqlUpdate(TtaReportAboiSummaryFixLineEntity_HI_RO.getInsertReportBaseTtaProposalStatus()) ;

		//插入预估基础数据
		ttaReportAboiSummaryFixLineDAO_HI.executeSqlUpdate(TtaReportAboiSummaryFixLineEntity_HI_RO.getInsertReportBase(time,userId)) ;

		//插入实际基础数据(预估不包含)
		ttaReportAboiSummaryFixLineDAO_HI.executeSqlUpdate(TtaReportAboiSummaryFixLineEntity_HI_RO.getInsertReportBaseInfoTwo(time,userId,year.substring(0,4))) ;
		//插入条款
		ttaReportAboiSummaryFixLineDAO_HI.executeSqlUpdate(TtaReportAboiSummaryFixLineEntity_HI_RO.getInsertReportBaseInfoTerms(time,userId)) ;
		//更新条款关联ID
		ttaReportAboiSummaryFixLineDAO_HI.executeSqlUpdate(TtaReportAboiSummaryFixLineEntity_HI_RO.getInsertReportBaseInfoTermsUpdate(time,userId)) ;

		//更新 tta_proposal_header 已经插入
		ttaReportAboiSummaryFixLineDAO_HI.executeSqlUpdate(TtaReportAboiSummaryFixLineEntity_HI_RO.getInsertReportBaseTtaProposalStatusEnd()) ;

	}

	@Override
	public List<TtaReportAboiSummaryFixLineEntity_HI_RO> findTermsList(JSONObject jsonObject) {
		StringBuffer sql = new StringBuffer();
		String year  = jsonObject.getString("reportYear");
		sql.append(TtaReportAboiSummaryFixLineEntity_HI_RO.getTermsCn(year.substring(0,4)));
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		List<TtaReportAboiSummaryFixLineEntity_HI_RO>   findList = ttaReportAboiSummaryFixLineDAO_HI_RO.findList(sql,paramsMap);
		return findList;
	}

	@Override
	public JSONObject findPagination(JSONObject queryParamJSON, LinkedBlockingQueue<JSONObject> queue, Integer pageIndex, Integer pageRows) throws Exception {
		boolean result = queue.offer(queryParamJSON);
		JSONObject resultJsonObject = new JSONObject();
		if (result) {
			saveOrUpdateInfo(queryParamJSON,queryParamJSON.getInteger("varUserId"));
		}
		List<TtaReportAboiSummaryFixLineEntity_HI_RO> termsList = findTermsList(queryParamJSON);
		StringBuffer sb = new StringBuffer(TtaReportAboiSummaryFixLineEntity_HI_RO.QUERY);
		String year  = queryParamJSON.getString("reportYear");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("year",Integer.valueOf(year.substring(0,4)));
		SaafToolUtils.parperHbmParam(TtaReportAboiSummaryFixLineEntity_HI_RO.class, queryParamJSON, "tras.vendor_Nbr", "vendorNbr", sb, paramsMap, "fulllike");
		StringBuffer countQueryString = SaafToolUtils.getSimpleSqlCountString(sb, "count(*)");
		Pagination findPages = ttaReportAboiSummaryFixLineDAO_HI_RO.findPagination(sb, countQueryString, paramsMap, pageIndex, pageRows);
		List<TtaReportAboiSummaryFixLineEntity_HI_RO> data = findPages.getData();
		JSONArray objects1 = new JSONArray();
		int i = 0 ;
		for (TtaReportAboiSummaryFixLineEntity_HI_RO datum : data) {
			objects1.add(JSONObject.parseObject(JSON.toJSONString(datum)));
			//预计
			List<Map<String, String>> objects = new ArrayList<>();
			String val = datum.getVal();
			Map<String, String> unitMap = null;
			if (!SaafToolUtils.isNullOrEmpty(val)) {
				List<String> vals = Arrays.asList(val.split("\\|"));

				for (String s : vals) {
					String[] splitArr = s.split("@");
					unitMap = new HashMap<>();
					unitMap.put("aboiId",splitArr[0]);
					if (splitArr.length >1) {
						unitMap.put("feeIntas",splitArr[1]);
					}

					objects.add(unitMap);
				}
				((JSONObject)objects1.get(i)).put("fcs",objects);
			}

			//实际
			String acVal = datum.getAcVal();
			if (!SaafToolUtils.isNullOrEmpty(acVal)) {
				List<Map<String, String>> acObjects = new ArrayList<>();
				List<String> acVals = new ArrayList<>(Arrays.asList( acVal.split("\\|")));
				Map<String, String> acUnitMap = null;
				for (int is = 0 ; is< termsList.size() ; is++) {
					if(acVal.indexOf(("N" + termsList.get(is).getOrderNo())) != -1) {
						String[] splitArr = acVals.get(0).split("@");
						acUnitMap = new HashMap<>();
						acUnitMap.put("aboiId",splitArr[0]);
						if (splitArr.length >1) {
							acUnitMap.put("feeIntas",splitArr[1]);
						}
						acObjects.add(acUnitMap);
						acVals.remove(0);
					} else {
						acUnitMap = new HashMap<>();
						acUnitMap.put("aboiId",termsList.get(is).getOrderNo());
						acUnitMap.put("feeIntas","");
						acObjects.add(acUnitMap);
					}
				}
				((JSONObject)objects1.get(i)).put("ac",acObjects);
			}
			 i = i + 1 ;
		}
		resultJsonObject.put("count",findPages.getCount());
		resultJsonObject.put("curIndex",findPages.getCurIndex());
		resultJsonObject.put("pageSize",findPages.getPageSize());
		resultJsonObject.put("nextIndex",findPages.getNextIndex());
		resultJsonObject.put("preIndex",findPages.getPreIndex());
		resultJsonObject.put("pagesCount",findPages.getPagesCount());
		resultJsonObject.put("firstIndex",findPages.getFirstIndex());
		resultJsonObject.put("lastIndex",findPages.getLastIndex());
		resultJsonObject.put("data",objects1);
		return resultJsonObject;
	}

	@Override
	public List<TtaReportAboiSummaryFixLineEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception {
		ArrayList<TtaReportAboiSummaryFixLineEntity_HI> objects = new ArrayList<>();
		JSONArray objects1 = new JSONArray();
		JSONArray jsonArrayLine = paramsJSON.getJSONArray("save");
		Date date = new Date();
		for(int i = 0 ;i<jsonArrayLine.size();i++){
			JSONObject  json = (JSONObject)jsonArrayLine.get(i) ;
			TtaReportAboiSummaryFixLineEntity_HI instance = SaafToolUtils.setEntity(TtaReportAboiSummaryFixLineEntity_HI.class, json, ttaReportAboiSummaryFixLineDAO_HI, userId);
			instance.setOperatorUserId(userId);
			objects.add(instance);
			if ( (!SaafToolUtils.isNullOrEmpty(json.get("fcs"))) && (json.get("fcs") instanceof JSONArray) ) {
				objects1.add(json.getJSONArray("fcs"));
			}

		}
		ttaReportAboiSummaryFixLineDAO_HI.saveOrUpdateAll(objects);
		ttaReportAboiSummaryServer.saveOrUpdateInfoALL(objects1,userId);
		return objects;
	}

	@Override
	public Pagination<TtaReportAboiSummaryFixLineEntity_HI_RO> findBrandList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		String year  = queryParamJSON.getString("reportYear");
		paramsMap.put("year",Integer.valueOf(year.substring(0,4)));
		sql.append(TtaReportAboiSummaryFixLineEntity_HI_RO.QUERY_BRAND);
		Pagination<TtaReportAboiSummaryFixLineEntity_HI_RO> findList = ttaReportAboiSummaryFixLineDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;

	}

	@Override
	public Pagination<TtaReportAboiSummaryFixLineEntity_HI_RO> findGroupList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		String year  = queryParamJSON.getString("reportYear");
		paramsMap.put("year",Integer.valueOf(year.substring(0,4)));
		sql.append(TtaReportAboiSummaryFixLineEntity_HI_RO.QUERY_GROUP);
		Pagination<TtaReportAboiSummaryFixLineEntity_HI_RO> findList = ttaReportAboiSummaryFixLineDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;

	}

    @Override
    public List<TtaReportAboiSummaryFixLineEntity_HI_RO> findBrandList1(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
		String year  = queryParamJSON.getString("reportYear");
		paramsMap.put("year",Integer.valueOf(year.substring(0,4)));
        sql.append(TtaReportAboiSummaryFixLineEntity_HI_RO.QUERY_BRAND);
        List<TtaReportAboiSummaryFixLineEntity_HI_RO> findList = ttaReportAboiSummaryFixLineDAO_HI_RO.findList(sql, paramsMap);
        return findList;

    }

    @Override
    public List<TtaReportAboiSummaryFixLineEntity_HI_RO> findGroupList1(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
		String year  = queryParamJSON.getString("reportYear");
		paramsMap.put("year",Integer.valueOf(year.substring(0,4)));
        sql.append(TtaReportAboiSummaryFixLineEntity_HI_RO.QUERY_GROUP);
        List<TtaReportAboiSummaryFixLineEntity_HI_RO> findList = ttaReportAboiSummaryFixLineDAO_HI_RO.findList(sql, paramsMap);
        return findList;

    }

    @Override
    public JSONArray findInfoList1(JSONObject queryParamJSON) throws Exception {
        JSONObject resultJsonObject = new JSONObject();

        StringBuffer sb = new StringBuffer(TtaReportAboiSummaryFixLineEntity_HI_RO.QUERY);
        Map<String, Object> paramsMap = new HashMap<>();
		String year  = queryParamJSON.getString("reportYear");
		paramsMap.put("year",Integer.valueOf(year.substring(0,4)));
        List<TtaReportAboiSummaryFixLineEntity_HI_RO> finds = ttaReportAboiSummaryFixLineDAO_HI_RO.findList(sb, paramsMap);
        JSONArray objects1 = new JSONArray();
        int i = 0 ;
        for (TtaReportAboiSummaryFixLineEntity_HI_RO datum : finds) {
            objects1.add(JSONObject.parseObject(JSON.toJSONString(datum)));
            //预计
            List<Map<String, String>> objects = new ArrayList<>();
            String val = datum.getVal();
            Map<String, String> unitMap = null;
            if (!SaafToolUtils.isNullOrEmpty(val)) {
                List<String> vals = Arrays.asList(val.split("\\|"));

                for (String s : vals) {
                    String[] splitArr = s.split("@");
                    unitMap = new HashMap<>();
                    unitMap.put("aboiId",splitArr[0]);
                    if (splitArr.length >1) {
                        unitMap.put("feeIntas",splitArr[1]);
                    }

                    objects.add(unitMap);
                }
                ((JSONObject)objects1.get(i)).put("fcs",objects);
            }

            //实际
            String acVal = datum.getAcVal();
            if (!SaafToolUtils.isNullOrEmpty(acVal)) {
                List<Map<String, String>> acObjects = new ArrayList<>();
                List<String> acVals = Arrays.asList( acVal.split("\\|"));
                Map<String, String> acUnitMap = null;
                for (String s : acVals) {
                    String[] splitArr = s.split("@");
                    acUnitMap = new HashMap<>();
                    acUnitMap.put("aboiId",splitArr[0]);
                    if (splitArr.length >1) {
                        acUnitMap.put("feeIntas",splitArr[1]);
                    }
                    acObjects.add(acUnitMap);
                }
                ((JSONObject)objects1.get(i)).put("ac",acObjects);
            }
            i = i + 1 ;
        }
        return objects1;
    }

}
