<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Лабуба</title>

    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/variables.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:ital,wght@0,100..800;1,100..800&display=swap" rel="stylesheet">
</head>
<body>
<div class="wrapper">
    <div class="header">
        <p class="name">Белов Вадим Алексеевич</p>
        <p class="group">Группа: <span class="bold">P3230</span></p>
        <p class="variant">Вариант: <span class="bold">81</span></p>
    </div>
    <div class="content">
        <form
            id="form"
        >
            <div class="input">
                <label for="x_input">Координата x</label>
                <input
                        id="x_input"
                        name="x"
                        class="field"
                        value="99999999999"
                        type="text"
                        placeholder="Введите число [-5; 5]"
                >
                <span class="error"></span>
            </div>
            <div class="input">
                <label for="y_input">Координата y</label>
                <input
                        id="y_input"
                        name="y"
                        class="field"
                        value="0.000000001"
                        type="text"
                        placeholder="Введите число [-5: 5]"
                >
                <span class="error"></span>
            </div>
            <div class="input">
                <label for="r_input">Значение r</label>
                <input
                        id="r_input"
                        name="r"
                        class="field"
                        value="9999999999"
                        type="text"
                        placeholder="Введите число [1; 4]"
                >
                <span class="error"></span>
            </div>
            <button class="btn" id="submit_btn" type="submit">Отправить запрос</button>
            <div id="res"></div>
        </form>
        <div class="plot_block">
            <img class="plot" src="public/plot.png" alt="plot">
            <div id="dot"></div>
        </div>
    </div>
    <div id="table" data-visible="true">
        <button class="btn small_btn" id="clear">Очистить</button>
        <div class="scrollable_container">
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
                </tbody>
            </table>
        </div>
    </div>

    <button id="generate_btn" class="btn small_btn">Запустить генерацию</button>
</div>
</body>
<script src="js/index.js"></script>
</html>