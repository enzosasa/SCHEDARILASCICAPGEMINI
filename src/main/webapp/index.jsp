<html>
<body>
	<script type="text/javascript">
		<% if(request.getParameter("msg") != null){	%>
			alert("<%=request.getParameter("msg")%>");
		<% } %>
		function start_new_scheduler() {
			window.location.replace("services?action=create");
		}
		function stop_scheduler() {
			window.location.replace("services?action=destroy");
		}
	</script>
	<div align="center">
		<table border="1">
			<thead>
				<tr>
					<th scope="col" colspan="2">Operations</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Starta a new Scheduler</td>
					<td><input type="button" value="Start"
						onclick="start_new_scheduler();" /></td>
				</tr>
				<tr>
					<td>Stops an existing Scheduler</td>
					<td><input type="button" value="Stop"
						onclick="stop_scheduler();" /></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
