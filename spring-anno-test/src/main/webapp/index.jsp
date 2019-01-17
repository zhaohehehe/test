<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
</body>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#test04").click(function(){
		$.ajax({
	        url: "service/control04",
	        type: "post",
	        contentType: "application/json; charset=utf-8",
	        success:function(result){
	        	alert(result.id+"="+result.name);
	        },
	        error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
	            alert(XMLHttpRequest.readyState);
	            alert(textStatus);
			}
	    });
	});
	$("#test05").click(function(){
		$.ajax({
	        url: "service/control05/1234",
	        type: "get",
	        contentType: "application/json; charset=utf-8",
	        success:function(result){
	        	alert(result);
	        },
	        error:function(XMLHttpRequest, textStatus, errorThrown){
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
	$("#test06").click(function(){
		$.ajax({
	        url: "service/control06",
	        type: "post",
	        data:JSON.stringify({
	        	"id":12,
	        	"name":"12"
	        }),
	        dataType: "json",
	        contentType: "application/json;charset=utf-8",
	        success:function(result){
	        	alert(result.id+"="+result.name);
	        },
	        error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
	            alert(XMLHttpRequest.readyState);
	            alert(textStatus);
	            alert(errorThrown);
			}
	    });
	});
    $("#test061").click(function(){
		$.ajax({
	        url: "service/control061",
	        type: "post",
	        data:JSON.stringify({
	        	"id":12,
	        	"name":"12"
	        }),
	        dataType: "json",
	        contentType: "application/json;charset=utf-8",
	        success:function(result){
	        	alert(result.id+"="+result.name);
	        },
	        error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
	            alert(XMLHttpRequest.readyState);
	            alert(textStatus);
	            alert(errorThrown);
			}
	    });
	});
	$("#test07").click(function(){
		$.ajax({
	        url: "service/control07",
	        type: "get",
	        data:{
	        	"id":123,
	        	"name":"123"
	        },
	        contentType: "application/json;charset=utf-8",
	        success:function(result){
	        	alert(result.id+"="+result.name);
	        },
	        error:function(XMLHttpRequest, textStatus, errorThrown){
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
