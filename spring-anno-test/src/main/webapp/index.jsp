<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" content="text/html; charset=UTF-8">
</head>
<body>
	<h2>Hello World!</h2>
	<a href="service/control01">service/control01</a>
	<br>
	<a href="service/control02">service/control02</a>
	<br>
	<a href="service/control03">service/control03</a>
	<hr>
	<button id="test04">service/control04</button>
	<hr>
	<button id="test05">service/control05</button>
	<hr>
	<button id="test06">service/control06</button>
	<hr>
	<button id="test061">service/control061</button>
	<hr>
	<button id="test07">service/control07</button>
	<hr>
	<a href="service/control08">service/control08</a>
	<hr>
	<button id="test08" href="service/application/form">service/application/form</button>
	<hr>
	<input type="file" id="file" name="file" onchange="printFileInfo()"
		multiple="multiple" />
	<!--accept 属性可以用来过滤文件-->
	<input id="test09" type="button" value="上传图片" />
</body>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	function printFileInfo() {
		var myFile = document.getElementById("file");
		var files = myFile.files;
		for (var i = 0; i < files.length; i++) {
			var file = files[i];
			var div = document.createElement("div")
			div.innerHTML = "第(" + (i + 1) + ") 个文件的名字：" + file.name
					+ " , 文件类型：" + file.type + " , 文件大小:" + file.size
			document.body.appendChild(div);
		}
	}
	$(document).ready(function() {
		$("#test04").click(function() {
			$.ajax({
				url : "service/control04",
				type : "post",
				contentType : "application/json; charset=utf-8",
				success : function(result) {
					alert(result.id + "=" + result.name);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
				}
			});
		});
		$("#test05").click(function() {
			$.ajax({
				url : "service/control05/1234",
				type : "get",
				contentType : "application/json; charset=utf-8",
				success : function(result) {
					alert(result);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
				}
			});
		});
		/*url: "findPwdNext",
		type: "post",
		data: JSON.stringify({
		   "checkCode": $("#findUserCode").val()
		}),
		contentType: "application/json;charset=utf-8",
		dataType: "json",*/
		$("#test06").click(function() {
			$.ajax({
				url : "service/control06",
				type : "post",
				data : JSON.stringify({
					"id" : 12,
					"name" : "12"
				}),
				dataType : "json",
				contentType : "application/json;charset=utf-8",
				success : function(result) {
					alert(result.id + "=" + result.name);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
				}
			});
		});
		$("#test061").click(function() {
			$.ajax({
				url : "service/control061",
				type : "post",
				data : JSON.stringify({
					"id" : 12,
					"name" : "12"
				}),
				dataType : "json",
				contentType : "application/json;charset=utf-8",
				success : function(result) {
					alert(result.id + "=" + result.name);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
				}
			});
		});
		$("#test07").click(function() {
			$.ajax({
				url : "service/control07",
				type : "get",
				data : {
					"id" : 123,
					"name" : "123"
				},
				contentType : "application/json;charset=utf-8",
				success : function(result) {
					alert(result.id + "=" + result.name);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
				}
			});
		});
		$("#test08").click(function() {
			$.ajax({
				url : "service/application/form",
				type : "post",
				data : {
					"age" : 123,
					"name" : "123"
				},
				/*contentType 是向浏览器发送的数据类型； dataType是浏览器返回的数据类型  */
				contentType : "application/x-www-form-urlencoded",
				success : function(result) {
					alert(result);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
				}
			});
		});
		$("#test09").click(function() {
			alert("hello");
			var fileList = $("#file")[0].files;
			var formData = new FormData();
			for (var i = 0; i < fileList.length; i++) {
				formData.append("file", fileList[i]);
			}
			$.ajax({
				url : "service/multipart/file",
				type : "post",
				data : formData,
				/* processData 默认为true，当设置为true的时候,jquery ajax 提交的时候不会处理 data ，而是直接使用data */
				processData : false,
				/*contentType 是向浏览器发送的数据类型； dataType是浏览器返回的数据类型  */
				contentType : false,
				cache : false,
				success : function(result) {
					alert(result);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
				}
			});
		});
	});
</script>
</html>
