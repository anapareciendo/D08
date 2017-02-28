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
			<spring:message code="dashboard.requestAcceptedByLessor"/>: <jstl:out value="${requestAcceptedByLessor}"/><br>
			<spring:message code="dashboard.requestDeniedByLessor"/>: <jstl:out value="${requestDeniedByLessor}"/><br>
			
			<spring:message code="dashboard.requestAcceptedByTenant"/>: <jstl:out value="${requestAcceptedByTenant}"/><br>
			<spring:message code="dashboard.requestDeniedByTenant"/>: <jstl:out value="${requestDeniedByTenant}"/><br>
			
	<%-- 		<spring:message code="dashboard.lessorMoreAccepted"/>: <jstl:out value="${lessorMoreAccepted}"/><br>
			<spring:message code="dashboard.lessorMoreDenied"/>: <jstl:out value="${lessorMoreDenied}"/><br>
			<spring:message code="dashboard.lessorMorePending"/>: <jstl:out value="${lessorMorePending}"/><br>
			
			<spring:message code="dashboard.tennatMoreAccepted"/>: <jstl:out value="${tennatMoreAccepted}"/><br>
			<spring:message code="dashboard.tennatMoreDenied"/>: <jstl:out value="${tennatMoreDenied}"/><br>
			<spring:message code="dashboard.tennatMorePending"/>: <jstl:out value="${tennatMorePending}"/><br>
			--%>
			
			<spring:message code="dashboard.minSocialIdentityPerActor"/>: <jstl:out value="${minSocialIdentityPerActor}"/><br>
			<spring:message code="dashboard.maxSocialIdentityPerActor"/>: <jstl:out value="${maxSocialIdentityPerActor}"/><br>
			<spring:message code="dashboard.avgSocialIdentityPerActor"/>: <jstl:out value="${avgSocialIdentityPerActor}"/><br>

			<%-- <spring:message code="dashboard.minInvoicePerTenant"/>: <jstl:out value="${minInvoicePerTenant}"/><br>
			<spring:message code="dashboard.maxInvoicePerTenant"/>: <jstl:out value="${maxInvoicePerTenant}"/><br>
			<spring:message code="dashboard.avgInvoicePerTenant"/>: <jstl:out value="${avgInvoicePerTenant}"/><br>   --%>
			
			<spring:message code="dashboard.totalAmountMoney"/>: <jstl:out value="${totalAmountMoney}"/><br>
			
		
			</p>
	</div>
			
		
			
		
