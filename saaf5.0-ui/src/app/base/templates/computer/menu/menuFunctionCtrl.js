/**
 * Created by houxingzhang on 2016-09-13.
 */
'use strict';
define(["app", 'ztree', 'pinyin'], function (app, ztree, pinyin) {
    //app.useModule('jcs-autoValidate');//按需加载编辑器    'autoValidate'
    app.controller('menuFunctionCtrl', function ($rootScope, $scope,arrayFindObj, SIEJS, menuList, httpServer, URLAPI,
                                                 $filter, $timeout, $compile, buttonList,validateForm) {

        $scope.treeParams = {
            systemCode: appName
        };
        $scope.AddData = {
            systemCode: appName
        };
        $scope.currentData = {
            systemCode: appName
        };
        $scope.showinfo = false;

        $scope.styles = {
            'padding-bottom': $rootScope.currentResp.isAdmin ? 60 : 0
        }

        $scope.fromTypes = {};

        //监听　ztree数据
        $scope.w1 = $scope.$watch('zTreeData.selectNode', function (newVal, oldVal) {

            if (newVal === oldVal || newVal == null) return;
            $scope.treeNodeClick(newVal, $scope.zTreeData.parentNode);
            $scope.systemCodeDisabled=true;

        }, true);

        $scope.treeNodeClick = function (newVal, parentNode) {
            $scope.showinfo = true;
            $scope.currentData = angular.copy(newVal);

            if (!parentNode) {
                // 当前node 的parentId被清除，重新获取
                var _node = arrayFindObj($scope.zTreeData.Json,newVal.menuId,'menuId');
                //获取到父级node
                parentNode = arrayFindObj($scope.zTreeData.Json,_node.menuParentId,'menuId');
            }
            parentNode = parentNode || {};
            $scope.currentData.menuParentName = parentNode.menuName || '－－－顶层菜单－－－';// 当前选择顶父级名
            $scope.currentDataMaster = angular.copy($scope.currentData);
            httpServer.post(URLAPI.resourceByMenu, {params: JSON.stringify({menuId: newVal.menuId})},
                function (res) {
                    $scope.currentData.btndata = res.data;
                })
        }

        // 首写拼音
        $scope.currentPinyin = function () {
            $scope.currentData.menuCode = pinyin.shouxiePinyin($scope.currentData.menuName);
        };
        $scope.addPinyin = function () {
            $scope.AddData.menuCode = pinyin.shouxiePinyin($scope.AddData.menuName);
        };
        //保存菜单 \修改
        $scope.btnSave = function (f) {


            if ($scope.currentData == undefined) {
                SIEJS.alert('请选择左边的菜单', 'info', '确定');
                return;
            }



            if (!validateForm(f)) {

               return;
            }
            $scope.currentData.menuType = $scope.currentData.htmlUrl !== '' ? '20' : '10';
            var treeObj = $.fn.zTree.getZTreeObj("menuTree");
            var parentNode = treeObj.getNodesByParam("menuId", $scope.currentData.menuParentId, null);
            if (parentNode.length > 0 && parentNode[0].menuType == '20') {
                $scope.currentData.updateParent = true;
                $scope.currentData.parentIsMenu = 'Y';
            } else {
                $scope.currentData.updateParent = false;
            }
            if ($scope.currentDataMaster.menuParentId != $scope.currentData.menuParentId) {
                //如果两次的父节点不一致，记录旧的父节点
                $scope.currentData.oldMenuParentId = $scope.currentDataMaster.menuParentId;
                $scope.currentData.updateOldParent = true;
            }


            if ($scope.currentData.menuId === $scope.currentData.menuParentId) {
                SIEJS.alert('父级菜单不能设置为自身节点', 'warning', '确定');
                return;
            }


            httpServer.post(
                URLAPI.menuSave,
                {
                    'params': JSON.stringify($scope.currentData)
                },
                function (res) {
                    if (res.status.toLowerCase() == 'e') {
                        SIEJS.alert(res.msg, "error");
                    } else {
                        SIEJS.alert("保存成功", "success", "确定");
                        $scope.zTreeData.selectNode = null;

                        overloadingJson();
                        $scope.currentDataMaster = angular.copy($scope.currentData);//重载数据

                    }
                },
                function (err) {
                    SIEJS.alert('查询失败！', "error", "确定");
                }
            );

            $("#EditModal").modal('hide');
        };
        // 获取基础按钮 列表
        $scope.getButtonList = function () {

            buttonList({}, function (res) {
                $scope.buttonList = res.data;
            })

        };
        $scope.getButtonList();

        ///
        function overloadingJson(systemCode, fromType) {
            $scope.showTree = false;
            $scope.searchTreeParam = null;
            if (systemCode) {
                $scope.treeParams.systemCode = systemCode;
            }
            if (fromType || fromType=='') {
                $scope.treeParams.fromType = fromType;
            }
            menuList({params: JSON.stringify($scope.treeParams)}, function (res) {
                $scope.zTreeData.Json = res.data;

                $scope.zTreeData.updataId = (new Date()).valueOf();
                $scope.showinfo = false;


            })


        }

        //新增菜单
        $scope.btnNew = function () {
            if (!$scope.currentData) {
                $scope.currentData = {
                    menuId: 0,
                    systemCode: appName
                }
            }
            $scope.AddData = {
                menuCode: '',
                menuName: '',
                menuDesc: '',
                htmlUrl: '',
                imageLink: '',
                imageLinkApp: '',
                menuShortName: '',
                fromType: 'pc',
                imageColor: '',
                "menuParentId": $scope.currentData.menuId,
                "enabled": "Y",
                "menuType": "10",
                systemCode: $scope.treeParams.systemCode,
                "startDateActive": $filter('date')(new Date(), 'yyyy-MM-dd')
            };

            $("#AddModal").modal('show');
        };
        ////////　新增菜单
        $scope.addMenu = function () {

           /* if (invalid) {
                SIEJS.alert('输入校验未通过','error');
                return;
            }*/
            $scope.AddData.menuType = $scope.AddData.htmlUrl !== '' ? '20' : '10';
            $scope.AddData.menuId = '';
            //$scope.AddData.menuParentId = $scope.currentData.menuId;

            var treeObj = $.fn.zTree.getZTreeObj("menuTree");
            var parentNode = treeObj.getNodesByParam("menuId", $scope.AddData.menuParentId, null);
            if (parentNode.length > 0 && parentNode[0].menuType == '20') {
                $scope.AddData.updateParent = true;
                $scope.AddData.parentIsMenu = 'Y';
            } else {
                $scope.AddData.updateParent = false;
            }

            httpServer.post(
                URLAPI.menuSave,
                {
                    'params': JSON.stringify($scope.AddData)
                },
                function (res) {
                    if (res.status.toLowerCase() == 'e') {
                        SIEJS.alert(res.msg, "error");
                    } else {
                        SIEJS.alert("保存成功", "success");
                        overloadingJson();
                        $scope.AddData = null;
                        //$state.reload();
                    }

                    $("#AddModal").modal('hide');
                },
                function (err) {
                    SIEJS.alert('新增失败！', "error", "确定");
                }
            );


        };

        $scope.btnDel = function () {

            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.menuDelete,

                    {
                        'params': JSON.stringify({
                            "id": $scope.currentData.menuId
                        })
                    },
                    function (res) {
                        if (res.status.toLowerCase() == 'e') {
                            SIEJS.alert(res.msg, "error");
                        } else {
                            SIEJS.alert(res.msg, "success");
                            overloadingJson();

                            $scope.showinfo = false;

                        }
                    }
                )
            });
        }

        //新增功能（按钮）
        $scope.addFunc = function () {
            //document.funcMenuForm.reset();//重置表单
            $scope.funcBtnActin = 'add';
            $scope.FuncData = {};
            $scope.FuncData.resourceType = '10';
            $("#addFuncModal").modal('show');
        };


        // 选择按钮
        $scope.selectedBtn = function () {
            var btn = $scope.FuncData.button;

            $scope.FuncData.buttonUrl = btn.buttonUrl;
            $scope.FuncData.resourceName = btn.bbdName;
            $scope.FuncData.resourceCode =btn.bbdCode;
            $scope.FuncData.resIcon = btn.resIcon;
            $scope.FuncData.menuId = $scope.currentData.menuId;
            $scope.FuncData.resourceType = '10';
        }
        ///添加功能按钮
        $scope.addFuncMenu = function ($valid) {

            if (!$valid) {
                SIEJS.alert('输入校验未通过', 'warning', '确定');
                return;
            }
            /* if ($scope.funcBtnActin == 'add') {
             $scope.currentData.btndata.push({
             'button': $scope.FuncData.button,
             'buttonUrl': $scope.FuncData.buttonUrl,
             'resourceDesc': $scope.FuncData.resourceDesc,
             'resourceName': $scope.FuncData.resourceName,
             'resourceCode': $scope.FuncData.resourceCode,
             'menuId': $scope.currentData.menuId,
             'resourceType': $scope.FuncData.resourceType
             });

             } else {
             var index = $scope.currentData.btndata.indexOf($scope.FuncData);
             $scope.currentData.btndata[index] = $scope.FuncData;

             }*/

            var p = {
                //'button': $scope.FuncData.button,
                'buttonUrl': $scope.FuncData.buttonUrl,
                'resourceDesc': $scope.FuncData.resourceDesc,
                'resourceName': $scope.FuncData.resourceName,
                'resourceCode': $scope.FuncData.resourceCode,
                'menuId': $scope.currentData.menuId,
                'resourceType': $scope.FuncData.resourceType,
                resIcon: $scope.FuncData.resIcon
            };

            //$scope.FuncData.menuId=$scope.currentData.menuId;
            //delete p.button;
            console.log(p)

            // 保存到数据库
            httpServer.save(URLAPI.resourceSave, {params: JSON.stringify(p)}, function (res) {
                if  (res.status==='S'){
                    $scope.currentData.btndata.push(res.data[0]);
                    $scope.FuncData = null;
                    $("#addFuncModal").modal('hide');
                }

            })


        }

        ///删除按钮
        $scope.removeFunc = function (item) {
            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.resourceDelete,
                    {
                        'params': JSON.stringify({
                            "id": item.resourceId
                        })
                    },
                    function (res) {
                        if (res.status.toLowerCase() == 'e') {
                            SIEJS.alert(res.msg, "error");
                        } else {
                            SIEJS.alert(res.msg, "success");

                            var index = $scope.currentData.btndata.indexOf(item);
                            $scope.currentData.btndata.splice(index, 1);

                            /*angular.forEach($scope.currentData.btndata, function (ele, index) {
                             if (ele.funButtonId == item.funButtonId && ele.buttonUrl == item.buttonUrl && ele.resourceName == item.resourceName)  //请以id为准
                             $scope.currentData.btndata.splice(index, 1);//删除数组
                             })*/
                        }
                    }
                )
            });
        }

        $scope.editFunc = function (item, $event) {
            //debugger;
            if ($event.target == $event.currentTarget) {

                $scope.FuncData = item;


                for (var n in $scope.buttonList) {
                    var btn = $scope.buttonList[n];
                    if (btn.bbdName === item.resourceName && item.resourceCode === btn.bbdCode) {
                        $scope.FuncData.button = btn;
                        break;
                    }
                }

                $("#addFuncModal").modal('show');
            }


        }

        $scope.$on("$destroy", function () {
            $scope.w1()
        })

        $scope.changeSys = function (val) {
            overloadingJson(val);
        }


        $scope.treeSearch = function () {

            if (!$scope.searchTreeParam) {
                $scope.showTree = false;
                return;
            }
            $scope.showTree = true;
            $scope.newTreeArray = [];
            for (var n in $scope.zTreeData.Json) {
                var item = $scope.zTreeData.Json[n];
                if (item.menuName.indexOf($scope.searchTreeParam) > -1) {
                    $scope.newTreeArray.push(item);
                }
            }

        }

        $scope.changeFormType = function () {
            var fromTypes = '';
            for (var n in $scope.fromTypes) {
                fromTypes += $scope.fromTypes[n] ? ',' + n : '';
            }
            overloadingJson(null, fromTypes.substr(1));


        }

    });
});
