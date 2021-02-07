/**
 * Created by sie_panshibang on 2019/9/17.
 */
define(["app"], function (app) {
    app.controller('equPosQualityAuditCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction) {
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
        }
        //重置
        $scope.restore = function(){
            $scope.params = {};
            $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
            $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        }
        //新增质量审核单据
        $scope.btnNew = function () {
            iframeTabAction('质量审核详情', 'equPosQualityAuditDetail/');
        }

        //查看/修改质量审核单据
        $scope.saveOrUpdateStore = function (row) {
            iframeTabAction('质量审核详情', 'equPosQualityAuditDetail/' + row.qualityAuditId);
        }

        //删除
        $scope.delete = function(row){
            JS.confirm('删除','确定删除质量审核？','确定',function() {
                //校验非拟定状态单据不能删除
                if(row.csrAuditStatus != 'DRAFT'){
                    JS.alert('单据非拟定状态不能删除！', 'error', '确定');
                    return false;
                }
                httpServer.post(URLAPI.deleteSupplierQualityAudit,{
                    'params': JSON.stringify({
                        "id": row.qualityAuditId
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