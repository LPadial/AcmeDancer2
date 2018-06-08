<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMINISTRATOR')">

	<b><spring:message code="administrator.minCourse" /> </b>
	<jstl:out value="${course[0]}" />
	<br />
	<b><spring:message code="administrator.stanCourse" /> </b>
	<jstl:out value="${course[1]}" />
	<br />
	<b><spring:message code="administrator.avgCourse" /> </b>
	<jstl:out value="${course[2]}" />
	<br />
	<b><spring:message code="administrator.maxCourse" /> </b>
	<jstl:out value="${course[3]}" />
	<br />


	<b><spring:message code="administrator.minApp" /> </b>
	<jstl:out value="${app[0]}" />
	<br />
	<b><spring:message code="administrator.avgApp" /> </b>
	<jstl:out value="${app[1]}" />
	<br />
	<b><spring:message code="administrator.stanApp" /> </b>
	<jstl:out value="${app[2]}" />
	<br />
	<b><spring:message code="administrator.maxApp" /> </b>
	<jstl:out value="${app[3]}" />
	<br />

	<b><spring:message code="administrator.minTutorialAcad" /> </b>
	<jstl:out value="${tutorialAcad[0]}" />
	<br />
	<b><spring:message code="administrator.avgTutorialAcad" /> </b>
	<jstl:out value="${tutorialAcad[1]}" />
	<br />
	<b><spring:message code="administrator.maxTutorialAcad" /> </b>
	<jstl:out value="${tutorialAcad[2]}" />
	<br />


	<b><spring:message code="administrator.minTutorialShow" /> </b>
	<jstl:out value="${tutorialShow[0]}" />
	<br />
	<b><spring:message code="administrator.avgTutorialShow" /> </b>
	<jstl:out value="${tutorialShow[1]}" />
	<br />
	<b><spring:message code="administrator.maxTutorialShow" /> </b>
	<jstl:out value="${tutorialShow[2]}" />
	<br />


	<b><spring:message code="administrator.listTutorialSee" /> </b>
	<jstl:forEach var="c" items="${tutorialSee}" varStatus="loop">
		<jstl:out value="${c.title}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />


	<b><spring:message code="administrator.avgChirpActor" /> </b>
	<jstl:out value="${chirpActor}" />
	<br />

	<b><spring:message code="administrator.avgChirpSubcription" /> </b>
	<jstl:out value="${chirpSubcription}" />
	<br />

	<!-- 2.0 -->

	<b><spring:message code="administrator.ratioDancerByCurricula" /> </b>
	<jstl:out value="${curriculaRatio}" />
	<br />

	<b><spring:message code="administrator.styleByOrderCourses" /> </b>
	<jstl:forEach var="c" items="${styleCourse}" varStatus="loop">
		<jstl:out value="${c.name}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.styleDancerTeach" /> </b>
	<jstl:forEach var="c" items="${styleDancer}" varStatus="loop">
		<jstl:out value="${c.name}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.avgFoldersPerActor" /> </b>
	<jstl:forEach var="c" items="${avgFoldersPerActor}" varStatus="loop">
		<jstl:out value="${c[0]}" />:
    <jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />


	<b><spring:message code="administrator.avgMailMessage" /> </b>
	<jstl:out value="${avgMailMessage}" />
	<br />


	<b><spring:message code="administrator.avgMailMessageSpamPerActor" />
	</b>
	<jstl:forEach var="c" items="${avgMailMessageSpamPerActor}"
		varStatus="loop">
		<jstl:out value="${c[0]}" />:
    <jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />


	<b><spring:message code="administrator.avgBannerByAcademy" /> </b>
	<jstl:forEach var="c" items="${avgBannerByAcademy}" varStatus="loop">
		<jstl:out value="${c[0]}" />:
    <jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.ratioAcademyBanner" /> </b>
	<jstl:out value="${ratioAcademyBanner}" />
	<br />




	<b><spring:message code="administrator.ratioDancerByCurricula" /> </b>
	<jstl:out value="${curriculaRatio}" />
	<br />

	<b><spring:message code="administrator.styleByOrderCourses" /> </b>
	<jstl:forEach var="c" items="${styleCourse}" varStatus="loop">
		<jstl:out value="${c.name}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.styleDancerTeach" /> </b>
	<jstl:forEach var="c" items="${styleDancer}" varStatus="loop">
		<jstl:out value="${c.name}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.avgFoldersPerActor" /> </b>
	<jstl:forEach var="c" items="${avgFoldersPerActor}" varStatus="loop">
		<jstl:out value="${c[0]}" />:
    <jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<!-- Acme-Dancer 2.0 -->

	<b><spring:message code="administrator.ratioDancerByCurricula" /> </b>
	<jstl:out value="${curriculaRatio}" />
	<br />

	<b><spring:message code="administrator.styleByOrderCourses" /> </b>
	<jstl:forEach var="c" items="${styleCourse}" varStatus="loop">
		<jstl:out value="${c.name}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.styleDancerTeach" /> </b>
	<jstl:forEach var="c" items="${styleDancer}" varStatus="loop">
		<jstl:out value="${c.name}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.avgFoldersPerActor" /> </b>
	<jstl:forEach var="c" items="${avgFoldersPerActor}" varStatus="loop">
		<jstl:out value="${c[0]}" />:
    <jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.avgMailMessage" /> </b>
	<jstl:out value="${avgMailMessage}" />
	<br />


	<b><spring:message code="administrator.avgMailMessageSpamPerActor" />
	</b>
	<jstl:forEach var="c" items="${avgMailMessageSpamPerActor}"
		varStatus="loop">
		<jstl:out value="${c[0]}" />:
    <jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />


	<b><spring:message code="administrator.avgBannerByAcademy" /> </b>
	<jstl:forEach var="c" items="${avgBannerByAcademy}" varStatus="loop">
		<jstl:out value="${c[0]}" />:
    <jstl:out value="${c[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	<br />

	<b><spring:message code="administrator.ratioAcademyBanner" /> </b>
	<jstl:out value="${ratioAcademyBanner}" />
	<br />


</security:authorize>
