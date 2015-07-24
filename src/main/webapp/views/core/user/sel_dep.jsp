<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<style type="text/css">
	#zTreeContent_1_span{width:120px;}
</style>
<ul id="zTreeContent" class="ztree transcipt_tree leftbox"></ul>
<div class="rightbox">
	<form id="spec_form" action="admin/user/seldatalist.do"
			data-ajax="true" data-ajax-begin="loadBegin"
			data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
			data-ajax-loading="#loading_img" data-ajax-method="POST"
			data-ajax-mode="replace" data-ajax-update="#data_list"
			novalidate="novalidate">
		<input type="hidden" name="page_index" id="page_index" value="${pageIndex}" />
		<input type="hidden" id="key_v1" name="key_v" value="" />
		<input type="hidden" name="is_my" value="${is_my }" />
      	<input type="hidden" class="inp_t" name="dept_id" id="dept_id" value="" />
		<input type="hidden" name="show_phone" value="${show_phone }" />
      	
	</form>
   	<div class="data_model wc100 data_list_wrap dt_query rbox_wrap" id="data_list">
    
	</div>
</div>
<div class="dn" id="TreeSourceContent">${Tree}</div>
<script type="text/javascript">
var selectNumber = 6;
    var setting = {

        async: {
            enable: true,
            url: 'department/getSonNodes.do?type=1',
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
        if (treeNode.showAddBtn && $("#ShowAdd").val()=="true") {
            var aObj = $("#" + treeNode.tId + "_a");
            if ($("#diyBtn_space_" + treeNode.id).length > 0) return;
            var editStr = "<span  class='button add' title='添加' id='diyBtn_space_" + treeNode.id + "' onfocus='this.blur();' > </span>";
            aObj.append(editStr);
            var btn = $("#diyBtn_space_" + treeNode.id);
            if (btn) btn.bind("click", function () {
                var zTree = $.fn.zTree.getZTreeObj("zTreeContent");
                var tempTreeNode = treeNode;
                tempTreeNode = zTree.addNodes(tempTreeNode, { id: (100 + newCount), parentId: treeNode.id, isAdd: true,showDeleteBtn:true,showEditBtn:true,isParent:false,showAddBtn:false, name: "NewNode" + (newCount++) });
                if (tempTreeNode) {
                    zTree.editName(tempTreeNode[0]);
                }
                return false;
            });
        }
    };
    function removeHoverDom(treeId, treeNode) {
        $("#diyBtn_space_" + treeNode.id).unbind().remove();
    };

    // 用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
    function zTreeBeforeRename(treeId, treeNode, newName, isCancel) {
        var currentId = treeNode["id"];
        var parentId = treeNode["parentId"];
      //修改
        if (!treeNode["isAdd"]) {
            $.ajax({
                type: 'POST',
                dataType: 'Json',
                url: 'department/update.do?id=' + currentId + '&name=' + encodeURI(newName),
                cache: false,
                async: false,
                success: function (result) {
                    if (result.flag) {
                        successMsg("修改成功！");
                        //InitTree();
                        $("#spec_form").submit();
                        return true;
                    }
                    else {
                        informationMsg(result.msg);
                        return false;
                    }
                },
                error: function () {
                    errorMsg("修改异常！");
                    return false;
                }
            });
        }
        else {  //添加
        	if(util.isNull(parentId))
       		{
        		parentId="";
       		}
            $.ajax({
                type: 'POST',
                dataType: 'Json',
                url: 'department/add.do?name=' + encodeURI(newName) + '&parent_id=' + parentId,
                cache: false,
                async: false,
                success: function (result) {
                    if (result.flag) {
                        successMsg("添加成功！");
                        //InitTree();
                        return true;
                    }
                    else {
                        informationMsg(result.msg);
                        return false;
                    }
                },
                error: function () {
                    errorMsg("添加异常！");
                    return false;
                }
            });
        }

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
                url: 'department/delete.do?id=' + currentId,
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
        return true;
    }

    // 设置哪些文档类别后面有删除按钮
    function setRemoveBtn(treeId, treeNode) {
        return treeNode.showDeleteBtn && $("#ShowDel").val()=="true";
    }

    // 设置哪些文档类别后面有编辑按钮
    function setRenameBtn(treeId, treeNode) {
        return treeNode.showEditBtn && $("#ShowEdit").val()=="true";
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
        //$(".level0").click(); //ajax加载所有的文档类别
        OpenAllForTree(); //如果放开这个方法，则默认就全部打开
    }

    
    function CheckCallLogin(){
		var cookies=document.cookie;
		var attrCookie=cookies.split(";");
		var callLogin="0";
		for(var i=0;i<attrCookie.length;i++)
		{
			var attr=attrCookie[i].split("=");
			if(attr[0]=="callLogin")
			{
				callLogin=attr[1];
				break;
			}
		}
		return callLogin;
	}
	
    $(function () {
    	$("#key_v1").val($("#key_v").val());
    	
	        //初始化树
	        InitTree();
	        if(util.isNull(parent.call) || parent.call.islogin==0)
	       	{
	        	$("a.hujiao").hide();
	       	}
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
            $("#spec_form").submit();
	        
	      	//这里的bw类是在zTree的js里面我手动添加的，为了方便我找到当前哪个节点被点击并给它绑定事件
	        //所以这个类没有其他意思，不要误解
	        $(".bw").live("click", function () {
	            var treeObj = $.fn.zTree.getZTreeObj("zTreeContent");
	            var node = treeObj.getSelectedNodes()[0];
	            var id = node["id"], name_zh = node["Name_ZH"];
	           
	            $("#dept_id").val(id);
	            $("#spec_form").submit();
	        });
		});
	    
	</script>