<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Результат</title>
    <meta charset="UTF-8">

    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/variables.css">
</head>
<body>
<div class="wrapper">
    <div class="header">
        <p class="name">Белов Вадим Алексеевич</p>
        <p class="group">Группа: <span class="bold">P3230</span></p>
        <p class="variant">Вариант: <span class="bold">81</span></p>
    </div>
    <div class="content" data-page="result">

        <c:set var="result" value="${sessionScope.result}" />
        <c:set var="history" value="${sessionScope.history.all}" />

        <div id="table" data-visible="true" data-page="result">
            <h2>Результаты проверки</h2>
            <button class="btn small_btn" id="clear">Очистить</button>
            <div class="scrollable_container" data-page='result'>
                <table>
                    <thead style="margin-bottom: 5px;">
                    <tr>
                        <th scope="col">Запрос №</th>
                        <th scope="col">RunTime (нано сек)</th>
                        <th scope="col">Координата X</th>
                        <th scope="col">Координата Y</th>
                        <th scope="col">Значение R</th>
                        <th scope="col">Результат</th>
                    </tr>
                    </thead>
                    <tbody id="tbody">
                    <c:forEach var="r" items="${history}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${r.run_time}</td>
                            <td>${r.x}</td>
                            <td>${r.y}</td>
                            <td>${r.r}</td>
                            <td>${r.res}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <a class="btn small_btn" href="controller">Вернуться</a>
        </div>
    </div>
</div>
</body>
</html>
