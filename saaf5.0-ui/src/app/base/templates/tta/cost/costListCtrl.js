'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('costListCtrl', function ($timeout, httpServer, $filter, userDel, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction,URLAPI) {
        $scope.current = {}
        $scope.current.disabled = false;
        $scope.params = {}
        $scope.btnNew = function () {
            $scope.current = {}
            $('#costModal').modal('toggle')
        }

        /**
         * 当重新选择接口时，清空规则信息
         */
        $scope.changeAllocationBase = function () {
            $scope.current.ruleSourceData = "";
        }

        // 显示渠道搜索LOV
        $scope.showChannel = function () {
            if (!$scope.current.allocationBase) {
                SIEJS.alert(res.msg, 'error', '确定');
                return;
            }
            $("#findRuleSave").modal('show');
        }

        $scope.btnModify = function () {
            $scope.current = angular.copy($scope.dataTable.selectRow)
            $scope.current.disabled = true;
            $('#costModal').modal('toggle');
        }


        $scope.confirmData = function () {
            httpServer.post(URLAPI.saveOrUpdateCostRule, {
                params: JSON.stringify($scope.current)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.dataTable.search();
                    $('#costModal').modal('toggle');
                    SIEJS.alert(res.msg, 'success', '确定');
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                SIEJS.alert('删除失败', 'error', '确定');
            })
        }


        $scope.btnDel = function (item) {
            var item = item || $scope.dataTable.selectRow;
            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                    httpServer.post(URLAPI.deleteById, {
                        params: JSON.stringify({id : item.id})
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            SIEJS.alert('删除成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        SIEJS.alert('删除失败', 'error', '确定');
                    })
                })
            }
        }

    });
});
