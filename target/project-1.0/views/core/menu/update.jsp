<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:PopLayout>
	<jsp:attribute name="css">
		<style type="text/css" > 
			#icon_td i{background:url(static/img/home_icon.png) no-repeat;}
			#icon_td i{display:inline-block;*display:inline;zoom:1;width:16px;height:16px;vertical-align:middle;margin:-4px 6px 0 0;}
			#icon_td .p_affair{background-position:-20px -70px;}
			#icon_td .workflow{background-position:-66px -71px;}
			#icon_td .office{background-position:-44px -50px;}
			#icon_td .knowledge{background-position:-66px -50px;}
			#icon_td .portal{background-position:-106px -71px;}
			#icon_td .management{background-position:-106px -49px;}
			#icon_td .hr{background-position:-86px -50px;}
			#icon_td .document{background-position:-44px -72px;}
			#icon_td .roll_manage{background-position:-86px -88px;}
			#icon_td .crm{background-position:0 -88px;}
			#icon_td .project{background-position:-21px -88px;}
			#icon_td .report{background-position:-86px -71px;}
			#icon_td .file{background-position:-66px -88px;}
			#icon_td .comm{background-position:-105px -88px;}
			#icon_td .mail{background-position:0 -132px;}
			#icon_td .hr_file{background-position:0 -108px;}
			#icon_td .os_setting{background-position:0 -49px;}
			#icon_td .money{background-position:-20px -49px;}
			#icon_td .flow{background-position:-62px -108px;}
			#icon_td .meeting{background-position:-93px -173px;}
			#icon_td .books{background-position:-76px -173px;}
			#icon_td .arrange{background-position:-62px -134px;}
			#icon_td .food{background-position:-92px -196px;}
			#icon_td .news{background-position:-82px -108px;}
			#icon_td .db{background-position:-40px -152px;}
			#icon_td .save{background-position:1px -151px;}
			#icon_td .suc{background-position:-83px -152px;}
			#icon_td .car{background-position:-38px -173px;}
			#icon_td .video{background-position:-58px -194px;}
			#icon_td .print{background-position:0 -194px;}
			#icon_td .shop{background-position:0 -174px;}
			#icon_td .notice{background-position:-22px -108px;}
			#icon_td .map{background-position:-40px -108px;}
			#icon_td .time{background-position:-83px -132px;}
			#icon_td .date{background-position:-40px -194px;}
			#icon_td .file_manage{background-position:-18px -174px;}
			#icon_td .modify{background-position:-40px -132px;}
		</style>
	</jsp:attribute>
	<jsp:body>
		<!-- S 添加信息 -->
		<div class="data_model data_cont_wrap pop_wrap"> 
			<form id="data_form" method="post" action="admin/menu/update.do" data-ajax="true" data-ajax-begin="showTip" data-ajax-complete="hideTip" data-ajax-success="backToList" data-ajax-error="showError">
				<input type="hidden" name="layer" value="${model.layer }" id="layer">
				<input type="hidden" name="child_ids" value="${model.child_ids }" id="child_ids">
				<input type="hidden" name="id" value="${model.id }" id="id">
				<input type="hidden" id="eicon_class" name="icon_class" value="${model.icon_class}" />
				<table class="view wc100">
					<tbody>
						<tr>
							<th class="w18per"><span class="c_red">*</span>名称：</th>
							<td>
								<input type="text" class="inp_t" data-val="true"
								data-val-required="请输入名称！" id="name" name="name"
								value="${model.name}" />
								<span class="field-validation-error" data-valmsg-for="name"
										data-valmsg-replace="true"></span>
							</td>
						</tr>
						<tr >
							<th>父级菜单</th>
							<td>
							<c:choose>
								<c:when test="${empty model.child_ids}">
									<select id="parent_id" name="parent_id" >
										<option value="">--请选择--</option>
										<c:forEach var="item" items="${menuList}" varStatus="vs">
											<option layer="${item.layer }" value="${item.id}" <c:if test="${item.id eq model.parent_id}">selected="selected"</c:if> >
												${item.name}
											</option>
										</c:forEach>
									</select>
								</c:when>
								<c:otherwise>
									<c:forEach var="item" items="${menuList}" varStatus="vs">
										<c:if test="${item.id eq model.parent_id}">${item.name}</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
								
							</td>
						</tr>
						<tr>
							<th><span class="c_red">*</span>菜单url：</th>
							<td>
								<input type="text" class="inp_t" data-val="true"
								data-val-remote-url="admin/menu/validateUrl.do" 
								data-val-remote-type="POST" 
								data-val-remote-additionalfields="*.url,*.id" 
								data-val-remote="该菜单url已存在！" 
								data-val-required="请输入菜单url！" id="url" name="url"
								value="${model.url}" />
								<span class="field-validation-error" data-valmsg-for="url"
										data-valmsg-replace="true"></span>
							</td>
						</tr>
						<tr>
							<th><span class="c_red">*</span>排序：</th>
							<td>
								<input type="text" class="inp_t" data-val="true"
								 data-val-regex="排序只能够输入整数！" data-val-regex-pattern="^\d+$"
								data-val-required="请输入排序！" id="sort" name="sort"
								value="${model.sort}" />
								<span class="field-validation-error" data-valmsg-for="sort"
										data-valmsg-replace="true"></span>
							</td>
						</tr>
						<c:if test="${model.layer == 1}">
							<tr id="icon_tr">
								<th><span class="c_red">*</span>图标：</th>
								<td id="icon_td">
									<select id="icon_class">
										<option value="mail">内部邮件</option>
										<option value="os_setting">系统设置</option>
										<option value="date">排班管理</option>
										<option value="books">通讯录</option>
										<option value="meeting">会议管理</option>
										<option value="p_affair">个人事务</option>
										<option value="workflow">工作流</option>
										<option value="office">行政办公</option>
										<option value="knowledge">知识管理</option>
										<option value="portal">智能门户</option>
										<option value="management">管理中心</option>
										<option value="hr">人力资源</option>
										<option value="document">公文管理</option>
										<option value="roll_manage">档案管理</option>
										<option value="crm">CRM系统</option>
										<option value="project">项目管理</option>
										<option value="report">报表系统</option>
										<option value="comm">交流园地</option>
										<option value="file">附件程序</option> 
									</select>
									<i id="icon_show" class="${model.icon_class}"></i>
								</td>
							</tr>
						</c:if>
						<tr>
							<th>是否启用</th>
							<td>
								<label><input type="radio" value="true" class="is_enable" <c:if test="${model.is_enable }">checked="checked"</c:if> name="is_enable"  />是</label>
								<label><input type="radio" value="false" class="is_enable ml30" <c:if test="${!model.is_enable }">checked="checked"</c:if> name="is_enable"  />否</label>
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td></td>
							<td class="ptb10">
								<span class="btn btn_pub">
									<input id="btn_save" type="submit" value="保存" />
								</span>
								<span class="ml10 btn btn_base">
									<input type="button" id="btn_pclose" value="取消" />
								</span>
							</td>
						</tr>
					</tfoot>
				</table>
			</form>
		</div>
		<!-- E 添加信息 -->
		<script type="text/javascript">
			$(function(){
				//父级菜单change事件
				$("#parent_id").change(function(){
					var $this = $(this), value = $this.val();
					if(!util.isEmpty(value)){
						var layer = $this.find("option:selected").attr("layer");
						$("#layer").val(Number(layer)+1);
						$('#icon_tr').hide();
						$('#eicon_class').val('');
					}else{
						$("#layer").val(1);
						$('#icon_tr').show();
					}
				});
				
				iconClass.init();
			});
			
			// 菜单图标
			var iconClass = (function(){
				var eiconClass = $('#eicon_class').val();
				function init(){
					$('#icon_class').find('option').each(function(index, item){
						if(item.value === eiconClass){
							$(item).attr('selected', 'selected');
						}
					})
					
					$('#icon_class').change(function(){
						$('#icon_show').attr('class', this.value);
					});
					
					$('#btn_save').click(function(){
						var iconClass = $('#icon_class').val();
						$('#eicon_class').val(iconClass);
					});
				}
				
				return { init: init };
			})();
			
			function backToList(result) {
				if (result.flag) {
					successMsg(result.msg ? result.msg : "保存成功！", function() {
						var opener = art.dialog.open.origin;
						opener.$('#spec_form').submit();
						closeDialog();
					});
				} else {
					errorMsg(result.msg ? result.msg : "保存失败！");
				}
			}
	
			function showError() {
				errorMsg("修改用户时出错！");
			} 
		</script>
	</jsp:body>
</gd:PopLayout>