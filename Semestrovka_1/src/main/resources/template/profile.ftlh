<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Мой профиль</title>
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
            max-width: 600px;
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
            margin-bottom: 1rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
            font-weight: bold;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-group input:focus,
        .form-group select:focus {
            border-color: #007bff;
            outline: none;
        }

        .form-group input:disabled {
            background: #e9ecef;
            color: #6c757d;
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
        }

        .btn-primary:hover {
            background: #0056b3;
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
    <h1>Мой профиль</h1>

    <#if error??>
        <div class="alert alert-error">${error}</div>
    </#if>

    <#if success??>
        <div class="alert alert-success">Данные успешно обновлены!</div>
    </#if>

    <form class="form-container" method="post" action="profile">
        <div class="form-group">
            <label>Имя пользователя:</label>
            <input type="text" name="username" value="${user.username}" required>
        </div>

        <div class="form-group">
            <label>Имя:</label>
            <input type="text" name="firstName" value="${user.firstName!''}" required>
        </div>

        <div class="form-group">
            <label>Фамилия:</label>
            <input type="text" name="lastName" value="${user.lastName!''}" required>
        </div>

        <div class="form-group">
            <label>Статус:</label>
            <input type="text" name="role" value="${user.role}" readonly>
        </div>

        <div class="form-group">
            <label>Email:</label>
            <input type="email" name="email" value="${user.email}" required>
        </div>

        <div class="form-group">
            <label>Телефон:</label>
            <input type="text" name="phoneNumber" value="${user.phoneNumber}" required>
        </div>

        <div class="form-group">
            <label>Пол:</label>
            <select name="gender">
                <option value="">Не выбрано</option>
                <option value="MALE" <#if user.gender?? && user.gender == 'MALE'>selected</#if>>Мужской</option>
                <option value="FEMALE" <#if user.gender?? && user.gender == 'FEMALE'>selected</#if>>Женский</option>
            </select>
        </div>

        <div class="form-group">
            <label>Дата рождения:</label>
            <input type="date" name="birthday" value="${user.birthday!''}">
        </div>

        <button type="submit" class="btn-primary">Сохранить изменения</button>
        <a href="changePassword" class="btn-primary">Изменить пароль</a>
    </form>
</div>
</body>
</html>