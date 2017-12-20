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
			<h1>Voeg een openlesdag toe</h1>

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
			
			<form method="post" action="Controller?action=addOpenDay">
				<div class="form-group">
					<label for="openDayName">Titel openlesdag</label> 
					<input type="text"
						name="openDayName" class="form-control" id="openDayName"
						placeholder="Titel" required
						value="${openDayNamePreviousValue}">
				</div>
				
				<div class="form-group">
					<label for="location">Locatie</label> 
					<input type="text"
						name="location" class="form-control" id="location"
						placeholder="Campus x" required
						value="${locationPreviousValue}">
				</div>
				
				<div class="form-group">
					<label for="date">Datum</label> <input type="date" name="date"
						class="form-control" id="date" required
						value="${datePreviousValue}">
				</div>
				
				<div class="form-group">
					<select name="opleiding">
						<c:forEach var="afdeling" items="${afdelingen}">
							<optgroup label="${afdeling.naam}">
								<c:out value="${afdeling.naam}" />

								<c:forEach var="opleiding" items="${afdeling.opleidingen}">
									<option value="${opleiding.id}"><c:out
											value="${opleiding.naam}" /></option>
								</c:forEach>
							</optgroup>
						</c:forEach>
					</select>
				</div>
</body>
</html>