<%@ tag language="java" pageEncoding="UTF-8" import="com.xz.base.utils.WebUtil" %>
<%@ attribute name="title" fragment="true"%>
<%@ attribute name="css" fragment="true"%>
<%@ attribute name="js" fragment="true"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<base href="<%=WebUtil.getBasePath(request)%>" />
	<link rel="stylesheet" type="text/css" href="static/css/manage.css" />
	<link rel="stylesheet" type="text/css" href="static/js/artDialog/skins/idialog.css" />
	<link rel="stylesheet" type="text/css" href="static/js/kindeditor-4.1.4/themes/default/default.css">
	<link rel="stylesheet" type="text/css" href="static/js/picbox/css/picbox.css">
	<jsp:invoke fragment="css" />
	
	<script type="text/javascript" src="static/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.unobtrusive-ajax.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.validate.unobtrusive.min.js"></script>
	<script type="text/javascript" src="static/js/artDialog/jquery.artDialog.js"></script>
	<script type="text/javascript" src="static/js/artDialog/plugins/iframeTools.js"></script>
	<script type="text/javascript" src="static/js/My97DatePicker/WdatePicker.js" ></script>
	
	<script type="text/javascript" src="static/js/kindeditor-4.1.4/kindeditor.js"></script>
	<script type="text/javascript" src="static/js/kindeditor-4.1.4/lang/zh_CN.js"></script>
	<script type="text/javascript" src="static/js/base.js"></script>
	<jsp:invoke fragment="js" />
</head>
<body>
	<jsp:doBody />
</body>
</html>