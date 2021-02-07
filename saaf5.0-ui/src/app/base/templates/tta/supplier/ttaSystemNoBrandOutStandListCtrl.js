'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    //app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('ttaSystemNoBrandOutStandListCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams,$timeout, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow,lookupCode) {
        $scope.searchParam = {};
        $scope.outStandListTable = {};
        var date=new Date;
        $scope.searchParam.year = date.getFullYear();
        $scope.outStandListTable.selectRowData= null ;
        $scope.selectRowList= [] ;
        $scope.currentSelectList = [];
        $scope.recordingList='true';

        $scope.lookupCodeParty = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
        if (!$scope.lookupCodeParty) {
            lookupCode(function (res) {
                $scope.lookupCodeParty = $rootScope.lookupCode = res.data;
            });
        }

        /*监听$scope.selectRowList*/
        $scope.$watch('outStandListTable.data', function (newVal, oldVal) {
            if (newVal === oldVal || !newVal || !$scope.flag){
                if(!$scope.flag){
                    $scope.flag = true ;
                }
                return;
            }
            $timeout(function () {
                $scope.selectRowList = [] ;
            })
        }, true);

        //刷新品牌信息
        $scope.refreshBrandBtn = function () {
            if (!$scope.searchParam.year) {
                SIEJS.alert("刷新前请先填写年度", "warning", "确定");
                return;
            }

            SIEJS.confirm('刷新', '确定刷新吗？', '确定', function () {
                httpServer.post(URLAPI.noSupplierBrandRefreshBrandInfo, {
                        'params': JSON.stringify($scope.searchParam)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            $scope.outStandListTable.getData();

                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            })
        }

        // 全选按钮
        $scope.checkedAll = function (e) {
            $scope.flag = false ;
            if (e.target.checked) {// 全选
                $scope.selectedAll = true;
                for (var n in $scope.outStandListTable.data) {
                    var row = $scope.outStandListTable.data[n];
                    if (true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.selectRowList) {//选中的数据
                            var list = $scope.selectRowList[m];
                            if (row.supplierBrandId === list.supplierBrandId) {//如果存在,就放入选中的集合中
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) {
                            $scope.selectRowList.push(row);
                            $scope.currentSelectList.push(row.supplierBrandId);
                        }
                    }

                }
            } else {// 反选
                $scope.selectedAll = false;
                for (var n in $scope.outStandListTable.data) {
                    var row = $scope.outStandListTable.data[n];
                    row.checked = false;
                    var index = $scope.selectRowList.indexOf(row);
                    var supplierIdIdx = $scope.currentSelectList.indexOf(row.supplierBrandId);
                    $scope.selectRowList.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                    $scope.currentSelectList.splice(supplierIdIdx--, 1);
                }
            }

        };

       //多选
        $scope.check = function (item, e, t) {
            $scope.flag = false ;
            var checked = e.currentTarget.checked; // 当前选中状态
            var currentIsPush = true;
            var currentIndex = -1;
            for (var i = 0; i < $scope.selectRowList.length; i++) {
                if (item.supplierBrandId === $scope.selectRowList[i].supplierBrandId) {
                    currentIsPush = false;
                    currentIndex = i;
                    break;
                }
            }

            if (checked && currentIsPush && t !== false) {//如果是选中的并且未放入选中的选中集合中的,那么就放入到选中的集合中
                item.checked = true; // 选中标识
                $scope.selectRowList.push(item);
                $scope.currentSelectList.push(item.supplierBrandId);
            }
            if (!checked && !currentIsPush) {
                item.checked = false; // 选中标识
                $scope.selectRowList.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－
                $scope.currentSelectList.splice(currentIndex, 1);
            }
            if (checked && !currentIsPush) {
                item.checked = true; // 选中标识
            }
        };

        $scope.btnSave = function () {
            //$scope.outStandListTable.data
            if ($scope.selectRowList.length == 0) {
                SIEJS.alert("请选择需要保存的行", "warning", "确定");
                return;
            }
            httpServer.post(URLAPI.noSupplierBrandSaveOrUpdate, {
                    'params': JSON.stringify({
                        data:$scope.selectRowList
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.outStandListTable.getData();
                        SIEJS.alert("保存成功", "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        //生成供应商品牌
        $scope.createSupplierBrandBtn = function () {
            if ($scope.selectRowList.length == 0) {
                SIEJS.alert("请选择需要生成供应商品牌的数据", "warning", "确定");
                return;
            }
            SIEJS.confirm('提示', '只有状态为生效的数据才会生成供应商品牌信息,您确定生成吗？', '确定', function () {
                httpServer.post(URLAPI.noSupplierBrandCreateSupplierBrand, {
                        'params': JSON.stringify({
                            data: $scope.selectRowList
                        })
                    },
                    function (res) {
                        if (res.status == 'S') {
                            $scope.outStandListTable.getData();
                            SIEJS.alert("生成供应商品牌成功", "success", "确定");
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
    });
});
