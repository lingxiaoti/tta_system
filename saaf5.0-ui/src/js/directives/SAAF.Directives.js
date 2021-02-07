define(['angular', 'app','jqueryUi'], function (angular, app,jqueryUi) {
    var module = angular.module('SAAF.Directives', []);
    module
        .directive('appFrame', function () {
            return {
                restrict: 'EA',
                replace: true,
                template: '',
                link: function (scope, element, Attr, controller) {

                }
            }
        })
        .directive('mainScrollStyle', function ($timeout) {
            return {
                restrict: 'EA',
                link: function (scope, element, Attr, controller) {
                    var w = $(window);
                    var _top = $(element).offset().top * 1;
                    var _h = w.innerHeight();
                    $(element).css("height", (_h - _top) + "px");
                    w.bind('resize', function () {
                        $(element).css("height", (w.innerHeight() - _top) + "px");
                        $timeout(function () {
                            scope.$apply();
                        }, 100)
                    });
                }
            }
        })
        .directive('mainScrollStyleM', function ($timeout) {
            return {
                restrict: 'EA',
                link: function (scope, element, Attr, controller) {
                    var w = $(window);
                    var _top = $(element).offset().top * 1;
                    var _h = w.innerHeight();
                    $(element).css("height", (_h - _top-50) + "px");
                    w.bind('resize', function () {
                        $(element).css("height", (w.innerHeight() - _top-50) + "px");
                        $timeout(function () {
                            scope.$apply();
                        }, 100)
                    });
                }
            }
        })
        .directive('menuScrollStyle', function ($timeout) {
            return {
                restrict: 'EA',
                link: function (scope, element, Attr, controller) {
                    var w = $(window);
                    var _top = $(element).offset().top * 1;
                    var _h = w.innerHeight();

                    $(element).css("height", (_h - _top) + "px");

                    w.bind('resize', function () {
                        $(element).css("height", (w.innerHeight() - _top) + "px");
                        $timeout(function () {
                            scope.$apply();
                        }, 100)

                    });
                }
            }
        })
        ///主菜单效果
        .directive('mainMenu', function ($compile, $timeout, $sce, httpServer, URLAPI, $rootScope, $state, SIEJS, tabAction, Base64) {
            return {
                restrict: 'EA',
                replace: true,
                template: '',
                link: function (scope, element, attr, controller) {
                    element.bind('mouseover', function () {
                        $timeout(function () {
                            $("#menuscop").getNiceScroll().resize();
                        })
                    });

                    if ($rootScope.userRespList.length === 0) {
                        SIEJS.alert('您还未有任何职责权限', "warning", null, '如您职责有改变，请退出重新登录');
                        return;
                    }
                    angular.element(document).ready(function () {
                        $rootScope.MenuTreeRoot = [];// 顶级菜单列表
                        scope.getMenu = function (pId) {
                            var p = {
                                isValid: true,
                                systemCode: appName
                            };
                            if (!$rootScope.currentResp.isAdmin) {
                                p.responsibilityId = $rootScope.currentResp.responsibilityId;
                                //p.systemCode = appName;
                            }

                            httpServer.post(URLAPI.menuListByReps,
                                {
                                    'params': JSON.stringify(p)
                                },
                                function (res) {
                                    if (res.data) {
                                        //scope.MenuTreeRoot = 0;///菜单根节点
                                        $rootScope.MenuTree = res.data; //树菜单的数据
                                        //$rootScope.saafButtonData = res.buttonData;
                                        res.data.map(function (item) {
                                            if (item.menuParentId === 0)
                                                $rootScope.MenuTreeRoot.push(item);
                                        });
                                        localStorage[appName + '_menuTree'] = JSON.stringify($rootScope.MenuTree);
                                        //localStorage[appName + '_saafButtonData'] = JSON.stringify($rootScope.saafButtonData);
                                        localStorage[appName + '_menuTreeRoot'] = JSON.stringify($rootScope.MenuTreeRoot);
                                    }
                                });
                        };

                        scope.getMenu(0);// 加载主菜单

                        // 当前 tabs菜单列表
                        if (sessionStorage[appName + '_headTab']) {
                            $rootScope.saafHeadTab = scope.$eval(Base64.decode(sessionStorage[appName + '_headTab']))
                        } else {
                            $rootScope.saafHeadTab = [];
                        }

                        scope.loadedMenu = []; // 已加载的子菜单节点
                        ///// 处理菜单点击效果
                        $(element).on("click", "li", function (e) {
                            var menuTyp = $(this).attr('data-menuType');
                            if (menuTyp === '20') {
                                // 功能菜单 则添加tabs与对应内容
                                tabAction($(this).attr('data-name'), $(this).attr('data-url'), $(this).attr('data-menuid'));

                            }
                            var _this = $(this);
                            var menuId = _this.attr('data-menuId') * 1;
                            scope.expand = function () {
                                $(element).find('li').removeClass('active');

                                if ($(_this).children('ul').length > 0) $(_this).addClass('active');

                                //如果子级还有菜单
                                if ($(_this).children("ul").find('li').length > 0) {
                                    $(_this).toggleClass("expand");

                                    if ($("body").hasClass('shrinkage-app')) {
                                        $(_this).siblings().removeClass('expand').children('ul').slideUp();
                                    }
                                    //$(".shrinkage-app").find().slideUp(); //　左栏被收缩时状态　处理ＵＩ显示
                                    $(_this).children("ul").stop(true, false).slideToggle();//stop很重要，避免动画重复
                                }


                                $(_this).children("a").children("i:eq(1)").toggleClass('fa-angle-down fa-angle-up');

                                var p = $(_this).parents("li").last();
                                p.addClass("active");
                                if (p.hasClass("expand")) {//处理父级
                                    p.addClass("active");
                                }
                                else {//处理当前
                                    $(_this).addClass("active");
                                }

                                $(element).find('li').removeClass('focus');
                                e.stopPropagation();
                            };
                            if (scope.loadedMenu.indexOf(menuId) > -1) {// 已加载过菜单
                                scope.expand()
                            } else {
                                var childNode = $('<ul class="sub-menu none "></ul>');
                                $rootScope.MenuTree.map(function (item) {
                                    if (item.menuParentId === menuId) {
                                        childNode.append('<li  data-name="' + $rootScope.$l(item.menuName) + '" data-url="' + item.htmlUrl + '" data-code="' + item.menuCode + '"  data-menuId="' + item.menuId + '" data-menuType="' + item.menuType + '">' +
                                            '<a href="javascript:" ><i class="fa icon-radius"></i><i class="fa fa-angle-down pull-right sub-fa"   ng-if="' + !item.htmlUrl + '"></i>' + $rootScope.$l(item.menuName) +
                                            '</a>' +
                                            '</li>');
                                    }
                                });
                                _this.append($compile(childNode)(scope));
                                scope.expand();
                                scope.loadedMenu.push(menuId);
                            }
                            e.stopPropagation();
                            //return false;//阻断下个事件
                        });
                        $(document).on("mouseleave", ".shrinkage-app #main-menu ul", function () {
                            $(this).parent().removeClass("expand");
                            $(this).slideUp('fast'); //　左栏被收缩时状态　处理ＵＩ显示
                        })
                    })


                }
            }
        })

        // 权限按钮组 根据职责、或　角色，与菜单ＩＤ获取　权限的按钮组  按钮组
        /*
         * permission-buttons="[btnModify,btnNew,btnDel]"  控制显示的按钮，只有在这数组里才会出现
         * data-disabled-for-table="dataTable" 常规被禁用按钮依赖的表格  如果：必须选中行之后，[删除]，[修改] 按钮才可以使用,
         * data-disabled-buttons="btnModify,btnNew" // 设置被禁用的按钮 ,  必须与  data-disabled-for 配合使用
         *
         *
         * Dome  <div  permission-buttons="btnNew,btnModify,btnDel" data-disabled-for-table="dataTable"  class="btn-group btn-group-xs " >
         <button   type="button" class="btn btn-default" ng-click="btnNew()"> </button> // 重定义按钮的属性
         </div>
         *
         *
         * */
        .directive('permissionButtons', function ($rootScope, $stateParams, $compile, SIEJS, arrayUnique, tableXlsExport) {
            return {
                restrict: 'EA',
                replace: true,
                link: function (scope, element, attr, controller) {
                    var buttons = element.find('button');//　已自定义的所有的按钮
                    buttons.attr('init', 'true');
                    buttons.hide();
                    if (!attr.permissionButtons) { // 未配置按钮权限
                        return;
                    }
                    var allow = attr.permissionButtons.split(',');
                    var disabledArray = ['btnModify', 'btnDel', 'btnDetail'];// 默认被禁用的按钮 ，需要选中某条记录才可激活 基础类型

                    var _disabled = attr.disabledButtons ? attr.disabledButtons.split(',') : []; // 定义的被禁用的按钮
                    disabledArray = disabledArray.concat(_disabled);
                    disabledArray = arrayUnique(disabledArray); // 去重


                    var disabledForTable = attr.disabledForTable || 'dataTable'; //  禁用条件的 table  ,默认为  dataTable
                    var tableExportName = attr.tableExportName || 'dataTable';// 需要导出的表格数据的 id
                    var tableExportParams = attr.tableExportParamsName || 'params';


                    scope.tableToExcel = function (name) {
                        var params = scope[tableExportParams];
                        var count = scope[tableExportName].count;

                        tableXlsExport(name, params, count);
                    };
                    $rootScope.$watch('permission', function (nval, oval) {

                        if (nval === oval) return;
                        for (var n in $rootScope.permission) {
                            var item = $rootScope.permission[n];
                            var hasBtn = false;
                            if (allow.indexOf(item.resourceCode) > -1) { // 创建按钮
                                var btn = '<button type="button" class="btn btn-default" style="display:inline-block" ';
                                if (disabledForTable && disabledArray.indexOf(item.resourceCode) > -1) { // 禁用条件
                                    btn += 'ng-disabled="!' + disabledForTable + '.selectRow" ';
                                }
                                if (item.resourceCode == 'btnExport') {
                                    btn += 'ng-disabled="!' + tableExportName + '.list.length" ';
                                    btn += ' ng-click="' + 'tableToExcel' + "('" + tableExportName + "')" + '" ng-disable>' +
                                        '<i class="' + item.resIcon + '"></i>' +
                                        item.resourceName +
                                        '</button>';

                                } else {
                                    btn += ' ng-click="' + (item.resourceCode.indexOf(')') === item.resourceCode.length - 1 ? item.resourceCode : item.resourceCode + '()') + '" >' +
                                        '<i class="' + item.resIcon + '"></i>' +
                                        item.resourceName +
                                        '</button>';
                                }


                                btn = $(btn);

                                //  重写的按钮
                                buttons.each(function (index, e) {

                                    var func = $(this).attr('ng-click');
                                    var reg = /\(.*\)/; // 匹配参数部分
                                    func = func.replace(reg, '');

                                    if (item.resourceCode === func) { // 匹配事件
                                        if (this.localName === 'button') {
                                            if ($(this).attr('ng-disabled')) btn.attr('ng-disabled', $(this).attr('ng-disabled'));
                                            if ($(this).attr('ng-show')) btn.attr('ng-show', $(this).attr('ng-show'));
                                            if ($(this).attr('ng-if')) btn.attr('ng-if', $(this).attr('ng-if'));
                                            if ($(this).attr('ng-click')) btn.attr('ng-click', $(this).attr('ng-click'));

                                            //btn.attr=$(this).attr;
                                        }
                                    }
                                });

                                element.append($compile(btn)(scope));

                            }


                        }
                        // 删除无权限的button
                        /* element.find('.btn:hidden').each(function (index, e) {
                         $(this).remove();
                         })*/
                        element.find('[init="true"]').each(function (index, e) {
                            $(this).remove();
                        })
                    })


                }
            }
        })


        // 权限按钮  根据职责、或　角色，与菜单ＩＤ获取　权限的按钮  单个按钮
        /*
         * * permission-btn="btnNew"  控制显示的按钮名,只能单个
         *  data-validate-form="paramForm"  校验的表单名  若需要校表单，请设置
         *  dome   <button permission-btn="" ng-click="btnSave()" data-validate-form="paramForm"  class="btn btn-primary btn-xs" ng-disabled="current.articleTitle"></button>
         * */
        .directive('permissionBtn', function ($rootScope, SIEJS, $compile, validateForm, tableXlsExport) {
            return {
                restrict: 'EA',
                replace: {},
                scope: true,
                template: '<button  type="button" ><i class="{{ button.resIcon}}"></i> {{ button.resourceName}}</button>',
                link: function (scope, element, attr, controller) {
                    var button = $(element);//　当前 buttonn
                    button.hide();

                    //var func = element.attr('ng-click') || element.attr('permission-btn');
                    var func = attr.ngClick || attr.permissionBtn;
                    var tableExportName = attr.tableExportName || 'dataTable';//
                    var tableExportParams = attr.tableExportParamsName || 'params';


                    scope.toggle = attr.toggle;

                    scope.show = false;
                    scope.nn = 0;

                    scope.tableToExcel = function (name) {
                        var params = scope[tableExportParams];
                        var count = scope[tableExportName].count;

                        tableXlsExport(name, params, count);
                    };
                    $rootScope.$watch('permission', function (nval, oval) {
                        if (nval === oval) return;
                        var lilst = angular.copy(nval);
                        for (var n in lilst) {
                            var item = lilst[n];
                            var reg = /\(.*\)/; // 匹配参数部分
                            if (func.replace(reg, '') === item.resourceCode) {
                                scope.button = item;
                                scope.show = true;
                                break;
                            }
                        }

                        if (scope.show) {
                            if (!button.attr('ng-click')) {
                                if (func == 'btnExport') {
                                    button.attr('ng-click', "tableToExcel('" + tableExportName + "')");
                                } else {
                                    button.attr('ng-click', func.indexOf(')') === func.length - 1 ? func : func + '()');

                                }
                                $compile(button)(scope);
                            }
                            scope.btnClick = button.attr('ng-click');
                            if (attr.validateForm && !scope.overwrite) {
                                button.attr('ng-click', 'validSubmit(' + attr.validateForm + ')');
                                scope.overwrite = true; // 保证只重写一次 阻止 $complie 多次执行
                                $compile(button)(scope);
                            }
                            button.show();
                        } else {
                            button.remove();
                        }

                        //校验提交
                        scope.validSubmit = function (formname) {

                            if (validateForm(formname)) {
                                scope.$eval(scope.btnClick);// 执行button事件;
                            }
                        };

                    })
                }
            }
        })

        // 提交验证表单按钮  非权限按钮
        /* data-validate-form="paramForm"  校验的表单名  若需要校表单，请设置
         * */
        .directive('validateFormBtn', function (SIEJS, $compile, validateForm) {
            return {
                restrict: 'EA',
                replace: true,
                scope: true,
                link: function (scope, element, attr, controller) {
                    var button = $(element);//　当前 buttonn
                    scope.btnClick = button.attr('ng-click');

                    scope.form = attr.validateFormBtn || attr.validateForm;
                    //校验提交
                    scope.validSubmit = function (formname) {
                        /* if (formname.$invalid) {
                         SIEJS.alert('请检验必填项', 'warning');
                         return;
                         }*/
                        if (validateForm(formname)) {
                            scope.$eval(scope.btnClick);// 执行button事件;
                        }

                    };

                    if (scope.form && !scope.overwrite) {

                        scope.overwrite = true; // 保证只重写一次 阻止 $complie 多次执行

                        var newBtn = $('<button type="button" class="' + button.attr('class') + '">' + button.html() + '</button>');
                        newBtn.attr('ng-click', 'validSubmit(' + scope.form + ')');
                        if (button.attr('disabled')) newBtn.attr('disabled', button.attr('disabled'));
                        if (button.attr('ng-disabled')) newBtn.attr('ng-disabled', button.attr('ng-disabled'));
                        $compile(newBtn)(scope);
                        button.before(newBtn);
                        button.remove();
                    }


                }
            }
        })

        // 右键
        .directive('ngRightClick', function ($parse) {
            return function (scope, element, attrs) {
                var fn = $parse(attrs.ngRightClick);

                element.bind('contextmenu', function (event) {

                    scope.$apply(function () {
                        fn(scope, { $event: event });
                        event.preventDefault();
                    });

                    return false;
                });


            };
        })

        // 头部tab
        .directive('headerTab', function ($rootScope, $stateParams, $compile, $state, $location, $timeout, arrayFindItemIndex) {
            return {
                restrict: 'EA',
                replace: true,
                link: function (scope, ele, attr, controller) {
                    $timeout(function () {
                        setWidth();
                        scrollTab();
                    });


                    ele.bind('mouseover', function () {
                        setWidth();
                        $timeout(function () {
                            $("#mainTabListNicescroll").getNiceScroll().resize();
                        })
                    });
                    var viewport = [0, 0]; // 视口
                    $rootScope.$watch('saafHeadTab', function (nval, oval) {
                        if (nval === oval) return;
                        $timeout(function () {
                            setWidth();
                            $("#mainTabListNicescroll").getNiceScroll().resize();
                            scrollTab();
                        }, 100)
                    }, true);

                    function scrollTab() {
                        var index = -1;
                        for (var n in $rootScope.saafHeadTab) {
                            var item = $rootScope.saafHeadTab[n];
                            if (item.active === true) {
                                index = n;
                                break;
                            }
                        }
                        if (index === -1) return;
                        scope.lastLI = ele.find('li').eq(index * 1 + 1)[0];

                        if (!scope.lastLI) return;
                        var pwidth = ele.parent().width();// 容器宽度

                        var scrollLeft = ele.parent()[0].scrollLeft; // 已滚动的距离
                        viewport = [scrollLeft, scrollLeft + pwidth]; // 视口 距离


                        var itemPostion = scope.lastLI.offsetLeft + scope.lastLI.offsetWidth; // 当前tab的位置，精确到右边


                        // 超出右边界
                        if (itemPostion > viewport[1]) {
                            var x1 = itemPostion - viewport[1];
                            $("#mainTabListNicescroll").animate({
                                scrollLeft: viewport[0] + x1
                            })
                        } else if (scope.lastLI.offsetLeft < viewport[0]) {  // 超出左边界

                            var x2 = scope.lastLI.offsetLeft;
                            $("#mainTabListNicescroll").animate({
                                scrollLeft: x2
                            })
                        }
                    }

                    function setWidth() {
                        scope.lastLI = ele.find('li').last()[0];
                        scope.style = {
                            width: scope.lastLI.offsetLeft + scope.lastLI.offsetWidth
                        };
                    }
                }
            }
        })
        // 字体图标按钮
        .directive('btnFontIcon', function ($timeout) {
            return {
                restrict: 'EA',
                replace: true,
                scope: {
                    ngModel: '='
                },
                templateUrl: 'js/directives/html/btn-font-icon-tpl.html',
                link: function (scope, element, attr, ngModel) {
                    scope.id = attr.id || 'fontIcon' + (new Date().getTime());


                    element.find('.font-icon-list li').click(function () {
                        $(this).siblings().removeClass('active');
                        $(this).addClass('active');
                        scope.currentIcon = $(this).find('i').attr('class');
                    });

                    $timeout(function () {
                        $('#modal_' + scope.id).appendTo('body');
                    }, 500);

                    scope.setVal = function () {

                        scope.ngModel = scope.currentIcon;

                    }


                }
            }
        })

        ///收缩左侧菜单
        .directive('shrinkageBtn', function ($timeout) {
            return {
                restrict: 'EA',
                link: function (scope, element, Attr, controller) {
                    element.bind('click', function () {
                        element.find("span").toggleClass('shrinkage');
                        $("body").toggleClass('shrinkage-app');
                        ///广播　数据给子控制器
                        scope.$broadcast('shrinkageBtn', $("body").hasClass('shrinkage-app'));
                        //scope.$apply();
                        $timeout(function () {
                            $("#menuscop").getNiceScroll().resize();
                        }, 150)

                    })
                }
            }
        })

        /* 职责切换
         * data-load-callback="function"  加载完成后回调
         * */

        .directive('respChange', function ($rootScope, httpServer, URLAPI, $location, $timeout, getResourceByResMenuCode, arrayFindObj, pageResp) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: {},
                scope: true,
                templateUrl: 'js/directives/html/resp-change.html',
                link: function (scope, element, attr, controller) {
                    scope.isShow = false;
                    var menuCode = $location.search().menucode;
                    scope.DataList = [];

                    function getCurrentResp(responsibilityId) {
                        var userInfo = JSON.parse(localStorage[window.appName + '_successLoginInfo']);
                        var userRespList = userInfo.userRespList; // 职责列表
                        var positionList = userInfo.positionList;  // 职位列表

                        var obj = arrayFindObj(userRespList, responsibilityId, 'responsibilityId');
                        var positionListByOrgId = [];// 当前职责下的职位
                        var primaryPosition; // 当前职责的主职位

                        if (obj.orgBean && obj.orgBean.orgId) {
                            for (var n = 0; n < positionList.length; n++) {
                                var item = positionList[n];
                                if (item.orgId === obj.orgBean.orgId) {
                                    positionListByOrgId.push(item);
                                    if (item.primaryFlag === 'Y') { // 主职位
                                        primaryPosition = item;
                                    }
                                }
                            }
                        }
                        obj.positionList = positionListByOrgId;
                        obj.primaryPosition = primaryPosition;
                        // localStorage[window.appName + '_currentResp'] = JSON.stringify(obj);
                        pageResp.save(obj);
                        return obj;
                    }

                    scope.changeResp = function (isReload) {
                        httpServer.post(URLAPI.changeResp, {
                                params: JSON.stringify({
                                    responsibilityId: scope.respId * 1,
                                    menuCode: menuCode
                                })
                            },
                            function (res) {
                                // console.log(res)
                                if (res.status === 'S') {
                                    getCurrentResp(scope.respId);
                                    if (isReload) {
                                        var url = $location.url();
                                        var respId = 'respId=' + scope.respId;
                                        if ($location.search().respId) {
                                            $location.url(url.replace(/respId=\d*/, respId));
                                        } else {
                                            $location.url(url + '&' + respId);
                                        }

                                        $timeout(function () {
                                            window.location.reload();
                                        })
                                    }
                                }
                            },
                            null, false
                        )
                        ;
                    };

                    scope.loadData = function () {
                        // 当前页面的职责列表获取 在 $stateChangeStart 事件获取了。
                        if (!$rootScope.pageRespList || $rootScope.pageRespList.length === 0) return;

                        if (attr.alwaysShow === 'true') {
                            scope.isShow = true;
                        } else {
                            scope.isShow = $rootScope.pageRespList.length > 1;
                        }

                        scope.DataList = $rootScope.pageRespList;
                        // 先获取当前的职责
                        var currentResp = pageResp.get();
                        if (currentResp && currentResp.responsibilityId) {
                            scope.respId = currentResp.responsibilityId + '';
                            scope.$parent.ctrlRespId = scope.respId;

                            if (!$location.search().respId) {
                                $location.url($location.url() + '&respId=' + scope.respId);
                            }

                        } else if (!$location.search().respId || $location.search().respId === 'undefined') {
                            scope.respId = $rootScope.pageRespList[0].responsibilityId + '';
                            scope.$parent.ctrlRespId = scope.respId;
                            $location.url($location.url() + '&respId=' + scope.respId);
                        } else {
                            scope.respId = $location.search().respId;
                        }

                        scope.$parent.ctrlRespId = scope.respId;
                        getResourceByResMenuCode(scope.respId); // 获取资源
                        var resp = getCurrentResp(scope.respId);

                        /*********Ly-2018-08-22*****职责变化匹配对应的orgId,userType,channelType
                         *页面通过$scope取
                         * 注意：职责是否有匹配对应的orgId,userType,channelType与后台管理的设置有关
                         * *******/
                            // console.log(localStorage[localStorage.lastAppName+'_successLoginInfo']);


                        angular.forEach($rootScope.pageRespList, function (data, index) {
                            if (scope.respId == data.responsibilityId) {
                                angular.forEach(data.proFileBeans, function (data, index) {
                                    if ('BRC_OU' == data.profileCode) {
                                        scope.$parent.ctrlOrgId = data.profileValue || '';
                                        //console.log(data.profileValue);
                                    }
                                    if ('USER_TYPE' == data.profileCode) {
                                        scope.$parent.ctrlUserType = data.profileValue || '';
                                        //console.log(data.profileValue);
                                    }
                                    if ('CHANNEL' == data.profileCode) {
                                        scope.$parent.ctrlChannelType = data.profileValue || '';
                                        //console.log(data.profileValue);
                                    }
                                });
                            }
                        });
                        /*********职责变化匹配---end*************/

                        if (attr.loadCallback && typeof scope.$parent[attr.loadCallback] === 'function') {
                            scope.$parent[attr.loadCallback](scope.respId, $rootScope.pageRespList, resp);
                        }


                        scope.changeResp(); // 是否进到页面就要进行切换职责

                    };
                    scope.loadData();

                }
            }
        })

        //内框，全屏按钮　　高级查询
        .run(function ($rootScope, $timeout, $interval, SIEJS, $state, $sce) {

            //全屏
            $rootScope.fullWindow = function (e) {
                var target = $(e.target);
                //为了兼容非常规写法的页面，以及用户点击小图标时候，特意添加下面的查找方法
                while (typeof (target.attr("ng-click")) == "undefined") {
                    target = $(e.target).parent()
                }
                if (window.parent.$('body').hasClass('data-on-all-screen')) {
                    window.parent.$('body').toggleClass('data-on-all-screen');
                    window.parent.saafrootScope.$broadcast("data-on-all-screen", { openFlag: false });

                    target.html('<i class="fa fa-square-o"></i>数据全屏');
                } else {
                    window.parent.$('body').toggleClass('data-on-all-screen');

                    window.parent.saafrootScope.$broadcast("data-on-all-screen", { openFlag: true });
                    target.html('<i class="fa fa-square-o"></i>退出全屏');
                }

            };

            //高级查询
            $rootScope.moreInput = function (e) {
                $('[input-form]').toggleClass('input-form');
                if (e) $(e.target).find("i").toggleClass('fa-chevron-circle-up fa-chevron-circle-down');
                $("[ng-nicescroll]").scrollTop(0);
            }


        })
        .directive('inputForm', function ($rootScope, $timeout) {
            return {
                restrict: 'EA',
                link: function (scope, element, Attr, controller) {
                    $rootScope.moreInput();// 执行高级查询，默认展开

                }
            }
        })

        ////    固定表格头部
        .directive('fixedTable', function ($window, $compile) {
            return {
                restrict: 'EA',
                link: function (scope, element, Attr, controller) {
                    scope.fixedThead = $("<div class='bg-white none' ng-style='TheadCss()'><table class='" + element.attr('class') + "' style='margin:0;'></table></div>");

                    var thead = $(element).find('thead').clone(true);
                    thead.appendTo(scope.fixedThead.find("table"));
                    $(element).before($compile(scope.fixedThead)(scope)); //添加固定表头

                    //表头的css
                    scope.TheadCss = function () {
                        var _top;
                        if ($("[user-button]").length > 0) {
                            _top = ($("[user-button]").offset().top * 1) + $("[user-button]").height();//顶部距离
                        } else {
                            _top = 'inherit';
                        }
                        return {
                            top: _top + "px"
                            , left: $(element).offset().left + "px",
                            width: $(element).width() + "px",
                            position: "fixed"

                        }
                    };

                    ////监听表头的CSS  若需要请取消注释
                    //scope.$watch(scope.TheadCss, function (newValue, oldValue) {
                    //
                    //}, true);//监听TheadCss


                    // 监听父controller 收缩按钮
                    scope.$on('shrinkageBtn', function (event, data) {
                        scope.TheadCss();
                        scope.$apply();
                    });

                    angular.element($window).bind('resize', function () {
                        scope.$apply();
                    });

                    //判断nicescroll　滚动位置，并显示与隐藏对应的表头
                    $("[ng-nicescroll]").bind('scroll', function () {
                        if ($(element).offset().top < (scope.TheadCss().top.replace('px', '') * 1)) {
                            if (scope.fixedThead.css("display") == 'none')
                                scope.fixedThead.css("display", "block");
                        } else {
                            if (scope.fixedThead.css("display") == 'block')
                                scope.fixedThead.css("display", "none");
                        }
                    });
                }
            }
        })

        ////   动态添加的html 重新编译
        .directive('compileHtml', function ($compile) {
            return {
                restrict: 'EA',
                replace: true,
                link: function (scope, ele, attrs) {
                    scope.$watch(function () {
                            return scope.$eval(attrs.ngBindHtml);
                        },
                        function (html) {
                            ele.html(html);
                            $compile(ele.contents())(scope);
                        });
                }
            };
        })

        .directive('businessFormBtn', function ($rootScope, $timeout) {
            return {
                restrict: 'EA',

                templateUrl: 'js/directives/html/business-form-btn-tpl.html',
                link: function (scope, element, Attr, controller) {
                }
            }
        })
        .directive('businessFormLov', function ($rootScope, $timeout) {
            return {
                restrict: 'EA',

                templateUrl: 'js/directives/html/business-form-lov-tpl.html',
                link: function (scope, element, Attr, controller) {
                }
            }
        })

        ///////////  zTree
        /*
         *  data-search='function' 搜索树
         *
         *   data-extend-name='{"name":"menuName",extend:"fromType",style:"color:red;"}' // 拓展的字段 把多个合并显示到 name 上
         *    data-keys='{"name":"extendName"}'   //   extendName 为 data-extend-name 后的值 ，没有设置些ntng属性请使用数据字段.  *****
         * */
        .directive('zTree', function ($http, $compile, URLAPI, httpServer, $parse, $rootScope, $timeout) {
            return {
                require: '?ngModel',
                restrict: 'A',
                scope: false,
                link: function (scope, element, attrs, ngModel) {
                    scope.zTreeData = {
                        name: attrs.zTree || 'defaultZtree'
                    };

                    // 拓展名
                    scope.extendName = attrs.extendName ? scope.$eval(attrs.extendName) : '';


                    scope.currentSelectNodeId = 0;
                    //scope.zTreeData.getNodesIdKey=[];
                    var _keys = attrs.keys == undefined ? null : scope.$eval(attrs.keys);
                    var _simpledata = attrs.simpledata == undefined ? null : scope.$eval(attrs.simpledata);
                    var _edit = attrs.edit == undefined ? null : scope.$eval(attrs.edit);

                    function getFont(treeId, node) {
                        return node.font ? node.font : {};
                    }

                    var setting = {
                        data: {},
                        edit: {
                            drag: {
                                isCopy: true,
                                isMove: false
                            }
                        },
                        view: {
                            showIcon: false,
                            nameIsHTML: true,
                            fontCss: getFont
                        },
                        check: {
                            enable: attrs.check == undefined ? false : attrs.check,
                            chkStyle: "checkbox",
                            chkboxType: attrs.chkboxType ? eval("(" + attrs.chkboxType + ")") : { "Y": "ps", "N": "s" }
                        },
                        callback: {
                            onClick: function (event, treeId, treeNode, clickFlag) {
                                scope.$apply(function () {
                                    scope.zTreeData.selectNode = treeNode; //当前选择的数据
                                    scope.currentSelectNodeId = treeNode[_simpledata.idKey];
                                    scope.zTreeData.parentNode = treeNode.getParentNode() || {}; // 顶级时为 0

                                    //console.log('scope.zTreeData.parentNode', scope.zTreeData.parentNode)
                                    //ngModel.$setViewValue(treeNode);
                                });

                            },
                            onCheck: function (event, treeId, treeNode) {
                                scope.$apply(function () {
                                    scope.zTreeData.selectNode = treeNode;
                                    if (attrs.id) {
                                        var treeObj = $.fn.zTree.getZTreeObj(attrs.id);
                                        scope.treeObj = treeObj;
                                        var nodes = treeObj.getCheckedNodes(true);
                                        scope.zTreeData.getCheckedNodes = nodes;//获取当前选中的所有节点

                                        var menuId = [];
                                        var getMenuAndBtn = [];/////// 职责分配权限专用,获取当前选中（分配）的菜单与权限（按钮）

                                        for (var y = 0; y < nodes.length; y++) {

                                            if (_simpledata.idKey) {
                                                menuId.push(nodes[y][_simpledata.idKey]);
                                            } else {
                                                menuId.push(nodes[y]['id']);
                                            }

                                            getMenuAndBtn.push({
                                                "menuId": nodes[y].menuId,
                                                "menuType": nodes[y].menuType,
                                                "menuParentId": nodes[y].menuParentId,
                                                "menuCode": nodes[y].menuCode
                                            })

                                        }
                                        scope.zTreeData.getNodesIdKey = menuId;
                                        scope.zTreeData.getMenuAndBtn = getMenuAndBtn;

                                        //console.log(scope.zTreeData.getMenuAndBtn)


                                    }


                                });
                            }
                        }
                    };

                    if (_keys != null) setting.data.key = _keys;
                    if (_simpledata != null) setting.data.simpleData = _simpledata;

                    for (var s in _edit) {
                        setting.edit[s] = _edit[s];
                    }

                    var urlParams = attrs.params ? JSON.stringify(attrs.params) : '';

                    if (attrs.params) {
                        urlParams = scope.$eval(attrs.params);
                    }


                    angular.element(document).ready(function () {
                        if (attrs.json != undefined && attrs.json != '') {
                            $.fn.zTree.init(element, setting, scope.$eval(attrs.json));
                            scope.zTreeData.Json = scope.$eval(attrs.json)
                        } else {
                            if (attrs.url != undefined && attrs.url != '') {
                                if ($rootScope.MenuTree && attrs.url == 'treeMenu') {
                                    var treeObj = $.fn.zTree.init(element, setting, $rootScope.MenuTree);
                                    scope.treeObj = treeObj;
                                    treeObj.expandAll(true);
                                } else {
                                    log(URLAPI[attrs.url]);
                                    httpServer.post(URLAPI[attrs.url],
                                        (function () {
                                            if (attrs.pageIndex) {
                                                return {
                                                    params: JSON.stringify(urlParams),
                                                    pageIndex: attrs.pageIndex,
                                                    pageRows: attrs.pageRows
                                                }
                                            } else {
                                                return {
                                                    'params': JSON.stringify(urlParams)
                                                }
                                            }
                                        })(),
                                        function (res) {
                                            if (res) {
                                                if (scope.extendName) {
                                                    for (var n in res.data) {
                                                        var item = res.data[n];
                                                        item.extendName = item[scope.extendName.name] + '<span style="' + scope.extendName.style + '">' + (item[scope.extendName.extend] || '') + '</span>'

                                                    }
                                                }
                                                var treeObj = $.fn.zTree.init(element, setting, res.data);
                                                scope.treeObj = treeObj;
                                                scope.zTreeData.Json = res.data;
                                                //console.log(scope.zTreeData.Json)
                                                treeObj.expandAll(true);
                                            }
                                        },
                                        function () {

                                        });
                                }

                            } else {
                                $.fn.zTree.init(element, setting, null);
                                scope.zTreeData.Json = null
                            }
                        }


                    });

                    if (attrs.watch == 'true') { //启动监听
                        scope.$watch('zTreeData.updataId', function (newVal, oldVal) {

                            //console.log(attrs.id + '  数据变动，监听执行', +newVal + '  ' + oldVal);


                            if (newVal === oldVal) {
                                return;
                            }

                            if (scope.extendName) {
                                for (var n in scope.zTreeData.Json) {
                                    var item = scope.zTreeData.Json[n];
                                    item.extendName = item[scope.extendName.name] + '<span style="' + scope.extendName.style + '">' + (item[scope.extendName.extend] || '') + '</span>'

                                }
                            }


                            var treeObj = $.fn.zTree.init(element, setting, scope.zTreeData.Json);
                            scope.treeObj = treeObj;
                            treeObj.expandAll(true);


                            var node = treeObj.getNodeByParam(_simpledata.idKey, scope.currentSelectNodeId);
                            treeObj.selectNode(node);

                            var nodes = treeObj.getCheckedNodes(true);
                            scope.zTreeData.getCheckedNodes = nodes;//获取当前选中的所有节点

                            var menuId = [];
                            var getMenuAndBtn = [];
                            for (var y = 0; y < nodes.length; y++) {

                                if (_simpledata.idKey) {
                                    menuId.push(nodes[y][_simpledata.idKey]);
                                } else {
                                    menuId.push(nodes[y]['id']);
                                }

                                getMenuAndBtn.push({
                                    "menuId": nodes[y].menuId,
                                    "menuParentId": nodes[y].menuParentId,
                                    "menuorbtn": nodes[y].menuorbtn,
                                    "funButtonId": nodes[y].funButtonId
                                })
                            }
                            $timeout(function () {
                                scope.zTreeData.getNodesIdKey = menuId;
                                scope.zTreeData.getMenuAndBtn = getMenuAndBtn;
                            }, 200)
                        }, true)
                    }


                }
            }
        })

        /* 树形下拉菜单  ************   请在使用的Controller 上 依赖 ztree  define(['ztree'],functoin(ztree){})
         * 父级返回一个 $scope['zTreeSelect']对象
         *   $scope['zTreeSelect'] = {
         *       selectNode: // 当前选择中的节点         *
         *       search:function() {},
         *       list: [] // 返回当前的数据集
         *   }
         *   z-tree-select="treename" // 请用唯一名称命名
         *   ng-value="params.value";// 当前选中的value文本   并返回当前controller 定义的 parmas对象里    #必输入#
         *   ng-key="params.key" ; //  当前选项的key值文本，并返回当前controller 定义的 parmas对象里
         *   ng-tree-data="" ;// 树的数组数据，如果有此项，url 将不会再去发起请求
         *   ng-params="params" ;//   参数
         *   @参数说明
         *    data-input-readonly="true" // 输入框是否只读
         *    data-label="请选择上级菜单" // 用于显示的标题   #必输入#
         *     data-label-css="w100"  // lable 的css ，默认是 w100 (宽100px)
         *    data-url='menulist' // 服务的API URL
         *    data-params = "{}" // 所要查询的参数
         *    data-keys='{"name":"menuName"}'  // 显示在树的字段名    #必输入#
         *    data-simpledata='{"enable":"true","idKey":"menuId","pIdKey":"menuParentId","name":"menuName"}'    #必输入#
         *                      ----- idKey是当前节点的ID，pIdKey是当前节点的父级ID，绑定到对应的字段,name:是当前显示的文本的字段
         *     data-append-node='[{}]' ;// 手动添加一个树结点，可以为数组对象，一般用于添加select 的顶层
         *     data-required='true'; // 是否必选
         *      data-check="true"  // 是 否多选
         *      data-height="200" // 控件调度
         *      data-callback="setVal"   多选时确定按钮的回调  返回function(nodes,keys,values);
         *      data-expand-node="1"  树默认展开节点级别  -1 或默认 为全部展开
         *      data-choose-childen="true"  只能选择子级，
         * */
        .directive('zTreeSelect', function ($http, $compile, URLAPI, httpServer, $parse, $rootScope, $timeout) {
            return {
                require: '?ngModel',
                restrict: 'EA',
                replace: true,
                templateUrl: 'js/directives/html/ztree-select-tpl.html',
                scope: {
                    ngModel: '=',// 指令与父级的双向绑定
                    ngValue: '=',
                    ngKey: '=',
                    ngTreeData: '=',
                    ngParams: '='

                },
                link: function (scope, element, attrs, ngModel) {

                    var tree = attrs.zTreeSelect || 'zTreeSelect'; // 当前gridTable的scope
                    if (typeof scope.$parent[tree] === 'undefined') {
                        scope.$parent[tree] = {};
                    }
                    scope.check = !attrs.check ? false : attrs.check;
                    element.find('ul').attr('id', tree); // 一定先要给当前 ztree ul 添加唯一标识 ID，否则 callback 则会覆盖，坑坑坑坑坑坑！！！！！
                    var _treeUl = element.find('#' + tree);
                    scope.showTree = false;
                    scope.inputName = attrs.name || tree;
                    scope.style = {
                        height: attrs.height || '350'
                    };
                    scope.labelCss = attrs.labelCss || 'w100';
                    scope.expandNode = (attrs.expandNode || -1) * 1;
                    //  是否只能选择子级
                    scope.chooseChilden = attrs.chooseChilden ? scope.$eval(attrs.chooseChilden) : false;

                    // 制件点击
                    element.find('.showtree').bind('click', function (even) {
                        // 计算ztree的宽
                        var w1 = element.find('.input-group')[0].clientWidth;
                        var w2 = element.find('label')[0].clientWidth;
                        var _style = {
                            'min-width': w1 - w2 - 10,
                            position: 'absolute',
                            opacity: 0
                        };

                        _treeUl.css(_style);


                        /*   scope.ztreeBox = {
                         'min-width': w1 - w2,
                         height: scope.style.height * 1 + 20
                         }*/
                        $timeout(function () {
                            var _w = _treeUl[0].offsetWidth;

                            scope.ztreeBox = {
                                'min-width': _w + 10,
                                height: scope.style.height * 1 + 20
                            };
                            _treeUl.css('opacity', '100');
                        });
                        if (scope.check) {
                            $timeout(function () {
                                scope.ztreeBox.height = scope.ztreeBox.height + 40;
                            })

                        }


                        scope.$apply(function () {
                            scope.showTree = !scope.showTree;
                        })
                    });

                    if (!scope.check) {
                        element.bind('mouseleave', function () {
                            /* if (scope.ngValue) {
                             scope.$apply(function () {
                             scope.showTree = false;
                             })
                             }*/
                            scope.$apply(function () {
                                scope.showTree = false;
                            })
                        });
                    }


                    // 设置input 属性
                    if (attrs.inputReadonly !== 'true') {
                        element.find('input').removeAttr('readonly');
                    }

                    scope.required = true;
                    if (attrs.required !== 'true') {
                        scope.required = false;
                        element.find('input').removeAttr('required');
                    }


                    // 取消
                    scope.btnCancel = function () {
                        scope.showTree = !scope.showTree; // 隐藏tree ui
                        scope.nodes = scope.ngKey = scope.ngValue = null;
                        scope.treeObj.checkAllNodes(false);// 取消勾选
                    };
                    // 确定 按钮
                    scope.btnEnter = function () {
                        scope.showTree = !scope.showTree; // 隐藏tree ui

                        if (attrs.callback) {

                            if (typeof scope.$parent[attrs.callback] !== 'function') {
                                alert('data-callback 参数错误, ' + attrs.callback + '不是一个function')
                            } else {
                                scope.$parent[attrs.callback](scope.nodes, scope.ngKey, scope.ngValue);
                            }

                        }

                    };


                    scope.selectName = attrs.label || '没有设置label';
                    var _keys = attrs.keys == undefined ? {} : scope.$eval(attrs.keys);
                    var _simpledata = attrs.simpledata == undefined ? null : scope.$eval(attrs.simpledata);

                    // 处理 name 属性兼容
                    if (_simpledata.name) {
                        _keys.name = _simpledata.name;
                    } else {
                        _simpledata.name = _keys.name;
                    }

                    var setting = {
                        view: {
                            dblClickExpand: false,
                            showIcon: false,
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        },
                        check: {
                            enable: scope.check,
                            chkStyle: "checkbox",
                            chkboxType: attrs.chkboxType ? eval("(" + attrs.chkboxType + ")") : { "Y": "ps", "N": "s" }
                        },
                        callback: {
                            beforeClick: function (treeId, treeNode) {
                                if (scope.chooseChilden && treeNode.isParent) {
                                    return false;
                                }
                                //var check = (treeNode && !treeNode.isParent);


                            },
                            onDblClick: function (event, treeId, treeNode) {
                                scope.$apply(function () {
                                    scope.showTree = false; // 隐藏tree ui
                                })
                            },
                            onClick: function (event, treeId, treeNode, clickFlag) {
                                if (scope.check) return; // 是否 多选
                                log(treeNode);



                                scope.$apply(function () {
                                    scope.$parent[tree].selectNode = treeNode; //当前选择的数据
                                    scope.$parent[tree].selectNodeId = treeNode[_simpledata.idKey];
                                    scope.$parent[tree].parentNode = treeNode.getParentNode() || {}; // 顶级时为 0
                                    if (attrs.ngValue) scope.ngValue = treeNode[_simpledata.name];//当前选择的文本
                                    if (attrs.ngKey) scope.ngKey = treeNode[_simpledata.idKey];// 当前选择的Id
                                    scope.showTree = !scope.showTree; // 隐藏tree ui
                                    if (attrs.nodeClick) {
                                        if (typeof scope.$parent[attrs.nodeClick] !== 'function') {
                                            alert('data-nodeClick 参数错误, ' + attrs.nodeClick + '不是一个function')
                                        } else {
                                            scope.$parent[attrs.nodeClick](treeNode);
                                        }
                                    }
                                });

                            },
                            onCheck: function (event, treeId, treeNode) {
                                var treeObj = $.fn.zTree.getZTreeObj(attrs.id || attrs.zTreeSelect);
                                scope.treeObj = treeObj;
                                var nodes = treeObj.getCheckedNodes(true);
                                if (attrs.ngValue || attrs.ngKey) {
                                    var _value = [];
                                    var _key = [];
                                    scope.nodes = [];
                                    for (var n in nodes) {
                                        var item = nodes[n];
                                        /* if (!item.isParent) {

                                         }*/
                                        _key.push(item[_simpledata.idKey]);// 当前选择的Id
                                        _value.push(item[_simpledata.name]);//当前选择的文本
                                        scope.nodes.push(item);
                                    }
                                    if (attrs.ngKey) scope.ngKey = _key;
                                    if (attrs.ngValue) scope.ngValue = _value;

                                }
                                scope.$apply();


                            }
                        }
                    };

                    if (_keys != null) setting.data.key = _keys;

                    if (_simpledata != null) setting.data.simpleData = _simpledata;

                    var newNode = attrs.appendNode ? scope.$eval(attrs.appendNode) : null;


                    scope.$parent[tree].search = function (params, callback) {
                        $timeout(function () {
                            var urlParams = params || scope.ngParams || (attrs.params ? scope.$eval(attrs.params) : {});
                            httpServer.post(URLAPI[attrs.url],
                                (function () {
                                    if (attrs.pageIndex) {
                                        return {
                                            params: JSON.stringify(urlParams),
                                            pageIndex: attrs.pageIndex,
                                            pageRows: attrs.pageRows
                                        }
                                    } else {
                                        return {
                                            params: JSON.stringify(urlParams)
                                        }
                                    }
                                })(),
                                function (res) {
                                    if (res.status === 'S') {
                                        scope.$parent[tree].list = res.data;
                                        scope.treeObj = $.fn.zTree.init(_treeUl, setting, res.data);
                                        if (newNode) scope.treeObj.addNodes(null, 0, newNode);
                                        if (scope.expandNode === -1) {
                                            scope.treeObj.expandAll(true);
                                        } else {
                                            var nodes = scope.treeObj.getNodes();
                                            for (var i = 0; i < nodes.length; i++) { //设置节点展开

                                                scope.treeObj.expandNode(nodes[i], true, false, true);
                                            }
                                        }
                                        if (typeof callback === 'function') {
                                            callback(res.data);
                                        }

                                    }
                                });
                        })

                    };
                    scope.$parent[tree].setSelect = function (params) {
                        var treeObj = $.fn.zTree.getZTreeObj(attrs.id || attrs.zTreeSelect);
                        scope.treeObj = treeObj;
                        var paramsList =params.split(",");
                        for (var ns in paramsList) {
                            var node = treeObj.getNodeByParam(_simpledata.idKey, paramsList[ns], null);
                            node.checked = true;
                            treeObj.updateNode(node)
                        }

                        var nodes = treeObj.getCheckedNodes(true);
                        if (attrs.ngValue || attrs.ngKey) {
                            var _value = [];
                            var _key = [];
                            scope.nodes = [];
                            for (var n in nodes) {
                                var item = nodes[n];
                                /* if (!item.isParent) {

                                 }*/
                                _key.push(item[_simpledata.idKey]);// 当前选择的Id
                                _value.push(item[_simpledata.name]);//当前选择的文本
                                scope.nodes.push(item);
                            }
                            if (attrs.ngKey) scope.ngKey = _key;
                            if (attrs.ngValue) scope.ngValue = _value;

                        }
                        scope.$apply();

                    };

                    if (attrs.ngTreeData) {
                        scope.w = scope.$watch('ngTreeData', function (newVal, oldVal) {
                            if (newVal === oldVal || newVal == null) return;
                            scope.treeObj = $.fn.zTree.init(_treeUl, setting, newVal);
                            if (newNode) scope.treeObj.addNodes(null, 0, newNode);// 添加节点

                            if (scope.expandNode === -1) {
                                scope.treeObj.expandAll(true);
                            } else {
                                var nodes = scope.treeObj.getNodes();
                                for (var i = 0; i < nodes.length; i++) { //设置节点展开

                                    scope.treeObj.expandNode(nodes[i], true, false, true);
                                }
                            }


                        });
                        scope.$on("$destroy", function () {
                            scope.w(); //　销毁监听
                        })

                    } else if (attrs.url) {
                        scope.$parent[tree].search();
                    }


                }

            }

        })


        /* 　ztree 的代替方案，请尽量使用此　directive
         ＊　重新封装的树  ***************************  请在使用的Controller 上 依赖 ztree  define(['ztree'],functoin(ztree){})
         *  newZtree  // 请用唯一名称命名
         *  父级返回一个 $scope['zTreeSelect']对象
         *   $scope['newZtree'] = {
         *       selectNode: // 当前选择中的节点
         *       reload:function() // 重载树
         *       treeData: 当前树的数据
         *       dom: //当前树的dom
         *   }

         *  data-expand-node: 展开第一节点
         *
         *   @参数说明
         *    data-url='menulist' // 服务的API URL
         *    ng-data-source="" ;// 树的数组数据，      如果有此项，url 将不会再去发起请求
         *    data-params = "{}" // 所要查询的参数
         *    data-chk-disabled="true"  // check 是否禁用？
         *    data-keys='{"name":"menuName"}'  // 显示在树的字段名    #必输入#
         *    data-simpledata='{"enable":"true","idKey":"menuId","pIdKey":"menuParentId","rootPId",0}'    #必输入#
         *                      ----- idKey是当前节点的ID，pIdKey是当前节点的父级ID，绑定到对应的字段
         *    data-node-click="treeClick"  节点click回调事件
         *     data-clear-data="true";   数据可能存在无法正确关联的沉积数据，是否清除？
         *    data-edit="true" // 　是否打开编辑功能，如拖曳＼删除等,只有打开此功能，编辑功能才会有效
         *    data-callback-beforedrop="updateLevel"  // 要确保 data-edit="true"   拖曳完成后事件，比如更新层级关系
         *    data-check="true"  // 是 否多选
         *    data-extend-name='{"name":"menuName",extend:"fromType",style:"color:red;"}' // 拓展的字段 把多个合并显示到 name 上
         *    data-keys='{"name":"extendName"}'   //   extendName 为 data-extend-name 后的值 ，没有设置些ntng属性请使用数据字段.
         *    data-onload=''  // 控件加载完成
         *    data-selectnode='0' // 默认选中第几个节点？ 如果是子级则是 1-1、1-1-2
         *    *****
         *
         * */
        .directive('sieZtree', function ($http, $compile, URLAPI, httpServer, $parse, $rootScope, $timeout, recursionJSON) {
            return {
                require: '?ngModel',
                restrict: 'EA',
                replace: true,
                scope: {
                    ngModel: '=',// 指令与父级的双向绑定
                    ngDataSource: '='
                },
                link: function (scope, element, attrs, ngModel) {
                    var tree = attrs.sieZtree || 'sieZtree' + Date.parse(new Date());
                    if (typeof scope.$parent[tree] === 'undefined') {
                        scope.$parent[tree] = {};
                    }
                    element.attr('id', tree); // 一定先要给当前 ztree ul 添加唯一标识 ID，否则 callback 则会覆盖，坑坑坑坑坑坑！！！！！
                    var _keys = attrs.keys == undefined ? null : scope.$eval(attrs.keys);
                    var _simpledata = attrs.simpledata == undefined ? null : scope.$eval(attrs.simpledata);
                    var _clearDdata = !attrs.clearData || attrs.clearData === 'true' ? true : false;
                    var chkDisabled = attrs.chkDisabled === undefined ? false : scope.$eval(attrs.chkDisabled);// 是否禁用checked

                    scope.check = !attrs.check ? false : attrs.check;
                    // 拓展名
                    scope.extendName = attrs.extendName ? scope.$eval(attrs.extendName) : '';

                    var setting = {
                        view: {
                            dblClickExpand: false,
                            showIcon: false,
                            nameIsHTML: true
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        },
                        edit: {
                            enable: attrs.edit === 'true',
                            showRemoveBtn: false,
                            showRenameBtn: false,
                            drag: {
                                autoExpandTrigger: true
                            }
                        },
                        check: {
                            enable: scope.check,
                            chkStyle: "checkbox",
                            chkboxType: attrs.chkboxType ? eval("(" + attrs.chkboxType + ")") : { "Y": "ps", "N": "s" }

                        },


                        callback: {
                            beforeClick: function (treeId, treeNode) {
                                /*  var check = (treeNode && !treeNode.isParent);
                                 if (!check) alert("只能选子节点 ...");
                                 return check;*/
                            },
                            onDblClick: function (event, treeId, treeNode) {

                            },
                            onClick: function (event, treeId, treeNode, clickFlag) {
                                scope.$apply(function () {
                                    scope.$parent[tree].selectNode = treeNode; //当前选择的数据
                                    scope.$parent[tree].selectNodeId = treeNode[_simpledata.idKey];
                                    scope.$parent[tree].parentNode = treeNode.getParentNode() || {}; // 顶级时为 0
                                    if (attrs.ngValue) scope.ngValue = treeNode[_keys.name];//当前选择的文本
                                    if (attrs.ngKey) scope.ngKey = treeNode[_simpledata.idKey];// 当前选择的Id
                                    if (attrs.nodeClick) {
                                        if (typeof scope.$parent[attrs.nodeClick] !== 'function') {
                                            alert('data-nodeClick 参数错误, ' + attrs.nodeClick + '不是一个function')
                                        } else {
                                            scope.$parent[attrs.nodeClick](treeNode);
                                        }
                                    }
                                });

                            },
                            beforeDrag: function (treeId, treeNodes) {
                                for (var i = 0, l = treeNodes.length; i < l; i++) {
                                    if (treeNodes[i].drag === false) {
                                        return false;
                                    }
                                }
                                return true;
                            },
                            beforeDrop: function (treeId, treeNodes, targetNode, moveType, isCopy) {
                                if (attrs.callbackBeforedrop) {
                                    scope.$parent[attrs.callbackBeforedrop](treeId, treeNodes, targetNode, moveType, isCopy);
                                    return false;
                                } else {
                                    return targetNode ? targetNode.drop !== false : true;
                                }


                            }
                        }
                    };

                    if (_keys != null) setting.data.key = _keys;
                    if (_simpledata != null) setting.data.simpleData = _simpledata;

                    var newNode = attrs.appendNode ? scope.$eval(attrs.appendNode) : null;


                    function http() {
                        $timeout(function () {
                            var urlParams = attrs.params ? JSON.stringify(scope.$eval(attrs.params)) : '{}';
                            httpServer.post(URLAPI[attrs.url],
                                (function () {
                                    if (attrs.pageRows) {
                                        return {
                                            params: urlParams,
                                            pageIndex: attrs.pageIndex || 1,
                                            pageRows: attrs.pageRows
                                        }
                                    } else {
                                        return {
                                            'params': urlParams
                                        }
                                    }
                                })(),
                                function (res) {
                                    if (res) {
                                        var treeData;
                                        // 理清数据，只取正确递归的数据
                                        if (_clearDdata) {
                                            treeData = recursionJSON(res.data, _simpledata.idKey, _simpledata.pIdKey, _simpledata.rootPId || 0);
                                        } else {
                                            treeData = res.data;
                                        }

                                        if (chkDisabled) {
                                            for (var i = 0; i < res.data.length; i++) {
                                                res.data[i].chkDisabled = true;
                                            }
                                        }


                                        if (scope.extendName) {
                                            for (var n in treeData) {
                                                var item = treeData[n];
                                                item.extendName = item[scope.extendName.name] + '<span style="' + scope.extendName.style + '">' + (item[scope.extendName.extend] || '') + '</span>'
                                            }
                                        }

                                        scope.$parent[tree].treeData = treeData;
                                        scope.treeObj = $.fn.zTree.init(element, setting, treeData);
                                        scope.$parent[tree].dom = scope.treeObj;

                                        if (newNode) scope.treeObj.addNodes(null, 0, newNode);
                                        if (attrs.expandNode && attrs.expandNode === 'true') {
                                            var nodes = scope.treeObj.getNodes();
                                            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                                                scope.treeObj.expandNode(nodes[i], true, false, false);
                                            }
                                        } else {
                                            scope.treeObj.expandAll(true);
                                        }
                                        // scope.treeObj

                                        if (attrs.onload && typeof scope.$parent[attrs.onload] === 'function') {
                                            scope.$parent[attrs.onload](scope.treeObj);
                                        }




                                    }
                                });
                        })

                    }

                    if (attrs.ngDataSource) {
                        scope.w = scope.$watch('ngDataSource', function (newVal, oldVal) {
                            if (newVal === oldVal || newVal == null) return;


                            var treeData;

                            if (_clearDdata) {
                                treeData = recursionJSON(newVal, _simpledata.idKey, _simpledata.pIdKey, _simpledata.rootPId || 0);
                            } else {
                                treeData = newVal;
                            }

                            if (scope.extendName) {
                                for (var n in treeData) {
                                    var item = treeData[n];
                                    item.extendName = item[scope.extendName.name] + '<span style="' + scope.extendName.style + '">' + (item[scope.extendName.extend] || '') + '</span>'
                                }
                            }

                            scope.treeObj = $.fn.zTree.init(element, setting, treeData);
                            scope.$parent[tree].dom = scope.treeObj;

                            if (newNode) scope.treeObj.addNodes(null, 0, newNode);// 添加节点
                            if (attrs.expandNode && attrs.expandNode === 'true') {
                                var nodes = scope.treeObj.getNodes();
                                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                                    scope.treeObj.expandNode(nodes[i], true, false, false);
                                }
                            } else {
                                scope.treeObj.expandAll(true);
                            }
                        });
                        scope.$on("$destroy", function () {
                            scope.w(); //　销毁监听
                        })

                    } else if (attrs.url) {
                        http();
                        // 请求数据
                    }

                    $timeout(function () {
                        scope.$parent[tree].reload = http;
                    })


                }

            }

        })


        /* 组织架构图
         * @参数　compOrgTree="org" 为控制名，请起唯一name
         * @参数  data-simpledata     与ztree　参数一样，定义了当前id与父级Id 的关键字段映射
         * @参数　data-expandlevel="2"　　默认展开的级别
         * @参数  data-url="orgList"  读取数据的ＵＲＬ
         * @参数  data-params="{}"  请求附带的参数
         * @参数  ng-data-source="objData" 数据源　　如果有此字段，URL将失效不再去发生请求
         * @参数　data-edit="true"　是否启用编辑功能
         * @参数  data-keys='{"name":"menuName"}'  // 显示在树的字段名    #必输入#
         * @参数  data-callback-add="add" 新增的回调参数　返回 add(item) item为当前选中的树节点
         * @参数  data-callback-edit="edit" 修改的回调参数　返回 edit(item) item 为当前选中的树节点
         * @参数  data-callback-del='delete" 删除的回调　返回 delete(id) 当前选中的 节点主键 id
         * @参数  data-table-legend="[{'value':'PeriodAmount','unit':'元','style':'text-align: left;color:red;', 'boolean':'icon','filter':'boolean:icon'}]"
         *        此参数用法与 dataTable　一样 , 用于绑定显示节点详细字段的table
         *
         * @返回
         * @ scope.$parent[compOrgTree]对像
         *   {
         *      search:function() 重新查寻数据
         *   }
         * */
        .directive('compOrgTree', function ($http, $compile, URLAPI, httpServer, $parse, $rootScope, $timeout, SIEJS, ToChildrenJson) {
            return {
                restrict: 'EA',
                templateUrl: 'js/directives/html/comp-org-tree-tpl.html',
                scope: {
                    ngDataSource: '='
                },
                link: function (scope, element, attrs) {
                    //绑定的ID与parendId/root的起始值
                    var simpledata = scope.$eval(attrs.simpledata);
                    scope.expandLevel = attrs.expandlevel ? attrs.expandlevel : 3;
                    scope.loop = 0;
                    scope.legend = attrs.tableLegend ? scope.$eval(attrs.tableLegend) : null;
                    scope.keys = scope.$eval(attrs.keys);
                    var compOrgTree = attrs.compOrgTree || 'compOrgTree';
                    if (typeof scope.$parent[compOrgTree] === 'undefined') {
                        scope.$parent[compOrgTree] = {};
                    }
                    //是否能编辑
                    scope.canEdit = attrs.edit == 'true' ? true : false;
                    scope.$parent[compOrgTree].search = function () {
                        var data = scope.$eval(attrs.params);
                        httpServer.post(URLAPI[attrs.url],
                            data,
                            function (res) {
                                if (res.status == 'E') {
                                    SIEJS.alert(res.msg, 'error');
                                    return;
                                }

                                var source = res.data.hasOwnProperty('data') ? res.data : res;
                                var json = ToChildrenJson(source.data, simpledata.pIdKey, simpledata.idKey, simpledata.pIdVal);
                                scope.orgTreeData = json;
                                scope.$emit('orgTreeData', scope.orgTreeData);// 广播　组织树嵌套格式数据
                                scope.$emit('orgTreeDataSource', source.data);// 广播　组织树数据
                            });

                    };
                    scope.search = scope.$parent[compOrgTree].search;

                    // 是否设置了数据源
                    if (attrs.ngDataSource) {
                        scope.w = scope.$watch('ngDataSource', function (newVal, oldVal) {
                            var _data = newVal.hasOwnProperty('data') ? newVal.data : newVal;
                            scope.orgTreeData = ToChildrenJson(_data, simpledata.pIdKey, simpledata.idKey, simpledata.pIdVal);
                        }, true);
                        scope.$on("$destroy", function () {
                            scope.w(); //　销毁监听
                        })
                    } else {
                        scope.search();
                    }

                    var anim = function (obj) {
                        var _p = obj;
                        var anim = _p.find('div.anim-width').first();
                        var ul = _p.find('ul').first();

                        if (_p.hasClass('noExpand')) {
                            _p.removeClass('noExpand');
                            var _w = _p.width();


                            anim.css({

                                'width': 1
                            });
                            _p.addClass('noExpand');

                            setTimeout(function () {
                                anim.css({
                                    'width': _w
                                });

                                setTimeout(function () {
                                    _p.removeClass('noExpand');

                                }, 300);
                            });
                        } else {

                            anim.css({
                                'width': anim.width()
                            });

                            _p.addClass('noExpand');

                            setTimeout(function () {
                                anim.css({
                                    'width': 1
                                });
                            })


                        }

                    };
                    element.on("click", "a.expand", function (e) {
                        anim($(this).parent().parent());
                        $(this).toggleClass('fa-sort-down fa-sort-up');
                        e.stopPropagation();
                    });

                    element.on("dblclick", ".labels", function () {
                        anim($(this).parent());
                        $(this).find('a.expand').toggleClass('fa-sort-down fa-sort-up');
                    });

                    element.on("click", ".labels", function (e) {
                        element.find(".labels").removeClass('active');
                        $(this).addClass('active');
                    });


                    element.bind('mousemove', function (e) {
                        element.find("[data-toggle='tooltip']").tooltip({ placement: 'right' });
                    });

                    //回调


                    // label 单击事件 设置当前节点
                    scope.labelClick = function (item, e) {
                        scope.$parent[compOrgTree].selectNode = item;
                        scope.currentNode = item;
                    };

                    scope.add = function (item) {
                        if (attrs.callbackAdd && typeof scope.$parent[attrs.callbackAdd] === 'function') {
                            scope.$parent[attrs.callbackAdd](item)
                        }
                    };
                    scope.edit = function (item) {
                        if (attrs.callbackEdit && typeof scope.$parent[attrs.callbackEdit] === 'function') {
                            scope.$parent[attrs.callbackEdit](item, item[scope.keys.key])
                        }
                    };
                    scope.remove = function (item) {
                        if (attrs.callbackRemove && typeof scope.$parent[attrs.callbackRemove] === 'function') {
                            scope.$parent[attrs.callbackRemove](item, item[scope.keys.key])
                        }
                    }


                }
            }
        })

        /*   数据表格　　　by houxingzhang 2017年7月12日00时52分59秒
         *   data-x="{'value':'Month','name':'月份'}"   按 x 统计
         *    ng-params="{}" 查询参数
         *    data-url="rolesList"  请求的API
         *     ng-data-source="res.data"  数据源  若有此项，则不会去发生请求
         *     ng-model="params.table"  ngModel　为一个对象，当data-check-type为单选时返回　{ key:'',value:''} , 当为多选时，返回 {key:[],value:[]}
         *
         *    data-legend="[{'value':'Amount'},
         *    {'value':'PeriodAmount','name':'状态','unitTh':'元','style':'text-align: left;color:red;', 'boolean':'icon','filter':'{boolean:'icon'}','fixed':'left'
         *    'date':'yyyy-MM-dd HH:mm:ss',image:[50,50]}
         *    ]"
         *          ------- 图例名称取值字段  unit 为列表的单位 ,style为 当前CSSStyle格式
         *          ————　fixed：被固定列，left , right
         *           ---------unitTh 表头上显示单位  unitTd 表格里显示 单位
         *           ————image:[宽,高]  // 显示当前为图片，当前字段必须为一个正确的url地址，
         *

         *          独立过滤器
         *          ------ boolean 当前字段为此时，'boolean':'cn'转为“是”“否”或是 'boolean':'icon' 打勾的 小图标
         *          ------ currency:'￥' 格式为货币样式
         *          --------- 'date'：'yyyy-MM-dd HH:mm:ss ' 日期格式样式  标准的angularJs date过滤器格式  必须使用时间戳
         *          —————formatDate: yyyy-MM-dd   日期格式样式  可以使用字符型时间文本进行格式
         *
         *          综合过滤器 (权重大于独立过滤器，使用此属性后，独立过滤器将失效）
         *          ------  filter:{boolean:'icon'}   // 同等于独立过滤器的 boolean:'icon'
         *
         *
         *    data-legend-exclude="['id','month']"　　　排除不显示的字段
         *    data-sort :{'value':'SumCnt','by':'desc'}   value欲进行排序的名称  by 是 排序的方式
         *    data-foot-total:'false'    // 显示统计
         *    data-height: 表格内容的高度，超过会滚动内容
         *    data-class: 自定义的 class
         *    data-recording-list="true" // 是否记录当前选择列表？
         *    $rootscope.reportTable.search 返回一个查询方法

         *    data-click-row="func"  行点击方法

         *    data-paging: 分页配置    /* 按需配置
         *    data-page-size="10" 分页大小  /* 按需配置
         *    data-paging－class 分页的class
         *    data-paging-show="true" ; 是否显示分页
         *    data-callback="[{'value':'Amount','callback':'function'}]"  // 指定列回调函数    /* 按需配置
         *    data-checked-type=""   选择类型（radio单选、checkbox多选）   /* 按需配置,默认null
         *    data-default-checked='false'   // 默认全选       /* 按需配置,默认false
         *    data-max-checked="3"  /  多选情况下最多能选择的数量
         *    data-keys="{'key':'lookupCode','value':'meaning',delKey:'id',delKeyType:'array'}"
         *          设置绑定显示的键值  当设置 checked-type的时候必须   如：绑定当前选中行　到key 、 meaning到value  /* 必须
         *          delKey: 删除接口使用的字段名，若无设置则以key , 'id'为删除主键
         *          delKeyType: 多条删除类型 attay 或 string  即删除条件为： key:[1,2,3,4]或 key:'1,2,3,4'
         *    data-xls-name="影院报表"  /* 按需要配置   导出excel数据的文件名 若设置此属性，则会出现导出按钮
         *    data-show-index='true' // 是否显示序号？ 默认不显示
         *    data-auto-request = 'true' //  与 data-url 组合使用，在组件加载时，是否自动去获取数据？ 默认是
         *    data-recording-list='true' //  是否记录当前已选中的条目，当返回列表时　，条目是选中状态
         *
         *    data-func-column="[{'icon':'fa fa-remove','text':'事件名称','callback':'function'}]" // 功能列表
         *    data-delete-url="deleteUser" // 删除当前选择行的接口
         *               //  请配置 data-keys="{'key':'orgId'"} 即可以 orgId 进 行删除
         *               // 单条或多条删除 格式只能为  id:[1,2,3,4] 或  id:2
         *
         *     data-row-color="{value:'isAutoTask',color:{'Y':'#ff9999','N':'#fff'},type:'background-color'}"  // 根据条件设置行的颜色
         *                      value 为取值 的字段名称  color:为设条件的颜色 ,type 为设置样式的类型，默认为 background-color
         *
         *     data-checked-disabled="{value:'status',condition:['Y']}"  // 单选或多选 被禁用的条件
         *                      value 为取值的字段名 condition: 条件里的都会禁止掉，不能被选择。
         *      data-onload='load' // 数据加载完成
         *    返回 scope[gridTable] 一个对象有 search() 查询方法、selectRow 当前选择行 selectRowList 所有选中行
         *　　    scope[gridTable]={
         *                  search:function(index,pageSize,clearSelectRowList) 进行查询 index当前行,pageSize分页大小,clearSelectRowList重载数据是否清除之前已选的数据列表
         *                  selectRow: obj 当前选中的行
         *                  rowIndex:0; 当前行的索引
         *                  selectRowList: array[obj] 多选时，所有选中的行
         *                  delete: function()  当前行或选中项进行删除
         *                  currentDom: $event  当前选中的行元素 dom
         *                  restore: function(exclude) 重置参数     exclude 排除不清除的参数
         *                  submit:function(form ,index, pageSize, clearSelectRowList)  提交表单进行查询请求，form为查询条件的表单(name),
         *                          其它参数与  search参数一致
         *                  list:  array[obj]   // 返回当前结果集
         *                  cancel: function(){}  取消已选择的数据
         *                  count: 1000 // 当前查询条件的总记录数
         *
         *          }

         * */
        .directive('baseDataTable', function (SortBy, PageRows, TotalRows, httpServer, URLAPI, $timeout, $window, $rootScope, $filter, validateForm,
                                              TableToExcel, SIEJS, $compile, IsArray, $sce, dragTable) {
            return {
                restrict: 'EA',
                replace: false,
                templateUrl: 'js/directives/html/data-table-tpl.html',
                scope: {
                    ngParams: '=',
                    ngModel: '=',
                    ngDataSource: '='
                },
                link: function (scope, element, attrs, controller) {
                    if (attrs.checkedType && !attrs.keys) {
                        SIEJS.alert('请设置 dataTable的 键值参数', "warning", null, '您设置了data-checked-type，必须要设置\n data-keys="{key:"唯一主键"}" ');
                        return
                    }

                    scope.$l = $rootScope.$l; // 多语言转换
                    scope.id = attrs.id ? 't_' + attrs.id : 't_' + (new Date().getTime());


                    var _grid = attrs.baseDataTable || 'baseDataTable'; // 当前gridTable的scope
                    scope._grid = _grid;
                  // debugger ;
                    scope.keys = attrs.keys ? scope.$eval(attrs.keys) : '';
                    scope.currentRadio = { // 当前单选选中的值
                        radioValue: 0
                    };


                    scope.checkedType = attrs.checkedType || '';
                    scope.defaultChecked = attrs.defaultChecked === 'true' ? true : false; // 多选时，默认为全选
                    scope.pagingClass = attrs.pagingClass;
                    scope.recordingList = attrs.recordingList === 'false' ? false : true;// 是否记录当前已选中的条目，当返回列表时　，条目是选中状态

                    $(document).on("mouseover", '#data_table_main_' + scope.id, function () {
                        $('#nicescroll_' + scope.id).getNiceScroll().resize();
                    });

                    scope.loading = {
                        show: false,
                        msg: '数据读取中....'
                    };
                    var ajaxLoading = false;

                    // 是否显示分页
                    scope.pagingShow = !attrs.pagingShow || attrs.pagingShow === 'true' ? true : false;
                    scope.funcColumn = scope.$eval(attrs.funcColumn);
                    scope.dataList = {};
                    scope.dataList.legend = scope.$eval(attrs.legend);
                    scope.$watch(function(){return attrs.legend},function(nv){
                        if(nv)
                            scope.dataList.legend = scope.$eval(nv);
                    });


                    if (typeof scope.$parent[_grid] === 'undefined') {
                        scope.$parent[_grid] = {};
                    }

                    scope.myClass = attrs.myClass;
                    $timeout(function () {
                        if (attrs.height) {
                            scope.tableStyle = {
                                'max-height': parseInt(attrs.height)   // tbody 的高度
                            };
                        }
                    }, 100);

                    scope.paging = {
                        currentPage: 1, //默认显示第一页
                        total: 0,
                        pageSize: attrs.pageSize ? parseInt(attrs.pageSize) : PageRows(element) //默认条数
                    };

                    scope.pageNumber = 0;
                    //log(scope.paging)
                    if (attrs.showIndex) {
                        scope.showIndex = attrs.showIndex && attrs.showIndex === 'true' ? true : false;
                    }

                    // 单选或多选时被禁用状态
                    scope.checkedDisabled = attrs.checkedDisabled ? scope.$eval(attrs.checkedDisabled) : null;

                    // 设置被禁用状态
                    scope.setCheckedDisabled = function (item) {
                        if (scope.checkedDisabled) {
                            return scope.checkedDisabled.condition.indexOf(item[scope.checkedDisabled.value]) > -1;
                        } else {
                            return false;
                        }

                    };

                    scope.setOption = function (res) {

                        if (!res) return;
                        var tid = _grid;
                        if (scope.checkedType === 'checkbox') {
                            if (scope.recordingList) {
                                for (var n in res.data) {
                                    var item = res.data[n];
                                    if (scope.$parent[_grid].selectRowList.length > 0) {
                                        for (var y in scope.$parent[_grid].selectRowList) {
                                            var subitem = scope.$parent[_grid].selectRowList[y];
                                            if (item[scope.keys.key] === subitem[scope.keys.key]) {
                                                item.checked = subitem.checked = true;
                                            }
                                        }
                                    }
                                }
                            }

                            if (scope.defaultChecked) {
                                scope.selectedAll = true;
                                for (var n in res.data) {
                                    res.data[n].checked = true;
                                }
                                scope.$parent[_grid].selectRowList = angular.copy(res.data); //全选内容
                            }

                        }
                        scope.paging.total = res.count;
                        scope.$parent[_grid].count = res.count;

                        if (attrs.sort) {
                            var _sort = scope.$eval(attrs.sort);
                            res.data.sort(SortBy(_sort.value, _sort.by))
                        }
                        var _legend = attrs.legend ? scope.$eval(attrs.legend) : null;
                        var _legendExclude = attrs.legendExclude ? scope.$eval(attrs.legendExclude) : null;
                        // 处理固定列
                        function fixedColumn() {
                            scope.fixedL = [];
                            scope.fixedR = [];
                            var fixedNormal = [];

                            for (var x in _legend) {
                                var item = _legend[x];
                                if (item.fixed === 'left') {
                                    scope.fixedL.push(item);
                                } else if (item.fixed === 'right') {
                                    scope.fixedR.push(item);
                                } else {
                                    //点击排序的时候对全部数据排序
                                    if (attrs.sortAllResult && attrs.sortAllResult === 'true') {
                                        if (scope.clickSortEvent == true) {//点击排序事件
                                            if (item.value == scope.sortValue.value) {
                                                item.sort =scope.sortValue.sort;
                                            }
                                        }
                                    }
                                    fixedNormal.push(item);
                                }
                            }
                            return scope.fixedL.concat(fixedNormal, scope.fixedR);

                        }

                        res.legend = fixedColumn();

                        scope.rowColor = attrs.rowColor ? scope.$eval(attrs.rowColor) : { value: null, color: {} }; // 根据条件设置表单行的颜色

                        // 是否达成　固定列的条件
                        $timeout(setFixedColumn);


                        var _x = null;
                        if (attrs.x) {
                            _x = scope.$eval(attrs.x);
                            res.x = _x;
                        } else {
                            res.x = res.legend[0]
                        }

                        /// 排除的字段
                        if (_legendExclude) {
                            for (var n in res.legend) {
                                if (_legendExclude.indexOf(res.legend[n].value) > -1) {
                                    res.legend.splice(n, 1);
                                }
                            }
                        }
                        if (!res.x.name) { // 设置X y轴
                            for (var y in res.legend) {
                                if (res.legend[y].value === res.x.value) res.x.name = res.legend[y].name;
                            }
                        }

                        // 设置回调
                        if (attrs.callback) {
                            scope.call = scope.$eval(attrs.callback);
                            for (var n in res.legend) {
                                for (var c in scope.call) {
                                    if (res.legend[n].value === scope.call[c].value) {
                                        res.legend[n].callback = scope.call[c].callback
                                    }
                                }
                            }

                        }
                        scope.callback = function (callbackName, item, key ) {
                            // debugger ;
                          if (typeof scope.$parent[callbackName] !== 'function') {
                                alert('data-callback 参数错误, ' + callbackName + '不是一个function')
                            } else {
                                scope.$parent[callbackName](item ,key);
                            }
                        };

                        scope.showTotal = attrs.footTotal === 'true' ? true : false;
                        if (scope.showTotal) {
                            scope.total = TotalRows(res.data); // 统计行
                        }

                        $timeout(function () {
                            scope.tStyle = {
                                height: $(element).find('table')[0].offsetHeight,
                                position: 'relative'
                            }
                        }, 200);
                        return res;
                    };


                    scope.clickSortEvent = false;
                    scope.sortValue = {};
                    scope.clickSortBy = function (item) {
                        if (!item) return;
                        item.sort = item.sort === 'desc' ? 'asc' : 'desc';
                        if (attrs.sortAllResult && attrs.sortAllResult === 'true') {
                            scope.clickSortEvent = true;
                            scope.sortValue.value = item.value;
                            scope.sortValue.sort = item.sort;
                            scope.$parent[_grid].search(scope.paging.index);

                        }else {//排序当前页的数据
                            scope.dataList.data.sort(SortBy(item.value, item.sort));
                        }

                    };

                    // 导出excel文件名
                    scope.xlsName = attrs.xlsName;
                    // 　当前的请求是否是导出的数据
                    scope.isExportData = false;

                    // 导出数据　按钮
                    scope.tableToExcel = function (n, e) {
                        if (e) {
                            // 导出名
                            scope.exportName = scope.xlsName + ' - ' + e.target.innerText.replace('导出', '')
                        } else {
                            scope.exportName = scope.xlsName;
                        }

                        // 当前数据只有一页  则导出当前的表格
                        if (scope.paging.total <= scope.paging.pageSize) {
                            TableToExcel(scope.id, scope.exportName);
                            return;
                        }
                        scope.isExportData = true;
                        if (scope.xlsName)
                            scope.$parent[_grid].search(n, scope.exportInfo.pageRows);
                    };

                    scope.$parent[_grid].restore = function (exclude) {
                        //console.log(scope.ngParams)
                        var ex = null;
                        if (typeof exclude === 'string') {
                            ex = scope.ngParams[exclude];
                            scope.ngParams = {};
                            scope.ngParams[exclude] = ex;
                        } else if (IsArray(exclude)) {
                            ex = {};
                            for (var n in exclude) {
                                ex[exclude[n]] = scope.ngParams[exclude[n]];
                            }
                            scope.ngParams = {};
                            for (var n in exclude) {
                                scope.ngParams[exclude[n]] = ex[exclude[n]];
                            }
                        } else {
                            scope.ngParams = {};
                        }

                        //console.log(scope.ngParams)
                    };
                    // 导出信息
                    scope.exportInfo = {
                        pageIndex: 1,
                        pageRows: 3000, // 每次能导出最大行数
                        list: []
                    };
                    //  计算导出数据
                    scope.exportAction = function (total) {
                        scope.exportInfo.list = [];
                        var _max = scope.exportInfo.pageRows;  // 每次能导出最大行数
                        if (total > _max) {
                            for (var n = 1; n <= Math.ceil(total / _max); n++) {
                                scope.exportInfo.list.push(n)
                            }
                            scope.showExportListBtn = true;

                        } else {
                            scope.showExportListBtn = false;
                        }

                    };
                    scope.$parent[_grid].selectRowList = [];// 返回的当前所 有选中行
                    scope.$parent[_grid].selectRow = null; // 当前选中的行
                    scope.rowIndex = -1000;
                    scope.$parent[_grid].rowIndex = scope.rowIndex;

                    //行选中
                    scope.rowChecked = function (index, event, item) {
                        if (scope.setCheckedDisabled(item)) return;
                        var item = scope.dataList.data[index];
                        scope.$parent[_grid].currentDom = event ? event.currentTarget : null;
                        scope.$parent[_grid].selectRow = item;
                        scope.rowIndex = (index + ((scope.paging.index - 1) * scope.paging.pageSize));
                        scope.$parent[_grid].rowIndex = scope.rowIndex;
                        item.checked = !item.checked;
                        scope.currentRadio.radioValue = item[scope.keys.key]; // 设置radio　选中的值
                        //log(scope.currentRadio.radioValue)
                        if (scope.checkedType === 'checkbox') { // 多选
                            if (item.checked) {
                                var ispush = false;
                                for (var n in scope.$parent[_grid].selectRowList) {
                                    var row = scope.$parent[_grid].selectRowList[n];
                                    if (row[scope.keys.key] === item[scope.keys.key]) {
                                        ispush = true;
                                        break;
                                    }
                                }
                                if (!ispush) {
                                    scope.$parent[_grid].selectRowList.push(item);
                                }
                            } else { // 删除当前选中行
                                //var y = scope.$parent[_grid].selectRowList.indexOf(item); // 此处不能使用indexOf 因为考虑会有不同来源的 list
                                var x = -1;
                                for (var y in scope.$parent[_grid].selectRowList) {
                                    var list = scope.$parent[_grid].selectRowList[y];

                                    if (list[scope.keys.key] === item[scope.keys.key]) {
                                        x = y;
                                    }
                                }
                                scope.$parent[_grid].selectRowList.splice(x, 1);
                            }

                            if (attrs.ngModel) { // 设置了ngModel 则返回值
                                scope.ngModel = { key: [], value: [] };
                                scope.$parent[_grid].selectRowList.map(function (item) {
                                    scope.ngModel.key.push(item[scope.keys.key]);
                                    scope.ngModel.value.push(item[scope.keys.value])
                                })
                            }
                        } else {
                            if (attrs.ngModel) { // 设置了ngModel 则返回值
                                scope.ngModel = {
                                    key: item[scope.keys.key],
                                    value: item[scope.keys.value]
                                };
                            }
                        }


                    };
                    // 行单击
                    scope.rowClick = function (index, event) {
                        var item = scope.dataList.data[index];
                        // 单选时关联
                        if (scope.checkedType === 'radio') {
                            scope.rowChecked(index, event);
                        } else {
                            scope.$parent[_grid].currentDom = event ? event.currentTarget : null;
                            scope.$parent[_grid].selectRow = item;
                            scope.rowIndex = (index + ((scope.paging.index - 1) * scope.paging.pageSize));
                            scope.$parent[_grid].rowIndex = scope.rowIndex;
                        }

                        if (attrs.clickRow) {
                            scope.callback(attrs.clickRow, scope.$parent[_grid].selectRow)
                        }
                    };

                    // 全选按钮
                    scope.checkedAll = function (e) {
                        if (e.target.checked) {// 全选
                            scope.selectedAll = true;
                            for (var n in scope.dataList.data) {
                                var row = scope.dataList.data[n];
                                if (!scope.setCheckedDisabled(row)) { // 非被禁用
                                    row.checked = true;
                                    var isPush = false;
                                    for (var m in scope.$parent[_grid].selectRowList) {
                                        var list = scope.$parent[_grid].selectRowList[m];
                                        if (row[scope.keys.key] === list[scope.keys.key]) {
                                            isPush = true;
                                            break;
                                        }
                                    }
                                    if (!isPush) scope.$parent[_grid].selectRowList.push(row);
                                }

                            }
                        } else {// 返选
                            scope.selectedAll = false;
                            for (var n in scope.dataList.data) {
                                var row = scope.dataList.data[n];
                                row.checked = false;
                                var index = scope.$parent[_grid].selectRowList.indexOf(row);
                                scope.$parent[_grid].selectRowList.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                            }
                        }

                        if (attrs.ngModel) { // 设置了ngModel 则返回值
                            scope.ngModel = { key: [], value: [] };
                            scope.$parent[_grid].selectRowList.map(function (item) {
                                scope.ngModel.key.push(item[scope.keys.key]);
                                scope.ngModel.value.push(item[scope.keys.value])
                            })
                        }
                    };
                    // 分页控件查询
                    scope.paging.search = function (index) {
                        scope.selectedAll = false;// 取消全选
                        scope.isExportData = false;
                        scope.$parent[_grid].search(index)

                    };

                    // 监听数据集变化，进行导出数据
                    scope.w = scope.$watch(function () {
                        return scope[attrs.ngDataSource] || scope.downloadData
                    }, function (nval, oval) {
                        if (!nval || nval == oval) return;
                        if (scope.isExportData) {
                            scope.exportDataList = scope.setOption(angular.copy(nval));
                            // 延迟 2秒，让table渲染完成后再导出
                            $timeout(function () {
                                TableToExcel('export_' + scope.id, scope.exportName);
                                scope.exportDataList.data = [];// 清除下载的数据
                            }, 2000)
                        } else {
                            scope.dataList = scope.setOption(angular.copy(nval));
                        }
                        if (!scope.$parent[_grid].search)
                            scope.$parent[_grid].search = scope.$parent['search'];
                        scope.exportAction(nval.count);
                        scope.isExportData = false;
                    }, true);

                    scope.$on("$destroy", function () {
                        scope.w(); //　销毁监听
                    });

                    if (scope.ngDataSource) { // // 绑定已已有的数据

                        scope.dataList.data = [];
                        scope.dataList.count = 0;

                        scope.w1 = scope.$watch('ngDataSource', function (newval, oldval) {
                            if (!newval) return;
                            scope.$parent[_grid].search();
                        }, true);

                        scope.$parent[_grid].search = function (index, pageSize, clearSelectRowList) {
                            scope.paging.pageSize = attrs.pageSize ? parseInt(attrs.pageSize) : PageRows(element);
                            scope.paging.currentPage = index || scope.paging.currentPage || 1; // ----------------------------
                            scope.paging.total = scope.ngDataSource.data.length;
                            scope.exportAction(scope.paging.total);
                            if (scope.isExportData) {// 当前请求是导出数据，
                                scope.downloadData = scope.setOption(angular.copy(scope.ngDataSource));
                                return;
                            }
                            scope.dataList = [];
                            var copyData = angular.copy(scope.ngDataSource);// 复制数组
                            scope.paging.index = scope.paging.currentPage;
                            //copyData.data = scope.formatField(copyData.data);// 格式化字段数据

                            // 判断分页
                            if (copyData.count > scope.paging.pageSize) {
                                scope.tempData = copyData.data.splice((scope.paging.currentPage - 1) * scope.paging.pageSize, scope.paging.pageSize);
                                scope.dataList = scope.setOption({ count: copyData.count, data: scope.tempData });
                            } else {
                                scope.dataList = scope.setOption(copyData);
                            }

                            scope.pageNumber = angular.copy(scope.paging.index);
                            dragTable('data_table_main_' + scope.id);
                        };

                        scope.$on("$destroy", function () {
                            scope.w1(); //　销毁监听
                        });

                    } else {
                        if (!attrs.url) return;
                        // 数据查询
                        scope.$parent[_grid].search = function (index, pageSize, clearSelectRowList) {

                            $timeout(function () {
                                if (!attrs.checkedType) {
                                    scope.rowIndex = -10000;
                                    scope.$parent[_grid].selectRow = null; // 重置数据
                                    scope.$parent[_grid].rowIndex = scope.rowIndex;
                                }
                                scope.paging.pageSize = attrs.pageSize ? parseInt(attrs.pageSize) : PageRows(element);

                                var pageCount = Math.ceil(scope.paging.total / scope.paging.pageSize);//获取分页大小

                                index = index > pageCount ? pageCount : index;

                                var p = {
                                    params: JSON.stringify(scope.ngParams || scope.$parent.params || {}),// 若当前不传params值，侧查到父级的params进行传参
                                    pageIndex: index || scope.paging.currentPage || 1,
                                    pageRows: pageSize || scope.paging.pageSize
                                };

                                if (attrs.sortAllResult && attrs.sortAllResult === 'true') {
                                    if (scope.clickSortEvent == true) {//点击排序事件
                                        p.orderBy = scope.sortValue.value;
                                        p.sort = scope.sortValue.sort;
                                    }
                                }

                                if (!scope.oldParams) {
                                    scope.oldParams = p.params; // 第一次查询　
                                } else if (scope.oldParams !== p.params) { // 再次查询，且查询条件有变动，将清除当前选中项
                                    scope.rowIndex = -10000;
                                    if (clearSelectRowList === true) {
                                        scope.$parent[_grid].cancel();
                                        /*  scope.$parent[_grid].selectRowList .map(function (item) {
                                         item.checked = false;
                                         });
                                         scope.$parent[_grid].selectRow = null; // 重置数据
                                         scope.$parent[_grid].selectRowList = null;
                                         scope.rowIndex = -1000;*/
                                    }

                                }


                                scope.paging.currentPage = scope.paging.index = p.pageIndex;
                                ajaxLoading = scope.paging.total > 0;

                                if (!ajaxLoading) {
                                    scope.loading.error = false;
                                    scope.loading.show = true;
                                    scope.loading.msg = '数据读取中....';
                                }

                                function errorAction() {
                                    scope.dataList.data = [];//清除原来数据
                                    scope.paging.total = 0;// 清除分页
                                    scope.loading.show = true;
                                    scope.loading.error = true;
                                }

                                httpServer.post(URLAPI[attrs.url], p, function (res) {
                                    if (res.status === 'E') {
                                        log('后台服务出错: ' + URLAPI[attrs.url]);
                                        errorAction();
                                        scope.loading.msg = '后台服务异常,数据读取失败!';
                                        return;
                                    }

                                    if (res.status === 'M') {
                                        log('后台服务出错: ' + URLAPI[attrs.url]);
                                        errorAction();
                                        scope.loading.msg = res.msg;
                                        return;
                                    }

                                    scope.paging.total = res.count;
                                    scope.exportAction(res.count);
                                    if (scope.isExportData) {// 当前请求是导出数据，
                                        scope.downloadData = scope.setOption(angular.copy(res));
                                        return;
                                    }
                                    scope.dataList = {};
                                    //res.data = scope.formatField(res.data);
                                    scope.dataList = scope.setOption(angular.copy(res));
                                    scope.$parent[_grid].list = scope.dataList.data;
                                    scope.loading.show = false;
                                    scope.pageNumber = angular.copy(scope.paging.index);
                                    setTimeout(function () {
                                        dragTable('data_table_main_' + scope.id);
                                        $('#nicescroll_' + scope.id).getNiceScroll().resize();

                                    }, 1000);

                                    if (attrs.onload && typeof scope.$parent[attrs.onload] === 'function') {
                                        scope.$parent[attrs.onload](scope, res.data);
                                    }
                                    //scope.clickSortEvent = false;

                                }, function (e) {
                                    errorAction();
                                    scope.loading.msg = '数据读取失败!'
                                }, ajaxLoading)
                            }, 200)

                        };
                        scope.autoRequest = !attrs.autoRequest ? true : attrs.autoRequest === 'true'; // 首次加载是 否去获取数据？

                        if (scope.autoRequest) {
                            $timeout(function () {
                                scope.$parent[_grid].search(1)
                            }, 300);
                        }
                    }

                    scope.$parent[_grid].cancel = function () {
                        // 取消选择

                        scope.$parent[_grid].selectRowList.map(function (item) {
                            item.checked = false;
                        });
                        scope.$parent[_grid].selectRow = null; // 重置数据
                        scope.$parent[_grid].selectRowList = [];
                        scope.rowIndex = -1000;
                        scope.$parent[_grid].rowIndex = scope.rowIndex;
                    };

                    // 删除
                    if (attrs.deleteUrl) {

                        scope.$parent[_grid].delete = function () {
                            var p = {};

                            var key = 'id';

                            if (scope.keys) {
                                //p[scope.keys.key] = scope.$parent[_grid].selectRow[scope.keys.key]
                                key = scope.keys.delKey || scope.keys.key;
                                var asKey = scope.keys.asKey || scope.keys.key;

                                if (scope.checkedType === 'checkbox') {
                                    //p[key] = scope.$parent[_grid].selectRowList[scope.keys.key]


                                    p[asKey] = [];
                                    for (var n in scope.$parent[_grid].selectRowList) {
                                        var obj = scope.$parent[_grid].selectRowList[n];
                                        if (obj[key]) p[asKey].push(obj[key] * 1);
                                    }

                                    if (scope.keys.delKeyType === 'string') {
                                        p[asKey] = p[asKey].join(',');
                                    }

                                } else {
                                    p[asKey] = scope.$parent[_grid].selectRow[scope.keys.key].toString()
                                }
                            }

                            httpServer.remove(URLAPI[attrs.deleteUrl], {
                                params: JSON.stringify(p)
                            }, function (res) {
                                if (res.status === 'S') {
                                    scope.$parent[_grid].search();//
                                    scope.$parent[_grid].selectRow = null;
                                    scope.rowIndex = -1000;
                                    scope.$parent[_grid].rowIndex = scope.rowIndex;
                                }
                            })
                        }
                    }

                    // 格式化数据单元格样式
                    scope.autoFormatStyle = function (val, style) {

                        var styleObj = {};// 返回的样式
                        if (style) {
                            style = style.replace(/(;*$)/g, "");
                            var array1 = style.split(';');
                            for (var n in array1) {
                                var subArray = array1[n].split(':');
                                styleObj[subArray[0]] = subArray[1]
                            }
                            //log(styleObj);
                            return styleObj;

                        }


                        if (val === null || val === undefined || val === '') return;

                        // 数字 正负 、浮点
                        var regNumber = /^-?[1-9]\d*\.?\d*$|^-?0\.\d*[1-9]\d*$|^0$|^\d*$/;
                        // 日间与日期
                        var regDateTime = /^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(\-|\/|.)(((0[13578]|1[02])(\-|\/|.)(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(\-|\/|.)(0[1-9]|[12][0-9]|30))|(02(\-|\/|.)(0[1-9]|[1][0-9]|2[0-8])))(\s(0[0-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9]):(0[0-9]|[1-5][0-9]))?$/;
                        // 货币 带千分符,的格式
                        var regCurrency = /^[$￥¥￡€฿₩]?[:：]?\d{1,3}(,\d{1,3}){0,}\.\d{1,2}$/;


                        // 日期
                        if (regDateTime.test(val)) {
                            styleObj = {
                                'text-align': 'right'
                            }

                        } else if (regCurrency.test(val)) { // 倾向
                            styleObj = {
                                'text-align': 'right'
                            }
                        } else if (regNumber.test(val)) {
                            styleObj = {
                                'text-align': 'right'
                            }
                        } else if (val === 'Y' || val === 'N' || val === 'true' || val === true || val === 'false' || val === false) {
                            styleObj = {
                                'text-align': 'center'
                            }
                        } else {
                            styleObj = {
                                'text-align': 'left'
                            }
                        }


                        return styleObj;
                    };

                    // 格式化字段
                    scope.formatField = function (row, td) {
                        var result = row[td.value] === undefined ? ' ' : row[td.value];
                        if (row[td.value] !== undefined) {
                            if (td.filter) {
                                var keys = Object.keys(td.filter);
                                result = $filter(keys[0])(result, td.filter[keys[0]]);
                            } else {
                                if (td.boolean) result = $filter('boolean')(result, td.boolean, false);
                                if (td.currency) result = $filter('currency')(result, td.currency);
                                if (td.date) result = $filter('date')(result, td.date);
                                if (td.formatDate) result = $filter('formatDate')(result, td.formatDate);
                                if (td.image && IsArray(td.image)) {
                                    result = '<img src="' + row[td.value] + '" style="margin:0 auto;max-width: ' + td.image[0] + 'px;max-height: ' + td.image[1] + 'px;">';
                                }
                            }
                            if (td.unitTd) result = result + ' ' + td.unitTd;
                        }
                        result = '<span>' + result + '</span>';
                        return $sce.trustAsHtml(result);
                    };

                    // 返回行颜色
                    scope.setRowStyle = function (val) {

                        if (!val || !scope.rowColor.value) return;

                        if (scope.rowColor.color[val.toString()]) {
                            return (scope.rowColor.type || 'background-color') + ':' + scope.rowColor.color[val.toString()];
                        } else {
                            return '';
                        }


                    };
                    // 提交表单进行查询
                    scope.$parent[_grid].submit = function (form, index, pageSize, clearSelectRowList) {
                        if (form) {
                            if (validateForm(form)) {
                                scope.$parent[_grid].search(index, pageSize, clearSelectRowList);
                            }
                        } else {
                            scope.$parent[_grid].search(index, pageSize, clearSelectRowList);
                        }


                    };
                    scope.$parent[_grid].customSubmit = function (index, pageSize, clearSelectRowList) {
                        console.log(attrs.customSubmitFn && typeof scope.$parent[attrs.customSubmitFn] === 'function');

                        if (attrs.customSubmitFn && typeof scope.$parent[attrs.customSubmitFn] === 'function') {
                            $timeout(function () {

                                //if (!scope.ngModel) return;
                                /*     var _val, _key;      //  2018年1月22日11时19分35秒  修改为ngMoel返回值
                                 if (typeof scope.ngModel.value !== 'object') {
                                 _val = scope.ngModel.value;
                                 _key = scope.ngModel.key;
                                 } else {
                                 _val = scope.ngModel.value.join(',');
                                 _key = scope.ngModel.key.join(',');
                                 }*/
                                scope.$parent[attrs.customSubmitFn]();
                            })
                        }

                    };

                    function setFixedColumn() {
                        if ($("#_fixedR").length > 0 || $("#_fixedL").length > 0) return;
                        var boxWidth = element.width();
                        var tableWidth = element.find('table')[0].offsetWidth;

                        //var tableWidth=element.find('#data_table_main_'+ scope.id)[0].offsetWidth;


                        if (boxWidth < tableWidth) { //  表格超出范围了。要固定列了。
                            var th = element.find('table th.ng-scope');

                            scope.thWidth = [];// 表头的宽度
                            th.each(function (index, item) {
                                scope.thWidth.push(item.offsetWidth);
                                item.style.width = item.offsetWidth + 'px';
                                //log($(this).text().replace(/\s+/gim,''));
                            });

                            var trs = element.find('table tbody tr');//　当前数据行数
                            var rows = trs.length;

                            // 冻结左边表格
                            if (scope.fixedL && scope.fixedL.length > 0) {
                                var colmunLength = scope.fixedL.length;
                                if (scope.showIndex) {
                                    colmunLength++;
                                }
                                if (scope.checkedType) {
                                    colmunLength++;
                                }
                                createFixedTableL('fixedL', 0, colmunLength);
                            }

                            // 冻结右边
                            if (scope.fixedR && scope.fixedR.length > 0 || scope.funcColumn) {
                                var ths = th.length;
                                var columnLen = scope.funcColumn ? 1 : 0;
                                columnLen += scope.fixedR.length;
                                var len = ths - columnLen;
                                createFixedTableR('fixedR', len, ths);
                            }

                            function createFixedTableL(name, len, thLength) {
                                var _table = $('<table class="report-data fixedTable ' + name + '" id="_' + name + '"><thead><tr></tr></thead><tbody></tbody></table>');
                                for (len; len < thLength; len++) {
                                    _table.find('thead tr').append($(th[len]).clone(true));// th 取原来表格的th 可以保持宽度样式一致
                                }
                                var _tr = '<tr ng-repeat=" item in dataList.data track by $index "  ng-click="rowClick($index)" ng-class="{active:($index + ((pageNumber-1) * paging.pageSize))===rowIndex}">' +
                                    '<td ng-if="checkedType"><input id="{{ _grid + item[keys.key]}}" ng-checked="item.checked" type="checkbox" ng-click="check(item,$event)" name="keys" ng-if="checkedType===\'checkbox\' || checkedType===\'check\'"> <input id="{{_grid+ item[keys.key]}}" type="radio" name="key" ng-value="item[keys.key]" ng-model="currentRadio.radioValue" ng-if="checkedType===\'radio\'"></td>' +
                                    ' <td ng-if="showIndex"> <span ng-bind="$index + ((pageNumber-1) * paging.pageSize) + 1"></span></td>' +
                                    '<td ng-repeat="td in ' + name + ' track by $index" data-field-name="{{td.value}}" ng-style="autoFormatStyle(item[td.value],td.style)">' +
                                    '<span class="report-td" title="{{ item[td.value] }}" ng-if="!td.callback && !td.boolean && !td.time" ng-bind="item[td.value] || \'　\'"></span> ' +
                                    '<span class="report-td" title="{{ item[td.value] }}" ng-if="!td.callback && !td.boolean && td.time" ng-bind="item[td.value] | formatDate: \'YYYY-MM-DD\'"></span> ' +
                                    '<span ng-if="!td.callback && td.boolean" ng-bind-html="item[td.value] | columnBoolean:td.boolean"></span>' +
                                    ' <a href="javascript:" class="report-td" title="{{ item[td.value] }}" ng-if="td.callback && !td.boolean" ng-click="callback(td.callback,item)">{{ item[td.value] }}</a>' +
                                    '</td></tr>';
                                _table.find('tbody').append($compile(_tr)(scope));
                                element.find('.report-data-box').prepend(_table); // 添加到 表格
                            }

                            function createFixedTableR(name, len, thLength) {
                                var _table = $('<table class="report-data fixedTable ' + name + '" id="_' + name + '"><thead><tr></tr></thead><tbody></tbody></table>');
                                for (len; len < thLength; len++) {
                                    _table.find('thead tr').append($(th[len]).clone(true));// th 取原来表格的th 可以保持宽度样式一致
                                }
                                var _tr = '<tr ng-repeat=" item in dataList.data track by $index "  ng-click="rowClick($index)" ng-class="{active:($index + ((pageNumber-1) * paging.pageSize))===rowIndex}">' +
                                    '<td ng-repeat="td in ' + name + ' track by $index" data-field-name="{{td.value}}" ng-style="autoFormatStyle(item[td.value],td.style)">' +
                                    '<span class="report-td" title="{{ item[td.value] }}" ng-if="!td.callback && !td.boolean && !td.time" ng-bind="item[td.value] || \'　\'"></span> ' +
                                    '<span class="report-td" title="{{ item[td.value] }}" ng-if="!td.callback && !td.boolean && td.time" ng-bind="item[td.value] | formatDate: \'YYYY-MM-DD\'"></span> ' +
                                    '<span ng-if="!td.callback && td.boolean" ng-bind-html="item[td.value] | columnBoolean:td.boolean"></span>' +
                                    ' <a href="javascript:" class="report-td" title="{{ item[td.value] }}" ng-if="td.callback && !td.boolean" ng-click="callback(td.callback,item)">{{ item[td.value] }}</a>' +
                                    '<td ng-if="funcColumn.length >0"><a ng-repeat="icon in funcColumn" type="button" ng-click="callback(icon.callback,item,$index)" style="cursor:pointer;white-space:nowrap"><i ng-class="icon.icon" class="fa"></i>{{icon.text || icon.value}}</a></td>' +
                                    '</td></tr>';
                                _table.find('tbody').append($compile(_tr)(scope));
                                element.find('.report-data-box').prepend(_table); // 添加到 表格
                            }


                        }

                        element.find('.report-data tbody tr').hover(function () {
                            var hoverIndex = $(this).parent().find('tr').index($(this));
                            //element.find('.report-data tbody tr').removeClass('inhover');
                            hoverTR(hoverIndex, 'add');

                        }, function () {
                            var hoverIndex = $(this).parent().find('tr').index($(this));
                            //$(this).removeClass('inhover');
                            //$('.report-data tbody tr').eq(hoverIndex).removeClass('inhover')

                            hoverTR(hoverIndex, 'remove');
                        });

                        function hoverTR(index, action, cls) {
                            //element.find('.report-data tbody tr').removeClass('inhover');
                            element.find('.report-data').each(function () {
                                if (action === 'add') {
                                    $(this).find('tbody tr').eq(index).addClass('inhover');
                                } else {
                                    $(this).find('tbody tr').eq(index).removeClass('inhover');
                                }
                            })
                        }
                    }

                    angular.element($window).bind('resize', function () {
                        var boxWidth = element.width();
                        var tableWidth = element.find('#data_table_main_' + scope.id)[0].offsetWidth;
                        if (boxWidth < tableWidth) { //  表格超出范围了。要固定列了。
                            setFixedColumn();
                            $(".fixedTable").show();
                        } else {
                            $(".fixedTable").hide();
                        }
                    });


                }


            }
        })

        /*
         * by chengzhinjian 2018年8月17日 表格添加操作集成
         *  data-legend="[{'value':'Amount'},
         *  {'value':'PeriodAmount','name':'状态','style':'textAlign: left;color:red;'date':'yyyy-MM-dd HH:mm:ss',,rowClassName:'text-left',maxlength:'2',placeholder:'请输入'}
         * * {key:'startDate',label:'开始日期',type:'time','date':'yyyy-mm-dd hh:ii:ss' ,endDate:'endDate',isToday:true}         *
         * {key:'endDate',label:'结束日期',type:'time','date':'yyyy-mm-dd hh:ii:ss',startDate:'startDate',requibase-data-table-editred:true,validFormat:'email',validOtherFormat:[{key:'point',value:'4'}]}
         *  type:'lookCode/lov/time/operation',lookCode:'','disabled':true， readonly:true,target:'',required:true,validFormat:'number='7'',callback:'callback'
         *  type:operation 横向计算  operationStr:'+/-/*'运算符，operationKey:[value1,value2] 相关联的运算字段 需要计算的value必须为数字类型 value值
         *
         * data-func-column="[{'icon':'fa fa-remove','text':'事件名称','callback':'function'}]" // 功能列表
         *  action 处理 编辑状态 (新增，查看，)
         *  data-form-params-key ngModel的key值 如ngModel[key]
         *  返回 scope[baseDataTableEdit]  ngModel绑定对象
         *   data-delete-show='false'
         *   data-show-total="true" 合计
         *       data-count-key="['assetPrice','assetQuantity']"需要通过计算的key;
         *   isEdit
         *   data-second="[
         {name:'资产类别1',required:true,type:'lable',colspan:2},
         {value:'bbb',name:'资产类别2',colspan:5,required:true},
         ]" 次标题必须colspan  type:'lable' lable类型为显示字段
         data-is-edit="isEdit" 是否可编辑  $scope.isEdit=true true:不可编辑

         *　　    scope[baseDataTableEdit]={
         *                  addTableRows: 添加行方法
         *                  relief: 删除行方法
         *          }
         *
         * */
        .directive('baseDataTableEdit', function (SortBy, PageRows, TotalRows, httpServer, URLAPI, $timeout, $window, $rootScope, $filter, validateForm,
                                                  TableToExcel, SIEJS, $compile, IsArray, $sce, dragTable) {
            return {
                restrict: 'EA',
                replace: false,
                scope: {
                    ngModel: '=',
                    isEdit: '=',
                    dynamicData: '='
                },
                transclude: true,
                templateUrl: 'js/directives/html/data-table-edit.html',
                link: function (scope, element, attrs, controller, transclude) {
                    /*if (scope.$parent.action) {
                     switch (scope.$parent.action) {

                     case 'new'://新增
                     scope.action = false;
                     break;
                     case 'edit'://编辑
                     scope.action = false;
                     break;
                     case 'approval'://待审批
                     scope.action = true;
                     break;
                     case 'unread'://待阅
                     scope.action = true;
                     break;
                     case 'detail':// 查看
                     scope.action = true;
                     break;
                     default:
                     scope.action = false;
                     console.warn('action没有匹配成功')
                     }
                     }*/
                    //影响html
                    //transclude(scope,function(clone){
                    //    var html=''
                    //    for (var i = 0; i < clone.length; i++) {
                    //        if (clone[i].outerHTML) html += clone[i].outerHTML;
                    //        else if (clone[i].wholeText) html += clone[i].wholeText;
                    //    }
                    //    console.log(html)
                    //})
                    console.log(123, attrs);
                    if (attrs.startDisabled) {
                        scope.startDisabled = attrs.startDisabled == 'true' ? true : false;
                        console.log(scope.startDisabled)

                    }
                    if (!attrs.baseDataTableEdit) {
                        SIEJS.alert('请设置 baseDataTableEdit的参数', "warning", null, "请设置base-data-table-edit参数名称");
                        return;
                    }
                    if (!attrs.ngModel) {
                        SIEJS.alert('请设置 baseDataTableEdit的参数', "warning", null, "请设置base-data-table-edit绑定的参数");
                        return;
                    }
                    if (!attrs.formParamsKey) {
                        SIEJS.alert('请设置 baseDataTableEdit的参数', "warning", null, "请设置ngModel绑定的key,ngModel[formParamsKey]");
                        return;
                    }


                    var name = attrs.baseDataTableEdit;
                    scope.name = attrs.ngModel;
                    scope.selectRow = -1;
                    scope.selectRows = [];
                    scope.second = scope.$eval(attrs.second);
                    scope.operationKey = {};

                    if (typeof scope.$parent[name] === 'undefined') {
                        scope.$parent[name] = {};
                    }
                    scope.selectClick = function (index) {
                        scope.$apply(function () {
                            scope.selectRow = index;
                        })
                    };
                    var modelKey = attrs.formParamsKey;
                    scope.modelKey = modelKey;
                    scope.modelKeyTotal = modelKey + 'Total';

                    scope.legend = scope.$eval(attrs.legend);
                    // 是否显示分页
                    scope.pagingShow = !attrs.pagingShow || attrs.pagingShow === 'true' ? true : false;
                    console.log('pagingShow', attrs.pagingShow, scope.pagingShow);
                    scope.paging = {
                        currentPage: 1, //默认显示第一页
                        total: 0,
                        pageSize: attrs.pageSize ? parseInt(attrs.pageSize) : PageRows(element) //默认条数
                    };

                    scope.pageNumber = 0;

                    scope.funcColumn = scope.$eval(attrs.funcColumn);
                    //是否显示合计
                    scope.showTotal = attrs.showTotal == 'true' ? true : false;
                    //是否横向计算
                    scope.operationSwitch = false;


                    //是否显示操作删除  attrs.deleteShow给值不显示操作
                    scope.deleteShow = attrs.deleteShow == 'false' ? false : true;
                    //debugger;
                    scope.obj = {};
                    scope.textAlign = function (item) {
                        if (item.textAlign) {
                            switch (item.textAlign) {
                                case 'left':
                                    // console.log(item)
                                    return styleObj = {
                                        'text-align': 'left'
                                    }
                            }
                        }

                    };
                    if (!scope.ngModel[modelKey]) {
                        scope.model = scope.ngModel[modelKey] = [{}];
                    } else {
                        scope.model = scope.ngModel[modelKey]
                    }

                    scope.totalInit = function () {
                        if (scope.showTotal) {
                            if (!attrs.formParamsKey) {
                                SIEJS.alert('请设置 baseDataTableEdit的参数', "warning", null, "请设置你需要计算的legend,key值,如[legendKey1,legendKey2]");
                                return
                            }
                            scope.totalKey = scope.$eval(attrs.countKey);
                            scope.ngModel[scope.modelKeyTotal] = [{}];
                            scope.legend.map(function (item, itemIndex) {
                                scope.ngModel[scope.modelKeyTotal][0][item.value + 'Total'] = '';
                            })
                        }
                    };
                    //处理验证指令
                    scope.init = function () {

                        $timeout(function () {
                            //if(JSON.stringify(scope.model).indexOf('[{}]')!=-1) return;
                            scope.ngModel[modelKey].map(function (datat, index) {
                                scope.legend.map(function (item, itemIndex) {
                                    if (item.type == 'operation') {
                                        if (!item.operationKey) {
                                            SIEJS.alert('请设置 baseDataTableEdit的参数', "warning", null, '请设置横向计算operationKey:[value1,value2]');
                                            return
                                        }
                                        if (!item.operationStr) {
                                            SIEJS.alert('baseDataTableEdit的参数', "warning", null, "请设置横向计算运算符operationStr:'+、-、*'运算符参数");
                                            return
                                        }
                                        var okey = item.operationKey;
                                        //scope.ngModel[modelKey][index][item.value]=0
                                        //处理赋值 新增BUG
                                        if (!scope.ngModel[modelKey][index][item.value]) {
                                            //scope.ngModel[modelKey][index][item.value] = 0
                                        }
                                        for (var i = 0; i < okey.length; i++) {
                                            scope.operationKey[okey[i]] = {
                                                'operationStr': item.operationStr,
                                                'value': item.value,
                                                'operationAttr': item.operationKey
                                            }
                                        }

                                        scope.operationSwitch = true
                                    }

                                    if (item.validFormat || item.maxlength) {
                                        //debugger;
                                        if (scope.showTotal) {
                                            //scope.model.totalData[0][item.value+'Total']=0
                                        }

                                        var dom = $(element).find('#' + scope.name + '_' + modelKey + '_' + item.value + '_' + index);
                                        //处理赋值 新增BUG
                                        if (!scope.ngModel[modelKey][index][item.value]) {
                                            //scope.ngModel[modelKey][index][item.value]=0
                                        }


                                        //处理验证指令，自定义参数
                                        if (item.validOtherFormat) {
                                            for (var i = 0; i < item.validOtherFormat.length; i++) {
                                                dom.attr(item.validOtherFormat[i].key, item.validOtherFormat[i].value);
                                            }
                                        }
                                        if (item.maxlength) {
                                            dom.attr('max-length', item.maxlength);
                                        }
                                        if (item.validFormat) {
                                            var validFormat = item.validFormat.split('=');
                                            dom.attr(validFormat[0], validFormat[1] || '');
                                            dom.attr('ng-keyup', 'inputKeyUp($event,"' + item.value + '",' + index + ')');
                                        }
                                        if (item.callback) {
                                            dom.attr('ng-change', 'inputCallback("' + item.callback + '",' + index + ')');
                                        }
                                        if (item.required) {
                                            dom.attr('ng-required', item.required);
                                        } else if (dom.attr('ng-required')) { //
                                            dom.attr('ng-required', 'isRequired(' + JSON.stringify(item) + ')')
                                        }

                                        if (item.disabled) dom.attr('ng-disabled', item.disabled);
                                        if (item.readonly) dom.attr('ng-required', item.readonly);
                                        if (item.placeholder) dom.attr('placeholder', item.placeholder);
                                        dom.attr('ng-model', 'model["' + index + '"]["' + item.value + '"]');
                                        if (scope.startDisabled) {
                                            if (datat.status == 'Y') {
                                                dom.attr('ng-disabled', true);
                                            }
                                           // debugger
                                        }
                                        //dom.attr('name', scope.name + '_' + modelKey + '_' + item.value + '_' + index);
                                        $compile(dom)(scope);
                                    }
                                });
                                if (scope.second) {
                                    if (scope.second.length) {
                                        scope.second.map(function (cell, itemIndex) {
                                            cell.items.map(function (item, condeindex) {
                                                if (!scope.model[index][item.value]) {
                                                    if (item.type == 'operation') {
                                                        if (!item.operationKey) {
                                                            SIEJS.alert('请设置 baseDataTableEdit的参数', "warning", null, '请设置横向计算operationKey:[value1,value2]');
                                                            return
                                                        }
                                                        if (!item.operationStr) {
                                                            SIEJS.alert('baseDataTableEdit的参数', "warning", null, "请设置横向计算运算符operationStr:'+、-、*'运算符参数");
                                                            return
                                                        }
                                                        var okey = item.operationKey;
                                                        //scope.ngModel[modelKey][index][item.value]=0
                                                        //处理赋值 新增BUG
                                                        if (!scope.ngModel[modelKey][index][item.value]) {
                                                            //scope.ngModel[modelKey][index][item.value] = 0
                                                        }
                                                        for (var i = 0; i < okey.length; i++) {
                                                            scope.operationKey[okey[i]] = {
                                                                'operationStr': item.operationStr,
                                                                'value': item.value,
                                                                'operationAttr': item.operationKey
                                                            }
                                                        }

                                                        scope.operationSwitch = true
                                                    }

                                                    if (item.validFormat || item.maxlength) {
                                                        //debugger;
                                                        if (scope.showTotal) {
                                                            //scope.model.totalData[0][item.value+'Total']=0
                                                        }

                                                        var dom = $(element).find('#' + scope.name + '_' + modelKey + '_' + item.value + '_' + index);
                                                        //处理赋值 新增BUG
                                                        if (!scope.ngModel[modelKey][index][item.value]) {
                                                            //scope.ngModel[modelKey][index][item.value]=0
                                                        }


                                                        //处理验证指令，自定义参数
                                                        if (item.validOtherFormat) {
                                                            for (var i = 0; i < item.validOtherFormat.length; i++) {
                                                                dom.attr(item.validOtherFormat[i].key, item.validOtherFormat[i].value);
                                                            }
                                                        }
                                                        if (item.maxlength) {
                                                            dom.attr('max-length', item.maxlength);
                                                        }
                                                        if (item.validFormat) {
                                                            var validFormat = item.validFormat.split('=');
                                                            dom.attr(validFormat[0], validFormat[1] || '');
                                                            dom.attr('ng-keyup', 'inputKeyUp($event,"' + item.value + '",' + index + ')');
                                                        }
                                                        if (item.callback) {
                                                            dom.attr('ng-change', 'inputCallback("' + item.callback + '",' + index + ')');
                                                        }
                                                        if (item.required) {
                                                            dom.attr('ng-required', item.required);
                                                        } else if (dom.attr('ng-required')) { //
                                                            dom.attr('ng-required', 'isRequired(' + JSON.stringify(item) + ')')
                                                        }

                                                        if (item.disabled) dom.attr('ng-disabled', item.disabled);
                                                        if (item.readonly) dom.attr('ng-required', item.readonly);
                                                        if (item.placeholder) dom.attr('placeholder', item.placeholder);
                                                        if (item.type == 'textarea') {//
                                                            dom.attr('ng-model', 'model["' + index + '"]["' + item.value + '"]');
                                                            //$(dom).html("{{model["+ index + "]["+ item.value + "]}}")

                                                        } else {
                                                            dom.attr('ng-model', 'model["' + index + '"]["' + item.value + '"]');
                                                        }
                                                        //dom.attr('ng-model', 'model["' + index + '"]["' + item.value + '"]');
                                                        //dom.attr('name', scope.name + '_' + modelKey + '_' + item.value + '_' + index);
                                                        $compile(dom)(scope);
                                                    }
                                                }

                                            })
                                        })
                                        /* scope.second.map(function (item, itemIndex) {
                                         item.items.map(function (cell,condeindex) {
                                         if (cell.validFormat||cell.maxlength) {
                                         if (scope.showTotal) {
                                         //scope.model.totalData[0][item.value+'Total']=0
                                         }
                                         var dom = $(element).find('#' + scope.name + '_' + modelKey + '_' + cell.value + '_' + index);
                                         console.log(dom)
                                         ////处理赋值 新增BUG
                                         //if (!scope.ngModel[modelKey][index][item.value]) {
                                         //    //scope.ngModel[modelKey][index][item.value]=0
                                         //}
                                         //
                                         //
                                         //处理验证指令，自定义参数
                                         if (cell.validOtherFormat) {
                                         for (var i = 0; i < cell.validOtherFormat.length; i++) {
                                         dom.attr(cell.validOtherFormat[i].key, cell.validOtherFormat[i].value);
                                         }
                                         }
                                         //
                                         if (cell.maxlength) {
                                         dom.attr('max-length', cell.maxlength);
                                         }
                                         if (cell.validFormat) {
                                         var validFormat = cell.validFormat.split('=');
                                         dom.attr(validFormat[0], validFormat[1] || '');
                                         dom.attr('ng-keyup', 'inputKeyUp($event,"' + cell.value + '",' + index + ')');
                                         }
                                         if (cell.callback) {
                                         dom.attr('ng-change', 'inputCallback("' + cell.callback + '",' + index + ')');
                                         }
                                         if (cell.required) {
                                         dom.attr('ng-required', cell.required);
                                         } else if (dom.attr('ng-required')) { //
                                         dom.attr('ng-required', 'isRequired(' + JSON.stringify(cell) + ')')
                                         }

                                         if (cell.disabled) dom.attr('ng-disabled', cell.disabled);
                                         if (cell.readonly) dom.attr('ng-required', cell.readonly);
                                         if (cell.placeholder) dom.attr('placeholder', cell.placeholder);

                                         dom.attr('ng-model', 'ngModel["' + modelKey + '"]["' + index + '"]["' + cell.value + '"]');
                                         $compile(dom)(scope);
                                         }
                                         })

                                         })*/
                                    }
                                }

                            })

                        }, 200)
                    };
                    scope.init();
                    scope.totalInit();
                    scope.inputKeyUp = function (e, item, index) {
                        if (!scope.model[index][item] && scope.model[index][item] !== undefined) {
                            if (!$(e.currentTarget).hasClass('border-red')) {
                                $(e.currentTarget).addClass('border-red')
                            }
                        } else {
                            $(e.currentTarget).removeClass('border-red');
                            /*if(attrs.operationKey){
                             console.log(scope.operationKey.indexOf(item));
                             angular.forEach(scope.operationKey,function(data,index){
                             angular.forEach(data[index].operationAttr,function(data2,index2){

                             })
                             })
                             }*/
                            if (scope.operationSwitch && scope.operationKey[item] && $(e.currentTarget).attr('number') !== undefined) {
                                scope.calculation(item, index);
                            }
                            if (!scope.showTotal) return;
                            if (scope.totalKey.indexOf(item) != -1 && $(e.currentTarget).attr('number') !== undefined) {
                                scope.totalfn(item)
                            }
                        }
                    };
                    //计算横向
                    scope.calculation = function (item, itemIndex) {
                        var operationAttr = scope.operationKey[item].operationAttr;
                        var operationStr = scope.operationKey[item].operationStr;
                        var number = 1;
                        //处理运算基准
                        switch (operationStr) {
                            case '*':
                                number = 1;
                                break;
                            case '-':
                            case '+':
                                number = 0;
                                break;
                        }
                        scope.ngModel[modelKey].map(function (data, index) {
                            if (data[item] != undefined) {
                                if (index == itemIndex) {
                                    for (var j = 0; j < operationAttr.length; j++) {
                                        switch (operationStr) {
                                            case '*':
                                                number = number * Number(data[operationAttr[j]]);
                                                break;
                                            case '+':
                                                number = number + Number(data[operationAttr[j]]);
                                                break;
                                            case '-':
                                                if (j == 0) {
                                                    number = Number(data[operationAttr[j]]);
                                                } else {
                                                    number = number - Number(data[operationAttr[j]]);
                                                }
                                                break;
                                        }

                                    }
                                }
                            }
                        });
                        number = Math.floor(number * 100) / 100;
                        //赋值 和 求和操作
                        scope.ngModel[modelKey][itemIndex][scope.operationKey[item].value] = number;
                        scope.totalfn(scope.operationKey[item].value)

                    };
                    //计算总数
                    scope.totalfn = function (item) {
                        var number = 0;
                        scope.ngModel[modelKey].map(function (data, index) {
                            if (data[item] != undefined) {
                                var n = isNaN(data[item]) ? 0 : Number(data[item]);
                                number += Number(n)
                            }
                        });
                        number = Math.floor(number * 100) / 100;
                        scope.ngModel[scope.modelKeyTotal][0][item + 'Total'] = number;

                    };
                    scope.textAlign = function (item) {
                        if (item.textAlign) {
                            switch (item.textAlign) {
                                case 'left':
                                    console.log(item);
                                    return styleObj = {
                                        'text-align': 'left'
                                    }
                            }
                        }

                    };
                    //对外暴露添加行方法
                    scope.$parent[name].addTableRows = function (obj) {
                        var data = obj ? obj : {};
                        scope.model.push(data);
                        console.log(scope.model);
                        scope.init();

                    };
                    //对外暴露删除方法
                    scope.$parent[name].relief = function (index) {
                        // console.log()
                        SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                            scope.model.splice(index, 1);
                            if (attrs.deleteCallback) {
                                if (typeof scope.$parent[attrs.deleteCallback] !== 'function') {
                                    alert('data-delete-callback 参数错误, 不是一个function')
                                } else {
                                    scope.$parent[attrs.deleteCallback](index);
                                }
                            }

                        })

                    };
                    //本身删除功能
                    scope.relief = function (index) {
                        SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                            scope.model.splice(index, 1);
                            if (scope.showTotal) {
                                scope.init();
                                scope.totalInit();
                                $timeout(function () {
                                    scope.totalKey.map(function (data, index) {
                                        scope.totalfn(data);

                                    })
                                })

                            }
                            scope.$apply();
                            if (attrs.deleteCallback) {
                                if (typeof scope.$parent[attrs.deleteCallback] !== 'function') {
                                    alert('data-delete-callback 参数错误, 不是一个function')
                                } else {
                                    scope.$parent[attrs.deleteCallback](index);
                                }
                            }
                        })
                    };
                    scope.callback = function (callbackName, index, item) {
                        if (!scope.$parent[callbackName]) return;
                        if (typeof scope.$parent[callbackName] !== 'function') {
                            alert('data-callback 参数错误, ' + callbackName + '不是一个function')
                        } else {
                            scope.$parent[callbackName](index, item);
                        }
                    };
                    scope.inputCallback = function (callbackName, index, item) {
                        if (!callbackName) {
                            return
                        }
                        if (typeof scope.$parent[callbackName] !== 'function') {
                            alert('data-callback 参数错误, ' + callbackName + '不是一个function')
                        } else {
                            scope.$parent[callbackName](index, item);
                        }
                    };
                    scope.funcCallback = function (callbackName, index, item) {
                        if (typeof scope.$parent[callbackName] !== 'function') {
                            alert('data-callback 参数错误, ' + callbackName + '不是一个function')
                        } else {
                            scope.$parent[callbackName](index, item);
                        }
                    };
                    $timeout(function () {
                        angular.forEach(scope.legend, function (data, index) {
                            scope.obj[data.value] = ''
                        })
                    }, 50);


                    // 日期格式
                    scope.dateFormat = function (format) {
                        return format || 'yyyy-mm-dd';
                    };

                    // 日期视图
                    scope.minView = function (format) {
                        format = format || 'yyyy-mm-dd';
                        format = format.toLowerCase();
                        var i = 2;
                        switch (format) {
                            case 'yyyy-mm-dd hh:ii:ss':
                                i = 0;
                                break;
                            case 'yyyy-mm-dd':
                                i = 2;
                                break;
                            case 'yyyy-mm':
                                i = 3;
                                break;
                            case 'hh:ii:ss':
                                i = 1;
                                break;
                        }
                        return i;
                    };

                    scope.startView = function (format) {
                        format = format || 'yyyy-mm-dd';
                        format = format.toLowerCase();
                        var i = 2;
                        switch (format) {
                            case 'yyyy-mm-dd hh:ii:ss':
                            case 'yyyy-mm-dd':
                                i = 2;
                                break;
                            case 'yyyy-mm':
                                i = 3;
                                break;
                            case 'hh:ii:ss':
                                i = 0;
                                break;
                        }
                        return i;
                    };
                    scope.w = scope.$watch('ngModel', function (newValue, oldValue) {
                        if (newValue === oldValue) return;
                        //return;
                        //$timeout(function () {
                        if (newValue) {
                            scope.model = newValue[modelKey];
                            console.log(scope.model);
                            if (scope.showTotal) {
                                scope.init();
                                scope.totalInit();
                                $timeout(function () {
                                    scope.totalKey.map(function (data, index) {
                                        scope.totalfn(data);

                                    })
                                })

                            }
                        }
                        //}, 100)

                    });
                    if (attrs.dynamicData) {
                        scope.w2 = scope.$watch('dynamicData', function (nval, oval) {
                            if (nval === oval) return;
                            console.log(nval === oval);
                            scope.ngModel[modelKey] = scope.model = nval;
                            scope.init();
                            if (scope.showTotal) {
                                //scope.init();
                                scope.totalInit();
                                $timeout(function () {
                                    scope.totalKey.map(function (data, index) {
                                        scope.totalfn(data);

                                    })
                                })

                            }
                        });
                        scope.$on("$destroy", function () {
                            scope.w2();
                        })
                    }
                    scope.wIsEdit = scope.$watch('isEdit', function (nval, oval) {
                        if (nval === oval) return;
                        if (nval) {
                            scope.isEdit = nval
                        }
                    }, true);
                    scope.$on('$destroy', function () {
                        scope.w();
                        scope.wIsEdit();
                    });
                    scope.addMutiRows = function (item) {
                        scope.selectRows.push(item)
                    };
                    scope.delMutiRows = function (id) {
                        scope.selectRows.splice(id, 1)
                    };
                    scope.mutiRowsSelect = function (item, id) {
                        if (scope.selectRows.indexOf(item) >= 0) {
                            scope.delMutiRows(id)
                        } else {
                            scope.addMutiRows(item)
                        }
                        scope.$parent[name].selectRows = scope.selectRows;
                        console.log(scope.selectRows)
                    }
                    /* if(!attrs.baseDataTableEndit){
                     SIEJS.alert('请设置base-data-table-endit参数名称');
                     }
                     var name=attrs.baseDataTableEndit
                     if (!attrs.legend) {
                     SIEJS.alert('请设置data-legend参数');
                     return
                     }
                     scope.legend=scope.$eval(attrs.legend)
                     scope.obj={}
                     angular.forEach(scope.legend,function(data,index){
                     scope.obj[data.value]=''
                     })

                     scope.ngModel['data']=[]
                     scope.ngModel['selectRow']=-1
                     scope.selectClick=function(index){
                     scope.$apply(function(){
                     scope.ngModel['selectRow']=index
                     })

                     }
                     scope.$parent[name].add=function(){
                     scope.ngModel.data.push(scope.obj)
                     }
                     scope.$parent[name].relief=function(){
                     scope.ngModel.data.splice(scope.ngModel.data.length-1,1)
                     }*/


                }
            }
        })
        // baseDataTable 子表查询
        /*
         * data-url=""   请求的API
         * data-key="userId"   查询的参数主键
         * data-value="110"  查询的值
         * data-page-size="5"  分页
         * data-legend="[]"  绑定的字段
         * parent-table="dataTable"  父级表格名
         * ng-params="{transactionId:57} ;  查询的参数
         * */
        .directive('subBaseDataTable', function (SIEJS, $compile, $window, $timeout) {
            return {
                require: "?ngModel",
                restrict: 'EA',
                replace: true,
                templateUrl: 'js/directives/html/sub-data-table-tpl.html',
                scope: {
                    parentTable: '=',
                    ngParams: '='
                },
                link: function (scope, element, attrs, ngModel) {


                    /* if (!attrs.key) {
                     SIEJS.alert('请设置 data-key 参数', 'error');
                     return;
                     }
                     */

                    if (!attrs.ngParams) {
                        scope.ngParams = {}
                    }

                    if (!attrs.parentTable) {
                        SIEJS.alert('请设置 parent-table 参数', 'error');
                        return;
                    }

                    if (!attrs.legend) {
                        SIEJS.alert('请设置 data-legend 参数', 'error');
                        return;
                    }

                    scope.legend = scope.$eval(attrs.legend);

                    if (!attrs.url) {
                        SIEJS.alert('请设置 data-url 参数', 'error');
                        return;
                    }
                    scope.id = attrs.subBaseDataTable;
                    scope.pageSize = attrs.pageSize ? parseInt(attrs.pageSize) : 5;// 分页大小;
                    scope.url = attrs.url;
                    scope.key = attrs.key;
                    //scope.tableParams = {};


                    scope.subTable = scope.$watch('ngParams', function (nval, oval) {


                        if (!nval || nval === oval) {
                            return;
                        }
                        //log(nval);
                        //scope.tableParams[scope.key] = nval;
                        var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight; // 浏览器可视高度
                        var top = getElemPos(scope.parentTable.currentDom).y;// 当前行距离顶部
                        var rh = scope.parentTable.currentDom.offsetHeight;// 当前行的高度

                        if ((top + rh / 2) > h / 2) { // 当前行在屏幕下方

                            scope.position = 'bottom';
                            scope.tableStyle = {
                                bottom: h - top + 6,  // 6 为箭头的高度 偏移量
                                top: 'auto'
                            }

                        } else {
                            scope.position = 'top';
                            scope.tableStyle = {
                                top: top + rh + 6,  // 6 为箭头的高度, 偏移量
                                bottom: 'auto'
                            }
                        }
                        $timeout(function () {
                            scope[scope.id].search(1);
                        }, 100);
                        scope.tableShow = true;
                    }, true);
                    function getElemPos(obj) {
                        var pos = { "top": 0, "left": 0 };
                        if (obj.offsetParent) {
                            while (obj.offsetParent) {
                                pos.top += obj.offsetTop;
                                pos.left += obj.offsetLeft;
                                obj = obj.offsetParent;
                            }
                        } else if (obj.x) {
                            pos.left += obj.x;
                        } else if (obj.x) {
                            pos.top += obj.y;
                        }
                        return { x: pos.left, y: pos.top };
                    }

                    scope.$on("$destroy", function () {
                        scope.subTable(); //　销毁监听
                    });
                    // mouseleave  mouseout
                    element.on('mouseleave', function (e) {
                        scope.$apply(function () {
                            scope.tableShow = false;
                            scope[scope.id].selectRow = null;
                        })

                    });


                }
            }
        })
        /*日期控件
         * data-is-today="true" // 默认显示为今天
         * data-stratdate="2016-12-1" 开始时间
         * data-enddate='2016-12-11'结束时间
         * data-min-view=2 最小视图
         * */
        .directive('dateTimePicker', function ($filter, $timeout) {
            return {
                require: "?ngModel",
                restrict: 'EA',
                scope: {
                    ngModel: '='
                },

                link: function (scope, element, attrs, ngModel) {
                    $timeout(function () {
                        element.datetimepicker({
                            language: 'zh-CN',
                            todayBtn: 1,
                            autoclose: 1,
                            todayHighlight: 1
                        });
                        element.on('focus', function (e) {
                            element.datetimepicker("setStartDate", attrs.stratdate == undefined ? null : attrs.stratdate);
                            element.datetimepicker("setEndDate", attrs.enddate == undefined ? null : attrs.enddate);
                        });
                    });

                    var format = 'yyyy-MM-dd';
                    switch (attrs.minView) {
                        case '0':
                            format = 'yyyy-MM-dd HH:mm:ss';
                            break;
                        case '1':
                            format = 'yyyy-MM-dd';
                            break;
                        case '2':
                            format = 'yyyy-MM-dd';
                            break;
                        case '3':
                            format = 'yyyy-MM';
                            break;
                        case '4':
                            format = 'yyyy';
                            break;
                        case '5':
                            format = 'MM';
                            break;
                        case '6':
                            format = 'dd';
                            break;
                    }


                    if (attrs.isToday === 'true' && !scope.ngModel) {
                        var today = new Date();
                        $timeout(function () {
                            scope.ngModel = $filter('date')(today, format);
                        })
                    }


                }
            }
        })

        .directive('searchInput', function ($timeout) {
            return {
                require: "?ngModel",
                restrict: 'EA',
                scope: {
                    ngModel: '='
                },

                link: function (scope, element, attrs, ngModel) {
                    var r = /^\s+|\s+$/gim;
                    element.bind('blur', function (s) {
                        var val = s.currentTarget.value;
                        if (val) {
                            s.currentTarget.value = val.replace(r, '');
                        }
                    });
                    element.bind('click', function (s) {
                        $(this).select();
                    })
                }
            }
        })


        // 单选按钮组
        /*  参数说明：
         * 　data-label="标签名称"
         *   data-url="apiURL"  数据请求服务
         *   data-params=" { name:$name$ }" 请求参数　可动态数据
         *   data-watch="name" 监听此值从而重载数据
         *   data-text="数据显示字段"
         *   data-value="数据值字段"
         *   data-method="post" 请求方式
         *   ng-model="params.city" 双向绑定参数
         *   data-component-type="radio"  // 目前提供三种类型　radio \ checkbox \ select
         * */
        .directive('listComponent', function (httpServer, URLAPI, $timeout) {
            return {
                restrict: 'EA',
                replace: false,
                templateUrl: 'baseModel/components/list-component-tpl.html',
                scope: {
                    ngModel: '=' // 指令与父级的双向绑定
                },
                link: function (scope, element, attrs, controller) {

                    scope.itemList = [{ text: '数据001', value: '001' }, { text: '数据002', value: '002' }];
                    scope.labelName = attrs.label || '列表组件';
                    scope.text = attrs.text || 'text'; // 绑定 数据显示字段
                    scope.value = attrs.value || 'value'; // 绑定 数据显示字段
                    scope.name = attrs.name || 'id' + new Date().getTime();
                    scope.type = attrs.componentType || 'radio';
                    scope.url = attrs.url;


                    scope.current = {};

                    scope.search = function () {
                        var queryParams = attrs.params;
                        var reg = new RegExp(/\$[^\$\s\,]+\$/, "gi");
                        if (queryParams) {
                            var r = queryParams.match(reg);
                            if (r) {
                                for (var n in r) {
                                    queryParams = queryParams.replace(r[n], '"' + (scope.$parent.params[r[n].replace(/\$/g, '')] || '') + '"')
                                    //queryParams=queryParams.replace(r[n], 'scope.$parent.params.' + r[n].replace(/\$/g,''))
                                }
                            }
                            queryParams = scope.$eval(queryParams);
                        } else {
                            queryParams = {}
                        }

                        var p = {
                            params: JSON.stringify(queryParams)
                        };
                        if (scope.url) {
                            httpServer.post(URLAPI[scope.url], attrs.method || 'post', p, function (res) {
                                scope.itemList = res.data;
                            })
                        }
                    };

                    scope.search();// 数据查询

                    if (attrs.watch) {
                        scope.w = scope.$watch(function () {
                            return scope.$parent.params[attrs.watch]
                        }, function (nval, oval) {
                            if (nval === oval) return;
                            if (scope.timer) $timeout.cancel(scope.timer);
                            scope.timer = $timeout(function () {
                                scope.search();// 数据查询

                            }, 1000)
                        });

                        scope.$on("$destroy", function () {
                            scope.w(); //　销毁监听
                        })
                    }

                    // 列表点击
                    scope.itemClick = function () {
                        scope.ngModel = scope.current.checked;
                    };

                    // checkbox 专属　*****************************************************************************
                    scope.selected = [];
                    scope.isChecked = function (id) {
                        return scope.selected.indexOf(id) >= 0;
                    };
                    scope.changeChecked = function ($event, id) {
                        var checkbox = $event.target;
                        if (checkbox.checked) { // 选中
                            scope.selected.push(id);
                        } else {
                            var i = scope.selected.indexOf(id);
                            if (i > -1)
                                scope.selected.splice(i, 1);
                        }
                        scope.ngModel = scope.selected;
                        //log(JSON.stringify(scope.selected));
                    }

                }
            }
        })

        /* 　by houxingzhang  2017年7月12日02时51分22秒
         *  LOV 键值对　 用于展示更多复杂对象的列表进行选取
         *   data-show-index="true" 是否显示序号？默认显示
         *  data-params={} 请求所需要的参数
         *  dynamicParams: '=',  // 动态查询参数
         *  data-css="modal-lg"
         *  data-pagination="{'pageIndex':'1','pageRows':'10'}" 分页配置   /* 按需配置
         *  data-url="url" 请求ＵＲＬ  /* 必须
         *  data-checked-type=""   选择类型（radio单选、checkbox多选）   /* 按需配置,默认单选
         *  data-keys="{'key':'lookupCode','value':'meaning','name':'我是名称'}"
         *              设置绑定显示的键值    如：绑定当前的 lookupCode　到key 、 meaning到value , name:'显示名称' /* 必须
         *  data-other-column="[{'name':'渠道','key':'websiteName'}]"  // 显示其它字段信息       /* 按需配置
         *  data-search-key="{'key':'standardMovieName','placeholder':'请输入要搜索的影片名'}"   // 搜索的参数字段名及提示文本  /* 按需配置
         *　data-max-checked=3  /  多选情况下最多能选择的数量
         *  data-callback="function" // 用户确认后的回调  function(key, value, currentList)
         *              key 选中的key value 选中的text currentList 当前所有选的条目列表
         *  data-clear="function" //用户手动清除功能
         *  data-auto-request = 'true' //  与 data-url 组合使用，在组件加载时，是否自动去获取数据？ 默认是
         *  返回 ngModel　为一个对象，当data-check-type为单选时返回　{ key:'',value:''} , 当为多选时，返回 {key:[],value:[]}    /* 必须
         *  返回 scope[lovList] ={
         *       selectRow: 当前选择行 selectRowList 所有选中行
         *       search:  当前搜索
         *        cancel: function(){}  取消已选择的数据
         *      }
         *  返回 ng-model="params.standardCinemaName"  // 返回的参数
         *  返回 data-return-value="idReturnValue"  // 返回的文本值　显示到对应的input 上与当前input数据双向绑定
         &  返回 data-return-key="key"   // 返回的key值　
         * */
        .directive('lovList', function (httpServer, URLAPI, IsArray, $rootScope, $timeout) {
            return {
                require: '^ngModel',
                restrict: 'EA',
                replace: true,
                scope: {
                    ngModel: '=', // 指令与父级的双向绑定
                    returnValue: '=',  // 返回的value值
                    returnKey: '=', // 返回的key值
                    dynamicParams: '=',  // 动态查询参数
                    subTitle: '=' // 字标题

                },
                templateUrl: function (element, attrs) {
                    return 'js/directives/html/lov-list-tpl.html';
                    //return attrs.type && attrs.type === 'competitor' ? 'templates/bigData/components/competitor-tpl.html' : 'templates/bigData/components/bigdata-lov-tpl.html'
                },
                link: function (scope, element, attrs, controller) {

                    var lovlist = attrs.lovList || 'lovList'; // 当前gridTable的scope
                    scope.lovlist = lovlist;
                    if (typeof scope.$parent[lovlist] === 'undefined') {
                        scope.$parent[lovlist] = {};
                    }
                    scope.clear = attrs.clear;
                    scope.css = attrs.css;
                    scope.title = attrs.title || '请选择';
                    scope.checkedType = attrs.checkedType || 'radio';

                    scope.currentRadio = { // 当前单选选中的值
                        radioValue: 0
                    };
                    scope.currentList = [];// 当前已选出来的列表　

                    if (!scope.ngModel) {
                        scope.ngModel = {};
                    }

                    var _params = {};
                    if (attrs.params) {
                        _params = scope.$eval(attrs.params);
                    }

                    var _url = attrs.url;
                    if (!_url) {
                        alert('请设置 ' + scope.title + ' URL参数');
                        return;
                    }
                    scope.loading = {
                        show: false,
                        msg: '数据读取中....'
                    };
                    var ajaxLoading = false;
                    if (!attrs.keys) {
                        alert('请设置 ' + scope.title + ' 键值参数');
                        return
                    }
                    scope.keys = scope.$eval(attrs.keys);

                    if (attrs.otherColumn) {
                        scope.otherColumn = scope.$eval(attrs.otherColumn);
                        scope.$watch(function(){return attrs.otherColumn},function(nv){
                            if(nv)
                                scope.otherColumn = scope.$eval(nv);
                        })
                    }
                    scope.showIndex = true;
                    if (attrs.showIndex) {
                        scope.showIndex = attrs.showIndex && attrs.showIndex === 'false' ? false : true;
                    }
                    $('.modal').draggable();



                    // 设置返回的值
                    scope.setReturn = function () {
                        var _val = '';
                        if (attrs.returnValue) {
                            if (typeof scope.ngModel.value !== 'object') {
                                _val = scope.ngModel.value;
                            } else {
                                _val = scope.ngModel.value.join(',');
                            }
                            scope.returnValue = _val;
                        }
                        if (attrs.returnKey) {
                            if (typeof scope.ngModel.key !== 'object') {
                                _val = scope.ngModel.key;
                            } else {
                                _val = scope.ngModel.key.join(',');
                            }
                            scope.returnKey = _val;
                        }
                    };

                    // 多选
                    scope.check = function (item, e, t) {
                        var checked = e.currentTarget.checked; // 当前选中状态
                        if (typeof scope.ngModel !== 'object') {
                            scope.ngModel = {
                                key: [],
                                value: []
                            }
                        }
                        if (!IsArray(scope.ngModel.key)) { //　是否是数组
                            scope.ngModel = {
                                key: [],
                                value: []
                            }
                        }

                        // 判断　选中的数量
                        var _maxChecked = attrs.maxChecked || 0;
                        if (checked && _maxChecked > 0 && scope.ngModel.value.length >= _maxChecked) {
                            if (swal) {
                                swal({
                                    title: '最多能选' + _maxChecked + '个' + scope.title,
                                    type: 'warning'
                                })
                            } else {
                                alert('最多能选' + _maxChecked + '个' + scope.title);
                            }
                            e.currentTarget.checked = false;
                            return;
                        }


                        var currentIsPush = true;
                        var currentIndex = -1;
                        for (i = 0; i < scope.currentList.length; i++) {
                            if (item[scope.keys.key] === scope.currentList[i][scope.keys.key]) {
                                currentIsPush = false;
                                currentIndex = i;
                                break;
                            }
                        }

                        if (checked && currentIsPush && t !== false) {
                            item.checked = true; // 选中标识
                            scope.currentList.push(item)
                        }
                        if (!checked && !currentIsPush) {
                            item.checked = false; // 选中标识
                            scope.currentList.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－２０１８－１－９
                        }
                        if (checked && !currentIsPush) {
                            item.checked = true; // 选中标识
                        }

                        if (typeof scope.ngModel !== 'object') {
                            scope.ngModel = {
                                key: [],
                                value: []
                            }
                        }
                        if (!IsArray(scope.ngModel.key)) { //　是否是数组
                            scope.ngModel = {
                                key: [],
                                value: []
                            }
                        }

                        if (!scope.$parent[lovlist].selectRowList) {
                            scope.$parent[lovlist].selectRowList = [];
                        }

                        var isPush = true;
                        var arrayIndex = -1;
                        for (var i in scope.ngModel.key) {
                            if (scope.ngModel.key[i] === item[scope.keys.key]) {
                                isPush = false;
                                arrayIndex = i;
                                break;
                            }
                        }

                        if (isPush && checked) { // 增加数组
                            scope.ngModel.key.push(item[scope.keys.key]);
                            scope.ngModel.value.push(item[scope.keys.value]);

                            //scope.$parent[lovlist].selectRowList.push(item);

                        } else if (!isPush && !checked) {
                            scope.ngModel.key.splice(arrayIndex, 1);
                            scope.ngModel.value.splice(arrayIndex, 1);

                            //scope.$parent[lovlist].selectRowList.splice(arrayIndex, 1);
                        }

                        scope.$parent[lovlist].selectRowList = angular.copy(scope.currentList);  //  - -----------------------------------------注意

                        //log(lovlist, scope.$parent[lovlist].selectRowList) //
                        scope.setReturn();
                        if (attrs.callbackclick && typeof scope.$parent[attrs.callbackclick] === 'function') {
                            if (!scope.ngModel) return;
                            var returnFlag =   scope.$parent[attrs.callbackclick](scope.ngModel.key, scope.ngModel.value, scope.currentList);
                            if ('-1' == returnFlag.flag){
                                e.currentTarget.checked = false;
                                scope.check (item, e, t);

                            }
                        }
                    };
                    // 单选
                    scope.radioClk = function (item, e, t) {

                        var checked = e.currentTarget.checked; // 当前选中状态
                        scope.ngModel = {
                            key: item[scope.keys.key],
                            value: item[scope.keys.value]
                        };

                        var currentIsPush = true;
                        var currentIndex = -1;
                        for (i = 0; i < scope.currentList.length; i++) {
                            if (item[scope.keys.key] === scope.currentList[i][scope.keys.key]) {
                                currentIsPush = false;
                                currentIndex = i;
                                break;
                            }
                        }

                        if (t !== false) {
                            if (currentIsPush) {
                                //scope.currentList.push(angular.copy(item)); // 注释，可增加到当前选择的数组中，供候选
                                scope.currentList = [angular.copy(item)]; // 永远显示当前挑选的值
                            }
                        }

                        scope.currentRadio.radioValue = item[scope.keys.key]; // 设置radio　选中的值

                        scope.$parent[lovlist].selectRow = item; // 当前选择行

                        scope.setReturn();
                        //console.log(scope.ngModel)
                    };

                    // 取消选择
                    scope.cancel = function () {
                        scope.currentRadio.radioValue = '';
                        if (scope.checkedType === 'radio') {
                            scope.ngModel = {
                                key: '',
                                value: ''
                            };
                        } else {
                            scope.ngModel = {
                                key: [],
                                value: []
                            };
                        }


                        if (scope.lovDataList) {
                            scope.lovDataList.map(function (item) {
                                item.checked = false;
                            });
                        }


                        scope.currentList = [];
                        scope.setReturn();

                    };

                  scope.btnCancel = function () {
                    scope.cancel();
                    if (attrs.cancelCallback && typeof  scope.$parent[attrs.cancelCallback] === 'function') {
                      $timeout(function () {

                        if (!scope.ngModel) {
                          scope.$parent[attrs.cancelCallback](null, null, angular.copy(scope.currentList));
                        } else {
                          scope.$parent[attrs.cancelCallback](scope.ngModel.key, scope.ngModel.value, angular.copy(scope.currentList));
                        }

                      })
                    }
                  }

                    //用户手动清除选择
                    scope.$parent[lovlist][scope.clear] = function () {
                        scope.cancel();
                        scope.LovSearch(1);
                    };

                    scope.$parent[lovlist].cancel = function () {
                        scope.cancel();
                    };
                    //初始化 lov
                    scope.$parent[lovlist].reset = function () {
                        scope.keyword = {};
                        _params = {};
                        scope.cancel();
                        scope.LovSearch(1);
                    };
                    scope.paging = {
                        pageIndex: 1,
                        pageRows: 5
                    };// 分页配置
                    if (attrs.pagination) {
                        scope.paging = scope.$eval(attrs.pagination);
                    }
                    scope.pageNumber = 1;// 当前分页
                    // 搜索键
                    scope.searchKey = attrs.searchKey ? scope.$eval(attrs.searchKey) : null;

                    if (scope.searchKey) {
                        if (IsArray(scope.searchKey)) {
                            scope.searchKeyList = scope.searchKey;
                        } else {
                            scope.searchKeyList = [scope.searchKey];
                        }
                    }

                    //scope.searchKeyArray = IsArray(scope.searchKey);// 为了兼容原来的单个对象写法

                    var _tagetInput = attrs.returnValue || attrs.returnKey;
                    //alert(_tagetInput)
                    $(element).on('shown.bs.modal', function () {
                        // 执行一些动作...
                        $(this).find('#keyword').focus();// 当前LOV获取焦点
                        $('input[ng-model="' + _tagetInput + '"]').blur(); // 对应的input失去焦点

                    });
                    scope.keyword = {};// 搜索关键词

                    scope.searchBtn = function () {
                        if (attrs.params) {
                            _params = scope.$eval(attrs.params);
                        }
                        //_params[scope.searchKey.key] = scope.keyword;
                        for (var obj in scope.keyword) {
                            _params[obj] = scope.keyword[obj];
                        }
                        scope.LovSearch();
                    };
                    scope.$parent[lovlist].search = scope.searchBtn;
                    scope.recordingList = attrs.recordingList === 'false' ? false : true;// 是否记录当前已选中的条目，当返回列表时　，条目是选中状态
                    scope.LovSearch = function (index) {
                        var p = {
                            params: JSON.stringify(_params)
                        };
                        if (scope.paging) {
                            scope.paging.pageIndex = index || 1;
                            p.pageIndex = scope.paging.pageIndex;
                            p.pageRows = scope.paging.pageRows;
                        }
                        ajaxLoading = scope.paging.total > 0;

                        if (!ajaxLoading) {
                            scope.loading.error = false;
                            scope.loading.show = true;
                            scope.loading.msg = '数据读取中....';
                        }
                        httpServer.post(URLAPI[_url], p, function (res) {

                            if (!res.data) return;

                            //还原当前已选中的条目
                            if (scope.recordingList && res.count > 0) {
                                for (var i = 0; i < scope.currentList.length; i++) {
                                    for (var n = 0; n < res.data.length; n++) {
                                        if (scope.currentList[i][scope.keys.key] === res.data[n][scope.keys.key]) {
                                            res.data[n] = scope.currentList[i];
                                        }
                                    }
                                }
                            }

                            scope.lovDataList = res.data;
                            /* if (attrs.pagination) {
                             scope.paging.total = res.count;
                             }*/
                            scope.paging.total = res.count;
                            scope.pageNumber = angular.copy(p.pageIndex);

                            scope.loading.show = false;
                        }, function (res) {
                            scope.loading.error = true;
                            scope.loading.msg = '数据读取失败!'
                        }, ajaxLoading)
                    };
                    scope.autoRequest = !attrs.autoRequest ? true : attrs.autoRequest === 'true'; // 首次加载是 否去获取数据？


                    // 动态参数需要待参数加载完毕后方去拉取数据
                    if (attrs.dynamicParams) {
                        scope.w2 = scope.$watch('dynamicParams', function (nval, oval) {
                            if (nval === oval && !nval) return;
                            _params = angular.copy(scope.dynamicParams); //copy 避免在LOV里搜索时 _params变动产生多次请求
                            for (var obj in nval) {
                                scope.keyword[obj] = nval[obj];  // 反向查询当前参数是否已存在于搜索条件 并更新搜索条件值 2018-7-27

                            }
                            if (scope.autoRequest) {
                                $timeout(function () {
                                    //scope.cancel();
                                    scope.LovSearch(1);// 初始化数据
                                }, 300);
                            }
                        }, true);
                        scope.$on("$destroy", function () {
                            scope.w2();
                        })
                    } else {


                        if (scope.autoRequest) {
                            $timeout(function () {
                                scope.LovSearch(1);// 初始化数据
                            }, 300);
                        }

                    }

                    // 确定选择按钮
                    scope.callback = function () {
                        $timeout(function () {
                            scope.currentRadio.radioValue = '';

                            scope.lovDataList.map(function (item) {
                                item.checked = false;
                            });

                            scope.currentList = [];

                        }, 500);
                        /// 回调
                        if (attrs.callback && typeof scope.$parent[attrs.callback] === 'function') {
                            $timeout(function () {

                                if (!scope.ngModel) return;
                                /*     var _val, _key;      //  2018年1月22日11时19分35秒  修改为ngMoel返回值
                                 if (typeof scope.ngModel.value !== 'object') {
                                 _val = scope.ngModel.value;
                                 _key = scope.ngModel.key;
                                 } else {
                                 _val = scope.ngModel.value.join(',');
                                 _key = scope.ngModel.key.join(',');
                                 }*/
                                scope.$parent[attrs.callback](scope.ngModel.key, scope.ngModel.value, scope.currentList);
                            })
                        }

                    }


                }
            }
        })

        /* 经销商选择控件
         *  data-label="经销商"  // 显示标题
         *
         *   返回 ng-model="params.standardCinemaName"  // 返回的参数 当前选择的行
         *  返回 data-return-value="idReturnValue"  // 返回的文本值　显示到对应的input 上与当前input数据双向绑定
         &  返回 data-return-key="key"   // 返回的key值　
         * */
        .directive('lovDealer', function ($timeout) {
            return {
                require: '^ngModel',
                restrict: 'EA',

                scope: {
                    ngModel: '=', // 指令与父级的双向绑定
                    returnValue: '=?',  // 返回的value值
                    returnKey: '=?', // 返回的key值
                    dynamicParams: '=',  // 动态查询参数
                    subTitle: '=' // 字标题

                },
                templateUrl: function (element, attrs) {
                    return 'js/directives/html/lov-dealer-tpl.html';

                },
                link: function (scope, element, attrs, controller) {
                    scope.id = attrs.lovDealer || attrs.id || 'lovDealer_' + Date.parse(new Date());
                    scope.label = attrs.label || '经销商';
                    scope.disabled = attrs.disabled === 'false' ? false : true;
                    scope.readonly = attrs.readonly === 'true' ? true : false;
                    scope.required = attrs.ngRequired === 'true' ? true : false;


                }
            }
        })

        /* 经销商选择控件
         *  data-label="经销商"  // 显示标题
         *
         *   返回 ng-model="params.standardCinemaName"  // 返回的参数 当前选择的行
         *  返回 data-return-value="idReturnValue"  // 返回的文本值　显示到对应的input 上与当前input数据双向绑定
         &  返回 data-return-key="key"   // 返回的key值　
         * */
        .directive('lovSubDealer', function () {
            return {
                require: '^ngModel',
                restrict: 'EA',

                scope: {
                    ngModel: '=', // 指令与父级的双向绑定
                    returnValue: '=?',  // 返回的value值
                    returnKey: '=?', // 返回的key值
                    dynamicParams: '=?',  // 动态查询参数
                    subTitle: '=' // 字标题

                },
                templateUrl: function (element, attrs) {
                    return 'js/directives/html/lov-sub-dealer-tpl.html';

                },
                link: function (scope, element, attrs, controller) {

                    scope.id = attrs.lovSubDealer || attrs.id || 'lovSubDealer_' + Date.parse(new Date());
                    scope.label = attrs.label || '经销商子库';
                    scope.disabled = attrs.disabled === 'false' ? false : true;
                    scope.readonly = attrs.readonly === 'true' ? true : false;
                    scope.required = attrs.ngRequired === 'true' ? true : false;


                }
            }
        })
        /*
         *  required="true";  // 是否必选
         *  readonly="true" ;  // 　只读
         *  data-callback='function' // 回调    function(key)  返回当前选中的值
         *  data-first-text='全部选择' // 默认第一项
         *  id
         *  @返回
         *   ngModel  当前选项值
         *   scope.$parent[lookupCode] ={
         *         dataList:data // 返回当前LOV的数据列表
         *   }
         * */
        .directive('lookupCode', function ($rootScope, $timeout, lookupCode) {
            return {
                restrict: 'EA',
                replace: true,
                scope: {
                    ngModel: '=',// 指令与父级的双向绑定
                    returnText: '=', // 返回选定的文本
                    lookDisabled: '='
                },
                templateUrl: 'js/directives/html/lookup-code-tpl.html',
                link: function (scope, element, attrs, controller) {

                    if (!attrs.lookupCode) {
                        alert('请配置 lookup-code=的值');
                        return;
                    }
                    var _id = attrs.id || attrs.lookupCode;
                    //scope.lookDisabled=attrs.lookDisabled || false;
                    //console.log('lookDisabled' + scope.lookDisabled)
                    //if(scope.lookDisabled){
                    //    element.removeAttr('disabled');
                    //}
                    scope.isDisabled = scope.lookDisabled || false;

                    scope.firstText = attrs.firstText || '请选择';
                  // console.log(_id);

                  if (typeof scope.$parent[_id] === 'undefined') {
                        scope.$parent[_id] = {};
                    }

                  scope.lookupcode = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;

                  if (!scope.lookupcode) {
                        lookupCode(function (res) {
                            scope.lookupcode = $rootScope.lookupCode = res.data;
                            setLookupCode();
                        });

                    } else {
                        setLookupCode();
                    }

                    function setLookupCode() {
                        scope.lookUpData = [];
                        for (var n = 0; n < scope.lookupcode.length; n++) {
                            var item = scope.lookupcode[n];
                            if (item.lookupType === attrs.lookupCode) {
                                // 为了兼容之前数据没有添加 systemCode
                              if (undefined !== attrs.systemCode && attrs.systemCode === item.systemCode) {
                                scope.lookUpData.push(item);
                              } else {
                                scope.lookUpData.push(item);
                              }
                              // if (item.systemCode && item.systemCode.toLowerCase() === window.appName.toLowerCase()) {
                              //       scope.lookUpData.push(item);
                              //   } else if (!item.systemCode) {
                              //       scope.lookUpData.push(item);
                              //   }
                            }
                        }
                      scope.$parent[_id].dataList = scope.lookUpData;
                    }


                    //
                    scope.change = function () {
                        if (attrs.callback) {
                            $timeout(function () {
                                scope.$parent[attrs.callback](scope.ngModel);
                            })
                        }
                        if (attrs.paramcallback) {
                            $timeout(function () {
                                scope.$parent[attrs.paramcallback](attrs.row,scope.ngModel,scope.lookUpData);
                            })
                        }
                        if (attrs.returnText) {
                            scope.returnText = $(element).find("option:selected").text();
                        }
                    };

                    // 如果是系统编码,及超级管理员
                    if (attrs.lookupCode === 'SYSTEM_CODE') {

                        $timeout(function () {
                            if ($rootScope.currentResp.isAdmin) {
                                element.removeAttr('disabled');
                            } else {
                                element.attr('disabled', 'disabled');
                                scope.ngModel = appName;
                            }
                        })
                    }
                }
            }
        })

        //lookUpCode 关联描述
        .directive('lookupCodeDesc', function ($rootScope, $timeout, lookupCode) {
            return {
                restrict: 'EA',
                replace: true,
                scope: {
                    ngModel: '=',// 指令与父级的双向绑定
                    returnText: '=', // 返回选定的文本
                    lookDisabled: '='
                },
                templateUrl: 'js/directives/html/lookup-code-desc-tpl.html',
                link: function (scope, element, attrs, controller) {

                    if (!attrs.lookupCodeDesc) {
                        alert('请配置 lookup-code=的值');
                        return;
                    }
                    var _id = attrs.id || attrs.lookupCodeDesc;
                    //scope.lookDisabled=attrs.lookDisabled || false;
                    //console.log('lookDisabled' + scope.lookDisabled)
                    //if(scope.lookDisabled){
                    //    element.removeAttr('disabled');
                    //}
                    scope.isDisabled = scope.lookDisabled || false;

                    scope.firstText = attrs.firstText || '请选择';


                    if (typeof scope.$parent[_id] === 'undefined') {
                        scope.$parent[_id] = {};
                    }


                    scope.lookupCodeDesc = window.parent.saafrootScope.lookupCodeDesc || $rootScope.lookupCodeDesc;


                    if (!scope.lookupCodeDesc) {
                        lookupCode(function (res) {
                            scope.lookupCodeDesc = $rootScope.lookupCodeDesc = res.data;
                            setLookupCode();
                        });

                    } else {
                        setLookupCode();
                    }

                    function setLookupCode() {
                        scope.lookUpData = [];
                        for (var n = 0; n < scope.lookupCodeDesc.length; n++) {
                            var item = scope.lookupCodeDesc[n];
                            if (item.lookupType === attrs.lookupCodeDesc) {
                                // 为了兼容之前数据没有添加 systemCode
                                if (undefined !== attrs.systemCode && attrs.systemCode === item.systemCode) {
                                    scope.lookUpData.push(item);
                                } else {
                                    scope.lookUpData.push(item);
                                }
                              /*  if (item.systemCode && item.systemCode.toLowerCase() === window.appName.toLowerCase()) {
                                    scope.lookUpData.push(item);
                                } else if (!item.systemCode) {
                                    scope.lookUpData.push(item);
                                }*/
                            }
                        }
                        scope.$parent[_id].dataList = scope.lookUpData;
                    }


                    //
                    scope.change = function () {
                        if (attrs.callback) {
                            $timeout(function () {
                                scope.$parent[attrs.callback](scope.ngModel);
                            })
                        }
                        if (attrs.returnText) {
                            scope.returnText = $(element).find("option:selected").text();
                        }
                    };

                    // 如果是系统编码,及超级管理员
                    if (attrs.lookupCodeDesc === 'SYSTEM_CODE') {

                        $timeout(function () {
                            if ($rootScope.currentResp.isAdmin) {
                                element.removeAttr('disabled');
                            } else {
                                element.attr('disabled', 'disabled');
                                scope.ngModel = appName;
                            }
                        })
                    }
                }
            }
        });



    /**
     * select2封装
     * @param {scope} ng-model 选中的ID
     * @param {scope} select2-model 选中的详细内容
     * @param {scope} config 自定义配置
     * @param {String} [query] 内置的配置 (怎么也还得默认一个config)
     * @example
     * <input select2 ng-model="a" select2-model="b" config="config" type="text" placeholder="占位符" />
     * <input select2 ng-model="a" select2-model="b" config="default" query="member" type="text" placeholder="占位符" />
     * <select select2 ng-model="b" class="form-control"></select>
     */
    module.directive('select2', function (select2Query, lookupCode) {
        return {
            restrict: 'A',
            scope: {
                config: '=',
                ngModel: '=',
                select2Model: '='
            },
            link: function (scope, element, attrs) {
                // 初始化
                var tagName = element[0].tagName,
                    config = {
                        allowClear: true,
                        multiple: !!attrs.multiple,
                        placeholder: attrs.placeholder || ' '   // 修复不出现删除按钮的情况
                    };

                // 生成select
                if (tagName === 'SELECT') {
                    // 初始化
                    var $element = $(element);
                    delete config.multiple;

                    angular.extend(config, scope.config);
                    $element
                        //     .prepend('<option value=""></option>') update on 2018-03-07
                        //     .val('')
                        .select2(config);

                    // model - view
                    scope.$watch('ngModel', function (newVal) {
                        setTimeout(function () {
                            $element.find('[value^="?"]').remove();    // 清除错误的数据
                            $element.select2('val', newVal);
                        }, 0);
                    }, true);
                    return false;
                }

                // 处理input
                if (tagName === 'INPUT') {
                    // 初始化
                    var $element = $(element);

                    // 获取内置配置
                    if (attrs.query) {
                        scope.config = select2Query[attrs.query]();
                    }

                    // 动态生成select2
                    scope.$watch('config', function () {
                        angular.extend(config, scope.config);
                        $element.select2('destroy').select2(config);
                    }, true);

                    // view - model
                    $element.on('change', function () {
                        scope.$apply(function () {
                            scope.select2Model = $element.select2('data');
                        });
                    });

                    // model - view
                    scope.$watch('select2Model', function (newVal) {
                        $element.select2('data', newVal);
                    }, true);

                    // model - view
                    scope.$watch('ngModel', function (newVal) {
                        // 跳过ajax方式以及多选情况
                        if (config.ajax || config.multiple) {
                            return false
                        }

                        $element.select2('val', newVal);
                    }, true);
                }
            }
        }
    });

    module.factory('select2Query', function ($timeout) {
        return {
            testAJAX: function () {
                var config = {
                    minimumInputLength: 1,
                    ajax: {
                        url: "http://api.rottentomatoes.com/api/public/v1.0/movies.json",
                        dataType: 'jsonp',
                        data: function (term) {
                            return {
                                q: term,
                                page_limit: 10,
                                apikey: "ju6z9mjyajq2djue3gbvv26t"
                            };
                        },
                        results: function (data, page) {
                            return { results: data.movies };
                        }
                    },
                    formatResult: function (data) {
                        return data.title;
                    },
                    formatSelection: function (data) {
                        return data.title;
                    }
                };

                return config;
            }
        }
    })

        // 自动完成输入框
        /* data-url="menuList"  接口地址
         *  data-keys="{'key':'lookupCode','value':'meaning'}"  // 绑定的 key 与显示的值 ,其中 value 是模糊查询的条件
         *  data-return-key="params.orderId"   // 把 data-keys.key 返回到 orderId 这个值里  ,
         *
         *  默认返回结果为 data-keys.value 到 ngModel
         *
         * *
         * */
        .directive('autoComplete', function (httpServer, URLAPI, $rootScope, $timeout, $filter, $compile, arrayUnique, debounce, SIEJS, SortBy) {
            return {
                restrict: 'EA',
                require: "?ngModel",
                scope: {
                    ngModel: '=', // 指令与父级的双向绑定
                    returnKey: '='  // 返回的key值
                },
                link: function (scope, element, attrs, ngModel) {
                    var id = attrs.id || attrs.ngModel || attrs.name || Date.parse(new Date());
                    id = id.replace('.', '_');
                    if (!attrs.name) {
                        element.attr('name', 'autoComplete_name_' + id);
                        attrs.name = 'autoComplete_name_' + id;
                    }
                    var url = attrs.url;
                    if (!url) {
                        SIEJS.alert('请设置auto complete指令的api', 'warning');
                        return;
                    }
                    if (!attrs.ngModel) {
                        SIEJS.alert('请设置auto complete指令的ngModel值', 'warning');
                        return;
                    }
                    scope.keys = attrs.keys ? scope.$eval(attrs.keys) : null;
                    if (!attrs.keys) {
                        SIEJS.alert('请设置auto complete指令的data-keys值', 'warning');
                        return;
                    }
                    scope.w = scope.$watch(function () {
                        return scope.ngModel;
                    }, debounce(function (nval, oval) {
                        if (!nval) {
                            ngModel.error = '';
                            ngModel.$setValidity('autoComplete', true); // 更新ngModel 验证信息状态
                        } else {
                            log(' 查询');
                            scope.getData()
                        }
                    }, 500));

                    scope.$on("$destroy", function () {
                        scope.w(); //　销毁监听
                    });


                    var blurrySearch = false;
                    // 获取数据
                    scope.getData = function (blurry) {
                        blurry = blurry || blurrySearch;
                        log(blurry);
                        var params = attrs.params ? scope.$eval(attrs.params) : {};
                        if (blurry) {
                            params[scope.keys.value + '_like'] = angular.copy(scope.ngModel);
                        } else {
                            params[scope.keys.value] = angular.copy(scope.ngModel);
                        }
                        httpServer.post(URLAPI[url], {
                            params: JSON.stringify(params),
                            pageIndex: 1,
                            pageRows: 200
                        }, function (res) {


                            // 移除遮罩
                            $("#saafAotuCompleteMask").remove();
                            if (res.count === 0 || res.data.length === 0) {
                                $("#autocomplete_" + id).hide();
                                if (!blurry) {
                                    //SIEJS.alert('当前值不合法，将自动清除', 'warning');
                                    //scope.ngModel = '';
                                    $("#autocomplete_" + id).hide();
                                    var tip = element.prev().text();
                                    tip = tip.replace(/[:：\*\s]/gim, '');
                                    ngModel.error = tip + '参数不合法';
                                    ngModel.$setValidity('autoComplete', false); // 更新ngModel 验证信息状态
                                }
                                return;
                            } else {
                                if (!blurry) {
                                    $("#autocomplete_" + id).hide();
                                    ngModel.$setValidity('autoComplete', true); // 更新ngModel 验证信息状态
                                } else {
                                    $("#autocomplete_" + id).show();
                                }

                            }
                            // 去重条件为 value 即输入的值
                            scope.list = arrayUnique(res.data, scope.keys.value);
                            scope.list.sort(SortBy(scope.keys.value, 'asc'));

                            var pos = element[0].getBoundingClientRect();
                            scope.style = {
                                position: 'absolute',
                                top: pos.top + element[0].offsetHeight,
                                left: pos.left,
                                'z-index': 1080, // modal 默认是 1050
                                'min-width': element[0].offsetWidth,
                                'max-heigth': 250
                            };


                            /*   ngModel.$formatters.push(function(){

                             return ngModel.$modelValue;
                             });
                             ngModel.$parsers.push(function(){
                             return ngModel.$modelValue;
                             });*/


                        })
                    };
                    element.on('keyup', function () {
                        blurrySearch = true;
                    });
                    element.on('blur', debounce(function () {
                        blurrySearch = false;

                        // 添加遮罩，先验证当前输入的有效性再进行一步操作
                        var autoCompleteMask = $("#saafAotuCompleteMask");
                        var _css = {
                            'z-index': 1060,
                            position: 'fixed',
                            left: 0,
                            right: 0,
                            top: 0,
                            bottom: 0,

                            opacity: 0.2
                        };
                        if (autoCompleteMask.length === 0) {
                            var mask = $('<div id="saafAotuCompleteMask" style=""></div>').css(_css);
                            mask.prependTo(angular.element('body'));
                        } else {
                            autoCompleteMask.css(_css);
                        }
                        scope.getData();
                    }, 150));


                    element.on('focus', debounce(function () {
                        //if (scope.list)  $("#autocomplete_" + id).show();
                        if (!scope.ngModel) return;

                        scope.getData(true);
                    }, 100));

                    scope.setChecked = function (item) {


                        scope.ngModel = item[scope.keys.value];
                        if (attrs.returnKey) {
                            if (attrs.options) { // lovAutoComplete 组件的配置项
                                var ops = scope.$eval(attrs.options);
                                if (ops.returnKey) {
                                    scope.returnKey = item[scope.keys.key];
                                }
                            } else {
                                scope.returnKey = item[scope.keys.key];
                            }

                        }
                        $("#autocomplete_" + id).hide();
                    };


                    function createHtml() {
                        var ullist = '<div id="autocomplete_' + id + '" ng-style="style" class="autocompleteList" ><ul style="padding:0;">' +
                            '<li ng-repeat="item in list" ng-click="setChecked(item)">' +
                            '<a href="javascript:" >{{ item[keys.value] }}</a> ' +
                            '</li></ul></div>';
                        $('body').append($compile(ullist)(scope));
                    }

                    createHtml();
                    /*
                     $("#autocomplete_" + id).hover(function(){
                     $("#autocomplete_" + id).show();
                     },function(){
                     $("#autocomplete_" + id).hide();
                     })*/


                }
            }
        })

        /* 搜索列表 form组
         * data-legend="[ {key:'xx',label:'下拉列表',type:'lookCode',lookCode:'','disabled':true， readonly:true }
         * {key:'startDate',label:'开始日期',type:'time','date':'yyyy-mm-dd hh:ii:ss' ,endDate:'endDate',isToday:true}         *
         * {key:'endDate',label:'结束日期',type:'time','date':'yyyy-mm-dd hh:ii:ss',startDate:'startDate',required:true,validFormat:'email'}
         *
         * {key:'xx',label:'选择弹窗',type:'lov',target:'#id','disabled':true， readonly:true}
         * ]"
         *   target:'#id'
         *   key,label 必填
         *   type: text,lookCode , time (单个时间) ,lov , lovAutoComplete ,lovDealer(经销商组件) ，lovSubDealer(经销商子库组件) 默认text
         *   endDate: 结束日期，开始的日期会被结束日期限制，超出范围不可选
         *   startDate: 开始日期，结束的日期会被开始日期限制，超出范围不可选
         *   isToday:true  是否显示当天日期
         *    required:必填
         *    requiredMsg: 必须错误提示信息
         *    errorMsg:  格式错误提示信息
         *    validFormat: 数据校验格式    格式请参数 ：自定义表单验证
         *    maxlength: 最大字符
         *   ng-model="params"  必填
         *   data-col='3'  每行分多少列  12/3
         *
         *   日期格式：
         *      yyyy-MM-dd   显示年月日
         yyyy-MM-dd HH:ii:ss   显示年月日到时分钞

         data-required-oneof="code,boxCode,trayCode"  /// 至少要必填一项
         data-disabled="disabled" // 整个表单被禁用

         *
         * */
        .directive('searchForm', function (httpServer, URLAPI, $rootScope, $timeout, $filter, $compile, SIEJS) {
            return {
                restrict: 'EA',
                scope: {
                    ngModel: '=',// 指令与父级的双向绑定
                    ngParams: '='// 指令与父级的双向绑定
                },
                templateUrl: 'js/directives/html/search-form-tpl.html',
                link: function (scope, element, attrs, controller) {
                    if (scope.ngModel == undefined) {
                        /* swal('', 'ngModel参数未定义', 'warning');
                         return*/
                        scope.ngModel = scope.$parent[attrs.ngModel] = {};
                    }
                    scope.ngModelName = attrs.ngModel;
                    scope.formList = eval(attrs.legend);
                    scope.col = attrs.col || 3;
                    scope.target = attrs.target;
                    scope.times = eval(attrs.times);
                    // 日期格式
                    scope.dateFormat = function (format) {
                        return format || 'yyyy-mm-dd';
                    };
                    // 日期视图
                    scope.minView = function (format) {
                        format = format || 'yyyy-mm-dd';
                        format = format.toLowerCase();
                        var i = 2;
                        switch (format) {
                            case 'yyyy-mm-dd hh:ii:ss':
                            case 'yyyy-mm-dd hh:ii':
                                i = 0;
                                break;
                            case 'yyyy-mm-dd':
                                i = 2;
                                break;
                            case 'yyyy-mm':
                                i = 3;
                                break;
                            case 'yyyy':
                                i = 4;
                                break;
                            case 'hh:ii:ss':
                                i = 1;
                                break;
                        }
                        return i;
                    };

                    scope.startView = function (format) {
                        format = format || 'yyyy-mm-dd';
                        format = format.toLowerCase();
                        var i = 2;
                        switch (format) {
                            case 'yyyy-mm-dd hh:ii:ss':
                            case 'yyyy-mm-dd':
                                i = 2;
                                break;
                            case 'yyyy-mm':
                                i = 3;
                                break;
                            case 'yyyy':
                                i = 4;
                                break;
                            case 'hh:ii:ss':
                                i = 0;
                                break;
                        }
                        return i;
                    };

                    /*    if (processing  && scope.ngModel){

                     for (var y in scope.ngModel){
                     log(y);
                     }
                     }*/
                    // scope.currentParam = {};
                    // scope.ngModel = scope.currentParam
                    var _gird = attrs.searchForm;
                    scope.$parent[_gird] = {};
                    scope.$parent[_gird].restore = function () {
                        scope.ngModel = {};
                    };

                    scope.requiredOneof = attrs.requiredOneof ? attrs.requiredOneof.split(',') : null;// 至少必填一项


                    // 是否必填
                    scope.isRequired = function (item) {

                        if (!item) {
                            return false;
                        }
                        if (typeof item === 'string') {
                            item = scope.$eval(item);
                        }
                        if (item.required) {
                            return item.required;
                        } else if (scope.requiredOneof) {
                            return scope.requiredOneof.indexOf(item.key) > -1;
                        } else {
                            return false;
                        }
                    };


                    if (attrs.requiredOneof) {
                        scope.w = scope.$watch('ngModel', function (nval, oval) {

                            var hasList = attrs.requiredOneof.split(',');
                            var oneof = attrs.requiredOneof;
                            var isOneOf = false;
                            for (var n in nval) {
                                if (hasList.indexOf(n) > -1 && nval[n]) {
                                    var reg = new RegExp(n + ',?');
                                    oneof = oneof.replace(reg, '').replace(/,$/, '');
                                    isOneOf = true;
                                }
                            }

                            if (isOneOf) {
                                scope.requiredOneof = [];
                            } else {
                                scope.requiredOneof = attrs.requiredOneof ? attrs.requiredOneof.split(',') : null;// 至少必填一项
                            }
                            // log(scope.requiredOneof);
                        }, true);
                        /*  $timeout(function(){
                         scope.requiredOneof.splice(0,1);
                         },2000)*/
                        scope.$on("$destroy", function () {
                            scope.w(); //　销毁监听
                        });
                    }

                    scope.createDom = function () {
                        scope.formList.map(function (item, index) {

                            if (item.date) { // 数据设置为日期，需要处理数据
                                if (scope.ngModel) {

                                    scope.ngModel[item.key] = $filter('date')(scope.ngModel[item.key], item.date)

                                    //$filter('formatDate')(result, td.formatDate);
                                }
                            }

                            var node = '<div class="form-group col-xs-6 col-md-' + (item.col ? item.col : scope.col) + '" >';
                            switch (item.type) {
                                case 'ztreeSelect':
                                    node = node + '<div z-tree-select="' + scope.ngModelName + '_' + item.key + '"' +
                                        '       id="' + scope.ngModelName + '_' + item.key + '"' +
                                        '       name="' + scope.ngModelName + '_' + item.key + '" ' +
                                        '         ng-value="ngModel[\'' + item.options.returnValue + '\']"' +
                                        '         ng-key="ngModel[\'' + item.options.returnKey + '\']"' +
                                        '         data-url="' + item.options.url + '"' +
                                        '         data-label="' + item.options.label + '"' +
                                        '         data-input-readonly="true"' +
                                        '         data-simpledata=\'' + (item.options.simpledata ? JSON.stringify(item.options.simpledata) : "") + '\'' +
                                        '         data-keys=\'' + (item.options.keys ? JSON.stringify(item.options.keys) : "") + '\'' +
                                        '         data-height="' + item.options.height + '"' +
                                        '    ></div>';
                                    // log(node)
                                    break;

                                case 'lovDealer': // 经销商组件
                                    if (!item.options) {
                                        SIEJS.alert('请为经销商组件配置options');
                                        return;
                                    }
                                    node = node + '<div lov-dealer="lovDealer_' + (item.key || item.options.returnKey) + '" ' +
                                        '         data-label="' + (item.label || item.options.label) + '"' +
                                        '         ng-required="' + (item.options.required || false) + '"' +
                                        '         data-disabled="' + (item.options.disabled || false) + '"' +
                                        '         data-readonly="' + (item.options.disabled || false) + '"';

                                    if (item.options.returnKey) {
                                        node = node + ' data-return-key="ngModel[\'' + item.options.returnKey + '\']"';
                                    }
                                    if (item.options.returnValue) {
                                        node = node + ' data-return-value="ngModel[\'' + item.options.returnValue + '\']"';
                                    }
                                    if (item.options.ngModel) {
                                        node = node + ' ng-model="' + item.options.ngModel + '"';
                                    }
                                    node = node + '></div>';

                                    break;
                                case 'lovSubDealer': // 经销商子库组件
                                    if (!item.options) {
                                        SIEJS.alert('请为经销商子库组件配置options');
                                        return;
                                    }
                                    node = node + ' <div lov-sub-dealer ' +
                                        '         data-label="' + (item.label || item.options.label) + '"' +
                                        '         ng-required="' + (item.options.required || false) + '"' +
                                        '         data-disabled="' + (item.options.disabled || false) + '"' +
                                        '         data-readonly="' + (item.options.disabled || false) + '"';

                                    if (item.options.returnKey) {
                                        node = node + ' data-return-key="ngModel[\'' + item.options.returnKey + '\']"';
                                    }
                                    if (item.options.returnValue) {
                                        node = node + ' data-return-value="ngModel[\'' + item.options.returnValue + '\']"';
                                    }
                                    if (item.options.ngModel) {
                                        node = node + ' ng-model="' + item.options.ngModel + '"';
                                    }
                                    node = node + '></div>';
                                    break;
                                case 'lov':
                                    node = node + '<div class="input-group input-group-xs " >' +
                                        '       <label class="input-group-addon bn">' +
                                        '            <span class="w100"><i class="red" ng-if=isRequired(' + JSON.stringify(item) + ')>* </i>' + item.label + '</span>' +
                                        '        </label>' +
                                        '<input  type="text" class="form-control radius3"' +
                                        '       maxlength="' + item.maxlength + '"' +
                                        '       ng-model="ngModel[\'' + item.key + '\']"' +
                                        '       ng-required=isRequired(' + JSON.stringify(item) + ')' +
                                        '       data-required-msg="' + (item.requiredMsg || '') + '" ' +
                                        '       data-error-msg="' + (item.errorMsg || '') + '"' +
                                        '       id="' + scope.ngModelName + '_' + item.key + '"' +
                                        '       name="' + scope.ngModelName + '_' + item.key + '" ' +
                                        '       ng-disabled="' + (item.disabled || false) + '" ' +
                                        '       ng-readonly="' + (item.readonly || false) + '"';

                                    node = node + '>' +
                                        '<span class="input-group-btn"   data-toggle="modal"' +
                                        '              data-target="' + item.target + '">' +
                                        '           <a class="btn btn-default"><i class="fa fa-search"></i></a>' +
                                        '        </span>' +
                                        '</div>';
                                    break;
                                case 'autoComplete':
                                case 'lovAutoComplete':
                                    node = node + '<div class="input-group input-group-xs ">' +
                                        '       <label class="input-group-addon bn">' +
                                        '            <span class="w100"><i class="red" ng-if=isRequired(' + JSON.stringify(item) + ')>* </i>' + item.label + '</span>' +
                                        '        </label>' +
                                        ' <input  type="text"' +
                                        '               class="form-control radius3"' +
                                        '       maxlength="' + item.maxlength + '" auto-complete=""' +
                                        '               data-url="' + item.options.url + '"' +
                                        '               data-keys=' + (item.options.keys ? JSON.stringify(item.options.keys) : "") +
                                        '               data-options="' + item.options + '"' +
                                        '               data-return-key="ngModel[\'' + item.options.returnKey + '\']"' +
                                        '               data-params="' + item.options.params + '"' +
                                        '       ng-required=isRequired(' + JSON.stringify(item) + ')' +
                                        '       data-required-msg="' + (item.requiredMsg || '') + '" ' +
                                        '       data-error-msg="' + (item.errorMsg || '') + '"' +
                                        '       id="' + scope.ngModelName + '_' + item.key + '"' +
                                        '       name="' + scope.ngModelName + '_' + item.key + '" ' +
                                        '       ng-model="ngModel[\'' + item.key + '\']">' +
                                        '<span class="input-group-btn"   data-toggle="modal"' +
                                        '              data-target="' + item.target + '">' +
                                        '           <a class="btn btn-default"><i class="fa fa-search"></i></a>' +
                                        '        </span>' +
                                        '</div>';
                                    break;
                                case 'lookCode':
                                case 'lookUpCode':
                                case 'lookupCode':
                                    node = node + '<div class="input-group input-group-xs ">' +
                                        '       <label class="input-group-addon bn">' +
                                        '            <span class="w100"><i class="red" ng-if=isRequired(' + JSON.stringify(item) + ')>* </i>' + item.label + '</span>' +
                                        '        </label>' +
                                        '<div lookup-code="' + (item.lookCode || item.lookUpCode || item.lookupCode) + '"' +
                                        '       ng-model="ngModel[\'' + item.key + '\']"' +
                                        '       ng-required=isRequired(' + JSON.stringify(item) + ')' +
                                        '       data-required-msg="' + (item.requiredMsg || '') + '" ' +
                                        '       data-error-msg="' + (item.errorMsg || '') + '"' +
                                        '       id="' + scope.ngModelName + '_' + item.key + '"' +
                                        '       data-callback="' + (item.callback ? item.callback : '') + '"' +
                                        '       name="' + scope.ngModelName + '_' + item.key + '" ' +
                                        '       ng-disabled="' + (item.disabled || false) + '" ' +
                                        '       ng-readonly="' + (item.readonly || false) + '"' +
                                        '></div></div>';

                                    break;
                                case 'time':
                                    node = node + '<div class="input-group input-group-xs ">' +
                                        '       <label class="input-group-addon bn">' +
                                        '            <span class="w100"><i class="red" ng-if="' + scope.isRequired(item) + '">* </i>' + item.label + '</span>' +
                                        '        </label>' +
                                        '<input type="text" class="form-control radius3 input-xs" ' +
                                        '               data-stratdate="{{ ngModel[\'' + item.startDate + '\']}}"' +
                                        '               data-enddate="{{ ngModel[\'' + item.endDate + '\']}}"' +
                                        '               date-time-picker ' +
                                        '               data-is-today="' + item.isToday + '"' +
                                        '               data-show-meridian="' + item.showMeridian + '"' +
                                        '               data-minute-step="' + item.minuteStep + '"' +
                                        '               data-start-view="' + scope.startView(item.date) + '"' +
                                        '               data-date-format="' + (item.date || 'yyyy-mm-dd') + '"' +
                                        '               data-min-view="' + scope.minView(item.date) + '"' +
                                        '       ng-model="ngModel[\'' + item.key + '\']"' +
                                        '       ng-required="' + (item.required || false) + '" ' +
                                        '       data-required-msg="' + (item.requiredMsg || '') + '" ' +
                                        '       data-error-msg="' + (item.errorMsg || '') + '"' +
                                        '       id="' + scope.ngModelName + '_' + item.key + '"' +
                                        '       name="' + scope.ngModelName + '_' + item.key + '" ' +
                                        '       ng-disabled="' + (item.disabled || false) + '" ' +
                                        '       ng-readonly="' + (item.readonly || false) + '"' +
                                        '>' +

                                        '</div>';
                                    break;
                                default: // 文本格式

                                    // debugger;
                                    node = node + '<div class="input-group input-group-xs ">' +
                                        '       <label class="input-group-addon bn">' +
                                        '            <span class="w100"><i class="red" ng-if=isRequired(' + JSON.stringify(item) + ')>* </i>' + item.label + '</span>' +
                                        '        </label>' +
                                        '  <input   type="text" class="form-control radius3"' +
                                        '       maxlength="' + (item.maxlength || '') + '"' +
                                        '       ng-model="ngModel[\'' + item.key + '\']"' +
                                        '       ng-required=isRequired(' + JSON.stringify(item) + ') ' +
                                        '       data-required-msg="' + (item.requiredMsg || '') + '" ' +
                                        '       data-error-msg="' + (item.errorMsg || '') + '"' +
                                        '       id="' + scope.ngModelName + '_' + item.key + '"' +
                                        '       name="' + scope.ngModelName + '_' + item.key + '" ' +
                                        '       ng-disabled="' + (item.disabled || false) + '" ' +
                                        '       ng-readonly="' + (item.readonly || false) + '"';

                                    if (item.validFormat) {
                                        var validFormat = item.validFormat.split('=');
                                        node = node + validFormat[0] + '=' + (validFormat[1] || '');
                                    }
                                    node = node + '></div>';
                                    // log(node)
                                    break;

                            }
                            node = node + '</div>';
                            $("#searchForm-box-" + scope.ngModelName).append(node);
                        });
                        $compile($("#searchForm-box-" + scope.ngModelName))(scope);

                        $timeout(function () {
                            // debugger;
                            if (attrs.disabled) {
                                $("#searchForm-box-" + scope.ngModelName).find('input').attr('disabled', 'disabled');
                            }
                        });

                    };

                    $timeout(function () {
                        scope.createDom();


                    })


                }
            }
        })

        /* lov  form组
         *   data-legend="[
         *   {key:'xx',label:'xx',type:'lookCode',lookCode:'xxx'}
         *   {key:'xx',label:'xx'}
         *   {key:'xx',label:'xx',type:'tiem',isToday:true}
         *   ]"
         *   key,label 必填
         *   type: text,lookCode , time (单个时间 如果需要默认今天  加 isToday:true ) , 默认text
         *
         *   ng-model="params"  必填  并且在自己的控制器里定义该绑定对象
         *   ng-id="id"  必填
         *   data-title="" 必填
         *   data-callback="function" 必填  保存后的操作
         * */
        .directive('lovForm', function (httpServer, URLAPI, $rootScope, $timeout) {
            return {
                restrict: 'EA',
                scope: {
                    ngModel: '='// 指令与父级的双向绑定
                },
                templateUrl: 'js/directives/html/lov-form-tpl.html',
                link: function (scope, element, attrs, controller) {


                    if (scope.ngModel == undefined || attrs.title == undefined || attrs.id == undefined) {

                        swal('', '核对model参数', 'warning');
                        return
                    }
                    scope.formList = eval(attrs.legend);
                    scope.title = attrs.title;
                    scope.id = attrs.id;
                    scope.formList.map(function (item) {
                        if (item.key == undefined || item.label == undefined) {
                            swal('', '核对 params参数', 'warning')
                        }
                    });
                    scope.currentParam = {}; //定义当前作用域的变量
                    scope.ngModel = scope.currentParam; //
                    scope.save = function () {
                        if (attrs.callback && typeof scope.$parent[attrs.callback] === 'function') {
                            scope.$parent[attrs.callback]();

                        }
                    };

                    scope.lov = function (data) {
                        if (typeof scope.$parent[data] === 'function') {
                            scope.$parent[data]();

                        } else {
                            swal('', '不存在该方法', 'warning')
                        }
                    }
                }
            }
        }
    )

        /*tree-table
         *
         * data-url  初始化表格数据
         *
         *
         * */
        .directive('treeTable', function (httpServer, PageRows, URLAPI, $rootScope, $timeout) {
            return {
                restrict: 'EA',
                scope: {
                    ngModel: '='// 指令与父级的双向绑定
                },
                link: function (scope, ele, attrs, controller) {
                    var _grid = attrs.treeTable;
                    if (typeof scope.$parent[_grid] === 'undefined') {
                        scope.$parent[_grid] = {};
                    }


                    scope.paging = {
                        currentPage: 1, //默认显示第一页
                        total: 1,
                        pageSize: PageRows(ele), //默认条数
                        index: 1
                    };


                    //搜索功能
                    scope.$parent[_grid].search = function (index) {
                        var p = {
                            params: JSON.stringify(scope.ngParams || scope.$parent.params || {}),// 若当前不传params值，侧查到父级的params进行传参
                            pageIndex: index || scope.paging.currentPage || 1,
                            pageRows: scope.paging.pageSize
                        };

                        httpServer.post(URLAPI[attrs.url], p, function (res) {
                            res.data.map(function (item) {
                                item.isShow = false
                            });
                            scope.paging.total = res.count;
                            scope.paging.index = res.curIndex;
                            console.log(scope.paging);
                            scope.$parent[attrs.treeTable].paging = scope.paging;
                            scope.$parent[attrs.treeTable].data = res.data
                        }, function (e) {
                            scope.loading.error = true;
                            scope.loading.msg = '数据读取失败!'
                        })

                    };

                    scope.$parent[_grid].search();
                    // 分页控件查询
                    scope.paging.search = function (index) {
                        scope.selectedAll = false;// 取消全选
                        // scope.isExportData = false;
                        scope.$parent[_grid].search(index)
                    }

                }
            }
        }
    )
        // 按键松开　同名 onkeyup 事件
        .directive('keyUp', function (Debounce) {
            return {
                restrict: 'A',
                replace: true,
                scope: true,
                link: function (scope, ele, attrs) {

                    //使用 Debounce 函数节流
                    ele.on('keyup', Debounce(function () {
                        scope[attrs.keyUp](this);
                    }, 500, true))
                }
            };
        })

        //
        .directive('hkDataTableEdit', function (httpServer, URLAPI, $timeout) {
            return {
                restrict: 'A',
                scope: {
                    ngModel: "=",
                    ngParams: "=",
                    tablelist: "=",
                    pagingdata: "=",
                },
                link: function (scope, ele, attrs) {
                    // console.log(attrs)
                    var _table = attrs['hkDataTableEdit'] || attrs.id,
                        $table = scope.$parent[_table] = scope;
                    $table.isCheckedAll = 'N';
                    // console.log(scope.dataUrl)
                    scope.$parent[_table]['getSelectedRows'] = function () {
                        var arr = [];
                        if (scope.tablelist && Array.isArray(scope.tablelist))
                            for (var i = 0; i < scope.tablelist.length; i++) {
                                if (scope.tablelist[i].isChecked == 'Y') {
                                    arr.push(scope.tablelist[i])
                                }
                            }
                        return arr;
                    };

                    $table.checkAll = function (value) {
                        if (scope.tablelist && Array.isArray(scope.tablelist))
                            for (var i = 0; i < scope.tablelist.length; i++) {
                                scope.tablelist[i].isChecked = value
                            }
                    };
                    $table.search = function (page) {
                        scope.pagingdata.index = page;
                        $timeout(function(){
                            getData()
                        })
                        //getData()
                    };

                    var getData = function () {
                        httpServer.post(URLAPI[attrs.url], {
                            params: JSON.stringify(scope.ngParams),
                            pageIndex: scope.pagingdata.index,
                            pageRows: scope.pagingdata.pageSize
                        }, function (res) {
                            if (res.status == "S") {
                                scope.tablelist = res.data;
                                scope.pagingdata.total = res.count;
                                scope.pagingdata.index = res.curIndex;
                                scope.pagingdata.countStart = (res.curIndex - 1) * res.pageSize + 1;
                                listCheck()
                            }
                        }, function (res) {

                        })
                    };
                    var listCheck = function(){
                        var isCheckedAll = false;
                        if (scope.tablelist && Array.isArray(scope.tablelist))
                            for (var i = 0; i < scope.tablelist.length; i++) {
                                if (scope.tablelist[i].isChecked == 'Y') {
                                    isCheckedAll = true;
                                } else if (scope.tablelist[i].isChecked == 'N') {
                                    isCheckedAll = false;
                                } else {
                                    scope.tablelist[i].isChecked = 'N';
                                    isCheckedAll = false;
                                }
                            }
                        $table.isCheckedAll = isCheckedAll ? 'Y' : 'N'
                    };
                    scope.init = function () {
                        listCheck();
                        if (attrs.autosearch === "true" || attrs.autosearch == true) {
                            $table.search(1)
                        }
                    };
                    scope.init()
                }
            };
        })

        /*  自定义表单验证  ******************************************************************************************************
         *   验证错误时，$error 返回与指令同名的错误提示，如 $error.passwordLevel:true
         *
         *   form.account.$error.passwordLevel //验证错误标识
         *   form.account.error  //错误提示信息
         *   <span data-error-msg='要显示验证控件的NAME'>错误提示信息显示标签</span>
         *   侯兴章  2016-12-30
         * */

        /*密码强度检测
         * passwordLevel 强度级别 默认：2（至少字母与数字组合），3（字母、数字、特殊字符组合）
         * */
        .directive('passwordLevel', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    var level = !attr.passwordLevel ? 2 : attr.passwordLevel * 1;
                    if (ngModel) {
                        var reg, tip;
                        if (level === 3) {
                            reg = /^(?=.*?[0-9])((?=.*?[a-z])|(?=.*?[A-Z]))(?=.*?[@!#$%^&*()_+\.\-\?<>'"|=\\\[\]\{\}:;,\/]+).{8,30}$/i;
                            tip = '密码至少要包含8个字符的字母、数字及特殊字符';
                            element.attr({
                                'minlength': 8,
                                'maxlength': 30
                            });
                        } else {
                            reg = /^(?=.*?[0-9])((?=.*?[a-z])|(?=.*?[A-Z])).{6,30}$/i;
                            tip = '密码至少要包含6个字符的字母与数字';
                            element.attr({
                                'minlength': 6,
                                'maxlength': 30
                            });
                        }
                        customValidator('passwordLevel', ngModel, reg, tip, element);
                    }
                }
            };
        })

        /*比较是否相等（ 密码重复验证 ）
         *  equals 需要检测的值 {{ value }}
         *  <input equals={{ params.pwd }} ng-model="params.pwd2">
         * */
        .directive('equals', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '请重复输入一致的密码！';
                        customValidator('equals', ngModel, attr, tip, element);

                    }
                }
            };
        })

        /* 账户验证    *
         * */
        .directive('account', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '账户由2到16位中文、字母、数字组成！';
                        var reg = /^[A-Za-z0-9_\-\u4e00-\u9fa5]{2,16}$/;
                        element.attr({
                            'minlength': 2,
                            'maxlength': 16
                        });
                        var av = customValidator('account', ngModel, reg, tip, element);
                    }
                }
            };
        })


        /* 远程验证
         * 用于：用户名验证、手机号验证、图形验证码、邮箱等
         * 参数：remoteVerify="{url:'checkUserName',param:'varQueryUserName',action:'',error:'账户已存在啦'}"
         * url: 远程接口(URLAPI)，param:要验证的字段名,action:请求的动作类型,  error:错误时的提示
         *
         * */
        .directive('remoteVerify', function (httpServer, URLAPI, customValidator, Debounce, SweetAlert, $timeout) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (!attr.remoteVerify) {
                        SweetAlert.swal({
                            title: '远程校验参数没配置',
                            imageUrl: 'images/info.png'
                        });
                        return
                    }
                    var json = scope.$eval(attr.remoteVerify);
                    var tip = json.error || '此值已存在!';

                    //使用 Debounce 函数节流
                    element.on('keyup', Debounce(function () {
                        var s = ngModel.$modelValue;
                        if (!s) return;

                        var data = {};
                        if (json.action) {
                            data.action = json.action;
                        }
                        if (json.param) {
                            data.params = '{"' + json.param + '":"' + s + '"}';
                        }
                        httpServer.post(
                            URLAPI[json.url],
                            data,
                            function (res) {
                                var r = res.status === 'S';
                                if (r) {
                                    ngModel.error = '';//清除对应提示
                                } else {
                                    ngModel.error = tip
                                }
                                ngModel.$setValidity('remoteVerify', r);//设置ngModel状态
                            }, null, true
                        );

                    }, 500, true));

                }
            };
        })
        /* 邮箱验证    *
         * */
        .directive('email', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '请输入正确的邮箱地址';
                        var reg = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
                        customValidator('email', ngModel, reg, tip, element);
                    }
                }
            };
        })

        /*网址验证  *
         * */
        .directive('http', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '网址要包括http';
                        var reg = /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/;
                        customValidator('url', ngModel, reg, tip, element);
                    }
                }
            };
        })

        /* 中文验证  *
         * */
        .directive('chinese', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '请输入中文';
                        var reg = /^[\u4e00-\u9fa5]+$/;
                        customValidator('chinese', ngModel, reg, tip, element);
                    }
                }
            };
        })
        /* 日期验证  *
         * 2016-12-30  或 2016-11-11 21:12:32
         *  */
        .directive('date', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '日期格式：2017-01-03';
                        var reg = /^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))(\s+(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d)?$/;
                        customValidator('date', ngModel, reg, tip, element);
                    }
                }
            };
        })

        /* 时间验证  *
         *   21:12:32
         *  */
        .directive('time', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '日期格式为 18:18:08';
                        var reg = /^(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;
                        customValidator('date', ngModel, reg, tip, element);
                    }
                }
            };
        })

        /* 身份证  *
         *
         *  */
        .directive('idCard', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '请输入18位身份证';
                        var reg = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X|x)$/;
                        customValidator('idCard', ngModel, reg, tip, element);
                        element.attr({
                            'maxlength': 18
                        });
                    }
                }
            };
        })

        /*手机验证  *     *
         *  */
        .directive('mobile', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '手机号码为11位数字';
                        var reg = /^(13|14|15|17|18)[0-9]{9}$/;
                        customValidator('mobile', ngModel, reg, tip, element);
                        element.attr({
                            'maxlength': 11
                        });
                    }
                }
            };
        })
        /*数字验证*/
        .directive('number', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '请输入整数';
                        var reg = /^\d*$/;
                        customValidator('number', ngModel, reg, tip, element);
                    }
                }
            };
        })
        /*数字小数点验证*/
        .directive('numberd', function (customValidator) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (ngModel) {
                        var tip = '请输入数字,小数点最多保留2位';
                        var reg = /^(([1-9]{1}\d*)|(0{1}))(\.\d{0,2})?$/;
                        customValidator('number', ngModel, reg, tip, element);
                    }
                }
            };
        })

        /*固定长度*/
        .directive('length', function (customValidator, SIEJS) {
            return {
                require: "?ngModel",
                link: function (scope, element, attr, ngModel) {
                    if (!attr.length) {
                        SIEJS.alert('请给length赋值', 'warning');
                        return;
                    }
                    if (ngModel) {
                        var tip = '请输入' + attr.length + '位字符';
                        //var reg = /^.{20}$/;
                        var reg = new RegExp('^.{' + attr.length + '}$');
                        customValidator('length', ngModel, reg, tip, element);
                    }
                }
            };
        })

        /*  自定义表单验证  结束  ******************************************************************************************************/
        //分隔条使用
        .directive('bgSplitter', function() {
            return {
                //使用 标签(tag)的方式
                restrict: 'E',
                //替换节点
                replace: true,
                //替换内容
                transclude: true,
                //独立作用域, 并引用属性
                scope: {
                    orientation: '@'
                },
                //包裹的节点;
                template: '<div class="split-panes {{orientation}}" ng-transclude></div>',
                controller: function ($scope) {
                    $scope.panes = [];

                    this.addPane = function(pane){
                        if ($scope.panes.length > 1)
                            throw 'splitters can only have two panes';
                        $scope.panes.push(pane);
                        return $scope.panes.length;
                    };
                },
                link: function(scope, element, attrs) {
                    //因为这个组件没有进行双向绑定, 链接阶段就对dom进行更改, 也都没什么事情;

                    //把分隔线添加进来;
                    var handler = angular.element('<div class="split-handler"></div>');
                    var pane1 = scope.panes[0];
                    var pane2 = scope.panes[1];
                    var vertical = scope.orientation == 'vertical';
                    var pane1Min = pane1.minSize || 0;
                    var pane2Min = pane2.minSize || 0;
                    var drag = false;

                    pane1.elem.after(handler);

                    //为这个元素添加事件(不给document添加事件吗?);
                    element.bind('mousemove', function (ev) {
                        if (!drag) return;

                        var bounds = element[0].getBoundingClientRect();
                        var pos = 0;

                        //垂直方向
                        if (vertical) {
                            //这个包裹元素的高度;
                            var height = bounds.bottom - bounds.top;

                            //pos是这个事件的;
                            pos = ev.clientY - bounds.top;


                            if (pos < pane1Min) return;
                            if (height - pos < pane2Min) return;

                            //这种设置高度的方式不常用啊, 但是的确是最方便的方式;
                            handler.css('top', pos + 'px');
                            pane1.elem.css('height', pos + 'px');
                            pane2.elem.css('top', pos + 'px');

                        } else {
                            //左右移动， 水平方向;
                            var width = bounds.right - bounds.left;
                            pos = ev.clientX - bounds.left;

                            if (pos < pane1Min) return;
                            if (width - pos < pane2Min) return;

                            //
                            handler.css('left', pos + 'px');
                            pane1.elem.css('width', pos + 'px');
                            pane2.elem.css('left', pos + 'px');
                        }
                    });

                    //为分割线添加事件;
                    handler.bind('mousedown', function (ev) {
                        ev.preventDefault();
                        //添加了拖拽的标志;
                        drag = true;
                    });

                    angular.element(document).bind('mouseup', function (ev) {
                        //删除拖拽的标志;
                        drag = false;
                    });
                }
            };
        })

        .directive('bgPane', function () {
            return {
                restrict: 'E',
                //依赖bgSplitter这个controller;
                require: '^bgSplitter',
                replace: true,
                transclude: true,
                scope: {
                    minSize: '='
                },
                //主要流程是根据dom的包裹节点从外部到内部
                //先是界面的渲染是先把指令转换成template, 然后为每一个指令定义controller控制器, 然后进行link;
                template: '<div class="split-pane{{index}}" ng-transclude></div>',
                link: function(scope, element, attrs, bgSplitterCtrl) {
                    scope.elem = element;
                    scope.index = bgSplitterCtrl.addPane(scope);
                }
            };
        })


        /**
         * 控制输入正整数
         */
        .directive('numbers0', function($parse) {
                return {
                    link: function (scope, element, attrs, ctrl) {
                        //控制输入框只能输入数字
                        function limit(){
                            var limitV=element[0].value;
                            var str = attrs["reg"];
                            var reg = eval(str);
                            if(attrs["regLength"]){
                                limitV = limitV.slice(0,attrs["regLength"]);
                            }
                            limitV=limitV.replace(reg,"");
                            element[0].value=limitV;
                            $parse(attrs['ngModel']).assign(scope, limitV);
                        }
                        scope.$watch(attrs.ngModel,function(){
                            limit();
                        })
                    }
                };
            })
        /*
            输入框输入时按金额格式变化自动插入千位分隔符
         */
        .directive('toChangeNumber', function($parse,SIEJS) {
            return {
                link: function (scope, element, attrs, ctrl) {
                    //控制输入框只能输入数字和小数点
                    function limit(){
                        var str = attrs["reg"];
                        var reg = eval(str);
                        if (str) {
                            disableInputPercent(reg);
                        }

                        var limitV=element[0].value;
                        //limitV=limitV.replace(/[^0-9.]/g,"");
                        limitV=limitV.replace(/[^\-?\d.]/g,"");//可输入负数
                        element[0].value=limitV;
                        $parse(attrs['ngModel']).assign(scope, limitV);
                        format();
                    }

                    //对输入数字的整数部分插入千位分隔符
                    function format(){
                        var formatV=element[0].value;
                        var array=[];
                        array=formatV.split(".");
                        var re=/(-?\d+)(\d{3})/;
                        while(re.test(array[0])){
                            array[0]=array[0].replace(re,"$1,$2")
                        }
                        var returnV=array[0];
                        for(var i=1;i<array.length;i++){
                            returnV+="."+array[i];
                        }
                        element[0].value=returnV;
                        $parse(attrs['ngModel']).assign(scope, formatV);


                    }

                    //禁止输入百分符
                    function disableInputPercent(reg){
                        var inputV=element[0].value;
                        if (reg.test(inputV)) {
                            SIEJS.alert('不能输入百分号(%)', 'warning');
                            return;
                        }
                        element[0].value = inputV;
                        $parse(attrs['ngModel']).assign(scope, inputV);

                    }

                    scope.$watch(attrs.ngModel,function(){
                        limit();
                    })
                }
            };
        })
    
    /* 城市联动
         * data-default-country='999'   默认国家的id
         * data-set-key='id' // 根据已选择的城市数据进行绑定下拉菜单的 主键
         * data-prop-list=['id','name','code']    //  对象的属性
         * data-title=''   显示的标题
         * data-select-level='4'   显示下拉菜单数。
         * data-required='true'  是否必须选
         * 返回对象 ngModel= {
         *    id:[],
         *    name:[],
         *    code:[],
         *    cityName:'' 返回对应name拼接的字符串
         *    }
         *
         *    返回预设默认值 方法：
         *       给ngModel 赋 id 值 ，如：$scope.citys.id:[999,"1-54IN","1-5APP","1-5NSK"]
         *
         *       scope.$parent[scope.id]={
         *       reset: function(){}  // 重置事件
         * */
        .directive('cityArea', function (httpServer, URLAPI, SIEJS) {
            return {
                restrict: 'EA',
                replace: false,
                scope: {
                    ngModel: '='
                },
                templateUrl: 'js/directives/html/city-area-tpl.html',
                link: function (scope, element, attr, controller) {
                    if (!attr.ngModel) {
                        SIEJS.alert('请给cityArea设置 ng-model 值', 'error');
                        return;
                    }
                    scope.id = attr.id || attr.cityArea || 'cityArea_' + Date.parse(new Date());

                    if (typeof scope.$parent[scope.id] === 'undefined') {
                        scope.$parent[scope.id] = {};
                    }

                    scope.title = attr.title;
                    var prop = attr.propList ? scope.$eval(attr.propList) : ['id', 'name', 'code']; // 获取对象的属性；
                    var setKey = attr.setKey || prop[0];
                    scope.isDefault = false; // 是否默认赋值
                    // debugger;
                    scope.selectLevel = (attr.selectLevel || 4) * 1; // 显示 多少个select ?
                    scope.required = attr.required; // 是否必选

                    if (!scope.ngModel || JSON.stringify(scope.ngModel) === '{}') {
                        scope.ngModel = {};
                        for (var p in prop) {
                            scope.ngModel[prop[p]] = new Array(scope.selectLevel);
                        }
                    } else {
                        scope.isDefault = true;
                        scope.DefaultVale = angular.copy(scope.ngModel[prop[0]]);
                        // debugger;
                        for (var p in prop) {
                            if (!scope.ngModel[prop[p]]) scope.ngModel[prop[p]] = new Array(scope.selectLevel);
                        }
                    }

                    /*scope.__areas = {
                     country: {},
                     province: {},
                     city: {},
                     district: {}
                     };*/
                    scope.__areas = {};
                    var defaultCountry = attr.defaultCountry || 999; // 默认选择999 中国的id

                    /* select 的默认值
                     *  list 数据集
                     *  defaultVal 要设置选择的默认值
                     *  type 返回的 类型
                     *  key 关联的属性主键
                     * */
                    function setOptionDefault(list, defaultVal, type, key) {
                        key = key || setKey;
                        for (var n = 0; list.length > 0; n++) {
                            var item = list[n];
                            if (item[key] == defaultVal) {
                                scope.__areas[type] = item;
                                break;
                            }
                        }
                    }


                    /* 国家
                     *  name 查询名
                     *  setDefault 是否设置默认值
                     *  */
                    function getCountryData(name, setDefault) {
                        httpServer.post(URLAPI.getCountryData, {
                            pageRows: -1,
                            params: JSON.stringify({
                                name: name
                            })
                        }, function (res) {
                            if (res.status === 'S') {
                                scope.countryList = res.data;
                                if (setDefault || defaultCountry) {
                                    // debugger;
                                    var _id = scope.DefaultVale ? scope.DefaultVale[0] : defaultCountry;
                                    setOptionDefault(scope.countryList, _id, 'country'); // 设置默认值
                                    scope.checkedCountry(setDefault);
                                }

                            } else {
                                SIEJS.alert(res.msg, 'error');
                            }
                        })
                    }

                    // 省
                    function getProvinceDataByCountryId(id, setDefault) {
                        httpServer.post(URLAPI.getProvinceDataByCountryId, {
                            pageRows: -1,
                            params: JSON.stringify({
                                countryId: id
                            })
                        }, function (res) {
                            if (res.status === 'S') {
                                scope.provinceList = res.data;
                                // debugger;
                                if (setDefault) {
                                    if (!scope.DefaultVale[1]) return;
                                    var _id = scope.DefaultVale[1] || defaultCountry;
                                    setOptionDefault(scope.provinceList, _id, 'province'); // 设置默认值
                                    scope.checkedProvince(setDefault);
                                }
                            } else {
                                SIEJS.alert(res.msg, 'error');
                            }
                        })
                    }

                    // 市
                    function getCityDataByProvinceId(id, setDefault) {
                        httpServer.post(URLAPI.getCityDataByProvinceId, {
                            pageRows: -1,
                            params: JSON.stringify({
                                provinceId: id
                            })
                        }, function (res) {
                            if (res.status === 'S') {
                                scope.cityList = res.data;
                                // debugger;
                                if (setDefault) {
                                    if (!scope.DefaultVale[2]) return;
                                    var _id = scope.DefaultVale[2] || defaultCountry;
                                    setOptionDefault(scope.cityList, _id, 'city'); // 设置默认值
                                    scope.checkedCity(setDefault);
                                }
                            } else {
                                SIEJS.alert(res.msg, 'error');
                            }
                        })
                    }

                    // 县区
                    function getCountryAreaDataByCityId(id, setDefault) {
                        httpServer.post(URLAPI.getCountryAreaDataByCityId, {
                            pageRows: 1000,
                            params: JSON.stringify({
                                cityId: id
                            })
                        }, function (res) {
                            if (res.status === 'S') {
                                scope.districtList = res.data;
                                // debugger;
                                if (setDefault) {
                                    if (!scope.DefaultVale[3]) return;
                                    var _id = scope.DefaultVale[3] || defaultCountry;
                                    setOptionDefault(scope.districtList, _id, 'district'); // 设置默认值
                                    scope.checkedDistrict();
                                }
                            } else {
                                SIEJS.alert(res.msg, 'error');
                            }
                        })
                    }


                    // 选择国家
                    scope.checkedCountry = function (setDefault) {
                        if (scope.__areas.country) {
                            getProvinceDataByCountryId(scope.__areas.country.id, setDefault);
                        } else {
                            // scope.__areas.country = {};
                            scope.provinceList = [];
                        }
                        // scope.__areas.province = {};
                        // scope.__areas.city = {};
                        // scope.__areas.district = {};
                        scope.cityList = [];
                        scope.districtList = [];
                        setValue();
                    };

                    //选择省
                    scope.checkedProvince = function (setDefault) {
                        if (scope.__areas.province) {
                            getCityDataByProvinceId(scope.__areas.province.id, setDefault);
                        } else {
                            // scope.__areas.province = {}
                            scope.cityList = [];
                        }
                        // scope.__areas.city = {};
                        // scope.__areas.district = {};
                        scope.districtList = [];
                        setValue();
                    };

                    // 选择市
                    scope.checkedCity = function (setDefault) {
                        if (scope.__areas.city) {
                            getCountryAreaDataByCityId(scope.__areas.city.id, setDefault);
                        } else {
                            // scope.__areas.city = {}
                            scope.districtList = [];
                        }
                        // scope.__areas.district = {};
                        setValue();
                    };

                    // 选择街道
                    scope.checkedDistrict = function () {
                        if (!scope.__areas.district) {
                            // scope.__areas.district = {};
                        }

                        setValue();
                    };


                    // 赋值
                    function setValue() {
                        //  赋值 的属性
                        function setProperty(name, index) {
                            for (var m in scope.ngModel) {
                                if (scope.__areas[name]) {
                                    scope.ngModel[m][index] = scope.__areas[name][m];
                                }
                            }
                        }

                        for (var n in scope.__areas) {
                            switch (n) {
                                case 'country':
                                    setProperty(n, 0);
                                    break;
                                case 'province':
                                    if (scope.selectLevel > 1) setProperty(n, 1);
                                    break;
                                case 'city':
                                    if (scope.selectLevel > 2) setProperty(n, 2);
                                    break;
                                case 'district':
                                    if (scope.selectLevel > 3) setProperty(n, 3);
                                    break;
                            }
                        }
                        scope.ngModel.cityName = scope.ngModel[prop[1]] ? scope.ngModel[prop[1]].join().replace(/,/g, '') : '';
                        //console.log(JSON.stringify(scope.ngModel));
                    }

                    // 组件初始化国家数据
                    getCountryData(null, scope.isDefault);

                    // 重置数据
                    scope.$parent[scope.id].reset = function () {
                        scope.ngModel = {};
                        for (var p in prop) {
                            scope.ngModel[prop[p]] = new Array(scope.selectLevel);
                        }
                        /* scope.isDefault = true;
                         scope.DefaultVale = [];*/
                        getCountryData();
                    };

                    // 重新赋选中的值 id 为标准国、省、市、县区 数组 []
                    scope.$parent[scope.id].setSelected = function (id) {

                        scope.DefaultVale = angular.copy(id);
                        //debugger;
                        for (var p in prop) {
                            if (!scope.ngModel[prop[p]]) scope.ngModel[prop[p]] = new Array(scope.selectLevel);
                        }
                        getCountryData(null, true);
                    }
                }
            };
        });
    //app.useModule('SAAF.mod.directives');
    return module;
});


