<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить PlayStation</title>
    <style>
        .aButton {
            border: 2px solid black;
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            background-color: white;
            color: black;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .error {
            color: red;
            background-color: #ffe6e6;
            padding: 10px;
            border: 1px solid red;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<h1>Добавить PlayStation</h1>
<a class="aButton" href="playStations">Вернуться к списку</a>
<br><br>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<form method='post' action='addPlayStation'>
    Модель: <input type='text' name='model' value='' required><br><br>
    Версия: <input type='text' name='version' value='' required><br><br>
    <input type='submit' class="aButton" value='Добавить PlayStation'>
</form>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при создании PlayStation: ' + errorMessage);
    }
    if (urlParams.has('success')) {
        alert('PlayStation успешно добавлен!');
    }
</script>
</body>
</html>