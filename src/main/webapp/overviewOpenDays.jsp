<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Overzicht Openlesdagen</title>
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
			<h1 style="margin-bottom: 1em">Overzicht openlesdagen</h1>
			<c:choose>
				<c:when test="${message != null}">
					<p>${message}</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="openLesDag" items="${openDays}">
						<a style="display: block" href="Controller?action=sessionoverview&openlesdagId=${openLesDag.id}">
							<div class="card"
								style="width: 60rem; margin: 0 auto; border: 2px solid #777777; padding: 10px; background-color: #dbdbdb; margin-bottom: 1em">
								<div class="card-block">
									<div class="row">
										<div class="col-lg-2 tags p-b-2" style="font-size: 1.3em">
											<p style="margin-top: 0">${openLesDag.datumString}</p>
										</div>
										<div class="col-lg-8 offset-lg-1">
											<h4 class="card-title">${openLesDag.titel}
												<span class="mb-2 text-muted">${openLesDag.tijdstipString}</span>
											</h4>
											<h3>${openLesDag.locatie}</h3>

										</div>
									</div>

								</div>
							</div>
						</a>
					</c:forEach>
				</c:otherwise>
			</c:choose>


		</div>
	</div>
</body>
</html>