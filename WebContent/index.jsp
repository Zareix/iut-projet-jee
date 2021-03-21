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
			Connecté en tant que <b> <%=user%>
			</b>
		</p>
		<div class="menu">
			<h3>Quel service souhaitez vous utiliser ?</h3>
			<p>
				<a href="/Projet_jEE/catalogue.jsp">Voir tous les documents</a>
			</p>
			<p>
				<a href="/Projet_jEE/addDocument.jsp">Ajouter un document</a>
			</p>
			<p>
				<a href="/Projet_jEE/supprDocument.jsp">Supprimer un document</a>
			</p>
		</div>
		<%
		}
		%>
	</div>

</body>
</html>