package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.dom4j.Document;

import java.util.List;

public interface IActBpmModel {

	/**
	 * 根据流程模型ID查询流程模型
	 * @param modelId 流程模型ID
	 * @return ActBpmModelEntity_HI_RO
	 */
	ActBpmModelEntity_HI_RO getById(String modelId);
	
	/**
	 * 获取流程模型
	 * @author laoqunzhao 2018-04-28
	 * @param key 模型KEY
	 * @return Model
	 */
	Model getByKey(String key);

	/**
	 * 根据流程标识查询流程(优先从Redis中查询)
	 * @author laoqunzhao 2018-09-04
	 * @param processKey 流程标识
	 * @param ouId 事业部ID
	 * @return List<ActBpmModelEntity_HI_RO>
	 */
	List<ActBpmModelEntity_HI_RO> findByProcessKeyInRedis(String processKey, Integer ouId);
	
	/**
	 * 根据流程标识查询流程
	 * @author laoqunzhao 2018-09-04
	 * @param processKey 流程标识
	 * @param ouId 事业部ID
	 * @return List<ActBpmModelEntity_HI_RO>
	 */
	List<ActBpmModelEntity_HI_RO> findByProcessKey(String processKey, Integer ouId);

	/**
	 * 根据流程标识及事业部查询流程
	 * @author laoqunzhao 2018-09-04
	 * @param processKey 流程标识
	 * @param ouId 事业部ID
	 * @return List<ActBpmModelEntity_HI_RO>
	 */
	ActBpmModelEntity_HI_RO findByProcessKeyAndOuId(String processKey, Integer ouId);
	
	/**
	 * 根据流程名称查询流程
	 * @author laoqunzhao 2018-09-04
	 * @param name 流程名称
	 * @param ouId 事业部ID
	 * @return List<ActBpmModelEntity_HI_RO>
	 */
	List<ActBpmModelEntity_HI_RO> findByName(String name, Integer ouId);


	/**
	 * 查询流程模型列表
	 * @author laoqunzhao 2018-04-28
	 * @param queryParamJSON
	 * {
	 * categoryId 流程分类ID
	 * name 名称(like)
	 * name_eq 名称(=)
	 * key KEY
	 * deployed 是否已部署
	 * systemCode 系统代码
	 * ouIds 事业部ID[]
	 * roleKey 角色KEY
	 * }
	 * @param pageIndex 页码索引
	 * @param pageRows 每页记录数
	 * @return
	 */
	Pagination<ActBpmModelEntity_HI_RO> findModels(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
	
	/**
	 * 根据多个分类ID或KEY获取模型列表
	 * @author laoqunzhao 2018-04-28
	 * @param categoryIds 流程分类ID[]
	 * @param keys 流程key[]
	 * @param pageIndex
	 * @param pageRows
	 * @return Pagination<Model>
	 */
	Pagination<Model> findModels(List<Integer> categoryIds, List<String> keys, Integer pageIndex, Integer pageRows);
	
	/**
     * 根据分类获取所有的有效流程定义KEY
     * @author laoqunzhao 2018-04-28
     * @param categoryIds
     * @param processDefinitionKeys
     * @return
     */
    List<String> getProcessDefinitionKeys(List<Integer> categoryIds, List<String> processDefinitionKeys);
    
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
    Model save(JSONObject paramsJSON) throws Exception;
    
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
	Model copy(JSONObject paramsJSON) throws Exception;
    
    /**
     * 发布流程
     * @author laoqunzhao 2018-04-28
     * @param modelId 模型ID
     * @return 流程发布信息Deployment
     */
	Deployment deploy(String modelId) throws Exception;
	
	/**
     * 删除流程
     * @author laoqunzhao 2018-04-28
     * @param modelId 模型ID
     */
	void delete(String modelId) throws Exception;
    	
	/**
     * 根据流程模型ID获取所有的用户任务节点
     * @author laoqunzhao 2018-04-28
     * @param processDefinitionId 流程定义ID
     * @param modelId 流程模型ID,processDefinitionId不为空无须此参数
     * @param sort 是否排序
     * @return
	 * @throws Exception 
     */
    List<UserTask> findUserTasks(String modelId, String processDefinitionId, boolean sort) throws Exception;
    
    /**
     * 获取指定节点后的用户任务节点
     * @author laoqunzhao 2018-05-21
     * @param processDefinitionId 流程定义ID
     * @param modelId 流程模型ID,processDefinitionId不为空无须此参数
     * @return 用户任务节点集合List<UserTask>
	 * @throws Exception 
     */
    List<UserTask> findFollowUserTasks(String modelId, String processDefinitionId,
									   String taskDefinitionId)throws Exception;
    
    /**
     * 获取指定节点的流入用户任务节点
     * @author laoqunzhao 2018-07-11
     * @param processDefinitionId 流程定义ID
     * @param activityId 节点ID
     * @return 用户任务节点集合List<UserTask>
     */
	List<UserTask> findInflowUserTasks(String processDefinitionId, String activityId);
    
    

	/**
     * 判断流程模型KEY是否存在
     * @author laoqunzhao 2018-04-28
     * @param id 模型ID
     * @param key 模型KEY
     * @return
     */
    boolean modelKeyExist(String id, String key);
    
    /**
     * 判断流程模型名称是否存在
     * @author laoqunzhao 2018-04-28
     * @param id 模型ID
     * @param name 模型名称
     * @return
     */
	boolean modelNameExist(String id, String name);
    
    /**
     * 将流程模型集合转换为JSONObejct
     * @author laoqunzhao 2018-04-28
     * @param model 流程模型
     * @param includeDeployment 是否包含流程发布信息
     * @return JSONObject格式的流程模型
     */
	//JSONObject toJsonObject(Model model, boolean includeDeployment);

	/**
     * 将流程模型集合转换为JSONArray
     * @author laoqunzhao 2018-04-28
     * @param models 流程模型集合
     * @param includeDeployment 是否包含流程发布信息
     * @return JSONArray格式的流程模型集合
     */
	//JSONArray toJsonArray(List<Model> models, boolean includeDeployment);

    /**
     * 根据流程定义KEY获取Gooflow格式模型JSON
     * @author laoqunzhao 2018-07-28
     * @param key 流程定义KEY
     * @return JSONObject
     * @throws Exception
     */
	JSONObject getGooflowModelJSON(String key) throws Exception;

	/**
     * 根据bpmn模型获取Gooflow格式模型JSON
     * @author laoqunzhao 2018-07-28
     * @param bpmnModel bpmn模型
     * @return JSONObject
     * @throws Exception
     */
	JSONObject getGooflowModelJSON(BpmnModel bpmnModel);

	/**
	 * 将流程导出到Document
	 * @author laoqunzhao 2018-09-10
	 * @param modelId 模型ID
	 * @param processDefinitionId 流程定义ID
	 * @return Document
	 * @throws Exception
	 */
	Document exportModel(String modelId, String processDefinitionId) throws Exception;

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
	Model importModel(JSONObject paramsJSON, String bpmXml) throws Exception;

	/**
	 * 由模型ID获取BPMNModel
	 * @author laoqunzhao 2018-04-28
	 * @param modelId 模型ID
	 * @return BPMNModel
	 * @throws Exception
	 */
	BpmnModel getBpmnModel(String modelId)throws Exception;

	/**
	 * 获取一个节点后的所有节点并排序（包括其本身）
	 * @author laoqunzhao 2018-05-21
	 * @param bpmnModel BpmnModel
	 * @param elementId 起始节点ID
	 * @return 排序后的节点List<FlowElement>
	 */
	List<FlowElement> getSortedFollowElements(BpmnModel bpmnModel, String elementId);

}
