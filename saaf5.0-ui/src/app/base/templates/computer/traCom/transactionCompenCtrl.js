/**
 * Created by dengdunxin on 2018/1/3.
 */
define(["app"], function (app) {
    app.controller('transactionCompenCtrl', function ($scope, URLAPI, $rootScope, $state, $stateParams, iframeTabAction, httpServer,SIEJS) {
        //do something here

        $scope.params={

        }
        $scope.btnNewTransaction= function () {
            if(!$scope.dataTable.selectRow){
                SIEJS.alert('请选择新增提交事务', "error", "确定");
                return
            }
            httpServer.post(URLAPI.retryCompensation, {
                    params:JSON.stringify({"queueName":$scope.dataTable.selectRow.queueName,"messageIdIndex":$scope.dataTable.selectRow.messageIdIndex})
            },
            function (res) {
                if(res.status=='S'){
                    $scope.dataTable.search(1)
                    SIEJS.alert('提交成功', "success", "确定");
                }else{
                    SIEJS.alert(res.msg, "error", "确定");
                }

            });
        }

    });
});
