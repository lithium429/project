<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<div class="pager" id="pager">
	<span>共：${pageInfo.recordCount} 条 / ${pageInfo.pageCount} 页</span>
	<input type="hidden" value="${pageInfo.pageCount}" id="page_count"/>
	<!--S 首页\上一页 按钮 -->
	<c:choose>
		<c:when test="${pageInfo.pageIndex != 1}">
			<a href="${url}?page_index=1" class="p_sign">&lt;&lt;</a>
			<a href="${url}?page_index=${pageInfo.pageIndex-1}" class="p_sign">&lt;</a>
		</c:when>
		<c:otherwise>
			<span class="disabled p_sign">&lt;&lt;</span>
			<span class="disabled p_sign">&lt;</span>
		</c:otherwise>
	</c:choose>
	<!--E 首页\上一页 按钮 -->
	<!--S 页数列表 -->
	<c:forEach items="${pageInfo.pageList}" var="item">
		<c:choose>
			<c:when test="${item == pageInfo.pageIndex}">
				<span class="current">${item}</span>
			</c:when>
			<c:otherwise>
				<a href="${url}?page_index=${item}">${item}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<!--E 页数列表 -->
	<!--S 下一页\尾页 按钮 -->
	<c:choose>
		<c:when test="${pageInfo.pageIndex != pageInfo.pageCount}">
			<a href="${url}?page_index=${pageInfo.pageIndex+1}" class="p_sign">&gt; </a>
			<a href="${url}?page_index=${pageInfo.pageCount}" class="p_sign">&gt;&gt;</a>
		</c:when>
		<c:otherwise>
			<span class="disabled p_sign">&gt;</span>
			<span class="disabled p_sign">&gt;&gt;</span>
		</c:otherwise>
	</c:choose>
	<!--E 下一页\尾页 按钮 -->
	<!--S 跳转 按钮 -->
	到第 <input type="text" id="text_goto_page" class="goto-page-input" value="${pageInfo.pageIndex}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" 
		size="3" maxlength="3" /> 页 <input type="submit" id="btn_goto_page" class="goto-page-button" value="跳转" />
	<!--E 跳转 按钮 -->
</div>