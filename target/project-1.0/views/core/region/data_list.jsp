<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<div class="datalistbox">
	<table class="OptionTable" key="${type }">
	    <thead>
	    	<tr>
		        <th class="" width="100">
	            	${type_str }
		        </th>
		  	</tr>
	    </thead>
	   	<tbody>
			<c:forEach items="${list}" var="item" varStatus="vs">  
	           	<tr v="${type }" class="list${type }Tr listTr <c:if test="${vs.index==0 }">cur</c:if>"  key="${item.id}">
			       <td class="NameTd">
			       		${item.name}
			       </td>
				</tr>               
			</c:forEach>
		   	<c:if test="${empty list}">
				<tr id="EmptyTr">
			       <td class="t_c">
		               <span class="no-records">暂无数据</span>
		           </td>
			    </tr>
		   	</c:if>
	   	</tbody>
	</table>
</div>

<script type="text/javascript">
$(function () {
    var key = '${type }';
    var ckey = Number(key) + 1;
    var key_str = '${type_str }';

    if (ckey < 5) {
        if ($("#list_" + key + " .cur").length > 0) {
            var $item = $("#list_" + key + " .cur");
            $("#parent_id" + ckey).val($item.attr("key"));
        } else {
            $("#parent_id" + ckey).val('0');
        }
        $('#spec_form' + ckey).submit();
    }

    // 【双击】【行】触发的事件
    $(".list" + key + "Tr").dblclick(function () {
        var $this = $(this);
        $this.addClass("cur").siblings("tr").removeClass("cur");
        Edit(key, key_str, '130px');
    });
});
</script>