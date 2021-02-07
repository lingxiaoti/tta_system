/**
 * Created by hmx on 2018/9/11.
 */
'use strict';
define(['angular', 'app'], function (angular, app) {
    var module = angular.module('EMS.ControlDirectives', []);
    module
        .directive('eUserSelect', function ($rootScope, httpServer, URLAPI, $location, $timeout) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: {},
                scope: {
                    ngModel: '=',// 指令与父级的双向绑定
                    ngValue: '=',
                    ngKey: '=',
                    ngParams: '=',
                    returnFun: '@'

                },
                templateUrl: 'js/directives/html/EMSControlTpl/user-list-tpl.html',
                link: function (scope, element, attrs, controller) {
                    scope.title = attrs.title;
                    scope.params = {};

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        //console.log(element);
                        //console.log(attrs);
                        // scope.params.responsibilityId = scope.ngParams.responsibilityId;
                        // scope.params.orgId = scope.ngParams.orgId;
                        // scope.params.userType = scope.ngParams.userType;
                        for (var i in scope.ngParams) {
                            scope.params[i] = scope.ngParams[i];
                        }

                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTableSelection.search(1);
                            }, 300)
                        }
                    });



                    scope.callback = function () {
                        if (scope.dataTableSelection.selectRow) {
                            scope.ngModel = scope.dataTableSelection.selectRow;
                            console.log(scope.ngModel);
                            $timeout(function () {
                                scope.$parent[scope.returnFun]();
                            }, 0)
                        }

                    };

                    scope.cancel = function () {
                        scope.ngModel = {};
                        $timeout(function () {
                            scope.$parent[scope.returnFun]();
                        }, 0)
                    };

                }
            }
        })

        //行人员选择控件--需传参数职责ID,部门ID
        .directive('eUserLineSelect', function ($rootScope, httpServer, URLAPI, $location, $timeout) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: {},
                scope: {
                    ngModel: '=',// 指令与父级的双向绑定
                    ngValue: '=',
                    ngKey: '=',
                    ngParams: '=',
                    returnFun: '@',
                    searchAuto: '@'  //打开弹窗是否自动查询

                },
                templateUrl: 'js/directives/html/EMSControlTpl/user-linelist-tpl.html',
                link: function (scope, element, attrs, controller) {
                    scope.title = attrs.title;
                    scope.params = {};

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        for (var i in scope.ngParams) {
                            scope.params[i] = scope.ngParams[i];
                        }
                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTableSelection.search(1);
                            }, 300)
                        }
                    });



                    scope.callback = function () {
                        if (scope.dataTableSelection.selectRow) {
                            scope.ngModel = scope.dataTableSelection.selectRow;
                            console.log(scope.ngModel);
                            $timeout(function () {
                                scope.$parent[scope.returnFun]();
                            }, 0)
                        }


                    };

                    scope.cancel = function () {
                        scope.ngModel = {};
                        $timeout(function () {
                            scope.$parent[scope.returnFun]();
                        }, 0)
                    };

                }
            }
        })


        //费用申请控件--需传参数职责ID,部门ID
        .directive('eApplySelect', function ($rootScope, httpServer, URLAPI, $location, $timeout) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: {},
                scope: {
                    ngModel: '=',// 指令与父级的双向绑定
                    ngValue: '=',
                    ngKey: '=',
                    ngParams: '=',
                    returnFun: '@'

                },
                templateUrl: 'js/directives/html/EMSControlTpl/apply-cost-tpl.html',
                link: function (scope, element, attrs, controller) {
                    scope.title = attrs.title;
                    scope.params = {};

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        for (var i in scope.ngParams) {
                            scope.params[i] = scope.ngParams[i];
                        }

                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTableSelection.getData(1);
                            }, 300)
                        }
                    });



                    scope.callback = function () {
                        if (scope.dataTableSelection.data[scope.dataTableSelection.data.selectRow]) {
                            scope.ngModel = scope.dataTableSelection.data[scope.dataTableSelection.data.selectRow];
                            console.log(scope.ngModel);
                            $timeout(function () {
                                scope.$parent[scope.returnFun]();
                            }, 0)
                        }

                    };

                    scope.cancel = function () {
                        scope.ngModel = {};
                        $timeout(function () {
                            scope.$parent[scope.returnFun]();
                        }, 0)
                    };

                }
            }
        })


        //期间控件--需传参数职责ID,期间类型
        .directive('ePeriod', function ($rootScope, httpServer, URLAPI, $location, $timeout) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: {},
                scope: {
                    ngModel: '=',// 指令与父级的双向绑定
                    ngValue: '=',
                    ngKey: '=',
                    ngParams: '=',
                    returnFun: '@'

                },
                templateUrl: 'js/directives/html/EMSControlTpl/period-tpl.html',
                link: function (scope, element, attrs, controller) {
                    scope.title = attrs.title;
                    scope.params = {};

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        for (var i in scope.ngParams) {
                            scope.params[i] = scope.ngParams[i];
                        }
                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTablePeriod.search(1);
                            }, 300)
                        }
                    });



                    scope.callback = function () {
                        if (scope.dataTablePeriod.selectRow) {
                            scope.ngModel = scope.dataTablePeriod.selectRow;
                            console.log(scope.ngModel);
                            $timeout(function () {
                                scope.$parent[scope.returnFun]();
                            }, 0)
                        }

                    };

                    scope.cancel = function () {
                        scope.ngModel = {};
                        $timeout(function () {
                            scope.$parent[scope.returnFun]();
                        }, 0)
                    };

                }
            }
        })

        //excel文件上传指令
        .directive('uploadFile', function (SIEJS, httpServer, URLAPI, $location, FileUploader, Cookies) {
            return {
                restrict: 'EA',
                replace: true,
                scope: {
                    curobj: '=',
                    uploadUrl: '=',
                    upcb: '@',
                    upParams: '=',
                    respId: '=',
                    formData:'='
                },
                template: '<input nv-file-select uploader="uploader" type="file" class="fileBtn"/>',
                link: function ($scope, element, attr) {

                },
                controller: function ($scope, $element, $attrs) {
                    var loading = $("#saafLoading");
                    console.log($scope.formData)
                    //debugger;
                    if (loading.length === 0) {
                        var loading = $('<div id="saafLoading"><div  style="position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;"><img src="img/loading1.gif"></div></div>');
                        loading.css({
                            position: "fixed",
                            top: 0,
                            width: "100%",
                            "z-index": 9000,
                            "height": "100%",
                            'display': 'none'
                        });
                        loading.prependTo(angular.element('body'));
                    } else {
                        loading.removeAttr('data-loading');
                    }
                    //上传
                    var uploadUrl = URLAPI[$scope.uploadUrl];
                    var uploader = $scope.uploader = new FileUploader({
                        url: uploadUrl+'?respId='+$scope.respId, //上传路径
                        queue: [],//需上传的文件队列
                        formData:[{
                            params:JSON.stringify($scope.formData)
                        }],
                        headers: {
                            //'Content-Type': 'application/x-www-form-urlencoded'
                            "Certificate": Cookies.getCookie(appName + '_certificate') || sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                        },
                        filters: [{//格式限制
                            name: 'imageFilter',
                            fn: function (item, options) {
                                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                                if (type == '||') {
                                    type = '|' + item.name.substr(item.name.lastIndexOf('.') + 1) + '|';
                                }
                                return '|vnd.openxmlformats-officedocument.spreadsheetml.sheet|vnd.ms-excel|xls|xlsx|'.indexOf(type) !== -1;
                            }
                        }],//过滤上传的文件|gif|pdf|zip|rar|x-zip-compressed|
                        queueLimit: 1,     //文件个数
                        autoUpload: true, //添加文件到队列后自动上传
                        removeAfterUpload: true   //上传后删除文件
                    });

                    if ($attrs.upParams) {
                        $scope.watchParams = $scope.$watch('upParams', function (nval, oval) {
                            if (nval === oval && !nval) return;
                            for (var o in nval) {
                                if(typeof nval[o] === 'undefined') continue;
                                uploader.url += '&' + o + '=' + nval[o];
                            }
                            $scope.uploader = uploader;
                        }, true);

                        $scope.$on("$destroy", function () {
                            $scope.watchParams();
                        })
                    }
                    if ($attrs.formData) {
                        $scope.watchFormData = $scope.$watch('formData', function (nval, oval) {
                            if (nval === oval && !nval) return;

                            uploader.formData=[{
                                params:JSON.stringify(nval)
                            }],
                            $scope.uploader = uploader;
                        }, true);

                        $scope.$on("$destroy", function () {
                            $scope.watchFormData();
                        })
                    }

                    uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
                        SIEJS.alert('当前格式仅支持|xls|xlsx|格式！！', "error", "确定");//|jpg|png|jpeg|bmp|gif|pdf|zip|rar|xls|xlsx|docx|zip|rar|
                    };

                    uploader.onAfterAddingFile = function (items) {
                        $scope.curobj = {finish: false, name: items._file.name};
                    };

                    uploader.onCompleteItem = function (fileItem, res, status, headers) {
                        if ('S' == res.status) {
                            $scope.curobj = res;
                            $scope.curobj.finish = true;
                            $scope.$parent[$scope.upcb](res);
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                        $($element).val('');
                        loading.removeAttr('data-loading');
                    };

                    uploader.onBeforeUploadItem = function(item) {
                        loading.show();
                    };

                    uploader.onSuccessItem = function() {
                        loading.hide();
                    }

                    uploader.onErrorItem = function() {
                        loading.hide();
                    }

                    uploader.onCompleteAll = function () {
                        uploader.clearQueue();//清除队列
                    };
                }
            }
        })

        /**
         * EMS 经销商控件
         */
        .directive('emsDealerSelect', function ($rootScope, httpServer, URLAPI, $location, $timeout, SIEJS) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: true,
                scope: {
                    ngModel: '=',
                    selParams: '=',
                    returnKey: '=?',
                    returnValue: '=?',
                    callaction: '&',
                    emptyaction: '&'
                },
                templateUrl: function (ele, attrs) {
                    if (attrs.dirType === 'header') {
                        return 'js/directives/html/EMSControlTpl/header-dealer-list-tpl.html'
                    } else if (attrs.dirType === 'row') {
                        return 'app/ems/service/dirHtml/row-dealer-list-tpl.html'
                    }
                },
                link: function (scope, element, attrs, controller) {
                    if (!attrs.dirType) {
                        SIEJS.alert('未添加dirType属性，无法判断经销商类型', 'error', '确定');
                        return;
                    }

                    if (attrs.keys && scope.$eval(attrs.keys)) {
                        scope.keys = scope.$eval(attrs.keys);
                    } else {
                        SIEJS.alert('keys属性配置不正确', 'error', '确定');
                        return;
                    }

                    scope.title = attrs.title ? attrs.title : '经销商选择';

                    scope.autoRequest = (attrs.autoRequest == true || attrs.autoRequest == 'true') ? true : false;

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        if (attrs.dirType === 'row') {
                            if (!scope.selParams.departmentId) {
                                SIEJS.alert('请选择部门', 'error', '确定');
                                return false;
                            }
                        }

                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTableSelection.search(1);
                            }, 300)
                        }
                    })

                    scope.params = {};

                    // 传进来的参数赋值给表格的查询参数
                    if (attrs.selParams) {
                        scope.watchSelParams = scope.$watch('selParams', function (nval, oval) {
                            if (nval === oval && !nval) return;
                            for (var o in nval) {
                                scope.params[o] = nval[o];
                            }
                        }, true);

                        scope.$on("$destroy", function () {
                            scope.watchSelParams();
                        })
                    }

                    scope.callback = function () {
                        if (attrs.ngModel) {
                            console.log(scope.dataTableSelection.selectRow)
                            scope.ngModel = scope.dataTableSelection.selectRow;
                        }
                        scope.setReturn();
                        $timeout(function () {
                            scope.callaction();
                        })
                    }

                    scope.cancel = function () {
                        scope.ngModel = '';
                        scope.returnKey = '';
                        scope.returnValue = '';
                        $timeout(function () {
                            scope.emptyaction();
                        })
                    };

                    scope.setReturn = function () {
                        if (attrs.keys) {
                            if (attrs.returnKey) {
                                scope.returnKey = scope.dataTableSelection.selectRow[scope.keys.key];
                            }
                            if (attrs.returnValue) {
                                scope.returnValue = scope.dataTableSelection.selectRow[scope.keys.value];
                            }
                        }
                    }
                }
            }
        })


        /**
         * EMS 费用科目控件
         */
        .directive('emsExpSubjectSelect', function ($rootScope, httpServer, URLAPI, $location, $timeout, SIEJS) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: true,
                scope: {
                    ngModel: '=',
                    selParams: '=',
                    returnKey: '=?',
                    returnValue: '=?',
                    callaction: '&',
                    emptyaction: '&'
                },
                templateUrl: 'js/directives/html/EMSControlTpl/expense-subject-list-tpl.html',
                link: function (scope, element, attrs, controller) {

                    if (attrs.keys && scope.$eval(attrs.keys)) {
                        scope.keys = scope.$eval(attrs.keys);
                    } else {
                        SIEJS.alert('keys属性配置不正确', 'error', '确定');
                        return;
                    }

                    scope.title = attrs.title ? attrs.title : '费用科目';

                    scope.autoRequest = (attrs.autoRequest == true || attrs.autoRequest == 'true') ? true : false;

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTableSelection.search(1);
                            }, 300)
                        }
                    })

                    scope.params = {};

                    // 传进来的参数赋值给表格的查询参数
                    if (attrs.selParams) {
                        scope.watchSelParams = scope.$watch('selParams', function (nval, oval) {
                            if (nval === oval && !nval) return;
                            for (var o in nval) {
                                scope.params[o] = nval[o];
                            }
                        }, true);

                        scope.$on("$destroy", function () {
                            scope.watchSelParams();
                        })
                    }

                    scope.callback = function () {
                        if (attrs.ngModel) {
                            scope.ngModel = scope.dataTableSelection.selectRow;
                        }
                        scope.setReturn();
                        $timeout(function () {
                            scope.callaction();
                        })
                    }

                    scope.cancel = function () {
                        scope.ngModel = '';
                        scope.returnKey = '';
                        scope.returnValue = '';
                        $timeout(function () {
                            scope.emptyaction();
                        })
                    };

                    scope.setReturn = function () {
                        if (attrs.keys) {
                            if (attrs.returnKey) {
                                scope.returnKey = scope.dataTableSelection.selectRow[scope.keys.key];
                            }
                            if (attrs.returnValue) {
                                scope.returnValue = scope.dataTableSelection.selectRow[scope.keys.value];
                            }
                        }
                    }
                }
            }
        })

        /**
         * EMS 部门控件
         */
        .directive('emsDeptSelect', function ($rootScope, httpServer, URLAPI, $location, $timeout, SIEJS) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: true,
                scope: {
                    ngModel: '=',
                    selParams: '=',
                    returnKey: '=?',
                    returnValue: '=?',
                    callaction: '&',
                    emptyaction: '&'
                },
                templateUrl: function (ele, attrs) {
                    if (!attrs.deptType || attrs.deptType === 'header') {
                        return 'js/directives/html/EMSControlTpl/dept-list-tpl.html'
                    } else if (attrs.deptType === 'row') {
                        return 'js/directives/html/EMSControlTpl/dept-row-list-tpl.html'
                    }
                },
                // templateUrl: 'app/ems/service/dirHtml/dept-list-tpl.html',
                link: function (scope, element, attrs, controller) {
                    scope.title = attrs.title ? attrs.title : '部门选择';

                    if (attrs.keys && scope.$eval(attrs.keys)) {
                        scope.keys = scope.$eval(attrs.keys);
                    } else {
                        SIEJS.alert('keys属性配置不正确', 'error', '确定');
                        return;
                    }

                    scope.autoRequest = (attrs.autoRequest == true || attrs.autoRequest == 'true') ? true : false;

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        //打开modal框前可以在这里添加逻辑，不满足return false
                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTableSelection.search(1);
                            }, 300)
                        }
                    })

                    scope.params = {};

                    // 传进来的参数赋值给表格的查询参数
                    if (attrs.selParams) {
                        scope.watchSelParams = scope.$watch('selParams', function (nval, oval) {
                            if (nval === oval && !nval) return;
                            for (var o in nval) {
                                scope.params[o] = nval[o];
                            }
                        }, true);

                        scope.$on("$destroy", function () {
                            scope.watchSelParams();
                        })
                    }

                    scope.callback = function () {
                        if (attrs.ngModel) {
                            scope.ngModel = scope.dataTableSelection.selectRow;
                        }
                        scope.setReturn();
                        $timeout(function () {
                            scope.callaction();
                        })
                    }

                    scope.cancel = function () {
                        scope.ngModel = '';
                        scope.returnKey = '';
                        scope.returnValue = '';
                        $timeout(function () {
                            scope.emptyaction();
                        })
                    };

                    scope.setReturn = function () {
                        if (attrs.keys) {
                            if (attrs.returnKey) {
                                scope.returnKey = scope.dataTableSelection.selectRow[scope.keys.key];
                            }
                            if (attrs.returnValue) {
                                scope.returnValue = scope.dataTableSelection.selectRow[scope.keys.value];
                            }
                        }
                    }
                }
            }
        })


        /**
         * EMS 费用申请列表控件
         */
        .directive('emsFeeApplySelect', function ($rootScope, httpServer, URLAPI, $location, $timeout, SIEJS) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: true,
                scope: {
                    ngModel: '=',
                    selParams: '=',
                    returnKey: '=?',
                    returnValue: '=?',
                    callaction: '&',
                    emptyaction: '&'
                },
                templateUrl: 'js/directives/html/EMSControlTpl/fee-list-tpl.html',
                link: function (scope, element, attrs, controller) {
                    scope.title = attrs.title ? attrs.title : '费用申请选择';

                    if (attrs.keys && scope.$eval(attrs.keys)) {
                        scope.keys = scope.$eval(attrs.keys);
                    } else {
                        SIEJS.alert('keys属性配置不正确', 'error', '确定');
                        return;
                    }

                    scope.autoRequest = (attrs.autoRequest == true || attrs.autoRequest == 'true') ? true : false;

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        //打开modal框前可以在这里添加逻辑，不满足return false
                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTableSelection.search(1);
                            }, 300)
                        }
                    })

                    scope.params = {};

                    // 传进来的参数赋值给表格的查询参数
                    if (attrs.selParams) {
                        scope.watchSelParams = scope.$watch('selParams', function (nval, oval) {
                            if (nval === oval && !nval) return;
                            for (var o in nval) {
                                scope.params[o] = nval[o];
                            }
                        }, true);

                        scope.$on("$destroy", function () {
                            scope.watchSelParams();
                        })
                    }

                    scope.callback = function () {
                        if (attrs.ngModel) {
                            scope.ngModel = scope.dataTableSelection.selectRow;
                        }
                        scope.setReturn();
                        $timeout(function () {
                            scope.callaction();
                        })
                    }

                    scope.cancel = function () {
                        scope.ngModel = '';
                        scope.returnKey = '';
                        scope.returnValue = '';
                        $timeout(function () {
                            scope.emptyaction();
                        })
                    };

                    scope.setReturn = function () {
                        if (attrs.keys) {
                            if (attrs.returnKey) {
                                scope.returnKey = scope.dataTableSelection.selectRow[scope.keys.key];
                            }
                            if (attrs.returnValue) {
                                scope.returnValue = scope.dataTableSelection.selectRow[scope.keys.value];
                            }
                        }
                    }
                }
            }
        })


        /**
         * EMS 银行账号控件
         */
        .directive('emsBankInfoSelect', function ($rootScope, httpServer, URLAPI, $location, $timeout, SIEJS) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: true,
                scope: {
                    ngModel: '=',
                    selParams: '=',
                    returnKey: '=?',
                    returnValue: '=?',
                    callaction: '&',
                    emptyaction: '&'
                },
                templateUrl: 'js/directives/html/EMSControlTpl/bank-list-tpl.html',
                link: function (scope, element, attrs, controller) {
                    scope.title = attrs.title ? attrs.title : '银行账号选择';

                    if (attrs.keys && scope.$eval(attrs.keys)) {
                        scope.keys = scope.$eval(attrs.keys);
                    } else {
                        SIEJS.alert('keys属性配置不正确', 'error', '确定');
                        return;
                    }

                    scope.autoRequest = (attrs.autoRequest == true || attrs.autoRequest == 'true') ? true : false;

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        //打开modal框前可以在这里添加逻辑，不满足return false
                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTableSelection.search(1);
                            }, 300)
                        }
                    })

                    scope.params = {};

                    // 传进来的参数赋值给表格的查询参数
                    if (attrs.selParams) {
                        scope.watchSelParams = scope.$watch('selParams', function (nval, oval) {
                            if (nval === oval && !nval) return;
                            for (var o in nval) {
                                scope.params[o] = nval[o];
                            }
                        }, true);

                        scope.$on("$destroy", function () {
                            scope.watchSelParams();
                        })
                    }

                    scope.callback = function () {
                        if (attrs.ngModel) {
                            scope.ngModel = scope.dataTableSelection.selectRow;
                        }
                        scope.setReturn();
                        $timeout(function () {
                            scope.callaction();
                        })
                    }

                    scope.cancel = function () {
                        scope.ngModel = '';
                        scope.returnKey = '';
                        scope.returnValue = '';
                        $timeout(function () {
                            scope.emptyaction();
                        })
                    };

                    scope.setReturn = function () {
                        if (attrs.keys) {
                            if (attrs.returnKey) {
                                scope.returnKey = scope.dataTableSelection.selectRow[scope.keys.key];
                            }
                            if (attrs.returnValue) {
                                scope.returnValue = scope.dataTableSelection.selectRow[scope.keys.value];
                            }
                        }
                    }
                }
            }
        })

        /**
         * EMS 费用项目控件
         */
        .directive('emsExpenseSelect', function ($rootScope, httpServer, URLAPI, $location, $timeout, SIEJS) {
            return {
                restrict: 'EA',
                require: '?ngModel',
                replace: true,
                scope: {
                    ngModel: '=',
                    selParams: '=',
                    returnKey: '=?',
                    returnValue: '=?',
                    callaction: '&',
                    emptyaction: '&'
                },
                templateUrl: 'js/directives/html/EMSControlTpl/expense-list-tpl.html',
                link: function (scope, element, attrs, controller) {
                    scope.title = attrs.title ? attrs.title : '费用项目选择';

                    if (attrs.keys && scope.$eval(attrs.keys)) {
                        scope.keys = scope.$eval(attrs.keys);
                    } else {
                        SIEJS.alert('keys属性配置不正确', 'error', '确定');
                        return;
                    }

                    scope.autoRequest = (attrs.autoRequest == true || attrs.autoRequest == 'true') ? true : false;

                    $('#' + attrs.id).on('show.bs.modal', function (e) {
                        //打开modal框前可以在这里添加逻辑，不满足return false
                        if (attrs.searchAuto && attrs.searchAuto == 'true') {
                            $timeout(function () {
                                scope.dataTableSelection.search(1);
                            }, 300)
                        }
                    })

                    scope.params = {};

                    // 传进来的参数赋值给表格的查询参数
                    if (attrs.selParams) {
                        scope.watchSelParams = scope.$watch('selParams', function (nval, oval) {
                            if (nval === oval && !nval) return;
                            for (var o in nval) {
                                scope.params[o] = nval[o];
                            }
                        }, true);

                        scope.$on("$destroy", function () {
                            scope.watchSelParams();
                        })
                    }

                    scope.callback = function () {
                        if (attrs.ngModel) {
                            scope.ngModel = scope.dataTableSelection.selectRow;
                        }
                        scope.setReturn();
                        $timeout(function () {
                            scope.callaction();
                        })
                    }

                    scope.cancel = function () {
                        scope.ngModel = '';
                        scope.returnKey = '';
                        scope.returnValue = '';
                        $timeout(function () {
                            scope.emptyaction();
                        })
                    };

                    scope.setReturn = function () {
                        if (attrs.keys) {
                            if (attrs.returnKey) {
                                scope.returnKey = scope.dataTableSelection.selectRow[scope.keys.key];
                            }
                            if (attrs.returnValue) {
                                scope.returnValue = scope.dataTableSelection.selectRow[scope.keys.value];
                            }
                        }
                    }
                }
            }
        })


        /**
         * 相差月份算法
         */
        .service('beYear', function () {
            return function (beYear) {
                var date = new Date();
                var sY = '',
                    sM = '',
                    eY = date.getFullYear(),
                    eM = date.getMonth() > 10 ? date.getMonth() + 1 : date.getMonth() + 1;

                if (date.getMonth() + 1 - beYear < 1) {
                    sY = date.getFullYear() - 1;
                    if (date.getMonth() + 1 - beYear == 0) {
                        sY = date.getFullYear();
                        sM = 1;
                    } else if (date.getMonth() + 1 - beYear < 0) {
                        sM = 12 + (date.getMonth() + 2 - beYear);
                    } else if (date.getMonth() + 1 - beYear > 0) {
                        sM = 12 - (date.getMonth() + 2 - beYear);
                    }
                } else {
                    sY = date.getFullYear();
                    sM = date.getMonth() + 2 - beYear;
                }
                return {stateDate: sY + '-' + (sM > 9 ? sM : '0' + sM), endDate: eY + '-' + (eM > 9 ? eM : '0' + eM)}
            }
        })

    return module;
})
