/**
 * Created by dengdunxin on 2018/2/3.
 */
'use strict';
define(["app", "ng.ueditor", "angularFileUpload"], function (app) {

    app.useModule('ng.ueditor');//按需加载编辑器
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('articleManagementDetailCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', '$state', 'articleSave', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, $state, articleSave) {

        $scope.stateParams = {};
        $scope.current = {};
        $scope.stateParams.id = $stateParams.id;
        $scope.userRespList=JSON.parse(localStorage[window.appName + '_successLoginInfo']).userRespList;
        $scope.buData=[]
        angular.forEach($scope.userRespList,function(data,index){
            if(data.systemCode==window.appName){
                $scope.buData=data.orgBeans;
            }
        })

        $scope.isAdmin = JSON.parse(localStorage[window.appName + '_currentResp']).isAdmin;
        $scope.search = function () {

            httpServer.post(URLAPI.articleFindById,
                {
                    params: JSON.stringify({id: $scope.stateParams.id})
                },
                function (res) {
                    $scope.current = res.data[0];
                    $scope.current.orgId=$scope.current.orgId.toString()=='0'?'':$scope.current.orgId.toString();
                    //$scope.current.articleContent=decodeURI($scope.current.articleContent)

                })
        }

        if ($scope.stateParams.id == null || $scope.stateParams.id == "" || $scope.stateParams.id == undefined) {
            //新增
            $scope.current = {systemCode:window.appName};
        }
        else {
            //修改
            $scope.search();
        }

        $scope.btnSave = function () {
            if(!$scope.current.articleContent||$scope.current.articleContent==''){
                JS.alert('请输入文章详情', 'error', "确定");
                return
            }



            //$scope.current.articleContent = encodeURI($scope.current.articleContent);
            //$scope.current.systemCode = window.appName
            articleSave({params: JSON.stringify($scope.current)}, function (res) {
                if (res.status == "S") {
                    $scope.current = res.data
                    $scope.current.orgId=$scope.current.orgId.toString()=='0'?'':$scope.current.orgId.toString();
                } else {
                    JS.alert(res.msg, 'error', "确定");

                }
            })

        }


        //编辑，新增框的设置
        $scope._simpleConfig = {


            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  具体按钮请参考 ueditor.config.js
            toolbars: [[
                'fullscreen', 'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'directionalityltr', 'directionalityrtl', 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
                'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                'print', 'preview', 'searchreplace', 'drafts', 'help'
            ]],
            //focus时自动清空初始化时的内容
            autoClearinitialContent: true,
            //关闭字数统计
            wordCount: false,
            //关闭elementPath
            elementPathEnabled: false,
            //设置编辑器高度
            initialFrameHeight: 300,
            initialFrameWidth: '98%',
            zIndex: 1,
            //当内容超出高度时，出现滚动条
            autoHeightEnabled: false,
            initialStyle: 'p{line-height:1em; font-size: 12px; }',
        }
        $scope.topFlagChang=function(value){
            if(value=='N'){
                if($scope.current.topToDate){
                    $scope.current.topToDate=''
                }
            }
        }
        //图片添加执行方法
        // $scope.imgAddAction = function (rows, targetType, imgChannel, returnMessage) {
        //     var imgMessage = $.extend({}, returnMessage, {
        //         "targetId": $scope.current.articleId,
        //         "targetType": targetType,
        //         "disabled": "N",
        //         'imgChannel': imgChannel
        //     })
        //     //rows传过来的是undefined
        //     $scope.current.articleImage = [imgMessage];
        //     console.log($scope.current.articleImage);
        // }
        // //图片删除执行方法
        // $scope.imGdeleteAction = function (rows, row) {
        //     var index = $.inArray(row, rows)
        //
        //     if (!$scope.current.hasOwnProperty('deletedImages')) {
        //         $scope.current.deletedImages = [];
        //     }
        //     if (rows[index].hasOwnProperty('id')) {
        //         $scope.current.deletedImages.push({'id': rows[index].id});
        //     }
        //
        //     rows.splice(index, 1);
        //
        // }

    }]);
});
