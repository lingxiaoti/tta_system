/**
 * Created by houxingzhang on 2017-07-15.
 */
define(['angular', 'app'], function (angular, app) {
    var module = angular.module('SAAF.filters', []);

    module
    /*格式化日期*/
        .filter("formatDate", function ($filter) {
            return function (date, format) {
                var reg = /[-\/ ]/;
                date = date.replace(/-/mig,'/');// 解决safafi 不兼容问题
                var _date = reg.test(date) ? new Date(date).getTime() : date;
                var newDate = '';
                switch (format.toUpperCase()) {
                    case 'YYYY-MM-DD':
                    case 'YYYYMMDD':
                        newDate = $filter('date')(_date, 'yyyy-MM-dd');
                        break;

                    case 'YYYY':
                        newDate = $filter('date')(_date, 'yyyy');
                        break;
                    case 'WEEK':
                    case 'EEEE':
                        switch (_date.getDay()) {
                            case 1:
                                newDate = '星期一';
                                break;
                            case 2:
                                newDate = '星期二';
                                break;
                            case 3:
                                newDate = '星期三';
                                break;
                            case 4:
                                newDate = '星期四';
                                break;
                            case 5:
                                newDate = '星期五';
                                break;
                            case 6:
                                newDate = '星期六';
                                break;
                            case 0:
                                newDate = '星期日';
                                break;
                        }
                        break;
                    default:
                        newDate = $filter('date')(_date, format);
                        break;
                }
                return newDate;


            };
        })
        // 高亮关键字
        .filter("highlightKey", function ($sce) {
            return function (input, s1, color) {

                color = color || 'red';
                if (!s1) return;
                var reg = new RegExp(s1, "gi");
                var len = s1.length;
                var index = reg.exec(input).index; // 匹配index
                var s = input.substring(0, index) + '<span style="color:' + color + ';">' + input.substr(index, len) + '</span>' + input.substring(index + len)
                return $sce.trustAsHtml(s);

            };
        })
        .filter('tohtml', function ($sce) {
            return function (text) {
                return $sce.trustAsHtml(text);
            }
        })

        .filter('boolean', function ($sce) {
            return function (input, type, trustAsHtml) {
                if (input==='' || input ==='undefined') return;
                type = type || 'cn';
                var s;
                input = typeof input==='string' ? input.toUpperCase() :input;
                type = type.toUpperCase();
                switch (input) {
                    case 'Y':
                    case 1:
                    case '1':
                    case 'YES':
                    case 'TRUE':
                    case true:
                    case '是':
                        if (type === 'CN') {
                            s = '<strong>是</strong>'
                        } else if (type === 'ICON') {
                            s = '<strong class="fa fa-check-circle" style="color:#2bb731;display: inline-block;width: 100%; text-align: center;"></strong>'
                        }
                        break;
                    case 'N':
                    case 0:
                    case '0':
                    case 'NO':
                    case 'FALSE':
                    case false:
                    case '否':
                        if (type === 'CN') {
                            s = '<strong style="color:#ccc">否</strong>'
                        } else if (type === 'ICON') {
                            s = '<strong class="fa fa-check-circle" style="color:#ccc;display: inline-block;width: 100%; text-align: center;"></strong>'
                        }
                        break;
                }
                return trustAsHtml ? $sce.trustAsHtml(s) : s;
            }
        })

        /* 块码键值转换 */
        .filter("lookupCode", function ($sce) {
            return function (input, lookupList) {
                for (var n in lookupList) {
                    var item = lookupList[n];
                    if (item.lookupCode === input) {
                        return item.meaning;
                    }
                }
            };
        })
        /* baseDataTable 过滤字段*/
        .filter('dataTablefield', function ($sce) {

        })


        /*
         *身份证
         * */
        .filter('carNo', function ($sce) {
            return function (input) {
                var exec = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)/
                if (exec.test(input))
                    return true;
                else return false
            }
        })

        /*
         * 手机
         * */
        .filter('mobilePhone', function ($sce) {
            return function (input) {
                var exec = /^1(3|4|5|7|8)\d{9}$/
                if (exec.test(input))
                    return true;
                else return false
            }
        })

        /*
         * 邮箱
         * */
        .filter('email', function ($sce) {
            return function (input) {
                var exec = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
                if (exec.test(input))
                    return true;
                else return false
            }
        })
        /**
         * 零转成 n/a
         */
        .filter('zeroToNa', function ($sce) {
            return function (inputValue) {
                //console.log("zeroToNa参数");
                //console.log(inputValue);
                if (inputValue == 0 || inputValue == '0'){
                    return "n/a";
                }
                return inputValue;
            }
        })
        /**
         * 零转成 -
         */
        .filter('zeroToStriping', function ($sce) {
            return function (inputValue) {
                if (inputValue == 0 || inputValue == '0'){
                    return "-";
                }
                return inputValue;
            }
        })
        /**
         * 数字转换正负号(+,-)
         * 举例:
         *     例如: 3 ---> +3;  -3 --->-3
         */
        .filter('changeNumberToPosiNumber', function ($sce) {
            return function (inputValue) {
                var str = inputValue + "";
                var inputV = str.replace(/,/g,"");
                var returnVal = (parseFloat(inputV) <= 0 ? "":"+") + inputValue;
                //console.log(returnVal);
                return returnVal;
            }
        })

    return module;
});