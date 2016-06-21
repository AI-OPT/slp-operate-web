define('app/jsp/product/edit', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
		Events = require('arale-events/1.2.0/events'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    AjaxController = require('opt-ajax/1.0.0/index');
	require("ckeditor/ckeditor.js")
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
	var prodDetail = 'prodDetail';
	var editDom;
	//当前操作受众类型
	var nowAudiType;

    //定义页面组件类
    var ProdEditPager = Widget.extend({
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
			AUDI_ENT_TYPE: "ent",
			AUDI_AGENT_TYPE: "agent"
    	},
    	//事件代理
    	events: {
			"click input:checkbox[name='targetProv']":"_showTarget",
			"click input:radio[name='audiencesEnterprise']":"_showAudi",
			"click input:radio[name='audiencesAgents']":"_showAudi",
			"click #finishTarget":"_finishTarget",
			//保存数据
			"click #save":"_saveProd"
        },
    	//重写父类
    	setup: function () {
			ProdEditPager.superclass.setup.call(this);
			editDom = CKEDITOR.replace(prodDetail);
			this._showPartTarget();
			this._showTarget();
			this._changeAudiEnt();
			this._changeAudiAgent();
		},
		//显示受众用户选择窗口
		_showAudiSelect:function(audiType){
			console.log("show audi type:"+audiType);
			nowAudiType = audiType;
			var audiMap;
			var typeName;
			//企业
			if (ProdEditPager.AUDI_ENT_TYPE==audiType){
				audiMap = audiEntObjs;
				typeName = "企业";
			}//代理商
			else if(ProdEditPager.AUDI_AGENT_TYPE==audiType){
				audiMap = audiAgentObjs;
				typeName = "代理商";
			}else
				return;
			var ind = 0;
			for (var key in audiMap) {
				$('#audiSelectedDiv').append("<p>"+audiMap[key]+"<a href=\"#\"><i class=\"icon-remove-sign\" userId='"+key+"'></i></a></p>");
				ind ++;
			}
			$("#audiType").text(typeName);
			$('#audiNum').text(ind);
			$('.eject-mask').fadeIn(100);
			$('.eject-large').slideDown(200);
		},
		//删除受众用户
		_delAudi:function (userId){
			console.log("del audi userId:"+userId);
			var audNum;
			//企业
			if (ProdEditPager.AUDI_ENT_TYPE==nowAudiType){
				delete(audiEntObjs[userId]);
				audNum = audiEntObjs.valueOf().length;
			}//代理商
			else if(ProdEditPager.AUDI_AGENT_TYPE==nowAudiType){
				delete(audiAgentObjs[userId]);
				audNum = audiAgentObjs.valueOf().length;
			}
			$('#audiNum').text(ind);
		},
		_showAudi:function(){
			var partTarget = $("input:radio[name='audiencesEnterprise']:checked").val();
			if ('1' == partTarget){
				$('#entAudiDiv').show();
			}else {
				$('#entAudiDiv').hide();
				$('#entAudiDivMore').hide();
			}
			var audiAgent = $("input:radio[name='audiencesAgents']:checked").val();
			if ('1' == audiAgent){
				$('#agentAudiDiv').show();
			}else {
				$('#agentAudiDiv').hide();
				$('#agentAudiDivMore').hide();
			}
		},
		//对企业受众进行处理
		_changeAudiEnt:function(){
			//获取audiEntObjs
			var ind = 0;
			var audiId = [];
			for (var key in audiEntObjs) {
				audiId.push(key);
				if (ind < 20)
					$('#entAudiDiv').append("<p>"+audiEntObjs[key]+"、</p>");
				else
					$('#entAudiDivMore').append("<p>"+audiEntObjs[key]+"、</p>");
				ind ++;
			}
			$('#entAudiDiv').prepend("<p class=\"width-xlag\">已选中"+audiId.length+"个<a href=\"#\" class=\"modify\" audi=\""+ProdEditPager.AUDI_ENT_TYPE+"\" >修改</a></p>");
			$('#audiEntIds').val(JSON.stringify(audiId));
			if(audiId.length>20){
				$('#entAudiDiv').append("<p><a href=\"javascript:void(0)\" class=\"zk\">显示更多<i class=\"icon-angle-down\"></i></a></p>");
			}
		},
		//对代理商受众进行处理
		_changeAudiAgent:function(){
			//获取audiEntObjs
			var ind = 0;
			var audiId = [];
			for (var key in audiAgentObjs) {
				audiId.push(key);
				if (ind < 20)
					$('#agentAudiDiv').append("<p>"+audiAgentObjs[key]+"、</p>");
				else
					$('#agentAudiDivMore').append("<p>"+audiAgentObjs[key]+"、</p>");
				ind ++;
			}
			$('#agentAudiDiv').prepend("<p class=\"width-xlag\">已选中"+audiId.length+"个<a href=\"#\" class=\"modify\" audi=\""+ProdEditPager.AUDI_AGENT_TYPE+"\">修改</a></p>");
			$('#audiAgentIds').val(JSON.stringify(audiId));
			if(audiId.length>20){
				$('#agentAudiDiv').append("<p><a href=\"javascript:\" class=\"zk\">显示更多<i class=\"icon-angle-down\"></i></a></p>");
			}
		},
		//完成目标地域选择
		_finishTarget:function(){
			$('.eject-mask').fadeOut(100);
			$('.eject-large2').slideUp(150);
		},
		//显示目标地域的信息
		_showPartTarget:function(){
			var partTarget = $("input[name='isSaleNationwide']:checked").val();
			if ('N' == partTarget){
				$('#check4').show();
			}
		},
		//改变目标地域
		_showTarget:function(){
			//选中省份的名称字符串
			var checkProvStr = new Array();
			var checkNum = 0;
			var provArry = [];
			//获取所有已选中省份
			$('input:checkbox[name=targetProv]:checked').each(function(i){
				checkNum ++;
				provArry.push(Number($(this).val()));
				checkProvStr.push($(this).attr("title"));
			});
			$('#dialogAreaNum').html(checkNum);
			$('#areaNum').text('已选中省份'+checkNum+'个');
			$('#areaName').text(checkProvStr.join("、"));
			if (provArry.length>0){
				console.log(JSON.stringify(provArry));
				$('#targetProd').val(JSON.stringify(provArry));
			}else
				$('#targetProd').val('');
		},
    	//保存商品信息
      	_saveProd:function() {
			//获取editor中内容
			$("#detailConVal").val(editDom.getData());
			console.log($('#detailConVal').val());
			//如果点击的是删除
			ajaxController.ajax({
				type: "post",
				processing: false,
				// message: "删除中，请等待...",
				url: _base+"/prodedit/save",
				data:$('#prodForm').serializeArray(),
				success: function(data){
					if("0"===data.statusCode){
						new Dialog({
							content:"保存成功",
							ok:function(){
								this.close();
							}
						}).show();
						//保存成功,跳转到列表页面
						//window.location.href = _base+"/prodquery/add";
					}
				}
			});
		}
    });
    
    module.exports = ProdEditPager
});

