<%--
 * edit.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="auditor/edit.do" modelAttribute="auditor">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="auditor.name" path="name"/>
	<acme:textbox code="auditor.surname" path="surname"/>
	<acme:textbox code="auditor.email" path="email"/>
	<acme:textbox code="auditor.phone" path="phone"/>
	<acme:textbox code="auditor.picture" path="picture"/>
	<acme:textbox code="auditor.company" path="company"/>
	
	<input type="submit" name="save" value="<spring:message code="auditor.save" />" />
	<input type="button" name="cancel" value="<spring:message code="auditor.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>

</form:form>