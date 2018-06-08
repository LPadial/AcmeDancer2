<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('DANCER')">

		<table class="table">
			<jstl:forEach var="e" items="${customrecord.links}">
				<tr>
					<td>${e}</td>
					<td><a href="customrecord/dancer/deleteLink.do?link=${e}"><spring:message code="acme.delete"/></a></td>
				</tr>
			</jstl:forEach>
		</table>
		
</security:authorize>
