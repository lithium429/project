<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.xz.base.utils.WebUtil" %>
<%
  String baseUrl = WebUtil.getBasePath(request);
  request.setAttribute("baseUrl", baseUrl);
%>
<html>
<head>
  <title>test define</title>
  <script src="../../../static/js/jquery-1.7.2.min.js" type="text/javascript"></script>
  <script src="../../../static/js/artDialog/dialog-min.js" type="text/javascript"></script>
  <link rel="stylesheet" href="../../../static/js/artDialog/skins/ui-dialog.css">
</head>

<style type="text/css">
  .tr a{
    padding-left: 10px;
  }
  .tr td{
    padding-left: 10px;
  }
  a{
    text-decoration: none;

  }
</style>
<body>
<div id="define">


</div>

<a href="javascript:void(0);" onclick="addModel();">添加模型</a>
<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>
<h5>模型信息</h5>
<table id="table">
  <thead>
  <tr>
    <th>模型id</th>

    <th>模型名称</th>

    <th>模型代码</th>

    <th>版本</th>

    <th>创建时间</th>

    <th>更新时间</th>

    <th>操作</th>
  </tr>

  </thead>
  <tbody id="tr" class="tr">


  </tbody>
</table>

<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>
<h5>部署信息</h5>
<table id="tab2">
  <thead>
  <tr>
    <th>
      流程定义
    </th>
    <th>
      模型名称
    </th>
    <th>
      模型代码
    </th>
    <th>
      部署版本
    </th>
    <th>
      状态
    </th>
    <th>
      操作
    </th>
  </tr>
  </thead>
  <tbody id="tr2" class="tr">

  </tbody>
</table>

<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>
<h5>流程实例</h5>
<table id="tab3">
  <thead>
  <tr>
    <th>
      实例id
    </th>
    <th>
      流程定义
    </th>
    <th>
      环节
    </th>
    <th>
      状态
    </th>
    <th>
      操作
    </th>
  </tr>
  </thead>
  <tbody id="tr3" class="tr">

  </tbody>

</table>

<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>
<h5>流程任务</h5>
<table id="tab4">
  <thead>
  <tr>
    <th>
      任务id
    </th>
    <th>
      任务名称
    </th>
    <th>
      负责人
    </th>
    <th>
      操作
    </th>
  </tr>
  </thead>
  <tbody id="tr4" class="tr">

  </tbody>
</table>

<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>
<h5>历史任务</h5>
<table id="tab5">
  <thead>
  <tr>
    <th>
      任务id
    </th>
    <th>
      任务名称
    </th>
    <th>
      负责人
    </th>

  </tr>
  </thead>
  <tbody id="tr5" class="tr">

  </tbody>
</table>


<script type="text/javascript">
  $(function(){
    // console.log("${baseUrl}");
    getDefineInfo();
    getDeployInfo();
    getFlowInfo();
    getTaskInfo();

    getHisTaskInfo();
    //getHisInst();

    <%--$.ajax({--%>
    <%--url:"${baseUrl}acti/history/flow/list",--%>
    <%--type:"post",--%>
    <%--data:"page=1&rows=10",--%>
    <%--success: function (data) {--%>
    <%--console.log(data.list);--%>
    <%--}--%>
    <%--})--%>

  })

  var getDefineInfo=function(){
    $.ajax({
      type:"POST",
      url:"${baseUrl}acti/model/list",
      data:"page=1&rows=10",
      success:function(data){
        var str="";
        for(var i in data.list){
          str+="<tr><td>"+data.list[i].id+"</td>"+
                  "<td>"+data.list[i].name+"</td>"+
                  "<td>"+data.list[i].key+"</td>"+
                  "<td>"+data.list[i].revision+"</td>"+
                  "<td>"+new Date(data.list[i].createTime).Format("yyyy-MM-dd hh:mm:ss")+"</td>"+
                  "<td>"+new Date(data.list[i].lastUpdateTime).Format("yyyy-MM-dd hh:mm:ss")+"</td>"+
                  "<td>"+"<a href='javascript:void(0);' onclick=editor('"+data.list[i].id+"')>编辑</a>"+"<a href='javascript:void(0);' onclick=deploy('"+data.list[i].id+"')>部署</a>"+
                  "<a href='javascript:void(0);' onclick=doDelete('"+data.list[i].id+"')>删除</a>"+"<a href='javascript:void(0);' onclick=doExport('"+data.list[i].id+"')>导出</a>"+
                  "<a href='javascript:void(0);' onclick=copy('"+data.list[i].id+"')>复制</a>"+"</td></tr>";

        }
        $("#tr").empty().append(str);
      }
    })
  }


  function editor(id){
    window.open("${baseUrl}acti/model/modeler?modelId="+id);
  }

  function deploy(id){
    $.ajax({
      url:"${baseUrl}acti/model/deploy/"+id,
      type:"post",
      success: function (data) {
        console.log(data);
        if(data.errcode==0){
          getDeployInfo();
        }

      }

    })
  }

  function doExport(id){

    window.open("${baseUrl}acti/model/export/"+id);

  }

  function doDelete(id){
    $.ajax({
      url:"${baseUrl}acti/model/delete/"+id,
      type:"post",
      success: function (data) {
        console.log(data);
        getDefineInfo();
      }
    })
  }

  function copy(id){
    var d = dialog({
      title: '参数',
      content: '<table ><tr><td>key:</td><td><input autofocus id="copymodkey"/></td></tr>'+
      "<tr><td>name:</td><td><input id='copymodname' /></td></tr></table>",
      button:[{
        value:"确定",
        callback:function(){
          $.ajax({
            url:"${baseUrl}acti/model/copymodel",
            type:"post",
            data:"categoryId=0001&name="+$("#copymodname").val()+"&key="+$("#copymodkey").val()+"&modelId="+id,
            success: function (data) {
              if(data.errcode==0){
                getDefineInfo();
              }else{
                alert(data.errmsg);
              }
            }
          })
        }
      }]
    });
    d.showModal();
  }


  var getDeployInfo=function(){
    $.ajax({
      url:"${baseUrl}acti/deploy/list",
      type:"post",
      data:"page=1&rows=10",
      success: function (data) {
        var jsonData=eval("["+data+"]");
        var data=jsonData[0];
        //console.log(jsonData[0]);
        var str="";
        for (var i in data.list){
          str+="<tr>"+"<td>"+data.list[i].deploymentId+"</td><td>"+data.list[i].name+"</td><td>"+
                  data.list[i].key+"</td><td>"+data.list[i].version+"</td>";
          if(data.list[i].suspensionState==1){
            str+="<td>"+"激活&nbsp;&nbsp;"+"<a href='javascript:void(0);' onclick=suspend('"+data.list[i].id+"')>挂起</td>";
          }else{
            str+="<td>"+"挂起&nbsp;&nbsp;"+"<a href='javascript:void(0);' onclick=active('"+data.list[i].id+"')>激活</td>";
          }
          str+="<td><a href='javascript:void(0);' onclick=graph('"+data.list[i].id+"')>流程图</a>"+
                  "<a href='javascript:void(0);' onclick=toXml('"+data.list[i].id+"')>XML</a>"+
                  "<a href='javascript:void(0);' onclick=deldeploy('"+data.list[i].deploymentId+"')>删除</a></td></tr>";

        }
        $("#tr2").empty().append(str);
      }
    })
  }

  function suspend(id){
    $.ajax({
      url:"${baseUrl}acti/deploy/suspend/"+id,
      type:"post",
      success: function (data) {
        console.log(data);
        if(data.errcode==0){
          getDeployInfo();
        }
      }
    })
  }

  function active(id){
    $.ajax({
      url:"${baseUrl}acti/deploy/active/"+id,
      type:"post",
      success: function (data) {
        console.log(data);
        if(data.errcode==0){
          getDeployInfo();
        }
      }
    })
  }

  function deldeploy(id){
    $.ajax({
      url:"${baseUrl}acti/deploy/delete/"+id,
      type:"post",
      success: function (data) {
        if(data.errcode==0){
          getDeployInfo();
        }
      }
    })
  }

  function graph(id){
    window.open("${baseUrl}acti/deploy/graph/"+id);
  }

  function toXml(id){
    window.open("${baseUrl}acti/deploy/xml/"+id);
  }


  function getFlowInfo(){
    $.ajax({
      url:"${baseUrl}acti/flow/list",
      type:"post",
      data:"page=1&rows=10",
      success: function (data) {
        var jsonData=eval("["+data+"]");
        var list=jsonData[0].list;
        var str="";
        for(var i in list){
          str+="<tr><td>"+list[i].processInstanceId+"</td>"+"<td>"+list[i].processDefinitionId+"</td><td>"+
                  list[0].activityId+"</td>";
          if(list[i].suspensionState==1){
            str+="<td>"+"激活&nbsp;"+"<a href='javascript:void(0);' onclick=flowSuspend('"+list[i].processInstanceId+"')>挂起</td>";
          }else{
            str+="<td>"+"挂起&nbsp;"+"<a href='javascript:void(0);' onclick=flowActive('"+list[i].processInstanceId+"')>激活</td>";
          }
          str+="<td><a href='javascript:void(0);' onclick=showhis('"+list[i].processInstanceId+"')>历史</a>&nbsp;<a href='javascript:void(0);' onclick=flowdel('"+list[i].processInstanceId+"')>删除</a></td></tr>";
        }
        $("#tr3").empty().append(str);
      }
    })
  }

  function flowSuspend(id){
    $.ajax({
      url:"${baseUrl}acti/flow/suspend/"+id,
      type:"post",
      success: function (data) {
        console.log(data.errmsg);
        if(data.errcode==0){
          getFlowInfo();
        }
      }
    })
  }

  function flowActive(id){
    $.ajax({
      url:"${baseUrl}acti/flow/active/"+id,
      type:"post",
      success: function (data) {
        console.log(data.errmsg);
        if(data.errcode==0){
          getFlowInfo();
        }
      }
    })
  }

  function showhis(id){
    var d = dialog({
      title: '流程图',
      content: '<div><img src="${baseUrl}acti/flow/graph/'+id+'" style="width:700px;"/></div>',
      button:[{
        value:"关闭"

      }]

    });
    d.showModal();


  }

  function flowdel(id){
    $.ajax({
      url:"${baseUrl}acti/flow/end/"+id,
      type:"post",
      success: function (data) {
        console.log(data.errmsg);
        if(data.errcode==0){
          getFlowInfo();
        }
      }
    })
  }

  function getTaskInfo(){
    $.ajax({
      url:"${baseUrl}acti/task/list",
      type:"post",
      data:"page=1&rows=10",
      success: function (data) {
        var jsonData=eval("["+data+"]");
        var list=jsonData[0].list;
        var str="";
        for(var i in list){
          str+="<tr><td>"+list[i].id+"</td>"+"<td>"+list[i].name+"</td>"+"<td>"+list[i].assignee+"</td>"
                  +"<td><a href='#' onclick=showhis('"+list[i].processInstanceId+"')>历史</a></td></tr>";
        }
        $("#tr4").empty().append(str);
      }
    })
  }

  function getHisInst(){
    //历史节点
    $.ajax({
      url:"${baseUrl}acti/history/node/list",
      type:"post",
      data:"page=1&rows=10",
      success: function (data) {
        // var jsonData=eval("["+data+"]");
        //console.log("task:"+jsonData[0].list);
        //console.log(data.list);
      }
    })

    //历史任务
    $.ajax({
      url:"${baseUrl}acti/history/task/list",
      type:"post",
      data:"page=1&rows=10",
      success: function (data) {
        //console.log(data.list);
      }
    })

    //历史实例
    $.ajax({
      url:"${baseUrl}acti/history/flow/list",
      type:"post",
      data:"page=1&rows=10",
      success: function (data) {
        //console.log(data.list);
      }
    })


    $.ajax({
      url:"${baseUrl}acti/history/flow/listvariable",
      type:"post",
      data:"page=1&rows=10&processInstanceId=dc96bcf9-2a04-11e5-83f1-c86000dfb4bd",
      success: function (data) {
        // console.log("flow :"+data.list);
      }
    })

  }

  function getHisTaskInfo(){
    //历史任务
    $.ajax({
      url:"${baseUrl}acti/history/task/list",
      type:"post",
      data:"page=1&rows=10",
      success: function (data) {
        console.log(data.list);
        var list=data.list;
        var str="<tr>";
        for(var i in list){
          str+="<td>"+list[i].id+"</td><td>"+list[i].name+"</td><td>"+list[i].assignee+"</td></tr>";
        }
        str+="</tr>";
        $("#tr5").html(str);
      }
    })
  }


  function addModel(){

    var d = dialog({
      title: '参数',
      content: '<table ><tr><td>key:</td><td><input autofocus id="modelkey"/></td></tr>'+
      "<tr><td>name:</td><td><input id='modelname' /></td></tr></table>",
      button:[{
        value:"确定",
        callback:function(){
          $.ajax({
            url:"${baseUrl}acti/model/addmodel",
            type:"post",
            data:"categoryId=0001&name="+$("#modelname").val()+"&key="+$("#modelkey").val(),
            success: function (data) {
              if(data.errcode==0){
                getDefineInfo();
              }
            }
          })
        }
      }]
    });
    d.showModal();

  }


  // 对Date的扩展，将 Date 转化为指定格式的String
  // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
  // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
  // 例子：
  // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
  // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18

  Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
      "M+": this.getMonth() + 1, //月份
      "d+": this.getDate(), //日
      "h+": this.getHours(), //小时
      "m+": this.getMinutes(), //分
      "s+": this.getSeconds(), //秒
      "q+": Math.floor((this.getMonth() + 3) / 3), //季度
      "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt))
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
  }

  //调用： var time1 = new Date().Format("yyyy-MM-dd");var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");

</script>
</body>
</html>
