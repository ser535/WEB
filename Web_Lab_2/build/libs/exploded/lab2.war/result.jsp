<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.models.PointCheckResult" %>
<%@ page import="org.example.models.Point" %>
<html>
<head>
    <title>Результат</title>
    <style>
        .result-wrapper {
            width: 100%;
            display: flex;
            justify-content: center;
            margin-top: 40px;
        }

        .result-block {
            text-align: center;
            float: none;
            width: 60%;
            max-width: 900px;
            border: 2px solid black;
            border-radius: 10px;
            padding: 20px 40px;
            box-sizing: border-box;
            font-size: 20px;
            background-color: white;
            margin: 0 auto;


            height: auto;
            max-height: none;
            overflow: visible;
            display: block;
        }

        .result-block table {
            margin: 0 auto 10px auto;
            border: 2px solid black;
            border-radius: 10px;
            border-collapse: separate;
            width: 100%;
        }

        .back-link {
            display: inline-block;
            margin-top: 5px;
            font-size: 20px;
        }
    </style>
</head>


<body>
<div class="result-wrapper">
    <div class="result-block">

        <h1>Результат</h1>

        <% PointCheckResult pcr = (PointCheckResult) request.getSession().getAttribute("pointCheckResult"); %>

        <table>
            <tr>
                <th>Попадание</th>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Время</th>
                <th>Время работы (мс)</th>
            </tr>

            <% if (pcr != null && !pcr.getResults().isEmpty()) {
                for (Point point : pcr.getResults()) { %>

            <tr>
                <td><%= point.getIsHit() ? "Да" : "Нет" %></td>
                <td><%= point.getX() %></td>
                <td><%= point.getY() %></td>
                <td><%= point.getR() %></td>
                <td><%= point.getTime() %></td>
                <td><%= point.getWorktime() %></td>
            </tr>

            <%   }
            } %>
        </table>

        <a href="index.jsp" class="back-link">Назад</a>

    </div>
</div>
</body>
</html>
