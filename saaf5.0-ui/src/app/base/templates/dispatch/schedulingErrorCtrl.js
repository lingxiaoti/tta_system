/**
 * Created by Administrator on 2018/3/27.
 */
'use strict';
define(['app'], function (app) {
    app.controller('schedulingErrorCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,schedulingErrorDetail) {
        $scope.params = {};
        $scope.btnNew = function () {
            $('#addSourceType').modal('show');
        };
        $scope.btnModify = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
        };
        $scope.btnDel = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
        };
        $scope.save = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            log($scope.addParams)
        }

        // 显示详情
        $scope.btnDetail=function(item){

            item = item || $scope.dataTable.selectRow;

            schedulingErrorDetail({
                params:JSON.stringify({
                    errorId:item.errorId
                })
            },function(res){
                $scope.logDetail=res.data;
                $("#lookLog").modal("show");
            })




        }


    });
});
