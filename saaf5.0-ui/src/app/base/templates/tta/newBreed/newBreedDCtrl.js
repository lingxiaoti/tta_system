/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('newBreedDCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams',
        function($scope,httpServer,URLAPI,SIEJS,$stateParams) {
            $scope.dataTable = [];
            $scope.dataTable.selectRow
            /**
             *
             * @param id
             */
            $scope.getNewBreed = function(id) {
                httpServer.post(URLAPI.findTtaNewbreedOne,{
                    params: JSON.stringify({
                        newbreedSetId: id,
                    })
                },function(res) {
                    if(res.status === 'S') {
                        $scope.dataTable = res.data.nbLine;
                        $scope.new =  res.data.nbHearder;
                    }else{
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败','error','确定');
                })
            }

            /**
             * 保存
             */
            $scope.btnSave = function() {
                for(var i=0;i<$scope.dataTable.length;i++){
                    var one = $scope.dataTable[i];
                    if(!one.purchaseType){
                        one.purchaseType ="";
                    }
                    if(!one.storeType){
                        one.storeType ="";
                    }
                    for(var j=0;j<$scope.dataTable.length;j++){
                        var two = $scope.dataTable[j];
                        if(!two.purchaseType){
                            two.purchaseType ="";
                        }
                        if(!two.storeType){
                            two.storeType ="";
                        }
                        if( one.range == two.range && one.unit == two.unit &&
                            one.purchaseType == two.purchaseType &&
                            one.storeType == two.storeType &&  one.isEnable == two.isEnable &&
                            one.deptCode == two.deptCode && i !=j){
                            SIEJS.alert('收取范围,单位,采购类型,店铺类型,是否启用,组合必须唯一,重复的是'+one.range,'error','确定');
                            return ;
                        }
                    }
                }
                httpServer.post(URLAPI.saveOrUpdateTtaNewbreedSetALL, {
                    params: JSON.stringify({
                        nbHearder:$scope.new,
                        nbLine:$scope.dataTable,
                        deleteFlag:0
                    })
                },function(res) {
                    if(res.status === 'S') {
                        $scope.dataTable = res.data.nbLine;
                        $scope.new =  res.data.nbHearder;
                        SIEJS.alert(res.msg);
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('保存失败','error','确定');
                })
            }

            /**
             * 切换是否生效
             * @param str
             */
            $scope.changeEnableFlag = function(str) {
                $scope.new.isEffective = str;

            }

            if($stateParams.itemId != '-1') {
                $scope.getNewBreed($stateParams.itemId)
            }else {
                $scope.new = {
                    isEffective: '1',
                };
            }

            $scope.btnHDel = function () {

                if ($scope.new.newbreedSetId) {
                    SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                        httpServer.post(URLAPI.saveOrUpdateTtaNewbreedSetALL, {
                            params: JSON.stringify({
                                nbHearder:$scope.new,
                                nbLine:$scope.dataTable,
                                deleteFlag:1
                            })
                        }, function (res) {
                            if ('S' == res.status) {
                                SIEJS.alert('删除成功');
                                $scope.new ={};
                                $scope.dataTable ={};
                            } else {
                                SIEJS.alert(res.msg, 'error', '确定');
                            }
                        }, function (error) {
                            console.error(error);
                            SIEJS.alert('删除失败', 'error', '确定');
                        })
                    })
                }else{
                    SIEJS.alert('当前没有数据可以删除', 'error', '确定');
                }

            }

            //增加按钮
            $scope.btnNew = function () {
                var newBreedArray = {
                    range: "",
                    unit: "元/SKU/店铺",
                    standard: ""
                };
                $scope.dataTable.push(newBreedArray);
            }

            //删除
            $scope.btnDel = function (value) {
                if('H'===value){
                    $scope.btnHDel();
                }else{
                    $scope.btnLDel();
                }
            }

            $scope.btnLDel = function(){
                var index=$scope.dataTable.selectRow
                var nbLine = $scope.dataTable[index];
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    if (nbLine.newbreedSetLineId == null || nbLine.newbreedSetLineId == "") {
                        $scope.dataTable.splice(index, 1);             //新增列没有ID直接删除
                        SIEJS.alert("操作成功!", "已成功删除数据！", "success");
                        // $scope.$apply();
                    }
                    else {
                        $scope.delete(nbLine);      //修改列有ID删除该行
                    }

                })
            }


            //删除
            $scope.delete = function (nbLine) {
                delete({
                    params: JSON.stringify({
                        nbLine: nbLine,
                        deleteFlag:1

                    })
                }, function (res) {
                    if (res.status == 'S') {
                       // $scope.$apply();
                    }
                })

                httpServer.post(URLAPI.saveOrUpdateTtaNewbreedSetLIne, {
                    'params': JSON.stringify({
                        nbLine: nbLine,
                        deleteFlag:1
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        SIEJS.alert("删除成功", "success", "确定");
                        $scope.getNewBreed($scope.new.newbreedSetId);
                       // $scope.$apply();
                    }
                    else
                        SIEJS.alert(res.msg, "error", "确定");
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            }
            /**
             * 收取范围控制 店铺类型 和采购类型是否必填
             */
            $scope.nbRangeChange = function (row,value,lookUpData){
                var rowObject =  $scope.dataTable[parseInt(row)] ;
                if(value){
                    if( '3' === value){
                        rowObject.purchaseType ='';
                    }else{
                        rowObject.storeType = '';
                    }
                }else{
                    rowObject.storeType = '';
                    rowObject.purchaseType ='';
                }

            }

            //选择部门
            $scope.getDeptCode = function (value) {
                //  $scope.areaComponent = e;
                $('#selectDeptId').modal('toggle')
            };

            //点击确认按钮
            $scope.selectDeptReturn = function (key, value, currentList) {
                $scope.new.deptCode = currentList[0].departmentCode;
                $scope.new.deptName = currentList[0].departmentName;
                $scope.new.deptId = currentList[0].departmentId;
            }

            //行选择部门
            $scope.getDeptCodeL = function (value) {
                //  $scope.areaComponent = e;
                if(! $scope.new.deptId){
                    SIEJS.alert('请先选择上面的部门', "error", "确定");
                    return ;
                }
                $('#selectDeptIdL').modal('toggle')
            };

            //点击确认按钮
            $scope.selectDeptLReturn = function (key, value, currentList) {
                var row = $scope.dataTable[$scope.dataTable.selectRow];
                row.deptCode = currentList[0].departmentCode;
                row.deptName = currentList[0].departmentName;
            }
        }])
})
