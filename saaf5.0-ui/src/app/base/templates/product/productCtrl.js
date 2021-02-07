/**
 * Created by zhuchaopeng on 2018/6/15.
 */
define(["app"], function (app) {
    app.controller('productCtrl', ['$scope', 'URLAPI', '$rootScope', 'iframeTabAction','$state', '$stateParams', "SIE.JS", 'httpServer',function saafTableController($scope, URLAPI, $rootScope,iframeTabAction, $state, $stateParams,JS, httpServer) {
        $scope.dataTable = {}
        $scope.productId=$stateParams.productId;
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }

        //新增
        $scope.btnNew = function () {
            $("#fillStatus").attr("disabled", true);
            $scope.modalTitle = '产品信息新增';
            $('#modiProduct').modal('show');
            $scope.paramsProduct={};
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
            $scope.paramsProduct = angular.copy(row);//获取值
            $scope.modalTitle = '产品信息修改';
            $('#modiProduct').modal('show');
        }
        //保存
        $scope.btnSave = function () {
            httpServer.post(URLAPI.saveProductInfo, {
                'params': JSON.stringify($scope.paramsProduct)
            }, function (res) {
                if (res.status == 'S') {
                    $('#modiProduct').modal('hide');
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

        /*********************查询组织信息************************/
        $scope.getOrganizationInfo = function () {
            $('#orgInfo').modal('toggle')
        };
        $scope.selectOrgReturn = function (key, value, currentList) {
            $scope.paramsProduct.orgId = currentList[0].orgId;
            $scope.paramsProduct.orgName = currentList[0].orgName;
        };

        $scope.getBigCatInfo = function () {
            $('#findBigCategoryLov').modal('toggle')
        };

        $scope.findLookupLineItemCallBack = function (key, value, currentList) {
            $scope.paramsProduct.bigCategory = currentList[0].lookupCode;
            $scope.paramsProduct.bigCategoryName = currentList[0].meaning;

        }

        $scope.getSmallCatInfo = function () {
            $('#findSmallCategoryLov').modal('toggle')
        };
        $scope.findLookupLineItemCallBack2 = function (key, value, currentList) {
            $scope.paramsProduct.smallCategory = currentList[0].lookupCode;
            $scope.paramsProduct.smallCategoryName = currentList[0].meaning;

        }

        $scope.getModelInfo = function () {
            $('#findModelLov').modal('toggle')
        };
        $scope.findLookupLineItemCallBack3 = function (key, value, currentList) {
            $scope.paramsProduct.modelCode = currentList[0].lookupCode;
            $scope.paramsProduct.modelName = currentList[0].meaning;

        }

    }]);
});
