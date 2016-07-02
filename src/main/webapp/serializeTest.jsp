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

				<select id="sel_test" onchange="a($(this));">
					<option>111111111</option>
					<option>222222222</option>
					<option>3333333</option>
				</select>

				<table>
					<tr>
						<td>aaa</td>
						<td>bbb</td>
						<td>ccc</td>
					</tr>
						<tr>
							<td>div_1_aaa</td>
							<td>div_1_bbb</td>
							<td>div_1_ccc</td>
						</tr>
						<tr>
							<td>div_2_aaa</td>
							<td>div_2_bbb</td>
							<td>div_2_ccc</td>
						</tr>
						<tr>
							<td>div_3_aaa</td>
							<td>div_3_bbb</td>
							<td>div_3_ccc</td>
						</tr>

				</table>
			</form>
		</div>
	</BODY>
	<SCRIPT type="text/javascript">
		var jsonInitData = {};
		$(document).ready(function() {
			$("#sel_test").get(0).selectedIndex = 2;
			$('#sel_test').trigger('change')

			$("#btn_test").on("click", function() {
				var jqArrRate = $("input[name='txt_rate']");
				console.log(jqArrRate.serialize());
				console.log(jqArrRate.serializeArray());
				console.log($.param(jqArrRate));

				// 表单提交
				$.post('serializeTest.jsp', $("#form_1").serialize(), function (res) {
					
				});
			});
		});

		function a(jqCurElement) {
			jsonInitData.abc = "123";
			jsonInitData.bcd = "333";
			alert(getJsonLength(jsonInitData));
//			alert(jqCurElement.val());
//			jqCurElement.get(0).selectedIndex = 1;
//			return;
		}

		function getJsonLength(jsonObject) {
			var length = 0;

			for (var jsonO in jsonObject) {
				length ++;
			}

			return length;
		}
	</SCRIPT>
</HTML>