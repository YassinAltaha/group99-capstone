<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

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
					</ul>
				</div>
				<div class="cta-header-container">
					<ul class="nav-horizontal cta-header">
<!-- 						<li><a href="/signup" target="">Sign Up</a></li>
						<li><a href="/login" target="">Login</a></li> -->
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
					<li><a href="/client/about" target="" data-nav="about"
						class="">About Sheridan</a></li>
					<li><a href="/client/meetProfs" target=""
						data-nav="meet-our-Professors" class="">Our Professors</a></li>
					<li><a href="/client/projects" target="" data-nav="projects"
						class="">Projects</a></li>
					<li><a href="/client/faq" target="" data-nav="faq" class="">FAQ</a></li>
					<li><a href="/client/contact" target="" data-nav="contactus"
						class="">Contact Us</a></li>
				</ul>
			</div>
		</div>
		<!-- /.nav-primary-container -->

		<div class="nav-primary-container" role="navigation">
			<div class="container">
				<ul class="nav-primary nav-horizontal" role="navigation">
					<li><a class="small" href="/">&nbsp;</a></li>
					<li><a href="/addClient" target="" data-nav="addClient"
						class="">Register Client</a></li>
					<li><a href="/addProject" target="" data-nav="addProject"
						class="">Add A Project</a></li>
					<li><a href="/myProject" target="" data-nav="myProject"
						class="">My Projects</a></li>
				</ul>
			</div>
		</div>
		<!-- /.nav-primary-container -->
	</div>

	<!-- CONTENT -->
							<br/>
						<br/>
		<div class="container" align="center">
			<div class="wrapper block-row" >
				<div align="center" class="block large-text callout-with-stats welcome" style="height: 458px;">
 
   					
   					 <div class="inner-wrapper title-block-wrapper"><h2>FAQs</h2></div>

						<div class="inner-wrapper">
						<br/>
						<br/>
  						  
  						<p>In this course, each Capstone group will:</p>
							<ul>
								<li>Develop a working final system using 5 development iterations</li>
								<li>Test the final system</li>
								<li>Deploy the final system</li>
								<li>Demonstrate and/or present the system to the Capstone faculty at the completion of each development iteration</li>
								<li>Hopefully learn a lot, have lots of fun and deliver a great product!</li>
  						  	</ul>
						</div>

				</div>
				
			</div>
		</div>

</body>
</html>