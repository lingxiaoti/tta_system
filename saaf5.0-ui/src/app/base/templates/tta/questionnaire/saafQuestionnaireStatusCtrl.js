define(["app"], function (app) {
    app.controller('saafQuestionnaireStatusCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams','$timeout', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams,$timeout) {

        if($stateParams.allowUpdate=='Y'){
            $scope.allowUpdate=true;
        }else{
            $scope.allowUpdate=false;
        }
        //获取信息
        $scope.search=function(){
            httpServer.getData(URLAPI.findVrQuestionSurveyById, 'POST', {
                'params': JSON.stringify({
                    qnHeadId:$stateParams.qnHeadId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.headList=res.data.headList;
                    $scope.lineList=res.data.lineList;
                }
            }, function (err) {
            });
        }
        $scope.search();

    }]);
});