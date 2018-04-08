<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="/fts/home/list.action">
		<input type="text" name="pageIndex" >
		<input type="text" name="pageSize" >
		<button type="submit"></button>
	</form>
	<c:forEach items="${ftsResources }" var="ftsResources">
		<TR
			style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
			<TD>${ftsResources.name }</TD>
			<TD>${ftsResources.description }</TD>
			<TD>${ftsResources.imageUrl }</TD>
			<TD>${ftsResources.resourceUrl }</TD>
			<TD>${ftsResources.insertTime }</TD>
			<TD>${ftsResources.downloads }</TD>
			<TD>${ftsResources.ftsCategories.name }</TD>
			</br>
		</TR>

	</c:forEach>

</body>
</html>