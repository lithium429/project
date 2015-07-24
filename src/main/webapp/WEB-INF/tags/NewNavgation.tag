<%@ tag language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ attribute name="nav_text" required="true" type="java.lang.String"%> 
<div class="mb10 crumbs">
	<span class="icon ico_place"></span><b>您当前的位置：</b>${nav_text} 
</div>