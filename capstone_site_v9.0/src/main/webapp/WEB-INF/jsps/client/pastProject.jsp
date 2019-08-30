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

<br/>
		<br/>
		<div class="container" align="center">
			<div class="wrapper block-row" >
				<div align="center" class="block large-text callout-with-stats welcome">


 		<div class="inner-wrapper title-block-wrapper"><h2>Past Projects</h2></div>
				<div class="inner-wrapper">
			            <p><img src="/resources/images/capstone-winners.jpg" alt="capstone winners" />
			            The annual Systems Analyst Capstone Project Competition took place on Wednesday, December 5. Applied Computing Capstones represent the cumulative work of a team of students who engage with an industry partner to develop a technology solution to a non-trivial problem. The project, which is completed over the course of the final year of the program, is integrated with the curriculum and provides students with a practical opportunity to generate a real solution to a real-world problem.</p>
							<p>This year, there were a total of 14 Systems Analyst projects completed by students at both the Trafalgar and Davis campuses. The top four projects were determined by faculty, and those four teams were invited to present at the annual Systems Analyst Capstone Project Competition, a significant milestone that showcases their remarkable work.</p>
							<p>Adjudicated by a panel of six industry professionals, all teams should be extremely proud of their accomplishments. Having worked tirelessly to overcome the inherent challenges presented when working on such projects, the judges were truly impressed with their professionalism, knowledge, skills and quality of work.</p>
							<p>This year’s Systems Analyst Capstone Winning team was Team RADS with their Amazon Echo Application to Manage Meetings. Team members included Raj Patel, Saloni Panchal, Dejoun Robinson and Anthony Rella.</p>
							<p>A sincere thank you to the outstanding faculty members who supported the students through their learning journey. And a special shout-out to the administrative staff who planned and organized the event.</p>
							<p>To catch a glimpse of the solutions our Capstone students developed for their year end project, click on the 2018 Capstone project video trailers below.</p>
							
							<ul>
								<li> </li>
							
							</ul>
							<table>
							
							<tr>
							<th>Team</td>
							<th>Project</td>
							<th>Video</td>
							</tr>
							<tr>
							<td>RADS<p></p>
							<p>(Winners)</p></td>
							<td>Amazon Echo Application&nbsp; to Manage Meetings</td>
							<td><a href="https://www.youtube.com/watch?v=zpG0-aJyX9I&amp;feature=youtu.be" target="_blank" rel="noopener">Youtube Video</a><p></p>
							<p>&nbsp;</p></td>
							</tr>
							<tr>
							<td>MAGS</td>
							<td>Recreational Facility Booking Application</td>
							<td><a href="https://www.youtube.com/watch?v=-xqQEmLjKv4&amp;feature=youtu.be" target="_blank" rel="noopener">Youtube Video</a><p></p>
							<p>&nbsp;</p>
							<p>&nbsp;</p></td>
							</tr>
							<tr>
							<td>Spectzero</td>
							<td>Small Business eCommerce Site</td>
							<td><a href="https://www.youtube.com/watch?v=JQlRdrUj28" target="_blank" rel="noopener">Youtube Video</a></td>
							</tr>
							<tr>
							<td>Innovators</td>
							<td>Vacation and Event Planner</td>
							<td><a href="https://www.youtube.com/watch?v=X6T8Z7zBcGE" target="_blank" rel="noopener">Youtube Video</a><p></p>
							<p>&nbsp;</p></td>
							</tr>
							
							</table>
					<p>Photo (left to right): Professors Rich Smith and Mark Orlando, Team RADS members Raj Patel, Saloni Panchal, Dejoun Robinson, Anthony Rella, and Program Coordinator Jerry Kotuba.</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>