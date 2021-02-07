/**
 * Created by hmx on 2018/8/7.
 */
define(['app'], function (app) {
    app.controller('positionAllotAddCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS', 'iframeTabAction','$stateParams','arrayFindItemIndex','$timeout','$filter',
        function ($scope, httpServer, URLAPI, SIEJS, iframeTabAction,$stateParams,arrayFindItemIndex,$timeout,$filter) {

            $scope.respId = $stateParams.respId;
            $scope.ouId = $stateParams.ouId;

            /**
             * 获取职位分配明细
             * @param id
             */
            $scope.getAllotDetail = function(id) {
                httpServer.post(URLAPI.findPersonAssignDetails, {
                    params: JSON.stringify({
                        personId: id,
                        ouId: $stateParams.ouId,
                        // respId: $stateParams.respId
                    })
                }, function (res) {
                    console.log(res);
                    if ( res.status =='S') {
                        $scope.allotDetail = res.data;
                    } else {
                        $scope.allotDetail.personAssignData = [];
                        // SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    SIEJS.alert('请求失败', 'error', '确定');
                })
            }

            /**
             * 保存
             */
            $scope.btnSave =function () {
                $scope.allotDetail.respId = $stateParams.respId;
                httpServer.post(URLAPI.saveOrUpdatePersonAssign, {
                    params: JSON.stringify($scope.allotDetail)
                }, function (res) {
                    console.log(res);
                    if ( res.status =='S') {
                        $scope.getAllotDetail(res.personId);
                        SIEJS.alert('保存成功');
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    SIEJS.alert('保存失败', 'error', '确定');
                })
            }


            /**
             * 删除行数据
             * @param item
             * @param index
             */
            $scope.deleteAction = function (item,index) {
                SIEJS.confirm('删除职位信息','是否确定删除'+item.positionName,'确定',function() {
                    if(item.assignId) {
                        httpServer.post(URLAPI.deletePersonAssign,{
                            params: JSON.stringify({
                                assignId: item.assignId,
                                respId: $stateParams.respId
                            })
                        },function(res) {
                            if(res.status === 'S') {
                                $scope.allotDetail.personAssignData.splice(index,1);
                                SIEJS.alert('删除成功');
                            }else {
                                SIEJS.alert(res.msg,'error','确定');
                            }
                        },function(err) {
                            SIEJS.alert('删除失败','error','确定');
                        })
                    }else {
                        $scope.allotDetail.personAssignData.splice(index,1);
                        SIEJS.alert('删除成功');
                    }

                })
            }

            /**
             * 人员选择确认回调
             */
            $scope.selectPerson = function(key, value, currentList){
                console.log(currentList)
                $scope.allotDetail.departmentId = currentList[0].departmentId;
                $scope.allotDetail.personId = currentList[0].personId;
                $scope.allotDetail.personName = currentList[0].personName;
                $scope.getAllotDetail(currentList[0].personId);
            };

            /**
             * 清除选择人员
             */
            $scope.clearPersonSel = function() {
                $scope.allotDetail.departmentId = '';
                $scope.allotDetail.personId = '';
                $scope.allotDetail.personName = '';
                $scope.allotDetail = [];
            }

            /**
             * 确定选择职位
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectPosition = function(key, value, currentList) {
                $scope.allotDetail.personAssignData = $scope.allotDetail.personAssignData?$scope.allotDetail.personAssignData:[];
                angular.forEach(currentList, function(item, index) {
                    if(arrayFindItemIndex($scope.allotDetail.personAssignData,item,'positionId') < 0) {
                        var date = new Date();
                        item.dateTo = '2099-12-31';
                        item.enableFlag = 'Y';
                        item.dateFrom = date.getFullYear() + '-'
                            + ((date.getMonth() + 1)>9?(date.getMonth() + 1):('0'+(date.getMonth()+1))) + '-'
                            + (date.getDate()>9?date.getDate():('0'+date.getDate()));

                        $scope.allotDetail.personAssignData.push(item);
                    }

                })
            }

            /**
             * 切换行的生效状态
             * @param str
             * @param row
             */
            $scope.changeEnableFlag = function(str, row) {
                // if(str === 'Y') {
                //     row.dateTo = '2099-12-31';
                // }else {
                //     row.dateTo = $filter('date')(new Date(),'yyyy-MM-dd');
                // }
            }

            if($stateParams.itemId && $stateParams.itemId != '-1'){
                $scope.getAllotDetail($stateParams.itemId)
            }else {
                $scope.allotDetail = {};
            }


        }])
})
