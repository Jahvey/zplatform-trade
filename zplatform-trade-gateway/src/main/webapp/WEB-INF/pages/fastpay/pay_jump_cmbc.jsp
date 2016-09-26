<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>处理中</title>
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/style.css">
<script type="text/javascript" src="<%=basePath %>js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.11.2.js"></script>
<style>
.logining{ width:50%;  min-width:250px;_width:expression((document.documentElement.clientWidth||document.body.clientWidth)<450?"250px":"60%");/*ie6 min-width*/ margin:5% auto 0;_margin-top:50px;padding:30px; border:2px solid #FFD887; background:#FEFEEC; font-size:20px;font-family:\5fae\8f6f\96c5\9ed1,\5b8b\4f53,sans-serif; color:#000;}
.logining img{ margin-left:20px; vertical-align:middle;}
</style>
</head>
<body>
<div class="logining">
正在支付，请稍候...
<img src="<%=basePath %>images/loading32.gif" /></div>
<input type="hidden" id="txnseqno" value="${trade.txnseqno }"/>
<input type="hidden" id="orderNo" value="${trade.orderId }"/>
<input type="hidden" id="reaPayOrderNo" value="${trade.reaPayOrderNo }"/>
<script type="text/javascript">

$(function(){
	searchTrade();
})
function searchTrade(){
	//if($("#orderNo").val()==null||$("#orderNo").val()==""){return;}
	$.ajax({
		type: "POST",
	  	url: "/zplatform-trade/gateway/queryCMBCTrade",
	  	data: "rand="+new Date().getTime()+"&orderNo="+$("#reaPayOrderNo").val()+"&txnseqno="+$("#txnseqno").val(),
	 	dataType: "text",
	 	success:function(json){
	 		var reapayorderno= $("#reaPayOrderNo").val();
			if(json=="completed"){
				window.location="<%=basePath %>gateway/success.htm?orderNo="+$("#orderNo").val()+"&txnseqno="+$("#txnseqno").val();
			}else if(json=="failed"){
				window.location="<%=basePath %>gateway/fail.htm?orderNo="+$("#orderNo").val()+"&txnseqno="+$("#txnseqno").val();
			}else if(json=="wait"){
				window.location="<%=basePath %>gateway/wait.htm?reapayOrderNo="+reapayorderno+"&orderNo="+$("#orderNo").val()+"&txnseqno="+$("#txnseqno").val();
			}else if(json=="processing"){
				window.location="<%=basePath %>gateway/processing.htm?orderNo="+$("#orderNo").val()+"&txnseqno="+$("#txnseqno").val();
			}else{
				window.location="<%=basePath %>gateway/error.htm?orderNo="+$("#orderNo").val()+"&txnseqno="+$("#txnseqno").val();
			}
	 	}
	});
}
</script>
</body>
</html>


