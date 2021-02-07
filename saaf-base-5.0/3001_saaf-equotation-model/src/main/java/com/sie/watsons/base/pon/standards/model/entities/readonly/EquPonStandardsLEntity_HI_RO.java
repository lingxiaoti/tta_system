package com.sie.watsons.base.pon.standards.model.entities.readonly;

import java.math.BigDecimal;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonStandardsLEntity_HI_RO Entity Object
 * Tue Oct 08 09:39:43 CST 2019  Auto Generate
 */

public class EquPonStandardsLEntity_HI_RO {
    private Integer standardsLId;
    private Integer standardsId;
	private String lineNumber;
    private String gradingDivision;
    private String requirement;
	private String mark;
	private BigDecimal score;
	private BigDecimal fullScore;
    private Integer weight;
    private Integer lineLv;
    private Integer parentId;
    private String parentDivision;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private String attribute11;
    private String attribute12;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private Integer operatorUserId;

	public static void main(String[] args) {
		System.out.println(QUERY_LINE_SQL);
	}


	public static String QUERY_LINE_SQL= "select C.STANDARDS_L_ID standardsLId\n" +
			"      ,C.STANDARDS_ID standardsId\n" +
			"      ,C.GRADING_DIVISION gradingDivision\n" +
			"      ,C.REQUIREMENT requirement\n" +
			"      ,C.WEIGHT weight\n" +
			"      ,h.mark mark\n" +
			"      ,h.score score\n" +
			"      ,h.score fullScore\n" +
			"      ,level linelv\n" +
			"      ,C.PARENT_ID parentId\n" +
			"      ,(select sl.grading_division\n" +
			"        from   EQU_PON_STANDARDS_L sl\n" +
			"        where  sl.standards_l_id = c.parent_id) parentDivision\n" +
			"      ,C.CREATION_DATE creationDate\n" +
			"      ,C.CREATED_BY createdBy\n" +
			"from   EQU_PON_STANDARDS_L c\n" +
			"      ,EQU_PON_STANDARDS_H h\n" +
			"where  c.standards_id = :standardsId\n" +
			"and    c.standards_id = h.standards_id\n" +
			"start  with c.linelv = 1\n" +
			"connect by prior c.standards_L_id = c.parent_id";


	public void setStandardsLId(Integer standardsLId) {
		this.standardsLId = standardsLId;
	}


	public Integer getStandardsLId() {
		return standardsLId;
	}

	public void setStandardsId(Integer standardsId) {
		this.standardsId = standardsId;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getStandardsId() {
		return standardsId;
	}

	public String getGradingDivision() {
		return gradingDivision;
	}

	public void setGradingDivision(String gradingDivision) {
		this.gradingDivision = gradingDivision;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}


	public Integer getWeight() {
		return weight;
	}

	public void setLineLv(Integer lineLv) {
		this.lineLv = lineLv;
	}


	public Integer getLineLv() {
		return lineLv;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	
	public Integer getParentId() {
		return parentId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}



	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	
	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	
	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	
	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	
	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	
	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	
	public String getAttribute15() {
		return attribute15;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getParentDivision() {
		return parentDivision;
	}

	public void setParentDivision(String parentDivision) {
		this.parentDivision = parentDivision;
	}

	public BigDecimal getFullScore() {
		return fullScore;
	}

	public void setFullScore(BigDecimal fullScore) {
		this.fullScore = fullScore;
	}
}
