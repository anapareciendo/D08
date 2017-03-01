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

<display:table name="socialIdentity" id="socialIdentity" requestURI="socialIdentity/list.do"
	pagesize="5" class="displaytag">
	
			
	<display:column>
	  	<a href="socialIdentity/delete.do?socialIdentityId=${socialIdentity.id}">
	 			<spring:message code="socialIdentity.delete" var="socialIdentityHeader" />
		  		<jstl:out value="${socialIdentityHeader}" />
		 </a>
	</display:column>
	
	
	<spring:message code="socialIdentity.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader }" sortable="true" />
	
	<spring:message code="socialIdentity.nameSocial" var="nameSocialHeader" />
	<display:column property="nameSocial" title="${nameSocialHeader }" sortable="true" />
	
	<spring:message code="socialIdentity.urlSocial" var="urlSocialHeader" />
	<display:column property="urlSocial" title="${urlSocialHeader }" sortable="true" />

	<security:authorize access="hasRole('LESSOR')">
	<display:column>
	  	<a href="socialIdentity/edit.do?socialIdentityId=${socialIdentity.id}">
	 			<spring:message code="socialIdentity.edit" var="editHeader" />
		  		<jstl:out value="${editHeader}" />
		 </a>
	</display:column>
	</security:authorize>
	
</display:table>


