
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="comment/create.do" modelAttribute="comment">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="comment.title" path="title"/>
	<acme:textbox code="comment.text" path="text"/>
	<acme:textbox code="comment.star" path="star"/>
	<acme:select items="${commentable}" itemLabel="userAccount.username" code="comment.commentable" path="commentable"/>
	
	<input type="submit" name="save" value="<spring:message code="comment.save" />" />
	<input type="button" name="cancel" value="<spring:message code="comment.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>