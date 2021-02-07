/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('supplierBidStatusReportFormCtrl', ['$scope', '$state', 'URLAPI', '$stateParams', 'iframeTabAction', "SIE.JS", 'httpServer', function ($scope, $state, URLAPI, $stateParams, iframeTabAction, JS, httpServer) {
        $scope.dataTable = {};
        $scope.dataTable.data = [];
        $scope.params = {};

        $scope.search = function (supRecoverId) {
            $scope.params.supRecoverId = supRecoverId;
            httpServer.post(URLAPI.findSupRecoverDatail, {
                params: JSON.stringify($scope.params)
            }, function (res) {
                if (res.status == "S") {
                    $scope.params = res.data;
                    $scope.changeShowFlag();
                    $scope.processInstanceParams = {
                        processInstanceId: $scope.params.procInstId
                    };
                    $scope.taskTable.search();
                    $scope.params.supRecoverStatus == '20'
                }
            }, function (error) {
                console.error(error);
            });
        };

    }]);
});



