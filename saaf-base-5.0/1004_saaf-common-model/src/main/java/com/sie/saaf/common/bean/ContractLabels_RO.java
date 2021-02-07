package com.sie.saaf.common.bean;

/**
 * TtaAttrCheckItemEntity_HI_RO Entity Object
 * Thu Jun 18 18:45:57 CST 2020  Auto Generate
 */

public class ContractLabels_RO {
	/**
     * 1.描述：无
	 *
	 * 2.是否必填： 是
	 * 3.名称：标 签 放 置 页 码
	 */
	private Number pageNumber;

	/**
	 * 1.描述：印章显示的宽度
	 *
	 * 2.是否必填： 否
	 * 3.名称：印 章 宽 度
	 */
	private Number width;

	/**
	 * 1.描述：印章显示的高度
	 *
	 * 2.是否必填： 否
	 * 3.名称：印章高度
	 */
	private Number height;

	/**
	 * 1.描述：标签左下角到签约文件顶点页
	 * 面左下角之间的距离
	 *
	 * 2.是否必填： 是
	 * 3.名称：标 签 x 轴 位 置
	 */
	private Number x;

	/**
	 * 1.描述：标签左下角到签约文件顶点页
	 * 面左下角之间的距离
	 *
	 * 2.是否必填： 是
	 * 3.名称：标 签 y 轴 位 置
	 */
	private Number y;

	/**
	 * 1.描述：如果上传的是多文档， 需说明
	 * 签署位置对应于哪一份合同。
	 * 参见文档数组（documents）
	 * 相应说明
	 *
	 * 2.是否必填： 是
	 * 3.名称：对 应 上 传 的 第 几 份 文 档
	 */
	private Number documentOrder;

	/**
	 * 1.描述：SIGNATURE(签名图
	 * 片),DATE(签名日期),SEAL(印
	 * 章图片)，
	 * DECORATE_RIDING_SEAL（骑
	 * 缝章， 只认 y 坐标， x 坐标值
	 * 无效)
	 *
	 * 2.是否必填： 是
	 * 3.名称：标 签 的 类 型
	 */
	private String type;

	/**
	 * 1.描述：发件人账号必须是企业
	 * 成员账号， 不填则为开
	 * 发者账号所在企业的主
	 * 管理员账号。
	 *
	 * 2.是否必填： 否
	 * 3.名称：发件人所属企业
	 */

	public Number getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Number pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Number getWidth() {
		return width;
	}

	public void setWidth(Number width) {
		this.width = width;
	}

	public Number getHeight() {
		return height;
	}

	public void setHeight(Number height) {
		this.height = height;
	}

	public Number getX() {
		return x;
	}

	public void setX(Number x) {
		this.x = x;
	}

	public Number getY() {
		return y;
	}

	public void setY(Number y) {
		this.y = y;
	}

	public Number getDocumentOrder() {
		return documentOrder;
	}

	public void setDocumentOrder(Number documentOrder) {
		this.documentOrder = documentOrder;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
