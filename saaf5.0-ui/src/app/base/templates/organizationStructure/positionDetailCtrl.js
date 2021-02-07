/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('positionDetailCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams','$timeout',
        function($scope,httpServer,URLAPI,SIEJS,$stateParams,$timeout) {

            $scope.respId = $stateParams.respId;

            $scope.nameArr = [];
            $scope.codeArr = [];

            //获取快码
            $scope.queryLookupLine = function() {
                httpServer.post(URLAPI.queryLookupLineDic, {
                    'params': JSON.stringify({lookupType: 'SAM_OU'})
                }, function (res) {
                    console.log(res)
                    if (res.status == 'S') {
                        for(var i = 0;i < res.data.length;i++) {
                            var item = res.data[i];
                            if($stateParams.orgId.toString() == item.lookupCode) {
                                $scope.nameArr[0] = item.meaning;
                                $scope.position.positionName = $scope.nameArr.join('.');
                                break;
                            }
                        }
                    }
                })
            }


            /**
             * 获取职位
             * @param id
             */
            $scope.getPosition = function(id) {
                httpServer.post(URLAPI.findBasePositionNewPagination, {
                    params: JSON.stringify({
                        positionId: id,
                        respId: $stateParams.respId
                    })
                },function(res) {
                    if(res.status === 'S') {
                        $scope.position = res.data[0];

                        $scope.nameArr = $scope.position.positionName.split('.');
                        $scope.codeArr = $scope.position.positionCode.split('.');

                        //处理旧数据
                        // if($scope.nameArr.length == 2) {
                        //     $scope.nameArr.unshift('');
                        //     $scope.queryLookupLine();
                        // }
                    }else {
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
                $scope.position.respId = $stateParams.respId;
                httpServer.post(URLAPI.saveOrUpdatePositionNewInfo, {
                    params: JSON.stringify($scope.position)
                },function(res) {
                    if(res.status === 'S') {
                        $scope.getPosition(res.data.positionId);
                        SIEJS.alert(res.msg);
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('保存失败','error','确定');
                })
            }

            /**
             * 选中部门赋值渠道并拼接职位名称
             */
            $scope.selectDept = function(key, value, currentList) {
                $scope.position.departmentName = currentList[0].departmentName;
                $scope.position.departmentId = currentList[0].departmentId;
                $scope.position.departmentCode = currentList[0].departmentCode;
                $scope.position.channelCode = currentList[0].channelCode;
                $scope.position.channelName = currentList[0].channelName;
                $scope.position.ouName = currentList[0].ouName;

                $scope.nameArr[1] = currentList[0].departmentName;
                $scope.nameArr[3] = currentList[0].channelName;
                $scope.codeArr[0] = currentList[0].departmentCode;
                $scope.position.positionName = $scope.nameArr.join('.');
                $scope.position.positionCode = $scope.codeArr.join('.');
            }

            /**
             * 清除选择部门
             */
            $scope.clearDeptSel = function() {
                $scope.position.departmentName = '';
                $scope.position.departmentId = '';
                $scope.position.departmentCode = '';
                $scope.position.channelCode = '';
                $scope.position.channelName = '';
                $scope.position.ouName = '';

                $scope.nameArr[1] = '';
                $scope.nameArr[3] = '';
                $scope.codeArr[0] = '';
                $scope.position.positionName = $scope.nameArr.join('.');
                $scope.position.positionCode = $scope.codeArr.join('.');
            }

            /**
             * 选中职务 拼接职位名称
             */
            $scope.selectJob = function(key, value, currentList) {
                $scope.position.jobCode = currentList[0].jobCode;
                $scope.position.jobName = currentList[0].jobName;
                $scope.position.jobId = currentList[0].jobId;

                $scope.nameArr[2] = currentList[0].jobName;
                $scope.codeArr[1] = currentList[0].jobCode;
                $scope.position.positionName = $scope.nameArr.join('.');
                $scope.position.positionCode = $scope.codeArr.join('.');

            }
            /**
             * 清除选择职务
             */
            $scope.clearJobSel = function() {
                $scope.position.jobCode = '';
                $scope.position.jobName = '';
                $scope.position.jobId = '';

                $scope.nameArr[2] = '';
                $scope.codeArr[1] = '';
                $scope.position.positionName = $scope.nameArr.join('.');
                $scope.position.positionCode = $scope.codeArr.join('.');
            }

            /**
             * 切换是否生效状态
             * @param sta
             */
            $scope.changeEnableFlag = function(sta) {
                $scope.position.enableFlag = sta;

                // if(sta === 'Y') {
                //     $scope.position.dateTo = '2099-12-31';
                // }else {
                //     var date = new Date();
                //     $scope.position.dateTo = date.getFullYear() + '-'
                //         + ((date.getMonth() + 1)>10?(date.getMonth() + 1):('0'+(date.getMonth()+1))) + '-'
                //         + (date.getDate()>10?date.getDate():('0'+date.getDate()));
                // }
            }

            /**
             * 监听失效时间  修改是否生效状态
             */
            // $scope.watchDateTo = $scope.$watch('position.dateTo', function(nval,lval) {
            //     if(nval != lval) {
            //         var date = new Date();
            //         var thisDate = date.getFullYear() + '/'
            //             + ((date.getMonth() + 1)>10?(date.getMonth() + 1):('0'+(date.getMonth()+1))) + '/'
            //             + (date.getDate()>10?date.getDate():('0'+date.getDate()));
            //
            //         var setDate = new Date($scope.position.dateTo.replace("-","/"));
            //         if(setDate <= new Date(thisDate)) {
            //             $scope.position.enableFlag = 'N';
            //         }
            //     }
            // },true)

            $scope.$on("$destroy", function () {
                $scope.watchDateTo(); //　销毁监听
            })


            if($stateParams.itemId != '-1') {
                $scope.getPosition($stateParams.itemId);
            }else {
                $scope.queryLookupLine();
                $scope.position = {
                    enableFlag: 'Y',
                    dateTo: '2099-12-31',
                    positionName: '',
                    positionCode: '',
                    respId: $stateParams.respId
                }
            }
        }])
})
