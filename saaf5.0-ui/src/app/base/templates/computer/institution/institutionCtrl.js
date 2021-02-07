/**
 * Created by houxingzhang on 2018-01-10.
 */
'use strict';
define(["app", 'pinyin', 'ztree'], function (app, pinyin) {
    app.controller('institutionCtrl', function ($scope, $rootScope, SIEJS, $timeout, httpServer, URLAPI, ToChildrenJson, $state, $filter,
                                                orgPersonDelete, setNewRow, orgDelete, $stateParams, positionSave, organizationSave, positionDel, arrayFindObj, orgPersonSave) {

        $scope.showFlag = 1; // 默认显示　树状模式
        $scope.showType = 'DEPT'; // 默认显示的是　部门　类型

        $scope.treeCurrentItem = null; // 当前树选中节点
        $scope.orgTableParams = {}; // orgTable的查询参数
        $scope.params={};
        $scope.orgTableUrl = 'orgChildrenAllList'; //  orgTable的查询url
        $scope.orgParams = {}; // 组织架构参数
        $scope.deptModalTitle = '';
        $scope.lookupCode = {};
        $scope.treeParams = {'deleteFlag': 0};
        $scope.rootParentId = 0; // 默认顶层的ID



        $scope.treeType = $stateParams.treeType;

        switch ($stateParams.treeType) {
            case '行政':
                $scope.treeParams.treeType = $stateParams.treeType;
                $scope.lookupCode = 'ORG_TREE_MODE';
                break;
            case '核算':
                $scope.treeParams.treeType = $stateParams.treeType;
                $scope.lookupCode = 'BASE_TREE_MODE';
                break;
            case '预算':
                $scope.treeParams.treeType = $stateParams.treeType;
                $scope.lookupCode = 'BASE_TREE_MODE';
                break;
            default:
                $scope.treeParams.treeType = $stateParams.treeType;
                $scope.lookupCode = 'ORG_TREE_MODE';
                break;
        }


        $scope.btnNew=function(){}
        $scope.btnModify=function(){
            $scope.add('edit');
        };
        $scope.btnRestore=function(){
            $scope.params={};
        }

        // 表格查询
        $scope.btnSearch=function(){
            $scope.orgTableParams =$scope.params;
            $scope.orgTableParams.deleteFlag=0;
            $scope.orgTableParams.treeType=$scope.treeType;
            $scope.orgTableUrl = 'orgList';
            $timeout(function () {
                $scope.orgTable.search(1);
            })

        }

        $scope.layout = function (index) {
            $scope.showFlag = index;
            if (index === 1) {
                $scope.orgTableParams = $scope.oldOrgTableParams || {};// 还原树模式下的参数

                $("#orgTable").prependTo($("#treeLayout"));
                $scope.orgTableUrl = 'orgChildrenAllList';
                $timeout(function () {
                    $scope.orgTable.search(1);
                })
            } else if (index === 2) { // 表格查询
              /*  $scope.orgTableParams = {
                    deleteFlag: 0,
                    treeType: $scope.treeType
                };*/
                $("#orgTable").prependTo($("#tableLayout"));
                $scope.btnSearch();
                //$scope.orgTableUrl = 'orgList';

              /*  $timeout(function () {
                    $scope.orgTable.search(1);
                })*/

            } else if (index === 3) {
                $scope.treeCurrentItem = $scope.orgTree.selectNode;
            }
        };

        // 简拼
        $scope.jianpin = function () {
            $scope.orgParams.orgCode = pinyin.shouxiePinyin($scope.orgParams.orgName)
        };

        // 删除组织
        $scope.btnDel = function () {

            switch ($scope.showType) {
                case 'DEPT':
                case 'ORG':
                    var id = $scope.orgTable.selectRow.orgId;
                    orgDelete({params: JSON.stringify({id: id})}, function (res) {
                        if (res.status === 'S') {
                            $scope.orgTable.search();
                            $scope.orgTree.reload();
                        }
                    });
                    break;
                case 'JOB': // 职位

                    positionDel({params: JSON.stringify({id: $scope.jobTable.selectRow.positionId})}, function (res) {
                        if (res.status === 'S') {
                            // 重载tree
                            $scope.orgTree.search();
                        }
                    });
                    break;
                case 'PERSON': // 人员
                    orgPersonDelete({params: JSON.stringify({id: $scope.personTable.selectRow.personOrgId})},function(res){
                        if (res.status === 'S') {
                            $scope.personTable.search(1);
                        }
                    });
                    break;
            }

        };

        // 新增
        $scope.add = function (type) {

            $scope.saveType = type || 'add'; // 保存类型


            function orgParams() {
                if (type === 'edit') {
                    $scope.orgParams = angular.copy($scope.orgTable.selectRow);
                    var item = arrayFindObj($scope.orgTree.treeData, $scope.orgParams.parentOrgId, 'orgId');
                    if (item) {
                        $scope.treeCurrentItem = {orgName: item.orgName, orgId: item.orgId}
                    } else {
                        $scope.treeCurrentItem = {orgName: '顶层组织架构', orgId: $scope.rootParentId}
                    }
                    $scope.orgParams.oldParentOrgId = $scope.orgParams.parentOrgId;// 更新层级时要传

                } else {
                    $scope.treeCurrentItem = $scope.orgTree.selectNode;

                    $scope.orgParams = {
                        orgType: $scope.showType,
                        startDate: $rootScope.$getToday(),
                        treeType: $scope.treeType
                    };


                }


            }

            switch ($scope.showType) {
                case 'DEPT':
                    orgParams();
                    $scope.orgTitle = '部门';
                    $("#deptModal").modal('show');
                    break;
                case 'ORG':
                    orgParams();
                    $scope.orgTitle = '组织机构';
                    $("#deptModal").modal('show');
                    break;
                case 'JOB': // 查职位


                    if (type === 'edit') {
                        $scope.jobParams = angular.copy($scope.jobTable.selectRow);
                    } else {
                        $scope.jobParams = {startDate: $rootScope.$getToday()};
                    }
                    $("#jobModal").modal('show');

                    break;
                case 'PERSON': // 查人员

                    if(!$scope.treeCurrentItem || !$scope.treeCurrentItem.orgId){

                        SIEJS.alert('请选择组织后进行添加人员','warning');
                        return;
                    }
                    if (type === 'edit') {
                        $rootScope.goto('员工详情：' + $scope.personTable.selectRow.personName, 'employeeDetail/' + $scope.personTable.selectRow.personId)
                    } else {
                        $("#person-lov").modal('show');
                    }
                    break;
            }
        };


        // 职位dataTable行内 新增员工
        $scope.addPerson = function (item) {
            $("#person-lov").modal('show');
            $scope.addPositionId = item.positionId;
        };

        // 显示类型更改
        $scope.showTypeChange = function (val) {
            $scope.showType = val || $scope.showType;
            $scope.orgTableParams.orgType = $scope.showType;
            if (!$scope.treeCurrentItem)return;// tree　还未选择节点 不进行数据查询
            orgTableSearch();
        };

        // 树点击节点回调
        $scope.treeClick = function (item) {
            $scope.treeCurrentItem = item; // 当前树选中节点

            orgTableSearch();
        }

        function orgTableSearch() {
            switch ($scope.showType) {
                case 'DEPT':
                case 'ORG':
                    $scope.orgTableParams = {
                        isValid: true,
                        orgHierarchyId: $scope.treeCurrentItem.orgHierarchyId,// 组织机构层级关系
                        orgType: $scope.showType // 类型（ORG：机构；DEPT：部门）
                    };
                    $scope.oldOrgTableParams = angular.copy($scope.orgTableParams); /// 记录参数
                    $timeout(function () {
                        $scope.orgTable.search(1);// 组织表格进行查询数据    部门＼机构 　使用同一个dataTable
                    });
                    break;
                case 'JOB': // 查职位
                    $scope.jobTableParams = {
                        orgId: $scope.treeCurrentItem.orgId
                    };
                    $timeout(function () {
                        $scope.jobTable.search(1);//  职位 查询
                    });
                    break;
                case 'PERSON': // 查人员
                    $scope.personTableParams = {
                        orgId: $scope.treeCurrentItem.orgId
                    };
                    $timeout(function () {
                        $scope.personTable.search(1);// 人员查询
                    });
                    break;
            }


        }

        $scope.saveOrg = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必须项', 'warning');
                return;
            }
            $scope.orgParams.parentOrgId = $scope.treeCurrentItem.orgId || $scope.rootParentId;

            organizationSave({params: JSON.stringify($scope.orgParams)}, function (res) {
                if (res.status === 'S') {
                    $("#deptModal").modal('hide');
                    $scope.orgTable.search();
                    $scope.orgTree.reload();

                }
            })
        };

        // 保存职位
        $scope.saveJob = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必须项', 'warning');
                return;
            }
            $scope.jobParams.orgId = $scope.treeCurrentItem.orgId;
            positionSave({params: JSON.stringify($scope.jobParams)}, function (res) {
                if (res.status === 'S') {
                    $("#jobModal").modal('hide');
                    if ($scope.saveType === 'edit') {
                        setNewRow($scope.jobTable.selectRow, $scope.jobParams);// 更新成功，只更新当前行，不必再去重载当前表格
                    } else {
                        $scope.jobTable.search(1); // 重载表格
                    }
                }
            })
        }


        // 添加员工  personLovList 进行回调  请注意,此方法用LOV-list 回调了,会传回三个参数: key,val,item
        $scope.savePerson = function (key, val, item) {

            var list = $scope.personLovList.selectRowList;
            var p = {
                params: JSON.stringify({
                    orgId: $scope.treeCurrentItem.orgId,
                    personIds: $scope.current.person.key,
                    positionId: $scope.addPositionId
                })
            };



            orgPersonSave(p, function (res) {
                if (res.status === 'S') {
                    $scope.personTable.search(1);
                }
            })
        };

        // 删除组织 com-org-tree 回调 -----------------------------------------------------
        $scope.comRemove = function (item, id) {
            orgDelete({params: JSON.stringify({id: id})}, function (res) {
                if (res.status === 'S') {

                    $scope.orgTable.search();
                    $scope.orgTree.reload();
                }
            });
        };
        $scope.comAdd = function (item) {
            $scope.saveType = 'add'; // 保存类型
            $("#deptModal").modal('show');

            $scope.orgParams = {
                orgType: 'DEPT',
                startDate: $rootScope.$getToday(),
                treeType: $scope.treeType
            };
            $scope.treeCurrentItem = {orgName: item.orgName, orgId: item.orgId};
            $scope.orgParams.parentOrgId = item.orgId;
        };
        $scope.comEdit = function (item) {
            $scope.saveType = 'edit'; // 保存类型
            $("#deptModal").modal('show');


            var parent = arrayFindObj($scope.orgTree.treeData, item.parentOrgId, 'orgId');

            if (parent) {
                $scope.treeCurrentItem = {orgName: parent.orgName, orgId: parent.orgId}
            } else {
                $scope.treeCurrentItem = {orgName: '顶层组织架构', orgId: $scope.rootParentId}
            }
            $scope.orgParams = angular.copy(item);
            $scope.orgParams.oldParentOrgId = item.parentOrgId;// 更新层级时要传


        }

        // 拖曳更新层级关系
        $scope.updateLevel = function (treeId, treeNodes, targetNode) {
            var params = treeNodes[0];
            params.oldParentOrgId = treeNodes[0].parentOrgId;
            params.parentOrgId = targetNode.orgId;

            organizationSave({params: JSON.stringify(params)}, function (res) {
                if (res.status === 'S') {
                    $("#deptModal").modal('hide');
                    $scope.orgTree.reload();
                }
            })
        }

   /*     $timeout(function(){
            $("#person-lov").modal("show");
        },1000)*/
    });
});