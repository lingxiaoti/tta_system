/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('positionCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction','$timeout',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction,$timeout) {

            $timeout(function() {
                console.log($scope.ctrlRespId,$scope.ctrlOrgId,$scope.ctrlChannelType,$scope.ctrlUserType)
            },3000)

            /**
             * 新增
             */
            $scope.btnNew = function() {
                iframeTabAction('新增职位','/positionDetail/' + $scope.ctrlRespId + '/' + $scope.ctrlOrgId + '/-1');
            }

            $scope.editing = function(row) {
                iframeTabAction('编辑职位','/positionDetail/' + $scope.ctrlRespId + '/' + $scope.ctrlOrgId + '/' + row.positionId);
            }

            /**
             * 获取渠道数据
             */
   /*         $scope.findChannel = function() {
                httpServer.post(URLAPI.findChannelCfg, {
                    params: JSON.stringify({respId: $scope.ctrlRespId})
                }, function(res) {
                    if(res.status === 'S') {
                        $scope.channelList = res.data;
                    }else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function(err) {
                    SIEJS.alert('查询渠道数据失败','error', '确定');
                })
            }
            $scope.findChannel();*/

        }])
})
