<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:Layout> 
	<style type="text/css">
		#zTreeContent_1_span{width:120px;}
	</style>
	<gd:Navgation addShow="${gdf:judgeRoleMenu(roleMenuList,'添加')}" addlink="admin/user/add.do" addr="系统管理 &gt; 组织架构 "></gd:Navgation>
	<div class="data_model colum_two clearfix">
		<ul id="zTreeContent" class="ztree transcipt_tree frame_inner_left leftbox"></ul>
		<div class="rightbox">
			<form id="spec_form" action="admin/user/datalist.do"
						data-ajax="true" data-ajax-begin="loadBegin"
						data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
						data-ajax-loading="#loading_img" data-ajax-method="POST"
						data-ajax-mode="replace" data-ajax-update="#data_list"
						novalidate="novalidate">
		        <input type="hidden" name="menu_id" id="menu_id" value="${menu_id}" />
				<input type="hidden" name="page_index" id="page_index" value="${pageIndex}" />
				<input type="hidden" name="role_ids" id="role_ids" value="" />
				<input type="hidden" name="department_id" id="department_id" value="" />
		        <div class="mb10 data_cont_wrap_list query_condition">
					<table>
						<tbody>
							<tr>
								<th class="w55">用户</th>					
								<td>
									<input class="inp_t inp_w150" name="key" id="key" />
								</td>
								<th class="w60">角色</th>					
								<td>
									<select id="role_id" multiple="multiple" name="role_id" class="w100">
										<option value="">--请选择--</option>
										<c:forEach items="${roleList }" var="item" varStatus="vs">
											<option value="${item.id }">${item.name }</option>
										</c:forEach>
									</select>
								</td>
								<th class="w60">状态</th>					
								<td>
									<select id="state" name="state" class="w100">
										<option value="">全部</option>
										<option value="2">冻结</option>
										<option value="1">正常</option>
									</select>
								</td>
								<td>
									<span class="btn btn_pub">
										<input type="submit" id="btn_search" value="查询" />
									</span><span class="ml8 btn btn_base">
										<a href="admin/user/list.do?menu_id=${menu_id}">重置</a>
									</span>
								</td>
							</tr>
						 </tbody>
				 	 </table>
		        </div>
	     	</form>
	     	<div class="data_model wc100 data_list_wrap dt_query rbox_wrap" id="data_list">
			    <%@ include file="data_list.jsp"%>
		 	</div>
	 	</div>
 	</div>
	<img class="dn" id="loading_img" src="static/img/loading.gif" alt="loading" /> 
	<script type="text/javascript" src="static/js/index.js"></script>
	<div class="dn" id="TreeSourceContent">${Tree}</div>
	<input type="hidden" id="Hidden_PId" name="Hidden_PId" value="" />
	<input type="hidden" id="ShowAdd" value="${gdf:judgeRoleMenu(roleMenuList,'部门添加')}" />
	<input type="hidden" id="ShowEdit" value="${gdf:judgeRoleMenu(roleMenuList,'部门修改')}" />
	<input type="hidden" id="ShowDel" value="${gdf:judgeRoleMenu(roleMenuList,'部门删除')}" />
<script type="text/javascript">
	var selectNumber = 6;
    var setting = { 
        async: {
            enable: true,
            url: 'admin/department/getSonNodes.do',
            autoParam: ["id"],
            dataType: "text",
            type: "post",
            dataFilter: null
        },
        callback: {
            beforeAsync: beforeAsync,
            onAsyncSuccess: onAsyncSuccess,
            onAsyncError: onAsyncError,
            beforeRemove: zTreeBeforeRemove,
            beforeEditName: zTreeBeforeEditName,
            beforeRename: zTreeBeforeRename
        },

        data: {
            simpleData: {
                enable: true,
                //idKey: "id",
                //pIdKey: "pId",//这2个参数可以不指定，因为默认就是这2个
                rootPId: 0
            }
        },
        edit: {
            drag: {
                isMove: false,
                isCopy: false
            },
            editNameSelectAll: true,
            enable: true,
            removeTitle: '删除',
            renameTitle: '修改',
            showRemoveBtn: setRemoveBtn,
            showRenameBtn: setRenameBtn
        },
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom
        }
    };

    var newCount = 1;
    function addHoverDom(treeId, treeNode) {
        var aObj = $("#" + treeNode.tId + "_a"); 
        var editStr = ""; 
        if(treeNode["showAddBtn"] && $("#ShowAdd").val()=="true")
       	{
        	if ($("#diyBtn_space_" + treeNode.id).length > 0) return;
            editStr =editStr + "<span  class='button add' title='添加' id='diyBtn_space_" + treeNode.id + "' onfocus='this.blur();' > </span>";
       	}
        if (treeNode["showEditBtn"] && $("#ShowEdit").val()=="true") {
            if ($("#diyBtn1_space_" + treeNode.id).length > 0) return;
            editStr = editStr +"<span  class='button edit' title='修改' id='diyBtn1_space_" + treeNode.id + "' onfocus='this.blur();' > </span>";
        }
        aObj.append(editStr);
        var btn = $("#diyBtn_space_" + treeNode.id), btn1 = $("#diyBtn1_space_" + treeNode.id);
        if (btn) btn.bind("click", function () {
            var zTree = $.fn.zTree.getZTreeObj("zTreeContent");
            var tempTreeNode = treeNode;
            if(util.isNull(treeNode.parentId)){
            	$("#Hidden_PId").val(treeNode.parentId);
            }else{
            	$("#Hidden_PId").val('');
            }
            
            var opts = { url: 'admin/department/add.do',
                id: 'addUser',
                title: '添加部门',
                width: '360px',
                height: '120px'
            }
            openPage(opts);
            return false;
        });
        if (btn1) btn1.bind("click", function () {
            var zTree = $.fn.zTree.getZTreeObj("zTreeContent");
            var tempTreeNode = treeNode;
            $("#Hidden_PId").val(treeNode.parentId);
            var opts = { url: 'admin/department/update.do?id=' + treeNode.id,
                    id: 'addUser',
                    title: '修改部门',
                    width: '360px',
                    height: '120px'
                }
            openPage(opts);
            return false;
        });
    };
    function removeHoverDom(treeId, treeNode) {
        $("#diyBtn_space_" + treeNode.id).unbind().remove();
    };

    // 用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
    function zTreeBeforeRename(treeId, treeNode, newName, isCancel) {
        var currentId = treeNode["id"];
        var parentId = treeNode["parentId"];
      //修改 
    }

    //删除节点前触发的事件 用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作
    function zTreeBeforeRemove(treeId, treeNode) {
        var currentId = treeNode["id"];
        var tId = treeNode["tId"];
        var flag = true;
        confirmDialog("您确定要删除吗？", function () {
            $.ajax({
                type: 'POST',
                dataType: 'Json',
                url: 'admin/department/delete.do?id=' + currentId,
                cache: false,
                async: false,
                success: function (result) {
                    if (result.flag) {
                        var treeObj = $.fn.zTree.getZTreeObj("zTreeContent");
                        treeObj.removeNode(treeNode);
                        successMsg("删除成功！");
                        $("#" + tId).remove();
                        $('#spec_form').submit();
                    }
                    else {
                        informationMsg(result.msg);
                        flag = false;
                    }
                },
                error: function () {
                    errorMsg("删除异常！");
                    flag = false;
                }
            });
        });
        return false;
    }

    // 设置哪些节点可以有编辑按钮
    function zTreeBeforeEditName(treeId, treeNode) {
        return false;
    }

    // 设置哪些文档类别后面有删除按钮
    function setRemoveBtn(treeId, treeNode) {
        return treeNode.showDeleteBtn && $("#ShowDel").val()=="true";
    }

    // 设置哪些文档类别后面有编辑按钮
    function setRenameBtn(treeId, treeNode) {
        return false;
    }

    var curStatus = "init", curAsyncCount = 0, asyncForAll = false, goAsync = false;

    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i = 0, l = childNodes.length; i < l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }

    // 异步加载前触发的方法
    function beforeAsync() {
        curAsyncCount++;
    }

    // 异步加载成功后触发的方法
    function onAsyncSuccess(event, treeId, treeNode, msg) {
        curAsyncCount--;
        if (curStatus == "expand") {
            expandNodes(treeNode.children);
        } else if (curStatus == "async") {
            asyncNodes(treeNode.children);
        }

        if (curAsyncCount <= 0) {
            if (curStatus != "init" && curStatus != "") {
                //$("#demoMsg").text((curStatus == "expand") ? demoMsg.expandAllOver : demoMsg.asyncAllOver);
                asyncForAll = true;
            }
            curStatus = "";
        }
    }

    // 异步加载【失败】的时候触发的方法
    function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
        curAsyncCount--;

        if (curAsyncCount <= 0) {
            curStatus = "";
            if (treeNode != null) asyncForAll = true;
        }
    }

    //检查是否已经加载过了。
    function check() {
        if (curAsyncCount > 0) {
            //alert("已经加载完毕，不再重新加载！");
            return false;
        }
        return true;
    }

    //加载所有节点
    function expandNodes(nodes) {
        if (!nodes) return;
        curStatus = "expand";
        var zTree = $.fn.zTree.getZTreeObj("zTreeContent");
        for (var i = 0, l = nodes.length; i < l; i++) {
            zTree.expandNode(nodes[i], true, false, false);
            if (nodes[i].isParent && nodes[i].zAsync) {
                expandNodes(nodes[i].children);
            } else {
                goAsync = true;
            }
        }
    }

    //全部打开树的方法
    function OpenAllForTree() {
        if (!check()) {
            return;
        }
        var zTree = $.fn.zTree.getZTreeObj("zTreeContent");
        if (asyncForAll) {
            zTree.expandAll(true);
        } else {
            expandNodes(zTree.getNodes());
            if (!goAsync) {
                curStatus = "";
            }
        }
    }

    // 初始化树
    function InitTree() {
        //初始化树
        var content = eval('(' + $("#TreeSourceContent").html() + ')');
        $.fn.zTree.init($("#zTreeContent"), setting, content);
        //获取当前zTree对象，后面的参数字符串指的是tree容器的Id
        var treeObj = $.fn.zTree.getZTreeObj("zTreeContent");
        $(".level0").click(); //ajax加载所有的文档类别
        OpenAllForTree(); //如果放开这个方法，则默认就全部打开
    } 
	
    $(function () {
    	
	        //初始化树
	        InitTree(); 
	        
	        //全部折叠
	        $("#btnCollapseAll").click(function () {
	            var treeObj = $.fn.zTree.getZTreeObj("zTreeContent");
	            treeObj.expandAll(false);
	        });
	
	        //全部展开
	        $("#btnExpendAll").click(function () {
	            if (!check()) {
	                return;
	            }
	            var zTree = $.fn.zTree.getZTreeObj("zTreeContent");
	            if (asyncForAll) {
	                zTree.expandAll(true);
	            } else {
	                expandNodes(zTree.getNodes());
	                if (!goAsync) {
	                    curStatus = "";
	                }
	            }
	        });
	        
	      	//这里的bw类是在zTree的js里面我手动添加的，为了方便我找到当前哪个节点被点击并给它绑定事件
	        //所以这个类没有其他意思，不要误解
	        $(".bw").live("click", function () {
	            var treeObj = $.fn.zTree.getZTreeObj("zTreeContent");
	            var node = treeObj.getSelectedNodes()[0];
	            var id = node["id"], name_zh = node["Name_ZH"];
	          
	            $("#department_id").val(id);
	            $("#spec_form").submit();
	        });
	      
			// 添加
			$('#btn_add').live('click',function(){
				var url = $(this).attr('href');
				openPage({
					url:url,
					id:'add_page',
					title:'添加用户',
					width:'850px',
					height:'350px'
				});
				return false;
			});
			
			// 修改
			$('.update').live('click',function(){
				var url = $(this).attr('href');
				openPage({
					url:url,
					id:'update_page',
					title:'修改用户',
					width:'850px',
					height:'350px'
				});
				return false;
			});
			
			// 查看
			$('.view').live('click',function(){
				var url = $(this).attr('href');
				openPage({
					url:url,
					id:'view_page',
					title:'查看用户',
					width:'600px',
					height:'350px'
				});
				return false;
			}); 
			
			$('.changeState').live('click',function(){
				var target = $(this), msg='', smsg='';
				if(target.hasClass('qy')){
					msg = '启用';
					smsg = '启用成功';
				}
				else{
					msg = '禁用';
					smsg = '禁用成功';
				}
				util.easyAjaxRequest(msg, target, smsg);
				return false;
			});
			 
			
			$('#batch_change, #batch_change1, #batch_reset').live('click',function(){
				var target = $(this), msg = '', smsg = '';
				if(target.hasClass('qy')){
					msg = '启用';
					smsg = '启用成功';
				}
				else if(target.hasClass('jy')){
					msg = '禁用';
					smsg = '禁用成功';
				}
				else{
					msg = '重置密码';
					smsg = '重置密码成功';
				}
				util.easyAjaxBatchRequest(msg, target, null, null, smsg);
				return false;
			});
			
			$('#role_id').multiSelect({ oneOrMoreSelected: '*' });
			$('#role_id').blur(setRoleIds);
		});
	    
	    function setRoleIds() {
		    var roleIdObj = $('#role_id'),
		    values = roleIdObj.selectedValuesString();
		    $('#role_ids').val(values);
		}
	</script>
</gd:Layout>
