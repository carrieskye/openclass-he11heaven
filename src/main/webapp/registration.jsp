<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Registratie</title>
<%@include file="fragments/head.jspf"%>
</head>

<body>
	<div class="container">
		<%@include file="fragments/header.jspf"%>
		<div class="jumbotron">
			<h1>Registratie ${session.title}</h1>
			<c:if test="${errormessage != null}">
				<div class="alert-danger">
					<ul>
						<c:forEach var="string" items="${errormessage}">
							<li>${string}</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
			<c:choose>
				<c:when test="${wijzigen}">
					<form method="post" action="Controller?action=updateStudent">
						<input type="hidden" name="id" value="${studentid}" />
						<input type="hidden" name="sessionId" value="${sessionId}" />
				</c:when>
				<c:otherwise>
					<form method="post" action="Controller?action=registerStudent&sessionId=${session.id}">	
				</c:otherwise>
			</c:choose>
				
				<div class="form-group">
					<label for="firstName">Voornaam</label> <input type="text"
						name="firstName" class="form-control" id="firstName"
						placeholder="Voornaam" required value="${firstNamePreviousValue}">
				</div>
				<div class="form-group">
					<label for="lastName">Achternaam</label> <input type="text"
						name="lastName" class="form-control" id="lastName"
						placeholder="Achternaam" required value="${lastNamePreviousValue}">
				</div>
				<div class="form-group">
					<label for="email">E-mail</label> <input type="email" name="email"
						class="form-control" id="email" placeholder="E-mail" required
						value="${emailPreviousValue}">
				</div>
				<c:choose>
				<c:when test="${wijzigen}">
					<button type="submit" class="btn btn-primary">Wijzig</button>
				</c:when>
				<c:otherwise>
					<button type="submit" class="btn btn-primary">Schrijf in</button>	
				</c:otherwise>
			</c:choose>
				
			</form>
		</div>
	</div>
</body>
</html>