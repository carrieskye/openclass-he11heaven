<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Inschrijvingen</title>
<%@include file="fragments/head.jspf"%>
</head>
<body>


	<div class="container">
		<%@include file="fragments/header.jspf"%>
		<div class="jumbotron">

			<h1>Inschrijvingen</h1>

			<div class="form-group">
				<select name="opleiding" onchange="window.location.href=this.value;">
					<option
						value="Controller?action=toonInschrijvingenOpenlesdagen&openlesdag=alleopleidingen">Alle
						opleidingen</option>
					<c:forEach var="afdeling" items="${afdelingen}">
						<optgroup label="${afdeling.naam}">
							<c:out value="${afdeling.naam}" />

							<c:forEach var="opleiding" items="${afdeling.opleidingen}">
								<option
									value="Controller?action=toonInschrijvingenOpenlesdagen&openlesdagId=${opleiding.id}">${opleiding.naam}

								</option>
							</c:forEach>
						</optgroup>
					</c:forEach>

				</select>
			</div>
			<c:forEach var="inschrijving" items="${inschrijvingen}">
				<h4>Inschrijvingen ${inschrijving.key.title}</h4>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Firstname</th>
							<th>Lastname</th>
							<th>Email</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>


						<c:forEach var="student" items="${inschrijving.value}">

							<tr>
								<td>${student.firstName}</td>
								<td>${student.lastName}</td>
								<td>${student.email}</td>
								<td><a
									href="Controller?action=updateSessionStudent&sessionId=${sessie.id}&studentId=${student.id}">Wijzig
										gegevens</a></td>
								<td><a
									href="Controller?action=removeSessionStudent&sessionId=${sessie.id}&studentId=${student.id}">Verwijder</a></td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</c:forEach>
		</div>
	</div>
</body>
</html>