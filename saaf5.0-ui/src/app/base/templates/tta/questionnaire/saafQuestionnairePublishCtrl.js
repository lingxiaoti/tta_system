define(["app", 'ztree'], function (app, ztree) {
    app.controller('saafQuestionnairePublishCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', '$rootScope', '$state', '$timeout','SIEJS','iframeTabAction', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, $rootScope, $state, $timeout,SIEJS,iframeTabAction) {

        $scope.publish = {};

        $scope.add = function () {
            $scope.allowUpdate = true;
            $scope.showTemplateFlag = true;
            $scope.publish = {
                qnHeadId: "",
                qnTitle: "",
                qnCode: "",
                qnType: "",
                startDateActive: $rootScope.$getToday(),
                endDateActive: "",
                userNames: "",
                cityNames: "",
                usersId: "",
                citysId: "",
                status: "draft"
            }
            $scope.selUserData = [];
            $('#publishInfo').modal('toggle');
        }

        $scope.edit = function () {
            if ($scope.dataTable.selectRow == null) {
                swal('请您先选中要修改的行!!');
                return;
            }
            $scope.searchLine($scope.dataTable.selectRow);
            $scope.allowUpdate = true;
        }
        $scope.searchLine = function (index) {
            $scope.allowUpdate = false;
            $scope.showTemplateFlag = false;

            $scope.selCityData = $scope.dataTable.data[index].cityList;
            $scope.selUserData = $scope.dataTable.data[index].userList;

            if ($scope.selCityData == null || $scope.selCityData == undefined || $scope.selCityData == "") {
                $scope.selCityData = [];
            }
            if ($scope.selUserData == null || $scope.selUserData == undefined || $scope.selUserData == "") {
                $scope.selUserData = [];
            }

            var cityNameInfo = "";
            for (var i = 0; i < $scope.selCityData.length; i++) {
                cityNameInfo = cityNameInfo + $scope.selCityData[i].instName;
                if (i != ($scope.selCityData.length - 1)) {
                    cityNameInfo = cityNameInfo + ",";
                }
            }

            var userNameInfo = "";
            for (var j = 0; j < $scope.selUserData.length; j++) {
                userNameInfo = userNameInfo + $scope.selUserData[j].userFullName;
                if (j != ($scope.selUserData.length - 1)) {
                    userNameInfo = userNameInfo + ",";
                }
            }

            $scope.publish = {
                publishId: $scope.dataTable.data[index].publishId,
                qnHeadId: $scope.dataTable.data[index].qnHeadId,
                qnTitle: $scope.dataTable.data[index].qnTitle,
                qnCode: $scope.dataTable.data[index].qnCode,
                qnType: $scope.dataTable.data[index].qnType,
                startDateActive: $scope.dataTable.data[index].startDateActive,
                endDateActive: $scope.dataTable.data[index].endDateActive,
                userNames: userNameInfo,
                cityNames: cityNameInfo,
                usersId: $scope.dataTable.data[index].usersId,
                citysId: $scope.dataTable.data[index].citysId,
                status: $scope.dataTable.data[index].status,
                description: $scope.dataTable.data[index].description
            }

            $('#publishInfo').modal('toggle');

        }

        $scope.delete = function (row) {
            JS.confirm('删除', '是否确认删除该条发布信息？', '确定', function () {
                httpServer.post(URLAPI.deleteSaafQuestionnairePublish, {
                    'params': JSON.stringify({
                        "publishId": row.publishId
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        JS.alert("删除成功!", "success", "确定");
                        $scope.dataTable.search();
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                });
            })
        }
        $scope.abandon = function (row) {
            JS.confirm('废弃', '是否确认废弃该条发布信息？', '确定', function () {
                httpServer.post(URLAPI.updateSaafQuestionnaireToAbandon, {
                    'params': JSON.stringify({
                        "publishId": row.publishId
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        JS.alert("废弃成功!", "success", "确定");
                        $scope.dataTable.search();
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                });
            })
        }
        $scope.publishSurvey = function (obj) {
            JS.confirm('发布', '是否确认发布该条信息？', '确定', function () {
                httpServer.post(URLAPI.updateSaafQuestionnairePublishToPublish, {
                    'params': JSON.stringify({
                        "publishId": obj.publishId
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        JS.alert("发布成功!", "success", "确定");
                        $scope.dataTable.search();
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                });
            })
        }

        $scope.analysis = function (obj) {
            httpServer.post(URLAPI.findResultPerson, {
                'params': JSON.stringify({
                    "publishId": obj.publishId
                })
            }, function (res) {
                if (res.status === 'S' && res.count > 0) {
                    $scope.resultPersonData = res.data;
                    iframeTabAction('统计', 'saafQuestionnaireReport/' + obj.publishId );
                } else {
                    JS.alert("暂无答卷信息！", "error", "确定");
                }
            });
        }

        $scope.getQRCode = function (obj) {
            $('#qRCode').modal('toggle');
            $('#qrcodeInfo').empty();
            //判断是对内还是对外
            $scope.statusFlag=true;
            $scope.publishUrl="";
            var _url = serviceURL;
            if(obj.qnTypeName=='对外使用'){
                $scope.statusFlag=false;
                $scope.publishUrl=_url+"questionSuveryIndex.html?id="+obj.publishId;
                $timeout(function () {
                    $('#qrcodeInfo').qrcode(_url+"questionSuveryIndex.html?id="+obj.publishId);
                }, 10);
            }else{
                $scope.statusFlag=true;
                $scope.publishUrl=_url+"index.html#/home/vrQuestionSurveyView/Y/"+obj.publishId;
                $timeout(function () {
                    $('#qrcodeInfo').qrcode(_url+"index.html#/home/vrQuestionSurveyView/Y/"+obj.publishId);
                }, 10);
            }

        }

        $scope.copyContent=function(){
            var copyobject=document.getElementById("copy-content");
            copyobject.select();
            document.execCommand("Copy");
            JS.alert("已复制链接");
        }

        $scope.downloadQrcode=function(){
            var canvas = $('#qrcodeInfo').find("canvas").get(0);
            try {//解决IE转base64时缓存不足，canvas转blob下载
                var blob = canvas.msToBlob();
                navigator.msSaveBlob(blob, 'qrcode.jpg');
            } catch (e) {//如果为其他浏览器，使用base64转码下载
                var url = canvas.toDataURL('image/jpeg');
                $("#download").attr('href', url).get(0).click();
            }
            return false;
        }

        $scope.queryAnswerSheet = function (obj) {
            httpServer.getData(URLAPI.findVrQuestionSurveyResult, 'POST', {
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
                        var qnChoiceIds = $scope.lineList[i].qnChoiceId;
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

        $scope.templateData = {};
        $scope.qnLov = {};
        $scope.templateParams = {
            status: "APPROVED"
        };
        $scope.selectSurveyTemplateInfo = function () {
            $('#qnLov').modal('toggle');
            $scope.templateParams = {
                status: "APPROVED"
            };
        }

        $scope.confrimTemplate = function () {
            if ($scope.templateData.selectRow == null) {
                JS.alert("请选中一行问卷信息！", "error", "确定");
                return false;
            }
            $scope.publish.qnHeadId = $scope.templateData.data[$scope.templateData.selectRow].qnHeadId;
            $scope.publish.qnTitle = $scope.templateData.data[$scope.templateData.selectRow].qnTitle;
            $scope.publish.qnCode = $scope.templateData.data[$scope.templateData.selectRow].qnCode;
            $scope.publish.qnType = $scope.templateData.data[$scope.templateData.selectRow].qnType;
            $('#templateInfo').modal('toggle');
        }

        $scope.savePublishInfo = function () {
            if ($scope.qnLov.selectRow.qnTitle == undefined || $scope.qnLov.selectRow.qnTitle == null || $scope.qnLov.selectRow.qnTitle == "") {
                JS.alert("标题不能为空,请选择问卷模板！", "error", "确定");
                return false;
            }
            //if ($scope.qnLov.selectRow.qnType == "INTERNAL_USER") {
            //    if($scope.qnLov.selectRow.userNames=='' || $scope.qnLov.selectRow.userNames==null || $scope.qnLov.selectRow.userNames==undefined){
            //        JS.alert("请选择人员，人员不能为空！", "error", "确定");
            //        return false;
            //    }
            //}
            $("#publishButton").attr("disabled");
            httpServer.post(URLAPI.saveSaafQuestionnairePublish, {
                    'params': JSON.stringify($scope.qnLov.selectRow)
                }, function (res) {
                    if (res.status == 'S') {
                        SIEJS.alert(res.msg, "success", "确定");
                        $("#publishButton").removeAttr("disabled");
                        $('#publishInfo').modal('toggle');
                        $scope.dataTable.search();
                    }else {
                        SIEJS.alert(res.msg, "error", "确定");
                        $("#publishButton").removeAttr("disabled");
                    }
                } ,
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            )


            //httpServer.post(URLAPI.saveSaafQuestionnairePublish, 'POST', {
            //    'params': JSON.stringify($scope.qnLov.selectRow)
            //}, function (res) {
            //    if (res.status == 'S') {
            //        JS.alert("保存成功。", "success", "确定");
            //        $("#publishButton").removeAttr("disabled");
            //        $('#publishInfo').modal('toggle');
            //        $scope.dataTable.search();
            //    }
            //    else {
            //        JS.alert(res.msg, "error", "确定");
            //        $("#publishButton").removeAttr("disabled");
            //    }
            //}, function (err) {
            //});
        }


        $scope.treeTableParams = {

        };

        /////监听ztree　当前选中的节点  获得当前上级职位的父级Id
        // var orgTreeSetting = {
        //     data:{
        //         key:{
        //             name:"instName"
        //         },
        //         simpleData:{
        //             enable: true,
        //             idKey: "instId",
        //             pIdKey: "parentInstId",
        //             rootPId: -1
        //         }
        //     },
        //     async:{
        //         url:URLAPI.queryInstZTree,
        //         enable:true,
        //         type:"POST",
        //         autoParam:["instId=parentInstId"],
        //         dataFilter:function (treeId, parentNode, responseData) {
        //             if(responseData.status=="S"){
        //                 return responseData.data;
        //             }
        //         }
        //     },
        //     edit: {
        //         drag: {
        //             isCopy: true,
        //             isMove: false
        //         }
        //     },
        //     view: {
        //         showIcon: true,
        //         fontCss: {}
        //     },
        //     callback:{
        //         onClick:function(event, treeId, treeNode, clickFlag){
        //
        //             $scope.currentSelectNode = treeNode;
        //             //如果是树状，则点击时在右边表格加载下级
        //             if(treeNode.instId > 0){
        //                 $scope.treeTableParams.instId=treeNode.instId;
        //             }else{
        //                 $scope.treeTableParams.instId=""
        //             }
        //             $scope.searchTreeTable();
        //             //展开当前节点
        //             var treeObj = $.fn.zTree.getZTreeObj("orgTree");
        //             treeObj.expandNode(treeNode,true);
        //         }
        //     }
        // };
        // angular.element(document).ready(function () {
        //     $scope.treeTableParams.varInstType = 'DEPT';
        //
        //     //初始化组织树数据（分层管理）
        //     var initTreeData = [
        //         {
        //             "instName":"组织架构",
        //             "instId":-2,
        //             "parentInstId":-1,
        //             "isParent":1,
        //             "open":1
        //         },{
        //             "instName":"层级架构",
        //             "instId":0,
        //             "parentInstId":-2,
        //             "isParent":1,
        //             "open":1
        //         }
        //     ];
        //     var treeObj = $.fn.zTree.init(angular.element('#orgTree'),orgTreeSetting,initTreeData);
        //     var node = treeObj.getNodeByParam("instId",0);
        //     treeObj.expandNode(node,true);
        // });

        //监听　ztree数据
        $scope.$watch('zTreeData.selectNode', function (newVal, oldVal) {

            if (newVal == oldVal) return;
            if (newVal.instId > 0) {
                $scope.treeTableParams.instId = newVal.instId;
                $scope.searchTreeTable();
            } else {
                $scope.treeTableParams.instId = ""
            }


        }, true);

        $scope.userNamesData=[];
        $scope.searchTreeTable = function () {
            httpServer.getData(URLAPI.findUserByInstIdList, 'POST', {
                'params': JSON.stringify({
                    instId:$scope.treeTableParams.instId
                })
            }, function (res) {
                $scope.userNamesData = res.data;
            });
        }

        $scope.searchUserTable = function () {
            httpServer.getData(URLAPI.findUserByInstIdList, 'POST', {
                'params': JSON.stringify({
                    instId:$scope.treeTableParams.instId,
                    userFullName:$scope.treeTableParams.userFullName,
                    responsibilitynIds:$scope.treeTableParams.responsibilitynIds
                })
            }, function (res) {
                $scope.userNamesData = res.data;
            });
        }

        $scope.deleteInstInfo=function(){
            $scope.treeTableParams.instId="";
            $scope.treeTableParams.userFullName="";
            $scope.treeTableParams.responsibilitynNames="";
            $scope.treeTableParams.responsibilitynIds="";
            $scope.userNamesData=[];
        }

        $scope.selectAll=function(){
            for(var k=0;k<$scope.userNamesData.length;k++){
                if ($scope.selUserData.length > 0) {
                    var existFlag = false;
                    for (var i = 0; i < $scope.selUserData.length; i++) {
                        if ($scope.userNamesData[k].userId == $scope.selUserData[i].userId) {
                            existFlag = true;
                        }

                        if (i == $scope.selUserData.length - 1 && !existFlag) {
                            $scope.selUserData.push({
                                userId: $scope.userNamesData[k].userId,
                                userName: $scope.userNamesData[k].userName,
                                userFullName: $scope.userNamesData[k].userFullName
                            })
                        }
                    }
                } else {
                    $scope.selUserData.push({
                        userId: $scope.userNamesData[k].userId,
                        userName: $scope.userNamesData[k].userName,
                        userFullName: $scope.userNamesData[k].userFullName
                    })
                }
            }

        }

        $scope.deleteAll=function(){
            $scope.selUserData=[];
        }


        $scope.selectPersons = function () {
            $scope.treeTableParams.instId="";
            $scope.treeTableParams.userFullName="";
            $scope.treeTableParams.responsibilitynNames="";
            $scope.treeTableParams.responsibilitynIds="";
            $scope.userNamesData=[];
            $('#userInfo').modal('toggle');
        }

        $scope.selUserInfo = function (obj, index) {
            if ($scope.selUserData.length > 0) {
                var existFlag = false;
                for (var i = 0; i < $scope.selUserData.length; i++) {
                    if (obj.userId == $scope.selUserData[i].userId) {
                        existFlag = true;
                    }

                    if (i == $scope.selUserData.length - 1 && !existFlag) {
                        $scope.selUserData.push({
                            userId: $scope.userNamesData[index].userId,
                            userName: $scope.userNamesData[index].userName,
                            userFullName: $scope.userNamesData[index].userFullName
                        })
                    }
                }
            } else {
                $scope.selUserData.push({
                    userId: $scope.userNamesData[index].userId,
                    userName: $scope.userNamesData[index].userName,
                    userFullName: $scope.userNamesData[index].userFullName
                })
            }
        }
        $scope.reduceUser = function (obj, index) {
            $scope.selUserData.splice(index, 1);
        }
        $scope.userSaveToTable = function () {
            if ($scope.selUserData.length > 0) {
                var userNameInfo = "";
                var userIdInfo = "";
                for (var i = 0; i < $scope.selUserData.length; i++) {
                    userNameInfo = userNameInfo + $scope.selUserData[i].userFullName;
                    userIdInfo = userIdInfo + $scope.selUserData[i].userId;
                    if (i != ($scope.selUserData.length - 1)) {
                        userNameInfo = userNameInfo + ",";
                        userIdInfo = userIdInfo + ",";
                    }
                }
                $scope.publish.userNames = userNameInfo;
                $scope.publish.usersId = userIdInfo;
                $('#userInfo').modal('toggle');
            } else {
                $scope.publish.userNames = "";
                $scope.publish.usersId = "";
                $('#userInfo').modal('toggle');
            }

        }

        //保存职责
        $scope.lovReturnFunctionResp = function (selectRowData) {
            $scope.treeTableParams.responsibilitynNames="";
            $scope.treeTableParams.responsibilitynIds="";
            for(var i=0;i<selectRowData.length;i++){
                if(i==0){
                    $scope.treeTableParams.responsibilitynNames=selectRowData[i].responsibilityName;
                    $scope.treeTableParams.responsibilitynIds=selectRowData[i].responsibilityId;
                }else{
                    $scope.treeTableParams.responsibilitynNames=
                        $scope.treeTableParams.responsibilitynNames+","+selectRowData[i].responsibilityName;
                    $scope.treeTableParams.responsibilitynIds=
                        $scope.treeTableParams.responsibilitynIds+","+selectRowData[i].responsibilityId;
                }
            }
        }

        $scope.approve=function(){
            httpServer.getData(URLAPI.saveSubmitQuestionSurveyPublish, 'POST', {
                'params': JSON.stringify({
                    "publishId": $scope.dataTable.data[$scope.dataTable.selectRow].publishId
                })
            }, function (res) {
                if (res.status === 'S') {
                    console.log(res);
                    $state.go("home.flowPaper", {processInstanceId: res.data.processInstanceId});
                }
                else {
                    JS.alert(res.msg, "error", "确定");
                }
            });
        }

        $scope.goApproveInfo=function(){
            if($scope.dataTable.data[$scope.dataTable.selectRow].processInstanceId==undefined ||
                $scope.dataTable.data[$scope.dataTable.selectRow].processInstanceId==null ||
                $scope.dataTable.data[$scope.dataTable.selectRow].processInstanceId==""){
                JS.alert("旧数据未发起审批，不允许跳转。", "error", "确定");
            }else{
                $state.go("home.flowPaper",{processInstanceId:$scope.dataTable.data[$scope.dataTable.selectRow].processInstanceId});
            }
        }
    }]);
});