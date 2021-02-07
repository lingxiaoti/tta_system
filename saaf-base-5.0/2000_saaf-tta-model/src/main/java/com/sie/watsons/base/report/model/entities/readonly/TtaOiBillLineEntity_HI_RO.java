package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaOiBillLineEntity_HI_RO Entity Object
 * Tue Jul 16 17:45:15 CST 2019  Auto Generate
 */

public class TtaOiBillLineEntity_HI_RO {

    public static final String  SUM_MONEY ="select * from TTA_OI_BILL_LINE l where 1=1 \n" +
            "and l.RMS_CODE  =  '1067634101' \n" +
            "and to_char(l.ACCOUNT_MONTH,'yyyy') = 2019 \n" +
            "union \n" +
            "select * from TTA_OI_BILL_LINE o where 1=1 \n" +
            "and to_char(o.ACCOUNT_MONTH,'yyyy') = 2019 \n" +
            " and o.RMS_CODE  in ( \n" +
            "select s.REL_SUPPLIER_CODE from TTA_REL_SUPPLIER s \n" +
            "left join TTA_SUPPLIER p \n" +
            "on p.SUPPLIER_ID = s.REL_ID \n" +
            "where \n" +
            "p.SUPPLIER_CODE = '1067634101')";


	public static final String  QUERY ="select \n" +
			"\n" +
			"BDF001_CONTRACT_TEXT bdf001ContractText\n" +
			",NPD001_SUM_MONEY npd001SumMoney\n" +
			",NPD001_CONTRACT_TEXT npd001ContractText\n" +
			",HBI001_SUM_MONEY hbi001SumMoney\n" +
			",HBI001_CONTRACT_TEXT hbi001ContractText\n" +
			",DMI001_SUM_MONEY dmi001SumMoney\n" +
			",DMI001_CONTRACT_TEXT dmi001ContractText\n" +
			",CRI001_SUM_MONEY cri001SumMoney\n" +
			",CRI001_CONTRACT_TEXT cri001ContractText\n" +
			",DRO001_SUM_MONEY dro001SumMoney\n" +
			",DRO001_CONTRACT_TEXT dro001ContractText\n" +
			",DRB001_SUM_MONEY drb001SumMoney\n" +
			",DRB001_CONTRACT_TEXT drb001ContractText\n" +
			",DRH001SUMMONEY drh001summoney\n" +
			",DRH001_CONTRACT_TEXT drh001ContractText\n" +
			",DRG001_SUM_MONEY drg001SumMoney\n" +
			",DRG001_CONTRACT_TEXT drg001ContractText\n" +
			",NPM001_SUM_MONEY npm001SumMoney\n" +
			",NPM001_CONTRACT_DATA npm001ContractData\n" +
			",NPM001_CONTRACT_NUM npm001ContractNum\n" +
			",NPM001_CONTRACT_TEXT npm001ContractText\n" +
			",WDP001_SUM_MONEY wdp001SumMoney\n" +
			",WDP001_CONTRACT_TEXT wdp001ContractText\n" +
			",ASS001_SUM_MONEY ass001SumMoney\n" +
			",ASS001_CONTRACT_NUM ass001ContractNum\n" +
			",ASS001_CONTRACT_TEXT ass001ContractText\n" +
			",RSS001_SUM_MONEY rss001SumMoney\n" +
			",RSS001_CONTRACT_DATA rss001ContractData\n" +
			",RSS001_CONTRACT_NUM rss001ContractNum\n" +
			",RSS001_CONTRACT_TEXT rss001ContractText\n" +
			",NSS001_SUM_MONEY nss001SumMoney\n" +
			",NSS001_CONTRACT_DATA nss001ContractData\n" +
			",NSS001_CONTRACT_NUM nss001ContractNum\n" +
			",NSS001_CONTRACT_TEXT nss001ContractText\n" +
			",BDF_SUM_MONEY bdfSumMoney\n" +
			",BDF_CONTRACT_TEXT bdfContractText\n" +
			",ADTR_SUM_MONEY adtrSumMoney\n" +
			",ADTR_CONTRACT_TEXT adtrContractText\n" +
			",ADDG_SUM_MONEY addgSumMoney\n" +
			",ADDG_CONTRACT_TEXT addgContractText\n" +
			",VIR001_SUM_MONEY vir001SumMoney\n" +
			",VIR001_CONTRACT_TEXT vir001ContractText\n" +
			",VIR002_SUM_MONEY vir002SumMoney\n" +
			",VIR002_CONTRACT_TEXT vir002ContractText\n" +
			",ADDA_SUM_MONEY addaSumMoney\n" +
			",ADDA_CONTRACT_TEXT addaContractText\n" +
			",ADPF_SUM_MONEY adpfSumMoney\n" +
			",ADPF_CONTRACT_TEXT adpfContractText\n" +
			",EP_SUM_MONEY epSumMoney\n" +
			",EP_CONTRACT_TEXT epContractText\n" +
			",ADRB_SUM_MONEY adrbSumMoney\n" +
			",ADRB_CONTRACT_TEXT adrbContractText\n" +
			",NET_PURCHASE_ORIGIN netPurchaseOrigin\n" +
			",GOODS_RETURN_ORIGIN goodsReturnOrigin\n" +
			",PURCHASE_ORIGIN purchaseOrigin\n" +
			",DSD dsd\n" +
			",NET_PURCHASE netPurchase\n" +
			",GOODS_RETURN goodsReturn\n" +
			",PURCHASE purchase\n" +
			",VALID_BEGIN_DATE validBeginDate\n" +
			",FAMILY_PLANING_FLAG familyPlaningFlag\n" +
			",VENDER_TYPE venderType\n" +
			",COOPERATE_STATUS cooperateStatus\n" +
			",USER_CONTRACT_ID userContractId\n" +
			",VENDERAB venderab\n" +
			",BRAND brand\n" +
			",DEPARTMENT department\n" +
			",TC tc\n" +
			",BUYER buyer\n" +
			",INVOICENAME invoicename\n" +
			",IMPORTJV importjv\n" +
			",VENDER_NAME venderName\n" +
			",RMS_CODE rmsCode\n" +
			",TTA_OI_BILL_IMPORT_ID ttaOiBillImportId\n" +
			",ACCOUNT_MONTH accountMonth\n" +
			",VERSION_NUM versionNum\n" +
			",LAST_UPDATE_LOGIN lastUpdateLogin\n" +
			",LAST_UPDATE_DATE lastUpdateDate\n" +
			",LAST_UPDATED_BY lastUpdatedBy\n" +
			",CREATED_BY createdBy\n" +
			",CREATION_DATE creationDate\n" +
			",REMARK remark\n" +
			",AP_SUM_MONEY apSumMoney\n" +
			",CPS001_SUM_MONEY cps001SumMoney\n" +
			",CPS001_CONTRACT_TEXT cps001ContractText\n" +
			",PSF001_SUM_MONEY psf001SumMoney\n" +
			",PSF001_CONTRACT_TEXT psf001ContractText\n" +
			",OTF001_SUM_MONEY otf001SumMoney\n" +
			",OTF001_CONTRACT_TEXT otf001ContractText\n" +
			",UEP001_SUM_MONEY uep001SumMoney\n" +
			",UEP001_CONTRACT_TEXT uep001ContractText\n" +
			",BAC001_SUM_MONEY bac001SumMoney\n" +
			",BAC001_CONTRACT_TEXT bac001ContractText\n" +
			",LPU001_SUM_MONEY lpu001SumMoney\n" +
			",LPU001_CONTRACT_TEXT lpu001ContractText\n" +
			",VIP001_SUM_MONEY vip001SumMoney\n" +
			",VIP001_CONTRACT_TEXT vip001ContractText\n" +
			",COU001_SUM_MONEY cou001SumMoney\n" +
			",COU001_CONTRACT_TEXT cou001ContractText\n" +
			",NFP001_SUM_MONEY nfp001SumMoney\n" +
			",NFP001_CONTRACT_TEXT nfp001ContractText\n" +
			",LDP001_CONTRACT_TEXT ldp001ContractText\n" +
			",LDP001_SUM_MONEY ldp001SumMoney\n" +
			",NTI001_SUM_MONEY nti001SumMoney\n" +
			",NTI001_CONTRACT_TEXT nti001ContractText\n" +
			",OTH001_SUM_MONEY oth001SumMoney\n" +
			",OTH001_CONTRACT_TEXT oth001ContractText\n" +
			",COS001_SUM_MONEY cos001SumMoney\n" +
			",COS001_CONTRACT_TEXT cos001ContractText\n" +
			",BDF001_SUM_MONEY bdf001SumMoney\n" +
			"\n" +
			"\n" +
			"from TTA_OI_BILL_LINE TOB where 1=1 ";

	@JSONField(format="yyyy-MM")
	public Date accountMonth;
	public Integer ttaOiBillImportId;
	public String rmsCode;
	public String venderName;
	public String importjv;
	public String invoicename;
	public String buyer;
	public String tc;
	public String department;
	public String brand;
	public String venderab;
	public String userContractId;
	public String cooperateStatus;
	public String venderType;
	public String familyPlaningFlag;
	@JSONField(format="yyyy-MM-dd")
	public Date validBeginDate;
	public String purchase;
	public String goodsReturn;
	public String netPurchase;
	public String dsd;
	public String purchaseOrigin;
	public String goodsReturnOrigin;
	public String netPurchaseOrigin;
	public String adrbContractText;
	public String adrbSumMoney;
	public String epContractText;
	public String epSumMoney;
	public String adpfContractText;
	public String adpfSumMoney;
	public String addaContractText;
	public String addaSumMoney;
	public String vir002ContractText;
	public String vir002SumMoney;
	public String vir001ContractText;
	public String vir001SumMoney;
	public String addgContractText;
	public String addgSumMoney;
	public String adtrContractText;
	public String adtrSumMoney;
	public String bdfContractText;
	public String bdfSumMoney;
	public String nss001ContractText;
	public String nss001ContractNum;
	public String nss001ContractData;
	public String nss001SumMoney;
	public String rss001ContractText;
	public String rss001ContractNum;
	public String rss001ContractData;
	public String rss001SumMoney;
	public String ass001ContractText;
	public String ass001ContractNum;
	public String ass001SumMoney;
	public String wdp001ContractText;
	public String wdp001SumMoney;
	public String npm001ContractText;
	public String npm001ContractNum;
	public String npm001ContractData;
	public String npm001SumMoney;
	public String drg001ContractText;
	public String drg001SumMoney;
	public String drh001ContractText;
	public String drh001summoney;
	public String drb001ContractText;
	public String drb001SumMoney;
	public String dro001ContractText;
	public String dro001SumMoney;
	public String cri001ContractText;
	public String cri001SumMoney;
	public String dmi001ContractText;
	public String dmi001SumMoney;
	public String hbi001ContractText;
	public String hbi001SumMoney;
	public String npd001ContractText;
	public String npd001SumMoney;
	public String bdf001ContractText;
	public String bdf001SumMoney;
	public String cos001ContractText;
	public String cos001SumMoney;
	public String oth001ContractText;
	public String oth001SumMoney;
	public String nti001ContractText;
	public String nti001SumMoney;
	public String ldp001SumMoney;
	public String ldp001ContractText;
	public String nfp001ContractText;
	public String nfp001SumMoney;
	public String cou001ContractText;
	public String cou001SumMoney;
	public String vip001ContractText;
	public String vip001SumMoney;
	public String lpu001ContractText;
	public String lpu001SumMoney;
	public String bac001ContractText;
	public String bac001SumMoney;
	public String uep001ContractText;
	public String uep001SumMoney;
	public String otf001ContractText;
	public String otf001SumMoney;
	public String psf001ContractText;
	public String psf001SumMoney;
	public String cps001ContractText;
	public String cps001SumMoney;
	public String apSumMoney;
	public String remark;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date creationDate;
	public Integer createdBy;
	public Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date lastUpdateDate;
	public Integer lastUpdateLogin;
	public Integer versionNum;
	public Integer operatorUserId;

	public void setAccountMonth(Date accountMonth) {
		this.accountMonth = accountMonth;
	}


	public Date getAccountMonth() {
		return accountMonth;
	}

	public void setTtaOiBillImportId(Integer ttaOiBillImportId) {
		this.ttaOiBillImportId = ttaOiBillImportId;
	}


	public Integer getTtaOiBillImportId() {
		return ttaOiBillImportId;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}


	public String getRmsCode() {
		return rmsCode;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}


	public String getVenderName() {
		return venderName;
	}

	public void setImportjv(String importjv) {
		this.importjv = importjv;
	}


	public String getImportjv() {
		return importjv;
	}

	public void setInvoicename(String invoicename) {
		this.invoicename = invoicename;
	}


	public String getInvoicename() {
		return invoicename;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}


	public String getBuyer() {
		return buyer;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}


	public String getTc() {
		return tc;
	}

	public void setDepartment(String department) {
		this.department = department;
	}


	public String getDepartment() {
		return department;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getBrand() {
		return brand;
	}

	public void setVenderab(String venderab) {
		this.venderab = venderab;
	}


	public String getVenderab() {
		return venderab;
	}

	public void setUserContractId(String userContractId) {
		this.userContractId = userContractId;
	}


	public String getUserContractId() {
		return userContractId;
	}

	public void setCooperateStatus(String cooperateStatus) {
		this.cooperateStatus = cooperateStatus;
	}


	public String getCooperateStatus() {
		return cooperateStatus;
	}

	public void setVenderType(String venderType) {
		this.venderType = venderType;
	}


	public String getVenderType() {
		return venderType;
	}

	public void setFamilyPlaningFlag(String familyPlaningFlag) {
		this.familyPlaningFlag = familyPlaningFlag;
	}


	public String getFamilyPlaningFlag() {
		return familyPlaningFlag;
	}

	public void setValidBeginDate(Date validBeginDate) {
		this.validBeginDate = validBeginDate;
	}


	public Date getValidBeginDate() {
		return validBeginDate;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}


	public String getPurchase() {
		return purchase;
	}

	public void setGoodsReturn(String goodsReturn) {
		this.goodsReturn = goodsReturn;
	}


	public String getGoodsReturn() {
		return goodsReturn;
	}

	public void setNetPurchase(String netPurchase) {
		this.netPurchase = netPurchase;
	}


	public String getNetPurchase() {
		return netPurchase;
	}

	public void setDsd(String dsd) {
		this.dsd = dsd;
	}


	public String getDsd() {
		return dsd;
	}

	public void setPurchaseOrigin(String purchaseOrigin) {
		this.purchaseOrigin = purchaseOrigin;
	}


	public String getPurchaseOrigin() {
		return purchaseOrigin;
	}

	public void setGoodsReturnOrigin(String goodsReturnOrigin) {
		this.goodsReturnOrigin = goodsReturnOrigin;
	}


	public String getGoodsReturnOrigin() {
		return goodsReturnOrigin;
	}

	public void setNetPurchaseOrigin(String netPurchaseOrigin) {
		this.netPurchaseOrigin = netPurchaseOrigin;
	}


	public String getNetPurchaseOrigin() {
		return netPurchaseOrigin;
	}

	public void setAdrbContractText(String adrbContractText) {
		this.adrbContractText = adrbContractText;
	}


	public String getAdrbContractText() {
		return adrbContractText;
	}

	public void setAdrbSumMoney(String adrbSumMoney) {
		this.adrbSumMoney = adrbSumMoney;
	}


	public String getAdrbSumMoney() {
		return adrbSumMoney;
	}

	public void setEpContractText(String epContractText) {
		this.epContractText = epContractText;
	}


	public String getEpContractText() {
		return epContractText;
	}

	public void setEpSumMoney(String epSumMoney) {
		this.epSumMoney = epSumMoney;
	}


	public String getEpSumMoney() {
		return epSumMoney;
	}

	public void setAdpfContractText(String adpfContractText) {
		this.adpfContractText = adpfContractText;
	}


	public String getAdpfContractText() {
		return adpfContractText;
	}

	public void setAdpfSumMoney(String adpfSumMoney) {
		this.adpfSumMoney = adpfSumMoney;
	}


	public String getAdpfSumMoney() {
		return adpfSumMoney;
	}

	public void setAddaContractText(String addaContractText) {
		this.addaContractText = addaContractText;
	}


	public String getAddaContractText() {
		return addaContractText;
	}

	public void setAddaSumMoney(String addaSumMoney) {
		this.addaSumMoney = addaSumMoney;
	}


	public String getAddaSumMoney() {
		return addaSumMoney;
	}

	public void setVir002ContractText(String vir002ContractText) {
		this.vir002ContractText = vir002ContractText;
	}


	public String getVir002ContractText() {
		return vir002ContractText;
	}

	public void setVir002SumMoney(String vir002SumMoney) {
		this.vir002SumMoney = vir002SumMoney;
	}


	public String getVir002SumMoney() {
		return vir002SumMoney;
	}

	public void setVir001ContractText(String vir001ContractText) {
		this.vir001ContractText = vir001ContractText;
	}


	public String getVir001ContractText() {
		return vir001ContractText;
	}

	public void setVir001SumMoney(String vir001SumMoney) {
		this.vir001SumMoney = vir001SumMoney;
	}


	public String getVir001SumMoney() {
		return vir001SumMoney;
	}

	public void setAddgContractText(String addgContractText) {
		this.addgContractText = addgContractText;
	}


	public String getAddgContractText() {
		return addgContractText;
	}

	public void setAddgSumMoney(String addgSumMoney) {
		this.addgSumMoney = addgSumMoney;
	}


	public String getAddgSumMoney() {
		return addgSumMoney;
	}

	public void setAdtrContractText(String adtrContractText) {
		this.adtrContractText = adtrContractText;
	}


	public String getAdtrContractText() {
		return adtrContractText;
	}

	public void setAdtrSumMoney(String adtrSumMoney) {
		this.adtrSumMoney = adtrSumMoney;
	}


	public String getAdtrSumMoney() {
		return adtrSumMoney;
	}

	public void setBdfContractText(String bdfContractText) {
		this.bdfContractText = bdfContractText;
	}


	public String getBdfContractText() {
		return bdfContractText;
	}

	public void setBdfSumMoney(String bdfSumMoney) {
		this.bdfSumMoney = bdfSumMoney;
	}


	public String getBdfSumMoney() {
		return bdfSumMoney;
	}

	public void setNss001ContractText(String nss001ContractText) {
		this.nss001ContractText = nss001ContractText;
	}


	public String getNss001ContractText() {
		return nss001ContractText;
	}

	public void setNss001ContractNum(String nss001ContractNum) {
		this.nss001ContractNum = nss001ContractNum;
	}


	public String getNss001ContractNum() {
		return nss001ContractNum;
	}

	public void setNss001ContractData(String nss001ContractData) {
		this.nss001ContractData = nss001ContractData;
	}


	public String getNss001ContractData() {
		return nss001ContractData;
	}

	public void setNss001SumMoney(String nss001SumMoney) {
		this.nss001SumMoney = nss001SumMoney;
	}


	public String getNss001SumMoney() {
		return nss001SumMoney;
	}

	public void setRss001ContractText(String rss001ContractText) {
		this.rss001ContractText = rss001ContractText;
	}


	public String getRss001ContractText() {
		return rss001ContractText;
	}

	public void setRss001ContractNum(String rss001ContractNum) {
		this.rss001ContractNum = rss001ContractNum;
	}


	public String getRss001ContractNum() {
		return rss001ContractNum;
	}

	public void setRss001ContractData(String rss001ContractData) {
		this.rss001ContractData = rss001ContractData;
	}


	public String getRss001ContractData() {
		return rss001ContractData;
	}

	public void setRss001SumMoney(String rss001SumMoney) {
		this.rss001SumMoney = rss001SumMoney;
	}


	public String getRss001SumMoney() {
		return rss001SumMoney;
	}

	public void setAss001ContractText(String ass001ContractText) {
		this.ass001ContractText = ass001ContractText;
	}


	public String getAss001ContractText() {
		return ass001ContractText;
	}

	public void setAss001ContractNum(String ass001ContractNum) {
		this.ass001ContractNum = ass001ContractNum;
	}


	public String getAss001ContractNum() {
		return ass001ContractNum;
	}

	public void setAss001SumMoney(String ass001SumMoney) {
		this.ass001SumMoney = ass001SumMoney;
	}


	public String getAss001SumMoney() {
		return ass001SumMoney;
	}

	public void setWdp001ContractText(String wdp001ContractText) {
		this.wdp001ContractText = wdp001ContractText;
	}


	public String getWdp001ContractText() {
		return wdp001ContractText;
	}

	public void setWdp001SumMoney(String wdp001SumMoney) {
		this.wdp001SumMoney = wdp001SumMoney;
	}


	public String getWdp001SumMoney() {
		return wdp001SumMoney;
	}

	public void setNpm001ContractText(String npm001ContractText) {
		this.npm001ContractText = npm001ContractText;
	}


	public String getNpm001ContractText() {
		return npm001ContractText;
	}

	public void setNpm001ContractNum(String npm001ContractNum) {
		this.npm001ContractNum = npm001ContractNum;
	}


	public String getNpm001ContractNum() {
		return npm001ContractNum;
	}

	public void setNpm001ContractData(String npm001ContractData) {
		this.npm001ContractData = npm001ContractData;
	}


	public String getNpm001ContractData() {
		return npm001ContractData;
	}

	public void setNpm001SumMoney(String npm001SumMoney) {
		this.npm001SumMoney = npm001SumMoney;
	}


	public String getNpm001SumMoney() {
		return npm001SumMoney;
	}

	public void setDrg001ContractText(String drg001ContractText) {
		this.drg001ContractText = drg001ContractText;
	}


	public String getDrg001ContractText() {
		return drg001ContractText;
	}

	public void setDrg001SumMoney(String drg001SumMoney) {
		this.drg001SumMoney = drg001SumMoney;
	}


	public String getDrg001SumMoney() {
		return drg001SumMoney;
	}

	public void setDrh001ContractText(String drh001ContractText) {
		this.drh001ContractText = drh001ContractText;
	}


	public String getDrh001ContractText() {
		return drh001ContractText;
	}

	public void setDrh001summoney(String drh001summoney) {
		this.drh001summoney = drh001summoney;
	}


	public String getDrh001summoney() {
		return drh001summoney;
	}

	public void setDrb001ContractText(String drb001ContractText) {
		this.drb001ContractText = drb001ContractText;
	}


	public String getDrb001ContractText() {
		return drb001ContractText;
	}

	public void setDrb001SumMoney(String drb001SumMoney) {
		this.drb001SumMoney = drb001SumMoney;
	}


	public String getDrb001SumMoney() {
		return drb001SumMoney;
	}

	public void setDro001ContractText(String dro001ContractText) {
		this.dro001ContractText = dro001ContractText;
	}


	public String getDro001ContractText() {
		return dro001ContractText;
	}

	public void setDro001SumMoney(String dro001SumMoney) {
		this.dro001SumMoney = dro001SumMoney;
	}


	public String getDro001SumMoney() {
		return dro001SumMoney;
	}

	public void setCri001ContractText(String cri001ContractText) {
		this.cri001ContractText = cri001ContractText;
	}


	public String getCri001ContractText() {
		return cri001ContractText;
	}

	public void setCri001SumMoney(String cri001SumMoney) {
		this.cri001SumMoney = cri001SumMoney;
	}


	public String getCri001SumMoney() {
		return cri001SumMoney;
	}

	public void setDmi001ContractText(String dmi001ContractText) {
		this.dmi001ContractText = dmi001ContractText;
	}


	public String getDmi001ContractText() {
		return dmi001ContractText;
	}

	public void setDmi001SumMoney(String dmi001SumMoney) {
		this.dmi001SumMoney = dmi001SumMoney;
	}


	public String getDmi001SumMoney() {
		return dmi001SumMoney;
	}

	public void setHbi001ContractText(String hbi001ContractText) {
		this.hbi001ContractText = hbi001ContractText;
	}


	public String getHbi001ContractText() {
		return hbi001ContractText;
	}

	public void setHbi001SumMoney(String hbi001SumMoney) {
		this.hbi001SumMoney = hbi001SumMoney;
	}


	public String getHbi001SumMoney() {
		return hbi001SumMoney;
	}

	public void setNpd001ContractText(String npd001ContractText) {
		this.npd001ContractText = npd001ContractText;
	}


	public String getNpd001ContractText() {
		return npd001ContractText;
	}

	public void setNpd001SumMoney(String npd001SumMoney) {
		this.npd001SumMoney = npd001SumMoney;
	}


	public String getNpd001SumMoney() {
		return npd001SumMoney;
	}

	public void setBdf001ContractText(String bdf001ContractText) {
		this.bdf001ContractText = bdf001ContractText;
	}


	public String getBdf001ContractText() {
		return bdf001ContractText;
	}

	public void setBdf001SumMoney(String bdf001SumMoney) {
		this.bdf001SumMoney = bdf001SumMoney;
	}


	public String getBdf001SumMoney() {
		return bdf001SumMoney;
	}

	public void setCos001ContractText(String cos001ContractText) {
		this.cos001ContractText = cos001ContractText;
	}


	public String getCos001ContractText() {
		return cos001ContractText;
	}

	public void setCos001SumMoney(String cos001SumMoney) {
		this.cos001SumMoney = cos001SumMoney;
	}


	public String getCos001SumMoney() {
		return cos001SumMoney;
	}

	public void setOth001ContractText(String oth001ContractText) {
		this.oth001ContractText = oth001ContractText;
	}


	public String getOth001ContractText() {
		return oth001ContractText;
	}

	public void setOth001SumMoney(String oth001SumMoney) {
		this.oth001SumMoney = oth001SumMoney;
	}


	public String getOth001SumMoney() {
		return oth001SumMoney;
	}

	public void setNti001ContractText(String nti001ContractText) {
		this.nti001ContractText = nti001ContractText;
	}


	public String getNti001ContractText() {
		return nti001ContractText;
	}

	public void setNti001SumMoney(String nti001SumMoney) {
		this.nti001SumMoney = nti001SumMoney;
	}


	public String getNti001SumMoney() {
		return nti001SumMoney;
	}

	public void setLdp001SumMoney(String ldp001SumMoney) {
		this.ldp001SumMoney = ldp001SumMoney;
	}


	public String getLdp001SumMoney() {
		return ldp001SumMoney;
	}

	public void setLdp001ContractText(String ldp001ContractText) {
		this.ldp001ContractText = ldp001ContractText;
	}


	public String getLdp001ContractText() {
		return ldp001ContractText;
	}

	public void setNfp001ContractText(String nfp001ContractText) {
		this.nfp001ContractText = nfp001ContractText;
	}


	public String getNfp001ContractText() {
		return nfp001ContractText;
	}

	public void setNfp001SumMoney(String nfp001SumMoney) {
		this.nfp001SumMoney = nfp001SumMoney;
	}


	public String getNfp001SumMoney() {
		return nfp001SumMoney;
	}

	public void setCou001ContractText(String cou001ContractText) {
		this.cou001ContractText = cou001ContractText;
	}


	public String getCou001ContractText() {
		return cou001ContractText;
	}

	public void setCou001SumMoney(String cou001SumMoney) {
		this.cou001SumMoney = cou001SumMoney;
	}


	public String getCou001SumMoney() {
		return cou001SumMoney;
	}

	public void setVip001ContractText(String vip001ContractText) {
		this.vip001ContractText = vip001ContractText;
	}


	public String getVip001ContractText() {
		return vip001ContractText;
	}

	public void setVip001SumMoney(String vip001SumMoney) {
		this.vip001SumMoney = vip001SumMoney;
	}


	public String getVip001SumMoney() {
		return vip001SumMoney;
	}

	public void setLpu001ContractText(String lpu001ContractText) {
		this.lpu001ContractText = lpu001ContractText;
	}


	public String getLpu001ContractText() {
		return lpu001ContractText;
	}

	public void setLpu001SumMoney(String lpu001SumMoney) {
		this.lpu001SumMoney = lpu001SumMoney;
	}


	public String getLpu001SumMoney() {
		return lpu001SumMoney;
	}

	public void setBac001ContractText(String bac001ContractText) {
		this.bac001ContractText = bac001ContractText;
	}


	public String getBac001ContractText() {
		return bac001ContractText;
	}

	public void setBac001SumMoney(String bac001SumMoney) {
		this.bac001SumMoney = bac001SumMoney;
	}


	public String getBac001SumMoney() {
		return bac001SumMoney;
	}

	public void setUep001ContractText(String uep001ContractText) {
		this.uep001ContractText = uep001ContractText;
	}


	public String getUep001ContractText() {
		return uep001ContractText;
	}

	public void setUep001SumMoney(String uep001SumMoney) {
		this.uep001SumMoney = uep001SumMoney;
	}


	public String getUep001SumMoney() {
		return uep001SumMoney;
	}

	public void setOtf001ContractText(String otf001ContractText) {
		this.otf001ContractText = otf001ContractText;
	}


	public String getOtf001ContractText() {
		return otf001ContractText;
	}

	public void setOtf001SumMoney(String otf001SumMoney) {
		this.otf001SumMoney = otf001SumMoney;
	}


	public String getOtf001SumMoney() {
		return otf001SumMoney;
	}

	public void setPsf001ContractText(String psf001ContractText) {
		this.psf001ContractText = psf001ContractText;
	}


	public String getPsf001ContractText() {
		return psf001ContractText;
	}

	public void setPsf001SumMoney(String psf001SumMoney) {
		this.psf001SumMoney = psf001SumMoney;
	}


	public String getPsf001SumMoney() {
		return psf001SumMoney;
	}

	public void setCps001ContractText(String cps001ContractText) {
		this.cps001ContractText = cps001ContractText;
	}


	public String getCps001ContractText() {
		return cps001ContractText;
	}

	public void setCps001SumMoney(String cps001SumMoney) {
		this.cps001SumMoney = cps001SumMoney;
	}


	public String getCps001SumMoney() {
		return cps001SumMoney;
	}

	public void setApSumMoney(String apSumMoney) {
		this.apSumMoney = apSumMoney;
	}


	public String getApSumMoney() {
		return apSumMoney;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getRemark() {
		return remark;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}


	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
