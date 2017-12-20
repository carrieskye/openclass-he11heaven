<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Overzicht Openlesdagen</title>
<%@include file="fragments/head.jspf"%>
</head>

<body>
	<div class="container">
		<%@include file="fragments/header.jspf"%>
		<div class="jumbotron">
			<h1 style="margin-bottom: 1em">Overzicht openlesdagen</h1>
			<c:choose>
				<c:when test="${message != null}">
					<p>${message}</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="openLesDag" items="${openDays}">
						<div class="card"
							style="width: 60rem; margin: 0 auto; border: 2px solid #777777; padding: 10px; background-color: #dbdbdb; margin-bottom: 1em">
							<a style="display: block; color: #000000;"
								href="Controller?action=sessionoverview&openlesdagId=${openLesDag.id}"><div
									class="card-block">
									<div class="row">
										<div class="col-lg-2 tags p-b-2" style="font-size: 1.2em">
											<p style="margin-top: 0">${openLesDag.datumString}</p>
										</div>
										<div class="col-lg-8 offset-lg-1">
											<h4 class="card-title">
												${openLesDag.titel} <span class="text-muted">${openLesDag.tijdstipString}</span>
											</h4>
											<h2>${openLesDag.locatie}</h2>

										</div>
									</div>

								</div> </a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>


		</div>
	</div>
</body>
</html>