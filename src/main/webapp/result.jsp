<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Результат</title></head>
<body>
<h2>Результаты проверки</h2>

<p>X = ${result.x}, Y = ${result.y}, R = ${result.r}</p>
<p>Результат: <b>${result.res}</b></p>
<p>Время выполнения: ${result.run_time} нс</p>

<h3>История</h3>
<table border="1">
    <tr><th>X</th><th>Y</th><th>R</th><th>Результат</th></tr>
    <c:forEach var="r" items="${history}">
        <tr>
            <td>${r.x}</td>
            <td>${r.y}</td>
            <td>${r.r}</td>
            <td>${r.res}</td>
        </tr>
    </c:forEach>
</table>

<br>
<a href="controller">Вернуться к форме</a>
</body>
</html>
