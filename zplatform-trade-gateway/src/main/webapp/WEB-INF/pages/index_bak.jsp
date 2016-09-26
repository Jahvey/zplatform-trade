<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>


<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>收银台_证联</title>
<link
	href="http://58.30.231.28:8080/static/css/v1/pay/public.css?v=V1.01.0044_Release"
	rel="stylesheet" type="text/css" />
<link
	href="http://58.30.231.28:8080/static/css/v1/pay/pay.css?v=V1.01.0044_Release"
	rel="stylesheet" type="text/css" />
<link
	href="http://58.30.231.28:8080/static/css/dialog/jquery-ui.custom.css?v=V1.01.0044_Release"
	rel="stylesheet" type="text/css" />
<script src="http://58.30.231.28:8080/static/js/jquery.js" />
<script
	src="http://58.30.231.28:8080/static/js/plugins/jscroller-0.4.js" />
<script
	src="http://58.30.231.28:8080/static/security/scypt.js?v=V1.01.0044_Release"
	type="text/javascript" />
<script src="http://58.30.231.28:8080/static/js/plugins/jquery-ui.js" />
<script src="http://58.30.231.28:8080/static/js/common/pay-dialog.js" />
<script
	src="http://58.30.231.28:8080/static/js/sainty_popup.js?v=V1.01.0044_Release" />
</head>

<body>
	<!--header str-->

	<div class="header_box">
		<div class="header">
			<h1 class="logo">
				<a href="/website/"><img alt="证联金融" title="证联金融"
					src="http://58.30.231.28:8080/static/img/v1/pay/logo.png"></a><i
					class="ver_txt">收银台</i>
			</h1>
		</div>
	</div>
	<!--/header end-->
	<!--主内容 str -->
	<div class="container">
		<!--订单信息 -->
		<div class="mod_box">
			<div class="hd">
				<h3 class="title f14">订单信息</h3>
			</div>
			<div class="bd pd10">
				<table class="pay_table txtC">
					<thead>
						<tr>
							<th>订单号</th>
							<th>商户名称</th>
							<th>交易时间</th>
							<th>金额(元)</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${order.orderId }</td>
							<td>测试商户</td>
							<td>${order.txnTime }</td>
							<td class="red f14b">${order.txnAmt/100 }</td>
							<td>${order.orderDesc }</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!--/订单信息 -->
		<!--支付信息 -->
		<div class="mod_box">
			<div class="bd pd10 pay_info">
				<div class="mode_tab mt20">


					<a name="a_pay_type" href="javascript:selectPay(1);" class="cur"><span>快捷支付</span></a>
					<a name="a_pay_type" href="javascript:selectPay(2);"><span>网银支付</span></a>
					<!-- <a name="a_pay_type" href="javascript:selectPay(3);"><span>账户支付</span></a> -->
					<script>
						$(function() {
							$("#payType")
									.load(
											"/website/gateway/accPay.htm?orderId=110520150824155800013643&pway=1");
							$("a[name=a_pay_type]").click(function() {
								$("a[name=a_pay_type]").removeClass();
								$(this).addClass("cur");
								$("#payType").load($(this).attr("uri"));
							});
						});
					</script>
					<div id="scroller_container">
						<div id="scroller"></div>
					</div>

					<script>
						$(function() {
							$jScroller.add("#scroller_container", "#scroller",
									"right", 2, 2);
							$jScroller.start();

						});
						function selectPay(num) {

							if (num == 2) {
								$("#bankpay_").show();
								$("#fastpay_").hide();
							} else if (num == 1) {
								$("#fastpay_").show();
								$("#bankpay_").hide();
							}
						}
					</script>
				</div>

				<div id="payType">
					<div id="bankpay_" style="display: none;">
						<!--网银支付 -->
						<form method="post" action="/zplatform-trade/gateway/toNetBank.htm">
							<input type="hidden" value="${order.orderId }" name="orderId">
							<input type="hidden" value="${order.txnAmt }" name="amount" />
							<input type="hidden" value="${txnseqno }" name="txnseqno"/>
							<input type="hidden" value="${order.merId }" name="merchId"/>
							<input type="hidden" value="ZXC00001" name="cashCode"/>
							<input type="hidden" value="${busicode }" name="busicode"/>
							<ul class="mod_list">
								<li class="pt20"><label class="mod_side">应付总价：</label> <strong
									class="red f14">${order.txnAmt/100.00 }</strong> 元</li>
								<li class="checkBankInfo"><label class="mod_side2">请选择银行：</label>
									<p class="myWay">
										<span id="myWay"> <label
											class="bk china_citic bank_cur" title="中国建设银行"></label>
										</span>
									</p>
									<div id="other-bank" class="other-bank">
										<div class="select-banklist per">
											<ul>
												<li><input type="radio" name="bankCode" value="0302"
													checked="" id="ecitic001_radio"> <label
													id="ecitic001" for="ecitic001_radio" class="bk china_citic"
													title="中信银行"></label></li>
											</ul>
											<!-- -->
										</div>
									</div></li>
								<li class="submit">
									<button class="btn btnA" type="submit">去网上银行支付</button></li>
							</ul>
						</form>
						<!--/网银支付 -->
					</div>

					<div id="fastpay_" >
						<form method="post"
							action="/zplatform-trade/gateway/toFastPay.htm">
							<input type="hidden" value="${order.orderId }" name="orderId">
							<input type="hidden" value="${order.txnAmt }" name="amount" />
							<input type="hidden" value="${txnseqno }" name="txnseqno"/>
							<input type="hidden" value="${order.merId }" name="merchId"/>
							<input type="hidden" value="ZLC00001" name="cashCode"/>
							<input type="hidden" value="${busicode }" name="busicode"/>
							<ul class="mt10">
								<li class="checkBankInfo ov_hid pl30">&nbsp; &nbsp; &nbsp;<img
									src="http://58.30.231.28:8080/static/img/v1/pay/fastpay_ico.jpg"
									width="360" height="35">
									<div class="fast_pay clearfix ">



										<div id="other-bank" class="other-bank">

											<div class="select-banklist per">
												<ul>
													<li><input type="radio" name="bankCode" value="0105"
														id="CBC_radio" checked="checked"> <label
														for="CBC_radio" class="bk construction" title="中国建设银行"></label>
													</li>

													<li><input type="radio" name="bankCode" value="0308"
														id="cmb001_radio"> <label id="cmb001"
														for="cmb001_radio" class="bk merchants" title="招商银行"></label>
													</li>
													<li><input type="radio" name="bankCode" value="0305"
														id="cmbc001_radio"> <label id="cmbc001"
														for="cmbc001_radio" class="bk minsheng" title="民生银行"></label>
													</li>
													<li><input type="radio" name="bankCode" value="0304"
														id="hxb001_radio"> <label id="hxb001"
														for="hxb001_radio" class="bk huaxia" title="华夏银行"></label>
													</li>

													<li><input type="radio" name="bankCode" value="0306"
														id="gdb_radio"> <label id="gdb001" for="gdb_radio"
														class="bk guangfa" title="广东发展银行"></label></li>
													<li><input type="radio" name="bankCode" value="0302"
														id="ecitic001_radio"> <label id="ecitic001"
														for="ecitic001_radio" class="bk china_citic" title="中信银行"></label>
													</li>



													<!-- <li>
										<input type="radio" name="bankCode" value="psbc" id="post">
										<label id="post" for="post" class="bk post" title="中国邮政"></label>
										</li> -->


													<li><input type="radio" name="bankCode" value="0102"
														id="icbc001_radio"> <label id="icbc001"
														for="icbc001_radio" class="bk icbc" title="中国工商银行"></label>
													</li>
													<li><input type="radio" name="bankCode" value="0104"
														id="BC_radio"> <label id="BC" for="BC_radio"
														class="bk china" title="中国银行"></label></li>
													<li><input type="radio" name="bankCode" value="0103"
														id="ABC_radio"> <label id="ABC" for="ABC_radio"
														class="bk agricultural" title="中国农业银行"></label></li>
													<li><input type="radio" name="bankCode" value="0301"
														id="comm001_radio"> <label id="comm001"
														for="comm001_radio" class="bk communications" title="交通银行"></label>
													</li>
													<!-- <li>
										<input type="radio" name="bankCode" value="pingan" id="pingan_radio">
										<label id="PINGAN" for="pingan_radio" class="bk pingan" title="平安银行"></label>
									</li>
								
									<li>
										<input type="radio" name="bankCode" value="bon" id="BON_radio">
										<label id="BON" for="BON_radio" class="bk bon" title="南京银行"></label>
									</li> -->


												</ul>


											</div>
										</div>

									</div>

								</li>
								<li class="submit mt20 ml50"><button class="btn btnA"
										type="submit">下一步</button> <!-- <a href="#" class="blues ml30">返 回</a></li> -->
								</li>
							</ul>

						</form>

					</div>
				</div>

			</div>
		</div>
		<!--/支付信息 -->
	</div>
	<!--主内容 end -->
	<!--footer -->
	<div class="footer">
		<p class="foot_nav">
			<a href="/website/help/index.htm" target="_blank">关于我们</a> | <a
				href="/website/help/service.htm" target="_blank">隐私条款</a> | <a
				href="/website/help/mer.htm" target="_blank">合作加盟</a> | <a
				href="/website/help/reg.htm" target="_blank">会员服务</a>
		</p>
		<p class="copyright">Copyright &copy; 2015 北京证联资本管理有限责任公司版权所有.</p>
		<p class="contact_info">
			<span>联系电话：010-84298418</span> <span>传真：010-842995798</span>
			<!--  <span>版本：2013V1.4 Beta</span> -->
		</p>
		<p class="icp">
			<a href=" http://www.miibeian.gov.cn/" target="_blank">京ICP备15034871号</a>
		</p>
	</div>
</body>
</html>

