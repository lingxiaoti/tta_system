/**
 * Created by hmx on 2018/10/9.
 */
'use strict';
define(['app','ztree'],function(app) {
    app.controller('positionLevelTreeCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$timeout','$location',
        function($scope,httpServer,URLAPI,SIEJS,$timeout,$location) {

            /**
             * 获取职责
             */
            $scope.getResps = function() {
                httpServer.post(URLAPI.findRespByMenuCode, {
                    __errorRepeated: 3,// 当请求错误的时候，重新请求3次
                    params: JSON.stringify({
                        menuCode: $location.search().menucode
                    })
                },function(res) {
                    console.log(res)
                    if(res.status === 'S') {
                        $scope.dataList = res.data;
                        $scope.respId = res.data[0].responsibilityId;
                        $scope.orgId = res.data[0].orgBean.orgId;
                        $scope.getTreeAction($scope.respId);
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取职责失败','error','确定');
                })
            }

            $scope.getResps();

            /**
             * 切换指着，重新请求树数据
             * @param id
             */
            $scope.changeResp = function(id) {
                for(var i in $scope.dataList) {
                    var o = $scope.dataList[i];
                    if(o.responsibilityId == id) {
                        $scope.orgId = o.orgBean.orgId;
                        break;
                    }
                }
                $scope.tabSwitch(0);
                $scope.getTreeAction(id);
                $scope.showLevelTabStatus = false;
            }


            /**
             * 刷新树数据
             * @param id
             */
            $scope.getTreeAction = function(id) {
                $scope.getTreeUrl = 'sam_findPositionTree';
                $scope.getTreeParams = {respId: id,positionName: $scope.positionName};
                $scope.positionTree.reload();
            }

            /**
             * 点击树节点
             * @param item
             */
            $scope.clickNode = function(item) {
                console.log(item)
                $scope.positionDetail = angular.copy(item);
                $scope.tabSwitch(0);
                $scope.showLevelTabStatus = false;
                $scope.allotParams = {
                    respId: $scope.respId,
                    ouId: $scope.orgId,
                    positionId: item.positionId
                }
                $scope.allotTable.search(1);
            }

            /**
             * 搜索树节点
             */
            $scope.treeSearch = function () {
                if (!$scope.searchTreeParam) {
                    $scope.showTree = false;
                    return;
                }
                $scope.showTree = true;
                $scope.newTreeArray = [];
                for (var n in $scope.positionTree.treeData) {
                    var item = $scope.positionTree.treeData[n];
                    if (item.allName.indexOf($scope.searchTreeParam) > -1) {
                        $scope.newTreeArray.push(item);
                    }
                }

            }

            /**
             * 点击职位层级tab后请求数据
             */
            $scope.getLevelList = function() {
                if(!$scope.showLevelTabStatus && $scope.positionDetail) {
                    $scope.showLevelTabStatus = true;
                    $scope.levelParams = {
                        respId: $scope.respId,
                        mgrPositionId: $scope.positionDetail.positionId
                    }
                    $scope.levelTable.search(1);
                }
            }

            /**
             * 切换tab
             */
            $scope.tabSwitch = function(index) {
                $('#myTab li:eq('+index+') a').tab('show');
            }


        }])
})