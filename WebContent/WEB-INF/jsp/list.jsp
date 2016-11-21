<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>秒杀列表页</title>
<%@include file="common/head.jsp"%>
<%@include file="common/tag.jsp"%>
</head>
<body>
	<!-- 页面显示界面 -->
	<div class="container">
		<div class="panel panel-default">
			<!-- 显示内容居中 -->
			<div class="panel-heading text-center">
				<h1>秒杀列表</h1>
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
							<th>详情</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="sk">
							<tr>
								<td>${sk.name}</td>
								<td>${sk.num}</td>
								<td><fmt:formatDate value="${sk.startTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${sk.endTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${sk.createTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><a class="btn btn-info"
									href="${pageContext.request.contextPath}/seckill/${sk.seckillId}/detail"
									target="_blank">link</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>