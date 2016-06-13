define('app/jsp/search/search', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
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
    var SearchPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//查询
    		   "click #BTN_SEARCH":"_searchBtnClick"
        },
    	//重写父类
    	setup: function () {
    		SearchPager.superclass.setup.call(this);
    	},
    	_searchBtnClick: function(){
    		var skuname = $("#serachName").val();
    		var area = "11";
    		//用于判断跳转页面是从哪里跳转的
    		var flag = "00";
    		window.location.href = _base + '/search/list?skuName='+encodeURI(encodeURI(skuname))+"&sourceFlag="+flag;
    	},
    	
    });
    
    module.exports = SearchPager
});

