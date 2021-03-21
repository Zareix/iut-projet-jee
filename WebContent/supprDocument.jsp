<%@page
	import="mediatek2021.Mediatek, mediatek2021.Document, mediatek2021.Utilisateur, mediatek2021.SuppressException"%>
<%@page import="java.util.ArrayList, java.util.List"%>
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

	int idDocument = -1;
	if (request.getParameter("id") != null)
		idDocument = Integer.parseInt(request.getParameter("id"));

	String deletedTitre = request.getParameter("titre");
	String msgError = "";
	List<Document> documents;

	if (idDocument > 0) {
		try {
			Mediatek.getInstance().suppressDoc(idDocument);
		} catch (SuppressException e) {
			msgError = e.getMessage();
		}
	}
	%>
	<a href="/Projet_jEE/index.jsp"><button>&larr; Retour au
			menu</button></a>
	<h1>Service de suppression de documents</h1>
	<%
	if (deletedTitre != null) {
		if (!msgError.equals("")) {
		%>
	<p class="error"><%=msgError%>
	<p>
		<%
		} else {
		%>
	
	<p>
		Vous venez de supprimer
		<%=deletedTitre%>
	<p>
		<%
		}
	}

	for (int i = 1; i <= types.length; i++) {
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
			<li><div><%=d.toString()%>
					--- <a
						href="/Projet_jEE/supprDocument.jsp?id=<%=d.data()[0]%>&titre=<%=d.data()[1]%>"><button>
							Supprimer</button></a>
				</div></li>
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