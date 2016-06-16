<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>

<HTML>
	<HEAD>
		<TITLE> 选项卡 DEMO </TITLE>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="css/demo.css" type="text/css">
		<script type="text/javascript" src="js/plug-in/jquery-1.11.3.min.js"></script>
	</HEAD>

	<BODY>
		<div id="div_mainContainer">
			<form id="form_1">
				<input type="text" name="txt_add">

				<input type="text" name="txt_rate"/>
				<input type="text" name="txt_rate">
				<input type="text" name="txt_rate">

				<input id="btn_test" type="button" value="测试serialize">
			</form>
		</div>
	</BODY>
	<SCRIPT type="text/javascript">
		$(document).ready(function() {
			$("#btn_test").on("click", function() {
				var jqArrRate = $("input[name='txt_rate']");
				console.log(jqArrRate.serialize());
				console.log(jqArrRate.serializeArray());

				// 表单提交
				$.post('serializeTest.jsp', $("#form_1").serialize(), function (res) {
					
				});
			});
		});
	</SCRIPT>
</HTML>