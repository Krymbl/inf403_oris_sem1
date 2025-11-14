<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Тест Freemarker</title>
    <meta name="description" content="Тест Freemarker">
    <style>
        .button {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
    </style>
</head>
<body>

<div style="color:red">${errormessage!}</div>

<form action="/lab06/usercheck" method="post">
    <div>
        <label>Имя пользователя</label>
        <input type="text" name="username">
    </div>
    <div>
        <label>Пароль</label>
        <input type="password" name="password">
    </div>
    <input type="submit" class="button" value="Вход">
</form>

<a href="/lab06/registration" class="button">Регистрация</a>
<a href="/lab06/index" class="button">На главную</a>


</body>
</html>