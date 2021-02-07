/**
 * Created by sie_panshibang on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('qualificationCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction) {
        $scope.dataTable = [];

        $scope.params = {};
        $scope.flag = {};
        //当前登录人所属部门
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.flag.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.flag.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;
        $scope.flag.supplierId = 0;

        if($scope.flag.userType == '60'){
            httpServer.post(URLAPI.findZzscSupplierContactsInfo, {
                'params': JSON.stringify({
                    "userId": $scope.flag.userId
                })
            }, function (res) {
                if (res.status == 'S') {
                    debugger;
                    $scope.flag.supplierId = res.data[0].supplierId;
                    $scope.params.supplierId = res.data[0].supplierId;
                    $scope.params.deptCode = res.data[0].deptCode;
                    if($scope.params.deptCode == '0E'){
                        $scope.params.deptName = 'OEM and QA';
                    }else if($scope.params.deptCode == '03'){
                        $scope.params.deptName = '电脑部';
                    }
                }
            }, function (error) {
                console.error(error)
            })
        }

        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }
        // 重置
        $scope.restore = function(){
            $scope.params = {};
            $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
            $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            if($scope.flag.userType == '60'){
                $scope.params.supplierId = $scope.flag.supplierId;
            }
        }
        //新增资质审查单据
        $scope.btnNew = function () {
            iframeTabAction('资质审查详情', 'qualificationDetail/');
        }

        //查看/修改资质审查单据
        $scope.saveOrUpdate = function (row) {
            iframeTabAction('资质审查详情', 'qualificationDetail/' + row.qualificationId);
        }

        //删除
        $scope.delete = function(row){
            if(row.qualificationStatus != '10' && row.qualificationStatus != '60'){
                JS.alert("只有状态为拟定或驳回的单据才允许删除!", "error", "确定");
                return;
            }
            JS.confirm('删除','是否确定删除？','确定',function() {
                httpServer.post(URLAPI.deleteQualificationInfo, {
                        'params': JSON.stringify(row)
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
