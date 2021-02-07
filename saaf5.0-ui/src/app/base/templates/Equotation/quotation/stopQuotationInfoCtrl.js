/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app", "angularFileUpload", "pinyin"], function (app, angularFileUpload, pinyin) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('stopQuotationInfoCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction', '$http', 'SIEJS', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction, $http, SIEJS) {
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
        $scope.editParams.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.editParams.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.editParams.departmentName = $scope.editParams.deptName;
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
                    $scope.editParams.quotationId = res.data.quotationId;
                    $scope.editParams.projectId = res.data.projectId;
                    if('QUOTATION'==res.data.docStatus || 'COMMIT' == res.data.docStatus){
                        $scope.editFlag = true;
                    }
                    if('MODIFIYING' == res.data.docStatus){
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

        //
        //
        // //初始化非价格文件选择文件
        // $scope.initNonPriceSelectFile = function () {
        //     httpServer.post(URLAPI.findProjectAttachment, {
        //         params: JSON.stringify({
        //             fileType:'FixedFile'
        //         })
        //     }, function (res) {
        //         if (res.status == 'S') {
        //             for(var i = 0; i < res.data.length; i++){
        //                 $scope.addNonPriceSelectFile();
        //                 $scope.nonPriceSelectFileDataTable[i].attachmentName = res.data[i].attachmentName;
        //                 $scope.nonPriceSelectFileDataTable[i].description = res.data[i].description;
        //                 $scope.nonPriceSelectFileDataTable[i].fileId = res.data[i].fileId;
        //                 $scope.nonPriceSelectFileDataTable[i].fileName = res.data[i].fileName;
        //                 $scope.nonPriceSelectFileDataTable[i].filePath = res.data[i].filePath;
        //                 $scope.nonPriceSelectFileDataTable[i].fileType = 'NonPriceSelectFile';
        //                 $scope.nonPriceSelectFileDataTable[i].index = res.data[i].index;
        //             }
        //         }
        //     }, function (error) {
        //         console.error(error)
        //     })
        // }

        //增加产品
        $scope.productAdd = function(){
            var productArray = {
                productName: "",
                firstProductPrice: "",
                twoProductPrice: "",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.productDataTable.push(productArray);
        };


        //导入回调方法:刷新数据
        $scope.returnFlash = function () {
            $scope.findQuotationInfo();
            $scope.searchNonPriceFileInfo();
            $scope.searchNonPriceSelectFileInfo();
            $scope.searchPriceFileInfo();
            $scope.findQuotationProductInfo();
        };

        //增加非价格文件
        $scope.addNonPriceFile = function () {
            var projectDetailArray = {
                attachmentName: "",
                description: "",
                fileId: "",
                fileName: "",
                filePath: "",
                fileType: "NonPriceFile",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.nonPriceFileDataTable.push(projectDetailArray);
        };

        //增加非价格文件
        $scope.addNonPriceSelectFile = function () {
            var projectDetailArray = {
                attachmentName: "",
                description: "",
                fileId: "",
                fileName: "",
                filePath: "",
                fileType: "NonPriceSelectFile",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.nonPriceSelectFileDataTable.push(projectDetailArray);
        };

        //增加价格文件
        $scope.addPriceFile = function () {
            var projectDetailArray = {
                attachmentName: "",
                description: "",
                fileId: "",
                fileName: "",
                filePath: "",
                fileType: "PriceFile",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.priceFileDataTable.push(projectDetailArray);
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

        /********************保存报价单据信息********************/
        $scope.btnSave = function (status,invalid) {
            console.log(status);
            //提交时校验必填
            if(status == 'commit'){
                if (invalid){
                    SIEJS.alert('请检查必填项', 'error', '确定');
                    return;
                }
            }
            //公共
            $scope.editParams.status = status;
            $scope.editParams.nopriceFileInfo = $scope.nonPriceFileDataTable;//非价格文件
            $scope.editParams.nopriceSelectFileInfo = $scope.nonPriceSelectFileDataTable;//非价格文件(选择文件)
            $scope.editParams.priceFileInfo = $scope.priceFileDataTable;//价格文件
            $scope.editParams.productInfo = $scope.productDataTable;// 产品文件
            httpServer.post(URLAPI.saveQuotationInfo, {
                'editParams': JSON.stringify($scope.editParams)
            }, function (res) {
                if ('S' == res.status) {
                    console.log(res);
                    $scope.editParams = res.data;
                    $scope.findQuotationInfo();
                    $scope.searchNonPriceFileInfo();
                    $scope.searchNonPriceSelectFileInfo();
                    $scope.searchPriceFileInfo();
                    // $scope.editParams.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
                    JS.alert('保存成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })
        };



    }]);
});