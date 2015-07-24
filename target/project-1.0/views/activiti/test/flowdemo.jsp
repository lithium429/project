<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.xz.base.utils.WebUtil" %>
<%
  String baseUrl = WebUtil.getBasePath(request);
  request.setAttribute("baseUrl", baseUrl);
%>
<html>
<script src="../../../static/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="../../../static/js/artDialog/dialog-min.js" type="text/javascript"></script>
<link rel="stylesheet" href="../../../static/js/artDialog/skins/ui-dialog.css">
<style type="text/css">
  input{
    margin-top: 5px;
  }

  a{
    text-decoration: none;

  }
  div{
    margin-top: 5px;
  }
</style>
<head>
    <title>flow demo</title>
</head>
<body>
    <%--<div>--%>
      <%--<a href="javascript:void(0);" onclick="startflow();" >启动流程</a>--%>
      <%--<div id="processInstance" style="display: none">流程id:<span id="picontent"></span></div>--%>
    <%--</div>--%>

    <%--<div>--%>
      <%--<a href="javascript:void(0);" onclick="listflow();" >待办任务</a><span style="margin-left: 12px;"><button onclick="$('#flowinfo').hide();">收起</button></span>--%>
      <%--<div id="flowinfo" style="display: none"></div>--%>
    <%--</div>--%>

    <%--<div>--%>
      <%--<a href="javascript:void(0);" onclick="completeflow();" >完成任务</a>--%>

    <%--</div>--%>

    <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>


    <div>
      <a href="javascript:void(0);" onclick="startexcluflow();" >启动流程</a>
      <div id="exclpi" style="display: none">流程id:<span id="exclpicontent"></span></div>
    </div>

    <div>
      <a href="javascript:void(0);" onclick="listexcluflowV2();" >查看待办任务</a>
      <div id="exclflowinfo" style="display: none"></div>
    </div>

    <div>
      <a href="javascript:void(0);" onclick="completeflow();" >完成任务</a>
      <div id="exclinfo"></div>
    </div>

    <%--<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>--%>
    <%--<div>--%>
      <%--<a href="javascript:void(0);" onclick="startPareflow();" >启动并行流程</a>--%>

    <%--</div>--%>

    <%--<div>--%>
      <%--<a href="javascript:void(0);" onclick="listPareflow();" >查看并行流程</a>--%>

    <%--</div>--%>

    <%--<div>--%>
      <%--<a href="javascript:void(0);" onclick="comPareflow();" >完成并行流程</a>--%>

    <%--</div>--%>

    <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>

    <div>
      <a href="javascript:void(0);" onclick="startjoinflow();" >启动会签流程</a>

    </div>


    <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>

    <button id="artd" >
      模态框测试
    </button>


<script type="text/javascript">

  $("#artd").click(function(){
    var d = dialog({
      title: 'message',
      content: '<table ><tr><td>key:</td><td><input autofocus id="key"/></td></tr>'+
              "<tr><td>name:</td><td><input id='name' /></td></tr></table>",
      button:[{
        value:"确定",
        callback:function(){

        }
      }]
    });
    d.showModal();

  })



  //*************************demo flow**************************//

  <%--function startflow(){--%>
    <%--$.ajax({--%>
      <%--url:"${baseUrl}acti/test/start",--%>
      <%--data:"key=lee",--%>
      <%--type:"post",--%>
      <%--success: function (data) {--%>
        <%--var data=eval("["+data+"]");--%>
        <%--console.log(data[0].processInstanceId);--%>
        <%--if(data[0].errcode==0){--%>
          <%--var d = dialog({--%>
            <%--title: 'message',--%>
            <%--content: '启动成功',--%>
            <%--button:[{--%>
              <%--value:"确定",--%>
              <%--callback:function(){--%>
                <%--$("#picontent").html(data[0].processInstanceId);--%>
                <%--$("#processInstance").show();--%>
              <%--}--%>
            <%--}]--%>
          <%--});--%>
          <%--d.showModal();--%>

        <%--}--%>
      <%--}--%>
    <%--})--%>
  <%--}--%>

  <%--function listflow(){--%>
    <%--$("#flowinfo").hide();--%>

    <%--var d = dialog({--%>
      <%--title: '参数',--%>
      <%--content: '<table ><tr><td>userId:</td><td><input autofocus id="userId"/></td></tr>'+--%>
      <%--"</table>",--%>
      <%--button:[{--%>
        <%--value:"确定",--%>
        <%--callback:function(){--%>
          <%--$.ajax({--%>
            <%--url:"${baseUrl}acti/test/list",--%>
            <%--type:"post",--%>
            <%--data:"category=0001&pageNum=1&pageSize=20&uid="+$("#userId").val(),--%>
            <%--success: function (data) {--%>
              <%--var data=eval("["+data+"]");--%>
              <%--console.log(data);--%>
             <%--if(data[0].errcode==0){--%>
               <%--var str="<div>总数:"+data[0].total+"<div><div><ul>";--%>
                <%--for(var i in data[0].list){--%>
                  <%--str+="<li><div>执行用户:&nbsp;"+data[0].list[i].name+"<div>" +--%>
                          <%--"<div>执行id:&nbsp;"+data[0].list[i].executionId+"</div>" +--%>
                          <%--"<div>任务id:&nbsp;"+data[0].list[i].id+"</div>"+"</li>";--%>
                <%--}--%>
                <%--str+="</ul></div>";--%>
                <%--$("#flowinfo").html(str).show();--%>

             <%--}--%>
            <%--}--%>
          <%--})--%>
        <%--}--%>
      <%--}]--%>
    <%--});--%>
    <%--d.showModal();--%>
  <%--}--%>



  function completeflow(){

    var d = dialog({
      title: 'message',
      content: '<table ><tr><td>taskid:</td><td><input autofocus id="taskid"/></td></tr>'+
     ' <tr><td>param:</td><td><input id="comparam"/></td></tr>'+
      ' <tr><td>comment:</td><td><input id="comment"/></td></tr>'+
      "<tr><td>uid:</td><td><input id='userid_2' /></td></tr></table>",
      button:[{
        value:"确定",
        callback:function(){
          $.ajax({
            url:"${baseUrl}acti/test/complete",
            type:"post",
            data:"taskId="+$("#taskid").val()+"&uid="+$("#userid_2").val()+"&param="+$("#comparam").val()+"&comment="+$("#comment").val(),
            success: function (data) {
              var data=eval("["+data+"]");
              if(data[0].errcode==0){successFrame();}else{failFrame();};
            }
          })
        }
      }]
    });
    d.showModal();
  }

  var successFrame=function(){
    var d = dialog({
      title: 'message',
      content: '成功',
      button:[{
        value:"确定"
        }]
    });
    d.showModal();
  }

  var failFrame=function(){
    var d = dialog({
      title: 'message',
      content: '失败',
      button:[{
        value:"确定"
      }]
    });
    d.showModal();
  }

  //***************************************排他*******************************************//

  function startexcluflow(){
    var d = dialog({
      title: '参数',
      content: '<table ><tr><td>key:</td><td><input autofocus id="exclkey"/></td></tr>'+
      "<tr><td>param:</td><td><input id='days' /></td></tr></table>",
      button:[{
        value:"确定",
        callback:function(){
          $.ajax({
            url:"${baseUrl}acti/test/start",
            type:"post",
            data:"key="+$("#exclkey").val()+"&param="+$("#days").val(),
            success: function (data) {
              var data=eval("["+data+"]");
              console.log(data);
              if(data[0].errcode==0){
                successFrame();
                $("#exclpicontent").html(data[0].processInstanceId);
                $("#exclpi").show();
              }else{
                failFrame();
              }
            }
          })
        }
      }]
    });
    d.showModal();

  }

    function listexcluflowV2(){
      var d = dialog({
        title: '参数',
        content: '<table ><tr><td>userId:</td><td><input autofocus id="userId_3"/></td></tr>',
        button:[{
          value:"确定",
          callback:function(){
            $.ajax({
              url:"${baseUrl}acti/test/list",
              type:"post",
              data:"category=0001&pageNum=1&pageSize=20&uid="+$("#userId_3").val(),
              success: function (data) {
                var data=eval("["+data+"]");
                console.log(data);
                if(data[0].errcode==0){
                  var str="<div>总数:"+data[0].total+"<div><div><ul>";
                  for(var i in data[0].list){
                    str+="<li><div>执行用户:&nbsp;"+data[0].list[i].name+"<div>" +
                             "<div>流程定义id:&nbsp;"+data[0].list[i].processDefinitionId+"</div>" +
                            "<div>任务id:&nbsp;"+data[0].list[i].id+"</div>"+"</li>";
                  }
                  str+="</ul></div>";
                  $("#exclflowinfo").html(str).show();
                }
              }
            })
          }
        }]
      });
      d.showModal();
    }


      //*********************************并行***************************************//
      <%--function startPareflow(){--%>
        <%--$.ajax({--%>
          <%--url:"${baseUrl}acti/test/start/www",--%>
          <%--type:"post",--%>

          <%--success: function (data) {--%>
            <%--console.log(data);--%>
          <%--}--%>
        <%--})--%>
      <%--}--%>

      <%--function listPareflow(){--%>
        <%--$.ajax({--%>
          <%--url:"${baseUrl}acti/test/list",--%>
          <%--type:"post",--%>
          <%--data:"uid=2&category=0001&pageNum=1&pageSize=20",--%>
          <%--success: function (data) {--%>
            <%--console.log(data);--%>
          <%--}--%>
        <%--})--%>
      <%--}--%>

      <%--function comPareflow(){--%>
        <%--$.ajax({--%>
          <%--url:"${baseUrl}acti/test/complete",--%>
          <%--type:"post",--%>
          <%--data:"taskId=2c86f448-2b83-11e5-87c1-c86000dfb4bd&uid=4",--%>
          <%--success: function (data) {--%>
            <%--console.log(data);--%>
          <%--}--%>
        <%--})--%>
      <%--}--%>

  //**************************会签*********************************//

  function startjoinflow(){
    $.ajax({
      url:"${baseUrl}acti/test/startjoinsign",
      type:"post",
      traditional:true,
      data:"key=joinsign",
      success: function (data) {
        var data=eval("["+data+"]");
        console.log(data);
        if(data[0].errcode==0){
          successFrame();
          $("#exclpicontent").html(data[0].processInstanceId);
          $("#exclpi").show();
        }else{
          failFrame();
        }

      }
    })
  }

</script>

</body>
</html>
