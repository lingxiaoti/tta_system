package com.sie.watsons.base.product.model.inter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.ServerException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierplaceinfoEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductAddReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductAddReportT;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductConditionReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductPackageReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductQaReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductSupplierReport;
import com.sie.watsons.base.product.model.entities.readonly.PlmSupplierQaReport;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmProductHead extends IBaseCommon<PlmProductHeadEntity_HI> {

	JSONObject saveProductInfo(JSONObject queryParamJSON)
			throws ServerException;

	String updateByHeaderIds(List<Integer> headerIds) throws ServerException;

	JSONObject findProductDetail(JSONObject param);

	Pagination<PlmProductHeadEntity_HI_RO> findProductList(JSONObject param,
			Integer pageIndex, Integer pageRows);

	void deleteByProductId(JSONObject param) throws ServerException;

	JSONObject SaveProductByExcel(JSONObject param)
			throws FileNotFoundException, IOException, ParseException;

	JSONObject updateProductInfo(JSONObject queryParamJSON)
			throws ServerException;

	JSONObject saveObProduct(JSONObject param) throws ServerException;

	JSONObject updateConfirmProduct(JSONObject param) throws ServerException;

	Pagination<PlmProductAddReport> FindProductReportList(JSONObject param,
			Integer pageIndex, Integer pageRows);

	JSONObject updateProductByExcel(JSONObject param) throws ServerException;

	Pagination<PlmProductAddReportT> FindProductReportTList(JSONObject param,
			Integer pageIndex, Integer pageRows);

	Pagination<PlmProductConditionReport> FindProductconditionReportLis(
			JSONObject param, Integer pageIndex, Integer pageRows);

	Pagination<PlmProductPackageReport> FindProductUpdatePackage(
			JSONObject param, Integer pageIndex, Integer pageRows)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ParseException;

	Pagination<PlmProductSupplierReport> FindProductUpdateSupplier(
			JSONObject param, Integer pageIndex, Integer pageRows);

	JSONObject productSupplierReturn(JSONObject param) throws ServerException;

	JSONObject saveNextPerson(JSONObject param) throws ServerException;

	Pagination<PlmProductHeadEntity_HI_RO> findProductList2(JSONObject param,
			Integer pageIndex, Integer pageRows);

	JSONObject productPicfileReturn(JSONObject param) throws ServerException;

	JSONObject productQafileReturn(JSONObject param) throws ServerException;

	JSONObject findProductDetail2(JSONObject param);

	String SaveProductPattch(JSONObject param);

	String uploadProductSupplierPattch(JSONObject param,
			HttpServletResponse response) throws ServerException;

	JSONObject SaveSupplierProductByExcel(JSONObject param)
			throws ServerException;

	JSONObject ConnectSupplierProductByFile(JSONObject param)
			throws ServerException;

	String SaveProductSupplierSubmit(JSONObject param);

	String uploadProductBuyerPattch(JSONObject param,
			HttpServletResponse response) throws ServerException;

	JSONObject SaveThreeProductByExcel(JSONObject param) throws ServerException;

	JSONObject SaveProductBuyer3Submit(JSONObject param);

	JSONObject getSupplierByProduct(JSONObject param) throws ServerException;

	Pagination<PlmProductQaReport> FindProductQaReportList(JSONObject param,
			Integer pageIndex, Integer pageRows);

	Pagination<PlmSupplierQaReport> FindSupplierQaReportList(JSONObject param,
			Integer pageIndex, Integer pageRows);

	void execute(String string);

	void UpdateRmsColumns(JSONObject dataJSON) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ParseException;

	List<PlmProductSupplierplaceinfoEntity_HI> getList(String string,
			Map<String, Object> map);

    void saveAndSync(JSONObject queryParamJSON) throws IOException;
}
