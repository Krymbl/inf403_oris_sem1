<html>
<head>
    <meta charset="UTF-8">
    <title> Крестики-нолики, две Миланы в домике</title>
    <meta name="description" content="Крестики-нолики, две Миланы в домике">
    <style>
        div {
            margin: 5px
        }
        label {
            color: red;
        }
        .div_style {
            display: table;
            border: 3px solid black;
        }
        .table_img {
            width: 90%;
            aspect-ratio: 1/1;
            display: block;
            margin: 0 auto;
        }
        .table_cell {
            border: 2px solid black;
            width: 100px;
            height: 100px;
            text-align: center;
            vertical-align: middle;
            background-color: white;
        }
    </style>

</head>
<body>
    <h1>Крестики-нолики, две Миланы в домике</h1>
    <div class="div_style">
        <table style = "width: 300px">
            <#list table as r>
                <tr>
                    <td class="table_cell"><img class="table_img" src = "static/img/${r.f}"></td>
                    <td class="table_cell"><img class="table_img" src = "static/img/${r.s}"></td>
                    <td class="table_cell"><img class="table_img" src = "static/img/${r.t}"></td>
                </tr>
            </#list>
        </table>
    </div>
<form method="post" action="/lab03/game">
    <input type="hidden" name="uid" value="${uid}">
    <div>
        <label>Номер строки:</label>
        <input type="number" name="row" min="1" max="3" required>
    </div>
    <div>
        <label>Номер столбца:</label>
        <input type="number" name="column" min="1" max="3" required>
    </div>
    <div>
        <input type="submit" value="Отправить">
    </div>
</form>
<form method="post" action="/lab03/game">

    <input type="hidden" name="action" value="restart">
    <input type="hidden" name="uid" value="${uid}">
    <input type="submit" value="Перезапуск">
</form>
    <div>
        <span>${status}</span>
    </div>
</body>
</html>