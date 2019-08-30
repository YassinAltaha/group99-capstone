<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>    
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form" %>
<!DOCTYPE html>

<html lang="en" dir="ltr">
   <link rel="stylesheet" type="text/css" href="/resources/css/base.css" media="all" />
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
  <body>

    <div class="header-secondary">
    <div class="container">
        <div class="wrapper">
                    <div class="nav-secondary">
                        <ul class="nav-horizontal">
                                <li><a href="https://slate.sheridancollege.ca/" target="_blank">Slate</a></li>
                                <li><a href="https://access.sheridaninstitute.ca/" target="_blank">Access Sheridan</a></li>
                                <li><a href="http://sheridaninstitute.intelliresponse.com/asksheridan/" target="">ASK.Sheridan</a></li>
                                
                        </ul>
                    </div>


                    <div class="cta-header-container">
                        <ul class="nav-horizontal cta-header">
                              <!--   <li><a href="/signup" target="">Sign Up</a></li> -->
                                <!-- <li><a href="/login" target="">Login</a></li> -->
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
        <a href="#"><img src="resources/images/sheridan-logo.svg" class="logo" alt="Sheridan College logo"></a>
        <a href="#" class="nav-mobile-link">
            <img alt="mobile navigation" src="resources/images/sheridan-logo.svg">
        </a>
    </div>
    <div class="nav-primary-container" role="navigation">
                <div class="container">
                    <ul class="nav-primary nav-horizontal" role="navigation">
                        <li><a class="small" href="/">&nbsp;</a></li>
                            <li><a href="/student/about" target="" data-nav="about" class="">About Sheridan</a></li>
                            <li><a href="/student/meetProfs" target="" data-nav="meet-our-Professors" class="">Our Professors</a></li>
                            <li><a href="/student/projects" target="" data-nav="projects" class="">Past Projects</a></li>
                            <li><a href="/student/faq" target="" data-nav="faq" class="">FAQ</a></li>
                            <li><a href="/student/contact" target="" data-nav="contactus" class="">Contact Us</a></li>
                            </ul>
                </div>
	</div>  <!-- /.nav-primary-container -->
			<div class="nav-primary-container" role="navigation">
                <div class="container">
                    <ul class="nav-primary nav-horizontal" role="navigation">
                        <li><a class="small" href="/">&nbsp;</a></li>
                            <li><a href="/createGroup" target="" data-nav="createGroup" class="">Create Group</a></li>
                            <li><a href="/joinGroup" target="" data-nav="manageGroup" class="">Join Group</a></li>
  							<li><a href="/leaveGroup" target="" data-nav="leaveGroup" class="">Leave Group</a></li>
  							<li><a href="/deleteGroup" target="" data-nav="deleteGroup" class="">Delete Group</a></li> 
  							<li><a href="/viewGroup" target="" data-nav="viewGroup" class="">View Group</a></li>
                            <li><a href="/viewProjects" target="" data-nav="viewProject" class="">View Projects</a></li>
                            </ul>
                </div>
	</div> 
</div>

<div class=".bg-medgrey">
  <div class=".bg-medgrey">
  </div>
</div>
  

<!--  Content body goes here  -->
<div class="bg-white" align="center">

	<br>
	<h2>Create a Group</h2>
	
	<c:url var="url" value="/addGroup" />
	<form:form action="${url }" modelAttribute="groupBean" method="post">
	<form:input type="hidden" path="groupId" />
	Group Name: &nbsp;&nbsp; <form:input path="groupName" /> <br><br>
	
	<input type="submit" value="Submit"> 
	
	</form:form>
	<br><br>
	
	${groupSuccess }
	

</div>
  </body>
</html>