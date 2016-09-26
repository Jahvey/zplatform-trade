<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<script type="text/javascript" src="<%=basePath %>js/jquery.easyui.min.js"></script>

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
			<li><b class="f"></b><a href="#">1.选择银行</a><s></s></li>
			<li ><b></b><a href="#">2.填写银行卡信息</a><s></s></li>
			<li class="on"><b></b><a href="#">3.登录到网上银行充值</a><s></s></li>
			<li><b></b><a href="#">4.交易成功</a><s></s></li>
		</ul>
</div>
 <div class="mod_box">
	
		<div class="bd pd10 pay_info">
<ul class="mod_list mt15">
                
            </ul>           
			<!--快捷支付-->
				<form method="post" action="/zplatform-trade/merch/getCodeForPay"
					id="hide_form1" class="">
						<input readonly="readonly" name="memberIP" id="memberIP"   type="hidden"   value="${trade.memberIP }"/>
						<input readonly="readonly" name="bankCode" id="bankCode"   type="hidden"   value="${trade.bankCode }" />
						<input readonly="readonly" name="orderId"  id="orderId"    type="hidden"   value="${trade.orderId }" />
						<input readonly="readonly" name="cardNo"   id="cardNo"     type="hidden"   value="${trade.cardNo }" /> 
						<input readonly="readonly" name="acctName" id="acctName"   type="hidden"   value="${trade.acctName }" /> 
						<input readonly="readonly" name="amount"   id="amount"     type="hidden"   value="${trade.amount }" /> 
						<input readonly="readonly" name="mobile"   id="mobile"     type="hidden"   value="${trade.mobile }" /> 
						<input readonly="readonly" name="certId"   id="certId"     type="hidden"   value="${trade.certId }" /> 
						<input readonly="readonly" name="certType" id="certType"   type="hidden"   value="00" /> 
						<input readonly="readonly" name="tradeType" id="tradeType" type="hidden"   value="01" />
						<input readonly="readonly" type="hidden" name="txnseqno"   value="${trade.txnseqno}" />
						<input readonly="readonly" type="hidden" name="merchId"    value="${trade.merchId }" />
						<input readonly="readonly" type="hidden" name="cashCode"   value="${trade.cashCode }" />
						<input readonly="readonly" type="hidden" name="busicode"   value="${trade.busicode }" />
						<input readonly="readonly" type="hidden" name="reaPayOrderNo" value="${trade.reaPayOrderNo }" />
						<input readonly="readonly" type="hidden" name="merUserId"     value="${trade.merUserId }"/>
						<input readonly="readonly" type="hidden" name="busitype"      value="${trade.busitype }"/>
						<input readonly="readonly" type="hidden" name="bindCardId"    value="${trade.bindCardId }" />
						<input readonly="readonly" type="hidden" name="cardId"    value="${trade.cardId }" />
						<input readonly="readonly" type="hidden" name="tn"        value="${trade.tn }"  />
						<input readonly="readonly" type="hidden" name="cardType"        value="${trade.cardType}" />
						
				</form>
            <div class="ov_hid bd mt30">
           <!--  <div class="t_ico mb30"><span class="ml20">实名认证成功后会员姓名为该银行卡持卡人姓名并不可修改，此后开通快捷支付默认会员姓名为银行卡持卡人姓名</span></div> -->
			<!-- <form method="post" action="/website/app/gateway/recharge/jump.htm" id="form1" class="" > -->
			<form method="post" action="/zplatform-trade/merch/toBankPay.htm?txnseqno_=${trade.txnseqno}"
						id="form1" class="">
              
						<ul class="mt10 mod_list ">
							<li><label class="mod_side">银行：</label>
								<p class="myWay">
									<span id="myWay"><label id="bank_lab" class="" ></label>${fn:substring(trade.cardNo,0, 5)}************${trade.miniCardNo }</span>
								</p>
							</li>
							<li>
								<label class="mod_side">交易金额：</label>${trade.amount_y}<span>元</span>
							</li>
							<li>
								<label class="mod_side">手机号：</label>
								<input readonly="readonly" name="mobile" id="mobile" type="text" class=" inp_normal" value="${trade.mobile }" disabled />
							</li>
							<li>
								<label class="mod_side">验证码：</label>
								<input name="identifyingCode" id="identifyingCode" type="text" class=" inp_normal" value="" maxlength="6">
								<span id="identifyingCodeTip">
								</span><input id="getcode" name="getcode" type="button" value="获取验证码" style="width:80px" onClick="javascript:clickHandler()">
							</li>
							<li>
								<input type="checkbox" id="chkAgree"> 
								<label for="fast_payment">同意 
									<span style="color: blue;">
										<a href="" class="blues" target="_blank">《实名认证相关协议》</a>
										<a href="" class="blues" target="_blank">《快捷支付服务相关协议》</a>
									</span>（本次即可享受快捷支付保障）
								</label>
							</li>
							<li class="submit mt30">
								<button class="btn btn_off" id="btnSubmit" disabled="disabled" type="button">同意协议并支付</button>
								<input type="button" class="btn btnA" id="cancelButton" value="取消" />
							</li>
						</ul>
							<input readonly="readonly" name="bankCode"    id="bankCode_"    type="hidden"  value="${trade.bankCode }" /> 
							<input readonly="readonly" name="orderId"     id="orderId"     type="hidden"  value="${trade.orderId }" /> 
							<input readonly="readonly" name="cardNo"      id="cardNo"      type="hidden"  value="${trade.cardNo }" /> 
							<input readonly="readonly" name="acctName"    id="acctName"    type="hidden"  value="${trade.acctName }" /> 
							<input readonly="readonly" name="amount"      id="amount"      type="hidden"  value="${trade.amount }" /> 
							<input readonly="readonly" name="mobile"      id="mobile"      type="hidden"  value="${trade.mobile }" /> 
							<input readonly="readonly" name="certId"      id="certId"      type="hidden"  value="${trade.certId }" /> 
							<input readonly="readonly" name="certType"    id="certType"    type="hidden"  value="00" /> 
							<input readonly="readonly" name="tradeType"   id="tradeType"   type="hidden"  value="01" />
							<input readonly="readonly" name="currentSetp" id="currentSetp" type="hidden"   />
							<input readonly="readonly" name="merchId"       type="hidden" value="${trade.merchId }" />
							<input readonly="readonly" name="txnseqno"      type="hidden" value="${trade.txnseqno}" />
							<input readonly="readonly" name="cashCode"      type="hidden" value="${trade.cashCode }" />
							<input readonly="readonly" name="busicode"      type="hidden" value="${trade.busicode }" />
							<input readonly="readonly" name="reaPayOrderNo" type="hidden"  value="${trade.reaPayOrderNo }" />
							<input readonly="readonly" name="merUserId"     type="hidden" value="${trade.merUserId }"/>
							<input readonly="readonly" name="busitype"      type="hidden" value="${trade.busitype }"/>
							<input readonly="readonly" name="bindCardId"    type="hidden"  value="${trade.bindCardId }" />
							<input readonly="readonly" name="cardId"        type="hidden"  value="${trade.cardId }" />
							<input readonly="readonly" name="tn"            type="hidden"         value="${trade.tn }"  />
							<input readonly="readonly" name="cardType"      type="hidden"  value="${trade.cardType}" />
			</form>
            </div>
			<!--/网银支付 --> 
		</div>
	</div>


<div class="footer">
	<p class="foot_nav"><a href="" target="_blank">关于我们</a> | <a href="" target="_blank">隐私条款</a> | <a href="" target="_blank">合作加盟</a> | <a href="" target="_blank">会员服务</a></p>
	<p class="copyright">Copyright  © 2015 北京证联资本管理有限责任公司版权所有</p>
	<p class="contact_info"> <span>联系电话：010-84298418</span> <span>传真：010-84299579</span> <!--  <span>版本：2013V1.4 Beta</span> --></p>
	<p class="icp"><a href=" http://www.miibeian.gov.cn/" target="_blank">京ICP备15034871号</a></p>
</div>
<script type="text/javascript">	
	
	$(function() {
		initBankPic();
		$("#cancelButton").click(function(){	
		  closeWin();
		});
	});
	$("#btnSubmit").click(function(){
		$("#btnSubmit").removeClass("btnA").addClass("btn_off").attr("disabled","disabled");
		if($("#identifyingCode").val()==""||$("#identifyingCode").val()==null){
			alert("请输入验证码！");
			$("#chkAgree").removeAttr("checked");
		}else{
			$('#form1').submit();
		}
		
	})
	function closeWin() {
		history.go(-1);
	}
	$("#chkAgree").click(function(){
		if(this.checked==true){
			if($("#identifyingCode").val()==""||$("#identifyingCode").val()==null){
				alert("请输入验证码！");
				$("#chkAgree").removeAttr("checked");
			}else{
				$("#btnSubmit").removeClass("btn_off").addClass("btnA").removeAttr("disabled");
			}
			
		}else{
			$("#btnSubmit").removeClass("btnA").addClass("btn_off").attr("disabled","disabled");
		}
	});
	function clickHandler(){
		
		$('#hide_form1').form('submit', {  
		    success:function(msg){
		    	//alert(msg);
		    	$("#getcode").attr("diabled","diabled");
		    	init=60;
	    		jz();
			}   
		});  
	} 
	
	var init=60;  
	function jz(){     
		if(init >1){
			$("#getcode").val(init+"秒");
			init--;         
			setTimeout("jz()",1000);        
			$("#getcode").attr("disabled","disabled"); 
		}else{             
			$("#getcode").removeAttr("disabled"); 
			$("#getcode").val("获取验证码");
		}     
	}          
	window.onload=jz;
	
	
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
</script>
</div>
</body>
</html>