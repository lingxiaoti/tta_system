package com.sie.saaf.common.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import java.util.List;

/**
 * TtaAttrCheckItemEntity_HI_RO Entity Object
 * Thu Jun 18 18:45:57 CST 2020  Auto Generate
 */

public class ContractCreateAndSendInfo_RO {
	/**
	 * 1.描述：未填写则默认发件人为“申
	 * 请了开发者的企业的主管
	 * 理员”。 如果填写了账号、
	 * 企业名称则以填写的为准
	 *
	 * 2.是否必填： 否
	 * 3.名称：发件人
	 */
	private ContractSender_RO sender;

	/**
	 * 1.描述：不能包含【； 】 ； [； ]， 否
	 * 则可能会导致短信通知失
	 * 败
	 *
	 * 2.是否必填： 是
	 * 3.名称：合同标题
	 */
	private String contractTitle;

	/**
	 * 1.描述：unix 时间戳的值。 格式为
	 * 毫秒 timestamp， 不能早
	 * 于当前发送时间
	 *
	 * 2.是否必填： 是
	 * 3.名称：签 约 截 止 日 期
	 */
	private String signDeadline;

	/**
	 * 1.描述：true 表示“receiver”中的
	 * routeOrder 字段生效， 各
	 * 个签署人是按 routeOrder
	 * 中的次序签署合同； false
	 * 表示没有先后顺序
	 *
	 * 2.是否必填： 是
	 * 3.名称：是 否 顺 序 签 署
	 */
	private Boolean signOrdered;

	/**
	 * 1.描述：无
	 *
	 * 2.是否必填： 是
	 * 3.名称：文 档 数 组
	 */
	private List<ContractDocuments_RO> documents;

	/**
	 * 1.描述：无
	 *
	 * 2.是否必填： 是
	 * 3.名称：文 档 数 组
	 */
	private List<ContractReceivers_RO> receivers;

	/**
	 * 1.描述：合同 类 型
	 *
	 * 2.是否必填： 否
	 * 3.名称：合 同 类 型
	 */
	private String contractType;

	/**
	 * 1.描述：点击返回的签署链接， 并
	 * 完成签署之后， 如果客户
	 * 输入了 returnUrl 参数， 则
	 * 会根据输入的 returnUrl 进
	 * 行跳转。 注： 签署完成、
	 * 或已撤销/已取消等完结状
	 * 态也会跳转。
	 *
	 * 2.是否必填： 否
	 * 3.名称：客 户 自 定 义 的 跳 转 链 接
	 */
	private String returnUrl;

	/**
	 * 1.描述：传入该参数， 则异步推送
	 * 消息改地址发送消息， 同
	 * 时配置开发者时的异步推
	 * 送地址也会正常推送。 除
	 * 了开发者固定的推送地址
	 * 以外， 每个合同同时最多
	 * 有一个 pusUrl， 上一个
	 * pushUrl 会被新传入的
	 * pushUrl 替代。 即： 传入
	 * 的 pushUrl 会影响接下来
	 * 合同的签署生命周期， 直
	 * 到在该签署生命周期中传
	 * 入了新的 pushUrl， 那么推
	 * 送会以新的 pushUrl 为推
	 * 送地址
	 *
	 * 2.是否必填： 否
	 * 3.名称：客 户 自 定 义 的 异 步 通 知 地 址
	 */
	private String pushUrl;

	/**
	 * 1.描述：默认为 false； true 表示隐藏拒签按钮， false 表示拒
	 * 签按钮不隐藏（支持控制
	 * 所有应用端）
	 *
	 * 2.是否必填： 否
	 * 3.名称：是 否 隐 藏 拒 签 按 钮
	 */
	private String hideRejectButton;

	/**
	 * 1.描述：审批流名称， 审批流名称
	 * 需要跟审批流需提前在企
	 * 业控制台-审批流管理中配
	 * 置， 否则无法触发审批。
	 *
	 * 2.是否必填： 否
	 * 3.名称：审 批 流 名 称
	 */
	private String approvalFlowName;

	/**
	 * 1.描述：用以对合同的描述， 可在
	 * SaaS 平台【企业控制台-业
	 * 务字段管理】 中增加描述
	 * 字段后进行使用。 合同描
	 * 述字段不会合成到合同中
	 * 去。
	 *
	 * 2.是否必填： 否
	 * 3.名称：合 同 描 述 字 段 集
	 * 4.示例：{fieldName：'',fieldValue:''}(是否必填否,类型 String)
	 */
	private JSONArray describeFields;

	public ContractSender_RO getSender() {
		return sender;
	}

	public void setSender(ContractSender_RO sender) {
		this.sender = sender;
	}

	public String getContractTitle() {
		return contractTitle;
	}

	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}

	public String getSignDeadline() {
		return signDeadline;
	}

	public void setSignDeadline(String signDeadline) {
		this.signDeadline = signDeadline;
	}

	public Boolean getSignOrdered() {
		return signOrdered;
	}

	public void setSignOrdered(Boolean signOrdered) {
		this.signOrdered = signOrdered;
	}

	public List<ContractDocuments_RO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<ContractDocuments_RO> documents) {
		this.documents = documents;
	}

	public List<ContractReceivers_RO> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<ContractReceivers_RO> receivers) {
		this.receivers = receivers;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getPushUrl() {
		return pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public String getHideRejectButton() {
		return hideRejectButton;
	}

	public void setHideRejectButton(String hideRejectButton) {
		this.hideRejectButton = hideRejectButton;
	}

	public String getApprovalFlowName() {
		return approvalFlowName;
	}

	public void setApprovalFlowName(String approvalFlowName) {
		this.approvalFlowName = approvalFlowName;
	}

	public JSONArray getDescribeFields() {
		return describeFields;
	}

	public void setDescribeFields(JSONArray describeFields) {
		this.describeFields = describeFields;
	}
}
