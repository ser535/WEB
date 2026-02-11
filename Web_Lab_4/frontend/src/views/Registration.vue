<template>
  <div class="content-wrapper">

    <div class="container">
      <br><br><br><br><br><br><br>
      <div class="input-block">
        <div class="input-header">
          <h2>Вход в систему</h2>
        </div>

        <form id="form" @submit.prevent="logIn">
          <div class="vhod-block">
            <div class="input-login">
              <h3>Логин:</h3>
              <input
                  type="text"
                  id="loginInput"
                  class="inputText"
                  required
                  placeholder="Логин"
                  v-model.trim="login"
              />
            </div>

            <div class="input-password">
              <h3>Пароль:</h3>
              <input
                  type="password"
                  id="passwordInput"
                  class="inputText"
                  required
                  placeholder="Пароль"
                  v-model.trim="password"
              />
            </div>
          </div>

          <div class="send-button">
            <br /><br />
            <button type="submit" class="mybutton" id="loginBtn">
              Войти в приложение
            </button>

            <button type="button" class="mybutton" id="registerBtn" @click="register">
              Зарегистрироваться
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Registration",
  data() {
    return {
      login: "",
      password: "",
    };
  },
  methods: {
    logIn(e) {
      e.preventDefault();
      this.$axios
          .post("/api/user", {
            login: this.login,
            password: this.password,
          })
          .then((response) => {
            localStorage.setItem("jwt", response.data);
            this.$router.push({ name: "app-page" });
          })
          .catch((error) => {
            this.AxiosErrorHandler(error.response?.data || "Ошибка входа");
          });
    },
    register(e) {
      e.preventDefault();
      this.$axios
          .put("/api/user", {
            login: this.login,
            password: this.password,
          })
          .then(() => {
            this.$notify({
              group: "success",
              title: "Регистрация",
              text: "Теперь вы можете войти",
              type: "success",
            });
          })
          .catch((error) => {
            this.AxiosErrorHandler(error.response?.data || "Ошибка регистрации");
          });
    },
    AxiosErrorHandler(errorText) {
      this.$notify({
        group: "error",
        title: "Error",
        text: typeof errorText === "string" ? errorText : JSON.stringify(errorText),
        type: "error",
      });
    },
  },
};
</script>

<style scoped>
.content-wrapper {
  width: 100%;
}

.container::after {
  content: "";
  display: table;
  clear: both;
}

.input-block {
  margin: 0 auto;
  height: 35vh;
  width: 50%;
  border: 2px solid black;
  border-radius: 10px;
  background-color: rgba(255, 255, 255, 0);
  box-sizing: border-box;
}

.input-header h2 {
  text-align: center;
}

.vhod-block {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.input-login {
  margin-left: 20%;
}

.input-password {
  margin-right: 20%;
}

.inputText {
  padding: 6px 8px;
  border: 2px solid black;
  border-radius: 10px;
  background: white;
  font-size: 16px;
}

.send-button {
  text-align: center;
}

.mybutton {
  margin: 20px 10px 10px 10px;
  padding: 10px 20px;
  border: 2px solid black;
  border-radius: 10px;
  background: white;
  cursor: pointer;
  font-size: 16px;
}

.mybutton:hover {
  background: #f2f2f2;
}

@media (max-width: 1176px) {
  .input-block {
    height: auto;
    width: 95%;
  }
}

@media (max-width: 699px) {
  .input-block {
    padding: 18px 16px 24px;
    box-sizing: border-box;
  }

  .vhod-block {
    display: grid;
    grid-template-columns: 1fr;
    gap: 22px;
    margin-top: 18px;
  }

  .input-login,
  .input-password {
    width: 100%;
    max-width: 420px;
    margin: 0 auto;
    padding: 0;
    box-sizing: border-box;
    text-align: left;
  }

  .input-login h3,
  .input-password h3 {
    margin: 0 0 10px 0;
    padding: 0;
    line-height: 1.2;
  }

  .inputText {
    width: 100%;
    box-sizing: border-box;
    display: block;
  }

  .send-button {
    display: grid;
    grid-template-columns: 1fr;
    gap: 14px;
    justify-items: center;
    margin-top: 18px;
  }

  .mybutton {
    width: 100%;
    max-width: 420px;
    margin: 0;
  }
}

</style>
