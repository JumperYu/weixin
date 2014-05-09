<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<sql:query var="rs" dataSource="jdbc/weixin">
	select cd_key,to_user,to_date from weixin where stat='1'
</sql:query>
<html>
<head>
<title>DB Test</title>
</head>
<body>

	<h2>Results</h2>

	<c:forEach var="row" items="${rs.rows}">
    CD-KEY: ${row.cd_key} &nbsp;&nbsp; USER: ${row.to_user} &nbsp;&nbsp; DATE: ${row.to_date}<br />
	</c:forEach>

</body>
</html>
