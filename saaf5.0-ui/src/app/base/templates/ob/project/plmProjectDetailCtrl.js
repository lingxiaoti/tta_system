/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload','jqueryUi','XLSX'], function (app, pinyin, ztree, angularFileUpload,jqueryUi,XLSX) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('plmProjectDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, iframeMessage) {
        var id = $stateParams.id;


        $scope.headerData = {};
        $scope.today = $rootScope.$getToday();
        var oldData = {};


        //产品明细表格
        $scope.projectProductDataTable = [];
        //项目异常明细表格
        $scope.projectExceptionDataTable = [];

        $scope.currentExceptionRowIndex = undefined;
        $scope.currentProductDetailIndex = undefined;

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;

        /************** 数据是否修改校验 *****************/

        //关闭页签事件
        iframeMessage.set('plmProjectDetail_check',function () {
            var eq = false;
            //注意存放行表顺序与查询时存放顺序一致，但查询快慢影响存放顺序
            var tempOldDataString = JSON.stringify(oldData);
            if(tempOldDataString.indexOf("projectProductList")!==-1&&tempOldDataString.indexOf("projectExceptionList")!==-1){
                if(tempOldDataString.indexOf("projectProductList")>tempOldDataString.indexOf("projectExceptionList")){
                    var projectExceptionTempList = angular.copy(oldData.projectExceptionList);
                    delete oldData.projectExceptionList;
                    oldData.projectExceptionList = projectExceptionTempList;
                }
            }
            if($scope.projectProductDataTable.length > 0) {
                $scope.headerData.projectProductList = angular.copy($scope.projectProductDataTable);
            }
            if($scope.projectExceptionDataTable.length > 0) {
                $scope.headerData.projectExceptionList = angular.copy($scope.projectExceptionDataTable);
            }
            // console.log(JSON.stringify($scope.headerData)+"\n");
            // console.log(JSON.stringify(oldData));
            if(JSON.stringify($scope.headerData)!==JSON.stringify(oldData)){
                eq = true;
                $('.modal').draggable();
                $('#saveModal').modal('show');
            }
            // angular.forEach($scope.headerData,function (value, key) {
            //     // console.log(key+'---->'+value+'----->'+$scope.headerData[key]+'\n');
            //     if(Array.isArray($scope.headerData[key])){
            //         // console.log("key-->"+key+"$scope.headerData[key]---->"+JSON.stringify($scope.headerData[key]));
            //         // console.log("key-->"+key+"  oldData[key]---->"+JSON.stringify(oldData[key])+"\n");
            //         if(JSON.stringify($scope.headerData[key])!==JSON.stringify(oldData[key])){
            //             eq = true;
            //             $('.modal').draggable();
            //             $('#saveModal').modal('show');
            //         }
            //     }
            //
            //     //数组不能直接比较,此处只比较属性值
            //     if(!Array.isArray($scope.headerData[key])&&$scope.headerData[key]!==oldData[key]){
            //         eq = true;
            //         $('.modal').draggable();
            //         $('#saveModal').modal('show');
            //     }
            // });
            if(!eq) {
                //项目详情与新增项目共用此属性，不能随意删除
                // iframeMessage.remove('plmProjectDetail_check');
                window.parent.deleteHeadTab();

            }
        });

        $scope.closeHeadTab = function(){
            window.parent.deleteHeadTab();
        };

        /************** 数据是否修改校验 *****************/


        //查询单据信息
        $scope.search = function () {

            $scope.plmProjectId = id;

            httpServer.post(URLAPI.findPlmProjectInfoInfo, {
                    'params': JSON.stringify({plmProjectId: $scope.plmProjectId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.headerData = res.data[0];
                        $scope.searchProjectProductDetails();
                        $scope.searchProjectException();
                        oldData = angular.copy($scope.headerData);

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };




        //id不为空，初始搜索
        if ($stateParams.id) {
            //查询头信息
            $scope.search();
        } else {
            $scope.headerData.billStatus = 'TODO';
            oldData = angular.copy($scope.headerData);
        }


        /**********************头按钮操作 start *********************/

        //全存
        $scope.btnSave = function (invalid) {


            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            $scope.checkData('save');

        };

        $scope.saveFunction = function(){
            $scope.headerData.productDetailList = $scope.projectProductDataTable;
            $scope.headerData.projectExceptionList = $scope.projectExceptionDataTable;

            httpServer.post(URLAPI.savePlmProjectInfoInfo, {
                    'params': JSON.stringify($scope.headerData)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.plmProjectId;
                        $scope.search();
                        SIEJS.alert(res.msg, 'success','确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.checkData = function(string){
            for(var i = 0; i < $scope.projectProductDataTable.length; i++){
                if($scope.projectProductDataTable[i].productName==undefined||$scope.projectProductDataTable[i].productName==''){
                    SIEJS.alert('产品明细行有未填写的产品名称！','error','确定');
                    return;
                }
                if($scope.projectProductDataTable[i].productCategory== undefined || $scope.projectProductDataTable[i].productCategory ==''){
                    SIEJS.alert('产品明细行有未选择的产品品类！','error','确定');
                    return;
                }
                if($scope.headerData.supplierType==='TRADER'&&($scope.projectProductDataTable[i].producerName=== undefined || $scope.projectProductDataTable[i].producerName ==='')){
                    SIEJS.alert('产品明细行有未选择的生产商名称！','error','确定');
                    return;
                }
            }

            for(var j = 0; j < $scope.projectExceptionDataTable.length; j++){
                if($scope.projectExceptionDataTable[j].exceptionProductName==undefined||$scope.projectExceptionDataTable[j].exceptionProductName==''){
                    SIEJS.alert('项目异常行有未选择的涉及产品！','error','确定');
                    return;
                }
                if($scope.projectExceptionDataTable[j].exceptionOccurrenceTime == undefined || $scope.projectExceptionDataTable[j].exceptionOccurrenceTime==''){
                    SIEJS.alert('项目异常行有未选择的异常发生时间！','error','确定');
                    return;
                }
                if($scope.projectExceptionDataTable[j].exceptionOccurrenceStage == undefined || $scope.projectExceptionDataTable[j].exceptionOccurrenceStage==''){
                    SIEJS.alert('项目异常行有未选择的异常发生阶段！','error','确定');
                    return;
                }
                if($scope.projectExceptionDataTable[j].exceptionDetails == undefined || $scope.projectExceptionDataTable[j].exceptionDetails==''){
                    SIEJS.alert('项目异常行有未输入的异常详情！','error','确定');
                    return;
                }

            }

            if(string === 'save'){
                $scope.saveFunction();
            }
            else if(string === 'submit'){
                $scope.submitFunction();
            }
            else if(string === 'saveException'){
                $scope.saveProjectExceptionFunction();
            }
        };

        //作废
        $scope.btnDiscard = function(){
            $scope.discardReason = "";

            for(var i = 0; i < $scope.projectProductDataTable.length; i++){
                if($scope.projectProductDataTable[i].productBillStatus=='COMPLETED'||$scope.projectProductDataTable[i].productBillStatus=='TOBEADDED'){
                    SIEJS.alert('产品明细行有开发完成/开发完成待补充的产品，禁止作废！','error','确定');
                    return;
                }
            }

            $('#discardModal').modal('show');

        };

        $scope.discardAction = function(){
            $scope.headerData.productDetailList = $scope.projectProductDataTable;
            $scope.headerData.projectExceptionList = $scope.projectExceptionDataTable;

            SIEJS.confirm('作废', '是否确定作废？', '确定', function () {
                var params = angular.copy($scope.headerData);
                if(params.remarks!==undefined&&params.remarks!=='') {
                    params.remarks += '; 作废原因：' + $scope.discardReason;
                } else {
                    params.remarks = '作废原因：' + $scope.discardReason;
                }
                params.billStatus = 'ABANDONED';

                httpServer.post(URLAPI.savePlmProjectInfoInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            id = res.data.plmProjectId;
                            $scope.search();
                            $('#discardModal').modal('hide');
                            SIEJS.alert(res.msg, 'success');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            });
        }


        $scope.btnSubmit = function(){

            $scope.checkData('submit');

        };

        $scope.submitFunction = function(){
            var params = angular.copy($scope.headerData);
            params.productDetailList = $scope.projectProductDataTable;
            params.projectExceptionList = $scope.projectExceptionDataTable;

            SIEJS.confirm('提交', '是否确定提交？', '确定', function () {

                params.billStatus = 'SUBMITTED';

                httpServer.post(URLAPI.savePlmProjectInfoInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            id = res.data.plmProjectId;
                            $scope.search();
                            SIEJS.alert(res.msg, 'success');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            });
        }

        /**********************头按钮操作 end *********************/



        /**********************产品明细 start *********************/

        //查询产品明细
        $scope.searchProjectProductDetails = function () {

            httpServer.post(URLAPI.findPlmProjectProductDetailInfo, {
                    'params': JSON.stringify({plmProjectId: $scope.plmProjectId}),
                    'pageRows': 100
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.projectProductDataTable = res.data;
                        if($scope.projectProductDataTable.length!==0)
                            oldData.projectProductList = angular.copy(res.data);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //产品明细新增
        $scope.addProductDetail = function () {
            var productDetailArray = {
                obId: "",
                barcode: "",
                productName: "",
                productCategory: "",
                productBillStatus: "TODO",
                productStatus: "TODO",
                producerName: "",
                plmProjectId: $scope.plmProjectId
            };
            if($scope.headerData.supplierType ==='PRODUCER'){
                productDetailArray.producerName = $scope.headerData.supplierName;
            }
            $scope.projectProductDataTable.push(productDetailArray);
        };

        //删除产品明细行按钮
        $scope.delProductDetail = function(){
            var index=$scope.projectProductDataTable.selectRow;
            var plmProjectProductDetailId = $scope.projectProductDataTable[index].plmProjectProductDetailId;

            for(var i = 0; i < $scope.projectExceptionDataTable.length; i++){
                if($scope.projectExceptionDataTable[i].plmProjectProductDetailId===plmProjectProductDetailId){
                    SIEJS.alert("项目异常栏存在引用当前行表数据情况，禁止删除！","error","确定");
                    return;
                }
            }

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if(plmProjectProductDetailId == null || plmProjectProductDetailId =='' ){
                    $scope.projectProductDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                    $scope.$apply();
                }
                else {
                    $scope.deleteProductDetailInfo(index);
                }
            })

        };

        //删除操作
        $scope.deleteProductDetailInfo = function(index){
            httpServer.post(URLAPI.deletePlmProjectProductDetailInfo, {
                    'params': JSON.stringify($scope.projectProductDataTable[index])
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.projectProductDataTable.splice(index, 1);
                        SIEJS.alert("操作成功!已成功删除数据！", "success");
                        $scope.$apply();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };


        /**********************产品明细 end *********************/


        /**********************异常明细 start *********************/

        //查询异常明细
        $scope.searchProjectException = function () {

            httpServer.post(URLAPI.findPlmProjectExceptionInfo, {
                    'params': JSON.stringify({plmProjectId: $scope.plmProjectId}),
                    'pageRows': 100
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.projectExceptionDataTable = res.data;
                        if($scope.projectExceptionDataTable.length!==0)
                            oldData.projectExceptionList = angular.copy(res.data);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //异常明细新增
        $scope.addProjectException = function () {
            var exceptionDetailArray ={};
            exceptionDetailArray.plmProjectId = $scope.headerData.plmProjectId;
            $scope.projectExceptionDataTable.push(exceptionDetailArray);
        };

        //删除项目异常行按钮
        $scope.delProjectException = function(){
            var index=$scope.projectExceptionDataTable.selectRow;
            var plmProjectExceptionId = $scope.projectExceptionDataTable[index].plmProjectExceptionId;
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if(plmProjectExceptionId == null || plmProjectExceptionId =='' ){
                    $scope.projectExceptionDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                    $scope.$apply();
                }
                else {
                    $scope.deleteProjectExceptionInfo(index);
                }
            })

        };

        //数据库删除项目异常操作
        $scope.deleteProjectExceptionInfo = function(index){
            httpServer.post(URLAPI.deletePlmProjectExceptionInfo, {
                    'params': JSON.stringify($scope.projectExceptionDataTable[index])
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.projectExceptionDataTable.splice(index, 1);
                        SIEJS.alert("操作成功!已成功删除数据！", "success");
                        $scope.$apply();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.saveProjectException = function(){

            $scope.checkData('saveException');

        };

        $scope.saveProjectExceptionFunction = function(){
            var params = {};
            params.projectExceptionList = $scope.projectExceptionDataTable;

            httpServer.post(URLAPI.savePlmProjectExceptionInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.searchProjectException();
                        SIEJS.alert(res.msg, 'success','确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        /**********************异常明细 end *********************/

        /**********************弹窗 start *********************/
        //项目异常涉及产品弹窗
        $scope.showExceptionProduct = function (index) {
            $scope.currentExceptionRowIndex = index;
            $scope.exceptionProductLov.search();
            $('#exceptionProduct').modal('show');
        };

        //项目异常涉及产品返回参数
        $scope.selectExceptionProductReturn = function (key, value, returnList) {
            $scope.projectExceptionDataTable[$scope.currentExceptionRowIndex].plmProjectProductDetailId = returnList[0].plmProjectProductDetailId;
            $scope.projectExceptionDataTable[$scope.currentExceptionRowIndex].plmDevelopmentInfoId = returnList[0].plmDevelopmentInfoId;
            $scope.projectExceptionDataTable[$scope.currentExceptionRowIndex].exceptionProductName = returnList[0].productName;

            $scope.currentExceptionRowIndex = undefined;

        };

        //供应商返回值
        $scope.selectSupplierInfoReturn = function(key, value, returnList){
            var newVal = '';
            $scope.headerData.supplierId = '';
            $scope.headerData.supplierCode = '';
            $scope.headerData.supplierName = '';

            if(returnList.length!==0) {
                $scope.headerData.supplierId = returnList[0].userId;
                //$scope.headerData.supplierCode = returnList[0].supplierCode;
                $scope.headerData.supplierName = returnList[0].userName;
                newVal = $scope.headerData.supplierName;
            }

            //
            if($scope.headerData.supplierType==='PRODUCER') {
                for (var i = 0; i < $scope.projectProductDataTable.length; i++) {
                    //由无到有
                    if($scope.oldSupplierName===undefined||$scope.oldSupplierName===''){
                        if($scope.projectProductDataTable[i].producerName===undefined||$scope.projectProductDataTable[i].producerName===''){
                            $scope.projectProductDataTable[i].producerName = newVal;
                        } else {
                            $scope.projectProductDataTable[i].producerName += ',' + newVal;
                        }
                    }
                    else {
                        if($scope.projectProductDataTable[i].producerName===undefined||$scope.projectProductDataTable[i].producerName===''){
                            $scope.projectProductDataTable[i].producerName = newVal;
                        } else {

                            var strArray = $scope.projectProductDataTable[i].producerName.split(",");
                            var resultStr = '';
                            for(var j = 0; j < strArray.length; j++){
                                if(!$scope.oldSupplierName===strArray[j]){
                                    resultStr += strArray[j] + ',';
                                }
                            }

                            resultStr += newVal + ',';

                            if(resultStr.charAt(resultStr.length-1)===','){
                                resultStr = resultStr.substring(0,resultStr.length-1);
                            }

                            $scope.projectProductDataTable[i].producerName = resultStr;

                            // if($scope.projectProductDataTable[i].producerName.indexOf(','+$scope.oldSupplierName)!==-1){
                            //     $scope.projectProductDataTable[i].producerName = $scope.projectProductDataTable[i].producerName.replace(','+$scope.oldSupplierName,"",'gm');
                            //     if($scope.projectProductDataTable[i].producerName.indexOf(newVal)===-1)
                            //         $scope.projectProductDataTable[i].producerName += ',' + newVal;
                            //
                            // }
                            // else if($scope.projectProductDataTable[i].producerName.indexOf($scope.oldSupplierName)!==-1){
                            //     $scope.projectProductDataTable[i].producerName = $scope.projectProductDataTable[i].producerName.replace($scope.oldSupplierName,"",'gm');
                            //     if($scope.projectProductDataTable[i].producerName.indexOf(newVal)===-1) {
                            //         $scope.projectProductDataTable[i].producerName = newVal;
                            //     }
                            //
                            // }

                        }
                    }

                }
            }

        };

        $scope.showSupplier = function(){
            $scope.oldSupplierName = $scope.headerData.supplierName;
            $('#supplierInfoId').modal('show');
        }

        //生产商弹窗
        $scope.showProducer = function(index){
            $scope.currentProductDetailIndex = index;
            $scope.producerInfoLov.search();
            $('#producerInfoId').modal('show');
        };

        //生产商返回值
        $scope.selectProducerInfoReturn = function(key, value, returnList){
            $scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName = '';
            for(var i = 0; i < returnList.length; i++){
                if($scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName!==undefined&&$scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName!=='') {
                    $scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName = $scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName + ',' + returnList[i].userName;
                }
                else {
                    $scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName = returnList[i].userName;
                }
            }
            var str = $scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName.split(",");
            var containSign = false;
            for(var j = 0; j < str.length; j ++){
                if ($scope.headerData.supplierType === 'PRODUCER'&&$scope.headerData.supplierName===str[j]){
                    containSign = true;
                }
            }
            if(containSign===false&&$scope.headerData.supplierType === 'PRODUCER'){
                $scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName +=  ',' + $scope.headerData.supplierName;
            }
            // if($scope.headerData.supplierType === 'PRODUCER'&&$scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName.indexOf($scope.headerData.supplierName)===-1){
            //     $scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName +=  ',' + $scope.headerData.supplierName;
            // }
            if($scope.headerData.supplierType === 'PRODUCER'&&$scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName.charAt(0)===','){
                $scope.projectProductDataTable[$scope.currentProductDetailIndex].producerName = $scope.headerData.supplierName;
            }
        };
        /**********************弹窗 end *********************/


        //跳转OB产品单详情
        $scope.findDetail = function (row) {
            if(row.plmDevelopmentInfoId!==undefined&&row.plmDevelopmentInfoId!=='') {
                iframeTabAction('产品详情：' + row.productName, 'plmDevelopmentDetail/' + row.plmDevelopmentInfoId);
            }
        };

        $scope.$watch("headerData.supplierType",function (newVal, oldVal) {
            if((oldVal==='PRODUCER'&&newVal==='TRADER')||(newVal===undefined&&($scope.headerData.plmProjectId!==undefined||$stateParams.id===''))){
                for(var i = 0; i < $scope.projectProductDataTable.length; i++){
                    // var stringRegex = ','+$scope.headerData.supplierName;
                    // var name =  $scope.projectProductDataTable[i].producerName;
                    // if(name!==undefined&&name.indexOf(stringRegex)>0) {
                    //     $scope.projectProductDataTable[i].producerName = name.replace(stringRegex, "", "gm");
                    // }
                    // else if(name!==undefined){
                    //     $scope.projectProductDataTable[i].producerName = name.replace($scope.headerData.supplierName, "", "gm");
                    // }
                    var strArray = $scope.projectProductDataTable[i].producerName.split(",");
                    var resultStr = '';
                    for(var k = 0; k < strArray.length; k++){
                        if($scope.headerData.supplierName!==strArray[k]){
                            resultStr += strArray[k] + ',';
                        }
                    }

                    if(resultStr.charAt(resultStr.length-1)===','){
                        resultStr = resultStr.substring(0,resultStr.length-1);
                    }

                    $scope.projectProductDataTable[i].producerName = resultStr;
                }
            }
            else if(newVal==='PRODUCER'&&($scope.headerData.plmProjectId!==undefined||$stateParams.id==='')){
                for(var j = 0; j < $scope.projectProductDataTable.length; j++){
                    // if($scope.projectProductDataTable[j].producerName===undefined||$scope.projectProductDataTable[j].producerName.indexOf($scope.headerData.supplierName)===-1) {
                    //     if($scope.projectProductDataTable[j].producerName===''||$scope.projectProductDataTable[j].producerName===undefined){
                    //         $scope.projectProductDataTable[j].producerName = $scope.headerData.supplierName;
                    //     }
                    //     else {
                    //         $scope.projectProductDataTable[j].producerName += ',' + $scope.headerData.supplierName;
                    //     }
                    // }
                    if($scope.projectProductDataTable[j].producerName===undefined){
                        $scope.projectProductDataTable[j].producerName = $scope.headerData.supplierName;
                    }
                    else {
                        var strArray2 = $scope.projectProductDataTable[j].producerName.split(",");
                        var resultStr2 = '';
                        for(var l = 0; l < strArray2.length; l++){
                            if($scope.headerData.supplierName!==strArray2[l]){
                                resultStr2 += strArray2[l] + ',';
                            }
                        }
                        resultStr2 += $scope.headerData.supplierName + ',';
                        if(resultStr2.charAt(resultStr2.length-1)===','){
                            resultStr2 = resultStr2.substring(0,resultStr2.length-1);
                        }

                        $scope.projectProductDataTable[j].producerName = resultStr2;
                    }


                }
            }
        })

    });
});
