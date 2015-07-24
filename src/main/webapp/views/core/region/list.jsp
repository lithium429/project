<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<gd:Layout>
	<gd:Navgation addlink="" addr="系统管理 &gt; 区域管理 "></gd:Navgation>
	<div class="frame_inner_cont country_cont">
		<!-- S省 -->
    	<div class="frame_inner_left col_inner_left scroll_box">
    		<form id="spec_form1" action="admin/region/datalist.do"
				data-ajax="true" data-ajax-begin="loadBegin"
				data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
				data-ajax-loading="#loading_img" data-ajax-method="POST"
				data-ajax-mode="replace" data-ajax-update="#list_1" novalidate="novalidate">
		        <input type="hidden" name="menu_id" id="menu_id" value="${menu_id}" />
				<input type="hidden" name="page_index" id="page_index" value="1" /> 
				<input type="hidden" class="type" id="type1" name="type" value="1" />
			</form>
			<form id="spec_form2" action="admin/region/datalist.do"
				data-ajax="true" data-ajax-begin="loadBegin"
				data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
				data-ajax-loading="#loading_img" data-ajax-method="POST"
				data-ajax-mode="replace" data-ajax-update="#list_2"
				novalidate="novalidate">
				<input type="hidden" name="page_index" id="page_index" value="1" />
				<input type="hidden" class="parent_id" id="parent_id2" name="parent_id" value="" />
				<input type="hidden" class="type" id="type2" name="type" value="2" />
				<div class="mtb15">
					<div class="model_top_fun">
						<c:if test="${gdf:judgeRoleMenu(roleMenuList,'添加') }">
			            	<span class="btn btn_pub"><a href="javascript:void(0);" class="provinceCreate" onclick="region.create(1,'添加省','')">
			                	添加</a></span>
			            </c:if>
			            <c:if test="${gdf:judgeRoleMenu(roleMenuList,'修改') }">
		                	<span class="btn btn_base"><a href="javascript:void(0);" class="provinceEdit" onclick="region.edit(1,'修改省','150px')">
		                		修改</a></span>
		               	</c:if>
		               	<c:if test="${gdf:judgeRoleMenu(roleMenuList,'删除') }">
		                	<span class="btn btn_base"><a href="javascript:void(0);" class="provinceDelete" onclick="region.remove(1)">
			                	删除</a></span>
			            </c:if>
			        </div>
				</div>
            	<div class="data_model data_lis t_wrap" id="list_1">
            	<%@ include file="data_list.jsp"%>
            	</div>
			</form>
    	</div>
    	<!-- E省 -->
    	<!-- S市 -->
    	<div class="frame_inner_left col_inner_center data_model scroll_box">
    		<form id="spec_form3" action="admin/region/datalist.do"
				data-ajax="true" data-ajax-begin="loadBegin"
				data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
				data-ajax-loading="#loading_img" data-ajax-method="POST"
				data-ajax-mode="replace" data-ajax-update="#list_3"
				novalidate="novalidate">
				<input type="hidden" class="parent_id" id="parent_id3" name="parent_id" value="" />
				<input type="hidden" class="type" id="type3" name="type" value="3" />
				<div class="mtb15">
					<div class="model_top_fun">
						<c:if test="${gdf:judgeRoleMenu(roleMenuList,'添加') }">
			            	<span class="btn btn_pub"><a href="javascript:void(0);" class="cityCreate" onclick="region.create(2,'添加市',1)">
			                	添加</a></span>
			            </c:if>
			            <c:if test="${gdf:judgeRoleMenu(roleMenuList,'修改') }">
		                	<span class="btn btn_base"><a href="javascript:void(0);" class="cityEdit" onclick="region.edit(2,'修改市','150px')">
		                		修改</a></span>
		                </c:if>
		                <c:if test="${gdf:judgeRoleMenu(roleMenuList,'删除') }">
		                	<span class="btn btn_base"><a href="javascript:void(0);" class="cityDelete" onclick="region.remove(2)">
			                	删除</a></span>
			            </c:if>
			        </div>
				</div>
            	<div class="data_model data_lis t_wrap" id="list_2">
            	</div>
			</form>
    	</div>
    	<!-- E市 -->
    	<!-- S区/县 -->
    	<div class="frame_inner_left col_inner_center2 data_model scroll_box">
    		<form id="spec_form4" action="admin/region/datalist.do"
				data-ajax="true" data-ajax-begin="loadBegin"
				data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
				data-ajax-loading="#loading_img" data-ajax-method="POST"
				data-ajax-mode="replace" data-ajax-update="#list_4"
				novalidate="novalidate">
				<input type="hidden" class="parent_id" id="parent_id4" name="parent_id" value="" />
				<input type="hidden" class="type" id="type4" name="type" value="4" />
				<div class="mtb15">
					<div class="model_top_fun">
						<c:if test="${gdf:judgeRoleMenu(roleMenuList,'添加') }">
			            	<span class="btn btn_pub"><a href="javascript:void(0);" class="districtCreate" onclick="region.create(3,'添加区/县',2)">
			                	添加</a></span>
			            </c:if>
			            <c:if test="${gdf:judgeRoleMenu(roleMenuList,'修改') }">
		                	<span class="btn btn_base"><a href="javascript:void(0);" class="districtEdit" onclick="region.edit(3,'修改区/县','150px')">
		                		修改</a></span>
		               	</c:if>
		               	<c:if test="${gdf:judgeRoleMenu(roleMenuList,'删除') }">
		                	<span class="btn btn_base"><a href="javascript:void(0);" class="districtDelete" onclick="region.remove(3)">
			                	删除</a></span>
			            </c:if>
			        </div>
				</div>
            	<div class="data_model data_lis t_wrap" id="list_3">
            	</div>
			</form>
    	</div>
    	<!-- E区/县 -->
	</div>
	<img class="dn" id="loading_img" src="img/loading.gif" alt="loading" />
	<script type="text/javascript" src="static/js/index.js"></script>
	<script type="text/javascript">
	$(function () {

	    $(".listTr").live("click", function () {
	        var $this = $(this);
	        $this.addClass("cur").siblings("tr").removeClass("cur");
	        v = Number($this.attr("v")) + 1;
	        if (v < 5) {
	            $("#parent_id" + v).val($this.attr("key"));
	            $('#spec_form' + v).submit();
	        } 
	    });
	});

	var region = (function(){
		function create(type, title, PType) {
		    if (PType == "" || $("#list_" + PType + " .cur").length > 0) {
		        if (PType == "") {
		            openPage({ url: 'region/add.do?type=' + type, id: 'add_page', title: title, width: '350px', height: '150px' });
		        } else {
		            var pId = $("#list_" + PType + " .cur:eq(0)").attr("key");
		            openPage({ url: 'region/add.do?parent_id=' + pId + '&type=' + type, id: 'add_page', title: title, width: '350px', height: '150px' });
		        }
		    } else {
		        informationMsg("请选择选项！");
		    } 
		}

		function edit(type, title, height) {
		    if ($("#list_" + type + " .cur").length > 0) {
		        var $item = $("#list_" + type + " .cur");
		        openPage({ url: 'region/update.do?id=' + $item.attr("key"), title: title, width: '350px', height: height });
		    } else {
		        informationMsg("请选择选项！");
		    }
		}

		function remove(type) {
		    if ($("#list_" + type + " .cur").length > 0) {
		        var selectedId = $("#list_" + type + " .cur").attr("key");
		        art.dialog.confirm('您确定要删除？', function () {
		            $.ajax({
		                type: "POST",
		                url: "admin/region/delete.do?id=" + selectedId,
		                dataType: "json",
		                success: function (data) {
		                    if (data.flag) { 
		                        successMsg("删除成功！", function () {
		                            // 2 刷新列表
		                            $('#spec_form' + type).submit();
		                        });

		                    } else {
		                        errorMsg(data.msg ? data.msg : "删除失败！");
		                    } 
		                },
		                error: function (XMLHttpRequest, textStatus, errorThrown) {
		                    errorMsg("出现错误！");
		                } 
		            });
		        });
		    } else {
		        informationMsg("请选择选项！");
		    }
		}
		
		return {
			create: create,
			edit: edit,
			remove: remove
		};
	})(); 
	</script> 
</gd:Layout>
