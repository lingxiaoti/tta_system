/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload','jqueryUi'], function (app, pinyin, ztree, angularFileUpload, jqueryUi) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('plmProductExceptionDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, iframeMessage) {

        var id = $stateParams.id;

        $scope.headerData = {};
        var oldData = {};


        //异常产品表格
        $scope.exceptionProductDataTable = [];


        $scope.currentExceptionRowIndex = undefined;


        //查询单据信息
        $scope.search = function () {

            $scope.plmProductExceptionInfoId = id;

            httpServer.post(URLAPI.findPlmProductExceptionInfoInfo, {
                    'params': JSON.stringify({plmProductExceptionInfoId: $scope.plmProductExceptionInfoId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.headerData = res.data[0];
                        $scope.headerData.department = 'OB';
                        oldData = angular.copy($scope.headerData);


                        $scope.searchProductExceptionDetails();

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
            $scope.headerData.productExceptionBillStatus = 'ADDING';
            oldData = angular.copy($scope.headerData);

        }

        /************** 数据是否修改校验 *****************/

        //关闭页签事件
        iframeMessage.set('plmProductExceptionDetail_check',function () {
            var eq = false;
            //注意存放顺序
            if($scope.exceptionProductDataTable.length > 0) {
                $scope.headerData.exceptionProductList = angular.copy($scope.exceptionProductDataTable);
            }

            // console.log(JSON.stringify($scope.headerData)+"\n");
            // console.log(JSON.stringify(oldData));
            if(JSON.stringify($scope.headerData)!==JSON.stringify(oldData)){
                eq = true;
                $('.modal').draggable();
                $('#saveModal').modal('show');
            }

            if(!eq) {
                // iframeMessage.remove('plmProductExceptionDetail_check');
                window.parent.deleteHeadTab();
            }
        });

        $scope.closeHeadTab = function(){
            window.parent.deleteHeadTab();
        };

        /************** 数据是否修改校验 *****************/


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
            var params = $scope.headerData;
            params.productExceptionDetailList = $scope.exceptionProductDataTable;

            httpServer.post(URLAPI.savePlmProductExceptionInfoInfo, {
                    'params': JSON.stringify($scope.headerData)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.plmProductExceptionInfoId;
                        $scope.search();
                        $('#saveModal').modal('hide');

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

        //作废
        $scope.btnDiscard = function(){

            var params = $scope.headerData;

            SIEJS.confirm('作废', '是否确定作废？', '确定', function () {
                params.productExceptionBillStatus = 'ABANDONED';

                httpServer.post(URLAPI.savePlmProductExceptionInfoInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            id = res.data.plmProductExceptionInfoId;
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
        };


        $scope.btnSubmit = function(){

            $scope.checkData('submit');

        };


        $scope.submitFunction = function(){
            SIEJS.confirm('提交', '是否确定提交？', '确定', function () {
                var params = $scope.headerData;
                params.productExceptionDetailList = $scope.exceptionProductDataTable;
                params.productExceptionBillStatus = 'FOLLOWING';

                httpServer.post(URLAPI.savePlmProductExceptionInfoInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            id = res.data.plmProductExceptionInfoId;
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
        };

        $scope.checkData = function(string){
            var errMsg = '';

            for(var i = 0; i < $scope.exceptionProductDataTable.length; i++){
                if($scope.exceptionProductDataTable[i].barcode===undefined||$scope.exceptionProductDataTable[i].barcode===''){
                    errMsg = '异常产品行无Barcode，请检查数据';
                }
                if($scope.exceptionProductDataTable[i].projectRange===undefined||$scope.exceptionProductDataTable[i].projectRange===''){
                    errMsg = '异常产品行无Range，请检查数据';
                }
                if($scope.exceptionProductDataTable[i].projectName===undefined||$scope.exceptionProductDataTable[i].projectName===''){
                    errMsg = '异常产品行无项目名称，请检查数据';
                }
                if($scope.exceptionProductDataTable[i].productNameCn===undefined||$scope.exceptionProductDataTable[i].productNameCn===''){
                    errMsg = '异常产品行无商品名称，请检查数据';
                }
                if($scope.exceptionProductDataTable[i].producer===undefined||$scope.exceptionProductDataTable[i].producer===''){
                    errMsg = '异常产品行无生产商，请检查数据';
                }
                // if($scope.exceptionProductDataTable[i].batchNumber===undefined||$scope.exceptionProductDataTable[i].batchNumber===''){
                //     errMsg = '异常产品行无批号，请检查数据';
                // }
                // if($scope.exceptionProductDataTable[i].complaintSamplesNumber===undefined||$scope.exceptionProductDataTable[i].complaintSamplesNumber===''){
                //     errMsg = '异常产品行无投诉样品数量，请检查数据';
                // }

                // if(!isNaN($scope.exceptionProductDataTable[i].complaintSamplesNumber)<=0){
                //     errMsg = '异常产品行投诉样品数量不符规范，请检查数据';
                // }
            }

            if(string ==='submit'){
                if($scope.exceptionProductDataTable.length ===0){
                    errMsg = '异常产品无数据';
                }
            }

            if(string==='CLOSED'){
                if($scope.exceptionProductDataTable.length ===0){
                    errMsg = '异常产品无数据';
                }

                if($scope.headerData.treatment===undefined||$scope.headerData.treatment==='') {
                    errMsg = '请选择处理方式';
                }
                if($scope.headerData.indemnity===undefined||$scope.headerData.indemnity==='') {
                    errMsg = '请填写赔偿金额';
                }
                if($scope.headerData.results===undefined||$scope.headerData.results==='') {
                    errMsg = '请填写处理结果';
                }
            }

            if(errMsg!==''){
                SIEJS.alert(errMsg,'error','确定');
                return;
            }

            if(string==='save'){
                $scope.saveFunction();
            }
            else if(string==='submit'){
                $scope.submitFunction();
            }
            else if(string==='CLOSED'){
                $scope.GBFunction();
            }


        };

        //关单
        $scope.GB = function(){
            $scope.checkData('CLOSED');
        };

        $scope.GBFunction = function(){
            SIEJS.confirm('关闭', '是否确定关闭？', '确定', function () {
                var params = $scope.headerData;
                params.productExceptionDetailList = $scope.exceptionProductDataTable;
                params.productExceptionBillStatus = 'CLOSED';
                params.commandStatus = 'CLOSED';

                httpServer.post(URLAPI.savePlmProductExceptionInfoInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            id = res.data.plmProductExceptionInfoId;
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
        };


        /**********************头按钮操作 end *********************/



        /**********************异常产品 start *********************/

        //查询产品明细
        $scope.searchProductExceptionDetails = function () {

            httpServer.post(URLAPI.findPlmProductExceptionDetailInfo, {
                    'params': JSON.stringify({plmProductExceptionInfoId: $scope.plmProductExceptionInfoId}),
                    'pageRows': 100
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.exceptionProductDataTable = res.data;
                        if($scope.exceptionProductDataTable.length>0)
                            oldData.exceptionProductList = angular.copy($scope.exceptionProductDataTable);
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
        $scope.addProductExceptionDetail = function () {
            var exceptionProductArray = {
                plmProductExceptionInfoId: $scope.plmProductExceptionInfoId
            };
            $scope.exceptionProductDataTable.push(exceptionProductArray);
        };

        //删除产品明细行按钮
        $scope.delProductExceptionDetail = function(){
            var index=$scope.exceptionProductDataTable.selectRow;
            var plmProductExcepDetailId = $scope.exceptionProductDataTable[index].plmProductExcepDetailId;

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if(plmProductExcepDetailId == null || plmProductExcepDetailId =='' ){
                    $scope.exceptionProductDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                    $scope.$apply();
                }
                else {
                    $scope.deleteProductExceptionDetailInfo(index);
                }
            })

        };

        //删除操作
        $scope.deleteProductExceptionDetailInfo = function(index){
            httpServer.post(URLAPI.deletePlmProductExceptionDetailInfo, {
                    'params': JSON.stringify($scope.exceptionProductDataTable[index])
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.exceptionProductDataTable.splice(index, 1);
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


        /**********************异常产品 end *********************/


        /**********************弹窗 start *********************/
        //项目异常涉及产品弹窗
        $scope.showExceptionProduct = function (index) {
            $scope.currentExceptionRowIndex = index;
            $scope.exceptionProductLov.search();
            $('#exceptionProductLovId').modal('show');
        };

        //项目异常涉及产品返回参数
        $scope.selectExceptionProductReturn = function (key, value, returnList) {
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].plmDevelopmentInfoId = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].barcode = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].projectName = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productName = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].projectRange = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productNameCn = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productNameEn = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productNo = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productCategory = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].supplierName = undefined;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].producer = undefined;

            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].plmDevelopmentInfoId = returnList[0].plmDevelopmentInfoId;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].barcode = returnList[0].barcode;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].projectName = returnList[0].projectName;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productName = returnList[0].productName;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].projectRange = returnList[0].projectRange;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productNameCn = returnList[0].productNameCn;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productNameEn = returnList[0].productNameEn;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productNo = returnList[0].productNo;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].productCategory = returnList[0].productCategory;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].supplierName = returnList[0].supplierName;
            $scope.exceptionProductDataTable[$scope.currentExceptionRowIndex].producer = returnList[0].producer;

            $scope.currentExceptionRowIndex = undefined;

        };


        /**********************弹窗 end *********************/

        $scope.$watch("headerData.exceptionSource",function (newVal, oldVal) {
            if(oldVal!==undefined&&newVal!==undefined&&newVal!==oldVal){
                $scope.headerData.exceptionCategory= '';
            }
        })

        $scope.findDetail = function(row){
            iframeTabAction('产品详情：'+row.productName, 'plmDevelopmentDetail/' + row.plmDevelopmentInfoId);
        }

    });
});
