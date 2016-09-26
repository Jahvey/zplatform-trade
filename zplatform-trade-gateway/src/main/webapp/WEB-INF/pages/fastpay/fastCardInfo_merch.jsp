<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>证联_收银台</title>

<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style.css">
<script type="text/javascript" src="<%=basePath %>js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.11.2.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jscroller-0.4.js"></script>
<script type="text/javascript" src="<%=basePath %>js/pay-dialog.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.validate.min.js"></script>
<script type="text/javascript">	

	$(function() {	
		$("#chkAgree").click(function(){
			if(this.checked==true){
				$("#btnSubmit").removeClass("btn_off").addClass("btnA").removeAttr("disabled");
			}else{
				$("#btnSubmit").removeClass("btnA").addClass("btn_off").attr("disabled","disabled");
			}
		});
		initBankPic();
		
	});
	
	function initBankPic(){
		var bankcode = $("#bankCode").val();
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
			//、case "0301":
			//	$("#bank_lab").addClass("bk communications ").attr("style","margin-left: -1px");
			//break;
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
	function closeWin() {
		history.go(-1);

	}
</script>
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
			<li><b  class="f"></b><a href="#">1.选择银行</a><s></s></li>
			<li class="on"><b></b><a href="#">2.填写银行卡信息</a><s></s></li>
			<li><b></b><a href="#">3.登录到网上银行充值</a><s></s></li>
			<li><b></b><a href="#">4.交易成功</a><s></s></li>
		</ul>
</div>
  <div class="mod_box"> 
   
<div class="bd pd10 pay_info">
<ul class="mod_list mt15">
               <input type="hidden" id="card_type_hide" value="${trade.cardType}"/>
               
            </ul>           
			<!--快捷支付 -->
			<form method="post" action="" id="hide_form1" class="">
            	
            </form>
            <div class="ov_hid bd mt30">
            
			<!-- <form method="post" action="/website/app/gateway/recharge/jump.htm" id="form1" class="" target="_blank"> -->
			<!-- <form method="post" action="/website/app/gateway/recharge/toBankPay.htm" id="form1" class="" target="_blank"> -->
			<form method="post" action="/zplatform-trade/merch/toPay.htm?txnseqno_=${trade.txnseqno}" id="form1" class="">
						<input type="hidden" name="bankCode" id="bankCode" value="${trade.bankCode}"  /> 
						<input type="hidden" name="orderId"   value="${trade.orderId}" /> 
						<input type="hidden" name="amount"    value="${trade.amount}" />
						<input type="hidden" name="txnseqno"  value="${trade.txnseqno}" />
						<input type="hidden" name="merchId"   value="${trade.merchId }" />
						<input type="hidden" name="cashCode"  value="${trade.cashCode }" />
						<input type="hidden" name="busicode"  value="${trade.busicode }" />
						<input type="hidden" name="goodsName" value="${trade.goodsName }" />
						<input type="hidden" name="merUserId" value="${trade.merUserId }"/>
						<input type="hidden" name="busitype"  value="${trade.busitype }"/>
						<input type="hidden" name="memberIP"  value="${trade.memberIP }"/>
						<input type="hidden" name="tn"        value="${trade.tn }"  />
				<ul class="mt10 mod_list ">
							<li><label class="mod_side">银行：</label>
								<p class="myWay">
									<span id="myWay">
										<label id="bank_lab" class="" ></label>
									</span>
								</p>
							<li id="li_cardno">
								<label class="mod_side">银行卡卡号：</label>
								<input name="cardNo" id="cardNo" type="text" class="w180 inp_normal" maxlength="19" value="" onblur="cardType()"/>
								<input type="hidden" name="cardType" id="cardType" />
								<span id="cardNoTip"></span>
							</li>
							<li id="li_validatetime" style="display: none;">
								<label class="mod_side">有效期：</label>
								<select name="month">
			                		<option value="">请选择</option>
			                		<option value="01">01</option>
			                		<option value="02">02</option>
			                		<option value="03">03</option>
			                		<option value="04">04</option>
			                		<option value="05">05</option>
			                		<option value="06">06</option>
			                		<option value="07">07</option>
			                		<option value="08">08</option>
			                		<option value="09">09</option>
			                		<option value="10">10</option>
			                		<option value="11">11</option>
			                		<option value="12">12</option>
			                	</select>
		                		月/
								<select name="year">
									<option value="">请选择</option>
									<option value="13">2013</option>
									<option value="14">2014</option>
									<option value="15">2015</option>
									<option value="16">2016</option>
									<option value="17">2017</option>
									<option value="18">2018</option>
									<option value="19">2019</option>
									<option value="20">2020</option>
									<option value="21">2021</option>
									<option value="22">2022</option>
									<option value="23">2023</option>
									<option value="24">2024</option>
									<option value="25">2025</option>
									<option value="26">2026</option>
									<option value="27">2027</option>
									<option value="28">2028</option>
									<option value="29">2029</option>
									<option value="30">2030</option>
									<option value="31">2031</option>
									<option value="32">2032</option>
									<option value="33">2033</option>
			                	</select>年
								<span id="validateymTip"></span>
							</li>
							<li id="li_cvv2" style="display: none;"><label class="mod_side">CVV：</label><input
								name="cvv2" id="cvv2" type="text" class="w180 inp_normal"
								value="" maxlength="3"><span id="cardNoTip"></span></li>		
							<li>
								<label class="mod_side">持卡人姓名：</label>
								<input name="acctName" id="acctName" type="text" class="w180 inp_normal" value="" maxlength="32"/>
								<span id="acctNameTip"></span>
							</li>
							<li><label class="mod_side">持卡人身份证：</label><input
								name="certId" id="certId" type="text" class="w180 inp_normal"
								value="" maxlength="18"/> <span id="idNoTip"></span></li>
							<li><label class="mod_side">交易金额：</label>${trade.amount_y}元<span
								id="amountTip"></span></li>
							<li><label class="mod_side">手机号：</label><input name="mobile"
								id="mobile" type="text" class="w180 inp_normal"
								value="" maxlength="11"> <span id="mobileTip"></span></li>
							<li><input type="checkbox" id="chkAgree"> <label
								for="fast_payment">同意 <span style="color: blue;"><a
										href="javascript:void()" class="blues" target="_blank">《快捷支付服务相关协议》</a></span>（本次即可享受快捷支付保障）
							</label></li>
							<li class="submit mt30">
							
							<button class="btn btn_off"
									id="btnSubmit" type="button">下一步</button> <input type="button"
								class="btn btnA" id="cancelButton" value="取消" onclick="closeWin()"/></li>
						</ul>
			</form>
            </div>
			<!--/网银支付 --> 
		</div>
		</div>

<div class="footer">
	<p class="foot_nav"><a href="" style=""  target="_blank">关于我们</a> | <a href="" target="_blank">隐私条款</a> | <a href="" target="_blank">合作加盟</a> | <a href="" target="_blank">会员服务</a></p>
	<p class="copyright">Copyright  © 2015 北京证联资本管理有限责任公司版权所有</p>
	<p class="contact_info"> <span>联系电话：010-84298418</span> <span>传真：010-84299579</span> <!--  <span>版本：2013V1.4 Beta</span> --></p>
	<p class="icp"><a href=" http://www.miibeian.gov.cn/" target="_blank">京ICP备15034871号</a></p>
</div>

</div>
<script type="text/javascript">
$.validator.addMethod("certIdValidator", function(value) {
	
	if(value!=null&&value!=""){
		if(value.length==18||value.length==15){
			return true;
		}
		
		
	}
	return false;
}, '请填写真实的证件号码,身份证号码为15位或18位');
$.validator.addMethod("cardNoValidator", function(value) {
	
	if(value!=null&&value!=""){
		if(value.length>=12&&value.length<=19){
			var flag = false;
			var cardNo = $("#cardNo").val();
			$.ajax({
				type: "POST",
			  	url: "/zplatform-trade/gateway/getCardType",
			  	data: "rand="+new Date().getTime()+"&cardNo="+cardNo,
			  	async:false,
			 	dataType: "text",
			 	success:function(text){
			 		if(text=="carderror"){
			 			//alert("银行卡号错误，请填写正确的银行卡号");
			 			$("#cardNoTip").html("银行卡号错误，请填写正确的银行卡号");
			 		}else{
			 			
			 			var json = eval('(' + text + ')');
			 			$("#cardType").val(json.TYPE);
						if(json.TYPE=="2"){
							//$("#li_validatetime").show();
							//$("#li_cvv2").show();
							if(json.BANKCODE.substring(0,4)==$("#bankCode").val()){
								flag = true;
								$("#cardNoTip").html("<img src='/zplatform-trade/images/onSuccess.gif' style='text-align: center;'/>");
							}else{
								$("#cardNoTip").html("该卡号不是所选银行");
								}
						}else if(json.TYPE=="1"){
							//$("#li_validatetime").hide();
							//$("#li_cvv2").hide();
							if(json.BANKCODE.substring(0,4)==$("#bankCode").val()){
								flag = true;
								$("#cardNoTip").html("<img src='/zplatform-trade/images/onSuccess.gif' style='text-align: center;'/>");
							}else{
								$("#cardNoTip").html("该卡号不是所选银行");
								}
						}else{
							//alert("银行卡号错误，请填写正确的银行卡号");
							$("#cardNoTip").html("银行卡号错误，请填写正确的银行卡号");
						}
			 		}
			 		
			 		
					
					
			 	}
			});
			return flag;
		}else{
			$("#cardNoTip").html("银行卡号错误,银行卡号必须为12-19位数字");
		}
		
		
	}
	return false;
}, '银行卡号错误,银行卡号必须为12-19位数字,');
// this one requires the value to be the same as the first parameter
$.validator.methods.equal = function(value, element, param) {
	return value == param;
};

$().ready(function() {
	var cardtype = $("#card_type_hide").val();
	if(cardtype=="2"){
		$("#li_validatetime").show();
		$("#li_cvv2").show();
		
	}else if(cardtype=="1"){
		$("#li_validatetime").hide();
		$("#li_cvv2").hide();
	}
	var validator = $("#form1").bind("invalid-form.validate", function() {
		$("#summary").html("Your form contains " + validator.numberOfInvalids() + " errors, see details below.");
	}).validate({
		errorPlacement: function(error, element) {
			error.appendTo(element.next("span"));
		},
		success: function(span) {
			span.html("<img src='/zplatform-trade/images/onSuccess.gif' style='text-align: center;'/>");
		},
		rules: {
			cardNo: "cardNoValidator",
			certId: "certIdValidator",
			mobile: {
				required: true,
				minlength: 11,
				maxlength:11,
				number: true
			},
			acctName:{
				required: true,
				minlength: 1,
				maxlength:20,
			},
			month:{
				required: true
			},
			year:{
				required: true
			},
			cvv2:{
				required: true
			}
		},
		
		messages:{
			cardNo: "银行卡号错误,银行卡号必须为12-19位数字",
			mobile:"请输入有效的手机号",
			acctName:"请输入姓名",
			month:"请选择有效日期",
			year:"请选择有效日期",
			cvv2:"请输入CVV"
		}
	});

});

function cardType(){
	var cardNo = $("#cardNo").val();
	$.ajax({
		type: "POST",
	  	url: "/zplatform-trade/gateway/getCardType",
	  	data: "rand="+new Date().getTime()+"&cardNo="+cardNo,
	  	async:false,
	 	dataType: "text",
	 	success:function(json){
	 		
			if(json.TYPE=="2"){
				$("#li_validatetime").show();
				$("#li_cvv2").show();
				
			}else if(json.TYPE=="1"){
				$("#li_validatetime").hide();
				$("#li_cvv2").hide();
			}else{
				//alert("银行卡信息错误")
			}
			
			
	 	}
	});
}
$("#btnSubmit").click(function(){
	$("#form1").submit();
	//$("#btnSubmit").removeClass("btnA").addClass("btn_off").attr("disabled","disabled");
})
</script>
</body>
</html>
