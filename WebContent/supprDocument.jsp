<%@page import="mediatek2021.Utilisateur"  %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="./index.css"></link>
</head>
<body>
	<%
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/Projet_jEE/");
	%>
	<a href="/Projet_jEE/index.jsp"><button>Retour au menu</button></a>
	<h1>Service de suppression de documents</h1>
</body>
</html>