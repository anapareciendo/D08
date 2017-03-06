<%--
 * display.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="audit" id="audit" requestURI="audit/list.do"
	pagesize="5" class="displaytag">

	<spring:message code="audit.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" />
	
	<spring:message code="audit.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />

	<spring:message code="audit.attachments" var="attachmentsHeader" />
	<display:column property="attachments" title="${attachmentsHeader}" sortable="true" />
	
	<jstl:if test="${owner == true}">
		<display:column>
		<a href="audit/auditor/send.do?auditId=${audit.id}" 
			onclick="return confirm('<spring:message code="audit.confirm.send" />')" >
	  		<spring:message code="audit.send" var="sendHeader" />
	  		<jstl:out value="${sendHeader}" />
	  	</a>
	  	</display:column>
	</jstl:if>
	
</display:table>


