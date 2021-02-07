/**
 * Created by hmx on 2018/9/6.
 */
'use strict';
define(['app','ztree','GooFlow'],function(app) {
    app.controller('clauseManageDCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams','$timeout','validateForm','$filter','$location','$rootScope','$state','iframeTabAction',
        function($scope,httpServer,URLAPI,SIEJS,$stateParams,$timeout,validateForm,$filter, $location, $rootScope, $state,iframeTabAction) {
            // tab 切换
            $scope.tabAction = 'clauseManageDBusiness'; //根据页面配置
            var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
            //获取url参数对象
            $scope.urlParams = urlToObj(location.hash);
            //先判断是否业务form是否有id 再判断流程form的id
            if ($stateParams.itemId) {
                $scope.id = $stateParams.itemId;
            } else if ($scope.urlParams.businessKey) {
                $scope.id = $scope.urlParams.businessKey;
            }
            $scope.CountH = {};
            $scope.clauseTree ={};
            $scope.currentNodeData =null ;
            $scope.clauseT ={expressionValue:''};
            $scope.clauseTX ={clauseId:-1,clauseCn:'请选择'};
            $scope.clauseTXTta ={clauseId:-1,clauseCn:'请选择'};
            $scope.clauseTXFee ={clauseId:-1,clauseCn:'请选择'};
            $scope.firstText = "请选择";
            $scope.unitList = [];//单位 主从为主的数据
            $scope.rootParentId = "0"; // 默认顶层的ID
            $scope.teamFrameworkId = getId();
            $scope.dataTableCo = [];
            $scope.clause = {};
            $scope.clause.disRequired = false ;
            $scope.clause.flag = false ;
            $scope.paramsBpm = {};
            $scope.treeUrl ="findClauseTree";
            $scope.treeParams = {teamFrameworkId:$scope.teamFrameworkId};
            $scope.processStatus = "DRAFT";// status 流程状态  DRAFT.草稿   APPROVAL.审批中  ALLOW.审批通过  REFUSAL.审批驳回 CLOSED.已关闭
            //增加按钮
            $scope.btnNew = function () {
                var expressionArray = {
                    localValue:'',
                    globalValue:'',
                    symbol:'',
                    inputValue:'',
                    isDefaultValue:'0',
                    deleteFlag:0,
                    isEnable:'Y',
                    isMajor:'Y',
                    unitValue:''
                };
                $scope.dataTableCo.push(expressionArray);
            }

            //删除表达式

            $scope.btnDel = function () {

                var index=$scope.dataTableCo.selectRow
                var collectTypeId = $scope.dataTableCo[index].collectTypeId;


                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    if (collectTypeId == null || collectTypeId == "") {
                        $scope.dataTableCo.splice(index, 1);             //新增列没有ID直接删除
                        SIEJS.alert("操作成功!", "已成功删除数据！", "success");
                        $scope.$apply();
                    }
                    else {
                        $scope.dataTableCoDelete($scope.dataTableCo[index],index);      //修改列有ID删除该行
                        $scope.$apply();
                    }

                })

            }

            //删除单位
            $scope.dataTableCoDelete = function (param,index){
                param.deleteFlag ='1';
                httpServer.post(URLAPI.deleteCollectTypeLine, {
                    'params': JSON.stringify(param)
                }, function (res) {
                    if (res.status == 'S') {
                        SIEJS.alert("删除成功", "success", "确定");
                        $scope.dataTableCo.splice(index, 1);
                    }
                    else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            }

            //增加下级
            $scope.btnNewClauseT = function (){
                $scope.clauseT = {
                    clauseCnP:'',
                    pCode:'0',
                    expressionValue:'',
                    isHierarchyShow:'0',
                    isLeaf:'0',
                    status:'1',
                    isDefault:'1'
                };

                    $scope.dataTableCo = [];



            }

            //删除当前以及所有下级
            $scope.btnDelClauseT = function (){
                var children = [];
                if(!$scope.currentNodeData){
                    SIEJS.alert("请先选中要删除的节点", "success", "确定");
                    return;
                }
                $scope.currentClauseCn =  $scope.currentNodeData.clauseCn ;
                if($scope.currentNodeData.children){
                    children =  $scope.currentNodeData.children ;
                }
               var  param ={
                   children: children,
                   clauseId:$scope.currentNodeData.clauseId}
                    SIEJS.confirm('当前删除的节点以及子节点名称【'+$scope.currentClauseCn+"】", '是否确定删除？', '确定', function () {
                        if(!$scope.clauseT || !$scope.clauseT.clauseId){
                            SIEJS.alert('请选择一个层级', "error", "确定");
                        }else{
                            httpServer.post(URLAPI.deleteClauseTree, {
                                'params': JSON.stringify(param)
                            }, function (res) {
                                    if (res.status == 'S') {
                                        //$scope.clauseT ={};
                                        $scope.reloadParam = {
                                            code: $scope.currentNodeData.pCode,
                                            flag: 'delete'
                                        };
                                        var node =  $scope.ztreeData.treeObj.getNodeByParam('code',$scope.reloadParam .code);
                                        if(node){
                                            var param = {
                                                clauseId :node.clauseId
                                            }
                                            $scope.getClauseOne(param,true);
                                        }else{
                                            $scope.clauseT = {
                                                expressionValue:'',
                                                isHierarchyShow:'0',
                                                isLeaf:'0',
                                                status:'1'
                                            };
                                        }
                                        $scope.clauseTree.reload();
 /*                                       $scope.clauseT = {
                                            expressionValue:'',
                                            isHierarchyShow:'0',
                                            isLeaf:'0',
                                            status:'1'
                                        };*/
                                        SIEJS.alert("删除成功", "success", "确定");
                                    }
                                    else {
                                    SIEJS.alert(res.msg, "error", "确定");
                                    }
                                }, function (err) {
                                         SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                                 });
                    }

                })

            }
            //保存 头表
            $scope.btnSave = function () {

                $scope.clauseEY = [];
                $scope.clauseEN = [];
                for(var i=0;i<$scope.dataTableCo.length;i++){
                    var one = $scope.dataTableCo[i];
                    if(!one.unitValue){
                        one.unitValue ="";
                    }
                    for(var j=0;j<$scope.dataTableCo.length;j++){
                        var two = $scope.dataTableCo[j];
                        if(!two.unitValue){
                            two.unitValue ="";
                        }
                        if( one.unitValue == two.unitValue  && i !=j){
                            SIEJS.alert('单位必须唯一,重复的是'+one.unitValue,'error','确定');
                            return ;
                        }
                    }
                    if('Y' == one.isMajor || one.parentId){
                        $scope.clauseEY.push(one);
                    }else{
                        $scope.clauseEN.push(one);
                    }

                }
                $scope.paramAll ={
                    clause:$scope.clause,
                    clauseT:$scope.clauseT,
                    clauseEY:$scope.clauseEY,
                    clauseEN:$scope.clauseEN
                }

                if('02' ===  $scope.clauseT.businessType &&  JSON.stringify($scope.dataTableCo)=='{}'){
                    SIEJS.alert("请添加收取方式", "error", "确定");
                }
                httpServer.post(URLAPI.saveOrUpdateTraermsAll, {
                    'params': JSON.stringify($scope.paramAll)
                }, function (res) {
                    if (res.status == 'S') {
                        var flag = $scope.clauseT.clauseId?false:true;
                        $scope.teamFrameworkId = res.data.clause.teamFrameworkId ;
                        $scope.treeParams = {teamFrameworkId:$scope.teamFrameworkId};
                        $scope.reloadParam = {
                            code: res.data.clauseT.pCode,
                            flag: 'save'
                        };
                        var node =  $scope.ztreeData.treeObj.getNodeByParam('code',$scope.reloadParam .code);
                        $scope.getClause(res.data.clause.teamFrameworkId,node== null?null:node.clauseId);
                        $scope.clauseTree.reload();

                       // $scope.clauseTree.reload();

/*                        $scope.clauseT = {
                            expressionValue:'',
                            isHierarchyShow:'0',
                            isLeaf:'0',
                            status:'1'
                        };*/
                        $scope.btnC(res.data.clause.billStatus);
                        SIEJS.alert("保存成功", "success", "确定");
                    }
                    else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").removeAttr("disabled");
                });
            };

            $scope.addTreeData = function (flag,res){
                if(flag){
                    var newNode ={
                        chkDisabled: true,
                        clauseCn:res.data.clauseT.clauseCn,
                        clauseId:res.data.clauseT.clauseId,
                        code:res.data.clauseT.code,
                        icon:"",
                        isLeaf:res.data.clauseT.isLeaf,
                        pCode:res.data.clauseT.pCode,
                        orderNo:res.data.clauseT.orderNo,
                        teamFrameworkId:res.data.clause.teamFrameworkId
                    };
                    var  index = 0;
                    if($scope.currentNodeData.children){
                        for(var i=0;i<$scope.currentNodeData.children.length;i++){
                            if(res.data.clauseT.orderNo>$scope.currentNodeData.children[i].orderNo){
                                index = i+1;
                            }
                        }
                    }

                    var newNodes = $scope.ztreeData.treeObj.addNodes($scope.currentNodeData,index,newNode);
                    $scope.ztreeData.treeObj.selectNode(newNodes[0]);

                    for(var i = 0 ;i<$scope.SaveclauseTree.length;i++){
                        if($scope.SaveclauseTree[i].code == $scope.currentNodeData.code){
                            var newNode2 =  angular.copy(newNode) ;
                            newNode2.chkDisabled = newNode2.isLeaf == 0?true:false;
                            newNode2.icon = newNode2.isLeaf == 0? "":"./././././img/open.png";
                            $scope.SaveclauseTree.splice(i+index,1,newNode2);
                            break ;
                        }

                    }
                    $scope.currentNodeData = newNodes[0];
                    //treeObj.addNodes(treeObj.getNodeByParam("code", 1, $scope.currentNodeData.code),index,newNode);
                }else{
                    for(var i = 0 ;i<$scope.SaveclauseTree.length;i++){
                        if($scope.currentNodeData && ($scope.SaveclauseTree[i].code == $scope.currentNodeData.code)){
                            var newNode2 =  angular.copy(newNode) ;
                            $scope.SaveclauseTree[i].chkDisabled = res.data.clauseT.isLeaf == 0?true:false;
                            $scope.SaveclauseTree[i].icon = res.data.clauseT.isLeaf == 0? "":"./././././img/open.png";
                            break ;
                        }

                    }
                }
            }

            /**
             * 获取头表以及树的信息
             * @param id
             */
            $scope.getClause = function(id,value) {
                if("-1" == id){
                    return ;
                }
                httpServer.post(URLAPI.findTtaTermsFrameHeaderPagination, {
                    params: JSON.stringify({
                        teamFrameworkId: id
                    })
                },function(res) {
                    if(res.status === 'S') {
                        $scope.clause = res.data[0];
                        var param = {
                            teamFrameworkId:res.data[0].teamFrameworkId,
                            pCode:"0"
                        }
                        if(value){
                             param = {
                                clauseId:value
                            }
                            $scope.getClauseOne(param,true);
                        }else{
                            $scope.getClauseOne(param,false);
                        }

                        $scope.startFormInfo = JSON.stringify($scope.clause);
                        $scope.btnC(res.data[0].billStatus);
                        $timeout(function () {
                            $scope.setTtaContractLine("clauseLeftId");
                            $scope.setTtaContractLine("clauseRightId");
                        },0);
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败1','error','确定');
                })
            }
            if ($scope.teamFrameworkId == '-1') {
                $scope.clause.createdByName = userLoginInfo.userName;
                $scope.clause.createdBy = userLoginInfo.userId;
                $scope.clause.billStatus = 'DS01';
                $scope.clauseT = {
                    expressionValue:'',
                    isHierarchyShow:'0',
                    isLeaf:'0',
                    status:'1'
                };
            } else {
                $scope.getClause($scope.teamFrameworkId,null);
            }

            /**
             * 获取树的单个信息
             * @param id
             */
            $scope.getClauseOne = function(param,value) {
                httpServer.post(URLAPI.findClauseInfo, {
                    params: JSON.stringify(param)
                },function(res) {
                    if(res.status === 'S') {
                        if(0 != res.data.clauseT.length) {
                            res.data.clauseT[0].isLeaf = "" + res.data.clauseT[0].isLeaf;
                            res.data.clauseT[0].isHierarchyShow = "" + res.data.clauseT[0].isHierarchyShow;
                            res.data.clauseT[0].status = "" + res.data.clauseT[0].status;
                            $scope.clauseBT(res.data.clauseT[0].businessType);
                            $scope.clauseT = res.data.clauseT[0];
                            $scope.startFormInfoT = JSON.stringify($scope.clauseT);
                            $scope.dataTableCo =  res.data.clauseC;

                            //获取 主从 为主 的选择项
                            var find = $scope.dataTableCo.filter(function (x) {
                                return x.isMajor == 'Y';
                            });
                            $scope.unitList = find ;
                        }
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败1','error','确定');
                })
            }

            /**
             * 符号变动时获取值
             */
            $scope.clauseSignGet = function (value){
                if(!$scope.clauseT.isGlobalVariable){
                    SIEJS.alert('请先选择是否全局','error','确定');
                    return ;
                }
                $scope.clauseT.expressionValue = $scope.clauseT.expressionValue+value;
            }
            $scope.clauseSignGetTta = function (value){
                if(!$scope.clauseT.isGlobalTta){
                    SIEJS.alert('请先选择是否全局','error','确定');
                    return ;
                }
                $scope.clauseT.expressionValueTta = $scope.clauseT.expressionValueTta+value;
            }
            $scope.clauseSignGetFee = function (value){
                if(!$scope.clauseT.isGlobalFee){
                    SIEJS.alert('请先选择是否全局','error','确定');
                    return ;
                }
                $scope.clauseT.expressionValueFee = $scope.clauseT.expressionValueFee+value;
            }
            /**
             * 是否变动时获取值
             */
            $scope.isGlobalVariable = function (value){
                $scope.clauseT.expressionValue = "";
                $scope.clauseT.globalValue = "";
            }
            $scope.isGlobalVariableTta = function (value){
                $scope.clauseT.expressionValueTta = "";
                $scope.clauseT.globalValueTta = "";


            }
            $scope.isGlobalVariableFee = function (value){
                $scope.clauseT.expressionValueFee = "";
                $scope.clauseT.globalValueFee = "";
            }

            $scope.isDefaultFun = function (value){
                $scope.clauseT.expressionValueCon = "";
                $scope.clauseT.globalValueCon = "";
            }
            /**
             * 业务类型发生变化
             * @param value
             */

            $scope.clauseBT = function (value){
                if('02'==value){
                    $scope.clauseT.clauseCode= "";
                    $scope.clause.required = true ;
                    $scope.clause.disRequired = false ;
                    $scope.clauseT.paymentMethod = '-';
                }else{
                    $scope.clause.required = false ;
                    $scope.clauseT.paymentMethod = '';
                    $scope.clause.disRequired = true ;

                    $scope.clauseT.isGlobalVariable = '';
                    $scope.clauseT.globalValue = '';
                    $scope.clauseT.expressionValue = '';

                    $scope.clauseT.isGlobalTta = '';
                    $scope.clauseT.globalValueTta ='';
                    $scope.clauseT.expressionValueTta = '';

                    $scope.clauseT.isGlobalFee ='';
                    $scope.clauseT.globalValueFee = '';
                    $scope.clauseT.expressionValueFee = '';

                    $scope.clauseT.isDefault ='';
                    $scope.clauseT.globalValueCon = '';
                    $scope.clauseT.expressionValueCon = '';
                }
            }
            $scope.btnClauseClear = function (){
                $scope.clauseT.expressionValue ="";
            }
            $scope.btnClauseClearTta = function (){
                $scope.clauseT.expressionValueTta ="";
            }
            $scope.btnClauseClearFee = function (){
                $scope.clauseT.expressionValueFee ="";
            }
            $scope.btnClauseClearCon = function (){
                $scope.clauseT.expressionValueCon ="";
            }
            $scope.SaveTree = function (data){
                $scope.ztreeData ={
                    treeObj:data
                };
                if($scope.reloadParam && ('delete' == $scope.reloadParam.flag || 'save' ==$scope.reloadParam.flag)){
                    var node =  data.getNodeByParam('code',$scope.reloadParam .code);
                    data.expandNode(node,true,false,true);
                    if(node){
                        $scope.currentNodeData = node ;
                        data.selectNode(node);
                    }
                }
                $scope.SaveclauseTree = angular.copy($scope.clauseTree.treeData);
                $scope.SaveclauseTreePcode = angular.copy($scope.clauseTree.treeData)  ;
                for(var i = 0 ;i<$scope.SaveclauseTree.length;i++){
                    $scope.SaveclauseTree[i].chkDisabled = $scope.SaveclauseTree[i].isLeaf == 0?true:false;
                    $scope.SaveclauseTree[i].icon = $scope.SaveclauseTree[i].isLeaf == 0? "":"./././././img/open.png";
                }

            }

            var setting = {
                view: {
                    showIcon: true,
                    fontCss: {}
                },
                data: {
                    key: {
                        name: "clauseCn"
                    },
                    simpleData: {
                        enable: "false",
                        idKey: "code",
                        pIdKey: "pCode",
                        rootPId: "0"
                    }
                },
                check: {
                    enable: true,
                    chkStyle: "radio",
                    radioType: "all",
                    chkboxType: {"Y": "", "N": ""}
                },
                callback: {
                    //设置父节点不可选
                    beforeCheck: function (treeId, treeNode) {
                    //    return !treeNode.isParent;
                    }
                }
            };
            var treeObj = null;
            $scope.xClauseCall = function (value){
                $scope.part = value ;
                treeObj = $.fn.zTree.init($("#clauseTreeZtree"), setting, $scope.SaveclauseTree);
                treeObj.expandAll(true);
                $('#addClauseTreej').modal('toggle');
            }

            var treeObjPcode = null;
            $scope.selectPCode = function (){

                for(var i = 0 ;i<$scope.SaveclauseTreePcode.length;i++){
                    $scope.SaveclauseTreePcode[i].chkDisabled = $scope.SaveclauseTreePcode[i].isLeaf == 0?false:true;
                    $scope.SaveclauseTreePcode[i].icon = $scope.SaveclauseTreePcode[i].isLeaf == 0? "./././././img/open.png":"";
                }
                if($scope.clauseT  && $scope.clauseT.code){
                    for(var i = 0 ;i<$scope.SaveclauseTreePcode.length;i++){
                        if($scope.SaveclauseTreePcode[i].code === $scope.clauseT.code ){
                            $scope.SaveclauseTreePcode[i].chkDisabled = true;
                            $scope.SaveclauseTreePcode[i].icon = "";
                            break ;
                        }

                    }
                    $scope.selectPCodeSet($scope.clauseT.code);
                }
                treeObjPcode = $.fn.zTree.init($("#clauseTreePCodeZtree"), setting, $scope.SaveclauseTreePcode);
                treeObjPcode.expandAll(true);
                $('#selectPcodeId').modal('toggle');
            };
            $scope.selectPCodeSet = function (code){
                for(var i = 0 ;i<$scope.SaveclauseTreePcode.length;i++){
                    if($scope.SaveclauseTreePcode[i].pCode === code ){
                        $scope.SaveclauseTreePcode[i].chkDisabled = true;
                        $scope.SaveclauseTreePcode[i].icon = "";
                        $scope.selectPCodeSet($scope.SaveclauseTreePcode[i].code);
                    }

                }
            }

            $scope.savePCode = function (){
                if(treeObjPcode.getCheckedNodes().length !=0){
                    if(!$scope.clauseT){
                        $scope.clauseT = {};
                    }
                    $scope.clauseT.pCode = treeObjPcode.getCheckedNodes()[0].code ;
                    $scope.clauseT.clauseCnP = treeObjPcode.getCheckedNodes()[0].clauseCn ;
                };
                $('#selectPcodeId').modal('toggle');
            };

            //局部值选择保存的时候
            $scope.savePart = function (){
                if(treeObj.getCheckedNodes().length !=0){
                    if(!$scope.clauseT[$scope.part]){
                        $scope.clauseT[$scope.part] = '';
                    }

                    $scope.clauseT[$scope.part] = $scope.clauseT[$scope.part] +'{'+treeObj.getCheckedNodes()[0].clauseId+'}';

                };
                $('#addClauseTreej').modal('toggle');
            }

            $scope.clauseSelect = function (){
                httpServer.post(
                    URLAPI.findClauseTree,
                    {
                        'params': JSON.stringify({"teamFrameworkId": $scope.teamFrameworkId, "isLeaf": 1})
                    },
                    function (res) {
                        console.log(res)
                        if (res.status.toLowerCase() == 'e') {
                            JS.alert(res.msg, "error");
                        }
                        setSelected(res.data);
                    },
                    function (err) {
                        JS.alert('数据获取失败！', "error");
                    }
                );
                $('#clauseModal').modal('toggle');
            }



            /**
             * 点击节点查询节点下的数据
             * @param nodeData
             */
            $scope.clickNode = function(nodeData) {
                $timeout(function() {
                    var param = {
                        clauseId :nodeData.clauseId
                    }
                    $scope.currentNodeData = nodeData ;
                    $scope.getClauseOne(param,false);

                })

            }

            /**********************************工作流配置**************************************/
            //如果表单的ID与页面的头ID不一致，需要做兼容处理
            //url参数转对象
            function urlToObj(url) {
                var index = url.lastIndexOf('?');
                var params = url.substring(index + 1);
                var arr = params.split('&');
                var obj = {};
                arr.forEach(function (item) {
                    obj[item.split('=')[0]] = item.split('=')[1];
                });
                return obj;
            }



            //获取单据ID
            function getId() {
                return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.id;
            }

            //流程图参数
            $scope.processImageParams = {
                token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
                id: 'processImg',
                instanceId: $scope.urlParams.processInstanceId,
                key: 'TTA_APP.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
            };

            $scope.tabChange = function (name) {
                $scope.tabAction = name;
                if (!$scope.processImgLoad) {
                    $scope.getProcessInfo(function () {
                        var p = $scope.processImageParams;
                        $timeout(function () {
                            processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                            $scope.processImgLoad = true;
                        }, 100)
                    });
                }
            };
            // 获取流程信息
            $scope.getProcessInfo = function (callback) {
                httpServer.post(URLAPI.processGet, {
                        'params': JSON.stringify({
                            processDefinitionKey: $scope.processImageParams.key,
                            businessKey: $scope.id
                        })
                    },
                    function (res) {
                        if (res.status === 'S') {
                            $scope.processImageParams.instanceId = res.data.processInstanceId;
                        }
                        callback && callback(res);
                    });
            };


            /**********************************工作流配置**************************************/
                //表单是否变更
            $scope.formIsChanged = function () {
                var endFormInfo = JSON.stringify($scope.clause);
                var endFormInfoT = JSON.stringify($scope.clauseT);
                if ($scope.startFormInfo != endFormInfo) {
                    return true;
                }
                if ($scope.startFormInfoT != endFormInfoT) {
                    return true;
                }
                return false;
            }

                //提交
            $scope.submitApproval = function (form) {
                if(!validateForm(form)){
                    return;
                }
               // if ($scope.formIsChanged()) {
                 //   SIEJS.alert('数据已改变，请先保存', 'info', '确定');
                 //   return;
               // }
                if('02' ===  $scope.clauseT.businessType &&  JSON.stringify($scope.dataTableCo)=='{}'){
                    SIEJS.alert("请添加收取方式", "error", "确定");
                }
                if($scope.clause.billStatus != 'DS01'){
                    SIEJS.alert('状态不是制单中，不允许提交审批', 'error', '确定');
                    return;
                }
                $scope.extend = {
                    "tasks_assignees": []
                }
                $scope.variables = []; //流程变量
                angular.forEach($scope.clause, function (value, key) {
                    $scope.variables.push({
                        name: key,
                        type: 'string',
                        value: value
                    });
                });

                $scope.properties = {
                    "menucode": "HTGL",
                    "i": ""
                };
                $scope.paramsBpm = {
                    "extend": $scope.extend,
                    "variables": $scope.variables,
                    "properties": $scope.properties,
                    "responsibilityId": "990021",
                    "respId": "990021",
                    "processDefinitionKey": "TTA_APP.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                    "saveonly": false,
                    "businessKey": $scope.clause.teamFrameworkId, //单据ID
                    "title": "条款框架审批" + $scope.clause.year, //流程标题，修改为当前业务信息
                    "positionId": 0,
                    "orgId": 0,
                    "userType": "20",
                    "billNo": $scope.clause.year
                };
                httpServer.post(URLAPI.processStart, {
                    'params': JSON.stringify($scope.paramsBpm)
                }, function (res) {
                    if (res.status == 'S') {
                        $timeout(function () {
                            $scope.btnC('DS02');
                        }, 100)
                        $scope.treeParams = {teamFrameworkId:getId()};
                        $scope.getClause(getId(),null);
                        $scope.clauseTree.reload();
                        SIEJS.alert("提交成功", "success", "确定");
                        //$scope.processSubmit();
                        //$("#TJSP").removeAttr("disabled");
                    }
                    else {
                        SIEJS.alert(res.msg, "error", "确定");
                        $scope.getClause($scope.paramsBpm.businessKey,null);

                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                   // $("#TJSP").removeAttr("disabled");
                });
            }

            /**
             * 确定往年数据
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectClauseOld = function(key, value, currentList) {
                httpServer.post(
                    URLAPI.saveOrUpdateCopy,
                    {
                        'params': JSON.stringify({"teamFrameworkId": key})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            iframeTabAction('条款框架详情:'+ 1800,'/clauseManageD/' + res.data.teamFrameworkId);
                        }
                        else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('数据获取失败！', "error");
                    }
                );
            }

            /**
             * 确定选择全局
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectClauseIsGlobal = function(key, value, currentList) {
                if(!$scope.clauseT.isGlobalVariable || '1'!=$scope.clauseT.isGlobalVariable){
                    SIEJS.alert('当前不是全局','error','确定');
                    return ;
                }
                $scope.clauseT.expressionValue = currentList[0].sqlValues;
                $scope.clauseT.globalValue = value;
            }

            $scope.selectClauseIsGlobalTta = function(key, value, currentList) {
                if(!$scope.clauseT.isGlobalTta || '1'!=$scope.clauseT.isGlobalTta){
                    SIEJS.alert('当前不是全局','error','确定');
                    return ;
                }
                $scope.clauseT.expressionValueTta = currentList[0].sqlValues;
                $scope.clauseT.globalValueTta = value;
            }

            $scope.selectClauseIsGlobalFee = function(key, value, currentList) {
                if(!$scope.clauseT.isGlobalFee || '1'!=$scope.clauseT.isGlobalFee){
                    SIEJS.alert('当前不是全局','error','确定');
                    return ;
                }
                $scope.clauseT.expressionValueFee = currentList[0].sqlValues;
                $scope.clauseT.globalValueFee = value;

            }

            $scope.selectClauseIsGlobalCon = function(key, value, currentList) {
                if(!$scope.clauseT.isDefault || '3'!=$scope.clauseT.isDefault){
                    SIEJS.alert('当前不是全局','error','确定');
                    return ;
                }
                $scope.clauseT.expressionValueCon = currentList[0].sqlValues;
                $scope.clauseT.globalValueCon = value;

            }

            $scope.btnChange = function (){
               if("-1" == $scope.id){
                   SIEJS.alert('请先建立单据！', "error");
                   return ;
               }
                if("DS03" != $scope.clause.billStatus){
                    SIEJS.alert('请选择审批通过的条框框架！', "error");
                    return ;
                }
                httpServer.post(
                    URLAPI.changeTraermsAll,
                    {
                        'params': JSON.stringify({"teamFrameworkId": $scope.id})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            //SIEJS.alert("变更成功，请前往条框名目操作", "success", "确定");
                            iframeTabAction('条款名目详情'+ res.data.pYear,'/clauseItemManageD/' + res.data.newId);
                        }
                        else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作数失败！', "error");
                    }
                );
            }

            /**
             * 按钮控制
             * @param value
             */
            $scope.btnC = function ( value){
                if("DS01"  != value){
                    $("#clauseForm input").attr("disabled","true");
                    $scope.clause.flag=true ;
                    $("#clauseForm select").attr("disabled","true");
                    $("#clauseForm textarea").attr("disabled","true");
                    $("#clauseForm button").attr("disabled","true");
                    $("#btnSave").attr("disabled","true");
                    $("#submitApprovala").attr("disabled","true");
                    if("DS02"  == value){
                        $("#btnChangeBt").attr("disabled","true");
                    }else{
                        $("#btnChangeBt").removeAttr("disabled");
                    }
                    //$("#btnCopyBt").attr("disabled","true");
                    //$("#btnChangeBt").attr("disabled","true");
                }else{
                    $("#btnChangeBt").attr("disabled","true");
                }
            }

            //控制滚动条高度
            $scope.setTtaContractLine = function (id){
                if( !$scope.CountH[id]) {
                    var w = jQuery(window);
                    var _top = jQuery("#" + id).offset().top * 1;
                    $scope.CountH[id] = _top;
                    var _h = w.innerHeight();
                    jQuery("#" + id).css("height", (_h - _top) + "px");
                    w.bind('resize', function () {
                        jQuery("#" + id).css("height", (w.innerHeight() - _top) + "px");
                        $timeout(function () {
                            $scope.$apply();
                        }, 100)
                    });
                }
            } ;

            //选择部门
            $scope.getDeptCode = function () {
                //  $scope.areaComponent = e;
                $('#selectDeptId').modal('toggle')
            };

            //点击确认按钮currentNodeData
            $scope.selectDeptReturn = function (key, value, currentList) {
                $scope.clause.deptCode = currentList[0].departmentCode;
                $scope.clause.deptName = currentList[0].departmentName;
                $scope.clause.deptId = -1;
            }

            //是否主从 改动的时候
            $scope.dataTableCoIsMajor = function (index,currentValue,lookUpData){
                var isMajor = $scope.dataTableCo[index].isMajor ;
                if(isMajor && 'Y' == isMajor){
                    $scope.dataTableCo[Number(index)].parentValue = '';
                    $scope.dataTableCo[Number(index)].parentId = '';
                    $scope.dataTableCo[Number(index)].parentIdObjectId = '';
                    $scope.unitList.push($scope.dataTableCo[index]);
                }else{
                    //当主变从的时候,清除父选项为当前的对应的值
                    var parentIdObjectId = 'p_'+$scope.dataTableCo[index].$$hashKey ;
                    var parentId = $scope.dataTableCo[index].collectTypeId;
                        for(var i = $scope.dataTableCo.length-1 ;i>=0;i--){
                            if(parentId){
                                if(parentId == $scope.dataTableCo[i].parentId){
                                    $scope.dataTableCo[i].parentValue = '';
                                    $scope.dataTableCo[i].parentId = '';
                                    $scope.dataTableCo[i].parentIdObjectId = '';
                                }
                            }else{
                                if($scope.dataTableCo[i].parentIdObjectId &&
                                parentIdObjectId == $scope.dataTableCo[i].parentIdObjectId){
                                    $scope.dataTableCo[i].parentValue = '';
                                    $scope.dataTableCo[i].parentId = '';
                                    $scope.dataTableCo[i].parentIdObjectId = '';
                                }
                            }
                        }
                    //select  中清空自己
                    var index = $scope.unitList.indexOf($scope.dataTableCo[index]);
                    if(-1 != index){
                        $scope.unitList.splice(index,1)
                    }
                    console.log($scope.unitList);
                    console.log($scope.dataTableCo);
                }
            }
            //select 父级发生变化的时候
               $scope.unitListChange = function (row,unitList2){
                if(row.parentValue){
                    var selectId = 'dataTableCo_'+row.$$hashKey;
                    var index = document.getElementById(selectId).selectedIndex-1;
                    if(-1 !=index){
                        row.parentIdObjectId = 'p_'+unitList2[index].$$hashKey;
                        if(unitList2[index].collectTypeId){
                            row.parentId =  unitList2[index].collectTypeId;
                        }
                    }
                }else{
                    row.parentId = '';
                    row.parentIdObjectId = '';
                }
            }

            $scope.unitValueChange = function (row){
                var parentIdObjectId = 'p_'+row.$$hashKey ;
                var parentId = row.collectTypeId;
                if(row.unitValue){
                    var finds = $scope.unitList.filter(function (x) {
                        return x.$$hashKey == row.$$hashKey;
                    });
                    if( 0 == finds.length && row.isMajor && 'Y' == row.isMajor){
                        $scope.unitList.push(row);
                    }
                    for(var i = $scope.dataTableCo.length-1 ;i>=0;i--){
                        if(parentId){
                            if(      ($scope.dataTableCo[i].parentId  && parentId == $scope.dataTableCo[i].parentId )
                                ||  (!$scope.dataTableCo[i].parentId && parentIdObjectId == $scope.dataTableCo[i].parentIdObjectId)){
                                $scope.dataTableCo[i].parentValue = row.unitValue;
                                $scope.dataTableCo[i].parentId = row.parentId;
                                $scope.dataTableCo[i].parentIdObjectId = 'p_'+row.$$hashKey;
                            }
                        }else{
                            if($scope.dataTableCo[i].parentIdObjectId &&
                                parentIdObjectId == $scope.dataTableCo[i].parentIdObjectId){
                                $scope.dataTableCo[i].parentValue = row.unitValue;
                                $scope.dataTableCo[i].parentId = row.parentId;
                                $scope.dataTableCo[i].parentIdObjectId = 'p_'+row.$$hashKey;
                            }
                        }
                    }
                }else{
                    var finds = $scope.unitList.filter(function (x) {
                        return x.$$hashKey == row.$$hashKey;
                    });
                    if( 0< finds.length){
                        $scope.unitList.splice($scope.unitList.indexOf(row),1);
                    }
                    for(var i = $scope.dataTableCo.length-1 ;i>=0;i--){
                        if(parentId){
                            if(      ($scope.dataTableCo[i].parentId  && parentId == $scope.dataTableCo[i].parentId )
                                ||  (!$scope.dataTableCo[i].parentId && parentIdObjectId == $scope.dataTableCo[i].parentIdObjectId)){
                                $scope.dataTableCo[i].parentValue = '';
                                $scope.dataTableCo[i].parentId = '';
                                $scope.dataTableCo[i].parentIdObjectId = '';
                            }
                        }else{
                            if($scope.dataTableCo[i].parentIdObjectId &&
                                parentIdObjectId == $scope.dataTableCo[i].parentIdObjectId){
                                $scope.dataTableCo[i].parentValue = '';
                                $scope.dataTableCo[i].parentId = '';
                                $scope.dataTableCo[i].parentIdObjectId = '';
                            }
                        }
                    }

                }

            }
        }])
})
