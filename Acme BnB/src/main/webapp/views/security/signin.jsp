
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="security/signin.do" modelAttribute="${authority}">
	
	<acme:textbox code="security.username" path="username"/>
	<acme:password code="security.password" path="password1"/>
	<acme:password code="security.password2" path="password2"/>
	<br/>

	<acme:textbox code="security.name" path="name"/>
	<acme:textbox code="security.surname" path="surname"/>
	<acme:textbox code="security.email" path="email"/>
	<acme:textbox code="security.phone" path="phone"/>
	<acme:textbox code="security.picture" path="picture"/>
	
	<br/>
	<jstl:if test="${authority == 'tenant2'}">
		<acme:textbox code="security.creditCard.holder" path="creditCard.holder"/>
		<acme:textbox code="security.creditCard.brand" path="creditCard.brand"/>
		<acme:textbox code="security.creditCard.number" path="creditCard.number"/>
		<acme:textbox code="security.creditCard.expirationMonth" path="creditCard.expirationMonth"/>
		<acme:textbox code="security.creditCard.expirationYear" path="creditCard.expirationYear"/>
		<acme:textbox code="security.creditCard.cvv" path="creditCard.cvv"/>
	</jstl:if>
	
	<jstl:if test="${authority == 'auditor2'}">
		<acme:textbox code="security.auditor.company" path="company"/>
	</jstl:if>
	
	<br/>
	<acme:checkbox code="security.condition" value="acepto" path="conditions"/>
	
	<br/><br/>
	<%-- <acme:checkbox code="security.condition" path="condition" value="Yes"/> --%>
	
	<input type="submit" name="${authority}" value="<spring:message code="security.submit" />" />
	<input type="button" name="cancel" value="<spring:message code="security.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>