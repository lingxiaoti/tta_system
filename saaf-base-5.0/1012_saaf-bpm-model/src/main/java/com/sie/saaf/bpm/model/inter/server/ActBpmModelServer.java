package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmCategoryEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmTaskProcesserEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import io.jsonwebtoken.lang.Assert;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.NativeModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Component("actBpmModelServer")
public class ActBpmModelServer implements IActBpmModel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmModelServer.class);

	@Autowired
    private RepositoryService repositoryService;
	
	@Autowired
	private IActBpmProcess bpmProcessServer;
	
	@Autowired
	private IActBpmList bpmListServer;
	
	@Autowired
	private IActBpmCategory bpmCategoryServer;
	
	@Autowired
	private IActBpmPermission bpmPermissionServer;
	
	@Autowired
	private IActBpmTaskConfig bpmTaskConfigServer;

    @Autowired
    private BaseViewObject<ActBpmModelEntity_HI_RO> bpmModelDAO_HI;
    
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 根据流程模型ID查询流程模型
     * @param modelId 流程模型ID
     * @return ActBpmModelEntity_HI_RO
     */
    @Override
    public ActBpmModelEntity_HI_RO getById(String modelId) {
        if(StringUtils.isBlank(modelId)){
            return null;
        }
        StringBuffer sql = new StringBuffer(ActBpmModelEntity_HI_RO.QUERY_ALL_MODEL_SQL);
        Map<String, Object> params = new HashMap<String, Object>();
        sql.append(" and model.id_= :modelId");
        params.put("modelId", modelId);
        List<ActBpmModelEntity_HI_RO> list = bpmModelDAO_HI.findList(sql.toString(), params);
        return list != null && !list.isEmpty()?list.get(0) : null;
    }
    
    /**
	 * 由流程KEY查询流程模型
	 * @author laoqunzhao 2018-04-28
	 * @param key 模型KEY
	 * @return Model
	 */
	@Override
	public Model getByKey(String key) {
		if(StringUtils.isBlank(key)) {
			return null;
		}
		List<Model> models = repositoryService.createModelQuery().modelKey(key).list();
		return models != null && !models.isEmpty()? models.get(0):null;
	}

    /**
     * 根据流程标识及事业部查询流程
     * @author laoqunzhao 2018-09-04
     * @param processKey 流程标识
     * @param ouId 事业部ID
     * @return List<ActBpmModelEntity_HI_RO>
     */
    @Override
    public ActBpmModelEntity_HI_RO findByProcessKeyAndOuId(String processKey, Integer ouId){
        List<ActBpmModelEntity_HI_RO> models = findByProcessKeyInRedis(processKey, ouId);
        if(models != null && models.size() == 1){
            return models.get(0);
        }
        return null;
    }
	
	/**
	 * 根据流程标识查询流程(优先从Redis中查询)
	 * @author laoqunzhao 2018-09-04
	 * @param processKey 流程标识
	 * @param ouId 事业部ID
	 * @return List<ActBpmModelEntity_HI_RO>
	 */
	@Override
	public List<ActBpmModelEntity_HI_RO> findByProcessKeyInRedis(String processKey, Integer ouId){
		if(StringUtils.isBlank(processKey)) {
			return null;
		}
		String key = processKey + "::" + ouId;
		//从Redis中加载，加快速度
		if(ouId != null) {
			try {
                String modelsStr = hget(WorkflowConstant.REDIS_PROC_OU_KEY, key);
				if(StringUtils.isNotBlank(modelsStr)) {
					List<ActBpmModelEntity_HI_RO> instances = new ArrayList<>();
					JSONArray modelsJSON = (JSONArray) JSON.parse(modelsStr);
					for(Object modelJSON : modelsJSON) {
                        ActBpmModelEntity_HI_RO instance = JSON.toJavaObject((JSONObject)modelJSON, ActBpmModelEntity_HI_RO.class);
						instances.add(instance);
					}
					return instances;
				}
			}catch(Exception e) {
                LOGGER.error(e.getMessage(), e);
			}
		}
        //Redis中取不到值，重新查询数据库
        List<ActBpmModelEntity_HI_RO> instances = findByProcessKey(processKey, ouId);
        //存Redis
		if(ouId != null && instances != null && !instances.isEmpty()) {
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(ActBpmModelEntity_HI_RO.class,
                    "modelId","key","name","categoryId","categoryName","metaInfo","createTime","lastUpdateTime",
                    "deploymentTime","deploymentId","version","tenantId","status","ouIds","systemCode","processKey");
            String modelsStr = JSON.toJSONString(instances, filter);
            hset(WorkflowConstant.REDIS_PROC_OU_KEY, key, modelsStr);
		}
		return instances;
	}

    /**
     * 根据流程标识查询流程
     * @author laoqunzhao 2018-09-04
     * @param processKey 流程标识
     * @param ouId 事业部ID
     * @return List<ActBpmModelEntity_HI_RO>
     */
    @Override
    public List<ActBpmModelEntity_HI_RO> findByProcessKey(String processKey, Integer ouId){
        if(StringUtils.isBlank(processKey)) {
            return null;
        }
        JSONObject queryParamJSON = new JSONObject();
        queryParamJSON.put("processKey_eq", processKey);
        if(ouId != null) {
            queryParamJSON.put("ouIds", new JSONArray().fluentAdd(ouId));
        }
        Pagination<ActBpmModelEntity_HI_RO> pagination = findModels(queryParamJSON, -1, -1);
        return pagination.getData();
    }
	
	/**
	 * 根据流程名称查询流程
	 * @author laoqunzhao 2018-09-04
	 * @param name 流程名称
	 * @param ouId 事业部ID
	 * @return List<ActBpmModelEntity_HI_RO>
	 */
	@Override
	public List<ActBpmModelEntity_HI_RO> findByName(String name, Integer ouId){
		if(StringUtils.isBlank(name)) {
			return null;
		}
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("name_eq", name);
		if(ouId != null) {
			queryParamJSON.put("ouIds", new JSONArray().fluentAdd(ouId));
		}
		Pagination<ActBpmModelEntity_HI_RO> pagination = findModels(queryParamJSON, -1, -1);
		return pagination.getData();
	}
	 
	
	/**
	 * 查询流程模型列表
	 * @author laoqunzhao 2018-04-28
	 * @param queryParamJSON
	 * {
	 * categoryId 流程分类ID
	 * processKey 流程标识（like）
     * processKey_eq 流程标识（=）
	 * name 名称(like)
	 * name_eq 名称(=)
	 * key KEY
	 * deployed 是否已部署
	 * systemCode 系统编码
	 * ouIds 事业部ID[]
	 * roleKey 角色KEY
     * processer 办理人
	 * }
	 * @param pageIndex 页码索引
	 * @param pageRows 每页记录数
	 * @return
	 */
	@Override
    public Pagination<ActBpmModelEntity_HI_RO> findModels(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer condition = new StringBuffer();
        Map<String, Object> params = new HashMap<String, Object>();
        //流程定义名称查询
        String name = StringUtils.trimToNull(queryParamJSON.getString("name"));
        if(name != null) {
            condition.append(" and upper(model.name_) like :name");
            params.put("name", "%" + name.toUpperCase() + "%");
        }
        String nameEq = StringUtils.trimToNull(queryParamJSON.getString("name_eq"));
        if(nameEq != null) {
            condition.append(" and model.name_ = :nameEq");
            params.put("nameEq", nameEq);
        }
        //流程定义KEY查询
        String key = StringUtils.trimToNull(queryParamJSON.getString("key"));
        if(key != null) {
            condition.append(" and upper(model.key_) like :key");
            params.put("key", "%" + key.toUpperCase() + "%");
        }
        //是否发布
        String deployed = StringUtils.trimToNull(queryParamJSON.getString("deployed"));
        if(deployed != null && deployed.equals("true")) {
            condition.append(" and exists(select 1 from act_re_procdef def1 where def1.key_=model.key_ and def1.suspension_state_=1)");
        }
        //流程分类
        String categoryId = StringUtils.trimToNull(queryParamJSON.getString("categoryId"));
        
        if(categoryId != null) {
            //分类查询
            ActBpmCategoryEntity_HI category =bpmCategoryServer.get(Integer.parseInt(categoryId));
            if(category != null) {
                condition.append(" and (cate.category_id = :categoryId or cate.category_code like :categoryCode)");
                params.put("categoryId", categoryId);
                params.put("categoryCode", category.getCategoryCode() + ".%");
            }
        }
        //流程标识
        String processKey = StringUtils.trimToNull(queryParamJSON.getString("processKey"));
        if(processKey != null) {
            condition.append(" and cate.process_key like :processKey");
        	params.put("processKey", "%" + processKey.toUpperCase() + "%");
        }
        String processKeyEq = StringUtils.trimToNull(queryParamJSON.getString("processKey_eq"));
        if(processKeyEq != null) {
            condition.append(" and cate.process_key = :processKeyEq");
            params.put("processKeyEq", processKeyEq.toUpperCase());
        }
        //系统代码查询
        String systemCode = StringUtils.trimToNull(queryParamJSON.getString("systemCode"));
        List<Integer> ouIds = new ArrayList<>();
        if(queryParamJSON.containsKey("ouIds")) {
            JSONArray ouIdsJSON = queryParamJSON.getJSONArray("ouIds");
            for(int i=0; i<ouIdsJSON.size(); i++) {
                ouIds.add(ouIdsJSON.getInteger(i));
            }
        }
        if(systemCode != null || !ouIds.isEmpty()) {
            condition.append(" and exists(select 1 from act_bpm_permission where proc_def_key=model.key_ ");
            if(systemCode != null) {
                condition.append(" and upper(system_code) = :systemCode ");
                params.put("systemCode", systemCode.toUpperCase());
            }
            /*if(!ouIds.isEmpty()) {
                condition.append(" and ou_id in (" + StringUtils.join(ouIds, ",") + ")");
            }*/
            condition.append(")");
        }
        //办理人
        String processer = StringUtils.trimToNull(queryParamJSON.getString("processer"));
        if(processer != null){
            condition.append(" and exists(select 1 from act_bpm_task_processer pcser inner join act_bpm_task_config tskcf on pcser.config_id=tskcf.config_id");
            condition.append(" where tskcf.proc_def_key=model.key_ and concat(',', pcser.processer_ids, ',') like :processer)");
            params.put("processer", "%," + processer + ",%");
        }
        //角色查询act_bpm_task_processer
        String roleKey = StringUtils.trimToNull(queryParamJSON.getString("roleKey"));
        if(roleKey != null) {
            condition.append(" and exists(select 1 from act_bpm_task_config tcfg left join act_bpm_task_processer pcs on pcs.config_id=tcfg.config_id where tcfg.delete_flag=0 and tcfg.proc_def_key=model.key_");
            condition.append(" and (tcfg.cc_role_keys = :ccRoleKey1 or tcfg.cc_role_keys like :ccRoleKey2 or tcfg.cc_role_keys like :ccRoleKey3 or tcfg.cc_role_keys like :ccRoleKey4");
            condition.append(" OR pcs.processer_role_keys = :roleKey1 or pcs.processer_role_keys like :roleKey2 or pcs.processer_role_keys like :roleKey3 or pcs.processer_role_keys like :roleKey4))");
            params.put("ccRoleKey1", roleKey);
            params.put("ccRoleKey2", "%," + roleKey);
            params.put("ccRoleKey3", roleKey + ",%");
            params.put("ccRoleKey4", "%," + roleKey + ",%");
            params.put("roleKey1", roleKey);
            params.put("roleKey2", "%," + roleKey);
            params.put("roleKey3", roleKey + ",%");
            params.put("roleKey4", "%," + roleKey + ",%");
        }

        StringBuffer countSql = new StringBuffer(ActBpmModelEntity_HI_RO.QUERY_ALL_MODEL_COUNT_SQL);
        countSql.append(condition);
        StringBuffer querySql = new StringBuffer(ActBpmModelEntity_HI_RO.QUERY_ALL_MODEL_SQL);
        querySql.append(condition);
        querySql.append(" order by model.last_update_time_");
        return bpmModelDAO_HI.findPagination(querySql.toString(),countSql.toString(), params, pageIndex, pageRows);
    }
	
	/**
	 * 根据多个分类ID或KEY获取模型列表
	 * @author laoqunzhao 2018-04-28
	 * @param categoryIds 流程分类ID[]
	 * @param keys 流程key[]
	 * @param pageIndex
	 * @param pageRows
	 * @return Pagination<Model>
	 */
	@Override
	public Pagination<Model> findModels(List<Integer> categoryIds, List<String> keys, Integer pageIndex, Integer pageRows) {
        Pagination<Model> pagination = new Pagination<Model>(pageIndex, pageRows);
        try {
            StringBuffer sql = new StringBuffer(" from act_re_model arm");
            //流程分类查询
            if(categoryIds != null && !categoryIds.isEmpty()) {
                sql.append(" where arm.category_ in(select cat1.category_id from act_bpm_category cat1 left join act_bpm_category cat2 "
                        + " on cat1.category_code like concat(cat2.category_code,'%')"
                        + " where cat2.category_id in (" + StringUtils.join(categoryIds, ",") + "))");
            }
            //流程定义KEY查询
            if(keys != null && !keys.isEmpty()) {
                String keysStr = "";
                for(String key:keys) {
                    keysStr += ((keysStr.isEmpty()?"":",") + "'" + key + "'");
                }
                sql.append(categoryIds != null && !categoryIds.isEmpty()?" or ":" where ");
                sql.append(" arm.key_ in (" + keysStr + ")");
            }
            NativeModelQuery modelQuery = repositoryService.createNativeModelQuery().sql("select count(*) " + sql.toString());
            long  count = modelQuery.count();
            if(count>0) {
                modelQuery = repositoryService.createNativeModelQuery().sql("select arm.* " + sql.toString());
                List<Model> list = null;
                if(pageIndex==-1 || pageRows==-1) {
                    list = modelQuery.list();
                }else {
                    list = modelQuery.listPage((pageIndex-1)*pageRows, pageRows);
                }
                pagination.setData(list);
            }
            pagination.setCount(count);
        }catch(Exception e){
        	LOGGER.error(e.getMessage(), e);
            throw e;
        }
        return pagination;
    }

	
	/**
     * 根据分类获取所有的有效流程定义KEY
     * @author laoqunzhao 2018-04-28
     * @param categoryIds
     * @param processDefinitionKeys
     * @return
     */
	@Override
    public List<String> getProcessDefinitionKeys(List<Integer> categoryIds, List<String> processDefinitionKeys){
        Pagination<Model> pagination = findModels(categoryIds, processDefinitionKeys, 1, Integer.MAX_VALUE);
        List<Model> models = pagination.getData();
        if(models != null && !models.isEmpty()) {
            processDefinitionKeys = new ArrayList<String>();
            for(Model model : models) {
                processDefinitionKeys.add(model.getKey());
            }
            return processDefinitionKeys;
        }
        return null;
    }
	
	/**
     * 保存流程模型
     * @author laoqunzhao 2018-04-28
     * @param paramsJSON
     * {
     * modelId 模型ID
     * categoryId 流程分类ID
     * key 流程定义KEY
     * name 流程名称
     * description 描述说明
     * systemCode 系统代码
     * ouIds 事业部ID JSONArray
     * }
     */
	@Override
    public Model save(JSONObject paramsJSON) throws Exception {
        try {
        	SaafToolUtils.validateJsonParms(paramsJSON, "categoryId","key","name");
            String modelId = paramsJSON.getString("modelId");
            String key = paramsJSON.getString("key").toUpperCase().trim();
            Assert.isTrue(!modelKeyExist(modelId, key), "流程已存在！");
            Model model = StringUtils.isBlank(modelId)?null:repositoryService.getModel(modelId);
            if(model== null) {
            	//创建新模型
           	    model = create(paramsJSON, null);
            }else {
            	//修改模型信息
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode modelObjectNode = objectMapper.createObjectNode();
                modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, paramsJSON.getString("name"));
                modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, paramsJSON.getString("description"));
                model.setCategory(paramsJSON.getString("categoryId"));
                model.setMetaInfo(modelObjectNode.toString());
                model.setName(paramsJSON.getString("name"));
                repositoryService.saveModel(model);
                //重新分配权限
                JSONObject permission = new JSONObject();
                permission.put("processDefinitionKey", model.getKey());
                permission.put("systemCode", paramsJSON.getString("systemCode"));
                permission.put("ouIds", paramsJSON.getJSONArray("ouIds"));
                bpmPermissionServer.save(permission);
                //唯一校验
                validate(model, paramsJSON.getJSONArray("ouIds"));
            }
            //清除Redis
            clearRedis(model.getKey(), false);
            return model;
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
         	throw e;
        }
    }
	
	/**
     * 复制流程模型
     * @author laoqunzhao 2018-09-06
     * @param paramsJSON
     * {
     * modelId 模型ID
     * categoryId 流程分类ID
     * key 流程定义KEY
     * name 流程名称
     * description 描述说明
     * systemCode 系统代码
     * ouIds 事业部ID JSONArray
     * }
     */
	@Override
    @Transactional
    public Model copy(JSONObject paramsJSON) throws Exception {
        try {
        	SaafToolUtils.validateJsonParms(paramsJSON, "modelId","categoryId","key","name");
            String modelId = paramsJSON.getString("modelId");
            String key = paramsJSON.getString("key");
            Assert.isTrue(!modelKeyExist(null, key), "流程已存在！");
            Model modelData = repositoryService.getModel(modelId);
            Assert.notNull(modelData, "流程不存在！");
            BpmnJsonConverter converter = new BpmnJsonConverter();
            BpmnModel bpmnModel = getBpmnModel(modelData);
            byte[] bytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            String bpmXml = new String(bytes, "utf-8");
            StringReader sr = new StringReader(bpmXml);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(sr);
            Element root = doc.getRootElement();
            Element processElement = root.element("process");
            processElement.remove(processElement.attribute("id"));
            processElement.addAttribute("id", key);
            processElement.remove(processElement.attribute("name"));
            processElement.addAttribute("name", paramsJSON.getString("name"));
            // 创建转换对象
            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(doc.asXML().getBytes()), "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(in);
            BpmnModel bpmnModelNew = new BpmnXMLConverter().convertToBpmnModel(xtr);
            ObjectNode editorNode = converter.convertToJson(bpmnModelNew);
            Model model = create(paramsJSON, editorNode);
            //复制流程设置
            List<ActBpmTaskConfigEntity_HI> taskConfigs = bpmTaskConfigServer.findByProcessDefinitionKey(modelData.getKey());
            if(taskConfigs == null){
                taskConfigs = new ArrayList<ActBpmTaskConfigEntity_HI>();
            }
            List<String> taskDefinitionIds = new ArrayList<>();
            for(ActBpmTaskConfigEntity_HI taskConfig : taskConfigs){
                taskDefinitionIds.add(taskConfig.getTaskDefinitionId());
                List<ActBpmTaskProcesserEntity_HI> processers = bpmTaskConfigServer.getProcessersByConfigId(taskConfig.getConfigId());
                taskConfig.setProcessers(processers);
            }
            List<UserTask> userTasks = findUserTasks(modelData.getId(), null, false);
            if(userTasks != null && !userTasks.isEmpty()){
                for(UserTask userTask : userTasks){
                    if(!taskDefinitionIds.contains(userTask.getId())){
                        ActBpmTaskConfigEntity_HI taskConfig = bpmTaskConfigServer.findByDefinition(modelData.getKey(), userTask.getId(), true);
                        if(taskConfig != null){
                            taskConfigs.add(taskConfig);
                        }
                    }
                }
            }
            bpmTaskConfigServer.initTaskConfig(model.getId(), (JSONArray)JSON.toJSON(taskConfigs), -1);
            //清除Redis
            clearRedis(model.getKey(), false);
            return model;
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
         	throw e;
        }
    }



	/**
     * 复制流程模型
     * @author laoqunzhao 2018-09-06
     * @param paramsJSON
     * {
     * categoryId 流程分类ID
     * key 流程定义KEY
     * name 流程名称
     * description 描述说明
     * systemCode 系统代码
     * ouIds 事业部ID JSONArray
     * }
	 * @throws UnsupportedEncodingException
     */
	private Model create(JSONObject paramsJSON, ObjectNode editorNode) throws UnsupportedEncodingException {
		//创建新模型
        ObjectMapper objectMapper = new ObjectMapper();
        editorNode = editorNode != null? editorNode : objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.set("stencilset", stencilSetNode);
        Model model = repositoryService.newModel();
        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, paramsJSON.getString("name"));
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, paramsJSON.getString("description"));
        model.setCategory(paramsJSON.getString("categoryId"));
        model.setMetaInfo(modelObjectNode.toString());
        model.setName(paramsJSON.getString("name"));
        model.setKey(paramsJSON.getString("key"));
        repositoryService.saveModel(model);
        repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
        //分配权限
        JSONObject permission = new JSONObject();
        permission.put("processDefinitionKey", paramsJSON.getString("key"));
        permission.put("systemCode", paramsJSON.getString("systemCode"));
        permission.put("ouIds", paramsJSON.getJSONArray("ouIds"));
        bpmPermissionServer.save(permission);
        //唯一校验
        validate(model, paramsJSON.getJSONArray("ouIds"));
        //清除Redis
        clearRedis(model.getKey(), false);
        return model;
	}

    /**
     * 流程唯一校验
     * @author laoqunzhao 2018-09-06
     * @param model 流程模型
     * @param ouIdsJSON BU[]
     */
	private void validate(Model model, JSONArray ouIdsJSON){
        //澳优唯一校验
        ActBpmCategoryEntity_HI category = bpmCategoryServer.get(Integer.parseInt(model.getCategory()));
        Assert.notNull(category, "流程分类不存在！");
        Assert.isTrue(StringUtils.isNotBlank(category.getProcessKey()), "当前分类流程标识为空，不可创建流程！");
        if(StringUtils.isNotBlank(category.getProcessKey())) {
            for(int i = 0; i < ouIdsJSON.size(); i++) {
                List<ActBpmModelEntity_HI_RO> models = this.findByProcessKey(category.getProcessKey(), ouIdsJSON.getInteger(i));
                if(models != null && !models.isEmpty()){
                    Assert.isTrue(models.size() == 1 && models.get(0).getModelId().equals(model.getId()), "小类下一个BU只能添加一个流程！");
                }
                models = findByName(model.getName(), ouIdsJSON.getInteger(i));
                if(models != null && !models.isEmpty()){
                    Assert.isTrue(models.size() == 1 && models.get(0).getModelId().equals(model.getId()), "同BU下流程名称不能相同！");
                }
            }
        }
        //流程key唯一校验
        Assert.isTrue(!modelKeyExist(model.getId(), model.getKey()), "流程已存在！");
    }



    /**
     * 发布流程
     * @author laoqunzhao 2018-04-28
     * @param modelId 模型ID
     * @return 流程发布信息Deployment
     */
    @Override
    @Transactional
    public Deployment deploy(String modelId) throws Exception {
    	Assert.isTrue(StringUtils.isNotBlank(modelId), "参数错误");
    	Model modelData = repositoryService.getModel(modelId);
    	Assert.notNull(modelData, "流程不存在！");
    	BpmnModel bpmnModel = getBpmnModel(modelId);
    	Assert.notNull(bpmnModel, "该流程未设计流程图，发布失败！");
    	Assert.notEmpty(bpmnModel.getLocationMap(), "该流程未设计流程图，发布失败！");
    	//判断是否设置流程办理人
    	List<UserTask> userTasks = findUserTasks(modelId, null, false);
    	if(userTasks != null && userTasks.size()>1) {
    		UserTask firstUserTask = getFirstUserTask(bpmnModel);
    		List<ActBpmTaskConfigEntity_HI> emptyTaskConfigs = bpmTaskConfigServer.findEmptyProcesserTaskConfigs(modelData.getKey());
    		for(UserTask userTask: userTasks) {
    			if(userTask.getId().equals(firstUserTask.getId())) {
    				continue;
    			}
    			//流程定义没有设置办理人，根据流程设置判断是否存在办理人
    			if(StringUtils.isBlank(userTask.getAssignee()) && (userTask.getCandidateUsers() == null || userTask.getCandidateUsers().isEmpty())) {
    				boolean noProcessers = false;
    				if(emptyTaskConfigs != null && !emptyTaskConfigs.isEmpty()) {
    					for(ActBpmTaskConfigEntity_HI taskConfig: emptyTaskConfigs) {
    						if(StringUtils.equals(taskConfig.getTaskDefinitionId(), userTask.getId())) {
    							noProcessers = true;
    							break;
    						}
    					}
    				}
    				Assert.isTrue(!noProcessers, "流程节点" + userTask.getName() + "未设置审批人，发布失败！");
    			}
    		}
    	}    	
    	//取上一发布版本，与当前版本进行比较，判断有无改变
    	ProcessDefinition latestProcessDefinition = bpmProcessServer.findLatestProcess(modelData.getKey());
        //发布新版本
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
        		.name(modelData.getName())
        		.category(modelData.getCategory())
         		.addString(processName, new String(bpmnBytes,"utf-8"))
         		.deploy();
        
        ProcessDefinition newProcessDefinition = repositoryService
        		.createProcessDefinitionQuery()
        		.deploymentId(deployment.getId())
        		.singleResult();
        if(latestProcessDefinition != null) {
        	BpmnModel latestBpmnModel = repositoryService.getBpmnModel(latestProcessDefinition.getId());
        	BpmnModel newBpmnModel = repositoryService.getBpmnModel(newProcessDefinition.getId());
        	if(latestBpmnModel.getLocationMap() != null && !latestBpmnModel.getLocationMap().isEmpty()) {
        		byte[] latestBpmnBytes = new BpmnXMLConverter().convertToXML(latestBpmnModel);
        		byte[] newBpmnBytes = new BpmnXMLConverter().convertToXML(newBpmnModel);
        		Assert.isTrue(!Arrays.equals(latestBpmnBytes, newBpmnBytes),"流程定义没有变化，发布失败！");
        	}
        }
        //清除Redis
        clearRedis(modelData.getKey(), true);
        return deployment;
    }
    
    /**
     * 删除流程
     * @author laoqunzhao 2018-04-28
     * @param modelId 模型ID
     * @return 成功true 失败false
     */
    @Override
    public void delete(String modelId) throws Exception {
    	Model model = repositoryService.getModel(modelId);
        //级联删除流程
        if(model!=null) {
        	//已有流程发起不可删除
        	Assert.isTrue(!bpmListServer.hasSubmited(model.getKey()), "流程已发起不可删除！");
            List<ProcessDefinition> procList = bpmProcessServer.findDefinitions(model.getKey());
            if(procList!=null && !procList.isEmpty()) {
                for(ProcessDefinition procDef: procList) {
                    repositoryService.deleteDeployment(procDef.getDeploymentId(), true);
                }
            }
        }
        repositoryService.deleteModel(modelId);
        //删除流程设置
        bpmTaskConfigServer.destory(model.getKey());
        //清除Redis
        clearRedis(model.getKey(), true);
    }
	
	 
	/**
     * 根据流程模型ID获取所有的用户任务节点
     * @author laoqunzhao 2018-04-28
     * @param processDefinitionId 流程定义ID
     * @param modelId 流程模型ID,processDefinitionId不为空无须此参数
     * @param sort 是否排序
     * @return 用户任务节点集合List<UserTask>
	 * @throws Exception
     */
    @Override
    public List<UserTask> findUserTasks(String modelId, String processDefinitionId, boolean sort) throws Exception{
    	BpmnModel bpmnModel = StringUtils.isNotBlank(processDefinitionId)?
    			repositoryService.getBpmnModel(processDefinitionId): getBpmnModel(modelId);
        if(bpmnModel==null || bpmnModel.getMainProcess()==null) {
        	return null;
        }
        List<UserTask> userTaskList = new ArrayList<UserTask>();
        //获取所有节点并排序
        Collection<FlowElement> flowElements = sort?getSortedFollowElements(bpmnModel, null)
        		:bpmnModel.getMainProcess().getFlowElements();
        for(FlowElement e : flowElements) {
            if(e instanceof UserTask) {
            	userTaskList.add((UserTask)e);
            }
        }
        return userTaskList;
    }
    
    /**
     * 获取指定节点后的用户任务节点
     * @author laoqunzhao 2018-05-21
     * @param processDefinitionId 流程定义ID
     * @param modelId 流程模型ID,processDefinitionId不为空无须此参数
     * @return 用户任务节点集合List<UserTask>
	 * @throws Exception 
     */
    @Override
    public List<UserTask> findFollowUserTasks(String modelId, String processDefinitionId, String taskDefinitionId) throws Exception{
    	BpmnModel bpmnModel = StringUtils.isNotBlank(processDefinitionId)?
    			repositoryService.getBpmnModel(processDefinitionId): getBpmnModel(modelId);
    	if(bpmnModel == null || bpmnModel.getMainProcess() == null) {
        	return null;
        }
        List<UserTask> userTaskList = new ArrayList<UserTask>();
        //获取所有节点并排序
        List<FlowElement> flowElements = getSortedFollowElements(bpmnModel, taskDefinitionId);
        for(FlowElement e : flowElements) {
            if(e instanceof UserTask && !e.getId().equals(taskDefinitionId)) {
            	userTaskList.add((UserTask)e);
            }
        }
        return userTaskList;
    }
    
    /**
     * 获取指定节点的流入用户任务节点
     * @author laoqunzhao 2018-07-11
     * @param processDefinitionId 流程定义ID
     * @param activityId 节点ID
     * @return 用户任务节点集合List<UserTask>
     */
    @Override
    public List<UserTask> findInflowUserTasks(String processDefinitionId, String activityId){
    	if(StringUtils.isBlank(processDefinitionId) || StringUtils.isBlank(activityId)) {
    		return null;
    	}
    	BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
    	if(bpmnModel == null || bpmnModel.getMainProcess() == null) {
        	return null;
        }
    	List<UserTask> userTaskList = new ArrayList<UserTask>();
    	Collection<FlowElement> elementList = bpmnModel.getMainProcess().getFlowElements();         
        //所有非连接线节点Map
        Map<String, FlowElement> elementsMap = new HashMap<String, FlowElement>();
        List<SequenceFlow> flows = new ArrayList<SequenceFlow>();
        for(FlowElement element : elementList) {
        	if(element instanceof SequenceFlow) {
        		flows.add((SequenceFlow)element);
            }else {
            	elementsMap.put(element.getId(), element);
            }
        }
        List<String> targetIds = new ArrayList<String>();
        targetIds.add(activityId);
        for(int i=0; i<targetIds.size(); i++) {
        	for(SequenceFlow flow: flows) {
            	if(targetIds.get(i).equals(flow.getTargetRef())) {
            		FlowElement element = elementsMap.get(flow.getSourceRef());
            		if(element instanceof Gateway) {
            			targetIds.add(flow.getSourceRef());
            		}else if(element instanceof UserTask){
            			userTaskList.add((UserTask)element);
            		}
            	}
            }
        }
        return userTaskList;
    }
    
    /**
     * 根据流程定义KEY获取Gooflow格式模型JSON
     * @author laoqunzhao 2018-07-28
     * @param key 流程定义KEY
     * @return JSONObject
     * @throws Exception
     */
    @Override
    public JSONObject getGooflowModelJSON(String key) throws Exception {
    	if(StringUtils.isBlank(key)) {
    		return null;
    	}
        //从Redis中加载，加快速度
        try {
            String gooflowStr = jedisCluster.hget(WorkflowConstant.REDIS_PROC_MOD_GOOFLOW_JSON, key);
            if(StringUtils.isNotBlank(gooflowStr)) {
                return JSON.parseObject(gooflowStr);
            }
        }catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    	Model model = getByKey(key);
    	if(model == null) {
    		return null;
    	}
    	JSONObject gooflowJSON = getGooflowModelJSON(getBpmnModel(model));
        //存Redis
    	if(gooflowJSON != null){
            try {
                jedisCluster.hset(WorkflowConstant.REDIS_PROC_MOD_GOOFLOW_JSON, key, gooflowJSON.toString());
            }catch(Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return gooflowJSON;
    }
    
    /**
     * 根据bpmn模型获取Gooflow格式模型JSON
     * @author laoqunzhao 2018-07-28
     * @param bpmnModel bpmn模型
     * @return JSONObject
     * @throws Exception
     */
    @Override
    public JSONObject getGooflowModelJSON(BpmnModel bpmnModel) {
    	JSONObject processJSON = new JSONObject();
   	    if(bpmnModel == null || bpmnModel.getMainProcess() == null) {
       	    return processJSON;
        }
   	    JSONObject linesJSON = new JSONObject();
   	    JSONObject nodesJSON = new JSONObject();
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements(); 
        for (FlowElement e : flowElements) {
            //连线
            if (e instanceof SequenceFlow) {
            	SequenceFlow flow = (SequenceFlow)e;
            	JSONObject lineJSON = new JSONObject();
            	lineJSON.put("name", StringUtils.trimToEmpty(flow.getName()));
            	lineJSON.put("from", flow.getSourceRef());
            	lineJSON.put("to", flow.getTargetRef());
            	lineJSON.put("type", "sl");
            	lineJSON.put("M", null);
            	lineJSON.put("marked", false);
            	linesJSON.put(flow.getId(), lineJSON);
            	List<GraphicInfo> graphics = bpmnModel.getFlowLocationGraphicInfo(e.getId());
            	if(graphics != null && graphics.size()>1) {
            		lineJSON.put("x0", (int)graphics.get(0).getX());
            		lineJSON.put("y0", (int)graphics.get(0).getY());
            		lineJSON.put("x1", (int)graphics.get(graphics.size()-1).getX());
            		lineJSON.put("y1", (int)graphics.get(graphics.size()-1).getY());
            		if(graphics.size()>2) {//折线
            			lineJSON.put("type", "lr");
                    	lineJSON.put("M", (int)graphics.get(1).getX());
                    	lineJSON.put("mx0", (int)graphics.get(1).getX());
                    	lineJSON.put("mx1", (int)graphics.get(graphics.size()-2).getX());
                    	lineJSON.put("my0", (int)graphics.get(1).getY());
                    	lineJSON.put("my1", (int)graphics.get(graphics.size()-2).getY());
            		}
            	}
            }else {
            	JSONObject nodeJSON = new JSONObject();
            	GraphicInfo graphic = bpmnModel.getGraphicInfo(e.getId());
            	nodeJSON.put("name", StringUtils.trimToEmpty(e.getName()).replaceAll("[\\r|\\n]+","<br/>"));
            	nodeJSON.put("left", (int)graphic.getX());
            	nodeJSON.put("top", (int)graphic.getY());
            	nodeJSON.put("width", (int)graphic.getWidth());
            	nodeJSON.put("height", (int)graphic.getHeight());
            	if(e instanceof StartEvent) {
            		nodeJSON.put("type", "start");
            	}else if(e instanceof EndEvent) {
            		nodeJSON.put("type", "end");
            	}else if(e instanceof UserTask) {
            		nodeJSON.put("type", "task");
            		//org.activiti.bpmn.model
            	}else if(e instanceof Gateway) {
            		Gateway gateway = (Gateway)e;
            		if(gateway.getIncomingFlows() == null || gateway.getIncomingFlows().size()<=1) {
            			nodeJSON.put("type", "fork");
            		}else {
            			nodeJSON.put("type", "join");
            		}
            	}else if(e instanceof CallActivity){
            		nodeJSON.put("type", "activity");
            	}else if(e instanceof ServiceTask){
            		nodeJSON.put("type", "service");
            	}else {
            		nodeJSON.put("type", "node");
            	}
            	nodesJSON.put(e.getId(), nodeJSON);
            }
        }
        processJSON.put("lines", linesJSON);
        processJSON.put("nodes", nodesJSON);
        return processJSON;
    }
            
   
    

     
    /**
     * 判断流程模型KEY是否存在
     * @author laoqunzhao 2018-04-28
     * @param id 模型ID
     * @param key 模型KEY
     * @return
     */
    @Override
    public boolean modelKeyExist(String id, String key) {
        List<Model> modelList = repositoryService.createModelQuery().modelKey(key).list();
        if(modelList != null && !modelList.isEmpty()) {
            for(Model model:modelList) {
                if(!model.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 判断流程模型名称是否存在
     * @author laoqunzhao 2018-07-18
     * @param id 模型ID
     * @param name 模型名称
     * @return
     */
    @Override
    public boolean modelNameExist(String id, String name) {
        List<Model> modelList = repositoryService.createModelQuery().modelName(name).list();
        if(modelList != null && !modelList.isEmpty()) {
            for(Model model:modelList) {
                if(!model.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将流程导出到Document
     * @author laoqunzhao 2018-09-10
     * @param modelId 模型ID
     * @param processDefinitionId 流程定义ID
     * @return Document
     * @throws Exception
     */
    @Override
    @Transactional(readOnly=true)
    public Document exportModel(String modelId, String processDefinitionId) throws Exception {
        BpmnModel bpmnModel = StringUtils.isNotBlank(processDefinitionId)?
                repositoryService.getBpmnModel(processDefinitionId): getBpmnModel(modelId);
        if(bpmnModel==null || bpmnModel.getMainProcess() == null) {
            return null;
        }
        String processDefinitionKey = bpmnModel.getMainProcess().getId();
        byte[] bytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        String bpmXml = new String(bytes, "utf-8");
        StringReader sr = new StringReader(bpmXml);
        SAXReader reader = new SAXReader();
        Document doc = reader.read(sr);
        Element root = doc.getRootElement();
        //复制流程设置
        JSONObject paramsJSON = new JSONObject();
        paramsJSON.put("processDefinitionKey", processDefinitionKey);
        paramsJSON.put("processDefinitionId", processDefinitionId);
        paramsJSON.put("deleteFlag", 0);
        paramsJSON.put("readonly", true);
        List<ActBpmTaskConfigEntity_HI> taskConfigs = bpmTaskConfigServer.findTaskConfig(paramsJSON);
        if(taskConfigs != null && !taskConfigs.isEmpty()) {
            for (ActBpmTaskConfigEntity_HI taskConfig : taskConfigs) {
                List<ActBpmTaskProcesserEntity_HI> processers = bpmTaskConfigServer.getProcessersByConfigId(taskConfig.getConfigId());
                taskConfig.setProcessers(processers);
            }
        }
        Element processElement = root.element("process");
        List<Element> userTaskElements = processElement.elements("userTask");
        if(userTaskElements != null && !userTaskElements.isEmpty()){
            Element extensionElements = userTaskElements.get(0).addElement("extensionElements");
            Element taskConfigsEl = extensionElements.addElement("activiti:taskConfigs");
            taskConfigsEl.addCDATA(JSON.toJSONString(taskConfigs));
        }
        return doc;
    }

    /**
     * 流程导入，同时导入流程设置
     * @author laoqunzhao 2018-09-10
     *  @param paramsJSON
     * {
     * categoryId 流程分类ID
     * systemCode 系统代码
     * ouIds 事业部ID JSONArray
     * }
     */
    @Override
    @Transactional
    public Model importModel(JSONObject paramsJSON, String bpmXml) throws Exception {
        try {
            SaafToolUtils.validateJsonParms(paramsJSON, "categoryId", "systemCode", "ouIds");
            Assert.isTrue(StringUtils.isNotBlank(bpmXml), "流程定义为空，导入失败！");
            StringReader sr = new StringReader(bpmXml);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(sr);
            Element root = doc.getRootElement();
            Element processElement = root.element("process");
            Assert.notNull(processElement, "流程定义缺少节点process！");
            List<Element> userTaskElements = processElement.elements("userTask");
            //取扩展属性流程任务配置
            String taskConfigsStr = null;
            if(userTaskElements != null && !userTaskElements.isEmpty()){
                for(Element userTaskElement: userTaskElements){
                    Element extensionElement = userTaskElement.element("extensionElements");
                    if(extensionElement == null){
                        continue;
                    }
                    List<Element> els = extensionElement.elements();
                    if(els != null && !els.isEmpty()){
                        for(Element el : els){
                            if("activiti:taskConfigs".equals(el.getNamespacePrefix() + ":" + el.getName())){
                                taskConfigsStr = el.getText();
                                break;
                            }
                        }
                        if(org.apache.commons.lang.StringUtils.isNotBlank(taskConfigsStr)){
                            userTaskElement.remove(extensionElement);
                            break;
                        }
                    }
                }
            }
            // 创建转换对象
            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(doc.asXML().getBytes()), "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(in);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
            org.activiti.bpmn.model.Process process = bpmnModel.getMainProcess();
            Assert.notNull(process, "流程定义为空，导入失败！");
            BpmnJsonConverter converter = new BpmnJsonConverter();
            ObjectNode modelNode = converter.convertToJson(bpmnModel);
            String key = process.getId();
            Model model = getByKey(key);
            if(model == null){
                model = repositoryService.newModel();
                model.setKey(key);
                model.setName(process.getName());
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode modelObjectNode = objectMapper.createObjectNode();
                modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, process.getName());
                modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, process.getDocumentation());
                model.setMetaInfo(modelObjectNode.toString());
            }
            model.setCategory(paramsJSON.getString("categoryId"));
            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), modelNode.toString().getBytes("utf-8"));
            //分配权限
            JSONObject permission = new JSONObject();
            permission.put("processDefinitionKey", model.getKey());
            permission.put("systemCode", paramsJSON.getString("systemCode"));
            permission.put("ouIds", paramsJSON.getJSONArray("ouIds"));
            bpmPermissionServer.save(permission);
            //唯一校验
            validate(model, paramsJSON.getJSONArray("ouIds"));
            //复制流程设置
            if(StringUtils.isNotBlank(taskConfigsStr)){
                JSONArray taskConfigsJOSN = JSON.parseArray(taskConfigsStr);
                bpmTaskConfigServer.initTaskConfig(model.getId(), taskConfigsJOSN, -1);
            }
            //清除Redis
            clearRedis(model.getKey(), false);
            return model;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 由模型ID获取BPMNModel
     * @author laoqunzhao 2018-04-28
     * @param modelId 模型ID
     * @return BPMNModel
     * @throws Exception
     */
    @Override
    public BpmnModel getBpmnModel(String modelId)throws Exception {
    	Model model = repositoryService.getModel(modelId);
    	return getBpmnModel(model);
    }
    
    /**
     * 由模型ID获取BPMNModel
     * @author laoqunzhao 2018-04-28
     * @param model 模型
     * @return BPMNModel
     * @throws Exception
     */
    private BpmnModel getBpmnModel(Model model) throws Exception {
        if(model == null) {
        	return null;
        }
        ObjectNode modelNode = (ObjectNode) new ObjectMapper()
        		.readTree(new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		return bpmnModel;
    }

    /**
     * 获取一个节点后的所有节点并排序（包括其本身）
     * @author laoqunzhao 2018-05-21
     * @param bpmnModel BpmnModel
     * @param elementId 起始节点ID
     * @return 排序后的节点List<FlowElement>
     */
    @Override
    public List<FlowElement> getSortedFollowElements(BpmnModel bpmnModel, String elementId){
        Collection<FlowElement> elementList = bpmnModel.getMainProcess().getFlowElements();
        //开始节点变量
        StartEvent startEvent = null;
        //所有连接线Map
        Map<String,SequenceFlow> flowsMap = new HashMap<String,SequenceFlow>();
        //所有非连接线节点Map
        Map<String, FlowElement> elementsMap = new HashMap<String, FlowElement>();
        for(FlowElement element : elementList) {
            if(startEvent==null && element instanceof StartEvent) {
                startEvent = (StartEvent) element;
            }
            if(element instanceof SequenceFlow) {
                SequenceFlow flow = (SequenceFlow)element;
                flowsMap.put(flow.getId(), flow);
            }else {
                elementsMap.put(element.getId(), element);
            }
        }
        //存储节点ID与流出节点ID Map
        Map<String,List<String>> nextIdMap = new HashMap<String,List<String>>();
        //获取节点ID与流出节点ID关系
        linkNextId(nextIdMap, bpmnModel, startEvent.getId(), elementsMap, flowsMap);
        List<FlowElement> sortElements = new ArrayList<FlowElement>();
        if(elementId == null) {
            elementId = startEvent.getId();
        }
        List<String> sortIds = sortFollowElementIds(elementId, null, nextIdMap);
        for(String sortId : sortIds) {
            sortElements.add(elementsMap.get(sortId));
        }
        return sortElements;
    }
    
    /**
     * 对指定节点后面的指定ID进行排序
     * @author laoqunzhao 2018-05-21
     * @param elementId 节点ID
     * @param sortIds 排序后的节点ID
     * @param nextIdMap 获取节点ID与流出节点ID关系Map
     * @return
     */
    private List<String> sortFollowElementIds(String elementId, 
            List<String> sortIds, Map<String,List<String>> nextIdMap) {
        if(sortIds == null) {
            sortIds = new ArrayList<String>();
            sortIds.add(elementId);
        }
        List<String> nextIds = nextIdMap.get(elementId);
        if(nextIds != null && !nextIds.isEmpty()) {
            for(String nextId: nextIds) {
                if(!sortIds.contains(nextId)) {
                    sortIds.add(nextId);
                    sortFollowElementIds(nextId, sortIds, nextIdMap);
                }
                
            }
        }
        return sortIds;
    }
    
    /**
     * 将所有节点的下一层节点装入到Map中
     * @author laoqunzhao 2018-05-21
     * @param nextIdMap 下一节点存储Map
     * @param bpmModel BpmnModel
     * @param elementId 起始节点ID
     * @param elementsMap 所有节点
     * @param flowsMap 所有连接线
     */
    private void linkNextId(Map<String,List<String>> nextIdMap, BpmnModel bpmModel, String elementId, 
            Map<String, FlowElement> elementsMap, Map<String,SequenceFlow> flowsMap){
        List<FlowElement> nextElements = getNextElements(bpmModel, elementId, elementsMap, flowsMap);
        
        if(nextElements != null && !nextElements.isEmpty()) {
            List<String> nextIds = new ArrayList<String>();
            for(FlowElement element: nextElements) {
                nextIds.add(element.getId());
            }
            nextIdMap.put(elementId, nextIds);
            for(FlowElement element: nextElements) {
                if(!nextIdMap.containsKey(element.getId())) {
                    linkNextId(nextIdMap, bpmModel, element.getId(), elementsMap, flowsMap);
                }
            }
        }else {
            nextIdMap.put(elementId, null);
        }
    }
    
    
    /**
     * 获取下一层节点
     * @author laoqunzhao 2018-05-21
     * @param bpmModel BpmnModel
     * @param elementId 节点ID
     * @param elementsMap 所有节点
     * @param flowsMap 所有连接线
     * @return
     */
    private List<FlowElement> getNextElements(BpmnModel bpmModel, String elementId, 
            Map<String, FlowElement> elementsMap, Map<String,SequenceFlow> flowsMap){
        int redundance = 20;//用于比较节点位置避免人工操作造成误差的冗余量
        List<FlowElement> nextElements = new ArrayList<FlowElement>();
        for(String flowId : flowsMap.keySet()) {
            //连接线没有流程节点或非起始节点，直接跳过
            if(flowsMap.get(flowId).getSourceRef() == null || !flowsMap.get(flowId).getSourceRef().equals(elementId)) {
                continue;
            }
            //获取节点流出
            String nextId = flowsMap.get(flowId).getTargetRef();
            if(nextId == null || !elementsMap.containsKey(nextId)) {
                continue;
            }
            if(nextElements.isEmpty()) {
                //第一个节点直接添加
                nextElements.add(elementsMap.get(nextId));
            }else {
                //同级节点按Y坐标、X坐标排序
                FlowElement element = elementsMap.get(nextId);
                GraphicInfo graphic = bpmModel.getGraphicInfo(nextId);
                int x = (int)graphic.getX();
                int y = (int)graphic.getY();
                boolean added = false;
                //先比较Y坐标
                for(int i=0; i<nextElements.size(); i++) {
                    FlowElement element_ = nextElements.get(i);
                    GraphicInfo graphic_ = bpmModel.getGraphicInfo(element_.getId());
                    int y_ = (int)graphic_.getY();
                    if(y_ - y >= redundance) {
                        nextElements.add(i, element);
                        added = true;
                        break;
                    }else if(i == nextElements.size() - 1) {
                        if(y - y_ >= redundance) {
                            nextElements.add(element);
                            added = true;
                        }
                    }
                }
                //y坐标无法排序按x坐标排序
                if(!added) {
                    for(int i=0; i<nextElements.size(); i++) {
                        FlowElement element_ = nextElements.get(i);
                        GraphicInfo graphic_ = bpmModel.getGraphicInfo(element_.getId());
                        int x_ = (int)graphic_.getX();                        
                        if(x_ - x >= redundance) {
                            nextElements.add(i, element);
                            added = true;
                            break;
                        }else if(i == nextElements.size() - 1) {
                            if(x - x_ >= redundance) {
                                nextElements.add(element);
                                added = true;
                            }
                        }
                    }
                    if(!added) {
                        nextElements.add(element);
                    }
                }
            }
        }
        return nextElements;
    }
    
    private UserTask getFirstUserTask(BpmnModel bpmnModel) {
		StartEvent startEvent = null;
		Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
	    for (FlowElement e : flowElements) {
		    if (e instanceof StartEvent) {
		        startEvent = (StartEvent) e;
		        break;
		    }
	    }
	    if(startEvent == null) {
	    	return null;
	    }
		String targetRef = startEvent.getOutgoingFlows().get(0).getTargetRef();// 记录启动节点流向的任务节点Id
		for (FlowElement e : flowElements) {
			//迭代用户任务节点
			if (e instanceof UserTask) {
				UserTask userTask = (UserTask) e;
				if (userTask.getId().equals(targetRef)) {
					return userTask;
				}
			}
		}
		return null;
	}

    /**
     * 清除流程设置相关的缓存
     * @param processDefinitionKey 流程定义Key
     */
	private void clearRedis(String processDefinitionKey, boolean deleteDef){
        try{
            jedisCluster.del(WorkflowConstant.REDIS_PROC_OU_KEY);
            jedisCluster.del(WorkflowConstant.REDIS_PROC_DEF_FTASK_CFG);
            jedisCluster.del(WorkflowConstant.REDIS_PROC_TASK_CFG);
            if(StringUtils.isNotBlank(processDefinitionKey)){
                jedisCluster.hdel(WorkflowConstant.REDIS_PROC_FTASK_CFG, processDefinitionKey);
                jedisCluster.hdel(WorkflowConstant.REDIS_PROC_MOD_GOOFLOW_JSON, processDefinitionKey);
            }else{
                jedisCluster.del(WorkflowConstant.REDIS_PROC_FTASK_CFG);
                jedisCluster.del(WorkflowConstant.REDIS_PROC_MOD_GOOFLOW_JSON);
            }
            if(deleteDef){
                jedisCluster.del(WorkflowConstant.REDIS_PROC_DEF_GOOFLOW_JSON);
                if(StringUtils.isNotBlank(processDefinitionKey)){
                    jedisCluster.hdel(WorkflowConstant.REDIS_PROC_DEF_RUNNING, processDefinitionKey);
                }else{
                    jedisCluster.del(WorkflowConstant.REDIS_PROC_DEF_RUNNING);
                }
            }

        }catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 保存数据到Redis，有异常不抛出
     * @param key 键
     * @param field 字段
     * @param value 值
     */
    private void hset(String key, String field, Object value){
        try {
            String str = JSON.toJSONString(value);
            jedisCluster.hset(key, field, str);
        }catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

//    /**
//     * 从Redis中查询对象，有异常不抛出
//     * @param key 键
//     * @param field 字段
//     * @param clazz 对象类
//     * @return
//     */
//    private <T> T hget(String key, String field, Class<T> clazz){
//        try {
//            String str = jedisCluster.hget(key, field);
//            if(StringUtils.isNotBlank(str)) {
//                JSONObject strJSON = JSON.parseObject(str);
//                return JSON.toJavaObject(strJSON, clazz);
//            }
//        }catch(Exception e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//        return null;
//    }

    /**
     * 从Redis中查询对象，有异常不抛出
     * @param key 键
     * @param field 字段
     * @return
     */
    private String hget(String key, String field){
        try {
            return jedisCluster.hget(key, field);
        }catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


}
