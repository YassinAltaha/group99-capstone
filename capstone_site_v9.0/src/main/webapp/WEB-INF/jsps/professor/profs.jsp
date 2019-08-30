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



	<br />
	<br />

	<div class="content" role="main" class="aligncenter">


		<div class="inner-wrapper">
			<div class="inner-wrapper title-block-wrapper">
				<h2>Mark Orlando</h2>
			</div>
			<img src="/resources/images/mark_orlando.jpg" class="alignright"
				alt="Mark Orlando" width="380" height="253">


			<p>
				<strong>Professor, School of Applied Computing</strong>
			</p>
			<p>
				Mark Orlando is a faculty member and school coordinator in the
				School of Applied Computing. Mark has taught at Sheridan since 2001
				and has held several positions within the college since that time
				including former Chair of Sheridan’s Academic Council (now called
				the Senate), Academic Advisor, Coordinator of the Information
				Technologies Support Services Program, and Associate Dean, Applied
				Computing.<br> <br> Prior to coming to Sheridan, Mark
				worked in industry in different areas of business and information
				systems. Mark brings that real-world experience to the
				classroom.&nbsp; He teaches courses in diverse areas such as Java
				programming, systems analysis, Web development and programming, user
				interface design, operating systems, and mobile device
				management.&nbsp; Mark also supervises Systems Analyst students in
				their graduating year as they work on their Capstone projects.<br>
				<br> Mark holds a Bachelor of Arts from the University of
				Toronto and a Masters of Science in Information Systems from
				Athabasca University where his research was in the field of
				Agent-based systems.
			</p>
		</div>
		<br /> <br />

		<div class="inner-wrapper">
			<div class="inner-wrapper title-block-wrapper">
				<h2>Jerry Kotub</h2>
			</div>

			<img src="/resources/images/jerry_kotub.jpg" class="alignright"
				alt="Jerry Kotub" width="380" height="253">


			<p>
				<strong>Professor, School of Applied Computing</strong>
			</p>
			<p>
				Jerry has degrees in mathematics, computer science and adult
				education. He worked in the manufacturing industry for over 25 years
				before joining Sheridan in 1999. His broad industry experience
				included development of operations and supply chain management
				systems, as well as sales and marketing systems. Jerry has recently
				worked as a consultant to police and health services, as well as
				small commercial services companies, to maintain his connection with
				industry and benefit Sheridan’s applied learning environment.<br>
				<br> As the Systems Analyst program coordinator and professor,
				Jerry is part of a dedicated and collaborative team of faculty
				working to implement a unique approach to applied learning called
				Capstone Based Learning (CBL). In the third year of this advanced
				diploma program, student teams engage with clients to solve a real
				information technology problem. The capstone project provides an
				applied learning focus for curriculum delivery and engage numerous
				faculty members who teach and mentor student teams as they apply
				their skills and learning to a specific client technology problem.<br>
				<br> Jerry has taught courses in web technologies, programming,
				systems analysis and design, user interface design, software testing
				and quality assurance but his current interest and focus is in the
				design of enterprise systems incorporating mobile and cloud
				services.
			</p>
		</div>

		<br /> <br />

		<div class="inner-wrapper">
			<div class="inner-wrapper title-block-wrapper">
				<h2>Rich Smith</h2>
			</div>

			<img src="/resources/images/rich_smith.jpg" class="alignright"
				alt="Jerry Kotub" width="380" height="253">


			<p>
				<strong>Professor, Faculty of Applied Sciences and
					Technology</strong>
			</p>

			<p>
				Hi, My name is Rich and I've spent my entire career working with
				software. I am currently a professor at a local college where I
				teach students how to write good, clean, well-managed code! I also
				spent many years at Microsoft where I worked with many great
				programmers and a few not so great ones. Programmers sometimes make
				the mistake of focusing too much on writing 'clever' code and not
				enough on writing clean, readable, maintainable code. <br> <br>
				Over the years, I've come to realize is that it is a programmer's
				style that is the most important factor in getting the really good
				jobs. Unfortunately, it is the thing that is most often ignored in
				education! Please join me as we look at how to improve our
				programming skills and style in order to become the kind of
				programmer that companies will pay anything to get! <br>
				Currently teaching:
			<ul>
				<li>Object Oriented Programming using Java</li>
				<li>Computer Logic</li>
				<li>Developing Web Applications Using C# and ASP.NET.</li>
				<li>Capstone project supervision</li>
				<li>Web development using PHP, HTML and CSS.</li>
				<li>Various development frameworks</li>
			</ul>
		</div>


	</div>



</body>
</html>