<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<body>
<h2>代付通用接口</h2>

<form name="tj" action="/zplatform-trade/interface/insteadPayMock.htm" method="post" target="_blank">
<textarea rows="20" cols="100" name="data"></textarea>
<br/>
<input type="submit" id="button" name="button" value="报文提交">
</form>
<form name="test" action="/zplatform-trade/mock/21.htm" method="post" target="_blank">
<br/>
<input type="submit" value="随即生成代付报文"/> 
</form>

</body>
</html>
