<%@ tag language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ attribute name="addr" required="true" type="java.lang.String"%>
<%@ attribute name="addlink" required="false" type="java.lang.String"%>
<%@ attribute name="addText" required="false" type="java.lang.String"%>
<%@ attribute name="addShow" required="false" type="java.lang.Boolean"%>
<div class="mb10 crumbs">
	<span class="icon ico_place"></span><b>您当前的位置：</b>${addr}
	<c:if test="${!empty addlink && (empty addShow || addShow)}">
		<div class="model_top_fun">
			<span class="btn btn_pub"><a href="${addlink}" id="btn_add">
			<c:choose>
				<c:when test="${!empty addText}">
					${addText}
				</c:when>
				<c:otherwise>
					添加
				</c:otherwise>
			</c:choose>
			</a></span>
		</div>
	</c:if>
</div>