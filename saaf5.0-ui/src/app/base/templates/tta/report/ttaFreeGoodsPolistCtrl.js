'use strict';
define(['app', 'pinyin', 'ztree','XLSX'], function (app, pinyin, ztree,XLSX) {
    app.controller('ttaFreeGoodsPolistCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer,validateForm,URLAPI, iframeTabAction, setNewRow,$http,saafLoading,tableXlsExport) {

        $scope.dataTable = {};
        //获取当前用户信息
        var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
        $scope.userType =  userLoginInfo.userType;//----------------45:为BIC类型  13:高级采购, 12:采购类型
        console.log("用户类型:"  + $scope.userType);
        $scope.params = {};
        $scope.currentList2 = [] ;
        $scope.requestParams ={};

        $scope.search = function () {
            $scope.dataTable.getData() ;
        };

        $scope.restore = function(){
            $scope.params = {};
        };

        $scope.btnDel = function () {
            var row = $scope.dataTable.selectRow;
            SIEJS.confirm('您确认要删除'+row.timesVersion+'批次数据吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.deleteImportFreeGoodsPolistInfo,

                    {
                        'params': JSON.stringify({
                            "timesVersion": row.timesVersion
                        })
                    },
                    function (res) {
                        if (res.status.toLowerCase() == 'e') {
                            SIEJS.alert(res.msg, "error");
                        } else {
                            SIEJS.alert(res.msg, "success");
                            $scope.dataTable.search(1);

                        }
                    }
                )
            });
        };

        $scope.btnModify=function(){
            $scope.addParams = {};
            $scope.modalTitle='编辑';
            $scope.addParams.promotionPrice = $scope.dataTable.selectRow.promotionPrice;
            $scope.addParams.isCalculate = $scope.dataTable.selectRow.isCalculate;
            $scope.addParams.chargeYear = $scope.dataTable.selectRow.chargeYear;
            $scope.addParams.openSelect = $scope.dataTable.selectRow.openSelect;
            $('#msgCfgModal').modal('toggle');
        };

        //保存操作
        $scope.btnSavePolist=function(){
            $scope.dataTable.selectRow.promotionPrice = $scope.addParams.promotionPrice;
            $scope.dataTable.selectRow.isCalculate = $scope.addParams.isCalculate;
            $scope.dataTable.selectRow.chargeYear = $scope.addParams.chargeYear;
            httpServer.post(URLAPI.freeGoodsPolistDetailSave, {
                params: JSON.stringify($scope.dataTable.selectRow)
            }, function (res) {
                if ('S' == res.status) {
                    $('#msgCfgModal').modal('toggle');
                    $scope.dataTable.search();
                    JS.alert('保存成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })

        };

        $scope.btnSave = function(){
            $scope.saveAll =  $scope.dataTable.data ;
            httpServer.post(URLAPI.freeGoodsPolistDetailBatchSave,
                {params: JSON.stringify({
                        save:$scope.saveAll
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert("保存成功", "success", "确定");
                        $scope.dataTable.getData() ;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        };

        $scope.daoru = function () {
            $('#excelTtaFreeGoodsPolist').modal('toggle');
            $scope.buttonFlag = true ;
        };

        // 查询导出结果
        function exportStatus(code) {
            var params = {
                params:"{Certificate:\""+code+"\"}"

            };
            $.ajax({
                url: URLAPI.getOiStatus,
                type: 'post',
                data: params,
                timeout: 60000 * 5,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
                    "Certificate": sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                },
                dataType: 'html',
                success: function (res) {
                    var res = JSON.parse(res);
                    if (res.status === 'S') {
                        jQuery("#value1").text(res.data.currentStage);
                        jQuery("#value2").text(res.data.orderNum);
                        $scope.dataTable.getData();
                        $('#excelTtaFreeGoodsPolist').modal('toggle');
                        clearInterval(exportTimer);
                    } else  if (res.status === 'E' || res.status === 'M') {
                        jQuery("#value1").text(res.data.currentStage);
                        jQuery("#value2").text(res.data.orderNum);
                        clearInterval(exportTimer);
                    }else{
                        $scope.messageCurrentStage = res.data.currentStage ;
                        jQuery("#value1").text(res.data.currentStage);
                        jQuery("#value2").text(res.data.orderNum);
                        $scope.messageCurrentOrderNum = res.data.orderNum ;
                    }

                },
                error: function (e) {
                    clearInterval(exportTimer);
                }
            });
        }
        var exportTimer;

        //上传附件
        $scope.save = function (invalid) {
            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            //saafLoading.show();
            var fd = new FormData();
            var file = document.getElementById('fileUpLoad').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            $scope.buttonFlag = false ;
            fd.append('file', file);
            //定时检测导入状态
            exportTimer = setInterval(function () {
                exportStatus(sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing');
            }, 1000);

            $http.post(URLAPI.saveImportFreeGoodsPolistInfo, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                //saafLoading.hide();
                //SIEJS.alert(response.msg, 'success', '确定');
                $scope.dataTable.getData();
                //$('#excelTtaFreeGoodsPolist').modal('hide');
            }).error(function(response) {
                //saafLoading.hide();
                SIEJS.alert(response.msg, 'error', '确定');
                $scope.buttonFlag = true ;
                clearInterval(exportTimer);
            });
        };

        /**
         * 多选
         * @param item
         * @param e
         * @param t
         */
        $scope.check = function (item, e, t) {
            $scope.flag2 = false;
            var checked = e.currentTarget.checked; // 当前选中状态
            var currentIsPush = true;
            var currentIndex = -1;
            for (var i = 0; i < $scope.currentList2.length; i++) {
                if (item.id === $scope.currentList2[i].id) {
                    currentIsPush = false;
                    currentIndex = i;
                    break;
                }
            }

            if (checked && currentIsPush && t !== false) {
                item.checked = true; // 选中标识
                $scope.currentList2.push(item)
            }
            if (!checked && !currentIsPush) {
                item.checked = false; // 选中标识
                $scope.currentList2.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－２０１８－１－９
            }
            if (checked && !currentIsPush) {
                item.checked = true; // 选中标识
            }
        };

        // 全选按钮
        $scope.checkedAll = function (e) {
            $scope.flag2 = false;
            if (e.target.checked) {// 全选
                $scope.selectedAll = true;
                for (var n in $scope.dataTable.data) {
                    var row = $scope.dataTable.data[n];
                    if (true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.currentList2) {
                            var list = $scope.currentList2[m];
                            if (row.id === list.id) {
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) $scope.currentList2.push(row);
                    }

                }
            } else {// 返选
                $scope.selectedAll = false;
                for (var n in $scope.dataTable.data) {
                    var row = $scope.dataTable.data[n];
                    row.checked = false;
                    var index = $scope.currentList2.indexOf(row);
                    $scope.currentList2.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                }
            }

        };

        $scope.btnBatchJoin = function () {
            if ($scope.currentList2 && $scope.currentList2.length === 0) {
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            $scope.requestParams = {};
            $('#fgBatchJoinCountModal').modal('toggle');
        };

        $scope.saveBatchJoin = function(form){
            if (!validateForm(form)) {
                return;
            }
            if ($scope.currentList2 && $scope.currentList2.length === 0) {
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            if ($scope.requestParams && !$scope.requestParams.isCalculate) {
                SIEJS.alert('请选择是否要加入计算', "error");
                return ;
            }
            httpServer.post(URLAPI.freeGoodsPolistDetailBatchJoin,
                {params: JSON.stringify({
                        requestParams:$scope.requestParams,
                        selectRowData:$scope.currentList2
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert("批量加入计算成功!", "success", "确定");
                        $scope.dataTable.getData();
                        $('#fgBatchJoinCountModal').modal('toggle');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        };

        //加入费用年度
        $scope.btnBatchJoinYear = function () {
            if ($scope.currentList2 && $scope.currentList2.length === 0) {
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            $scope.requestParams = {};
            $('#fgBatchJoinFeeYearModal').modal('toggle');
        };

        $scope.saveBatchJoinFeeYear = function(form){
            if (!validateForm(form)) {
                return;
            }
            if ($scope.currentList2 && $scope.currentList2.length === 0) {
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            if ($scope.requestParams && !$scope.requestParams.chargeYear) {
                SIEJS.alert('请选择费用年度', "error");
                return ;
            }
            httpServer.post(URLAPI.freeGoodsPolistDetailBatchJoinFeeYear,
                {params: JSON.stringify({
                        requestParams:$scope.requestParams,
                        selectRowData:$scope.currentList2
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert("批量加入费用年度成功!", "success", "确定");
                        $scope.dataTable.getData();
                        $('#fgBatchJoinFeeYearModal').modal('toggle');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        };

        //批量删除
        $scope.PLSC = function () {
            if ($scope.currentList2 && $scope.currentList2.length === 0) {
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            var ids = [];
            for (var i = 0; i < $scope.currentList2.length; i++) {
                var row = $scope.currentList2[i];
                ids.push(row.id);
            }
            SIEJS.confirm('提示', '您确定执行批量删除操作吗?', '确定', function () {
                httpServer.post(URLAPI.freeGoodsPolistDetailDelete,
                    {params: JSON.stringify({
                            ids:ids
                        })},
                    function (res) {
                        if (res.status == "S") {
                            $scope.currentList2 = [];
                            SIEJS.alert("删除成功", "success", "确定");
                            $scope.dataTable.getData();
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#saveButton").attr("disabled", true);
                    }
                );
            });
        };

        $scope.getSupplierCode = function () {
            $('#supplierCode').modal('toggle');
        };

        $scope.selectSupplierReturn = function (key, value, currentList) {
            $scope.params.supplierCode = currentList[0].supplierCode;
            $scope.params.supplierName = currentList[0].supplierName;
        };

        $scope.btnExport1 = function () {
            var name = 'poDataTable' ;
            var params = $scope.params;
            var count = $scope.poDataTable.count;
            tableXlsExport(name, params, count);
        };

    });
});
