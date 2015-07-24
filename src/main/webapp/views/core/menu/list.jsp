<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:Layout>
	<gd:Navgation addlink="admin/menu/add.do" addr="菜单管理 &gt; 菜单管理"></gd:Navgation>
	<div class="data_model">
		<div class="data_cont_wrap">
			<form id="spec_form" action="admin/menu/datalist.do"
						data-ajax="true" data-ajax-begin="loadBegin"
						data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
						data-ajax-loading="#loading_img" data-ajax-method="POST"
						data-ajax-mode="replace" data-ajax-update="#data_list"
						novalidate="novalidate">
				<input type="hidden" name="page_index" id="page_index" value="${pageIndex}" />
		        
	     	</form>
	 	</div>
		<div class="data_model wc100 data_list_wrap no_query" id="data_list">
		    <%@ include file="data_list.jsp"%>
	 	</div>
 	</div>
	<img class="dn" id="loading_img" src="static/img/loading.gif" alt="loading" /> 
	<script type="text/javascript" src="static/js/index.js"></script>
	<script type="text/javascript">
		$(function() {
			
			$('#btn_add,#btn_add_bottom').live('click', function() {
				var url = $(this).attr('href');
				openPage({
					url : url,
					id : 'add_page',
					title : '添加菜单 ',
					width : '500px',
					height : '260px'
				});
				return false;
			});
			
			$('.action').live('click', function() {
				var url = $(this).attr('href');
				openPage({
					url : url,
					id : 'action_page',
					title : '菜单动作 ',
					width : '800px',
					height : '560px'
				});
				return false;
			});

			$('.update').live('click', function() {
				var url = $(this).attr('href');
				openPage({
					url : url,
					id : 'update_page',
					title : '修改菜单 ',
					width : '500px',
					height : '260px'
				});
				return false;
			});

			$('.view').live('click', function() {
				var url = $(this).attr('href');
				openPage({
					url : url,
					id : 'view_page',
					title : '查看菜单 ',
					width : '500px',
					height : '200px'
				});
				return false;
			});

		});
	</script>
</gd:Layout>
