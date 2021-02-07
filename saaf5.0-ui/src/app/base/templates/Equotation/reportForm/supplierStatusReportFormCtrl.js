/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('supplierStatusReportFormCtrl', ['$scope', '$state', 'URLAPI', '$stateParams', 'iframeTabAction', "SIE.JS", 'httpServer', function ($scope, $state, URLAPI, $stateParams, iframeTabAction, JS, httpServer) {
        $scope.dataTable = {};
        $scope.dataTable.data = [];

        $scope.search = function () {
            httpServer.post(URLAPI.findSupplierStatusReportForm,
                {}, function (res) {
                    if (res.status == "S") {
                        $scope.dataTable.data = res.data;
                    }
                }, function (error) {
                    SIEJS.alert('查询数据详情失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error");
                });
        };

        //excel下载
        $scope.btnExport = function () {
            // if ($scope.params.batchId == undefined ||  $scope.params.batchId == '') {
            //     SIEJS.alert("缺失批次,导出失败", "error", "确定");
            //     return;
            // }

            var url = URLAPI.supplierStatusReportFormExcelExport;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        }
    }]);
});



