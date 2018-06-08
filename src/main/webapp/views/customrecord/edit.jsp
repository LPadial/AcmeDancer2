<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="btn-group btn-group-xs" role="group" aria-label="label">
		
	<button
		onclick="javascript:location.href='customrecord/dancer/addLink.do?q=${customrecord.id}'"
		type="button" class="btn btn-default">
		<spring:message code="customrecord.add.link" />
	</button>

	<button
		onclick="javascript:location.href='customrecord/dancer/listLinks.do?q=${customrecord.id}'"
		type="button" class="btn btn-default">
		<spring:message code="customrecord.edit.link" />
	</button>
	
</div>

<acme:acme_form type="edit" entity="${customrecord}"
	url="customrecord/dancer/edit.do" numberSteps="0.25">
</acme:acme_form>