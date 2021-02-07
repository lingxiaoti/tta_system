package com.sie.saaf.common.bean;

/**
 * TtaAttrCheckItemEntity_HI_RO Entity Object
 * Thu Jun 18 18:45:57 CST 2020  Auto Generate
 */

public class ContractDocuments_RO {
	/**
     * 1.描述： 上传的元素文档的名称， 不是合同标题。 必须包含文件后缀
	 *
	 * 2.是否必填： 是
	 * 3.名称：文档名称
	 */
	private String fileName;

	/**
	 * 1.描述：文件内容用 BASE64 编码
	 *
	 * 2.是否必填： 是
	 * 3.名称：文 档 内 容
	 */
	private String content;

	/**
	 * 1.描述：Order 需为大于等于 0 的非负整
	 * 数。 用于支持多文档。 指定签署
	 * 位置时（即 receiver 中的
	 * labels） 需用到此字段
	 *
	 * 2.是否必填： 是
	 * 3.名称：文 档 顺 序
	 */
	private Number order;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Number getOrder() {
		return order;
	}

	public void setOrder(Number order) {
		this.order = order;
	}
}
