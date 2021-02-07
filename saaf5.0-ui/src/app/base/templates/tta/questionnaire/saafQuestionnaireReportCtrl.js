define(["app"], function (app) {
    app.controller('saafQuestionnaireReportCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams','$rootScope','$state', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams,$rootScope,$state) {


        $scope.changeTab=function(flag){
            if(flag===0){
                $scope.report();
            }else if(flag==1){
                $scope.analysis();
            }
        }

        $scope.exportXls=function(){

            var xlsParams = JSON.stringify(
                {
                    "publishId": $stateParams.publishId
                }
            )

            var DownLoadFile = function (options) {
                var config = $.extend(true, {method: 'post'}, options);
                var downloadIframe = $('<iframe id="down-file-iframe" style="display:none"/>');
                var downloadForm = $('<form id="down-file-form" target="down-file-iframe" method="' + config.method + '" />');
                downloadForm.attr('action', config.url);
                downloadForm.append('<input type=\'hidden\' name=\'params\' value=\'' + config.data.params + '\' />');

                downloadIframe.append(downloadForm);
                $(document.body).append(downloadIframe);
                downloadForm.submit();
                downloadIframe.remove();
            };

            var data = {
                'params': xlsParams
            }
            DownLoadFile({url: URLAPI.findReportToExport, data: data});


            // httpServer.getData(URLAPI.findReportToExport, 'POST', {
            //     'params': JSON.stringify({
            //         "publishId": $stateParams.publishId
            //     })
            // }, function (res) {
            //     if (res.status === 'S') {
            //     } else {
            //         JS.alert(res.msg, "error", "确定");
            //     }
            // });
        }

        $scope.report=function(){

            httpServer.post(URLAPI.findResultReport, {
                'params': JSON.stringify({
                    "publishId": $stateParams.publishId
                })
            }, function (res) {
                if (res.status === 'S') {
                    $scope.resultReportData = res.data;
                    for(var i=0;i<$scope.resultReportData.lineList.length;i++){
                        //先判断题型（多选题挑出来，其他照旧）
                        var resultInfoData=[];
                        //2018-08-23增加一个名称字段
                        var userFullNames="";
                        if($scope.resultReportData.lineList[i].projectType!='multi_selection'){
                            //不为多选题，总答题人数肯定就是答题结果数
                            $scope.resultReportData.lineList[i]["num"]=$scope.resultReportData.lineList[i].qnResultData.length;
                            for(var j=0;j<$scope.resultReportData.lineList[i].qnChoiceData.length;j++){
                                resultInfoData=[];
                                userFullNames="";
                                for(var k=0;k<$scope.resultReportData.lineList[i].qnResultData.length;k++){
                                    //判断选项id是否一致(判断占比)
                                    if($scope.resultReportData.lineList[i].qnChoiceData[j].qnChoiceId==
                                        $scope.resultReportData.lineList[i].qnResultData[k].qnChoiceId){
                                        if(userFullNames=="" || userFullNames==null  || userFullNames==undefined ){
                                            userFullNames=$scope.resultReportData.lineList[i].qnResultData[k].userFullName;
                                        }else{
                                            userFullNames=userFullNames+","+$scope.resultReportData.lineList[i].qnResultData[k].userFullName;
                                        }
                                        resultInfoData.push({
                                            "qnChoiceId":$scope.resultReportData.lineList[i].qnResultData[k].qnChoiceId,
                                            "resultNum":$scope.resultReportData.lineList[i].qnResultData[k].resultNum,
                                            "publishId":$scope.resultReportData.lineList[i].qnResultData[k].publishId,
                                            "qnChoiceResult":$scope.resultReportData.lineList[i].qnResultData[k].qnChoiceResult
                                        })
                                    }
                                    if(k==$scope.resultReportData.lineList[i].qnResultData.length-1){
                                        $scope.resultReportData.lineList[i].qnChoiceData[j]["resultInfoData"]=resultInfoData;
                                        $scope.resultReportData.lineList[i].qnChoiceData[j]["num"]= resultInfoData.length;
                                        $scope.resultReportData.lineList[i].qnChoiceData[j]["userFullNames"]= userFullNames;
                                    }
                                }
                            }
                        }
                        else{
                            //多选题，总答题人数还是答题结果数，刚刚想错了，哎！
                            $scope.resultReportData.lineList[i]["num"]=$scope.resultReportData.lineList[i].qnResultData.length;

                            for(var m=0;m<$scope.resultReportData.lineList[i].qnChoiceData.length;m++){
                                resultInfoData=[];
                                userFullNames="";
                                for(var n=0;n<$scope.resultReportData.lineList[i].qnResultData.length;n++){
                                    //多选题有多个选项，将qnChoiceId分隔出来
                                    var qnChoiceIdData=[];


                                    if($scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId==undefined ||
                                        $scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId==null ||
                                        $scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId=="" ){

                                    }else{
                                        qnChoiceIdData= $scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId.split(",");
                                    }

                                    //判断是否存在选项
                                    for(var p=0;p<qnChoiceIdData.length;p++){
                                        if($scope.resultReportData.lineList[i].qnChoiceData[m].qnChoiceId== qnChoiceIdData[p]){
                                            if(userFullNames=="" || userFullNames==null  || userFullNames==undefined ){
                                                userFullNames=$scope.resultReportData.lineList[i].qnResultData[n].userFullName;
                                            }else{
                                                userFullNames=userFullNames+","+$scope.resultReportData.lineList[i].qnResultData[n].userFullName;
                                            }
                                            resultInfoData.push({
                                                "qnChoiceId":$scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId,
                                                "resultNum":$scope.resultReportData.lineList[i].qnResultData[n].resultNum,
                                                "publishId":$scope.resultReportData.lineList[i].qnResultData[n].publishId,
                                                "qnChoiceResult":$scope.resultReportData.lineList[i].qnResultData[n].qnChoiceResult
                                            })
                                        }
                                    }

                                    if(n==$scope.resultReportData.lineList[i].qnResultData.length-1){
                                        $scope.resultReportData.lineList[i].qnChoiceData[m]["resultInfoData"]=resultInfoData;
                                        $scope.resultReportData.lineList[i].qnChoiceData[m]["num"]= resultInfoData.length;
                                        $scope.resultReportData.lineList[i].qnChoiceData[m]["userFullNames"]= userFullNames;
                                    }
                                }
                            }


                        }

                    }
                }
            });


            //httpServer.getData(URLAPI.findResultReport, 'POST', {
            //    'params': JSON.stringify({
            //        "publishId": $stateParams.publishId
            //    })
            //}, function (res) {
            //    if (res.status === 'S') {
            //        $scope.resultReportData = res.data;
            //        for(var i=0;i<$scope.resultReportData.lineList.length;i++){
            //            //先判断题型（多选题挑出来，其他照旧）
            //            var resultInfoData=[];
            //            //2018-08-23增加一个名称字段
            //            var userFullNames="";
            //            if($scope.resultReportData.lineList[i].projectType!='multi_selection'){
            //                //不为多选题，总答题人数肯定就是答题结果数
            //                $scope.resultReportData.lineList[i]["num"]=$scope.resultReportData.lineList[i].qnResultData.length;
            //                for(var j=0;j<$scope.resultReportData.lineList[i].qnChoiceData.length;j++){
            //                    resultInfoData=[];
            //                    userFullNames="";
            //                    for(var k=0;k<$scope.resultReportData.lineList[i].qnResultData.length;k++){
            //                        //判断选项id是否一致(判断占比)
            //                        if($scope.resultReportData.lineList[i].qnChoiceData[j].qnChoiceId==
            //                            $scope.resultReportData.lineList[i].qnResultData[k].qnChoiceId){
            //                            if(userFullNames=="" || userFullNames==null  || userFullNames==undefined ){
            //                                userFullNames=$scope.resultReportData.lineList[i].qnResultData[k].userFullName;
            //                            }else{
            //                                userFullNames=userFullNames+","+$scope.resultReportData.lineList[i].qnResultData[k].userFullName;
            //                            }
            //                            resultInfoData.push({
            //                                "qnChoiceId":$scope.resultReportData.lineList[i].qnResultData[k].qnChoiceId,
            //                                "resultNum":$scope.resultReportData.lineList[i].qnResultData[k].resultNum,
            //                                "publishId":$scope.resultReportData.lineList[i].qnResultData[k].publishId,
            //                                "qnChoiceResult":$scope.resultReportData.lineList[i].qnResultData[k].qnChoiceResult
            //                            })
            //                        }
            //                        if(k==$scope.resultReportData.lineList[i].qnResultData.length-1){
            //                            $scope.resultReportData.lineList[i].qnChoiceData[j]["resultInfoData"]=resultInfoData;
            //                            $scope.resultReportData.lineList[i].qnChoiceData[j]["num"]= resultInfoData.length;
            //                            $scope.resultReportData.lineList[i].qnChoiceData[j]["userFullNames"]= userFullNames;
            //                        }
            //                    }
            //                }
            //            }
            //            else{
            //                //多选题，总答题人数还是答题结果数，刚刚想错了，哎！
            //                $scope.resultReportData.lineList[i]["num"]=$scope.resultReportData.lineList[i].qnResultData.length;
            //
            //                for(var m=0;m<$scope.resultReportData.lineList[i].qnChoiceData.length;m++){
            //                    resultInfoData=[];
            //                    userFullNames="";
            //                    for(var n=0;n<$scope.resultReportData.lineList[i].qnResultData.length;n++){
            //                        //多选题有多个选项，将qnChoiceId分隔出来
            //                        var qnChoiceIdData=[];
            //
            //
            //                        if($scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId==undefined ||
            //                            $scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId==null ||
            //                            $scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId=="" ){
            //
            //                        }else{
            //                            qnChoiceIdData= $scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId.split(",");
            //                        }
            //
            //                        //判断是否存在选项
            //                        for(var p=0;p<qnChoiceIdData.length;p++){
            //                            if($scope.resultReportData.lineList[i].qnChoiceData[m].qnChoiceId== qnChoiceIdData[p]){
            //                                if(userFullNames=="" || userFullNames==null  || userFullNames==undefined ){
            //                                    userFullNames=$scope.resultReportData.lineList[i].qnResultData[n].userFullName;
            //                                }else{
            //                                    userFullNames=userFullNames+","+$scope.resultReportData.lineList[i].qnResultData[n].userFullName;
            //                                }
            //                                resultInfoData.push({
            //                                    "qnChoiceId":$scope.resultReportData.lineList[i].qnResultData[n].qnChoiceId,
            //                                    "resultNum":$scope.resultReportData.lineList[i].qnResultData[n].resultNum,
            //                                    "publishId":$scope.resultReportData.lineList[i].qnResultData[n].publishId,
            //                                    "qnChoiceResult":$scope.resultReportData.lineList[i].qnResultData[n].qnChoiceResult
            //                                })
            //                            }
            //                        }
            //
            //                        if(n==$scope.resultReportData.lineList[i].qnResultData.length-1){
            //                            $scope.resultReportData.lineList[i].qnChoiceData[m]["resultInfoData"]=resultInfoData;
            //                            $scope.resultReportData.lineList[i].qnChoiceData[m]["num"]= resultInfoData.length;
            //                            $scope.resultReportData.lineList[i].qnChoiceData[m]["userFullNames"]= userFullNames;
            //                        }
            //                    }
            //                }
            //
            //
            //            }
            //
            //        }
            //    }
            //});
        }
        $scope.report();

        $scope.analysis = function () {

            httpServer.post(URLAPI.findResultPerson, {
                'params': JSON.stringify({
                    "publishId": $stateParams.publishId
                })
            }, function (res) {
                if (res.status === 'S' && res.count > 0) {
                    $scope.resultPersonData = res.data;
                } else {
                    JS.alert("暂无答卷信息！", "error", "确定");
                }
            });



            //httpServer.getData(URLAPI.findResultPerson, 'POST', {
            //    'params': JSON.stringify({
            //        "publishId": $stateParams.publishId
            //    })
            //}, function (res) {
            //    if (res.status === 'S' && res.count > 0) {
            //        $scope.resultPersonData = res.data;
            //    } else {
            //        JS.alert("暂无答卷信息！", "error", "确定");
            //    }
            //});
        }
        $scope.analysis();

        $scope.queryAnswerSheet = function (obj) {

            httpServer.post(URLAPI.findSaafQuestionnaireResult, {
                'params': JSON.stringify({
                    publishId: obj.publishId,
                    resultNum: obj.resultNum
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




            //httpServer.getData(URLAPI.findVrQuestionSurveyResult, 'POST', {
            //    'params': JSON.stringify({
            //        publishId: obj.publishId,
            //        resultNum: obj.resultNum
            //    })
            //}, function (res) {
            //    if (res.status === 'S') {
            //        $scope.headList = res.data.headList;
            //        $scope.lineList = res.data.lineList;
            //        //获取选中的行
            //        for (var i = 0; i < $scope.lineList.length; i++) {
            //            var qnChoiceIds = $scope.lineList[i].qnChoiceId+"";
            //            var qnChoiceIdData = qnChoiceIds.split(",");
            //            for (var j = 0; j < $scope.lineList[i].qnChoiceData.length; j++) {
            //                var exitFlag = false;
            //                for (var k = 0; k < qnChoiceIdData.length; k++) {
            //                    if (qnChoiceIdData[k] == $scope.lineList[i].qnChoiceData[j].qnChoiceId) {
            //                        exitFlag = true;
            //                    }
            //                    if (k == qnChoiceIdData.length - 1 && exitFlag) {
            //                        $scope.lineList[i].qnChoiceData[j]["answerFlag"] = "Y";
            //                    }
            //                }
            //            }
            //        }
            //        $('#answerSheetInfo').modal('toggle');
            //    }
            //});
        }

        $scope.queryAnswerList=function(obj){
            //将数据弄成自己需要的格式
            var resultNum="";
            $scope.resultInfo=obj.resultInfoData;
            for(var i=0;i<$scope.resultInfo.length;i++){
                if(i==0){
                    resultNum=$scope.resultInfo[i].resultNum;
                }else{
                    resultNum=resultNum+","+$scope.resultInfo[i].resultNum;
                }

                if(i==$scope.resultInfo.length-1){

                    httpServer.post(URLAPI.findAnswerResultList, {
                        'params': JSON.stringify({
                            "publishId": $stateParams.publishId,
                            "resultNum": resultNum
                        })
                    }, function (res) {
                        if (res.status === 'S' && res.count > 0) {
                            $scope.resultData = res.data;
                            $('#answerListInfo').modal('toggle');
                        } else {
                            JS.alert("暂无答卷信息！", "error", "确定");
                        }
                    });
                }
            }
        }

    }]);
});