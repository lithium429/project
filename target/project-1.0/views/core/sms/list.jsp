<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:Layout>
	<gd:Navgation  addlink="" addr="短信管理  &gt; 短信记录"></gd:Navgation>
	<div class="data_model">
	    <table class="wc100">
	        <tr>
	             <td>
					<div class="data_cont_wrap">
						<form id="spec_form" action="admin/sms/datalist.do"
									data-ajax="true" data-ajax-begin="loadBegin"
									data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
									data-ajax-loading="#loading_img" data-ajax-method="POST"
									data-ajax-mode="replace" data-ajax-update="#data_list"
									novalidate="novalidate">
							<input type="hidden" name="page_index" id="page_index" value="${pageIndex}" />
					        <input type="hidden" name="menu_id" id="menu_id" value="${menu_id}" />
					        <input type="hidden" name="type" id="type" value="${type}" />
					         <div class="mb10 data_cont_wrap_list query_condition">
								<table>
									<tbody>
										<tr>
											<th class="w55">发信人</th>					
											<td>
												<input class="inp_t inp_w150" name="sender_name" id="sender_name" />
											</td>
											<th class="w55">收信人</th>					
											<td>
												<input class="inp_t inp_w150" name="receiver_name" id="receiver_name" />
											</td>
											<th class="w75">收信人手机</th>					
											<td>
												<input class="inp_t inp_w150" name="phone" id="phone" />
											</td>
											<th class="w60">发送时间</th>					
											<td>
												<input id="create_time_min" class="inp_t inp_w150 search_sel ico_date" onclick="WdatePicker({readyOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'create_time_max\')}'})"
					                            onfocus="WdatePicker({readyOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'create_time_max\')}'})"  name="create_time_min"  />
												-
												<input id="create_time_max" class="inp_t inp_w150 search_sel ico_date" onclick="WdatePicker({readyOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'create_time_min\')}'})"
					                            onfocus="WdatePicker({readyOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'create_time_min\')}'})"  name="create_time_max"  />
											</td>
											<th class="w60">状态</th>					
											<td>
												<select id="state" name="state" class="w100">
													<option value="">--请选择--</option>
													<option value="0">成功</option>
													<option value="-1">失败</option>
												</select>
											</td>
											<td>
												<span class="btn btn_pub">
													<input type="submit" id="btn_search" value="查询" />
												</span><span class="ml8 btn btn_base">
													<a href="admin/sms/list.do?menu_id=${menu_id}">重置</a>
												</span>
											</td>
										</tr>
									 </tbody>
							 	 </table>
					        </div>
				     	</form>
				 	</div>
					<div class="data_model wc100 data_list_wrap" id="data_list">
					    <%@ include file="data_list.jsp"%>
				 	</div>
		 		</td>
		 	</tr>
	 	</table>
 	</div>
	<img class="dn" id="loading_img" src="static/img/loading.gif" alt="loading" /> 
	<script type="text/javascript" src="static/js/index.js"></script>
	<script type="text/javascript">
		$(function() {
			//重新发送
			$('.send_again').live('click', function() {
				util.easyAjaxRequest('重新发送',$(this),"重新发送成功")
				return false;
			}); 
			
			//重新发送
			$('#bath_send_again').live('click', function() {
				util.easyAjaxBatchRequest('重新发送',$(this),'','',"重新发送成功")
				return false;
			}); 

			$('.view').live('click', function() {
				var url = $(this).attr('href');
				openPage({
					url : url,
					id : 'view_page',
					title : '查看短信模板 ',
					width : '600px',
					height : '260px'
				});
				return false;
			});

		});
	</script>
</gd:Layout>
