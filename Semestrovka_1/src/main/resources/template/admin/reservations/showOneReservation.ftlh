<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить бронирование</title>
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
        .btn-delete {
            background-color: #dc3545;
            color: white;
            border: 2px solid #dc3545;
        }
    </style>
</head>
<body>
<h1>Изменить бронирование</h1>
<a href="reservations" class="aButton">Вернуться к списку</a>
<br><br>

<form method='post' action='reservationShowOne'>
    <input type='hidden' name='id' value='${reservation.id}'>
    ID пользователя: <input type="number" name="userId" value='${reservation.userId}' required><br><br>
    ID игрового места: <input type="number" name="gamePlaceId" value='${reservation.gamePlaceId}' required><br><br>
    Номер пользователя: <input type="text" name="personPhoneNumber" value='${reservation.personPhoneNumber}' required><br><br>
    Время начала: <input type='datetime-local' name='startTime' value='${reservation.startTime?string("yyyy-MM-dd'T'HH:mm")}' required><br><br>
    Время окончания: <input type='datetime-local' name='endTime' value='${reservation.endTime?string("yyyy-MM-dd'T'HH:mm")}' required><br><br>
    Цена: <input type="number" name="totalPrice" value='${reservation.totalPrice}' required><br><br>
    Статус:
    <select name='status' required>
        <option value='ACTIVE' ${(reservation.status == 'ACTIVE')?string('selected', '')}>Активно</option>
        <option value='COMPLETED' ${(reservation.status == 'COMPLETED')?string('selected', '')}>Завершено</option>
        <option value='CANCELLED' ${(reservation.status == 'CANCELLED')?string('selected', '')}>Отменено</option>
    </select>
    <br><br>
    <input type='submit' value='Сохранить изменения' class="aButton">
    <button type="button" onclick="confirmDelete(${reservation.id})" class="aButton">Удалить бронирование</button>
</form>

<script>
    function confirmDelete(reservationId) {
        if (confirm(`Вы уверены, что хотите удалить бронирование (ID:` + reservationId + `)?`)) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'deleteReservation';

            const reservationIdInput = document.createElement('input');
            reservationIdInput.type = 'hidden';
            reservationIdInput.name = 'id';
            reservationIdInput.value = reservationId;

            form.appendChild(reservationIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении бронирования: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('Бронирование успешно обновлено!');
    }
</script>
</body>
</html>