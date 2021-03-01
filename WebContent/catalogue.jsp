<%@page import="mediatek2021.Mediatek, mediatek2021.Document"%>
<%@page import="java.util.ArrayList, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Catalogue</title>
<link rel="stylesheet" href="./index.css" type="text/css"></link>
</head>
<body>
	<%
	String[] types = { "Livre", "CD", "DVD" };
	List<Document> documents;
	%>

	<h1>Catalogue de tout les documents</h1>
	<%
	for (int i = 1; i <= 3; i++) {
	%>
	<fieldset class="catalogue">
		<legend><%=types[i - 1]%></legend>
		<ul>
			<%
			documents = Mediatek.getInstance().catalogue(i);
			for (Document d : documents) {
			%>
			<li><%=d.toString()%></li>
			<%
			}
			%>
		</ul>
	</fieldset>
	<%
	}
	%>
</body>
</html>