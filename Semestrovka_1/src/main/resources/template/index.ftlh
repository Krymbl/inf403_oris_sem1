<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Computer Club</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: #f8f9fa;
            color: #333;
        }

        .header {
            background: #343a40;
            color: white;
            padding: 1rem 2rem;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .header a {
            color: white;
            text-decoration: none;
            margin-right: 1.5rem;
            font-weight: 500;
        }

        .header a:hover {
            color: #007bff;
        }

        .main {
            padding: 2rem;
            text-align: center;
            max-width: 1200px;
            margin: 0 auto;
        }

        .main h1 {
            color: #343a40;
            margin-bottom: 1rem;
            font-size: 2.5rem;
        }

        .main p {
            color: #666;
            font-size: 1.1rem;
            margin-bottom: 2rem;
        }

        .btn {
            display: inline-block;
            padding: 12px 24px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            margin: 10px;
            border: none;
            cursor: pointer;
        }

        .btn:hover {
            background: #0056b3;
        }

        .features {
            margin: 3rem 0;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 1.5rem;
        }

        .feature {
            background: white;
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            width: 250px;
        }

        .feature h3 {
            color: #343a40;
            margin-bottom: 0.5rem;
        }

        .feature p {
            color: #666;
            margin: 0;
        }

        .footer {
            background: #343a40;
            color: white;
            padding: 1.5rem;
            text-align: center;
            margin-top: 3rem;
        }

        .footer p {
            margin: 0.5rem 0;
        }
    </style>
</head>
<body>
<div class="header">
    <a href="index">Главная</a>
    <a href="prices">Цены</a>
    <span style="float:right">
            <#if user??>
                <a href="user/profile">Профиль</a>
                <a href="/computerclub/logout">Выйти</a>
            <#else>
                <a href="login">Войти</a>
                <a href="registration">Регистрация</a>
            </#if>
        </span>
</div>

<div class="main">
    <h1>Computer Club</h1>
    <p>Лучшее место для игр и отдыха</p>

    <#if user??>
        <a href="user/createReservation" class="btn">Забронировать место</a>
    <#else>
        <a href="registration" class="btn">Начать играть</a>
    </#if>

    <div class="features">
        <div class="feature">
            <h3>Игровые ПК</h3>
            <p>Мощные компьютеры</p>
        </div>
        <div class="feature">
            <h3>PlayStation</h3>
            <p>Новейшие консоли</p>
        </div>
        <div class="feature">
            <h3>Комфорт</h3>
            <p>Удобные места</p>
        </div>
    </div>
</div>

<div class="footer">
    <p>Телефон: +7 999 999-99-99</p>
    <p>Адрес: ул. Пушкина, 1</p>
</div>
</body>
</html>