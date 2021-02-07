'use strict';
define(['app', 'pinyin', 'ztree', 'XLSX', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload, XLSX) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('editSpendUpdateCtrl', function (wechatFind, $filter, Base64, deleteUserResp, findProfileSqlDatas, $window,
                                                    saveUserResp, userSave, $timeout, $scope, httpServer, URLAPI, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS) {
        $scope.headData = {};
        $scope.lineData = {};
        $scope.editParams = {};
        $scope.dataTable = {};
        $scope.dataTable.data = [];
        $scope.currentIndex = 0;
        $scope.row = {};
        $scope.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.deptId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptId;
        $scope.index = 0;

        $scope.changeShowFlag = function () {
            $scope.saveFlag = false;
            $scope.submitFlag = false;
            $scope.editFlag = false;
            // 如果是批准状态,保存,提交不可见
            console.log($scope.headData.docStatus);
            if ($scope.headData.docStatus == 'APPROVAL') {
                $scope.saveFlag = false;
                $scope.submitFlag = false;
                $scope.editFlag = false;
            }
            if ($scope.headData.docStatus == 'DRAFT') {
                $scope.saveFlag = true;
                $scope.submitFlag = true;
                $scope.editFlag = true;
            }
        };

        var updateHeadId = $stateParams.updateHeadId;
        if (updateHeadId) {
            $scope.editParams.updateHeadId = updateHeadId;
            httpServer.post(URLAPI.findSpendUpdateHeadInfo, {
                editParams: JSON.stringify($scope.editParams)
            }, function (res) {
                if (res.status == "S") {
                    console.log(res);
                    $scope.headData = res.data;
                    $scope.headData.deptId = $scope.deptId;
                    $scope.headData.deptCode = $scope.deptCode;
                    $scope.search(updateHeadId);
                    $scope.changeShowFlag();
                }
            }, function (error) {
                SIEJS.alert('根据id查询数据详情失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error");
            });
        } else {
            $scope.headData = {};
            // 用户名
            $scope.headData.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
            $scope.headData.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
            $scope.headData.deptId = $scope.deptId;
            $scope.headData.deptCode = $scope.deptCode;
            $scope.headData.docStatus = 'DRAFT';
            $scope.submitFlag = false;
            $scope.changeShowFlag();
        }

        //增加按钮
        $scope.btnNew = function () {
            var qualityUpdateLineArray = {
                updateHeadId: $scope.headData.updateHeadId,
                supplierNumber: "",
                supplierName: "",
                score: "",
                result: "",
                beginValidDate: "",
                fileId: "",
                userNameLine: "",
                lastUpdateDate: ""
            };
            $scope.dataTable.data.push(qualityUpdateLineArray);
        };

        $scope.changeFileForHead = function () {
            $scope.headData.fileId = null;
            $scope.headData.filePath = null;
            $scope.headData.fileName = null;
        };
        //上传附件
        $scope.uploadFileForHead = function () {
            var fd = new FormData();
            var file = document.querySelector('#file1').files[0];
            if (!file) {
                SIEJS.alert("请选择上传附件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.editParams.updateHeadId;
            if (!id) {
                id = '0';
            }
            fd.append('businessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.fileUploadForSpendUpdateHead, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                SIEJS.alert(response.msg, 'success', '确定');
                console.log(response);
                $scope.headData.fileId = response.data[0].fileId;
                $scope.headData.fileName = response.data[0].fileName;
                $scope.headData.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        };

        $scope.validate = function () {
            for (var i = 0; i < $scope.dataTable.data.length; i++) {
                if ($scope.dataTable.data[i].supplierNumber == null || $scope.dataTable.data[i].supplierNumber == undefined) {
                    SIEJS.alert("供应商档案号不能为空!", 'error', '确定');
                    return true;
                }
                if ($scope.dataTable.data[i].score == null || $scope.dataTable.data[i].score == undefined) {
                    SIEJS.alert("分数不能为空!", 'error', '确定');
                    return true;
                }
                if ($scope.dataTable.data[i].beginValidDate == null || $scope.dataTable.data[i].beginValidDate == undefined) {
                    SIEJS.alert("有效期从不能为空!", 'error', '确定');
                    return true;
                }
                if ($scope.dataTable.data[i].endValidDate == null || $scope.dataTable.data[i].endValidDate == undefined) {
                    SIEJS.alert("有效期至不能为空!", 'error', '确定');
                    return true;
                }
            }
        };

        $scope.btnSave = function (status) {
            // 保存提交前校验
            if ($scope.validate()) {
                return
            }

            $scope.editParams = $scope.headData;
            $scope.editParams.status = status;
            $scope.editParams.lineData = $scope.dataTable.data;
            httpServer.post(URLAPI.saveSpendUpdateHeadAndLine, {
                'editParams': JSON.stringify($scope.editParams)
            }, function (res) {
                if (res.status == "S") {
                    $scope.editParams.updateHeadId = res.data;
                    // 保存后刷新
                    $scope.search(res.data);
                    SIEJS.alert("操作成功!", "success");
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };
        // 查询单条头行记录
        $scope.search = function (id) {
            httpServer.post(URLAPI.findSpendUpdateHeadInfo, {
                editParams: JSON.stringify({
                    updateHeadId: id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.headData = res.data;
                    $scope.headData.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
                    $scope.editParams.updateHeadId = id;
                    $scope.dataTable.getData();
                    $scope.changeShowFlag();
                }
            }, function (error) {
                console.error(error)
            })
        };

        //上传附件
        $scope.fileUploadForLine = function (element) {
            var fd = new FormData();
            var file = element.files[0];
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
            $http.post(URLAPI.fileUploadForSpendUpdateLine, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                console.log($scope.dataTable);
                $scope.index = element.getAttribute("data-attr-id");
                var index = $scope.index;
                $scope.dataTable.data[index].fileId = response.data[0].fileId;
                $scope.dataTable.data[index].fileName = response.data[0].fileName;
                $scope.dataTable.data[index].filePath = response.data[0].filePath;

            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        };

        //重新上传-供应商基础信息-附件
        $scope.changeFileForLine = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        };

        //导入回调方法:刷新数据
        $scope.returnFlash = function () {
            $scope.search($scope.editParams.updateHeadId);
        };

        // 供应商值列表查询
        //选择供应商
        $scope.selectSupplierInfo = function (index) {
            $scope.index = index;
            $('#selectSupplierLov').modal('toggle')
        };

        $scope.selectSupplierLov = function (key, value, currentList) {
            console.log($scope.index);
            $scope.dataTable.data[$scope.index].supplierNumber = currentList[0].supplierNumber;
            $scope.dataTable.data[$scope.index].supplierName = currentList[0].supplierName;
        };

        $scope.btnDel = function (row, index) {
            var updateLineId = row.updateLineId;
            console.log(updateLineId);
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if (updateLineId == null || updateLineId == '') {
                    $scope.dataTable.data.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                } else {
                    httpServer.post(URLAPI.deleteSpendUpdateLine, {
                        'params': JSON.stringify(row)
                    }, function (res) {
                        if (res.status == 'S') {
                            console.log($scope.editParams.updateHeadId);
                            $scope.search($scope.editParams.updateHeadId);
                            SIEJS.alert("删除成功", "success", "确定");
                        }
                        else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    }, function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });
                }
            })
        }
    });
});
