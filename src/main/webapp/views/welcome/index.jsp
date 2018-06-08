<%--
 * index.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>

<!-- 2.0 -->
<acme:acme_view entity="${banner}" skip_fields="url" >

<h1><jstl:out value="${banner.academy.commercialName}"/></h1>
<tr>
		<td>
			<spring:message code="banner.index.url" />
		</td>
		<td>
			<a href="${banner.url}">
			<jstl:out value="${banner.url}"/>
			
			</a>
		</td>
	</tr>

	<tr>
		<td>
			<spring:message code="banner.tutorials" />
		</td>
		<td>
		
		<jstl:forEach var="c" items="${banner.academy.tutorials}" varStatus="loop">
		<a href="tutorial/view.do?q=${c.id}">
			<jstl:out value="${c.title}"/>
			</a>
			<jstl:if test="${!loop.last}">, </jstl:if> 
		</jstl:forEach>
		<br />
		</td>
		
		
		
		
	</tr>
	
	<tr>
		<td>
			<spring:message code="banner.courses" />
		</td>
		<td>
		<jstl:forEach var="c" items="${banner.academy.courses}" varStatus="loop">
			<jstl:out value="${c.title}"/>
			<jstl:if test="${!loop.last}">, </jstl:if> 
		</jstl:forEach>
		<br />
		</td>
		
	</tr>
	
	
	
	
</acme:acme_view>


<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 
