'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmDevelopmentInfoCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};
        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;


        $scope.restore = function(){
            $scope.params = {};
            if($scope.userType==='60'){
                $scope.params.producer = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
            }
        };

        $scope.restore();

        $scope.btnNew = function () {
            iframeTabAction('新增产品', 'plmDevelopmentDetail/')
        };

        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            iframeTabAction('产品详情：'+row.productName, 'plmDevelopmentDetail/' + row.plmDevelopmentInfoId)
        };

        $scope.btnDetail = function (data) {
            iframeTabAction('产品详情：'+data.productName, 'plmDevelopmentDetail/' + data.plmDevelopmentInfoId);
        };

        $scope.btnDiscard = function () {

            $scope.discardReason = "";
            $('#discardModal').modal('show');

        };

        $scope.discardAction = function () {
            var params = angular.copy($scope.dataTable.selectRow);
            params.commandStatus = 'ABANDONED';
            // params.remarks = $scope.discardReason;
            if(params.remarks!==undefined&&params.remarks!=='') {
                params.remarks += '; 作废原因：' + $scope.discardReason;
            } else {
                params.remarks = '作废原因：' + $scope.discardReason;
            }

            httpServer.post(URLAPI.savePlmDevelopmentInfoInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status == 'S') {

                        $scope.dataTable.search();
                        $('#discardModal').modal('hide');

                        SIEJS.alert(res.msg, 'success','确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }





    });
});
