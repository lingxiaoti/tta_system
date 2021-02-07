/**
 * Created by houxingzhang on 2016-09-03.
 */
'use strict';
define(["app"], function (app) {

    //app.useModule('ng.ueditor');//按需加载编辑器

    //app.useModule('ngDialog');//按需加载弹出层模块

    app.controller('homeCtrl', function ($rootScope, $scope, httpServer, SIEJS, URLAPI, $state, Fullscreen, $http, $location) {
        var module = angular.module('SAAF.Directives', 'SIE.Services', []);
        $scope.executeQueryParent = function (queryParam, requestURL) {
            httpServer.getData(requestURL, "post", {
                    'params': JSON.stringify(queryParam),
                    'pageIndex': '1',
                    'pageRows': '10'
                },
                function (resultListData) { 
                    $scope.resultListData = resultListData.data;
                 },
                function (err) {
                 });
        }




        $scope.executeQueryGetList = function (requestURL) {
            httpServer.getData(requestURL, 'post', {
                'params': JSON.stringify()
            }, function (res) {
                if (res.data instanceof Array) {
                    $scope.List = res.data; 
                } else {
                    console.log('requestURL=' + requestURL + ',查询失败！！！');
                }
            }, function (err) {
                console.log('requestURL=' + requestURL + ',查询异常！！！');
            })
        }
        /**
         *跳转详情页，查询头信息
         * @param queryParam
         * @param requestURL
         */
        $scope.executeQueryParentById = function (queryParam, requestURL) {
            httpServer.getData(requestURL, "post", {
                    'params': JSON.stringify(queryParam),
                    'pageIndex': '1',
                    'pageRows': '1'
                },
                function (resultListData) {
                    $scope.headerData = resultListData.data[0];
                },
                function (err) {
                    SIEJS.alert('查询头信息失败!请重试！', "error", "确定");
                });
        }

        $scope.resetQueryParamParent = function (requestURL) {
            $scope.queryParam = "";
            $scope.executeQueryParent("{}", requestURL);
        }
        $scope.createNewRowParent = function (createParam, targetPageAddress) {
            createParam.flag = 'createFlag';
            $state.go(targetPageAddress, {"queryParam": JSON.stringify(createParam)}); 
        }

        $scope.updateCurrentRowParent = function (upateParam, targetPageAddress) {
            upateParam.flag = 'updateFlag';
            $state.go(targetPageAddress, {"queryParam": JSON.stringify(upateParam)}); 
         }

        $scope.updateCurrentRowParent4Modal = function (upateParam, updateModel) {
            if (!upateParam) {
                SIEJS.alert('请您先选中要修改的行!', "error", "确定");
                return;
            }
            if (!updateModel) {
                SIEJS.alert('程序猿，请传入弹出窗!', "error", "确定");
                return;
            }
            updateModel.modal('toggle');
        }

        $scope.highQueryParent = function () {
        }

        $scope.exportDataParent = function (upateParam, targetPageAddress) {
        }



        /**
         *
         * 保存，updated by luofenwu
         * @param saveParam 保存的jsonObject
         * @param requestURL 保存接口URL
         * @param executeMethod 非必须：需要执行的方法名，例如查询，例如赋值，传入function对象，如$scope.search
         * @param saveBtn 非必须：保存按钮
         * @param goExecute  非必须：是否执行方法,不传或者传0为一定查询，传1为保存成功时执行，传-1或其他为不执行
         */
        $scope.saveObjectParent2 = function (saveParam, requestURL, executeMethod, saveBtn, goExecute) {
            if (saveBtn) {
                saveBtn.attr("disabled");
            }
            httpServer.getData(requestURL, "post", {'params': JSON.stringify(saveParam)}, function (res) {//AUX
                $scope.returnResultData = res;
                if (res.status == 'S') { 
                    SIEJS.alert((res.msg==null||res.msg=="")?"保存成功":res.msg, "success", "确定");
                    if (goExecute == 1 && executeMethod && typeof executeMethod == 'function') {
                        executeMethod();
                    }
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
                if (saveBtn) {//不管成功还是失败，让保存按钮恢复可点击
                    saveBtn.removeAttr("disabled");
                }
                if ((null == goExecute || goExecute == 0) && executeMethod && typeof executeMethod == 'function') {
                    executeMethod();
                }
            }, function (err) {
                SIEJS.alert("保存失败", res.msg, "error");
            });
        }

        /**
         *
         * 保存，updated by luofenwu
         * @param saveParam 保存的jsonObject
         * @param requestURL 保存接口URL
         * @param executeMethod 非必须：需要执行的方法名，传入function对象，如$scope.search
         * @param saveBtn 非必须：保存按钮
         * @param toggleModal  非必须：弹出窗（如果是弹出窗的保存）
         * @param goSearch  非必须：是否启用查询方法,不传或者传0为一定查询，传1为保存成功时查询，传-1或其他为不查询
         */
        $scope.saveObjectParent = function (saveParam, requestURL, executeMethod, saveBtn, toggleModal, goSearch) {
            if (saveBtn) {
                saveBtn.attr("disabled");
            }
            httpServer.getData(requestURL, "post", {'params': JSON.stringify(saveParam)}, function (res) {//AUX
                $scope.returnResultData = res;
                if (res.status == 'S') {
                    SIEJS.alert((res.msg==null||res.msg=="")?"保存成功":res.msg, "success", "确定");
                    if (toggleModal) {
                        toggleModal.modal('toggle');
                    }
                    if (goSearch == 1 && executeMethod && typeof executeMethod == 'function') {
                        executeMethod();
                    }
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
                if (saveBtn) {
                    saveBtn.removeAttr("disabled");
                }
                if ((null == goSearch || goSearch == 0) && executeMethod && typeof executeMethod == 'function') {
                    executeMethod();
                }
            }, function (err) {
                SIEJS.alert("保存失败", res.msg, "error");
            });
        }

        $scope.deleteProjectInfo = function (param) {
            if (!$scope.resultData[param].projectId) {
            } else {
                httpServer.getData(URLAPI.deleteProjectInfo, "post", {"projectId": $scope.resultData[param].projectId}, function (resultData) {
                    },
                    function (data) {
                    });
            }
            $scope.queryProjectInfo(queryParam);
        }
        $scope.editProjectInfo = function (param) {
            $scope.saveParam = $scope.resultData[param];
            $scope.createNewRow();
        }
        $scope.createNewRow = function () {
            $scope.mask = true;
            $scope.mask1 = true;
        }
        $scope.hideMask = function () {
            $scope.mask1 = false;
            $timeout(function () {
                $scope.mask = false;
            }, 500)
        };

        $scope.openA = function () {
            //ngDialog.open({
            //    template: '<p>my template</p><div>  <button ng-click="closeThisDialog()">Cancel</button></div>',
            //    plain: true,
            //    closeByDocument :false//允许点击文件关闭对话框
            //});
        };

        $scope._simpleConfig = {
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  具体按钮请参考 ueditor.config.js
            toolbars: [['source', '|', 'bold', 'italic', 'underline', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']],
            //focus时自动清空初始化时的内容
            autoClearinitialContent: true,
            //关闭字数统计
            wordCount: false,
            //关闭elementPath
            elementPathEnabled: false,
            initialFrameHeight: 200,
            initialFrameWidth:'100%'
        }


    });
});
