<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.models.PointCheckResult" %>
<%@ page import="org.example.models.Point" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Лаба по вебу 2</title>

    <style type="text/css">
        .header {
            height: 10vh;
            text-align: center;
            color: black;
            font-family: sans-serif;
            font-size: 15px;
        }
        #git {
            float: right;
            margin-right: 50px;
        }

        .input-block {
            height: 35vh;
            float: left;
            width: 50%;
            border: 2px solid black;
            border-radius: 10px;
            background-color: rgba(255, 255, 255, 0);
        }

        .input-header h2 {
            text-align: center;
        }

        .input-XYR {
            display: flex;
            justify-content: space-between;
            /*  align-items: center; */
        }

        .input-X {
            margin-left: 5%;
        }

        .input-Y {
        }

        .input-R {
            margin-right: 5%;
        }

        .send-button {
            text-align: center;
        }

        .result-block {
            height: 50vh;
            width: 50%;
            float: left;
            border: 2px solid black;
            border-radius: 10px;
            font-size: 20px;
            margin-top: 0.4%;
            box-sizing: border-box;
            overflow-y: auto;
        }

        th, tr, td {
            border: 2px solid black;
            border-radius: 10px;

            /*border-top-left-radius: 10px;*/
            /*border-top-right-radius: 10px;*/
        }

        tr {
        }

        thead {
            /*border-radius: 10px;*/
        }
        /*.bg-above-thead {*/
        /*    position: relative;*/
        /*    background-color: white;*/
        /*    z-index: 3;*/
        /*    width: 100%;*/
        /*    height: 30px;*/

        /*}*/

        #res-table {
            text-align: center;
            margin: auto;
            width: 100%;
            max-width: 100%;
            border-collapse: separate;
            font-size: 20px;
        }


        #res-table thead th {
            position: sticky;
            top: 2px;
            background-color: white;
            z-index: 4;
            border-bottom: 2px solid black;
            box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1);
        }

        .graph-block {
            height: 80vh;
            float: right;
            width: 49%;
            border: 2px solid black;
            border-radius: 10px;
            font-size: 20px;
        }

        .graph {
            margin-left: 8%;
            margin-top: 2%;
        }

        .input-log-block {
            margin-top: 0.4%;
            height: 5vh;
            float: right;
            width: 49%;
            border: 2px solid black;
            border-radius: 10px;
            font-size: 20px;
        }

        .graphSVG {
            width: 100%;
            height: 100%;
        }



    </style>
</head>
<body>
<div class="header">
    <h1> Хлопцов Сергей Дмитриевич P3213, вариант 1734</h1>
    <a id="git" href="https://github.com/ser535/WEB">проект на гитхабе</a>
</div>
<div class="container" id="container">
    <div class="input-block">
        <div class="input-header">
            <h2>Введите X, Y и R</h2>
        </div>
        <form id="form" method="post" action="controller">
            <div class="input-XYR">
                <div class="input-X" id="x-coord">
                    <h3>X:</h3>

                    <select id="coord-x">
                        <option value="-5">-5</option>
                        <option value="-4">-4</option>
                        <option value="-3">-3</option>
                        <option value="-2">-2</option>
                        <option value="-1">-1</option>
                        <option value="0" selected>0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                </div>
                <div class="input-Y">
                    <h3>Y (-5 ... 5):</h3>
                    <input type="text" name="y" id="coord-y" maxlength="5">
                </div>
                <div class="input-R" id="r-val">
                    <h3>R:</h3>
                    <button id="r1" type="button" class="r">1</button>
                    <button id="r1.5" type="button" class="r">1.5</button>
                    <button id="r2" type="button" class="r">2</button>
                    <button id="r2.5" type="button" class="r">2.5</button>
                    <button id="r3" type="button" class="r">3</button>
                </div>
            </div>
            <div class="send-button">
                <br><br>
                <button type="submit" id="send-button">Отправить</button>
            </div>
        </form>
    </div>
    <div class="graph-block">
        <div class="graph">
            <svg id="graph" class="graphSVG" xmlns="http://www.w3.org/2000/svg" width="650" height="650">

                <line stroke="gray" x1="0" x2="600" y1="300" y2="300"></line>
                <line stroke="gray" x1="300" x2="300" y1="0" y2="600"></line>
                <polygon points="300,0 294,15 306,15" stroke="white"></polygon>
                <polygon points="600,300 585,306 585,294" stroke="white"></polygon>

                <line stroke="gray" x1="400" x2="400" y1="310" y2="290"></line>
                <line stroke="gray" x1="500" x2="500" y1="310" y2="290"></line>

                <line stroke="gray" x1="200" x2="200" y1="310" y2="290"></line>
                <line stroke="gray" x1="100" x2="100" y1="310" y2="290"></line>

                <line stroke="gray" x1="290" x2="310" y1="100" y2="100"></line>
                <line stroke="gray" x1="290" x2="310" y1="200" y2="200"></line>

                <line stroke="gray" x1="290" x2="310" y1="400" y2="400"></line>
                <line stroke="gray" x1="290" x2="310" y1="500" y2="500"></line>

                <text x="385" y="280">R/2</text>
                <text x="495" y="280">R</text>

                <text x="90" y="280">-R</text>
                <text x="180" y="280">-R/2</text>

                <text x="320" y="205">R/2</text>
                <text x="320" y="105">R</text>

                <text x="320" y="405">-R/2</text>
                <text x="320" y="505">-R</text>

                <text x="310" y="15">Y</text>
                <text x="585" y="290">X</text>


                <rect x="100" y="300" width="200" height="100" fill="#0a0eff" fill-opacity="0.1" stroke="#0c0fff"></rect>

                <polygon points="300,200 400,300 300,300" fill="#0a0eff" fill-opacity="0.1" stroke="#0c0fff"></polygon>

                <path d="M 100 300 A 200 200, 0, 0, 1, 300 100 L 300 300 Z" fill="#0a0eff" fill-opacity="0.1" stroke="#0c0fff"></path>

                <circle cx="300" cy="300" class="dot" id="dot" r="5" visibility="visible" stroke="black" fill="black" stroke-opacity="0" fill-opacity="0"></circle>
            </svg>
        </div>
    </div>

    <div class="result-block">
        <div class="bg-above-thead">
        </div>

        <table class="res-table" id="res-table">
            <% PointCheckResult pcr = (PointCheckResult) request.getSession().getAttribute("pointCheckResult");
                if (pcr == null || pcr.getResults().isEmpty()) {
            %>
            <tr>

            </tr>
            <% } else { %>
            <thead>
            <tr>
                <th>Попадание</th>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Время</th>
                <th>Время работы (мс)</th>
            </tr>
            </thead>
            <% for (Point point : pcr.getResults()) { %>
            <tr>
                <td><%= point.getIsHit() ? "Да" : "Нет" %></td>
                <td><%= point.getX() %></td>
                <td><%= point.getY() %></td>
                <td><%= point.getR() %></td>
                <td><%= point.getTime() %></td>
                <td><%= point.getWorktime() %></td>
            </tr>
            <% } %>
            <% } %>
        </table>
    </div>
    <div class="input-log-block" id="input-log">
    </div>

</div>
<script type="module" src="main.js"></script>
</body>
</html>

