define(["app"], function (app) {
    app.controller('saafQuestionnaireListCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams','$rootScope','$state','iframeTabAction', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams,$rootScope,$state,iframeTabAction) {

        $scope.params={};
        $scope.dataTable={};

        $scope.params["statusFlag"]=$stateParams.statusFlag;
        console.log(JSON.stringify($stateParams.statusFlag));
        if($scope.params.statusFlag!=undefined && $scope.params.statusFlag!=""){
            //$scope.dataTable.search();
        }

        $scope.editQuestionSurvey=function(item){
            if($stateParams.statusFlag == 'Y'){
                iframeTabAction('问卷调查信息', 'saafQuestionnaireView/'+ "Y/" + item.publishId )
            }
        }

        $scope.showQuestionSurvey=function(obj){
            if($stateParams.statusFlag == 'N'){
                httpServer.post(URLAPI.findSaafQuestionnaireResultByUserId, {
                    'params': JSON.stringify({
                        publishId: obj.publishId
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        $scope.headList = res.data.headList;
                        $scope.lineList = res.data.lineList;
                        //获取选中的行
                        for (var i = 0; i < $scope.lineList.length; i++) {
                            var qnChoiceIds = $scope.lineList[i].qnChoiceId+"";
                            var qnChoiceIdData = qnChoiceIds.split(",");
                            for (var j = 0; j < $scope.lineList[i].qnChoiceData.length; j++) {
                                var exitFlag = false;
                                for (var k = 0; k < qnChoiceIdData.length; k++) {
                                    if (qnChoiceIdData[k] == $scope.lineList[i].qnChoiceData[j].qnChoiceId) {
                                        exitFlag = true;
                                    }
                                    if (k == qnChoiceIdData.length - 1 && exitFlag) {
                                        $scope.lineList[i].qnChoiceData[j]["answerFlag"] = "Y";
                                    }
                                }
                            }
                        }
                        $('#answerSheetInfo').modal('toggle');
                    }
                });
            }
        }
    }]);
});