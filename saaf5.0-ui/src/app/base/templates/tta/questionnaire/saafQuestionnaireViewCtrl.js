define(["app"], function (app) {
    app.controller('saafQuestionnaireViewCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams','$rootScope','$state', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams,$rootScope,$state) {

        $scope.saveData = {};
        $scope.hideSubmitBtnFlag=false;

        if($stateParams.allowUpdate=='Y'){
            $scope.allowUpdate=true;
        }else{
            $scope.allowUpdate=false;
        }
        //获取信息
        $scope.search=function(){
            httpServer.post(URLAPI.findSaafQuestionnaireByPublishId, {
                'params': JSON.stringify({
                    "publishId": $stateParams.publishId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.headList=res.data.headList;
                    $scope.lineList=res.data.lineList;

                    for(var i=0;i<$scope.lineList.length;i++){
                        $scope.lineList[i]["showFlag"]=true;
                    }
                    $scope.initData();
                }else{
                    JS.alert(res.msg, "error", "确定");
                }
            });
        }
        $scope.search();

        $scope.submitSurvey=function(){
            var flag = true;
            angular.forEach($scope.saveData.SurveyResultData,function (item,index) {
                console.log(JSON.stringify(item.testQnChoiceId));
                if(!item.testQnChoiceId&&$scope.lineList[index].requireFlag == 'Y'&&$scope.lineList[index].displayFlag == 'Y'){
                    flag = false;
                }
            })
            if(!flag){
                JS.alert("必填项不能空!", "error", "确定");
                return;
            }

            httpServer.post(URLAPI.saveSaafQuestionnaireResult, {
                'params': JSON.stringify($scope.saveData)
            }, function (res) {
                if (res.status == 'S') {
                    if (res.status == 'S') {
                        JS.alert("提交问卷成功!", "success", "确定");
                        $scope.hideSubmitBtnFlag=true;
                    }else{
                        JS.alert(res.msg, "error", "确定");
                    }
                }else{
                    JS.alert(res.msg, "error", "确定");
                }
            });
        }


        // 初始化保存数据的格式
        $scope.initData = function () {
            $scope.saveData = {
                testQnHeadId: $scope.headList[0].testQnHeadId,
                publishId:  $scope.headList[0].publishId,
                qnType: $scope.headList[0].qnType,
                SurveyResultData:[]
            }
            angular.forEach($scope.lineList ,function (item) {
                $scope.saveData.SurveyResultData.push({
                    testQnHeadId: $scope.headList[0].testQnHeadId,
                    publishId:  $scope.headList[0].publishId,
                    testQnLineId: item.testQnLineId,
                    testQnChoiceId:null,
                    qnChoiceResult:null
                })
            })
        }

        // 单选事件
        $scope.changeRadio = function (index,row) {
            $scope.saveData.SurveyResultData[index].testQnChoiceId = row.testQnChoiceId;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = row.qnChoice;
        }

        // 跳题事件
        $scope.changeRadioSkip= function (index,row) {
            $scope.saveData.SurveyResultData[index].testQnChoiceId = row.testQnChoiceId;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = row.qnChoice;
            //获取跳到几题
            $scope.selectQnLineId=row.selectQnLineId;
            $scope.skipSerialNumber="";
            for(var i=0;i<$scope.lineList.length;i++){
                //$scope.lineList[i]["showFlag"]=true;
                if($scope.selectQnLineId==$scope.lineList[i].qnLineId){
                    //获取排序id
                    $scope.skipSerialNumber=$scope.lineList[i].serialNumber;
                }
                if(i==$scope.lineList.length-1){
                    if($scope.skipSerialNumber!="" && $scope.skipSerialNumber!=null && $scope.skipSerialNumber!=undefined){
                        for(var j=0;j<$scope.lineList.length;j++){
                            if($scope.lineList[j].serialNumber>$scope.lineList[index].serialNumber){
                                if($scope.lineList[j].serialNumber<$scope.skipSerialNumber){
                                    $scope.lineList[j]["showFlag"]=false;
                                }else{
                                    $scope.lineList[j]["showFlag"]=true;
                                }
                            }
                        }
                    }else{
                        for(var k=0;k<$scope.lineList.length;k++){
                            if($scope.lineList[k].serialNumber>$scope.lineList[index].serialNumber){
                                $scope.lineList[k]["showFlag"]=true;
                            }
                        }
                    }
                }
            }
        }

        // 显示题事件
        $scope.changeRadioShow= function (index,row) {
            $scope.saveData.SurveyResultData[index].testQnChoiceId = row.testQnChoiceId;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = row.qnChoice;

            //获取显示的题目
            $scope.selectQnLineId=row.selectQnLineId+"";
            $scope.showSurveyLineIdData=[];
            $scope.showSurveyLineIdData=$scope.selectQnLineId.split(",");
            for(var i=0;i<$scope.lineList.length;i++){
                //$scope.lineList[i]["showFlag"]=true;
                if($scope.lineList[i].serialNumber>$scope.lineList[index].serialNumber){
                    if($scope.showSurveyLineIdData.length>0){
                        var existFlag=false;
                        for(var j=0;j<$scope.showSurveyLineIdData.length;j++){
                            if($scope.showSurveyLineIdData[j]==$scope.lineList[i].qnLineId){
                                existFlag=true;
                                $scope.lineList[i]["showFlag"]=true;
                            }

                            if(j==$scope.showSurveyLineIdData.length-1 && !existFlag){
                                $scope.lineList[i]["showFlag"]=false;
                            }
                        }
                    }else{
                        $scope.lineList[i]["showFlag"]=false;
                    }
                }

            }
        }

        // 多选事件
        $scope.changeCheckbox = function (index,row) {
            var ids = '';
            var str = '';
            angular.forEach($scope.lineList[index].qnChoiceData,function (item) {
                if(item.itemValue){
                    ids = ids + item.testQnChoiceId + ',';
                    str = str + item.qnChoice + ',';
                }
            })
            if(ids){
                ids = ids.substring(0,ids.length-1);
                str = str.substring(0,str.length-1);
            }
            $scope.saveData.SurveyResultData[index].testQnChoiceId = ids;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = str;
        }

        // 下拉事件
        $scope.changeSelect = function (index,selectIndex) {
            // 根据数据下标和select选中的下标重新匹配数据
            var selectKey = $scope.lineList[index].itemValue;

            $scope.saveData.SurveyResultData[index].testQnChoiceId = $scope.lineList[index].qnChoiceData[selectKey].testQnChoiceId;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = $scope.lineList[index].qnChoiceData[selectKey].qnChoiceContent;
        }

        // 文本事件
        $scope.changeText = function (index,row) {
            $scope.saveData.SurveyResultData[index].testQnChoiceId = row.qnChoiceData[0].testQnChoiceId;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = row.itemValue;
        }




    }]);
});