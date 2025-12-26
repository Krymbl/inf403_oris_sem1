<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Самолеты</title>
    <meta name="description" content="Самолеты">
    <style>
        .tablo {
            display: flex;
            flex-direction: column;
        }
        .tablo-row {
            display: flex;
            flex-direction: row;
            font-weight: lighter;
        }
        .tablo-head {
            font-weight: bold;
        }
        .tablo-row > div {
            padding: 8px 12px;
            flex: 1;
        }
    </style>
</head>
<body>
<h1>Самолеты</h1>

<div class="tablo">

    <form action="tables" method="post">
        <div class="topic">
            <select class="select_airport" name="airportCode">
                <#list airports as airport>
                <option value="${airport.code}"
                        <#if selectedAirport?? && selectedAirport == airport.code>selected</#if>>
                    ${airport.name} </option>
                </#list>
            </select>
            <input class="date" name="date" type="date"
                   value="<#if selectedDate??>${selectedDate}</#if>" required>
            <select class="select_kind" name="type">
                <option value="departure" <#if selectedType?? && selectedType == 'departure'>selected</#if>>Вылет</option>
                <option value="arrival" <#if selectedType?? && selectedType == 'arrival'>selected</#if>>Прилет</option>
            </select>

            <input type="submit" value="Отправить">
        </div>
    </form>


    <#if flightsInfo??>
        <div class="tablo-row tablo-head">
            <div>Номер рейса</div>
            <div>Время отправления</div>
            <div>Время прилета</div>
            <div>Состояние</div>
            <div>Пункт</div>
            <div>Город</div>
        </div>

        <#list flightsInfo as flightInfo>
            <div class="tablo-row">
                <div>${flightInfo.routeNo}</div>
                <div>${flightInfo.scheduledDeparture}</div>
                <div>${flightInfo.scheduledArrival}</div>
                <div>${flightInfo.status}</div>
                <div>${flightInfo.airportName}</div>
                <div>${flightInfo.city}</div>
            </div>
        </#list>
    </#if>

</div>
</body>
</html>