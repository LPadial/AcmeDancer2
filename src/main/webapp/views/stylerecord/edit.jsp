<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:acme_form type="edit" entity="${stylerecord}"
	url="stylerecord/dancer/edit.do" numberSteps="1">

	<div class="form-group" style="width: 55%;">
		<label for="label"><spring:message code="course.style" /> </label> <select
			name="style">
			<jstl:forEach var="s" items="${styles}">
				<option value="${s.id}">${s.name}</option>
			</jstl:forEach>
		</select>
	</div>

</acme:acme_form>
