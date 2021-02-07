package com.sie.saaf.bpm.model.inter.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskDelegateConfigEntity_HI_RO;
import com.sie.saaf.bpm.model.inter.IActBpmModel;
import com.sie.saaf.bpm.model.inter.IActBpmTaskDelegateConfig;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;


@Component("actBpmTaskDelegateConfigServer")
public class ActBpmTaskDelegateConfigServer implements IActBpmTaskDelegateConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmTaskDelegateConfigServer.class);
	
	@Autowired
	private ViewObject<ActBpmTaskDelegateConfigEntity_HI> bpmTaskDelegateConfigDAO_HI;
	
	@Autowired
	private BaseViewObject<ActBpmTaskDelegateConfigEntity_HI_RO> bpmTaskDelegateConfigDAO_HI_RO;
	
	@Autowired
	private IActBpmModel bpmModelServer;

	public ActBpmTaskDelegateConfigServer() {
		super();
	}
	
	
	/**
     * 流程代办查询
     * @param queryParamJSON
	 * {
     * categoryId 分类ID
     * clientUser 委托人
     * delegateUser 被委托人
     * clientUserId 委托人ID
     * delegateUserId 被委托人ID
     * disabled 是否禁用  Y是  N否
     * deleteFlag 删除标记，1.已删除，0.未删除
     * taskDate 任务日期 yyyy-MM-dd
     * startDate 起始时间，格式yyyy-MM-dd
     * endDate 截止时间，格式yyyy-MM-dd
	 * }
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     */
    @Override
    public Pagination<ActBpmTaskDelegateConfigEntity_HI_RO> findDelegateConfig(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer(ActBpmTaskDelegateConfigEntity_HI_RO.QUERY_ALL_CONFIG_SQL);
        SaafToolUtils.parperHbmParam(ActBpmTaskDelegateConfigEntity_HI.class, queryParamJSON, "cfg.client_user_id", "clientUserId", sql, paramsMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskDelegateConfigEntity_HI.class, queryParamJSON, "cfg.delegate_user_id", "delegateUserId", sql, paramsMap, "=");
        SaafToolUtils.parperHbmParam(ActBpmTaskDelegateConfigEntity_HI.class, queryParamJSON, "cfg.delete_flag", "deleteFlag", sql, paramsMap, "=");
        if(StringUtils.isNotBlank(queryParamJSON.getString("disabled"))) {
        	sql.append(" and cfg.disabled = :disabled");
        	paramsMap.put("disabled", "Y".equals(queryParamJSON.getString("disabled"))?1:0);
        }
        String clientUser = StringUtils.trimToNull(queryParamJSON.getString("clientUser"));
        if(clientUser != null) {
        	sql.append(" and exists(select 1 from base_users usr1 where usr1.user_id=cfg.client_user_id and (upper(usr1.user_name) like :userName1 or upper(usr1.user_full_name) like :userFullName1))");
            paramsMap.put("userName1", "%" + clientUser.toUpperCase() + "%");
            paramsMap.put("userFullName1", "%" + clientUser.toUpperCase() + "%");
        }
        String delegateUser = StringUtils.trimToNull(queryParamJSON.getString("delegateUser"));
        if(delegateUser != null) {
        	sql.append(" and exists(select 1 from base_users usr2 where usr2.user_id=cfg.delegate_user_id and (upper(usr2.user_name) like :userName2 or upper(usr2.user_full_name) like :userFullName2))");
            paramsMap.put("userName2", "%" + delegateUser.toUpperCase() + "%");
            paramsMap.put("userFullName2", "%" + delegateUser.toUpperCase() + "%");
        }
        String taskDate = StringUtils.trimToNull(queryParamJSON.getString("taskDate"));
        if(taskDate != null) {
        	sql.append(" and cfg.start_time <= :taskDate1 and cfg.end_time >= :taskDate2");
            paramsMap.put("taskDate1", ConvertUtil.stringToDateYMD(taskDate));
            paramsMap.put("taskDate2", ConvertUtil.stringToDateYMD(taskDate));
        }
        String categoryId = StringUtils.trimToNull(queryParamJSON.getString("categoryId"));
        if(categoryId != null) {
        	sql.append(" and ((cfg.category_ids = :categoryId1 or cfg.category_ids like :categoryId2 or cfg.category_ids like :categoryId3 or cfg.category_ids like :categoryId4)");
        	sql.append(" or exists(select 1 from act_re_model model where model.category_ = :categoryId5 and instr(concat(',',cfg.proc_def_keys,','), concat(',',model.key_,','))>0))");
        	paramsMap.put("categoryId1", categoryId);
        	paramsMap.put("categoryId2", "%," + categoryId);
            paramsMap.put("categoryId3", categoryId + ",%");
            paramsMap.put("categoryId4", "%," + categoryId + ",%");
            paramsMap.put("categoryId5", categoryId);
        }
        String startDate = StringUtils.trimToNull(queryParamJSON.getString("startDate"));
        if(startDate != null) {
            sql.append(" and cfg.end_time >= :startDate");
            paramsMap.put("startDate", ConvertUtil.stringToDateYMD(startDate));
        }
        String endDate = StringUtils.trimToNull(queryParamJSON.getString("endDate"));
        if(endDate != null) {
            sql.append(" and cfg.start_time < :endDate");
            Calendar c = new GregorianCalendar();
            c.setTime(ConvertUtil.stringToDateYMD(endDate));
            c.add(Calendar.DATE, 1);//日期向后移一天
            paramsMap.put("endDate", c.getTime());
        }
        sql.append(" order by cfg.config_id desc");
        return bpmTaskDelegateConfigDAO_HI_RO.findPagination(sql.toString(), paramsMap, pageIndex, pageRows);
    }
	

	/**
	 * 保存代办设置
	 * @param paramJSON
	 * {
	 * configId"：主键
	 * categoryIds" : 流程分类ID，多个用”,”分隔,
	 * delegateUserId" : 代理人ID,
	 * disabled"：禁用 true.是  false.否
	 * endTime" : 有效期截止时间,
	 * processDefinitionKeys" : 流程唯一标识，多个用”,”分隔,
 	 * startTime" : 有效期起始时间
	 * }
	 */
	@Override
	public boolean save(JSONObject paramJSON) {
		String selectDelegateUserId = paramJSON.getString("selectDelegateUserId");
		//将json转换成entity
		paramJSON.remove("delegate_userId");//解决前端传delegate_userId将delegateUserId值覆盖的问题
		ActBpmTaskDelegateConfigEntity_HI delegateConfig = JSON.parseObject(
				paramJSON.toString(), ActBpmTaskDelegateConfigEntity_HI.class);
		Assert.notNull(delegateConfig.getCategoryIds(), "参数错误，流程范围没有被选中！");
		Assert.notNull(selectDelegateUserId, "委托人为空！");
		delegateConfig.setDeleteFlag(0);
		Integer clientUserId = Integer.parseInt(selectDelegateUserId); //delegateConfig.getOperatorUserId();
		delegateConfig.setClientUserId(clientUserId);
		if(delegateConfig.getDisabled()==null) {
		    delegateConfig.setDisabled(false);
		}
		if(delegateConfig.getConfigId()!=null) {
		    ActBpmTaskDelegateConfigEntity_HI entity = bpmTaskDelegateConfigDAO_HI.getById(delegateConfig.getConfigId());
		    //Assert.isTrue(delegateConfig.getClientUserId().equals(entity.getClientUserId()), "非本人委托办理设置不可修改！");
			Assert.isTrue(!delegateConfig.getClientUserId().equals(delegateConfig.getDelegateUserId()), "委托人和被委托人不能相同！");
		    entity.setCategoryIds(delegateConfig.getCategoryIds());
		    entity.setClientUserId(delegateConfig.getClientUserId());
		    entity.setDelegateUserId(delegateConfig.getDelegateUserId());
		    entity.setDisabled(delegateConfig.getDisabled()==null?false:delegateConfig.getDisabled());
		    entity.setEndTime(delegateConfig.getEndTime());
		    entity.setProcessDefinitionKeys(delegateConfig.getProcessDefinitionKeys());
		    entity.setStartTime(delegateConfig.getStartTime());
		    entity.setOperatorUserId(delegateConfig.getOperatorUserId());
		    bpmTaskDelegateConfigDAO_HI.update(entity);
		}else {
			Assert.isTrue(!delegateConfig.getClientUserId().equals(delegateConfig.getDelegateUserId()), "委托人和被委托人不能相同！");
		    bpmTaskDelegateConfigDAO_HI.save(delegateConfig);
		}
		LOGGER.info("saved bpm delegate config:"+paramJSON.toString());
		return true;
	}

	/**
	 * 标记删除代办设置
     * @param paramJSON JSONObject
     * configIds JSONArray 代办ID
	 */
	@Override
	public void delete(JSONObject paramJSON) {
	   //JSONArray configIds = paramJSON.getJSONArray("configIds");
		//List<String> configIds = new ArrayList<String>();
		JSONArray configIds = new JSONArray();
		configIds.add(paramJSON.getString("configIds"));

	    Integer operatorUserId = paramJSON.getInteger("operatorUserId");
		if(configIds!=null && !configIds.isEmpty()){
			for(int i=0; i<configIds.size(); i++){
			    int id = configIds.getIntValue(i);
				ActBpmTaskDelegateConfigEntity_HI entity = bpmTaskDelegateConfigDAO_HI.getById(id);
				if(entity!=null) {
					entity.setDeleteFlag(1);
					entity.setOperatorUserId(operatorUserId);
					bpmTaskDelegateConfigDAO_HI.update(entity);
					LOGGER.info("deleted bpm delegate config:"+entity.getConfigId());
				}
			}
		}
	}
	
	/**
	 * 物理删除代办设置
     * @param paramJSON JSONObject
     * configIds JSONArray 代办ID
	 */
	@Override
	public void destory(JSONObject paramJSON) {
	    JSONArray configIds = paramJSON.getJSONArray("configIds");
	    if(configIds!=null && !configIds.isEmpty()){
	        for(int i=0; i<configIds.size(); i++){
	            int id = configIds.getIntValue(i);
	            bpmTaskDelegateConfigDAO_HI.delete(id);
	            LOGGER.info("destory bpm delegate config:"+id );
	        }
	    }
	}
	
	/**
	 * 代办设置启用、禁用
     * @param paramJSON JSONObject
     * configIds JSONArray 代办ID
     * disabled 禁用 true.是  false 否
	 */
	@Override
	public void updateStatus(JSONObject paramJSON) {
		JSONArray configIds = paramJSON.getJSONArray("configIds");
		Integer operatorUserId = paramJSON.getInteger("operatorUserId");
		boolean disabled = StringUtils.equals("true", paramJSON.getString("disabled"));
		if(configIds!=null && !configIds.isEmpty()){
			for(int i=0; i<configIds.size(); i++){
			    int id = configIds.getIntValue(i);
				ActBpmTaskDelegateConfigEntity_HI entity = bpmTaskDelegateConfigDAO_HI.getById(id);
				if(entity!=null) {
					Assert.isTrue(operatorUserId != null && operatorUserId.equals(entity.getClientUserId()), "非本人委托办理设置不可修改！");
					entity.setDisabled(disabled);
					entity.setOperatorUserId(operatorUserId);
					bpmTaskDelegateConfigDAO_HI.update(entity);
				}
			}
		}
		LOGGER.info("update bpm delegate config status:"+ paramJSON.toString());
	}

    @Override
    public ActBpmTaskDelegateConfigEntity_HI getById(Integer configId) {
        return bpmTaskDelegateConfigDAO_HI.getById(configId);
    }
    
    @Override
    public List<Integer> getDelegateUserIds(String processDefinitionKey, Integer clientUserId) {
        List<Integer> delegateUserIds = new ArrayList<Integer>();
        JSONObject parameters = new JSONObject();
        parameters.put("clientUserId", clientUserId);
        parameters.put("disabled", "N");
        parameters.put("deleteFlag", 0);
        parameters.put("taskDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        List<ActBpmTaskDelegateConfigEntity_HI_RO> configs = findDelegateConfig(parameters, 1, Integer.MAX_VALUE).getData();
        if(configs != null && !configs.isEmpty()) {
            for(ActBpmTaskDelegateConfigEntity_HI_RO config: configs) {
            	if(config.getDisabled() != null && config.getDisabled()) {
            		continue;
            	}
                if(StringUtils.isNotBlank(config.getProcessDefinitionKeys())
                        && ("," + config.getProcessDefinitionKeys() + ",").indexOf(processDefinitionKey)>0) {
                    if(!delegateUserIds.contains(config.getDelegateUserId())) {
                        delegateUserIds.add(config.getDelegateUserId());
                    }
                }else {
                    List<Integer> categoryIds = new ArrayList<Integer>();
                    List<String> processDefinitionKeys = new ArrayList<String>();
                    String[] categoryIds_ = StringUtils.isBlank(config.getCategoryIds())? null : config.getCategoryIds().split(",");
                    String[] processDefinitionKeys_ = StringUtils.isBlank(config.getProcessDefinitionKeys())? null : config.getProcessDefinitionKeys().split(",");
                    if(categoryIds_!=null) {
                        for(String categoryId:categoryIds_) {
                            categoryIds.add(Integer.parseInt(categoryId));
                        }
                    }
                    if(processDefinitionKeys_!=null) {
                        for(String processDefinitionKey_:processDefinitionKeys_) {
                            processDefinitionKeys.add(processDefinitionKey_);
                        }
                    }
                    List<String> processDefinitionKeyList = bpmModelServer.getProcessDefinitionKeys(categoryIds, processDefinitionKeys);
                    if(processDefinitionKeyList!=null && processDefinitionKeyList.contains(processDefinitionKey)) {
                        delegateUserIds.add(config.getDelegateUserId());
                    }
                }
            }
        }
        return delegateUserIds;
        
    }


	
	
}
