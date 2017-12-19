<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Opleiding overzicht</title>
<jsp:include page="fragments/head.jspf" />
</head>

<body>
	<%@include file="fragments/header.jspf"%>

	<div class="container">
	<h1>Opleidingen</h1>
		<c:forEach var="afdeling" items="${afdelingen}">
			<h2>
				<c:out value="${afdeling.naam}" />
			</h2>
			<ul id = "${afdeling.naam}">
				<c:forEach var="opleiding" items="${afdeling.opleidingen}">
					<a
						href="Controller?action=overviewOpendays&id=${opleiding.id }&afdeling=${afdeling.naam}"><li><c:out
								value="${opleiding.naam}" /></li></a>
				</c:forEach>
			</ul>
		</c:forEach>
	</div>
</body>
</html>