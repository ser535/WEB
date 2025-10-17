// main.js

import Validate from './validate.js';
const validator = new Validate();

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('form');
    const resultsTable = document.getElementById('result-table'); // предполагаем, что таблица существует
    const tbody = resultsTable.querySelector('tbody'); // находим tbody для добавления строк

    // Обработчик отправки формы
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const x = getXValue();
        const y = getYValue();
        const r = getRValue();

        const check = validator.check(x, y, r);
    });

    if (check.allOk) {
        const coords = validator.getCoords();
    }

        // Функции получения значений (оставляем как есть)
        function getXValue() {
            const xInput = document.querySelector('input[name="x"]:checked');
            if (xInput) return parseFloat(xInput.value);

            const xText = document.querySelector('input[name="x"]');
            if (xText) return parseFloat(xText.value);

            return null;
        }

        function getYValue() {
            const yInput = document.querySelector('input[name="y"]');
            if (yInput) return parseFloat(yInput.value);
            return null;
        }

        function getRValue() {
            const rSelect = document.querySelector('select[name="r"]');
            if (rSelect) return parseFloat(rSelect.value);

            const rInput = document.querySelector('input[name="r"]:checked');
            if (rInput) return parseFloat(rInput.value);

            const rText = document.querySelector('input[name="r"]');
            if (rText) return parseFloat(rText.value);

            return null;
        }

        // Функция отправки данных на сервер
        function sendDataToServer(x, y, r) {
            const data = {x, y, r};
            fetch('/api/check-point', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(result => {
                    handleServerResponse(result);
                })
                .catch(error => {
                    console.error('Ошибка:', error);
                    showError('Произошла ошибка при отправке данных на сервер: ' + error.message);
                });
        }

        // Обработка ответа от сервера и добавление в таблицу
        function handleServerResponse(response) {
            // Создаем новую строку таблицы
            const newRow = document.createElement('tr');

            // Заполняем ячейки данными из JSON
            newRow.innerHTML = `
            <td>${response.x || ''}</td>
            <td>${response.y || ''}</td>
            <td>${response.r || ''}</td>
            <td class="${response.hit ? 'hit' : 'miss'}">
                ${response.hit ? 'Попадание' : 'Промах'}
            </td>
            <td>${response.calculationTime || ''}</td>
            <td>${formatTimestamp(response.timestamp)}</td>
        `;


            // Добавляем строку в начало таблицы (новые результаты сверху)
            if (tbody) {
                tbody.insertBefore(newRow, tbody.firstChild);
                // } else {
                //     // Если tbody нет, создаем его
                //     const newTbody = document.createElement('tbody');
                //     newTbody.appendChild(newRow);
                //     resultsTable.appendChild(newTbody);
            }
        }

        // Форматирование времени
        function formatTimestamp(timestamp) {
            if (!timestamp) return '';

            const date = new Date(timestamp);
            return date.toLocaleString();
        }

        // Показать ошибку (упрощенная версия)
        function showError(message) {
            console.error('Error:', message);

            // Простой вывод ошибки в консоль, можно добавить уведомление если нужно
            const errorDiv = document.createElement('div');
            errorDiv.style.cssText = `
            background-color: #ffebee;
            color: #c62828;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ef5350;
            border-radius: 4px;
            position: fixed;
            top: 10px;
            right: 10px;
            z-index: 1000;
        `;
            errorDiv.textContent = message;
            document.body.appendChild(errorDiv);

            // Автоматически скрываем через 5 секунд
            setTimeout(() => {
                errorDiv.remove();
            }, 5000);
        }

        // Загрузка истории результатов при загрузке страницы
        function loadHistory() {
            fetch('/api/results')
                .then(response => response.json())
                .then(history => {
                    if (Array.isArray(history)) {
                        history.forEach(result => handleServerResponse(result));
                    }
                })
                .catch(error => {
                    console.error('Ошибка загрузки истории:', error);
                });
        }

        // Загружаем историю при загрузке страницы
        loadHistory();
    });