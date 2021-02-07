/**
 * Created by houxingzhang on 2018-01-22.
 */
'use strict';
define(['app'], function (app) {
    app.controller('dynamicChartsInfoCtrl', function ($scope, $http, $location, $rootScope, $state, $stateParams,dynamicReportTypeSave,setNewRow,dynamicReportTypeDelete) {
        //do something here

        $scope.params={};

        $scope.demo = function (item) {
            window.open(item.demoUrl);
        };

        $scope.btnNew = function () {
            $scope.current = {};
            $('#myModal').modal('toggle');
        };

        $scope.save = function(){
            $("#saveButton").attr("disabled");
            dynamicReportTypeSave({params:JSON.stringify($scope.current)},function(res){
                if (res.status == 'S') {
                    $("#saveButton").removeAttr("disabled");
                    $('#myModal').modal('toggle');

                    // 修改
                    if ($scope.current.chartsId){
                        setNewRow($scope.dataTable.selectRow,$scope.current);
                    }else{
                        $scope.dataTable.search(1);
                    }

                }else {
                    $("#saveButton").removeAttr("disabled");
                }
            })

        }

        $scope.btnModify = function() {
            $scope.current=angular.copy($scope.dataTable.selectRow);
            $('#myModal').modal('toggle');
        }

        $scope.btnDel=function(){
            var p={
                params:JSON.stringify({
                    id:$scope.dataTable.selectRow.chartsId
                })
            };
            dynamicReportTypeDelete(p,function(res){
                if(res.status==='S'){
                    $scope.dataTable.search();
                }
            });

        }

    });
});
