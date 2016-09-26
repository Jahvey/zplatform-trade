<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单提交</title>


</head>
<body onload="submitForm()">
<br><br><br><br><br><br><br>

<form name="tj" id="tj" action="${url }" method="post" >
<table>
	<tr>
		<td>
			<input type="hidden"  name="version" value="${order.version }">
			<input type="hidden"  name="partner_id" value="${order.partner_id }">
			<input type="hidden"  name="_input_charset" value="${order._input_charset }">
			<input type="hidden"  name="transDate" value="${order.transDate }">
			<input type="hidden"  name="service" value="${order.service }">
			<input type="hidden"  name="sign" value="${order.sign}">
			<input type="hidden"  name="sign_type" value="${order.sign_type }">
	    </td>
	<tr>
</table>
</form>
<script type="text/javascript">
	function submitForm(){
		document.forms[0].submit();
	}
</script>
</body>
</html>