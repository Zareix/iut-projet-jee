<%@page
	import="mediatek2021.Mediatek, mediatek2021.Document, mediatek2021.Utilisateur"%>
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
	Utilisateur user = (Utilisateur) session.getAttribute("user");
	if (user == null)
		response.sendRedirect("/Projet_jEE/");

	String[] types = { "Livre", "CD", "DVD" };
	List<Document> documents;
	%>

	<a href="/Projet_jEE/index.jsp"><button>&larr; Retour au
			menu</button></a>
	<h1>Catalogue de tous les documents</h1>
	<%
	for (int i = 1; i <= 3; i++) {
	%>
	<fieldset class="catalogue">
		<legend><%=types[i - 1]%></legend>
		<ul>
			<%
			documents = Mediatek.getInstance().catalogue(i);
			int cpt = 0;
			for (Document d : documents) {
				cpt++;
			%>
			<li><%=d.toString()%></li>
			<%
			}
			if (cpt == 0) {
			%>
			<li>Aucun document de ce type</li>
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