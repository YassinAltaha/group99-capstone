<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<html lang="en" dir="ltr">
<link rel="stylesheet" type="text/css" href="/resources/css/base.css"
	media="all" />
<meta charset="UTF-8">
<title>Insert title here</title>
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
						<li><a
							href="/life-at-sheridan/student-services/library-services"
							target="">Library</a></li>
					</ul>
				</div>


				<div class="cta-header-container">
					<ul class="nav-horizontal cta-header">
						<li><a href="/signup" target="">Sign Up</a></li>
						<li><a href="/login" target="">Login</a></li>

					</ul>
				</div>

			</div>
		</div>
	</div>


	<div class="header-primary">
		<div class="container inner-wrapper logo-container">
			<a href="/"><img src="resources/images/sheridan-logo.svg"
				class="logo" alt="Sheridan College logo"></a> <a href="#"
				class="nav-mobile-link"> <img alt="mobile navigation"
				src="resources/images/sheridan-logo.svg">
			</a>
		</div>

		<div class="nav-primary-container" role="navigation">
			<div class="container">
				<ul class="nav-primary nav-horizontal" role="navigation">
					<li><a class="small" href="/">&nbsp;</a></li>
					<li><a href="/about" target="" data-nav="about" class="">About
							Sheridan</a></li>
					<li><a href="/meetProfs" target=""
						data-nav="meet-our-Professors" class="">Our Professors</a></li>
					<li><a href="/projects" target="" data-nav="projects" class="">Projects</a></li>
					<li><a href="/faq" target="" data-nav="faq" class="">FAQ</a></li>
					<li><a href="/contact" target="" data-nav="contactus" class="">Contact
							Us</a></li>
				</ul>
			</div>


		</div>
		<!-- /.nav-primary-container -->
	</div>


	<div class="message-highlight">


		<div class="" align="center">
			<h1>Sign up</h1>

			<table>
				<c:url var="url" value="/saveProf" />
				<form:form action="${url}" modelAttribute="professor" method="POST">
					<tr>
						<td>Name:</td>
						<td><form:input path="profName" /></td>
					</tr>

					<tr>
						<td>Email:</td>
						<td><form:input path="profEmail" /></td>
					</tr>

					<tr>
						<td>Password:</td>
						<td><form:input type="password" path="password" /></td>
					</tr>

					<tr>
						<td>Program:</td>
						<td><form:input path="program" /></td>
					</tr>

					<tr>
						<td colspan="2" align="center"><input type="submit"
							value="Register" /></td>
					</tr>

					<c:forEach var="error" items="${errors}">
						<tr>
							<td colspan="2">${error}</td>
						</tr>
					</c:forEach>

				</form:form>
			</table>

		</div>

	</div>
	<!--  Content body goes here  -->


</body>
</html>