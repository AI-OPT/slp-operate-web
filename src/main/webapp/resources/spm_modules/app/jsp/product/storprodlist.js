define('app/jsp/product/storprodlist', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
	    Widget = require('arale-widget/1.2.0/widget'),
	    Dialog = require("optDialog/src/dialog"),
	    Paging = require('paging/0.0.1/paging-debug'),
	    AjaxController = require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var StorprodlistPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 30
    	},
    	//事件代理
    	events: {
    		//查询未编辑商品
            "click #searchStayUpProd":"_selectStayUpProd",
        },
    	//重写父类
    	setup: function () {
    		StorprodlistPager.superclass.setup.call(this);
    		this._selectStayUpProd();
    	},
    	// 改变商品类目
    	_selectChange:function(osel){
    		var prodCatId = osel.options[osel.selectedIndex].value;
    		var clickId = $(osel).parent().attr('id');
    		//获取当前ID的最后数字
    		var index = Number(clickId.substring(10))+1;
    		//获取下拉菜单的总个数
    		var div = document.getElementById("date1");
    		var length = div.getElementsByTagName("select").length;
    		if(index==length){
    			return;
    		}
    		//从当前元素开始移除后面的下拉菜单
    		for(var i=index;i<length;i++){
    			$("#productCat"+i).remove();
    		}
    		ajaxController.ajax({
				type: "post",
				processing: false,
				// message: "加载中，请等待...",
				url: _base+"/prodquery/getCat",
				data:{"prodCatId":prodCatId},
				success: function(data){
					if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#prodCatTemple");
	            	    var htmlOutput = template.render(data);
	            	    $("#"+clickId).append(htmlOutput);
	            	}else{
	            		var d = Dialog({
							content:"获取类目信息出错:"+data.statusInfo,
							icon:'fail',
							okValue: '确 定',
							ok:function(){
								this.close();
							}
						});
						d.show();
	            	}
				}
			});
    	},
    	//查询待上架商品-点击查询触发
    	_selectStayUpProd:function(){
    		var _this = this;
    		//获取下拉菜单的总个数
    		var div = document.getElementById("date1");
    		var length = div.getElementsByTagName("select").length;
    		var i = length-1;
//    		var productCatId;
//    		for(var i=0;i<length;i++){
    		var	productCatId = $("#productCat"+i+" option:selected").val();
//    		}
    		var productType = $("#productType").val().trim();
    		var productId = $("#productId").val().trim();
    		var productName = $("#productName").val().trim();
    		$("#stayup-pagination-ul").runnerPagination({
	 			url: _base+"/prodquery/getStayUpList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data: {"productCatId":productCatId,"productType":productType,"productId":productId,"productName":productName},
	           	pageSize: StorprodlistPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#selectStayUpProdTemple");
	            	    var htmlOutput = template.render(data);
	            	    $("#selectStayUpProdData").html(htmlOutput);
	            	}else{
    					$("#selectStayUpProdData").html("没有搜索到相关信息");
	            	}
	            	_this._returnTop();
	            }
    		});
    	},
    	//滚动到顶部
    	_returnTop:function(){
    		var container = $('.wrapper-right');
    		container.scrollTop(0);//滚动到div 顶部
    	},
    	
    });
    
    module.exports = StorprodlistPager
});

