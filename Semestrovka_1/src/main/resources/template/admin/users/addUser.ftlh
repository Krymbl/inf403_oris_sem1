<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить пользователя</title>
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
    </style>
</head>
<body>
<h1>Добавить пользователя</h1>
<a class="aButton" href="users">Вернуться к списку</a>
<br><br>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<form method='post' action='addUser'>
    <input type='hidden' name='id' value=''>
    Имя пользователя: <input type='text' name='username' value='' required><br><br>
    Пароль: <input type='text' name='password' value='' required minlength="6"> <br><br>
    Телефон: <input type='text' name='phoneNumber' value='' required><br><br>
    Email: <input type='email' name='email' value='' required><br><br>
    Имя: <input type='text' name='firstName' value='' required><br><br>
    Фамилия: <input type='text' name='lastName' value='' required><br><br>
    Пол:
    <select name='gender'>
        <option value=''>Не выбрано</option>
        <option value='MALE'>Мужской</option>
        <option value='FEMALE'>Женский</option>
    </select>  <br><br>
    Дата рождения: <input type='date' name='birthday' value=''><br><br>
    Роль:
    <select name='role' required>
        <option value='USER STANDARD'>Пользователь стандарт</option>
        <option value='USER SILVER'>Пользователь серебро</option>
        <option value='USER GOLD'>Пользователь золото</option>
        <option value='ADMIN'>Администратор</option>
    </select>
    <br><br>
    <input type='submit' class="aButton" value='Добавить пользователя'>
    </form>

<script>

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении пользователя: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('Пользователь успешно добавлен!');
    }
</script>
</body>
</html>