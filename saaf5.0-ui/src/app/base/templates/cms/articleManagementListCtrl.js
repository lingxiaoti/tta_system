/**
 * Created by dengdunxin on 2018/1/9.
 */
'use strict';
define(['app'], function (app) {
    app.controller('articleManagementListCtrl', function ($timeout, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction, articleDel) {


        $scope.clickStatus = '';
        $scope.articleTable = {};
        $scope.current = {};


        $scope.params = {
            systemCode: window.appName,
            isAdmin: $rootScope.currentResp.isAdmin ? 'Y' : 'N'
        };
        $scope.legend = [
            {key:'articleType',label:'文章类型',type:'lookCode',lookCode:'ARTICLE_TYPE'},
            {key:'articleTitle',label:'文章标题'}
        ];
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
        $scope.save = function () {
            if(!$scope.current.articleContent||$scope.current.articleContent==''){
                JS.alert('请输入文章详情', 'error', "确定");
                return
            }
            $scope.current.systemCode = window.appName
            articleSave({params: JSON.stringify($scope.current)}, function (res) {
                if (res.status == "S") {
                    $scope.current = res.data
                } else {
                    JS.alert(res.msg, 'error', "确定");

                }
            })

        }

        //点击新增
        $scope.btnNew = function () {
            iframeTabAction('文章详情', '/articleManagementDetail/')
        }
        //修改
        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            iframeTabAction('文章详情：'+row.articleTitle, '/articleManagementDetail/' + row.articleId)

        }

        //删除
        $scope.btnDel = function () {
            var id = $scope.dataTable.selectRow.articleId;
            articleDel({
                params: JSON.stringify({
                    articleId: id
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.dataTable.search();
                    SIEJS.alert(res.msg, "success", '确定')
                }
            })
        }

        // 显示文章内容
        $scope.showContent=function(msg){
            $("#articleContent").modal('show');

            $scope.articleContent=msg;
        }

    });
});
