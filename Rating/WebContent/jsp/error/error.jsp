<%@ page  isErrorPage="true" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>error page</title>
<meta name="description" content="Error" />

<link rel="stylesheet" type="text/css" href="css/error500Style.css" />
</head>
<body>
	<div class="wrapper">
		<div class="content">
			<div id="whoops"></div>
			<h1>ERROR PAGE</h1>
			Request from ${pageContext.errorData.requestURI} is failed <br />
			Servlet name: ${pageContext.errorData.servletName} <br /> Status
			code: ${pageContext.errorData.statusCode} <br /> Exception:
			${pageContext.exception} <br /> Message from exception:
			${pageContext.exception.message}
<br/>
<a href="index.jsp">Back</a> 
		</div>
	</div>
</body>
</html>