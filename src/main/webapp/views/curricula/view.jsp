<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<table class="table">

	<tr>
		<td><spring:message code="curricula.personalRecord" /></td>
		<td>
			<table class="table">
				<tr>
					<td><spring:message code="personalrecord.fullName" /></td>
					<td>${curricula.personalRecord.fullName}</td>
				</tr>
				<tr>
					<td><spring:message code="personalrecord.email" /></td>
					<td>${curricula.personalRecord.email}</td>
				</tr>
				<tr>
					<td><spring:message code="personalrecord.numWhatsapp" /></td>
					<td>${curricula.personalRecord.numWhatsapp}</td>
				</tr>
				<tr>
					<td><spring:message code="personalrecord.linkedln" /></td>
					<td><a target="_blank"
						href="${curricula.personalRecord.linkedln}">${curricula.personalRecord.linkedln}</a></td>
				</tr>
			</table>
		</td>
	</tr>

	<tr>
		<td><spring:message code="curricula.styleRecord" /></td>
		<td>
			<table class="table">
				<jstl:forEach items="${curricula.styleRecord}" var="e">
					<tr>
						<td><spring:message code="stylerecord.numYear" /></td>
						<td>${e.numYear}</td>
					</tr>
					<tr>
						<td><spring:message code="stylerecord.style" /></td>
						<td>${e.style.name}</td>
					</tr>
					<tr>
						<td colspan="2">
							<hr />
						</td>
					</tr>
				</jstl:forEach>
			</table>
		</td>
	</tr>

	<tr>
		<td><spring:message code="curricula.customRecord" /></td>
		<td>
			<table class="table">
				<jstl:forEach items="${curricula.customRecord}" var="e">
					<tr>
						<td><spring:message code="customrecord.title" /></td>
						<td>${e.title}</td>
					</tr>
					<tr>
						<td><spring:message code="customrecord.text" /></td>
						<td>${e.text}</td>
					</tr>
					<tr>
						<td><spring:message code="customrecord.links" /></td>
						<td>
							<table class="table">
								<jstl:forEach items="${e.links}" var="s">
									<tr>
										<td>${s}</td>
									</tr>
								</jstl:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<hr />
						</td>
					</tr>
				</jstl:forEach>
			</table>
		</td>
	</tr>

	<tr>
		<td><spring:message code="curricula.endorserRecord" /></td>
		<td>
			<table class="table">
				<jstl:forEach items="${curricula.endorserRecord}" var="e">
					<tr>
						<td><spring:message code="endorserrecord.fullName" /></td>
						<td>${e.fullName}</td>
					</tr>
					<tr>
						<td><spring:message code="endorserrecord.contact" /></td>
						<td>${e.contact}</td>
					</tr>
					<tr>
						<td colspan="2">
							<hr />
						</td>
					</tr>
				</jstl:forEach>
			</table>
		</td>
	</tr>

</table>

