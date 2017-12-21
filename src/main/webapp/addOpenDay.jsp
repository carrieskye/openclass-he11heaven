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

			<p>Geef alle informatie van een openlesdag die je wilt toevoegen.</p>

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
					<label for="title">Titel evenement</label> <input type="text"
						name="title" class="form-control" id="title"
						placeholder="Bvb. openlesdag, openlesweek, ..." required
						value="${openDayNamePreviousValue}">
				</div>

				<div class="form-group">
					<label for="location">Locatie</label> 
					<select name="location">
						<option value="Campus Clenardus">Campus Clenardus</option>
						<option value="Campus Diepenbeek">Campus Diepenbeek</option>
						<option value="Campus Gasthuisberg">Campus Gasthuisberg</option>
						<option value="Campus Hemelrijk">Campus Hemelrijk</option>
						<option value="Campus Hertogstraat">Campus Hertogstraat</option>
						<option value="Campus LiZa">Campus LiZa</option>
						<option value="Campus Oude Luikerbaan">Campus Oude
							Luikerbaan</option>
						<option value="Campus Proximus">Campus Proximus</option>
						<option value="Campus Sociale School">Campus Sociale
							School</option>
					</select>
				</div>

				<div class="form-group">
					<label for="date">Datum</label> <input type="date" name="date"
						class="form-control" id="date" required
						value="${datePreviousValue}">
				</div>
				<div class="form-group">
					<label for="opleiding"> Opleiding</label> 
					<select name="id">
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

				<button type="submit" class="btn btn-primary">Voeg
					openlesdag toe</button>
			</form>
</body>
</html>