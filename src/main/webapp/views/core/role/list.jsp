<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:Layout>
	<gd:Navgation addr="系统管理 &gt; 角色权限"></gd:Navgation>
	<div class="p_rel data_model colum_two clearfix">
		<form id="data_form" method="post" action="admin/role/rolepower.do" data-ajax="true" data-ajax-begin="showTip" data-ajax-complete="hideTip" data-ajax-success="backToList" data-ajax-error="showError">
	        <!-- S left -->
	        <div class="frame_inner_left left_box" id="list">
	        </div>
	        <!-- E left -->
	        <!-- S rightbox -->
	        <div class="frame_right right_box ml100" id="list_rp">
	        </div>
	        <!-- E rightbox -->
		    <div class="dn">
		        <input type="hidden" id="MenuIds" name="MenuIds" />
		        <input type="hidden" id="MenuActionIds" name="MenuActionIds" />
				<input type="hidden" id="role_id" name="role_id" value="" />
		    </div>
		</form>
	</div>
	<form id="spec_form" action="admin/role/datalist.do"
			data-ajax="true" data-ajax-begin="loadBegin"
			data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
			data-ajax-loading="#loading_img" data-ajax-method="POST"
			data-ajax-mode="replace" data-ajax-update="#list"
			novalidate="novalidate">
		<input type="hidden" name="page_index" id="page_index" value="${pageIndex}" />
        <input type="hidden" name="menu_id" id="menu_id" value="${menu_id}" />
   	</form>
	<form id="spec_form_rp" action="admin/role/rolePermission.do"
			data-ajax="true" data-ajax-begin="loadBegin"
			data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
			data-ajax-loading="#loading_img" data-ajax-method="POST"
			data-ajax-mode="replace" data-ajax-update="#list_rp"
			novalidate="novalidate">
		<input type="hidden" id="roleId" name="roleId" value="" />
        <input type="hidden" name="menu_id" id="menu_id1" value="${menu_id}" />
   	</form>
	<img class="dn" id="loading_img" src="static/img/loading.gif" alt="loading" /> 
	<script type="text/javascript" src="static/js/index.js"></script>
	<script type="text/javascript">
		$(function() {
	        $('#spec_form').submit();

	        $.ajaxSetup({
	            cache: false //设置成false将不会从浏览器缓存读取信息
	        });
	        
	        $(".role_ul .role_li").live("click", function () {
	            $(this).addClass("cur").siblings("li").removeClass("cur");
	            var roleId = $(this).attr("span_id");
	            $("#roleId,#role_id").val(roleId);
	            $("#spec_form_rp").submit();
	        }); 
			

			$('.update').live('click', function() {
				var url = $(this).attr('href');
				openPage({
					url : url,
					id : 'update_page',
					title : '修改角色 ',
					width : '420px',
					height : '200px'
				});
				return false;
			}); 

		});
		
		//添加
		var role = (function(){
			function create() {
		        var opts = { 
		        	url: 'admin/role/add.do',
		            id: 'add_role',
		            title: '添加角色',
		            width: '420px',
		            height: '200px'
		        }
		        openPage(opts);
		        return false;
		    }
			
			return { create: create };
		})();
		 
	</script>
</gd:Layout>
