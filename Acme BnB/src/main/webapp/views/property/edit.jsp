
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="property/lessor/edit.do" modelAttribute="property">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="totalRate"/>

	<acme:textbox code="property.name" path="name"/>
	<acme:textbox code="property.ratePerDay" path="ratePerDay"/>
	<acme:textbox code="property.description" path="description"/>
	<acme:textbox code="property.address" path="address"/>
	
	<input type="submit" name="save" value="<spring:message code="property.save" />" />
	
	 <jstl:if test="${edit == true}">
	<input type="submit" name="delete"
			value="<spring:message code="property.delete" />"
			onclick="return confirm('<spring:message code="property.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel" value="<spring:message code="property.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>