/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('supplierReportFormCtrl', ['$scope', '$state', 'URLAPI', '$stateParams', 'iframeTabAction', "SIE.JS", 'httpServer', function ($scope, $state, URLAPI, $stateParams, iframeTabAction, JS, httpServer) {
        $scope.dataTable = {};


        $scope.dataTable.data = [];
        $scope.params = {};

        $scope.search = function () {
            httpServer.post(URLAPI.findSupplierReportForm,
                {
                    'params': JSON.stringify($scope.params)
                }, function (res) {
                    if (res.status == "S") {
                        $scope.dataTable.data = res.data;
                    }
                }, function (error) {
                    SIEJS.alert('查询数据详情失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error");
                });
        };
    }]);
});



