package com.sie.saaf.bpm.model.entities.readonly;


public class ActBpmCategoryEntity_HI_RO {
	
	public static final String QUERY_ALL_CATEGORY_SQL = "select \n" +
			"\t cate.category_id as categoryId,\n" +
			"\t cate.category_name as categoryName,\n" +
			"\t cate.category_code as categoryCode,\n" +
			"\t cate.parent_id as parentId,\n" +
			"\t cate.process_key as processKey\n" +
			"\t from act_bpm_category cate\n" +
			"\t where 1=1 ";
	
	private Integer categoryId; // 主键
	private String categoryName; // 分类名称
	private String categoryCode; // 分类编码
	private Integer parentId; // 上级分类
	private String processKey;//流程标识
	private boolean checked;
	
	public Integer getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Integer getParentId() {
        return parentId;
    }
    
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
