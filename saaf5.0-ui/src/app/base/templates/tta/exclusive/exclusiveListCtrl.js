'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('exclusiveListCtrl', function ($timeout, httpServer, $filter, userDel, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction,URLAPI) {
        $scope.standardType = $stateParams.type;
        $scope.params = {'agrtType':$scope.standardType};
        $scope.btnNew = function () {
            if ('nostandard' === $scope.standardType) {
                iframeTabAction('新增独家协议(非标准)', 'exclusiveDetail//nostandard')
            } else {
                iframeTabAction('独家协议(标准)新增', 'exclusiveDetail//standard');
            }

        };


        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            if ('nostandard' === $scope.standardType) {
                iframeTabAction('新增独家协议(非标准)', 'exclusiveDetail/'+ row.soleAgrtHId+'/nostandard');
            } else {
                iframeTabAction('独家协议(标准)编辑', 'exclusiveDetail/'+ row.soleAgrtHId+'/standard');
            }

        };

        $scope.selectSupplierReturn = function (key, value, currentList) {
            $scope.params.vendorCode = currentList[0].supplierCode;
            $scope.params.vendorName = currentList[0].supplierName;

        };

        $scope.selectGroupReturn = function (key, value, currentList) {
            $scope.params.deptCode = currentList[0].groupCode;
            $scope.params.deptName = currentList[0].groupDesc;
        };

     /*   $scope.btnDel = function (item) {
            item = item || $scope.dataTable.selectRow;

            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    userDel({params: JSON.stringify({id: item.ssoId})}, function (res) {
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
        }*/
    });
});
