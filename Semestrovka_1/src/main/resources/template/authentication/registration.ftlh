<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f8f9fa;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            color: #333;
        }

        .form-container {
            background: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 450px;
        }

        .form-title {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #343a40;
            font-size: 1.5rem;
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

        .btn-success {
            width: 100%;
            padding: 12px;
            background: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 1rem;
        }

        .btn-success:hover {
            background: #218838;
        }

        .alert {
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 1rem;
            text-align: center;
            display: none;
        }

        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .form-links {
            text-align: center;
            margin-top: 1rem;
        }

        .form-links a {
            color: #007bff;
            text-decoration: none;
            margin: 0 10px;
        }

        .form-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<form class="form-container" method="post" action="registration">
    <h2 class="form-title">Регистрация</h2>

    <div class="alert alert-error" id="errorMessage">
        Ошибка при регистрации
    </div>

    <div class="form-group">
        <label for="username">Имя пользователя:</label>
        <input type="text" id="username" name="username" required>
    </div>

    <div class="form-group">
        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required minlength="6">
    </div>

    <div class="form-group">
        <label for="phoneNumber">Телефон:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" required>
    </div>

    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>

    <div class="form-group">
        <label for="firstName">Имя:</label>
        <input type="text" id="firstName" name="firstName" required>
    </div>

    <div class="form-group">
        <label for="lastName">Фамилия:</label>
        <input type="text" id="lastName" name="lastName" required>
    </div>

    <div class="form-group">
        <label for="gender">Пол:</label>
        <select id="gender" name="gender">
            <option value="">Не выбрано</option>
            <option value="MALE">Мужской</option>
            <option value="FEMALE">Женский</option>
        </select>
    </div>

    <div class="form-group">
        <label for="birthday">Дата рождения:</label>
        <input type="date" id="birthday" name="birthday">
    </div>

    <button type="submit" class="btn-success">Зарегистрироваться</button>

    <div class="form-links">
        <a href="index">Вернуться на сайт</a>
        <a href="login">Войти</a>
    </div>
</form>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        document.getElementById('errorMessage').textContent = errorMessage;
        document.getElementById('errorMessage').style.display = 'block';
    }
</script>
</body>
</html>