/**
 * Created by sie_panshibang on 2019/9/17.
 */
define(["app"], function (app) {
    app.controller('equPosArchivesChangeCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction) {
        $scope.dataTable = [];

        $scope.params = {};
        $scope.flag = {};
        //当前登录人所属部门
        debugger;
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.flag.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;

        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
            // httpServer.post(URLAPI.findArchivesChangeOrder, {
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
        //新增供应商档案变更单据
        $scope.btnNew = function () {
            iframeTabAction('供应商档案变更详情', 'equPosArchivesChangeDetail/');
        }

        //查看/修改供应商档案变更单据
        $scope.saveOrUpdateStore = function (row) {
            iframeTabAction('供应商档案变更详情', 'equPosArchivesChangeDetail/' + row.changeId);
        }

        //删除
        $scope.delete = function(row){
            JS.confirm('删除','确定删除此档案变更单据？','确定',function() {
                httpServer.post(URLAPI.deleteArchivesChangeOrder,{
                    'params': JSON.stringify({
                        "changeId": row.changeId
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        JS.alert("删除成功!", "success", "确定");
                        $scope.search();
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                });
            })
        }
    }]);
});