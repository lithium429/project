<%@ tag language="java" pageEncoding="UTF-8" import="com.xz.base.utils.WebUtil" %>
<%@ attribute name="title" fragment="true"%>
<%@ attribute name="css" fragment="true"%>
<%@ attribute name="js" fragment="true"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%
	String baseUrl = WebUtil.getBasePath(request);
	request.setAttribute("baseUrl", baseUrl);
%>

<!DOCTYPE HTML>
<html class="Mainframe_html">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<!-- <meta http-equiv="X-UA-Compatible"content="IE=7" /> -->
	<title>学舟Java后台系统管理框架</title>
	<base href="${baseUrl}" />

	<link rel="stylesheet" type="text/css" href="static/css/core.css" />
	<link rel="stylesheet" type="text/css" href="static/js/artDialog/skins/blue.css" />
    <link rel="stylesheet" type="text/css" href="static/js/ztree/css/zTreeStyle/zTreeStyle.css" />
    
	<jsp:invoke fragment="css" />
	<script type="text/javascript" src="static/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="static/js/artDialog/jquery.artDialog.js"></script>
	<script type="text/javascript" src="static/js/artDialog/plugins/iframeTools.js"></script>
	<script type="text/javascript" src="static/js/ztree/js/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript" src="static/js/base.js"></script> 
	
	<!--[if lte IE 6]>
	<script type="text/javascript" src="static/js/DD_belatedPNG_0.0.8a-min.js"></script>
	<script type="text/javascript">
		DD_belatedPNG.fix('.png,.count,#control_navbar,.app_cont,#funbar_left .subtabs a,#funbar_left .subtabs a.cur .inner,.app_name_inner,.app_inner_r,.foot .start,.pannel_split,#menu_panel .panel_head,#menu_panel .panel_user,#menu_panel .panel_menu,#menu_panel .panel_foot');
	</script>
	<![endif]-->
	<jsp:invoke fragment="js" />
</head>
<body class="frame_wrap">
	<jsp:doBody />
</body>
</html>