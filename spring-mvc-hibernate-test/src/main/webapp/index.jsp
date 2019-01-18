<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC - HelloWorld Index Page</title>
</head>
<body>
	<center>
		<h2>Hello World!!</h2>
		<h3>
			<a href="/hello.html?name=Eric">Click Here</a>
			<a href="hello.html?name=Eric">Click Here</a>
			<!--请求要和web.xml中<url-pattern>*.html</url-pattern>一致  -->
			<!-- jetty:run 以上两个链接都可以执行;
				 但是在Tomcat中第一个请求形式出现错误 -->
		</h3>
		<form action="springmvc.html" method="post">
			请输入姓名：<input type="text" name="userName" />
			<input type="submit" value="提交" />
		<br/>
		</form>
	</center>
	
</body>
</html>

