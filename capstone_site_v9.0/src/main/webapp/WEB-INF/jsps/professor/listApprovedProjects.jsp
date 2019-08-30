<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/css/base.css"
	media="all" />
<meta charset="ISO-8859-1">
<title>List All Projects</title>
</head>
<body>
	<div class="header-secondary">
		<div class="container">
			<div class="wrapper">
				<div class="nav-secondary">
					<ul class="nav-horizontal">
						<li><a href="https://slate.sheridancollege.ca/"
							target="_blank">Slate</a></li>
						<li><a href="https://access.sheridaninstitute.ca/"
							target="_blank">Access Sheridan</a></li>
						<li><a
							href="http://sheridaninstitute.intelliresponse.com/asksheridan/"
							target="">ASK.Sheridan</a></li>
						<!--  <li><a href="/life-at-sheridan/student-services/library-services" target="">Library</a></li> -->
					</ul>
				</div>


				<div class="cta-header-container">
					<ul class="nav-horizontal cta-header">
						<!--       <li><a href="/signup" target="">Sign Up</a></li>
                                <li><a href="/login" target="">Login</a></li>
 							-->
						<li><c:url var="logoutUrl" value="/logout" />
							<form action="${logoutUrl}" method="POST" class="from-inline">
								<!--  <input type="submit" value="logout">-->
								<a href="/login" target="" value="logout">Logout</a> <input
									type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}">

							</form></li>

					</ul>
				</div>

			</div>
		</div>
	</div>


	<div class="header-primary">
		<div class="container inner-wrapper logo-container">
			<a href="#"><img src="/resources/images/sheridan-logo.svg"
				class="logo" alt="Sheridan College logo"></a> <a href="#"
				class="nav-mobile-link"> <img alt="mobile navigation"
				src="/resources/images/sheridan-logo.svg">
			</a>
		</div>

		<div class="nav-primary-container" role="navigation">
			<div class="container">
				<ul class="nav-primary nav-horizontal" role="navigation">
					<li><a class="small" href="/">&nbsp;</a></li>
					<li><a href="/professor/about" target="" data-nav="about"
						class="">About Sheridan</a></li>
					<li><a href="/professor/meetProfs" target=""
						data-nav="meet-our-Professors" class="">Our Professors</a></li>
					<li><a href="/professor/projects" target=""
						data-nav="projects" class="">Past Projects</a></li>
					<li><a href="/professor/faq" target="" data-nav="faq" class="">FAQ</a></li>
					<li><a href="/professor/contact" target=""
						data-nav="contactus" class="">Contact Us</a></li>
				</ul>
			</div>
		</div>
		<!-- /.nav-primary-container -->

		<div class="nav-primary-container" role="navigation">
			<div class="container">
				<ul class="nav-primary nav-horizontal" role="navigation">
					<li><a class="small" href="/">&nbsp;</a></li>
					<li><a href="/professor/listProjects" target=""
						data-nav="listProject" class="">List Projects</a></li>
					<li><a href="/professor/listApprovedProjects" target=""
						data-nav="listApprovedProject" class="">Assign Projects</a></li>
					<li><a href="/report" target="" data-nav="report" class="">Reports</a></li>
				</ul>
			</div>
		</div>
	</div>


	<div class="bg-white" align="center">

		<h2>Approved Projects Ready To Be Assigned</h2>
		<c:if test="${!empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<table>
			<tr>
				<th>Title</th>
				<th>Description</th>
				<th>Status</th>
				<th>Assigned To</th>
				<th>Options</th>
			</tr>
			<c:forEach var="p" items="${projectList}">
				<tr>
					<td>${p.title}</td>
					<td>${p.description}</td>
					<td>${p.status}</td>
					<td>${p.groupBean.groupName }</td>
					<td><a
						href="<c:url value="/professor/profAssignProject/${p.projectId}"/>">Edit</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>