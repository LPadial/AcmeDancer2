<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ACADEMY')">
<spring:message code="banner.url" var="url" />
<spring:message code="banner.academy" var="academy" />

<display:table name="banner" id="row" requestURI="banner/academy/list.do"
			pagesize="8" class="table table-over">
			<spring:message code="banner.edit" var="edit"></spring:message>
			<spring:message code="banner.delete" var="delete"></spring:message>
			<display:column property="url" title="${url}" />
			<display:column title="${academy}">
				<a href="academy/view.do?q=${row.academy.id}"> <jstl:out
						value="${row.academy.commercialName}" />
				</a>
			</display:column>
			<display:column title="${edit}">
			
			<a href="banner/academy/edit.do?q=${row.id}"> <spring:message code="banner.edit" /></a>
			
			</display:column>
			
			<display:column title="${delete}">
			
			<a href="banner/academy/delete.do?q=${row.id}"> <spring:message code="banner.delete" /></a>
			
			</display:column>
			
			</display:table>
			
			
	<br/>
			
<a href="banner/academy/create.do">
<spring:message code="banner.create" />

</a>
		
		
		
</security:authorize>