/**
 * Created by sie_panshibang on 2019/10/4.
 */
define(["app"], function (app) {
    app.controller('equPonScoringCtrl', ['$scope', 'URLAPI', '$rootScope', 'iframeTabAction','$state', '$stateParams', "SIE.JS", 'httpServer','$filter',function saafTableController($scope, URLAPI, $rootScope,iframeTabAction, $state, $stateParams,JS, httpServer,$filter) {
        $scope.dataTable = {}
        $scope.params = {};
        $scope.flag = {};
        //当前登录人所属部门
        debugger;
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.userFullName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userFullName;
        $scope.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;
        // $scope.params.employeeNumber = JSON.parse(sessionStorage[appName + '_successLoginInfo']).employeeNumber;
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }

        //新增评分
        $scope.btnNew = function () {
            iframeTabAction('评分详情', 'equPonScoringDetail///');
        }
        //查看/修改评分
        $scope.saveOrUpdate = function (row) {

            httpServer.post(URLAPI.findSupplierInvitation, {
                params: JSON.stringify({
                    projectId: row.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierInvitationDataTable = res.data;
                    var dataListOptions = '';
                    var dataMessage = '';
                    var options = '{';
                    options = options + 'itemDescription:{headName:\'item description\'},';
                    options = options + 'highestScore:{headName:\'Highest Score\'},';
                    options = options + 'benchmark:{headName:\'benchmark\'},';

                    var message = '';

                    for(var i = 0; i < $scope.supplierInvitationDataTable.length; i++){
                        var supplierRow = $scope.supplierInvitationDataTable[i];
                        if(supplierRow.isQuit != 'Y'){
                            options = options + $scope.supplierInvitationDataTable[i].supplierNumber +':{headName:\'' + $scope.supplierInvitationDataTable[i].supplierName + '\'},';
                            message = message + $scope.supplierInvitationDataTable[i].supplierName + '@';
                        }
                    }
debugger;
                    dataListOptions = options.substring(0, options.length - 1) + '}';
                    dataMessage = message;

                    iframeTabAction('评分详情', 'equPonScoringDetail/' + row.scoringId + '/' + dataListOptions + '/' + dataMessage);
                }
            }, function (error) {
                console.error(error)
            })
        }
        //删除立项
        $scope.deleteScoring = function(row){
            //$scope.program.createdBy != $scope.flags.userId
            if(row.createdBy != $scope.userId){
                JS.alert('当前用户非单据创建人，不能删除！', 'error', '确定');
                return false;
            }
            if(row.scoringStatus != '10'){
                JS.alert('单据状态为【' + row.scoringStatusMeaning + '】，不能删除！', 'error', '确定');
                return false;
            }
            JS.confirm('删除','确认删除评分单据？','确定',function() {
                httpServer.post(URLAPI.deleteScoringInfo,{
                    'params': JSON.stringify({
                        "id": row.scoringId
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
