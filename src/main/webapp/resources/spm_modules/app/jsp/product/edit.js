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
	//查询受众用户类型
	var selectUserType;

    //定义页面组件类
    var ProdEditPager = Widget.extend({
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
			DEFAULT_PAGE_SIZE: 30,
			AUDI_ENT_TYPE: "ent",
			AUDI_AGENT_TYPE: "agent",
			USER_ENT_TYPE: "11",
			USER_AGENT_TYPE: "13",
			FILE_MAX_SIZE:3,
			FILE_TYPES:['.jpg','.png']
    	},
    	//事件代理
    	events: {
			"click input:checkbox[name='targetProv']":"_showTarget",
			"click input:radio[name='audiencesEnterprise']":"_showAudi",
			"click input:radio[name='audiencesAgents']":"_showAudi",
			"click #finishTarget":"_finishTarget",
			"click #searchBut":"_searchBtnClick",
			"change #uploadFile":"_uploadFile",
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
		//刷新受众信息
		_flushAudiInfo:function(){
			//企业
			if (ProdEditPager.AUDI_ENT_TYPE==nowAudiType){
				this._changeAudiEnt();
			}//代理商
			else if(ProdEditPager.AUDI_AGENT_TYPE==nowAudiType){
				this._changeAudiAgent();
			}
			//清除已有搜索
			$('#userList').text("请输入用户名进行搜索");
			$('#selectName').val('');
			$('#pagination-ul').empty();
			$('.eject-mask').fadeOut(100);
			$('.eject-large').slideUp(150);
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
				selectUserType = ProdEditPager.USER_ENT_TYPE;
			}//代理商
			else if(ProdEditPager.AUDI_AGENT_TYPE==audiType){
				audiMap = audiAgentObjs;
				typeName = "代理商";
				selectUserType = ProdEditPager.USER_AGENT_TYPE;
			}else
				return;
			var ind = 0;
			for (var key in audiMap) {
				$('#audiSelectedDiv').append("<p>"+audiMap[key]+"<a href=\"#\"><i class=\"icon-remove-sign\" userId='"+key+"'></i></a></p>");
				ind ++;
			}
			$("#audiType").text(typeName);
			$("#selectType").text(typeName);
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
				audNum = Object.keys(audiEntObjs).length;
			}//代理商
			else if(ProdEditPager.AUDI_AGENT_TYPE==nowAudiType){
				delete(audiAgentObjs[userId]);
				audNum = Object.keys(audiAgentObjs).length;
			}else {
				return;
			}
			$('#audiNum').text(audNum);
		},
		//添加受众用户
		_addAudi:function(userId,userName){
			var audNum;
			//企业
			if (ProdEditPager.AUDI_ENT_TYPE==nowAudiType){
				audiEntObjs[userId] = userName;
				audNum = Object.keys(audiEntObjs).length;
			}//代理商
			else if(ProdEditPager.AUDI_AGENT_TYPE==nowAudiType){
				audiAgentObjs[userId]=userName;
				audNum = Object.keys(audiAgentObjs).length;
			}else{
				return;
			}
			$('#audiNum').text(audNum);
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
			$('#entAudiDiv').empty();
			$('#entAudiDivMore').empty();
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
			$('#agentAudiDiv').empty();
			$('#agentAudiDivMore').empty();
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
			var _this = this;
			//获取editor中内容
			$("#detailConVal").val(editDom.getData());
			console.log($('#detailConVal').val());
			this._convertProdPic();
			//如果点击的是删除
			ajaxController.ajax({
				type: "post",
				processing: false,
				// message: "删除中，请等待...",
				url: _base+"/prodedit/save",
				data:$('#prodForm').serializeArray(),
				success: function(data){
					if("0"===data.statusCode){
						_this._showMsg("保存成功");
						//保存成功,跳转到列表页面
						//window.location.href = _base+"/prodquery/add";
					}
				}
			});
		},
		//将图片信息转换为json字符串
		_convertProdPic:function(){
			var prodPic = [];
			var prodAttrPic = [];
			//获取属性值图
			$(".width-img img[imgId!='']").each(function(i){
				var attrVal = $(this).attr('attrVal');
				//设置该图片的信息
				console.log("已设置图片:"+$(this).attr('attrVal')+",序号:"+$(this).attr('picInd'));
				console.log("====图片信息:"+$(this).attr('imgId')+",");
				//主图图片
				if (attrVal=='0'){
					var pic = {'attrvalueDefId':'0','vfsId':$(this).attr('imgId'),'picType':$(this).attr('imgType'),'serialNumber':$(this).attr('picInd')};
					prodPic.push(pic);
				}else {
					var pic = {'attrvalueDefId':$(this).attr('attrVal'),'vfsId':$(this).attr('imgId'),'picType':$(this).attr('imgType'),'serialNumber':$(this).attr('picInd')};
					prodAttrPic.push(pic);
				}
			});
			$('#prodPicStr').val(JSON.stringify(prodPic));
			$('#prodAttrValPicStr').val(JSON.stringify(prodAttrPic));
		},
		//查询用户
		_searchBtnClick: function() {
			var _this = this;
			var selectName = $("#selectName").val();
			if (selectName == null || '' == selectName) {
				this._showMsg("请输入要查询用户名");
				return;
			}
			$("#pagination-ul").runnerPagination({
				url: _base + "/home/queryuser",
				method: "POST",
				dataType: "json",
				processing: true,
				data: {"userName":selectName,"userType":selectUserType},
				pageSize: ProdEditPager.DEFAULT_PAGE_SIZE,
				visiblePages: 5,
				message: "正在为您查询数据..",
				render: function (data) {
					if (data != null && data != 'undefined' && data.length > 0) {
						var template = $.templates("#userListTemple");
						var htmlOutput = template.render(data);
						$("#userList").html(htmlOutput);
					} else {
						$("#userList").html("没有搜索到相关信息");
					}
				}
			});
		},
		//上传文件
		_uploadFile:function(){
			var _this = this;
			var checkFileData = this._checkFileData();
			if(!checkFileData){
				return false;
			}
			var form = new FormData();
			form.append("uploadFile", document.getElementById("uploadFile").files[0]);
			form.append("imgSize","78x78");

			// XMLHttpRequest 对象
			var xhr = new XMLHttpRequest();
			var uploadURL = _base+"/home/upImg";
			xhr.open("post", uploadURL, true);

			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {// 4 = "loaded"
					if (xhr.status == 200) {
						var responseData = $.parseJSON(xhr.response);
						if(responseData.statusCode=="1"){
							var fileData = responseData.data;
							//文件上传成功
							if(fileData){
								//文件标识
								var filePosition = fileData.vfsId;
								//文件类型
								var fileName = fileData.fileType;
								//文件地址
								var fileUrl = fileData.imgUrl;
								_this._showMsg("上传成功:"+filePosition+","+fileName);
								_this._closeDialog();
								_this._showProdPicPreview(filePosition,fileName,fileUrl);
								return;
							}
						}
					}
					var msgDialog = Dialog({
						title: '提示',
						content: "文件上失败,状态:"+xhr.status,
						ok: function () {
							this.close();
						}
					});
					_this._closeDialog();
					msgDialog.showModal();
				}
			};
			xhr.send(form);
		},
		//检查文件
		_checkFileData:function(){
			var fileupload = document.getElementById("uploadFile");
			var fileLocation = fileupload.value;
			if(fileLocation == "" || fileLocation == null || fileLocation == undefined){
				return false;
			}
			var fileType = fileLocation.substring(fileLocation.lastIndexOf("."));
			var fileName,fileSize;
			if (fileupload.files && fileupload.files[0]) {
				fileName = fileupload.files[0].name;
				var size = fileupload.files[0].size;
				fileSize = size/(1024 * 1024)
			} else {
				fileupload.select();
				fileupload.blur();
				var filepath = document.selection.createRange().text;
				try {
					var fso, f, fname, fsize;
					fso = new ActiveXObject("Scripting.FileSystemObject");
					f = fso.GetFile(filepath); //文件的物理路径
					fileName = fso.GetFileName(filepath); //文件名（包括扩展名）
					fsize = f.Size; //文件大小（bit）
					fileSize = fsize / (1024*1024);
				} catch (e) {
					var msgDialog = Dialog({
						title: '提示',
						content: e + "\n 跳出此消息框，是由于你的activex控件没有设置好,\n" +
						"你可以在浏览器菜单栏上依次选择\n" +
						"工具->internet选项->\"安全\"选项卡->自定义级别,\n" +
						"打开\"安全设置\"对话框，把\"对没有标记为安全的\n" +
						"ActiveX控件进行初始化和脚本运行\"，改为\"启动\"即可",
						ok: function () {
							this.close();
						}
					});
					msgDialog.showModal();
					return false;
				}
			}
			fileType = fileType.toLowerCase();
			console.log("上传图片信息,图片名称:"+fileName+",图片大小:"+fileSize);
			//文件大小
			var checkSize = true;
			//文件类型
			var checkType = true;
			fileSize = fileSize.toFixed(4);
			if(fileSize > ProdEditPager.FILE_MAX_SIZE){
				this._showMsg('图片不能超过3M');
				checkSize = false;
			}else if(!$.inArray(fileType, ProdEditPager.FILE_TYPES)){
				this._showMsg('请上传jpg/png格式图片');
				checkType = false;
			}
			return checkSize&&checkType;
		},
		_closeDialog:function(){
			$("#uploadFile").val("");
			//document.getElementById("uploadFileMsg").setAttribute("style","display:none")
		},
		//预览图片信息
		_showProdPicPreview:function(filePosition,fileType,imgUrl){
			//确定当前要显示商品属性
			var divId = "prod_pic_"+picAttrVal;
			var imgObj;
			//查询该商品下未有图片的位置
			$("#"+divId+" img[imgId='']").each(function(i){
				//设置该图片的信息
				console.log("未设置图片:"+$(this).attr('attrVal')+",序号:"+$(this).attr('picInd'));
				if (imgObj ==null){
					imgObj = $(this);
				}
			});
			console.log("将要设置属性图片:"+imgObj.attr('attrVal')+",序号:"+imgObj.attr('picInd'));
			imgObj.attr('imgId',filePosition);
			imgObj.attr('imgType',fileType);
			imgObj.attr('src',imgUrl);
		},
		_showMsg:function(msg){
			var msg = Dialog({
				title: '提示',
				content:msg,
				ok:function(){
					this.close();
				}
			});
			msg.showModal();
		}
    });
    
    module.exports = ProdEditPager
});

