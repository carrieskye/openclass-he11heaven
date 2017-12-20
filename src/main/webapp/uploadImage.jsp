<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Upload image</title>
<%@include file="fragments/head.jspf"%>
</head>
<body>

	<h1>Upload image</h1>

	<form method="post" action="Controller?action=uploadimage"
		enctype="multipart/form-data">
		<p>
			<label for="image">Image</label><input type="file" name="image" accept=".jpg,.png,.jpeg"
				id="image"/>
		</p>
		<p>
			<input type="submit" id="upload" value="Upload">
		</p>
	</form>

</body>
</html>