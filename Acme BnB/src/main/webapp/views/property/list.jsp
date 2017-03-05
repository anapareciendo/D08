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

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="property" id="property"
  requestURI="${requestURI }" pagesize="5"
  class="displaytag">
  
  <spring:message code="property.name" var="nameHeader"/>
  <display:column property="name" title="${nameHeader}" sortable="true" />
  
  <spring:message code="property.ratePerDay" var="ratePerDayHeader"/>
  <display:column property="ratePerDay" title="${ratePerDayHeader}" sortable="true" />
  
  <spring:message code="property.address" var="addressHeader"/>
  <display:column property="address" title="${addressHeader}" sortable="false" />
  
  <spring:message code="property.description" var="descriptionHeader"/>
  <display:column property="description" title="${descriptionHeader}" sortable="false" />
  
  <display:column>
	  <a href="lessor/display.do?lessorId=${property.lessor.id}">
	  	<spring:message code="property.lessor" var="lessorHeader" />
	  	<jstl:out value="${lessorHeader}" />
	  </a>
  </display:column>

	<security:authorize access="isAuthenticated()">
	  <display:column>
	  		<a href="audit/list.do?propertyId=${property.id}">
	  			<spring:message code="property.audit" var="auditHeader" />
	  			<jstl:out value="${auditHeader}" />
	  		</a>
  		</display:column>
	
	</security:authorize>
	<security:authorize access="hasRole('TENANT')">
		<display:column>
	  		<a href="request/tenant/new.do?propertyId=${property.id}">
	  			<spring:message code="master.page.tenant.request.new" var="requestHeader" />
	  			<jstl:out value="${requestHeader}" />
	  		</a>
  		</display:column>
	</security:authorize>
 
  <jstl:if test="${owner == true}">
  		<display:column>
	  		<a href="value/list.do?propertyId=${property.id}">
	  			<spring:message code="property.attributes" var="attributesHeader" />
	  			<jstl:out value="${attributesHeader}" />
	  		</a>
  		</display:column>
  		<display:column>
	  		<a href="property/lessor/edit.do?propertyId=${property.id}">
	  			<spring:message code="property.edit" var="editHeader" />
	  			<jstl:out value="${editHeader}" />
	  		</a>
  		</display:column>
  	</jstl:if>
  	
</display:table>

<jstl:if test="${owner != true}">
  	<div>
		<a href="property/sorted.do"> <spring:message code="property.list.sorted" /></a>
	</div>	
</jstl:if>

<jstl:if test="${owner == true}">
  	<div>
		<a href="property/lessor/create.do"> <spring:message code="property.create" /></a>
	</div>	
</jstl:if>