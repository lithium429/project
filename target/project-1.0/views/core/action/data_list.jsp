<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
		<table class="wc100">
		    <thead>
		    	<tr>
			        <th class="check w3per">
			            <input type="checkbox" id="check_all" class="inp_analog" /><span class="icon ico-checkbox"></span>
			        </th>
			        <th class="serial w5per">序号</th>
	   				<th class="w15per">菜单名称</th>
	   				<th class="w50per">动作名称</th>
			        <th class="hd_operate">操作</th>
			  	</tr>
		    </thead>
	    	<tbody>
				<c:forEach items="${list}" var="item" varStatus="vs">  
	            	<tr >
						<td class="check w3per">
		                	<input type="checkbox" name="id" class="inp_analog batch" value="${item.id}" /><span
			                    class="icon ico-checkbox"></span>
			            </td>
			            <td class="serial">
			                ${vs.count+pageInfo.startIndex}
			            </td>
			            <td>
			            	${item.menu_name}
			            </td>
			            <td>
			            	${item.name}
			            </td>
						<td  class="operations">
							<a href="admin/action/update.do?id=${item.id}" class="update">修改</a>&nbsp;
							<a href="admin/action/delete.do?id=${item.id}" class="delete">删除</a>&nbsp;
							<a href="admin/action/view.do?id=${item.id}" class="view">查看</a>
						</td>
					</tr>            
				</c:forEach>
	         	<c:if test="${empty list}">
					<tr>
			        	<td colspan="5" class="t_c">
			                <span class="no-records">暂无数据</span>
			            </td>
			        </tr>
	        	</c:if>
	    	</tbody>
	    </table>
	    <c:if test="${!empty list}">
			<gd:Pager />
			<div class="bot_fun" style="width:300px;">
				<a class="btn" id="batch_delete" href="admin/action/batchdelete.do">批量删除</a>
			</div>
		</c:if>