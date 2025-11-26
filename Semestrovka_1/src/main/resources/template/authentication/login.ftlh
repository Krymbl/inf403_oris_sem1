<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход в систему</title>
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
            max-width: 400px;
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

        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-group input:focus {
            border-color: #007bff;
            outline: none;
        }

        .btn-primary {
            width: 100%;
            padding: 12px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 1rem;
        }

        .btn-primary:hover {
            background: #0056b3;
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
<form class="form-container" method="post" action="login">
    <h2 class="form-title">Вход в систему</h2>

    <div class="alert alert-error" id="errorMessage">
        Неверные данные для входа
    </div>

    <div class="form-group">
        <label for="username">Имя пользователя:</label>
        <input type="text" id="username" name="username" required>
    </div>

    <div class="form-group">
        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>
    </div>

    <button type="submit" class="btn-primary">Войти</button>

    <div class="form-links">
        <a href="registration">Регистрация</a>
        <a href="index">Вернуться на сайт</a>
    </div>
</form>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        document.getElementById('errorMessage').style.display = 'block';
    }
</script>
</body>
</html>