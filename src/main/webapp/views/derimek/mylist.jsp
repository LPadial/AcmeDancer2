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


<script>
	$(document).ready(function() {
		$.ajax({
			success : function(result) {
				var tdYo, tdPuntuacion;
				table = document.getElementById("row");
				tr = table.getElementsByTagName("tr");
				for (i = 0; i < tr.length; i++) {
					tdPuntuacion = tr[i].getElementsByTagName("td")[4];
					tdYo = tr[i].getElementsByTagName("td")[5];
					if (tdYo) {
						if (tdYo.innerHTML.toUpperCase().indexOf('C') > -1) {
							tr[i].style.background = "orange";
						}
					}
					if (tdPuntuacion) {
						if (tdYo.indexOf('1')) {
							tr[i].style.background = "blue";
						}

					}
				}
			}
		});

	});
</script>

<security:authorize access="hasRole('ADMINISTRATOR')">

	<acme:list list="${derimeks}" hidden_fields="isCancelled,justification"
		requestURI="derimek/administrator/mylist.do" pagesize="8"
		variable="row">
		<security:authentication property="principal.id" var="id" />
		<jstl:if test="${mios.contains(row)}">
			<td><a href="derimek/administrator/cancelView.do?q=${row.id}">
					<spring:message code="derimek.cancel" />
			</a></td>
		</jstl:if>
	</acme:list>

</security:authorize>
