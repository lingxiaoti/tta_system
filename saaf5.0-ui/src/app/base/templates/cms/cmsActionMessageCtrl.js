/**
 * Created by dengdunxin on 2018/2/5.
 */
/**
 *  内容管理模块   消息模板
 */
'use strict';
define(["app","ng.ueditor"], function (app) {
    app.useModule('ng.ueditor');//按需加载编辑器
    app.controller('cmsActionMessageCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI','$rootScope', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI,$rootScope) {
        $scope.current = {};
        $scope.params = {};
        $scope.cmsActionMessageTable = {};
        $scope.dataTable = {};
        $scope.flag = true;
        $scope.count = [];
        $scope.arr = [];
        $scope.tempLine = [];

        // setTimeout(function () {
        //     console.log($rootScope.saafPageButtons.save+"消息模板");
        // },5000)
        $scope._simpleConfig = {
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  具体按钮请参考 ueditor.config.js
            toolbars: [['bold', 'italic', 'underline', 'forecolor', 'backcolor', 'insertorderedlist',
                'insertunorderedlist', 'selectall', 'cleardoc', '|', 'customstyle', 'paragraph', 'fontfamily', 'fontsize']],
            //focus时自动清空初始化时的内容
            autoClearinitialContent: true,
            //关闭字数统计
            wordCount: false,
            //关闭elementPath
            elementPathEnabled: false,
            initialFrameHeight: '150px',
            initialFrameWidth: '100%'
        }

        //获取当前日期
        $scope.getMyDate = function () {
            var date = new Date()
            var sysDate = date.getFullYear() + "-"
            if ((date.getMonth() + 1) < 10) {
                sysDate = sysDate + "0" + (date.getMonth() + 1) + "-"
            } else {
                sysDate = sysDate + (date.getMonth() + 1) + "-"
            }
            if (date.getDate() < 10) {
                sysDate = sysDate + "0" + date.getDate();
            } else {
                sysDate = sysDate + date.getDate();
            }
            return sysDate;
        }
        //日期加减
        $scope.transDate = function (date, days, operator) {
            date = date.replace(/-/g, "/"); //更改日期格式
            var nd = new Date(date);
            nd = nd.valueOf();
            if (operator == "+") {
                nd = nd + days * 24 * 60 * 60 * 1000;
            } else if (operator == "-") {
                nd = nd - days * 24 * 60 * 60 * 1000;
            } else {
                return false;
            }
            nd = new Date(nd);

            var y = nd.getFullYear();
            var m = nd.getMonth() + 1;
            var d = nd.getDate();
            if (m <= 9) m = "0" + m;
            if (d <= 9) d = "0" + d;
            var cdate = y + "-" + m + "-" + d;
            return cdate;
        }
        //生效失效日期校验
        $scope.currentstartDateActive = $scope.current.startDateActive;
        $('#currentData_endDateActive')
            .on('click', function () {
                $scope.currentstartDateActive = $scope.current.startDateActive;
                $scope.currentstartDateActive = $scope.transDate($scope.currentstartDateActive, 1, '+');
                $('#currentData_endDateActive').datetimepicker('setStartDate', $scope.currentstartDateActive);
            });
        $scope.varcurrentstartDateActive = $scope.params.varStartDateActive;
        $('#varEndDateActive')
            .on('click', function () {
                $scope.varcurrentstartDateActive = $scope.params.varStartDateActive;
                $scope.varcurrentstartDateActive = $scope.transDate($scope.varcurrentstartDateActive, 1, '+');
                $('#varEndDateActive').datetimepicker('setStartDate', $scope.varcurrentstartDateActive);
            });
        //编辑電子郵件
        $scope.linkToLine_email = function () {
            $scope.current = $scope.cmsActionMessageTable.data[$scope.cmsActionMessageTable.selectRow];
            if($scope.current.emailTemplateId == undefined){
                $scope.dataTable = {};
                $scope.dataTable.messageTemplateType = 'EMAIL';
                $scope.flag = false;
                $('#emailModal').modal('toggle');
                return;
            }
            httpServer.getData(
                URLAPI.MsgTemplateByIdFind,
                'POST', {
                    'params': JSON.stringify({
                            //"varMessageTemplateId": $scope.current.emailTemplateId == undefined ?  0 : $scope.current.emailTemplateId
                            "varMessageTemplateId": $scope.current.emailTemplateId
                        }
                    )
                }, function (res) {
                    $scope.flag = true;
                    $('#emailModal').modal('toggle');
                    $scope.dataTable = res
                }, function (err) {
                    $scope.cmsActionMessageTable.getData();
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!', '', "error");
                });
        }
        //编辑站內信息
        $scope.linkToLine_web = function () {
            $scope.current = $scope.cmsActionMessageTable.data[$scope.cmsActionMessageTable.selectRow];
            if($scope.current.sitemsgTemplateId == undefined){
                $scope.dataTable = {};
                $scope.dataTable.messageTemplateType = 'SITEMSG';
                $scope.flag = false;
                $('#emailModal').modal('toggle');
                return;
            }
            httpServer.getData(
                URLAPI.MsgTemplateByIdFind,
                'POST', {
                    'params': JSON.stringify({
                            "varMessageTemplateId": $scope.current.sitemsgTemplateId == undefined ?  0 : $scope.current.sitemsgTemplateId
                        }
                    )
                }, function (res) {
                    $('#webModal').modal('toggle');
                    $scope.flag = true;
                    $scope.dataTable = res
                }, function (err) {
                    $scope.cmsActionMessageTable.getData();
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!', '', "error");
                });
        }
        //编辑手機短信
        $scope.linkToLine_phone = function () {
            $scope.current = $scope.cmsActionMessageTable.data[$scope.cmsActionMessageTable.selectRow];
            if($scope.current.phonemsgTemplateId == undefined){
                $scope.dataTable = {};
                $scope.dataTable.messageTemplateType = 'PHONEMSG';
                $scope.flag = false;
                $('#emailModal').modal('toggle');
                return;
            }
            httpServer.getData(
                URLAPI.MsgTemplateByIdFind,
                'POST', {
                    'params': JSON.stringify({
                            "varMessageTemplateId": $scope.current.phonemsgTemplateId == undefined ?  0 : $scope.current.phonemsgTemplateId
                        }
                    )
                }, function (res) {
                    $('#phoneModal').modal('toggle');
                    $scope.flag = true;
                    $scope.dataTable = res
                }, function (err) {
                    $scope.cmsActionMessageTable.getData();
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!', '', "error");
                });
        }
        //重置
        $scope.reset = function () {
            $scope.params.varActionName = '',
                $scope.params.varStartDateActive = '',
                $scope.params.varEndDateActive = ''
            $scope.cmsActionMessageTable.getData();
        }
        //点击新增
        $scope.addAll = function () {
            $('#addModal').modal('toggle');
            $scope.current = {};
            $scope.current.actionId = ''
            $scope.current.startDateActive = $scope.getMyDate();

        }
        //点击保存
        $scope.saveAll = function () {

            //if ($scope.current.actionName == null || $scope.current.actionName == '') {
            //    JS.alert('发送项目不能为空', "error", "确定");
            //    return
            //}
            ////else
            //if ($scope.current.startDateActive == null || $scope.current.startDateActive == '') {
            //    JS.alert('生效日期不能为空', "error", "确定");
            //    return
            //}
            //else if ($scope.current.emailTemplateFlag == null || $scope.current.emailTemplateFlag == '') {
            //    JS.alert('电子邮件模板是否禁用不能为空', "error", "确定");
            //    return
            //}
            //else if ($scope.current.sitemsgTemplateFlag == null || $scope.current.sitemsgTemplateFlag == '') {
            //    JS.alert('站内消息模板是否禁用不能为空', "error", "确定");
            //    return
            //}
            //else if ($scope.current.phonemsgTemplateFlag == null || $scope.current.phonemsgTemplateFlag == '') {
            //    JS.alert('手机短信模板是否禁用不能为空', "error", "确定");
            //    return
            //}
            //else {
            if ($scope.current.actionId == null) {
                $scope.current.actionId = ""
            }
            httpServer.getData(
                URLAPI.CmsActionMessageEdit,
                'POST', {
                    'params': JSON.stringify($scope.current)
                }, function (res) {
                    $scope.cmsActionMessageTable.getData();
                    //$('#addModal').modal('toggle');
                    if (res.status == 'S') {
                        // JS.alert(res.msg);
                        JS.alert('保存成功','success','确定');

                    }
                    else
                    // JS.alert(res.error, res.msg, "error");
                        JS.alert(res.msg, "error", "确定");
                }, function (err) {
                    $scope.messageMaintenanceTable.getData();
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!', '', "error");
                });
        }
        //}
        //点击保存----信息模板
        $scope.save = function () {
            //if ($scope.dataTable.title == null || $scope.dataTable.title == '') {
            //    JS.alert('标题不能为空', "error", "确定");
            //    return
            //}
            //else
            //if ($scope.dataTable.variableLabel == null || $scope.dataTable.variableLabel == '') {
            //    JS.alert('变量标签不能为空', "error", "确定");
            //    return
            //}
            //else if ($scope.dataTable.content == null || $scope.dataTable.content == '') {
            //    JS.alert('内容不能为空', "error", "确定");
            //    return
            //}
            //else {
            httpServer.getData(
                URLAPI.MsgTemplateEdit,
                'POST', {
                    'params': JSON.stringify(
                        {
                            "actionId": $scope.cmsActionMessageTable.data[$scope.cmsActionMessageTable.selectRow].actionId,
                            "messageTemplateId": $scope.dataTable.messageTemplateId,
                            "messageTemplateType": $scope.dataTable.messageTemplateType,
                            "title": $scope.dataTable.title,
                            "content": $scope.dataTable.content,
                            "variableLabel": $scope.dataTable.variableLabel
                        })
                }, function (res) {
                    //if ($scope.dataTable.messageTemplateType == 'Email') {
                    //    $('#emailModal').modal('hide');
                    //}
                    //if ($scope.dataTable.messageTemplateType == 'Phonemsg') {
                    //    $('#phoneModal').modal('hide');
                    //}
                    //if ($scope.dataTable.messageTemplateType == 'Sitemsg') {
                    //    $('#webModal').modal('hide');
                    //}
                    $('#emailModal').modal('hide');
                    $('#phoneModal').modal('hide');
                    $('#webModal').modal('hide');
                    $scope.cmsActionMessageTable.getData();
                    if (res.status == 'S') {
                        JS.alert('保存成功','success','确定');
                    }
                    else
                        JS.alert(res.msg, "error", "确定");
                }, function (err) {
                    $scope.cmsActionMessageTable.getData();
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!', '', "error");
                });
        }
        //}

        //checkbox ng-change事件
        $scope.checkboxClick = function () {
            $scope.count.push($scope.cmsActionMessageTable.selectRow==null ? 0 : $scope.cmsActionMessageTable.selectRow);
            return $scope.count;
        }

        //出掉数组中重复的数字(坐标)
        $scope.check = function () {
            //循环遍历当前数组
            for(var i = 0; i <  $scope.count.length; i++){
                //判断当前数组下标为i的元素是否已经保存到临时数组
                //如果已保存，则跳过，否则将此元素保存到临时数组中
                if($scope.arr.indexOf( $scope.count[i]) == -1){
                    $scope.arr.push( $scope.count[i]);
                    //将一列数据push到数组tempLine中
                    $scope.tempLine.push($scope.cmsActionMessageTable.data[ $scope.count[i]]);
                }
            }
            // console.info('$scope.tempLine'+$scope.tempLine);
            return $scope.tempLine;
        }

        //保存  -- 改變是否禁用
        $scope.changeFlag = function () {
            if ($scope.count==null ||  $scope.count == '') {
                JS.alert('请修改后再保存','','确定');
                return;
            }
            else{
                httpServer.getData(
                    URLAPI.CmsActionMessageUpdate,
                    'POST', {
                        //'params': JSON.stringify($scope.cmsActionMessageTable.data[$scope.cmsActionMessageTable.selectRow])
                        'params': JSON.stringify({
                            "values": $scope.check()
                        })
                    }, function (res) {
                        $scope.cmsActionMessageTable.getData();
                        //JS.alert('保存成功！');
                        //清空数组
                        $scope.count = [];
                        $scope.arr = [];
                        $scope.tempLine = [];
                        if (res.status == 'S') {
                            JS.alert('保存成功','success','确定');
                        }
                        else
                            JS.alert(res.msg, "error", "确定");
                    }, function (err) {
                        $scope.cmsActionMessageTable.getData();
                        JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!', '', "error");
                    });
            }
        }
    }]);

});
