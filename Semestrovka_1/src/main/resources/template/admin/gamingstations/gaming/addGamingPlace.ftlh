<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить игровое место</title>
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
<h1>Добавить игровое место</h1>
<a class="aButton" href="gamingPlaces">Вернуться к списку</a>
<br><br>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<form method='post' action='addGamingPlace'>
    Название: <input type='text' name='name' value='' required><br><br>
    Тип:
    <select name='type' required>
        <option value='COMPUTER'>Компьютер</option>
        <option value='PLAYSTATION'>PlayStation</option>
    </select><br><br>
    Категория:
    <select name='category' required>
        <option value='STANDARD'>Стандарт</option>
        <option value='VIP'>VIP</option>
        <option value='PREMIUM'>Премиум</option>
    </select><br><br>
    Доступно:
    <input type='checkbox' name='isAvailable' value='true' checked> Да<br><br>
    Цена за час: <input type='number' name='pricePerHour' value='' required min="0" step="0.01"><br><br>
    <input type='submit' class="aButton" value='Добавить игровое место'>
</form>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при создании игрового места: ' + errorMessage);
    }
    if (urlParams.has('success')) {
        alert('Игровое место успешно добавлено!');
    }
</script>
</body>
</html>