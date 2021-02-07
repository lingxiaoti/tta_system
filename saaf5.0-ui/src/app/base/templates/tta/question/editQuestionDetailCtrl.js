/**
 * Created by Administrator on 2016/9/26.
 */
define(["app"], function (app) {
    app.controller('editQuestionDetailCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'deleteLookupLine','$location', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, deleteLookupLine,$location) {

        $scope.onlyShowFlag = $location.search()['onlyShow'] == "1" ? true : false; //是否仅仅查看，不修改数据。
        if($scope.onlyShowFlag) {
            setInterval(function () {
                $(":input").not("[name='fullWindow']").not("[name='printButton']").attr("disabled","true");
                $(":button").not("[name='fullWindow']").not("[name='printButton']").attr("disabled","true");
            }, 500)
        }
        
        var qHeaderId = $stateParams.qHeaderId;
        $scope.flag = {};
        $scope.dataTable = [];
        $scope.childDataTable = [];
        $scope.selectDisabled = false
        $scope.editParentSwitch = true//父类型为空  代码详情不能编辑
        //$scope.findByParentIndex=-1
        $scope.addParam = {}
        $scope.params = {}
        $scope.search = function (qHeaderId) {
            httpServer.post(URLAPI.findQuestionByHeaderId, {
                    'params': JSON.stringify(
                        {
                            qHeaderId: parseInt(qHeaderId)
                        }
                    ),
                    pageRows: 130,
                    pageIndex: 1
                },
                function (res) {
                    console.log('头部' + res);
                    if (res.status == 'S') {
                        //debugger;
                        console.log(res.data);
                        $scope.params = res.data.headerData;             //代码类型的数据
                        $scope.dataTable = res.data.lineList;
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        $scope.addCheck = true;
        $scope.updateCheck = true;
        $scope.transitionUpper = function (e) {
            $scope.params.lookupType = $scope.params.lookupType.toUpperCase();

        }

        if (qHeaderId == null || qHeaderId == "" || qHeaderId == undefined || qHeaderId == "undefined") {
            //新增
            $scope.editParentSwitch = false
            $scope.selectDisabled = false
        } else {
            console.log('--------------------')
            console.log($stateParams.qHeaderId)
            //修改
            $scope.selectDisabled = true
            $("#lookupType").attr("readonly", true);
            $("#platformCode").attr("disabled", true);
            $("#systemCode").attr("disabled", true);
            // $("#meaning").attr("readonly", true);
            // $("#description").attr("readonly", true);
            $scope.search(qHeaderId);
        }
        //增加按钮
        $scope.btnNew = function () {
            var lookuptypeArray = {
                qHeaderId: $stateParams.qHeaderId,
                choiceCnContent: null,
                choiceEnContent: null,
                choiceLineId: null,
                choiceType: null,
                isEnable: "Y",
                isShowChild: "Y",
                serialNumber: null
            };
            $scope.dataTable.push(lookuptypeArray);
        }
        //父值Lov回调
        $scope.findByParentCallback = function () {
            if ($scope.flag == 'child') {
                $scope.childDataTable[$scope.findByParentIndex].ruleName = $scope.findByParentLov.selectRow.ruleName;
                $scope.childDataTable[$scope.findByParentIndex].ruleId = $scope.findByParentLov.selectRow.ruleId;
            }
            if ($scope.flag == 'parent') {
                $scope.dataTable[$scope.findByParentIndex].ruleName = $scope.findByParentLov.selectRow.ruleName;
                $scope.dataTable[$scope.findByParentIndex].ruleId = $scope.findByParentLov.selectRow.ruleId;
            }
        }
        $scope.showFindParentModel = function (index, vFlag) {
            $scope.flag = vFlag;
            $scope.findByParentIndex = index;
            $('#findByParentModel').modal('toggle')
        }


        $scope.showFindLookUpModel = function (index, vFlag) {
            $scope.flag = vFlag;
            $scope.findByParentIndex = index;
            $('#findByLookUpModel').modal('toggle')
        }


        //删除职责分配
        $scope.btnDel = function () {
            var index = $scope.dataTable.selectRow
            var choiceLineId = $scope.dataTable[index].choiceLineId;
            JS.confirm('删除', '是否确定删除？', '确定', function () {

                if (choiceLineId == null || choiceLineId == "") {
                    $scope.dataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                    $scope.$apply();
                } else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteQuestionHeaderOrLine, {
                        'params': JSON.stringify({
                            choiceLineId: choiceLineId
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.search(qHeaderId);
                            JS.alert("删除成功", "success", "确定");
                            $scope.$apply();
                        } else
                            JS.alert(res.msg, "error", "确定");
                    }, function (err) {
                        JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });
                    $scope.$apply();
                }
            })
        }




        //保存
        $scope.btnSave = function () {
            $("#saveButton").attr("readonly", true);
            //给子级添加systemCode
            if ($scope.dataTable.length > 0) {
                angular.forEach($scope.dataTable, function (key, value) {
                   // $scope.dataTable[value].systemCode = $scope.params.systemCode
                })
            }
            $scope.allData = {
                headData: $scope.params,
                lineArr: $scope.dataTable,

            }
            httpServer.post(URLAPI.saveOrUpdateQuestionALL, {
                'params': JSON.stringify($scope.allData)
            }, function (res) {
                if (res.status == 'S') {
                    console.info(res);
                    $scope.params = res.data.headerData;
                    $scope.dataTable = res.data.lineList;
                    qHeaderId =  res.data.headerData.qHeaderId;
                   // $scope.search(qHeaderId);
                    JS.alert("保存成功", "success", "确定");
                    $("#saveButton").removeAttr("disabled");
                } else {
                    JS.alert(res.msg, "error", "确定");
                    $("#saveButton").removeAttr("disabled");
                }
            }, function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                $("#saveButton").removeAttr("disabled");
            });
        }
        
        ///////////////////////////////child 操作start////////////////////
        $scope.addChild = function () {
            var rowData = {
                qHeaderId: $stateParams.qHeaderId,
                parentId: $scope.dataTable[$scope.dataTable.selectRow].choiceLineId,
                choiceCnContent: null,
                choiceEnContent: null,
                choiceLineId: null,
                choiceType: null,
                isEnable: "Y",
                isShowChild: "Y",
                serialNumber: null
            };
            $scope.childDataTable.push(rowData);
            console.log("childDataTable：" + JSON.stringify($scope.childDataTable));
        }

        $scope.delChild = function () {
                var index = $scope.childDataTable.selectRow;
                var choiceLineId = $scope.childDataTable[index].choiceLineId;
                JS.confirm('删除', '是否确定删除？', '确定', function () {
                    if (choiceLineId == null || choiceLineId == "") {
                        $scope.childDataTable.splice(index, 1);             //新增列没有ID直接删除
                        JS.alert("操作成功!", "已成功删除数据！", "success");
                        $scope.$apply();
                    } else {
                        //修改列有ID删除该行
                        httpServer.post(URLAPI.deleteQuestionHeaderOrLine, {
                            'params': JSON.stringify({
                                choiceLineId: choiceLineId
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                $scope.childDataTable.splice(index, 1);
                                JS.alert("删除成功", "success", "确定");
                                $scope.$apply();
                            } else
                                JS.alert(res.msg, "error", "确定");
                        }, function (err) {
                            JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        });
                        $scope.$apply();
                    }
                })
            }

        $scope.dbClickShowChild = function () {
            var rowData = $scope.dataTable[$scope.dataTable.selectRow];
            if (!rowData.choiceLineId || !qHeaderId) {
                console.log("choiceLineId:" + rowData.choiceLineId + ", qHeaderId:" + qHeaderId);
                JS.alert("请先选择保存表头信息", "error", "确定");
                return ;
            }
            httpServer.post(URLAPI.findQuestionChoiceLineChild, {
                    'params': JSON.stringify(
                        {
                            qHeaderId: parseInt(qHeaderId),
                            parentId : rowData.choiceLineId
                        }
                    ),
                    pageRows: 130,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.childDataTable = res.data;
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }
        
        $scope.saveChild = function () {
            var vQHeaderId = $scope.childDataTable[0].qHeaderId;
            var vParentId = $scope.childDataTable[0].parentId;
            httpServer.post(URLAPI.saveOrUpadateChoiceLineList, {
                'params': JSON.stringify({lineArr: $scope.childDataTable,qHeaderId: vQHeaderId, parentId: vParentId})
            }, function (res) {
                if (res.status == 'S') {
                    $scope.childDataTable = res.data;
                    //qHeaderId =  res.data.headerData.qHeaderId;
                    // $scope.search(qHeaderId);
                    JS.alert("保存成功", "success", "确定");
                    $("#saveButton").removeAttr("disabled");
                } else {
                    JS.alert(res.msg, "error", "确定");
                    $("#saveButton").removeAttr("disabled");
                }
            }, function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                $("#saveButton").removeAttr("disabled");
            });
        }

        $scope.choiceTypeChange = function (index, vFlag) {
            // 1:自动计算 2:文本 3:值  4:值+自动计算 5:值+文本
            var dataTable = [];
            if (vFlag == 'parent') {
                dataTable = $scope.dataTable[index];
            } else {
                dataTable = $scope.childDataTable[index];
            }
            var choiceType = dataTable.choiceType;
            if (choiceType == '1') {//自动计算,禁用内容
                dataTable.choiceCnContent = dataTable.choiceEnContent = null;
                dataTable.choiceCnContentFlag = dataTable.choiceEnContentFlag = 1;
                dataTable.ruleNameFlag = 0;
            } else if (choiceType == '2') { //全禁用
                dataTable.choiceCnContent = dataTable.choiceEnContent = null;
                dataTable.ruleNameFlag = dataTable.choiceCnContentFlag = dataTable.choiceEnContentFlag = 1;
            } else if (choiceType == '3' || choiceType == '5') { //禁用规则
                dataTable.ruleNameFlag = 1;
                dataTable.choiceCnContentFlag = dataTable.choiceEnContentFlag = 0;
            }else if(choiceType == '4') { //全部禁用
                dataTable.ruleNameFlag = dataTable.choiceCnContentFlag = dataTable.choiceEnContentFlag = 0;
            }
        }
        
        $scope.controlTypeChange = function (index) {
            var dataTable = $scope.childDataTable[index];
            console.log("dataTable:" + JSON.stringify(dataTable));
        }

        //父值Lov回调
        $scope.findByLookCallback = function () {
            if ($scope.flag == 'child') {
                $scope.childDataTable[$scope.findByParentIndex].lookupTypeName = $scope.findByLookUpLov.selectRow.meaning;
                $scope.childDataTable[$scope.findByParentIndex].lookupType = $scope.findByLookUpLov.selectRow.lookupType;
            }
        }

        ///////////////////////////////child 操作end////////////////////


    }]);
});