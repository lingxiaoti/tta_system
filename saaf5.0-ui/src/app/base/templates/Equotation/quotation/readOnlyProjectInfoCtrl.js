/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app", "angularFileUpload", "pinyin"], function (app, angularFileUpload, pinyin) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('readOnlyProjectInfoCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction', '$http', 'SIEJS', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction, $http, SIEJS) {
        var id = $stateParams.projectId;
        $scope.params = {};
        $scope.program = {};
        //非价格文件
        $scope.nonPriceFileDataTable = [];
        //非价格文件(固定附件)
        $scope.nonPriceSelectFileDataTable = [];
        //价格文件
        $scope.priceFileDataTable = [];

        //当前登录人所属部门
        $scope.program.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.program.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

        var departmentName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.program.departmentName = $scope.program.deptName;

        /********************查询非价格文件信息********************/
        $scope.searchNonPriceFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId,
                    fileType: "NonPriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.nonPriceFileDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询非价格文件(选择)信息********************/
        $scope.searchNonPriceSelectFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId,
                    fileType: "NonPriceSelectFile",
                    selectedFlag2: "Y"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.nonPriceSelectFileDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询价格文件信息********************/
        $scope.searchPriceFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.program.projectId,
                    fileType: "PriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.priceFileDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        // 查询立项信息
        $scope.program.projectId = id;
        console.log($scope.program);
        httpServer.post(URLAPI.findProjectInfo, {
            params: JSON.stringify($scope.program)
        }, function (res) {
            if (res.status == 'S') {
                console.log(res);
                $scope.program = res.data[0];
                $scope.searchNonPriceFileInfo();
                $scope.searchNonPriceSelectFileInfo();
                $scope.searchPriceFileInfo();
                $("input").attr("disabled",true);
            }
        }, function (error) {
            console.error(error)
        });
    }]);
});