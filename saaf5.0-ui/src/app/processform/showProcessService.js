/**
 * Created by houxingzhang on 2018-01-05.
 *    // PDA 私有 http 服 务
 */
'use strict';
define(['angular'], function (angular) {
    var module = angular.module('http.Service', []);
    module
    // 最近50条回复记录
        .service('processHistoricOpinions', function (httpServer, URLAPI) {
            return function (callBack, errCallBack) {
                httpServer.post(URLAPI.processHistoricOpinions, {}, callBack, errCallBack)
            }
        })
        // 查找会议室 设备
        .service('findMeetingFacilityInfoPagination', function (httpServer, URLAPI, SIEJS) {
            return function (p, callBack, errCallBack) {
                httpServer.post(URLAPI.findMeetingFacilityInfoPagination, {params: JSON.stringify(p)}, function (res) {
                    if (res.status === 'S') {
                        callBack(res);
                    } else {
                        SIEJS.alert(res.msg, 'error');
                    }
                })
            }
        })
        //流程新增
        .service('processFormNew', function (httpServer, URLAPI, processGetStartUrl, iframeTabAction, SIEJS) {
            return function (tableTitle, p) {
                processGetStartUrl({
                    params: JSON.stringify(p)
                }, function (res) {
                    if (res.status === 'S') {
                        if (res.data.url) {
                            if (res.data.url.indexOf('ApplyForm?') == -1) {
                                SIEJS.alert('请按照**ApplyForm进行配置,例如列表页leaveManagement,表单页leaveApplyForm', 'error');
                                return;
                            }
                            var activeTabName = res.data.url.substr(0, res.data.url.indexOf('ApplyForm?')) + 'Management';
                            iframeTabAction(tableTitle, '/showProcess/' + res.data.url + '&action=new&activeTab=' + activeTabName);
                        } else {
                            SIEJS.alert('流程没有设置表单', 'error');
                        }
                    } else {
                        SIEJS.alert(res.msg, 'error');
                    }
                })
            }
        })
        //流程修改跳转
        .service('processFormModify', function (httpServer, URLAPI, processGetStartUrl, iframeTabAction, SIEJS) {
            return function (tableTitle, p, item) {
                processGetStartUrl({
                    params: JSON.stringify(p)
                }, function (res) {
                    if (res.status === 'S') {
                        if (res.data.url.indexOf('ApplyForm?') == -1) {
                            SIEJS.alert('请按照，查询表为：列表名+Management,提交表单为：列表名+ApplyForm进行配置,例如列表页leaveManagement,表单页leaveApplyForm', 'error');
                            return;
                        }
                        var activeTabName = res.data.url.substr(0, res.data.url.indexOf('ApplyForm?')) + 'Management';
                        if (item.status === 'DRAFT') { // 草稿
                            //iframeTabAction('发起流程：' + $scope.processTable.selectRow.name, 'showProcess/' + decodeURIComponent(res.data.url) + '&action=new');
                            iframeTabAction(tableTitle, 'showProcess/' + decodeURIComponent(res.data.url) + '&action=draft&activeTab=' + activeTabName)
                        } else if (item.status === 'ALLOW') { // 审批通过
                            iframeTabAction(tableTitle, 'showProcess/' + decodeURIComponent(res.data.url) + '&action=detail&activeTab=' + activeTabName)

                        } else if (item.status === 'REFUSAL' && res.data.isStart === true) { // 审批驳回

                            iframeTabAction(tableTitle, 'showProcess/' + decodeURIComponent(res.data.url) + '&action=refusal&activeTab=' + activeTabName)

                        } else if (item.status === 'APPROVAL' && res.data.isStart === false) { // 审批中

                            iframeTabAction(tableTitle, 'showProcess/' + decodeURIComponent(res.data.url) + '&action=detail&activeTab=' + activeTabName)

                        }
                    } else {
                        SIEJS.alert(res.msg, 'error');
                    }
                })
            }
        })
        .service('numberToZh', function () {
            return function (n) {
                console.log(n)
                if(n=='' || n == null|| n == undefined) {
                    return;
                }

                //if (!isNumber(n)) {
                //    if(n < 0) {
                //        return false;
                //    }
                //    return;
                //}
                var temp = n.toString().replace(".", '');
                if(temp.length > 10) {
                    var resultStr = '请输入';
                    return resultStr;
                }else{
                    //var fraction = ['角', '分'];
                    //var digit = [
                    //    '零', '壹', '贰', '叁', '肆',
                    //    '伍', '陆', '柒', '捌', '玖'
                    //];
                    //var unit = [
                    //    ['元', '万', '亿'],
                    //    ['', '拾', '佰', '仟']
                    //];
                    //var head = n < 0? '欠': '';
                    //n = Math.abs(n);
                    //var s = '';
                    //
                    //for (var i = 0; i < fraction.length; i++) {
                    //    s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
                    //}
                    //s = s || '整';
                    //n = Math.floor(n);
                    //
                    //for (var i = 0; i < unit[0].length && n > 0; i++) {
                    //    var p = '';
                    //    for (var j = 0; j < unit[1].length && n > 0; j++) {
                    //        p = digit[n % 10] + unit[1][j] + p;
                    //        n = Math.floor(n / 10);
                    //    }
                    //    s = p.replace(/(零.)*零$/, '')
                    //            .replace(/^$/, '零')
                    //        + unit[0][i] + s;
                    //}
                    //
                    //var resultStr =  head + s.replace(/(零.)*零元/, '元')
                    //        .replace(/(零.)+/g, '零')
                    //        .replace(/^整$/, '零元整');
                    //console.log(resultStr)
                    //return resultStr
                    var fraction = ['角', '分'];
                    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
                    var unit = [['元', '万', '亿'], ['', '拾', '佰', '仟']];
                    var head = n < 0 ? '欠' : '';
                    var m = '';
                    if(n){
                        n = Math.abs(n).toString();
                        m = n.split(".");
                        var s = '';
                        if(m[1]){
                            if(m[1].length == 2){
                                for (var i = 0; i < fraction.length; i++) {
                                    s += (digit[m[1][i]] + fraction[i]).replace(/零./, '');
                                }
                            }else if(m[1].length == 1){
                                m[1] = m[1] + "0";
                                for (var i = 0; i < fraction.length; i++) {
                                    s += (digit[m[1][i]] + fraction[i]).replace(/零./, '');
                                }
                            }
                        }
                        s = s || '整';
                        n = Math.floor(n);
                        for (var i = 0; i < unit[0].length && n > 0; i++) {
                            var p = '';
                            for (var j = 0; j < unit[1].length && n > 0; j++) {
                                p = digit[n % 10] + unit[1][j] + p;

                                n = Math.floor(n / 10);
                            }
                            s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
                        }
                        return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
                    }
                }

                //var fraction = ['角', '分'];
                //var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
                //var unit = [['元', '万', '亿'], ['', '拾', '佰', '仟']];
                //var head = n < 0 ? '欠' : '';
                //n = Math.abs(n);
                //var s = '';
                //for (var i = 0; i < fraction.length; i++) {
                //    s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
                //}
                //s = s || '整';
                //n = Math.floor(n);
                //for (var i = 0; i < unit[0].length && n > 0; i++) {
                //    var p = '';
                //    for (var j = 0; j < unit[1].length && n > 0; j++) {
                //        p = digit[n % 10] + unit[1][j] + p;
                //        n = Math.floor(n / 10);
                //    }
                //    s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
                //}
                //return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
            }
        })
        .service('processDelete', function (httpServer, URLAPI, $location, SIEJS, $timeout, pageResp) {
            return function (params, callBack, errCallBack) {
                if (!localStorage[appName + '_currentResp']) return;
                // var resp = JSON.parse(localStorage[appName + '_currentResp']);
                var resp = pageResp.get()
                var respId = $location.search().respId || resp.responsibilityId;
                var p = params.params;
                if (typeof p === 'string') {
                    p = JSON.parse(p);
                }
                ;
                p.responsibilityId = respId;
                params = {
                    params: JSON.stringify(p)
                };
                httpServer.remove(URLAPI.processApplyDelete, params, function (res) {
                    if (res.status === 'S') {
                        $timeout(function () {
                            callBack(res);
                        })
                    } else {
                        SIEJS.alert(res.msg, 'error');
                    }
                    ;
                });
            }
        })

        .service('limitNumLength', function () {
            return function (item,key,len) {
                if(!/^(-?\d+)(\.\d+)?$/.test(item[key])) {
                    item[key] = '';
                    return
                }
                var strAll;
                var n = item[key]
                if(n && n.toString().indexOf('.') > -1) {
                    strAll = n.toString().split('.');
                    if(strAll[1].length > len) {
                        strAll[1] = strAll[1].substring(0,2);
                        item[key] = parseFloat(strAll[0] + '.' + strAll[1]);
                    }
                }
            }
        })

    return module;

});
