<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:PopLayout>
	<!-- S 添加信息 -->
	<div class="data_model data_cont_wrap pop_wrap">
		<form id="data_form" method="post" action="admin/user/reset_psw.do" data-ajax="true" data-ajax-begin="showTip" data-ajax-complete="hideTip" data-ajax-success="backToList" data-ajax-success="showError" novalidate="novalidate">
			<input type="hidden" name="id" value="${model.id}" />
			<table class="view wc100">
				<tbody>
					<tr>
						<th class="w20per"></span>用户名：</th>
						<td class="">
							${model.name}
						</td>
					</tr>
					<tr>
						<th><span class="c_red">*</span>新密码：</th>
						<td>
							<input class="inp_t" data-val="true" data-val-length="新密码为6-18个字符！" data-val-length-max="18" data-val-length-min="6" data-val-required="请输入新密码！" id="password" name="password" type="password" />                        
			                      <span class="field-validation-valid" data-valmsg-for="password" data-valmsg-replace="true"></span>
			
						</td>
					</tr>
					<tr>
						<th><span class="c_red">*</span>确认密码：</th>
						<td>
							<input class="inp_t" data-val="true" data-val-equalto="新密码与确认新密码不一样，请重新输入！" data-val-equalto-other="*.password" data-val-length="密码为6-18个字符" data-val-length-max="18" data-val-length-min="6" data-val-required="请再次输入密码！" id="qrpassword" name="qrpassword" type="password" />
			                      <span class="field-validation-valid" data-valmsg-for="qrpassword" data-valmsg-replace="true"></span>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td class="btn_area" colspan="4"><span class="btn btn_pub">
								<input type="submit" value="保存" />
						</span><span class="ml10 btn btn_base"> <input type="button"
								id="btn_pclose" value="取消">
						</span></td>
					</tr>
				</tfoot>
			</table>
			<script type="text/javascript">
				function backToList(result) {
					if (result.flag) {
						successMsg('重置密码成功', function() {
							var opener = art.dialog.open.origin;
							opener.$('#spec_form').submit();
							closeDialog();
						});
					} else {
						errorMsg(result.msg);
					}
				}
			
				function showError(result) {
					errorMsg(result.msg);
				}
				
			</script>
		</form>	
	</div>
</gd:PopLayout> 