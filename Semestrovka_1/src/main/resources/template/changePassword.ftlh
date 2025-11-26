<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Смена пароля</title>
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
            max-width: 500px;
            margin: 0 auto;
        }

        .main h1 {
            color: #343a40;
            margin-bottom: 1.5rem;
            text-align: center;
        }

        .form-container {
            background: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
            font-weight: bold;
        }

        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }

        .form-group input:focus {
            border-color: #007bff;
            outline: none;
        }

        .btn-primary {
            padding: 12px 24px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            width: 100%;
        }

        .btn-primary:hover {
            background: #0056b3;
        }

        .btn-secondary {
            padding: 10px 20px;
            background: #6c757d;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            margin-top: 1rem;
        }

        .btn-secondary:hover {
            background: #545b62;
        }

        .alert {
            padding: 12px;
            border-radius: 4px;
            margin-bottom: 1rem;
            text-align: center;
        }

        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .password-requirements {
            font-size: 12px;
            color: #6c757d;
            margin-top: 0.25rem;
        }

        .back-link {
            text-align: center;
            margin-top: 1rem;
        }
    </style>
</head>
<body>
<div class="header">
    <a href="/computerclub/index">Главная</a>
    <a href="/computerclub/user/profile">Профиль</a>
    <a href="/computerclub/user/reservations">Мои брони</a>
    <a href="/computerclub/user/createReservation">Забронировать</a>
    <span style="float:right">
        <a href="/computerclub/logout">Выйти</a>
    </span>
</div>

<div class="main">
    <h1>Смена пароля</h1>

    <#if error??>
        <div class="alert alert-error">${error}</div>
    </#if>

    <#if success??>
        <div class="alert alert-success">Пароль успешно обновлен!</div>
    </#if>

    <form class="form-container" method="post" action="changePassword">

        <div class="form-group">
            <label for="currentPassword">Текущий пароль:</label>
            <input type="password" id="currentPassword" name="currentPassword" required>
        </div>

        <div class="form-group">
            <label for="newPassword">Новый пароль:</label>
            <input type="password" id="newPassword" name="newPassword" required>
            <div class="password-requirements">
                Пароль должен содержать минимум 6 символов
            </div>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Подтвердите новый пароль:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>

        <button type="submit" class="btn-primary">Сменить пароль</button>

        <div class="back-link">
            <a href="profile" class="btn-secondary">Назад к профилю</a>
        </div>
    </form>
</div>

<script>

</script>
</body>
</html>