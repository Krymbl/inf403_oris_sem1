<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Бронирование - Computer Club</title>
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
        }

        .header a {
            color: white;
            text-decoration: none;
            margin-right: 1.5rem;
        }

        .main {
            padding: 2rem;
            max-width: 800px;
            margin: 0 auto;
        }

        h1 {
            text-align: center;
            color: #343a40;
            margin-bottom: 2rem;
        }

        .form-group {
            margin-bottom: 1rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: bold;
        }

        select, input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .btn {
            padding: 12px 24px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }

        .btn:hover {
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
    <a href="index">Главная</a>
    <a href="user/profile">Профиль</a>
    <a href="user/reservations">Мои брони</a>
    <span style="float:right">
            <a href="logout">Выйти</a>
        </span>
</div>

<div class="main">
    <h1>Бронирование места</h1>

    <#if error??>
        <div class="alert alert-error">${error}</div>
    </#if>

    <#if success??>
        <div class="alert alert-success">${success}</div>
    </#if>

    <form method="post" action="createReservation">
        <div class="form-group">
            <label>Тип места:</label>
            <select name="type" id="type" required onchange="updatePlaces()">
                <option value="">Выберите тип</option>
                <option value="COMPUTER">Компьютер</option>
                <option value="PLAYSTATION">PlayStation</option>
            </select>
        </div>

        <div class="form-group">
            <label>Место:</label>
            <select name="gamePlaceId" id="gamePlaceId" required>
                <option value="">Сначала выберите тип</option>
            </select>
        </div>

        <div class="form-group">
            <label>Дата и время начала:</label>
            <input type="datetime-local" name="startTime" required>
        </div>

        <div class="form-group">
            <label>Количество часов:</label>
            <select name="hours" required>
                <option value="1">1 час</option>
                <option value="2" selected>2 часа</option>
                <option value="3">3 часа</option>
                <option value="4">4 часа</option>
            </select>
        </div>

        <div class="form-group">
            <label>Контактный телефон:</label>
            <input type="text" name="personNumber" value="${user.phoneNumber}" required>
        </div>

        <button type="submit" class="btn">Забронировать</button>
    </form>
</div>

<script>
    function updatePlaces() {
        const type = document.getElementById('type').value;
        const placeSelect = document.getElementById('gamePlaceId');

        placeSelect.innerHTML = '<option value="">Загрузка...</option>';

        if (type) {
            fetch('availablePlaces?type=' + type)
                .then(response => response.json())
                .then(places => {
                    placeSelect.innerHTML = '<option value="">Выберите место</option>';
                    places.forEach(place => {
                        const option = document.createElement('option');
                        option.value = place.id;
                        option.textContent = place.name + ' - ' + place.pricePerHour + ' руб/час';
                        placeSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    placeSelect.innerHTML = '<option value="">Ошибка загрузки</option>';
                });
        } else {
            placeSelect.innerHTML = '<option value="">Сначала выберите тип</option>';
        }
    }
</script>
</body>
</html>