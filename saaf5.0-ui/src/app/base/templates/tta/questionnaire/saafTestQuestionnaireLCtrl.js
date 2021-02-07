/**
 * Created by zl on 2019/2/16.
 */
define(["app"], function (app) {
    app.controller('saafTestQuestionnaireLCtrl', ['$scope','$timeout', 'httpServer', 'SIE.JS', 'URLAPI', '$stateParams', '$rootScope', function ($scope,$timeout, httpServer, JS, URLAPI, $stateParams, $rootScope) {

        // 选中的数据
        $scope.selectData = []
        $scope.qnChoiceData=[];
        if($stateParams.allowUpdate=='Y'){
            $scope.allowUpdate=true;
        }else{
            $scope.allowUpdate=false;
        }
       
        $scope.selectAll = function(data){
            data.forEach(v => {
                if(v.requireFlag&&v.requireFlag==='Y') return
                v.checked = $scope.allCheckForm ? 'Y' : 'N'
            })
            $scope.checkSelect()
        }
        
        $scope.checkBoxActForm = function(row){
            row.checked = row.checkForm ? 'Y' : 'N'
            $scope.checkSelect()
        }
        $scope.checkSelect = function(){
            $scope.selectData = $scope.formTable.data.filter(v=>{
               return v.checked && v.checked === 'Y'
            })
        }
        $scope.saveForm=function(){
            $scope.lineForm = {
                "lineList" : $scope.selectData,
                "testQnHeadId" : $stateParams.testQnHeadId
            }
            httpServer.post(URLAPI.saveSaafTestQuestionnaireL, {
                params: JSON.stringify($scope.lineForm)
            }, function (res) {
                if (res.status == 'S') {
                    JS.alert(res.msg, 'success', '确定');
                    $scope.findLine();
                }
            }, function (err) {
                JS.alert(res.msg, 'error', '确定');
            });
        }
        $scope.searchData = function(){
            $scope.formTable.getData()
            $timeout(function(){
                $scope.checkSelect()
            },1000)
        }
        //查询头
        $scope.search=function(){
            httpServer.post(URLAPI.findSaafTestQuestionnaireList, {
                params: JSON.stringify({
                    testQnHeadId:$stateParams.testQnHeadId
                }),
                pageIndex:1,
                pageRows:10
            }, function (res) {
                if (res.status == 'S') {
                    $scope.headList=res.data[0];
                    $scope.findLine();
                }
            }, function (err) {

            });
        }
        $scope.search();
        //查询行
        $scope.findLine = function(){
            httpServer.post(URLAPI.findLineAndchoice, {
                params: JSON.stringify({
                    testQnHeadId:$stateParams.testQnHeadId
                }),
                pageIndex:1,
                pageRows:10000
            }, function (res1) {
                if (res1.status == 'S') {
                    $scope.lineList = res1.data;
                    if(!$scope.allowUpdate){
                        for(var i=0;i<$scope.lineList.length;i++){
                            $scope.lineList[i]["showFlag"]=true;
                        }
                    }
                }
            }, function (err) {

            });
        }


        $scope.add=function(){
            $scope.allowUpdate=true;
            //$scope.current={
            //    qnHeadId:$stateParams.qnHeadId,
            //    displayFlag:"Y",
            //    projectType:"single_selection"
            //}

            $scope.qnChoiceData=[];
            $scope.lineParams = {
                "qnType":$scope.headList.qnType,
                "LOV_FLAG":"Y",
                testQnHeadId:$stateParams.testQnHeadId
            };
            $('#qnLov1').modal('toggle');
        }

        $scope.choiceFlag=true;
        $scope.changeProjectType=function(){
            if($scope.current.projectType=='single_selection'){
                $scope.choiceFlag=true;
            }else if($scope.current.projectType=='multi_selection'){
                $scope.choiceFlag=true;
            }else if($scope.current.projectType=='drop_down_list'){
                $scope.choiceFlag=true;
            }else if($scope.current.projectType=='single_selection_skip'){
                $scope.choiceFlag=true;
            }else if($scope.current.projectType=='single_selection_show'){
                $scope.choiceFlag=true;
            }else{
                $scope.choiceFlag=false;
                $scope.qnChoiceData=[];
                $scope.qnChoiceData.push({
                    "qnChoiceId":"",
                    "qnChoice":"",
                    "qnChoiceContent":"",
                    "selectSurveyLineId":"",
                    "qnAnswer":"",
                    "displayFlag":"Y"
                })
            }
        }

        $scope.addChoice=function(){
            $scope.qnChoiceData.push({
                "qnChoiceId":"",
                "qnChoice":"",
                "qnChoiceContent":"",
                "selectSurveyLineId":"",
                "qnAnswer":"N",
                "displayFlag":"Y"
            })
        }

        $scope.deleteChoice=function(index){
            JS.confirm('删除','是否确认删除','确定',function() {
                if($scope.qnChoiceData[index].qnChoiceId==undefined || $scope.qnChoiceData[index].qnChoiceId==null || $scope.qnChoiceData[index].qnChoiceId=="" ){
                    $scope.qnChoiceData.splice(index,1);
                }
                else{
                    httpServer.post(URLAPI.deleteSurveyChoice, {
                        params: JSON.stringify({
                            "qnChoiceId": $scope.qnChoiceData[index].qnChoiceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功,请点击下方保存按钮保存数据!", "success", "确定");
                            $timeout(function(){$scope.qnChoiceData.splice(index,1);},10);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }

            })
        }

        $scope.save=function(){
            //先校验数据的准确性
            $("#saveButton").attr("disabled");
            var character = new Array("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","X","Y","Z");
            for(var i=0;i<$scope.qnChoiceData.length;i++) {
                $scope.qnChoiceData[i].qnChoice=character[i];
            }
            $scope.current["qnChoiceData"]=$scope.qnChoiceData;
            httpServer.post(URLAPI.saveSaafQuestionnaireL, {
                params: JSON.stringify($scope.current)
            }, function (res) {
                if (res.status == 'S') {
                    JS.alert(res.msg, 'success', '确定');
                    $("#saveButton").removeAttr("disabled");
                    $('#myModal').modal('toggle');
                    $scope.search();
                }
                else {
                    JS.alert(res.msg, "error", "确定");
                    $("#saveButton").removeAttr("disabled");
                }
            }, function (err) {

            });
        }

        $scope.testLine;
        $scope.getSelectRow=function(index,item){
            $scope.selectRow=index;
            $scope.testLine = item;
            $timeout(function(){
                $('lineListInfo').removeClass("active");
                $(this).addClass("active");
            },10);
        }

        $scope.edit=function(){
            $scope.allowUpdate=true;
            $scope.current=$scope.lineList[$scope.selectRow];
            $scope.qnChoiceData=$scope.current.qnChoiceData;
            if($scope.current.projectType=='text' || $scope.current.projectType=='textarea'){
                $scope.choiceFlag=false;
            }else{
                $scope.choiceFlag=true;
            }
            $('#myModal').modal('toggle');
        }

        $scope.delete=function(){

            JS.confirm('删除','是否确认删除','确定',function() {
                if($scope.lineList[$scope.selectRow].testQnLineId==undefined || $scope.lineList[$scope.selectRow].testQnLineId==null || $scope.lineList[$scope.selectRow].testQnLineId=="" ){
                    $scope.lineList.splice($scope.selectRow,1);
                }else{
                    httpServer.post(URLAPI.deleteLine, {
                        params: JSON.stringify({
                            "testQnLineId": $scope.lineList[$scope.selectRow].choiceList[0].testQnLineId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.findLine();
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }

            })
        }

        //$scope.findQnLine = function(){
        //    httpServer.post(URLAPI.findQuestionnaireLAndQuestionnaireChoice,  {
        //        params: JSON.stringify({
        //            "qnType":"EXTERNAL_USER",
        //            "LOV_FLAG":"Y"
        //        }),
        //        pageIndex:1,
        //        pageRows:10000
        //    }, function (res) {
        //        if(res.status==='S'){
        //            $scope.multiProjectData=res.data;
        //        }else{
        //            JS.alert(res.msg, "error", "确定");
        //            return false;
        //        }
        //
        //    });
        //}
        //
        //$scope.findQnLine();

        $scope.selectSingleProject=function(obj,index){
            if($scope.current.qnLineId==undefined || $scope.current.qnLineId==null || $scope.current.qnLineId=="" ){
                JS.alert("请先保存后再选择!", "error", "确定");
                return false;
            }
            //
            $scope.selectSingleIndex=index;
            $scope.singleProjectData=[];
            httpServer.post(URLAPI.findSelectProjectList,  {
                params: JSON.stringify({
                    qnHeadId:$stateParams.qnHeadId,
                    qnLineId:$scope.current.qnLineId
                })
            }, function (res) {
                if(res.status==='S'){
                    $scope.singleProjectData=res.data;
                    for(var i=0;i<$scope.singleProjectData.length;i++){
                        if(obj.selectSurveyLineId!=undefined &&
                            obj.selectSurveyLineId!=null && obj.selectSurveyLineId!=""){
                            if($scope.singleProjectData[i].qnLineId==obj.selectSurveyLineId){
                                $scope.singleProjectData[i].checkFlag=true;
                            }
                        }
                    }
                    $('#singleSelectProject').modal('toggle');
                }else{
                    JS.alert(res.msg, "error", "确定");
                    return false;
                }

            });
        }

        $scope.confirmSingleProject=function(){
            var singleSelectName = document.getElementsByName('singleSelectName');
            for (var i = 0; i < singleSelectName.length; i++) {
                if (singleSelectName[i].checked == true) {
                    $scope.current.qnChoiceData[$scope.selectSingleIndex]["selectSurveyLineId"]= singleSelectName[i].value;
                }
            }

            $('#singleSelectProject').modal('toggle');
        }

        $scope.selectMultiProject=function(obj,index){
            if($scope.current.qnLineId==undefined || $scope.current.qnLineId==null || $scope.current.qnLineId=="" ){
                JS.alert("请先保存后再选择!", "error", "确定");
                return false;
            }

            $scope.selectMultiIndex=index;
            $scope.multiProjectData=[];

            var selectMultiData=[];
            if(obj.selectSurveyLineId==undefined || obj.selectSurveyLineId==null || obj.selectSurveyLineId==""){
            }else{
                var selectSurveyLineIdInfo=obj.selectSurveyLineId+"";
                selectMultiData=selectSurveyLineIdInfo.split(",");
            }

            httpServer.post(URLAPI.findSelectProjectList,{
                params: JSON.stringify({
                    qnHeadId:$stateParams.qnHeadId,
                    qnLineId:$scope.current.qnLineId
                })
            }, function (res) {
                if(res.status==='S'){
                    $scope.multiProjectData=res.data;
                    for(var i=0;i<$scope.multiProjectData.length;i++){
                        for(var j=0;j<selectMultiData.length;j++){
                            if($scope.multiProjectData[i].qnLineId==selectMultiData[j]){
                                $scope.multiProjectData[i].checkFlag="Y";
                            }
                        }
                    }
                    $('#multiSelectProject').modal('toggle');
                }else{
                    JS.alert(res.msg, "error", "确定");
                    return false;
                }
            });
        }

        $scope.confirmMultiProject=function(){
            $scope.checkMultiData=[];
            var multiSurveyLineIds="";
            for(var i=0;i<$scope.multiProjectData.length;i++){
                if($scope.multiProjectData[i].checkFlag=='Y'){
                    if($scope.checkMultiData.length>0){
                        multiSurveyLineIds=multiSurveyLineIds+","+$scope.multiProjectData[i].qnLineId;
                    }else{
                        multiSurveyLineIds=$scope.multiProjectData[i].qnLineId;
                    }
                    $scope.checkMultiData.push({
                        "projectTitle":$scope.multiProjectData[i].projectTitle,
                        "serialNumber":$scope.multiProjectData[i].serialNumber,
                        "qnLineId":$scope.multiProjectData[i].qnLineId
                    })
                }
                if(i==$scope.multiProjectData.length-1){

                    $scope.current.qnChoiceData[$scope.selectMultiIndex]["selectSurveyLineId"]=multiSurveyLineIds;
                }
            }
            $('#multiSelectProject').modal('toggle');
        }


        //从其他地方移植过来的
        // 跳题事件
        $scope.changeRadioSkip= function (index,row) {
            //获取跳到几题
            $scope.selectSurveyLineId=row.selectSurveyLineId;
            $scope.skipSerialNumber="";
            for(var i=0;i<$scope.lineList.length;i++){
                //$scope.lineList[i]["showFlag"]=true;
                console.log("-------selectSurveyLineId:"+$scope.selectSurveyLineId);
                if($scope.selectSurveyLineId==$scope.lineList[i].qnLineId){
                    //获取排序id
                    $scope.skipSerialNumber=$scope.lineList[i].serialNumber;
                    console.log("-------skipSerialNumber:"+$scope.skipSerialNumber);
                }
                if(i==$scope.lineList.length-1){
                    if($scope.skipSerialNumber!="" && $scope.skipSerialNumber!=null
                        && $scope.skipSerialNumber!=undefined){
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
            //获取显示的题目
            $scope.selectSurveyLineId=row.selectSurveyLineId+"";
            $scope.showSurveyLineIdData=[];
            $scope.showSurveyLineIdData=$scope.selectSurveyLineId.split(",");
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

    }]);
});