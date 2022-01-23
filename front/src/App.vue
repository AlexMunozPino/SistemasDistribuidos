<template>
  <v-app id="app">
    <appbar/>
    <div>
      <router-view/>
      <gotop/>
    </div>
  </v-app>
</template>

<script>
import appbar from "./components/AppBar.vue"
import gotop from "./components/GoTop.vue"

export default{
  name:'App',
  components:{
    appbar,
    gotop
  }
}
</script>

<style lang="scss">
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background: url("./assets/world2.jpg") no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}
</style>