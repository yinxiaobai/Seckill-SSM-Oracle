<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>秒杀列表页</title>
	<%@include file="common/head.jsp" %>
</head>
<body>
	<!-- 页面显示界面 -->
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>秒杀列表</h2>	
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
							<th>库存</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="sk">
							<tr>
								<td>${sk.name}</td>
								<td>${sk.num}</td>
								<td>${sk.startTime}</td>
								<td>${sk.endTime}</td>
								<td>${sk.createTime}</td>
								<td><a class="btn btn-info" href="#">link</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

</body>
	<!-- 引入jquery,务必在bootstrap之前引入 -->
	<script src="${pageContext.request.contextPath}/bootstrap/jquery.min.js"></script>
	<!-- 最新的bootstrap核心,javascript文件 -->
    <script src="${pageContext.request.contextPath}/bootstrap/bootstrap.min.js"></script>
</html>