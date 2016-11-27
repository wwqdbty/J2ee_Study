<%--
  Created by IntelliJ IDEA.
  User: wenqiang.wang
  Date: 2016/7/4
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table id="table_id" class="display">
    <thead>
    <tr>
        <th>Column 1</th>
        <th>Column 2</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>Row 1 Data 1</td>
        <td>Row 1 Data 2</td>
    </tr>
    <tr>
        <td>Row 2 Data 1</td>
        <td>Row 2 Data 2</td>
    </tr>
    </tbody>
</table>

    <%--<!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="/js/plug-in/dataTables/css/jquery.dataTables.min.css">

    <!-- jQuery -->
    <script type="text/javascript" charset="utf8" src="/js/lib/jquery-1.11.3.min.js"></script>
    <!-- DataTables -->
    <script type="text/javascript" charset="utf8" src="/js/plug-in/dataTables/js/jquery.dataTables.min.js"></script>--%>

    <!-- DataTables CSS -->
    <%--<link rel="stylesheet" type="text/css" href="/js/plug-in/dataTables/css/jquery.dataTables.css">

    <!-- jQuery -->
    <script type="text/javascript" charset="utf8" src="/js/plug-in/dataTables/js/jquery.js"></script>

    <!-- DataTables -->
    <script type="text/javascript" charset="utf8" src="/js/plug-in/dataTables/js/jquery.dataTables.js"></script>--%>

<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.7/css/jquery.dataTables.css">

<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>

<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>


    <script>
        $(document).ready( function () {
            $('#table_id').DataTable();
        });
    </script>
</body>
</html>
