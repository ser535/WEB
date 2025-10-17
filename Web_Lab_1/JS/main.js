import Validate from './validate.js';

const validator = new Validate();

let x;
let r;

const buttonsX = document.querySelectorAll('.x')
buttonsX.forEach(button => {
    button.addEventListener('click', function () {
        x = this.getAttribute('value');
        // document.getElementById('chosenX').textContent = x;
        console.log("Выбран X:", x);
    });
});

const buttonsR = document.querySelectorAll('.r')
buttonsR.forEach(button => {
    button.addEventListener('click', function () {
        r = this.getAttribute('value');
        //  document.getElementById('chosenR').textContent = r;
    });
});

document.getElementById('send-button').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById("input-log").innerText = "SEND!!";

    const y = document.getElementById('y-input').value.trim();

    const check = validator.check(x, y, r);
    if (check.allOk) {
        console.log(x, y, r);
        const coords = validator.getCoords();
        //let data = {x:x, y:y, r:r};
        const params = new URLSearchParams({ x, y, r });

        fetch('/fcgi-bin/fcgi_server.jar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: params.toString()
        })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP ${response.status}`);
                return response.json();
            })
            .then(res => {
                localStorage.setItem('session', JSON.stringify(res));

                if (res.error) {
                    const map = { fill: 'Заполните все поля', method: 'Только POST запросы' };
                    document.getElementById('input-log').innerText = map[res.error] ?? res.error;
                    return;
                }

                document.getElementById('input-log').innerText = '';

                const tbody = document.querySelector('#result-table tbody');
                const row = document.createElement('tr');

                const isHit = document.createElement('td');
                const tdX = document.createElement('td');
                const tdY = document.createElement('td');
                const tdR = document.createElement('td');
                const time = document.createElement('td');
                const worktime = document.createElement('td');

                const hit = (res.result === true || res.result === 'true');
                isHit.innerText = hit ? 'Попали!' : 'Неудача!';
                tdX.innerText = res.x;
                tdY.innerText = res.y;
                tdR.innerText = res.r;
                time.innerText = res.time;
                worktime.innerText = res.workTime;

                row.append(isHit, tdX, tdY, tdR, time, worktime);
                tbody.appendChild(row);

                // Обновляем точку
                const xx = parseFloat(res.x);
                const yy = parseFloat(res.y);
                const rr = parseFloat(res.r);
                const cx = 300 + xx * (200 / rr);
                const cy = 300 - yy * (200 / rr);
                const dot = document.getElementById('dot');
                dot.setAttribute('cx', String(cx));
                dot.setAttribute('cy', String(cy));
                dot.setAttribute('visibility', 'visible');

                const resultBlock = document.querySelector('.result-block');
                resultBlock.scrollTop = resultBlock.scrollHeight;
            })
            .catch(err => {
                alert(err.message);
            })

    } else {
        document.getElementById("input-log").innerText = check.log;
    }
});