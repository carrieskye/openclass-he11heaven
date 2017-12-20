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
	<div class="container">
		<%@include file="fragments/header.jspf"%>
		<div class="jumbotron">
			<h1>Voeg een sessie toe</h1>

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
		<form method="post" action="Controller?action=voegSessieToe">
			<div class="form-group">
				<label for="sessionName">Sessie naam</label> <input type="text" name="sessionName"
					class="form-control" id="sessionName" placeholder="Sessie naam" required
					value="${sessionNamePreviousValue}">
			</div>
			<div class="form-group">
				<label for="beginTime">Startuur</label> <input type="time" name="beginTime"
					class="form-control" id="beginTime" required 
					value="${beginTimePreviousValue}">
			</div>
			<div class="form-group">
				<label for="endTime">Einduur</label> <input type="time" name="endTime"
					class="form-control" id="endTime" required
					value="${endTimePreviousValue}">
			</div>
			<div class="form-group">
				<label for="content">Inhoud</label> <textarea name="content"
					class="form-control" id="content" required
					value="${contentPreviousValue}" rows = 4></textarea>
			</div>
			<div class="form-group">
				<label for="classroom">Klaslokaal</label> <input type="text" name="classroom"
					class="form-control" id="classroom" placeholder="Klaslokaal" required
					value="${contentPreviousValue}">
			</div>
			<div class="form-group">
				<label for="maxaantal">Maximum aantal inschrijvingen:</label> <input type="text" name="maxaantal"
					class="form-control" id="maxaantal" placeholder="maximum aantal inschrijvingen" required
					value="${maxaantalPreviousValue}">
			</div>
			<div class="form-group">
				<label for="date">Datum</label> <input type="date" name="date"
					class="form-control" id="date" required
					value="${datePreviousValue}">
			</div>
			<div class="form-group">
				<select name="opleiding">
					<c:forEach var="afdeling" items="${afdelingen}">
						<optgroup label = "${afdeling.naam}"><c:out value="${afdeling.naam}" />
						
						<c:forEach var="opleiding" items="${afdeling.opleidingen}">
								<option><c:out value="${opleiding.naam}" /></option>
							</c:forEach>
						</optgroup>
						</c:forEach>
					
					</select>
				</div>

				<button type="submit" class="btn btn-primary">Voeg de
					sessie toe</button>
			</form>
		</div>

	</div>

</body>
</html>