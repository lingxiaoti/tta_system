/**
 * Created by sie_panshibang on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('supplierFilesCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction) {
        $scope.dataTable = [];

        $scope.params = {};
        $scope.flag = {};
        //当前登录人所属部门
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.flag.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;

        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
            // httpServer.post(URLAPI.findSupplierFiles, {
            //     params: JSON.stringify($scope.params)
            // }, function (res) {
            //     if (res.status == 'S') {
            //         $scope.dataTable = res.data;
            //
            //     }
            // }, function (error) {
            //     console.error(error)
            // })
        }
        //重置
        $scope.restore = function(){
            $scope.params = {};
            $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
            $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        }
        //供应商档案详情
        $scope.saveOrUpdateStore = function (row) {
            iframeTabAction('供应商档案详情', 'supplierFilesDetail/' + row.supplierId + '/' + row.supplierName);
        }

        //选择品类
        $scope.getCategoryInfo = function () {
            $scope.selectCategoryParams = {
                "deptCode" : $scope.params.deptCode
            };
            $('#selectCategoryLov').modal('toggle')
        };

        //选择品类-回调
        $scope.selectCatetoryReturn = function (key, value, currentList) {
            console.log(currentList[0]);
            $scope.params.categoryId = currentList[0].categoryMaintainId;
            $scope.params.categoryName = currentList[0].categoryGroup;
        }
    }]);
});
