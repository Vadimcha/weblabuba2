<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.models.Result" %>
<%@ page import="org.example.models.History" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Результат</title>

    <link rel="stylesheet" href="/lab2web/css/reset.css">
    <link rel="stylesheet" href="/lab2web/css/index.css">
    <link rel="stylesheet" href="/lab2web/css/variables.css">
</head>

<body>
<div class="wrapper">
    <div class="header">
        <p class="name">Белов Вадим Алексеевич</p>
        <p class="group">Группа: <span class="bold">P3230</span></p>
        <p class="variant">Вариант: <span class="bold">81</span></p>
    </div>

    <div class="content" data-page="result">

        <%
            // Получаем историю из сессии
            History history = History.getInstance(request);
        %>

        <div id="table" data-visible="true" data-page="result">
            <h2>Результаты проверки</h2>

<%--            <button class="btn small_btn" id="clear">--%>
<%--                Очистить--%>
<%--            </button>--%>

            <div class="scrollable_container" data-page='result'>
                <table>
                    <thead>
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col">RunTime (нс)</th>
                        <th scope="col">X</th>
                        <th scope="col">Y</th>
                        <th scope="col">R</th>
                        <th scope="col">Результат</th>
                    </tr>
                    </thead>

                    <tbody id="tbody">
                    <%
                        int index = 1;
                        for (Result r : history.getAll()) {
                    %>
                    <tr>
                        <td><%= index++ %></td>
                        <td><%= r.getRun_time() %></td>
                        <td><%= r.getX() %></td>
                        <td><%= r.getY() %></td>
                        <td><%= r.getR() %></td>
                        <td><%= r.getRes() %></td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>

                </table>
            </div>

            <!-- Кнопка назад -->
            <a class="btn small_btn" href="../index.jsp">Вернуться</a>

        </div>

    </div>
</div>
<script src="/lab2web/js/index.js"></script>
</body>
</html>
