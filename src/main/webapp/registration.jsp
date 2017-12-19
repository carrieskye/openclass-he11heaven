<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Registratie</title>
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
<%@include file="fragments/header.jspf"%>
	<div class="container">
		<h1>Registratie ${session.title}</h1>

		<c:choose>
			<c:when test="${errormessage != null}">
				<div class="alert-danger">
					<ul>
						<c:forEach var="string" items="${errormessage}">
							<li>${string}</li>
						</c:forEach>
					</ul>
				</div>
			</c:when>
		</c:choose>

		<form method="post" action="Controller?action=registerStudent&sessionId=${session.id}">
			<div class="form-group">
				<label for="firstName">Voornaam</label> <input type="text" name="firstName"
					class="form-control" id="firstName" placeholder="Voornaam" required
					value="${firstNamePreviousValue}">
			</div>
			<div class="form-group">
				<label for="lastName">Achternaam</label> <input type="text" name="lastName"
					class="form-control" id="lastName" placeholder="Achternaam"
					required value="${lastNamePreviousValue}">
			</div>
			<div class="form-group">
				<label for="email">E-mail</label> <input type="email" name="email"
					class="form-control" id="email" placeholder="E-mail" required
					value="${emailPreviousValue}">
			</div>
			<button type="submit" class="btn btn-primary">Schrijf in</button>
		</form>
	</div>
</body>
</html>