<template>
  <div class="header">
    <h1>
      Хлопцов Сергей Дмитриевич P3213, вариант 1758
    </h1>
    <a
        id="git"
        href="https://github.com/ser535/WEB"
        target="_blank"
        rel="noreferrer"
    >
      проект на гитхабе
    </a>
  </div>
</template>

<script>
export default {
  name: "AppHeader"
}
</script>

<style scoped>
.header {
  height: 3vh;
  text-align: center;
  color: black;
  font-family: sans-serif;
  font-size: 15px;
  position: relative;
}

#git {
  position: absolute;
  right: 50px;
  top: 50%;
  transform: translateY(-50%);
  color: black;
  text-decoration: none;
}

#git:hover {
  text-decoration: underline;
}
@media (max-width: 1176px) {
  .header {
    height: auto;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 6px;
  }

  #git {
    position: static;
    transform: none;
    margin-top: 4px;
    font-size: 14px;
  }
}

</style>
