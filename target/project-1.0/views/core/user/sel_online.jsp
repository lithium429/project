<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<form id="spec_form" action="admin/user/seldatalist.do"
		data-ajax="true" data-ajax-begin="loadBegin"
		data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
		data-ajax-loading="#loading_img" data-ajax-method="POST"
		data-ajax-mode="replace" data-ajax-update="#data_list"
		novalidate="novalidate">
	<input type="hidden" name="page_index" id="page_index" value="${pageIndex}" />
	<input type="hidden" id="key_v1" name="key_v" value="" />
	<input type="hidden" name="is_my" value="${is_my }" />
	<input type="hidden" name="is_online" value="true" />
				<input type="hidden" name="show_phone" value="${show_phone }" />
</form>
<div class="data_model wc100 data_list_wrap dt_query rbox_wrap" id="data_list">
  
</div>
<script type="text/javascript">
    $(function () {
    	$("#key_v1").val($("#key_v").val());
    	$("#spec_form").submit();
	});
</script>