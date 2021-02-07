define(["app"], function (app) {
    app.controller('saafQuestionnaireDetailCtrl', ['$scope','$timeout', 'httpServer', 'SIE.JS', 'URLAPI', '$stateParams', '$rootScope', function ($scope,$timeout, httpServer, JS, URLAPI, $stateParams, $rootScope) {

        $scope.allowUpdate=true;
        $scope.choiceFlag=true;
        $scope.selctCurrent ={};
        $scope.qnChoiceData=[];

        // 显示渠道搜索LOV
        $scope.showChannel = function (index) {
            $("#findSubject").modal('show');
        }

        $scope.showChannelRule = function (index) {
            $("#findRuleSave").modal('show');
        }

        $scope.selectReturn = function (key, value, currentList) {
            var childProjectTitle = ""; //题目标题
            var childQnLineIds = ""; //二级题目ids
            var index = $scope.qnChoiceData.selectRow;
            if (currentList) {
                for (var idx in currentList) {
                    childProjectTitle = childProjectTitle + currentList[idx].projectTitle + ",";
                    childQnLineIds =  childQnLineIds + currentList[idx].qnLineId + ",";
                }
                if (childProjectTitle) {
                    childProjectTitle = childProjectTitle.substring(0, childProjectTitle.length - 1);
                    childQnLineIds = childQnLineIds.substring(0, childQnLineIds.length - 1);
                }
            }
            $scope.qnChoiceData[index].childProjectTitle = childProjectTitle;
            $scope.qnChoiceData[index].childQnLineIds = childQnLineIds;
            console.log("qnChoiceData:" + JSON.stringify( $scope.qnChoiceData));
        }

        //获取信息   
        $scope.search=function(){
            httpServer.post(URLAPI.findSaafQuestionnaireById, {
                params: JSON.stringify({
                    qnHeadId:$stateParams.qnHeadId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.lineList=res.data.lineList;
                    if(!$scope.allowUpdate){
                        for(var i=0;i<$scope.lineList.length;i++){
                            $scope.lineList[i]["showFlag"]=true;
                        }
                    }

                }
            }, function (err) {
            });
        }
        $scope.search();

        $scope.add=function(){
            $scope.allowUpdate=true;
            $scope.current={
                qnHeadId:$stateParams.qnHeadId,
                displayFlag:"Y",
                projectType:"single_selection"
            }

            $scope.qnChoiceData=[];
            $('#myModal').modal('toggle');
        }

        $scope.changeProjectType=function(){
            if($scope.current.projectType=='single_selection' || $scope.current.projectType=='multi_selection'
                || $scope.current.projectType=='drop_down_list' || $scope.current.projectType=='single_selection_skip'
                || $scope.current.projectType=='single_selection_show'){
                $scope.choiceFlag=true;
            } else {
                if ($scope.current.projectCategory == 2 && $scope.current.projectType == 'rule_model') {
                    $scope.current.projectCategory = '';
                    $scope.current.projectType = '';
                    JS.alert("若是自动计算，请选择题目类别一级题目！", "error", "确定");
                    return;
                }
                $scope.choiceFlag=false;
                $scope.qnChoiceData=[];
                $scope.qnChoiceData.push({
                    "qnChoiceId":"",
                    "qnChoice":"",
                    "qnChoiceContent":"",
                    "selectQnLineId":"",
                    "qnAnswer":"",
                    "displayFlag":"Y"
                })
            }
        }

        $scope.selectProCategory = function () {
            if ($scope.current.projectCategory == 2 && $scope.current.projectType == 'rule_model') {
                $scope.current.projectCategory = '';
                $scope.current.projectType = '';
                JS.alert("若是自动计算，请选择题目类别一级题目！", "error", "确定");
                return;
            }
        }

        $scope.addChoice=function(){
            $scope.qnChoiceData.push({
                "qnChoiceId":"",
                "qnChoice":"",
                "qnChoiceContent":"",
                "selectQnLineId":"",
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
            if (!$scope.current.calcRule && $scope.current.projectType == 'rule_model') {
                JS.alert("题目类型为自动计算，请选择对应的计算规则！", "error", "确定");
                return;
            }

            //先校验数据的准确性
            $("#saveButton").attr("disabled");
            var character = new Array("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","X","Y","Z");
            for(var i=0;i<$scope.qnChoiceData.length;i++)
            {
                $scope.qnChoiceData[i].qnChoice=character[i];
            }
            $scope.current["qnChoiceData"]=$scope.qnChoiceData;
            httpServer.post(URLAPI.saveSaafQuestionnaireL, {
                params: JSON.stringify($scope.current)
            }, function (res) {
                if (res.status == 'S') {
                    JS.alert("保存成功", "success", "确定");
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

        $scope.getSelectRow=function(index){
            $scope.selectRow=index;
            $timeout(function(){
                $('lineListInfo').removeClass("active");
                $(this).addClass("active");
            },10);
        }

        $scope.edit=function(){
            $scope.allowUpdate=true;
            $scope.current=$scope.lineList[$scope.selectRow];
            $scope.qnChoiceData=$scope.current.qnChoiceData;
            if($scope.current.projectType=='text' || $scope.current.projectType=='textarea' || $scope.current.projectType == 'rule_model'){
                $scope.choiceFlag=false;  
            }else{
                $scope.choiceFlag=true;
            }
            $('#myModal').modal('toggle');
        }

        $scope.delete=function(){
            JS.confirm('删除','是否确认删除','确定',function() {
                if($scope.lineList[$scope.selectRow].qnLineId==undefined || $scope.lineList[$scope.selectRow].qnLineId==null || $scope.lineList[$scope.selectRow].qnLineId=="" ){
                    $scope.lineList.splice($scope.selectRow,1);
                }
                else{
                    httpServer.post(URLAPI.deleteSaafQuestionnaireL, {
                        params: JSON.stringify({
                            "qnLineId": $scope.lineList[$scope.selectRow].qnLineId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $timeout(function(){$scope.lineList.splice($scope.selectRow,1);},10);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }

            })
        }

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
                        if(obj.selectQnLineId!=undefined &&
                            obj.selectQnLineId!=null && obj.selectQnLineId!=""){
                            if($scope.singleProjectData[i].qnLineId==obj.selectQnLineId){
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
                    $scope.current.qnChoiceData[$scope.selectSingleIndex]["selectQnLineId"]= singleSelectName[i].value;
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
            if(obj.selectQnLineId==undefined || obj.selectQnLineId==null || obj.selectQnLineId==""){
            }else{
                var selectQnLineIdInfo=obj.selectQnLineId+"";
                selectMultiData=selectQnLineIdInfo.split(",");
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

                    $scope.current.qnChoiceData[$scope.selectMultiIndex]["selectQnLineId"]=multiSurveyLineIds;
                }
            }
            $('#multiSelectProject').modal('toggle');
        }


        //从其他地方移植过来的
        // 跳题事件
        $scope.changeRadioSkip= function (index,row) {
            //获取跳到几题
            $scope.selectQnLineId=row.selectQnLineId;
            $scope.skipSerialNumber="";
            for(var i=0;i<$scope.lineList.length;i++){
                //$scope.lineList[i]["showFlag"]=true;
                console.log("-------selectQnLineId:"+$scope.selectQnLineId);
                if($scope.selectQnLineId==$scope.lineList[i].qnLineId){
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

    }]);
});