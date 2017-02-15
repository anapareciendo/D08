
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="security/signin.do" modelAttribute="${authority}">
	
	<form:hidden path="userAccount.id" />
	<form:hidden path="userAccount.authorities"/>
	
	<acme:textbox code="security.username" path="userAccount.username"/>
	<acme:textbox code="security.password" path="userAccount.password"/>
	<br/>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<!-- Actor -->
	<form:hidden path="socialIdentities" />
	<form:hidden path="postComments" />
	<form:hidden path="reciveComments" />
	<form:hidden path="userAccount" />
	
	<jstl:choose>
  		<jstl:when test="${authority == 'lessor2'}">
			<!-- SuperUser -->
			<form:hidden path="properties"/>
		</jstl:when>
		
	</jstl:choose>
	
	<acme:textbox code="security.name" path="name"/>
	<acme:textbox code="security.surname" path="surname"/>
	<acme:textbox code="security.email" path="email"/>
	<acme:textbox code="security.phone" path="phone"/>
	<acme:textbox code="security.picture" path="picture"/>
	
	<input type="submit" name="${authority}" value="<spring:message code="security.submit" />" />
	<input type="button" name="cancel" value="<spring:message code="security.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>