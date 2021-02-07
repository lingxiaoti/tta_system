package com.sie.saaf.common.bean;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * TtaAttrCheckItemEntity_HI_RO Entity Object
 * Thu Jun 18 18:45:57 CST 2020  Auto Generate
 */

public class ContractReceivers_RO extends BaseContractReceivers_RO {
	/**
	 * 1.描述：PERSON(个人)，
	 * ENTERPRISE(企业)，
	 * 大写字母格式。 个人
	 * 用户使用签名签署合
	 * 同， 企业用户使用盖
	 * 章签署合同
	 *
	 * 2.是否必填： 是
	 * 3.名称：参 与 人 类 型
	 */
	private String userType;


	/**
     * 1.描述： 手机或邮箱。 设置
	 * ifProxyClaimer 为
	 * false 后此字段生效，
	 * 必须填写一个手机或
	 * 邮箱， 且不填写
	 * proxyClaimerAccount
	 * s 字段。
	 *
	 * 2.是否必填： 是
	 * 3.名称：参 与 人 账 号
	 */
	private String userAccount;


	/**
	 * 1.描述：serType 为个人， 填
	 * 写该字段后系统将校
	 * 验签约主体是否与填
	 * 写的姓名一致， 如果
	 * 不一致则短信通知发
	 * 件人。 userType 为企
	 * 业， 则不无上述校验
	 *
	 * 2.是否必填： 否
	 * 3.名称：参 与 人 姓 名
	 */
	private String userName;




	/**
	 * 1.true 表示必须手写签
	 * 名签署， false 表示不 生效。 只对个人签名
	 * 有效， 企业盖章不做
	 * 校验。 注意： “是否需
	 * 要强制其进行手绘签
	 * 署”与“不允许手写签
	 * 名”是互斥参数， 最多
	 * 只能有一个为 true
	 *
	 * 2.是否必填： 否
	 * 3.名称：是 否 需 要 强 制 其 进 行 手 绘 签 署
	 */
	private Boolean forceHandWrite;

	/**
	 * 1.true 表示必须不允许
	 * 手写签名， false 表示
	 * 不生效。 只对个人签
	 * 名有效， 企业盖章不
	 * 做校验。 注意： “是否
	 * 需要强制其进行手绘
	 * 签署”与“不允许手写
	 * 签名”是互斥参数， 最
	 * 多只能有一个为 true
	 *
	 * 2.是否必填： 否
	 * 3.名称：不 允 许 手 写 签 名
	 */
	private Boolean handWriteNotAllowed;

	/**
	 * 1.ture 表示必须已通过
	 * 上上签实名认证的用
	 * 户才能签署， 仅对
	 * SIGNER(签署人)有
	 * 效， CC_USER(抄送
	 * 人)不做校验
	 *
	 * 2.是否必填： 否
	 * 3.名称：是 否 需 要 对 方 实 名 认 证
	 */
	private Boolean requireIdentityAssurance;

	/**
	 * 1.ture 表示必须刷脸校
	 * 验， false 表示不生
	 * 效。 只对个人签名有
	 * 效， 企业盖章不做校
	 * 验。 注意： 是否需要
	 * 刷脸校验与优先刷
	 * 脸， 备用验证码校验
	 * 是互斥参数， 最有只
	 * 能有一个 true
	 *
	 * 2.是否必填： 否
	 * 3.名称：是 否 需 要 刷 脸 校 验
	 */
	private Boolean faceVerify;

	/**
	 * 1.true 表示必须优先刷脸， 备用验证码校
	 * 验， false 表示不生
	 * 效。 注意： 是否需要
	 * 刷脸校验与优先刷
	 * 脸， 备用验证码校验
	 * 是互斥参数， 最有只
	 * 能有一个 true
	 *
	 * 2.是否必填： 否
	 * 3.名称：优 先 刷 脸 ， 备 用 验 证 码 校 验
	 */
	private Boolean faceFirst;

	/**
	 * 1.SIGNER(签署人签署
	 * 合同),CC_USER(抄送
	 * 人查看合同)
	 *
	 * 2.是否必填： 是
	 * 3.名称：参 与 方 式
	 */
	private String receiverType;

	/**
	 * 1.在合同签署页面和合
	 * 同详情页中显示流程
	 *
	 * 2.是否必填： 否
	 * 3.名称：给 参 与 人 发 送 的 私 信
	 */
	private String privateLetter;

	/**
	 * 不支持负数。
	 * signOrdered 为
	 * true， 则各个参与人
	 * 按次序签署合同
	 *
	 * 2.是否必填： 是
	 * 3.名称：参 与 人 的 参 与 顺 序
	 */
	private Number routeOrder;

	/**
	 * 仅支持开发者签署自
	 * 己的合同
	 *
	 * 2.是否必填： 否
	 * 3.名称：是 否 发 送 之 后 ， 立即 签 署 ， 仅 允 许 签 署 自 己 相 关 的 签 名
	 */
	private Boolean signImmediately;

	/**
	 * 印章名称必须使用签
	 * 署人账号（即开发者
	 * 主管理员账号） 用户
	 * 中心-我的印章中的印
	 * 章名称， 不填写则表
	 * 示使用本账号默认印
	 * 章， 如账号无印章则
	 * 无法完成签署
	 *
	 * 2.是否必填： 否
	 * 3.名称：使 用 的 印 章 文 件 名 称
	 */
	private String signSealFileName;

	/**
	 * 标签（ 即 印 章 或 签 名 ） 数 组
	 *
	 * 2.是否必填： 是
	 * 3.名称：指定盖章或签名的页码、 位置
	 */
	private List<ContractLabels_RO> labels;

	/**
	 * 当填写了身份证号码
	 * 之后， 则会校验该身
	 * 份证号和名称是否一致， 如果不一致， 会
	 * 返回错误信息
	 *
	 * 2.是否必填： 否
	 * 3.名称：身 份 证号码
	 */
	private String idNumberForVerify;

	/**
	 * 1） 只有当参与方式
	 * （receiverType） 为
	 * SIGNER(签署人签署
	 * 合同)时， 本参数才可
	 * 生效； 2） 一份合同
	 * 中只有一个付费方，
	 * 当合同中设置了多个
	 * 付费方时， 进行报错
	 * 提醒； 3） 如果合同
	 * 中没有指定任何签署
	 * 人为合同付费方， 则
	 * 合同的付费方为合同
	 * 发送人
	 *
	 * 2.是否必填： 否
	 * 3.名称：是 否 是 合 同 付 费 方
	 */
	private Boolean contractPayer;

	/**
	 * 指企业签署时经办人
	 * 需实名认证。 true：
	 * 需要经办人实名认
	 * 证； false： 不需要经
	 * 办人实名认证， 默认
	 * 为 false
	 *
	 * 2.是否必填： 否
	 * 3.名称：是 否 需 要 经 办 人 实 名 认 证
	 */
	private Boolean requireEnterIdentityAssurance;

	/**
	 * 默认为 false， 合同直
	 * 接推送给指定的
	 * userAccount 字段填
	 * 写的账号； 如果设置
	 * 为 true（userAccount
	 * 需为空） ， 则该签署
	 * 人使用前台代收模
	 * 式， 即： 合同可由
	 * proxyClaimerAccount
	 * s 指定的上上签账号
	 * 中的任一个账号认领
	 * 合同
	 *
	 * 2.是否必填： 否
	 * 3.名称：是 否 是 企 业 前 台 代 收 模 式
	 */
	private Boolean ifProxyClaimer;

	/**
	 *是否开启强制阅读
	 *
	 * 2.是否必填： 否
	 * 3.名称：强制阅读
	 */
	private Boolean mustReadBeforeSign;

	/**
	 *默认为false不开启，true为开启，开启后则手写面板上手写后会进行手写笔迹与姓名的比对
	 *
	 * 2.是否必填： 否
	 * 3.名称：是否开启手写笔迹识别
	 */
	private Boolean handWritingRecognition;

	/**
	 * 允许上上签账号集中
	 * 的一个账号代表企业认领合同并继续签署
	 * 操作。 用于认领合同
	 * 的上上签账号需为账
	 * 号集合中的一个， 否
	 * 则将领取失败。 设置
	 * ifProxyClaimer 为
	 * true 后此字段生效。
	 * 注意 ：
	 * ifProxyClaimer 为
	 * true 且
	 * proxyClaimerAccount
	 * s 为空， 代表： 可使
	 * 用任意账号代表企业
	 * 认领合同。
	 * sender 参数
	 *
	 * 2.是否必填： 否
	 * 3.名称：上 上 签 账 号 集
	 */
	private JSONArray proxyClaimerAccounts;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getForceHandWrite() {
		return forceHandWrite;
	}

	public void setForceHandWrite(Boolean forceHandWrite) {
		this.forceHandWrite = forceHandWrite;
	}

	public Boolean getHandWriteNotAllowed() {
		return handWriteNotAllowed;
	}

	public void setHandWriteNotAllowed(Boolean handWriteNotAllowed) {
		this.handWriteNotAllowed = handWriteNotAllowed;
	}

	public Boolean getRequireIdentityAssurance() {
		return requireIdentityAssurance;
	}

	public void setRequireIdentityAssurance(Boolean requireIdentityAssurance) {
		this.requireIdentityAssurance = requireIdentityAssurance;
	}

	public Boolean getFaceVerify() {
		return faceVerify;
	}

	public void setFaceVerify(Boolean faceVerify) {
		this.faceVerify = faceVerify;
	}

	public Boolean getFaceFirst() {
		return faceFirst;
	}

	public void setFaceFirst(Boolean faceFirst) {
		this.faceFirst = faceFirst;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getPrivateLetter() {
		return privateLetter;
	}

	public void setPrivateLetter(String privateLetter) {
		this.privateLetter = privateLetter;
	}

	public Number getRouteOrder() {
		return routeOrder;
	}

	public void setRouteOrder(Number routeOrder) {
		this.routeOrder = routeOrder;
	}

	public Boolean getSignImmediately() {
		return signImmediately;
	}

	public void setSignImmediately(Boolean signImmediately) {
		this.signImmediately = signImmediately;
	}

	public String getSignSealFileName() {
		return signSealFileName;
	}

	public void setSignSealFileName(String signSealFileName) {
		this.signSealFileName = signSealFileName;
	}

	public List<ContractLabels_RO> getLabels() {
		return labels;
	}

	public void setLabels(List<ContractLabels_RO> labels) {
		this.labels = labels;
	}

	public String getIdNumberForVerify() {
		return idNumberForVerify;
	}

	public void setIdNumberForVerify(String idNumberForVerify) {
		this.idNumberForVerify = idNumberForVerify;
	}

	public Boolean getContractPayer() {
		return contractPayer;
	}

	public void setContractPayer(Boolean contractPayer) {
		this.contractPayer = contractPayer;
	}

	public Boolean getRequireEnterIdentityAssurance() {
		return requireEnterIdentityAssurance;
	}

	public void setRequireEnterIdentityAssurance(Boolean requireEnterIdentityAssurance) {
		this.requireEnterIdentityAssurance = requireEnterIdentityAssurance;
	}

	public Boolean getIfProxyClaimer() {
		return ifProxyClaimer;
	}

	public void setIfProxyClaimer(Boolean ifProxyClaimer) {
		this.ifProxyClaimer = ifProxyClaimer;
	}

	public JSONArray getProxyClaimerAccounts() {
		return proxyClaimerAccounts;
	}

	public void setProxyClaimerAccounts(JSONArray proxyClaimerAccounts) {
		this.proxyClaimerAccounts = proxyClaimerAccounts;
	}

	public Boolean getMustReadBeforeSign() {
		return mustReadBeforeSign;
	}

	public void setMustReadBeforeSign(Boolean mustReadBeforeSign) {
		this.mustReadBeforeSign = mustReadBeforeSign;
	}

	public Boolean getHandWritingRecognition() {
		return handWritingRecognition;
	}

	public void setHandWritingRecognition(Boolean handWritingRecognition) {
		this.handWritingRecognition = handWritingRecognition;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
