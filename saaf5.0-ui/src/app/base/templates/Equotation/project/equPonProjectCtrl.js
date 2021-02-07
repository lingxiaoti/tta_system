/**
 * Created by sie_panshibang on 2019/10/4.
 */
define(["app"], function (app) {
    app.controller('equPonProjectCtrl', ['$scope', 'URLAPI', '$rootScope', 'iframeTabAction','$state', '$stateParams', "SIE.JS", 'httpServer','$filter',function saafTableController($scope, URLAPI, $rootScope,iframeTabAction, $state, $stateParams,JS, httpServer,$filter) {
        $scope.dataTable = {}
        $scope.params = {};
        $scope.flag = {};
        //当前登录人所属部门
        debugger;
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.userFullName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userFullName;
        $scope.params.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }

        //新增立项
        $scope.btnNew = function () {
            iframeTabAction('立项详情', 'equPonProjectDetail//N');
        }
        //查看/修改立项
        $scope.saveOrUpdate = function (row) {
            iframeTabAction('立项详情', 'equPonProjectDetail/' + row.projectId + "/N");
        }
        //删除立项
        $scope.deleteProject = function(row){
            if(row.projectStatus != '10'){
                JS.alert('立项单据不是拟定状态，不能删除！', 'error', '确定');
                return false;
            }
            JS.confirm('删除','确认删除立项？','确定',function() {
                httpServer.post(URLAPI.deleteProjectInfo,{
                    'params': JSON.stringify({
                        "id": row.projectId
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
