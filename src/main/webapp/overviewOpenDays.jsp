<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Overzicht Openlesdagen</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<h1>Overzicht openlesdagen</h1>
<main>
		<table>
			<tr>
				<th>Datum</th>
				<th>Tijdsduur</th>
				<th>Campus</th>
			</tr>
			<c:forEach var="openLesDag" items="${openLesDagen}">
				<tr>
					<td>${openLesDag.datum}</td>
					<td>${openLesDag.time}</td>
					<td>${openLesDag.campus}</td>
				</tr>
			</c:forEach>

			<caption>Overzicht openlesdagen</caption>
		</table>
		</main>
</body>
</html>