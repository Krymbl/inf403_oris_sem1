<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Изменить назначение PlayStation</title>
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
<h1>Изменить назначение PlayStation</h1>
<a href="playStationAssignments" class="aButton">Вернуться к списку</a>
<br><br>

<form method='post' action='playStationAssignmentShowOne'>
    <input type='hidden' name='id' value='${playStationAssignment.id}'>
    ID PlayStation: <input type='number' name='playStationId' value='${playStationAssignment.playStationId}' required><br><br>
    ID PlayStation места: <input type='number' name='playStationPlaceId' value='${playStationAssignment.playStationPlaceId}' required><br><br>
    Время назначения: <input type='datetime-local' name='assignedAt' value='${playStationAssignment.assignedAt?string("yyyy-MM-dd'T'HH:mm")}' required><br><br>
    Время снятия: <input type='datetime-local' name='unassignedAt' value='${(playStationAssignment.unassignedAt?string("yyyy-MM-dd'T'HH:mm"))!''}'><br><br>
    Активно:
    <input type='radio' name='isActive' value='true' ${playStationAssignment.isActive?string('checked', '')}> Да
    <input type='radio' name='isActive' value='false' ${playStationAssignment.isActive?string('', 'checked')}> Нет
    <br><br>
    <input type='submit' value='Сохранить изменения' class="aButton">
    <button type="button" onclick="confirmDelete(${playStationAssignment.id})" class="aButton">Удалить назначение</button>
</form>

<script>
    function confirmDelete(assignmentId) {
        if (confirm(`Вы уверены, что хотите удалить назначение (ID:` + assignmentId + `)?`)) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'deletePlayStationAssignment';

            const assignmentIdInput = document.createElement('input');
            assignmentIdInput.type = 'hidden';
            assignmentIdInput.name = 'id';
            assignmentIdInput.value = assignmentId;

            form.appendChild(assignmentIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('error')) {
        const errorMessage = decodeURIComponent(urlParams.get('error'));
        alert('Ошибка при сохранении назначения: ' + errorMessage);
    }

    if (urlParams.has('success')) {
        alert('Назначение успешно обновлено!');
    }
</script>
</body>
</html>