define(["app"], function (app) {
    app.controller('saafQuestionnairePublishStatusCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams','$timeout', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams,$timeout) {

        if($stateParams.allowUpdate=='Y'){
            $scope.allowUpdate=true;
        }else{
            $scope.allowUpdate=false;
        }
        //获取信息
        $scope.search=function(){
            httpServer.getData(URLAPI.findVrQuestionSurveyPublishList, 'POST', {
                'params': JSON.stringify({
                    publishId:$stateParams.publishId
                }),
                pageIndex:1,
                pageRows:1
            }, function (res) {
                if(res.count>0){
                    $scope.qnPublishData=res.data;
                    httpServer.getData(URLAPI.findVrQuestionSurveyById, 'POST', {
                        'params': JSON.stringify({
                            qnHeadId:$scope.qnPublishData[0].qnHeadId
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.headList=res.data.headList;
                            $scope.lineList=res.data.lineList;
                        }
                    }, function (err) {
                    });
                }
            }, function (err) {
            });
        }
        $scope.search();

    }]);
});