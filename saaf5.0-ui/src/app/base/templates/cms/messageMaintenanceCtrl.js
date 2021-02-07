/**
 * Created by dengdunxin on 2018/2/5.
 */
'use strict';
define(["app"], function (app) {
    app.controller('messageMaintenanceCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI','$stateParams', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI,$stateParams) {
        $scope.messageMaintenanceTable = {};
        $scope.dataTable = {};
        $scope.falg = true;
        $scope.status_falg = true;

        //获取当前日期   yyyy-mm-dd hh:mm:ss
        $scope.getNowFormatDate = function () {
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            var hours =  date.getHours();
            var minutes = date.getMinutes();
            var seconds = date.getSeconds();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            if (hours >= 0 && hours <= 9) {
                hours = "0" + hours;
            }
            if (minutes >= 0 && minutes <= 9) {
                minutes = "0" + minutes;
            }
            if (seconds >= 0 && seconds <= 9) {
                seconds = "0" + seconds;
            }
            var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + hours + seperator2 + minutes + seperator2 + seconds;
            return currentdate;
        }
        $("#currentData_endDateActive").on("click", function() {
            $("#currentData_endDateActive").datetimepicker("setStartDate", $("#editDate").val());
        });

        //点击新增
        $scope.add = function () {
            $('#myModal').modal('toggle');
            $scope.status_falg = true;
            $scope.current = {};
            $scope.current.status = 'Y',
                $scope.current.messageFrom = '系统',
                $scope.current.editName = 'sysadmin',
                $scope.current.pushMessageId = '',
                $scope.current.memberId = '',
                $scope.current.mobileNumber = ''
            $scope.current.editDate =  $scope.getNowFormatDate()
        }

        //修改
        $scope.edit = function () {
            if ($scope.messageMaintenanceTable.selectRow==null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            $scope.status_falg = false;
            $('#myModal').modal('toggle');
            $scope.current = $scope.messageMaintenanceTable.data[$scope.messageMaintenanceTable.selectRow]
        }

        //校验信息模板和信息类型一致
        $scope.check = function () {
            if($scope.current.title != null || $scope.current.title != ''){
                $scope.current.title = ''
                // JS.alert('请先选择信息类型！');
                return;
            }
        }

        //取消
        $scope.cancel = function () {
            if ($scope.messageMaintenanceTable.selectRow==null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            $scope.current = $scope.messageMaintenanceTable.data[$scope.messageMaintenanceTable.selectRow]
            if($scope.current.status == 'C'){
                //JS.alert('该消息已经状态已失效，不能重复发送！');
                JS.alert('该消息已经状态已失效，不能重复发送！','','确定');
                return;
            }else{
                $scope.current.status = 'C'
                httpServer.getData(
                    URLAPI.CmsPushMessageEdit,
                    'POST', {
                        'params': JSON.stringify($scope.current)
                    }, function (res) {
                        $scope.messageMaintenanceTable.getData();
                        if (res.status == 'S') {
                            //JS.alert('状态取消成功！');
                            JS.alert('状态取消成功','success','确定');
                        }
                        else
                            JS.alert(res.error, res.msg, "error");
                    }, function (err) {
                        $scope.messageMaintenanceTable.getData();
                        JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!', '', "error");
                    });
            }
        };

        //发送
        $scope.send = function () {

            if ($scope.messageMaintenanceTable.selectRow==null) {
                JS.alert('请您先选中要修改的行!');
                return;
            }
            $scope.current = $scope.messageMaintenanceTable.data[$scope.messageMaintenanceTable.selectRow]
            if ($scope.current.pushScheduleDate !=null || $scope.current.pushScheduleDate !=null) {
                //JS.alert('已设定发送时间，不能发送信息！');
                JS.alert('已设定发送时间，不能发送信息！','','确定');
                return;
            }
            if ($scope.current.status == 'C' ) {
                //JS.alert('状态是失效，不能发送信息！');
                JS.alert('状态是失效，不能发送信息！','','确定');
                return;
            }
            httpServer.getData(
                URLAPI.CmsSendhMessage,
                'POST', {
                    'params': JSON.stringify(
                        {
                            'messageTitle': $scope.current.title == null ?  ''  : $scope.current.title,
                            'messageContent': $scope.current.content == null ?  ''  : $scope.current.content,
                            // 'orderCode': $scope.current.title == null ?  ''  : $scope.current.title,
                            'messageTemplateType': $scope.current.messageTemplateType == null ?  ''  : $scope.current.messageTemplateType,
                            'messageTo': $scope.current.messageTo  == null ?  ''  : $scope.current.messageTo

                        }
                    )
                }, function (res) {
                    $scope.messageMaintenanceTable.getData();
                    if (res == 0) {
                        //JS.alert('发送成功！');
                        JS.alert('发送成功','success','确定');
                    }
                    else
                        JS.alert(res.error, res.msg, "error");
                }, function (err) {
                    $scope.messageMaintenanceTable.getData();
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!', '', "error");
                });
        };

        //获取发送目标选中的所有值
        $scope.returnData = function () {
            $('#lovOrderModal_2').modal('toggle');
            //选择所有name="'saaflovradio_2'"的对象，返回数组
            var obj=document.getElementsByName('saaflovradio_2');
            //取到对象数组后，我们来循环检测它是不是被选中
            var s='';
            for(var i=0; i<obj.length; i++){
                if(obj[i].checked) s+=obj[i].value+','; //如果选中，将value添加到变量s中
            }
            //除掉最后的 ','
            s = s.substr(0, s.length-1);
            $scope.current.messageTo = s;
            //那么现在来检测s的值就知道选中的复选框的值了
            //alert(s==''?'你还没有选择任何内容！':s);
        }

        //点击保存
        $scope.save = function () {
            if ($scope.current.messageContent == null || $scope.current.messageContent == '') {
                JS.alert('消息主题不能为空', "error", "确定");
                return
            }
            else if ($scope.current.messageTo == null || $scope.current.messageTo == '') {
                JS.alert('推送目标不能为空', "error", "确定");
                return
            }
            //else if ($scope.current.meaning == null || $scope.current.meaning == '') {
            //    JS.alert('短信平台不能为空', "error", "确定");
            //    return
            //}
            else if ($scope.current.messageTemplateType == null || $scope.current.messageTemplateType == '') {
                JS.alert('信息类型不能为空', "error", "确定");
                return
            }
            else if ($scope.current.title == null || $scope.current.title == '') {
                JS.alert('信息模板不能为空', "error", "确定");
                return
            }
            //else if ($scope.current.status == null || $scope.current.status == '') {
            //    JS.alert('状态不能为空', "error", "确定");
            //    return
            //}
            //else if ($scope.current.editName == null || $scope.current.editName == '') {
            //    JS.alert('编辑人不能为空', "error", "确定");
            //    return
            //}
            //else if ($scope.current.pushScheduleDate == null || $scope.current.pushScheduleDate == '') {
            //    JS.alert('计划推送时间不能为空', "error", "确定");
            //    return
            //}
            else {
                httpServer.getData(
                    URLAPI.CmsPushMessageEdit,
                    'POST', {
                        'params': JSON.stringify($scope.current)
                    }, function (res) {
                        $scope.messageMaintenanceTable.getData();
                        $('#myModal').modal('toggle');
                        if (res.status == 'S') {
                            //JS.alert(res.msg);
                            JS.alert('保存成功','success','确定')
                        }
                        else
                            JS.alert(res.error, res.msg, "error");
                    }, function (err) {
                        $scope.messageMaintenanceTable.getData();
                        JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员!!', '', "error");
                    });
            }
        };

        ////关闭
        //$scope.close = function () {
        //    $scope.dataTable.meaning = 'N'
        //    $scope.current.messageTo = ''
        //}

        //重置
        $scope.reset = function(){
            $scope.params.messageContent = '';
            //$scope.messageMaintenanceTable.getData();
        }
    }]);
});