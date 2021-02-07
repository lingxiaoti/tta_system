/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('positionAllotCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction) {

        $scope.initSearch=function(){
            $scope.params.respId=$scope.ctrlRespId;
            $scope.params.ouId=$scope.ctrlOrgId;
            $scope.dataTable.search(1);
        }
            /**
             * 新增
             */
            $scope.btnNew = function() {
                // console.log($scope.ctrlOrgId)
                iframeTabAction('新增职位分配','/positionAllotAdd/' + $scope.ctrlRespId + '/' + $scope.ctrlOrgId + '/-1');
            }

            /**
             *修改
             * @param row
             */
            $scope.editing = function(row) {
                iframeTabAction('编辑职位分配','/positionAllotAdd/' + $scope.ctrlRespId + '/' + $scope.ctrlOrgId + '/' + row.personId);
            }

            //人员选择确认回调
            $scope.personSelect=function(key, value, currentList){
                $scope.params.personName =value;
                $scope.params.personId =key;
            };
            //人员选择清除回调
            $scope.cancelPerSelect =function(){
                $scope.params.personName='';
                $scope.params.personId='';
            }

            //职位选择确认回调
            $scope.positionSelect=function(key, value, currentList){
                $scope.params.positionName =value;
                $scope.params.positionId =key;
            };
            //职位选择清除回调
            $scope.cancelPosSelect =function(){
                $scope.params.positionName='';
                $scope.params.positionId='';
            }

        }])
})