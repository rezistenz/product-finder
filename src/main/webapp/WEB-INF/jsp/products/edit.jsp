<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet"href="<c:url value="/resources/css/displaytag-table.css"/>"></link>

<c:url var="saveURL" value="/products/save"/>
<c:url var="cancelURL" value="/products/list"/>

<form:form commandName="productForm" action="${saveURL}" method="POST">
	
	<form:hidden path="id"/>
	
	<form:errors path="*">
		<div class="warning alert">
			<spring:message code="error.global" />
		</div>
	</form:errors>
	
	<table>
		<tr>
			<td><spring:message code="product.name"/></td>
			<td><form:input path="name"/></td>
			<td>
				<form:errors path="name" htmlEscape="false" />
			</td>
		</tr>
		<tr>
			<td><spring:message code="product.price"/></td>
			<td><form:input path="price"/></td>
			<td>
				<form:errors path="price" htmlEscape="false" />
			</td>
		</tr>
		<tr>
			<td><spring:message code="category.name"/></td>
			<td>
				<form:select path="categoryId">
					<form:option label="..." value="0"/>
					<form:options 
						items="${categories}" 
						itemLabel="name" 
						itemValue="id"/>
				</form:select>
			</td>
			<td>
				<form:errors path="categoryId" htmlEscape="false" />
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<input type="submit" value="<spring:message code="save"/>" />
				<input type="button" value="<spring:message code="cancel"/>" onclick="btnCancelClick()"/>
			</td>
		</tr>
	</table>
	
</form:form>

<script>
	function btnCancelClick(){
		location.href='${cancelURL}';
	}
</script>