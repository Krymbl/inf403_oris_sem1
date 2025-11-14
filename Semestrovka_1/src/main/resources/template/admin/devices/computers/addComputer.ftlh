<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить компьютер</title>
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
<h1>Добавить компьютер</h1>
<a class="aButton" href="computers">Вернуться к списку</a>
<br><br>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<form method='post' action='addComputer'>
    Название: <input type='text' name='name' value='' required><br><br>
    Процессор: <input type='text' name='cpu' value='' required><br><br>
    Оперативная память (GB): <input type='number' name='ram' value='' required min="1"><br><br>
    Видеокарта: <input type='text' name='videoCard' value='' required><br><br>
    <input type='submit' class="aButton" value='Добавить компьютер'>
</form>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при создании компьютера: ' + errorMessage);
    }
    if (urlParams.has('success')) {
        alert('Компьютер успешно добавлен!');
    }
</script>
</body>
</html>