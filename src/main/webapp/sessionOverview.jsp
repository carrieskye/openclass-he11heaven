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
								<c:set var="isVolzet" value="${session.currentEntries >= session.maxEntries}" />
								<div class="col-sm-3">
									<c:choose>
										<c:when test="${isVolzet eq true}">
											<div class="panel panel-danger">
										</c:when>
										<c:otherwise>
											<div class="panel panel-info">
										</c:otherwise>
									</c:choose>
									
										<div class="panel-heading">${session.header}</div>
										<div class="panel-body">
											<p style="font-size: 15px">${session.description}</p>
											<p style="font-size: 15px">
											<c:choose>
												<c:when test="${isVolzet eq true}">
													Sessie volzet.
												</c:when>
												<c:otherwise>
													<a href="Controller?action=registerForm&sessionId=${session.id}">Schrijf in</a>
												</c:otherwise>
											</c:choose>
											</p>
											
											<p style="font-size: 15px; color: gray;">Ingeschreven: ${session.currentEntries}/${session.maxEntries}</p>
											<p style="font-size: 15px">
												<a href="Controller?action=registrationOverview&sessionId=${session.id}">Overzicht inschrijvingen</a>
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