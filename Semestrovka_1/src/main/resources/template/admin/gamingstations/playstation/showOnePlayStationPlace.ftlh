<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить PlayStation место</title>
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
<h1>Изменить PlayStation место</h1>
<a href="playStationPlaces" class="aButton">Вернуться к списку</a>
<br><br>

<form method='post' action='playStationPlaceShowOne'>
    <input type='hidden' name='id' value='${playStationPlace.id}'>
    ID игрового места: <input type='number' name='gamingPlaceId' value='${playStationPlace.gamingPlaceId}' required><br><br>
    Телевизор: <input type='text' name='tv' value='${playStationPlace.tv}' required><br><br>
    Максимальное количество игроков: <input type='number' name='maxPlayers' value='${playStationPlace.maxPlayers}' required><br><br>
    <input type='submit' value='Сохранить изменения' class="aButton">
    <button type="button" onclick="confirmDelete(${playStationPlace.id})" class="aButton">Удалить PlayStation место</button>
</form>

<script>
    function confirmDelete(playStationPlaceId) {
        if (confirm(`Вы уверены, что хотите удалить PlayStation место (ID:` + playStationPlaceId + `)?`)) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'deletePlayStationPlace';

            const playStationPlaceIdInput = document.createElement('input');
            playStationPlaceIdInput.type = 'hidden';
            playStationPlaceIdInput.name = 'id';
            playStationPlaceIdInput.value = playStationPlaceId;

            form.appendChild(playStationPlaceIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении PlayStation места: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('PlayStation место успешно обновлено!');
    }
</script>
</body>
</html>