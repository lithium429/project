<%@ tag language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp"%> 
<%@ attribute name="url" required="true" type="java.lang.String"%>
<%@ attribute name="is_show" required="true" type="java.lang.Boolean"%>
<%@ attribute name="text" required="false" type="java.lang.String"%>

<c:if test="${is_show}">
	<span class="btn btn_pub" id="btn_add_span">
		<a href="${url}" id="btn_add">
			<c:choose>
				<c:when test="${!empty text}">
					${text}
				</c:when>
				<c:otherwise>
					添加
				</c:otherwise>
			</c:choose>
		</a>
	</span>	
</c:if>