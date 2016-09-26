<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>证联_收银台</title>
<link type="text/css" rel="stylesheet" href="/zplatform-trade/css/style.css">
<script type="text/javascript" src="/zplatform-trade/js/jquery-ui.js"></script>
<script type="text/javascript" src="/zplatform-trade/js/jquery-1.11.2.js"></script>
<script type="text/javascript" src="/zplatform-trade/js/jscroller-0.4.js"></script>
<script type="text/javascript" src="/zplatform-trade/js/pay-dialog.js"></script>
</head>

<body>
<input type="hidden" id="suburl" value="${suburl}" />
<div class="g-hd">
  <div class="g-row">
    <div class="logo"><a href="http://www.vip.com/" ></a></div>
    <div class="hd-logo-nav">收银台</div>
  </div>
</div>
<div class="g-row">
 <div class="wrapper">
		 <ul class="flow-steps">
			<li><b  class="f"></b><a href="<%=basePath %>gateway/cash.htm?txnseqno=${trade.txnseqno}">1.选择银行</a><s></s></li>
			<li ><b></b><a href="#">2.填写银行卡信息</a><s></s></li>
			<li ><b></b><a href="#">3.登录到网上银行充值</a><s></s></li>
			<li class="on"><b></b><a href="#">4.交易结果</a><s></s></li>
		</ul>
</div>

<div class="mod_box">
		
	
			
		<div class="bd">
			<!--提示 -->
		<div class="txtC">
		 
				 <div class="result_tips">
					   <div class="set_height clearfix">
								<!--icon调用的class   pop_s:√  pop_war:! pop_fail:X--> 
								<span class="pop_s"></span>
								<p class="words f16b">${errMsg}</p>
								
								<input type="hidden" id="txnseqno" value="${txnseqno }"/>
								<span id="tx_second">10</span>秒后返回商户网站
					   </div>
					   
				 </div>	 
		        <div style="text-align: center;text-decoration: overline;">
					<input type="button" class="btn btn_on" id="btn_Return" value="返回商户网站"></input>
				</div>
				</div>
		<!--/提示 --> 
		
			
		</div>

</div>


<div class="footer">
	<p class="foot_nav"><a href="" target="_blank">关于我们</a> | <a href="" target="_blank">隐私条款</a> | <a href="" target="_blank">合作加盟</a> | <a href="" target="_blank">会员服务</a></p>
	<p class="copyright">Copyright  © 2015 北京证联资本管理有限责任公司版权所有</p>
	<p class="contact_info"> <span>联系电话：010-84298418</span> <span>传真：010-84299579</span> <!--  <span>版本：2013V1.4 Beta</span> --></p>
	<p class="icp"><a href=" http://www.miibeian.gov.cn/" target="_blank">京ICP备15034871号</a></p>
</div>

<form action="${suburl}" method="get" id="form1"></form>
<script type="text/javascript">
$(function(){
	 if($("#suburl").val()!=null&&$("#suburl").val()!=""){
		 //window.location=$("#suburl").val();
	} 
	 
	
})

 $("#btn_Return").click(function(){
	window.location=$("#suburl").val();
 });
var init=10;  
function jz(){     
	if(init >1){         
		document.getElementById('tx_second').innerHTML=init;         
		init--;         
		setTimeout("jz()",1000);          
	}else{             
		window.location=$("#suburl").val();
		}     
	}          
window.onload=jz;
</script>
</div>
</body>
</html>