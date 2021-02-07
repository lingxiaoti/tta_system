/**
 * Created by hemingxing on 2017/08/06.
 */
define(['app'], function(app) {
    app.controller('modelCtrl', ['$scope','httpServer','URLAPI','SIE.JS',function($scope,httpServer,URLAPI,JS) {

        $scope.selectBusinessCode;
        $scope.selectModelArray;
        $scope.selectModel;

        $scope.modelContext = false;

        $scope.newModelList = {};
        $scope.newModel = {};

        $scope.init = function() {
            getBusinessLineData();
            getOperator();
            getDisplayType();
            getRULE_MODELGROUP_VIEW_TYPE();
            $scope.dataTypeArr = [
                {
                    ruleDimDataType:'字符串',
                    ruleDimDataCode:'String'
                },
                {
                    ruleDimDataType:'浮点型',
                    ruleDimDataCode:'Double'
                },
                {
                    ruleDimDataType:'日期',
                    ruleDimDataCode:'Date'
                },
                {
                    ruleDimDataType:'整型',
                    ruleDimDataCode:'Integer'
                }
            ];
        }

        //根据业务线获取维度
        function getRile(businessCode) {
            httpServer.post(URLAPI.queryRule,  {
                params: JSON.stringify({
                    var_equal_ruleBusinessLineCode: businessCode
                }),
                pageIndex:-1,
                pageRows:-1
            }, function (res) {
                if (res.status == "S") {
                    $scope.ruleArr = res.data;
                }else {
                    JS.alert(res.msg,'error','确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

        //获取运算符
        function getOperator() {
            httpServer.post(URLAPI.getOpreateType,  {
                params: JSON.stringify({})
            }, function (res) {
                if (res.status == "S") {
                    $scope.operatorArr = res.data;
                }else {
                    JS.alert(res.msg,'error','确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

        //获取展示类型
        function getDisplayType() {
            httpServer.post(URLAPI.saafLookupServlet,  {
                params: JSON.stringify({
                    lookupType:'RULE_GROUP_DET_VIEW_TYPE'
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.displayTypeArr=res.data
                }else {
                    JS.alert(res.msg,'error','确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

        //获取模板组类别
        function getRULE_MODELGROUP_VIEW_TYPE() {
            httpServer.post(URLAPI.saafLookupServlet,  {
                params: JSON.stringify({
                    lookupType:'RULE_MODELGROUP_VIEW_TYPE'
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.groupNameViewTypeData=res.data;
                }else {
                    JS.alert(res.msg,'error','确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };

        //获取业务线//第一层数据
        function getBusinessLineData() {
            httpServer.post(URLAPI.queryBusinessLine,{
                params: JSON.stringify({}),
                pageIndex:-1,
                pageRows:-1
            },function(res) {
                if(res.status == 'S') {
                    $scope.businessLineData = res.data;
                }else {
                    JS.alert(res.msg,'error','确定');
                    console.error(res);
                }
            },function(error) {
                console.error(error);
            })
        }

        //点击业务线
        $scope.selectBusiness = function(item,index) {
            $scope.selectBusinessCode = item.ruleBusinessLineCode;
            getModelArr(item.ruleBusinessLineCode);
            $scope.currentBusinessIndex = index;
            //把展开下标全部还原
            $scope.currentModelArrIndex = -1;
            $scope.currentModelIndex_one = -1;
            $scope.currentModelIndex_three = -1;
            getRile(item.ruleBusinessLineCode);
        }

        //第二层数据
        function getModelArr(businessCode) {
            httpServer.post(URLAPI.queryModel,{
                params:JSON.stringify({
                    var_like_ruleBusinessLineCode: businessCode
                })
            },function(res) {
                if(res.status == '') {}
                $scope.modelArrData = res.data;
            },function(error) {
                console.error(error);
            })
        }

        //点击模板组
        $scope.selectModelArr = function(item,index) {
            getModel_one(item);
            $scope.currentModelArrIndex = index;
            $scope.currentModelIndex_one = -1;
            $scope.currentModelIndex_three = -1;
        }

        //第三层数据
        function getModel_one(item) {
            httpServer.post(URLAPI.queryModelGroup,{
                params: JSON.stringify({
                    var_like_ruleBusinessLineCode: $scope.selectBusinessCode,
                    var_like_modelCode: item.modelCode,
                    var_like_groupLevel:1
                })
            },function(res) {
                $scope.modelData_one = res.data;
            },function(error) {
                console.error(error);
            })
        }

        //点击模板1
        $scope.selectModel_one = function(item,index) {
            getModel_two(item)
            $scope.currentModelIndex_one = index;
            $scope.currentModelIndex_three = -1;
        }

        //第四层数据
        function getModel_two(item) {
            httpServer.post(URLAPI.queryModelGroup,{
                params: JSON.stringify({
                    var_like_ruleBusinessLineCode: $scope.selectBusinessCode,
                    var_like_groupParentCode: item.groupCode,
                    var_like_groupLevel:2
                })
            },function(res) {
                $scope.modelData_two = res.data;
                $scope.changeModel(res.data,item);
            },function(error) {
                console.error(error);
            })
        }

        //点击模板2
        $scope.selectModel_two = function(item,index) {
            $scope.currentModelIndex_three = index;
            getModel_three(item);
        }

        //第五层数据
        function getModel_three(item) {
            httpServer.post(URLAPI.queryModelGroup,{
                params: JSON.stringify({
                    var_like_ruleBusinessLineCode: $scope.selectBusinessCode,
                    var_like_groupParentCode: item.groupCode,
                    var_like_groupLevel:3
                })
            },function(res) {
                $scope.modelData_three = res.data;
                $scope.changeModel(res.data,item);
            },function(error) {
                console.error(error);
            })
        }

        //点击模板三
        $scope.selectModel_four = function(item,index) {
            getModel_four(item);
        }

        function getModel_four(item) {
            httpServer.post(URLAPI.queryModelGroup,{
                params: JSON.stringify({
                    var_like_ruleBusinessLineCode: $scope.selectBusinessCode,
                    var_like_groupParentCode: item.groupCode,
                    var_like_groupLevel:4
                })
            },function(res) {
                // $scope.modelData_three = res.data;
                $scope.changeModel(res.data,item);
            },function(error) {
                console.error(error);
            })
        }

        $scope.changeModel = function(itemArr,item) {
            if(itemArr.length < 1) {
                httpServer.post(URLAPI.queryModelGroupDetail,  {
                    params: JSON.stringify({
                        var_like_groupCode:item.groupCode
                    })
                },function(res) {
                    $scope.model = res.data[0];
                    angular.forEach($scope.ruleArr,function(value, key) {
                        if($scope.model.groupDetDimCode == value.ruleDimCode) {
                            $scope.model.ruleObj = value;
                        }
                    });
                    angular.forEach($scope.displayTypeArr, function(value, key) {
                        if($scope.model.groupDetDimActionViewType == value.lookupCode) {
                            $scope.model.displayTypeObj = value;
                        }
                    })
                    angular.forEach($scope.operatorArr,function(value, key) {
                        if($scope.model.groupDetDimOptCode == value.operatorCode) {
                            $scope.model.operatorObj = value;
                        }
                    })
                    $scope.modelContext = true;
                },function(error) {
                    console.error(error)
                })

            }
        }

        //新增模板组
        $scope.addModelArr = function(event,item) {
            event.stopPropagation();//阻止冒泡事件
            $scope.newModelList.businessObj = item;
            $('#modelListModal').modal('show');
        }

        //保存模板组
        $scope.confirmModelList = function(formStatus) {
            if(!formStatus) {
                $scope.newModelList.ruleBusinessLineCode = $scope.newModelList.businessObj.ruleBusinessLineCode;
                httpServer.post(URLAPI.saveModel,  {
                    params: JSON.stringify($scope.newModelList)
                }, function (res) {
                    if (res.status == "S") {
                        getBusinessLineData();
                        $('#modelListModal').modal('hide');
                        JS.alert('保存成功');
                    }else{
                        JS.alert('保存失败','error','确定');
                    }
                }, function (error) {
                    console.error(error);
                });
            }else {
                JS.alert('请填写完整表单','error','确定');
            }
        }

        //新增模板
        $scope.addModel = function(event,item,level) {
            event.stopPropagation();
            $scope.newModel = {};
            $scope.selectModel = item;
            $scope.level = level;
            $('#modelModal').modal('show');
        }

        $scope.setModelArr = function(item) {
            $scope.selectModelArray=item
        }

        //保存新增模板
        $scope.confirmModel = function(formStatus) {
            if(!formStatus) {
                $scope.newModel.modelCode = $scope.selectModelArray.modelCode;
                $scope.newModel.groupLevel = $scope.level;
                if(!$scope.newModel.hasOwnProperty('groupNameViewFlag')) {
                    $scope.newModel.groupNameViewFlag = false;
                }
                if($scope.level == 1) {
                    $scope.newModel.groupParentCode = '';
                }else {
                    $scope.newModel.groupParentCode = $scope.selectModel.groupCode;
                }
                httpServer.post(URLAPI.saveModelGroup,{
                    params: JSON.stringify($scope.newModel)
                },function(res) {
                    if(res.status == 'S') {
                        getBusinessLineData();
                        $('#modelModal').modal('hide');
                        JS.alert('保存成功');
                    }else {
                        JS.alert('保存失败','error','确定');
                    }
                },function(error) {
                    console.error(error);
                })
            }else {
                JS.alert('请填写完整表单','error','确定');
            }
        }

        //保存模板维度维护
        $scope.confirmEditModal = function(formStatus) {
            if(!formStatus) {
                $scope.model.groupDetDimActionViewType = $scope.model.displayTypeObj.lookupCode;
                $scope.model.groupDetDimActionViewTypeMeaning = $scope.model.displayTypeObj.meaning;
                $scope.model.groupDetDimOptCode = $scope.model.operatorObj.operatorCode;
                $scope.model.groupDetDimOptName = $scope.model.operatorObj.operatorName;
                httpServer.post(URLAPI.saveModelGroupDetail,{
                    params: JSON.stringify($scope.model)
                },function(res) {
                    if(res.status == 'S') {
                        getBusinessLineData();
                        $scope.modelContext = false;
                        JS.alert('保存成功');
                    }else {
                        JS.alert('保存成功','error','确定');
                    }
                })
            }else {
                JS.alert('请填写完整表单','error','确定');
            }
        }
    }])
})