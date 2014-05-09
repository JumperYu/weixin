<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<sql:query var="rs" dataSource="jdbc/weixin">
	select id, name from tb_test
</sql:query>
<html>
<head>
<title>DB Test</title>
</head>
<body>

	<h2>Results</h2>

	<c:forEach var="row" items="${rs.rows}">
    Foo ${row.id} &nbsp;&nbsp; Bar ${row.name}<br />
	</c:forEach>

</body>
</html>
