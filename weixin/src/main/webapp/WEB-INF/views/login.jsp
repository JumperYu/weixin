<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>微信后台管理登录</title>
<link rel="shortcut icon" href="../../assets/ico/favicon.ico">

<!-- Bootstrap core CSS -->
<link href="${ctx}/static/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${ctx}/static/styles/signin.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="${ctx}/static/js/html5/html5shiv.js"></script>
      <script src="${ctx}/static/js/html5/respond.min.js"></script>
	<![endif]-->

<link href="${ctx}/static/jquery-validation/1.11.1/validate.css"
	type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script
	src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"
	type="text/javascript"></script>
</head>

<body>
	<form class="form-signin" action="https://localhost/spring-demo/login">
		<h2 class="form-signin-heading">请登陆</h2>
		<div class="controls">
			<input type="email" class="form-control input-medium required"
				placeholder="输入邮箱" required autofocus>
		</div>
		<input type="password" class="form-control" placeholder="密码" required>
		<label class="checkbox"> <input type="checkbox"
			value="remember-me"> 记住我
		</label>
		<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
	</form>
	<script>
 		$(function() {
			$(".form-signin").validate();
		}); 
	</script>
</body>
</html>
