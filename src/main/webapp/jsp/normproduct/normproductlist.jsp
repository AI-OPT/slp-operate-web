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

<!-- UED -->
<script src="/slp-op/resources/slpoperate/scripts/frame.js" type="text/javascript"></script>
<script src="/slp-op/resources/slpoperate/scripts/metismenu.js"></script>
	
</head>

<body align="center">
	<!--确认是否下架弹出框 -->
	<!-- <div class="eject-big">
	<div class="eject-samll">
		<div class="eject-samll-title">
			<p>下架操作确认</p>
			<p class="img"><A href="#"></A></p>
		</div>
		确认上架
		<div class="eject-samll-confirm">
			<ul>
			<li class="word">确定要将商品手动下架吗?</li>
			<li><input id="upConfirm" type="button"  class="slp-btn eject-small-btn" value="确认"><input type="button"  class="slp-btn eject-small-btn close-btn" value="取消"></li>		
			</ul>
		</div>
	</div>	
	<div class="eject-mask"></div>	
	</div> -->
	<!--确认是否上架弹出框 结束-->



	<!--顶部菜单-->
	<%@ include file="/inc/top-menu.jsp"%>
	<!--顶部菜单结束-->
	<!-- 左侧菜单 -->
	<%@ include file="/inc/left-menu.jsp"%>
	<!-- 左侧菜单结束 -->

	<div class="wrapper">
		<!--外围框架-->
		<!--右侧框架-->
		<div class="wrapper-right">
			<!--公告位置-->
			<%@ include file="/inc/public-msg.jsp"%>
			<!--公告位置结束-->
			<!--标签-->
			<div class="right-tags">
				<ul>
					<li>
						<p class="none">您现在的位置：</p>
						<p>
							<a href="#">标准品管理</a>
						</p>
						<p></p>
						<p></p>
					</li>
				</ul>
			</div>

			<!--标签结束-->
			<!--查询结果-->
			<div class="form-wrapper">
				<!--白底内侧-->

				<div class="nav-tplist-wrapper">
					<!--白底内侧-->
					<!--结果标题-->
					<div id="date1">
						<div class="form-label">
							<ul id="data1ProdCat">
								<li class="width-xlag">
									<p class="word">商品类目</p> <c:forEach var="map"
										items="${catInfoMap}" varStatus="status">
										<p id="productCat${status.index}">
											<select class="select select-small"
												onChange="pager._selectChange(this);">
												<c:forEach var="info" items="${map.value}">
													<option value="${info.productCatId}">${info.productCatName}</option>
												</c:forEach>
											</select>
										</p>
									</c:forEach> <script id="prodCatTemple" type="text/template">
                                	 <p id="productCat{{:level}}">
										<select class="select select-small" onChange="pager._selectChange(this);">
											{{for prodCatList}}
                                   			 	<option value="{{:productCatId}}">{{:productCatName}}</option>
											{{/for}}
                               			</select>
									</p>
								</script>
								</li>
							</ul>


						</div>
						<!--查询区域-->
						<div class="form-wrapper">
							<!--白底内侧-->

							<div class="form-label">
								<ul>
									<li class="width-xlag">
										<p class="word">标准品名称</p>
										<p>
											<input id="productName" type="text"
												class="int-text int-medium">
										</p> <!-- <p>
											
											<input type="button" value="查询"class="biu-btn btn-blue btn-mini">
										</p> -->
										<p>
											<input id="selectNormProductList" type="button" value="查询"
												class="biu-btn btn-blue btn-mini">
										</p>
										<!-- <p class="sos">
											<a href="#">高级搜索<i class="icon-caret-down"></i></a>
										</p> -->
										<p class="sos"><a href="javascript:void(0);">高级搜索<i class="icon-caret-down"></i></a></p>
										
									</li>
								</ul>
								<!--点击展开-->
								<div class="open" style="display: none;">
									<ul>
										<!-- <li>
											<p class="word">标准品ID</p>
											<p>
												<input type="text" class="int-text int-medium">
											</p>
										</li> -->
										<li>
											<p class="word">标准品ID</p>
											<p>
												<input id="productId" type="text"
													class="int-text int-medium">
											</p>
										</li>
										<li>
											<p class="word">标准品状态</p>
											<p>
												<select id="state" class="select select-medium"></select>
											</p>
										</li>
									</ul>
									<ul>
										<!-- <li>
											<p class="word">标准品类型</p>
											<p>
												<select class="select select-medium"></select>
											</p>
										</li> -->
										<li>
											<p class="word">标准品类型</p>
											<p>
												<select id="productType" class="select select-medium">
													<option value="">全部</option>
													<option value="1">实物</option>
													<option value="2">虚拟</option>
												</select>
											</p>
										</li>
									</ul>
									<ul>
										<li>
											<p class="word">操作时间</p>
											<p>
												<input id="operTime" type="text" class="int-text int-medium"><a
													href="#" class="ccc"><i class="icon-calendar"></i></a>
											</p>
											<p>~</p>
											<p>
												<input id="operTime" type="text" class="int-text int-medium"><a
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
								<div class="nav-tplist-title nav-tplist-title-border">
									<ul>
										<!--<div class="title-right">
											<p class="plus">
												<a href="#"><i class="icon-download-alt"></i></a>
											</p>
											<p class="plus-word">
												<a href="#">下载模板</a>
											</p>
										</div>
										 <div class="title-right">
											<p class="plus">
												<a href="#"><i class="icon-folder-open-alt"></i></a>
											</p>
											<p class="plus-word">
												<a href="#">批量导入</a>
											</p>
										</div>
										<div class="title-right">
											<p class="plus">
												<a href="#"><i class="icon-share"></i></a>
											</p>
											<p class="plus-word">
												<a href="#">导出商品</a>
											</p>
										</div>
										<div class="title-right">
											<p class="plus">
												<a href="#"><i class="icon-search"></i></a>
											</p>
											<p class="plus-word">
												<a href="#">查看废弃标准品</a>
											</p>
										</div> -->
										<div class="title-right">
											<p class="plus">
												<a href="#"><i class="icon-plus"></i></a>
											</p>
											<p class="plus-word">
												<a href="#">新增</a>
											</p>
										</div>

									</ul>
								</div>
								<div class="nav-form"></div>


								<div class="nav-tplist-table">
									<table width="100%" border="0">
										<tr class="bj">
											<td></td>
											<td>序号</td>
											<td>标准品ID</td>
											<td>标准品名称</td>
											<td>所属类目</td>
											<td>类型</td>
											<td>标准品状态</td>
											<td>操作时间</td>
											<td>操作人</td>
											<td>操作</td>
										</tr>
										<tbody id="searchNormProductData"></tbody>
									</table>

									<div id="showMessageDiv"></div>
									<script id="searchNormProductTemple" type="text/template">
                            <tr>

                                <td></td>
                                <td></td>
                                <td>{{:productId}}</td>

                                <td>{{:productName}}</td>
								<td>{{:productCatName}}</td>
                             <%--  <td>{{:productCatId}}</td>--%> 
                                <td>{{:productType}}</td>

                                <td>{{:state}}</td>
								
								<td>{{:~timesToFmatter(operTime)}}</td>

                                <td>{{:operId}}</td>

                                <td><a href="#" class="blue">查看详情</a><a href="#" class="red">编辑</a><a href="＃" class="blue">废弃</a></td> 
                            </tr>
							</script>
								</div>
								<!--分页-->
								<div>
									<nav style="text-align: right">
										<ul id="pagination-ul">
										</ul>
									</nav>
								</div>
								<!--分页-->
								<!--结果表格结束-->

								<!--结果表格-->

							</div>
						</div>
					</div>
					<!--查询结果结束-->

				</div>
			</div>
			<!-- footer -->
			<div class="footer">版权所有 © SLP版权归运营家所有</div>
</body>
<script src="${_slpres }/scripts/jquery-1.11.1.min.js"></script>
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
