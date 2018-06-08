<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('DANCER')">

	<div class="btn-group btn-group-xs" role="group" aria-label="label">
		<jstl:if test="${curricula.personalRecord != null}">
			<button
				onclick="javascript:location.href='personalrecord/dancer/edit.do?personalrecord=${curricula.personalRecord.id}&q=${curricula.id}'"
				type="button" class="btn btn-default">
				<spring:message code="curricula.edit.personalRecord" />
			</button>
		</jstl:if>

		<jstl:if test="${curricula.personalRecord == null}">
			<button
				onclick="javascript:location.href='personalrecord/dancer/create.do?q=${curricula.id}'"
				type="button" class="btn btn-default">
				<spring:message code="curricula.add.personalRecord" />
			</button>
		</jstl:if>

		<button
			onclick="javascript:location.href='stylerecord/dancer/create.do?q=${curricula.id}'"
			type="button" class="btn btn-default">
			<spring:message code="curricula.add.styleRecords" />
		</button>

		<button
			onclick="javascript:location.href='customrecord/dancer/create.do?q=${curricula.id}'"
			type="button" class="btn btn-default">
			<spring:message code="curricula.add.customRecords" />
		</button>

		<button
			onclick="javascript:location.href='endorserrecord/dancer/create.do?q=${curricula.id}'"
			type="button" class="btn btn-default">
			<spring:message code="curricula.add.endorserRecords" />
		</button>
		
		<br /><br />
		
		<security:authentication property="principal.id" var="id" />
		<button
			onclick="javascript:location.href='curricula/dancer/mylist.do?q=${curricula.id}'"
			type="button" class="btn btn-default">
			<spring:message code="curricula.return" />
		</button>

		<br />

	</div>

</security:authorize>