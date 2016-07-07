<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>商城商品管理</title>
	<%@ include file="/inc/inc.jsp" %>
	<link href="${_slpres }/styles/bootstrap.css"rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/font-awesome.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/frame.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/global.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/modular.css" rel="stylesheet" type="text/css">
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
	           <p><a href="#">仓库中商品</a></p>  
	           <p></p>  
	           <p></p>
	           </li>
	        </ul>  
	    </div>
	    
	   <!--标签结束-->
	      <!--查询区域-->
	   <!--查询区域结束-->
	   <!--查询结果-->
	   <div class="form-wrapper"><!--白底内侧-->
	   	
	         <div class="nav-tplist-wrapper"><!--白底内侧-->
	         	 <div class="order-list-table">
		           <ul>
			           <li><a href="#" class="current">待上架</a></li>
			           <li><a href="#">售罄下架</a></li>
			           <li><a href="#">库存暂停</a></li>
		           </ul>                                        
		     	</div>
	          <!--结果标题-->
	         <div id="date1">
	          <div class="nav-form">
	           	<ul>
	                <li class="width-xlag">
	                    <p class="word">商品类目</p>
	                    <c:forEach var="map" items="${catInfoMap}" varStatus="status">
                           <p id="productCat${status.index}">
                               <select class="select-small" onChange="pager._selectChange(this);">
                               <c:forEach var="info" items="${map.value}">
                                   <option value="${info.productCatId}">${info.productCatName}</option>
                               </c:forEach>
                               </select>
                           </p>
                        </c:forEach>
                        <script id="prodCatTemple" type="text/template">
                            <p id="productCat{{:level}}">
								<select class="select-small" onChange="pager._selectChange(this);">
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
	                    <p><input id="productType" type="text" class="int-medium"></p>
	                 </li>
	                 <li>
	                    <p class="word">商品ID</p>
	                    <p><input id="productId"  type="text" class="int-medium"></p>
	                </li>
	            </ul>
	            <ul>
	                <li  class="width-xlag">
	                    <p class="word">商品名称</p>
	                    <p><input  id="productName" type="text" class="int-medium"></p>
	                    <p><input id="searchStayUpProd" type="button" value="查询" class="blling-btn blue-btn"></p>
	                </li>
	            </ul>  
	            
	        </div>
	         <!--结果表格-->
	        <div class="nav-tplist-table commodity-tplist-table">
	          <table width="100%" border="0">
	              <tr class="bj">
	                <td width="10%">商品ID</td>                                                                                                      
	                <td width="10%">所属类目</td>
	                <td>类型</td>
	                <td>预览图</td>
	                <td width="20%">商品名称</td>
	                <td>状态</td>
	                <td>上架时间</td>    
	                <td>操作</td>                                                                                
	              </tr>
	              <tbody id="selectStayUpProdData"></tbody>
	              <%-- <tr>
	                <td>24343433</td>
	                <td>话费</td>
	                <td>虚拟</td>
	                <td><img src="${_slpres}/images/sp-01.png"></td>
	                <td>中国移动100元充值卡</td>
	                <td>状态</td>
	                <td>2016-3-18  13:25</td>
	                <td>
		                	<div>
		                		<p><a href="#" class="blue-border">上架销售</a></p>
		                		<p><a href="#" class="blue">编辑商品</a></p>
		                	</div>
	                </td> 
	              </tr>  --%>
				</table>
	          	 <script id="selectStayUpProdTemple" type="text/template">
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
                                        <p><a href="#" class="blue-border">上架销售</a></p>
                                        <p><a href="${_base}/prodedit/{{:prodId}}" class="blue">编辑商品</a></p>
                                    </div>
                                </td>
                            </tr>
					</script>
	          </div> 
	        <!--结果表格结束-->
	        <!--分页-->
					 <div>
		 				 <nav style="text-align: right">
							<ul id="stayup-pagination-ul">
							</ul>
						</nav>
					  </div>
					 <!--分页-->
	        <!-- <div class="paging-large">
	        <ul>
	            <li class="prev-up"><a href="#">&lt;上一页</a> </li>
	            <li class="active"> <a href="#">1 </a> </li>
	            <li> <a href="#">2 </a> </li>
	            <li> <span>…</span> </li>
	            <li> <a href="#">38</a> </li>
	            <li> <a href="#">39</a> </li>
	            <li> <a href="#">40</a> </li>
	            <li> <a href="#">41</a> </li>
	            <li> <a href="#">42</a> </li>
	            <li> <span>…</span> </li>
	            <li class="next-down"><a href="#">下一页&gt;</a> </li>
	            <li>共100页</li>
	   		     <li>
	                <span>到</span>
	                <span><input type="text" class="int-verysmall"></span>
	                <span>页</span>
	                <span class="btn-span"><a class="but-determine">确定</a></span>
	            </li>
	            
	         </ul>
		</div>
	         -->
	         </div>
	        <div id="date2" style="display:none;">
	        <div class="nav-form">
	           	<ul>
	                <li class="width-xlag">
	                    <p class="word">商品类目</p>
	                    <p><select class="select-small"><option>一级类目</option></select></p>
	                    <p><select class="select-small"><option>二级类目</option></select></p>
	                    <p><select class="select-small"><option>三级类目</option></select></p>
	                    <p><select class="select-small"><option>三级类目</option></select></p>
	                    <p><select class="select-small"><option>三级类目</option></select></p>
	                </li> 
	            </ul>
	            <ul>
	           	 	<li>
	            	 		<p class="word">商品类型</p>
	                    <p><input type="text" class="int-medium"></p>
	                 </li>
	                 <li>
	                    <p class="word">商品ID</p>
	                    <p><input type="text" class="int-medium"></p>
	                </li>
	            </ul>
	            <ul>
	                <li  class="width-xlag">
	                    <p class="word">商品名称</p>
	                    <p><input type="text" class="int-medium"></p>
	                    <p><input type="button" value="查询" class="blling-btn blue-btn"></p>
	                </li>
	            </ul>  
	        </div>
	         <!--结果表格-->
	        <div class="nav-tplist-table commodity-tplist-table">
	          <table width="100%" border="0">
	              <tr class="bj">
	                <td width="10%">>商品ID</td>                                                                                                      
	                <td width="10%">所属类目</td>
	                <td>类型</td>
	                <td>预览图</td>
	                <td width="30%">商品名称</td>
	                <!-- <td>价格（￥）</td>
	                <td>剩余库存</td>
	                <td>总销量</td> -->
	                <td>状态</td>
	                <td>下架时间</td>    
	                <td>操作</td>                                                                                
	              </tr>
	              <tr>
	                <td>24343433</td>
	                <td>话费</td>
	                <td>虚拟</td>
	                <td><img src="${_slpres}/images/sp-01.png"></td>
	                <td>苹果正版iphone 6s</td>
	                <td>状态</td>
	                <td>2016-3-18  13:25</td>
	                <td>
	                	<div>
	                		<p><a href="#" class="blue">查看商品</a></p>
	                	</div>
	                </td> 
	              </tr> 
	              <%-- <tr>
	                <td>24343433</td>
	                <td>话费</td>
	                <td>虚拟</td>
	                <td><img src="${_slpres}/images/sp-01.png"></td>
	                <td>苹果正版iphone 6s</td>
	                <td>6000</td>
	                <td>20</td>
	                <td>0</td>
	                <td>2016-3-18  13:25</td>
	                <td>
	                	<div>
	                		<p><a href="#" class="blue">查看商品</a></p>
	                	</div>
	                </td> 
	              </tr>  --%>
				</table>
	          
	          </div> 
	        <!--结果表格结束-->
	        <div class="paging-large">
	        <ul>
	            <li class="prev-up"><a href="#">&lt;上一页</a> </li>
	            <li class="active"> <a href="#">1 </a> </li>
	            <li> <a href="#">2 </a> </li>
	            <li> <span>…</span> </li>
	            <li> <a href="#">38</a> </li>
	            <li> <a href="#">39</a> </li>
	            <li> <a href="#">40</a> </li>
	            <li> <a href="#">41</a> </li>
	            <li> <a href="#">42</a> </li>
	            <li> <span>…</span> </li>
	            <li class="next-down"><a href="#">下一页&gt;</a> </li>
	            <li>共100页</li>
	   		     <li>
	                <span>到</span>
	                <span><input type="text" class="int-verysmall"></span>
	                <span>页</span>
	                <span class="btn-span"><a class="but-determine">确定</a></span>
	            </li>
	            
	         </ul>
		</div>
	        
	         </div>
	        <div id="date3" style="display:none;">
	          <div class="nav-form">
	           	<ul>
	                <li class="width-xlag">
	                    <p class="word">商品类目</p>
	                    <p><select class="select-small"><option>一级类目</option></select></p>
	                    <p><select class="select-small"><option>二级类目</option></select></p>
	                    <p><select class="select-small"><option>三级类目</option></select></p>
	                    <p><select class="select-small"><option>三级类目</option></select></p>
	                    <p><select class="select-small"><option>三级类目</option></select></p>
	                </li> 
	            </ul>
	            <ul>
	           	 	<li>
	            	 		<p class="word">商品类型</p>
	                    <p><input type="text" class="int-medium"></p>
	                 </li>
	                 <li>
	                    <p class="word">商品ID</p>
	                    <p><input type="text" class="int-medium"></p>
	                </li>
	            </ul>
	            <ul>
	                <li class="width-xlag">
	                    <p class="word">商品名称</p>
	                    <p><input type="text" class="int-medium"></p>
	                    <p><input type="button" value="查询" class="blling-btn blue-btn"></p>
	                </li>
	            </ul>  
	            
	        </div>
	         <!--结果表格-->
	        <div class="nav-tplist-table commodity-tplist-table">
	          <table width="100%" border="0">
	          	<tr class="bj">
	                <td width="10%">>商品ID</td>                                                                                                      
	                <td width="10%">所属类目</td>
	                <td>类型</td>
	                <td>预览图</td>
	                <td width="30%">商品名称</td>
	                <td>状态</td>
	                <td>暂停时间</td>    
	                <td>操作</td>  
	              </tr>
	              <tr>
	                <td>24343433</td>
	                <td>话费</td>
	                <td>虚拟</td>
	                <td><img src="${_slpres}/images/sp-01.png"></td>
	                <td>中国移动100元充值卡</td>
	                <td>状态</td>
	                <td>2016-3-18  13:25</td>
	                <td>
		                	<div>
		                		<p><a href="#" class="blue">查看商品</a></p>
		                	</div>
	                </td> 
	              </tr> 
				</table>
	          
	          </div> 
	        <!--结果表格结束-->
	        <div class="paging-large">
		        <ul>
		            <li class="prev-up"><a href="#">&lt;上一页</a> </li>
		            <li class="active"> <a href="#">1 </a> </li>
		            <li> <a href="#">2 </a> </li>
		            <li> <span>…</span> </li>
		            <li> <a href="#">38</a> </li>
		            <li> <a href="#">39</a> </li>
		            <li> <a href="#">40</a> </li>
		            <li> <a href="#">41</a> </li>
		            <li> <a href="#">42</a> </li>
		            <li> <span>…</span> </li>
		            <li class="next-down"><a href="#">下一页&gt;</a> </li>
		            <li>共100页</li>
		   		     <li>
		                <span>到</span>
		                <span><input type="text" class="int-verysmall"></span>
		                <span>页</span>
		                <span class="btn-span"><a class="but-determine">确定</a></span>
		            </li>
		            
		         </ul>
			</div>
	        
	         </div>
	        
	         </div> 
	    </div>
	   <!--查询结果结束-->
	
	    </div>
	</div>	
	<!-- footer -->
	<div class="footer">版权所有 © SLP版权归运营家所有</div>
</body>
	
	<script src="${_slpres }/scripts/frame.js"  type="text/javascript" ></script>
	<script src="${_slpres }/scripts/metismenu.js"></script>
	 <script type="text/javascript"> 
		window.onload = function(){	
			var timer;
			var elem = document.getElementById('elem');
			var elem1 = document.getElementById('elem1');
			var elem2 = document.getElementById('elem2');
			elem2.innerHTML = elem1.innerHTML;
			timer = setInterval(Scroll,40);
			function Scroll(){
				if(elem.scrollTop>=elem1.offsetHeight){
					elem.scrollTop -= elem1.offsetHeight;
				}else{
					elem.scrollTop += 1;
				}
			}	
			elem.onmouseover = function(){
				clearInterval(timer);
			}	
			elem.onmouseout = function(){
				timer = setInterval(Scroll,40);
			}
		}
	</script>
	<script type="text/javascript">
		var pager;
		var count = '${count}';
		var prodInfoList = '${prodInfoList}';
		var productEditInfo = '${productEditInfo}';
		(function () {
			seajs.use('app/jsp/product/storprodlist', function (StorprodlistPager) {
				pager = new StorprodlistPager({element: document.body});
				pager.render();
			});
		})();
	</script>
</html>

