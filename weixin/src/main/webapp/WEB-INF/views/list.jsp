<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>查询列表</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>关键字</th>
				<th>回复类型</th>
				<th>回复内容</th>
				<th>图文标题</th>
				<th>图文描述</th>
				<th>图片url</th>
				<th>图文链接</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<script>
		$(function() {
			var ctx = '${ctx}';
			var listUri = "/listAll";
			var deleteUri = "/delete";
			$.ajax({
				url : ctx + listUri,
				type : "get",
				dataType : "json",
				success : function(data) {
					if(data != null && data.length > 0){
						var trs = [];
						$.each(data, function(i, n){
							trs.push("<tr>");
							trs.push("<td style='display:none;'>" + n["id"] + "</td>");
							trs.push("<td>" + n["key_word"] + "</td>");
							trs.push("<td>文本</td>");
							trs.push("<td>" + n["reply"] + "</td>");
							trs.push("<td>&nbsp;</td>");
							trs.push("<td>&nbsp;</td>");
							trs.push("<td>&nbsp;</td>");
							trs.push("<td>&nbsp;</td>");
							trs.push("<td><a href='#' name='edit'>编辑</a><a href='#' name='delete'>删除</a></td>");
							trs.push("</tr>");
						});
					}
					$("table tbody").html(trs.join(""));
				},
				error : function(msg) {
					console.log("error");
					console.log(msg);
				}
			});//-->> End of ajax
			$(document).on("click", "table a", function(){
				var name = this.name;
				if( name == "delete" || name == "edit"){
					var val = $(this).parent().parent().find("td:first").text();
					if(name == "edit"){
						window.location.href = ctx + "/input?id=" + val;
					} else{
						$.ajax({
							url : ctx + deleteUri,
							dataType : "text",
							data : {id : val},
							success : function(data) {
								alert(data);
								window.location.href = ctx + "/list";
							},
							error : function(error) {
								console.log(error);
							}
						});
					}
				} else{
					return false;
				}
			});//-->> End of on <a> be clicked
		});
	</script>
</body>
</html>
