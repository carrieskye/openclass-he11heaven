<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Sessie overzicht</title>
<%@include file="fragments/head.jspf"%>
</head>
<body>


	<div class="container">
		<%@include file="fragments/header.jspf"%>
		<div class="jumbotron">

			<h1>Sessies</h1>
			<c:choose>
				<c:when test="${message != null}">
					<p>${message}</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="sessionRow" items="${sessions}">
						<div class="row">
							<c:forEach var="session" items="${sessionRow}">
								<div class="col-sm-3">
									<div class="panel panel-danger">
										<div class="panel-heading">${session.header}\\\
											0/${session.maxEntries}</div>
										<div class="panel-body">
											<p style="font-size: 15px">${session.description}</p>
											<p style="font-size: 15px">
												<a
													href="Controller?action=registerForm&sessionId=${session.id}">Schrijf
													in</a>
											</p>
											<p style="font-size: 15px">
												<a
													href="Controller?action=registrationOverview&sessionId=${session.id}">Overzicht
													inschrijvingen</a>
											</p>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>