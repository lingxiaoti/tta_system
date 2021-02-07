/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('ttaSaStdTplDefHeaderCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow,validateForm,$http) {

        $scope.ttaSaStdFieldCfgLineData = {};
        $scope.params = {};
        $scope.rootParentId = 0;
        $scope.tableInfo = {};
        $scope.currentNodeData ={}; // 当前树选中节点
        $scope.headerInfo = null; // 当前行信息
        $scope.ttaSaStdFieldCfgLineTableParams = {};
        $scope.zTreeData = {};
        $scope.ttaSaTplTabLineHeaderTable = [];
        //NEW
        $scope.Ztree = function () {

            httpServer.post(URLAPI.findTtaSaStdTplDefHeaderTree, {
                    'params': JSON.stringify({})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.zTreeData.treeData = res.data;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.rowClick = function (row) {
            $scope.headerInfo.curentRow = row ;

           // $scope.dataTable = [];
           // $scope.dataTable.selectRow = $scope.headerInfo;
        };
        //NEW
        $scope.ttaSaTplTabLineSearch = function () {
            $scope.ttaSaTplTabLineHeaderTable = [] ;
            httpServer.post(URLAPI.findTtaSaTplTabLineList, {
                    'params': JSON.stringify({'saStdTplDefHeaderId':$scope.headerInfo.saStdTplDefHeaderId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.tableInfo.table= res.data;
                        $scope.tableInfo.col = null ;
                        $scope.tableInfo.row = null ;
                        if (res.data && res.data.length > 0) {
                            $scope.tableInfo.row = res.data.length;

                            if (res.data[0].length > 0) {
                                $scope.tableInfo.col = res.data[0].length;
                                //$scope.ttaSaTplTabLineHeaderTable.push("");
                                for (var i = 0; i<$scope.tableInfo.col; i++ ) {
                                    $scope.ttaSaTplTabLineHeaderTable.push(String.fromCharCode(65 + i));
                                }
                            }
                        }
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //NEW
        $scope.ttaSaStdFieldCfgLineSave = function (data) {

            httpServer.post(URLAPI.saveOrUpdateTtaSaStdFieldCfgLine, {
                    'params': JSON.stringify({'list':data,'id':$scope.headerInfo.saStdTplDefHeaderId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.ttaSaStdFieldCfgLineTable.search(1);
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );


        };

        //NEW
        $scope.btnSaveTable = function () {

            httpServer.post(URLAPI.saveOrUpdateAllTtaSaTplTabLine, {
                    'params': JSON.stringify({'list':$scope.tableInfo.table,'id':$scope.headerInfo.saStdTplDefHeaderId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.ttaSaTplTabLineSearch();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //NEW
        // 树点击节点回调
        $scope.treeClick = function (item) {
            $scope.treeCurrentItem = item; // 当前树选中节点
            $scope.headerInfo =  item;
            $scope.currentNodeData = item ;
            if($scope.headerInfo.parentId=='0'){
                $scope.headerInfo.parentTplCode = '－－－顶层条款编号－－－';
            }
            ttaSaStdFieldCfgLineTableSearch();
            $scope.ttaSaTplTabLineSearch();
        };

        function ttaSaStdFieldCfgLineTableSearch() {
            $scope.ttaSaStdFieldCfgLineTableParams = {
                saStdTplDefHeaderId: $scope.headerInfo.saStdTplDefHeaderId
            };
            $scope.ttaSaStdFieldCfgLineTable.search(1);

        }

        //NEW
        $scope.btnNew = function () {
            $scope.headerInfo = {
            };
            $scope.headerInfo = {
                "parentId":0,
                "parentTplCode":"－－－顶层条款编号－－－",
                "isEnable": "Y"
            };
        };

        //NEW
        $scope.btnSave = function (form) {

            if(!validateForm(form)){
                return;
            }

            if ($scope.headerInfo.saStdTplDefHeaderId === $scope.headerInfo.parentId) {
                SIEJS.alert('上级节点不能设置为自身节点', 'warning', '确定');
                return;
            }

            httpServer.post(URLAPI.saveOrUpdateTtaSaStdTplDefHeaderTree, {
                    'params': JSON.stringify($scope.headerInfo)
                },
                function (res) {
                    if (res.status == 'S') {
                        //$scope.headerInfo.saStdTplDefHeaderId =  res.data.saStdTplDefHeaderId;
                        //$scope.selectL();   FX:1
                        $scope.ttaSaStdTplDefHeaderTree.reload();
                        $scope.Ztree();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );

        };

        //NEW
        //下载附件事件
        $scope.downFileEvent = function (row,property,fileName,type,propertyId) {
            //console.log(msg);
            if (row[propertyId] == undefined || row[propertyId] == null){
                SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
                return;
            }
            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + row[propertyId];
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };

        //NEW
        //删除附件
        $scope.delUploadFile = function (row,property,fileName,type,propertyId) {
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var p = {
                    fileId: row[propertyId] //文件id
                };
                httpServer.post(URLAPI.ttaSideAgrtHeaderFileDelete, {
                    'params': JSON.stringify(p)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.headerInfo[property] = null ;
                        $scope.headerInfo[propertyId] = null ;
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert("删除失败! " + err.msg, "error", "确定");
                });

            });
        };

        //NEW
        //上传附件
        $scope.upload = function (form,property,fileName,type,propertyId) {

            if ($scope.headerInfo[property]) {
                SIEJS.alert("当前已经有文件请先删除，再上传", 'error', '确定');
                return;
            }
            var loading = $("#saafLoading");
            if (loading.length === 0) {
                var loading = $('<div id="saafLoading"><div  style="position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;"><img src="img/loading1.gif"></div></div>');
                loading.css({
                    position: "fixed",
                    top: 0,
                    width: "100%",
                    "z-index": 9000,
                    "height": "100%",
                    'display': 'none'
                });
                loading.prependTo(angular.element('body'));
            } else {
                loading.removeAttr('data-loading');
            }
            loading.show();
            var fd = new FormData();
            var file = document.getElementById(fileName).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                loading.hide();
                return;
            }
            var fileName = file.name;
            var sideAgrtHId = $scope.headerInfo.saStdTplDefHeaderId;
            if (sideAgrtHId == undefined || sideAgrtHId == null){
                SIEJS.alert("头信息为空,上传失败", 'error', '确定');
            }

            fd.append('sideAgrtHId', sideAgrtHId);
            fd.append('file', file);
            fd.append("fileName", fileName);
            fd.append("functionId", type);

            $http.post(URLAPI.ttaSideAgrtHeaderUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                if (response.status == 'S') {
                    loading.hide();
                    $scope.headerInfo[property] = response.data[0].sourceFileName ;
                    $scope.headerInfo[propertyId] = response.data[0].fileId ;
                    SIEJS.alert(response.msg, 'success', '确定');

                } else {
                    loading.hide();
                    SIEJS.alert(response.msg, 'error', '确定');
                }
            }).error(function(response) {
                loading.hide();
                SIEJS.alert("上传失败", 'error', '确定');
            });
        };

        //NEW
        $scope.btnAdd = function () {
            $('#selectFieldId').modal('toggle')
        };
        //NEW
        //点击确认按钮
        $scope.selectFieldReturn = function (key, value, currentList) {
            $scope.ttaSaStdFieldCfgLineSave(currentList);
        };
        $scope.btnDel = function () {
            var idsString = "";
            var children = [];
            if(!$scope.currentNodeData){
                SIEJS.alert("请先选中要删除的节点", "success", "确定");
                return;
            }
            if($scope.currentNodeData.children){
                children =  $scope.currentNodeData.children ;
            }
            if ($scope.headerInfo.saStdTplDefHeaderId ) {
                idsString = idsString + $scope.headerInfo.saStdTplDefHeaderId + ',';
                for (var i = 0; i < children.length; i++) {
                    idsString = idsString + children[i].saStdTplDefHeaderId + ',';
                }
                idsString = idsString.substr(0, idsString.length - 1);
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                    var saStdTplDefHeaderId = $scope.headerInfo.saStdTplDefHeaderId;
                    httpServer.post(URLAPI.deleteTtaSaStdTplDefHeaderTree, {
                        params: JSON.stringify({ids:idsString })
                    }, function (res) {
                        if ('S' == res.status) {
                            //$scope.search();
                            $scope.ttaSaStdTplDefHeaderTree.reload();
                            $scope.headerInfo = {
                            };
                            $scope.tableInfo = {};
                            $scope.Ztree();
                            ttaSaStdFieldCfgLineTableSearch();
                            SIEJS.alert('删除成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        SIEJS.alert('删除失败', 'error', '确定');
                    })

                })
            }



        };

        //NEW
        //删除信息
        $scope.btnDelField = function () {
            var selectRowList = $scope.ttaSaStdFieldCfgLineTable.selectRowList;
            if (!selectRowList.length) {
                SIEJS.alert("请选择要删除的数据!", "error", "确定");
                return;
            }
            var ids = "";
            for (var i = 0; i < selectRowList.length; i++) {
                ids = ids + selectRowList[i].saStdFieldCfgLineId + ",";
            }


            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                httpServer.post(URLAPI.deleteTtaSaStdFieldCfgLine,
                    {params: JSON.stringify({ids: ids})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.ttaSaStdFieldCfgLineTable.selectRowList = [];
                            $scope.ttaSaStdFieldCfgLineTable.search();
                            SIEJS.alert("处理成功", "success", "确定");
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#btnArrival").attr("disabled", true);
                    }
                );
            });
        };

        $scope.Ztree();
        $scope.btnNew();
        var tableInfoObject = {

        };
        $scope.ttaSaTplTab = function () {
            var row = $scope.tableInfo.row ;
            var col = $scope.tableInfo.col;
            var rowNo = 0;
            var colNo = 0;
            if (!$scope.tableInfo.table) {
                $scope.tableInfo.table = [] ;
            }
            if (row && col) {
                 rowNo = $scope.tableInfo.table.length;
                 colNo = rowNo == 0 ? 0:$scope.tableInfo.table[0].length ;
            }
            for (var i = (row > rowNo ? row:rowNo) -1; i >= 0; i--) {
                for (var j = (col > colNo ? col:colNo) - 1; j >= 0; j--) {
            //for (var i = 0; i<(row > rowNo ? row:rowNo); i++) {
              //  for (var j = 0; j<(col > colNo ? col:colNo); j++) {
                    if ( i < row && j < col ) {
                        if ($scope.tableInfo.table[i] && !$scope.tableInfo.table[i][j]) {
                            $scope.tableInfo.table[i][j] = {'xPoint':String.fromCharCode(65+j),'yPoint':i+ 1,'pointValue':''};
                        } else if (!$scope.tableInfo.table[i]) {
                            $scope.tableInfo.table[i] = [] ;
                            $scope.tableInfo.table[i][j] = {'xPoint':String.fromCharCode(65+j),'yPoint':i + 1,'pointValue':''};
                        }
                    } else {
                        if ($scope.tableInfo.table[i] && $scope.tableInfo.table[i][j]) {
                            $scope.tableInfo.table[i].length = j;
                            if (0 == $scope.tableInfo.table[i].length) {
                                $scope.tableInfo.table.length = i;
                            }
                        }
                    }
                }
            }
        }

    });

});
