
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="value/edit.do" modelAttribute="value">

	<form:hidden path="id" />
	<form:hidden path="property" />

	<acme:textbox code="value.name" path="name"/>
	
	<acme:select items="${attributes}" itemLabel="name" code="value.attribute" path="attribute"/>

	
	<input type="submit" name="save" value="<spring:message code="value.save" />" />
	<%-- <input type="submit" name="delete"
			value="<spring:message code="value.delete" />"
			onclick="return confirm('<spring:message code="value.confirm.delete" />')" />&nbsp; --%>
	<input type="button" name="cancel" value="<spring:message code="value.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>