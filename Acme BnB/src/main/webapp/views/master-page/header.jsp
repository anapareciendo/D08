<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme BnB Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/action-1.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a></li>
					<li><a href="dashboard/admin/display.do"><spring:message code="master.page.administrator.dashboard" /></a></li>					
					<li><a href="attribute/admin/list.do"><spring:message code="master.page.administrator.attribute.name" /></a></li>									
					<li><a href="attribute/admin/create.do"><spring:message code="master.page.administrator.attribute.create" /></a></li>
					<li><a href="administrator/edit.do"><spring:message code="master.page.administrator.data" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message code="master.page.customer.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
			<security:authorize access="hasRole('LESSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.lessor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="lessor/edit.do"><spring:message code="master.page.lessor.edit" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('TENANT')">
			<li><a class="fNiv"><spring:message	code="master.page.tenant" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tenant/edit.do"><spring:message code="master.page.tenant.edit" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message	code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="auditor/edit.do"><spring:message code="master.page.auditor.edit" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.signin" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="security/signinLessor.do"><spring:message code="master.page.security.lessor" /></a></li>
					<li><a href="security/signinTenant.do"><spring:message code="master.page.security.tenant" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.property" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="property/list.do"><spring:message code="master.page.property.list" /></a>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>					
					<li><a href="socialIdentity/list.do"><spring:message code="master.page.socialIdentity.list" /> </a></li>
					<li><a href="socialIdentity/create.do"><spring:message code="master.page.socialIdentity.create" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.property" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="property/list.do"><spring:message code="master.page.property.list" /></a>
					<security:authorize access="hasRole('LESSOR')">
						<li><a href="property/lessor/list.do"><spring:message code="master.page.lessor.list" /></a></li>
						<li><a href="request/lessor/accept.do"><spring:message code="master.page.lessor.request.accept" /></a></li>
						<li><a href="request/lessor/denied.do"><spring:message code="master.page.lessor.request.denied" /></a></li>					
					</security:authorize>
					<security:authorize access="hasRole('TENANT')">
						<li><a href="finder/tenant/finder.do"><spring:message code="master.page.property.finder" /></a></li>					
						<li><a href="request/tenant/list.do"><spring:message code="master.page.tenant.request.list" /></a></li>
					</security:authorize>
				</ul>
			</li>
			<security:authorize access="hasRole('LESSOR') or hasRole('TENANT')">
				<li><a class="fNiv"><spring:message code="master.page.comment" /></a>
					<ul>
						<li class="arrow"></li>
					
							<li><a href="comment/list.do"><spring:message code="master.page.comment.list" /></a></li>					
					
					</ul>
				</li>
			</security:authorize>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

