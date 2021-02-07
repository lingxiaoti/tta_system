/**
 * Created by luofu
 */
define(['angular', 'app'], function (angular, app) {
    var module = angular.module('SAAF.DirectivesForCommon', []);
    module

    //saaf表格页码
        .run(['$templateCache', function ($templateCache) {
            $templateCache.put('templateCache/saaf-table-tfoot.html',
                "<table style=\"width: 100%;\">\n" +
                "            <tfoot >\n" +
                "            <tr>\n" +
                "                <td class=\"text-center\" >\n" +
                "                    <nav >\n" +
                "                        <ul class=\"pagination pagination-sm\" style='margin-top: 0px;margin-bottom: 0px'>\n" +
                "                            <li><a style='cursor: pointer' ng-click=\"saafTable.getData(1)\">首页</a>\n" +
                "                            </li><li><a style='cursor: pointer' ng-click=\"saafTable.getData(saafTable.curIndex - 1)\">&lt;</a>\n" +
                "                        </li><li><a><input type=\"text\" ng-model=\"saafTable.inputPage\" style=\"width:50px;padding: 0px; margin: -5px 0;text-align: center\" ng-change=\"changeInputAction(saafTable.inputPage>saafTable.pagesCount?saafTable.pagesCount:saafTable.inputPage)\">" +
                "                            &nbsp;共{{saafTable.pagesCount?saafTable.pagesCount:'0'}}页（总共{{saafTable.count?saafTable.count:'0'}}行）</a>\n" +
                "                        </li><li><a style='cursor: pointer' ng-click=\"saafTable.getData((saafTable.curIndex + 1)>saafTable.pagesCount?saafTable.pagesCount:(saafTable.curIndex + 1))\">&gt;</a>\n" +
                "                        </li><li><a style='cursor: pointer' ng-click=\"saafTable.getData(saafTable.pagesCount)\">尾页</a></li>\n" +
                "                        </ul>\n" +
                "                    </nav>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            </tfoot>\n" +
                "        </table>");
        }])

        //鼠标放在表格上面弹出小标题
        .run(
            function ($rootScope, $location) {
                $(document).on("mouseover", "table[saaf-table] tbody tr td", function () {
                    var value =  $(this).context?$(this).context.innerText:$(this)[0].innerText;
                    $(this).attr('title',value );
                })
            }
        );


    module.controller('saafTableController', function saafTableController(saafXlsExport, $http, $state, $stateParams, $timeout, $interval, $scope, $parse, $filter, $attrs, SIEJS, httpServer, URLAPI, $element, $rootScope, $location, SIEJS) {


        var ctrl = this;
        var saafTable = $attrs.saafTable;
      console.log($attrs);

      if (!saafTable) {
            SIEJS.alert('请设置表格saaf-table属性！', "error", "确定");
            return
        }
        var url = URLAPI[$attrs.url];

        if (!url) {
            SIEJS.alert('未找到表格data-url属性！', "error", "确定");
            return
        }
        $scope[$attrs.params] = $scope[$attrs.params] ? $scope[$attrs.params] : {};
        var params = $scope[$attrs.params];
        var paramsForreStore = $.extend({}, params);
        var X = $element.offset().top;
        var height = $(window).height();
        var rowHeight = $attrs.rowHeight;

        var pageRows = parseInt((height - X - 90) / (rowHeight ? rowHeight : 30));
        if (pageRows < 5) pageRows = 5;
        pageRows = $attrs.pageRows ? $attrs.pageRows : pageRows;
        var pageIndex = $attrs.pageIndex ? $attrs.pageIndex : 1;


        $scope[saafTable] = $scope[$attrs.saafTable] ? $scope[$attrs.saafTable] : {};
//构建表格
        function rebuildTable(res) {

            $scope[saafTable] = (res == null || $.trim(res) == '') ? {} : res;
            $scope[saafTable].getData = ctrl.getData;
            $scope[saafTable].pageNumbers = [];
            //创建页页码
            for (var i = $scope[saafTable].curIndex; i <= $scope[saafTable].pagesCount; i++) {
                $scope[saafTable].pageNumbers.push(i);
            }
            $scope[saafTable].inputPage = $scope[saafTable].curIndex;
            $scope[saafTable].restore = function () {

                $scope[$attrs.params] = $.extend({}, paramsForreStore);
            };
            $scope[saafTable].xlsExport = ctrl.xlsExport;

            //add by luofenwu 初始化列数
            $scope[saafTable].initColNum = ctrl.initColNum;
        }

        ctrl.initColNum = function () {
            $scope.saafTableColNum = $("table[saaf-table] tr th").length;
            //console.log("  $scope.saafTableColNum ="+  $scope.saafTableColNum )
        }();

//表格xls导出
        ctrl.xlsExport = function xlsExport(exportName) {

            if ($scope[saafTable].count == null) {
                SIEJS.alert("请先点击搜索再点击导出！", "info");
                return;
            }
            if ($scope[saafTable].count > (100 * 1000)) {
                SIEJS.alert("导出数量过大（超过10万），请添加搜索条件！", "info");
                return;
            }
            $http.get($state.current.templateUrl).success(function (response) {

                var responseTable = $(response).find("[saaf-table='" + $attrs.saafTable + "']");
                var responseHeadname = [];
                responseTable.find("thead>tr>th").each(function (n) {
                    var text = $.trim($(this).text());

                    responseHeadname.push(text);


                });

                var index = response.indexOf('saaf-table="' + $attrs.saafTable)
                if (index < 0) {
                    index = response.indexOf("saaf-table='" + $attrs.saafTable)
                }

                response = response.slice(index);

                var indexEnd = response.indexOf('table>');
                response = response.slice(0, indexEnd);
                var indexTbody = response.lastIndexOf('<tbody');
                response = response.slice(indexTbody);
                for (var i = 0; i < 200; i++) {
                    if (response.indexOf('<!--') < 0) {
                        break;
                    }

                    var str1 = response.substring(0, response.indexOf('<!--'));

                    var str3 = response.substring(response.indexOf('<!--'));
                    var str2 = str3.substring(str3.indexOf('-->') + 3);

                    response = str1 + str2;
                }
                response = response.slice(response.indexOf('<tr '));

                var repeat = response.slice(response.indexOf(' ng-repeat=') + 12, response.indexOf(' in '));
                var repeatRow = $.trim(repeat);

                var tds = [];
                while (response.indexOf('<td') > 0) {
                    tds.push(response.slice(response.indexOf('<td') + 4, response.indexOf('</td>')));
                    response = response.slice(response.indexOf('</td>') + 5);
                }

                var realName = [];

                $.each(tds, function (n, value) {

                    var kuHaoNei = value.substring(value.indexOf('{{') + 2, value.indexOf('}}'));
                    kuHaoNei = kuHaoNei.substring(kuHaoNei.indexOf(repeatRow + '.') + repeatRow.length + 1);
                    if (kuHaoNei.indexOf('.') >= 0) kuHaoNei = kuHaoNei.substring(0, kuHaoNei.indexOf('.'));
                    if (kuHaoNei.indexOf('.') >= 0) kuHaoNei = kuHaoNei.substring(0, kuHaoNei.indexOf('.'));
                    if (kuHaoNei.indexOf('.') >= 0) kuHaoNei = kuHaoNei.substring(0, kuHaoNei.indexOf('.'));
                    if (kuHaoNei.indexOf(')') >= 0) kuHaoNei = kuHaoNei.substring(0, kuHaoNei.indexOf(')'));
                    if (kuHaoNei.indexOf(')') >= 0) kuHaoNei = kuHaoNei.substring(0, kuHaoNei.indexOf(')'));
                    if (kuHaoNei.indexOf(')') >= 0) kuHaoNei = kuHaoNei.substring(0, kuHaoNei.indexOf(')'));
                    if ($.trim(kuHaoNei) == '') {

                        if (value.indexOf('ng-model') > 0) {
                            kuHaoNei = value.substring(value.indexOf('ng-model') + 8);
                            kuHaoNei = $.trim(kuHaoNei).substring(kuHaoNei.indexOf('=') + 1);
                            if ($.trim(kuHaoNei.indexOf('"')) == 0) {
                                kuHaoNei = $.trim(kuHaoNei).substring(1);
                                kuHaoNei = $.trim(kuHaoNei).substring(0, kuHaoNei.indexOf('"'));
                            }
                            else if ($.trim(kuHaoNei.indexOf("'")) == 0) {
                                kuHaoNei = $.trim(kuHaoNei).substring(1);
                                kuHaoNei = $.trim(kuHaoNei).substring(0, kuHaoNei.indexOf("'"));
                            }
                            kuHaoNei = kuHaoNei.substring(kuHaoNei.indexOf(repeatRow + '.') + repeatRow.length + 1);
                        }
                    }
                    realName.push($.trim(kuHaoNei));
                })
                //纠正取到的name不正确
                $element.find('thead th').each(function (index) {
                    var name = $(this).attr('data-xls-export-name');
                    if (name != null) {
                        realName[index] = name;
                    }
                })
                var headName = [];

                $element.find('thead th').each(function (index) {
                    if ($(this).is(':visible')) {
                        headName.push($(this).text())
                    }

                })

                var deleteIndex;
                $.each(responseHeadname, function (hh, row1) {
                    if ($.trim(row1) == '序号') deleteIndex = hh;
                })
                if (deleteIndex != null) {
                    responseHeadname.splice(deleteIndex, 1);
                    realName.splice(deleteIndex, 1);
                }

                for (var hh = 0; hh < responseHeadname.length; hh++) {
                    if (headName.indexOf(responseHeadname[hh]) < 0) {
                        responseHeadname.splice(hh, 1);
                        realName.splice(hh, 1);
                        hh--;

                    }
                }


                /*       console.log(realName);
                 console.log(headName);
                 console.log( ctrl.paramFunction());
                 console.log(URLAPI[$attrs.url] );*/
                // console.log(window.parent.$('#mainTabList .active a').text());


                saafXlsExport(URLAPI[$attrs.url], ctrl.paramFunction(), exportName ? exportName : window.parent.$('#mainTabList .active a').text(), responseHeadname, realName);
            })
        };

        var interval;
        var progressBar = {};
//添加进度条
        progressBar.createProgressBar = function () {
            if ($("#tableProgressBar").length > 0) {

                return;
            }
            $('body').prepend("<div class=\"progress\" style=\"position: fixed;top: 0px;width: 100%;z-index: 1000;height: 3px;background-color: rgba(0, 0, 0, 0);\" >" +
                "	<div class=\"progress-bar progress-bar-warning\" role=\"progressbar\"\n" +
                "	id='tableProgressBar'	 style=\"width: 3%;background-color: white\">\n" +
                "	</div>\n" +
                "</div>");
            var width = 3;

            interval = $interval(function () {

                width = width + 1;
                if (width > 50) {
                    $interval.cancel(interval);
                    second();
                }
                $('#tableProgressBar').css('width', width + '%');
                //console.log($state)
                if ($state.current.name == 'login') {
                    progressBar.cancelProgressBar();
                }
            }, 500, false);

            function second() {
                interval = $interval(function () {
                    width += 0.5;
                    if (width > 95) {
                        $interval.cancel(interval);
                    }
                    $('#tableProgressBar').css('width', width + '%');
                    if ($state.current.name == 'login') {
                        progressBar.cancelProgressBar();
                    }
                }, 1000, false);
            }

        }

//添加取消进度条
        progressBar.cancelProgressBar = function () {
            if (!$("#tableProgressBar").length > 0) {
                return;
            }
            $interval.cancel(interval);
            $('#tableProgressBar').css('width', '100%');

            $timeout(function () {
                $('#tableProgressBar').parent().remove();
            }, 1500, false);
        }

        this.getData = function getData(pageIndex1) {
            progressBar.createProgressBar();
//
            httpServer.post(
                url,

                (   ctrl.paramFunction = function () {
                    if ($attrs.notParam == "true") {
                        return null;
                    }
                    else if ($attrs.otherParamFunction != null) {
                        return $scope[$attrs.otherParamFunction](pageIndex1, pageRows);
                    } else if ($attrs.hasPageIndex == 'false') {
                        return {
                            'params': JSON.stringify($scope[$attrs.params])
                        }
                    }
                    else return {
                            'params': JSON.stringify($scope[$attrs.params]),
                            pageRows: pageRows,
                            pageIndex: pageIndex1 ? pageIndex1 : pageIndex
                        }
                })(),
                function (res) {
                    res = res ? res : {};
                    progressBar.cancelProgressBar();

//                        if (res.hasOwnProperty('status')) {
//                            if (res.status == 'E') {
//                                SIEJS.alert('查询有误！错误详情已打印至浏览器输出窗口；查询路径为' + url + '；res.status == "E"' + res.msg, "error", "确定");
//                                console.log(url, '异常详情为：');
//                                console.log(res);
//                            }
//                        }
//
//                        if (res.data == null || !res.data instanceof Array) {
//                            SIEJS.alert('后台返回异常；异常详情已打印至浏览器输出窗口；查询路径为' + url + '；', "warning", "确定");
//                            console.log(url, '异常详情为：');
//                            console.log(res);
//                        }
                    if (res.hasOwnProperty('status')) {
                        if (res.status != 'S') {
                            SIEJS.alert(res.msg + "! 错误编码：" + res.status, "error", "确定");
                            console.log(url, '消息详情：');
                            console.log(res);
                        }
                    } else if (res.data == null || !res.data instanceof Array) {
                        SIEJS.alert('后台返回异常；异常详情已打印至浏览器输出窗口；查询路径为' + url + '；', "error", "确定");
                        console.log(url, '异常详情为：');
                        console.log(res);
                    }

                    rebuildTable(res);
                    if ($attrs.afterGetData) {
                        $scope.$eval($attrs.afterGetData)
                    }

                },
                function (err) {
                    progressBar.cancelProgressBar();
                    SIEJS.alert('查询有误！查询路径为' + url + '；后台返回异常！', "error", "确定");
                }
            );
        }
        $scope[saafTable].getData = ctrl.getData;


    })

    /**
     *   saaf表格控制器 add by luofu
     *    配置信息：
     *    saaf-table="dataTable"：将表格数据，表格查询方法，注入到$scope.dataTable
     *    data-page-rows="15"：定义表格行数为15，如果未定义，则默认会取屏幕高度计算，需要显示多少行
     *    data-row-height="20"：告诉表格，每一行大概有20px的高度，用于自动计算查询的行数
     *    data-page-index="2":定义查询的页码
     *    data-auto-get-data="true":定义表格是否自动获取数据
     *    data-params="params":定义查询传参为$scope.params
     *    data-other-param-function="otherParamFunction"  :自定义的传参的方法 该方法为$scope.otherParamFunction
     *   data-has-page-index="false":查询是否带pageIndex
     *   data-after-get-data="afterGetData()":执行完getData()，执行的方法
     *   data-not-param="true"  :不会添加查询条件
     *
     *    暴露的方法：
     *    dataTable.getData(pageIndex):查询数据的方法
     *    dataTable.restore():重置查询条件的方法
     *    dataTable.xlsExport():表格导出方法，导出对于复杂的列会有取值错误的情况，请加上    例如   <th data-xls-export-name="userName">账户名称</th>
     */
        .directive('saafTable', function () {
            return {
                restrict: 'AE',
                controller: 'saafTableController',
                link: function ($scope, $element, $attr, $ctrl) {

                    if ($attr.autoGetData != 'false') {
                        $ctrl.getData();
                    }

                    //$element.find('td').on("mouseenter",function(){
                    //    alert("The paragraph was clicked.");
                    //});
                }
            };
        })
        //saaf表格页码 <!--add by luofu-->
        .directive('saafTableTfoot', function ($timeout) {
            return {

                templateUrl: 'templateCache/saaf-table-tfoot.html',
                scope: {
                    saafTable: '=saafTableTfoot'
                },
                replace: true,
                restrict: 'AE',
                link: function ($scope, $element, $attr, $ctrl) {
                    var timer = null;

                    $scope.changeInputAction = function (changeInput) {
                        console.log(changeInput);
                        if (timer != null) {

                            $timeout.cancel(timer)
                        }
                        timer = $timeout(
                            function () {
                                $scope.saafTable.getData(changeInput);
                                timer = null;
                            },
                            750
                        );

                    }
                }
            };
        })
        //saaf lov控制器 <!--add by luofu-->
        .controller('saafLovController', ['$timeout', '$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', function saafTableController($timeout, $scope, $parse, $filter, $attrs, JS, httpServer) {


            var option = eval("(" + $attrs.saafLovOption + ")");
            $.extend($scope, option);

            $scope.$watch(function () {
                return $attrs.saafLovOption;
            }, function (newVal, oldVal) {
                $.extend($scope, eval("(" + $attrs.saafLovOption + ")"));
            });
            $scope.showRadio = function (selectVal) {
                $scope.selectRadio = selectVal;
            }
            $scope.dbclickAction = function (selectVal) {

                $scope.selectRadio = selectVal;
                $scope.returnData();
            }
            $scope.returnData = function () {
                var selectRow = $scope.selectRadio - ($scope.dataTable.curIndex - 1) * $scope.dataTable.pageSize - 1;
                if (selectRow < 0 || selectRow > $scope.dataTable.pageSize) {

                    $('#lovOrderModal').modal('toggle');
                    return;
                }

                $scope.returnValue1 = $scope.dataTable.data[selectRow][$scope.returnColName1];
                $scope.returnValue1 = $scope.dataTable.data[selectRow][$scope.returnColName1];

                if ($attrs.returnValue2 != null) {
                    $scope.returnValue2 = $scope.dataTable.data[selectRow][$scope.returnColName2];
                }
                if ($attrs.returnValue3 != null) {
                    $scope.returnValue3 = $scope.dataTable.data[selectRow][$scope.returnColName3];
                }
                if ($attrs.returnValue4 != null) {
                    $scope.returnValue4 = $scope.dataTable.data[selectRow][$scope.returnColName4];
                }
                if ($attrs.returnValue5 != null) {
                    $scope.returnValue5 = $scope.dataTable.data[selectRow][$scope.returnColName5];
                }
                if ($attrs.returnValue6 != null) {
                    $scope.returnValue6 = $scope.dataTable.data[selectRow][$scope.returnColName6];
                }
                if ($attrs.returnValue7 != null) {
                    $scope.returnValue7 = $scope.dataTable.data[selectRow][$scope.returnColName7];
                }
                if ($attrs.returnValue8 != null) {
                    $scope.returnValue8 = $scope.dataTable.data[selectRow][$scope.returnColName8];
                }
                if ($attrs.returnValue9 != null) {
                    $scope.returnValue9 = $scope.dataTable.data[selectRow][$scope.returnColName9];
                }
                if ($attrs.returnValue10 != null) {
                    $scope.returnValue10 = $scope.dataTable.data[selectRow][$scope.returnColName10];
                }
                if ($attrs.returnValue11 != null) {
                    $scope.returnValue11 = $scope.dataTable.data[selectRow][$scope.returnColName11];
                }
                if ($attrs.returnValue12 != null) {
                    $scope.returnValue12 = $scope.dataTable.data[selectRow][$scope.returnColName12];
                }
                if ($attrs.returnValue13 != null) {
                    $scope.returnValue13 = $scope.dataTable.data[selectRow][$scope.returnColName13];
                }
                if ($attrs.returnValue14 != null) {
                    $scope.returnValue14 = $scope.dataTable.data[selectRow][$scope.returnColName14];
                }
                if ($attrs.returnValue15 != null) {
                    $scope.returnValue15 = $scope.dataTable.data[selectRow][$scope.returnColName15];
                }
                if ($attrs.returnValue16 != null) {
                    $scope.returnValue16 = $scope.dataTable.data[selectRow][$scope.returnColName16];
                }
                if ($attrs.returnValue17 != null) {
                    $scope.returnValue17 = $scope.dataTable.data[selectRow][$scope.returnColName17];
                }
                if ($attrs.returnValue18 != null) {
                    $scope.returnValue18 = $scope.dataTable.data[selectRow][$scope.returnColName18];
                }
                if ($attrs.returnValue19 != null) {
                    $scope.returnValue19 = $scope.dataTable.data[selectRow][$scope.returnColName19];
                }
                if ($attrs.returnValue20 != null) {
                    $scope.returnValue20 = $scope.dataTable.data[selectRow][$scope.returnColName20];
                }
                if ($attrs.returnValue21 != null) {
                    $scope.returnValue21 = $scope.dataTable.data[selectRow][$scope.returnColName21];
                }
                if ($attrs.returnValue22 != null) {
                    $scope.returnValue22 = $scope.dataTable.data[selectRow][$scope.returnColName22];
                }
                if ($attrs.returnValue23 != null) {
                    $scope.returnValue23 = $scope.dataTable.data[selectRow][$scope.returnColName23];
                }
                if ($attrs.returnValue24 != null) {
                    $scope.returnValue24 = $scope.dataTable.data[selectRow][$scope.returnColName24];
                }
                if ($attrs.returnValue25 != null) {
                    $scope.returnValue25 = $scope.dataTable.data[selectRow][$scope.returnColName25];
                }

                $('#lovOrderModal').modal('toggle');
                $timeout(function () {
                        if ($attrs.hasOwnProperty('returnAction')) {//判断是否有客制化的上传完成方法
                            $scope.$parent.$eval($attrs.returnAction);
                        }
                    }, 200
                )

            }

            $scope.clearData = function () {
                $scope.returnValue1 = "";
                $scope.returnValue2 = "";
                $scope.returnValue3 = "";
                $scope.returnValue4 = "";
                $scope.returnValue5 = "";
                $scope.returnValue6 = "";
                $scope.returnValue7 = "";
                $scope.returnValue8 = "";
                $scope.returnValue9 = "";
                $scope.returnValue10 = "";
                $scope.returnValue11 = "";
                $scope.returnValue12 = "";
                $scope.returnValue13 = "";
                $scope.returnValue14 = "";
                $scope.returnValue15 = "";
                $scope.returnValue16 = "";
                $scope.returnValue17 = "";
                $scope.returnValue18 = "";
                $scope.returnValue19 = "";
                $scope.returnValue20 = "";
                $scope.returnValue21 = "";
                $scope.returnValue22 = "";
                $scope.returnValue23 = "";
                $scope.returnValue24 = "";
                $scope.returnValue25 = "";

                $('#lovOrderModal').modal('toggle');
                // $timeout(function () {
                //         if ($attrs.hasOwnProperty('returnAction')) {//判断是否有客制化的上传完成方法
                //             $scope.$parent.$eval($attrs.returnAction);
                //         }
                //     }, 200
                // )
            }

        }])


    //saaf lov指令 <!--add by luofu-->
    /*    <input type="text" class="form-control radius3" name="messageTitle"
     ng-model="current.instName" required>
     <span class="input-group-btn" saaf-lov=""
     data-is-auto-show="false" //是否监控输入框改变，则自动查询
     data-return-action="lovAction()" //选中之后，执行回调$scope.lovAction();
     data-return-value1="current.instId"//返回的值  传到$scope.current.instId
     data-return-value2="current.instName"//返回的值  传到$scope.current.instName
     saaf-lov-option="{
     titleName:'lov名称',
     autoGetData:'false',   //定义表格是否自动获取数据
     dataUrl:'queryInstLov',
     params:{},   //设置初始化参数
     searchLabel1:'搜索标签名1',
     searchColName1:'varInstCode', //搜索参数名称
     searchLabel2:'搜索标签名2',
     searchColName2:'varInstName',//搜索参数名称
     label1:'lov表格名称1',
     colName1:'instCode', //表格显示列名
     label2:'lov表格名称2',
     colName2:'instName',//表格显示列名
     returnColName1:'instId' ,//选中 的列名
     returnColName2:'instName'//选中 的列名
     }"></span>*/
    module.directive('saafLov', function ($http, $compile, $timeout) {


        return {
            template: '<button ng-disabled="ngDisabled" id="{{attrs.id?attrs.id+\'button\':\'\'}}" type="button" class="btn btn-default"  ng-click="showLov()" >' +
            '<i class="fa fa-search" aria-hidden="true"></i>' +
            '</button>',
            scope: {
                returnValue1: '=',
                returnValue2: '=',
                returnValue3: '=',
                returnValue4: '=',
                returnValue5: '=',
                returnValue6: '=',
                returnValue7: '=',
                returnValue8: '=',
                returnValue9: '=',
                returnValue10: '=',
                returnValue11: '=',
                returnValue12: '=',
                returnValue13: '=',
                returnValue14: '=',
                returnValue15: '=',
                returnValue16: '=',
                returnValue17: '=',
                returnValue18: '=',
                returnValue19: '=',
                returnValue20: '=',
                returnValue21: '=',
                returnValue22: '=',
                returnValue23: '=',
                returnValue24: '=',
                returnValue25: '=',
                ngDisabled: '=',

            },
            transclude: true,
            controller: 'saafLovController',
            restrict: 'AE',
            link: function saafLovLinkAction($scope, $element, $attrs, $ctrl) {

                $scope.attrs = $attrs;
                //监听是否用户直接输入；如果直接输入，则弹出搜索框
                var openFlag = false;
                var old = '';

                if ($attrs.isAutoShow != 'false') {
                    $($element).prev().keydown(function (e) {
                        if (old == $($element).prev().val()) {
                            if (e.key == 'Enter') {
                                return
                            }
                        }

                        if (e.key == 'ArrowUp' || e.key == 'ArrowDown' || e.key == 'ArrowRight' || e.key == 'ArrowLeft' || e.key == 'Tab' || e.key == 'F11' || e.key == 'F12' || e.key == 'F5' || e.key == 'CapsLock' || e.key == 'Shift' || e.key == 'Control') {
                            return
                        }


                        if (!openFlag) {
                            $scope.showLov();
                        }

                        e.preventDefault();
                    });


                    $($element).prev().focus(function (e) {
                        old = $($element).prev().val();
                    });
                    $($element).prev().change(function (e) {

                        var changeFlag = $($element).prev().val() == old;
                        $($element).prev().val(old)
                        if ((!changeFlag) && (!openFlag)) {
                            $scope.showLov();
                        }
                    });

                }

                $scope.showLov = function () {
                    openFlag = true;
                    $scope.selectRadio = null;
                    if ($scope.searchColName1)
                        $scope.params[$scope.searchColName1] = '';
                    if ($scope.searchColName2)
                        $scope.params[$scope.searchColName2] = '';


                    var lovUrl = "js/directives/html/saafLov.html"
                    $http.get(lovUrl)
                        .success(function (response) {
                            $('body').append(response);
                            if ($scope.dataTable) {
                                $scope.dataTable.data = null;
                            }

                            $('#lovOrderModal').find('table').attr('data-url', $scope.dataUrl);
                            $('#lovOrderModal').find('table').attr('data-auto-get-data', $scope.autoGetData == null ? true : $scope.autoGetData);

                            $('#lovOrderModal').modal('toggle');
                            $compile($('#lovOrderModal'))($scope);

                            $('#lovOrderModal').on('hidden.bs.modal', function () {

                                $('#lovOrderModal').remove();
                                openFlag = false;

                            })
                        });
                }

            }
        }
    })


    //saaf 快码下拉框指令 <!--add by luofu-->
        .directive('saafSelectLookupcode', function ($http, $compile, URLAPI, httpServer, $rootScope, $timeout) {
            return {
                template: '  <option ng-repeat="data in lookUpData |filter:myFilter" value="{{data.lookupCode}}">' +
                '{{data.meaning}}' +
                '</option>',
                scope: {},
                replace: true,
                restrict: 'AE',

                link: function ($scope, $element, $attr, $ctrl) {
                    $scope.$attr = $attr;
                    $scope.lookUpData = [];
                    // $scope.myFilter = function (item) {
                    //     return item.lookupType == $attr.saafSelectLookupcode;
                    // };

                    httpServer.post(URLAPI.queryLookupLineDic, {
                        'params': JSON.stringify({lookupType: $attr.saafSelectLookupcode})
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.lookUpData = res.data
                            // console.info(res);
                        }
                    })

                    //
                    // $timeout(function () {
                    //         $scope.lookUpData = eval("(" + window.localStorage.SaafLookupCodes + ")");
                    //
                    //     }, 500
                    // )

                }
            };
        })
        //saaf 快码下拉框获取tex值的指令 <!--add by luofu-->
        .directive('saafSelectTex', function ($parse, $http, $compile, URLAPI, httpServer, $rootScope) {
            return {
                restrict: 'AE',
                link: function ($scope, $element, $attr, $ctrl) {
                    $scope.$watch(function () {
                        return $parse($attr.ngModel)($scope);
                    }, function (newVal, oldVal) {
                        var tex = $element.find("option:selected").text();
                        if (tex == null)
                            $parse($attr.saafSelectTex + '=null')($scope);
                        else
                            $parse($attr.saafSelectTex + '="' + tex + '"')($scope);
                    });
                }
            };
        })
        /**
         * 图片上传控制器
         */

        .controller('saafImgController', function saafTableController($rootScope, Cookies, $window, $timeout, $interval, $scope, $parse, $filter, $attrs, SIEJS, httpServer, URLAPI, $element, FileUploader, saafImageLook) {


            var deleteData = [];//删除的数组，减少产生太多垃圾图片
            var setting = {};//设置上传参数
            setting.url = URLAPI.imgUpload;
            setting.headers = {certificate: Cookies.getCookie('certificate')};
            var uploader = $scope.uploader = new FileUploader(setting);
            uploader.filters.push({//图片限制
                name: 'imageFilter',
                fn: function (item, options) {
                    var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                    return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
                }
            });
            uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
                SIEJS.alert('当前图片格式仅支持jpg|png|jpeg|bmp|gif格式！！', "error", "确定");
            };
            uploader.onCompleteAll = function () {

                uploader.clearQueue();
            };
            uploader.onAfterAddingAll = function (items) {
                if (!$attrs.hasOwnProperty('size')) {
                    uploader.uploadAll();
                    return
                }
                var size = $attrs.size.split('*');
                var heightLimit = size[1];
                var withLimit = size[0];
                //console.info('onBeforeUploadItem', items);
                var message = {};
                message.flag = true;
                message.noOkName = '';

                if ($($element).parent().find("#selectSite").hasOwnProperty("data-max-length")) {
                    var length = $($element).parent().find("input").length;
                    var _maxlength = $($element).parent().find("#selectSite").attr("data-max-length")
                    if (length >= _maxlength) {
                        SIEJS.alert("'最多只能上传'" + _maxlength + "'张'", "error", "确定", message.noOkName);
                        return
                    }
                }
                $.each(items, function (n, item) {
                    isValidFileSize(item, message);
                })
                var doingSize = 0;

                function afterIsValid() {
                    doingSize++;
                    if (doingSize == items.length) {
                        if (!message.flag) {
                            SIEJS.alert('请上传' + $attrs.size + '像素的图片！\n以下图片不符合要求：', "error", "确定", message.noOkName);
                            uploader.clearQueue();
                        }
                        else {
                            uploader.uploadAll();
                        }
                    }
                }


                function isValidFileSize(item, message) {
                    var reader = new FileReader();
                    reader.onload = onLoadFile;

                    reader.readAsDataURL(item._file);

                    function onLoadFile(event) {
                        var img = new Image();
                        img.src = event.target.result;
                        img.onload = function () {
                            var naturalWidth = img.width;
                            var naturalHeight = img.height;
                            //if()
                            console.log(naturalWidth, naturalHeight);
                            console.log(withLimit, heightLimit)
                            if (naturalWidth == withLimit && naturalHeight == heightLimit) {

                            }
                            else {
                                message.flag = false;

                                message.noOkName = message.noOkName + ( (message.noOkName == '' ? '' : '\n') + item.file.name);
                                console.log(message.noOkName);
                            }
                            afterIsValid();
                        }
                    }
                }
            };
            uploader.onCompleteItem = function (fileItem, res, status, headers) {
                if (res.status != 'S') {
                    SIEJS.alert('上传文件失败！！！', "error", "确定");
                    return;
                }
                if ($attrs.hasOwnProperty('addAction')) {//判断是否有客制化的上传完成方法
                    $timeout(function () {
                            var returnMessage = JSON.stringify(res.data[0]);
                            var doString = $attrs.addAction.replace('$src', "'" + res.data[0].accessPath + "'").replace('$imgName', "'" + res.data[0].fileName + "'").replace('$returnMessage', returnMessage);
                            $scope.$parent.$eval(doString)
                        },
                        300
                    )
                    //add by ou  上传成功后清除type="file"的value值，这样子上传同一张图片的时候
                    //就可以触发图片上传事件
                    $('#fileUploadId').val('');
                }
                else { //自带的图片上传完成的方法
                    $scope.imgUrl = res.data[0].accessPath;
                    if (deleteData.length > 0) {

                        for (var i = 0; i < deleteData.length; i++) {

                            httpServer.post(URLAPI.imgDelete, {
                                groupName: deleteData[i].groupName,
                                remoteFileName: deleteData[i].remoteFileName
                            }, function (res) {
                                if (res.status != 'S')
                                    SIEJS.alert('上传文件成功后，清除旧文件失败！！！', "error", "确定");
                            }, function (err) {
                                SIEJS.alert('上传文件成功后，清除旧文件失败！！！', "error", "确定");
                            });
                        }
                    }
                    deleteData = res.data;
                }
            };

            $scope.deleteImg = function (e) {
                if ($attrs.hasOwnProperty('deleteAction')) {//是否有客制化图片删除的操作
                    $scope.$parent.$eval($attrs.deleteAction)
                }
                else
                    $scope.imgUrl = '';
            };
            //图片点击上传按钮的方法
            $scope.autoClick = function (e) {

                if (!($attrs.hasOwnProperty('hasUp') && $attrs.hasUp == 'false')) {

                    if ($attrs.hasOwnProperty('clickAction') && $attrs.clickAction != '') {
                        var continueFlag = $scope.$parent.$eval($attrs.clickAction);
                        if (continueFlag == false) {
                            return;
                        }
                    }

                    if ($attrs.multiple == true) $($element).find('input').attr('multiple', 'true');

                    $($element).find('input').trigger("click");
                }

            };
            $scope.saafImageLook = saafImageLook;//图片放大服务
            $scope.imgShow = true;//是否显示
            if ($attrs.maxLength) {//判断是否超过了最大上传量参数
                $scope.$watch(function () {
                    var length = $($element).parent().children('div').size();
                    return length;
                }, function (newVal, oldVal) {
                    if (newVal > $attrs.maxLength) {
                        $scope.imgShow = false;
                    } else {
                        $scope.imgShow = true;
                    }

                });
            }


        })

        //
        .directive('saafImg', function (FileUploader) {

            return {

                template: '<div  ng-show="imgShow" style="float: left;margin: 5px 10px 5px 0;position:relative;display: table;">' +
                '<i ng-if="(!attrs.hasOwnProperty(\'hasDeleteFlag\')&& (imgUrl&&imgUrl!=\'\'))||attrs.hasDeleteFlag==\'true\'" class="fa fa-times btn  btn-warning btn-xs " aria-hidden="true" style="position:absolute;margin-left:105px;top: -5px;z-index: 100;border-radius:10px;" ng-click="deleteImg()"></i>' +
                '<div  class="w120 h120 text-center saaf-img-up" style="display:table-cell;  text-align:center;vertical-align:middle;"    ng-click="autoClick($event)">' +

                '<span ng-if="imgUrl&&imgUrl!=\'\'"><img  ng-src="{{imgUrl}}"  style="max-width: 100%;max-height: 100%; " ng-mouseover="saafImageLook.open($event)"  ng-mouseleave="saafImageLook.close()"></span>' +
                '<div ng-if="!(imgUrl&&imgUrl!=\'\')" >' +
                '<h2 ng-if="!attrs.hasUp"  style="margin-top: 10px">{{uploader.isUploading ?((uploader.progress==0)?0:uploader.progress-1)+"%":"+" }}<h4 ng-if="!uploader.isUploading">{{attrs.maxLength?(\'至多\'+attrs.maxLength+\'张\'):(uploader.isHTML5 && attrs.multiple)?"可多选":""}}</h4></h2>' +
                '<div>{{message}}</div>' +
                '<div ng-if="attrs.size" >大小:{{attrs.size}}</div>' +
                '</div>' +
                '</div>' +
                '<span hidden>  <input id="fileUploadId" type="file" nv-file-select="" uploader="uploader" /></span>' +
                '<div ng-if="attrs.imgName" class="   btn-success  btn-xs " aria-hidden="true" style="position:absolute;margin-left:-125px;top: 100px;z-index: 100;border-radius:10px;" >{{attrs.imgName}}</div>' +

                '</div>',
                scope: {
                    imgUrl: '=',
                    message: '='

                },
                replace: true,
                restrict: 'AE',
                controller: 'saafImgController',
                link: function ($scope, $element, $attrs, $ctrl) {
                    $scope.attrs = $attrs;

                }
            };
        })
        .service('saafImageLook', function () {
            var service = {};
            service.open = function (e) {
                var style = 'right:50%';

                var winWidth = ($(window).width() / 2)

                if (e.clientX < winWidth) {
                    style = 'left:50%'
                }
                if ($(e.target).attr('src') != null && $.trim($(e.target).attr('src')) != '') {
                    $('body').prepend("<div id='saafImageLook' class='text-center saaf-img-look'  style=\"" + style + ";\" >" +
                        '<img  src=' + $(e.target).attr('src') + ' style="max-width:100%;max-height: 70%">' +
                        '<div>大图速览（任意图片右键，即可保存）</div>' +
                        "</div>");
                }
            }
            service.close = function () {
                $('#saafImageLook').remove();
            }
            return service;
        })

        /**
         * 条形码
         *       <input saaf-barcode="{{dataValue.shortCode}}" data-bar-width="3"  data-bar-height="100">
         *       saaf-barcode： 条形码参数也可以写为     saaf-barcode="123456098765"
         *       data-bar-width="3" ：宽带为3个像素
         *        data-bar-height="100" ：高度为100像素
         */
        .directive("saafBarcode", function () {
            return {
                template: '<div></div>',
                scope: {},
                replace: true,
                restrict: 'AE',
                link: function ($scope, $element, $attr, $ctrl) {
                    require('jquery-barcode');

                    $scope.$watch(function () {
                        return $attr.saafBarcode;
                    }, function (newVal, oldVal) {
                        var setting = {
                            barWidth: $attr.barWidth ? ($.trim($attr.barWidth) != '' ? $attr.barWidth : 2) : 2,
                            output: "css",
                            barHeight: ( $attr.barHeight ? $attr.barHeight : 60)
                        };
                        console.log(newVal);
                        $($element).html("").barcode($attr.saafBarcode, "code128", setting);
                    });

                }
            };
        })

        //saaf lov控制器 <!--add by luofu-->
        .controller('saafLovMultipleController', ['$timeout', '$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', function saafTableController($timeout, $scope, $parse, $filter, $attrs, JS, httpServer) {


            $scope.$watch(function () {
                return $attrs.saafLovOption;
            }, function (newVal, oldVal) {
                $scope.options = $.extend({}, eval("(" + $attrs.saafLovOption + ")"));
                $scope.params = $scope.options.params;
            });


//全选
            $scope.respSelectedAll = function (flag, datas) {
                if (flag) {
                    $.each(datas, function (n, row) {
                            var index = $.inArray(row[$scope.options.rowId], $scope.respSelectData);
                            if (index < 0) {
                                $scope.respSelectData.push(row[$scope.options.rowId]);
                                $scope.respSelectRowData.push(row);
                            }
                        }
                    )
                } else {
                    $.each(datas, function (n, row) {
                            var index = $.inArray(row[$scope.options.rowId], $scope.respSelectData);
                            if (index >= 0) {

                                $scope.respSelectRowData.splice(index, 1);
                                $scope.respSelectData.splice(index, 1);
                            }
                        }
                    )
                }

            }
//add by luofenwu 初始化时勾选曾选记录
            $scope.initLovData = function (id, row) {
                if (!$attrs.saafHasSelected) {
                    // console.log("not selected data before this time")
                    return;
                }
                var hasArr = $attrs.saafHasSelected.split(",");
                // console.log("id=" + id + "     hasArr=" + hasArr + "  index===" + $.inArray("" + id, hasArr))
                var index = $.inArray("" + id, hasArr);
                //console.log("避免重复添加----------")
                //console.log( -1==$.inArray(row[$scope.options.rowId],$scope.respSelectData))
                if (index >= 0 && -1 == $.inArray(row[$scope.options.rowId], $scope.respSelectData)) {
                    $scope.respSelectData.push(row[$scope.options.rowId]);
                    $scope.respSelectRowData.push(row);
                }
                console.log(1234);
            }

            //多选框点击选中
            $scope.respSelectedAction = function (event, id, row) {
                if (!$scope.respIsSelected(id)) {
                    $scope.respSelectData.push(row[$scope.options.rowId]);
                    $scope.respSelectRowData.push(row);

                }
                else {
                    var index = $.inArray(id, $scope.respSelectData);
                    //splice 而不是 slice
                    $scope.respSelectRowData.splice(index, 1);
                    $scope.respSelectData.splice(index, 1);
                }
            }
            //判断多选框  是否被选中
            $scope.respIsSelected = function (id) {
                var index = $.inArray(id, $scope.respSelectData);
                if (index < 0) return false;
                else return true;
            }
            //保存
            $scope.saveResp = function () {
                var returnRows = JSON.stringify($scope.respSelectRowData);
                var returnIds = JSON.stringify($scope.respSelectData);
                var doString = $scope.options.returnFunction.replace('selectRowData', returnRows).replace('selectIds', returnIds);
                $scope.$parent.$eval(doString);
                $('#lovMultipleModal').modal('toggle');
            }
            //清除选择
            $scope.clearAction = function () {
                $scope.respSelectData = [];
                $scope.respSelectRowData = [];
            }
        }])


    //saaf  多选lov指令 <!--add by luofu-->
    /*       <button class="btn btn-default" saaf-multiole-lov
     data-resp-select-data="selectIds" //选择的行id同步到$scope.selectIds
     data-resp-select-row-data="selectRows" //选择的行同步到$scope.selectRows
     saaf-lov-option="{
     clearBtn:true, //显示清除按钮
     unClearFlag:false,//每次打开lov是否自动清除旧的选择
     dataUrl:'queryRespList', //查询地址
     params:{platformCode:'AGENT'}, //查询预设参数
     rowId:'responsibilityId', //查询所得表的主键
     searchLabel1:'角色名称',//搜索标题
     searchColName1:'varResponsibilityName',//搜索对应的列名
     label1:'角色名称', //表格的第一列标题
     colName1:'responsibilityName',  //表格的第一列
     returnFunction:'returnFunction(selectRowData,selectIds)' //点击确定，将会执行$scope.returnFunction(selectRowData);
     }">
     <i  class="fa fa-plus"  ></i>新增
     </button>*/
    module.directive('saafMultioleLov', function ($http, $compile) {


        return {
            template: '<div ng-transclude ></div>',
            scope: {},
            transclude: true,
            controller: 'saafLovMultipleController',
            restrict: 'AE',
            link: function ($scope, $element, $attrs, $ctrl) {
                $scope.attrs = $attrs;
                $($element).click(function (e) {
                    $scope.showLov();
                });


                $scope.showLov = function () {
                    var lovUrl = "js/directives/html/saafLovMultiple.html";

                    function isArray(o) {
                        return Object.prototype.toString.call(o) == '[object Array]';
                    }

                    $scope.respSelectData = [];
                    $scope.respSelectRowData = [];


                    $http.get(lovUrl)
                        .success(function (response) {
                            $('body').append(response);
                            cn
                            $('#lovMultipleModal').find('table').attr('data-url', $scope.options.dataUrl);
                            $('#lovMultipleModal').modal('toggle');
                            $compile($('#lovMultipleModal'))($scope);

                            $('#lovMultipleModal').on('hidden.bs.modal', function () {

                                $('#lovMultipleModal').remove();


                            })
                        });
                }

            }

        }
    })
    //saaf  多选lov指令 <!--add by luofu-->
    /*       <button class="btn btn-default" saaf-multiole-lov
     data-resp-select-data="selectIds" //选择的行id同步到$scope.selectIds
     data-resp-select-row-data="selectRows" //选择的行同步到$scope.selectRows
     saaf-lov-option="{
     clearBtn:true, //显示清除按钮
     unClearFlag:false,//每次打开lov是否自动清除旧的选择
     dataUrl:'queryRespList', //查询地址
     params:{platformCode:'AGENT'}, //查询预设参数
     rowId:'responsibilityId', //查询所得表的主键
     searchLabel1:'角色名称',//搜索标题
     searchColName1:'varResponsibilityName',//搜索对应的列名
     label1:'角色名称', //表格的第一列标题
     colName1:'responsibilityName',  //表格的第一列
     returnFunction:'returnFunction(selectRowData,selectIds)' //点击确定，将会执行$scope.returnFunction(selectRowData);
     }">
     <i  class="fa fa-plus"  ></i>新增
     </button>*/
    module.directive('saafMultioleLovNew', function ($http, $compile) {


        return {
            template: '<div ng-transclude ></div>',
            scope: {
                respSelectData: '=',
                respSelectRowData: '='
            },
            transclude: true,
            controller: 'saafLovMultipleController',
            restrict: 'AE',
            link: function ($scope, $element, $attrs, $ctrl) {
                $scope.attrs = $attrs;
                $($element).click(function (e) {
                    $scope.showLov();
                });


                $scope.showLov = function () {
                    var lovUrl = "js/directives/html/saafLovMultiple.html";

                    function isArray(o) {
                        return Object.prototype.toString.call(o) == '[object Array]';
                    }

                    if (!isArray($scope.respSelectData)) {
                        $scope.respSelectData = [];
                        $scope.respSelectRowData = [];
                    }

                    if (!$scope.options.unClearFlag) {
                        $scope.respSelectData = [];
                        $scope.respSelectRowData = [];
                    }

                    $http.get(lovUrl)
                        .success(function (response) {
                            $('body').append(response);
                            $('#lovMultipleModal').find('table').attr('data-url', $scope.options.dataUrl);
                            $('#lovMultipleModal').modal('toggle');
                            $compile($('#lovMultipleModal'))($scope);

                            $('#lovMultipleModal').on('hidden.bs.modal', function () {

                                $('#lovMultipleModal').remove();


                            })
                        });
                }

            }

        }
    })


    /**
     * 使用xls导入解析模块请在controler头引入  例如
     * define(["app",'XLSX'], function (app,XLSX) {
       app.controller('inst_commission_standardCtrl', function ($state,
     */
    module.directive('saafXlsImport', function ($http, $compile) {


        return {
            scope: {
                returnAction: "&"
            },
            restrict: 'AE',
            link: function ($scope, $element, $attrs, $ctrl) {


                function handleFile(e) {


                    var rABS = typeof FileReader !== "undefined" && typeof FileReader.prototype !== "undefined" && typeof FileReader.prototype.readAsBinaryString !== "undefined";

                    var files = e.target.files;
                    var i, f;
                    i = 0;
                    for (f = files[i]; i != files.length; ++i) {
                        var reader = new FileReader();
                        var name = f.name;
                        reader.onload = function (e) {
                            var data = e.target.result;
                            if (rABS) {
                                workbook = XLSX.read(data, {type: 'binary'});
                            } else {
                                var arr = fixdata(data);
                                workbook = XLSX.read(btoa(arr), {type: 'base64'});
                            }

                            process_wb(workbook);
                            /* DO SOMETHING WITH workbook HERE */
                        };
                        if (rABS) reader.readAsBinaryString(f);
                        else reader.readAsArrayBuffer(f);

                    }
                }

                /**
                 * 兼容旧版ie代码
                 * 不用可注释掉
                 */
                /*     function ajaxSend(){




                 var url = "AUX-CMS测试问题跟踪清单.xlsx";

                 var oReq;
                 if(window.XMLHttpRequest) oReq = new XMLHttpRequest();
                 else if(window.ActiveXObject) oReq = new ActiveXObject('MSXML2.XMLHTTP.3.0');
                 else throw "XHR unavailable for your browser";



                 oReq.open("GET", url, true);

                 if(typeof Uint8Array !== 'undefined') {
                 oReq.responseType = "arraybuffer";
                 oReq.onload = function(e) {
                 if(typeof console !== 'undefined') console.log("onload", new Date());
                 var arraybuffer = oReq.response;
                 var data = new Uint8Array(arraybuffer);
                 var arr = new Array();
                 for(var i = 0; i != data.length; ++i) arr[i] = String.fromCharCode(data[i]);
                 var wb = XLSX.read(arr.join(""), {type:"binary"});
                 process_wb(wb);
                 };
                 } else {
                 oReq.setRequestHeader("Accept-Charset", "x-user-defined");
                 oReq.onreadystatechange = function() { if(oReq.readyState == 4 && oReq.status == 200) {
                 var ff = convertResponseBodyToText(oReq.responseBody);
                 if(typeof console !== 'undefined') console.log("onload", new Date());
                 var wb = XLSX.read(ff, {type:"binary"});
                 process_wb(wb);
                 } };
                 }

                 oReq.send();
                 }*/


                function to_json(workbook) {
                    var result = {};
                    workbook.SheetNames.forEach(function (sheetName) {
                        var roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                        if (roa.length > 0) {
                            result[sheetName] = roa;
                        }
                    });
                    return result;
                }

                function process_wb(wb) {
                    var output = "";
                    output = JSON.stringify(to_json(wb), 2, 2);

                    var doString = $attrs.returnAction.replace('xlsJson', output);
                    $scope.$parent.$eval(doString);

                    // $scope.returnAction(to_json(wb));
                    /* if (out.innerText === undefined) out.textContent = output;
                     else out.innerText = output;
                     if (typeof console !== 'undefined') console.log("output", new Date());*/
                }

                function fixdata(data) {
                    var o = "", l = 0, w = 10240;
                    for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
                    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
                    return o;
                }

                $($element).change(handleFile);
                // ajaxSend();

            }

        }
    })
    /**
     * 使用xls导入解析模块请在controler头引入  例如
     * define(["app",'XLSX'], function (app,XLSX) {
       app.controller('inst_commission_standardCtrl', function ($state,

       前台使用用例如下：
         <button type="button" class="btn btn-default" saaf-xls-import-component data-list-options="{
                    instCode:{headName:'中心编码',require:true},//设置列信息instCode对应xls表的中心编码列，该选项为必填项
                    itemCode:{headName:'内机编码',require:true},//设置列信息itemCode对应xls表的内机编码，该选项为必填项
                    startDateActive:{headName:'生效日期',require:true},
                    endDateActive:{headName:'失效日期'},
                    enabledFlag:{headName:'是否生效',require:true},
                    commissionStandard:{headName:'标准',require:true}
                    }" data-url="instCommissionStandardServicexlssave" //将xls转为json后，提交的后台处理接口
                     data-info="{date:'{{params.var_min_startDateActive}}'}"  //设置上传所携带的信息，比如头表id等等
                     data-after-action="dataTable.getData()" //执行成功后，或者失败，即后台不再返回  res.status == "ERR_IMPORT" 的状态，则该执行该方法
                    data-message="‘中心名称’与‘机型描述’导入时，可以不用填写，服务器可自动查询出对应的名称；是否生效不填则默认为Y。"   >//设置用户提示信息
                        <i class="fa fa-upload"></i>导入
                    </button>


           传给后台的报文如下
{
    "data":[
        {
            "ERR_MESSAGE":"",
            "ROW_NUM":"1",//对应到xls的第几行，该信息需要后台也返回
            "instCode":"108",
            "itemCode":"11101072000236",
            "startDateActive":"2016-12-02",
            "endDateActive":"2016-12-02",
            "enabledFlag":"Y",
            "commissionStandard":"50.1"
        }
    ]
}

    后台返回给前台的报文如下
   返回xls导入错误的提示
{
    "data":[
        {
            "ERR_MESSAGE":"数据与已有期间重叠. ", //错误信息
            "ROW_NUM":"1" //对应的是xls的第几行
        }
    ],
    "status":"ERR_IMPORT"  //表示改成xls导入失败
}
返回xls导入成功的提示
{
    "msg":"保存成功行数为1行！",
    "status":"S"
}

返回系统异常未知异常的提示
{
    "msg":"数据异常，请下载xls模板，重新导入！",
    "status":"E"
}

     */
    module.directive('saafXlsImportComponent', function ($http, $compile, $timeout, httpServer, URLAPI, SIEJS) {


        return {
            scope: {},
            restrict: 'AE',
            link: function ($scope, $element, $attrs, $ctrl) {
                $scope.dataTable = $scope.dataTable ? $scope.dataTable : {};
                $scope.$attrs = $attrs;
                var log = console.log.bind(console);
                //2020.2.1 hmb修改,动态添加listOptions信息
                /*var listOption = eval("(" + $attrs.listOptions + ")");
                var headNames = ['错误信息', '序号'];
                var jsonNames = ['ERR_MESSAGE', 'ROW_NUM'];
                var isRequire = [false, false];


                $.each(listOption, function (name, value) {
                    headNames.push(value.headName);
                    isRequire.push(value.require ? value.require : false);
                    jsonNames.push(name);
                });

                $scope.dataTable.headNames = headNames;
                $scope.dataTable.jsonNames = jsonNames;
                $scope.dataTable.isRequire = isRequire;*/
                var headNames = [];
                var jsonNames = [];
                var isRequire = [];

                $scope.changOptions= function(listOptions){
                    headNames = ['错误信息', '序号'];
                    jsonNames = ['ERR_MESSAGE', 'ROW_NUM'];
                    isRequire = [false, false];
                    $.each(listOptions, function (name, value) {
                        headNames.push(value.headName);
                        isRequire.push(value.require ? value.require : false);
                        jsonNames.push(name);
                    });

                    $scope.dataTable.headNames = headNames;
                    $scope.dataTable.jsonNames = jsonNames;
                    $scope.dataTable.isRequire = isRequire;
                }

                var listOption = eval("(" + $attrs.listOptions + ")");
                $scope.changOptions(listOption);

                $($element).click(function (e) {
                    //console.log(e.target)
                    var dataType = $(e.target).attr("data-type");
                    if (dataType !=undefined && dataType=='changOptions') {
                        $scope.dataTable.headNames = [];
                        $scope.dataTable.jsonNames = [];
                        $scope.dataTable.isRequire = [];
                        var listOptions = eval("(" + $(e.target).attr("data-list-options") + ")");
                        $scope.changOptions(listOptions);
                    }

                    $scope.showLov();
                });
                $scope.showLov = function () {
                    var lovUrl = "js/directives/html/saafXlsImport.html"
                    $http.get(lovUrl)
                        .success(function (response) {
                            $('body').append(response);
                            $("#saafxlsimport").change(handleFile);
                            if ($scope.dataTable) {
                                $scope.dataTable.data = null;
                            }
                            $('#saafXlsModal').modal('toggle');
                            $compile($('#saafXlsModal'))($scope);
                            $('#saafXlsModal').on('hidden.bs.modal', function () {
                                $('#saafXlsModal').remove();
                            })

                        });
                }


                function handleFile(e) {

                    $('#saafLoading').show();
                    window.setTimeout(handleFile2, 100);
                    // handleFile2();
                    function handleFile2() {
                        var rABS = typeof FileReader !== "undefined" && typeof FileReader.prototype !== "undefined" && typeof FileReader.prototype.readAsBinaryString !== "undefined";

                        var files = e.target.files;
                        var i, f;
                        i = 0;
                        for (f = files[i]; i != files.length; ++i) {
                            var reader = new FileReader();
                            var name = f.name;
                            reader.onload = function (e) {
                                var data = e.target.result;
                                if (rABS) {
                                    workbook = XLSX.read(data, {type: 'binary'});
                                } else {
                                    var arr = fixdata(data);
                                    workbook = XLSX.read(btoa(arr), {type: 'base64'});
                                }

                                process_wb(workbook);
                                /* DO SOMETHING WITH workbook HERE */
                            };
                            if (rABS) reader.readAsBinaryString(f);
                            else reader.readAsArrayBuffer(f);

                        }
                    }


                }

                function to_json(workbook) {
                    var result = {};
                    workbook.SheetNames.forEach(function (sheetName) {
                        var roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                        if (roa.length > 0) {
                            result[sheetName] = roa;
                        }
                    });

                    return result;
                }

                function process_wb(wb) {

                    var jsonwb = to_json(wb);

                    var sheelJson = [];
                    for (var name in jsonwb) {
                        sheelJson = jsonwb[name]
                    }
                    var tableJson = [];
                    $.each(sheelJson, function (n, obj) {

                        var row = {};
                        var effectiveDataSign = false;
                        for (var i = 0; i < jsonNames.length; i++) {
                            row[jsonNames[i]] = obj[headNames[i]];
                            if(row[jsonNames[i]]!==undefined){
                                effectiveDataSign = true;
                            }
                        }
                        row.ROW_NUM = obj.__rowNum__;
                        if(effectiveDataSign === true) {
                            tableJson.push(row);
                        }

                    });


                    $scope.dataTable.data = tableJson;

                    $scope.$apply();
                    dataToHtml($scope.dataTable.data);
                    $('#saafLoading').hide();

                    /*
                     var doString = $attrs.returnAction.replace('xlsJson', output);
                     $scope.$parent.$eval(doString);*/

                    // $scope.returnAction(to_json(wb));
                    /* if (out.innerText === undefined) out.textContent = output;
                     else out.innerText = output;
                     if (typeof console !== 'undefined') console.log("output", new Date());*/
                }

                function dataToHtml(data, flag) {
                    var tdNoInput = '<td      class="table-input-td w30" data-rownum data-rowname >value</td>';
                    var td = '<td   data-rownum data-rowname   class="table-input-td "><input style="min-width: 50px" class="form-control" required value ></td>';
                    var td_ERR_MESSAGE = '   <td data-rownum data-rowname  style="max-width: 200px" class="red"  title="titleMessage"><div style="max-width: 200px" class="ellipsis" >value</div></td>';
                    var tableBody = "";
                    $.each(data, function (n, row) {
                        var tr = "<tr>";
                        var ii = 0;
                        if (flag == 'err') {
                            if (row.ERR_MESSAGE == null || $.trim(row.ERR_MESSAGE) == '')
                                return
                        }
                        $.each(row, function (name, obj) {

                            if (name == 'ROW_NUM') {

                                tr += tdNoInput.replace("value", obj != null ? obj : "").replace("data-rownum", 'data-rownum="' + row.ROW_NUM + '"').replace("data-rowname", 'data-rowname="' + name + '"');

                            } else if (name == 'ERR_MESSAGE') {

                                tr += td_ERR_MESSAGE.replace('titleMessage', obj != null ? obj : "").replace("value", obj != null ? obj : "").replace("data-rownum", 'data-rownum="' + row.ROW_NUM + '"').replace("data-rowname", 'data-rowname="' + name + '"');

                            }
                            else {
                                tr += td.replace("value", 'value="' + (obj != null ? obj : "") + '"').replace("required", $scope.dataTable.isRequire[ii] ? "required" : "").replace("data-rownum", 'data-rownum="' + row.ROW_NUM + '"').replace("data-rowname", 'data-rowname="' + name + '"');

                            }
                            ii++;

                        })
                        tr += "</tr>";
                        tableBody += tr;
                    })

                    var tbody = $('#saaf_xls_import_tbody');
                    tbody.html(tableBody);
                    // $compile( tbody)($scope)

                }

                function htmlToData() {

                    var tbody = $('#saaf_xls_import_tbody >tr').each(function (n) {

                        $(this).find('td').each(function (h) {
                            var ii = $(this).attr("data-rownum");
                            var rowname = $(this).attr("data-rowname");

                            if (rowname == null) return;
                            if (rowname == 'ERR_MESSAGE' || rowname == 'ROW_NUM') {
                                if($scope.dataTable.data[0].ROW_NUM===1) {
                                    $scope.dataTable.data[ii - 1][rowname] = $(this).text();
                                }
                                else {
                                    $scope.dataTable.data[ii-$scope.dataTable.data[0].ROW_NUM][rowname] = $(this).text();
                                }
                            }
                            else {
                                if($scope.dataTable.data[0].ROW_NUM===1) {
                                    $scope.dataTable.data[ii - 1][rowname] = $(this).find('input').val();
                                }
                                else {
                                    $scope.dataTable.data[ii - $scope.dataTable.data[0].ROW_NUM][rowname] = $(this).find('input').val();
                                }
                            }
                        });
                    });


                }

                function fixdata(data) {
                    var o = "", l = 0, w = 10240;
                    for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
                    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
                    return o;
                }

                $scope.upXls = function () {
                    htmlToData();
                    var upData = {};
                    upData.info = eval("(" + $attrs.info + ")");
                    upData.data = $scope.dataTable.data;
                    httpServer.post(URLAPI[$attrs.url], {
                        'params': JSON.stringify(upData),
                        timeout: 60 * 10
                    }, function (res) {

                        if (res.status == "ERR_IMPORT") {
                            $.each($scope.dataTable.data, function (n, row) {
                                row.ERR_MESSAGE = null;

                            })

                            $.each(res.data, function (n, row) {
                                if($scope.dataTable.data[0].ROW_NUM===1) {
                                    $scope.dataTable.data[row.ROW_NUM - 1].ERR_MESSAGE = row.ERR_MESSAGE;
                                }
                                else {
                                    $scope.dataTable.data[row.ROW_NUM - $scope.dataTable.data[0].ROW_NUM].ERR_MESSAGE = row.ERR_MESSAGE;

                                }
                            })
                            $scope.selectFlag = 'err';
                            dataToHtml($scope.dataTable.data, 'err');
                        }
                        else {
                            //SIEJS.alertByRes(res);
                            if(res.status==='M'){
                                SIEJS.alert(res.msg,'error','确定');
                            }
                            else {
                                SIEJS.alert(res.msg);
                            }
                            $('#saafXlsModal').modal('toggle');
                            $scope.$parent.$eval($attrs.afterAction);
                        }
                    });

                }
                $scope.changeSee = function () {
                    htmlToData();
                    dataToHtml($scope.dataTable.data, $scope.selectFlag);
                }
            }
        }
    })

        .controller('saafValueSetLovController', ['$timeout', '$http', '$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', function saafTableController($timeout, $http, $scope, $parse, $filter, $attrs, JS, httpServer, URLAPI) {
            $scope.$watch(function () {
                return $attrs.saafLovOption;
            }, function (newVal, oldVal) {
                $scope.options = $.extend({}, eval("(" + $attrs.saafLovOption + ")"));
                $scope.params = $scope.options.params;
                $scope.params.queryParams = {}
            });

            $scope.init = function (item) {
                for (var i = 0; i < $scope.selectRowData.length; i++) {
                    if ($scope.getSubscriptValue(item) >= 0) {
                        item.checked = true;
                    }
                }
                angular.forEach($scope.hasArr, function (v, k) {
                    if (v == item[$scope.flexValueFlag] && !($scope.getSubscriptValue(item) >= 0)) {
                        item.checked = true;
                        $scope.selectRowData.push(item);
                    }
                })
            }

            $scope.alert = function (con, type, btn) {
                JS.alert(con, type, btn)
            }

            $scope.submitQueryParams = function () {
                $scope.setParams();
                $scope.dataTable.getData();
            }

            //组建筛选条件
            $scope.setParams = function () {
                $scope.params.queryParams = {};
                $scope.paramsStr = '{';
                var paramCol = '';
                var j = 0;
                for (var i = 0; i < $scope.paramsObjArr.length; i++) {
                    paramCol = $scope.getParamObj($scope.paramsObjArr[i]);
                    if (paramCol != '') {
                        j++;
                        paramCol += ',';
                    }
                    $scope.paramsStr += paramCol;
                }
                if (j > 0) {
                    $scope.paramsStr = $scope.paramsStr.substr(0, $scope.paramsStr.length - 1);
                }
                $scope.paramsStr += '}';
                $scope.params.queryParams = JSON.parse($scope.paramsStr);
            }

            $scope.getParamObj = function (columnObj) {
                var paramObj = '';
                if (columnObj.dynamicParamFlag == 'Y') {
                    //动态参数字段只能充当参数条件，并且需要在sql中配置命名参数，并以default_var_XXX_开头后面接字段名称，例如：default_var_min_column_name、default_var_equal_column_name
                    if (columnObj.rangeFlag == 'Y') {
                        if (columnObj.conditionDefaultValue) {
                            paramObj += '"default_var_min_' + columnObj.columnName + '":"' + columnObj.conditionDefaultValue + '"';
                        } else {
                            paramObj += '"default_var_min_' + columnObj.columnName + '":null';
                        }
                        if (paramObj != '') {
                            paramObj += ',';
                        }
                        if (columnObj.conditionDefaultValueTo) {
                            paramObj += '"default_var_max_' + columnObj.columnName + '":"' + columnObj.conditionDefaultValueTo + '"';
                        } else {
                            paramObj += '"default_var_max_' + columnObj.columnName + '":null';
                        }
                    } else {
                        if (columnObj.conditionDefaultValue) {
                            paramObj = '"default_' + columnObj.conditionExpression + columnObj.columnName + '":"' + columnObj.conditionDefaultValue + '"';
                        } else {
                            paramObj = '"default_' + columnObj.conditionExpression + columnObj.columnName + '":null';
                        }
                    }
                    //console.log(paramObj);
                } else {
                    if (columnObj.rangeFlag == 'Y') {
                        if (columnObj.conditionDefaultValue) {
                            paramObj += '"var_min_' + columnObj.columnName + '":"' + columnObj.conditionDefaultValue + '"';
                        }
                        if (columnObj.conditionDefaultValueTo) {
                            if (paramObj != '') {
                                paramObj += ',';
                            }
                            paramObj += '"var_max_' + columnObj.columnName + '":"' + columnObj.conditionDefaultValueTo + '"';
                        }
                    } else {
                        if (columnObj.conditionDefaultValue) {
                            paramObj = '"' + columnObj.conditionExpression + columnObj.columnName + '":"' + columnObj.conditionDefaultValue + '"';
                        }
                    }
                }
                return paramObj;
            }

            //重置筛选条件
            $scope.initQuery = function () {
                $scope.getValueSetColumn();
                $scope.params.queryParams = {};
                $scope.dataTable.getData();
            }

            $scope.dataTable = new Object();
            $scope.params = new Object();
            $scope.valueSetHeader = new Object();
            $scope.valueSetColumn = new Array();
            $scope.paramsObjArr = new Array();
            $scope.flexValueFlag = new String();
            $scope.formValueSet = new Object();//广播出去的对象

            $scope.getValueSetHeader = function (fn) {
                if ($scope.params.querySetNum) {
                    httpServer.post(URLAPI.queryDynamicQueryHeader, {
                        params: JSON.stringify({
                            querySetNum: $scope.params.querySetNum,
                            setType: "SINGLE"
                        }),
                        pageRows: 1,
                        pageIndex: 1
                    }, function (res) {
                        console.log(res);
                        $scope.valueSetHeader = res.data[0];
                        fn(res.data[0]);
                    }, function (error) {
                        console.error(error);
                    })
                } else {
                    JS.alert('未获取到值集编码', 'error', '确定');
                    return;
                }

            }

            $scope.getValueSetColumn = function () {
                $scope.valueSetColumn = [];
                $scope.paramsObjArr = [];
                var val = new Object();
                httpServer.post(URLAPI.queryDynamicQueryLine, {
                    params: JSON.stringify({
                        querySetNum: $scope.params.querySetNum
                    }),
                    pageRows: -1,
                    pageIndex: 1
                }, function (res) {
                    for (var i = 0; i < res.data.length; i++) {
                        val = res.data[i];
                        //有效并显示
                        if ('Y' == val.enabledFlag && 'Y' == val.displayFlag) {
                            $scope.valueSetColumn.push(val);
                            //作为搜索字段
                            if ('Y' == val.conditionFlag) {
                                $scope.paramsObjArr.push(val);
                            }
                            //应用值
                            if ('Y' == val.flexValueFlag) {
                                $scope.flexValueFlag = 'column' + (i + 1);
                            }
                        }
                    }
                    $scope.valueSetTitle = res.data;
                }, function (error) {
                    console.error(error);
                })
            }

            $scope.splitHasCon = function (saafHasSelected) {
                if (saafHasSelected.length < 1) {
                    return;
                }
                $scope.hasArr = saafHasSelected.split(",");
            }

            $scope.selectRow = function (obj) {
                if (!obj.hasOwnProperty('checked')) {
                    obj.checked = true;
                    $scope.selectRowData.push(obj);
                } else {
                    obj.checked = obj.checked ? false : true;
                    if (obj.checked) {
                        $scope.selectRowData.push(obj);
                    } else {
                        $scope.selectRowData.splice($scope.getSubscriptValue(obj), 1);
                    }
                }
            }

            $scope.getSubscriptValue = function (obj) {
                for (var i = 0; i < $scope.selectRowData.length; i++) {
                    if (obj[$scope.flexValueFlag] == $scope.selectRowData[i][$scope.flexValueFlag]) {
                        return i;
                    }
                }
            }

            $scope.submitValueSet = function () {
                $scope.formValueSet.selectRowData = $scope.selectRowData;
                $scope.formValueSet.index = $scope.options.index;
                $scope.formValueSet.getColumn = $scope.flexValueFlag;
                $scope.$emit('formValueSet', JSON.stringify($scope.formValueSet));
                $('#lovValueSetModal').modal('hide');
                $timeout(function () {
                    $('#lovValueSetModal').remove();
                }, 1000);
            }
        }])

    module.directive('saafValueSetLov', function ($http, $compile) {
        return {
            template: '<div ng-transclude ></div>',
            scope: {},
            transclude: true,
            controller: 'saafValueSetLovController',
            restrict: 'AE',
            link: function ($scope, $element, $attrs, $ctrl) {
                $scope.attrs = $attrs;
                $($element).click(function (e) {
                    $scope.showLov();
                });

                $scope.showLov = function () {
                    var lovUrl = '';
                    $scope.selectRowData = [];
                    $scope.hasArr = [];
                    $scope.formValueSet = {};
                    $scope.params.queryParams = {};
                    $scope.splitHasCon($attrs.saafHasSelected);
                    $scope.getValueSetHeader(function (res) {
                        if (res) {
                            if (res.flexValuesType == 'T') {
                                lovUrl = "js/directives/html/saafLovValueSet.html";
                            } else {
                                $scope.alert('未支持值集类型', 'error', '确定')
                                return;
                            }
                            $scope.getValueSetColumn();
                            $http.get(lovUrl).success(function (response) {
                                $('body').append(response);
                                $('#lovValueSetModal').find('table').attr('data-url', $scope.options.dataUrl);
                                $('#lovValueSetModal').modal('toggle');
                                $compile($('#lovValueSetModal'))($scope);
                                $('#lovValueSetModal').on('hidden.bs.modal', function () {
                                    $('#lovValueSetModal').remove();
                                })
                            });
                        }
                    });
                }
            }
        }
    })

    module.directive('contextmenuAction', function ($rootScope) {
        return {
            restrict: 'AE',
            scope: {
                url: '@url',
                allRuls: '@allRul'
            },
            link: function ($scope, $element, $attrs, $ctrl) {
                var options = {
                    items: [
                        {
                            text: '关闭', onclick: function () {
                            $scope.$apply(function () {
                                $rootScope.saafRemoveTab.removeByHref($scope.url)
                            })
                        }
                        },
                        {
                            text: '关闭其他', onclick: function () {
                            $scope.$apply(function () {
                                angular.forEach(JSON.parse($scope.allRuls), function (v, k) {
                                    if (v.url != $scope.url) {
                                        $rootScope.saafRemoveTab.removeByHref(v.url)
                                    }
                                })
                            })
                        }
                        },
                        {
                            text: '关闭全部', onclick: function () {
                            $scope.$apply(function () {
                                angular.forEach(JSON.parse($scope.allRuls), function (v, k) {
                                    $rootScope.saafRemoveTab.removeByHref(v.url)
                                })
                            })
                        }
                        },
                    ]
                }
                $('.contextify').contextify(options);
            }
        }
    })

    // 文件大小 字节转对应单位
      .filter('fileSize', function ($sce) {
        return function (val) {
          if (!val) return;
          var kb = 1024;
          var mb = kb * 1024;
          var gb = mb * 1024;
          var size;

          if (val < kb) {
            size = '1 KB'
          } else if (val < mb && val > kb) {
            size = (val / kb).toFixed(2) + ' KB';
          } else if (val < gb && val > mb) {
            size = (val / mb).toFixed(2) + ' MB';
          } else if (val) {
            size = (val / gb).toFixed(2) + ' GB';
          }
          return size;
        }
      })
      // 文件格式icon
      .filter('fileIcon', function ($sce) {
        return function (val) {
          if (!val) return;
          var icon = '';
          val = val.replace(/\./g, '').toLowerCase();
          switch (val) {
            case 'doc':
            case 'docx':
            case 'rtf':
              icon = 'file-word-o text-brand-primary';
              break;
            case 'xls':
            case 'xlsx':
              icon = 'file-excel-o text-green';
              break;
            case 'ppt':
            case 'pptx':
              icon = 'file-powerpoint-o text-darkred';
              break;
            case 'pdf':
              icon = 'file-pdf-o text-darkorange';
              break;
            case 'txt':
              icon = 'file-text-o ';
              break;
            case 'jpg':
            case 'gif':
            case 'bmp':
            case 'png':
              icon = 'file-photo-o text-dodgerblue';
              break;
            case 'zip':
            case 'rar':
              icon = 'file-zip-o text-darkred';
              break;
            case 'xml':
            case 'js':
            case 'html':
            case 'htm':
            case 'xhtml':
              icon = 'file-code-o';
              break;
            default:
              icon = 'file-o ';
              break;
          }


          return $sce.trustAsHtml('<span class="fa fa-' + icon + '" style="font-size: 18px;"></span>');
        }
      })

      //时间戳转日期字符串
      .filter('timeToDate', function () {
        return function (time, format_type) {
          time = time ? time : new Date().getTime();  //time为毫秒
          if (time.toString().length === 10) {
            time = time * 1000;
          }

          var option = 'year,month,day,hour,minute,second';    //年月日选项
          var date = new Date(time);
          var dateObj = {
            year: date.getFullYear(),
            month: date.getMonth() + 1,
            day: date.getDate(),
            hour: date.getHours(),
            minute: date.getMinutes(),
            second: date.getSeconds()
          };
          if (angular.isString(option)) {
            option = option.split(',');
          }

          var date_arr = [];
          var time_arr = [];
          angular.forEach(option, function (item) {
            if (dateObj[item].toString().length === 1) {
              dateObj[item] = '0' + dateObj[item];
            }
            if (item === 'year' || item === 'month' || item === 'day') {
              date_arr.push(dateObj[item]);
            } else {
              time_arr.push(dateObj[item]);
            }
          });

          if (format_type === 'date') {           //返回年月日
            return date_arr.join('-')
          } else if (format_type === 'time') {    //返回时分秒
            return time_arr.join('-')
          } else if (format_type === 'obj') {     //返回对象，方便自定义格式
            return dateObj;
          } else {    //返回最常用格式
            return date_arr.join('-') + ' ' + time_arr.join(':')
          }

        }
      })


  module.controller('saafFileController', function saafTableController($rootScope, $window, $timeout, $interval, $scope, $parse, $filter, $attrs, SIEJS, httpServer, URLAPI, $element, FileUploader,lookupCode) {


    // var setting = {};//设置上传参数
    // setting.url = URLAPI.saafCommonImageUp;
    // var uploader = $scope.uploader = new FileUploader(setting);

    if($scope.sourceId==undefined || $scope.sourceId=="" || $scope.sourceCode==undefined || $scope.sourceCode==""){
      $scope.fileData=[];
    }
    // else{
    //     //获取文件信息
    //     httpServer.getData(URLAPI.getVrFileUploadList, 'POST', {
    //         params: JSON.stringify({
    //             "sourceCode": $scope.sourceCode,
    //             "sourceId":$scope.sourceId
    //         }),
    //         pageIndex:1,
    //         pageRows:-1
    //     }, function (res) {
    //         $scope.fileData=res.data;
    //     }, function (error) {
    //         SIEJS.alert('查询失败', 'error', '确定');
    //     })
    // }

    // httpServer.post(URLAPI.queryLookupCode,  {
    //     params: JSON.stringify({
    //         "lookupType":"VR_FILE_SOURCE",
    //         "lookupCode": $scope.sourceCode
    //     })
    // }, function (res) {
    //     if(res.data.length>0){
    //         $scope.allowFileType=res.data[0].description;
    //     }else{
    //         $scope.allowFileType="";
    //     }
    // })

    $scope.lookupcode = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
    if (! $scope.lookupcode) {
      lookupCode(function (res) {
        $scope.lookupcode = $rootScope.lookupCode = res.data;
        setLookupCode();
      });
    } else {
      setLookupCode();
    }
    function setLookupCode() {
      for (var n = 0; n < $scope.lookupcode.length; n++) {
        var item = $scope.lookupcode[n];
        if (item.lookupType === "FILE_SOURCE" && item.lookupCode==$scope.sourceCode) {
          $scope.allowFileType=item.description;
          break;
        }else{
          $scope.allowFileType="";
        }
      }
    }

    //上传预告片
    var fileUploader = $scope.fileUploader = new FileUploader({
      url: URLAPI.imgUpload,
      autoUpload: true,
      removeAfterUpload: true,
      headers: {
        "Certificate": sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing'
      }
    });
    fileUploader.filters.push({//文件限制
      name: 'imageFilter',
      fn: function (item, options) {
        var fileTypeData=item.name.split(".");
        var type = '|' + fileTypeData[fileTypeData.length-1] + '|';
        if($scope.allowFileType==undefined || $scope.allowFileType=="" || $scope.allowFileType==null){
          return 1;
        }else{
          return $scope.allowFileType.indexOf(type) !== -1;
        }
      }
    });
    fileUploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
      SIEJS.alert('仅支持'+$scope.allowFileType+'文件格式！！', "error", "确定");
    };
    //上传完成后重置进度条
    fileUploader.onCompleteAll = function () {
      fileUploader.clearQueue();
    };
    fileUploader.onAfterAddingAll = function (items) {
      $scope.fileSize=parseFloat(items[0].file.size/1024).toFixed(2);//保留成M
    };
    //上传后调用的方法
    fileUploader.onCompleteItem = function (fileItem, res, status, headers) {
      // console.log(fileItem);
      console.log(res);
      if(status!=200){
        SIEJS.alert('上传失败', 'error', '确定');
        return ;
      }

      if(res.status!='S'){
        if(!res.msg) return ;
        SIEJS.alert(res.msg, 'error', '确定');
        return ;
      }
      if(sessionStorage[appName + '_successLoginInfo']==undefined || sessionStorage[appName + '_successLoginInfo']==null || sessionStorage[appName + '_successLoginInfo']==""){
        $scope.userName ="";
      }else {
        $scope.saafsuccessLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
        $scope.userName = $scope.saafsuccessLoginInfo.userName;
      }
      var now = new Date();
      $scope.fileData.push({
        "uploadId":"",
        "userFullName":$scope.userName,
        "fileName": res.data[0].fileName,
        "sourceFileName": res.data[0].fileName, // 文件名称
        "createdByUser": $scope.userName,   // 上传者
        "creationDate": $filter('timeToDate')(res.data[0].uploadDate),
        "fileAddress": res.data[0].accessPath,
        "fileType": res.data[0].fileType,
        "fileSize": $scope.fileSize,
        "sourceCode": $scope.sourceCode,
        "sourceId": $scope.sourceId,
        // "startDateActive": now.getFullYear() + '-' + ((now.getMonth() + 1) > 9 ? (now.getMonth() + 1) : '0' +
        //   (now.getMonth() + 1)) + '-' + (now.getDate() > 9 ? now.getDate() : '0' + now.getDate())
      })
      console.log($scope.fileData);
      if ($scope.saveFlag){
        console.log("我直接保存了啊！");
        httpServer.post(URLAPI.saveSaafFileUpload,  {
          params: JSON.stringify({
            "fileData": $scope.fileData
          })
        }, function (res) {
          if (res.status==='S') {
            $scope.fileData=res.data;
            SIEJS.alert('上传成功');
          } else {
            SIEJS.alert(res.msg, 'error', '确定');
          }
        }, function (error) {
          SIEJS.alert('上传失败', 'error', '确定');
        })
      }else {
        console.log("自己保存去吧！")
      }

    }

    $scope.delFileInfo = function (index) {
      SIEJS.confirm('删除附件','是否确定删除该附件','确定',function() {
        //如果没有id
        if($scope.fileData[index].uploadId==undefined || $scope.fileData[index].uploadId==""){
          $scope.fileData.splice(index,1);
        }else{
          httpServer.post(URLAPI.deleteSaafFileUpload,  {
            params: JSON.stringify({
              "uploadId": $scope.fileData[index].uploadId
            })
          }, function (res) {
            if (res.status==='S') {
              $scope.fileData.splice(index,1);
              SIEJS.alert('删除成功');
            } else {
              SIEJS.alert(res.msg, 'error', '确定');
            }
          }, function (error) {
            SIEJS.alert('删除失败', 'error', '确定');
          })
        }

      })
    }


  })
  module.directive('saafFile', function (FileUploader) {
    return {
      templateUrl: 'js/directives/html/file-tpl.html',
      scope: {
        fileData: '=',
        sourceCode:'=',
        sourceId:"=",
        saveFlag:"=",
        viewOnly:"=" //该类型只是判断是否为只读(true:只读,false:允许新增删除,addOnly:只允许新增)
      },
      replace: true,
      restrict: 'AE',
      controller: 'saafFileController',
      link: function ($scope, $element, $attrs, $ctrl) {
        $scope.attrs = $attrs;

        // 最大上传数
        $scope.maxUpload = $attrs.maxUpload ? $attrs.maxUpload * 1 : 0;
        // $scope.viewOnly = !$attrs.viewOnly ? 1 : $scope.viewOnly;
        $scope.userDelete = !$attrs.userDelete ? true : $scope.userDelete;
        $scope.label = $attrs.label || '附件上传';
        $scope.id = $attrs.id || $attrs.saafFile || 'saaafFile_' + new Date().getTime();
      }
    };
  })

    /**
     * 数字验证
     *
     */
    module.directive("stringFormat",function($parse, $timeout, $filter, SIEJS){
        return {
            require: 'ngModel',
            link: function($scope, $element, $attr, $modelCtrl) {

                $modelCtrl.$parsers.push(function (inputValue) {
                    var reg = /^[\d?]+$/;
                    var oldInputValue = $parse($attr.ngModel)($scope)
                    if(inputValue == ""){
                        return;
                    }else{
                        if(!reg.test(inputValue)){
                            inputValue = oldInputValue;
                            $modelCtrl.$setViewValue(inputValue);
                            $modelCtrl.$render();
                        }
                    }
                    return inputValue;
                });
            }
        };
    });

    return module;
})
;
