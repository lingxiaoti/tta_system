/**
 * Created by zhuchaopeng on 2018/6/15.
 */
define(["app"], function (app) {
    app.controller('baseModelCtrl', ['$scope', 'URLAPI', '$rootScope', 'iframeTabAction','$state', '$stateParams', "SIE.JS", 'httpServer',function saafTableController($scope, URLAPI, $rootScope,iframeTabAction, $state, $stateParams,JS, httpServer) {
        $scope.dataTable = {}
        $scope.componentId=$stateParams.componentId;
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }

        //新增
        $scope.btnNew = function () {
            $("#fillStatus").attr("disabled", true);
            $scope.modalTitle = '基础型号新增';
            $('#modiBaseModel').modal('show');
            $scope.paramsModel={};

            $scope.priceParams = {
                "componentId": -1,
                "psiSalesItemId": -1
            };
            $scope.dataPriceHistoryRegion.search();
        }
        //修改
        $scope.btnModify = function () {
            $("#fillStatus").attr("disabled", true);
            if ($scope.dataTable.selectRow == null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            var row = $scope.dataTable.selectRow;
            // $scope.adjustAmountDisabled = false;
            $scope.paramsModel = angular.copy(row);//获取值
            $scope.modalTitle = '基础型号修改';
            $('#modiBaseModel').modal('show');

            $scope.priceParams = {
                "componentId": $scope.paramsModel.componentId,
                "psiSalesItemId": $scope.paramsModel.baseModelId
            };
            $scope.dataPriceHistoryRegion.search();
        }
        //保存
        $scope.btnSave = function () {
            httpServer.post(URLAPI.saveBaseModelInfo, {
                'params': JSON.stringify($scope.paramsModel)
            }, function (res) {
                if (res.status == 'S') {
                    $('#modiBaseModel').modal('hide');
                    // $scope.paramsCustomer = null;
                    JS.alert("保存成功", "success", "确定");
                    $scope.dataTable.search();
                }
                else {
                    JS.alert(res.msg, "error", "确定");
                    $("#saveButton").removeAttr("disabled");
                }
            }, function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                $("#saveButton").removeAttr("disabled");
            });
        }

        //导入psi
        $scope.btnImport = function () {
            $("#fillStatus").attr("disabled", true);
            if ($scope.dataTable.selectRow == null) {
                JS.alert('请您先选中要导入的行!!');
                return;
            }
            httpServer.post(URLAPI.synBaseModelInfo, {
                'params': JSON.stringify($scope.dataTable.selectRow)
            }, function (res) {
                if (res.status == 'S') {
                    JS.alert("导入PSI成功!", "success", "确定");
                }
                else {
                    JS.alert(res.msg, "error", "确定");
                    $("#saveButton").removeAttr("disabled");
                }
            }, function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                $("#saveButton").removeAttr("disabled");
            });
        }
    }]);
});
