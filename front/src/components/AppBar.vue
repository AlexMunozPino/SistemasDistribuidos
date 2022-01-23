<template>
  <div>
    <v-app-bar
      color="transparent"
      dark
      elevation="24"
    >
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-spacer/>
      <v-toolbar-title>EARTHQUAKE DATA ANALYSIS USACH</v-toolbar-title>
      <v-spacer/>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
      color="#000000"
      class="navigator"
      dark
    >
      <v-list
        nav
        dense
      >
          <v-list-item-group
            v-model="group"
          >
            <v-list-item @click="cargarComp(1)">
              <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Home</v-list-item-title>
            </v-list-item>

            <v-list-item @click="cargarComp(2)">
              <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
              </v-list-item-icon>
              <v-list-item-title>About us</v-list-item-title>
            </v-list-item>
          </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
  export default {
    name: 'AppBar',
    data: () => ({
      drawer: false,
      group: null,
    }),
    methods: {
      cargarComp(componente){
        switch(componente){

          case 1:
            this.$router.push('/')
          break;

          case 2:
            this.$router.push('/about')
          break;
        }
      },
    },
  }
</script>

<style scoped>
  .navigator{
    text-align:justify;
    opacity: 70%;
  }
</style>