/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('quotationInfoCtrl', ['$scope', '$state', 'URLAPI', '$stateParams', 'iframeTabAction', "SIE.JS", 'httpServer', function ($scope, $state, URLAPI, $stateParams, iframeTabAction, JS, httpServer) {
        $scope.dataTable = {};
        // $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        var docStatus = $stateParams.docStatus.toUpperCase();
        // 报价中有三种单据状态，报价中和已提交，修改报价中
        if (docStatus == "QUOTATION") {
            $scope.params = {"docStatus": "('QUOTATION','COMMIT','MODIFYING')"};
            $scope.toEditQuotationInfo = function (row) {
                iframeTabAction('报价管理报价详情', 'editQuotationInfo/' + $stateParams.docStatus + '/' + row.quotationId);
            };
        } else if (docStatus == "STOP") {
            $scope.params = {"docStatus": "STOP"};
            $scope.toEditQuotationInfo = function (row) {
                iframeTabAction('报价管理截止详情', 'stopQuotationInfo/' + $stateParams.docStatus + '/' + row.quotationId);
            };
        } else if (docStatus == "COMPLETE") {
            $scope.params = {"docStatus": "COMPLETE"};
            $scope.toEditQuotationInfo = function (row) {
                iframeTabAction('报价管理完成详情', 'completeQuotationInfo/' + $stateParams.docStatus + '/' + row.quotationId);
            };
        } else if (docStatus == "BARGAIN") {
            $scope.params = {"docStatus": "BARGAIN"};
            $scope.toEditQuotationInfo = function (row) {
                iframeTabAction('报价管理待议价详情', 'bargainQuotationInfo/' + $stateParams.docStatus + '/' + row.quotationId);
            };
        } else if (docStatus == "BREAK") {
            $scope.params = {"docStatus": "BREAK"};
            $scope.toEditQuotationInfo = function (row) {
                iframeTabAction('报价管理已终止详情', 'breakQuotationInfo/' + $stateParams.docStatus + '/' + row.quotationId);
            };
        }
        // 编辑--跳转功能
        $scope.toReadOnlyForProjectInfo = function (row) {
            iframeTabAction('查看立项详情', 'readOnlyProjectInfo/' + row.projectId);
        };
    }]);
});



