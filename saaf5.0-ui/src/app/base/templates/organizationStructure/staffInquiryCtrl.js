/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('staffInquiryCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction','$timeout',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction,$timeout) {



            $scope.respLoad  = function (id) {
                $scope.params =  {
                    responsibilityId: id
                }
                // $scope.dataTable.search(1);
            }
        }])
})