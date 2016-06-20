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

    //定义页面组件类
    var ProdEditPager = Widget.extend({
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
			"click input:checkbox[name='targetProv']":"_showTarget",
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

		},
		_finishTarget:function(){
			$('.eject-mask').fadeOut(100);
			$('.eject-large2').slideUp(150);
		},
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

