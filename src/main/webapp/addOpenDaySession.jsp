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

			<form method="post" action="Controller?action=addOpenDaySession">
				<fieldset>
				<legend >Informatie OpenLesdag</legend>
				<div class="form-group">
					<label for="title">Titel evenement</label> <input type="text"
						name="title" class="form-control" id="title"
						placeholder="Bvb. openlesdag, openlesweek, ..." required
						value="${openDayNamePreviousValue}">
				</div>

				<div class="form-group">
					<label for="location">Locatie</label> <input type="text"
						name="location" class="form-control" id="location"
						placeholder="Campus x" required value="${locationPreviousValue}">
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
									<option value="${opleiding.id}"
										<c:if test="${opleiding.id == opleidingId }"> selected="selected"</c:if>><c:out
											value="${opleiding.naam}" /></option>
								</c:forEach>
							</optgroup>
						</c:forEach>
					</select>
				</div>
				</fieldset>
				<fieldset>
				<legend>Informatie Sessie</legend>
				<div class="form-group">
					<label for="sessionName">Sessie naam</label> <input type="text"
						name="sessionName" class="form-control" id="sessionName"
						placeholder="Sessie naam" required
						value="${sessie.title}">
				</div>
				<div class="form-group">
					<label for="beginTime">Startuur</label> <input type="time"
						name="beginTime" class="form-control" id="beginTime" required
						value="${sessie.start}">
				</div>
				<div class="form-group">
					<label for="endTime">Einduur</label> <input type="time"
						name="endTime" class="form-control" id="endTime" required
						value="${sessie.end}">
				</div>
				<div class="form-group">
					<label for="content">Inhoud</label>
					<textarea name="content" class="form-control" id="content" required
						 rows=4>${sessie.description}</textarea>
				</div>
				<div class="form-group">
					<label for="classroom">Klaslokaal</label> <input type="text"
						name="classroom" class="form-control" id="classroom"
						placeholder="Klaslokaal" required value="${sessie.classroom}">
				</div>
				<div class="form-group">
					<label for="maxaantal">Maximum aantal inschrijvingen:</label> <input
						type="text" name="maxaantal" class="form-control" id="maxaantal"
						placeholder="maximum aantal inschrijvingen" required
						value="${sessie.maxEntries}">
				</div>
				</fieldset>

				<button type="submit" class="btn btn-primary">Voeg
					openlesdag en sessie toe</button>
</body>
</html>