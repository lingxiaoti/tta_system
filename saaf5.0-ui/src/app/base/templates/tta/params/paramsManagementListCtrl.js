/**
 * Created by dengdunxin on 2018/1/9.
 */
'use strict';
define(['app'], function (app) {
    app.controller('paramsManagementListCtrl', function ($timeout, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction, URLAPI, httpServer) {


        $scope.clickStatus = '';
        $scope.articleTable = {};
        $scope.current = {};


        $scope.params = {
            systemCode: window.appName,
            isAdmin: $rootScope.currentResp.isAdmin ? 'Y' : 'N'
        };
        $scope.legend = [
            {key:'paramKey',label:'参数名称'},
            {key:'paramContent',label:'参数内容'},
            {key:'remark',label:'描述信息'},

        ];
        // {key:'isSql',label:'是否sql',type:'lookCode',lookCode:'YES_OR_NO'},
        if ($rootScope.currentResp.isAdmin) {
            $scope.legend.push({
                key: 'systemCode',
                label: '系统编码',
                type: 'lookCode',
                lookCode: 'SYSTEM_CODE'
            });
        }else {
            $scope.legend.push({
                key: 'systemCode',
                label: '系统编码',
                type: 'lookCode',
                lookCode: 'SYSTEM_CODE',
                disabled:true
            });
        }

        /*if ($rootScope.currentResp.isAdmin) {
         $scope.legend.push({
         key: 'systemCode',
         label: '系统编码',
         type: 'lookCode',
         lookCode: 'SYSTEM_CODE',
         disabled:true
         });
         }*/

        //显示内容框的设置
        // $scope._simpleConfigOnlyRead = {
        //     //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  具体按钮请参考 ueditor.config.js
        //     toolbars: '',
        //     //focus时自动清空初始化时的内容
        //     autoClearinitialContent: true,
        //     //关闭字数统计
        //     wordCount: false,
        //     //关闭elementPath
        //     elementPathEnabled: false,
        //     initialFrameHeight: 380,
        //     initialFrameWidth: '100%',
        //     autoHeightEnabled: false,
        //     readonly: true
        // }


        //点击新增
        $scope.btnNew = function () {
            iframeTabAction('新增配置', '/paramsManageDetail/')
        }
        //修改
        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            iframeTabAction('参数详情：'+row.paramKey, '/paramsManageDetail/' + row.paramId)

        }

        //查看
        $scope.btnDetail = function () {
            var row = $scope.dataTable.selectRow;
            iframeTabAction('参数详情：'+row.paramKey, '/paramsManageDetail/' + row.paramId + "?onlyShow=1")
        }

        //删除
        $scope.btnDel = function () {
            SIEJS.confirm('删除名称为：'+ $scope.dataTable.selectRow.paramKey, '是否确定删除？', '确定', function () {
                var paramId = $scope.dataTable.selectRow.paramId;
                httpServer.post(URLAPI.paramsDel, {params: JSON.stringify({id: paramId})},function (res) {
                    if (res.status == "S") {
                        $scope.dataTable.search();
                        SIEJS.alert(res.msg, "success", '确定')
                    } else {
                        SIEJS.alert(res.msg, 'error', "确定");

                    }
                }, function (error) {
                    console.error(error);
                    SIEJS.alert('删除失败', 'error', '确定');
                })
            });
        }


        // 显示文章内容
        $scope.showContent=function(msg){
            $("#articleContent").modal('show');

            $scope.articleContent=msg;
        }

    });
});
