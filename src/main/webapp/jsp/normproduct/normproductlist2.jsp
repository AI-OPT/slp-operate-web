<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>运营管理</title>
<%@ include file="/inc/inc.jsp"%>
<link href="${_slpres }/styles/bootstrap.css" rel="stylesheet"
	type="text/css">
<link href="${_slpres }/styles/font-awesome.css" rel="stylesheet"
	type="text/css">
<link href="${_slpres }/styles/global.css" rel="stylesheet"
	type="text/css">
<link href="${_slpres }/styles/frame.css" rel="stylesheet"
	type="text/css">
<link href="${_slpres }/styles/modular.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<!--右侧弹出框-->
	<div class="msg-cnt">
		<div class="p">
			<a ng-click="$hide()" class="pull-right text-muted"><img
				src="../images/close.png"></a> 审批待办事项
		</div>
		<div class="box-row">
			<div class="box-cell">
				<div class="box-inner">
					<div class="list-group no-radius no-borders">
						<a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-success text-xs m-r-xs"></i> <span>审批待办事项1</span>
						</a> <a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-success text-xs m-r-xs"></i> <span>
								审批待办事项5条</span>
						</a> <a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-warning text-xs m-r-xs"></i> <span>审批待办事项2</span>
						</a> <a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-muted-lt text-xs m-r-xs"></i> <span>审批待办事项5条</span>
						</a> <a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-muted-lt text-xs m-r-xs"></i> <span>审批待办事项3个审批</span>
						</a> <a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-muted-lt text-xs m-r-xs"></i> <span>审批待办事项</span>
						</a> <a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-muted-lt text-xs m-r-xs"></i> <span>审批待办事项</span>
						</a> <a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-muted-lt text-xs m-r-xs"></i> <span>审批待办事项5条</span>
						</a> <a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-muted-lt text-xs m-r-xs"></i> <span>审批待办事项</span>
						</a> <a class="list-group-item p-h-md p-v-xs"> <i
							class="icon-circle text-muted-lt text-xs m-r-xs"></i> <span>审批待办事项</span>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--右侧弹出框结束-->


	<div class="header">
		<!--头部-->
		<div class="logo">
			<a href="#"><img src="../images/logo.png"></a>
		</div>
		<div class="right">
			<!--头部右侧-->
			<div class="date">今天是2015-12-01 15：46 星期二</div>
			<div class="Bubble msg">
				<a href="#"><img src="../images/qipao.png"></a><span>4</span>
			</div>
			<div class="information">
				<ul>
					<li>欢迎，<a href="#">admin！</a></li>
					<li><a href="#"><i class="icon-off"></i></a></li>
				</ul>
			</div>
		</div>
	</div>
	<!--头部结束-->

	<!--左侧菜单-->
	<div class="wrapper-left">
		<ul id="menu3">
			<li><A href="#">合同管理<span><img
						src="../images/left-sj.png"></span></A>
				<ul>
					<li><a href="#">合同统计分析</a></li>
					<li><a href="#">客户信息管理</a></li>
					<li><a href="#">合同信息管理</a></li>
					<li><a href="#">网络协议签订列表</a></li>
					<li><a href="#">变更记录</a></li>
				</ul></li>
			<li><A href="#">服务配置管理<span><img
						src="../images/left-sj.png"></span></A>
				<ul>
					<li><a href="服务配置管理.html">服务配置信息列表</a></li>
				</ul></li>
			<li><A href="#">路由管理<span><img
						src="../images/left-sj.png"></span></A>
				<ul>
					<li><a href="路由列表.html">路由列表</a></li>
					<li><a href="路由规则管理.html">路由规则列表</a></li>
					<li><a href="路由组列表.html">路由组列表</a></li>
				</ul></li>
		</ul>
	</div>

	<div class="wrapper">
		<!--外围框架-->
		<!--右侧框架-->
		<div class="wrapper-right">
			<!--公告位置-->
			<div class="right-topnav">
				<p class="gongg">
					<A href="#">［公告］:</A>
				</p>
				<div id="elem">
					<ul id="elem1">
						<li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
						<li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
						<li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
						<li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
					</ul>
					<ul id="elem2">
					</ul>
				</div>
				<p class="dclose">
					<A href="#"><i class="icon-remove"></i></A>
				</p>
			</div>
			<!--公告位置结束-->
			<!--标签-->
			<div class="right-tags">
				<ul>
					<li>
						<p class="none">您现在的位置：</p>
						<p>
							<a href="#">库存管理</a> >
						</p>
						<p>生成虚拟库存</p>
					</li>
				</ul>
			</div>

			<!--标签结束-->
			<!--查询区域-->
			<div class="form-wrapper">
				<!--白底内侧-->

				<div class="form-label">
					<ul>
						<li class="width-xlag">
							<p class="word">标准品名称</p>
							<p>
								<input type="text" class="int-text int-medium">
							</p>
							<p>
								<input type="button" value="查询" class="biu-btn btn-blue btn-mini">
							</p>
							<p class="sos">
								<a href="#">高级搜索<i class="icon-caret-down"></i></a>
							</p>
						</li>
					</ul>
					<!--点击展开-->
					<div class="open" style="display: none;">
						<ul>
							<li>
								<p class="word">标准品ID</p>
								<p>
									<input type="text" class="int-text int-medium">
								</p>
							</li>
							<li>
								<p class="word">标准品状态</p>
								<p>
									<select class="select select-medium"></select>
								</p>
							</li>
						</ul>
						<ul>
							<li>
								<p class="word">标准品类型</p>
								<p>
									<select class="select select-medium"></select>
								</p>
							</li>
							<li>
								<p class="word">操作人</p>
								<p>
									<select class="select select-medium"></select>
								</p>
							</li>
						</ul>
						<ul>
							<li>
								<p class="word">操作时间</p>
								<p>
									<input type="text" class="int-text int-medium"><a
										href="#" class="ccc"><i class="icon-calendar"></i></a>
								</p>
								<p>~</p>
								<p>
									<input type="text" class="int-text int-medium"><a
										href="#" class="ccc"><i class="icon-calendar"></i></a>
								</p>
							</li>
						</ul>
					</div>
					<!--点击展开结束-->
				</div>

			</div>
			<!--查询区域结束-->
			<!--查询结果-->
			<div class="form-wrapper">
				<!--白底内侧-->
				<div class="nav-tplist-wrapper">
					<!--白底内侧-->
					<!--结果标题-->
					<div class="nav-tplist-title">
						<ul>
							<li>标准品列表</li>
						</ul>
					</div>
					<div class="nav-form nav-form-padding">
						<ul>
							<li class="width-xlag">
								<p>所属类目：</p>
								<p>
									<select class="select select-small"></select>
								</p>
								<p>
									<select class="select select-small"></select>
								</p>
								<p>
									<select class="select select-small"></select>
								</p>
							</li>
						</ul>
					</div>
					<!--结果表格-->
					<div
						class="table table-border table-bordered table-bg table-hover mt-10">
						<table width="100%" border="0">
							<tbody>
								<tr class="bj">
									<td>序号</td>
									<td>标准品ID</td>
									<td>标准品名称</td>
									<td>所属类目</td>
									<td>类型</td>
									<td>操作日期</td>
									<td>操作人</td>
									<td>操作</td>
								</tr>
								<tr>
									<td>1</td>
									<td>3434111</td>
									<td>Apple iPhone 6s plus</td>
									<td>手机</td>
									<td>实物</td>
									<td>2015-1-23 10:02:34</td>
									<td>hesuan</td>
									<td><a href="#" class="blue">编辑</a></td>
								</tr>
								<tr>
									<td>1</td>
									<td>3434111</td>
									<td>Apple iPhone 6s plus</td>
									<td>手机</td>
									<td>实物</td>
									<td>2015-1-23 10:02:34</td>
									<td>hesuan</td>
									<td><a href="#" class="blue">编辑</a></td>
								</tr>
								<tr>
									<td>1</td>
									<td>3434111</td>
									<td>Apple iPhone 6s plus</td>
									<td>手机</td>
									<td>实物</td>
									<td>2015-1-23 10:02:34</td>
									<td>hesuan</td>
									<td><a href="#" class="blue">编辑</a></td>
								</tr>

							</tbody>
						</table>
					</div>
					<!--结果表格结束-->
					<!--分页-->
					<div class="paging-large">
						<ul>
							<li class="prev-up"><a href="#">&lt;上一页</a></li>
							<li class="active"><a href="#">1 </a></li>
							<li><a href="#">2 </a></li>
							<li><span>…</span></li>
							<li><a href="#">38</a></li>
							<li><a href="#">39</a></li>
							<li><a href="#">40</a></li>
							<li><a href="#">41</a></li>
							<li><a href="#">42</a></li>
							<li><span>…</span></li>
							<li class="next-down"><a href="#">下一页&gt;</a></li>
							<li>共100页</li>
							<li><span>到</span> <span><input type="text"
									class="int-verysmall"></span> <span>页</span> <span
								class="btn-span"><a class="but-determine">确定</a></span></li>
						</ul>
					</div>
				</div>
			</div>
			<!--查询结果结束-->

		</div>
	</div>
	<!-- footer -->
	<div class="footer">版权所有 © SLP版权归运营家所有</div>
</body>
<script src="${_slpres }/scripts/frame.js" type="text/javascript"></script>
<script src="${_slpres }/scripts/metismenu.js"></script>
<script type="text/javascript">
	window.onload = function() {
		var timer;
		var elem = document.getElementById('elem');
		var elem1 = document.getElementById('elem1');
		var elem2 = document.getElementById('elem2');
		elem2.innerHTML = elem1.innerHTML;
		timer = setInterval(Scroll, 40);
		function Scroll() {
			if (elem.scrollTop >= elem1.offsetHeight) {
				elem.scrollTop -= elem1.offsetHeight;
			} else {
				elem.scrollTop += 1;
			}
		}

		elem.onmouseover = function() {
			clearInterval(timer);
		}
		elem.onmouseout = function() {
			timer = setInterval(Scroll, 40);
		}
	}
</script>
<script type="text/javascript">
	var pager;
	var count = '${count}';
	var prodInfoList = '${prodInfoList}';
	var productEditInfo = '${productEditInfo}';
	(function() {
		$('#searchNormProductData').delegate("a[class='blue-border']", 'click',
				function() {
					console.log('prodId:' + $(this).attr('id'));
					clickId = $(this).attr('id');

					pager._showUpConfirm($(this).attr('id'));
					//pager._prodToInSale($(this).attr('id'));
				});
		seajs.use('app/jsp/normproduct/normproductlist', function(
				InsalelistPager) {
			pager = new InsalelistPager({
				element : document.body
			});
			pager.render();
		});
	})();
</script>

</html>
