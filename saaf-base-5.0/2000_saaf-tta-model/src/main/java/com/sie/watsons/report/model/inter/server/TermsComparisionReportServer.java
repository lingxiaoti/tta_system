package com.sie.watsons.report.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.watsons.report.model.entities.readonly.TermsComparisionReportEntity_HI_RO;
import com.sie.watsons.report.model.inter.ITermsComparisionReport;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component("termsComparisionReportServer")
public class TermsComparisionReportServer  implements ITermsComparisionReport {
	private static final Logger LOGGER = LoggerFactory.getLogger(TermsComparisionReportServer.class);

    @Autowired
    private ViewObject<BaseLookupValuesEntity_HI> baseLookupValuesDAO_HI;

	@Autowired
	private BaseViewObject<TermsComparisionReportEntity_HI_RO> TermsComparisionReportDAO_HI_RO;

	@Override
	public JSONObject find(JSONObject queryParamJSON) {
	    JSONObject result =  new JSONObject();
        StringBuffer sbSql = new StringBuffer();
        sbSql.append(TermsComparisionReportEntity_HI_RO.queryLastCurrentTerms());
		Map<String, Object> queryMap = new HashMap<String, Object>();
        //queryMap.put("year", queryParamJSON.getInteger("year"));
		queryMap.put("proposalId",queryParamJSON.getInteger("proposalId"));
        //queryMap.put("vendorNbr", queryParamJSON.getString("vendorNbr"));
        List<BaseLookupValuesEntity_HI> lookUpList = baseLookupValuesDAO_HI.findByProperty(new JSONObject().fluentPut("lookupType", "TERM_CATEGORY").fluentPut("enabledFlag", "Y").fluentPut("deleteFlag", 0));
        if (lookUpList == null || lookUpList.isEmpty()) {
            LOGGER.info(".findTermsAgrement 没有设置快码");
            return null;
        }

        List<TermsComparisionReportEntity_HI_RO> part2List = null;
		for (BaseLookupValuesEntity_HI entityHiRo : lookUpList) {
            List<TermsComparisionReportEntity_HI_RO> partList = null;
            queryMap.put("termCategory", entityHiRo.getLookupCode());
            switch(entityHiRo.getLookupCode()){
                case "1" :
                    //语句
                    partList = TermsComparisionReportDAO_HI_RO.findList(sbSql.toString(), queryMap);
                    Assert.notEmpty(partList, "合同数据为空！");
                    result.fluentPut("partList1", partList);
                    break; //可选
                case "2" :
                    HashMap<String, Object> paramsMap = new HashMap<>();
                    paramsMap.putAll(queryMap);
                    paramsMap.remove("vendorNbr");
                    String[] headTileArr  = null;
                    //目标退佣头信息及免费货品
                    TermsComparisionReportEntity_HI_RO headAndBody = null;
                    part2List = TermsComparisionReportDAO_HI_RO.findList(TermsComparisionReportEntity_HI_RO.queryPart2Sql(), paramsMap);
                    if (part2List != null && part2List.size() > 0) {
                        headAndBody = part2List.get(0); //目标推送头信息
                        part2List.remove(0);
                    }
                    Map<String, Object> queryTitleMap = new HashMap<>();
                    queryTitleMap.put("proposalYear", queryParamJSON.getInteger("proposalYear"));
                    queryTitleMap.put("majorDeptCode", queryParamJSON.get("majorDeptCode"));
                    TermsComparisionReportEntity_HI_RO titleHead = TermsComparisionReportDAO_HI_RO.get(TermsComparisionReportEntity_HI_RO.queryHeadTitleSql(), queryTitleMap);
                    if (titleHead != null) {
                        String termsCnName = titleHead.getTermsCn();
                        titleHead.setTermsCn(termsCnName);
                        String str = "目标退佣@@";
                        if (headAndBody != null && StringUtils.isNotBlank(headAndBody.getTermsCn())) {
                            str = headAndBody.getTermsCn() + "@@";
                        }
                        headTileArr = (str + titleHead.getTermsCn() + "").split("@@");
                    }
                    partList = TermsComparisionReportDAO_HI_RO.findList(TermsComparisionReportEntity_HI_RO.queryFloor(), paramsMap);
                    Assert.notEmpty(partList, "合同数据为空！");
                    List<String> currentValueList = null;
                    ArrayList<List> partListCurrent = new ArrayList<>();
                    for (TermsComparisionReportEntity_HI_RO entity : partList) {
                        String currentTtaValue = entity.getCurrentTtaValue() + "";
                        int length = currentTtaValue.split("@@").length;
                        while (headTileArr.length > length) {
                            currentTtaValue += "@@-999";
                            LOGGER.info("合同条款currentTtaValue:{}", currentTtaValue);
                            length = currentTtaValue.split("@@").length;
                        }
                        currentValueList = new ArrayList<>(Arrays.asList(currentTtaValue.split("@@")));
                        for (int idx = 0; idx < currentValueList.size(); idx ++) {
                            if ("-999".equals(currentValueList.get(idx))) {
                                LOGGER.info("合同条款setCurrentTtaValue idx:{}", idx);
                                currentValueList.set(idx, "");
                            }
                        }
                        partListCurrent.add(currentValueList) ;

                    }
                    Assert.notEmpty(partList, "合同数据为空！");
                    Map<String, Object> params = new HashMap<>();
                    params.put("oiType", partList.get(0).getOiType());
                    params.put("termsEn", headAndBody.getTermsEn());
                    setPart2Params(headTileArr, partListCurrent, params);
                    result.put("partList2", params);
                    break;
                default :
                    partList = TermsComparisionReportDAO_HI_RO.findList(sbSql.toString(), queryMap);
                    if (partList != null && part2List != null && part2List.size() > 0) {
                        partList.addAll(0, part2List);
                    } else {
                        result.fluentPut("partList3", part2List);
                    }
                    result.fluentPut("partList3", partList);
            }
        }
        return result;
	}

    private void setPart2Params(String[] headTileArr, ArrayList<List> partListCurrent, Map<String, Object> params) {
        ArrayList headList = new ArrayList(Arrays.asList(headTileArr));
        int idx = 0;
        boolean idxFlag = false;
        if (headList != null) {
            for (;idx < headList.size(); idx ++) {
                if ("/".equalsIgnoreCase(headList.get(idx) + "")) {
                    headList.remove(idx);
                    idxFlag = true;
                    break;
                }
            }
        }
        if (partListCurrent != null && idxFlag) {
          for (List partLists :  partListCurrent) {
              for (int i = 0; i < partLists.size(); i ++) {
                  if (i == idx) {
                      partLists.remove(idx);
                  }
              }
          }
        }
        partListCurrent.add(0, headList);
        params.put("bodyData",partListCurrent );
        if (idxFlag) {
            params.put("leftCol", idx - 1);
            params.put("rightCol", headList.size() - idx);
        } else {
            params.put("leftCol", (headList.size() - 1)/2);
            params.put("rightCol", (headList.size() - 1)/2);
        }
    }

    private void getFloorData() {
        List<TermsComparisionReportEntity_HI_RO> list = TermsComparisionReportDAO_HI_RO.findList(TermsComparisionReportEntity_HI_RO.queryFloor());
        LinkedHashSet headerData = new LinkedHashSet<>();
        list.forEach(item -> {
            headerData.add(item.getIncomeType());
        });
        Map<Integer, Object> map = new HashMap();
        ArrayList<Map<Integer, Object>> lineArray = new ArrayList<>();
        int size = headerData.size();//列数
        for (int idx = 0; idx < list.size(); idx++) {
            TermsComparisionReportEntity_HI_RO entity_hi_ro = list.get(idx);
            String[] arrayValue = new String[]{entity_hi_ro.getCurrentTtaValue(), entity_hi_ro.getLastTtaValue()};
            if (idx == 0) {
                map.put(0, arrayValue);
            } else {
                Integer key = idx % size;
                map.put(key, arrayValue);
                if (key + 1 == size) {
                    lineArray.add(map);
                    map = new HashMap();
                }
            }
        }

      for (int idx = 0; idx < lineArray.size(); idx ++) {
          Map<Integer, Object> integerObjectMap = lineArray.get(idx);
          Set<Map.Entry<Integer, Object>> entries = integerObjectMap.entrySet();
          for (Map.Entry<Integer, Object> entry : entries ) {
              String[] arrayValue = (String[])entry.getValue();
              for (String arr : arrayValue) {

              }
          }
      }

        System.out.println("lineArray:" + lineArray);
    }
}
