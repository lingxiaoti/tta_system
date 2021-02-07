/**
 * Created by Administrator on 2018/5/13.
 */
'use strict';
define(['app', '../processDesigner/formHtml/formListConfig', 'GooFlow'], function (app, formList) {
    app.controller('setProcessTaskCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS, URLAPI, processTaskConfigSave,processGetProcesser) {
        //流程对象
        $scope.task = {
            id: $stateParams.id,
            key: $stateParams.key
        };
        $scope.show = {};
        $scope.abc="true"
        $scope.formList = formList;


        $scope.tabAction = 'proImg';

        //抄送类型
        $scope.expressionTypeList = [{name: '抄送人', value: 'user'}, {name: '抄送角色', value: 'role'}]

        //页面左边配置对象
        $scope.taskTable = {};


        //页面左边配置参数对象
        if ($location.search().type==='version') {
            $scope.taskParams = {
                processDefinitionId: $stateParams.id
            }

            //获取流程图
            processImageDraw(localStorage.getItem(window.appName + '_certificate'),
                "processImg",
                '',
                $stateParams.key,
                $stateParams.id);
        }else {

            $scope.taskParams = {
                processDefinitionKey: $stateParams.key
            }
            //获取流程图
            processImageDraw(localStorage.getItem(window.appName + '_certificate'),
                "processImg",
                '',
                $stateParams.key);
        }


       $scope.maxSortId=1;

        //拼接流程视图Src
        // $scope.getTaskImage = function (id) {
        //     var s = encodeURIComponent('{"modelId":"' + id + '","type":"image"}');
        //     $scope.imgSrc = URLAPI.processModelsExport + '?params=' + s;
        // }
        // $scope.getTaskImage($stateParams.id);




        /*  //获取角色列表
         processFindRoles({
         params: JSON.stringify({}),
         pageIndex: 1,
         pageRows: -1
         },function(res){
         $scope.roleList = res.data;
         });*/


        //新增配置
        $scope.newNode = function () {
            $scope.node = {
                processDefinitionKey: $stateParams.key,
                ccExpressionType: 'SQL'
            }
        }

        /**
         * create by hmx 2018年9月17日
         * 缩放：zoom
         * 放大：enlarge
         * 还原：reduction
         */
        $scope.zoomNum = 1;
        $scope.zoom = function(str) {
            if(str === 'zoom') {

                if($scope.zoomNum <= 0.2) {
                    return;
                }
                $scope.zoomNum = $scope.zoomNum - 0.1;
                console.log($scope.zoomNum)
            }

            if(str === 'enlarge') {
                if($scope.zoomNum > 1.5) {
                    return;
                }
                $scope.zoomNum = $scope.zoomNum + 0.1;
                console.log($scope.zoomNum)
            }

            if(str === 'reduction') {
                $scope.zoomNum = 1;
            }

            $('.GooFlow_work_inner').css({
                '-webkit-transform': 'scale(' + $scope.zoomNum + ')',
                '-moz-transform': 'scale(' + $scope.zoomNum + ')',
                '-ms-transform': 'scale(' + $scope.zoomNum + ')',
                '-o-transform': 'scale(' + $scope.zoomNum + ')',
                'transform': 'scale(' + $scope.zoomNum + ')',
            });
        }

        $scope.historyTaskDefinitionId = '';
        $scope.clickTaskRow = function (item) {
            //高亮显示选中的节点 hemingxing  update:2018年9月17日
            window._svgImage.markItem(item.taskDefinitionId, "node", true);
            if($scope.historyTaskDefinitionId) {
                window._svgImage.markItem($scope.historyTaskDefinitionId, "node", false);
            }
            $scope.historyTaskDefinitionId = item.taskDefinitionId;

            $scope.node = angular.copy(item);
            $scope.tabAction = 'dealWith';
           
            $scope.show={};
            $scope.node.assignee_taskIds = $scope.node.extend ? JSON.parse($scope.node.extend).assignee_taskIds.join(',') : '';
           
            $scope.node.variables = $scope.node.variables ? JSON.parse($scope.node.variables) : [{
                type: 'string'
            }];

            processGetProcesser({params: JSON.stringify({
                configId:$scope.node.configId
            })},function(res){
               
                if(res.status === 'S'){
                    $scope.maxSortId=1;
                    if($scope.taskTable.rowIndex==0){
                        $scope.node.processers=[]
                    }else{
                        $scope.node.processers = res.data.processers.length ? res.data.processers : [{
                            disabled:'0',sortId:1
                        }];
                        //console.log($scope.node.processers);
                       //记录最大的优先值
                        angular.forEach($scope.node.processers,function (data,index) {
                            if(data.sortId && data.sortId > $scope.maxSortId){
                                $scope.maxSortId =data.sortId;
                            }
                        })
                    }

                    $scope.show.ccNames = res.data.ccUserFullNames; // 抄送人
                    $scope.show.ccRoleNames = res.data.ccRoleNames;// 抄送角色

                    $scope.show.assignee_taskNames = res.data.extend ? JSON.parse(res.data.extend).assignee_taskNames:'';
                  
                }
            })

            if (!$scope.node.editStatus) {
                $scope.node.editStatus = 0;
            }


            $scope.node.pcformUrl = item.pcformUrl ? item.pcformUrl.split('?')[0] : '';
            $scope.node.mobformUrl = item.mobformUrl ? item.mobformUrl.split('?')[0] : '';


            if($location.search().type==='version') {

                // 选人任务节点参数
                $scope.followUserTasksParams = {
                    processDefinitionId: $scope.task.id,
                    taskDefinitionId: $scope.node.taskDefinitionId

                };
            }else {

                // 选人任务节点参数
                $scope.followUserTasksParams = {
                    modelId: $scope.task.id,
                    taskDefinitionId: $scope.node.taskDefinitionId
                };
            }


        }


        //保存设置
        $scope.saveSetProcessTask = function (formStatus) {
            /*

            var urlParam = '?type=' + $scope.node.mobformType;
          urlParam = $scope.node.mobformEditPart ? urlParam + '&editPart=' + $scope.node.mobformEditPart : urlParam;
            if ($scope.node.mobformType === 'editPart' && !$scope.node.mobformEditPart) {
                SIEJS.alert('请填写可编辑的参数名', 'error');
                return;
            }
            debugger;
            var pcUrlParam = $scope.node.pcformOther ? urlParam + '&' + $scope.node.pcformOther.replace(/^\&?/, '') : urlParam;
            var mobUrlParam = $scope.node.mobformOther ? urlParam + '&' + $scope.node.mobformOther.replace(/^\&?/, '') : urlParam;

*/
            var urlParam = '?type=' + ( $scope.node.editStatus ==1 ? 'edit':'readonly');
            var p = {
                ccIds: $scope.node.ccIds,
                ccRoleKeys: $scope.node.ccRoleKeys,
                configId: $scope.node.configId,
                extend: {
                    assignee_taskIds: $scope.node.assignee_taskIds.split(',')
                },
                mobformUrl: $scope.node.mobformUrl ? $scope.node.mobformUrl + urlParam : null,
                pcformUrl: $scope.node.pcformUrl ? $scope.node.pcformUrl + urlParam : null,
                processDefinitionKey: $scope.node.processDefinitionKey,
                processers: $scope.node.processers,
                taskDefinitionId: $scope.node.taskDefinitionId,
                taskName: $scope.node.taskName,
                toAll: $scope.node.toAll,
                callbackUrl: $scope.node.callbackUrl,
                variables: $scope.node.variables,
                editStatus: $scope.node.editStatus,
                pcDataUrl: $scope.node.pcDataUrl,
                mobDataUrl: $scope.node.mobDataUrl,
                enableAutoJump: $scope.node.enableAutoJump
            };


            if (!formStatus) {
                processTaskConfigSave({params: JSON.stringify(p)}, function (res) {
                    if (res.status === 'S') {
                        $scope.taskTable.search();
                        /*  $scope.node = {};
                         $scope.show={};*/
                    }
                });
            }
        }

        // 添加变量
        $scope.addVariables = function () {
            $scope.node.variables.push({
            })
        }

        // 删除变量
        $scope.delVariables = function (index) {
            $scope.node.variables.splice(index, 1);
        }
        //添加角色
        $scope.addProcesser = function () {
            $scope.maxSortId+=1;
            $scope.node.processers.push({
                disabled:'0',
                sortId:$scope.maxSortId
            })
        }
        // 删除变量
        $scope.delProcesser = function (index) {

            $scope.node.processers.splice(index, 1);
            //记录最大的优先值
            if($scope.node.processers.length){
                $scope.maxSortId=1;
                angular.forEach($scope.node.processers,function (data,index) {
                    if(data.sortId && data.sortId > $scope.maxSortId){
                        $scope.maxSortId =data.sortId;
                    }
                })
            }


        }
        // 选择审批人
        $scope.selectProcesser=function(index){
            $scope.selectIndex=index;
            $('#lovUserFind').modal('show');

            $scope.show.processerNames = $scope.node.processers[index].userFullNames;
        }
        $scope.selectProcesserRol=function(index){
            $scope.selectIndex=index;
            $('#lovprocesserRoleKeys').modal('show');
            $scope.show.processerRoleNames = $scope.node.processers[index].roleNames;
        };
        //审批人lov回调
        $scope.processerCallback=function(a ,b ,c){

            var data=$scope.node.processers[$scope.selectIndex];

            data.userFullNames=$scope.show.processerNames;
            data.processerIds=$scope.node.processerIds;
        };

        // 审批人取消选择  lov回调
        $scope.processerCancelCB = function () {
            var data=$scope.node.processers[$scope.selectIndex];
            data.userFullNames=null;
            data.processerIds=null;
        }


        //审批角色lov回调
        $scope.processerRoleCallback= function () {
            var data=$scope.node.processers[$scope.selectIndex];
            data.roleNames=$scope.show.processerRoleNames;
            data.processerRoleKeys=$scope.show.processerRoleKeys;

        };

        //审批角色lov回调
        $scope.processerRoleCB= function () {
            var data=$scope.node.processers[$scope.selectIndex];
            data.roleNames=null;
            data.processerRoleKeys=null;

        };
    });
});
