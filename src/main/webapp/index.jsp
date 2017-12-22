<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Startpagina</title>
<%@include file="fragments/head.jspf"%>
</head>

<body>
	<jsp:forward page="/Controller?action=getOpleidingenOverzicht"></jsp:forward>
	<div class="container">
		<%@include file="fragments/header.jspf"%>
		<div class="jumbotron">

			<h1>Openlesdagen UCLL</h1>
			<p>Dit platform biedt een snel en eenvoudig overzicht van alle openlesdagen die aangeboden worden voor de verschillende richtingen binnen UCLL.</p>
			<p>Het overzicht is te vinden onder <a href="Controller?action=getOpleidingenOverzicht">Opleidingen</a></p>
		</div>
	</div>
</body>
</html>