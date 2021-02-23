<%@page import="mediatek2021.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mediatek</title>
<link rel="stylesheet" href="./index.css" type="text/css"></link>
</head>
<body>
	<div class="main">
		<h1>Bienvenue sur le menu de la Mediatek</h1>
		<%
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		if (user == null) {
		%>
		<%@include file="needLogin.html"%>
		<%
		} else {
		%>
		<p>
			Connecté en tant que
			<%=user%></p>
		<div class="menu">
			<h3>Quel service souhaitez vous utiliser ?</h3>
			<ul>
				<li><a href="/Projet_jEE/addDocument.jsp">Ajouter un
						document</a></li>
				<li><a href="/Projet_jEE/supprDocument.jsp">Suppriment un
						document</a></li>
			</ul>
		</div>
		<%
		}
		%>
	</div>

</body>
</html>