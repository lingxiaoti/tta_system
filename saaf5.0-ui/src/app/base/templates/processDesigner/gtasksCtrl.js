/**
 * Created by Administrator on 2018/6/6.
 */
'use strict';
define(['app', 'ztree'], function (app, ztree) {
    app.controller('gtasksCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,
                                           processDelegateConfigSave, processDelegateConfigStatus) {
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));

        $scope.isAdmin = $scope.userData.isadmin;
        $scope.currentUserName = $scope.userData.userName;
        $scope.currentUserId = $scope.userData.userId;
        $scope.params = {};
        $scope.processParams = {
            type: 1
        };
        $scope.rangeTreeParams= {

        };
        $scope.showRangeTreeParams = {};
        $scope.addParams = {};
        $scope.user = JSON.parse(sessionStorage[window.appName.toUpperCase() + '_successLoginInfo']);
        $scope.showParams = {
            currentUser: $scope.user.userName
        }

        $scope.btnNew = function () {
            $scope.rangeTreeParams= {};
            $scope.addParams = {};
            $scope.rangeTree.search();

            $scope.addParams.selectCurrentUser = $scope.isAdmin == 'Y' ? '': $scope.currentUserName;
            $scope.addParams.selectDelegateUserId =  $scope.isAdmin == 'Y' ? '' :  $scope.currentUserId;
            $('#entrustApproval').modal('show');
        };
        $scope.btnModify = function () {
            if (!$scope.gtasksTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
            debugger;
            $('#entrustApproval').modal('show');
            $scope.addParams = $scope.gtasksTable.selectRow;

            $scope.rangeTreeParams= {
                delegateConfigId:$scope.addParams.configId
            };
            $scope.addParams.delegateUser= $scope.gtasksTable.selectRow.delegate_userName;
            //$scope.showParams.currentUser = $scope.gtasksTable.selectRow.client_userName;
            $scope.addParams.selectCurrentUser = $scope.gtasksTable.selectRow.client_userName;
            $scope.addParams.selectDelegateUserId = $scope.gtasksTable.selectRow.clientUserId;

            $scope.rangeTree.search(null,function(list) {

                var categoryName = [];
                for (var i = 0 ; i < list.length ; i ++) {
                    if (list[i].checked === true){
                        categoryName.push(list[i].categoryName);
                    }
                }

                $scope.showParams.value = categoryName.join(',');

            });


        };
        $scope.btnDel = function () {
            if (!$scope.gtasksTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
            $scope.gtasksTable.delete();
        };

        $scope.clickRow = function (item) {

            var txt = item.disabled ? '生效' : '失效';
            var icon = item.disabled ? 'fa-refresh' : 'fa-ban';
            $("#btnInvalid").html('<i class="fa ' + icon + '" aria-hidden="true"></i>' + txt);
        }

        // 失效按钮
        $scope.btnInvalid = function () {
            var ids = [$scope.gtasksTable.selectRow.configId];
             processDelegateConfigStatus({
                "configIds": ids,
                "disabled": !$scope.gtasksTable.selectRow.disabled

            }, function (res) {
                if (res.status === 'S') {
                    $scope.gtasksTable.search();
                }
            })
        }


        $scope.save = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            log($scope.addParams);


            processDelegateConfigSave($scope.addParams, function (res) {
                if (res.status === 'S') {
                    $('#entrustApproval').modal('hide');
                    $scope.gtasksTable.selectRow = false;
                    $scope.gtasksTable.search(1);
                }
            })
        }


        // 选择范围
        $scope.setRangeVal = function (nodes, keys, values) {

            var category = [];
            var categoryName = [];
            var items = [];
            var itemNames = [];
            var itemCode = [];
            for (var i = 0; i < nodes.length; i++) {
                var item = nodes[i];
                //var parentNode = item.getParentNode();
                // 当树被全选或 未包含字节点时，  categoryId > 0 是分类  < 0 是流程
                /*if (item.check_Child_State === 2 || (item.check_Child_State === -1 && item.categoryId > 0)) {
                    if (!parentNode || parentNode.check_Child_State !== 2) {
                        category.push(item.categoryId);
                        categoryName.push(item.categoryName)
                    }
                } else if (item.categoryId < 0) {  // 当前节点是流程
                    // 当前流程的分类不是全选状态
                    if (parentNode && parentNode.check_Child_State !== 2) {
                        items.push(item.categoryId);
                        itemNames.push(item.categoryName);
                        itemCode.push(item.categoryCode);
                    }
                }*/
                // console.log(item.check_Child_State)
                // console.log(item.categoryId)
                if ((item.check_Child_State === 2 || item.check_Child_State === -1) && item.categoryId > 0) {
                    category.push(item.categoryId);
                    categoryName.push(item.categoryName)
                }
            }
            $scope.addParams.processDefinitionKeys = itemCode.join(',');
            $scope.addParams.categoryIds = category.join(',');
            log($scope.addParams);
        }

        /*      $scope.clickColumns = function (item) {
         var p = {
         taskId: item.taskId
         };
         processGetTaskUrl({
         params: JSON.stringify(p)
         }, function (res) {
         if (res.status === 'S') {

         iframeTabAction('委托审批流程：' + item.bpm_title, 'showProcess/' + res.data.url+
         '&action=approval&processDefinitionId=' + res.data.processDefinitionId + '&processDefinitionKey='+ res.data.processDefinitionKey +
         '&delegateId=' + item.delegateId
         )
         } else {
         SIEJS.alert(res.msg, 'error');
         }
         })
         }*/

        // 切换 表格类型
        $scope.changeListType = function (index) {
            $scope.processParams = {
                type: index
            };
            $scope.gtasksTable.search(1);
        }

        // 查看流程范围
        $scope.showRange=function(item) {
            $("#rangeModal").modal('show');
            $scope.showRangeTreeParams = {
                delegateConfigId:item.configId,
                delegateShow:true
            }

            $scope.showRangeTree.reload();

        }


    });
});
