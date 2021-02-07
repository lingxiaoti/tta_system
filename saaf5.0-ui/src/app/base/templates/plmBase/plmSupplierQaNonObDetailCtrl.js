/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('plmSupplierQaNonObDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, $compile, FileUploader, iframeMessage) {
        var id = $stateParams.id;

        $scope.headerData = {};
        $scope.originSpecialProductType = undefined;
        var oldData = {};

        $scope.plmSupplierQaBrandDataTable = [];
        $scope.plmDealerDataTable = [];
        $scope.plmProducerDataTable = [];
        $scope.specialProductTypeList = [];
        $scope.dealerQaTypeList = [];
        $scope.producerQaTypeList = [];


        $scope.brandFileIndex = undefined;
        $scope.dealerFileIndex = undefined;
        $scope.producerFileIndex = undefined;

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;

        //查询单据信息
        $scope.search = function (string) {

            $scope.plmSupplierQaNonObInfoId = id;

            httpServer.post(URLAPI.findPlmSupplierQaNonObInfoInfo, {
                    'params': JSON.stringify({plmSupplierQaNonObInfoId: $scope.plmSupplierQaNonObInfoId})
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.headerData = res.data[0];
                        oldData = angular.copy($scope.headerData);
                        $scope.headerData.originSpecialProductType = $scope.headerData.specialProductType;
                        $scope.updateDealerQaType();
                        $scope.updateProducerQaType();
                        if(string!=='header') {
                            $scope.searchPlmSupplierQaBrand();
                            $scope.searchDealer();
                            $scope.searchProducer();
                        }

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };

        //动态添加特殊商品类型
        $scope.addSpecialProductType = function () {
            var params = {};
            httpServer.post(URLAPI.findPlmSpecialProductTypeInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.specialProductTypeList = res.data;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );

        };

        $scope.addSpecialProductType();

        $scope.recordSpecialProductTypeName = function (newVal) {
            $scope.headerData.specialProductTypeName = $('#specialProductTypeId option:checked').text();
            $scope.headerData.specialProductType = newVal;

            $scope.saveFunction('silent');
            $scope.updateDealerQaType();
            $scope.updateProducerQaType();
        };

        //id不为空，初始搜索
        if ($stateParams.id) {
            //查询头信息
            $scope.search();
        } else {
            $scope.headerData.billStatus = 'TODO';

        }

        /*********************品牌 start*********************/
        $scope.addPlmSupplierQaBrand = function () {
            // if($scope.headerData.plmSupplierQaNonObInfoId === undefined){
            //     SIEJS.alert('请先保存头表','error','确定');
            //     return;
            // }
            if($scope.headerData.billStatus === 'APPROVAL'){
                SIEJS.alert('已审批单据禁止修改','error','确定');
                return;
            }
            var data = {};
            data.plmSupplierQaNonObInfoId = $scope.headerData.plmSupplierQaNonObInfoId;
            $scope.plmSupplierQaBrandDataTable.push(data);
        };

        //动态增加加号
        var html = "<tr><td ng-click=\"addPlmSupplierQaBrand()\"  style=\"font-size: 14px\"><i class=\"fa fa-plus-square-o\"></i></td></tr>";
        var $html = $compile(html)($scope);
        $("#plmSupplierQaBrandBody").append($html);

        //品牌资质文件上传
        $scope.brandFileUploader = new FileUploader({
            url: URLAPI.imgUpload,
            autoUpload: true,
            removeAfterUpload: true,
            headers: {
                "Certificate": sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing'
            }
        });

        $scope.brandFileUploader.filters.push({
            name:'filter',
            fn: function (item, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|pdf|'.indexOf(type) !== -1;
            }
        });

        $scope.brandFileUploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
            SIEJS.alert('当前仅支持jpg,png,jpeg,pdf格式文件','error','确定');
        };

        //品牌资质文件上传回调
        $scope.brandFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
            if (res.status !== 'S') {
                console.log(res);
                SIEJS.alert(res.msg ? res.msg : '文件上传失败', 'error', '确定');
                return;
            }
            var currentIndex = $scope.brandFileIndex;
            $scope.plmSupplierQaBrandDataTable[currentIndex].fileName = res.data[0].fileName;
            $scope.plmSupplierQaBrandDataTable[currentIndex].uploadDate = res.data[0].uploadDate;
            $scope.plmSupplierQaBrandDataTable[currentIndex].fileAddress = res.data[0].filePath;
            $scope.plmSupplierQaBrandDataTable[currentIndex].fileType = res.data[0].fileType;
            SIEJS.alert('上传成功', 'success', '确定');
            return;
        };

        $scope.uploadBrandFile = function (index) {
            $scope.brandFileIndex = index;
            document.getElementById('brandFileUploadId').click();
        };

        //保存品牌行
        $scope.saveBrand = function () {

            // for(var i = 0; i < $scope.plmSupplierQaBrandDataTable.length; i++){
            //     if($scope.plmSupplierQaBrandDataTable[i].brandCnName===undefined||$scope.plmSupplierQaBrandDataTable[i].brandCnName===''){
            //         SIEJS.alert('品牌名称未填写','error','确定');
            //         return;
            //     }
            //     if($scope.plmSupplierQaBrandDataTable[i].brandQaType===undefined||$scope.plmSupplierQaBrandDataTable[i].brandQaType===''){
            //         SIEJS.alert('品牌资质类型未选择','error','确定');
            //         return;
            //     }
            //     if($scope.plmSupplierQaBrandDataTable[i].fileAddress===undefined||$scope.plmSupplierQaBrandDataTable[i].fileAddress===''){
            //         SIEJS.alert('品牌资质文件未上传','error','确定');
            //         return;
            //     }
            // }

            var params = {};
            params.plmSupplierQaBrandList = $scope.plmSupplierQaBrandDataTable;
            httpServer.post(URLAPI.savePlmSupplierQaBrandInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.searchPlmSupplierQaBrand();
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.deleteBrand = function (index) {
            var plmSupplierQaBrandId = $scope.plmSupplierQaBrandDataTable[index].plmSupplierQaBrandId;

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if(plmSupplierQaBrandId === undefined || plmSupplierQaBrandId =='' ){
                    $scope.plmSupplierQaBrandDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                    $scope.$apply();
                }
                else {
                    $scope.deleteBrandAction(index);
                }
            });
        };

        $scope.deleteBrandAction = function(index){
            var params = {};
            var array = [];
            array.push($scope.plmSupplierQaBrandDataTable[index]);
            params.plmSupplierQaBrandList = array;
            httpServer.post(URLAPI.deletePlmSupplierQaBrandInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.search('header');
                        $scope.plmSupplierQaBrandDataTable.splice(index, 1);
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //查询品牌行
        $scope.searchPlmSupplierQaBrand = function () {
            httpServer.post(URLAPI.findPlmSupplierQaBrandInfo, {
                    'params': JSON.stringify({plmSupplierQaNonObInfoId: $scope.plmSupplierQaNonObInfoId}),
                    'pageRows': 1000
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.plmSupplierQaBrandDataTable = res.data;
                        oldData.plmSupplierQaBrandList = angular.copy($scope.plmSupplierQaBrandDataTable);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };

        /**********************品牌 end************************/


        /********************** 经销商 start **************************/

        $scope.updateDealerQaType = function(){
            if($scope.headerData.specialProductType === '0'){
                $scope.dealerQaTypeList = [{dealerQaTypeValue: '营业执照',dealerQaTypeName:'营业执照'},
                    {dealerQaTypeValue: '食品经营许可证',dealerQaTypeName:'食品经营许可证'},
                    {dealerQaTypeValue: '其他',dealerQaTypeName:'其他'}];
                return;
            }
            if($scope.headerData.specialProductType === '1'){
                $scope.dealerQaTypeList = [{dealerQaTypeValue: '营业执照',dealerQaTypeName:'营业执照'},
                    {dealerQaTypeValue: '上一年度企业年度公示报告',dealerQaTypeName:'上一年度企业年度公示报告'},
                    {dealerQaTypeValue: '《药品经营（或生产）许可证》',dealerQaTypeName:'《药品经营（或生产）许可证》'},
                    {dealerQaTypeValue: '《药品经营（或生产）质量管理规范认证》',dealerQaTypeName:'《药品经营（或生产）质量管理规范认证》'},
                    {dealerQaTypeValue: '印章备案表',dealerQaTypeName:'印章备案表'},
                    {dealerQaTypeValue: '发票样式',dealerQaTypeName:'发票样式'},
                    {dealerQaTypeValue: '开户户名、开户银行及账号',dealerQaTypeName:'开户户名、开户银行及账号'},
                    {dealerQaTypeValue: '随货同行单样式',dealerQaTypeName:'随货同行单样式'},
                    {dealerQaTypeValue: '质量保证体系情况表',dealerQaTypeName:'质量保证体系情况表'},
                    {dealerQaTypeValue: '法人授权书',dealerQaTypeName:'法人授权书'},
                    {dealerQaTypeValue: '销售人员身份证复印件',dealerQaTypeName:'销售人员身份证复印件'},
                    {dealerQaTypeValue: '质量保证协议',dealerQaTypeName:'质量保证协议'},
                    {dealerQaTypeValue: '法人授权书',dealerQaTypeName:'法人授权书'},
                    {dealerQaTypeValue: '其他',dealerQaTypeName:'其他'}
                ];
                return;
            }
            if($scope.headerData.specialProductType === '2'){
                $scope.dealerQaTypeList = [{dealerQaTypeValue: '营业执照',dealerQaTypeName:'营业执照'},
                    {dealerQaTypeValue: '上一年度企业年度公示报告',dealerQaTypeName:'上一年度企业年度公示报告'},
                    {dealerQaTypeValue: '《第一类医疗器械生产备案凭证》',dealerQaTypeName:'《第一类医疗器械生产备案凭证》'},
                    {dealerQaTypeValue: '《医疗器械生产许可证》',dealerQaTypeName:'《医疗器械生产许可证》'},
                    {dealerQaTypeValue: '《医疗器械经营许可证》',dealerQaTypeName:'《医疗器械经营许可证》'},
                    {dealerQaTypeValue: '《第二类医疗器械经营备案凭证》',dealerQaTypeName:'《第二类医疗器械经营备案凭证》'},
                    {dealerQaTypeValue: '销售授权书原件',dealerQaTypeName:'销售授权书原件'},
                    {dealerQaTypeValue: '被委托人身份证复印件',dealerQaTypeName:'被委托人身份证复印件'},
                    {dealerQaTypeValue: '质量保证协议',dealerQaTypeName:'质量保证协议'},
                    {dealerQaTypeValue: '其他',dealerQaTypeName:'其他'}
                ];
                return;
            }

        };

        //获取经销商资质类型变动值
        $scope.initDealerInfoByQaType = function(newVal){
            console.log('修改行为'+$scope.plmDealerDataTable.selectRow+'\n 原类型为'+$scope.plmDealerDataTable[$scope.plmDealerDataTable.selectRow].personQaType);
            var index = $scope.plmDealerDataTable.selectRow;
            if($scope.headerData.specialProductType === '0'){
                $scope.plmDealerDataTable[index].caCodeTypeName = '证书编号';
                $scope.plmDealerDataTable[index].dateTypeName = '失效日期';
                return;
            }

            if($scope.headerData.specialProductType === '1'){
                //先特殊后一般
                if(newVal === '营业执照'){
                    $scope.plmDealerDataTable[index].caCodeTypeName = '统一信用代码';
                    $scope.plmDealerDataTable[index].dateTypeName = '失效日期';
                    return;
                }

                if(newVal === '上一年度企业年度公示报告'){
                    $scope.plmDealerDataTable[index].caCodeTypeName = '证书编号';
                    $scope.plmDealerDataTable[index].dateTypeName = '填报日期';
                    return;
                }

                if(newVal === '《药品经营（或生产）许可证》'|| newVal === '《药品经营（或生产）质量管理规范认证》'||newVal === '法人授权书'
                    || newVal === '销售人员身份证复印件' || newVal === '质量保证协议'){
                    $scope.plmDealerDataTable[index].caCodeTypeName = '证书编号';
                    $scope.plmDealerDataTable[index].dateTypeName = '失效日期';
                    return;
                }

                $scope.plmDealerDataTable[index].caCodeTypeName = '证书编号';
                $scope.plmDealerDataTable[index].dateTypeName = '签发日期';
                return;
            }

            if($scope.headerData.specialProductType === '2') {
                if(newVal === '营业执照'){
                    $scope.plmDealerDataTable[index].caCodeTypeName = '统一信用代码';
                    $scope.plmDealerDataTable[index].dateTypeName = '失效日期';
                    return;
                }
                if(newVal === '上一年度企业年度公示报告'){
                    $scope.plmDealerDataTable[index].caCodeTypeName = '证书编号';
                    $scope.plmDealerDataTable[index].dateTypeName = '填报日期';
                    return;
                }
                if(newVal === '《第一类医疗器械生产备案凭证》'|| newVal === '《第二类医疗器械经营备案凭证》'){
                    $scope.plmDealerDataTable[index].caCodeTypeName = '备案编号';
                    $scope.plmDealerDataTable[index].dateTypeName = '备案日期';
                    return;
                }
                if(newVal === '其他'){
                    $scope.plmDealerDataTable[index].caCodeTypeName = '证书编号';
                    $scope.plmDealerDataTable[index].dateTypeName = '签发日期';
                    return;
                }
                $scope.plmDealerDataTable[index].caCodeTypeName = '证书编号';
                $scope.plmDealerDataTable[index].dateTypeName = '失效日期';
            }

        };



        $scope.addDealer = function () {
            // if($scope.headerData.plmSupplierQaNonObInfoId === undefined){
            //     SIEJS.alert('请先保存头表','error','确定');
            //     return;
            // }
            if($scope.headerData.billStatus === 'APPROVAL'){
                SIEJS.alert('已审批单据禁止修改','error','确定');
                return;
            }
            var data = {};
            data.plmSupplierQaNonObInfoId = $scope.headerData.plmSupplierQaNonObInfoId;
            data.personType = 'DEALER';
            data.personTypeName = '经销商';
            $scope.plmDealerDataTable.push(data);
        };

        //品牌资质文件上传
        $scope.dealerFileUploader = new FileUploader({
            url: URLAPI.imgUpload,
            autoUpload: true,
            removeAfterUpload: true,
            headers: {
                "Certificate": sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing'
            }
        });

        $scope.dealerFileUploader.filters.push({
            name:'filter',
            fn: function (item, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|pdf|'.indexOf(type) !== -1;
            }
        });

        $scope.dealerFileUploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
            SIEJS.alert('当前仅支持jpg,png,jpeg,pdf格式文件','error','确定');
        };

        //品牌资质文件上传回调
        $scope.dealerFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
            if (res.status !== 'S') {
                console.log(res);
                SIEJS.alert(res.msg ? res.msg : '文件上传失败', 'error', '确定');
                return;
            }
            var currentIndex = $scope.dealerFileIndex;
            $scope.plmDealerDataTable[currentIndex].fileName = res.data[0].fileName;
            $scope.plmDealerDataTable[currentIndex].uploadDate = res.data[0].uploadDate;
            $scope.plmDealerDataTable[currentIndex].fileAddress = res.data[0].filePath;
            $scope.plmDealerDataTable[currentIndex].fileType = res.data[0].fileType;
            SIEJS.alert('上传成功', 'success', '确定');
        };

        $scope.uploadDealerFile = function (index) {
            $scope.dealerFileIndex = index;
            document.getElementById('dealerFileUpload').click();
        };

        //保存品牌行
        $scope.saveDealer = function () {

            // for(var i = 0; i < $scope.plmDealerDataTable.length; i++){
            //     if($scope.plmDealerDataTable[i].personName===undefined||$scope.plmDealerDataTable[i].personName===''){
            //         SIEJS.alert('经销商名称未填写','error','确定');
            //         return;
            //     }
            //     if($scope.plmDealerDataTable[i].personQaType===undefined||$scope.plmDealerDataTable[i].personQaType===''){
            //         SIEJS.alert('经销商资质类型未选择','error','确定');
            //         return;
            //     }
            //     if($scope.plmDealerDataTable[i].fileAddress===undefined||$scope.plmDealerDataTable[i].fileAddress===''){
            //         SIEJS.alert('经销商资质文件未上传','error','确定');
            //         return;
            //     }
            //     if($scope.plmDealerDataTable[i].caCode===undefined||$scope.plmDealerDataTable[i].caCode===''){
            //         SIEJS.alert('经销商证书编号未填写','error','确定');
            //         return;
            //     }
            //     if($scope.plmDealerDataTable[i].chooseDate===undefined||$scope.plmDealerDataTable[i].chooseDate===''){
            //         SIEJS.alert('经销商日期未选择','error','确定');
            //         return;
            //     }
            // }

            var params = {};
            params.plmSupplierQaDealerList = $scope.plmDealerDataTable;
            httpServer.post(URLAPI.savePlmSupplierQaDealerInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.search();
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.deleteDealer = function (index) {
            var plmSupplierQaDealerId = $scope.plmDealerDataTable[index].plmSupplierQaDealerId;

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if(plmSupplierQaDealerId == null || plmSupplierQaDealerId =='' ){
                    $scope.plmDealerDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                    $scope.$apply();
                }
                else {
                    $scope.deleteDealerAction(index);
                }
            });

        };

        $scope.deleteDealerAction = function(index){
            var params = {};
            var array = [];
            array.push($scope.plmDealerDataTable[index]);
            params.plmSupplierQaDealerList = array;
            params.plmSupplierQaNonObInfoId = $scope.headerData.plmSupplierQaNonObInfoId;
            httpServer.post(URLAPI.deletePlmSupplierQaDealerInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.search('header');
                        $scope.plmDealerDataTable.splice(index, 1);
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //查询品牌行
        $scope.searchDealer = function () {
            httpServer.post(URLAPI.findPlmSupplierQaDealerInfo, {
                    'params': JSON.stringify({plmSupplierQaNonObInfoId: $scope.plmSupplierQaNonObInfoId, personType: 'DEALER'}),
                    'pageRows': 1000
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.plmDealerDataTable = res.data;
                        oldData.plmDealerList = angular.copy($scope.plmDealerDataTable);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };


        /********************** 经销商 end **************************/

        /********************** 生产商 start **************************/

        $scope.updateProducerQaType = function(){
            if($scope.headerData.specialProductType === '0'){
                $scope.producerQaTypeList = [{producerQaTypeValue: '营业执照',producerQaTypeName:'营业执照'},
                    {producerQaTypeValue: '化妆品生产许可证',producerQaTypeName:'化妆品生产许可证'},
                    {producerQaTypeValue: '食品生产许可证',producerQaTypeName:'食品生产许可证'},
                    {producerQaTypeValue: '消毒产品卫生许可证',producerQaTypeName:'消毒产品卫生许可证'},
                    {producerQaTypeValue: '其他',producerQaTypeName:'其他'}
                ];
                return;
            }
            if($scope.headerData.specialProductType === '1'){
                $scope.producerQaTypeList = [{producerQaTypeValue: '营业执照',producerQaTypeName:'营业执照'},
                    {producerQaTypeValue: '药品生产许可证',producerQaTypeName:'药品生产许可证'}
                ];
                return;
            }
            if($scope.headerData.specialProductType === '2'){
                $scope.producerQaTypeList = [{producerQaTypeValue: '营业执照',producerQaTypeName:'营业执照'},
                    {producerQaTypeValue: '《第一类医疗器械生产备案凭证》',producerQaTypeName:'《第一类医疗器械生产备案凭证》'},
                    {producerQaTypeValue: '《医疗器械生产许可证》',producerQaTypeName:'《医疗器械生产许可证》'}
                ];
                return;
            }

        };

        //获取经销商资质类型变动值
        $scope.initProducerInfoByQaType = function(newVal){
            console.log('修改行为'+$scope.plmProducerDataTable.selectRow+'\n 原类型为'+$scope.plmProducerDataTable[$scope.plmProducerDataTable.selectRow].personQaType);
            var index = $scope.plmProducerDataTable.selectRow;
            if($scope.headerData.specialProductType === '0'){
                if(newVal === '营业执照' || newVal ==='其他'){
                    $scope.plmProducerDataTable[index].caCodeTypeName = '证书编号';
                    $scope.plmProducerDataTable[index].dateTypeName = '失效日期';
                    return;
                }
                if(newVal === '化妆品生产许可证' || newVal ==='食品生产许可证'){
                    $scope.plmProducerDataTable[index].caCodeTypeName = '生产许可证编号';
                    $scope.plmProducerDataTable[index].dateTypeName = '失效日期';
                    return;
                }
                if(newVal === '消毒产品卫生许可证' ){
                    $scope.plmProducerDataTable[index].caCodeTypeName = '卫生许可证号';
                    $scope.plmProducerDataTable[index].dateTypeName = '失效日期';
                    return;
                }
            }

            if($scope.headerData.specialProductType === '1'){
                //先特殊后一般
                if(newVal === '营业执照'){
                    $scope.plmProducerDataTable[index].caCodeTypeName = '统一信用代码';
                    $scope.plmProducerDataTable[index].dateTypeName = '失效日期';
                    return;
                }

                if(newVal === '药品生产许可证'){
                    $scope.plmProducerDataTable[index].caCodeTypeName = '证书编号';
                    $scope.plmProducerDataTable[index].dateTypeName = '失效日期';
                    return;
                }
            }

            if($scope.headerData.specialProductType === '2') {
                if(newVal === '营业执照'){
                    $scope.plmProducerDataTable[index].caCodeTypeName = '统一信用代码';
                    $scope.plmProducerDataTable[index].dateTypeName = '失效日期';
                    return;
                }

                if(newVal === '《第一类医疗器械生产备案凭证》'){
                    $scope.plmProducerDataTable[index].caCodeTypeName = '备案编号';
                    $scope.plmProducerDataTable[index].dateTypeName = '备案日期';
                    return;
                }
                if(newVal === '《医疗器械生产许可证》'){
                    $scope.plmProducerDataTable[index].caCodeTypeName = '证书编号';
                    $scope.plmProducerDataTable[index].dateTypeName = '失效日期';
                    return;
                }

            }

        };



        $scope.addProducer = function () {
            if($scope.headerData.billStatus === 'APPROVAL'){
                SIEJS.alert('已审批单据禁止修改','error','确定');
                return;
            }
            // if($scope.headerData.plmSupplierQaNonObInfoId === undefined){
            //     SIEJS.alert('请先保存头表','error','确定');
            //     return;
            // }
            var data = {};
            data.plmSupplierQaNonObInfoId = $scope.headerData.plmSupplierQaNonObInfoId;
            data.personType = 'PRODUCER';
            data.personTypeName = '生产商';
            $scope.plmProducerDataTable.push(data);
        };

        //品牌资质文件上传
        $scope.producerFileUploader = new FileUploader({
            url: URLAPI.imgUpload,
            autoUpload: true,
            removeAfterUpload: true,
            headers: {
                "Certificate": sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing'
            }
        });

        $scope.producerFileUploader.filters.push({
            name:'filter',
            fn: function (item, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|pdf|'.indexOf(type) !== -1;
            }
        });

        $scope.producerFileUploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
            SIEJS.alert('当前仅支持jpg,png,jpeg,pdf格式文件','error','确定');
        };

        //品牌资质文件上传回调
        $scope.producerFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
            if (res.status !== 'S') {
                console.log(res);
                SIEJS.alert(res.msg ? res.msg : '文件上传失败', 'error', '确定');
                return;
            }
            var currentIndex = $scope.producerFileIndex;
            $scope.plmProducerDataTable[currentIndex].fileName = res.data[0].fileName;
            $scope.plmProducerDataTable[currentIndex].uploadDate = res.data[0].uploadDate;
            $scope.plmProducerDataTable[currentIndex].fileAddress = res.data[0].filePath;
            $scope.plmProducerDataTable[currentIndex].fileType = res.data[0].fileType;
            SIEJS.alert('上传成功', 'success', '确定');
        };

        $scope.uploadProducerFile = function (index) {
            $scope.producerFileIndex = index;
            document.getElementById('producerFileUpload').click();
        };

        //保存品牌行
        $scope.saveProducer = function () {

            // for(var i = 0; i < $scope.plmProducerDataTable.length; i++){
            //     if($scope.plmProducerDataTable[i].personName===undefined||$scope.plmProducerDataTable[i].personName===''){
            //         SIEJS.alert('经销商名称未填写','error','确定');
            //         return;
            //     }
            //     if($scope.plmProducerDataTable[i].personQaType===undefined||$scope.plmProducerDataTable[i].personQaType===''){
            //         SIEJS.alert('经销商资质类型未选择','error','确定');
            //         return;
            //     }
            //     if($scope.plmProducerDataTable[i].fileAddress===undefined||$scope.plmProducerDataTable[i].fileAddress===''){
            //         SIEJS.alert('经销商资质文件未上传','error','确定');
            //         return;
            //     }
            //     if($scope.plmProducerDataTable[i].caCode===undefined||$scope.plmProducerDataTable[i].caCode===''){
            //         SIEJS.alert('经销商证书编号未填写','error','确定');
            //         return;
            //     }
            //     if($scope.plmProducerDataTable[i].chooseDate===undefined||$scope.plmProducerDataTable[i].chooseDate===''){
            //         SIEJS.alert('经销商日期未选择','error','确定');
            //         return;
            //     }
            // }

            var params = {};
            params.plmSupplierQaDealerList = $scope.plmProducerDataTable;
            httpServer.post(URLAPI.savePlmSupplierQaDealerInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.search();
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.deleteProducer = function (index) {
            var plmSupplierQaDealerId = $scope.plmProducerDataTable[index].plmSupplierQaDealerId;

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if(plmSupplierQaDealerId == null || plmSupplierQaDealerId =='' ){
                    $scope.plmProducerDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                    $scope.$apply();
                }
                else {
                    $scope.deleteProducerAction(index);
                }
            });
        };

        $scope.deleteProducerAction = function(index){
            var params = {};
            var array = [];
            array.push($scope.plmProducerDataTable[index]);
            params.plmSupplierQaDealerList = array;
            params.plmSupplierQaNonObInfoId = $scope.headerData.plmSupplierQaNonObInfoId;
            httpServer.post(URLAPI.deletePlmSupplierQaDealerInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.search('header');
                        SIEJS.alert("操作成功!已成功删除数据！", "success");
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        //查询品牌行
        $scope.searchProducer = function () {
            httpServer.post(URLAPI.findPlmSupplierQaDealerInfo, {
                    'params': JSON.stringify({plmSupplierQaNonObInfoId: $scope.plmSupplierQaNonObInfoId, personType: 'PRODUCER'}),
                    'pageRows': 1000
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.plmProducerDataTable = res.data;
                        oldData.plmProducerList = angular.copy($scope.plmProducerDataTable);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };


        /********************** 生产商 end **************************/


        /**********************头按钮操作 start *********************/

        //全存
        $scope.btnSave = function (invalid) {


            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            $scope.saveFunction();

        };

        $scope.reject = function(){
            $scope.saveFunction('reject');
        };

        setInterval(function () {
            $scope.saveFunction('silent');
        },60000);

        $scope.saveFunction = function (string) {

            if($scope.headerData.creatorName===undefined||$scope.headerData.creatorName===''){
                $scope.headerData.creatorName = $scope.userName;
            }

            var params = angular.copy($scope.headerData);

            if(string === 'reject'){
                params.billStatus = 'TODO';
            }

            if($scope.plmSupplierQaBrandDataTable.length>0){
                params.plmSupplierQaBrandList = angular.copy($scope.plmSupplierQaBrandDataTable);
            }

            if($scope.plmProducerDataTable.length>0){
                params.plmSupplierQaProducerList = angular.copy($scope.plmProducerDataTable);
            }

            if($scope.plmDealerDataTable.length>0){
                params.plmSupplierQaDealerList = angular.copy($scope.plmDealerDataTable);
            }

            httpServer.post(URLAPI.savePlmSupplierQaNonObInfoInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        id = res.data.plmSupplierQaNonObInfoId;
                        $scope.search();
                        if(string !=='silent')
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };

        $scope.btnSubmit = function(){

            var currentPlmSupplierQaBrandList = angular.copy($scope.plmSupplierQaBrandDataTable);
            var currentPlmDealerDataTable = angular.copy($scope.plmDealerDataTable);
            var currentPlmProducerDataTable = angular.copy($scope.plmProducerDataTable);

            // //检验行数据是否修改未保存
            // if(JSON.stringify(oldData.plmSupplierQaBrandList)!==JSON.stringify(currentPlmSupplierQaBrandList)){
            //     SIEJS.alert('品牌行数据修改未保存','error','确定');
            //     return;
            // }
            // if(JSON.stringify(oldData.plmDealerList)!==JSON.stringify(currentPlmDealerDataTable)){
            //     SIEJS.alert('经销商行数据修改未保存','error','确定');
            //     return;
            // }
            // if(JSON.stringify(oldData.plmProducerList)!==JSON.stringify(currentPlmProducerDataTable)){
            //     SIEJS.alert('生产商行数据修改未保存','error','确定');
            //     return;
            // }

            for(var m = 0; m < $scope.plmSupplierQaBrandDataTable.length; m++){
                if($scope.plmSupplierQaBrandDataTable[m].brandCnName===undefined||$scope.plmSupplierQaBrandDataTable[m].brandCnName===''){
                    SIEJS.alert('品牌名称未填写','error','确定');
                    return;
                }
                if($scope.plmSupplierQaBrandDataTable[m].brandQaType===undefined||$scope.plmSupplierQaBrandDataTable[m].brandQaType===''){
                    SIEJS.alert('品牌资质类型未选择','error','确定');
                    return;
                }
                if($scope.plmSupplierQaBrandDataTable[m].fileAddress===undefined||$scope.plmSupplierQaBrandDataTable[m].fileAddress===''){
                    SIEJS.alert('品牌资质文件未上传','error','确定');
                    return;
                }
            }

            for(var l = 0; l < $scope.plmProducerDataTable.length; l++){
                if($scope.plmProducerDataTable[l].personName===undefined||$scope.plmProducerDataTable[l].personName===''){
                    SIEJS.alert('经销商名称未填写','error','确定');
                    return;
                }
                if($scope.plmProducerDataTable[l].personQaType===undefined||$scope.plmProducerDataTable[l].personQaType===''){
                    SIEJS.alert('经销商资质类型未选择','error','确定');
                    return;
                }
                if($scope.plmProducerDataTable[l].fileAddress===undefined||$scope.plmProducerDataTable[l].fileAddress===''){
                    SIEJS.alert('经销商资质文件未上传','error','确定');
                    return;
                }
                if($scope.plmProducerDataTable[l].caCode===undefined||$scope.plmProducerDataTable[l].caCode===''){
                    SIEJS.alert('经销商证书编号未填写','error','确定');
                    return;
                }
                if($scope.plmProducerDataTable[l].chooseDate===undefined||$scope.plmProducerDataTable[l].chooseDate===''){
                    SIEJS.alert('经销商日期未选择','error','确定');
                    return;
                }
            }

            for(var i = 0; i < $scope.plmDealerDataTable.length; i++){
                if($scope.plmDealerDataTable[i].personName===undefined||$scope.plmDealerDataTable[i].personName===''){
                    SIEJS.alert('经销商名称未填写','error','确定');
                    return;
                }
                if($scope.plmDealerDataTable[i].personQaType===undefined||$scope.plmDealerDataTable[i].personQaType===''){
                    SIEJS.alert('经销商资质类型未选择','error','确定');
                    return;
                }
                if($scope.plmDealerDataTable[i].fileAddress===undefined||$scope.plmDealerDataTable[i].fileAddress===''){
                    SIEJS.alert('经销商资质文件未上传','error','确定');
                    return;
                }
                if($scope.plmDealerDataTable[i].caCode===undefined||$scope.plmDealerDataTable[i].caCode===''){
                    SIEJS.alert('经销商证书编号未填写','error','确定');
                    return;
                }
                if($scope.plmDealerDataTable[i].chooseDate===undefined||$scope.plmDealerDataTable[i].chooseDate===''){
                    SIEJS.alert('经销商日期未选择','error','确定');
                    return;
                }
            }

            var dealerQaType = '';
            var producerQaType = '';
            //根据特殊商品类型检查行必填项
            for(var k = 0; k < $scope.plmDealerDataTable.length; k++){
                dealerQaType += $scope.plmDealerDataTable[k].personQaType + ',';
            }
            for(var j = 0; j < $scope.plmProducerDataTable.length; j++){
                producerQaType += $scope.plmProducerDataTable[j].personQaType + ',';
            }
            var dealerPassSign = true;
            var producerPassSign = true;
            if($scope.headerData.specialProductType==='0'){
                dealerPassSign = $scope.alertNeededParams('经销商行',dealerQaType, '营业执照');
            }
            if($scope.headerData.specialProductType==='1'){
                dealerPassSign = $scope.alertNeededParams('经销商行',dealerQaType, '营业执照', '上一年度企业年度公示报告', '《药品经营（或生产）许可证》',
                    '印章备案表','发票样式','开户户名、开户银行及账号','随货同行单样式','质量保证体系情况表','法人授权书','销售人员身份证复印件','质量保证协议');
                producerPassSign = $scope.alertNeededParams('生产商行',producerQaType, '营业执照');
            }
            if($scope.headerData.specialProductType==='2'){
                dealerPassSign = $scope.alertNeededParams('经销商行',dealerQaType, '营业执照', '上一年度企业年度公示报告', '销售授权书原件',
                    '被委托人身份证复印件','质量保证协议');
                producerPassSign = $scope.alertNeededParams('生产商行',producerQaType, '营业执照');
            }

            if(dealerPassSign&&producerPassSign) {
                $scope.submitAction();
            }
        };

        $scope.alertNeededParams = function(table,string){
            for(var i = 2; i < arguments.length; i++){
                if(string.indexOf(arguments[i])=== -1){
                    SIEJS.alert('无'+table+'资质类型为'+arguments[i]+'数据！','error','确定');
                    return false;
                }
            }
            return true;
        };

        $scope.submitAction = function(){
            var params = angular.copy($scope.headerData);
            //根据用户类型判断提交状态
            if($scope.userType!=='13'&&$scope.userType!=='75'&&params.billStatus==='TODO'){
                params.billStatus = 'PURCHASE_TODO';
            }
            if($scope.userType==='13'&&params&&params.billStatus==='PURCHASE_TODO'){
                params.billStatus = 'QA_TODO';
            }
            if($scope.userType==='75'&&params.billStatus==='QA_TODO'){
                params.billStatus = 'APPROVAL';
            }
            if(params.creatorName===undefined||params.creatorName===''){
                params.creatorName = $scope.userName;
            }
            SIEJS.confirm('是否提交','','确定',function() {

                httpServer.post(URLAPI.savePlmSupplierQaNonObInfoInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status === 'S') {
                            id = res.data.plmSupplierQaNonObInfoId;
                            $scope.search();
                            SIEJS.alert(res.msg, 'success', '确定');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            })
        };

        $scope.selectPurchaseInfoReturn = function (key, value, returnList) {
            if(returnList.length!==0){
                $scope.headerData.purchaseApprovalRole = returnList[0].userName;
                $scope.headerData.purchaseApprovalUser = returnList[0].userId;
            }else {
                $scope.headerData.purchaseApprovalRole = '';
                $scope.headerData.purchaseApprovalUser = '';
            }
        };

        $scope.selectQaInfoReturn = function (key, value, returnList) {
            if(returnList.length!==0){
                $scope.headerData.qaApprovalRole = returnList[0].userName;
                $scope.headerData.qaApprovalUser = returnList[0].userId;
            } else {
                $scope.headerData.qaApprovalRole ='';
                $scope.headerData.qaApprovalUser ='';
            }
        }


    });
});
