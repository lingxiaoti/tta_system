/**
 * Created by sie_chubaodong on 2019/9/4.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'XLSX', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload, XLSX) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('editQuotationInfoCtrl', function (wechatFind, $filter, Base64, deleteUserResp, findProfileSqlDatas, $window,
                                                      saveUserResp, userSave, $timeout, $scope, httpServer, URLAPI, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS) {
        $scope.params = {};
        $scope.editParams = {};
        //非价格文件
        $scope.nonPriceFileDataTable = [];
        //非价格文件(固定附件)
        $scope.nonPriceSelectFileDataTable = [];
        //价格文件
        $scope.priceFileDataTable = [];
        // 产品
        $scope.productDataTable = [];
        $scope.row = {};
        $scope.rowsIndex = 5;

        //当前登录人所属部门
        // $scope.editParams.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        // $scope.editParams.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        // var departmentName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).departmentName;
        // $scope.editParams.departmentName = $scope.editParams.deptName;
        // console.log($scope.editParams.deptName);
        // console.log(departmentName);
        //当前登录人类型
        var id = $stateParams.quotationId;
        $scope.editParams.quotationId = id;
        $scope.editFlag = false;
        $scope.updateFlag = false;
        $scope.bargainFlag = false;
        var supplierId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).supplierId;
        var contactId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).contactId;
        console.log(supplierId);
        console.log(contactId);

        /********************查询报价管理信息********************/
        $scope.findQuotationInfo = function () {
            console.log($scope.editParams.quotationId);
            httpServer.post(URLAPI.findQuotationInfo, {
                params: JSON.stringify({
                    quotationId: $scope.editParams.quotationId
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.log(res);
                    $scope.editParams = res.data;
                    // $scope.editParams.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
                    $scope.editParams.supplierId = res.data.supplierId;
                    $scope.editParams.quotationId = res.data.quotationId;
                    $scope.editParams.projectId = res.data.projectId;
                    $scope.editParams.projectNumber = res.data.projectNumber;
                    if ('QUOTATION' == res.data.docStatus || 'COMMIT' == res.data.docStatus) {
                        $scope.editFlag = true;
                    }
                    if ('MODIFYING' == res.data.docStatus) {
                        $scope.editFlag = false;
                        $scope.updateFlag = true;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询非价格文件信息********************/
        $scope.searchNonPriceFileInfo = function () {
            httpServer.post(URLAPI.findQuotationNopriceInfo, {
                params: JSON.stringify({
                    quotationId: $scope.editParams.quotationId,
                    fileType: "NonPriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.log(res);
                    $scope.nonPriceFileDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询非价格文件(选择)信息********************/
        $scope.searchNonPriceSelectFileInfo = function () {
            httpServer.post(URLAPI.findQuotationNopriceInfo, {
                params: JSON.stringify({
                    quotationId: $scope.editParams.quotationId,
                    fileType: "NonPriceSelectFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.log(res);
                    $scope.nonPriceSelectFileDataTable = res.data;
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询价格文件信息********************/
        $scope.searchPriceFileInfo = function () {
            httpServer.post(URLAPI.findQuotationPriceInfo, {
                params: JSON.stringify({
                    quotationId: $scope.editParams.quotationId,
                    fileType: "PriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.log(res);
                    $scope.priceFileDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询价格产品信息********************/
        $scope.findQuotationProductInfo = function () {
            httpServer.post(URLAPI.findQuoProductInfo, {
                params: JSON.stringify({
                    quotationId: $scope.editParams.quotationId
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.log(res);
                    $scope.productDataTable = res.data;
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************初始化默认按照id查询********************/
        $scope.findQuotationInfo();
        $scope.searchNonPriceFileInfo();
        $scope.searchNonPriceSelectFileInfo();
        $scope.searchPriceFileInfo();
        $scope.findQuotationProductInfo();

        //增加产品
        $scope.productAdd = function () {
            var productArray = {
                productName: "",
                firstProductPrice: "",
                twoProductPrice: "",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.productDataTable.unshift(productArray);
        };


        //导入回调方法:刷新数据
        $scope.returnFlash = function () {
            $scope.findQuotationProductInfo();
        };

        // 上传报价非价格文件选择附件
        $scope.uploadNonPriceSelectFile = function () {
            var row = $scope.nonPriceSelectFileDataTable[$scope.nonPriceSelectFileDataTable.selectRow];
            var file = document.querySelector('#nonPriceSelectFile' + $scope.nonPriceSelectFileDataTable.selectRow).files[0];
            var fd = new FormData();
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.row.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('businessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.uploadNonPriceFileCommon, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                row.nopriceSelectFileId = response.data[0].fileId;
                row.nopriceSelectFileName = response.data[0].fileName;
                row.nopriceSelectFilePath = response.data[0].filePath;
                console.log(row);
                SIEJS.alert(response.msg, 'success', '确定');
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        };

        //重新上传-附件
        $scope.changeFileForNopriceSelect = function (row) {
            row.nopriceSelectFileId = null;
            row.nopriceSelectFileName = null;
            row.nopriceSelectFilePath = null;
        };

        // 上传报价非价格文件选择修改附件
        $scope.uploadNonPriceSelectUpdateFile = function () {
            var row = $scope.nonPriceSelectFileDataTable[$scope.nonPriceSelectFileDataTable.selectRow];
            var file = document.querySelector('#nonPriceSelectUpdateFile' + $scope.nonPriceSelectFileDataTable.selectRow).files[0];
            var fd = new FormData();
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('businessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.uploadNonPriceFileCommon, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.updateNopriceSelectFileId = response.data[0].fileId;
                row.updateNopriceSelectFileName = response.data[0].fileName;
                row.updateNopriceSelectFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        };

        //重新上传-附件
        $scope.changeFileForUpdateNopriceSelect = function (row) {
            row.updateNopriceSelectFileId = null;
            row.updateNopriceSelectFileName = null;
            row.updateNopriceSelectFilePath = null;
        };

        //上传-非价格文件修改附件
        $scope.uploadNonPriceUpdateFile = function () {
            var row = $scope.nonPriceFileDataTable[$scope.nonPriceFileDataTable.selectRow];
            var file = document.querySelector('#nonPriceUpdateFile' + $scope.nonPriceFileDataTable.selectRow).files[0];
            var fd = new FormData();
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('businessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.uploadNonPriceFileCommon, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.updateNopriceFileId = response.data[0].fileId;
                row.updateNopriceFileName = response.data[0].fileName;
                row.updateNopriceFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        };

        //重新上传-附件
        $scope.changeFileForUpdateNoprice = function (row) {
            row.updateNopriceFileId = null;
            row.updateNopriceFileName = null;
            row.updateNopriceFilePath = null;
        };

        //上传-非价格文件-附件
        $scope.uploadNonPriceFile = function () {
            var row = $scope.nonPriceFileDataTable[$scope.nonPriceFileDataTable.selectRow];
            var file = document.querySelector('#nonPriceFile' + $scope.nonPriceFileDataTable.selectRow).files[0];
            var fd = new FormData();
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('businessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.uploadNonPriceFileCommon, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.nopriceFileId = response.data[0].fileId;
                row.nopriceFileName = response.data[0].fileName;
                row.nopriceFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        };

        //重新上传-附件
        $scope.changeFileForNoprice = function (row) {
            row.nopriceFileId = null;
            row.nopriceFileName = null;
            row.nopriceFilePath = null;
        };
        //上传-价格文件-附件
        $scope.uploadPriceFile = function () {
            var row = $scope.priceFileDataTable[$scope.priceFileDataTable.selectRow];
            var file = document.querySelector('#priceFile' + $scope.priceFileDataTable.selectRow).files[0];
            var fd = new FormData();
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('businessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.uploadPriceFileCommon, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.priceFileId = response.data[0].fileId;
                row.priceFileName = response.data[0].fileName;
                row.priceFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        };

        //重新上传-附件
        $scope.changeFileForPrice = function (row) {
            row.priceFileId = null;
            row.priceFileName = null;
            row.priceFilePath = null;
        };

        //上传-价格文件修改附件
        $scope.uploadPriceUpdateFile = function () {
            var row = $scope.priceFileDataTable[$scope.priceFileDataTable.selectRow];
            var file = document.querySelector('#priceUpdateFile' + $scope.priceFileDataTable.selectRow).files[0];
            var fd = new FormData();
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('businessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.uploadPriceFileCommon, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.updatePriceFileId = response.data[0].fileId;
                row.updatePriceFileName = response.data[0].fileName;
                row.updatePriceFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        };

        //重新上传-附件
        $scope.changeFileForUpdatePrice = function (row) {
            row.updatePriceFileId = null;
            row.updatePriceFileName = null;
            row.updatePriceFilePath = null;
        };

        /********************提交前校验********************/
        $scope.validate = function () {
            // 校验价格非价格必填
            if ($scope.editFlag) {
                for (var i = 0; i < $scope.nonPriceSelectFileDataTable.length; i++) {
                    if ($scope.nonPriceSelectFileDataTable[i].nopriceSelectFileName == '' || $scope.nonPriceSelectFileDataTable[i].nopriceSelectFileName == undefined) {
                        SIEJS.alert('非价格文件选项文件均不能为空', 'error', '确定');
                        return true;
                    }
                }
                for (var i = 0; i < $scope.nonPriceFileDataTable.length; i++) {
                    if ($scope.nonPriceFileDataTable[i].nopriceFileName == '' || $scope.nonPriceFileDataTable[i].nopriceFileName == undefined) {
                        SIEJS.alert('非价格文件均不能为空', 'error', '确定');
                        return true;
                    }
                }
                for (var i = 0; i < $scope.priceFileDataTable.length; i++) {
                    if ($scope.priceFileDataTable[i].priceFileName == '' || $scope.priceFileDataTable[i].priceFileName == undefined) {
                        SIEJS.alert('价格文件均不能为空', 'error', '确定');
                        return true;
                    }
                }
            }
            if ($scope.updateFlag) {
                for (var i = 0; i < $scope.nonPriceSelectFileDataTable.length; i++) {
                    if ($scope.nonPriceSelectFileDataTable[i].updateNopriceSelectFileName == '' || $scope.nonPriceSelectFileDataTable[i].updateNopriceSelectFileName == undefined) {
                        SIEJS.alert('非价格文件选项更新文件均不能为空', 'error', '确定');
                        return true;
                    }
                }
                for (var i = 0; i < $scope.nonPriceFileDataTable.length; i++) {
                    if ($scope.nonPriceFileDataTable[i].updateNopriceFileName == '' || $scope.nonPriceFileDataTable[i].updateNopriceFileName == undefined) {
                        SIEJS.alert('非价格文件更新文件均不能为空', 'error', '确定');
                        return true;
                    }
                }
                for (var i = 0; i < $scope.priceFileDataTable.length; i++) {
                    if ($scope.priceFileDataTable[i].updatePriceFileName == '' || $scope.priceFileDataTable[i].updatePriceFileName == undefined) {
                        SIEJS.alert('价格文件更新文件均不能为空', 'error', '确定');
                        return true;
                    }
                }
            }
            // 校验产品必填
            for (var i = 0; i < $scope.productDataTable.length; i++) {
                console.log($scope.productDataTable[i].firstProductPrice);
                if ($scope.productDataTable[i].firstProductPrice == undefined) {
                    SIEJS.alert('产品报价表承载产品的包装价格,但不含合同费用及不含税Net Net Cost(元)不能为空', 'error', '确定');
                    return true;
                }
                if ($scope.productDataTable[i].twoProductPrice == undefined) {
                    SIEJS.alert('产品报价表承载产品的包装价格,含O1%免费货,但不含税Net Net', 'error', '确定');
                    return true;
                }
                if ($scope.productDataTable[i].productName == undefined) {
                    SIEJS.alert('产品报价表名称不能为空', 'error', '确定');
                    return true;
                }
            }
            // 校验页面下方百分数必填
            if ($scope.editParams.firstPercent == undefined) {
                SIEJS.alert('产品备注不能为空', 'error', '确定');
                return true;
            }
            if ($scope.editParams.secondPercent == undefined) {
                SIEJS.alert('产品备注不能为空', 'error', '确定');
                return true;
            }
        };
        /********************保存报价单据信息********************/
        $scope.btnSave = function (status, invalid) {
            var str = '';
            //提交时校验必填
            if (status == 'commit' && $scope.editParams.docStatus == "QUOTATION") {
                str = '请保证所上传的扫描件与原件内容完全一致，且与原件具有同等法律效力！';
                if ($scope.validate()) {
                    return;
                }
            } else if (status == 'commit' && $scope.editParams.docStatus == "MODIFYING") {
                str = '点击确认后单据状态将跳转为“已截止”';
                if ($scope.validate()) {
                    return;
                }
            }
            //公共
            $scope.editParams.status = status;
            $scope.editParams.nopriceFileInfo = $scope.nonPriceFileDataTable;//非价格文件
            $scope.editParams.nopriceSelectFileInfo = $scope.nonPriceSelectFileDataTable;//非价格文件(选择文件)
            $scope.editParams.priceFileInfo = $scope.priceFileDataTable;//价格文件
            $scope.editParams.productInfo = $scope.productDataTable;// 产品文件
            // 如果是新增的话,需要给下面三个字段赋值
            for (var i = 0; i < $scope.editParams.productInfo.length; i++) {
                $scope.editParams.productInfo[i].supplierId = $scope.editParams.supplierId;
                $scope.editParams.productInfo[i].projectNumber = $scope.editParams.projectNumber;
                $scope.editParams.productInfo[i].quotationId = $scope.editParams.quotationId;
            }
            SIEJS.confirm(str, null, "确定", function () {
                httpServer.post(URLAPI.saveQuotationInfo, {
                    'editParams': JSON.stringify($scope.editParams)
                }, function (res) {
                    if ('S' == res.status) {
                        console.log(res);
                        $scope.editParams = res.data;
                        // $scope.editParams.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
                        $scope.findQuotationInfo();
                        $scope.searchNonPriceFileInfo();
                        $scope.searchNonPriceSelectFileInfo();
                        $scope.searchPriceFileInfo();
                        SIEJS.alert('操作成功');
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error);
                    SIEJS.alert('操作失败', 'error', '确定');
                })
            })
        };

        $scope.productTemplateExport = function () {
            httpServer.post(URLAPI.productTemplateExport, {
                'params': JSON.stringify(
                    {
                        "projectNumber": $scope.editParams.projectNumber,
                        "projectId": $scope.editParams.projectId,
                        "orderType": $scope.editParams.orderType,
                        "sourceId": $scope.editParams.sourceId
                    }
                )
            }, function (res) {
                if ('S' == res.status) {
                    window.open(res.data);
                    SIEJS.alert('导出成功');
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                SIEJS.alert('导出失败', 'error', '确定');
            })
        };

        // 产品行删除
        $scope.deleteData = function (row, index) {
            var quotationProductId = row.quotationProductId;
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                //如果没有id则执行删除
                if (quotationProductId == null || quotationProductId == '' || quotationProductId == undefined) {
                    $scope.productDataTable.splice(index, 1);
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                    return;
                }

                httpServer.post(URLAPI.deleteQuotationProductInfo, {
                    'params': JSON.stringify(row)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.findQuotationProductInfo();
                        SIEJS.alert("操作成功!已成功删除数据！", "success", "确定");
                    }
                    else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            })
        }
    });
});