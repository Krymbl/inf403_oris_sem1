<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <meta name="description" content="Регистрация">
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
        }
    </style>
</head>
<body>

<div style="color:red">${errormessage!}</div>

<form action="/lab06/registration" method="post">
    <div>
        <label>Фамилия</label>
        <input type="text" name="lastname" required>
    </div>
    <div>
        <label>Имя</label>
        <input type="text" name="firstname" required>
    </div>
    <div>
        <label>Телефон</label>
        <input type="text" name="phone" required>
    </div>


    <div>
        <label>Логин</label>
        <input type="text" name="username" required>
    </div>
    <div>
        <label>Пароль</label>
        <input type="password" name="password" required>
    </div>
    <input type="submit" class="button" value="Готово">
</form>
<a href="/lab06/login" class="button">Войти</a>
<a href="/lab06/index" class="button">На главную</a>
</body>
</html>