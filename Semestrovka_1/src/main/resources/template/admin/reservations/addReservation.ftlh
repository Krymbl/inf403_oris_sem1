<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить бронирование</title>
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
        .error {
            color: red;
            background-color: #ffe6e6;
            padding: 10px;
            border: 1px solid red;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<h1>Добавить бронирование</h1>
<a class="aButton" href="reservations">Вернуться к списку</a>
<br><br>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<form method='post' action='addReservation'>
    ID пользователя: <input type="number" name="userId" value='' required><br><br>
    Номер телефона: <input type="text" name="personPhoneNumber" value='' required><br><br>
    ID игрового места: <input type="number" name="gamePlaceId" value='' required><br><br>
    Время начала: <input type='datetime-local' name='startTime' value='' required><br><br>
    Время окончания: <input type='datetime-local' name='endTime' value='' required><br><br>
    Цена: <input type="number" name="totalPrice" value='' required><br><br>
    Статус:
    <select name='status' required>
        <option value='ACTIVE'>Активно</option>
        <option value='COMPLETED'>Завершено</option>
        <option value='CANCELLED'>Отменено</option>
    </select>
    <br><br>
    <input type='submit' class="aButton" value='Добавить бронирование'>
</form>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при создании бронирования: ' + errorMessage);
    }
    if (urlParams.has('success')) {
        alert('Бронирование успешно добавлено!');
    }
</script>
</body>
</html>