<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>运营管理</title>
    <%@ include file="/inc/inc.jsp" %>
    <link href="${_slpres }/styles/bootstrap.css"rel="stylesheet" type="text/css">
    <link href="${_slpres }/styles/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="${_slpres }/styles/global.css" rel="stylesheet" type="text/css">
    <link href="${_slpres }/styles/frame.css" rel="stylesheet" type="text/css">
    <link href="${_slpres }/styles/modular.css" rel="stylesheet" type="text/css">
    
    <!-- <script type="text/javascript">
		function editState()
		{
		var se=confirm("是否确认下架?");
		if (se==true)
		  {
		  alert("你按下的是【确认】");
		  }
		else
		  {
		  alert("你按下的是【取消】");
		  }
		}
	</script> -->
</head>

<body>
<!--顶部菜单-->
<%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->
<!-- 左侧菜单 -->
<%@ include file="/inc/left-menu.jsp" %>
<!-- 左侧菜单结束 -->

<div class="wrapper"><!--外围框架-->
    <!--右侧框架-->
    <div class="wrapper-right">
        <!--公告位置-->
        <%@ include file="/inc/public-msg.jsp" %>
        <!--公告位置结束-->
        <!--标签-->
        <div class="right-tags">
            <ul>
                <li>
                    <p class="none">您现在的位置：</p>
                    <p><a href="#">商城商品管理</a></p>
                    <p></p>
                    <p></p>
                </li>
            </ul>
        </div>

        <!--标签结束-->
        <!--查询结果-->
        <div class="form-wrapper"><!--白底内侧-->

            <div class="nav-tplist-wrapper"><!--白底内侧-->
                <div class="order-list-table">
                    <ul>
                        <li><a href="#" class="current">销售中商品</a></li>
                        <!-- <li><a href="#">被拒绝</a></li>
                        <li><a href="#">审核中</a></li> -->
                    </ul>
                </div>
                <!--结果标题-->
                <div id="date1">
                    <div class="form-label">
                        <ul id="data1ProdCat">
                            <li class="width-xlag">
                                <p class="word">商品类目</p>
                                <c:forEach var="map" items="${catInfoMap}" varStatus="status">
	                                <p id="productCat${status.index}">
	                                    <select class="select select-small" onChange="pager._selectChange(this);">
	                                    <c:forEach var="info" items="${map.value}">
	                                        <option value="${info.productCatId}">${info.productCatName}</option>
	                                    </c:forEach>
	                                    </select>
	                                </p>
                                </c:forEach>
                                <script id="prodCatTemple" type="text/template">
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
                        <ul>
                        	<li>
                                <p class="word">商品类型</p>
                                <p>
	                                <select id="productType" class="select select-medium">
	                                	<option value="">全部</option>
	                                	<option value="1">实物</option>
	                                	<option value="2">虚拟</option>
	                                </select>
                                </p>
                            </li>
                            <li>
                                <p class="word">商品ID</p>
                                <p><input id="productId" type="text" class="int-text int-medium"></p>
                            </li>
                        </ul>
                        <ul>
                            <li class="width-xlag">
                                <p class="word">商品名称</p>
                                <p><input id="productName" type="text" class="int-text int-medium"></p>
                                <p><input id="selectProductInSale" type="button" value="查询" class="biu-btn btn-blue btn-mini"></p>
                            </li>
						</ul>
                    </div>
                    <!--结果表格-->
                    <div class="table table-border table-bordered table-bg table-hover mt-10 commodity-tplist-table">
                        <table width="100%" border="0">
                            <tr class="bj">
				                <td width="10%">商品ID</td>                                                                                                      
				                <td width="10%">所属类目</td>
				                <td>类型</td>
				                <td>预览图</td>
				                <td width="30%">商品名称</td>
				                <!-- <td>总库存</td> -->
				                <td>状态</td>
				                <td>生成时间</td>    
				                <td>操作</td>                                                                                
				             </tr>
                            <tbody id="searchProductData"></tbody>
                        </table>
                        	<div id="showMessageDiv"></div>
                            <script id="searchProductTemple" type="text/template">
                            <tr>
                                <td>{{:prodId}}</td>
                                <td>{{:productCatName}}</td>
                                <td>{{:productTypeName}}</td>
								{{if picUrl==null || picUrl==""}}
                            	    <td><img src="${_slpres}/images/sp-03-a.png"></td>
								{{else}}
									<td><img src="{{:picUrl}}"></td>
								{{/if}}
                                <td>{{:prodName}}</td>
                                <%-- <td>{{:totalNum}}</td>--%>
                                <td>{{:stateName}}</td>
                                <td>{{:~timesToFmatter(createTime)}}</td>
                                <td>
                                    <div>
                                        <p><a href="${_base}/prodedit/{{:prodId}}" class="blue-border">编辑商品</a></p>
                                    </div>
                                    <div>
                                     <!--  <p><a href="${_base}/prodquery/inStore" onclick="inStore()" class="blue-border">商品下架</a></p>  --!> 
                                        <p><a id="{{:prodId}}" href="#" onclick="inStore()" class="blue-border">商品下架</a></p>
                                    </div>
                                </td>
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
                </div>
            </div>
        </div>
        <!--查询结果结束-->

    </div>
</div>
</body>


	<script type="text/javascript">
	    window.onload = function () {
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
	
	        elem.onmouseover = function () {
	            clearInterval(timer);
	        }
	        elem.onmouseout = function () {
	            timer = setInterval(Scroll, 40);
	        }
	    }
	</script>
	<script src="${_slpres }/scripts/frame.js" type="text/javascript"></script>
<!-- 	<script type="text/javascript">
		var pager;
		var count = '${count}';
		var prodInfoList = '${prodInfoList}';
		var productEditInfo = '${productEditInfo}';
		(function () {
			seajs.use('app/jsp/product/insalelist', function (InsalelistPager) {
				pager = new InsalelistPager({element: document.body});
				pager.render();
			});
		})();
	</script> -->
	<script type="text/javascript">
		var pager;
		var count = '${count}';
		var prodInfoList = '${prodInfoList}';
		var productEditInfo = '${productEditInfo}';
		(function () {
			$('#selectStayUpProdData').delegate('.blue-border','click',function(){
				console.log('prodId:'+$(this).attr('id'));
				pager._prodToInSale($(this).attr('id'));
			});
			seajs.use('app/jsp/product/insalelist', function (InsalelistPager) {
				pager = new InsalelistPager({element: document.body});
				pager.render();
			});
		})();
	</script>
	
	<script type="text/javascript">
		function inStore()
		{
		 if (!confirm("确认要手动下架？")) { 
	            window.event.returnValue = false; 
	        } 
		}
	
		/* <form action="del.html" target="_blank" method="get">
			function inStore()
			{
			 if (!confirm("确认要手动下架？")) { 
		            window.event.returnValue = false; 
		        } 
			}
		</form> */
	</script>
	
</html>