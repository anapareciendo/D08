
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="property/lessor/edit.do" modelAttribute="property">

	<acme:textbox code="finder.destinationCity" path="destinationCity"/>
	<acme:textbox code="finder.maxPrice" path="maxPrice"/>
	<acme:textbox code="finder.minPrice" path="minPrice"/>
	<acme:textbox code="finder.keyword" path="keyword"/>
	<acme:textbox code="finder.address" path="address"/>
	<acme:textarea code="finder.description" path="description"/>

	<input type="submit" name="save" value="<spring:message code="finder.save" />" />
	<input type="reset"  name="reset" value="<spring:message code="finder.reset" />"/>
	<input type="button" name="cancel" value="<spring:message code="finder.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>