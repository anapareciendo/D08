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

<display:table name="attribute" id="attribute" requestURI="attribute/admin/list.do"
	pagesize="5" class="displaytag">
	
			
	<display:column>
	  	<a href="attribute/admin/delete.do?attributeId=${attribute.id}">
	 			<spring:message code="attribute.delete" var="attributeHeader" />
		  		<jstl:out value="${attributeHeader}" />
		 </a>
	</display:column>
	
	
	<spring:message code="attribute.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader }" sortable="true" />
	
			
	<display:column>
	  	<a href="attribute/admin/edit.do?attributeId=${attribute.id}">
	 			<spring:message code="attribute.edit" var="editHeader" />
		  		<jstl:out value="${editHeader}" />
		 </a>
	</display:column>
	
	
</display:table>


