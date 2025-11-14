<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить пользователя</title>
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
<h1>Изменить пользователя</h1>
<a class="aButton" href="users">Вернуться к списку</a>
<br><br>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<form method='post' action='userShowOne'>
    <input type='hidden' name='id' value='${user.id}'>
    Имя пользователя: <input type='text' name='username' value='${user.username}' required><br><br>
    Телефон: <input type='text' name='phoneNumber' value='${user.phoneNumber}' required><br><br>
    Email: <input type='email' name='email' value='${user.email}' required><br><br>
    Имя: <input type='text' name='firstName' value='${user.firstName}' required><br><br>
    Фамилия: <input type='text' name='lastName' value='${user.lastName}' required><br><br>
    Пол:
    <select name='gender'>
        <option value=''>Не выбрано</option>
        <option value='MALE' ${(user.gender == 'MALE')?then('selected', '')}>Мужской</option>
        <option value='FEMALE' ${(user.gender == 'FEMALE')?then('selected', '')}>Женский</option>
    </select>  <br><br>
    Дата рождения: <input type='date' name='birthday' value='${user.birthday?string("yyyy-MM-dd")!''}'><br><br>
    Роль:
    <select name='role' required>
        <option value='USER STANDARD' ${(user.role == 'USER STANDARD')?string('selected', '')}>Пользователь стандарт</option>
        <option value='USER SILVER' ${(user.role == 'USER SILVER')?string('selected', '')}>Пользователь серебро</option>
        <option value='USER GOLD' ${(user.role == 'USER GOLD')?string('selected', '')}>Пользователь золото</option>
        <option value='ADMIN' ${(user.role == 'ADMIN')?string('selected', '')}>Администратор</option>
    </select>
    <br><br>
    <input type='submit' class="aButton" value='Сохранить изменения'>
    <button type="button" onclick="confirmDelete(${user.id}, '${user.username}')" class="aButton">Удалить пользователя</button>
</form>

<script>
    function confirmDelete(userId, username) {
        if (confirm('Вы уверены, что хотите удалить пользователя ' + username + ' (ID: ' + userId + ')?')) {

            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'deleteUser';

            const userIdInput = document.createElement('input');
            userIdInput.type = 'hidden';
            userIdInput.name = 'id';
            userIdInput.value = userId;

            form.appendChild(userIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении пользователя: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('Пользователь успешно обновлен!');
    }
</script>
</body>
</html>