/**
 * Created by hemingxing on 2017/08/04.
 */
define(['app'], function(app) {
    app.controller('serviceCtrlOld',['$scope','httpServer','URLAPI','SIE.JS',function($scope,httpServer,URLAPI,JS) {
        //---------------------------------------
        $scope.businessTable = {};
        $scope.businessParams = {};

        $scope.serverTable = {};
        $scope.serverParams = {}

        $scope.serverTableStatus = false;
        $scope.actionStatus = false;

        $scope.serverModalTitle = '新增服务';

        $scope.websericeAgreementData = ['REST', 'SOAP'];
        $scope.webserviceTypeData = ['GET', 'POST'];


        $scope.selectBusiness = function(item) {
            $scope.serverParams.var_equal_businessLineCode = item.ruleBusinessLineCode;
            $scope.serverTable.getData();
            $scope.serverTableStatus = true;
            $scope.actionStatus = true;
        }

        //回车搜索业务线
        //已取消2017-08-17
        // $scope.searchKeyupBusiness = function(event) {
        //     if(event.keyCode == 13) {
        //         $scope.businessTable.getData();
        //         $scope.serverTableStatus = false;
        //     }
        // }

        //新增服务
        $scope.addServer = function() {
            $scope.server = {};
            $scope.serverModalTitle = '新增服务';
            $('#serverModal').modal('show');
        }


        //修改服务
        $scope.updateServer=function (item) {
            $scope.server = angular.copy(item);
            $scope.serverModalTitle = '修改服务';
            $('#serverModal').modal('show');
        };



        //删除
        $scope.removeServer=function (item) {
            JS.confirm('删除服务','是否确定删除该服务？','确定',function() {
                httpServer.getData(URLAPI.saafWebserviceInfoServicesDelete, 'post', {
                    params: JSON.stringify({
                        id:item.webserviceId
                    })
                }, function (res) {
                    if (res.status == "S") {
                        $scope.serverTable.getData();
                        JS.alert('删除成功!');
                    }else {
                        JS.alert(res.msg,'error','确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            })
        };

        //新增服务保存
        $scope.confirmServer=function () {
            $scope.server.businessLineCode = $scope.serverParams.var_equal_businessLineCode;

            httpServer.getData(URLAPI.saafWebserviceInfoServicesSave, 'post', {
                params: JSON.stringify($scope.server)
            }, function (res) {
                if (res.status == "S") {
                    $scope.serverTable.getData();
                    $('#serverModal').modal('hide');
                    JS.alert('保存成功');
                }else {
                    JS.alert(res.msg,'error','确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };
    }])
})