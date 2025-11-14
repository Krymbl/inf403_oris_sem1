<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить игровое место</title>
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
<h1>Изменить игровое место</h1>
<a href="gamingPlaces" class="aButton">Вернуться к списку</a>
<br><br>

<form method='post' action='gamingPlaceShowOne'>
    <input type='hidden' name='id' value='${gamingPlace.id}'>
    Название: <input type='text' name='name' value='${gamingPlace.name}' required><br><br>
    Тип:
    <select name='type' required>
        <option value='COMPUTER' ${(gamingPlace.type == 'COMPUTER')?string('selected', '')}>Компьютер</option>
        <option value='PLAYSTATION' ${(gamingPlace.type == 'PLAYSTATION')?string('selected', '')}>PlayStation</option>
    </select><br><br>
    Категория:
    <select name='category' required>
        <option value='STANDARD' ${(gamingPlace.category == 'STANDARD')?string('selected', '')}>Стандарт</option>
        <option value='VIP' ${(gamingPlace.category == 'VIP')?string('selected', '')}>VIP</option>
        <option value='PREMIUM' ${(gamingPlace.category == 'PREMIUM')?string('selected', '')}>Премиум</option>
    </select><br><br>
    Цена за час: <input type='number' step='0.01' name='pricePerHour' value='${gamingPlace.pricePerHour}' required><br><br>
    Доступно:
    <input type='radio' name='isAvailable' value='true' ${gamingPlace.available?string('checked', '')}> Да
    <input type='radio' name='isAvailable' value='false' ${gamingPlace.available?string('', 'checked')}> Нет
    <br><br>
    <input type='submit' value='Сохранить изменения' class="aButton">
    <button type="button" onclick="confirmDelete(${gamingPlace.id}, '${gamingPlace.name}')" class="aButton">Удалить игровое место</button>
</form>

<script>
    function confirmDelete(gamingPlaceId, gamingPlaceName) {
        if (confirm(`Вы уверены, что хотите удалить игровое место ` + gamingPlaceName  + ` (ID:` + gamingPlaceId + `)?`)) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'deleteGamingPlace';

            const gamingPlaceIdInput = document.createElement('input');
            gamingPlaceIdInput.type = 'hidden';
            gamingPlaceIdInput.name = 'id';
            gamingPlaceIdInput.value = gamingPlaceId;

            form.appendChild(gamingPlaceIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении игрового места: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('Игровое место успешно обновлено!');
    }
</script>
</body>
</html>