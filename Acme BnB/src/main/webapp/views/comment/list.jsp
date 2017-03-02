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

<display:table name="comment" id="comment"
  requestURI="${requestURI }" pagesize="5"
  class="displaytag">
  
  <spring:message code="comment.title" var="titleHeader"/>
  <display:column property="title" title="${titleHeader}" sortable="true" />
  
  <spring:message code="comment.moment" var="momentHeader"/>
  <display:column property="moment" title="${momentHeader}" sortable="true" />
  
  <spring:message code="comment.text" var="textHeader"/>
  <display:column property="text" title="${textHeader}" sortable="false" />
  
  <spring:message code="comment.star" var="starHeader"/>
  <display:column property="star" title="${starHeader}" sortable="false" />
 
</display:table>
<%-- <jstl:if test="${owner == true}">
  	<div>
		<a href="comment/create.do"> <spring:message code="comment.create" /></a>
	</div>	
</jstl:if> --%>