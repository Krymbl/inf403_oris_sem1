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
            border: 2px solid green;
        }
    </style>

</head>
<body>
    <h1>Крестики-нолики, две Миланы в домике</h1>

    <table width="300">
        <#list table as r>
            <tr>
                <td><img src = "/lab03/static/img/tac.svg"></td>
                <td><img src = "/lab03/static/img/${r.s}"></td>
                <td><img src = "/lab03/static/img/${r.t}"></td>
            </tr>
        </#list>
    </table>

<form method="post" action="/lab03/game">
    <input type="hidden" name="uid" value="${uid}">
    <div>
        <label>Номер строки:</label>
        <input type="text" name="row">
    </div>
    <div>
        <label>Номер столбца:</label>
        <input type="text" name="column">
    </div>
    <div>
        <input type="submit" name="Отправить">
    </div>

</form>
</body>
</html>