
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="request/tenant/make.do" modelAttribute="req">

	<form:hidden path="id" />
	
	<acme:textbox code="request.checkIn" path="checkIn"/>
	<acme:textbox code="request.checkOut" path="checkOut"/>
	<acme:textbox code="request.smoke" path="smoke"/>
	
	<input type="submit" name="save" value="<spring:message code="property.save" />" />
	<input type="button" name="cancel" value="<spring:message code="property.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>