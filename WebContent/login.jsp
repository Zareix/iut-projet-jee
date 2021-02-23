<%@page import="mediatek2021.Utilisateur"%>
<%@page import="mediatek2021.Mediatek"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mediatek</title>
<link rel="stylesheet" href="./index.css"></link>
</head>
<body>
	<%
	String login = request.getParameter("login");
	String password = request.getParameter("password");
	Utilisateur user = null;

	if (login != null && password != null)
		user = Mediatek.getInstance().getUser(login, password);

	String msgError = "";
	if (login != null && password != null && user == null)
		msgError = "Aucun utilisateur pour cet identifiant et mot de passe";
	%>
	<h1>Bonjour, merci de vous connecter</h1>
	<%
	if (user == null) {
	%>
	<form action="/Projet_jEE/login.jsp" method="post">
		<input name="login" placeholder="Entrer votre identifiant"> 
		<input
			type="password" name="password"
			placeholder="Entrer votre mot de passe">
		<button type="submit">Se connecter</button>
	</form>
	<%=msgError%>
	<%
	} else {
	%>
	<%
	session.setAttribute("user", user); // May be useless
	%>
	<h2>
		Connecté en tant que
		<%=user%></h2>
	<a href="/Projet_jEE/index.jsp">Vers le menu</a>
	<% } %>
</body>
</html>