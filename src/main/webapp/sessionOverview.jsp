<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Session overview</title>
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
<%@include file="header.jspf" %>

	<div class="container">

		<c:forEach var="sessionRow" items="${sessions}">
			<div class="row">
				<c:forEach var="session" items="${sessionRow}">
					<div class="col-sm-3">
						<div class="panel panel-primary">
							<div class="panel-heading">${session.header}</div>
							<div class="panel-body">${session.description}</div>
							<div class="panel-footer">Schrijf in</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>

</body>
</html>