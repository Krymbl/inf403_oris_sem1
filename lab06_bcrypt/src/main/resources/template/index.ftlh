<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Тест аутентификации</title>
    <meta name="description" content="Тест аутентификации">

    <style>
        label {
            color: red;
        }

        .div_style {
            border: 1px solid #9fb5de;
        }

        div {
            margin: 3px;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
<h1>Поздравляем ${user.userName!}, вы свой!</h1>
<a href="/lab06/logout" class="button">Выйти</a>
</body>
</html>