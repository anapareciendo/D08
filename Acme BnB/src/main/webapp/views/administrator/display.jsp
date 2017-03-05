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
			<spring:message code="admin.name"/>: <jstl:out value="${administrator.name}"/><br>
			<spring:message code="admin.surname"/>: <jstl:out value="${administrator.surname}" /><br>
			<spring:message code="admin.email"/>: <jstl:out value="${administrator.email}" /><br>
			<spring:message code="admin.phone"/>: <jstl:out value="${administrator.phone}" /><br>
			<spring:message code="admin.picture"/>: <jstl:out value="${administrator.picture}" /><br>	
		</p>
	</div>
