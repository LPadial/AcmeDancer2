<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form action="application/dancer/applyTeacher.do">
	<div class="form-group" style="width: 55%;">
		<select class="form-control" name="q">
			<jstl:forEach var="e" items="${curricula}">
				<option value="${e.id}"><spring:message code='application.curricula'/> :  ${e.id}</option>
			</jstl:forEach>
		</select>
	</div>

	<input type="submit" class="btn btn-primary"
		value="<spring:message code="application.save"/>">
</form>