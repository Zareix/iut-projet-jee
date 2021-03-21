<%@page import="mediatek2021.Utilisateur"%>
<%@page import="mediatek2021.NewDocException, mediatek2021.Mediatek"%>
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
	if (user == null)
		response.sendRedirect("/Projet_jEE/");

	String[] types = { "Livre", "CD", "DVD" };

	int type = -1;
	if (request.getParameter("type") != null)
		type = Integer.parseInt(request.getParameter("type"));
	String titre = request.getParameter("titre");
	String auteur = request.getParameter("auteur");
	String msgError = "";

	if (type != -1 && titre != null && !titre.equals("")) {
		try {
			Mediatek.getInstance().newDocument(type, titre, auteur);
			msgError = "Document ajouté !";
		} catch (NewDocException e) {
			msgError = e.getMessage();
		}
	}
	%>
	<a href="/Projet_jEE/index.jsp"><button>&larr; Retour au
			menu</button></a>
	<h1>Service d'ajout de documents</h1>
	<form action="/Projet_jEE/addDocument.jsp" method="post">
		<select name="type" id="type-select">
			<option value="-2">--Choisir le type de Document--</option>
			<%
			for (int i = 1; i <= types.length; i++) {
			%>
			<option value="<%=i%>"><%=types[i - 1]%></option>
			<%
			}
			%>
		</select> <input name="titre" placeholder="Entrer le titre"> <input
			name="auteur" placeholder="Entrer l'auteur de ce document">
		<div class="error"><%=msgError%></div>
		<button type="submit">Envoyer</button>
	</form>
</body>
</html>