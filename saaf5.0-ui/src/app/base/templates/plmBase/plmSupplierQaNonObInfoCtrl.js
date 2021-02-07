'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmSupplierQaNonObInfoCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;

        $scope.params = {};

        $scope.restore = function(){
            $scope.params = {};
            if($scope.userType === '13'){
                $scope.params.purchaseApprovalUser = $scope.userId;
                $scope.params.billStatus = 'PURCHASE_TODO';
            }
            else if($scope.userType === '75'){
                $scope.params.qaApprovalUser = $scope.userId;
                $scope.params.billStatus = 'QA_TODO';
            }
        };

        $scope.restore();


        $scope.btnNew = function () {
            iframeTabAction('新增资质组', 'plmSupplierQaNonObDetail/')
        };


        $scope.btnDetail = function (data) {
            iframeTabAction('资质组详情：'+data.qaGroupCode, 'plmSupplierQaNonObDetail/' + data.plmSupplierQaNonObInfoId);
        };








    });
});
