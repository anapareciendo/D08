
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${vacio == true}">
	<spring:message code="audit.property.empty" />
</jstl:if>

<jstl:if test="${vacio != true}">
<form:form action="audit/auditor/save.do" modelAttribute="audit">

	<form:hidden path="draft"/>
	<form:hidden path="moment"/>

	<acme:select items="${properties}" itemLabel="name" code="audit.property" path="property"/>

	<acme:textarea code="audit.text" path="text"/>
	<acme:textarea code="audit.attachments" path="attachments"/>
	
	<input type="submit" name="draft" value="<spring:message code="audit.draft" />" />
	<input type="button" name="cancel" value="<spring:message code="audit.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>
</jstl:if>

