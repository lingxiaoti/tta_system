/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('purchaseSupplierDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, $http) {
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
        var id = $stateParams.id;
        $scope.showFlag = $scope.userData.userType == '45' ?  true : false;
        $scope.fileDataParams = {};

        $scope.opreateUpdateFlag = true;
        $scope.params = {};
        $scope.startFormInfo = "";
        $scope.headerParams = {};

        $scope.relSupplierInfo = {};

        $scope.supplierDeptInfo = {};

        $scope.supplierBrandInfo = {};
        $scope.fileInfo = {};

        $scope.cancelSupplier = function(){
            $scope.params.formalCode = '';
            $scope.params.formalName = '';
        }

        $scope.changeStatus = function(){
            debugger;
            if ($scope.params.status == 'N'){
                SIEJS.confirm('修改', '状态修改，确认把不提交proposal及不提交合同状态为否？', '确定', function () {
                    $scope.params.isSubmitContract = $scope.params.isSubmitProposal = 'N';
                });
            }

        }

        //查询单据信息
        $scope.search = function () {

            $scope.supplierId = id;
            $scope.relSupplierParams = {relId: $scope.supplierId};
            $scope.supplierDeptParams = {relId: $scope.supplierId};
            $scope.supplierBrandParams = {relId: $scope.supplierId};
            httpServer.post(URLAPI.supplierFindById, {
                    'params': JSON.stringify({supplierId: $scope.supplierId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.params = res.data;

                        $scope.relSupplierTable.search();
                        $scope.supplierDeptTable.search();
                        $scope.supplierBrandTable.search();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }
        //id不为空
        if ($stateParams.id) {

            //查询头信息
            $scope.search();
        } else {
            $scope.params.status = 'B';
            $scope.params.isLatent = 'Y';
        }

        $scope.fileDataParams = {functionId : "tta_supplier_attr", businessId : id};

        //下载附件事件
        $scope.downFileEvent = function (m) {
            //console.log(msg);
            if (m.fileId == undefined || m.fileId == null){
                SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
                return;
            }
            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + m.fileId;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };

        //新增附件
        $scope.addAttr = function () {
            if (!id) {
                SIEJS.alert('请先保存供应商信息！', 'error', '确定');
                return;
            }
            $scope.fileInfo = {};
            $("#fileUpLoad").val('');
            $('#fileLov').modal('toggle');
        };

        $scope.deletAttr = function () {
            var item = $scope.fileDataTable.selectRow;
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var p = {
                    fileId: item.businessId //文件id
                };
                httpServer.post(URLAPI.deletAttr, {
                    'params': JSON.stringify(p)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.fileDataTable.search(1);
                        SIEJS.alert(res.msg, 'success', '确定');
                        if (item.fileType == '承诺函'){ //承诺函
                            $scope.params.isCollect = 'N';
                            $scope.saveHeader();
                        }
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert("删除失败! " + err.msg, "error", "确定");
                });

            });
        };

        //上传附件
        $scope.saveFile = function (invalid) {
            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            var loading = $("#saafLoading");
            if (loading.length === 0) {
                var loading = $('<div id="saafLoading"><div  style="position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;"><img src="img/loading1.gif"></div></div>');
                loading.css({
                    position: "fixed",
                    top: 0,
                    width: "100%",
                    "z-index": 9000,
                    "height": "100%",
                    'display': 'none'
                });
                loading.prependTo(angular.element('body'));
            } else {
                loading.removeAttr('data-loading');
            }
            loading.show();
            var f = $scope.myFile;
            var fd = new FormData();
            var file = document.getElementById('fileUpLoad').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                loading.hide();
                return;
            }
            var fileName = file.name;
            fd.append('file', file);
            fd.append("fileName", fileName);
            fd.append("functionId", "tta_supplier_attr");
            $scope.fileInfo.supplierId = id;
            fd.append("fileInfo", JSON.stringify($scope.fileInfo));
            fd.append("businessId",id);
            $http.post(URLAPI.ttaSupplierAttrUploadFile, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                if (response.status == 'S') {
                    loading.hide();
                    $('#fileLov').modal('hide');
                    $scope.fileDataTable.search(1);
                    SIEJS.alert(response.msg, 'success', '确定');
                    if ($scope.fileInfo.fileType == '1'){ //承若菡
                        $scope.params.isCollect = 'Y';
                        $scope.saveHeader();
                    }
                } else {
                    loading.hide();
                    SIEJS.alert(response.msg, 'error', '确定');
                }
            }).error(function(response) {
                loading.hide();
                SIEJS.alert("上传失败", 'error', '确定');
            });
        };


        $scope.btnSave = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            $scope.saveHeader();
        }


        $scope.saveHeader = function() {
            httpServer.post(URLAPI.supplierSave, {
                    'params': JSON.stringify($scope.params)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.supplierId;
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
        }

        //新增供应商信息
        $scope.addRelSupplier = function () {
            $scope.relSupplierInfo = {relId: $scope.params.supplierId};
            $scope.opreateUpdateFlag = false;
            $('#relSupplierLov').modal('toggle');
        }


        //选择供应商
        $scope.getRelSupplierCode = function () {
            //  $scope.areaComponent = e;
            $('#prandSupplierCode').modal('toggle')
        };

        //点击确认按钮
        $scope.selectSupplierCodeReturn = function (key, value, currentList) {
            if($scope.params.supplierCode ==currentList[0].supplierCode){
                SIEJS.alert('供应商编码一致不能添加！', 'error', '确定');
                return ;
            }

            $scope.relSupplierInfo.relSupplierCode = currentList[0].supplierCode;
            $scope.relSupplierInfo.relSupplierName = currentList[0].supplierName;
            $scope.relSupplierInfo.relContractOutput = currentList[0].contractOutput;
        }


        //修改关联供应商信息
        $scope.editRelSupplier = function () {
            if ($scope.relSupplierTable.selectRowList.length != 1) {
                SIEJS.alert("请选择一行数据进行修改!", "error", "确定");
                return;
            }
            $scope.relSupplierInfo = $scope.relSupplierTable.selectRowList[0];
            $scope.opreateUpdateFlag = true;
            $('#relSupplierLov').modal('toggle');
        }

        //保存关联信息
        $scope.saveSupplier = function (invalid) {

            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            httpServer.post(URLAPI.supplierRelSave,
                {params: JSON.stringify($scope.relSupplierInfo)},
                function (res) {
                    if (res.status == "S") {
                        $scope.relSupplierTable.search(); //查询司机信息
                        SIEJS.alert("处理成功", "success", "确定");
                        $('#relSupplierLov').modal('hide');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        }
        //删除关联供应商信息
        $scope.delRelSupplier = function () {
            var selectRowList = $scope.relSupplierTable.selectRowList;
            if (!selectRowList.length) {
                SIEJS.alert("请选择要删除的数据!", "error", "确定");
                return;
            }
            var ids = "";
            for (var i = 0; i < selectRowList.length; i++) {
                ids = ids + selectRowList[i].relSupplierId + ",";
            }

            SIEJS.confirm('提示', '确定要删除所选的供应商信息吗？', '确定', function () {
                httpServer.post(URLAPI.supplierRelDel,
                    {params: JSON.stringify({ids: ids})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.relSupplierTable.selectRowList = [];
                            $scope.relSupplierTable.search();
                            SIEJS.alert("处理成功", "success", "确定");
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#btnArrival").attr("disabled", true);
                    }
                );
            });
        }


        //新增部门信息
        $scope.addSupplierDept = function () {
            $scope.supplierDeptInfo = {relId: $scope.params.supplierId};
            $('#supplierDeptLov').modal('toggle');
        }


        //选择部门
        $scope.getRelDeptCode = function () {
            //  $scope.areaComponent = e;
            $('#prandSupplierDept').modal('toggle')
        };

        //点击确认按钮
        $scope.selectSupplierDeptReturn = function (key, value, currentList) {
            $scope.supplierDeptInfo.relDeptCode = currentList[0].departmentCode;
            $scope.supplierDeptInfo.relDeptName = currentList[0].departmentName;

        }


        //修改关联部门信息
        $scope.editSupplierDept = function () {
            if ($scope.supplierDeptTable.selectRowList.length != 1) {
                SIEJS.alert("请选择一行数据进行修改!", "error", "确定");
                return;
            }
            $scope.supplierDeptInfo = $scope.supplierDeptTable.selectRowList[0];
            $('#prandSupplierDept').modal('toggle');
        }

        //保存部门信息
        $scope.saveSupplierDept = function (invalid) {

            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            httpServer.post(URLAPI.supplierDeptSave,
                {params: JSON.stringify($scope.supplierDeptInfo)},
                function (res) {
                    if (res.status == "S") {
                        $scope.supplierDeptTable.search(); //查询司机信息
                        SIEJS.alert("处理成功", "success", "确定");
                        $('#supplierDeptLov').modal('hide');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        }
        //删除部门信息
        $scope.delSupplierDept = function () {
            var selectRowList = $scope.supplierDeptTable.selectRowList;
            if (!selectRowList.length) {
                SIEJS.alert("请选择要删除的数据!", "error", "确定");
                return;
            }
            var ids = "";
            for (var i = 0; i < selectRowList.length; i++) {
                ids = ids + selectRowList[i].relSupplierId + ",";
            }


            SIEJS.confirm('提示', '确定要删除所选的部门信息吗？', '确定', function () {
                httpServer.post(URLAPI.supplierDeptDel,
                    {params: JSON.stringify({ids: ids})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.supplierDeptTable.selectRowList = [];
                            $scope.supplierDeptTable.search();
                            SIEJS.alert("处理成功", "success", "确定");
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#btnArrival").attr("disabled", true);
                    }
                );
            });
        }


        //新增品牌信息
        $scope.addSupplierBrand = function () {
            $scope.supplierBrandInfo = {relId: $scope.params.supplierId};
            $('#supplierBrandLov').modal('toggle');
        }


        //修改关联品牌信息
        $scope.editSupplierBrand = function () {
            if ($scope.supplierBrandTable.selectRowList.length != 1) {
                SIEJS.alert("请选择一行数据进行修改!", "error", "确定");
                return;
            }
            $scope.supplierBrandInfo = $scope.supplierBrandTable.selectRowList[0];
            $('#supplierBrandLov').modal('toggle');
        }

        //保存品牌信息
        $scope.saveSupplierBrand = function (invalid) {

            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            httpServer.post(URLAPI.supplierBrandSave,
                {params: JSON.stringify($scope.supplierBrandInfo)},
                function (res) {
                    if (res.status == "S") {
                        $scope.supplierBrandTable.search(); //查询司机信息
                        SIEJS.alert("处理成功", "success", "确定");
                        $('#supplierBrandLov').modal('hide');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        }
        //删除品牌信息
        $scope.delSupplierBrand = function () {
            var selectRowList = $scope.supplierBrandTable.selectRowList;
            if (!selectRowList.length) {
                SIEJS.alert("请选择要删除的数据!", "error", "确定");
                return;
            }
            var ids = "";
            for (var i = 0; i < selectRowList.length; i++) {
                ids = ids + selectRowList[i].relSupplierId + ",";
            }


            SIEJS.confirm('提示', '确定要删除所选的部门信息吗？', '确定', function () {
                httpServer.post(URLAPI.supplierBrandDel,
                    {params: JSON.stringify({ids: ids})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.supplierBrandTable.selectRowList = [];
                            $scope.supplierBrandTable.search();
                            SIEJS.alert("处理成功", "success", "确定");
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#btnArrival").attr("disabled", true);
                    }
                );
            });
        }


        //选择部门
        $scope.getBrandDeptCode = function () {
            //  $scope.areaComponent = e;
            $('#supplierBrandDept').modal('toggle')
        };

        //点击确认按钮
        $scope.selectBrandDeptReturn = function (key, value, currentList) {
            $scope.supplierBrandInfo.deptCode = currentList[0].departmentCode;
            $scope.supplierBrandInfo.deptName = currentList[0].departmentName;

        }

        //选择GROUP
        $scope.getBrandGroupCode = function (value) {
            //  $scope.areaComponent = e;
            $scope.getBrandGroupCodeFlag = value ;
            $('#supplierBrandGroup').modal('toggle')
        };

        //点击确认按钮
        $scope.selectBrandGroupReturn = function (key, value, currentList) {
            if('1' == $scope.getBrandGroupCodeFlag){
                $scope.supplierBrandInfo.groupCode = currentList[0].groupCode;
                $scope.supplierBrandInfo.groupName = currentList[0].groupDesc;
            }else{
                $scope.params.ownerGroup = currentList[0].groupCode;
                $scope.params.ownerGroupName = currentList[0].groupDesc;
            }

        }

        //BRAND
        $scope.getBrandBrandCode = function () {
            //  $scope.areaComponent = e;
            $('#supplierBrandBrand').modal('toggle')
        };

        //点击确认按钮
        $scope.selectBrandBrandReturn = function (key, value, currentList) {
            $scope.supplierBrandInfo.relBrandCode = currentList[0].brandCode;
            $scope.supplierBrandInfo.relBrandName = currentList[0].brandCn;
            $scope.supplierBrandInfo.relBrandNameEn = currentList[0].brandEn;
        }

        //选择部门
        $scope.getDeptCode = function () {
            //  $scope.areaComponent = e;
            $('#supplierHeadDept').modal('toggle')
        };

        //点击确认按钮
        $scope.selectHeadDeptReturn = function (key, value, currentList) {
            $scope.params.ownerDept = currentList[0].departmentCode;
            $scope.params.deptName = currentList[0].departmentName;

        }


        //选择正式供应商
        $scope.getLatenSupplierCode = function () {
            //  $scope.areaComponent = e;
            $('#latentSupplierCode').modal('toggle')
        };

        //点击确认按钮
        $scope.selectLatentSupplierReturn = function (key, value, currentList) {
            $scope.params.formalCode = currentList[0].supplierCode;
            $scope.params.formalName = currentList[0].supplierName;

        }


    });
});
