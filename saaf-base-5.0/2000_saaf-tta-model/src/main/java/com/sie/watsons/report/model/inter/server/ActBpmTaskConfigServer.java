package com.sie.watsons.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnLEntity_HI_RO;
import com.sie.watsons.report.model.entities.readonly.ActBpmTaskConfigEntity_HI_RO;
import com.sie.watsons.report.model.entities.readonly.TermsComparisionReportEntity_HI_RO;
import com.yhg.base.utils.DateUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.report.model.entities.ActBpmTaskConfigEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.report.model.inter.IActBpmTaskConfig;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;

@Component("actBpmTaskConfigServer")
public class ActBpmTaskConfigServer extends BaseCommonServer<ActBpmTaskConfigEntity_HI> implements IActBpmTaskConfig{
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmTaskConfigServer.class);

	@Autowired
	private BaseCommonDAO_HI<ActBpmTaskConfigEntity_HI> actBpmTaskConfigDAO_HI;

	@Autowired
	private BaseViewObject<ActBpmTaskConfigEntity_HI_RO> actBpmTaskConfigDAO_HI_RO;

	public ActBpmTaskConfigServer() {
		super();
	}

	/**
	 * 查询流程节点
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findNodeList(String  lookupType) {
		StringBuffer sql = new StringBuffer();
		sql.append(ActBpmTaskConfigEntity_HI_RO.getNodeSql());
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("lookupType",lookupType);//TTA_PROPOSAL_PROCESS_NODE
		SaafToolUtils.parperParam(queryParamJSON, "blv.lookup_type", "lookupType", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "blv.lookup_code asc", false);
		List<Map<String, Object>>   findList = actBpmTaskConfigDAO_HI.queryNamedSQLForList(sql.toString(), paramsMap);
		return findList;
	}

    /**
     * 获取流程节点统计各种数量。
     * @param params
     * @return
     */
    @Override
    public List<List<Map<String, Object>>> findPropsolProcessSummery(JSONObject params) {
		String endDate = params.getString("endDate");
		String proposalYear = params.getString("proposalYear");
		proposalYear =  StringUtils.isBlank(proposalYear) ? DateUtil.date2Str(new Date(),"yyyy"): proposalYear;
		if (StringUtils.isNotEmpty(endDate)) {
			params.put("endDate", endDate + " 23:59:59");
		}
    	List<List<Map<String, Object>>> listResultMap = new ArrayList<>();
		//orderNum, nodeName
		List<Map<String, Object>> nodeDicList = this.findNodeList("TTA_PROPOSAL_PROCESS_NODE");
		Assert.notEmpty(nodeDicList, "数据字典没有节点信息!");
		//为保证列数一致性，需前后追加指定的列数
		for (int i = 0; i < 4; i ++) {
			nodeDicList.add(0, new HashMap<>());//往前面追加4列
		}
		for (int i = 0; i < 5; i ++) {
			nodeDicList.add(new HashMap<>());//往后面追加5列
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("procDefKey", "TTA_PROPOSAL.-999");
		//deptDesc, deptCode, nodeName, deptNodeName
		StringBuffer waitNodeCntSql = new StringBuffer(ActBpmTaskConfigEntity_HI_RO.getWaitNodeCntSql());
		SaafToolUtils.parperParam(params, "tth.dept_desc", "deptName", waitNodeCntSql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(params, "b.create_time_", "endDate", waitNodeCntSql, paramsMap, " <= ");
		waitNodeCntSql.append(" group by b.name_, tth.dept_code");
		List<Map<String, Object>>  deptNodeList = actBpmTaskConfigDAO_HI.queryNamedSQLForList(waitNodeCntSql.toString(), paramsMap);
		//查询所有部门结合
	    TreeSet<String> deptHashSet = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
	    //查询部门编码及部门名称封装成map便于使用
		HashMap<String, String> queryDeptCodeAndNameMap = new HashMap<>();
		List<Map<String, Object>> deptListMap = actBpmTaskConfigDAO_HI.queryNamedSQLForList(ActBpmTaskConfigEntity_HI_RO.getDeptCodeAndNameSql(false), new HashMap<String, Object>());
		deptListMap.forEach(item->{
			String deptDesc = item.get("deptDesc") + "";
			String deptCode = item.get("deptCode") + "";
			queryDeptCodeAndNameMap.put(deptCode, deptDesc);
		});
		//统计部门节点数量
	    Map<String, Object>  deptNodeCntMap = new HashMap<String, Object>();
		for (Map<String, Object> deptNode : deptNodeList) {
			deptHashSet.add(deptNode.get("deptCode") + "");
			deptNodeCntMap.put(deptNode.get("deptNodeName") + "", deptNode.get("cnt"));
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		//Proposal need buyer sumbit to BIC, 第二列
		Map<String, Object>  needBuyerMap = new HashMap<String, Object>();
		List<Map<String, Object>> needBuyerList = actBpmTaskConfigDAO_HI.queryNamedSQLForList(ActBpmTaskConfigEntity_HI_RO.getProposalNeedBuyerSumbitToBIC(false), queryMap);
		for (Map<String, Object> map : needBuyerList) {
			needBuyerMap.put(map.get("deptCode") + "", map.get("cnt"));
		}

		//pendingToSubmit 状态为制单中的合集 第三列 status A
		Map<String, Object>  pendingToSubmitMap = new HashMap<String, Object>();
		//第Proposal approved列，已审批单据的合集 status C
		Map<String, Object>  approvedMap = new HashMap<String, Object>();
		queryMap.put("proposalYear",proposalYear);
		List<Map<String, Object>> pendingToSubmitList = actBpmTaskConfigDAO_HI.queryNamedSQLForList(ActBpmTaskConfigEntity_HI_RO.getPendingToSubmitSql(), queryMap);
		for (Map<String, Object> map : pendingToSubmitList) {
			Object cnt = map.get("cnt");
			if ("A".equalsIgnoreCase(map.get("status") + "")) {
				pendingToSubmitMap.put(map.get("deptCode") + "", cnt  == null ? 0 : cnt);
			} else {
				approvedMap.put(map.get("deptCode") + "", cnt  == null ? 0 : cnt);
			}
		}
		//没有部门对应的数据使用0填充。
		for (Map.Entry<String, String> deptEntry : queryDeptCodeAndNameMap.entrySet()) {
			if (!pendingToSubmitMap.containsKey(deptEntry.getKey())){
				pendingToSubmitMap.put(deptEntry.getKey(), 0);
			}
			if (!approvedMap.containsKey(deptEntry.getKey())){
				approvedMap.put(deptEntry.getKey(), 0);
			}
			if (!needBuyerMap.containsKey(deptEntry.getKey())){
				needBuyerMap.put(deptEntry.getKey(), 0);
			}
		}
		//部门名称：[[{deptDesc:Health,nodeName:BIC审批,nodeValue:2},deptDesc:Health,nodeName:BIC审批3,nodeValue:3}]]
		//组装参数 //orderNum, nodeName
		List<Map<String, Object>> listMap = null;
		Map<String, Object> resutMap = null;
		Iterator<String> iterator = deptHashSet.iterator();
		while(iterator.hasNext()){
			listMap = new ArrayList<>();
			String deptCode = iterator.next();
			for (int idx = 0; idx < nodeDicList.size(); idx++) {
				Map<String, Object> nodeDicMap = nodeDicList.get(idx);
				resutMap = new HashMap<>();;
				//前三列，后5列需特殊处理
				if (idx <= 3 || nodeDicList.size() -5 <= idx) {
					if (idx == 0) {
						//第一列部门信息
						resutMap.put("nodeValue", queryDeptCodeAndNameMap.get(deptCode));
					} else if (idx == 1) {
						//第二列 Proposal need buyer sumbit to BIC
						resutMap.put("nodeValue", needBuyerMap.get(deptCode));
					} else if (idx == 2) {
						resutMap.put("nodeValue", pendingToSubmitMap.get(deptCode));
					} else if (idx == nodeDicList.size() - 3) {
						//Proposal  approved approvedMap
						resutMap.put("nodeValue", approvedMap.get(deptCode));
					}
				} else {
					String nodeName = nodeDicMap.get("nodeName") + "";
					resutMap.put("nodeName", nodeName);//节点名称
					String cnt = deptNodeCntMap.get(deptCode + "_" + nodeName) + "";
					cnt = (StringUtils.isBlank(cnt) || "null".equalsIgnoreCase(cnt)) ? "0" : cnt;
					resutMap.put("nodeValue", cnt);//节点值
				}
				resutMap.put("deptCode", deptCode);//部门编码
				listMap.add(resutMap);
			}
			listResultMap.add(listMap);
		}

		List<Map<String, Object>> thisWeekList = new ArrayList<>();
		Map<String, Object> map = null;
		for (int i = 0; i < nodeDicList.size(); i++) {
            map = new HashMap<>();
            map.put("nodeValue", i == 0 ? "TOTAL" : 0);
            thisWeekList.add(map);
        }
		listResultMap.add(thisWeekList);

        return listResultMap;
    }


	@Override
	public List<List<Map<String, Object>>> findContratProcessSummery(JSONObject params) {
		String endDate = params.getString("endDate");
		String proposalYear = params.getString("proposalYear");
		proposalYear =  StringUtils.isBlank(proposalYear) ? DateUtil.date2Str(new Date(),"yyyy"): proposalYear;
		if (StringUtils.isNotEmpty(endDate)) {
			params.put("endDate", endDate + " 23:59:59");
		}
		List<List<Map<String, Object>>> listResultMap = new ArrayList<>();
		//orderNum, nodeName，流程节点配置信息
		List<Map<String, Object>> nodeDicList = this.findNodeList("TTA_PROPOSAL_PROCESS_NODE");
		Assert.notEmpty(nodeDicList, "数据字典没有节点信息!");
		//为保证列数一致性，需前后追加指定的列数
		for (int i = 0; i < 4; i ++) {
			nodeDicList.add(0, new HashMap<>());//往前面追加4列
		}
		for (int i = 0; i < 8; i ++) {
			nodeDicList.add(new HashMap<>());//往后面追加5列 + 3列 = 8列  2020.11.12修改,增加多了3列
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("procDefKey", "TTA_ELEC_CONTRACT.-999");
		//deptDesc, deptCode, nodeName, deptNodeName
		StringBuffer waitNodeCntSql = new StringBuffer(ActBpmTaskConfigEntity_HI_RO.getContratWaitNodeCntSql());
		SaafToolUtils.parperParam(params, "tth.dept_desc", "deptName", waitNodeCntSql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(params, "b.create_time_", "endDate", waitNodeCntSql, paramsMap, " <= ");
		waitNodeCntSql.append(" group by b.name_, tth.dept_code");
		List<Map<String, Object>>  deptNodeList = actBpmTaskConfigDAO_HI.queryNamedSQLForList(waitNodeCntSql.toString(), paramsMap);
		//查询所有部门结合
		TreeSet<String> deptHashSet = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		//查询部门编码及部门名称封装成map便于使用
		HashMap<String, String> queryDeptCodeAndNameMap = new HashMap<>();
		List<Map<String, Object>> deptListMap = actBpmTaskConfigDAO_HI.queryNamedSQLForList(ActBpmTaskConfigEntity_HI_RO.getDeptCodeAndNameSql(true), new HashMap<String, Object>());
		deptListMap.forEach(item->{
			String deptDesc = item.get("deptDesc") + "";
			String deptCode = item.get("deptCode") + "";
			queryDeptCodeAndNameMap.put(deptCode, deptDesc);
		});
		//统计部门节点数量
		Map<String, Object>  deptNodeCntMap = new HashMap<String, Object>();
		for (Map<String, Object> deptNode : deptNodeList) {
			deptHashSet.add(deptNode.get("deptCode") + "");
			deptNodeCntMap.put(deptNode.get("deptNodeName") + "", deptNode.get("cnt"));
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		//Proposal need buyer sumbit to BIC, 第二列
		Map<String, Object>  needBuyerMap = new HashMap<String, Object>();
		List<Map<String, Object>> needBuyerList = actBpmTaskConfigDAO_HI.queryNamedSQLForList(ActBpmTaskConfigEntity_HI_RO.getProposalNeedBuyerSumbitToBIC(true), queryMap);
		for (Map<String, Object> map : needBuyerList) {
			needBuyerMap.put(map.get("deptCode") + "", map.get("cnt"));
		}

		//pendingToSubmit 状态为制单中的合集 第三列 status A
		Map<String, Object>  pendingToSubmitMap = new HashMap<String, Object>();
		//第Proposal approved列，已审批单据的合集 status C
		Map<String, Object>  approvedMap = new HashMap<String, Object>();
		queryMap.put("proposalYear",proposalYear);
		List<Map<String, Object>> pendingToSubmitList = actBpmTaskConfigDAO_HI.queryNamedSQLForList(ActBpmTaskConfigEntity_HI_RO.getContractPendingToSubmitSql(), queryMap);
		for (Map<String, Object> map : pendingToSubmitList) {
			Object cnt = map.get("cnt");
			if ("A".equalsIgnoreCase(map.get("status") + "")) {
				pendingToSubmitMap.put(map.get("deptCode") + "", cnt  == null ? 0 : cnt);
			} else {
				approvedMap.put(map.get("deptCode") + "", cnt  == null ? 0 : cnt);
			}
		}
		//没有部门对应的数据使用0填充。
		for (Map.Entry<String, String> deptEntry : queryDeptCodeAndNameMap.entrySet()) {
			if (!pendingToSubmitMap.containsKey(deptEntry.getKey())){
				pendingToSubmitMap.put(deptEntry.getKey(), 0);
			}
			if (!approvedMap.containsKey(deptEntry.getKey())){
				approvedMap.put(deptEntry.getKey(), 0);
			}
			if (!needBuyerMap.containsKey(deptEntry.getKey())){
				needBuyerMap.put(deptEntry.getKey(), 0);
			}
		}
		//部门名称：[[{deptDesc:Health,nodeName:BIC审批,nodeValue:2},deptDesc:Health,nodeName:BIC审批3,nodeValue:3}]]
		//组装参数 //orderNum, nodeName
		List<Map<String, Object>> listMap = null;
		Map<String, Object> resutMap = null;
		Iterator<String> iterator = deptHashSet.iterator();
		//组装每个部门的节点相关信息
		while(iterator.hasNext()){
			listMap = new ArrayList<>();
			String deptCode = iterator.next();
			//遍历列
			for (int idx = 0; idx < nodeDicList.size(); idx++) {
				Map<String, Object> nodeDicMap = nodeDicList.get(idx);
				resutMap = new HashMap<>();;
				//前三列，后5列需特殊处理 2020.11.12增加多3列处理
				//if (idx <= 3 || nodeDicList.size() -5 <= idx) {
				if (idx <= 3 || nodeDicList.size() - 8 <= idx) {
					if (idx == 0) {
						//第一列部门信息
						resutMap.put("nodeValue", queryDeptCodeAndNameMap.get(deptCode));
					} else if (idx == 1) {
						//第二列 Proposal need buyer sumbit to BIC
						resutMap.put("nodeValue", needBuyerMap.get(deptCode));
					} else if (idx == 2) {
						resutMap.put("nodeValue", pendingToSubmitMap.get(deptCode));
					} else if (idx == nodeDicList.size() - 3) {
						//Proposal  approved approvedMap
						resutMap.put("nodeValue", approvedMap.get(deptCode));
					} else {
						resutMap.put("nodeValue",0);
					}
				} else {
					String nodeName = nodeDicMap.get("nodeName") + "";
					resutMap.put("nodeName", nodeName);//节点名称
					String cnt = deptNodeCntMap.get(deptCode + "_" + nodeName) + "";
					cnt = (StringUtils.isBlank(cnt) || "null".equalsIgnoreCase(cnt)) ? "0" : cnt;
					resutMap.put("nodeValue", cnt);//节点值
				}
				resutMap.put("deptCode", deptCode);//部门编码
				listMap.add(resutMap);
			}
			listResultMap.add(listMap);
		}

		List<Map<String, Object>> thisWeekList = new ArrayList<>();
		Map<String, Object> map = null;
		for (int i = 0; i < nodeDicList.size(); i++) {
			map = new HashMap<>();
			map.put("nodeValue", i == 0 ? "TOTAL" : 0);
			thisWeekList.add(map);
		}
		listResultMap.add(thisWeekList);

		return listResultMap;
	}
}
