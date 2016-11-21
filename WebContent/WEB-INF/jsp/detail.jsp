<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>秒杀详情页</title>
<%@include file="common/head.jsp"%>
<%@include file="common/tag.jsp"%>
</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading">
				<h1>${seckill.name}</h1>
			</div>
			<div class="panel-body">
				<h2 class="text-danger">
					<!-- 显示time图标 -->
					<span class="glyphicon glyphicon-time"> </span>
					<!-- 展示倒计时 -->
					<span class="glyphicon" id="seckill-box"></span>
				</h2>
			</div>
		</div>
	</div>
	<!-- 登陆弹出层，输入电话 -->
	<div id="killPhoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>秒杀电话：
					</h3>
				</div>
				<div class="model-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="killPhone" id="killPhoneKey"
								placeholder="填写手机号^o^" class="form-control" />
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<!-- 验证信息 -->
					<span id="killPhoneMessage" class="glyphicon"></span>
					<button type="button" id="killPhoneBtn" class="bbtn btn-success">
						<span class="glyphicon glyphicon-phone"></span> Submit
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
<!-- 使用CND 获取公用js http://www.bootcdn.cn -->
<!-- jQuery cookie操作插件 -->
<script
	src="${pageContext.request.contextPath}/bootstrap/js/jquery.cookie.min.js"></script>
<!-- jQuery countdown操作插件 -->
<script
	src="${pageContext.request.contextPath}/bootstrap/js/jquery.countdown.min.js"></script>
<!-- 开始编写交互逻辑 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/script/seckill.js"></script>
<script type="text/javascript">
	$(function(){
		//使用EL表达式传入参数
		seckill.detail.init({
			seckillId : '${seckill.seckillId}',
			startTime : '${seckill.startTime.time}',//毫秒时间
			endTime : '${seckill.endTime.time}'
		});
	});
</script>
</html>