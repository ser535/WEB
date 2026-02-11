<template>
  <div class="content-wrapper">
    <div class="under-header">
      <div class="user-info">
        <span>Логин: <b>{{ loginName || "—" }}</b></span>
      </div>

      <button id="logout" class="backButton" type="button" @click="logout">
        Выйти из аккаунта
      </button>
    </div>

    <div class="container">
      <div class="input-block">
        <div class="input-header">
          <h2>Введите X, Y и R</h2>
        </div>

        <div id="mainForm">
          <div class="vhod-block">

            <div class="input-login" id="x-coord">
              <h3>X:</h3>
              <div class="radio-row" id="x-coord-row">
                <label class="radio-item" v-for="v in allowedX" :key="'x-' + v">
                  <input type="radio" name="x" :value="v" v-model.number="x">
                  <span>{{ v }}</span>
                </label>
              </div>
            </div>

            <div class="input-Y">
              <h3>Y:</h3>
              <div class="row">
                <label class="text-label">
                  <input
                      id="input-y"
                      class="text-input"
                      type="text"
                      name="y"
                      placeholder="(-5..5)"
                      maxlength="5"
                      v-model.trim="y"
                  >
                </label>
              </div>
              <br>
              <div class="send-button">
                <button id="submit-button" @click="validateForm" type="button">
                  Проверить
                </button>
              </div>

              <div style="text-align: center; margin-top: 20px;">
                <button id="remove-button" class="deleteButton" @click="deleteDots">
                  Удалить все точки
                </button>
              </div>

            </div>

            <div class="input-password" id="r-val">
              <h3>R:</h3>
              <div class="radio-row" id="r-val-row">
                <label class="radio-item" v-for="v in allowedR" :key="'r-' + v">
                  <input type="radio" name="r" :value="v" v-model.number="r">
                  <span>{{ v }}</span>
                </label>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="graph-block">
        <svg
            class="graphSVG"
            id="graph"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 600 600"
            preserveAspectRatio="xMidYMid meet"
            width="100%"
            height="100%"
            @click="validateFromGraph"
        >
          <line stroke="gray" x1="0" x2="600" y1="300" y2="300" />
          <line stroke="gray" x1="300" x2="300" y1="0" y2="600" />
          <polygon points="300,0 294,15 306,15" stroke="white" />
          <polygon points="600,300 585,306 585,294" stroke="white" />

          <line stroke="gray" x1="400" x2="400" y1="310" y2="290" />
          <line stroke="gray" x1="500" x2="500" y1="310" y2="290" />
          <line stroke="gray" x1="200" x2="200" y1="310" y2="290" />
          <line stroke="gray" x1="100" x2="100" y1="310" y2="290" />

          <line stroke="gray" x1="290" x2="310" y1="100" y2="100" />
          <line stroke="gray" x1="290" x2="310" y1="200" y2="200" />
          <line stroke="gray" x1="290" x2="310" y1="400" y2="400" />
          <line stroke="gray" x1="290" x2="310" y1="500" y2="500" />

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

          <rect x="300" y="100" width="100" height="200"
                fill="#0a0eff" fill-opacity="0.1" stroke="#0c0fff"/>

          <polygon points="200,300 300,300 300,400"
                   fill="#0a0eff" fill-opacity="0.1" stroke="#0c0fff"/>

          <path d="M 500 300 A 200 200 0 0 1 300 500 L 300 300 Z"
                fill="#0a0eff" fill-opacity="0.1" stroke="#0c0fff"></path>
        </svg>
      </div>

      <div class="result-block">
        <table id="res-table" class="results-table">
          <thead>
          <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Время</th>
            <th>Попадание</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="dot in dots" :key="dot.id || (dot.x + '_' + dot.y + '_' + dot.time)">
            <td>{{ dot.x }}</td>
            <td>{{ dot.y }}</td>
            <td>{{ dot.r }}</td>
            <td>{{ dot.time }}</td>
            <td>{{ dot.result ? 'Да' : 'Нет' }}</td>
          </tr>

          <tr v-if="!dots.length">
            <td colspan="5" style="text-align:center; padding: 12px;">
              Пока нет результатов
            </td>
          </tr>
          </tbody>
        </table>
      </div>


    </div>
  </div>
</template>

<script>

export default {
  name: "Main",

  data() {
    return {
      allowedX: [-3,-2,-1,0,1,2,3,4,5],
      allowedR: [-3,-2,-1,0,1,2,3,4,5],

      x: null,
      y: "",
      r: 1,

      xGraph: "",
      yGraph: "",
      dots: [],

      formError: "",
      errorMessage: "",

      loginName: "",


    };
  },


  watch: {
    y(val) {
      this.validateInput(-5, 5, "input-y", val);
    },
    r() {
      this.drawDots();
    }
  },


  methods: {
    getLoginFromJwt() {
      const token = localStorage.getItem("jwt");
      if (!token) return "";

      try {
        const payload = token.split(".")[1];
        const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");
        const json = decodeURIComponent(
            atob(base64)
                .split("")
                .map(c => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
                .join("")
        );
        const obj = JSON.parse(json);
        return obj.sub || "";
      } catch (e) {
        return "";
      }
    },

    addDots(x, y) {
      const roundedX = Number(parseFloat(x).toFixed(3));
      const roundedY = Number(parseFloat(y).toFixed(3));

      this.$axios
          .put(
              "/api/point",
              { x: roundedX, y: roundedY, r: this.r },
              { headers: { Authorization: "Bearer " + localStorage.getItem("jwt") } }
          )
          .then(() => {
            this.loadDots();
            this.$notify({
              group: "success",
              title: "Добавление точки",
              text: "Успешно",
              type: "success",
            });
          })
          .catch(() => {
            this.AxiosErrorHandler("Точку не удалось добавить");
          });
    },

    deleteDots() {
      this.$axios
          .delete("/api/point", {
            headers: { Authorization: "Bearer " + localStorage.getItem("jwt") },
          })
          .then(() => {
            this.loadDots();
            this.$notify({
              group: "success",
              title: "Удаление точек",
              text: "Успешно",
              type: "success",
            });
          })
          .catch(() => {
            this.AxiosErrorHandler("Точки не удалось удалить");
          });
    },

    logout() {
      this.$notify({
        group: "success",
        title: "Выход из аккаунта",
        text: "Успешно",
        type: "success",
      });
      this.$router.push({ name: "auth-page" }, () => localStorage.clear());
    },

    loadDots() {
      this.$axios
          .get("/api/point", {
            headers: { Authorization: "Bearer " + localStorage.getItem("jwt") },
          })
          .then((response) => {
            this.dots = Array.isArray(response.data) ? response.data : [];
            this.drawDots();
          })
          .catch(() => {
            this.AxiosErrorHandler("Точки не удалось загрузить");
          });
    },

    clearSvgDots() {
      const svg = document.getElementById("graph");
      if (!svg) return;
      const points = svg.querySelectorAll("circle");
      points.forEach((p) => p.parentNode && p.parentNode.removeChild(p));
    },

    toSvgCoords(x, y, r) {
      const cx0 = 300;
      const cy0 = 300;
      const base = 200;

      const xn = parseFloat(x);
      const yn = parseFloat(y);
      const rn = parseFloat(r);

      if (Number.isNaN(xn) || Number.isNaN(yn) || Number.isNaN(rn) || rn === 0) {
        return null;
      }

      const pixelsPerUnit = base / rn;

      return {
        cx: cx0 + xn * pixelsPerUnit,
        cy: cy0 - yn * pixelsPerUnit,
      };
    },

    isHit(result) {
      if (result === true) return true;
      const s = String(result).trim().toLowerCase();
      return s === "да" || s === "true" || s === "hit";
    },

    drawDots() {
      const svg = document.getElementById("graph");
      if (!svg) return;

      this.clearSvgDots();

      const r = parseFloat(this.r);
      if (Number.isNaN(r) || r === 0) return;
      if (!this.dots || this.dots.length === 0) return;

      this.dots.forEach((dot) => {
        const coords = this.toSvgCoords(dot.x, dot.y, r);
        if (!coords) return;

        const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        point.setAttribute("cx", String(coords.cx));
        point.setAttribute("cy", String(coords.cy));
        point.setAttribute("r", "5");
        point.setAttribute("fill", this.isHit(dot.result) ? "green" : "red");

        svg.appendChild(point);
      });
    },

    validateFromGraph(event) {
      const svg = document.getElementById("graph");
      if (!svg) return;

      const point = new DOMPoint(event.clientX, event.clientY);

      const svgPoint = point.matrixTransform(svg.getScreenCTM().inverse());

      const r = parseFloat(this.r);
      if (Number.isNaN(r) || r === 0) {
        this.AxiosErrorHandler("Да, есть возможность выбрать 0. Но это очень-очень плохо. Выберите другой R, пожалуйста");
        return;
      }

      const pixelsPerUnit = 200 / r;

      const normalizedX = (svgPoint.x - 300) / pixelsPerUnit;
      const normalizedY = (300 - svgPoint.y) / pixelsPerUnit;

      this.xGraph = Number(normalizedX.toFixed(3));
      this.yGraph = Number(normalizedY.toFixed(3));

      this.addDots(this.xGraph, this.yGraph);
    },

    validateForm() {
      if (this.x === null || this.r === null) {
        this.AxiosErrorHandler("Проверьте введенные данные");
        return;
      }

      const r = Number(this.r);
      if (r === 0) {
        this.AxiosErrorHandler("Да, есть возможность выбрать 0. Но это очень-очень плохо. Выберите другой R, пожалуйста");
        return;
      }

      const yRaw = String(this.y ?? "").trim();
      if (yRaw === "") {
        this.AxiosErrorHandler("Проверьте введенные данные");
        return;
      }

      const yStr = yRaw.replace(",", ".");
      if (!this.isStrictNumber(yStr)) {
        this.AxiosErrorHandler("Проверьте введенные данные");
        return;
      }

      const x = Number(this.x);
      const y = Number(yStr);

      const ok =
          x >= -3 && x <= 5 &&
          y >= -5 && y <= 5 &&
          r >= -3 && r <= 5;

      if (!ok) {
        this.AxiosErrorHandler("Проверьте введенные данные");
        return;
      }

      this.addDots(x, y);
    },



    isStrictNumber(val) {
      const s = String(val ?? "").trim().replace(",", ".");
      return /^-?\d+(\.\d+)?$/.test(s);
    },




    validateInput(min, max, elementId, val) {
      const element = document.getElementById(elementId);
      if (!element) return false;

      const s = String(val ?? "").trim().replace(",", ".");
      const ok = this.isStrictNumber(s) && (Number(s) >= min) && (Number(s) <= max);

      if (!ok) {
        element.classList.add("red");
        element.style.color = "red";
        return false;
      } else {
        element.classList.remove("red");
        element.style.color = "";
        return true;
      }
    },


    AxiosErrorHandler(errorText) {
      this.$notify({
        group: "error",
        title: "Error",
        text: errorText,
        type: "error",
      });
    },
  },

  mounted() {
    this.loginName = this.getLoginFromJwt();
    this.loadDots();
  },

};
</script>


<style scoped>

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

.vhod-block {
  display: flex;
  justify-content: space-between;
}

.input-login {
  margin-left: 5%;
}
.deleteButton {
  margin-bottom: 5%;
}

.input-password {
  margin-right: 5%;
}

.send-button {
  text-align: center;
}

.result-block {
  height: 47vh;
  width: 50%;
  float: left;
  border: 2px solid black;
  border-radius: 10px;
  font-size: 20px;
  margin-top: 0.4%;
  box-sizing: border-box;
  overflow-y: auto;
}

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

th, tr, td {
  border: 2px solid black;
  border-radius: 10px;
}

.graph-block {
  height: 80vh;
  float: right;
  width: 49%;
  border: 2px solid black;
  border-radius: 10px;
  font-size: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  box-sizing: border-box;
}

.graphSVG {
  width: 100%;
  height: 100%;
  max-width: 600px;
  max-height: 600px;
  display: block;
}


.backButton {
  padding: 10px 20px;
  border: 2px solid black;
  border-radius: 10px;
  background: white;
  cursor: pointer;
}

.container::after {
  content: "";
  display: table;
  clear: both;
}

.radio-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px 14px;
  justify-content: center;
  margin: 10px 0 20px;
}

.radio-item {
  display: flex;
  align-items: center;
  cursor: pointer;
  user-select: none;
}

.radio-item input {
  cursor: pointer;
}
#submit-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

#submit-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.under-header{
  display: flex;
  align-items: center;
  margin: 12px 20px 10px 20px;
}

.user-info{
  font-size: 18px;
  margin-right: 20px;
}

.radio-row {
  display: inline-flex;
  flex-wrap: wrap;
  border-radius: 12px;
  background: #f0f0f0;
  padding: 4px;
  margin: 10px 0;
  width: 100%;
  box-sizing: border-box;
}

.radio-item {
  display: flex;
  flex: 0 0 60px;
}

.radio-item input[type="radio"] {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
}

.radio-item span {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 8px 0;
  font-size: 16px;
  font-weight: 500;
  color: #666;
  background: transparent;
  border-radius: 8px;
  transition: all 0.15s;
  white-space: nowrap;
}

.radio-item input[type="radio"]:checked + span {
  background: white;
  color: #0a0eff;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
}
/* DESKTOP*/

.input-block {
  width: 50%;
  height: auto;
}

.graph-block {
  width: 49%;
}

.result-block {
  width: 50%;
  margin-top: 10px;
}


@media (max-width: 1176px) {
  .container {
    display: flex;
    flex-direction: column;
  }

  .input-block,
  .graph-block,
  .result-block {
    width: 100%;
  }
  .radio-item {
    flex: 0 0 55px;
  }
}

@media (max-width: 698px) {
  .container {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }

  .input-block,
  .graph-block,
  .result-block {
    width: 100%;
    height: auto;
  }

  .graphSVG {
    width: 100%;
    height: auto;
  }
  .graph-block {
    height: auto;
    min-height: 350px;
    padding: 10px;
  }

  .graphSVG {
    width: 100%;
    height: auto;
    max-height: 80vh;
  }
  .radio-row {
    width: 100%;
    justify-content: center;
  }

  .radio-item span {
    padding: 6px 12px;
    font-size: 14px;
  }
}

</style>
