<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<div class="leftbox">
<h2 class="inside_group">角色列表</h2>
<ul class="list roleUl">
	<c:forEach items="${list}" var="item" varStatus="vs">  
		<li class="roleLi ${vs.index==0?'cur':''}" spanId="${item.id }">
		${item.name }
		</li>
	</c:forEach>
</ul>
</div>
<div class="rightbox">
	<form id="spec_form" action="admin/user/seldatalist.do"
			data-ajax="true" data-ajax-begin="loadBegin"
			data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
			data-ajax-loading="#loading_img" data-ajax-method="POST"
			data-ajax-mode="replace" data-ajax-update="#data_list"
			novalidate="novalidate">
		<input type="hidden" name="page_index" id="page_index" value="${pageIndex}" />
		<input type="hidden" id="key_v1" name="key_v" value="" />
		<input type="hidden" name="is_my" value="${is_my }" />
      	<input type="hidden" class="inp_t" name="role_id" id="role_id" value="" />
		<input type="hidden" name="show_phone" value="${show_phone }" />
	</form>
   	<div class="data_model wc100 data_list_wrap dt_query rbox_wrap" id="data_list">
    
	</div>
</div>
<script type="text/javascript">
    $(function () {
    	$("#key_v1").val($("#key_v").val());
    	Init();
    	
    	 $(".roleUl .roleLi").click(function () {
	            var $this = $(this);
	            
	            $this.addClass("cur").siblings("li").removeClass("cur");
	            $("#role_id").val($this.attr("spanId"));
	            $("#spec_form").submit();
	        });
		});

    function Init() {
        if ($(".roleUl .cur").length > 0) {
            var $item = $(".roleUl .cur");
            $("#roleId,#role_id").val($item.attr("spanId"));
            $("#spec_form").submit();
        }
    }
	</script>