<template>
  <div id="app">
    <AppHeader />

    <div v-if="loading" class="loader-overlay">
      <Loader />
    </div>

    <div id="contentContainer" v-else>
      <router-view />
      <notifications group="error" position="bottom right" max="5" />
      <notifications group="success" position="bottom right" max="5" />
    </div>
  </div>
</template>

<script>
import AppHeader from "@/components/Header";
import Loader from "@/components/Loader";

export default {
  name: "App",
  components: {
    AppHeader,
    Loader
  },
  data() {
    return {
      loading: true
    };
  },
  mounted() {
    // имитация загрузки)
    setTimeout(() => {
      this.loading = false;
    }, 800);
  }
};
</script>

<style>
#app {
  font-family: sans-serif;
  color: #2c3e50;
}

body {
  margin: 0;
  padding: 0;
  background-color: whitesmoke;
}

#contentContainer {
  padding: 10px;
}

.loader-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;

  display: flex;
  align-items: center;
  justify-content: center;

  background-color: rgba(255, 255, 255, 0.8);
  z-index: 999;
}
</style>
