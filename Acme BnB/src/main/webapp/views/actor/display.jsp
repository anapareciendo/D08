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
		<h2>
			<spring:message code="actor.profile" var="profileHeader" />
			<jstl:out value="${profileHeader}" />
		</h2>
		<p>
			<spring:message code="actor.name"/>: <jstl:out value="${actor.name}" /><br>
			<spring:message code="actor.surname"/>: <jstl:out value="${actor.surname}" /><br>
			<spring:message code="actor.email"/>: <jstl:out value="${actor.email}" /><br>
			<spring:message code="actor.phone"/>: <jstl:out value="${actor.phone}" /><br>
			<spring:message code="actor.picture"/>: 
			<jstl:if test="${actor.picture != null}">
				<img src="${actor.picture}" alt="">
			</jstl:if>	
		</p>
	</div>
	
	<security:authorize access="hasRole('LESSOR')">
		<p>
			<strong><spring:message code="actor.lessor.amount"/></strong>: <jstl:out value="${amount}" /> Euros<br>
		</p>
	</security:authorize>
	
	<div>
	
		<h3>
			<spring:message code="actor.profile.comments"  />
		</h3>
	<display:table name="comment" id="comment" requestURI="${requestURI}" class="displaytag">	
	<spring:message code="actor.comment.title" var="titleHeader"/>
  	<display:column property="title" title="${titleHeader}" sortable="true" />
  
  	<spring:message code="actor.comment.moment" var="momentHeader"/>
  	<display:column property="moment" title="${momentHeader}" sortable="true" />
  
  	<spring:message code="actor.comment.text" var="textHeader"/>
  	<display:column property="text" title="${textHeader}" sortable="false" />
  
  	<spring:message code="actor.comment.star" var="starHeader"/>
  	<display:column property="star" title="${starHeader}" sortable="false" />
  	
  	<spring:message code="actor.comment.sender" var="senderHeader"/>
  	<display:column title="${senderHeader} " sortable="false">
		<jstl:out value="${comment.sender.userAccount.username}" />
	</display:column>

	</display:table>
	
	</div>
	
		<div>
	
		<h3>
			<spring:message code="actores.social.title.tabla"  />
		</h3>
	<display:table name="social" id="social" requestURI="${requestURI}" class="displaytag">	

	<spring:message code="social.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}" sortable="true"/>
	
	<spring:message code="social.nameSocial" var="nameSocialHeader" />
	<display:column property="nameSocial" title="${nameSocialHeader}" sortable="true"/>
	
	<spring:message code="social.urlSocial" var="urlSocialHeader" />
	<display:column property="urlSocial" title="${urlSocialHeader}" sortable="true"/>

	
	</display:table>
	
	</div>
	
	