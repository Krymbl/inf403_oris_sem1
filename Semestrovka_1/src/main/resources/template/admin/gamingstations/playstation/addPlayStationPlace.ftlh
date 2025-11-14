<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить PlayStation место</title>
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
<h1>Добавить PlayStation место</h1>
<a class="aButton" href="playStationPlaces">Вернуться к списку</a>
<br><br>

<#if error??>
    <div class="error">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<form method='post' action='addPlayStationPlace'>
    ID игрового места: <input type='number' name='gamingPlaceId' value='' required><br><br>
    Телевизор: <input type='text' name='tv' value='' required><br><br>
    Максимальное количество игроков: <input type='number' name='maxPlayers' value='4' required min="1" max="8"><br><br>
    <input type='submit' class="aButton" value='Добавить PlayStation место'>
</form>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при создании PlayStation места: ' + errorMessage);
    }
    if (urlParams.has('success')) {
        alert('PlayStation место успешно добавлено!');
    }
</script>
</body>
</html>