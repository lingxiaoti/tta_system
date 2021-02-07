/**
 * Created by Administrator on 2018/5/9.
 */
'use strict';
define(['app', 'ztree', 'angularFileUpload'], function (app) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('processListCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS, openWindow, iframeTabAction,samUpdateTaskConfigProcessers,
                                                samUpdateRoleProcessers, processSuspend, processActivate, IsArray, $timeout, samProcessImport, URLAPI, pageResp, samProcessCopy,
                                                processModelDetail, processDeploy, processCategoriesSave, processCategoriesDelete, processModelsSave, arrayUnique) {
        $scope.params = {};
        //流程分类对象
        $scope.sort = {};
        //流程对象
        $scope.process = {
            processModel: {}
        };
        //流程表格对象
        $scope.processTabel = {};
        //流程表格查询参数对象
        $scope.processParams = {};


        $scope.getBuData = function (hasIds) {
            $scope.userRespList = JSON.parse(localStorage[window.appName + '_successLoginInfo']).userRespList;
            $scope.buData = [];
            $scope.userRespList.map(function (item, index) {
                if (item.responsibilityId == $scope.ctrlRespId) {
                    if (hasIds && item.orgBean && (hasIds.indexOf(item.orgBean.orgId * 1) > -1 || hasIds.indexOf(item.orgBean.orgId + '') > -1)) {
                        item.orgBean.checked = true;
                    }
                    if (item.orgBean) $scope.buData.push(item.orgBean)
                }
            });
            console.log('bu列表', $scope.buData)
     /*       if ($scope.buData.length === 0) {
                SIEJS.alert('当前职责没有配置BU', 'warning');
                return false;
            } else {*/
                return true;
            //}
        };


        $scope.btnNew = function () {
            $('#addSourceType').modal('show');

        };
        $scope.btnModify = function () {
            if (!$scope.processTabel.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
        };
        $scope.btnDel = function () {
            if (!$scope.processTabel.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }

            $scope.processTabel.delete();

        };
        $scope.save = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            log($scope.addParams)
        }


        $scope.checkOU = function (event, item) {
            item.checked = !item.checked;
            if (event.target.nodeName.toLowerCase() === 'label') {
                event.preventDefault();
            }


            /*  if (!$scope.OUIds) $scope.OUIds = [];

              if (item.checked) {
                  $scope.OUIds.push(item.orgId);
              } else {
                  $scope.OUIds.splice($scope.OUIds.indexOf(item.orgId), 1);
              }

              $scope.process.processModel.ouIds = $scope.OUIds;*/


        }


        //新增分类  完成迁移
        $scope.btnNewCategory = function () {
            $scope.sort.sortModalTitle = '新增分类';
            $scope.sort.isEditSort = false;
            $scope.sort.sortTime = {
                parentId: $scope.sort.selectNode ? $scope.sort.selectNode.categoryId : -1,
                parentName: $scope.sort.selectNode ? $scope.sort.selectNode.categoryName : '顶级分类'
            }

            angular.element('#newSortModal').modal('show');
        }
        //编辑分类 完成迁移
        $scope.btnModifyCategory = function () {
            $scope.sort.sortModalTitle = '编辑分类';
            $scope.sort.isEditSort = true;
            $scope.sort.sortTime = $scope.sort.selectNode;
            $scope.sort.sortTime.parentName = $scope.setZtreeSelectText($scope.categoryTree.treeData, $scope.sort.sortTime.parentId) || '顶级分类';
            angular.element('#newSortModal').modal('show');
        }

        //删除分类 完成迁移
        $scope.btnDelCategory = function () {
            processCategoriesDelete({
                params: JSON.stringify({
                    categoryIds: [$scope.sort.selectNode.categoryId]
                })
            }, function (res) {
                if (res.status === 'S') {
                    $scope.categoryTree.reload();
                    $scope.sort.selectNode = null;
                }
            })


        }
        //提交分类 完成迁移
        $scope.sort.confirmSort = function () {
            var p = {
                parentId: $scope.sort.sortTime.parentId || -1,
                categoryName: $scope.sort.sortTime.categoryName,
                processKey: $scope.sort.sortTime.processKey,
                systemCode: window.appName
            }
            if ($scope.sort.isEditSort) { // 编辑
                p.categoryId = $scope.sort.selectNode.categoryId;
                p.versionNum = $scope.sort.selectNode.versionNum
            }

            processCategoriesSave({
                params: JSON.stringify(p)
            }, function (res) {
                if (res.status === 'S') {
                    $('#newSortModal').modal('hide');
                    $scope.categoryTree.reload();
                    $scope.sort.selectNode = null;
                }
            })

        }


        // 树节点点击  完成迁移
        $scope.treeClick = function (item) {

            $scope.processParams.categoryId = item.categoryId;
            $scope.sort.isActive = false;
            $scope.sort.selectNode = item;

            $scope.processTabel.selectRow = false;
            $scope.processTabel.search();
        }

        //
        //点击流程名称  完成迁移
        $scope.clickProcessRow = function (item) {
            $scope.moreTable.selectRow = false;
            angular.element('#moreModal').modal('show');
            $scope.moreParams = {
                processDefinitionKey: item.key

            }
            $timeout(function () {
                $scope.moreTable.search(1);
            }, 100)
        }

        //点击版本列表行
        $scope.clickMoreRow = function () {
            $scope.isSuspended = $scope.moreTable.selectRow.suspended;
        }
        //激活版本
        $scope.btnActive = function () {
            processActivate({
                params: JSON.stringify({
                    processDefinitionId: $scope.moreTable.selectRow.id
                })
            }, function (res) {
                if (res.status === 'S') {
                    angular.element('#moreModal').modal('hide');
                    $scope.processTabel.search(1, null, true);
                }
            });

        }

        //挂起版本
        $scope.btnHangup = function () {
            processSuspend({
                params: JSON.stringify({
                    processDefinitionId: $scope.moreTable.selectRow.id
                })
            }, function (res) {
                if (res.status === 'S') {
                    angular.element('#moreModal').modal('hide');
                    $scope.processTabel.search(1, null, true);
                }
            });
        }

        // 创建随机 KEY
        function modelKey() {
            return JSON.parse(localStorage[window.appName + '_successLoginInfo']).userName + '_' + (new Date()).getTime();
        }

        // 树选择
        $scope.selectTree = function (item) {

            $scope.process.processModel.key = item.processKey;
            if (!item.processKey) {
                SIEJS.alert('当前分类没有设置流程标识，无法新增！', 'error');

            }

        }


        //新增模型
        $scope.btnNew = function () {

            if (!$scope.sort.selectNode.processKey) {
                SIEJS.alert('当前分类没有设置流程标识，无法新增！', 'error');
                return;
            }
            $scope.process.processModalTitle = '新增流程';
            $scope.process.isEditProcess = false;
            angular.forEach($scope.buData, function (data, index) {
                data.checked = false;
            })

            $scope.process.processModel = {
                key: $scope.sort.selectNode.processKey,
                name: $scope.sort.selectNode.categoryName,
                systemCode: window.appName,
                categoryId: $scope.sort.selectNode.categoryId,
                categoryName: $scope.setZtreeSelectText($scope.categoryTree.treeData, $scope.sort.selectNode.categoryId) || '顶级分类'
            }


            if ($scope.getBuData()) {
                if ($scope.buData[0]) $scope.buData[0].checked = true;

                angular.element('#newProcessModal').modal('show');
            }

        }
        // 编辑流程
        $scope.btnModify = function () {
            $scope.process.processModalTitle = '编辑流程';
            $scope.process.isEditProcess = true;
            $scope.process.processModel = $scope.processTabel.selectRow;
            $scope.process.processModel.categoryId = parseInt($scope.processTabel.selectRow.categoryId);
            angular.element('#newProcessModal').modal('show');


            // 获取当前节点的系统码与BU
            processModelDetail({
                modelId: $scope.processTabel.selectRow.modelId
            }, function (res) {
                $scope.process.processModel.systemCode = res.data.systemCode;
                // 处理BU

                $scope.buData = [];

                $scope.process.processModel.ouIds = res.data.ouIds;
                // $scope.getBuData(res.data.ouIds);

                for (var n = 0; n < res.data.ouIds.length; n++) {

                    $scope.buData.push({
                        checked: true,
                        orgId: res.data.ouIds[n],
                        orgName: res.data.ouNames[n],
                        orgDescription: res.data.ouNames[n]
                    })
                }

                $scope.OUIds = res.data.ouIds; // 当前已经有的bu
            })
        };
        //复制流程
        $scope.btnCopy = function () {
            $scope.process.processModalTitle = '复制流程';
            $scope.process.isEditProcess = false;
            $scope.process.processModel = $scope.processTabel.selectRow;
            $scope.process.processModel.categoryId = parseInt($scope.processTabel.selectRow.categoryId);
            // $scope.process.processModel.buId = '-999';
            angular.element('#newProcessModal').modal('show');

            // var resp = pageResp.get();
            // var bu = resp.orgBean;
            // bu.checked = true;
            // $scope.buData = [bu];


        };

        //提交流程模板，成功后跳转到流程新建界面
        $scope.process.confirmProcessModel = function () {

            var ouids = [];
            // $scope.buData.map(function (node, index) {
            //     if (node.checked) {
            //
            //         ouids.push(node.orgId);
            //     }
            // })
            if(ouids&&ouids.length==0)
            ouids.push(-999);
            $scope.process.processModel.ouIds = ouids;

         /*   if (!$scope.process.processModel.ouIds || $scope.process.processModel.ouIds.length === 0) {
                SIEJS.alert('请选择BU', 'warning');
                return;
            }*/

            if ($scope.process.processModalTitle === '复制流程') {
              $scope.process.processModel.key = $scope.process.processModel.processKey + '.-999';
              // $scope.process.processModel.modelId = '';
                samProcessCopy({params: JSON.stringify($scope.process.processModel)}, function (res) {
                    if (res.status === 'S') {
                        $scope.processTabel.search(1);
                        $('#newProcessModal').modal('hide');
                        SIEJS.alert('复制流程成功', 'success');
                    } else {

                        SIEJS.alert(res.msg, 'error');
                    }
                })
            } else {
                processModelsSave({params: JSON.stringify($scope.process.processModel)}, function (res) {
                    if (res.status === 'S') {

                        $('#newProcessModal').modal('hide');
                        $scope.processTabel.search(1);
                        if ($scope.process.processModalTitle === '新增流程') {

                            var ids = res.data.modelId;
                            var url = './plugin/designerModel/modeler.html?modelId=' + ids + '&key=' + res.data.key;
                            openWindow(url);
                        }

                    }
                })
            }


        }


        //设计流程
        $scope.btnDesign = function () {
            var url = './plugin/designerModel/modeler.html?modelId=' + $scope.processTabel.selectRow.modelId + '&key=' + $scope.processTabel.selectRow.key;
            openWindow(url);
        }

        //设置流程
        $scope.btnSetting = function () {
            //$state.go("home.setProcessTask",{id:$scope.processTabel.selectRow.modelId,key:$scope.processTabel.selectRow.key});
            iframeTabAction('流程设置：' + $scope.processTabel.selectRow.name, 'setProcessTask/' + $scope.processTabel.selectRow.modelId + '/' + $scope.processTabel.selectRow.key);

        }

        $scope.showSetting = function (item) {
            iframeTabAction('流程设置：' + item.name, 'setProcessTask/' + item.id + '/' + item.key + '?type=version');
        }

        //流程发布 完成
        $scope.btnRelease = function () {
            processDeploy({
                params: JSON.stringify({
                    modelId: $scope.processTabel.selectRow.modelId
                })
            }, function (res) {
                if (res.status === 'S') {
                    $scope.processTabel.search();
                    SIEJS.alert('发布成功', 'success');
                }
                else {
                    SIEJS.alert(res.msg, 'error');
                }
            });
        }

        $scope.importEven = function (invalid) {
          console.log($scope.fileData);
          var p = {
                categoryId: $scope.process.importModel.categoryId,
                systemCode: window.appName,
                ouIds: [],
                fileId: $scope.fileData[0].filePath
            }

            $scope.buData.map(function (node, index) {
                if (node.checked) {
                    p.ouIds.push(node.orgId);
                }
            })

      /*      if (!p.ouIds || p.ouIds.length === 0) {
                SIEJS.alert('请选择BU', 'warning');
                return;
            }*/
            if ($scope.fileData.length === 0) {
                SIEJS.alert('请上传流程文件！', 'error');
                return;
            }

            samProcessImport({
                params: JSON.stringify(p)
            }, function (res) {
                if (res.status === 'S') {
                    SIEJS.alert('上传流程成功！', 'success');
                    $scope.processTabel.search();
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })

        }

        $scope.importSelectTree = function (item) {
            $scope.process.importModel.key = item.processKey;
            if (!item.processKey) {
                SIEJS.alert('当前分类没有设置流程标识，无法导入！', 'error');

            }
        }

        // 流程导入
        $scope.btnImport = function () {
            if (!$scope.sort.selectNode.processKey) {
                SIEJS.alert('当前分类没有设置流程标识，无法新增！', 'error');
                return;
            }
            /*   $scope.fileData = [{
                   "accessPath": "244",
                   "createdBy": "62169",
                   "createdByUser": "赵湘萍",
                   "creationDate": "2018-09-26 23:49:46",
                   "fileId": 630002,
                   "fileName": "OA_ASSET_APPLY_TASK.bpmn20 (1).xml",
                   "filePath": "244",
                   "fileSize": 7591,
                   "fileType": "xml",
                   "remoteFileName": "OA_ASSET_APPLY_TASK.bpmn20 (1).xml",
                   "sourceFileName": "OA_ASSET_APPLY_TASK.bpmn20 (1).xml",
                   "uploadDate": "2018-09-26 23:49:46"
               }]; // 上传文件*/
            angular.forEach($scope.buData, function (data, index) {
                data.checked = false;
            })
            $scope.process.importModel = {

                key: $scope.sort.selectNode.processKey,
                name: $scope.sort.selectNode.categoryName,
                systemCode: window.appName,
                categoryId: $scope.sort.selectNode.categoryId,
                categoryName: $scope.setZtreeSelectText($scope.categoryTree.treeData, $scope.sort.selectNode.categoryId) || '顶级分类'
            }


            if ($scope.getBuData()) {
                if ($scope.buData[0]) $scope.buData[0].checked = true;

                angular.element('#importModal').modal('show');
            }
        }

        //  流程导出
        $scope.btnExportProcess = function (type) {

            var p = {};
            if (type === 'version') {
                p = {
                    "processDefinitionId": $scope.moreTable.selectRow.id,
                    "type": "xml"
                };

            } else if (type === 'current') {
                p = {
                    "modelId": $scope.processTabel.selectRow.modelId,
                    "type": "xml"
                };
            }

            var url = URLAPI.samProcessExport + '?params=' + encodeURIComponent(JSON.stringify(p));
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截

        }

        // 树加载完成
        $scope.treeLoad = function (ztree) {
            var selectedNode = ztree.getSelectedNodes();
            var nodes = ztree.getNodes();
            var childNodes = ztree.transformToArray(nodes[0]);
            ztree.checkNode(nodes[0], true, true);// 选中节点
            ztree.selectNode(nodes[0]); //选中第一个节点
            ztree.expandNode(nodes[0], true); // 展开节点
            $scope.treeClick(nodes[0]);
        }


        //  根据id 值  设置z-tree-select 的文本
        $scope.setZtreeSelectText = function (ztreeNodes, id) {

            var txt;

            if (IsArray(ztreeNodes)) {
                for (var n = 0; n < ztreeNodes.length; n++) {
                    var item = ztreeNodes[n];
                    if (item.categoryId === id) {
                        txt = item.categoryName;
                        break;
                    }
                }
            }

            return txt;
        }


        $scope.respLoad = function (id, list) {
            $scope.copyBulist = list;
            // debugger;
        }

        $scope.btnBatchReplace = function () {
            $("#batchReplaceModal").modal('show');

        }

        // 批量替换审批人事件
        $scope.batchReplaceEvent = function () {
            if(  $scope.replaceParams.oldName === $scope.replaceParams.newName){
                SIEJS.alert('原审批人与新审批人不能是同一个人','error');
                return;
            }
            var p = {};
            if ($scope.replaceType==='role') {
                if (!$scope.roleListTable.selectRowList || $scope.roleListTable.selectRowList.length===0) {
                    SIEJS.alert('至少要选择一个角色','error');
                    return;
                }

               var roleIds =[];

                $scope.roleListTable.selectRowList.map(function (item) {
                    roleIds.push(item.roleId);
                });

                p={
                    roleIds:roleIds,
                    processerOld:$scope.replaceParams.oldName,
                    processerNew:$scope.replaceParams.newName
                }

                samUpdateRoleProcessers({
                    params:JSON.stringify(p),
                    __tip:'替换角色审批人'
                },function (res) {
                    if(res.status==='S'){
                        $scope.roleListTable.search(1);
                        $scope.roleListTable.selectRowList=[];
                    }
                })


            }else  if ($scope.replaceType==='list') {

                if (!$scope.processTable.selectRowList || $scope.processTable.selectRowList.length===0) {
                    SIEJS.alert('至少要选择一个流程','error');
                    return;
                }
                var keys =[];
                $scope.processTable.selectRowList.map(function (item) {
                    keys.push(item.key);
                });

                p={
                    processDefinitionKeys:keys,
                    processerOld:$scope.replaceParams.oldName,
                    processerNew:$scope.replaceParams.newName
                }

                samUpdateTaskConfigProcessers({
                    params:JSON.stringify(p),
                    __tip:'替换审批人'
                },function (res) {
                    if(res.status==='S'){
                        $scope.processTable.search(1);
                        $scope.processTable.selectRowList=[];
                    }
                })
            }
        }

        //选择用户
        $scope.checkedUser = function (type) {
            $("#userList").modal('show');
            $scope.userType = type;

        }


        $scope.showParams = {};
        $scope.replaceParams={};
        $scope.replaceType='list';

        $scope.changeType=function(type){
            $scope.replaceType=type||$scope.replaceType;
            if ($scope.showParams.oldName){
                if ($scope.replaceType==='role') {
                    $scope.roleListTable.search(1);
                    $scope.roleListTableLoad=function () {
                        $timeout(function () {
                            // 弹窗居中
                            var dialog = $("#batchReplaceModal").find('.modal-dialog');
                            // $(element).css('display', 'block');
                            var h = Math.max(0, ($(window).height() - dialog.height()) / 2);
                            dialog.css({'margin': h + 'px auto'});
                        },500)
                    }
                }else  if ($scope.replaceType ==='list') {
                    $scope.processTable.search(1);
                    $scope.processTableLoad=function () {
                        $timeout(function () {
                            // 弹窗居中
                            var dialog = $("#batchReplaceModal").find('.modal-dialog');
                            // $(element).css('display', 'block');
                            var h = Math.max(0, ($(window).height() - dialog.height()) / 2);
                            dialog.css({'margin': h + 'px auto'});
                        },500)
                    }
                }
            }
        }


        // 选人控件回调，设置当前要替换的人员
        $scope.setReplaceParams = function (key, val) {
            if ($scope.userType === 'old') {
                $scope.showParams.oldName = val+'('+key+')';
                $scope.replaceParams.oldName = key;
                $scope.roleListParams ={
                    processer:key
                }
                $scope.replaceProcessParams ={
                    processer:key
                }
                $scope.changeType();

            } else if ($scope.userType === 'new') {
                $scope.showParams.newName = val+'('+key+')';
                $scope.replaceParams.newName = key;
            }

            if($scope.replaceParams.oldName === $scope.replaceParams.newName){
                SIEJS.alert('原审批人与新审批人不能是同一个人','error');
                return;
            }

        }
    });
});
