/**
 * Created by houxingzhang on 2016-09-13.
 */
'use strict';
define(["app", 'ztree'], function (app) {
    app.controller('menuFuncPrivilegeCtrl', function ($scope, $rootScope, $timeout, httpServer, URLAPI, SIEJS, menuListByButton) {

        $scope.params = {
            systemCode: appName
        };

        //分配菜单
        function setTree(val) {

            var ids = val;
            var json = $scope.zTreeData.Json;
            if (ids != undefined && json != null) {
                var ztree = $.fn.zTree.getZTreeObj('existing');
                var setting = ztree.setting;
                var nodes = [];
                for (var i = 0; i < json.length; i++) {
                    if (ids.indexOf(json[i].menuId) > -1)
                        nodes.push(json[i])
                }
                var treeObj = $.fn.zTree.init($("#existing"), setting, nodes);
                treeObj.expandAll(true);
            }
        }

        //监听　ztree数据
        $scope.$watch('zTreeData.getNodesIdKey', function (val) {
            //console.log($scope.zTreeData.getNodesIdKey);
            //console.log($scope.zTreeData.getMenuAndBtn);
            setTree(val);
        }, true);


        $scope.currentRoleId = 0;
        //获取职责数据  单击单元格
        $scope.getRespData = function () {

            $timeout(function () {
                    var row = $scope.dataTable.selectRow;

                    if ($scope.currentRoleId === row.roleId) return; //防止重复点击单元格
                    $scope.currentRoleId = row.roleId;


                    var p1 = {
                        'params': JSON.stringify({
                            systemCode: row.systemCode
                        })
                    };
                    menuListByButton(p1, function (res) {

                        $scope.zTreeData.Json=res.data;


                        var p2 = {
                            'params': JSON.stringify({
                                roleId: row.roleId
                            })
                        };

                        menuListByButton(p2, function (res) {

                            setSelected(res.data);
                        })
                    })
                    /*  httpServer.post(
                     URLAPI.menuListByButton,
                     {
                     'params': JSON.stringify({
                     roleId: row.roleId
                     })
                     },
                     function (res) {
                     if (res.status.toLowerCase() == 'e') {
                     SIEJS.alert(res.msg, "error");
                     }else{

                     setSelected(res.data);
                     }


                     },
                     function (err) {
                     SIEJS.alert('数据获取失败！', "error");
                     }
                     );*/

                },
                200
            )


        }
        function setSelected(_existing) {

            var _json = $scope.zTreeData.Json;
            if (!_json) return;

            for (var _n = 0; _n < _json.length; _n++) {
                var node = _json[_n];
                node.checked = false;
                for (var _i = 0; _i < _existing.length; _i++) {
                    var checked = _existing[_i];
                    if (node.menuId === checked.menuId) {
                        node.checked = true;
                    }
                }
            }
            $scope.zTreeData.updataId = $scope.currentRoleId;//更新的标识
        }

        //保存当前分配的菜单与权限按钮
        $scope.btnSave = function () {


            httpServer.post(
                URLAPI.menuByRoleSave,

                {
                    'params': JSON.stringify({
                        'roleId': $scope.currentRoleId,
                        'menuData': $scope.zTreeData.getMenuAndBtn
                    })
                },
                function (res) {
                    if (res.status.toLowerCase() == 'e') {
                        SIEJS.alert(res.msg, "error");
                    } else {
                        SIEJS.alert("保存成功", 'success', '确定')
                    }
                },
                function (err) {
                    SIEJS.alert('数据获取失败！', "error");
                }
            );
        }

        //查询
        $scope.search=function(){
            $scope.dataTable.selectRow=null;
            $scope.dataTable.search(1);

        }
    });
});
