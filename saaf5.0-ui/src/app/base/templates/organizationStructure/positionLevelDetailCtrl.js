/**
 * Created by hmx on 2018/9/5.
 */
'use strict';
define(['app'],function(app) {
    app.controller('positionLevelDetailCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams','$timeout','arrayFindItemIndex','$filter',
        function($scope,httpServer,URLAPI,SIEJS,$stateParams,$timeout,arrayFindItemIndex,$filter) {

            $scope.respId = $stateParams.respId;

            /**
             * 获取职位层级数据
             * @param id
             */
            $scope.getPositionLevelDetail = function(id) {
                httpServer.post(URLAPI.findPersonLevelDetails,{
                    params: JSON.stringify({
                        mgrPositionId: id,
                        respId: $stateParams.respId
                    })
                },function(res) {
                    if(res.status === 'S') {
                        console.log()
                        if(Array.isArray(res.data)) {
                            $scope.levelDetail.positionLevelData = res.data;
                        }else {
                            $scope.levelDetail = res.data;
                            $scope.levelDetail.respId = $stateParams.respId;
                        }
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
                $scope.levelDetail.respId = $stateParams.respId;
                if(!$scope.levelDetail.mgrPositionId) {
                    $scope.levelDetail.mgrPositionId = 0
                }
                httpServer.post(URLAPI.saveOrUpdatePersonLevel, {
                    params: JSON.stringify($scope.levelDetail)
                },function(res) {
                    if(res.status === 'S') {
                        $scope.getPositionLevelDetail(res.mgrPositionId);
                        // $scope.levelDetail.positionLevelData = res.data;
                        SIEJS.alert('保存成功');
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('保存失败','error','确定');
                })
            }

            /**
             * 确定选中头职位
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectUpPosition = function(key, value, currentList) {
                // $scope.levelDetail.mgrDepartmentId = currentList[0].departmentId;
                $scope.levelDetail.mgrPositionName = currentList[0].positionName;
                $scope.levelDetail.mgrPositionId = currentList[0].positionId;
                $scope.getPositionLevelDetail(currentList[0].positionId);
            }

            /**
             * 确定选择职位
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectPosition = function(key, value, currentList) {
                console.log(currentList)
                $scope.levelDetail.positionLevelData = $scope.levelDetail.positionLevelData?$scope.levelDetail.positionLevelData:[];
                angular.forEach(currentList, function(item, index) {
                    if(arrayFindItemIndex($scope.levelDetail.positionLevelData,item,'positionId') < 0) {
                        item.dateFrom = $filter('date')(new Date(), 'yyyy-MM-dd');;
                        item.dateTo = '2099-12-31';
                        $scope.levelDetail.positionLevelData.push(item);
                    }
                })
            }

            /**
             * 确定选中上级头人员
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectHeaderPerson = function(key, value, currentList) {
                $scope.levelDetail.mgrPersonName = currentList[0].personName;
                $scope.levelDetail.mgrPersonId = currentList[0].personId;
            }

            /**
             * 确定选中行人员
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectPerson = function(key, value, currentList) {
                $scope.levelDetail.positionLevelData[$scope.levelDetail.selectRow].personName = currentList[0].personName;
                $scope.levelDetail.positionLevelData[$scope.levelDetail.selectRow].personId = currentList[0].personId;
            }

            /**
             * 删除行职位
             * @param item
             * @param index
             */
            $scope.deleteAction = function(item, index) {
                console.log(item)
                SIEJS.confirm('删除职位信息','是否确定删除'+item.positionName,'确定',function() {
                    if(item.mgrPositionId) {
                        httpServer.post(URLAPI.deletePersonLevel,{
                            params: JSON.stringify({
                                levelId: item.levelId
                            })
                        },function(res) {
                            if(res.status === 'S') {
                                $scope.levelDetail.positionLevelData.splice(index,1);
                                SIEJS.alert('删除成功');
                            }else {
                                SIEJS.alert(res.msg,'error','确定');
                            }
                        },function(err) {
                            SIEJS.alert('删除失败','error','确定');
                        })
                    }else {
                        $scope.levelDetail.positionLevelData.splice(index,1);
                        SIEJS.alert('删除成功');
                    }

                })
            }

            /**
             * 点击行时，赋值选中行的职位ID
             * @param row
             */
            $scope.clickRow = function(row) {
                $scope.rowPositionId = row.positionId;
            }

            /**
             * 切换行的生效状态
             * @param str
             * @param row
             */
            $scope.changeEnableFlag = function(str, row) {
                if(str === 'Y') {
                    row.dateTo = '2099-12-31';
                }else {
                    row.dateTo = $filter('date')(new Date(),'yyyy-MM-dd');
                }
            }

            /**
             * 查找上级
             */
            $scope.findUpPosition = function () {
                httpServer.post(URLAPI.findPersonLevelPagination, {
                    params: JSON.stringify({
                        positionId: $scope.levelDetail.mgrPositionId,
                        positionName:$scope.levelDetail.mgrPositionName,
                        respId: $stateParams.respId
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        if(res.data[0].mgrPositionId){
                            $scope.levelDetail.mgrPositionId = res.data[0].mgrPositionId;
                            $scope.levelDetail.mgrPositionName = res.data[0].mgrPositionName;
                            httpServer.post(URLAPI.findPersonLevelPagination, {
                                params: JSON.stringify({
                                    mgrPositionId: res.data[0].mgrPositionId,
                                    mgrPositionName:res.data[0].mgrPositionName,
                                    respId: $stateParams.respId
                                })
                            }, function (res2) {
                                if (res2.status === 'S') {
                                    $scope.levelDetail.positionLevelData = res2.data;
                                } else {
                                    SIEJS.alert(res2.msg, 'error');
                                }
                            })
                        }else {
                            SIEJS.alert("当前职位已没有上级！",'warning');
                        }
                    } else {
                        SIEJS.alert(res.msg, 'error');
                    }
                })
            }
            /**
             * 查找下级
             */
            $scope.findDownPosition = function (row) {
                var tempMgrPositionId = row.positionId;
                var tempMgrPositionName = row.positionName;
                httpServer.post(URLAPI.findPersonLevelPagination, {
                    params: JSON.stringify({
                        mgrPositionId: tempMgrPositionId,
                        mgrPositionName: tempMgrPositionName,
                        respId: $stateParams.respId
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        if(res.data.length!=0){
                            $scope.levelDetail.mgrPositionId = angular.copy(tempMgrPositionId);
                            $scope.levelDetail.mgrPositionName = angular.copy(tempMgrPositionName);
                            $scope.levelDetail.positionLevelData = res.data;
                        }else {
                            SIEJS.alert("当前职位已没有下级！",'warning');
                        }
                    } else {
                        SIEJS.alert(res.msg, 'error');
                    }
                })
            }

            if($stateParams.itemId != '-1') {
                $scope.getPositionLevelDetail($stateParams.itemId);
            }else {
                $scope.levelDetail = {};
            }


        }])
})
