<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Image overview</title>
<%@include file="fragments/head.jspf"%>
</head>
<body>
	<h1>Image overview</h1>

	<c:forEach items="${images}" var="image">
		<img src="data:image/*;base64,${image}" alt="Image not found" />
	</c:forEach>

</body>
</html>