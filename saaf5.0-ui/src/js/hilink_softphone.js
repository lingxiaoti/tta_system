/*!
*
* 软电话JS类库
* ver:6.1.0.0
*
*/

function fCreateSoftPhone(strAgentID,iAcdType)
{
		var _strAgentID,_iAcdType;
		var sp_ws;
		var sp_heartbeattimer;
		
//关于WebSocket事件
		var _ws_onopened;
		var _ws_onerror;
		var _ws_onclose;
		var _ws_onmessage;
		
		this._strAgentID = strAgentID;
		this._iAcdType = iAcdType;
	
		var sp_bretflag;
		var b_spwsconnectedflag;
		
		b_spwsconnectedflag = 0;
		
//座席状态类		
		var _sp_init_callback;
		var _sp_uninit_callback;
		var _sp_setloginstate_callback;
		var _sp_setivrno_callback;
		var _sp_agentlogin_callback;
		var _sp_agentlogout_callback;
		var _sp_agentready_callback;
		var _sp_agentacw_callback;
		var _sp_agentnotready_callback;
		var _sp_autologin_callback;
		var _sp_getcfg_callback;
		var _sp_setcfg_callback;
		
//呼叫状态类		
		var _sp_answercall_callback;
		var _sp_makecall_callback;
		var _sp_consultcall_callback;
    var _sp_transfercall_callback;
    var _sp_transfertoivr_callback;
    var _sp_conference_callback;
    var _sp_holdcall_callback;
    var _sp_reconnectcall_callback;
    var _sp_hangupcall_callback;
    var _sp_senddtmf_callback;
    
//实时数据操作类
	  var _sp_rt_setcustomercallinfo_callback;
	  var _sp_rt_getcustomercallinfo_callback;
	  var _sp_rt_startsubscribe_callback;
	  var _sp_rt_cancelsubscribe_callback;
	  var _sp_rt_queryrt_callback;
    var _sp_rt_transfermessage_callback;
    
//事件类		    
    var _sp_agentstate_callback;
    var _sp_stationstate_callback;
    var _sp_inboundcall_callback;
    var _sp_callinfochange_callback;
    var _sp_calltransfer_callback;
    var _sp_error_callback;
		var _sp_getagentinfo_callback;
		var _sp_subscribe_evt_callback;
		var _sp_buttonstate_evt_callback;
		var _sp_tranmessage_evt_callback;
		
		this.sp_Reconnect = function()
		{
			sp_ws = new WebSocket("ws://127.0.0.1:18080");
      this.sp_ws.onmessage = function(evt) 
      {    
          _ws_onmessage(evt);
      };
         
      this.sp_ws.onclose = function(evt) 
      {    
      	 _ws_onclose(evt);
      };   
         
      this.sp_ws.onerror = function(evt)
      {   
      	_ws_onerror(evt);
      };
         
      this.sp_ws.onopen = function(evt) 
      {
      	_ws_onopened(evt);
      };    
		}
		
//WebSocket已经关闭
		this.sp_OnClose = function(evt)
		{
	    alert("连接已关闭。");
	    sp_ws.close();   
	    sp_ws = null;   
			clearInteval(sp_heartbeattimer);
			setTimeout(function()
			{
         sp_Reconnect();
    	},1000);
		}

//WebSocket连接出现错误
		this.sp_OnError = function(evt)
		{
	    alert("连接出错:"+evt.data);   
	    sp_ws = null;   
			clearInteval(sp_heartbeattimer);
		}

//WebSocket连接成功
		this.sp_OnConnected = function(evt)
		{
			alert("连接已打开。");   
			this.b_spwsconnectedflag = 1;
		}
		
//接受到消息进行解析			 
		this.sp_ParserJSON = function(evt)
		{
			var obj = JSON.parse(evt.data);
			if( obj.MSGTYPE == "CMDRET")
			{
				if(obj.CMD == "AUTOLOGIN" && _sp_autologin_callback != null)
					_sp_autologin_callback(obj.RETVALUE);
				else if(obj.CMD == "SPINIT" && _sp_init_callback != null)
					_sp_init_callback(obj.RETVALUE);
				else if(obj.CMD == "SPUNINIT" && _sp_uninit_callback != null)
					_sp_uninit_callback(obj.RETVALUE);
				else if(obj.CMD == "SETLOGINAGTSTATE" && _sp_setloginstate_callback != null)
					_sp_setloginstate_callback(obj.RETVALUE);
				else if(obj.CMD == "SETIVRNO" && _sp_setivrno_callback != null)
					_sp_setivrno_callback(obj.RETVALUE);
				else if(obj.CMD == "AGENTLOGIN" && _sp_agentlogin_callback != null)
					_sp_agentlogin_callback(obj.RETVALUE);
				else if(obj.CMD == "AGENTLOGOUT" && _sp_agentlogout_callback != null)
					_sp_agentlogout_callback(obj.RETVALUE);
				else if(obj.CMD == "AGENTREADY" && _sp_agentready_callback != null)
					_sp_agentready_callback(obj.RETVALUE);
				else if(obj.CMD == "AGENTACW" && _sp_agentacw_callback != null)
					_sp_agentacw_callback(obj.RETVALUE);
				else if(obj.CMD == "AGENTNOTREADY" && _sp_agentnotready_callback != null)
					_sp_agentnotready_callback(obj.RETVALUE);
				else if(obj.CMD == "ANSWERCALL" && _sp_answercall_callback != null)
					_sp_answercall_callback(obj.RETVALUE);
				else if(obj.CMD == "MAKECALL" && _sp_makecall_callback != null)
					_sp_makecall_callback(obj.RETVALUE);
				else if(obj.CMD == "CONSULTCALL" && _sp_consultcall_callback != null)
					_sp_consultcall_callback(obj.RETVALUE);
				else if(obj.CMD == "TRNASFERCALL" && _sp_transfercall_callback != null)
					_sp_transfercall_callback(obj.RETVALUE);
				else if(obj.CMD == "TRNASFERTOIVR" && _sp_transfertoivr_callback != null)
					_sp_transfertoivr_callback(obj.RETVALUE);
				else if(obj.CMD == "CONFERENCECALL" && _sp_conference_callback != null)
					_sp_conference_callback(obj.RETVALUE);
				else if(obj.CMD == "HOLDCALL" && _sp_holdcall_callback != null)
					_sp_holdcall_callback(obj.RETVALUE);
				else if(obj.CMD == "RECONNECTCALL" && _sp_reconnectcall_callback != null)
					_sp_reconnectcall_callback(obj.RETVALUE);
				else if(obj.CMD == "HANGUPCALL" && _sp_hangupcall_callback != null)
					_sp_hangupcall_callback(obj.RETVALUE);
				else if(obj.CMD == "SENDDTMF" && _sp_senddtmf_callback != null)
					_sp_senddtmf_callback(obj.RETVALUE);
				else if(obj.CMD == "SETCUSTOMERCALLINFO" && _sp_rt_setcustomercallinfo_callback != null)
					_sp_rt_setcustomercallinfo_callback(obj.RETVALUE);
				else if(obj.CMD == "TRANSFERMESSAGE" && _sp_rt_transfermessage_callback != null)
					_sp_rt_transfermessage_callback(obj.RETVALUE);
				else if(obj.CMD == "GETCUSTOMERCALLINFO" && _sp_rt_getcustomercallinfo_callback != null)
				{
					if(obj.RETVALUE == "1")
						_sp_rt_getcustomercallinfo_callback(obj.RETVALUE,obj.DATA);
					else
						_sp_rt_getcustomercallinfo_callback(obj.RETVALUE,"");
				}
				else if(obj.CMD == "SUBSCRIBESTART" && _sp_rt_startsubscribe_callback != null)
				{
					if(obj.RETVALUE == "1")
						_sp_rt_startsubscribe_callback(obj.RETVALUE,obj.SUBSCRIBEID);
					else
						_sp_rt_startsubscribe_callback(obj.RETVALUE,"");
				}
				else if(obj.CMD == "SETCUSTOMERCALLINFO" && _sp_rt_setcustomercallinfo_callback != null)
					_sp_rt_setcustomercallinfo_callback(obj.RETVALUE);
					
				else if(obj.CMD == "SUBSCRIBESTOP" && _sp_rt_cancelsubscribe_callback != null)
					_sp_rt_cancelsubscribe_callback(obj.RETVALUE);
				else if(obj.CMD == "QUERYDATA" && _sp_rt_queryrt_callback != null)
				{
					if(obj.RETVALUE == "1")
						_sp_rt_queryrt_callback(obj.RETVALUE,obj.DATA);
					else
						_sp_rt_queryrt_callback(obj.RETVALUE,"");
				}
				else if(obj.CMD == "GETAGENTINFO" && _sp_getagentinfo_callback != null)
				{
					if(obj.RETVALUE == "1")
						_sp_getagentinfo_callback(obj.RETVALUE,obj.AGENTID,obj.AGENTNAME,obj.STATIONID);
					else
						_sp_getagentinfo_callback(obj.RETVALUE,"","","");
				}
				else if(obj.CMD == "READCFG" && _sp_getcfg_callback != null)
				{
					if(obj.RETVALUE == "1")
						_sp_getcfg_callback(obj.RETVALUE,obj.KEYVALUE);
					else
						_sp_getcfg_callback(obj.RETVALUE,"");
				}
				else if(obj.CMD == "SETCFG" && _sp_setcfg_callback != null)
					_sp_setcfg_callback(obj.RETVALUE);
			}
			else if(obj.MSGTYPE == "EVENT")
			{
				if(obj.EVENT == "AGENTSTATECHANGE" && _sp_agentstate_callback != null)
					_sp_agentstate_callback(obj.AGENTID,obj.STATEID,obj.STATENAME,obj.SUBSTATEID,obj.SUBSTATENAME,obj.OLDSTATEID,obj.OLDSTATENAME,obj.OLDSUBSTATEID,obj.OLDSUBSTATENAME);
				else if(obj.EVENT == "STATIONSTATECHANGE" && _sp_stationstate_callback != null)
					_sp_stationstate_callback(obj.OTHERPARTY,obj.STATEID,obj.STATENAME,obj.OLDSTATEID,obj.OLDSTATENAME,obj.UUI);
				else if(obj.EVENT == "INBANDCALL" && _sp_inboundcall_callback != null)
					_sp_inboundcall_callback(obj.AGENTID,obj.CUSTOMERCALLID,obj.CALLID,obj.CUSTOMERNO,obj.SERVICENO,obj.QUEUE,obj.TRUNKNO);
				else if(obj.EVENT == "CALLINFOCHANGE" && _sp_callinfochange_callback != null)
					_sp_callinfochange_callback(obj.AGENTID,obj.CUSTOMERCALLID,obj.CALLID,obj.CUSTOMERNO,obj.SERVICENO);
				else if(obj.EVENT == "CALLTRANSFER" && _sp_calltransfer_callback != null)
					_sp_calltransfer_callback(obj.AGENTID,obj.CUSTOMERCALLID,obj.CUSTOMERNO,obj.SERVICENO,obj.OTHERPARTY,obj.THIRDPARTY,obj.CALLEDPARTY);
				else if(obj.EVENT == "ERROR" && _sp_error_callback != null)
					_sp_error_callback(obj.AGENTID,obj.ERRORCODE);
				else if(obj.EVENT == "SUBSCRIBE" && _sp_subscribe_evt_callback != null)
					_sp_subscribe_evt_callback(obj.SUBSCRIBEID,obj.DATA);
				else if(obj.EVENT == "BUTTONINVISBLECHANGE" && _sp_buttonstate_evt_callback != null)
					_sp_buttonstate_evt_callback(obj.AGENTID,obj.AGTSTATEBUTTONSFLAG,obj.NOTREADYSUBSTATEBUTTONSFLAG,obj.STATIONBUTTONSFLAG);
				else if(obj.EVENT == "TRANSFERMESSAGE" && _sp_tranmessage_evt_callback != null)
					_sp_tranmessage_evt_callback(obj.MESSAGEDATA);
			}
			else if(obj.MSGTYPE == "HEARTBEAT")
			{
				this.sp_HeartbeatRet(obj.INVOKEID);
			}
			else if(obj.MSGTYPE == "HEARTBEATRET")
			{
			}
			else			
			  alert("未知的消息类型="+obj.MSGTYPE);
		}

		
		
//发送心跳消息
		this.sp_Heartbeat = function()
		{
			//return;
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"HEARTBEAT\",\"INVOKEID\":\""+invokeid+"\"}";   
	        this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	        alert("发送心跳消息时,发现连接失效。");   
	    }
		}
		
//心跳消息回应
		this.sp_HeartbeatRet = function(invokeid)
		{
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"HEARTBEATRET\",\"INVOKEID\":\""+invokeid+"\"}";   
	        this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	        alert("回应心跳消息时,检测到连接失效。");   
	    }   
		}
				 

//通过WebSocket发送消息
		this.fSendWSMsg = function(strJSON)
		{   
		    if(this.sp_ws != null)
		    {   
		        if(strJSON == null || strJSON == "")
		        {   
		            return 0;
		        } 
		        
		     //alert("发送消息:"+strJSON);   
		        this.sp_ws.send(strJSON);
		        return 1;
		    }
		    else
		    {   
		    		return 0;
		    }   
		}   
		
//进行WebSocket初始化		
		this.f_initws = function()
		{
//判断是否支持WebSocket	 
			if (!window.WebSocket) 
			{
			    alert("WebSocket not supported by this browser!");    
			    return 0;
			}
		
//进行WebSocket连接初始化		
			_ws_onopened = this.sp_OnConnected;
			_ws_onerror = this.sp_OnError;
			_ws_onclose = this.sp_OnClose;
			_ws_onmessage = this.sp_ParserJSON;
			
	    if(this.sp_ws == null)
	    {   
	        this.sp_ws = new WebSocket("ws://127.0.0.1:18080");    
	        this.sp_ws.onmessage = function(evt) 
	        {    
	            _ws_onmessage(evt);
	        };
	           
	        this.sp_ws.onclose = function(evt) 
	        {    
	        	 _ws_onclose(evt);
	        };   
	           
	        this.sp_ws.onerror = function(evt)
	        {   
	        	_ws_onerror(evt);
	        };
	           
	        this.sp_ws.onopen = function(evt) 
	        {
	        	_ws_onopened(evt);
	        };    
	        
	        _heartbeat = function(){this.sp_Heartbeat();};
	        sp_heartbeattimer = setInterval(this.sp_Heartbeat.bind(this),5000);
	        
	       _self = this; // 把自己的thi指针保存起来
	        
	    }   
		}
		
//发送SPINIT消息
   this.f_sendspinit = function()
   {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SPINIT\",\"INVOKEID\":\""+invokeid+"\",\"AGENTID\":\"" + this._strAgentID+"\",\"ACDTYPE\":\""+this._iAcdType+"\"}";   
	        var iRetCode;
	        iRetCode =  this.fSendWSMsg(sendstr);   
		       return iRetCode;
	    }
	    else
	    {   
	    		return 0;
	    }   
   }		

//软电话初始化	 
	 this.f_spinit = function()
	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SPINIT\",\"INVOKEID\":\""+invokeid+"\",\"AGENTID\":\"" + this._strAgentID+"\",\"ACDTYPE\":\""+this._iAcdType+"\"}";   
	        var iRetCode;
	        iRetCode =  this.fSendWSMsg(sendstr);   
	       return iRetCode;
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//软电话直接登录
	 this.f_sp_direct_login = function(iLoginState,strIvrNo,pcallback)
	 {
	 	  _sp_autologin_callback = pcallback;
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"AUTOLOGIN\",\"INVOKEID\":\""+invokeid+"\",\"AGENTID\":\"" + this._strAgentID+"\",\"ACDTYPE\":\""+this._iAcdType+"\",\"LOGINSTATE\":\""+iLoginState+"\",\"IVRNO\":\""+strIvrNo+"\"}";   
	        var iRetCode;
	        iRetCode =  this.fSendWSMsg(sendstr);   
	       return iRetCode;
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//软电话销毁
	 this.f_spuninit = function()
	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SPUNINIT\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//设置登录后状态
	 this.f_spsetloginstate = function(iAgentState)
	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SETLOGINAGTSTATE\",\"INVOKEID\":\""+invokeid+"\",\"AGENTSTATE\":\""+iAgentState+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//设置IVR号码
	 this.f_spsetivrno = function(strIvrNO)
	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SETIVRNO\",\"INVOKEID\":\""+invokeid+"\",\"IVRNO\":\""+strIvrNO+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//座席登录
	 this.f_sp_agentlogin = function()
	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"AGENTLOGIN\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//座席签退
	 this.f_sp_agentlogout = function()
	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"AGENTLOGOUT\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//座席就绪
	 this.f_sp_agentready = function()
	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"AGENTREADY\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//座席事后处理
	 this.f_sp_agentacw = function()
	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"AGENTACW\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//座席未就绪的某个子状态
	 this.f_sp_agentnotready = function(iSubState)
	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"AGENTNOTREADY\",\"INVOKEID\":\""+invokeid+"\",\"SUBSTATE\":\""+iSubState+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
	 }

//软电话话路部分

//应答呼叫
 	 this.f_sp_answercall = function()
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"ANSWERCALL\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }

//外拨
 	 this.f_sp_makecall = function(strCalled,strCaller)
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"MAKECALL\",\"INVOKEID\":\""+invokeid+"\",\"CALLEDPARTY\":\""+strCalled+"\",\"CALLERPARTY\":\""+strCaller+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }

//咨询
 	 this.f_sp_consultcall = function(strCalled)
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"CONSULTCALL\",\"INVOKEID\":\""+invokeid+"\",\"CONSULTPARTY\":\""+strCalled+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }
 	 

//转接
 	 this.f_sp_transfercall = function()
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"TRNASFERCALL\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }
 	 
//转接IVR
 	 this.f_sp_transfertoivr = function()
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"TRNASFERTOIVR\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }


//会议
 	 this.f_sp_conference = function()
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"CONFERENCECALL\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }

//保持
 	 this.f_sp_holdcall = function()
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"HOLDCALL\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }

//接回
 	 this.f_sp_reconnectcall = function()
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"RECONNECTCALL\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }

//挂机
 	 this.f_sp_hangupcall = function()
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"HANGUPCALL\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }

//发送DTMF
 	 this.f_sp_senddtmf = function(strDTMF)
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SENDDTMF\",\"INVOKEID\":\""+invokeid+"\",\"DTMF\":\""+strDTMF+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }
 	 
//软电话命令结果回调函数
	 this.f_sp_SetCommandCallback = function(strcmd,pcallback)
	 {
	    if(strcmd.toLowerCase() == "f_spinit")
	    	_sp_init_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_spuninit")
	    	_sp_uninit_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_spsetloginstate")
	    	_sp_setloginstate_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_spsetivrno")
	    	_sp_setivrno_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_agentlogin")
	    	_sp_agentlogin_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_agentlogout")
	    	_sp_agentlogout_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_agentready")
	    	_sp_agentready_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_agentacw")
	    	_sp_agentacw_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_agentnotready")
	    	_sp_agentnotready_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_answercall")
	    	_sp_answercall_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_makecall")
	    	_sp_makecall_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_consultcall")
	    	_sp_consultcall_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_transfercall")
	    	_sp_transfercall_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_transfertoivr")
	    	_sp_transfertoivr_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_conference")
	    	_sp_conference_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_holdcall")
	    	_sp_holdcall_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_reconnectcall")
	    	_sp_reconnectcall_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_hangupcall")
	    	_sp_hangupcall_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_senddtmf")
	    	_sp_senddtmf_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_setcustomercallinfo")
	    	_sp_rt_setcustomercallinfo_callback = pcallback;
	    else if(strcmd.toLowerCase() == "f_sp_cancelsubscribe")
	    	_sp_rt_cancelsubscribe_callback = pcallback;
	 }

//软电话座席状态变化回调函数
	 this.f_sp_SetAgentStateEventCallback = function(pcallback)
	 {
			_sp_agentstate_callback = pcallback;
		} 
		
//软电话分机状态变化回调函数
	this.f_sp_SetStationStateEventCallback = function(pcallback)
	{
		_sp_stationstate_callback = pcallback;
	} 
	
//软电话来电事件回调函数
	 this.f_sp_SetInboundCallEventCallback = function(pcallback)
	 {
			_sp_inboundcall_callback = pcallback;
		} 
		
//软电话呼叫变化事件回调函数
	 this.f_sp_SetCallInfoChangeEventCallback = function(pcallback)
	 {
			_sp_callinfochange_callback = pcallback;
		} 

//软电话呼叫转移事件回调函数
	 this.f_sp_SetCallTransferEventCallback = function(pcallback)
	 {
			_sp_calltransfer_callback = pcallback;
		} 
		
//软电话错误事件回调函数
	 this.f_sp_SetErrorEventCallback = function(pcallback)
	 {
			_sp_error_callback = pcallback;
		} 

//软电话按钮状态变化事件的 回调函数
	 this.f_sp_SetButtonStateChangedEventCallback = function(pcallback)
	 {
			_sp_buttonstate_evt_callback = pcallback;
		} 
		
		
//软电话订阅事件到达回调函数
	 this.f_sp_SetSubScribeEventCallback = function(pcallback)
	 {
			_sp_subscribe_evt_callback = pcallback;
		} 

//软电话收到转发消息的回调函数
	 this.f_sp_SetTranferMessageEventCallback = function(pcallback)
	 {
			_sp_tranmessage_evt_callback = pcallback;
		} 


//软电话查询座席信息
	 this.f_sp_getagentinfo = function(pcallback)
	 {
			_sp_getagentinfo_callback = pcallback;
			if(pcallback == null)
				return 0;

		  var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"GETAGENTINFO\",\"INVOKEID\":\""+invokeid+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
	} 
	 
//实时数据操作类
	//设置customercall数据
 	 this.f_sp_setcustomercallinfo = function(strCustomerCallID,strFieldList)
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SETCUSTOMERCALLINFO\",\"INVOKEID\":\""+invokeid+"\",\"CUSTOMERCALLID\":\""+strCustomerCallID+"\",\"VALUELIST\":\""+ strFieldList+"\"}";   
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }

	//获取customercall数据
 	 this.f_sp_getcustomercallinfo = function(strCustomerCallID,strFieldList,pCallBack)
 	 {
 	 	  if(pCallBack == null)
 	 	  	return 0;
	
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"GETCUSTOMERCALLINFO\",\"INVOKEID\":\""+invokeid+"\",\"CUSTOMERCALLID\":\""+strCustomerCallID+"\",\"FIELDLIST\":\""+ strFieldList+"\"}";  
	        _sp_rt_getcustomercallinfo_callback =  pCallBack;
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }

	//开始订阅数据
 	 this.f_sp_startsubscribe = function(strTableName,strKeyValue,strTimeSlice,strSliceDuration,strScribeFieldList,pSubScribeCallBack)
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SUBSCRIBESTART\",\"INVOKEID\":\""+invokeid+"\",\"TABLENAME\":\""+ strTableName+"\",\"FILTER\":\""+ strKeyValue +"\",\"TIMESLICE\":\""+strTimeSlice+"\",\"SLICEDURATION\":\""+strSliceDuration+"\",\"FIELDLIST\":\""+ strScribeFieldList+"\"}";  
	        _sp_rt_startsubscribe_callback =  pSubScribeCallBack;
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }
	 
	//取消订阅数据
 	 this.f_sp_cancelsubscribe = function(strSubScribeID)
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SUBSCRIBESTOP\",\"INVOKEID\":\""+invokeid+"\",\"SUBSCRIBEID\":\""+ strSubScribeID+"\"}";  
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }
	
	
	//查询实时内存数据库表数据
 	 this.f_sp_queryrt = function(strTableName,strFieldList,strWhereList, pQueryCallBack)
 	 {
 	 	  if(pQueryCallBack == null)
 	 	  	return 0;
	
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"QUERYDATA\",\"INVOKEID\":\""+invokeid+"\",\"TABLENAME\":\""+ strTableName+"\",\"FIELDLIST\":\""+ strFieldList +"\",\"WHERELIST\":\""+ strWhereList +"\"}";  
	        _sp_rt_queryrt_callback =  pQueryCallBack;
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }
 	 
	//转发TS消息
 	 this.f_sp_transfermessage = function(strDestClientID,strMessageData,pCallBack)
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"TRANSFERMESSAGE\",\"INVOKEID\":\""+invokeid+"\",\"DESTCLIENTNAME\":\""+strDestClientID+"\",\"TRANMESSAGE\":\""+ strMessageData+"\"}";  
	        _sp_rt_transfermessage_callback =  pCallBack;
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }
 //读取配置信息
 	 this.f_sp_getcfginfo = function(strSecName,strKeyName,pCallBack)
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"READCFG\",\"INVOKEID\":\""+invokeid+"\",\"SECNAME\":\""+strSecName+"\",\"KEYNAME\":\""+ strKeyName+"\"}";  
	        _sp_getcfg_callback =  pCallBack;
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }
 
 //设置配置信息
 	 this.f_sp_setcfginfo = function(strSecName,strKeyName,strKeyValue,pCallBack)
 	 {
	   	var invokeid = new Date().getTime();
	    if(this.sp_ws != null)
	    {   
	        var sendstr = "{\"MSGTYPE\":\"CMD\",\"CMD\":\"SETCFG\",\"INVOKEID\":\""+invokeid+"\",\"SECNAME\":\""+strSecName+"\",\"KEYNAME\":\""+ strKeyName+"\",\"KEYVALUE\":\""+strKeyValue+"\"}";  
	        _sp_setcfg_callback =  pCallBack;
	        return this.fSendWSMsg(sendstr);   
	    }
	    else
	    {   
	    		return 0;
	    }   
 	 }
 
}