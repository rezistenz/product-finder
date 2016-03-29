<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet"href="<c:url value="/resources/css/displaytag-table.css"/>"></link>

<c:url var="createURL" value="/products/create"/>
<a href="${createURL}"><spring:message code="create"/></a>


<c:url var="searchURL" value="/products/list"/>

<form:form commandName="productFilter" action="${searchURL}">
	<table>
		<tr>
			<td>
				<form:label path="categoryName">
					<spring:message code="category.name"/>:
				</form:label>
				<form:input path="categoryName"/>
			</td>
			
			<td>
				<form:label path="productName">
					<spring:message code="product.name"/>:
				</form:label>
				<form:input path="productName"/>
			</td>
			
			<td>
				<form:label path="priceFrom">
					<spring:message code="product.price.from"/>:
				</form:label>
				<form:input path="priceFrom"/>
			</td>
			
			<td>
				<form:label path="priceTo">
					<spring:message code="product.price.to"/>:
				</form:label>
				<form:input path="priceTo"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="pageSize">
					<spring:message code="list.page.size"/>:
				</label>
				<select
					id="pageSize" 
					name="pageSize">
					<option value="5" ${(pagingInfo.pageSize == 5)?'selected="selected"':''}>5</option>
					<option value="10" ${(pagingInfo.pageSize == 10)?'selected="selected"':''}>10</option>
					<option value="15" ${(pagingInfo.pageSize == 15)?'selected="selected"':''}>15</option>
					<option value="20" ${(pagingInfo.pageSize == 20)?'selected="selected"':''}>20</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="<spring:message code="filter"/>" />
			</td>
		</tr>
	</table>
</form:form>

<display:table 
	list="${productPagedList}"
	requestURI="/products/list"
	sort="external"
	id="item">
	<display:column 
		titleKey="product.id"
		property="id" 
		sortProperty="id" 
		sortable="true"/>
	<display:column 
		titleKey="category.name" 
		property="categoryName" 
		sortProperty="category_name" 
		sortable="true"/>
	<display:column 
		titleKey="product.name" 
		property="name" 
		sortProperty="name" 
		sortable="true"/>
	<display:column 
		titleKey="product.price" 
		property="price" 
		sortProperty="price" 
		sortable="true" 
		format="{0,number,0.00}"/>
	<display:column 
		titleKey="actions">
		<c:url var="editURL" value="/products/edit">
			<c:param name="id" value="${item.id}"/>
		</c:url>
		<a href="${editURL}"><spring:message code="edit"/></a>
	</display:column>
</display:table>


