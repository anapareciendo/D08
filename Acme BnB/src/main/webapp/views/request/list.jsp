<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="req" id="req"  requestURI="${requestURI }" pagesize="5" class="displaytag">
  
  <spring:message code="request.checkIn" var="inHeader"/>
  <display:column property="checkIn" title="${inHeader}" sortable="false" />
  
  <spring:message code="request.checkOut" var="outHeader"/>
  <display:column property="checkOut" title="${outHeader}" sortable="false" />
  
  <spring:message code="request.smoke" var="smokeHeader"/>
  <display:column property="smoke" title="${smokeHeader}" sortable="false" />
  
  <spring:message code="request.status" var="statusHeader"/>
  <display:column property="status" title="${statusHeader}" sortable="true" />
  
  <security:authorize access="hasRole('LESSOR')">
  		<display:column>
  			<jstl:if test="${accept == true}">
	  			<a href="request/lessor/toAccept.do?requestId=${req.id}">
	  				<spring:message code="request.lessor.accept" var="changeHeader" />
	  				<jstl:out value="${changeHeader}" />
	  			</a>
	  		</jstl:if>
	  		<jstl:if test="${accept == false}">
	  			<a href="request/lessor/toDeny.do?requestId=${req.id}">
	  				<spring:message code="request.lessor.deny" var="changeHeader" />
	  				<jstl:out value="${changeHeader}" />
	  			</a>
	  		</jstl:if>
  		</display:column>
  </security:authorize>
  
  	
</display:table>
