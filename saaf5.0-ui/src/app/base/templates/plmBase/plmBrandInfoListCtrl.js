'use strict';
define(['app', 'pinyin', 'ztree','XLSX'], function (app, pinyin, ztree, XLSX) {
    app.controller('plmBrandInfoListCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;

        $scope.btnDetail = function (data) {
            iframeTabAction('品牌详情：'+data.plmBrandCn, 'plmBrandInfoDetail/' + data.plmBrandInfoId);
        };

        $scope.btnNew = function () {
            iframeTabAction('新增品牌：', 'plmBrandInfoDetail/');
        };

        $scope.btnModify = function () {
            var headerData = $scope.dataTable.selectRow;
            iframeTabAction('品牌详情：'+headerData.plmBrandCn, 'plmBrandInfoDetail/' + headerData.plmBrandInfoId);
        };

        $scope.restore = function () {
            $scope.params = {};
            if($scope.userType==='45'){
                $scope.params.bic = $scope.userId;
                $scope.params.billStatus = 'BIC_IN_APPROVAL';
            }
            if($scope.userType==='46'){
                $scope.params.ba = $scope.userId;
                $scope.params.billStatus = 'BA_IN_APPROVAL';
            }
            if($scope.userType==='47'){
                $scope.params.ta = $scope.userId;
                $scope.params.billStatus = 'TA_IN_APPROVAL';
            }
        };

        $scope.restore();

        $scope.btnDiscard = function () {
            var data = angular.copy($scope.dataTable.selectRow);
            data.billStatus = 'N';
            data.billStatusName = '已失效';
            SIEJS.confirm('作废', '是否确定作废？', '确定', function () {
                httpServer.post(URLAPI.savePlmBrandInfoInfo, {
                        'params': JSON.stringify(data)
                    },
                    function (res) {
                        if (res.status === 'S') {
                            $scope.dataTable.search(1);

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






    });
});
