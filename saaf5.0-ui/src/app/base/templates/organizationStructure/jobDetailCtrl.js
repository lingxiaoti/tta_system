/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('jobDetailCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams',
        function($scope,httpServer,URLAPI,SIEJS,$stateParams) {

            /**
             * 获取job
             * @param id
             */
            $scope.getJob = function(id) {
                httpServer.post(URLAPI.findBaseJobPagination,{
                    params: JSON.stringify({
                        jobId: id,
                        respId: $stateParams.respId
                    })
                },function(res) {
                    if(res.status === 'S') {
                        $scope.job = res.data[0];
                        $scope.job.respId = $stateParams.respId;
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
                httpServer.post(URLAPI.saveOrUpdateBaseJob, {
                    params: JSON.stringify($scope.job)
                },function(res) {
                    if(res.status === 'S') {
                        $scope.getJob(res.data.jobId);
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
                $scope.job.enableFlag = str;
                // if(str === 'Y') {
                //     $scope.job.dateTo = '2099-12-31';
                // }else {
                //     var date = new Date();
                //     var thisDate = date.getFullYear() + '-'
                //         + ((date.getMonth() + 1)>10?(date.getMonth() + 1):('0'+(date.getMonth()+1))) + '-'
                //         + (date.getDate()>10?date.getDate():('0'+date.getDate()));
                //
                //     $scope.job.dateTo = thisDate;
                // }
            }

            /**
             * 监听失效时间  修改是否生效状态
             */
            // $scope.watchDateTo = $scope.$watch('job.dateTo', function(nval,lval) {
            //     if(nval != lval) {
            //         var date = new Date();
            //         var thisDate = date.getFullYear() + '/'
            //             + ((date.getMonth() + 1)>10?(date.getMonth() + 1):('0'+(date.getMonth()+1))) + '/'
            //             + (date.getDate()>10?date.getDate():('0'+date.getDate()));
            //
            //         var setDate = new Date($scope.job.dateTo.replace("-","/"));
            //         if(setDate <= new Date(thisDate)) {
            //             $scope.job.enableFlag = 'N';
            //         }
            //     }
            // },true)

            $scope.$on("$destroy", function () {
                $scope.watchDateTo(); //　销毁监听
            })



            if($stateParams.itemId != '-1') {
                $scope.getJob($stateParams.itemId)
            }else {
                $scope.job = {
                    respId: $stateParams.respId,
                    enableFlag: 'Y',
                    dateTo: '2099-12-31'
                };
            }

        }])
})
