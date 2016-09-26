<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>充值_证联</title>
<link
	href="http://58.30.231.28:8080/static/css/v1/pay/public.css?v=V1.01.0044_Release"
	rel="stylesheet" type="text/css">
<link
	href="http://58.30.231.28:8080/static/css/v1/pay/account.css?v=V1.01.0044_Release"
	rel="stylesheet" type="text/css">
<link
	href="http://58.30.231.28:8080/static/css/v1/pay/pay.css?v=V1.01.0044_Release"
	rel="stylesheet" type="text/css">
<script src="http://58.30.231.28:8080/static/js/jquery.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
	
	<div class="header_box">
		<div class="header">
			<h1 class="logo">
				<a href="/website/"><img alt="证联金融" title="证联金融"
					src="http://58.30.231.28:8080/static/img/v1/pay/logo.png"></a><i
					class="ver_txt">收银台</i>
			</h1>
		</div>
	</div>

	<!--导航 end-->
	<!--主内容 str -->
	<div class="container">
		<div class="step mb10">
			<ol class="step-4">
				<!-- 改变ol里的step-数字 即可 1.2.3.4
				<li class="li-1">1.选择银行</li>
				<li class="li-2">2.填写银行卡信息</li>
				<li class="li-3">3.登录到网上银行充值</li> -->
				<li class="li-4">交易完成</li>
			</ol>
		</div>
		<div class="mod_box">
			<div class="hd hd2">
				<h3 class="title blod">交易结果</h3>
				<div class="bd">
					<!--提示 -->
					<div class="txtC">
						<div class="result_tips">
							<div class="set_height clearfix">
								<!--icon调用的class   pop_s:√  pop_war:! pop_fail:X-->
								<span class="pop_s"></span>
								<p class="words f16b">${errMsg}</p>
							</div>
						</div>
					</div>
					<!--/提示 -->
				</div>
			</div>
		</div>
		<!--主内容 end -->
		<!--footer -->
		<!--footer -->
		<div class="footer">
			<p class="foot_nav">
				<a href="/website/help/index.htm" target="_blank">关于我们</a> |
				<a href="/website/help/service.htm" target="_blank">隐私条款</a> | 
				<a href="/website/help/mer.htm" target="_blank">合作加盟</a> | 
				<a href="/website/help/reg.htm" target="_blank">会员服务</a>
			</p>
			<p class="copyright">Copyright &copy; 2015 北京证联资本管理有限责任公司版权所有</p>
			<p class="contact_info">
				<span>联系电话：010-84298418</span> <span>传真：010-84299579</span>
				<!--  <span>版本：2013V1.4 Beta</span> -->
			</p>
			<p class="icp">
				<a href=" http://www.miibeian.gov.cn/" target="_blank">京ICP备15034871号</a>
			</p>
		</div>
		<script src="/static/js/v1/pay/account.js?v=V1.01.0044_Release"></script>
</body>
</html>