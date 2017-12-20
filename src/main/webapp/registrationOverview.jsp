<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Inschrijvingen ${session.title}</title>
<%@include file="fragments/head.jspf"%>
</head>
<body>
	<div class="container">
		<%@include file="fragments/header.jspf"%>
		<div class="jumbotron">
			<div class="container">
				<h2>Inschrijvingen ${session.title}</h2>
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
						<c:forEach var="student" items="${students}">
							<tr>
								<td>${student.firstName}</td>
								<td>${student.lastName}</td>
								<td>${student.email}</td>
								<td><a
									href="Controller?action=updateSessionStudent&sessionId=${session.id}&studentId=${student.id}">Wijzig
										gegevens</a></td>
								<td><a
									href="Controller?action=removeSessionStudent&sessionId=${session.id}&studentId=${student.id}">Verwijder</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
</body>
</html>