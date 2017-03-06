<%--
 * list.jsp
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

	<div>
		<p>
			<spring:message code="auditor.name"/>: <jstl:out value="${auditor.name}"/><br>
			<spring:message code="auditor.surname"/>: <jstl:out value="${auditor.surname}" /><br>
			<spring:message code="auditor.email"/>: <jstl:out value="${auditor.email}" /><br>
			<spring:message code="auditor.phone"/>: <jstl:out value="${auditor.phone}" /><br>
			<spring:message code="auditor.picture"/>: 
			<jstl:if test="${auditor.picture != null}">
				<img src="${auditor.picture}" alt="">
			</jstl:if>		
		</p>
	</div>

		<div>
	
		<h3>
			<spring:message code="actores.social.title.tabla"  />
		</h3>
	<display:table name="social" id="social" requestURI="${requestURI}" class="displaytag">	

	<spring:message code="social.nick" var="nickHeader" />
	<display:column property="social.nick" title="${nickHeader}" sortable="true"/>
	
	<spring:message code="social.nameSocial" var="nameSocialHeader" />
	<display:column property="social.nameSocial" title="${nameSocialHeader}" sortable="true"/>
	
	<spring:message code="social.urlSocial" var="urlSocialHeader" />
	<display:column property="social.urlSocial" title="${urlSocialHeader}" sortable="true"/>

	
	</display:table>
	
	</div>
	