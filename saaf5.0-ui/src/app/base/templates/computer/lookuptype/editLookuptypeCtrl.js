/**
 * Created by Administrator on 2016/9/26.
 */
define(["app"], function (app) {
    app.controller('editLookuptypeCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'deleteLookupLine', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, deleteLookupLine) {

        var lookupTypeId = $stateParams.lookupTypeId;


        $scope.dataTable = [];
        $scope.selectDisabled=false
        $scope.editParentSwitch=true//父类型为空  代码详情不能编辑
        //$scope.findByParentIndex=-1
        $scope.addParam={}
        $scope.params={}
        $scope.search = function (lookupTypeId) {


            httpServer.post(URLAPI.querylookupType, {
                    'params': JSON.stringify(
                        {
                            lookupTypeId: parseInt(lookupTypeId)
                        }
                    ),
                    pageRows: 130,
                    pageIndex: 1
                },
                function (res) {
                     console.log('头部');
                     console.log(res);
                    if (res.status == 'S') {
                        // $scope.dataTable = res.data.lookupValuesList;       //代码详情的数据
                        $scope.params = res.data[0];             //代码类型的数据
                        console.log(res.data[0])
                        $scope.addParam['lookupType']=$scope.params.pLookupType
                        $scope.params['parentLookupTypeId']=$scope.params.parentLookupTypeId
                        //console.log($scope.params.parentLookupTypeId)
                        if($scope.params.parentLookupTypeId==''){//没有父类型 不允许修改代码详情父值
                            $scope.editParentSwitch=true
                        }else{
                            $scope.editParentSwitch=false
                        }
                        if ($scope.params.customizationLevel == "S") {//系统级不允许新增修改修改
                            $scope.addCheck = false;
                            $scope.updateCheck = false;
                        } else if ($scope.params.customizationLevel == "E") {//扩展级允许新增不允许修改
                            $scope.updateCheck = false;
                        }

                        // 查询行表
                        httpServer.post(URLAPI.queryLookupLine, {
                                'params': JSON.stringify(
                                    {
                                        lookupType: $scope.params.lookupType,
                                        systemCode: $scope.params.systemCode,
                                        deleteFlag: 0
                                    }
                                ),
                                pageRows: 1000,
                                pageIndex: 1
                            },
                            function (res) {
                                console.log('代码详情的数据')
                                console.log(res)
                                if (res.status == 'S') {
                                    $scope.dataTable = res.data;       //代码详情的数据
                                }
                                else {
                                    JS.alert(res.msg, "error", "确定");
                                }
                            }

                            ,
                            function (err) {
                                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                            }
                        )
                    }
                    else {
                        JS.alert(res.msg, "error", "确定");
                    }
                }

                ,
                function (err) {
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            )


            ;
        }
        $scope.addCheck = true;
        $scope.updateCheck = true;

        $scope.transitionUpper = function (e) {
            $scope.params.lookupType = $scope.params.lookupType.toUpperCase();

        }

        if (lookupTypeId == null || lookupTypeId == "" || lookupTypeId == undefined || lookupTypeId == "undefined") {
            //新增
            $scope.editParentSwitch=false
            $scope.selectDisabled=false
        }
        else {
            console.log('--------------------')
            console.log($stateParams.lookupTypeId)
            //修改
            $scope.selectDisabled=true
            $("#lookupType").attr("readonly", true);
            $("#platformCode").attr("disabled", true);
            $("#systemCode").attr("disabled", true);
            // $("#meaning").attr("readonly", true);
            // $("#description").attr("readonly", true);
            $scope.search(lookupTypeId);
        }
        //增加按钮
        $scope.btnNew = function () {
            var lookuptypeArray = {
                lookupCode: "",
                lookupType: $scope.params.lookupType,
                meaning: "",
                tag: "",
                description: "",
                enabledFlag: "Y",
                startDateActive: "",
                endDateActive: "",
                newFlag: "Y",
                systemCode: '',
                parentMeaning:'',
                parentLookupValuesId:''

            };
            $scope.dataTable.push(lookuptypeArray);
        }
        //父值Lov回调
        $scope.findByParentCallback= function () {
            $scope.dataTable[$scope.findByParentIndex].parentMeaning=$scope.findByParentLov.selectRow.meaning
            $scope.dataTable[$scope.findByParentIndex].parentLookupValuesId=$scope.findByParentLov.selectRow.lookupValuesId
        }
        $scope.showFindParentModel= function (index) {
            $scope.findByParentIndex=index
            $('#findByParentModel').modal('toggle')
            $scope.findByParentLov.search()
        }
        //删除职责分配
        $scope.btnDel = function () {

            var index=$scope.dataTable.selectRow
            var lookupValuesId = $scope.dataTable[index].lookupValuesId;


            JS.confirm('删除', '是否确定删除？', '确定', function () {

                if (lookupValuesId == null || lookupValuesId == "") {
                    $scope.dataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                    $scope.$apply();
                }
                else {
                    $scope.deleteLookupValue(lookupValuesId);      //修改列有ID删除该行
                    $scope.search(lookupTypeId);
                    $scope.$apply();
                }

            })

        }


        //删除
        $scope.deleteLookupValue = function (lookupValuesId) {
            //var userRespId = $scope.respdata[index].userRespId;


            deleteLookupLine({
                params: JSON.stringify({
                    id: lookupValuesId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.$apply();
                }
            })


            httpServer.post(URLAPI.deleteLookupLine, {
                'params': JSON.stringify({
                    id: lookupValuesId
                })
            }, function (res) {
                if (res.status == 'S') {
                    JS.alert("删除成功", "success", "确定");
                    $scope.$apply();
                }
                else
                    JS.alert(res.msg, "error", "确定");
            }, function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        }


        //保存
        $scope.btnSave = function () {
            $("#saveButton").attr("readonly", true);

            $scope.params.lookupType= $scope.params.lookupType==undefined?'':$scope.params.lookupType.toUpperCase();
            //$scope.params.lookupTypeId = lookupTypeId;
            //$scope.dataTable.lookupCode = $scope.params.lookupCode;
            //给子级添加systemCode
            if($scope.dataTable.length>0){
                angular.forEach($scope.dataTable,function(key,value){
                    $scope.dataTable[value].systemCode= $scope.params.systemCode
                })
            }
            $scope.allData = {
                value: $scope.dataTable,
                type: $scope.params,

            }
            //console.log($scope.dataTable)
            //console.log($scope.allData)
            //return
            httpServer.post(URLAPI.saveLookupType, {
                'params': JSON.stringify($scope.allData)
            }, function (res) {
                if (res.status == 'S') {
                    console.info(res);
                    $scope.params = res.data.header;
                    $scope.dataTable = res.data.line;
                    lookupTypeId=res.data.header.lookupTypeId
                     $scope.search(lookupTypeId);
                    JS.alert("保存成功", "success", "确定");
                    $("#saveButton").removeAttr("disabled");
                }
                else {
                    JS.alert(res.msg, "error", "确定");
                    $("#saveButton").removeAttr("disabled");
                }
            }, function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                $("#saveButton").removeAttr("disabled");
            });
        }

    /*    //查询重复
        $scope.virSearch = function (lookupTypeId) {
            console.info(lookupTypeId);
            httpServer.post(URLAPI.queryLookupLine, {
                'params': JSON.stringify({lookupTypeId: lookupTypeId})
            }, function (res) {
                if (res.status == 'S') {
                    console.info(res);
                    $scope.dataTable = res.data.lookupValuesList;       //代码详情的数据
                    $scope.params = res.data.lookupTypeRow;             //代码类型的数据
                }
            })
        }*/

    }]);
});