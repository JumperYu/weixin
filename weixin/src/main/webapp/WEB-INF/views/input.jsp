<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<%-- <script type="text/javascript" src="${ctx}/static/js/app/upload.js"></script> --%>
<title>上传管理</title>
</head>
<body>
	<form class="form" action="" method="post"
		enctype="multipart/form-data">
		<table>
			<tr id="replyid" style="display:none;">
				<td>编码：</td>
				<td><input type="hidden" name="id" value="${id}"/></td>
			</tr>
			<tr>
				<td>关键字：</td>
				<td><input type="text" name="key_word" value="${key_word}"
					placeholder="多个关键字以英文逗号隔开" /></td>
			</tr>
			<tr>
				<td>回复类型：</td>
				<td>
					<input id="text" type="radio" value="text" name="key_type"/>文字 
					<input id="image" type="radio" value="image" name="key_type"/>图文
				</td>
			</tr>
			<tr class="no_class_text">
				<td>回复内容：</td>
				<td><textarea name="reply" rows="3" cols="3"
					placeholder="请输入匹配后自动回复的内容"></textarea></td>
			</tr>
			<tr class="no_class_news">
				<td>图文标题：</td>
				<td><input type="text" name="title" value="${title}"
					placeholder="请输入标题" /></td>
			</tr>
			<tr class="no_class_news">
				<td>图文描述: </td>
				<td><textarea name="imageDesc" rows="3" cols="3"
						placeholder="图文描述">${imageDesc}</textarea></td>
			</tr>
			<tr class="no_class_news">
				<td>图片url：</td>
				<td><input type="text" name="picUrl" value="${picUrl}"
					placeholder="请输入url" /></td>
			</tr>
			<tr class="no_class_news">
				<td>图文链接：</td>
				<td><input type="text" name="linked" value="${linked}"
					placeholder="请输入链接" /></td>
			</tr>
			<c:choose>
					<c:when test="${key_type eq 'news'}">
						<script>
						(function(){
						document.getElementById("image").checked = "checked";
						$(".no_class_news").show();
						$(".no_class_text").hide();
						})();//-->>注入些脚本选中
						</script>
					</c:when>
					<c:otherwise>
						<script>
						(function(){
						document.getElementById("text").checked = "checked";
						$(".no_class_news").hide();
						$(".no_class_text").show();
						})();//-->>注入些脚本选中
						</script>
					</c:otherwise>
			</c:choose>
			<tr>
				<td>
					<input type="button" value="提交" />
				</td>
			</tr>
		</table>
	</form>
	<script>
		$(function() {
			$("input[name='key_type']").on("change", function() {
				var _input = $("table tr input").get(0);
				if(_input && _input.value){
					var yesOrNot = confirm("你确认要离开吗？");
					if(yesOrNot){
						$("#replyid").remove();
						$("table input[type='text']").val("");
						$("table textarea").text("");
					} else{
						return false;
					}
				}
				$(".no_class_news, .no_class_text").toggle("fast");
			});
			$("input[type='button']").on("click", function() {
				$(".form").ajaxSubmit({
					url : "${ctx}/textReply",
					dataType : "text",
					success : function(data){
						console.log(data);
					},
					error : function(data){
						console.log(data);
					}
				});
			});
		});
	</script>
</body>
</html>
