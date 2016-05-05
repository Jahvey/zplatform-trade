<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<meta charset="UTF-8">
	<title>证联_收银台</title>
	<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style.css">
	<script type="text/javascript" src="<%=basePath %>js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/jquery-1.11.2.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/jscroller-0.4.js"></script>
	<style type="text/css">
	.fastPaybank{
		width: 200px;
	    line-height: 28px;
		cursor: pointer;
		background-color: #fff;
	    border: 1px solid #ddd;
	    display: inline-block;
	    position: relative;
	    vertical-align: middle;
		}
		.icon{float:left; height:30px;background-repeat: no-repeat; color:#07f; display:block; width:120px;}
	</style>

</head>

<body>
<div class="g-hd">
  <div class="g-row">
    <div class="logo"><a href="http://www.vip.com/" ></a></div>
    <div class="hd-logo-nav">收银台</div>
  </div>
</div>
<div class="g-row">
 <div class="wrapper">
		 <ul class="flow-steps">
			<li class="on"><b></b><a href="#">1.选择银行</a><s></s></li>
			<li><b></b><a href="#">2.填写银行卡信息</a><s></s></li>
			<li><b></b><a href="#">3.登录到网上银行充值</a><s></s></li>
			<li><b></b><a href="#">4.交易成功</a><s></s></li>
		</ul>
</div>
  <div class="g-t"> 
    <div class="orders-hd">
      <ul >
        <li class="inline-block-item product-item"> <span class="product-item-desc">订单信息</span> </li>
      </ul>
    </div>
    <table id="tt" class ="f" style="width:1000px;height:auto;">
    <input type="hidden" id="bindFlag" value="${bindFlag }"/>
      <thead>
        <tr>
          <th field="name1" width="50">订单号</th>
          <th field="name2" width="50">机构名称</th>
          <th field="name3" width="50">商户名称</th>
          <th field="name4" width="50">交易时间</th>
          <th field="name5" width="50">金额（元）</th>
          <th field="name6" width="50">备注</th>
        </tr>
      </thead>
      <tbody>
        <tr>
			<td>${orderId }</td>
			<td>${subMerName }</td>
			<td>${merchName }</td>
			<td>${txnTime }</td>
			<td class="red f14b">${amount_y }</td>
			<td>${orderDesc}</td>
		</tr>
      </tbody>
    </table>
  </div>

<div class="mod_box">
			<div class="bd pd10 pay_info">
				<div class="mode_tab mt20">

					
					<a name="a_pay_type" href="javascript:selectPay(1);" id="a_fast" class="cur"><span>快捷支付</span></a>
					<!-- <a name="a_pay_type" href="javascript:selectPay(2);" id="a_bank"><span>网银支付</span></a> -->
					<!--<a name="a_pay_type" href="javascript:selectPay(4);"><span>提现</span></a> -->
					<script type="text/javascript">
						$(function() {
							initFastPay();
							if($("#bindFlag").val()==1||$("#card_size").val()>0){
								$("#other-bank").hide();
							}else{
								$("#other-bank").show();
							}
							initBankPic();
						})
						
						
					</script>
					<!--  <a name="a_pay_type" href="javascript:void(0);" uri="/website/gateway/fastPayment.htm?orderId=110520150824155800013643"><span>快捷支付</span></a> -->
					<!-- <script>
			$(function(){			 
				$("#payType").load("/website/gateway/accPay.htm?orderId=110520150824155800013643");
					$("a[name=a_pay_type]").click(function(){
					$("a[name=a_pay_type]").removeClass();
					$(this).addClass("cur");
					$("#payType").load($(this).attr("uri"));					
				});				
			});
		 </script>  -->
					<div id="scroller_container" style="overflow: hidden;">
						<div id="scroller" style="position: absolute; left: 154px; top: 0px;"></div>
					</div>

					<script type="text/javascript">
						
						
						function selectPay(num){
							if(num==2){
								$("#a_acct").removeClass("cur");
								$("#a_fast").removeClass("cur");
								$("#a_bank").addClass("cur");
								$("#bankpay_").show();
								$("#fastpay_").hide();
								$("#accountpay_").hide();
							}else if(num==1){
								
								
								$("#a_acct").removeClass("cur");
								$("#a_fast").addClass("cur");
								$("#a_bank").removeClass("cur");
								$("#fastpay_").show();
								$("#bankpay_").hide();
								$("#accountpay_").hide();
							}else if(num==3){
								$("#a_acct").addClass("cur");
								$("#a_fast").removeClass("cur");
								$("#a_bank").removeClass("cur");
								$("#accountpay_").show()
								
								$("#fastpay_").hide();
								$("#bankpay_").hide();
								showAccBalance($("#merUserId").val())
							}
						}
						
						function showAccBalance(memberId){
							$.ajax({
								type: "POST",
							  	url: "/zplatform-trade/gateway/showAccount",
							  	data: "rand="+new Date().getTime()+"&memberId="+memberId,
							  	async:false,
							 	dataType: "json",
							 	success:function(json){
									$("#balance_s").html(json.money);
									$("#balance").val(json.money);
									
									if(json.money/1.0<($("#acct_amount").val()/100)){
										
										$("#btnPay").removeClass("btnA").addClass("btn_off").attr("disabled","disabled");
										$("#acct_error").text("账户余额不足");
										$("#payPwd").attr('disabled','disabled');
									}
									if(json.paypwd=="none"){
										$("#acct_error").text("未设置支付密码");
										$("#pwdTip").show();
									}
							 	}
							});
						}
						
						function showInitPwd(){
							$("#initpwd").show();
							$("#accountpay_").hide();
							$("#btn_pwd").click(function(){
								var memberId = $("#merUserId").val();
								var pwd = $("#mod_payPwd").val();
								var pwd_repeat =  $("#mod_payPwd_rep").val();
								//alert(pwd+"--"+pwd_repeat);
								if(pwd!=pwd_repeat){
									$("#err_pwd_tip").show();
									return;
								}
								$.ajax({
									type: "POST",
								  	url: "/zplatform-trade/gateway/initPassWord",
								  	data: "rand="+new Date().getTime()+"&memberId="+memberId+"&pwd="+pwd+"&pwd_rep="+pwd_repeat,
								 	dataType: "json",
								 	success:function(json){
										if(json.retcode=="00"){
											alert("支付密码初始化成功");
											window.location="<%=basePath %>gateway/cash.htm?txnseqno="+$("#acc_txnseqno").val();
										}else{
											alert(json.retinfo);
										}
								 	}
								});
							})
						}
						
					</script>
				</div>

				<div id="payType">
					<div id="accountpay_" style="display:none;">
							<!--账户支付 -->
							<form method="post" action="/zplatform-trade/gateway/accountPay.htm?txnseqno_=${txnseqno }">
								<input type="hidden" value="0" name="balance" id="balance"/>
								<input type="hidden" value="${orderId }" name="orderId">
								<input type="hidden" value="${txnAmt }" name="amount" id="acct_amount"/>
								<input type="hidden" value="${txnseqno }" name="txnseqno" id="acc_txnseqno"/>
								<input type="hidden" value="${merchId }" name="merchId"/>
								<input type="hidden" value="ZLC00001" name="cashCode"/>
								<input type="hidden" value="${busicode }" name="busicode"/>
								<input type="hidden" value="${goodsName }" name="goodsName"/>
								<input type="hidden" value="${merchName }" name="merchName">
								<input type="hidden" value="${subMerName }" name="subMerName" />
								<input type="hidden" value="${memberId }" name="merUserId" id="merUserId" />
								<input type="hidden" value="${busitype }" name="busitype" />
								<input type="hidden" value="${memberIP }" name="memberIP" />
								<input type="hidden" value="${tn }" name="tn" />
								<ul class="mod_list">
								<li>
								</li>
									<li>
										<label class="mod_side">可用余额：</label><strong class="red f14" id="balance_s"></strong> 元 <a href="" class="blue ml30" target="_blank">  </a>
										<span style="color: red;size: 14px" id="acct_error"></span>
									</li>
									<li>
										<label class="mod_side">应付总价：</label>
										<strong class="red f14"></strong>${amount_y } 元</li>
									<li>
										<label class="mod_side">请输入支付密码：</label>
										<input name="pay_pwd" id="payPwd" type="password"/> 
										<span id="pwdTip" style="display:none;"><a class="red" href="javascript:showInitPwd()">初始化支付密码</a></span>
										<p id="errTip" style="display:none;"><span class="onError">支付密码输入错误,再错误3次后账户将被锁定</span></p>
										<li class="submit">
											<input id="btnPay" type="submit" class="btn btnOff" value="确认支付"/>
										</li>
								</ul>
								
							</form>
							<!--/账户支付 -->
						</div>
						<div id="initpwd" style="display:none;">
							<ul class="mod_list">
								<li></li>
								<li>
									<label class="mod_side">支付密码：</label>
									<input name="pay_pwd" id="mod_payPwd" type="password"/> 
								</li>
								<li>
									<label class="mod_side">重复密码：</label>
									<input name="mod_pay_pwd_re" id="mod_payPwd_rep" type="password"/>
									<span id="err_pwd_tip" style="display:none;"><span class="onError">支付密码和重复密码不一致</span></span>
								<li class="submit">
									<input id="btn_pwd" type="submit" class="btn btnOff" value="提交"/>
								</li>
							</ul>
						</div>
						<div id="bankpay_" style="display:none;">
						<!--网银支付 -->
						<form method="post" action="/zplatform-trade/gateway/toNetBank.htm?txnseqno_=${txnseqno }">
							<input type="hidden" value="${txnAmt }" name="amount" />
							<input type="hidden" value="${txnseqno }" name="txnseqno"/>
							<input type="hidden" value="${merchId }" name="merchId"/>
							<input type="hidden" value="ZXC00001" name="cashCode"/>
							<input type="hidden" value="${busicode }" name="busicode"/>
							<input type="hidden" value="${merchName }" name="merchName"/>
							<input type="hidden" value="${subMerName }" name="subMerName" />
							<input type="hidden" value="${busitype }" name="busitype" />
							<input type="hidden" value="${memberIP }" name="memberIP" />
							<input type="hidden" value="${tn }" name="tn" />
							<ul class="mod_list">
								<li class="pt20"><label class="mod_side">应付总价：</label> <strong class="red f14">${order.txnAmt/100.00 }</strong> 元</li>
								<li class="checkBankInfo"><label class="mod_side2">请选择银行：</label>
									<p class="myWay">
										<span id="myWay"> <label class="bk china_citic bank_cur" title="中国建设银行"></label>
										</span>
									</p>
                                    </li>
									<div id="other-bank2" class="other-bank2">
										<div class="select-banklist per">
											<ul>
												<li><input type="radio" name="bankCode" value="0302"
													checked="" id="ecitic001_radio"> <label
													id="ecitic001" for="ecitic001_radio" class="bk china_citic"
													title="中信银行"></label>
											</li>
											</ul>
											<!-- -->
										</div>
									</div>
								<li class="submit">
									<button class="btn btnA" type="submit">去网上银行支付</button></li>
							</ul>
						</form>
						<!--/网银支付 -->
					</div>
					
					<div id="fastpay_">
						
							
				<ul class="mt10"> 
					<li class="checkBankInfo ov_hid pl30">
                      <img src="<%=basePath %>images/fastpay_ico.jpg" width="360" height="35">
						<input type="hidden" id="card_size" value="${fn:length(cardList)}"/>
						
							
							
						</ul>
						
						
						
						<c:if test="${bindFlag=='1'||fn:length(cardList)>0}">
						<form method="post" action="/zplatform-trade/merch/bindPay.htm?txnseqno_=${txnseqno }" id="bank_form">
							<input type="hidden" value="bindingpay" name="payFlag"/>
							<input type="hidden" value="${orderId }" name="orderId"/>
							<input type="hidden" value="${txnAmt }" name="amount" />
							<input type="hidden" value="${txnseqno }" name="txnseqno"/>
							<input type="hidden" value="${merchId }" name="merchId"/>
							<input type="hidden" value="ZLC00001" name="cashCode"/>
							<input type="hidden" value="${busicode }" name="busicode"/>
							<input type="hidden" value="${goodsName }" name="goodsName"/>
							<input type="hidden" value="${merchName }" name="merchName"/>
							<input type="hidden" value="${subMerName }" name="subMerName" />
							<input type="hidden" value="${memberId }" name="merUserId" />
							<input type="hidden" value="${busitype }" name="busitype" />
							<input type="hidden" value="${memberIP }" name="memberIP" />
							<input type="hidden" value="${tn }" name="tn" />
							<input type="hidden" id="rad_bankCode" name="bankCode"/>
							<div class="payment_list per">
							<div class="select-banklist per">		
								<ul style="height:60px;" id="default_bank">
									<c:forEach var="card" items="${cardList }" varStatus="i" >
										<c:if test="${i.index==0 }">
											
											<li>
												<label for="communications_radio" class="fastPaybank" title="${card.bankname }" >
											    	<input type="radio" name="bindCardId"  value="${card.id}" index="${i.index }" checked="checked" />
											    	<span title="" class="" id="bank_lab_${i.index }" ></span>
											    	<span class="card-number">${fn:substring(card.cardno,0, 5)}*********${fn:substring(card.cardno,fn:length(card.cardno)-4, fn:length(card.cardno))} </span>
											    	<input type="hidden" name="bankCode_" id="radio_bank_${i.index}" value="${fn:substring(card.bankcode,0, 4)}"/>
											    </label>
											</li>
										</c:if>
										<c:if test="${i.index!=0 }">
											<li>
												<label for="communications_radio" class="fastPaybank" title="${card.bankname }" >
											    	<input type="radio" name="bindCardId" value="${card.id}" index="${i.index }"/>
											    	<span title="" class="" id="bank_lab_${i.index }" ></span>
											    	<span class="card-number">**${fn:substring(card.cardno,fn:length(card.cardno)-4, fn:length(card.cardno))} </span>
											    	<input type="hidden" name="bankCode_" id="radio_bank_${i.index}" value="${fn:substring(card.bankcode,0, 4)}"/>
											    </label>
											</li>
										</c:if>
								
								
							</c:forEach>
									  
						        </ul>
						        </div>
							</div>
							</form>
							<!-- <a class="blues ml30 f14" href="javascript:void(0);" id="chooseBank" style="color:#06f;text-decoration:underline;"><i>▼</i>选择其他银行</a> -->
						</c:if>
						
						
						
						<form method="post" action="/zplatform-trade/merch/toFastPay.htm?txnseqno_=${txnseqno }" id="other_bank_form">
							<input type="hidden" value="${memberIP }" name="memberIP" />
							<input type="hidden" value="${orderId }" name="orderId"/>
							<input type="hidden" value="${txnAmt }" name="amount" />
							<input type="hidden" value="${txnseqno }" name="txnseqno"/>
							<input type="hidden" value="${merchId }" name="merchId"/>
							<input type="hidden" value="ZLC00001" name="cashCode"/>
							<input type="hidden" value="${busicode }" name="busicode"/>
							<input type="hidden" value="${goodsName }" name="goodsName"/>
							<input type="hidden" value="${merchName }" name="merchName"/>
							<input type="hidden" value="${subMerName }" name="subMerName" />
							<input type="hidden" value="${memberId }" name="merUserId" />
							<input type="hidden" value="${busitype }" name="busitype" />
							<input type="hidden" value=""             name="cardType" id="other_card_type"/>
							<input type="hidden" value="${tn }" name="tn" />
							<div id="other-bank" class="other-bank" style="display:none">
							<input type="hidden" id="bankId" name="bankId" value="0105"/>						
								<div class="select-banklist per">借记卡
								<ul>
										<li>
											<input type="radio" name="bankCode" value="0102" id="CBC_radio" checked="checked" cardtype="1"/>
											<label for="CBC_radio" class="bk icbc" title="中国工商银行"></label>
										</li>
										<li>
										<input type="radio" name="bankCode" value="0105" id="ecitic001_radio" cardtype="1"/>
										<label id="ecitic001" for="ecitic001_radio" class="bk construction" title="中国建设银行"></label>
										</li>
																										
									<li>
										<input type="radio" name="bankCode" value="0103" id="icbc001_radio"  cardtype="1"/>
										<label id="icbc001" for="icbc001_radio" class="bk agricultural" title="中国农业银行"></label>
									</li>
									<li>
	                                  <input type="radio" name="bankCode" value="0303" id="ceb001_radio" cardtype="1"/>
	                                  <label id="ceb001" for="ceb001_radio" class="bk everbright" title="光大银行"></label>
	                              	</li>
		                              <li>
		                                  <input type="radio" name="bankCode" value="0309" id="cib001_radio" cardtype="1"/>
		                                  <label id="cib001" for="cib001_radio" class="bk industrial" title="兴业银行"></label>
		                              </li>
									<li>
										<input type="radio" name="bankCode" value="0104" id="BC_radio" cardtype="1"/>
										<label id="BC" for="BC_radio" class="bk china" title="中国银行"></label>
									</li>
									<!-- <li>
										<input type="radio" name="bankCode" value="0103" id="ABC_radio">
										<label id="ABC" for="ABC_radio" class="bk communications" title="交通银行"></label>
									</li>
									<li>
										<input type="radio" name="bankCode" value="0301" id="comm001_radio">
										<label id="comm001" for="comm001_radio" class="bk huaxia" title="华夏银行"></label>
									</li> -->
									<li>
										<input type="radio" name="bankCode" value="0410" id="pingan_radio" cardtype="1"/>
										<label id="PINGAN" for="pingan_radio" class="bk pingan" title="平安银行"></label>
									</li>
								<!-- 
									<li>
										<input type="radio" name="bankCode" value="bon" id="BON_radio">
										<label id="BON" for="BON_radio" class="bk bon" title="南京银行"></label>
									</li> -->
									
									
								</ul>
								
							
							</div>
						</div>
                         
					</li>
					<li class="submit mt20 ml50"><button class="btn btnA" type="button" id="btn_next">下一步</button>	</li>
                     <!-- <a href="#" class="blues ml30">返 回</a></li> -->
				</li></ul>
                
			</form>
					
					</div>
				</div>

			</div>
		</div>
<script type="text/javascript">
$("#chooseBank").click(function(){
	if($("#other-bank").css("display")=='none'){
		$("#other-bank").show();
		$(this).html("<i>▲</i>隐藏其他银行");
		$("#def_bank").attr("checked","checked");
		$("#default_bank").hide();
	}else{
		
		$("#other-bank").hide();
		$(this).html("<i>▼</i>选择其他银行");
		
		$("#default_bank").show()
	}
});
$("#btn_next").click(function(){
	if($("#other-bank").css("display")=='none'){
		var index=$("#bank_form input:checked ").attr("index");
		var bank_code = $("#radio_bank_"+index).val();
		$("#rad_bankCode").val(bank_code);
		$("#bank_form").submit();
		$("#btn_next").removeClass("btn_off").addClass("btnA").attr("disabled","disabled");
	}else{
		var cardtype =$("#other_bank_form :radio:checked").attr("cardtype");
		$("#other_card_type").val(cardtype);
		$("#other_bank_form").submit();
		$("#btn_next").removeClass("btn_off").addClass("btnA").attr("disabled","disabled");
	}
})


							
function initBankPic(){
	
		var bankcode = $("#default_bank_code").val().substring(0,4);
		$("#def_bank").val(bankcode);
		switch (bankcode) {
			case "0102":
				$("#bank_lab").addClass("bk icbc ").attr("style","margin-left: -1px");
				break;
			case "0308":
				$("#bank_lab").addClass("bk merchants ").attr("style","margin-left: -1px");
				break;
			case "0305":
				$("#bank_lab").addClass("bk minsheng ").attr("style","margin-left: -1px");
			break;
			case "0302":
				$("#bank_lab").addClass("bk china_citic ").attr("style","margin-left: -1px");
			break;
			case "0306":
				$("#bank_lab").addClass("bk guangfa ").attr("style","margin-left: -1px");
			break;
			case "0105"://建行
				$("#bank_lab").addClass("bk construction ").attr("style","margin-left: -1px");
			break;
			case "0103":
				$("#bank_lab").addClass("bk agricultural ").attr("style","margin-left: -1px");
			break;
			case "0104":
				$("#bank_lab").addClass("bk china ").attr("style","margin-left: -1px");
			break;
			case "0304":
				$("#bank_lab").addClass("bk huaxia ").attr("style","margin-left: -1px");
			break;
			
			case "0310":
				$("#bank_lab").addClass("bk spdb ").attr("style","margin-left: -1px");
			break;
			case "0303":
				$("#bank_lab").addClass("bk ceb ").attr("style","margin-left: -1px");
			break;
			case "0403":
				$("#bank_lab").addClass("bk bjb ").attr("style","margin-left: -1px");
			break;
			case "0301":
				$("#bank_lab").addClass("bk bocom ").attr("style","margin-left: -1px");
			break;
			case "0309":
				$("#bank_lab").addClass("bk cib ").attr("style","margin-left: -1px");
			break;
			case "0410":
				$("#bank_lab").addClass("bk pingan ").attr("style","margin-left: -1px");
			break;
			case "0100":
				$("#bank_lab").addClass("bk post ").attr("style","margin-left: -1px");
			break;
			case "0401":
				$("#bank_lab").addClass("bk shanghai ").attr("style","margin-left: -1px");
			break;
		}
	}
</script>
<div class="footer">
	<p class="foot_nav"><a href="" target="_blank">关于我们</a> | <a href="" target="_blank">隐私条款</a> | <a href="" target="_blank">合作加盟</a> | <a href="" target="_blank">会员服务</a></p>
	<p class="copyright">Copyright  © 2015 北京证联资本管理有限责任公司版权所有</p>
	<p class="contact_info"> <span>联系电话：010-84298418</span> <span>传真：010-84299579</span> <!--  <span>版本：2013V1.4 Beta</span> --></p>
	<p class="icp"><a href=" http://www.miibeian.gov.cn/" target="_blank">京ICP备15034871号</a></p>
</div>
<script type="text/javascript">
							
							function initFastPay(){
								var size = $("#card_size").val();
								for(var i=0;i<size;i++){
									var bank_code = $("#radio_bank_"+i).val();
									switch (bank_code) {
										case "0102":
											$("#bank_lab_"+i).addClass("bk icbc icon").attr("style","margin-left: -1px");
											break;
										case "0308":
											$("#bank_lab_"+i).addClass("bk merchants icon").attr("style","margin-left: -1px");
											break;
										case "0305":
											$("#bank_lab_"+i).addClass("bk minsheng icon").attr("style","margin-left: -1px");
										break;
										case "0302":
											$("#bank_lab_"+i).addClass("bk china_citic icon").attr("style","margin-left: -1px");
										break;
										case "0306":
											$("#bank_lab_"+i).addClass("bk guangfa icon").attr("style","margin-left: -1px");
										break;
										case "0105"://建行
											$("#bank_lab_"+i).addClass("bk construction icon").attr("style","margin-left: -1px");
										break;
										case "0103":
											$("#bank_lab_"+i).addClass("bk agricultural icon").attr("style","margin-left: -1px");
										break;
										case "0104":
											$("#bank_lab_"+i).addClass("bk china icon").attr("style","margin-left: -1px");
										break;
										
										case "0304":
											$("#bank_lab_"+i).addClass("bk huaxia icon").attr("style","margin-left: -1px");
										break;
										
										case "0310":
											$("#bank_lab_"+i).addClass("bk spdb icon").attr("style","margin-left: -1px");
										break;
										case "0303":
											$("#bank_lab_"+i).addClass("bk ceb icon").attr("style","margin-left: -1px");
										break;
										case "0403":
											$("#bank_lab_"+i).addClass("bk bjb icon").attr("style","margin-left: -1px");
										break;
										case "0301":
											$("#bank_lab_"+i).addClass("bk bocom icon").attr("style","margin-left: -1px");
										break;
										case "0309":
											$("#bank_lab_"+i).addClass("bk cib icon").attr("style","margin-left: -1px");
										break;
										case "0410":
											$("#bank_lab_"+i).addClass("bk pingan icon").attr("style","margin-left: -1px");
										break;
										case "0100":
											$("#bank_lab_"+i).addClass("bk post icon").attr("style","margin-left: -1px");
										break;
										case "0401":
											$("#bank_lab_"+i).addClass("bk shanghai icon").attr("style","margin-left: -1px");
										break;
									}
								}
							}
						</script>
</div>
</body>
</html>



