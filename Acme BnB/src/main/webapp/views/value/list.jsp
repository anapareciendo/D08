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


<jstl:if test="${vacio != true}">

<display:table name="value" id="value"
  requestURI="${requestURI }" pagesize="5"
  class="displaytag">
  
  <spring:message code="value.attribute" var="attributeHeader"/>
  <display:column property="attribute.name" title="${attributeHeader}" sortable="true" />
  
  <spring:message code="value.name" var="nameHeader"/>
  <display:column property="name" title="${nameHeader}" sortable="true" />
  
  <display:column>
	  <a href="value/edit.do?valueId=${value.id}">
	  	<spring:message code="value.edit" var="editHeader" />
	  	<jstl:out value="${editHeader}" />
	  </a>
  </display:column>
  
  <%-- <display:column>
	  <a href="value/delete.do?valueId=${value.id}">
	  	<spring:message code="value.delete" var="deleteHeader" />
	  	<jstl:out value="${deleteHeader}" />
	  </a>
  </display:column> --%>

</display:table>

</jstl:if>
<jstl:if test="${vacio == true}">
	<spring:message code="value.empty" var="emptyHeader" />
	<jstl:out value="${emptyHeader}" />
</jstl:if>

<div>
	<a href="value/create.do?propertyId=${propertyId}"> <spring:message code="value.create" /></a>
</div>	