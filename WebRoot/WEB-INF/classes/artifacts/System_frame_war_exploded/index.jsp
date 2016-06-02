<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
<script type="text/javascript" src="assets/lib/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>

	<a href="test/page1">page1</a>
	<a href="test/page6/牛">page6</a>
	
	<button id="ajaxBtn">ajaxBth</button>

</body>

<script type="text/javascript">
	(function(){
		$('#ajaxBtn').click(function(){
			$.ajax({
				   type: "POST",
				   url: "test/page5",
				   data: {
					   name:'lin',
					   age:'23'
				   },
				   success: function(msg){
				     alert( "success" );
				   }
				}); 
		})
	})(window.jQuery)
</script>
</html>