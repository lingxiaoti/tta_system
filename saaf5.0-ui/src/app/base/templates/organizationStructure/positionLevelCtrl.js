/**
 * Created by hmx on 2018/9/5.
 */
'use strict';
define(['app'],function(app) {
    app.controller('positionLevelCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction','$timeout',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction,$timeout) {

            /**
             * 新增
             */
            $scope.btnNew = function() {
                iframeTabAction('新增职位层级','/positionLevelDetail/' + $scope.ctrlRespId + '/-1');
            }

            /**
             * 编辑
             * @param row
             */
            $scope.editing = function(row) {
                iframeTabAction('编辑职位层级','/positionLevelDetail/' + $scope.ctrlRespId + '/' + row.mgrPositionId);
            }

            /**
             * 确定选中上级职位
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectUpPosition = function(key, value, currentList) {
                $scope.params.mgrPositionName = value;
                $scope.params.mgrPositionId = key;
            }

            /**
             * 确定选中职位
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectPosition = function(key, value, currentList) {
                $scope.params.positionName = value;
                $scope.params.positionId = key;
            }

            /**
             * 确定选中上级人员
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectUpPerson = function(key, value, currentList) {
                $scope.params.mgrPersonName = value;
                $scope.params.mgrPositionId = key;
            }

            /**
             * 确定选中人员
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectPerson = function(key, value, currentList) {
                $scope.params.personName = value;
                $scope.params.personId = key;
            }

        }])
})