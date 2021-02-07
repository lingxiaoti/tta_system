package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmListEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IActBpmList {

	/**
	 * 流程发起查询
	 * @author laoqunzhao 2018-04-28
     * @param parameters JSONObject
	 * createdBy 发起人ID
	 * drafter 起草人
	 * listCode 流程编号
	 * listName 流程名称
	 * title 流程标题
	 * businessKey 业务主键
	 * billNo 业务申请单号
	 * categoryId 流程分类
	 * systemCode 系统代码
	 * processDefinitionKey 流程定义Key，条件=
	 * startDate 流程发起起始时间，格式yyyy-MM-dd
	 * endDate 流程发起截止时间，格式yyyy-MM-dd
	 * deleteFlag 删除标记，1.已删除，0.未删除
	 * communicated 发起沟通 Y.是   N.否
	 * exceptional 异常   Y.是   N.否
	 * status 流程状态  DRAFT.草稿   APPROVAL.审批中  ALLOW.审批通过  REFUSAL.审批驳回 CLOSED.已关闭
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
     * @return 流程发起数据集合Pagination<ActBpmListEntity_HI_RO>
	 * @throws Exception 
	 */
	Pagination<ActBpmListEntity_HI_RO> findBpmLists(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

	/**
	 * 根据ID查询申请单
	 * @author laoqunzhao 2018-04-28
     * @param listId 申请单ID
     * @return ActBpmListEntity_HI
	 */
    ActBpmListEntity_HI getById(Integer listId);

	/**
	 * 根据ID查询申请单
	 * @author laoqunzhao 2018-04-28
	 * @param listIds 申请单ID
	 * @return List<ActBpmListEntity_HI>
	 */
	List<ActBpmListEntity_HI_RO> findByIds(List<Integer> listIds);

    /**
	 * 根据流程实例ID查询申请单
	 * @author laoqunzhao 2018-04-28
     * @param processInstanceId 流程实例ID
     * @return ActBpmListEntity_HI
	 */
    ActBpmListEntity_HI getByProcessInstanceId(String processInstanceId);
    
    /**
	 * 根据流程实例ID查询申请单
	 * @author laoqunzhao 2018-04-28
     * @param processDefinitionKey 流程定义Key
	 * @param businessKey 业务主键
     * @return ActBpmListEntity_HI
	 */
    ActBpmListEntity_HI getByBusinessKey(String processDefinitionKey, String businessKey);

	/**
	 * 根据流程实例ID查询申请单
	 * @author laoqunzhao 2018-04-28
	 * @param paramsJSON
	 * {
	 *  listId 申请单ID
	 *  processInstanceId 流程实例ID
	 *  processDefinitionKey 流程定义Key/流程标识
	 *  businessKey 业务主键
	 *  ouId OU ID
	 *  responsibilityId 职责ID
	 * }
	 * @param
	 * @param userId 用户ID
	 * @return ActBpmListEntity_HI
	 */
	ActBpmListEntity_HI get(JSONObject paramsJSON, Integer userId);
    
    /**
	 * 保存申请单
	 * @author laoqunzhao 2018-04-28
	 * @param paramJSON JSON格式entity
	 * listId 申请单ID
	 * description 发起说明
	 * businessKey 业务主键
	 * processDefinitionKey 流程定义KEY
	 * variables 流程发起业务变量JSONArray
	 * [{
     * name: 变量名称,
     * type: 变量类型long/double/boolean/date/string,
     * value: 变量值
     *}，。。。]
	 * properties 流程发起流程表单JSONObject
	 * {字段名称:字段内容，。。。}
	 * title 流程发起标题
	 * categoryId 流程分类ID
	 * operatorUserId 操作人ID
	 */
    ActBpmListEntity_HI save(JSONObject paramJSON);
    
    /**
	 * 根据流程流转的情况更改流程属性
	 * @author laoqunzhao 2018-04-28
	 * @param instance ActBpmListEntity_HI
	 */
    void update(ActBpmListEntity_HI instance);

    /**
	 * 标记删除流程申请单
	 * @author laoqunzhao 2018-04-28
     * @param instance 申请单
	 */
    void delete(ActBpmListEntity_HI instance, ActIdUserEntity_RO user);

    /**
	 * 物理删除流程申请单
	 * @author laoqunzhao 2018-04-28
     * @param paramJSON JSONObject
     * listIds JSONArray 申请单ID
	 */
    void destory(JSONObject paramJSON);
    
    /**
	 * 判断某个流程下是否有申请单
	 * @author laoqunzhao 2018-04-28
	 * @param processDefinitionKey 流程定义KEY
	 * @return true：有，false：没有
	 */
    boolean hasSubmited(String processDefinitionKey);
    
    /**
     * 将申请单信息写入JSONArray结果 集
     * @author laoqunzhao 2018-04-28
     * @param array JSONArray结果 集
     */
    void appendBpmListInfo(JSONArray array);


	

}
